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
package org.ednovo.gooru.client.mvp.assessments.play.resource.flag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ContentReportDo;
import org.ednovo.gooru.client.mvp.assessments.play.collection.flag.FlagThankYouPopUpView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class AssessmentsResourceFlagView extends PopupViewWithUiHandlers<AssessmentsResourceFlagUiHandler> implements IsAssessmentsResourceFlag {

	private static FlaggingPopUpUiBinder uiBinder = GWT
			.create(FlaggingPopUpUiBinder.class);

	interface FlaggingPopUpUiBinder extends
			UiBinder<Widget, AssessmentsResourceFlagView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField HTMLEventPanel closeButton;
	@UiField Button cancelButton,submitButton,submitButtonGray;
	@UiField TextArea descriptionTextArea;
	@UiField CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
	@UiField HTML titleText;
	@UiField Label flagText,provideMoreText;
	@UiField InlineLabel  inappropriateText,unavailableText,inaccurateText,otherReasonText;
	private FlagThankYouPopUpView thankYouToolTip=null;
	int formateSize=0;

	ArrayList<String> contentReportList=new ArrayList<String>();


	public PopupPanel appPopUp;
	private String resourceTitle;
    private String resourceGooruId,collectionItemId;
	private String deleteContentReportGooruOids="";

	@Inject
	public AssessmentsResourceFlagView(EventBus eventBus) {
		super(eventBus);
		appPopUp = new FlagPopupPanel(false);
		appPopUp.setWidget(uiBinder.createAndBindUi(this));
		cancelButton.setText(i18n.GL0608());
		cancelButton.getElement().setAttribute("id", "cancelButton");
		cancelButton.getElement().setAttribute("alt",i18n.GL0608());
		cancelButton.getElement().setAttribute("title",i18n.GL0608());

		submitButton.setText(i18n.GL0486());
		submitButton.getElement().setAttribute("id", "SubmitButton");
		submitButton.getElement().setAttribute("alt",i18n.GL0486());
		submitButton.getElement().setAttribute("title",i18n.GL0486());

		submitButtonGray.setText(i18n.GL0486());
		submitButtonGray.getElement().setId("btnSubmitButtonGray");
		submitButtonGray.getElement().setAttribute("alt",i18n.GL0486());
		submitButtonGray.getElement().setAttribute("title",i18n.GL0486());
		submitButtonGray.getElement().getStyle().setColor("#999");
		submitButtonGray.setVisible(true);
		submitButton.setVisible(false);
		flagText.setText(i18n.GL0600());
		flagText.getElement().setId("lblFlagText");
		flagText.getElement().setAttribute("alt",i18n.GL0600());
		flagText.getElement().setAttribute("title",i18n.GL0600());

		inappropriateText.setText(i18n.GL0612());
		inappropriateText.getElement().setId("lblInappropriateText");
		inappropriateText.getElement().setAttribute("alt",i18n.GL0612());
		inappropriateText.getElement().setAttribute("title",i18n.GL0612());

		provideMoreText.getElement().getStyle().setMarginTop(25, Unit.PX);

		inaccurateText.setText(i18n.GL0614());
		inaccurateText.getElement().setId("lblInaccurateText");
		inaccurateText.getElement().setAttribute("alt",i18n.GL0614());
		inaccurateText.getElement().setAttribute("title",i18n.GL0614());

		unavailableText.setText(i18n.GL0613());
		unavailableText.getElement().setId("lblUnavailableText");
		unavailableText.getElement().setAttribute("alt",i18n.GL0613());
		unavailableText.getElement().setAttribute("title",i18n.GL0613());

		otherReasonText.setText(i18n.GL0606());
		otherReasonText.getElement().setId("lblOtherReasonText");
		otherReasonText.getElement().setAttribute("alt",i18n.GL0606());
		otherReasonText.getElement().setAttribute("title",i18n.GL0606());

		provideMoreText.setText(i18n.GL0607());
		provideMoreText.getElement().setId("lblProvideMoreText");
		provideMoreText.getElement().setAttribute("alt",i18n.GL0607());
		provideMoreText.getElement().setAttribute("title",i18n.GL0607());

		closeButton.getElement().setId("epnlCloseButton");
		titleText.getElement().setId("htmlTitleText");
		checkBox4.getElement().setId("chkCheckBox4");
		checkBox3.getElement().setId("chkCheckBox3");
		checkBox2.getElement().setId("chkCheckBox2");
		checkBox1.getElement().setId("chkCheckBox1");
		descriptionTextArea.getElement().setId("tatDescriptionTextArea");
		StringUtil.setAttributes(descriptionTextArea, true);
		closeButton.addClickHandler(new CloseFlagPopupEvent());
	}
	@UiHandler("cancelButton")
	public void onClickOfCancelButton(ClickEvent event){
		resetFlagData();
	}
	@UiHandler("checkBox1")
	public void onClickOfcheckBox1(ClickEvent event)
	{
	if(checkBox1.getValue()||checkBox2.getValue()||checkBox3.getValue()||checkBox4.getValue())
		{
			submitButtonGray.setVisible(false);
			submitButton.setVisible(true);
		}
		else
		{
			submitButtonGray.setVisible(true);
			submitButton.setVisible(false);
		}

	}
	@UiHandler("checkBox2")
	public void onClickOfcheckBox2(ClickEvent event)
	{
		if(checkBox2.getValue()||checkBox3.getValue()||checkBox4.getValue()||checkBox1.getValue()){
			submitButtonGray.setVisible(false);
			submitButton.setVisible(true);
		}
		else{
			submitButtonGray.setVisible(true);
			submitButton.setVisible(false);
		}

	}
	@UiHandler("checkBox3")
	public void onClickOfcheckBox3(ClickEvent event)
	{
		if(checkBox3.getValue()||checkBox4.getValue()||checkBox2.getValue()||checkBox1.getValue()){
			submitButtonGray.setVisible(false);
			submitButton.setVisible(true);
		}
		else{
			submitButtonGray.setVisible(true);
			submitButton.setVisible(false);
		}

	}

	@UiHandler("checkBox4")
	public void onClickOfcheckBox4(ClickEvent event)
	{
		if(checkBox4.getValue()||checkBox3.getValue()||checkBox2.getValue()||checkBox1.getValue()){
			submitButtonGray.setVisible(false);
			submitButton.setVisible(true);
		}
		else{
			submitButtonGray.setVisible(true);
			submitButton.setVisible(false);
		}

	}

	@UiHandler("submitButton")
	public void onClicksubmitButton(ClickEvent event){
		if(checkBox1.getValue())
		{
			contentReportList.add("missing-concept");
		}
		if(checkBox2.getValue())
		{
			contentReportList.add("not-loading");
		}
		if(checkBox3.getValue())
		{
			contentReportList.add("inappropriate");
		}
		if(checkBox4.getValue())
		{
			contentReportList.add("other");
		}
		getUiHandlers().createReport(resourceGooruId, descriptionTextArea.getText(), contentReportList, deleteContentReportGooruOids,collectionItemId);
	}

	@Override
	public void showSuccessPopup() {
		resetFlagData();
		appPopUp.hide();
		thankYouToolTip=new FlagThankYouPopUpView();
		thankYouToolTip.getOkButton().addClickHandler(new CloseFlagPopupEvent());
		thankYouToolTip.getCloseButton().addClickHandler(new CloseFlagPopupEvent());
		thankYouToolTip.show();
		thankYouToolTip.center();
		submitButtonGray.setVisible(true);
		submitButton.setVisible(false);

	}
	public void getThankYouPopUp(){
		resetFlagData();
		appPopUp.hide();
		FlagThankYouPopUpView thankYouToolTip=new FlagThankYouPopUpView();
		thankYouToolTip.show();
		thankYouToolTip.center();
		submitButtonGray.setVisible(true);
		submitButton.setVisible(false);
	}
	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	@Override
	public void setFlagView(CollectionItemDo collectionItemDo) {
		if(collectionItemDo!=null){
		resourceTitle=collectionItemDo.getResourceTitle();
		if(collectionItemDo.getResource()!=null){
		resourceGooruId=collectionItemDo.getResource().getGooruOid();
		}
		collectionItemId=collectionItemDo.getCollectionItemId();
		if(!StringUtil.isEmpty(resourceTitle)){
		resourceTitle=resourceTitle.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
		titleText.setHTML(i18n.GL1430() +resourceTitle+" \" "+i18n.GL1431()+"");
		titleText.getElement().setAttribute("alt",i18n.GL1430() +resourceTitle+" \" "+i18n.GL1431()+"");
		titleText.getElement().setAttribute("title",i18n.GL1430() +resourceTitle+" \" "+i18n.GL1431()+"");
		}
		}
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
	public void resetFlagData(){
		checkBox1.setValue(false);
		checkBox2.setValue(false);
		checkBox3.setValue(false);
		checkBox4.setValue(false);
		descriptionTextArea.setText("");
		descriptionTextArea.getElement().setAttribute("alt","");
		descriptionTextArea.getElement().setAttribute("title","");
		submitButtonGray.setVisible(true);
		submitButton.setVisible(false);
		contentReportList.clear();
		deleteContentReportGooruOids="";
	}
	@Override
	public void getreportData(ContentReportDo contentReportDo,String gooruFlagId) {
		deleteContentReportGooruOids=gooruFlagId;
	}
	@Override
	public HTMLEventPanel resourceCloseButton() {
		return closeButton;
	}
	@Override
	public Button getSubmitButton() {
		return submitButton;
	}

	private class CloseFlagPopupEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			if(thankYouToolTip!=null){
				thankYouToolTip.hide();
			}
			hide();
			hideResourceFlagPopup();
		}
	}
	public void hideResourceFlagPopup(){
		resetFlagData();
		String viewToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
		String resourceId=AppClientFactory.getPlaceManager().getRequestParameter("rid", null);

		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionId);
		params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);
		params.put("rid", resourceId);
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(viewToken, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	private class FlagPopupPanel extends PopupPanel{
		public FlagPopupPanel(boolean isAutoHide){
			super(isAutoHide);
			this.setGlassEnabled(true);
			/*this.setStyleName("");*/
			this.getGlassElement().getStyle().setZIndex(99999);
			this.getElement().getStyle().setZIndex(99999);
		}
	}
	@Override
	public void setContentDeleteIds(String gooruFlagId) {
		this.deleteContentReportGooruOids=gooruFlagId;
	}


}
