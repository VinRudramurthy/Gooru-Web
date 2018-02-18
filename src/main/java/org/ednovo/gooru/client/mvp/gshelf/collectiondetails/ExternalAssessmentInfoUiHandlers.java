package org.ednovo.gooru.client.mvp.gshelf.collectiondetails;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.ShelfTreeWidget;

import com.google.gwt.user.client.ui.TreeItem;

public interface ExternalAssessmentInfoUiHandlers extends BaseUiHandlers {
	/**
	 * This method is used for checking profanity
	 * @param textValue
	 * @param isCreate
	 * @param createOrUpDate 
	 * @param currentShelfTreeWidget 
	 * @param type
	 */
	public void checkProfanity(String textValue,boolean isCreate,int index, CreateDo createOrUpDate, TreeItem currentShelfTreeWidget);
	/**
	 * To Create Course
	 * @param createObj {@link String} 
	 * @param isCreateAssessment {@link boolean} 
	 * @param currentShelfTreeWidget 
	 */
	void createAndSaveAssessmentDetails(CreateDo createObj,boolean isCreateAssessment, TreeItem currentShelfTreeWidget); 
	/**
	 * To update the course details
	 * @param currentShelfTreeWidget 
	 * @param courseTitle {@link String} 
	 */
	void updateAssessmentDetails(CreateDo createOrUpDate, String id,boolean isCreateAssessment,FolderDo folderObj, TreeItem currentShelfTreeWidget);
	
	public TreeItem getSelectedWidget();
}
