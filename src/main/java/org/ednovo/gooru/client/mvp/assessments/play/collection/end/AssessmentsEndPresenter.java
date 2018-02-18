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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.AnalyticsServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.analytics.AssessmentSummaryStatusDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.PrintUserDataDO;
import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;
import org.ednovo.gooru.application.shared.model.classpages.ClassDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.client.mvp.assessments.play.collection.AssessmentsPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.end.study.AssessmentsHomeMetadataPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.end.AssessmentsPreviewEndPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.home.AssessmentsPreviewHomePresenter;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AssessmentsEndPresenter extends PresenterWidget<IsAssessmentsEndView> implements AssessmentsEndUiHandlers,ClientConstants{


	@Inject
	private AnalyticsServiceAsync analyticService;


	private CollectionDo collectionDo=null;

	private AssessmentsPlayerPresenter collectionPlayerPresenter=null;

	private AssessmentsPreviewHomePresenter previewHomePresenter;

	private AssessmentsPreviewEndPresenter previewEndPresenter;

	private AssessmentsHomeMetadataPresenter collectionHomeMetadataPresenter;

	public static final  Object METADATA_PRESENTER_SLOT = new Object();

	PrintUserDataDO printData=new PrintUserDataDO();

	String classpageId=null;

	ClasspageItemDo classpageItemDo=null;

	public String sessionId=null;



	private MessageProperties i18n = GWT.create(MessageProperties.class);

	int count=0;

	ArrayList<UserDataDo> questionsData=new ArrayList<UserDataDo>();
	final List<Integer> questionRowIndex=new ArrayList<Integer>();
	private int collectionProgressCount=1;

	@Inject
	public AssessmentsEndPresenter(EventBus eventBus, IsAssessmentsEndView view,AssessmentsPreviewHomePresenter previewHomePresenter,
			AssessmentsPreviewEndPresenter previewEndPresenter,AssessmentsHomeMetadataPresenter collectionHomeMetadataPresenter) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.previewHomePresenter=previewHomePresenter;
		this.previewEndPresenter=previewEndPresenter;
		this.collectionHomeMetadataPresenter=collectionHomeMetadataPresenter;
	}

	public void setCollectionMetadata(final CollectionDo collectionDo,String classpageId){
		this.collectionDo=collectionDo;
		this.classpageId=classpageId;
		getView().setCollectionMetadata(collectionDo);
	}

	public void setPreviewHomePresenter(){
		previewHomePresenter.setCollectionMetadata(collectionDo);
		previewHomePresenter.removeAssignmentButtons();
		setInSlot(METADATA_PRESENTER_SLOT, previewHomePresenter,false);
	}
	public void setPreviewEndPresenter(){
		previewEndPresenter.setCollectionMetadata(collectionDo);
		setInSlot(METADATA_PRESENTER_SLOT, previewEndPresenter,false);
	}

	public void setStudyEndPage(){
		previewHomePresenter.setCollectionMetadata(collectionDo);
		previewHomePresenter.removeAssignmentImagetButtons();
		setInSlot(METADATA_PRESENTER_SLOT, previewHomePresenter,false);
	}

	public void setCollectionHomeMetadata(){
		collectionHomeMetadataPresenter.setCollectionMetadata(collectionDo);
		setInSlot(METADATA_PRESENTER_SLOT, collectionHomeMetadataPresenter,false);
	}
	public void setCollectionSummaryBasedOnClasspageIdSessionId(){
		getView().setReportContainer(sessionId);
		//getSessionsDataByUser(collectionDo.getGooruOid(),classpageId!=null?classpageId:"",AppClientFactory.getLoggedInUser().getGooruUId());
	}

	public void setCollectionSummaryData(String collectionId,String classpageId,String userId,String sessionId,PrintUserDataDO printData){
		setIndividualData(collectionId, this.classpageId!=null?this.classpageId:"", userId, sessionId,"",false,printData);
	}

	public void clearslot(){
		getView().resetCollectionMetaData();
	}

	public void resetMetadataFields(){
		getView().resetMetadataFields();
	}

	public void setCollectionDoOnRefresh(CollectionDo collectionDo) {
		this.collectionDo = collectionDo;
		getView().setCollectionMetadata(collectionDo);
	}

	public AssessmentsPlayerPresenter getCollectionPlayerPresenter() {
		return collectionPlayerPresenter;
	}

	public void setCollectionPlayerPresenter(AssessmentsPlayerPresenter collectionPlayerPresenter) {
		this.collectionPlayerPresenter = collectionPlayerPresenter;
		previewHomePresenter.setCollectionPlayerPresenter(collectionPlayerPresenter);
	}

	public void setDataInsightsSummaryUrl(String sessionId){
		getView().setDataInsightsSummaryUrl(sessionId);
	}

	public void setDataInsightsUrl(){
		getView().setDataInsightsUrl();
	}
	public Button getBackToClassButton(){
		return previewHomePresenter.getBackToClassButton();
	}
	public void setClasspageInsightsUrl(String classpageId, String sessionId){
		getView().setClasspageInsightsUrl(classpageId, sessionId);
	}

	@Override
	public void triggerCollectionShareDataEvent(String collectionId,String itemType, String shareType, boolean confirmStatus) {
		if(collectionPlayerPresenter!=null){
			collectionPlayerPresenter.triggerCollectionShareDataEvent( collectionId, itemType,  shareType,  confirmStatus);
		}
	}


	public AnalyticsServiceAsync getAnalyticService() {
		return analyticService;
	}

	public void setAnalyticService(AnalyticsServiceAsync analyticService) {
		this.analyticService = analyticService;
	}

	@Override
	public void getSessionsDataByUser(final String collectionId,final String classId,final String userId) {

		ClassDo classObj=new ClassDo();
		classObj.setAssessmentId(collectionId);
		classObj.setClassId(classId);
		classObj.setSessionId(sessionId);

		getCollectionMetaDataByUserAndSession(collectionId, classId, userId,sessionId,printData);
	}

	public void setTeacherInfo(ClasspageItemDo classpageItemDo) {
		this.classpageItemDo=classpageItemDo;
	}


	public static native String roundToTwo(double number) /*-{
		return ""+(Math.round(number + "e+2")  + "e-2");
	}-*/;
	public void displayScoreCountData(CollectionSummaryMetaDataDo result){
		getView().displayScoreCount(result);
	}
	@Override
	public void getCollectionMetaDataByUserAndSession(final String collectionId,final String classId, final String userId, final String sessionId,final PrintUserDataDO printData) {

		if (sessionId != null){
			this.analyticService.getCollectionMetaDataByUserAndSession(StringUtil.getClassObj(),collectionId, classId, userId, sessionId, new AsyncCallback<ArrayList<CollectionSummaryMetaDataDo>>() {
				@Override
				public void onSuccess(ArrayList<CollectionSummaryMetaDataDo> result) {

					if(result!=null && result.size()!=0){
						count=0;
						if(result.get(0).getSession()!=null && result.get(0).getSession().size()!=0){

							int sessionSize=result.get(0).getSession().size();

							int day=result.get(0).getSession().get(sessionSize-1).getSequence();
							printData.setUserName(null);
							printData.setSession(day+AnalyticsUtil.getOrdinalSuffix(day)+" Session");
							printData.setSessionStartTime(AnalyticsUtil.getSessionsCreatedTime((Long.toString(result.get(0).getSession().get(sessionSize-1).getEventTime()))));
							getView().setSessionsData(result.get(0).getSession());
						}

						displayScoreCountData(result.get(0));
						getView().setCollectionMetaDataByUserAndSession(result);
						setCollectionSummaryData(collectionId, classId,	userId, sessionId, printData);
					}else{
						Timer timer = new Timer() {

							@Override
							public void run() {
								if (count < 10){
									getSessionsDataByUser(collectionId, classId, userId);
									count++;
								}else{
									if (count >= 10){
										getView().errorMsg();
									}
								}
							}
						};
						timer.schedule(100);
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					getView().errorMsg();
				}
			});
		}else{
			getView().errorMsg();
		}

	}

	@Override
	public void getCollectionScoreForSession(final String collectionId,final String classId, final String userId, final String sessionId,final PrintUserDataDO printData) {
		this.analyticService.getCollectionMetaDataByUserAndSession(StringUtil.getClassObj(),collectionId, classId, userId, sessionId, new AsyncCallback<ArrayList<CollectionSummaryMetaDataDo>>() {
			@Override
			public void onSuccess(ArrayList<CollectionSummaryMetaDataDo> result) {
				if(result!=null && result.size()!=0){
					displayScoreCountData(result.get(0));
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				
			}
		});

	}



	/*analytics*/

	public void setIndividualData(final String collectionId, final String classpageId,final String userId, final String sessionId,final String pathwayId,final boolean isSummary,final PrintUserDataDO printUserDataDO) {

		getView().loadingIcon();

		analyticService.getUserSessionDataByUser(StringUtil.getClassObj(),collectionId, classpageId,userId, sessionId, pathwayId,new AsyncCallback<ArrayList<UserDataDo>>() {

			@Override
			public void onSuccess(ArrayList<UserDataDo> result) {
				if(result!=null){
					setIndividualData(result);
				}else{
					getView().errorMsg();
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				getView().errorMsg();
			}
		});

	}


	public void setIndividualData(final ArrayList<UserDataDo> result) {

		getView().loadingIcon();
		collectionProgressCount=0;
		Collections.sort(result,new Comparator<UserDataDo>() {
        	public int compare(UserDataDo o1, UserDataDo o2) {
        		 Integer obj1 = new Integer(o1.getSequence());
				 Integer obj2 = new Integer(o2.getSequence());
        	     return obj1.compareTo(obj2);
        	}
        });
		questionRowIndex.clear();
		questionsData.clear();
		for (UserDataDo userDataDo : result) {
				if(QUESTION.equalsIgnoreCase(userDataDo.getResourceFormat())){
					if(!OE.equalsIgnoreCase(userDataDo.getType())){
						questionsData.add(userDataDo);
					}
					questionRowIndex.add(collectionProgressCount);
				}
				collectionProgressCount++;
		}
		getView().setQuestionsData(questionsData);
		getView().setQuestionsPrintData(questionsData);


	}

	@Override
	public void setHtmltopdf(final String htmlString, final String fileName,final boolean isClickedOnEmail) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				analyticService.setHTMLtoPDF(htmlString,fileName,isClickedOnEmail, new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {

						if(!StringUtil.checkNull(result)){
							if(isClickedOnEmail){
								getView().setPdfForEmail(result);
							}else{
								getView().getFrame().setUrl(result);
							}
						}
					}

					@Override
					public void onFailure(Throwable caught) {
					}
				});

			}
		});
	}

	public void getAssessmentSummaryDetails(){

		String classId=AppClientFactory.getPlaceManager().getRequestParameter("class");
		String courseId=AppClientFactory.getPlaceManager().getRequestParameter("course");
		String unitId=AppClientFactory.getPlaceManager().getRequestParameter("unit");
		String lessonId=AppClientFactory.getPlaceManager().getRequestParameter(LESSON);
		String assessmentId=AppClientFactory.getPlaceManager().getRequestParameter("assessment");

		ClassDo classObj=new ClassDo();
		classObj.setClassId(classId);
		classObj.setCourseId(courseId);
		classObj.setLessonId(lessonId);
		classObj.setUnitId(unitId);
		classObj.setAssessmentId(assessmentId);


		this.analyticService.getAssessmentSummary(classObj, new AsyncCallback<AssessmentSummaryStatusDo>() {

			@Override
			public void onFailure(Throwable caught) {
				throw new RuntimeException("Not implemented");
			}

			@Override
			public void onSuccess(AssessmentSummaryStatusDo result) {
				getView().displaySummaryMetadata(result);
			}
		});
	}
	public void setSessionId(String sessionId){
		this.sessionId=sessionId;
	}

	@Override
	protected void onReset() {
		getView().getQuestionsTable().clear();
	}

}
