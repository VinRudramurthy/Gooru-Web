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
package org.ednovo.gooru.client.mvp.gshelf.righttabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.util.FolderInfoWidget;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.DeletePopupViewVc;
import org.ednovo.gooru.client.uc.AlertContentUc;
import org.ednovo.gooru.client.uc.DeleteContentPopup;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class MyCollectionsRightClusterView extends BaseViewWithHandlers<MyCollectionsRightClusterUiHandlers> implements IsMyCollectionsRightClusterView,ClientConstants  {

	private static MyCollectionsRightViewUiBinder uiBinder = GWT.create(MyCollectionsRightViewUiBinder.class);

	interface MyCollectionsRightViewUiBinder extends UiBinder<Widget, MyCollectionsRightClusterView> {
	}
	
	public MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField HTMLPanel mainPanel,pnlSlotInnerContent,toggleButton,deletePnl;
	@UiField Anchor lnkInfo,lnkContent,lnkshare,lnkPreview,lnkDeleteButton;
	@UiField HTMLEventPanel popupPanelDropDwn,copyPopupPanel;
	@UiField Label copyLbl,moveLbl,myCollDelLbl;
	
	@UiField FlowPanel pnlBreadCrumbMain;
	
	FolderDo folderObj;
	
	DeletePopupViewVc deletePopup = null;
	
	DeleteContentPopup deleteContentPopup = null;
	
	ArrayList<ClasspageDo> classesList = new ArrayList<>();
	
	final String ACTIVE="active";
	private static final String O1_LEVEL = "o1";
	private static final String O2_LEVEL = "o2";
	private static final String O3_LEVEL = "o3";
	
	private static final String COURSE = "Course";
	private static final String UNIT = "Unit";
	private static final String LESSON = "Lesson";
	private static final String COLLECTION = "collection";
	private static final String ASSESSMENT = "assessment";
	private static final String ASSESSMENT_URL = "assessment/url";
	final String PASTEURL="Paste URL here";
	
	private String currentTypeView;
	String o1,o2,o3;
	String oldO1Value=null,oldO2Value=null,oldO3Value=null;
	
	ArrayList<String> breadCumsSting=new ArrayList<String>();
	private boolean isCopySelected= false;
	private boolean isMoveSelected= false;
	
	private boolean isCollaborator= false;
	
	
	public MyCollectionsRightClusterView() {
		setWidget(uiBinder.createAndBindUi(this));
		setIds();
		lnkInfo.addClickHandler(new TabClickHandler(1,lnkInfo));
		lnkContent.addClickHandler(new TabClickHandler(2,lnkContent));
		lnkshare.addClickHandler(new TabClickHandler(3,lnkshare));
		lnkDeleteButton.addClickHandler(new DeleteContent());
		lnkPreview.addClickHandler(new PreviewClickHandler());
//		popupPanelDropDwn.addClickHandler(new openDropDownFilters());
		
		copyLbl.addClickHandler(new onCopyClickHandler());
		moveLbl.addClickHandler(new onMoveClickHandler());
		myCollDelLbl.addClickHandler(new DeleteContentData()); 
		
		lnkPreview.setVisible(false);
		toggleButton.setVisible(false);
		copyLbl.setText(i18n.GL0827());
		moveLbl.setText(i18n.GL1261());
		
	/*	Event.addNativePreviewHandler(new NativePreviewHandler() {
	        public void onPreviewNativeEvent(NativePreviewEvent event) {
	        	hideDropDown(event);
	          }
	    });*/
		
	}
	public void setIds(){
		mainPanel.getElement().setId("gShelfCourseInfo");
		pnlSlotInnerContent.getElement().setId("pnlSlotInnerContent");
		lnkInfo.getElement().setId("lnkInfo");
		lnkContent.getElement().setId("lnkContent");
		lnkshare.getElement().setId("lnkshare");
		pnlBreadCrumbMain.getElement().setId("pnlBreadCrumbMain");
	}
	/**
	 * This inner class will handle the click event on the info,content and share tab.
	 */
	class TabClickHandler implements ClickHandler{
		int index;
		Anchor selectedTab;
		TabClickHandler(int index,Anchor selectedTab){
			this.index=index;
			this.selectedTab=selectedTab;
		}
		@Override
		public void onClick(ClickEvent event) {
			resetHilightStyles();
			selectedTab.setStyleName(ACTIVE);
			getUiHandlers().setTabItems(index, currentTypeView,folderObj);
		}
	}
	@Override
	public void resetHilightStyles(){
		lnkInfo.removeStyleName(ACTIVE);
		lnkContent.removeStyleName(ACTIVE);
		lnkshare.removeStyleName(ACTIVE);
	}
	@Override
	public void setInSlot(Object slot, Widget content) {
		pnlSlotInnerContent.clear();
		if(content!=null){
		  if(slot==MyCollectionsRightClusterPresenter.INNER_SLOT){
				pnlSlotInnerContent.add(content);
			}
		}
	}
	@Override	
	public void setBreadCrumbSlot(FolderDo folderObj, String type, HashMap<String, String> selectedWidgetsTitleType){
		this.folderObj=folderObj;
		
		/*if(selectedWidgetsTitleType!=null && selectedWidgetsTitleType.containsKey(COURSE)){
		this.folderObj=folderObj;
		if(selectedWidgetsTitleType!=null && selectedWidgetsTitleType.containsKey(COURSE)){
			if(selectedWidgetsTitleType.containsKey(COURSE)){
				setBreadCrumbs(selectedWidgetsTitleType.get(COURSE), COURSE);
			}
			if(selectedWidgetsTitleType.containsKey(UNIT)){
				setBreadCrumbs(selectedWidgetsTitleType.get(UNIT), UNIT);
			}
			if(selectedWidgetsTitleType.containsKey(LESSON)){
				setBreadCrumbs(selectedWidgetsTitleType.get(LESSON), LESSON);
			}
			if(selectedWidgetsTitleType.containsKey(COLLECTION)){
				setBreadCrumbs(selectedWidgetsTitleType.get(COLLECTION), COLLECTION);
			}
			if(selectedWidgetsTitleType.containsKey(ASSESSMENT)){
				setBreadCrumbs(selectedWidgetsTitleType.get(ASSESSMENT), ASSESSMENT);
			}
			if(selectedWidgetsTitleType.containsKey(ASSESSMENT_URL)){
				setBreadCrumbs(selectedWidgetsTitleType.get(ASSESSMENT_URL), ASSESSMENT_URL);
			}
		}else{
			String title=folderObj!=null?folderObj.getTitle():"";
			setBreadCrumbs(title,type);
		}*/
	}
	
	
	/**
	 * Used to set the Bread crumbs.
	 * @param title
	 * @param type
	 */
	public void setBreadCrumbs(String title, String type) {
		getUiHandlers().setViewTitleWthicon(title,type);
	}
	
	/**
	 * gets the current bread crumbs item and updates the title.
	 * @param title {@link String}
	 * @param type {@link String}
	 * @param index {@link int}
	 */
	public void getBreadCrumbs(String title,String type,int index){
		Iterator<Widget> breadCrumbswidgets = pnlBreadCrumbMain.iterator();
		while(breadCrumbswidgets.hasNext()){
			Widget widget = breadCrumbswidgets.next();
			if(widget instanceof BreadcrumbItem && ((BreadcrumbItem) widget).getType().equalsIgnoreCase(type)){ 
				BreadcrumbItem breadCrumbItem=(BreadcrumbItem)widget;
				breadCrumbItem.getLabel().setText(title);
				removeBreadCrumbs(index);
			}
		}
	}
	
	/**
	 * Removes the the bread crumbs.
	 * @param index
	 */
	private void removeBreadCrumbs(int index) {
		int widgetCount=pnlBreadCrumbMain.getWidgetCount();
		for(int i=index;i<widgetCount;){
			BreadcrumbItem breadCrumbItem=(BreadcrumbItem)pnlBreadCrumbMain.getWidget(i);
			breadCrumbItem.removeFromParent();
			widgetCount=pnlBreadCrumbMain.getWidgetCount();
		}
	}

	
	@Override
	public void setDefaultActiveTab(int tabIndex){
		resetHilightStyles();
		if(tabIndex==2){
			lnkContent.addStyleName(ACTIVE);
		}else if(tabIndex==3){
			lnkshare.addStyleName(ACTIVE);
		}else{
			lnkInfo.addStyleName(ACTIVE);
		}
		
	}
	/**
	 * This inner class is used to generate breadcum item widget
	 */
	class BreadcrumbItem extends Composite {
		 Label lblTitle;
		 private String type;
		 public BreadcrumbItem(String title,String type,String imageStyle) {
			 this.type = type;
			 HTMLPanel panel=new HTMLPanel("");
			 panel.setStyleName("active");
			 InlineLabel spnIcon=new InlineLabel();
			 spnIcon.setStyleName(imageStyle);
			 lblTitle=new Label(title);
			 lblTitle.setStyleName("breadCrumbTitle"); 
			 panel.add(spnIcon);
			 panel.add(lblTitle);
			 initWidget(panel);
		 }
		 public Label getLabel(){
			 return  lblTitle;
		 }
		 
		 public String getType(){
			 return  type;
		 }
	}
	
	public void setRequestParams(){
		o1=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
		o2=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
		o3=AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL,null);
		if(oldO1Value==null){
			oldO1Value=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
		}
		if(oldO2Value==null){
			oldO2Value=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
		}
		if(oldO3Value==null){
			oldO3Value=AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL,null);
		}
	}
	@Override
	public void setCurrentTypeView(String currentTypeView) {
		this.currentTypeView =currentTypeView;
		enableAndHideTabs(true);
		enableOrHidePreviewBtn();
		enableOrHideShareTab();
	}
	
	
	/**
	 * To enable and disable the share tab based on type.
	 */
	private void enableOrHideShareTab() {
		if(UNIT.equalsIgnoreCase(currentTypeView)|| LESSON.equalsIgnoreCase(currentTypeView) || FOLDER.equalsIgnoreCase(currentTypeView) || ASSESSMENT_URL.equalsIgnoreCase(currentTypeView)){
			lnkshare.setVisible(false);
		}else{
			lnkshare.setVisible(true);
		}
	}
	/**
	 * Hiding preview button when type is course/unit/lesson/folder
	 */
	private void enableOrHidePreviewBtn() {
		if(currentTypeView!=null){
			/*if(COLLECTION.equalsIgnoreCase(currentTypeView) || ASSESSMENT.equalsIgnoreCase(currentTypeView)){
				disableCollabaratorOptions(isCollaborator);
			}else{
				disableCollabaratorOptions(true);
			}*/
			if(COLLECTION.equalsIgnoreCase(currentTypeView)|| currentTypeView.contains(ASSESSMENT)){
				lnkPreview.setVisible(true);
				toggleButton.setVisible(true);
				deletePnl.setVisible(false);
				copyLbl.setVisible(true);
				disableCollabaratorOptions(isCollaborator);
//				moveLbl.setVisible(true);
			}else{
				lnkPreview.setVisible(false);
				toggleButton.setVisible(true);
				boolean isVisible=(FOLDER.equalsIgnoreCase(currentTypeView))?false:true;
				copyLbl.setVisible(isVisible);
				moveLbl.setVisible(false);
				myCollDelLbl.setVisible(true);
				deletePnl.setVisible(false);
			}
		}else{
			lnkPreview.setVisible(false);
			
		}
	}
	@Override
	public void enableAndHideTabs(boolean isVisible){
		lnkContent.setVisible(isVisible);
		if(COURSE.equalsIgnoreCase(currentTypeView) || COLLECTION.equalsIgnoreCase(currentTypeView)|| currentTypeView.contains(ASSESSMENT)){
			lnkshare.setVisible(isVisible);
		}
		toggleButton.setVisible(isVisible);
		if(COLLECTION.equalsIgnoreCase(currentTypeView) || currentTypeView.contains(ASSESSMENT)){
			lnkPreview.setVisible(isVisible);
		}
	}
	/**
	 * 
	 * This inner class is used to delete the user content like C/U/L and Collection.
	 *
	 */
	private class DeleteContent implements ClickHandler{
		DeleteContent(){
		}
		@Override
		public void onClick(ClickEvent event) {
			initiateDelete();
		}
	}
	
	private class DeleteContentData implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			/*copyLbl.getElement().removeClassName("selected");
			moveLbl.getElement().removeClassName("selected");
			myCollDelLbl.getElement().addClassName("selected");*/
			initiateDelete();
			
		}
		
	}
	
	
	/**
	 * This inner class is used to Open the respective collection/Assessment player
	 *  when click on preview.
	 *
	 */
	private class PreviewClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			String placeToken,folderId;
			if(AppClientFactory.getPlaceManager().getRequestParameter("o3")!=null){
				folderId=AppClientFactory.getPlaceManager().getRequestParameter("o3");
			}else if(AppClientFactory.getPlaceManager().getRequestParameter("o2")!=null){
				folderId=AppClientFactory.getPlaceManager().getRequestParameter("o2");
			}else if(AppClientFactory.getPlaceManager().getRequestParameter("o1")!=null){
				folderId=AppClientFactory.getPlaceManager().getRequestParameter("o1");
			}else{	
				folderId="";
			}
			String type = folderObj==null?null:StringUtil.isEmpty(folderObj.getType())?null:folderObj.getType();
			HashMap<String,String> params = new HashMap<String,String>();
			params.put("id", folderObj.getGooruOid());
			if(!folderId.isEmpty()){
				params.put("folderId", folderId);
			}
			if(type!=null){
				placeToken=COLLECTION.equalsIgnoreCase(type)?PlaceTokens.COLLECTION_PLAY:ASSESSMENT.equalsIgnoreCase(type)?PlaceTokens.ASSESSMENT_PLAY:"";
				if(!placeToken.isEmpty()){
					PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(placeToken, params);
					AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
				}else{
					if(!folderObj.getUrl().isEmpty() && !folderObj.getUrl().equalsIgnoreCase(PASTEURL)){
						Window.open(folderObj.getUrl(), "", "");
					}
				}
			}
		}
	}
	/**
	 * Invokes the delete course popup.
	 * 
	 * @param currentTypeView {@link String}
	 * @param o1CourseId {@link String}
	 * @param o2UnitId {@link String}
	 * @param o3LessonId {@link String}
	 * @param assessmentCollectionId 
	 * @param deletePopup {@link DeletePopupViewVc}
	 */
	public void invokeDeletePopup(final String currentTypeView,final String o1CourseId,final String o2UnitId,final String o3LessonId, final String assessmentCollectionId) {
		
		deleteContentPopup = new DeleteContentPopup() {
			
			@Override
			public void onClickPositiveButton(ClickEvent event) {
				if(!StringUtil.isEmpty(o2UnitId) && UNIT.equalsIgnoreCase(currentTypeView)){
					getUiHandlers().deleteUnitContent(o1CourseId,o2UnitId);
				}else if(!StringUtil.isEmpty(o1CourseId) && COURSE.equalsIgnoreCase(currentTypeView)){
					getUiHandlers().deleteCourseContent(o1CourseId);
				}else if(!StringUtil.isEmpty(o3LessonId) && LESSON.equalsIgnoreCase(currentTypeView)){ 
					getUiHandlers().deleteLessonContent(o1CourseId,o2UnitId,o3LessonId);
				}else if("Folder".equalsIgnoreCase(AppClientFactory.getPlaceManager().getRequestParameter("view",null))){ 
					if((AppClientFactory.getPlaceManager().getRequestParameter("id",null)!=null)){
						getUiHandlers().deleteMyCollectionContent((AppClientFactory.getPlaceManager().getRequestParameter("id",null)),"folderCollection");
					}else{
						if(AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL)!=null){
							String parentId = AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL);
							getUiHandlers().deleteMyCollectionContent(parentId,LESSON);
						} else if(AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL)!=null){
							String parentId = AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL);
							getUiHandlers().deleteMyCollectionContent(parentId,UNIT);
						} else {
							String parentId = AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL);
							getUiHandlers().deleteMyCollectionContent(parentId,COURSE);
						}
					}
				}else{
					getUiHandlers().deleteCollectionContent(o1CourseId,o2UnitId,o3LessonId,assessmentCollectionId);
				}
			}
			
			@Override
			public void onClickNegitiveButton(ClickEvent event) {
				hide();
				Window.enableScrolling(true);
			}
		};
		deleteContentPopup.getElement().getStyle().setZIndex(9999999);
		deleteContentPopup.setPopupTitle(i18n.GL0748());
		String title=folderObj.getTitle().trim();
		if(title.length()>50){
			title=title.substring(0, 50)+"...";
		}
		deleteContentPopup.setNotes(StringUtil.generateMessage(i18n.GL3456(),title));
		deleteContentPopup.setDeleteValidate("delete");
		deleteContentPopup.setPositiveButtonText("Delete Forever");
		deleteContentPopup.setNegitiveButtonText(i18n.GL0142());
		deleteContentPopup.setPleaseWaitText(i18n.GL0339());
		deleteContentPopup.show();
		deleteContentPopup.center();
	}
	
	/**
	 * This method defines functionality after deleting the course.
	 */
	@Override
	public void onDeleteCourseSuccess(String o1CourseId) {
		hideDeletePopup();
		Map<String, String> params= new HashMap<String, String>();
		if("Folder".equalsIgnoreCase(AppClientFactory.getPlaceManager().getRequestParameter("view", null))){
			params.put("view", "Folder");
			getUiHandlers().setRightClusterContent(o1CourseId,COURSE);
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
		}else{
			params.put("view", COURSE);
			getUiHandlers().setRightClusterContent(o1CourseId,currentTypeView);
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
		}
		
	}
	

	/**
	 * On deleting the Unit, reveals the my content and loads the respective right cluster.
	 */
	
	@Override
	public void onDeleteUnitSuccess(String o1CourseId, String o2DeletedUnitId) {
		hideDeletePopup();
		Map<String, String> params= new HashMap<String, String>();
		if("Folder".equalsIgnoreCase(AppClientFactory.getPlaceManager().getRequestParameter("view", null))){
			params.put("view", "Folder");
			params.put(O1_LEVEL, AppClientFactory.getPlaceManager().getRequestParameter("o1",null)); 
			getUiHandlers().setUnitsListOnRightCluster(o1CourseId,o2DeletedUnitId,UNIT);
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
			
		}else{
			params.put("view", COURSE);
			params.put(O1_LEVEL, o1CourseId);
			getUiHandlers().setUnitsListOnRightCluster(o1CourseId,o2DeletedUnitId,currentTypeView);
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
		}
	}
	
	private void hideDeletePopup() {
		if(deleteContentPopup!=null){
			deleteContentPopup.hide();
		}
		if(deletePopup !=null && deletePopup.isShowing()){
			deletePopup.hide();
		}
		Window.enableScrolling(true);
	}
	private class onCopyClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			if(!(COURSE.equalsIgnoreCase(currentTypeView))){
				getUiHandlers().disableCopyPopupTabs((LESSON.equalsIgnoreCase(currentTypeView)||UNIT.equalsIgnoreCase(currentTypeView))?false:true,currentTypeView);
				getUiHandlers().EnableMyCollectionsTreeData(folderObj.getGooruOid(),folderObj.getTitle());
				isCopySelected= true;
				isMoveSelected=false;
				getUiHandlers().checkCopyOrMoveStatus(isCopySelected,isMoveSelected,folderObj.getType());
				getUiHandlers().enableAddButton();
			}else if((COURSE.equalsIgnoreCase(currentTypeView))){
				getUiHandlers().copyCourse(folderObj.getGooruOid());
			}
		}
	}
	private class onMoveClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			isCopySelected= false;
			isMoveSelected= true;
			getUiHandlers().enableAddButton();
			getUiHandlers().checkCopyOrMoveStatus(isCopySelected,isMoveSelected,folderObj.getType());
			String NameTokenValue= AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
			if(NameTokenValue.equalsIgnoreCase(PlaceTokens.MYCONTENT)){
				String viewParamVal= AppClientFactory.getPlaceManager().getRequestParameter("view",null);
				
				if(viewParamVal!=null && viewParamVal.equalsIgnoreCase("folder")){
					getUiHandlers().EnableMyCollectionsTreeData(folderObj.getGooruOid(),folderObj.getTitle());
				}else{
					getUiHandlers().DisableMyCollectionsTreeData(folderObj.getGooruOid(),folderObj.getTitle());
				}
			}
		}

	}
		
	@Override
	public void setOnDeleteBreadCrumbs(String title, String type) {
		setBreadCrumbs(title,type);
	}
	
	/**
	 * On deleting the lesson, reveals the my content and loads the respective right cluster.
	 */
	@Override
	public void onDeleteLessonSuccess(String o1CourseId, String o2UnitId,String o3LessDeletedonId) {
		hideDeletePopup();
		Map<String, String> params= new HashMap<String, String>();
		if("Folder".equalsIgnoreCase(AppClientFactory.getPlaceManager().getRequestParameter("view", null))){
			params.put("view", COURSE);
			params.put(O1_LEVEL, AppClientFactory.getPlaceManager().getRequestParameter("o1",null));
			params.put(O2_LEVEL, AppClientFactory.getPlaceManager().getRequestParameter("o2",null));
			getUiHandlers().setLessonsListOnRightCluster(o1CourseId,o2UnitId,o3LessDeletedonId,LESSON);
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
		}else{
			params.put("view", COURSE);
			params.put(O1_LEVEL, o1CourseId);
			params.put(O2_LEVEL, o2UnitId);
			getUiHandlers().setLessonsListOnRightCluster(o1CourseId,o2UnitId,o3LessDeletedonId,currentTypeView);
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
		}
	}
	

	/**
	 * On deleting the collection/assessment, reveals the my content and loads the respective right cluster.
	 */
	@Override
	public void onDeleteCollectionAssessmentSuccess(String o1CourseId,String o2UnitId, String o3LessonId, String deletedAssessmentCollectionId) {
		hideDeletePopup();
		if("Folder".equalsIgnoreCase(AppClientFactory.getPlaceManager().getRequestParameter("view", null))){
			Map<String, String> params = new HashMap<String, String>();
			if(AppClientFactory.getPlaceManager().getRequestParameter("o3")!=null){
				params.put("view", "Folder");
				params.put("o1",AppClientFactory.getPlaceManager().getRequestParameter("o1"));  
				params.put("o2",AppClientFactory.getPlaceManager().getRequestParameter("o2"));
				params.put("o3",AppClientFactory.getPlaceManager().getRequestParameter("o3"));
				getUiHandlers().setCollectionsListOnRightCluster(o1CourseId,o2UnitId,o3LessonId,deletedAssessmentCollectionId,COLLECTION);
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
			}else if(AppClientFactory.getPlaceManager().getRequestParameter("o2")!=null){
				params.put("view", "Folder");
				params.put("o1",AppClientFactory.getPlaceManager().getRequestParameter("o1"));  
				params.put("o2",AppClientFactory.getPlaceManager().getRequestParameter("o2"));
				getUiHandlers().setLessonsListOnRightCluster(o1CourseId,o2UnitId,deletedAssessmentCollectionId,LESSON);
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
			}else if(AppClientFactory.getPlaceManager().getRequestParameter("o1")!=null){
				params.put("view", "Folder");
				params.put("o1",AppClientFactory.getPlaceManager().getRequestParameter("o1")); 
				getUiHandlers().setUnitsListOnRightCluster(o1CourseId,deletedAssessmentCollectionId,UNIT);
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
			}else{
				params.put("view", "Folder");
				getUiHandlers().setRightClusterContent(o1CourseId,COURSE);
//				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
			}
		}else{
			Map<String, String> params= new HashMap<String, String>();
			params.put("view", COURSE);
			params.put(O1_LEVEL, o1CourseId);
			params.put(O2_LEVEL, o2UnitId);
			params.put(O3_LEVEL, o3LessonId);
			getUiHandlers().setCollectionsListOnRightCluster(o1CourseId,o2UnitId,o3LessonId,deletedAssessmentCollectionId,currentTypeView);
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
		}
	}
	
	/**
	 * Invokes the delete functionality if the course is not associated with class.
	 */
	@Override
	public void invokeContentDeletePopup(String o1CourseId, String o2UnitId,String o3LessonId,String assessmentCollectionId,ArrayList<ClasspageDo> classesList) {
		this.classesList = classesList;
		if(classesList.size()>0){
			invokeMyCollDeletePopUp(currentTypeView,o1CourseId, o2UnitId, o3LessonId,assessmentCollectionId,true);
		}else{
			invokeMyCollDeletePopUp(currentTypeView,o1CourseId, o2UnitId, o3LessonId,assessmentCollectionId,false);
		}
	}
	/**
	 * This method is used to hide the bread cums panel
	 */
	@Override
	public void disableAndEnableBreadCums(boolean isVisible){
		pnlBreadCrumbMain.setVisible(isVisible);
	}
	@Override
	public void setFolderInfoWidget(FolderDo folderObj,MyCollectionsRightClusterPresenter rightPresenter) {
		FolderInfoWidget folderInfoWidget = new FolderInfoWidget();
		pnlSlotInnerContent.add(folderInfoWidget);
		folderInfoWidget.setData(folderObj,rightPresenter);
	}
	
	public void initiateDelete() {
		String o1CourseId = AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
		String o2UnitId = AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
		String o3LessonId = AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL,null);
		String assessmentCollectionId = AppClientFactory.getPlaceManager().getRequestParameter("id",null);
		String view = AppClientFactory.getPlaceManager().getRequestParameter("view",null);
		if(currentTypeView.equalsIgnoreCase(COLLECTION) || currentTypeView.contains(ASSESSMENT)){ 
			if(FOLDER.equalsIgnoreCase(view)){
				invokeMyCollDeletePopUp(currentTypeView,o1CourseId, o2UnitId, o3LessonId,assessmentCollectionId,false);
			}else{
				getUiHandlers().isAssignedToClassPage(currentTypeView,o1CourseId,o2UnitId,o3LessonId,assessmentCollectionId);
			}
		}else{
			if(COURSE.equalsIgnoreCase(currentTypeView)){
				getUiHandlers().isStudentDataAvailable(currentTypeView,o1CourseId,o2UnitId, o3LessonId,assessmentCollectionId);
			}else{
				invokeDeletePopup(currentTypeView,o1CourseId, o2UnitId, o3LessonId,assessmentCollectionId);
			}
		}
	}
	

	/**
	 * This method invokes for Collection/assessment delete at my collections/ my content.
	 * @param currentTypeView
	 * @param o1CourseId
	 * @param o2UnitId
	 * @param o3LessonId
	 * @param assessmentCollectionId
	 * @param isTiedWithClasses
	 */
	private void invokeMyCollDeletePopUp(String currentTypeView, final String o1CourseId, final String o2UnitId, final String o3LessonId, final String assessmentCollectionId, boolean isTiedWithClasses) {
		deletePopup = new DeletePopupViewVc() {

			@Override
			public void onClickPositiveButton(ClickEvent event) {
				if("Folder".equalsIgnoreCase(AppClientFactory.getPlaceManager().getRequestParameter("view",null))){
					getUiHandlers().deleteMyCollectionColl((AppClientFactory.getPlaceManager().getRequestParameter("id",null)));
				}else{
					getUiHandlers().deleteCollectionContent(o1CourseId,o2UnitId,o3LessonId,assessmentCollectionId);
				}
			}
			
			@Override
			public void onClickNegitiveButton(ClickEvent event) {
				hide();
				Window.enableScrolling(true);
			}
		};
		
		deletePopup.setPopupTitle(i18n.GL0748());
		if(isTiedWithClasses && (currentTypeView.equalsIgnoreCase(COLLECTION) || currentTypeView.equalsIgnoreCase(ASSESSMENT))){
			StringBuffer sb = new StringBuffer();
			String anchString = "<a href=\"{0}\" target=\"_blank\">{1}</a>";
			String classpageUrl = "#newteach&c-id={0}&report-type=course-view&classpageId={1}&subpage-view=reports&page-view=teach-dashboard";
			int count = classesList.size(); 
			for (int i=0; i<count;i++){
				String url = StringUtil.generateMessage(classpageUrl,o1CourseId, classesList.get(i).getClassUid());
				if (count==1){
					sb.append(StringUtil.generateMessage(anchString, url,classesList.get(i).getName()));
				}else{
					if (i == (count-1)){
						sb.append(i18n.GL_GRR_AND()+" "+StringUtil.generateMessage(anchString, url,classesList.get(i).getName()));
					}else{
						sb.append(StringUtil.generateMessage(anchString, url,classesList.get(i).getName()) + ", ");
					}
				}
			}
			
			String remaining = count==1?(" "+i18n.GL1155()):(" "+i18n.GL1154()+i18n.GL_SPL_EXCLAMATION());
			if(currentTypeView.contains(ASSESSMENT)){
				deletePopup.setNotes("This assessment is currently being used in your"+" "+sb.toString()+" "+remaining);
				deletePopup.setDescText(i18n.GL3039());
			}else{
				deletePopup.setNotes(i18n.GL1156()+" "+sb.toString()+" "+remaining);
				deletePopup.setDescText(i18n.GL1238());
			}
			
		}else{
			if(currentTypeView.contains(ASSESSMENT)){
				deletePopup.setNotes(StringUtil.generateMessage(i18n.GL3038(), folderObj.getTitle().trim()));
				deletePopup.setDescText(i18n.GL3039());
			}else{
				deletePopup.setNotes(StringUtil.generateMessage(i18n.GL1020(), folderObj.getTitle().trim()));
				deletePopup.setDescText(i18n.GL1238());
			}
		}
		deletePopup.setDeleteValidate("delete");
		deletePopup.setPositiveButtonText(i18n.GL0190());
		deletePopup.setNegitiveButtonText(i18n.GL0142());
		deletePopup.setPleaseWaitText(i18n.GL0339());
		deletePopup.show();
		deletePopup.center();
	}
	
	
	public void hideDropDown(NativePreviewEvent event){
    	if(event.getTypeInt()==Event.ONCLICK){
    		Event nativeEvent = Event.as(event.getNativeEvent());
        	boolean target=eventTargetsPopup(nativeEvent);
        	if(!target){
        		copyPopupPanel.setVisible(false);
        	}
    	}
     }
	private boolean eventTargetsPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			return copyPopupPanel.getElement().isOrHasChild(Element.as(target))||copyPopupPanel.getElement().isOrHasChild(Element.as(target));
		}
		return false;
	}
	

	public void removeActiveStyle() {
		copyLbl.getElement().removeClassName("selected");
		moveLbl.getElement().removeClassName("selected");
		myCollDelLbl.getElement().removeClassName("selected");
	}
	
	@Override
	public void disableCollabaratorOptions(boolean hide){
		hide = ASSESSMENT_URL.equalsIgnoreCase(currentTypeView)?true:hide;
		moveLbl.setVisible(hide);
		myCollDelLbl.setVisible(hide);
	}
	@Override
	public void setIsCollaboratorValue(boolean isHide) {
		this.isCollaborator=isHide;
	}
	public void disableButtons(boolean isTrue){
		toggleButton.setVisible(isTrue);
		lnkPreview.setVisible(isTrue);
	}
	
	
	@Override
	public void isCourseDeleteStatus(Boolean status, String type, String o1CourseId, String o2UnitId, String o3LessonId, String assessmentCollectionId) {
		if(!status){
			invokeDeletePopup(type,o1CourseId, o2UnitId, o3LessonId,assessmentCollectionId);
		}else{
			new AlertContentUc(i18n.GL0061(),"Sorry this Course is tied with student data, you cannot delete the course");
		}
	}
}
