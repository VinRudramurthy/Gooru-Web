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
package org.ednovo.gooru.client.mvp.assessments.play.collection.share.email;



import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.PlayerBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class SentEmailSuccessVc extends PopupPanel{

	private static SentEmailSuccessVcUiBinder uiBinder = GWT
			.create(SentEmailSuccessVcUiBinder.class);

	interface SentEmailSuccessVcUiBinder extends
			UiBinder<Widget, SentEmailSuccessVc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	Label toEmailLbl;

	@UiField
	Button okLbl;

	@UiField HTMLPanel emailToFriendText,thanksEmailText;

	/**
	 * Class constructor , create a new pop up
	 * @param toEmail
	 */
	public SentEmailSuccessVc(String toEmail) {
		setWidget(uiBinder.createAndBindUi(this));
		toEmailLbl.setText(toEmail);
		toEmailLbl.getElement().setId("lblToEmailLbl");
		toEmailLbl.getElement().setAttribute("alt",toEmail);
		toEmailLbl.getElement().setAttribute("title",toEmail);
		this.setGlassEnabled(true);
		this.setGlassStyleName(PlayerBundle.INSTANCE.getPlayerStyle().setGlassPanelStyle());
		this.getElement().getStyle().setZIndex(99999);
		this.show();
		this.center();
		emailToFriendText.getElement().setInnerHTML(i18n.GL0222());
		emailToFriendText.getElement().setId("pnlEmailToFriendText");
		emailToFriendText.getElement().setAttribute("alt",i18n.GL0222());
		emailToFriendText.getElement().setAttribute("title",i18n.GL0222());
		thanksEmailText.getElement().setInnerHTML(i18n.GL0648());
		thanksEmailText.getElement().setId("pnlThanksEmailText");
		thanksEmailText.getElement().setAttribute("alt",i18n.GL0648());
		thanksEmailText.getElement().setAttribute("title",i18n.GL0648());
		okLbl.setText(i18n.GL0190());
		okLbl.getElement().setId("lblOkLbl");
		okLbl.getElement().setAttribute("alt",i18n.GL0190());
		okLbl.getElement().setAttribute("title",i18n.GL0190());

	}

	/**
	 * Hide {@link SentEmailSuccessVc} popup
	 * @param clickEvent instOLance of {@link ClickEvent}
	 */
	@UiHandler("okLbl")
	public void onBtnClick(ClickEvent clickEvent) {
		hide();
	}
}
