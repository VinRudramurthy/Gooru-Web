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
package org.ednovo.gooru.client.mvp.gshelf.lessondetails;

import java.util.List;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
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
public interface LessonInfoUiHandlers extends BaseUiHandlers {
	/**
	 * This method is used to create lesson
	 * @param createObj
	 * @param isCreateCollOrAssessment
	 * @param creationType
	 * @param currentShelfTreeWidget 
	 */
	public void createAndSaveLessonDetails(CreateDo createObj,final boolean isCreateCollOrAssessment,String creationType,String courseId,String unitId, TreeItem currentShelfTreeWidget);
	/**
	 * This method is used for checking profanity
	 * @param textValue
	 * @param isCreate
	 * @param type
	 * @param createOrUpDate 
	 * @param currentShelfTreeWidget 
	 */
	public void checkProfanity(String textValue,boolean isCreate,String type, CreateDo createOrUpDate,final String courseId,final String unitId, TreeItem currentShelfTreeWidget);

	/**
	 * This method is used to update lesson details
	 * @param createObj
	 * @param id
	 * @param isCreateUnit
	 * @param type
	 * @param currentShelfTreeWidget 
	 */
	public void updateLessonDetails(final CreateDo createObj, final String id,final boolean isCreateUnit,String type,FolderDo folderObj, TreeItem currentShelfTreeWidget);
	
	public MyCollectionsRightClusterPresenter getMyCollectionsRightClusterPresenter();


	void callTaxonomyService(int subdomainId);
	
//	void invokeTaxonomyPopup(String type, UlPanel ulSelectedItems);
	
	public void callCourseInfoTaxonomy();
	
	void invokeTaxonomyPopup(String type,List<LiPanelWithClose> lessonLiPanelWithCloseArray);
	
	TreeItem getSelectedWidget();
	void showStandardsPopup(String standardVal, String standardsDesc,
			List<LiPanelWithClose> collectionLiPanelWithCloseArray);

}
