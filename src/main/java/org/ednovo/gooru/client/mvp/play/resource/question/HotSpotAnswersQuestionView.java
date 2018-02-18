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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.QuestionAnswerDo;
import org.ednovo.gooru.application.shared.model.player.AnswerAttemptDo;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddAnswerImg;
import org.ednovo.gooru.shared.util.AttemptedAnswersDo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
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

public abstract  class HotSpotAnswersQuestionView extends Composite{

	@UiField Button checkAnswer;
	@UiField FlowPanel optionsContainer;
	@UiField Label messageBodyText;
	@UiField HTMLPanel answerText;

	private CollectionItemDo collectionItemDo;

	private AttemptedAnswersDo attemptedAnswerDo=null;

	private boolean isCheckButtonEnabled=true;

	private boolean isCheckAnswerButtonClicked=false;

	private static final String NORMAL_RADIO="hsanswer-normal";
	private static final String SELECTED_RADIO="hsanswer-selected";
	private static final String CORRECT_ICON="hsanswer-right";
	private static final String INCORRECT_ICON="hsanswer-wrong";
	private static final String IMAGE_SELECTED_STYLE="hsImage";
	private static final String IMAGE_CORRECT_STYLE="correct";
	private static final String IMAGE_INCORRECT_STYLE="inCorrect";
	private static final String MARGIN_IMAGE="hsImageAssessmentPlayer";


	private static HotSpotAnswersQuestionViewUiBinder uiBinder = GWT.create(HotSpotAnswersQuestionViewUiBinder.class);

	interface HotSpotAnswersQuestionViewUiBinder extends UiBinder<Widget, HotSpotAnswersQuestionView> {

	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public HotSpotAnswersQuestionView(){
		initWidget(uiBinder.createAndBindUi(this));
		setQuestionTypeCaption();
	}

	@UiConstructor
	public HotSpotAnswersQuestionView(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo){
		initWidget(uiBinder.createAndBindUi(this));
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

			if(collectionItemDo.getResource().getHlType().equalsIgnoreCase(i18n.GL3228_1())){
				while (answersList.hasNext()) {
					QuestionAnswerDo questionAnswerDo=answersList.next();
					double randNumber = Math.random();
					final AddAnswerImg addAnswerImage = new AddAnswerImg();
					addAnswerImage.getChangeImgLbl().setVisible(false);
					addAnswerImage.getRemoveImgLbl().setVisible(false);
					addAnswerImage.setAnswerId(questionAnswerDo.getSequence());
					addAnswerImage.setAnswerImage(collectionItemDo.getAssetURI()+collectionItemDo.getFolder()+questionAnswerDo.getAnswerText()+"?"+randNumber);
					addAnswerImage.setFileName(null);
					addAnswerImage.setAnswerCorrect(questionAnswerDo.isIsCorrect());
					addAnswerImage.selLbl.removeStyleName("answerMarkDeselected");
					addAnswerImage.getElement().getStyle().setCursor(Cursor.POINTER);
					addAnswerImage.addStyleName(MARGIN_IMAGE);
					addAnswerImage.addDomHandler(new imageSelectEvent(addAnswerImage),ClickEvent.getType());

					optionsContainer.add(addAnswerImage);
					if(attemptedAnswerDo!=null){
						showPreviousImageAttemptResult(questionAnswerDo.getSequence(),addAnswerImage,questionAnswerDo.isIsCorrect());
					}
				}
			}else{
				while (answersList.hasNext()) {
					QuestionAnswerDo questionAnswerDo=answersList.next();
					final HSAnswerOptionView questionAnswerOptionView=new HSAnswerOptionView(questionAnswerDo.getAnswerText(),("(" + (char) (65 + i) + ") "));
					questionAnswerOptionView.setAnswerId(questionAnswerDo.getSequence());
					questionAnswerOptionView.answerChoiceResult.addStyleName(NORMAL_RADIO);
					questionAnswerOptionView.setAnswerCorrect(questionAnswerDo.isIsCorrect());
					questionAnswerOptionView.answerChoiceResult.addDomHandler(new RadioButtonSelectEvent(questionAnswerOptionView,questionAnswerDo,i+1), ClickEvent.getType());
					//questionAnswerOptionView.radioButton.addClickHandler(new RadioButtonSelectEvent(questionAnswerOptionView,questionAnswerDo,i+1));
					optionsContainer.add(questionAnswerOptionView);

					if(attemptedAnswerDo!=null){
						showPreviousAttemptResult(questionAnswerDo.getSequence(),questionAnswerOptionView,questionAnswerDo.isIsCorrect());
					}
					i++;
				}
			}
		}
	}

	public void showPreviousAttemptResult(int answerId,HSAnswerOptionView questionAnswerOptionView,boolean isCorrect){

		Map<Integer,Boolean> answerOptionCount=attemptedAnswerDo.getAnswerOptionResult();

		if(answerOptionCount.containsKey(answerId) && answerOptionCount.get(answerId)!=null){
			questionAnswerOptionView.isChecked=true;
			if(isCorrect){
				questionAnswerOptionView.answerChoiceResult.addStyleName(CORRECT_ICON);
				questionAnswerOptionView.answerChoiceResult.addStyleName(SELECTED_RADIO);
			}else{
				questionAnswerOptionView.answerChoiceResult.addStyleName(INCORRECT_ICON);
				questionAnswerOptionView.answerChoiceResult.addStyleName(SELECTED_RADIO);
			}
		}
	}


	public void showPreviousImageAttemptResult(int answerId,AddAnswerImg answerImg,boolean isCorrect){
		Map<Integer,Boolean> answerOptionCount=attemptedAnswerDo.getAnswerOptionResult();
		if(answerOptionCount.containsKey(answerId) && answerOptionCount.get(answerId)!=null){
			answerImg.getElement().addClassName(IMAGE_SELECTED_STYLE);
			answerImg.selectedImage=true;
			if(isCorrect){
				answerImg.getElement().addClassName(IMAGE_CORRECT_STYLE);
			}else{
				answerImg.getElement().addClassName(IMAGE_INCORRECT_STYLE);
			}
		}
	}

	public class imageSelectEvent implements ClickHandler{
		private AddAnswerImg ansImage=null;

		public imageSelectEvent(AddAnswerImg ansImage){
			this.ansImage=ansImage;

		}

		@Override
		public void onClick(ClickEvent event) {
			clearImageAnswers();
			if(ansImage.selectedImage){
				ansImage.getElement().removeClassName(IMAGE_SELECTED_STYLE);
				ansImage.selectedImage=false;
			}else{
				ansImage.getElement().addClassName(IMAGE_SELECTED_STYLE);
				ansImage.selectedImage=true;
			}

			checkSeletcedImage();
		}

	}
	private void clearImageAnswers(){
		int widgetCount=optionsContainer.getWidgetCount();
		disableCheckAnswerButton();
		for(int i=0;i<widgetCount;i++){
			Widget widget=optionsContainer.getWidget(i);
			if(widget instanceof AddAnswerImg){
				AddAnswerImg answerImg=(AddAnswerImg)widget;
				answerImg.removeStyleName(IMAGE_CORRECT_STYLE);
				answerImg.removeStyleName(IMAGE_INCORRECT_STYLE);
			}
		}

	}

	public void checkSeletcedImage(){
		int widgetCount=optionsContainer.getWidgetCount();
		disableCheckAnswerButton();
		for(int i=0;i<widgetCount;i++){
			Widget widget=optionsContainer.getWidget(i);
			if(widget instanceof AddAnswerImg){
				AddAnswerImg answerImg=(AddAnswerImg)widget;
				if(answerImg.selectedImage){
					enableCheckAnswerButton();
				}
			}
		}
	}


	public class RadioButtonSelectEvent implements ClickHandler{
		private HSAnswerOptionView questionAnswerOptionView=null;
		private QuestionAnswerDo questionAnswerDo=null;
		private int attemptSequence=1;

		public RadioButtonSelectEvent(HSAnswerOptionView questionAnswerOptionView,QuestionAnswerDo questionAnswerDo,int attemptSequence){
			this.questionAnswerOptionView=questionAnswerOptionView;
			this.questionAnswerDo=questionAnswerDo;
			this.attemptSequence=attemptSequence;
		}

		@Override
		public void onClick(ClickEvent event) {

			if(questionAnswerOptionView.isChecked){
				questionAnswerOptionView.isChecked=false;
				questionAnswerOptionView.answerChoiceResult.removeStyleName(SELECTED_RADIO);
				int attemptStatus=questionAnswerDo.isIsCorrect()?1:0;
				setAnswerAttemptSequence(attemptSequence,attemptStatus,questionAnswerDo.getSequence());
			}else{
				questionAnswerOptionView.isChecked=true;
				questionAnswerOptionView.answerChoiceResult.addStyleName(SELECTED_RADIO);
				int attemptStatus=questionAnswerDo.isIsCorrect()?1:0;
				setAnswerAttemptSequence(attemptSequence,attemptStatus,questionAnswerDo.getSequence());
			}
			isUserAnswerAttempted(true);
			checkSelectedRadioButton();

		}
	}

	private void checkSelectedRadioButton(){
		int widgetCount=optionsContainer.getWidgetCount();
		disableCheckAnswerButton();
		for(int i=0;i<widgetCount;i++){
			Widget widget=optionsContainer.getWidget(i);
			if(widget instanceof HSAnswerOptionView){
				HSAnswerOptionView questionAnswerOptionView=(HSAnswerOptionView)widget;
				questionAnswerOptionView.answerChoiceResult.removeStyleName(CORRECT_ICON);
				questionAnswerOptionView.answerChoiceResult.removeStyleName(INCORRECT_ICON);
				if(questionAnswerOptionView.answerChoiceResult.getStyleName().contains(SELECTED_RADIO)){
					enableCheckAnswerButton();
				}
			}
		}
	}

	private void enableCheckAnswerButton(){
		isCheckButtonEnabled=true;
		checkAnswer.removeStyleName("hintsInActiveButton");
		checkAnswer.addStyleName("primary");
	}
	private void disableCheckAnswerButton(){
		isCheckButtonEnabled=true;
		checkAnswer.addStyleName("hintsInActiveButton");
		checkAnswer.removeStyleName("primary");
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
		boolean hsChoiceStatus=true;
		Map<Integer,Boolean> answerOptionResult=new LinkedHashMap<Integer,Boolean>();
		List<Integer> answerIds=new ArrayList<Integer>();
		List<String> userAttemptedValueList=new ArrayList<String>();
		List<AnswerAttemptDo> userAttemptedOptionsList=new ArrayList<AnswerAttemptDo>();

		for(int i=0;i<widgetCount;i++){
			Widget widget=optionsContainer.getWidget(i);
			if(widget instanceof HSAnswerOptionView){
				HSAnswerOptionView questionAnswerOptionView=(HSAnswerOptionView)widget;

				AttemptedAnswersDo attempteAnswersDo=new AttemptedAnswersDo();
				attempteAnswersDo.setQuestionType(collectionItemDo.getResource().getType());
				attempteAnswersDo.setAttemptResult(questionAnswerOptionView.isAnswerCorrect());
				attempteAnswersDo.setAnswerId(questionAnswerOptionView.getAnswerId());

				AnswerAttemptDo answerAttemptDo=new AnswerAttemptDo();
				answerAttemptDo.setText(URL.encodeQueryString(questionAnswerOptionView.getAnswerText()));
				//answerAttemptDo.setText(StringUtil.replaceSpecial(questionAnswerOptionView.getAnswerText()));
				answerAttemptDo.setAnswerId(questionAnswerOptionView.getAnswerId());
				answerAttemptDo.setOrder((i+1)+"");
				answerIds.add(i+1);

				if(questionAnswerOptionView.isChecked){

					if(questionAnswerOptionView.isAnswerCorrect()){
						if(hsChoiceStatus){
							hsChoiceStatus=true;
						}
						answerAttemptDo.setStatus("1");
						answerOptionResult.put(questionAnswerOptionView.getAnswerId(), true);
						userAttemptedValueList.add("1");
						questionAnswerOptionView.answerChoiceResult.addStyleName(CORRECT_ICON);
					}else{
						hsChoiceStatus=false;
						answerOptionResult.put(questionAnswerOptionView.getAnswerId(), false);
						userAttemptedValueList.add("0");
						answerAttemptDo.setStatus("0");
						questionAnswerOptionView.answerChoiceResult.addStyleName(INCORRECT_ICON);
					}

				}else if(!questionAnswerOptionView.isChecked && questionAnswerOptionView.isAnswerCorrect()) {
					hsChoiceStatus=false;
					answerAttemptDo.setStatus("skipped");
				}

				setAttemptStatus(collectionItemDo.getCollectionItemId(),attempteAnswersDo);
				attempteAnswersDo.setAnswerOptionResult(answerOptionResult);
				userAttemptedOptionsList.add(answerAttemptDo);

			}else if(widget instanceof AddAnswerImg){
				AddAnswerImg answerImg=(AddAnswerImg)widget;
				AttemptedAnswersDo attempteAnswersDo=new AttemptedAnswersDo();
				attempteAnswersDo.setQuestionType(collectionItemDo.getResource().getType());
				attempteAnswersDo.setAttemptResult(answerImg.isAnswerCorrect());
				attempteAnswersDo.setAnswerId(answerImg.getAnswerId());
				AnswerAttemptDo answerAttemptDo=new AnswerAttemptDo();
				answerAttemptDo.setText(answerImg.getAnswerImage());
				answerAttemptDo.setAnswerId(answerImg.getAnswerId());
				answerAttemptDo.setOrder((i+1)+"");
				answerIds.add(i+1);

				if(answerImg.selectedImage){

					if(answerImg.isAnswerCorrect()){
						if(hsChoiceStatus){
							hsChoiceStatus=true;
						}
						answerAttemptDo.setStatus("1");
						answerOptionResult.put(answerImg.getAnswerId(), true);
						userAttemptedValueList.add("1");
						answerImg.getElement().addClassName(IMAGE_CORRECT_STYLE);
					}else{
						hsChoiceStatus=false;
						answerOptionResult.put(answerImg.getAnswerId(), false);
						userAttemptedValueList.add("0");
						answerAttemptDo.setStatus("0");
						answerImg.getElement().addClassName(IMAGE_INCORRECT_STYLE);
					}

				}else if(!answerImg.selectedImage && answerImg.isAnswerCorrect()) {
					hsChoiceStatus=false;
					answerAttemptDo.setStatus("skipped");
				}

				setAttemptStatus(collectionItemDo.getCollectionItemId(),attempteAnswersDo);
				attempteAnswersDo.setAnswerOptionResult(answerOptionResult);
				userAttemptedOptionsList.add(answerAttemptDo);
			}
		}
		userAttemptedAnswerObject(userAttemptedOptionsList);
		increaseUserAttemptCount();

		String attemptStatus=hsChoiceStatus==true?"correct":"wrong";
		int score=0;
		boolean isFirstTry=isCheckAnswerButtonClicked;
		isCheckAnswerButtonClicked=true;
		score=hsChoiceStatus?1:0;
		userAttemptedValue(userAttemptedValueList);
		setAnswersDetailsWitithTime(answerIds,hsChoiceStatus?1:0,1,score,!isFirstTry);
		createSesstionItemAttemptForMultipleAnswer(answerIds,userAttemptedValueList,attemptStatus);
	}

	public abstract void createSessionItemAttempt(int answerId,String answerAttemptStatus);
	public abstract void setAttemptStatus(String collectionItemId,AttemptedAnswersDo attemptAnswerDo);
	public abstract void setAnswerAttemptSequence(int attemptSequence,int attemptStatus, int answerId);
	public void isUserAnswerAttempted(boolean isUserAttemptedResult){}
	public void setAnswersDetailsWitithTime(List<Integer> answerIds,int answerStatus,int answerSequence,int score,boolean isFirstTry){}

	public abstract void increaseUserAttemptCount();
	public abstract void userAttemptedValue(List<String> userAttemptedValueList);
	public abstract void createSesstionItemAttemptForMultipleAnswer(List<Integer> answerIds,List<String> userAttemptedAnswers,String attemptStatus);
	public abstract void userAttemptedAnswerObject(List<AnswerAttemptDo> answerOptionAttemptList);
}
