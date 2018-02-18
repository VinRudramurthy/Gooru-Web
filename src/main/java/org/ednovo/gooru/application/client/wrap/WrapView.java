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
package org.ednovo.gooru.application.client.wrap;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseView;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.mvp.home.HeaderUc;
import org.ednovo.gooru.client.mvp.home.event.HeaderTabType;
import org.ednovo.gooru.client.uc.BrowserAgent;
import org.ednovo.gooru.client.uc.DeviceUc;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class WrapView extends BaseView implements IsWrapView {

	private static WrapViewUiBinder uiBinder = GWT.create(WrapViewUiBinder.class);

	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface WrapViewUiBinder extends UiBinder<Widget, WrapView> {
	}

	@UiField
	SimplePanel wrapperPanel;

	@UiField
	HeaderUc headerUc;

	@UiField HTMLPanel panelWrapper, panelDevice,searchPush,menuRight, resorceSearchFilters, collectionSearchFilters,wrapBodyPanel;

	private boolean isArrowIcon = false;

	Boolean isIpad,isAndriod,isWinDskp;
	/**
	 * Class constructor
	 */
	public WrapView() {
		setWidget(uiBinder.createAndBindUi(this));

		panelWrapper.getElement().setId("wrapper");
		wrapBodyPanel.getElement().setId("main");
		headerUc.getElement().setId("homeHeaderUc");
		wrapperPanel.getElement().setId("spnlWrapperPanel");
		searchPush.getElement().setId("searchPush");
		menuRight.getElement().setId("menuRight");


		  Boolean isIpad = !!Navigator.getUserAgent().matches("(.*)iPad(.*)");
		  Boolean isAndriod = !!Navigator.getUserAgent().matches("(.*)Android(.*)");
		  Boolean isWinDskp = !!Navigator.getUserAgent().matches("(.*)NT(.*)");
		  if (BrowserAgent.isDevice()){
			  if(isAndriod && !StringUtil.IPAD_MESSAGE_Close_Click){
				  panelDevice.add(new DeviceUc(wrapperPanel, headerUc));
				  panelDevice.setVisible(true);
				  wrapperPanel.getElement().setAttribute("style", "margin-top:0px;");
				  headerUc.getElement().getFirstChildElement().setAttribute("style", "position:relative;");
			  }
		  }else{
			  panelDevice.clear();
			  panelDevice.setVisible(false);
			  wrapperPanel.getElement().setAttribute("style", "margin-top:36px;");
		  }

		  ClickHandler rootClick = new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {

				}
			};

			RootPanel.get().addDomHandler(rootClick, ClickEvent.getType());

	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.SEARCH_COLLECTION)){
			if(AppClientFactory.getPlaceManager().getRequestParameter("query")!=null)
			{
			if(AppClientFactory.getPlaceManager().getRequestParameter("query").equalsIgnoreCase("*"))
			{
				headerUc.editSearchTxtBox.setText("");
			}
			else
			{
				headerUc.editSearchTxtBox.setText(AppClientFactory.getPlaceManager().getRequestParameter("query"));
			}
			}

		}
		activateSearchBar(true);
		if (slot == WrapPresenter.TYPE_VIEW) {
			if (content != null) {
				String place=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
				if(place!=null&&((!place.equals(PlaceTokens.HOME)||!(place.equals(PlaceTokens.SEARCH_COLLECTION)||!place.equals(PlaceTokens.FOLDER_TOC)||!(place.equals(PlaceTokens.SEARCH_RESOURCE)))))){
					if (place.equals(PlaceTokens.SHELF)
							|| place.equalsIgnoreCase(PlaceTokens.COMMUNITY)
							|| place.equalsIgnoreCase(PlaceTokens.RUSD_LIBRARY)
							|| place.equalsIgnoreCase(PlaceTokens.EPISD_LIBRARY)
							|| place.equalsIgnoreCase(PlaceTokens.SAUSD_LIBRARY)
							|| place.equalsIgnoreCase(PlaceTokens.YCGL_LIBRARY)
							|| place.equalsIgnoreCase(PlaceTokens.VALVERDE)
							|| place.equalsIgnoreCase(PlaceTokens.LIFEBOARD)
							|| place.equalsIgnoreCase(PlaceTokens.EDIT_CLASSPAGE)
							|| place.equalsIgnoreCase(PlaceTokens.STUDENT)) {
						wrapperPanel.getElement().setAttribute("style", "margin-top:36px;");
					}else{

					}
				}
				wrapperPanel.setWidget(content);
			}
		}
	}

	@Override
	public void invokeLogin() {
		headerUc.onLinkPopupClicked(null);
	}

	@Override
	public void setLoginData(UserDo user) {
		headerUc.setLoggedInUser(user);
	}

	@Override
	public void invokeRegister() {
		headerUc.onRegisterPopupClicked(null);
	}

	@Override
	public void activateSearchBar(boolean activate) {
		headerUc.setSearchBarEnabled(activate);
	}

	@Override
	public void invokeGooruGuideBubble() {

	}

	public void setDotsPanelSelection(HeaderTabType tabType){

		headerUc.manageDotsMenuSelectionFromEvent(tabType);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.wrap.IsWrapView#setDiscoverLinkFromLibrary(java.lang.String)
	 */
	@Override
	public void setDiscoverLinkFromLibrary(String discoverLink) {
		headerUc.setDiscoverLinkFromLibrary(discoverLink);
	}


	@Override
	public HTMLPanel getSearchFiltersPanel(){
		return resorceSearchFilters;
	}

	@Override
	public HTMLPanel getCollectionSearchFiltersPanel(){

		return collectionSearchFilters;
	}

	@Override
	public void updateUserHeaderProfileImage(String imageUrl) {
		headerUc.updateHeaderProfileImage(imageUrl);
	}


}
