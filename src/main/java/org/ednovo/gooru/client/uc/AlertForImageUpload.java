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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class AlertForImageUpload extends Composite {

	private static AlertContentUcUiBinder uiBinder = GWT.create(AlertContentUcUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	Button okButton;

	@UiField
	Label alertMessageField;

	AlertBoxUc alertBoxUc;

	@UiField
	Label alertMessageHeaderField;

	interface AlertContentUcUiBinder extends UiBinder<Widget, AlertForImageUpload> {
	}

	/**
	 * Class constructor
	 * @param messageHeader header for {@link AlertForImageUpload}
	 * @param messageContent content text for {@link AlertForImageUpload}
	 */
	public AlertForImageUpload(String messageHeader, String messageContent) {
		alertBoxUc = new AlertBoxUc();
		alertBoxUc.setContent(uiBinder.createAndBindUi(this));
		alertBoxUc.getElement().setAttribute("style", "position: absolute;z-index: 99999");
		alertMessageField.setText(messageContent);
		alertMessageField.getElement().setId("lblAlertMessageField");
		alertMessageField.getElement().setAttribute("alt",messageContent);
		alertMessageField.getElement().setAttribute("title",messageContent);
		
		alertMessageHeaderField.setText(messageHeader);
		alertMessageHeaderField.getElement().setId("lblAlertMessageHeaderField");
		alertMessageHeaderField.getElement().setAttribute("alt",messageHeader);
		alertMessageHeaderField.getElement().setAttribute("title",messageHeader);
		
		okButton.setText(i18n.GL0190());
		okButton.getElement().setId("btnOkButton");
		okButton.getElement().setAttribute("alt",i18n.GL0190());
		okButton.getElement().setAttribute("title",i18n.GL0190());
		
		alertBoxUc.show();
		alertBoxUc.center();
		
	}

	/**
	 * @param messageContent message content text
	 */
	public void setAlertMessage(String messageContent) {
		alertMessageField.setText(messageContent);
		alertMessageField.getElement().setAttribute("alt",messageContent);
		alertMessageField.getElement().setAttribute("title",messageContent);
	}

	/**
	 * @param messageHeader for {@link AlertForImageUpload}
	 */
	public void setAlertHeaderMessage(String messageHeader) {
		alertMessageHeaderField.setText(messageHeader);
		alertMessageHeaderField.getElement().setAttribute("alt",messageHeader);
		alertMessageHeaderField.getElement().setAttribute("title",messageHeader);
	}

	/**
	 * Close {@link AlertBoxUc}
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("okButton")
	public void onOkClick(ClickEvent clickEvent) {
		alertBoxUc.hide();
	}

	/**
	 * @return okButton instance of {@link Button}
	 */
	public Button getAlertButton() {
		return okButton;
	}
	
	/**
	 * @return alertBoxUc instance of {@link AlertBoxUc}
	 */
	public AlertBoxUc getAlertBox() {
		return alertBoxUc;
	}
}
