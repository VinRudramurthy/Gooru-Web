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
package org.ednovo.gooru.client.mvp.library.partner;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.library.PartnerFolderListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

public class PartnerLibraryPresenter extends PresenterWidget<IsPartnerLibraryView> implements PartnerLibraryUiHandlers {

	private static final String LIBRARY_PAGE = "partner-page";
	
	private static final String SHARING_TYPE = "public";
	
	@Inject
	public PartnerLibraryPresenter(EventBus eventBus, IsPartnerLibraryView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}
	
	@Override
	public void onBind() {
		super.onBind();
		Window.enableScrolling(true);
		Window.scrollTo(0, 0);
	}

	@Override
	public void onReveal() {
		super.onReveal();
		getView().onLoad();
		getView().reset();
		Window.enableScrolling(true);
		Window.scrollTo(0, 0);
	}

	@Override
	public void onReset() {
		super.onReset();
		Window.enableScrolling(true);
		Window.scrollTo(0, 0);
	}
	@Override
	public void getPartnerWorkspaceFolders() {
		final long startTime = System.currentTimeMillis();
		AppClientFactory.getInjector().getLibraryService().getLibraryPartnerWorkspace(AppClientFactory.getCurrentPlaceToken(), 20, SHARING_TYPE, null, AppClientFactory.getCurrentPlaceToken(), new SimpleAsyncCallback<PartnerFolderListDo>(){
			 
			@Override
			public void onSuccess(PartnerFolderListDo result) {
				getView().setUnitList(result.getSearchResult());
			}
		});
	}
	
	@Override
	public void getPartnerChildFolderItems(final String folderId, final int pageNumber,final String libraryGooruOid) {
		final long startTime = System.currentTimeMillis();
		AppClientFactory.getInjector().getLibraryService().getPartnerPaginationWorkspace(folderId,SHARING_TYPE, 20,new SimpleAsyncCallback<PartnerFolderListDo>() {
			@Override
			public void onSuccess(PartnerFolderListDo result) {
				getView().setTopicListData(result.getSearchResult(), folderId,libraryGooruOid);
				
			}
		});
	}
	
	public void setPartnerWidget() {
			if(AppClientFactory.getLoggedInUser()!=null) {
				getView().clearPanels();
				getView().getComingSoonText(false);
				getView().loadingPanel(true);
				getIntoPartnerLibrarypage();
				getPartnerWorkspaceFolders();
			} else {
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
			}
	}
	
	/**
	 * @function getIntoPartnerLibrarypage 
	 * @created_date : 18-Mar-2014
	 * @description
	 * @parm(s) : 
	 * @return : void
	 */
	@Override
	public void getIntoPartnerLibrarypage() {
		getView().loadPartnersPage(LIBRARY_PAGE,getViewToken());
	}
	
	public String getViewToken() {
		return AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
	}

}