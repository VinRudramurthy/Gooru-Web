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

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * @author Search Team
 *
 */
public interface IsShelfMainView extends IsViewWithHandlers<ShelfMainUiHandlers> {

	void getChildFolderItems(TreeItem currentTreeItem, List<FolderDo> folderListDo);

	void setChildPageNumber(Integer pageNumber);

	int getChildPageNumber();

	void setUserMetaData(List<FolderDo> searchResult, boolean clrPanel);
	
	HTMLPanel getSlot();

	void setDefaultOrganizePanel(String view);
	/**
	 * This method is used to enable the panels based on the user status.
	 * @param isAnonymous
	 */
	void setNoDataForAnonymousUser(boolean isAnonymous);
	/**
	 * This method is used to execute scroll functionality
	 * @param isLeftScroll
	 */
	public void executeScroll(boolean isLeftScroll);
    /**
     * Update the tree widget as active style
     */
	void updateLeftShelfPanelActiveStyle();

	void createNewItem(String type, TreeItem currentShelfTreeWidget); 
    /**
     * Updating tree widget 
     * @param courseDo {@link FolderDo}
     * @param currentShelfTreeWidget 
     */
	void updateTreeWidget(FolderDo courseDo, boolean flag, TreeItem currentShelfTreeWidget); 

	void enableDisableCourseButton(boolean isEnable);
	/**
	 * This method is used to get the collection title label
	 * @return
	 */
	Label getCollectionLabel();

	void removeDeletedTreeWidget(String deletedTreeWidgetId, String currentTypeView);  
	
	void reorderShelfItems(String itemId, int toBeMovedPos, String direction, HashMap<String, String> params, FolderDo folderDo, String itemSeqNumb);

	String getViewType();

	void updateWidgetsCount(CollectionItemDo collectionItem,boolean isDelete);

	InlineLabel getImgInlineLbl();

	void setViewTitleWthIcon(String title, String type);

	HTMLPanel getTitleIconContainer();

	TreeItem getCurrentEditingWidget();

	void showLastEditCollaborater(String lastEditedBy,
			boolean hasLastModifiedUser);

	void invokeSpinner();  
}
