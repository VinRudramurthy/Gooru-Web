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



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.service.LibraryService;
import org.ednovo.gooru.application.server.ArrayListSorter;
import org.ednovo.gooru.application.server.annotation.ServiceURL;
import org.ednovo.gooru.application.server.deserializer.ProfileLibraryDeserializer;
import org.ednovo.gooru.application.server.request.JsonResponseRepresentation;
import org.ednovo.gooru.application.server.request.ServiceProcessor;
import org.ednovo.gooru.application.server.request.UrlToken;
import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;
import org.ednovo.gooru.application.shared.model.library.CourseDo;
import org.ednovo.gooru.application.shared.model.library.LessonDo;
import org.ednovo.gooru.application.shared.model.library.LibraryCollectionItemDo;
import org.ednovo.gooru.application.shared.model.library.LibraryResourceDo;
import org.ednovo.gooru.application.shared.model.library.LibraryUserDo;
import org.ednovo.gooru.application.shared.model.library.PartnerConceptListDo;
import org.ednovo.gooru.application.shared.model.library.PartnerFolderDo;
import org.ednovo.gooru.application.shared.model.library.PartnerFolderListDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;
import org.ednovo.gooru.application.shared.model.library.StandardCourseDo;
import org.ednovo.gooru.application.shared.model.library.StandardsDo;
import org.ednovo.gooru.application.shared.model.library.SubjectDo;
import org.ednovo.gooru.application.shared.model.library.TopicDo;
import org.ednovo.gooru.application.shared.model.library.UnitDo;
import org.ednovo.gooru.shared.util.GooruConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

@Service("libraryService")
@ServiceURL("/libraryService")
public class LibraryServiceImpl extends BaseServiceImpl implements LibraryService {

	private static final long serialVersionUID = 1L;
	private static final String DATA = "data";
	private static final String RUSD = "rusd";
	private static final String TOTAL_LIMIT = "10";
	public static final String CURRICULUM = "curriculum";
	public static final String CURRICULUM_CODE = "curriculumCode";
	public static final String CURRICULUM_DESCRIPTION = "curriculumDesc";
	public static final String TAXONOMY_DATA_SET = "taxonomyDataSet";
	public static final String STANDARD_CODE = "code";
	public static final String STANDARD_DESCRIPTION = "description";
	private static final String SEARCH_RESULTS = "searchResult";
	private static final String COUNT = "count";
	private static final String COLLECTION_ITEMS = "collectionItems";

	private static final String TITLE = "title";
	private static final String GOALS = "goals";
	private static final String GOORUOID = "gooruOid";
	private static final String THUMBNAILS = "thumbnails";
	private static final String FOLDER = "folder";
	private static final String TYPE = "type";
	private static final String COLLECTION = "collection";
	private static final String LESSON = "lesson";
	private static final String CONCEPT = "concept";
	private static final String COURSE = "course";
	private static final String UNIT = "unit";
	private static final String TOPIC = "topic";
	private static final String COURSE_100_75_IMG = "../images/library/partners/TeachableMoments.png";
	private static final String FEATURED = "featured";

	private static final String EXCLUDED_COURSE = "Language and Composition";
	private static final String INCLUDED_COURSE = "Teachable Moments";
	private static final String AUTODESK_GOORU_UID = "237011b7-d174-4221-9dcb-563526dd0b09";
	private final static String DEFAULT_USER_IMG = "../images/settings/setting-user-image.png";
	private static final String MALE = "none";
	private final static String RUSDLEARNS = "rusdlearns";
	private final static String RUSD_LAST_NAME = "RUSD Teachers";
	private final static String NODE = "node";

	private static final Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);

	@Override
	public ArrayList<CourseDo> getCourses(String subjectName, String libraryName) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_COURSES, subjectName);
		url = AddQueryParameter.constructQueryParams(url, GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
//		url+=getLibraryName(libraryName);
		getLogger().info("Get Courses -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeCourses(jsonRepresentation, subjectName);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.service.LibraryService#getLessonsOnPagination(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public ArrayList<LessonDo> getLessonsOnPagination(String subjectName, String topicId, int offset, int limit, String libraryName) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_TOPIC_OFFSET, subjectName, topicId);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.OFFSET, String.valueOf(offset));
		params.put(GooruConstants.LIMIT, String.valueOf(limit));
		params.put(GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
//		url+=getLibraryName(libraryName);
		getLogger().info("community - getLessonsOnPagination- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeLessons(jsonRepresentation);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.service.LibraryService#getLibraryFeaturedUsers()
	 */
	@Override
	public ArrayList<LibraryUserDo> getLibraryFeaturedUsers(String libraryName) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_FEATURED_USERS);
		url = AddQueryParameter.constructQueryParams(url, GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
		getLogger().info("-- getLibraryFeaturedUsers -- "+url);
//		url+=getLibraryName(libraryName);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeCollaborators(jsonRepresentation);
	}


	@Override
	public String getLibraryContributorsUsers(String libraryName) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_FEATURED_USERS);
		url = AddQueryParameter.constructQueryParams(url, GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();

		String jsonResponse;
		try {
			jsonResponse = jsonRepresentation.getJsonArray().toString();
		} catch (JSONException e) {
			throw new RuntimeException("message", e);
		}

		return jsonResponse;
	}


	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.service.LibraryService#getSubjects()
	 */
	@Override
	public HashMap<String, SubjectDo> getSubjects(String subjectId, String libraryName) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_COURSES, subjectId);
		url = AddQueryParameter.constructQueryParams(url, GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
		getLogger().info("--- getSubjects -- "+url);
//		url+=getLibraryName(libraryName);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeSubjects(jsonRepresentation,subjectId,libraryName);
	}

	@Override
	public HashMap<String, StandardsDo> getSubjectsForStandards(String subjectId, String libraryName) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_COURSES, subjectId);
		url = AddQueryParameter.constructQueryParams(url, GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
		getLogger().info("-- getSubjectsForStandards -- "+url);
//		url+=getLibraryName(libraryName);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		//need to do here
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeSubjectsforStandards(jsonRepresentation,subjectId);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.service.LibraryService#getConcept(java.lang.String, boolean)
	 */
	@Override
	public ConceptDo getConcept(String gooruOid, boolean skipCollectionItems) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_COLLECTION, gooruOid);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.SKIP_COLL_ITEM, String.valueOf(skipCollectionItems));
		params.put(GooruConstants.INCLUDE_META_INFO,GooruConstants.TRUE );
		params.put(GooruConstants.MERGE, GooruConstants.PERMISSIONS);
		params.put(GooruConstants.INCLUDE_CONTENT_PROVDER, GooruConstants.FALSE);
		params.put(GooruConstants.INCLUDE_CUSTOM_FIELDS,GooruConstants.FALSE);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("--getConcept --  "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeConcept(jsonRepresentation);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.service.LibraryService#getConceptForStandards(java.lang.String, boolean)
	 */
	@Override
	public ConceptDo getConceptForStandards(String gooruOid, String roteNodeId, boolean skipCollectionItems) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_COLLECTIONForStandards, gooruOid);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.ROOT_NODE_ID, roteNodeId);
		params.put(GooruConstants.SKIP_COLL_ITEM, String.valueOf(skipCollectionItems));
		params.put(GooruConstants.INCLUDE_META_INFO, GooruConstants.TRUE);
		params.put(GooruConstants.MERGE, GooruConstants.PERMISSIONS);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("--- getConceptForStandards -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeConcept(jsonRepresentation);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.service.LibraryService#getTopicsOnPagination(java.lang.String)
	 */
	@Override
	public ArrayList<TopicDo> getTopicsOnPagination(String subjectId, String unitId, String libraryName, int offset, String standardsId) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_UNIT_OFFSET, subjectId, unitId);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.OFFSET, String.valueOf(offset));
		params.put(GooruConstants.LIMIT, String.valueOf(TOTAL_LIMIT));
		params.put(GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
		params.put(GooruConstants.ROOT_NODE_ID, getStandardId(standardsId));
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("-- getTopicsOnPagination -- "+url);
//		url+=getLibraryName(libraryName);
//		url+=getStandardId(standardsId);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());

		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeTopicList(jsonRepresentation);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.service.LibraryService#getLibraryCollections(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<ConceptDo> getLibraryCollections(String courseType, String lessonId, String libraryName) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_COLLECTIONS, courseType, lessonId);
		url = AddQueryParameter.constructQueryParams(url, GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
		getLogger().info("--- getLibraryCollections -- "+url);
//		url+=getLibraryName(libraryName);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());

		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeConceptDoList(jsonRepresentation);
	}

	/**
	 *
	 * @function deserializeCollaborators
	 *
	 * @created_date : 13-Dec-2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param jsonRep
	 * @parm(s) : @return
	 *
	 * @return : ArrayList<LibraryUserDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public ArrayList<LibraryUserDo> deserializeCollaborators(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<ArrayList<LibraryUserDo>>() {
				});
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new ArrayList<LibraryUserDo>();
	}


	/**
	 *
	 * @function deserializeCollaborators
	 *
	 * @created_date : 08-Apr-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param jsonString
	 * @parm(s) : @return
	 *
	 * @return : ArrayList<LibraryUserDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public ArrayList<LibraryUserDo> deserializeCollaborators(String jsonString) {
		getLogger().info("jsonString : "+jsonString);
		if (jsonString != null) {
			return JsonDeserializer.deserialize(jsonString, new TypeReference<ArrayList<LibraryUserDo>>() {
			});
		}
		return new ArrayList<LibraryUserDo>();
	}

	/**
	 *
	 * @function deserializeCourses
	 *
	 * @created_date : 13-Dec-2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param jsonRep
	 * @parm(s) : @param subjectName
	 * @parm(s) : @return
	 *
	 * @return : ArrayList<CourseDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public ArrayList<CourseDo> deserializeCourses(JsonRepresentation jsonRep, String subjectName) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				String jsonRep1 = jsonRep.getJsonObject().getJSONObject(subjectName).getJSONArray(DATA).toString();
				return JsonDeserializer.deserialize(jsonRep1, new TypeReference<ArrayList<CourseDo>>() {
				});
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new ArrayList<CourseDo>();
	}

	/**
	 *
	 * @function deserializeLessons
	 *
	 * @created_date : 13-Dec-2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param jsonRep
	 * @parm(s) : @return
	 *
	 * @return : ArrayList<LessonDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public ArrayList<LessonDo> deserializeLessons(JsonRepresentation jsonRep) {
		ArrayList<LessonDo> lessonDoList = new ArrayList<LessonDo>();
		ArrayList<ConceptDo> conceptDoList = new ArrayList<ConceptDo>();
		LessonDo lessonDo = new LessonDo();
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				JSONArray lessonArray = jsonRep.getJsonArray();
				for(int i=0;i<lessonArray.length();i++) {
					if(lessonArray.getJSONObject(i).getJSONArray(COLLECTION)!=null) {
						lessonDoList.add(JsonDeserializer.deserialize(lessonArray.getJSONObject(i).toString(), LessonDo.class));
					} else {
						conceptDoList.add(JsonDeserializer.deserialize(lessonArray.getJSONObject(i).toString(), ConceptDo.class));
					}
				}
				if(conceptDoList.size()>0) {
					lessonDo.setCollection(conceptDoList);
					lessonDoList.add(lessonDo);
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return lessonDoList;
	}

	/**
	 * @function deserializeSubjects
	 *
	 * @created_date : 13-Dec-2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param jsonRep
	 * @parm(s) : @param subjectName
	 * @parm(s) : @return
	 *
	 * @return : ArrayList<CourseDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public HashMap<String,SubjectDo> deserializeSubjects(JsonRepresentation jsonRep, String subjectName, String libraryName) {
		HashMap<String, SubjectDo> subjectList = new HashMap<String, SubjectDo>();
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				String jsonRepStr = jsonRep.getJsonObject().toString();
				subjectList = JsonDeserializer.deserialize(jsonRepStr, new TypeReference<HashMap<String, SubjectDo>>() {});
				if(libraryName.equals(RUSD)&&subjectName.equals(FEATURED)) {
					subjectList = getThirdPartyPartnerData(subjectList,subjectName);
				}
				return subjectList;
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new HashMap<String,SubjectDo>();
	}

	private HashMap<String, SubjectDo> getThirdPartyPartnerData(HashMap<String, SubjectDo> subjectList, String subjectName) {
		ArrayList<CourseDo> data = subjectList.get(subjectName).getData();
		CourseDo courseDo = new CourseDo();
		courseDo.setLabel(INCLUDED_COURSE);
		courseDo.setParentId(AUTODESK_GOORU_UID);
		ThumbnailDo thumbnailDo = new ThumbnailDo();
		thumbnailDo.setUrl(COURSE_100_75_IMG);
		courseDo.setThumbnails(thumbnailDo);
		LibraryUserDo libraryUserDo = new LibraryUserDo();
		libraryUserDo.setGender(MALE);
		libraryUserDo.setLastName(RUSD_LAST_NAME);
		libraryUserDo.setUsername(RUSDLEARNS);
		libraryUserDo.setGooruUId(AUTODESK_GOORU_UID);
		courseDo.setCreator(libraryUserDo);
		courseDo.setUser(null);
		try {
			for(int i=0;i<data.size();i++) {
				if(data.get(i).getLabel().equalsIgnoreCase(EXCLUDED_COURSE)) {
					data.remove(i);
				}
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		data.add(0, courseDo);
		subjectList.get(subjectName).setData(data);
		return subjectList;
	}

	public HashMap<String,StandardsDo> deserializeSubjectsforStandards(JsonRepresentation jsonRep, String subjectName) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				String jsonRepStr = jsonRep.getJsonObject().toString();
			//	JSONObject mainObj = jsonRep.getJsonObject();
				/*if(jsonRep.getJsonObject().getJSONObject(subjectName).getJSONArray(DATA).length()>0)
				{
					for(int z=0;z<jsonRep.getJsonObject().getJSONObject(subjectName).getJSONArray(DATA).length();z++){
					JSONArray courseArray = jsonRep.getJsonObject().getJSONObject(subjectName).getJSONArray(DATA).getJSONObject(z).getJSONArray(COURSE);
						for(int n=0; n<courseArray.length();n++)
						{
							for(int m=0; m<courseArray.getJSONObject(n).getJSONArray(UNIT).length();m++)
							{
								JSONArray topicArray = courseArray.getJSONObject(n).getJSONArray(UNIT).getJSONObject(m).getJSONArray(TOPIC);

								for(int l=0; l<topicArray.length();l++) {
										JSONArray lessonDoList = new JSONArray();
										JSONObject lessonList = topicArray.getJSONObject(l);
										//This code is only for TEXS
										if(jsonRep.getJsonObject().getJSONObject(subjectName).getJSONArray(DATA).getJSONObject(z).getString("label").contains("Texas")){
											JSONObject lessonObj1 = new JSONObject();
											lessonObj1=topicArray.getJSONObject(l);
											lessonDoList.put(lessonObj1);
										}
										if(!lessonList.isNull(LESSON)) {
											for(int j=0;j<topicArray.getJSONObject(l).getJSONArray(LESSON).length();j++)
											{
												JSONObject lessonObj = new JSONObject();
												lessonObj = topicArray.getJSONObject(l).getJSONArray(LESSON).getJSONObject(j);
												lessonDoList.put(lessonObj);
												if(!lessonObj.isNull(CONCEPT)) {
													JSONArray conceptArray = topicArray.getJSONObject(l).getJSONArray(LESSON).getJSONObject(j).getJSONArray(CONCEPT);
													for(int k=0;k<lessonObj.getJSONArray(CONCEPT).length();k++)
													{
														JSONObject conceptObj = new JSONObject();
														conceptObj = conceptArray.getJSONObject(k);
														lessonDoList.put(conceptObj);
													}
												}
											}
										}
										mainObj.getJSONObject(subjectName).getJSONArray(DATA).getJSONObject(z).getJSONArray(COURSE).getJSONObject(n).getJSONArray(UNIT).getJSONObject(m).getJSONArray(TOPIC).getJSONObject(l).remove(LESSON);
										mainObj.getJSONObject(subjectName).getJSONArray(DATA).getJSONObject(z).getJSONArray(COURSE).getJSONObject(n).getJSONArray(UNIT).getJSONObject(m).getJSONArray(TOPIC).getJSONObject(l).put(LESSON, lessonDoList);
									}
								jsonRepStr = mainObj.toString();

							}
						}
					}
				}*/
				return JsonDeserializer.deserialize(jsonRepStr, new TypeReference<HashMap<String, StandardsDo>>() {});
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new HashMap<String,StandardsDo>();
	}

	/**
	 *
	 * @function deserializeConcept
	 *
	 * @created_date : 13-Dec-2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param jsonRep
	 * @parm(s) : @return
	 *
	 * @return : ConceptDo
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public ConceptDo deserializeConcept(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), ConceptDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new ConceptDo();
	}

	/**
	 * @function deserializeTopicList
	 *
	 * @created_date : 18-Dec-2013
	 *
	 * @description
	 *
	 * @parm(s) : @param jsonRepresentation
	 * @parm(s) : @return
	 *
	 * @return : ArrayList<TopicDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	*/

	private ArrayList<TopicDo> deserializeTopicList(JsonRepresentation jsonRep) {
		ArrayList<TopicDo> topicDoList = new ArrayList<TopicDo>();
		TopicDo topicDo = new TopicDo();

		//ArrayList<ConceptDo> conceptDoList = new ArrayList<ConceptDo>();

		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {

				JSONArray topicArray = jsonRep.getJsonArray();

			//	topicDoList = JsonDeserializer.deserialize(topicArray, ArrayList<TopicDo>.class);
				for(int i=0;i<topicArray.length();i++) {
					topicDoList.add(JsonDeserializer.deserialize(topicArray.getJSONObject(i).toString(), TopicDo.class));
				//	ArrayList<LessonDo> lessonDoList = new ArrayList<LessonDo>();
					/*try {
						if(!topicArray.getJSONObject(i).isNull(COLLECTION)){
							lessonDoList.add(JsonDeserializer.deserialize(topicArray.getJSONObject(i).toString(), LessonDo.class));
						}
						if(topicArray.getJSONObject(i).getJSONArray(LESSON)!=null) {
							for(int j=0;j<topicArray.getJSONObject(i).getJSONArray(LESSON).length();j++)
							{
								lessonDoList.add(JsonDeserializer.deserialize(topicArray.getJSONObject(i).getJSONArray(LESSON).getJSONObject(j).toString(), LessonDo.class));

								for(int k=0;k<topicArray.getJSONObject(i).getJSONArray(LESSON).getJSONObject(j).getJSONArray(CONCEPT).length();k++)
								{
								lessonDoList.add(JsonDeserializer.deserialize(topicArray.getJSONObject(i).getJSONArray(LESSON).getJSONObject(j).getJSONArray(CONCEPT).getJSONObject(k).toString(), LessonDo.class));
								}
							}
							topicDoList.add(JsonDeserializer.deserialize(topicArray.getJSONObject(i).toString(), TopicDo.class));
							topicDoList.get(i).setLesson(lessonDoList);
						}
						else
						{
							conceptDoList.add(JsonDeserializer.deserialize(topicArray.getJSONObject(i).toString(), ConceptDo.class));
						}
					} catch (JSONException e) {
						try {
							if(topicArray.getJSONObject(i).getJSONArray(COLLECTION)!=null) {

							} else {
								conceptDoList.add(JsonDeserializer.deserialize(topicArray.getJSONObject(i).toString(), ConceptDo.class));
							}
						} catch(Exception ex) {
							conceptDoList.add(JsonDeserializer.deserialize(topicArray.getJSONObject(i).toString(), ConceptDo.class));
						}
					}*/
				}
/*				if(conceptDoList.size()>0) {
					topicDo.setCollection(conceptDoList);
					topicDoList.add(topicDo);
				}*/
			}
			catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return topicDoList;
	}

	/**
	 * @function getLibraryName
	 *
	 * @created_date : 30-Dec-2013
	 *
	 * @description
	 *
	 * @parm(s) : @param libraryName
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private String getLibraryName(String libraryName) {
		String libraryCode = "";
		if(libraryName.equalsIgnoreCase(RUSD)) {
			libraryCode = RUSD;
		}
		return libraryCode;
	}

	private String getStandardId(String standardId) {
		String standardIdVal = "";
		if(standardId != null) {
			standardIdVal = standardId;
		}
		return standardIdVal;
	}

	/**
	 *
	 * @function deserializeConceptDoList
	 *
	 * @created_date : 07-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param jsonRep
	 * @return : ArrayList<ConceptDo>
	 *
	 * @throws : JSONException
	 *
	 */
	public ArrayList<ConceptDo> deserializeConceptDoList(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<ArrayList<ConceptDo>>() {
				});
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new ArrayList<ConceptDo>();
	}

	@Override
	public ArrayList<ConceptDo> getPopularCollectionsData(String courseId) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		JsonResponseRepresentation jsonResponseRep = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_POPULAR_LIBRARY, courseId);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.LIBRARY_NAME, GooruConstants.POPULAR_LOWER_CASE);
		params.put(GooruConstants.PAGE_NUM, "1");
		params.put(GooruConstants.PAGE_SIZE, "5");
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		try {
			getLogger().info(" -- getPopularCollectionsData -- "+url);
			jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		} catch (Exception e){
			logger.error("Exception::", e);
		}
		if(jsonResponseRep.getStatusCode()==200) {
			jsonRepresentation=jsonResponseRep.getJsonRepresentation();
			return deserializeConceptDoList(jsonRepresentation);
		}
		return new ArrayList<ConceptDo>();
	}

	//Partner Library APIs
	@Override
	public PartnerFolderListDo getLibraryPartnerWorkspace(String gooruUid, int limit, String sharingType, String collectionType, String placeToken) throws GwtException {
		PartnerFolderListDo partnerFolderListDo = new PartnerFolderListDo();
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		String sessionToken = getLoggedInSessionToken();


		partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_PARTNER_WORKSPACE, gooruUid);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.FETCH_CHILDS, GooruConstants.TRUE);
		params.put(GooruConstants.ITEM_LIMIT, String.valueOf(limit));
		params.put(GooruConstants.OFFSET, "0");
		params.put(GooruConstants.LIMIT, "20");
		params.put(GooruConstants.TOP_LEVEL_COLLECTION_TYPE, GooruConstants.FOLDER);
		params.put(GooruConstants.ORDER_BY, GooruConstants.SEQUENCE);
		if(sharingType!=null){
			params.put(GooruConstants.SHARING,sharingType);
//			sessionToken=sessionToken+"&sharing="+sharingType;
		}
		if(collectionType!=null){
			params.put(GooruConstants.COLLECTION_TYPE,collectionType);
//			sessionToken=sessionToken+"&collectionType="+collectionType;
		}
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("-- getLibraryPartnerWorkspace -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		getLogger().info("----Partners WS API - Time stamp to get data from API on server side --"+System.currentTimeMillis());
		if(placeToken.equals(PlaceTokens.PROFILE_PAGE)) {
			//partnerFolderListDo = new ProfileLibraryDeserializer().deserializeFolderList(jsonRep);
		} else {
			partnerFolderListDo = deserializeFolderList(jsonRep);
		}
		return partnerFolderListDo;
	}

	@Override
	public PartnerConceptListDo getPartnerChildFolders(String gooruUid, int offset, int limit, String parentId,String sharingType, String collectionType) throws GwtException {
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		String sessionToken = getLoggedInSessionToken();
		partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_CHILD_FOLDER_LIST, parentId);
		Map<String, String> params = new LinkedHashMap<String, String>();
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
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("-- getPartnerChildFolders -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializePartnerFolderList(jsonRep);
	}

	@Override
	public PartnerFolderListDo getPartnerPaginationWorkspace(String parentId, String sharingType, int limit) throws GwtException {
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		String sessionToken = getLoggedInSessionToken();


		partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_PARTNER_CHILD_FOLDER_LIST, parentId);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.FETCH_CHILDS,GooruConstants.TRUE);
		params.put(GooruConstants.ITEM_LIMIT, String.valueOf(limit));
		params.put(GooruConstants.OFFSET, "0");
		params.put(GooruConstants.ORDER_BY, GooruConstants.SEQUENCE);
		if(sharingType!=null){
			params.put(GooruConstants.SHARING, sharingType);
//			sessionToken=sessionToken+"&sharing="+sharingType;
		}
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("getPartnerPaginationWorkspace -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return deserializePaginatedWorkspaceFolders(jsonRep);
	}

	public PartnerFolderListDo deserializeFolderList(JsonRepresentation jsonRep) {
		PartnerFolderListDo searchResults = new PartnerFolderListDo();
		ArrayList<PartnerFolderDo> firstLevelFolders = new ArrayList<PartnerFolderDo>();
		Integer count;
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				JSONArray firstLevelArray = jsonRep.getJsonObject().getJSONArray(SEARCH_RESULTS);
				count = jsonRep.getJsonObject().getInt(COUNT);
				for(int i=0;i<firstLevelArray.length();i++) {
					PartnerFolderDo folderFirstLevelDo = JsonDeserializer.deserialize(firstLevelArray.getJSONObject(i).toString(), PartnerFolderDo.class);
					if(i==0) {
						if(firstLevelArray.getJSONObject(i).get("type").equals("scollection")) {
							ArrayList<ConceptDo> conceptDoList = new ArrayList<ConceptDo>();
							ConceptDo conceptDo = JsonDeserializer.deserialize(firstLevelArray.getJSONObject(i).toString(), ConceptDo.class);
							conceptDoList.add(conceptDo);
							searchResults.setCollections(conceptDoList);
						} else {
							JSONArray secondLevelArray = firstLevelArray.getJSONObject(i).getJSONArray(COLLECTION_ITEMS);
							folderFirstLevelDo.setFolderItems(getDeserializedPartnerList(secondLevelArray, i));
						}
					}
					firstLevelFolders.add(folderFirstLevelDo);
				}
				searchResults.setCount(count);
				searchResults.setSearchResult(firstLevelFolders);
			}
			return searchResults;
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return new PartnerFolderListDo();
	}

	private ArrayList<PartnerFolderDo> getDeserializedPartnerList(JSONArray secondLevelArray, int level) {
		ArrayList<PartnerFolderDo> secondLevelFolders = new ArrayList<PartnerFolderDo>();
		try {
			if(secondLevelArray.length()>0) {
				for(int j=0;j<secondLevelArray.length();j++) {
					JSONObject secondLevelJsonObject = secondLevelArray.getJSONObject(j);
					PartnerFolderDo folderTwoLevelDo = JsonDeserializer.deserialize(secondLevelJsonObject.toString(), PartnerFolderDo.class);
					if(!secondLevelJsonObject.isNull(COLLECTION_ITEMS)) {
						JSONArray thirdLevelArray = secondLevelJsonObject.getJSONArray(COLLECTION_ITEMS);
						ArrayList<ConceptDo> thirdLevelConcepts = new ArrayList<ConceptDo>();
						ArrayList<PartnerFolderDo> thirdLevelFolders = new ArrayList<PartnerFolderDo>();
						if(thirdLevelArray.length()>0) {
							for(int k=0;k<thirdLevelArray.length();k++) {
								JSONObject thirdLevelJsonObject = thirdLevelArray.getJSONObject(k);
								if(thirdLevelJsonObject.getString(TYPE).equalsIgnoreCase(FOLDER)) {
									PartnerFolderDo folderThirdLevelDo = JsonDeserializer.deserialize(thirdLevelJsonObject.toString(), PartnerFolderDo.class);
									JSONArray fourthLevelArray = thirdLevelJsonObject.getJSONArray(COLLECTION_ITEMS);
									ArrayList<ConceptDo> fourthLevelConcepts = new ArrayList<ConceptDo>();
									if(fourthLevelArray.length()>0) {
										for(int m=0;m<fourthLevelArray.length();m++) {
											ConceptDo conceptDo = new ConceptDo();
											JSONObject fourthLevelJsonObject = fourthLevelArray.getJSONObject(m);
											if(!fourthLevelJsonObject.isNull(GOALS)) {
												conceptDo.setGoals(fourthLevelJsonObject.getString(GOALS));
											} else {
												conceptDo.setGoals("");
											}
											conceptDo.setTitle(fourthLevelJsonObject.getString(TITLE));
											conceptDo.setGooruOid(fourthLevelJsonObject.getString(GOORUOID));
											//logger.info("fourthLevelJsonObject.isNull(THUMBNAILS) onbbb::::"+fourthLevelJsonObject.getJSONObject(THUMBNAILS));
											if(!fourthLevelJsonObject.isNull(THUMBNAILS)){
											conceptDo.setThumbnails(JsonDeserializer.deserialize(fourthLevelJsonObject.getJSONObject(THUMBNAILS).toString(), ThumbnailDo.class));
											}else{
												conceptDo.setThumbnails(null);
											}
											if(m==0) {
												JSONArray resourceArray = fourthLevelJsonObject.getJSONArray(COLLECTION_ITEMS);
												ArrayList<LibraryCollectionItemDo> collectionItems = new ArrayList<LibraryCollectionItemDo>();
												for(int l=0;l<resourceArray.length();l++) {
													LibraryCollectionItemDo libraryCollectionItemDo = new LibraryCollectionItemDo();
													libraryCollectionItemDo.setResource(JsonDeserializer.deserialize(resourceArray.getJSONObject(l).toString(), LibraryResourceDo.class));
													collectionItems.add(libraryCollectionItemDo);
												}
												conceptDo.setCollectionItems(collectionItems);
											}
											fourthLevelConcepts.add(conceptDo);
										}
									}
									folderThirdLevelDo.setCollections(fourthLevelConcepts);
									thirdLevelFolders.add(folderThirdLevelDo);
								} else {
									thirdLevelConcepts.add(getConceptDeserialization(thirdLevelJsonObject,k));
								}
							}
							if(thirdLevelArray.getJSONObject(0).getString(TYPE).equalsIgnoreCase(FOLDER)) {
								folderTwoLevelDo.setFolderItems(thirdLevelFolders);
							} else {
								folderTwoLevelDo.setCollections(thirdLevelConcepts);
							}
						}
						if(thirdLevelArray.length()>0) {
							secondLevelFolders.add(folderTwoLevelDo);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return secondLevelFolders;
	}

	public ConceptDo getConceptDeserialization(JSONObject thirdLevelJsonObject, int k) {
		ConceptDo conceptDo = new ConceptDo();
		try {
			if(thirdLevelJsonObject.isNull(GOALS)) {

			} else {
				conceptDo.setGoals(thirdLevelJsonObject.getString(GOALS));
			}

			conceptDo.setTitle(thirdLevelJsonObject.getString(TITLE));
			if(thirdLevelJsonObject.isNull("itemCount")) {

			}else{
				conceptDo.setItemCount(thirdLevelJsonObject.getInt("itemCount"));
			}

			conceptDo.setGooruOid(thirdLevelJsonObject.getString(GOORUOID));
			if(!thirdLevelJsonObject.isNull(THUMBNAILS)){
			   conceptDo.setThumbnails(JsonDeserializer.deserialize(thirdLevelJsonObject.getJSONObject(THUMBNAILS).toString(), ThumbnailDo.class));
			}
			if(k==0) {
				JSONArray resourceArray = thirdLevelJsonObject.getJSONArray(COLLECTION_ITEMS);
				ArrayList<LibraryCollectionItemDo> collectionItems = new ArrayList<LibraryCollectionItemDo>();
				for(int l=0;l<resourceArray.length();l++) {
					LibraryCollectionItemDo libraryCollectionItemDo = new LibraryCollectionItemDo();
					libraryCollectionItemDo.setResource(JsonDeserializer.deserialize(resourceArray.getJSONObject(l).toString(), LibraryResourceDo.class));
					collectionItems.add(libraryCollectionItemDo);
				}
				conceptDo.setCollectionItems(collectionItems);
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return conceptDo;
	}

	public PartnerConceptListDo deserializePartnerFolderList(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), new TypeReference<PartnerConceptListDo>() {});
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return new PartnerConceptListDo();
	}

	public PartnerFolderListDo deserializePaginatedWorkspaceFolders(JsonRepresentation jsonRep) {
		PartnerFolderListDo searchResults = new PartnerFolderListDo();
		Integer count;
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				JSONArray secondLevelArray = jsonRep.getJsonObject().getJSONArray(SEARCH_RESULTS);
				count = jsonRep.getJsonObject().getInt(COUNT);
				searchResults.setCount(count);
				searchResults.setSearchResult(getDeserializedPartnerList(secondLevelArray, 0));
			}
			return searchResults;
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return new PartnerFolderListDo();
	}

	@Override
	public ArrayList<LibraryUserDo> getPartners() throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_PARTNERS);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeCollaborators(jsonRepresentation);
	}

	@Override
	public ProfileLibraryListDo getLibraryWorkspace(String gooruUid, int limit, String sharingType, String collectionType, int offset) throws GwtException {
		ProfileLibraryListDo profileLibraryListDo = new ProfileLibraryListDo();
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		String sessionToken = getLoggedInSessionToken();

		partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_SAUSD_LIBRARY, gooruUid);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.FETCH_CHILDS, GooruConstants.TRUE);
		params.put(GooruConstants.ITEM_LIMIT, String.valueOf(limit));
		params.put(GooruConstants.OFFSET, String.valueOf(offset));
		params.put(GooruConstants.LIMIT, "14");
		params.put(GooruConstants.TOP_LEVEL_COLLECTION_TYPE, GooruConstants.FOLDER);
		params.put(GooruConstants.ORDER_BY, GooruConstants.SEQUENCE);
		if(sharingType!=null){
			params.put(GooruConstants.SHARING, sharingType);
//			sessionToken=sessionToken+"&sharing="+sharingType;
		}
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("-- getLibraryWorkspace -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		profileLibraryListDo = new ProfileLibraryDeserializer().deserializeFolderList(jsonRep);
		return profileLibraryListDo;
	}

	@Override
	public ProfileLibraryListDo getLibraryPaginationWorkspace(String parentId, String sharingType, int limit,int offset) throws GwtException {
		ProfileLibraryListDo profileLibraryListDo = new ProfileLibraryListDo();
		JsonRepresentation jsonRep = null;
		String partialUrl = null;
		String sessionToken = getLoggedInSessionToken();

		partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_PARTNER_CHILD_FOLDER_LIST, parentId);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.FETCH_CHILDS,GooruConstants.TRUE);
		params.put(GooruConstants.ITEM_LIMIT, String.valueOf(limit));
		params.put(GooruConstants.OFFSET, String.valueOf(offset));
		params.put(GooruConstants.ORDER_BY, GooruConstants.SEQUENCE);
		if(sharingType!=null){
			params.put(GooruConstants.SHARING, sharingType);
//			sessionToken=sessionToken+"&sharing="+sharingType;
		}
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("-- getLibraryPaginationWorkspace -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		profileLibraryListDo = new ProfileLibraryDeserializer().deserializeFolderList(jsonRep);
		return profileLibraryListDo;
	}

	@Override
	public ProfileLibraryListDo getLibraryCoursesList(String parentId,String sharingType,String offset) throws GwtException {
			ProfileLibraryListDo profileLibraryListDo = new ProfileLibraryListDo();
			JsonRepresentation jsonRep = null;
			String partialUrl = null;
			String sessionToken = getLoggedInSessionToken();

			partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_CHILD_FOLDER_LIST_PUBLIC, parentId);
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put(GooruConstants.OFFSET, offset);
			params.put(GooruConstants.LIMIT, "20");
			params.put(GooruConstants.SHARING, GooruConstants.SHARING_TYPE);
			params.put(GooruConstants.ORDER_BY, GooruConstants.SEQUENCE);
			String url = AddQueryParameter.constructQueryParams(partialUrl, params);
			getLogger().info("-- getLibraryCoursesList -- "+url);
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
			jsonRep = jsonResponseRep.getJsonRepresentation();
			profileLibraryListDo = new ProfileLibraryDeserializer().deserializeFolderList(jsonRep);
			return profileLibraryListDo;
	}

	@Override
	public ArrayList<StandardCourseDo> getStandardLibraryMenuList(String subjectCode, String libraryName) throws GwtException,ServerDownException {
		ArrayList<StandardCourseDo> standardCourseList=new ArrayList<StandardCourseDo>();
		JsonRepresentation jsonRep = null;
		String url=UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_STANDARD_LIBRARY_MENUS, subjectCode);
		url = AddQueryParameter.constructQueryParams(url, GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
//		url=url+getLibraryName(libraryName);
		getLogger().info("getStandardLibraryMenuList---:::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		try {
			JSONArray libraryMenuArray=jsonRep.getJsonArray();
			if(libraryMenuArray!=null){
				for(int index=0;index<libraryMenuArray.length();index++){
					StandardCourseDo standardCourseDo=deserializeStandardLibraryList(libraryMenuArray.getJSONObject(index));
					standardCourseDo.setCourse(deserializeStandardLibraryCourses(libraryMenuArray.getJSONObject(index).getJSONArray(NODE)));
					standardCourseList.add(standardCourseDo);
				}
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return standardCourseList;
	}

	public StandardCourseDo deserializeStandardLibraryList(JSONObject jsonObject) {
		if (jsonObject != null) {
				return JsonDeserializer.deserialize(jsonObject.toString(), new TypeReference<StandardCourseDo>() {
				});
		}
		return new StandardCourseDo();
	}

	public ArrayList<CourseDo> deserializeStandardLibraryCourses(JSONArray jsonArray) {
		if (jsonArray != null && jsonArray.length()>0) {
			return JsonDeserializer.deserialize(jsonArray.toString(), new TypeReference<ArrayList<CourseDo>>() {
			});
		}
		return new ArrayList<CourseDo>();
	}

	/** New Library Optimized APIs Implementation**/
	@Override
	public HashMap<String, SubjectDo> deserializeLibrarySubjects(String subjectName, String courseId, String libraryName, String jsonString) throws GwtException {
		ArrayList<SubjectDo> subjectList = new ArrayList<SubjectDo>();
		HashMap<String, SubjectDo> subjectMap = new HashMap<String, SubjectDo>();

		if (jsonString != null) {
			try {
				String jsonRepStr = jsonString;
				subjectList = JsonDeserializer.deserialize(jsonRepStr, new TypeReference<ArrayList<SubjectDo>>() {});
				for (SubjectDo object : subjectList) {
					subjectMap.put(object.getLabel(), object);
				}
			} catch (Exception e) {
				logger.error("Exception::", e);
			}
		}
		if(subjectName!=null) {
			ArrayList<CourseDo> courseList = getLibraryCourses(subjectName, libraryName);
			subjectMap.get(subjectName).setData(courseList);
			if(courseId==null) {
				courseId = courseList.get(0).getCodeId()+"";
			}
			ArrayList<UnitDo> unitListDo = getLibraryUnits(subjectName, courseId, libraryName);
			subjectMap.get(subjectName).getData().get(getCourseDoFromCourseId(courseList, courseId)).setUnit(unitListDo);
		}
		return subjectMap;
	}

	@Override
	public String getLibrarySubjectsJson(String subjectName, String courseId, String libraryName) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_SUBJECTS_OPTIMIZED);
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.LIBRARY_NAME, libraryName);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		String response;
		try {
			response = jsonRepresentation.getText();
		} catch (IOException e) {
			throw new RuntimeException("message", e);
		}
		return response;
	}

	@Override
	public HashMap<String, SubjectDo> getLibrarySubjects(String subjectName, String courseId, String libraryName) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_SUBJECTS_OPTIMIZED);
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.LIBRARY_NAME, libraryName);
		getLogger().info("-- getLibrarySubjects -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		HashMap<String,SubjectDo> subjectList = new HashMap<String,SubjectDo>();
		subjectList = deserializeLibrarySubjects(jsonRepresentation);
		if(subjectName!=null) {
			ArrayList<CourseDo> courseList = getLibraryCourses(subjectName, libraryName);
			subjectList.get(subjectName).setData(courseList);
			if(courseId==null) {
				courseId = courseList.get(0).getCodeId()+"";
			}
			ArrayList<UnitDo> unitListDo = getLibraryUnits(subjectName, courseId, libraryName);
			subjectList.get(subjectName).getData().get(getCourseDoFromCourseId(courseList, courseId)).setUnit(unitListDo);
		}
		return subjectList;
	}

	public HashMap<String,SubjectDo> deserializeLibrarySubjects(JsonRepresentation jsonRep) {
		ArrayList<SubjectDo> subjectList = new ArrayList<SubjectDo>();
		HashMap<String, SubjectDo> subjectMap = new HashMap<String, SubjectDo>();

		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				String jsonRepStr = jsonRep.getJsonArray().toString();
				subjectList = JsonDeserializer.deserialize(jsonRepStr, new TypeReference<ArrayList<SubjectDo>>() {});
				for (SubjectDo object : subjectList) {
					subjectMap.put(object.getLabel(), object);
				}
				return subjectMap;
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new HashMap<String,SubjectDo>();
	}

	public Integer getCourseDoFromCourseId(ArrayList<CourseDo> courseList, String courseId) {
		final Iterator<CourseDo> courses = courseList.iterator();
		int courseCount = 0;
		while (courses.hasNext()) {
			final CourseDo courseDo = courses.next();
			String course = ""+courseDo.getCodeId();
			if(course.equals(courseId)) {
				return courseCount;
			}
			courseCount++;
		}
		return courseCount;
	}

	@Override
	public ArrayList<CourseDo> getLibraryCourses(String subjectName, String libraryName) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_COURSES_OPTIMIZED, subjectName);
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.LIBRARY_NAME, libraryName);
		getLogger().info("-- community lib - getLibraryCourses - "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeLibraryCourses(jsonRepresentation, subjectName, libraryName);
	}

	public ArrayList<CourseDo> deserializeLibraryCourses(JsonRepresentation jsonRep, String subjectName, String libraryName) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				ArrayList<CourseDo> courseDoList = JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<ArrayList<CourseDo>>() {});
				if(!subjectName.equalsIgnoreCase(FEATURED)) {
					Collections.sort(courseDoList, new ArrayListSorter("grade", true));
				}
				if(libraryName.equals(RUSD)&&subjectName.equals(FEATURED)) {
					courseDoList = getRusdLearnsData(courseDoList);
				}
				return courseDoList;
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new ArrayList<CourseDo>();
	}

	private ArrayList<CourseDo> getRusdLearnsData(ArrayList<CourseDo> courseDoList) {
		CourseDo courseDo = new CourseDo();
		courseDo.setLabel(INCLUDED_COURSE);
		courseDo.setParentId(AUTODESK_GOORU_UID);
		ThumbnailDo thumbnailDo = new ThumbnailDo();
		thumbnailDo.setUrl(COURSE_100_75_IMG);
		courseDo.setThumbnails(thumbnailDo);
		LibraryUserDo libraryUserDo = new LibraryUserDo();
		libraryUserDo.setGender(MALE);
		libraryUserDo.setLastName(RUSD_LAST_NAME);
		libraryUserDo.setUsername(RUSDLEARNS);
		libraryUserDo.setGooruUId(AUTODESK_GOORU_UID);
		courseDo.setCreator(libraryUserDo);
		courseDo.setUser(null);
		try {
			for(int i=0;i<courseDoList.size();i++) {
				if(courseDoList.get(i).getLabel().equalsIgnoreCase(EXCLUDED_COURSE)) {
					courseDoList.remove(i);
				}
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		courseDoList.add(0, courseDo);
		return courseDoList;
	}

	@Override
	public ArrayList<UnitDo> getLibraryUnits(String subjectName, String courseId, String libraryName) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_UNITS_OPTIMIZED, subjectName, courseId);
		url = AddQueryParameter.constructQueryParams(url, GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
//		url=url+getLibraryName(libraryName);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeLibraryUnits(jsonRepresentation, subjectName);
	}

	public ArrayList<UnitDo> deserializeLibraryUnits(JsonRepresentation jsonRep, String subjectName) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				return JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<ArrayList<UnitDo>>() {
				});
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new ArrayList<UnitDo>();
	}

	@Override
	public ArrayList<TopicDo> getLibraryTopics(String subjectName, String unitId, String libraryName, int offset, int limit) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_TOPICS_OPTIMIZED, subjectName, unitId);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.OFFSET, String.valueOf(offset));
		params.put(GooruConstants.LIMIT, TOTAL_LIMIT);
		params.put(GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		url = AddQueryParameter.constructQueryParams(url, GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
//		url=url+getLibraryName(libraryName);
		getLogger().info("getLibraryTopics---:::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeTopicList(jsonRepresentation);
	}

	@Override
	public ArrayList<LessonDo> getLibraryLessons(String subjectName, String topicId, String libraryName, int offset, int limit) throws GwtException {
		JsonRepresentation jsonRepresentation = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_LIBRARY_LESSONS_OPTIMIZED, subjectName, topicId);
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(GooruConstants.OFFSET, String.valueOf(offset));
		params.put(GooruConstants.LIMIT, String.valueOf(limit));
		params.put(GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		url = AddQueryParameter.constructQueryParams(url, GooruConstants.LIBRARY_NAME, getLibraryName(libraryName));
//		url=url+getLibraryName(libraryName);
		getLogger().info("getLibraryLessons---:::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRepresentation=jsonResponseRep.getJsonRepresentation();
		return deserializeLessons(jsonRepresentation);
	}


}