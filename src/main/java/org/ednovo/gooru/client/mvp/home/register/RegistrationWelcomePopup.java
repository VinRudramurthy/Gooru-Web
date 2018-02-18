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

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.uc.BlueButtonUc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class RegistrationWelcomePopup extends Composite{

	private static RegistrationWelcomePopupUiBinder uiBinder = GWT.create(RegistrationWelcomePopupUiBinder.class);

	interface RegistrationWelcomePopupUiBinder extends UiBinder<Widget, RegistrationWelcomePopup> {
	}
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField
	BlueButtonUc registerWelcomeUc;
	
	@UiField Label getStartedText,happyStudyingText;
	
	private AppPopUp appPopUp;
	

	/**
	 * Class constructor, creates welcome popup after successful registration 
	 */
	public RegistrationWelcomePopup() {
		RegisterCBundle.INSTANCE.css().ensureInjected();
		appPopUp = new AppPopUp();
		appPopUp.setContent(i18n.GL1190()+i18n.GL_SPL_EXCLAMATION(), uiBinder.createAndBindUi(this));
		appPopUp.show();
		appPopUp.center();
		
		getStartedText.setText(i18n.GL1188()+i18n.GL_SPL_SEMICOLON()+" ");
		getStartedText.getElement().setId("lblGetStartedText");
		getStartedText.getElement().setAttribute("alt",i18n.GL1188());
		getStartedText.getElement().setAttribute("title",i18n.GL1188());
		
		happyStudyingText.setText(i18n.GL1189()+i18n.GL_SPL_EXCLAMATION());
		happyStudyingText.getElement().setId("lblHappyStudyingText");
		happyStudyingText.getElement().setAttribute("alt",i18n.GL1189());
		happyStudyingText.getElement().setAttribute("title",i18n.GL1189());
		
		registerWelcomeUc.setText(i18n.GL0190());
		registerWelcomeUc.getElement().setId("btnRegisterWelcome");
		registerWelcomeUc.getElement().setAttribute("alt",i18n.GL0190());
		registerWelcomeUc.getElement().setAttribute("title",i18n.GL0190());
	}
	
	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	/**
	 * @return {@link BlueButtonUc} widget
	 */
	public BlueButtonUc getConfirmButton() {
		return registerWelcomeUc; 
	}
	
	/**
	 * @return {@link AppPopUp} widget
	 */
	public AppPopUp getPopupPanel() { 
		return appPopUp;
	}

}
