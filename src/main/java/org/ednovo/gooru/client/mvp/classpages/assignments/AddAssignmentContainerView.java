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
package org.ednovo.gooru.client.mvp.classpages.assignments;

import java.util.Date;
import java.util.List;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.settings.CustomAnimation;
import org.ednovo.gooru.client.mvp.shelf.list.TreeMenuImages;
import org.ednovo.gooru.client.uc.DateBoxUcCustomizedForAssign;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
/**
 *
 * @fileName : AddAssignmentContainerView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 07-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class AddAssignmentContainerView extends PopupViewWithUiHandlers<AddAssignmentContainerUiHandlers> implements IsAddAssignmentContainerView {

	private static AddAssignmentContainerViewUiBinder uiBinder = GWT.create(AddAssignmentContainerViewUiBinder.class);

	public MessageProperties i18n = GWT.create(MessageProperties.class);

	interface AddAssignmentContainerViewUiBinder extends UiBinder<Widget, AddAssignmentContainerView> {
	}

	@UiField HTMLPanel floderTreeContainer,buttonsContainer;
	@UiField Button addResourceBtnLbl,cancelResourcePopupBtnLbl;
	@UiField ScrollPanel dropdownListContainerScrollPanel;
	@UiField Label addCollectionPopupHeader, addingText,emptyMsgLbl,subHeadingMsgLbl,displayCountLabel;

	@UiField FlowPanel popupContent;

	private int offset=0;
	private int limit=20;
	private int totalHitCount=0;
	private int pageNum=1;

	private static final String ASSESSMENT = "assessment";

	protected PopupPanel appPopUp;
	private DateBoxUcCustomizedForAssign dateBoxUc;
	/**
	 *
	 */
	private Tree folderTreePanel = new Tree(new TreeMenuImages()){
		 @Override
		 public void onBrowserEvent(Event event) {
			    int eventType = DOM.eventGetType(event);
			    if(!(eventType==Event.ONKEYUP||eventType==Event.ONKEYPRESS||eventType==Event.ONKEYDOWN)) {
			    	super.onBrowserEvent(event);
			    }
		 }
	};
	private CollectionTreeItem cureentcollectionTreeItem=null;
	private CollectionTreeItem previousSelectedItem=null;
	private FolderTreeItem currentFolderSelectedTreeItem=null;
	private FolderTreeItem previousFolderSelectedTreeItem=null;



	@Inject
	public AddAssignmentContainerView(EventBus eventBus) {
		super(eventBus);
		appPopUp=new PopupPanel();
		appPopUp.setWidget(uiBinder.createAndBindUi(this));
		AddAssignmentContainerCBundle.INSTANCE.css().ensureInjected();
		appPopUp.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().popupContainer());
		setStaticTexts();
		enableAssignButton(false);
		addingText.setVisible(false);
		appPopUp.setGlassEnabled(true);
		appPopUp.setAutoHideOnHistoryEventsEnabled(true);

		dateBoxUc = new DateBoxUcCustomizedForAssign(false, false,false);
		dropdownListContainerScrollPanel.addScrollHandler(new ScrollDropdownListContainer());
		folderTreePanel.addSelectionHandler(new SelectionHandler<TreeItem>() {
			  @Override
			  public void onSelection(SelectionEvent<TreeItem> event) {
			   final TreeItem item = (TreeItem) event.getSelectedItem();
			    Widget folderWidget= item.getWidget();
			    FolderTreeItem folderTreeItemWidget=null;
			    if(folderWidget instanceof FolderTreeItem){
			    	folderTreeItemWidget=(FolderTreeItem)folderWidget;
			    	if(folderTreeItemWidget.isOpen()){
			    		folderTreeItemWidget.removeStyleName(AddAssignmentContainerCBundle.INSTANCE.css().open());
			    		folderTreeItemWidget.setOpen(false);
			    	}else{
			    		folderTreeItemWidget.addStyleName(AddAssignmentContainerCBundle.INSTANCE.css().open());
			    		folderTreeItemWidget.setOpen(true);
			    	}
			    	removePreviousSelectedItem();
			    	currentFolderSelectedTreeItem=folderTreeItemWidget;
			    	previousFolderSelectedTreeItem=currentFolderSelectedTreeItem;
			    	currentFolderSelectedTreeItem.addStyleName(AddAssignmentContainerCBundle.INSTANCE.css().selected());
			    	previousSelectedItem=cureentcollectionTreeItem=null;
				    TreeItem parent = item.getParentItem();
				    item.getTree().setSelectedItem(parent, false); // TODO FIX ME
				    if(!folderTreeItemWidget.isApiCalled()){
				    	folderTreeItemWidget.setApiCalled(true);
				    	getFolderItems(item,folderTreeItemWidget.getGooruOid());
				    }
				    if(parent != null)
				    	parent.setSelected(false);   // TODO FIX ME
				    item.setState(!item.getState(), false);
				    setSelectedCollectionsCount(getCollectionCountFromFolder(item));
			    }else if(folderWidget instanceof CollectionTreeItem){
			    	removePreviousSelectedItem();
			    	cureentcollectionTreeItem=(CollectionTreeItem)folderWidget;
			    	previousSelectedItem=cureentcollectionTreeItem;
			    	cureentcollectionTreeItem.addStyleName(AddAssignmentContainerCBundle.INSTANCE.css().selected());
			    	previousFolderSelectedTreeItem=currentFolderSelectedTreeItem=null;
			    	setSelectedCollectionTitle();
			    }
			  }
			});
		floderTreeContainer.clear();
		floderTreeContainer.add(folderTreePanel);
		folderTreePanel.addItem(loadingTreeItem());
	}
	/**
	 *
	 * @function getCollectionCountFromFolder
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param item
	 * @parm(s) : @return
	 *
	 * @return : int
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public int getCollectionCountFromFolder(TreeItem item){
	    int childCount = item.getChildCount();
	    int collectionCount=0;
	    for(int i=0;i<childCount;i++){
	    	TreeItem childTree=item.getChild(i);
	    	Widget childWidget=childTree.getWidget();
	    	 if(childWidget instanceof CollectionTreeItem){
	    		 collectionCount++;
	    	 }
	    }
	    return collectionCount;
	}
	/**
	 *
	 * @function setStaticTexts
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setStaticTexts(){
		subHeadingMsgLbl.setText(i18n.GL1974());
		subHeadingMsgLbl.getElement().setId("lblSubHeadingMsg");
		subHeadingMsgLbl.getElement().setAttribute("alt",i18n.GL1974());
		subHeadingMsgLbl.getElement().setAttribute("title",i18n.GL1974());

		addCollectionPopupHeader.setText(i18n.GL1973());
		addCollectionPopupHeader.getElement().setId("lblAddCollectionPopupHeader");
		addCollectionPopupHeader.getElement().setAttribute("alt",i18n.GL1973());
		addCollectionPopupHeader.getElement().setAttribute("title",i18n.GL1973());

		cancelResourcePopupBtnLbl.setText(i18n.GL0142());
		cancelResourcePopupBtnLbl.getElement().setId("btnCancelResourcePoup");
		cancelResourcePopupBtnLbl.getElement().setAttribute("alt",i18n.GL0142());
		cancelResourcePopupBtnLbl.getElement().setAttribute("title",i18n.GL0142());

		addResourceBtnLbl.setText(i18n.GL0104());
		addResourceBtnLbl.getElement().setId("btnAddResource");
		addResourceBtnLbl.getElement().setAttribute("alt",i18n.GL0104());
		addResourceBtnLbl.getElement().setAttribute("title",i18n.GL0104());

		addingText.setText(i18n.GL1172());
		addingText.getElement().setId("lblAddText");
		addingText.getElement().setAttribute("alt",i18n.GL1172());
		addingText.getElement().setAttribute("title",i18n.GL1172());

		popupContent.getElement().setId("pnlPopupContent");
		emptyMsgLbl.getElement().setId("lblEmptyMsg");
		dropdownListContainerScrollPanel.getElement().setId("sbDropDownListContainer");
		floderTreeContainer.getElement().setId("pnlFolderTreeContainer");
		displayCountLabel.getElement().setId("lblDisplayCount");
		buttonsContainer.getElement().setId("pnlButtonsContainer");
	}
	/**
	 *
	 * @function setSelectedCollectionTitle
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setSelectedCollectionTitle(){
		if(cureentcollectionTreeItem!=null){
			enableAssignButton(true);
			displayCountLabel.setText("\""+cureentcollectionTreeItem.getCollectionName()+"\" "+i18n.GL1975());
			displayCountLabel.getElement().setAttribute("alt","\""+cureentcollectionTreeItem.getCollectionName()+"\" "+i18n.GL1975());
			displayCountLabel.getElement().setAttribute("title","\""+cureentcollectionTreeItem.getCollectionName()+"\" "+i18n.GL1975());
		}
	}
	/**
	 *
	 * @function setSelectedCollectionsCount
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param count
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setSelectedCollectionsCount(int count){
		if(count>0){
			enableAssignButton(true);
			String label=count==1?count+" collection":count+" collections";
			displayCountLabel.setText(label+" "+i18n.GL1975());
			displayCountLabel.getElement().setAttribute("alt",label+" "+i18n.GL1975());
			displayCountLabel.getElement().setAttribute("title",label+" "+i18n.GL1975());

		}else{
			enableAssignButton(false);
			displayCountLabel.setText("");
			displayCountLabel.getElement().setAttribute("alt","");
			displayCountLabel.getElement().setAttribute("title","");
		}
	}
	/**
	 *
	 * @function enableAssignButton
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param enable
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void enableAssignButton(boolean enable){
		if(enable){
			addResourceBtnLbl.setEnabled(enable);
			addResourceBtnLbl.removeStyleName("secondary");
			addResourceBtnLbl.addStyleName("primary");
		}else{
			addResourceBtnLbl.setEnabled(enable);
			addResourceBtnLbl.removeStyleName("primary");
			addResourceBtnLbl.addStyleName("secondary");
		}
	}
	/**
	 *
	 * @function removePreviousSelectedItem
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected void removePreviousSelectedItem(){
		if(previousFolderSelectedTreeItem!=null){
    		previousFolderSelectedTreeItem.removeStyleName(AddAssignmentContainerCBundle.INSTANCE.css().selected());
    	}
		if(previousSelectedItem!=null){
    		previousSelectedItem.removeStyleName(AddAssignmentContainerCBundle.INSTANCE.css().selected());
    	}
	}
	/**
	 *
	 * @function closeDropDown
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void closeDropDown(){
		new CustomAnimation(dropdownListContainerScrollPanel).run(300);
	}
	/**
	 *
	 */
	public void setFolderItems(TreeItem item,FolderListDo folderListDo){
		displayWorkspaceData(item,folderListDo);
	}
	/**
	 *
	 * @function getFolderItems
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param item
	 * @parm(s) : @param parentId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void getFolderItems(TreeItem item,String parentId){
		getUiHandlers().getFolderItems(item,parentId);
	}
	@UiHandler("addResourceBtnLbl")
	public void addButtonEvent(ClickEvent event){
		if(addResourceBtnLbl.getText().trim().equalsIgnoreCase("Create a Collection")){
			hide();
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.COLLECTION);
		}else{
			addResourceBtnLbl.setEnabled(false);
			addCollectionToAssign();
		}

	}
	@UiHandler("cancelResourcePopupBtnLbl")
	public void cancelButtonEvent(ClickEvent event){
		hide();
	}

	/**
	 *
	 * @function addCollectionToAssign
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void addCollectionToAssign(){
		if(cureentcollectionTreeItem!=null){
			addResourceBtnLbl.setVisible(false);
			cancelResourcePopupBtnLbl.setVisible(false);
			addingText.setVisible(true);
			getUiHandlers().addCollectionToAssign(cureentcollectionTreeItem.getGooruOid());
			addResourceBtnLbl.setEnabled(true);
		}else if(currentFolderSelectedTreeItem!=null){
			addResourceBtnLbl.setVisible(false);
			cancelResourcePopupBtnLbl.setVisible(false);
			addingText.setVisible(true);
			getUiHandlers().addCollectionToAssign(currentFolderSelectedTreeItem.getGooruOid());
			addResourceBtnLbl.setEnabled(true);
		}
	}

	/**
	 *
	 * @fileName : AddAssignmentContainerView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 07-Dec-2014
	 *
	 * @Author tumbalam
	 *
	 * @Reviewer:
	 */
	public class FolderTreeItem extends Composite{
		private FlowPanel folderContainer=null;
		private String gooruOid=null;
		Label floderName=null;
		Label arrowLabel=null;
		private boolean isOpen=false;
		private boolean isApiCalled=false;
		private int folerLevel=1;
		public FolderTreeItem(){
			initWidget(folderContainer=new FlowPanel());
			folderContainer.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().folderLevel());
			Label arrowLabel=new Label();
			arrowLabel.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().arrow());
			folderContainer.add(arrowLabel);
			floderName=new Label();
			floderName.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().title());
			folderContainer.add(floderName);
		}
		public FolderTreeItem(String levelStyleName,String folderTitle,String gooruOid){
			this();
			if(levelStyleName!=null){
				folderContainer.addStyleName(levelStyleName);
			}
			this.gooruOid=gooruOid;
			floderName.setText(folderTitle);
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
		public boolean isApiCalled() {
			return isApiCalled;
		}
		public void setApiCalled(boolean isApiCalled) {
			this.isApiCalled = isApiCalled;
		}
		public int getFolerLevel() {
			return folerLevel;
		}
		public void setFolerLevel(int folerLevel) {
			this.folerLevel = folerLevel;
		}
	}

	/**
	 *
	 * @fileName : AddAssignmentContainerView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 07-Dec-2014
	 *
	 * @Author tumbalam
	 *
	 * @Reviewer:
	 */
	public class CollectionTreeItem extends Composite{
		private FlowPanel folderContainer=null;
		Label folderName=null;
		private String collectionName=null;
		private String gooruOid=null;
		private boolean isOpen=false;
		public CollectionTreeItem(){
			initWidget(folderContainer=new FlowPanel());
			folderContainer.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().foldercollection());
			folderName=new Label();
			folderName.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().title());
			folderContainer.add(folderName);
		}
		public CollectionTreeItem(String levelStyleName){
			this();
			folderContainer.addStyleName(levelStyleName);
		}
		public CollectionTreeItem(String levelStyleName,String folderTitle,String gooruOid, String collectionType){
			this();
			if(ASSESSMENT.equals(collectionType)){
				folderContainer.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().folderAssessment());
			}
			if(levelStyleName!=null){
				folderContainer.addStyleName(levelStyleName);
			}
			this.gooruOid=gooruOid;
			this.collectionName=folderTitle;
			folderName.setText(folderTitle);
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
		public String getCollectionName(){
			return collectionName;
		}

	}
	@Override
	public void displayWorkspaceData(FolderListDo folderListDo,boolean clearShelfPanel) {
		resetEmptyCollMsg();
		if(!dropdownListContainerScrollPanel.isVisible()){
			dropdownListContainerScrollPanel.setVisible(true);
		}

		if(clearShelfPanel){
			folderTreePanel.clear();
		}
		if(folderListDo!=null){
			 List<FolderDo> foldersArrayList=folderListDo.getSearchResult();
			 setPagination(folderListDo.getCount());
			 if(foldersArrayList!=null&&foldersArrayList.size()>0){
				 for(int i=0;i<foldersArrayList.size();i++){
					 FolderDo floderDo=foldersArrayList.get(i);
					 if(floderDo.getType().equals("folder")){
						 TreeItem folderItem=new TreeItem(new FolderTreeItem(null,floderDo.getTitle(),floderDo.getGooruOid()));
						 folderTreePanel.addItem(folderItem);
						 adjustTreeItemStyle(folderItem);
					 }else {
						 String collectionType=floderDo.getCollectionType().equals(ASSESSMENT)?floderDo.getCollectionType():floderDo.getType();
						 TreeItem folderItem=new TreeItem(new CollectionTreeItem(null,floderDo.getTitle(),floderDo.getGooruOid(),collectionType));
						 folderTreePanel.addItem(folderItem);
						 adjustTreeItemStyle(folderItem);
					 }
				 }
			 }
		}
	}
	/**
	 *
	 * @function displayWorkspaceData
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param item
	 * @parm(s) : @param folderListDo
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void displayWorkspaceData(TreeItem item, FolderListDo folderListDo) {
		if(folderListDo!=null){
			 List<FolderDo> foldersArrayList=folderListDo.getSearchResult();
			 int collectionCount=0;
			 if(foldersArrayList!=null&&foldersArrayList.size()>0){
				 FolderTreeItem folderTreeItemWidget=(FolderTreeItem)item.getWidget();
				 int folderLevel=folderTreeItemWidget.getFolerLevel();
				 for(int i=0;i<foldersArrayList.size();i++){
					 FolderDo floderDo=foldersArrayList.get(i);
					 if(floderDo.getType().equals("folder")){
						 String styleName=folderLevel==1?AddAssignmentContainerCBundle.INSTANCE.css().parent():AddAssignmentContainerCBundle.INSTANCE.css().child();
						 FolderTreeItem innerFolderTreeItem=new FolderTreeItem(styleName,floderDo.getTitle(),floderDo.getGooruOid());
						 innerFolderTreeItem.setFolerLevel(folderLevel+1);
						 TreeItem folderItem=new TreeItem(innerFolderTreeItem);
						 item.addItem(folderItem);
						 adjustTreeItemStyle(folderItem);
					 }else{
						 collectionCount++;
						 String collectionType=floderDo.getCollectionType().equals(ASSESSMENT)?floderDo.getCollectionType():floderDo.getType();
						 TreeItem folderItem=new TreeItem(new CollectionTreeItem(getTreeItemStyleName(folderLevel),floderDo.getTitle(),floderDo.getGooruOid(),collectionType));
						 item.addItem(folderItem);
						 adjustTreeItemStyle(folderItem);
					 }
				 }
					item.setState(folderTreeItemWidget.isOpen());
			 }
			 setSelectedCollectionsCount(collectionCount);
		}
	}
	/**
	 *
	 * @function getTreeItemStyleName
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param folderLevel
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private String  getTreeItemStyleName(int folderLevel){
		if(folderLevel==1){
			return AddAssignmentContainerCBundle.INSTANCE.css().parent();
		}else if(folderLevel==2){
			return AddAssignmentContainerCBundle.INSTANCE.css().child();
		}else{
			return AddAssignmentContainerCBundle.INSTANCE.css().innerchild();
		}
	}
	/**
	 *
	 * @function adjustTreeItemStyle
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param uiObject
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
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
   	/**
   	 *
   	 * @function setPagination
   	 *
   	 * @created_date : 07-Dec-2014
   	 *
   	 * @description
   	 *
   	 *
   	 * @parm(s) : @param count
   	 *
   	 * @return : void
   	 *
   	 * @throws : <Mentioned if any exceptions>
   	 *
   	 *
   	 *
   	 *
   	 */
	public void setPagination(int count){
		totalHitCount=count;
	}

	/**
	 *
	 * @fileName : AddAssignmentContainerView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 07-Dec-2014
	 *
	 * @Author Gooru Team
	 *
	 * @Reviewer:
	 */
	private class ScrollDropdownListContainer implements ScrollHandler{
		@Override
		public void onScroll(ScrollEvent event) {
			if((dropdownListContainerScrollPanel.getVerticalScrollPosition() == dropdownListContainerScrollPanel.getMaximumVerticalScrollPosition())&&(totalHitCount>pageNum*limit)){
					getUiHandlers().getWorkspaceData(pageNum*limit, limit,false);
					pageNum++;
				}
			}
	}
	/**
	 *
	 * @fileName : AddAssignmentContainerView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 07-Dec-2014
	 *
	 * @Author Gooru Team
	 *
	 * @Reviewer:
	 */
	private class OnDoneClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (dateBoxUc.dateValidation()){
				if (!(dateBoxUc.getValue() == null || dateBoxUc.getDateBox()
						.getText().isEmpty())
						&& dateBoxUc.hasValidateDate()) {
				Date date = dateBoxUc.getValue();
				} else {
					dateBoxUc.getDatePickerUc().hide();
				}
			}
		}
	}

	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	@Override
	public void reset() {
	}

	@Override
	public void onLoad() {
	}

	@Override
	public void onUnload() {
		appPopUp.hide();
	}

	/**
	 *
	 */
	public void hide(){
		resetEmptyCollMsg();
		dropdownListContainerScrollPanel.setVisible(true);
		addResourceBtnLbl.setVisible(true);
		cancelResourcePopupBtnLbl.setVisible(true);
		displayCountLabel.setText("");
		dateBoxUc.getDateBox().setValue("");
		enableAssignButton(false);
		Window.enableScrolling(true);
		super.hide();
	}
	@Override
	public void clearShelfData() {
		addingText.setVisible(false);
		folderTreePanel.clear();
		folderTreePanel.addItem(loadingTreeItem());
		cureentcollectionTreeItem=null;
		previousSelectedItem=null;
		offset=0;
		limit=20;
		totalHitCount=0;
		pageNum=1;
	}
	@Override
	public void hideAddCollectionPopup(String collectionTitle) {
		hide();
		clearShelfData();
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		Window.enableScrolling(false);
		new SuccessMessagePopupView(collectionTitle);
	}

	public TreeItem loadingTreeItem(){
		Label loadingText=new Label(i18n.GL1452());
		loadingText.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().loadingText());
		return new TreeItem(loadingText);
	}

	@Override
	public void displayNoCollectionsMsg() {
		dropdownListContainerScrollPanel.setVisible(false);
		enableAssignButton(true);
		addResourceBtnLbl.setText(i18n.GL1964());
		emptyMsgLbl.setText(i18n.GL1963());
		emptyMsgLbl.getElement().setAttribute("alt",i18n.GL1963());
		emptyMsgLbl.getElement().setAttribute("title",i18n.GL1963());
		subHeadingMsgLbl.setVisible(false);
		emptyMsgLbl.setVisible(true);
		appPopUp.removeStyleName(AddAssignmentContainerCBundle.INSTANCE.css().popupContainer());
		appPopUp.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().noCollectionMsgOuterContainer());
		popupContent.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().noCollectionMsgContainer());
	}
	/**
	 *
	 * @function resetEmptyCollMsg
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void resetEmptyCollMsg(){
		dropdownListContainerScrollPanel.setVisible(true);
		addResourceBtnLbl.setText(i18n.GL0104());
		emptyMsgLbl.setVisible(false);
		subHeadingMsgLbl.setVisible(true);
		buttonsContainer.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().assignmentButtonsContainer());
		appPopUp.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().popupContainer());
		appPopUp.removeStyleName(AddAssignmentContainerCBundle.INSTANCE.css().noCollectionMsgOuterContainer());
		popupContent.removeStyleName(AddAssignmentContainerCBundle.INSTANCE.css().noCollectionMsgContainer());
	}

}
