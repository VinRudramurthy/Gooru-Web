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
package org.ednovo.gooru.client.mvp.classpage.studentclassview;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpages.event.OpenJoinClassPopupEvent;
import org.ednovo.gooru.client.mvp.classpages.event.OpenJoinClassPopupHandler;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentView;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.SuccessPopupViewVc;
import org.ednovo.gooru.client.uc.EmPanel;
import org.ednovo.gooru.client.uc.H2Panel;
import org.ednovo.gooru.client.uc.LoadingUc;
import org.ednovo.gooru.client.uc.SpanPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
/**
 * 
 * @fileName : StudentClassView.java
 *
 * @description : 
 *
 * @version : 1.0
 *
 * @date: 19-Jun-2015
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class StudentClassView extends BaseViewWithHandlers<StudentClassUiHandlers> implements IsStudentClassView ,ClickHandler{
	
	@UiField SpanPanel classCodeSpan, studentMessage, onoffSwitchBtn;
	@UiField EmPanel teacherOwnership;
	@UiField H2Panel courseName;
	@UiField SimplePanel learningMapContainer;
	@UiField Image classImage, teacherImage, studentImage;
	@UiField SimpleCheckBox switchCheckBox;
	@UiField HTMLPanel editClassMetadataPanel, previewClassMetadataPanel, planProgressBar, planProgressPanel;
	@UiField
	static HTMLPanel classpageMainContainer;
	@UiField Button joinClassBtn, teachViewBtn;
	@UiField Label studentViewLbl;
	@UiField LoadingUc cropImageLoading;
	
	ClasspageDo classpageDo;
	
	StudentJoinClassPopup joinClassPopup;
	
	private static final String DEFAULT_CLASSPAGE_IMAGE = "../images/Classpage/default-classpage.png";
	
	private static final String DEFAULT_USER_IMAGE = "../images/settings/setting-user-image.png";
	
	private static final String CLASS_OWNERSHIP_NAME = "'s Class";
	
	private static final String CLASS_READER_NAME = "Hello, ";
	
	private static StudentClassViewUiBinder uiBinder = GWT.create(StudentClassViewUiBinder.class);

	interface StudentClassViewUiBinder extends UiBinder<Widget, StudentClassView> {    
		
	}
	
	public static final MessageProperties i18n = GWT.create(MessageProperties.class); 
	
	@Inject
	public StudentClassView() {
		setWidget(uiBinder.createAndBindUi(this));
		switchCheckBox.addClickHandler(new ClasspageTabNavigator());
		setDebugIds();
	}

	@Override
	public void onClick(ClickEvent event) {
		
	}

	@Override
	public void clearAll() {
		
	}
	
	private void setDebugIds() {
		cropImageLoading.setLoadingText(i18n.GL1234());
		cropImageLoading.getElement().setId("loadingUcCropImageLoadingInClasspage");
		setEmptyContainerVisiblity(false);
	}
	
	@Override
	public void setEmptyContainerVisiblity(boolean isVisible) {
		cropImageLoading.setVisible(!isVisible);
		classpageMainContainer.setVisible(isVisible);
	}
	
	@Override
	public void setPrivateLogoutPanelVisibility(boolean isVisible) {
		classpageMainContainer.setVisible(isVisible);
	}
	
	@Override
	public void setCourseData(ClasspageDo classpageDo) {
		this.classpageDo=classpageDo;
		switchCheckBox.getElement().setId("myonoffswitch");
		switchCheckBox.getElement().setAttribute("name", "onoffswitch");
		
		String thumbnail = DEFAULT_CLASSPAGE_IMAGE;
		if(classpageDo!=null&&classpageDo.getThumbnails()!=null&&classpageDo.getThumbnails().getUrl()!=null) {
			thumbnail = classpageDo.getThumbnails().getUrl();
		}
		classImage.setUrl(thumbnail);
		classImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				classImage.setUrl(DEFAULT_CLASSPAGE_IMAGE);
			}
		});
		classImage.setWidth("1000px");
		classImage.setHeight("165px");
		classCodeSpan.setText(classpageDo.getClassCode());
		courseName.setText(classpageDo.getName());
		teacherOwnership.setText(classpageDo.getUser().getUsername()+CLASS_OWNERSHIP_NAME);
		teacherImage.setUrl(classpageDo.getUser().getProfileImageUrl());
		teacherImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				teacherImage.setUrl(DEFAULT_USER_IMAGE);
			}
		});
		studentMessage.setText(CLASS_READER_NAME+AppClientFactory.getLoggedInUser().getUsernameDisplay()+"!");
		studentImage.setUrl(AppClientFactory.getLoggedInUser().getProfileImageUrl());
		studentImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				studentImage.setUrl(DEFAULT_USER_IMAGE);
			}
		});
		
		
		OpenJoinClassPopupHandler openJoinClassPopupHandler=new OpenJoinClassPopupHandler() {
					
					@Override
					public void openJoinClassPopup() {
						getUiHandlers().getClasspageDetails();
					}
		};
		AppClientFactory.getEventBus().addHandler(OpenJoinClassPopupEvent.TYPE,openJoinClassPopupHandler);
		setEmptyContainerVisiblity(true);
	}
	
	@Override
	public void addToSlot(Object slot, Widget content) {
		if (content != null) {
			learningMapContainer.clear();
			learningMapContainer.setWidget(content);
		}
	}

	public class ClasspageTabNavigator implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			PlaceRequest request = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
			if(switchCheckBox.isChecked()) {
				request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_TAB, UrlNavigationTokens.STUDENT_CLASSPAGE_REPORT_ITEM);
			} else {
				request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_TAB, UrlNavigationTokens.STUDENT_CLASSPAGE_LEARNING_MAP_ITEM);
			}
			AppClientFactory.getPlaceManager().revealPlace(request);
		}
	}
	
	@Override
	public void setButtonHighlight() {
		String page = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_TAB, UrlNavigationTokens.STUDENT_CLASSPAGE_LEARNING_MAP_ITEM);
		if(page.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LEARNING_MAP_ITEM)) {
			switchCheckBox.setChecked(false);
		} else {
			switchCheckBox.setChecked(true);
		}
	}

	@Override
	public void setPreviewClassMode(boolean isPreview) {
		editClassMetadataPanel.setVisible(!isPreview);
		previewClassMetadataPanel.setVisible(isPreview);
		joinClassBtn.setVisible(false);
	}

	@Override
	public void setJoinClassData() {
		setPreviewClassMode(true);
		joinClassBtn.setVisible(true);
		teachViewBtn.setVisible(false);
		studentViewLbl.setVisible(false);
	}
	
	@UiHandler("joinClassBtn")
	public void clickJoinClassBtn(ClickEvent event) {
		initiateJoinClassPopup();
	}
	
	@UiHandler("teachViewBtn")
	public void clickTeachViewBtn(ClickEvent event) {
		String classpageId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_CLASS_ID);
		String cId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, "");
		PlaceRequest request = new PlaceRequest(PlaceTokens.EDIT_CLASS);
		request = request.with(UrlNavigationTokens.CLASSPAGEID, classpageId);
		request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.TEACHER_CLASS_DASHBOARD);
		request = request.with(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW, UrlNavigationTokens.TEACHER_CLASS_CONTENT_SUB_REPORTS);
		request = request.with(UrlNavigationTokens.TEACHER_CLASSPAGE_REPORT_TYPE, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, cId);
		AppClientFactory.getPlaceManager().revealPlace(request);
	}
	
	public static void setPublicPagePending() {
		if(!AppClientFactory.isAnonymous()){
			
		}
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.studentclassview.IsStudentClassView#initiateJoinClassPopup()
	 */
	@Override
	public void initiateJoinClassPopup() {
		if(AppClientFactory.isAnonymous()){
			LoginPopupUc loginPopupUc=new LoginPopupUc() {
				@Override
				public void onLoginSuccess() {
					// TODO Auto-generated method stub
				}
			};
		}else{
			
			joinClassPopup = new StudentJoinClassPopup(classpageDo) {
				
				@Override
				void joinIntoClass() {
					getUiHandlers().studentJoinClassPoup(classpageDo.getClassUid());
				}
				
				@Override
				public void closePoup() {
					hide();
					Window.enableScrolling(true);
					if(!classpageDo.isVisibility()){
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
					}
				}
			};
			int windowHeight=Window.getClientHeight()/2; //I subtract 10 from the client height so the window isn't maximized.
			int windowWidth=Window.getClientWidth()/2;
			joinClassPopup.setPopupPosition(windowWidth-253, windowHeight-70);
			joinClassPopup.setPixelSize(506, 261);		
			//joinPopup.center();
			joinClassPopup.show();
		}
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.studentclassview.IsStudentClassView#setSuccesspopup()
	 */
	@Override
	public void setSuccesspopup() {
		//joinClassPopup.setVisible(false);
		joinClassPopup.hide();
		SuccessPopupViewVc success=new SuccessPopupViewVc(){
			@Override
			public void onClickPositiveButton(
					ClickEvent event) {
				if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.SEARCH_COLLECTION) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.SEARCH_RESOURCE)){
					Window.enableScrolling(false);
				}else{
					Window.enableScrolling(true);
				}
				this.hide();
				setPreviewClassMode(false);
			}
		};
		success.setWidth("450px");
        success.setPopupTitle(i18n.GL1553());
        success.setDescText(i18n.GL1554()+" "+classpageDo.getName()+StudentAssignmentView.i18n.GL_SPL_EXCLAMATION()+'\n'+StudentAssignmentView.i18n.GL1552());
        success.setPositiveButtonText(i18n.GL0190());
        success.center();
        success.show();
	}

	@Override
	public void setProgressBarVisibility(boolean isVisible) {
		planProgressBar.setVisible(isVisible);
	}

	@Override
	public void disableSwitchBtn(boolean isDisable) {
		planProgressPanel.setVisible(!isDisable);
	}

	@Override
	public void closeJoinPopup(boolean isVisible) {
		//joinClassPopup.setVisible(isVisible);
		joinClassPopup.hide();
	}
	
	public static boolean getMainContainerStatus()
	{
		Boolean mainContainerStatus = false;
		try
		{
			mainContainerStatus = classpageMainContainer.isVisible();
		}
		catch(Exception ex)
		{
			AppClientFactory.printSevereLogger("StudentAssignmentView : getMainContainerStatus : "+ex.getMessage());
		}
		return mainContainerStatus;
	}
}