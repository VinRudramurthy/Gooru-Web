package org.ednovo.gooru.client.mvp.gsearch.addResourcePopup;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.folder.CourseSummaryDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.client.mvp.gsearch.util.SuccessPopupForResource;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.shelf.list.TreeMenuImages;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;


public class SearchAddResourceToCollectionView extends PopupViewWithUiHandlers<SearchAddResourceToCollectionUiHandlers> implements IsSearchAddResourceToCollectionView,ClientConstants {

	private static SearchAddResourceToCollectionViewUiBinder uiBinder = GWT
			.create(SearchAddResourceToCollectionViewUiBinder.class);

	interface SearchAddResourceToCollectionViewUiBinder extends
	UiBinder<Widget, SearchAddResourceToCollectionView> {
	}

	@UiField HTMLPanel floderTreeContainer,remixPopupTabPnl,myCourseContainer;
	@UiField Anchor cancelResourcePopupBtnLbl,mycollectionsLbl,mycontentLbl;
	@UiField ScrollPanel dropdownListContainerScrollPanel;
	@UiField Button btnAddNew,btnAddExisting;
	@UiField Label lblEmptyErrorMessage,lblError;
	@UiField public Label myCollDefault,addtocollHeaderText,addingTextLbl;

	SuccessPopupForResource successPopup=new SuccessPopupForResource();

	private int limit=20;
	private int totalHitCount=0;
	private int pageNum=1;
	private FolderTreeItem previousFolderSelectedTreeItem = null;
	private CollectionTreeItem previousSelectedItem = null;
	private FolderTreeItem currentFolderSelectedTreeItem = null;
	private CollectionTreeItem cureentcollectionTreeItem = null;
	private static final String ASSESSMENT = "assessment";
	String currentsearchType = "collection";

	boolean isFromMyCourse= false;
	boolean isFromCopyResource= false;
	private boolean isCopySelected= false;
	private boolean isMoveSelected= false;
	private String selectedType="";

	HashMap<String,String> urlparams ;
	private static final String O1_LEVEL = "o1";
	private static String O2_LEVEL = "o2";
	private static String O3_LEVEL = "o3";
	private static String ID="id";
	private static final String COURSE = "Course";
	private static final String UNIT = "Unit";
	private static final String FOLDER = "folder";
	private String courseId=null;
	private String unitId=null;
	private String lessonId=null;
	private String copyType;

	private static  final String LOADER_IMAGE = "images/core/B-Dot.gif";

	boolean isTopMostSelected =false,isAddingInProgress=true;

	static MessageProperties i18n = GWT.create(MessageProperties.class);

	PopupPanel appPopUp;

	private Tree folderTreePanel = new Tree(new TreeMenuImages()){
		@Override
		public void onBrowserEvent(Event event) {
			int eventType = DOM.eventGetType(event);
			if(!(eventType==Event.ONKEYUP||eventType==Event.ONKEYPRESS||eventType==Event.ONKEYDOWN)) {
				super.onBrowserEvent(event);
			}
		}
	};
	
	
	@Inject
	public SearchAddResourceToCollectionView(EventBus eventBus) {
		super(eventBus);
		appPopUp=new PopupPanel();
		appPopUp.setWidget(uiBinder.createAndBindUi(this));
		btnAddNew.setVisible(false);
		appPopUp.setGlassEnabled(true);
		appPopUp.getElement().getStyle().setZIndex(999999);
		floderTreeContainer.clear();
		folderTreePanel.add(loadingImage());
		floderTreeContainer.add(folderTreePanel);
		dropdownListContainerScrollPanel.addScrollHandler(new ScrollDropdownListContainer());
		dropdownListContainerScrollPanel.getElement().setId("sbDropDownListContainer");
		folderTreePanel.getElement().setId("addResourcefolderTreePanel");
		lblEmptyErrorMessage.setVisible(false);
		lblEmptyErrorMessage.getElement().getStyle().setPadding(0, Unit.PX);
		lblError.setVisible(false);
		remixPopupTabPnl.getElement().setId("gShelfMainContainer");
		mycollectionsLbl.setText(i18n.GL0180());
		mycontentLbl.setText(i18n.GL3285());
		urlparams= new HashMap<String, String>();
		myCollDefault.setVisible(false);
		btnAddExisting.setEnabled(true);
		btnAddExisting.setStyleName("primary");
		mycollectionsLbl.addClickHandler(new OnClickMyCollectionsLabel());
		mycontentLbl.addClickHandler(new OnClickMyContentLabel());
		folderTreePanel.addSelectionHandler(new OnClickFolderTreePanel());
	}
	
	
	/**
	 * @return the appPopUp
	 */
	@Override
	public PopupPanel getAppPopUp() {
		return appPopUp;
	}

	@Override
	public void reset() {
	}

	@Override
	public void onLoad() {
		isAddingInProgress=true;
		cureentcollectionTreeItem=null;
		currentFolderSelectedTreeItem=null;
	}

	@Override
	public void onUnload() {

	}

	private void removePreviousSelectedItem() {
		if (previousFolderSelectedTreeItem != null) {
			previousFolderSelectedTreeItem
			.removeStyleName("selected");
		}
		if (previousSelectedItem != null) {
			previousSelectedItem
			.removeStyleName("selected");
		}
	}

	
	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	
	
	

	@Override
	public void displayWorkspaceData(FolderListDo folderListDo,boolean clearShelfPanel,String searchType) {
		if(clearShelfPanel){
			folderTreePanel.add(loadingImage());
			pageNum=1;
			folderTreePanel.clear();
			setPopupTitle();
		}
		if(folderListDo!=null){
			List<FolderDo> foldersArrayList=folderListDo.getSearchResult();
			if(foldersArrayList!=null&&foldersArrayList.size()>0){
				for(int i=0;i<foldersArrayList.size();i++){
					FolderDo floderDo=foldersArrayList.get(i);
					if(!floderDo.getType().equals("collection") && !floderDo.getType().equals("scollection") && !floderDo.getType().equals("assessment") &&  !floderDo.getType().equals("assessment/url")){
						TreeItem folderItem=new TreeItem(new FolderTreeItem(null,floderDo.getTitle(),floderDo.getGooruOid(),floderDo));
						folderTreePanel.addItem(folderItem);
						adjustTreeItemStyle(folderItem);
					}else if(floderDo.getType().equals("collection") || ("scollection").equals(floderDo.getType()) || ("assessment").equals(floderDo.getType()) || ("assessment/url").equals(floderDo.getType())){
						String resourceInstanceId = AppClientFactory.getPlaceManager().getRequestParameter("rid");
						String nameToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
						if(nameToken.equalsIgnoreCase(PlaceTokens.SEARCH_RESOURCE) || nameToken.equalsIgnoreCase(PlaceTokens.RESOURCE_PLAY )
								|| (nameToken.equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY) && resourceInstanceId!=null)
								|| (nameToken.equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY) && resourceInstanceId!=null) || isFromCopyResource){
							String collectionType=floderDo.getCollectionType().equals(ASSESSMENT)?floderDo.getCollectionType():floderDo.getType();
							TreeItem folderItem=new TreeItem(new CollectionTreeItem(null,floderDo.getTitle(),floderDo.getGooruOid(),collectionType));
							folderTreePanel.addItem(folderItem);
							adjustTreeItemStyle(folderItem);
						}
					}

				}
			}
		}
		currentsearchType=searchType;
		if(folderListDo.getCount()!=null){
			totalHitCount = folderListDo.getCount();
		}
		btnAddExisting.setVisible(true);
		dropdownListContainerScrollPanel.setVisible(true);
		lblEmptyErrorMessage.setVisible(false);
		lblError.setVisible(false);
		if(searchType.equals("collection")){
			isTopMostSelected =true;
			currentFolderSelectedTreeItem=null;
		}else{
			isAddingInProgress=true;
			cureentcollectionTreeItem=null;
		}
		lblEmptyErrorMessage.getElement().getStyle().setPadding(0, Unit.PX);
	}
	
	
	
	
	public void displayWorkspaceData(TreeItem item, FolderListDo folderListDo) {
		if(folderListDo!=null){
			List<FolderDo> foldersArrayList=folderListDo.getSearchResult();
			if(foldersArrayList!=null&&foldersArrayList.size()>0){
				FolderTreeItem folderTreeItemWidget=(FolderTreeItem)item.getWidget();
				int folderLevel=folderTreeItemWidget.getFolerLevel();
				for(int i=0;i<foldersArrayList.size();i++){
					FolderDo floderDo=foldersArrayList.get(i);

					if(!floderDo.getType().equals("collection") && !floderDo.getType().equals("scollection") && !floderDo.getType().equals("assessment") && !floderDo.getType().equals("assessment/url")){
						FolderTreeItem innerFolderTreeItem=new FolderTreeItem(folderLevel+"",floderDo.getTitle(),floderDo.getGooruOid(),floderDo);
						innerFolderTreeItem.setFolerLevel(folderLevel+1);
						TreeItem folderItem=new TreeItem(innerFolderTreeItem);
						item.addItem(folderItem);
						adjustTreeItemStyle(folderItem);
					}else if(floderDo.getType().equals("collection") || ("scollection").equals(floderDo.getType()) || ("assessment").equals(floderDo.getType()) || ("assessment/url").equals(floderDo.getType())){
						if(getStatus()){
							String collectionType=floderDo.getCollectionType().equals(ASSESSMENT)?floderDo.getCollectionType():floderDo.getType();
							TreeItem folderItem=new TreeItem(new CollectionTreeItem(getTreeItemStyleName(folderLevel),floderDo.getTitle(),floderDo.getGooruOid(),collectionType));
							item.addItem(folderItem);
							adjustTreeItemStyle(folderItem);
						}
					}
				}
				item.setState(folderTreeItemWidget.isOpen());
			}
		}
	}
	
	@Override
	public void setFolderItems(TreeItem item, FolderListDo folderListDo) {
		displayWorkspaceData(item,folderListDo);
	}

	@Override
	public void displayNoCollectionsMsg(String searchType){
		setPopupTitle();
		if(searchType == null){
			disableAddButton();
			dropdownListContainerScrollPanel.setVisible(false);
			lblEmptyErrorMessage.getElement().getStyle().clearPadding();
			lblEmptyErrorMessage.setVisible(true);
			lblEmptyErrorMessage.setText(i18n.GL3462_21());
		}
		else if(FOLDER.equalsIgnoreCase(searchType)){
			enableAddButton();
			dropdownListContainerScrollPanel.setVisible(false);
			lblEmptyErrorMessage.getElement().getStyle().clearPadding();
			lblEmptyErrorMessage.setVisible(true);
			lblEmptyErrorMessage.setText(i18n.GL3462_23());
		}else if(COURSE.equalsIgnoreCase(searchType)){
			disableAddButton();
			dropdownListContainerScrollPanel.setVisible(false);
			lblEmptyErrorMessage.getElement().getStyle().clearPadding();
			lblEmptyErrorMessage.setVisible(true);
			lblEmptyErrorMessage.setText(i18n.GL3462_22());
			folderTreePanel.clear();
		}
	}
	private  void adjustTreeItemStyle(final UIObject uiObject) {
		if (uiObject instanceof TreeItem) {
			if (uiObject != null && uiObject.getElement() != null) {
				Element element = uiObject.getElement();
				element.getStyle().setPadding(0, Unit.PX);
				element.getStyle().setMarginLeft(0, Unit.PX);
			}
		} else {
			if (uiObject != null && uiObject.getElement() != null && uiObject.getElement().getParentElement() != null
					&& uiObject.getElement().getParentElement().getParentElement() != null
					&& uiObject.getElement().getParentElement().getParentElement().getStyle() != null) {
				Element element = uiObject.getElement().getParentElement().getParentElement();
				element.getStyle().setPadding(0, Unit.PX);
				element.getStyle().setMarginLeft(0, Unit.PX);
			}
		}
	}
	
	
	private String  getTreeItemStyleName(int folderLevel){
		if(folderLevel==1){
			return "parent";
		}else if(folderLevel==2){
			return "child";
		}else{
			return "innerchild";
		}
	}
	
	
	public class FolderTreeItem extends Composite{
		private FlowPanel folderContainer=null;
		private String selectedFolderName=null;
		private String gooruOid=null,folderTitle=null;
		private CourseSummaryDo courseSummary;
		Label floderName=null;
		Label arrowLabel=null;
		private boolean isOpen=false;
		private boolean isApiCalled=false;
		private int folerLevel=1;
		private String type="";
		public FolderTreeItem(){
			initWidget(folderContainer=new FlowPanel());
			folderContainer.setStyleName("foldermenuLevel");
		}
		
		public FolderTreeItem(String levelStyleName,String folderTitle,String gooruOid,FolderDo floderDo){
			this();
			if(floderDo.getType()!=null){
				if(floderDo.getType().equalsIgnoreCase(COURSE)){
					folderContainer.removeStyleName("foldermenuLevel");
					floderTreeContainer.getElement().setId("gShelfMainContainer");
					folderContainer.addStyleName("folderLevel");
					folderContainer.addStyleName("course");
					folderContainer.getElement().getStyle().setBackgroundColor("white");
					isFromMyCourse=true;
				}else if(floderDo.getType().equalsIgnoreCase(UNIT)) {
					folderContainer.removeStyleName("foldermenuLevel");
					floderTreeContainer.getElement().setId("gShelfMainContainer");
					folderContainer.addStyleName("folderLevel");
					folderContainer.addStyleName("unit");
					folderContainer.getElement().getStyle().setBackgroundColor("white");
					isFromMyCourse=true;
				}else if(floderDo.getType().equalsIgnoreCase(LESSON)) {
					folderContainer.removeStyleName("foldermenuLevel");
					floderTreeContainer.getElement().setId("gShelfMainContainer");
					folderContainer.addStyleName("folderLevel");
					folderContainer.addStyleName("lesson");
					folderContainer.getElement().getStyle().setBackgroundColor("white");
					isFromMyCourse=true;
				}else if(!floderDo.getType().equals(FOLDER)) {
					folderContainer.addStyleName(COLLECTION);
					folderContainer.removeStyleName("folderLevel");
					folderContainer.removeStyleName("course");
					/*isFromMyCourse=false;*/
				}else{
					if(levelStyleName!=null){
						folderContainer.addStyleName("foldermenuLevel"+levelStyleName);
					}
				}
			}
			this.gooruOid=gooruOid;
			this.selectedFolderName = folderTitle;
			this.folderTitle=folderTitle;
			this.type = floderDo.getType();
			this.courseSummary=floderDo.getSummary();
			folderContainer.getElement().setInnerText(folderTitle);
		}
		
		
		public boolean isOpen() {
			return isOpen;
		}
		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
		}
		public String getGooruOid(){
			return gooruOid;
		}
		public String getFolderTitle(){
			return folderTitle;
		}
		public boolean isApiCalled() {
			return isApiCalled;
		}
		public void setApiCalled(boolean isApiCalled) {
			this.isApiCalled = isApiCalled;
		}
		public int getFolerLevel() {
			return folerLevel;
		}
		public String getSelectedFolerTitle() {
			return selectedFolderName;
		}
		public void setFolerLevel(int folerLevel) {
			this.folerLevel = folerLevel;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public CourseSummaryDo getCourseSummary() {
			return courseSummary;
		}
		public void setCourseSummary(CourseSummaryDo courseSummary) {
			this.courseSummary = courseSummary;
		}
	}
	
	
	public class CollectionTreeItem extends Composite{
		private FlowPanel folderContainer=null;
		Label folderName=null;
		private String collectionName=null;
		private String gooruOid=null;
		private String collectionType;
		private boolean isOpen=false;
		public CollectionTreeItem(){
			initWidget(folderContainer=new FlowPanel());
			folderContainer.setStyleName("foldercollection");
		}
		public CollectionTreeItem(String levelStyleName){
			this();
			folderContainer.addStyleName(levelStyleName);
		}
		public CollectionTreeItem(String levelStyleName,String folderTitle,String gooruOid,String collectionType){
			this();
			if(ASSESSMENT.equals(collectionType)){
				folderContainer.setStyleName("folderAssessment");
			}
			if(levelStyleName!=null){
				folderContainer.addStyleName(levelStyleName);
			}
			this.gooruOid=gooruOid;
			this.collectionName=folderTitle;
			this.collectionType=collectionType;
			folderContainer.getElement().setInnerText(folderTitle);
		}
		public boolean isOpen() {
			return isOpen;
		}
		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
		}
		public String getcollectionType(){
			return collectionType;
		}
		public String getGooruOid(){
			return gooruOid;
		}
		public String getCollectionName(){
			return collectionName;
		}
	}
	
	
	
	@UiHandler("cancelResourcePopupBtnLbl")
	public void cancelButtonEvent(ClickEvent event){
		hide();
		enableTopFilters();
	}
	public void getFolderItems(TreeItem item,String parentId){
		getUiHandlers().getFolderItems(item,parentId);
	}

	@UiHandler("btnAddExisting")
	public void addResourceToCollection(ClickEvent event){
		if(getStatus()){
			if(cureentcollectionTreeItem!=null){
				boolean flag = getUiHandlers().validateIsAssessments(cureentcollectionTreeItem.getcollectionType());
				if(flag){
					lblError.setVisible(false);
					getUiHandlers().addResourceToCollection(cureentcollectionTreeItem.getGooruOid(), "resource",cureentcollectionTreeItem.getCollectionName(),this.urlparams,isFromMyCourse);
				}else{
					lblError.setVisible(true);
					lblError.setText(i18n.GL3462_19());
					isAddingInProgress=true;
				}
			}else{
				lblError.setVisible(true);
				isAddingInProgress=true;
			}
		}else{
			if(isFromMyCourse){
				if(currentFolderSelectedTreeItem!=null){
					FolderTreeItem item =currentFolderSelectedTreeItem;
					if(item.getCourseSummary()!=null && item.getCourseSummary().getCollectionCount()!=null && item.getCourseSummary().getCollectionCount()>=10){
						restrictionToAddResourcesData(i18n.GL3491());
					}else{
						/*if(COURSE.equalsIgnoreCase(currentFolderSelectedTreeItem.getType()) || UNIT.equalsIgnoreCase(currentFolderSelectedTreeItem.getType())){
							restrictionToAddResourcesData("You can add Collections only to Lesson Level");
						}else if(LESSON.equalsIgnoreCase(currentFolderSelectedTreeItem.getType())){*/
							if(isMoveSelected){
								getUiHandlers().moveCollectionTOLesson("","",this.urlparams);
							}else{
								if(UNIT.equalsIgnoreCase(copyType)){
									if(item.getCourseSummary()!=null && item.getCourseSummary().getUnitCount()!=null&&item.getCourseSummary().getUnitCount()>=20){
										lblError.setText("Unit limit reached");
										lblError.setVisible(true);
										enableDisableAddBtn(false);
										
									}else{
										getUiHandlers().copyUnitToCourse(this.urlparams,AppClientFactory.getPlaceManager().getRequestParameter("o2",null));
									}
								}else if(LESSON.equalsIgnoreCase(copyType)){
									if(item.getCourseSummary()!=null && item.getCourseSummary().getUnitCount()!=null&&item.getCourseSummary().getUnitCount()>=20){
										lblError.setText("Lesson limit reached");
										lblError.setVisible(true);
										enableDisableAddBtn(false);
										
									}else{
										getUiHandlers().copyLessonToUnit(this.urlparams,AppClientFactory.getPlaceManager().getRequestParameter("o3",null));
									}
								}else{
									getUiHandlers().copyCollectionToLession("","",this.urlparams);
								}
							}
//						}
					}
				}/*else{
					restrictionToAddResourcesData("Please select lesson type to add a collection");
				}*/
			}else{
				String collectionId= urlparams.get(ID);
				if(isTopMostSelected){
					this.urlparams.clear();
					if(isMoveSelected){
						getUiHandlers().moveCollectionToMyCOllections(collectionId, null, currentsearchType,"",null);
					}else{
						getUiHandlers().CopyToplevelMyCollections(collectionId, null, currentsearchType, "",null);
					}
				}else if(FOLDER.equalsIgnoreCase(currentFolderSelectedTreeItem.getType())){
					collectionId= urlparams.get(O1_LEVEL);
					if(isMoveSelected){
						getUiHandlers().moveCollectionToMyCOllections(collectionId, currentFolderSelectedTreeItem.getGooruOid(), currentsearchType,"",this.urlparams);
					}else{
						getUiHandlers().CopyToplevelMyCollections(collectionId, currentFolderSelectedTreeItem.getGooruOid(), currentsearchType, "",this.urlparams);
					}
				}
			}
		}
	}
	
	
	@Override
	public Button getAddButton(){
		return btnAddNew;
	}
	
	
	@Override
	public void hidePopup(){
		Element element = Document.get().getElementById("fixedFilterSearchID");
		if(element!=null){
			element.removeAttribute("style");
		}
		hide();
		isAddingInProgress=true;
		enableTopFilters();
	}



	@Override
	public void setDefaultPanelVisibility(Boolean blnVal){
		pageNum=1;
	}

	@Override
	public void displaySuccessPopup(String collectionName,String selectedGooruOid,HashMap<String, String> params,String searchType,FolderDo folderDo) {
		isAddingInProgress=true;
		hide();
		successPopup.setData(collectionName, selectedGooruOid,params,searchType,folderDo);
		successPopup.setGlassEnabled(true);
		successPopup.getElement().getStyle().setZIndex(999999);
		successPopup.setGlassStyleName("setGlassPanelZIndex");
		successPopup.show();
		successPopup.center();
	}

	public void enableTopFilters(){
		Element element = Document.get().getElementById("fixedFilterSearchID");
		if(element!=null){
			element.removeAttribute("style");
		}
		Window.enableScrolling(true);
	}
	@Override
	public void restrictionToAddResourcesData(String message) {
		lblError.setText(message);
		lblError.setVisible(true);
	}



	@Override
	public void clearUrlParams() {
		urlparams.clear();
	}

	@Override
	public void setCopyAndMoveStatus(boolean isCopySelected,boolean isMoveSelected,String selectedType) {
		
		this.isCopySelected=isCopySelected;
		this.isMoveSelected=isMoveSelected;
		this.selectedType=selectedType;
		mycollectionsLbl.removeStyleName("active");
		mycontentLbl.addStyleName("active");
		isFromMyCourse=true;
	}


	public boolean isFromMyCourse() {
		return isFromMyCourse;
	}

	@Override
	public void setFromMyCourse(boolean value) {
		this.isFromMyCourse = value;
	}

	public void disableAddButton(){
		btnAddExisting.setEnabled(false);
		btnAddExisting.getElement().addClassName("disabled");
	}

	@Override
	public void enableAddButton() {
		btnAddExisting.setEnabled(true);
		btnAddExisting.getElement().removeClassName("disabled");
	}

	@Override
	public Label getMycollectionsDefaultLbl() {
		return myCollDefault;
	}
	
	
	@Override
	public Image loadingImage(){
		Image loadingImage =  new Image();
		loadingImage.setUrl(LOADER_IMAGE);
		loadingImage.getElement().setId("myCollectionsListViewLoaddingImage");
		return loadingImage;
	}

	@Override
	public Anchor getMycollectionsLbl() {
		return mycollectionsLbl;
	}

	@Override
	public Anchor getMycontentLbl() {
		return mycontentLbl;
	}

	@Override
	public void isFromCopyResource(boolean isFromCopyResource) {
		this.isFromCopyResource=isFromCopyResource;
	}

	@Override
	public void closeTabView() {
		hide();
		hideResourceAddPopup();
	}
	
	
	public void hideResourceAddPopup(){
		String viewToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
		String resourceId=AppClientFactory.getPlaceManager().getRequestParameter("rid", null);
		String folderId=AppClientFactory.getPlaceManager().getRequestParameter("folderId", null);
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionId);
		params = PreviewPlayerPresenter.setConceptPlayerParameters(params);
		params.put("rid", resourceId);
		params.put("folderId", folderId);
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(viewToken, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	
	
	public void setPopupTitle(){

		if(getStatus()){
			addtocollHeaderText.setText(i18n.GL3224());
			addingTextLbl.setText(i18n.GL3462_18());
		}else{
			if(isCopySelected){
				if("collection".equalsIgnoreCase(this.selectedType)){
					addtocollHeaderText.setText(i18n.GL3462_13());
					addingTextLbl.setText(i18n.GL3462_14());
				}else if(!StringUtil.isEmpty(copyType)&& UNIT.equalsIgnoreCase(copyType)){
					addtocollHeaderText.setText(i18n.GL3510());
					addingTextLbl.setText(i18n.GL3509());
				}else if(!StringUtil.isEmpty(copyType)&& LESSON.equalsIgnoreCase(copyType)){
					addtocollHeaderText.setText(i18n.GL3512());
					addingTextLbl.setText(i18n.GL3511());
				}else{
					addtocollHeaderText.setText(i18n.GL3462_13_1());
					addingTextLbl.setText(i18n.GL3462_14_1());
				}
			}else if(isMoveSelected){
				if("collection".equalsIgnoreCase(this.selectedType)){
					addtocollHeaderText.setText(i18n.GL3462_15());
					addingTextLbl.setText(i18n.GL3462_16());
				}else{
					addtocollHeaderText.setText(i18n.GL3462_15_1());
					addingTextLbl.setText(i18n.GL3462_16_1());
				}
			}else{
				addtocollHeaderText.setText(i18n.GL3223());
				addingTextLbl.setText(i18n.GL3462_17());
			}
		}
	}
	
	
	private class ScrollDropdownListContainer implements ScrollHandler{
		@Override
		public void onScroll(ScrollEvent event) {
			if((dropdownListContainerScrollPanel.getVerticalScrollPosition() == dropdownListContainerScrollPanel.getMaximumVerticalScrollPosition() && folderTreePanel.getItemCount()>=20)){
				if(isFromMyCourse){
					currentsearchType="coursebuilder";
				}else{
					currentsearchType = getStatus()?"resource":"collection";
				}
				getUiHandlers().getWorkspaceData(pageNum*limit, limit,false,currentsearchType);
				pageNum++;
			}
		}
	}

	public class OnClickMyCollectionsLabel implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			urlparams.clear();
			pageNum=1;
			mycollectionsLbl.addStyleName("active");
			mycontentLbl.removeStyleName("active");
			folderTreePanel.clear();
			folderTreePanel.add(loadingImage());
			isFromMyCourse=false;
			btnAddExisting.setEnabled(true);
			btnAddExisting.setStyleName("primary");

			if(getStatus()){
				isTopMostSelected=false;
				removePreviousSelectedItem();
				getUiHandlers().getWorkspaceData(0, 20, true, "resource");
			}else{
				isTopMostSelected=true;
				getUiHandlers().getWorkspaceData(0, 20, true, "collection");
			}

		}
	}


	public boolean getStatus() {
		String nameToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		String resourceInstanceId = AppClientFactory.getPlaceManager().getRequestParameter("rid");
		boolean flag=false;
		
		if(nameToken.equalsIgnoreCase(PlaceTokens.SEARCH_RESOURCE)|| nameToken.equalsIgnoreCase(PlaceTokens.RESOURCE_PLAY )
				|| (nameToken.equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY) && resourceInstanceId!=null)
				|| (nameToken.equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY) && resourceInstanceId!=null) || isFromCopyResource){
			flag = true;

		}
		return flag;
	}

	public class OnClickMyContentLabel implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			pageNum=1;
			urlparams.clear();
			mycontentLbl.addStyleName("active");
			mycollectionsLbl.removeStyleName("active");
			btnAddExisting.setEnabled(true);
			btnAddExisting.setStyleName("primary");
			isTopMostSelected=false;
			folderTreePanel.clear();
			folderTreePanel.add(loadingImage());
			isFromMyCourse=true;
			getUiHandlers().getWorkspaceData(0, 20, true, "coursebuilder");
		}

	}

	public class OnClickFolderTreePanel implements SelectionHandler<TreeItem>{

		@Override
		public void onSelection(SelectionEvent<TreeItem> event) {
			isTopMostSelected = false;
			lblError.setVisible(false);
			lblError.setText(i18n.GL1134());
			enableDisableAddBtn(true);
			final TreeItem item = (TreeItem) event.getSelectedItem();
			Widget folderWidget= item.getWidget();
			FolderTreeItem folderTreeItemWidget=null;
			if(folderWidget instanceof FolderTreeItem){
				folderTreeItemWidget = (FolderTreeItem) folderWidget;
				String foldertypevalue=	folderTreeItemWidget.getType();
				if(myCourseContainer.isVisible()){
					enableDisableAddBtn((COURSE.equalsIgnoreCase(foldertypevalue) || UNIT.equalsIgnoreCase(foldertypevalue))?false:true);
				}else{
					if(LESSON.equalsIgnoreCase(copyType)&&(COURSE.equalsIgnoreCase(foldertypevalue))){
						enableDisableAddBtn(false);
					}
					if(LESSON.equalsIgnoreCase(copyType)&&(UNIT.equalsIgnoreCase(foldertypevalue))){
						enableDisableAddBtn(true);
					}
					/*enableDisableAddBtn(((UNIT.equalsIgnoreCase(copyType) || LESSON.equalsIgnoreCase(copyType))&&!((COURSE.equalsIgnoreCase(foldertypevalue)))?true:false));
					if(UNIT.equalsIgnoreCase(copyType)){
						enableDisableAddBtn(true);
					}else if(LESSON.equalsIgnoreCase(copyType)){
						enableDisableAddBtn(true);
					}*/
				}
				
				if (folderTreeItemWidget.isOpen()) {
					folderTreeItemWidget.removeStyleName("open");
					folderTreeItemWidget.setOpen(false);
				}else {
					folderTreeItemWidget.addStyleName("open");
					folderTreeItemWidget.setOpen(true);
				}
				removePreviousSelectedItem();
				currentFolderSelectedTreeItem = folderTreeItemWidget;
				previousFolderSelectedTreeItem = currentFolderSelectedTreeItem;
				currentFolderSelectedTreeItem.addStyleName("selected");
				previousSelectedItem = cureentcollectionTreeItem = null;
				TreeItem parent = item.getParentItem();
				item.getTree().setSelectedItem(parent, false);
				if(currentFolderSelectedTreeItem.getFolerLevel()==1) {
					urlparams.clear();
					urlparams.put(O1_LEVEL, folderTreeItemWidget.getGooruOid());
				}
				if(currentFolderSelectedTreeItem.getFolerLevel()==2) {
					urlparams.put(O1_LEVEL, urlparams.get(O1_LEVEL));
					urlparams.put(O2_LEVEL, folderTreeItemWidget.getGooruOid());
					if(O3_LEVEL!=null){
						urlparams.remove(O3_LEVEL);
					}
				}
				if(currentFolderSelectedTreeItem.getFolerLevel()==3) {
					urlparams.put(O1_LEVEL, urlparams.get(O1_LEVEL));
					urlparams.put(O2_LEVEL, urlparams.get(O2_LEVEL));
					urlparams.put(O3_LEVEL, folderTreeItemWidget.getGooruOid());
				}
				
				
				if (!folderTreeItemWidget.isApiCalled()) {
					folderTreeItemWidget.setApiCalled(true);
					String typevalue=	folderTreeItemWidget.getType();
					if(FOLDER.equalsIgnoreCase(typevalue)){
						getFolderItems(item, folderTreeItemWidget.getGooruOid());
					}else if(!StringUtil.isEmpty(copyType)&& UNIT.equalsIgnoreCase(copyType)){
					}else if(!StringUtil.isEmpty(copyType)&& LESSON.equalsIgnoreCase(copyType)){ 
						courseId=urlparams.get(O1_LEVEL);
						if(!UNIT.equalsIgnoreCase(typevalue)){
							getUiHandlers().getCourseItems(item,courseId,unitId,lessonId,typevalue);
						}
					}else{
						courseId=urlparams.get(O1_LEVEL);
						unitId=urlparams.get(O2_LEVEL);
						lessonId=urlparams.get(O3_LEVEL);
						getUiHandlers().getCourseItems(item,courseId,unitId,lessonId,typevalue);
					}
				}
				if (parent != null){
					parent.setSelected(false);
				}
				item.setState(!item.getState(), false);
				
			}else if(folderWidget instanceof CollectionTreeItem){
				removePreviousSelectedItem();
				cureentcollectionTreeItem=(CollectionTreeItem) folderWidget;
				previousSelectedItem = cureentcollectionTreeItem;
				cureentcollectionTreeItem.addStyleName("selected");
				previousFolderSelectedTreeItem = currentFolderSelectedTreeItem = null;

				String style = cureentcollectionTreeItem.getStyleName();
				if(style != null && !style.contains("parent") && !style.contains("child") && !style.contains("innerchild")){
					urlparams.clear();
				}
			}
		}

	}
	
	
	public void enableDisableAddBtn(boolean btnStatus) {
		if(btnStatus){
			btnAddExisting.setEnabled(true);
			btnAddExisting.getElement().removeClassName("disabled");
		}else{
			btnAddExisting.setEnabled(false);
			btnAddExisting.getElement().addClassName("disabled");
		}
	}


	@Override
	public void disableTabs(boolean isVisible,String copyType) {
		if(UNIT.equalsIgnoreCase(copyType)||LESSON.equalsIgnoreCase(copyType)){
			this.copyType=copyType;
		}else{
			this.copyType=null;
		}
		myCourseContainer.setVisible(isVisible);
		
		
	}

}
