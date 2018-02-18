/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 *
 *  http://www.goorulearning.org/
 *
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package org.ednovo.gooru.application.server.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.ednovo.gooru.application.client.service.ProfilePageService;
import org.ednovo.gooru.application.server.annotation.ServiceURL;
import org.ednovo.gooru.application.server.deserializer.ProfileLibraryDeserializer;
import org.ednovo.gooru.application.server.deserializer.ResourceDeserializer;
import org.ednovo.gooru.application.server.form.ResourceFormFactory;
import org.ednovo.gooru.application.server.request.JsonResponseRepresentation;
import org.ednovo.gooru.application.server.request.ServiceProcessor;
import org.ednovo.gooru.application.server.request.UrlToken;
import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.ProfileCodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.shared.util.GooruConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

@Service("profilePageService")
@ServiceURL("/profilePageService")
public class ProfilePageServiceImpl extends BaseServiceImpl implements ProfilePageService {

	private static final long serialVersionUID = -3513384975044132831L;
	private String pageNum = "1";
	private String pageSize = "50";

	private static final String PROFILE = "profile";

	private static final String COURSES = "courses";

	@Autowired
	ResourceDeserializer resourceDeserializer;
	private static Logger logger=LoggerFactory.getLogger(ProfilePageServiceImpl.class);
	public CollectionDo deserializeCollection(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject()
						.toString(), CollectionDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new CollectionDo();
	}


	public List<CollectionItemDo> deserializeWorkspace(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				return JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<List<CollectionItemDo>>() {
				});
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return new ArrayList<CollectionItemDo>();
	}

	@Override
	public List<CollectionItemDo> getFolders(String collectionId) throws GwtException {
		JsonRepresentation jsonRep = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_COLLECTION_ITEMS, collectionId);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.PAGE_SIZE, pageNum);
		params.put(GooruConstants.PAGE_NO, pageSize);
		params.put(GooruConstants.SHARING, GooruConstants.PUBLIC);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
//		url+="&sharing=public";
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		getLogger().info("getFolders:"+url);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		List<CollectionItemDo> collectionItemDo = deserializeCollectionItems(jsonRep);
		//Collections.sort(collectionItemDo, new ArrayListSorter("itemSequence", true));
		return collectionItemDo;
	}

	public List<CollectionItemDo> deserializeCollectionItems(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<List<CollectionItemDo>>() {
				});
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new ArrayList<CollectionItemDo>();
	}

	 public String getUUID(){
	        return String.valueOf(UUID.randomUUID());
	 }

	@Override
	public void addGradeUserProfile(String grade, String userLevel) throws GwtException {
		JsonRepresentation jsonRep = null;
		String userMetaActiveFlag = "1";
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_USER_BIOGRAPHY, getLoggedInUserUid());
		ProfileDo profileDo = new ProfileDo();
		profileDo.setGrade(grade);
		String formData = ResourceFormFactory.generateStringDataForm(profileDo,PROFILE);
		if(userLevel.equalsIgnoreCase("profilePage")) {
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_USER_GRADE_COURSE, getLoggedInUserUid());
//			formData = "{\"profile\":{\"grade\":\""+grade+"\"}, \"userMetaActiveFlag\" : \"1\"}";
			formData = getAddGradeUserProfileJsonString(grade,userMetaActiveFlag);
			getLogger().info("-- json paylooad addGradeUserProfile -- "+formData);
		}
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), formData);
		jsonRep = jsonResponseRep.getJsonRepresentation();
	}

	/**
	 * Returns jsaon payload.
	 *
	 * @param grade
	 * @param userMetaActiveFlag
	 * @return
	 */
	private String getAddGradeUserProfileJsonString(String grade, String userMetaActiveFlag) {
		JSONObject dataObj = new JSONObject();
		JSONObject profileObj = new JSONObject();
		try {
			profileObj.put(GooruConstants.GRADE, grade);
			dataObj.put(GooruConstants.USER_META_ACTIVE_FLAG, userMetaActiveFlag);
			dataObj.put(GooruConstants.PROFILE, profileObj);

		} catch (Exception e) {
			getLogger().error("-- "+e.getMessage());
		}
		return dataObj.toString();

	}

	/**
	 * Returns jsaon payload.
	 * @param grade
	 * @return
	 */
	private String getDelGradeUserProfileSettingsJsonStr(String grade) {
		JSONObject dataObj = new JSONObject();
		try {
			dataObj.put(GooruConstants.GRADE, grade);
		} catch (Exception e) {
			getLogger().error("-- "+e.getMessage());
		}
		return dataObj.toString();

	}

	@Override
	public void deleteGradeUserProfile(String grade, String userLevel) throws GwtException {
		String formData = "";
		String url = "";
		String userMetaActiveFlag = "0";
		JsonRepresentation jsonRep = null;
		if(userLevel.equalsIgnoreCase("profilePage")) {
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_USER_GRADE_COURSE, getLoggedInUserUid());
//			formData = "{\"profile\":{\"grade\":\""+grade+"\"}, \"userMetaActiveFlag\" : \"0\"}";
			formData = getAddGradeUserProfileJsonString(grade,userMetaActiveFlag);
			getLogger().info("-- deleteGradeUserProfile in if -- "+formData);
		} else if(userLevel.equalsIgnoreCase("settings")) {
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.DELETE_USER_BIOGRAPHY, getLoggedInUserUid());
//			formData = "{\"grade\":\""+grade+"\"}";
			formData = getDelGradeUserProfileSettingsJsonStr(grade);
			getLogger().info("-- deleteGradeUserProfile in else -- "+formData);
		}
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), formData);
		jsonRep = jsonResponseRep.getJsonRepresentation();
	}

	@Override
	public void addCourseUserProfile(Set<ProfileCodeDo> profileCodeDo, String userLevel) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_USER_BIOGRAPHY, getLoggedInUserUid());
		String userMetaActiveFlag = "1";
		ProfileDo profileDo = new ProfileDo();
		profileDo.setCourses(profileCodeDo);
		String formData = ResourceFormFactory.generateStringDataForm(profileDo,PROFILE);
		if(userLevel.equalsIgnoreCase("profilePage")) {

			Iterator codeDoList = profileCodeDo.iterator();
			while(codeDoList.hasNext()) {
				formData = ResourceFormFactory.generateStringMultiDataForm(codeDoList.next(),COURSES);
			}

			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_USER_GRADE_COURSE, getLoggedInUserUid());
			formData = "{\"profile\":" +formData+ ", \"userMetaActiveFlag\" : \"1\"}";
//			formData = getAddCourseUserProfileJsonString(formData,userMetaActiveFlag);
			getLogger().info("---addCourseUserProfile payload --  "+formData);
		}
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), formData);
		jsonRep = jsonResponseRep.getJsonRepresentation();
	}

	/**
	 * Returns jsaon payload.
	 *
	 * @param formData
	 * @param userMetaActiveFlag
	 * @return
	 */
	private String getAddCourseUserProfileJsonString(String formData, String userMetaActiveFlag) {
		JSONObject dataObj = new JSONObject();
		try {
			dataObj.put(GooruConstants.USER_META_ACTIVE_FLAG, userMetaActiveFlag);
			dataObj.put(GooruConstants.PROFILE, formData);

		} catch (Exception e) {
			getLogger().error("--- "+e.getMessage());
		}
		return dataObj.toString();

	}

	@Override
	public void deleteCourseUserProfile(CodeDo codeDo, String userLevel) throws GwtException {
		JsonRepresentation jsonRep = null;
		String userMetaActiveFlag = "0";
		ProfileCodeDo profileCodeDo = new ProfileCodeDo();
		profileCodeDo.setCode(codeDo);
		String formData = ResourceFormFactory.generateStringMultiDataForm(profileCodeDo,COURSES);
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.DELETE_USER_BIOGRAPHY, getLoggedInUserUid());
		if(userLevel.equalsIgnoreCase("profilePage")) {
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_USER_GRADE_COURSE, getLoggedInUserUid());
			formData = "{\"profile\":" +formData+ ", \"userMetaActiveFlag\" : \"0\"}";

//			formData = getAddCourseUserProfileJsonString(formData,userMetaActiveFlag);
			getLogger().info("---deleteCourseUserProfile   --  "+formData);
		}
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), formData);
		jsonRep = jsonResponseRep.getJsonRepresentation();
	}

	@Override
	public ProfileLibraryListDo getProfileLibraryWorkspace(String gooruUid, int limit, String sharingType, String collectionType, String placeToken, int offset) throws GwtException {
		ProfileLibraryListDo profileLibraryListDo = new ProfileLibraryListDo();
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		String sessionToken = getLoggedInSessionToken();
		partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_PROFILE_WORKSPACE, gooruUid);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.FETCH_CHILDS, GooruConstants.TRUE);
		params.put(GooruConstants.ITEM_LIMIT, String.valueOf(limit));
		params.put(GooruConstants.OFFSET, String.valueOf(offset));
		params.put(GooruConstants.LIMIT, "20");
		params.put(GooruConstants.ORDER_BY, GooruConstants.SEQUENCE);
		if(sharingType!=null){
			params.put(GooruConstants.SHARING, sharingType);
//			sessionToken=sessionToken+"&sharing="+sharingType;
		}
		if(collectionType!=null){
			params.put(GooruConstants.COLLECTION_TYPE, collectionType);
//			sessionToken=sessionToken+"&collectionType="+collectionType;
		}

		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("--- getProfileLibraryWorkspace -"+url);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		profileLibraryListDo = new ProfileLibraryDeserializer().deserializeFolderList(jsonRep);
		return profileLibraryListDo;
	}

	@Override
	public ProfileLibraryListDo getProfilePaginationWorkspace(String parentId, String sharingType, int limit) throws GwtException {
		ProfileLibraryListDo profileLibraryListDo = new ProfileLibraryListDo();
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		String sessionToken = getLoggedInSessionToken();

		partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_PARTNER_CHILD_FOLDER_LIST, parentId);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.FETCH_CHILDS,GooruConstants.TRUE);
		params.put(GooruConstants.ITEM_LIMIT, String.valueOf(limit));
		params.put(GooruConstants.OFFSET, "0");
		params.put(GooruConstants.ORDER_BY, GooruConstants.SEQUENCE);
		if(sharingType!=null){
			params.put(GooruConstants.SHARING, sharingType);
//			sessionToken=sessionToken+"&sharing="+sharingType;
		}
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("getProfilePaginationWorkspace:"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		profileLibraryListDo = new ProfileLibraryDeserializer().deserializeFolderList(jsonRep);
		return profileLibraryListDo;
	}

	@Override
	public ProfileLibraryDo getProfileLibraryCollection(String gooruOid, boolean skipCollectionItems) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_COLLECTION, gooruOid);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.SKIP_COLL_ITEM, String.valueOf(skipCollectionItems));
		params.put(GooruConstants.INCLUDE_META_INFO,GooruConstants.TRUE );
		params.put(GooruConstants.MERGE, GooruConstants.PERMISSIONS);
		params.put(GooruConstants.INCLUDE_CONTENT_PROVDER, GooruConstants.FALSE);
		params.put(GooruConstants.INCLUDE_CUSTOM_FIELDS,GooruConstants.FALSE);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		getLogger().info("getProfileLibraryCollection:"+url);
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep != null && jsonResponseRep.getStatusCode()==200){
			return deserializeConcept(jsonRepresentation);
		}else{
			return null;
		}
		
	}

	public ProfileLibraryDo deserializeConcept(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), ProfileLibraryDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new ProfileLibraryDo();
	}

}
