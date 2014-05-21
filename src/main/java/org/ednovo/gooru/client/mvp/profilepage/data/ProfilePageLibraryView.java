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
import java.util.Iterator;

import org.ednovo.gooru.client.child.ChildView;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.profilepage.data.item.LeftMenuItemView;
import org.ednovo.gooru.client.mvp.profilepage.data.item.ProfileTopicListView;
import org.ednovo.gooru.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.shared.model.library.ConceptDo;
import org.ednovo.gooru.shared.model.library.PartnerFolderDo;
import org.ednovo.gooru.shared.model.library.PartnerFolderListDo;
import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru Team
 * 
 */
public class ProfilePageLibraryView extends ChildView<ProfilePageLibraryPresenter> implements IsProfilePageLibraryView,MessageProperties {

	@UiField HTMLPanel leftNav, contentScroll;
	
	@UiField ProfilePageLibraryStyleBundle style;
	
	private static final String FOLDERID = "folderId";
	
	private String unitListId = "";
	
	ProfilePageLibraryPresenter profilePageLibraryPresenter = null;
	
	private static ProfilePageLibraryViewUiBinder uiBinder = GWT.create(ProfilePageLibraryViewUiBinder.class);

	interface ProfilePageLibraryViewUiBinder extends UiBinder<Widget, ProfilePageLibraryView> {}

	/**
	 * Class constructor
	 
	 * @param collectionItem
	 *            instance of {@link CollectionItemDo}
	 */
	public ProfilePageLibraryView() {
		initWidget(uiBinder.createAndBindUi(this));
		setPresenter(new ProfilePageLibraryPresenter(this));
	}
	
	public void setData() {
		getPresenter().getPartnerWorkspaceFolders();
	}
	
	@Override
	public void loadPartnersPage(String callBack, String placeToken) {
		
	}
	
	@Override
	public void setUnitList(final PartnerFolderListDo partnerFolderListDo) {
		leftNav.clear();
		String folderId = AppClientFactory.getPlaceManager().getRequestParameter(FOLDERID);
		int j = 0;
		final ArrayList<PartnerFolderDo> folderList = partnerFolderListDo.getSearchResult();
		for(int i = 0; i<folderList.size(); i++) {
			LeftMenuItemView leftMenuItemView = new LeftMenuItemView(folderList.get(i));
			leftNav.add(leftMenuItemView);
			if(folderList.get(i).getType().equals("scollection")) {
				leftMenuItemView.addStyleName(style.collection());
			}
			if(j==0&&folderId==null) {
				j++;
				loadingPanel(true);
				leftMenuItemView.addStyleName(style.open());
				leftMenuItemView.addStyleName(style.active());
				unitListId = folderList.get(i).getGooruOid();
				if(folderList.get(i).getType().equals("scollection")) {
					setTopicListData(null,partnerFolderListDo.getCollections().get(i) , unitListId);
				} else {
					setTopicListData(folderList.get(i).getFolderItems(), null, unitListId);
				}
			}
		}
		
		final Iterator<Widget> widgets = leftNav.iterator();
		int widgetCount = 0;
		while (widgets.hasNext()) {
			final Widget widget = widgets.next();
			final LeftMenuItemView leftMenuItemView = ((LeftMenuItemView) widget);
			final int finalWidgetCount = widgetCount;
			leftMenuItemView.getUnitMenuItemPanel().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					loadingPanel(true);
					final Iterator<Widget> widgetsPanel = leftNav.iterator();
					while (widgetsPanel.hasNext()) {
						final Widget widgetTxt = widgetsPanel.next();
						widgetTxt.removeStyleName(style.active());
						widgetTxt.removeStyleName(style.open());
					}
					widget.addStyleName(style.open());
					widget.addStyleName(style.active());
					unitListId = leftMenuItemView.getUnitId();
					if(finalWidgetCount==0) {
						if(folderList.get(finalWidgetCount).getType().equals("scollection")) {
							setTopicListData(null, partnerFolderListDo.getCollections().get(finalWidgetCount), unitListId);
						} else {
							setTopicListData(folderList.get(finalWidgetCount).getFolderItems(), null, unitListId);
						}
					} else {
						getPresenter().getPartnerChildFolderItems(unitListId, 1);
					}
				}
			});
			widgetCount++;
		}
	}

	@Override
	public void setTopicListData(ArrayList<PartnerFolderDo> folderListDo, ConceptDo conceptDo, String folderId) {
		contentScroll.clear();
		try {
			int count = 0;
			if(conceptDo!=null) {
				contentScroll.add(new ProfileTopicListView(conceptDo, count, AppClientFactory.getCurrentPlaceToken()));
			} else {
				for(int i = 0; i <folderListDo.size(); i++) {
					count++;
					contentScroll.add(new ProfileTopicListView(folderListDo.get(i), count, AppClientFactory.getCurrentPlaceToken()));
				}
			}
			contentScroll.setVisible(true);
			loadingPanel(false);
		} catch (Exception e) {
			e.printStackTrace();
			loadingPanel(false);
		}

	}
	
	@Override
	public void loadingPanel(boolean isVisible) {

	}

	@Override
	public void clearPanels() {
		
	}	
	
	@Override
	public void setPresenter(ProfilePageLibraryPresenter profilePageLibraryPresenter) {
		this.profilePageLibraryPresenter = profilePageLibraryPresenter;
	}
	
	@Override
	public ProfilePageLibraryPresenter getPresenter() {
		return profilePageLibraryPresenter;
	}
}