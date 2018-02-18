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
package org.ednovo.gooru.client.mvp.search;

/**
* @fileName : IsAddResourceView.java 
*
* @description :This file is responsible for UI Handlers.
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

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public interface IsAddResourceContainerView extends IsViewWithHandlers<AddResourceContainerUiHandlers>{
	public void displayWorkspaceData(FolderListDo folderListDo,boolean clearShelfPanel,String searchType);
	public void clearShelfData();
	public void setFolderItems(TreeItem item,FolderListDo folderListDo);
	public void displayNoCollectionsMsg();
	public void setSearchResultDo(ResourceSearchResultDo searchResultDo);
	public Anchor getAddButton(); 
	public Tree getfolderTreePanel(); 
	public void setPlayerStyle(boolean isPlayer);
	public void removePlayerStyle(boolean isPlayer);
	public void getButtonVisiblity();
	public void clearSelectedId();
	void restrictionToAddResourcesData(String message);
	public void enableSuccessView(String title,String gooruOid,HashMap<String,String> params);
	public Button getCancelButton();
	public void clearSelectedFolderId();
	public TreeItem loadingTreeItem();
	public void setCollectionSearchResultDo(
			ResourceSearchResultDo searchResultDo);
	public void showAndHideMyCollections();
	public void hideNoCollectionsMsg();

}
