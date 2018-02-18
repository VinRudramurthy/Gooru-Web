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
package org.ednovo.gooru.client.mvp.assessments.play.resource.add;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemsList;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.classpages.assignments.AddAssignmentContainerCBundle;
import org.ednovo.gooru.client.mvp.settings.CustomAnimation;
import org.ednovo.gooru.client.mvp.shelf.list.TreeMenuImages;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class AddResourceAssessmentsView extends BaseViewWithHandlers<AddResourceAssessmentsUiHandlers> implements IsAddResourceAssessmentsView{


	private static final String ASSESSMENT = "assessment";

	private static AddResourceCollectionViewUiBinder uiBinder = GWT.create(AddResourceCollectionViewUiBinder.class);

	interface AddResourceCollectionViewUiBinder extends UiBinder<Widget, AddResourceAssessmentsView> {

	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField HTMLPanel dropdownListContainer,createCollectionLabelContainer,resourceImageContainerInAddResource,
		addCollectionInsteadLabelContainer,addCollectionContainer,addresourceText,existingCollectionContainer,mainPanel;
	@UiField InlineLabel dropdownListPlaceHolder;
	@UiField ScrollPanel dropdownListContainerScrollPanel;
	@UiField Button addResourceToCollectionButton,workSpaceBtn;
	@UiField Label addingLabel,addToExistingColl,errorMessage,
					sizeMessage,successMessageLabelText,addCollectionInsteadLabelText,hideText,resourceAdditionErrorStyle;
	@UiField FlowPanel resourceAddedSuccessMessageContainer;
	@UiField Label addNewCollectionLabel;

	@UiField HTMLEventPanel hideButton;

	private String collectionId=null;

	private String resourceId=null;

	private String questionType=null;

	private String category = null;

	private boolean isAllUserShelfCollectionsLoaded=false;

	private int limit=20;
	private int totalHitCount=0;
	private int pageNum=1;

	private String FOLDER = "folder";
	private String SCOLLECTION = "scollection";

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

	@Inject
	public AddResourceAssessmentsView(){
		setWidget(uiBinder.createAndBindUi(this));
		AddAssignmentContainerCBundle.INSTANCE.css().ensureInjected();
		mainPanel.getElement().setId("mainPanel");
		hideText.setText(i18n.GL0592());
		hideText.getElement().setId("lblHideText");
		hideText.getElement().setAttribute("alt",i18n.GL0592());
		hideText.getElement().setAttribute("title",i18n.GL0592());

		addresourceText.getElement().setInnerHTML(i18n.GL0698());
		addresourceText.getElement().setId("pnlAddresourceText");
		addresourceText.getElement().setAttribute("alt",i18n.GL0592());
		addresourceText.getElement().setAttribute("title",i18n.GL0592());

		dropdownListPlaceHolder.getElement().setInnerHTML(i18n.GL0105());
		dropdownListPlaceHolder.getElement().setId("spnDropdownListPlaceHolder");
		dropdownListPlaceHolder.getElement().setAttribute("alt",i18n.GL0105());
		dropdownListPlaceHolder.getElement().setAttribute("title",i18n.GL0105());

		dropdownListContainerScrollPanel.getElement().getStyle().setDisplay(Display.NONE);
		dropdownListPlaceHolder.addClickHandler(new OnDropdownListPlaceHolderClick());
		dropdownListContainerScrollPanel.addScrollHandler(new ScrollDropdownListContainer());
		getAddResourceToCollectionButton().addClickHandler(new OnAddResourceButtonClick());
		resourceAddedSuccessMessageContainer.setVisible(false);
		addNewCollectionLabel.setText(i18n.GL0696());
		addNewCollectionLabel.getElement().setId("lblAddNewCollectionLabel");
		addNewCollectionLabel.getElement().setAttribute("alt",i18n.GL0696());
		addNewCollectionLabel.getElement().setAttribute("title",i18n.GL0696());

		resourceAdditionErrorStyle.setText(i18n.GL0659());
		resourceAdditionErrorStyle.getElement().setId("lblResourceAdditionErrorStyle");
		resourceAdditionErrorStyle.getElement().setAttribute("alt",i18n.GL0659());
		resourceAdditionErrorStyle.getElement().setAttribute("title",i18n.GL0659());

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
				    TreeItem parent = item.getParentItem();
				    item.getTree().setSelectedItem(parent, false); // TODO FIX ME
				    if(!folderTreeItemWidget.isApiCalled()){
				    	folderTreeItemWidget.setApiCalled(true);
				    	if(item!=null&&folderTreeItemWidget.getGooruOid()!=null){
				    	getFolderItems(item,folderTreeItemWidget.getGooruOid());
				    	}
				    }
				    if(parent != null)
				    	parent.setSelected(false);   // TODO FIX ME
				    	item.setState(!item.getState(), false);
			    }else if(folderWidget instanceof CollectionTreeItem){
			    	if(previousSelectedItem!=null){
			    		previousSelectedItem.removeStyleName(AddAssignmentContainerCBundle.INSTANCE.css().selected());
			    	}
			    	cureentcollectionTreeItem=(CollectionTreeItem)folderWidget;
			    	previousSelectedItem=cureentcollectionTreeItem;
			    	cureentcollectionTreeItem.addStyleName(AddAssignmentContainerCBundle.INSTANCE.css().selected());
			    	setSelectedCollectionTitle();
			    	closeDropDown();
			    }
			  }
			});
		dropdownListContainer.clear();
		dropdownListContainer.add(folderTreePanel);
		folderTreePanel.addItem(loadingTreeItem());
		existingCollectionContainer.setVisible(true);
		setId();
	}
	public void setSelectedCollectionTitle(){
		if(cureentcollectionTreeItem!=null){
			dropdownListPlaceHolder.setText(cureentcollectionTreeItem.getCollectionName());
		}
	}
	public void closeDropDown(){
		new CustomAnimation(dropdownListContainerScrollPanel).run(300);
	}
	public void setFolderItems(TreeItem item,FolderListDo folderListDo){
		displayWorkspaceData(item,folderListDo);
	}
	public void getFolderItems(TreeItem item,String parentId){
		if(parentId!=null){
		getUiHandlers().getFolderItems(item,parentId);
		}
	}
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
			floderName.addStyleName("arav-addResource");

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
	public class CollectionTreeItem extends Composite{
		private FlowPanel folderContainer=null;
		Label folderName=null;
		private String collectionName=null;
		private String gooruOid=null;
		private String collectionType;
		private int itemsCount=0;
		private boolean isOpen=false;
		public CollectionTreeItem(){
			initWidget(folderContainer=new FlowPanel());
			folderContainer.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().foldercollection());
			folderName=new Label();
			folderName.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().title());
			folderName.addStyleName("arav-addResource");

			folderContainer.add(folderName);
		}
		public CollectionTreeItem(String levelStyleName){
			this();
			folderContainer.addStyleName(levelStyleName);
		}
		public CollectionTreeItem(String levelStyleName,String folderTitle,String gooruOid,int itemsCount,String collectionType){
			this();
			if(ASSESSMENT.equals(collectionType)){
				folderContainer.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().folderAssessment());
			}
			if(levelStyleName!=null){
				folderContainer.addStyleName(levelStyleName);
			}
			this.gooruOid=gooruOid;
			this.collectionName=folderTitle;
			this.itemsCount=itemsCount;
			this.collectionType=collectionType;
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
		public String getCollectionType(){
			return collectionType;
		}
		public int getItemsCount() {
			return itemsCount;
		}
		public void setItemsCount(int itemsCount) {
			this.itemsCount = itemsCount;
		}
	}
	@Override
	public void displayWorkspaceData(FolderListDo folderListDo,boolean clearShelfPanel) {
		if(clearShelfPanel){
			folderTreePanel.clear();
		}
		if(folderListDo!=null){
			 List<FolderDo> foldersArrayList=folderListDo.getSearchResult();
			 setPagination(folderListDo.getCount());
			 if(foldersArrayList!=null && foldersArrayList.size()>0){
				 for(int i=0;i<foldersArrayList.size();i++){
					 FolderDo floderDo=foldersArrayList.get(i);
						 if(FOLDER.equals(floderDo.getType())){
							 if(floderDo.getTitle()!=null && floderDo.getGooruOid()!=null){
									 TreeItem folderItem=new TreeItem(new FolderTreeItem(null,floderDo.getTitle(),floderDo.getGooruOid()));
									 if(foldersArrayList.get(i).getCollectionItems()!=null && foldersArrayList.get(i).getCollectionItems().size() == 0)
									 {
										 if(folderItem.getElement().hasChildNodes())
										 {
											 if(folderItem.getElement().getFirstChildElement().hasChildNodes())
											 {
												 if(folderItem.getElement().getFirstChildElement().getFirstChildElement().hasChildNodes())
												 {
													 folderItem.getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getStyle().setDisplay(Display.NONE);
												 }

											 }

										 }
									 }
									 folderTreePanel.addItem(folderItem);
									 adjustTreeItemStyle(folderItem);
								}
							 }else if(SCOLLECTION.equals(floderDo.getType())){
								 if(floderDo.getTitle()!=null && floderDo.getGooruOid()!=null && floderDo.getItemCount()!=null){
									 String collectionType=floderDo.getCollectionType().equals(ASSESSMENT)?floderDo.getCollectionType():floderDo.getType();
									 TreeItem folderItem=new TreeItem(new CollectionTreeItem(null,floderDo.getTitle(),floderDo.getGooruOid(),floderDo.getItemCount(), collectionType));
									 folderTreePanel.addItem(folderItem);
									 adjustTreeItemStyle(folderItem);
								 }
						 }
				 }
			 }
		}
	}

	public void displayWorkspaceData(TreeItem item, FolderListDo folderListDo) {
		if(folderListDo!=null){
			 List<FolderDo> foldersArrayList=folderListDo.getSearchResult();
			 if(foldersArrayList!=null && foldersArrayList.size()>0){
				 FolderTreeItem folderTreeItemWidget=(FolderTreeItem)item.getWidget();
				 int folderLevel=folderTreeItemWidget.getFolerLevel();
				 for(int i=0;i<foldersArrayList.size();i++){
					 FolderDo floderDo=foldersArrayList.get(i);
						 if(FOLDER.equals(floderDo.getType())){
							 String styleName=folderLevel==1?AddAssignmentContainerCBundle.INSTANCE.css().parent():AddAssignmentContainerCBundle.INSTANCE.css().child();
							 if(floderDo.getTitle()!=null && floderDo.getGooruOid()!=null){
								 FolderTreeItem innerFolderTreeItem=new FolderTreeItem(styleName,floderDo.getTitle(),floderDo.getGooruOid());
								 innerFolderTreeItem.setFolerLevel(folderLevel+1);
								 TreeItem folderItem=new TreeItem(innerFolderTreeItem);
								 if(foldersArrayList.get(i).getCollectionItems()!=null && foldersArrayList.get(i).getCollectionItems().size() == 0)
								 {
									 if(folderItem.getElement().hasChildNodes())
									 {
										 if(folderItem.getElement().getFirstChildElement().hasChildNodes())
										 {
											 if(folderItem.getElement().getFirstChildElement().getFirstChildElement().hasChildNodes())
											 {
												 folderItem.getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getStyle().setDisplay(Display.NONE);
											 }

										 }

									 }
								 }
								 item.addItem(folderItem);
								 adjustTreeItemStyle(folderItem);
							 }
						 }else if(SCOLLECTION.equals(floderDo.getType())){
							 if(floderDo.getTitle()!=null&&floderDo.getGooruOid()!=null && floderDo.getItemCount()!=null){
								 String collectionType=floderDo.getCollectionType().equals(ASSESSMENT)?floderDo.getCollectionType():floderDo.getType();
								 TreeItem folderItem=new TreeItem(new CollectionTreeItem(getTreeItemStyleName(folderLevel),floderDo.getTitle(),floderDo.getGooruOid(),floderDo.getItemCount(), collectionType));
								 item.addItem(folderItem);
								 adjustTreeItemStyle(folderItem);
							 }
						 }
				 }
				item.setState(folderTreeItemWidget.isOpen());
			 }
		}
	}
	private String  getTreeItemStyleName(int folderLevel){
		if(folderLevel==1){
			return AddAssignmentContainerCBundle.INSTANCE.css().parent();
		}else if(folderLevel==2){
			return AddAssignmentContainerCBundle.INSTANCE.css().child();
		}else{
			return AddAssignmentContainerCBundle.INSTANCE.css().innerchild();
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
	private class ScrollDropdownListContainer implements ScrollHandler{
		@Override
		public void onScroll(ScrollEvent event) {
			if((dropdownListContainerScrollPanel.getVerticalScrollPosition() == dropdownListContainerScrollPanel.getMaximumVerticalScrollPosition())&&(totalHitCount>pageNum*limit)){
					getUiHandlers().getWorkspaceData(pageNum*limit, limit,false);
					pageNum++;
				}
			}
	}
	public void setPagination(int count){
		totalHitCount=count;
	}
	public Label getAddNewCollectionLabel(){
		return addNewCollectionLabel;
	}

	@UiHandler("workSpaceBtn")
	public void workSpaceBtnClickEvent(ClickEvent event){
		AppClientFactory.getPlaceManager().setRefreshPlace(true);
		if(collectionId!=null){
			AppClientFactory.getInjector().getClasspageService().getCollectionParentFolders(collectionId, new SimpleAsyncCallback<ArrayList<String>>() {
				@Override
				public void onSuccess(ArrayList<String> foldersList) {
					if(foldersList!=null){
						Map<String,String> parametesMap=new HashMap<String,String>();
						parametesMap.put("id", collectionId);
						if(foldersList.size()>0){
							for(int i=0;i<foldersList.size();i++){
								parametesMap.put("o"+(i+1), foldersList.get(i));
							}
						}
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SHELF, parametesMap);
					}
				}
			});
		}
	}

	public Label getAddingLabel() {
		return addingLabel;
	}

	public void setAddingLabel(Label addingLabel) {
		this.addingLabel = addingLabel;
	}

	public HTMLPanel getDropdownListContainer() {
		return dropdownListContainer;
	}

	public void setDropdownListContainer(HTMLPanel dropdownListContainer) {
		this.dropdownListContainer = dropdownListContainer;
	}
	public InlineLabel getDropdownListPlaceHolder() {
		return dropdownListPlaceHolder;
	}

	public void setDropdownListPlaceHolder(InlineLabel dropdownListPlaceHolder) {
		this.dropdownListPlaceHolder = dropdownListPlaceHolder;
	}

	public Button getAddResourceToCollectionButton() {
		return addResourceToCollectionButton;
	}

	public void setAddResourceToCollectionButton(
			Button addResourceToCollectionButton) {
		this.addResourceToCollectionButton = addResourceToCollectionButton;
	}
	public HTMLPanel getCreateCollectionLabelContainer() {
		return createCollectionLabelContainer;
	}

	public void setCreateCollectionLabelContainer(
			HTMLPanel createCollectionLabelContainer) {
		this.createCollectionLabelContainer = createCollectionLabelContainer;
	}

	public ScrollPanel getDropdownListContainerScrollPanel() {
		return dropdownListContainerScrollPanel;
	}


	public void setDropdownListContainerScrollPanel(
			ScrollPanel dropdownListContainerScrollPanel) {
		this.dropdownListContainerScrollPanel = dropdownListContainerScrollPanel;
	}


	public void addDropDownListItem(String collectionName,String collectionId,int collectionItemsSize){
		Label dropDownListItem=new Label(collectionName);
		dropDownListItem.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().dropdownListItemContainer());
		dropdownListContainer.add(dropDownListItem);
		dropDownListItem.addClickHandler(new OnDropdownItemClick(collectionName,collectionId,collectionItemsSize));
	}

	public Label getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(Label errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Label getSizeMessage() {
		return sizeMessage;
	}


	public void getUserShelfCollections(int dropdownListContainertWidgetCount){
		if(!isAllUserShelfCollectionsLoaded){

		}
	}


	/**
	 * This method is to get the addToExistingColl
	 */
	public Label getAddCollectionViewButton() {
		return addToExistingColl;
	}

	/**
	 * This method is to set the addToExistingColl
	 */
	public void setAddToExistingColl(Label addToExistingColl) {
		this.addToExistingColl = addToExistingColl;
	}

	private class OnDropdownListPlaceHolderClick implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			new CustomAnimation(dropdownListContainerScrollPanel).run(300);
		}
	}
	private class OnDropdownItemClick implements ClickHandler{
		private String collectionName="";
		private String collectionId="";
		private int collectionItemsSize;
		public OnDropdownItemClick(String collectionName,String collectionId,int collectionItemsSize){
			this.collectionName=collectionName;
			this.collectionId=collectionId;
			this.collectionItemsSize=collectionItemsSize;
		}
		@Override
		public void onClick(ClickEvent event) {
			dropdownListPlaceHolder.setText(collectionName);
			dropdownListPlaceHolder.getElement().setAttribute("id", collectionId);
			dropdownListPlaceHolder.getElement().setAttribute("itemsSize", ""+collectionItemsSize);
			new CustomAnimation(dropdownListContainerScrollPanel).run(300);
		}
	}


	public HTMLPanel getResourceImageContainerInAddResource() {
		return resourceImageContainerInAddResource;
	}

	public void setResourceImageContainerInAddResource(
			HTMLPanel resourceImageContainerInAddResource) {
		this.resourceImageContainerInAddResource = resourceImageContainerInAddResource;
	}

	public HTMLPanel getAddCollectionInsteadLabelContainer() {
		return addCollectionInsteadLabelContainer;
	}

	public void setAddCollectionInsteadLabelContainer(
			HTMLPanel addCollectionInsteadLabelContainer) {
		this.addCollectionInsteadLabelContainer = addCollectionInsteadLabelContainer;
	}

	public Label getSuccessMessageLabelText() {
		return successMessageLabelText;
	}

	public void setSuccessMessageLabelText(Label successMessageLabelText) {
		this.successMessageLabelText = successMessageLabelText;
	}

	public FlowPanel getResourceAddedSuccessMessageContainer() {
		return resourceAddedSuccessMessageContainer;
	}

	public void setResourceAddedSuccessMessageContainer(
			FlowPanel resourceAddedSuccessMessageContainer) {
		this.resourceAddedSuccessMessageContainer = resourceAddedSuccessMessageContainer;
	}

	public Label getAddCollectionInsteadLabelText() {
		return addCollectionInsteadLabelText;
	}

	public void setAddCollectionInsteadLabelText(Label addCollectionInsteadLabelText) {
		this.addCollectionInsteadLabelText = addCollectionInsteadLabelText;
	}
	public void updateWorkSpaceLink(String collectionId){
		showSuccessWidget(true);
		addingLabel.setVisible(false);
		addResourceToCollectionButton.setVisible(true);
		if(getAddResourceToCollectionButton().getText().equalsIgnoreCase("Add Again")){
			successMessageLabelText.setText(i18n.GL0699());
			successMessageLabelText.getElement().setAttribute("alt",i18n.GL0699());
			successMessageLabelText.getElement().setAttribute("title",i18n.GL0699());
		}else{
			successMessageLabelText.setText(i18n.GL0663());
			successMessageLabelText.getElement().setAttribute("alt",i18n.GL0663());
			successMessageLabelText.getElement().setAttribute("title",i18n.GL0663());
			addResourceToCollectionButton.setText(i18n.GL0691());
			addResourceToCollectionButton.getElement().setAttribute("alt",i18n.GL0691());
			addResourceToCollectionButton.getElement().setAttribute("title",i18n.GL0691());
		}
		workSpaceBtn.setText(i18n.GL1630());
		workSpaceBtn.getElement().setAttribute("alt",i18n.GL1630());
		workSpaceBtn.getElement().setAttribute("title",i18n.GL1630());

		this.collectionId=collectionId;
		addCollectionInsteadLabelContainer.clear();
		Label colletionIsteadButton=getAddCollectionViewButton();
		colletionIsteadButton.setText(i18n.GL0664());
		colletionIsteadButton.getElement().setAttribute("alt",i18n.GL0664());
		colletionIsteadButton.getElement().setAttribute("title",i18n.GL0664());
		colletionIsteadButton.getElement().getStyle().setMarginRight(138, Unit.PX);

		addCollectionInsteadLabelContainer.add(colletionIsteadButton);

	}
	public void showSuccessWidget(boolean showWidget){
		resourceAddedSuccessMessageContainer.setVisible(showWidget);
		resourceImageContainerInAddResource.setVisible(!showWidget);
		existingCollectionContainer.setVisible(!showWidget);
	}
	public void changeButtonText(){
		showSuccessWidget(false);
		addingLabel.setVisible(false);
		errorMessage.setVisible(false);
		addResourceToCollectionButton.setVisible(true);
		addResourceToCollectionButton.setText(i18n.GL0590());
		addResourceToCollectionButton.getElement().setAttribute("alt",i18n.GL0590());
		addResourceToCollectionButton.getElement().setAttribute("title",i18n.GL0590());
		addCollectionContainer.clear();
		Label colletionIsteadButton=getAddCollectionViewButton();
		colletionIsteadButton.getElement().getStyle().clearMarginRight();
		colletionIsteadButton.getElement().getStyle().clearMarginTop();
		colletionIsteadButton.setText(i18n.GL0664());
		colletionIsteadButton.getElement().setAttribute("alt",i18n.GL0664());
		colletionIsteadButton.getElement().setAttribute("title",i18n.GL0664());
		addCollectionContainer.add(colletionIsteadButton);
	}

	@Override
	public void setCollectionItemData(String collectionId,CollectionItemDo collectionItemDo) {
		if(collectionItemDo.getResource()!=null && collectionItemDo.getResource().getGooruOid()!=null){
		this.resourceId=collectionItemDo.getResource().getGooruOid();
		this.category = collectionItemDo.getResource().getCategory();
		this.questionType=collectionItemDo.getQuestionType();
		}
		changeButtonText();
	}
	public void resetSelectionData(){
		limit=20;
		totalHitCount=0;
		pageNum=1;
		folderTreePanel.clear();
		folderTreePanel.addItem(loadingTreeItem());
		cureentcollectionTreeItem=null;
		previousSelectedItem=null;
		errorMessage.setText("");
		errorMessage.getElement().setAttribute("alt","");
		errorMessage.getElement().setAttribute("title","");
		dropdownListContainerScrollPanel.getElement().getStyle().setDisplay(Display.NONE);
		getDropdownListPlaceHolder().setText(i18n.GL0105());
	}
	private class OnAddResourceButtonClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if(dropdownListPlaceHolder.getText().equalsIgnoreCase(i18n.GL0105())){
				errorMessage.setVisible(true);
				errorMessage.setText(i18n.GL0699_1());
				errorMessage.getElement().setAttribute("alt",i18n.GL0699_1());
				errorMessage.getElement().setAttribute("title",i18n.GL0699_1());
			}else if(cureentcollectionTreeItem!=null){
				errorMessage.setVisible(false);
				if(cureentcollectionTreeItem.getItemsCount()>=25){
					errorMessage.setVisible(true);
					errorMessage.setText(i18n.GL0699_2());
					errorMessage.getElement().setAttribute("alt",i18n.GL0699_2());
					errorMessage.getElement().setAttribute("title",i18n.GL0699_2());
				}else{
					boolean flag = getUiHandlers().validateIsAssessments(cureentcollectionTreeItem.getCollectionType(),category,questionType);
					if(flag){
						errorMessage.setVisible(false);
						getAddResourceToCollectionButton().getElement().getStyle().setDisplay(Display.NONE);
						errorMessage.setText("");
						errorMessage.getElement().setAttribute("alt","");
						errorMessage.getElement().setAttribute("title","");
						addingLabel.setText(i18n.GL0591());
						addingLabel.getElement().setAttribute("alt",i18n.GL0591());
						addingLabel.getElement().setAttribute("title",i18n.GL0591());
						getAddingLabel().getElement().getStyle().setDisplay(Display.BLOCK);
						if(resourceId!=null&&cureentcollectionTreeItem.getGooruOid()!=null){
						copyCollectionItem(resourceId, cureentcollectionTreeItem.getGooruOid());
						}
					}else{
						errorMessage.setVisible(true);
						errorMessage.setText("Oops! can copy only questions for Assessments.");
					}
				}
			}
		}
	}

	public void copyCollectionItem(String collectionItemId,String collectionId){
		getUiHandlers().copyCollectionItem(collectionItemId, collectionId);
	}

	public void addCollectionItems(ArrayList<CollectionItemsList> userCollectionsList,boolean isClearPanel) {
			if (isClearPanel) {
				getDropdownListContainer().clear();
			}
			if (userCollectionsList!=null && userCollectionsList.size() > 0) {
				for (CollectionItemsList userCollection : userCollectionsList) {
					addDropDownListItem(userCollection.getCollectionTitle(),userCollection.getCollectionId(),userCollection.getCollectionItemsListSize());
				}
			}
	}

	/**
	 *
	 * @function onhideBtnClicked
	 *
	 * @created_date : 11-Dec-2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param clickEvent
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("hideButton")
	public void onhideBtnClicked(ClickEvent clickEvent)
	{
		PlaceRequest collectionRequest = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
		String collectionId = collectionRequest.getParameter("id", null);
		String collectionItemId = collectionRequest.getParameter("rid", null);
		String chkViewParam = collectionRequest.getParameter("view", null);

		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionId);
		params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);

	if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.RESOURCE_PLAY))
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.RESOURCE_PLAY).
				with("id", collectionId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.ASSESSMENT_PLAY) && chkViewParam == null && collectionItemId != null)
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.ASSESSMENT_PLAY).
				with("id", collectionId).with("rid", collectionItemId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId != null)
	{
		params.put("rid", collectionItemId);
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.ASSESSMENT_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.ASSESSMENT_PLAY).
				with("id", collectionId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.ASSESSMENT_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.ASSESSMENT_PLAY).
				with("id", collectionId).with("view", "end");
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		params.put("view", "end");
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	}


	public TreeItem loadingTreeItem(){
		Label loadingText=new Label(i18n.GL1452());
		loadingText.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().loadingText());
		return new TreeItem(loadingText);
	}
	public void setId(){
		resourceImageContainerInAddResource.getElement().setId("pnlResourceImageContainerInAddResource");
		addCollectionContainer.getElement().setId("pnlAddCollectionContainer");
		addToExistingColl.getElement().setId("lblAddToExistingColl");
		resourceAddedSuccessMessageContainer.getElement().setId("fpnlResourceAddedSuccessMessageContainer");
		successMessageLabelText.getElement().setId("lblSuccessMessageLabelText");
		workSpaceBtn.getElement().setId("btnWorkSpaceBtn");
		addCollectionInsteadLabelContainer.getElement().setId("pnlAddCollectionInsteadLabelContainer");
		addCollectionInsteadLabelText.getElement().setId("lblAddCollectionInsteadLabelText");
		existingCollectionContainer.getElement().setId("pnlExistingCollectionContainer");
		dropdownListContainerScrollPanel.getElement().setId("sbDropdownListContainerScrollPanel");
		dropdownListContainer.getElement().setId("pnlDropdownListContainer");
		addResourceToCollectionButton.getElement().setId("btnAddResourceToCollectionButton");
		addingLabel.getElement().setId("lblAddingLabel");
		resourceAdditionErrorStyle.getElement().setId("lblResourceAdditionErrorStyle");
		createCollectionLabelContainer.getElement().setId("pnlCreateCollectionLabelContainer");
		errorMessage.getElement().setId("lblErrorMessage");
		sizeMessage.getElement().setId("lblSizeMessage");
		hideButton.getElement().setId("epnlHideButton");
	}
}
