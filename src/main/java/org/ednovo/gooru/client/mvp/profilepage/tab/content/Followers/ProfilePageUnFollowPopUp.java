package org.ednovo.gooru.client.mvp.profilepage.tab.content.Followers;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProfilePageUnFollowPopUp extends PopupPanel {

	private static ProfilePageUnFollowPopUpUiBinder uiBinder = GWT
			.create(ProfilePageUnFollowPopUpUiBinder.class);

	interface ProfilePageUnFollowPopUpUiBinder extends
			UiBinder<Widget, ProfilePageUnFollowPopUp> {
	}
	
	MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField Label popupHeaderTitleLbl;
	@UiField Button okBtn;
	@UiField InlineLabel inputTitleLbl,inputTitleLblName;
	@UiField HTMLPanel buttonsContainer;
	
	public ProfilePageUnFollowPopUp() {
		setWidget(uiBinder.createAndBindUi(this));
		this.setGlassEnabled(true);
		okBtn.setText(i18n.GL1386());
		okBtn.getElement().setId("btnOkBtn");
		okBtn.getElement().setAttribute("alt",i18n.GL1386());
		okBtn.getElement().setAttribute("title",i18n.GL1386());
		
		popupHeaderTitleLbl.setText(i18n.GL1939());
		popupHeaderTitleLbl.getElement().setId("lblPopupHeaderTitleLbl");
		popupHeaderTitleLbl.getElement().setAttribute("alt",i18n.GL1939());
		popupHeaderTitleLbl.getElement().setAttribute("title",i18n.GL1939());
		
		String userName=AppClientFactory.getPlaceManager().getRequestParameter("user", null);
		inputTitleLbl.setText(i18n.GL1940());
		inputTitleLbl.getElement().setId("spnInputTitleLbl");
		inputTitleLbl.getElement().setAttribute("alt",i18n.GL1940());
		inputTitleLbl.getElement().setAttribute("title",i18n.GL1940());
		
		inputTitleLblName.setText(userName);
		inputTitleLblName.getElement().setId("spnInputTitleLblName");
		inputTitleLblName.getElement().setAttribute("alt",userName);
		inputTitleLblName.getElement().setAttribute("title",userName);
		
		inputTitleLblName.getElement().setAttribute("style", "font-weight:bold");
		buttonsContainer.getElement().setId("pnlButtonsContainer");
	}
	@UiHandler("okBtn")
	public void clickOnOk(ClickEvent event)
	{
		this.hide();
		Window.enableScrolling(true);
	}
}
