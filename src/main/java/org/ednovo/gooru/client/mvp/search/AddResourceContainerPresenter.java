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
package org.ednovo.gooru.client.mvp.search;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ResourceFormatDo;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderItemDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.application.shared.model.search.CollectionSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.folders.event.RefreshFolderType;
import org.ednovo.gooru.client.mvp.shelf.collection.folders.events.RefreshFolderItemEvent;
import org.ednovo.gooru.client.mvp.shelf.event.CreateCollectionItemEvent;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 *
 * @fileName : AddAssignmentContainerPresenter.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 27-Dec-2013
 *
 * @Author : Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class AddResourceContainerPresenter extends PresenterWidget<IsAddResourceContainerView> implements AddResourceContainerUiHandlers,ClientConstants{

	ResourceSearchResultDo searchResultDo =null;
	String type =null;
	String accessType =null;

	CollectionItemDo collectionItemDo;
	private static final String ASSESSMENT = "assessment";
	boolean isPlayer=false;
	HashMap<String,String> successparams = new HashMap<String, String>();
	HashMap<String, String>  urlParameters;
	private String parentId=null;
	 private String O1_LEVEL_VALUE = null, O2_LEVEL_VALUE = null, O3_LEVEL_VALUE = null;
	@Inject
	public AddResourceContainerPresenter(EventBus eventBus, IsAddResourceContainerView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	@Override
	public void getWorkspaceData(int offset,int limit, final boolean clearShelfPanel,final String searchType){
		if(COLLECTION.equalsIgnoreCase(searchType)){
			type= FOLDER;
			accessType = ACESSTEXT;
		}else{
			type=null;
			accessType = ACESSTEXT;
		}
		AppClientFactory.getInjector().getResourceService().getFolderWorkspace(offset, limit,null, type,true, new SimpleAsyncCallback<FolderListDo>() {
			@Override
			public void onSuccess(FolderListDo folderListDo) {
				if(folderListDo!=null && folderListDo.getCount()!=null){
				if(folderListDo.getCount()==0){
					getView().displayNoCollectionsMsg();
				}else{
					getView().hideNoCollectionsMsg();
					getView().displayWorkspaceData(folderListDo,clearShelfPanel,searchType);
				}
			}else{
				getView().displayNoCollectionsMsg();
			}
			}
		});
	}

	@Override
	public void getFolderItems(final TreeItem item,String parentId) {
		AppClientFactory.getInjector().getfolderService().getChildFolders(0, 20, parentId,null, null,true, new SimpleAsyncCallback<FolderListDo>() {
			@Override
			public void onSuccess(FolderListDo folderListDo) {
				getView().setFolderItems(item,folderListDo);
			}
		});
	}

	@Override
	public void getUserShelfData(ResourceSearchResultDo searchResultDo,String searchType) {
		// TODO Auto-generated method stub
		this.searchResultDo =searchResultDo;
		getView().clearShelfData();
		getWorkspaceData(0,20,true,searchType);
		getView().setSearchResultDo(this.searchResultDo);
	}
	@Override
	public void getUserShelfCollectionsData(CollectionSearchResultDo collectionsearchResultDo,String searchType) {
		// TODO Auto-generated method stub
		this.searchResultDo =collectionsearchResultDo;
		getView().clearShelfData();
		getWorkspaceData(0,20,true,searchType);
		//getView().setCollectionSearchResultDo(this.collectionsearchResultDo);
	}

	@Override
	public void addResourceToCollection(final String selectedFolderOrCollectionid,String searchType,final String title) {
		if(searchType.equalsIgnoreCase("resource")){
				if(selectedFolderOrCollectionid!=null){
			AppClientFactory.getInjector().getfolderService().getCollectionResources(selectedFolderOrCollectionid,null, null, new SimpleAsyncCallback<FolderListDo>(){
				@Override
				public void onSuccess(FolderListDo result) {
					getView().getButtonVisiblity();
					if (result.getCount()<25){
						if(isPlayer){
							AppClientFactory.getInjector().getResourceService().createCollectionItem(selectedFolderOrCollectionid, collectionItemDo.getResource().getGooruOid(), new AsyncCallback<CollectionItemDo>() {
								@Override
								public void onSuccess(CollectionItemDo result) {
									// TODO Auto-generated method stub
									successparams.put("id", selectedFolderOrCollectionid);
									getView().enableSuccessView(title,selectedFolderOrCollectionid,successparams);
								}
								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
								}
							});
							//	AppClientFactory.fireEvent(new CreateCollectionItemEvent(selectedFolderOrCollectionid,collectionItemDo.getResource().getGooruOid()));
						}
						else{
							AppClientFactory.fireEvent(new CreateCollectionItemEvent(selectedFolderOrCollectionid,searchResultDo.getGooruOid()));
							successparams.put("id", selectedFolderOrCollectionid);
							getView().enableSuccessView(title,selectedFolderOrCollectionid,successparams);
						}
						}else{
						getView().restrictionToAddResourcesData("You Can't add more than 25 resources to a collection");
						getView().getButtonVisiblity();
					}
					}
    		});

				}else{
					getView().restrictionToAddResourcesData("Please select collection");
					getView().getButtonVisiblity();
				}
		}
	}

	@Override
	public void createFolderInParent(String folderName, final String parentId,final HashMap<String, String> params) {
		boolean addToShelf = false;
		if(parentId.isEmpty()) {
			addToShelf = true;
		}
		AppClientFactory.getInjector().getfolderService().createFolder(folderName, parentId, addToShelf, new SimpleAsyncCallback<FolderDo>() {
			@Override
			public void onSuccess(final FolderDo result) {
				result.setType("folder");
				params.put("from", "AddResourcePresenter");
				final CollectionDo collection = new CollectionDo();
				collection.setGooruOid(searchResultDo.getGooruOid());

				AppClientFactory.getInjector().getfolderService().copyDraggedCollectionIntoFolder(collection,searchResultDo.getGooruOid(),result.getGooruOid(),false,new SimpleAsyncCallback<CollectionDo>() {
					@Override
					public void onSuccess(CollectionDo result1) {
						AppClientFactory.fireEvent(new RefreshFolderItemEvent(result, RefreshFolderType.INSERT, params,result1));
						//fireEvent(new RefreshDisclosurePanelForFoldersEvent(result1.getGooruOid()));
						getView().getButtonVisiblity();
						successparams.put("o1", result.getGooruOid());
						getView().enableSuccessView(result.getTitle(),result.getGooruOid(),successparams);
					}
				});
				//AppClientFactory.fireEvent(new CopyDraggedCollectionEvent(collection,searchResultDo.getGooruOid(),result.getGooruOid()));
			}
		});

	}
	@Override
	public Anchor getAddButton() {
		return getView().getAddButton();
	}

	@Override
	public Tree getfolderTreePanel() {
		return getView().getfolderTreePanel();
	}

	public void setCollectionItemData(String collectionId,
			CollectionItemDo collectionItemDo) {
		this.collectionItemDo = collectionItemDo;
		getView().clearShelfData();
		getWorkspaceData(0,20,true,"resource");
	}

	@Override
	public void setplayerStyle() {
		isPlayer=true;
		getView().setPlayerStyle(isPlayer);

	}

	@Override
	public void removePlayerStyle() {
		isPlayer=false;
		getView().removePlayerStyle(isPlayer);

	}

	public void cleartheSelecteGooruOid() {
		// TODO Auto-generated method stub
		getView().clearSelectedId();
		getView().getButtonVisiblity();
	}
	public void clearSelectedFolderId(){
		getView().clearSelectedFolderId();
		getView().getButtonVisiblity();
	}

	@Override
	public Button getCancelButton() {
		// TODO Auto-generated method stub
		return getView().getCancelButton();
	}

	@Override
	public TreeItem loadingTreeItem() {
		// TODO Auto-generated method stub
		return getView().loadingTreeItem();
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		getView().clearShelfData();
	}

	@Override
	public void addCollectionToMyCollections(String object,
			String currentsearchType) {
		final CollectionDo collection = new CollectionDo();
		if(currentsearchType.equalsIgnoreCase("collection")){
			collection.setGooruOid(searchResultDo.getGooruOid());
//			collectionFormPresenter.copyCollection(this.searchResultDo.getResourceTitle(), searchResultDo.getGooruOid());
			getView().getButtonVisiblity();
	}
}

	public void SetDefaultMyCollections() {
		// TODO Auto-generated method stub
		getView().hideNoCollectionsMsg();
		getView().showAndHideMyCollections();
	}

	@Override
	public void addCollectionToFolder(final String selectedFolderOrCollectionid,String searchType, final String title, final int folerLevel,HashMap<String, String> urlparams) {
		this.urlParameters=urlparams;
		final CollectionDo collection = new CollectionDo();
		if(searchType.equalsIgnoreCase("collection")){
		collection.setGooruOid(searchResultDo.getGooruOid());
		collection.setSharing("anyonewithlink");
		if(selectedFolderOrCollectionid!=null){
			successparams.put("o1", selectedFolderOrCollectionid);
			O1_LEVEL_VALUE = urlparams.get("o1");
			O2_LEVEL_VALUE = urlparams.get("o2");
			O3_LEVEL_VALUE = urlparams.get("o3");

			if(O3_LEVEL_VALUE!=null){
				parentId=O3_LEVEL_VALUE;
			}else if(O2_LEVEL_VALUE!=null){
				parentId=O2_LEVEL_VALUE;
			}else if(O1_LEVEL_VALUE!=null){
				parentId=O1_LEVEL_VALUE;
			}
			AppClientFactory.getInjector().getfolderService().copyDraggedCollectionIntoFolder(collection,searchResultDo.getGooruOid(),parentId,false,new SimpleAsyncCallback<CollectionDo>() {
				@Override
				public void onSuccess(CollectionDo result) {
					FolderDo folderDo=getFolderDo(result);
					HashMap<String,String> params = new HashMap<String,String>();
					if(O3_LEVEL_VALUE!=null) {
						params.put("o3", O3_LEVEL_VALUE);
					}
					if(O2_LEVEL_VALUE!=null) {
						params.put("o2", O2_LEVEL_VALUE);
					}
					if(O1_LEVEL_VALUE!=null) {
						params.put("o1", O1_LEVEL_VALUE);
					}
//					AppClientFactory.fireEvent(new RefreshFolderItemForSearchInAddResourceEvent(folderDo, RefreshFolderType.INSERT, params));
					getView().enableSuccessView(title,selectedFolderOrCollectionid,successparams);
				}
			});

		}else{
			getView().restrictionToAddResourcesData("Please select a folder to add collection");
			getView().getButtonVisiblity();
		}
	}

	}

	public FolderDo getFolderDo(CollectionDo collectionDo) {
		FolderDo folderDo = new FolderDo();
		folderDo.setGooruOid(collectionDo.getGooruOid());
		folderDo.setTitle(collectionDo.getTitle());
		folderDo.setType(collectionDo.getCollectionType());
		folderDo.setSharing(collectionDo.getSharing());
		folderDo.setCollectionType(collectionDo.getCollectionType());
		ThumbnailDo thumbnailDo = new ThumbnailDo();
		thumbnailDo.setUrl(collectionDo.getThumbnailUrl());
		folderDo.setThumbnails(thumbnailDo);
		List<FolderItemDo> folderItems = new ArrayList<FolderItemDo>();
		if(collectionDo.getCollectionItems()!=null) {
			for(int i=0;i<collectionDo.getCollectionItems().size();i++) {
				CollectionItemDo collectionItemDo = collectionDo.getCollectionItems().get(i);
				FolderItemDo folderItemDo = new FolderItemDo();
				folderItemDo.setGooruOid(collectionItemDo.getGooruOid());
				folderItemDo.setTitle(collectionItemDo.getResourceTitle());
				folderItemDo.setType(collectionItemDo.getItemType());
				ResourceFormatDo resourceFormatDo = new ResourceFormatDo();
				resourceFormatDo.setValue(collectionItemDo.getCategory());
				folderItems.add(folderItemDo);
			}
			folderDo.setCollectionItems(folderItems);
		}
		return folderDo;
	}

	@Override
	public boolean validateIsAssessments(String collectionType) {
		boolean flag=false;
		if(ASSESSMENT.equalsIgnoreCase(collectionType)){
			if(QUESTION.equalsIgnoreCase(collectionItemDo.getResource().getCategory()) && (collectionItemDo.getQuestionType()!=null && !(collectionItemDo.getQuestionType().equalsIgnoreCase("OE")))){
				flag=true;
			}else{
				flag=false;
			}
		}else{
			flag=true;
		}
		return flag;
	}
}