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
package org.ednovo.gooru.client.mvp.home;

import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.uc.BlueButtonUc;
import org.ednovo.gooru.client.uc.ErrorLabelUc;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
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
public class ResetPasswordVc extends Composite{

	private static ResetPasswordVcUiBinder uiBinder = GWT
			.create(ResetPasswordVcUiBinder.class);

	interface ResetPasswordVcUiBinder extends UiBinder<Widget, ResetPasswordVc> {
	}

	 private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	Anchor resetPwdCancelAnr;

	@UiField
	BlueButtonUc sendMailBtnUc;

	@UiField
	TextBox resetConfirmPwdTxtBox;

	@UiField
	TextBox resetNewPwdTxtBox;

	@UiField
	ErrorLabelUc confirmPwdValidationUc;

	@UiField
	ErrorLabelUc newPwdValidationUc;

	@UiField
	Label newPasswordText,newPwdLbl,confirmPwdLbl,tokenExpireErrorLabel;

	private String resetToken;

	private AppPopUp appPopUp;

	private String homeEndPoint=null;

	private boolean isDigiSpclChars=false;

	private static final String PASSWORD_PATTERN = "[0-9]|[$@!#*%^/[/]}{()_&-+=.,<>;\\|]";

	/**
	 * Class constructor , create reset password popup and assign password reset token
	 * @param resetToken password token
	 */
	public ResetPasswordVc(String resetToken) {
		appPopUp = new AppPopUp();
		AppClientFactory.getInjector().getSearchService()
		.getHomeEndPointUrl(new SimpleAsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				homeEndPoint = result;
			}
		});

		if(!appPopUp.isShowing()){
			this.resetToken = resetToken;
			appPopUp.setContent(i18n.GL0062(), uiBinder.createAndBindUi(this));
			appPopUp.show();
			appPopUp.center();
			appPopUp.getMainPanel().addStyleName("PopupMainVSmall");
			newPasswordText.setText(i18n.GL1254());
			newPasswordText.getElement().setId("lblNewPasswordText");
			newPasswordText.getElement().setAttribute("alt",i18n.GL1254());
			newPasswordText.getElement().setAttribute("title",i18n.GL1254());

			newPwdLbl.setText(i18n.GL1255());
			newPwdLbl.getElement().setId("lblNewPwdLbl");
			newPwdLbl.getElement().setAttribute("alt",i18n.GL1255());
			newPwdLbl.getElement().setAttribute("title",i18n.GL1255());

			confirmPwdLbl.setText(i18n.GL0427());
			confirmPwdLbl.getElement().setId("lblConfirmPwdLbl");
			confirmPwdLbl.getElement().setAttribute("alt",i18n.GL0427());
			confirmPwdLbl.getElement().setAttribute("title",i18n.GL0427());

			sendMailBtnUc.setText(i18n.GL0141());
			sendMailBtnUc.getElement().setAttribute("alt",i18n.GL0141());
			sendMailBtnUc.getElement().setAttribute("title",i18n.GL0141());

			resetPwdCancelAnr.setText(i18n.GL0142());
			resetPwdCancelAnr.getElement().setAttribute("alt",i18n.GL0142());
			resetPwdCancelAnr.getElement().setAttribute("title",i18n.GL0142());

			newPwdValidationUc.getElement().setId("errlblNewPwdValidationUc");

			resetConfirmPwdTxtBox.getElement().setPropertyString("type", "password");
			resetNewPwdTxtBox.getElement().setPropertyString("type", "password");
			resetConfirmPwdTxtBox.getElement().setId("txtConfirmPwd");
			resetNewPwdTxtBox.getElement().setId("txtNewPwd");
			resetNewPwdTxtBox.getElement().setAttribute("style", "width:60% !important;");
			resetConfirmPwdTxtBox.getElement().setAttribute("style", "width:60% !important;");
			newPwdValidationUc.setStyleName("passwordErrorLabel");
			newPwdValidationUc.setVisible(false);
			confirmPwdValidationUc.getElement().setId("errlblConfirmPwdValidationUc");
			confirmPwdValidationUc.setStyleName("passwordErrorLabel");
			confirmPwdValidationUc.setVisible(false);
			sendMailBtnUc.getElement().setId("btnSave");
			resetPwdCancelAnr.getElement().setId("lnkCancel");
			resetNewPwdTxtBox.addFocusHandler(new OnNewPasswordFocus());
			resetConfirmPwdTxtBox.addFocusHandler(new OnConfirmPasswordFocus());

			Window.enableScrolling(false);
			AppClientFactory.getEventBus().fireEvent(new SetHeaderZIndexEvent(99, false));
		}
	}

	/**
	 * Check the password reset token has expired or not if not create success popup with their user name
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("sendMailBtnUc")
	public void onCancelClick(ClickEvent clickEvent) {
		if (validatePassword()) {
			tokenExpireErrorLabel.setText("");
			String cryptoData = StringUtil.getCryptoData(this.getresetConfirmPwd());
			AppClientFactory.getInjector().getUserService().resetCredential(resetToken,cryptoData,homeEndPoint,new SimpleAsyncCallback<Map<String, Object>>() {
				@Override
				public void onSuccess(Map<String, Object> result) {
					if(result!=null){
						int httpStatusCode = result.get("statusCode") != null ? Integer.parseInt(result.get("statusCode").toString()) : 200;
						String errorCode = result.get("errorCode") != null ? result.get("errorCode").toString() : "";
						String statusMessage = result.get("statusMessage") != null ?  result.get("statusMessage").toString() : "";
						if (httpStatusCode==400 && errorCode.equalsIgnoreCase("400-GL0074")) {
							resetNewPwdTxtBox.addStyleName("resetPwdTextError");
							newPwdValidationUc.setVisible(true);
							newPwdValidationUc.setText(i18n.GL0078());
						}else if(httpStatusCode==400 && errorCode.equalsIgnoreCase("400") && statusMessage.contains("tokenExpired")){
							tokenExpireErrorLabel.setText(i18n.GL0100());
						}else{
							Window.enableScrolling(true);
							AppClientFactory.getEventBus().fireEvent(new SetHeaderZIndexEvent(0, true));
							tokenExpireErrorLabel.setText("");
							appPopUp.hide();
							new ResetPwdSuccessVc(result.get("username").toString());
						}
					}else{
						Window.enableScrolling(true);
						AppClientFactory.getEventBus().fireEvent(new SetHeaderZIndexEvent(0, true));
						tokenExpireErrorLabel.setText("");
						appPopUp.hide();
					}
				}
			});
		}
	}

	/**
	 * Takes to reset password link
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("resetPwdCancelAnr")
	public void onFormSubmit(ClickEvent clickEvent) {
		Window.enableScrolling(true);
		tokenExpireErrorLabel.setText("");
		AppClientFactory.getEventBus().fireEvent(new SetHeaderZIndexEvent(0, true));
		appPopUp.hide();
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
	}

	/**
	 * @return password
	 */
	protected String getresetConfirmPwd() {
		return resetConfirmPwdTxtBox.getText();
	}

	/**
	 * Validate the password , that contain all requirement of password text
	 * @return true or false
	 */
	public boolean validatePassword() {
		String newPassword = resetNewPwdTxtBox.getText();
		String confirmPassword = resetConfirmPwdTxtBox.getText();
		boolean isValid = true;
		try {
			boolean pwdChck =checkPassword(newPassword);
			if ((newPassword == null || (newPassword != null && newPassword
					.isEmpty()))) {
				newPwdValidationUc.setVisible(true);
				resetNewPwdTxtBox.addStyleName("resetPwdTextError");
				newPwdValidationUc.setText(StringUtil.generateMessage(i18n.GL0070(),
						"Password"));
				newPwdValidationUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0070(),
						"Password"));
				newPwdValidationUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0070(),
						"Password"));

				isValid = false;
			}

			else if ((confirmPassword == null || (confirmPassword != null && confirmPassword
					.isEmpty()))) {
				confirmPwdValidationUc.setVisible(true);
				resetConfirmPwdTxtBox.addStyleName("resetPwdTextError");
				confirmPwdValidationUc.setText(StringUtil.generateMessage(i18n.GL0070(),
						"Confirm Password"));
				confirmPwdValidationUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0070(),
						"Confirm Password"));
				confirmPwdValidationUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0070(),
						"Confirm Password"));
				isValid = false;
			}

			else if (newPassword.length() > 0 && newPassword.length() < 5) {
				newPwdValidationUc.setVisible(true);
				resetNewPwdTxtBox.addStyleName("resetPwdTextError");
				newPwdValidationUc.setText(StringUtil.generateMessage(i18n.GL0071(),
						"Password", "5"));
				newPwdValidationUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0071(),
						"Password", "5"));
				newPwdValidationUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0071(),
						"Password", "5"));
				isValid = false;
			}

			else if (newPassword != null && newPassword.length() >= 14) {
				resetNewPwdTxtBox.addStyleName("resetPwdTextError");
				newPwdValidationUc.setText(StringUtil.generateMessage(i18n.GL0072(), "Password", "<= 14"));
				newPwdValidationUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0072(), "Password", "<= 14"));
				newPwdValidationUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0072(), "Password", "<= 14"));
				newPwdValidationUc.setVisible(true);
				isValid = false;
			}
			else if (!pwdChck) {
				if(isDigiSpclChars){
					isDigiSpclChars=false;
					resetNewPwdTxtBox.addStyleName("resetPwdTextError");
					newPwdValidationUc.setText(StringUtil.generateMessage(i18n.GL0073(),"Password"));
					newPwdValidationUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0073(),"Password"));
					newPwdValidationUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0073(),"Password"));
					newPwdValidationUc.setVisible(true);
				}else{
					resetNewPwdTxtBox.addStyleName("resetPwdTextError");
					newPwdValidationUc.setText(StringUtil.generateMessage(i18n.GL0073(),"Password"));
					newPwdValidationUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0073(),"Password"));
					newPwdValidationUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0073(),"Password"));
					newPwdValidationUc.setVisible(true);
				}
				isValid = false;
			}
			else if ((!confirmPassword.equals(newPassword))) {
				confirmPwdValidationUc.setVisible(true);
				resetConfirmPwdTxtBox.addStyleName("resetPwdTextError");
				confirmPwdValidationUc.setText(StringUtil.generateMessage(i18n.GL0069(),
						"Passwords"));
				confirmPwdValidationUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0069(),
						"Passwords"));
				confirmPwdValidationUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0069(),
						"Passwords"));
				isValid = false;
			}

			else if ((newPassword != null && !newPassword.isEmpty())
					&& !pwdChck && newPassword.length() > 5
					&& newPassword.length() <= 14
					&& !newPassword.equalsIgnoreCase("PASSWORD")) {
				newPwdValidationUc.setVisible(true);
				resetNewPwdTxtBox.addStyleName("resetPwdTextError");
				newPwdValidationUc.setText(StringUtil.generateMessage(i18n.GL0073(),"Password"));
				newPwdValidationUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0073(),"Password"));
				newPwdValidationUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0073(),"Password"));
				isValid = false;
			}

		} catch (Exception e) {
			AppClientFactory.printSevereLogger("ResetPasswordVc validatePassword:::"+e);
		}
		return isValid;
	}
	/**
	 * @author Search Team
	 * Focus on new password text box
	 *
	 */
	private class OnNewPasswordFocus implements FocusHandler {
		@Override
		public void onFocus(FocusEvent event) {
			if (newPwdValidationUc.isVisible()) {
				newPwdValidationUc.setVisible(false);
				resetNewPwdTxtBox.removeStyleName("resetPwdTextError");
			}
		}
	}

	/**
	 * @author Search Team
	 * Focus on confirm password text box
	 *
	 */
	private class OnConfirmPasswordFocus implements FocusHandler {
		@Override
		public void onFocus(FocusEvent event) {
			if (confirmPwdValidationUc.isVisible()) {
				confirmPwdValidationUc.setVisible(false);
				resetConfirmPwdTxtBox.removeStyleName("resetPwdTextError");
			}
		}
	}

	private  boolean checkPassword(String pwd) {
		boolean isLetterIncluded=false,isDigiIncluded=false,isSpecialCharIncluded=false,isValidPwd=false;
		boolean isLetters=false,isDigits=false,isSpclChars=false;
		String digiOnly="";
		String letterOnly="";
		String spclCharsOnly="";
		for(int i=0;i<pwd.length();i++)
		{
			if(Character.isLetter(pwd.charAt(i))){
				isLetterIncluded=true;
				letterOnly+=pwd.charAt(i);
			}
			if(!Character.isLetterOrDigit(pwd.charAt(i))){
				isSpecialCharIncluded=true;
			}
			if(Character.isDigit(pwd.charAt(i))){
				isDigiIncluded=true;
				digiOnly+=pwd.charAt(i);
			}
		}
		if(isLetterIncluded){
			if(isDigiIncluded){
				isValidPwd=true;
				isDigiIncluded=false;
				isSpecialCharIncluded=false;
			}else{
				isValidPwd=false;
			}
		}else{
			isValidPwd=false;
			isDigiSpclChars=true;
		}
		return isValidPwd;
	}
}
