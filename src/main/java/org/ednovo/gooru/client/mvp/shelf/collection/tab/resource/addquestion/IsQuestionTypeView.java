package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.addquestion;

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;

import com.google.gwt.user.client.ui.Button;


/**
 * @author Hari
 *
 */
public interface IsQuestionTypeView extends IsViewWithHandlers<QuestionTypeUiHandlers>{

	void getRevealType();

	void setImageUrl(String fileName,String fileNameWithoutRepository,boolean isQuestionImage, boolean isUserOwnResourceImage);

	void OnBrowseStandardsClickEvent(Button addBtn);

	void setUpdatedStandardsCode(String setStandardsVal,int setStandardsIdVal, String setStandardDesc);

	void resetFields(String type);

	void editQuestion(CollectionItemDo collectionItemDo);

	void clearTinyMce();

	void setEditData();
	
	void setMetadata(CollectionQuestionItemDo collectionQuestionItemDo);
	
	CollectionQuestionItemDo getMetadata();
	
	void removeQuestionEditImage();

	void displaySelectedStandards(List<Map<String, String>> standListArray);

	void getAddStandards();



}