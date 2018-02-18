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
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.gwtplatform.mvp.client.PopupView;

public interface IsSearchAddResourceToCollectionView extends PopupView, IsViewWithHandlers<SearchAddResourceToCollectionUiHandlers>{
	public void displayWorkspaceData(FolderListDo folderListDo,boolean clearShelfPanel,String searchType);
	public void setFolderItems(TreeItem item,FolderListDo folderListDo);
	public void displayNoCollectionsMsg(String searchType);
	public Button getAddButton();
	public void hidePopup();
	void setDefaultPanelVisibility(Boolean blnVal);
	public void displaySuccessPopup(String title,String selectedGooruOid,HashMap<String, String> params,String searchType, FolderDo folderDo);
	void restrictionToAddResourcesData(String message);
	public void clearUrlParams();
    PopupPanel getAppPopUp();
	public void setCopyAndMoveStatus(boolean isCopySelected,boolean isMoveSelected,String selectedType);
	public Anchor getMycollectionsLbl();
	public Label getMycollectionsDefaultLbl();
	public Anchor getMycontentLbl();
	public void setFromMyCourse(boolean value);
	public void enableAddButton();
	public void isFromCopyResource(boolean isFromCopyResource);
	public Image loadingImage();
	public void closeTabView();
	
	public void disableTabs(boolean isVisible, String copyType);
}
