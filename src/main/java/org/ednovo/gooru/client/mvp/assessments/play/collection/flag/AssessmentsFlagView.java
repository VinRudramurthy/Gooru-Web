package org.ednovo.gooru.client.mvp.assessments.play.collection.flag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ContentReportDo;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.settings.CustomAnimation;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @fileName : CollectionFlagView.java
 *
 * @description :This file is responsible to show view based on
 *              CollectionFlagPopUp.ui.xml for flag in the collection and
 *              resources
 *
 * @version :5.8
 *
 * @date: aug 30 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer
 *
 */
public class AssessmentsFlagView extends
		PopupViewWithUiHandlers<AssessmentsFlagUiHandler> implements
		IsAssessmentsFlagView,ClientConstants {
	private static CollectionFlagPopUpUiBinder uiBinder = GWT
			.create(CollectionFlagPopUpUiBinder.class);

	interface CollectionFlagPopUpUiBinder extends
			UiBinder<Widget, AssessmentsFlagView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	HTMLEventPanel closeButton,flagCollections, flagResources;

	@UiField Button collectionCancelButton,collectionSubmitButton,submitButtonGray;

	@UiField
	HTMLPanel resourceFlagContainer, collectionFlagContainer,
			dropdownListContainer,flagresourceleftpart;
	@UiField
	CheckBox resourceCheckBox1, resourceCheckBox2, resourceCheckBox3,
			resourceCheckBox4, collectionCheckBox1, collectionCheckBox2,
			collectionCheckBox3, collectionCheckBox4;
	@UiField
	TextArea resourceDescTextArea, collectionDescTextArea;
	@UiField
	HTML collectionTitleField;
	@UiField
	Image popUpCloseButton;
	@UiField
	HTML dropdownListPlaceHolder;
	@UiField
	ScrollPanel dropdownListContainerScrollPanel;
	@UiField
	InlineLabel otherReasonText,inaccurateText,notAppropriateText,incorporateText,incorporateresourceText,unavailableresourceText,inaccurateTextresource,otherReason;
	@UiField Label headerflagtext,provideMoreDetails,chooseResourceText,becauseText,provideMore;
	@UiField Anchor flagCollectionText,flagResourceText;
	HTMLEventPanel flagButtonOnCover = new HTMLEventPanel("");
	HTMLEventPanel flagButtonOnSummary = new HTMLEventPanel("");

	String collectionGooruOid = "";
	String getDeleteContentGooruOid="";
	String getDeleteContentResourceGooruOid="";
	private PopupPanel appPopUp;
	private boolean isClickedOnDropDwn=false;
	CollectionItemDo collectionItemDo;
	String collectionTitle="";
	ArrayList<String> resourcesList = new ArrayList<String>();
	ArrayList<String> resourcesListId = new ArrayList<String>();
	ArrayList<String> contentReportList=new ArrayList<String>();
	ArrayList<String> reourceContentReportList=new ArrayList<String>();
	private FlagThankYouPopUpView flagThankYouPopUpView=null;
	@Inject
	public AssessmentsFlagView(EventBus eventBus) {
		super(eventBus);
		appPopUp = new FlagPopupPanel(false);
		appPopUp.setWidget(uiBinder.createAndBindUi(this));
		popUpCloseButton.addClickHandler(new CloseFlagPopupEvent());
		headerflagtext.setText(i18n.GL0600());
		headerflagtext.getElement().setId("lblHeaderflagtext");
		headerflagtext.getElement().setAttribute("alt",i18n.GL0600());
		headerflagtext.getElement().setAttribute("title",i18n.GL0600());
		closeButton.getElement().getStyle().setCursor(Cursor.POINTER);
		provideMoreDetails.getElement().getStyle().setMarginTop(25, Unit.PX);
		provideMore.getElement().getStyle().setMarginTop(25, Unit.PX);

		flagCollectionText.getElement().setInnerHTML(i18n.GL3494());
		flagCollectionText.getElement().setId("pnlFlagCollectionText");
		flagCollectionText.getElement().setAttribute("alt",i18n.GL0601());
		flagCollectionText.getElement().setAttribute("title",i18n.GL0601());

		flagResourceText.getElement().setInnerHTML(i18n.GL0601());
		flagResourceText.getElement().setInnerHTML(i18n.GL0602());
		flagResourceText.getElement().setId("pnlFlagResourceText");
		flagResourceText.getElement().setAttribute("alt",i18n.GL0602());
		flagResourceText.getElement().setAttribute("title",i18n.GL0602());

		incorporateText.setText(i18n.GL3495());
		incorporateText.getElement().setId("lblIncorporateText");
		incorporateText.getElement().setAttribute("alt",i18n.GL3495());
		incorporateText.getElement().setAttribute("title",i18n.GL3495());

		notAppropriateText.setText(i18n.GL3496());
		notAppropriateText.getElement().setId("lblNotAppropriateText");
		notAppropriateText.getElement().setAttribute("alt",i18n.GL3496());
		notAppropriateText.getElement().setAttribute("title",i18n.GL3496());

		inaccurateText.setText(i18n.GL3497());
		inaccurateText.getElement().setId("lblInaccurateText");
		inaccurateText.getElement().setAttribute("alt",i18n.GL3497());
		inaccurateText.getElement().setAttribute("title",i18n.GL3497());

		otherReasonText.setText(i18n.GL0606());
		otherReasonText.getElement().setId("lblOtherReasonText");
		otherReasonText.getElement().setAttribute("alt",i18n.GL0606());
		otherReasonText.getElement().setAttribute("title",i18n.GL0606());

		provideMoreDetails.setText(i18n.GL0607());
		provideMoreDetails.getElement().setId("lblProvideMoreDetails");
		provideMoreDetails.getElement().setAttribute("alt",i18n.GL0607());
		provideMoreDetails.getElement().setAttribute("title",i18n.GL0607());

		collectionCancelButton.getElement().setInnerHTML(i18n.GL0608());
		collectionCancelButton.getElement().setId("epnlCollectionCancelButton");
		collectionCancelButton.getElement().setAttribute("alt",i18n.GL0608());
		collectionCancelButton.getElement().setAttribute("title",i18n.GL0608());

		collectionSubmitButton.getElement().setInnerHTML(i18n.GL0486());
		collectionSubmitButton.getElement().setId("epnlCollectionSubmitButton");
		collectionSubmitButton.getElement().setAttribute("alt",i18n.GL0486());
		collectionSubmitButton.getElement().setAttribute("title",i18n.GL0486());

		submitButtonGray.getElement().setInnerHTML(i18n.GL0486());
		submitButtonGray.getElement().setId("epnlSubmitButtonGray");
		submitButtonGray.getElement().setAttribute("alt",i18n.GL0486());
		submitButtonGray.getElement().setAttribute("title",i18n.GL0486());
		submitButtonGray.getElement().getStyle().setColor("#999");

		chooseResourceText.setText(i18n.GL0609());
		chooseResourceText.getElement().setId("lblChooseResourceText");
		chooseResourceText.getElement().setAttribute("alt",i18n.GL0609());
		chooseResourceText.getElement().setAttribute("title",i18n.GL0609());

		dropdownListPlaceHolder.getElement().setInnerHTML(i18n.GL0610());
		dropdownListPlaceHolder.getElement().setId("htmlDropdownListPlaceHolder");
		dropdownListPlaceHolder.getElement().setAttribute("alt",i18n.GL0610());
		dropdownListPlaceHolder.getElement().setAttribute("title",i18n.GL0610());

		becauseText.setText(i18n.GL0611());
		becauseText.getElement().setId("lblBecauseText");
		becauseText.getElement().setAttribute("alt",i18n.GL0611());
		becauseText.getElement().setAttribute("title",i18n.GL0611());

		incorporateresourceText.setText(i18n.GL0612());
		incorporateresourceText.getElement().setId("lblIncorporateresourceText");
		incorporateresourceText.getElement().setAttribute("alt",i18n.GL0612());
		incorporateresourceText.getElement().setAttribute("title",i18n.GL0612());

		unavailableresourceText.setText(i18n.GL0613());
		unavailableresourceText.getElement().setId("lblUnavailableresourceText");
		unavailableresourceText.getElement().setAttribute("alt",i18n.GL0613());
		unavailableresourceText.getElement().setAttribute("title",i18n.GL0613());

		inaccurateTextresource.setText(i18n.GL0614());
		inaccurateTextresource.getElement().setId("lblInaccurateTextresource");
		inaccurateTextresource.getElement().setAttribute("alt",i18n.GL0614());
		inaccurateTextresource.getElement().setAttribute("title",i18n.GL0614());

		otherReason.setText(i18n.GL0606());
		otherReason.getElement().setId("lblOtherReason");
		otherReason.getElement().setAttribute("alt",i18n.GL0606());
		otherReason.getElement().setAttribute("title",i18n.GL0606());

		provideMore.setText(i18n.GL0607());
		provideMore.getElement().setId("lblProvideMore");
		provideMore.getElement().setAttribute("alt",i18n.GL0607());
		provideMore.getElement().setAttribute("title",i18n.GL0607());

		resourceFlagContainer.setVisible(false);
		submitButtonGray.setVisible(true);
		collectionSubmitButton.setVisible(false);
		dropdownListContainerScrollPanel.getElement().getStyle().setDisplay(Display.NONE);
		dropdownListPlaceHolder
		.addClickHandler(new OnDropdownListPlaceHolderClick());

		closeButton.getElement().setId("epnlCloseButton");
		popUpCloseButton.getElement().setId("imgPopUpCloseButton");
		flagresourceleftpart.getElement().setId("pnlFlagresourceleftpart");
		flagCollections.getElement().setId("epnlFlagCollections");
		flagResources.getElement().setId("epnlFlagResources");
		resourceFlagContainer.getElement().setId("pnlResourceFlagContainer");
		dropdownListContainerScrollPanel.getElement().setId("sbDropdownListContainerScrollPanel");
		dropdownListContainer.getElement().setId("pnlDropdownListContainer");
		resourceCheckBox4.getElement().setId("chkResourceCheckBox4");
		resourceCheckBox3.getElement().setId("chkResourceCheckBox3");
		resourceCheckBox2.getElement().setId("chkResourceCheckBox2");
		resourceCheckBox1.getElement().setId("chkResourceCheckBox1");
		resourceDescTextArea.getElement().setId("tatResourceDescTextArea");
		StringUtil.setAttributes(resourceDescTextArea, true);
		collectionFlagContainer.getElement().setId("pnlCollectionFlagContainer");
		collectionTitleField.getElement().setId("htmlCollectionTitleField");
		collectionCheckBox4.getElement().setId("chkCollectionCheckBox4");
		collectionCheckBox3.getElement().setId("chkCollectionCheckBox3");
		collectionCheckBox2.getElement().setId("chkCollectionCheckBox2");
		collectionCheckBox1.getElement().setId("chkCollectionCheckBox1");
		collectionDescTextArea.getElement().setId("tatCollectionDescTextArea");
		StringUtil.setAttributes(collectionDescTextArea, true);

		ClickHandler flagHandler= new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(!isClickedOnDropDwn){
					if(!dropdownListContainerScrollPanel.getElement().getStyle().getProperty("display").equals(NONE)){
						new CustomAnimation(dropdownListContainerScrollPanel).run(400);
					}
				}else{
					isClickedOnDropDwn=false;
				}

			}
		};
		RootPanel.get().addDomHandler(flagHandler, ClickEvent.getType());
	}

	public void displayView(String collectionTitle,ArrayList<String> resourcesList,ArrayList<String> resourcesListId) {

		AssessmentsFlagBundle.IMAGEBUNDLEINSTANCE.flagstyle().ensureInjected();
//		AddResourceToCollectionStylesBundle.IMAGEBUNDLEINSTANCE
//				.addResourceToCollectionStyles().ensureInjected();
		popUpCloseButton.setResource(AssessmentsFlagBundle.IMAGEBUNDLEINSTANCE
				.closeFlagPopUpImages());

		if(collectionTitle!=null){
			collectionTitle = collectionTitle.replaceAll("</p>", " ")
					.replaceAll("<p>", "")
					.replaceAll("<br data-mce-bogus=\"1\">", "")
					.replaceAll("<br>", "").replaceAll("</br>", "");
			collectionTitleField.setHTML(i18n.GL1430()+" "+ collectionTitle+ " \" "+i18n.GL1431()+"");
			collectionTitleField.getElement().setAttribute("alt",i18n.GL1430()+" "+ collectionTitle+ " \" "+i18n.GL1431()+"");
			collectionTitleField.getElement().setAttribute("title",i18n.GL1430()+" "+ collectionTitle+ " \" "+i18n.GL1431()+"");
		}
		flagCollections.addStyleName("flagbuttonDeSelected");
		// To get content report
		getUiHandlers().getContentReport(collectionGooruOid);
	}

	private class OnDropdownItemClick implements ClickHandler {

		private String Resourcetitle = "";
		private String resourceId = "";
		private int resourceItemSize;
		private String collectionItemId="";

		public OnDropdownItemClick(String Resourcetitle, String resourceId,String collectionItemId, int resourceItemSize) {
			this.Resourcetitle = Resourcetitle;
			this.resourceId = resourceId;
			this.resourceItemSize = resourceItemSize;
			this.collectionItemId=collectionItemId;
		}
		@Override
		public void onClick(ClickEvent event) {
			dropdownListPlaceHolder.setHTML(Resourcetitle);
			dropdownListPlaceHolder.getElement().setAttribute("id", resourceId);
			dropdownListPlaceHolder.getElement().setAttribute("cid", collectionItemId);
			dropdownListPlaceHolder.getElement().setAttribute("itemsSize",
					"" + resourceItemSize);

			getDeleteContentResourceGooruOid=getUiHandlers().getResourceContentReport(dropdownListPlaceHolder.getElement().getId());
			new CustomAnimation(dropdownListContainerScrollPanel).run(400);
		}
	}

	private class OnDropdownListPlaceHolderClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			isClickedOnDropDwn=true;
			new CustomAnimation(dropdownListContainerScrollPanel).run(400);
		}
	}

	@UiHandler("collectionCancelButton")
	public void onClickOfCollectionCancelButton(ClickEvent event) {
		clearAll();
	}

	@UiHandler("collectionCheckBox1")
	public void onClickOfcollectionCheckBox1(ClickEvent event) {
		if (collectionCheckBox1.getValue() || collectionCheckBox2.getValue()
				|| collectionCheckBox3.getValue()
				|| collectionCheckBox4.getValue()
				|| resourceCheckBox1.getValue()
				|| resourceCheckBox2.getValue()
				|| resourceCheckBox3.getValue()
				|| resourceCheckBox4.getValue()) {
			submitButtonGray.setVisible(false);
			collectionSubmitButton.setVisible(true);
		} else {
			submitButtonGray.setVisible(true);
			collectionSubmitButton.setVisible(false);
		}
	}

	// }
	@UiHandler("collectionCheckBox2")
	public void onClickOfcollectionCheckBox2(ClickEvent event) {
		if (collectionCheckBox1.getValue() || collectionCheckBox2.getValue()
				|| collectionCheckBox3.getValue()
				|| collectionCheckBox4.getValue()
				|| resourceCheckBox1.getValue()
				|| resourceCheckBox2.getValue()
				|| resourceCheckBox3.getValue()
				|| resourceCheckBox4.getValue()) {
			submitButtonGray.setVisible(false);
			collectionSubmitButton.setVisible(true);
		} else {
			submitButtonGray.setVisible(true);
			collectionSubmitButton.setVisible(false);
		}
	}

	@UiHandler("collectionCheckBox3")
	public void onClickOfcollectionCheckBox3(ClickEvent event) {
		if (collectionCheckBox1.getValue() || collectionCheckBox2.getValue()
				|| collectionCheckBox3.getValue()
				|| collectionCheckBox4.getValue()
				|| resourceCheckBox1.getValue()
				|| resourceCheckBox2.getValue()
				|| resourceCheckBox3.getValue()
				|| resourceCheckBox4.getValue()) {
			submitButtonGray.setVisible(false);
			collectionSubmitButton.setVisible(true);
		} else {
			submitButtonGray.setVisible(true);
			collectionSubmitButton.setVisible(false);
		}

	}

	@UiHandler("collectionCheckBox4")
	public void onClickOfcollectionCheckBox4(ClickEvent event) {
		if (collectionCheckBox1.getValue() || collectionCheckBox2.getValue()
				|| collectionCheckBox3.getValue()
				|| collectionCheckBox4.getValue()
				|| resourceCheckBox1.getValue()
				|| resourceCheckBox2.getValue()
				|| resourceCheckBox3.getValue()
				|| resourceCheckBox4.getValue()) {
			submitButtonGray.setVisible(false);
			collectionSubmitButton.setVisible(true);
		} else {
			submitButtonGray.setVisible(true);
			collectionSubmitButton.setVisible(false);
		}
	}
	@UiHandler("resourceCheckBox1")
	public void onClickOfresourceCheckBox1(ClickEvent event)
	{
		if(collectionCheckBox1.getValue()||collectionCheckBox2.getValue()||collectionCheckBox3.getValue()||collectionCheckBox4.getValue()||resourceCheckBox1.getValue()||resourceCheckBox2.getValue()||resourceCheckBox3.getValue()||resourceCheckBox4.getValue())
		{
			submitButtonGray.setVisible(false);
			collectionSubmitButton.setVisible(true);
		}
		else
		{
			submitButtonGray.setVisible(true);
			collectionSubmitButton.setVisible(false);
		}
	}

	@UiHandler("resourceCheckBox2")
	public void onClickOfresourceCheckBox2(ClickEvent event)
	{
		if(collectionCheckBox1.getValue()||collectionCheckBox2.getValue()||collectionCheckBox3.getValue()||collectionCheckBox4.getValue()||resourceCheckBox1.getValue()||resourceCheckBox2.getValue()||resourceCheckBox3.getValue()||resourceCheckBox4.getValue())
			{
				submitButtonGray.setVisible(false);
				collectionSubmitButton.setVisible(true);
			}
			else
			{
				submitButtonGray.setVisible(true);
				collectionSubmitButton.setVisible(false);
			}
		}


	@UiHandler("resourceCheckBox3")
	public void onClickOfresourceCheckBox3(ClickEvent event)
	{
		if(collectionCheckBox1.getValue()||collectionCheckBox2.getValue()||collectionCheckBox3.getValue()||collectionCheckBox4.getValue()||resourceCheckBox1.getValue()||resourceCheckBox2.getValue()||resourceCheckBox3.getValue()||resourceCheckBox4.getValue())
			{
				submitButtonGray.setVisible(false);
				collectionSubmitButton.setVisible(true);
			}	else
			{
				submitButtonGray.setVisible(true);
				collectionSubmitButton.setVisible(false);
			}
		}

	@UiHandler("resourceCheckBox4")
	public void onClickOfresourceCheckBox4(ClickEvent event)
	{
		if(collectionCheckBox1.getValue()||collectionCheckBox2.getValue()||collectionCheckBox3.getValue()||collectionCheckBox4.getValue()||resourceCheckBox1.getValue()||resourceCheckBox2.getValue()||resourceCheckBox3.getValue()||resourceCheckBox4.getValue())
			{
				submitButtonGray.setVisible(false);
				collectionSubmitButton.setVisible(true);
			}
			else
			{
				submitButtonGray.setVisible(true);
				collectionSubmitButton.setVisible(false);
			}
			}

	@UiHandler("flagCollections")
	public void onClickOfflagCollections(ClickEvent event) {
		callFlagThisCollection();
	}
	public void callFlagThisCollection(){
		flagResources.removeStyleName("flagbuttonDeSelected");
		flagCollections.addStyleName("flagbuttonDeSelected");
		collectionFlagContainer.setVisible(true);
		resourceFlagContainer.setVisible(false);
	}
	@UiHandler("flagResources")
	public void onClickOfflagResources(ClickEvent event) {
		flagResources.addStyleName("flagbuttonDeSelected");

		flagCollections.removeStyleName("flagbuttonDeSelected");

		collectionFlagContainer.setVisible(false);
		resourceFlagContainer.setVisible(true);
	}

	@Override
	public void reset() {

	}

	@Override
	public void onLoad() {

	}

	@Override
	public void onUnload() {

	}

	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	@Override
	public void getDisplayData(CollectionDo collectionDo) {
		callFlagThisCollection();
		dropdownListPlaceHolder.getElement().setInnerHTML(i18n.GL0610());
		collectionTitle = collectionDo.getTitle();
		collectionGooruOid=collectionDo.getGooruOid();
		dropdownListContainer.clear();
		resourcesList.clear();
		resourcesListId.clear();
		if (collectionDo.getCollectionItems()!=null&&collectionDo.getCollectionItems().size() > 0) {
			for (CollectionItemDo collectionItem : collectionDo.getCollectionItems()) {
				resourcesList.add(collectionItem.getResourceTitle()!=null?collectionItem.getResourceTitle():"");
				resourcesListId.add(collectionItem.getResource().getGooruOid());
				String Resourcetitle = (collectionItem.getResourceTitle()!=null?collectionItem.getResourceTitle():"").replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
				HTML dropDownListItemTitle=new HTML();
				dropDownListItemTitle.setHTML(Resourcetitle);
				dropDownListItemTitle.setStyleName(AssessmentsFlagBundle.IMAGEBUNDLEINSTANCE.flagstyle().dropdownListItemContainer());

				dropdownListContainer.add(dropDownListItemTitle);
				dropDownListItemTitle.addClickHandler(new OnDropdownItemClick(Resourcetitle,collectionItem.getResource().getGooruOid(),collectionItem.getCollectionItemId(),resourcesList.size()));
				dropdownListPlaceHolder.getElement().setAttribute("id", collectionItem.getResource().getGooruOid());
				dropdownListPlaceHolder.getElement().setAttribute("cid", collectionItem.getCollectionItemId());
			}
		}
		displayView(collectionTitle,resourcesList,resourcesListId);
	}
	@UiHandler("collectionSubmitButton")
	public void onClickOfcollectionSubmitButton(ClickEvent event){
			//For Resource Flag
			if(resourceCheckBox1.getValue())
			{
				reourceContentReportList.add("missing-concept");
			}
			if(resourceCheckBox2.getValue())
			{
				reourceContentReportList.add("not-loading");
			}
			if(resourceCheckBox3.getValue())
			{
				reourceContentReportList.add("inappropriate");
			}
			if(resourceCheckBox4.getValue())
			{
				reourceContentReportList.add("other");
			}
			//For Collection Flag

			if(collectionCheckBox1.getValue())
			{
				contentReportList.add("missing-concept");
			}
			if(collectionCheckBox2.getValue())
			{
				contentReportList.add("not-loading");
			}
			if(collectionCheckBox3.getValue())
			{
				contentReportList.add("inappropriate");
			}
			if(collectionCheckBox4.getValue())
			{
				contentReportList.add("other");
			}

			if(resourceCheckBox1.getValue()||resourceCheckBox2.getValue()||resourceCheckBox3.getValue()||resourceCheckBox4.getValue()){
				String collectionItemId=dropdownListPlaceHolder.getElement().getAttribute("cid");
				getUiHandlers().createCollectionContentReport(dropdownListPlaceHolder.getElement().getId(),resourceDescTextArea.getText(),reourceContentReportList,getDeleteContentResourceGooruOid,true,collectionItemId);
			}else{
				getUiHandlers().createCollectionContentReport(collectionGooruOid, collectionDescTextArea.getText(), contentReportList, getDeleteContentGooruOid,false,null);
			}
	}
	public void showSuccesmessagePopup(){
		appPopUp.hide();
		clearAll();
		flagThankYouPopUpView=new FlagThankYouPopUpView();
		flagThankYouPopUpView.getOkButton().addClickHandler(new CloseFlagPopupEvent());
		flagThankYouPopUpView.show();
		flagThankYouPopUpView.center();
	}
	public void clearAll()
	{
		resourceCheckBox1.setValue(false);
		resourceCheckBox2.setValue(false);
		resourceCheckBox3.setValue(false);
		resourceCheckBox4.setValue(false);
		resourceDescTextArea.setText("");
		collectionCheckBox1.setValue(false);
		collectionCheckBox2.setValue(false);
		collectionCheckBox3.setValue(false);
		collectionCheckBox4.setValue(false);
		collectionDescTextArea.setText("");
		submitButtonGray.setVisible(true);
		collectionSubmitButton.setVisible(false);
		contentReportList.clear();
		reourceContentReportList.clear();
	}

	@Override
	public void setDefaultView() {

	}

	@Override
	public void setFlag(ContentReportDo contentReportDo, String gooruFlagId) {
		getDeleteContentGooruOid=gooruFlagId;
	}

	@Override
	public Image getCloseButton() {
		return popUpCloseButton;
	}

	@Override
	public Button getSubmitButton() {
		return collectionSubmitButton;
	}

	private class CloseFlagPopupEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			if(flagThankYouPopUpView!=null){
				flagThankYouPopUpView.hide();
			}
			hide();
			hideResourceFlagPopup();
		}
	}
	public void hideResourceFlagPopup(){
		clearAll();
		String viewToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
		String viewParam=AppClientFactory.getPlaceManager().getRequestParameter("view", null);

		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionId);
		params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);
		if(viewParam != null)
		{
			params.put("view", viewParam);
		}
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(viewToken, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	private class FlagPopupPanel extends PopupPanel{
		public FlagPopupPanel(boolean isAutoHide){
			super(isAutoHide);
			this.setGlassEnabled(true);
		/*	this.setStyleName("");*/
			this.getGlassElement().getStyle().setZIndex(99999);
			this.getElement().getStyle().setZIndex(99999);
		}
	}
}
