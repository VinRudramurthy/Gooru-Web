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
package org.ednovo.gooru.client.mvp.gshelf.unitdetails;

import java.util.List;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.ShelfTreeWidget;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.uc.UlPanel;

import com.google.gwt.user.client.ui.TreeItem;

/**
 * @author Search Team
 *
 */
public interface UnitInfoUiHandlers extends BaseUiHandlers {
	
	public void callCourseBasedOnSubject(int subjectId,final int selectedId);
    
	/**
	 * To Create and Save the details of Course
	 * @param createDo {@link String} 
	 * @param isCreateLesson {@link boolean} 
	 * @param currentShelfTreeWidget 
	 */
	public void createAndSaveUnitDetails(CreateDo createDo,boolean isCreateLesson,FolderDo courseObj,String courseId, TreeItem currentShelfTreeWidget);
	/**
	 * This method is used to update the unit info details
	 * @param createDo
	 * @param id
	 * @param isCreateUnit
	 * @param currentShelfTreeWidget 
	 */
	void updateUnitDetails(final CreateDo createDo, final String id,final boolean isCreateUnit,FolderDo courseObj, TreeItem currentShelfTreeWidget);
	/**
	 * This method is used to check profanity checker
	 * @param textValue
	 * @param isCreate
	 * @param createOrUpDate 
	 * @param currentShelfTreeWidget 
	 */
	void checkProfanity(String textValue,final boolean isCreate,int index,String courseId, CreateDo createOrUpDate, TreeItem currentShelfTreeWidget);

	void showUnitInfo();

	void showUnitTemplate();

//	public void invokeTaxonomyPopup(String type, UlPanel ulSelectedItems); 
	
	MyCollectionsRightClusterPresenter getMyCollectionsRightClusterPresenter();
	
	public void getDomainsBasedOnCourseId(int courseId,int selectedId, CourseSubjectDo libraryCodeDo);
	
	public void callCourseInfoTaxonomy();

	public void invokeTaxonomyPopup(String unit,List<LiPanelWithClose> unitLiPanelWithCloseArray);

	void getPaginatedDomainsBasedOnCourseId(int courseId, int selectedId,
			int offSetVal);

	public TreeItem getSelectedWidget(); 
}
