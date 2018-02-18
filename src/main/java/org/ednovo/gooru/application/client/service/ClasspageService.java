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

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
//import org.ednovo.gooru.shared.model.content.ResourceItemDo;

@RemoteServiceRelativePath("gwt-service/classpageService")
public interface ClasspageService extends BaseService {
	
	/**
	 * Create new Classpage
	 * @param collectionDo instance of {@link CollectionDo} has collection meta info
	 * @return serialized created {@link CollectionDo}
	 * @throws GwtException
	 */
	public CollectionDo createClasspage(CollectionDo collectionDo)  throws GwtException, ServerDownException;
	
	/** new class v3 apis started
	 */
	public ClasspageDo createClass(String titlinviteStudentToClasse,String grades,boolean visiblity) throws GwtException,ServerDownException;

	public ClasspageDo v3GetClassByCode(String classCode) throws GwtException,ServerDownException;
	
	public ClasspageDo v3GetClassById(String classpageId) throws GwtException,ServerDownException;
	
	public ClasspageListDo  v3GetUserClasses(String limit, String offSet, boolean isContainsCourse) throws GwtException, ServerDownException;
	
	public ClasspageListDo  v3GetUserStudyClasses(String limit, String offSet) throws GwtException, ServerDownException;
	
	public StudentsAssociatedListDo getAssociatedPendingStudentListByCode(String classCode,  int offSet, int pageSize, String statusType)	throws GwtException, ServerDownException;
	
	public StudentsAssociatedListDo getActiveAssociatedStudentInClassListByCode(String classCode, int offSet, int pageSize, String statusType) throws GwtException, ServerDownException;
	
	public void removePendingStudentFromClass(String classCode, boolean type, String emailIds) throws GwtException, ServerDownException;
	
	public void removeActiveStudentFromClass(String classId,boolean visiblity,String gooruUid) throws GwtException,ServerDownException;
	
	public Boolean v3StudentJoinIntoClass(String classCode) throws GwtException, ServerDownException;
	
	public ClasspageDo v3UpdateClass(String classId,String title,String grade,String fileName,String sharing,String score,String courseId) throws GwtException,ServerDownException;
	
	public List<FolderDo> getClassUnitList(String classId,String courseIdn,int offset, int limit) throws GwtException,ServerDownException;
	
	public List<ClassLessonDo> getClassLessonCollectionList(String classId,String courseId,String unitId,int offset, int limit) throws GwtException,ServerDownException;
	
	public ClassLessonDo updateClassLessonVisiblity(String classId,String courseId,String unitId,List<ClassLessonDo> listClassLessonDo) throws GwtException,ServerDownException;
	
	ClasspageListDo v3GetAllClass(String limit, String offSet) throws GwtException, ServerDownException;
	
	public Boolean v3GetAllClass() throws GwtException, ServerDownException;
	
	public Integer V3DeleteClass(String classId) throws GwtException, ServerDownException;
	
	
	/**
	 * Get Classpage by Classpage id
	 * @param String classpageID
	 * @return serialized created {@link CollectionDo}
	 * @throws GwtException
	 */
//	public CollectionDo getClasspageById(String classpageId) throws GwtException, ServerDownException;
	
	/**
	 * Get All Classpage by User
	 * @return serialized created {@link CollectionDo}
	 * @throws GwtException
	 */
//	public List<CollectionDo> getAllClasspages() throws GwtException, ServerDownException;
	
	/**
	 * Delete Classpage by Id
	 * @param String classpageId
	 * @return void
	 * @throws GwtException
	 */
	public void deleteClasspage(String classpageId)  throws GwtException, ServerDownException;
	
	/**
	 * Create new Assignment in side classpage
	 * @param collectionDo instance of {@link CollectionDo} has collection meta info
	 * @param gooruContentId
	 * @param dueDate
	 * @return void
	 * @throws GwtException
	 */
	/*public CollectionDo createAssignment(CollectionDo collectionDo,String gooruContentId, String dueDate)  throws GwtException, ServerDownException;*/
	
	/**
	 * delete Assignment
	 * @param String assignmentId
	 * @return void
	 * @throws GwtException
	 */
//	public void deleteAssignment(String assignmentId)  throws GwtException, ServerDownException;
	
	/**
	 * Edit Assignment in side classpage
	 * @param collectionDo instance of {@link CollectionDo} has collection meta info
	 * @return void
	 * @throws GwtException
	 */
//	public CollectionDo updateAssignment(CollectionDo collectionDo, String dueDate)  throws GwtException, ServerDownException;
	
	/**
	 * Get Classpage by Classpage code
	 * @param String classpageCode
	 * @return serialized created {@link CollectionDo}
	 * @throws GwtException
	 */
//	public CollectionDo getClasspageByCode(String classpageCode) throws GwtException, ServerDownException;
	/**
	 * Adding collection to assignment.
	 * @param String collectionID and assignmentId
	 * @return void
	 * @throws GwtException
	 */
//	public CollectionItemDo addCollectionToAssignment(String collectionId, String assignmentId, CollectionDo collectionDo)  throws GwtException, ServerDownException;

//	public List<CollectionItemDo> getAssignmentCollectionsById(String assignmentId) throws GwtException, ServerDownException;

	/**
	 * Delete/remove collection from assignment.
	 * @param String collectionID and assignmentId
	 * @return void
	 * @throws GwtException
	 */
//	public void removeCollectionFromAssignment(String collectionId)  throws GwtException, ServerDownException;
	
	
	/**
	 * To retire all Assignments by Classpage ID
	 * @param String assignmentId
	 * @return List<CollectionItemDo>
	 * @throws GwtException
	 */
//	public AssignmentsListDo getAssignemtsByClasspageId(String classpageId,String pageSize, String pageNum) throws GwtException, ServerDownException;
	
	/**
	 * To update classpage by Classpage ID
	 * @param String assignmentId
	 * @return CollectionDo
	 * @throws GwtException
	 */
//	public CollectionDo updateClassPageByid(String classpageId,String CollectionType, String title)throws GwtException, ServerDownException;

	/**
	 * Create new Classpage migrating to Version 2 api
	 * @param collectionDo instance of {@link CollectionDo} has collection meta info
	 * @return serialized created {@link CollectionDo}
	 * @throws GwtException
	 */
	public CollectionDo v2CreateClasspage(CollectionDo collectionDo) throws GwtException, ServerDownException;

	/**
	 * To update classpage by Classpage ID using Version 2 APIs
	 * @param String assignmentId
	 * @return CollectionDo
	 * @throws GwtException
	 */
	public CollectionDo v2UpdateClassPageByid(String classpageId, String CollectionType, String title, String shareType) throws GwtException, ServerDownException;

	public AssignmentsListDo v2GetAssignemtsByClasspageId(String classpageId, String pageSize, String pageNum) throws GwtException, ServerDownException;

	public ClasspageListDo v2GetAllClasspages(String limit, String offSet) throws GwtException, ServerDownException;
	
	public ClasspageListDo  v2GetUserClasses(String limit, String offSet, String randomId) throws GwtException, ServerDownException;
	
	public ClasspageListDo  v2GetUserStudyClasses(String limit, String offSet, String randomId) throws GwtException, ServerDownException;
	
	//public ArrayList<CollaboratorsDo> inviteStudentToClass_V3(String classId,List<String> lstEmailId) throws GwtException, ServerDownException;
	
	public AssignmentDo v2CreateAssignment(AssignmentDo assignmentDo) throws GwtException, ServerDownException;

	/**
	 * Get Classpage by Classpage id using V2 APIs
	 * @param String classpageID
	 * @return serialized created {@link CollectionDo}
	 * @throws GwtException
	 */
	public CollectionDo v2GetClasspageById(String classpageId) throws GwtException, ServerDownException;
	
	/**
	 * Get Classpage by Classpage id using V2 APIs
	 * @param String classpageID
	 * @return serialized created {@link CollectionDo}
	 * @throws GwtException
	 */
	public CollectionDo getSCollIdClasspageById(String classpageId) throws GwtException, ServerDownException;

	public void socialShareEmail(String fromTxt, String toTxt, String cfm,String subtxt,String msgTxt) throws GwtException, ServerDownException;
	
	
	/**
	 * Edit Assignment in side classpage with V2 apis
	 * @param AssignmentDo instance of {@link CollectionDo}
	 * @return void
	 * @throws GwtException
	 */
	public TaskDo v2UpdateAssignment(AssignmentDo assignmentDo, String assignmentId)  throws GwtException, ServerDownException;

	
	/**
	 * delete Assignment for v2 apis
	 * @param String assignmentId
	 * @return void
	 * @throws GwtException
	 */
	public void v2DeleteAssignment(String assignmentId)  throws GwtException, ServerDownException;
	
	/**
	 * add collection to Assignment for v2 apis
	 * @param String assignmentId, resourceID (CollectionID)
	 * @return TaskResourceAssocDo
	 * @throws GwtException
	 */
	public TaskResourceAssocDo v2AddCollectionToAssignment(String assignmentId, TaskResourceAssocDo taskResourceAssocDo)  throws GwtException, ServerDownException;
	
	/**
	 * get collection to Assignment for v2 apis
	 * @param String assignmentId, resourceID (CollectionID)
	 * @return List<ResourceDo>
	 * @throws GwtException
	 */
	public List<ResourceDo> v2GetAssignmentCollectionsById(String assignmentId)  throws GwtException, ServerDownException;

	/**
	 * Delete/remove collection from assignment using V2 Apis
	 * @param String collectionID and assignmentId
	 * @return void
	 * @throws GwtException
	 */
	public void v2RemoveCollectionFromAssignment(String collectionId, String assignmentId)  throws GwtException, ServerDownException;
	
	/**
	 * Get Classpage by Classpage Code using V2 APIs
	 * @param String classpageCode
	 * @return serialized created {@link CollectionDo}
	 * @throws GwtException
	 */
	
	public CollectionDo v2getClasspageByCode(String classpageCode)throws GwtException, ServerDownException;
	
	/**
	 * To Generate Bityly link for any URL
	 * @param url (original url)
	 * @return BitlyUrlDo
	 * @throws GwtException
	 */
	public List<String> ShotenUrl(String url) throws GwtException, ServerDownException;
	
	/**
	 * 
	 * @function checkPermissionsForClasspage 
	 * 
	 * @created_date : 13-09-2013
	 * 
	 * @description
	 * 	this method is used to check whether the User has Edit permissions or not.
	 * 
	 * @parm(s) : @param classpageId
	 * @parm(s) : @return
	 * @parm(s) : @throws GwtException
	 * 
	 * @return : PermissionsDO
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	/*public MetaDO checkPermissionsForClasspage(String classpageId) throws GwtException, ServerDownException;*/

	/**
	 * To get know which classpages having this collection. 
	 * @param collectionId 
	 * @return ResourceDo
	 * @throws GwtException
	 */
	List<ClassPageCollectionDo> getCollectionClasspageAssoc(String collectionId) throws GwtException, ServerDownException;
	
	/**
	 * delete Collection from Classpage/Assignment
	 * @param String collectionId
	 * @return void
	 * @throws GwtException
	 */
	public void deleteCollectionAssocInAssignment(String collectionId)  throws GwtException, ServerDownException;
	
	
	
	public ArrayList<ClasspageDo> getMyClassPages(String limit, String offset) throws GwtException, ServerDownException;
	public CollectionDo createClassPage(String classPageTitle) throws GwtException, ServerDownException;
	public ClasspageDo getClasspage(String classpageId)  throws GwtException, ServerDownException;
	public ClasspageItemDo createClassPageItem(String classpageId,String collectionId,String dueDate,String direction)  throws GwtException, ServerDownException;
	public ArrayList<ClasspageItemDo> assignItemToClass(String classpageId,String itemId,String dueDate,String direction)  throws GwtException, ServerDownException;
	public ArrayList<ClasspageItemDo> getClassPageItems(String classpageId,String offset,String limit,String sortingOrder,String studyStatus) throws GwtException, ServerDownException;
	public String updateClasspageItem(String classpageItemId,String direction,String dueDate,String readStatus) throws GwtException, ServerDownException;
	public String deleteClassPageItem(String collectionId) throws GwtException, ServerDownException;
	
	/**
	 * 
	 * @function getClasspagesListByCollectionId 
	 * 
	 * @created_date : Feb 4, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param collectionId
	 * @param collabUId
	 * @return
	 * 
	 * @return : ArrayList<ResourceDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public ArrayList<ClassPageCollectionDo> getClasspagesListByCollectionId(String collectionId,
			String collabUId) throws GwtException, ServerDownException;
	
	
	/**
	 * @function getCollectionUsedCount 
	 * 
	 * @created_date : Feb 14, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param collectionId
	 * @return
	 * 
	 * @return : Integer
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	Integer getCollectionUsedCount(String collectionId) throws GwtException, ServerDownException;
	
	public ArrayList<String> getCollectionParentFolders(String collectionId) throws GwtException, ServerDownException;
	
	/**
	 * 
	 * @function inviteStudentToClass 
	 * 
	 * @created_date : Mar 17, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param classId
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
	public ArrayList<CollaboratorsDo> inviteStudentToClass(String classId,List<String> lstEmailId) throws GwtException, ServerDownException;
	
	/**
	 * 
	 * @function getAssociatedStudentListByCode 
	 * 
	 * @created_date : Mar 17, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param classCode
	 * @param type
	 * @return
	 * @throws GwtException
	 * 
	 * @return : Map<String,ArrayList<CollaboratorsDo>>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public StudentsAssociatedListDo getAssociatedStudentListByCode(String classCode,  int offSet, int pageSize, String statusType)
			throws GwtException, ServerDownException;

	public void removeStudentFromClass(
			String classCode, String type, String emailIds)
			throws GwtException, ServerDownException;
	/**
	 * @function studentJoinIntoClass 
	 * 
	 * @created_date : Mar 19, 2014
	 * @param classCode
	 * @param emailId
	 * @return ClasspageDo
	 * @throws GwtException
	 */
	public ClasspageDo studentJoinIntoClass(String classCode, String emailId) throws GwtException, ServerDownException;

	/**
	 * @function getSuggestionByName 
	 * 
	 * @created_date : Mar 20, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param emailId
	 * @return
	 * 
	 * @return : List<String>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	List<String> getSuggestionByName(String emailId) throws GwtException, ServerDownException;
	
	public ClasspageItemDo getClassPageItem(String classItemId) throws GwtException, ServerDownException;

	ProfilePageDo v2GetClassPartyCustomField(String gooruUid)
			throws GwtException, ServerDownException;

	public StudentsAssociatedListDo getActiveAssociatedStudentListByCode(String classCode, int offSet, int pageSize, String statusType) throws GwtException, ServerDownException;

	/**
	 * @function v2GetAllClass 
	 * 
	 * @created_date : May 20, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param limit
	 * @param offSet
	 * @return
	 * @throws GwtException
	 * 
	 * @return : ClasspageListDo
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	ClasspageListDo v2GetAllClass(String limit, String offSet)
			throws GwtException, ServerDownException;

	/**
	 * @function v2ChangeAssignmentSequence 
	 * 
	 * @created_date : Jun 11, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param classpageId
	 * @param classpageAssignmentId
	 * @param sequence
	 * @throws GwtException
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	public void v2ChangeAssignmentSequence(String classpageId,
			String classpageAssignmentId, int sequence) throws GwtException, ServerDownException;
	
	ArrayList<ClasspageDo> getClassesAssociatedWithCourse(String courseId) throws GwtException, ServerDownException;
	
	/** Student Plan and Progress **/
	public ArrayList<PlanProgressDo> getStudentPlanProgressData(String classpageId, String courseId, String unitId, String lessonId, String type, Map<String,String> queryParams) throws GwtException, ServerDownException;

	/** Mastery Reports **/
	public ArrayList<PlanProgressDo> getCourseMasteryReport(String classpageId, String courseId) throws GwtException, ServerDownException;
	
	public ArrayList<PlanProgressDo> getUnitMasteryReport(String classpageId, String courseId, String unitId, String collectionType) throws GwtException, ServerDownException;

	public ArrayList<MasterReportDo> getCollectionMasteryReport(String classpageId, String courseId, String unitId, String lessonId, String assessmentId, String collectionType) throws GwtException, ServerDownException;

	public PlanContentDo getClasspageCollections(String classpageId, String courseId, String unitId, String lessonId, String collectionType) throws GwtException, ServerDownException;

	public List<UserPlayedSessionDo> getContentPlayAllSessions(String gooruUid, String classGooruId, String lessonGooruId, String unitGooruId, String courseGooruId, String assessmentId)throws GwtException, ServerDownException;

	public boolean getClassUsageDataSignal(String classpageId, String courseId) throws GwtException, ServerDownException;
	
	public String getXlsxReport(String tableData, String fileName) throws GwtException, ServerDownException;

}
