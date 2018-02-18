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
package org.ednovo.gooru.client.mvp.gshelf.righttabs;

import java.util.Map;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;

/**
 * @author Search Team
 *
 */
public interface MyCollectionsRightClusterUiHandlers extends BaseUiHandlers{
	public void setTabItems(int index,String type,FolderDo folderObj);
	/**
	 * This method is used to set Default Active Tab
	 */
	public void setDefaultActiveTab();
	
//	void setUnitTemplate(String type);
	
	public void setRightClusterContent(String o1CourseId, String currentTypeView);
	
	public void deleteCourseContent(String o1CourseId);
	
	public void getUserShelfData(String collectionId,String valuetype);
	
	public void deleteUnitContent(String o1CourseId, String o2UnitId);
	
	public void deleteLessonContent(String o1CourseId, String o2UnitId,String o3LessonId);
	
	public void setUnitsListOnRightCluster(String o1CourseId,String o2DeletedUnitId, String currentTypeView);
	
	public void setLessonsListOnRightCluster(String o1CourseId,	String o2UnitId, String o3LessDeletedonId, String currentTypeView);
	
	public void isAssignedToClassPage(String type,String o1CourseId, String o2UnitId, String o3LessonId, String collAssessmentId); 
	
	public void deleteCollectionContent(String o1CourseId, String o2UnitId,	String o3LessonId, String assessmentCollectionId);
	
	public void setCollectionsListOnRightCluster(String o1CourseId,String o2UnitId, String o3LessonId,String deletedAssessmentCollectionId, String currentTypeView);  
	
	public void setFirstSelectedData(Map<Integer,Integer> firstSelectedData);
	
	public void deleteMyCollectionContent(String id, String folderCollection);

	public boolean checkCopyOrMoveStatus(boolean copySelected, boolean moveSelected,String clickedType);

	public void EnableMyCollectionsTreeData(String collectionId,String collectionTitle);
	
	public void DisableMyCollectionsTreeData(String collectionId,String collectionTitle);
	public void enableAddButton();
	
	public void setViewTitleWthicon(String title, String type);
	
	public void deleteMyCollectionColl(String id);
	
	public void disableCopyPopupTabs(boolean isVisible, String copyType);
	
	public void copyCourse(String gooruOid);
	
	public void isStudentDataAvailable(String type, String o1CourseId, String o2UnitId, String o3LessonId, String assessmentCollectionId);   
	
}
