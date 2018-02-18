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
/**
 *
 */
package org.ednovo.gooru.application.server.deserializer;

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Search Team
 *
 */
public abstract class SearchDeSerializer<T extends ResourceSearchResultDo>  extends DeSerializer{


	private static String SEARCH_RESULTS = "searchResults";
	private static String SEARCH_HITS = "totalHitCount";
	public static String URL = "url";
	public static String RESOURCE_TITLE = "title";
	public static String RESOURCE_TYPE = "resourceType";
	public static String RESOURCE_TYPE_NAME = "name";
	public static final String CATEGORY="category";
	public static String COURSENAMES = "courseNames";
	public static String RESOURCE_DESCRIPTION = "description";
	public static String COLLECTIONCOUNT = "scollectionCount";
	public static String RESOURCESCOUNT = "collectionItemCount";
	public static String TOTALVIEWS = "viewCount";
	public static final String OWNER_FIRST_NAME = "userFirstName";
	public static final String OWNER_LAST_NAME = "userLastName";
	public static final String OWNER_NAME_DISPLAY = "usernameDisplay";
	public static final String OWNER_USERNAME = "username";
	public static final String OWNER_PROFILE_USER_VISIBILITY = "profileUserVisibility";
	public static final String OWNER_GOORU_UID = "gooruUId";
	public static final String STANDARD_CODE = "code";
	public static final String STANDARD_DESCRIPTION = "description";
	public static final String TAXONOMY_SUBJECT = "subject";
	public static final String TAXONOMY_COURSE = "course";
	public static final String TAXONOMY_UNIT = "unit";
	public static final String TAXONOMY_TOPIC = "topic";
	public static final String TAXONOMY_LESSON = "lesson";
	public static final String CURRICULUM = "curriculum";
	public static final String CURRICULUM_CODE = "curriculumCode";
	public static final String CURRICULUM_DESCRIPTION = "curriculumDesc";
	public static final String TAXONOMY_DATA_SET = "taxonomyDataSet";
	public static final String GRADE = "grade";
	public static final String TAGS = "tags";
	public static final String AVERAGE_TIME = "averageTime";
	public static final String SHARED_COUNT = "collaboratorCount";
	public static final String GOORU_OID = "gooruOid";
	public static final String NO_OF_PAGES = "numOfPages";
	public static final String DURATION_IN_SEC = "durationInSec";
	public static final String LICENSE = "license";
	public static final String VOTES_UP = "votesUp";
	public static final String VOTES_DOWN = "votesDown";
	public static final String COLLABORATOR_COUNT = "collaboratorCount";
	public static final String ASSETURI = "assetURI";
	public static final String RESOURCE_SOURCE="resourceSource";
	public static String QUESTION_TEXT = "questionText";
	public static String EXPLANATION="explanation";
	public static String TIME_TO_COMPLETE_IN_SEC="timeToCompleteInSec";
	public static String QUESTION_TYPE_NAME="typeName";
	public static final String COLLECTION_ITEMS="collectionItems";

	public static final String MEDIA_TYPE = "mediaType";

	public static final String USER_COUNT = "resourceUsedUserCount";

	public static final String ADD_COUNT = "resourceAddedCount";

	public static final String RESOURCE_FORMAT = "resourceFormat";

	public static final String RATINGS = "ratings";

	public static final String AGGREGATOR = "aggregator";

	public static final String PUBLISHER = "publisher";

	public static final String CREATOR = "creator";
	public static final String SUGGEST_RESULTS ="suggestResults";

	/**
	 * Deserialize the search json object
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @param searchDo instance of {@link SearchDo}
	 */
	private static final Logger logger = LoggerFactory.getLogger(SearchDeSerializer.class);
	public void deserialize(JsonRepresentation jsonRep, SearchDo<T> searchDo, String profileEndPoint) {
		searchDo.setSearchResults(new ArrayList<T>());
		try {
			if (jsonRep != null) {
				if(!jsonRep.getJsonObject().isNull("spellCheckQueryString"))
				{
				searchDo.setSpellCheckQueryString(jsonRep.getJsonObject().getString("spellCheckQueryString"));
				searchDo.setUserQueryString(jsonRep.getJsonObject().getString("userQueryString"));
				}
				else
				{
				searchDo.setSpellCheckQueryString(null);
				searchDo.setUserQueryString(null);
				}
				searchDo.setSearchHits(stringtoInteger(jsonRep.getJsonObject(), SEARCH_HITS, 0));
				JSONArray searchResultJsonArray = jsonRep.getJsonObject().getJSONArray(SEARCH_RESULTS);
				List<T> collectionSearchResults = searchDo.getSearchResults();
				for (int pointer = 0; pointer < searchResultJsonArray.length(); pointer++) {
					T record = deserializeRecord(searchResultJsonArray.getJSONObject(pointer));
					if (record != null) {
						if(!profileEndPoint.isEmpty()){
							record.setAssetURI(profileEndPoint);
						}
						collectionSearchResults.add(record);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
			throw new RuntimeException(e.getCause());
		}
	}
	public void deserializeJsonObject(String jsonRepObj, SearchDo<T> searchDo, String profileEndPoint) {
		JSONObject jsonRep;
		searchDo.setSearchResults(new ArrayList<T>());
		try {
			jsonRep=new JSONObject(jsonRepObj);
			if (jsonRep != null) {
				if(!jsonRep.isNull("spellCheckQueryString"))
				{
				searchDo.setSpellCheckQueryString(jsonRep.getString("spellCheckQueryString"));
				searchDo.setUserQueryString(jsonRep.getString("userQueryString"));
				}
				else
				{
				searchDo.setSpellCheckQueryString(null);
				searchDo.setUserQueryString(null);
				}
				searchDo.setSearchHits(stringtoInteger(jsonRep, SEARCH_HITS, 0));
				JSONArray searchResultJsonArray = jsonRep.getJSONArray(SEARCH_RESULTS);
				List<T> collectionSearchResults = searchDo.getSearchResults();
				for (int pointer = 0; pointer < searchResultJsonArray.length(); pointer++) {
					T record = deserializeRecord(searchResultJsonArray.getJSONObject(pointer));
					if (record != null) {
						if(!profileEndPoint.isEmpty()){
							record.setAssetURI(profileEndPoint);
						}
						collectionSearchResults.add(record);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
	}
	public void deserializeSuggestedResources(JsonRepresentation jsonRep, SearchDo<T> searchDo) {
		searchDo.setSuggestResults(new ArrayList<T>());
		try {
			if (jsonRep != null) {
				JSONArray suggestedResultJsonArray = jsonRep.getJsonArray().getJSONObject(0).getJSONArray(SUGGEST_RESULTS);
				List<T> suggestedSearchResults = searchDo.getSuggestResults();
				for (int pointer = 0; pointer < suggestedResultJsonArray.length(); pointer++) {
					T record = deserializeRecord(suggestedResultJsonArray.getJSONObject(pointer));
					if (record != null) {
						suggestedSearchResults.add(record);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
	}
	public void deserializeCollectionItems(JsonRepresentation jsonRep, SearchDo<T> searchDo){
		searchDo.setSearchResults(new ArrayList<T>());
		try{
			if(jsonRep!=null){
				JSONArray searchResultJsonArray = jsonRep.getJsonObject().getJSONArray(COLLECTION_ITEMS);
				List<T> collectionSearchResults = searchDo.getSearchResults();
				int collectionItemsCount=searchResultJsonArray.length();
				searchDo.setCollectionItemsCount(collectionItemsCount);
				for (int pointer = 0; pointer < collectionItemsCount; pointer++) {
					T record = deserializeRecord(searchResultJsonArray.getJSONObject(pointer));
					if (record != null) {
						collectionSearchResults.add(record);
					}
				}
			}
		} catch(Exception e){
			logger.error("Exception::", e);
		}

	}

	public void deserializeV2CollectionItems(JsonRepresentation jsonRep, SearchDo<T> searchDo){
		searchDo.setSearchResults(new ArrayList<T>());
		try{
			if(jsonRep!=null){
				JSONArray searchResultJsonArray = jsonRep.getJsonArray();
				List<T> collectionSearchResults = searchDo.getSearchResults();
				int collectionItemsCount=searchResultJsonArray.length();
				searchDo.setCollectionItemsCount(collectionItemsCount);
				for (int pointer = 0; pointer < collectionItemsCount; pointer++) {
					T record = deserializeRecord(searchResultJsonArray.getJSONObject(pointer));
					if (record != null) {
						collectionSearchResults.add(record);
					}
				}
			}
		} catch(Exception e){
			logger.error("Exception::", e);
		}

	}
	public abstract T deserializeRecord(JSONObject recordJsonObject);

}
