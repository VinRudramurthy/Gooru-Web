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
package org.ednovo.gooru.client.mvp.authentication;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * 
 * @fileName : SignUpCBundle.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 20-09-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface SignUpCBundle extends ClientBundle {
	
	static final SignUpCBundle INSTANCE = GWT.create(SignUpCBundle.class);
	
	public interface SignUp extends CssResource{
		
		String iconPosition();
		String emphasis();

		String popupGlass();

		String popupInner();

		String popupHeader();

		String closeButton ();

		String popupContent();
		
		String tooltipContent ();

		String tooltipContentTitle();

		String inputRow();
		
		String errorRowFirstName();
		
		String inputRowWidth();
		
		String inputRowWidthRight();

		String horizontalTooltipContainer();

		String horizontalArrow();

		String arrowBorder ();
		
		String arrow ();

		String interests(); 

		String questionHover();

		String questionHoverContainer(); 

		String tooltipContainer ();

		/******************************************************* page content ****/

		String popup();
		
		String imgBG();

		String h1();

		String subheader();

		String h2();

		String leftContainer();

		String rightContainer();
		
		String rightContainerShort();

		String signInContainer();

		String gConnectButton();

		String gConnectWhy();

		String divider();

		String dividerText ();

		String emailSignIn();

		String haveAccount();

		String footer();

		String userInfoContainer();

		String role();

		String roleOption();

		String roleOptionImage();

		String roleOptionInput();

		String teacherRole();

		String studentRole();

		String parentRole();

		String otherRole();

		String congratsHeader();

		String congratsTop(); 

		String header();

		String leftAlign();

		String skipSubmit();

		String gooruReminder();

		String ssoLoginMethodsText();

		String supportMessage();

		String DOTSbuttons();

		String h6();

		String selectGrades();

		String gradeOption();

		String selectCourses();

		String subjectTabs();

		String courseContainer();

		String subjectOption();

		String active();

		//span();

		String selected();

		String courseOption(); 
		
		String title();

		String verticallyCentered();

		String metadataSelection(); 

		String startUsingGooru();

		String ssoEmail();

		String mpTwoLoginOptions();
		
		String left();

		String right();

		String tooltipImage();
		
		String chooseUsername();

		String halfSize();

		String agreeToPolicies();
		
		String emailReg();
		
		String longWidth();
		
		String popupBackground();
		
		String whyGoogle();
		
		String alreadyHaveContainer();
		
		String roleLabel();
		
		String userCreateContainer();
		
		String dob();
		
		String gooruDateBox();
		
		String errorMsgDisplay();

		String loginTextBoxMargin();

		String thanksPopup();
		
		String thanksPopupContent();
		
		String userNameTxtBox();
		
		String pleaseWait();
		
		String error();
		
		String errorLbl();
		
		String errorRow();
		
		String dobError();
		
		String loadingImage();
		String termsAlignStyle();
		
		String belowThirteenPanel();
		
		String parentsAccount();
		
		String questionHoverContainerParentAccount();
		
		String parentEmailContainer();
		
		String rightIconImage();
		
		String orBoder();
		
		String orBoderLeftSide();
		
		String orBoderRightSide();
		
		String orContainerDown();
		
		String parentEmailError();
		
		String parentEmail();
		
		String parentEmailIdContainer();
		
		String termsAndConditionContainer();
		
		String studentRoleDefault();
		
		String termsAndConditionContainerAlign();
		
		String createStudentAccount();
		
		String green();
		
		String teacherRoleSelected();

		String studentRoleSelected();

		String parentRoleSelected();

		String otherRoleSelected();
		
		String chooseUsernameInStu();
		
		String leaveRegistrationBottomContainer();
		
		String leaveRegistarionFooterText();
		
		String popupInnerLeaveReg();
		
		String leaveRegistrationButtonContainer();
		
		String popupContentLeaveRegistration();
		
		String signUpButtonStyle();
		
		String stuEmailContainer();
		
		String turnsThirteenButtonContainer();
		
		String popupcornerBg();
		
		String usersRight();
		
		String changePassowrdlabel();
		
		String buttonContainer();
		
		String popupFooter();
		
		String inputTextStyle();
		
		String textAreaStyle();
		
		String userWhiteBg();
		
		String userLabel();
		
		String users();
		
		String popupFooterText();
		
		String profilePopup();
		
		String editBtn();
		
		String popupContentProfilePage();
				
		String imgBGprofile();
		
		String subheaderprofile();
		
		String userRegErrorStylePPPFName();
		
		String userRegErrorStylePPPLName();
		
		String h2Header();
		
		String updatingText();
		
		String h2DontWHeader();
		
		String profilePasswordError();
		
		String errorContainer();

		String stuEmailTopContainer();
		
		String afterThirteenh2Container();
		
		String mathActive();
		
		String ssActive();
		
		String elaActive();
		
		String pleaseWaitSS();
		
		String subHeaderTurnsThirteen();
		
		String subjectOptionMath();
		
		String subjectOptionSS();
		
		String subjectOptionELA();

		String signUpPopUpGlassCss();
		String paddingClear();
	}
	@Source("SignUp.css")
	SignUp css();
}
