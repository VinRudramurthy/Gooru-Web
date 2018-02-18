package org.ednovo.gooru.client.mvp.assessments.play.collection.flag;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.ContentReportDo;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.gwtplatform.mvp.client.PopupView;


public interface IsAssessmentsFlagView extends PopupView,IsViewWithHandlers<AssessmentsFlagUiHandler>{

	public void getDisplayData(CollectionDo collectionDo);
	public void setDefaultView();
	public void setFlag(ContentReportDo contentReportDo, String gooruFlagId);
	public Image getCloseButton();
	public Button getSubmitButton();

	public void showSuccesmessagePopup();


}
