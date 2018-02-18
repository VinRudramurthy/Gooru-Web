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

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.application.shared.model.user.V2UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.authentication.uc.SignUpGradeCourseView;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.ErrorLabelUc;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru Team
 *
 */


public class AlmostDoneUc extends PopupPanel{


	private UserDo user=null;

	@UiField
	HTMLPanel rdTeacher, rdStudent, rdParent, rdOther, panelOther, panelTeacher, panelStudent,panelParent,panelUsernameTooltip;

	@UiField
	ErrorLabelUc lblSelectRole, userNameValidUc;

	@UiField
	TextBoxWithPlaceholder txtChooseUsername;

	@UiField
	Label lblHeader,lblTeacher, lblStudent, lblParent, lblOther, lblUserEmail, lblToolTipOther,lblOtherDesc, lblJoin, lblSubHeader, lblTeacherContainer, lblTeacherDesc, lblTooltipStudent, lblStudentDesc, lblTooltipParent, lblParentDesc, lblPickWisely, lblClose;

	@UiField
	Button btnSubmit;


	RadioButton rbTeacher;
	RadioButton rbStudent;
	RadioButton rbParent;
	RadioButton rbOther;

	boolean isHavingBadWordsUserName=false;

	private String selectedRole = null;
	String USER_NAME_REGEX = "[A-Za-z0-9^]*";

	@UiField(provided = true)
	AlmostDoneUcCBundle res;

	@UiTemplate("AlmostDoneUc.ui.xml")
	interface Binder extends UiBinder<Widget,AlmostDoneUc>
	{

	}
	private static final Binder binder = GWT.create(Binder.class);
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	/**
	 * Class constructor , to create Almost done popup
	 * @param userEmail
	 * @param user {@link UserDo}
	 */


	public AlmostDoneUc(String userEmail, UserDo user) {
		super(false);

		this.res = AlmostDoneUcCBundle.INSTANCE;
		res.css().ensureInjected();
		add(binder.createAndBindUi(this));
		this.setGlassEnabled(true);

		this.getElement().getStyle().setWidth(512, Unit.PX);
		this.getElement().getStyle().setHeight(500, Unit.PX);
		this.getElement().getStyle().setBackgroundColor("transparent");

		this.center();
		this.user = user;

		txtChooseUsername.addBlurHandler(new CheckProfanityInOnBlur(txtChooseUsername,null, userNameValidUc, isHavingBadWordsUserName));
		txtChooseUsername.addMouseOverHandler(new OnMouseOver());
		txtChooseUsername.addMouseOutHandler(new OnMouseOut());

		lblTeacher.setText(i18n.GL0416());
		StringUtil.setAttributes(lblTeacher.getElement(), "lblTeacher", i18n.GL0416(), i18n.GL0416());

		lblStudent.setText(i18n.GL0417());
		StringUtil.setAttributes(lblStudent.getElement(), "lblStudent", i18n.GL0417(), i18n.GL0417());

		lblParent.setText(i18n.GL0418());
		StringUtil.setAttributes(lblParent.getElement(), "lblParent", i18n.GL0418(), i18n.GL0418());

		lblOther.setText(i18n.GL0419());
		StringUtil.setAttributes(lblOther.getElement(), "lblOther", i18n.GL0419(), i18n.GL0419());

		lblUserEmail.setText(StringUtil.generateMessage(i18n.GL2194(), AppClientFactory.getLoggedInUser() != null ? AppClientFactory.getLoggedInUser().getEmailId() : ""));

		txtChooseUsername.setPlaceholder(i18n.GL0423());
		txtChooseUsername.getElement().setId("txtChooseUsername");
		txtChooseUsername.setMaxLength(20);
		txtChooseUsername.setText(user.getUsername());

		rbTeacher = new RadioButton("roleOption", "");
		rbStudent = new RadioButton("roleOption", "");
		rbParent = new RadioButton("roleOption", "");
		rbOther = new RadioButton("roleOption", "");

		lblSelectRole.getElement().setId("lblSelectRole");

		lblToolTipOther.setText(i18n.GL0419());
		lblOtherDesc.setText(i18n.GL2196());

		rbTeacher.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MixpanelUtil.select_teacher();
				selectedRole = "teacher";
				lblSelectRole.setVisible(false);
				if (rbTeacher.getValue()){
					//Remove normal and Set Selected Image
					panelTeacher.getElement().addClassName(res.css().teacherRoleSelected());
				}
				//Remove selected image and set normal
				panelOther.getElement().removeClassName(res.css().otherRoleSelected());
//				panelTeacher.getElement().removeClassName(res.css().teacherRoleSelected());
				panelStudent.getElement().removeClassName(res.css().studentRoleSelected());
				panelParent.getElement().removeClassName(res.css().parentRoleSelected());
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
					panelStudent.getElement().addClassName(res.css().studentRoleSelected());
				}
				//Remove selected image and set normal
				panelOther.getElement().removeClassName(res.css().otherRoleSelected());
				panelTeacher.getElement().removeClassName(res.css().teacherRoleSelected());
//				panelStudent.getElement().removeClassName(res.css().studentRoleSelected());
				panelParent.getElement().removeClassName(res.css().parentRoleSelected());

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
					panelParent.getElement().addClassName(res.css().parentRoleSelected());
				}
				//Remove selected image and set normal
				panelOther.getElement().removeClassName(res.css().otherRoleSelected());
				panelTeacher.getElement().removeClassName(res.css().teacherRoleSelected());
				panelStudent.getElement().removeClassName(res.css().studentRoleSelected());
				//panelParent.getElement().removeClassName(res.css().parentRoleSelected());
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
					panelOther.getElement().addClassName(res.css().otherRoleSelected());
				}
				//Remove selected image and set normal
				//panelOther.getElement().removeClassName(res.css().otherRoleSelected());
				panelTeacher.getElement().removeClassName(res.css().teacherRoleSelected());
				panelStudent.getElement().removeClassName(res.css().studentRoleSelected());
				panelParent.getElement().removeClassName(res.css().parentRoleSelected());

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

		btnSubmit.setEnabled(false);
		btnSubmit.getElement().addClassName("disabled");


		lblJoin.setText(i18n.GL0400());
		StringUtil.setAttributes(lblJoin.getElement(), "lblJoin", i18n.GL0400(), i18n.GL0400());
		lblSubHeader.setText(i18n.GL2197());
		StringUtil.setAttributes(lblSubHeader.getElement(), "lblSubHeader", i18n.GL2197(), i18n.GL2197());
		lblTeacherContainer.setText(i18n.GL0416());
		StringUtil.setAttributes(lblTeacherContainer.getElement(), "lblTeacherContainer", i18n.GL0416(), i18n.GL0416());
		lblTeacherDesc.setText(i18n.GL2198());
		StringUtil.setAttributes(lblTeacherDesc.getElement(), "lblTeacherDesc", i18n.GL2198(), i18n.GL2198());
		lblTooltipStudent.setText(i18n.GL0417());
		StringUtil.setAttributes(lblTooltipStudent.getElement(), "lblTooltipStudent", i18n.GL0417(), i18n.GL0417());
		lblStudentDesc.setText(i18n.GL2199());
		StringUtil.setAttributes(lblStudentDesc.getElement(), "lblStudentDesc", i18n.GL2199(), i18n.GL2199());
		lblTooltipParent.setText(i18n.GL0418());
		StringUtil.setAttributes(lblTooltipParent.getElement(), "lblTooltipParent", i18n.GL0418(), i18n.GL0418());
		lblParentDesc.setText(i18n.GL2200());
		StringUtil.setAttributes(lblParentDesc.getElement(), "lblParentDesc", i18n.GL2200(), i18n.GL2200());
		btnSubmit.setText(i18n.GL0486());
		StringUtil.setAttributes(btnSubmit.getElement(), "btnSubmit", i18n.GL0486(), i18n.GL0486());

		lblHeader.setText(i18n.GL0186());
		StringUtil.setAttributes(lblHeader.getElement(), "lblHeader", i18n.GL0186(), i18n.GL0186());

		lblPickWisely.setText(i18n.GL0410());
		StringUtil.setAttributes(lblPickWisely.getElement(), "lblPickWisely", i18n.GL0410(), i18n.GL0410());

		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));

		panelUsernameTooltip.setVisible(false);
		txtChooseUsername.setFocus(true);
		this.center();
	}
	/**
	 *
	 * @fileName : AlmostDoneUc.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 30-Oct-2014
	 *
	 * @Author tumbalam
	 *
	 * @Reviewer:
	 */
	private class OnMouseOver implements MouseOverHandler {

		@Override
		public void onMouseOver(MouseOverEvent event) {
			if (event.getSource() == txtChooseUsername) {
				panelUsernameTooltip.setVisible(true);
			}
		}
	}
	/**
	 *
	 * @fileName : AlmostDoneUc.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 30-Oct-2014
	 *
	 * @Author tumbalam
	 *
	 * @Reviewer:
	 */
	private class OnMouseOut implements MouseOutHandler {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			panelUsernameTooltip.setVisible(false);
		}

	}

	@Override
	public void onLoad(){
		super.onLoad();
        Scheduler.get().scheduleDeferred(new ScheduledCommand(){
			@Override
			public void execute() {
				new CheckProfanityInOnBlur(txtChooseUsername,null, userNameValidUc, isHavingBadWordsUserName).onBlur(null);
			}
        });
	}

	/**
	 *
	 * @fileName : AlmostDoneUc.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 30-Oct-2014
	 *
	 * @Author tumbalam
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
			if (txtChooseUsername.getText().trim() != null && txtChooseUsername.getText().trim().length()>0){
				btnSubmit.setEnabled(false);
				btnSubmit.getElement().addClassName("disabled");
				boolean fieldValidationStaus=true;
				final Boolean userNameValidate = txtChooseUsername.getText().matches(USER_NAME_REGEX);
				if(!userNameValidate){
					 if(!txtChooseUsername.getText().contains(" ")){
						if (txtChooseUsername.isVisible()){
							userNameValidUc.setText(i18n.GL0475());
						}
					}else if(txtChooseUsername.getText().contains(" ")){
						userNameValidUc.setText(i18n.GL1635());
					}
					userNameValidUc.setVisible(true);
					fieldValidationStaus = false;
				}
				if (txtChooseUsername.getText().length()>0 && txtChooseUsername.getText().length()<4)
				{
					userNameValidUc.setText(i18n.GL0473()+i18n.GL_SPL_FULLSTOP());
					userNameValidUc.getElement().setAttribute("alt",i18n.GL0473()+i18n.GL_SPL_FULLSTOP());
					userNameValidUc.getElement().setAttribute("title",i18n.GL0473()+i18n.GL_SPL_FULLSTOP());

					userNameValidUc.setVisible(true);
					fieldValidationStaus = false;
				}

				if(txtChooseUsername.getText()==null||txtChooseUsername.getText().trim().equals(""))
				{
					userNameValidUc.setText(i18n.GL1284()+i18n.GL_SPL_FULLSTOP());
					userNameValidUc.getElement().setAttribute("alt",i18n.GL1284()+i18n.GL_SPL_FULLSTOP());
					userNameValidUc.getElement().setAttribute("title",i18n.GL1284()+i18n.GL_SPL_FULLSTOP());
					userNameValidUc.setVisible(true);
					fieldValidationStaus=false;
				}


				if (fieldValidationStaus && textBox.getValue() !=null && textBox.getValue().length() > 0){
					Map<String, String> parms = new HashMap<String, String>();
					parms.put("text", textBox.getValue());


					AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

						@Override
						public void onSuccess(Boolean value) {
							if (!value && userNameValidate){
								btnSubmit.setEnabled(true);
								btnSubmit.getElement().removeClassName("disabled");
							}

							SetStyleForProfanity.SetStyleForProfanityForTextBox(textBox, label, value);
							if(textBox!=null){
								label.getElement().getStyle().setWidth(92, Unit.PCT);
							}
						}
					});
				}
			}
		}

	}


	@UiHandler("lblClose")
	public void closePopup(ClickEvent event){
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		hide();
	}

	/**
	 * Added click handler for Textfield to perform basic validations.
	 * @param event instance of {@link KeyUpEvent}
	 */

	@UiHandler("txtChooseUsername")
	public void keyUserNameTextBox(KeyUpEvent event){
		String userName=txtChooseUsername.getText();
		userNameValidUc.setText("");
		userNameValidUc.setVisible(false);
		if(userName.length()>0){
			userNameValidUc.setText("");
		}
		if(userName.length()==20){
			userNameValidUc.setText(i18n.GL1097()+" "+i18n.GL0143());
			userNameValidUc.getElement().setAttribute("alt",i18n.GL1097()+" "+i18n.GL0143());
			userNameValidUc.getElement().setAttribute("title",i18n.GL1097()+" "+i18n.GL0143());
			userNameValidUc.setVisible(true);
		//	fieldValidationStaus=false;
		}else{
			userNameValidUc.setVisible(false);
		}


	}


	/**
	 * Added click handler for checking availability of username and to set userName and role.
	 * @param event instance of {@link ClickEvent}
	 */

	@UiHandler("btnSubmit")
	public void onOkButtonClicked(ClickEvent clickEvent)
	{
		boolean fieldValidationStaus=true;

		if(btnSubmit.isEnabled()){

			if(selectedRole == null || selectedRole.trim().equalsIgnoreCase(""))
			{
				lblSelectRole.setText(i18n.GL1146());
				lblSelectRole.getElement().setAttribute("alt",i18n.GL1146());
				lblSelectRole.getElement().setAttribute("title",i18n.GL1146());
				lblSelectRole.setVisible(true);
				fieldValidationStaus=false;
			}
			if(txtChooseUsername.getText().length()==21){
				fieldValidationStaus=false;

			}
			if(txtChooseUsername.getText().length()>=4 && fieldValidationStaus)
			{
				checkUserAvailability(txtChooseUsername.getText(), "username");
			}
		}

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

		if (result != null && result.isAvailability() && txtChooseUsername.getText() != null && !txtChooseUsername.getText().trim().equalsIgnoreCase(AppClientFactory.getLoggedInUser().getUsername())) {
			userNameValidUc.setText(i18n.GL0061() +" "+ txtChooseUsername.getText() + " "+i18n.GL1286()+i18n.GL_SPL_FULLSTOP());
			userNameValidUc.getElement().setAttribute("alt",i18n.GL0061() +" "+ txtChooseUsername.getText() + " "+i18n.GL1286()+i18n.GL_SPL_FULLSTOP());
			userNameValidUc.getElement().setAttribute("title",i18n.GL0061() +" "+ txtChooseUsername.getText() + " "+i18n.GL1286()+i18n.GL_SPL_FULLSTOP());
			userNameValidUc.setVisible(true);

		}
		else
		{
			String userName = txtChooseUsername.getText();
			String userRole=selectedRole;

			AppClientFactory.getInjector().getUserService().updateV2ProfileDo("", "", "", "", "", "", userName,"", false, userRole, new SimpleAsyncCallback<V2UserDo>() {

				@Override
				public void onSuccess(V2UserDo result) {
					AppClientFactory.setLoggedInUser(result.getUser());
					AppClientFactory.fireEvent(new SetHeaderEvent(result.getUser()));

					hide();

					SignUpGradeCourseView setCourseView = new SignUpGradeCourseView(AppClientFactory.getLoggedInUser());
					setCourseView.show();
					setCourseView.center();
				}
			});
		}

	}

}


