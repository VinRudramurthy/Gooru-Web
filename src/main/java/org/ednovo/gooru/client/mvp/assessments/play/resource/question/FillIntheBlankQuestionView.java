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
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.QuestionAnswerDo;
import org.ednovo.gooru.application.shared.model.player.AnswerAttemptDo;
import org.ednovo.gooru.client.mvp.assessments.play.collection.event.AssessmentsNextResourceEvent;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.shared.util.AttemptedAnswersDo;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.InfoUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class FillIntheBlankQuestionView extends Composite implements ClientConstants{

	@UiField Button checkAnswer;
	@UiField FlowPanel optionsContainer,resultPanel,questionsMainPanel;
	@UiField Label messageBodyText;
	@UiField HTMLPanel answerText;

	private List<TextBox> textBoxArray=new ArrayList<TextBox>();
	private String[] enteredAnswerText=new String[3];
	private CollectionItemDo collectionItemDo=null;
	private boolean isCheckButtonEnabled=false;

	private AttemptedAnswersDo attemptedAnswerDo=null;

	private static OpendEndedQuestionViewUiBinder uiBinder = GWT.create(OpendEndedQuestionViewUiBinder.class);

	interface OpendEndedQuestionViewUiBinder extends UiBinder<Widget, FillIntheBlankQuestionView> {

	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);
	/**
	 * Default constructor
	 */
	public FillIntheBlankQuestionView(){
		initWidget(uiBinder.createAndBindUi(this));
		questionsMainPanel.getElement().setId("fpnlQuestionContainer");
		setQuestionTypeCaption();
		answerText.getElement().setInnerHTML(i18n.GL0665());
		answerText.getElement().setAttribute("alt",i18n.GL0665());
		answerText.getElement().setAttribute("title",i18n.GL0665());

		checkAnswer.setText(i18n.GL3460_4());
		checkAnswer.getElement().setAttribute("alt",i18n.GL3460_4());
		checkAnswer.getElement().setAttribute("title",i18n.GL3460_4());

		messageBodyText.getElement().setAttribute("title",i18n.GL0666());

		renderFibQuestion();
		setId();
	}
	/**
	 * This method is used to set the Id's
	 */
	public void setId(){
		answerText.getElement().setId("pnlAnswerText");
		checkAnswer.getElement().setId("btnCheckAnswer");
		messageBodyText.getElement().setId("lblMessageBodyText");
		optionsContainer.getElement().setId("fpnlOptionsContainer");
		resultPanel.getElement().setId("fpnlResultPanel");
	}
	@UiConstructor
	public FillIntheBlankQuestionView(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo){
		initWidget(uiBinder.createAndBindUi(this));
		questionsMainPanel.getElement().setId("fpnlQuestionContainer");
		this.collectionItemDo=collectionItemDo;
		this.attemptedAnswerDo=attemptedAnswerDo;
		setQuestionTypeCaption();
		renderFibQuestion();
		answerText.getElement().setInnerHTML(i18n.GL0665());
		answerText.getElement().setAttribute("alt",i18n.GL0665());
		answerText.getElement().setAttribute("title",i18n.GL0665());
		checkAnswer.setText(i18n.GL3460_4());
		checkAnswer.getElement().setAttribute("alt",i18n.GL3460_4());
		checkAnswer.getElement().setAttribute("title",i18n.GL3460_4());
		setId();
	}
	/**
	 * This method is used to set the message for FIll in the blanks
	 */
	public void setQuestionTypeCaption(){
		messageBodyText.setText(i18n.GL3499()+i18n.GL_SPL_FULLSTOP());
		messageBodyText.getElement().setAttribute("alt",i18n.GL3499());
		messageBodyText.getElement().setAttribute("title",i18n.GL3499());
	}
	/**
	 * This method is used to render the FIB data
	 */
	public void renderFibQuestion(){
		String[] fibArray = null;
		if(this.collectionItemDo!=null && this.collectionItemDo.getResource()!=null){
			fibArray = this.collectionItemDo.getResource().getQuestionText()!=null?this.collectionItemDo.getResource().getQuestionText().split(FIB_SEPARATOR):null;
			String fibQuestionTxt = "";
			int answerArraySize = this.collectionItemDo.getResource().getAnswers()!=null?this.collectionItemDo.getResource().getAnswers().size():0;
			HTML fibText=new HTML();
			fibText.setStyleName("answerTextContainer");
			if(fibArray!=null){
				for(int i = 0; i < fibArray.length; i++) {
					fibQuestionTxt = fibQuestionTxt + fibArray[i];
					if(i<answerArraySize) {
						String titlelbl1=InfoUtil.removeQuestionTagsOnBoldClick(fibQuestionTxt);
						fibQuestionTxt = titlelbl1 + "<span id=\"fib"+i+"\"></span>";
					}
				}
			}

			fibText.setHTML(fibQuestionTxt);
			optionsContainer.add(fibText);
		}
	}
	/**
	 * This method will create and add textboxes based on the Answers size
	 */
	@Override
	public void onLoad(){
		super.onLoad();
		if(this.collectionItemDo!=null && this.collectionItemDo.getResource()!=null){
		int answerArraySize = this.collectionItemDo.getResource().getAnswers()!=null?this.collectionItemDo.getResource().getAnswers().size():0;
		for(int i = 0; i < answerArraySize; i++) {
			TextBox answerText = new TextBox();
			answerText.addKeyUpHandler(new AnswerTextBoxKeypressEvent());
			answerText.setStyleName("answerTextBox");
			optionsContainer.add(answerText);
			textBoxArray.add(answerText);
			Document.get().getElementById("fib"+i).appendChild(answerText.getElement());
		}
		showPreviousAttemptedResult();
		}
	}
	/**
	 * This method will set the previous attempted result
	 */
	public void showPreviousAttemptedResult(){
		if(attemptedAnswerDo!=null){
			String[] previoustAttemptedResult=attemptedAnswerDo.getFibAnswersList();
			for(int i=0;i<textBoxArray.size();i++){
				textBoxArray.get(i).setText(StringUtil.replaceSpecial(previoustAttemptedResult[i]));
			}
		}
	}
	/**
	 * This method is used to enable the text boxes if it is not read only.
	 */
	private void enableCheckAnswerButton(){
		isCheckButtonEnabled=true;
		checkAnswer.removeStyleName("hintsInActiveButton");
		checkAnswer.addStyleName("openEndedQuestionSubmitButton");
	}
	/**
	 * This handler is used to call the highlight text boxes functionality and increase the no.of user attempts
	 * @param event
	 */
	@UiHandler("checkAnswer")
	public void checkAnswerButtonEvent(ClickEvent event){
		if(isCheckButtonEnabled){
			highlightTextBoxes();
			increaseUserAttemptCount();
			setEnteredFibAnswersData(enteredAnswerText);
			isCheckButtonEnabled=false;
			checkAnswer.removeStyleName("openEndedQuestionSubmitButton");
			checkAnswer.addStyleName("hintsInActiveButton");
			AppClientFactory.fireEvent(new AssessmentsNextResourceEvent());
		}
	}
	/**
	 * THis method will highlight the text boxes if the correct answer matched
	 */
	private void highlightTextBoxes(){
		if(this.collectionItemDo!=null && this.collectionItemDo.getResource()!=null){
			 if(this.collectionItemDo.getResource().getAnswers()!=null){
					TreeSet<QuestionAnswerDo> answersList=this.collectionItemDo.getResource().getAnswers();
					Iterator<QuestionAnswerDo> answersIterator=answersList.iterator();
					int i=0;
					boolean isFibStatus=true;
					List<String> attemptedAnswersList=new ArrayList<String>();
					List<Integer> attemptAnswerIds=new ArrayList<Integer>();
					List<Integer> attemptTrySequenceArray=new ArrayList<Integer>();
					List<Integer> attemptStatusArray=new ArrayList<Integer>();
					List<AnswerAttemptDo> userAttemptedOptionsList=new ArrayList<AnswerAttemptDo>();
					while(answersIterator.hasNext()){
						QuestionAnswerDo questionAnswerDo=answersIterator.next();
						String 	questionAnswerDoAnswerText	=	questionAnswerDo.getAnswerText()!=null?questionAnswerDo.getAnswerText().trim():"";
					  	String 	textBoxAnswerDoAnswerText	=	textBoxArray.get(i).getText().trim();
					  	questionAnswerDoAnswerText 	=questionAnswerDoAnswerText.replaceAll("<[^>]*>", "");
					  	questionAnswerDoAnswerText = StringUtil.replaceSpecial(questionAnswerDoAnswerText);
					  	attemptedAnswersList.add(textBoxAnswerDoAnswerText);
					  	attemptAnswerIds.add(questionAnswerDo.getAnswerId());
					  	attemptTrySequenceArray.add(i+1);
					  	AnswerAttemptDo answerAttemptDo=new AnswerAttemptDo();
						answerAttemptDo.setText(StringUtil.replaceSpecial(textBoxAnswerDoAnswerText));
						answerAttemptDo.setAnswerId(questionAnswerDo.getAnswerId());
						answerAttemptDo.setOrder((i+1)+"");
						userAttemptedOptionsList.add(answerAttemptDo);
						if(StringUtil.isEmpty(textBoxAnswerDoAnswerText)){
							answerAttemptDo.setSkip(true);
						}
						if(questionAnswerDoAnswerText.equalsIgnoreCase(textBoxAnswerDoAnswerText)){
							answerAttemptDo.setStatus("1");
							if(isFibStatus){
								isFibStatus=true;
							}
						}else{
							answerAttemptDo.setStatus("0");
							isFibStatus=false;
						}
						textBoxArray.get(i).setReadOnly(true);
						i++;
					}
					attemptStatusArray.add(isFibStatus?1:0);
					int score=0;
					boolean isFirstAttempt=false;
					isFirstAttempt=true;
					score=isFibStatus?1:0;

					userAttemptedAnswerObject(userAttemptedOptionsList);
					String attemptStatus=isFibStatus==true?"correct":"wrong";
					createSesstionItemAttemptOe(attemptAnswerIds,attemptedAnswersList,attemptStatus);
					setFibAnswerIdsWithTime(attemptAnswerIds,attemptTrySequenceArray,attemptStatusArray,score,isFirstAttempt,attemptedAnswersList);
			 }
		 }
	}
	/**
	 * This method is used to set the answers data when navigating through resources
	 */
	public void createSesstionItemAttemptOeWhenNavigation(){
		if(this.collectionItemDo!=null && this.collectionItemDo.getResource()!=null){
			if(this.collectionItemDo.getResource().getAnswers()!=null){
				TreeSet<QuestionAnswerDo> answersList=this.collectionItemDo.getResource().getAnswers();
				Iterator<QuestionAnswerDo> answersIterator=answersList.iterator();
				int i=0;
				boolean isFibStatus=true;
				List<String> attemptedAnswersList=new ArrayList<String>();
				List<Integer> attemptAnswerIds=new ArrayList<Integer>();
				while(answersIterator.hasNext()){
					QuestionAnswerDo questionAnswerDo=answersIterator.next();
					String 	questionAnswerDoAnswerText	=	StringUtil.replaceSpecial(questionAnswerDo.getAnswerText()!=null?questionAnswerDo.getAnswerText().trim():"");
				  	String 	textBoxAnswerDoAnswerText	=	StringUtil.replaceSpecial(textBoxArray.get(i).getText().trim());
				  	attemptedAnswersList.add(textBoxAnswerDoAnswerText);
				  	attemptAnswerIds.add(questionAnswerDo.getAnswerId());
					if(questionAnswerDoAnswerText.equalsIgnoreCase(textBoxAnswerDoAnswerText)){
						if(isFibStatus){
							isFibStatus=true;
						}
					}else{
						isFibStatus=false;
					}
					i++;
				}
				String attemptStatus=isFibStatus==true?"correct":"wrong";
				createSesstionItemAttemptOe(attemptAnswerIds,attemptedAnswersList,attemptStatus);
			}
		}
	}
	/**
	 * This method is used to enable textboxes on keypressevent of textbox if it is not readonly type
	 * @author gooruteam
	 *
	 */
	public class AnswerTextBoxKeypressEvent implements KeyUpHandler{
		@Override
		public void onKeyUp(KeyUpEvent event) {
			for(int i=0;i<textBoxArray.size();i++){
				if(textBoxArray.get(i).getText().length()>0){
					if(!textBoxArray.get(i).isReadOnly()){
						enableCheckAnswerButton();
					}
				}
				enteredAnswerText[i]=StringUtil.replaceSpecial(textBoxArray.get(i).getText().trim());
			}
			isUserAnswerAttempted(true);
		}
	}

	/**
	 * This method is used to set the FIB answer's data
	 * @param enteredFibAnswersData
	 */
	public void setEnteredFibAnswersData(String[] enteredFibAnswersData){
		AttemptedAnswersDo attempteAnswersDo=new AttemptedAnswersDo();
		attempteAnswersDo.setFibAnswersList(enteredFibAnswersData);
		attempteAnswersDo.setQuestionType(collectionItemDo.getResource().getType());
		setAttemptStatus(collectionItemDo.getCollectionItemId(),attempteAnswersDo);
	}
	public void setAttemptStatus(String collecionItemId, AttemptedAnswersDo attempteAnswersDo){

	}
	public void createSesstionItemAttemptOe(List<Integer> answerIds,List<String> userAttemptedAnswers,String attemptStatus){

	}
	public void isUserAnswerAttempted(boolean isUserAttemptedResult){
	}
	public void setFibAnswerIdsWithTime(List<Integer> attemptAnswerIds,List<Integer> attemptTrySequenceArray,List<Integer> attemptStatusArray,Integer score,boolean isFirstAttempt,List<String> attemptedAnswersList){

	}
	public void increaseUserAttemptCount(){

	}

	public void userAttemptedAnswerObject(List<AnswerAttemptDo> userAttemptedOptionsList) {
	}
}
