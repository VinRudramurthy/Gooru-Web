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
package org.ednovo.gooru.client.mvp.assessments.play.resource.question;



import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.player.AnswerAttemptDo;
import org.ednovo.gooru.client.mvp.assessments.play.collection.event.AssessmentsNextResourceEvent;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.AttemptedAnswersDo;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public abstract class OpendEndedQuestionView extends Composite{

	@UiField HTMLPanel answetTextAfterSubmission,answertext;
	@UiField Button submitButton;
	@UiField TextArea openEndedAnswerTextArea;
	@UiField Label submittedText,errorMessageText,messageBodyText, lblCharLimit;
	@UiField FlowPanel questionsMainPanel;
	private String answerText="";
	private CollectionItemDo collectionItemDo;
	private boolean isCheckButtonEnabled=false;
	private static OpendEndedQuestionViewUiBinder uiBinder = GWT.create(OpendEndedQuestionViewUiBinder.class);

	interface OpendEndedQuestionViewUiBinder extends UiBinder<Widget, OpendEndedQuestionView> {

	}


	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public OpendEndedQuestionView(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo){
		initWidget(uiBinder.createAndBindUi(this));
		questionsMainPanel.getElement().setId("fpnlQuestionContainer");
		this.collectionItemDo=collectionItemDo;
		setQuestionTypeCaption();
		answertext.getElement().setInnerHTML(i18n.GL0665());
		answertext.getElement().setAttribute("alt",i18n.GL0665());
		answertext.getElement().setAttribute("title",i18n.GL0665());
		submitButton.setText(i18n.GL3460_4());
		submitButton.getElement().setAttribute("alt",i18n.GL3460_4());
		submitButton.getElement().setAttribute("title",i18n.GL3460_4());
		showPreviousAttemptResult(attemptedAnswerDo);

		String value = StringUtil.generateMessage(i18n.GL2103(), "1000");

		StringUtil.setAttributes(lblCharLimit.getElement(), "lblCharLimit", value, value);
		lblCharLimit.setText(value);
	}

	public void setQuestionTypeCaption(){
		messageBodyText.setText(i18n.GL1460());
		messageBodyText.getElement().setAttribute("alt",i18n.GL1460());
		messageBodyText.getElement().setAttribute("title",i18n.GL1460());
		answertext.getElement().setId("pnlAnswertext");
		submitButton.getElement().setId("btnSubmitButton");
		messageBodyText.getElement().setId("lblMessageBodyText");
		openEndedAnswerTextArea.getElement().setId("tatOpenEndedAnswerTextArea");
		StringUtil.setAttributes(openEndedAnswerTextArea, false);
		errorMessageText.getElement().setId("lblErrorMessageText");
		answetTextAfterSubmission.getElement().setId("pnlAnswetTextAfterSubmission");
		submittedText.getElement().setId("lblSubmittedText");
	}

	public void showPreviousAttemptResult(AttemptedAnswersDo attemptedAnswerDo){
		if(attemptedAnswerDo!=null){
			openEndedAnswerTextArea.setValue(StringUtil.isEmpty(attemptedAnswerDo.getAnswersText())?"":attemptedAnswerDo.getAnswersText());
		}
	}

	@UiHandler("openEndedAnswerTextArea")
	public void onKeypressTextArea(KeyUpEvent event){
		answerText=openEndedAnswerTextArea.getValue();
		setOeQuestionAnswerText(answerText);
		 if(!StringUtil.isEmpty(answerText)){
			 if(answerText.trim().length()==0){
				 disableSubmitButton();
			 }else{
				 enableSubmitButton();
			 }
			 if(answerText.trim().length()>=1000){
				 enableSubmitButton();
				 errorMessageText.setText(i18n.GL1458()+i18n.GL_SPL_FULLSTOP());
				 errorMessageText.getElement().setAttribute("alt",i18n.GL1458()+i18n.GL_SPL_FULLSTOP());
				 errorMessageText.getElement().setAttribute("title",i18n.GL1458()+i18n.GL_SPL_FULLSTOP());
				 event.preventDefault();
			 }else{
				 errorMessageText.setText("");
				 errorMessageText.getElement().setAttribute("alt","");
				 errorMessageText.getElement().setAttribute("title","");
			 }
			 if(answerText.length()>0){
				 isOeAnswerSubmited(false);
			 }else{
				 isOeAnswerSubmited(true);
			 }
		 }
		 isUserAnswerAttempted(true);
	}

	@UiHandler("submitButton")
	public void clickOnSubmitButton(ClickEvent clickEvent){
		if(isCheckButtonEnabled){
		isCheckButtonEnabled=false;
		submitButton.removeStyleName("openEndedQuestionSubmitButton");
		submitButton.addStyleName("hintsInActiveButton");
		if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY)){
			MixpanelUtil.ClickOpenEndedQuestionSubmitButtonFromCollectionPlayer();
		}else if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.RESOURCE_PLAY)){
			MixpanelUtil.ClickOpenEndedQuestionSubmitButtonFromResourcePlayer();
		}

		 answerText=openEndedAnswerTextArea.getValue();
		 if(!StringUtil.isEmpty(answerText)){
			 if(answerText.trim().length()>1000){
				 errorMessageText.setText(i18n.GL1458()+i18n.GL_SPL_FULLSTOP());
				 errorMessageText.getElement().setAttribute("alt",i18n.GL1458()+i18n.GL_SPL_FULLSTOP());
				 errorMessageText.getElement().setAttribute("title",i18n.GL1458()+i18n.GL_SPL_FULLSTOP());
			 }else{
				 errorMessageText.setText("");
				 errorMessageText.getElement().setAttribute("alt","");
				 errorMessageText.getElement().setAttribute("title","");
				 submitButton.removeFromParent();
				 submittedText.setText(i18n.GL1138());
				 submittedText.getElement().setAttribute("alt",i18n.GL1138());
				 submittedText.getElement().setAttribute("title",i18n.GL1138());
				 showSubmitedText();
				 isOeAnswerSubmited(true);
			 }
		 }else{
			 errorMessageText.setText(i18n.GL1459()+i18n.GL_SPL_FULLSTOP());
		 }
		}
		AppClientFactory.fireEvent(new AssessmentsNextResourceEvent());
	}

	public void showSubmitedText(){
		submittedText.setText("");
		submittedText.getElement().setAttribute("alt","");
		submittedText.getElement().setAttribute("title","");
		answetTextAfterSubmission.add(new HTML(openEndedAnswerTextArea.getValue().replaceAll("<", "&lt;").replaceAll(">", "&gt;")));
		createSesstionItemAttemptOe("",openEndedAnswerTextArea.getValue());
		saveOeAnswerData();
		openEndedAnswerTextArea.removeFromParent();
		increaseUserAttemptCount();
		userAnswerObject();
		saveOeQuestionAnswerDataLogEvent();
		triggerSaveOeAnswerTextDataEvent();
	}

	public void userAnswerObject(){
		List<AnswerAttemptDo> userAttemptedOptionsList=new ArrayList<AnswerAttemptDo>();
		AnswerAttemptDo answerAttemptDo=new AnswerAttemptDo();
		answerAttemptDo.setText(StringUtil.replaceSpecial(openEndedAnswerTextArea.getValue()));
		answerAttemptDo.setAnswerId(0);
		answerAttemptDo.setOrder("");
		answerAttemptDo.setStatus("1");
		userAttemptedOptionsList.add(answerAttemptDo);
		userAttemptedAnswerObject(userAttemptedOptionsList);
	}
	public void saveOeAnswerData(){
		AttemptedAnswersDo attempteAnswersDo=new AttemptedAnswersDo();
		attempteAnswersDo.setAnswersText(openEndedAnswerTextArea.getValue());
		if(collectionItemDo!=null && collectionItemDo.getResource()!=null){
		attempteAnswersDo.setQuestionType(collectionItemDo.getResource().getType());
		}
		if(!StringUtil.isEmpty(collectionItemDo.getCollectionItemId())){
			setAttemptStatus(collectionItemDo.getCollectionItemId(),attempteAnswersDo);
		}
	}
	public void createSesstionItemAttemptOeWhenNavigation(){
		createSesstionItemAttemptOe("",openEndedAnswerTextArea.getValue());
	}
	private void enableSubmitButton(){
		isCheckButtonEnabled=true;
		submitButton.removeStyleName("hintsInActiveButton");
		submitButton.addStyleName("openEndedQuestionSubmitButton");
	}
	private void disableSubmitButton(){
		isCheckButtonEnabled=false;
		submitButton.removeStyleName("openEndedQuestionSubmitButton");
		submitButton.addStyleName("hintsInActiveButton");
	}
	public abstract void createSesstionItemAttemptOe(String answerId,String answerText);
	public abstract void setAttemptStatus(String collecionItemId, AttemptedAnswersDo attempteAnswersDo);
	public abstract void setOeQuestionAnswerText(String answerText);
	public abstract void saveOeQuestionAnswerDataLogEvent();
	public void isUserAnswerAttempted(boolean isUserAttemptedResult){
	}
	public void triggerSaveOeAnswerTextDataEvent(){

	}
	public abstract void increaseUserAttemptCount();
	public void isOeAnswerSubmited(boolean isOeAnswerSubmited){

	}
	public abstract void userAttemptedAnswerObject(List<AnswerAttemptDo> userAttemptedOptionsList);
}
