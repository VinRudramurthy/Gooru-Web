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
package org.ednovo.gooru.client.mvp.gshelf.coursedetails;

import java.util.List;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.ShelfTreeWidget;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.AudienceView;

import com.google.gwt.user.client.ui.TreeItem;

/**
 * @author Search Team
 *
 */
public interface IsCourseInfoView extends IsViewWithHandlers<CourseInfoUiHandlers> {
	
	/**
	 * This method will display the Grades according to the subject
	 */
	void showCourseDetailsBasedOnSubjectd(List<CourseSubjectDo> libraryCodeDo,int selectedId);
	/**
	 * Set collection default course
	 * @param libraryCode instance {@link CourseSubjectDo} as List
	 */
	void setCourseList(List<CourseSubjectDo> libraryCode);
	/**
	 * To set the Updated course data
	 * @param courseObj
	 */
	void setCouseData(FolderDo courseObj);
	/**
	 * To get the course title 
	 * @return {@link String}
	 */
	String getCourseTitle(); 
	/**
	 * This method is used for calling create and update api
	 * @param isCreate
	 * @param result
	 * @param createOrUpDate 
	 * @param currentShelfTreeWidget 
	 */
	void callCreateAndUpdate(boolean isCreate,boolean result, CreateDo createOrUpDate, TreeItem currentShelfTreeWidget); 
	AudienceView getAudienceContainer();
	FolderDo getCourseDetails();
	void resetBtns();
	void spinnerImageVisibility(boolean isVisible);
}
