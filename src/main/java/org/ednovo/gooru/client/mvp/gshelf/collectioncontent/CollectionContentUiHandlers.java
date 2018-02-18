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
package org.ednovo.gooru.client.mvp.gshelf.collectioncontent;

import java.util.List;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.shelf.event.UpdateEditResourceImageHandler;

/**
 * @author Search Team
 *
 */
public interface CollectionContentUiHandlers extends BaseUiHandlers ,UpdateEditResourceImageHandler {
	/**
	 * This method is used to set the folderdo object
	 * @param folderDo
	 */
	public void setData(FolderDo folderDo);
	/**
	 * This method is used to reorder positions of the widgets
	 * @param idToMove
	 * @param itemSeqToAPI
	 */
	public void reorderWidgetPositions(String idToMove,int itemSeqToAPI,String moveGooruOid) ;
	/**
	 * This method is used to update narration.
	 * @param collectionItemId
	 * @param narration
	 */
	public void updateNarrationItem(CollectionItemDo collectionItem, String narration);
	/**
	 * This method is used to delete resoruce
	 * @param collectionItemId
	 */
	public void deleteCollectionItem(String collectionId,String collectionItemId, int itemSequence);
	/**
	 * This method is used to update the pdf pages
	 * @param collectionItem
	 * @param narration
	 * @param start
	 * @param stop
	 */
	public void updateCollectionItem(final CollectionItemDo collectionItem, String narration, String start, String stop);
	
	
	public void updateNarrationItemMetaData(String collectionId,CollectionItemDo collectionItem, String narration,String start, String stop);

	/**
	 *
	 * @function addResourcePopup
	 *
	 * @created_date : 03-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param collectionDo
	 * @parm(s) : @param clickType
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void addResourcePopup(CollectionDo collectionDo, String clickType);
	/**
	 *
	 * @function updateQustionImage
	 *
	 * @created_date : 04-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param collectionItemId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void updateQustionImage(String collectionItemId);
	/**
	 *
	 * @function removeQuestionImage
	 *
	 * @created_date : 04-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param collectionQuestionId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void removeQuestionImage(String collectionQuestionId);

	/**
	 *
	 * @function updateResourceInfo
	 *
	 * @created_date : 04-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param collectionItemDo
	 * @parm(s) : @param tagList
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void updateResourceInfo(CollectionItemDo collectionItemDo,
			List<String> tagList);
	/**
	 *
	 * @function imageEditResourceUpload
	 *
	 * @created_date : 04-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void imageEditResourceUpload();

	/**
	 *
	 * @function imageEditUserOwnResourceUpload
	 *
	 * @created_date : 04-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void imageEditUserOwnResourceUpload();
	/**
	 *
	 * @param collectionId 
	 * @function editUserOwnResource
	 *
	 * @created_date : 04-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param jsonString
	 * @parm(s) : @param gooruOid
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void editUserOwnResource(String jsonString, String gooruOid, String collectionId);

	/**
	 *
	 * @function updateQuestionResource
	 *
	 * @created_date : 04-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param questionItemId
	 * @parm(s) : @param collectionQuestionItemDo
	 * @parm(s) : @param thumbnailUrl
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void updateQuestionResource(String questionItemId,
			CollectionQuestionItemDo collectionQuestionItemDo,
			String thumbnailUrl);

	/**
	 *
	 * @function showEditQuestionResourcePopup
	 *
	 * @created_date : 04-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param collectionItem
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void showEditQuestionResourcePopup(CollectionItemDo collectionItem);
	void updateVideoTimeUpdate(CollectionItemDo collectionItemDo);
	public void updateWidgetCount(CollectionItemDo collectionItem,boolean isDelete);
	public void showResourcePopup(CollectionItemDo collectionItem);
	public void disableCollabaratorOptions(boolean b);
	
	public void showLastEditCollaborater(String lblLastEditedBy,
			boolean hasLastModifiedUser);
	public void showStandardsPopup(String standardVal, String standardsDesc,
			List<LiPanelWithClose> collectionLiPanelWithCloseArray);
	
}
