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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.service.PlayerAppService;
import org.ednovo.gooru.application.server.annotation.ServiceURL;
import org.ednovo.gooru.application.server.deserializer.ResourceCollectionDeSerializer;
import org.ednovo.gooru.application.server.deserializer.ShareDeSerializer;
import org.ednovo.gooru.application.server.request.JsonResponseRepresentation;
import org.ednovo.gooru.application.server.request.ServiceProcessor;
import org.ednovo.gooru.application.server.request.UrlToken;
import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemsList;
import org.ednovo.gooru.application.shared.model.content.ContentReportDo;
import org.ednovo.gooru.application.shared.model.content.ContentStarRatingsDistributionDo;
import org.ednovo.gooru.application.shared.model.content.ContentStarRatingsDo;
import org.ednovo.gooru.application.shared.model.content.ReactionDo;
import org.ednovo.gooru.application.shared.model.content.ResoruceCollectionDo;
import org.ednovo.gooru.application.shared.model.content.SearchRatingsDo;
import org.ednovo.gooru.application.shared.model.content.StarRatingsDo;
import org.ednovo.gooru.application.shared.model.content.UserPlayedSessionDo;
import org.ednovo.gooru.application.shared.model.content.UserStarRatingsDo;
import org.ednovo.gooru.application.shared.model.folder.FolderWhatsNextCollectionDo;
import org.ednovo.gooru.application.shared.model.player.CommentsDo;
import org.ednovo.gooru.application.shared.model.player.CommentsListDo;
import org.ednovo.gooru.application.shared.model.player.FeaturedContentDo;
import org.ednovo.gooru.application.shared.model.player.InsightsCollectionDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.user.CreatorDo;
import org.ednovo.gooru.shared.util.GooruConstants;
import org.ednovo.gooru.shared.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.StringRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;


@Service("playerService")
@ServiceURL("/playerService")
public class PlayerAppServiceImpl extends BaseServiceImpl implements PlayerAppService {

	private static Logger logger = LoggerFactory.getLogger(PlayerAppServiceImpl.class);

	private static final long serialVersionUID = 1L;

	@Autowired
	private ShareDeSerializer shareDeSerializer;

	private static final String REAL_URL = "realUrl";
	private static final String RESOURCE="resource";
	private static final String NARRATION="narration";
	private static final String TITLE="title";
	private static final String FIRST_NAME="firstName";
	private static final String PROFILE_IMAGE_URL="profileImageUrl";
	private static final String SCORE="score";
	private static final String AVERAGE="average";
	private static final String COUNT="count";
	private static final String ASSOCIATE_GOORU_OID="assocGooruOid";
	private static final String FREE_TEXT = "freeText";
	private static final String CREATE_DATE = "createdDate";
	private static final String LAST_MODIFIED_ON = "lastModifiedOn";

	private static final String YOUTUBE_PART_DETAILS = "contentDetails,status,snippet";
	private static final String ITEMS = "items";
	private static final String STATUS = "status";


	@Override
	public CollectionDo getSimpleCollectionDetils(String simpleCollectionId,String resourceId,String tabview, String rootNodeId) {
		return null;
	}

	public CollectionDo deserializeCollection(JsonRepresentation jsonRep) {
		CollectionDo collectionDo=new CollectionDo();
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				collectionDo=JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), CollectionDo.class);
				if(collectionDo!=null&&collectionDo.getCollectionItems()!=null&&collectionDo.getCollectionItems().size()>0){
					for(int i=0;i<collectionDo.getCollectionItems().size();i++){
						CollectionItemDo collectionItemDo=collectionDo.getCollectionItems().get(i);
						String decodeUrl=collectionItemDo.getResource().getUrl();
						if(!StringUtil.isEmpty(decodeUrl)&&!decodeUrl.equals("null")){
							if(decodeUrl.substring(0, 4).equalsIgnoreCase("http")){

							}else{
								String encodeUrl;
								try {
									encodeUrl = URLEncoder.encode(collectionItemDo.getResource().getUrl(),"UTF-8").replaceAll("\\+", "%20");
									collectionDo.getCollectionItems().get(i).getResource().setUrl(encodeUrl);
								} catch (UnsupportedEncodingException e) {
								}
							}
						}
					}
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return collectionDo;
	}

	public ResoruceCollectionDo deserializeResourceCollection(JsonRepresentation jsonRep) {
		ResoruceCollectionDo resourceCollectionDo=new ResoruceCollectionDo();
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				JSONObject resourceCollectionObject=jsonRep.getJsonObject();
				JSONArray collectionList=resourceCollectionObject.isNull("searchResults")?null:resourceCollectionObject.getJSONArray("searchResults");
				resourceCollectionDo.setTotalHitCount(resourceCollectionObject.isNull("totalHitCount")?null:resourceCollectionObject.getInt("totalHitCount"));
				List<ResourceSearchResultDo> resourceSearchResultList=new ArrayList<ResourceSearchResultDo>();
				if(collectionList!=null && collectionList.length()>0){
					for(int i=0;i<collectionList.length();i++){
						if(collectionList.getJSONObject(i)!=null){
							resourceSearchResultList.add(ResourceCollectionDeSerializer.deserializeRecord(collectionList.getJSONObject(i)));
						}
					}
				}
				resourceCollectionDo.setSearchResults(resourceSearchResultList);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return resourceCollectionDo;
	}


	@Override
	public CollectionDo getSimpleCollectionDetils(String apiKey,String simpleCollectionId, String resourceId, String tabView, String rootNodeId) {
		CollectionDo collectionDo = new CollectionDo();
		JsonRepresentation jsonRepresentation = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(),UrlToken.V2_GET_COLLECTION,simpleCollectionId);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.SKIP_COLL_ITEM, GooruConstants.TRUE);
		params.put(GooruConstants.INCLUDE_META_INFO,GooruConstants.TRUE );
		params.put(GooruConstants.MERGE, GooruConstants.PERMISSIONS);
		params.put(GooruConstants.INCLUDE_CONTENT_PROVDER, GooruConstants.FALSE);
		params.put(GooruConstants.INCLUDE_CUSTOM_FIELDS,GooruConstants.FALSE);
		if(rootNodeId != null) {
			params.put(GooruConstants.ROOT_NODE_ID,rootNodeId);
		}
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
//		url+=getStandardId(rootNodeId);

		JsonResponseRepresentation jsonResponseRep=ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		getLogger().info("getSimpleCollectionDetils:"+url);
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		if(jsonResponseRep.getStatusCode()==200){
			collectionDo = deserializeCollection(jsonRepresentation);
			collectionDo.setStatusCode(jsonResponseRep.getStatusCode());
		}else{
			collectionDo=new CollectionDo();
			collectionDo.setStatusCode(jsonResponseRep.getStatusCode());
		}

		try {

			if(collectionDo.getLanguageObjective() != null)
			{
				collectionDo.setLanguageObjective(URLDecoder.decode(collectionDo.getLanguageObjective(), "UTF-8"));
			}
			if(collectionDo.getKeyPoints() != null)
			{
				collectionDo.setKeyPoints(URLDecoder.decode(collectionDo.getKeyPoints(), "UTF-8"));
			}

        } catch (UnsupportedEncodingException e) {
        	logger.error("Exception::", e);
        }

		return collectionDo;
	}


	public ResoruceCollectionDo getResourceCollectionsList(String gooruOid,String pageNum,String pageSize) {
		JsonRepresentation jsonRep = null;

		String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.V2_RESOURCE_COLLECTION_LIST);
		Map<String, String> params = new HashMap<String, String>();
		params.put(GooruConstants.Q, gooruOid);
		params.put(GooruConstants.START, String.valueOf(pageNum));
		params.put(GooruConstants.LENGTH, String.valueOf(pageSize));
		params.put(GooruConstants.ACCESS_TYPE, "my");
		params.put(GooruConstants.CATEGORY, "all");
		params.put(GooruConstants.FILTER_RES_GOORU_OID,gooruOid);
		params.put(GooruConstants.BOOSTFIELD_HASNO_THUMBNAIL, "0");
		params.put(GooruConstants.SHOW_CANONICAL_ONLY, "false");

		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("urlresourceinfotab getResourceCollectionsList playerAPP::"+url);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		return deserializeResourceCollection(jsonRep);
	}

	public CollectionItemDo deserializeResourceInfoObj(JsonRepresentation jsonRep) {
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
	public CollectionItemDo getResourceInfoDetails(String apiKey,String resourceId, String tabView) {
		JsonRepresentation jsonRepresentation = null;
		CollectionItemDo collectionItemDo=null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_RESOURCE_DETAILS,resourceId);
		getLogger().info("getResourceInfoDetails::"+url);
		JsonResponseRepresentation jsonResponseRep=ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		getLogger().info("player APp Service impl getResourceInfoDetails:::::"+url);
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		try {
			if(jsonResponseRep.getStatusCode()==200){
				collectionItemDo=ResourceCollectionDeSerializer.deserializeCollectionItemDoV2API(deserializeResourceInfoObj(jsonRepresentation));
				collectionItemDo.setStatusCode(jsonResponseRep.getStatusCode());
				String decodeUrl=collectionItemDo.getResource().getUrl();
				if(decodeUrl!=null&&!decodeUrl.isEmpty()&&!decodeUrl.equals("null")){
					if(decodeUrl.substring(0, 4).equalsIgnoreCase("http")){
					}else{
						String encodeUrl;
						try {
							encodeUrl = URLEncoder.encode(collectionItemDo.getResource().getUrl(),"UTF-8").replaceAll("\\+", "%20");
							collectionItemDo.getResource().setUrl(encodeUrl);
						} catch (UnsupportedEncodingException e) {
							logger.error("Exception::", e);
						}
					}
				}
			}else{
				collectionItemDo=new CollectionItemDo();
				collectionItemDo.setStatusCode(jsonResponseRep.getStatusCode());
			}

			//Added this line because of URL encoding is not supported in Shared and View packages.
			//collectionItemDo.getResource().setEncodedUrl(URLEncoder.encode(collectionItemDo.getResource().getUrl()));
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return collectionItemDo;
	}

	@Override
	public CollectionItemDo getResourceObj(String resourceId) {
		JsonRepresentation jsonRepresentation = null;
		CollectionItemDo collectionItemDo=null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_RESOURCE_DETAILS,resourceId);

		JsonResponseRepresentation jsonResponseRep=ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		try {
			if(jsonResponseRep.getStatusCode()==200){
				collectionItemDo=ResourceCollectionDeSerializer.deserializeCollectionItemDo(jsonRepresentation.getJsonObject());
				collectionItemDo.setStatusCode(jsonResponseRep.getStatusCode());
				String decodeUrl=collectionItemDo.getResource().getUrl();
				if(decodeUrl!=null&&!decodeUrl.equals("")&&!decodeUrl.equals("null")){
					if(decodeUrl.substring(0, 4).equalsIgnoreCase("http")){
					}else{
						String encodeUrl;
						try {
							encodeUrl = URLEncoder.encode(collectionItemDo.getResource().getUrl(),"UTF-8").replaceAll("\\+", "%20");
							collectionItemDo.getResource().setUrl(encodeUrl);
						} catch (UnsupportedEncodingException e) {
							logger.error("Exception::", e);
						}
					}
				}
			}else{
				collectionItemDo=new CollectionItemDo();
				collectionItemDo.setStatusCode(jsonResponseRep.getStatusCode());
			}
			//Added this line because of URL encoding is not supported in Shared and View packages.
			//collectionItemDo.getResource().setEncodedUrl(URLEncoder.encode(collectionItemDo.getResource().getUrl()));

		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return collectionItemDo;
	}



	@Override
	public Map<String, String> getShortenShareUrl(String contentGooruOid) {
		JsonRepresentation jsonRep = null;
		Map<String, String> shareUrls=new HashMap<String, String>();
		String embededShortenPartialUrl= UrlGenerator.generateUrl(getHomeEndPoint()+"/" + UrlToken.COLLECTION_PLAY_EMBEDED_URL.getUrl());
		String embededShortenUrl = AddQueryParameter.constructQueryParams(embededShortenPartialUrl, GooruConstants.ID, contentGooruOid);
		String collectionSharePartialUrl= UrlGenerator.generateUrl(getHomeEndPoint() +"/" + UrlToken.COLLECTION_PLAY_URL.getUrl());
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.ID, contentGooruOid);
		params.put(GooruConstants.SHARE, GooruConstants.TRUE);
		String collectionShareUrl = AddQueryParameter.constructQueryParams(collectionSharePartialUrl, params);
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.SHARE_SHORTEN_URL_PLAY, contentGooruOid);
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.REAL_URL, embededShortenUrl);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		String shortenUrl=shareDeSerializer.deserializeShortenUrlFromJson(jsonRep);
		shareUrls.put("embedbitlyurl", shortenUrl);
		url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.SHARE_SHORTEN_URL_PLAY, contentGooruOid);
		url = AddQueryParameter.constructQueryParams(url, GooruConstants.REAL_URL, collectionShareUrl);
		getLogger().info("SHARE_SHORTEN_URL_PLAY url:::::"+url);
	    jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
	    jsonRep = jsonResponseRep.getJsonRepresentation();
		shortenUrl=shareDeSerializer.deserializeShortenUrlFromJson(jsonRep);
		try {
			collectionShareUrl=URLDecoder.decode(collectionShareUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception::", e);
		}
		shareUrls.put("sharebitlyurl", shortenUrl);
		shareUrls.put("shareurl", collectionShareUrl);
		return shareUrls;
	}

	@Override
	public String updateViewCount(String gooruid, String viewCount,String resourceType) {
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_VIEW_COUNT,gooruid);
		Form form=new Form();
		form.add("resourceViews", viewCount);
		form.add("sessionToken", getLoggedInSessionToken());
		getLogger().info("UPDATE_VIEW_COUNT form url::::"+form);
		ServiceProcessor.post(url, getRestUsername(), getRestPassword(),form);
		return resourceType;
	}

	@Override
	public String startActivityPlayerLog(String activityEventId,String activityParentEventId, String eventName, String gooruOid,String resourceGooruOid, String context, String userAgent) {
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.START_ACTIVITY_LOG,activityEventId);

		Form form=new Form();
		form.add("sessionToken", getLoggedInSessionToken());
		form.add("contentGooruOid", resourceGooruOid);
		form.add("parentGooruOid", gooruOid);
		form.add("context", getLoggedInSessionToken());
		form.add("parentEventId", activityParentEventId);
		form.add("userAgent", userAgent);
		form.add("eventName", eventName);

		ServiceProcessor.post(url, getRestUsername(), getRestPassword(),form);
		return activityEventId;
	}

	@Override
	public String stopActivityPlayerLog(String activityEventId,String activityParentEventId, String eventName, String gooruOid,String resourceGooruOid, String context, String userAgent) {
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.STOP_ACTIVITY_LOG,activityEventId);

		Form form=new Form();
		form.add("sessionToken", getLoggedInSessionToken());
		form.add("contentGooruOid", resourceGooruOid);
		form.add("parentGooruOid", gooruOid);
		form.add("context", getLoggedInSessionToken());
		form.add("parentEventId", activityParentEventId);
		form.add("userAgent", userAgent);
		form.add("eventName", eventName);

		ServiceProcessor.post(url, getRestUsername(), getRestPassword(),form);
		return activityEventId;
	}

	public String createSessionTracker(String collectionGooruOid,String clientsSessionId,String mode){
		String seesionId="";
//		JSONObject createSessionObject=new JSONObject();
//		JsonRepresentation jsonRepresentation = null;
//		try {
//			createSessionObject.put("contentGooruId", collectionGooruOid);
//			if(clientsSessionId!=null && !clientsSessionId.isEmpty()){
//				createSessionObject.put("parentGooruId", clientsSessionId);
//			}
//			createSessionObject.put("mode", "test");
//			createSessionObject.put("type", mode);
//			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.CREATE_SESSION);
//			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(),createSessionObject.toString());
//			logger.info("createSessionTracker url::"+url);
//			logger.info("createSessionObject::"+createSessionObject.toString());
//			jsonRepresentation=jsonResponseRep.getJsonRepresentation();
//			if(jsonRepresentation!=null&&jsonRepresentation.getSize()!=-1){
//				JSONObject createSessionResponse=jsonRepresentation.getJsonObject();
//				seesionId=createSessionResponse.getString("sessionActivityId");
//			}
//		} catch (JSONException e) {
//			logger.error("Exception::", e);
//		}
		return seesionId;
	}


	public String updateSessionInCollection(String sessionTrackerId) {
		//This method is not required.
		String sessionItemId="";
		return sessionItemId;
	}

	public void getSessionInCollection(String sessionTrackerId) {

	}

	public String createSessionItemInCollection(String sessionTrackerId,String collectionItemId, String resourceGooruOid, String questionType, String status) {
		String sessionItemId="";
		//Need to pass the same sessionId;
		return sessionItemId;
	}

	public String createSessionItemAttemptTry(String contentGooruOid,String sessionTrackerId,String sessionItemTrackerId, Integer answerId, String attemptResult) {
		//This Method is not required.
		return "";
	}
	@Override
	public String createSessionItemAttemptTryForOe(String contentGooruOid,String sessionTrackerId,String sessionItemTrackerId,String answerId,String attemptStatus,String attemptAnswerResult) {
//		JSONObject sessionItemAttemptTry=new JSONObject();
//		try {
//			/*assessmentAnswer.put("attemptItemTryStatus",attemptStatus);
//			assessmentAnswer.put("answerText",attemptAnswerResult);
//			sessionItemAttemptTry.put("sessionItemAttemptTry",assessmentAnswer);*/
//
//			sessionItemAttemptTry.put("contentGooruId", contentGooruOid);
//			//sessionItemAttemptTry.put("answerId",answerId);
//			sessionItemAttemptTry.put("answerStatus",attemptStatus);
//			sessionItemAttemptTry.put("sessionActivityId",sessionTrackerId);
//			sessionItemAttemptTry.put("answerText",attemptAnswerResult);
//			//sessionItemAttemptTry.put("answerOptionSequence","");
//			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.CREATE_SESSION_ITEM_ATTEMPT, sessionTrackerId,sessionItemTrackerId);
//			getLogger().info("--->>  createSessionItemAttemptTryOE --- "+url);
//			getLogger().info("--->>  createSessionItemAttemptTry payloadOE  --- "+sessionItemAttemptTry.toString());
//			ServiceProcessor.post(url, getRestUsername(), getRestPassword(),sessionItemAttemptTry.toString());
//		} catch (JSONException e) {
//			logger.error("Exception::", e);
//		}
		return "";
	}

	@Override
	public String sendEmail(String fromEmail, String toEmail, String copyEmail,String subject, String message) {
		JSONObject mailJsonObject=new JSONObject();
		try {

			String completeLogoDetails = "<!DOCTYPE html> <html> <head> <meta http-equiv=" +
					"Content-Type" +
					" content=" +
					"text/html; charset=UTF-8" +
					" /> </head> <body style='font-family: arial, sans-serif;color: #515151;height:375px;font-size: 12px; background-color: #f0f0f0;text-align: center;'> <img id='logo-header' src='http://sfs.gooru.org/media/mail/v1/images/gooru-logo-small.png' style='width:100px;height:30px;margin: 30px auto 10px auto;'" +
					"></img> <div class='content-block img-desc' style='text-align: left;width: 500px;padding: 35px;margin: 0px auto 30px auto;background-color: white;border: 1px solid #DDD;-moz-box-shadow: 0 0 10px rgba(0,0,0,.1); -webkit-box-shadow: 0 0 10px rgba(0,0,0,.1);box-shadow: 0 0 10px rgba(0,0,0,.1);'> <div style='font-family: arial;width: 520px;color:#666;height:150px'>"+ message +
					"</body> </html>";

			mailJsonObject.put("fromDisplayName", fromEmail);
			mailJsonObject.put("to", toEmail);
			mailJsonObject.put("cfm", copyEmail);
			mailJsonObject.put("subject", subject);
			mailJsonObject.put("message", completeLogoDetails);

			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_SOCIAL_EMAIL);
			ServiceProcessor.post(url, getRestUsername(), getRestPassword(),mailJsonObject.toString());
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return null;
	}

	public boolean getUserProfileVisibility(String gooruUid){
		boolean userProfileVisibility=false;
		JsonRepresentation jsonRep =null;
		JSONObject jsonObject= null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_USER_PROFILE,gooruUid);
		JsonResponseRepresentation jsonResponseRep =ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		try{
			if(jsonRep!=null && jsonRep.getSize()!=-1){
			jsonObject = jsonRep.getJsonObject();
			if(!jsonObject.isNull("optionalValue")){
			userProfileVisibility = jsonObject.getBoolean("optionalValue");
			}
			}
		}catch(Exception exception){
			logger.error("Exception::", exception);
		}
		return userProfileVisibility;
	}
	@Override
	public String copyCollection(String collectionId, String collectionTitle) {
		String copiedCollectionId="";
		JsonRepresentation jsonRep =null;
		try {
			collectionTitle = collectionTitle.trim();
			collectionTitle=URLEncoder.encode(collectionTitle);
			String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.COPY_RENAME_COLLECTION, collectionId);
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put(GooruConstants.ADD_TO_SHELF, GooruConstants.TRUE);
			params.put(GooruConstants.TITLE, collectionTitle);
			String url = AddQueryParameter.constructQueryParams(partialUrl, params);
			getLogger().info("COPY_RENAME_COLLECTION put url:::::"+url);
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.put(url, getRestUsername(), getRestPassword(),new Form());
			jsonRep=jsonResponseRep.getJsonRepresentation();
			if(jsonRep!=null && jsonRep.getSize()!=-1){
				JSONObject copiedCollectionObj=jsonRep.getJsonObject();
				if(!copiedCollectionObj.isNull("gooruOid")){
				copiedCollectionId=copiedCollectionObj.getString("gooruOid");
				}
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return copiedCollectionId;
	}

	@Override
	public String copyCollectionItem(String collectionItemId, String collectionId) {
		String copiedCollectionItemId="";
		try {
			JsonRepresentation jsonRep =null;
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.COPY_RESOURCCE);
			getLogger().info("COPY_RESOURCCE API Cal :::"+url);
			Form form=new Form();
			form.add("sessionToken", getLoggedInSessionToken());
			form.add("resourceId",collectionItemId);
			form.add("collectionId",collectionId);
			form.add("data","{\"collectionItem\":{\"itemType\":\"subscribed\"}}");
			getLogger().info("COPY_RESOURCCE form data post method payload::::::"+form);
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.post(url, getRestUsername(), getRestPassword(),form);
			jsonRep = jsonResponseRep.getJsonRepresentation();
			if(jsonRep!=null && jsonRep.getSize()!=-1){
				JSONObject copiedCollectionObj=jsonRep.getJsonObject();
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return copiedCollectionItemId;
	}

	@Override
	public ArrayList<CollectionItemsList> getWorkspaceCollections(String userId,String offset,String limit) {
		    JsonRepresentation jsonRep =null;
			String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_USER_WORKSPACE);
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put(GooruConstants.PAGE_NUM, offset);
			params.put(GooruConstants.PAGE_SIZE, limit);
			String url = AddQueryParameter.constructQueryParams(partialUrl, params);
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.get(url, getRestUsername(), getRestPassword());
			jsonRep= jsonResponseRep.getJsonRepresentation();
			return deserializeUserCollections(jsonRep);
	}


	public ArrayList<CollectionItemsList> deserializeUserCollections(JsonRepresentation jsonRep){
		ArrayList<CollectionItemsList> resourceModelList=new ArrayList<CollectionItemsList>();
		try {
			if(jsonRep!=null){
				JSONArray myCollectionArryObj=jsonRep.getJsonArray();
				for(int i=0;i<myCollectionArryObj.length();i++){
					JSONObject myCollectionObj=myCollectionArryObj.getJSONObject(i);
					String myCollectionTitle=myCollectionObj.isNull("title")?"":myCollectionObj.getString("title").toString();
					String mycollectionGid=myCollectionObj.isNull("gooruOid")?"":myCollectionObj.getString("gooruOid").toString();
					String myCollectionType=myCollectionObj.isNull("collectionType")?"":myCollectionObj.getString("collectionType").toString();
					int collectionItemsListSize = 0;
					if(!myCollectionObj.isNull("collectionItems")){
						JSONArray collectionInfo=myCollectionObj.getJSONArray("collectionItems");
						collectionItemsListSize = collectionInfo.length();
					}
					CollectionItemsList collectionDetails=new CollectionItemsList();
					collectionDetails.setCollectionTitle(myCollectionTitle);
					collectionDetails.setCollectionId(mycollectionGid);
					collectionDetails.setCollectionType(myCollectionType);
					collectionDetails.setCollectionItemsListSize(collectionItemsListSize);
					resourceModelList.add(collectionDetails);
				}//main for loop
			}
		}//try end
		catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return resourceModelList;
	}

	public ArrayList<ContentReportDo> getContentReport(String associatedGooruOid,String gooruUid){
		JsonRepresentation jsonRep = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_CONTENT_REPORT,associatedGooruOid);
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.CREATOR_UID, gooruUid);
		JsonResponseRepresentation jsonResponseRep=ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();

		return deserializeGetContentReport(jsonRep);
	}

	public ArrayList<ContentReportDo> deserializeGetContentReport(JsonRepresentation jsonRep){

		ArrayList<ContentReportDo> reactionsList=new ArrayList<ContentReportDo>();
		if(jsonRep!=null && jsonRep.getSize()!=-1){
			try {
				JSONArray jsonArray=jsonRep.getJsonArray();
				if(jsonArray!=null && jsonArray.length()>0){
					for(int i=0;i<jsonArray.length();i++){
						JSONObject jsonObject=jsonArray.getJSONObject(i);
						ContentReportDo contentReportDo =deserializContentReport(jsonObject);
						reactionsList.add(contentReportDo);
					}
				}
			} catch (Exception e) {
				logger.error("Exception::", e);
			}
		}
		return reactionsList;
	}

	public ContentReportDo createContentReport(String associatedGooruOid,String freeText,ArrayList<String> contentReportList,String deleteContentReportGooruOids){
		JsonRepresentation jsonRep=null;
		try{
			if(deleteContentReportGooruOids!=null&&!deleteContentReportGooruOids.isEmpty()){
				deleteContentReport(deleteContentReportGooruOids);
			}
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.CREATE_CONTENT_REPORT);
			JSONObject contentReportObject=new JSONObject();
			JSONArray contentReportJsonArray=new JSONArray();
			contentReportObject.put("assocGooruOid", associatedGooruOid);
			contentReportObject.put("freeText", freeText);
			contentReportObject.put("target", new JSONObject().put("value", "content"));
			if(contentReportList!=null && contentReportList.size()>0){
				for(int i=0;i<contentReportList.size();i++){
					contentReportJsonArray.put(new JSONObject().put("value", contentReportList.get(i)));
				}
			}
			contentReportObject.put("types", contentReportJsonArray);
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.post(url, getRestUsername(), getRestPassword(),contentReportObject.toString());
			jsonRep = jsonResponseRep.getJsonRepresentation();
		}catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return null;
	}

	@Override
	public ReactionDo createReaction(String resourceId, String reactionText,String gooruReactionId,String collectionId, String createStudyPlayerReaction) {
		JsonRepresentation jsonRep=null;
		JSONObject jsonObject=null;
		try {
			if(gooruReactionId!=null&&!gooruReactionId.isEmpty()){
				deleteContentReaction(gooruReactionId);
			}
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.CREATE_RECTION);
			JSONObject createReactionJsonObj = new JSONObject();
			JSONObject createContextJsonObj = new JSONObject();
			createReactionJsonObj.put("assocGooruOid", resourceId);
			createReactionJsonObj.put("target", new JSONObject().put("value","content"));
			if(reactionText!=null){
				createReactionJsonObj.put("type", new JSONObject().put("value",reactionText));
			}
			createContextJsonObj.put("collectionGooruId", collectionId);
			createContextJsonObj.put("resourceGooruId", resourceId);
			createContextJsonObj.put("eventName", createStudyPlayerReaction);
			createReactionJsonObj.put("context",createContextJsonObj.toString());
			getLogger().info("create Reaction API call::::::"+url);
			getLogger().info("create Reaction  post payload data::::"+createReactionJsonObj.toString());
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(),createReactionJsonObj.toString());
			jsonRep = jsonResponseRep.getJsonRepresentation();
			if(jsonRep!=null && jsonRep.getSize()!=-1){
			jsonObject=jsonRep.getJsonObject();
			}

		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return deserializeResourceReaction(jsonObject);
	}

	public void deleteContentReaction(String associatedGooruOid){
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.DELETE_CONTENT_REACTION,associatedGooruOid);
		ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
	}

	@Override
	public ArrayList<ReactionDo> getResourceReaction(String associatedGooruOid, String gooruUid) {
		JsonRepresentation jsonRep=null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_CONTENT_REACTION,associatedGooruOid);
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.CREATOR_UID, gooruUid);
		JsonResponseRepresentation jsonResponseRep=ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		return deserializeGetReaction(jsonRep);
	}



	public ArrayList<ReactionDo> deserializeGetReaction(JsonRepresentation jsonRep){
		ArrayList<ReactionDo> reactionsList=new ArrayList<ReactionDo>();
		if(jsonRep!=null && jsonRep.getSize()!=-1){
		try {
			JSONArray jsonArray=jsonRep.getJsonArray();
			if(jsonArray.length()>0){
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject=jsonArray.getJSONObject(i);
					ReactionDo reactionDo =deserializeResourceReaction(jsonObject);
					reactionsList.add(reactionDo);
				}
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		}
		return reactionsList;
	}
	private ReactionDo deserializeResourceReaction(JSONObject jsonObject) {
		ReactionDo reactionDo = new  ReactionDo();
		try {
			reactionDo.setAssocGooruOid(jsonObject.isNull("assocGooruOid")?"":jsonObject.getString("assocGooruOid"));
			reactionDo.setReactionText((!jsonObject.isNull("type")&&!jsonObject.getJSONObject("type").isNull("value"))?(jsonObject.getJSONObject("type").getString("value")):"");
			reactionDo.setDeleteReactionGooruOid(jsonObject.isNull("gooruOid")?"":jsonObject.getString("gooruOid"));
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return reactionDo;
	}

	public void deleteContentReport(String associatedGooruOid){
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.DELETE_CONTENT_REPORT,associatedGooruOid);
		ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
	}

	protected ContentReportDo deserializContentReport(JSONObject jsonObject){
		ContentReportDo contentReportDo=new ContentReportDo();
		ArrayList<String> contentReportList=new ArrayList<String>();
		try {
			if(!jsonObject.isNull("type") && !jsonObject.getJSONObject("type").isNull("value")){
			String contentReportText=jsonObject.getJSONObject("type").getString("value");
			contentReportList.add(contentReportText);
			}
			contentReportDo.setFreeText(jsonObject.isNull("freeText")?"":jsonObject.getString("freeText"));
			contentReportDo.setAssocGooruOid(jsonObject.isNull("assocGooruOid")?"":jsonObject.getString("assocGooruOid"));
			contentReportDo.setDeleteContentGooruOid(jsonObject.isNull("gooruOid")?"":jsonObject.getString("gooruOid"));
			contentReportDo.setContentReportList(contentReportList);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return contentReportDo;
	}

	public CommentsDo createCommentForCollection(String gooruCollectionId, String userCommentsEntered){
		JsonRepresentation jsonRep=null;
		try{
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_CREATE_COLLECTION_COMMENT);
			JSONObject commentObject=new JSONObject();
			commentObject.put("comment", userCommentsEntered);
			commentObject.put("gooruOid", gooruCollectionId);
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.post(url, getRestUsername(), getRestPassword(),commentObject.toString());
			jsonRep =jsonResponseRep.getJsonRepresentation();
		}catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return deserializCommentsObject(jsonRep);
	}

	protected CommentsDo deserializCommentsObject(JsonRepresentation jsonRep){
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				CommentsDo commentsDoObj = JsonDeserializer.deserialize(
						jsonRep.getJsonObject().toString(), CommentsDo.class);
				return commentsDoObj;
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new CommentsDo();
	}

	public CommentsListDo getCollectionCommentsList(String gooruOid,String offset,String pageLimit) {
		JsonRepresentation jsonRep= null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_COLLECTION_COMMENTS);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.GOORU_OID, gooruOid);
		params.put(GooruConstants.OFFSET, offset);
		params.put(GooruConstants.LIMIT, pageLimit);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("---- get comments --- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		return deserializeCollectionComments(jsonRep);
	}

	public CommentsListDo deserializeCollectionComments(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), CommentsListDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new CommentsListDo();
	}

	public void deleteCollectionCommentbyCommentUid(String commentUid){
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_DELETE_COLLECTION_COMMENT,commentUid);
		getLogger().info("--- delete comment -- "+url);
		ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
	}

	public CommentsDo updateCollectionCommentbyCommentUid(String commentUid, String commentsUpdatedByUser) {
		CommentsDo commentsDo= new CommentsDo();
		JSONObject updateSessionObject=new JSONObject();
		JSONObject statusActive = new JSONObject();
		JsonRepresentation jsonRepresentation = null;
		try {
			statusActive.put("value", "active");
			updateSessionObject.put("comment",commentsUpdatedByUser);
			updateSessionObject.put("status", statusActive);
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_UPDATE_COLLECTION_COMMENT, commentUid);
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(),updateSessionObject.toString());
			jsonRepresentation =jsonResponseRep.getJsonRepresentation();
			if(jsonResponseRep.getStatusCode()==200){
				commentsDo = deserializCommentsObject(jsonRepresentation);
				commentsDo.setStatusCode(jsonResponseRep.getStatusCode());
			}else{
				commentsDo=new CommentsDo();
				commentsDo.setStatusCode(jsonResponseRep.getStatusCode());
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return commentsDo;
	}

	@Override

	public String generatePdf(String innerHtml, String completedDateTime) {
		String pdfUrl=null;
		JSONObject generatePdfJsonObj=new JSONObject();
		StringRepresentation stringRepresentation = null;
		try {
			generatePdfJsonObj.put("html", innerHtml);
			generatePdfJsonObj.put("fileName", completedDateTime);
			String url =UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GENERATE_PDF);
			stringRepresentation=ServiceProcessor.postString(url, getRestUsername(), getRestPassword(),generatePdfJsonObj.toString());
			pdfUrl=stringRepresentation.getText();
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return pdfUrl;

	}

	@Override

	public String sendEmailWithPdf(String toAddress, String fromAddress,String cfm, String subject, String message, String url,String FileName) {

		JSONObject infoJsonObj=new JSONObject();

		JSONObject attachment=new JSONObject();

		StringRepresentation stringRepresentation = null;

		String response=null;

		try {

			infoJsonObj.put("to", toAddress);

			infoJsonObj.put("fromDisplayName", fromAddress);

			infoJsonObj.put("cfm", cfm);

			infoJsonObj.put("subject", subject);

			infoJsonObj.put("message", message);

			url= url.replaceAll("\n", "");

			url= url.replaceAll("\\n", "");

			FileName= FileName.replaceAll("\n", "");

			FileName= FileName.replaceAll("\\n", "");

			attachment.put("url",url);

			attachment.put("fileName",FileName);

			infoJsonObj.put("attachment", new JSONArray("["+attachment.toString()+"]"));

			String apiUrl =UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_SEND_EMAIL_WITH_PDF);

			stringRepresentation=ServiceProcessor.postString(apiUrl, getRestUsername(), getRestPassword(),infoJsonObj.toString());

			response = stringRepresentation.getText();

			if(response==null) {

				response = "success";

			} else {

				response = "failure";

			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.service.PlayerAppService#getFeaturedContent()
	 */
	/**
	 * This method will send email with pdf attachment by making API call.
	 *
	 * @param toAddress it specifies the to mail.
	 * @param fromAddress it specifies the from email.
	 * @param cfm it specifies "yes" or "no",if yes copy of mail send to from email also,if not will not send copy of message.
	 * @param subject  it specifies the subject of the email.
	 * @param message  it specifies the text message of the email.
	 * @param url it specifies the pdf url value.
	 * @param fileName it specfies the pdf file name value
	 */
	@Override
	public ArrayList<FeaturedContentDo> getFeaturedContent() {
		ArrayList<FeaturedContentDo> featuredContentDos=new ArrayList<FeaturedContentDo>();
		JsonRepresentation jsonRepresentation =null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_FEATURED_COLLECTIONS);
		JsonResponseRepresentation jsonResponseRep =ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation =jsonResponseRep.getJsonRepresentation();
		try{
			JSONArray featuredContentArray=jsonRepresentation.getJsonArray();
			if(featuredContentArray.length()>0){
			 for(int i=0;i<featuredContentArray.length();i++){
				 FeaturedContentDo featuredContentDo=new FeaturedContentDo();
				 JSONObject featureJsonObject=featuredContentArray.getJSONObject(i);
				 JSONArray featureArray=featureJsonObject.getJSONArray("scollections");
				 if(featureArray.length()>0){
					 JSONObject featureContent=featureArray.getJSONObject(0);
					 featuredContentDo.setCollectionTitle(featureContent.isNull("title")?"":featureContent.getString("title"));
					 featuredContentDo.setCollectionGooruOid(featureContent.isNull("gooruOid")?"":featureContent.getString("gooruOid"));
					 JSONObject thubnailObject=featureContent.getJSONObject("thumbnails");
					 featuredContentDo.setCollectionThumbnailUrl(thubnailObject.isNull("url")?"":thubnailObject.getString("url"));
					 featuredContentDos.add(featuredContentDo);
				 }
			 }
			}

		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return featuredContentDos;
	}

	@Override
	public void deleteReaction(String gooruReactionId) {
		deleteContentReaction(gooruReactionId);
	}

	private String getStandardId(String standardId) {
		String standardIdVal = "";
		if(standardId != null) {
			standardIdVal = "&rootNodeId="+standardId;
		}
		return standardIdVal;
	}

	/**
	 * Creates a Ratings given by the user by triggering Create Rating API.
	 *
	 *  @param associateGooruOid {@link String}
	 *  @param starRatingValue {@link Integer}
	 *
	 *  @return StarRating model object {@link StarRatingsDo}
	 */
	@Override
	public StarRatingsDo createStarRatings(String associateGooruOid,int starRatingValue,String userReview) {
		JsonRepresentation jsonRep=null;
		JSONObject jsonObject=null;
		try {
			 String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.CREATE_STAR_RATINGS);
			 JSONObject createStarRatingsJsonObj = new JSONObject();
			 createStarRatingsJsonObj.put(ASSOCIATE_GOORU_OID, associateGooruOid);
			 createStarRatingsJsonObj.put(SCORE, starRatingValue);
			 if(userReview!=null && !userReview.equals("")){
				 createStarRatingsJsonObj.put(FREE_TEXT, userReview);
			 }
			 createStarRatingsJsonObj.put("target",new JSONObject().put("value","content"));
			 createStarRatingsJsonObj.put("type",new JSONObject().put("value","star"));
			 JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url,getRestUsername(), getRestPassword(), createStarRatingsJsonObj.toString());
			 jsonRep= jsonResponseRep.getJsonRepresentation();
			 if(jsonRep!=null && jsonRep.getSize()!=-1){
			 jsonObject= jsonRep.getJsonObject();
			 }

		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return deserializeResourceStarRatings(jsonObject);
	}

	/**
	 * Deserializes  Star rating model object.
	 *
	 * @param jsonObject {@link JSONObject}
	 * @return starRatingsDo {@link StarRatingsDo}
	 */

	private StarRatingsDo deserializeResourceStarRatings(JSONObject jsonObject) {
		StarRatingsDo starRatingsDo = new  StarRatingsDo();
		try {
			starRatingsDo.setAssocGooruOid(jsonObject.isNull("assocGooruOid")?"":jsonObject.getString("assocGooruOid"));
			starRatingsDo.setDeleteRatingGooruOid(jsonObject.isNull("gooruOid")?"":jsonObject.getString("gooruOid"));
			if(!jsonObject.isNull("ratings")){
				JSONObject resourceRating = jsonObject.getJSONObject("ratings");
				SearchRatingsDo searchRatingsDo =JsonDeserializer.deserialize(resourceRating.toString(), SearchRatingsDo.class);
				starRatingsDo.setRatings(searchRatingsDo);
			}

			if(!jsonObject.isNull("creator"))
			{
				CreatorDo creatorObject = new CreatorDo();
				JSONObject jsonCreatorObject = new JSONObject();
				jsonCreatorObject = jsonObject.getJSONObject("creator");
				creatorObject.setUsername(jsonCreatorObject.isNull("username")?"":jsonCreatorObject.getString("username"));
				creatorObject.setFirstName(jsonCreatorObject.isNull("firstName")?"":jsonCreatorObject.getString("firstName"));
				creatorObject.setLastName(jsonCreatorObject.isNull("lastName")?"":jsonCreatorObject.getString("lastName"));
				creatorObject.setGooruUId(jsonCreatorObject.isNull("gooruUId")?"":jsonCreatorObject.getString("gooruUId"));
				starRatingsDo.setCreator(creatorObject);
			}

			starRatingsDo.setScore(jsonObject.isNull(SCORE)?0:jsonObject.getInt(SCORE));
			starRatingsDo.setCreatedDate(jsonObject.isNull(CREATE_DATE)?0:jsonObject.getLong(CREATE_DATE));
			starRatingsDo.setLastModifiedOn(jsonObject.isNull(LAST_MODIFIED_ON)?0:jsonObject.getLong(LAST_MODIFIED_ON));
			starRatingsDo.setFreeText(jsonObject.isNull("freeText")?"":jsonObject.getString("freeText"));
		} catch (Exception e) {
			logger.error("Exception::", e);
		}

		return starRatingsDo;
	}

	/**
	 * Not in use...
	 * Gets the ratings given by the user for each resource by calling an API.
	 *
	 * @param associateGooruOid {@link String}
	 * @param gooruUid {@link String}
	 *
	 * @return StarRating model object {@link StarRatingsDo}
	 */

	@Override
	public StarRatingsDo getResourceStarRatings(String associatedGooruOid, String gooruUid) {
		JsonRepresentation jsonRep=null;
		JSONObject jsonObject=null;
		try {
			String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_STAR_RATINGS,associatedGooruOid);
			String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.CREATOR_UID,gooruUid);
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.get(url, getRestUsername(), getRestPassword());
			jsonRep=jsonResponseRep.getJsonRepresentation();
			jsonObject= jsonRep.getJsonObject();
		} catch (Exception e) {
			logger.error("Exception::", e);
		}

		return deserializeResourceStarRatings(jsonObject);
	}

	/**
	 * Gets the over all content star ratings and average for each resource by calling an API.
	 *
	 * @param associateGooruOid {@link String}
	 * @param gooruUid {@link String}
	 *
	 * @return StarRating model object {@link StarRatingsDo}
	 */
	@Override
	public ContentStarRatingsDo getContentStarRatings(String gooruOid) {
		JsonRepresentation jsonRepresentation =null;
		JSONObject jsonObject = null;
		try {
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_CONTENT_STAR_RATINGS,gooruOid);
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.get(url,getRestUsername(), getRestPassword());
			jsonRepresentation=jsonResponseRep.getJsonRepresentation();
			if(jsonRepresentation!=null && jsonRepresentation.getSize()!=-1){
			jsonObject= jsonRepresentation.getJsonObject();
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return deserializeContentStarRatings(jsonObject);
	}

	/**
	 * Deserializes Content star rating  model object.
	 *
	 * @param jsonObject {@link JSONObject}
	 * @return starRatingsDo {@link ContentStarRatingsDo}
	 */
	private ContentStarRatingsDo deserializeContentStarRatings(JSONObject jsonObject) {
		ContentStarRatingsDo contentStarRatingsDo = new  ContentStarRatingsDo();
		ContentStarRatingsDistributionDo contentRatingDistribution = new  ContentStarRatingsDistributionDo();
		try {
			if(!jsonObject.isNull("average")){
				contentStarRatingsDo.setAverage(jsonObject.getInt(AVERAGE));
			}
			if(!jsonObject.isNull("count")){
				contentStarRatingsDo.setCount(jsonObject.getInt(COUNT));
			}
			for(int i=1; i<6;i++)
			{
				JSONObject resourceCollectionObject=jsonObject.isNull("scores")?null:jsonObject.getJSONObject("scores");
				try
				  {
					if(resourceCollectionObject!=null){
							if(!resourceCollectionObject.isNull(String.valueOf(i)))
							{
								if(i==1)
								{
									contentRatingDistribution.setOne(resourceCollectionObject.getInt(String.valueOf(i)));
								}
								else if(i==2)
								{
									contentRatingDistribution.setTwo(resourceCollectionObject.getInt(String.valueOf(i)));
								}
								else if(i==3)
								{
									contentRatingDistribution.setThree(resourceCollectionObject.getInt(String.valueOf(i)));
								}
								else if(i==4)
								{
									contentRatingDistribution.setFour(resourceCollectionObject.getInt(String.valueOf(i)));
								}
								else if(i==5)
								{
									contentRatingDistribution.setFive(resourceCollectionObject.getInt(String.valueOf(i)));
								}
							}else{
								if(i==1)
								{
									contentRatingDistribution.setOne(0);
								}
								else if(i==2)
								{
									contentRatingDistribution.setTwo(0);
								}
								else if(i==3)
								{
									contentRatingDistribution.setThree(0);
								}
								else if(i==4)
								{
									contentRatingDistribution.setFour(0);
								}
								else if(i==5)
								{
									contentRatingDistribution.setFive(0);
								}
							}
						}
					}catch(Exception ex){

					}

			}//for end
			contentStarRatingsDo.setScores(contentRatingDistribution);
		}catch (Exception e) {
			logger.error("Exception::", e);
		}
		return contentStarRatingsDo;
	}

	/**
	 * Updates the rating for a resource resource by calling an API.
	 *
	 * @param gooruOid {@link String}
	 * @param score {@link Integer}
	 *
	 * @return StarRating model object {@link StarRatingsDo}
	 */

	@Override
	public ArrayList<StarRatingsDo> updateResourceStarRatings(String gooruOid, int score) {
		JsonRepresentation jsonRepresentation=null;
		try {
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_STAR_RATINGS,gooruOid);
			JSONObject updateStarRatingsJsonObj = new JSONObject();
			updateStarRatingsJsonObj.put(SCORE,score);
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(),updateStarRatingsJsonObj.toString());
			jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		} catch (Exception e) {
			logger.error("Exception::", e);
		}

		return deserializeStarRatings(jsonRepresentation);
	}

	public ArrayList<StarRatingsDo> deserializeStarRatings(JsonRepresentation jsonRep){
		ArrayList<StarRatingsDo> ratingsList=new ArrayList<StarRatingsDo>();
		if(jsonRep!=null && jsonRep.getSize()!=-1){
		try {
			JSONArray jsonArray=jsonRep.getJsonArray();
			 if(jsonArray!=null && jsonArray.length()>0){
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject=jsonArray.getJSONObject(i);
					StarRatingsDo reactionDo =deserializeUpdateRatings(jsonObject);
					ratingsList.add(reactionDo);
				}
			 }
		} catch (Exception e) {
			logger.error("Exception::", e);
		}

		return ratingsList;
		}
		return ratingsList;
	}

	private StarRatingsDo deserializeUpdateRatings(JSONObject jsonObject) {
		StarRatingsDo starRatingsDo = new  StarRatingsDo();
		try {
			starRatingsDo.setAssocGooruOid(jsonObject.isNull("assocGooruOid")?"":jsonObject.getString("assocGooruOid"));
			starRatingsDo.setFreeText(jsonObject.isNull("freeText")?"":jsonObject.getString("freeText"));
			starRatingsDo.setDeleteRatingGooruOid(jsonObject.isNull("gooruOid")?"":jsonObject.getString("gooruOid"));
			if(!jsonObject.isNull(SCORE)){
			starRatingsDo.setScore(jsonObject.getInt(SCORE));
			}
			if(!jsonObject.isNull("ratings")){
				JSONObject resourceRating = jsonObject.getJSONObject("ratings");
				SearchRatingsDo searchRatingsDo =JsonDeserializer.deserialize(resourceRating.toString(), SearchRatingsDo.class);
				starRatingsDo.setRatings(searchRatingsDo);
			}

		} catch (Exception e) {
			logger.error("Exception::", e);
		}

		return starRatingsDo;
	}

	/**
	 * Gets the user star ratings.
	 * @param gooruOid {@link String}
	 * @return {@link UserStarRatingsDo}
	 */

	@Override
	public UserStarRatingsDo getUserStarRatings(String gooruOid) {
		JsonRepresentation jsonRepresentation =null;
		JSONObject jsonObject = null;
		try {
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_USER_STAR_RATINGS,gooruOid);
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.get(url,getRestUsername(), getRestPassword());
			jsonRepresentation=jsonResponseRep.getJsonRepresentation();
			jsonObject= jsonRepresentation.getJsonObject();
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return deserializeUserStarRatings(jsonObject);
	}

	/**
	 * De-serialize User star rating model object
	 * @param jsonObject {@link JSONObject}
	 * @return {@link UserStarRatingsDo}
	 */
	private UserStarRatingsDo deserializeUserStarRatings(JSONObject jsonObject) {
		UserStarRatingsDo userStarRatingsDo = new  UserStarRatingsDo();
		try {
			userStarRatingsDo.setAverage(jsonObject.getInt(AVERAGE));
			userStarRatingsDo.setCount(jsonObject.getInt(COUNT));
		} catch (Exception e) {
			logger.error("Exception::", e);
		}

		return userStarRatingsDo;
	}

	/**
	 * Gets all the ratings and reviews for the resources filtered by user.
	 * @param resourceId {@link String}
	 * @param gooruUid {@link String}
	 *
	 * @return {@link ArrayList<StarRatingsDo> }
	 */
	@Override
	public ArrayList<StarRatingsDo> getResourceRatingWithReviews(String resourceId, String gooruUid,int offSet) {
		JsonRepresentation jsonRep=null;
		JSONObject jsonObject = null;
		String url = "";
		String limit="20";
		try {
			if(gooruUid!=null&& !gooruUid.equals("")){
				String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_LOGGED_IN_USER_RATINGS_REVIEWS,resourceId);
				url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.CREATOR_UID, gooruUid);
			}else{
				String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_USER_RATINGS_REVIEWS,resourceId);
				Map<String, String> params = new LinkedHashMap<String, String>();
				params.put(GooruConstants.OFFSET, String.valueOf(offSet));
				params.put(GooruConstants.LIMIT, limit);
				url = AddQueryParameter.constructQueryParams(partialUrl, params);
			}
			JsonResponseRepresentation jsonResponseRep=ServiceProcessor.get(url, getRestUsername(), getRestPassword());
			jsonRep=jsonResponseRep.getJsonRepresentation();
			if(jsonRep!=null && jsonRep.getSize()!=-1){
			jsonObject = jsonRep.getJsonObject();
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
			}
		return deserializeGetResourceRatingWithReviews(jsonObject);
	}

	/**
	 * De-Serialize Resource ratings with reviews.
	 * @param jsonObject {@link JSONObject}
	 * @return {@link  ArrayList<StarRatingsDo> }
	 */

	private ArrayList<StarRatingsDo> deserializeGetResourceRatingWithReviews(JSONObject jsonObject) {
		ArrayList<StarRatingsDo> starRatingsList=new ArrayList<StarRatingsDo>();
		try {
			if(!jsonObject.isNull("totalHitCount") && jsonObject.getInt("totalHitCount")>0){
				if(!jsonObject.isNull("searchResults")){
				  JSONArray jsonArray=jsonObject.getJSONArray("searchResults");
					 if(jsonArray!=null && jsonArray.length()>0){
							for(int i=0;i<jsonArray.length();i++){
								JSONObject resourceRatingsJsonObject=jsonArray.getJSONObject(i);
								StarRatingsDo starRatingsDo =deserializeResourceStarRatings(resourceRatingsJsonObject);
								starRatingsDo.setTotalHitCount(jsonObject.getInt("totalHitCount"));
								starRatingsList.add(starRatingsDo);
							}
					 }
				}
			}

		} catch (Exception e) {
			logger.error("Exception::", e);
		}

		return starRatingsList;
	}

	@Override
	public ArrayList<StarRatingsDo> updateResourceStarReviews(String deleteRatingGooruOid, Integer score, String userReview) {
		JsonRepresentation jsonRepresentation=null;
		try {
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.UPDATE_STAR_RATINGS,deleteRatingGooruOid);
			JSONObject updateStarRatingsJsonObj = new JSONObject();
			updateStarRatingsJsonObj.put(SCORE,score);
			updateStarRatingsJsonObj.put(FREE_TEXT,userReview);
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(),updateStarRatingsJsonObj.toString());
			jsonRepresentation=jsonResponseRep.getJsonRepresentation();

		} catch (Exception e) {
			logger.error("Exception::", e);
		}

		return deserializeStarRatings(jsonRepresentation);
	}

	@Override
	public void deleteRating(String deleteRatingGooruOid) {
		try {
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.DELETE_RATINGS,deleteRatingGooruOid);
			getLogger().info("-- Delete review --- "+url);
			ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
		}catch(Exception e){
			logger.error("Exception::", e);
		}
	}

	@Override
	public int getGoogleDriveFileStatusCode(String fileUrl){
		int responseStatusCode=new WebService(fileUrl,true).getStatusCode("GET");
		return 	responseStatusCode;
	}

	@Override
	public Map<String, String> getYoutubeFeedCallback(String utubeId)
			throws GwtException, ServerDownException {
		Map<String, String> youtubeValues=new HashMap<String, String>();
		try{
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put(GooruConstants.ID, utubeId);
			params.put(GooruConstants.YOUTUBE_KEY, getYoutubeApiKey());
			params.put(GooruConstants.YOUTUBE_PART, YOUTUBE_PART_DETAILS);
			String url=AddQueryParameter.constructQueryParams(getYouTubeApiUrl(), params);

			getLogger().info("getyoutube feed call back url::"+url);
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url);
			JsonRepresentation jsonRep = jsonResponseRep.getJsonRepresentation();
			if(jsonRep!=null && jsonRep.getSize()!=-1){
				if(jsonRep.getJsonObject()!=null && jsonRep.getJsonObject().getJSONArray(ITEMS) != null && jsonRep.getJsonObject().getJSONArray(ITEMS).length() > 0){
					JSONObject resourceJsonObject = jsonRep.getJsonObject().getJSONArray(ITEMS).getJSONObject(0).getJSONObject(STATUS);

					String embed=resourceJsonObject.getString("embeddable");
					youtubeValues.put("embed", embed);
				}
			}

		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return youtubeValues;
	}


	public InsightsCollectionDo getInsightsCollectionSummary(String collectionId,String classpageId,String sessionId,String userId){
		String formdata=insightsJsonPayload(collectionId,classpageId,sessionId,getLoggedInUserUid());
		String partialUrl = UrlGenerator.generateUrl(getHomeEndPoint(), UrlToken.GET_COLLECTION_SUMMARY,collectionId);
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.DATA, formdata);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url);
		JsonRepresentation jsonRep = jsonResponseRep.getJsonRepresentation();
		InsightsCollectionDo insightCollectionDo=null;
		if(jsonRep!=null){
			try {
				JSONObject jsonObject=jsonRep.getJsonObject();
				JSONArray contentJsonArrayjsonObject=jsonObject.getJSONArray("content");
				if(contentJsonArrayjsonObject!=null){
					if(contentJsonArrayjsonObject.length()>0){
						JSONObject contentJsonObject=contentJsonArrayjsonObject.getJSONObject(0);
						insightCollectionDo=JsonDeserializer.deserialize(contentJsonObject.toString(), InsightsCollectionDo.class);

					}
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return insightCollectionDo;

	}

	public String insightsJsonPayload(String collectionId,String classpageId,String sessionId,String userId){
		JSONObject jsonObject=new JSONObject();
		JSONObject filterJsonObject=new JSONObject();

		try {
			jsonObject.put("fields", "thumbnail,completionStatus,userCount,lastAccessed,timeSpent,views,avgTimeSpent,gooruOId,title,description,avgReaction,score,totalQuestionCount");
			filterJsonObject.put("userUId", userId);
			filterJsonObject.put("session", "CS");
			filterJsonObject.put("sessionId", sessionId);
			filterJsonObject.put("classId", classpageId);
			jsonObject.put("filters", filterJsonObject);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return jsonObject.toString();
	}

	public FolderWhatsNextCollectionDo getNextCollectionFromToc(String folderId,String collectionItemId){
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GETNEXTTOCCOLLECTION, collectionItemId);
		String url=AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.SHARING, GooruConstants.PUBLIC);
		getLogger().info("-- FolderWhatsNextCollectionDo API - - - - "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializeFolderTocList(jsonRep);
	}
	public FolderWhatsNextCollectionDo deserializeFolderTocList(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), new TypeReference<FolderWhatsNextCollectionDo>() {});
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return new FolderWhatsNextCollectionDo();
	}

	@Override
	public void updateSessionActivityItem(String gooruOid, String status, String sessionTrackerId) throws GwtException, ServerDownException {

	}

	@Override
	public void getUpdateSessionActivityItemForRatReac(int emoticRatingNumber,String gooruOid, String isRatingsReactions, String sessionId) throws GwtException, ServerDownException {



	}


	@Override
	public List<UserPlayedSessionDo> getPreviousSessionDataForUser(String gooruUid, String classGooruId, String lessonGooruId, String unitGooruId, String courseGooruId, String assessmentId){
		List<UserPlayedSessionDo> userPlayedSessions = null;

		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getAnalyticsEndPoint(), UrlToken.GET_LAST_PLAYER_ASSESSMENT_INFO, assessmentId);

		url = AddQueryParameter.constructQueryParams(url, "classGooruId", classGooruId);
		url = AddQueryParameter.constructQueryParams(url, "userUid", gooruUid);
		url = AddQueryParameter.constructQueryParams(url, "lessonGooruId", lessonGooruId);
		url = AddQueryParameter.constructQueryParams(url, "unitGooruId", unitGooruId);
		url = AddQueryParameter.constructQueryParams(url, "courseGooruId", courseGooruId);
		url = AddQueryParameter.constructQueryParams(url, "fetchOpenSession", "true");

		getLogger().info("getPreviousSessionDataForUser - url: "+url);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();

		JSONObject resourceObj;
		try {
			resourceObj = jsonRep.getJsonObject();
			if(resourceObj!=null){
				if(resourceObj.getJSONArray("content") != null && resourceObj.optJSONArray("content") != null){
					userPlayedSessions = JsonDeserializer.deserialize(resourceObj.getJSONArray("content").toString(), new TypeReference<ArrayList<UserPlayedSessionDo>>() {});
				}
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}

		return userPlayedSessions;
	}


}
