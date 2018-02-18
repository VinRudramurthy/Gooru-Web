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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.client.service.ResourceService;
import org.ednovo.gooru.application.server.annotation.ServiceURL;
import org.ednovo.gooru.application.server.deserializer.ResourceDeserializer;
import org.ednovo.gooru.application.server.form.ResourceFormFactory;
import org.ednovo.gooru.application.server.request.JsonResponseRepresentation;
import org.ednovo.gooru.application.server.request.ServiceProcessor;
import org.ednovo.gooru.application.server.request.UrlToken;
import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionAddQuestionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemsListDo;
import org.ednovo.gooru.application.shared.model.content.CollectionMetaInfoDo;
import org.ednovo.gooru.application.shared.model.content.CollectionProfileItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionSettingsDo;
import org.ednovo.gooru.application.shared.model.content.ExistsResourceDo;
import org.ednovo.gooru.application.shared.model.content.GetFlagContentDO;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.content.MetaDO;
import org.ednovo.gooru.application.shared.model.content.NewResourceDo;
import org.ednovo.gooru.application.shared.model.content.PermissionsDO;
import org.ednovo.gooru.application.shared.model.content.ProfanityCheckDo;
import org.ednovo.gooru.application.shared.model.content.ResourceCollDo;
import org.ednovo.gooru.application.shared.model.content.ResourceDo;
import org.ednovo.gooru.application.shared.model.content.ResourceFormatDo;
import org.ednovo.gooru.application.shared.model.content.ResourceMetaInfoDo;
import org.ednovo.gooru.application.shared.model.content.ResourceTagsDo;
import org.ednovo.gooru.application.shared.model.content.ResourceTypeDo;
import org.ednovo.gooru.application.shared.model.content.SearchRatingsDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;
import org.ednovo.gooru.application.shared.model.content.checkboxSelectedDo;
import org.ednovo.gooru.application.shared.model.drive.ErrorDo;
import org.ednovo.gooru.application.shared.model.drive.GoogleDriveDo;
import org.ednovo.gooru.application.shared.model.drive.GoogleDriveItemDo;
import org.ednovo.gooru.application.shared.model.folder.CourseSummaryDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.application.shared.model.library.ProfanityDo;
import org.ednovo.gooru.application.shared.model.user.GoogleToken;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.application.shared.model.user.UserDoMorePeople;
import org.ednovo.gooru.shared.util.GooruConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;


@Service("resourceService")
@ServiceURL("/resourceService")
public class ResourceServiceImpl extends BaseServiceImpl implements ResourceService {

	private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

	private static final long serialVersionUID = 3247182821197046755L;

	private static final String ADD_TO_SHELF = "addToShelf";

	private static final String TAXONOMY_CODE = "taxonomyCode";

	private static final String PARENT_ID = "parentId";

	private static final String YOUTUBE_PART_DETAILS = "contentDetails";

	private static final String RESOURCE_ID = "resourceId";

	private static final String COLLECTION = "collection";

	private static final String GRADE = "grade";

	private static final String TITLE = "title";

	private static final String TRUE = "true";

	private static final String ADDED = "added";

	private static final String JSON = "json";

	private static final String FALSE = "false";

	private static final String RESOURCE = "resource";

	private static final String GOOGLE_DRIVE = "Google Drive";

	private static final String NEW_RESOURCE = "newResource";

	private static final String TAXONOMY_SET = "taxonomySet";

	private static final String RESOURCE_TAGS = "resourceTags";

	private static final String CHOOSE = "Please choose one of the following...";

	@Autowired
	ResourceDeserializer resourceDeserializer;

	@Override
	public CollectionDo createCollection(CollectionDo collectionDo, String codeId) {
		CollectionDo collectionDoObj;
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_CREATE_COLLECTION_IN_FOLDER);
		getLogger().info("createCollection API post url::::"+url);
		//collectionDo.setAddToShelf(TRUE);
		if (codeId != null) {
			Set<CodeDo> codeDo=new HashSet<>();
			CodeDo codeDoObj=new CodeDo();
			codeDoObj.setCodeId(Integer.parseInt(codeId));
			codeDo.add(codeDoObj);
			collectionDo.setTaxonomySet(codeDo);
		}
		if(collectionDo.getSettings()!=null){
			collectionDo.getSettings().setComment("turn-on");
		}else{
			CollectionSettingsDo collSetting = new CollectionSettingsDo();
			collSetting.setComment("turn-on");
			collectionDo.setSettings(collSetting);
		}
		String form = ResourceFormFactory.generateStringDataForm(collectionDo, COLLECTION);

		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj = new JSONObject(form);
			jsonObj.put(ADD_TO_SHELF, TRUE);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		getLogger().info("payload data create collection API:::"+jsonObj.toString());

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(), jsonObj.toString());
		jsonRep = jsonResponseRep.getJsonRepresentation();

		logger.info("createCollection 1 : "+url);
		logger.info("jsonObj.toString() : "+jsonObj.toString());
		logger.info("jsonResponseRep.getStatusCode() : "+jsonResponseRep.getStatusCode());

		if(jsonResponseRep.getStatusCode()==200){
			collectionDoObj = deserializeCollection(jsonRep);
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}else{
			collectionDoObj=new CollectionDo();
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}
		return collectionDoObj;
	}

	@Override
	public void deleteCollection(String collectionId) {
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.DELETE_COLLECTION, collectionId);
		getLogger().info("del coll -- "+url);
		ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
	}

	@Override
	public CollectionItemDo createCollectionItem(String collectionId, String resourceId) {
		JsonRepresentation jsonRep = null,jsonResponseRepget=null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_ADDRESOURCE_COLLECTION,collectionId,resourceId);
		try{
		getLogger().info("---createCollectionItem--  "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(),"");
		jsonRep = jsonResponseRep.getJsonRepresentation();

		String getURL = getRestEndPoint()+jsonRep.getJsonObject().getString("uri");
		getLogger().info("--- getURL -- "+getURL);

		JsonResponseRepresentation jsonResponseRep1 = ServiceProcessor.get(getURL, getRestUsername(), getRestPassword());
		jsonResponseRepget=jsonResponseRep1.getJsonRepresentation();
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return deserializeCollectionItem(jsonResponseRepget);
	}

	@Override
	public CollectionItemDo createNewCollectionItem(String collectionId, String resourceId,String resoruceType) {
		JsonRepresentation jsonRep = null,jsonResponseRepget=null;
		String url;
		try{
			if("Question".equalsIgnoreCase(resoruceType)){
				url=UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_ADDQUESTION_COLLECTION,collectionId,resourceId);
			}else{
				url=UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_ADDRESOURCE_COLLECTION,collectionId,resourceId);
			}
			getLogger().info("--- createNewCollectionItem -- "+url);
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword());
			jsonRep = jsonResponseRep.getJsonRepresentation();
			getLogger().info("--- jsonRep -- "+jsonRep.getJsonObject());
			String getURL = getRestEndPoint()+jsonRep.getJsonObject().getString("uri");
			getLogger().info("--- getURL -- "+getURL);
			JsonResponseRepresentation jsonResponseRep1 = ServiceProcessor.get(getURL, getRestUsername(), getRestPassword());
			jsonResponseRepget=jsonResponseRep1.getJsonRepresentation();
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return deserializeCollectionItem(jsonResponseRepget);
	}
	@Override
	public void deleteCollectionItem(String collectionId,String collectionItemId) {
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_DELETE_COLLECTION_ITEM,collectionId,collectionItemId);
		getLogger().info("--- deleteCollectionItem -- "+url);
		ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
	}

	@Override
	public CollectionItemDo reorderCollectionItem(CollectionItemDo collectionItemDo) {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_REORDER_COLLECTION_ITEM_SEQUENCE, collectionItemDo.getCollectionItemId(), collectionItemDo.getItemSequence() + "");
		getLogger().info("--- reorder resource -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), new Form());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeCollectionItem(jsonRep);
	}

	@Override
	public CollectionDo copyCollection(CollectionDo collectionDo, String addToShelf, String taxonomyCode) {
		CollectionDo collectionDoObj=new CollectionDo();
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_COPY_COLLECTION, collectionDo.getGooruOid());
		try{
			JSONObject copyCollectionJsonObject=new JSONObject();
			JSONObject itemTypeJsonObject=new JSONObject();
				itemTypeJsonObject.put("collectionType", "collection");
				itemTypeJsonObject.put("title", collectionDo.getTitle());
				if(taxonomyCode != null) {
					itemTypeJsonObject.put(TAXONOMY_SET, taxonomyCode);
				}
			copyCollectionJsonObject.put("collection", itemTypeJsonObject);
			copyCollectionJsonObject.put("addToShelf", "true");
			getLogger().info("--- Copy collection URl -- "+url);
			getLogger().info("-- Copy coll payload (Put method) -- "+copyCollectionJsonObject.toString());
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), copyCollectionJsonObject.toString());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			collectionDoObj = deserializeCollection(jsonRep);
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}else{
			collectionDoObj=new CollectionDo();
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}
		}
		catch(Exception ex){
			logger.error("Exception::", ex);
		}
		return collectionDoObj;
	}

	@Override
	public CollectionDo getCollection(String collectionGooruOid, boolean skipCollectionItem) {
		CollectionDo collectionDoObj;
		JsonRepresentation jsonRep = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_GET_COLLECTION_RESOURCES, collectionGooruOid);
		Map<String, String> params = new LinkedHashMap<>();
		params.put(GooruConstants.INCLUDU_ITEMS,GooruConstants.TRUE);
		//Get last modified user details when collaborate
		if(!skipCollectionItem){
			params.put(GooruConstants.INCLUDE_LASTMODIFIED_USER,GooruConstants.TRUE);
		}
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("get coll res url --- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();

		if(jsonResponseRep.getStatusCode()==200){
			collectionDoObj = deserializeCollection(jsonRep);
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}else{
			collectionDoObj=new CollectionDo();
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}

		try {
			if(collectionDoObj.getLanguageObjective() != null)
			{
			collectionDoObj.setLanguageObjective(URLDecoder.decode(collectionDoObj.getLanguageObjective(), "UTF-8"));
			}
			if(collectionDoObj.getKeyPoints() != null)
			{
			collectionDoObj.setKeyPoints(URLDecoder.decode(collectionDoObj.getKeyPoints(), "UTF-8"));
			}

        } catch (UnsupportedEncodingException e) {
        	logger.error("Exception::", e);
        }

		return collectionDoObj;

	}

	public ResourceMetaInfoDo deserializeResourceMetaInfo(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), ResourceMetaInfoDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new ResourceMetaInfoDo();
	}

	public CollectionDo deserializeCollection(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				CollectionDo obj=new CollectionDo();
				if(!jsonRep.getJsonObject().isNull("summary")){
					CourseSummaryDo courseSummaryDoObj 	=JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONObject("summary").toString(), CourseSummaryDo.class);
					obj.setSummary(courseSummaryDoObj);
				}
				obj.setCollectionType(jsonRep.getJsonObject().isNull("collectionType")?"":jsonRep.getJsonObject().getString("collectionType"));
				obj.setType(jsonRep.getJsonObject().isNull("type")?"":jsonRep.getJsonObject().getString("type"));
				obj.setGooruOid(jsonRep.getJsonObject().isNull("gooruOid")?"":jsonRep.getJsonObject().getString("gooruOid"));
				obj.setTitle(jsonRep.getJsonObject().isNull("title")?"":jsonRep.getJsonObject().getString("title"));
				obj.setSharing(jsonRep.getJsonObject().isNull("sharing")?"":jsonRep.getJsonObject().getString("sharing"));
				obj.setViews(jsonRep.getJsonObject().getInt("views")+"");
				obj.setGoals(jsonRep.getJsonObject().isNull("goals")?"":jsonRep.getJsonObject().getString("goals"));
				obj.setDescription(jsonRep.getJsonObject().isNull("description")?"":jsonRep.getJsonObject().getString("description"));
				obj.setLanguageObjective(jsonRep.getJsonObject().isNull("languageObjective")?"":jsonRep.getJsonObject().getString("languageObjective"));
				List<checkboxSelectedDo> checkboxSelectedDos=new ArrayList<>();

				if(jsonRep.getJsonObject().has("settings")){
					CollectionSettingsDo settings=JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONObject("settings").toString(), CollectionSettingsDo.class);
					obj.setSettings(settings);
				}
				if(jsonRep.getJsonObject().has("thumbnails")){
					ThumbnailDo thumbnailDo=JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONObject("thumbnails").toString(), ThumbnailDo.class);
					obj.setThumbnails(thumbnailDo);
				}

				List<String> lstPermission = new ArrayList<>();
				if(jsonRep.getJsonObject().has("permissions")){
					JSONArray permissionsArray=jsonRep.getJsonObject().getJSONArray("permissions");
					for (int i=0;i<permissionsArray.length();i++){
						lstPermission.add(permissionsArray.getString(i));
					}
					obj.setPermissions(lstPermission);
				}

				if(jsonRep.getJsonObject().has("audience")){
					JSONArray array=jsonRep.getJsonObject().getJSONArray("audience");
					for(int i=0;i<array.length();i++){
						checkboxSelectedDo item=JsonDeserializer.deserialize(array.getJSONObject(i).toString(), checkboxSelectedDo.class);
						checkboxSelectedDos.add(item);
					}
				}
				obj.setAudience(checkboxSelectedDos);
				List<checkboxSelectedDo> checkboxSelectedDos1=new ArrayList<>();

				if(jsonRep.getJsonObject().has("depthOfKnowledge")){
					JSONArray array=jsonRep.getJsonObject().getJSONArray("depthOfKnowledge");
					for(int i=0;i<array.length();i++){
						checkboxSelectedDo item=JsonDeserializer.deserialize(array.getJSONObject(i).toString(), checkboxSelectedDo.class);
						checkboxSelectedDos1.add(item);
					}
				}


				CollectionMetaInfoDo metaInfoDo = new CollectionMetaInfoDo();

				List<StandardFo> lstStandardsFo =new ArrayList<>();
				if(jsonRep.getJsonObject().has("standards")){
					JSONArray array=jsonRep.getJsonObject().getJSONArray("standards");
					for(int i=0;i<array.length();i++){
						StandardFo item=JsonDeserializer.deserialize(array.getJSONObject(i).toString(), StandardFo.class);
						lstStandardsFo.add(item);
					}
				}
				metaInfoDo.setStandards(lstStandardsFo);

				List<String> lstCourse =new ArrayList<>();
				if(jsonRep.getJsonObject().has("taxonomyCourse")){
					JSONArray array=jsonRep.getJsonObject().getJSONArray("taxonomyCourse");
					for(int i=0;i<array.length();i++){
						checkboxSelectedDo item=JsonDeserializer.deserialize(array.getJSONObject(i).toString(), checkboxSelectedDo.class);
						lstCourse.add(item.getName());
					}
				}
				metaInfoDo.setCourse(lstCourse);

				List<StandardFo> lstSkills =new ArrayList<>();
				if(jsonRep.getJsonObject().has("skills")){
					JSONArray array=jsonRep.getJsonObject().getJSONArray("skills");
					for(int i=0;i<array.length();i++){
						StandardFo item=JsonDeserializer.deserialize(array.getJSONObject(i).toString(), StandardFo.class);
						lstSkills.add(item);
					}
				}
				metaInfoDo.setSkills(lstSkills);

				obj.setMetaInfo(metaInfoDo);

				obj.setDepthOfKnowledges(checkboxSelectedDos1);
				obj.setPublishStatus(jsonRep.getJsonObject().isNull("publishStatus")?"":jsonRep.getJsonObject().getString("publishStatus"));
				long time = jsonRep.getJsonObject().isNull("lastModified")?0:jsonRep.getJsonObject().getLong("lastModified");
				Date lastModifiedTime= new Date(time);
				obj.setLastModified(lastModifiedTime);
				obj.setCollaborator(jsonRep.getJsonObject().isNull("isCollaborator")?false:jsonRep.getJsonObject().getBoolean("isCollaborator"));
				UserDo user=JsonDeserializer.deserialize(jsonRep.getJsonObject().getString("user"), UserDo.class);
				obj.setUser(user);
				UserDo lastUserModified=JsonDeserializer.deserialize(jsonRep.getJsonObject().isNull("lastUserModified")?"":jsonRep.getJsonObject().getString("lastUserModified"), UserDo.class);
				obj.setLastModifiedUser(lastUserModified);
				if(!jsonRep.getJsonObject().isNull("collectionItems")){
				JSONArray array=jsonRep.getJsonObject().getJSONArray("collectionItems");
				List<CollectionItemDo> collectionItems=new ArrayList<>();
				for(int i=0;i<array.length();i++){
					CollectionItemDo item = JsonDeserializer.deserialize(array.getJSONObject(i).toString(), CollectionItemDo.class);
					ResourceDo resoruce = JsonDeserializer.deserialize(array.getJSONObject(i).toString(), ResourceDo.class);
					item.setQuestionInfo(resoruce);
					item.setResource(resoruce);
					collectionItems.add(item);
				}
				obj.setCollectionItems(collectionItems);
				}
				return obj;
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new CollectionDo();
	}

	public CollectionItemDo deserializeCollectionItem(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				CollectionItemDo item = JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), CollectionItemDo.class);
				ResourceDo resoruce = JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), ResourceDo.class);
				item.setResource(resoruce);
				item.setQuestionInfo(resoruce);
				return item;
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new CollectionItemDo();
	}

	public CollectionQuestionItemDo deserializeCollectionQuestionItemDo(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), CollectionQuestionItemDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new CollectionQuestionItemDo();
	}
	public MetaDO  deserializePermissions(JsonRepresentation jsonRep){
		if(jsonRep !=null && jsonRep.getSize() !=-1){
			try{
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), MetaDO.class);
			}catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new MetaDO();
	}
	public List<CollectionDo> deserializeCollections(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				return JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<List<CollectionDo>>() {
				});
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return new ArrayList<>();
	}

	public List<CollectionItemsListDo> deserializeMyUserCollections(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {


				return JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<List<CollectionItemsListDo>>() {});
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return new ArrayList<>();
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
		return new ArrayList<>();
	}

	public List<UserDo> deserializeCollaborators(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<List<UserDo>>() {
				});
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new ArrayList<>();
	}



	@Override
	public CollectionDo createCollectionWithItem(CollectionDo collectionDo, String codeId, String resourceId) {
		JsonRepresentation jsonRep = null;
		CollectionDo collectionDoObj;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_CREATE_COLLECTION_IN_FOLDER);
		if (codeId != null) {
			Set<CodeDo> codeDo=new HashSet<>();
			CodeDo codeDoObj=new CodeDo();
			codeDoObj.setCodeId(Integer.parseInt(codeId));
			codeDo.add(codeDoObj);
			collectionDo.setTaxonomySet(codeDo);
		}
		CollectionSettingsDo collSetting = new CollectionSettingsDo();
		collSetting.setComment("turn-on");
		collectionDo.setSettings(collSetting);

		String form = ResourceFormFactory.generateStringDataForm(collectionDo, COLLECTION);
		JSONObject jsonObj = new JSONObject();
		try {
		jsonObj = new JSONObject(form);
		jsonObj.put(ADD_TO_SHELF, TRUE);
		if (resourceId != null) {
			jsonObj.put(RESOURCE_ID,resourceId);
		}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(), jsonObj.toString());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			collectionDoObj = deserializeCollection(jsonRep);
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}else{
			collectionDoObj=new CollectionDo();
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}
		return collectionDoObj;
	}

	@Override
	public CollectionDo updateCollectionMetadata(String collectionId, String title, String description, String grade, String sharing, String vocabulary, String taxonomyCode, String updateTaxonomyByCode, String mediaType,String action) {
		JsonRepresentation jsonRep = null;
		CollectionDo collectionDoObj;
	    String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_V2_COLLLECTION, collectionId);
	    JSONObject classPageJsonObject=new JSONObject();
		JSONObject collectionTypeJsonObject=new JSONObject();
		try{
			if(title!=null){
				collectionTypeJsonObject.put(TITLE, title);
			}
			if(description != null){
				collectionTypeJsonObject.put("goals", description);
			}
			if(grade!=null){
				collectionTypeJsonObject.put(GRADE, grade);
			}
			if(sharing!=null){
				collectionTypeJsonObject.put("sharing", sharing);
			}
			if(vocabulary!=null){
				collectionTypeJsonObject.put("vocabulary", vocabulary);
			}
			if(taxonomyCode!=null){
				JSONArray taxonomySet = new JSONArray();
				JSONObject code = new JSONObject();
				code.put("codeId", Integer.parseInt(taxonomyCode));
				taxonomySet.put(code);
				collectionTypeJsonObject.put("taxonomySet", taxonomySet);
			}
			if(mediaType!=null){
				collectionTypeJsonObject.put("mediaType", mediaType);
			}
			if(action!=null){
				collectionTypeJsonObject.put("action", action);
			}
			classPageJsonObject.put("collection", collectionTypeJsonObject);
		}catch(JSONException | NumberFormatException e){
                    // TODO: AM: Do we need to continue here or return/throw prematurely
			logger.error("Exception::", e);
		}
	 	getLogger().info("updateCollectionMetadata::API:"+url);
	 	getLogger().info("data passed:::"+classPageJsonObject.toString());
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), classPageJsonObject.toString());
	    jsonRep = jsonResponseRep.getJsonRepresentation();
	    if(jsonResponseRep.getStatusCode()==200){
			collectionDoObj = deserializeCollection(jsonRep);
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}else{
			collectionDoObj=new CollectionDo();
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}
	    return collectionDoObj;
	}

	@Override
	public void updateCollection(String collectionType,String collectionId, String title, String sharing,
			List<String> depthOfKnowledgeIds, List<String> skillsIds, List<String> audienceIds, String mediaFilename,String buildTypeId) {
		JsonRepresentation jsonRep = null;
		CollectionDo collectionDoObj;
	    String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_V2_COLLLECTION, collectionId);
	    JSONObject classPageJsonObject=new JSONObject();
		JSONObject collectionTypeJsonObject=new JSONObject();
		try{
			if(title!=null){
				collectionTypeJsonObject.put(TITLE, title);
			}
			if(collectionType != null){
				collectionTypeJsonObject.put("collectionType", collectionType);
			}
			if(sharing!=null){
				collectionTypeJsonObject.put("sharing", sharing);
			}
			classPageJsonObject.put("collection", collectionTypeJsonObject);
		}catch(Exception e){
			logger.error("Exception::", e);
		}
	 	getLogger().info("updateCollection::API:"+url);
	 	getLogger().info("data passed:::"+classPageJsonObject.toString());
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), classPageJsonObject.toString());
	    jsonRep = jsonResponseRep.getJsonRepresentation();
	    if(jsonResponseRep.getStatusCode()==200){
			collectionDoObj = deserializeCollection(jsonRep);
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}else{
			collectionDoObj=new CollectionDo();
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}
	}
	@Override
	public CollectionDo update21CenturySkills(String collectionId,String action,Map<Long, String> skillsData){
		JsonRepresentation jsonRep = null;
		CollectionDo collectionDoObj;
	    String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_V2_COLLLECTION, collectionId);
	    JSONObject classPageJsonObject=new JSONObject();
		JSONObject collectionTypeJsonObject=new JSONObject();
		try{
			JSONArray taxonomySet = new JSONArray();
			int size=skillsData.size();
			if(size>0){
				for (Map.Entry<Long, String> entry : skillsData.entrySet()){
					JSONObject code = new JSONObject();
					code.put("codeId", entry.getKey());
					taxonomySet.put(code);
				}
			}
			collectionTypeJsonObject.put("taxonomySet", taxonomySet);
			if(action!=null){
				collectionTypeJsonObject.put("action", action);
			}
			classPageJsonObject.put("collection", collectionTypeJsonObject);
		}catch(Exception e){
			logger.error("Exception::", e);
		}
	 	getLogger().info("updateCollection 21 skills::API:"+url);
	 	getLogger().info("data passed 21 skills:::"+classPageJsonObject.toString());
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), classPageJsonObject.toString());
	    jsonRep = jsonResponseRep.getJsonRepresentation();
	    if(jsonResponseRep.getStatusCode()==200){
			collectionDoObj = deserializeCollection(jsonRep);
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}else{
			collectionDoObj=new CollectionDo();
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}
	    return collectionDoObj;
	}

	@Override
	public CollectionDo updateCollectionSettingForComments(String collectionId, String title, String description, String grade, String sharing, String vocabulary, String taxonomyCode, String updateTaxonomyByCode, String mediaType,String action,String comments) {
		JsonRepresentation jsonRep = null;
		CollectionDo collectionDoObj;
		CollectionDo collectionDoInputObj= new CollectionDo();
	    String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_V2_COLLLECTION, collectionId);
		String form = "";

		try{
			if(title!=null){
				collectionDoInputObj.setTitle(title);
			}
			if(description != null){
				collectionDoInputObj.setGoals(description);
			}
			if(grade!=null){
				collectionDoInputObj.setGrade(grade);
			}
			if(sharing!=null){
				collectionDoInputObj.setSharing(sharing);
			}
			if (taxonomyCode != null) {
				Set<CodeDo> codeDo=new HashSet<>();
				CodeDo codeDoObj=new CodeDo();
				codeDoObj.setCodeId(Integer.parseInt(taxonomyCode));
				codeDo.add(codeDoObj);
				collectionDoInputObj.setTaxonomySet(codeDo);
			}
			if(mediaType!=null){
				collectionDoInputObj.setMediaType(mediaType);
			}
			if(action!=null){
				collectionDoInputObj.setAction(action);
			}
			if(comments!=null)
			{

			CollectionSettingsDo collSetting = new CollectionSettingsDo();
			collSetting.setComment(comments);
			collectionDoInputObj.setSettings(collSetting);
			}

			form = ResourceFormFactory.generateStringDataForm(collectionDoInputObj, "collection");


		}catch(Exception e){
			logger.error("Exception::", e);
		}
	    JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), form);
	    jsonRep = jsonResponseRep.getJsonRepresentation();
	    if(jsonResponseRep.getStatusCode()==200){
			collectionDoObj = deserializeCollection(jsonRep);
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}else{
			collectionDoObj=new CollectionDo();
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}
	    return collectionDoObj;
	}

	@Override
	public CollectionItemDo updateCollectionItemMetadata(String collectionItemId, String narration, String narrationType, String start, String stop) {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_COLLLECTION_ITEM_METADATA, collectionItemId);
		getLogger().info("updateNarrationMetadata url put call111:::::"+url);
		getLogger().info("update narration form data111::::"+ResourceFormFactory.updateCollectionItem(narration, narrationType, start, stop));
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), ResourceFormFactory.updateCollectionItem(narration, narrationType, start, stop));
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeCollectionItem(jsonRep);

	}

	@Override
	public String getYoutubeDuration(String videoId) throws GwtException {
		JsonRepresentation jsonRep = null;
		String youtubeDuration = "0";

		Map<String, String> params = new LinkedHashMap<>();
		params.put(GooruConstants.ID, videoId);
		params.put(GooruConstants.YOUTUBE_KEY, getYoutubeApiKey());
		params.put(GooruConstants.YOUTUBE_PART, YOUTUBE_PART_DETAILS);
		logger.info("getYouTubeApiUrl() here---------------"+getYouTubeApiUrl());
		String url=AddQueryParameter.constructQueryParams(getYouTubeApiUrl(), params);

		logger.info("getYoutubeDuration-----url------"+url);
		try {
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url);
			jsonRep = jsonResponseRep.getJsonRepresentation();
			if(jsonRep != null && jsonRep.getSize() != -1){
				return resourceDeserializer.serializeYoutubeInfo(jsonRep);
			}
		} catch(Exception e) {
			logger.error("Exception::", e);
		}

		return youtubeDuration;
	}

        @Override
	public CollectionItemDo addNewResource(String gooruOid, String idStr,
			String urlStr, String titleStr, String descriptionStr,
			String categoryStr, String thumbnailImgSrcStr, Integer endTime,String edcuationalUse,String momentsOfLearning,List<CodeDo> standards,List<StandardFo> centurySkills,String hostName, List<String> tagList,Map<String,List<Integer>> hazardsAndMediaMap,String mediaType) throws GwtException {

			categoryStr = categoryStr.trim();
			NewResourceDo newResourceDo = new NewResourceDo();
			newResourceDo.setId(idStr);
			newResourceDo.setUrl(URLDecoder.decode(urlStr));
			newResourceDo.setTitle(titleStr);
			newResourceDo.setAccessHazardIds(hazardsAndMediaMap.get("hazard"));
			newResourceDo.setMediaFeatureIds(hazardsAndMediaMap.get("media"));
			if(standards!=null && standards.size()>0 ){
			Set<CodeDo> standardsDo=new HashSet<CodeDo>();
			List<Integer> standardIds=new ArrayList<Integer>();
			for(CodeDo item:standards)
			{
				standardIds.add(item.getCodeId());
			}
			newResourceDo.setStandardIds(standardIds);
			}
			newResourceDo.setDescription(descriptionStr);
			newResourceDo.setCategory(categoryStr);
			newResourceDo.setStop(endTime);

			List<Integer> educationalUseList=new ArrayList<Integer>();
			if(edcuationalUse!=null&&!edcuationalUse.equalsIgnoreCase("")){
				educationalUseList.add(Integer.parseInt(edcuationalUse));
			}

			newResourceDo.setEducationalUseIds(educationalUseList);
			List<Integer> momentoflearningList=new ArrayList<Integer>();
			if(momentsOfLearning!=null&&!momentsOfLearning.equalsIgnoreCase("")){
				momentoflearningList.add(Integer.parseInt(momentsOfLearning));
				newResourceDo.setMomentsOfLearningIds(momentoflearningList);
			}

			List<Integer> centurySkillsList=new ArrayList<Integer>();
			if(centurySkills!=null){
				for(StandardFo fo:centurySkills){
					centurySkillsList.add(fo.getCodeId());
				}
			}

			newResourceDo.setSkillIds(centurySkillsList);
			newResourceDo.setMediaType(mediaType);
			ResourceFormatDo resourceFormat = new ResourceFormatDo();
			resourceFormat.setValue(categoryStr);

			newResourceDo.setResourceFormat(resourceFormat);

			if (thumbnailImgSrcStr==null){
				thumbnailImgSrcStr="";
			}

			if (urlStr!=null&&urlStr.indexOf("youtube") > 0){
				newResourceDo.setThumbnail("");
			}else{
				newResourceDo.setThumbnail(thumbnailImgSrcStr);
			}
			if(hostName!=null){
				ArrayList<String> hostArray= new ArrayList<String>();
				hostArray.add(GOOGLE_DRIVE);
				newResourceDo.setHost(hostArray);
			}
			Map<String,Object> resourceMap=new HashMap<String,Object>();
			resourceMap.put(RESOURCE, newResourceDo);


			JsonRepresentation jsonRep = null,jsonResponseRepget=null;
			String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.ADD_NEW_RESOURCE, idStr);
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put(GooruConstants.TITLE, URLEncoder.encode(titleStr).toString());
			params.put(GooruConstants.URL, urlStr);
			params.put(GooruConstants.CATEGORY, categoryStr);
			params.put(GooruConstants.DESCRIPTION, URLEncoder.encode(descriptionStr).toString());
			params.put(GooruConstants.THUMBNAILIMGSRC, thumbnailImgSrcStr);
			params.put(GooruConstants.STOP, String.valueOf(endTime));
			String url=AddQueryParameter.constructQueryParams(partialUrl, params);

			String form = ResourceFormFactory.generateStringDataForm(resourceMap, null);

			getLogger().info("Add new Web resource --- "+url);
			getLogger().info("Pay load --- "+form);

			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(), form);
			jsonRep = jsonResponseRep.getJsonRepresentation();
			try{
				getLogger().info("response --- "+ jsonRep.getJsonObject().getString("uri").toString());
				String getURL = getRestEndPoint()+jsonRep.getJsonObject().getString("uri").toString();
				JsonResponseRepresentation jsonResponseRep1 = ServiceProcessor.get(getURL, getRestUsername(), getRestPassword());
				jsonResponseRepget=jsonResponseRep1.getJsonRepresentation();
				getLogger().info("getURlresource --- "+getURL);
			}catch(Exception e){

				logger.error("Exception::", e);
			}
			return deserializeCollectionItem(jsonResponseRepget);

	}

	@Override
	public ResourceMetaInfoDo getResourceMetaInfo(String url) throws GwtException {

		JsonRepresentation jsonRep = null;
		String partialUrlStr = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_RESOURCE_INFO);
		Map<String, String> params = new HashMap<>();
		params.put(GooruConstants.URL, url);
		params.put(GooruConstants.TITLE, GooruConstants.NOTHING);
		params.put(GooruConstants.FETCHTHUMBNAIL, GooruConstants.TRUE);
		String urlStr=AddQueryParameter.constructQueryParams(partialUrlStr, params);

		getLogger().info("GET_RESOURCE_INFO get API call::::::"+urlStr);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(urlStr);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeResourceMetaInfo(jsonRep);
	}

	@Override
	public ExistsResourceDo checkResourceExists(String url) throws GwtException {
		try {
			url = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			logger.error("Exception::", ex);
		}

		JsonRepresentation jsonRep = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.CHECK_RESOURCE_EXISTS, url);
		String urlStr = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.CHECK_SHORTENED_URL, GooruConstants.TRUE);
		getLogger().info("--- checkResourceExists --  "+urlStr);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(urlStr);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeResourceItem(jsonRep);
	}

	public ExistsResourceDo deserializeResourceItem(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				if(!jsonRep.getJsonObject().isNull("resource")){
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONObject("resource").toString(), ExistsResourceDo.class);
				}
				} catch (JSONException e) {
					logger.error("Exception::", e);
			}
		}
		return new ExistsResourceDo();
	}

	public ResourceDo deserializeResourceDoItem(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), ResourceDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return null;
	}


	@Override
	public CollectionItemDo addQuestionResource(String collectionId, String mediafileName, CollectionQuestionItemDo collectionQuestionItemDo) throws GwtException {
		JsonRepresentation jsonRep = null,jsonRepGet=null;

		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_ADD_QUESTION_ITEM, collectionId);
		CollectionAddQuestionItemDo collectionAddQuestionItemDo=new CollectionAddQuestionItemDo();
		collectionQuestionItemDo.setMediaFilename(mediafileName);
		collectionAddQuestionItemDo.setQuestion(collectionQuestionItemDo);
		//collectionAddQuestionItemDo.setMediaFileName(mediafileName);
		String collectionQuestionData=ResourceFormFactory.generateStringDataForm(collectionAddQuestionItemDo, null);
		getLogger().info("addQuestionResource --- "+ url);
		getLogger().info("addQuestionResource data --- "+ collectionQuestionData);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(), collectionQuestionData);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		try{
			getLogger().info("response --- "+ jsonRep.getJsonObject().getString("uri"));
			String getURL = getRestEndPoint()+jsonRep.getJsonObject().getString("uri");
			JsonResponseRepresentation jsonResponseRep1 = ServiceProcessor.get(getURL, getRestUsername(), getRestPassword());
			jsonRepGet=jsonResponseRep1.getJsonRepresentation();
			getLogger().info("getURlresource --- "+getURL);
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return deserializeCollectionItem(jsonRepGet);
	}


	@Override
	public CollectionItemDo updateResourceInfo(CollectionItemDo collectionItemDo,List<String> tagList)
			throws GwtException {
		try{

			JsonRepresentation jsonRep = null,jsonRepGet=null;
			String url =null;
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_RESOURCE_INFO,collectionItemDo.getParentGooruOid(), collectionItemDo.getCollectionItemId());

			NewResourceDo newResourceDo = new NewResourceDo();

			String urlStr=collectionItemDo.getResource().getUrl();

			newResourceDo.setTitle(collectionItemDo.getResource().getTitle());
			newResourceDo.setDescription(collectionItemDo.getResource().getDescription());
			newResourceDo.setCategory(collectionItemDo.getResource().getCategory().trim());

			ResourceFormatDo resourceFormat = new ResourceFormatDo();
			resourceFormat.setValue(collectionItemDo.getResource().getCategory());

			String thumbnailImgSrcStr=collectionItemDo.getResource().getThumbnails()!=null?collectionItemDo.getResource().getThumbnails().getUrl():"";
			if (thumbnailImgSrcStr==null){
				thumbnailImgSrcStr="";
			}

			if (urlStr != null && urlStr.contains("youtube")){
				newResourceDo.setThumbnail("");
			}else{
				newResourceDo.setThumbnail(thumbnailImgSrcStr);
			}

			newResourceDo.setResourceFormat(resourceFormat);
			List<Integer> educationListId=new ArrayList<>();
			if(collectionItemDo.getEducationalUse()!=null){
				for(checkboxSelectedDo checkboxSelectedDo:collectionItemDo.getEducationalUse()){
					educationListId.add(checkboxSelectedDo.getId());
				}
			}

			newResourceDo.setEducationalUseIds(educationListId);
			//	newResourceDo.setEducationalUse(collectionItemDo.getEducationalUse());
			newResourceDo.setTaxonomySet(collectionItemDo.getResource().getTaxonomySet());
			List<Integer> momentOfLearningIdList=new ArrayList<>();
			if(collectionItemDo.getMomentsOfLearning()!=null){
				for(checkboxSelectedDo checkboxSelectedDo:collectionItemDo.getMomentsOfLearning()){
					momentOfLearningIdList.add(checkboxSelectedDo.getId());
				}
			}
			List<Integer> skills=new ArrayList<Integer>();
			List<StandardFo> standardFos=collectionItemDo.getSkills();
			if(standardFos!=null){
				for(StandardFo standardFo:standardFos){
					skills.add(standardFo.getId());
				}
			}
			newResourceDo.setSkillIds(skills);

			newResourceDo.setMomentsOfLearningIds(momentOfLearningIdList);
			//newResourceDo.setMomentsOfLearning(collectionItemDo.getMomentsOfLearning());
			List<Integer> accessHardList=new ArrayList<>();
			if(collectionItemDo.getAccessHazard()!=null){
				for(checkboxSelectedDo selectedDo:collectionItemDo.getAccessHazard()){
					accessHardList.add(selectedDo.getId());
				}
			}

			newResourceDo.setAccessHazardIds(accessHardList);
			List<Integer> mediaFeaturesList=new ArrayList<>();
			if(collectionItemDo.getMediaFeature()!=null){
				for(ListValuesDo do1:collectionItemDo.getMediaFeature()){
					mediaFeaturesList.add(do1.getId());
				}
			}
			List<Map<String,String>> standards=collectionItemDo.getStandards();
			List<Integer> standardIds=new ArrayList<>();
			if(standards!=null){
				for(Map<String,String> standard:standards){
					for(Map.Entry<String, String> entry:standard.entrySet()){
						String id=entry.getKey();

						if(id!=null&&id.equalsIgnoreCase("id")){
							int idInt=Integer.parseInt(entry.getValue());
							standardIds.add(idInt);
						}
					}

				}
			}
			newResourceDo.setStandardIds(standardIds);
			newResourceDo.setMediaFeatureIds(mediaFeaturesList);
			newResourceDo.setMediaType(collectionItemDo.getMediaType());
			Map<String,Object> resourceMap=new HashMap<String,Object>();
			resourceMap.put(RESOURCE, newResourceDo);

			if(tagList!=null && !tagList.isEmpty() ){
				resourceMap.put(RESOURCE_TAGS, tagList);
			}

			String form = ResourceFormFactory.generateStringDataForm(resourceMap, null);
			getLogger().info("---- Updating Web url --- "+url);
			getLogger().info("--- pay load -- "+form);
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(),form);
			jsonRep = jsonResponseRep.getJsonRepresentation();

			JsonResponseRepresentation jsonResponseRep1 = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
			getLogger().info("---url -- "+url);
			jsonRepGet=jsonResponseRep1.getJsonRepresentation();
			return deserializeCollectionItem(jsonRepGet);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *
	 * @function deserializeCollectionResourceItem
	 *
	 * @created_date : 11-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param jsonRep
	 * @parm(s) : @return
	 *
	 * @return : CollectionItemDo
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public CollectionItemDo deserializeCollectionResourceItem(JsonRepresentation jsonRep) {
		CollectionItemDo collectionItemDo = new CollectionItemDo();
		UserDo user = null;
		UserDo creator =null;
		ResourceTypeDo resourceType= null;
		ResourceFormatDo resourceFormat = null;
		ThumbnailDo thumbnails = null;
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				String responseString = jsonRep.getJsonObject().toString();
				ResourceDo resourceDo = new ResourceDo();
				Map<String, Object> map = JsonDeserializer.deserialize(responseString, new TypeReference<Map<String, Object>>() {
				});
				if(!jsonRep.getJsonObject().isNull("user")){
				user = JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONObject("user").toString(), UserDo.class);
				}
				if(!jsonRep.getJsonObject().isNull("creator")){
					 creator = JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONObject("creator").toString(), UserDo.class);
				}
				if(!jsonRep.getJsonObject().isNull("resourceType")){
					resourceType = JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONObject("resourceType").toString(), ResourceTypeDo.class);
				}
				if(!jsonRep.getJsonObject().isNull("resourceFormat")){
				 resourceFormat = JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONObject("resourceFormat").toString(), ResourceFormatDo.class);
				}
				if (!jsonRep.getJsonObject().isNull("ratings")){
					SearchRatingsDo ratings = JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONObject("ratings").toString(), SearchRatingsDo.class);
					resourceDo.setRatings(ratings);
				}
				if (!jsonRep.getJsonObject().isNull("depthOfKnowledges")){
					List<checkboxSelectedDo> depthOfKnowledges = JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONArray("depthOfKnowledges").toString(), new TypeReference<ArrayList<checkboxSelectedDo>>() {
					});
					resourceDo.setDepthOfKnowledge(depthOfKnowledges);
				}
				if (!jsonRep.getJsonObject().isNull("educationalUse")){
					ArrayList<checkboxSelectedDo> educationalUse = JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONArray("educationalUse").toString(), new TypeReference<ArrayList<checkboxSelectedDo>>() {
					});
					resourceDo.setEducationalUse(educationalUse);
				}
				if (!jsonRep.getJsonObject().isNull("momentsOfLearning")){
					ArrayList<checkboxSelectedDo> momentsOfLearning = JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONArray("momentsOfLearning").toString(), new TypeReference<ArrayList<checkboxSelectedDo>>() {
					});
					resourceDo.setMomentsOfLearning(momentsOfLearning);
				}
				if (!jsonRep.getJsonObject().isNull("resourceTags")){
					ArrayList<ResourceTagsDo> resourceTags = JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONArray("resourceTags").toString(), new TypeReference<ArrayList<ResourceTagsDo>>() {
					});
					resourceDo.setResourceTags(resourceTags);
				}
				if(!jsonRep.getJsonObject().isNull("thumbnails")){
					thumbnails = JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONObject("thumbnails").toString(), ThumbnailDo.class);
				}
				resourceDo.setCategory(map.get("category") != null ? map.get("category").toString() : null);
				resourceDo.setTitle(map.get("title") != null ? map.get("title").toString() : null);
				resourceDo.setUrl(map.get("url") !=null ? map.get("url").toString() : null);
				resourceDo.setGooruOid(map.get("gooruOid") != null ? map.get("gooruOid").toString() : null);
				resourceDo.setGooruOid(map.get("gooruOid") != null ? map.get("gooruOid").toString() : null);
				resourceDo.setViews(map.get("views") != null ? map.get("views").toString() : null);
				resourceDo.setSharing(map.get("sharing") != null ? map.get("sharing").toString() : null);
				resourceDo.setGoals(map.get("goals") != null ? map.get("goals").toString() : null);
				resourceDo.setDescription(map.get("description") != null ? map.get("description").toString() : null);

				resourceDo.setUser(user);
				resourceDo.setCreator(creator);
				resourceDo.setResourceType(resourceType);
				resourceDo.setResourceFormat(resourceFormat);
				resourceDo.setThumbnails(thumbnails);

				collectionItemDo.setItemSequence(map.get("itemSequence") != null ? Integer.parseInt(map.get("itemSequence").toString()) : 1 );
				collectionItemDo.setResource(resourceDo);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return collectionItemDo;
	}

        @Override
	public void removeQuestionImage(String collectionQuestionId) throws GwtException{
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.REMOVE_QUESTION_IMAGE, collectionQuestionId);
		getLogger().info("REMOVE_QUESTION_IMAGE delete call:::"+url);
		JsonResponseRepresentation jsonResponseRep=ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
	}

	@Override
	public void updateQuestionImage(String collectionItemId, String fileName) throws GwtException {

		JsonRepresentation jsonRep = null;

		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.ATTACH_IMAGE_TO_QUESTION, collectionItemId);
		Map<String, String> params = new HashMap<>();
		params.put(GooruConstants.FILENAMES, fileName);
		params.put(GooruConstants.ASSETKEY, GooruConstants.ASSETQUESTION);
		String url=AddQueryParameter.constructQueryParams(partialUrl, params);

		getLogger().info("ATTACH_IMAGE_TO_QUESTION updateQuestionImage post call:::::"+url);
		JsonResponseRepresentation jsonResponseRep=ServiceProcessor.post(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
	}
	@Override
	public CollectionItemDo copyCollectionItem(String collectionId, String resourceId) {
		JsonRepresentation jsonRep = null;
		String url = null;
		url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_COPY_COLLLECTION_ITEM, resourceId, collectionId);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(),new Form());

		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeCollectionItem(jsonRep);
	}

	@Override
	public List<CollectionDo> getUserCollectionList(Integer pageSize,
			Integer pageNum, boolean isSharable) throws GwtException {
		String pageNum1 =Integer.toString(pageNum);
		String pageSize1 = Integer.toString(pageSize);
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		Map<String, String> params = new HashMap<>();
		if (isSharable){
			partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.SHARABLE_USER_COLLECTION, JSON);
			params.put(GooruConstants.SHARING, GooruConstants.ACESSTEXT);
			getLogger().info("SHARABLE_USER_COLLECTION is sherable getUserCollectionList API call::::......"+partialUrl);
		}else{
			partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.USER_COLLECTION, JSON);
			getLogger().info("SHARABLE_USER_COLLECTION getUserCollectionList API call::::...."+partialUrl);
		}
		params.put(GooruConstants.PAGE_SIZE, pageSize1);
		params.put(GooruConstants.PAGE_NUM, pageNum1);
		params.put(GooruConstants.FILTERBY, GooruConstants.COLLECTION);
		params.put(GooruConstants.MERGE, GooruConstants.PERMISSIONS);

		String url=AddQueryParameter.constructQueryParams(partialUrl, params);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeCollections(jsonRep);

	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.service.ResourceService#getPermissions(java.lang.String)
	 */
	@Override
	public MetaDO getPermissions(String collectionId)throws GwtException {
		String url=null;
		JsonRepresentation jsonRep = null;
		url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.PERMISSION_COLLECTION, collectionId);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializePermissions(jsonRep);
	}

	@Override
	public String checkShortenUrl(String shortenUrl) throws GwtException {
		JsonRepresentation jsonRep = null;
		try {
			shortenUrl = URLEncoder.encode(shortenUrl, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			logger.error("Exception::", ex);
		}

		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.CHECK_SHORTEN_URL,shortenUrl);
		getLogger().info("CHECK_SHORTEN_URL get call::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		boolean isShortenUrl=false;
		try {
			if(!jsonRep.getJsonObject().isNull("shortenedUrlStatus")){
				isShortenUrl=jsonRep.getJsonObject().getBoolean("shortenedUrlStatus");
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return isShortenUrl ? "true" : "false";
	}

	public CollectionDo getCollection(String collectionGooruOid){
		JsonRepresentation jsonRep = null;
		CollectionDo collectionDoObj;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_COLLECTION, collectionGooruOid, getGuestSessionToken(""));
		Map<String, String> params = new LinkedHashMap<>();
		params.put(GooruConstants.SKIP_COLL_ITEM, GooruConstants.TRUE);
		params.put(GooruConstants.INCLUDE_META_INFO,GooruConstants.TRUE);
		params.put(GooruConstants.MERGE, GooruConstants.PERMISSIONS);
		params.put(GooruConstants.INCLUDE_CONTENT_PROVDER, GooruConstants.FALSE);
		params.put(GooruConstants.INCLUDE_CUSTOM_FIELDS,GooruConstants.FALSE);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("getCollection::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			collectionDoObj = deserializeCollection(jsonRep);
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}else{
			collectionDoObj=new CollectionDo();
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}
		try {

			if(collectionDoObj.getLanguageObjective() != null)
			{
				collectionDoObj.setLanguageObjective(URLDecoder.decode(collectionDoObj.getLanguageObjective(), "UTF-8"));
			}
			if(collectionDoObj.getKeyPoints() != null)
			{
				collectionDoObj.setKeyPoints(URLDecoder.decode(collectionDoObj.getKeyPoints(), "UTF-8"));
			}

        } catch (UnsupportedEncodingException e) {
        	logger.error("Exception::", e);
        }

		return collectionDoObj;
	}

	public CollectionDo getCollectionFromEmbed(String collectionGooruOid, String restEndPointFromEmbed){
		JsonRepresentation jsonRep = null;
		CollectionDo collectionDoObj;
		String partialUrl = UrlGenerator.generateUrl(restEndPointFromEmbed, UrlToken.V2_GET_COLLECTION, collectionGooruOid, getGuestSessionToken(restEndPointFromEmbed));
		Map<String, String> params = new LinkedHashMap<>();
		params.put(GooruConstants.SKIP_COLL_ITEM,GooruConstants.TRUE);
		params.put(GooruConstants.INCLUDE_META_INFO,GooruConstants.TRUE);
		params.put(GooruConstants.MERGE, GooruConstants.PERMISSIONS);
		params.put(GooruConstants.INCLUDE_CONTENT_PROVDER, GooruConstants.FALSE);
		params.put(GooruConstants.INCLUDE_CUSTOM_FIELDS,GooruConstants.FALSE);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("--getCollectionFromEmbed --  "+url);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			collectionDoObj = deserializeCollection(jsonRep);
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}else{
			collectionDoObj=new CollectionDo();
			collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
		}

		try {

			if(collectionDoObj.getLanguageObjective() != null)
			{
				collectionDoObj.setLanguageObjective(URLDecoder.decode(collectionDoObj.getLanguageObjective(), "UTF-8"));
			}
			if(collectionDoObj.getKeyPoints() != null)
			{
				collectionDoObj.setKeyPoints(URLDecoder.decode(collectionDoObj.getKeyPoints(), "UTF-8"));
			}

        } catch (UnsupportedEncodingException e) {
        	logger.error("Exception::", e);
        }

		return collectionDoObj;
	}

	@Override
	public CollectionItemDo addNewUserResource(	String jsonString,String gooruOid)throws GwtException {
		JsonRepresentation jsonRep = null,jsonResponseRepget=null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_ADD_NEW_USER_RESOURCE, gooruOid);

		getLogger().info("Add user own resource -- "+url);
		getLogger().info("--- Payload -- "+jsonString);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(), jsonString);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		try{
			logger.info("copy collection v3 uri here:::::::"+jsonRep.getJsonObject().getString("uri"));
			String getURL= getRestEndPoint()+jsonRep.getJsonObject().getString("uri");
			JsonResponseRepresentation	jsonResponseRepresentation1=ServiceProcessor.get(getURL,getRestUsername(),getRestPassword());
			jsonResponseRepget=jsonResponseRepresentation1.getJsonRepresentation();
		}catch(Exception e){
			logger.error("Exception-------",e);
		}
		return deserializeCollectionItem(jsonResponseRepget);
	}

	@Override
	public MediaUploadDo saveUserOwnResource(String fileName) throws GwtException {
		MediaUploadDo mediaUploadDo = null;
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_USER_RESOURCE_MEDIA_FILE_SAVE);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(),	getRestPassword(),fileName);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if (jsonRep != null) {
		mediaUploadDo = JsonDeserializer.deserialize(jsonRep.toString(), MediaUploadDo.class);
		  }
		return mediaUploadDo;
	}

	public CollectionItemDo convertResourceToCollectionItemDo(ResourceDo resourceDo,CollectionItemDo collectionItemDo){

		collectionItemDo.setResource(resourceDo);// replacing the update question details Do
		collectionItemDo.setCollectionQuestionItemDo(null);// removing existing collection item object details.

		return collectionItemDo;
	}

	@Override
	public CollectionItemDo updateUserOwnResource(String jsonString,String gooruOid,String collectionId) throws GwtException {
		JsonRepresentation jsonRep = null, jsonResponseRepget=null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_UPDATE_USER_RESOURCE, collectionId, gooruOid);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), jsonString);

		getLogger().info("---- Updating User own resource -- "+url);
		getLogger().info("--- payload -- "+jsonString);

		jsonRep = jsonResponseRep.getJsonRepresentation();
		try{
		/*	logger.info("updateUserOwnResource v3 uri here:::::::"+jsonRep.getJsonObject().getString("uri"));
			String getURL= getRestEndPoint()+jsonRep.getJsonObject().getString("uri");
		*/	JsonResponseRepresentation	jsonResponseRepresentation1=ServiceProcessor.get(url,getRestUsername(),getRestPassword());
			jsonResponseRepget=jsonResponseRepresentation1.getJsonRepresentation();
		}catch(Exception e){
			logger.error("Exception-------",e);
		}
		return deserializeCollectionItem(jsonResponseRepget);
	}

	@Override
	public CollectionItemDo updateNarrationMetadata(String collectionItemId,
			String narration, String narrationType) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_UPDATE_COLLLECTION_ITEM_METADATA, collectionItemId);
		getLogger().info("updateNarrationMetadata url put call:::::"+url);
		JSONObject collectionItemObject = new JSONObject();
		JSONObject narrationObject = new JSONObject();
		try {
			narrationObject.put("itemType", "added");
			if(narration!=null){
				narrationObject.put("narration", narration);
			}
			collectionItemObject.put("collectionItem", narrationObject);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		getLogger().info("data url put call:::::"+collectionItemObject.toString());
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), collectionItemObject.toString());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		getLogger().info("response:::::"+jsonResponseRep.getStatusCode());
		return deserializeCollectionItem(jsonRep);
	}

	@Override
	public CollectionItemDo updateNarrationItemMetadata(String collectionId,String collectionItemId,
			String narration, String narrationType,String start,String stop) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_UPDATE_COLLLECTION_ITEM_METADATA,collectionId,collectionItemId);
		getLogger().info("updateNarrationMetadata url put call:::::"+url);
		JSONObject narrationObject = new JSONObject();
		try {
			if(start!=null){
				narrationObject.put("start", start);
			}
			if(stop!=null){
				narrationObject.put("stop", stop);
			}
			if(narration!=null){
				narrationObject.put("narration", narration);
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		getLogger().info("data url put call:::::"+narrationObject.toString());
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), narrationObject.toString());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		getLogger().info("response:::::"+jsonResponseRep.getStatusCode());
		return deserializeCollectionItem(jsonRep);
	}


	@Override
	public void createContentReport(String assocGooruOid, String targetValue, String typesvalue1,
			String typesvalue2, String typesvalue3, String typesvalue4,
			String otherDescription) {
//		new CreateContentReportController().createCollectionContentreport(getRestEndPoint(), getLoggedInSessionToken(), assocGooruOid, targetValue,typesvalue1,typesvalue2,typesvalue3, typesvalue4,otherDescription);
		String url=UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.CREATE_CONTENT_REPORT, getLoggedInSessionToken(), assocGooruOid, targetValue,typesvalue1,typesvalue2,typesvalue3, typesvalue4,otherDescription);
		JSONObject createtargetObject=new JSONObject();
		JSONObject targetObject=new JSONObject();
		JSONArray typesArray=new JSONArray();
		JSONObject typesObject1=new JSONObject();
		JSONObject typesObject2=new JSONObject();
		JSONObject typesObject3=new JSONObject();
		JSONObject typesObject4=new JSONObject();
		String responseData = "";
		try {

			createtargetObject.put("assocGooruOid", assocGooruOid);
			targetObject.put("value", targetValue);
			createtargetObject.put("target", targetObject);
			if(!typesvalue1.equalsIgnoreCase("")){
				typesObject1.put("value", typesvalue1);
				typesArray.put(typesObject1);
			}
			if(!typesvalue2.equalsIgnoreCase("")){
				typesObject2.put("value", typesvalue2);
				typesArray.put(typesObject2);
			}
			if(!typesvalue3.equalsIgnoreCase("")){
				typesObject3.put("value", typesvalue3);
				typesArray.put(typesObject3);
			}
			if(!typesvalue4.equalsIgnoreCase("")){
				typesObject4.put("value", typesvalue4);
				typesArray.put(typesObject4);
			}
			createtargetObject.put("types", typesArray);
			if(!otherDescription.equalsIgnoreCase("")){
				createtargetObject.put("freeText", otherDescription);
			}
			JsonRepresentation jsonRep = null;
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(), createtargetObject.toString());
			jsonRep = jsonResponseRep.getJsonRepresentation();


			try {
				responseData=jsonRep.getText();
			} catch (IOException e) {
				getLogger().error("Error while creating Content Report (IO) : "+e.toString());
			}

       } catch (JSONException e) {
    	   getLogger().error("Error while creating Get Content Report (JSON) : "+e.toString());
       }

	}

	@Override
	public String updateReport(String gooruOid, String freeText) {
//		new CreateContentReportController().updateReport(getRestEndPoint(), getLoggedInSessionToken(), gooruOid, freeText);
		String response=null;
		String url=UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_CONTENT_REPORT, gooruOid, getLoggedInSessionToken());
		try {
			JSONObject updateReportObject=new JSONObject();
			updateReportObject.put("freeText", freeText);

			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), updateReportObject.toString());
			JsonRepresentation jsonRep = jsonResponseRep.getJsonRepresentation();

			response=jsonRep.getText();
		} catch (JSONException e) {
			getLogger().error("Error while Updating Content Report (JSON) : "+e.toString());
		} catch (IOException e) {
			getLogger().error("Error while Updating Content Report (IO) : "+e.toString());
		}

		return response;
	}

	@Override
	public GetFlagContentDO getContentReport(String assocGooruOid) {
		JsonRepresentation jsonRep = null;
		String url= UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_CONTENT_REPORT, assocGooruOid,getLoggedInSessionToken());

	    JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeContentReport(jsonRep);
	}

    private GetFlagContentDO deserializeContentReport(JsonRepresentation jsonRep) {
        GetFlagContentDO getFlagContentDO = null;
        String response;

        String type = "";
        String gooruOid = "";
        String loginUserId = "";
        ArrayList<String> getTypeList = new ArrayList<>();
        ArrayList<String> getGooruId = new ArrayList<>();
        try {
            if (jsonRep == null) {
                getLogger().error("GetFlagContentDO: jsonRep received as null");
                return getFlagContentDO;
            }
            response = jsonRep.getText();
            if (response != null && !"[]".equals(response)) {
                JSONArray getFormattingArray = new JSONArray(response);
                for (int i = 0; i < getFormattingArray.length(); i++) {
                    JSONObject typeObj = getFormattingArray.getJSONObject(i);
                    type = typeObj.getString("type");
                    gooruOid = typeObj.getString("gooruOid");
                    getGooruId.add(gooruOid);
                    getFlagContentDO = new GetFlagContentDO();
                    getFlagContentDO.setGetAsscociatedId(getGooruId);
                    getTypeList.add(type);
                    getFlagContentDO.setGetTypeList(getTypeList);
                    loginUserId = typeObj.getString("creator");
                    getFlagContentDO.setUserId(loginUserId);
                }
            }
        } catch (JSONException e) {
            getLogger().error("Error while deserializing Get Content Report (JSON) : " + e.toString());
        } catch (IOException e1) {
            getLogger().error("Error while deserializing Get Content Report (IO) : " + e1.toString());
        }
        return getFlagContentDO;
    }

	@Override
	public String deleteContentReport(String gooruOid) {
		String url=UrlGenerator.generateUrl(getRestEndPoint(),getLoggedInSessionToken(),gooruOid);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.delete(url, getRestUsername(),getRestPassword());
		String response="";
		try {
			response = jsonResponseRep.getJsonRepresentation().getText();
		} catch (IOException e) {
			getLogger().error("Error while deserializing Content Report (IO) : "+e.toString());
		}
		return response;
	}


	/**
	 *
	 * @param parms
	 */
	@Override
	public Boolean checkProfanity(Map<String, String> parms){
		boolean value=false;
		JsonRepresentation jsonRep = null;
		ProfanityDo profanityDo = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.PROFANITY_CHECK);
		String formData = ResourceFormFactory.generateStringDataForm(parms, null);
		formData = formData.replaceAll("&", "%26").replaceAll("#", "%23");
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(), formData);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				profanityDo =  JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), ProfanityDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		if (profanityDo!=null){
			value = profanityDo.isFound();
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.service.ResourceService#checkProfanityForList(java.util.ArrayList)
	 */
	@Override
	public List<ProfanityCheckDo> checkProfanityForList(
			List<ProfanityCheckDo> profanityList) {
		Map<String, String> parms = new HashMap<>();
		if(profanityList!=null && profanityList.size()>0){
                    for (ProfanityCheckDo profanityListItem : profanityList) {
                        parms.put("text", profanityListItem.getQuestionText());
                        profanityListItem.setQuestionValue(checkProfanityForLsit(parms));
                    }
		}
		return profanityList;
	}
	public Boolean checkProfanityForLsit(Map<String, String> parms){
		boolean value=false;
		JsonRepresentation jsonRep = null;
		ProfanityDo profanityDo = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.PROFANITY_CHECK);

		String formData = ResourceFormFactory.generateStringDataForm(parms, null);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(), formData);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				profanityDo =  JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), ProfanityDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		if (profanityDo!=null){
			value = profanityDo.isFound();
		}
		return value;
	}

	@Override
	public FolderListDo getFolderWorkspace(int offset, int limit,String sharingType, String collectionType,boolean isExcludeAssessment) throws GwtException {
		String offsetSize =Integer.toString(offset);
		String limitSize = Integer.toString(limit);
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		Map<String, String> params = new LinkedHashMap<>();
		if(GooruConstants.COURSE.equalsIgnoreCase(collectionType)){
			partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_USER_COURSES_LIST,getLoggedInUserUid());
		}else{
			partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_WORKSPACE_FOLDER_LIST);
			params.put(GooruConstants.ORDER_BY, GooruConstants.SEQUENCE);
		}
		params.put(GooruConstants.OFFSET, offsetSize);
		if(sharingType!=null){
			params.put(GooruConstants.SHARING, sharingType);
		}
		if(collectionType!=null && !GooruConstants.COURSE.equalsIgnoreCase(collectionType)){
			params.put(GooruConstants.COLLECTION_TYPE, collectionType);
		}
		params.put(GooruConstants.LIMIT,limitSize);

		if(isExcludeAssessment){
			params.put(GooruConstants.EXCLUDE_TYPE, "assessment/url");
		}
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);

		getLogger().info("---- getFolderWorkspace ---  "+url);
		getLogger().info("---- collectionType ---  "+collectionType);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		getLogger().info("---- jsonRep ---  "+jsonRep);
		getLogger().info("---- deserialise ---  "+deserializeWorkspaceFolderList(jsonRep,collectionType));
		return deserializeWorkspaceFolderList(jsonRep,collectionType);
	}

	public FolderListDo deserializeWorkspaceFolderList(JsonRepresentation jsonRep,String collectionType) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				if(!GooruConstants.COURSE.equalsIgnoreCase(collectionType)){
					return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), new TypeReference<FolderListDo>() {});
				}else{
					FolderListDo listObj=new FolderListDo();
					listObj.setSearchResult((ArrayList<FolderDo>) JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(),new TypeReference<List<FolderDo>>() {}));
					return listObj;
				}
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return new FolderListDo();
	}

	@Override
	public CollectionDo updateCollectionInfo(CollectionDo collectionDo, String teacherTips) throws GwtException {
		CollectionDo collectionDoObj = new CollectionDo();
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_V2_COLLLECTION, collectionDo.getGooruOid());
		if(ResourceFormFactory.updateCollectionInfo(collectionDo.getTitle(), teacherTips).getValuesArray("data").length>0)
		{
			if(teacherTips != null)
			{
				try {
					teacherTips = URLEncoder.encode(teacherTips, "UTF-8");

	            } catch (UnsupportedEncodingException e) {
	            	logger.error("Exception::", e);
	            }
			}

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), ResourceFormFactory.updateCollectionInfo(collectionDo.getTitle(), teacherTips).getValuesArray("data")[0]);
		jsonRep = jsonResponseRep.getJsonRepresentation();

		collectionDoObj = deserializeCollection(jsonRep);

		}
		try {

			if(collectionDoObj.getLanguageObjective() != null)
			{
				collectionDoObj.setLanguageObjective(URLDecoder.decode(collectionDoObj.getLanguageObjective(), "UTF-8"));
			}
			if(collectionDoObj.getKeyPoints() != null)
			{
				collectionDoObj.setKeyPoints(URLDecoder.decode(collectionDoObj.getKeyPoints(), "UTF-8"));
			}


        } catch (UnsupportedEncodingException e) {
        	logger.error("Exception::", e);
        }
		return collectionDoObj;

	}

	@Override
	public CollectionDo updateCollectionLanguageObjective(CollectionDo collectionDo, String languageObjective) throws GwtException {
		JsonRepresentation jsonRep = null;
		CollectionDo collectionObjectDo = new CollectionDo();
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_V2_COLLLECTION, collectionDo.getGooruOid());
		if(ResourceFormFactory.updateCollectionLanguageObjective(collectionDo.getTitle(), languageObjective).getValuesArray("data").length>0)
		{
			if(languageObjective != null)
			{
				try {
					languageObjective = URLEncoder.encode(languageObjective, "UTF-8");

	            } catch (UnsupportedEncodingException e) {
	            	logger.error("Exception::", e);
	            }
			}

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), ResourceFormFactory.updateCollectionLanguageObjective(collectionDo.getTitle(), languageObjective).getValuesArray("data")[0]);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		}
		collectionObjectDo = deserializeCollection(jsonRep);
		try
		{

			if(collectionObjectDo.getLanguageObjective() != null)
			{
				String correctDecoded = URLDecoder.decode(collectionObjectDo.getLanguageObjective(), "UTF-8");
				collectionObjectDo.setLanguageObjective(correctDecoded);
			}
			if(collectionObjectDo.getKeyPoints() != null)
			{
				collectionObjectDo.setKeyPoints(URLDecoder.decode(collectionObjectDo.getKeyPoints(), "UTF-8"));
			}

		  } catch (UnsupportedEncodingException e) {
			  logger.error("Exception::", e);
          }
		return collectionObjectDo;

	}

	@Override
	public CollectionDo updateCollectionInstructionalMethod(CollectionDo collectionDo, String instructionalMethod, Boolean selectedVal) throws GwtException {
		JsonRepresentation jsonRep = null;
		CollectionDo collectionObjectDo = new CollectionDo();
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_V2_COLLLECTION, collectionDo.getGooruOid());
		if(ResourceFormFactory.updateCollectionInstructionalMethod(collectionDo.getTitle(), instructionalMethod,selectedVal).getValuesArray("data").length>0)
		{
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), ResourceFormFactory.updateCollectionInstructionalMethod(collectionDo.getTitle(), instructionalMethod,selectedVal).getValuesArray("data")[0]);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		}
		collectionObjectDo = deserializeCollection(jsonRep);
		try
		{

			if(collectionObjectDo.getLanguageObjective() != null)
			{
				String correctDecoded = URLDecoder.decode(collectionObjectDo.getLanguageObjective(), "UTF-8");
				collectionObjectDo.setLanguageObjective(correctDecoded);
			}
			if(collectionObjectDo.getKeyPoints() != null)
			{
				collectionObjectDo.setKeyPoints(URLDecoder.decode(collectionObjectDo.getKeyPoints(), "UTF-8"));
			}

		  } catch (UnsupportedEncodingException e) {
			  logger.error("Exception::", e);
          }
		return collectionObjectDo;

	}

	@Override
	public CollectionDo updateCollectionAudience(CollectionDo collectionDo, String audience, Boolean selectedVal) throws GwtException {
		JsonRepresentation jsonRep = null;
		CollectionDo collectionObjectDo;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_V2_COLLLECTION, collectionDo.getGooruOid());
		if(ResourceFormFactory.updateCollectionAudience(collectionDo.getTitle(), audience,selectedVal).getValuesArray("data").length>0)
		{
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), ResourceFormFactory.updateCollectionAudience(collectionDo.getTitle(), audience,selectedVal).getValuesArray("data")[0]);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		}
		collectionObjectDo = deserializeCollection(jsonRep);
		try
		{

			if(collectionObjectDo.getLanguageObjective() != null)
			{
				String correctDecoded = URLDecoder.decode(collectionObjectDo.getLanguageObjective(), "UTF-8");
				collectionObjectDo.setLanguageObjective(correctDecoded);
			}
			if(collectionObjectDo.getKeyPoints() != null)
			{
				collectionObjectDo.setKeyPoints(URLDecoder.decode(collectionObjectDo.getKeyPoints(), "UTF-8"));
			}

		  } catch (UnsupportedEncodingException e) {
			  logger.error("Exception::", e);
          }
		return collectionObjectDo;

	}

	@Override
	public CollectionDo updateCollectionDepthOfKnowledge(CollectionDo collectionDo, String depthOfKnowlwedgevalues, Boolean selectedVal) throws GwtException {
		JsonRepresentation jsonRep = null;
		CollectionDo collectionObjectDo = new CollectionDo();
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_V2_COLLLECTION, collectionDo.getGooruOid());
		if(ResourceFormFactory.updateCollectionDepthOfKnowledge(collectionDo.getTitle(), depthOfKnowlwedgevalues,selectedVal).getValuesArray("data").length>0)
		{
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), ResourceFormFactory.updateCollectionDepthOfKnowledge(collectionDo.getTitle(), depthOfKnowlwedgevalues,selectedVal).getValuesArray("data")[0]);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		}
		try
		{
			collectionObjectDo = deserializeCollection(jsonRep);

			if(collectionObjectDo.getLanguageObjective() != null)
			{
				String correctDecoded = URLDecoder.decode(collectionObjectDo.getLanguageObjective(), "UTF-8");
				collectionObjectDo.setLanguageObjective(correctDecoded);
			}
			if(collectionObjectDo.getKeyPoints() != null)
			{
				collectionObjectDo.setKeyPoints(URLDecoder.decode(collectionObjectDo.getKeyPoints(), "UTF-8"));
			}

		  } catch (UnsupportedEncodingException e) {
			  logger.error("Exception::", e);
          }
		return collectionObjectDo;

	}

	@Override
	public CollectionDo updateCollectionLearningSkills(CollectionDo collectionDo, String learningSkillsvalues, Boolean selectedVal) throws GwtException {
		JsonRepresentation jsonRep = null;
		CollectionDo collectionObjectDo = new CollectionDo();
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_V2_COLLLECTION, collectionDo.getGooruOid());
		if(ResourceFormFactory.updateCollectionLearningSkills(collectionDo.getTitle(), learningSkillsvalues,selectedVal).getValuesArray("data").length>0)
		{
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), ResourceFormFactory.updateCollectionLearningSkills(collectionDo.getTitle(), learningSkillsvalues,selectedVal).getValuesArray("data")[0]);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		}
		try
		{
			collectionObjectDo = deserializeCollection(jsonRep);

			if(collectionObjectDo.getLanguageObjective() != null)
			{
				String correctDecoded = URLDecoder.decode(collectionObjectDo.getLanguageObjective(), "UTF-8");
				collectionObjectDo.setLanguageObjective(correctDecoded);
			}
			if(collectionObjectDo.getKeyPoints() != null)
			{
				collectionObjectDo.setKeyPoints(URLDecoder.decode(collectionObjectDo.getKeyPoints(), "UTF-8"));
			}

		  } catch (UnsupportedEncodingException e) {
			  logger.error("Exception::", e);
          }
		return collectionObjectDo;

	}


	@Override
	public CollectionDo getCollectionInfoV2API(String collectionId) throws GwtException {
		JsonRepresentation jsonRep = null;
		CollectionDo collectionObjectDo = new CollectionDo();
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_V2_COLLLECTION, collectionId);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url);
		jsonRep = jsonResponseRep.getJsonRepresentation();

		try
		{
			collectionObjectDo = deserializeCollection(jsonRep);

			if(collectionObjectDo.getLanguageObjective() != null)
			{
				String correctDecoded = URLDecoder.decode(collectionObjectDo.getLanguageObjective(), "UTF-8");
				collectionObjectDo.setLanguageObjective(correctDecoded);
			}
			if(collectionObjectDo.getKeyPoints() != null)
			{
				collectionObjectDo.setKeyPoints(URLDecoder.decode(collectionObjectDo.getKeyPoints(), "UTF-8"));
			}

		  } catch (UnsupportedEncodingException e) {
			  logger.error("Exception::", e);
          }

		return collectionObjectDo;

	}

	@Override
	public void deleteTaxonomyResource(String resourceId, Integer codeId)
			throws GwtException {
		JsonRepresentation jsonRep = null;

		String url = UrlGenerator.generateUrl(getRestEndPoint(),
				UrlToken.DELETE_TAXONOMY_RESOURCE, resourceId);
		getLogger().info("deleteTaxonomyResource:"+url);
		try {
			JSONObject taxonomyObject = new JSONObject();
			JSONObject taxonomySetObj = new JSONObject();

			JSONArray codeIdJsonArray = new JSONArray();
			codeIdJsonArray.put(new JSONObject().put("codeId", codeId));

			taxonomySetObj.put("taxonomySet", codeIdJsonArray);
			taxonomyObject.put("resource", taxonomySetObj);
			getLogger().info("deleteTaxonomy:"+taxonomyObject.toString());
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor
					.put(url, getRestUsername(), getRestPassword(),
							taxonomyObject.toString());

		} catch (Exception ex) {
			logger.error("Exception::", ex);
		}

	}

	@Override
	public void UpdateResourceTaxonomy(String resourceId,Set<CodeDo> taxonomyObj) throws GwtException {
		JsonRepresentation jsonRep = null;
		if(taxonomyObj!=null){
			for (CodeDo codeDo : taxonomyObj) {
				String url = UrlGenerator.generateUrl(getRestEndPoint(),
						UrlToken.UPDATE_TAXONOMY_RESOURCE, resourceId);

				try {
					JSONObject taxonomyObject = new JSONObject();
					JSONObject taxonomySetObj = new JSONObject();

					JSONArray codeIdJsonArray = new JSONArray();
					codeIdJsonArray.put(new JSONObject().put("codeId", codeDo.getCodeId()));

					taxonomySetObj.put("taxonomySet", codeIdJsonArray);
					taxonomyObject.put("resource", taxonomySetObj);
					JsonResponseRepresentation jsonResponseRep = ServiceProcessor
							.put(url, getRestUsername(), getRestPassword(),
									taxonomyObject.toString());
					jsonRep = jsonResponseRep.getJsonRepresentation();

				} catch (Exception ex) {
					logger.error("Exception::", ex);
				}
			}
		}
	}

	@Override
	public List<ResourceTagsDo> addTagsToResource(String resourceId, String addedTags)
			throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(),
				UrlToken.ADD_TAGS, resourceId);
		getLogger().info("add tags::"+url);
		getLogger().info("add tags::"+ResourceFormFactory.frameTagObject(addedTags).getValuesArray("data")[0]);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(),
				getRestPassword(), ResourceFormFactory.frameTagObject(addedTags).getValuesArray("data")[0]);
		jsonRep =jsonResponseRep.getJsonRepresentation();
		return deserializeResourceTags(jsonRep);
	}

	@Override
	public List<ResourceTagsDo> getTagsToResource(String resourceId)
			throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(),
				UrlToken.GET_TAGS, resourceId);
		getLogger().info("get tags::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(),getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		return deserializeResourceTags(jsonRep);
	}

	public List<ResourceTagsDo> deserializeResourceTags(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonArray()
						.toString(), new TypeReference<List<ResourceTagsDo>>() {
				});

			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new  ArrayList<>();
	}

	@Override
	public void deleteTagsServiceRequest(String resourceId, String addedTags)
			throws GwtException {
		JsonRepresentation jsonRep = null;

		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(),
				UrlToken.DELETE_TAGS, resourceId);
		String url=AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.DATA, addedTags);

		getLogger().info("delete tags::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.delete(url, getRestUsername(),getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
	}

	@Override
	public GoogleDriveDo getGoogleDriveFilesList(String folderId,String nextPageToken) throws UnsupportedEncodingException {
		GoogleDriveDo googleDriveDo=new GoogleDriveDo();
		String contentType="application/json";
		String access_token = getLoggedInAccessToken() != null ? getLoggedInAccessToken() : null;
		String enocodedString="";
		try {
			enocodedString = URLEncoder.encode("(mimeType = 'application/vnd.google-apps.document' or mimeType = 'application/vnd.google-apps.spreadsheet' or mimeType = 'application/vnd.google-apps.folder' or mimeType='application/vnd.google-apps.form' or mimeType='application/vnd.google-apps.presentation' or mimeType='application/vnd.google-apps.drawing')","UTF-8");
			folderId = folderId != null ? folderId : "root";
			enocodedString=enocodedString+URLEncoder.encode(" and '"+folderId+"' in parents and trashed!=true","UTF-8");
			if(nextPageToken!=null){
				enocodedString=enocodedString+"&pageToken="+nextPageToken;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception::", e);
		}
		String url = UrlGenerator.generateUrl(getGoogleRestEndPoint(), UrlToken.GET_GOOGLEDRIVE_FIlES, enocodedString);

		String response=new WebService(url,false).webInvokeforget("GET", "", contentType, access_token);
		if (response!=null){
			googleDriveDo=deserializeGoogleDriveFilesList(response);
		}else{
			googleDriveDo.setError(new ErrorDo());
			googleDriveDo.getError().setCode(401);
		}
		return googleDriveDo;
	}


        @Override
	public GoogleDriveDo updateFileShareToAnyoneWithLink(String driveFileId) throws UnsupportedEncodingException{
		GoogleDriveDo googleDriveDo=new GoogleDriveDo();
		String contentType="application/json";
		String access_token = getLoggedInAccessToken() != null ? getLoggedInAccessToken() : null;
		JSONObject premissonJsonObject=new JSONObject();
		try {
			premissonJsonObject.put("role", "reader");
			premissonJsonObject.put("type", "anyone");
			premissonJsonObject.put("withLink", true);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		String url = UrlGenerator.generateUrl(getGoogleRestEndPoint(), UrlToken.UPDATE_FILE_PERMISSION, driveFileId);
		String response=new WebService(url,false).postWebservice("POST",premissonJsonObject.toString(),contentType,access_token);

		if (response!=null){
			googleDriveDo=deserializeGoogleDriveFilesList(response);
		}
		return googleDriveDo;
	}

	public GoogleDriveDo deserializeGoogleDriveFilesList(String jsonRep) {
		GoogleDriveDo googleDriveDo=null;
		if (jsonRep != null) {
			googleDriveDo=JsonDeserializer.deserialize(jsonRep, new TypeReference<GoogleDriveDo>() {
			});
		}
		return googleDriveDo;
	}
	public List<GoogleDriveItemDo> deserializegetGoogleDriveContent(JSONArray GoogleDriveJsonArray){
		GoogleDriveItemDo driveObj;
		List<GoogleDriveItemDo> googleResult=new ArrayList<>();
		if(GoogleDriveJsonArray!=null && GoogleDriveJsonArray.length()>0){
			for (int pointer = 0; pointer < GoogleDriveJsonArray.length(); pointer++) {
			try {
			driveObj = deserializeDriveDetails(GoogleDriveJsonArray.getJSONObject(pointer));
			if (driveObj != null) {
			googleResult.add(driveObj);
			}

			} catch (JSONException e) {
			throw new RuntimeException("message", e);
			}

			}
		}

		return googleResult;

		}
	public GoogleDriveItemDo deserializeDriveDetails(JSONObject jsonRep) {
        if (jsonRep != null) {
                return (GoogleDriveItemDo) JsonDeserializer.deserialize(jsonRep.toString(), new TypeReference<GoogleDriveItemDo>() {
                });
        }
        return new GoogleDriveItemDo();
}


	public List<GoogleDriveItemDo> deserializeFolderContent(JSONArray FolderContentJsonArray){
		GoogleDriveItemDo driveObj;
		List<GoogleDriveItemDo> folderResult=new ArrayList<>();
		if(FolderContentJsonArray!=null&& FolderContentJsonArray.length()>0){
			for (int pointer = 0; pointer < FolderContentJsonArray.length(); pointer++) {
			try {
			driveObj = deserializeFolderDetails(FolderContentJsonArray.getJSONObject(pointer));
			if (driveObj != null) {
				folderResult.add(driveObj);
			}

			} catch (JSONException e) {
			throw new RuntimeException("message", e);
			}

			}
		}
		return folderResult;

		}
	public GoogleDriveItemDo deserializeFolderDetails(JSONObject jsonRep) {
        if (jsonRep != null) {
                return (GoogleDriveItemDo) JsonDeserializer.deserialize(jsonRep.toString(), new TypeReference<GoogleDriveItemDo>() {
                });
        }
        return new GoogleDriveItemDo();
	}

	@Override
	public GoogleToken refreshGoogleAccessToken(String refreshToken) throws GwtException {
		JsonRepresentation jsonRep = null;
		GoogleToken token = null;
		String url = UrlGenerator.generateUrl(getHomeEndPoint(),
				UrlToken.REFRESH_TOKEN, refreshToken);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(),getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();

		String str = null;
		try {
			if(jsonRep!=null && jsonRep.getSize()!=-1){
				str = jsonRep.getJsonObject().toString();
				token =  deserializeGoogleToken(str);
				setLoggedInAccessToken(token != null && token.getAccess_token() != null ? token.getAccess_token() : null);
			}
		} catch (JSONException eJson) {
			logger.error("Exception::", eJson);
		}
		return token;
	}

	public GoogleToken deserializeGoogleToken(String jsonRep) {
        if (jsonRep != null) {
                return (GoogleToken) JsonDeserializer.deserialize(jsonRep, new TypeReference<GoogleToken>() {
                });
        }
        return new GoogleToken();
	}

	@Override
	public CollectionItemDo v2UpdateQuestionResource(CollectionItemDo collectionItemDo,CollectionQuestionItemDo collectionQuestionItemDo,String thumbnailUrl) throws GwtException, ServerDownException {
		CollectionItemDo collItemDo = collectionItemDo;
		JsonRepresentation jsonRep ,jsonRep2= null;
		CollectionItemDo collectionItemDoNew=null;


		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_UPDATE_QUESTION_ITEM,collectionItemDo.getParentGooruOid(), collItemDo.getCollectionItemId());

		getLogger().info("edit--"+url);
		getLogger().info("edit form--"+ResourceFormFactory.generateStringDataForm(collectionQuestionItemDo, "question"));

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), ResourceFormFactory.generateStringDataForm(collectionQuestionItemDo, "question"));

		try{
			if(jsonResponseRep.getStatusCode()==200){
				getLogger().info("edit-success-");
				String getUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_GET_QUESTION_ITEM,collectionItemDo.getParentGooruOid(), collItemDo.getCollectionItemId());
				getLogger().info("get-Question-"+getUrl);
				JsonResponseRepresentation jsonResponseRep2 = ServiceProcessor.get(getUrl, getRestUsername(), getRestPassword());
				jsonRep2 = jsonResponseRep2.getJsonRepresentation();

				if(jsonRep2!=null){

				collectionItemDoNew=deserializeCollectionItem(jsonRep2);
				if(collectionItemDoNew.getQuestionInfo()!=null){
				collItemDo.setResource(collectionItemDoNew.getQuestionInfo());
				}
				if(collectionItemDoNew.getStandards()!=null){
				collItemDo.setStandards(collectionItemDoNew.getStandards());
				}
				}

			}else{
				collectionItemDoNew=new CollectionItemDo();
			}
		}
		catch(Exception e){
			logger.info("Exception:::::::"+e);
		}
		return collectionItemDoNew;
	}


        @Override
	public String getUserShelfDetails(String userUid){
		String shelfGooruOid=null;
		JsonRepresentation jsonRep = null;

		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_USER_WORKSPACE,userUid);
		Map<String,String> params = new HashMap<>();
		params.put(GooruConstants.OFFSET, "0");
		params.put(GooruConstants.LIMIT, "1");
		String url=AddQueryParameter.constructQueryParams(partialUrl, params);

		logger.info("V2_GET_USER_WORKSPACE API=>"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(),getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		shelfGooruOid=getShelfGooruOid(jsonRep);
		return shelfGooruOid;
	}

	public String getShelfGooruOid(JsonRepresentation jsonRep){
		String shelfGooruOid=null;
		JSONObject jsonObject = null;
		if(jsonRep!=null){
			try {
				jsonObject = jsonRep.getJsonObject();
				JSONArray jsonArray=jsonObject.getJSONArray("searchResult");
				if(jsonArray!=null&&jsonArray.length()>0){
					for(int i=0;i<jsonArray.length();i++){
						JSONObject searchJsonObject=jsonArray.getJSONObject(i);
						if(searchJsonObject!=null){
							shelfGooruOid=searchJsonObject.isNull("parentGooruOid")?null:searchJsonObject.getString("parentGooruOid");
							return shelfGooruOid;
						}
					}
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return shelfGooruOid;
	}

	@Override
	public FolderDo updateAssessmentDetails(String assessmentId,String title,String assessmentUrl,String description,String sharing,String requireLogin) {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_V2_COLLLECTION,assessmentId);
		logger.info("assessment update API=>"+url);
		JSONObject assessmentMainObject=new JSONObject();
		JSONObject assessmentJsonObject=new JSONObject();
		JSONObject isRequireJsonObject=new JSONObject();
		try{

			assessmentJsonObject.put("title",title);
			assessmentJsonObject.put("url",assessmentUrl);
			if(description != null){
				assessmentJsonObject.put("goals", description);
			}
			if(sharing!=null){
				assessmentJsonObject.put("sharing", sharing);
			}
			if(requireLogin!=null){
				isRequireJsonObject.put("isLoginRequired",requireLogin);
				assessmentJsonObject.put("settings",isRequireJsonObject);
			}
			assessmentMainObject.put("collection",assessmentJsonObject);
			logger.info("data for update API=>"+assessmentMainObject.toString());
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(),getRestPassword(),assessmentMainObject.toString());

			jsonRep =jsonResponseRep.getJsonRepresentation();
			if(jsonRep!=null){
					return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), FolderDo.class);
			}
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return new FolderDo();
	}

	@Override
	public ArrayList<ResourceCollDo> getResourceBasedUsersDetails(String resourceId, int offSet, int limit) {
		JsonRepresentation jsonRepresentation = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_RESOURCE_BASED_USERS,resourceId, getLoggedInSessionToken(),String.valueOf(offSet),String.valueOf(limit));
		logger.info("getResourceBasedUsersDetails::"+url);
		JsonResponseRepresentation jsonResponseRep=ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation= jsonResponseRep.getJsonRepresentation();
		return deserializeUserCollections(jsonRepresentation);
	}
	public ArrayList<ResourceCollDo> deserializeUserCollections(JsonRepresentation jsonRep){
		ArrayList<ResourceCollDo> resourceModelList=new ArrayList<>();
		try {
			if(jsonRep!=null){
				JSONArray myCollectionArryObj=jsonRep.getJsonArray();
				for(int i=0;i<myCollectionArryObj.length();i++){
					JSONObject myCollectionObj=myCollectionArryObj.getJSONObject(i);
					UserDoMorePeople userObj=new UserDoMorePeople();
					ThumbnailDo thumbnailsDo=new ThumbnailDo();
					String myCollectionTitle=myCollectionObj.isNull("title")?"":myCollectionObj.getString("title");
					String mycollectionGid=myCollectionObj.isNull("gooruOid")?"":myCollectionObj.getString("gooruOid");
					String myCollectionType=myCollectionObj.isNull("collectionType")?"":myCollectionObj.getString("collectionType");
					if(!myCollectionObj.isNull("user")){
						userObj = JsonDeserializer.deserialize(myCollectionObj.getJSONObject("user").toString(), UserDoMorePeople.class);
					}
					if(!myCollectionObj.isNull("thumbnails")){
						thumbnailsDo = JsonDeserializer.deserialize(myCollectionObj.getJSONObject("thumbnails").toString(), ThumbnailDo.class);
					}
					ResourceCollDo collectionDetails=new ResourceCollDo();
					collectionDetails.setTitle(myCollectionTitle);
					collectionDetails.setGooruOid(mycollectionGid);
					collectionDetails.setCollectionType(myCollectionType);
					collectionDetails.setUser(userObj);
					collectionDetails.setThumbnails(thumbnailsDo);
					resourceModelList.add(collectionDetails);
				}//main for loop
			}
		}//try end
		catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return resourceModelList;
	}
	@Override
	public CollectionDo moveCollectionToMyCOllections(String collectionId, String folderId,String collectionTitle) {
		String url="";
		CollectionDo collectionDoObj=new CollectionDo();
		JsonRepresentation jsonRep = null,jsonResponseRepget=null;
		if(folderId==null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.COPY_V3_COLLECTION, collectionId);
		}else{
			String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.COPY_V3_COLLECTION, collectionId);
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put("folderId", folderId);
			url=AddQueryParameter.constructQueryParams(partialUrl, params);
		}
			getLogger().info("--- moveCollectionToMyCOllections collection URl -- "+url);
			JSONObject copyCollectionJsonObject=new JSONObject();
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), copyCollectionJsonObject.toString());
			jsonRep = jsonResponseRep.getJsonRepresentation();
			logger.info("status code moveCollectionToMyCOllections:::::"+jsonResponseRep.getStatusCode());
			try{
					if(jsonResponseRep.getStatusCode()==200){
						collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
					}else{
						collectionDoObj=new CollectionDo();
						collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
						if(jsonResponseRep.getResponseDo()!=null && jsonResponseRep.getResponseDo().getErrorMessage()!=null){
						logger.info("status code moveCollectionToMyCOllections errormessage:::::"+jsonResponseRep.getResponseDo().getErrorMessage());
						collectionDoObj.setErrorMessage(jsonResponseRep.getResponseDo().getErrorMessage());
						}
					}
			}
			catch(Exception e){
				logger.info("Exception:::::::"+e);
			}
		return collectionDoObj;
	}

	@Override
	public CollectionDo moveCollectionTOLesson(String courseId,String unitId,String LessonId,String CollectionId) {
		CollectionDo collectionDoObj=new CollectionDo();
		JsonRepresentation jsonRep = null,jsonResponseRepget=null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.MOVE_V1_COLLECTION, courseId,unitId,LessonId,CollectionId);
		try{
			getLogger().info("--- moveCollectionTOLesson CopyCollectionToLesson URl -- "+url);
			getLogger().info("-- moveCollectionTOLesson CopyCollectionToLesson payload (Put method) -- ");
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), "");
			jsonRep = jsonResponseRep.getJsonRepresentation();
			logger.info("status code moveCollectionTOLesson:::::"+jsonResponseRep.getStatusCode());
			try{
					if(jsonResponseRep.getStatusCode()==200){
						/*collectionDoObj = deserializeCollection(jsonResponseRepget);*/
						collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
					}else{
						collectionDoObj=new CollectionDo();
						collectionDoObj.setStatusCode(jsonResponseRep.getStatusCode());
						if(jsonResponseRep.getResponseDo()!=null && jsonResponseRep.getResponseDo().getErrorMessage()!=null){
						logger.info("status code moveCollectionTOLesson errormessage:::::"+jsonResponseRep.getResponseDo().getErrorMessage());
						collectionDoObj.setErrorMessage(jsonResponseRep.getResponseDo().getErrorMessage());
						}
					}
			}catch(Exception e){
				logger.error("Exception-------",e);
			}
		}catch(Exception ex){
			logger.error("Exception::", ex);
		}
		return collectionDoObj;
	}
	@Override
	public CollectionDo CopyToplevelMyCollections(String collectionId, String folderId,String collectionTitle) {
		String url="";
		CollectionDo collectionDoObj=new CollectionDo();
		JsonRepresentation jsonRep = null,jsonResponseRepget=null;
		if(folderId==null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.COPY_V3_COLLECTION, collectionId);
		}else{
			String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.COPY_V3_COLLECTION, collectionId);
			Map<String, String> params = new LinkedHashMap<>();
			params.put("folderId", folderId);
			url=AddQueryParameter.constructQueryParams(partialUrl, params);
		}
		try{
			JSONObject copyCollectionJsonObject=new JSONObject();
			copyCollectionJsonObject.put("title", collectionTitle);
			getLogger().info("--- Copy collection URl -- "+url);
			getLogger().info("-- Copy coll payload (Put method) -- "+copyCollectionJsonObject.toString());
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(), copyCollectionJsonObject.toString());
			jsonRep = jsonResponseRep.getJsonRepresentation();
			try{
				String getURL= getRestEndPoint()+jsonRep.getJsonObject().getString("uri");
				logger.info("copy collection v3 uri here:::::::"+getURL);
				JsonResponseRepresentation	jsonResponseRepresentation1=ServiceProcessor.get(getURL,getRestUsername(),getRestPassword());
				jsonResponseRepget=jsonResponseRepresentation1.getJsonRepresentation();
					if(jsonResponseRepresentation1.getStatusCode()==200){
						collectionDoObj = deserializeCollection(jsonResponseRepget);
						collectionDoObj.setStatusCode(jsonResponseRepresentation1.getStatusCode());
					}else{
						collectionDoObj=new CollectionDo();
						collectionDoObj.setStatusCode(jsonResponseRepresentation1.getStatusCode());
					}
			}catch(Exception e){
				logger.error("Exception-------",e);
			}
		}catch(Exception ex){
			logger.error("Exception::", ex);
		}
		return collectionDoObj;
	}

	@Override
	public CollectionDo CopyCollectionToLesson(String courseId,String unitId,String LessonId,String CollectionId,String collecctionTitle) {
		CollectionDo collectionDoObj=new CollectionDo();
		JsonRepresentation jsonRep = null,jsonResponseRepget=null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.MOVE_V1_COLLECTION, courseId,unitId,LessonId,CollectionId);
		try{
			JSONObject copyCollectionJsonObject=new JSONObject();
			copyCollectionJsonObject.put("title", collecctionTitle);
			getLogger().info("--- Copy CopyCollectionToLesson URl -- "+url);
			getLogger().info("-- Copy CopyCollectionToLesson payload (Post method) -- "+copyCollectionJsonObject.toString());
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(), copyCollectionJsonObject.toString());
			jsonRep = jsonResponseRep.getJsonRepresentation();
			try{
				String getURL= getRestEndPoint()+jsonRep.getJsonObject().getString("uri");
				logger.info("copy collection v3 uri here:::::::"+getURL);
				JsonResponseRepresentation	jsonResponseRepresentation1=ServiceProcessor.get(getURL,getRestUsername(),getRestPassword());
				jsonResponseRepget=jsonResponseRepresentation1.getJsonRepresentation();
					if(jsonResponseRepresentation1.getStatusCode()==200){
						collectionDoObj = deserializeCollection(jsonResponseRepget);
						collectionDoObj.setStatusCode(jsonResponseRepresentation1.getStatusCode());
					}else{
						collectionDoObj=new CollectionDo();
						collectionDoObj.setStatusCode(jsonResponseRepresentation1.getStatusCode());
					}
			}catch(Exception e){
				logger.error("Exception-------",e);
			}
		}catch(Exception ex){
			logger.error("Exception::", ex);
		}
		return collectionDoObj;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.application.client.service.ResourceService#getCourseDataById(java.lang.String)
	 */
	@Override
	public FolderDo getCourseDataById(String courseId) throws GwtException,ServerDownException {
		JsonRepresentation jsonRep = null;
		String partialUrlStr = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_COURSE,courseId);
		getLogger().info("GET_COURSE_INFO get API call::::::"+partialUrlStr);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(partialUrlStr);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeCourseInfo(jsonRep);
	}

	public FolderDo deserializeCourseInfo(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), FolderDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new FolderDo();
	}

	@Override
	public CollectionItemDo addCollectionItem(String collectionId,String resourceId,String type) throws GwtException, ServerDownException {
		JsonRepresentation jsonRep = null,jsonResponseRepget=null;
		String url="";
		CollectionItemDo collectionItemDo= new CollectionItemDo();
		getLogger().info("addCollectionItem collectionId::::::"+collectionId);
		getLogger().info("addCollectionItem resourceId::::::"+resourceId);
		getLogger().info("addCollectionItem type::::::"+type);
		if("question".equalsIgnoreCase(type)){
			 url=UrlGenerator.generateUrl(getRestEndPoint(),UrlToken.V3_ADDQUESTION_COLLECTION,collectionId,resourceId);
		}else{
			 url=UrlGenerator.generateUrl(getRestEndPoint(),UrlToken.V3_ADDRESOURCE_COLLECTION,collectionId,resourceId);
		}
		getLogger().info("addCollectionItem post API call::::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		logger.info("jsonRep:::::::"+jsonResponseRep.getStatusCode());
		try{
			if(jsonResponseRep.getStatusCode()==200){
					logger.info("addCollectionItem collection v3 uri here:::::::"+jsonRep.getJsonObject().getString("uri"));
					String getURL= getRestEndPoint()+jsonRep.getJsonObject().getString("uri");
					JsonResponseRepresentation	jsonResponseRepresentation1=ServiceProcessor.get(getURL,getRestUsername(),getRestPassword());
					jsonResponseRepget=jsonResponseRepresentation1.getJsonRepresentation();
					logger.info("jsonResponseRepget response here:::::::"+jsonResponseRepget.getJsonObject().toString());
						if(jsonResponseRepget!=null && jsonResponseRepresentation1.getStatusCode()==200){
							logger.info("jsonResponseRepget response here11111:::::::"+jsonResponseRepget.getJsonObject().toString());
							collectionItemDo = JsonDeserializer.deserialize(jsonResponseRepget.getJsonObject().toString(),CollectionItemDo.class);
							ResourceDo resoruce=JsonDeserializer.deserialize(jsonResponseRepget.getJsonObject().toString(), ResourceDo.class);
							collectionItemDo.setQuestionInfo(resoruce);
							collectionItemDo.setResource(resoruce);
							collectionItemDo.setStatusCode(jsonResponseRepresentation1.getStatusCode());
							logger.info("jsonResponseRepget response here2222:::::::"+collectionItemDo.getResource().getGooruOid());
						}else{

						}
				}else{
					collectionItemDo.setStatusCode(jsonResponseRep.getStatusCode());
					ResourceDo resoruce=new ResourceDo();
					collectionItemDo.setQuestionInfo(resoruce);
					collectionItemDo.setResource(resoruce);
				}
		}catch(Exception e){
			logger.error("Exception-------",e);
		}
		return collectionItemDo;
	}

	@Override
	public CollectionItemDo updateTimeMetadata(String collectionItemId,
			String start, String stop) throws GwtException, ServerDownException {
		// TODO Auto-generated method stub
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_UPDATE_COLLLECTION_ITEM_METADATA, collectionItemId);
		getLogger().info("updateNarrationMetadata url put call:::::"+url);
		JSONObject collectionItemObject = new JSONObject();
		JSONObject collectionMetadata=new JSONObject();
		try {
			if(stop!=null){
				collectionMetadata.put("stop", stop);
			}
			if(start!=null){
				collectionMetadata.put("start", start);
			}
			collectionItemObject.put("collectionItem", collectionMetadata);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		getLogger().info("data url put call:::::"+collectionItemObject.toString());
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), collectionItemObject.toString());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		getLogger().info("response:::::"+jsonResponseRep.getStatusCode());
		return deserializeCollectionItem(jsonRep);
	}
	@Override
	public List<ListValuesDo> getEducationalUseList() throws GwtException {
		// TODO Auto-generated method stub
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_EDUCATIONAL_USE,getLoggedInSessionToken());
		getLogger().info("-- get Educatioal -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		List<ListValuesDo> listValues = deserializeListValues(jsonRep);
		return listValues;
	}

	public List<ListValuesDo> deserializeListValues(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				return JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<List<ListValuesDo>>() {
				});
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<ListValuesDo> getMomentOfLearning()throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_MOMENT_OF_LEARERNING,getLoggedInSessionToken());
		getLogger().info("-- get Moment  of Learning -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		List<ListValuesDo> listValues = deserializeListValues(jsonRep);
		return listValues;
	}

	@Override
	public List<ListValuesDo> getMediaFeature()throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_MEDIA_FEATURES,getLoggedInSessionToken());
		getLogger().info("-- get Media features -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		List<ListValuesDo> listValues = deserializeListValues(jsonRep);
		return listValues;
	}


	@Override
	public List<ListValuesDo> getAccessHazards()throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_ACCESS_HAZARD,getLoggedInSessionToken());
		getLogger().info("-- get Access Hazards -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		List<ListValuesDo> listValues = deserializeListValues(jsonRep);
		return listValues;
	}

}
