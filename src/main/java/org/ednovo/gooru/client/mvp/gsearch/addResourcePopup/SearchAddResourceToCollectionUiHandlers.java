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
package org.ednovo.gooru.client.mvp.gsearch.addResourcePopup;
/**
* @fileName : AddResourceUiHandlers.java 
*
* @description :This file is used to handle Ui Click events.
*
* @version :5.1
*
* @date: Apr 6 2013
   	
* @Author  Gooru Team
* 
* @Reviewer 
*
*/
import java.util.HashMap;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.search.CollectionSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.client.mvp.search.util.CollectionResourceWidget;
import org.ednovo.gooru.client.mvp.search.util.CollectionSearchWidget;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TreeItem;

public interface SearchAddResourceToCollectionUiHandlers extends BaseUiHandlers, EventHandler{
	/**
	 * This method is used to get sub folder tree items
	 * @param item
	 * @param parentId
	 */
	public void getFolderItems(final TreeItem item,String parentId);
	public void getWorkspaceData(int offset,int limit,boolean clearShelfPanel,String searchType);
	public void addResourceToCollection(final String selectedFolderOrCollectionid,String searchType,final String title,HashMap<String, String> urlparams,boolean isFromMyCourse);
	void getUserShelfData(ResourceSearchResultDo searchResultDo,String searchType,CollectionResourceWidget collectionResourceWidget);
	public Button getAddButton();
	public void hidePopup();
	void getUserShelfCollectionsData(CollectionSearchResultDo collectionsearchResultDo, String searchType,CollectionSearchWidget collectionSearchWidget);
	void addCollectionToFolder(String selectedFolderOrCollectionid,String searchType, String title, int folerLevel,HashMap<String, String> urlparams);
	void addCollectionToMyCollections(String object, String currentsearchType);
	void getUserShelfCollectionsData(String collectionId, String searchType,String collectionTitle);
	public boolean validateIsAssessments(String getcollectionType); 
	public void getCourseItems(final TreeItem item,String courseId, String UnitId,String lessionId,String typeValue);
	public void CopyToplevelMyCollections(String gooruOid, String folderId,String searchType,String collectionTitle,HashMap<String, String> urlparams);
	public void copyCollectionToLession(String collectionId, String collectionTitle,HashMap<String, String> urlparams);
	public void moveCollectionTOLesson(String collectionId, String collectionTitle,HashMap<String, String> urlparams);
	public void moveCollectionToMyCOllections(String gooruOid, String folderId,String searchType,String collectionTitle,HashMap<String, String> urlparams);
	public void enableAddButton();
	public void getLoadingImage();
	public void copyLessonToUnit(HashMap<String, String> urlparams, String lessonId);
	public void copyUnitToCourse(HashMap<String, String> urlparams, String unitId);
}
