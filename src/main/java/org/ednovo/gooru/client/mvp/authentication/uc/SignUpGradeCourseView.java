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

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.LibraryCodeDo;
import org.ednovo.gooru.application.shared.model.code.ProfileCodeDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.authentication.SignUpCBundle;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
/**
 *
 * @fileName : SignUpGradeCourseView.java
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
public class SignUpGradeCourseView extends PopupPanel{

	@UiField HTMLPanel signupBgPanel, metaDataSelectionPanel, courseContainer,congratsLbl,accountCreatedText;

	@UiField Label lblTitle, lblCancel, lblErrorMessage,menuIcon;

	@UiField FlowPanel registerGradeList;

	@UiField Anchor mathCourseLbl, scienceCourseLbl, elaCourseLbl, socialCourseLbl;

	@UiField Button skipBtn, submitBtn;
	@UiField UlPanel subject;


	HTMLPanel mathCourseContainer, scienceCourseContainer, elaCourseContainer, socialCourseContainer;

	private int selectedCourseCount = 0;
	private boolean isSubjectMenuOpened=false;

	private List<LibraryCodeDo> libraryDo = null;

	private static final String SCIENCE_LBL = "Science";

	private static final String MATH_LBL = "Math";

	private static final String SOCIAL_LBL = "Social Sciences";

	private static final String ELA_LBL = "Language Arts";

	private static final String REGISTER_USER_LEVEL = "settings";

	@UiField Image imgLoading;

	List<ProfileCodeDo> selectedCoureses=new ArrayList<ProfileCodeDo>();

	UserDo userDo= null;

	private static SignUpGradeCourseViewUiBinder uiBinder = GWT
			.create(SignUpGradeCourseViewUiBinder.class);

	interface SignUpGradeCourseViewUiBinder extends
			UiBinder<Widget, SignUpGradeCourseView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public SignUpGradeCourseView(UserDo userDo) {
		this.userDo = userDo;
		SignUpCBundle.INSTANCE.css().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));
		signupBgPanel.getElement().setId("pnlSignUpBgPanel");

		metaDataSelectionPanel.getElement().getStyle().setPadding(15, Unit.PX);
		metaDataSelectionPanel.getElement().setId("pnlMetaDataSelectionPanel");

		lblTitle.setText(i18n.GL0186() + i18n.GL_SPL_EXCLAMATION());
		lblTitle.getElement().setId("lblTitle");
		lblTitle.getElement().setAttribute("alt",i18n.GL0186());
		lblTitle.getElement().setAttribute("title",i18n.GL0186());

		lblCancel.getElement().setId("lblCancel");
		lblCancel.getElement().setAttribute("alt","");
		lblCancel.getElement().setAttribute("title","");

		scienceCourseContainer = new HTMLPanel("");
		mathCourseContainer = new HTMLPanel("");
		socialCourseContainer = new HTMLPanel("");
		elaCourseContainer = new HTMLPanel("");

		imgLoading.setVisible(true);
		imgLoading.setUrl("images/core/B-Dot.gif");

		setRegisterGradeList();
		setRegisterCourseList();

		lblErrorMessage.setVisible(false);
		lblErrorMessage.setText(i18n.GL0500());
		lblErrorMessage.getElement().setId("errlblCourse");
		lblErrorMessage.getElement().setAttribute("alt",i18n.GL0500());
		lblErrorMessage.getElement().setAttribute("title",i18n.GL0500());
		lblErrorMessage.getElement().getStyle().clearMarginLeft();
		lblErrorMessage.getElement().getStyle().clearWidth();
		lblErrorMessage.getElement().getStyle().setFloat(Float.RIGHT);
		lblErrorMessage.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
		lblErrorMessage.getElement().getStyle().setWidth(205, Unit.PX);
		Window.enableScrolling(false);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
        this.setGlassEnabled(true);
        this.getElement().getStyle().setZIndex(99999);
        this.show();
        this.center();
        congratsLbl.getElement().setInnerHTML(i18n.GL1159()+i18n.GL_SPL_EXCLAMATION());
        congratsLbl.getElement().setId("pnlCongrats");
        congratsLbl.getElement().setAttribute("alt",i18n.GL1159());
        congratsLbl.getElement().setAttribute("title",i18n.GL1159());

        accountCreatedText.getElement().setInnerHTML(i18n.GL1160());
        accountCreatedText.getElement().setId("pnlAccountCreatedText");
        accountCreatedText.getElement().setAttribute("alt",i18n.GL1160());
        accountCreatedText.getElement().setAttribute("title",i18n.GL1160());

        imgLoading.setAltText(i18n.GL0110());
        imgLoading.setTitle(i18n.GL0110());
        imgLoading.getElement().setId("imgLoadingImage");
        imgLoading.getElement().setAttribute("alt",i18n.GL0110());

        registerGradeList.getElement().setId("fpnlGradesList");
        scienceCourseLbl.getElement().setId("epnlSienceCourse");
        mathCourseLbl.getElement().setId("epnlMathCourse");
        socialCourseLbl.getElement().setId("epnlSocialCourse");
        elaCourseLbl.getElement().setId("epnlElaCourse");
        courseContainer.getElement().setId("pnlCourseContainer");

        skipBtn.setText(i18n.GL1004());
        skipBtn.getElement().setId("btnSkip");
        skipBtn.getElement().setAttribute("alt",i18n.GL1004());
        skipBtn.getElement().setAttribute("title",i18n.GL1004());

        submitBtn.setText(i18n.GL0486());
        submitBtn.getElement().setId("btnSubmit");
        submitBtn.getElement().setAttribute("alt",i18n.GL1004());
        submitBtn.getElement().setAttribute("title",i18n.GL1004());
	}
	/**
	 *
	 * @function senEmail
	 *
	 * @created_date : Jan 15, 2014
	 *
	 * @description
	 *
	 *
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void senEmail(){
        	AppClientFactory.getInjector().getUserService().sendWelcomeMail(userDo.getGooruUId(), ClientConstants.WELCOME, new SimpleAsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {

				}
			});
	}

	/**
	 *
	 * @function closeSignUpGradeCourseView
	 *
	 * @created_date : Nov 8, 2013
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
	public void closeSignUpGradeCourseView() {
			AppClientFactory.fireEvent(new SetHeaderEvent(userDo));
			final String loginType = AppClientFactory.getLoggedInUser().getLoginType() !=null ? AppClientFactory.getLoggedInUser().getLoginType() : "";
			if(!AppClientFactory.isAnonymous() &&  loginType.equalsIgnoreCase("Credential")) {
				senEmail();
			}
			OpenThanksPopup();
	}
	/**
	 *
	 * @function OpenThanksPopup
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
	private void OpenThanksPopup(){
		this.hide();

		AppClientFactory.getInjector().getUserService().updateUserViewFlag(AppClientFactory.getLoggedInUser().getGooruUId(), 12, new SimpleAsyncCallback<UserDo>() {
			@Override
			public void onSuccess(UserDo newUser) {
				UserDo user = AppClientFactory.getLoggedInUser();
				user.setViewFlag(newUser.getViewFlag());
				AppClientFactory.setLoggedInUser(user);
			}
		});

		ThanksPopupUc thanks = new ThanksPopupUc();
		if (AppClientFactory.getLoggedInUser().getAccountTypeId() == null || AppClientFactory.getLoggedInUser().getAccountTypeId() == 2){
			thanks.setAccountType("normal");
		}
		thanks.center();
		thanks.show();
	}

	/**
	 *
	 * @function setRegisterGradeList
	 *
	 * @created_date : Nov 8, 2013
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
	private void setRegisterGradeList() {
		UlPanel ulPanel=new UlPanel();
		ulPanel.addStyleName("Grades");
		LiPanel liPanel1=new LiPanel();
		liPanel1.add(new SignupGradeLabel("Kindergarten", new ProfileDo()));
		ulPanel.add(liPanel1);
		for (int i = 1; i <= 12; i++) {
			LiPanel liPanel=new LiPanel();
			liPanel.add(new SignupGradeLabel(i + "", new ProfileDo()));
			ulPanel.add(liPanel);
		}
		LiPanel liPanel3=new LiPanel();
		liPanel3.add(new SignupGradeLabel("Higher Education",new ProfileDo()));
		ulPanel.add(liPanel3);
		registerGradeList.add(ulPanel);
	}

	/**
	 *
	 * @function setRegisterCourseList
	 *
	 * @created_date : Nov 8, 2013
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
	private void setRegisterCourseList() {
		mathCourseLbl.setHTML("<span>"+MATH_LBL+"</span>");
		scienceCourseLbl.setHTML("<span>"+SCIENCE_LBL+"</span>");
		elaCourseLbl.setHTML("<span>"+ELA_LBL+"</span>");
		socialCourseLbl.setHTML("<span>"+SOCIAL_LBL+"</span>");

		imgLoading.setVisible(true);
		AppClientFactory.getInjector().getTaxonomyService().getCourse(new SimpleAsyncCallback<List<LibraryCodeDo>>() {
			@Override
			public void onSuccess(List<LibraryCodeDo> result) {
				libraryDo = result;
				if (libraryDo != null) {
					setCourseData(libraryDo.get(0),libraryDo.get(0).getLabel());
				}
			}
		});
	}

	/**
	 *
	 * @function setCourseData
	 *
	 * @created_date : Nov 8, 2013
	 *
	 * @description	based on api response data set the label and respect images
	 *
	 *
	 * @parm(s) : @param libraryCodeDo
	 * @parm(s) : @param courseLabel
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void setCourseData(LibraryCodeDo libraryCodeDo, String courseLabel) {
		selectedCoureses.clear();
		List<LibraryCodeDo> liCodeDo = libraryCodeDo.getNode();
		for(int i = 0; i < liCodeDo.size(); i++) {
			final int codeID=liCodeDo.get(i).getCodeId();
			SignupCourseLabel signupCourseLabel = new SignupCourseLabel(liCodeDo.get(i).getCodeId(), liCodeDo.get(i).getLabel().trim(), new ProfileDo(), "images/course/"+courseLabel.trim().toLowerCase().replaceAll(" ", "_")+"/"+liCodeDo.get(i).getLabel().trim().replaceAll(" ", "_")+".png", courseLabel) {
					@Override
					public int selectCourseLabel(boolean isSelected) {
							if(isSelected == true) {
								if(selectedCourseCount<5) {
									selectedCourseCount++;
									ProfileCodeDo profileCodeDo = new ProfileCodeDo();
									CodeDo codeDo = new CodeDo();
									codeDo.setCodeId(codeID);
									profileCodeDo.setCode(codeDo);
									selectedCoureses.add(profileCodeDo);
								}
							} else {
								selectedCourseCount--;
							}
						return selectedCourseCount;
					}

					@Override
					public void showErrorMessage(boolean value) {
						lblErrorMessage.setVisible(value);
					}

					@Override
					public int getCourseCount() {
						return selectedCourseCount;
					}
			};

			if(courseLabel.equalsIgnoreCase(MATH_LBL)) {
				mathCourseContainer.add(signupCourseLabel);
			} else if(courseLabel.equalsIgnoreCase(SCIENCE_LBL)) {
				scienceCourseContainer.add(signupCourseLabel);
			} else if(courseLabel.equalsIgnoreCase(ELA_LBL)) {
				elaCourseContainer.add(signupCourseLabel);
			} else if(courseLabel.equalsIgnoreCase(SOCIAL_LBL)) {
				socialCourseContainer.add(signupCourseLabel);
			}
		}
		imgLoading.setVisible(false);
		if(courseLabel.equalsIgnoreCase(MATH_LBL)) {
			courseContainer.add(mathCourseContainer);
		} else if(courseLabel.equalsIgnoreCase(SCIENCE_LBL)) {
			courseContainer.add(scienceCourseContainer);
		} else if(courseLabel.equalsIgnoreCase(ELA_LBL)) {
			courseContainer.add(elaCourseContainer);
		} else if(courseLabel.equalsIgnoreCase(SOCIAL_LBL)) {
			courseContainer.add(socialCourseContainer);
		}

	}
	/* Ui Hanlders*/
	/**
	 *
	 * @function clickScienceLbl
	 *
	 * @created_date : Nov 8, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param event
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	@UiHandler("scienceCourseLbl")
	public void clickScienceLbl(ClickEvent event) {
		removeStyleNames();
		scienceCourseLbl.addStyleName("sceinceActive");
		if(!(scienceCourseContainer.getWidgetCount()>0)) {
			setCourseData(libraryDo.get(0), libraryDo.get(0).getLabel());
		}
		setCourseContainerVisibility(SCIENCE_LBL);
	}
	/**
	 *
	 * @function clickMathLbl
	 *
	 * @created_date : Nov 8, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param event
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	@UiHandler("mathCourseLbl")
	public void clickMathLbl(ClickEvent event) {
		removeStyleNames();
		mathCourseLbl.addStyleName("mathActive");
		if(!(mathCourseContainer.getWidgetCount()>0)) {
			setCourseData(libraryDo.get(1), MATH_LBL);
		}
		setCourseContainerVisibility(MATH_LBL);
	}
	 /**
	  *
	  * @function clickSocialLbl
	  *
	  * @created_date : Nov 8, 2013
	  *
	  * @description
	  *
	  *
	  * @parm(s) : @param event
	  *
	  * @return : void
	  *
	  * @throws : <Mentioned if any exceptions>
	  *
	  *
	  *
	  *
	  */
	@UiHandler("socialCourseLbl")
	public void clickSocialLbl(ClickEvent event) {
		removeStyleNames();
		socialCourseLbl.addStyleName("ssActive");
		if(!(socialCourseContainer.getWidgetCount()>0)) {
			setCourseData(libraryDo.get(2), SOCIAL_LBL);
		}
		setCourseContainerVisibility(SOCIAL_LBL);
	}
	/**
	 *
	 * @function clickElaLbl
	 *
	 * @created_date : Nov 8, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param event
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	@UiHandler("elaCourseLbl")
	public void clickElaLbl(ClickEvent event) {
		removeStyleNames();
		elaCourseLbl.addStyleName("elaActive");
		if(!(elaCourseContainer.getWidgetCount()>0)) {
			setCourseData(libraryDo.get(3), ELA_LBL);
		}
		setCourseContainerVisibility(ELA_LBL);
	}


	/**
	 * Hide the popup and redirect to home page while clicking cancel
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("lblCancel")
	public void onCancelClick(ClickEvent clickEvent) {
		closeSignUpGradeCourseView();

	}
	/**
	 *
	 * @function onSkipClick
	 *
	 * @created_date : Nov 8, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param clickEvent
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	@UiHandler("skipBtn")
	public void onSkipClick(ClickEvent clickEvent) {
		if(selectedCoureses.size()>0){
			for(int i=0;i<selectedCoureses.size();i++){
				AppClientFactory.getInjector().getProfilePageService().deleteCourseUserProfile(selectedCoureses.get(i).getCode(), REGISTER_USER_LEVEL, new SimpleAsyncCallback<Void>(){
					@Override
					public void onSuccess(Void result) {
					}
				});
			}
		}
		MixpanelUtil.skip_grade_course();
		closeSignUpGradeCourseView();
	}
	/**
	 *
	 * @function onSubmitClick
	 *
	 * @created_date : Nov 8, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param clickEvent
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	@UiHandler("submitBtn")
	public void onSubmitClick(ClickEvent clickEvent) {
		MixpanelUtil.submit_grade_course();
		closeSignUpGradeCourseView();
	}


	/**
	 *
	 * @function removeStyleNames
	 *
	 * @created_date : Nov 8, 2013
	 *
	 * @description	to the set default css for all items
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
	private void removeStyleNames() {
		mathCourseLbl.removeStyleName("mathActive");
		scienceCourseLbl.removeStyleName("sceinceActive");
		elaCourseLbl.removeStyleName("elaActive");
		socialCourseLbl.removeStyleName("ssActive");
	}
	/**
	 *
	 * @function setCourseContainerVisibility
	 *
	 * @created_date : Nov 8, 2013
	 *
	 * @description	set the visibility for all language containers and make visible based on click/selection.
	 *
	 *
	 * @parm(s) : @param courseLabel
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void setCourseContainerVisibility(String courseLabel) {
		mathCourseContainer.setVisible(courseLabel.equalsIgnoreCase(MATH_LBL) ? true : false);
		scienceCourseContainer.setVisible(courseLabel.equalsIgnoreCase(SCIENCE_LBL) ? true : false);
		elaCourseContainer.setVisible(courseLabel.equalsIgnoreCase(ELA_LBL) ? true : false);
		socialCourseContainer.setVisible(courseLabel.equalsIgnoreCase(SOCIAL_LBL) ? true : false);
	}
	@UiHandler("menuIcon")
	public void menuIconClickEvent(ClickEvent event){

		if(isSubjectMenuOpened){
			menuIcon.removeStyleName("active");
			isSubjectMenuOpened=false;
			subject.setVisible(false);
		}else{
			menuIcon.addStyleName("active");
			subject.getElement().getStyle().setDisplay(Display.BLOCK);
			isSubjectMenuOpened=true;
		}

	}
}
