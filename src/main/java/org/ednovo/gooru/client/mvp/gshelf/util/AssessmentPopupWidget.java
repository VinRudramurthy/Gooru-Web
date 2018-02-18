package org.ednovo.gooru.client.mvp.gshelf.util;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.H4Panel;
import org.ednovo.gooru.client.uc.suggestbox.widget.Paragraph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AssessmentPopupWidget extends PopupPanel {

	private static AssessmentPopupWidgetUiBinder uiBinder = GWT
			.create(AssessmentPopupWidgetUiBinder.class);

	interface AssessmentPopupWidgetUiBinder extends
			UiBinder<Widget, AssessmentPopupWidget> {
	}
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField HTMLPanel pnlMainContainer;
	@UiField InlineLabel spnClose;
	@UiField Button btnCreateNormalAssessment,btnCreateExternalAssessment;
	@UiField H4Panel pnlTitle;
	@UiField Paragraph pBuildText,pLinkText,pNoteText;
	
	public AssessmentPopupWidget() {
		setWidget(uiBinder.createAndBindUi(this));
		pnlMainContainer.getElement().setId("addAssessmentPopup");
		pnlTitle.getElement().setId("myModalLabel");
		pnlTitle.setText(i18n.GL3024());
		pBuildText.setText(i18n.GL3200());
		pLinkText.setText(i18n.GL3201());
		pNoteText.setText(i18n.GL3202());
	}
	@UiHandler("btnCreateNormalAssessment")
	public void clickOnNormalAssessment(ClickEvent event){
		clickOnNoramlAssessmentClick();
	}
	@UiHandler("btnCreateExternalAssessment")
	public void clickOnExternalAssessment(ClickEvent event){
		clickOnExternalAssessmentClick();
	}
	@UiHandler("spnClose")
	public void clickOnClose(ClickEvent event){
		AppClientFactory.printInfoLogger("fire Click Event");
		this.hide();
		Window.enableScrolling(true);
	}
	public abstract void clickOnNoramlAssessmentClick();
	public abstract void clickOnExternalAssessmentClick();
}
