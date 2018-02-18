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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.AnalyticsService;
import org.ednovo.gooru.application.server.ArrayListSorter;
import org.ednovo.gooru.application.server.annotation.ServiceURL;
import org.ednovo.gooru.application.server.request.JsonResponseRepresentation;
import org.ednovo.gooru.application.server.request.ServiceProcessor;
import org.ednovo.gooru.application.server.request.UrlToken;
import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.model.analytics.AssessmentSummaryStatusDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionProgressDataDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryUsersDataDo;
import org.ednovo.gooru.application.shared.model.analytics.FeedBackResponseDataDO;
import org.ednovo.gooru.application.shared.model.analytics.GradeJsonData;
import org.ednovo.gooru.application.shared.model.analytics.OetextDataDO;
import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;
import org.ednovo.gooru.application.shared.model.classpages.ClassDo;
import org.ednovo.gooru.shared.util.AnalyticsServiceConstants;
import org.ednovo.gooru.shared.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.StringRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

@Service("analyticsService")
@ServiceURL("/analyticsService")
public class AnalyticsServiceImpl extends BaseServiceImpl implements AnalyticsService, AnalyticsServiceConstants{

	private static final Logger logger = LoggerFactory.getLogger(AnalyticsServiceImpl.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ArrayList<CollectionProgressDataDo> getCollectionProgressData(String collectionId,String classPageId,String pathwayId) {
		JsonRepresentation jsonRep = null;
		ArrayList<CollectionProgressDataDo> collectionProgressDataList=new ArrayList<CollectionProgressDataDo>();
		String jsonStr = getCollectionProgressDataJsonStr(classPageId, pathwayId);
		String partialUrl = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_COLLECTIONPROGRESSDATA, collectionId);
		String url = AddQueryParameter.constructQueryParams(partialUrl, DATA, jsonStr);
		logger.info("getCollectionProgressData url:+--->> "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword(),true);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			try {
				collectionProgressDataList= (ArrayList<CollectionProgressDataDo>) JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONArray("content").toString(),new TypeReference<List<CollectionProgressDataDo>>() {});
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}else{
		}
		return collectionProgressDataList;
	}

	@Override
	public ArrayList<CollectionSummaryUsersDataDo> getCollectionSummaryUsersData(String classpageId) {
		JsonRepresentation jsonRep = null;
		ArrayList<CollectionSummaryUsersDataDo> collectionSummaryUsersDataDoList=new ArrayList<CollectionSummaryUsersDataDo>();
//		String dataPassing="{%22fields%22:%22userGroupUId,userName,gooruUId%22}";
		String jsonStr = getCollectionSummaryUsersDataJsonStr();
		String partialUrl = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_GETUSERSFORPATHWAY, classpageId);
		String url = AddQueryParameter.constructQueryParams(partialUrl, DATA, jsonStr);
		logger.info("getCollectionSummaryUsersData url:+"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword(),true);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			try {
				collectionSummaryUsersDataDoList= (ArrayList<CollectionSummaryUsersDataDo>) JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONArray("content").toString(),new TypeReference<List<CollectionSummaryUsersDataDo>>() {});
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}else{
		}
		return collectionSummaryUsersDataDoList;
	}


	@Override
	public ArrayList<UserDataDo> getCollectionResourceData(String collectionId,String classpageId,String pathwayId) {
		JsonRepresentation jsonRep = null;
		ArrayList<UserDataDo> collectionResourcesList=new ArrayList<UserDataDo>();
//		String dataPassing="{%22fields%22:%22answerObject,score,totalAttemptUserCount,timeSpent,views,avgTimeSpent,OE,collectionGooruOId,category,resourceGooruOId,metaData,title,questionType,options,description,options,skip,totalInCorrectCount,avgReaction,reaction,attempts,text,totalCorrectCount,itemSequence%22,%22filters%22:{%22session%22:%22AS%22,%22classId%22:%22"+classpageId+"%22,%22pathwayId%22:%22"+pathwayId+"%22},%22paginate%22:{%22sortBy%22:%22itemSequence%22,%22sortOrder%22:%22DESC%22}}";
		String jsonStr = getCollectionResourceDataJsonStr(classpageId, pathwayId);
		String partialUrl = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_GETCOLLECTIONRESOURCEDATA, collectionId);
		String url = AddQueryParameter.constructQueryParams(partialUrl, DATA, jsonStr);
		logger.info("getCollectionResourceData url:+"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword(),true);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			try {
				collectionResourcesList= (ArrayList<UserDataDo>) JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONArray("content").toString(),new TypeReference<List<UserDataDo>>() {});
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return collectionResourcesList;
	}


	@Override
	public ArrayList<CollectionSummaryUsersDataDo> getSessionsDataByUser(ClassDo classObj,String collectionId, String classId, String userId) {
		/*JsonRepresentation jsonRep = null;
		ArrayList<CollectionSummaryUsersDataDo> sessionDataList=new ArrayList<CollectionSummaryUsersDataDo>();
		String jsonString = getSessionsDataByUserJsonString(classId,userId);

//		String dataPassing ="{%22fields%22:%22%22,%22filters%22:{%22userUId%22:\""+userId+"\",%22classId%22:%22"+classId+"%22},%22paginate%22:{%22sortBy%22:%22timeStamp%22,%22sortOrder%22:%22ASC%22}}";
		String partialUrl = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_GETSESSIONSDATABYUSER, classObj.getAssessmentId());
		String url = AddQueryParameter.constructQueryParams(partialUrl, DATA, jsonString);

		String newurl= UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_GETSESSIONSDATABYUSER,classObj.getSessionId(), classObj.getAssessmentId());

		logger.info("getSessionsDataByUser --newurl:+------ "+newurl);
		logger.info("getSessionsDataByUser --url:+------ "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword(),true);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			try {
				logger.info("jsonRep.getSize():+------ "+jsonRep.getSize());
				if(jsonRep!=null&& jsonRep.getSize()!=-1){
					if(!jsonRep.getJsonObject().isNull("content")){
						sessionDataList= (ArrayList<CollectionSummaryUsersDataDo>) JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONArray("content").toString(),new TypeReference<List<CollectionSummaryUsersDataDo>>() {});
					}
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}else{
		}
		return sessionDataList;*/
		return new ArrayList<CollectionSummaryUsersDataDo>();
	}


	@Override
	public ArrayList<UserDataDo> getUserSessionDataByUser(ClassDo classObj,String collectionId,String classId, String userId,String sessionId,String pathwayId) {
		JsonRepresentation jsonRep = null;
		ArrayList<UserDataDo> collectionResourcesList=new ArrayList<UserDataDo>();
//		String dataPassing ="{%22fields%22:%22answerObject,score,totalAttemptUserCount,timeSpent,views,avgTimeSpent,OE,collectionGooruOId,category,resourceGooruOId,metaData,title,questionType,options,description,options,skip,totalInCorrectCount,avgReaction,reaction,attempts,text,totalCorrectCount,itemSequence%22,%22filters%22:{%22userUId%22:%22"+userId+"%22,%22session%22:%22CS%22,%22sessionId%22:%22"+sessionId+"%22,%22pathwayId%22:%22"+pathwayId+"%22,%22classId%22:%22"+classId+"%22},%22paginate%22:{%22sortBy%22:%22itemSequence%22,%22sortOrder%22:%22ASC%22}}&timestamp=1410757700537";
		String jsonString = getUserSessionDataByUserJsonStr(classId, userId, sessionId, pathwayId);
		Map<String,String> params = new HashMap<String, String>();
		params.put(DATA, jsonString);
		params.put(TIME_STAMP_LOWER_CASE, TIME_STAMP_LOWER_CASE_VALUE);
		String url = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_GETSESSIONDATABYUSERSESSION, collectionId,userId,classObj.getClassId(),classObj.getCourseId(),classObj.getUnitId(),classObj.getLessonId(),sessionId);
		//String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		logger.info("getUserSessionDataByUser url:+--- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword(),true);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		logger.info("getUserSessionDataByUser statuscode::"+jsonResponseRep.getStatusCode());
		try{
		logger.info("jsonres--"+jsonRep.getJsonObject());
		}catch(Exception e){
		e.printStackTrace();
		}
		if(jsonResponseRep.getStatusCode()==200){
			logger.info("getUserSessionDataByUser 200statuscode"+jsonResponseRep.getStatusCode());
			try {
				collectionResourcesList= (ArrayList<UserDataDo>) JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONArray("content").toString(),new TypeReference<ArrayList<UserDataDo>>() {});
				Collections.sort(collectionResourcesList, new ArrayListSorter("sequence", true));
				logger.info("collectionResourcesList--"+collectionResourcesList);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return collectionResourcesList;
	}



	@Override
	public ArrayList<CollectionSummaryMetaDataDo> getCollectionMetaDataByUserAndSession(ClassDo classObj,
			String collectionId, String classId, String userId, String sessionId) {

		JsonRepresentation jsonRep = null;
		JsonRepresentation jsonRep2 = null;
		ArrayList<CollectionSummaryMetaDataDo> collectionSummaryMetaDataDoList=new ArrayList<CollectionSummaryMetaDataDo>();
		String jsonString = getCollMetaDataByUserAndSessionJsonStr(classId,userId, sessionId);
		//		String dataPassing="{%22fields%22:%22thumbnail,userCount,lastAccessed,completionStatus,timeSpent,views,avgTimeSpent,OE,gooruOId,title,description,options,skip,score,avgReaction,totalQuestionCount,gradeInPercentage%22,%22filters%22:{%22userUId%22:%22"+userId+"%22,%22session%22:%22CS%22,%22sessionId%22:%22"+sessionId+"%22,%22classId%22:%22"+classId+"%22}}";
		//String partialUrl = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_GETCOLLECTIONMETADATA, collectionId);
		//String url = AddQueryParameter.constructQueryParams(partialUrl, DATA, jsonString);

		String url = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_GETCOLLECTIONMETADATA,sessionId,collectionId);
		logger.info(" getCollectionMetaDataByUserAndSession url:+--- "+url);

		String url2 = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_GETSESSIONSDATABYUSER,collectionId,userId,classObj.getClassId(),classObj.getCourseId(),classObj.getUnitId(),classObj.getLessonId(),sessionId);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword(),true);
		jsonRep = jsonResponseRep.getJsonRepresentation();

		try {

			if(getSessionStatus(url)){
				logger.info("getCollectionMetaDataByUserAndSession url2:+--- "+url2);
				JsonResponseRepresentation jsonResponseRep2 = ServiceProcessor.get(url2, getRestUsername(), getRestPassword(),true);
				jsonRep2 = jsonResponseRep2.getJsonRepresentation();

				collectionSummaryMetaDataDoList= (ArrayList<CollectionSummaryMetaDataDo>) JsonDeserializer.deserialize(jsonRep2.getJsonObject().getJSONArray("content").toString(),new TypeReference<List<CollectionSummaryMetaDataDo>>() {});

				logger.info("API collectionSummaryMetaDataDoList--"+collectionSummaryMetaDataDoList.size());
			}

		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		logger.info("end collectionSummaryMetaDataDoList--"+collectionSummaryMetaDataDoList.size());
		return  collectionSummaryMetaDataDoList;
	}

	public boolean getSessionStatus(final String url) {

		boolean status = true;
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword(), true);
		JsonRepresentation jsonSessionStatusRep = jsonResponseRep.getJsonRepresentation();
		String messageSessionStatus=null;
		try {
			messageSessionStatus = jsonSessionStatusRep.getJsonObject()
					.getJSONObject("message").getString("status");
		} catch (JSONException e) {
			AppClientFactory
					.printSevereLogger("Error while getting status from JSON object");
		}
		status = messageSessionStatus != null && "completed".equalsIgnoreCase(messageSessionStatus) ? true : false;
		logger.info("last return--" + status);
		return status;

	}

	@Override
	public String setHTMLtoPDF(String htmlString,String fileName,boolean isClickedOnEmail) {
		String pdfName = "";
		try {
			fileName = URLEncoder.encode(fileName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception::", e);
		}
		pdfName = fileName + PDF_SUFFIX;
		String savedFileName=null;
		StringRepresentation stringRepresentation= null;
		String downloadUrl="";
		try{
			//String url = "http://www.goorulearning.org/gooruapi/rest/v2/media/htmltopdf?sessionToken=aec96f9c-42df-11e4-8d6c-123141016e2a";
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GENERATE_PDF,getLoggedInSessionToken());
			//To disable escape sequence enabled this line
			htmlString=htmlString.replaceAll("max-height: 100%;", "");
			htmlString = htmlString.replaceAll("[\n\r]", "<br>");
			String jsonStr="{\"fileName\":\"Mymedia\",\"html\":\""+htmlString+"\"}";
			//String jsonStr = setHTMLtoPDFJsonStr(htmlString);
			logger.info("html to pdf url-- "+url);
			logger.info("html to pdf url json -- "+jsonStr);
			stringRepresentation = ServiceProcessor.postString(url, getRestUsername(), getRestPassword(),jsonStr);
			savedFileName=stringRepresentation.getText();

			if(isClickedOnEmail){
				downloadUrl=savedFileName;
			}else{
				downloadUrl=UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_DOWNLOADFILE,savedFileName,pdfName,getLoggedInSessionToken());
			}
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return downloadUrl;
	}

	@Override
	public ArrayList<GradeJsonData> getAnalyticsGradeData(String classpageId,String pathwayId) {
		JsonRepresentation jsonRep = null;
		ArrayList<GradeJsonData> collectionResourcesList=new ArrayList<GradeJsonData>();
//		String dataPassing ="{%22fields%22:%22timeSpent,score,gradeInPercentage,totalQuestionCount,avgTimeSpent,resourceGooruOId,gooruUId,userName,userData,gooruOId,title%22,%22filters%22:{%22session%22:%22AS%22,%22userUId%22:%22%22,%22collectionGooruOId%22:%22%22,%22pathwayId%22:%22"+pathwayId+"%22},%22paginate%22:{%22sortBy%22:%22itemSequence%22,%22sortOrder%22:%22ASC%22}}";
		String jsonStr = getAnalyticsGradeDatajsonStr(pathwayId);
		String partialUrl = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_GETGRADEJSON, classpageId);
		String url = AddQueryParameter.constructQueryParams(partialUrl, DATA, jsonStr);
		logger.info("getAnalyticsGradeData url:+"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword(),true);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			try {
				String boolVal="";
				if(!jsonRep.getJsonObject().isNull("message")){
					JSONObject messageObj=jsonRep.getJsonObject().getJSONObject("message");
					if(messageObj!=null)
					{
					boolVal=messageObj.getString("aggregateData");
					if(boolVal.equalsIgnoreCase("true")){
						collectionResourcesList= (ArrayList<GradeJsonData>) JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONArray("content").toString(),new TypeReference<List<GradeJsonData>>() {});
					}else{
						GradeJsonData gradeData=new GradeJsonData();
						gradeData.setAggregateData(boolVal);
						collectionResourcesList.add(gradeData);
					}
					}
				}

			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}else{
		}
		return collectionResourcesList;
	}

	@Override
	public String exportPathwayOE(String classpageId, String pathwayId,String timeZone) {
//		String dataPassing ="{%22fields%22:%22%22,%22filters%22:{%22session%22:%22FS%22,%22sessionId%22:%22%22,%22userUId%22:%22%22,%22classId%22:%22%22,%22collectionGooruOId%22:%22%22,%22pathwayId%22:%22"+pathwayId+"%22},%22paginate%22:{%22sortBy%22:%22%22,%22sortOrder%22:%22%22}}";
		String jsonStr = exportPathwayOEJsonStr(pathwayId);
		String partialUrl = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_EXPORTOEPATHWAY, classpageId, getLoggedInSessionToken());
		Map<String, String> params = new HashMap<String, String>();
		params.put(TIME_ZONE, timeZone);
		params.put(DATA, jsonStr);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		logger.info("exportPathwayOE url:+"+url);
		return url;
	}


	@Override
	public ArrayList<GradeJsonData>  getBottomAndTopScoresData(String collectionId, String classId,
			String pathwayId,String sortOrder) {
		JsonRepresentation jsonRep = null;
		ArrayList<GradeJsonData> collectionResourcesList=new ArrayList<GradeJsonData>();
//		String dataPassing ="{%22fields%22:%22timeSpent,firstName,lastName,emailId,profileUrl,score,gradeInPercentage,totalQuestionCount,avgTimeSpent,resourceGooruOId,gooruUId,userName,userData,gooruOId,title%22,%22filters%22:{%22session%22:%22FS%22,%22userUId%22:%22%22,%22collectionGooruOId%22:%22"+collectionId+"%22,%22pathwayId%22:%22"+pathwayId+"%22},%22paginate%22:{%22sortBy%22:%22itemSequence,gradeInPercentage%22,%22sortOrder%22:%22"+sortOrder+"%22,%22totalRecords%22:3}}";
		String jsonStr = getBottomAndTopScoresDatajsonStr(collectionId,pathwayId,sortOrder);
		String partialUrl = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_GETGRADEJSON, classId);
		String url = AddQueryParameter.constructQueryParams(partialUrl, DATA, jsonStr);
		logger.info("getBottomAndTopScoresData url:+"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword(),true);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			try {
				collectionResourcesList= (ArrayList<GradeJsonData>) JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONArray("content").toString(),new TypeReference<List<GradeJsonData>>() {});
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}else{
		}
		return collectionResourcesList;
	}


	@Override
	public CollectionSummaryMetaDataDo getAssignmentAverageData(String classId,String unitId,String collectionId){

		JsonRepresentation jsonRep = null;
		CollectionSummaryMetaDataDo collectionSummaryMetaDataDo=null;
		String urlDataParameterValue=createJsonPayloadObject(unitId,classId,"",FIELDS_ASSIGNMENT_AVERAGE_DATA);
		String  partialUrl= UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_OLDGETCOLLECTIONMETADATA, collectionId);
		String url = AddQueryParameter.constructQueryParams(partialUrl, DATA, urlDataParameterValue);
		logger.info("getAssignmentAverageData url==>"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword(),true);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			try {
				JSONArray jsonArray=jsonRep.getJsonObject().getJSONArray("content");
				if(jsonArray!=null&&jsonArray.length()>0){
					collectionSummaryMetaDataDo=JsonDeserializer.deserialize(jsonArray.getJSONObject(0).toString(),new TypeReference<CollectionSummaryMetaDataDo>(){});
					logger.info("JSON_RESPONSE==>"+jsonArray.getJSONObject(0).toString());
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return collectionSummaryMetaDataDo;
	}

	public String createJsonPayloadObject(String unitId,String classId,String userUid,String requiredFields){
		JSONObject jsonDataObject=new JSONObject();
		try {
			jsonDataObject.put(FIELDS, requiredFields);

			JSONObject filtersJsonObject=new JSONObject();
			filtersJsonObject.put(SESSION, "AS");
			filtersJsonObject.put(USER_UID, userUid);
			filtersJsonObject.put(CLASSID, classId);
			filtersJsonObject.put(PATHWAYID, unitId);

			jsonDataObject.put(FILTERS, filtersJsonObject);

			JSONObject paginateJsonObject=new JSONObject();
			paginateJsonObject.put(SORTBY, ITEMSEQUENCE);
			paginateJsonObject.put(SORTORDER, ASCENDING);

			jsonDataObject.put(PAGINATE, paginateJsonObject);

		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return jsonDataObject.toString();
	}

	private ArrayList<OetextDataDO> deserializeOETextResponse(JSONArray jsonArray) {
		ArrayList<OetextDataDO> oetextlistObj=new ArrayList<OetextDataDO>();
		if(jsonArray.length()!=0){
			for (int i=0;i<jsonArray.length();i++) {
				try {
					JSONObject jsonObject=(JSONObject) jsonArray.get(i);
					OetextDataDO oetextDataDOObj=new OetextDataDO();
					if(!jsonObject.isNull("answerObject"))
					oetextDataDOObj.setAnswerObject(jsonObject.getString("answerObject"));

					if(!jsonObject.isNull("feedbackStatus"))
					oetextDataDOObj.setFeedbackStatus(jsonObject.getString("feedbackStatus"));

					if(!jsonObject.isNull("feedbackText"))
					oetextDataDOObj.setFeedbackText(jsonObject.getString("feedbackText"));

					if(!jsonObject.isNull("feedbackTimestamp"))
					oetextDataDOObj.setFeedbackTimestamp(jsonObject.getLong("feedbackTimestamp"));

					if(!jsonObject.isNull("feedbackProviderUId"))
					oetextDataDOObj.setFeedbackProviderUId(jsonObject.getString("feedbackProviderUId"));

					if(!jsonObject.isNull("gooruUId"))
					oetextDataDOObj.setGooruUId(jsonObject.getString("gooruUId"));

					if(!jsonObject.isNull("OEText"))
					oetextDataDOObj.setOEText(jsonObject.getString("OEText"));

					if(!jsonObject.isNull("organizationUId"))
					oetextDataDOObj.setOrganizationUId(jsonObject.getString("organizationUId"));

					if(!jsonObject.isNull("status"))
					oetextDataDOObj.setStatus(jsonObject.getInt("status"));

					if(!jsonObject.isNull("userGroupUId"))
					oetextDataDOObj.setUserGroupUId(jsonObject.getString("userGroupUId"));

					if(!jsonObject.isNull("userName"))
					oetextDataDOObj.setUserName(jsonObject.getString("userName"));

					oetextlistObj.add(oetextDataDOObj);
				} catch (JSONException e) {
					logger.error("Exception::", e);
				}
			}
		}
		return oetextlistObj;
	}

	@Override
	public FeedBackResponseDataDO postTeacherFeedBackToStudent(String freeText,
			String resourceId, String collectionId, String classpageId,	String pathwayId, String userId, String session,String contentItemId,String parentItemId,String classCode,String feedbackProvidedUserUid) {
		JsonRepresentation jsonRep = null;
		FeedBackResponseDataDO feedBackResponseDataDO=new FeedBackResponseDataDO();
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.CREATE_SESSION_ITEM, ("AS".equals(session)?"0":session));
		//String url ="http://www.goorulearning.org/gooruapi/rest/v2/session/AS/item/feedback?sessionToken=08a99a16-4ea5-11e4-8d6c-123141016e2a";
		logger.info("postTeacherFeedBackToStudent URL --- :"+url);
		JSONObject mainObj=new JSONObject();
		JSONObject userObj=new JSONObject();
		JSONObject setPlayLoadObj=new JSONObject();
		try {


			setPlayLoadObj.put("classCode", classCode);
			setPlayLoadObj.put("pathwayId", pathwayId);
			setPlayLoadObj.put("classId",classpageId);
			setPlayLoadObj.put("sessionId",session);
			setPlayLoadObj.put("userId", userId);
			setPlayLoadObj.put("collectionId", collectionId);
			if(contentItemId.equalsIgnoreCase("commentsDelete")){
				setPlayLoadObj.put("active","false");
			}else{
				setPlayLoadObj.put("active","true");
			}

			mainObj.put("contentGooruId",resourceId);
//			mainObj.put("contentItemId",contentItemId);
			mainObj.put("feedbackProvidedUserUid",feedbackProvidedUserUid);
			if(!"AS".equals(session)){
				mainObj.put("sessionActivityId", session);
			}
			mainObj.put("parentGooruId",collectionId);
//			mainObj.put("parentItemId",parentItemId);
			mainObj.put("feedbackText",freeText);
			mainObj.put("payLoadObject",setPlayLoadObj.toString());
//			mainObj.put("user",userObj);
			logger.info("postTeacherFeedBackToStudent payload --- "+mainObj.toString());
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(),mainObj.toString());
			jsonRep = jsonResponseRep.getJsonRepresentation();
			if(jsonResponseRep.getStatusCode()==200){
				feedBackResponseDataDO=deserializeTeacherResponse(jsonRep.getJsonObject(),freeText);
			}else{
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return feedBackResponseDataDO;
	}

	public static long getTodayDateInMilliSeconds(){
		Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.DATE);
		long timeInMilliSeconds = cal.getTime().getTime();
		return timeInMilliSeconds;
	}

	private FeedBackResponseDataDO deserializeTeacherResponse(
			JSONObject jsonObject, String freeText) {
		FeedBackResponseDataDO feedBackResponseDataDO=new FeedBackResponseDataDO();
		try {
			feedBackResponseDataDO.setCreatedOn(getTodayDateInMilliSeconds());
			feedBackResponseDataDO.setFreeText(freeText);
		} catch (Exception e) {
				logger.error("Exception::", e);
			}
		return feedBackResponseDataDO;
	}

	@Override
	public ArrayList<OetextDataDO> getOETextData(String resourceId,String collectionId, String classpageId, String pathwayId,String session, String sessionId, String userUId) {
		JsonRepresentation jsonRep = null;
		ArrayList<OetextDataDO> collectionResourcesList=new ArrayList<OetextDataDO>();
//		String dataPassing ="{%22fields%22:%22feedbackStatus,userName,OEText,gooru_uid,feedbackText,feedbackProviderUId,feedbackTimestamp,answerObject%22,%22filters%22:{%22resourceGooruOId%22:%22"+resourceId+"%22,%22classId%22:%22"+classpageId+"%22,%22pathwayId%22:%22"+pathwayId+"%22,%22session%22:%22"+session+"%22,%22sessionId%22:%22"+sessionId+"%22,%22userUId%22:%22"+userUId+"%22}}";
		String jsonStr = getOETextDataJsonStr(resourceId, classpageId, pathwayId, session, sessionId, userUId);
		String partialUrl = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_OETEXTJSON, collectionId);
		String url = AddQueryParameter.constructQueryParams(partialUrl, DATA, jsonStr);
		logger.info("getOETextData url:+"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword(),true);
		jsonRep = jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			try {
				collectionResourcesList=deserializeOETextResponse(jsonRep.getJsonObject().getJSONArray("content"));
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}else{
		}
		return collectionResourcesList;
	}
	@Override
	public HashMap<String,String> getResourceAndCollectionCounts(String Id,String searchType){
		try {
			String requiredFields=VIEWSCOUNT+","+COPYCOUNT+","+RESOURCEADDED+","+RESOURCEUSEDUSER+","+RESOURCEADDEDPUBLIC+","+RESOURCEUSEDUSERPUBLIC;
			JsonRepresentation jsonRep = null;
			String  partialUrl= UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V2_GET_VIEW_COUNTS, getLoggedInSessionToken(),Id);
			String url = AddQueryParameter.constructQueryParams(partialUrl, FIELDS, requiredFields);
			logger.info("getResourceAndCollectionCounts url:+"+url);
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword(),true);
			jsonRep = jsonResponseRep.getJsonRepresentation();
			if(jsonResponseRep.getStatusCode()==200 && !jsonRep.getJsonObject().isNull("content")){
				JSONArray jsonArray=jsonRep.getJsonObject().getJSONArray("content");
				if(jsonArray!=null&&jsonArray.length()>0){
					return deserializeViewsCount(jsonArray);
				}
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return new HashMap<String, String>();
	}
	public HashMap<String,String> deserializeViewsCount(JSONArray jsonArray){
		HashMap<String, String> resultMap=new HashMap<String, String>();
		try {
			JSONObject jsonObj=jsonArray.getJSONObject(0);
			resultMap.put("copyCount", Integer.toString(jsonObj.getInt("copyCount")));
			resultMap.put("viewsCount", Integer.toString(jsonObj.getInt("viewsCount")));
			resultMap.put("resourceAdded", Integer.toString(jsonObj.getInt("resourceAddedPublic")));
		}catch(JSONException e){
			logger.error("Exception::", e);
		}
		return resultMap;
	}
	@Override
	public void sendEmail(String to, String subject, String message,String displayName, String fileName, String path) {
		JsonRepresentation jsonRep = null;
		//String url = "http://www.goorulearning.org/gooruapi/rest/v2/share/mail?sessionToken=5ef6d576-663a-11e4-a2ea-123141016e2a";
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_SEND_EMAIL_WITH_PDF,getLoggedInSessionToken());
		logger.info("sendEmail url:+"+url);
		JSONObject mainObj=new JSONObject();
		JSONObject attachmentObj=new JSONObject();
		JSONArray setPlayLoadObj=new JSONArray();
		try {
			attachmentObj.put("fileName", fileName);
			attachmentObj.put("url", path);

			setPlayLoadObj.put(attachmentObj);

			mainObj.put("to",to);
			mainObj.put("subject",subject);
			mainObj.put("message",message);
			mainObj.put("fromDisplayName",displayName);
			mainObj.put("attachment",setPlayLoadObj.toString());

			logger.info("mainObj.toString()::"+mainObj.toString());
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(),mainObj.toString());
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
	}

	/**
	 * Generates the json string.
	 *
	 * @param classId {@link String}
	 * @param userId {@link String}
	 * @return dataObj {@link String}
	 */
	private String getSessionsDataByUserJsonString(String classId, String userId) {
		String classpageId = StringUtil.isEmpty(classId) ? "" : classId;
		String user_id = StringUtil.isEmpty(userId) ? "" : userId;

		JSONObject filtersObj = new JSONObject();
		JSONObject paginateObj = new JSONObject();
		JSONObject dataObj = new JSONObject();
		try {

			filtersObj.put(USER_UID, user_id);
			filtersObj.put(CLASS_ID, classpageId);
			paginateObj.put(SORTBY, TIME_STAMP);
			paginateObj.put(SORT_ORDER, ASC);
			dataObj.put(FIELDS, "timeStamp,sessionId,frequency");
			dataObj.put(FILTERS, filtersObj);
			dataObj.put(PAGINATE, paginateObj);

		}catch (Exception e) {
			logger.error("Exception::", e);
		}
		return dataObj.toString();
	}


	/**
	 * Constructing jason string
	 *
	 * @param classId {@link String}
	 * @param userId {@link String}
	 * @param sessionId {@link String}
	 * @return dataObj {@link String}
	 */
	private String getCollMetaDataByUserAndSessionJsonStr(String classId,String userId, String sessionId) {

		String classpageId = StringUtil.isEmpty(classId) ? "" : classId;
		String user_id = StringUtil.isEmpty(userId) ? "" : userId;
		String session_id = StringUtil.isEmpty(sessionId) ? "" : sessionId;

		JSONObject filtersObj = new JSONObject();
		JSONObject dataObj = new JSONObject();
		try {
			filtersObj.put(USER_UID, user_id);
			filtersObj.put(SESSION, SESSION_CS);
			filtersObj.put(SESSION_ID, session_id);
			filtersObj.put(CLASS_ID, classpageId);
			dataObj.put(FIELDS, FIELDS_METADATA_BY_USR_AND_SESSION);
			dataObj.put(FILTERS, filtersObj);


		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return dataObj.toString();
	}


	/**
	 * Constructing jason string
	 *
	 * @param classId {@link String}
	 * @param userId {@link String}
	 * @param sessionId {@link String}
	 * @param pathwayId {@link String}
	 *
	 * @return dataObj {@link String}
	 */
	private String getUserSessionDataByUserJsonStr(String classId,String userId, String sessionId, String pathwayId) {
		String classpageId = StringUtil.isEmpty(classId) ? "" : classId;
		String user_id = StringUtil.isEmpty(userId) ? "" : userId;
		String session_id = StringUtil.isEmpty(sessionId) ? "" : sessionId;
		String pathway_id = StringUtil.isEmpty(pathwayId) ? "" : pathwayId;

		JSONObject filtersObj = new JSONObject();
		JSONObject paginateObj = new JSONObject();
		JSONObject dataObj = new JSONObject();
		try {
			filtersObj.put(PATHWAYID, pathway_id);
			filtersObj.put(CLASS_ID, classpageId);
			filtersObj.put(SESSION, SESSION_CS);
			filtersObj.put(SESSION_ID, session_id);
			filtersObj.put(USER_UID, user_id);
			paginateObj.put(SORTBY, ITEMSEQUENCE);
			paginateObj.put(SORTORDER, ASCENDING);
			dataObj.put(FILTERS, filtersObj);
			dataObj.put(PAGINATE, paginateObj);
			dataObj.put(FIELDS, FIELDS_USER_SESSION_DATA_BY_USER);

		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return dataObj.toString();
	}


	/**
	 * Constructing jason string
	 *
	 * @param classId {@link String}
	 * @param pathwayId {@link String}
 	 * @return dataObj {@link String}
	 */
	private String getCollectionProgressDataJsonStr(String classId,String pathwayId) {
		String classpageId = StringUtil.isEmpty(classId) ? "" : classId;
		String pathway_id = StringUtil.isEmpty(pathwayId) ? "" : pathwayId;

		JSONObject filtersObj = new JSONObject();
		JSONObject paginateObj = new JSONObject();
		JSONObject dataObj = new JSONObject();
		try {
			filtersObj.put(PATHWAYID, pathway_id);
			filtersObj.put(CLASS_ID, classpageId);
			filtersObj.put(SESSION, SESSION_FS);
			paginateObj.put(SORTORDER, ASCENDING);
			paginateObj.put(SORTBY, ITEMSEQUENCE);
			dataObj.put(PAGINATE, paginateObj);
			dataObj.put(FILTERS,filtersObj);
			dataObj.put(FIELDS,FIELDS_COLL_PROG_DATA);

		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return dataObj.toString();
	}



	/**
	 * Constructing json string
	 *
	 * @return dataObj {@link String}
	 */
	private String getCollectionSummaryUsersDataJsonStr() {
		JSONObject dataObj = new JSONObject();
		try {
			dataObj.put(FIELDS,FIELDS_COLL_SUMMARY_USERS_DATA);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}

		return dataObj.toString();
	}

	/**
	 * Constructing json string
	 *
	 * @param classpageId {@link String}
	 * @param pathwayId {@link String}
	 *
	 * @return dataObj {@link String}
	 */
	private String getCollectionResourceDataJsonStr(String classId,String pathwayId) {
		String classpageId = StringUtil.isEmpty(classId) ? "" : classId;
		String pathway_id = StringUtil.isEmpty(pathwayId) ? "" : pathwayId;

		JSONObject filtersObj = new JSONObject();
		JSONObject paginateObj = new JSONObject();
		JSONObject dataObj = new JSONObject();
		try {
			filtersObj.put(PATHWAYID, pathway_id);
			filtersObj.put(CLASS_ID, classpageId);
			filtersObj.put(SESSION, SESSION_AS);
			paginateObj.put(SORTORDER, DESCENDING);
			paginateObj.put(SORTBY, ITEMSEQUENCE);
			dataObj.put(PAGINATE, paginateObj);
			dataObj.put(FILTERS,filtersObj);
			dataObj.put(FIELDS,FIELDS_USER_SESSION_DATA_BY_USER);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return dataObj.toString();
	}


	/**
	 * Constructing json string
	 *
	 * @param htmlString {@link String}
	 * @return dataObj {@link String}
	 */
	private String setHTMLtoPDFJsonStr(String htmlString) {
		String html_str = StringUtil.isEmpty(htmlString)?"":htmlString;
		JSONObject dataObj = new JSONObject();
		try {
			dataObj.put(FILE_NAME, FILE_NAME_VALUE);
			dataObj.put(HTML, html_str);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return dataObj.toString();
	}

	/**
	 * Constructing json string
	 *
	 * @param classpageId {@link String}
	 * @param pathwayId {@link String}
	 * @return dataObj {@link String}
	 */
	private String getAnalyticsGradeDatajsonStr(String pathwayId) {
		String pathway_id = StringUtil.isEmpty(pathwayId) ? "" : pathwayId;

		JSONObject filtersObj = new JSONObject();
		JSONObject paginateObj = new JSONObject();
		JSONObject dataObj = new JSONObject();
		try {
			filtersObj.put(PATHWAYID, pathway_id);
			filtersObj.put(COLLECTIONGOORUOID, "");
			filtersObj.put(USER_UID, "");
			filtersObj.put(SESSION, SESSION_AS);

			paginateObj.put(SORTORDER, ASCENDING);
			paginateObj.put(SORTBY, ITEMSEQUENCE);

			dataObj.put(PAGINATE, paginateObj);
			dataObj.put(FILTERS, filtersObj);
			dataObj.put(FIELDS, FIELDS_ANALYTICS_GRADE_DATA);

		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return dataObj.toString();
	}


	/**
	 * Constructing json string
	 *
	 * @param resourceId {@link String}
	 * @param classpageId {@link String}
	 * @param pathwayId {@link String}
	 * @param session {@link String}
	 * @param sessionId {@link String}
	 * @param userUId {@link String}
	 *
	 * @return dataObj {@link String}
	 */
	private String getOETextDataJsonStr(String resourceId, String classId,String pathwayId, String session, String sessionId, String userUId) {
		String resource_Id = StringUtil.isEmpty(resourceId) ? "" : resourceId;
		String classpageId = StringUtil.isEmpty(classId) ? "" : classId;
		String pathway_id = StringUtil.isEmpty(pathwayId) ? "" : pathwayId;
		String session_val = StringUtil.isEmpty(session) ? "" : session;
		String session_Id = StringUtil.isEmpty(sessionId) ? "" : sessionId;
		String user_uid = StringUtil.isEmpty(userUId) ? "" : userUId;

		JSONObject filtersObj = new JSONObject();
		JSONObject dataObj = new JSONObject();
		try {
			filtersObj.put(USER_UID, user_uid);
			filtersObj.put(SESSION_ID, session_Id);
			filtersObj.put(SESSION, session_val);
			filtersObj.put(PATHWAYID, pathway_id);
			filtersObj.put(CLASS_ID, classpageId);
			filtersObj.put(RES_GOORU_OID, resource_Id);
			dataObj.put(FILTERS, filtersObj);
			dataObj.put(FIELDS, FIELDS_OE_TXT_DATA);

		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return dataObj.toString();
	}

	/**
	 * Constructing json string
	 *
	 * @param pathwayId {@link String}
	 *
	 * @return dataObj {@link String}
	 */
	private String exportPathwayOEJsonStr(String pathwayId) {
		String pathway_id = StringUtil.isEmpty(pathwayId) ? "" : pathwayId;
		JSONObject filtersObj = new JSONObject();
		JSONObject paginateObj = new JSONObject();
		JSONObject dataObj = new JSONObject();
		try {
			filtersObj.put(PATHWAYID, pathway_id);
			filtersObj.put(COLLECTIONGOORUOID, "");
			filtersObj.put(CLASS_ID, "");
			filtersObj.put(USERUID, "");
			filtersObj.put(SESSION_ID, "");
			filtersObj.put(SESSION, SESSION_FS);
			paginateObj.put(SORTORDER, "");
			paginateObj.put(SORTBY, "");
			dataObj.put(PAGINATE, paginateObj);
			dataObj.put(FILTERS, filtersObj);
			dataObj.put(FIELDS, "");

		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return dataObj.toString();
	}

	/**
	 * Constructing json string
	 *
	 * @param collectionId {@link String}
	 * @param pathwayId {@link String}
	 * @param sortOrder {@link String}
	 *
	 * @return dataObj {@link String}
	 */
	private String getBottomAndTopScoresDatajsonStr(String collectionId,String pathwayId, String sortOrder) {
		String coll_id = StringUtil.isEmpty(collectionId) ? "" : collectionId;
		String pathway_id = StringUtil.isEmpty(pathwayId) ? "" : pathwayId;
		String sort_order = StringUtil.isEmpty(sortOrder) ? "" : sortOrder;

		JSONObject filtersObj = new JSONObject();
		JSONObject paginateObj = new JSONObject();
		JSONObject dataObj = new JSONObject();
		try {
			filtersObj.put(PATHWAYID, pathway_id);
			filtersObj.put(COLLECTIONGOORUOID, coll_id);
			filtersObj.put(USERUID, "");
			filtersObj.put(SESSION, SESSION_FS);
			paginateObj.put(TOTAL_REC, 3);
			paginateObj.put(SORTORDER, sort_order);
			paginateObj.put(SORTBY, SORT_BY_BOTTOM_AND_TOP_SCORES);
			dataObj.put(PAGINATE, paginateObj);
			dataObj.put(FILTERS, filtersObj);
			dataObj.put(FIELDS, FIELDS_BOTTOM_AND_TOP_SCORES);

		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return dataObj.toString();
	}

	/**
	 * Constructing json string
	 *
	 * @param collectionGooruOId {@link String}
	 * @param classId {@link String}
	 * @return dataObj {@link String}
	 */
	private String exportTeacherSummaryJsonStr(String collectionId,String classId) {
		String coll_id = StringUtil.isEmpty(collectionId) ? "" : collectionId;
		String classpageId = StringUtil.isEmpty(classId) ? "" : classId;

		JSONObject filtersObj = new JSONObject();
		JSONObject paginateObj = new JSONObject();
		JSONObject dataObj = new JSONObject();
		try {
			filtersObj.put(COLLECTIONGOORUOID, coll_id);
			filtersObj.put(CLASS_ID, classpageId);
			filtersObj.put(USERUID, "");
			filtersObj.put(SESSION_ID, "");
			filtersObj.put(SESSION, SESSION_AS);
			paginateObj.put(SORTORDER, "");
			paginateObj.put(SORTBY, "");
			dataObj.put(PAGINATE, paginateObj);
			dataObj.put(FILTERS, filtersObj);
			dataObj.put(FIELDS, "");
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return dataObj.toString();
	}

	/**
	 * Constructing json string
	 *
	 * @param collectionId {@link String}
	 * @return dataObj {@link String}
	 */
	private String exportProgressJsonStr(String collectionId) {
		String coll_id = StringUtil.isEmpty(collectionId) ? "" : collectionId;

		JSONObject filtersObj = new JSONObject();
		JSONObject paginateObj = new JSONObject();
		JSONObject dataObj = new JSONObject();
		try {
			filtersObj.put(COLLECTIONGOORUOID, coll_id);
			filtersObj.put(CLASS_ID, "");
			filtersObj.put(USERUID, "");
			filtersObj.put(SESSION_ID, "");
			filtersObj.put(SESSION, SESSION_FS);
			paginateObj.put(SORTORDER, "");
			paginateObj.put(SORTBY, "");
			dataObj.put(PAGINATE, paginateObj);
			dataObj.put(FILTERS, filtersObj);
			dataObj.put(FIELDS, "");
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return dataObj.toString();
	}

	@Override
	public String exportTeacherSummary(String collectionGooruOId,String pathwayId, String classId,String timeZone) {
//		String dataPassing ="{%22fields%22:%22%22,%22filters%22:{%22session%22:%22AS%22,%22sessionId%22:%22%22,%22userUId%22:%22%22,%22classId%22:%22"+classId+"%22,%22collectionGooruOId%22:%22"+collectionGooruOId+"%22},%22paginate%22:{%22sortBy%22:%22%22,%22sortOrder%22:%22%22}}";
		String jsonStr = exportTeacherSummaryJsonStr(collectionGooruOId,classId);
		String partialUrl = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_EXPORTSUMMARYATHWAY, classId,getLoggedInSessionToken());
		Map<String,String> params = new HashMap<String, String>();
		params.put(DATA, jsonStr);
		params.put(TIME_ZONE, timeZone);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		logger.info("exportTeacherSummary url:+"+url);
		return url;
	}

	@Override
	public String exportProgress(String collectionId, String classpageId,String timeZone) {
//		String dataPassing ="{%22fields%22:%22%22,%22filters%22:{%22session%22:%22FS%22,%22sessionId%22:%22%22,%22userUId%22:%22%22,%22classId%22:%22%22,%22collectionGooruOId%22:%22"+collectionId+"%22},%22paginate%22:{%22sortBy%22:%22%22,%22sortOrder%22:%22%22}}";
		String jsonStr = exportProgressJsonStr(collectionId);
		String partialUrl = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.V1_EXPORTPROGRESS, classpageId,getLoggedInSessionToken());
		Map<String,String> params = new HashMap<String, String>();
		params.put(DATA, jsonStr);
		params.put(TIME_ZONE, timeZone);
		String url= AddQueryParameter.constructQueryParams(partialUrl, params);
		logger.info("exportProgress url:+"+url);
		return url;
	}


	@Override
	public AssessmentSummaryStatusDo getAssessmentSummary(ClassDo classObj){
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_ASSESSMENT_SUMMARY_DETAILS,classObj.getClassId(),classObj.getCourseId(),classObj.getUnitId(),classObj.getLessonId(),classObj.getAssessmentId());
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeAssessmentSummary(jsonRep);
	}

	public AssessmentSummaryStatusDo deserializeAssessmentSummary(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), AssessmentSummaryStatusDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new AssessmentSummaryStatusDo();
	}
}
