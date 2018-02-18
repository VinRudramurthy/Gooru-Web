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
package org.ednovo.gooru.client.uc;


import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.play.collection.flag.FlagBundle;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ThankYouToolTip extends PopupPanel{

	private static ThankYouToolTipUiBinder uiBinder = GWT
			.create(ThankYouToolTipUiBinder.class);

	interface ThankYouToolTipUiBinder extends UiBinder<Widget, ThankYouToolTip> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField
	HTMLEventPanel closeButton;
	
	@UiField Image popUpCloseButton;
	
	@UiField Button okButton;
	
	@UiField Label successPopupTitle,emailIdLbl,emailConfirmationText;
	@UiField Label successPopupMessage;
	@UiField HTMLPanel emailPopupThanksmsg;
	
	public ThankYouToolTip() {
		super(false);
		setWidget(uiBinder.createAndBindUi(this));
		this.getElement().getStyle().setZIndex(999999);
		this.setGlassStyleName(FlagBundle.IMAGEBUNDLEINSTANCE.flagstyle().glassStyle());
		setGlassEnabled(true);
		FlagBundle.IMAGEBUNDLEINSTANCE.flagstyle().ensureInjected();
		successPopupTitle.setText(i18n.GL0600());
		successPopupTitle.getElement().setId("lblSuccessPopupTitle");
		successPopupTitle.getElement().setAttribute("alt", i18n.GL0600());
		successPopupTitle.getElement().setAttribute("title", i18n.GL0600());
		closeButton.getElement().setId("epnlCloseButton");
		popUpCloseButton.getElement().setId("imgPopUpCloseButton");
		popUpCloseButton.getElement().setAttribute("alt", i18n.GL1050());
		popUpCloseButton.getElement().setAttribute("title", i18n.GL1050());
		popUpCloseButton.setAltText(i18n.GL1050());
		successPopupMessage.setText(i18n.GL0615());
		successPopupMessage.getElement().setId("lblSuccessPopupMessage");
		successPopupMessage.getElement().setAttribute("alt", i18n.GL0615());
		successPopupMessage.getElement().setAttribute("title", i18n.GL0615());
		emailPopupThanksmsg.getElement().setId("pnlEmailPopupThanksmsg");
		emailConfirmationText.setText(i18n.GL0648());
		emailConfirmationText.getElement().setId("lblEmailConfirmationText");
		emailConfirmationText.getElement().setAttribute("alt", i18n.GL0648());
		emailConfirmationText.getElement().setAttribute("title", i18n.GL0648());
		emailIdLbl.getElement().setId("lblEmailIdLbl");
		okButton.setText(i18n.GL0190());
		okButton.getElement().setAttribute("alt", i18n.GL0190());
		okButton.getElement().setAttribute("title", i18n.GL0190());
		popUpCloseButton.setResource(FlagBundle.IMAGEBUNDLEINSTANCE.closeFlagPopUpImages());
		okButton.getElement().setAttribute("id","okButton");
		successPopupMessage.setVisible(true);
		emailPopupThanksmsg.setVisible(false);
	}
	
	
	public void setTitleData(String title, String emailId) {
		successPopupTitle.setText(title);
		successPopupTitle.getElement().setAttribute("alt", title);
		successPopupTitle.getElement().setAttribute("title", title);
		successPopupMessage.setVisible(false);
		emailPopupThanksmsg.setVisible(true);
		emailIdLbl.setText(emailId);
		emailIdLbl.getElement().setAttribute("alt", emailId);
		emailIdLbl.getElement().setAttribute("title", emailId);

	}
	
	@UiHandler("okButton")
	public void onClickOfOkButton(ClickEvent event) {
		Window.enableScrolling(true);
		this.hide();
				
	}

	@UiHandler("closeButton")
	public void onClickOfcloseButton(ClickEvent event) {
		Window.enableScrolling(true);
		this.hide();
	}
	
}
