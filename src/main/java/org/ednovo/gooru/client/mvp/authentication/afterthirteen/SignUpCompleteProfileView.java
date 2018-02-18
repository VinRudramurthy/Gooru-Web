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
package org.ednovo.gooru.client.mvp.authentication.afterthirteen;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.authentication.SignUpCBundle;
import org.ednovo.gooru.client.mvp.authentication.uc.SignUpDontWorryView;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.uc.ErrorLabelUc;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewCloseHandler;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
/**
 *
 * @fileName : SignUpCompleteProfileView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class SignUpCompleteProfileView extends
		PopupViewWithUiHandlers<SignUpCompleteProfileUiHandler> implements
		IsSignUpCompleteProfile {

	private static SignUpCompleteProfileViewUiBinder uiBinder = GWT
			.create(SignUpCompleteProfileViewUiBinder.class);

	interface SignUpCompleteProfileViewUiBinder extends
			UiBinder<Widget, SignUpCompleteProfileView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField(provided = true)
	SignUpCBundle res;
	@UiField
	Label lblTitle, lblHeading, lblSubHeading, lblchangePassword,
			userName,lblUpdating,quriesText,pleaseContactText;
	@UiField
	Anchor  lblCancel;
	@UiField
	Image profileImage;
	@UiField Anchor supportLink;
	@UiField
	TextBoxWithPlaceholder txtFirstName, txtlastName, txtPassword,
			txtConfirmPassword;
	@UiField
	TextArea txtAreaAbout;
	@UiField
	Button btnUpdateProfileLater, btnSubmit, btnEditImage;
	@UiField
	HTMLPanel passwordContainer,panelSignUp;
	@UiField
	ErrorLabelUc lastNameValidUc, firstNameValidUc, passwordValidUc;
	private AppPopUp appPopUp;
	private static final String PWD_PATTERN = "[0-9]|[$@!#*%^/[/]}{()_&-+=.,<>;\\|]";

	@Inject
	public SignUpCompleteProfileView(EventBus eventBus) {
		super(eventBus);
		this.res = SignUpCBundle.INSTANCE;
		res.css().ensureInjected();
	}

	@Override
	public void displayView() {
		appPopUp = new AppPopUp(i18n.GL0697());
		appPopUp.setContent(uiBinder.createAndBindUi(this));
		appPopUp.setGlassEnabled(true);
		appPopUp.addStyleName(SignUpCBundle.INSTANCE.css().popupBackground());
		appPopUp.setGlassStyleName(SignUpCBundle.INSTANCE.css().signUpPopUpGlassCss());

		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));

		setUiAndIds();
		appPopUp.center();
	}
	/**
	 *
	 * @function setUiAndIds
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setUiAndIds() {
		lblTitle.setText(i18n.GL0481()
				+ i18n.GL_SPL_EXCLAMATION());
		lblTitle.getElement().setId("lblTitle");
		lblTitle.getElement().setAttribute("alt",i18n.GL0481());
		lblTitle.getElement().setAttribute("title",i18n.GL0481());

		lblCancel.getElement().setId("lblCancel");
		lblCancel.getElement().setAttribute("alt","");
		lblCancel.getElement().setAttribute("title","");

		panelSignUp.getElement().setId("pnlSignUp");
		panelSignUp.getElement().setAttribute("alt","");
		panelSignUp.getElement().setAttribute("title","");

		lblHeading.setText(i18n.GL0492()
				+ i18n.GL_SPL_EXCLAMATION());
		lblHeading.getElement().setId("lblCompleteYourProfile");
		lblHeading.getElement().setAttribute("alt",i18n.GL0492());
		lblHeading.getElement().setAttribute("title",i18n.GL0492());

		lblSubHeading.setText(i18n.GL0493());
		lblSubHeading.getElement().setId("lblSubHeading");
		lblSubHeading.getElement().setAttribute("alt",i18n.GL0493());
		lblSubHeading.getElement().setAttribute("title",i18n.GL0493());

		profileImage.setUrl("images/signup/user.png");
		profileImage.getElement().setId("imgProfileImage");
		profileImage.getElement().setAttribute("alt","Profile Image");
		profileImage.getElement().setAttribute("title","Profile Image");

		lblchangePassword.setText(i18n.GL0494());
		lblchangePassword.getElement().setId("lblchangePassword");
		lblchangePassword.getElement().setAttribute("alt",i18n.GL0494());
		lblchangePassword.getElement().setAttribute("title",i18n.GL0494());

		txtFirstName.setPlaceholder(i18n.GL1140());
		txtlastName.setPlaceholder(i18n.GL1141());
		StringUtil.setAttributes(txtlastName, true);
		StringUtil.setAttributes(txtFirstName, true);

		txtAreaAbout.getElement().setAttribute("placeholder",
				i18n.GL1142());
		txtAreaAbout.getElement().setId("tatAbout");
		txtAreaAbout.getElement().setAttribute("alt","");
		txtAreaAbout.getElement().setAttribute("title","");
		StringUtil.setAttributes(txtAreaAbout, true);

		txtPassword.setPlaceholder(i18n.GL1143());
		txtPassword.getElement().setId("txtPassword");
		txtPassword.getElement().setAttribute("alt","");
		txtPassword.getElement().setAttribute("title","");

		txtConfirmPassword.setPlaceholder(i18n.GL1144());
		txtConfirmPassword.getElement().setId("txtConfirmPassword");
		txtConfirmPassword.getElement().setAttribute("alt","");
		txtConfirmPassword.getElement().setAttribute("title","");

		btnUpdateProfileLater.setText(i18n.GL0495());
		btnUpdateProfileLater.getElement().setId("btnUpdateProfileLater");
		btnUpdateProfileLater.getElement().setAttribute("alt",i18n.GL0495());
		btnUpdateProfileLater.getElement().setAttribute("title",i18n.GL0495());

		btnSubmit.setText(i18n.GL0486());
		btnSubmit.getElement().setId("btnSubmit");
		btnSubmit.getElement().setAttribute("alt",i18n.GL0486());
		btnSubmit.getElement().setAttribute("title",i18n.GL0486());

		passwordContainer.setVisible(false);
		passwordContainer.getElement().setId("pnlPassWordContainer");
		passwordContainer.getElement().setAttribute("alt","");
		passwordContainer.getElement().setAttribute("title","");

		btnEditImage.setText(i18n.GL0138());
		btnEditImage.getElement().setId("btnEditImage");
		btnEditImage.getElement().setAttribute("alt",i18n.GL0138());
		btnEditImage.getElement().setAttribute("title",i18n.GL0138());

		btnSubmit.getElement().setAttribute("style", "margin-left: 10px");

		userName.setText(AppClientFactory.getLoggedInUser().getUsername());
		userName.getElement().setId("lblUserName");
		userName.getElement().setAttribute("alt",AppClientFactory.getLoggedInUser().getUsername());
		userName.getElement().setAttribute("title",AppClientFactory.getLoggedInUser().getUsername());

		txtFirstName.getElement().setId("txtFirstName");
		txtFirstName.getElement().setAttribute("alt","");
		txtFirstName.getElement().setAttribute("title","");

		txtlastName.getElement().setId("txtLastName");
		txtlastName.getElement().setAttribute("alt","");
		txtlastName.getElement().setAttribute("title","");

		firstNameValidUc.getElement().setId("errlblFirstName");
		firstNameValidUc.getElement().setAttribute("alt",StringUtil.generateMessage(
				i18n.GL0072(), "First name", "<=20"));
		firstNameValidUc.getElement().setAttribute("title",StringUtil.generateMessage(
				i18n.GL0072(), "First name", "<=20"));

		lastNameValidUc.getElement().setId("errlblLastName");
		lastNameValidUc.getElement().setAttribute("alt",StringUtil.generateMessage(
				i18n.GL0072(), "Last name", "<= 20"));
		lastNameValidUc.getElement().setAttribute("title",StringUtil.generateMessage(
				i18n.GL0072(), "Last name", "<= 20"));


		quriesText.setText(i18n.GL1139()+i18n.GL_GRR_COMMA());
		quriesText.getElement().setId("lblQuriesText");
		quriesText.getElement().setAttribute("alt",i18n.GL1139());
		quriesText.getElement().setAttribute("title",i18n.GL1139());

		pleaseContactText.setText(i18n.GL1145());
		pleaseContactText.getElement().setId("lblPleaseContactText");
		pleaseContactText.getElement().setAttribute("alt",i18n.GL1145());
		pleaseContactText.getElement().setAttribute("title",i18n.GL1145());
		pleaseContactText.getElement().setAttribute("style", "width: 80%;position: absolute;");

		supportLink.setText(i18n.GL0299());
		supportLink.getElement().setId("lnkSupportLink");
		supportLink.getElement().setAttribute("alt",i18n.GL0299());
		supportLink.getElement().setAttribute("title",i18n.GL0299());
		supportLink.setHref(i18n.GL1055());
		supportLink.getElement().setAttribute("style","margin-left:100px;");

		passwordValidUc.setVisible(false);
		passwordValidUc.getElement().setId("errlblPasswordValid");
		passwordValidUc.getElement().setAttribute("alt",i18n.GL0446());
		passwordValidUc.getElement().setAttribute("title",i18n.GL0446());

		lblUpdating.setVisible(false);
		lblUpdating.getElement().setId("lblUpdatingText");
		lblUpdating.getElement().setAttribute("alt",i18n.GL1138());
		lblUpdating.getElement().setAttribute("title",i18n.GL1138());

		profileImage.setWidth("96px");
		profileImage.setHeight("98px");
		txtPassword.addKeyUpHandler(new OnKeyUpHandler());
		txtFirstName.addKeyUpHandler(new OnKeyUpHandler());
		txtlastName.addKeyUpHandler(new OnKeyUpHandler());
		txtConfirmPassword.addKeyUpHandler(new OnKeyUpHandler());
	}

	@UiHandler("lblCancel")
	public void onClickLblCancel(ClickEvent event) {
		MixpanelUtil.close_signUp();
		Window.enableScrolling(true);
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.PREVIEW_PLAY)){

		}else{
			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		}

	}

	@UiHandler("lblchangePassword")
	public void onClicklblchangePassword(ClickEvent event) {
		passwordContainer.setVisible(true);
	}

	@UiHandler("btnEditImage")
	public void onClickbtnEditImage(ClickEvent event) {
		getUiHandlers().showUploadProfileImageWidget();
	}

	@UiHandler("btnSubmit")
	public void onClickbtnSubmit(ClickEvent event) {
		if (validateUserInput()) {
			MixpanelUtil.Registration_turns13_submit_profile();
			appPopUp.hide();
			lblUpdating.setText(i18n.GL1138());
			lblUpdating.setVisible(true);
			btnSubmit.setVisible(false);
			getUiHandlers().updateProfile(txtFirstName.getText(),
					txtlastName.getText(), txtAreaAbout.getText(),txtConfirmPassword.getText()
			);

		}
	}

	@UiHandler("btnUpdateProfileLater")
	public void onClickbtnUpdateProfileLater(ClickEvent event) {
		appPopUp.hide();
		SignUpDontWorryView signUpDontWorryView = new SignUpDontWorryView();
		signUpDontWorryView.show();

	}

	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	@Override
	public void center() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void setAutoHideOnNavigationEventEnabled(boolean autoHide) {

	}

	@Override
	public void setCloseHandler(PopupViewCloseHandler popupViewCloseHandler) {

	}

	@Override
	public void setPosition(int left, int top) {

	}

	@Override
	public void show() {

	}

	@Override
	public void addToSlot(Object slot, Widget content) {

	}

	@Override
	public void removeFromSlot(Object slot, Widget content) {

	}

	@Override
	public void setInSlot(Object slot, Widget content) {

	}

	@Override
	public void reset() {

	}

	@Override
	public void onLoad() {

	}

	@Override
	public void onUnload() {

	}

	@Override
	public void setUpdateProfileImage(String imageUrl) {
		profileImage.setUrl(imageUrl + "?" + Math.random());
		profileImage.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				profileImage.setUrl("images/signup/user.png");

			}
		});
	}
	/**
	 *
	 * @function validateUserInput
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : boolean
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public boolean validateUserInput() {
		String firstName = txtFirstName.getText().trim();
		String lastName = txtlastName.getText().trim();
		String password = txtPassword.getText().trim();
		boolean isValid = true;
		String confirmPassword = txtConfirmPassword.getText().trim();

		// TODO Validate password fields
		try {
			RegExp reg = RegExp.compile(PWD_PATTERN, "gi");
			if (!password.equalsIgnoreCase("") && password.length() < 5) {
				txtPassword.addStyleName(res.css().errorMsgDisplay());
				passwordValidUc.setText(StringUtil.generateMessage(
						i18n.GL0071(), "Password", "5"));
				passwordValidUc.setVisible(true);
				isValid = false;
			}
			if (!password.equalsIgnoreCase("") && password.length() >= 14) {
				txtPassword.addStyleName(res.css().errorMsgDisplay());
				passwordValidUc.setText(StringUtil.generateMessage(
						i18n.GL0072(), "Password", "<= 14"));
				passwordValidUc.setVisible(true);
				isValid = false;
			}
			 if (password.equalsIgnoreCase("PASSWORD")) {
			  txtPassword.addStyleName(res.css().errorMsgDisplay());
			  passwordValidUc
			  .setText(StringUtil.generateMessage(i18n.GL0076(),
			  "Password"));
			  passwordValidUc.setVisible(true);
			  isValid = false;
			  }

			 if ((!password.equalsIgnoreCase("") && !password.isEmpty())
					&& !reg.test(password) && password.length() >= 5
					&& password.length() <= 14
					&& !password.equalsIgnoreCase("PASSWORD")) {
				txtPassword.addStyleName(res.css().errorMsgDisplay());
				passwordValidUc.setText(StringUtil.generateMessage(
						i18n.GL0073(), "Password"));
				passwordValidUc.setVisible(true);
				isValid = false;
			}
		} catch (Exception e) {
			AppClientFactory.printSevereLogger("SignUpcompleteProfileView : validateUserInput :"+e.getMessage());
		}
		RegExp reg = RegExp.compile(PWD_PATTERN, "gi");
		if (firstName.length() > 20) {
			txtFirstName.getElement().addClassName(res.css().errorMsgDisplay());
			firstNameValidUc.setText(StringUtil.generateMessage(
					i18n.GL0072(), "First name", "<=20"));
			firstNameValidUc.setVisible(true);
			isValid = false;
		}

		 if (lastName.length() > 20) {
			txtlastName.getElement().addClassName(res.css().errorMsgDisplay());
			lastNameValidUc.setText(StringUtil.generateMessage(
					i18n.GL0072(), "Last name", "<= 20"));
			lastNameValidUc.setVisible(true);
			isValid = false;
		}

		 if (!password.equalsIgnoreCase(confirmPassword)) {
			txtConfirmPassword.addStyleName(res.css().errorMsgDisplay());
			txtPassword.addStyleName(res.css().errorMsgDisplay());
			passwordValidUc.setText(i18n.GL0446());
			passwordValidUc.setVisible(true);
			isValid = false;
		}
		 if (!reg.test(password) && password.length() >= 5
				&& password.length() <= 14) {
			passwordValidUc.setText(StringUtil.generateMessage(
					i18n.GL0073(), "Password"));
			passwordValidUc.setVisible(true);
			isValid = false;
		}
		 return isValid;
	}

	@Override
	public Label getUpdateButton() {
		return lblUpdating;
	}
	/**
	 *
	 * @fileName : SignUpCompleteProfileView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 06-Dec-2014
	 *
	 * @Author tumbalam
	 *
	 * @Reviewer:
	 */
	private class OnKeyUpHandler implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			passwordValidUc.setVisible(false);
			if (event.getSource() == txtFirstName) {
				txtFirstName.removeStyleName(res.css().errorMsgDisplay());
				firstNameValidUc.setVisible(false);
			} else if (event.getSource() == txtlastName) {
				txtlastName.removeStyleName(res.css().errorMsgDisplay());
				lastNameValidUc.setVisible(false);
			} else if (event.getSource() == txtPassword) {
				txtPassword.removeStyleName(res.css().errorMsgDisplay());
				passwordValidUc.setVisible(false);
			} else if (event.getSource() == txtConfirmPassword) {
				txtConfirmPassword.removeStyleName(res.css().errorMsgDisplay());
				passwordValidUc.setVisible(false);
			}
		}

	}

}
