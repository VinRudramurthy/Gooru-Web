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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.mvp.classpages.assignments.AddAssignmentContainerCBundle;
import org.ednovo.gooru.client.mvp.faq.CopyRightPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsAndPolicyVc;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.uc.BlueButtonUc;
import org.ednovo.gooru.client.uc.DateBoxUc;
import org.ednovo.gooru.client.uc.ErrorLabelUc;
import org.ednovo.gooru.client.uc.GenderRadioButton;
import org.ednovo.gooru.client.uc.ValidTextUc;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

/**
 * @author Search Team
 *
 */
public class UserRegistrationView extends
		PopupViewWithUiHandlers<UserRegistrationUiHandlers> implements
		IsUserRegistrationView{

	private static UserRegistrationViewUiBinder uiBinder = GWT
			.create(UserRegistrationViewUiBinder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	Anchor cancelAnr;

	@UiField
	ListBox userCategoryLisBox;

	@UiField
	BlueButtonUc updateUserDetailsUc;

	@UiField
	ValidTextUc firstNameFieldUc, lastNameFieldUc;

	@UiField
	TextBox userNameFieldTxtBox;

	@UiField
	PasswordTextBox passwordFieldTxtBox, confirmPasswordFieldTxtBox;

	@UiField
	ErrorLabelUc passwordValidUc, confirmPasswordValidUc, genderValidUc,
			userNameValidUc, dateValidationUc;

	@UiField
	FocusPanel genderFocusFocPanel;

	@UiField
	HTML welcomeMessageHtml;

	@UiField
	Label accountTypeFieldLbl, accountInformationLbl, userEmailFieldLbl,fName,lName,uName,pWord,cPword,
			bdLbl,emailText,genderText,aboutMe,conditionsText,andText,gooruText;

	@UiField
	FlowPanel birthdayFloPanel, firstnameFloPanel, lastnameFloPanel,
			userCategoryFloPanel, termsAndConditionFloPanel,
			userRegisterFloPanel, genderFieldsFloPanel;

	@UiField
	SimplePanel dateSimPanel;

	@UiField
	GenderRadioButton female, male, other, donot;

	private DateBoxUc dateBoxUc;

	protected AppPopUp appPopUp;

	private String registartionType;

	private static final String PWD_PATTERN = "[0-9]|[$@!#*%^/[/]}{()_&-+=.,<>;\\|]";

	@UiField
	Anchor termsAndConditionsAnr, copyRightPolicyAnr;

	private TermsAndPolicyVc termsAndPolicyVc;

	private CopyRightPolicyVc copyRightPolicy;

	interface UserRegistrationViewUiBinder extends
			UiBinder<Widget, UserRegistrationView> {
	}

	/**
	 * Class constructor, creates popup , focus and blur events
	 *
	 * @param eventBus
	 *            {@link EventBus}
	 */
	@Inject
	public UserRegistrationView(EventBus eventBus) {
		super(eventBus);
		RegisterCBundle.INSTANCE.css().ensureInjected();
		appPopUp = new AppPopUp();
		appPopUp.setStyleName(RegisterCBundle.INSTANCE.css().registerPopup());
		appPopUp.setContent((i18n.GL1207()), uiBinder.createAndBindUi(this));
		appPopUp.setGlassStyleName(RegisterCBundle.INSTANCE.css()
				.registerPopupGlassPanel());

		userRegisterFloPanel.getElement().setId("fpnlUserRegisterFloPanel");
		welcomeMessageHtml.getElement().setId("htmlWelcomeMessageHtml");
		firstnameFloPanel.getElement().setId("fpnlFirstnameFloPanel");
		lastnameFloPanel.getElement().setId("fpnlLastnameFloPanel");
		birthdayFloPanel.getElement().setId("fpnlBirthdayFloPanel");
		dateSimPanel.getElement().setId("spnlDateSimPanel");
		dateValidationUc.getElement().setId("errlblDateValidationUc");
		genderFocusFocPanel.getElement().setId("fouspnlGenderFocusFocPanel");
		genderFieldsFloPanel.getElement().setId("fouspnlGenderFieldsFloPanel");
		genderValidUc.getElement().setId("errlblGenderValidUc");
		userCategoryFloPanel.getElement().setId("fpnlUserCategoryFloPanel");
		userCategoryLisBox.getElement().setId("lbUserCategoryLisBox");
		termsAndConditionFloPanel.getElement().setId("fpnlTermsAndConditionFloPanel");


		accountTypeFieldLbl.setText(i18n.GL0807());
		accountTypeFieldLbl.getElement().setId("lblAccountTypeFieldLbl");
		accountTypeFieldLbl.getElement().setAttribute("alt",i18n.GL0807());
		accountTypeFieldLbl.getElement().setAttribute("title",i18n.GL0807());

		accountInformationLbl.setText(i18n.GL1198());
		accountInformationLbl.getElement().setId("lblAccountInformationLbl");
		accountInformationLbl.getElement().setAttribute("alt",i18n.GL1198());
		accountInformationLbl.getElement().setAttribute("title",i18n.GL1198());

		fName.setText(i18n.GL0424());
		fName.getElement().setId("lblFName");
		fName.getElement().setAttribute("alt",i18n.GL0424());
		fName.getElement().setAttribute("title",i18n.GL0424());

		lName.setText(i18n.GL0425());
		lName.getElement().setId("lblLName");
		lName.getElement().setAttribute("alt",i18n.GL0425());
		lName.getElement().setAttribute("title",i18n.GL0425());

		uName.setText(i18n.GL1035());
		uName.getElement().setId("lblUName");
		uName.getElement().setAttribute("alt",i18n.GL1035());
		uName.getElement().setAttribute("title",i18n.GL1035());

		pWord.setText(i18n.GL0204());
		pWord.getElement().setId("lblPWord");
		pWord.getElement().setAttribute("alt",i18n.GL0204());
		pWord.getElement().setAttribute("title",i18n.GL0204());

		cPword.setText(i18n.GL0427());
		cPword.getElement().setId("lblCPword");
		cPword.getElement().setAttribute("alt",i18n.GL0427());
		cPword.getElement().setAttribute("title",i18n.GL0427());

		bdLbl.setText(i18n.GL0211());
		bdLbl.getElement().setId("lblBdLbl");
		bdLbl.getElement().setAttribute("alt",i18n.GL0211());
		bdLbl.getElement().setAttribute("title",i18n.GL0211());

		emailText.setText(i18n.GL0212());
		emailText.getElement().setId("lblEmailText");
		emailText.getElement().setAttribute("alt",i18n.GL0212());
		emailText.getElement().setAttribute("title",i18n.GL0212());

		genderText.setText(i18n.GL0809());
		genderText.getElement().setId("lblGenderText");
		genderText.getElement().setAttribute("alt",i18n.GL0809());
		genderText.getElement().setAttribute("title",i18n.GL0809());

		female.setText(i18n.GL0811());
		female.getElement().setId("rdFemale");
		female.getElement().setAttribute("alt",i18n.GL0811());
		female.getElement().setAttribute("title",i18n.GL0811());

		male.setText(i18n.GL0810());
		male.getElement().setId("rdMale");
		male.getElement().setAttribute("alt",i18n.GL0810());
		male.getElement().setAttribute("title",i18n.GL0810());

		other.setText(i18n.GL1047());
		other.getElement().setId("rdOther");
		other.getElement().setAttribute("alt",i18n.GL1047());
		other.getElement().setAttribute("title",i18n.GL1047());

		donot.setText(i18n.GL1199());
		donot.getElement().setId("rdDoNot");
		donot.getElement().setAttribute("alt",i18n.GL1199());
		donot.getElement().setAttribute("title",i18n.GL1199());

		aboutMe.setText(i18n.GL1200());
		aboutMe.getElement().setId("lblAboutMe");
		aboutMe.getElement().setAttribute("alt",i18n.GL1200());
		aboutMe.getElement().setAttribute("title",i18n.GL1200());

		conditionsText.setText(i18n.GL1201()+" ");
		conditionsText.getElement().setId("lblConditionsText");
		conditionsText.getElement().setAttribute("alt",i18n.GL1201());
		conditionsText.getElement().setAttribute("title",i18n.GL1201());

		termsAndConditionsAnr.setText(i18n.GL0297()+" "+i18n.GL_GRR_AND()+" "+i18n.GL0452());
		termsAndConditionsAnr.getElement().setAttribute("alt",i18n.GL0297()+" "+i18n.GL_GRR_AND()+" "+i18n.GL0452());
		termsAndConditionsAnr.getElement().setAttribute("title",i18n.GL0297()+" "+i18n.GL_GRR_AND()+" "+i18n.GL0452());

		andText.setText("  "+i18n.GL_GRR_AND()+"  ");
		andText.getElement().setId("lblAndText");
		andText.getElement().setAttribute("alt","  "+i18n.GL_GRR_AND()+"  ");
		andText.getElement().setAttribute("title","  "+i18n.GL_GRR_AND()+"  ");

		copyRightPolicyAnr.setText(i18n.GL0421());
		copyRightPolicyAnr.getElement().setAttribute("alt",i18n.GL0421());
		copyRightPolicyAnr.getElement().setAttribute("title",i18n.GL0421());

		gooruText.setText(" "+i18n.GL_GRR_OF()+" "+i18n.GL1202());
		gooruText.getElement().setId("lblGooruText");
		gooruText.getElement().setAttribute("alt"," "+i18n.GL_GRR_OF()+" "+i18n.GL1202());
		gooruText.getElement().setAttribute("title"," "+i18n.GL_GRR_OF()+" "+i18n.GL1202());

		updateUserDetailsUc.setText(i18n.GL1203());
		updateUserDetailsUc.getElement().setAttribute("alt",i18n.GL1203());
		updateUserDetailsUc.getElement().setAttribute("title",i18n.GL1203());

		cancelAnr.setText(i18n.GL0142());
		cancelAnr.getElement().setAttribute("alt",i18n.GL0142());
		cancelAnr.getElement().setAttribute("title",i18n.GL0142());

		welcomeMessageHtml.setHTML(i18n.GL1211());
		welcomeMessageHtml.getElement().setAttribute("alt",i18n.GL1211());
		welcomeMessageHtml.getElement().setAttribute("title",i18n.GL1211());

		passwordFieldTxtBox.addFocusHandler(new OnPasswordFocus());
		dateBoxUc = new DateBoxUc(true, false, false);
		dateSimPanel.add(dateBoxUc);
		confirmPasswordValidUc.setVisible(false);
		passwordValidUc.setVisible(false);
		dateValidationUc.setVisible(false);
		dateValidationUc.setStyleName(RegisterCBundle.INSTANCE.css()
				.rightErrorLabel());
		genderValidUc.setVisible(false);
		genderValidUc.setStyleName(RegisterCBundle.INSTANCE.css()
				.rightErrorLabel());
		dateBoxUc.setStyleName("Uc-parentDateBox");
		dateBoxUc.getDateBox().setStyleName(
				"Uc-parentDateText");
		dateBoxUc.getDatePickerUc().setStyleName(
				"Uc-parentDatePickerContainer");
		dateBoxUc.addDomHandler(new OnDateFocus(), FocusEvent.getType());
		dateBoxUc.getDateBox().addFocusHandler(new OnDateFocus());
		dateBoxUc.getDoneButton().addClickHandler(new OnDoneClick());
		dateBoxUc.getDateBox().addBlurHandler(new OnDateBlur());
		genderFocusFocPanel.addFocusHandler(new OnGenderFocus());
		userNameValidUc.setVisible(false);
		userNameFieldTxtBox.setMaxLength(20);
		userNameFieldTxtBox.addBlurHandler(new OnUserNameBlur());
		userNameFieldTxtBox.addFocusHandler(new OnUserNameFocus());
		userNameFieldTxtBox.getElement().setId("txtUserName");
		StringUtil.setAttributes(userNameFieldTxtBox, true);
		firstNameFieldUc.getElement().setId("txtFirstName");
		lastNameFieldUc.getElement().setId("txtLastName");
		passwordFieldTxtBox.getElement().setId("txtpassword");
		confirmPasswordFieldTxtBox.getElement().setId("txtConfirmPassword");
		termsAndConditionsAnr.getElement().setId("lnkTermsAndConditions");
		copyRightPolicyAnr.getElement().setId("lnkCopyRightPolicy");
		updateUserDetailsUc.getElement().setId("btnRegister");
		cancelAnr.getElement().setId("lnkCancel");
		termsAndPolicyVc = new TermsAndPolicyVc(false) {

			@Override
			public void openParentPopup() {
				appPopUp.show();
			}
		};

		copyRightPolicy = new CopyRightPolicyVc() {

			@Override
			public void openParentPopup() {
				appPopUp.show();
			}
		};

		/**
		 * Added click handler for showing Terms ans Policy popup in footer
		 **/
		termsAndConditionsAnr.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				hide();
				if (termsAndPolicyVc == null) {
					termsAndPolicyVc = new TermsAndPolicyVc(false) {
						@Override
						public void openParentPopup() {
							Window.enableScrolling(false);
							AppClientFactory
									.fireEvent(new SetHeaderZIndexEvent(98,
											false));
							appPopUp.show();
						}
					};
				}
				termsAndPolicyVc.getElement().getStyle().setZIndex(999);
				termsAndPolicyVc.show();

				termsAndPolicyVc.center();
			}
		});

		copyRightPolicyAnr.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
				copyRightPolicy.show();
				copyRightPolicy.center();
			}
		});
	}

	/**
	 * Hide the popup and redirect to home page while clicking cancel
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("cancelAnr")
	public void onCancelClick(ClickEvent clickEvent) {
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
		appPopUp.hide();
	}

	/**
	 * Calls update user method
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("updateUserDetailsUc")
	public void onUpdateUserClick(ClickEvent clickEvent) {
		updateUserData();
	}

	public class OnUserNameFocus implements FocusHandler {

		@Override
		public void onFocus(FocusEvent event) {
			if (userNameValidUc.isVisible()) {
				userNameValidUc.setVisible(false);
				userNameFieldTxtBox.removeStyleName(RegisterCBundle.INSTANCE
						.css().userRegistrationError());
			}
		}

	}

	public class OnDateFocus implements FocusHandler {
		@Override
		public void onFocus(FocusEvent event) {
			if (dateValidationUc.isVisible()) {
				dateBoxUc
						.setStyleName("Uc-parentDateBox");
				dateBoxUc.getDateBox().setStyleName(
						"Uc-parentDateText");
				dateValidationUc.setVisible(false);
			}
		}

	}

	private class OnDoneClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (dateBoxUc.dateValidation()) {
				if (!(dateBoxUc.getValue() == null || dateBoxUc.getDateBox()
						.getText().isEmpty())
						&& dateBoxUc.hasValidateDate()) {
					Date date = dateBoxUc.getValue();

				} else {
					dateBoxUc.getDatePickerUc().hide();
				}
			}
		}
	}

	private class OnDateBlur implements BlurHandler {
		@Override
		public void onBlur(BlurEvent event) {
			dateBoxUc.removeStyleName(AddAssignmentContainerCBundle.INSTANCE
					.css().gooruDateBoxError());
			dateBoxUc.getDateBox().removeStyleName(
					AddAssignmentContainerCBundle.INSTANCE.css()
							.gooruDateError());
		}
	}

	private class OnUserNameBlur implements BlurHandler {
		@Override
		public void onBlur(BlurEvent event) {
			getUiHandlers().checkUserAvailability(
					userNameFieldTxtBox.getText(), "username");
		}
	}

	/**
	 * Update user details userName, firstName, lastName ,etc..
	 */
	private void updateUserData() {
		if (hasValidateData()) {
			Map<String, String> params = new HashMap<String, String>();
			if (!getRegistartionType().equalsIgnoreCase("Child")) {
				params.put("firstName", firstNameFieldUc.getUserText());
				params.put("lastName", lastNameFieldUc.getUserText());
				params.put("userrole", userCategoryLisBox
						.getValue(userCategoryLisBox.getSelectedIndex()));
			} else if (getRegistartionType().equalsIgnoreCase("Parent")) {
				params.put("dateOfBirth", dateBoxUc.getDateBox().getText());
			}
			params.put("username", userNameFieldTxtBox.getText());
			params.put("password", passwordFieldTxtBox.getText());
			params.put("gender", getSelectedGender(genderFieldsFloPanel));
			if (getRegistartionType().equalsIgnoreCase("Child")) {
				params.put("userName", userNameFieldTxtBox.getText());
				params.put("firstName", firstNameFieldUc.getUserText());
				params.put("userName", userNameFieldTxtBox.getText());
				getUiHandlers().registerChildUserAccount(params);
			} else {
				getUiHandlers().updateUserDetails(params);
			}
		}
	}

	private class OnPasswordFocus implements FocusHandler {
		@Override
		public void onFocus(FocusEvent event) {
			if (passwordValidUc.isVisible()
					|| confirmPasswordValidUc.isVisible()) {
				passwordFieldTxtBox.removeStyleName(RegisterCBundle.INSTANCE
						.css().userRegistrationError());
				confirmPasswordFieldTxtBox
						.removeStyleName(RegisterCBundle.INSTANCE.css()
								.userRegistrationError());

				confirmPasswordValidUc.setVisible(false);
				passwordValidUc.setVisible(false);
			}
		}
	}

	private class OnGenderFocus implements FocusHandler {
		@Override
		public void onFocus(FocusEvent event) {
			if (genderValidUc.isVisible()) {
				genderValidUc.setVisible(false);
			}
		}
	}

	/**
	 * @return true if password, userName, firstName,gender etc.. are satisfied
	 *         the required condition else false
	 */
	private boolean hasValidateData() {
		String firstName = firstNameFieldUc.getUserText();
		String lastName = lastNameFieldUc.getUserText();
		String userName = userNameFieldTxtBox.getText();
		String password = passwordFieldTxtBox.getText();
		String confirmPassword = confirmPasswordFieldTxtBox.getText();
		String dob = dateBoxUc.getDateBox().getText();
		Date dateOfBirth = dateBoxUc.getValue();
		int age = getAge(dateOfBirth);
		boolean isValid = true;
		try {
			RegExp reg = RegExp.compile(PWD_PATTERN, "gi");
			if (password == null || (password != null && password.isEmpty())) {
				passwordFieldTxtBox.addStyleName(RegisterCBundle.INSTANCE.css()
						.userRegistrationError());
				passwordValidUc.setText(StringUtil.generateMessage(i18n.GL0070(),
						"Password"));
				passwordValidUc.setVisible(true);
				isValid = false;
			}
			if (password != null && password.length() < 5) {
				passwordFieldTxtBox.addStyleName(RegisterCBundle.INSTANCE.css()
						.userRegistrationError());
				passwordValidUc.setText(StringUtil.generateMessage(i18n.GL0071(),
						"Password", "5"));
				passwordValidUc.setVisible(true);
				isValid = false;
			}
			if (password != null && password.length() >= 14) {
				passwordFieldTxtBox.addStyleName(RegisterCBundle.INSTANCE.css()
						.userRegistrationError());
				passwordValidUc.setText(StringUtil.generateMessage(i18n.GL0072(),
						"Password", "<= 14"));
				passwordValidUc.setVisible(true);
				isValid = false;
			}
			if (password.equalsIgnoreCase("PASSWORD")) {
				passwordFieldTxtBox.addStyleName(RegisterCBundle.INSTANCE.css()
						.userRegistrationError());
				passwordValidUc.setText(StringUtil.generateMessage(i18n.GL0076(),
						"Password"));
				passwordValidUc.setVisible(true);
				isValid = false;
			}
			if ((password != null && !password.isEmpty())
					&& !reg.test(password) && password.length() > 5
					&& password.length() <= 14
					&& !password.equalsIgnoreCase("PASSWORD")) {
				passwordFieldTxtBox.addStyleName(RegisterCBundle.INSTANCE.css()
						.userRegistrationError());
				passwordValidUc.setText(StringUtil.generateMessage(i18n.GL0073(),
						"Password"));
				passwordValidUc.setVisible(true);
				isValid = false;
			}
		} catch (Exception e) {
			AppClientFactory.printSevereLogger("UserRegistrationView hasValidateData:::"+e);
		}

		if ((getRegistartionType().equalsIgnoreCase(i18n.GL0074()) || getRegistartionType()
				.equalsIgnoreCase(i18n.GL0077()))
				&& (firstName == null || (firstName != null && firstName
						.isEmpty()))) {
			firstNameFieldUc.addTextStyle(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
			firstNameFieldUc.setErrText(StringUtil.generateMessage(i18n.GL0067(),
					"First name"));
			firstNameFieldUc.setErrorVisible(true);
			isValid = false;
		}
		if ((getRegistartionType().equalsIgnoreCase(i18n.GL0074()) || getRegistartionType()
				.equalsIgnoreCase(i18n.GL0077())) && (firstName.length() > 20)) {
			firstNameFieldUc.addTextStyle(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
			firstNameFieldUc.setErrText(StringUtil.generateMessage(i18n.GL0072(),
					"First name", "<=20"));
			firstNameFieldUc.setErrorVisible(true);
			isValid = false;
		}
		if ((getRegistartionType().equalsIgnoreCase(i18n.GL0074()) || getRegistartionType()
				.equalsIgnoreCase(i18n.GL0077()))
				&& (lastName == null || (lastName != null && lastName.isEmpty()))) {
			lastNameFieldUc.addTextStyle(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
			lastNameFieldUc.setErrText(StringUtil.generateMessage(i18n.GL0067(),
					"Last Name"));
			lastNameFieldUc.setErrorVisible(true);
			isValid = false;
		}
		if ((getRegistartionType().equalsIgnoreCase(i18n.GL0074()) || getRegistartionType()
				.equalsIgnoreCase(i18n.GL0077())) && (lastName.length() > 20)) {
			lastNameFieldUc.addTextStyle(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
			lastNameFieldUc.setErrText(StringUtil.generateMessage(i18n.GL0072(),
					"Last name", "<= 20"));
			lastNameFieldUc.setErrorVisible(true);
			isValid = false;
		}

		if ((getRegistartionType().equalsIgnoreCase("child") || getRegistartionType()
				.equalsIgnoreCase("child"))
				&& (firstName == null || (firstName != null && firstName
						.isEmpty()))) {
			firstNameFieldUc.addTextStyle(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
			firstNameFieldUc.setErrText(StringUtil.generateMessage(i18n.GL0067(),
					"First name"));
			firstNameFieldUc.setErrorVisible(true);
			isValid = false;
		}
		if ((getRegistartionType().equalsIgnoreCase("child") || getRegistartionType()
				.equalsIgnoreCase("child")) && (firstName.length() > 20)) {
			firstNameFieldUc.addTextStyle(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
			firstNameFieldUc.setErrText(StringUtil.generateMessage(i18n.GL0072(),
					"First name", "<=20"));
			firstNameFieldUc.setErrorVisible(true);
			isValid = false;
		}
		if ((getRegistartionType().equalsIgnoreCase("child") || getRegistartionType()
				.equalsIgnoreCase("child"))
				&& (lastName == null || (lastName != null && lastName.isEmpty()))) {
			lastNameFieldUc.addTextStyle(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
			lastNameFieldUc.setErrText(StringUtil.generateMessage(i18n.GL0067(),
					"Last Name"));
			lastNameFieldUc.setErrorVisible(true);
			isValid = false;
		}
		if ((getRegistartionType().equalsIgnoreCase("child") || getRegistartionType()
				.equalsIgnoreCase("child")) && (lastName.length() > 20)) {
			lastNameFieldUc.addTextStyle(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
			lastNameFieldUc.setErrText(StringUtil.generateMessage(i18n.GL0072(),
					"Last name", "<= 20"));
			lastNameFieldUc.setErrorVisible(true);
			isValid = false;
		}
		if (userName == null || (userName != null && userName.isEmpty())) {
			userNameFieldTxtBox.addStyleName(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
			userNameValidUc.setText(StringUtil.generateMessage(i18n.GL0070(),
					"Username"));
			userNameValidUc.setVisible(true);
			isValid = false;
		}
		if (userName.length() < 5) {
			userNameFieldTxtBox.addStyleName(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
			userNameValidUc.setText(StringUtil.generateMessage(i18n.GL0071(),
					"Username", "5"));
			userNameValidUc.setVisible(true);
			isValid = false;
		}
		if (userName.length() > 20) {
			userNameFieldTxtBox.addStyleName(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
			userNameValidUc.setText(StringUtil.generateMessage(i18n.GL0072(),
					"Username", "<=20"));
			userNameValidUc.setVisible(true);
			isValid = false;
		}
		if (getSelectedGender(genderFieldsFloPanel) == null) {
			genderValidUc.setText(i18n.GL0075());
			genderValidUc.setVisible(true);
			isValid = false;
		}
		if (confirmPassword == null
				|| (confirmPassword != null && confirmPassword.isEmpty())) {
			confirmPasswordFieldTxtBox.addStyleName(RegisterCBundle.INSTANCE
					.css().userRegistrationError());
			confirmPasswordValidUc.setText(StringUtil.generateMessage(i18n.GL0070(),
					"Confirm password"));
			confirmPasswordValidUc.setVisible(true);
			isValid = false;
		}
		if (!password.equalsIgnoreCase(confirmPassword)) {
			confirmPasswordFieldTxtBox.addStyleName(RegisterCBundle.INSTANCE
					.css().userRegistrationError());
			confirmPasswordValidUc.setText(StringUtil.generateMessage(i18n.GL0069(),
					"Password"));
			confirmPasswordValidUc.setVisible(true);
			isValid = false;
		}
		if (password.equalsIgnoreCase(userName)) {
			passwordFieldTxtBox.addStyleName(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
			passwordValidUc.setText(i18n.GL0078());
			passwordValidUc.setVisible(true);
			isValid = false;
		}

		if ((getRegistartionType().equalsIgnoreCase("parent"))
				&& (dob == null || (dob != null && dob.isEmpty()))) {
			dateBoxUc.addStyleName(RegisterCBundle.INSTANCE.css()
					.parentDateBoxError());
			dateBoxUc.getDateBox().setStyleName(
					RegisterCBundle.INSTANCE.css().parentDateTextError());
			dateValidationUc.setText(StringUtil.generateMessage(i18n.GL0082(),
					"birthday."));
			dateValidationUc.setVisible(true);
			isValid = false;
		}

		if ((getRegistartionType().equalsIgnoreCase("parent")) && age < 13) {
			dateBoxUc.addStyleName(RegisterCBundle.INSTANCE.css()
					.parentDateBoxError());
			dateBoxUc.getDateBox().setStyleName(
					RegisterCBundle.INSTANCE.css().parentDateTextError());
			dateValidationUc.setText(StringUtil.generateMessage(i18n.GL0079()));
			dateValidationUc.setVisible(true);
			isValid = false;
		}

		return isValid;
	}

	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	@Override
	public void reset() {
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUnload() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRegisteredUserDetails(UserDo user, String accountType) {
		userEmailFieldLbl.setText(user.getEmailId());
		userEmailFieldLbl.getElement().setId("lblUserEmailFieldLbl");
		userEmailFieldLbl.getElement().setAttribute("alt",user.getEmailId());
		userEmailFieldLbl.getElement().setAttribute("title",user.getEmailId());

		userCategoryLisBox.addItem(i18n.GL0417());
		userCategoryLisBox.addItem(i18n.GL0416());
		userCategoryLisBox.addItem(i18n.GL0418());
		userCategoryLisBox.addItem(i18n.GL0419());
		userCategoryLisBox.setVisibleItemCount(1);
		if (user.getConfirmStatus() == 1
				&& accountType.equalsIgnoreCase("Parent")) {
			renderChildAccount();
		} else if (accountType.equalsIgnoreCase("Parent")) {
			setRegistartionType(accountType);
			welcomeMessageHtml
					.setHTML(i18n.GL1208());
			accountTypeFieldLbl.setText((i18n.GL1205()+" "+i18n.GL1198()));
			accountInformationLbl.setText((i18n.GL1205()+" "+i18n.GL1198()));
			birthdayFloPanel.setVisible(true);
			userCategoryLisBox.setItemSelected(2, true);
		} else {
			setRegistartionType("NonParent");
			birthdayFloPanel.setVisible(false);
		}
	}

	/**
	 * Get user's gender while register
	 *
	 * @param genderFlowPanel
	 *            instance of {@link FlowPanel} which contains gender details
	 * @return gender value
	 */
	private String getSelectedGender(FlowPanel genderFlowPanel) {
		String gender = null;
		for (Widget genderWidget : genderFlowPanel) {
			if (genderWidget instanceof CheckBox) {
				GenderRadioButton genderWidgetRadioButton = (GenderRadioButton) genderWidget;
				if (genderWidgetRadioButton.getValue()) {
					gender = genderWidgetRadioButton.getGenderValue();
					break;
				}
			}
		}
		return gender;
	}

	/**
	 * Clear the selected gender
	 *
	 * @param genderFlowPanel
	 *            instance of {@link FlowPanel} which contains gender details
	 * @return gender
	 */
	private String clearSelectedGender(FlowPanel genderFlowPanel) {
		String gender = null;
		for (Widget genderWidget : genderFlowPanel) {
			if (genderWidget instanceof CheckBox) {
				((GenderRadioButton) genderWidget).setValue(false);
			}
		}
		return gender;
	}

	@Override
	public void closeRegisterPopup() {
		appPopUp.hide();
	}

	/**
	 * @return registration type
	 */
	public String getRegistartionType() {
		return registartionType;
	}

	/**
	 * Assign registration type
	 *
	 * @param registartionType
	 */
	public void setRegistartionType(String registartionType) {
		this.registartionType = registartionType;
	}

	@Override
	public String getRegistrationType() {
		return getRegistartionType();
	}

	@Override
	public void renderChildAccount() {
		setRegistartionType("Child");
		welcomeMessageHtml
				.setHTML(i18n.GL1209());
		appPopUp.setViewTitle(i18n.GL1210());
		userCategoryFloPanel.setVisible(false);
		lastnameFloPanel.setVisible(true);
		firstnameFloPanel.setVisible(true);
		birthdayFloPanel.setVisible(false);
		firstNameFieldUc.setUserText("");
		lastNameFieldUc.setUserText("");
		userNameFieldTxtBox.setText("");
		confirmPasswordFieldTxtBox.setText("");
		passwordFieldTxtBox.setText("");
		clearSelectedGender(genderFieldsFloPanel);
		accountTypeFieldLbl.setText(i18n.GL1206()+" "+i18n.GL0807());
		accountInformationLbl.setText(i18n.GL1206()+" "+i18n.GL1198());
		termsAndConditionFloPanel.setVisible(false);
	}

	@Override
	public void checkUserNameAvailability(UserDo user) {
		if (user != null && user.isAvailability()
				&& userNameFieldTxtBox.getText() != null) {
			userNameValidUc.setText(i18n.GL0061()+" " + userNameFieldTxtBox.getText()
					+ ( " "+i18n.GL1204()));
			userNameValidUc.setVisible(true);
			userNameFieldTxtBox.addStyleName(RegisterCBundle.INSTANCE.css()
					.userRegistrationError());
		}
	}

	private int getAge(Date birthDate) {
		if (birthDate != null) {
			long ageInMillis = new Date().getTime() - birthDate.getTime();
			Date age = new Date(ageInMillis);
			return age.getYear() - 70;
		} else {
			return 0;
		}
	}

	public String getFirstName() {
		return firstNameFieldUc.getUserText();

	}

	public String getLastName() {
		return lastNameFieldUc.getUserText();
	}

}
