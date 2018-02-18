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
import org.ednovo.gooru.application.client.gin.BasePlacePresenter;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.client.SeoTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.StudentClassPresenter.IsStudentClassProxy;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.StudentClassLearningMapPresenter;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.StudentClassReportPresenter;
import org.ednovo.gooru.client.mvp.home.AlmostDoneUc;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.home.event.HeaderTabType;
import org.ednovo.gooru.client.mvp.home.event.HomeEvent;
import org.ednovo.gooru.client.mvp.search.event.ConfirmStatusPopupEvent;
import org.ednovo.gooru.client.mvp.search.event.SetFooterEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.ErrorPopup;
import org.ednovo.gooru.client.mvp.socialshare.SentEmailSuccessVc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

/**
 *
 * @fileName : StudentClassPresenter.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 19-Jun-2015
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class StudentClassPresenter extends BasePlacePresenter<IsStudentClassView, IsStudentClassProxy> implements StudentClassUiHandlers {

	SignUpPresenter signUpViewPresenter = null;

	StudentClassLearningMapPresenter studentClassLearningMapPresenter = null;

	StudentClassReportPresenter studentClassReportPresenter = null;

	ClasspageDo classpageDo = null;

	@ProxyCodeSplit
	@NameToken(PlaceTokens.STUDENT_VIEW)
	public interface IsStudentClassProxy extends ProxyPlace<StudentClassPresenter> {
	}

	MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public StudentClassPresenter(IsStudentClassView view,IsStudentClassProxy proxy,SignUpPresenter signUpViewPresenter, StudentClassLearningMapPresenter studentClassLearningMapPresenter, StudentClassReportPresenter studentClassReportPresenter) {
		super(view, proxy);
		this.signUpViewPresenter = signUpViewPresenter;
		this.studentClassLearningMapPresenter = studentClassLearningMapPresenter;
		this.studentClassReportPresenter = studentClassReportPresenter;
		getView().setUiHandlers(this);
	}

	@Override
	public void onBind() {
		super.onBind();
		Window.enableScrolling(true);
		Window.scrollTo(0, 0);
	}


	@Override
	protected void onReveal() {
		super.onReveal();
		Window.enableScrolling(true);
		Window.scrollTo(0, 0);
		AppClientFactory.setBrowserWindowTitle(SeoTokens.STUDY_TITLE);
		AppClientFactory.setMetaDataDescription(SeoTokens.HOME_META_DESCRIPTION);
		AppClientFactory.fireEvent(new HomeEvent(HeaderTabType.TEACH));
		classpageDo = null;
		getClasspageDetails();

		//Call Event for Setting Confirm popup
		AppClientFactory.fireEvent(new ConfirmStatusPopupEvent(true));
		AppClientFactory.fireEvent(new SetFooterEvent(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken()));
	}
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		if (AppClientFactory.getPlaceManager().refreshPlace()) {
			initParam();
		}
	}
	
	private void initParam() {
		getView().clearAll();
	}

	@Override
	protected void onReset() {
		super.onReset();
		Window.enableScrolling(true);
		Window.scrollTo(0, 0);
		//Call Event for Setting Confirm popup
		AppClientFactory.fireEvent(new ConfirmStatusPopupEvent(true));
		AppClientFactory.fireEvent(new SetFooterEvent(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken()));

		if (getPlaceManager().getRequestParameter("callback") != null && getPlaceManager().getRequestParameter("callback").equalsIgnoreCase("signup")) {
			//To show SignUp (Registration popup)
			Window.enableScrolling(false);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
			String type = getPlaceManager().getRequestParameter("type") ;
			int displayScreen =getPlaceManager().getRequestParameter("type") !=null  ? Integer.parseInt(type) : 1;
			signUpViewPresenter.displayPopup(displayScreen);
			addToPopupSlot(signUpViewPresenter);
		}
		int flag = AppClientFactory.getLoggedInUser().getViewFlag();
		final String loginType = AppClientFactory.getLoggedInUser().getLoginType() !=null ? AppClientFactory.getLoggedInUser().getLoginType() : "";
		if(!AppClientFactory.isAnonymous() && flag==0 &&  !loginType.equalsIgnoreCase("Credential")) {
			AlmostDoneUc update = new AlmostDoneUc(AppClientFactory.getLoggedInUser().getEmailId(), AppClientFactory.getLoggedInUser());
			update.setGlassEnabled(true);
			update.show();
			update.center();
		}
		if(classpageDo!=null) {
 	       loadNavigationPage();
 	   }
	}

	private void loadNavigationPage() {
		String loadPage = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_TAB, UrlNavigationTokens.STUDENT_CLASSPAGE_LEARNING_MAP_ITEM);
		getView().setButtonHighlight();
		clearSlot(LEARNING_MAP_TAB);

		clearSlot(CLASSPAGE_REPORT_TAB);
		if(loadPage.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LEARNING_MAP_ITEM)) {
			addToSlot(LEARNING_MAP_TAB, studentClassLearningMapPresenter);
			studentClassLearningMapPresenter.setData();
		} else if(loadPage.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_REPORT_ITEM)) {
			addToSlot(CLASSPAGE_REPORT_TAB, studentClassReportPresenter);
			studentClassReportPresenter.setData();
		}
		
		String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW)) {
			getView().disableSwitchBtn(false);
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
			getView().disableSwitchBtn(false);
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
			getView().disableSwitchBtn(true);
		}
	}

	@Override
	protected void onHide() {
		AppClientFactory.getPlaceManager().setClasspageEventId(null);
	}

	@Override
	public String getViewToken() {
		return PlaceTokens.STUDENT_VIEW;
	}

	@Override
	public void joinStudentClass() {

		getView().setJoinClassData();
		getView().initiateJoinClassPopup();

	}

	@Override
	public void getClasspageDetails() {
		getView().setEmptyContainerVisiblity(false);
		String id = AppClientFactory.getPlaceManager().getRequestParameter("id");
		AppClientFactory.getInjector().getClasspageService().v3GetClassById(id, new AsyncCallback<ClasspageDo>() {
			@Override
			public void onSuccess(ClasspageDo result) {
				classpageDo = result;
				getView().setCourseData(classpageDo);
				setCheckClassVisiblity(classpageDo);
				studentClassLearningMapPresenter.setClasspageDo(classpageDo);
				studentClassReportPresenter.setClasspageDo(classpageDo);
				if(result.getCourseGooruOid()==null) {
					getView().setProgressBarVisibility(false);
				} else {
					getView().setProgressBarVisibility(true);
				}
				loadNavigationPage();
			}
			
			@Override
			public void onFailure(Throwable caught) {

			}
		});
	}
	
	public void setCheckClassVisiblity(ClasspageDo classpageDo){
		String status = classpageDo.getStatus();
		getView().setPrivateLogoutPanelVisibility(true);
		String page = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_PREVIEW_MODE, UrlNavigationTokens.FALSE);
		if(page.equalsIgnoreCase(UrlNavigationTokens.FALSE)) {
			if(!classpageDo.isVisibility()){
				if(AppClientFactory.isAnonymous()){
					getView().setPrivateLogoutPanelVisibility(false);
					classpageDo = null;
					LoginPopupUc loginPopupUc=new LoginPopupUc() {
						@Override
						public void onLoginSuccess() {
							
						}
					};
				}else{
					if(!classpageDo.getUser().getGooruUId().equalsIgnoreCase(AppClientFactory.getGooruUid())){
						if(status.equalsIgnoreCase("not-invited")){
							getView().setPrivateLogoutPanelVisibility(false);
							new SentEmailSuccessVc(i18n.GL1177(), i18n.GL1535_1());
						}else if(status.equalsIgnoreCase("pending")){
							getView().setPrivateLogoutPanelVisibility(false);
							studentClassLearningMapPresenter.showProgressMapBar(false);
							joinStudentClass();
						} else {
							studentClassLearningMapPresenter.showProgressMapBar(true);
							getView().setPreviewClassMode(false);
						}
					}else{
						studentClassLearningMapPresenter.showProgressMapBar(false);
						getView().setPreviewClassMode(true);
					}
				}
				
			}else{
				if(!classpageDo.getUser().getGooruUId().equalsIgnoreCase(AppClientFactory.getGooruUid())){
					if(status.equalsIgnoreCase("not-invited") || status.equalsIgnoreCase("pending")){
						studentClassLearningMapPresenter.showProgressMapBar(false);
						joinStudentClass();
					} else {
						studentClassLearningMapPresenter.showProgressMapBar(true);
						getView().setPreviewClassMode(false);
					}
				}else{
					studentClassLearningMapPresenter.showProgressMapBar(false);
					getView().setPreviewClassMode(true);
				}
			}
		} else {
			studentClassLearningMapPresenter.showProgressMapBar(false);
			getView().setPreviewClassMode(true);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.studentclassview.StudentClassUiHandlers#studentJoinClassPoup(java.lang.String)
	 */
	@Override
	public void studentJoinClassPoup(String classUid) {
		
		AppClientFactory.getInjector().getClasspageService().v3StudentJoinIntoClass(classUid,new SimpleAsyncCallback<Boolean>(){

			@Override
			public void onSuccess(Boolean isJoined) {
				if(isJoined) {
					getView().setSuccesspopup();
					getView().setPrivateLogoutPanelVisibility(true);
					setClassPageDo();
				} else {
					getView().closeJoinPopup(false);
					new ErrorPopup(i18n.GL3464());
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.studentclassview.StudentClassUiHandlers#setClassPageDo()
	 */
	@Override
	public void setClassPageDo() {
		classpageDo.setStatus("active");
		PlaceRequest request = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
		AppClientFactory.getPlaceManager().revealPlace(request);
	}
	
}

