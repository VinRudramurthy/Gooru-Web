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
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.search.CollectionSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;



public interface AddResourceContainerUiHandlers extends BaseUiHandlers,EventHandler{
	
	public void getWorkspaceData(int offset,int limit,boolean clearShelfPanel,String searchType);
	public void getFolderItems(TreeItem item,String parentId);
	public void getUserShelfData(ResourceSearchResultDo searchResultDo,String searchType);
	public void getUserShelfCollectionsData(CollectionSearchResultDo searchResultDo,String SearchType);
	public void addResourceToCollection(String gooruOid,String searchType,String title);
	public void createFolderInParent(String folderName, String parentId,
			HashMap<String, String> params);
	public Anchor getAddButton(); 
	public Tree getfolderTreePanel() ;
	public void setCollectionItemData(String collectionId,CollectionItemDo collectionItemDo);
	public void setplayerStyle();
	public void removePlayerStyle();
	public Button getCancelButton();
	public TreeItem loadingTreeItem();
	public void clearData();
	public void addCollectionToMyCollections(String object,
			String currentsearchType);
	public void addCollectionToFolder(String selectedFolderGooruOid,
			String currentsearchType, String text, int folerLevel,HashMap<String, String> urlparams);
	public boolean validateIsAssessments(String collectionType);
	
}
