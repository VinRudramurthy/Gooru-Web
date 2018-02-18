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
package org.ednovo.gooru.client.mvp.analytics.collectionSummary;
import java.util.ArrayList;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.AnalyticsServiceAsync;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryUsersDataDo;
import org.ednovo.gooru.application.shared.model.analytics.PrintUserDataDO;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.CollectionSummaryIndividualPresenter;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryTeacher.CollectionSummaryTeacherPresenter;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
public class CollectionSummaryPresenter extends PresenterWidget<IsCollectionSummaryView> implements CollectionSummaryUiHandlers,ClientConstants{

	public static final  Object TEACHER_STUDENT_SLOT = new Object();

	private CollectionSummaryTeacherPresenter collectionSummaryTeacherPresenter;

	private CollectionSummaryIndividualPresenter collectionSummaryIndividualPresenter;

    CollectionSummaryMetaDataDo collectionMetadata;

	@Inject
	private  AnalyticsServiceAsync analyticService;

	String collectionId;

	/**
	 * Constructor
	 * @param eventBus
	 * @param view
	 * @param collectionSummaryTeacherPresenter
	 * @param collectionSummaryIndividualPresenter
	 */
	@Inject
	public CollectionSummaryPresenter(EventBus eventBus, IsCollectionSummaryView view,CollectionSummaryTeacherPresenter collectionSummaryTeacherPresenter,CollectionSummaryIndividualPresenter collectionSummaryIndividualPresenter) {
		super(eventBus, view);
		this.collectionSummaryTeacherPresenter=collectionSummaryTeacherPresenter;
		this.collectionSummaryIndividualPresenter=collectionSummaryIndividualPresenter;
		getView().setUiHandlers(this);
	}

	/**
	 * This method will return the analytics service
	 * @return
	 */
	public AnalyticsServiceAsync getAnalyticService() {
		return analyticService;
	}

	/**
	 * This method will set the analytics serivice
	 * @param analyticService
	 */
	public void setAnalyticService(AnalyticsServiceAsync analyticService) {
		this.analyticService = analyticService;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummary.CollectionSummaryUiHandlers#setCollectionSummaryData(java.lang.String, java.lang.String)
	 */
	@Override
	public void setCollectionSummaryData(final String collectionId,final String pathwayId) {
		this.collectionId=collectionId;
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				getView().getLoadinImage().setVisible(true);
				final String classpageId=AppClientFactory.getPlaceManager().getRequestParameter("classpageid", null);
				analyticService.getCollectionSummaryUsersData(classpageId,new AsyncCallback<ArrayList<CollectionSummaryUsersDataDo>>() {

					@Override
					public void onSuccess(ArrayList<CollectionSummaryUsersDataDo> result) {
						if(result !=null){
							getView().setUsersData(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
					}
				});
				analyticService.getAssignmentAverageData(classpageId, pathwayId, collectionId,new AsyncCallback<CollectionSummaryMetaDataDo>() {

					@Override
					public void onSuccess(CollectionSummaryMetaDataDo result) {
						getView().setCollectionMetaData(result,pathwayId,classpageId);
						collectionMetadata=result;
						setTeacherData(collectionId,classpageId,pathwayId);
					}

					@Override
					public void onFailure(Throwable caught) {
					}
				});

			}
		});
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummary.CollectionSummaryUiHandlers#loadUserSessions(java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.ednovo.gooru.shared.model.analytics.PrintUserDataDO)
	 */
	@Override
	public void loadUserSessions(final String sessionId,final String collectionId,final String classId,final String userId,final String pathwayId,final PrintUserDataDO printUserDataDO) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {


				analyticService.getSessionsDataByUser(StringUtil.getClassObj(),collectionId, classId, userId, new AsyncCallback<ArrayList<CollectionSummaryUsersDataDo>>() {

					@Override
					public void onSuccess(ArrayList<CollectionSummaryUsersDataDo> result) {
						getView().setUserSessionsData(result);
						if(result.size()!=0){
							printUserDataDO.setSession(FIRST_SESSION);
							printUserDataDO.setSessionStartTime(AnalyticsUtil.getSessionsCreatedTime(Long.toString(result.get(0).getTimeStamp())));
							setIndividualData(collectionId,classId,userId,result.get(0).getSessionId(), pathwayId,printUserDataDO);
						}else{
							getView().resetDataIfNoSessions();
							clearSlot(TEACHER_STUDENT_SLOT);
							setInSlot(TEACHER_STUDENT_SLOT, null,false);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
					}
				});

			}
		});
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummary.CollectionSummaryUiHandlers#setTeacherData(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void setTeacherData(final String collectionId,final String classpageId, final String pathwayId) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				getView().getLoadinImage().setVisible(true);
				clearSlot(TEACHER_STUDENT_SLOT);
				collectionSummaryTeacherPresenter.setTeacherData(collectionId,classpageId,pathwayId,collectionMetadata,getView().getLoadinImage());
				setInSlot(TEACHER_STUDENT_SLOT, collectionSummaryTeacherPresenter,false);

			}
		});
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummary.CollectionSummaryUiHandlers#setIndividualData(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.ednovo.gooru.shared.model.analytics.PrintUserDataDO)
	 */
	@Override
	public void setIndividualData(final String collectionId,final String classpageId,final String userId,final String sessionId,final String pathwayId, final PrintUserDataDO printUserDataDO) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				getView().getLoadinImage().setVisible(true);
				clearSlot(TEACHER_STUDENT_SLOT);
				collectionSummaryIndividualPresenter.setIndividualData(collectionId,classpageId,userId,sessionId,pathwayId,true,getView().getLoadinImage(),printUserDataDO);
				setInSlot(TEACHER_STUDENT_SLOT, collectionSummaryIndividualPresenter,false);

			}
		});
	}
	@Override
	protected void onHide() {
		super.onHide();
		clearFrames();
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummary.CollectionSummaryUiHandlers#exportCollectionSummary(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void exportCollectionSummary(final String collectionId, final String classpageId,final String userId, final String sessionId,final String pathwayId, final String timeZone) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				analyticService.exportTeacherSummary(collectionId, pathwayId, classpageId, timeZone, new AsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {
						if(!StringUtil.isEmpty(result)){
							getView().getFrame().setUrl(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
					}
				});

			}
		});
	}
	@Override
	public void clearFrames(){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				getView().getFrame().setUrl("");
				collectionSummaryTeacherPresenter.clearFrame();
				collectionSummaryIndividualPresenter.clearFrame();

			}
		});
	}
}
