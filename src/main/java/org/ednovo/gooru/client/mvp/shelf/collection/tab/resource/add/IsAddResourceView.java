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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add;

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
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.application.shared.model.content.ExistsResourceDo;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.content.ResourceMetaInfoDo;
import org.ednovo.gooru.application.shared.model.drive.GoogleDriveItemDo;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;

import com.google.gwt.user.client.ui.FlowPanel;
import com.gwtplatform.mvp.client.PopupView;

public interface IsAddResourceView extends PopupView, IsViewWithHandlers<AddResourceUiHandlers>{
	void setImageUrl(String fileName,String fileNameWithoutRepository,boolean isQuestionImage, boolean isUserOwnResourceImage);

	void setNewResourcePopupData(ResourceMetaInfoDo resMetaInfo);

	void setExistingResourceData(ExistsResourceDo existsResourceDo,CollectionDo collectionDo);


	void setPopup(String clickType);
	
	public void getResourceMetaInfo(String webUrl);

	void setShortenUrlAlertMsg();

	void closePopUp();
	
	void setCollectionItemDo(CollectionItemDo collectionItemDo);
	
	void removeQuestionEditImage();

	void uploadResource(MediaUploadDo result); 
	
	void getDriveDetails(GoogleDriveItemDo driveDo);
	
	void getFolderDetails(String title, String id, List<GoogleDriveItemDo> result); 
	
	public void showAddWebResourceWidget(boolean isGoogleDriveFile,FlowPanel googleDriveContainer,GoogleDriveItemDo googleDriveItemDo);

	void setUpdatedStandardsCode(String setStandardsVal,int id,String desc,boolean val, boolean isUserOwnResource);
	
	public void setCollectionDo(CollectionDo collectionDo);

	void setPopupImageData(ResourceMetaInfoDo result);
	
	void clearQuestionSlot();
	
	void hidePopup();
	
	void questionMetadata(CollectionQuestionItemDo collectionQuestionItemDo);
	
	boolean checkQuestionSlot();
	
	void setDepthOfKnowledges(List<ListValuesDo> result);

	void displaySelectedStandards(List<Map<String, String>> standListArray);
	
	
}
