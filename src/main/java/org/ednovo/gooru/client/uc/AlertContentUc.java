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

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author Search Team
 *
 */
public class AlertContentUc extends Composite {

	private static AlertContentUcUiBinder uiBinder = GWT.create(AlertContentUcUiBinder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField
	Button okButton;

	@UiField
	HTML alertMessageField;

	AlertBoxUc alertBoxUc;

	@UiField
	Label alertMessageHeaderField;
	
	@UiField 
	FlowPanel headerPanel;

	interface AlertContentUcUiBinder extends UiBinder<Widget, AlertContentUc> {
	}

	/**
	 * Class constructor
	 * @param messageHeader header for {@link AlertContentUc}
	 * @param messageContent content text for {@link AlertContentUc}
	 */
	public AlertContentUc(String messageHeader, String messageContent) {
		alertBoxUc = new AlertBoxUc();
		alertBoxUc.setContent(uiBinder.createAndBindUi(this));
		
		alertMessageField.setHTML(messageContent);
		alertMessageField.getElement().setId("pnlAlertMessageField");
		alertMessageField.getElement().setAttribute("alt",messageContent);
		alertMessageField.getElement().setAttribute("title",messageContent);
		
		alertMessageHeaderField.setText(messageHeader);
		alertMessageHeaderField.getElement().setId("lblAlertMessageHeaderField");
		alertMessageHeaderField.getElement().setAttribute("alt",messageHeader);
		alertMessageHeaderField.getElement().setAttribute("title",messageHeader);
		
		alertBoxUc.center();
		alertBoxUc.show();
		
		
		okButton.setText(i18n.GL0190());
		okButton.getElement().setId("btnOkButton");
		okButton.getElement().setAttribute("alt",i18n.GL0190());
		okButton.getElement().setAttribute("title",i18n.GL0190());
		
		alertBoxUc.getElement().getStyle().setZIndex(999999);
		
		headerPanel.getElement().setId("fpnlHeaderPanel");
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
	 * @param messageHeader for {@link AlertContentUc}
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
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
		if(placeRequest.getNameToken().equals(PlaceTokens.SEARCH_RESOURCE)||placeRequest.getNameToken().equals(PlaceTokens.MYCONTENT)){
			Window.enableScrolling(true);
		}
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
