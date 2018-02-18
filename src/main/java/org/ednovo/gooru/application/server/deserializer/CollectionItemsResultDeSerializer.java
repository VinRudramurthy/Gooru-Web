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
import org.ednovo.gooru.application.shared.model.content.TagDo;
import org.ednovo.gooru.application.shared.model.search.CollectionItemSearchResultDo;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.shared.util.ResourceImageUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Search Team
 *
 */
@Component
public class CollectionItemsResultDeSerializer extends SearchDeSerializer<CollectionItemSearchResultDo> {

	private static final String VIDEO_YOUTUBE = "video/youtube";

	private static final String THUMBNAILS = "thumbnails";

	private static final String URL = "url";

	private static final String ASSESSMENT_QUESTION = "assessment-question";

	private static final String TAG_SET = "tagSet";

	private static final String RESOURCE="resource";

	private static final String COLLECTION_ITEM_ID="collectionItemId";

	private static final String ITEM_SEQUENCE="itemSequence";

	private static final String VIEWS="views";

	private static final String RATINGS="ratings";

	private static final Logger logger = LoggerFactory.getLogger(CollectionItemsResultDeSerializer.class);

	@Override
	public CollectionItemSearchResultDo deserializeRecord(JSONObject collectionItemJsonObject) {
		CollectionItemSearchResultDo collectionItemSearchResultDo=new CollectionItemSearchResultDo();
		try{
			JSONObject recordJsonObject=collectionItemJsonObject.getJSONObject(RESOURCE);
			collectionItemSearchResultDo.setCollectionItemId(getJsonString(collectionItemJsonObject, COLLECTION_ITEM_ID));
			collectionItemSearchResultDo.setItemSequence(stringtoInteger(collectionItemJsonObject, ITEM_SEQUENCE));
			try {

				if(!recordJsonObject.isNull(RATINGS)){

					JSONObject ratingsObj = recordJsonObject.getJSONObject(RATINGS);
					collectionItemSearchResultDo.setRatings(JsonDeserializer.deserialize(ratingsObj.toString(), SearchRatingsDo.class));
				}

				if(!recordJsonObject.isNull(RESOURCE_TYPE)){
					JSONObject resourceType = recordJsonObject.getJSONObject(RESOURCE_TYPE);
					collectionItemSearchResultDo.setResourceType(JsonDeserializer.deserialize(resourceType.toString(), ResourceTypeDo.class));
					collectionItemSearchResultDo.setResourceTypeString((String) resourceType.get(RESOURCE_TYPE_NAME));
				}


				//JSONObject resourceSourceJson = recordJsonObject.getJSONObject(RESOURCE_SOURCE);
				if(getJsonString(recordJsonObject, RESOURCE_SOURCE) != null){
					JSONObject resourceSourceJson =new JSONObject(getJsonString(recordJsonObject, RESOURCE_SOURCE));
					ResourceSourceDo resourceSourceDo=JsonDeserializer.deserialize(resourceSourceJson.toString(), ResourceSourceDo.class);;
					collectionItemSearchResultDo.setResourceSource(resourceSourceDo);
				}
			} catch (JSONException e1) {
				logger.error("Exception::", e1);
			}
			try {
				if (collectionItemSearchResultDo.getResourceTypeString() != null && collectionItemSearchResultDo.getResourceTypeString().equalsIgnoreCase(VIDEO_YOUTUBE)) {
					collectionItemSearchResultDo.setUrl(ResourceImageUtil.youtubeImageLink(ResourceImageUtil.getYoutubeVideoId(getJsonString(recordJsonObject, URL))));
				} else {
					collectionItemSearchResultDo.setUrl(getJsonString(recordJsonObject.getJSONObject(THUMBNAILS), URL));
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
			collectionItemSearchResultDo.setResourceTitle(getJsonString(recordJsonObject, RESOURCE_TITLE));
			collectionItemSearchResultDo.setMediaType(getJsonString(recordJsonObject, MEDIA_TYPE));
			collectionItemSearchResultDo.setDescription(getJsonString(recordJsonObject, RESOURCE_DESCRIPTION));
			if(collectionItemSearchResultDo.getResourceType().getName().equals(ASSESSMENT_QUESTION)){
				collectionItemSearchResultDo.setDurationInSec(getJsonString(recordJsonObject, TIME_TO_COMPLETE_IN_SEC));
				collectionItemSearchResultDo.setQuestionType(getJsonString(recordJsonObject, QUESTION_TYPE_NAME));

			}else{
				collectionItemSearchResultDo.setDurationInSec(getJsonString(recordJsonObject, DURATION_IN_SEC));
			}

			collectionItemSearchResultDo.setVotesUp(stringtoInteger(recordJsonObject, VOTES_UP, 0));
			collectionItemSearchResultDo.setVotesDown(stringtoInteger(recordJsonObject, VOTES_DOWN, 0));
			collectionItemSearchResultDo.setGooruOid(getJsonString(recordJsonObject, GOORU_OID));
			collectionItemSearchResultDo.setCategory(getJsonString(recordJsonObject, CATEGORY));
			collectionItemSearchResultDo.setTotalViews(stringtoInteger(recordJsonObject, TOTALVIEWS, 0));
			collectionItemSearchResultDo.setTotalViews(stringtoInteger(recordJsonObject, VIEWS, 0));
			collectionItemSearchResultDo.setNumOfPages(getJsonString(recordJsonObject, NO_OF_PAGES));

			collectionItemSearchResultDo.setAssetURI(getJsonString(recordJsonObject, ASSETURI));

			UserDo ownerDo = new UserDo();
			ownerDo.setFirstName(getJsonString(recordJsonObject, OWNER_FIRST_NAME));
			ownerDo.setLastName(getJsonString(recordJsonObject, OWNER_LAST_NAME));
			ownerDo.setUsername(getJsonString(recordJsonObject, OWNER_NAME_DISPLAY));
			collectionItemSearchResultDo.setOwner(ownerDo);

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
						if (standardDescriptions.get(i) != null) {
							standard.put(STANDARD_DESCRIPTION, (String) standardDescriptions.get(i));
						}
						standards.add(standard);
					}
					collectionItemSearchResultDo.setStandards(standards);

					collectionItemSearchResultDo.setSubjectNames(convertJSONArrayToList(((JSONArray) taxonomyDataSet.get(TAXONOMY_SUBJECT))));
					collectionItemSearchResultDo.setCourseNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_COURSE)));
					collectionItemSearchResultDo.setUnitNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_UNIT)));
					collectionItemSearchResultDo.setTopicNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_TOPIC)));
					collectionItemSearchResultDo.setLessonNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_LESSON)));
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
			collectionItemSearchResultDo.setAverageTime(getJsonString(recordJsonObject, AVERAGE_TIME));
			collectionItemSearchResultDo.setSharedCount(stringtoInteger(recordJsonObject, SHARED_COUNT, 0));
			collectionItemSearchResultDo.setGrade(getJsonString(recordJsonObject, GRADE));
			collectionItemSearchResultDo.setTags(getJsonString(recordJsonObject, TAGS));
			try {
				JSONObject license = recordJsonObject.isNull(LICENSE)?new JSONObject():recordJsonObject.getJSONObject(LICENSE);
				LicenseDo licenseDo=JsonDeserializer.deserialize(license.toString(), LicenseDo.class);
				collectionItemSearchResultDo.setLicense(licenseDo);
				if((!recordJsonObject.isNull(TAG_SET))&& recordJsonObject.get(TAG_SET) != null && !recordJsonObject.get(TAG_SET).equals("") && !recordJsonObject.get(TAG_SET).equals(null) ){
				JSONArray tagSetArray = (JSONArray) recordJsonObject.get(TAG_SET);
				Set<TagDo> tagSet= new HashSet<TagDo>();
				if(tagSetArray !=null){
					for(int i=0;i<tagSetArray.length();i++){
						tagSet.add(JsonDeserializer.deserialize(tagSetArray.get(i).toString(), TagDo.class));
					}
					collectionItemSearchResultDo.setTagSet(tagSet);
				}
				}

			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		catch(JSONException e){
			logger.error("Exception::", e);
		}

		return collectionItemSearchResultDo;

	}
}
