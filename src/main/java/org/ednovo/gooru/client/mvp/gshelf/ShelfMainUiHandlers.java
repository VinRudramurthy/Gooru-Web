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
package org.ednovo.gooru.client.mvp.gshelf;

import java.util.HashMap;
import java.util.List;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.courselist.MyCollectionsListPresenter;
import org.ednovo.gooru.client.mvp.shelf.event.GetEditPageHeightEventHandler;
import org.ednovo.gooru.client.mvp.shelf.event.UpdateResourceCountEventHandler;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.TreeItem;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

/**
 * @author Search Team
 *
 */
public interface ShelfMainUiHandlers extends BaseUiHandlers,GetEditPageHeightEventHandler,UpdateResourceCountEventHandler,LoadMyContentEventHandler{

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SHELF_TAB = new Type<RevealContentHandler<?>>();

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_COLLECTION_RESOURCE_TAB = new Type<RevealContentHandler<?>>();

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_COLLECTION_INFO_TAB = new Type<RevealContentHandler<?>>();

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_ASSIGN_INFO_TAB = new Type<RevealContentHandler<?>>();

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_COLLABORATOR_TAB = new Type<RevealContentHandler<?>>();

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_FOLDERS_SLOT = new Type<RevealContentHandler<?>>();
	/**
	 * To get the child folders items
	 * @param folderId {@link String}
	 * @param isDataCalled
	 * @param treeChildSelectedItem
	 */
	void getChildFolderItems(String folderId,String type ,boolean isDataCalled, TreeItem treeChildSelectedItem);
	/**
	 * This method is used to get the child units,lessons and collections
	 * @param courseId
	 * @param unitId
	 * @param lessonId
	 * @param typeVal
	 * @param isDataCalled
	 * @param treeChildSelectedItem
	 */
	void getChildFolderItemsForCourse(final String courseId,final String unitId,final String lessonId,final String typeVal,final boolean isDataCalled, TreeItem treeChildSelectedItem);

	/**
	 * This is used to display all the courses,Units, Lessons and collections list with move functionality.
	 * @param type
	 */
	void setListPresenterBasedOnType(String type);

	/**
	 * To get the user content when scrolling the page.
	 * @param pageSize
	 * @param pageNumber {@link Integer}
	 * @param clearShelfPanel
	 */
	void getMoreListItems(int pageSize, Integer pageNumber, boolean clearShelfPanel);

	/**
	 * This method is used to get list presenter
	 */
	public MyCollectionsListPresenter getMyCollectionsListPresenter();

	/**
	 * To show Folder/Course/Collection content on Right side panel when clicking on Lift side tree.
	 * @param folderObj
	 * @param clickedItemType
	 * @param folderListDoChild
	 */
	public void setRightPanelData(FolderDo folderObj,String clickedItemType, List<FolderDo> folderListDoChild);
    /**
     * To show List of user content
     * @param listOfContent
     * @param title
     */
	void setRightListData(List<FolderDo> listOfContent,FolderDo folderDo);
    /**
     * Update tree widget as active style.
     */
	void updateLeftShelfPanelActiveStyle();
	/**
	 * This method is used to set collection content presenter
	 * @param collectionDo
	 */
	void setCollectionContent(FolderDo collectionDo);

	void setBreadCrumbs(HashMap<String, String> selectedWidgetsTitleType);

	void onDeleteSetBreadCrumbs(String title, String course);

	void setVersion();

	void SetDefaultTypeAndVersion();

	void refreshUserShelfCollections();

	void updateWidgetsCount(CollectionItemDo collectionItem,boolean isDelete);
	void addNewContent(String string);
}
