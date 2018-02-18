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

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 * 
 */
public class AlertMessageUc extends Composite {

	public AppPopUp appPopUp;

	@UiField
	public BlueButtonUc okButton;

	@UiField
	FlowPanel content;

	private static AlertMessageUcUiBinder uiBinder = GWT.create(AlertMessageUcUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface AlertMessageUcUiBinder extends UiBinder<Widget, AlertMessageUc> {
	}

	/**
	 * Class constructor
	 * 
	 * @param messageHeader
	 *            header for popup
	 * @param messageContent
	 *            for popup
	 */
	public AlertMessageUc(String messageHeader, Widget messageContent) {
		initWidget(uiBinder.createAndBindUi(this));
		appPopUp = new AppPopUp();
		
		appPopUp.setContent(messageHeader, uiBinder.createAndBindUi(this));
		content.add(messageContent);
		appPopUp.show();
		appPopUp.center();
		okButton.setText(i18n.GL0190());
		okButton.getElement().setId("btnOk");
		okButton.getElement().setAttribute("alt",i18n.GL0190());
		okButton.getElement().setAttribute("title",i18n.GL0190());
		content.getElement().setId("fpnlContent");
	}

	@UiHandler("okButton")
	public void onClick(ClickEvent clickEvent) {
		hide();
	}

	public void hide() {
		Window.enableScrolling(true);
		 AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		appPopUp.hide();
	}
}
