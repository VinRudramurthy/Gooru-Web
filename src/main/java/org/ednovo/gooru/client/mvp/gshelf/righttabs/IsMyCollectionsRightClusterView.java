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

import java.util.ArrayList;
import java.util.HashMap;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;

import com.google.gwt.user.client.ui.Anchor;

/**
 * @author Search Team
 *
 */
public interface IsMyCollectionsRightClusterView extends IsViewWithHandlers<MyCollectionsRightClusterUiHandlers> {
	/**
	 * This method is used to set folder data
	 * @param folderObj
	 * @param selectedWidgetsTitleType 
	 */
	public void setBreadCrumbSlot(FolderDo folderObj, String type, HashMap<String, String> selectedWidgetsTitleType);
	/**
	 * This method is used to clear tab active status
	 */
	public void resetHilightStyles();
	/**
	 * This method is used to set Default or based on index tab selection
	 * @param tabIndex 
	 */
	public void setDefaultActiveTab(int tabIndex);
	public void setCurrentTypeView(String type);
	public void enableAndHideTabs(boolean isVisible);
	
	/**
	 * This method is used to set the content on delete of course.
	 * @param o1CourseId 
	 */
	public void onDeleteCourseSuccess(String o1CourseId);
	
	public void onDeleteUnitSuccess(String o1CourseId, String o2UnitId);
	
	public void onDeleteLessonSuccess(String o1CourseId, String o2UnitId,String o3LessonId);
	
	public void onDeleteCollectionAssessmentSuccess(String o1CourseId,String o2UnitId, String o3LessonId, String assessmentCollectionId);
	
	public void setOnDeleteBreadCrumbs(String title, String type);
	
	public void invokeContentDeletePopup(String o1CourseId, String o2UnitId,String o3LessonId, String collAssessmentId,ArrayList<ClasspageDo> result);
	
	public void disableAndEnableBreadCums(boolean isVisible);
	
	public void setFolderInfoWidget(FolderDo folderObj, MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter);
	
	public void disableCollabaratorOptions(boolean isHide);
	
	public void setIsCollaboratorValue(boolean isHide);
	
	public void disableButtons(boolean isTrue);
	
	public void isCourseDeleteStatus(Boolean status, String type, String o1CourseId, String o2UnitId, String o3LessonId, String assessmentCollectionId);
}
