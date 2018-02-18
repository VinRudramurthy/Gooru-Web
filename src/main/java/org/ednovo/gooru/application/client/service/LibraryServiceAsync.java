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
import org.ednovo.gooru.application.shared.model.library.ConceptDo;
import org.ednovo.gooru.application.shared.model.library.CourseDo;
import org.ednovo.gooru.application.shared.model.library.LessonDo;
import org.ednovo.gooru.application.shared.model.library.LibraryUserDo;
import org.ednovo.gooru.application.shared.model.library.PartnerConceptListDo;
import org.ednovo.gooru.application.shared.model.library.PartnerFolderListDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;
import org.ednovo.gooru.application.shared.model.library.StandardCourseDo;
import org.ednovo.gooru.application.shared.model.library.StandardsDo;
import org.ednovo.gooru.application.shared.model.library.SubjectDo;
import org.ednovo.gooru.application.shared.model.library.TopicDo;
import org.ednovo.gooru.application.shared.model.library.UnitDo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LibraryServiceAsync extends BaseServiceAsync {

	void getCourses(String subjectName, String libraryName, AsyncCallback<ArrayList<CourseDo>> callback);
	void getLibraryFeaturedUsers(String libraryName, AsyncCallback<ArrayList<LibraryUserDo>> callback);
	void getLessonsOnPagination(String subjectName, String topicId, int offset, int limit, String libraryName, AsyncCallback<ArrayList<LessonDo>> callback);
	void getSubjects(String subjectId, String libraryName, AsyncCallback<HashMap<String, SubjectDo>> callback);
	void getSubjectsForStandards(String subjectId, String libraryName, AsyncCallback<HashMap<String, StandardsDo>> callback);
	void getConcept(String gooruOid, boolean skipCollectionItems, AsyncCallback<ConceptDo> callback);
	void getTopicsOnPagination(String subjectId, String unitId, String libraryName, int offset, String standardId, AsyncCallback<ArrayList<TopicDo>> callback);
	void getLibraryCollections(String courseType, String lessonId, String libraryName, AsyncCallback<ArrayList<ConceptDo>> callback);
	void getPopularCollectionsData(String courseId, AsyncCallback<ArrayList<ConceptDo>> callback);
	void getLibraryPartnerWorkspace(String gooruUid, int limit,String sharingType, String collectionType, String placeToken, AsyncCallback<PartnerFolderListDo> callback);
	void getPartnerChildFolders(String gooruUid, int offset, int limit,String parentId,String sharingType, String collectionType, AsyncCallback<PartnerConceptListDo> callback);
	void getPartnerPaginationWorkspace(String parentId,String sharingType, int limit, AsyncCallback<PartnerFolderListDo> callback);
	void getPartners(AsyncCallback<ArrayList<LibraryUserDo>> callback) throws GwtException;
	void getConceptForStandards(String gooruOid,String rooteNodeId, boolean skipCollectionItems, AsyncCallback<ConceptDo> callback);

	void getLibraryWorkspace(String gooruUid, int limit,String sharingType, String collectionType, int offset, AsyncCallback<ProfileLibraryListDo> callback);
	void getLibraryPaginationWorkspace(String parentId,String sharingType, int limit,int offset, AsyncCallback<ProfileLibraryListDo> callback);
	void getLibraryCoursesList(String parentId,String sharingType, String offset, AsyncCallback<ProfileLibraryListDo> callback);
	
	public void  getStandardLibraryMenuList(String subjectCode,String libraryName,AsyncCallback<ArrayList<StandardCourseDo>> callback);
	/** New Library Optimized APIs **/
	void getLibrarySubjects(String subjectName, String courseId, String libraryName, AsyncCallback<HashMap<String, SubjectDo>> callback);
	void getLibraryCourses(String subjectName, String libraryName, AsyncCallback<ArrayList<CourseDo>> callback);
	void getLibraryUnits(String subjectName, String courseId, String libraryName, AsyncCallback<ArrayList<UnitDo>> callback);
	void getLibraryTopics(String subjectName, String unitId, String libraryName, int offset, int limit, AsyncCallback<ArrayList<TopicDo>> callback);
	void getLibraryLessons(String subjectName, String topicId, String libraryName, int offset, int limit, AsyncCallback<ArrayList<LessonDo>> callback);
	void deserializeCollaborators(String jsonString, AsyncCallback<ArrayList<LibraryUserDo>> callback);
	void getLibraryContributorsUsers(String libraryName, AsyncCallback<String> callback);
	void deserializeLibrarySubjects(String subjectName, String courseId, String libraryName, String jsonString,
			AsyncCallback<HashMap<String, SubjectDo>> callback);
	void getLibrarySubjectsJson(String subjectName, String courseId,
			String libraryName, AsyncCallback<String> callback); 
}