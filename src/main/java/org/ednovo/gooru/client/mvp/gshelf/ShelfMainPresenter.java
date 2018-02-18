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
package org.ednovo.gooru.client.mvp.gshelf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.AppPlaceKeeper;
import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePlacePresenter;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.client.service.ShelfServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ResourceFormatDo;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderItemDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.event.InvokeLoginEvent;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.gshelf.courselist.MyCollectionsListPresenter;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;
import org.ednovo.gooru.client.mvp.home.AlmostDoneUc;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.ErrorPopup;
import org.ednovo.gooru.client.mvp.shelf.event.GetEditPageHeightEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshCollectionInShelfListEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.mvp.shelf.event.UpdateResourceCountEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

/**
 * @author Search Team
 *
 */
public class ShelfMainPresenter extends BasePlacePresenter<IsShelfMainView, ShelfMainPresenter.IsShelfMainProxy> implements ShelfMainUiHandlers {

	@Inject
	private ShelfServiceAsync shelfService;

	@Inject
	private ResourceServiceAsync resourceService;

	private boolean clrPanel=false;

	private String type="Course";

	private static final String VIEW= "view";

	private static final String ID= "id";

	private static final String FOLDER = "Folder";

	public static final  Object RIGHT_SLOT = new Object();

	private static final String O1_LEVEL = "o1";

	MyCollectionsListPresenter myCollectionsListPresenter;

	SignUpPresenter signUpViewPresenter;

	private String version = null;

	boolean isApiCalled=false;

	boolean isSuccess=true;

	public FolderDo folderMetaData = null;

	private SimpleAsyncCallback<FolderListDo> userCollectionAsyncCallback = null;

	private SimpleAsyncCallback<CollectionDo> collectionAsyncCallback;

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	private static final String CALLBACK = "callback";

	ErrorPopup errorPopup = null;

	String parentId;

	private List<FolderDo> searchResult = new ArrayList<FolderDo>();

	@ProxyCodeSplit
	@NameToken(PlaceTokens.MYCONTENT)
	@UseGatekeeper(AppPlaceKeeper.class)
	public interface IsShelfMainProxy extends ProxyPlace<ShelfMainPresenter> {

	}

	/**
	 * class constructor to set all class of instance
	 *
	 * @param myCollectionsListPresenter
	 *            instance of {@link MyCollectionsListPresenter}
	 * @param signUpPresenter
	 *            instance of {@link SignUpPresenter}
	 * @param view
	 *            {@link View}
	 * @param proxy
	 *            {@link Proxy}
	 */
	@Inject
	public ShelfMainPresenter(SignUpPresenter signUpViewPresenter,MyCollectionsListPresenter myCollectionsListPresenter,IsShelfMainView view, IsShelfMainProxy proxy) {
		super(view, proxy);
		getView().setUiHandlers(this);
		this.signUpViewPresenter = signUpViewPresenter;
		this.myCollectionsListPresenter=myCollectionsListPresenter;

		myCollectionsListPresenter.setShelfMainPresenter(this);
		addRegisteredHandler(GetEditPageHeightEvent.TYPE, this);
		addRegisteredHandler(UpdateResourceCountEvent.TYPE, this);
		addRegisteredHandler(LoadMyContentEvent.TYPE, this);
		Document doc = Document.get();
		doc.getBody().setClassName("");
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		callBackMethods();
	}

	private void callBackMethods(){
		if (getPlaceManager().getRequestParameter(CALLBACK) != null && getPlaceManager().getRequestParameter(CALLBACK).equalsIgnoreCase("signup")) {
			//To show SignUp (Registration popup)
			if (AppClientFactory.isAnonymous()){
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				String type = getPlaceManager().getRequestParameter("type") ;
				int displayScreen =getPlaceManager().getRequestParameter("type") !=null  ? Integer.parseInt(type) : 1;
				signUpViewPresenter.displayPopup(displayScreen);
				addToPopupSlot(signUpViewPresenter);
			}
		}
		int flag = AppClientFactory.getLoggedInUser().getViewFlag();
		final String loginType = AppClientFactory.getLoggedInUser().getLoginType() !=null ? AppClientFactory.getLoggedInUser().getLoginType() : "";
		if(!AppClientFactory.isAnonymous() && flag==0 &&  !loginType.equalsIgnoreCase("Credential")) {
			AlmostDoneUc update = new AlmostDoneUc(AppClientFactory.getLoggedInUser().getEmailId(), AppClientFactory.getLoggedInUser());
			update.setGlassEnabled(true);
			update.show();
			update.center();
		}
	}


	@Override
	public void onBind() {
		super.onBind();
		Window.enableScrolling(true);
		setCollectionAsyncCallback(new SimpleAsyncCallback<CollectionDo>() {
			@Override
			public void onSuccess(CollectionDo collection) {
				isApiCalled= false;
				if (collection.getStatusCode()==200){
					FolderDo folderDo=getFolderDo(collection);
					if(collection.getPermissions() != null){
						if (!collection.getPermissions().toString().contains("edit")){
							errorPopup = null;
							invokeErrorPopup();
						}else{
							setCollectionContent(folderDo);
						}
					}
				}else if(collection.getStatusCode()!=400){
					errorPopup = null;
					invokeErrorPopup();
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				super.onFailure(caught);
				AppClientFactory.fireEvent(new RefreshCollectionInShelfListEvent(null, RefreshType.OPEN));
			}
		});
	}

	@Override
	public void onUnbind(){
		super.onUnbind();
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		Window.enableScrolling(true);
		version=null;
		type="Course";
	}

	@Override
	protected void onReset() {
		super.onReset();
		Window.enableScrolling(true);
		String id = getPlaceManager().getRequestParameter("id");
		String o1 = getPlaceManager().getRequestParameter("o1");
		String o2 = getPlaceManager().getRequestParameter("o2");
		String o3 = getPlaceManager().getRequestParameter("o3");

		String idParm = AppClientFactory.getPlaceManager().getRequestParameter("id") !=null && !AppClientFactory.getPlaceManager().getRequestParameter("id").equalsIgnoreCase("") ? AppClientFactory.getPlaceManager().getRequestParameter("id") : null;
		String isSuccessParm = AppClientFactory.getPlaceManager().getRequestParameter("isSuccess") !=null && !AppClientFactory.getPlaceManager().getRequestParameter("isSuccess").equalsIgnoreCase("") ? AppClientFactory.getPlaceManager().getRequestParameter("isSuccess") : null;
		if(isSuccessParm!=null && isSuccessParm.equalsIgnoreCase("true") && isSuccess){
			SetDefaultTypeAndVersion();
			isSuccess = false;
		}
		if (idParm != null && AppClientFactory.isAnonymous()){
			AppClientFactory.fireEvent(new InvokeLoginEvent());

		}else if(AppClientFactory.isAnonymous()){
			getView().setNoDataForAnonymousUser(true);
		}else{
			if (version == null || (version != null && !version.equalsIgnoreCase(AppClientFactory.getLoggedInUser().getToken()))) {
				Window.scrollTo(0,0);
				callWorkspaceApi();
				version = AppClientFactory.getLoggedInUser().getToken();
				if(o3!=null&&id==null) {
					getFolderMetaData(o3);
				} else if(o2!=null&&id==null) {
					getFolderMetaData(o2);
				} else if(o1!=null&&id==null) {
					getFolderMetaData(o1);
				}else if (idParm!=null && !isApiCalled){
					isApiCalled= true;
					getResourceService().getCollection(idParm, false, getCollectionAsyncCallback());
				}
			}
		}
	}
	private void getFolderMetaData(final String folderId) {
		if("Folder".equalsIgnoreCase(getView().getViewType())){
			AppClientFactory.getInjector().getfolderService().getFolderMetaData(folderId, new SimpleAsyncCallback<FolderDo>() {
				@Override
				public void onSuccess(FolderDo result) {
					folderMetaData=result;
					getChildFolderItems(folderId,"folder", false,null);
				}
			});
		}
	}

	/**
	 * This method will call the workspace API
	 */
	public void callWorkspaceApi(){
		getView().setNoDataForAnonymousUser(false);
		int limit;
		String view= AppClientFactory.getPlaceManager().getRequestParameter(VIEW,null);
		String typeVal=type;
		if(view!=null && view.equalsIgnoreCase(FOLDER)){
			typeVal=null;//if we are passing as null we get all the folders and collections
			type=view;
			limit=20;
		}else{
			typeVal=type;
			limit=50;
		}
		getResourceService().getFolderWorkspace(0, limit,null,typeVal,false,getUserCollectionAsyncCallback(true));
		getView().setDefaultOrganizePanel(view);
	}
	public ShelfServiceAsync getShelfService() {
		return shelfService;
	}

	public ResourceServiceAsync getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceServiceAsync resourceService) {
		this.resourceService = resourceService;
	}
	@Override
	public String getViewToken() {
		return PlaceTokens.MYCONTENT;
	}

	@Override
	public void updateResourceCount(int resourceCount) {
		//getView().updateResoureCount(resourceCount);

	}
	@Override
	public void updateWidgetsCount(CollectionItemDo collectionItem,boolean isDelete){
		getView().updateWidgetsCount(collectionItem,isDelete);
	}
	public void getUserSheldId(){
		if(!AppClientFactory.isAnonymous()){
			String userUid=AppClientFactory.getLoggedInUser().getGooruUId();
			AppClientFactory.getInjector().getResourceService().getUserShelfDetails(userUid, new SimpleAsyncCallback<String>() {
				@Override
				public void onSuccess(String result) {
					AppClientFactory.getPlaceManager().setUserShelfId(result);
				}
			});
		}
	}

	@Override
	public void getEditPageHeight(PopupPanel editQuestionPopupPanel,boolean isHeightClear) {

	}
	public SimpleAsyncCallback<FolderListDo> getUserCollectionAsyncCallback(boolean clearShelfPanel) {
		clrPanel=clearShelfPanel;
		if (userCollectionAsyncCallback == null) {
			userCollectionAsyncCallback = new SimpleAsyncCallback<FolderListDo>() {
				@Override
				public void onSuccess(FolderListDo result) {
					String o1=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
					if(o1==null){
						if(clrPanel){
							if(result !=null)
							{
							if(result.getSearchResult() !=null)
							{
							if(result.getSearchResult().size()>0){
								setRightListData(result.getSearchResult(),null);
							}else{
								setInSlot(RIGHT_SLOT, null);
							}
							}
							}
						}else{
							myCollectionsListPresenter.setData(type,result.getSearchResult(),clrPanel,false,null);
						}
					}
					getView().setUserMetaData(result.getSearchResult(),clrPanel);
				}
			};
		}
		return userCollectionAsyncCallback;
	}

	@Override
	public void getChildFolderItems(final String folderId,final String typeVal,final boolean isDataCalled,final TreeItem currentTreeItem) {
		if(isDataCalled) {
			getView().getChildFolderItems(currentTreeItem,null);
		}else{
			AppClientFactory.getInjector().getfolderService().getChildFolders((getView().getChildPageNumber()-1)*20, 20, folderId,null, null,false,new SimpleAsyncCallback<FolderListDo>() {
				@Override
				public void onSuccess(FolderListDo result) {
					searchResult.addAll(result.getSearchResult());
					if(currentTreeItem!=null){
						if(result.getSearchResult().size()==20) {
							getView().setChildPageNumber(getView().getChildPageNumber()+1);
							setPaginatedChildFolders(folderId,typeVal,isDataCalled,currentTreeItem);
						} else {
							getView().setChildPageNumber(1);
							getView().getChildFolderItems(currentTreeItem,searchResult);
							searchResult.clear();
						}
					}else{
						setRightPanelData(folderMetaData, typeVal, result.getSearchResult());
					}

				}
			});
		}
	}
	@Override
	public void getChildFolderItemsForCourse(final String courseId,final String unitId,final String lessonId,final String typeVal,final boolean isDataCalled,final TreeItem currentTreeItem) {
		if(isDataCalled) {
			getView().getChildFolderItems(currentTreeItem,null);
		}else{
			AppClientFactory.getInjector().getfolderService().getChildFoldersForCourse((getView().getChildPageNumber()-1)*20, 20,courseId, unitId, lessonId, null, null, false, new SimpleAsyncCallback<FolderListDo>() {
				@Override
				public void onSuccess(FolderListDo result) {
					searchResult.addAll(result.getSearchResult());
					if(result.getSearchResult().size()==20) {
						getView().setChildPageNumber(getView().getChildPageNumber()+1);
						setPaginatedChilds(courseId,unitId,lessonId,typeVal,isDataCalled,currentTreeItem);
					} else {
						getView().setChildPageNumber(1);
						getView().getChildFolderItems(currentTreeItem,searchResult);
						searchResult.clear();
					}
				}
			});
		}
	}
	@Override
	public void setRightPanelData(FolderDo folderObj,String clickedItemType,List<FolderDo> folderListDoChild){
		clearSlot(ShelfMainPresenter.RIGHT_SLOT);
		if(!FOLDER.equalsIgnoreCase(getView().getViewType())){
			getView().getCollectionLabel().setVisible(false);
			setTileIcon((folderObj!=null&&folderObj.getGooruOid()!=null)?folderObj.getTitle():(clickedItemType.equalsIgnoreCase("collection")?
					"Untitled Collection":clickedItemType.equalsIgnoreCase("assessment")?
							"Untitled Assessment":clickedItemType.equalsIgnoreCase("course")?
									"Untitled Course":clickedItemType.equalsIgnoreCase("unit")?"Untitled Unit":clickedItemType.equalsIgnoreCase("lesson")?"Untitled Lesson":"Untitled External Assessment"),clickedItemType);
		}else{
			getView().getCollectionLabel().setVisible(true);
			getView().getTitleIconContainer().setVisible(true);
			getView().getImgInlineLbl().setStyleName("");
			getView().getCollectionLabel().setText((folderObj!=null&&folderObj.getGooruOid()!=null)?folderObj.getTitle():(clickedItemType.equalsIgnoreCase("collection")?
					"Untitled Collection":clickedItemType.equalsIgnoreCase("assessment")?
							"Untitled Assessment":"Untitled External Assessment"));
		}
		if(folderObj!=null && folderObj.getGooruOid()!=null){
			//when displaying the existing data at that time we are opening the content tab.
			getMyCollectionsRightClusterPresenter().setFolderListDoChild(folderListDoChild);
			getMyCollectionsRightClusterPresenter().setTabItems(2, clickedItemType,folderObj);
		}else{
			//when creating the default course we are opening the info tab
			getMyCollectionsRightClusterPresenter().setTabItems(1, clickedItemType,folderObj);
		}
		setInSlot(ShelfMainPresenter.RIGHT_SLOT, getMyCollectionsRightClusterPresenter());
	}

	@Override
	public void setRightListData(List<FolderDo> listOfContent,FolderDo folderDo){
		clearSlot(RIGHT_SLOT);
		String view= AppClientFactory.getPlaceManager().getRequestParameter(VIEW,null);
		String id=AppClientFactory.getPlaceManager().getRequestParameter(ID,null);
		if(view==null){
			view=type;
		}
		if(id!=null && folderDo!=null){
			getView().getCollectionLabel().setVisible(true);
			getView().getTitleIconContainer().setVisible(true);
			setCollectionContent(folderDo);
		}else{
			getView().getCollectionLabel().setVisible(false);
			getView().getTitleIconContainer().setVisible(false);
			myCollectionsListPresenter.setData(view,listOfContent,clrPanel,false,folderDo);
			setInSlot(RIGHT_SLOT, myCollectionsListPresenter,false);
		}
	}

	private void setPaginatedChildFolders(String folderId,String typeVal, boolean isDataCalled, TreeItem currentTreeItem) {
		getChildFolderItems(folderId,typeVal, isDataCalled,currentTreeItem);
	}
	private void setPaginatedChilds(String courseId,String unitId,String lessonId,String typeVal, boolean isDataCalled,TreeItem currentTreeItem) {
		getChildFolderItemsForCourse(courseId,unitId,lessonId,typeVal,isDataCalled,currentTreeItem);
	}
	@Override
	public void refreshUserShelfCollections() {
		getResourceService().getFolderWorkspace((ShelfMainView.getpageNumber()-1)*20, 20,null,null,false,getUserCollectionAsyncCallback(true));
	}

	@Override
	public void updateLeftShelfPanelActiveStyle() {
		getView().updateLeftShelfPanelActiveStyle();
	}

	@Override
	public void setListPresenterBasedOnType(String type) {
		this.type=type;
		version=null;
		Map<String,String> params = new HashMap<String,String>();
		params.put(VIEW, type);
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
		//getResourceService().getFolderWorkspace((ShelfListView.getpageNumber()-1)*20, 20,null,type,false,getUserCollectionAsyncCallback(true));
	}

	public MyCollectionsRightClusterPresenter getMyCollectionsRightClusterPresenter() {
		return myCollectionsListPresenter.getMyCollectionsRightClusterPresenter();
	}
	@Override
	public MyCollectionsListPresenter getMyCollectionsListPresenter(){
		return  myCollectionsListPresenter;
	}

	public void invokeErrorPopup(){
		if (errorPopup == null){
			errorPopup = new ErrorPopup(i18n.GL0340());
			errorPopup.show();
			errorPopup.center();
		}
	}

	@Override
	public void getMoreListItems(int pageSize, Integer pageNumber, boolean clearShelfPanel) {
		String typeVal=type;
		if(type.equalsIgnoreCase(FOLDER)){
			typeVal=null;//if we are passing as null we get all the folders and collections
		}
		getResourceService().getFolderWorkspace((pageNumber-1)*pageSize,pageSize,null,typeVal,false,getUserCollectionAsyncCallback(clearShelfPanel));
	}

	public void createNewUnitItem(String type, TreeItem currentShelfTreeWidget) {
		getView().createNewItem(type,currentShelfTreeWidget);
	}

	public void updateTitleOfTreeWidget(FolderDo courseDo, boolean flag, TreeItem currentShelfTreeWidget) {
		getView().updateTreeWidget(courseDo, flag, currentShelfTreeWidget);
	}

	@Override
	public void setCollectionContent(FolderDo folderObj){
		clearSlot(RIGHT_SLOT);
		getMyCollectionsRightClusterPresenter().setTabItems(2, folderObj.getType(),folderObj);
		String view= AppClientFactory.getPlaceManager().getRequestParameter(VIEW,null);
		getView().getTitleIconContainer().setVisible(true);
		if(FOLDER.equalsIgnoreCase(view)){
			getView().getImgInlineLbl().setVisible(false);
			getView().getCollectionLabel().setVisible(true);
			getView().getCollectionLabel().setText(folderObj.getTitle());
		}else{
			setTileIcon((folderObj!=null&&folderObj.getGooruOid()!=null)?folderObj.getTitle():"",folderObj.getType());
		}
		setInSlot(ShelfMainPresenter.RIGHT_SLOT, getMyCollectionsRightClusterPresenter(),false);
	}
	/**
	 * To enable course button based on the boolean parameter.
	 * @param isEnable
	 */
	public void enableCreateCourseButton(boolean isEnable) {
		getView().enableDisableCourseButton(isEnable);
	}

	@Override
	public void setBreadCrumbs(HashMap<String, String> selectedWidgetsTitleType) {
		getMyCollectionsRightClusterPresenter().setMycontentBreadcrumbs(selectedWidgetsTitleType);
	}

	/**
	 * Sets all courses on right cluster.
	 * @param currentTypeView
	 */
	public void setUserAllCourses(String deletedTreeWidgetId, String currentTypeView) {
		getView().removeDeletedTreeWidget(deletedTreeWidgetId,currentTypeView);
	}

	/**
	 *
	 * @param o1CourseId
	 * @param deletedTreeWidgetId
	 * @param currentTypeView
	 */
	public void setUserAllUnits(String o1CourseId, String deletedTreeWidgetId, String currentTypeView) {
		getView().removeDeletedTreeWidget(deletedTreeWidgetId,currentTypeView);
	}

	/**
	 *
	 * @param o1CourseId
	 * @param o2UnitId
	 * @param o3LessDeletedonId
	 * @param currentTypeView
	 */
	public void setUserAllLessons(String o1CourseId, String o2UnitId,String o3LessDeletedonId, String currentTypeView) {
		getView().removeDeletedTreeWidget(o3LessDeletedonId,currentTypeView);
	}
	/**
	 *
	 * @param o1CourseId
	 * @param o2UnitId
	 * @param o3LessonId
	 * @param deletedAssessmentCollectionId
	 * @param currentTypeView
	 */
	public void setUserAllCollAssessment(String o1CourseId, String o2UnitId,String o3LessonId, String deletedAssessmentCollectionId,String currentTypeView) {
		getView().removeDeletedTreeWidget(deletedAssessmentCollectionId,currentTypeView);
	}

	/**
	 * This is used to set the bread crumbs after delete.
	 */
	@Override
	public void onDeleteSetBreadCrumbs(String title, String course) {
		getMyCollectionsRightClusterPresenter().getView().setOnDeleteBreadCrumbs(title,course);
	}
	@Override
	public void setVersion() {
		version=null;
	}

	@Override
	public void SetDefaultTypeAndVersion() {
		version=null;
		type="Course";
		isSuccess=true;
		ShelfMainView.pageNumber=1;
	}
	@Override
	public void addNewContent(String type) {
		getMyCollectionsRightClusterPresenter().addNewContent(type);
	}

	public void setTileIcon(String title, String type) {
		getView().setViewTitleWthIcon(title,type);
	}

	public SimpleAsyncCallback<CollectionDo> getCollectionAsyncCallback() {
		return collectionAsyncCallback;
	}

	/**
	 * @param collectionAsyncCallback
	 *            instance of {@link CollectionDo}
	 */
	public void setCollectionAsyncCallback(SimpleAsyncCallback<CollectionDo> collectionAsyncCallback) {
		this.collectionAsyncCallback = collectionAsyncCallback;
	}

	public TreeItem getEditingWidget() {
		return getView().getCurrentEditingWidget();
	}

	public void showLastEditCollaborater(String lastEditedBy,
			boolean hasLastModifiedUser) {
		getView().showLastEditCollaborater(lastEditedBy,hasLastModifiedUser);
	}
	/**
	 * To convert collection do to FolderDo obj
	 * @param collectionDo
	 * @return folderDo
	 */
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
	public void loadMyContentData(String type) {
		getView().invokeSpinner();
		setListPresenterBasedOnType(type);
	}

}
