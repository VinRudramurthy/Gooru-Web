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
package org.ednovo.gooru.client.mvp.folder.toc;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePlacePresenter;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderTocDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.folder.toc.FolderTocPresenter.IsFolderTocProxy;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
/**
 * @fileName : FolderTocPresenter.java
 *
 * @description : 
 *
 * @version : 1.3
 *
 * @date: 06-02-2015
 *
 * @Author Gooru Team
 *
 * @Reviewer: 
 */
public class FolderTocPresenter extends BasePlacePresenter<IsFolderTocView, IsFolderTocProxy> implements FolderTocUiHandlers {

	public static final String ID = "id";
	public static final String PARENT_ID = "parentId";
	public static final String LIBRARY_NAME = "libName";
	public static final String USER_ID = "userId";
	public static final String TYPE = "type";
	SignUpPresenter signUpViewPresenter = null;
	
	Map<String, String> params;
	
	@ProxyCodeSplit
	@NameToken(PlaceTokens.FOLDER_TOC)
	public interface IsFolderTocProxy extends ProxyPlace<FolderTocPresenter> {
	}

	@Inject
	public FolderTocPresenter(EventBus eventBus, IsFolderTocView view, IsFolderTocProxy proxy,SignUpPresenter signUpViewPresenter) {
		super(view, proxy);
		getView().setUiHandlers(this);
		this.signUpViewPresenter = signUpViewPresenter;
	}

	@Override
	public String getViewToken() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		String folderId=AppClientFactory.getPlaceManager().getRequestParameter(ID);
		getfolderTocList(folderId);
	}
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		showSignupPopup();
		
	}
	/**
	 * This method is used to display signup popup
	 */
	protected void showSignupPopup() {
		if (AppClientFactory.isAnonymous()){
			//To show SignUp (Registration popup)
			if (AppClientFactory.getPlaceManager().getRequestParameter("callback") != null && AppClientFactory.getPlaceManager().getRequestParameter("callback").equalsIgnoreCase("signup")) {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				String type = AppClientFactory.getPlaceManager().getRequestParameter("type") ;
				int displayScreen= (type!=null)? Integer.parseInt(type) : 1;
				signUpViewPresenter.displayPopup(displayScreen);
				addToPopupSlot(signUpViewPresenter);
			}
		}
	}
	@Override
	protected void onReset() {
		super.onReset();
	}
	
	@Override
	public void onBind() {
		super.onBind();
	}
	
	@Override
	public void getfolderTocList(String folderId) {
		getView().getTreePanel();
		getMapParams();
		//Check the user is logged in or not, and enabling the TOC if we are viewing from library
		if(AppClientFactory.isAnonymous() && StringUtil.isEmpty(folderId)){
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
		}else if(params.containsKey(USER_ID) && !params.get(USER_ID).equals(AppClientFactory.getLoggedInUser().getGooruUId())){
				getUserProfileVisibility();
		}else{
			Window.enableScrolling(true);
			getTocFolders(folderId);
			setFolderBanner();
		}
	}
	
	@Override
	public void getTocFolders(final String folderId) {
		AppClientFactory.getInjector().getfolderService().getTocFolders(folderId,checkSharable(), new SimpleAsyncCallback<FolderTocDo>() {
			@Override
			public void onSuccess(FolderTocDo folderListDo) {
				getView().clearTocData();
				if(folderListDo!=null && 200==folderListDo.getStatusCode()){
					getView().setFolderItems(folderListDo);
					getFolderRouteNodes(folderId);
					getView().setBackButtonText(params);
				}else{
					getView().showPageNotFound(true);
				}
			}
		});
		getShortenUrl(folderId, params);
	}
	/**
	 * This method is used to check the sharable value what need to be passed.
	 * @return
	 */
    public boolean checkSharable(){
    	//This lines is used to check the values exists are not and based on that it will pass sharing value.
    	boolean value=false;
		if(params.containsKey(USER_ID)){
			value=false;
		}else if(params.containsKey(LIBRARY_NAME)){
			value=false;
		}
		return value;
    }
	@Override
	public void getFolderItems(final TreeItem item,final String folderId) {
		AppClientFactory.getInjector().getfolderService().getTocFolders(folderId,checkSharable(),new SimpleAsyncCallback<FolderTocDo>() {
			@Override
			public void onSuccess(FolderTocDo folderListDo) {
				getView().setFolderItems(item,folderListDo,folderId);
			}
		});
	}
	/**
	 * To set the folder meta data details.
	 */
	private void setFolderBanner() {
		if(params.containsKey(LIBRARY_NAME)){
			if(params.containsKey(PARENT_ID)){
				getFolderMetaData(params.get(PARENT_ID));
			}else{
				getView().setBannerImages();
			}
		}else if(!params.containsKey(USER_ID)){
			getView().hidePanels();
		}
	}
	
    /**
     * To generate the bitly link of the TOC Page.
     */
	@Override
	public void getShortenUrl(String folderId, Map<String, String> params) {
		AppClientFactory.getInjector().getSearchService().getShortenShareUrl(folderId, params, new SimpleAsyncCallback<Map<String,String>>() {
			@Override
			public void onSuccess(Map<String, String> result) {
				if(result!=null){
					getView().setBitlyLink(result);
				}
			}
		});
	}
    /**
     * To get the User profile details 
	 * @param profId {@link String}
     */
	@Override
	public void getProfilePageDetails(String profId) {
		AppClientFactory.getInjector().getUserService().getUserProfileV2Details(profId, "0", new SimpleAsyncCallback<ProfileDo>() {
			@Override
			public void onSuccess(ProfileDo profileDo) {
				if(profileDo!=null){
				 getView().setProfileBannerDetails(profileDo);	
				}
			}
		});
	}
	/**
	 * This API used for to get the Parent folder Metadata of library.
	 * @param parentId
	 */
	@Override
	public void getFolderMetaData(String parentId) {
		AppClientFactory.getInjector().getfolderService().getFolderMetaData(parentId, new SimpleAsyncCallback<FolderDo>() {
			@Override
			public void onSuccess(FolderDo result) {
				if(result!=null && result.getThumbnails()!=null && result.getThumbnails().getUrl()!=""){
					getView().setCourseBanner(result);
				}else{
					getView().setBannerImages();
				}
			}
		});
	}
	/**
	 * To store the request params into Map 
	 */
	private void getMapParams() {
		params = new HashMap<String, String>();
		String folderId=AppClientFactory.getPlaceManager().getRequestParameter(ID,null);
	    String parentId=AppClientFactory.getPlaceManager().getRequestParameter(PARENT_ID,null);
		String libName=AppClientFactory.getPlaceManager().getRequestParameter(LIBRARY_NAME,null);
		String userId=AppClientFactory.getPlaceManager().getRequestParameter(USER_ID,null);
		params.put(TYPE, PlaceTokens.FOLDER_TOC);
		if(folderId!=null){
			params.put(ID, folderId);
		}
		if(parentId!=null){
			params.put(PARENT_ID, parentId);
		}
		if(libName!=null){
			params.put(LIBRARY_NAME, libName);
		}
		if(userId!=null){
			params.put(USER_ID, userId);
		}
	}
	
	/**
	 * To get the folder route nodes
	 * @param folderId {@link String}
	 */
	private void getFolderRouteNodes(String folderId) {
		AppClientFactory.getInjector().getfolderService().getFolderRouteNodes(folderId, new SimpleAsyncCallback<Map<String,String>>() {
			@Override
			public void onSuccess(Map<String,String> result) {
				getView().setBreadCrumbs(result);
			}
		});
	}
	/**
	 * To get profile visibility status
	 */
	private void getUserProfileVisibility(){
		AppClientFactory.getInjector().getPlayerAppService().getUserProfileVisibility(params.get(USER_ID), new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					Window.enableScrolling(true);
					getTocFolders(params.get(ID));
					setFolderBanner();
					
				}else{
					getView().showPageNotFound(true);
				}
			}
		});
	}
}
