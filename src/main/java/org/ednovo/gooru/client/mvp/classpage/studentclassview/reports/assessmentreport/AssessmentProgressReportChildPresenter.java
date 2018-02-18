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
/**
 *
 */
package org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.assessmentreport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.child.ChildPresenter;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.PrintUserDataDO;
import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;
import org.ednovo.gooru.application.shared.model.classpages.ClassDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.UserPlayedSessionDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Search Team
 *
 */
public class AssessmentProgressReportChildPresenter extends ChildPresenter<AssessmentProgressReportChildPresenter, IsAssessmentProgressReportView> implements AssessmentProgressReportChildPresenterUiHandlers,ClientConstants{

	private CollectionDo collectionDo=null;

	public static final  Object METADATA_PRESENTER_SLOT = new Object();

	PrintUserDataDO printData=new PrintUserDataDO();

	String classpageId=null;

	ClasspageItemDo classpageItemDo=null;

	String sessionId=null;

	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	int count=0;

	ArrayList<UserDataDo> questionsData=new ArrayList<UserDataDo>();
	
	ArrayList<UserDataDo> printDataDo=new ArrayList<UserDataDo>();
	
	final List<Integer> questionRowIndex=new ArrayList<Integer>();

	boolean isSession = true;
	
	public AssessmentProgressReportChildPresenter(IsAssessmentProgressReportView childView) {
		super(childView);
	}

	public void setCollectionMetadata(final CollectionDo collectionDo,String classpageId){
		this.collectionDo=collectionDo;
		this.classpageId=classpageId;
		getView().setCollectionMetadata(collectionDo);
	}

	public void setCollectionSummaryData(String collectionId,String classpageId,String userId,String sessionId,PrintUserDataDO printData,String type){
		setIndividualData(collectionId, this.classpageId!=null?this.classpageId:"", userId, sessionId,"",false,printData,type);
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

	@Override
	public void getSessionsDataByUser(final String collectionId,final String classId,String courseId, String unitId, String lessonId,final String userId) {
		ClassDo classObj=new ClassDo();
		classObj.setAssessmentId(collectionId);
		classObj.setClassId(classId);
		classObj.setSessionId(sessionId);
		getCollectionMetaDataByUserAndSession(collectionId, classId, courseId, unitId, lessonId, userId,sessionId,printData);
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
	public void getCollectionMetaDataByUserAndSession(final String collectionId,final String classId, final String courseId, final String unitId, final String lessonId, final String userId, final String sessionId,final PrintUserDataDO printData) {
		if (sessionId != null){
			ClassDo classObj=new ClassDo();
			classObj.setAssessmentId(collectionId);
			classObj.setClassId(classId);
			classObj.setCourseId(courseId);
			classObj.setUnitId(unitId);
			classObj.setLessonId(lessonId);
			classObj.setSessionId(sessionId);
			
			AppClientFactory.getInjector().getAnalyticsService().getCollectionMetaDataByUserAndSession(classObj,collectionId, classId, userId, sessionId, new AsyncCallback<ArrayList<CollectionSummaryMetaDataDo>>() {
				@Override
				public void onSuccess(ArrayList<CollectionSummaryMetaDataDo> result) {
					if(result!=null && result.size()!=0){
						getView().loaderVisibility(false);
						getView().errorPanelData(false, true);
						count=0;
						if(!isSession()) {
							if(result.get(0).getSession()!=null && result.get(0).getSession().size()!=0){
								int sessionSize=result.get(0).getSession().size();
								int day=result.get(0).getSession().get(sessionSize-1).getSequence();
								getView().setAttemptsData(result.get(0).getSession());
							}
							setSession(true);
						}
						displayScoreCountData(result.get(0));
						getView().setCollectionMetaDataByUserAndSession(result);
						setCollectionSummaryData(collectionId, classId,	userId, sessionId, printData,null);
					}else{
						Timer timer = new Timer() {
							@Override
							public void run() {
								if (count < 10){
									getSessionsDataByUser(collectionId, classId, courseId, unitId, lessonId, userId);
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
	
	/*analytics*/

	public void setIndividualData(final String collectionId, final String classpageId,final String userId, final String sessionId,final String pathwayId,final boolean isSummary,final PrintUserDataDO printUserDataDO, final String type) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				getView().loadingIcon();
				ClassDo classObj=new ClassDo();
				classObj.setAssessmentId(collectionId);
				classObj.setClassId(classpageId);
				classObj.setSessionId(sessionId);

				AppClientFactory.getInjector().getAnalyticsService().getUserSessionDataByUser(classObj,collectionId, classpageId,userId, sessionId, pathwayId,new AsyncCallback<ArrayList<UserDataDo>>() {

					@Override
					public void onSuccess(ArrayList<UserDataDo> result) {
						if(!StringUtil.checkNull(result)){
							setIndividualData(result,type);
						} else {
							getView().errorMsg();
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						getView().errorMsg();
					}
				});
			}
		});
	}
	
	public void setIndividualData(final ArrayList<UserDataDo> result, final String type) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				getView().loadingIcon();
				Collections.sort(result,new Comparator<UserDataDo>() {
		        	public int compare(UserDataDo o1, UserDataDo o2) {
		        		 Integer obj1 = new Integer(o1.getSequence());
						 Integer obj2 = new Integer(o2.getSequence());
		        	     return obj1.compareTo(obj2);
		        	}
		        });
				questionRowIndex.clear();
				questionsData.clear();
				boolean isCollection = false;
				String isContentType=AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT, UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT);
				if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.COLLECTION_PLAY)||isContentType.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASSPAGE_COLLECTION)) {
					isCollection = true;
				}
				printDataDo = result;
				for (UserDataDo userDataDo : result) {
					if(isCollection) {
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
					} else {
						if(QUESTION.equalsIgnoreCase(userDataDo.getResourceFormat())){
							if(!OE.equalsIgnoreCase(userDataDo.getType())){
								questionsData.add(userDataDo);
							}
						}
					}
				}
				if(isCollection) {
					if(type!=null&&type.equalsIgnoreCase(QUESTION)) {
						getView().setQuestionsData(questionsData,QUESTION,false);
					} else if(type!=null&&type.equalsIgnoreCase(OE)) {
						getView().setQuestionsData(questionsData,OE,false);
					} else {
						getView().setResourcesData(questionsData,false);
					}
				} else {
					getView().setQuestionsData(questionsData,QUESTION,false);
				}
			}
		});
	}

	@Override
	public void setHtmltopdf(final String htmlString, final String fileName,final boolean isClickedOnEmail) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				AppClientFactory.getInjector().getAnalyticsService().setHTMLtoPDF(htmlString,fileName,isClickedOnEmail, new AsyncCallback<String>() {
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
	
	public void setSessionId(String sessionId){
		this.sessionId=sessionId;
	}

	@Override
	public void getContentPlayAllSessions(final String gooruUid, final String classGooruId, final String lessonGooruId, final String unitGooruId, final String courseGooruId, final String assessmentId, final String currentSessionId) {
		AppClientFactory.getInjector().getClasspageService().getContentPlayAllSessions(gooruUid, classGooruId, lessonGooruId, unitGooruId, courseGooruId, assessmentId, new SimpleAsyncCallback<List<UserPlayedSessionDo>>() {
			@Override
			public void onSuccess(List<UserPlayedSessionDo> result) {
				if(result!=null&&result.size()>0) {
					getView().loaderVisibility(false);
					getView().errorPanelData(false, true);
					setSessionId(result.get(0).getSessionId());
					getSessionsDataByUser(assessmentId, classGooruId, classGooruId, lessonGooruId, unitGooruId, gooruUid);
					getView().setSessionsData(result);
				} else {
					getView().setAnonymousData();
				}
			}
		});
	}

	@Override
	public void getCollectionScoreForSession(final String collectionId,final String classId, final String userId, final String sessionId,final PrintUserDataDO printData) {
		ClassDo classObj=new ClassDo();
		classObj.setAssessmentId(collectionId);
		classObj.setClassId(classId);
		classObj.setSessionId(sessionId);
		AppClientFactory.getInjector().getAnalyticsService().getCollectionMetaDataByUserAndSession(classObj,collectionId, classId, userId, sessionId, new AsyncCallback<ArrayList<CollectionSummaryMetaDataDo>>() {
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
	
	public boolean isSession() {
		return isSession;
	}

	public void setSession(boolean isSession) {
		this.isSession = isSession;
	}

	@Override
	public ArrayList<UserDataDo> getPrintDataDo() {
		return printDataDo;
	}

	public void setPrintDataDo(ArrayList<UserDataDo> printDataDo) {
		this.printDataDo = printDataDo;
	}
	
}