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
package org.ednovo.gooru.application.server.deserializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.model.content.ResourceTypeDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.user.ResponseStatusDo;
import org.ednovo.gooru.shared.util.ResourceImageUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ResourceDeserializer extends DeSerializer {

	private static final String RESOURCE = "resource";
	private static final String LABEL = "label";
	private static final String CATEGORY = "category";
	private static final String COLLECTIONCOUNT = "scollectionCount";
	private static final String ID = "id";
	private static final String DESCRIPTION = "description";
	private static final String TYPE = "type";
	private static final String RESOURCE_SOURCE = "resourceSource";
	private static final String RESOURCE_SOURCE_ATTRIBUTION = "attribution";
	private static final String COLLECTON_ITEMS = "collectionItems";
	private static final String RESOURCE_TYPE = "resourceTypeDo";
	public static String ACTIVE_STATUS = "activeStatus";
	public static String ATTRIBUTION = "attribution";
	public static String DOMAIN_NAME = "domainName";
	public static String RESOURCE_SOURCE_ID = "resourceSourceId";
	private static final String VIDEO_YOUTUBE = "video/youtube";
	private static final String YOUTUBE_URL = "url";
	private static final String THUMBNAILS = "thumbnails";
	private static final String DATA = "data";
	private static final String ITEMS = "items";
	private static final String CONTENTDETAILS = "contentDetails";
	private static final String DURATION = "duration";
	private static final String ERROR = "error";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String GOORU_UID = "gooruUId";
	private static final String TOKEN_EXPIRED = "tokenExpired";
	private static final String USER = "user";
	private static final String USER_NAME = "username";
	private static final String ASSET_URI = "assetURI";

	private static final Logger logger = LoggerFactory.getLogger(ResourceDeserializer.class);
	/**
	 * Deserialize json object to {@link ResourceSearchResultDo}
	 * @param resourceJsonObject instance of {@link JSONObject}
	 * @return instance of {@link ResourceSearchResultDo}
	 * @throws JSONException
	 */
	public ResourceSearchResultDo deserialize(JSONObject resourceJsonObject) throws JSONException {
		ResourceSearchResultDo resultDo = new ResourceSearchResultDo();
		resultDo.setResourceTitle(getJsonString(resourceJsonObject, LABEL));
		resultDo.setResourceTypeString(getJsonString(resourceJsonObject, TYPE));
		if (resultDo.getResourceTypeString() != null && resultDo.getResourceTypeString().equalsIgnoreCase(VIDEO_YOUTUBE)) {
			resultDo.setUrl(ResourceImageUtil.youtubeImageLink(ResourceImageUtil.getYoutubeVideoId(getJsonString(resourceJsonObject, YOUTUBE_URL))));
		} else {
			resultDo.setUrl(getJsonString(resourceJsonObject.getJSONObject(THUMBNAILS), YOUTUBE_URL));
		}
		resultDo.setCategory(getJsonString(resourceJsonObject, CATEGORY));
		resultDo.setScollectionCount(getJsonInteger(resourceJsonObject, COLLECTIONCOUNT));
		resultDo.setGooruOid(getJsonString(resourceJsonObject, ID));
		resultDo.setDescription(getJsonString(resourceJsonObject, DESCRIPTION));
		resultDo.setAssetURI(getJsonString(resourceJsonObject, "assetURI"));
		resultDo.setAttribution(getJsonString(resourceJsonObject.getJSONObject(RESOURCE_SOURCE), RESOURCE_SOURCE_ATTRIBUTION));
		JSONObject resourceType = resourceJsonObject.getJSONObject(RESOURCE_TYPE);
		if (resourceType != null) {
			resultDo.setResourceType(JsonDeserializer.deserialize(resourceType.toString(), ResourceTypeDo.class));
		}
		return resultDo;
	}

	/**
	 * Deserialize json object to list of {@link ResourceSearchResultDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return list of resource search result
	 */
	public List<ResourceSearchResultDo> getResourceList(JsonRepresentation jsonRep) {
		JSONObject resourceJsonObject;
		List<ResourceSearchResultDo> resultDos = null;
		try {
			resourceJsonObject = jsonRep.getJsonObject();
			resultDos = new ArrayList<ResourceSearchResultDo>();
			JSONArray collectionItems = resourceJsonObject.getJSONArray(COLLECTON_ITEMS);
			for (int i = 0; i < collectionItems.length(); i++) {
				JSONObject resource = (JSONObject) collectionItems.get(i);
				JSONObject resourceJsonArray = resource.getJSONObject(RESOURCE);
				resultDos.add(deserialize(resourceJsonArray));
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return resultDos;
	}

	/**
	 * Get youtube video duration from json object
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return duration of youtube video
	 */
	public String serializeYoutubeInfo(JsonRepresentation jsonRep) {
		JSONObject resourceJsonObject;
		String videoDuration = null;
		try {
			resourceJsonObject = jsonRep.getJsonObject().getJSONArray(ITEMS).getJSONObject(0).getJSONObject(CONTENTDETAILS);
			videoDuration =getJsonString(resourceJsonObject, DURATION);

			Duration dur;
			try {
				dur = DatatypeFactory.newInstance().newDuration(videoDuration);
			} catch (DatatypeConfigurationException e) {
				throw new RuntimeException("message", e);
			}
			int hours = dur.getHours();
			int min = dur.getMinutes();
			int sec = dur.getSeconds();
			int milliseconds = (hours * 60 * 60) + (min * 60) + sec;
			videoDuration = milliseconds +"";
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return videoDuration;
	}

	/**
	 * Deserialize json object to map which contains error and gooruUid values
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return forgotPassword
	 */
	public Map<String, Object> forgotPassword(JsonRepresentation jsonRep,int code,String errorMessage, ResponseStatusDo responseDo) {
		JSONObject jsonObject;
		Map<String, Object> forgotPassword = new HashMap();
			try {
				if(code==400 || code==404 ){
					forgotPassword.put("code", 400);
					forgotPassword.put("errorCode", responseDo.getErrorCode());
					forgotPassword.put("errorMessage", responseDo.getErrorMessage());
					forgotPassword.put("status", responseDo.getStatus());
				}else{
					if(jsonRep != null && jsonRep.getSize() != -1){
						jsonObject = jsonRep.getJsonObject();
						forgotPassword.put(GOORU_UID, (getJsonString(jsonObject, GOORU_UID)));
					}
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		/*}*/
		return forgotPassword;
	}

	/**
	 * Deserialize json object to map which contains userName and token expired details
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return forgotPassword
	 */
	public Map<String, Object> resetPassword(JsonRepresentation jsonRep,int code,String errorMessage, ResponseStatusDo responseDo) {
		JSONObject jsonObject;
		Map<String, Object> resetPassword = new HashMap<String, Object>();
			try {
				if(code==400){
					resetPassword.put("statusCode", 400);
					resetPassword.put("errorCode", responseDo.getErrorCode());
					resetPassword.put("statusMessage", errorMessage);
				}else{
					if (jsonRep != null ) {
						jsonObject = jsonRep.getJsonObject();
						JSONObject userJsonObject = jsonObject.getJSONObject(USER);
						resetPassword.put("statusCode", 200);
						resetPassword.put(USER_NAME, (getJsonString(userJsonObject, USER_NAME)));
					}
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		return resetPassword;
	}
}
