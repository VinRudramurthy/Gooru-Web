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
package org.ednovo.gooru.client.mvp.play.collection.share;
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.play.collection.CollectionPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
public class CollectionSharePresenter extends PresenterWidget<IsCollectionShareView> implements CollectionShareUiHandlers,ClientConstants{

	private String collectionId=null, resourceId=null;
	private boolean isResourceView;
	private boolean isCollectionPlayer=false;
	private boolean isPreviewPlayer=false;
	

	private CollectionPlayerPresenter collectionPlayerPresenter=null;
	private PreviewPlayerPresenter previewPlayerPresenter=null;
	
	public CollectionPlayerPresenter getCollectionPlayerPresenter() {
		return collectionPlayerPresenter;
	}

	public void setCollectionPlayerPresenter(CollectionPlayerPresenter collectionPlayerPresenter) {
		this.collectionPlayerPresenter = collectionPlayerPresenter;
		this.isCollectionPlayer=true;
	}

	public PreviewPlayerPresenter getPreviewPlayerPresenter() {
		return previewPlayerPresenter;
		
	}

	public void setPreviewPlayerPresenter(PreviewPlayerPresenter previewPlayerPresenter) {
		this.previewPlayerPresenter = previewPlayerPresenter;
		this.isPreviewPlayer=true;
	}
	

	@Inject
	public CollectionSharePresenter(EventBus eventBus, IsCollectionShareView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}
	
	public void showShareView(boolean isResourceView){
		this.isResourceView=isResourceView;
		getView().showShareView(isResourceView);
	}
	public void showResourceData(CollectionItemDo collectionItemDo){
		if(isResourceView)
		getView().showResourceData(collectionItemDo);
	}
	
	public void setCollectionShareData(final CollectionDo collectionDo) {
		
		if(collectionId!=null&&this.collectionId.equalsIgnoreCase(collectionDo.getGooruOid())){
			getView().setCollectionShareData();
		}else{
		 collectionId=collectionDo.getGooruOid();
		 final Map<String, String> params = new HashMap<String, String>();
			params.put(ClientConstants.TYPE, AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken());
			params.put(ClientConstants.SHARETYPE, ClientConstants.SHARE);
			AppClientFactory.getInjector().getSearchService().getShortenShareUrl(collectionId,params, new SimpleAsyncCallback<Map<String,String>>() {
				@Override
				public void onSuccess(final Map<String, String> collectionShareMap) {
					String shareUrl=collectionShareMap.get(ClientConstants.DECODERAWURL);
					getView().setData(collectionDo);
					getView().setEmbedurl(shareUrl);
					params.put(ClientConstants.SHARETYPE, ClientConstants.EMBED);
					AppClientFactory.getInjector().getSearchService().getShortenShareUrl(collectionDo.getGooruOid(),params, new SimpleAsyncCallback<Map<String,String>>() {
						@Override
						public void onSuccess(Map<String, String> result) {
							collectionShareMap.put(ClientConstants.EMBEDURLRAWURL, result.get(ClientConstants.DECODERAWURL));
							getView().setCollectionShareData(collectionShareMap);
						}
					});
				}
			});
		}
	}
	
	public void setResourceShareData(String resourceId) {
		if(resourceId!=null&&!resourceId.isEmpty()){
			if(this.resourceId!=null&&this.resourceId.equalsIgnoreCase(resourceId)){
				getView().setResourceShareData();
			}else{
				this.resourceId=resourceId;
				this.collectionId=null;
				 Map<String, String> params = new HashMap<String, String>();
					params.put(ClientConstants.TYPE, PlaceTokens.SEARCH_RESOURCE);
					params.put(ClientConstants.SHARETYPE, ClientConstants.SHARE);
				AppClientFactory.getInjector().getSearchService().getShortenShareUrl(resourceId,params, new SimpleAsyncCallback<Map<String,String>>() {
						@Override
						public void onSuccess(Map<String, String> result) {
							getView().setResourceShareData(result);
						}
					});
			}
		}
	}
	
	
	@Override
	protected void onBind() {
		super.onBind();
	}
	
	@Override
	protected void onReset() {
		super.onReset();
	}
	
	@Override
	public void sendEmail(String fromEmail, final String toEmail, String copyEmail,	String subject, String message) {
		AppClientFactory.getInjector().getPlayerAppService().sendEmail(fromEmail, toEmail, copyEmail, subject, message, new SimpleAsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				getView().hideSendEmailPopup(toEmail);
			}
		});
	}

	@Override
	public void triggerShareDatalogEvent(String resourceGooruOid,String collectionItemId, String itemType,String shareType, boolean confirmStatus) {
		if(isCollectionPlayer){
			collectionPlayerPresenter.triggerShareDatalogEvent(resourceGooruOid, collectionItemId,  itemType, shareType,  confirmStatus);
		}else if(isPreviewPlayer){
			previewPlayerPresenter.triggerShareDatalogEvent(resourceGooruOid, collectionItemId,  itemType, shareType,  confirmStatus);
		}
	}
}
