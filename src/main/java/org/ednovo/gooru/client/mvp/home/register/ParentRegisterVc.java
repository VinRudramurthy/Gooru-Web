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
import org.ednovo.gooru.client.uc.ErrorLabelUc;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class ParentRegisterVc extends Composite {

	private static ParentRegisterVcUiBinder uiBinder = GWT.create(ParentRegisterVcUiBinder.class);

	@UiField
	Anchor guardianCancelAnr;

	@UiField
	BlueButtonUc almostDoneRegisterationUc;
	
	@UiField
	TextBox parentEmailIdTxtBox;
	
	@UiField
	ErrorLabelUc emailValidationUc; 	
	
	@UiField Label setUpText,emailtext;

	private AppPopUp appPopUp;
	
//	private static final String REGISTER_GOORU_BETA_ACCOUNT = i18n.GL1197+i18n.GL_SPL_EXCLAMATION ;
	
	private static final String EMAIL = "email";

	interface ParentRegisterVcUiBinder extends UiBinder<Widget, ParentRegisterVc> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor , creates popup for parent registration
	 */
	public ParentRegisterVc() {
		RegisterCBundle.INSTANCE.css().ensureInjected();
		appPopUp = new AppPopUp();
		appPopUp.setContent((i18n.GL1197()+i18n.GL_SPL_EXCLAMATION()), uiBinder.createAndBindUi(this));
		appPopUp.show();
		appPopUp.center();
		setUpText.setText(i18n.GL1195());
		setUpText.getElement().setId("lblSetUpText");
		setUpText.getElement().setAttribute("alt",i18n.GL1195());
		setUpText.getElement().setAttribute("title",i18n.GL1195());
		
		emailtext.setText(i18n.GL1196());
		emailtext.getElement().setId("lblEmailtext");
		emailtext.getElement().setAttribute("alt",i18n.GL1196());
		emailtext.getElement().setAttribute("title",i18n.GL1196());
		
		almostDoneRegisterationUc.setText(i18n.GL0228());
		almostDoneRegisterationUc.getElement().setId("btnSend");
		almostDoneRegisterationUc.getElement().setAttribute("alt",i18n.GL0228());
		almostDoneRegisterationUc.getElement().setAttribute("title",i18n.GL0228());
		
		guardianCancelAnr.setText(i18n.GL0142());
		guardianCancelAnr.getElement().setId("lnkCancel");
		guardianCancelAnr.getElement().setAttribute("alt",i18n.GL0142());
		guardianCancelAnr.getElement().setAttribute("title",i18n.GL0142());
		
		emailValidationUc.setVisible(false);
		emailValidationUc.setStyleName(RegisterCBundle.INSTANCE.css().parentErrorLabel());
		parentEmailIdTxtBox.addFocusHandler(new OnEmailFocus());
		parentEmailIdTxtBox.addBlurHandler(new OnEmailBlur());
		parentEmailIdTxtBox.getElement().setId("txtParentEmailId");
		StringUtil.setAttributes(parentEmailIdTxtBox, true);
		emailValidationUc.getElement().setId("errlblEmailValidationUc");		
	}
	
	/**
	 * @author Search Team
	 * Focus event on parent email id
	 *
	 */
	private class OnEmailFocus implements FocusHandler{
		@Override
		public void onFocus(FocusEvent event) {
			if(emailValidationUc.isVisible()){
				emailValidationUc.setVisible(false);
				parentEmailIdTxtBox.removeStyleName(RegisterCBundle.INSTANCE.css().guardianErrorEmail());
			}
		}		
	}
	
	/**
	 * @author Search Team
	 * Blur events on parent email id validation
	 */
	private class OnEmailBlur implements BlurHandler{
		@Override
		public void onBlur(BlurEvent event) {
			hasValidateData();
		}	
	}
	
	
	/**
	 * Hide popup
	 * @param clickEvent instance Of {@link ClickEvent} 
	 */
	@UiHandler("guardianCancelAnr")
	public void onCancelClick(ClickEvent clickEvent) {
		appPopUp.hide();
		Window.enableScrolling(true);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
	}

	/**
	 * @return {@link BlueButtonUc} widget 
	 */
	public BlueButtonUc getConfirmationButton() {
		return almostDoneRegisterationUc; 
	}
	
	/**
	 * Parent email validation that contains empty string and '@' symbol 
	 * @return true if entered parent email id is satisfy the condition or false  
	 */
	public boolean hasValidateData() {
		boolean isValid = true;
		String email = getParentEmailId();
		if (email == null || (email != null && email.isEmpty())) {
			parentEmailIdTxtBox.addStyleName(RegisterCBundle.INSTANCE.css().guardianErrorEmail());
			emailValidationUc.setVisible(true);
			emailValidationUc.setText(StringUtil.generateMessage(i18n.GL0082(), EMAIL));
			emailValidationUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0082(), EMAIL));
			emailValidationUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0082(), EMAIL));
			isValid = false;
		}
		if ((email != null && !email.isEmpty()) && !email.contains("@")) {
			parentEmailIdTxtBox.addStyleName(RegisterCBundle.INSTANCE.css().guardianErrorEmail());
			emailValidationUc.setVisible(true);
			emailValidationUc.setText(StringUtil.generateMessage(i18n.GL0067(), EMAIL));
			emailValidationUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0067(), EMAIL));
			emailValidationUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0067(), EMAIL));
			isValid = false;
		}		
		return isValid;
	}
	
	/**
	 * @return parent email id
	 */
	public String getParentEmailId() {
		return parentEmailIdTxtBox.getText(); 
	}
	
	/**
	 * @return {@link AppPopUp} widget
	 */
	public AppPopUp getPopupPanel() { 
		return appPopUp;
	}
	
	


}
