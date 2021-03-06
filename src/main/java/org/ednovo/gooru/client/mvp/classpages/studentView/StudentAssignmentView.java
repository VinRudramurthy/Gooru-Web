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
package org.ednovo.gooru.client.mvp.classpages.studentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.client.mvp.classpages.edit.EditClasspageCBundle;
import org.ednovo.gooru.client.mvp.classpages.event.DeleteClasspageListEvent;
import org.ednovo.gooru.client.mvp.classpages.event.OpenJoinClassPopupEvent;
import org.ednovo.gooru.client.mvp.classpages.event.OpenJoinClassPopupHandler;
import org.ednovo.gooru.client.mvp.classpages.event.RefreshClasspageListEvent;
import org.ednovo.gooru.client.mvp.classpages.event.SetSelectedClasspageListEvent;
import org.ednovo.gooru.client.mvp.classpages.tabitem.assignments.collections.CollectionsView;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.DeletePopupViewVc;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.SuccessPopupViewVc;
import org.ednovo.gooru.client.mvp.socialshare.SentEmailSuccessVc;
import org.ednovo.gooru.client.uc.tooltip.LibraryTopicCollectionToolTip;
import org.ednovo.gooru.shared.model.content.ClasspageDo;
import org.ednovo.gooru.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.shared.util.DataLogEvents;
import org.ednovo.gooru.shared.util.GwtUUIDGenerator;
import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
/**
 * 
 * @fileName : StudentAssignmentView.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: Nov 22, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class StudentAssignmentView extends BaseViewWithHandlers<StudentAssignmentUiHandlers> implements IsStudentAssignmentView ,ClickHandler,MessageProperties{
	
	private static StudentAssignmentViewUiBinder uiBinder = GWT.create(StudentAssignmentViewUiBinder.class);

	interface StudentAssignmentViewUiBinder extends UiBinder<Widget, StudentAssignmentView> {    

	}
	
	@UiField Label mainTitleLbl,noAssignmentMsg,lblUserName;
	
	@UiField HTMLPanel contentpanel;
	
	@UiField
	static HTMLPanel mainContainer;
	
	@UiField Button backToEditPanel;

	@UiField
	static Button btnJoinClass;

	@UiField
	static
	Button btnWithDraw;
	
	@UiField FlowPanel paginationFocPanel;
	
	@UiField Image studentViewImage,imgProfileImage;

	@UiField
	static Image userImage;
	
	@UiField
	static Label lblWebHelp;

	@UiField
	static
	Label LblMember;
	
	static StudentJoinClassPopup joinPopupPrivate;
	
	static StudentJoinClassPopup joinPopupPublic;
	
	static StudentJoinClassPopup joinPopupButtonClick;
	
	static ClasspageDo classpageDo;
	
	String defaultProfileImage = "images/settings/setting-user-image.png";
	
	private static boolean isJoinPopupPublic = false;
	
	private static boolean isJoinPopupPrivate = false;
	
	private static boolean isJoinPopupPrivateStatic = false;
	
	private static boolean isJoinPopupButtonclick = false;
	
	private String DEFAULT_CLASSPAGE_IMAGE = "images/Classpage/default-classpage.png";
	private int totalHitCount=0;
	private int limit=5;
	private int pageNumber=1;
	private PopupPanel toolTipPopupPanel = new PopupPanel();
	
	public static boolean islogin = false;
	public static boolean isloginPrivate = false;
	public static boolean isloginButtonClick = false;
	
	
	@Inject
	public StudentAssignmentView() {
		setWidget(uiBinder.createAndBindUi(this));
		EditClasspageCBundle.INSTANCE.css().ensureInjected();
		setStaticData();
		lblWebHelp.addMouseOverHandler(new OnMouseOver());
		lblWebHelp.addMouseOutHandler(new OnMouseOut());
		OpenJoinClassPopupHandler openJoinClassPopupHandler=new OpenJoinClassPopupHandler() {
			
			@Override
			public void openJoinClassPopup() {
				openJoinClassEvent();
			}
		};
		
		AppClientFactory.getEventBus().addHandler(OpenJoinClassPopupEvent.TYPE,openJoinClassPopupHandler);

	}

	private void setStaticData() {
		backToEditPanel.setText(GL1130);
		noAssignmentMsg.setText(GL1131);
		btnJoinClass.setText(GL1536);
		btnWithDraw.setText(GL1537);
		LblMember.setText(GL1549);
		btnJoinClass.getElement().setId("btnJoinClass");
		backToEditPanel.getElement().setId("btnBackToEdit");
		noAssignmentMsg.setVisible(false);
		btnJoinClass.setVisible(false);
		lblWebHelp.setVisible(false);
		btnWithDraw.setVisible(false);
		LblMember.setVisible(false);
		userImage.setVisible(false);
	}

	@Override
	public void setClasspageData(ClasspageDo classpageDo) {
		this.classpageDo=classpageDo;
		mainTitleLbl.setText(classpageDo.getTitle() !=null ? classpageDo.getTitle() : "");
		studentViewImage.setAltText(classpageDo.getTitle() !=null ? classpageDo.getTitle() : "");
		studentViewImage.setTitle(classpageDo.getTitle() !=null ? classpageDo.getTitle() : "");
		studentViewImage.setUrl(classpageDo.getThumbnailUrl());
		AppClientFactory.fireEvent(new SetSelectedClasspageListEvent(classpageDo.getClasspageId()));
		imgProfileImage.setUrl(classpageDo.getCreatorProfileImage());
		lblUserName.setText(classpageDo.getCreatorUsername());
		
		imgProfileImage.addErrorHandler(new ErrorHandler() {
			
			@Override
			public void onError(ErrorEvent event) {
				imgProfileImage.setUrl(defaultProfileImage);
			}
		});

		
		if(classpageDo.getSharing().equalsIgnoreCase("public"))
		{
			if(classpageDo.getCreatorId().equalsIgnoreCase(AppClientFactory.getGooruUid()))
			{
				btnJoinClass.setVisible(false);
				userImage.setVisible(true);
				lblWebHelp.setVisible(false);
				btnWithDraw.setVisible(false);
				LblMember.setVisible(true);
				userImage.setVisible(true);
				LblMember.setText(GL1551);
				mainContainer.setVisible(true);
			}
			else if(classpageDo.getStatus().equalsIgnoreCase("active")){
				btnJoinClass.setVisible(false);
				userImage.setVisible(true);
				lblWebHelp.setVisible(false);
				btnWithDraw.setVisible(true);
				LblMember.setVisible(true);
				userImage.setVisible(true);
				mainContainer.setVisible(true);
			}
			else 
			{
				
				btnJoinClass.setVisible(true);
				userImage.setVisible(false);
				lblWebHelp.setVisible(true);
				btnWithDraw.setVisible(false);
				LblMember.setVisible(false);
				userImage.setVisible(false);
				mainContainer.setVisible(true);


				
			}	
		}
		else
		{
			if(AppClientFactory.isAnonymous()){


				mainContainer.setVisible(false);
				LoginPopupUc loginPopupUc=new LoginPopupUc();
				
			}else{
			if(classpageDo.getCreatorId().equalsIgnoreCase(AppClientFactory.getGooruUid()))
			{
				btnJoinClass.setVisible(false);
				userImage.setVisible(true);
				lblWebHelp.setVisible(false);
				btnWithDraw.setVisible(false);
				LblMember.setVisible(true);
				LblMember.setText(GL1551);
				userImage.setVisible(true);
				mainContainer.setVisible(true);
			}
			else if(classpageDo.getStatus().equalsIgnoreCase("active")){
					btnJoinClass.setVisible(false);
					userImage.setVisible(true);
					lblWebHelp.setVisible(false);
					btnWithDraw.setVisible(false);
					LblMember.setVisible(true);
					userImage.setVisible(true);
					mainContainer.setVisible(true);
				}else if(classpageDo.getStatus().equalsIgnoreCase("pending")) {
					btnJoinClass.setVisible(true);
					userImage.setVisible(false);
					lblWebHelp.setVisible(true);
					btnWithDraw.setVisible(false);
					LblMember.setVisible(false);
					userImage.setVisible(false);
					mainContainer.setVisible(false);


						if(!isJoinPopupPrivateStatic){
							isJoinPopupPrivateStatic=true;
							joinPopupPrivate =  new StudentJoinClassPopup(classpageDo) {
							
							@Override
							void joinIntoClass() {
									String emailId=AppClientFactory.getLoggedInUser().getEmailId();
									AppClientFactory.getInjector().getClasspageService().studentJoinIntoClass(classpageDo.getClasspageCode(),emailId, new SimpleAsyncCallback<ClasspageDo>() {

										@Override
										public void onSuccess(ClasspageDo result) {
											joinPopupPrivate.hide();
											mainContainer.setVisible(true);
											SuccessPopupViewVc success=new SuccessPopupViewVc(){

												@Override
												public void onClickPositiveButton(
														ClickEvent event) {
													Window.enableScrolling(true);
													btnJoinClass.setVisible(false);
													userImage.setVisible(true);
													lblWebHelp.setVisible(false);
													btnWithDraw.setVisible(false);
													LblMember.setVisible(true);
													LblMember.setText(GL1549);
													mainContainer.setVisible(true);
													this.hide();
													isJoinPopupPrivateStatic=false;
													
												}
												
											};
											success.setHeight("248px");
                                            success.setWidth("450px");
                                            success.setPopupTitle(GL1553);
                                            success.setDescText(GL1554+classpageDo.getTitle()+GL_SPL_EXCLAMATION+'\n'+GL1552);
                                            success.setPositiveButtonText(GL0190);
                                            success.center();
                                            success.show();
								
										}
									});
							}

							@Override
							public void closePoup() {
								hide();
								Window.enableScrolling(true);
								AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
							}
						};
						}
						int windowHeight=Window.getClientHeight()/2; //I subtract 10 from the client height so the window isn't maximized.
						int windowWidth=Window.getClientWidth()/2;
						joinPopupPrivate.setPopupPosition(windowWidth-253, windowHeight-70);
						joinPopupPrivate.setPixelSize(506, 261);		
						//joinPopup.center();
						joinPopupPrivate.show();
					
					
				}
				else 
				{
					try
					{
					mainContainer.setVisible(false);
					}
					catch(Exception ex)
					{
						
					}
				       if(AppClientFactory.isAnonymous()){
				    	   new SentEmailSuccessVc(GL1177, GL1535);
				       }else{
				    	   new SentEmailSuccessVc(GL1177, GL1535_1);
				       }
				}
			
		}
		}
		/*}
		else
		{
			if(classpageDo.getStatus().equalsIgnoreCase("active")){
				btnJoinClass.setVisible(false);
				lblWebHelp.setVisible(false);
				btnWithDraw.setVisible(true);
				LblMember.setVisible(true);
			}else if(classpageDo.getStatus().equalsIgnoreCase("pending")) {
				btnJoinClass.setVisible(true);
				lblWebHelp.setVisible(true);
				btnWithDraw.setVisible(false);
				LblMember.setVisible(false);
			}else {
		//dont have access popup
			}
		}*/

		
		//here we need to check for http://collab.ednovo.org/jira/browse/CORE-516 this 
		//api response and display the button text and functionality.
		DataLogEvents.classpageView(GwtUUIDGenerator.uuid(), "classpage-view", classpageDo.getClasspageId(), AppClientFactory.getLoggedInUser().getGooruUId(), System.currentTimeMillis(), System.currentTimeMillis(),"",0L, AppClientFactory.getLoggedInUser().getToken()	,"start");
	}
	
	@UiHandler("studentViewImage")
	public void setErrorImage(ErrorEvent event){
		studentViewImage.setUrl(DEFAULT_CLASSPAGE_IMAGE);
	}
	
	@Override
	public void showClasspageItems(ArrayList<ClasspageItemDo> classpageItemsList){
		removeLoadingPanel();
		if(classpageItemsList!=null&&classpageItemsList.size()>0){
			noAssignmentMsg.setVisible(false);
			for(int itemIndex=0;itemIndex<classpageItemsList.size();itemIndex++){
				ClasspageItemDo classpageItemDo=classpageItemsList.get(itemIndex);
				CollectionsView collectionsView = new CollectionsView(classpageItemDo,true){
					public void resetPagination(){
						setPagination();
						contentpanel.add(setLoadingPanel());
						getUiHandlers().getNextClasspageItems(((pageNumber*limit)-1),1);
					}
				};
				this.totalHitCount=classpageItemDo.getTotalHitCount();
				contentpanel.add(collectionsView);
			}
			setPagination();
		}else{
			noAssignmentMsg.setVisible(true);
		}
	}
	public void setPagination(){
		if((pageNumber*limit)<this.totalHitCount){
			showPaginationButton();
		}else{
			clearPaginationButton();
		}
	}
	public void showPaginationButton(){
		paginationFocPanel.clear();
		Label seeMoreLabel=new Label(GL0508);
		seeMoreLabel.addClickHandler(new PaginationEvent());
		seeMoreLabel.setStyleName(EditClasspageCBundle.INSTANCE.css().paginationPanel());
		paginationFocPanel.add(seeMoreLabel);
	}
	public void clearPaginationButton(){
		paginationFocPanel.clear();
	}
	private class PaginationEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			pageNumber++;
			setPagination();
			contentpanel.add(setLoadingPanel());
			getUiHandlers().getNextClasspageItems(((pageNumber-1)*limit),limit);
		}
	}
	public void resetEditClasspageView(){
		paginationFocPanel.clear();
		contentpanel.clear();
		contentpanel.add(setLoadingPanel());
		limit=20;
		pageNumber=1;
	}
	public Label setLoadingPanel(){
		Label loadingImage=new Label();
		loadingImage.setStyleName(EditClasspageCBundle.INSTANCE.css().loadingpanelImage());
		return loadingImage;
	}
	public void removeLoadingPanel(){
		if(contentpanel.getWidgetCount()>0){
			Widget loadingPanel=contentpanel.getWidget(contentpanel.getWidgetCount()-1);
			if(loadingPanel!=null&&loadingPanel instanceof Label){
				loadingPanel.removeFromParent();
			}
		}
	}
	@UiHandler("backToEditPanel")
	public void onClickHandler(ClickEvent event){
		//getPreviousPage();
		String pageSize=Cookies.getCookie("pageSize");
		String classpageid=Cookies.getCookie("classpageid");
		String pageNum=Cookies.getCookie("pageNum");
		String pos=Cookies.getCookie("pos");
		
		if(AppClientFactory.getPlaceManager().getRequestParameter("source").equalsIgnoreCase("T")){
//			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.TEACH);
		}
		else if(AppClientFactory.getPlaceManager().getRequestParameter("source").equalsIgnoreCase("E")){
			
			Map<String, String> params=new HashMap<String, String>();
			params.put("pageSize", pageSize);
			params.put("classpageid", classpageid);
			params.put("pageNum", pageNum);
			params.put("pos", pos);
			
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.EDIT_CLASSPAGE,params,true);
		}
		backToEditPanel.setVisible(false);
	}

	@Override
	public void onClick(ClickEvent event) {
		
	}
	
	@UiHandler("btnJoinClass")
	public void joinClassPopup(ClickEvent clickEvent){

		if(AppClientFactory.isAnonymous()){
	
			LoginPopupUc loginPopupUc=new LoginPopupUc();
			
			
			
			
		}else{
			if(!isJoinPopupButtonclick){
				isJoinPopupButtonclick=true;
				joinPopupButtonClick =  new StudentJoinClassPopup(classpageDo) {
				
				@Override
				void joinIntoClass() {
						String emailId=AppClientFactory.getLoggedInUser().getEmailId();
						AppClientFactory.getInjector().getClasspageService().studentJoinIntoClass(classpageDo.getClasspageCode(),emailId, new SimpleAsyncCallback<ClasspageDo>() {

							@Override
							public void onSuccess(ClasspageDo result) {
								joinPopupButtonClick.hide();
								SuccessPopupViewVc success=new SuccessPopupViewVc(){

									@Override
									public void onClickPositiveButton(
											ClickEvent event) {
										Window.enableScrolling(true);
										btnJoinClass.setVisible(false);
										userImage.setVisible(true);
										lblWebHelp.setVisible(false);
										btnWithDraw.setVisible(true);
										LblMember.setVisible(true);
										LblMember.setText(GL1549);
										mainContainer.setVisible(true);
										this.hide();
										isJoinPopupButtonclick=false;
									}
									
								};
								success.setHeight("248px");
                                success.setWidth("450px");
                                success.setPopupTitle(GL1553);
                                success.setDescText(GL1554+classpageDo.getTitle()+GL_SPL_EXCLAMATION+'\n'+GL1552);
                                success.setPositiveButtonText(GL0190);
                                success.center();
                                success.show();
								
							}
						});
				}

				@Override
				public void closePoup() {
					hide();
					Window.enableScrolling(true);
				}
			};
			}
			joinPopupButtonClick.setPopupPosition(btnJoinClass.getElement().getAbsoluteLeft() - 509, btnJoinClass.getElement().getAbsoluteTop());
			joinPopupButtonClick.setPixelSize(506, 261);		
			//joinPopup.center();
			joinPopupButtonClick.show();

		}
	    
	}
	
	@UiHandler("btnWithDraw")
	public void onWithdrawButtonClick(ClickEvent clickEvent){
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		DeletePopupViewVc delete = new DeletePopupViewVc() {

			@Override
			public void onClickPositiveButton(ClickEvent event) 
			{
				final ArrayList<String> arrayEmailId = new ArrayList<String>();
				arrayEmailId.add('"'+AppClientFactory.getLoggedInUser().getEmailId()+'"');
				
				getUiHandlers().removeUserFromClass(classpageDo, arrayEmailId.toString());
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
				Window.enableScrolling(true);
				AppClientFactory.fireEvent(new DeleteClasspageListEvent(classpageDo.getClasspageId()));
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
				hide();
			}

			@Override
			public void onClickNegitiveButton(ClickEvent event) {
				Window.enableScrolling(true);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
				hide();
				
			}
			
		};
		
		delete.setPopupTitle(GL1618);
		delete.setNotes(GL0748);
		delete.setDescText(GL1555);
		delete.setPositiveButtonText(GL_GRR_YES);							
		delete.setNegitiveButtonText(GL0142);
//		delete.setDeleteValidate("delete");
		delete.setPixelSize(450, 294);		
		delete.center();
		delete.show();
	    
	}
	

	@Override
	public void clearAll() {
		paginationFocPanel.clear();
		contentpanel.clear();
		contentpanel.add(setLoadingPanel());
		limit=20;
		pageNumber=1;
	}

	@Override
	public Button getBackToEditPanel() {
		return backToEditPanel;
	}
	
	public static void setPrivatePage()
	{
		btnJoinClass.setVisible(false);
		userImage.setVisible(true);
		lblWebHelp.setVisible(false);
		btnWithDraw.setVisible(false);
		LblMember.setVisible(true);
		LblMember.setText(GL1551);
		mainContainer.setVisible(true);
	}
	public static void setPrivatePageActive()
	{	
	btnJoinClass.setVisible(false);
	userImage.setVisible(true);
	lblWebHelp.setVisible(false);
	btnWithDraw.setVisible(false);
	LblMember.setVisible(true);
	LblMember.setText(GL1549);
	mainContainer.setVisible(true);
	}
	
	public static void setPrivatePagePending()
	{
	btnJoinClass.setVisible(true);
	userImage.setVisible(false);
	lblWebHelp.setVisible(true);
	btnWithDraw.setVisible(false);
	LblMember.setVisible(false);
	LblMember.setText(GL1549);
	mainContainer.setVisible(false);
	
	if(AppClientFactory.isAnonymous()){

		LoginPopupUc loginPopupUc=new LoginPopupUc();
		
		
	}else{

		if(!isJoinPopupPrivate){
			isJoinPopupPrivate=true;
		joinPopupPrivate =  new StudentJoinClassPopup(classpageDo) {
			
			@Override
			void joinIntoClass() {
					String emailId=AppClientFactory.getLoggedInUser().getEmailId();
					AppClientFactory.getInjector().getClasspageService().studentJoinIntoClass(classpageDo.getClasspageCode(),emailId, new SimpleAsyncCallback<ClasspageDo>() {

						@Override
						public void onSuccess(ClasspageDo result) {
							joinPopupPrivate.hide();
							mainContainer.setVisible(true);
							SuccessPopupViewVc success=new SuccessPopupViewVc(){

								@Override
								public void onClickPositiveButton(
										ClickEvent event) {
									Window.enableScrolling(true);
									btnJoinClass.setVisible(false);
									userImage.setVisible(true);
									lblWebHelp.setVisible(false);
									btnWithDraw.setVisible(false);
									LblMember.setVisible(true);
									LblMember.setText(GL1549);
									mainContainer.setVisible(true);
									this.hide();
									isJoinPopupPrivate=false;
									
								}
								
							};
							success.setHeight("248px");
                            success.setWidth("450px");
                            success.setPopupTitle(GL1553);
                            success.setDescText(GL1554+classpageDo.getTitle()+GL_SPL_EXCLAMATION+'\n'+GL1552);
                            success.setPositiveButtonText(GL0190);
                            success.center();
                            success.show();
				
						}
					});
			}

			@Override
			public void closePoup() {
				hide();
				Window.enableScrolling(true);
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
			}
		};
		}
		int windowHeight=Window.getClientHeight()/2; //I subtract 10 from the client height so the window isn't maximized.
		int windowWidth=Window.getClientWidth()/2;
		joinPopupPrivate.setPopupPosition(windowWidth-253, windowHeight-70);
		joinPopupPrivate.setPixelSize(506, 261);		
		//joinPopup.center();
		joinPopupPrivate.show();
	}
	
	}
	
	public static void setPublicPage()
	{
		btnJoinClass.setVisible(false);
		userImage.setVisible(true);
		lblWebHelp.setVisible(false);
		btnWithDraw.setVisible(true);
		LblMember.setVisible(true);
		LblMember.setText(GL1551);
		mainContainer.setVisible(true);
	}
	public static void setPublicPageActive()
	{	
		btnJoinClass.setVisible(false);
		userImage.setVisible(true);
		lblWebHelp.setVisible(false);
		btnWithDraw.setVisible(true);
		LblMember.setVisible(true);
		LblMember.setText(GL1549);
		mainContainer.setVisible(true);
	}
	
	public static void setPublicPagePending()
	{
		btnJoinClass.setVisible(true);
		userImage.setVisible(false);
		lblWebHelp.setVisible(true);
		btnWithDraw.setVisible(false);
		LblMember.setVisible(false);
		LblMember.setText(GL1549);
		mainContainer.setVisible(true);
		

		
	}
	private class OnMouseOver implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanel.clear();
			toolTipPopupPanel.clear();
			toolTipPopupPanel
					.setWidget(new LibraryTopicCollectionToolTip(GL1563,"studyView"));
			toolTipPopupPanel.setStyleName("");
			toolTipPopupPanel.setPopupPosition(lblWebHelp.getElement()
					.getAbsoluteLeft() - 204, lblWebHelp.getElement()
					.getAbsoluteTop() + 19);
			toolTipPopupPanel.show();
		
			
		}
		
	}
	private class OnMouseOut implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			// TODO Auto-generated method stub
			toolTipPopupPanel.hide();
			
		}
		
		
	}
	
	public void openJoinClassEvent(){
System.out.println("checkstatus::"+classpageDo.getStatus());
		if(classpageDo.getSharing().equalsIgnoreCase("public"))
		{
		if(!classpageDo.getCreatorId().equalsIgnoreCase(AppClientFactory.getGooruUid()) && !classpageDo.getStatus().equalsIgnoreCase("active") && classpageDo.getStatus().equalsIgnoreCase("pending"))
		{
		
		joinPopupButtonClick =  new StudentJoinClassPopup(classpageDo) {
		
		@Override
		void joinIntoClass() {
				String emailId=AppClientFactory.getLoggedInUser().getEmailId();
				AppClientFactory.getInjector().getClasspageService().studentJoinIntoClass(classpageDo.getClasspageCode(),emailId, new SimpleAsyncCallback<ClasspageDo>() {

					@Override
					public void onSuccess(ClasspageDo result) {
						joinPopupButtonClick.hide();
						SuccessPopupViewVc success=new SuccessPopupViewVc(){

							@Override
							public void onClickPositiveButton(
									ClickEvent event) {
								Window.enableScrolling(true);
								btnJoinClass.setVisible(false);
								userImage.setVisible(true);
								lblWebHelp.setVisible(false);
								btnWithDraw.setVisible(true);
								LblMember.setVisible(true);
								LblMember.setText(GL1549);
								mainContainer.setVisible(true);
								this.hide();
								
							}
							
						};
						success.setHeight("248px");
                        success.setWidth("450px");
                        success.setPopupTitle(GL1553);
                        success.setDescText(GL1554+classpageDo.getTitle()+GL_SPL_EXCLAMATION+'\n'+GL1552);
                        success.setPositiveButtonText(GL0190);
                        success.center();
                        success.show();
						
					}
				});
		}

		@Override
		public void closePoup() {
			hide();
			Window.enableScrolling(true);
			
		}
	};
	
	joinPopupButtonClick.setPopupPosition(btnJoinClass.getElement().getAbsoluteLeft() - (509), btnJoinClass.getElement().getAbsoluteTop());
	joinPopupButtonClick.setPixelSize(506, 261);		
	//joinPopup.center();
	joinPopupButtonClick.show();
	
	}
	}

	}
	
	public static boolean getMainContainerStatus()
	{
		Boolean mainContainerStatus = false;
		try
		{
		mainContainerStatus = mainContainer.isVisible();		
		}
		catch(Exception ex)
		{
			
		}
		return mainContainerStatus;		
	}
	
}
	

