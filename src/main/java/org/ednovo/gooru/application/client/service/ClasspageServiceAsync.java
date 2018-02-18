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
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.classpages.MasterReportDo;
import org.ednovo.gooru.application.shared.model.classpages.PlanContentDo;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.application.shared.model.content.AssignmentDo;
import org.ednovo.gooru.application.shared.model.content.AssignmentsListDo;
import org.ednovo.gooru.application.shared.model.content.ClassLessonDo;
import org.ednovo.gooru.application.shared.model.content.ClassPageCollectionDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageListDo;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.ResourceDo;
import org.ednovo.gooru.application.shared.model.content.StudentsAssociatedListDo;
import org.ednovo.gooru.application.shared.model.content.TaskDo;
import org.ednovo.gooru.application.shared.model.content.TaskResourceAssocDo;
import org.ednovo.gooru.application.shared.model.content.UserPlayedSessionDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.user.ProfilePageDo;

import com.google.gwt.user.client.rpc.AsyncCallback;
//import org.ednovo.gooru.shared.model.content.ResourceItemDo;

public interface ClasspageServiceAsync extends BaseServiceAsync {

	void createClasspage(CollectionDo collectionDo, AsyncCallback<CollectionDo> callback);
//	Version 1 APIs
//	void getClasspageById(String classpageId, AsyncCallback<CollectionDo> callback);
	
//	void getAllClasspages(AsyncCallback<List<CollectionDo>> callback);
	
//	void deleteAssignment(String assignmentId, AsyncCallback<Void> callback);
	
//	void updateAssignment(CollectionDo collectionDo, String dueDate, AsyncCallback<CollectionDo> callback);
	
//	void getClasspageByCode(String classpageCode, AsyncCallback<CollectionDo> callback);

//	void addCollectionToAssignment(String collectionId, String assignmentId, CollectionDo collectionDo, AsyncCallback<CollectionItemDo> callback);
	
//	void getAssignmentCollectionsById(String assignmentId, AsyncCallback<List<CollectionItemDo>> callback);
	
//	void removeCollectionFromAssignment(String collectionId, AsyncCallback<Void> callback);
	
//	void getAssignemtsByClasspageId(String classpageId, String pageSize, String pageNum, AsyncCallback<AssignmentsListDo> callback);
	
//	void updateClassPageByid(String classpageId,String CollectionType,String title,AsyncCallback<CollectionDo> callback);
	
	void deleteClasspage(String classpageId, AsyncCallback<Void> callback);
	
	/*void createAssignment(CollectionDo collectionDo, String gooruContentId,  String dueDate, AsyncCallback<CollectionDo> callback);*/
	
//	Version 2 APIs
	void v2CreateClasspage(CollectionDo collectionDo, AsyncCallback<CollectionDo> callback);
	
	void v2UpdateClassPageByid(String classpageId, String CollectionType, String title, String shareType, AsyncCallback<CollectionDo> callback);
	
	void v2GetAssignemtsByClasspageId(String classpageId, String pageSize, String pageNum, AsyncCallback<AssignmentsListDo> callback);
	
	void v2GetAllClasspages(String limit, String offSet, AsyncCallback<ClasspageListDo> callback);
	
	void v2GetUserClasses(String limit, String offSet,String randomId, AsyncCallback<ClasspageListDo> callback);
	
	void v2GetUserStudyClasses(String limit, String offSet,String randomId, AsyncCallback<ClasspageListDo> callback);

	void v2CreateAssignment(AssignmentDo assignmentDo,AsyncCallback<AssignmentDo> callback);
	
	void v2GetClasspageById(String classpageId, AsyncCallback<CollectionDo> callback);
	
	void getSCollIdClasspageById(String classpageId, AsyncCallback<CollectionDo> callback);
	
	void v2getClasspageByCode(String classpageCode, AsyncCallback<CollectionDo> callback);
	
	void v2UpdateAssignment(AssignmentDo assignmentDo,  String assignmentId, AsyncCallback<TaskDo> callback);
	
	void v2DeleteAssignment(String assignmentId, AsyncCallback<Void> callback);
	
	void v2AddCollectionToAssignment(String assignmentId, TaskResourceAssocDo taskResourceAssocDo, AsyncCallback<TaskResourceAssocDo> callback);
	
	void v2GetAssignmentCollectionsById(String assignmentId, AsyncCallback<List<ResourceDo>> callback);
	
	void v2RemoveCollectionFromAssignment(String collectionId, String assignmentId, AsyncCallback<Void> callback);

	void socialShareEmail(String fromTxt, String toTxt,String cfm, String subtxt,String msgTxt, AsyncCallback<Void> callback);
	
	void ShotenUrl(String url, AsyncCallback<List<String>> callback);
	
	/*void checkPermissionsForClasspage(String classpageId, AsyncCallback<MetaDO> callback);*/
	
	void getCollectionClasspageAssoc(String collectionId, AsyncCallback<List<ClassPageCollectionDo>> callback);
	
	void deleteCollectionAssocInAssignment(String collectionId, AsyncCallback<Void> callback);
	
	public void getMyClassPages(String limit, String offset, AsyncCallback<ArrayList<ClasspageDo>> callback);
	
	public void createClassPage(String classPageTitle, AsyncCallback<CollectionDo> callback);
	
	public void getClasspage(String classpageId, AsyncCallback<ClasspageDo> callback);
	
	public void createClassPageItem(String classpageId,String collectionId,String dueDate,String direction, AsyncCallback<ClasspageItemDo> callback);
	
	public void assignItemToClass(String classpageId,String itemId,String dueDate,String direction,AsyncCallback<ArrayList<ClasspageItemDo>> callback);
	
	public void getClassPageItems(String classpageId,String offset,String limit,String sortingOrder,String studyStatus, AsyncCallback<ArrayList<ClasspageItemDo>> callback);
	
	public void updateClasspageItem(String classpageItemId,String direction,String dueDate,String readStatus, AsyncCallback<String> callback);
	
	public void deleteClassPageItem(String collectionId, AsyncCallback<String> callback);
	
	public void getCollectionUsedCount(String collectionId, AsyncCallback<Integer> callback);
	
	/**
	 * @function getClasspagesListByCollectionId 
	 * 
	 * @created_date : Feb 4, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param gooruOid
	 * @param gooruOid2
	 * @param simpleAsyncCallback
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	void getClasspagesListByCollectionId(
			String collectionId,
			String collabUId,
			AsyncCallback<ArrayList<ClassPageCollectionDo>> simpleAsyncCallback);
	public void  getCollectionParentFolders(String collectionId,AsyncCallback<ArrayList<String>> simpleAsyncCallback);

	
	public void inviteStudentToClass(String classId,List<String> lstEmailId, AsyncCallback<ArrayList<CollaboratorsDo>> simpleAsyncCallback);

	public void getAssociatedStudentListByCode(String classCode, int offSet, int pageSize, String statusType, AsyncCallback<StudentsAssociatedListDo> simpleAsyncCallback);
	
	public void removeStudentFromClass(String classCode, String type, String emailIds, AsyncCallback<Void> simpleAsyncCallback);
	
	public void studentJoinIntoClass(String classCode, String emailId, AsyncCallback<ClasspageDo> simpleAsyncCallback);
	
	public void getSuggestionByName(String emailId, AsyncCallback<List<String>> simpleAsyncCallback);
	
	public void getClassPageItem(String classItemId, AsyncCallback<ClasspageItemDo> simpleAsyncCallback);
	
	public void v2GetClassPartyCustomField(String gooruUid, AsyncCallback<ProfilePageDo> simpleAsyncCallback);
	
	public void getActiveAssociatedStudentListByCode(String classCode, int offSet, int pageSize, String statusType, AsyncCallback<StudentsAssociatedListDo> simpleAsyncCallback);
	
	public void v2GetAllClass(String limit, String offSet, AsyncCallback<ClasspageListDo> callback);
	
	public void v2ChangeAssignmentSequence(String classpageId, String classpageAssignmentId, int sequence, AsyncCallback<Void> callback);
	
	
	
	/** new class **/
	
	public void v3GetClassByCode(String classCode, AsyncCallback<ClasspageDo> callback);

	public void v3GetClassById(String classpageId, AsyncCallback<ClasspageDo> callback);
	
	public void createClass(String title,String grade,boolean visiblity,AsyncCallback<ClasspageDo> callback);
	
	public void  v3GetUserClasses(String limit, String offSet,boolean isContainsCourse, AsyncCallback<ClasspageListDo> callback) throws GwtException, ServerDownException;
	
	public void  v3GetUserStudyClasses(String limit, String offSet,AsyncCallback<ClasspageListDo> callback) throws GwtException, ServerDownException;
	
	public void  getActiveAssociatedStudentInClassListByCode(String classCode, int offSet, int pageSize, String statusType,AsyncCallback<StudentsAssociatedListDo> callback) throws GwtException,ServerDownException;
	
	public void getAssociatedPendingStudentListByCode(String classCode,  int offSet, int pageSize, String statusType,AsyncCallback<StudentsAssociatedListDo> callback) throws GwtException,ServerDownException;
	
	public void removePendingStudentFromClass(String classCode, boolean type, String emailIds,AsyncCallback<Void> simpleAsyncCallback);
	
	public void removeActiveStudentFromClass(String classUid,boolean visiblity,String gooruUids,AsyncCallback<Void> simpleAsyncCallback) throws GwtException,ServerDownException;
	
	public void v3StudentJoinIntoClass(String classCode,AsyncCallback<Boolean> callback) throws GwtException,ServerDownException;
	
	public void v3UpdateClass(String classId,String title,String grade,String fileName,String sharing,String score,String courseId,AsyncCallback<ClasspageDo> callback) throws GwtException,ServerDownException;
	
	void getClassesAssociatedWithCourse(String courseId,AsyncCallback<ArrayList<ClasspageDo>> callback) throws GwtException,ServerDownException;
	
	public void getClassUnitList(String classId,String courseId,int offset, int limit,AsyncCallback<List<FolderDo>> callback) throws GwtException,ServerDownException;
	
	public void getClassLessonCollectionList(String classId,String courseId,String unitId,int offset, int limit,AsyncCallback<List<ClassLessonDo>> callback) throws GwtException,ServerDownException;  
	
	public void updateClassLessonVisiblity(String classId,String courseId,String unitId,List<ClassLessonDo> listClassLessonDo,AsyncCallback<ClassLessonDo> callback) throws GwtException,ServerDownException;
	
	public void v3GetAllClass(String limit, String offSet, AsyncCallback<ClasspageListDo> callback);
	
	public void v3GetAllClass(AsyncCallback<Boolean> callback);
	
	public void V3DeleteClass(String classpageId,AsyncCallback<Integer> callback);
	
	/** Student Reports **/
	public void getStudentPlanProgressData(String classpageId, String courseId, String unitId, String lessonId, String type, Map<String,String> queryParams, AsyncCallback<ArrayList<PlanProgressDo>> simpleAsyncCallback) throws GwtException,ServerDownException;
	
	/** Mastery Reports **/
	public void getCourseMasteryReport(String classpageId, String courseId, AsyncCallback<ArrayList<PlanProgressDo>> simpleAsyncCallback) throws GwtException,ServerDownException;
	
	public void getUnitMasteryReport(String classpageId, String courseId, String unitId, String collectionType, AsyncCallback<ArrayList<PlanProgressDo>> simpleAsyncCallback) throws GwtException,ServerDownException;
	
	public void getCollectionMasteryReport(String classpageId, String courseId, String unitId, String lessonId, String assessmentId, String collectionType, AsyncCallback<ArrayList<MasterReportDo>> simpleAsyncCallback) throws GwtException,ServerDownException;

	public void getClasspageCollections(String classpageId, String courseId, String unitId, String lessonId, String collectionType, AsyncCallback<PlanContentDo> simpleAsyncCallback) throws GwtException,ServerDownException;
	
	public void getContentPlayAllSessions(String gooruUid, String classGooruId, String lessonGooruId, String unitGooruId, String courseGooruId, String assessmentId, AsyncCallback<List<UserPlayedSessionDo>> callback) throws GwtException,ServerDownException;
	
	public void getClassUsageDataSignal(String classpageId, String courseId, AsyncCallback<Boolean> callback) throws GwtException, ServerDownException;
	
	public void getXlsxReport(String tableData, String fileName, AsyncCallback<String> callback) throws GwtException, ServerDownException;

}

