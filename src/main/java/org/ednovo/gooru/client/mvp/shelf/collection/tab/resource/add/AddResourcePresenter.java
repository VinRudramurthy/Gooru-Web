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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add;
/**
* @fileName : AddResourcePresenter.java 
*
* @description :This file is a UiBinder for AddResourcePresenter.
*
* @version :5.1
*
* @date: Apr 6 2013
   	
* @Author  Gooru Team
* 
* @Reviewer 
*
*/


import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.application.shared.model.content.ExistsResourceDo;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.content.ResourceMetaInfoDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.drive.GoogleDriveItemDo;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.home.library.events.StandardPreferenceSettingEvent;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.DrivePresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.addquestion.QuestionTypePresenter;
import org.ednovo.gooru.client.mvp.shelf.event.AddResouceImageEvent;
import org.ednovo.gooru.client.mvp.shelf.event.InsertCollectionItemInAddResourceEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.mvp.standards.StandardsPopupPresenter;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.GooruConstants;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AddResourcePresenter extends PresenterWidget<IsAddResourceView> implements AddResourceUiHandlers{
	
	private ImageUploadPresenter imageUploadPresenter;
	
	StandardsPopupPresenter standardsPopupPresenter;
	
	private SimpleAsyncCallback<CollectionDo> collectionAsyncCallback;
	
	private SimpleAsyncCallback<CollectionItemDo> collectionItemAsyncCallback;
	
	private AsyncCallback<CollectionItemDo> userResourceCollectionItemAsyncCallback;
	
	private SimpleAsyncCallback<ResourceMetaInfoDo> resoureMetaInfoAsyncCallback;
	
	private SimpleAsyncCallback<ResourceMetaInfoDo> resoureMetaInfoImageAsyncCallback;
	
	private SimpleAsyncCallback<ExistsResourceDo> resoureCheckAsyncCallback;
	
	private SimpleAsyncCallback<CollectionItemDo> addQuestionResourceAsyncCallback;
	
	private SimpleAsyncCallback<CollectionItemDo> updateQuestionResourceAsyncCallback;
	
	private SimpleAsyncCallback<CollectionItemDo> v2UpdateQuestionResourceAsyncCallback;
	
	private SimpleAsyncCallback<Void> removeQuestionImageAsyncCallback; 
	
	private static final String KEY_OER = "resourceLicense";
	private static final String VAL_OER = "OER";
	private boolean isQuestionResource = false;
	private boolean isUserResource = false;
	
	private static final String USER_META_ACTIVE_FLAG = "0";
	
	private boolean isCCSSAvailable =false;
	private boolean isNGSSAvailable =false;
	private boolean isTEKSAvailable =false;
	private boolean isCAAvailable =false;
	
	
	private static final String O1_LEVEL = "o1";
	
	private static final String O2_LEVEL = "o2";
	
	private static final String O3_LEVEL = "o3";
	
	private static final String ID = "id";
	
	
	public SimpleAsyncCallback<CollectionItemDo> getAddQuestionResourceAsyncCallback() {
		return addQuestionResourceAsyncCallback;
	}

	public void setAddQuestionResourceAsyncCallback(
			SimpleAsyncCallback<CollectionItemDo> addQuestionResourceAsyncCallback) {
		this.addQuestionResourceAsyncCallback = addQuestionResourceAsyncCallback;
	}

	private DrivePresenter drivePresenter=null;

	public DrivePresenter getDrivePresenter() {
		return drivePresenter;
	}

	public void setDrivePresenter(DrivePresenter drivePresenter) {
		this.drivePresenter = drivePresenter;
	}

	@Inject QuestionTypePresenter questionTypePresenter;

	@Inject
	private ResourceServiceAsync resourceService;

	CollectionDo collectionDo;

	String clickType;

	@Inject
	public AddResourcePresenter(EventBus eventBus, IsAddResourceView view,ImageUploadPresenter imageUploadPresenter,DrivePresenter drivePresenter,QuestionTypePresenter questionTypePresenter,StandardsPopupPresenter standardsPopupPresenter) {
		super(eventBus, view);
		this.setImageUploadPresenter(imageUploadPresenter);
		this.questionTypePresenter=questionTypePresenter;
		this.standardsPopupPresenter=standardsPopupPresenter;
		getView().setUiHandlers(this);
		addRegisteredHandler(AddResouceImageEvent.TYPE, this);

		this.drivePresenter=drivePresenter;
	}

	@Override
	public void resourceImageUpload() {
		addToPopupSlot(imageUploadPresenter);
		imageUploadPresenter.setCollectionImage(false);
		imageUploadPresenter.setQuestionImage(false);
		imageUploadPresenter.setEditResourceImage(false);
		imageUploadPresenter.setUserOwnResourceImage(false);
		imageUploadPresenter.setEditUserOwnResourceImage(false);
		imageUploadPresenter.setAnswerImage(false);
		imageUploadPresenter.getView().isFromEditQuestion(true);
	}
	
	@Override
	public void userOwnResourceImageUpload() {
		addToPopupSlot(imageUploadPresenter);
		imageUploadPresenter.setCollectionImage(false);
		imageUploadPresenter.setQuestionImage(false);
		imageUploadPresenter.setEditResourceImage(false);
		imageUploadPresenter.setEditUserOwnResourceImage(false);
		imageUploadPresenter.setUserOwnResourceImage(true);
		imageUploadPresenter.setAnswerImage(false);
		imageUploadPresenter.getView().isFromEditQuestion(true);
	}
	@Override
	public void questionImageUpload() {
		addToPopupSlot(imageUploadPresenter);
		imageUploadPresenter.setCollectionImage(false);
		imageUploadPresenter.setQuestionImage(true);
		imageUploadPresenter.setEditResourceImage(false);
		imageUploadPresenter.setUserOwnResourceImage(false);
		imageUploadPresenter.setEditUserOwnResourceImage(false);
		imageUploadPresenter.setAnswerImage(false);
		imageUploadPresenter.getView().isFromEditQuestion(true);
	}
	
	@Override
	public void questionImageUpload(String collectionItemId) {
		addToPopupSlot(imageUploadPresenter);
		imageUploadPresenter.setCollectionImage(false);
		imageUploadPresenter.setUpdateQuestionImage(true);
		imageUploadPresenter.setCollectionItemId(collectionItemId);
		imageUploadPresenter.setEditResourceImage(false);
        imageUploadPresenter.setQuestionImage(false);
        imageUploadPresenter.setUserOwnResourceImage(false);
        imageUploadPresenter.setEditUserOwnResourceImage(false);
        imageUploadPresenter.setAnswerImage(false);
        imageUploadPresenter.getView().isFromEditQuestion(true);
	}
	@Override
	public void showStandardsPopup(String standardVal, String standardsDesc,List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
		Window.enableScrolling(false);
		standardsPopupPresenter.callStandardsBasedonTypeService(standardVal,standardsDesc);
		standardsPopupPresenter.setAddResourcePresenter(this);
		standardsPopupPresenter.setAlreadySelectedItems(collectionLiPanelWithCloseArray);
		addToPopupSlot(standardsPopupPresenter);
	}

	public ImageUploadPresenter getImageUploadPresenter() {
		return imageUploadPresenter;
	}

	public void setImageUploadPresenter(ImageUploadPresenter imageUploadPresenter) {
		this.imageUploadPresenter = imageUploadPresenter;
	}
	@Override
	public void setResourceImageUrl(String fileName,String fileNameWithoutRepository,boolean isQuestionImage,boolean isUserOwnResourceImage) {
	    getView().setImageUrl(fileName,fileNameWithoutRepository,isQuestionImage,isUserOwnResourceImage);
	}

	@Override
	protected void onBind() {
		super.onBind();
		setUserResourceCollectionItemAsyncCallback(new SimpleAsyncCallback<CollectionItemDo>() {
			@Override
			public void onSuccess(CollectionItemDo result) {
				getView().hide();
				AppClientFactory.fireEvent(new InsertCollectionItemInAddResourceEvent(result, RefreshType.INSERT));
			}
			@Override
			public void onFailure(Throwable caught) {
				super.onFailure(caught);
				Window.enableScrolling(true);
			}
		});

		setCollectionItemAsyncCallback(new SimpleAsyncCallback<CollectionItemDo>() {
			@Override
			public void onSuccess(CollectionItemDo result) {
				getView().hide();
				AppClientFactory.fireEvent(new InsertCollectionItemInAddResourceEvent(result, RefreshType.INSERT));
				/**
				 * Dis-abled for 6.5 release
				 */
				MixpanelUtil.AddResourceByUrl();
			}
		});
		setResoureMetaInfoAsyncCallback(new SimpleAsyncCallback<ResourceMetaInfoDo>() {

			@Override
			public void onSuccess(ResourceMetaInfoDo result) {
				getView().setNewResourcePopupData(result);

			}
		});
		
		setResoureMetaInfoImageAsyncCallback(new SimpleAsyncCallback<ResourceMetaInfoDo>() {

			@Override
			public void onSuccess(ResourceMetaInfoDo result) {
				getView().setPopupImageData(result);

			}
		});
		
		setResoureCheckAsyncCallback(new SimpleAsyncCallback<ExistsResourceDo>() {
			@Override
			public void onSuccess(ExistsResourceDo result) {
				
				if (result.getSharing()!=null)
					if(GooruConstants.PUBLIC.equals(result.getSharing())){
						getView().setExistingResourceData(result, getCollectionDo());
					}
					
			}
		});
		setAddQuestionResourceAsyncCallback(new SimpleAsyncCallback<CollectionItemDo>() {
            @Override
            public void onSuccess(CollectionItemDo result) {
            		getView().hide();
            		AppClientFactory.fireEvent(new InsertCollectionItemInAddResourceEvent(result, RefreshType.INSERT));
            		/**
    				 *  dis-abled tagging Quest resource for 6.5 release
    				 */
                    MixpanelUtil.AddQuestion();
            }
            @Override
            public void onFailure(Throwable caught) {
            	// TODO Auto-generated method stub
            	super.onFailure(caught);
            	AppClientFactory.getClientLogger().log(Level.SEVERE, caught.getMessage(), caught);
            }
		});
		setRemoveQuestionImageAsyncCallback(new SimpleAsyncCallback<Void>(){
			@Override
			public void onSuccess(Void result) {
				getView().removeQuestionEditImage();
			}
		});
		setUpdateQuestionResourceAsyncCallback(new SimpleAsyncCallback<CollectionItemDo>() {
            @Override
            public void onSuccess(CollectionItemDo result) {
            		getView().hide();
                    MixpanelUtil.AddQuestion();
            }
		});

		setV2UpdateQuestionResourceAsyncCallback(new SimpleAsyncCallback<CollectionItemDo>() {
            @Override
            public void onSuccess(CollectionItemDo result) {
            		getView().hide();
                    MixpanelUtil.AddQuestion();
                    AppClientFactory.fireEvent(new InsertCollectionItemInAddResourceEvent(result, RefreshType.UPDATE));
            }
		});
	}
	native void redirect(String url)
    /*-{
            $wnd.location.reload();
    }-*/;
	
	public void addResource(String idStr, String urlStr,String titleStr, String descriptionStr, String categoryStr, String thumbnailImgSrcStr, Integer endTime,String edcuationalUse,String momentsOfLearning,List<CodeDo> standards,List<StandardFo> centurySkills, String hostName,List<String> tagList,Map<String,List<Integer>> hazardsMap,String mediaType) {
		getResourceService().addNewResource("", collectionDo.getGooruOid(), urlStr, titleStr, descriptionStr, categoryStr, thumbnailImgSrcStr, endTime,edcuationalUse,momentsOfLearning,standards,centurySkills,hostName,tagList,hazardsMap,mediaType, getCollectionItemAsyncCallback());

	}
	
	@Override
	public void getResourceMetaInfo(String url){
		getResourceService().getResourceMetaInfo(url, resoureMetaInfoAsyncCallback);
	}
	@Override
	public void getResourceImageInfo(String url) {
		getResourceService().getResourceMetaInfo(url, resoureMetaInfoImageAsyncCallback);
	}
	@Override
	public void getResourceExists(String url){
		getResourceService().checkResourceExists(url, resoureCheckAsyncCallback);
	}
	
	public SimpleAsyncCallback<ResourceMetaInfoDo> getResoureMetaInfoAsyncCallback() {
		return resoureMetaInfoAsyncCallback;
	}
	public SimpleAsyncCallback<ResourceMetaInfoDo> getResoureMetaInfoImageAsyncCallback() {
		return resoureMetaInfoImageAsyncCallback;
	}
	public void setResoureMetaInfoImageAsyncCallback(
			SimpleAsyncCallback<ResourceMetaInfoDo> resoureMetaInfoImageAsyncCallback) {
		this.resoureMetaInfoImageAsyncCallback = resoureMetaInfoImageAsyncCallback;
	}
	public void setResoureMetaInfoAsyncCallback(
			SimpleAsyncCallback<ResourceMetaInfoDo> resoureMetaInfoAsyncCallback) {
		this.resoureMetaInfoAsyncCallback = resoureMetaInfoAsyncCallback;
	}

	public SimpleAsyncCallback<CollectionDo> getCollectionAsyncCallback() {
		return collectionAsyncCallback;
	}

	public void setCollectionAsyncCallback(SimpleAsyncCallback<CollectionDo> collectionAsyncCallback) {
		this.collectionAsyncCallback = collectionAsyncCallback;
	}
	
	public ResourceServiceAsync getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceServiceAsync resourceService) {
		this.resourceService = resourceService;
	}
	public CollectionDo getCollectionDo() {
		return collectionDo;
	}
	public void setCollectionDo(CollectionDo collectionDo) {
		
		this.collectionDo = collectionDo;
	}
	public void setCollectionItemDo(CollectionItemDo collectionItemDo){
		getView().setCollectionItemDo(collectionItemDo);
	}
	
	public SimpleAsyncCallback<CollectionItemDo> getCollectionItemAsyncCallback() {
		return collectionItemAsyncCallback;
	}

	public void setCollectionItemAsyncCallback(SimpleAsyncCallback<CollectionItemDo> collectionItemAsyncCallback) {
		this.collectionItemAsyncCallback = collectionItemAsyncCallback;
	}
	
	public SimpleAsyncCallback<ExistsResourceDo> getResoureCheckAsyncCallback() {
		return resoureCheckAsyncCallback;
	}

	public void setResoureCheckAsyncCallback(SimpleAsyncCallback<ExistsResourceDo> resoureCheckAsyncCallback) {
		this.resoureCheckAsyncCallback = resoureCheckAsyncCallback;
	}

	@Override
	public void addQeustionResource(String mediaFileName, CollectionQuestionItemDo collectionQuestionItemDo) {
		getResourceService().addQuestionResource(collectionDo.getGooruOid(), mediaFileName, collectionQuestionItemDo, getAddQuestionResourceAsyncCallback());
	}

	@Override
	public CollectionDo getParentCollectionDetails() {
		return this.collectionDo;
	}

	public void setCollectionDoAndType(CollectionDo collectionDo, String clickType) {
		this.collectionDo = collectionDo;
		this.clickType=clickType;
		getView().setCollectionDo(collectionDo);
		getView().setPopup(clickType);
		getDepthOfKnowledges();
	}

	@Override
	public void isShortenUrl(final String userUrlStr) {
		AppClientFactory.getInjector().getResourceService().checkShortenUrl(userUrlStr.trim(),new SimpleAsyncCallback<String>(){
			@Override
			public void onSuccess(String result) {
				if(result.equalsIgnoreCase("True")){
					getView().setShortenUrlAlertMsg();
				}else{
					getView().getResourceMetaInfo(userUrlStr);
				}
				
			}
			
		});
	}
	@Override
	public void onHide(){
		super.onHide();
		getView().closePopUp();
	}

	@Override
	public void userOwnResourceUpload() {
		addToPopupSlot(imageUploadPresenter);
		
	}

	@Override
	public void addUserOwnResource(String jsonString) {
		MixpanelUtil.Add_Resource_Computer_Success();
		getResourceService().addNewUserResource(jsonString, collectionDo.getGooruOid(), getUserResourceCollectionItemAsyncCallback());
	}

	public AsyncCallback<CollectionItemDo> getUserResourceCollectionItemAsyncCallback() {
		return userResourceCollectionItemAsyncCallback;
	}

	public void setUserResourceCollectionItemAsyncCallback(AsyncCallback<CollectionItemDo> asyncCallback) {
		this.userResourceCollectionItemAsyncCallback = asyncCallback;
	}

	@Override
	public void saveUserResource(String filePath) {
		AppClientFactory.getInjector().getResourceService().saveUserOwnResource(filePath,new SimpleAsyncCallback<MediaUploadDo>() {

			@Override
			public void onSuccess(MediaUploadDo result) {
				getView().uploadResource(result);
			}
		});
	}
	

	@Override
	public void removeQuestionImage(String collectionItemId) {
		getResourceService().removeQuestionImage(collectionItemId, getRemoveQuestionImageAsyncCallback());
	}

	public SimpleAsyncCallback<Void> getRemoveQuestionImageAsyncCallback() {
		return removeQuestionImageAsyncCallback;
	}

	public void setRemoveQuestionImageAsyncCallback(
			SimpleAsyncCallback<Void> removeQuestionImageAsyncCallback) {
		this.removeQuestionImageAsyncCallback = removeQuestionImageAsyncCallback;
	}

	public SimpleAsyncCallback<CollectionItemDo> getUpdateQuestionResourceAsyncCallback() {
		return updateQuestionResourceAsyncCallback;
	}

	private SimpleAsyncCallback<CollectionItemDo> getV2UpdateQuestionResourceAsyncCallback() {
		return v2UpdateQuestionResourceAsyncCallback;
	}
	
	public void setUpdateQuestionResourceAsyncCallback(
			SimpleAsyncCallback<CollectionItemDo> updateQuestionResourceAsyncCallback) {
		this.updateQuestionResourceAsyncCallback = updateQuestionResourceAsyncCallback;
	}

	public void setV2UpdateQuestionResourceAsyncCallback(
			SimpleAsyncCallback<CollectionItemDo> v2UpdateQuestionResourceAsyncCallback) {
		this.v2UpdateQuestionResourceAsyncCallback = v2UpdateQuestionResourceAsyncCallback;
	}
	
	@Override
	public void showDriveResoureView(HTMLPanel tabContainer) {
			drivePresenter.setAddResourcePresenter(this);
			drivePresenter.getGoogleDriveFiles(null, null, true);
			drivePresenter.setBreadCrumbLabel(null,null);
		tabContainer.add(drivePresenter.getWidget());
		tabContainer.getElement().setId("pnlTabViewContainer");
	}
	
	public void showAddWebResourceWidget(boolean isGoogleDriveFile,FlowPanel googleDriveContainer,GoogleDriveItemDo googleDriveItemDo){
		googleDriveContainer.clear();
		getView().showAddWebResourceWidget(isGoogleDriveFile,googleDriveContainer,googleDriveItemDo);
	}
	@Override
	public void v2UpdateQuestionResource(CollectionItemDo collectionItemDo,CollectionQuestionItemDo collectionQuestionItemDo, String thumbnailUrl) {
		getResourceService().v2UpdateQuestionResource(collectionItemDo, collectionQuestionItemDo,thumbnailUrl, getV2UpdateQuestionResourceAsyncCallback());
	}

	@Override
	public void addSelectedQuestionType(String type,CollectionQuestionItemDo collectionQuestionItemDo) {
		if(type.equalsIgnoreCase("HS_TXT") || type.equalsIgnoreCase("HS_IMG")){
		questionTypePresenter.getView().resetFields(type);
		questionTypePresenter.getView().getAddStandards();
		if(getView().checkQuestionSlot()){
		addToSlot(SLOT_QUESTION_TYPE, questionTypePresenter);
		}
		questionTypePresenter.ImageUpload(imageUploadPresenter,getView(),collectionDo);
		
		if(collectionQuestionItemDo!=null){
			questionTypePresenter.getView().setMetadata(collectionQuestionItemDo);
		}
		
		}else {
			CollectionQuestionItemDo questionMetadataDo=null;
			if(!getView().checkQuestionSlot()){
			 questionMetadataDo=questionTypePresenter.getView().getMetadata();
			}
			clearSlot(SLOT_QUESTION_TYPE);
			getView().clearQuestionSlot();
			getView().questionMetadata(questionMetadataDo);
		}
	}

	@Override
	public void setEditQuestionData(CollectionItemDo collectionItemDo) {
		
		questionTypePresenter.getView().editQuestion(collectionItemDo);
	}

	@Override
	public void setHSEditData() {
		questionTypePresenter.getView().setEditData();
	}
	@Override
	public void getDepthOfKnowledges(){
		AppClientFactory.getInjector().getfolderService().getDepthOfKnowledgesList(new SimpleAsyncCallback<List<ListValuesDo>>() {
			@Override
			public void onSuccess(List<ListValuesDo> result) {
				getView().setDepthOfKnowledges(result);
			}
		});
	}

   	public void setSelectedStandards(List<Map<String,String>> standListArray){
   		getView().displaySelectedStandards(standListArray);
   	}
}

