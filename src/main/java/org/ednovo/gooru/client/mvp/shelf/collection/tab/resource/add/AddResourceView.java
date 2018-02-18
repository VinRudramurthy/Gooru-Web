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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add;
/**
* @fileName : AddResourceView.java 
*
* @description :This file is a UiBinder for AddResourceView.ui.xml.
*
* @version :5.1
*
* @date: Apr 6 2013
   	
* @Author  Gooru Team
* 
* @Reviewer 
*
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.application.shared.model.content.ExistsResourceDo;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.content.QuestionHintsDo;
import org.ednovo.gooru.application.shared.model.content.ResourceMetaInfoDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.drive.GoogleDriveItemDo;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.GoogleDocsResourceView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.GoogleWebResource;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.addquestion.QuestionTypePresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.exists.ExistsResourceView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.CollectionEditResourceCBundle;
import org.ednovo.gooru.client.mvp.shelf.event.GetEditPageHeightEvent;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.uc.ConfirmationPopupVc;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.ResourceImageUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class AddResourceView extends PopupViewWithUiHandlers<AddResourceUiHandlers> implements IsAddResourceView {

	private static AddResourcePopupViewUiBinder uiBinder = GWT.create(AddResourcePopupViewUiBinder.class);
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);
	
	interface AddResourcePopupViewUiBinder extends
			UiBinder<Widget, AddResourceView> {
		
	}
	 static List<GoogleDriveItemDo> listobj=new ArrayList<GoogleDriveItemDo>();

	private static final String MESSAGE_HEADER = i18n.GL0748();
	private static final String MESSAGE_CONTENT = i18n.GL0891();
	private AddWebResourceWidget addWebResourceWidget;
	private AddQuestionResourceWidget addQuestionResourceWidget;
	private AddSearchResourceWidget addSearchResourceWidget;
	private AddUserOwnResourceWidget addUserOwnResourceWidget;

	private DeleteConfirmationPopupVc deleteConfirmationPopup;

	private String ownResourceImgUrl;

	public AddWebResourceWidget getAddWebResourceWidget() {
		return addWebResourceWidget;
	}
	protected AppPopUp appPopUp;

	public UserOwnResourcePreview userOwnResourcePreview;

	public WebResourcePreview webResourcePreview;

	@UiField HTMLPanel tabViewContainer,addResourceTabContainer,popUpMain;

	@UiField Anchor fromweb,fromfile,fromwsearch,multiplechoice,truefalase,openended,truefalseText,googleDrive,multipleAnswerAnc,hotSpotImg,hotSpotWord,hotTextRO,hotTextHL;

	@UiField HTMLEventPanel questionTabButton,urlTabButton,searchTabButton,trueOrFlaseButton,openEndedButton,multipleAnswerTabButton,myComputerTabButton,fillInTheBlankTabButton,myDriveButton,hotSpotImageTabButton,hotSpotWordTabButton,hotTextROTabButton,hotTextHLTabButton;


	@UiField Label titleLbl,addResourceCloseButton;

	@UiField RadioButton multipleChoiceRadioButton,trueOrFalseRadioButton,openEndedRadioButton,multipleAnswerRadioButton,fillInTheBlankRadioButton,hotSpotImgRadioButton,hotSpotWordRadioButton,hotTextRORadioButton,hotTextHLRadioButton;

	//@UiField HTMLEventPanel singleCorrectResponseButton,multipleSelectButton,evidenceBasedResponseButton,hotTextButton,reorderTextButton,matchingTablesButton/*,shortTextResponseButton,writtenResponseButton*/;
//	@UiField RadioButton singleCorrectResponseRadioButton,multipleSelectRadioButton,evidenceBasedResponseRadioButton,hotTextRadioButton,reorderTextRadioButton,matchingTablesRadioButton/*,shortTextResponseRadioButton,writtenResponseRadioButton*/;
//	@UiField Anchor singleCorrectResponseText,multipleSelectText,evidenceBasedResponseText,hotTextRadioText,reorderTextText,matchingTablesText/*,shortTextResponseText,writtenResponseText*/;

	@UiField SimplePanel questionContainerPnl;
	private ResourceMetaInfoDo resMetaInfo;

	private ExistsResourceView existsResource=null;

	@UiField CollectionEditResourceCBundle res;

	CollectionDo collectionDo=null;
	String title,desc,category,mediaFilename,originalFileName;

	String webResourceId;
	String webResourceUrl;
	String webResourceTitle;
	String webResourceDescription;
	String webResourceCategory;
	String webResourceThumbnail;
	Integer webResourceEnd;

	private CollectionItemDo collectionItemDo=null;

	private HandlerRegistration handlerRegistration=null;

	private boolean isQuestion =false;

	private boolean isUserResource =false;

	private boolean isEdit =false;

	@Inject
	QuestionTypePresenter questionTypePresenter;

	@Inject
	public AddResourceView(EventBus eventBus) {
		super(eventBus);
		appPopUp = new AppPopUp("type");
		appPopUp.setContent(uiBinder.createAndBindUi(this));

		CollectionEditResourceCBundle.INSTANCE.css().ensureInjected();

		tabViewContainer.getElement().setId("pnlTabViewContainer");
		tabViewContainer.clear();
		fromweb.setText(i18n.GL0887());
		fromfile.setText(i18n.GL0888());
		googleDrive.setText(i18n.GL2009());
		fromwsearch.setText(i18n.GL1916());
		multiplechoice.setText(i18n.GL0305());
		multiplechoice.getElement().setAttribute("title", i18n.GL0305());
		truefalase.setText(i18n.GL0306());
		truefalase.getElement().setAttribute("title", i18n.GL0306());
		truefalseText.setText(i18n.GL0890());
		truefalseText.getElement().setAttribute("title", i18n.GL0890());
		openended.setText(i18n.GL0307());
		openended.getElement().setAttribute("title", i18n.GL0307());
		multipleAnswerAnc.setText(StringUtil.generateMessage(i18n.GL2017()));
		multipleAnswerAnc.getElement().setAttribute("title", StringUtil.generateMessage(i18n.GL2017()));
		fromweb.getElement().setId("lnkFromWeb");
		fromfile.getElement().setId("lnkFromFile");
		fromwsearch.getElement().setId("lnkFromwSearch");
		multiplechoice.getElement().setId("lnkMultipleChoice");
		truefalase.getElement().setId("lnkTrueFalse");
		openended.getElement().setId("lnkOpenEnded");
		questionTabButton.getElement().setId("epnlQuestionTabButton");
		questionTabButton.addClickHandler(new showMultipleChoiceWidget());
		trueOrFlaseButton.getElement().setId("epnlTrueOrFlaseButton");
		trueOrFlaseButton.addClickHandler(new showTrueOrFalseWidget());
		openEndedButton.getElement().setId("epnlOpenEndedButton");
		openEndedButton.addClickHandler(new showOpenEndedWidget());
		fillInTheBlankTabButton.getElement().setId("FillInTheBlankTabButton");
		fillInTheBlankTabButton.addClickHandler(new ShowFillInTheBlanWidget());
		multipleAnswerTabButton.getElement().setId("epnlMultipleAnswerTabButton");
		multipleAnswerTabButton.addClickHandler(new ShowMultipleAnswerWidget());
		titleLbl.getElement().setId("lblTitleLbl");
		addResourceCloseButton.getElement().setId("lblAddResourceCloseButton");
		addResourceTabContainer.getElement().setId("pnlAddResourceTabContainer");
		urlTabButton.getElement().setId("epnlUrlTabButton");
		myComputerTabButton.getElement().setId("epnlMyComputerTabButton");
		myDriveButton.getElement().setId("epnlMyDriveButton");
		googleDrive.getElement().setId("lnkGoogleDrive");
		searchTabButton.getElement().setId("epnlSearchTabButton");
		multipleChoiceRadioButton.getElement().setId("rdMultipleChoiceRadioButton");
		multipleAnswerRadioButton.getElement().setId("rdMultipleAnswerRadioButton");
		trueOrFalseRadioButton.getElement().setId("rdTrueOrFalseRadioButton");
		fillInTheBlankRadioButton.getElement().setId("rdFillInTheBlankRadioButton");
		openEndedRadioButton.getElement().setId("rdOpenEndedRadioButton");
		hotSpotImgRadioButton.getElement().setId("rdHotSpotImgRadioButton");
		hotSpotWordRadioButton.getElement().setId("rdHotSpotWordRadioButton");
		hotTextROTabButton.getElement().setId("epnlhotTextROTabButton");
		hotTextROTabButton.addClickHandler(new showHotTextROWidget());
		hotTextHLTabButton.getElement().setId("epnlhotTextHLTabButton");
		hotTextHLTabButton.addClickHandler(new showHotTextHLWidget());
		hotTextRORadioButton.getElement().setId("rdhotTextRORadioButton");
		hotTextHLRadioButton.getElement().setId("rdhotTextHLRadioButton");
		hotSpotImg.setText(i18n.GL3231_1());
		hotSpotImg.getElement().setAttribute("title", i18n.GL3231_1());
		hotSpotWord.setText(i18n.GL4002());
		hotSpotImageTabButton.getElement().setId("hotSpotImageTabButton");
		hotSpotImageTabButton.addClickHandler(new ShowHotSpotWidget());
		hotSpotWordTabButton.getElement().setId("hotSpotWordTabButton");
		hotSpotWordTabButton.addClickHandler(new ShowHotSpotWordWidget());
		hotTextRO.setText(i18n.GL3212_1());
		hotTextRO.getElement().setId("lnkHotText");
		
		hotTextHL.setText(i18n.GL4003());
		hotTextHL.getElement().setId("lnkHotText");
		
		//assessments tabs
/*		singleCorrectResponseButton.addClickHandler(new AssessmentQuestionsEvent());
		multipleSelectButton.addClickHandler(new AssessmentQuestionsEvent());
		evidenceBasedResponseButton.addClickHandler(new AssessmentQuestionsEvent());
		hotTextButton.addClickHandler(new AssessmentQuestionsEvent());
		reorderTextButton.addClickHandler(new AssessmentQuestionsEvent());
		matchingTablesButton.addClickHandler(new AssessmentQuestionsEvent());*/
		urlTabButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				tabViewContainer.clear();
				showAddWebResourceWidget(false,null,null);
				MixpanelUtil.Add_Resource_Click_Computer();
			}
		});
		
		
		myComputerTabButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				tabViewContainer.clear();
				hideTabButtons(true, false, false);
				addUserOwnResourceWidget=new AddUserOwnResourceWidget(getUiHandlers().getParentCollectionDetails());
				tabViewContainer.add(addUserOwnResourceWidget);
				tabViewContainer.getElement().setId("pnlTabViewContainer");
				deselectSelectedButton();
				myComputerTabButton.setStyleName(res.css().buttonSelected());
			}
		});
		
		searchTabButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				tabViewContainer.clear();
				hideTabButtons(true, false, false);
				addSearchResourceWidget=new AddSearchResourceWidget(getUiHandlers().getParentCollectionDetails(),appPopUp);
				tabViewContainer.add(addSearchResourceWidget);
				tabViewContainer.getElement().setId("pnlTabViewContainer");
				deselectSelectedButton();
				searchTabButton.setStyleName(res.css().buttonSelected());
			}
		});

		Window.enableScrolling(false);
		
		myDriveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				hideTabButtons(true, false, false);
				tabViewContainer.clear();
				getUiHandlers().showDriveResoureView(tabViewContainer);
				deselectSelectedButton();
				myDriveButton.setStyleName(res.css().buttonSelected());
			}
		});
			
	}
	
	public void hideTabButtons(boolean isResourceWidget,boolean isQuestionWidget,boolean isAssementsWidget){
		//Add Resource Tabs
		urlTabButton.setVisible(isResourceWidget);
		searchTabButton.setVisible(isResourceWidget);
		myComputerTabButton.setVisible(isResourceWidget);
		myDriveButton.setVisible(isResourceWidget);
		//Question and assements Tabs
		questionTabButton.setVisible(isQuestionWidget);
		multipleAnswerTabButton.setVisible(isQuestionWidget);
		trueOrFlaseButton.setVisible(isQuestionWidget);
		openEndedButton.setVisible(isAssementsWidget);
		fillInTheBlankTabButton.setVisible(isQuestionWidget);
		hotSpotImageTabButton.setVisible(isQuestionWidget);
		hotSpotWordTabButton.setVisible(isQuestionWidget);
		hotTextROTabButton.setVisible(isQuestionWidget);
		hotTextHLTabButton.setVisible(isQuestionWidget);
		
		//assessment tabs
/*		singleCorrectResponseButton.setVisible(isAssementsWidget);
		multipleSelectButton.setVisible(isAssementsWidget);
		evidenceBasedResponseButton.setVisible(isAssementsWidget);
		hotTextButton.setVisible(isAssementsWidget);
		reorderTextButton.setVisible(isAssementsWidget);
		matchingTablesButton.setVisible(isAssementsWidget);*/
	}
	
	public void showAddWebResourceWidget(boolean isGoogleDriveFile,FlowPanel googleDriveContainer,GoogleDriveItemDo googleDriveItemDo){
		hideTabButtons(true, false, false);
		urlTabButton.getElement().setId("epnlUrlTabButton");
		addWebResourceWidget=new AddWebResourceWidget(getUiHandlers().getParentCollectionDetails(),isGoogleDriveFile,googleDriveItemDo);
		if(isGoogleDriveFile){
			addWebResourceWidget.setStyleName(res.css().driveWebContainer());
			googleDriveContainer.add(addWebResourceWidget);
		}else{
			tabViewContainer.add(addWebResourceWidget);
			tabViewContainer.getElement().setId("pnlTabViewContainer");
			deselectSelectedButton();
			urlTabButton.setStyleName(res.css().buttonSelected());

		}
		
	}
	@Override
	public void setCollectionItemDo(CollectionItemDo collectionItemDo){
		this.collectionItemDo=collectionItemDo;
	}
	@Override
	public void getResourceMetaInfo(String webUrl){
		addWebResourceWidget.getResourceInfo(webUrl);
	}
	@UiHandler("addResourceCloseButton")
	public void closePopup(ClickEvent event){
		fillInTheBlankRadioButton.setValue(false);
		openEndedRadioButton.setValue(false);
		trueOrFalseRadioButton.setValue(false);
		hotTextRORadioButton.setValue(false);
		hotTextHLRadioButton.setValue(false);
		hotSpotImgRadioButton.setValue(false);
		hotSpotWordRadioButton.setValue(false);
//		fillInTheBlankTabButton.setStyleName(res.css().buttonDeSelected());
//		trueOrFlaseButton.setStyleName(res.css().buttonDeSelected());
//		openEndedButton.setStyleName(res.css().buttonDeSelected());
		deselectSelectedButton();
		closeAddResourcePopup();
		tabViewContainer.clear();	
		addWebResourceWidget=new AddWebResourceWidget(getUiHandlers().getParentCollectionDetails(),false,null);
		tabViewContainer.add(addWebResourceWidget);
		tabViewContainer.getElement().setId("pnlTabViewContainer");
		urlTabButton.setStyleName(res.css().buttonSelected());
		getUiHandlers().addSelectedQuestionType("MC",getAddResourceMetadata());
//		myComputerTabButton.setStyleName(res.css().buttonSelected());
//		questionTabButton.setStyleName(res.css().buttonDeSelected());
//		searchTabButton.setStyleName(res.css().buttonDeSelected());
//		myDriveButton.setStyleName(res.css().buttonDeSelected());
	}
	
	
	public class AddWebResourceWidget extends AddWebResourceView{

		public AddWebResourceWidget(CollectionDo parentCollectionDetails,boolean isGoogleDriveFile,GoogleDriveItemDo googleDriveItemDo) {
			super(parentCollectionDetails,isGoogleDriveFile,googleDriveItemDo);
		}


		@Override
		public void getResourceInfo(String userUrlStr) {

			//Check whether the resource already existing or not.
			userUrlStr=userUrlStr.replaceAll("feature=player_detailpage&", "");
			userUrlStr=userUrlStr.replaceAll("feature=player_embedded&","");
			getUiHandlers().getResourceExists(userUrlStr);
			//Get Meta Info for Resource
			getUiHandlers().getResourceMetaInfo(userUrlStr);
		}


		@Override

		public void addResource(String idStr, String urlStr, String titleStr,String descriptionStr, String categoryStr,String thumbnailUrlStr, Integer endTime,boolean conformationFlag,final String educationalUse,final String momentsOfLearning,final List<CodeDo> standards,final List<StandardFo> centurySkill,final String hostname,final List<String> tagList,final Map<String,List<Integer>> hazardsAndMediaFeatures,final String mediaType) {

			this.setVisible(false);
			
			 webResourceId = idStr;
			 webResourceUrl = urlStr;
			 webResourceTitle = titleStr;
			 webResourceDescription = descriptionStr;
			 webResourceCategory = categoryStr;
			 //Bcaz In the DB the resource category's are singular.
			 if(webResourceCategory.contains("Videos")||webResourceCategory.contains("Interactives")||webResourceCategory.contains("Images")||webResourceCategory.contains("Texts"))
			{
				 webResourceCategory=webResourceCategory.substring(0, webResourceCategory.length()-1);
			}
			 webResourceThumbnail = thumbnailUrlStr;
			 webResourceEnd = endTime; 
			urlStr=urlStr.replaceAll("feature=player_detailpage&", "");
			urlStr=urlStr.replaceAll("feature=player_embedded&","");
			if(conformationFlag){
				webResourcePreview = new WebResourcePreview() {
					
					@Override
					public void showAddResourcePopup() {
						webResourcePreview.hide();
						webResourcePreview.removeFromParent();
						appPopUp.getElement().getStyle().setVisibility(Visibility.VISIBLE);
						loadingTextLbl.getElement().getStyle().setDisplay(Display.NONE);
						buttonsPanel.getElement().getStyle().setDisplay(Display.BLOCK);
					}

					@Override
					public void closeAppPopUp() {
						appPopUp.hide();
					}

					@Override
					public void addWebResource() {
						getUiHandlers().addResource( webResourceId,  webResourceUrl,  webResourceTitle,  webResourceDescription, webResourceCategory,  webResourceThumbnail,  webResourceEnd,educationalUse,momentsOfLearning,standards,centurySkill,hostname,tagList,hazardsAndMediaFeatures,mediaType);
						webResourcePreview.hide();
						webResourcePreview.setGlassEnabled(false);
						appPopUp.hide();
						webResourcePreview.removeFromParent();
					}
				};
				appPopUp.getElement().getStyle().setVisibility(Visibility.HIDDEN);
				webResourcePreview.filePathValueLbl.setText(urlStr);
				webResourcePreview.resourceTitleValueLbl.setText(titleStr);
				if(descriptionStr.equalsIgnoreCase("")||descriptionStr==null){
					webResourcePreview.descriptionTxtLbl.setVisible(false);
				}
				else{
					webResourcePreview.descriptionTxtValueLbl.setText(descriptionStr);
				}

				webResourcePreview.categoryValueLbl.setText(categoryStr);
				if(thumbnailUrlStr!=null){
					webResourcePreview.setThumbnailImage.setUrl(thumbnailUrlStr);
				}else{
					webResourcePreview.setThumbnailImage.setVisible(false);
					webResourcePreview.thumbnailLbl.setVisible(false);
				}

				webResourcePreview.center();
				webResourcePreview.show();

			}else{
				getUiHandlers().addResource(idStr, urlStr, titleStr, descriptionStr, webResourceCategory, thumbnailUrlStr, endTime,educationalUse,momentsOfLearning,standards,centurySkill,hostname,tagList,hazardsAndMediaFeatures,mediaType);
			}
		}

			public void hidePopup(){
			closeAddResourcePopup();
		}

		@Override
		public void resourceImageUpload() {
			getUiHandlers().resourceImageUpload();
		}


		@Override
		public void checkShortenUrl(String userUrlStr) {
			getUiHandlers().isShortenUrl(userUrlStr);
		}
		@Override
		public void updateThumbanilImage(String userUrlStr) {
			getUiHandlers().getResourceImageInfo(userUrlStr);
		}


		@Override
		public void onSelection(SelectionEvent<Suggestion> event) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void showStandardsPopup(String standardVal, String standardsDesc,
				List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
			getUiHandlers().showStandardsPopup(standardVal, standardsDesc, collectionLiPanelWithCloseArray);
			
		}

	}


	public class AddQuestionResourceWidget extends AddQuestionResourceView{

		private CollectionItemDo collectionItemDo;
		public AddQuestionResourceWidget(){
			super();
			collectionItemDo=null;
		}
		public AddQuestionResourceWidget(CollectionItemDo collectionItemDo){
			super(collectionItemDo);
			this.collectionItemDo=collectionItemDo;
		}

		@Override
		public void hidePopup() {
			multipleChoiceRadioButton.setValue(true);
			closeAddResourcePopup();
			tabViewContainer.clear();
			deselectSelectedButton();
			questionTabButton.setStyleName(res.css().buttonSelected());
		}
		@Override
		public void onLoad(){
			super.onLoad();
			setEditQuestionImage();

			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					if(collectionItemDo!=null){
						int type = collectionItemDo.getResource().getType() != null ? collectionItemDo.getResource().getType() : collectionItemDo.getQuestionInfo().getType();
						if(type==10 || type==11){
							getUiHandlers().setHSEditData();
						}
						showEditQuestionResourceView();

					}
				}
			});
		}

		@Override
		public void uploadQuestionImage() {
			getUiHandlers().questionImageUpload();
		}

		@Override
		public void createQuestionResource(String mediaFileName, CollectionQuestionItemDo collectionQuestionItemDo) {
			if(getQuestionEditMode()){

				String thumbnailUrl=null;
				if(addQuestionResourceWidget.addQuestImgContainer.getWidgetCount()>0){
					AddQuestionImg addQuestionImage=(AddQuestionImg)addQuestionResourceWidget.addQuestImgContainer.getWidget(0);
					thumbnailUrl=addQuestionImage.getFileName();
				}
				collectionQuestionItemDo.setMediaFilename(thumbnailUrl);
				getUiHandlers().v2UpdateQuestionResource(collectionItemDo,collectionQuestionItemDo,thumbnailUrl==null?null:"asset-question_"+thumbnailUrl);
			}else{
				getUiHandlers().addQeustionResource(mediaFileName,collectionQuestionItemDo);
			}


		}
		@Override
		public void setEditQuestionImage(String fileName) {
			setImageUrl(fileName, null, true, false);
		}
		@Override
		public void callBrowseStandards() {
			isQuestion =true;
			isUserResource = false;
		}
		public void setUpdatedBrowseStandardsVal(String standardsCodeVal,int id, String desc) {
			//super.setUpdatedBrowseStandarsCode(standardsCodeVal,id,desc);

		}
		@Override
		public void closeStandardsPopup() {

		}
		@Override
		public void showStandardsPopup(String standardVal, String standardsDesc,
				List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
			getUiHandlers().showStandardsPopup(standardVal,standardsDesc,collectionLiPanelWithCloseArray);
			
		}
		@Override
		public void onSelection(SelectionEvent<Suggestion> event) {
			// TODO Auto-generated method stub
			
		}

	}

	public class AddSearchResourceWidget extends AddSearchResourceView{
		@Override
		public void hidePopup() {
			closeAddResourcePopup();
			tabViewContainer.clear();
			addWebResourceWidget=new AddWebResourceWidget(getUiHandlers().getParentCollectionDetails(),false,null);
			tabViewContainer.add(addWebResourceWidget);
			tabViewContainer.getElement().setId("pnlTabViewContainer");
			deselectSelectedButton();
			urlTabButton.setStyleName(res.css().buttonSelected());
		}

		public AddSearchResourceWidget(CollectionDo collectionDo,PopupPanel popuppanel){
			super(collectionDo,popuppanel);
		}
	}

	public class AddUserOwnResourceWidget extends AddUserOwnResourceView {
		public AddUserOwnResourceWidget(CollectionDo collectionDo) {
			super(collectionDo);
		}

		@Override
		public void hidePopup() {
			closeAddResourcePopup();
		}

		@Override
		public void resourceImageUpload() {
			getUiHandlers().userOwnResourceImageUpload();
		}

		@Override
		public void resourceUpload() {
			getUiHandlers().userOwnResourceUpload();
		}

		@Override
		public void showResourcePreview(final String filePath,String resourceMediaFileName,String resourceOriginalFileName,  String resourceTitle,  String resourceDesc, String resourceCategory, final String educationalLevel, final String momentsOfLearning, final List<CodeDo> standardsDo, final List<String> tagList,final Map<String, List<Integer>> hazardsAndMediaFeatures,final String mediaType,final List<StandardFo>  centuryCodesMap) {
			title=resourceTitle;
			desc=resourceDesc;
			category=resourceCategory;
			mediaFilename=resourceMediaFileName;
			originalFileName= resourceOriginalFileName;
			userOwnResourcePreview = new UserOwnResourcePreview() {
				@Override
				public void showAddResourcePopup() {
					userOwnResourcePreview.hide();
					userOwnResourcePreview.removeFromParent();
					appPopUp.getElement().getStyle().setVisibility(Visibility.VISIBLE);
					lblAdding.getElement().getStyle().setDisplay(Display.NONE);
					panelAction.getElement().getStyle().setDisplay(Display.BLOCK);
				}

				@Override
				public void addUserOwnResource() {
					JSONObject jsonObject = setImageUploadJsonObject(originalFileName,mediaFilename, title, desc, category, ownResourceImgUrl,educationalLevel,momentsOfLearning,standardsDo,tagList,hazardsAndMediaFeatures,mediaType,centuryCodesMap);
					ownResourceImgUrl = null;
					userOwnResourcePreview.lblConfirmAdding.getElement().getStyle().setDisplay(Display.BLOCK);
					userOwnResourcePreview.actionPanel.getElement().getStyle().setDisplay(Display.NONE);

					getUiHandlers().addUserOwnResource(jsonObject.toString());
					userOwnResourcePreview.hide();
					userOwnResourcePreview.setGlassEnabled(false);
					appPopUp.hide();
					userOwnResourcePreview.removeFromParent();
				}

				@Override
				public void closeAppPopUp() {
					appPopUp.hide();
				}
			};
			appPopUp.getElement().getStyle().setVisibility(Visibility.HIDDEN);
			userOwnResourcePreview.filePathValueLbl.setText(filePath);
			userOwnResourcePreview.resourceTitleValueLbl.setText(resourceTitle);
			userOwnResourcePreview.descriptionTxtValueLbl.setText(resourceDesc);
			userOwnResourcePreview.categoryValueLbl.setText(resourceCategory);
			if(ownResourceImgUrl!=null){
				userOwnResourcePreview.setThumbnailImage.setUrl(ownResourceImgUrl);
			}
			else{
				userOwnResourcePreview.setThumbnailImage.setVisible(false);
				userOwnResourcePreview.thumbnailLbl.setVisible(false);
			}
			userOwnResourcePreview.setGlassEnabled(true);
			userOwnResourcePreview.setWidth("502px");
			userOwnResourcePreview.getElement().setAttribute("style", "min-height:400px;");
			userOwnResourcePreview.show();
			userOwnResourcePreview.center();

		}
		@Override
		public void addUserResource(String filePath,String mediaFileName,String originalFileName, String resourceTitle,String resourceDesc, String resourceCategory, String educationalLevel, String momentsOfLearning, List<CodeDo> standardsDo, List<String> tagList,Map<String, List<Integer>> hazardsAndMediaFeatures,String mediaType,List<StandardFo>  centuryCodesMap) {
			title=resourceTitle;
			desc=resourceDesc;
			category=resourceCategory;
			JSONObject jsonObject = setImageUploadJsonObject(originalFileName,mediaFileName, title, desc, category, ownResourceImgUrl,educationalLevel,momentsOfLearning,standardsDo,tagList,hazardsAndMediaFeatures,mediaType,centuryCodesMap);
			ownResourceImgUrl = null;
			getUiHandlers().addUserOwnResource(jsonObject.toString());
		}

		@Override
		public void showStandardsPopup(String standardVal, String standardsDesc,
				List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
			getUiHandlers().showStandardsPopup(standardVal,standardsDesc,collectionLiPanelWithCloseArray);
			
		}

		@Override
		public void onSelection(SelectionEvent<Suggestion> event) {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	public Widget asWidget() {
		return appPopUp;
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
	}
	@Override
	public void onUnload() {
		// TODO Auto-generated method stub
	}
	public List<String> getThumbnailImages() {
		return addWebResourceWidget.thumbnailImages;
	}

	public void setThumbnailImages(List<String> thumbnailImages) {
		addWebResourceWidget.thumbnailImages = thumbnailImages;
	}

	public ResourceMetaInfoDo getResMetaInfo() {
		return resMetaInfo;
	}

	public void setResMetaInfo(ResourceMetaInfoDo resMetaInfo) {
		this.resMetaInfo = resMetaInfo;
	}

	public void updateUi(){
		String userUrlStr = addWebResourceWidget.urlTextBox.getText().trim();
		addWebResourceWidget.generateImageLbl.setVisible(false);
		if (userUrlStr.indexOf("youtube")==-1){
			addWebResourceWidget.rightArrowLbl.setVisible(true);
			addWebResourceWidget.isValidYoutubeUrlFlag = true;
		}else{
			if(userUrlStr.indexOf("user") == -1 && userUrlStr.indexOf("list") == -1 && userUrlStr.indexOf("channel") == -1){
				String youTubeIbStr = ResourceImageUtil.getYoutubeVideoId(userUrlStr);
				addWebResourceWidget.thumbnailUrlStr = "http://img.youtube.com/vi/"+youTubeIbStr+"/1.jpg";
				addWebResourceWidget.rightArrowLbl.setVisible(false);
				addWebResourceWidget.isValidYoutubeUrlFlag = true;
				addWebResourceWidget.setVideoDuration(resMetaInfo.getVideoDuration());
			}else{
				addWebResourceWidget.rightArrowLbl.setVisible(true);
				addWebResourceWidget.isValidYoutubeUrlFlag = false;
			}
		}
		if(addWebResourceWidget.thumbnailUrlStr!=null){
			addWebResourceWidget.setThumbnailImage.setUrl(addWebResourceWidget.thumbnailUrlStr);
			addWebResourceWidget.setThumbnailImage.setHeight("60px");
			addWebResourceWidget.setThumbnailImage.setWidth("80px");
			addWebResourceWidget.setThumbnailImage.setVisible(true);
		}else{
			addWebResourceWidget.generateImageLbl.setVisible(true);
			addWebResourceWidget.rightArrowLbl.setVisible(false);
		}
	}
	public void setImageUrl(String fileName,String fileNameWithoutRepository,boolean isQuestionImage,boolean isUserOwnResourceImage){
		double randNumber = Math.random();
		if(isQuestionImage){
			AddQuestionImg addQuestionImage = new AddQuestionImg();
			addQuestionImage.setQuestionImage(fileName+"?"+randNumber);
			addQuestionImage.setFileName(fileNameWithoutRepository);
			addQuestionResourceWidget.addQuestImgContainer.clear();
			addQuestionResourceWidget.addQuestionImg.getElement().getStyle().setDisplay(Display.NONE);
			addQuestionResourceWidget.addQuestImgContainer.add(addQuestionImage);
			addQuestionResourceWidget.setImageHandler();
			addQuestionImage.changeImgLbl.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(addQuestionResourceWidget.getQuestionEditMode()){
						getUiHandlers().questionImageUpload(collectionItemDo.getCollectionItemId());
					}else{
						getUiHandlers().questionImageUpload();
					}
				}
			});
			addQuestionImage.removeImgLbl.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					boolean isQuestionEditMode=addQuestionResourceWidget.getQuestionEditMode();
					if(isQuestionEditMode){
						deleteConfirmationPopup=new DeleteConfirmationPopupVc(MESSAGE_HEADER,MESSAGE_CONTENT);
					}else{
						addQuestionResourceWidget.addQuestImgContainer.clear();
						addQuestionResourceWidget.addQuestionImg.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
					}
				}
			});
		}
		else if(isUserOwnResourceImage){
			addUserOwnResourceWidget.thumbnailUrlStr = fileName+"?"+randNumber;
			addUserOwnResourceWidget.setThumbnailImage.setUrl(addUserOwnResourceWidget.thumbnailUrlStr);
			addUserOwnResourceWidget.setThumbnailImage.setHeight("60px");
			addUserOwnResourceWidget.setThumbnailImage.setWidth("80px");
			addUserOwnResourceWidget.imageContainer.getElement().getStyle().setDisplay(Display.BLOCK);
			addUserOwnResourceWidget.setThumbnailImage.setVisible(true);
			ownResourceImgUrl = fileName+"?"+randNumber;
		}else{
			addWebResourceWidget.rightArrowLbl.setVisible(false);
			addWebResourceWidget.leftArrowLbl.setVisible(false);
			addWebResourceWidget.generateImageLbl.setVisible(false);
			addWebResourceWidget.thumbnailUrlStr = fileName+"?"+randNumber;
			System.out.println("asdf::"+fileName+"?"+randNumber);
			addWebResourceWidget.setThumbnailImage.setUrl(addWebResourceWidget.thumbnailUrlStr);
			addWebResourceWidget.setThumbnailImage.setHeight("60px");
			addWebResourceWidget.setThumbnailImage.setWidth("80px");
			addWebResourceWidget.setThumbnailImage.setVisible(true);
		}
	}

	public void closeAddResourcePopup(){
		AppClientFactory.fireEvent(new GetEditPageHeightEvent(appPopUp, true));
		Window.enableScrolling(true);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		appPopUp.hide();
	}
	@Override
	public void setNewResourcePopupData(ResourceMetaInfoDo resMetaInfo) {
		addWebResourceWidget.loadingPanel.setVisible(false);
		addWebResourceWidget.contentPanel.getElement().getStyle().clearOpacity();
		if(resMetaInfo!=null){
			if (resMetaInfo.getTitle().length()>50){
				addWebResourceWidget.titleTextBox.setText(resMetaInfo.getTitle().substring(0, 50));
			}else{
				addWebResourceWidget.titleTextBox.setText(resMetaInfo.getTitle());
			}

			if (resMetaInfo.getDescription().length()>300){
				addWebResourceWidget.descriptionTxtAera.setText(resMetaInfo.getDescription().substring(0, 300));
			}else{
				addWebResourceWidget.descriptionTxtAera.setText(resMetaInfo.getDescription());
			}
			setResMetaInfo(resMetaInfo);
			setThumbnailImages(resMetaInfo.getImages());
		}
	}

	@Override
	public void setPopupImageData(ResourceMetaInfoDo resMetaInfo) {
		addWebResourceWidget.loadingPanel.setVisible(false);
		addWebResourceWidget.contentPanel.getElement().getStyle().clearOpacity();
		if(resMetaInfo!=null){
			setResMetaInfo(resMetaInfo);
			setThumbnailImages(resMetaInfo.getImages());
			updateUi();
			if(resMetaInfo.getImages().size()<=0){
				addWebResourceWidget.generateImageLbl.setVisible(true);
				addWebResourceWidget.rightArrowLbl.setVisible(false);
				addWebResourceWidget.leftArrowLbl.setVisible(false);
				addWebResourceWidget.setThumbnailImage.setVisible(false);
			}
			addWebResourceWidget.setThumbnailImage.setUrl(resMetaInfo.getImages().size() >0 ? resMetaInfo.getImages().get(0) : "");
			addWebResourceWidget.setThumbnailImage.setHeight("60px");
			addWebResourceWidget.setThumbnailImage.setWidth("80px");
		}
	}
	@Override
	public void setExistingResourceData(ExistsResourceDo resourceDo, CollectionDo collectionDo){
		this.hide();
		existsResource = new ExistsResourceView();
		existsResource.displayResourceInformation(resourceDo, collectionDo);
		existsResource.setAddNewPopup(this);
		existsResource.show();
		existsResource.center();
	}

	@Override
	public void setShortenUrlAlertMsg(){
		addWebResourceWidget.mandatoryUrlLbl.setText(i18n.GL0892());
		addWebResourceWidget.mandatoryUrlLbl.setVisible(true);
		addWebResourceWidget.setShortenedUrl(true);
	}

	public HTMLEventPanel getQuestionTabButton() {
		return questionTabButton;
	}
	public CollectionDo getCollectionDo() {
		return collectionDo;
	}
	public void setCollectionDo(CollectionDo collectionDo) {
		this.collectionDo = collectionDo;
	}

	@Override
	public void setPopup(String clickType) {
		titleLbl.getElement().setId("lblTitleLbl");
		addResourceCloseButton.getElement().setId("lblAddResourceCloseButton");
		addResourceTabContainer.getElement().setId("pnlAddResourceTabContainer");
		if(clickType.equalsIgnoreCase("Url")){
			tabViewContainer.clear();
			Window.enableScrolling(false);
			titleLbl.setText(i18n.GL0886());
			hideTabButtons(true, false, false);
			addWebResourceWidget=new AddWebResourceWidget(getUiHandlers().getParentCollectionDetails(),false,null);
			tabViewContainer.add(addWebResourceWidget);
			tabViewContainer.getElement().setId("pnlTabViewContainer");
			deselectSelectedButton();
			urlTabButton.setStyleName(res.css().buttonSelected());
		} else if(clickType.equalsIgnoreCase("Question")){
			try{
				Window.enableScrolling(false);
				isEdit=true;
				disableEditMode(Cursor.POINTER);
				tabViewContainer.clear();
				titleLbl.setText(i18n.GL0893());
				getUiHandlers().addSelectedQuestionType("MC",null);
				addQuestionResourceWidget=new AddQuestionResourceWidget();
				addQuestionResourceWidget.getHideRightsToolTip();
//				questionTabButton.getElement().getStyle().setDisplay(Display.BLOCK);
//				trueOrFlaseButton.getElement().getStyle().setDisplay(Display.BLOCK);
//				openEndedButton.getElement().getStyle().setDisplay(Display.BLOCK);
//				fillInTheBlankTabButton.getElement().getStyle().setDisplay(Display.BLOCK);
//				multipleAnswerTabButton.getElement().getStyle().setDisplay(Display.BLOCK);
//				urlTabButton.getElement().getStyle().setDisplay(Display.NONE);
//				myComputerTabButton.getElement().getStyle().setDisplay(Display.NONE);
//				searchTabButton.getElement().getStyle().setDisplay(Display.NONE);
//				myDriveButton.getElement().getStyle().setDisplay(Display.NONE);
				if(collectionDo!=null&&collectionDo.getCollectionType()!=null&&collectionDo.getCollectionType().equalsIgnoreCase("assessment")){
					hideTabButtons(false, true, false);
				}else{
					hideTabButtons(false, true, true);
				}
				setRadioButtonValues();
				addQuestionResourceWidget.setQuestionType("MC");
				addQuestionResourceWidget.showMulipleChoice();
				tabViewContainer.add(addQuestionResourceWidget);
				tabViewContainer.getElement().setId("pnlTabViewContainer");
				questionTabButton.setStyleName(res.css().buttonSelected());
				AppClientFactory.fireEvent(new GetEditPageHeightEvent(appPopUp, false));
			    urlTabButton.setStyleName(res.css().buttonDeSelected());
			    myComputerTabButton.setStyleName(res.css().buttonDeSelected());
			    searchTabButton.setStyleName(res.css().buttonDeSelected());
			    fillInTheBlankTabButton.setStyleName(res.css().buttonDeSelected());
			    multipleAnswerTabButton.setStyleName(res.css().buttonDeSelected());
			    trueOrFlaseButton.setStyleName(res.css().buttonDeSelected());
			    openEndedButton.setStyleName(res.css().buttonDeSelected());
                hotTextROTabButton.setStyleName(res.css().buttonDeSelected());
                hotTextHLTabButton.setStyleName(res.css().buttonDeSelected());
            	questionTypePresenter.getView().editQuestion(null);
			}catch(Exception e) {
				AppClientFactory.printSevereLogger("AddResourceView setPopup:::"+e);
			}
		} else if(clickType.equalsIgnoreCase("QuestionEdit")){
			try{
				Window.enableScrolling(false);
				isEdit=false;
				disableEditMode(Cursor.DEFAULT);
				tabViewContainer.clear();
				titleLbl.setText(i18n.GL0304());
				int type = collectionItemDo.getResource().getType() != null ? collectionItemDo.getResource().getType() : collectionItemDo.getQuestionInfo().getType();
				
				if(type==10){
					getUiHandlers().addSelectedQuestionType("HS_TXT",null);
				}else if(type==11){
					getUiHandlers().addSelectedQuestionType("HS_IMG",null);
				}else{
					getUiHandlers().addSelectedQuestionType("MC",null);
				}
				getUiHandlers().setEditQuestionData(collectionItemDo);
				addQuestionResourceWidget=new AddQuestionResourceWidget(collectionItemDo);
				//addQuestionResourceWidget.setCheckedData(collectionItemDo.getDepthOfKnowledges());
				
				addQuestionResourceWidget.getHideRightsToolTip();
				
				if(collectionItemDo.getCollection()!=null&&collectionItemDo.getCollection().getCollectionType().equalsIgnoreCase("assessment")){
					hideTabButtons(false, true, false);
				}else{
					hideTabButtons(false, true, true);
				}
				tabViewContainer.add(addQuestionResourceWidget);
				tabViewContainer.getElement().setId("pnlTabViewContainer");
				int questionTypeNum=collectionItemDo.getResource().getType() != null ? collectionItemDo.getResource().getType() : collectionItemDo.getQuestionInfo().getType();
				if(questionTypeNum==1){
					highlightSelectedTab("MC");
					multipleChoiceRadioButton.setValue(true);
				}else if(questionTypeNum==3){
					highlightSelectedTab("TF");
					trueOrFalseRadioButton.setValue(true);
				}else if(questionTypeNum==6){
					highlightSelectedTab("OE");
					openEndedRadioButton.setValue(true);
				}else if(questionTypeNum==7){
					highlightSelectedTab("MA");
					multipleAnswerRadioButton.setValue(true);
				}else if(questionTypeNum==4){
					highlightSelectedTab("FIB");
					fillInTheBlankRadioButton.setValue(true);
				}else if(questionTypeNum==8){
					highlightSelectedTab("HT_HL");
					hotTextHLRadioButton.setValue(true);
				}else if(questionTypeNum==9){
					highlightSelectedTab("HT_RO");
					hotTextRORadioButton.setValue(true);
				}else if(questionTypeNum==10){
					highlightSelectedTab("HS_TXT");
					hotSpotWordRadioButton.setValue(true);
				}else if(questionTypeNum==11){
					highlightSelectedTab("HS_IMG");
					hotSpotImgRadioButton.setValue(true);
				}
				AppClientFactory.fireEvent(new GetEditPageHeightEvent(appPopUp, false));
			}catch(Exception e) {
				AppClientFactory.printSevereLogger("AddResourceView:::"+e);
			}
		}else if(clickType.equalsIgnoreCase("Search")) {
			Window.enableScrolling(true);
			titleLbl.setText(i18n.GL0886());
			tabViewContainer.clear();
			addSearchResourceWidget=new AddSearchResourceWidget(getUiHandlers().getParentCollectionDetails(),appPopUp);
			tabViewContainer.add(addSearchResourceWidget);
			tabViewContainer.getElement().setId("pnlTabViewContainer");
			searchTabButton.setStyleName(res.css().buttonSelected());
			questionTabButton.setStyleName(res.css().buttonDeSelected());
			urlTabButton.setStyleName(res.css().buttonDeSelected());
			myComputerTabButton.setStyleName(res.css().buttonDeSelected());
			hideTabButtons(true, false, false);
		}
	}

	private void setRadioButtonValues() {
		multipleChoiceRadioButton.setValue(true);
		trueOrFalseRadioButton.setValue(false);
		fillInTheBlankRadioButton.setValue(false);
		openEndedRadioButton.setValue(false);
		hotTextRORadioButton.setValue(false);
		hotTextHLRadioButton.setValue(false);
	}
	@Override
	public void closePopUp() {
		if(appPopUp.isShowing()){
			appPopUp.hide();
		}
	}

	private class showMultipleChoiceWidget implements ClickHandler{
		public void onClick(ClickEvent event) {
			Window.enableScrolling(false);
			if(!multipleChoiceRadioButton.getValue() && isEdit){
				//Window.enableScrolling(true);
				getUiHandlers().addSelectedQuestionType("MC",getAddResourceMetadata());
				displayQuestionWidget();
				multipleChoiceRadioButton.setValue(true);
				highlightSelectedTab("MC");
				addQuestionResourceWidget.setQuestionType("MC");
				addQuestionResourceWidget.showMulipleChoice();
				addQuestionResourceWidget.addResourceFormTitleChoice.setText(i18n.GL0864());
				addQuestionResourceWidget.correctText.clear();
				addQuestionResourceWidget.correctText.getElement().setInnerHTML(i18n.GL0314());
				addQuestionResourceWidget.setCorrectTextStyle();
				addQuestionResourceWidget.noLabelText.setVisible(false);
				AppClientFactory.fireEvent(new GetEditPageHeightEvent(appPopUp, false));
			}
		}
	}
	private class showTrueOrFalseWidget implements ClickHandler{
		public void onClick(ClickEvent event) {
			Window.enableScrolling(false);
			if(!trueOrFalseRadioButton.getValue() && isEdit){

				getAddResourceMetadata();

				getUiHandlers().addSelectedQuestionType("T/F",getAddResourceMetadata());
				displayQuestionWidget();
				trueOrFalseRadioButton.setValue(true);
				highlightSelectedTab("TF");
				addQuestionResourceWidget.setQuestionType("T/F");
				addQuestionResourceWidget.showTrueOrFalseAnswerChoice();
				addQuestionResourceWidget.addResourceFormTitleChoice.setText(i18n.GL0864());
				addQuestionResourceWidget.correctText.clear();
				addQuestionResourceWidget.correctText.getElement().setInnerHTML(i18n.GL0314());
				addQuestionResourceWidget.setCorrectTextStyle();
				addQuestionResourceWidget.noLabelText.setVisible(false);

			}
		}
	}
	private class showOpenEndedWidget implements ClickHandler{
		public void onClick(ClickEvent event) {
			Window.enableScrolling(false);
			if(!openEndedRadioButton.getValue() && isEdit){
				getUiHandlers().addSelectedQuestionType("OE",getAddResourceMetadata());
				displayQuestionWidget();
				openEndedRadioButton.setValue(true);
				highlightSelectedTab("OE");
				addQuestionResourceWidget.setQuestionType("OE");
				addQuestionResourceWidget.showOpenEndedQuestion();
			}
		}
	}
	private class ShowMultipleAnswerWidget implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			Window.enableScrolling(false);
			if(!multipleAnswerRadioButton.getValue() && isEdit){
				getUiHandlers().addSelectedQuestionType("MA",getAddResourceMetadata());
				displayQuestionWidget();
				highlightSelectedTab("MA");
				multipleAnswerRadioButton.setValue(true);
				addQuestionResourceWidget.setQuestionType("MA");
				addQuestionResourceWidget.addResourceFormTitleChoice.setText("Enter answers and select correct ones *");
				addQuestionResourceWidget.correctText.clear();
				addQuestionResourceWidget.correctText.getElement().setInnerHTML("Yes");
				addQuestionResourceWidget.noLabelText.setVisible(true);
				addQuestionResourceWidget.noLabelText.getElement().setInnerHTML("No");
				addQuestionResourceWidget.setYesOrNoLabelStyles();
				addQuestionResourceWidget.showMulipleAnswerChoiceOptions();
			}
		}

	}
	private class ShowFillInTheBlanWidget implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			Window.enableScrolling(false);
			if(!fillInTheBlankRadioButton.getValue() && isEdit){
				getUiHandlers().addSelectedQuestionType("FIB",getAddResourceMetadata());
				displayQuestionWidget();
				fillInTheBlankRadioButton.setValue(true);
				highlightSelectedTab("FIB");
				addQuestionResourceWidget.setQuestionType("FIB");
				addQuestionResourceWidget.showFillInTheBlank();
			}
		}
	}
	public void displayQuestionWidget(){
		int widgetCount=tabViewContainer.getWidgetCount();
		if(widgetCount>0){
			Widget widget=tabViewContainer.getWidget(0);
			if(!(widget instanceof AddQuestionResourceWidget)){
				tabViewContainer.clear();
				tabViewContainer.add(addQuestionResourceWidget);
			}
		}
	}


	private class ShowHotSpotWidget implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			Window.enableScrolling(false);
			if(!hotSpotImgRadioButton.getValue() && isEdit){
				hotSpotImgRadioButton.setValue(true);
				highlightSelectedTab("HS_IMG");
				getUiHandlers().addSelectedQuestionType("HS_IMG",getAddResourceMetadata());

				if(titleLbl.getText().equalsIgnoreCase(i18n.GL0304())){
					getUiHandlers().setHSEditData();
				}
			}
		}

	}
	private class ShowHotSpotWordWidget implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			Window.enableScrolling(false);
			if(!hotSpotWordRadioButton.getValue() && isEdit){
				hotSpotWordRadioButton.setValue(true);
				highlightSelectedTab("HS_TXT");
				getUiHandlers().addSelectedQuestionType("HS_TXT",getAddResourceMetadata());

				if(titleLbl.getText().equalsIgnoreCase(i18n.GL0304())){
					getUiHandlers().setHSEditData();
				}
			}
		}

	}


	private class showHotTextROWidget implements ClickHandler{
		public void onClick(ClickEvent event) {
			Window.enableScrolling(false);
			if(!hotTextRORadioButton.getValue() && isEdit){
				getUiHandlers().addSelectedQuestionType("HT_RO",getAddResourceMetadata());
				displayQuestionWidget();
				hotTextRORadioButton.setValue(true);
				highlightSelectedTab("HT_RO");
				addQuestionResourceWidget.setQuestionType("HT_RO");
				addQuestionResourceWidget.showHotTextQuestion();
			}
		}
	}

	private class showHotTextHLWidget implements ClickHandler{
		public void onClick(ClickEvent event) {
			Window.enableScrolling(false);
			if(!hotTextHLRadioButton.getValue() && isEdit){
				getUiHandlers().addSelectedQuestionType("HT_HL",getAddResourceMetadata());
				displayQuestionWidget();
				hotTextHLRadioButton.setValue(true);
				highlightSelectedTab("HT_HL");
				addQuestionResourceWidget.setQuestionType("HT_HL");
				addQuestionResourceWidget.showHotTextQuestion();
			}
		}
	}

	@Override
	public void addToSlot(Object slot, Widget content) {
		super.addToSlot(slot, content);
		questionContainerPnl.clear();
		if (slot == AddResourceUiHandlers.SLOT_QUESTION_TYPE) {
			tabViewContainer.setVisible(false);
			questionContainerPnl.setWidget(content);
		}

	}

	public void clearQuestionSlot() {
		questionContainerPnl.clear();
		tabViewContainer.setVisible(true);
	}

/*	private class AssessmentQuestionsEvent implements ClickHandler{
		public void onClick(ClickEvent event) {
			Window.enableScrolling(false);
			if(collectionDo!=null&&collectionDo.getCollectionType().equals("quiz")){
				tabViewContainer.clear();
				tabViewContainer.add(new HTML("<h3>Comming soon........</h3>"));
			}
		}
	}*/
	private void deselectSelectedButton(){
		int widgetsCount=addResourceTabContainer.getWidgetCount();
		for(int i=0;i<widgetsCount;i++){
			Widget widget=addResourceTabContainer.getWidget(i);
			if( widget instanceof HTMLEventPanel){
				widget.setStyleName(res.css().buttonDeSelected());
			}
		}
	}
	private void highlightSelectedTab(String tabType){
		deselectSelectedButton();
		if(tabType.equals("FIB")){
			fillInTheBlankTabButton.setStyleName(res.css().buttonSelected());
		}else if(tabType.equals("TF")){
			trueOrFlaseButton.setStyleName(res.css().buttonSelected());
		}else if(tabType.equals("MA")){
			multipleAnswerTabButton.setStyleName(res.css().buttonSelected());
		}else if(tabType.equals("MC")){
			questionTabButton.setStyleName(res.css().buttonSelected());
		}else if(tabType.equals("OE")){
			openEndedButton.setStyleName(res.css().buttonSelected());
		}else if(tabType.equals("URL")){
			urlTabButton.setStyleName(res.css().buttonSelected());
		}else if(tabType.equals("SEARCH")){
			searchTabButton.setStyleName(res.css().buttonSelected());
		}else if(tabType.equals("HS_IMG")){
			hotSpotImageTabButton.setStyleName(res.css().buttonSelected());
		}else if(tabType.equals("HS_TXT")){
			hotSpotWordTabButton.setStyleName(res.css().buttonSelected());
		}else if(tabType.equals("HT_HL")){
			hotTextHLTabButton.setStyleName(res.css().buttonSelected());
		}else if(tabType.equals("HT_RO")){
			hotTextROTabButton.setStyleName(res.css().buttonSelected());
		}
	}
	@Override
	public void removeQuestionEditImage() {
		deleteConfirmationPopup.hide();
		addQuestionResourceWidget.addQuestImgContainer.clear();
		addQuestionResourceWidget.addQuestionImg.getElement().getStyle().setDisplay(Display.BLOCK);

	}

	private class DeleteConfirmationPopupVc extends ConfirmationPopupVc{
		public DeleteConfirmationPopupVc(String messageHeader,String messageContent){
			super(messageHeader, messageContent);
			setPopupZindex(9999);
			setGlassZindex(9998);
			setScrollDisable();
		}
		@Override
		public void onDelete(ClickEvent clickEvent) {
			getUiHandlers().removeQuestionImage(collectionItemDo.getCollectionItemId());
		}
		public void hide() {
			 super.hide();

			//Window.enableScrolling(true);
			//AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0,true));
		}
	}


	@Override
	public void uploadResource(MediaUploadDo result) {
	}

	public JSONObject setImageUploadJsonObject(String originalFileName,String mediaFileName,String resourceTitle, String resourceDesc,	String resourceCategory, String ownResourceImgUrl, String educationalLevel, String momentsOfLearning, List<CodeDo> standardsDo, List<String> tagList,Map<String, List<Integer>> hazardsAndMediaFeatures,String mediaType, List<StandardFo> centuryCodesMap) {
		JSONObject file = new JSONObject();
        file.put("filename", new JSONString(originalFileName));
        file.put("mediaFilename", new JSONString(mediaFileName));
        JSONObject attach = new JSONObject();
        attach.put("title", new JSONString(resourceTitle));
        attach.put("description", new JSONString(resourceDesc));
        JSONObject resourceFormat = new JSONObject();
        if(resourceCategory.equalsIgnoreCase("Text")){
        	resourceFormat.put("value", new JSONString("Text"));
	        attach.put("category", new JSONString("Handout"));
        }else{
           	resourceFormat.put("value", new JSONString("Image"));
        	attach.put("category", new JSONString("Slide"));
        }
        attach.put("resourceFormat", resourceFormat);
        if(ownResourceImgUrl!=null){
        	 attach.put("thumbnail", new JSONString(ownResourceImgUrl));
        }
        attach.put("attach", file);

        JSONArray standardsJsonArray = new JSONArray();
        JSONArray momentsOfLearningArrValue = new JSONArray();
        JSONArray educatUseArrValue = new JSONArray();
        JSONArray tagsArrValue = new JSONArray();

        for(int i=0;i<standardsDo.size();i++){
           	standardsJsonArray.set(i,new JSONNumber(standardsDo.get(i).getCodeId()));
        }
        attach.put("standardIds", standardsJsonArray);
        momentsOfLearningArrValue.set(0, new JSONNumber(Integer.parseInt(momentsOfLearning)));

        attach.put("momentsOfLearningIds", momentsOfLearningArrValue);

        educatUseArrValue.set(0,new JSONNumber(Integer.parseInt(educationalLevel)));

        attach.put("educationalUseIds", educatUseArrValue);

        for(int i=0;i<tagList.size();i++){
        	tagsArrValue.set(i, new JSONString(tagList.get(i)));
        }

        JSONObject resource = new JSONObject();
        resource.put("resourceTags",tagsArrValue);
        resource.put("resource", attach);
        resource.put("mediaType",new JSONString(mediaType));
        JSONArray hazardIds = new JSONArray();
        for(int i=0;i<hazardsAndMediaFeatures.get("hazard").size();i++){
        	hazardIds.set(i, new JSONNumber(hazardsAndMediaFeatures.get("hazard").get(i)));
        }
        resource.put("accessHazardIds",hazardIds);
        JSONArray mediaIds = new JSONArray();
        for(int i=0;i<hazardsAndMediaFeatures.get("media").size();i++){
        	mediaIds.set(i, new JSONNumber(hazardsAndMediaFeatures.get("media").get(i)));
        }
        resource.put("mediaFeatureIds",mediaIds);
       
        JSONArray skillsIds = new JSONArray();
        for (int i = 0; i < centuryCodesMap.size(); i++) {
        	StandardFo standardFo = centuryCodesMap.get(i);
        	skillsIds.set(i, new JSONNumber(standardFo.getCodeId()));
		}
        resource.put("skillIds",skillsIds);
       	return resource;
	}

	public String getYoutubeVideoLength(Integer length){
		int totalVideoLength = length;
		String tolTimeInmin = "";
		String totalTimeSec = "";

		int tolTimeInminutes = totalVideoLength / 60;
		if (tolTimeInminutes < 10) {
			tolTimeInmin = "0"
					+ tolTimeInminutes;
		} else {
			tolTimeInmin = tolTimeInminutes
					+ "";
		}

		int totalTimeInseconds = totalVideoLength % 60;
		if (totalTimeInseconds < 10) {
			totalTimeSec = "0"
					+ totalTimeInseconds;
		} else {
			totalTimeSec = totalTimeInseconds
					+ "";
		}
		return tolTimeInmin+":"+totalTimeSec;
	}
	public static Map setData(Map<String, Object> resultObj){
	listobj=(List<GoogleDriveItemDo>) resultObj.get("items");
		return resultObj;
	}
	@Override
	public void getDriveDetails(GoogleDriveItemDo driveDo){
		tabViewContainer.clear();
		tabViewContainer.add(new GoogleDocsResourceView(driveDo));
		tabViewContainer.getElement().setId("pnlTabViewContainer");
	}


	@Override
	public void getFolderDetails(String title, String id, List<GoogleDriveItemDo> result) {
		tabViewContainer.clear();
		for(int m=0;m<result.size();m++){
	         tabViewContainer.add(new GoogleWebResource(result.get(m)));
		}
		tabViewContainer.getElement().setId("pnlTabViewContainer");
	}
	@Override
	public void hidePopup() {
		getUiHandlers().addSelectedQuestionType("MC",getAddResourceMetadata());
		multipleChoiceRadioButton.setValue(true);
		closeAddResourcePopup();
		tabViewContainer.clear();
		deselectSelectedButton();
		questionTabButton.setStyleName(res.css().buttonSelected());
	}

	public CollectionQuestionItemDo getAddResourceMetadata(){

		if(addQuestionResourceWidget!=null){

			CollectionQuestionItemDo collectionQuestionItemDo = new CollectionQuestionItemDo();

		collectionQuestionItemDo.setQuestionText(addQuestionResourceWidget.questionNameTextArea.getText()!=null?addQuestionResourceWidget.questionNameTextArea.getText():"");
		collectionQuestionItemDo.setExplanation(addQuestionResourceWidget.explainationTextArea.getText()!=null?addQuestionResourceWidget.explainationTextArea.getText():"");
		collectionQuestionItemDo.setDepthOfKnowledgeIds(addQuestionResourceWidget.depthOfKnowledgesList);



		ArrayList<QuestionHintsDo> enteredHints = new ArrayList<QuestionHintsDo>();
		HashMap<String,ArrayList<QuestionHintsDo>> hintsMap = new HashMap<String,ArrayList<QuestionHintsDo>>();

		for(int i=0;i<addQuestionResourceWidget.hintsContainer.getWidgetCount();i++)
		{
			AddHintsView addHints = (AddHintsView)addQuestionResourceWidget.hintsContainer.getWidget(i);
			QuestionHintsDo questionHintsDo=new QuestionHintsDo();
			String hintText=addHints.hintTextBox.getText();
			if(hintText!=null&&!hintText.trim().equals("")&&!hintText.isEmpty()){
				hintText=addHints.hintTextBox.getRawContent().trim();
			}
			questionHintsDo.setHintText(hintText);
			questionHintsDo.setSequence(i+1);
			enteredHints.add(questionHintsDo);
		}
		hintsMap.put("hint",enteredHints);
		collectionQuestionItemDo.setHints(hintsMap);


		collectionQuestionItemDo.setCenturySelectedValues(addQuestionResourceWidget.centurySelectedValues);

		if(collectionQuestionItemDo.getTaxonomySet()!=null){
		collectionQuestionItemDo.getTaxonomySet().clear();
		}
		HashMap<String,ArrayList<CodeDo>> taxonomySet = new HashMap<String,ArrayList<CodeDo>>();
		taxonomySet.put("taxonomyCode", addQuestionResourceWidget.standardsDo);
		collectionQuestionItemDo.setTaxonomySet(taxonomySet);

		HashMap<String,Boolean> moreOptions=new HashMap<String, Boolean>();

		moreOptions.put("explanation",addQuestionResourceWidget.addExplanationLabel.isVisible());
		moreOptions.put("hints", addQuestionResourceWidget.addHintsLabel.isVisible());
		moreOptions.put("DOK", addQuestionResourceWidget.addDepthOfKnowledgeLabel.isVisible());
		moreOptions.put("standards", addQuestionResourceWidget.addStandardsLabel.isVisible());
		moreOptions.put("21stcentury", addQuestionResourceWidget.addCenturyLabel.isVisible());

		collectionQuestionItemDo.setMoreOptions(moreOptions);

		return collectionQuestionItemDo;
	}
	return null;
	}

	@Override
	public void questionMetadata(final CollectionQuestionItemDo collectionQuestionItemDo) {

		if(collectionQuestionItemDo!=null){

		Timer timer1=new Timer() {
			@Override
			public void run() {
				addQuestionResourceWidget.questionNameTextArea.setText(collectionQuestionItemDo.getQuestionText());
				addQuestionResourceWidget.explainationTextArea.setText(collectionQuestionItemDo.getExplanation());

				if(addQuestionResourceWidget.addExplanationLabel.isVisible()){addQuestionResourceWidget.setExplanationContainer();}
			}
		};
		timer1.schedule(0);

		if(collectionQuestionItemDo.getDepthOfKnowledgeIds()!=null){
			addQuestionResourceWidget.resetDepthOfKnowledges();
			addQuestionResourceWidget.depthOfKnowledgesList.clear();
			int checkBoxCount=0;
			for (Integer item : collectionQuestionItemDo.getDepthOfKnowledgeIds()) {
				   checkBoxCount++;
				}

			addQuestionResourceWidget.setDOKCheckBoxes();
			}

		TreeSet<QuestionHintsDo> hintset = new TreeSet<QuestionHintsDo>(collectionQuestionItemDo.getHints().get("hint"));
		TreeSet<QuestionHintsDo> hintsList = hintset;
		Iterator<QuestionHintsDo> iterator = hintsList.iterator();
		addQuestionResourceWidget.hintsContainer.clear();
		while (iterator.hasNext()) {
			QuestionHintsDo hints = iterator.next();
			int widgetCount=addQuestionResourceWidget.hintsContainer.getWidgetCount();
	        final AddHintsView addHints = new AddHintsView(widgetCount+1,hints.getHintText());
	        addQuestionResourceWidget.addHintsTextArea(addHints);
		}
		int count=addQuestionResourceWidget.hintsContainer.getWidgetCount();
		addQuestionResourceWidget.addHintsLabel.setText(i18n.GL3210_1()+i18n.GL_SPL_OPEN_SMALL_BRACKET()+(5-count)+i18n.GL3207_1()+i18n.GL_SPL_CLOSE_SMALL_BRACKET());

		Map<Long, String> centurySkills=collectionQuestionItemDo.getCenturySelectedValues();
		addQuestionResourceWidget.centuryPanel.clear();
	//	addQuestionResourceWidget.standardsPanel.clear();
		addQuestionResourceWidget.standardsDo.clear();
		for (Map.Entry<Long, String> entry : centurySkills.entrySet())
		{
			CodeDo codeDo=new CodeDo();
			codeDo.setDepth((short) 2);
			codeDo.setLabel(entry.getValue());
			codeDo.setCodeId(entry.getKey()!=null?entry.getKey().intValue():0);
			addQuestionResourceWidget.standardsDo.add(codeDo);
			addQuestionResourceWidget.centurySelectedValues.put(entry.getKey(),entry.getValue());
			addQuestionResourceWidget.centuryPanel.add(addQuestionResourceWidget.create21CenturyLabel(entry.getValue(), entry.getKey()+"", addQuestionResourceWidget.centuryCodesMap.get(entry.getKey())));
		}
		HashMap<String,ArrayList<CodeDo>> taxonomySet = new HashMap<String,ArrayList<CodeDo>>();
		taxonomySet.put("taxonomyCode", addQuestionResourceWidget.standardsDo);
		collectionQuestionItemDo.setTaxonomySet(taxonomySet);

		HashMap<String,Boolean> moreOptions= collectionQuestionItemDo.getMoreOptions();

		addQuestionResourceWidget.addExplanationLabel.setVisible(moreOptions.get("explanation"));
		addQuestionResourceWidget.addHintsLabel.setVisible(moreOptions.get("hints"));
		addQuestionResourceWidget.addDepthOfKnowledgeLabel.setVisible(moreOptions.get("DOK"));
		addQuestionResourceWidget.addStandardsLabel.setVisible(moreOptions.get("standards"));
		addQuestionResourceWidget.addCenturyLabel.setVisible(moreOptions.get("21stcentury"));

		addQuestionResourceWidget.setAncTabs();


		if(addQuestionResourceWidget.addDepthOfKnowledgeLabel.isVisible()){addQuestionResourceWidget.setDepthOfKnowledgeContainer();}
		if(addQuestionResourceWidget.addHintsLabel.isVisible()){addQuestionResourceWidget.setHintsContainer();}
		//if(addQuestionResourceWidget.addStandardsLabel.isVisible()){addQuestionResourceWidget.setStandardsContainer();}
		if(addQuestionResourceWidget.addCenturyLabel.isVisible()){addQuestionResourceWidget.setCenturyContainer();}
	}
	}

	@Override
	public boolean checkQuestionSlot() {

		return tabViewContainer.isVisible();

	}
	public void disableEditMode(Cursor cursorType){
		multiplechoice.getElement().getStyle().setCursor(cursorType);
		multipleAnswerAnc.getElement().getStyle().setCursor(cursorType);
		truefalase.getElement().getStyle().setCursor(cursorType);
		truefalseText.getElement().getStyle().setCursor(cursorType);
		openended.getElement().getStyle().setCursor(cursorType);
		hotTextRO.getElement().getStyle().setCursor(cursorType);
		hotTextHL.getElement().getStyle().setCursor(cursorType);
		hotSpotImg.getElement().getStyle().setCursor(cursorType);
		hotSpotWord.getElement().getStyle().setCursor(cursorType);

		questionTabButton.getElement().getStyle().setCursor(cursorType);
		multipleAnswerTabButton.getElement().getStyle().setCursor(cursorType);
		trueOrFlaseButton.getElement().getStyle().setCursor(cursorType);
		fillInTheBlankTabButton.getElement().getStyle().setCursor(cursorType);
		openEndedButton.getElement().getStyle().setCursor(cursorType);
		hotTextHLTabButton.getElement().getStyle().setCursor(cursorType);
		hotTextROTabButton.getElement().getStyle().setCursor(cursorType);
		hotSpotImageTabButton.getElement().getStyle().setCursor(cursorType);
		hotSpotWordTabButton.getElement().getStyle().setCursor(cursorType);
	}
	@Override
	public void setDepthOfKnowledges(List<ListValuesDo> result){
		if(addQuestionResourceWidget!=null){
			addQuestionResourceWidget.setDepthOfKnowledes(result);
		}
	}

	@Override
	public void setUpdatedStandardsCode(String setStandardsVal, int id, String desc, boolean val,
			boolean isUserOwnResource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySelectedStandards(List<Map<String, String>> standListArray) {
		try
		{
		if(addWebResourceWidget!=null){
		addWebResourceWidget.displaySelectedStandards(standListArray);}
		}catch(Exception e){}
		try
		{
		if(addUserOwnResourceWidget!=null){
		addUserOwnResourceWidget.displaySelectedStandards(standListArray);}
		}catch(Exception e){}
		try
		{
		if(addQuestionResourceWidget!=null){
		addQuestionResourceWidget.displaySelectedStandards(standListArray);}
		}catch(Exception e){}
	}
	
}
