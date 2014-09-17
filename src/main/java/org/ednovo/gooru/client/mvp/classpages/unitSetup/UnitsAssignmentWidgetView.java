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

package org.ednovo.gooru.client.mvp.classpages.unitSetup;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.classpages.unitdetails.UnitAssigmentReorder;
import org.ednovo.gooru.client.mvp.home.WaitPopupVc;
import org.ednovo.gooru.client.uc.HTMLEventPanel;
import org.ednovo.gooru.shared.i18n.MessageProperties;
import org.ednovo.gooru.shared.model.content.ClassDo;
import org.ednovo.gooru.shared.model.content.ClassUnitsListDo;
import org.ednovo.gooru.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.shared.model.content.UnitAssignmentsDo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class UnitsAssignmentWidgetView extends Composite {
	
	private static UnitsAssignmentWidgetViewUiBinder uibinder = GWT.create(UnitsAssignmentWidgetViewUiBinder.class);
	interface UnitsAssignmentWidgetViewUiBinder extends UiBinder<Widget, UnitsAssignmentWidgetView>{
		
	}
	
	
	@UiField HTMLPanel assignmentsContainer;
	
	@UiField Button editUnitButton,addAssignmentButton,cancelEditButton;
	
	@UiField Label lblUnitName,lblUnitNumber;
	
	@UiField HTMLEventPanel htPanelNextArrow,htPanelPreviousArrow;
	@UiField Anchor unitDetailsButton;
	
	private ClassUnitsListDo classUnitsDo;
	
	private ClassDo classDo;
	
	private UnitAssignmentsDo unitAssignmentsDo;
	
	private UnitAssigmentReorder unitAssigmentReorder;
	
	private int assignmentOffset=0;
	private int assignmentLimit=10;
	
	boolean isDeleted=false;
	private boolean isEditMode=false;
	private boolean isReorderPopupShowing = false;
	private boolean isStudentMode=false;
	
	private static final String NEXT="next";
	private static final String PREVIOUS= "previous";
	private int totalHitCount=0;
	
	private String pathwayId;
	

	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	public UnitsAssignmentWidgetView(ClassUnitsListDo classUnitsDo){
		initWidget(uibinder.createAndBindUi(this));
		this.classUnitsDo=classUnitsDo;
		addAssignmentButton.getElement().getStyle().setMarginTop(28, Unit.PX);
		setAssignmentsForUnit();
		setUnitNameDetails();
		cancelEditButton.setVisible(false);
		editUnitButton.addClickHandler(new EditAssignmentEvent());
		cancelEditButton.addClickHandler(new CancelEditEvent());
		unitDetailsButton.addClickHandler(new UnitChangeEvent(classUnitsDo.getResource().getGooruOid(),PlaceTokens.EDIT_CLASSPAGE));
	}
	
	public UnitsAssignmentWidgetView(ClassUnitsListDo classUnitsDo, boolean studentMode){
		initWidget(uibinder.createAndBindUi(this));
		editUnitButton.removeFromParent();
		addAssignmentButton.removeFromParent();
		cancelEditButton.removeFromParent();
		this.classUnitsDo=classUnitsDo;
		setAssignmentsForUnit();
		setUnitNameDetails();
		unitDetailsButton.addClickHandler(new UnitChangeEvent(classUnitsDo.getResource().getGooruOid(),PlaceTokens.STUDENT));
	}



	private void setAssignmentsForUnit() {
		assignmentsContainer.clear();
		if(classUnitsDo!=null && classUnitsDo.getResource()!=null){
			if(classUnitsDo.getResource().getCollectionItems() != null)
			{
			if(classUnitsDo.getResource().getCollectionItems().size()==0){
				htPanelNextArrow.setVisible(false);
				htPanelPreviousArrow.setVisible(false);
			}
			}
			else
			{
				htPanelNextArrow.setVisible(false);
				htPanelPreviousArrow.setVisible(false);
			}
			if(classUnitsDo.getResource().getCollectionItems() != null)
			{
			for(int i=0;i<classUnitsDo.getResource().getCollectionItems().size();i++){
				ClasspageItemDo classpageItemDo=classUnitsDo.getResource().getCollectionItems().get(i);
				showAndHidePaginationArrows();
				assignmentsContainer.add(new AssignmentsContainerWidget(classpageItemDo));
			}
			}
		}
	}
	
	private void showAndHidePaginationArrows() {
		if(classUnitsDo.getResource().getItemCount()>assignmentLimit){
			htPanelNextArrow.setVisible(true);
		}else{
			htPanelNextArrow.setVisible(false);
			htPanelPreviousArrow.setVisible(false);
		}
	}

	public void clearAssignmentsFromDo(){
		classUnitsDo.getResource().setCollectionItems(new ArrayList<ClasspageItemDo>());
	}
	
	public class EditAssignmentEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			isEditMode = true;
			hideEditButton(true);
			assignmentsContainer.clear();
			setAssignmentsEditView(); 
		}
	}
	
	
	public class DeleteAssignment implements ClickHandler{
		String collectionItemId=null;
		
		public DeleteAssignment(String collectionItemId) {
			this.collectionItemId = collectionItemId;
		}

		@Override
		public void onClick(ClickEvent event) {
			final WaitPopupVc popupVc = new WaitPopupVc(i18n.GL1387(),i18n.GL1388()) { 
				@Override
				public void onTextConfirmed() {
					AppClientFactory.getInjector().getClasspageService().deleteClassPageItem(collectionItemId, new SimpleAsyncCallback<String>() {
						@Override
						public void onSuccess(String result) {
							if(result.equals("200")){
								boolean isAssignmentDeleted = deleteAssignmentWidget(collectionItemId);
								if(isAssignmentDeleted){
									clearAssignmentsFromDo();
									hide();
									if((getTotalHitCount()-1)==assignmentOffset){
										assignmentOffset=assignmentOffset-10;
									}
									getUnitAssignments(assignmentOffset,isEditMode);
								}
							}
						}
					});
				}
			};
		}

	}
	
	
	
	public void setAssignmentsEditView() {
		assignmentsContainer.clear();
		for(int i=0;i<classUnitsDo.getResource().getCollectionItems().size();i++){
			AssignmentEditView assignmentEditView = new AssignmentEditView(classUnitsDo);
			assignmentEditView.getDeleteAssignmentLbl().addClickHandler(new DeleteAssignment(classUnitsDo.getResource().getCollectionItems().get(i).getCollectionItemId()));
			if(classUnitsDo.getResource().getCollectionItems().size()>0){ 
				assignmentEditView.getAssignmentReorderLbl().addMouseOverHandler(new ReorderAssignment(classUnitsDo.getResource().getCollectionItems().get(i).getResource().getTitle(),classUnitsDo.getResource().getCollectionItems().get(i).getNarration(),classUnitsDo.getResource().getCollectionItems().get(i).getCollectionItemId()));
			}
			assignmentEditView.setAssignmentId(classUnitsDo.getResource().getCollectionItems().get(i).getCollectionItemId());
			assignmentsContainer.add(assignmentEditView);
		}
		
		Event.addNativePreviewHandler(new NativePreviewHandler() {
	        public void onPreviewNativeEvent(NativePreviewEvent event) {
	        	hideReorderPopup(event);
	          }
	    });
	}

	public class ReorderAssignment implements MouseOverHandler{

		String collectionItem,title,narration;
		
		
		public ReorderAssignment(String title,String narration,String collectionItem){
			this.title = title;
			this.collectionItem = collectionItem;
			this.narration = narration;
		}

		

		@Override
		public void onMouseOver(MouseOverEvent event) {
			String classPageId = AppClientFactory.getPlaceManager().getRequestParameter("classpageid", null);
			unitAssigmentReorder = new UnitAssigmentReorder(getClassDo(),title, "",classPageId){

				@Override
				public void reorderAssignment(int seqPosition,String selectedPathId) {
					boolean isAssignmentDeleted = deleteAssignmentWidget(collectionItem);
					if(isAssignmentDeleted){
						clearAssignmentsFromDo();
						assignmentOffset =(seqPosition/assignmentLimit)*assignmentLimit;
						if(assignmentOffset==seqPosition){
							assignmentOffset = assignmentOffset-assignmentLimit;
						}
						getUnitAssignments(assignmentOffset,isEditMode);
					}
					
				}
				
			};
			unitAssigmentReorder.setPopupPosition(event.getRelativeElement().getAbsoluteLeft()-148,event.getRelativeElement().getAbsoluteTop()+36);
			unitAssigmentReorder.show();
			isReorderPopupShowing = true;
		}
	}
	
	public void hideReorderPopup(NativePreviewEvent event){
		try{
			if(event.getTypeInt()==Event.ONMOUSEOVER){
				Event nativeEvent = Event.as(event.getNativeEvent());
				boolean target=eventTargetsPopup(nativeEvent);
				if(!target)
				{
					if(isReorderPopupShowing){
						unitAssigmentReorder.hide();
					}
				}
			}
		}catch(Exception ex){ex.printStackTrace();}
	}

	private boolean eventTargetsPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			try{
				return unitAssigmentReorder.getElement().isOrHasChild(Element.as(target));
			}catch(Exception ex){}
		}
		return false;
	}
	
	
	public class CancelEditEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			isEditMode = false;
			hideEditButton(false);
			setAssignmentsForUnit();
		}
	}
	
	public class UnitChangeEvent implements ClickHandler{
		private String unitGooruOid;
		private String viewToken;
		public UnitChangeEvent(String unitGooruOid,String viewToken){
			this.unitGooruOid=unitGooruOid;
			this.viewToken=viewToken;
		}
		@Override
		public void onClick(ClickEvent event) {
			revealPlace("unitdetails",null,unitGooruOid,viewToken);
		}
	}
	
	 public void revealPlace(String tabName,String pageNum,String unitId,String viewToken){
			Map<String,String> params = new HashMap<String,String>();
			String classpageid= "";
			if(viewToken.equals(PlaceTokens.STUDENT))
			{
				classpageid=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
				params.put("id", classpageid);
			}
			else
			{
			classpageid=AppClientFactory.getPlaceManager().getRequestParameter("classpageid", null);
			params.put("classpageid", classpageid);
			}
			
			if(pageNum!=null){
				params.put("pageNum", pageNum);
			}
			if(tabName!=null){
				params.put("tab", tabName);
			}
			if(unitId!=null){
				params.put("uid", unitId);
			}
			PlaceRequest placeRequest= null;
			placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(viewToken, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	 }
	
	public void hideEditButton(boolean hide){
		if(hide){
			editUnitButton.getElement().setAttribute("style", "display:none !important");
		}else{
			editUnitButton.getElement().removeAttribute("style");
		}
		addAssignmentButton.setVisible(!hide);
		cancelEditButton.setVisible(hide);
	}
	
	public boolean deleteAssignmentWidget(String collectionItemId) { 
		Iterator<Widget> assignmentContainerWidget = assignmentsContainer.iterator();
		while(assignmentContainerWidget.hasNext()){
			Widget widget = assignmentContainerWidget.next();
			if(widget instanceof AssignmentEditView){
				if(((AssignmentEditView) widget).getAssignmentId().equals(collectionItemId)){
					widget.removeFromParent();
					isDeleted = true;
				}
			}
		}
		return isDeleted;
	}

	private void setUnitNameDetails() {
			int number=classUnitsDo.getItemSequence();
			String sequenceNumber=Integer.toString(number);
			lblUnitName.setText(classUnitsDo.getResource().getTitle());
			lblUnitNumber.setText(sequenceNumber);
	}
	
	@UiHandler("htPanelNextArrow")
	public void clickOnNextArrow(ClickEvent clickEvent){
		clearAssignmentsFromDo();
		getUnitAssignments(getAssignmentOffsetValue(NEXT),isEditMode);
	}
	
	
	private int getAssignmentOffsetValue(String direction) {
		if(direction.equals(NEXT)){
			assignmentOffset = assignmentOffset+assignmentLimit;
		}else{
			assignmentOffset = Math.abs(assignmentOffset-assignmentLimit);
		}
		return assignmentOffset;
	}

	@UiHandler("htPanelPreviousArrow")
	public void clickOnPreviousArrow(ClickEvent clickEvent){
		clearAssignmentsFromDo();
		getUnitAssignments(getAssignmentOffsetValue(PREVIOUS),isEditMode);
	}
	
	public void getUnitAssignments(int assignmentOffset,final boolean isAssignmentEditmode){
		String classPageId= AppClientFactory.getPlaceManager().getRequestParameter("classpageid", null);
		AppClientFactory.getInjector().getClasspageService().v2GetPathwayItems(classPageId, classUnitsDo.getResource().getGooruOid(), "sequence", assignmentLimit, assignmentOffset, new SimpleAsyncCallback<UnitAssignmentsDo>() {

			@Override
			public void onSuccess(UnitAssignmentsDo result) {
				setTotalHitCount(result.getTotalHitCount());
				classUnitsDo.getResource().setCollectionItems(result.getSearchResults());
				if(isAssignmentEditmode){
					setAssignmentsEditView();
					classUnitsDo.getResource().setItemCount(getTotalHitCount());
				}else{
					setAssignmentsForUnit();
				}
				showAndHideAssignmentArrows(result);
			}
		}); 
	}
	
	
	public void addAssignment(ArrayList<ClasspageItemDo> classpageItemDo){
		setTotalHitCount(getTotalHitCount()+classpageItemDo.size());
		getUnitAssignments(getOffsetValue(),false);
	}
	
	private int getOffsetValue() {
		int pageNum = getTotalHitCount()/10;
		if(getTotalHitCount()>(pageNum*10)){
			assignmentOffset = (pageNum*10);
		}else if(getTotalHitCount()==(pageNum*10)){
			assignmentOffset = ((pageNum-1)*10);
		}
		return assignmentOffset;
	}

	/**
	 * @return the pathwayId
	 */
	public String getPathwayId() {
		return pathwayId;
	}



	/**
	 * @param pathwayId the pathwayId to set
	 */
	public void setPathwayId(String pathwayId) {
		this.pathwayId = pathwayId;
	}
	
	
	/**
	 * @return the addAssignmentButton
	 */
	public Button getAddAssignmentButton() {
		return addAssignmentButton;
	}

	
	private void showAndHideAssignmentArrows(UnitAssignmentsDo unitAssignmentsDo) {
		int totalAssignments=unitAssignmentsDo.getTotalHitCount();
		if(Math.abs(totalAssignments-assignmentOffset)>assignmentLimit){
			if(Math.abs(totalAssignments-assignmentOffset)==totalAssignments){
				htPanelPreviousArrow.setVisible(false);
				htPanelNextArrow.setVisible(true);
			}else{
				htPanelPreviousArrow.setVisible(true);
				htPanelNextArrow.setVisible(true);
			}
			
		}else{
			htPanelNextArrow.setVisible(false);
			htPanelPreviousArrow.setVisible(true);
		}
	}
	
	
	/**
	 * @return the totalHitCount
	 */
	public int getTotalHitCount() {
		return totalHitCount;
	}

	/**
	 * @param totalHitCount the totalHitCount to set
	 */
	public void setTotalHitCount(int totalHitCount) {
		this.totalHitCount = totalHitCount;
	}
	
	
	/**
	 * @return the classDo
	 */
	public ClassDo getClassDo() {
		return classDo;
	}

	/**
	 * @param classDo the classDo to set
	 */
	public void setClassDo(ClassDo classDo) {
		this.classDo = classDo;
	}

}
