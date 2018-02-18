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
package org.ednovo.gooru.client.mvp.assessments.play.resource;


import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePopupViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.assessments.play.collection.header.ResourcePlayerHeaderView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.metadata.NavigationConfirmPopup;
import org.ednovo.gooru.client.mvp.assessments.play.resource.body.AssessmentsResourcePlayerMetadataView;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.uc.tooltip.GlobalTooltipWithButton;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class AssessmentsResourcePlayerView extends BasePopupViewWithHandlers<AssessmentsResourcePlayerUiHandlers> implements IsAssessmentsResourcePlayerView{


	@UiField FlowPanel playerBodyContainer,navigationContainer;

	@UiField HTMLPanel androidSectiondiv;

	@UiField com.google.gwt.user.client.ui.Image closeAndriodBtn;

	@UiField ResourcePlayerHeaderView headerView;

	@UiField HTMLPanel msgPanel,msglinkPanel;

	private PopupPanel appPopUp;
	private boolean isInfoButtonActive=false;
	private boolean isShareButtonActive=false;
	private boolean isAddButtonActive=false;
	private boolean isFlagButtonActive=false;
	private int userRating=0;
	private static final String RESOURCE_PLAYER="resourceplayer";
	GlobalTooltipWithButton globalTooltipWithButton;
	private HandlerRegistration autoHideHandler;
	private final EventBus eventBus;

	private static AssessmentsPlayerViewUiBinder uiBinder = GWT.create(AssessmentsPlayerViewUiBinder.class);

	interface AssessmentsPlayerViewUiBinder extends UiBinder<Widget, AssessmentsResourcePlayerView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public AssessmentsResourcePlayerView(EventBus eventBus){
		super(eventBus);
		this.eventBus = eventBus;
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		appPopUp=new PopupPanel();
		appPopUp.setGlassEnabled(true);
		appPopUp.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().setPlayerContainer());
		appPopUp.add(uiBinder.createAndBindUi(this));
		headerView.getShareButton().addClickHandler(new ShowTabWidgetView("share"));
		headerView.getInfoButton().addClickHandler(new ShowTabWidgetView("info"));
		headerView.getAddButton().addClickHandler(new ShowTabWidgetView("add"));
		getNavigationContainer().getElement().getStyle().setProperty("display", "none");
		headerView.getCloseButton().addClickHandler(new CloseResourcePlayerEvent());
		headerView.getFlagButton().addClickHandler(new ShowTabWidgetView("flag"));


		setAutoHideOnNavigationEventEnabled(true);


		  Boolean isIpad = !!Navigator.getUserAgent().matches("(.*)iPad(.*)");
		  Boolean isAndriod = !!Navigator.getUserAgent().matches("(.*)Android(.*)");
		  Boolean isWinDskp = !!Navigator.getUserAgent().matches("(.*)NT(.*)");

		  if(isIpad && !StringUtil.IPAD_MESSAGE_Close_Click)
		  {
			  headerView.getElement().setAttribute("style", "position:relative;");
			 androidSectiondiv.setVisible(false);

		  }
		  else if(isAndriod && !StringUtil.IPAD_MESSAGE_Close_Click)
		  {
			  headerView.getElement().setAttribute("style", "position:relative;");
			  androidSectiondiv.setVisible(true);
		  }
		  else
		  {
			  androidSectiondiv.setVisible(false);
			  headerView.getElement().setAttribute("style", "position:fixed;");

		  }
		  setUiText();
	}

	  @Override
	  public void setAutoHideOnNavigationEventEnabled(boolean autoHide) {
	    if (autoHide) {
	      if (autoHideHandler != null) {
	        return;
	      }
	      autoHideHandler = eventBus.addHandler(NavigationEvent.getType(),
	          new NavigationHandler() {
	            @Override
	            public void onNavigation(NavigationEvent navigationEvent) {
	             if(!AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.RESOURCE_PLAY)){
	            	 hideFromPopup(false);
	            	 resetPlayer();
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
	public FlowPanel getNavigationContainer() {
		return navigationContainer;
	}
	@Override
	public void setResourceTitle(String resourceTitle) {
		headerView.setResourceTitle(resourceTitle);
	}

	@Override
	public void enablePlayerButton(boolean isAddButtonEnable,boolean isInfoButtonEnable,
			boolean isShareButtonEnable,boolean isFlagButtonEnable) {
		headerView.enableButtons(isAddButtonEnable,isInfoButtonEnable, isShareButtonEnable,isFlagButtonEnable);
	}

	public class ShowTabWidgetView implements ClickHandler{
		String tabView="";
		public ShowTabWidgetView(String tabView){
			this.tabView=tabView;
		}
		@Override
		public void onClick(ClickEvent event) {
			if(tabView.equalsIgnoreCase("info")){
				MixpanelUtil.clickInfo(RESOURCE_PLAYER);
				setTabPlaceRequest(tabView,headerView.isInfoButtonEnabled(),isInfoButtonActive);
			}else if(tabView.equalsIgnoreCase("share")){
				MixpanelUtil.clickShareResource(RESOURCE_PLAYER);
				setTabPlaceRequest(tabView,headerView.isShareButtonEnabled(),isShareButtonActive);
			}
			else if(tabView.equalsIgnoreCase("add")){
				setTabPlaceRequest(tabView,headerView.isAddButtonEnabled(),isAddButtonActive);
			}
			else if(tabView.equalsIgnoreCase("flag")){
				setTabPlaceRequest(tabView,true,isFlagButtonActive);
			}
		}

	}

	public class CloseResourcePlayerEvent implements ClickHandler{
		public void onClick(ClickEvent event) {

			if (AppClientFactory.getPlaceManager().getPreviousRequest()!=null){
				if(AppClientFactory.getPlaceManager().getPreviousRequest().getNameToken().equalsIgnoreCase("resource-search") || AppClientFactory.getPlaceManager().getPreviousRequest().getNameToken().equalsIgnoreCase("collection-search")){
					Window.enableScrolling(false);
				}
			}

			if(!getUiHandlers().isOpenEndedAnswerSubmited()){
				new NavigationConfirmPopup() {
					@Override
					public void navigateToNextResource() {
						super.hide();
						hideFromPopup(true);
						resetPlayer();
					}
				};
			}else{
				hideFromPopup(true);
				resetPlayer();
			}

		}
	}

	public void resetPlayer(){
		hide();
		resetResourcePlayer();
	}

	public void setTabPlaceRequest(String tabView, boolean isButtonEnable,boolean  isButtonActive){
		if(isButtonEnable){
			String resourceId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			 if(isButtonActive){
				PlaceRequest request=new PlaceRequest(PlaceTokens.RESOURCE_PLAY).with("id", resourceId);
				AppClientFactory.getPlaceManager().revealPlace(false,request,true);
			 }else{
				    PlaceRequest request=new PlaceRequest(PlaceTokens.RESOURCE_PLAY).with("id", resourceId).with("tab", tabView);
				    boolean refreshPlace=tabView.equalsIgnoreCase("add")?true:false;
					AppClientFactory.getPlaceManager().revealPlace(false,request,true);

			 }
		}

	}

	@Override
    public void setInSlot(Object slot, Widget content) {
		if(slot!=null){
			if(slot==AssessmentsResourcePlayerPresenter.TAB_PRESENTER_SLOT){
				getNavigationContainer().clear();
				getNavigationContainer().setVisible(false);
				if(content!=null){
					getNavigationContainer().add(content);
				}
			}else if(slot==AssessmentsResourcePlayerPresenter.METADATA_PRESENTER_SLOT){
				getPlayerBodyContainer().clear();
				if(content!=null){
				getPlayerBodyContainer().add(content);
				}
			}
		}
	}

	@Override
	public void makeButtonActive(boolean makeAddButtonActive,boolean makeInfoButtionActive,
			boolean makeShareButtonActive,boolean makeFlagButtonActive) {
		headerView.makeButtonActive(makeAddButtonActive,makeInfoButtionActive, makeShareButtonActive,makeFlagButtonActive);
		setActiveButton(makeAddButtonActive, makeInfoButtionActive,makeShareButtonActive,makeFlagButtonActive);
		if(makeInfoButtionActive || makeShareButtonActive)
		{

		}
		if(!AppClientFactory.isAnonymous() && makeAddButtonActive){

		}
	}

	@Override
	public void clearActiveButton(boolean deselectAddButton,boolean deselectInfoButton,boolean deselectShareButtion,boolean deselectFlagButton) {
		headerView.clearActiveButton(deselectAddButton,deselectInfoButton, deselectShareButtion,deselectFlagButton);
		setActiveButton(false,false,false,false);
	}

	public void setActiveButton(boolean makeAddButtonActive,boolean makeInfoButtionActive,
			boolean makeShareButtonActive,boolean makeFlagButtonActive){
		this.isAddButtonActive=makeAddButtonActive;
		this.isInfoButtonActive=makeInfoButtionActive;
		this.isShareButtonActive=makeShareButtonActive;
		this.isFlagButtonActive=makeFlagButtonActive;
	}

	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	@Override
	protected String getDefaultView() {
		return PlaceTokens.HOME;
	}

	public void resetResourcePlayer(){
		getUiHandlers().resetResourcePlayer();
	}

	@Override
	public void updateThumbsRatingView(int userThumbRating) {}

	public int getUserRating() {
		return userRating;
	}

	public void setUserRating(int userRating) {
		this.userRating = userRating;
	}


	@UiHandler("closeAndriodBtn")
	public void onAndriodCloseClick(ClickEvent clickEvent){
		  androidSectiondiv.setVisible(false);
		  headerView.getElement().setAttribute("style", "position:fixed;");
		  StringUtil.IPAD_MESSAGE_Close_Click = true;
		 setMarginTopToNav();
		  //CollectionPlayerMetadataView.onClosingAndriodorIpaddiv();
		  AssessmentsResourcePlayerMetadataView.onClosingAndriodorIpaddiv();
	}

	public void setMarginTopToNav(){
		 int count=navigationContainer.getWidgetCount();
		  if(count>0){
			  getNavigationContainer().getWidget(0).getElement().getStyle().setMarginTop(50, Unit.PX);
		  }
	}

	@Override
	public Button getFlagButton() {
		return headerView.getFlagButton();
	}

	@Override
	public void makeFlagButtonOrange() {
		headerView.makeFlagButtonOrange();
	}
	public void setUiText()
	{
		  androidSectiondiv.getElement().setId("pnlAndroidSectiondiv");
		  closeAndriodBtn.getElement().setId("imgCloseAndriodBtn");
		  headerView.getElement().setId("collectionPlayerHeaderView");
		  navigationContainer.getElement().setId("fpnlNavigationContainer");
		  playerBodyContainer.getElement().setId("fpnlPlayerBodyContainer");

		  msgPanel.getElement().setInnerHTML(i18n.GL1983());
		  msgPanel.getElement().setId("pnlMsgPanel");
		  msgPanel.getElement().setAttribute("alt",i18n.GL1983());
		  msgPanel.getElement().setAttribute("title",i18n.GL1983());

		  msglinkPanel.getElement().setInnerHTML(i18n.GL1984());
		  msglinkPanel.getElement().setId("pnlMsglinkPanel");
		  msglinkPanel.getElement().setAttribute("alt",i18n.GL1984());
		  msglinkPanel.getElement().setAttribute("title",i18n.GL1984());
	}
}
