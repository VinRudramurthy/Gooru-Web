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
package org.ednovo.gooru.client.mvp.profilepage.list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.profilepage.event.RefreshProfileListEvent;
import org.ednovo.gooru.client.mvp.profilepage.event.RequestCollectionOpenEvent;
import org.ednovo.gooru.client.mvp.profilepage.event.RequestFolderOpenEvent;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru Team
 *
 */
public class ProfilePageCollection extends FocusPanel implements ClickHandler {

	private static ProfilePageCollectionUiBinder uiBinder = GWT
			.create(ProfilePageCollectionUiBinder.class);

	interface ProfilePageCollectionUiBinder extends UiBinder<Widget, ProfilePageCollection> {
	}

	@UiField
	HTML titleLbl;

	@UiField
	VerticalPanel contentVerPanel;

	@UiField(provided = true)
	ShelfListCBundle res;

	@UiField
	FocusPanel titleFocPanel;

	@UiField
	FocusPanel wrapperFocPanel;

	@UiField
	DisclosurePanel disPanel;

	@UiField
	FlowPanel folderIcon, collectionIcon;

	private CollectionItemDo collectionItemDo;

	private static ProfilePageCollection shelfCollection;

	/**
	 * Class constructor , assign the {@link CollectionItemDo} instance
	 *
	 * @param collectionDo
	 *            instance of {@link CollectionItemDo}
	 */
	public ProfilePageCollection(final CollectionItemDo collectionItemDo) {
		res = ShelfListCBundle.INSTANCE;
		res.css().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));
		setData(collectionItemDo);
		titleFocPanel.addClickHandler(this);
		wrapperFocPanel.addClickHandler(this);
		titleFocPanel.setStyleName(res.css().foldersLi());

		titleFocPanel.getElement().setId("focuspnlTitleFocPanel");
		folderIcon.getElement().setId("fpnlFolderIcon");
		collectionIcon.getElement().setId("fpnlCollectionIcon");
		titleLbl.getElement().setId("htmlTitleLbl");
		disPanel.getElement().setId("discpnlDisPanel");
		wrapperFocPanel.getElement().setId("focuspnlWrapperFocPanel");
		contentVerPanel.getElement().setId("vpnlContentVerPanel");
	}

	public void setData(CollectionItemDo collectionItemDo) {
		this.collectionItemDo = collectionItemDo;
		titleLbl.setHTML(this.collectionItemDo.getResource().getTitle());
		titleLbl.getElement().setAttribute("alt",this.collectionItemDo.getResource().getTitle());
		titleLbl.getElement().setAttribute("title",this.collectionItemDo.getResource().getTitle());
		if (this.collectionItemDo.getResource().getResourceType().getName().equalsIgnoreCase("folder")) {
			collectionIcon.removeFromParent();
		} else {
			folderIcon.removeFromParent();
		}
	}

	public void setOpen(boolean isOpen) {
		disPanel.setOpen(isOpen);
	}

	public boolean isOpen() {
		return disPanel.isOpen();
	}

	public void setOpenFolderStyle(boolean isOpen) {
		if(isOpen) {
			titleFocPanel.removeStyleName(res.css().foldersLi());
			titleFocPanel.setStyleName(res.css().foldersLiOpen());
		} else {
			titleFocPanel.removeStyleName(res.css().foldersLiOpen());
			titleFocPanel.setStyleName(res.css().foldersLi());
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource().equals(titleFocPanel))
		{
			if(this.collectionItemDo.getResource().getResourceType().getName().equalsIgnoreCase("folder")) {
				 MixpanelUtil.Click_On_Folder();
				 Map<String, String> params = new HashMap<String, String>();
				 String userId = AppClientFactory.getPlaceManager().getRequestParameter("id");
				 params.put("id", userId);
				 params.put("folderid", collectionItemDo.getResource().getGooruOid());
				 params.put("level", "1");
				 AppClientFactory.fireEvent(new RefreshProfileListEvent(collectionItemDo.getResource().getGooruOid(), "1"));
				 AppClientFactory.fireEvent(new RequestFolderOpenEvent(collectionItemDo.getResource().getGooruOid(),params));
			 } else {
				MixpanelUtil.Click_On_Collection();
				Map<String, String> params = new HashMap<String, String>();
				String userId = AppClientFactory.getPlaceManager().getRequestParameter("id");
				params.put("id", userId);
				params.put("collectionid", collectionItemDo.getResource().getGooruOid());
				AppClientFactory.fireEvent(new RefreshProfileListEvent(collectionItemDo.getResource().getGooruOid(), "1"));
				AppClientFactory.fireEvent(new RequestCollectionOpenEvent(collectionItemDo,params));
			 }
		} else if (event.getSource().equals(wrapperFocPanel)) {
		}
	}

	public void setInnerFolderContent() {
	 AppClientFactory.getInjector().getProfilePageService().getFolders(this.collectionItemDo.getResource().getGooruOid(), new SimpleAsyncCallback<List<CollectionItemDo>>() {
		 @Override
		 public void onSuccess(List<CollectionItemDo> collectionItems) {
			 contentVerPanel.clear();
			 for (CollectionItemDo collectionItemDo : collectionItems) {
				 contentVerPanel.add(new ProfilePageCollectionFolder(collectionItemDo));
			 }
		 }
	 });
	 setOpen();
	}

	public CollectionItemDo getCollectionItemDo() {
		return collectionItemDo;
	}

	public VerticalPanel getContentVerPanel() {
		return contentVerPanel;
	}

	public void setOpen() {
		if (shelfCollection == null || !shelfCollection.equals(this)) {
			if (shelfCollection != null) {
				shelfCollection.setOpen(false);
			}
			shelfCollection = this;
			setOpen(!isOpen());
		}
	}
}
