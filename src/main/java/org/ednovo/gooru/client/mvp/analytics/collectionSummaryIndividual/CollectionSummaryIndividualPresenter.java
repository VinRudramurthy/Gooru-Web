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
package org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual;
import java.util.ArrayList;

import org.apache.commons.collections.list.SetUniqueList;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.AnalyticsServiceAsync;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.OetextDataDO;
import org.ednovo.gooru.application.shared.model.analytics.PrintUserDataDO;
import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;
import org.ednovo.gooru.application.shared.model.classpages.ClassDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
public class CollectionSummaryIndividualPresenter extends PresenterWidget<IsCollectionSummaryIndividualView> implements CollectionSummaryIndividualUiHandlers,ClientConstants{
	@Inject
	private  AnalyticsServiceAsync analyticService;
	
	private String collectionId,classpageId,pathwayId,userId,sessionId;
	private boolean isSummary;
	ClasspageItemDo classpageItemDo=null;
	
	/**
	 * Constructor
	 * @param eventBus
	 * @param view
	 */
	@Inject
	public CollectionSummaryIndividualPresenter(EventBus eventBus, IsCollectionSummaryIndividualView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	/**
	 * Get analytics service
	 * @return
	 */
	public AnalyticsServiceAsync getAnalyticService() {
		return analyticService;
	}

	/**
	 * @param analyticService
	 */
	public void setAnalyticService(AnalyticsServiceAsync analyticService) {
		this.analyticService = analyticService;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.CollectionSummaryIndividualUiHandlers#setIndividualData(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, com.google.gwt.user.client.ui.HTMLPanel, org.ednovo.gooru.shared.model.analytics.PrintUserDataDO)
	 */
	@Override
	public void setIndividualData(final String collectionId, final String classpageId,final String userId, final String sessionId,final String pathwayId,final boolean isSummary,final HTMLPanel loadingImage,final PrintUserDataDO printUserDataDO) {
		this.pathwayId=pathwayId;
		this.classpageId=classpageId;
		this.collectionId=collectionId;
		this.userId=userId;
		this.sessionId=sessionId;
		this.isSummary=isSummary;
		GWT.runAsync(new SimpleRunAsyncCallback() {
			
			@Override
			public void onSuccess() {

				getView().enableAndDisableEmailButton(isSummary);
				
				
				analyticService.getCollectionMetaDataByUserAndSession(StringUtil.getClassObj(),collectionId, classpageId,userId, sessionId, new AsyncCallback<ArrayList<CollectionSummaryMetaDataDo>>() {
					
					@Override
					public void onSuccess(ArrayList<CollectionSummaryMetaDataDo> result) {
						if(!StringUtil.checkNull(result)){
							getView().setIndividualCollectionMetaData(result,printUserDataDO);
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
					
					}
				});
				analyticService.getUserSessionDataByUser(StringUtil.getClassObj(),collectionId, classpageId,userId, sessionId, pathwayId,new AsyncCallback<ArrayList<UserDataDo>>() {
					
					@Override
					public void onSuccess(ArrayList<UserDataDo> result) {
						if(!StringUtil.checkNull(result)){
							getView().setIndividualData(result,loadingImage);
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
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.CollectionSummaryIndividualUiHandlers#setHtmltopdf(java.lang.String, boolean)
	 */
	@Override
	public void setHtmltopdf(final String htmlString,final String fileName,final boolean isClickedOnEmail) {
		
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

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.CollectionSummaryIndividualUiHandlers#setOEtextData(java.lang.String, java.lang.String)
	 */
	@Override
	public void setOEtextData(final String resourceGooruId, final String questionType) {
		GWT.runAsync(new SimpleRunAsyncCallback() {
			
			@Override
			public void onSuccess() {

				analyticService.getOETextData(resourceGooruId, collectionId, classpageId, pathwayId,"CS",sessionId,userId, new AsyncCallback<ArrayList<OetextDataDO>>() {
					@Override
					public void onSuccess(ArrayList<OetextDataDO> result) {
						if(!StringUtil.checkNull(result)){
							getView().setViewResponseData(result,resourceGooruId,collectionId,classpageId,pathwayId,questionType,isSummary,sessionId,classpageItemDo);
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
	public void setNoDataMessage(HTMLPanel loadingImage) {
		loadingImage.setVisible(false);
		getView().setErrorMessage();
	}
	@Override
	public void clearFrame(){
		getView().getFrame().setUrl("");
	}
	@Override
	public void setTeacherImage(ClasspageItemDo classpageItemDo){
      this.classpageItemDo=classpageItemDo;
	}
	
}
