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
import java.util.Map;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Search Team
 *
 */
public interface IsCollectionContentView extends IsViewWithHandlers<CollectionContentUiHandlers> {
	/**
	 * This method is used to set the data in side the content panel
	 * @param result
	 * @param folderDo
	 */
	public void setData(CollectionDo result,FolderDo folderDo,RefreshType type);
	/**
	 * This method is used to reset the widgets positions
	 */
	public void resetWidgetPositions();
	/**
	 * This method is used to set the presenter reference.
	 * @param collectionContentPresenter
	 */
	public void setCollectionContentPresenter(CollectionContentPresenter collectionContentPresenter);

	/**
	 *
	 * @function displayNewResourcePopup
	 *
	 * @created_date : 03-Jul-2015
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
	void displayNewResourcePopup();

	/**
	 *
	 * @function setDisplayResourceItem
	 *
	 * @created_date : 03-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param collectionItem
	 * @parm(s) : @param type
	 * @parm(s) : @param index
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void setDisplayResourceItem(CollectionItemDo collectionItem,RefreshType type, int index);
	/**
	 *
	 * @function updateDeleteItem
	 *
	 * @created_date : 03-Jul-2015
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
	public void updateDeleteItem(String collectionItemId, int itemSequence);
	/**
	 *
	 * @function hideUpdateResourcePopup
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
	void hideUpdateResourcePopup();
	void updateCollectionItem(CollectionItemDo collectionItem);


	/**
	 *
	 * @function setUpdatedStandardsCode
	 *
	 * @created_date : 04-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param setStandardsVal
	 * @parm(s) : @param codeId
	 * @parm(s) : @param setStandardDesc
	 * @parm(s) : @param value
	 * @parm(s) : @param userResource
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void setUpdatedStandardsCode(String setStandardsVal, Integer codeId,
			String setStandardDesc, boolean value, boolean userResource);

	/**
	 *
	 * @function hideUpdateOwnResourcePopup
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
	void hideUpdateOwnResourcePopup();
	
	public void getResourceListPanel();
	void setCollectionDetails(CollectionItemDo collectionItemDo);
	public void updateResouceItemImage(String fileName,
			String fileNameWithOutRespUrl, boolean isEditUserOwnResourceImage);
	public void displaySelectedStandards(List<Map<String, String>> standListArray);
}
