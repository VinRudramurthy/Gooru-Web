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

import org.restlet.data.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.client.service.FolderService;
import org.ednovo.gooru.application.server.ArrayListSorter;
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
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.application.shared.model.folder.FolderTocDo;
import org.ednovo.gooru.shared.util.GooruConstants;
import org.ednovo.gooru.shared.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

@Service("folderService")
@ServiceURL("/folderService")
public class FolderServiceImpl extends BaseServiceImpl implements FolderService {

	/**
	 *
	 */
	private static final long serialVersionUID = -3513384975044132831L;

	private static final String FOLDER = "collection";
	private static final String ADD_TO_SHELF = "addToShelf";
	private static final String TAXONOMY_CODE = "taxonomyCode";
	private static final String PARENT_ID = "parentId";
	private static final String WORKSPACE_PAGE_SIZE = "50";
	private static final String ORDER_BY="sequence";
	private static final String TITLE = "title";
	private static final String SOURCE_ID = "sourceId";
	private static final String TARGET_ID = "targetId";
	private static final String IDEAS = "ideas";
	private static final String QUESTIONS = "questions";
	private static final String PERFORMANCE_TASKS = "performanceTasks";

	private String parentId = "";

	private static final String TRUE = "true";
	private static final String FALSE = "false";

	private static final Logger logger = LoggerFactory.getLogger(FolderServiceImpl.class);

	@Autowired
	ResourceDeserializer resourceDeserializer;

	@Override
	public CollectionDo createFolder(CollectionDo collectionDo) {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(),UrlToken.CREATE_FOLDER);
		Iterator it = collectionDo.getTaxonomySet().iterator();
		String taxonomyId = "";
		while(it.hasNext()){
			CodeDo codeDo = (CodeDo) it.next();
			if(codeDo.getCodeId()!=null){
				taxonomyId = codeDo.getCodeId().toString();
			}
		}

		collectionDo.getTaxonomySet().clear();
		Form form = ResourceFormFactory.generateDataForm(collectionDo, FOLDER);
		form.add(ADD_TO_SHELF, TRUE);
		if(!taxonomyId.isEmpty()){
			form.add(TAXONOMY_CODE, taxonomyId);
		}


		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(),getRestPassword(), form);
		logger.info("CreteFolder Url : "+url);
		logger.info("form : "+form);
		jsonRep =jsonResponseRep.getJsonRepresentation();
		return deserializeCollection(jsonRep);

	}

	@Override
	public CollectionDo createFolderToParentFolder(CollectionDo collectionDo,String parentId) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(),UrlToken.CREATE_FOLDER);

		Iterator it = collectionDo.getTaxonomySet().iterator();
		String taxonomyId = "";
		while(it.hasNext()){
			CodeDo codeDo = (CodeDo) it.next();
			if(codeDo.getCodeId()!=null){
				taxonomyId = codeDo.getCodeId().toString();
			}
		}
		this.parentId = parentId;
		collectionDo.getTaxonomySet().clear();
		Form form = ResourceFormFactory.generateDataForm(collectionDo, FOLDER);
		form.add(ADD_TO_SHELF, FALSE);
		if(!taxonomyId.isEmpty()){
			form.add(TAXONOMY_CODE, taxonomyId);
		}
		form.add(PARENT_ID, this.parentId);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(),getRestPassword(), form);
		logger.info("createFolderToParentFolder : "+url);
		logger.info("form : "+form);
		jsonRep =jsonResponseRep.getJsonRepresentation();
		return deserializeCollection(jsonRep);
	}

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

	@Override
	public List<CollectionItemDo> getAllFolders() throws GwtException {
		JsonRepresentation jsonRep = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.LIST_MY_FOLDERS);
		Map<String, String> params = new LinkedHashMap<>();
		params.put(GooruConstants.PAGE_SIZE, WORKSPACE_PAGE_SIZE);
		params.put(GooruConstants.ORDER_BY, ORDER_BY);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("-- getAllFolders -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		List<CollectionItemDo> collectionItemDo = deserializeWorkspace(jsonRep);

		Collections.sort(collectionItemDo, new ArrayListSorter("itemSequence", true));
		return collectionItemDo;
	}
	
	@Override
	public List<ListValuesDo> getDepthOfKnowledgesList() throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_DEPTHOFKNOWLEDGELIST);
		getLogger().info("-- getdepthOfKnowledge -- "+url);
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
	
	public List<CollectionItemDo> deserializeWorkspace(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				return JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<List<CollectionItemDo>>() {
				});
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return new ArrayList<>();
	}

	@Override
	public void deleteFolder(String collectionId) throws GwtException {
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.DELETE_COLLECTION, collectionId);
		ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
	}


	@Override
	public CollectionDo getFolderInformation(String folderId) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_A_FOLDER_INFORMATION, folderId);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		return deserializeCollection(jsonRep);
	}

	public List<CollectionDo> deserializeFoldersAndCollections(JsonRepresentation jsonRep) {
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

	@Override
	public List<CollectionItemDo> getFolders(String collectionId) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.LIST_MY_FOLDER_LEVELS, collectionId);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		return deserializeCollectionItems(jsonRep);
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

	@Override
	public FolderListDo getChildFolders(int offset, int limit, String parentId,String sharingType, String collectionType,boolean isExcludeAssessment) throws GwtException {
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_CHILD_FOLDER_LIST, parentId);
		Map<String, String> params = new LinkedHashMap<>();
		params.put(GooruConstants.OFFSET, String.valueOf(offset));
		params.put(GooruConstants.LIMIT, String.valueOf(limit));
		params.put(GooruConstants.ORDER_BY, GooruConstants.SEQUENCE);

		if(sharingType!=null){
			params.put(GooruConstants.SHARING, sharingType);
//			sessionToken=sessionToken+"&sharing="+sharingType;
		}
		if(collectionType!=null){
			params.put(GooruConstants.COLLECTION_TYPE, collectionType);
//			sessionToken=sessionToken+"&collectionType="+collectionType;
		}
		if(isExcludeAssessment){
			params.put(GooruConstants.EXCLUDE_TYPE, "assessment/url");
//			url=url+"&excludeType=assessment/url";
		}

		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		logger.info("getChildFolders folder service : "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeFolderList(jsonRep);
	}

	
	//Get Call for course
	@Override
	public FolderListDo getChildFoldersForCourse(int offset, int limit,String courseId,String unitId,String lessonId,String sharingType, String collectionType,boolean isExcludeAssessment) throws GwtException {
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		if(courseId!=null && unitId==null){
			partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_UNITS_BY_COURSEID, courseId);
		}else if(courseId!=null && unitId!=null && lessonId==null){
			partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_LESSONS_BY_LESSONID, courseId,unitId);
		}else if(courseId!=null && unitId!=null && lessonId!=null){
			partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_COLLECTIONS_BY_COLLECTIONID, courseId,unitId,lessonId);
		}
		Map<String, String> params = new LinkedHashMap<>();
		params.put(GooruConstants.OFFSET, String.valueOf(offset));
		params.put(GooruConstants.LIMIT, String.valueOf(limit));
		params.put(GooruConstants.ORDER_BY, GooruConstants.SEQUENCE);
		if(sharingType!=null){
			params.put(GooruConstants.SHARING, sharingType);
		}
		if(collectionType!=null){
			if(courseId!=null && unitId!=null && lessonId!=null){
			params.put(GooruConstants.COLLECTION_TYPE, collectionType);
			}
		}
		if(isExcludeAssessment){
			params.put(GooruConstants.EXCLUDE_TYPE, "assessment/url");
		}
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		logger.info("getChildFoldersForCourse service : "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeFolderListForCourse(jsonRep);
	}
        
	public FolderListDo deserializeFolderListForCourse(JsonRepresentation jsonRep) {
		FolderListDo listObj=new FolderListDo();
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				listObj.setSearchResult((ArrayList<FolderDo>) JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(),new TypeReference<List<FolderDo>>() {}));
				return listObj;
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return listObj;
	}
        
	public FolderListDo deserializeFolderList(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), new TypeReference<FolderListDo>() {});
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return new FolderListDo();
	}

	@Override
	public FolderDo createFolder(String folderName, String parentId, boolean addToShelf) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = null;
		FolderDo folderDo = null;
		url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_CREATE_FOLDER);
		JSONObject folderObject=new JSONObject();
		try {
			folderObject.put(TITLE, folderName);
			if(!parentId.isEmpty()) {
				folderObject.put(PARENT_ID, parentId);
			}
			if(addToShelf) {
				folderObject.put(ADD_TO_SHELF, addToShelf);
			}
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.post(url, getRestUsername(), getRestPassword(),folderObject.toString());

			logger.info("createFolderToParentFolder : "+url);
			logger.info("folderObject : "+folderObject.toString());

			jsonRep=jsonResponseRep.getJsonRepresentation();
			folderDo = deserializeCreatedFolder(jsonRep);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return folderDo;
	}
	
	
	public FolderDo deserializeCreatedFolder(JsonRepresentation jsonRep) {
		try {
                        // TODO: Remove this hard coded -1 with UNKNOWN_SIZE as provided by Restlet
			if (jsonRep != null && jsonRep.getSize() != -1) {
        			getLogger().info("jsonRep.getJsonObject().toString():::::::"+jsonRep.getJsonObject().toString());
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), new TypeReference<FolderDo>() {});
			} else {
                            getLogger().error(new StringBuilder("deserializeCreatedFolder: jsonRep is either null or UNKNOWN_SIZE").toString());
                        }
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return new FolderDo();
	}

	@Override
	public void deleteCollectionsFolder(String folderId) throws GwtException {
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_DELETE_FOLDER, folderId);
		getLogger().info("deleteCollectionsFolder:::::::"+url);
		ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
	}

	@Override
	public void moveCollectionIntoFolder(String sourceId, String targetId) throws GwtException {
		String url = null;
		url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_MOVE_COLLECTION);
		JSONObject folderObject=new JSONObject();
		try {
			folderObject.put(SOURCE_ID, sourceId);
			if(targetId!=null && !targetId.equalsIgnoreCase("")){
				folderObject.put(TARGET_ID, targetId);
			}
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.put(url, getRestUsername(), getRestPassword(),folderObject.toString());
			logger.info("moveCollectionIntoFolder : "+url);
			logger.info("folderObject 1 :" +folderObject.toString() );
		} catch (Exception e) {
			logger.error("Exception::", e);
		}

	}

	@Override
	public CollectionDo createCollectionInParent(CollectionDo data,String courseCodeId, String folderId) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = null;
		CollectionDo collectionDo = null;
		JSONObject collectionDataObject=new JSONObject();
		JSONObject courseIdObj=new JSONObject();
		JSONObject settingsObj=new JSONObject();
		JSONObject FolderDataObject=new JSONObject();
		url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_CREATE_COLLECTION_IN_FOLDER);
		try {
			collectionDataObject.put("collectionType", data.getCollectionType());
			collectionDataObject.put("title", data.getTitle());
			collectionDataObject.put("sharing", data.getSharing());
			collectionDataObject.put("grade", data.getGrade());
			collectionDataObject.put("mediaType", data.getMediaType());
			collectionDataObject.put("url", data.getUrl());
			collectionDataObject.put("goals", data.getGoals());
			if (courseCodeId != null) {
				courseIdObj.put("codeId", courseCodeId);
				ArrayList<JSONObject> taxonomyArray= new ArrayList<>();
				taxonomyArray.add(courseIdObj);
				collectionDataObject.put("taxonomySet", taxonomyArray);
			}
			if(data.getSettings()!=null && data.getSettings().getIsLoginRequired()!=null){
				settingsObj.put("comment", "turn-on");
				settingsObj.put("isLoginRequired", data.getSettings().getIsLoginRequired());
				collectionDataObject.put("settings", settingsObj);
			}

			FolderDataObject.put("collection", collectionDataObject);
			FolderDataObject.put("parentId", folderId);




			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.post(url, getRestUsername(), getRestPassword(),FolderDataObject.toString());
			logger.info("FolderDataObject.toString() : "+FolderDataObject.toString());
			logger.info("createCollectionInParent : "+url);
			jsonRep=jsonResponseRep.getJsonRepresentation();
			collectionDo = deserializeCreatedCollInFolder(jsonRep);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return collectionDo;
	}

	public CollectionDo deserializeCreatedCollInFolder(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), new TypeReference<CollectionDo>() {});
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return new CollectionDo();
	}

	@Override
	public void updateFolder(String folderId, String title, String ideas, String questions, String performance) throws GwtException {
		String url = null;
		url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_UPDATE_FOLDER_METADATA, folderId);
		logger.info("updateFolder : "+url);
		JSONObject folderObject=new JSONObject();
		try {
			folderObject.put(TITLE, title);
			if(ideas!=null) {
				if(ideas.length()>1000) {
					ideas = ideas.substring(0, 1000);
				}
				folderObject.put(IDEAS, ideas);
			}
			if(questions!=null) {
				if(questions.length()>1000) {
					questions = questions.substring(0, 1000);
				}
				folderObject.put(QUESTIONS, questions);
			}
			if(performance!=null) {
				if(performance.length()>1000) {
					performance = performance.substring(0, 1000);
				}
				folderObject.put(PERFORMANCE_TASKS, performance);
			}
			logger.info("folderObject : "+folderObject.toString());
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.put(url, getRestUsername(), getRestPassword(),folderObject.toString());
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
	}
	
	

	@Override
	public CollectionDo copyDraggedCollectionIntoFolder(CollectionDo data,String courseCodeId,String parentId,boolean addToShelf) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = null;
		CollectionDo collectionDo = null;
		JSONObject collectionDataObject=new JSONObject();
		JSONObject FolderDataObject=new JSONObject();
		url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_COPY_COLLECTION_IN_FOLDER,data.getGooruOid());
		try {
			collectionDataObject.put("collectionType", "collection");
			collectionDataObject.put("title", data.getTitle());
			collectionDataObject.put("sharing", data.getSharing());
			collectionDataObject.put("grade", data.getGrade());
			collectionDataObject.put("mediaType", data.getMediaType());
			if (courseCodeId != null) {
				collectionDataObject.put("taxonomyCode", courseCodeId);

			}
			FolderDataObject.put("collection", collectionDataObject);
			FolderDataObject.put("parentId", parentId);
			FolderDataObject.put("addToShelf", addToShelf);
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.put(url, getRestUsername(), getRestPassword(),FolderDataObject.toString());
			jsonRep=jsonResponseRep.getJsonRepresentation();
			collectionDo = deserializeCreatedCollInFolder(jsonRep);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return collectionDo;
	}

	@Override
	public FolderListDo getCollectionResources(String parentId,	String sharingType, String collectionType) throws GwtException {
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		String sessionToken=getLoggedInSessionToken();
		partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_COLLECTION_RESOURCE_LIST, parentId);
		Map<String, String> params = new LinkedHashMap<>();
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
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeFolderList(jsonRep);
	}

    @Override
    public void reorderFoldersOrCollections(String courseId, String unitId, String lessonId, String collectionId, int itemToBeMovedPosSeqNumb, String collectionItemId, String type) throws GwtException, ServerDownException {
        JsonRepresentation jsonRep = null;
        String url = null;
        try {
            getLogger().info("-- itemToBeMovedPosSeqNumbI - - - - " + itemToBeMovedPosSeqNumb);
            getLogger().info("-- collectionItemId - - - - " + collectionItemId);
            getLogger().info("-- collectionId - - - - " + collectionId);
            JSONObject jsonObj = new JSONObject();
            if (type != null && type.equalsIgnoreCase("Folder")) {
                url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_UPDATE_COLLECTONITEM_METADATA, collectionId, collectionItemId);
                //url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_REORDER_FOLDER_COLLECTION,collectionItemId, itemToBeMovedPosSeqNumb+"");
            } else {
                /*if(courseId!=null && unitId!=null && lessonId!=null && collectionId!=null){
                 //Reorder resources
                 url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_UPDATE_COLLECTONITEM_METADATA,collectionId,collectionItemId);
                 }else*/
                if (courseId != null && unitId != null && lessonId != null && collectionId != null && collectionItemId != null) {
                    //Reorder collections
                    url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_UPDATE_COLLECTONITEM_METADATA, collectionId, collectionItemId);
                } else if (courseId != null && unitId != null && lessonId == null) {
                    //Reorder lessons
                    url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_LESSON_METADATA, courseId, unitId, collectionItemId);
                } else if (courseId != null && unitId == null) {
                    //Reorder units
                    url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_UNIT_METADATA, courseId, collectionItemId);
                } else if (courseId == null) {
                    //Reorder courses
                    url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_UPDATE_COURSE_METADATA, collectionItemId);
                }
            }
            jsonObj.put("position", itemToBeMovedPosSeqNumb);
            getLogger().info("-- Folder Re-order API - - - - " + url);
            getLogger().info("-- payload Re-order API - - - - " + jsonObj.toString());
            // TODO: AM: We need to handle the case where in the return value is checked 
            // as well as in case of exception being thrown we need to show something
            // on UI
            JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), jsonObj.toString());
        } catch (Exception e) {
            // TODO: AM: Need to communicate back to UI about this exception instead of 
            // just eating it up
            logger.error("Exception::", e);
        }
    }

	@Override
	public FolderTocDo getTocFolders(String folderId,boolean fromPPP) throws GwtException,ServerDownException {
		FolderTocDo folderTocDo = new FolderTocDo();
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GETTOCFOLDERSANDCOLLECTIONS, folderId);
		Map<String, String> params = new LinkedHashMap<>();
		params.put(GooruConstants.ORDER_BY, GooruConstants.SEQUENCE);
		if(!fromPPP){
		params.put(GooruConstants.SHARING, GooruConstants.PUBLIC);
		}
//		url=url+"&sharing=public";
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("-- Folder toc API - - - - "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		if(jsonResponseRep!=null && jsonResponseRep.getJsonRepresentation()!=null){
			folderTocDo = deserializeFolderTocList(jsonResponseRep.getJsonRepresentation());
        		folderTocDo.setStatusCode(jsonResponseRep.getStatusCode());
		} else {
                    logger.error(new StringBuilder("getTocFolders: jsonResponseRep is null for url ").append(url).toString());
                }
                
		return folderTocDo;
	}
	public FolderTocDo deserializeFolderTocList(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), new TypeReference<FolderTocDo>() {});
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return new FolderTocDo();
	}

	@Override
	public FolderDo getFolderMetaData(String folderId) throws GwtException,ServerDownException {
		JsonRepresentation jsonRep = null;
		String url = null;
		url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_FOLDER_META_DATA, folderId);
		getLogger().info("-- getFolderMetaData - - - - "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeCreatedFolder(jsonRep);
	}

	@Override
	public Map<String,String> getFolderRouteNodes(String folderId) throws GwtException, ServerDownException {
		Map<String,String> folderList=new LinkedHashMap<>();
		String url = UrlGenerator.generateUrl(getRestEndPoint(),UrlToken.V2_FOLDER_ROUTE_NODES, folderId);
		getLogger().info("getFolderRouteNodes:"+url);
		JsonResponseRepresentation jsonResponseRep =ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		if(jsonResponseRep.getStatusCode()==200){
			JsonRepresentation jsonRepresentation=jsonResponseRep.getJsonRepresentation();
			try{
				JSONArray foldersArray=jsonRepresentation.getJsonArray();
				if(foldersArray!=null&&foldersArray.length()>0){
					for(int i=0;i<foldersArray.length();i++){
						JSONObject jsonObj=foldersArray.getJSONObject(i);
						folderList.put(jsonObj.getString("gooruOid"), jsonObj.getString("title"));
					}
				}
				return folderList;
			}catch(Exception e){
				logger.error("Exception::", e);
			}
		}
		return folderList;
	}
	
	@Override
	public FolderDo createCourse(CreateDo createDo,boolean addToShelf, String courseId, String unitId, String lessonId) throws GwtException {
		JsonRepresentation jsonRep = null,jsonRepGet=null;
		String url = null;
		FolderDo folderDo = new FolderDo();
		if(courseId==null && unitId==null && lessonId==null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_CREATE_COURSE);
		}else if(courseId!=null && unitId==null && lessonId==null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_CREATE_UNIT,courseId);
		}else if(courseId!=null && unitId!=null && lessonId==null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_CREATE_LESSON,courseId,unitId);
		}else{
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_CREATE_COLLECTION,courseId,unitId,lessonId);
		}
		JSONObject courseObject=new JSONObject();
		try {
			courseObject.put(TITLE, createDo.getTitle());
			if(addToShelf) {
				courseObject.put(ADD_TO_SHELF, addToShelf);
			}
			String dataPassing=ResourceFormFactory.generateStringDataForm(createDo, null);
			logger.info("createCourse : "+url);
			logger.info("dataPassing: "+dataPassing);
			
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.post(url, getRestUsername(), getRestPassword(),dataPassing);
			jsonRep=jsonResponseRep.getJsonRepresentation();
			logger.info("jsonRep result: "+ jsonRep.getJsonObject().toString());
			logger.info("rest point: "+ getRestEndPoint());
			logger.info("uri : "+jsonRep.getJsonObject().getString("uri"));
			String getURL = getRestEndPoint()+jsonRep.getJsonObject().getString("uri");
			JsonResponseRepresentation jsonResponseRep1 = ServiceProcessor.get(getURL, getRestUsername(), getRestPassword());
			jsonRepGet=jsonResponseRep1.getJsonRepresentation();
			folderDo = deserializeCreatedFolder(jsonRepGet);
			logger.info("folderDo obj : "+folderDo);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return folderDo;
	}
	
	
	@Override
	public FolderDo createCollection(CreateDo createDo,String parentId,boolean addToShelf) throws GwtException {
		JsonRepresentation jsonRep = null,jsonRepGet=null;
		String url = null;
		FolderDo folderDo = new FolderDo();
		url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_CREATE_COLLECTION);
		if(!StringUtil.isEmpty(parentId)){
			url=AddQueryParameter.constructQueryParams(url, "folderId", parentId);
		}
		
		JSONObject collectionObject=new JSONObject();
		try {
			collectionObject.put(TITLE, createDo.getTitle());
			if(addToShelf) {
				collectionObject.put(ADD_TO_SHELF, addToShelf);
			}
			String dataPassing=ResourceFormFactory.generateStringDataForm(createDo, null);
			logger.info("createCollection : "+url);
			logger.info("dataPassing: "+dataPassing);
			
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.post(url, getRestUsername(), getRestPassword(),dataPassing);
			jsonRep=jsonResponseRep.getJsonRepresentation();
			logger.info("jsonRep result: "+ jsonRep.getJsonObject().toString());
			logger.info("rest point: "+ getRestEndPoint());
			logger.info("uri : "+jsonRep.getJsonObject().getString("uri"));
			String getURL = getRestEndPoint()+jsonRep.getJsonObject().getString("uri");
			JsonResponseRepresentation jsonResponseRep1 = ServiceProcessor.get(getURL, getRestUsername(), getRestPassword());
			jsonRepGet=jsonResponseRep1.getJsonRepresentation();
			folderDo = deserializeCreatedFolder(jsonRepGet);
			logger.info("folderDo obj : "+folderDo);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return folderDo;
	}
	
	

	@Override
	public FolderDo getCourseDetails(String courseId, String unitId, String lessonId) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = null;
		FolderDo folderDo = new FolderDo();
		if(courseId!=null && unitId!=null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_UNIT_METADATA,courseId,unitId);
		}else if(courseId!=null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_UPDATE_COURSE_METADATA,courseId);
		}
		try {
			logger.info("getCourseDetails : "+url);
			if(url!=null)
			{
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.get(url, getRestUsername(), getRestPassword());
			jsonRep=jsonResponseRep.getJsonRepresentation();
			folderDo = deserializeCreatedFolder(jsonRep);
			logger.info("folderDo obj : "+folderDo);
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return folderDo;
	}

	@Override
	public void updateCourse(String courseId,String unitId,String lessonId,String collectionId, CreateDo createDo) throws GwtException, ServerDownException {
		String url = null;
	
		if(courseId!=null && unitId!=null && lessonId!=null && collectionId!=null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_UPDATE_COLLECTION, courseId,unitId,lessonId,collectionId);
		}else if(courseId!=null && unitId!=null && lessonId!=null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_UPDATE_LESSON, courseId,unitId,lessonId);
		}else if(courseId!=null && unitId!=null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_UPDATE_UNIT, courseId,unitId);
		}else{
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_UPDATE_COURSE_METADATA, courseId);
		}
		
		JSONObject courseObj=new JSONObject();
		try {
			courseObj.put(TITLE, createDo.getTitle());
			logger.info("updateCourse : "+url);
			String dataPassing=ResourceFormFactory.generateStringDataForm(createDo, null);
			logger.info("dataPassing : "+dataPassing);
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.put(url, getRestUsername(), getRestPassword(),dataPassing);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
	}

	@Override
	public Integer deleteCourse(String o1CourseId) throws GwtException,ServerDownException {
		
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.DELETE_COURSE, o1CourseId);
		getLogger().info("DELETE_COURSE:::::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
		Integer statusCode = jsonResponseRep.getStatusCode();
		return statusCode;
	}

	@Override
	public Integer deleteUnit(String o1CourseId, String o2UnitId) throws GwtException, ServerDownException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.DELETE_UNIT, o1CourseId,o2UnitId);
		getLogger().info("DELETE_Unit:::::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
		Integer statusCode = jsonResponseRep.getStatusCode();
		return statusCode;
	}

	@Override
	public Integer deleteLesson(String o1CourseId, String o2UnitId,	String o3LessonId) throws GwtException, ServerDownException {
		
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.DELETE_LESSON, o1CourseId,o2UnitId,o3LessonId);
		getLogger().info("Lesson delete:::::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
		Integer statusCode = jsonResponseRep.getStatusCode();
		return statusCode;
	}

	@Override
	public Integer getClassesAssociatedWithCourse(String o1CourseId) throws GwtException, ServerDownException {

		JsonRepresentation jsonRep = null;
		Integer associatedClassesSize = 0;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_CLASSES_ASSOCIATED_WITH_COURSE, o1CourseId);
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.LIMIT,"10");
		getLogger().info("--- Associated classes for course -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		try {
			associatedClassesSize = jsonRep.getJsonArray().length();
		} catch (Exception e) {
			getLogger().error("Exception -- ",e);
		}
		return associatedClassesSize;
	}

	@Override
	public Integer deleteCollectionAssessment(String o1CourseId, String o2UnitId,String o3LessonId, String assessmentCollectionId)	throws GwtException, ServerDownException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.DELETE_LESSON_COLLECTION, o1CourseId,o2UnitId,o3LessonId,assessmentCollectionId);
		getLogger().info("Lesson Collection delete:::::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
		Integer statusCode = jsonResponseRep.getStatusCode();
		return statusCode;
	}

	@Override
	public List<ListValuesDo> getAudienceList() throws GwtException {
		// TODO Auto-generated method stub
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_AUDIENCELIST,getLoggedInSessionToken());
		getLogger().info("-- get Audience -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		List<ListValuesDo> listValues = deserializeListValues(jsonRep);

		return listValues;
	}
	
	@Override
	public void updateCollectionDetails(CreateDo createDoObj,String collectionId, Map<Integer,String> audience,Map<Integer,String> dok,Map<Long,String> centurySkills,String languageObjective){
		
		String url = null;
		url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V3_UPDATE_COLLECTION, collectionId);
		JSONObject collectionObject=new JSONObject();
		try {
			
			collectionObject.put("languageObjective", languageObjective);
			List<String> dokKeys=new ArrayList<>();		
			Set<Integer> audienceKeys=audience!=null?audience.keySet():null;
			if(audience!=null){
				collectionObject.put("audienceIds", getKeys(audience.keySet()));
			}
			if(dok!=null){
				collectionObject.put("depthOfKnowledgeIds", getKeys(dok.keySet()));
			}
			if(centurySkills!=null){
				collectionObject.put("skillsIds", getKeysLong(centurySkills.keySet()));
			}
			collectionObject.put("title", createDoObj.getTitle());
			String dataPassing=ResourceFormFactory.generateStringDataForm(createDoObj, null);
			
			getLogger().info("Url update coll details -- "+url);
			getLogger().info("form data -- "+dataPassing);
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.put(url, getRestUsername(), getRestPassword(),dataPassing);
		
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
	}
	
	
	public List<String> getKeys(Set<Integer> keys){
		List<String> keyString=new ArrayList<>();		
	
		for(Integer key:keys){
			keyString.add(key+"");
		}
		return keyString;
	}
        
	public List<String> getKeysLong(Set<Long> keys){
		List<String> keyString=new ArrayList<>();		
	
		for(Long key:keys){
			keyString.add(key+"");
		}
		return keyString;
	}

	@Override
	public String copyCourse(String courseId, String unitId, String lessonId) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = null;
		if(courseId!=null && unitId==null && lessonId==null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_COPY_COURSE,courseId);
		}else if(courseId!=null && unitId!=null && lessonId==null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_COPY_UNIT,courseId,unitId);
		}else if(courseId!=null && unitId!=null && lessonId!=null){
			url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_COPY_LESSON,courseId,unitId,lessonId);
		}
		String jobUri = "";
		try {
			
			logger.info("copyCUL : "+url);
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.post(url, getRestUsername(), getRestPassword());
			jsonRep=jsonResponseRep.getJsonRepresentation();
			logger.info("jsonRep result: "+ jsonRep.getJsonObject().toString());
			jobUri = jsonRep.getJsonObject().getString("uri");
		} catch (JSONException e) {
			logger.error("Exception::", e);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return jobUri;
	}

	@Override
	public Map<String, String> jobCheck(String result) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = null;
		Map<String, String> resultMap = new HashMap<>();
		try {
			url = getRestEndPoint()+result;
			logger.info("JOB getURL : "+url);
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
			jsonRep=jsonResponseRep.getJsonRepresentation();
			logger.info("jsonRep result: "+ jsonRep.getJsonObject().toString());
			resultMap = deserializeJob(jsonRep);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return resultMap;
	}

	private Map<String, String> deserializeJob(JsonRepresentation jsonRep) {
		Map<String, String> resMap = new HashMap<>();
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				JSONObject jobObject=jsonRep.getJsonObject();
				resMap.put("gooruOid", jobObject.isNull("gooruOid")?null:jobObject.getString("gooruOid"));
				resMap.put("status", jobObject.isNull("status")?null:jobObject.getString("status"));
			} catch (Exception e) {
				logger.error("Exception -- ",e); 
			}
		}
		return resMap;
	}

	@Override
	public Boolean isTiedWithStudentData(String o1CourseId) throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = null;
		Boolean status = null;
		try {
			url = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_IS_STUDENT_DATA_AVAILABLE,o1CourseId);
			logger.info("V1_IS_STUDENT_DATA_AVAILABLE URl : "+url);
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
			jsonRep=jsonResponseRep.getJsonRepresentation();
			logger.info("jsonRep result: "+ jsonRep.getJsonObject().toString());
			status = deserializeStudentDataAvailability(jsonRep);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return status; 
	}

	private Boolean deserializeStudentDataAvailability(JsonRepresentation jsonRep) {
		Boolean status = null;
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				JSONObject jsonObj=jsonRep.getJsonObject();
				if(!jsonObj.isNull("message")){
					JSONObject messageJsonObj=jsonObj.getJSONObject("message"); 
					status =messageJsonObj.getBoolean("userSignalsAvailable");
				}
			} catch (Exception e) {
				logger.error("Exception -- ",e); 
			}
		}

		return status; 
	}
}
