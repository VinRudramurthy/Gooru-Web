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

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.ednovo.gooru.application.client.service.MediaUploadService;
import org.ednovo.gooru.application.server.annotation.ServiceURL;
import org.ednovo.gooru.application.server.form.ResourceFormFactory;
import org.ednovo.gooru.application.server.request.JsonResponseRepresentation;
import org.ednovo.gooru.application.server.request.ServiceProcessor;
import org.ednovo.gooru.application.server.request.UrlToken;
import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.model.content.CollectionAddQuestionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.QuestionAnswerDo;
import org.ednovo.gooru.application.shared.model.content.QuestionHintsDo;
import org.ednovo.gooru.application.shared.model.content.ResourceDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;
import org.ednovo.gooru.shared.util.GooruConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.data.Header;
import org.restlet.data.MediaType;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.ext.html.FormData;
import org.restlet.ext.html.FormDataSet;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;


/**
 * @author Search Team
 *
 */
@Service("mediaUploadService")
@ServiceURL("/mediaUploadService")
public class MediaUploadServiceImpl extends BaseServiceImpl implements
		MediaUploadService {
	private static final long serialVersionUID = -8673556966040594979L;
	private static final String ADDED = "added";
	private static final String TITLE = "title";
	private static final Logger logger = LoggerFactory.getLogger(MediaUploadServiceImpl.class);
	private static final String HEADER_GOORU_SESSION_TOKEN = "Gooru-Session-Token";
	@Override
	public MediaUploadDo imageWebUpload(String imageURL) {
		MediaUploadDo mediaUploadDo = null;
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.MEDIA_FILE_UPLOAD);
		JSONObject jsonObj=new JSONObject();
		try {
			jsonObj.put("imageURL", imageURL);
			jsonObj.put("reSize",true);
			jsonObj.put("width",750);
			jsonObj.put("height",525);
		} catch (JSONException e1) {
			getLogger().error(e1.getMessage());
		}
		String responseText ="";
			try {
				responseText = fileUploadImage(jsonObj.toString(), url);
			} catch (Exception e) {
				logger.error("Exception::", e);
			}

			try {
				mediaUploadDo = JsonDeserializer.deserialize(responseText, MediaUploadDo.class);
			} catch (Exception e) {
				logger.error("Exception::", e);
			}
		return mediaUploadDo;

	}

	@Override
	public String saveImageCollection(String courseId, String unitId, String lessonId, String collectionId, CreateDo createDo, String fileName) {
		createDo.setMediaFilename(fileName);
		JsonRepresentation jsonRep1 = null;
		String url1 = null;

		String filepath ="";

		if(courseId!=null && unitId!=null && lessonId!=null && collectionId!=null){
			url1 = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_UPDATE_COLLECTION, courseId,unitId,lessonId,collectionId);
		}

		JSONObject courseObj=new JSONObject();
		try {
			courseObj.put(TITLE, createDo.getTitle());
			logger.info("updateCourse : "+url1);
			logger.info("updateCoursetoken : "+getLoggedInSessionToken());
			String dataPassing=ResourceFormFactory.generateStringDataForm(createDo, null);
			logger.info("dataPassing : "+dataPassing);
			JsonResponseRepresentation jsonResponseRep1=ServiceProcessor.put(url1, getRestUsername(), getRestPassword(),dataPassing);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		CollectionDo collDo = new CollectionDo();
		String urlGet = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_UPDATE_COLLECTION, courseId,unitId,lessonId,collectionId);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(urlGet, getRestUsername(), getRestPassword());
		jsonRep1 = jsonResponseRep.getJsonRepresentation();
		collDo = deserializeCreatedCollInFolder(jsonRep1);
		filepath = collDo.getThumbnailUrl();
		return filepath;


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
	public CollectionItemDo saveImage(String gooruOid, String resourceId, String fileName) {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_CREATE_COLLECTION_ITEM,gooruOid);
		try{
		JSONObject createCollectionJsonObject=new JSONObject();
		JSONObject itemTypeJsonObject=new JSONObject();
		itemTypeJsonObject.put("itemType", ADDED);
		createCollectionJsonObject.put("collectionItem", itemTypeJsonObject);
		if (resourceId != null) {
			createCollectionJsonObject.put("resourceId", resourceId);
		}
		if (fileName != null) {
			createCollectionJsonObject.put("mediaFileName", fileName);
		}

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(),createCollectionJsonObject.toString());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return deserializeCollectionItem(jsonRep);
	}

	public CollectionItemDo deserializeCollectionItem(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), CollectionItemDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new CollectionItemDo();
	}

	@Override
	public String cropImage(String fileName, String height, String width,String xPosition, String yPosition, String imageUrl) {

		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(),UrlToken.IMAGE_CROP, fileName);
		Map<String,String> params = new HashMap<String, String>();
		params.put(GooruConstants.HEIGHT,height);
		params.put(GooruConstants.WIDTH,width);
		params.put(GooruConstants.XPOSITION,xPosition);
		params.put(GooruConstants.YPOSITION,yPosition);
		params.put(GooruConstants.CROPENGINE,GooruConstants.BUFFERIMAGE);
		String url=AddQueryParameter.constructQueryParams(partialUrl,params);
		try{
			logger.info("cropImage:"+url);
			ServiceProcessor.put(url, getRestUsername(), getRestPassword(),	new Form());
		}catch(Exception ex){
			logger.error("Exception::", ex);
		}
		/*String croppedURL = UrlGenerator.generateUrl(getRestEndPoint(),UrlToken.GET_CROPPED_IMAGE, height,width,xPosition,yPosition,fileName,getLoggedInSessionToken());
		logger.info("croppedURL:"+croppedURL);
		return croppedURL+"&id="+Math.random();*/
		if (imageUrl == null){
			return fileName;
		}else{
			return imageUrl;
		}
	}
	@Override
	public MediaUploadDo imageFileUpload(String response) {
		MediaUploadDo mediaUploadDo = null;
		if (response != null) {
			try {
				JSONObject responseJson = new JSONObject(response);
				mediaUploadDo = JsonDeserializer.deserialize(responseJson.toString(), MediaUploadDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return mediaUploadDo;
	}
	public CollectionItemDo saveQuestionImage(String collectionItemId, String fileName) {

		CollectionItemDo collItemDo = getCollectionItem(collectionItemId);

		JsonRepresentation jsonRep = null;
		CollectionAddQuestionItemDo collectionAddQuestionItemDo=new CollectionAddQuestionItemDo();
		collectionAddQuestionItemDo.setQuestion(collItemDo.getCollectionQuestionItemDo());
		collectionAddQuestionItemDo.setMediaFileName(fileName);

		CollectionItemDo collectionItemDoNew=new CollectionItemDo();


		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_UPDATE_QUESTION_ITEM, collItemDo.getCollectionItemId());

		collItemDo.getQuestionInfo().setLicense(null);
		collItemDo.getQuestionInfo().setResourceFormat(null);
		collItemDo.getQuestionInfo().setTaxonomySet(null);
		collItemDo.getQuestionInfo().setThumbnails(null);
		collItemDo.getQuestionInfo().setResourceType(null);
		collItemDo.getQuestionInfo().setCreator(null);
		collItemDo.getQuestionInfo().setUser(null);
		collItemDo.getQuestionInfo().setCreatedOn(null);
		collItemDo.getQuestionInfo().setUrl(null);
		collItemDo.getQuestionInfo().setViews(null);
		collItemDo.getQuestionInfo().setGooruOid(null);
		collItemDo.getQuestionInfo().setAssets(null);

		TreeSet<QuestionHintsDo> treeSetHints = new TreeSet<QuestionHintsDo>();
		treeSetHints.addAll(collItemDo.getQuestionInfo().getHints());

		 Object[] objArrayHints = treeSetHints.toArray();
		 JSONArray jArrHints = new JSONArray();

		 for(int i=0; i < objArrayHints.length ; i++)
		 {
			 try {
				JSONObject jsonObjVal = new JSONObject(ResourceFormFactory.generateStringDataForm(objArrayHints[i],null));
				jArrHints.put(jsonObjVal);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}

		 }

		TreeSet<QuestionAnswerDo> treeSet = new TreeSet<QuestionAnswerDo>();
		treeSet.addAll(collItemDo.getQuestionInfo().getAnswers());

		 Object[] objArray = treeSet.toArray();
		 JSONArray jArr = new JSONArray();

		 for(int i=0; i < objArray.length ; i++)
		 {
			 try {
				JSONObject jsonObjVal = new JSONObject(ResourceFormFactory.generateStringDataForm(objArray[i],null));
				jArr.put(jsonObjVal);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}

		 }

		 collItemDo.getQuestionInfo().setAnswers(null);
		 collItemDo.getQuestionInfo().setHints(null);
		  JSONObject mainQuestionTempObj = new JSONObject();
		  JSONObject mainAnswerTempObj = new JSONObject();
		  JSONObject mainQTempObj = new JSONObject();
		  JSONObject mainHintTempObj = new JSONObject();
		  String data = "";
		 // mainQTempObj. = new LinkedHashMap();
		  try {

			  mainQuestionTempObj.put("question", ResourceFormFactory.generateStringDataForm(collItemDo.getQuestionInfo(), null));
			  JSONObject mainQuestionTempObj1 = new JSONObject(mainQuestionTempObj.get("question").toString());
			  mainAnswerTempObj.put("answer", jArr);
			  mainHintTempObj.put("hint", jArrHints);
			  mainQuestionTempObj1.put("answers", mainAnswerTempObj);
			  mainQuestionTempObj1.put("hints", mainHintTempObj);
			  data = "{\""+"question"+"\" : " + mainQuestionTempObj1.toString() +", \""+"mediaFileName"+"\" : " +fileName+"}";
			  mainQTempObj.put("question", mainQuestionTempObj1).put("mediaFileName", fileName);
			  //mainQTempObj.put("mediaFileName", fileName);

		} catch (JSONException e) {
			logger.error("Exception::", e);
		}


		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(), data);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		collectionItemDoNew=deserializeCollectionItem(jsonRep);
		collItemDo.setResource(collectionItemDoNew.getQuestionInfo());
		collItemDo.setStandards(collectionItemDoNew.getStandards());
		return collItemDo;
	}


	public CollectionItemDo convertResourceToCollectionItemDo(ResourceDo resourceDo,CollectionItemDo collectionItemDo){

		collectionItemDo.setResource(resourceDo);// replacing the update question details Do
		collectionItemDo.setCollectionQuestionItemDo(null);// removing existing collection item object details.

		return collectionItemDo;
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

	public CollectionItemDo getCollectionItem(String collectionItemId) {

		JsonRepresentation jsonRep = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_COLLLECTION_ITEM, collectionItemId);
		String url = AddQueryParameter.constructQueryParams(partialUrl,GooruConstants.INCLUDE_ADDITIONAL_INFO,GooruConstants.TRUE);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeCollectionItem(jsonRep);
	}

	@Override
	public String uploadProfileImage(String fileNameWithOurRespository,String fileName) {
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(),UrlToken.UPLOAD_PROFILE_IMAGE, getLoggedInUserUid());
		String url = AddQueryParameter.constructQueryParams(partialUrl,GooruConstants.MEDIA_FILE_NAME,fileNameWithOurRespository);
		Form form = new Form();
		form.add("sessionToken",getLoggedInSessionToken());
		ServiceProcessor.post(url, getRestUsername(),getRestPassword(),form);
		return fileName;
	}

	public String fileUploadImage(String data,String webServiceUrl)throws Exception {
		String respStr = "";
		FormData fd = new FormData("data", data);
	    FormDataSet fds = new FormDataSet();
	    String boundary = "boundary";
	    fds.setMediaType(MediaType.MULTIPART_FORM_DATA);
	    fds.setMultipartBoundary(boundary);
	    fds.setMultipart(true);
	    fds.getEntries().add(fd);
	    ClientResource c = new ClientResource(webServiceUrl);
	    Series<Header> headers = (Series<Header>)c.getRequestAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
	    if (headers == null) {
			headers = new Series<Header>(Header.class);
			c.getRequestAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, headers);
		}
	    headers.add(HEADER_GOORU_SESSION_TOKEN, getLoggedInSessionToken());
	    c.getRequestAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, headers);
		c.setEntityBuffering(true);
	    Representation cr = c.post(fds, MediaType.MULTIPART_FORM_DATA);
		respStr = cr.getText();
		return respStr;
	}
}
