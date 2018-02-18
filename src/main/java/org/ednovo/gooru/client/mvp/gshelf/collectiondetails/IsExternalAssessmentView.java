package org.ednovo.gooru.client.mvp.gshelf.collectiondetails;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.ShelfTreeWidget;

import com.google.gwt.user.client.ui.TreeItem;

public interface IsExternalAssessmentView extends IsViewWithHandlers<ExternalAssessmentInfoUiHandlers> {

	void callCreateAndUpdate(boolean isCreate, boolean value, int index, CreateDo createOrUpDate, TreeItem currentShelfTreeWidget);
	/**
	 * This method is used to set data object
	 * @param folderObj
	 */
	void setData(FolderDo folderObj);
	void resetBtns();
	void spinnerImageVisibility(boolean isVisible);

}
