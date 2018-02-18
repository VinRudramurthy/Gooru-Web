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
package org.ednovo.gooru.client.mvp.gshelf.courselist;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.mvp.gshelf.ShelfMainPresenter;
import org.ednovo.gooru.client.mvp.gshelf.util.ContentWidgetWithMove;
import org.ednovo.gooru.client.uc.H2Panel;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MyCollectionsListView  extends BaseViewWithHandlers<MyCollectionsListUiHandlers> implements IsMyCollectionsListView,ClientConstants  {

	private static MyCollectionsListViewUiBinder uiBinder = GWT.create(MyCollectionsListViewUiBinder.class);

	interface MyCollectionsListViewUiBinder extends UiBinder<Widget, MyCollectionsListView> {
	}
	private static MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField HTMLPanel emptyContainerDiv,pnlMainButtonsContainer,listScrollPanel,courseListContainer,pnlH2TitleContainer,pnlCreateContainer,pnlAddContainer,pnlCreate,collectionLevelPnl;
	@UiField VerticalPanel pnlCourseList;
	@UiField H2Panel h2Title;
	@UiField Button btnCreate,btnCreateResource,btnCreateQuestion,createCollectionBtn,createAssessmentBtn;
	@UiField Label lblAddNew,lblTitle,lblLimitReached;
	@UiField Anchor lblAddNewForResource,lblAddNewForQuestion;
	@UiField HTMLEventPanel createPanel;
	
	int index=0;
	
	String type;
	
	final String COURSE="COURSE",UNIT="Unit",LESSON="Lesson",FOLDER="Folder",COLLECTION="Collection",ASSESSMENT="Assessment",ASSESSMENT_URL="Assessment/url";
	
	private static final String VIEW= "view";

	private static final String O1_LEVEL = "o1";
	
	private static final String O2_LEVEL = "o2";
	
	private static final String O3_LEVEL = "o3";
	
	private static final String COURSE_LIMIT_MSG = i18n.GL3567();
	private static final String UNIT_LIMIT_MSG = i18n.GL3489();
	private static final String LESSON_LIMIT_MSG = i18n.GL3490();
	private static final String COLLECTION_LIMIT_MSG = i18n.GL3491();
	
	private static final String ID = "id";

	private static  final String LOADER_IMAGE = "images/core/B-Dot.gif";   
	
	private PopupPanel toolTipPopupPanel=new PopupPanel(true);
	
	public MyCollectionsListView() {
		setWidget(uiBinder.createAndBindUi(this));
		setIds();
		collectionLevelPnl.getElement().getStyle().setFloat(Float.RIGHT);

		
	}
	/**
	 * This method is used to set id's
	 */
	public void setIds(){
		courseListContainer.getElement().setId("gShelfCousesList");
		h2Title.getElement().setId("h2Title");
		pnlCourseList.getElement().setId("pnlCourseList");
		pnlH2TitleContainer.getElement().setId("pnlH2TitleContainer");
		StringUtil.setAttributes(btnCreate.getElement(),"btnCreate", "", "");
		StringUtil.setAttributes(btnCreateQuestion.getElement(),"btnCreateQuestion", "", "");
		StringUtil.setAttributes(btnCreateResource.getElement(),"btnCreateResource", "", "");
		createCollectionBtn.setText(i18n.GL_SPL_PLUS()+ " " +i18n.GL1451());
		createAssessmentBtn.setText(i18n.GL_SPL_PLUS()+ " " +i18n.GL3024());
		courseListContainer.getElement().setAttribute("style","min-height:"+Window.getClientHeight()+"px");
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				courseListContainer.getElement().setAttribute("style","min-height:"+Window.getClientHeight()+"px");
			}
		});
	}
	/**
	 * This method is used to reset the widget positions with default text
	 */
	@Override
	public void resetWidgetPositions(){
		Iterator<Widget> widgets=pnlCourseList.iterator();
		int index=0;
		while (widgets.hasNext()){
			Widget widget=widgets.next();
			if(widget instanceof ContentWidgetWithMove){
				ContentWidgetWithMove contentWidgetWithMove=(ContentWidgetWithMove) widget;
				contentWidgetWithMove.getIndexLabel().setText((index+1)+"");
				contentWidgetWithMove.getTextBox().setText((index+1)+"");
				contentWidgetWithMove.getTextBox().getElement().setAttribute("index",index+"");
				if(index==0){
					//If this is the first widget we are hiding the up arrow
					contentWidgetWithMove.getTopArrow().setVisible(false);
					contentWidgetWithMove.getDownArrow().setVisible(true);
				}else if(index==(pnlCourseList.getWidgetCount()-1)){
					//If this the last widget hiding the down arrow
					contentWidgetWithMove.getTopArrow().setVisible(true);
					contentWidgetWithMove.getDownArrow().setVisible(false);
				}else{
					contentWidgetWithMove.getTopArrow().setVisible(true);
					contentWidgetWithMove.getDownArrow().setVisible(true);
				}
				index++;
			}
		}
	}
	/**
	 * This method is used to set data for fields
	 */
	@Override
	public void setData(String type,List<FolderDo> listOfContent,boolean clrPanel,boolean isInnerSlot,FolderDo folderDo) {
		this.type=type;
		pnlH2TitleContainer.setVisible(true);
		pnlCreateContainer.setVisible(false);
		lblLimitReached.setVisible(false);
		lblTitle.setVisible(false);
		collectionLevelPnl.setVisible(false);
		if(listOfContent ==null || listOfContent.size()==0){
			emptyContainerDiv.setVisible(true);
		}else{
			emptyContainerDiv.setVisible(false);
		}
		if(isInnerSlot){
			pnlH2TitleContainer.setVisible(false);
			pnlCreateContainer.setVisible(true);
			//lblTitle.setVisible(true);
			//lblTitle.setText(StringUtil.capitalizeFirstLetter(folderDo.getType())+": "+folderDo.getTitle());
			String view=AppClientFactory.getPlaceManager().getRequestParameter(VIEW);
			if(view!=null && (view.equalsIgnoreCase(FOLDER) || view.equalsIgnoreCase(COLLECTION))){
				btnCreate.setVisible(false);
				pnlAddContainer.setVisible(false);
			}else{
				btnCreate.setVisible(true);
				lblAddNew.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
				pnlAddContainer.setVisible(true);
			}
			setCreateText(type);
		}else{
			if(COURSE.equalsIgnoreCase(type)){
				enableCreateButtons(false);
				pnlAddContainer.setVisible(true);
				h2Title.setText(i18n.GL1180());
				lblAddNew.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
				lblAddNew.setText(i18n.GL0326());
			}else if(FOLDER.equalsIgnoreCase(type)){
				enableCreateButtons(true);
				collectionLevelPnl.setVisible(true);
				btnCreateResource.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL1451());
				btnCreateQuestion.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3024());
				btnCreate.setVisible(false);
				pnlAddContainer.setVisible(false);
				if(folderDo!=null){
					h2Title.setText(folderDo.getTitle());
				}else{
					h2Title.setText(i18n.GL0180());
				}
			}else if(COLLECTION.equalsIgnoreCase(type)){
				btnCreate.setVisible(false);
				pnlAddContainer.setVisible(false);
				if(folderDo!=null){
					h2Title.setText(folderDo.getTitle()+" "+i18n.GL3336());
				}else{
					h2Title.setText(i18n.GL3282());
				}
			}else{
				pnlH2TitleContainer.setVisible(false);
				pnlCreateContainer.setVisible(true);
			}
		}
		if(clrPanel){
			index=0;
			pnlCourseList.clear();
		}else{
			index=pnlCourseList.getWidgetCount();
			setLastWidgetArrowVisiblity(true);
		}
		//setCreateText();
		if(listOfContent!=null && listOfContent.size()>0){
			for (final FolderDo folderObj : listOfContent) {
				final ContentWidgetWithMove widgetMove=new ContentWidgetWithMove(index,type,folderObj) {
					@Override
					public void moveWidgetPosition(String movingPosition,String currentWidgetPosition, boolean isDownArrow,String moveId,String moveGooruOId,String moveParentGooruOid) {
						int movingIndex= Integer.parseInt(movingPosition);
						if(pnlCourseList.getWidgetCount()>=movingIndex){
							//Based on the position it will insert the widget in the vertical panel
							String itemSequence=pnlCourseList.getWidget(movingIndex-1).getElement().getAttribute("itemSequence");
							String tempGooruOid=moveGooruOId;
							String o1 = AppClientFactory.getPlaceManager().getRequestParameter("o1",null);
							String o2 = AppClientFactory.getPlaceManager().getRequestParameter("o2",null);
							String o3 = AppClientFactory.getPlaceManager().getRequestParameter("o3",null);
							String view = AppClientFactory.getPlaceManager().getRequestParameter("view",null);
							if((o1!=null && o2!=null && o3!=null) || "Folder".equalsIgnoreCase(view)){
								moveGooruOId=moveParentGooruOid;
							}
							getUiHandlers().reorderWidgetPositions(moveId, Integer.parseInt(itemSequence),movingIndex,moveGooruOId);
							if(!isDownArrow){
								movingIndex= (movingIndex-1);
								int currentIndex= Integer.parseInt(currentWidgetPosition);
								resetWidgetItemSequencePositions(movingIndex,itemSequence,true,currentWidgetPosition);
								pnlCourseList.getWidget(currentIndex).getElement().setAttribute("itemSequence",itemSequence);
								pnlCourseList.insert(pnlCourseList.getWidget(currentIndex), movingIndex);
								resetWidgetPositions();
								getUiHandlers().getShelfMainPresenter().getView().reorderShelfItems(tempGooruOid,movingIndex, "MoveUp", updatePrams(), folderObj,currentWidgetPosition);
							}else{
								int currentIndex= Integer.parseInt(currentWidgetPosition);
								resetWidgetItemSequencePositions(movingIndex,itemSequence,false,currentWidgetPosition);
								pnlCourseList.getWidget(currentIndex).getElement().setAttribute("itemSequence",itemSequence);
								pnlCourseList.insert(pnlCourseList.getWidget(currentIndex), movingIndex);
								resetWidgetPositions();
								getUiHandlers().getShelfMainPresenter().getView().reorderShelfItems(tempGooruOid,movingIndex, "MoveDown", updatePrams(), folderObj,currentWidgetPosition);
							}
						}else{
							int index=Integer.parseInt(currentWidgetPosition);
							Element widget=(Element) pnlCourseList.getWidget(index).getElement().getLastChild();
							toolTipPopupPanel.clear();
							toolTipPopupPanel.setWidget(new GlobalToolTip(StringUtil.generateMessage(i18n.GL3004(),movingIndex+"")));
							toolTipPopupPanel.setStyleName("");
							toolTipPopupPanel.setPopupPosition(widget.getAbsoluteLeft(), widget.getAbsoluteTop()+40);
							toolTipPopupPanel.getElement().getStyle().setZIndex(9999);
							toolTipPopupPanel.show();
							new FadeInAndOut(toolTipPopupPanel.getElement(), 10200);
						}
					}
					@Override
					public void checkKeyUpHandler(int position,ContentWidgetWithMove contentWidget) {
						if(pnlCourseList.getWidgetCount()<position){
							contentWidget.getTopArrow().setVisible(false);
							contentWidget.getDownArrow().setVisible(false);
							toolTipPopupPanel.clear();
							toolTipPopupPanel.setWidget(new GlobalToolTip(StringUtil.generateMessage(i18n.GL3004(),position+"")));
							toolTipPopupPanel.setStyleName("");
							toolTipPopupPanel.setPopupPosition(contentWidget.getTextBox().getAbsoluteLeft(), contentWidget.getTextBox().getAbsoluteTop()+40);
							toolTipPopupPanel.getElement().getStyle().setZIndex(9999);
							toolTipPopupPanel.show();
							new FadeInAndOut(toolTipPopupPanel.getElement(), 10200);
						}
					}
					@Override
					public void checkBlurHandler(int position,ContentWidgetWithMove contentWidget) {
						String currentWidgetString=contentWidget.getTextBox().getElement().getAttribute("index").trim();
						int currentIndex=Integer.valueOf(currentWidgetString);
						int enterdVal=Integer.valueOf(contentWidget.getTextBox().getText());
						if(enterdVal>pnlCourseList.getWidgetCount() && currentIndex!=0){
							contentWidget.getTopArrow().setVisible(true);
							contentWidget.getDownArrow().setVisible(true);
							contentWidget.getTextBox().setText((currentIndex+1)+"");
						}
						setLastWidgetArrowVisiblity(false);
					}
				};
				widgetMove.getElement().setAttribute("itemSequence", folderObj.getItemSequence()+"");
				widgetMove.getTitleContainer().addClickHandler(new ClickOnTitleContainer(folderObj,true));
				widgetMove.enableAndDisableCount(folderObj.getType());
				pnlCourseList.add(widgetMove);
				index++;
			}
			setLastWidgetArrowVisiblity(false);
		}
		//Showing Validation Msg when list limit reached
		if(folderDo!=null){
			if(pnlCourseList.getWidgetCount()>=20){
				createPanel.setVisible(false);
				pnlCreate.setVisible(false);
				lblLimitReached.setVisible(true);
				btnCreate.setEnabled(false);
				btnCreate.getElement().addClassName("disabled");
				if(type.equalsIgnoreCase(COURSE)){
					lblLimitReached.setText(UNIT_LIMIT_MSG);
				}else if(type.equalsIgnoreCase(UNIT)){
					lblLimitReached.setText(LESSON_LIMIT_MSG);
				}
			}
			if(type.equalsIgnoreCase(LESSON) && pnlCourseList.getWidgetCount()>=10  ){
				createPanel.setVisible(false);
				pnlCreate.setVisible(false);
				lblLimitReached.setVisible(true);
				btnCreateQuestion.setEnabled(false);
				btnCreateResource.setEnabled(false);
				btnCreateQuestion.getElement().addClassName("disabled");
				btnCreateResource.getElement().addClassName("disabled");
				lblLimitReached.setText(COLLECTION_LIMIT_MSG);
			}
		}else{
			if(type.equalsIgnoreCase(COURSE)&& pnlCourseList.getWidgetCount()>=50){
				createPanel.setVisible(false);
				pnlCreate.setVisible(false);
				lblLimitReached.setVisible(true);
				lblLimitReached.setText(COURSE_LIMIT_MSG);
			}
		}
		
		pnlMainButtonsContainer.setVisible(true);
	}
	public HashMap<String,String> updatePrams(){
		String id = AppClientFactory.getPlaceManager().getRequestParameter("id",null);
		String o1 = AppClientFactory.getPlaceManager().getRequestParameter("o1",null);
		String o2 = AppClientFactory.getPlaceManager().getRequestParameter("o2",null);
		String o3 = AppClientFactory.getPlaceManager().getRequestParameter("o3",null);
		HashMap<String,String> params = new HashMap<String,String>();
		if(o3!=null&&id==null) {
			params.put("o3",o3);
			params.put("o2",o2);
			params.put("o1",o1);
		} else if(o2!=null&&id==null) {
			params.put("o2",o2);
			params.put("o1",o1);
		} else if(o1!=null&&id==null) {
			params.put("o1",o1);
		}
		return params;
	}
	public void resetWidgetItemSequencePositions(int selectedIndex,String itemSequence,boolean isdown,String currentWidgetPosition){
		if(isdown){
			for (int i = selectedIndex; i < Integer.parseInt(currentWidgetPosition); i++){
				String sequence=pnlCourseList.getWidget(i).getElement().getAttribute("itemSequence");
				int sequenceVal=Integer.valueOf(sequence)+1;
				pnlCourseList.getWidget(i).getElement().setAttribute("itemSequence",sequenceVal+"");
			}
		}else{
			for (int i=(selectedIndex-1);i>Integer.parseInt(currentWidgetPosition);i--){
				String sequence=pnlCourseList.getWidget(i).getElement().getAttribute("itemSequence");
				int sequenceVal=Integer.valueOf(sequence)-1;
				pnlCourseList.getWidget(i).getElement().setAttribute("itemSequence",sequenceVal+"");
			}
		}
	}
	public void enableCreateButtons(boolean isEnabled){
		btnCreateResource.setVisible(isEnabled);
		btnCreateQuestion.setVisible(isEnabled);
		pnlCreate.setVisible(isEnabled);
		createPanel.setVisible(!isEnabled);
	}
	@UiHandler("createPanel")
	public void clickOnCreatePanel(ClickEvent clickEvent){
		getUiHandlers().addNewContent(createPanel.getElement().getInnerText());
	}
	
	@UiHandler("lblAddNewForResource")
	public void clickOnResource(ClickEvent clickEvent){
		getUiHandlers().addNewContent(lblAddNewForResource.getText());
	}
	
	@UiHandler("lblAddNewForQuestion")
	public void clickOnQuestion(ClickEvent clickEvent){
		getUiHandlers().addNewContent(lblAddNewForQuestion.getText());
	}
	@UiHandler("btnCreate")
	public void clickOnCreate(ClickEvent clickEvent){
		getUiHandlers().addNewContent(btnCreate.getText());
	}
	@UiHandler("btnCreateQuestion")
	public void clickOnQues(ClickEvent clickEvent){
		getUiHandlers().addNewContent(btnCreateQuestion.getText());
	}
	@UiHandler("btnCreateResource")
	public void clickOnRes(ClickEvent clickEvent){
		getUiHandlers().addNewContent(btnCreateResource.getText());
	}
	@UiHandler("createCollectionBtn")
	public void clickOnCollection(ClickEvent clickEvent){
		getUiHandlers().addNewContent(createCollectionBtn.getText());
	}
	@UiHandler("createAssessmentBtn")
	public void clickOnAssessment(ClickEvent clickEvent){
		getUiHandlers().addNewContent(createAssessmentBtn.getText());
	}
	/**
	 * This method is used to set the create text
	 * @param typeVal
	 */
	public void setCreateText(String type){
		String courseId = AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
		btnCreate.setEnabled(true);
		btnCreateQuestion.setEnabled(true);
		btnCreateResource.setEnabled(true);
		
		btnCreate.getElement().removeClassName("disabled");
		btnCreateQuestion.getElement().removeClassName("disabled");
		btnCreateResource.getElement().removeClassName("disabled");
		
		if(COURSE.equalsIgnoreCase(type) && courseId!=null){
			enableCreateButtons(false);
			btnCreate.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3370());
			lblAddNew.setText(i18n.GL3281());
		}else if(UNIT.equalsIgnoreCase(type)){
			enableCreateButtons(false);
			btnCreate.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3371());
			lblAddNew.setText(i18n.GL0910());
		}else if(LESSON.equalsIgnoreCase(type)){
			enableCreateButtons(true);
			btnCreateResource.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL1451());
			btnCreateQuestion.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3024());
			lblAddNewForResource.setText(i18n.GL3483());
			lblAddNewForQuestion.setText(i18n.GL3372());
			
			StringUtil.setAttributes(btnCreateResource.getElement(), i18n.GL1451(), i18n.GL1451());
			StringUtil.setAttributes(btnCreateQuestion.getElement(), i18n.GL3024(), i18n.GL3024());
			StringUtil.setAttributes(lblAddNewForResource.getElement(), i18n.GL2001(), i18n.GL3483());
			StringUtil.setAttributes(lblAddNewForQuestion.getElement(), i18n.GL3418(), i18n.GL3372());
			
			btnCreate.setVisible(false);
			//lblAddNew.setVisible(false);
			//createPanel.setVisible(false);
		}else if(FOLDER.equalsIgnoreCase(type)){
			enableCreateButtons(true);
			String o1=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
			String o2=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
			String o3=AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL,null);
			btnCreateResource.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL1451());
			btnCreateQuestion.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3024());
			btnCreate.setVisible(false);
			lblAddNewForResource.setText(i18n.GL1451());
			lblAddNewForQuestion.setText(i18n.GL3024());
			//Showing buttons for folder creation 
			/*if(o3!=null){
				btnCreateResource.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL1451());
				btnCreateQuestion.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3024());
				btnCreate.setVisible(false);
				lblAddNew.setVisible(false);
				lblAddNewForResource.setText(i18n.GL1451());
				lblAddNewForQuestion.setText(i18n.GL3024());
			}else if(o2!=null || o1!=null){
				btnCreateResource.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL1451());
				btnCreateQuestion.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3024());
				btnCreate.setVisible(false);
				lblAddNew.setVisible(false);
				//btnCreate.setText(i18n.GL3024());
				lblAddNewForResource.setText(i18n.GL1451());
				lblAddNewForQuestion.setText(i18n.GL3024());
			}*/
			/*btnCreateResource.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL1450());
			btnCreateQuestion.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL1451());
			lblAddNewForResource.setText(i18n.GL1450());
			lblAddNewForQuestion.setText(i18n.GL1451());*/
			
			StringUtil.setAttributes(btnCreateResource.getElement(), i18n.GL1450(), i18n.GL1450());
			StringUtil.setAttributes(btnCreateQuestion.getElement(), i18n.GL1451(), i18n.GL1451());
			StringUtil.setAttributes(lblAddNewForResource.getElement(), i18n.GL1450(), i18n.GL1450());
			StringUtil.setAttributes(lblAddNewForQuestion.getElement(), i18n.GL1451(), i18n.GL1451());
			
			
		}else{
			enableCreateButtons(true);
			btnCreateResource.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL1110());
			btnCreateQuestion.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL0308());
			lblAddNewForResource.setText(i18n.GL2000());
			lblAddNewForQuestion.setText(i18n.GL3218());
			
			StringUtil.setAttributes(btnCreateResource.getElement(), i18n.GL1110(), i18n.GL1110());
			StringUtil.setAttributes(btnCreateQuestion.getElement(), i18n.GL0308(), i18n.GL0308());
			StringUtil.setAttributes(lblAddNewForResource.getElement(), i18n.GL2000(), i18n.GL2000());
			StringUtil.setAttributes(lblAddNewForQuestion.getElement(), i18n.GL3218(), i18n.GL3218());
			
			btnCreate.setVisible(false);
		}
		
	}
	/**
	 * This inner class is used to handle the click event on title container
	 */
	class ClickOnTitleContainer implements ClickHandler{
		FolderDo folderObj;
		boolean isClicked;
		ClickOnTitleContainer(FolderDo folderObj,boolean isClicked){
			this.folderObj=folderObj;
			this.isClicked=isClicked;
		}
		@Override
		public void onClick(ClickEvent event) {
			if(isClicked){
				isClicked=false;
				Map<String,String> params = new HashMap<String,String>();
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, updateParameters(params,folderObj));
				getUiHandlers().getShelfMainPresenter().updateLeftShelfPanelActiveStyle();
			}
		}
	}
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (content != null) {
			 if(slot==ShelfMainPresenter.RIGHT_SLOT){
				 getUiHandlers().getShelfMainPresenter().getView().getSlot().clear();
				 getUiHandlers().getShelfMainPresenter().getView().getSlot().add(content);
			 }
		}
	}
	/**
	 * On pagination it will enable the previous widget down arrow for move functionality
	 * @param isVisible
	 */
	public void setLastWidgetArrowVisiblity(boolean isVisible){
		if(pnlCourseList!=null && pnlCourseList.getWidgetCount()>0){
			ContentWidgetWithMove lastwidget=(ContentWidgetWithMove) pnlCourseList.getWidget(pnlCourseList.getWidgetCount()-1);
			lastwidget.getDownArrow().setVisible(isVisible);
		}
	}
	/**
	 * This method is used to update the url parameters
	 * @param params
	 * @param folderObj
	 * @return
	 */
	public Map<String,String> updateParameters(Map<String,String> params,FolderDo folderObj){
		String view=AppClientFactory.getPlaceManager().getRequestParameter(VIEW,null);
		String o1=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
		String o2=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
		String o3=AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL,null);
		String id=AppClientFactory.getPlaceManager().getRequestParameter(ID,null);
		if(view!=null){
			params.put(VIEW,view);
		}else{
			params.put(VIEW,"Course");
		}
		if(o1==null && o2==null && o3==null && id==null && !COLLECTION.equalsIgnoreCase(folderObj.getType())){
			params.put(O1_LEVEL,folderObj.getGooruOid());
		} else if(o1!=null && o2==null && o3==null && id==null && !COLLECTION.equalsIgnoreCase(folderObj.getType())){
			params.put(O1_LEVEL, o1);
			params.put(O2_LEVEL,folderObj.getGooruOid());
		}else if(o1!=null && o2!=null && o3==null && id==null && !COLLECTION.equalsIgnoreCase(folderObj.getType())) {
			params.put(O1_LEVEL,o1);
			params.put(O2_LEVEL,o2);
			params.put(O3_LEVEL,folderObj.getGooruOid());
		}else if(COLLECTION.equalsIgnoreCase(folderObj.getType()) || ASSESSMENT.equalsIgnoreCase(folderObj.getType()) || ASSESSMENT_URL.equalsIgnoreCase(folderObj.getType())){
			params.put(O1_LEVEL,o1);
			params.put(O2_LEVEL,o2);
			params.put(O3_LEVEL,o3);
			params.put(ID,folderObj.getGooruOid());
		}
		return params;
	}
	/**
	 * This is used for adding loader image
	 * @return
	 */
	@Override
	public void loadingImage(){
		pnlMainButtonsContainer.setVisible(false);
		pnlCourseList.clear();
		lblTitle.setText("");
		Image loadingImage =  new Image();
		loadingImage.setUrl(LOADER_IMAGE);
		loadingImage.getElement().setId("myCollectionsListViewLoaddingImage");
		pnlCourseList.add(loadingImage);
	}
	@Override
	public VerticalPanel getPanelCourseContainer(){
		return pnlCourseList;
	}
}
