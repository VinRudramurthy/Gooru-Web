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
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.content.AssetDo;
import org.ednovo.gooru.application.shared.model.content.AssetsDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.LicenseDo;
import org.ednovo.gooru.application.shared.model.content.QuestionAnswerDo;
import org.ednovo.gooru.application.shared.model.content.QuestionHintsDo;
import org.ednovo.gooru.application.shared.model.content.RatingDo;
import org.ednovo.gooru.application.shared.model.content.ResourceDo;
import org.ednovo.gooru.application.shared.model.content.ResourceFormatDo;
import org.ednovo.gooru.application.shared.model.content.ResourceSourceDo;
import org.ednovo.gooru.application.shared.model.content.ResourceTypeDo;
import org.ednovo.gooru.application.shared.model.content.SearchRatingsDo;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;
import org.ednovo.gooru.application.shared.model.content.customFieldValuesDO;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
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
public class ResourceCollectionDeSerializer extends DeSerializer{

	private static final String VIDEO_YOUTUBE = "video/youtube";

	private static final String THUMBNAILS = "thumbnails";

	private static final String URL = "url";

	private static final String ASSESSMENT_QUESTION = "assessment-question";

	private static final String TAG_SET = "tagSet";

	private static String SEARCH_RESULTS = "searchResults";
	private static String SEARCH_HITS = "totalHitCount";
	public static String RESOURCE_TITLE = "title";
	public static String FOLDER = "folder";
	public static String RESOURCE_TYPE = "resourceType";
	public static String RESOURCE_TYPE_NAME = "name";
	public static final String CATEGORY="category";
	public static String COURSENAMES = "courseNames";
	public static String RESOURCE_DESCRIPTION = "description";
	public static String TOTALVIEWS = "viewCount";
	public static String VIEWS = "views";
	public static final String OWNER_FIRST_NAME = "userFirstName";
	public static final String OWNER_LAST_NAME = "userLastName";
	public static final String OWNER_NAME_DISPLAY = "usernameDisplay";
	public static final String USERNAME = "username";
	public static final String CREATOR = "creator";
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
	public static final String TAXONOMY_SET = "taxonomySet";
	public static final String RESOURCE_TAXONOMY_DATA_SET = "resourceTaxonomyData";
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
	public static final String ASSETURI = "assetURI";
	public static final String RESOURCE_SOURCE="resourceSource";
	public static String QUESTION_TEXT = "questionText";
	public static String EXPLANATION="explanation";
	public static String TIME_TO_COMPLETE_IN_SEC="timeToCompleteInSec";
	public static String QUESTION_TYPE_NAME="typeName";
	public static final String COLLECTION_ITEMS="collectionItems";
	public static final String EDUCATIONALUSE = "educationalUse";
	public static final String MOMENTSOFLEARNING = "momentsOfLearning";
	public static final String DEPTHOFKNOWLEDGE = "depthOfKnowledges";

	public static final String NUMBEROF_RESOURECES="numberOfResources";

	public static final String HAS_FRAME_BREAKER="hasFrameBreaker";
	public static final Short DEPTH=2;

	public static final String SCOCIAL="social";

	public static final String CONTENT_RATING="contentRating";

	public static final String CONTENT_USER_RATING="contentUserRating";

	public static final String ANSWERS="answers";

	public static final String HINTS="hints";

	public static final String TYPE="type";

	public static final String TYPENAME="typeName";

	public static final String ASSESTS="assets";

	public static final String SEQUENCE="sequence";

	public static final String ANSWERID="answerId";

	public static final String HINTID="hintId";

	public static final String ANSWERTEXT="answerText";

	public static final String HINTTEXT="hintText";

	public static final String ISCORRECT="isCorrect";

	public static final String ASSET="asset";

	public static final String NAME="name";

	public static final String QUIZQUESTION="quizQuestion";

	public static final String GOORUUID = "gooruUId";

	public static final String RESOURCE_FORMAT = "resourceFormat";

	public static final String RESOURCE_COUNT="resourceCount";

	public static final String QUESTION_COUNT="questionCount";

	public static final String CUSTOM_FIELDS="customFieldValues";

	public static final String RATINGS = "ratings";

	public static final String AGGREGATOR="aggregator";

	public static final String PUBLISHER="publisher";

	public static final String HOST="host";

	private static final Logger logger = LoggerFactory.getLogger(ResourceCollectionDeSerializer.class);

	public static ResourceSearchResultDo deserializeRecord(JSONObject recordJsonObject) {
		ResourceSearchResultDo resourceSearchResultDo = new ResourceSearchResultDo();
		try {
				resourceSearchResultDo.setUrl(getJsonString(recordJsonObject.isNull(THUMBNAILS)?null:recordJsonObject.getJSONObject(THUMBNAILS), URL));
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		resourceSearchResultDo.setResourceTitle(getJsonString(recordJsonObject, RESOURCE_TITLE));
		resourceSearchResultDo.setFolder((getJsonString(recordJsonObject, FOLDER)));
		resourceSearchResultDo.setDescription(getJsonString(recordJsonObject, RESOURCE_DESCRIPTION));

		resourceSearchResultDo.setVotesUp(stringtoInteger(recordJsonObject, VOTES_UP, 0));
		resourceSearchResultDo.setVotesDown(stringtoInteger(recordJsonObject, VOTES_DOWN, 0));
		resourceSearchResultDo.setGooruOid(getJsonString(recordJsonObject, "id"));
		resourceSearchResultDo.setCategory(getJsonString(recordJsonObject, CATEGORY));
		resourceSearchResultDo.setTotalViews(stringtoInteger(recordJsonObject, TOTALVIEWS, 0));
		resourceSearchResultDo.setNumOfPages(getJsonString(recordJsonObject, NO_OF_PAGES));
		resourceSearchResultDo.setNoOfResources(stringtoInteger(recordJsonObject, NUMBEROF_RESOURECES, 0));
		resourceSearchResultDo.setQuestionCount(stringtoInteger(recordJsonObject, QUESTION_COUNT, 0));
		resourceSearchResultDo.setResourceCount(stringtoInteger(recordJsonObject, RESOURCE_COUNT, 0));

		resourceSearchResultDo.setAssetURI(getJsonString(recordJsonObject, ASSETURI));

		UserDo ownerDo = new UserDo();
		ownerDo.setFirstName(getJsonString(recordJsonObject, OWNER_FIRST_NAME));
		ownerDo.setLastName(getJsonString(recordJsonObject, OWNER_LAST_NAME));
		ownerDo.setUsername(getJsonString(recordJsonObject, OWNER_NAME_DISPLAY));

		resourceSearchResultDo.setOwner(ownerDo);
		JSONArray standardCodes = null;
		JSONArray standardDescriptions = null;
		try{
			if(getJsonString(recordJsonObject, TAXONOMY_DATA_SET) != null) {
				JSONObject taxonomyDataSet = new JSONObject(getJsonString(recordJsonObject, TAXONOMY_DATA_SET));
				JSONObject curriculum =taxonomyDataSet.isNull(CURRICULUM)?null:taxonomyDataSet.getJSONObject(CURRICULUM);
				if(!curriculum.isNull(CURRICULUM_CODE)){
					standardCodes = (JSONArray) (curriculum.get(CURRICULUM_CODE));
				}
				if(!curriculum.isNull(CURRICULUM_DESCRIPTION)){
					standardDescriptions = (JSONArray) (curriculum.get(CURRICULUM_DESCRIPTION));
				}
				List<Map<String, String>> standards = new ArrayList<Map<String, String>>();
				if(standardCodes!=null&&standardCodes.length()>0){
					for (int i = 0; i < standardCodes.length(); i++) {
						Map<String, String> standard = new HashMap<String, String>();
						if(standardCodes.get(i)!=null){
							standard.put(STANDARD_CODE, (String)standardCodes.get(i));
						}
						if(standardDescriptions.get(i) != null) {
							standard.put(STANDARD_DESCRIPTION, (String)standardDescriptions.get(i));
						}
						standards.add(standard);
					}
				}
				resourceSearchResultDo.setStandards(standards);
				if(!taxonomyDataSet.isNull(TAXONOMY_SUBJECT)){
					resourceSearchResultDo.setSubjectNames(convertJSONArrayToList(((JSONArray) taxonomyDataSet.get(TAXONOMY_SUBJECT))));
				}
				if(!taxonomyDataSet.isNull(TAXONOMY_COURSE)){
					resourceSearchResultDo.setCourseNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_COURSE)));
				}
				if(!taxonomyDataSet.isNull(TAXONOMY_UNIT)){
					resourceSearchResultDo.setUnitNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_UNIT)));
				}
				if(!taxonomyDataSet.isNull(TAXONOMY_TOPIC)){
					resourceSearchResultDo.setTopicNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_TOPIC)));
				}
				if(!taxonomyDataSet.isNull(TAXONOMY_LESSON)){
					resourceSearchResultDo.setLessonNames(convertJSONArrayToList((JSONArray) taxonomyDataSet.get(TAXONOMY_LESSON)));
				}
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		resourceSearchResultDo.setAverageTime(getJsonString(recordJsonObject, AVERAGE_TIME));
		resourceSearchResultDo.setSharedCount(stringtoInteger(recordJsonObject, SHARED_COUNT, 0));
		resourceSearchResultDo.setGrade(getJsonString(recordJsonObject, GRADE));
		resourceSearchResultDo.setTags(getJsonString(recordJsonObject, TAGS));
		resourceSearchResultDo.setGooruUId(getJsonString(recordJsonObject, GOORUUID));

		return resourceSearchResultDo;
	}

	public static CollectionItemDo deserializeCollectionItemDoV2API(CollectionItemDo recordJsonObject){
		ResourceDo resourceDo=new ResourceDo();
		Set<CodeDo> taxonomySet = new HashSet<CodeDo>();
		int size = 0;
		StringTokenizer courses = null;
		try{
			resourceDo = recordJsonObject.getResource();
			if(recordJsonObject.getCourse()!=null && recordJsonObject.getCourse().size()>0){
				size=recordJsonObject.getCourse().size();
			}else{
				if(resourceDo.getCustomFieldValues()!=null && !resourceDo.getCustomFieldValues().equals("")){
					if(resourceDo.getCustomFieldValues().getCfGooruCourse()!=null && !resourceDo.getCustomFieldValues().getCfGooruCourse().equalsIgnoreCase("")&&!resourceDo.getCustomFieldValues().getCfGooruCourse().equalsIgnoreCase("null")){
						courses = new StringTokenizer(resourceDo.getCustomFieldValues().getCfGooruCourse(), ",");
						size=courses.countTokens();
					}
				}
			}
			for(int j=0;j<size;j++){
				CodeDo codeDo=new CodeDo();
				codeDo.setDepth(DEPTH);
				if(recordJsonObject.getCourse().size()==0){
					codeDo.setLabel(courses.nextToken());
				}else{
					codeDo.setLabel(recordJsonObject.getCourse().get(j));
				}
				taxonomySet.add(codeDo);
			}
			resourceDo.setTaxonomySet(taxonomySet);
			resourceDo.setSkills(recordJsonObject.getSkills());
			recordJsonObject.setStandards(recordJsonObject.getStandards());
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		recordJsonObject.setResource(resourceDo);
		return recordJsonObject;
	}

	public static CollectionItemDo deserializeCollectionItemDo(JSONObject recordJsonObject){
		CollectionItemDo collectionItemDo=new CollectionItemDo();
		ResourceDo resourceDo=new ResourceDo();
		try {
			JSONObject resourceType = recordJsonObject.getJSONObject(RESOURCE_TYPE);
			resourceDo.setResourceType(JsonDeserializer.deserialize(resourceType.toString(), ResourceTypeDo.class));

			JSONObject resourceSourceJson = recordJsonObject.isNull(RESOURCE_SOURCE)?null:recordJsonObject.getJSONObject(RESOURCE_SOURCE);
			ResourceSourceDo resourceSourceDo=resourceSourceJson==null?null:JsonDeserializer.deserialize(resourceSourceJson.toString(), ResourceSourceDo.class);
			resourceDo.setResourceSource(resourceSourceDo);

		} catch (JSONException e1) {
			logger.error("Exception::", e1);
		}

		try {
			JSONObject thumbnailJson = recordJsonObject.getJSONObject(THUMBNAILS);
			resourceDo.setThumbnails(JsonDeserializer.deserialize(thumbnailJson.toString(), ThumbnailDo.class));

		} catch (JSONException e1) {
			logger.error("Exception::", e1);
		}

		resourceDo.setTitle(getJsonString(recordJsonObject, RESOURCE_TITLE));
		resourceDo.setFolder((getJsonString(recordJsonObject, FOLDER)));
		resourceDo.setDescription(getJsonString(recordJsonObject, RESOURCE_DESCRIPTION));

		try {
			if (getJsonString(recordJsonObject, RESOURCE_TAXONOMY_DATA_SET) != null) {
				JSONObject taxonomyDataSet = new JSONObject(getJsonString(recordJsonObject, RESOURCE_TAXONOMY_DATA_SET));
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
				collectionItemDo.setStandards(standards);
				Set<CodeDo> taxonomySet = new HashSet<CodeDo>();
				//JSONArray courseArray=taxonomyDataSet.getJSONArray(TAXONOMY_COURSE);
				JSONArray courseArray = new JSONArray(getJsonString(recordJsonObject, TAXONOMY_SET));
				for(int j=0;j<courseArray.length();j++){
					if(courseArray.getJSONObject(j).getInt("depth")==DEPTH){
						CodeDo codeDo=new CodeDo();
						codeDo.setDepth(DEPTH);
						codeDo.setLabel(courseArray.getJSONObject(j).getString("label"));
						taxonomySet.add(codeDo);
						}
				}
				resourceDo.setTaxonomySet(taxonomySet);
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		resourceDo.setUrl(getJsonString(recordJsonObject, URL));
		resourceDo.setAssetURI(getJsonString(recordJsonObject, ASSETURI));
		resourceDo.setViews(getJsonString(recordJsonObject, VIEWS));
		resourceDo.setCategory(getJsonString(recordJsonObject, CATEGORY));
		resourceDo.setGooruOid(getJsonString(recordJsonObject, GOORU_OID));
		resourceDo.setGrade(getJsonString(recordJsonObject, GRADE));
		resourceDo.setEducationalUse(getJsonArray(recordJsonObject, EDUCATIONALUSE));
		resourceDo.setMomentsOfLearning(getJsonArray(recordJsonObject, MOMENTSOFLEARNING));

		try {
			resourceDo.setPublisher(JsonDeserializer.deserialize(recordJsonObject.getJSONArray(PUBLISHER).toString(), new TypeReference<List<String>>() {
			}));
		} catch (JSONException e2) {
			logger.error("Exception::", e2);
		}

		try {
			if(!recordJsonObject.isNull(HOST)){
				resourceDo.setHost(JsonDeserializer.deserialize(recordJsonObject.getJSONArray(HOST).toString(), new TypeReference<List<String>>() {
				}));
			}

		} catch (JSONException e2) {
			logger.error("Exception::", e2);
		}

		try {
			resourceDo.setAggregator(JsonDeserializer.deserialize(recordJsonObject.getJSONArray(AGGREGATOR).toString(), new TypeReference<List<String>>() {
			}));
		} catch (JSONException e2) {
			logger.error("Exception::", e2);
		}

		resourceDo.setDepthOfKnowledge(getJsonArray(recordJsonObject, DEPTHOFKNOWLEDGE));
		UserDo ownerDo = new UserDo();
			try {
				JSONObject createrObject = new JSONObject(getJsonString(recordJsonObject, CREATOR));
				ownerDo.setUsername(getJsonString(createrObject,USERNAME));
				resourceDo.setCreator(ownerDo);
				resourceDo.setUser(ownerDo);
			} catch (JSONException e1) {
				logger.error("Exception::", e1);
			}
		try{
			resourceDo.setCustomFieldValues(JsonDeserializer.deserialize(recordJsonObject.getJSONObject(CUSTOM_FIELDS).toString(), customFieldValuesDO.class));
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		try{
			resourceDo.setResourceFormat(JsonDeserializer.deserialize(recordJsonObject.getJSONObject(RESOURCE_FORMAT).toString(), ResourceFormatDo.class));
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		try{
			resourceDo.setRatings(JsonDeserializer.deserialize(recordJsonObject.getJSONObject(RATINGS).toString(), SearchRatingsDo.class));
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		/*JSONObject resourceRating = recordJsonObject.getJSONObject(RATINGS);
		SearchRatingsDo searchRatingsDo =JsonDeserializer.deserialize(resourceRating.toString(), SearchRatingsDo.class);
		resourceDo.setSearchRatingsDo(searchRatingsDo);*/
		try{
			resourceDo.setHasFrameBreaker(recordJsonObject.isNull(HAS_FRAME_BREAKER)?null:recordJsonObject.getBoolean(HAS_FRAME_BREAKER));
		}catch(Exception e){
			logger.error("Exception::", e);
		}

		try{
			resourceDo.setLicense(JsonDeserializer.deserialize(recordJsonObject.getJSONObject(LICENSE).toString(), LicenseDo.class));
		}catch(Exception e){
			logger.error("Exception::", e);
		}

		try{
			RatingDo ratindDo=new RatingDo();
			JSONObject ratingObject=recordJsonObject.isNull(SCOCIAL)?null:recordJsonObject.getJSONObject(SCOCIAL);
				ratindDo.setVotesUp(ratingObject.isNull(CONTENT_RATING)?null:ratingObject.getJSONObject(CONTENT_RATING).getInt(VOTES_UP));
				resourceDo.setUserRating(ratingObject.isNull(CONTENT_USER_RATING)?0:ratingObject.getInt(CONTENT_USER_RATING));
			collectionItemDo.setRating(ratindDo);
		}catch(Exception e){
			logger.error("Exception::", e);
		}

		try{
			JSONObject quizObject=recordJsonObject.isNull(QUIZQUESTION)?null:recordJsonObject.getJSONObject(QUIZQUESTION);
			if(quizObject!=null){
				TreeSet<QuestionAnswerDo> questionAnwersDo=new TreeSet<QuestionAnswerDo>();
				JSONArray answersArray=quizObject.isNull(ANSWERS)?null:quizObject.getJSONArray(ANSWERS);
				if(answersArray!=null){
					for(int i=0;i<answersArray.length();i++){
						QuestionAnswerDo questionAnwerDo=new QuestionAnswerDo();
						JSONObject answerObject=answersArray.getJSONObject(i);
						questionAnwerDo.setAnswerText(getJsonString(answerObject, ANSWERTEXT));
						questionAnwerDo.setIsCorrect(answerObject.isNull(ISCORRECT)?null:answerObject.getBoolean(ISCORRECT));
						questionAnwerDo.setAnswerId(answerObject.isNull(ANSWERID)?null:answerObject.getInt(ANSWERID));
						questionAnwerDo.setSequence(answerObject.isNull(SEQUENCE)?null:answerObject.getInt(SEQUENCE));
						questionAnwersDo.add(questionAnwerDo);
					}
				}
				TreeSet<QuestionHintsDo> questionHintsArray=new TreeSet<QuestionHintsDo>();
				JSONArray hintsArray=quizObject.isNull(HINTS)?null:quizObject.getJSONArray(HINTS);
				if(hintsArray!=null){
					for(int i=0;i<hintsArray.length();i++){
						QuestionHintsDo questionHintDo=new QuestionHintsDo();
						JSONObject hintsObject=hintsArray.getJSONObject(i);
						questionHintDo.setHintText(getJsonString(hintsObject, HINTTEXT));
						questionHintDo.setHintId(hintsObject.isNull(HINTID)?null:hintsObject.getInt(HINTID));
						questionHintDo.setSequence(hintsObject.isNull(SEQUENCE)?null:hintsObject.getInt(SEQUENCE));
						questionHintsArray.add(questionHintDo);
					}
				}
				resourceDo.setAnswers(questionAnwersDo);
				resourceDo.setHints(questionHintsArray);
				resourceDo.setQuestionText(getJsonString(recordJsonObject, QUESTION_TEXT));
				resourceDo.setExplanation(getJsonString(recordJsonObject, EXPLANATION));
				resourceDo.setType(recordJsonObject.isNull(TYPE)?null:recordJsonObject.getInt(TYPE));
				resourceDo.setType(recordJsonObject.isNull(TYPENAME)?null:recordJsonObject.getInt(TYPENAME));

				ArrayList<AssetsDo> assetsList=new ArrayList<AssetsDo>();
				JSONArray assetArray=quizObject.isNull(ASSESTS)?null:quizObject.getJSONArray(ASSESTS);
				if(assetArray!=null){
					for(int i=0;i<assetArray.length();i++){
						AssetsDo assestDo=new AssetsDo();
						assestDo.setAsset(JsonDeserializer.deserialize(assetArray.getJSONObject(i).toString(), AssetDo.class));
					}
				}
				resourceDo.setAssets(assetsList);

			}



		}catch(Exception e){
			logger.error("Exception::", e);
		}
		collectionItemDo.setResource(resourceDo);
		return collectionItemDo;

	}

}
