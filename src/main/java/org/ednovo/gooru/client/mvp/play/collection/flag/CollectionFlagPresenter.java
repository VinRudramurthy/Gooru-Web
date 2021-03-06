package org.ednovo.gooru.client.mvp.play.collection.flag;
import java.util.ArrayList;

import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.play.collection.CollectionPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.end.CollectionEndPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.end.PreviewEndPresenter;

import com.google.gwt.user.client.ui.Image;
import org.ednovo.gooru.client.service.PlayerAppServiceAsync;
import org.ednovo.gooru.client.uc.HTMLEventPanel;
import org.ednovo.gooru.shared.model.content.CollectionDo;
import org.ednovo.gooru.shared.model.content.ContentReportDo;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
	public void createCollectionContentReport(String associatedGooruOid,String freeText,ArrayList<String> contentReportList,String deleteContentReportGooruOids) {
		playerAppService.createContentReport(associatedGooruOid, freeText, contentReportList, deleteContentReportGooruOids, new SimpleAsyncCallback<ContentReportDo>() {
			
			@Override
			public void onSuccess(ContentReportDo result) {
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
		//playerAppService.getContentReport(associatedGooruOid, new AsyncCallback<ContentReportDo>() {
		playerAppService.getContentReport(associatedGooruOid, AppClientFactory.getGooruUid(), new AsyncCallback<ArrayList<ContentReportDo>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<ContentReportDo> result) {
				String gooruFlagId="";
				if(result.size()==0){
					getView().setDefaultView();
				}else{
					for(int i =0;i<result.size();i++){
						gooruFlagId = gooruFlagId+result.get(i).getDeleteContentGooruOid();
						if(result.size()!=(i+1)){
							gooruFlagId=gooruFlagId+",";
							getView().setFlag(result.get(0), gooruFlagId);
						}
					}
			}
			}
		});

			
	
		
	}

	@Override
	public String getResourceContentReport(String associatedGooruOid) {
		playerAppService.getContentReport(associatedGooruOid, AppClientFactory.getGooruUid(), new AsyncCallback<ArrayList<ContentReportDo>>() {
		
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<ContentReportDo> result) {
				
				if(result.size()==0){
					//getView().setDefaultView();
				}else{
					for(int i =0;i<result.size();i++){
						resourceFlagId = resourceFlagId+result.get(i).getDeleteContentGooruOid();
						if(result.size()!=(i+1)){
							resourceFlagId=resourceFlagId+",";
							//getView().setFlag(result.get(0), resourceFlagId);
						}
					}
			}
			}
		});
		return resourceFlagId;
	}
	public Image getCloseButtonImage()
	{
		return getView().getCloseButton();
	}
	public HTMLEventPanel getSubmitButton(){
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

