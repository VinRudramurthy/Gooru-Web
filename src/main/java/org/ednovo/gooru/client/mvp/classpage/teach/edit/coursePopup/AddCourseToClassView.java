package org.ednovo.gooru.client.mvp.classpage.teach.edit.coursePopup;

import java.util.HashMap;
import java.util.List;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.gsearch.util.SuccessPopupForResource;
import org.ednovo.gooru.client.mvp.shelf.list.TreeMenuImages;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.H4Panel;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
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

public class AddCourseToClassView extends PopupViewWithUiHandlers<AddCourseToClassUiHandlers> implements IsAddCourseToClassView,ClientConstants {

	private static SearchAddResourceToCollectionViewUiBinder uiBinder = GWT
			.create(SearchAddResourceToCollectionViewUiBinder.class);

	interface SearchAddResourceToCollectionViewUiBinder extends
			UiBinder<Widget, AddCourseToClassView> {
	}

	@UiField HTMLPanel floderTreeContainer,emptyCourseBlockContainer,footerPanel;
	@UiField Button cancelResourcePopupBtnLbl,gotoMyContent;
	@UiField Anchor btnAddNew;
	@UiField ScrollPanel dropdownListContainerScrollPanel;
	@UiField Button btnAddExisting,assignBtn,cancel;
	@UiField H4Panel addtocollHeaderText;

	@UiField Label lblEmptyErrorMessage,lblError;

	@UiField HTML addingTextLbl;

	@UiField HTMLPanel popupContainer,assignCourseBlockContainer;

	String classId;

	String currentsearchType="class";

	@UiField InlineLabel bluedotLbl,greenLbl,orangeLbl,courseNotesLbl;


	@UiField H3Panel assginCourse,createCourseHeader;


	SuccessPopupForResource successPopup=new SuccessPopupForResource();

	private int limit=20;
	private int pageNum=1;
	private CourseTreeItem currectCourseSelectedTreeItem = null;
	private CourseTreeItem previousCourseSelectedTreeItem = null;
	HashMap<String,String> urlparams ;
	boolean isTopMostSelected =true,isAddingInProgress=true;
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
	public AddCourseToClassView(EventBus eventBus) {
		super(eventBus);
		appPopUp=new PopupPanel();
		appPopUp.setWidget(uiBinder.createAndBindUi(this));
		appPopUp.setGlassEnabled(true);
		appPopUp.getElement().getStyle().setZIndex(999999);
		assignCourseBlockContainer.setVisible(false);
		cancel.setVisible(false);
		assignBtn.setVisible(false);
		dropdownListContainerScrollPanel.setVisible(false);
		floderTreeContainer.clear();
		floderTreeContainer.add(folderTreePanel);
		dropdownListContainerScrollPanel.addScrollHandler(new ScrollDropdownListContainer());
		dropdownListContainerScrollPanel.getElement().setId("sbDropDownListContainer");
		popupContainer.getElement().setId("addCourseToClasPopup");
		folderTreePanel.getElement().setId("addResourcefolderTreePanel");
		lblEmptyErrorMessage.setVisible(false);
		lblEmptyErrorMessage.getElement().getStyle().setPadding(0, Unit.PX);
		lblError.setVisible(false);
		urlparams= new HashMap<String, String>();
		addtocollHeaderText.setText(i18n.GL3428());
		addingTextLbl.setHTML(i18n.GL3436());
		folderTreePanel.addSelectionHandler(new SelectionHandler<TreeItem>() {
			  @Override
			  public void onSelection(SelectionEvent<TreeItem> event) {
			   isTopMostSelected = false;
			    lblError.setVisible(false);
			    lblError.setText(i18n.GL3429());
			   final TreeItem item = (TreeItem) event.getSelectedItem();
			    Widget folderWidget= item.getWidget();
			     if(folderWidget instanceof CourseTreeItem){
					removePreviousSelectedItem();
					currectCourseSelectedTreeItem = (CourseTreeItem)folderWidget;
					previousCourseSelectedTreeItem = currectCourseSelectedTreeItem;
					currectCourseSelectedTreeItem.addStyleName("selected");
					TreeItem parent = item.getParentItem();
					item.getTree().setSelectedItem(parent, false);
					if (parent != null)
						parent.setSelected(false);
					item.setState(!item.getState(), false);
			    }
			  }
		});
		assginCourse.setText(i18n.GL3450_11());
		bluedotLbl.setText(i18n.GL3450_12());
		bluedotLbl.getElement().getStyle().setDisplay(Display.INLINE);
		greenLbl.setText(i18n.GL3450_13());
		greenLbl.getElement().getStyle().setDisplay(Display.INLINE);
		orangeLbl.setText(i18n.GL3450_14());
		orangeLbl.getElement().getStyle().setDisplay(Display.INLINE);
		createCourseHeader.setText(i18n.GL3450_15());
		courseNotesLbl.setText(i18n.GL3450_16());
		courseNotesLbl.getElement().getStyle().setDisplay(Display.INLINE);
	}

	private void removePreviousSelectedItem() {
		if(previousCourseSelectedTreeItem != null){
			previousCourseSelectedTreeItem.removeStyleName("selected");
		}
	}

	private class ScrollDropdownListContainer implements ScrollHandler{
		@Override
		public void onScroll(ScrollEvent event) {
			if((dropdownListContainerScrollPanel.getVerticalScrollPosition() == dropdownListContainerScrollPanel.getMaximumVerticalScrollPosition() && folderTreePanel.getItemCount()>=20)){
				getUiHandlers().getWorkspaceData(pageNum*limit, limit,false,currentsearchType);
				pageNum++;
			}
		}
	}
	@Override
	public Widget asWidget() {
		return appPopUp;
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
		currectCourseSelectedTreeItem=null;
	}

	@Override
	public void onUnload() {

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
	public class CourseTreeItem extends Composite{
		private FlowPanel folderContainer=null;
		private String selectedFolderName=null;
		private String gooruOid=null,folderTitle=null;
		Label floderName=null;
		Label arrowLabel=null;
		private boolean isOpen=false;
		private boolean isApiCalled=false;
		private int folerLevel=1;
		public CourseTreeItem(){
			initWidget(folderContainer=new FlowPanel());
			folderContainer.setStyleName("foldermenuLevel");
		}
		public CourseTreeItem(String levelStyleName,String folderTitle,String gooruOid,FolderDo folderDo){
			this();
			if(folderDo.getType() != null){
				if(folderDo.getType().equalsIgnoreCase("course")){
					folderContainer.removeStyleName("foldermenuLevel");
					floderTreeContainer.getElement().setId("gShelfMainContainer");
					folderContainer.addStyleName("courseLevel");
					folderContainer.addStyleName("course");
					folderContainer.getElement().getStyle().setBackgroundColor("white");
				}
			}
			if(levelStyleName!=null){
				folderContainer.addStyleName("foldermenuLevel"+levelStyleName);
			}
			this.gooruOid=gooruOid;
			this.selectedFolderName = folderTitle;
			this.folderTitle=folderTitle;
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
	}
	@UiHandler("cancelResourcePopupBtnLbl")
	public void cancelButtonEvent(ClickEvent event){
		hide();
		Window.enableScrolling(true);
		enableTopFilters();
	}

	@UiHandler("cancel")
	public void cancelEvent(ClickEvent event){
		hide();
		Window.enableScrolling(true);
		enableTopFilters();
	}
	@UiHandler("btnAddExisting")
	public void addResourceToCollection(ClickEvent event){
		if(currectCourseSelectedTreeItem != null){
			dropdownListContainerScrollPanel.setVisible(false);
			assignCourseBlockContainer.setVisible(true);
			addingTextLbl.setHTML(i18n.GL3437());
			assignBtn.setVisible(true);
			cancel.setVisible(true);
			btnAddExisting.setVisible(false);
			btnAddNew.setVisible(false);
		}else{
			lblError.setVisible(true);
		}
	}

	@UiHandler("assignBtn")
	public void addCourseToClass(ClickEvent event){
		classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		if(classId != null && currectCourseSelectedTreeItem.getGooruOid() != null){
			getUiHandlers().connectCourseToClass(classId,currectCourseSelectedTreeItem.getGooruOid());
		}
	}

	@UiHandler("gotoMyContent")
	public void connectToMyContent(ClickEvent event){
		hide();
		Window.enableScrolling(true);
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT);
	}
	@UiHandler("btnAddNew")
	public void createNewCourse(ClickEvent event){
		AppClientFactory.getPlaceManager().redirectPlace(PlaceTokens.MYCONTENT);
		hide();
	}

	@Override
	public Anchor getAddButton(){
		return null;
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
		btnAddNew.setVisible(!blnVal);
		pageNum=1;
	}

	@Override
	public void displaySuccessPopup(String collectionName,String selectedGooruOid,HashMap<String, String> params) {
		isAddingInProgress=true;
		hide();
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.EDIT_CLASS,params);
	}

	public void enableTopFilters(){
		Element element = Document.get().getElementById("fixedFilterSearchID");
		if(element!=null){
			element.removeAttribute("style");
		}
		Window.enableScrolling(true);
	}


	@Override
	public void clearUrlParams() {
		urlparams.clear();
		lblError.setVisible(false);
		assignCourseBlockContainer.setVisible(false);
		assignBtn.setVisible(false);
		addingTextLbl.setHTML(i18n.GL3436());
		btnAddNew.setVisible(true);
		emptyCourseBlockContainer.setVisible(false);
		currectCourseSelectedTreeItem=null;
		cancel.setVisible(false);
		pageNum=1;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.gsearch.addResourcePopup.IsSearchAddResourceToCollectionView#displayCourseWorkspaceData(org.ednovo.gooru.application.shared.model.folder.FolderListDo, boolean, java.lang.String)
	 */
	@Override
	public void displayCourseWorkspaceData(FolderListDo folderListDo,boolean clearShelfPanel,String searchType) {
		if(clearShelfPanel){
			folderTreePanel.clear();
		}
		if(folderListDo!=null){
			 List<FolderDo> foldersArrayList=folderListDo.getSearchResult();
			 if(foldersArrayList!=null&&foldersArrayList.size()>0){
				 for(int i=0;i<foldersArrayList.size();i++){
					FolderDo floderDo=foldersArrayList.get(i);
					 if(!floderDo.getType().equals("collection")){
						 TreeItem folderItem=new TreeItem(new CourseTreeItem(null,floderDo.getTitle(),floderDo.getGooruOid(),floderDo));
						 folderTreePanel.addItem(folderItem);
						 adjustTreeItemStyle(folderItem);
					 }
					 dropdownListContainerScrollPanel.setVisible(true);
					 footerPanel.setVisible(true);
				 }
			 }else{
				 if(clearShelfPanel){
					 dropdownListContainerScrollPanel.setVisible(false);
					 footerPanel.setVisible(false);
					 addingTextLbl.setText(i18n.GL3439());
					 emptyCourseBlockContainer.setVisible(true);
					 assignCourseBlockContainer.setVisible(false);
				 }
				
			 }
		}
		currentsearchType=searchType;
		/*totalHitCount = folderListDo.getCount();*/
		btnAddExisting.setVisible(true);
		//dropdownListContainerScrollPanel.setVisible(true);
		lblEmptyErrorMessage.setVisible(false);
		lblError.setVisible(false);
		lblEmptyErrorMessage.getElement().getStyle().setPadding(0, Unit.PX);

	}

}
