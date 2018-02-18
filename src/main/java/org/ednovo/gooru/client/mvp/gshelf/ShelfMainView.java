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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClassPageCollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.folders.FoldersWelcomePage;
import org.ednovo.gooru.client.mvp.gshelf.util.EmptyCourseBuilderWidget;
import org.ednovo.gooru.client.mvp.gshelf.util.EmptyNoCollectionsWidget;
import org.ednovo.gooru.client.mvp.shelf.collection.folders.events.RemoveMovedCollectionFolderEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.folders.events.RemoveMovedCollectionFolderHandler;
import org.ednovo.gooru.client.mvp.shelf.list.TreeMenuImages;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * @fileName : ShelfView.java
 *
 * @description :
 *
 *
 * @version : 5.5
 *
 * @date: June 17, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class ShelfMainView extends BaseViewWithHandlers<ShelfMainUiHandlers> implements IsShelfMainView {

	private static final MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField HTMLPanel floderTreeContainer,gShelfMainContainer,pnlSlot,pnlNoDataContainer,pnlMainContainer,titleIconContainer;

	@UiField HTMLEventPanel organizeRootPnl;

	@UiField HTMLEventPanel createNewCourse,createNewCollection,createNewAssessment;

	@UiField Anchor lnkMyCourses,lnkMyFoldersAndCollecctions;

	@UiField Label organizelbl,lblCollectionTitle,lblLastEditedBy;

	@UiField InlineLabel imgIconLbl;

	@UiField static ScrollPanel collectionListScrollpanel;

	private static final String O1_LEVEL = "o1";

	private static final String O2_LEVEL = "o2";

	private static final String O3_LEVEL = "o3";

	private static final String ID = "id";

	private static final String FOLDER = "Folder";

	private static final String DOWN_ARROW = "MoveDown";

	private static final String UP_ARROW = "MoveUp";

	private static final String COURSE = "Course";
	private static final String UNIT = "Unit";
	private static final String LESSON = "Lesson";
	private static final String COLLECTION = "collection";
	private static final String ASSESSMENT = "assessment";
	private static final String ASSESSMENT_URL = "assessment/url";

	private static final String UNTITLEDCOURSE = i18n.GL3347();
	private static final String UNTITLEDUNIT = i18n.GL3364();
	private static final String UNTITLEDLESSON = i18n.GL3365();

	private boolean isCreateCourse;

	private final String VIEW ="view";

	static Integer pageNumber = 1;

	private TreeItem treeChildSelectedItem = new TreeItem();

	private TreeItem previousTreeChildSelectedItem = new TreeItem();

	private Integer childPageNumber = 1;

	int collectionItemDoSize, count;

	boolean collectionItemsNotFriendly = false;

	String selectedFolderId = "";

	List<ClassPageCollectionDo> classpageTitles = null;

	private static final List<FolderDo> SHELF_COLLECTIONS = new ArrayList<>();

	private static  final String LOADER_IMAGE = "images/core/B-Dot.gif";

	List<FolderDo> folderListDoChild=new ArrayList<>();

	private static final ShelfMainViewUiBinder uiBinder = GWT
			.create(ShelfMainViewUiBinder.class);

	interface ShelfMainViewUiBinder extends UiBinder<Widget, ShelfMainView> {
	}

	private Tree shelfFolderTree = new Tree(new TreeMenuImages()) {
		@Override
		public void onBrowserEvent(Event event) {
			int eventType = DOM.eventGetType(event);
			if (!(eventType == Event.ONKEYUP || eventType == Event.ONKEYPRESS || eventType == Event.ONKEYDOWN)) {
				super.onBrowserEvent(event);
			}
		}

                @Override
		public void onLoad(){
			 super.onLoad();
			 adjustTreeItemElementsStyle(shelfFolderTree);
		}
	};

	/**
	 * class constructor
	 */
	public ShelfMainView() {
		setWidget(uiBinder.createAndBindUi(this));
		setIdForFields();
		setTreeStucture();
		setCreateCourse(true);
		organizeRootPnl.setVisible(false);
		//setDefaultOrganizePanel();
		//organizelbl.setText(i18n.GL3285());
		lnkMyCourses.addClickHandler(new DropDownClickEvent(0));
		lnkMyFoldersAndCollecctions.addClickHandler(new DropDownClickEvent(1));
		Window.addWindowScrollHandler(new com.google.gwt.user.client.Window.ScrollHandler() {
			@Override
			public void onWindowScroll(com.google.gwt.user.client.Window.ScrollEvent event) {
				//This will check the placetoken,o1 and id values for pagination purpose
				String placeToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
				String o1=AppClientFactory.getPlaceManager().getRequestParameter("o1", null);
				String id=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
				int limit = 20;
				if(getViewType().equalsIgnoreCase(COURSE)){
					limit = 50;
				}
				if(placeToken.equals(PlaceTokens.MYCONTENT) && o1==null && id==null && shelfFolderTree.getItemCount()>=limit){
					if ((event.getScrollTop() + Window.getClientHeight()) == Document.get().getBody().getClientHeight()) {
						executeScroll(false);
					}
				}
			}
		});

		AppClientFactory.getEventBus().addHandler(RemoveMovedCollectionFolderEvent.TYPE,deleteCollaborator);
	}

	/**
	 * This inner class will handle the click event on the drop down box
	 */
	class DropDownClickEvent implements ClickHandler{
		int selectedIndex;
		DropDownClickEvent(int selectedIndex){
			this.selectedIndex=selectedIndex;
		}
		@Override
		public void onClick(ClickEvent event) {
			shelfFolderTree.clear();
			shelfFolderTree.add(loadingImage());
			pnlSlot.clear();
			pnlSlot.add(loadingImage());
			if(selectedIndex==0){
				enableDisableCourseButton(true);
				organizelbl.setText(i18n.GL3335());
				getUiHandlers().setListPresenterBasedOnType(COURSE);
				lnkMyCourses.addStyleName("active");
				lnkMyFoldersAndCollecctions.removeStyleName("active");
				createNewCourse.setVisible(true);
				createNewCollection.setVisible(false);
				createNewAssessment.setVisible(false);
			}else if(selectedIndex==1){
				enableDisableCourseButton(false);
			    organizelbl.setText(i18n.GL0180());
				lnkMyFoldersAndCollecctions.addStyleName("active");
				lnkMyCourses.removeStyleName("active");
				createNewCourse.setVisible(false);
				createNewCollection.setVisible(true);
				createNewAssessment.setVisible(true);
				getUiHandlers().setListPresenterBasedOnType(FOLDER);
			}
		}
	}

	/**
	 * To set the default organize list.
	 * @param tabView
	 */
	@Override
	public void setDefaultOrganizePanel(String tabView) {
		if(treeChildSelectedItem!=null){
			if(getFolderLevel()==0) {
				organizeRootPnl.addStyleName("active");
			} else {
				organizeRootPnl.removeStyleName("active");
			}
			ShelfTreeWidget treeItemShelfTree = (ShelfTreeWidget) treeChildSelectedItem.getWidget();
			if(treeItemShelfTree!=null){
				if(organizeRootPnl.getStyleName().contains("active")) {
					treeItemShelfTree.setActiveStyle(false);
				} else {
					treeItemShelfTree.setActiveStyle(true);
				}
			}
		}
		if(tabView==null || tabView.equalsIgnoreCase(COURSE)){
			enableDisableCourseButton(true);
			organizelbl.setText(i18n.GL3335());
		//	btnSelectedText.setText(i18n.GL3335());
			lnkMyCourses.addStyleName("active");
			lnkMyFoldersAndCollecctions.removeStyleName("active");
			createNewCourse.setVisible(true);
			createNewCollection.setVisible(false);
			createNewAssessment.setVisible(false);
			collectionListScrollpanel.getElement().getStyle().setHeight(Window.getClientHeight()-150, Unit.PX);

		}else if(tabView.equalsIgnoreCase(FOLDER)){
			enableDisableCourseButton(false);
			organizelbl.setText(i18n.GL0180());
		//	btnSelectedText.setText(i18n.GL0180());
			lnkMyFoldersAndCollecctions.addStyleName("active");
			lnkMyCourses.removeStyleName("active");
			createNewCourse.setVisible(false);
			createNewCollection.setVisible(true);
			createNewAssessment.setVisible(true);
			collectionListScrollpanel.getElement().getStyle().setHeight(Window.getClientHeight()-210, Unit.PX);

		}
		collectionListScrollpanel.getElement().getStyle().setMarginRight(0, Unit.PX);
		collectionListScrollpanel.getElement().getStyle().setWidth(235, Unit.PX);



	}

	/**
	 * To set id's for Ui fields
	 */
	private void setIdForFields() {
		gShelfMainContainer.getElement().setId("gShelfMainContainer");
		//btnSelectedText.getElement().setId("btnSelectedText");
		lnkMyCourses.getElement().setId("lnkMyCourses");
		lnkMyFoldersAndCollecctions.getElement().setId("lnkMyFoldersAndCollecctions");
		StringUtil.setAttributes(createNewCourse.getElement(), "createNew", "createNew", "createNew");
		StringUtil.setAttributes(organizeRootPnl.getElement(), "organizeRootPnl", "", "");
		StringUtil.setAttributes(organizelbl.getElement(), "organizelbl", "", "");
		StringUtil.setAttributes(collectionListScrollpanel.getElement(), "FoldersListScrollpanel", "", "");
	}
	private void setTreeStucture() {
		floderTreeContainer.clear();
		shelfFolderTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				organizeRootPnl.removeStyleName("active");
				ShelfTreeWidget shelfTreeWidget = (ShelfTreeWidget) event.getSelectedItem().getWidget();
				treeChildSelectedItem = event.getSelectedItem();
				((ShelfTreeWidget) treeChildSelectedItem.getWidget()).openFolderItem();
				getUiHandlers().setBreadCrumbs(shelfTreeWidget.getUrlParams());
				setFolderActiveStatus();
				if(shelfTreeWidget.getCollectionDo()==null){
					String widgetType = shelfTreeWidget.getTreeWidgetType();
					getUiHandlers().setBreadCrumbs(null);
					getUiHandlers().setRightPanelData(shelfTreeWidget.getCollectionDo(), widgetType, null);
				}
				showLastEditCollaborater("", false);
			}
		});
		floderTreeContainer.add(shelfFolderTree);
	}

	/**
	 * To get Level of Folder
	 * @return folderLevel
	 */
	public int getFolderLevel() {
		int folderLevel = 0;
		String o1=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL);
		String o2=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL);
		String o3=AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL);
		String id=AppClientFactory.getPlaceManager().getRequestParameter(ID);
		if(o3!=null) {folderLevel = 3;} else if (o2!=null) {folderLevel = 2;} else if(o1!=null) {folderLevel = 1;} else if(id!=null) {folderLevel = 4;}
		return folderLevel;
	}

	/**
	 * To set the active status current selected tree item.
	 */
	public void setFolderActiveStatus() {
		ShelfTreeWidget shelfTreeWidget = (ShelfTreeWidget) treeChildSelectedItem.getWidget();
		if(shelfTreeWidget!=null&&shelfTreeWidget.getCollectionDo()!=null){
				if("folder".equalsIgnoreCase(shelfTreeWidget.getCollectionDo().getType()) || COURSE.equalsIgnoreCase(shelfTreeWidget.getCollectionDo().getType()) || UNIT.equalsIgnoreCase(shelfTreeWidget.getCollectionDo().getType()) || LESSON.equalsIgnoreCase(shelfTreeWidget.getCollectionDo().getType())) {
					TreeItem parent = treeChildSelectedItem.getParentItem();
					treeChildSelectedItem.getTree().setSelectedItem(null, false);
					if(parent != null){
						parent.setSelected(false);
					}
					treeChildSelectedItem.setState(treeChildSelectedItem.getState(), false);
					String type=shelfTreeWidget.getCollectionDo().getType();
					if(FOLDER.equalsIgnoreCase(type)){
						getUiHandlers().getChildFolderItems(shelfTreeWidget.getCollectionDo().getGooruOid(),type,shelfTreeWidget.getFolderOpenedStatus(),treeChildSelectedItem);
					}else{
						callChilds(shelfTreeWidget,type, treeChildSelectedItem);
					}
					shelfTreeWidget.setFolderOpenedStatus(true);
				}else{
					getUiHandlers().setCollectionContent(shelfTreeWidget.getCollectionDo());
					shelfTreeWidget.setCollectionOpenedStatus(true);
				}
				shelfTreeWidget.setActiveStyle(true);
				ShelfTreeWidget previousshelfTreeWidget = (ShelfTreeWidget) previousTreeChildSelectedItem.getWidget();
				if(previousshelfTreeWidget==null) {
					previousTreeChildSelectedItem = treeChildSelectedItem;
				}
				if(previousshelfTreeWidget!=null && previousshelfTreeWidget.getCollectionDo()!=null &&(shelfTreeWidget.getCollectionDo().getGooruOid()!=previousshelfTreeWidget.getCollectionDo().getGooruOid())) {
					previousshelfTreeWidget.setActiveStyle(false);
				}
				if(previousshelfTreeWidget!=null && previousshelfTreeWidget.getCollectionDo()==null){
					previousshelfTreeWidget.setActiveStyle(false);
				}
		}else{
			TreeItem parent = treeChildSelectedItem.getParentItem();
			treeChildSelectedItem.getTree().setSelectedItem(null, false);
			if(parent != null)parent.setSelected(false);
			treeChildSelectedItem.setState(treeChildSelectedItem.getState(), false);

			/*if(!"Collection".equalsIgnoreCase(shelfTreeWidget.getCollectionDo().getCollectionType()) && !"Assessment".equalsIgnoreCase(shelfTreeWidget.getCollectionDo().getCollectionType())){
				shelfTreeWidget.setFolderOpenedStatus(true);
			}else{
				shelfTreeWidget.setCollectionOpenedStatus(true);
			}*/
            if (shelfTreeWidget != null) {
        		shelfTreeWidget.setActiveStyle(true);
            }
			ShelfTreeWidget previousshelfTreeWidget = (ShelfTreeWidget) previousTreeChildSelectedItem.getWidget();
			if(previousshelfTreeWidget==null) {
				previousTreeChildSelectedItem = treeChildSelectedItem;
			}
			if(previousshelfTreeWidget!=null) {
				previousshelfTreeWidget.setActiveStyle(false);
			}
		}
		previousTreeChildSelectedItem = treeChildSelectedItem;
	}

	public void callChilds(ShelfTreeWidget shelfTreeWidget,String typeVal, TreeItem treeChildSelectedItem){
		String courseId=null,unitId=null,lessonId=null;
		if(COURSE.equalsIgnoreCase(typeVal)){
			courseId=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
		}else if(UNIT.equalsIgnoreCase(typeVal)){
			courseId=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
			unitId=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
		}else{
		    courseId=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
			unitId=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
			lessonId=AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL,null);
		}
		getUiHandlers().getChildFolderItemsForCourse(courseId, unitId, lessonId, typeVal, shelfTreeWidget.getFolderOpenedStatus(),treeChildSelectedItem);
		shelfTreeWidget.setFolderOpenedStatus(true);
	}
	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#setInSlot(java.lang.Object, com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void setInSlot(Object slot, Widget content) {
		pnlSlot.clear();
		if (content != null) {
			 if(slot==ShelfMainPresenter.RIGHT_SLOT){
				 pnlSlot.add(content);
			 }else{

			}
		}else{
			if(slot==ShelfMainPresenter.RIGHT_SLOT){
				if(COURSE.equalsIgnoreCase(getViewType())){
					getImgInlineLbl().setStyleName("");
					getCollectionLabel().setText("");
					pnlSlot.add(new EmptyCourseBuilderWidget() {
						@Override
						public void onClick() {
							createTopLevelTemplate(COURSE);
						}
					});
				}else{
					pnlSlot.add(new EmptyNoCollectionsWidget() {

						@Override
						public void onCollectionClick() {
							getUiHandlers().addNewContent("collection");

						}

						@Override
						public void onAssessmentClick() {
							getUiHandlers().addNewContent("assessment");

						}
					});
				}
			}
		}
	}
	/**
	 * To get the child items of particular tree widget
	 */
	@Override
	public void getChildFolderItems(TreeItem currentItem,List<FolderDo> folderListDo) {
		String o2 = null, o3 = null, selectedFolder = null, selectedFolderName = null, id = null;
		FolderDo folderDo = null;
		TreeItem selectedItem = null;
		ShelfTreeWidget shelfTreeWidget=null;
		ShelfTreeWidget selectedWidget = (ShelfTreeWidget) currentItem.getWidget();

		if(folderListDo!=null && selectedWidget != null) {
			int nextLevel = 1;
			if(selectedWidget.getLevel()==1) {
				o2=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL);
				id=AppClientFactory.getPlaceManager().getRequestParameter(ID);
				nextLevel = 2;
			} else if (selectedWidget.getLevel()==2) {
				o3=AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL);
				id=AppClientFactory.getPlaceManager().getRequestParameter(ID);
				nextLevel = 3;
			}else if (selectedWidget.getLevel()==3) {
				id=AppClientFactory.getPlaceManager().getRequestParameter(ID);
				nextLevel = 4;
			}
			for(int i=0;i<folderListDo.size();i++) {
				shelfTreeWidget = new ShelfTreeWidget(folderListDo.get(i), nextLevel,folderListDo.get(i).getType());
				shelfTreeWidget.setWidgetPositions(nextLevel, i, selectedWidget.getUrlParams());
				shelfTreeWidget.setTreeWidgetType(folderListDo.get(i).getType());
				TreeItem item = new TreeItem(shelfTreeWidget);
				currentItem.addItem(item);
				correctStyle(item);
				if(nextLevel==2&& (o2!=null&&o2.equalsIgnoreCase(folderListDo.get(i).getGooruOid()) || id!=null&&id.equalsIgnoreCase(folderListDo.get(i).getGooruOid()))) {
					if(o2!=null) {
						selectedFolder = o2;
					} else {
						selectedFolder = id;
					}
					selectedItem = item;
					selectedFolderName = folderListDo.get(i).getTitle();
					folderDo = folderListDo.get(i);
				} else if(nextLevel==3&& (o3!=null&&o3.equalsIgnoreCase(folderListDo.get(i).getGooruOid()) || id!=null&&id.equalsIgnoreCase(folderListDo.get(i).getGooruOid()))) {
					if(o3!=null) {
						selectedFolder = o3;
					} else {
						selectedFolder = id;
					}
					selectedItem = item;
					selectedFolderName = folderListDo.get(i).getTitle();
					folderDo = folderListDo.get(i);
				} else if (nextLevel==4&&id!=null&&id.equalsIgnoreCase(folderListDo.get(i).getGooruOid())) {
					selectedFolder = id;
					selectedItem = item;
					selectedFolderName = folderListDo.get(i).getTitle();
					folderDo = folderListDo.get(i);
				}
			}
		}

		if(currentItem.getState() && selectedWidget != null) {
			currentItem.setState(false);
			selectedWidget.setOpenStyle(false, currentItem.getChildCount());
		} else {
			currentItem.setState(true);
                        if (selectedWidget != null)
                            selectedWidget.setOpenStyle(true, currentItem.getChildCount());
		}
		//This will set the data in the right panel
		if(selectedWidget!=null){
			folderListDoChild.clear();
			int childWidgetsCount=currentItem.getChildCount();
			for (int i = 0; i < childWidgetsCount; i++) {
				ShelfTreeWidget widget = (ShelfTreeWidget)currentItem.getChild(i).getWidget();
				//If it is a template the object will be null so excluding that
				if(widget.getCollectionDo()!=null){
					folderListDoChild.add(widget.getCollectionDo());
				}
			}
			if(FOLDER.equalsIgnoreCase(selectedWidget.getCollectionDo().getType()) || COURSE.equalsIgnoreCase(selectedWidget.getCollectionDo().getType()) || UNIT.equalsIgnoreCase(selectedWidget.getCollectionDo().getType())|| LESSON.equalsIgnoreCase(selectedWidget.getCollectionDo().getType())){
				getUiHandlers().setRightPanelData(selectedWidget.getCollectionDo(), selectedWidget.getCollectionDo().getType(),folderListDoChild);
			}else{
				getUiHandlers().setRightListData(folderListDoChild,((ShelfTreeWidget)currentItem.getWidget()).getCollectionDo());
			}
		}
		if(selectedFolder!=null&&selectedItem!=null) {
			checkShelfRefreshStatus(selectedItem, selectedFolder);
			ShelfTreeWidget selectedWidget1 = (ShelfTreeWidget) currentItem.getWidget();
			selectedWidget1.setFolderOpenedStatus(true);
		}
		//treecurrentItem
	}

	private static void correctStyle(final UIObject uiObject) {
	      if (uiObject instanceof TreeItem) {
	         if (uiObject.getElement() != null) {
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
	 * This method is to get the childPageNumber
	 */
        @Override
	public int getChildPageNumber() {
		return childPageNumber;
	}

	@Override
	public void setChildPageNumber(Integer childPageNumber) {
		this.childPageNumber = childPageNumber;
	}
    /**
     * To set the user meta data
     */
	@Override
	public void setUserMetaData(List<FolderDo> collections, boolean clearShelfPanel) {
		String o1 = AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL);
		String id = AppClientFactory.getPlaceManager().getRequestParameter(ID);
		if (clearShelfPanel) {
			pageNumber = 1;
			shelfFolderTree.clear();
		}
		SHELF_COLLECTIONS.clear();
		if(collections != null) {
			SHELF_COLLECTIONS.addAll(collections);
			collectionItemDoSize = SHELF_COLLECTIONS.size();
		}
		String gooruOid = o1!=null?o1:id;
		int collectionCount=0;
		if(collections!=null){
			collectionItemDoSize = collections.size();
                    for (FolderDo floderDo : collections) {
                        if(!getShelffCollection(floderDo.getGooruOid())){
                            ShelfTreeWidget shelfTreeWidget = new ShelfTreeWidget(floderDo, 1,floderDo.getType());
                            shelfTreeWidget.setWidgetPositions(1, collectionCount, null);
                            shelfTreeWidget.setTreeWidgetType(floderDo.getType());
                            TreeItem folderItem=new TreeItem(shelfTreeWidget);
                            shelfFolderTree.addItem(folderItem);
                            //When page is refreshed, the folderItem previously selected will be highlighted.
                            if(gooruOid!=null&&gooruOid.equalsIgnoreCase(floderDo.getGooruOid())) {
                                checkShelfRefreshStatus(folderItem, floderDo.getGooruOid());
                                shelfTreeWidget.setFolderOpenedStatus(true);
                            }
                            collectionCount++;
                        }
                        floderTreeContainer.clear();
                        floderTreeContainer.add(shelfFolderTree);
                    }
		}
		AppClientFactory.printInfoLogger("treeItemcount-"+shelfFolderTree.getItemCount());
		if(shelfFolderTree.getItemCount()>=50 && getViewType().equalsIgnoreCase(COURSE)){
			setCreateCourse(false);
		}
		lnkMyCourses.setEnabled(true);
		lnkMyCourses.removeStyleName("disabled");
		lnkMyFoldersAndCollecctions.setEnabled(true);
		lnkMyFoldersAndCollecctions.removeStyleName("disabled");
	}

	private boolean getShelffCollection(String collectionId) {
		boolean flag = false;
		Iterator<Widget> widgets = shelfFolderTree.iterator();
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			try
			{
			if(((ShelfTreeWidget) widget).getCollectionDo()!=null){
			if (widget instanceof ShelfTreeWidget && ((ShelfTreeWidget) widget).getCollectionDo().getGooruOid().equals(collectionId)) {
				flag = true;
			}
			}
			}
			catch(Exception ex)
			{

			}
		}
		return flag;
	}

	/**
	 * @function checkShelfRefreshStatus
	 * @created_date : 11-Jun-2015
	 * @description
	 * @parm(s) : @param treeItem
	 * @return : void
	 * @throws : <Mentioned if any exceptions>
	*/

	private void checkShelfRefreshStatus(TreeItem treeItem, String parentId) {
		treeChildSelectedItem = treeItem;
		ShelfTreeWidget shelfTreeWidget = (ShelfTreeWidget) treeChildSelectedItem.getWidget();
		shelfTreeWidget.setActiveStyle(true);


		String id = AppClientFactory.getPlaceManager().getRequestParameter(ID);
		id = id!=null?id:"";
		if(!parentId.equalsIgnoreCase(id)) {
			String type=shelfTreeWidget.getCollectionDo().getType();
			if(FOLDER.equalsIgnoreCase(type)){
				getUiHandlers().getChildFolderItems(parentId,type,false,treeChildSelectedItem);
			}else{
				callChilds(shelfTreeWidget,type,treeChildSelectedItem);
			}
		}else{
			getUiHandlers().setCollectionContent(shelfTreeWidget.getCollectionDo());
		}
		ShelfTreeWidget previousshelfTreeWidget = (ShelfTreeWidget) previousTreeChildSelectedItem.getWidget();
		if(previousshelfTreeWidget!=null) {
			previousshelfTreeWidget.setActiveStyle(false);
		}
		previousTreeChildSelectedItem = treeChildSelectedItem;
	}

	public void adjustTreeItemElementsStyle(Tree shelfTreePanel){
		int treeItemsCount;
		if(shelfTreePanel!=null){
                    treeItemsCount = shelfTreePanel.getItemCount();
                    if (treeItemsCount  > 0 ) {
			for(int i=0;i<treeItemsCount;i++){
				TreeItem treeItem=shelfTreePanel.getItem(i);
				Widget shelfWidget= treeItem.getWidget();
				if(shelfWidget instanceof ShelfTreeWidget){
					adjustChildTreeItemsStyle(treeItem);
				}
				correctStyle(treeItem);
			}
                    }
		}
	}
	public void adjustChildTreeItemsStyle(TreeItem treeItem){
		if(treeItem != null) {
                    int treeItemsCount=treeItem.getChildCount();
                    if (treeItemsCount > 0) {
			for(int i=0;i<treeItemsCount;i++){
				TreeItem childTreeItem=treeItem.getChild(i);
				Widget shelfWidget= childTreeItem.getWidget();
				if(shelfWidget instanceof ShelfTreeWidget){
					adjustChildTreeItemsStyle(childTreeItem);
				}
				correctStyle(childTreeItem);
			}
                    }
		}
	}

	@UiHandler("organizeRootPnl")
	public void clickOnOrganizeRootPnl(ClickEvent event) {
		organizeRootPnl.addStyleName("active");
		ShelfTreeWidget shelfTreeWidget = (ShelfTreeWidget) treeChildSelectedItem.getWidget();
		if(shelfTreeWidget!=null&&shelfTreeWidget.getLevel()!=0) {
			shelfTreeWidget.setActiveStyle(false);
		}
		getUiHandlers().setRightListData(SHELF_COLLECTIONS,null);
		Map<String, String> params = new HashMap<>();
		params.put(VIEW, getViewType());
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
	}
	/**
	 * To create couse template and adding to the root tree
	 * @param event
	 */
	@UiHandler("createNewCourse")
	public void createNewCourseOrCollection(ClickEvent event) {
		if(isCreateCourse()){
			createTopLevelTemplate(COURSE);
		}
	}
	/**
	 * This method is used to display create course template
	 */
	public void createTopLevelTemplate(String type){
		ShelfTreeWidget shelfTreeWidget=null;
		if(!FOLDER.equals(getViewType()) && isCreateCourse() && type.equalsIgnoreCase(COURSE)){
			setCreateCourse(false);
			//createNewCourse.getElement().getFirstChildElement().getStyle().setBackgroundColor("#dddddd");
			createNewCourse.getElement().getFirstChildElement().getStyle().setCursor(Cursor.DEFAULT);
			organizeRootPnl.removeStyleName("active");
			shelfTreeWidget = new ShelfTreeWidget(null, 1,COURSE);
			shelfTreeWidget.setTreeWidgetType(COURSE);

			shelfTreeWidget.getTitleLbl().setText(UNTITLEDCOURSE);
			shelfTreeWidget.getTitleFocPanel().addStyleName("course");
			setTitleWithIcon(UNTITLEDCOURSE,"courseFolderCloseIcon");
			getUiHandlers().setRightPanelData(getFolderDo(COURSE,UNTITLEDCOURSE), COURSE,null);

			shelfTreeWidget.setFolderOpenedStatus(true);
        		shelfTreeWidget.setLevel(1);
		}else if(type.equalsIgnoreCase(COLLECTION)) {
			shelfTreeWidget = new ShelfTreeWidget(null, 1,COLLECTION);
			shelfTreeWidget.setTreeWidgetType(COLLECTION);

			shelfTreeWidget.getTitleLbl().setText(i18n.GL3367());
			shelfTreeWidget.getTitleFocPanel().addStyleName("collection");
			shelfTreeWidget.setCollectionOpenedStatus(true);
			getUiHandlers().setRightPanelData(getFolderDo(COLLECTION,i18n.GL3367()),COLLECTION,null);
        		shelfTreeWidget.setLevel(1);
		}else if(ASSESSMENT.equalsIgnoreCase(type) || ASSESSMENT_URL.equalsIgnoreCase(type)){
			shelfTreeWidget = new ShelfTreeWidget(null, 1,type);
			shelfTreeWidget.setTreeWidgetType(ASSESSMENT.equalsIgnoreCase(type)?ASSESSMENT:ASSESSMENT_URL);

			shelfTreeWidget.getTitleLbl().setText(ASSESSMENT.equalsIgnoreCase(type)?"UntitledAssessment":"UntitledExternalAssessment");
			shelfTreeWidget.getTitleFocPanel().addStyleName("assessment");
			shelfTreeWidget.setCollectionOpenedStatus(true);
			getUiHandlers().setRightPanelData(getFolderDo(type,shelfTreeWidget.getTitleLbl().getText()), type,null);
        		shelfTreeWidget.setLevel(1);
		}
		TreeItem treeItem = new TreeItem(shelfTreeWidget);
		treeItem.getElement().scrollIntoView();
		shelfFolderTree.insertItem(COURSE.equalsIgnoreCase(getViewType())?shelfFolderTree.getItemCount():0, treeItem);
		treeChildSelectedItem=treeItem;
		correctStyle(treeItem);
		floderTreeContainer.add(shelfFolderTree);
		setFolderActiveStatus();
		Map<String, String> params = new HashMap<>();
		params.put(VIEW, getViewType());
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
		collectionListScrollpanel.scrollToBottom();

	}
	/**
	 * To add new unit/lesson/collection/assessment template to the existing tree
	 */
	@Override
	public void createNewItem(String type,TreeItem currentShelfTreeWidget) {
		String o2=null,id=null,o3=null;
		String o1=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
		if(null==o1){
			if(type.equalsIgnoreCase(COURSE)||type.equalsIgnoreCase(COLLECTION)||type.toLowerCase().contains(ASSESSMENT.toLowerCase())){
				createTopLevelTemplate(type);
			}
		}else{
			ShelfTreeWidget selectedWidget = (ShelfTreeWidget) treeChildSelectedItem.getWidget();
			int nextLevel = 1;
			if(selectedWidget.getLevel()==1) {
				o2=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL);
				id=AppClientFactory.getPlaceManager().getRequestParameter(ID);
				nextLevel = 2;
			} else if (selectedWidget.getLevel()==2) {
				o3=AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL);
				id=AppClientFactory.getPlaceManager().getRequestParameter(ID);
				nextLevel = 3;
			}else if (selectedWidget.getLevel()==3) {
				id=AppClientFactory.getPlaceManager().getRequestParameter(ID);
				nextLevel = 4;
			}
			selectedWidget.setOpen(true);
			ShelfTreeWidget shelfTreeWidget = null;
			if(UNIT.equalsIgnoreCase(type)){
				shelfTreeWidget = new ShelfTreeWidget(null, 2,type);
				shelfTreeWidget.setTreeWidgetType(UNIT);
				shelfTreeWidget.getTitleLbl().setText(UNTITLEDUNIT);
				shelfTreeWidget.getTitleFocPanel().addStyleName("unit");
				shelfTreeWidget.setLevel(2);
				setViewTitleWthIcon(UNTITLEDUNIT,UNIT);
			}else if(LESSON.equalsIgnoreCase(type)){
				shelfTreeWidget = new ShelfTreeWidget(null, 3,type);
				shelfTreeWidget.setTreeWidgetType(LESSON);
				shelfTreeWidget.getTitleLbl().setText(UNTITLEDLESSON);
				shelfTreeWidget.getTitleFocPanel().addStyleName("lesson");
				shelfTreeWidget.setLevel(3);
				setViewTitleWthIcon(UNTITLEDLESSON,LESSON);
			}else if(FOLDER.equalsIgnoreCase(type)){
				shelfTreeWidget = new ShelfTreeWidget(null, nextLevel,type);
				shelfTreeWidget.setTreeWidgetType(FOLDER);
				shelfTreeWidget.getTitleLbl().setText(i18n.GL3394());
				//shelfTreeWidget.getTitleFocPanel().addStyleName("collection");
				shelfTreeWidget.setLevel(nextLevel);
			}else if(COLLECTION.equalsIgnoreCase(type)){
				if(getViewType().equalsIgnoreCase(FOLDER)){
					shelfTreeWidget = new ShelfTreeWidget(null,nextLevel,type);
				}else{
					shelfTreeWidget = new ShelfTreeWidget(null, 4,type);
				}
				shelfTreeWidget.setTreeWidgetType(COLLECTION);
				shelfTreeWidget.getTitleLbl().setText(i18n.GL3367());
				shelfTreeWidget.getTitleFocPanel().addStyleName("collection");
				shelfTreeWidget.setLevel(4);
				setViewTitleWthIcon(i18n.GL3367(),COLLECTION);
			}else if(ASSESSMENT.equalsIgnoreCase(type) || ASSESSMENT_URL.equalsIgnoreCase(type)){
				if(getViewType().equalsIgnoreCase(FOLDER)){
					shelfTreeWidget = new ShelfTreeWidget(null,nextLevel,type);
				}else{
					shelfTreeWidget = new ShelfTreeWidget(null, 4,type);
				}
				shelfTreeWidget.setTreeWidgetType(ASSESSMENT.equalsIgnoreCase(type)?ASSESSMENT:ASSESSMENT_URL);
				shelfTreeWidget.getTitleLbl().setText(ASSESSMENT.equalsIgnoreCase(type)?"UntitledAssessment":"UntitledExternalAssessment");
				shelfTreeWidget.getTitleFocPanel().addStyleName("assessment");
				shelfTreeWidget.setLevel(4);
				setViewTitleWthIcon(ASSESSMENT.equalsIgnoreCase(type)?"UntitledAssessment":"UntitledExternalAssessment",ASSESSMENT);
			}

			if (shelfTreeWidget == null) {
				StringBuilder builder = new StringBuilder("createNewItem: shelfTreeWidget is null");
				AppClientFactory.printSevereLogger(builder.toString());
				throw new IllegalStateException(builder.toString());
			}

			shelfTreeWidget.setWidgetPositions(nextLevel, 1, selectedWidget.getUrlParams());
			TreeItem item = new TreeItem(shelfTreeWidget);
			if(currentShelfTreeWidget!=null){
				currentShelfTreeWidget.insertItem(currentShelfTreeWidget.getChildCount(), item);
				currentShelfTreeWidget.setState(true);
			}else{
				treeChildSelectedItem.insertItem(treeChildSelectedItem.getChildCount(), item);
				treeChildSelectedItem.setState(true);
			}
			if(!COLLECTION.equalsIgnoreCase(type) && !type.contains(ASSESSMENT)){
				shelfTreeWidget.setFolderOpenedStatus(true);
			}else{
				shelfTreeWidget.setCollectionOpenedStatus(true);
			}

			correctStyle(item);
			treeChildSelectedItem=item;
			setFolderActiveStatus();
		}
	}

	/**
	 * To get more collection item after scroll down,if collection is more than 20.
	 * @param event instance of ScrollEvent
	 */
	class ScrollHandlerImpl implements ScrollHandler{
		boolean isLeftScroll;
		ScrollHandlerImpl(boolean isLeftScroll){
			this.isLeftScroll=isLeftScroll;
		}
		@Override
		public void onScroll(ScrollEvent event) {
		}
	}

	@UiHandler("collectionListScrollpanel")
	public void onScroll(ScrollEvent event){
		executeScroll(true);
	}

	@Override
	public void executeScroll(boolean isLeftScroll){
		int limit=20;
		lnkMyCourses.setEnabled(false);
		lnkMyCourses.addStyleName("disabled");
		lnkMyFoldersAndCollecctions.setEnabled(false);
		lnkMyFoldersAndCollecctions.addStyleName("disabled");
		if(getViewType().equalsIgnoreCase(COURSE)){
			limit=50;
		}
		if(isLeftScroll){
			if(collectionListScrollpanel.getVerticalScrollPosition() == collectionListScrollpanel.getMaximumVerticalScrollPosition() && collectionItemDoSize >= limit) {
				pageNumber = pageNumber + 1;
				getUiHandlers().getMoreListItems(limit, pageNumber, false);
			}
		}else if(collectionItemDoSize >= limit){
			pageNumber = pageNumber + 1;
			getUiHandlers().getMoreListItems(limit, pageNumber, false);
		}
	}
   	@Override
   	public HTMLPanel getSlot(){
   		return pnlSlot;
   	}

   	@Override
   	public void setNoDataForAnonymousUser(boolean isAnonymous){
   		if(isAnonymous){
   			pnlMainContainer.setVisible(false);
   			pnlNoDataContainer.setVisible(true);
   			pnlNoDataContainer.clear();
   			pnlNoDataContainer.add(new FoldersWelcomePage());
   			Window.enableScrolling(true);
   		}else{
   			pnlMainContainer.setVisible(true);
   			pnlNoDataContainer.setVisible(false);
   		}
   	}
   	/**
   	 * @return viewType
   	 */
   	@Override
   	public String getViewType(){
   		String view =AppClientFactory.getPlaceManager().getRequestParameter(VIEW,null);
		return view==null?COURSE:view;
   	}
    /**
     * Highlight the Tree based on id's when reveal the page.
     */
	@Override
	public void updateLeftShelfPanelActiveStyle() {
		String gooruOid = null;
		String o1 = AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL);
		String o2 = AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL);
		String o3 = AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL);
		String id = AppClientFactory.getPlaceManager().getRequestParameter(ID);
		ShelfTreeWidget shelfTreeWidget = (ShelfTreeWidget) treeChildSelectedItem.getWidget();

		if(shelfTreeWidget==null || organizeRootPnl.getStyleName().contains("active")) {
			if(id!=null) {
				gooruOid = id;
			} else {
				gooruOid = o1;
			}
			for(int i = 0; i < shelfFolderTree.getItemCount(); i++) {
				TreeItem item = shelfFolderTree.getItem(i);
				checkFolderItemStyle(item, gooruOid);
			}
			organizeRootPnl.removeStyleName("active");
		} else {
			/** If the selected folder is closed, and when clicked on right side the following condition executes and make that folder open. **/
			if(treeChildSelectedItem.getParentItem()!=null){
				ShelfTreeWidget parentTreeWidget = (ShelfTreeWidget) treeChildSelectedItem.getParentItem().getWidget();
				parentTreeWidget.getTitleFocPanel().addStyleName("open");
				if(treeChildSelectedItem.getParentItem().getParentItem()!=null){
					ShelfTreeWidget parent = (ShelfTreeWidget) treeChildSelectedItem.getParentItem().getParentItem().getWidget();
					parent.getTitleFocPanel().addStyleName("open");
				}
			}
			shelfTreeWidget.getTitleFocPanel().addStyleName("open");
			if(treeChildSelectedItem.getState()==false){
				treeChildSelectedItem.setState(true);
			}
			if(organizeRootPnl.getStyleName().contains("active")) {
				gooruOid = o1;
			} else if(shelfTreeWidget.getLevel()==1) {
				if(id==null){
					gooruOid = o2;
				}else{
					gooruOid = id;
				}
			} else if(shelfTreeWidget.getLevel()==2) {
				if(id==null){
					gooruOid = o3;
				}else{
					gooruOid = id;
				}
			} else if(shelfTreeWidget.getLevel()==3) {
				gooruOid = id;
			}
			for(int i = 0; i < treeChildSelectedItem.getChildCount(); i++) {
				 TreeItem item = treeChildSelectedItem.getChild(i);
				 checkFolderItemStyle(item, gooruOid);
			}
		}

	}

	private void checkFolderItemStyle(TreeItem item, String gooruOid) {

		ShelfTreeWidget updatedItem = (ShelfTreeWidget) item.getWidget();
		if(gooruOid!=null){
			if(gooruOid.equalsIgnoreCase(updatedItem.getCollectionDo().getGooruOid())) {
				treeChildSelectedItem = item;
				getUiHandlers().setBreadCrumbs(updatedItem.getUrlParams());
				//updatedItem.setActiveStyle(true);
				setFolderActiveStatus();
				return;
			}
		}
	}
	/**
	 * set basic data of course and get the folderObj
	 * @param type
         * @param title
	 * @param untitledcourse2
	 * @return folderObj
	 */
	public FolderDo getFolderDo(String type, String title){
		FolderDo folderObj = new FolderDo();
		folderObj.setTitle(title);
		folderObj.setType(type.toLowerCase());
		return folderObj;
	}
   /**
    * Updates the respective tree widget,
    * as we create/update course/unit/lesson/collection
    */
	@Override
	public void updateTreeWidget(FolderDo courseDo,boolean flag, TreeItem currentShelfTreeWidget) {
		ShelfTreeWidget shelfTreeWidget = (ShelfTreeWidget) currentShelfTreeWidget.getWidget();
		shelfTreeWidget.updateData(courseDo);
		String type = shelfTreeWidget.getTreeWidgetType();
		if(COURSE.equalsIgnoreCase(type)){
			HashMap<String,String> urlParams = new HashMap<>();
			urlParams.put(COURSE, courseDo.getTitle());
			urlParams.put(O1_LEVEL,courseDo.getGooruOid());
			shelfTreeWidget.setUrlParams(urlParams);
//			setViewTitleWthIcon(courseDo.getTitle(),COURSE);
			setTitleWithIcon(courseDo.getTitle(),"courseFolderCloseIcon");
		}else if(UNIT.equalsIgnoreCase(type)){
			ShelfTreeWidget parentShelfTreeWidget = (ShelfTreeWidget) treeChildSelectedItem.getParentItem().getWidget();
			HashMap<String,String> urlParams = new HashMap<>();

			urlParams.put(COURSE,parentShelfTreeWidget.getUrlParams().get(COURSE));
			urlParams.put(UNIT, courseDo.getTitle());

			urlParams.put(O1_LEVEL,parentShelfTreeWidget.getUrlParams().get("o1"));
			urlParams.put(O2_LEVEL,courseDo.getGooruOid());
			shelfTreeWidget.setUrlParams(urlParams);
			setTitleWithIcon(courseDo.getTitle(),"unitFolderCloseIcon");
		}else if(LESSON.equalsIgnoreCase(type)){
			ShelfTreeWidget courseShelfTreeWidget = (ShelfTreeWidget) currentShelfTreeWidget.getParentItem().getParentItem().getWidget();
			HashMap<String,String> urlParams = new HashMap<>();
			urlParams.put(COURSE,courseShelfTreeWidget.getUrlParams().get(COURSE));
			urlParams.put(O1_LEVEL,courseShelfTreeWidget.getUrlParams().get(O1_LEVEL));

			ShelfTreeWidget unitShelfTreeWidget = (ShelfTreeWidget) currentShelfTreeWidget.getParentItem().getWidget();
			unitShelfTreeWidget.getCollectionDo().getSummary().setLessonCount(unitShelfTreeWidget.getCollectionDo().getSummary().getLessonCount()+1);

			urlParams.put(UNIT, unitShelfTreeWidget.getUrlParams().get(UNIT));
			urlParams.put(O2_LEVEL,unitShelfTreeWidget.getUrlParams().get(O2_LEVEL));

			urlParams.put(LESSON,courseDo.getTitle());
			urlParams.put(O3_LEVEL,courseDo.getGooruOid());

			shelfTreeWidget.setUrlParams(urlParams);
			setTitleWithIcon(courseDo.getTitle(),"lessonFolderCloseIcon");
		}else if(COLLECTION.equalsIgnoreCase(type) || ASSESSMENT.equalsIgnoreCase(type) || ASSESSMENT_URL.equalsIgnoreCase(type)){
			if(getViewType().equalsIgnoreCase(FOLDER)){
				getCollectionLabel().setText(courseDo.getTitle());
				shelfTreeWidget.setUrlParams(getTreeParentIds(courseDo));
				setTitleWithIcon(courseDo.getTitle(),"");
			}else{
				ShelfTreeWidget courseShelfTreeWidget = (ShelfTreeWidget) currentShelfTreeWidget.getParentItem().getParentItem().getParentItem().getWidget();
				HashMap<String,String> urlParams = new HashMap<String,String>();
				urlParams.put(COURSE,courseShelfTreeWidget.getUrlParams().get(COURSE));
				urlParams.put(O1_LEVEL,courseShelfTreeWidget.getUrlParams().get(O1_LEVEL));

				ShelfTreeWidget unitShelfTreeWidget = (ShelfTreeWidget) currentShelfTreeWidget.getParentItem().getParentItem().getWidget();
				urlParams.put(UNIT, unitShelfTreeWidget.getUrlParams().get(UNIT));
				urlParams.put(O2_LEVEL,unitShelfTreeWidget.getUrlParams().get(O2_LEVEL));

				ShelfTreeWidget lessonShelfTreeWidget = (ShelfTreeWidget) currentShelfTreeWidget.getParentItem().getWidget();
				urlParams.put(LESSON, lessonShelfTreeWidget.getUrlParams().get(LESSON));
				urlParams.put(O3_LEVEL,unitShelfTreeWidget.getUrlParams().get(O3_LEVEL));
				if(flag){
					if(COLLECTION.equalsIgnoreCase(type)){
						lessonShelfTreeWidget.getCollectionDo().getSummary().setCollectionCount(lessonShelfTreeWidget.getCollectionDo().getSummary().getCollectionCount()+1);
					}else{
						lessonShelfTreeWidget.getCollectionDo().getSummary().setAssessmentCount(lessonShelfTreeWidget.getCollectionDo().getSummary().getAssessmentCount()+1);
					}
				}
				urlParams.put("id",courseDo.getGooruOid());
				urlParams.put(COLLECTION.equalsIgnoreCase(type)?COLLECTION:ASSESSMENT_URL.equalsIgnoreCase(type)?ASSESSMENT_URL:ASSESSMENT,courseDo.getTitle());
				shelfTreeWidget.setUrlParams(urlParams);
				setTitleWithIcon(courseDo.getTitle(),(type.contains(ASSESSMENT)?"breadcrumbsAssessmentIcon":"breadcrumbsCollectionIcon"));
			}
		}
	}


	@Override
	public void updateWidgetsCount(CollectionItemDo collectionItem,boolean isDelete) {
		try{
		ShelfTreeWidget collectionShelfTreeWidget = (ShelfTreeWidget) treeChildSelectedItem.getWidget();
		String type=collectionItem!=null?collectionItem.getResource()!=null?(collectionItem.getResource().getResourceFormat()!=null?collectionItem.getResource().getResourceFormat().getDisplayName():""):"":"";
		if(collectionShelfTreeWidget.getCollectionDo() !=null && collectionShelfTreeWidget.getCollectionDo().getSummary()!=null)
		{		
			if("Question".equalsIgnoreCase(type)){
				//question count increment and decrement
				int count;
				if(isDelete){
					count=collectionShelfTreeWidget.getCollectionDo().getSummary().getQuestionCount()-1;
				}else{
					count=collectionShelfTreeWidget.getCollectionDo().getSummary().getQuestionCount()+1;	
				}
				collectionShelfTreeWidget.getCollectionDo().getSummary().setQuestionCount(count);
			}else{
				//resource count increment and decrement
				int count;			
				if(isDelete){
					count=collectionShelfTreeWidget.getCollectionDo().getSummary().getResourceCount()-1;
				}else{
					count=collectionShelfTreeWidget.getCollectionDo().getSummary().getResourceCount()+1;
				}
				collectionShelfTreeWidget.getCollectionDo().getSummary().setResourceCount(count);		
			}
		}
		}catch(Exception e){
			AppClientFactory.printSevereLogger("Exception------"+e);
		}
	}
	private HashMap<String,String> getTreeParentIds(FolderDo courseDo) {
		String o1 = AppClientFactory.getPlaceManager().getRequestParameter("o1",null);
		String o2 = AppClientFactory.getPlaceManager().getRequestParameter("o2",null);
		String o3 = AppClientFactory.getPlaceManager().getRequestParameter("o3",null);
		ShelfTreeWidget parentShelfTreeWidget = null;
		HashMap<String,String> urlParams = new HashMap<>();
		if(o3!=null){
			parentShelfTreeWidget = (ShelfTreeWidget) treeChildSelectedItem.getParentItem().getParentItem().getParentItem().getWidget();
			urlParams.put(O1_LEVEL,parentShelfTreeWidget.getUrlParams().get(O1_LEVEL));
			urlParams.put(O2_LEVEL,parentShelfTreeWidget.getUrlParams().get(O2_LEVEL));
			urlParams.put(O3_LEVEL,parentShelfTreeWidget.getUrlParams().get(O3_LEVEL));
			urlParams.put("id",courseDo.getGooruOid());
		}else if(o2!=null){
			parentShelfTreeWidget = (ShelfTreeWidget) treeChildSelectedItem.getParentItem().getParentItem().getWidget();
			urlParams.put(O1_LEVEL,parentShelfTreeWidget.getUrlParams().get(O1_LEVEL));
			urlParams.put(O2_LEVEL,parentShelfTreeWidget.getUrlParams().get(O2_LEVEL));
			urlParams.put("id",courseDo.getGooruOid());
		}else if(o1!=null){
			//if(treeChildSelectedItem.getParentItem()!=null){
			parentShelfTreeWidget = (ShelfTreeWidget) treeChildSelectedItem.getParentItem().getWidget();
			urlParams.put(O1_LEVEL,parentShelfTreeWidget.getUrlParams().get(O1_LEVEL));
			urlParams.put("id",courseDo.getGooruOid());
		}else{
			urlParams.put("id",courseDo.getGooruOid());
		}
		return urlParams;
	}

	/**
	 * Sets the boolean value to enable or disable the course create.
	 */
	@Override
	public void enableDisableCourseButton(boolean isEnable) {
		AppClientFactory.printInfoLogger("enable-Itemcount"+shelfFolderTree.getItemCount());
		if(isEnable){
			//createNewCourse.getElement().getFirstChildElement().getStyle().setBackgroundColor("#4d99cd");
			createNewCourse.getElement().getFirstChildElement().getStyle().setCursor(Cursor.POINTER);
		}else{
			//createNewCourse.getElement().getFirstChildElement().getStyle().setBackgroundColor("#dddddd");
			createNewCourse.getElement().getFirstChildElement().getStyle().setCursor(Cursor.DEFAULT);
		}
		setCreateCourse(isEnable);
	}

	/**
	 * @return the isCreateCourse
	 */
	public boolean isCreateCourse() {
		return isCreateCourse;
	}

	/**
	 * @param isCreateCourse the isCreateCourse to set
	 */
	public final void setCreateCourse(boolean isCreateCourse) {
		this.isCreateCourse = isCreateCourse;
	}
	@Override
	public Label getCollectionLabel(){
		return lblCollectionTitle;
	}



	@Override
	public void removeDeletedTreeWidget(String deletedTreeWidgetId,String currentTypeView){
		if(COURSE.equalsIgnoreCase(currentTypeView)){
			/*for (FolderDo folderDo : SHELF_COLLECTIONS) {
				if(folderDo.getGooruOid().equalsIgnoreCase(deletedTreeWidgetId)){
					SHELF_COLLECTIONS.remove(folderDo);
					break;
				}
			}
			organizeRootPnl.addStyleName("active");
			getUiHandlers().setRightListData(SHELF_COLLECTIONS, null);*/
			getUiHandlers().setVersion();
			organizeRootPnl.addStyleName("active");
			treeChildSelectedItem.remove();
			Map<String, String> params= new HashMap<>();
			params.put("view", "Folder");
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
		}else if(UNIT.equalsIgnoreCase(currentTypeView)){
			ShelfTreeWidget deletedTreeParentWidget = (ShelfTreeWidget) treeChildSelectedItem.getParentItem().getWidget();
			TreeItem treeItem = treeChildSelectedItem.getParentItem();
			getUiHandlers().setRightPanelData(deletedTreeParentWidget.getCollectionDo(), deletedTreeParentWidget.getCollectionDo().getType(),folderListDoChild);
			treeChildSelectedItem.remove();
			checkFolderItemStyle(treeItem,deletedTreeParentWidget.getCollectionDo().getGooruOid());
//			getUiHandlers().onDeleteSetBreadCrumbs(deletedTreeParentWidget.getCollectionDo().getTitle(),COURSE);
		}else if(LESSON.equalsIgnoreCase(currentTypeView)){
			ShelfTreeWidget deletedTreeParentWidget = (ShelfTreeWidget) treeChildSelectedItem.getParentItem().getWidget();
			TreeItem treeItem = treeChildSelectedItem.getParentItem();
			getUiHandlers().setRightPanelData(deletedTreeParentWidget.getCollectionDo(), deletedTreeParentWidget.getCollectionDo().getType(),folderListDoChild);
			treeChildSelectedItem.remove();
			checkFolderItemStyle(treeItem,deletedTreeParentWidget.getCollectionDo().getGooruOid());
//			getUiHandlers().onDeleteSetBreadCrumbs(deletedTreeParentWidget.getCollectionDo().getTitle(),UNIT);

		}else if(COLLECTION.equalsIgnoreCase(currentTypeView) || currentTypeView.contains(ASSESSMENT)){
			ShelfTreeWidget deletedTreeParentWidget = (ShelfTreeWidget) treeChildSelectedItem.getParentItem().getWidget();
			TreeItem treeItem = treeChildSelectedItem.getParentItem();
			getUiHandlers().setRightPanelData(deletedTreeParentWidget.getCollectionDo(), deletedTreeParentWidget.getCollectionDo().getType(),folderListDoChild);
			treeChildSelectedItem.remove();
			if(COLLECTION.equalsIgnoreCase(currentTypeView)){
				deletedTreeParentWidget.getCollectionDo().getSummary().setCollectionCount(deletedTreeParentWidget.getCollectionDo().getSummary().getCollectionCount()-1);
			}else{
				deletedTreeParentWidget.getCollectionDo().getSummary().setAssessmentCount(deletedTreeParentWidget.getCollectionDo().getSummary().getAssessmentCount()-1);
			}
			checkFolderItemStyle(treeItem,deletedTreeParentWidget.getCollectionDo().getGooruOid());
//			getUiHandlers().onDeleteSetBreadCrumbs(deletedTreeParentWidget.getCollectionDo().getTitle(),COLLECTION.equalsIgnoreCase(currentTypeView)?COLLECTION:ASSESSMENT);
		}
	}


	/**
	 * Reorders shelf list items to the new respective position.
	 */

	@Override
	public void reorderShelfItems(String itemId, int toBeMovedPos, String direction, HashMap<String, String> params, FolderDo folderDo, String itemSeqNumb){
		if(direction.equals(DOWN_ARROW)){
			toBeMovedPos-=1;
		}
		if(params.get(O3_LEVEL)!=null){
			TreeItem level1Item = getFirstLevelTreeWidget(params.get(O1_LEVEL));
			if(level1Item!=null) {
				TreeItem level2Item = getSecondLevelTreeWidget(level1Item, params.get(O2_LEVEL));
				if(level2Item!=null){
					TreeItem level3Item = getSecondLevelTreeWidget(level2Item, params.get(O3_LEVEL));
					TreeItem shelfCollection = getChildFolderWidgetToReorder(level3Item,itemId);
					level3Item.insertItem(toBeMovedPos, shelfCollection);
					correctStyle(shelfCollection);
				}
			}
		}else if(params.get(O2_LEVEL)!=null){
			TreeItem level1Item = getFirstLevelTreeWidget(params.get(O1_LEVEL));
			if(level1Item!=null) {
				TreeItem level2Item = getSecondLevelTreeWidget(level1Item, params.get(O2_LEVEL));
				TreeItem shelfCollection = getChildFolderWidgetToReorder(level2Item,itemId);
				level2Item.insertItem(toBeMovedPos, shelfCollection);
				correctStyle(shelfCollection);
			}
		}else if(params.get(O1_LEVEL)!=null){
			TreeItem level1Item = getFirstLevelTreeWidget(params.get(O1_LEVEL));
			TreeItem shelfCollection = getChildFolderWidgetToReorder(level1Item,itemId);
			level1Item.insertItem(toBeMovedPos, shelfCollection);
			correctStyle(shelfCollection);
		}else{
			TreeItem shelfCollection = getWidgetToreorder(itemId);
			shelfFolderTree.insertItem(toBeMovedPos, shelfCollection);
			adjustTreeItemElementsStyle(shelfFolderTree);
		}
	}

	/**
	 *
	 * @param itemId {@link String}
	 * @return item {@link TreeItem}
	 */
	private TreeItem getWidgetToreorder(String itemId) {
		int childCount=shelfFolderTree.getItemCount();
		for(int i=0;i<childCount;i++){
			TreeItem item=shelfFolderTree.getItem(i);
			Widget widget=item.getWidget();
			if (widget instanceof ShelfTreeWidget && ((ShelfTreeWidget) widget).getCollectionDo().getGooruOid().equals(itemId)) {
				return item;
			}
		}
		return null;
	}
	public TreeItem getFirstLevelTreeWidget(String gooruOid) {
		 for(int i = 0; i < shelfFolderTree.getItemCount(); i++) {
			 TreeItem item = shelfFolderTree.getItem(i);
			 ShelfTreeWidget selectedItem = (ShelfTreeWidget) item.getWidget();
			 if(selectedItem.getCollectionDo().getGooruOid().equalsIgnoreCase(gooruOid)) {
				 return item;
			 }
		 }
		 return null;
	}
	private TreeItem getChildFolderWidgetToReorder(TreeItem level1Item,String itemId) {
			int childCount=level1Item.getChildCount();
			for(int i=0;i<childCount;i++){
				TreeItem item=level1Item.getChild(i);
				Widget widget=item.getWidget();
				if (widget instanceof ShelfTreeWidget && ((ShelfTreeWidget) widget).getCollectionDo().getGooruOid().equals(itemId)) {
					return item;
				}
			}
			return null;
	}
	public TreeItem getSecondLevelTreeWidget(TreeItem widget, String gooruOid) {
		 for(int i = 0; i < widget.getChildCount(); i++) {
			 TreeItem item = widget.getChild(i);
			 ShelfTreeWidget selectedItem = (ShelfTreeWidget) item.getWidget();
			 if(selectedItem.getCollectionDo().getGooruOid().equalsIgnoreCase(gooruOid)) {
				 return item;
			 }
		 }
		 return null;
	}
	/**
	 * This method is used to get pageNumber
	 *
	 * @return pageNumber
	 */
	public static int getpageNumber() {
		return pageNumber;
	}
	@UiHandler("createNewCollection")
	public void clickOnCollection(ClickEvent clickEvent){
		getUiHandlers().addNewContent("collection");
		getImgInlineLbl().setStyleName("");
	}
	@UiHandler("createNewAssessment")
	public void clickOnAssessment(ClickEvent clickEvent){
		getUiHandlers().addNewContent("assessment");
		getImgInlineLbl().setStyleName("");
	}

	@Override
	public InlineLabel getImgInlineLbl() {
		return imgIconLbl;
	}

	@Override
	public void setViewTitleWthIcon(String title, String type) {
		if(COURSE.equalsIgnoreCase(type)){
			setTitleWithIcon(StringUtil.isEmpty(title)?i18n.GL3347():title,"courseFolderCloseIcon");
		}else if(UNIT.equalsIgnoreCase(type)){
			setTitleWithIcon(StringUtil.isEmpty(title)?i18n.GL3364():title, "unitFolderCloseIcon");
		}else if(LESSON.equalsIgnoreCase(type)){
			setTitleWithIcon(StringUtil.isEmpty(title)?i18n.GL3365():title,"lessonFolderCloseIcon");
		}else if(COLLECTION.equalsIgnoreCase(type) || ASSESSMENT.equalsIgnoreCase(type) || ASSESSMENT_URL.equalsIgnoreCase(type)){
			setTitleWithIcon((COLLECTION.equalsIgnoreCase(type)&&StringUtil.isEmpty(title))?i18n.GL3367():
					                       (ASSESSMENT.equalsIgnoreCase(type)&&StringUtil.isEmpty(title))?i18n.GL3460():
					                       (ASSESSMENT_URL.equalsIgnoreCase(type)&&StringUtil.isEmpty(title))?"UntitledExternalAssessment":title, type.contains(ASSESSMENT)?"breadcrumbsAssessmentIcon":"breadcrumbsCollectionIcon");
		}/*else{
			setTitleWithIcon(StringUtil.isEmpty(title)?i18n.GL3364():title, "");
		}*/
	}


	private void setTitleWithIcon(String title, String iconStyle) {
		getTitleIconContainer().setVisible(true);
		if(FOLDER.equalsIgnoreCase(getViewType())){
			getImgInlineLbl().setVisible(false);
			getCollectionLabel().setText(title);
		}else{
			getImgInlineLbl().setStyleName(iconStyle);
			getCollectionLabel().setText(title);
			getImgInlineLbl().setVisible(true);
			getCollectionLabel().setVisible(true);
		}
	}

	@Override
	public HTMLPanel getTitleIconContainer() {
		return titleIconContainer;
	}
	public Image loadingImage(){
		Image loadingImage =  new Image();
		loadingImage.setUrl(LOADER_IMAGE);
		loadingImage.getElement().setId("myCollectionsListViewLoaddingImage");
		return loadingImage;
	}

	RemoveMovedCollectionFolderHandler deleteCollaborator = new RemoveMovedCollectionFolderHandler() {

		@Override
		public void removeMovedCollectionFromShelf(String sourceId) {
			ShelfTreeWidget collectionShelfTreeWidget = (ShelfTreeWidget) treeChildSelectedItem.getWidget();
			String type = collectionShelfTreeWidget.getCollectionDo().getType();
			String o1 = AppClientFactory.getPlaceManager().getRequestParameter("o1",null);
			String o2 = AppClientFactory.getPlaceManager().getRequestParameter("o2",null);
			String o3 = AppClientFactory.getPlaceManager().getRequestParameter("o3",null);
			String id = AppClientFactory.getPlaceManager().getRequestParameter("id",null);
			if(o3!=null){
				removeDeletedTreeWidget(sourceId, type);
			}else if(o2!=null){
				removeDeletedTreeWidget(sourceId, LESSON);
			}else if(o1!=null){
				removeDeletedTreeWidget(sourceId, UNIT);
			}else{
				removeDeletedTreeWidget(sourceId, COURSE);
			}
		}
	};

	@Override
	public TreeItem getCurrentEditingWidget() {
//		TreeItem shelfTreeWidget = treeChildSelectedItem;
		return treeChildSelectedItem;
	}

	@Override
	public void showLastEditCollaborater(String lastEditedBy,
			boolean hasLastModifiedUser) {
		lblLastEditedBy.setText(lastEditedBy);
		lblLastEditedBy.setVisible(hasLastModifiedUser);
	}

	@Override
	public void invokeSpinner() {
		shelfFolderTree.clear();
		shelfFolderTree.add(loadingImage());
		pnlSlot.clear();
		pnlSlot.add(loadingImage());
	}
}
