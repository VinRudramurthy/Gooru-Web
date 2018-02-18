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
package org.ednovo.gooru.client.mvp.gshelf.collectiondetails;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.SimpleAsyncCallback;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.CollectionCollaboratorsTabPresenter;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

/**
 * @author Search TeamUnitInfoPresenter.java
 *
 */
public class CollectionShareTabPresenter extends PresenterWidget<IsCollectionShareTabView> implements CollectionShareTabUiHandlers {

	CollectionDo collectionDo;
	FolderDo folderDo;
	
	CollectionCollaboratorsTabPresenter collaboratorsTabPresenter;
	
	MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter;
	/**
	 * Class constructor
	 * @param view {@link View}
	 * @param proxy {@link Proxy}
	 */
	@Inject
	public CollectionShareTabPresenter( EventBus eventBus,IsCollectionShareTabView view,CollectionCollaboratorsTabPresenter collaboratorsTabPresenter) {
		super(eventBus,view);
		getView().setUiHandlers(this);
		this.collaboratorsTabPresenter=collaboratorsTabPresenter;
	}

	@Override
	public void onBind() {
		super.onBind();
	}

	@Override
	protected void onReveal(){
		super.onReveal();
	}
	
	@Override
	protected void onReset() {
		super.onReset();
	}

	public void setData(final FolderDo folderDo) { 
		this.folderDo=folderDo;
		getShortenShareUrl();
		//getView().setData(collectionDo);
		String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
		if(collectionId!=null){
			AppClientFactory.getInjector().getResourceService().getCollection(collectionId,true, new SimpleAsyncCallback<CollectionDo>() {
				@Override
				public void onSuccess(CollectionDo collectionDo) {
					CollectionShareTabPresenter.this.collectionDo=collectionDo;
					collaboratorsTabPresenter.setData(collectionDo);
					getView().setCollectionData(collectionDo,folderDo);
					
				}
			});
		}
		getView().getCollaboratorPanel().add(collaboratorsTabPresenter.getWidget());
	}

	public void getShortenShareUrl(){
		final Map<String, String> params = new HashMap<String, String>();
		params.put(ClientConstants.TYPE, AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken());
		params.put(ClientConstants.SHARETYPE, ClientConstants.SHARE);
		params.put("collectionType", folderDo.getType());
		AppClientFactory.getInjector().getSearchService().getShortenShareUrl(folderDo.getGooruOid(),params, new SimpleAsyncCallback<Map<String,String>>() {
			@Override
			public void onSuccess(final Map<String, String> collectionShare) {
				String shareUrl=collectionShare.get(ClientConstants.DECODERAWURL);
				getView().setShareUrl(shareUrl);
				params.put(ClientConstants.SHARETYPE, ClientConstants.EMBED);
				AppClientFactory.getInjector().getSearchService().getShortenShareUrl(folderDo.getGooruOid(),params, new SimpleAsyncCallback<Map<String,String>>() {
					@Override
					public void onSuccess(Map<String, String> result) {
						collectionShare.put(ClientConstants.EMBEDURLRAWURL, result.get(ClientConstants.DECODERAWURL));
						getView().setCollectionShareData(collectionShare);
					}
				});
				
			}
		});
	}

	public void setMyCollectionRightClusterPresenter(
			MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter) {
		this.myCollectionsRightClusterPresenter= myCollectionsRightClusterPresenter;
	}

	@Override
	public void disableCollabaratorOptions(boolean isHide) {
		myCollectionsRightClusterPresenter.disableCollabaratorOptions(isHide);
	}
}
