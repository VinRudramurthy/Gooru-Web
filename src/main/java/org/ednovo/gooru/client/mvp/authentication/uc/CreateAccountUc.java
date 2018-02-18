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
package org.ednovo.gooru.client.mvp.authentication.uc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.authentication.SignUpCBundle;
import org.ednovo.gooru.client.mvp.home.register.NewRegisterCBundle;
import org.ednovo.gooru.client.mvp.home.register.ParentRegisterVc;
import org.ednovo.gooru.client.mvp.home.register.RegistrationConfirmationVc;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AlertContentUc;
import org.ednovo.gooru.client.uc.DateBoxUc;
import org.ednovo.gooru.client.uc.ErrorLabelUc;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : CreateAccountUc.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: Sep 26, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public abstract class CreateAccountUc extends PopupPanel{

	@UiField(provided = true)
	SignUpCBundle res;

	@UiTemplate("CreateAccountUc.ui.xml")
	interface Binder extends UiBinder<Widget, CreateAccountUc> {

	}

	private static final Binder binder = GWT.create(Binder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	Label lblPleaseFill, lblPickWisely, lblQuestionMark, lblWhyEnterBirthday,
			lblWhyEnterBirthdayDesc, lblNameTooltipContent,
			lblEmailTooltipContent, lblPasswordTooltipContent;

	@UiField
	Label lblTeacher, lblStudent, lblParent, lblOther, lblPleaseWait,
			lblQuestionMarkNeedParentAccount, lblWhyNeedParent,
			lblWhyNeedParentDesc;

	@UiField
	TextBoxWithPlaceholder txtChooseUsername, txtFirstName, txtLastName,
			txtChooseEmail, txtChoosePassword, txtConfirmPassword;

	@UiField
	Anchor ancCopyRight, ancTermsAndPrivacy, ancPrivacy;

	@UiField
	Button btnSignUp;

	@UiField
	HTMLPanel rdTeacher,mainContainer, rdStudent, rdParent, rdOther, panelOther, panelTeacher, panelStudent, panelParent,emailFieldContainer;

	@UiField
	HTMLPanel panelUserNamePopUp,popupbody, panelPublic, panelEmail, panelPassword,toolTip,userDetailscontainer;

	@UiField
	HTMLEventPanel panelDataOfBirth;

	@UiField
	SimplePanel sPanelDateOfBirth;

	@UiField
	HTMLPanel panelBelowThirteen, panelAboveThirteen, tootltipContainer;

	@UiField
	ErrorLabelUc dateValidationUc, passwordValidUc, lblSelectRole,
			userNameValidUc, parentEmailValidUc, emailValidUc, firstNameValidUc, lastNameValidUc;

	@UiField
	Label lblNeedParentsAccount;
	@UiField
	Label lblMyParentHasGooruAccount;
	@UiField
	TextBoxWithPlaceholder txtParentEmailId;
	@UiField
	Label lblOr;
	@UiField
	Label lblMyParentDontHaveAccount;
	@UiField
	Label lblGetCorrectEmail,errorLblForUsername,errorLblForFirstName,errorLblForLastName;
	@UiField
	Anchor ancParentRegister;
	@UiField
	InlineLabel lblAgree,andText;


	ParentRegisterVc parentRegisterVc = null;

	private DateBoxUc dateBoxUc;

	RadioButton rbTeacher;
	RadioButton rbStudent;
	RadioButton rbParent;
	RadioButton rbOther;

	private String dob;

	private String selectedRole = null;

	private String homeEndPoint = null;

	boolean isAvailable = false;

	boolean underThirtheen = false;

	/*boolean isEmailAvailable = true;*/

//	private static final String LOGIN_YOUR_EXISTING_ACCOUNT = i18n.GL0214();

	private static final String PWD_PATTERN = "[0-9]|[$@!#*%^/[/]}{()_&-+=.,<>;\\|]";

	private static final String PARENT = "Parent";

	private static final String BIRTH_DAY = "birthday";

	private static final String GOORU_UID = "gooruUid";

	private static final String ACCOUNT_TYPE = "accountType";

	private static final String DATE_OF_BIRTH = "dateOfBirth";

	private static final String FIRST_NAME = "firstName";

	private static final String USER_NAME = "username";

	private static final String PASSWORD = "password";

	private static final String EMAIL_ID = "emailId";

	private static final String ORGANIZATION_CODE = "organizationCode";

	private static final String LAST_NAME = "lastName";

	private static final String GOORU = "gooru";

	String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	String USER_NAME_REGEX = "[A-Za-z0-9^]*";
	// private static final String PWD_VALUE = "041299";

	// private static final String AT_SYMBOL = "@";

	// private static final String NON_SYMBOL = "NonParent";

	String account = null;

	boolean isValidEmailId = false;
	boolean isValidUserName = false,isHavingBadWordsUserName,isHavingBadWordsFirstName,isHavingBadWordsLastName;

	String privateGooruUId = null;

	/**
	 * Class constructor , to create Almost done popup
	 *
	 * @param userEmail
	 * @param user
	 *            {@link UserDo}
	 */

	@SuppressWarnings("unchecked")
	public CreateAccountUc() {
		super(false);

		this.res = SignUpCBundle.INSTANCE;
		res.css().ensureInjected();
		add(binder.createAndBindUi(this));
		this.setGlassEnabled(true);

		this.center();
		panelUserNamePopUp.getElement().getStyle().setLeft(-82, Unit.PX);
		panelPublic.getElement().getStyle().setLeft(-82, Unit.PX);
		toolTip.getElement().getStyle().setLeft(-64, Unit.PX);
		lblQuestionMark.addDomHandler(new MouseOverEventQuestion(), MouseOverEvent.getType());
		lblQuestionMark.addDomHandler(new MouseOutEventQuestion(), MouseOutEvent.getType());
		account = AppClientFactory.getPlaceManager().getRequestParameter(
				"account") != null ? AppClientFactory.getPlaceManager()
				.getRequestParameter("account") : null;

		setUiAndIds();

		dateBoxUc = new DateBoxUc(true, true, false);
		//dateBoxUc.getElement().getStyle().setFloat(Float.LEFT);
		sPanelDateOfBirth.add(dateBoxUc);

		sPanelDateOfBirth.getElement().setId("spnlDateOfBirth");

		dateBoxUc.getDateBox().addFocusHandler(new OnDateFocus());
		dateBoxUc.getDateBox().addBlurHandler(new OnDateBlur());
		dateBoxUc.addDomHandler(new OnDateFocus(), FocusEvent.getType());
		dateBoxUc.getDoneButton().addClickHandler(new OnDoneClick());

		lblQuestionMarkNeedParentAccount.addDomHandler(new MouseoverQuestion(), MouseOverEvent.getType());
		lblQuestionMarkNeedParentAccount.addDomHandler(new MouseOutQuestion(), MouseOutEvent.getType());

		this.getElement().getStyle().setBackgroundColor("transparent");

		dateValidationUc.setVisible(false);
		passwordValidUc.setVisible(false);
		userNameValidUc.setVisible(false);
		emailValidUc.setVisible(false);

		errorLblForUsername.setVisible(false);
		errorLblForFirstName.setVisible(false);

		//errorLblForLastName.getElement().setAttribute("style", "position: absolute;left: 32px;bottom: -6px;");
		errorLblForLastName.setVisible(false);

		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));

		AppClientFactory.getInjector().getSearchService()
				.getHomeEndPointUrl(new SimpleAsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {
						homeEndPoint = result;
					}
				});

		RootPanel.get().addDomHandler(new DateValueChange(), ClickEvent.getType());
		txtChooseUsername.addBlurHandler(new CheckProfanityInOnBlur(txtChooseUsername,null, errorLblForUsername, isHavingBadWordsUserName));
		txtFirstName.addBlurHandler(new CheckProfanityInOnBlur(txtFirstName,null, errorLblForFirstName, isHavingBadWordsFirstName));
		txtLastName.addBlurHandler(new CheckProfanityInOnBlur(txtLastName, null,errorLblForLastName, isHavingBadWordsLastName));


		if(AppClientFactory.getPlaceManager().getRequestParameter("type")!=null && AppClientFactory.getPlaceManager().getRequestParameter("email")!=null
			&& !AppClientFactory.getPlaceManager().getRequestParameter("email").equals(""))
				{
			txtChooseEmail.setText(AppClientFactory.getPlaceManager().getRequestParameter("email"));
			isValidEmailId = checkUserAvailability(
					txtChooseEmail.getText(), "emailId");
		}
	}

	public abstract void OpenTermsPrivacy();

	public abstract void OpenCopyRight();

	public abstract void OpenPrivacy();

	public abstract void CreateUser(Map<String, String> registrationDetailsParams, String username,String password);

	/**
	 *
	 * @function toggleButtons
	 *
	 * @created_date : Dec 10, 2013
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
	public void toggleButtons(){
		lblPleaseWait.setVisible(false);
		btnSignUp.setVisible(true);
	}

	@UiHandler("ancParentRegister")
	public void onClickParentRegister(ClickEvent event) {
		String userName = txtChooseUsername.getText();
		if (userName.equalsIgnoreCase("") || userName == null) {
			txtChooseUsername.addStyleName(res.css().errorMsgDisplay());
		}else{
			MixpanelUtil.register_as_a_parent();
			closePoup();
			Map<String, String> params = new HashMap<String, String>();
			params.put("callback", "signup");
			params.put("type", "3");
			params.put("account", "parent");

			String dob = dateBoxUc.getDateBox().getText().toString().trim()
					.replaceAll("\\/", "D");

			if (dob != null) {
				params.put("dob", dob);
			}
			if (userName != null) {
				params.put("userName", userName);
			}
			AppClientFactory.getPlaceManager()
					.revealPlace(PlaceTokens.HOME, params);
		}
	}

	/**
	 * Added click handler for showing Terms and Policy popup in footer
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 **/
	@UiHandler("ancTermsAndPrivacy")
	public void onClickTermsPrivacy(ClickEvent event) {
		OpenPrivacy();
	}

	/**
	 * Added click handler for showing copy right popup in footer
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 *
	 **/
	@UiHandler("ancCopyRight")
	public void onClickCopyRight(ClickEvent event) {
		OpenCopyRight();
	}

	@UiHandler("ancPrivacy")
	public void onClickancPrivacy(ClickEvent event) {
		OpenTermsPrivacy();
	}

	@UiHandler("btnSignUp")
	public void onClickSignUp(ClickEvent event) {
		final Map<String, String> parms = new HashMap<String, String>();
		parms.put("text", txtChooseUsername.getValue());
		lblPleaseWait.setVisible(true);
		btnSignUp.setVisible(false);
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean value) {
				if(value){
					SetStyleForProfanity.SetStyleForProfanityForTextBox(txtChooseUsername, errorLblForUsername, value);
				}else{
					parms.put("text", txtFirstName.getValue());
					AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

						@Override
						public void onSuccess(Boolean value) {
							if(value){
								SetStyleForProfanity.SetStyleForProfanityForTextBox(txtFirstName, errorLblForFirstName, value);
							}else{
								parms.put("text", txtLastName.getValue());
								AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

									@Override
									public void onSuccess(Boolean value) {
										if(value){
											SetStyleForProfanity.SetStyleForProfanityForTextBox(txtLastName, errorLblForLastName, value);
										}else{
											String userName = txtChooseUsername.getText().trim();
											String firstName = txtFirstName.getText().trim();
											String lastName = txtLastName.getText().trim();
											String emilId = txtChooseEmail.getText().trim();
											String password = StringUtil.getCryptoData(txtChoosePassword.getText().trim());
											String confirmPassword = txtConfirmPassword.getText().trim();
											String dob = dateBoxUc.getDateBox().getValue().trim();
											String parentEmailId = txtParentEmailId.getText().trim();

											Map<String, String> registrationDetailsParams = new HashMap<String, String>();
											registrationDetailsParams.put(FIRST_NAME, firstName);
											registrationDetailsParams.put(LAST_NAME, lastName);
											registrationDetailsParams.put(USER_NAME, userName);
											registrationDetailsParams.put(EMAIL_ID, emilId);
											registrationDetailsParams.put(ORGANIZATION_CODE, GOORU);
											registrationDetailsParams.put(PASSWORD, password);
											registrationDetailsParams.put("gooruBaseUrl", homeEndPoint +"#discover");
											registrationDetailsParams.put("role", selectedRole);
											registrationDetailsParams.put("dateOfBirth", dob);

											if (validateUserInput()) {
												lblPleaseWait.setVisible(true);
												btnSignUp.setVisible(false);
												if (!underThirtheen) {
													MixpanelUtil.sign_up_over_Thrteen();
													CreateUser(registrationDetailsParams, userName,password);
												} else {
													MixpanelUtil.continue_Child_registration();
													closePoup();
													Map<String, String> params = new HashMap<String, String>();
													params.put("callback", "registerChild");
													params.put("type", "4");
													params.put("privateGooruUId", AppClientFactory.isAnonymous() ? privateGooruUId : AppClientFactory.getLoggedInUser().getGooruUId());

													if (dob != null) {
														params.put("dob", dob);
													}
													if (userName != null) {
														params.put("userName", userName);
													}
													if(parentEmailId!=null){
														params.put("emailId",parentEmailId);
													}
													AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getCurrentPlaceToken(), params );
												}
											} else {
												toggleButtons();
											}
										}
									}
								});
							}
						}
					});
				}
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
		boolean isValid = true;
		lblPleaseWait.setVisible(true);
		btnSignUp.setVisible(false);


		String userName = txtChooseUsername.getText().trim();
		String dob = dateBoxUc.getDateBox().getValue().trim();
		String parentEmailId = txtParentEmailId.getText().trim();
		if (!underThirtheen) {
			String firstName = txtFirstName.getText().trim();
			String lastName = txtLastName.getText().trim();
			String emilId = txtChooseEmail.getText().trim();
			String password = txtChoosePassword.getText().trim();
			String confirmPassword = txtConfirmPassword.getText().trim();

			// TODO Validate all Fields are not null

			// TODO Validate password fields
			try {
				RegExp reg = RegExp.compile(PWD_PATTERN, "gi");
				if (password == null || (password != null && password.isEmpty())) {
					txtChoosePassword.addStyleName(res.css().errorMsgDisplay());
					passwordValidUc.setText(StringUtil.generateMessage(i18n.GL0070(), "Password"));
					passwordValidUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0070(), "Password"));
					passwordValidUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0070(), "Password"));
					passwordValidUc.setVisible(true);
					isValid = false;
				}
				if (!password.equalsIgnoreCase("") && password.length() > 0 && password.length() < 5) {
					txtChoosePassword.addStyleName(res.css().errorMsgDisplay());
					passwordValidUc.setText(StringUtil.generateMessage(i18n.GL0071(), "Password", "5"));
					passwordValidUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0071(), "Password", "5"));
					passwordValidUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0071(), "Password", "5"));
					passwordValidUc.setVisible(true);
					isValid = false;
				}
				if (!password.equalsIgnoreCase("") && password.length() >= 14) {
					txtChoosePassword.addStyleName(res.css().errorMsgDisplay());
					passwordValidUc.setText(StringUtil.generateMessage(i18n.GL0072(), "Password", "<= 14"));
					passwordValidUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0072(), "Password", "<= 14"));
					passwordValidUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0072(), "Password", "<= 14"));
					passwordValidUc.setVisible(true);
					isValid = false;
				}
				if (password.equalsIgnoreCase("PASSWORD")) {
					txtChoosePassword.addStyleName(res.css().errorMsgDisplay());
					passwordValidUc.setText(StringUtil.generateMessage(i18n.GL0076(), "Password"));
					passwordValidUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0076(), "Password"));
					passwordValidUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0076(), "Password"));
					passwordValidUc.setVisible(true);
					isValid = false;
				}
				if ((!password.equalsIgnoreCase("") && !password.isEmpty()) && !reg.test(password) && password.length() >= 5 && password.length() <= 14 && !password.equalsIgnoreCase("PASSWORD")) {
					txtChoosePassword.addStyleName(res.css().errorMsgDisplay());
					passwordValidUc.setText(StringUtil.generateMessage(i18n.GL0073(), "Password"));
					passwordValidUc.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0073(), "Password"));
					passwordValidUc.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0073(), "Password"));
					/*passwordValidUc.getElement().getStyle().setWidth(340, Unit.PX);*/
					passwordValidUc.getElement().getStyle().setMarginLeft(0, Unit.PX);
					passwordValidUc.setVisible(true);
					isValid = false;
				}
			} catch (Exception e) {
				isValid = false;
			}

			if (userName.equalsIgnoreCase("") || userName == null) {
				txtChooseUsername.addStyleName(res.css().errorMsgDisplay());
				isValid = false;
			} else {
				// TODO Validate UserName is already Exists or Not
				// if (checkUserAvailability(userName, "byUsername")) {
				// txtChooseUsername.addStyleName(res.css().errorMsgDisplay());
				// userNameValidUc.setVisible(true);
				// isValid = false;
				// }
				if (isValidUserName)
					isValid = !isValidUserName;
			}
			if (dob.equalsIgnoreCase("") || dob == null || dob.isEmpty()
					|| !dateBoxUc.hasValidateDate()) {
				sPanelDateOfBirth.addStyleName(res.css().errorMsgDisplay());
				// dateValidationUc.setVisible(true);
				isValid = false;
			} else {
				sPanelDateOfBirth.removeStyleName(
						res.css().errorMsgDisplay());
			}
			if (firstName.equalsIgnoreCase("") || firstName == null) {
				txtFirstName.addStyleName(res.css().errorMsgDisplay());
				isValid = false;
			}
			if (firstName.length() > 20) {
				txtFirstName.getElement().addClassName(res.css().errorMsgDisplay());
				firstNameValidUc.setText(StringUtil.generateMessage(i18n.GL0072(), "First name", "<=20"));
				firstNameValidUc.getElement().removeAttribute("style");
				firstNameValidUc.setVisible(true);

				isValid = false;
			}


			if (lastName.equalsIgnoreCase("") || lastName == null) {
				txtLastName.addStyleName(res.css().errorMsgDisplay());
				isValid = false;
			}
			if (lastName.length() > 20) {
				lastNameValidUc.getElement().addClassName(res.css().errorMsgDisplay());
				lastNameValidUc.setText(StringUtil.generateMessage(i18n.GL0072(), "Last name", "<= 20"));
				lastNameValidUc.getElement().removeAttribute("style");
				lastNameValidUc.setVisible(true);

				isValid = false;
			}

			if (emilId.equalsIgnoreCase("") || emilId == null) {
				txtChooseEmail.addStyleName(res.css().errorMsgDisplay());
				isValid = false;
			} else {
				// TODO Validate Email Id is already registered or not
				if (isValidEmailId)
					isValid = !isValidEmailId;
			}
			if (password.equalsIgnoreCase("") || password == null) {
				txtChoosePassword.addStyleName(res.css().errorMsgDisplay());
				isValid = false;
			}
			if (confirmPassword.equalsIgnoreCase("") || confirmPassword == null) {
				txtConfirmPassword.addStyleName(res.css().errorMsgDisplay());
				isValid = false;
			}

			// TODO Validate Password fields are match each other.
			if (!password.equalsIgnoreCase(confirmPassword)) {
				txtConfirmPassword.addStyleName(res.css().errorMsgDisplay());
				txtChoosePassword.addStyleName(res.css().errorMsgDisplay());
				passwordValidUc.setText(i18n.GL0446());
				passwordValidUc.getElement().setAttribute("alt",i18n.GL0446());
				passwordValidUc.getElement().setAttribute("title",i18n.GL0446());
				passwordValidUc.setVisible(true);
				isValid = false;
			}

			// TODO Validate Date of Birth
			if (dateBoxUc.hasValidateDate()) {
				// TODO Check age
				Date date = dateBoxUc.getValue();
				int age = getAge(date);

				// TODO if <13 then open parent registration
				if (age < 13) {

					isValid = false;
				}
				// else continue.
			}
			// TODO Validate Whether is Seleted or not
			if (selectedRole == null) {
			//	lblSelectRole.addStyleName(res.css().error());
				lblSelectRole.setVisible(true);
				isValid = false;
			}

			// TODO Support Password Regx
			RegExp reg = RegExp.compile(PWD_PATTERN, "gi");
			if (!reg.test(password) && password.length() >= 5
					&& password.length() <= 14) {
				passwordValidUc.setText(StringUtil.generateMessage(
						i18n.GL0073(), "Password"));
				passwordValidUc.getElement().setAttribute("alt",StringUtil.generateMessage(
						i18n.GL0073(), "Password"));
				passwordValidUc.getElement().setAttribute("title",StringUtil.generateMessage(
						i18n.GL0073(), "Password"));
				passwordValidUc.setVisible(true);
				isValid = false;
			}

		} else {
			// TODO Validation different components for age < 13
			if (parentEmailId.equalsIgnoreCase("") || parentEmailId == null) {
				txtParentEmailId.addStyleName(res.css().errorMsgDisplay());
				parentEmailValidUc.setText(i18n.GL0463());
				parentEmailValidUc.getElement().setAttribute("alt",i18n.GL1146());
				parentEmailValidUc.getElement().setAttribute("title",i18n.GL1146());

				isValid = false;
			}
			if (!parentEmailId.equalsIgnoreCase("") || parentEmailId != null) {

				Boolean from = parentEmailId.matches(EMAIL_REGEX);
				if (from) {
					if (isValidEmailId) {
						// Found user as registered user in gooru
						txtParentEmailId.removeStyleName(res.css()
								.errorMsgDisplay());
						parentEmailValidUc.setVisible(false);
						lblGetCorrectEmail.setVisible(true);
						isValid = true;
					} else {
						// Found user is not registered user in gooru
						txtParentEmailId.addStyleName(res.css()
								.errorMsgDisplay());
						parentEmailValidUc.setText(i18n.GL0465());
						parentEmailValidUc.getElement().setAttribute("alt",i18n.GL1146());
						parentEmailValidUc.getElement().setAttribute("title",i18n.GL1146());
						parentEmailValidUc.setVisible(true);
						lblGetCorrectEmail.setVisible(false);
						isValid = false;
					}
				} else {
					// lblGetCorrectEmail.setVisible(false);
					txtParentEmailId.addStyleName(res.css().errorMsgDisplay());
					parentEmailValidUc.setText(i18n.GL0464());
					parentEmailValidUc.getElement().setAttribute("alt",i18n.GL1146());
					parentEmailValidUc.getElement().setAttribute("title",i18n.GL1146());
					isValid = false;
				}
			}
			if (userName.equalsIgnoreCase("") || userName == null) {
				txtChooseUsername.addStyleName(res.css().errorMsgDisplay());
				isValid = false;
			} else {
				if (isValidUserName) {
					txtChooseUsername.addStyleName(res.css().errorMsgDisplay());
					userNameValidUc.setVisible(true);
					isValid = false;
				}

			}

		}
		return isValid;
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
	private void setUiAndIds() {
		lblPleaseFill.getElement().setId("lblPleaseFill");
		if (account != null) {
			lblPleaseFill.setText(i18n.GL0471());
			lblPleaseFill.getElement().getStyle().setColor("#000000");
			lblPleaseFill.getElement().getStyle().setFontSize(18, Unit.PX);
			lblPleaseFill.getElement().setAttribute("alt",i18n.GL0471());
			lblPleaseFill.getElement().setAttribute("title",i18n.GL0471());
		} else {
			lblPleaseFill.setText(i18n.GL0409());
			lblPleaseFill.getElement().getStyle().clearColor();
			lblPleaseFill.getElement().getStyle().clearFontSize();
			lblPleaseFill.getElement().setAttribute("alt",i18n.GL0409());
			lblPleaseFill.getElement().setAttribute("title",i18n.GL0409());
		}

		lblPickWisely.setText(i18n.GL0410());
		lblPickWisely.getElement().setId("lblPickWisely");
		lblPickWisely.getElement().setAttribute("alt",i18n.GL0410());
		lblPickWisely.getElement().setAttribute("title",i18n.GL0410());

		lblQuestionMark.setText(i18n.GL_SPL_QUESTION());
		lblQuestionMark.getElement().setId("lblQuestionMark");
		lblQuestionMark.getElement().setAttribute("alt",i18n.GL_SPL_QUESTION());
		lblQuestionMark.getElement().setAttribute("title",i18n.GL_SPL_QUESTION());

		lblWhyEnterBirthday.setText(i18n.GL0411()
				+i18n.GL_SPL_QUESTION());
		lblWhyEnterBirthday.getElement().setId("lblWhyEnterBirthday");
		lblWhyEnterBirthday.getElement().setAttribute("alt",i18n.GL0411());
		lblWhyEnterBirthday.getElement().setAttribute("title",i18n.GL0411());

		lblWhyEnterBirthdayDesc.setText(i18n.GL0412());
		lblWhyEnterBirthdayDesc.getElement().setId("lblWhyEnterBirthdayDesc");
		lblWhyEnterBirthdayDesc.getElement().setAttribute("alt",i18n.GL0412());
		lblWhyEnterBirthdayDesc.getElement().setAttribute("title",i18n.GL0412());

		lblNameTooltipContent.setText(i18n.GL0413());
		lblNameTooltipContent.getElement().setId("lblNameTooltipContent");
		lblNameTooltipContent.getElement().setAttribute("alt",i18n.GL0413());
		lblNameTooltipContent.getElement().setAttribute("title",i18n.GL0413());

		lblEmailTooltipContent.setText(i18n.GL0414());
		lblEmailTooltipContent.getElement().setId("lblEmailTooltipContent");
		lblEmailTooltipContent.getElement().setAttribute("alt",i18n.GL0414());
		lblEmailTooltipContent.getElement().setAttribute("title",i18n.GL0414());

		lblPasswordTooltipContent.setText(i18n.GL0415());
		lblPasswordTooltipContent.getElement().setId("lblPasswordTooltipContent");
		lblPasswordTooltipContent.getElement().setAttribute("alt",i18n.GL0415());
		lblPasswordTooltipContent.getElement().setAttribute("title",i18n.GL0415());

		lblPleaseWait.setVisible(false);
		lblPleaseWait.setText(i18n.GL0339());
		lblPleaseWait.getElement().setId("lblPleaseWait");
		lblPleaseWait.getElement().setAttribute("alt",i18n.GL0339());
		lblPleaseWait.getElement().setAttribute("title",i18n.GL0339());

		lblTeacher.setText(i18n.GL0416());
		lblTeacher.getElement().setId("lblTeacher");
		lblTeacher.getElement().setAttribute("alt",i18n.GL0416());
		lblTeacher.getElement().setAttribute("title",i18n.GL0416());

		lblStudent.setText(i18n.GL0417());
		lblStudent.getElement().setId("lblStudent");
		lblStudent.getElement().setAttribute("alt",i18n.GL0417());
		lblStudent.getElement().setAttribute("title",i18n.GL0417());

		lblParent.setText(i18n.GL0418());
		lblParent.getElement().setId("lblParent");
		lblParent.getElement().setAttribute("alt",i18n.GL0418());
		lblParent.getElement().setAttribute("title",i18n.GL0418());

		lblOther.setText(i18n.GL0419());
		lblOther.getElement().setId("lblOther");
		lblOther.getElement().setAttribute("alt",i18n.GL0419());
		lblOther.getElement().setAttribute("title",i18n.GL0419());

		lblAgree.setText(i18n.GL0420()+" ");
		lblAgree.getElement().setId("lblAgree");
		lblAgree.getElement().setAttribute("alt",i18n.GL0420());
		lblAgree.getElement().setAttribute("title",i18n.GL0420());

		txtChooseUsername.setPlaceholder(i18n.GL0423());
		txtChooseUsername.getElement().setId("txtChooseUsername");
		txtChooseUsername.setMaxLength(20);

		txtFirstName.setPlaceholder(i18n.GL0424());
		txtFirstName.getElement().setId("txtFirstName");

		txtLastName.setPlaceholder(i18n.GL0425());
		txtLastName.getElement().setId("txtLastName");

		txtChooseEmail.setPlaceholder(i18n.GL0426());
		txtChooseEmail.getElement().setId("txtChooseEmail");

		txtChoosePassword.setPlaceholder(i18n.GL0204());
		txtChoosePassword.getElement().setId("txtChoosePassword");
		txtChoosePassword.setMaxLength(14);

		txtConfirmPassword.setPlaceholder(i18n.GL0427());
		txtConfirmPassword.getElement().setId("txtConfirmPassword");
		txtConfirmPassword.setMaxLength(14);

		panelDataOfBirth.getElement().setId("pnlDataOfBirth");

		errorLblForUsername.getElement().setId("errlblUserName");

		userNameValidUc.getElement().setId("errlblUserNameValidUc");
		userNameValidUc.getElement().setAttribute("alt",i18n.GL0473());
		userNameValidUc.getElement().setAttribute("title",i18n.GL0473());

		txtChooseUsername.addKeyUpHandler(new OnKeyUpHandler());
		txtFirstName.addKeyUpHandler(new OnKeyUpHandler());
		txtLastName.addKeyUpHandler(new OnKeyUpHandler());
		txtChooseEmail.addKeyUpHandler(new OnKeyUpHandler());
		txtChoosePassword.addKeyUpHandler(new OnKeyUpHandler());
		txtConfirmPassword.addKeyUpHandler(new OnKeyUpHandler());

		txtChooseUsername.addBlurHandler(new OnBlurHandler());
		txtChooseEmail.addBlurHandler(new OnBlurHandler());
		txtParentEmailId.addBlurHandler(new OnBlurHandler());
		txtParentEmailId.addKeyUpHandler(new OnKeyUpHandler());

		txtChooseUsername.addMouseOverHandler(new OnMouseOver());
		txtFirstName.addMouseOverHandler(new OnMouseOver());
		txtLastName.addMouseOverHandler(new OnMouseOver());
		txtChooseEmail.addMouseOverHandler(new OnMouseOver());
		txtChoosePassword.addMouseOverHandler(new OnMouseOver());
		txtConfirmPassword.addMouseOverHandler(new OnMouseOver());
		panelDataOfBirth.addMouseOverHandler(new OnMouseOver());
		lblQuestionMarkNeedParentAccount.addMouseOverHandler(new OnMouseOver());

		txtChooseUsername.addMouseOutHandler(new OnMouseOut());
		txtFirstName.addMouseOutHandler(new OnMouseOut());
		txtLastName.addMouseOutHandler(new OnMouseOut());
		txtChooseEmail.addMouseOutHandler(new OnMouseOut());
		txtChoosePassword.addMouseOutHandler(new OnMouseOut());
		txtConfirmPassword.addMouseOutHandler(new OnMouseOut());
		panelDataOfBirth.addMouseOutHandler(new OnMouseOut());
		lblQuestionMarkNeedParentAccount.addMouseOutHandler(new OnMouseOut());

		txtFirstName.setMaxLength(20);
		txtLastName.setMaxLength(20);

		panelUserNamePopUp.setVisible(false);
		panelUserNamePopUp.getElement().setId("pnlUserNamePopUp");

		panelPublic.setVisible(false);
		panelPublic.getElement().setId("pnlPublic");

		panelEmail.setVisible(false);
		panelEmail.getElement().setId("pnlEmail");

		panelPassword.setVisible(false);
		panelPassword.getElement().setId("pnlPassword");

		ancCopyRight.setText(i18n.GL0421() + ",");
		ancCopyRight.getElement().setId("lnkCopyRight");
		ancCopyRight.getElement().setAttribute("alt",i18n.GL0421());
		ancCopyRight.getElement().setAttribute("title",i18n.GL0421());

		ancTermsAndPrivacy.setText(i18n.GL0422());
		ancTermsAndPrivacy.getElement().setId("lnkTermsAndPrivacy");
		ancTermsAndPrivacy.getElement().setAttribute("alt",i18n.GL0422());
		ancTermsAndPrivacy.getElement().setAttribute("title",i18n.GL0422());

		ancPrivacy.setText(i18n.GL0452());
		ancPrivacy.getElement().setId("lnkPrivacy");
		ancPrivacy.getElement().setAttribute("alt",i18n.GL0452());
		ancPrivacy.getElement().setAttribute("title",i18n.GL0452());

		btnSignUp.setText(i18n.GL0186());
		btnSignUp.getElement().setId("btnSignUp");
		btnSignUp.getElement().setAttribute("alt",i18n.GL0186());
		btnSignUp.getElement().setAttribute("title",i18n.GL0186());
		btnSignUp.setEnabled(false);
		btnSignUp.getElement().addClassName("disabled");

		lblNeedParentsAccount.setText(i18n.GL0455());
		lblNeedParentsAccount.getElement().setId("lblNeedParentsAccount");
		lblNeedParentsAccount.getElement().setAttribute("alt",i18n.GL0455());
		lblNeedParentsAccount.getElement().setAttribute("title",i18n.GL0455());

		lblMyParentHasGooruAccount.setText(i18n.GL0456());
		lblMyParentHasGooruAccount.getElement().setId("lblMyParentHasGooruAccount");
		lblMyParentHasGooruAccount.getElement().setAttribute("alt",i18n.GL0456());
		lblMyParentHasGooruAccount.getElement().setAttribute("title",i18n.GL0456());

		txtParentEmailId.setPlaceholder(i18n.GL0457());
		txtParentEmailId.getElement().setId("txtParentEmailId");

		lblOr.setText(i18n.GL0466());
		lblOr.getElement().setId("lblOr");
		lblOr.getElement().setAttribute("alt",i18n.GL0466());
		lblOr.getElement().setAttribute("title",i18n.GL0466());

		lblMyParentDontHaveAccount.setText(i18n.GL0458());
		lblMyParentDontHaveAccount.getElement().setId("lblMyParentDontHaveAccount");
		lblMyParentDontHaveAccount.getElement().setAttribute("alt",i18n.GL0458());
		lblMyParentDontHaveAccount.getElement().setAttribute("title",i18n.GL0458());

		ancParentRegister.setText(i18n.GL0459());
		ancParentRegister.getElement().setId("lnkParentRegister");
		ancParentRegister.getElement().setAttribute("alt",i18n.GL0459());
		ancParentRegister.getElement().setAttribute("title",i18n.GL0459());

		lblQuestionMarkNeedParentAccount
				.setText(i18n.GL_SPL_QUESTION());
		lblQuestionMarkNeedParentAccount.getElement().setId("lblQuestionMarkNeedParentAccount");
		lblQuestionMarkNeedParentAccount.getElement().setAttribute("alt",i18n.GL_SPL_QUESTION());
		lblQuestionMarkNeedParentAccount.getElement().setAttribute("title",i18n.GL_SPL_QUESTION());

		lblWhyNeedParentDesc.setText(i18n.GL0461());
		lblWhyNeedParentDesc.getElement().setId("lblWhyNeedParentDesc");
		lblWhyNeedParentDesc.getElement().setAttribute("alt",i18n.GL0461());
		lblWhyNeedParentDesc.getElement().setAttribute("title",i18n.GL0461());

		lblWhyNeedParent.setText(i18n.GL0462()
				+ i18n.GL_SPL_QUESTION());
		lblWhyNeedParent.getElement().setId("lblWhyNeedParent");
		lblWhyNeedParent.getElement().setAttribute("alt",i18n.GL0462());
		lblWhyNeedParent.getElement().setAttribute("title",i18n.GL0462());

		tootltipContainer.getElement().setAttribute("style", "left:311px");
		tootltipContainer.getElement().setId("pnlTootltipContainer");

		panelBelowThirteen.setVisible(false);
		panelBelowThirteen.getElement().setId("pnlBelowThirteen");

		panelAboveThirteen.setVisible(true);
		panelAboveThirteen.getElement().setId("pnlAboveThirteen");

		lblGetCorrectEmail.setVisible(false);
		lblGetCorrectEmail.getElement().setId("lblGetCorrectEmail");
		lblGetCorrectEmail.getElement().setAttribute("alt","");
		lblGetCorrectEmail.getElement().setAttribute("title","");


		dateValidationUc.setText(StringUtil.generateMessage(
			i18n.GL0082(), BIRTH_DAY));
		dateValidationUc.getElement().setId("errlblDateValidationUc");
		dateValidationUc.getElement().setAttribute("alt",i18n.GL0473());
		dateValidationUc.getElement().setAttribute("title",i18n.GL0473());

		lblSelectRole.setText(i18n.GL1146());
		lblSelectRole.setVisible(false);
		lblSelectRole.getElement().setId("lblSelectRole");
		lblSelectRole.getElement().setAttribute("alt",i18n.GL1146());
		lblSelectRole.getElement().setAttribute("title",i18n.GL1146());

		parentEmailValidUc.getElement().setId("errlblParentEmailValidUc");
		parentEmailValidUc.getElement().getStyle().setMarginLeft(0, Unit.PX);
		parentEmailValidUc.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		firstNameValidUc.getElement().setId("errlblFirstNameValidUc");
		errorLblForFirstName.getElement().setId("errlblFirstName");

		lastNameValidUc.getElement().setId("errlblLastNameValidUc");
		errorLblForLastName.getElement().setId("errlblLastName");

		emailFieldContainer.getElement().setId("pnlEmailFieldContainer");
		emailValidUc.getElement().setId("errlblEmailValidUc");
		passwordValidUc.getElement().setId("errlblPasswordValidUc");

		panelTeacher.getElement().setId("pnlTeacher");
		panelStudent.getElement().setId("pnlStudent");
		panelParent.getElement().setId("pnlParent");
		panelOther.getElement().setId("pnlOther");

		andText.setText(i18n.GL_GRR_AND()+" ");
		andText.getElement().setId("spnAndText");
		andText.getElement().setAttribute("alt",i18n.GL_GRR_AND());
		andText.getElement().setAttribute("title",i18n.GL_GRR_AND());

		rbTeacher = new RadioButton("roleOption", "");
		rbStudent = new RadioButton("roleOption", "");
		rbParent = new RadioButton("roleOption", "");
		rbOther = new RadioButton("roleOption", "");

		rbTeacher.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MixpanelUtil.select_teacher();
				selectedRole = "teacher";
				lblSelectRole.setVisible(false);
				if (rbTeacher.getValue()){
					//Remove normal and Set Selected Image
					panelTeacher.getElement().addClassName("teacherRoleSelected");
				}
				//Remove selected image and set normal
				panelOther.getElement().removeClassName("otherRoleSelected");
//				panelTeacher.getElement().removeClassName(res.css().teacherRoleSelected());
				panelStudent.getElement().removeClassName("studentRoleSelected");
				panelParent.getElement().removeClassName("parentRoleSelected");
			}
		});
		rbStudent.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MixpanelUtil.select_student();
				selectedRole = "student";
				lblSelectRole.setVisible(false);
				if (rbStudent.getValue()){
					//Remove normal and Set Selected Image
					panelStudent.getElement().addClassName("studentRoleSelected");
				}
				//Remove selected image and set normal
				panelOther.getElement().removeClassName("otherRoleSelected");
				panelTeacher.getElement().removeClassName("teacherRoleSelected");
				panelParent.getElement().removeClassName("parentRoleSelected");
			}
		});
		rbParent.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MixpanelUtil.select_parent();
				selectedRole = "parent";
				lblSelectRole.setVisible(false);
				if (rbParent.getValue()){
					//Remove normal and Set Selected Image
					panelParent.getElement().addClassName("parentRoleSelected");
				}
				//Remove selected image and set normal
				panelOther.getElement().removeClassName("otherRoleSelected");
				panelTeacher.getElement().removeClassName("teacherRoleSelected");
				panelStudent.getElement().removeClassName("studentRoleSelected");
			}
		});
		rbOther.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MixpanelUtil.select_other();
				selectedRole = "other";
				lblSelectRole.setVisible(false);
				if (rbOther.getValue()){
					//Remove normal and Set Selected Image
					panelOther.getElement().addClassName("otherRoleSelected");
				}
				//Remove selected image and set normal
				panelTeacher.getElement().removeClassName("teacherRoleSelected");
				panelStudent.getElement().removeClassName("studentRoleSelected");
				panelParent.getElement().removeClassName("parentRoleSelected");
			}
		});

		rdTeacher.add(rbTeacher);
		rdTeacher.getElement().setId("rdTeacher");
		rdStudent.add(rbStudent);
		rdStudent.getElement().setId("rdStudent");
		rdParent.add(rbParent);
		rdParent.getElement().setId("rdParent");
		rdOther.add(rbOther);
		rdOther.getElement().setId("rdOther");

		if (account != null && account.equalsIgnoreCase("parent")) {
			rbParent.setChecked(true);
			selectedRole = "parent";
		} else {
			rbParent.setChecked(false);
			selectedRole = null;
		}

	}
	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	private class OnMouseOver implements MouseOverHandler {

		@Override
		public void onMouseOver(MouseOverEvent event) {

			if (event.getSource() == txtChooseUsername
					|| event.getSource() == panelDataOfBirth) {
				panelUserNamePopUp.setVisible(true);
			} else if (event.getSource() == txtFirstName
					|| event.getSource() == txtLastName) {
				panelPublic.setVisible(true);
			} else if (event.getSource() == txtChooseEmail) {
				panelEmail.setVisible(true);
			} else if (event.getSource() == txtChoosePassword
					|| event.getSource() == txtConfirmPassword) {
				panelPassword.setVisible(true);
			}
		}
	}
	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	private class OnMouseOut implements MouseOutHandler {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			panelUserNamePopUp.setVisible(false);
			panelPublic.setVisible(false);
			panelEmail.setVisible(false);
			panelPassword.setVisible(false);
		}

	}
	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	private class OnBlurHandler implements BlurHandler {

		@Override
		public void onBlur(BlurEvent event) {
			btnSignUp.setEnabled(false);
			btnSignUp.getElement().addClassName("disabled");

			if (event.getSource() == txtChooseEmail
					&& txtChooseEmail.getText() != null
					&& !txtChooseEmail.getText().equalsIgnoreCase("")) {
				boolean isValidFrom = txtChooseEmail.getText().matches(EMAIL_REGEX);
				if (isValidFrom) {
					isValidEmailId = checkUserAvailability(
							txtChooseEmail.getText(), "emailId");
					emailValidUc.setVisible(false);
					txtChooseEmail.removeStyleName(res.css().errorMsgDisplay());
				} else {
					txtChooseEmail.addStyleName(res.css().errorMsgDisplay());
					emailValidUc.addStyleName(res.css().errorLbl());
					emailValidUc.setText(i18n.GL0464());
					emailValidUc.getElement().setAttribute("alt",i18n.GL0464());
					emailValidUc.getElement().setAttribute("title",i18n.GL0464());
					emailValidUc.setVisible(true);
				}
			} else if (event.getSource() == txtChooseUsername
					&& txtChooseUsername.getText().trim() != null
					&& !txtChooseUsername.getText().equalsIgnoreCase("")) {
					/*
					//Check for Bad Words
					Map<String, String> parms = new HashMap<String, String>();
					parms.put("text", txtChooseUsername.getText().trim());
					AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

						@Override
						public void onSuccess(Boolean value) {
							boolean isHavingBadWords = value;
							if (value){
								txtChooseUsername.getElement().getStyle().setBorderColor("orange");
								userNameValidUc.setText(i18n.GL0554);
								userNameValidUc.setVisible(true);
								isValidUserName = false;
							}else{
								txtChooseUsername.getElement().getStyle().clearBackgroundColor();
								txtChooseUsername.getElement().getStyle().setBorderColor("#ddd");
								userNameValidUc.setVisible(false);*/

								/// Words are clear then continue the next steps

								if (txtChooseUsername.getText().length() < 4 || txtChooseUsername.getText().length() > 20){
									userNameValidUc.addStyleName(res.css().errorLbl());
									userNameValidUc.setText(i18n.GL0473());
									userNameValidUc.setVisible(true);
									isValidUserName = false;
								}
								else{
									isValidUserName = checkUserAvailability(
										txtChooseUsername.getText(), "username");
								}
								Boolean userNameValidate = txtChooseUsername.getText().matches(USER_NAME_REGEX);
								if(!userNameValidate){
									userNameValidUc.addStyleName(res.css().errorLbl());
									 if(!txtChooseUsername.getText().contains(" ")){
											if (txtChooseUsername.isVisible()){
												userNameValidUc.setText(i18n.GL0475());
												}
									}else if(txtChooseUsername.getText().contains(" ")){
										userNameValidUc.setText(i18n.GL1635());
									}
									userNameValidUc.setVisible(true);
									isValidUserName = false;
								}

								/*}
						}
					});*/

			} else if (event.getSource() == txtParentEmailId
					&& txtParentEmailId.getText() != null
					&& !txtParentEmailId.getText().equalsIgnoreCase("")) {
				isValidEmailId = checkUserRegisteredWithGooru(
						txtParentEmailId.getText(), "emailId");
			}
		}
	}
	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	private class OnKeyUpHandler implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			passwordValidUc.setVisible(false);
			if (event.getSource() == txtChooseUsername) {
				txtChooseUsername.removeStyleName(res.css().errorMsgDisplay());
				txtChooseUsername.getElement().getStyle().clearBackgroundColor();
				txtChooseUsername.getElement().getStyle().setBorderColor("#ddd");
				userNameValidUc.setText("");
				userNameValidUc.setVisible(false);
			} else if (event.getSource() == txtFirstName) {
				txtFirstName.removeStyleName(res.css().errorMsgDisplay());
			} else if (event.getSource() == txtLastName) {
				txtLastName.removeStyleName(res.css().errorMsgDisplay());
			} else if (event.getSource() == txtChooseEmail) {
				txtChooseEmail.removeStyleName(res.css().errorMsgDisplay());
				emailValidUc.setVisible(false);
			} else if (event.getSource() == txtChoosePassword) {
				txtChoosePassword.removeStyleName(res.css().errorMsgDisplay());
			} else if (event.getSource() == txtConfirmPassword) {
				txtConfirmPassword.removeStyleName(res.css().errorMsgDisplay());
			} else if (event.getSource() == txtParentEmailId) {
				txtParentEmailId.removeStyleName(res.css().errorMsgDisplay());
				parentEmailValidUc.setVisible(false);
			}
		}

	}

	/**
	 * Checks the availability of user name, entered by User.
	 *
	 * @param userName
	 * @param type
	 *
	 */
	public boolean checkUserAvailability(final String userName, final String type) {

		AppClientFactory.getInjector().getUserService()
				.getEmailId(userName, type, new SimpleAsyncCallback<UserDo>() {
					@Override
					public void onSuccess(UserDo result) {
						isAvailable = result.isAvailability();
						if (type.equalsIgnoreCase("emailId") && isAvailable) {
							privateGooruUId = result.getGooruUId();
							isValidEmailId = result.isAvailability();
							txtChooseEmail.addStyleName(res.css()
									.errorMsgDisplay());
							emailValidUc.addStyleName(res.css().errorLbl());
							emailValidUc.setText(i18n.GL0447());
							emailValidUc.getElement().setAttribute("alt",i18n.GL0447());
							emailValidUc.getElement().setAttribute("title",i18n.GL0447());
							emailValidUc.setVisible(true);
						}else if (type.equalsIgnoreCase("emailId") && !isAvailable){
							isValidEmailId = result.isAvailability();
						}
						if (type.equalsIgnoreCase("username") && isAvailable) {
							isValidUserName = result.isAvailability();
							if(!userName.contains(" ")){
								txtChooseUsername.addStyleName(res.css()
										.errorMsgDisplay());
								userNameValidUc.addStyleName(res.css().errorLbl());
								userNameValidUc.setText(i18n.GL0444());
								userNameValidUc.setVisible(true);
							}
						}else if (type.equalsIgnoreCase("username") && !isAvailable) {
							isValidUserName = result.isAvailability();
						}
						if (isValidEmailId==false && isValidUserName==false){
							btnSignUp.setEnabled(true);
							btnSignUp.getElement().removeClassName("disabled");
						}
						if (underThirtheen){
							if (isValidEmailId==true && isValidUserName==false){
								btnSignUp.setEnabled(true);
								btnSignUp.getElement().removeClassName("disabled");
							}
						}

					}
				});

		return isAvailable;
	}
	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	private class MouseOverEventQuestion implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			// TODO Auto-generated method stub

			toolTip.getElement().getStyle().setDisplay(Display.BLOCK);
		}

	}
	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	private class MouseOutEventQuestion implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			// TODO Auto-generated method stub
			toolTip.getElement().getStyle().setDisplay(Display.NONE);
		}



	}


	/**
	 * Checks the availability of user name, entered by User.
	 *
	 * @param userName
	 * @param type
	 *
	 */
	public boolean checkUserRegisteredWithGooru(String userName,
			final String type) {
		AppClientFactory.getInjector().getUserService()
				.getEmailId(userName, type, new SimpleAsyncCallback<UserDo>() {
					@Override
					public void onSuccess(UserDo result) {
						isValidEmailId = result.isAvailability();
						privateGooruUId = result.getGooruUId();
						if (isValidEmailId) {
							// Found user as registered user in gooru
							lblGetCorrectEmail.setVisible(true);
							txtParentEmailId.removeStyleName(res.css()
									.errorMsgDisplay());
							parentEmailValidUc.setVisible(false);
							lblGetCorrectEmail.setVisible(true);
							btnSignUp.setEnabled(true);
							btnSignUp.getElement().removeClassName("disabled");
						} else {
							// Found user is not registered user in gooru
							txtParentEmailId.addStyleName(res.css()
									.errorMsgDisplay());
							parentEmailValidUc
									.setText(i18n.GL0465());
							parentEmailValidUc.setVisible(true);
							lblGetCorrectEmail.setVisible(false);
						}
					}
				});
			return isValidEmailId;
	}
	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	private class DateValueChange implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (!(dateBoxUc.getValue() == null || dateBoxUc.getDateBox()
					.getText().isEmpty())
					&& dateBoxUc.hasValidateDate()) {

				Date date = dateBoxUc.getValue();
				int age = getAge(date);
				if (age < 13) {
					MixpanelUtil.create_Child_account();
					dob = dateBoxUc.getDate();
					underThirtheen = true;
					panelBelowThirteen.setVisible(true);
					popupbody.setStyleName("childUserInfoContainer");
					userDetailscontainer.setStyleName("firstInputGroup");
					if (panelAboveThirteen.isVisible()) {
						btnSignUp.setText(i18n.GL0460());
					}
					panelAboveThirteen.setVisible(false);
				}else{
					underThirtheen = false;
					panelBelowThirteen.setVisible(false);
					popupbody.removeStyleName("childUserInfoContainer");
					popupbody.setStyleName("userInfoContainer");
					userDetailscontainer.removeStyleName("firstInputGroup");

					panelAboveThirteen.setVisible(true);
					btnSignUp.setText(i18n.GL0186());
				}
			} else {
//				dateBoxUc.getDatePickerUc().hide();
			}
		}

	}
	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	private class OnDateFocus implements FocusHandler {
		@Override
		public void onFocus(FocusEvent event) {
			dateBoxUc.removeStyleName(NewRegisterCBundle.INSTANCE.css()
					.gooruDateBoxError());
			dateBoxUc.getDateBox().removeStyleName(
					NewRegisterCBundle.INSTANCE.css().gooruDateError());
			if (dateValidationUc.isVisible()) {
				dateValidationUc.setVisible(false);
			}

		}
	}
	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	private class OnDoneClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			dateBoxUc.getDateBox().removeStyleName(res.css().errorMsgDisplay());
			if (dateBoxUc.dateValidation()) {
				if (!(dateBoxUc.getValue() == null || dateBoxUc.getDateBox()
						.getText().isEmpty())
						&& dateBoxUc.hasValidateDate()) {
						Date date = dateBoxUc.getValue();
						int age = getAge(date);
						if (age < 13) {
							if (account != null && !underThirtheen){
								dateValidationUc.setText(i18n.GL0503());
								dateValidationUc.setVisible(true);
								isValidUserName = false;
							}else{
								MixpanelUtil.create_Child_account();
								dob = dateBoxUc.getDate();
								// TODO set the parent user details.
								underThirtheen = true;
								panelBelowThirteen.setVisible(true);
								popupbody.setStyleName("childUserInfoContainer");
								userDetailscontainer.setStyleName("firstInputGroup");
								if (panelAboveThirteen.isVisible()) {
									btnSignUp.setText(i18n.GL0460());
								}
								panelAboveThirteen.setVisible(false);
							}
						}

				} else {
					dateBoxUc.getDatePickerUc().hide();
				}
			}
		}
	}


	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	private class OnDateBlur implements BlurHandler {
		@Override
		public void onBlur(BlurEvent event) {
			dateBoxUc.removeStyleName(NewRegisterCBundle.INSTANCE.css()
					.gooruDateBoxError());
			dateBoxUc.getDateBox().removeStyleName(
					NewRegisterCBundle.INSTANCE.css().gooruDateError());
			if (dateBoxUc.getValue() != null){
				Date date = dateBoxUc.getValue();
				int age = getAge(date);
				if (age < 13) {
					if (account != null && !underThirtheen){
						dateValidationUc.setText(i18n.GL0503());
						dateValidationUc.setVisible(true);
						isValidUserName = false;
					}else{
						MixpanelUtil.create_Child_account();
						dob = dateBoxUc.getDate();
						// TODO set the parent user details.
						underThirtheen = true;
						panelBelowThirteen.setVisible(true);
						popupbody.setStyleName("childUserInfoContainer");
						userDetailscontainer.setStyleName("firstInputGroup");
						if (panelAboveThirteen.isVisible()) {
							btnSignUp.setText(i18n.GL0460());
						}
						panelAboveThirteen.setVisible(false);
					}
				}
			}
		}
	}
	/**
	 *
	 * @function getAge
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param birthDate
	 * @parm(s) : @return
	 *
	 * @return : int
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private int getAge(Date birthDate) {
		if (birthDate != null) {
			long ageInMillis = new Date().getTime() - birthDate.getTime();
			Date age = new Date(ageInMillis);
			return age.getYear() - 70;
		} else {
			return 0;
		}
	}

	/**
	 * Checks user availability with user email id and user account type
	 *
	 * @param accountType
	 *            user account type nonParent, parent, child
	 * @param isValid
	 *            true if email id satisfy the condition else false
	 * @param emailId
	 *            of the user that is mandatory to check availability
	 */
	private void checkAvailability(final String accountType, boolean isValid,
			final String emailId) {
		if (isValid) {
			try {
				AppClientFactory.getInjector().getUserService()
						.getEmailId(emailId, new SimpleAsyncCallback<UserDo>() {
							@Override
							public void onSuccess(UserDo user) {
								MixpanelUtil.Click_Go_Register_popup();
								validateAvailability(user, accountType, emailId);
							}
						});
			} catch (Exception e) {
			}
		}
	}

	/**
	 * validate the user availability after check availability calls, validate
	 * the email id already has been taken by some one and validate the confirm
	 * status of user account If emailId already has been taken by some one it
	 * brings alert popup
	 *
	 * @param user
	 *            instance of {@link UserDo} which has user all user info
	 * @param accountType
	 *            of user
	 * @param emailId
	 *            of user
	 */
	private void validateAvailability(UserDo user, String accountType,
			String emailId) {
		if (!user.isAvailability() && user.getConfirmStatus() == 0) {
			this.hide();
			if (parentRegisterVc != null) {
				parentRegisterVc.getPopupPanel().hide();
			}
			new RegistrationConfirmationVc();
			// registerUser(accountType, emailId, dob);
		} else if (user.isAvailability() && user.getConfirmStatus() == 0) {
			this.hide();
			Map<String, String> params = new HashMap<String, String>();
			params.put(GOORU_UID, user.getGooruUId());
			params.put(ACCOUNT_TYPE, accountType);
			sendConfirmationMail(params);
			new AlertContentUc(i18n.GL0065(),
					i18n.GL0092());

		} else if (user.isAvailability() && user.getConfirmStatus() == 1) {
			if (!accountType.equalsIgnoreCase(PARENT)) {
				new AlertContentUc(i18n.GL0065(),
						i18n.GL0214());
			} else {
				Map<String, String> params = new HashMap<String, String>();
				params.put(GOORU_UID, user.getGooruUId());
				params.put(ACCOUNT_TYPE, accountType);
				params.put(DATE_OF_BIRTH, dob);
				sendConfirmationMail(params);
				if (parentRegisterVc != null) {
					parentRegisterVc.getPopupPanel().hide();
				}
				new RegistrationConfirmationVc();
			}
		}
	}

	/**
	 * Send confirmation mail to user after successful registration
	 *
	 * @param params
	 *            contains emailId, accountType, dataOfBirth
	 */
	private void sendConfirmationMail(Map<String, String> params) {

		AppClientFactory
				.getInjector()
				.getUserService()
				.resendConfirmationMail(params,
						new SimpleAsyncCallback<Void>() {
							@Override
							public void onSuccess(Void result) {
								if (parentRegisterVc != null) {
									parentRegisterVc.getPopupPanel().hide();
								}
							}
						});
	}

	public abstract void closePoup();
	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	public class CheckProfanityInOnBlur implements BlurHandler{
		private TextBox textBox;
		private Label label;
		private RichTextArea richTextArea;
		private boolean isHavingBadWords;
		public CheckProfanityInOnBlur(TextBox textBox,RichTextArea richTextArea,Label label,boolean isHavingBadWords){
			this.textBox=textBox;
			this.label=label;
			this.richTextArea=richTextArea;
			this.isHavingBadWords=isHavingBadWords;

		}
		@Override
		public void onBlur(BlurEvent event) {


			Map<String, String> parms = new HashMap<String, String>();
			if(textBox!=null){
				parms.put("text", textBox.getValue());
			}else{
				parms.put("text", richTextArea.getText());
			}
			btnSignUp.setEnabled(false);
			AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

				@Override
				public void onSuccess(Boolean value) {
					btnSignUp.setEnabled(true);
					panelAboveThirteen.getElement().getStyle().setMarginTop(25, Unit.PX);
					SetStyleForProfanity.SetStyleForProfanityForTextBox(textBox, label, value);
					if(textBox!=null){
						label.getElement().getStyle().setPosition(Position.ABSOLUTE);
						label.getElement().getStyle().setWidth(87, Unit.PCT);
						errorLblForFirstName.getElement().getStyle().setTop(-3, Unit.PX);
					}

					if(userNameValidUc.isVisible() && errorLblForUsername.isVisible()){
						userNameValidUc.setVisible(false);
						label.getElement().getStyle().clearPosition();
					}else{
						label.getElement().getStyle().setPosition(Position.ABSOLUTE);
					}

					if(errorLblForLastName.isVisible()|| errorLblForFirstName.isVisible()){
						emailFieldContainer.getElement().setAttribute("style", "margin-top:30px;");
					}else{
						emailFieldContainer.getElement().removeAttribute("style");
					}
					if(errorLblForUsername.isVisible()){
						errorLblForUsername.getElement().getStyle().setTop(-8, Unit.PX);
						errorLblForUsername.getElement().getStyle().setLeft(16,  Unit.PX);
					}else{
						panelAboveThirteen.getElement().getStyle().clearMarginTop();
					}
				}
			});
		}
	}
	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	public class MouseoverQuestion implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			// TODO Auto-generated method stub
			tootltipContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		}

	}
	/**
	 *
	 * @fileName : CreateAccountUc.java
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
	public class MouseOutQuestion implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			// TODO Auto-generated method stub
			tootltipContainer.getElement().getStyle().setDisplay(Display.NONE);

		}



	}
}
