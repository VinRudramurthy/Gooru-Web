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
package org.ednovo.gooru.client.mvp.play.resource.question;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.QuestionAnswerDo;
import org.ednovo.gooru.application.shared.model.player.AnswerAttemptDo;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.shared.util.AttemptedAnswersDo;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class  MultipleChoicesQuestionView extends Composite {

	@UiField Button checkAnswer;
	@UiField FlowPanel optionsContainer,questionsMainPanel;
	@UiField Label messageBodyText;
	@UiField HTMLPanel answerText;
	private boolean isCheckButtonEnabled=true;

	private boolean isChekcAnswerButtonClicked=false;

	private CollectionItemDo collectionItemDo;

	private AttemptedAnswersDo attemptedAnswerDo=null;

	private static MultipleChoicesQuestionViewUiBinder uiBinder = GWT.create(MultipleChoicesQuestionViewUiBinder.class);

	interface MultipleChoicesQuestionViewUiBinder extends UiBinder<Widget, MultipleChoicesQuestionView> {

	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public MultipleChoicesQuestionView(){
		initWidget(uiBinder.createAndBindUi(this));
		questionsMainPanel.getElement().setId("fpnlQuestionContainer");
		setQuestionTypeCaption();
	}

	@UiConstructor
	public MultipleChoicesQuestionView(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo){
		initWidget(uiBinder.createAndBindUi(this));
		questionsMainPanel.getElement().setId("fpnlQuestionContainer");
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		this.collectionItemDo=collectionItemDo;
		setQuestionTypeCaption();
		this.attemptedAnswerDo=attemptedAnswerDo;
		renderQuestionAnswerOptions();
		answerText.getElement().setInnerHTML(i18n.GL0665());
		answerText.getElement().setAttribute("alt",i18n.GL0665());
		answerText.getElement().setAttribute("title",i18n.GL0665());
		checkAnswer.setText(i18n.GL0666());
		checkAnswer.getElement().setAttribute("alt",i18n.GL0666());
		checkAnswer.getElement().setAttribute("title",i18n.GL0666());
	}

	private void setQuestionTypeCaption(){
		messageBodyText.setText(i18n.GL1457()+i18n.GL_SPL_FULLSTOP());
		answerText.getElement().setId("pnlAnswerText");
		checkAnswer.getElement().setId("btnCheckAnswer");
		messageBodyText.getElement().setId("lblMessageBodyText");
		optionsContainer.getElement().setId("fpnlOptionsContainer");
	}

	private void renderQuestionAnswerOptions(){
		if(collectionItemDo!=null && collectionItemDo.getResource()!=null && collectionItemDo.getResource().getAnswers()!=null){
			TreeSet<QuestionAnswerDo> answersSet=collectionItemDo.getResource().getAnswers();
			Iterator<QuestionAnswerDo> answersList=answersSet.iterator();
			int i=0;
			while (answersList.hasNext()) {
				QuestionAnswerDo questionAnswerDo=answersList.next();
				final QuestionAnswerOptionView questionAnswerOptionView=new QuestionAnswerOptionView(questionAnswerDo.getAnswerText(),("(" + (char) (65 + i) + ") "));
				questionAnswerOptionView.setAnswerId(questionAnswerDo.getAnswerId());
				questionAnswerOptionView.radioButton.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().normalRadioIcon());
				questionAnswerOptionView.setAnswerCorrect(questionAnswerDo.isIsCorrect());
				questionAnswerOptionView.radioButton.addClickHandler(new RadioButtonSelectEvent(questionAnswerOptionView,questionAnswerDo,i+1));
				optionsContainer.add(questionAnswerOptionView);
				showPreviousAttemptResult(questionAnswerDo.getAnswerId(),questionAnswerOptionView);
				i++;
			}
		}
	}

	public void showPreviousAttemptResult(int answerId,QuestionAnswerOptionView questionAnswerOptionView){
		if(attemptedAnswerDo!=null){
			if(attemptedAnswerDo.getAnswerId()==answerId){
				if(attemptedAnswerDo.isAttemptResult()){
					questionAnswerOptionView.answerChoiceResult.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().answerRightIcon());
					questionAnswerOptionView.radioButton.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().selectedRadioIcon());
					questionAnswerOptionView.answerOptionRadioButton.setValue(true);
				}else{
					questionAnswerOptionView.answerChoiceResult.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().answerWronIcon());
					questionAnswerOptionView.radioButton.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().selectedRadioIcon());
					questionAnswerOptionView.answerOptionRadioButton.setValue(true);
				}
			}
		}
	}
	public class RadioButtonSelectEvent implements ClickHandler{
		private QuestionAnswerOptionView questionAnswerOptionView=null;
		private QuestionAnswerDo questionAnswerDo=null;
		private int attemptSequence=1;

		public RadioButtonSelectEvent(QuestionAnswerOptionView questionAnswerOptionView,QuestionAnswerDo questionAnswerDo,int attemptSequence){
			this.questionAnswerOptionView=questionAnswerOptionView;
			this.questionAnswerDo=questionAnswerDo;
			this.attemptSequence=attemptSequence;
		}

		@Override
		public void onClick(ClickEvent event) {
			if(!this.questionAnswerOptionView.answerOptionRadioButton.getValue()){
				resetSelectedRadioButton();
				enableCheckAnswerButton();
				questionAnswerOptionView.answerOptionRadioButton.setValue(true);
				questionAnswerOptionView.radioButton.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().selectedRadioIcon());
				int attemptStatus=questionAnswerDo.isIsCorrect()?1:0;
				setAnswerAttemptSequence(attemptSequence,attemptStatus,questionAnswerDo.getAnswerId());
				isUserAnswerAttempted(true);
			}
		}
	}

	private void resetSelectedRadioButton(){
		int widgetCount=optionsContainer.getWidgetCount();
		for(int i=0;i<widgetCount;i++){
			Widget widget=optionsContainer.getWidget(i);
			if(widget instanceof QuestionAnswerOptionView){
				QuestionAnswerOptionView questionAnswerOptionView=(QuestionAnswerOptionView)widget;
				questionAnswerOptionView.radioButton.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().normalRadioIcon());
				questionAnswerOptionView.answerOptionRadioButton.setValue(false);
				questionAnswerOptionView.answerChoiceResult.setStyleName("");
			}
		}
	}

	private void enableCheckAnswerButton(){
		isCheckButtonEnabled=true;
		checkAnswer.removeStyleName("hintsInActiveButton");
		checkAnswer.addStyleName("primary");
	}

	@UiHandler("checkAnswer")
	public void checkButtonClickEvent(ClickEvent event){
		if(isCheckButtonEnabled){
			showCorrectResult();
			isCheckButtonEnabled=false;
			checkAnswer.removeStyleName("primary");
			checkAnswer.addStyleName("hintsInActiveButton");
		}
	}

	private void showCorrectResult(){
		int widgetCount=optionsContainer.getWidgetCount();
		for(int i=0;i<widgetCount;i++){
			Widget widget=optionsContainer.getWidget(i);
			if(widget instanceof QuestionAnswerOptionView){
				QuestionAnswerOptionView questionAnswerOptionView=(QuestionAnswerOptionView)widget;
				if(questionAnswerOptionView.answerOptionRadioButton.getValue()){
					createSessionItemAttempt(questionAnswerOptionView.getAnswerId(), questionAnswerOptionView.isAnswerCorrect()?"correct":"wrong");
					AttemptedAnswersDo attempteAnswersDo=new AttemptedAnswersDo();
					attempteAnswersDo.setQuestionType(collectionItemDo.getResource().getType());
					attempteAnswersDo.setAttemptResult(questionAnswerOptionView.isAnswerCorrect());
					attempteAnswersDo.setAnswerId(questionAnswerOptionView.getAnswerId());
					setAttemptStatus(collectionItemDo.getCollectionItemId(),attempteAnswersDo);
					increaseUserAttemptCount();
					int score=0;
					boolean isFirstTry=isChekcAnswerButtonClicked;
					isChekcAnswerButtonClicked=true;
					score=questionAnswerOptionView.isAnswerCorrect()?1:0;

					setAnswersDetailsWitithTime(questionAnswerOptionView.getAnswerId(),questionAnswerOptionView.isAnswerCorrect()?1:0,(i+1),score,!isFirstTry);
					if(questionAnswerOptionView.isAnswerCorrect()){
						questionAnswerOptionView.answerChoiceResult.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().answerRightIcon());
					}else{
						questionAnswerOptionView.answerChoiceResult.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().answerWronIcon());
					}
					List<AnswerAttemptDo> userAttemptedOptionsList=new ArrayList<AnswerAttemptDo>();
					AnswerAttemptDo answerAttemptDo=new AnswerAttemptDo();
					answerAttemptDo.setText(StringUtil.replaceSpecial(questionAnswerOptionView.getAnswerText()));
					answerAttemptDo.setAnswerId(questionAnswerOptionView.getAnswerId());
					answerAttemptDo.setOrder((i+1)+"");
					answerAttemptDo.setStatus(questionAnswerOptionView.isAnswerCorrect()?"1":"0");
					userAttemptedOptionsList.add(answerAttemptDo);
					userAttemptedAnswerObject(userAttemptedOptionsList);
				}
			}
		}
	}

	public void createSessionItemAttemptWhenNavigation(){
		int widgetCount=optionsContainer.getWidgetCount();
		for(int i=0;i<widgetCount;i++){
			Widget widget=optionsContainer.getWidget(i);
			if(widget instanceof QuestionAnswerOptionView){
				QuestionAnswerOptionView questionAnswerOptionView=(QuestionAnswerOptionView)widget;
				if(questionAnswerOptionView.answerOptionRadioButton.getValue()){
					createSessionItemAttempt(questionAnswerOptionView.getAnswerId(), questionAnswerOptionView.isAnswerCorrect()?"correct":"wrong");
				}
			}
		}
	}
	public abstract void createSessionItemAttempt(int answerId,String answerAttemptStatus);
	public abstract void setAttemptStatus(String collectionItemId,AttemptedAnswersDo attemptAnswerDo);
	public abstract void setAnswerAttemptSequence(int attemptSequence,int attemptStatus, int answerId);
	public void isUserAnswerAttempted(boolean isUserAttemptedResult){}
	public void setAnswersDetailsWitithTime(int answerId,int answerStatus,int answerSequence,int score,boolean isFirstTry){

	}
	public abstract void increaseUserAttemptCount();
	public abstract void userAttemptedAnswerObject(List<AnswerAttemptDo> answerOptionAttemptList);

}
