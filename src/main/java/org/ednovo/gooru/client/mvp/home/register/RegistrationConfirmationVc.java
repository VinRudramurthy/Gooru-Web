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
package org.ednovo.gooru.client.mvp.home.register;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.uc.BlueButtonUc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class RegistrationConfirmationVc extends Composite{
	@UiField
	BlueButtonUc confirmRegisterUc;
	
	private AppPopUp appPopUp;
	
	@UiField Label almostHereText,sentConformationText,contactText,supportText,didnotReceiveText;
	
	private static final String REGISTRATION_RECEIVED = "Registration Received";
	
	private static RegistrationConfirmationUiBinder uiBinder = GWT
			.create(RegistrationConfirmationUiBinder.class);

	interface RegistrationConfirmationUiBinder extends
			UiBinder<Widget, RegistrationConfirmationVc> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor, creates popup after register and tells to update the details by clicking mail which entered   
	 * by user 
	 */
	public RegistrationConfirmationVc() {
		appPopUp = new AppPopUp();		
		appPopUp.setContent(REGISTRATION_RECEIVED, uiBinder.createAndBindUi(this));
		appPopUp.show();
		appPopUp.center();
		
		almostHereText.setText(i18n.GL1191());
		almostHereText.getElement().setId("lblAlmostHereText");
		almostHereText.getElement().setAttribute("alt",i18n.GL1191());
		almostHereText.getElement().setAttribute("title",i18n.GL1191());
		
		sentConformationText.setText(i18n.GL1192());
		sentConformationText.getElement().setId("lblSentConformationText");
		sentConformationText.getElement().setAttribute("alt",i18n.GL1192());
		sentConformationText.getElement().setAttribute("title",i18n.GL1192());
		
		contactText.setText(i18n.GL1193());
		contactText.getElement().setId("lblContactText");
		contactText.getElement().setAttribute("alt",i18n.GL1193());
		contactText.getElement().setAttribute("title",i18n.GL1193());
		
		supportText.setText("  "+i18n.GL0299());
		supportText.getElement().setId("lblSupportText");
		supportText.getElement().setAttribute("alt","  "+i18n.GL0299());
		supportText.getElement().setAttribute("title","  "+i18n.GL0299());
		
		confirmRegisterUc.setText(i18n.GL0190());
		confirmRegisterUc.getElement().setId("btnConfirmRegister");
		confirmRegisterUc.getElement().setAttribute("alt",i18n.GL0190());
		confirmRegisterUc.getElement().setAttribute("title",i18n.GL0190());
		
		didnotReceiveText.setText(i18n.GL1194()+i18n.GL_SPL_QUESTION());
		didnotReceiveText.getElement().setId("lblDidnotReceiveText");
		didnotReceiveText.getElement().setAttribute("alt",i18n.GL1194());
		didnotReceiveText.getElement().setAttribute("title",i18n.GL1194());
	}
	
	/**
	 * Close the registration confirmation popup by clicking ok button 
	 * @param clickEvent instance of the {@link ClickEvent}
	 */
	@UiHandler("confirmRegisterUc")
	public void confirmRegisteration(ClickEvent clickEvent){
		appPopUp.hide();
		Window.enableScrolling(true);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
	}
	

}
