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

package org.ednovo.gooru.application.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;
import org.ednovo.gooru.application.shared.model.library.CourseDo;
import org.ednovo.gooru.application.shared.model.library.LessonDo;
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

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwt-service/libraryService")
public interface LibraryService extends BaseService {
	
	/**
	 * @function getCourses 
	 * @return : ArrayList<CourseDo>
	 * @description: Get the list of courses of a user
	 * @param: subjectName
	 * @throws : GwtException
	 */
	public ArrayList<CourseDo> getCourses(String subjectName, String libraryName) throws GwtException, ServerDownException;
	
	/**
	 * @function getLibraryFeaturedUsers 
	 * @return : ArrayList<LibraryUserDo>
	 * @description: Get the list of featured users for the library
	 * @throws : GwtException
	 */
	public ArrayList<LibraryUserDo> getLibraryFeaturedUsers(String libraryName) throws GwtException, ServerDownException;
	
	/**
	 * @function getLessonsOnPagination 
	 * @return : ArrayList<LessonDo>
	 * @description: Get the list of Lesson based on the subject name and the topic id
	 * @param: subjectName
	 * @param: topicId
	 * @param: offset
	 * @param: limit
	 * @throws : GwtException
	 */
	public ArrayList<LessonDo> getLessonsOnPagination(String subjectName, String topicId, int offset, int limit, String libraryName) throws GwtException, ServerDownException;
	/**
	 * @function getSubjects 
	 * @return : HashMap<String,SubjectDo>
	 * @description: Get the list of subjects in the taxonomy list
	 * @param: subjectId
	 * @throws : GwtException
	 */
	public HashMap<String,SubjectDo> getSubjects(String subjectId, String libraryName) throws GwtException, ServerDownException;
	
	public HashMap<String,StandardsDo> getSubjectsForStandards(String subjectId, String libraryName) throws GwtException, ServerDownException;
	/**
	 * @function getConcept 
	 * @return : ConceptDo
	 * @description: Get the Collection data for a concept
	 * @param: gooruOid
	 * @param: skipCollectionItems
	 * @throws : GwtException
	 */
	public ConceptDo getConcept(String gooruOid, boolean skipCollectionItems) throws GwtException, ServerDownException;
	
	/**
	 * @function getTopicsOnPagination 
	 * @return : ArrayList<TopicDo>
	 * @description: Get the list of the topics based on the unitId
	 * @parm(s) : @param unitId
	 * @parm(s) : @throws GwtException
	 * @throws : GwtException
	 */
	public ArrayList<TopicDo> getTopicsOnPagination(String subjectId, String unitId, String libraryName, int offset, String standardId) throws GwtException, ServerDownException;
	
	/**
	 * @function getLibraryCollections 
	 * @return : ArrayList<ConceptDo>
	 * @description: Get the list of the related concepts to a particular collection from Library
	 * @parm(s) : @param unitId
	 * @parm(s) : @throws GwtException
	 * @throws : GwtException
	 */
	public ArrayList<ConceptDo> getLibraryCollections(String courseType, String lessonId, String libraryName) throws GwtException, ServerDownException;
	
	/**
	 * @function getPopularCollectionsData 
	 * @return : ArrayList<ConceptDo>
	 * @description: Get the list of the related concepts to a polular collection from Library
	 * @parm(s) : @param courseType
	 * @parm(s) : @param lessonId
	 * @parm(s) : @param libraryName
	 * @throws : GwtException
	 */
	
	public ArrayList<ConceptDo> getPopularCollectionsData(String courseId) throws GwtException, ServerDownException;
	
	/**
	 * @function getLibraryPartnerWorkspace 
	 * @return : FolderListDo
	 * @description: Get the list of the workspace of the partners
	 * @parm(s) : @param offset
	 * @parm(s) : @param limit
	 * @parm(s) : @param sharingType
	 * @parm(s) : @param collectionType
	 * @throws : GwtException
	 */
	public PartnerFolderListDo getLibraryPartnerWorkspace(String gooruUid, int limit,String sharingType, String collectionType, String placeToken) throws GwtException, ServerDownException;
	
	/**
	 * Get Folders of the second level and third level by User
	 * @return serialized created {@link PartnerFolderListDo}
	 * @parm(s) : @param offset
	 * @parm(s) : @param limit
	 * @parm(s) : @param parentId
	 * @parm(s) : @param sharingType
	 * @parm(s) : @param collectionType
	 * @throws GwtException
	 */
	public PartnerConceptListDo getPartnerChildFolders(String gooruUid, int offset, int limit,String parentId,String sharingType, String collectionType) throws GwtException, ServerDownException;

	/**
	 * Get paginated workspace API
	 * @return serialized created {@link PartnerFolderDo}
	 * @parm(s) : @param limit
	 * @parm(s) : @param parentId
	 * @parm(s) : @param sharingType
	 * @throws GwtException
	 */
	public PartnerFolderListDo getPartnerPaginationWorkspace(String parentId, String sharingType, int limit) throws GwtException, ServerDownException;
	
	/**
	 * Get all the partners
	 * @return serialized created {@link ArrayList<LibraryUserDo>}
	 * @throws GwtException
	 */
	public ArrayList<LibraryUserDo> getPartners() throws GwtException, ServerDownException;

	public ConceptDo getConceptForStandards(String gooruOid, String roteNodeId,
			boolean skipCollectionItems) throws GwtException, ServerDownException;
	
	/**
	 * @function getProfileLibraryWorkspace
	 * @return : ProfileLibraryListDo
	 * @description: Get the list of the workspace of the partners
	 * @parm(s) : @param offset
	 * @parm(s) : @param limit
	 * @parm(s) : @param sharingType
	 * @parm(s) : @param collectionType
	 * @throws : GwtException
	 */
	public ProfileLibraryListDo getLibraryWorkspace(String gooruUid, int limit,String sharingType, String collectionType, int offset) throws GwtException, ServerDownException;
	
	/**
	 * Get paginated workspace API
	 * @return serialized created {@link ProfileLibraryListDo}
	 * @parm(s) : @param limit
	 * @parm(s) : @param parentId
	 * @parm(s) : @param sharingType
	 * @throws GwtException
	 */
	public ProfileLibraryListDo getLibraryPaginationWorkspace(String parentId, String sharingType, int limit,int offset) throws GwtException, ServerDownException;
	
	/**
	 * @function getConcept 
	 * @return : ConceptDo
	 * @description: Get the Collection data for a concept
	 * @param: gooruOid
	 * @param: skipCollectionItems
	 * @throws : GwtException
	 */
	public ProfileLibraryListDo getLibraryCoursesList(String parentId,String sharingType, String offset) throws GwtException, ServerDownException;
	
	public ArrayList<StandardCourseDo> getStandardLibraryMenuList(String subjectCode,String libraryName) throws GwtException, ServerDownException;
	
	/** New Optimized Library APIs **/
	
	/**
	 * @function getLibrarySubjects 
	 * @return : HashMap<String,SubjectDo>
	 * @description: Get the list of subjects in the taxonomy list
	 * @param: libraryName
	 * @throws : GwtException
	 */
	public HashMap<String,SubjectDo> getLibrarySubjects(String subjectName, String courseId, String libraryName) throws GwtException;	

	/**
	 * @function getLibraryCourses
	 * @return : ArrayList<CourseDo>
	 * @description: Get the list of courses of the library
	 * @param: subjectName
	 * @param: libraryName
	 * @throws : GwtException
	 */
	public ArrayList<CourseDo> getLibraryCourses(String subjectName, String libraryName) throws GwtException;

	/**
	 * @function getLibraryUnits
	 * @return : ArrayList<UnitDo>
	 * @description: Get the list of units of the library
	 * @param: subjectName
	 * @param: courseId
	 * @param: libraryName
	 * @throws : GwtException
	 */
	public ArrayList<UnitDo> getLibraryUnits(String subjectName, String courseId, String libraryName) throws GwtException;
	
	/**
	 * @function getLibraryTopics
	 * @return : ArrayList<TopicDo>
	 * @description: Get the list of topics of the library
	 * @param: subjectName
	 * @param: unitId
	 * @param: libraryName
	 * @throws : GwtException
	 */
	public ArrayList<TopicDo> getLibraryTopics(String subjectName, String unitId, String libraryName, int offset, int limit) throws GwtException;

	/**
	 * @function getLibraryLessons
	 * @return : ArrayList<LessonDo>
	 * @description: Get the list of Lessons of the library
	 * @param: subjectName
	 * @param: topicId
	 * @param: libraryName
	 * @throws : GwtException
	 */
	public ArrayList<LessonDo> getLibraryLessons(String subjectName, String topicId, String libraryName, int offset, int limit) throws GwtException;

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
	 * @parm(s) : @throws GwtException
	 * 
	 * @return : ArrayList<LibraryUserDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public ArrayList<LibraryUserDo> deserializeCollaborators(String jsonString) throws GwtException;

	/**
	 * 
	 * @function getLibraryContributorsUsers 
	 * 
	 * @created_date : 27-Apr-2015
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param libraryName
	 * @parm(s) : @return
	 * @parm(s) : @throws GwtException
	 * 
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	String getLibraryContributorsUsers(String libraryName) throws GwtException;

	/**
	 * 
	 * @function deserializeLibrarySubjects 
	 * 
	 * @created_date : 27-Apr-2015
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param jsonString
	 * @parm(s) : @return
	 * @parm(s) : @throws GwtException
	 * 
	 * @return : HashMap<String,SubjectDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	HashMap<String, SubjectDo> deserializeLibrarySubjects(String subjectName, String courseId, String libraryName,String jsonString)
			throws GwtException;
	/**
	 * 
	 * @function getLibrarySubjectsJson 
	 * 
	 * @created_date : 27-Apr-2015
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param subjectName
	 * @parm(s) : @param courseId
	 * @parm(s) : @param libraryName
	 * @parm(s) : @return
	 * @parm(s) : @throws GwtException
	 * 
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	String getLibrarySubjectsJson(String subjectName, String courseId,
			String libraryName) throws GwtException;
}