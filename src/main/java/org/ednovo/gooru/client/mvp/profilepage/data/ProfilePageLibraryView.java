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
package org.ednovo.gooru.client.mvp.profilepage.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;
import org.ednovo.gooru.client.mvp.profilepage.data.item.LeftMenuItemView;
import org.ednovo.gooru.client.mvp.profilepage.data.item.ProfileTopicListView;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru Team
 *
 */
public class ProfilePageLibraryView extends ChildView<ProfilePageLibraryPresenter> implements IsProfilePageLibraryView {

	@UiField HTMLPanel leftNav, contentScroll, emptyContainer, loadingIconPanel,folderListPanel;

	@UiField ProfilePageLibraryStyleBundle style;

	@UiField Label noCollectionsMsg, collectionsRedirectionMsg,folderTopicTitleLbl;

	@UiField Button myCollectionsBtn,listAllBtn;

	private static final String FOLDERID = "folderId";

	private static final String ASSESSMENT = "assessment";

	private static final String COLLECTION = "collection";

	private String unitListId = "";

	ProfilePageLibraryPresenter profilePageLibraryPresenter = null;

	private int totalLeftPanelCount = 0;

	private boolean isApiProgress  = true;

	HandlerRegistration handlerRegistration = null;

	private static ProfilePageLibraryViewUiBinder uiBinder = GWT.create(ProfilePageLibraryViewUiBinder.class);

	interface ProfilePageLibraryViewUiBinder extends UiBinder<Widget, ProfilePageLibraryView> {}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor

	 * @param collectionItem
	 *            instance of {@link CollectionItemDo}
	 */
	public ProfilePageLibraryView() {
		initWidget(uiBinder.createAndBindUi(this));
		setPresenter(new ProfilePageLibraryPresenter(this));
		emptyContainer.setVisible(false);
		setMetaData();
		Window.addWindowScrollHandler(new LeftPanelScroll());
	}

	private void setMetaData() {
		myCollectionsBtn.setText(i18n.GL1752());
		myCollectionsBtn.getElement().setId("btnMyCollectionsBtn");
		myCollectionsBtn.getElement().setAttribute("alt",i18n.GL1752());
		myCollectionsBtn.getElement().setAttribute("title",i18n.GL1752());

		collectionsRedirectionMsg.setText(i18n.GL1788());
		collectionsRedirectionMsg.getElement().setId("lblCollectionsRedirectionMsg");
		collectionsRedirectionMsg.getElement().setAttribute("alt",i18n.GL1788());
		collectionsRedirectionMsg.getElement().setAttribute("title",i18n.GL1788());

		emptyContainer.getElement().setId("pnlEmptyContainer");
		listAllBtn.getElement().setId("btnViewAll");
		noCollectionsMsg.getElement().setId("lblNoCollectionsMsg");
		leftNav.getElement().setId("pnlLeftNav");
		loadingIconPanel.getElement().setId("pnlLoadingImage");
		contentScroll.getElement().setId("pnlContentScroll");
		listAllBtn.getElement().setAttribute("style", "float:right;margin: -22px -6px -6px 0px;");
		folderListPanel.setVisible(false);
		listAllBtn.setVisible(false);
	}

	public void setData() {
		leftNav.clear();
		loadingPanel(true);
		folderListPanel.setVisible(false);
		getPresenter().getPartnerWorkspaceFolders(0);
	}

	@Override
	public void loadPartnersPage(String callBack, String placeToken) {

	}

	@Override
	public void setUnitList(final ProfileLibraryListDo profileLibraryListDo) {
		totalLeftPanelCount = profileLibraryListDo.getCount();
		String folderId = AppClientFactory.getPlaceManager().getRequestParameter(FOLDERID);
		int firstWidgetCount = leftNav.getWidgetCount();
		final ArrayList<ProfileLibraryDo> folderList = profileLibraryListDo.getSearchResult();
		for(int i = 0; i<folderList.size(); i++) {
			final LeftMenuItemView leftMenuItemView = new LeftMenuItemView(folderList.get(i));
			leftNav.add(leftMenuItemView);
			leftMenuItemView.setWidgetCount(leftNav.getWidgetCount()+1);
			leftMenuItemView.setType(folderList.get(i).getType());
			if(folderList.get(i).getType().contains(ASSESSMENT)) {
				leftMenuItemView.addStyleName("assessment");
			}else if(folderList.get(i).getType().equals(COLLECTION)){
				leftMenuItemView.addStyleName("collection");
			}
			if(firstWidgetCount==0&&folderId==null) {
				firstWidgetCount++;
				loadingPanel(true);
				folderListPanel.setVisible(false);
				leftMenuItemView.addStyleName("folderOpen");
				leftMenuItemView.addStyleName(style.active());
				unitListId = folderList.get(i).getGooruOid();
				if(folderList.get(i).getType().equals(COLLECTION) || folderList.get(i).getType().contains(ASSESSMENT)) {
					leftMenuItemView.removeStyleName("folderOpen");
					setTopicListData(folderList.get(i),  unitListId);
					listAllBtn.setVisible(false);
					folderListPanel.setVisible(false);
				} else {
					listAllBtn.setVisible(true);
					folderListPanel.setVisible(true);
					folderTopicTitleLbl.setText(folderList.get(i).getTitle());
					if(handlerRegistration!=null){
						handlerRegistration.removeHandler();
					}
					handlerRegistration=listAllBtn.addClickHandler(new ClickOnListAll(folderList.get(i).getGooruOid()));
					setTopicListData(folderList.get(i).getCollectionItems(),  unitListId);
				}
			}
			leftMenuItemView.getUnitMenuItemPanel().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(leftMenuItemView.getWidgetCount()>10) {
						Window.scrollTo(0, 0);
					}
					loadingPanel(true);
					folderListPanel.setVisible(false);
					final Iterator<Widget> widgetsPanel = leftNav.iterator();
					while (widgetsPanel.hasNext()) {
						final Widget widgetTxt = widgetsPanel.next();
						widgetTxt.removeStyleName(style.active());
						widgetTxt.removeStyleName("folderOpen");
					}
					leftMenuItemView.addStyleName("folderOpen");
					leftMenuItemView.addStyleName(style.active());
					unitListId = leftMenuItemView.getUnitId();
					if(leftMenuItemView.getType().equals(COLLECTION) || leftMenuItemView.getType().contains(ASSESSMENT)) {
						leftMenuItemView.removeStyleName("folderOpen");
						getPresenter().getProfileLibraryCollection(unitListId, false);
						listAllBtn.setVisible(false);
						folderListPanel.setVisible(false);
					} else {
						folderListPanel.setVisible(true);
						listAllBtn.setVisible(true);
						folderTopicTitleLbl.setText(leftMenuItemView.getTitle());
						if(handlerRegistration!=null){
							handlerRegistration.removeHandler();
						}
						handlerRegistration=listAllBtn.addClickHandler(new ClickOnListAll(unitListId));
						getPresenter().getPartnerChildFolderItems(unitListId, 1);
					}
				}
			});
		}
		isApiProgress = true;
	}

	@Override
	public void setTopicListData(ProfileLibraryDo profileLibraryDo, String folderId) {
		contentScroll.clear();
		try {
			if(profileLibraryDo != null){
				contentScroll.add(new ProfileTopicListView(profileLibraryDo, 0, AppClientFactory.getCurrentPlaceToken(), COLLECTION,null));
				loadingPanel(false);
			}
			
		} catch (Exception e) {
			loadingPanel(false);
			//e.printStackTrace();
		    AppClientFactory.printSevereLogger("ProfilePageLibraryView-setTopicListData"+e);
		}
	}

	@Override
	public void setTopicListData(ArrayList<ProfileLibraryDo> folderListDo, String folderId) {
		contentScroll.clear();
		try {
			int count = 0;
			if(folderListDo.size()>0) {
				for(int i = 0; i <folderListDo.size(); i++) {
					count++;
					if(folderListDo.get(i).getType().equals(COLLECTION)) {
						contentScroll.add(new ProfileTopicListView(folderListDo.get(i), count, AppClientFactory.getCurrentPlaceToken(), COLLECTION,null));
					} else {
						contentScroll.add(new ProfileTopicListView(folderListDo.get(i), AppClientFactory.getCurrentPlaceToken(),null,count,""));
					}
				}
			} else {
				HTMLPanel emptyContainer = new HTMLPanel("");
				emptyContainer.setStyleName(style.emptyFolderContainer());
				contentScroll.add(emptyContainer);
			}
			loadingPanel(false);
		} catch (Exception e) {
			loadingPanel(false);
			//e.printStackTrace();
			AppClientFactory.printSevereLogger("ProfilePageLibraryView-setTopicListData"+e);
		}
	}

	@Override
	public void loadingPanel(boolean isVisible) {
		loadingIconPanel.setVisible(isVisible);
		contentScroll.setVisible(!isVisible);
		//folderListPanel.setVisible(!isVisible);
	}

	@Override
	public void clearPanels() {
		leftNav.clear();
	}

	@Override
	public void setPresenter(ProfilePageLibraryPresenter profilePageLibraryPresenter) {
		this.profilePageLibraryPresenter = profilePageLibraryPresenter;
	}

	@Override
	public ProfilePageLibraryPresenter getPresenter() {
		return profilePageLibraryPresenter;
	}

	@UiHandler("myCollectionsBtn")
	public void clickMyCollectionsBtn(ClickEvent event) {
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT);
	}

	@Override
	public void setEmptyContainer(boolean isEmpty) {
		emptyContainer.setVisible(isEmpty);
		leftNav.setVisible(!isEmpty);
		contentScroll.setVisible(!isEmpty);
		if(isEmpty) {
			setUserNoContentMsg();
		}
		loadingIconPanel.setVisible(false);
	}

	private void setUserNoContentMsg() {
		if(AppClientFactory.getPlaceManager().getRequestParameter("id").equals(AppClientFactory.getLoggedInUser().getGooruUId())) {
			noCollectionsMsg.setText(i18n.GL1790());
			noCollectionsMsg.getElement().setAttribute("alt",i18n.GL1790());
			noCollectionsMsg.getElement().setAttribute("title",i18n.GL1790());
			collectionsRedirectionMsg.setVisible(true);
			myCollectionsBtn.setVisible(true);
		} else {
//			noCollectionsMsg.setText("\""+AppClientFactory.getPlaceManager().getRequestParameter("user")+"\" "+i18n.GL1791);
			noCollectionsMsg.setText(StringUtil.generateMessage(i18n.GL1791(),AppClientFactory.getPlaceManager().getRequestParameter("user")));
			noCollectionsMsg.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL1791(),AppClientFactory.getPlaceManager().getRequestParameter("user")));
			noCollectionsMsg.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL1791(),AppClientFactory.getPlaceManager().getRequestParameter("user")));
			collectionsRedirectionMsg.setVisible(false);
			myCollectionsBtn.setVisible(false);
		}
	}

	public class LeftPanelScroll implements ScrollHandler{
		@Override
		public void onWindowScroll(ScrollEvent event) {
			if(leftNav.getWidgetCount()<totalLeftPanelCount&&isApiProgress) {
				isApiProgress = false;
				getPresenter().getPartnerWorkspaceFoldersOnScroll(leftNav.getWidgetCount());
			}
		}
	}
	/**
	 * This Inner class used to navigate to Folder TOC page when click on ListAll button.
	 * @author janamitra
	 *
	 */
	public class ClickOnListAll implements ClickHandler{
		String folderId="";
		public ClickOnListAll(String folderId){
			this.folderId=folderId;
		}
		@Override
		public void onClick(ClickEvent event) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", folderId);
			params.put("userId", AppClientFactory.getPlaceManager().getRequestParameter("id"));
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.FOLDER_TOC,params);
		}

	}
}