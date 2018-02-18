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

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.application.shared.model.folder.FolderTocDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwt-service/folderService")
public interface FolderService extends BaseService {
	
	/**
	 * Create new FOLDER
	 * @param collectionDo instance of {@link CollectionDo} has collection meta info
	 * @return serialized created {@link CollectionDo}
	 * @throws GwtException
	 */
	public CollectionDo createFolder(CollectionDo collectionDo)  throws GwtException, ServerDownException;
	
	/**
	 * Create new FOLDER
	 * @param collectionDo instance of {@link CollectionDo} has collection meta info
	 * @return serialized created {@link CollectionDo}
	 * @throws GwtException
	 */
	public CollectionDo createFolderToParentFolder(CollectionDo collectionDo, String parentId)  throws GwtException, ServerDownException;

	/**
	 * Get All Folders by User
	 * @return serialized created {@link List<CollectionItemDo>}
	 * @throws GwtException
	 */
	public List<CollectionItemDo> getAllFolders() throws GwtException, ServerDownException;
	/**
	 * Delete new FOLDER by collectionId
	 * @param collectionId of the folder 
	 * @throws GwtException
	 
	 */

	public void deleteFolder(String collectionId)  throws GwtException, ServerDownException;

	/**
	 * Get all folders and collections inside a folder
	 * @return serialized created {@link List<CollectionDo>}
	 * @throws GwtException
	 */
//	public List<CollectionDo> getFoldersAndCollections(String folderId) throws GwtException, ServerDownException;
	
	/**
	 * Get a folder information
	 * @return serialized created {@link CollectionDo}
	 * @throws GwtException
	 */
	public CollectionDo getFolderInformation(String folderId) throws GwtException, ServerDownException;

	/**
	 * Get Folders of the second level and third level by User
	 * @return serialized created {@link List<CollectionDo>}
	 * @throws GwtException
	 */
	public List<CollectionItemDo> getFolders(String collectionId) throws GwtException, ServerDownException;
	
	//New APIs for 6.0
	/**
	 * Get Folders of the second level and third level by User
	 * @return serialized created {@link List<CollectionDo>}
	 * @throws GwtException
	 */
	public FolderListDo getChildFolders(int offset, int limit,String parentId,String sharingType, String collectionType,boolean isExcludeAssessment) throws GwtException, ServerDownException;
	/**
	 * This method is used to get the child items of the parent.
	 * @param offset
	 * @param limit
	 * @param courseId
	 * @param unitId
	 * @param lessonId
	 * @param sharingType
	 * @param collectionType
	 * @param isExcludeAssessment
	 * @return
	 * @throws GwtException
	 * @throws ServerDownException
	 */
	public FolderListDo getChildFoldersForCourse(int offset, int limit,String courseId,String unitId,String lessonId,String sharingType, String collectionType,boolean isExcludeAssessment) throws GwtException, ServerDownException;
	/**
	 * Create Folder - generic method for first, second and third levels.
	 * @return serialized created {@link FolderDo}
	 * @throws GwtException
	 */
	public FolderDo createFolder(String folderName, String parentId, boolean addToShelf) throws GwtException, ServerDownException;
	
	/**
	 * Create Course - generic method for first, second and third levels.
	 * @return serialized created {@link FolderDo}
	 * @throws GwtException
	 */
	public FolderDo createCourse(CreateDo createDo,boolean addToShelf,String courseId,String unitId,String lessonId) throws GwtException, ServerDownException;

	/**
	 * delete a folder from organize
	 * @return serialized created {@link void}
	 * @throws GwtException
	 */
	public void deleteCollectionsFolder(String folderId) throws GwtException, ServerDownException;
	
	/**
	 * Move a Collection into a selected target folder.
	 * @param sourceId
	 * @param targetId
	 * @throws GwtException
	 */
	public void moveCollectionIntoFolder(String sourceId, String targetId) throws GwtException, ServerDownException;
	
	
	public CollectionDo createCollectionInParent(CollectionDo data, String courseCodeId,String folderId) throws GwtException, ServerDownException;
	
	/**
	 * updates the folder title for a particular folder.
	 * @param folderId
	 * @param title
	 * @throws GwtException
	 */
	public void updateFolder(String folderId, String title, String ideas, String questions, String performance) throws GwtException, ServerDownException;
	
	/**
	 * updates the course details.
	 * @param courseId
	 * @param courseTitle
	 * @throws GwtException
	 */
	public void updateCourse(String courseId,String unitId,String lessonId,String collectionId, CreateDo createDo) throws GwtException, ServerDownException;
	
	public CollectionDo  copyDraggedCollectionIntoFolder(CollectionDo collectionDo,String courseCodeId,String parentId,boolean addToShelf) throws GwtException, ServerDownException;
	
	public FolderListDo getCollectionResources(String parentId,String sharingType, String collectionType) throws GwtException, ServerDownException;
	
	public void reorderFoldersOrCollections(String courseId,String unitId,String lessonId,String collectionId,int itemToBeMovedPosSeqNumb, String collectionItemId,String type) throws GwtException, ServerDownException;
	
	/**
	 * This method is used to get the sub folders and collections based on the folder id
	 * @param folderId {@link String}
	 * @return FolderTocDo {@link FolderTocDo}
	 * @throws GwtException
	 * @throws ServerDownException
	 */
	public FolderTocDo getTocFolders(String folderId,boolean fromPPP) throws GwtException, ServerDownException;

	/**
	 * This method is used to get Folder meta data based on the folder id
	 * @param folderId {@link String}
	 * @return FolderDo {@link FolderDo}
	 * @throws GwtException
	 * @throws ServerDownException
	 */
	FolderDo getFolderMetaData(String folderId) throws GwtException,ServerDownException;
	
	/**
	 * This method is used to get Folder route nodes based on the folder id
	 * @param folderId {@link String}
	 * @return FolderDo {@link FolderDo}
	 * @throws GwtException
	 * @throws ServerDownException
	 */
	Map<String,String> getFolderRouteNodes(String folderId) throws GwtException,ServerDownException;
	
	Integer deleteCourse(String o1CourseId) throws GwtException,ServerDownException;
	
	Integer deleteUnit(String o1CourseId, String o2UnitId) throws GwtException,ServerDownException;
	
	Integer deleteLesson(String o1CourseId, String o2UnitId, String o3LessonId)throws GwtException,ServerDownException;
	
	public Integer getClassesAssociatedWithCourse(String o1CourseId) throws GwtException,ServerDownException;
	
	public Integer deleteCollectionAssessment(String o1CourseId, String o2UnitId,String o3LessonId, String assessmentCollectionId) throws GwtException,ServerDownException;
	/**
	 * Create Collection in mycollections
	 * @return serialized created {@link FolderDo}
	 * @throws GwtException
	 */
	public FolderDo createCollection(CreateDo createDo,String parentId,boolean addToShelf) throws GwtException, ServerDownException;
	
	List<ListValuesDo> getDepthOfKnowledgesList() throws GwtException;
	
	List<ListValuesDo> getAudienceList() throws GwtException;
	
	public FolderDo getCourseDetails(String courseId, String unitId, String lessonId)  throws GwtException;

	void updateCollectionDetails(CreateDo createDoObj,String collectionId,Map<Integer, String> audience, Map<Integer, String> dok,Map<Long, String> centurySkills, String languageObjective);
    
	String copyCourse(String courseId, String unitId, String lessonId) throws GwtException;
	
	public Map<String,String> jobCheck(String result) throws GwtException;
	
	public Boolean isTiedWithStudentData(String o1CourseId) throws GwtException;

	

	
}
