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
/**
 * 
 */
package org.ednovo.gooru.client.mvp.profilepage.data;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.child.ChildPresenter;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;

/**
 * @author Search Team
 * 
 */
public class ProfilePageLibraryPresenter extends ChildPresenter<ProfilePageLibraryPresenter, IsProfilePageLibraryView> implements ProfilePageLibraryUiHandlers{
	
	private static final String LIBRARY_PAGE = "partner-page";
	
	private static final String SHARING_TYPE = "public";
	
	/**
	 * Class constructor
	 * 
	 * @param childView 
	 */
	public ProfilePageLibraryPresenter(IsProfilePageLibraryView childView) {
		super(childView);
	}
	
	@Override
	public void getPartnerWorkspaceFolders(int offset) {
		
		String id = AppClientFactory.getPlaceManager().getRequestParameter("id");
		AppClientFactory.getInjector().getProfilePageService().getProfileLibraryWorkspace(id, 14, SHARING_TYPE, null, getViewToken(), offset, new SimpleAsyncCallback<ProfileLibraryListDo>(){

			@Override
			public void onSuccess(ProfileLibraryListDo result) {
				if(result!=null && result.getSearchResult()!=null && result.getSearchResult().size()>0) {
					getView().setEmptyContainer(false);
					getView().setUnitList(result);
				} else {
					getView().setEmptyContainer(true);
				}
			}
		});
	}
	@Override
	public void getPartnerWorkspaceFoldersOnScroll(int offset) {
		String id = AppClientFactory.getPlaceManager().getRequestParameter("id");
		AppClientFactory.getInjector().getProfilePageService().getProfileLibraryWorkspace(id, 14, SHARING_TYPE, null, getViewToken(), offset, new SimpleAsyncCallback<ProfileLibraryListDo>(){
			@Override
			public void onFailure(Throwable caught) {
				super.onFailure(caught);
				getView().setEmptyContainer(true);
			}
			
			@Override
			public void onSuccess(ProfileLibraryListDo result) {
					getView().setEmptyContainer(false);
					getView().setUnitList(result);
				
			}
		});
	}
	@Override
	public void getPartnerChildFolderItems(final String folderId, final int pageNumber) {
		AppClientFactory.getInjector().getProfilePageService().getProfilePaginationWorkspace(folderId,SHARING_TYPE, 14,new SimpleAsyncCallback<ProfileLibraryListDo>() {
			@Override
			public void onSuccess(ProfileLibraryListDo result) {
 				getView().setTopicListData(result.getSearchResult(), folderId);
			}
		
		});
	}
	
	public void setPartnerWidget() {
		if(AppClientFactory.getLoggedInUser()!=null) {
			getView().clearPanels();
			getView().loadingPanel(true);
			getIntoPartnerLibrarypage();
			getPartnerWorkspaceFolders(0);
		} else {
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.ERROR);
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
		return PlaceTokens.PROFILE_PAGE;
	}

	@Override
	public void getProfileLibraryCollection(final String gooruOid, boolean skipCollectionItems) {
		AppClientFactory.getInjector().getProfilePageService().getProfileLibraryCollection(gooruOid, skipCollectionItems, new SimpleAsyncCallback<ProfileLibraryDo>() {
			@Override
			public void onSuccess(ProfileLibraryDo result) {
				getView().setTopicListData(result, gooruOid);
			}
			
		});
	}

}