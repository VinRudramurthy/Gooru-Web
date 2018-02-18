package org.ednovo.gooru.client.mvp.play.collection.flag;
import java.util.ArrayList;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.PlayerAppServiceAsync;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.ContentReportDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.play.collection.CollectionPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.end.CollectionEndPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

public class CollectionFlagPresenter extends PresenterWidget<IsCollectionFlagView> implements CollectionFlagUiHandler{
	
	String deleteContentGooruOid;
	String resourceFlagId="";
	
	private PreviewPlayerPresenter previewPlayerPresenter=null;
	private CollectionEndPresenter collectionEndPresenter=null;
	private CollectionPlayerPresenter collectionPlayerPresenter=null;
	private boolean isPreviewPlayer=false,isCollectionPlayer=false;
	/**
	 * @param previewPlayerPresenter the previewPlayerPresenter to set
	 */
	public void setPreviewPlayerPresenter(PreviewPlayerPresenter previewPlayerPresenter) {
		this.previewPlayerPresenter = previewPlayerPresenter;
		this.isPreviewPlayer=true;
	}
	public void setCollectionPlayerPresenter(CollectionPlayerPresenter collectionPlayerPresenter){
		this.collectionPlayerPresenter=collectionPlayerPresenter;
		this.isCollectionPlayer=true;
	}
	@Inject
	private PlayerAppServiceAsync playerAppService;
	
	@Inject
	public CollectionFlagPresenter(EventBus eventBus, IsCollectionFlagView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	public void displayCollectionFlagData(CollectionDo collectionDo) {
		getView().getDisplayData(collectionDo);
	}

	@Override
	public void createCollectionContentReport(final String associatedGooruOid, final String freeText, final ArrayList<String> contentReportList,final String deleteContentReportGooruOids,final boolean isResourceFlag,final String collectionItemId) {
		playerAppService.createContentReport(associatedGooruOid, freeText, contentReportList, deleteContentReportGooruOids, new SimpleAsyncCallback<ContentReportDo>() {
			
			@Override
			public void onSuccess(ContentReportDo result) {
				ItemFlagDataLogEvent( associatedGooruOid, freeText,contentReportList, isResourceFlag, collectionItemId);
				getView().showSuccesmessagePopup();
				String chkViewPage = AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getParameter("view", null);			
				if(chkViewPage == null)
				{
				if(isPreviewPlayer){
					previewPlayerPresenter.updateFlagImageOnHomeView();
				}else if(isCollectionPlayer){
					collectionPlayerPresenter.updateFlagImageOnHomeView();
				}
				}
				else
				{
					if(isPreviewPlayer){
						previewPlayerPresenter.updateFlagImageOnHomeView();
					}else if(isCollectionPlayer){
						collectionPlayerPresenter.updateFlagImageOnHomeView();
					}
				}
			}
		});	
	}

	@Override
	public void getContentReport(String associatedGooruOid) {
		playerAppService.getContentReport(associatedGooruOid, AppClientFactory.getGooruUid(), new SimpleAsyncCallback<ArrayList<ContentReportDo>>() {
			@Override
			public void onSuccess(ArrayList<ContentReportDo> result) {
				String gooruFlagId="";
				if(result!=null){
					if(result.size()==0){
					}else{
						for(int i =0;i<result.size();i++){
							if(result.get(i).getDeleteContentGooruOid()!=null){
								gooruFlagId = gooruFlagId+result.get(i).getDeleteContentGooruOid();
								if(result.size()!=(i+1)){
									gooruFlagId=gooruFlagId+",";
									getView().setFlag(result.get(0), gooruFlagId);
								}
							}
						}
					}
				}
			}
		});
	}

	@Override
	public String getResourceContentReport(String associatedGooruOid){
		playerAppService.getContentReport(associatedGooruOid, AppClientFactory.getGooruUid(), new SimpleAsyncCallback<ArrayList<ContentReportDo>>() {
			@Override
			public void onSuccess(ArrayList<ContentReportDo> result) {
				if(result!=null){
					if(result.size()==0){
					}else{
						for(int i =0;i<result.size();i++){
							if(result.get(i).getDeleteContentGooruOid()!=null){
								resourceFlagId = resourceFlagId+result.get(i).getDeleteContentGooruOid();
								if(result.size()!=(i+1)){
									resourceFlagId=resourceFlagId+",";
								}
							}
						}
					}
				}
				
			}
		});
		return resourceFlagId;
	}
	
	public void ItemFlagDataLogEvent(String associatedGooruOid,String freeText,ArrayList<String> contentReportList,boolean isResourceFlag,String collectionItemId){
		String itemType=isResourceFlag?PlayerDataLogEvents.RESOURCE:PlayerDataLogEvents.COLLECTION;
	   if(isPreviewPlayer){
		   previewPlayerPresenter.triggerItemFlagDataLogEvent(PlayerDataLogEvents.getUnixTime(),itemType,freeText,contentReportList,associatedGooruOid, collectionItemId);
	   }else if(isCollectionPlayer){
		   collectionPlayerPresenter.triggerItemFlagDataLogEvent(PlayerDataLogEvents.getUnixTime(),itemType,freeText,contentReportList,associatedGooruOid, collectionItemId);
	   }
	}
	public Image getCloseButtonImage()
	{
		return getView().getCloseButton();
	}
	public Button getSubmitButton(){
		return getView().getSubmitButton();
	}
	public CollectionEndPresenter getCollectionEndPresenter() {
		return collectionEndPresenter;
	}
	public void setCollectionEndPresenter(
			CollectionEndPresenter collectionEndPresenter) {
		this.collectionEndPresenter = collectionEndPresenter;
		this.isPreviewPlayer=false;
		this.isCollectionPlayer=true;
	}

	
}

