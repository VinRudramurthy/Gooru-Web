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
package org.ednovo.gooru.client.mvp.gsearch.addResourcePopup;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
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
import org.ednovo.gooru.client.mvp.search.util.CollectionResourceWidget;
import org.ednovo.gooru.client.mvp.search.util.CollectionSearchWidget;
import org.ednovo.gooru.client.uc.AlertContentUc;
import org.ednovo.gooru.client.uc.AlertForImageUpload;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 *
 * @fileName : SearchAddResourceToCollectionPresenter.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 22-APR-2015
 *
 * @Author : Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class SearchAddResourceToCollectionPresenter extends PresenterWidget<IsSearchAddResourceToCollectionView> implements SearchAddResourceToCollectionUiHandlers,ClientConstants{

	ResourceSearchResultDo searchResultDo =null;
	String type =null;
	String accessType =null;
	String collectionId = null;
	private String parentId=null;
	HashMap<String, String>  urlParameters;
	 private String O1_LEVEL_VALUE = null, O2_LEVEL_VALUE = null, O3_LEVEL_VALUE = null;
	 private String courseId=null;
	private String unitId=null;
	private String lessonId=null;
	private String collectionTitle="";
	CollectionResourceWidget collectionResourceWidget=null;
	CollectionSearchWidget collectionSearchWidget=null;
	private static final String ASSESSMENT = "assessment";
	private static final String QUESTION = "question";

	private static final String MYCONTENT ="coursebuilder";
	private boolean isFromCopyResource= false;


	HashMap<String,String> successparams = new HashMap<String, String>();

	private SimpleAsyncCallback<CollectionDo> saveCollectionAsyncCallback;
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public SearchAddResourceToCollectionPresenter(EventBus eventBus, IsSearchAddResourceToCollectionView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
	@Override
	public void getUserShelfData(ResourceSearchResultDo searchResultDo,String searchType,CollectionResourceWidget collectionResourceWidget) {
		this.searchResultDo =searchResultDo;
		this.collectionResourceWidget=collectionResourceWidget;
		getView().setDefaultPanelVisibility(false);
		getWorkspaceData(0,20,true,searchType);
	}
	@Override
	public void getUserShelfCollectionsData(CollectionSearchResultDo collectionsearchResultDo,String searchType,CollectionSearchWidget collectionSearchWidget) {
		this.searchResultDo =collectionsearchResultDo;
		this.collectionSearchWidget=collectionSearchWidget;
		this.collectionTitle=collectionsearchResultDo.getTitle();
		getView().setDefaultPanelVisibility(false);
		getWorkspaceData(0,20,true,searchType);
	}

	@Override
	public void getUserShelfCollectionsData(String collectionId,String searchType,String collectionTitle) {
		this.collectionSearchWidget=null;
		this.collectionId=collectionId;
		this.collectionTitle=collectionTitle;
		getView().setDefaultPanelVisibility(false);
		getWorkspaceData(0,20,true,searchType);
	}


	public void getWorkspaceData(int offset,int limit, final boolean clearShelfPanel,final String searchType){
		if(clearShelfPanel){
			getView().clearUrlParams();
		}
		if(COLLECTION.equalsIgnoreCase(searchType)){
			type= FOLDER;
			accessType = ACESSTEXT;
		}else if(MYCONTENT.equalsIgnoreCase(searchType)){
			type="course";
		}
		else{
			type=null;
			accessType = ACESSTEXT;
		}
		
		AppClientFactory.getInjector().getResourceService().getFolderWorkspace(offset, limit,null, type,true, new SimpleAsyncCallback<FolderListDo>() {
			@Override
			public void onSuccess(FolderListDo folderListDo) {
				if(type==null){
					if(folderListDo!=null && folderListDo.getCount()!=null && folderListDo.getCount()==0 && clearShelfPanel){
						getView().displayNoCollectionsMsg(type);
					}else{
						getView().enableAddButton();
						getView().displayWorkspaceData(folderListDo,clearShelfPanel,searchType);
					}
				}
				else if(type.equalsIgnoreCase(FOLDER)){
					if(folderListDo!=null && folderListDo.getCount()!=null && folderListDo.getCount()==0 && clearShelfPanel){
						getView().displayNoCollectionsMsg(type);
					}else{
						getView().enableAddButton();
						getView().displayWorkspaceData(folderListDo,clearShelfPanel,searchType);
					}
				}else if(type.equalsIgnoreCase("course")){
					if(folderListDo.getSearchResult().size()==0 && clearShelfPanel){
						getView().displayNoCollectionsMsg(type);
					}else{
						getView().enableAddButton();
						getView().displayWorkspaceData(folderListDo,clearShelfPanel,searchType);
					}
				}
			}
		});
	}
	
	
	
	@Override
	public void getFolderItems(final TreeItem item,String parentId) {
		AppClientFactory.getInjector().getfolderService().getChildFolders(0, 20, parentId,null, type,true, new SimpleAsyncCallback<FolderListDo>() {
			@Override
			public void onSuccess(FolderListDo folderListDo) {
				getView().setFolderItems(item,folderListDo);
			}
		});
	}
	
	
	
	
	@Override
	public void addResourceToCollection(final String selectedFolderOrCollectionid,final String searchType,final String title,final HashMap<String, String> urlparams,final boolean isFromMyCourse) {
		if(selectedFolderOrCollectionid!=null){
			urlparams.put("id",selectedFolderOrCollectionid);
			if(isFromMyCourse){
				urlparams.put("view", "Course");
			}else{
				urlparams.put("view", "Folder");
			}
			AppClientFactory.getInjector().getResourceService().getCollection(selectedFolderOrCollectionid, true, new AsyncCallback<CollectionDo>() {
				@Override
				public void onSuccess(CollectionDo result) {
					int resourceCount=0,questionCount=0,totalCount=0;
					if(result!=null && result.getSummary()!=null){
						if(result.getSummary().getResourceCount()!=null){
							 resourceCount= result.getSummary().getResourceCount();
						}
						if(result.getSummary().getQuestionCount()!=null){
							questionCount = result.getSummary().getQuestionCount();
						}
					}
					totalCount = resourceCount+questionCount;
					if(totalCount<25){
						String resourceFormatValue= searchResultDo.getNewResourceFormat().getValue();
						AppClientFactory.getInjector().getResourceService().addCollectionItem(selectedFolderOrCollectionid, searchResultDo.getGooruOid(),resourceFormatValue, new SimpleAsyncCallback<CollectionItemDo>() {
							@Override
							public void onSuccess(CollectionItemDo result) {
								if(result!=null && result.getStatusCode()==200){
									AppClientFactory.getInjector().getAnalyticsService().getResourceAndCollectionCounts(getCollectionGooruId(),searchType, new SimpleAsyncCallback<HashMap<String,String>>() {
										@Override
										public void onSuccess(HashMap<String, String> result) {
											if(collectionResourceWidget!=null){
												collectionResourceWidget.getLbladdCount().setText(result.get("resourceAdded"));
											}
										}
									});
									successparams.put("id", selectedFolderOrCollectionid);
									urlparams.put("isSuccess", "true");
									getView().displaySuccessPopup(title,selectedFolderOrCollectionid,urlparams,searchType,null);
								}else{
									getView().hidePopup();
									Window.enableScrolling(false);
									AlertContentUc alertContentUc = new AlertContentUc(i18n.GL0061(),"Sorry You can't add this resource to a Collection");
								}
							}
						});
					}else{
						getView().hidePopup();
						Window.enableScrolling(false);
						AlertContentUc alertContentUc = new AlertContentUc(i18n.GL0061(),"Sorry You can't add more than 25 resources/questions");
					}
				}
				@Override
				public void onFailure(Throwable caught) {
				}
			});
		}
	}
	
	
	
	@Override
	public void addCollectionToFolder(final String selectedFolderOrCollectionid,final String searchType, final String title, final int folerLevel,HashMap<String, String> urlparams) {
			this.urlParameters=urlparams;
			final CollectionDo collection = new CollectionDo();
			if(searchType.equalsIgnoreCase("collection")){
				collection.setGooruOid(getCollectionGooruId());
				collection.setSharing("anyonewithlink");
			if(selectedFolderOrCollectionid!=null){
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
				AppClientFactory.getInjector().getfolderService().copyDraggedCollectionIntoFolder(collection,getCollectionGooruId(),parentId,false,new SimpleAsyncCallback<CollectionDo>() {
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
						params.put("view", "Folder");
						AppClientFactory.getInjector().getAnalyticsService().getResourceAndCollectionCounts(getCollectionGooruId(),searchType, new SimpleAsyncCallback<HashMap<String,String>>() {
							@Override
							public void onSuccess(HashMap<String, String> result) {
								if(collectionSearchWidget!=null){
									collectionSearchWidget.getRemixCountLbl().setText(result.get("copyCount"));
								}
							}
						});
					//	getView().displaySuccessPopup(title, result.getGooruOid(), params,"collection",folderDo);
					}
				});
			}else{
				getView().restrictionToAddResourcesData("Please select a folder to add collection");
			}
		}
	}
	private String getCollectionGooruId() {
		String gooruOid="";
		if(AppClientFactory.getCurrentPlaceToken().contains("search")){
			gooruOid = searchResultDo.getGooruOid();
		}else{
			gooruOid =  collectionId;
		}
		return gooruOid;
	}
	private String getCategory(){
		String category="";
		if(AppClientFactory.getCurrentPlaceToken().contains("search")){
			category = searchResultDo.getCategory();
		}else{
			category =  searchResultDo.getNewResourceFormat().getValue();
		}
		return category;
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
	public void addCollectionToMyCollections(String object,String currentsearchType) {
		final CollectionDo collection = new CollectionDo();
		if(currentsearchType.equalsIgnoreCase("collection")){
			collection.setGooruOid(getCollectionGooruId());
			AppClientFactory.getInjector().getResourceService().copyCollection(collection, "true", null,getSaveCollectionAsyncCallback());
		}
	}
	
	/**
	 * @return instance of collectionDo after collection save
	 */
	public SimpleAsyncCallback<CollectionDo> getSaveCollectionAsyncCallback() {
		if (saveCollectionAsyncCallback == null) {
			saveCollectionAsyncCallback = new SimpleAsyncCallback<CollectionDo>() {
				@Override
				public void onSuccess(CollectionDo result) {
					FolderDo folderDo=getFolderDo(result);
					AppClientFactory.getInjector().getAnalyticsService().getResourceAndCollectionCounts(getCollectionGooruId(),"collection", new SimpleAsyncCallback<HashMap<String,String>>() {
						@Override
						public void onSuccess(HashMap<String, String> result) {
							if(collectionSearchWidget!=null){
								collectionSearchWidget.getRemixCountLbl().setText(result.get("copyCount"));
							}
						}
					});
					getView().displaySuccessPopup("My Collections", result.getGooruOid(),null ,"collection",folderDo);
				}
			};
		}
		return saveCollectionAsyncCallback;
	}

	@Override
	public Button getAddButton() {
		return getView().getAddButton();
	}
	@Override
	public void hidePopup() {
		getView().hidePopup();
	}

	@Override
	public boolean validateIsAssessments(String collectionType) {
		boolean flag=false;
		if(ASSESSMENT.equalsIgnoreCase(collectionType)){
			if(QUESTION.equalsIgnoreCase(getCategory()) && (searchResultDo.getQuestionType()!=null && !(searchResultDo.getQuestionType().equalsIgnoreCase("OE")))){
				flag=true;
			}else{
				flag=false;
			}
		}else{
			flag=true;
		}
		return flag;
	}

	
	@Override
	public void getCourseItems(final TreeItem item,String courseId, String UnitId,String lessionId, String typeValue) {
		final String COLLECTION_ASSESMENT="collection,assessment";
		AppClientFactory.getInjector().getfolderService().getChildFoldersForCourse(0, 20,courseId, UnitId, lessionId, null, COLLECTION_ASSESMENT, false, new SimpleAsyncCallback<FolderListDo>() {
			@Override
			public void onSuccess(FolderListDo result) {
				getView().setFolderItems(item,result);
			}
		});
	}
	
	
	@Override
	public void CopyToplevelMyCollections(String collectionId, String folderId,String searchType,String collectionTitle,final HashMap<String, String> urlparams) {
		AppClientFactory.getInjector().getResourceService().CopyToplevelMyCollections(getCollectionGooruId(), folderId, this.collectionTitle, new SimpleAsyncCallback<CollectionDo>() {
			@Override
			public void onSuccess(CollectionDo result) {
				if(result!=null  && result.getStatusCode()==200){
					FolderDo folderDo=getFolderDo(result);
					AppClientFactory.getInjector().getAnalyticsService().getResourceAndCollectionCounts(getCollectionGooruId(),"collection", new SimpleAsyncCallback<HashMap<String,String>>() {
						@Override
						public void onSuccess(HashMap<String, String> result) {
							if(collectionSearchWidget!=null){
								collectionSearchWidget.getRemixCountLbl().setText(result.get("copyCount"));
							}
						}
					});
					HashMap<String,String> params = new HashMap<String,String>();
					if(urlparams!=null && urlparams.get("o3")!=null) {
						params.put("o3", urlparams.get("o3"));
					}
					if(urlparams!=null && urlparams.get("o2")!=null) {
						params.put("o2", urlparams.get("o2"));
					}
					if(urlparams!=null && urlparams.get("o1")!=null) {
						params.put("o1", urlparams.get("o1"));
					}
					params.put("id", result.getGooruOid());
					params.put("view", "Folder");
					String NameTokenValue= AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
					if(NameTokenValue.equalsIgnoreCase(PlaceTokens.MYCONTENT)){
						getView().hidePopup();
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
					}else{
						getView().displaySuccessPopup("My Content", result.getGooruOid(),params ,"collection",folderDo);
					}
				}else{
					getView().hidePopup();
					Window.enableScrolling(false);
					AlertContentUc alertContentUc = new AlertContentUc(i18n.GL0061(),"Sorry Something Went Wrong");
				}
			}
		});
	}

	@Override
	public void copyCollectionToLession(String collectionId,String collectionTitle, final HashMap<String, String> urlparams) {
		if(urlparams!=null){
			this.urlParameters=urlparams;
			courseId=urlparams.get("o1");
			unitId=urlparams.get("o2");
			lessonId=urlparams.get("o3");
		}
		
		AppClientFactory.getInjector().getResourceService().CopyCollectionToLesson(courseId, unitId, lessonId, getCollectionGooruId(),this.collectionTitle, new org.ednovo.gooru.application.client.SimpleAsyncCallback<CollectionDo>() {
			@Override
			public void onSuccess(CollectionDo result) {
				if(result!=null && result.getStatusCode()==200){
					FolderDo folderDo=getFolderDo(result);
					AppClientFactory.getInjector().getAnalyticsService().getResourceAndCollectionCounts(getCollectionGooruId(),"collection", new SimpleAsyncCallback<HashMap<String,String>>() {
						@Override
						public void onSuccess(HashMap<String, String> result) {
							if(collectionSearchWidget!=null){
								collectionSearchWidget.getRemixCountLbl().setText(result.get("copyCount"));
							}
						}
					});
					HashMap<String,String> params = new HashMap<String,String>();
					if(urlparams!=null && urlparams.get("o3")!=null) {
						params.put("o3", urlparams.get("o3"));
					}
					if(urlparams!=null && urlparams.get("o2")!=null) {
						params.put("o2", urlparams.get("o2"));
					}
					if(urlparams!=null && urlparams.get("o1")!=null) {
						params.put("o1", urlparams.get("o1"));
					}
					params.put("view", "Course");
					params.put("id", result.getGooruOid());
					String NameTokenValue= AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
					if(NameTokenValue.equalsIgnoreCase(PlaceTokens.MYCONTENT)){
						getView().hidePopup();
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
					}else{
						getView().displaySuccessPopup("My Content", result.getGooruOid(),params ,"collection",folderDo);
					}
				}else{
					getView().hidePopup();
					Window.enableScrolling(false);
					AlertContentUc alertContentUc = new AlertContentUc(i18n.GL0061(),"Sorry Something Went Wrong");
				}
			}
		});
	}

	@Override
	public void moveCollectionTOLesson(String collectionId,String collectionTitle, final HashMap<String, String> urlparams) {
		if(urlparams!=null){
			this.urlParameters=urlparams;
			courseId=urlparams.get("o1");
			unitId=urlparams.get("o2");
			lessonId=urlparams.get("o3");
		}
		AppClientFactory.getInjector().getResourceService().moveCollectionTOLesson(courseId, unitId, lessonId, getCollectionGooruId(), new org.ednovo.gooru.application.client.SimpleAsyncCallback<CollectionDo>() {
			@Override
			public void onSuccess(CollectionDo result) {
				if(result!=null && result.getStatusCode()==200){
					FolderDo folderDo=getFolderDo(result);
					HashMap<String,String> params = new HashMap<String,String>();
					if(urlparams!=null && urlparams.get("o3")!=null) {
						params.put("o3", urlparams.get("o3"));
					}
					if(urlparams!=null && urlparams.get("o2")!=null) {
						params.put("o2", urlparams.get("o2"));
					}
					if(urlparams!=null && urlparams.get("o1")!=null) {
						params.put("o1", urlparams.get("o1"));
					}
					params.put("id", getCollectionGooruId());
					params.put("view", "Course");
					String NameTokenValue= AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
					if(NameTokenValue.equalsIgnoreCase(PlaceTokens.MYCONTENT)){
						getView().hidePopup();
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
					}else{
						getView().displaySuccessPopup("My Content", getCollectionGooruId(),params ,"collection",folderDo);
					}
				}else{
					getView().hidePopup();
					Window.enableScrolling(false);
					AlertContentUc alertContentUc = new AlertContentUc(i18n.GL0061(),result.getErrorMessage());
				}
			}
		});
	}
	@Override
	public void moveCollectionToMyCOllections(String gooruOid, String folderId,
			String searchType, String collectionTitle,
			final HashMap<String, String> urlparams) {
		AppClientFactory.getInjector().getResourceService().moveCollectionToMyCOllections(getCollectionGooruId(), folderId, this.collectionTitle, new SimpleAsyncCallback<CollectionDo>() {
			@Override
			public void onSuccess(CollectionDo result) {
				if(result!=null && result.getStatusCode()==200){
					FolderDo folderDo=getFolderDo(result);
					HashMap<String,String> params = new HashMap<String,String>();
					if(urlparams!=null && urlparams.get("o3")!=null) {
						params.put("o3", urlparams.get("o3"));
					}
					if(urlparams!=null && urlparams.get("o2")!=null) {
						params.put("o2", urlparams.get("o2"));
					}
					if(urlparams!=null && urlparams.get("o1")!=null) {
						params.put("o1", urlparams.get("o1"));
					}
					params.put("id", getCollectionGooruId());
					params.put("view", "Folder");
					String NameTokenValue= AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
					if(NameTokenValue.equalsIgnoreCase(PlaceTokens.MYCONTENT)){
						getView().hidePopup();
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
					}else{
						getView().displaySuccessPopup("My Content", getCollectionGooruId(),params ,"collection",folderDo);
					}
				}else{
					getView().hidePopup();
					Window.enableScrolling(false);
					AlertContentUc alertContentUc = new AlertContentUc(i18n.GL0061(),result.getErrorMessage());
				}
			}
		});
	}

	public void selectedCopyOrMoveStatus(boolean isCopySelected,boolean isMoveSelected,String selectedType) {
		getView().setCopyAndMoveStatus(isCopySelected,isMoveSelected,selectedType);
	}

	public void DisableMyCollectionsPanelData(boolean val) {
		if(val){
			getView().getMycollectionsLbl().getElement().getStyle().setDisplay(Display.NONE);
			getView().getMycontentLbl().getElement().getStyle().setDisplay(Display.BLOCK);
			getView().getMycollectionsLbl().removeStyleName("active");
			getView().getMycontentLbl().addStyleName("active");
			getView().setFromMyCourse(true);
			getView().getMycollectionsDefaultLbl().getElement().getStyle().setDisplay(Display.NONE);
		}else{
			getView().getMycollectionsLbl().getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
			getView().getMycontentLbl().getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
			getView().getMycontentLbl().addStyleName("active");
			getView().getMycollectionsLbl().removeStyleName("active");
			getView().setFromMyCourse(true);
			getView().getMycollectionsDefaultLbl().getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	public void setCollectionTitle(String collectionTitle) {
		this.collectionTitle=collectionTitle;
	}

	@Override
	public void enableAddButton() {
		getView().enableAddButton();
	}

	public void setCollectionsData(boolean value) {
		isFromCopyResource=value;
		getView().isFromCopyResource(isFromCopyResource);
	}

	@Override
	public void getLoadingImage() {
		getView().loadingImage();
	}

	@Override
	public void copyLessonToUnit(final HashMap<String, String> urlparams, String lessonId) {
		if(urlparams!=null){
			this.urlParameters=urlparams;
			courseId=urlparams.get("o1");
			unitId=urlparams.get("o2");
		}

		AppClientFactory.getInjector().getfolderService().copyCourse(courseId, unitId, lessonId, new SimpleAsyncCallback<String>()  {
			
			@Override
			public void onSuccess(String result) {
				callJobSuccessApi(result,urlparams);
			}
		});
		
	}

	@Override
	public void copyUnitToCourse(final HashMap<String, String> urlparams,String unitId) {
		if(urlparams!=null){
			this.urlParameters=urlparams;
			courseId=urlparams.get("o1");
			lessonId=null;
		}

		AppClientFactory.getInjector().getfolderService().copyCourse(courseId, unitId, lessonId, new SimpleAsyncCallback<String>()  {
			
			@Override
			public void onSuccess(String result) {
				callJobSuccessApi(result,urlparams);
			}
		});
	}

	protected void callJobSuccessApi(final String jobUrl,final HashMap<String, String> urlparams) {
		AppClientFactory.getInjector().getfolderService().jobCheck(jobUrl,new SimpleAsyncCallback<Map<String,String>>() {

			@Override
			public void onSuccess(Map<String, String> result) {
				if(result.get("status").equalsIgnoreCase("completed")){
					HashMap<String,String> params = new HashMap<String,String>();
					if(urlparams!=null && urlparams.get("o1")!=null) {
						params.put("o1", urlparams.get("o1"));
						params.put("o2", result.get("gooruOid"));
					}
					if(urlparams!=null && urlparams.get("o2")!=null) {
						params.put("o2", urlparams.get("o2"));
						params.put("o3", result.get("gooruOid"));
					}
					params.put("view", "Course");
					getView().hidePopup();
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
				}else if(result.get("status").equalsIgnoreCase("inprogress")){
					Timer timer = new Timer() {

						@Override
						public void run() {
							callJobSuccessApi(jobUrl,urlparams);
						}
						
					};
					timer.schedule(150);
				}else{
					new AlertForImageUpload("Oops", "Something went wrong, plewase try again.");
					getView().hidePopup();
				}
			}
		});
	}

}
