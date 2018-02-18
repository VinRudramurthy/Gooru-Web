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
package org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.assessmentreport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.analytics.AssessmentSummaryStatusDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.MetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.PrintUserDataDO;
import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;
import org.ednovo.gooru.application.shared.model.analytics.session;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.content.UserPlayedSessionDo;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;
import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.EmailPopup;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.SummaryAnswerStatusPopup;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsReactionWidget;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.client.mvp.analytics.util.Print;
import org.ednovo.gooru.client.mvp.search.SearchResultWrapperCBundle;
import org.ednovo.gooru.client.uc.H2Panel;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.H4Panel;
import org.ednovo.gooru.client.uc.LoadingUc;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.InfoUtil;
import org.ednovo.gooru.shared.util.StringUtil;
import org.gwt.advanced.client.ui.widget.AdvancedFlexTable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru Team
 *
 */
public class AssessmentProgressReportChildView extends ChildView<AssessmentProgressReportChildPresenter> implements IsAssessmentProgressReportView,ClientConstants {

	@UiField FlowPanel PrintPnl, printOptions, reportViewContainer, scoreObject,scorePrintObject;
	@UiField FlowPanel progressRadial,scoreRoundPanel, thumbnailImage, timeSpentPanel, headerLinksContainer, attemptPanel, selfReportPanel;
	@UiField HTMLPanel  collectionSummaryText, questionsTable, printDataTable, collectionOverviewPanel;
	@UiField ListBox sessionsDropDown;
	@UiField Image collectionImage;
	@UiField InlineLabel collectionResourcesCount,correctStatus;
	@UiField H3Panel collectionTitle;
	@UiField H4Panel scoreTitle;
	@UiField H2Panel score;
	@UiField PPanel lastModifiedTime,goal;
	@UiField Button printButton,downloadButton;
	@UiField Frame downloadFile;
	@UiField Label collectionOverviewBtn, questionsBtn, oeQuestionsBtn,errorPanel;
	@UiField LoadingUc loadingImageLabel;
	@UiField Anchor externalAssessmentUrl;
	@UiField LoadingUc cropImageLoading;

	HTMLPanel printScoredData=new HTMLPanel("");
	EmailPopup emailPopup=null;

	Map<String, Long> sessionData=new HashMap<String, Long>();
	PrintUserDataDO printData=new PrintUserDataDO();
	String urlDomain = "";
	String style="";

	private String userId = null, contentType = null;

	String classId = "", lessonId = "", unitId = "", courseId = "", assessmentId = "";

	private CollectionDo collectionDo=null;
	private boolean isCollection = false, isExternalAssessment = false;
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	private static AssessmentProgressReportChildViewUiBinder uiBinder = GWT.create(AssessmentProgressReportChildViewUiBinder.class);

	interface AssessmentProgressReportChildViewUiBinder extends UiBinder<Widget, AssessmentProgressReportChildView> {
	}

	public boolean isTableToDestroy=false;

	public AssessmentProgressReportChildView(String assessmentId, String classId, String userId, String courseId, String unitId, String lessonId, String contentType, String sessionId) {
		initWidget(uiBinder.createAndBindUi(this));
		setPresenter(new AssessmentProgressReportChildPresenter(this));
		selfReportPanel.setVisible(false);
		this.contentType = contentType;
		if(contentType.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASSPAGE_COLLECTION)) {
			isCollection = true;
		} else if(contentType.equalsIgnoreCase(UrlNavigationTokens.EXTERNAL_ASSESSMENT)) {
			isExternalAssessment = true;
			setExternalAssessment();
		}
		setLabelAndIds();
		urlDomain=Window.Location.getProtocol()+"//"+Window.Location.getHost();
		style="<link rel='styleSheet' type='text/css' href='"+urlDomain+"/css/main-styles.min.css'>";
		downloadFile.setVisible(false);
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		SearchResultWrapperCBundle.INSTANCE.css().ensureInjected();
		sessionsDropDown.addChangeHandler(new StudentsSessionsChangeHandler());
		collectionOverviewBtn.addClickHandler(new ResourceDataCall(collectionOverviewBtn));
		questionsBtn.addClickHandler(new ResourceDataCall(questionsBtn));
		oeQuestionsBtn.addClickHandler(new ResourceDataCall(oeQuestionsBtn));
		this.userId = userId;
		this.classId = classId;
		this.lessonId = lessonId;
		this.unitId = unitId;
		this.courseId = courseId;
		this.assessmentId = assessmentId;
		if(AppClientFactory.isAnonymous()) {
			setAnonymousData();
		} else {
			if(sessionId==null) {
				getPresenter().getContentPlayAllSessions(userId, classId, lessonId, unitId, courseId, assessmentId, sessionId);
			} else {
				getPresenter().setSessionId(sessionId);
				getPresenter().setSession(false);
				getPresenter().getSessionsDataByUser(assessmentId,classId,courseId, unitId, lessonId, userId);
			}
		}
	}

	private void setExternalAssessment() {
		headerLinksContainer.setVisible(false);
		collectionResourcesCount.setVisible(false);
		timeSpentPanel.addStyleName("col-md-20p");
		scoreTitle.setText(i18n.GL3465_1());
		scoreRoundPanel.setVisible(false);
		attemptPanel.setVisible(false);
		selfReportPanel.setVisible(true);
	}

	public class StudentsSessionsChangeHandler implements ChangeHandler{
		@Override
		public void onChange(ChangeEvent event) {
			int selectedIndex=sessionsDropDown.getSelectedIndex();
			setSessionStartTime(selectedIndex);
			getContentData(null,false,null);
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
		loaderVisibility(true);
		errorPanelData(false, false);

		PrintPnl.getElement().setAttribute("style", "min-height:"+(Window.getClientHeight()-106)+"px");

		if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY)) {
			PrintPnl.removeStyleName("LearningMapContainer");
			PrintPnl.getElement().getStyle().setBackgroundColor("white");
		}

		progressRadial.getElement().setId("fpnlprogressRadial");
		cropImageLoading.setLoadingText(i18n.GL1234());
		cropImageLoading.getElement().setId("loadingUcCropImageLoadingInStudentSummaryView");
		score.setText("--");
		String collectionType = i18n.GL4006();

		if(isCollection) {
			collectionType = i18n.GL1587();
			scoreRoundPanel.setVisible(false);
			goal.setVisible(false);
			scoreTitle.setText(i18n.GL2084());
			thumbnailImage.addStyleName("blueBorder");
			timeSpentPanel.setStyleName("col-md-4 col-sm-4");
			collectionOverviewBtn.setText(i18n.GL2274());
			questionsBtn.setText(i18n.GL2290());
			oeQuestionsBtn.setText(i18n.GL0654());
			collectionOverviewBtn.getElement().setAttribute("type", RESOURCE);
			questionsBtn.getElement().setAttribute("type", QUESTION);
			oeQuestionsBtn.getElement().setAttribute("type", OE);
			collectionOverviewPanel.setVisible(true);
		} else {
			thumbnailImage.addStyleName("orgBorder");
			collectionOverviewPanel.setVisible(false);
		}

		collectionSummaryText.getElement().setInnerText(collectionType);

		StringUtil.setAttributes(collectionSummaryText.getElement(), "pnlCollectionSummaryText", collectionType, collectionType);

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

	public void displayScore(Integer collectionScore, Integer noOfQuestions){
		score.setText(collectionScore+" %");
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
		if(!isCollection) {
			score.setText(result.getScoreInPercentage()+" %");
			goal.setText(i18n.GL3460_5() + result.getGoal()+" %");
			correctStatus.setText(result.getScore()+"/"+result.getScorableQuestionCount()+" "+i18n.GL2278());
			int scorePercentage=result.getScoreInPercentage();
			String progressRedialStyle="blue-progress-"+scorePercentage;
			progressRadial.setStyleName("progress-radial");
			progressRadial.addStyleName(progressRedialStyle);
			if(isExternalAssessment) {
				String url = result.getEvidence();
				if(!(url!=null&&(url.contains("http://")||url.contains("https://")))) {
					url = "http://"+url;
				}
				externalAssessmentUrl.setText(url);
				externalAssessmentUrl.setHref(url);
				externalAssessmentUrl.setTarget("_blank");
			}
		}
	}

	@Override
	public void setAttemptsData(ArrayList<session> result) {
		sessionsDropDown.clear();
		sessionData.clear();
		for (session session : result) {
			sessionData.put(session.getSessionId(), session.getEventTime());
			int day=session.getSequence();

			String attemptType = " Attempt";
			if(isCollection) {
				attemptType = " Session";
			}
			sessionsDropDown.addItem(day+AnalyticsUtil.getOrdinalSuffix(day)+attemptType,session.getSessionId());
		}
		setSessionStartTime(result.size()-1);
	}

	@Override
	public void setSessionsData(List<UserPlayedSessionDo> result) {
		sessionsDropDown.clear();
		sessionData.clear();
		for (UserPlayedSessionDo session : result) {
			sessionData.put(session.getSessionId(), session.getEventTime());
			int day=session.getSequence();

			String attemptType = " Attempt";
			if(isCollection) {
				attemptType = " Session";
			}
			sessionsDropDown.addItem(day+AnalyticsUtil.getOrdinalSuffix(day)+attemptType,session.getSessionId());
		}
		setSessionStartTime(0);
	}

	public void setSessionStartTime(int selectedIndex) {
		if(sessionData.size()!=0){
			if(isCollection) {
				lastModifiedTime.setText("You studied this collection on: "+AnalyticsUtil.getSessionsCreatedTime(Long.toString(sessionData.get(sessionsDropDown.getValue(selectedIndex)))));
			} else {
				lastModifiedTime.setText(i18n.GL4005()+" "+AnalyticsUtil.getSessionsCreatedTime(Long.toString(sessionData.get(sessionsDropDown.getValue(selectedIndex)))));
			}
			sessionsDropDown.setSelectedIndex(selectedIndex);
		}
	}

	@Override
	public void setCollectionMetaDataByUserAndSession(ArrayList<CollectionSummaryMetaDataDo> result) {
		if(result.size()!=0){
			collectionTitle.setText(result.get(0).getTitle());
			if(result.get(0).getThumbnail()!=null && !result.get(0).getThumbnail().trim().equalsIgnoreCase("")){
				collectionImage.setUrl(result.get(0).getThumbnail());
			}else{
				setDefaultImg();
			}
			collectionImage.addErrorHandler(new ErrorHandler() {
				@Override
				public void onError(ErrorEvent event) {
					setDefaultImg();
				}
			});
			if(isCollection) {
				collectionResourcesCount.setText(result.get(0).getResourceCount()+" "+i18n.GL2291()+" " + result.get(0).getQuestionCount()+" "+i18n.GL2290());
			} else {
				collectionResourcesCount.setText(result.get(0).getScorableQuestionCount()+" "+i18n.GL2290());
			}
		}
	}

	private void setDefaultImg() {
		if(isCollection) {
			collectionImage.setUrl(urlDomain+"/images/default-collection-image-160x120.png");
		} else {
			collectionImage.setUrl(urlDomain+"/images/default-assessment-image -160x120.png");
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
	public void setResourcesData(final ArrayList<UserDataDo> result, boolean isPrint) {
		loadingImageLabel.setVisible(false);
		questionsTable.setVisible(true);
		if(!isPrint) {
			questionsTable.clear();
		}
		long totalTimeSpent = 0L;
		if(result.size() > 0){
			final AdvancedFlexTable adTable=new AdvancedFlexTable();
			adTable.removeAllRows();
			adTable.getElement().setId("report-student-assessment-report");
			adTable.addStyleName("table table-bordered reportTableStyle");
			if(!isPrint) {
				questionsTable.add(adTable);
			} else {
				printDataTable.add(adTable);
			}

			Label heading1 = new Label(i18n.GL3259());
			Label heading2 = new Label(StringUtil.capitalizeFirstLetter(i18n.GL1424()));
			Label heading3 = new Label(i18n.GL3182());
			Label heading4 = new Label(i18n.GL2275());
			Label heading5 = new Label(i18n.GL3261());

			heading1.addStyleName("headingLabel");
			heading2.addStyleName("headingLabel");
			heading3.addStyleName("headingLabel");
			heading4.addStyleName("headingLabel");
			heading5.addStyleName("headingLabel");

			adTable.setHeaderWidget(0, heading1);
			adTable.setHeaderWidget(1, heading2);
			adTable.setHeaderWidget(2, heading3);
			adTable.setHeaderWidget(3, heading4);
			adTable.setHeaderWidget(4, heading5);

			UserDataDo maxAvgValue=Collections.max(result,new Comparator<UserDataDo>() {
	        	public int compare(UserDataDo o1, UserDataDo o2) {
	        		 Long obj1 = new Long(o1.getTimeSpent());
	        	     Long obj2 = new Long(o2.getTimeSpent());
	        	     return obj1.compareTo(obj2);
	        	}
	        });

			for(int i=0;i<result.size();i++) {
				String titlelbl1=InfoUtil.removeQuestionTagsOnBoldClick(result.get(i).getTitle()!=null? result.get(i).getTitle():"");
				HTML questionTitle=new HTML(StringUtil.removeAllHtmlCss(titlelbl1));
				questionTitle.setStyleName(STYLE_TABLE_CENTER);
				questionTitle.setStyleName(STYLE_TXTLEFT);
				adTable.setWidget(i, 0,new Label(String.valueOf(result.get(i).getSequence())));
	            Image categorylbl=new Image();
	            String  resourceCategory =result.get(i).getResourceFormat()!=null?result.get(i).getResourceFormat().trim():"";
	            categorylbl.setUrl(urlDomain+StringUtil.getResourceTypeImage(resourceCategory.toLowerCase()));
				adTable.setWidget(i, 1,categorylbl);
				adTable.setWidget(i, 2,questionTitle);

	            HorizontalPanel timeSpentpnl=new HorizontalPanel();
	            Label timeLabel = AnalyticsUtil.getTimeStampLabel(result.get(i).getTimeSpent());
	            timeLabel.setWidth("100px");
	            InlineLabel progressBar=new InlineLabel();
	            progressBar.setStyleName("setProgressBar");
	            timeSpentpnl.add(timeLabel);
	            timeSpentpnl.add(progressBar);
	            totalTimeSpent = totalTimeSpent + result.get(i).getTimeSpent();
	            double maxAvgVal = ((double) result.get(i).getTimeSpent())/((double) maxAvgValue.getTimeSpent());
	            progressBar.getElement().getStyle().setWidth(maxAvgVal*100, Unit.PX);
	            adTable.setWidget(i, 3,timeSpentpnl);

	            //Set reactions
	            int reaction=result.get(i).getReaction();
	            adTable.setWidget(i, 4,new AnalyticsReactionWidget(reaction));
			}
			score.setText(AnalyticsUtil.getTimeSpent(totalTimeSpent));
		}else {
			if(!isPrint) {
				setErrorData(questionsTable);
			} else {
				setPrintErrorData(printDataTable,RESOURCE);
			}
		}
	}


	@Override
	public void setQuestionsData(final ArrayList<UserDataDo> result, String contentType, boolean isPrint) {



		loadingImageLabel.setVisible(false);
		questionsTable.setVisible(true);
		if(!isPrint) {
			questionsTable.clear();
		}
		if (isTableToDestroy){
			isTableToDestroy = false;
			destoryTables();
		}

		if(result.size() > 0){
			final AdvancedFlexTable adTable=new AdvancedFlexTable();
			adTable.removeAllRows();
			adTable.getElement().setId("report-student-assessment-report");
			adTable.addStyleName("table table-bordered reportTableStyle");
			if(!isPrint) {
				questionsTable.add(adTable);
			} else {
				printDataTable.add(adTable);
			}

			Label heading1 = new Label(i18n.GL3259());
			Label heading2 = new Label(i18n.GL0308());

			String heading3Str = i18n.GL0315();
			if(contentType.equalsIgnoreCase(OE)) {
				heading3Str = i18n.GL3262();
			}
			Label heading3 = new Label(heading3Str);

			String heading4Str = i18n.GL2288();
			if(contentType.equalsIgnoreCase(OE)) {
				heading4Str = i18n.GL3260();
			}
			Label heading4 = new Label(heading4Str);

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
				String titlelbl1=InfoUtil.removeQuestionTagsOnBoldClick(result.get(i).getTitle()!=null? result.get(i).getTitle():"");
				HTML questionTitle=new HTML(StringUtil.removeAllHtmlCss(titlelbl1));
				questionTitle.setStyleName(STYLE_TABLE_CENTER);
				questionTitle.setStyleName(STYLE_TXTLEFT);
				adTable.setWidget(i, 0,new Label(String.valueOf(result.get(i).getSequence())));
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
									anserlbl.setText(getTextFromHTML(userSelectedOption));
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
								boolean skip = attemptsObj.get(j).isObject().get("skip").isBoolean().booleanValue();
								String status =attemptsObj.get(j).isObject().get("status").isString().stringValue();
								String fibtext =getTextFromHTML(attemptsObj.get(j).isObject().get("text").isString().stringValue());
								if(skip == false)
								{
									AppClientFactory.printInfoLogger("fibtext: "+fibtext+";-- status: "+status);
									answerChoice.setText(fibtext);
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
								String matext =getTextFromHTML(attemptsObj.get(j).isObject().get("text").isString().stringValue());
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
				}else if(HT_RO.equalsIgnoreCase(questionType) || HT_HL.equalsIgnoreCase(questionType) || HS_TXT.equalsIgnoreCase(questionType) || HS_IMG.equalsIgnoreCase(questionType) || OE.equalsIgnoreCase(questionType)){
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

				if(OE.equalsIgnoreCase(questionType)) {
		            Label completion=new Label();
		            completion.setStyleName("alignCenterAndBackground");
		            if(noOfAttempts>0){
		            	completion.setText(i18n.GL_GRR_YES());
		            }else{
		            	completion.setText(i18n.GL_GRR_NO());
		            }
		            adTable.setWidget(i, 3,completion);
				} else {
					if(STATUS_INCORRECT.equalsIgnoreCase(scoreStatus)) {
						adTable.setWidget(i, 3,attempts);
						adTable.getRowFormatter().addStyleName(i, STYLE_WHITE);
					} else if(STATUS_CORRECT.equalsIgnoreCase(scoreStatus)) {
						Image correctImg=new Image();
						correctImg.setUrl(urlDomain+"/images/analytics/tick.png");
						adTable.setWidget(i, 3,correctImg);
						adTable.getRowFormatter().addStyleName(i, STYLE_GREEN);
					}
				}

				//Set time spent
				adTable.setWidget(i, 4, AnalyticsUtil.getTimeStampLabel(result.get(i).getTimeSpent()));

				//Set reactions
				int reaction=result.get(i).getReaction();
				adTable.setWidget(i, 5, new AnalyticsReactionWidget(reaction));
			}
		}else {
			if(!isPrint) {
				setErrorData(questionsTable);
			} else {
				setPrintErrorData(printDataTable,contentType);
			}
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

				if(isCollection) {
					setPrintData();
					setDataPanelVisibility(false,true);
				}

				scoreObject.setVisible(false);

				collectionSummaryText.setVisible(false);

				Map<String, String> printMap=new HashMap<String, String>();

				printMap.put("collectionSummaryText", collectionSummaryText.getElement().getInnerText());
				printMap.put("collectionImage", collectionImage.getUrl());
				printMap.put("collectionTitle", collectionTitle.getText());
				printMap.put("collectionResourcesCount", collectionResourcesCount.getText());
				printMap.put("score", score.getText());
				printMap.put("scoreTitle", scoreTitle.getText());
				printMap.put("goal", goal.getText());
				printMap.put("correctStatus", correctStatus.getText());
				printMap.put("lastModifiedTime", lastModifiedTime.getText());
				printMap.put("sessionsDropDown", sessionsDropDown.getItemText(sessionsDropDown.getSelectedIndex()));

				scorePrintObject.getElement().setAttribute("style", "padding: 0 15px;background: #fff;border-bottom: 1px solid #ddd;");

				scorePrintObject.add(new AssessmentProgressReportPrintView(printMap));


				if(isClickedOnSave){
					printOptions.setVisible(false);

					String outputData = PrintPnl.getElement().getInnerHTML().toString();

					setDataPanelVisibility(true,false);
					printOptions.setVisible(true);
					getPresenter().setHtmltopdf(style.toString().replaceAll("'", "\\\\\"")+outputData.replaceAll("\"", "\\\\\""),collectionTitle.getText(),isClickedOnEmail);
				}else{
					printOptions.setVisible(false);
					downloadFile.setUrl("");
					Print.it(style,PrintPnl);
					printOptions.setVisible(true);
					setDataPanelVisibility(true,false);
				}
			}
		});
	}

	private void setDataPanelVisibility(boolean isQuestionData, boolean isPrintData) {
		questionsTable.setVisible(isQuestionData);
		printDataTable.setVisible(isPrintData);
		scorePrintObject.clear();
		scoreObject.setVisible(true);
		collectionSummaryText.setVisible(true);
	}

	Timer timer1=new Timer() {
		@Override
		public void run() {
			printButton.setVisible(true);
			downloadButton.setVisible(true);

		}
	};

	private void setPrintData() {
		printDataTable.clear();
		if(isCollection) {
			setResourcesData(getPresenter().getPrintDataDo(),true);
			setQuestionsData(getQuestionData(getPresenter().getPrintDataDo(),QUESTION),QUESTION,true);
			setQuestionsData(getQuestionData(getPresenter().getPrintDataDo(),OE),OE,true);
		}
	}

	private ArrayList<UserDataDo> getQuestionData(ArrayList<UserDataDo> result, String type) {
		ArrayList<UserDataDo> questionsData=new ArrayList<UserDataDo>();
		for (UserDataDo userDataDo : result) {
			if(type!=null&&type.equalsIgnoreCase(QUESTION)) {
				if(QUESTION.equalsIgnoreCase(userDataDo.getResourceFormat())){
					if(!OE.equalsIgnoreCase(userDataDo.getType())){
						questionsData.add(userDataDo);
					}
				}
			} else if(type!=null&&type.equalsIgnoreCase(OE)) {
				if(QUESTION.equalsIgnoreCase(userDataDo.getResourceFormat())){
					if(OE.equalsIgnoreCase(userDataDo.getType())){
						questionsData.add(userDataDo);
					}
				}
			} else {
				questionsData.add(userDataDo);
			}
		}
		return questionsData;
	}

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
	 var table =$wnd.$('#student-myclasses-assessment-summary-report').DataTable({
       scrollY:        "300px",
       scrollX:        true,
       scrollCollapse: true,
       paging:         false,
       bFilter:false,
       bInfo: false
   });
	}-*/;
	public static native void destoryTables() /*-{
		var table = $wnd.$('#student-myclasses-assessment-summary-report').DataTable();
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
		setErrorData(questionsTable);
	}

	public class ResourceDataCall implements ClickHandler {

		Label selectedLbl;

		public ResourceDataCall(Label selectedLbl) {
			this.selectedLbl = selectedLbl;
		}

		@Override
		public void onClick(ClickEvent event) {
			getContentData(selectedLbl.getElement().getAttribute("type"), true, selectedLbl);
		}
	}

	private void setPanelStyling(Label selectedLbl) {
		collectionOverviewBtn.removeStyleName(CssTokens.ACTIVE);
		questionsBtn.removeStyleName(CssTokens.ACTIVE);
		oeQuestionsBtn.removeStyleName(CssTokens.ACTIVE);
		selectedLbl.addStyleName(CssTokens.ACTIVE);
	}

	private void getContentData(String type, boolean isRefresh, Label selectedLbl) {
		if(contentType.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT)) {
			getPresenter().getCollectionScoreForSession(assessmentId, classId, userId, sessionsDropDown.getValue(sessionsDropDown.getSelectedIndex()), null);
		} else {
			if(isRefresh) {
				setPanelStyling(selectedLbl);
			} else {
				setPanelStyling(collectionOverviewBtn);
			}
		}
		getPresenter().setCollectionSummaryData(assessmentId, classId,userId,sessionsDropDown.getValue(sessionsDropDown.getSelectedIndex()),printData,type);
	}

	private void setErrorData(HTMLPanel globalPanel) {
		if(!isExternalAssessment) {
			Label erroeMsg=new Label();
			erroeMsg.setStyleName(STYLE_ERROR_MSG);
			if(collectionOverviewPanel.isVisible()) {
				if(collectionOverviewBtn.getStyleName()!=null&&collectionOverviewBtn.getStyleName().contains(CssTokens.ACTIVE)) {
					erroeMsg.setText(i18n.GL3467());
				}
				if(questionsBtn.getStyleName()!=null&&questionsBtn.getStyleName().contains(CssTokens.ACTIVE)) {
					erroeMsg.setText(!isCollection?i18n.GL3507():i18n.GL3265());
				}
				if(oeQuestionsBtn.getStyleName()!=null&&oeQuestionsBtn.getStyleName().contains(CssTokens.ACTIVE)) {
					erroeMsg.setText(i18n.GL3264());
				}
			} else {
				erroeMsg.setText(!isCollection?i18n.GL3507():i18n.GL3265());
			}
			globalPanel.add(erroeMsg);
		}
	}

	private void setPrintErrorData(HTMLPanel globalPanel, String type) {
		if(!isExternalAssessment) {
			Label erroeMsg=new Label();
			erroeMsg.setStyleName(STYLE_ERROR_MSG);
			if(collectionOverviewPanel.isVisible()) {
				if(type.equalsIgnoreCase(RESOURCE)) {
					erroeMsg.setText(i18n.GL3467());
				}
				if(type.equalsIgnoreCase(QUESTION)) {
					erroeMsg.setText(!isCollection?i18n.GL3507():i18n.GL3265());
				}
				if(type.equalsIgnoreCase(OE)) {
					erroeMsg.setText(i18n.GL3264());
				}
			} else {
				erroeMsg.setText(!isCollection?i18n.GL3507():i18n.GL3265());
			}
			globalPanel.add(erroeMsg);
		}
	}

	@Override
	public void errorPanelData(boolean isErrorPanelVisible, boolean isReportContainerVisible) {
		errorPanel.setVisible(isErrorPanelVisible);
		reportViewContainer.setVisible(isReportContainerVisible);
		if(isErrorPanelVisible) {
			errorPanel.setStyleName(STYLE_ERROR_MSG);
			errorPanel.setText(i18n.GL3115());
		}
	}

	@Override
	public void loaderVisibility(boolean isVisible) {
		cropImageLoading.setVisible(isVisible);
	}

	@Override
	public void setAnonymousData() {
		loaderVisibility(false);
		errorPanelData(false, true);
		errorMsg();
		printOptions.setVisible(false);
		scoreObject.setVisible(false);
		collectionOverviewPanel.setVisible(false);
	}

	private String getTextFromHTML(String html){
		html = html.replaceAll("\\+", "%2B");
		html = URL.decodeQueryString(html);
		return html;
	}

}