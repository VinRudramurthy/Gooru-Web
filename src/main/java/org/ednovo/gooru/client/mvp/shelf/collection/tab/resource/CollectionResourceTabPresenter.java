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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource;

import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddResourcePresenter;
import org.ednovo.gooru.client.mvp.shelf.event.InsertCollectionItemInAddResourceEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.mvp.shelf.event.UpdateEditResourceImageEvent;
import org.ednovo.gooru.client.mvp.shelf.event.UpdateQuestionImageEvent;
import org.ednovo.gooru.client.service.ResourceServiceAsync;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.model.content.CollectionDo;
import org.ednovo.gooru.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.shared.model.content.CollectionQuestionItemDo;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author Search Team
 *
 */
public class CollectionResourceTabPresenter extends PresenterWidget<IsCollectionResourceTabView> implements CollectionResourceTabUiHandlers {
	/**
	 * Class constructor
	 * 
	 * @param eventBus {@link EventBus}
	 * @param view {@link View}
	 */
	
	@Inject
	private ResourceServiceAsync resourceService;
	private SimpleAsyncCallback<CollectionItemDo> updateQuestionItemResourceAsyncCallback;
	
	private SimpleAsyncCallback<CollectionItemDo> updateResourceItemAsyncCallback;
	
	private SimpleAsyncCallback<Void> removeImageAsyncCallback;
	
	private SimpleAsyncCallback<Void> updateQuestionImageAsyncCallback;
	
	public SimpleAsyncCallback<Void> getUpdateQuestionImageAsyncCallback() {
		if (updateQuestionImageAsyncCallback == null) {
			updateQuestionImageAsyncCallback = new SimpleAsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
				
				}
			};
		}
		return updateQuestionImageAsyncCallback;
	}

	public void setUpdateQuestionImageAsyncCallback(
			SimpleAsyncCallback<Void> updateQuestionImageAsyncCallback) {
		this.updateQuestionImageAsyncCallback = updateQuestionImageAsyncCallback;
	}

	public SimpleAsyncCallback<Void> getRemoveImageAsyncCallback() {
		
		if (removeImageAsyncCallback == null) {
			removeImageAsyncCallback = new SimpleAsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
			
					getView().removeUpdateQuestionView();
				}
			};
		}
		return removeImageAsyncCallback;
	}

	public void setRemoveImageAsyncCallback(
			SimpleAsyncCallback<Void> removeImageAsyncCallback) {
		this.removeImageAsyncCallback = removeImageAsyncCallback;
	}
	ImageUploadPresenter imgUploadPresenter=null;
	AddResourcePresenter addResourcePresenter=null;
	
	CollectionDo collectionDo;
	
	@Inject
	public CollectionResourceTabPresenter(EventBus eventBus, IsCollectionResourceTabView view, ImageUploadPresenter imgUpload,AddResourcePresenter addResourcePresenter) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.imgUploadPresenter = imgUpload;
		this.addResourcePresenter = addResourcePresenter;
		addRegisteredHandler(UpdateQuestionImageEvent.TYPE, this);
		addRegisteredHandler(UpdateEditResourceImageEvent.TYPE, this);
		addRegisteredHandler(InsertCollectionItemInAddResourceEvent.TYPE, this);
	}

	@Override
	public void onBind() {
		super.onBind();
		
	}

	@Override
	public void onReveal() {
		super.onReveal();
		getView().onLoad();
		getView().reset();
	}

	@Override
	protected void onHide() {
		super.onHide();
		getView().onUnload();
		getView().closeAllOpenedPopUp();
	}
	
	public ResourceServiceAsync getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceServiceAsync resourceService) {
		this.resourceService = resourceService;
	}
	
    @Override
    public void addResourcePopup(CollectionDo collectionDo, String clickType) {
    	
    	addResourcePresenter.setCollectionDo(collectionDo);
    	addResourcePresenter.setCollectionDoAndType(collectionDo, clickType);
        addToPopupSlot(addResourcePresenter);
        
    }

	@Override
	public void updateQuestionResource(String questionItemId,CollectionQuestionItemDo collectionQuestionItemDo,String thumbnailUrl) {
		//getResourceService().updateQuestionResource(questionItemId, collectionQuestionItemDo,thumbnailUrl, getUpdateQuestionItemResourceAsyncCallback());
		
	}
	
	public SimpleAsyncCallback<CollectionItemDo> getUpdateQuestionItemResourceAsyncCallback() {
		if (updateQuestionItemResourceAsyncCallback == null) {
			updateQuestionItemResourceAsyncCallback = new SimpleAsyncCallback<CollectionItemDo>() {

				@Override
				public void onSuccess(CollectionItemDo result) {
					getView().hideUpdateResourceQuestionPopup();
					//getView().updateData(result);
				}
			};
		}
		return updateQuestionItemResourceAsyncCallback;
	}

	public void setUpdateQuestionItemResourceAsyncCallback(
			SimpleAsyncCallback<CollectionItemDo> updateQuestionItemResourceAsyncCallback) {
		this.updateQuestionItemResourceAsyncCallback = updateQuestionItemResourceAsyncCallback;
	}

	@Override
	public void setUpdateQuestionImageUrl(String fileName,String fileNameWithOutRepository) {
	    getView().updateCollectionItemImage(fileName,fileNameWithOutRepository);
		
	}

	@Override
	public void updateQustionImage(String collectionItemId) {
		addToPopupSlot(imgUploadPresenter);
        imgUploadPresenter.setCollectionImage(false);
        imgUploadPresenter.setUpdateQuestionImage(true);
        imgUploadPresenter.setCollectionItemId(collectionItemId);
        imgUploadPresenter.setEditResourceImage(false);
		
	}

	@Override
	public void updateResourceInfo(CollectionItemDo collectionItemDo) {
		getResourceService().updateResourceInfo(collectionItemDo, getUpdateResourceItemAsyncCallback());
	}
	
	@Override
	public void removeQuestionImage(String collectionQuestionId) {
		getResourceService().removeQuestionImage(collectionQuestionId, getRemoveImageAsyncCallback());
	}
	
	
	/** 
	 * This method is to get the updateResourceItemAsyncCallback
	 */
	public SimpleAsyncCallback<CollectionItemDo> getUpdateResourceItemAsyncCallback() {
		if (updateResourceItemAsyncCallback == null) {
			updateResourceItemAsyncCallback = new SimpleAsyncCallback<CollectionItemDo>() {

				@Override
				public void onSuccess(CollectionItemDo result) {
					getView().hideUpdateResourcePopup();
					redirect(Window.Location.getHref());
				}
			};
		}
		return updateResourceItemAsyncCallback;
	}

	/** 
	 * This method is to set the updateResourceItemAsyncCallback
	 */
	public void setUpdateResourceItemAsyncCallback(
			SimpleAsyncCallback<CollectionItemDo> updateResourceItemAsyncCallback) {
		this.updateResourceItemAsyncCallback = updateResourceItemAsyncCallback;
	}
	native void redirect(String url)
    /*-{
            $wnd.location.reload();
    }-*/;
	@Override
	public void setUpdateResourceImageUrl(String fileName, String fileNameWithOutRespUrl, boolean isEditUserOwnResourceImage) {
	    getView().updateResouceItemImage(fileName, fileNameWithOutRespUrl,isEditUserOwnResourceImage);
		
	}

	@Override
	public void imageEditResourceUpload() {
		 addToPopupSlot(imgUploadPresenter);
         imgUploadPresenter.setEditResourceImage(true);
         imgUploadPresenter.setCollectionImage(false);
         imgUploadPresenter.setQuestionImage(false);
	}
	
	@Override
	public void imageEditUserOwnResourceUpload() {
		 addToPopupSlot(imgUploadPresenter);
		 imgUploadPresenter.setEditUserOwnResourceImage(true);
         imgUploadPresenter.setEditResourceImage(false);
         imgUploadPresenter.setCollectionImage(false);
         imgUploadPresenter.setQuestionImage(false);
         imgUploadPresenter.getView().isFromEditQuestion(true);
	}

	@Override
	public void insertCollectionItemInAddResource(
			CollectionItemDo collectionItem, RefreshType refreshType) {
		getView().insertCollectionItemInAddResource(collectionItem, refreshType);
	}
	
	@Override
	public void showEditQuestionResourcePopup(CollectionItemDo collectionItemDo) {
		 addResourcePresenter.setCollectionItemDo(collectionItemDo);
		 addResourcePresenter.setCollectionDoAndType(null, "QuestionEdit");
		 addToPopupSlot(addResourcePresenter);
	}

	@Override
	public void editUserOwnResource(String jsonString, String gooruOid) {
		MixpanelUtil.Resource_Edit_Info_Success();
		AppClientFactory.getInjector().getResourceService().updateUserOwnResource(jsonString,gooruOid,new SimpleAsyncCallback<CollectionItemDo>(){

			@Override
			public void onSuccess(CollectionItemDo result) {
				getView().hideUpdateOwnResourcePopup();
				redirect(Window.Location.getHref());
			}
			
		});
	}

	
	
}
