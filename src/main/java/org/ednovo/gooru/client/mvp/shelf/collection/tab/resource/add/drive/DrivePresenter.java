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
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.client.AppPlaceKeeper;
import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.gin.BasePlacePresenter;
import org.ednovo.gooru.client.mvp.classpages.edit.EditClasspageCBundle;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.event.DriveEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.event.DriveEventHandler;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.event.FolderEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.event.FolderEventHandlers;
import org.ednovo.gooru.shared.model.code.CodeDo;
import org.ednovo.gooru.shared.model.drive.GoogleDriveDo;
import org.ednovo.gooru.shared.model.drive.GoogleDriveItemDo;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class DrivePresenter extends
		BasePlacePresenter<IsDriveView, DrivePresenter.IsDriveyProxy> implements
		DriveUiHandlers {

	
	protected static ImageUploadPresenter imageUploadPresenter;
	
	@ProxyCodeSplit
	@NameToken(PlaceTokens.DRIVE)
	@UseGatekeeper(AppPlaceKeeper.class)
	public interface IsDriveyProxy extends ProxyPlace<DrivePresenter> {
	}

	@Inject
	public DrivePresenter(IsDriveView view, IsDriveyProxy proxy,ImageUploadPresenter imageUploadPresenter) {
		super(view, proxy);
		getView().setUiHandlers(this);
		this.setImageUploadPresenter(imageUploadPresenter);
		addRegisteredHandler(DriveEvent.TYPE, driveEvent);
		addRegisteredHandler(FolderEvent.TYPE, folderEvent);

		
	}

	@Override
	public void onBind() {
		super.onBind();
	}

	@Override
	public void onReveal() {
		super.onReveal();
		System.out.println("reveal in drive");
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

	@Override
	public Map<String, Object> redirect() {
		// TODO Auto-generated method stub
		return null;
	}

	DriveEventHandler driveEvent = new DriveEventHandler() {

		@Override
		public void clearDrivepage(GoogleDriveItemDo driveDo) {
			getView().getDriveDetails(driveDo);
			// TODO Auto-generated method stub

		}
	};

	FolderEventHandlers folderEvent = new FolderEventHandlers() {

		@Override
		public void clearFolderpage(String title, String id,
				List<GoogleDriveItemDo> result) {
			getView().getFolderDetails(title, id, result);
		}

	};

	@Override
	public void getdriveListAgain() {
//		AppClientFactory.getInjector().getResourceService()
//				.getDrive(new AsyncCallback<List<GoogleDriveItemDo>>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//
//					}
//
//					@Override
//					public void onSuccess(List<GoogleDriveItemDo> result) {
//						getView().driveContentList(result);
//						// TODO Auto-generated method stub
//
//					}
//
//				});

	}

	public void getGoogleDriveFiles(String folderId,String nextPageToken,final boolean isPanelClear) {
		if(isPanelClear){
			getView().getPanelFileList().clear();
			getView().getPanelFileList().add(setLoadingPanel());
		}
		AppClientFactory.getInjector().getResourceService().getGoogleDriveFilesList(folderId,nextPageToken,new SimpleAsyncCallback<GoogleDriveDo>() {
			@Override
			public void onSuccess(GoogleDriveDo googleDriveDo) {
				if(isPanelClear){
					getView().getPanelFileList().clear();
				}
				if (googleDriveDo==null){
					getView().showNoDriveAccess(401);
				}else if (googleDriveDo.getItems() !=null && googleDriveDo.getItems().size() <=0){
					getView().showNoDriveAccess(404);
				}else if (googleDriveDo.getError() != null && googleDriveDo.getError().getCode() == 401){
					getView().showNoDriveAccess(401);
				}else if (googleDriveDo.getError() != null && googleDriveDo.getError().getCode() == 403){
					getView().showNoDriveAccess(403);
				}else if (googleDriveDo.getError() != null && googleDriveDo.getError().getCode() == 401){
					getView().showNoDriveAccess(401);
				}else{
					getView().driveContentList(googleDriveDo);
				}
			}
		});
	}
	@Override
	public void resourceImageUpload() {
		addToPopupSlot(imageUploadPresenter);
		imageUploadPresenter.setCollectionImage(false);
		imageUploadPresenter.setQuestionImage(false);
		imageUploadPresenter.setEditResourceImage(false);
		imageUploadPresenter.setUserOwnResourceImage(false);
		imageUploadPresenter.setEditUserOwnResourceImage(false);
		imageUploadPresenter.getView().isFromEditQuestion(true);
	}
	
	public void setImageUploadPresenter(ImageUploadPresenter imageUploadPresenter) {
		this.imageUploadPresenter = imageUploadPresenter;
	}
	
	public Label setLoadingPanel(){
		Label loadingImage=new Label();
		EditClasspageCBundle.INSTANCE.css().ensureInjected();
		loadingImage.setStyleName(EditClasspageCBundle.INSTANCE.css().loadingpanelImage());
		loadingImage.getElement().getStyle().setMarginLeft(70, Unit.PX);
		loadingImage.getElement().getStyle().setMarginTop(25, Unit.PX);
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
}