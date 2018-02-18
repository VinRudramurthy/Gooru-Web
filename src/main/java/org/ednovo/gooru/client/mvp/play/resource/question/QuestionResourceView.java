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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.QuestionAnswerDo;
import org.ednovo.gooru.application.shared.model.content.QuestionHintsDo;
import org.ednovo.gooru.application.shared.model.player.AnswerAttemptDo;
import org.ednovo.gooru.shared.util.AttemptedAnswersDo;
import org.ednovo.gooru.shared.util.InfoUtil;
import org.ednovo.gooru.shared.util.RandomIterator;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class QuestionResourceView extends BaseViewWithHandlers<QuestionResourceUiHandlers> implements IsQuestionResourceView{

	@UiField HTMLPanel explanationContainer,questiontext;
	@UiField Image openEndedQuestionImage;
	@UiField Button hintsButton,explanaionButton;
	@UiField FlowPanel hintsContainer,questionContainer;
	@UiField HTML openEndedQuestionText;
	private CollectionItemDo collectionItemDo;
	private int hintsLength=0;

	private MultipleChoicesQuestionWidget multipleChoicesQuestionWidget=null;
	private MultipleAnswersQuestionWidget multipleAnswersQuestionWidget=null;
	private OpendEndedQuestionWidget opendEndedQuestionWidget=null;
	private FillInTheBlankQuestionWidget fillInTheBlankQuestionWidget=null;
	private HotTextQuestionWidget HotTextQuestionWidget=null;
	private HotSpotQuestionWidget HotSpotQuestionWidget=null;

	private Map<String, List> reorderMap=new HashMap<String, List>();

	private static QuestionResourceViewUiBinder uiBinder = GWT.create(QuestionResourceViewUiBinder.class);

	interface QuestionResourceViewUiBinder extends UiBinder<Widget, QuestionResourceView> {

	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public QuestionResourceView(){
		setWidget(uiBinder.createAndBindUi(this));
		questiontext.getElement().setInnerHTML(i18n.GL0308());
		questiontext.getElement().setId("pnlQuestiontext");
		questiontext.getElement().setAttribute("alt",i18n.GL0308());
		questiontext.getElement().setAttribute("title",i18n.GL0308());

		hintsButton.setText(i18n.GL0667());
		hintsButton.getElement().setId("btnHintsButton");
		hintsButton.getElement().setAttribute("alt",i18n.GL0667());
		hintsButton.getElement().setAttribute("title",i18n.GL0667());

		explanaionButton.setText(i18n.GL0316());
		explanaionButton.getElement().setId("btnexplanaionButton");
		explanaionButton.getElement().setAttribute("alt",i18n.GL0316());
		explanaionButton.getElement().setAttribute("title",i18n.GL0316());

		openEndedQuestionText.getElement().setId("htmlOpenEndedQuestionText");
		questionContainer.getElement().setId("fpnlQuestionContainer");
		openEndedQuestionImage.getElement().setId("imgOpenEndedQuestionImage");
		hintsContainer.getElement().setId("fpnlHintsContainer");
		explanationContainer.getElement().setId("pnlExplanationContainer");

	}

	@Override
	public void showQuestionPreview(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo){
		this.collectionItemDo=collectionItemDo;
		renderQuestionView();
		renderAnswerView(attemptedAnswerDo);
	}

	private void renderQuestionView(){
		hintsLength=0;
		if(collectionItemDo!=null && collectionItemDo.getResource()!=null){
			String titlelbl1=InfoUtil.removeQuestionTagsOnBoldClick(StringUtil.isEmpty(collectionItemDo.getResource().getQuestionText())?"":collectionItemDo.getResource().getQuestionText());
			openEndedQuestionText.setHTML(removeHtmlTags(titlelbl1));
			openEndedQuestionText.getElement().setAttribute("alt",StringUtil.removeAllHtmlCss(StringUtil.isEmpty(collectionItemDo.getResource().getQuestionText())?"":collectionItemDo.getResource().getQuestionText()));
			openEndedQuestionText.getElement().setAttribute("title",StringUtil.removeAllHtmlCss(StringUtil.isEmpty(collectionItemDo.getResource().getQuestionText())?"":collectionItemDo.getResource().getQuestionText()));
			if(collectionItemDo.getResource().getHints()!=null && collectionItemDo.getResource().getHints().size()>0){
				hintsButton.setText(" "+i18n.GL0317()+" ("+collectionItemDo.getResource().getHints().size()+" Left)");
				hintsButton.getElement().setAttribute("alt"," "+i18n.GL0317()+" ("+collectionItemDo.getResource().getHints().size()+" Left)");
				hintsButton.getElement().setAttribute("title"," "+i18n.GL0317()+" ("+collectionItemDo.getResource().getHints().size()+" Left)");
			}else{
				hintsButton.setStyleName("hintsInActiveButton");
			}
			if(collectionItemDo.getResource().getExplanation()!=null && collectionItemDo.getResource().getExplanation().trim().length()>0){
			}
			else{
				explanaionButton.setStyleName("hintsInActiveButton");
			}
			if(!StringUtil.isEmpty(getQuestionImage())){
				openEndedQuestionImage.setUrl(getQuestionImage());
			}
		}
	}
	private void renderAnswerView(AttemptedAnswersDo attemptedAnswerDo){
		clearAnswerOptionsContainer();
		if(collectionItemDo!=null && collectionItemDo.getResource()!=null){

			if(collectionItemDo.getResource().getType()==null){
				collectionItemDo.getResource().setType(8);
			}

			if(collectionItemDo.getResource().getType()==1||collectionItemDo.getResource().getType()==3){
				multipleChoicesQuestionWidget=new MultipleChoicesQuestionWidget(collectionItemDo,attemptedAnswerDo);
				questionContainer.add(multipleChoicesQuestionWidget);
			}else if(collectionItemDo.getResource().getType()==6){
				opendEndedQuestionWidget=new OpendEndedQuestionWidget(collectionItemDo,attemptedAnswerDo);
				questionContainer.add(opendEndedQuestionWidget);
			}
			else if(collectionItemDo.getResource().getType()==4){
				fillInTheBlankQuestionWidget=new FillInTheBlankQuestionWidget(collectionItemDo,attemptedAnswerDo);
				questionContainer.add(fillInTheBlankQuestionWidget);
			}else if(collectionItemDo.getResource().getType()==7){
				multipleAnswersQuestionWidget=new MultipleAnswersQuestionWidget(collectionItemDo,attemptedAnswerDo);
				questionContainer.add(multipleAnswersQuestionWidget);
			}else if(collectionItemDo.getResource().getType()==8 || collectionItemDo.getResource().getType()==9){
				List randomList=null;
				if(collectionItemDo!=null && collectionItemDo.getResource()!=null && collectionItemDo.getResource().getAnswers()!=null){
					TreeSet<QuestionAnswerDo> answersSet=collectionItemDo.getResource().getAnswers();
					List<QuestionAnswerDo> answerListSet=new ArrayList<QuestionAnswerDo>(answersSet);
					randomList= RandomIterator.getRandomList(answerListSet.size());
				}
				if(!reorderMap.containsKey(collectionItemDo.getResource().getGooruOid())){
					reorderMap.put(collectionItemDo.getResource().getGooruOid(), randomList);
				}
				HotTextQuestionWidget=new HotTextQuestionWidget(collectionItemDo, attemptedAnswerDo,reorderMap.get(collectionItemDo.getResource().getGooruOid()));
				questionContainer.add(HotTextQuestionWidget);
			}else if(collectionItemDo.getResource().getType()==10){
				HotSpotQuestionWidget=new HotSpotQuestionWidget(collectionItemDo,attemptedAnswerDo);
				questionContainer.add(HotSpotQuestionWidget);
			}else if(collectionItemDo.getResource().getType()==11){
				HotSpotQuestionWidget=new HotSpotQuestionWidget(collectionItemDo,attemptedAnswerDo);
				questionContainer.add(HotSpotQuestionWidget);
			}
		}
	}
	private void clearAnswerOptionsContainer(){
		int widgetCount=questionContainer.getWidgetCount();
		if(widgetCount>1){
			questionContainer.getWidget(widgetCount-1).removeFromParent();
		}
	}
	@UiHandler("hintsButton")
	public void ClickOnHintButton(ClickEvent clickEvent){
		if(hintsButton.getStyleName().equals("hintsActiveButton")){
			if(collectionItemDo.getResource().getHints().size()>hintsLength){
				hintsContainer.add(getHTML(getQuestionHintsDo(hintsLength).getHintText(),"hintsText"));

				if(collectionItemDo.getResource().getType()==8 || collectionItemDo.getResource().getType()==9 || collectionItemDo.getResource().getType()==10 || collectionItemDo.getResource().getType()==11){

				}else{
					startHintDataLogEvent(getQuestionHintsDo(hintsLength).getHintId());
				}
				hintsButton.setText(""+i18n.GL0317()+" ("+((collectionItemDo.getResource().getHints().size()-hintsLength)-1)+" Left)");
				hintsButton.getElement().setAttribute("alt"," "+i18n.GL0317()+" ("+collectionItemDo.getResource().getHints().size()+" Left)");
				hintsButton.getElement().setAttribute("title"," "+i18n.GL0317()+" ("+collectionItemDo.getResource().getHints().size()+" Left)");
				hintsLength++;
				if(collectionItemDo.getResource().getHints().size()==hintsLength){
					hintsButton.setStyleName("hintsInActiveButton");
				}
			}else{
				hintsButton.setStyleName("hintsInActiveButton");
			}
		}
	}
	public QuestionHintsDo getQuestionHintsDo(int hintPosition){
		TreeSet<QuestionHintsDo> questionHintsList=collectionItemDo.getResource().getHints();
		Iterator<QuestionHintsDo> it = questionHintsList.iterator();
		int i = 0;
		QuestionHintsDo currentQuestionHintsDo= null;
		while(it.hasNext()) {
			currentQuestionHintsDo = it.next();
			if(i==hintPosition){
				return currentQuestionHintsDo;
			}
		   i++;
		}
		return currentQuestionHintsDo;
	}
	@UiHandler("explanaionButton")
	public void ClickOnExplanationButton(ClickEvent clickEvent){
		if(explanaionButton.getStyleName().equals("hintsActiveButton")){
			explanationContainer.add(getHTML(collectionItemDo.getResource().getExplanation()));
			explanationContainer.addStyleName("explanationTextBorder");
			explanaionButton.setStyleName("hintsInActiveButton");
			startExplanationDataLogEvent(collectionItemDo.getResource().getExplanation());
		}
	}
	@UiHandler("openEndedQuestionImage")
	public void defaultQuesoinImage(ErrorEvent event){
		openEndedQuestionImage.getElement().removeAttribute("src");
	}
	private HTML getHTML(String html){
		html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
		HTML contentHtml=new HTML(html);
		contentHtml.setStyleName("");
		return contentHtml;
	}
	private HTML getHTML(String html,String styleName){
		html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
		HTML contentHtml=new HTML(html);
		contentHtml.setStyleName(styleName);
		return contentHtml;
	}

	public String removeHtmlTags(String htmlText){
		htmlText = htmlText.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
		return htmlText;
	}

	@Override
	public void resetQuestionView() {
		openEndedQuestionText.setHTML("");
		hintsButton.setText(i18n.GL0667());
		hintsButton.getElement().setAttribute("alt",i18n.GL0667());
		hintsButton.getElement().setAttribute("title",i18n.GL0667());
		hintsButton.setStyleName("hintsActiveButton");
		explanaionButton.setStyleName("hintsActiveButton");
		explanationContainer.removeStyleName("explanationTextBorder");
		hintsContainer.clear();
		explanationContainer.clear();
		clearAnswerOptionsContainer();
		openEndedQuestionImage.getElement().removeAttribute("src");
	}
	private String getQuestionImage(){
		String thumbnailImage="";
		String assetName="";
		try{
			if(collectionItemDo.getResource().getAssets()!=null&&collectionItemDo.getResource().getAssets().size()>0){
				assetName=collectionItemDo.getResource().getAssets().get(0).getAsset().getName();
				thumbnailImage=collectionItemDo.getResource().getAssetURI()+collectionItemDo.getResource().getFolder()+assetName;
			}else if(collectionItemDo.getResource().getThumbnails() != null && collectionItemDo.getResource().getThumbnails().getUrl() != null){
				thumbnailImage=collectionItemDo.getResource().getThumbnails().getUrl();
			}
		}catch(Exception e){
			AppClientFactory.printSevereLogger("QuestionResourceView : getQuestionImage : "+e.getMessage());
		}
		return thumbnailImage;
	}

	public void startHintDataLogEvent(int hintId) {
		getUiHandlers().startHintDataLogEvent(hintId);
		getUiHandlers().setHintIdWithTime(hintId);
	}

	public void startExplanationDataLogEvent(String explanation) {
		getUiHandlers().startExplanationDataLogEvent();
		getUiHandlers().setExplanationIdWithTime(explanation);
	}

	public class MultipleChoicesQuestionWidget extends MultipleChoicesQuestionView{
		private AttemptedAnswersDo attemptedAnswerDo=null;
		public MultipleChoicesQuestionWidget(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo){
			super(collectionItemDo,attemptedAnswerDo);
			this.attemptedAnswerDo=attemptedAnswerDo;
		}
		@Override
		public void createSessionItemAttempt(int answerId,String answerAttemptStatus) {
			getUiHandlers().createSessionItemAttempt(collectionItemDo.getResource().getGooruOid(),answerId, answerAttemptStatus);
		}
		public void setAttemptStatus(String collectionItemId,AttemptedAnswersDo attemptAnswerDo){
			getUiHandlers().setAttemptStatus(collectionItemId, attemptAnswerDo);
		}
		public void setAnswerAttemptSequence(int attemptSequence,int attemptStatus, int answerId) {
			getUiHandlers().setAnswerAttemptSequence(attemptSequence, attemptStatus, answerId);
		}
		public void isUserAnswerAttempted(boolean isUserAttemptedResult){
			getUiHandlers().setUserAttemptedQuestionTypeAndStatus(isUserAttemptedResult,1);
		}
		public void setAnswersDetailsWitithTime(int answerId,int answerStatus,int answerSequence,int score,boolean isFirstTry){
			getUiHandlers().setAnswerIdWithTime(answerId, answerStatus, answerSequence);
			getUiHandlers().setResourceScore(score);
		}
		public void increaseUserAttemptCount(){
			getUiHandlers().increaseUserAttemptCount();
		}
		public void userAttemptedAnswerObject(List<AnswerAttemptDo> answerOptionAttemptList){
			getUiHandlers().userAttemptedAnswerObject(answerOptionAttemptList);
		}
	}

	public class MultipleAnswersQuestionWidget extends MultipleAnswersQuestionView{
		private AttemptedAnswersDo attemptedAnswerDo=null;
		public MultipleAnswersQuestionWidget(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo){
			super(collectionItemDo,attemptedAnswerDo);
			this.attemptedAnswerDo=attemptedAnswerDo;
		}
		@Override
		public void createSessionItemAttempt(int answerId,String answerAttemptStatus) {
			getUiHandlers().createSessionItemAttempt(collectionItemDo.getResource().getGooruOid(),answerId, answerAttemptStatus);
		}
		public void setAttemptStatus(String collectionItemId,AttemptedAnswersDo attemptAnswerDo){
			getUiHandlers().setAttemptStatus(collectionItemId, attemptAnswerDo);
		}
		public void setAnswerAttemptSequence(int attemptSequence,int attemptStatus, int answerId) {
			getUiHandlers().setAnswerAttemptSequence(attemptSequence, attemptStatus, answerId);
		}
		public void isUserAnswerAttempted(boolean isUserAttemptedResult){
			getUiHandlers().setUserAttemptedQuestionTypeAndStatus(isUserAttemptedResult,1);
		}
		public void setAnswersDetailsWitithTime(List<Integer> answerId,int answerStatus,int answerSequence,int score,boolean isFirstTry){
			getUiHandlers().setAnswerIdWithTimeForMa(answerId, answerStatus, answerSequence);
			getUiHandlers().setResourceScore(score);
		}
		public void increaseUserAttemptCount(){
			getUiHandlers().increaseUserAttemptCount();
		}
		@Override
		public void userAttemptedValue(List<String> userAttemptedValueList) {
			String attemptedAnswersText="";
			if(userAttemptedValueList!=null && userAttemptedValueList.size()>0){
				for(int i=0;i<userAttemptedValueList.size();i++){
					attemptedAnswersText=attemptedAnswersText+"["+userAttemptedValueList.get(i)+"]";
					if((i+1)!=userAttemptedValueList.size()){
						attemptedAnswersText=attemptedAnswersText+",";
					}
				}
			}
			getUiHandlers().setOeQuestionAnswerText(attemptedAnswersText);
		}
		public void createSesstionItemAttemptForMultipleAnswer(List<Integer> answerIds,List<String> userAttemptedAnswers,String attemptStatus){
			String attemptedAnswers="";
			String answerId="";
			if(userAttemptedAnswers!=null && userAttemptedAnswers.size()>0){
					for(int i=0;i<userAttemptedAnswers.size();i++){
						attemptedAnswers=attemptedAnswers+userAttemptedAnswers.get(i);
						try{
							answerId=answerId+(StringUtil.toString(answerIds.get(i)));
						}catch(Exception e){
							AppClientFactory.printSevereLogger("QuestionResourceView : createSesstionItemAttemptForMultipleAnswer : "+e.getMessage());
						}
						if((i+1)!=userAttemptedAnswers.size()){
							attemptedAnswers=attemptedAnswers+",";
							answerId=answerId+",";
						}
					}
			}
			getUiHandlers().createSesstionItemAttemptOe(collectionItemDo.getResource().getGooruOid(),answerId,attemptStatus,attemptedAnswers);
		}
		public void userAttemptedAnswerObject(List<AnswerAttemptDo> answerOptionAttemptList){
			getUiHandlers().userAttemptedAnswerObject(answerOptionAttemptList);
		}
	}

	public class OpendEndedQuestionWidget extends OpendEndedQuestionView{
		public OpendEndedQuestionWidget(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo){
			super(collectionItemDo,attemptedAnswerDo);
		}
		@Override
		public void createSesstionItemAttemptOe(String answerId,String answerText){
			getUiHandlers().createSesstionItemAttemptOe(collectionItemDo.getResource().getGooruOid(),answerId, "pending",answerText);
		}
		public void setAttemptStatus(String collectionItemId,AttemptedAnswersDo attemptAnswerDo){
			attemptAnswerDo.setQuestionType(collectionItemDo.getResource().getType());
			getUiHandlers().setAttemptStatus(collectionItemDo.getCollectionItemId(), attemptAnswerDo);
		}
		public void setOeQuestionAnswerText(String answerText) {
			getUiHandlers().setOeQuestionAnswerText(answerText);
		}
		public void saveOeQuestionAnswerDataLogEvent() {
			getUiHandlers().saveOeQuestionAnswerDataLogEvent();
		}
		public void isUserAnswerAttempted(boolean isUserAttemptedResult){
			getUiHandlers().setUserAttemptedQuestionTypeAndStatus(isUserAttemptedResult,6);
		}
		public void triggerSaveOeAnswerTextDataEvent(){
			getUiHandlers().triggerSaveOeAnswerTextDataEvent();
		}
		public void increaseUserAttemptCount(){
			getUiHandlers().increaseUserAttemptCount();
		}
		public void isOeAnswerSubmited(boolean isOeAnswerSubmited){
			getUiHandlers().isOeAnswerSubmited(isOeAnswerSubmited);
		}
		public void userAttemptedAnswerObject(List<AnswerAttemptDo> answerOptionAttemptList){
			getUiHandlers().userAttemptedAnswerObject(answerOptionAttemptList);
		}
	}

	public class FillInTheBlankQuestionWidget extends FillIntheBlankQuestionView{
		private AttemptedAnswersDo attemptedAnswerDo=null;
		public FillInTheBlankQuestionWidget(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo){
			super(collectionItemDo,attemptedAnswerDo);
			this.attemptedAnswerDo=attemptedAnswerDo;
		}
		public void setAttemptStatus(String collectionItemId,AttemptedAnswersDo attemptAnswerDo){
			if(collectionItemDo!=null){
			attemptAnswerDo.setQuestionType(collectionItemDo.getResource().getType());
			getUiHandlers().setAttemptStatus(collectionItemDo.getCollectionItemId(), attemptAnswerDo);
			}
		}
		public void increaseUserAttemptCount(){
			getUiHandlers().increaseUserAttemptCount();
		}
		@Override
		public void createSesstionItemAttemptOe(List<Integer> answerIds,List<String> userAttemptedAnswers,String attemptStatus){
			String attemptedAnswers="";
			String answerId="";
			if(userAttemptedAnswers!=null && userAttemptedAnswers.size()>0){
				for(int i=0;i<userAttemptedAnswers.size();i++){
					attemptedAnswers=attemptedAnswers+userAttemptedAnswers.get(i);
					try{
						answerId=answerId+(StringUtil.toString(answerIds.get(i)));
					}catch(Exception e){
						AppClientFactory.printSevereLogger("QuestionResourceView : createSesstionItemAttemptOe : "+e.getMessage());
					}
					if((i+1)!=userAttemptedAnswers.size()){
						attemptedAnswers=attemptedAnswers+",";
						answerId=answerId+",";
					}
				}
			}
			getUiHandlers().createSesstionItemAttemptOe(collectionItemDo.getResource().getGooruOid(),answerId,attemptStatus,attemptedAnswers);
		}
		public void isUserAnswerAttempted(boolean isUserAttemptedResult){
			getUiHandlers().setUserAttemptedQuestionTypeAndStatus(isUserAttemptedResult,4);
		}
		public void setFibAnswerIdsWithTime(List<Integer> attemptAnswerIds,List<Integer> attemptTrySequenceArray,List<Integer> attemptStatusArray,Integer score,boolean isFirstAttempt,List<String> attemptedAnswersList){
			String attemptedAnswersText="";
			if(attemptedAnswersList!=null && attemptedAnswersList.size()>0){
				for(int i=0;i<attemptedAnswersList.size();i++){
					attemptedAnswersText=attemptedAnswersText+"["+attemptedAnswersList.get(i)+"]";
					if((i+1)!=attemptedAnswersList.size()){
						attemptedAnswersText=attemptedAnswersText+",";
					}
				}
			}
			getUiHandlers().setOeQuestionAnswerText(attemptedAnswersText);
			getUiHandlers().setFibAnswerIdsWithTime(attemptAnswerIds,attemptTrySequenceArray,attemptStatusArray);
			getUiHandlers().setResourceScore(score);
		}
		public void userAttemptedAnswerObject(List<AnswerAttemptDo> answerOptionAttemptList){
			getUiHandlers().userAttemptedAnswerObject(answerOptionAttemptList);
		}
	}
	@Override
	public void createSessionAttemptTryWhenNavigation(int questionType) {
		if(questionType==1){
			if(multipleChoicesQuestionWidget!=null){
				//multipleChoicesQuestionWidget.createSessionItemAttemptWhenNavigation();
			}
		}else if(questionType==4){
			if(fillInTheBlankQuestionWidget!=null){
				//fillInTheBlankQuestionWidget.createSesstionItemAttemptOeWhenNavigation();
			}
		}else if(questionType==6){
			if(opendEndedQuestionWidget!=null){
				//opendEndedQuestionWidget.createSesstionItemAttemptOeWhenNavigation();
			}
		}
	}
	@Override
	public FlowPanel getQuestionContainer(){
		return questionContainer;
	}

	public class HotTextQuestionWidget extends HotTextAnswersQuestionView{
		private AttemptedAnswersDo attemptedAnswerDo=null;
		public HotTextQuestionWidget(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo,List randomList){
			super(collectionItemDo,attemptedAnswerDo,randomList);
			this.attemptedAnswerDo=attemptedAnswerDo;
		}
		@Override
		public void createSesstionItemAttemptForHTDragDrop(List<Integer> answerIds, List<String> userAttemptedAnswers,String attemptStatus) {
			String attemptedAnswers="";
			String answerId="";
			if(userAttemptedAnswers!=null && userAttemptedAnswers.size()>0){
					for(int i=0;i<userAttemptedAnswers.size();i++){
						attemptedAnswers=attemptedAnswers+userAttemptedAnswers.get(i);
						try{
							answerId=answerId+(StringUtil.toString(answerIds.get(i)));
						}catch(Exception e){
							AppClientFactory.printSevereLogger("QuestionResourceView : createSesstionItemAttemptForHTDragDrop : "+e.getMessage());
						}
						if((i+1)!=userAttemptedAnswers.size()){
							attemptedAnswers=attemptedAnswers+",";
							answerId=answerId+",";
						}
					}
			}
			getUiHandlers().createSesstionItemAttemptOe(collectionItemDo.getResource().getGooruOid(),answerId,attemptStatus,attemptedAnswers);

		}
		@Override
		public void increaseUserAttemptCount() {
			getUiHandlers().increaseUserAttemptCount();
		}
		@Override
		public void setAttemptStatus(String collectionItemId,AttemptedAnswersDo attemptAnswerDo) {
			getUiHandlers().setAttemptStatus(collectionItemId, attemptAnswerDo);
		}
		@Override
		public void userAttemptedValue(List<String> userAttemptedValueList) {
			String attemptedAnswersText="";
			if(userAttemptedValueList!=null && userAttemptedValueList.size()>0){
				for(int i=0;i<userAttemptedValueList.size();i++){
					attemptedAnswersText=attemptedAnswersText+userAttemptedValueList.get(i);
					if((i+1)!=userAttemptedValueList.size()){
						attemptedAnswersText=attemptedAnswersText+",";
					}
				}
			}
			getUiHandlers().setOeQuestionAnswerText(attemptedAnswersText);
		}
		@Override
		public void userAttemptedAnswerObject(	List<AnswerAttemptDo> answerOptionAttemptList) {
			getUiHandlers().userAttemptedAnswerObject(answerOptionAttemptList);
		}
		@Override
		public void setAnswerAttemptSequence(int attemptSequence,int attemptStatus, int answerId) {
			getUiHandlers().setAnswerAttemptSequence(attemptSequence, attemptStatus, answerId);
		}
		public void isUserAnswerAttempted(boolean isUserAttemptedResult){
			getUiHandlers().setUserAttemptedQuestionTypeAndStatus(isUserAttemptedResult,1);
		}
		public void setAnswersDetailsWitithTime(List<Integer> answerId,int answerStatus,int answerSequence,int score,boolean isFirstTry){
			getUiHandlers().setAnswerIdWithTimeForHT(answerId, answerStatus, answerSequence);
			getUiHandlers().setResourceScore(score);
		}
	}
	public class HotSpotQuestionWidget extends HotSpotAnswersQuestionView{
		private AttemptedAnswersDo attemptedAnswerDo=null;
		public HotSpotQuestionWidget(CollectionItemDo collectionItemDo,AttemptedAnswersDo attemptedAnswerDo){
			super(collectionItemDo,attemptedAnswerDo);
			this.attemptedAnswerDo=attemptedAnswerDo;
		}
		@Override
		public void createSessionItemAttempt(int answerId,	String answerAttemptStatus) {
			getUiHandlers().createSessionItemAttempt(collectionItemDo.getResource().getGooruOid(),answerId, answerAttemptStatus);
		}
		@Override
		public void setAttemptStatus(String collectionItemId,AttemptedAnswersDo attemptAnswerDo) {
			getUiHandlers().setAttemptStatus(collectionItemId, attemptAnswerDo);
		}
		@Override
		public void setAnswerAttemptSequence(int attemptSequence,int attemptStatus, int answerId) {
			getUiHandlers().setAnswerAttemptSequence(attemptSequence, attemptStatus, answerId);
		}
		public void isUserAnswerAttempted(boolean isUserAttemptedResult){
			getUiHandlers().setUserAttemptedQuestionTypeAndStatus(isUserAttemptedResult,1);
		}
		public void setAnswersDetailsWitithTime(List<Integer> answerId,int answerStatus,int answerSequence,int score,boolean isFirstTry){
			getUiHandlers().setAnswerIdWithTimeForHT(answerId, answerStatus, answerSequence);
			getUiHandlers().setResourceScore(score);
		}
		public void increaseUserAttemptCount(){
			getUiHandlers().increaseUserAttemptCount();
		}
		@Override
		public void userAttemptedValue(List<String> userAttemptedValueList) {
			String attemptedAnswersText="";
			if(userAttemptedValueList!=null && userAttemptedValueList.size()>0){
				for(int i=0;i<userAttemptedValueList.size();i++){
					attemptedAnswersText=attemptedAnswersText+"["+userAttemptedValueList.get(i)+"]";
					if((i+1)!=userAttemptedValueList.size()){
						attemptedAnswersText=attemptedAnswersText+",";
					}
				}
			}
			getUiHandlers().setOeQuestionAnswerText(attemptedAnswersText);
		}
		public void createSesstionItemAttemptForMultipleAnswer(List<Integer> answerIds,List<String> userAttemptedAnswers,String attemptStatus){
			String attemptedAnswers="";
			String answerId="";
			if(userAttemptedAnswers!=null && userAttemptedAnswers.size()>0){
					for(int i=0;i<userAttemptedAnswers.size();i++){
						attemptedAnswers=attemptedAnswers+userAttemptedAnswers.get(i);
						try{
							answerId=answerId+(StringUtil.toString(answerIds.get(i)));
						}catch(Exception e){
							AppClientFactory.printSevereLogger("QuestionResourceView : createSesstionItemAttemptForMultipleAnswer : "+e.getMessage());
						}
						if((i+1)!=userAttemptedAnswers.size()){
							attemptedAnswers=attemptedAnswers+",";
							answerId=answerId+",";
						}
					}
			}
			getUiHandlers().createSesstionItemAttemptOe(collectionItemDo.getResource().getGooruOid(),answerId,attemptStatus,attemptedAnswers);
		}
		public void userAttemptedAnswerObject(List<AnswerAttemptDo> answerOptionAttemptList){
			getUiHandlers().userAttemptedAnswerObject(answerOptionAttemptList);
		}
	}
}
