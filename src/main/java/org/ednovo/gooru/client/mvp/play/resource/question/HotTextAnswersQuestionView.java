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

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.QuestionAnswerDo;
import org.ednovo.gooru.application.shared.model.player.AnswerAttemptDo;
import org.ednovo.gooru.client.mvp.dnd.Draggable;
import org.ednovo.gooru.client.mvp.play.resource.question.event.ResetDragDropEvent;
import org.ednovo.gooru.client.mvp.play.resource.question.event.ResetDragDropHandler;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.shared.util.AttemptedAnswersDo;
import org.ednovo.gooru.shared.util.InfoUtil;
import org.ednovo.gooru.shared.util.RandomIterator;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract  class HotTextAnswersQuestionView extends Composite{

	@UiField Button checkAnswer;
	@UiField FlowPanel optionsContainerFpnl;
	HTAnswerDragPanelVc optionsContainer;
	@UiField Label messageBodyText;
	@UiField HTMLPanel answerText;

	private CollectionItemDo collectionItemDo;

	private AttemptedAnswersDo attemptedAnswerDo=null;

	private List randomSequenceList;

	private boolean isCheckButtonEnabled=true;

	private boolean isCheckAnswerButtonClicked=false;

	String[] correctAnsSequence;
	String[] attemptAnsSequence;

	private static String SPACE=" ";
	private static String COMMA=",";
	private static String SEMICOLUMN=";";
	private static String DELIMITER_SPACE="[\\s\\xA0]+";
	private static String STYLE_HIGHLIGHT="htHiglightText";
	private static String STYLE_CORRECT="correct";
	private static String STYLE_INCORRECT="inCorrect";
	private static String STYLE_INACTIVE_BUTTON="htPlayerSubmitInActiveButton";
	private static String STYLE_DND_CORRECT="dragDropAnsCorrect";
	private static String STYLE_DND_INCORRECT="dragDropAnsInCorrect";
	private static String DOT=".";
	private static String START_CORRECT_DELIMITER="[$";
	private static String START_DELIMITER="[";
	private static String END_CORRECT_DELIMITER="$]";
	private static String END_DELIMITER="]";


	private static HotTextAnswersQuestionViewUiBinder uiBinder = GWT.create(HotTextAnswersQuestionViewUiBinder.class);

	interface HotTextAnswersQuestionViewUiBinder extends UiBinder<Widget, HotTextAnswersQuestionView> {

	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public HotTextAnswersQuestionView(){
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiConstructor
	public HotTextAnswersQuestionView(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo,List randomList){
		initWidget(uiBinder.createAndBindUi(this));
		AppClientFactory.getEventBus().addHandler(ResetDragDropEvent.TYPE,resetReorderData);
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		this.collectionItemDo=collectionItemDo;
		this.attemptedAnswerDo=attemptedAnswerDo;
		this.randomSequenceList=randomList;
		setQuestionTypeCaption();

		answerText.getElement().setInnerHTML(i18n.GL0665());
		checkAnswer.setText(i18n.GL0666());
	}

	private void setQuestionTypeCaption(){
		messageBodyText.setText(i18n.GL1457()+i18n.GL_SPL_FULLSTOP());
		optionsContainerFpnl.clear();
		if(collectionItemDo!=null && collectionItemDo.getResource()!=null && collectionItemDo.getResource().getAnswers()!=null && collectionItemDo.getResource().getType()==9){
			messageBodyText.setText(i18n.GL3234_1()+i18n.GL_SPL_FULLSTOP());
			optionsContainerFpnl.addStyleName("drapDropContainer");
			optionsContainer=new HTAnswerDragPanelVc();
			optionsContainerFpnl.add(optionsContainer);
			Label label = new Label("");
			label.setStyleName("dragDropSpacer");
			optionsContainer.superAdd(label);
			Label toplabel = new Label("");
			toplabel.setStyleName("dragDropSpacer");
			optionsContainer.add(toplabel);
		}else{
			optionsContainerFpnl.removeStyleName("drapDropContainer");
		}
		renderQuestionAnswerOptions();
	}

	private void renderQuestionAnswerOptions(){

		if(collectionItemDo!=null && collectionItemDo.getResource()!=null && collectionItemDo.getResource().getAnswers()!=null){
			final TreeSet<QuestionAnswerDo> answersSet=collectionItemDo.getResource().getAnswers();
			List<QuestionAnswerDo> answerListSet=new ArrayList<QuestionAnswerDo>(answersSet);

			if(collectionItemDo.getResource().getType()==8){

				if(attemptedAnswerDo!=null){
					HTML ansHtml=new HTML(URL.decodeQueryString(attemptedAnswerDo.getAnswersText()));
					ansHtml.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							optionsContainerFpnl.clear();
							setRenderAnswers(answersSet);
							enableCheckAnswerButton();
						}
					});
					
					optionsContainerFpnl.add(ansHtml);
					
					/*String text=removeHtmlTags(attemptedAnswerDo.getAnswersText());
					String[] temp;
					String delimiter;
					if(collectionItemDo.getResource().getHlType().equalsIgnoreCase(i18n.GL3219_1())){
						temp = text.split(SPACE);
						delimiter=SPACE;
					}else {
						temp = text.split("\\.");
						delimiter=DOT;
					}
					for(int k=0;k<temp.length;k++){
						final InlineLabel lbl=new InlineLabel(temp[k]+delimiter);
						if(lbl.getText().startsWith(START_CORRECT_DELIMITER) && (lbl.getText().endsWith(END_CORRECT_DELIMITER+SPACE)|| lbl.getText().trim().endsWith(DOT+END_CORRECT_DELIMITER+SPACE) || lbl.getText().trim().endsWith(END_CORRECT_DELIMITER+DOT))){
							String lblText=lbl.getText().replaceAll("[${}\\[\\]]", "");
							lbl.setText(lblText);
							lbl.getElement().setId(STYLE_CORRECT);
							lbl.addStyleName(STYLE_HIGHLIGHT);
						}
						else if(lbl.getText().startsWith(START_DELIMITER) && (lbl.getText().endsWith(END_DELIMITER+SPACE)|| lbl.getText().trim().endsWith(END_DELIMITER+DOT))){
							String lblText=lbl.getText().replaceAll("[${}\\[\\]]", "");
							lbl.setText(lblText);
							lbl.getElement().setId(STYLE_INCORRECT);
							lbl.addStyleName(STYLE_HIGHLIGHT);
						}

						lbl.addStyleName("htPlayerAns");
						lbl.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								optionsContainerFpnl.clear();
								setRenderAnswers(answersSet);
								enableCheckAnswerButton();
							}
						});
						optionsContainerFpnl.add(lbl);
					}*/

					showPreviousResult();

				}else{
					setRenderAnswers(answersSet);
				}

			}else{
				correctAnsSequence=RandomIterator.getRandomStringArray(answerListSet.size()) ;
				attemptAnsSequence=new String[answerListSet.size()];

				if(attemptedAnswerDo!=null){
					Map<Integer,Boolean> answerOptionCount=attemptedAnswerDo.getAnswerOptionResult();
					int k=0;

					for (Map.Entry<Integer, Boolean> entry : answerOptionCount.entrySet())
					{

						QuestionAnswerDo questionAnswerDo=answerListSet.get(entry.getKey());
						HTAnswerChoiceOptionView htAnswerOptionView=new HTAnswerChoiceOptionView(questionAnswerDo.getAnswerText(),("(" + (char) (65 + k) + ") "));
						htAnswerOptionView.getElement().setId(String.valueOf(entry.getKey()));
						int beforeIndex=k+1;
						optionsContainer.addDraggable(htAnswerOptionView,beforeIndex,entry.getKey());
						showPreviousResult(entry.getKey(),htAnswerOptionView,entry.getValue());
						k++;
					}

				}else{
					for(int i=0;i<answerListSet.size();i++){
						int randomSeq=(Integer) randomSequenceList.get(i);
						QuestionAnswerDo questionAnswerDo=answerListSet.get(randomSeq);
						HTAnswerChoiceOptionView htAnswerOptionView=new HTAnswerChoiceOptionView(questionAnswerDo.getAnswerText(),("(" + (char) (65 + i) + ") "));
						htAnswerOptionView.getElement().setId(String.valueOf(randomSeq));
						int beforeIndex=i+1;
						optionsContainer.addDraggable(htAnswerOptionView,beforeIndex,randomSeq);
						enableCheckAnswerButton();

					}
				}
			}
		}

	}



	public void setRenderAnswers(TreeSet<QuestionAnswerDo> answersSet){
		Iterator<QuestionAnswerDo> answersList=answersSet.iterator();
		while (answersList.hasNext()) {
			QuestionAnswerDo questionAnswerDo=answersList.next();

			String text=StringUtil.removeAllHtmlCss(removeHtmlTags(InfoUtil.removeQuestionTagsOnBoldClick(StringUtil.isEmpty(questionAnswerDo.getAnswerText())?"":questionAnswerDo.getAnswerText())));

			String[] temp;
			if(collectionItemDo.getResource().getHlType().equalsIgnoreCase(i18n.GL3219_1())){
				temp = text.split(DELIMITER_SPACE);

				for(int k=0;k<temp.length;k++){

					final InlineLabel lbl=new InlineLabel(temp[k]+SPACE);
					if(lbl.getText().startsWith(START_DELIMITER) && (lbl.getText().endsWith(END_DELIMITER+SPACE)|| lbl.getText().trim().endsWith(END_DELIMITER+DOT)|| lbl.getText().trim().endsWith(END_DELIMITER+COMMA) || lbl.getText().trim().endsWith(END_DELIMITER+SEMICOLUMN))){
						String lblText=lbl.getText().replaceAll("[${}\\[\\]]", "");
						lbl.setText(lblText);
						lbl.getElement().setId(STYLE_CORRECT);
					}else{
						lbl.getElement().setId(STYLE_INCORRECT);
					}

					lbl.addStyleName("htPlayerAns");
					lbl.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							clearAnswers();

							if(lbl.getStyleName().contains(STYLE_HIGHLIGHT)){
								lbl.removeStyleName(STYLE_HIGHLIGHT);
							}else{

								lbl.addStyleName(STYLE_HIGHLIGHT);
							}
							enableCheckAnswerButton();
						}
					});
					optionsContainerFpnl.add(lbl);
				}
			}else{
				temp = text.split("\\.");
				for(int k=0;k<temp.length;k++){
					if(temp[k].trim().length()>0){
						final InlineLabel lbl=new InlineLabel(temp[k]+DOT);

						if(lbl.getText().startsWith(START_DELIMITER) ||  lbl.getText().startsWith(" [") ){
							String lblText=lbl.getText().replaceAll("[${}\\[\\]]", "");
							lbl.setText(lblText);
							lbl.getElement().setId(STYLE_CORRECT);
						}else{
							String lblText=lbl.getText().replaceAll("[${}\\[\\]]", "");
							lbl.setText(lblText);
							lbl.getElement().setId(STYLE_INCORRECT);
						}
						lbl.addStyleName("htPlayerAns");
						lbl.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								clearAnswers();

								if(lbl.getStyleName().contains(STYLE_HIGHLIGHT)){
									lbl.removeStyleName(STYLE_HIGHLIGHT);
								}else{

									lbl.addStyleName(STYLE_HIGHLIGHT);
								}
								enableCheckAnswerButton();
							}
						});
						optionsContainerFpnl.add(lbl);

					}
				}
			}
		}
	}


	public void showPreviousResult(int answerId,HTAnswerChoiceOptionView htAnswerOptionView,boolean isCorrect){

		if(isCorrect){
			htAnswerOptionView.addStyleName(STYLE_DND_CORRECT);
		}else{
			htAnswerOptionView.addStyleName(STYLE_DND_INCORRECT);
		}

	}

	public void showPreviousResult(){
/*
		for(int i=0;i<optionsContainerFpnl.getWidgetCount();i++){

			InlineLabel lbl=(InlineLabel) optionsContainerFpnl.getWidget(i);
			if(lbl.getStyleName().contains(STYLE_HIGHLIGHT)){

				if(lbl.getElement().getId().equalsIgnoreCase(STYLE_CORRECT)){
					lbl.addStyleName(STYLE_CORRECT);
				}else {
					lbl.addStyleName(STYLE_INCORRECT);
				}
			}
		}*/
	}


	@UiHandler("checkAnswer")
	public void checkButtonClickEvent(ClickEvent event){
		if(isCheckButtonEnabled){
			showCorrectResult();
			isCheckButtonEnabled=false;
			checkAnswer.removeStyleName("primary");
			checkAnswer.addStyleName(STYLE_INACTIVE_BUTTON);
		}

	}

	private void enableCheckAnswerButton(){
		boolean isOptionSelected=false;

		if(collectionItemDo.getResource().getType()==8){
			for(int i=0;i<optionsContainerFpnl.getWidgetCount();i++){
				InlineLabel widget=(InlineLabel) optionsContainerFpnl.getWidget(i);
				if(widget.getStyleName().contains(STYLE_HIGHLIGHT)){
					isOptionSelected=true;
				}
			}
		}else{
			isOptionSelected=true;
		}

		if(isOptionSelected){
			isCheckButtonEnabled=true;
			checkAnswer.removeStyleName(STYLE_INACTIVE_BUTTON);
			checkAnswer.addStyleName("primary");
		}else{
			isCheckButtonEnabled=false;
			checkAnswer.removeStyleName("primary");
			checkAnswer.addStyleName(STYLE_INACTIVE_BUTTON);
		}
	}

	private void showCorrectResult(){

		if(collectionItemDo.getResource().getType()==9){

			boolean HTDragChoiceStatus=true;
			List<Integer> answerIds=new ArrayList<Integer>();
			List<String> userAttemptedValueList=new ArrayList<String>();
			Map<Integer,Boolean> answerOptionResult=new LinkedHashMap<Integer, Boolean>();
			List<AnswerAttemptDo> userAttemptedOptionsList=new ArrayList<AnswerAttemptDo>();
			int j=0;

			//String answerText="";
			//AnswerAttemptDo answerAttemptDo=new AnswerAttemptDo();
			for(int i=0;i<optionsContainer.getWidgetCount();i++){
				Widget widget=optionsContainer.getWidget(i);
				Element el=(Element) widget.getElement().getLastChild();
				if(widget instanceof Draggable && el!=null && !el.getId().equalsIgnoreCase("")){
					Draggable draggable=(Draggable)widget;
					HTAnswerChoiceOptionView htAnswerOption=(HTAnswerChoiceOptionView) draggable.getWidget();
					String answerText=URL.encodeQueryString(htAnswerOption.getAnswerText());
					userAttemptedValueList.add(answerText);
						AnswerAttemptDo answerAttemptDo=new AnswerAttemptDo();
					if(el!=null && el.getId()!=null && !el.getId().equalsIgnoreCase("")){
						answerIds.add(Integer.parseInt(el.getId())+1);
						if(el.getId().equalsIgnoreCase(correctAnsSequence[j])){
							el.addClassName(STYLE_DND_CORRECT);
							answerOptionResult.put(Integer.parseInt(el.getId()), true);
							answerAttemptDo.setStatus("1");
						}else{
							el.addClassName(STYLE_DND_INCORRECT);
							HTDragChoiceStatus=false;
							answerOptionResult.put(Integer.parseInt(el.getId()), false);
							answerAttemptDo.setStatus("0");
						}
						j++;
					}
					answerAttemptDo.setText(answerText);
					answerAttemptDo.setAnswerId(0);
					answerAttemptDo.setOrder("0");

					userAttemptedOptionsList.add(answerAttemptDo);
				}
				
			}

			

			AttemptedAnswersDo attempteAnswersDo=new AttemptedAnswersDo();
			if(collectionItemDo.getResource()!=null && collectionItemDo.getResource().getType()!=null){
				attempteAnswersDo.setQuestionType(collectionItemDo.getResource().getType());
			}
			attempteAnswersDo.setAnswerOptionResult(answerOptionResult);

			setAttemptStatus(collectionItemDo.getCollectionItemId(),attempteAnswersDo);
			userAttemptedAnswerObject(userAttemptedOptionsList);
			increaseUserAttemptCount();

			String attemptStatus=HTDragChoiceStatus==true?"correct":"wrong";
			int score=HTDragChoiceStatus==true?1:0;
			createSesstionItemAttemptForHTDragDrop(answerIds,userAttemptedValueList,attemptStatus);
			userAttemptedValue(userAttemptedValueList);
			setAnswerAttemptSequence(1,score,0);

			boolean isFirstTry=isCheckAnswerButtonClicked;
			isCheckAnswerButtonClicked=true;

			setAnswersDetailsWitithTime(answerIds,HTDragChoiceStatus?1:0,1,score,!isFirstTry);

		}else{

			boolean HTHLChoiceStatus=true;
			List<Integer> answerIds=new ArrayList<Integer>();
			List<String> userAttemptedValueList=new ArrayList<String>();
			Map<Integer,Boolean> answerOptionResult=new LinkedHashMap<Integer, Boolean>();
			List<AnswerAttemptDo> userAttemptedOptionsList=new ArrayList<AnswerAttemptDo>();

			AnswerAttemptDo answerAttemptDo=new AnswerAttemptDo();
			String answerText="";
			HTML ansText=new HTML();
			for(int i=0;i<optionsContainerFpnl.getWidgetCount();i++){

				InlineLabel lbl=(InlineLabel) optionsContainerFpnl.getWidget(i);
				answerIds.add(i+1);
				if(lbl.getStyleName().contains(STYLE_HIGHLIGHT)){

					if(lbl.getElement().getId().equalsIgnoreCase(STYLE_CORRECT)){
						lbl.addStyleName(STYLE_CORRECT);
						answerOptionResult.put(1, true);
						answerAttemptDo.setStatus("1");
						if(collectionItemDo.getResource().getHlType().equalsIgnoreCase(i18n.GL3219_1())){
							//answerText=answerText+START_CORRECT_DELIMITER+lbl.getText().trim()+END_CORRECT_DELIMITER+SPACE;
						}else{
							//String inLblTxt=lbl.getText()+END_CORRECT_DELIMITER+DOT;						
							if(lbl.getText().trim().lastIndexOf(DOT) ==lbl.getText().trim().length()-1){
								//inLblTxt=lbl.getText().replace(DOT, END_CORRECT_DELIMITER+DOT);
							}
							//answerText=answerText+START_CORRECT_DELIMITER+inLblTxt;
						}

					}else {
						lbl.addStyleName(STYLE_INCORRECT);
						answerOptionResult.put(0, true);
						answerAttemptDo.setStatus("0");
						HTHLChoiceStatus=false;
						if(collectionItemDo.getResource().getHlType().equalsIgnoreCase(i18n.GL3219_1())){
							//answerText=answerText+START_DELIMITER+lbl.getText().trim()+END_DELIMITER+SPACE;
						}else{

							/*String inLblTxt=lbl.getText()+END_DELIMITER+DOT;						
							if(lbl.getText().trim().lastIndexOf(DOT) ==lbl.getText().trim().length()-1){
								inLblTxt=lbl.getText().replace(DOT, END_DELIMITER+DOT);
							}
							answerText=answerText+START_DELIMITER+inLblTxt;*/
						}
					}
				}else if(!lbl.getStyleName().contains(STYLE_HIGHLIGHT) && lbl.getElement().getId().equalsIgnoreCase(STYLE_CORRECT)){
					HTHLChoiceStatus=false;
				}else {
					//answerText=answerText+lbl.getText();
				}
				answerText=answerText+lbl.toString();
				ansText.setHTML(lbl.toString());
			}
			//userAttemptedValueList.add("["+answerText+"]");
			//answerAttemptDo.setText("\""+answerText);
			ansText.setHTML(answerText);
			userAttemptedValueList.add(URL.encodeQueryString(ansText.toString()));
			answerAttemptDo.setText(URL.encodeQueryString(ansText.toString()));
			answerAttemptDo.setAnswerId(0);
			answerAttemptDo.setOrder("0");
			userAttemptedOptionsList.add(answerAttemptDo);
			AttemptedAnswersDo attempteAnswersDo=new AttemptedAnswersDo();
			if(collectionItemDo.getResource()!=null && collectionItemDo.getResource().getType()!=null){
				attempteAnswersDo.setQuestionType(collectionItemDo.getResource().getType());
			}
			attempteAnswersDo.setAnswersText(URL.encodeQueryString(optionsContainerFpnl.toString()));
			attempteAnswersDo.setAnswerOptionResult(answerOptionResult);

			setAttemptStatus(collectionItemDo.getCollectionItemId(),attempteAnswersDo);

			userAttemptedAnswerObject(userAttemptedOptionsList);
			increaseUserAttemptCount();

			String attemptStatus=HTHLChoiceStatus==true?"correct":"wrong";
			int score=HTHLChoiceStatus==true?1:0;
			createSesstionItemAttemptForHTDragDrop(answerIds,userAttemptedValueList,attemptStatus);
			userAttemptedValue(userAttemptedValueList);
			setAnswerAttemptSequence(1,score,0);

			boolean isFirstTry=isCheckAnswerButtonClicked;
			isCheckAnswerButtonClicked=true;

			setAnswersDetailsWitithTime(answerIds,HTHLChoiceStatus?1:0,1,score,!isFirstTry);
		}

		isUserAnswerAttempted(true);
	}

	private void clearAnswers(){
		for(int i=0;i<optionsContainerFpnl.getWidgetCount();i++){

			InlineLabel lbl=(InlineLabel) optionsContainerFpnl.getWidget(i);

			if(lbl.getStyleName().contains(STYLE_HIGHLIGHT)){

				if(lbl.getElement().getId().equalsIgnoreCase(STYLE_CORRECT)){
					lbl.removeStyleName(STYLE_CORRECT);
				}else {
					lbl.removeStyleName(STYLE_INCORRECT);
				}
			}
		}
	}

	private void clearReorderAnswers(){
		
		if(optionsContainer!=null){

			for(int i=0;i<optionsContainer.getWidgetCount();i++){
				Widget widget=optionsContainer.getWidget(i);
				Element el=(Element) widget.getElement().getLastChild();
				if(el != null && el.getId()!=null && !el.getId().equalsIgnoreCase("")){
					el.removeClassName(STYLE_DND_CORRECT);
					el.removeClassName(STYLE_DND_INCORRECT);
				}
			}
		}
	}

	/**
	 * This method is used to remove HTMLTags from the String
	 * @param text
	 * @return
	 */
	private String removeHtmlTags(String text){
		/**
		 * Commented the following line to fix issue with displaying math symbols.
		 */
		text=text.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
		return text;
	}

	ResetDragDropHandler resetReorderData=new ResetDragDropHandler() {

		@Override
		public void resetReorder() {

			if(checkAnswer.getStyleName().contains(STYLE_INACTIVE_BUTTON)){
				clearReorderAnswers();
				enableCheckAnswerButton();
			}
		}
	};

	public abstract void createSesstionItemAttemptForHTDragDrop(List<Integer> answerIds,List<String> userAttemptedAnswers,String attemptStatus);
	public abstract void setAttemptStatus(String collectionItemId,AttemptedAnswersDo attemptAnswerDo);
	public abstract void setAnswerAttemptSequence(int attemptSequence,int attemptStatus, int answerId);
	public void isUserAnswerAttempted(boolean isUserAttemptedResult){}
	public void setAnswersDetailsWitithTime(List<Integer> answerIds,int answerStatus,int answerSequence,int score,boolean isFirstTry){}

	public abstract void increaseUserAttemptCount();
	public abstract void userAttemptedValue(List<String> userAttemptedValueList);
	public abstract void userAttemptedAnswerObject(List<AnswerAttemptDo> answerOptionAttemptList);
}
