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
package org.ednovo.gooru.client.mvp.socialshare;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.AppPopUp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @fileName : SentEmailSuccessVc.java
 *
 * @description : This file used to show email share success popUp.
 *
 *
 * @version : 5.4
 *
 * @date: August, 2013
 *
 * @Author: Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class SentEmailSuccessVc extends Composite {

	private static SentEmailSuccessVcUiBinder uiBinder = GWT
			.create(SentEmailSuccessVcUiBinder.class);

	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface SentEmailSuccessVcUiBinder extends
	UiBinder<Widget, SentEmailSuccessVc> {
	}

	private AppPopUp appPopUp;

	@UiField
	Label toEmailLbl;

	@UiField
	Button okLbl;
	@UiField HTMLPanel emailToFriendText,emailSentText,popupContainer;

	/**
	 * Class constructor , create a new pop up
	 * @param toEmail
	 */
	public SentEmailSuccessVc(String toEmail) {
		initWidget(uiBinder.createAndBindUi(this));
		appPopUp = new AppPopUp();
		appPopUp.setWidget(uiBinder.createAndBindUi(this));
		appPopUp.getElement().getStyle().setZIndex(999999);
		appPopUp.setGlassStyleName("loginPopupGlassStyle");
		appPopUp.setGlassEnabled(true);
		appPopUp.show();
		appPopUp.center();

		emailToFriendText.getElement().setInnerHTML(i18n.GL0222());
		emailToFriendText.getElement().setId("pnlEmailToFriendText");
		emailToFriendText.getElement().setAttribute("alt", i18n.GL0222());
		emailToFriendText.getElement().setAttribute("title", i18n.GL0222());

		emailSentText.getElement().setInnerHTML(i18n.GL0648());
		emailSentText.getElement().setId("pnlEmailSentText");
		emailSentText.getElement().setAttribute("alt", i18n.GL0648());
		emailSentText.getElement().setAttribute("title", i18n.GL0648());

		toEmailLbl.setText(toEmail);
		toEmailLbl.getElement().setId("lblToEmailLbl");
		toEmailLbl.getElement().setAttribute("alt", toEmail);
		toEmailLbl.getElement().setAttribute("title", toEmail);

		okLbl.setText(i18n.GL0190());
		okLbl.getElement().setId("lblOkLbl");
		okLbl.getElement().setAttribute("alt", i18n.GL0190());
		okLbl.getElement().setAttribute("title", i18n.GL0190());

		Window.enableScrolling(false);
	}

	public SentEmailSuccessVc(String messageHeader, String desc) {
		initWidget(uiBinder.createAndBindUi(this));
		appPopUp = new AppPopUp();
		appPopUp.setWidget(uiBinder.createAndBindUi(this));
		appPopUp.setStyleName("ses-shelfItemSucessPopUp");
		appPopUp.getElement().getStyle().setZIndex(999999);
		appPopUp.setPixelSize(480, 208);
		appPopUp.setGlassStyleName("loginPopupGlassStyle");
		appPopUp.setGlassEnabled(true);
		appPopUp.show();
		appPopUp.center();

		emailToFriendText.getElement().setInnerHTML(messageHeader);
		emailToFriendText.getElement().setId("pnlEmailToFriendText");
		emailToFriendText.getElement().setAttribute("alt", messageHeader);
		emailToFriendText.getElement().setAttribute("title", messageHeader);

		emailSentText.getElement().setInnerHTML("");
		emailSentText.getElement().setId("pnlEmailSentText");

		toEmailLbl.setStyleName("ses-aleartDescText");
		toEmailLbl.setText(desc);
		toEmailLbl.getElement().setId("lblToEmailLbl");
		toEmailLbl.getElement().setAttribute("alt", desc);
		toEmailLbl.getElement().setAttribute("title", desc);

		okLbl.setText(i18n.GL0190());
		okLbl.getElement().setId("lblOkLbl");
		okLbl.getElement().setAttribute("alt", i18n.GL0190());
		okLbl.getElement().setAttribute("title", i18n.GL0190());
		if(i18n.GL1535().equalsIgnoreCase(desc) || i18n.GL1535_1().equalsIgnoreCase(desc)){
			popupContainer.getElement().getStyle().setWidth(438, Unit.PX);
			popupContainer.getElement().getStyle().setMargin(0, Unit.PX);
			popupContainer.getElement().getStyle().clearHeight();
			toEmailLbl.getElement().getStyle().clearWidth();
			toEmailLbl.addStyleName("ses-aleartDescTextForNotLoggedInUser");
			appPopUp.setStyleName("ses-shelfItemSucessPopUpWithOutWidth");
		}
		Window.enableScrolling(false);
	}

	/**
	 * Hide {@link SentEmailSuccessVc} popup
	 * @param clickEvent instOLance of {@link ClickEvent}
	 */
	@UiHandler("okLbl")
	public void onBtnClick(ClickEvent clickEvent) {
		appPopUp.hide();
		String placeToken = AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		if(placeToken.equals(PlaceTokens.STUDENT_VIEW) || placeToken.equals(PlaceTokens.STUDENT))
		{
			Window.enableScrolling(true);
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
		}
		else if(!(placeToken.equals(PlaceTokens.SEARCH_RESOURCE) || placeToken.equals(PlaceTokens.SEARCH_COLLECTION) || placeToken.equals(PlaceTokens.COLLECTION_PLAY) || placeToken.equals(PlaceTokens.ASSESSMENT_PLAY)  ||placeToken.equals(PlaceTokens.PREVIEW_PLAY)||placeToken.equals(PlaceTokens.RESOURCE_PLAY))) {
			Window.enableScrolling(true);
		}
	}
}
