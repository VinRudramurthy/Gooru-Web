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
package org.ednovo.gooru.client.mvp.assessments.play.collection.end;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.analytics.AssessmentSummaryStatusDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.MetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.PrintUserDataDO;
import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;
import org.ednovo.gooru.application.shared.model.analytics.session;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.EmailPopup;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.SummaryAnswerStatusPopup;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsReactionWidget;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.client.mvp.analytics.util.Print;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.assessmentreport.AssessmentProgressReportChildView;
import org.ednovo.gooru.client.mvp.search.SearchResultWrapperCBundle;
import org.ednovo.gooru.client.uc.H2Panel;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.H4Panel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.InfoUtil;
import org.ednovo.gooru.shared.util.StringUtil;
import org.gwt.advanced.client.ui.widget.AdvancedFlexTable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class AssessmentsEndView extends BaseViewWithHandlers<AssessmentsEndUiHandlers> implements IsAssessmentsEndView,ClientConstants{
	@UiField FlowPanel PrintPnl, studentAssessmentReportViewContainer, assessmentReportContainer;
	@UiField FlowPanel progressRadial;
	@UiField HTMLPanel  collectionSummaryText,loadingImageLabel, questionsTable;
	@UiField ListBox sessionsDropDown;
	@UiField Image collectionImage;
	@UiField InlineLabel collectionResourcesCount,correctStatus;
	@UiField H3Panel collectionTitle;
	@UiField H4Panel scoreTitle;
	@UiField H2Panel score;
	@UiField PPanel lastModifiedTime,goal;
	@UiField HTMLPanel printWidget;
	@UiField Button printButton, downloadButton;
	@UiField Frame downloadFile;

	HTMLPanel printScoredData=new HTMLPanel("");
	EmailPopup emailPopup=null;

	Map<String, Long> sessionData=new HashMap<String, Long>();
	PrintUserDataDO printData=new PrintUserDataDO();
	String urlDomain = "";
	String style="";

	private CollectionDo collectionDo=null;


	private static AssessmentsPlayerMetadataViewUiBinder uiBinder = GWT.create(AssessmentsPlayerMetadataViewUiBinder.class);

	interface AssessmentsPlayerMetadataViewUiBinder extends UiBinder<Widget, AssessmentsEndView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public boolean isTableToDestroy=false;

	@Inject
	public AssessmentsEndView(){
		setWidget(uiBinder.createAndBindUi(this));
		setLabelAndIds();
		urlDomain=Window.Location.getProtocol()+"//"+Window.Location.getHost();
		style="<link rel='styleSheet' type='text/css' href='"+urlDomain+"/css/main-styles.min.css'>";
		downloadFile.setVisible(false);
		downloadFile.setUrl("");
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		SearchResultWrapperCBundle.INSTANCE.css().ensureInjected();
		sessionsDropDown.addChangeHandler(new StudentsSessionsChangeHandler());

	}
	public class StudentsSessionsChangeHandler implements ChangeHandler{
		@Override
		public void onChange(ChangeEvent event) {
			isTableToDestroy = true;
			int selectedIndex=sessionsDropDown.getSelectedIndex();
			String classpageId=AppClientFactory.getPlaceManager().getRequestParameter("cid", null);
			if(classpageId==null){
				classpageId="";
			}
			setSessionStartTime(selectedIndex);
			getUiHandlers().getCollectionScoreForSession(collectionDo.getGooruOid(), classpageId, AppClientFactory.getLoggedInUser().getGooruUId(), sessionsDropDown.getValue(selectedIndex), null);
			getUiHandlers().setCollectionSummaryData(collectionDo.getGooruOid(), classpageId,AppClientFactory.getLoggedInUser().getGooruUId(),sessionsDropDown.getValue(selectedIndex),printData);
		}
	}


	@Override
	public void setCollectionMetadata(CollectionDo collectionDo) {
		this.collectionDo = collectionDo;
	}



	public List<Map<String,String>> getStandardsMap(List<StandardFo> standareds){
		List<Map<String,String>> standardsList=new ArrayList<Map<String,String>>();
		for(int i=0;i<standareds.size();i++){
			Map<String, String> standardMap=new HashMap<String, String>();
			standardMap.put(STANDARD_CODE, standareds.get(i).getCode());
			standardMap.put(STANDARD_DESCRIPTION, standareds.get(i).getDescription());
			standardsList.add(standardMap);
		}
		return standardsList;
	}


	public void setLabelAndIds() {
		studentAssessmentReportViewContainer.setVisible(false);
		printWidget.setVisible(false);
		downloadFile.setVisible(false);

		//PrintPnl.getElement().getStyle().setHeight(Window.getClientHeight()-106, Unit.PX);

		progressRadial.getElement().setId("fpnlprogressRadial");

		collectionSummaryText.getElement().setInnerText(i18n.GL4006());
		StringUtil.setAttributes(collectionSummaryText.getElement(), "pnlCollectionSummaryText", i18n.GL4006(), i18n.GL4006());

		printButton.getElement().setId("printButton");
		downloadButton.getElement().setId("downloadButton");
		String html = "<span class='printIcon'></span>";
		printButton.setHTML(html);
		printButton.setText(i18n.GL4007());
		downloadButton.setText(i18n.GL4008());

		loadingImageLabel.setVisible(false);
	}

	public void resetMetadataFields(){
		this.collectionDo=null;
	}

	@Override
	public void setRelatedConceptsContent(ArrayList<ConceptDo> conceptDoList,
			String coursePage, String subject, String lessonId,
			String libraryName) {

	}



	public void setDataInsightsUrl(){

			getUiHandlers().setCollectionSummaryBasedOnClasspageIdSessionId();
	}

	public void setClasspageInsightsUrl(String classpageId, String sessionId){
		if(sessionId==null) {
			sessionId = "";
		}
			getUiHandlers().setCollectionSummaryBasedOnClasspageIdSessionId();
	}

	public void setDataInsightsSummaryUrl(String sessionId){
			sessionId=sessionId!=null?sessionId:"";
			getUiHandlers().setCollectionSummaryBasedOnClasspageIdSessionId();
	}



	public void displayScore(Integer collectionScore, Integer noOfQuestions){

		score.setText(collectionScore+" %");
//		goal.setText("Goal : 90%");
		correctStatus.setText(collectionScore+"/"+noOfQuestions+" "+i18n.GL2278());
		int scorePercentage=0;
		if(collectionScore!=0){
			 scorePercentage=(collectionScore/noOfQuestions)*100;
		}
		String progressRedialStyle="blue-progress-"+scorePercentage;
		progressRadial.setStyleName("progress-radial");
		progressRadial.addStyleName(progressRedialStyle);
	}


	@Override
	public void displayScoreCount(CollectionSummaryMetaDataDo result) {
		score.setText(result.getScoreInPercentage()+" %");
		goal.setText(i18n.GL3460_5() + result.getGoal()+" %");
		correctStatus.setText(result.getScore()+"/"+result.getSelectedSessionScorableQuestionCount()+" "+i18n.GL2278());
		int scorePercentage=result.getScoreInPercentage();
		String progressRedialStyle="blue-progress-"+scorePercentage;
		progressRadial.setStyleName("progress-radial");
		progressRadial.addStyleName(progressRedialStyle);
	}


	@Override
	public void setSessionsData(ArrayList<session> result) {
		sessionsDropDown.clear();
		sessionData.clear();
		for (session session : result) {
			sessionData.put(session.getSessionId(), session.getEventTime());
			int day=session.getSequence();
			sessionsDropDown.addItem(day+AnalyticsUtil.getOrdinalSuffix(day)+" Attempt",session.getSessionId());
		}
		setSessionStartTime(result.size()-1);
	}
	public void setSessionStartTime(int selectedIndex) {
		if(sessionData.size()!=0){
			lastModifiedTime.setText(i18n.GL4005()+AnalyticsUtil.getSessionsCreatedTime(Long.toString(sessionData.get(sessionsDropDown.getValue(selectedIndex)))));
			sessionsDropDown.setSelectedIndex(selectedIndex);
			printData.setUserName(null);
			printData.setSession(sessionsDropDown.getItemText(selectedIndex));
			printData.setSessionStartTime(AnalyticsUtil.getSessionsCreatedTime(Long.toString(sessionData.get(sessionsDropDown.getValue(selectedIndex)))));
		}
	}

	@Override
	public void setCollectionMetaDataByUserAndSession(ArrayList<CollectionSummaryMetaDataDo> result) {
		if(result.size()!=0){
			collectionTitle.setText(result.get(0).getTitle());
			if(result.get(0).getThumbnail()!=null && !result.get(0).getThumbnail().trim().equalsIgnoreCase("")){
				collectionImage.setUrl(result.get(0).getThumbnail());
			}else{
				collectionImage.setUrl("images/default-assessment-image -160x120.png");
			}
			collectionImage.addErrorHandler(new ErrorHandler() {
				@Override
				public void onError(ErrorEvent event) {
					collectionImage.setUrl("images/default-assessment-image -160x120.png");
				}
			});
			collectionResourcesCount.setText(result.get(0).getScorableQuestionCount()+" Questions");
		}
	}

	@Override
	public void resetCollectionMetaData(){
		collectionTitle.setText("");
		collectionImage.setUrl("");
		collectionResourcesCount.setText("");
	}


	/**
	 * @function removeHtmlTags
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description this method is used to remove the html tags in comment input box
	 *
	 * @parm(s) : @param String
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private String removeHtmlTags(String html){
		html = html.replaceAll("(<\\w+)[^>]*(>)", "$1$2");
		html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "").replaceAll("</a>", "").replaceAll("<a>", "");
		return html;
	}


	@Override
	public void setQuestionsData(final ArrayList<UserDataDo> result) {

		loadingImageLabel.setVisible(false);
		questionsTable.setVisible(true);
		questionsTable.clear();
		if (isTableToDestroy){
			isTableToDestroy = false;
			destoryTables();
		}

		if(result.size() > 0){
			final AdvancedFlexTable adTable=new AdvancedFlexTable();
			adTable.removeAllRows();
			adTable.getElement().setId("report-student-assessment-report");
			adTable.addStyleName("table table-bordered reportTableStyle");
			questionsTable.add(adTable);

			Label heading1 = new Label(i18n.GL3259());
			Label heading2 = new Label(i18n.GL0308());
			Label heading3 = new Label(i18n.GL0315());
			Label heading4 = new Label(i18n.GL2288());
			Label heading5 = new Label(i18n.GL2084());
			Label heading6 = new Label(i18n.GL3271());

			heading1.addStyleName("headingLabel");
			heading2.addStyleName("headingLabel");
			heading3.addStyleName("headingLabel");
			heading4.addStyleName("headingLabel");
			heading5.addStyleName("headingLabel");
			heading6.addStyleName("headingLabel");

			adTable.setHeaderWidget(0, heading1);
			adTable.setHeaderWidget(1, heading2);
			adTable.setHeaderWidget(2, heading3);
			adTable.setHeaderWidget(3, heading4);
			adTable.setHeaderWidget(4, heading5);
			adTable.setHeaderWidget(5, heading6);
			for(int i=0;i<result.size();i++) {
				Label questionTitle=new Label(StringUtil.removeAllHtmlCss(result.get(i).getTitle() != null ? result.get(i).getTitle() : "" ));
				questionTitle.setStyleName(STYLE_TABLE_CENTER);
				questionTitle.setStyleName(STYLE_TXTLEFT);
				adTable.setWidget(i, 0,new Label(String.valueOf(i+1)));
				adTable.setWidget(i, 1,questionTitle);

				int noOfAttempts=result.get(i).getAttempts();
				String scoreStatus= result.get(i).getStatus();

				//Set Answer choices
				String questionType= result.get(i).getType();
				if(questionType.equalsIgnoreCase("HS")){
					questionType= result.get(i).getQuestionType();
				}
				if(MC.equalsIgnoreCase(questionType) ||TF.equalsIgnoreCase(questionType) || TSLASHF.equalsIgnoreCase(questionType)){
					Label anserlbl=new Label();
					if(result.get(i).getMetaData()!=null && result.get(i).getOptions()!=null){
						 Map<String, Integer> authorObject = result.get(i).getOptions();

						 for (Map.Entry<String, Integer> entry : authorObject.entrySet())
						 {
							 String userSelectedOption=entry.getKey();
							// int ansStatus=entry.getValue();
							 if(userSelectedOption!=null){
									anserlbl.setText(userSelectedOption);
									if(STATUS_CORRECT.equalsIgnoreCase(scoreStatus) && noOfAttempts==1){
										anserlbl.getElement().getStyle().setColor(CORRECT);
									}else if(STATUS_CORRECT.equalsIgnoreCase(scoreStatus) && noOfAttempts>1){
										anserlbl.getElement().getStyle().setColor(ONMULTIPULEATTEMPTS);
									}else{
										anserlbl.getElement().getStyle().setColor(INCORRECT);
									}
								}
						 }
					}
					anserlbl.setStyleName(STYLE_TABLE_CENTER);
					adTable.setWidget(i, 2,anserlbl);
				}else if (FIB.equalsIgnoreCase(questionType)){
					VerticalPanel answerspnl=new VerticalPanel();
					if(result.get(i).getMetaData()!=null && result.get(i).getOptions()!=null){
						String answerTextFormat = "";
						String[] answersArry = null;
						ArrayList<MetaDataDo> questionList=result.get(i).getMetaData();

						for (MetaDataDo metaDataDo : questionList) {

							String answerText = "";
							if((metaDataDo.getAnswerText() != null)) {
								String text=StringUtil.removeAllHtmlCss(removeHtmlTags(InfoUtil.removeQuestionTagsOnBoldClick(metaDataDo.getAnswerText())));
								answerText = text;
							}
							answerTextFormat += '[' + answerText +']';
							if(questionList.size()  != metaDataDo.getSequence()){
								answerTextFormat += ",";
							}
						}
						String[] userFibOption = null;
						if(result.get(i).getText() != null) {
							answersArry=answerTextFormat.split(",");
							userFibOption =result.get(i).getText().split(",");
						}
						if(answersArry!=null && userFibOption!=null){
							for (int k = 0; k < answersArry.length; k++) {
								Label answerChoice=new Label();
								if(answersArry[k]!=null && k<userFibOption.length){
									if((answersArry[k].toLowerCase().trim().equalsIgnoreCase(userFibOption[k].toLowerCase().trim())) && (noOfAttempts == 1)){
										answerChoice.setText(userFibOption[k]);
										answerChoice.getElement().getStyle().setColor(CORRECT);
									}else if((answersArry[k].toLowerCase().trim().equalsIgnoreCase(userFibOption[k].toLowerCase().trim())) && (noOfAttempts > 1)) {
										answerChoice.setText(userFibOption[k]);
										answerChoice.getElement().getStyle().setColor(ONMULTIPULEATTEMPTS);
									}else{
										answerChoice.setText(userFibOption[k]);
										answerChoice.getElement().getStyle().setColor(INCORRECT);
									}
									answerChoice.setStyleName(STYLE_TABLE_CENTER);
									answerspnl.add(answerChoice);
								}
							}
						}
					}
					answerspnl.setStyleName(STYLE_MARGIN_AUTO);
					adTable.setWidget(i, 2,answerspnl);
					//data.setValue(i, 2, answerspnl.toString());
				}else  if(MA.equalsIgnoreCase(questionType)){
					VerticalPanel answerspnl=new VerticalPanel();
					if(result.get(i).getAnswerObject()!=null) {
						JSONValue value = JSONParser.parseStrict(result.get(i).getAnswerObject());
						JSONObject answerObject = value.isObject();
						Set<String> keys=answerObject.keySet();
						Iterator<String> itr = keys.iterator();
						while(itr.hasNext()) {
							answerspnl.clear();
							JSONArray attemptsObj=(JSONArray) answerObject.get(itr.next().toString());
							for(int j=0;j<attemptsObj.size();j++){
								Label answerChoice=new Label();
								String showMessage = null;
								boolean skip = attemptsObj.get(j).isObject().get("skip").isBoolean().booleanValue();
								String status =attemptsObj.get(j).isObject().get("status").isString().stringValue();
								String matext =attemptsObj.get(j).isObject().get("text").isString().stringValue();
								if(skip == false)
								{
									if(ZERO_NUMERIC.equalsIgnoreCase(matext)) {
										showMessage = i18n.GL_GRR_NO();
									} else if(ONE.equalsIgnoreCase(matext)) {
										showMessage = i18n.GL_GRR_YES();
									}
									answerChoice.setText(showMessage);
									if(ZERO_NUMERIC.equalsIgnoreCase(status)) {
										answerChoice.getElement().getStyle().setColor(INCORRECT);
									} else if(ONE.equalsIgnoreCase(status) && (noOfAttempts == 1)) {
										answerChoice.getElement().getStyle().setColor(CORRECT);
									} else if(ONE.equalsIgnoreCase(status) && (noOfAttempts > 1)) {
										answerChoice.getElement().getStyle().setColor(ONMULTIPULEATTEMPTS);
									}
								}
								answerChoice.setStyleName(STYLE_TABLE_CENTER);
								answerspnl.add(answerChoice);
							}
						}
					}
					answerspnl.setStyleName(STYLE_MARGIN_AUTO);
					adTable.setWidget(i, 2,answerspnl);
				}else if(HT_RO.equalsIgnoreCase(questionType) || HT_HL.equalsIgnoreCase(questionType) || HS_TXT.equalsIgnoreCase(questionType) || HS_IMG.equalsIgnoreCase(questionType)){
					if(result.get(i).getAnswerObject()!=null && !result.get(i).getStatus().equalsIgnoreCase("skipped")) {
						Label viewResponselbl=new Label(VIEWRESPONSE);
						viewResponselbl.setStyleName("summaryViewResponse");
						viewResponselbl.getElement().setAttribute("resourceGooruId", result.get(i).getResourceGooruOId());
						viewResponselbl.getElement().setAttribute("questionType", result.get(i).getType());
						viewResponselbl.getElement().setAttribute("answerObj", result.get(i).getAnswerObject());
						viewResponselbl.getElement().setAttribute("attempts",String.valueOf(noOfAttempts));
						viewResponselbl.addClickHandler(new SummaryPopupClick(result.get(i)));
						adTable.setWidget(i, 2,viewResponselbl);
					}
				}

				//Set attempts
				Label attempts=new Label(i18n.GL2269());
				attempts.setStyleName(STYLE_TABLE_CENTER);
				attempts.getElement().getStyle().setColor(ONMULTIPULEATTEMPTS);

				if(STATUS_INCORRECT.equalsIgnoreCase(scoreStatus)) {
					adTable.setWidget(i, 3,attempts);
					adTable.getRowFormatter().addStyleName(i, STYLE_WHITE);
				} else if(STATUS_CORRECT.equalsIgnoreCase(scoreStatus)) {
					Image correctImg=new Image();
					correctImg.setUrl(urlDomain+"/images/analytics/tick.png");
					adTable.setWidget(i, 3,correctImg);
					adTable.getRowFormatter().addStyleName(i, STYLE_GREEN);
				}

				//Set time spent
				adTable.setWidget(i, 4, AnalyticsUtil.getTimeStampLabel(result.get(i).getTimeSpent()));

				//Set reactions
				int reaction=result.get(i).getReaction();
				adTable.setWidget(i, 5, new AnalyticsReactionWidget(reaction));
			}
//			sortAndFixed();

		}else {
			Label erroeMsg=new Label();
			erroeMsg.setStyleName(STYLE_ERROR_MSG);
			erroeMsg.setText(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.ASSESSMENT_PLAY)?i18n.GL3507():i18n.GL3265());
			questionsTable.add(erroeMsg);
		}


	}

	/**
	 * This will return the correct answers
	 * @param metaDataObj
	 * @return
	 */
	String getCorrectAnswer(ArrayList<MetaDataDo> metaDataObj){
		for (MetaDataDo metaDataDo : metaDataObj) {
			if(metaDataDo.getIsCorrect()==1){
				return AnalyticsUtil.getCharForNumber(metaDataDo.getSequence()-1);
			}
		}
		return null;
	}


	public class SummaryPopupClick implements ClickHandler{

		String answerObj;
		String questionType;
		String attempts;

		public SummaryPopupClick(UserDataDo userDataDo) {
			answerObj=userDataDo.getAnswerObject();
			questionType=userDataDo.getType();
			attempts=String.valueOf(userDataDo.getAttempts());
		}

		@Override
		public void onClick(ClickEvent event) {
				JSONValue value = JSONParser.parseStrict(answerObj);
				JSONObject answerObject = value.isObject();
				Set<String> keys=answerObject.keySet();
				Iterator<String> itr = keys.iterator();
				JSONArray attemptsObj=null;
				while(itr.hasNext()) {
					attemptsObj=(JSONArray) answerObject.get(itr.next().toString());
				}
				if(attemptsObj!=null){
					SummaryAnswerStatusPopup summaryPopup=new SummaryAnswerStatusPopup(attemptsObj, questionType,attempts);
				}
		}
	};

	@Override
	public void setQuestionsPrintData(final ArrayList<UserDataDo> result) {
	}

	public void setPrintIndividualSummayData(final boolean isClickedOnSave, final boolean isClickedOnEmail){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				printWidget.clear();
				/*Label collectionSummaryText=new Label();
				collectionSummaryText.setText(i18n.GL4006());
				collectionSummaryText.getElement().getStyle().setPaddingBottom(15, Unit.PX);
				collectionSummaryText.addStyleName("collectionSummaryText");
				printWidget.add(collectionSummaryText);
				//printWidget.add(collectionSummaryWidget);

				//To add scored questions
				Label scoredQuestionHeading=new Label();
				scoredQuestionHeading.setText(i18n.GL2282());
				scoredQuestionHeading.getElement().getStyle().setClear(Clear.BOTH);
				scoredQuestionHeading.getElement().getStyle().setPaddingTop(15, Unit.PX);
				scoredQuestionHeading.getElement().getStyle().setPaddingBottom(20, Unit.PX);
				printWidget.add(scoredQuestionHeading);
				printWidget.add(printScoredData);
				printScoredData.getElement().getStyle().setPaddingBottom(20, Unit.PX);*/

				printButton.setVisible(false);
				downloadButton.setVisible(false);
				timer1.schedule(100);

				//To add resource breakdown
				//printWidget.add(collectionOverViewWidget);
				//printWidget.add(printResourceData);
				if(isClickedOnSave){
					getUiHandlers().setHtmltopdf(style.toString().replaceAll("'", "\\\\\"")+PrintPnl.getElement().getInnerHTML().toString().replaceAll("\"", "\\\\\""),collectionTitle.getText(),isClickedOnEmail);
					printWidget.clear();
				}else{
					downloadFile.setUrl("");
					Print.it(style,PrintPnl);
					printWidget.clear();
				}

			}
		});
	}


	Timer timer1=new Timer() {
		@Override
		public void run() {
			printButton.setVisible(true);
			downloadButton.setVisible(true);
		}
	};


	@UiHandler("printButton")
	public void printButtonClick(ClickEvent event){
		setPrintIndividualSummayData(false,false);
	}

	@UiHandler("downloadButton")
	public void downlaodButtonClick(ClickEvent event){
		setPrintIndividualSummayData(true,false);
	}



	@Override
	public void setPdfForEmail(String path) {
		if(emailPopup!=null){
			emailPopup.setEmailData(collectionTitle.getText(),path);
		}
	}



	@Override
	public Frame getFrame() {
		return downloadFile;
	}

	public static native void sortAndFixed() /*-{
		 var table =$wnd.$('#report-student-assessment-report').DataTable({
	       scrollY:        "300px",
	       scrollX:        true,
	       scrollCollapse: true,
	       paging:         false,
	       bFilter:false,
	       bInfo: false
	   });
	}-*/;
	public static native void destoryTables() /*-{
		var table = $wnd.$('#report-student-assessment-report').DataTable();
	  	table.destroy();
	}-*/;



	@Override
	public void displaySummaryMetadata(AssessmentSummaryStatusDo assessmentSummaryStatusDo) {
		throw new RuntimeException("Not implemented");
	}



	@Override
	public void loadingIcon() {
		loadingImageLabel.setVisible(true);
		questionsTable.setVisible(false);
	}



	@Override
	public void errorMsg() {
		Label erroeMsg=new Label();
		erroeMsg.setStyleName(STYLE_ERROR_MSG);
		erroeMsg.setText(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.ASSESSMENT_PLAY)?i18n.GL3507():i18n.GL3265());
		questionsTable.clear();
		questionsTable.add(erroeMsg);
	}


	@Override
	public HTMLPanel getQuestionsTable() {
		return questionsTable;
	}



	public void setQuestionsTable(HTMLPanel questionsTable) {
		this.questionsTable = questionsTable;
	}



	@Override
	public void setReportContainer(String sessionId) {
		assessmentReportContainer.clear();
		String id = AppClientFactory.getPlaceManager().getRequestParameter("id","");
		String classId = AppClientFactory.getPlaceManager().getRequestParameter("cid","");
		String courseId = AppClientFactory.getPlaceManager().getRequestParameter("courseId","");
		String unitId = AppClientFactory.getPlaceManager().getRequestParameter("unitId","");
		String lessonId = AppClientFactory.getPlaceManager().getRequestParameter("lessonId","");
		assessmentReportContainer.add(new AssessmentProgressReportChildView(id, classId, AppClientFactory.getGooruUid(), courseId, unitId, lessonId, UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT, sessionId));
	}

}
