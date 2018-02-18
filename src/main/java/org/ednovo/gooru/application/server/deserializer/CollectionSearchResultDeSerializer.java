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
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.LicenseDo;
import org.ednovo.gooru.application.shared.model.search.CollectionSearchResultDo;
import org.ednovo.gooru.application.shared.model.user.UserDo;
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
public class CollectionSearchResultDeSerializer extends SearchDeSerializer<CollectionSearchResultDo> {

	private static String CREATORNAME = "creatornameDisplay";
	private static String COLLECTION_DESCRIPTION = "goals";
	private static final String HAS_ADDED_TO_SHELF = "hasAddedToShelf";
	private static final String THUMBNAILS = "thumbnails";
	private static final String URL = "url";
	private static final String COLLECTION_ITEM_COUNT="collectionItemCount";
	private static final String QUESTION_COUNT="questionCount";
	private static final String RESOURCE_COUNT="resourceCount";
	private static final String REMIX_COUNT="scollectionRemixCount";
	private static String COLLECTION_TYPE  = "collectionType";

	private static final String GOORU_OID = "id";

	private static final Logger logger = LoggerFactory.getLogger(CollectionSearchResultDeSerializer.class);

	@Override
	public CollectionSearchResultDo deserializeRecord(JSONObject recordJsonObject) {
		CollectionSearchResultDo searchResult = new CollectionSearchResultDo();
		searchResult.setResourceTitle(getJsonString(recordJsonObject, RESOURCE_TITLE));
		searchResult.setCreatorName(getJsonString(recordJsonObject, CREATORNAME));
		searchResult.setCollectionType(getJsonString(recordJsonObject, COLLECTION_TYPE));
		searchResult.setTotalViews(stringtoInteger(recordJsonObject, TOTALVIEWS, 0));

		searchResult.setGooruOid(getJsonString(recordJsonObject, GOORU_OID));
		searchResult.setResourceTitle(getJsonString(recordJsonObject, RESOURCE_TITLE));
		searchResult.setDescription((getJsonString(recordJsonObject, COLLECTION_DESCRIPTION)));
		searchResult.setCollaboratorCount(stringtoInteger(recordJsonObject, COLLABORATOR_COUNT, 0));
		UserDo ownerDo = new UserDo();
		ownerDo.setFirstName(getJsonString(recordJsonObject, OWNER_FIRST_NAME));
		ownerDo.setLastName(getJsonString(recordJsonObject, OWNER_LAST_NAME));
		ownerDo.setUsername(getJsonString(recordJsonObject, OWNER_NAME_DISPLAY));
		String userVisibility = getJsonString(recordJsonObject, OWNER_PROFILE_USER_VISIBILITY);
		searchResult.setGooruUId(getJsonString(recordJsonObject, "gooruUId"));
		if(userVisibility.equalsIgnoreCase("true")) {
			ownerDo.setProfileUserVisibility(true);
		} else {
			ownerDo.setProfileUserVisibility(false);
		}
		ownerDo.setGooruUId(getJsonString(recordJsonObject, OWNER_GOORU_UID));

		searchResult.setOwner(ownerDo);
		try {
			if(!recordJsonObject.isNull(THUMBNAILS))
			{
			searchResult.setUrl(getJsonString(recordJsonObject.getJSONObject(THUMBNAILS), URL));
			}
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
				searchResult.setStandards(standards);
				searchResult.setSubjectNames(convertJSONArrayToList(((JSONArray) taxonomyDataSet.get(TAXONOMY_SUBJECT))));
				searchResult.setCourseNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_COURSE)));
				searchResult.setUnitNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_UNIT)));
				searchResult.setTopicNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_TOPIC)));
				searchResult.setLessonNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_LESSON)));

			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}

		searchResult.setHasAddedToShelf(stringtoInteger(recordJsonObject, HAS_ADDED_TO_SHELF, 0));

		searchResult.setAverageTime(getJsonString(recordJsonObject, AVERAGE_TIME));
		searchResult.setSharedCount(stringtoInteger(recordJsonObject, SHARED_COUNT, 0));
		searchResult.setGrade(getJsonString(recordJsonObject, GRADE));
		searchResult.setTags(getJsonString(recordJsonObject, TAGS));
		try {
			searchResult.setResourceCount(stringtoInteger(recordJsonObject, COLLECTION_ITEM_COUNT, 0));
			searchResult.setQuestionCount(stringtoInteger(recordJsonObject, QUESTION_COUNT, 0));
			searchResult.setOnlyResourceCount(stringtoInteger(recordJsonObject, RESOURCE_COUNT, 0));
			searchResult.setScollectionRemixCount(stringtoInteger(recordJsonObject, REMIX_COUNT, 0));

			if (recordJsonObject.has(LICENSE)) {
				JSONObject license = recordJsonObject.getJSONObject(LICENSE);
				if (license != null) {
					LicenseDo licenseDo = JsonDeserializer.deserialize(license.toString(), LicenseDo.class);
					searchResult.setLicense(licenseDo);
				}
			}

			List<CollectionItemDo> collectionItemDoList= (ArrayList<CollectionItemDo>) JsonDeserializer.deserialize(recordJsonObject.getJSONArray("collectionItems").toString(),new TypeReference<List<CollectionItemDo>>() {});
			searchResult.setCollectionItems(collectionItemDoList);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return searchResult;
	}

}
