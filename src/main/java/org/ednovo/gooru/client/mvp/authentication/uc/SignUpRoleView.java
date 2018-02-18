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

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.authentication.SignUpCBundle;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
/**
 *
 * @fileName : SignUpRoleView.java
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
public class SignUpRoleView extends PopupPanel{

	@UiField Label lblTitle, lblCancel,teachLbl,studentLbl,parentLbl,otherLbl;

	@UiField HTMLPanel teacherRg, studentRg, parentRg, otherRg,oneMoreStepText,fillText;

	@UiField TextBoxWithPlaceholder loginTxtBox;

	@UiField Button submitRegistration;

	private RadioButton teacherRb, studentRb, parentRb, otherRb;

	private String userRole = null;

	@UiField(provided = true)
	SignUpCBundle res;

	private static SignUpRoleViewUiBinder uiBinder = GWT
			.create(SignUpRoleViewUiBinder.class);

	interface SignUpRoleViewUiBinder extends UiBinder<Widget, SignUpRoleView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public SignUpRoleView(String email, UserDo userDo) {
		this.res = SignUpCBundle.INSTANCE;
		res.css().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));
		oneMoreStepText.getElement().setInnerText(i18n.GL0898()+i18n.GL_SPL_EXCLAMATION());
		oneMoreStepText.getElement().setId("pnlOneMoreStepText");
		oneMoreStepText.getElement().setAttribute("alt",i18n.GL0898());
		oneMoreStepText.getElement().setAttribute("title",i18n.GL0898());

		fillText.getElement().setInnerText(i18n.GL0953());
		fillText.getElement().setId("pnlFillText");
		fillText.getElement().setAttribute("alt",i18n.GL0953());
		fillText.getElement().setAttribute("title",i18n.GL0953());

		teachLbl.setText(i18n.GL0416());
		teachLbl.getElement().setId("lblTeach");
		teachLbl.getElement().setAttribute("alt",i18n.GL0416());
		teachLbl.getElement().setAttribute("title",i18n.GL0416());

		studentLbl.setText(i18n.GL0417());
		studentLbl.getElement().setId("lblStudent");
		studentLbl.getElement().setAttribute("alt",i18n.GL0417());
		studentLbl.getElement().setAttribute("title",i18n.GL0417());

		parentLbl.setText(i18n.GL0418());
		parentLbl.getElement().setId("lblParent");
		parentLbl.getElement().setAttribute("alt",i18n.GL0418());
		parentLbl.getElement().setAttribute("title",i18n.GL0418());

		otherLbl.setText(i18n.GL0419());
		otherLbl.getElement().setId("lblOther");
		otherLbl.getElement().setAttribute("alt",i18n.GL0419());
		otherLbl.getElement().setAttribute("title",i18n.GL0419());

		submitRegistration.setText(i18n.GL0486());
		teacherRb = new RadioButton("roleRadioGroup", "");
		studentRb = new RadioButton("roleRadioGroup", "");
		parentRb = new RadioButton("roleRadioGroup", "");
		otherRb = new RadioButton("roleRadioGroup", "");
		teacherRb.addClickHandler(new TeacherRbClick());
		studentRb.addClickHandler(new StudentRbClick());
		parentRb.addClickHandler(new ParentRbClick());
		otherRb.addClickHandler(new OtherRbClick());

		teacherRg.add(teacherRb);
		teacherRg.getElement().setId("rdTeacher");

		studentRg.add(studentRb);
		studentRg.getElement().setId("rdStudent");

		parentRg.add(parentRb);
		parentRg.getElement().setId("rdParent");

		otherRg.add(otherRb);
		otherRg.getElement().setId("rdOther");

		submitRegistration.getElement().setId("submitRegistration");

		loginTxtBox.addStyleName(res.css().loginTextBoxMargin());
        loginTxtBox.getElement().setAttribute("placeholder", "Username");
        loginTxtBox.getElement().setId("txtLogin");

        lblTitle.setText(i18n.GL0186() + i18n.GL_SPL_EXCLAMATION());
		lblTitle.getElement().setId("lblTitle");
		lblTitle.getElement().setAttribute("alt",i18n.GL0186());
		lblTitle.getElement().setAttribute("title",i18n.GL0186());

		lblCancel.getElement().setId("lblCancel");

		this.setGlassEnabled(true);
		this.show();
		this.center();

	}
	/**
	 *
	 * @function closeSignUpRoleView
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
	public void closeSignUpRoleView() {
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		this.hide();
	}

	@UiHandler("lblCancel")
	public void onCancelClick(ClickEvent clickEvent) {
		closeSignUpRoleView();
	}
	/**
	 *
	 * @fileName : SignUpRoleView.java
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
	private class TeacherRbClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			userRole = "Teacher";
		}
	}
	/**
	 *
	 * @fileName : SignUpRoleView.java
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
	private class StudentRbClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			userRole = "Student";
		}
	}

	private class ParentRbClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			userRole = "Parent";
		}
	}

	private class OtherRbClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			userRole = "Other";
		}
	}

	@UiHandler("submitRegistration")
	public void onClickSubmitRegistration(ClickEvent event) {
		checkUserAvailability(loginTxtBox.getText(), "username");
	}

	/**
	 * Checks the availability of user name, entered by User.
	 * @param userName
	 * @param type
	 *
	 */
	public void checkUserAvailability(String userName, String type) {
		AppClientFactory.getInjector().getUserService().getEmailId(userName, type, new SimpleAsyncCallback<UserDo>()
		{
				@Override
				public void onSuccess(UserDo result) {
					checkUserNameAvailability(result);
				}
		});
	}

	/**
	 * If username exists, display alert message else proceed further.
	 * @param result {{@link UserDo}
	 */

	public void checkUserNameAvailability(UserDo result) {
		if (result != null && result.isAvailability() && loginTxtBox.getText() != null) {

		}
		else
		{
			String userName = loginTxtBox.getText();
			String userRole = this.userRole;
			final UserDo userDo = AppClientFactory.getLoggedInUser();

			AppClientFactory.getInjector().getHomeService().updateUserDetails(userName, userRole,new SimpleAsyncCallback<Void>(){
				@Override
				public void onSuccess(Void result) {
					AppClientFactory.getInjector().getUserService().updateUserViewFlag(userDo.getGooruUId(), 1, new SimpleAsyncCallback<UserDo>() {
						@Override
						public void onSuccess(UserDo newUser) {
							UserDo userDo = AppClientFactory.getLoggedInUser();
							userDo.setViewFlag(newUser.getViewFlag());
							AppClientFactory.setLoggedInUser(userDo);
							AppClientFactory.fireEvent(new SetHeaderEvent(newUser));
							closeSignUpRoleView();
							SignUpGradeCourseView signUpGradeCourseView = new SignUpGradeCourseView(userDo);
						}
					});
					Window.enableScrolling(false);
					AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, false));
				}
			});
		}
	}

}
