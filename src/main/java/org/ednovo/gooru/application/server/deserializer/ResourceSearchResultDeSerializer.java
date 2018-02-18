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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.model.content.LicenseDo;
import org.ednovo.gooru.application.shared.model.content.ResourceSourceDo;
import org.ednovo.gooru.application.shared.model.content.ResourceTypeDo;
import org.ednovo.gooru.application.shared.model.content.SearchRatingsDo;
import org.ednovo.gooru.application.shared.model.content.SearchResourceFormatDO;
import org.ednovo.gooru.application.shared.model.content.SearchResultsTagsDo;
import org.ednovo.gooru.application.shared.model.content.TagDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.shared.util.ResourceImageUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Search Team
 *
 */
@Component
public class ResourceSearchResultDeSerializer extends SearchDeSerializer<ResourceSearchResultDo> {

	private static final String VIDEO_YOUTUBE = "video/youtube";

	private static final String THUMBNAILS = "thumbnails";

	private static final String URL = "url";

	private static final String ASSESSMENT_QUESTION = "assessment-question";

	private static final String TAG_SET = "tagSet";

	private static final String RESOURCE_TAGS = "resourceTags";

	private static final Logger logger = LoggerFactory.getLogger(ResourceSearchResultDeSerializer.class);

	@Override
	public ResourceSearchResultDo deserializeRecord(JSONObject recordJsonObject) {
		ResourceSearchResultDo resourceSearchResultDo = new ResourceSearchResultDo();
		try {
			JSONObject resourceType = recordJsonObject.getJSONObject(RESOURCE_TYPE);
			resourceSearchResultDo.setResourceType(JsonDeserializer.deserialize(resourceType.toString(), ResourceTypeDo.class));
			resourceSearchResultDo.setResourceTypeString((String) resourceType.get(RESOURCE_TYPE_NAME));
			resourceSearchResultDo.setGooruUId(getJsonString(recordJsonObject, "gooruUId"));
			JSONObject resourceSourceJson = recordJsonObject.getJSONObject(RESOURCE_SOURCE);
			ResourceSourceDo resourceSourceDo=JsonDeserializer.deserialize(resourceSourceJson.toString(), ResourceSourceDo.class);
			resourceSearchResultDo.setResourceSource(resourceSourceDo);

			JSONObject resourceFormat = recordJsonObject.getJSONObject(RESOURCE_FORMAT);
			SearchResourceFormatDO resourceFormatDO =JsonDeserializer.deserialize(resourceFormat.toString(), SearchResourceFormatDO.class);
			resourceSearchResultDo.setResourceFormat(resourceFormatDO);

			JSONObject resourceRating = recordJsonObject.getJSONObject(RATINGS);
			SearchRatingsDo searchRatingsDo =JsonDeserializer.deserialize(resourceRating.toString(), SearchRatingsDo.class);
			resourceSearchResultDo.setRatings(searchRatingsDo);

		} catch (JSONException e1) {
			logger.error("Exception::", e1);
		}

		try {
			if (resourceSearchResultDo.getResourceTypeString() != null && resourceSearchResultDo.getResourceTypeString().equalsIgnoreCase(VIDEO_YOUTUBE)) {
				resourceSearchResultDo.setUrl(ResourceImageUtil.youtubeImageLink(ResourceImageUtil.getYoutubeVideoId(getJsonString(recordJsonObject, URL))));
			} else {
				if(!recordJsonObject.isNull(THUMBNAILS))
				{
				resourceSearchResultDo.setUrl(getJsonString(recordJsonObject.getJSONObject(THUMBNAILS), URL));
				}
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		resourceSearchResultDo.setResourceTitle(getJsonString(recordJsonObject, RESOURCE_TITLE));
		resourceSearchResultDo.setDescription(getJsonString(recordJsonObject, RESOURCE_DESCRIPTION));

		resourceSearchResultDo.setScollectionCount(getJsonInteger(recordJsonObject, COLLECTIONCOUNT));

		if(resourceSearchResultDo.getResourceType().getName().equals(ASSESSMENT_QUESTION)){
			resourceSearchResultDo.setDurationInSec(getJsonString(recordJsonObject, TIME_TO_COMPLETE_IN_SEC));
			resourceSearchResultDo.setQuestionType(getJsonString(recordJsonObject, QUESTION_TYPE_NAME));

		}else{
			resourceSearchResultDo.setDurationInSec(getJsonString(recordJsonObject, DURATION_IN_SEC));
		}

		resourceSearchResultDo.setVotesUp(stringtoInteger(recordJsonObject, VOTES_UP, 0));
		resourceSearchResultDo.setVotesDown(stringtoInteger(recordJsonObject, VOTES_DOWN, 0));
		resourceSearchResultDo.setGooruOid(getJsonString(recordJsonObject, GOORU_OID));
		resourceSearchResultDo.setCategory(getJsonString(recordJsonObject, CATEGORY));
		resourceSearchResultDo.setTotalViews(stringtoInteger(recordJsonObject, TOTALVIEWS, 0));

		if (getJsonString(recordJsonObject, AGGREGATOR) != null) {
			try {
				JSONArray aggregatorArrayObj = new JSONArray(getJsonString(recordJsonObject, AGGREGATOR));
				List<String> aggregatorList=new ArrayList<String>();
				for(int i=0;i<aggregatorArrayObj.length();i++){
					aggregatorList.add(aggregatorArrayObj.getString(i).toString());

				}
				resourceSearchResultDo.setAggregator(aggregatorList);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		if (getJsonString(recordJsonObject, PUBLISHER) != null) {
			try {
				JSONArray publisherArrayObj = new JSONArray(getJsonString(recordJsonObject, PUBLISHER));
				List<String> bublisherList=new ArrayList<String>();
				for(int i=0;i<publisherArrayObj.length();i++){
					bublisherList.add(publisherArrayObj.getString(i).toString());

				}
				resourceSearchResultDo.setPublisher(bublisherList);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}

		/**
		 * Deserialzing Resource tags.
		 */

		try {
			if(recordJsonObject.get(RESOURCE_TAGS)!= null){
				List<SearchResultsTagsDo> searchTags = new ArrayList<SearchResultsTagsDo>();
				searchTags.addAll((ArrayList<SearchResultsTagsDo>) JsonDeserializer.deserialize(recordJsonObject.getJSONArray(RESOURCE_TAGS).toString(),new TypeReference<List<SearchResultsTagsDo>>() {}));
				resourceSearchResultDo.setResourceTags(searchTags);
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}


		resourceSearchResultDo.setAssetURI(getJsonString(recordJsonObject, ASSETURI));
		resourceSearchResultDo.setMediaType(getJsonString(recordJsonObject, MEDIA_TYPE));
		resourceSearchResultDo.setResourceAddedCount(getJsonInteger(recordJsonObject, ADD_COUNT));
		resourceSearchResultDo.setResourceUsedUserCount(getJsonInteger(recordJsonObject, USER_COUNT));

		try {
			if (getJsonString(recordJsonObject, TAXONOMY_DATA_SET) != null) {
				JSONObject taxonomyDataSet = new JSONObject(getJsonString(recordJsonObject, TAXONOMY_DATA_SET));
				JSONObject curriculum = taxonomyDataSet.getJSONObject(CURRICULUM);
				JSONArray standardCodes = (JSONArray) curriculum.get(CURRICULUM_CODE);
				JSONArray standardDescriptions = (JSONArray) curriculum.get(CURRICULUM_DESCRIPTION);
				List<Map<String, String>> standards = new ArrayList<Map<String, String>>();
				for (int i = 0; i < standardCodes.length(); i++) {
					Map<String, String> standard = new HashMap<String, String>();
					standard.put(STANDARD_CODE, (String) standardCodes.get(i));
					try{
					if (standardDescriptions.get(i) != null) {
						standard.put(STANDARD_DESCRIPTION, (String) standardDescriptions.get(i));
					}
					}catch(Exception ex){
						logger.error("Exception::", ex);
					}
					standards.add(standard);
				}
				resourceSearchResultDo.setStandards(standards);
				resourceSearchResultDo.setSubjectNames(convertJSONArrayToList(((JSONArray) taxonomyDataSet.get(TAXONOMY_SUBJECT))));
				resourceSearchResultDo.setCourseNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_COURSE)));
				resourceSearchResultDo.setUnitNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_UNIT)));
				resourceSearchResultDo.setTopicNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_TOPIC)));
				resourceSearchResultDo.setLessonNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_LESSON)));
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		resourceSearchResultDo.setAverageTime(getJsonString(recordJsonObject, AVERAGE_TIME));
		resourceSearchResultDo.setSharedCount(stringtoInteger(recordJsonObject, SHARED_COUNT, 0));
		resourceSearchResultDo.setGrade(getJsonString(recordJsonObject, GRADE));
		resourceSearchResultDo.setTags(getJsonString(recordJsonObject, TAGS));
		try {
			JSONObject license = recordJsonObject.getJSONObject(LICENSE);
			LicenseDo licenseDo=JsonDeserializer.deserialize(license.toString(), LicenseDo.class);
			resourceSearchResultDo.setLicense(licenseDo);
			if((!recordJsonObject.isNull(TAG_SET))&& recordJsonObject.get(TAG_SET) != null && !recordJsonObject.get(TAG_SET).equals("") && !recordJsonObject.get(TAG_SET).equals(null) ){
			JSONArray tagSetArray = (JSONArray) recordJsonObject.get(TAG_SET);
			Set<TagDo> tagSet= new HashSet<TagDo>();
			if(tagSetArray !=null){
				for(int i=0;i<tagSetArray.length();i++){
					tagSet.add(JsonDeserializer.deserialize(tagSetArray.get(i).toString(), TagDo.class));
				}
				resourceSearchResultDo.setTagSet(tagSet);
			}
			}

		} catch (JSONException e) {
			logger.error("Exception::", e);
		}

		return resourceSearchResultDo;
	}

	public void deserializeSuggestedResorcesRecord(JSONObject recordJsonObject) {
		// TODO Auto-generated method stub

	}

}
