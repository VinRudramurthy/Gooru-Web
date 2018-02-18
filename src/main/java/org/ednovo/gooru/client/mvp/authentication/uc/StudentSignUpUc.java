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

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.authentication.SignUpCBundle;
import org.ednovo.gooru.client.mvp.faq.CopyRightPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsAndPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.DateBoxUc;
import org.ednovo.gooru.client.uc.ErrorLabelUc;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.WhiteSpace;
import com.google.gwt.event.dom.client.ClickEvent;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : StudentSignUpUc.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: Dec 9, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class StudentSignUpUc extends PopupPanel implements ClientConstants{

	private static StudentSignUpUcUiBinder uiBinder = GWT
			.create(StudentSignUpUcUiBinder.class);

	interface StudentSignUpUcUiBinder extends UiBinder<Widget, StudentSignUpUc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField(provided = true)
	SignUpCBundle res;
	@UiField Label lblCancel,lblTitle,lblStuDes,lblParentEmailId,lblTxtParent,lblPickWisely,lblQuestionMark,lblWhyEnterBirthday,lblWhyEnterBirthdayDesc;
	@UiField SimplePanel sPanelDateOfBirth;

	@UiField
	ErrorLabelUc userNameValidUc,dateValidationUc,lblSelectRole,passwordValidUc;
	@UiField HTMLPanel panelUserNamePopUp,panelSignUp;
	@UiField TextBoxWithPlaceholder txtChooseUsername,txtChoosePassword,txtConfirmPassword;
	@UiField Label lblTeacher,lblStudent, lblParent, lblOther,lblPasswordTooltipContent,lblPleaseWait;
	@UiField HTMLPanel rdTeacher, rdStudent, rdParent, rdOther,panelPassword;
	@UiField Button btnSignUp;
	@UiField Anchor ancCopyRight, ancTermsAndPrivacy,ancPrivacy;
	@UiField HTMLEventPanel panelDataOfBirth;
	@UiField InlineLabel lblAgree,andText;

	private String privateGooruUId = null;

	private String homeEndPoint = null;

	private static final String FIRST_NAME = "firstName";

	private static final String USER_NAME = "username";

	private static final String PASSWORD = "password";

	private static final String EMAIL_ID = "emailId";

	private static final String ORGANIZATION_CODE = "organizationCode";

	private static final String LAST_NAME = "lastName";

	private static final String GOORU = "gooru";

	RadioButton rbTeacher;
	RadioButton rbStudent;
	RadioButton rbParent;
	RadioButton rbOther;
	private static final String PWD_PATTERN = "[0-9]|[$@!#*%^/[/]}{()_&-+=.,<>;\\|]";

	private DateBoxUc dateBoxUc;
	/**
	 *
	 * @param emailId
	 * @param username
	 * @param dob
	 */
	public StudentSignUpUc(String emailId,String username,String dob, String privateGooruUId) {
		super(false);
		this.res = SignUpCBundle.INSTANCE;
		res.css().ensureInjected();
		this.privateGooruUId = privateGooruUId;
		setWidget(uiBinder.createAndBindUi(this));
		this.setGlassEnabled(true);
		this.center();
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		this.addStyleName(SignUpCBundle.INSTANCE.css().popupBackground());
		this.setGlassStyleName(SignUpCBundle.INSTANCE.css().signUpPopUpGlassCss());

		lblTitle.getElement().setAttribute("style", "height: 17px");
		lblParentEmailId.setText(emailId != null && !emailId.equalsIgnoreCase("") ?" "+ emailId :" "+ AppClientFactory.getPlaceManager().getRequestParameter("emailId"));
		lblParentEmailId.getElement().getStyle().setWhiteSpace(WhiteSpace.PRE);
		lblParentEmailId.getElement().setId("lblParentEmailId");
		lblParentEmailId.getElement().setAttribute("alt",emailId != null && !emailId.equalsIgnoreCase("") ? emailId : AppClientFactory.getPlaceManager().getRequestParameter("emailId"));
		lblParentEmailId.getElement().setAttribute("title",emailId != null && !emailId.equalsIgnoreCase("") ? emailId : AppClientFactory.getPlaceManager().getRequestParameter("emailId"));

		txtChooseUsername.setText(username);
		txtChooseUsername.setReadOnly(true);
		lblPleaseWait.setVisible(false);
		setUiAndIds(dob);
		txtChoosePassword.setFocus(true);

		AppClientFactory.getInjector().getSearchService().getHomeEndPointUrl(new SimpleAsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				homeEndPoint = result;
			}
		});

	}
	@UiHandler("lblCancel")
	public void onClickLblCancel(ClickEvent event){
		this.hide();
		LeaveRegistrationPopUpUc leaveRegistrationPopUpUc=new LeaveRegistrationPopUpUc(i18n.GL1394(),lblParentEmailId.getText(),txtChooseUsername.getValue(),dateBoxUc.getDateBox().getText());
		leaveRegistrationPopUpUc.show();
	}
	/**
	 *
	 * @function setUiAndIds
	 *
	 * @created_date : Dec 9, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param dob
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void setUiAndIds(String dob){
		lblStuDes.setText(i18n.GL0467());
		lblStuDes.getElement().setId("lblStuDes");
		lblStuDes.getElement().setAttribute("alt",i18n.GL0467());
		lblStuDes.getElement().setAttribute("title",i18n.GL0467());

		lblTitle.setText(i18n.GL0186() + i18n.GL_SPL_EXCLAMATION());
		lblTitle.getElement().setId("lblTitle");
		lblTitle.getElement().setAttribute("alt",i18n.GL0186());
		lblTitle.getElement().setAttribute("title",i18n.GL0186());

		lblCancel.getElement().setId("lblCancel");
		panelSignUp.getElement().setId("pnlSignUp");

		lblTxtParent.setText(i18n.GL0468());
		lblTxtParent.getElement().setId("lblTxtParent");
		lblTxtParent.getElement().setAttribute("alt",i18n.GL0468());
		lblTxtParent.getElement().setAttribute("title",i18n.GL0468());

		dateBoxUc = new DateBoxUc(true, true,true);
		sPanelDateOfBirth.add(dateBoxUc);
		sPanelDateOfBirth.getElement().setId("spnlDateOfBirth");

		dateBoxUc.getDateBox().setText(dob);
		panelUserNamePopUp.setVisible(false);
		panelUserNamePopUp.getElement().setId("pnlUserNamePopup");

		panelPassword.setVisible(false);
		panelPassword.getElement().setId("pnlPassword");

		lblPickWisely.setText(i18n.GL0410());
		lblPickWisely.getElement().setId("lblPickWisely");
		lblPickWisely.getElement().setAttribute("alt",i18n.GL0410());
		lblPickWisely.getElement().setAttribute("title",i18n.GL0410());

		txtChooseUsername.getElement().setId("txtChooseUserName");
		userNameValidUc.getElement().setId("errlblUserNameValidUc");
		dateValidationUc.getElement().setId("errlblDateValidationUc");
		passwordValidUc.getElement().setId("errlblPasswordValidUc");

		txtChoosePassword.setPlaceholder(i18n.GL0204());
		txtChoosePassword.getElement().setId("pswChoosePassword");

		txtConfirmPassword.setPlaceholder(i18n.GL0427());
		txtConfirmPassword.getElement().setId("pswConfirmPassword");
		passwordValidUc.getElement().getStyle().setMarginLeft(0, Unit.PX);
		passwordValidUc.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		lblQuestionMark.setText(i18n.GL_SPL_QUESTION());
		lblQuestionMark.getElement().setId("lblQuestionMark");
		lblQuestionMark.getElement().setAttribute("alt",i18n.GL_SPL_QUESTION());
		lblQuestionMark.getElement().setAttribute("title",i18n.GL_SPL_QUESTION());

		lblWhyEnterBirthday.setText(i18n.GL0411() + i18n.GL_SPL_QUESTION());
		lblWhyEnterBirthday.getElement().setId("lblWhyEnterBirthday");
		lblWhyEnterBirthday.getElement().setAttribute("alt",i18n.GL0411());
		lblWhyEnterBirthday.getElement().setAttribute("title",i18n.GL0411());

		lblWhyEnterBirthdayDesc.setText(i18n.GL0412());
		lblWhyEnterBirthdayDesc.getElement().setId("lblWhyEnterBirthdayDesc");
		lblWhyEnterBirthdayDesc.getElement().setAttribute("alt",i18n.GL0412());
		lblWhyEnterBirthdayDesc.getElement().setAttribute("title",i18n.GL0412());

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

		panelDataOfBirth.getElement().setId("pnlDateOfBirth");

		rbTeacher = new RadioButton("roleOption","");
		rbStudent = new RadioButton("roleOption","");
		rbParent = new RadioButton("roleOption","");
		rbOther = new RadioButton("roleOption","");
		btnSignUp.setText(i18n.GL0186());
		btnSignUp.getElement().setId("btnSignUp");
		btnSignUp.getElement().setAttribute("alt",i18n.GL0186());
		btnSignUp.getElement().setAttribute("title",i18n.GL0186());

		lblAgree.setText(i18n.GL0420());
		lblAgree.getElement().setId("lblAgree");
		lblAgree.getElement().setAttribute("alt",i18n.GL0420());
		lblAgree.getElement().setAttribute("title",i18n.GL0420());

		ancCopyRight.setText(" "+i18n.GL0421()+",");
		ancCopyRight.getElement().setId("lnkCopyRight");
		ancCopyRight.getElement().setAttribute("alt",i18n.GL0421());
		ancCopyRight.getElement().setAttribute("title",i18n.GL0421());

		ancTermsAndPrivacy.setText(i18n.GL0422());
		ancTermsAndPrivacy.getElement().setId("lnkTermsAndPrivacy");
		ancTermsAndPrivacy.getElement().setAttribute("alt",i18n.GL0422());
		ancTermsAndPrivacy.getElement().setAttribute("title",i18n.GL0422());

		ancPrivacy.setText(" "+i18n.GL0452());
		ancPrivacy.getElement().setId("lnkPrivacy");
		ancPrivacy.getElement().setAttribute("alt",i18n.GL0422());
		ancPrivacy.getElement().setAttribute("title",i18n.GL0422());

		lblPasswordTooltipContent.setText(i18n.GL0415());
		lblPasswordTooltipContent.getElement().setId("lblPasswordToolTipContent");
		lblPasswordTooltipContent.getElement().setAttribute("alt",i18n.GL0415());
		lblPasswordTooltipContent.getElement().setAttribute("title",i18n.GL0415());

		rbStudent.setChecked(true);
		MixpanelUtil.select_student();
		lblSelectRole.setText(i18n.GL1146());
		lblSelectRole.getElement().setId("lblSelectRole");
		lblSelectRole.getElement().setAttribute("alt",i18n.GL1146());
		lblSelectRole.getElement().setAttribute("title",i18n.GL1146());

		andText.setText(i18n.GL_GRR_AND());
		andText.getElement().setId("spnAndText");
		andText.getElement().setAttribute("alt",i18n.GL_GRR_AND());
		andText.getElement().setAttribute("title",i18n.GL_GRR_AND());

		lblSelectRole.setVisible(false);

		lblPleaseWait.setText(i18n.GL0469());
		lblPleaseWait.getElement().setId("lblPleaseWait");
		lblPleaseWait.getElement().setAttribute("alt",i18n.GL0469());
		lblPleaseWait.getElement().setAttribute("title",i18n.GL0469());

		txtChooseUsername.addMouseOverHandler(new OnMouseOver());
		txtChoosePassword.addMouseOverHandler(new OnMouseOver());
		txtConfirmPassword.addMouseOverHandler(new OnMouseOver());
		panelDataOfBirth.addMouseOverHandler(new OnMouseOver());

		txtChooseUsername.addMouseOutHandler(new OnMouseOut());
		txtChoosePassword.addMouseOutHandler(new OnMouseOut());
		txtConfirmPassword.addMouseOutHandler(new OnMouseOut());
		panelDataOfBirth.addMouseOutHandler(new OnMouseOut());

		txtChoosePassword.addKeyUpHandler(new OnKeyUpHandler());
		txtConfirmPassword.addKeyUpHandler(new OnKeyUpHandler());

		rbParent.setEnabled(false);
		rbTeacher.setEnabled(false);
		rbOther.setEnabled(false);
		rdTeacher.add(rbTeacher);
		rdTeacher.getElement().setId("rdTeacher");

		rdStudent.add(rbStudent);
		rdStudent.getElement().setId("rdStudent");

		rdParent.add(rbParent);
		rdParent.getElement().setId("rdParent");

		rdOther.add(rbOther);
		rdOther.getElement().setId("rdOther");

	}
	private TermsAndPolicyVc termsAndPolicyVc;
	private CopyRightPolicyVc copyRightPolicy;
	private TermsOfUse termsOfUse;


	@UiHandler("ancTermsAndPrivacy")
	public void onClickTrems(ClickEvent event){
		Window.enableScrolling(false);
		termsOfUse = new TermsOfUse() {
			@Override
			public void openParentPopup() {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98,false));
			}
		};

		termsOfUse.show();
		termsOfUse.center();
		termsOfUse.getElement().getStyle().setZIndex(999);

	}

	/**
	 * Opens up Privacy pop-up.
	 *
	 * @param event instance of {@link ClickEvent}
	 */

	@UiHandler("ancPrivacy")
	public void onClickPrivacy(ClickEvent event){
		Window.enableScrolling(false);
		termsAndPolicyVc = new TermsAndPolicyVc(false) {
			@Override
			public void openParentPopup() {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98,false));
			}
		};

		termsAndPolicyVc.show();
		termsAndPolicyVc.center();
		termsAndPolicyVc.getElement().getStyle().setZIndex(999);

	}

	/**
	 * Opens up Copy rights pop-up.
	 *
	 * @param event instance of {@link ClickEvent}
	 */

	@UiHandler("ancCopyRight")
	public void onClickCopyright(ClickEvent event){
		Window.enableScrolling(false);
		copyRightPolicy = new  CopyRightPolicyVc() {
			@Override
			public void openParentPopup() {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98,false));
			}
		};

		copyRightPolicy.show();
		copyRightPolicy.center();
		copyRightPolicy.getElement().getStyle().setZIndex(999);
	}
	/**
	 *
	 * @fileName : StudentSignUpUc.java
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
	private class OnMouseOver implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			if (event.getSource() ==  txtChooseUsername || event.getSource() == panelDataOfBirth){
				panelUserNamePopUp.setVisible(true);
			}else if (event.getSource() == txtChoosePassword || event.getSource() == txtConfirmPassword){
				panelPassword.setVisible(true);
			}
		}
	}
	/**
	 *
	 * @fileName : StudentSignUpUc.java
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
	private class OnMouseOut implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			panelUserNamePopUp.setVisible(false);
			panelPassword.setVisible(false);
		}

	}
	/**
	 *
	 * @function validateUserInput
	 *
	 * @created_date : Dec 9, 2013
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
	public boolean validateUserInput(){
		boolean isValid = true;
		String userName = txtChooseUsername.getText().trim();
		String password = txtChoosePassword.getText().trim();
		String confirmPassword = txtConfirmPassword.getText().trim();

		if (userName.equalsIgnoreCase("") || userName == null){
			txtChooseUsername.addStyleName(res.css().errorMsgDisplay());
			isValid= false;
		}
		if (password.equalsIgnoreCase("") || password == null){
			txtChoosePassword.addStyleName(res.css().errorMsgDisplay());
			isValid= false;
		}
		if (confirmPassword.equalsIgnoreCase("") || confirmPassword == null){
			txtConfirmPassword.addStyleName(res.css().errorMsgDisplay());
			isValid= false;
		}
		
		if (!password.equalsIgnoreCase(confirmPassword)) {
			txtConfirmPassword.addStyleName(res.css().errorMsgDisplay());
			txtChoosePassword.addStyleName(res.css().errorMsgDisplay());
			passwordValidUc.setText(i18n.GL0446());
			passwordValidUc.getElement().setAttribute("alt",i18n.GL0446());
			passwordValidUc.getElement().setAttribute("title",i18n.GL0446());
			passwordValidUc.setVisible(true);
			isValid = false;
		}
		
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
		return isValid;

	}
	/**
	 *
	 * @fileName : StudentSignUpUc.java
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
			if (event.getSource() == txtChoosePassword) {
				txtChoosePassword.removeStyleName(res.css().errorMsgDisplay());
			} else if (event.getSource() == txtConfirmPassword) {
				txtConfirmPassword.removeStyleName(res.css().errorMsgDisplay());
			}
		}
	}
	@UiHandler("btnSignUp")
	public void onClickSignUp(ClickEvent event){
		lblPleaseWait.setVisible(true);
		btnSignUp.setVisible(false);
		if (validateUserInput()){
			checkUserNameAvailability();
		}else{
			lblPleaseWait.setVisible(false);
			btnSignUp.setVisible(true);
		}
	}
	/**
	 *
	 * @function checkUserNameAvailability
	 *
	 * @created_date : Dec 9, 2013
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
	public void checkUserNameAvailability() {
		MixpanelUtil.sign_up_Child_registration();
		String userRole = "student";

		final String userName = txtChooseUsername.getText();
		String emilId = lblParentEmailId.getText();
		final String password = StringUtil.getCryptoData(txtChoosePassword.getText().trim());
		String confirmPassword = txtConfirmPassword.getText().trim();
		Map<String, String> registrationDetailsParams = new HashMap<String, String>();
		registrationDetailsParams.put(USER_NAME, userName);
		registrationDetailsParams.put(EMAIL_ID, emilId);
		registrationDetailsParams.put(ORGANIZATION_CODE, GOORU);
		registrationDetailsParams.put(FIRST_NAME, "Child");
		registrationDetailsParams.put(LAST_NAME, "User");

		registrationDetailsParams.put(PASSWORD, password);
		registrationDetailsParams.put("gooruBaseUrl", homeEndPoint + "#discover");
		registrationDetailsParams.put("role", userRole);
		registrationDetailsParams.put("dateOfBirth", dateBoxUc.getDateBox().getText());

		registrationDetailsParams.put("accountType", "Child");
		registrationDetailsParams.put("userParentId", AppClientFactory.isAnonymous() ? privateGooruUId : AppClientFactory.getLoggedInUser().getGooruUId());

		AppClientFactory.getInjector().getUserService().createUser(registrationDetailsParams,CHILD_REG,new SimpleAsyncCallback<UserDo>() {
				@Override
				public void onSuccess(UserDo result) {
					if (result.getGooruUId() != null && !result.getGooruUId().equalsIgnoreCase("")) {
						AppClientFactory.getInjector().getAppService().v2Signin(userName,password,new SimpleAsyncCallback<UserDo>() {
							@Override
							public void onSuccess(UserDo result) {
								hide();
								AppClientFactory.setLoggedInUser(result);
								AppClientFactory.fireEvent(new SetHeaderEvent(result));
								SignUpGradeCourseView gradeCourseView = new SignUpGradeCourseView(result);
							}
						});
					}
				}
			});
	}
}
