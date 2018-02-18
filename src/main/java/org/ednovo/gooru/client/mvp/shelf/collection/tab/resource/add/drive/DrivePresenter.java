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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.ednovo.gooru.application.client.AppPlaceKeeper;
import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePlacePresenter;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.drive.GoogleDriveDo;
import org.ednovo.gooru.application.shared.model.drive.GoogleDriveItemDo;
import org.ednovo.gooru.application.shared.model.user.GoogleToken;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddResourcePresenter;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
/**
 * 
 * @fileName : DrivePresenter.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 28-Oct-2014
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class DrivePresenter extends
		BasePlacePresenter<IsDriveView, DrivePresenter.IsDriveyProxy> implements
		DriveUiHandlers {

	
	private AddResourcePresenter addResourcePresenter;
	
	private static final String GOOGLE_REFRESH_TOKEN = "google-refresh-token";
	private static final String GOOGLE_ACCESS_TOKEN = "google-access-token";

	
	@ProxyCodeSplit
	@NameToken(PlaceTokens.DRIVE)
	@UseGatekeeper(AppPlaceKeeper.class)
	public interface IsDriveyProxy extends ProxyPlace<DrivePresenter> {
	}

	@Inject
	public DrivePresenter(IsDriveView view, IsDriveyProxy proxy) {
		super(view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	public void onBind() {
		super.onBind();
	}

	@Override
	public void onReveal() {
		super.onReveal();
	}

	@Override
	public void onReset() {
		super.onReset();

	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
	}

	@Override
	public String getViewToken() {
		return PlaceTokens.HOME;
	}

	
	public void showDriveNotConnectedErrorMessage(){
		getView().getPanelFileList().clear();
		getView().showDriveNotConnectedErrorMessage();
	}
	
	public void getGoogleDriveFiles(final String folderId,final String nextPageToken,final boolean isPanelClear) {
		if(isPanelClear){
			getView().getPanelFileList().clear();
			getView().getPanelFileList().add(setLoadingPanel());
		}
		String refresh_token = AppClientFactory.getLoggedInUser().getRefreshToken();
		
		if (refresh_token == null){
			AppClientFactory.getInjector().getUserService().getRefershToken(AppClientFactory.getLoggedInUser().getGooruUId(),new SimpleAsyncCallback<String>() {
				@Override
				public void onSuccess(String result) {
					
					if(result!=null&&!result.equals("")&&!result.equals("null")){
						UserDo user = AppClientFactory.getLoggedInUser();
						user.setRefreshToken(result);
						AppClientFactory.setLoggedInUser(user);
						getAccessToken(result, folderId, nextPageToken, isPanelClear);
					}else{
						getView().showNoDriveAccess(401);
					}
				}
			});
		}else{
			getAccessToken(refresh_token, folderId, nextPageToken, isPanelClear);
		}
		
			
	}
	
	public void getAccessToken(String refresh_token,final String folderId,final String nextPageToken,final boolean isPanelClear){

		if (refresh_token != null&&!refresh_token.equals("")&&!refresh_token.equals("null")){
			String tmpRefreshToken = refresh_token;
			AppClientFactory.getInjector().getResourceService().refreshGoogleAccessToken(tmpRefreshToken, new SimpleAsyncCallback<GoogleToken>() {

				@Override
				public void onSuccess(GoogleToken result) {
//					StringUtil.consoleLog("refreshGoogleAccessToken : Success");
					final String access_token = result.getAccess_token() !=null && !result.getAccess_token().equalsIgnoreCase("") ? result.getAccess_token() : null;
//					StringUtil.consoleLog("access_token : Success : "+access_token);
					if (access_token !=null ){
						
						UserDo user = AppClientFactory.getLoggedInUser();
						user.setAccessToken(access_token);
						AppClientFactory.setLoggedInUser(user);
						
						
						try {
							AppClientFactory.getInjector().getResourceService().getGoogleDriveFilesList(folderId,nextPageToken,new SimpleAsyncCallback<GoogleDriveDo>() {
								@Override
								public void onSuccess(GoogleDriveDo googleDriveDo) {
									if(isPanelClear){
										getView().getPanelFileList().clear();
									}
									if(googleDriveDo!=null){
										if (googleDriveDo.getError()!=null && googleDriveDo.getError().getCode() == 401){
											getView().showNoDriveAccess(401);
										}else if (googleDriveDo.getError()!=null && googleDriveDo.getError().getCode()==403){
											getView().showNoDriveAccess(403);
										}else{
											getView().driveContentList(googleDriveDo);
										}
									}else{
										getView().showNoDriveAccess(401);
									}
								}
							});
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						getView().showNoDriveAccess(401);
					}
				}
			});
		}else{
			getView().showNoDriveAccess(401);
		}
		
	}
	
	
	public Label setLoadingPanel(){
		Label loadingImage=new Label();
		loadingImage.setStyleName("loadingpanelImage");
		//loadingImage.getElement().getStyle().setMarginLeft(70, Unit.PX);
		loadingImage.getElement().getStyle().setMarginTop(95, Unit.PX);
		return loadingImage;
	}
	
	public void setBreadCrumbLabel(String folderId,String folderTitle){
		getView().setBreadCrumbLabel(folderId,folderTitle);
	}

	
	@Override
	public void addResource(String idStr, String urlStr, String titleStr,
			String descriptionStr, String webResourceCategory,
			String thumbnailUrlStr, Integer endTime, String educationalUse,
			String momentsOfLearning, List<CodeDo> standards) {
		throw new RuntimeException("Not implemented");
	}

	
	@Override
	public void isShortenUrl(String userUrlStr) {
		throw new RuntimeException("Not implemented");
	}

	public AddResourcePresenter getAddResourcePresenter() {
		return addResourcePresenter;
	}

	public void setAddResourcePresenter(AddResourcePresenter addResourcePresenter) {
		this.addResourcePresenter = addResourcePresenter;
	}
	
	public void showAddResourceWidget(GoogleDriveItemDo googleDriveItemDo){
		if(addResourcePresenter!=null){
			addResourcePresenter.showAddWebResourceWidget(true, getView().getPanelFileList(),googleDriveItemDo);
		}
	}
	
}