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

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.ShelfTreeWidget;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;

import com.google.gwt.user.client.ui.TreeItem;

/**
 * @author Search Team
 *
 */
public interface CourseInfoUiHandlers extends BaseUiHandlers {
	/**
	 * This method will call the taxonomy service method
	 */
	public void callTaxonomyService(int classifierId);
	
	public void callCourseBasedOnSubject(int subjectId,final int selectedText);
    
	/**
	 * To Create Course
	 * @param courseTitle {@link String} 
	 * @param isCreateUnit {@link boolean} 
	 * @param currentShelfTreeWidget 
	 */
	public void createAndSaveCourseDetails(CreateDo createDo,boolean isCreateUnit,FolderDo folderDo, TreeItem currentShelfTreeWidget);
	/**
	 * To update the course details
	 * @param currentShelfTreeWidget 
	 * @param courseTitle {@link String} 
	 */
	public void updateCourseDetails(CreateDo createDo, String id,boolean isCreateUnit,FolderDo folderDo, TreeItem currentShelfTreeWidget);
	
	/**
	 * This method is used for profanity checker
	 * @param textValue
	 * @param createOrUpDate 
	 * @param treeSelectedItem 
	 * @param index 
	 */
	public void checkProfanity(String textValue,boolean isCreate, CreateDo createOrUpDate, TreeItem treeSelectedItem);

	public TreeItem getSelectedWidget();  
}
