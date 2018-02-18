package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.addquestion;

import java.util.List;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.shelf.event.AddResourceImageHandler;



/**
 * @author Hari
 *
 */

public interface QuestionTypeUiHandlers extends BaseUiHandlers,AddResourceImageHandler{

	void removeQuestionImage(String collectionItemId);
	void questionImageUpload();
	void questionImageUpload(String collectionItemId);
	void browseStandardsInfo(boolean isQuestion, boolean isUserResource);
	void addUpdatedBrowseStandards();
	void closeStandardsPopup();
	void hidePopup();
	void removeAnswerImage(String collectionItemId);
	void answerImageUpload();
	void answerImageUpload(String collectionItemId);
	void v2UpdateQuestionResource(CollectionItemDo collectionItemDo,CollectionQuestionItemDo collectionQuestionItemDo, String thumbnailUrl);
	void addHSQuestionResource(String mediaFileName, CollectionQuestionItemDo collectionQuestionItemDo);
	void showStandardsPopup(String standardVal, String standardsDesc,
			List<LiPanelWithClose> collectionLiPanelWithCloseArray);
}
