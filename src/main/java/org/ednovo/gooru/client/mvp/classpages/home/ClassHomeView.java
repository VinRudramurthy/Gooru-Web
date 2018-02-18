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
package org.ednovo.gooru.client.mvp.classpages.home;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageListDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.study.StudyClassCodeView;
import org.ednovo.gooru.client.mvp.classpages.newclasspage.NewClassPopupView;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.socialshare.SentEmailSuccessVc;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.LoadingUc;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
/**
 *
 * @fileName : ClassHomeView.java
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
public class ClassHomeView extends BaseViewWithHandlers<ClassHomeUiHandlers> implements IsClassHomeView {

	private static ClassCodeViewUiBinder uiBinder = GWT
			.create(ClassCodeViewUiBinder.class);

	MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField Button btnCreateClass,btnEnter,disabledBtn,seeMorebtnJoined,seeMorebtnOwner;

	/*@UiField Label lblCreateAClass;*/

	@UiField HTMLPanel joinedClassesContainer,ownerClassesContainer;

	@UiField TextBoxWithPlaceholder txtCode;

	@UiField Anchor myClassesAnr,archivedAnr;

	@UiField H3Panel joinedContainerTitle,teachContainerTitle;

	@UiField PPanel schoolAlertPanel;

	AlertMessageUc alertMessageUc;

	@UiField LoadingUc studyLoading,teachLoading;


	private NewClassPopupView newPopup = null;

	private boolean isValid=true;

	private Integer pageInitialLimitJoined = 10;
	private Integer offsetLimitJoined = 0;

	private Integer defaultLimit = 10;

	private Integer pageInitialLimitOwner = 10;
	private Integer offsetLimitOwner = 0;


	@UiField HTMLPanel notesPanel,createClassPanel,classCodePanel,mainPanel,emptyClassesPanel,landingPagePanel;




	interface ClassCodeViewUiBinder extends UiBinder<Widget, ClassHomeView> {

	}



	@Inject
	public ClassHomeView() {
		setWidget(uiBinder.createAndBindUi(this));

		setText();
		myClassesAnr.addClickHandler(new TeachClassTabNavigationHandler(myClassesAnr));
		archivedAnr.addClickHandler(new TeachClassTabNavigationHandler(archivedAnr));
		landingPagePanel.setVisible(false);
		emptyClassesPanel.add(new StudyClassCodeView());
		emptyClassesPanel.setVisible(false);
		mainPanel.setVisible(false);

	}
	/**
	 *
	 */
	public void callServiceRequestsToBindData() {
		ownerClassesContainer.clear();
		joinedClassesContainer.clear();
		joinedClassesContainer.getElement().setInnerHTML("");
		ownerClassesContainer.getElement().setInnerHTML("");
		txtCode.setText("");
		pageInitialLimitOwner = 10;
		pageInitialLimitJoined = 10;
		offsetLimitOwner = 0;
		offsetLimitJoined = 0;

		getUiHandlers().getV2TeachStudy();

		getUiHandlers().getV1TeachStudy("10", "0");


		String view = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT,"");



		if(view.equalsIgnoreCase(UrlNavigationTokens.MYCLASS) || view.isEmpty()){
			isSetVisiblity(false);
			studyLoading.setVisible(true);
			teachLoading.setVisible(true);
			myClassesAnr.addStyleName(CssTokens.ACTIVE);
			archivedAnr.removeStyleName(CssTokens.ACTIVE);
			AppClientFactory.getInjector().getClasspageService().v3GetUserClasses(defaultLimit.toString(), offsetLimitOwner.toString(), false,
					new SimpleAsyncCallback<ClasspageListDo >() {
						@Override
						public void onSuccess(ClasspageListDo result) {
							try{
							teachLoading.setVisible(false);
							if(result.getTotalHitCount()>pageInitialLimitOwner)
							{
								seeMorebtnOwner.setVisible(true);
							}
							else
							{
								seeMorebtnOwner.setVisible(false);
							}

							if(result.getSearchResult().size()>0)
							{
								ownerClassesContainer.getElement().setInnerHTML("");
							for(int i = 0; i<result.getSearchResult().size();i++)
							{
								ClasspageWidgetView classpageWidget =  new ClasspageWidgetView();
								classpageWidget.setClassPageImage(result.getSearchResult().get(i),"Teach");
								ownerClassesContainer.add(classpageWidget);
							}
							}
							else
							{
								ownerClassesContainer.getElement().setInnerHTML(i18n.GL1929());
								ownerClassesContainer.getElement().getStyle().setPaddingLeft(15, Unit.PX);
							}
							}catch(Exception e){
								AppClientFactory.printInfoLogger("v3GetUserClasses..."+e.getMessage());
							}
						}
					});

			AppClientFactory.getInjector().getClasspageService().v3GetUserStudyClasses(defaultLimit.toString(), offsetLimitJoined.toString(),
					new SimpleAsyncCallback<ClasspageListDo >() {
						@Override
						public void onSuccess(ClasspageListDo result) {
							studyLoading.setVisible(false);
							if(result.getTotalHitCount()>pageInitialLimitJoined)
							{
								seeMorebtnJoined.setVisible(true);
							}
							else
							{
								seeMorebtnJoined.setVisible(false);
							}
							if(result.getSearchResult().size()>0)
							{
							joinedClassesContainer.getElement().setInnerHTML("");
							for(int i = 0; i<result.getSearchResult().size();i++)
							{
								ClasspageWidgetView classpageWidget =  new ClasspageWidgetView();
								classpageWidget.setClassPageImage(result.getSearchResult().get(i),"Study");
								joinedClassesContainer.add(classpageWidget);
							}
							}
							else
							{
								joinedClassesContainer.getElement().setInnerHTML(i18n.GL1930());
								joinedClassesContainer.getElement().getStyle().setPaddingLeft(15, Unit.PX);
							}

						}
					});
		}else if(view.equalsIgnoreCase(UrlNavigationTokens.OLDCLASS)){
			landingPagePanel.setVisible(true);
			studyLoading.setVisible(true);
			teachLoading.setVisible(true);
			myClassesAnr.removeStyleName(CssTokens.ACTIVE);
			archivedAnr.addStyleName(CssTokens.ACTIVE);
			isSetVisiblity(true);
			AppClientFactory.getInjector().getClasspageService().v2GetUserClasses(defaultLimit.toString(), offsetLimitOwner.toString(),String.valueOf(Math.random()),
					new SimpleAsyncCallback<ClasspageListDo >() {
						@Override
						public void onSuccess(ClasspageListDo result) {
							teachLoading.setVisible(false);
							if(result.getTotalHitCount()>pageInitialLimitOwner)
							{
								seeMorebtnOwner.setVisible(true);
							}
							else
							{
								seeMorebtnOwner.setVisible(false);
							}

							if(result.getSearchResults().size()>0)
							{
								ownerClassesContainer.getElement().setInnerHTML("");
							for(int i = 0; i<result.getSearchResults().size();i++)
							{
								ClasspageWidgetView classpageWidget =  new ClasspageWidgetView();
								classpageWidget.setArchedClassPageImage(result.getSearchResults().get(i),"Teach");
								ownerClassesContainer.add(classpageWidget);
							}
							}
							else
							{
								ownerClassesContainer.getElement().setInnerHTML(i18n.GL1929());
								ownerClassesContainer.getElement().getStyle().setPaddingLeft(15, Unit.PX);
							}

						}
					});

			AppClientFactory.getInjector().getClasspageService().v2GetUserStudyClasses(defaultLimit.toString(), offsetLimitJoined.toString(),String.valueOf(Math.random()),
					new SimpleAsyncCallback<ClasspageListDo >() {
						@Override
						public void onSuccess(ClasspageListDo result) {
							studyLoading.setVisible(false);
							if(result.getTotalHitCount()>pageInitialLimitJoined)
							{
								seeMorebtnJoined.setVisible(true);
							}
							else
							{
								seeMorebtnJoined.setVisible(false);
							}
							if(result.getSearchResults().size()>0)
							{
							joinedClassesContainer.getElement().setInnerHTML("");
							for(int i = 0; i<result.getSearchResults().size();i++)
							{
								ClasspageWidgetView classpageWidget =  new ClasspageWidgetView();
								classpageWidget.setArchedClassPageImage(result.getSearchResults().get(i),"Study");
								joinedClassesContainer.add(classpageWidget);
							}
							}
							else
							{
								joinedClassesContainer.getElement().setInnerHTML(i18n.GL1930());
								joinedClassesContainer.getElement().getStyle().setPaddingLeft(15, Unit.PX);
							}

						}
					});

		}


	}
	private void setCreateClassVisibility() {

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

		setCreateClassVisibility();
		disabledBtn.setText(i18n.GL0213());
		joinedContainerTitle.getElement().setInnerHTML(i18n.GL1925());
		joinedContainerTitle.getElement().setId("pnlJoinedContainerTitle");
		joinedContainerTitle.getElement().setAttribute("alt",i18n.GL1925());
		joinedContainerTitle.getElement().setAttribute("title",i18n.GL1925());

		teachContainerTitle.getElement().setInnerHTML(i18n.GL1927());
		teachContainerTitle.getElement().setId("pnlTeachContainerTitle");
		teachContainerTitle.getElement().setAttribute("alt",i18n.GL1927());
		teachContainerTitle.getElement().setAttribute("title",i18n.GL1927());

		txtCode.setPlaceholder(i18n.GL1785());
		txtCode.getElement().setId("txtCode");
		txtCode.getElement().setAttribute("alt",i18n.GL1785());
		txtCode.getElement().setAttribute("title",i18n.GL1785());

		btnEnter.setText(i18n.GL0213());
		btnEnter.getElement().setId("btnEnter");
		btnEnter.getElement().setAttribute("alt",i18n.GL0213());
		btnEnter.getElement().setAttribute("title",i18n.GL0213());

		btnCreateClass.getElement().setInnerHTML(i18n.GL1928());
		btnCreateClass.getElement().setId("btnCreateClass");
		btnCreateClass.getElement().setAttribute("alt",i18n.GL1928());
		btnCreateClass.getElement().setAttribute("title",i18n.GL1928());

		disabledBtn.setVisible(false);
		seeMorebtnJoined.setText(i18n.GL0508());
		seeMorebtnJoined.getElement().setId("btnSeeMoreJoined");
		seeMorebtnJoined.getElement().setAttribute("alt",i18n.GL0508());
		seeMorebtnJoined.getElement().setAttribute("title",i18n.GL0508());

		seeMorebtnOwner.setText(i18n.GL0508());
		seeMorebtnOwner.getElement().setId("btnSeeMoreOwner");
		seeMorebtnOwner.getElement().setAttribute("alt",i18n.GL0508());
		seeMorebtnOwner.getElement().setAttribute("title",i18n.GL0508());

		studyLoading.getElement().setId("studyLoadingId");
		studyLoading.getElement().setAttribute("alt","Loading");
		studyLoading.getElement().setAttribute("title","Loading");

		teachLoading.getElement().setId("studyLoadingId");
		teachLoading.getElement().setAttribute("alt","Loading");
		teachLoading.getElement().setAttribute("title","Loading");

		schoolAlertPanel.setText(i18n.GL3450_9());
		schoolAlertPanel.getElement().setId("schoolAlertPanelId");

		studyLoading.setVisible(false);
		teachLoading.setVisible(false);


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
		seeMorebtnJoined.addClickHandler(new OnClickSeeMoreJoined());
		seeMorebtnOwner.addClickHandler(new OnClickSeeMoreOwner());
		joinedClassesContainer.getElement().setId("pnljJoinedClassesContainer");
		//lblCreateAClass.getElement().setId("lblCreateAClass");
		ownerClassesContainer.getElement().setId("pnlOwnerClassesContainer");
	}


	/**
	 *
	 * @function OpenClasspageEdit
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param gooruOId
	 * @parm(s) : @param token
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void OpenClasspageEdit(String gooruOId, String token) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(UrlNavigationTokens.CLASSPAGEID, gooruOId);
		params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.TEACHER_CLASS_SETTINGS);
		params.put(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW, UrlNavigationTokens.TEACHER_CLASS_CONTENT_SUB_SCORE);
		AppClientFactory.getPlaceManager().revealPlace(token, params);
	}
	/**
	 *
	 * @fileName : ClassHomeView.java
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
	public class OnClickCreateClass implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			MixpanelUtil.ClickOnNewClassPage();

			newPopup = new NewClassPopupView() {

				@Override
				public void createNewClasspage(String title, String grade, boolean sharing) {

					MixpanelUtil.Create_NewClasspage();
					CollectionDo collectionDo = new CollectionDo();
					collectionDo.setTitle(title);
					collectionDo.setCollectionType("classpage");
					AppClientFactory
							.getInjector()
							.getClasspageService()
							.createClass(title,grade,sharing,new SimpleAsyncCallback<ClasspageDo>() {

										@Override
										public void onSuccess(ClasspageDo result) {
											//final String classpageId = result.getUri();
											String[] uri=result.getUri().split("/");
											final String classpageId =  uri[uri.length-1];
											OpenClasspageEdit(classpageId, PlaceTokens.EDIT_CLASS);
											newPopup.ClosePopup();
												}
											});
					}
			};
			newPopup.center();
		}

	}
	/**
	 *
	 * @fileName : ClassHomeView.java
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
	 * @fileName : ClassHomeView.java
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
	public class OnClickSeeMoreJoined implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			offsetLimitJoined = pageInitialLimitJoined;
			String view = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT,"");
			if(view.equalsIgnoreCase(UrlNavigationTokens.MYCLASS)){
				AppClientFactory.getInjector().getClasspageService().v3GetUserStudyClasses(defaultLimit.toString(), offsetLimitJoined.toString(),
						new SimpleAsyncCallback<ClasspageListDo >() {
							@Override
							public void onSuccess(ClasspageListDo result) {
								pageInitialLimitJoined = pageInitialLimitJoined + 10;
								if(result.getTotalHitCount()>pageInitialLimitJoined){
									seeMorebtnJoined.setVisible(true);
								}else{
									seeMorebtnJoined.setVisible(false);
								}
								for(int i = 0; i<result.getSearchResult().size();i++) {
									ClasspageWidgetView classpageWidget =  new ClasspageWidgetView();
									classpageWidget.setClassPageImage(result.getSearchResult().get(i),"Study");
									joinedClassesContainer.add(classpageWidget);
								}
							}
						});
			}else if(view.equalsIgnoreCase(UrlNavigationTokens.OLDCLASS)){
				offsetLimitJoined = pageInitialLimitJoined;
				AppClientFactory.getInjector().getClasspageService().v2GetUserStudyClasses(defaultLimit.toString(), offsetLimitJoined.toString(),String.valueOf(Math.random()),
						new SimpleAsyncCallback<ClasspageListDo >() {
							@Override
							public void onSuccess(ClasspageListDo result) {
								pageInitialLimitJoined = pageInitialLimitJoined + 10;
								if(result.getTotalHitCount()>pageInitialLimitJoined)
								{
									seeMorebtnJoined.setVisible(true);
								}
								else
								{
									seeMorebtnJoined.setVisible(false);
								}


								for(int i = 0; i<result.getSearchResults().size();i++)
								{
									ClasspageWidgetView classpageWidget =  new ClasspageWidgetView();
									classpageWidget.setArchedClassPageImage(result.getSearchResults().get(i),"Study");
									joinedClassesContainer.add(classpageWidget);
								}


							}
						});
			}

		}

	}
	/**
	 *
	 * @fileName : ClassHomeView.java
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
	public class OnClickSeeMoreOwner implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			String view = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT,"");
			if(view.equalsIgnoreCase(UrlNavigationTokens.MYCLASS)){
				offsetLimitOwner = pageInitialLimitOwner;
				AppClientFactory.getInjector().getClasspageService().v3GetUserClasses(defaultLimit.toString(), offsetLimitOwner.toString(), false,
						new SimpleAsyncCallback<ClasspageListDo >() {
							@Override
							public void onSuccess(ClasspageListDo result) {
								pageInitialLimitOwner = pageInitialLimitOwner + 10;
								if(result.getTotalHitCount()>pageInitialLimitOwner)
								{
									seeMorebtnOwner.setVisible(true);
								}
								else
								{
									seeMorebtnOwner.setVisible(false);
								}

								for(int i = 0; i<result.getSearchResult().size();i++)
								{
									ClasspageWidgetView classpageWidget =  new ClasspageWidgetView();
									classpageWidget.setClassPageImage(result.getSearchResult().get(i),"Teach");
									ownerClassesContainer.add(classpageWidget);
								}


							}
						});
			}else if(view.equalsIgnoreCase(UrlNavigationTokens.OLDCLASS)){
				offsetLimitOwner = pageInitialLimitOwner;
				AppClientFactory.getInjector().getClasspageService().v2GetUserClasses(defaultLimit.toString(), offsetLimitOwner.toString(),String.valueOf(Math.random()),
						new SimpleAsyncCallback<ClasspageListDo >() {
							@Override
							public void onSuccess(ClasspageListDo result) {
								pageInitialLimitOwner = pageInitialLimitOwner + 10;
								if(result.getTotalHitCount()>pageInitialLimitOwner)
								{
									seeMorebtnOwner.setVisible(true);
								}
								else
								{
									seeMorebtnOwner.setVisible(false);
								}


								for(int i = 0; i<result.getSearchResults().size();i++)
								{
									ClasspageWidgetView classpageWidget =  new ClasspageWidgetView();
									classpageWidget.setArchedClassPageImage(result.getSearchResults().get(i),"Teach");
									ownerClassesContainer.add(classpageWidget);
								}


							}
				 });
			}

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

	private class TeachClassTabNavigationHandler implements ClickHandler{


		Anchor headerAnr;

		public TeachClassTabNavigationHandler(Anchor headerAnr){
			this.headerAnr=headerAnr;
		}
		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		@Override
		public void onClick(ClickEvent event) {
			if(headerAnr.equals(myClassesAnr)){
				Map<String, String> params = new HashMap<String, String>();
				params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.MYCLASS);
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.CLASSHOME,params);
			}else if(headerAnr.equals(archivedAnr)){
				Map<String, String> params = new HashMap<String, String>();
				params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.OLDCLASS);
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.CLASSHOME,params);
			}
		}

	}

	public void isSetVisiblity(boolean isVisible){
		notesPanel.setVisible(isVisible);
		classCodePanel.setVisible(!isVisible);
		createClassPanel.setVisible(!isVisible);
	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpages.home.IsClassHomeView#setClassVisiblityData(org.ednovo.gooru.application.shared.model.content.ClasspageListDo)
	 */
	@Override
	public void setClassVisiblityData(ClasspageListDo result) {
		String view = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT,"");
		if(result != null && result.getSearchResults() != null){
			if(result.getSearchResults().size() > 0){
				mainPanel.setVisible(true);
				if(view.equalsIgnoreCase(UrlNavigationTokens.OLDCLASS)){
					landingPagePanel.setVisible(true);
					emptyClassesPanel.setVisible(false);
				}
			}else{
				mainPanel.setVisible(false);
			}
		}
	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpages.home.IsClassHomeView#setClassesData(java.lang.Boolean)
	 */
	@Override
	public void setClassesData(Boolean result) {
		String view = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT,"");
		if(view.equalsIgnoreCase(UrlNavigationTokens.MYCLASS)){
			if(result){
				landingPagePanel.setVisible(true);
				emptyClassesPanel.setVisible(false);
			}else{
				landingPagePanel.setVisible(false);
				emptyClassesPanel.setVisible(true);
			}
		}

	}
}
