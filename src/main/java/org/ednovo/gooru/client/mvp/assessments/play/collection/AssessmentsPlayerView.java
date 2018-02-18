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
package org.ednovo.gooru.client.mvp.assessments.play.collection;

import java.util.LinkedHashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePopupViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ContentReportDo;
import org.ednovo.gooru.client.mvp.assessments.play.collection.footer.StudyPlayerFooterView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.header.StudyPlayerHeaderView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.metadata.NavigationConfirmPopup;
import org.ednovo.gooru.client.mvp.assessments.play.resource.body.AssessmentsResourcePlayerMetadataView;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshUserShelfCollectionsEvent;
import org.ednovo.gooru.client.uc.ConfirmationPopupVc;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.uc.tooltip.GlobalTooltipWithButton;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class AssessmentsPlayerView extends BasePopupViewWithHandlers<AssessmentsPlayerUiHandlers> implements IsAssessmentsPlayerView,ClientConstants{

	@UiField FlowPanel pnlFullScreenNarration,playerBodyContainer,navigationContainer,playerContent,menuContent,headerFixedContainer;

	@UiField StudyPlayerHeaderView headerView;

	@UiField StudyPlayerFooterView footerView;

	@UiField HTMLPanel androidSectiondiv;

	@UiField com.google.gwt.user.client.ui.Image closeAndriodBtn;

	@UiField Anchor menuButton;

	@UiField HTMLPanel msgPanel,msglinkPanel;

	@UiField Label lblSeeMore;

	@UiField HTMLEventPanel lblNarrationText,pnlBackToCollection;

	@UiField Image authorImage;

	private PopupPanel appPopUp;

	private boolean isInfoButtonActive=false;

	private boolean isShareButtonActive=false;

	private boolean isNarrationButtonActive=false;

	private boolean isNavigationButtonActive=false;

	private boolean isAddButtonActive=false;

	private boolean  isFlagButtonActive=false;

	private String collectionType=null;

	private static final String STUDY_PLAYER ="studyPlayer";


	GlobalTooltipWithButton globalTooltipWithButton,logOutToolTip;

	CollectionItemDo  collectionItemDo=null;
	CollectionDo collectionDo=null;
	boolean isSeeMoreClicked=false;
	RequestToLoginPopupUc requestToLogin = null;

	SubmitYourAnswersPopupUc submit=null;

	private int userRating=0;
	private HandlerRegistration autoHideHandler;

	private final EventBus eventBus;
	private static AssessmentsPlayerViewUiBinder uiBinder = GWT.create(AssessmentsPlayerViewUiBinder.class);

	interface AssessmentsPlayerViewUiBinder extends UiBinder<Widget, AssessmentsPlayerView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public AssessmentsPlayerView(EventBus eventBus){
		super(eventBus);
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		this.eventBus = eventBus;
		appPopUp=new PopupPanel();
		appPopUp.setGlassEnabled(true);
		appPopUp.setStyleName("study-player-container");
		appPopUp.add(uiBinder.createAndBindUi(this));
		footerView.getShareButton().addClickHandler(new ShowTabWidgetView(SHARE));
		footerView.getInfoButton().addClickHandler(new ShowTabWidgetView(INFO));
		footerView.getAddButton().addClickHandler(new ShowTabWidgetView(ADD));
		footerView.getFlagButton().addClickHandler(new ShowTabWidgetView(FLAG));
		getResourceAnimationContainer().getElement().getStyle().setProperty("display", "none");
		headerView.getCloseButton().addClickHandler(new CloseResourcePlayerEvent());


		pnlFullScreenNarration.setVisible(false);

		pnlBackToCollection.addClickHandler(new ExitFullScreenPlayer());

		headerView.getAuthorContainer().addClickHandler(new ShowLoginPopupEvent());
		headerView.getBtnSubmitAllAnswers().addClickHandler(new ShowPopUp());
		headerView.getBtnSubmitAllAnswers().setVisible(false);
		headerView.getBtnLogin().setVisible(false);
		headerView.getBtnLogin().addClickHandler(new ShowLoginPopupEvent());
		menuButton.addClickHandler(new ShowAuthorContainerEvent());
		menuButton.addTouchStartHandler(new ShowAuthorContainerTouchEvent());
		setAutoHideOnNavigationEventEnabled(true);
		hidePlayerButtons(true,null);
		  Boolean isIpad = !!Navigator.getUserAgent().matches("(.*)iPad(.*)");
		  Boolean isAndriod = !!Navigator.getUserAgent().matches("(.*)Android(.*)");

		  if(isIpad && !StringUtil.IPAD_MESSAGE_Close_Click)
		  {
			 androidSectiondiv.setVisible(false);

		  }
		  else if(isAndriod && !StringUtil.IPAD_MESSAGE_Close_Click)
		  {
			  androidSectiondiv.setVisible(true);
		  }
		  else
		  {
			  androidSectiondiv.setVisible(false);

		  }
		  setUiText();


		  if (AppClientFactory.isAnonymous()){
			  requestToLogin = new RequestToLoginPopupUc();
			  requestToLogin.getBtnLoginAndContinue().addClickHandler(new RequestLoginPopupEvent());
			  requestToLogin.setPopupPosition(0, (Window.getClientHeight()-428)/2);
			  requestToLogin.show();
		  }



	}

	private class RequestLoginPopupEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			if(AppClientFactory.isAnonymous() && requestToLogin != null){
				requestToLogin.hide();
				getUiHandlers().showLoginPopupWidget(AssessmentsPlayerPresenter.UPDATE_HEADER);
			}
		}
	}





	@UiHandler("closeAndriodBtn")
	public void onAndriodCloseClick(ClickEvent clickEvent){
		  androidSectiondiv.setVisible(false);
		  StringUtil.IPAD_MESSAGE_Close_Click = true;
		  AssessmentsResourcePlayerMetadataView.onClosingAndriodorIpaddiv();
	}

	 @Override
	  public void setAutoHideOnNavigationEventEnabled(boolean autoHide) {
	    if (autoHide) {
	      if (autoHideHandler != null) {
	        return;
	      }
	      autoHideHandler = eventBus.addHandler(NavigationEvent.getType(), new NavigationHandler() {
	            @Override
	            public void onNavigation(NavigationEvent navigationEvent) {
	              if(!AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.ASSESSMENT_PLAY)){
	            	 if(!AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.SHELF)){
	            		 closePreviewPlayer();
	            		 hideFromPopup(false);
	            	 }
	              }
	            }
	          });
	    } else {
	      if (autoHideHandler != null) {
	        autoHideHandler.removeHandler();
	      }
	    }
	  }

	@Override
	public FlowPanel getPlayerBodyContainer() {
		return playerBodyContainer;
	}

	public FlowPanel getHeaderFixedContainer(){
		return headerFixedContainer;
	}

	public FlowPanel getNavigationContainer(){
		return navigationContainer;
	}

	public FlowPanel getResourceAnimationContainer() {
		return footerView.getResourceAnimationContainer();
	}

	@Override
	public void setResourceTitle(String resourceTitle) {
		headerView.setResourceTitle(resourceTitle);
		String view=AppClientFactory.getPlaceManager().getRequestParameter("view", null);
		String resourceId=AppClientFactory.getPlaceManager().getRequestParameter("rid", null);
		if((view!=null&&view.equalsIgnoreCase("end")) || (resourceId==null)){
			appPopUp.addStyleName("scrollStudyContainer");
		}
	}

	public void setNarrationButton(Button narrationButton){
		footerView.setNarrationButton(narrationButton);
		footerView.getNarrationButton().addClickHandler(new ShowTabWidgetView(NARRATION));
	}

	public void setFullScreenButton(Button fullScreenButton){
		footerView.setFullScreenButton(fullScreenButton);
		footerView.getFullScreenButton().addClickHandler(new FullScreenPlayerEvent("fullScreen"));
	}

	@Override
	public void enablePlayerButton(boolean isAddButtonEnable,boolean isInfoButtonEnable,
			boolean isShareButtonEnable, boolean isNarrationButtonEnable,
			boolean isNavigationButtonEnable, boolean isFlagButtonActive, boolean isFullScreenButtonActive) {
		footerView.enableButtons(isAddButtonEnable,isInfoButtonEnable, isShareButtonEnable, isNarrationButtonEnable, isNavigationButtonEnable,isFlagButtonActive,isFullScreenButtonActive);
	}

	public class ShowTabWidgetView implements ClickHandler{
		String tabView="";
		public ShowTabWidgetView(String tabView){
			this.tabView=tabView;
		}
		@Override
		public void onClick(ClickEvent event) {
			if(ADD.equalsIgnoreCase(tabView)){
				MixpanelUtil.clickInfo(STUDY_PLAYER);
				setTabPlaceRequest(tabView,footerView.isAddButtonEnabled(),isAddButtonActive);
			}
			else if(INFO.equalsIgnoreCase(tabView)){
				MixpanelUtil.clickInfo(STUDY_PLAYER);
				MixpanelUtil.OpenInfo();
				setTabPlaceRequest(tabView,footerView.isInfoButtonEnabled(),isInfoButtonActive);
			}else if(SHARE.equalsIgnoreCase(tabView)){
				MixpanelUtil.clickShareCollection(STUDY_PLAYER);
				MixpanelUtil.OpenShare();
				setTabPlaceRequest(tabView,footerView.isShareButtonEnabled(),isShareButtonActive);
			}else if(NAVIGATION.equalsIgnoreCase(tabView)){
				MixpanelUtil.clickNavigation(STUDY_PLAYER);
				setTabPlaceRequest(tabView,footerView.isNavigationButtonEnabled(),isNavigationButtonActive);
			}else if(NARRATION.equalsIgnoreCase(tabView)){
				MixpanelUtil.ClickNarration(STUDY_PLAYER);
				setTabPlaceRequest(tabView,footerView.isNarrationButtonEnabled(),isNarrationButtonActive);
			}
			else if(FLAG.equalsIgnoreCase(tabView)){
				setTabPlaceRequest(tabView,true,false);
			}
		}
	}
	public void setTabPlaceRequest(String tabView, boolean isButtonEnable,boolean isButtonActive){
		if(isButtonEnable){
			String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			String resourceId=AppClientFactory.getPlaceManager().getRequestParameter("rid", null);
			String view=AppClientFactory.getPlaceManager().getRequestParameter("view", null);

			Map<String,String> params = new LinkedHashMap<String,String>();
			params.put("id", collectionId);
			params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);
			if(resourceId!=null&&!resourceId.equalsIgnoreCase("")){
				appPopUp.removeStyleName("scrollStudyContainer");
				if(isButtonActive){
					params.put("rid", resourceId);
					PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
					AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
				}else{
					params.put("rid", resourceId);
					params.put("tab", tabView);
					PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
					AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);

				}
			}else if(view!=null&&view.equalsIgnoreCase("end")){
				appPopUp.addStyleName("scrollStudyContainer");
				if(isButtonActive){
					params.put("view", "end");
					PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
					AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
				}else{
					params.put("tab", tabView);
					params.put("view", "end");
					PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
					AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
				}
			}else{
				appPopUp.addStyleName("scrollStudyContainer");
				if(isButtonActive){
					PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
					AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);

				}else{
					params.put("tab", tabView);
					PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
					AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
				}
			}
		}

	}

	@Override
    public void setInSlot(Object slot, Widget content) {
		if(slot!=null){
			if(slot==AssessmentsPlayerPresenter.COLLECTION_PLAYER_TOC_PRESENTER_SLOT){
				getResourceAnimationContainer().clear();
				getResourceAnimationContainer().setVisible(false);
				if(content!=null){
					getResourceAnimationContainer().add(content);
				}
			}else if(slot==AssessmentsPlayerPresenter.COLLECTION_PLAYER_NAVIGATION_SLOT){
				navigationContainer.clear();
				navigationContainer.setVisible(true);
				if(content!=null){
					navigationContainer.add(content);
				}
			}else if(slot==AssessmentsPlayerPresenter.METADATA_PRESENTER_SLOT){
				getPlayerBodyContainer().clear();
				if(content!=null){
					getPlayerBodyContainer().add(content);
				}
			}
		}
	}

	@Override
	public void makeButtonActive(boolean makeAddButtionActive,boolean makeInfoButtionActive,boolean makeShareButtonActive, boolean makeNarrationButtonActive,
			boolean makeNavigationButtonActive,boolean makeFlagButtonActive,boolean makeFullScreenButtonActive) {
		footerView.makeButtonActive(makeAddButtionActive,makeInfoButtionActive, makeShareButtonActive, makeNarrationButtonActive, makeNavigationButtonActive,makeFlagButtonActive,makeFullScreenButtonActive);
		setActiveButton(makeAddButtionActive,makeInfoButtionActive, makeShareButtonActive, makeNarrationButtonActive, makeNavigationButtonActive,makeFlagButtonActive);
		if(makeNavigationButtonActive || makeInfoButtionActive || makeShareButtonActive)
		{
			if(!AppClientFactory.isAnonymous() && makeAddButtionActive){
				AssessmentsResourcePlayerMetadataView.removePadding();

			}
		}
	}

	@Override
	public void clearActiveButton(boolean deselectAddButton,boolean deselectInfoButton,boolean deselectShareButtion,boolean deselectNarrationButton,boolean deselectNavigationButton,boolean deselectFlagButton,boolean deselectFullScreenButton) {
		footerView.clearActiveButton(deselectAddButton,deselectInfoButton, deselectShareButtion, deselectNarrationButton, deselectNavigationButton,deselectFlagButton,deselectFullScreenButton);
		setActiveButton(false,false,false,false,false,false);

	}
	public void setActiveButton(boolean makeAddButtionActive,boolean makeInfoButtionActive,
			boolean makeShareButtonActive,boolean makeNarrationButtonActive,boolean makeNavigationButtonActive,boolean makeFlagButtonActive){
		this.isInfoButtonActive=makeInfoButtionActive;
		this.isShareButtonActive=makeShareButtonActive;
		this.isNarrationButtonActive=makeNarrationButtonActive;
		this.isNavigationButtonActive=makeNavigationButtonActive;
		this.isAddButtonActive=makeAddButtionActive;
		this.isFlagButtonActive=makeFlagButtonActive;
	}

	public class CloseResourcePlayerEvent implements ClickHandler{
		public void onClick(ClickEvent event) {
			if(!getUiHandlers().isOpenEndedAnswerSubmited()){
				new NavigationConfirmPopup() {
					@Override
					public void navigateToNextResource() {
						super.hide();
						closePlayer();
					}
				};
			}else{
				closePlayer();
			}

		}
	}
	/**
	 * This inner class is used to handle the click event for exit full screen mode
	 * @author Gooru
	 */
	public class ExitFullScreenPlayer implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			String resourceId=AppClientFactory.getPlaceManager().getRequestParameter("rid", null);
			Map<String,String> params = new LinkedHashMap<String,String>();
			params.put("id", collectionId);
			if(resourceId!=null&&!resourceId.equalsIgnoreCase("")){
				params.put("rid", resourceId);
			}
			params.put("view", null);
			params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
			restFullScreenChanges();
		}
	}
	/**
	 * This inner class is used to handle the click event on Full screen button.
	 * @author Gooru
	 */
	public class FullScreenPlayerEvent implements ClickHandler{
		String tabView="";
		public FullScreenPlayerEvent(String tabView){
			this.tabView=tabView;
		}
		@Override
		public void onClick(ClickEvent event) {
			String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			String resourceId=AppClientFactory.getPlaceManager().getRequestParameter("rid", null);
			Map<String,String> params = new LinkedHashMap<String,String>();
			params.put("id", collectionId);
			if(resourceId!=null&&!resourceId.equalsIgnoreCase("")){
				params.put("rid", resourceId);
			}
			params.put("view", tabView);
			params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
			setFullScreenMode();
		}
	}
	/**
	 * This method is used to set full screen mode
	 */
	@Override
	public void setFullScreenMode(){
		menuButton.getElement().setAttribute("style", "display:none !important;");
		pnlFullScreenNarration.setVisible(true);
		headerView.setVisible(false);
		footerView.setVisible(false);
		navigationContainer.setVisible(false);
		getUiHandlers().setFullScreenMode(true,pnlFullScreenNarration);

	}
	/**
	 * This method is used to reset the full screen changes.
	 */
	@Override
	public void restFullScreenChanges(){
		menuButton.getElement().removeAttribute("style");
		pnlFullScreenNarration.setVisible(false);
		headerView.setVisible(true);
		footerView.setVisible(true);
		navigationContainer.setVisible(true);
		getUiHandlers().setFullScreenMode(false,pnlFullScreenNarration);
	}

	public void closePlayer(){
		String page=AppClientFactory.getPlaceManager().getRequestParameter("page",null);
		if(page!=null){
			if(TEACH.equals(page)){
				getUiHandlers().revealTeachOrStudypage(page);
			}else if(STUDY.equals(page)){
				getUiHandlers().revealTeachOrStudypage(page);
			}else{
				hideFromPopup(true);
				closePreviewPlayer();
			}
		}else{
			hideFromPopup(true);
			closePreviewPlayer();
		}
	}

	@Override
	public void showClasspage() {
		hideFromPopup(true);
		closePreviewPlayer();
	}

	public void showClasspage(String classpageId,String page){
		String viewToken=AppClientFactory.getPlaceManager().getPreviousPlayerRequestUrl().getNameToken();
		if(PlaceTokens.DISCOVER.equals(viewToken)){
			Map<String,String> paramsMap=new LinkedHashMap<String,String>();
			if(TEACH.equals(page)){
				paramsMap.put("classpageid", classpageId);
				revealToClassPage(PlaceTokens.EDIT_CLASSPAGE,paramsMap);
			}else if(STUDY.equals(page)){
				paramsMap.put("id", classpageId);
				revealToClassPage(PlaceTokens.STUDENT,paramsMap);
			}
		}else{
			hideFromPopup(true);
			closePreviewPlayer();
		}
	}


	public void revealToClassPage(String placeToken,Map<String,String> paramsMap){
		paramsMap.put("pageNum", "0");
		paramsMap.put("pageSize", "10");
		paramsMap.put("pos", "1");
		PlaceRequest placeRequest=new PlaceRequest(placeToken);
		AppClientFactory.getPlaceManager().revealPlace(placeRequest, paramsMap);
	}

	@Override
	public void closePreviewPlayer() {
		AppClientFactory.fireEvent(new RefreshUserShelfCollectionsEvent());
		String rid = AppClientFactory.getPlaceManager().getRequestParameter("rid", null);
		String view = AppClientFactory.getPlaceManager().getRequestParameter("view", null);
		if (rid != null && view == null){
			getUiHandlers().setLastEventType(PlayerDataLogEvents.PAUSE_EVENT_TYPE);
		}else{
			getUiHandlers().setLastEventType(PlayerDataLogEvents.STOP_EVENT_TYPE);
		}

		hide();
		getUiHandlers().resetCollectionPlayer();
	}

	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	@Override
	protected String getDefaultView() {
		return PlaceTokens.HOME;
	}
	@Override
	public void updateThumbsRatingView(int userThumbRating) {
	}

	public int getUserRating() {
		return userRating;
	}

	public void setUserRating(int userRating) {
		this.userRating = userRating;
	}

	public void resetThumbsButtons(){
	}

	@Override
	public void defaultReportView() {
		footerView.getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrange());
		footerView.getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonActive());
		footerView.getFlagButton().setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonDisable());

	}

	@Override
	public void flaggedReportView(ContentReportDo contentReportDo, String FlagId) {
		footerView.getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonDisable());
		footerView.getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonActive());
		footerView.getFlagButton().setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrange());

	}

	@Override
	public void hideFlagButton(boolean hideButton) {
	}
	@Override
	public void makeFlagButtonOrange() {
		footerView.makeFlagButtonOrange();
	}

	/**
	 * To show the Flagged resource info
	 */
	@Override
	public void showFlaggedResourcePopup(PlaceRequest previous, PlaceRequest next) {
		ConfirmationPopupVc confiPopupVc = new ConfirmationPopupVc(i18n.GL2190(), i18n.GL2191()) {

			@Override
			@UiHandler("okButton")
			public void onDelete(ClickEvent clickEvent) {
				hide();
				getUiHandlers().navigateToNext(NEXT);
			}
			@Override
			@UiHandler("cancelButton")
			public void onCancelClick(ClickEvent clickEvent){
				hide();
				getUiHandlers().navigateToNext(PREVIOUS);
			}
		};
		String hasPrevious=previous.getParameter("rid", null);
		String hasNext=next.getParameter("view", null);
		if(hasPrevious==null){
			confiPopupVc.getCancelButton().setVisible(false);
		}else{
			confiPopupVc.getCancelButton().setVisible(true);
		}
		confiPopupVc.setPopupZindex(99999);
		confiPopupVc.setGlassZindex(99999);
		confiPopupVc.setAndHideButtonInPlayer(i18n.GL2192(),i18n.GL2204());
		if(hasNext!=null){
			confiPopupVc.getOkButton().setText(i18n.GL2205());
		}
	}

	/**
	 * This method is used to show first time Add tooltip popup.
	 */
	@Override
	public void showAddToolTip() {
	}

	public void showLogoutMessage(boolean hide) {
		String resourcePlayerFirstTimeUser = Cookies.getCookie("lp");
		if(hide){
			if(logOutToolTip!=null){
				logOutToolTip.hide();
			}
		}else {
			if(AppClientFactory.isAnonymous()){
				if(resourcePlayerFirstTimeUser==null){
					logOutToolTip=new GlobalTooltipWithButton(i18n.GL1614(),i18n.GL1615(), i18n.GL0543());
					logOutToolTip.getCloseButton().addClickHandler(new StoreCookieHandler());
					logOutToolTip.setGlassStyleName("playerAddToolTipGlassStyle");
					logOutToolTip.setStyleName("");
					logOutToolTip.getElement().getStyle().setZIndex(999999);
					logOutToolTip.getElement().getStyle().clearLeft();
					logOutToolTip.getElement().getStyle().setFloat(Float.RIGHT);
					logOutToolTip.getElement().getStyle().setRight(23, Unit.PCT);
					logOutToolTip.getElement().getStyle().setTop(15, Unit.PX);
					logOutToolTip.show();
				}
			}
		}
	}
	private class StoreCookieHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			if(logOutToolTip!=null){
				Cookies.setCookie("lp", "1");
			}
		}
	}

	private class ShowPopUp implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			submit = new SubmitYourAnswersPopupUc() {

				@Override
				public void onClickSubmit(ClickEvent event) {
					submit.hide();
					Map<String, String> parms = StringUtil.splitQuery(Window.Location.getHref());
					parms.put("view", "end");
					parms.remove("rid");
					AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken(), parms);
				}
			};
			submit.center();
			submit.show();
		}
	}


	private class ShowLoginPopupEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			if(AppClientFactory.isAnonymous()){
				getUiHandlers().showLoginPopupWidget(AssessmentsPlayerPresenter.UPDATE_HEADER);
			}
		}
	}

	@Override
	public void hidePlayerButtons(boolean isHidePlayerButtons,String collectionId) {

		String resourceId=AppClientFactory.getPlaceManager().getRequestParameter("rid", null);
		if(!StringUtil.isEmpty(collectionId)){
			String view=AppClientFactory.getPlaceManager().getRequestParameter("view",null);
			if(view!=null&&view.equalsIgnoreCase("end")){
				headerView.getAuthorContainer().setVisible(!isHidePlayerButtons);
				headerView.getBtnSubmitAllAnswers().setVisible(false);
				headerView.getBtnLogin().setVisible(false);
			}else{
				headerView.getAuthorContainer().setVisible(isHidePlayerButtons);
				String rid= AppClientFactory.getPlaceManager().getRequestParameter("rid",null);
				if(rid!=null){
					if(AppClientFactory.isAnonymous()) {
						headerView.getBtnSubmitAllAnswers().setVisible(false);
						headerView.getBtnLogin().setVisible(true);
					} else {
						headerView.getBtnSubmitAllAnswers().setVisible(true);
						headerView.getBtnLogin().setVisible(false);
					}
				}else{
					headerView.getBtnSubmitAllAnswers().setVisible(false);
					headerView.getBtnLogin().setVisible(false);
				}
			}
			//headerView.getFlagButton().setVisible(isHidePlayerButtons);
			footerView.setVisible(!isHidePlayerButtons);
		}else{
			String view=AppClientFactory.getPlaceManager().getRequestParameter("view",null);
			if(view!=null&&view.equalsIgnoreCase("end")){
				appPopUp.addStyleName("scrollStudyContainer");
				headerView.getAuthorContainer().setVisible(!isHidePlayerButtons);
				headerView.getBtnSubmitAllAnswers().setVisible(false);
			}
			else if(view!=null&&view.equalsIgnoreCase("fullScreen"))
			{
				appPopUp.removeStyleName("scrollStudyContainer");
				headerView.getAuthorContainer().setVisible(isHidePlayerButtons);
				headerView.getBtnSubmitAllAnswers().setVisible(false);
				headerView.displayAuthorName(getCollectionType());
				showLogoutMessage(!isHidePlayerButtons);
			}
			else{
				if(view==null && resourceId != null)
				{
					appPopUp.removeStyleName("scrollStudyContainer");
				}
				else
				{
					appPopUp.addStyleName("scrollStudyContainer");
				}
				headerView.getAuthorContainer().setVisible(isHidePlayerButtons);
				headerView.getBtnSubmitAllAnswers().setVisible(false);
				headerView.displayAuthorName(getCollectionType());
				showLogoutMessage(!isHidePlayerButtons);
			}
			footerView.setVisible(!isHidePlayerButtons);
		}
	}

	public void updateAuthorDetails(){
		headerView.displayAuthorName(getCollectionType());
	}

	public void hideAuthorInHeader(boolean showOrHide){
		headerView.getAuthorContainer().setVisible(showOrHide);
		headerView.getBtnSubmitAllAnswers().setVisible(!showOrHide);
	}

	public void scrollStudyPage(){
		appPopUp.getElement().setScrollTop(300);
	}
	public void setUiText()
	{
		  menuContent.getElement().setId("menu");
		  playerContent.getElement().setId("page");
		  menuButton.getElement().setId("toggle-menu");
		  androidSectiondiv.getElement().setId("pnlAndroidSectiondiv");
		  closeAndriodBtn.getElement().setId("imgCloseAndriodBtn");
		  headerView.getElement().setId("studnetPlayerHeaderHeaderView");
		  navigationContainer.getElement().setId("fpnlNavigationContainer");
		  playerBodyContainer.getElement().setId("fpnlPlayerBodyContainer");

		  msglinkPanel.getElement().setInnerHTML(i18n.GL1984());
		  msglinkPanel.getElement().setId("pnlMsglinkPanel");
		  msglinkPanel.getElement().setAttribute("alt",i18n.GL1984());
		  msglinkPanel.getElement().setAttribute("title",i18n.GL1984());

		  msgPanel.getElement().setInnerHTML(i18n.GL1983());
		  msgPanel.getElement().setId("pnlMsgPanel");
		  msgPanel.getElement().setAttribute("alt",i18n.GL1983());
		  msgPanel.getElement().setAttribute("title",i18n.GL1983());
	}


	public void addClonedMenuContent(FlowPanel rightPanelElement){
		menuContent.clear();
		if(rightPanelElement!=null){
			menuContent.add(rightPanelElement);
		}
	}

	private class ShowAuthorContainerEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			invokeShowHideMenuContainer();
		}
	}
	private class ShowAuthorContainerTouchEvent implements TouchStartHandler{
		@Override
		public void onTouchStart(TouchStartEvent event) {
			invokeShowHideMenuContainer();
		}
	}

	public static native void invokeShowHideMenuContainer() /*-{
    	$wnd.showAuthorContianer();
	}-*/;

	public FlowPanel menuContent(){
		return menuContent;
	}

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}


    /**
     * This event will handle the click event on the see more for narration.
     * @param event
     */
	@UiHandler("lblSeeMore")
	public void clickOnSeeMoreBtn(ClickEvent event){
		if(collectionItemDo!=null && collectionItemDo.getNarration()!=null){
			if(!isSeeMoreClicked){
				String narrationText=collectionItemDo.getNarration();
				narrationText=StringUtil.replaceAnchoreWithTarget(narrationText);
				lblSeeMore.setText(i18n.GL0509());
				lblNarrationText.getElement().setInnerHTML(narrationText);
				isSeeMoreClicked=true;
			}else{
				setNarrationInFullScreenMode(collectionItemDo,collectionDo);
				isSeeMoreClicked=false;
			}
		}else{
			lblNarrationText.getElement().setInnerHTML("");
		}
		setSeemoreBackGround();
	}
	/**
	 * This method will set the background image up and arrows
	 */
	void setSeemoreBackGround(){
		if(isSeeMoreClicked){
			lblSeeMore.addStyleName("seelessText");
		}else{
			lblSeeMore.removeStyleName("seelessText");
		}
	}
	private void setUserProfileImage(String profileUserId) {
		authorImage.setUrl(AppClientFactory.loggedInUser.getSettings().getProfileImageUrl()+profileUserId+".png");
	}
	@UiHandler("authorImage")
	public void setDefaultProfileImage(ErrorEvent event){
		authorImage.setUrl("images/settings/setting-user-image.png");
	}
	/**
	 * This method will set the narration text.
	 */
	@Override
	public void setNarrationInFullScreenMode(CollectionItemDo collectionItemDo,CollectionDo collectionDo) {
		if(collectionItemDo!=null && collectionItemDo.getNarration()!=null){
			authorImage.setVisible(true);
			lblNarrationText.setVisible(true);
			setUserProfileImage(collectionDo.getUser().getGooruUId());
			String narrationText=collectionItemDo.getNarration();
			narrationText=StringUtil.replaceAnchoreWithTarget(narrationText);
			this.collectionItemDo=collectionItemDo;
			this.collectionDo=collectionDo;
			if(narrationText.length()>0 && narrationText.length()>240){
				lblNarrationText.getElement().setInnerHTML(narrationText.substring(0, 240)+"...");
				lblSeeMore.setVisible(true);
				lblSeeMore.setText(i18n.GL0508());
			}else{
				lblNarrationText.getElement().setInnerHTML(narrationText);
				lblSeeMore.setVisible(false);
			}
		}else{
			authorImage.setVisible(false);
			lblNarrationText.setVisible(false);
			lblNarrationText.getElement().setInnerHTML("");
			lblSeeMore.setVisible(false);
		}
	}
	@Override
	public void removeStudentViewButton() {

	}


}
