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
package org.ednovo.gooru.client.mvp.gshelf.collectioncontent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.mvp.gshelf.courselist.EmptyAssessmentView;
import org.ednovo.gooru.client.mvp.gshelf.courselist.EmptyCollectionView;
import org.ednovo.gooru.client.mvp.gshelf.util.ContentResourceWidgetWithMove;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.EditQuestionPopupVc;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.EditResourcePopupVc;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.EditUserOwnResourcePopupVc;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.UpdateQuestionImageView;
import org.ednovo.gooru.client.mvp.shelf.event.GetEditPageHeightEvent;
import org.ednovo.gooru.client.mvp.shelf.event.InsertCollectionItemInAddResourceEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.uc.ConfirmationPopupVc;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.ui.TinyMCE;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CollectionContentView extends BaseViewWithHandlers<CollectionContentUiHandlers> implements IsCollectionContentView  {

	private static CollectionContentViewUiBinder uiBinder = GWT
			.create(CollectionContentViewUiBinder.class);

	interface CollectionContentViewUiBinder extends
			UiBinder<Widget, CollectionContentView> {
	}

	@UiField HTMLPanel pnlContentContainer,emptyContainerdiv,bottomPanel;
	@UiField VerticalPanel pnlReosurceList;
	@UiField Button btnAddResources, btnAddQuestions;
	@UiField Anchor ancAddResource, ancAddQuestion;
	@UiField InlineLabel lblSpanOr;
	@UiField Label lblTitle,lblLimitReached;
	@UiField TinyMCE testTextArea;

	CollectionContentPresenter collectionContentPresenter;
	
	CollectionDo listOfContent=null;

	private EditQuestionPopupWidget editQuestionPopupWidget;

	private EditResourcePopupVc editResoruce;

	private EditUserOwnResourcePopupVc ownResourcePopupVc;

	ConfirmationPopupVc deleteConfirmationPopupVc = null;

	private HandlerRegistration handlerRegistration=null;
	
	private static  final String LOADER_IMAGE = "images/core/B-Dot.gif";   
	
	private PopupPanel toolTipPopupPanel=new PopupPanel(true);

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	int index=0;
	String type;
	private String clickType;
	String title,description,category,thumbnailUrl;
	
	String lastEditedBy;
	public boolean hasLastModifiedUser=false;
	
	FolderDo folderDo;

	Map<String, ContentResourceWidgetWithMove> moveWidgets=new HashMap<String, ContentResourceWidgetWithMove>();

	public CollectionContentView() {
		setWidget(uiBinder.createAndBindUi(this));
		pnlContentContainer.getElement().setId("resourceEdit");

		StringUtil.setAttributes(btnAddResources.getElement(), "btnAddResources", i18n.GL_SPL_PLUS() +" "+ i18n.GL0174(), i18n.GL_SPL_PLUS() +" "+ i18n.GL0174());
		StringUtil.setAttributes(btnAddQuestions.getElement(), "btnAddQuestions", i18n.GL_SPL_PLUS() +" "+ i18n.GL1042(), i18n.GL_SPL_PLUS() +" "+ i18n.GL1042());

		lblSpanOr.setText(i18n.GL0209().toLowerCase());

		//Adding Click Handlers.
		btnAddResources.addClickHandler(new NewResourceClickEvent());
		btnAddQuestions.addClickHandler(new NewQuestionClickEvent());

		ancAddResource.addClickHandler(new NewResourceClickEvent());
		ancAddQuestion.addClickHandler(new NewQuestionClickEvent());
		testTextArea.setVisible(false);
	}

	@Override
	public void setData(CollectionDo listOfContent,FolderDo folderDo, RefreshType type){
		this.listOfContent = listOfContent;
		this.folderDo = folderDo;
		emptyContainerdiv.clear();
		bottomPanel.setVisible(true);
		btnAddResources.setEnabled(true);
		btnAddQuestions.setEnabled(true);
		lblLimitReached.setVisible(false);
		btnAddResources.getElement().removeClassName("disabled");
		btnAddQuestions.getElement().removeClassName("disabled");
		if(listOfContent!=null)
		{
		if(listOfContent.getUser()!=null){
		if (AppClientFactory.isContentAdmin() || listOfContent
				.getUser().getGooruUId().equals(AppClientFactory.getLoggedInUser()
						.getGooruUId())){
			getUiHandlers().disableCollabaratorOptions(true);
		}else if(listOfContent.isIsCollaborator()){
			getUiHandlers().disableCollabaratorOptions(false);
		}
		}
		}
		if (listOfContent!=null && listOfContent.getLastModifiedUser() != null){
			String lastModifiedDate = listOfContent.getLastModified().toString() != null ? getTimeStamp(listOfContent.getLastModified().getTime()+"") : "";
			String lastModifiedUser = listOfContent.getLastModifiedUser().getUsername() != null ?  listOfContent.getLastModifiedUser().getUsername() : "";
			lastEditedBy = StringUtil.generateMessage(i18n.GL1112(), lastModifiedDate, lastModifiedUser);
			hasLastModifiedUser = lastModifiedUser!=null && !lastModifiedUser.equalsIgnoreCase("") ? true : false;
		}else{
			lastEditedBy="";
			hasLastModifiedUser=false;
		}
		getUiHandlers().showLastEditCollaborater(lastEditedBy,hasLastModifiedUser);

		lblTitle.setVisible(false);
		if(folderDo.getType().equalsIgnoreCase("assessment") || folderDo.getType().equalsIgnoreCase("assessment/url")){
			btnAddResources.setVisible(false);		
			lblSpanOr.setVisible(false);
			ancAddResource.setVisible(false);
			lblTitle.setText(i18n.GL3007()+": "+folderDo.getTitle());
		}else{
			btnAddResources.setVisible(true);
			lblSpanOr.setVisible(true);
			ancAddResource.setVisible(true);
			lblTitle.setText(i18n.GL0645()+": "+folderDo.getTitle());
		}
		if(listOfContent.getCollectionItems()!=null && listOfContent.getCollectionItems().size()>0){
			index=0;
			pnlReosurceList.clear();
			for (CollectionItemDo collectionItem : listOfContent.getCollectionItems()) {
				setDisplayResourceItem(collectionItem, type, index);
				index++;
			}
			if(index>=25){
				bottomPanel.setVisible(false);
				btnAddResources.setEnabled(false);
				btnAddQuestions.setEnabled(false);
				lblLimitReached.setVisible(true);
				btnAddResources.getElement().addClassName("disabled");
				btnAddQuestions.getElement().addClassName("disabled");
				lblLimitReached.setText("Resource limit reached.");
			}
			setLastWidgetArrowVisiblity(false);
		}else{
			pnlReosurceList.clear();
			if(folderDo!=null && folderDo.getType()!=null){
				if(folderDo.getType().equalsIgnoreCase("collection")){
					EmptyCollectionView emptyColl = new EmptyCollectionView();
					emptyColl.getAddResourceBtn().addClickHandler(new NewResourceClickEvent());
					emptyColl.getAddQuestionBtn().addClickHandler(new NewQuestionClickEvent());
					emptyContainerdiv.add(emptyColl);
				}else if(folderDo.getType().equalsIgnoreCase("assessment")){
					EmptyAssessmentView emptyAssessment = new EmptyAssessmentView();
					emptyAssessment.getAddQuestionBtn().addClickHandler(new NewQuestionClickEvent());
					emptyContainerdiv.add(emptyAssessment);
				}
			}else{
				emptyContainerdiv.clear();
			}
			Window.enableScrolling(true);
		}
		if(pnlReosurceList.getWidgetCount()>0){
			emptyContainerdiv.setVisible(false);
		}else{
			emptyContainerdiv.setVisible(true);
		}
	}

	@Override
	public void setDisplayResourceItem(CollectionItemDo collectionItem,RefreshType type, int index){
		int tmpIndex = index;
		Window.enableScrolling(true);
		if (tmpIndex ==-1){
			index = pnlReosurceList.getWidgetCount()>0 ? pnlReosurceList.getWidgetCount() : 0;
			listOfContent.getCollectionItems().add(collectionItem);
		}
		if (type.equals(RefreshType.INSERT)){
			final ContentResourceWidgetWithMove widgetMove=new ContentResourceWidgetWithMove(index,collectionItem,folderDo.getType()) {
				@Override
				public void moveWidgetPosition(String movingPosition,String currentWidgetPosition, boolean isDownArrow, String moveId,String moveGooruOid) {
					int movingIndex= Integer.parseInt(movingPosition);
					if(pnlReosurceList.getWidgetCount()>=movingIndex){
						//Based on the position it will insert the widget in the vertical panel
						String itemSequence=pnlReosurceList.getWidget(movingIndex-1).getElement().getAttribute("itemSequence");
						getUiHandlers().reorderWidgetPositions(moveId, Integer.parseInt(itemSequence),moveGooruOid);
						if(!isDownArrow){
							movingIndex= (movingIndex-1);
							int currentIndex= Integer.parseInt(currentWidgetPosition);
							pnlReosurceList.insert(pnlReosurceList.getWidget(currentIndex), movingIndex);
						}else{
							int currentIndex= Integer.parseInt(currentWidgetPosition);
							pnlReosurceList.insert(pnlReosurceList.getWidget(currentIndex), movingIndex);
						}
					}else{
						int index=Integer.parseInt(currentWidgetPosition);
						Element widget=(Element) pnlReosurceList.getWidget(index).getElement().getLastChild();
						toolTipPopupPanel.clear();
						toolTipPopupPanel.setWidget(new GlobalToolTip(StringUtil.generateMessage(i18n.GL3005(),movingIndex+"")));
						toolTipPopupPanel.setStyleName("");
						toolTipPopupPanel.setPopupPosition(widget.getAbsoluteLeft()+120, widget.getAbsoluteTop()+30);
						toolTipPopupPanel.getElement().getStyle().setZIndex(9999);
						toolTipPopupPanel.show();
						new FadeInAndOut(toolTipPopupPanel.getElement(), 10200);
					}
				}
				@Override
				public void checkKeyUpHandler(int position,ContentResourceWidgetWithMove contentWidget) {
					if(pnlReosurceList.getWidgetCount()<position){
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
				public void checkBlurHandler(int position,ContentResourceWidgetWithMove contentWidget) {
					String currentWidgetString=contentWidget.getTextBox().getElement().getAttribute("index").trim();
					int currentIndex=Integer.valueOf(currentWidgetString);
					int enterdVal=Integer.valueOf(contentWidget.getTextBox().getText());
					if(enterdVal>pnlReosurceList.getWidgetCount() && currentIndex!=0){
						contentWidget.getTopArrow().setVisible(true);
						contentWidget.getDownArrow().setVisible(true);
						contentWidget.getTextBox().setText((currentIndex+1)+"");
					}
					setLastWidgetArrowVisiblity(false);
				}
				@Override
				public void updateNarration(CollectionItemDo collectionItem,String narration) {
					getUiHandlers().updateNarrationItem(collectionItem, narration);
				}
				@Override
				public void editResource(final CollectionItemDo collectionItem) {
					String resourceType="";
					if(collectionItem.getResource().getResourceType().getName()!=null){
						resourceType=collectionItem.getResource().getResourceType().getName();
						if(resourceType.equalsIgnoreCase("assessment-question")){
							resourceType=collectionItem.getResource().getResourceFormat().getDisplayName();
						}
					}
					AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99,false));
					if (resourceType.equalsIgnoreCase("Question")) {
						if(collectionItem.getCollection()!=null){
							collectionItem.getCollection().setCollectionType(listOfContent.getCollectionType());
						}else{
							CollectionDo colDo=new CollectionDo();
							colDo.setCollectionType(listOfContent.getCollectionType());
							collectionItem.setCollection(colDo);
						}
						getUiHandlers().showEditQuestionResourcePopup(collectionItem);
					} else if(resourceType.equals("resource/url") || resourceType.equals("video/youtube")
							|| resourceType.equals("vimeo/video")){
						editResoruce = new EditResourcePopupVc(collectionItem) {
							@Override
							public void updateResource(CollectionItemDo collectionItemDo,List<String> tagList) {
								getUiHandlers().updateResourceInfo(collectionItemDo,tagList);
							}
							@Override
							public void resourceImageUpload() {
								getUiHandlers().imageEditResourceUpload();
							}
							@Override
							public void onSelection(SelectionEvent<Suggestion> event) {

							}
	
							@Override
							public void showStandardsPopup(String standardVal, String standardsDesc,
									List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
								getUiHandlers().showStandardsPopup(standardVal,standardsDesc,collectionLiPanelWithCloseArray);
								
							}
							@Override
							public void browseStandardsInfo(boolean val, boolean userResource) {
								// TODO Auto-generated method stub
								
							}
							@Override
							public void closeStandardsPopup() {
								// TODO Auto-generated method stub
								
							}
						};
					}else {
						MixpanelUtil.Resource_Action_Edit_Info();
						ownResourcePopupVc = new EditUserOwnResourcePopupVc(collectionItem) {
							@Override
							public void resourceImageUpload() {
								getUiHandlers().imageEditUserOwnResourceUpload();
							}

							@Override
							public void updateUserOwnResource(String resourceFilePath,String resMediaFileName,String resOriginalFileName,String titleStr, String desc,String categoryStr,String thumbnailUrlStr,CollectionItemDo collectionItemDo, List<String> tagList) {
								title=titleStr;
								description = desc;
								category = categoryStr;
								thumbnailUrl = thumbnailUrlStr;
								JSONObject jsonObject = setEditUserResourceJsonObject(resOriginalFileName,resMediaFileName, title, desc, category, thumbnailUrlStr,collectionItemDo,tagList);
								getUiHandlers().editUserOwnResource(jsonObject.toString(),collectionItemDo.getCollectionItemId(),collectionItemDo.getParentGooruOid());
							}
	
							@Override
							public void onSelection(SelectionEvent<Suggestion> event) {
								
							}

							@Override
							public void showStandardsPopup(String standardVal, String standardsDesc,
									List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
								getUiHandlers().showStandardsPopup(standardVal,standardsDesc,collectionLiPanelWithCloseArray);
								
							}
						};
					}
				}
				@Override
				public void updateVideoTime(CollectionItemDo collectionItemDo,String start,String stop) {
					getUiHandlers().updateVideoTimeUpdate(collectionItemDo);
				}
				@Override
				public void dispalyNewResourcePopup(
						CollectionItemDo collectionItemDo) {
					getUiHandlers().showResourcePopup(collectionItemDo);
				}
				@Override
				public void showStandardsPopupInTags(String standardVal, String standardsDesc,
						List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
					getUiHandlers().showStandardsPopup(standardVal, standardsDesc, collectionLiPanelWithCloseArray);
					
				}
			};
			widgetMove.setPresenter(collectionContentPresenter);
			widgetMove.getElement().setAttribute("itemSequence", collectionItem.getItemSequence()+"");
			moveWidgets.put(collectionItem.getCollectionItemId(), widgetMove);
			pnlReosurceList.insert(widgetMove, index);
			index++;
		}else{
			if(collectionItem.getItemSequence()!=null){
				pnlReosurceList.remove(collectionItem.getItemSequence() - 1);
				//listOfContent.getCollectionItems().remove(collectionItem.getItemSequence()-1);
				listOfContent.getCollectionItems().set((collectionItem.getItemSequence()-1), collectionItem);
				setDisplayResourceItem(collectionItem, RefreshType.INSERT, (collectionItem.getItemSequence()-1));
			}
		}
		if (tmpIndex ==-1){
			setLastWidgetArrowVisiblity(false);
			resetWidgetPositions();
		}
		if(pnlReosurceList.getWidgetCount()>0){
			emptyContainerdiv.setVisible(false);
		}else{
			emptyContainerdiv.setVisible(true);
		}
	}

	/**
	 * On pagination it will enable the previous widget down arrow for move functionality
	 * @param isVisible
	 */
	public void setLastWidgetArrowVisiblity(boolean isVisible){
		ContentResourceWidgetWithMove lastwidget=(ContentResourceWidgetWithMove) pnlReosurceList.getWidget(pnlReosurceList.getWidgetCount()-1);
		lastwidget.getDownArrow().setVisible(isVisible);
	}
	/**
	 * This method is used to reset the widget positions with default text.
	 */
	@Override
	public void resetWidgetPositions(){
		Iterator<Widget> widgets=pnlReosurceList.iterator();
		int index=0;
		while (widgets.hasNext()){
			Widget widget=widgets.next();
			if(widget instanceof ContentResourceWidgetWithMove){
				ContentResourceWidgetWithMove contentWidgetWithMove=(ContentResourceWidgetWithMove) widget;
				contentWidgetWithMove.getElement().setAttribute("itemSequence",Integer.toString((index+1)));
				contentWidgetWithMove.getItemSequenceLabel().setText(Integer.toString((index+1)));
				contentWidgetWithMove.getTextBox().setText((index+1)+"");
				contentWidgetWithMove.getTextBox().getElement().setAttribute("index",index+"");
				if(index==0){
					//If this is the first widget we are hiding the up arrow
					contentWidgetWithMove.getTopArrow().setVisible(false);
					contentWidgetWithMove.getDownArrow().setVisible(true);
				}else if(index==(pnlReosurceList.getWidgetCount()-1)){
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

	@Override
	public void setCollectionContentPresenter(CollectionContentPresenter collectionContentPresenter){
		this.collectionContentPresenter=collectionContentPresenter;
	}


	/*
	 * To handle click event for New Resource
	 */
	private class NewResourceClickEvent implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			MixpanelUtil.Click_On_AddResource();
			clickType = "Url";
			if(listOfContent!=null && listOfContent.getCollectionItems()!=null)
			{
			if (listOfContent.getCollectionItems().size() >= 25) {

			} else {
				MixpanelUtil.Click_Add_NewResource();
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				displayNewResourcePopup();
			}
			}
		}
	}

	/*
	 * To handle click event for New Resource
	 */
	private class NewQuestionClickEvent implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			try{
				MixpanelUtil.Click_On_AddQuestion();
				clickType = "Question";
				if (listOfContent.getCollectionItems().size() >= 25) {

				} else {
					Window.enableScrolling(false);
					AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
					displayNewResourcePopup();
				}
			}catch(Exception e){
				//AppClientFactory.printInfoLogger(e.getMessage());
			}
		}

	}

	@Override
	public void displayNewResourcePopup() {
		getUiHandlers().addResourcePopup(listOfContent, clickType);
	}

	@Override
	public void updateDeleteItem(String collectionItemId, int itemSequence) {
		//This method will delete the collectionItemId and reset the widget orders.
		if(pnlReosurceList.getWidgetCount()>0){
			emptyContainerdiv.setVisible(false);
		}else{
			emptyContainerdiv.setVisible(true);
		}
		if(listOfContent.getCollectionItems().size()>0){
			listOfContent.getCollectionItems().remove(itemSequence-1);
		}
		resetWidgetPositions();
	}

	@Override
	public void hideUpdateResourcePopup() {
		editResoruce.hide();
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
	}

	@Override
	public void updateCollectionItem(CollectionItemDo collectionItem) {
		if(collectionItem != null){
			AppClientFactory.fireEvent(new InsertCollectionItemInAddResourceEvent(collectionItem, RefreshType.UPDATE));
		}
	}

	@Override
	public void setUpdatedStandardsCode(String setStandardsVal, Integer codeId,String setStandardDesc, boolean value, boolean userResource) {
		if(value == false){
			if(userResource){
				//ownResourcePopupVc.setUpdatedBrowseStandardsVal(setStandardsVal,codeId,setStandardDesc);
			}else{
				//editResoruce.setUpdatedBrowseStandardsVal(setStandardsVal,codeId,setStandardDesc);
			}

		}else{
			editQuestionPopupWidget.setUpdatedBrowseStandardsVal(setStandardsVal,codeId,setStandardDesc);
		}
	}

	private JSONObject setEditUserResourceJsonObject(String originalFilename,String mediaFileName, String editedTitle, String editedDescription, String editedCategory,String editedThumbnailUrl, CollectionItemDo collectionItemDo, List<String> tagList) {
		JSONObject file = new JSONObject();
		 if(originalFilename!=null && mediaFileName!=null){
			 file.put("filename", new JSONString(originalFilename));
			 file.put("mediaFilename", new JSONString(mediaFileName));
		 }
		JSONObject attach = new JSONObject();
        attach.put("title", new JSONString(editedTitle));
        attach.put("description", new JSONString(editedDescription));
        JSONObject resourceFormat = new JSONObject();
        resourceFormat.put("value", new JSONString(editedCategory));
        attach.put("resourceFormat", resourceFormat);
        if(editedThumbnailUrl!=null){
        	 attach.put("thumbnail", new JSONString(editedThumbnailUrl));
        }
        if(originalFilename!=null && mediaFileName!=null){
        	 attach.put("attach", file);

        }

        

        JSONArray standardsJsonArray = new JSONArray();
        JSONArray momentsOfLearningArrValue = new JSONArray();
        JSONArray educatUseArrValue = new JSONArray();
        JSONArray tagsArrValue = new JSONArray();
        

        for(int i=0;i<collectionItemDo.getStandards().size();i++){
         	standardsJsonArray.set(i,new JSONNumber(Integer.parseInt(collectionItemDo.getStandards().get(i).get("id"))));
        }
        attach.put("standardIds", standardsJsonArray);

        for(int i=0;i<collectionItemDo.getResource().getMomentsOfLearning().size();i++){
        	JSONObject momentsOfLearningJsonObj = new JSONObject();
        	momentsOfLearningJsonObj.put("selected",JSONBoolean.getInstance(collectionItemDo.getResource().getMomentsOfLearning().get(i).isSelected()));
        	momentsOfLearningJsonObj.put("value",new JSONString(collectionItemDo.getResource().getMomentsOfLearning().get(i).getValue()));
            momentsOfLearningArrValue.set(i, momentsOfLearningJsonObj);
        }
        attach.put("momentsOfLearning", momentsOfLearningArrValue);

        for(int i=0;i<collectionItemDo.getResource().getEducationalUse().size();i++){
        	JSONObject educatUseJsonObj = new JSONObject();
        	educatUseJsonObj.put("selected",JSONBoolean.getInstance(collectionItemDo.getResource().getEducationalUse().get(i).isSelected()));
        	educatUseJsonObj.put("value", new JSONString(collectionItemDo.getResource().getEducationalUse().get(i).getValue()));
        	educatUseArrValue.set(i, educatUseJsonObj);
        }
        attach.put("educationalUse", educatUseArrValue);

        for(int i=0;i<tagList.size();i++){
        	tagsArrValue.set(i, new JSONString(tagList.get(i)));
        }
        JSONObject resource = new JSONObject();
        resource.put("resourceTags",tagsArrValue);
        resource.put("resource", attach);

		return resource;
	}

	@Override
	public void hideUpdateOwnResourcePopup() {
		ownResourcePopupVc.hide();
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
	}

	public class EditQuestionPopupWidget extends EditQuestionPopupVc {
		private String collectionItemId;
		public EditQuestionPopupWidget(CollectionItemDo collectionItemDo) {
			super(collectionItemDo);
			this.collectionItemId = collectionItemDo.getCollectionItemId();
			AppClientFactory.fireEvent(new GetEditPageHeightEvent(this, false));
		}
		public void show() {
			super.show();
			this.center();
		}
		@Override
		public void updateQuestionResource(String collectionItemId,
				CollectionQuestionItemDo collectionQuestionItemDo) {

			if (editQuestionPopupWidget.getQuestionImageContainer()
					.getElement().hasChildNodes()) {
				UpdateQuestionImageView updateQuestionImage = (UpdateQuestionImageView) editQuestionPopupWidget
						.getQuestionImageContainer().getWidget(0);
				String thumbnailUrl = updateQuestionImage
						.getThumbnailImageUrl();
				if (thumbnailUrl != null) {
					getUiHandlers().updateQuestionResource(collectionItemId,
							collectionQuestionItemDo,
							"asset-question_" + thumbnailUrl);

				} else {
					getUiHandlers().updateQuestionResource(collectionItemId,
							collectionQuestionItemDo, null);
				}

			} else {
				getUiHandlers().updateQuestionResource(collectionItemId,
						collectionQuestionItemDo, null);
			}

		}

		@Override
		public void callBrowseStandardsInfo(boolean val,boolean userResource) {

		}

		public void setUpdatedBrowseStandardsVal(String setStandardsVal,Integer codeId, String setStandardDesc) {
			super.setUpdatedBrowseStandards(setStandardsVal,codeId,setStandardDesc);

		}

		@Override
		public void closeBrowseStandardsPopup() {

		}
	}
	@Override
	public void getResourceListPanel(){
		pnlReosurceList.clear();
		Image loadingImage =  new Image();
		loadingImage.setUrl(LOADER_IMAGE);
		loadingImage.getElement().setId("myCollectionsListViewLoaddingImage");
		pnlReosurceList.add(loadingImage);
	}

	@Override
	public void setCollectionDetails(CollectionItemDo collectionItemDo){
		ContentResourceWidgetWithMove contentResourceWidgetWithMove=moveWidgets.get(collectionItemDo.getCollectionItemId());
		if(contentResourceWidgetWithMove!=null){
			contentResourceWidgetWithMove.setCollectionDetails(collectionItemDo);
		}
	}
	
	/**
	 * @function getCreatedTime 
	 * 
	 * @created_date : 22-Jul-2015
	 * 
	 * @description
	 * 
	 * @parm(s) : @param commentCreatedTime
	 * 
	 * @return : String
	 */
	private String getTimeStamp(String commentCreatedTime) {
		String createdTime = null;
		Long currentTime = System.currentTimeMillis();
		Long commentTime = Long.parseLong(commentCreatedTime);
		Long elapsedTime = (currentTime - commentTime);
		
		int seconds = (int) (elapsedTime / 1000) % 60 ;
		int minutes = (int) ((elapsedTime / (1000*60)) % 60);
		int hours   = (int) ((elapsedTime / (1000*60*60)) % 24);
		int days = (int) (elapsedTime / (1000*60*60*24));
		
		if(days>0) {
			createdTime = days + getTimePrefix(days," "+i18n.GL0562(), i18n.GL0579(), i18n.GL0580());
		} else if(hours>0&&hours<24) {
			createdTime = hours + getTimePrefix(hours," "+i18n.GL0563(), i18n.GL1435(), i18n.GL1436());
		} else if(minutes>0&&minutes<60) {
			createdTime = minutes + getTimePrefix(minutes," "+i18n.GL0564(), i18n.GL1437(), i18n.GL1438());
		} else if(seconds<=60) {
			createdTime = i18n.GL0561();
		}
		return createdTime;
	}
	
	/**
	 * @function getTimePrefix 
	 * 
	 * @created_date : 22-Jul-2015
	 * 
	 * @description
	 * 
	 * @parm(s) : @param count
	 * @parm(s) : @param msg
	 * @parm(s) : @param regex
	 * @parm(s) : @param replacement
	 * 
	 * @return : String
	 *
	 */
	private String getTimePrefix(int count, String msg, String regex, String replacement) {
		if(count==1) {
			msg = msg.replaceAll(regex, replacement);
		}
		return msg;
	}
	
	@Override
	public void updateResouceItemImage(String imageUrl,String fileNameWithOutRespUrl, boolean isEditUserOwnResourceImage) {
		if(isEditUserOwnResourceImage){
			ownResourcePopupVc.getSetThumbnailImage().setUrl(imageUrl + "?" + Math.random());
			ownResourcePopupVc.setThumbnailUrlStr(imageUrl);
			ownResourcePopupVc.setFileNameWithOutRespUrl(fileNameWithOutRespUrl);
		}
		else{
			editResoruce.getSetThumbnailImage().setUrl(imageUrl + "?" + Math.random());
			editResoruce.setThumbnailUrlStr(imageUrl);
			editResoruce.setFileNameWithOutRespUrl(fileNameWithOutRespUrl);
		}
		
	}

	@Override
	public void displaySelectedStandards(List<Map<String, String>> standListArray) {
		try
		{
		editResoruce.displaySelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}
		try
		{
		ownResourcePopupVc.displaySelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}
	}


}
