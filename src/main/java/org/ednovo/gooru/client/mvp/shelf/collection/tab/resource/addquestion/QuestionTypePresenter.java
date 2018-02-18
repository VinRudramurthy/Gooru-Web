package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.addquestion;

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.IsAddResourceView;
import org.ednovo.gooru.client.mvp.shelf.event.AddResouceImageEvent;
import org.ednovo.gooru.client.mvp.shelf.event.InsertCollectionItemInAddResourceEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshCollectionItemInShelfListEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.mvp.standards.StandardsPopupPresenter;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author Hari
 *
 */
public class QuestionTypePresenter extends PresenterWidget<IsQuestionTypeView> implements QuestionTypeUiHandlers {

	private ImageUploadPresenter imageUploadPresenter;
	private IsAddResourceView isAddResourceView;
	private SimpleAsyncCallback<Void> removeQuestionImageAsyncCallback;
	@Inject
	private ResourceServiceAsync resourceService;

	private SimpleAsyncCallback<CollectionItemDo> v2UpdateQuestionResourceAsyncCallback;
	private SimpleAsyncCallback<CollectionItemDo> addQuestionResourceAsyncCallback;

	CollectionDo collectionDo;

	private boolean isQuestionResource = false;
	private boolean isUserResource = false;

	StandardsPopupPresenter standardsPopupPresenter;
	/**
	 * Class Constructor
	 * @param eventBus {@link EventBus}
	 * @param view {@link View}
	 */
	@Inject
	public QuestionTypePresenter(EventBus eventBus, IsQuestionTypeView view,StandardsPopupPresenter standardsPopupPresenter) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		addRegisteredHandler(AddResouceImageEvent.TYPE, this);
		this.standardsPopupPresenter = standardsPopupPresenter;
	}

	@Override
	public void onBind() {
		super.onBind();

		setAddQuestionResourceAsyncCallback(new SimpleAsyncCallback<CollectionItemDo>() {
            @Override
            public void onSuccess(CollectionItemDo result) {
            	 hidePopup();
            	AppClientFactory.fireEvent(new RefreshCollectionItemInShelfListEvent(
            			result, RefreshType.INSERT));
        		AppClientFactory.fireEvent(new InsertCollectionItemInAddResourceEvent(
        				result, RefreshType.INSERT));
                    MixpanelUtil.AddQuestion();
            }
		});
		
		setV2UpdateQuestionResourceAsyncCallback(new SimpleAsyncCallback<CollectionItemDo>() {
            @Override
            public void onSuccess(CollectionItemDo result) {
            	      hidePopup();
            	      if(result != null)
            			{
            			AppClientFactory.fireEvent(new InsertCollectionItemInAddResourceEvent(result, RefreshType.UPDATE));
            			}
                    MixpanelUtil.AddQuestion();
            }
		});
		
		setRemoveQuestionImageAsyncCallback(new SimpleAsyncCallback<Void>(){
			@Override
			public void onSuccess(Void result) {
				getView().removeQuestionEditImage();
			}	
		});
		
	}

	public ResourceServiceAsync getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceServiceAsync resourceService) {
		this.resourceService = resourceService;
	}

	public SimpleAsyncCallback<Void> getRemoveQuestionImageAsyncCallback() {
		return removeQuestionImageAsyncCallback;
	}

	public void setRemoveQuestionImageAsyncCallback(
			SimpleAsyncCallback<Void> removeQuestionImageAsyncCallback) {
		this.removeQuestionImageAsyncCallback = removeQuestionImageAsyncCallback;
	}


	public void ImageUpload(ImageUploadPresenter imageUploadPresenter,IsAddResourceView view,CollectionDo collectionDo){
		this.imageUploadPresenter=imageUploadPresenter;
		this.isAddResourceView =view;
		this.collectionDo=collectionDo;
	}


	@Override
	protected void onReveal() {
		super.onReveal();
		getView().getRevealType();
	}

	@Override
	public void setResourceImageUrl(String fileName,String fileNameWithoutRepository,boolean isQuestionImage,boolean isUserOwnResourceImage) {
		getView().setImageUrl(fileName,fileNameWithoutRepository,isQuestionImage,isUserOwnResourceImage);
	}

	@Override
	public void removeQuestionImage(String collectionItemId) {
		getResourceService().removeQuestionImage(collectionItemId, getRemoveQuestionImageAsyncCallback());
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
	public void answerImageUpload() {
		addToPopupSlot(imageUploadPresenter);
		imageUploadPresenter.setCollectionImage(false);
		imageUploadPresenter.setEditResourceImage(false);
		imageUploadPresenter.setEditUserOwnResourceImage(false);
		imageUploadPresenter.setQuestionImage(false);
		imageUploadPresenter.setUpdateQuestionImage(false);
		imageUploadPresenter.setProfileImage(false);
		imageUploadPresenter.setPublicProfileImage(false);
		imageUploadPresenter.setUserOwnResourceImage(false);
		imageUploadPresenter.setUdateProfileImage(false);
		imageUploadPresenter.getView().isFromEditQuestion(false);
		imageUploadPresenter.setAnswerImage(true);
		
		
	}

	
 	public void setSelectedStandards(List<Map<String,String>> standListArray){
   		getView().displaySelectedStandards(standListArray);
   	}
	@Override
	public void showStandardsPopup(String standardVal, String standardsDesc,List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
		Window.enableScrolling(false);
		standardsPopupPresenter.callStandardsBasedonTypeService(standardVal,standardsDesc);
		standardsPopupPresenter.setQuestionTypePresenter(this);
		standardsPopupPresenter.setAlreadySelectedItems(collectionLiPanelWithCloseArray);
		addToPopupSlot(standardsPopupPresenter);
	}

	@Override
	public void hidePopup() {
		isAddResourceView.hidePopup();
	}

	@Override
	public void removeAnswerImage(String collectionItemId) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void answerImageUpload(String collectionItemId) {
		throw new RuntimeException("Not implemented");
	}

	private SimpleAsyncCallback<CollectionItemDo> getV2UpdateQuestionResourceAsyncCallback() {
		return v2UpdateQuestionResourceAsyncCallback;
	}

	public void setV2UpdateQuestionResourceAsyncCallback(
			SimpleAsyncCallback<CollectionItemDo> v2UpdateQuestionResourceAsyncCallback) {
		this.v2UpdateQuestionResourceAsyncCallback = v2UpdateQuestionResourceAsyncCallback;
	}

	public SimpleAsyncCallback<CollectionItemDo> getAddQuestionResourceAsyncCallback() {
		return addQuestionResourceAsyncCallback;
	}

	public void setAddQuestionResourceAsyncCallback(
			SimpleAsyncCallback<CollectionItemDo> addQuestionResourceAsyncCallback) {
		this.addQuestionResourceAsyncCallback = addQuestionResourceAsyncCallback;
	}


	@Override
	public void v2UpdateQuestionResource(CollectionItemDo collectionItemDo,CollectionQuestionItemDo collectionQuestionItemDo,	String thumbnailUrl) {
		getResourceService().v2UpdateQuestionResource(collectionItemDo, collectionQuestionItemDo,thumbnailUrl, getV2UpdateQuestionResourceAsyncCallback());
	}

	@Override
	public void addHSQuestionResource(String mediaFileName,CollectionQuestionItemDo collectionQuestionItemDo) {

		getResourceService().addQuestionResource(collectionDo.getGooruOid(), mediaFileName, collectionQuestionItemDo, getAddQuestionResourceAsyncCallback());

	}

	@Override
	public void browseStandardsInfo(boolean isQuestion, boolean isUserResource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUpdatedBrowseStandards() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeStandardsPopup() {
		// TODO Auto-generated method stub
		
	}




}