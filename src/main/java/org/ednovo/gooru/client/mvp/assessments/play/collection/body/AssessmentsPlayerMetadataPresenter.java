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
package org.ednovo.gooru.client.mvp.assessments.play.collection.body;


import java.util.ArrayList;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.LibraryServiceAsync;
import org.ednovo.gooru.application.client.service.PlayerAppServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.ContentReportDo;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;
import org.ednovo.gooru.application.shared.model.player.CommentsDo;
import org.ednovo.gooru.application.shared.model.player.CommentsListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.assessments.play.collection.AssessmentsPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.end.study.AssessmentsHomeMetadataPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.event.AssessmentsSetPlayerLoginStatusEvent;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.end.AssessmentsPreviewEndPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.home.AssessmentsPreviewHomePresenter;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AssessmentsPlayerMetadataPresenter extends PresenterWidget<IsAssessmentsPlayerMetadataView> implements AssessmentsPlayerMetadataUiHandlers,ClientConstants{

	@Inject
	private PlayerAppServiceAsync playerAppService;

	@Inject
	private LibraryServiceAsync libraryService;

    private SimpleAsyncCallback<CommentsListDo> commentsListDoAsync;

	private CollectionDo collectionDo=null;

	private AssessmentsPlayerPresenter collectionPlayerPresenter=null;

	private AssessmentsPreviewHomePresenter previewHomePresenter;

	private AssessmentsPreviewEndPresenter previewEndPresenter;

	private AssessmentsHomeMetadataPresenter collectionHomeMetadataPresenter;

	public static final  Object METADATA_PRESENTER_SLOT = new Object();

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public AssessmentsPlayerMetadataPresenter(EventBus eventBus, IsAssessmentsPlayerMetadataView view,AssessmentsPreviewHomePresenter previewHomePresenter,
			AssessmentsPreviewEndPresenter previewEndPresenter,AssessmentsHomeMetadataPresenter collectionHomeMetadataPresenter) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.previewHomePresenter=previewHomePresenter;
		this.previewEndPresenter=previewEndPresenter;
		this.collectionHomeMetadataPresenter=collectionHomeMetadataPresenter;
	}

	public void setCollectionMetadata(final CollectionDo collectionDo){
		this.collectionDo=collectionDo;
		getView().setCollectionMetadata(collectionDo);
		setRelatedConcepts(collectionDo);
	}

	public void displayAuthorDetails(boolean isDisplayDetails){
		getView().displayAuthorDetails(isDisplayDetails);
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

	public void setViewCount(String viewCount){
		getView().setViewCount(viewCount);
	}

	public void updateLikesCount(int likesCount){
	}

	public void setUserProfileName(String gooruUid){
		getView().setUserProfileName(gooruUid);
	}

	public void resetMetadataFields(){
		getView().resetMetadataFields();
	}

	public PlayerAppServiceAsync getPlayerAppService() {
		return playerAppService;
	}

	public void setPlayerAppService(PlayerAppServiceAsync playerAppService) {
		this.playerAppService = playerAppService;
	}

	public SimpleAsyncCallback<CommentsListDo>  getCommentsListDoAsync() {
		return commentsListDoAsync;
	}

	public void setCommentsListDoAsync(SimpleAsyncCallback<CommentsListDo> commentsListDoAsync) {
		this.commentsListDoAsync = commentsListDoAsync;
	}


	public void getFlagedReport(String gooruOid) {
		playerAppService.getContentReport(collectionDo.getGooruOid(), AppClientFactory.getGooruUid(), new SimpleAsyncCallback<ArrayList<ContentReportDo>>() {
			@Override
			public void onSuccess(ArrayList<ContentReportDo> result) {
				String gooruFlagId="";
				if(result!=null){
					if(result.size()==0){
						getView().getFlagButton().setText(i18n.GL3493());
						getView().getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().previewCoverFlagImageOrange());
						getView().getFlagButton().setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().playerPreviewCoverFlagImage());
					}else{
						for(int i =0;i<result.size();i++){
							if(result.get(i).getDeleteContentGooruOid()!=null){
								gooruFlagId = gooruFlagId+result.get(i).getDeleteContentGooruOid();
							}
							getView().getFlagButton().setText(i18n.GL3498());
							getView().getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().playerPreviewCoverFlagImage());
							getView().getFlagButton().setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().previewCoverFlagImageOrange());
						}
					}
				}
			}
		});
	}

	public void setRelatedConcepts(CollectionDo collectionDo) {
		final String subject = AppClientFactory.getPlaceManager().getRequestParameter("subject");
		final String lessonId = AppClientFactory.getPlaceManager().getRequestParameter("lessonId", "123");
		final String libraryType = AppClientFactory.getPlaceManager().getRequestParameter("library", PlaceTokens.DISCOVER);

		if(subject!=null) {
			this.libraryService.getLibraryCollections(subject, lessonId, libraryType, new SimpleAsyncCallback<ArrayList<ConceptDo>>() {
				@Override
				public void onSuccess(ArrayList<ConceptDo> conceptDoList) {
					getView().isConceptsContainerVisible(true);
					getView().setRelatedConceptsContent(conceptDoList, COURSE_PAGE, subject, lessonId, libraryType);
				}
			});
		} else {
			getView().isConceptsContainerVisible(false);
		}
	}

	public void setCollectionDoOnRefresh(CollectionDo collectionDo) {
		this.collectionDo = collectionDo;
		getView().setCollectionMetadata(collectionDo);
		setRelatedConcepts(collectionDo);
	}

	public AssessmentsPlayerPresenter getCollectionPlayerPresenter() {
		return collectionPlayerPresenter;
	}

	public void setCollectionPlayerPresenter(AssessmentsPlayerPresenter collectionPlayerPresenter) {
		this.collectionPlayerPresenter = collectionPlayerPresenter;
		collectionHomeMetadataPresenter.setCollectionPlayerPresenter(collectionPlayerPresenter);
		previewHomePresenter.setCollectionPlayerPresenter(collectionPlayerPresenter);
	}

	public void setTeacherInfo(ClasspageItemDo classpageItemDo) {
		getView().setTeacherInfo(classpageItemDo);
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
	public void clearDashBoardIframe(){
		getView().clearDashBoardIframe();
	}
	public void setClasspageInsightsUrl(String classpageId, String sessionId){
		getView().setClasspageInsightsUrl(classpageId, sessionId);
	}

	public void hideTeacherInfo() {
//		getView().hideTeachPanelDetails(false);

	}
	@Override
	public FlowPanel getMenuContainer() {
		if(collectionPlayerPresenter!=null){
			return collectionPlayerPresenter.getMenuContainer();
		}else{
			return null;
		}
	}

}


