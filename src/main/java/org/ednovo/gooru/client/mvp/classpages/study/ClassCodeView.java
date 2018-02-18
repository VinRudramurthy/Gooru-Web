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
package org.ednovo.gooru.client.mvp.classpages.study;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpages.newclasspage.NewClassPopupView;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.search.event.SetButtonEvent;
import org.ednovo.gooru.client.mvp.search.event.SetButtonHandler;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.socialshare.SentEmailSuccessVc;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.uc.H1Panel;
import org.ednovo.gooru.client.uc.H2Panel;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.H4Panel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
/**
 * 
 * @fileName : ClassCodeView.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 07-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class ClassCodeView extends BaseViewWithHandlers<ClassCodeUiHandlers> implements IsClassCodeView {
	
	private static ClassCodeViewUiBinder uiBinder = GWT
			.create(ClassCodeViewUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField TextBoxWithPlaceholder txtCode;

	@UiField Button btnCreateClass, btnEnter, disabledBtn;
	
	@UiField H1Panel txtLearnH1;
	
	@UiField H2Panel txtTeacherH2;
	
	@UiField H3Panel txtExploreH3, txtLearnH3, txtFeedbackH3, txtScoreH3;
	
	@UiField H4Panel txtClassCodeH4;
	
	@UiField PPanel txtStudyP, txtQuizP, txtTeachP;
	
	@UiField Anchor signupAcr;
	
	AlertMessageUc alertMessageUc;
	
	//private NewClasspagePopupView newPopup = null;
	
	private NewClassPopupView newPopup = null;
	
	private boolean isValid=true;
	
	interface ClassCodeViewUiBinder extends UiBinder<Widget, ClassCodeView> {

	}
	
	@Inject
	public ClassCodeView() {
		setWidget(uiBinder.createAndBindUi(this));
		setText();
		SetButtonHandler setButtonVisibility = new SetButtonHandler() {
			@Override
			public void setButtonVisibility() {
				setCreateClassVisibility();
			}
		};
		AppClientFactory.getEventBus().addHandler(SetButtonEvent.TYPE, setButtonVisibility);
		Window.enableScrolling(true);
	}
	/**
	 * 
	 * @function setCreateClassVisibility 
	 * 
	 * @created_date : 07-Dec-2014
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
	private void setCreateClassVisibility() {
		btnCreateClass.setVisible(true);
	}
	/**
	 * @function setText 
	 * 
	 * @created_date : May 21, 2014
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
	
	private void setText() {
		
		txtLearnH1.setText(i18n.GL3461_1());
		StringUtil.setAttributes(txtLearnH1.getElement(), "txtLearnH1", i18n.GL3461_1(), i18n.GL3461_1());
		
		txtExploreH3.setText(i18n.GL3461_2());
		StringUtil.setAttributes(txtExploreH3.getElement(), "txtExploreH3", i18n.GL3461_2(), i18n.GL3461_2());
		
		txtClassCodeH4.setText(i18n.GL3461_3());
		StringUtil.setAttributes(txtClassCodeH4.getElement(), "txtClassCodeH4", i18n.GL3461_3(), i18n.GL3461_3());
		
		txtLearnH3.setText(i18n.GL3461_4());
		StringUtil.setAttributes(txtLearnH3.getElement(), "txtLearnH3", i18n.GL3461_4(), i18n.GL3461_4());
		
		txtStudyP.setText(i18n.GL3461_5());
		StringUtil.setAttributes(txtStudyP.getElement(), "txtStudyP", i18n.GL3461_5(), i18n.GL3461_5());
		
		txtFeedbackH3.setText(i18n.GL3461_6());
		StringUtil.setAttributes(txtFeedbackH3.getElement(), "txtFeedbackH3", i18n.GL3461_6(), i18n.GL3461_6());
		
		txtQuizP.setText(i18n.GL3461_7());
		StringUtil.setAttributes(txtQuizP.getElement(), "txtQuizP", i18n.GL3461_7(), i18n.GL3461_7());
		
		txtTeacherH2.setText(i18n.GL3461_8());
		StringUtil.setAttributes(txtTeacherH2.getElement(), "txtTeacherH2", i18n.GL3461_8(), i18n.GL3461_8());

		txtScoreH3.setText(i18n.GL3461_9());
		StringUtil.setAttributes(txtScoreH3.getElement(), "txtScoreH3", i18n.GL3461_9(), i18n.GL3461_9());
		
		txtTeachP.setText(i18n.GL3461_10());
		StringUtil.setAttributes(txtTeachP.getElement(), "txtTeachP", i18n.GL3461_10(), i18n.GL3461_10());
		
		setCreateClassVisibility();
		btnCreateClass.setText(i18n.GL1771());
		StringUtil.setAttributes(btnCreateClass.getElement(), "btnCreateClass", i18n.GL1771(), i18n.GL1771());
		
		btnEnter.setText(i18n.GL0213());
		StringUtil.setAttributes(btnEnter.getElement(), "btnEnter", i18n.GL0213(), i18n.GL0213());
		
		disabledBtn.setText(i18n.GL0213());
		StringUtil.setAttributes(disabledBtn.getElement(), "btnDisable", i18n.GL0213(), i18n.GL0213());
		disabledBtn.setVisible(false);
		
		txtCode.setPlaceholder(i18n.GL1785());
		txtCode.getElement().setId("txtCode");
		txtCode.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				txtCode.getElement().addClassName("textTransform");
			}
		});
		txtCode.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				if (txtCode.getText().length() > 0 ){
					
				}else{
					txtCode.getElement().removeClassName("textTransform");
				}
			}
		});
		btnEnter.addClickHandler(new OnEnterClassCodeClick());
		btnCreateClass.addClickHandler(new OnClickCreateClass());
		Window.enableScrolling(true);
	}
	
	/**
	 * @function OpenClasspageEdit 
	 * @created_date : 07-Dec-2014
	 * @description
	 * @parm(s) : @param gooruOId
	 * @parm(s) : @param token
	 * @return : void
	 * @throws : <Mentioned if any exceptions>
	 */
	private void OpenClasspageEdit(String gooruOId, String token) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(UrlNavigationTokens.CLASSPAGEID, gooruOId);
		AppClientFactory.getPlaceManager().revealPlace(token, params);
		AppClientFactory.getPlaceManager().revealPlace(token, params);
	}
	/**
	 * @fileName : ClassCodeView.java
	 * @description : 
	 * @version : 1.0
	 * @date: 07-Dec-2014
	 * @Author Gooru Team
	 * @Reviewer:
	 */
	public class OnClickCreateClass implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			MixpanelUtil.ClickOnNewClassPage();
			if(AppClientFactory.isAnonymous()) {
				LoginPopupUc loginPopupUc=new LoginPopupUc() {
					@Override
					public	void onLoginSuccess(){
						
					}
				};
				Window.enableScrolling(false);
				loginPopupUc.show();
				loginPopupUc.setGlassEnabled(true);
			} else {
				newPopup = new NewClassPopupView()  {
					@Override
					public void createNewClasspage(String title, String grade, boolean sharing) {
						MixpanelUtil.Create_NewClasspage();
						CollectionDo collectionDo = new CollectionDo();
						collectionDo.setTitle(title);
						collectionDo.setCollectionType("classpage");
						AppClientFactory.getInjector().getClasspageService().createClass(title,grade,sharing,new SimpleAsyncCallback<ClasspageDo>() {
							@Override
							public void onSuccess(ClasspageDo result) {
								String[] uri=result.getUri().split("/");
								final String classpageId =  uri[uri.length-1];
								String title = result.getName();
								OpenClasspageEdit(classpageId, PlaceTokens.EDIT_CLASS);
								newPopup.ClosePopup();
							}
						});
					}
				};
			}
		}
	}
	/**
	 * 
	 * @fileName : ClassCodeView.java
	 *
	 * @description : 
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 07-Dec-2014
	 *
	 * @Author Gooru Team
	 *
	 * @Reviewer:
	 */
	public class OnEnterClassCodeClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			setEnterLblVisbility(true);
			if (txtCode.getText().trim().equalsIgnoreCase("") || txtCode.getText().trim() == null){
				alertMessageUc=new AlertMessageUc(i18n.GL0061(), new Label(i18n.GL0243()));
				ClickHandler alertHandler=new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						isValid=false;
						setEnterLblVisbility(false);
					}
				};
				alertMessageUc.appPopUp.addDomHandler(alertHandler, ClickEvent.getType());
				
				alertMessageUc.okButton.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						isValid=false;
						setEnterLblVisbility(false);
					}
				});
				return;
			}
			
			MixpanelUtil.ClickOnStudyNow();
			AppClientFactory.getInjector().getClasspageService().v3GetClassByCode(txtCode.getText().trim(), new SimpleAsyncCallback<ClasspageDo>(){

//				@Override
//				public void onFailure(Throwable caught) {
//					setEnterLblVisbility(false);
//				}
				
				@Override
				public void onSuccess(ClasspageDo result) {
					 setEnterLblVisbility(false);
					 
					 String classUid = null;
					 String status = null;
					 boolean sharing = false;
					 
					 if(result.getClassType()!=null) {
						 if(result.getClassType().equalsIgnoreCase("new-class")) {
							 classUid = result.getClassUid();
							 status = result.getStatus();
							 sharing = result.isVisibility();
						 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
							 classUid = result.getGooruOid();
							 status = result.getMeta().getStatus();
							 if(result.getSharing()!=null&&result.getSharing().equalsIgnoreCase("public")) {
								 sharing = true;
							 }
						 }
					 }
					 
					 if(classUid==null){
						Window.enableScrolling(false);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
						alertMessageUc=new AlertMessageUc(i18n.GL0061(), new Label(i18n.GL0244()));
						ClickHandler alertHandler=new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								isValid=false;

							}
						};
						alertMessageUc.appPopUp.addDomHandler(alertHandler, ClickEvent.getType());

						alertMessageUc.okButton.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								isValid=false;
							}
						});
					}else if(result.getUser().getGooruUId().equalsIgnoreCase(AppClientFactory.getGooruUid()))
					{
						 if(result.getClassType()!=null) {
							 Map<String, String> params = new HashMap<String, String>();
							 if(result.getClassType().equalsIgnoreCase("new-class")) {
									params.put("id",classUid);
									if(result.getCourseGooruOid() != null){
										params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
									}
									AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
							 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
									params.put("id",classUid);
									params.put("pageSize","5");
									params.put("pageNum","0");
									params.put("pos","1");
									params.put("b","true");
									AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
							 }
						 }
						txtCode.setText("");
						if(alertMessageUc!=null)
						alertMessageUc.hide();
					}
					 else if(!sharing){
						if(result.getUser().getGooruUId().equalsIgnoreCase(AppClientFactory.getGooruUid()))
						{
							if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
								MixpanelUtil.Click_Study_LandingPage();
							}
							
							 if(result.getClassType()!=null) {
								 Map<String, String> params = new HashMap<String, String>();
								 if(result.getClassType().equalsIgnoreCase("new-class")) {
										if(result.getCourseGooruOid() != null){
											params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
										}
										params.put("id",result.getClassUid());
										AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
								 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
										params.put("id",classUid);
										params.put("pageSize","5");
										params.put("pageNum","0");
										params.put("pos","1");
										params.put("b","true");
										AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
								 }
							 }
							txtCode.setText("");
							if(alertMessageUc!=null)
							alertMessageUc.hide();
						}
						else if(status!=null&&status.equalsIgnoreCase("active"))
						{
							if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
								MixpanelUtil.Click_Study_LandingPage();
							}
							
							 if(result.getClassType()!=null) {
								 Map<String, String> params = new HashMap<String, String>();
								 if(result.getClassType().equalsIgnoreCase("new-class")) {
										if(result.getCourseGooruOid() != null){
											params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
										}
										params.put("id",result.getClassUid());
										AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
								 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
										params.put("id",result.getGooruOid());
										params.put("pageSize","5");
										params.put("pageNum","0");
										params.put("pos","1");
										AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
								 }
							 }
							
							txtCode.setText("");
							if(alertMessageUc!=null)
							alertMessageUc.hide();
						}
						else if(status!=null&&status.equalsIgnoreCase("pending"))
						{
							if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
								MixpanelUtil.Click_Study_LandingPage();
							}
							
							 if(result.getClassType()!=null) {
								 Map<String, String> params = new HashMap<String, String>();
								 if(result.getClassType().equalsIgnoreCase("new-class")) {
										if(result.getCourseGooruOid() != null){
											params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
										}
										params.put("id",result.getClassUid());
										AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
								 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
							    	   new SentEmailSuccessVc(i18n.GL1177(), i18n.GL1535_1());
								 }
							 }

							txtCode.setText("");
							if(alertMessageUc!=null)
							alertMessageUc.hide();
						}
						else
						{
							       if(AppClientFactory.isAnonymous()){
							    	   new SentEmailSuccessVc(i18n.GL1177(), i18n.GL1535());
							       }else{
							    	   new SentEmailSuccessVc(i18n.GL1177(), i18n.GL1535_1());
							       }
						}

					}
					else
					{
						
						 if(result.getClassType()!=null) {
							 Map<String, String> params = new HashMap<String, String>();
							 if(result.getClassType().equalsIgnoreCase("new-class")) {
									if(result.getCourseGooruOid() != null){
										params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
									}
									params.put("id",result.getClassUid());
									AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
							 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
									params.put("id",result.getGooruOid());
									params.put("pageSize","5");
									params.put("pageNum","0");
									params.put("pos","1");
									AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
							 }
						 }
						
						txtCode.setText("");
						if(alertMessageUc!=null)
						alertMessageUc.hide();
					}
					setEnterLblVisbility(false);
			}

			});
		}
	}
	/**
	 * 
	 * @function setEnterLblVisbility 
	 * 
	 * @created_date : 07-Dec-2014
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param isVisible
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public void setEnterLblVisbility(boolean isVisible) {
		btnEnter.setVisible(!isVisible);
		disabledBtn.setVisible(isVisible);
	}
	
	@Override
	public void onLoad() {
		animate();
	}
	
	public static native void animate() /*-{
		new $wnd.WOW().init();
	}-*/;
	
	@UiHandler("signupAcr")
	public void clickSignupAcr(ClickEvent event) {
		Map<String, String> map = StringUtil.splitQuery(Window.Location.getHref());
		map.put("callback", "signup");
		map.put("type", "1");
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), map);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, false);
	}
}