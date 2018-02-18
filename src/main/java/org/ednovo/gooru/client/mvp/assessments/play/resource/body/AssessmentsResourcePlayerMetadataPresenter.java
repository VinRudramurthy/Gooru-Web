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
package org.ednovo.gooru.client.mvp.assessments.play.resource.body;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ContentReportDo;
import org.ednovo.gooru.application.shared.model.content.ContentStarRatingsDo;
import org.ednovo.gooru.application.shared.model.content.ReactionDo;
import org.ednovo.gooru.application.shared.model.content.ResourceTagsDo;
import org.ednovo.gooru.application.shared.model.content.StarRatingsDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.htmltags.SectionTag;
import org.ednovo.gooru.client.mvp.assessments.play.collection.AssessmentsPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.end.AssessmentsEndPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.AssessmentsResourcePlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.question.AssessmentsQuestionResourcePresenter;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.rating.RatingAndReviewPopupPresenter;
import org.ednovo.gooru.client.mvp.rating.events.DeletePlayerStarRatingsEvent;
import org.ednovo.gooru.client.mvp.rating.events.DeletePlayerStarReviewEvent;
import org.ednovo.gooru.client.mvp.rating.events.OpenReviewPopUpEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateUserStarReviewEvent;
import org.ednovo.gooru.client.mvp.standards.StandardsPopupPresenter;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class AssessmentsResourcePlayerMetadataPresenter extends PresenterWidget<IsAssessmentsResourcePlayerMetadataView> implements AssessmentsResourcePlayerMetadataUiHandlers{

	private AssessmentsQuestionResourcePresenter questionResourcePresenter;

	private AssessmentsPlayerPresenter collectionPlayerPresenter;

	private AssessmentsPreviewPlayerPresenter previewPlayerPresenter;
	
	StandardsPopupPresenter standardsPopupPresenter;

	private AssessmentsResourcePlayerPresenter resourcePlayerPresenter;

	private boolean isCollectionPlayer=false;

	private boolean isResourcePlayer=false;

	private boolean isPreviewPlayer=false;

	private RatingAndReviewPopupPresenter ratingAndReviewPopup;

	private String resourceTitle = null;

	private static final String REACTION = "reaction";

	private static final String RATING = "rating";

	@Inject
	public AssessmentsResourcePlayerMetadataPresenter(EventBus eventBus, IsAssessmentsResourcePlayerMetadataView view,AssessmentsQuestionResourcePresenter questionResourcePresenter,AssessmentsEndPresenter collectionEndPresenter,RatingAndReviewPopupPresenter ratingAndReviewPopup,StandardsPopupPresenter standardsPopupPresenter) {
		super(eventBus, view);
		this.questionResourcePresenter=questionResourcePresenter;
		this.ratingAndReviewPopup = ratingAndReviewPopup;
		this.standardsPopupPresenter=standardsPopupPresenter;
		getView().setUiHandlers(this);
		addRegisteredHandler(OpenReviewPopUpEvent.TYPE, this);
		addRegisteredHandler(UpdateUserStarReviewEvent.TYPE,this);
		addRegisteredHandler(DeletePlayerStarReviewEvent.TYPE,this);
		addRegisteredHandler(DeletePlayerStarRatingsEvent.TYPE,this);
	}

	public void showResourceWidget(CollectionItemDo collectionItemDo){
		if(collectionItemDo!=null){
		getView().showResourceWidget(collectionItemDo);
		}
	}

	public void showResourceWidget(CollectionItemDo collectionItemDo,PlaceRequest nextResoruceRequest,PlaceRequest previousResourceRequest){
		getView().showResourceWidget(collectionItemDo,nextResoruceRequest,previousResourceRequest);
	}

	public void showResourceWidget(CollectionDo collectionDo,PlaceRequest previousResourceRequest){
		getView().showResourceWidget(previousResourceRequest);
		getView().getResourceWidgetContainer().clear();
	}

	@Override
	public void showQuestionView(CollectionItemDo collectionItemDo) {
		questionResourcePresenter.setCollectionPlayer(isCollectionPlayer);
		questionResourcePresenter.setResourcePlayer(isResourcePlayer);
		questionResourcePresenter.setPreviewPlayer(isPreviewPlayer);
		questionResourcePresenter.setCollectionPlayerPresnter(collectionPlayerPresenter);
		questionResourcePresenter.setResourcePlayerPresenter(resourcePlayerPresenter);
		questionResourcePresenter.setPreviewPlayerPresenter(previewPlayerPresenter);
		questionResourcePresenter.showQuestionPreview(collectionItemDo);
		int windowHeight=Window.getClientHeight();
		if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.RESOURCE_PLAY)){
			questionResourcePresenter.getWidget().setHeight((windowHeight-116)+"px");
		}else{
			questionResourcePresenter.getWidget().setHeight((windowHeight-202)+"px");
		}

		getView().getResourceWidgetContainer().add(questionResourcePresenter.getWidget());
		questionResourcePresenter.getWidget().getParent().getElement().getStyle().clearPaddingTop();
	}

	@Override
	public FlowPanel getQuestioncontainer(){
		return questionResourcePresenter.getQuestioncontainer();
	}
	public void setCollectionPlayerPresnter(AssessmentsPlayerPresenter collectionPlayerPresenter,boolean isCollectionPlayer){
		this.collectionPlayerPresenter=collectionPlayerPresenter;
		this.isCollectionPlayer=true;
		this.isResourcePlayer=false;
		this.isPreviewPlayer=false;
	}

	public void setPreviewPlayerPresenter(AssessmentsPreviewPlayerPresenter previewPlayerPresenter){
		this.previewPlayerPresenter=previewPlayerPresenter;
		this.isCollectionPlayer=false;
		this.isResourcePlayer=false;
		this.isPreviewPlayer=true;
	}

	public void setResourcePlayerPresenter(AssessmentsResourcePlayerPresenter resourcePlayerPresenter,boolean isCollectionPlayer){
		this.resourcePlayerPresenter=resourcePlayerPresenter;
		this.isCollectionPlayer=false;
		this.isResourcePlayer=true;
		this.isPreviewPlayer=false;
	}

	public void removeUserAttemptResult(){
		if(isCollectionPlayer){
			collectionPlayerPresenter.removeUserAttemptResult();
		}else if(isResourcePlayer){
			resourcePlayerPresenter.removeUserAttemptResult();
		}else if(isPreviewPlayer){
			previewPlayerPresenter.removeUserAttemptResult();
		}
	}

	public boolean isOeAnswerSubmited(){
		boolean isOeAnswerSubmited=true;
		if(isCollectionPlayer){
			isOeAnswerSubmited=collectionPlayerPresenter.isOpenEndedAnswerSubmited();
		}else if(isResourcePlayer){
			isOeAnswerSubmited=resourcePlayerPresenter.isOpenEndedAnswerSubmited();
		}else if(isPreviewPlayer){
			isOeAnswerSubmited=previewPlayerPresenter.isOpenEndedAnswerSubmited();
		}
		return isOeAnswerSubmited;
	}
	public void  triggerCreateReactionEvent(String resourceId,String reactionType,String eventName){
		Long startTime=PlayerDataLogEvents.getUnixTime();
		if(isCollectionPlayer){
			collectionPlayerPresenter.triggerReactiontDataLogEvent(resourceId, startTime, startTime, reactionType,eventName);
		}else if(isResourcePlayer){
		}else if(isPreviewPlayer){
			previewPlayerPresenter.triggerReactiontDataLogEvent(resourceId, startTime, startTime, reactionType,eventName);
		}
	}

	public void resetResourceMetaData(){
		getView().getResourceWidgetContainer().clear();
	}
	public void removeRatingContainer(boolean flag){
		getView().removeRatingContainer(flag);
	}
	@Override
	public void createReaction(final String resourceId,final String reactionText,String gooruReactionId,String collectionId, String createStudyPlayerReaction,final int emoticNum) {
		AppClientFactory.getInjector().getPlayerAppService().createReaction(resourceId,reactionText,gooruReactionId,collectionId,createStudyPlayerReaction, new SimpleAsyncCallback<ReactionDo>() {
			@Override
			public void onSuccess(ReactionDo result) {
				if(result!=null){
					getView().setReaction(result,result.getDeleteReactionGooruOid());
					collectionPlayerPresenter.updateRatReacSessionActivityItem(emoticNum, resourceId, REACTION);

				}
			}
		});
	}

	/**
	 * Get reaction API is called and gets respective reaction.
	 * @param collectionItemDo {@link CollectionItemDo}
	 */
	public void setReaction(CollectionItemDo collectionItemDo) {
			AppClientFactory.getInjector().getPlayerAppService().getResourceReaction(collectionItemDo.getResource().getGooruOid(),AppClientFactory.getGooruUid(), new SimpleAsyncCallback<ArrayList<ReactionDo>>() {
				@Override
				public void onSuccess(ArrayList<ReactionDo> result) {
					if(result!=null){
						String gooruReactionId="";
						if(result.size()==0){
							getView().setDefaultReaction();
						}else{
							for(int i =0;i<result.size();i++){
								if(result.get(i).getDeleteReactionGooruOid()!=null){
									gooruReactionId = gooruReactionId+result.get(i).getDeleteReactionGooruOid();
									if(result.size()!=(i+1)){
										gooruReactionId=gooruReactionId+",";
									}
								}
							}
							getView().setReaction(result.get(0),gooruReactionId);
						}
					}
				}
			});
	}

	@Override
	public void deleteReaction(String gooruReactionId) {
			AppClientFactory.getInjector().getPlayerAppService().deleteReaction(gooruReactionId,new SimpleAsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {

				}
			});
	}

	public void clearIfrmaeContent(){
		try {
			Element ifrmaeElement = Document.get().getElementById("resourcePlayerContainer");
			if (ifrmaeElement != null) {
				ifrmaeElement.setAttribute("src", "");
			}
		} catch (Exception exception) {
			AppClientFactory.printSevereLogger("AssessmentsResourcePlayerMetadataPresenter : clearIfrmaeContent : "+exception.getMessage());
		}
	}

	/**
	 * API call to create the ratings based on the user input and triggers the API.
	 *
	 * @param associateGooruOid {@link String}
	 * @param starRatingValue {@link Integer}
	 * @param clickEvent {@link ClickEvent}
	 */
	@Override
	public void createStarRatings(String associateGooruOid, final int starRatingValue, final boolean showThankYouToolTip,String userReview, final String resourceGooruId) {
		if(showThankYouToolTip){
			triggerCreateRatingEvent(resourceGooruId, starRatingValue, getView().getPreviousRating());
		}else{
			triggerCreateReviewEvent(resourceGooruId, userReview);
		}
			AppClientFactory.getInjector().getPlayerAppService().createStarRatings(associateGooruOid,starRatingValue,userReview,new SimpleAsyncCallback<StarRatingsDo>() {
				@Override
				public void onSuccess(StarRatingsDo result) {
					getView().setUserStarRatings(result,showThankYouToolTip);
					if(collectionPlayerPresenter!=null){
						collectionPlayerPresenter.updateRatReacSessionActivityItem(starRatingValue, resourceGooruId, RATING);
					}
					if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RESOURCE_PLAY)){
						if(resourcePlayerPresenter!=null){
							resourcePlayerPresenter.updateRatReacSessionActivityItem(starRatingValue, resourceGooruId, RATING);
						}
					}

				}
			});
	}

	/**
	 * API call to set Ratings for a resource.
	 *
	 * @param collectionItemDo {@link CollectionItemDo}
	 */
	public void setResourceStarRatings(CollectionItemDo collectionItemDo) {

		AppClientFactory.getInjector().getPlayerAppService().getResourceRatingWithReviews(collectionItemDo.getResource().getGooruOid(), AppClientFactory.getGooruUid(),0, new SimpleAsyncCallback<ArrayList<StarRatingsDo>>() {

			@Override
			public void onSuccess(ArrayList<StarRatingsDo> result) {
				if(result.size()>0){
					getView().setUserStarRatings(result.get(0),false);
				}else{
					getView().setUserStarRatings(null,false);
				}

			}
		});
	}

	/**
	 * Receiving event to post the review.
	 */

	public void postReview(String assocGooruOId, String userReview, Integer score,boolean isUpdate) {
		getView().postReview(assocGooruOId,userReview,score,isUpdate);
	}


	public void postReviewForResource(String assocGooruOId, String userReview, Integer score,boolean isUpdate) {
		getView().postReview(assocGooruOId,userReview,score,isUpdate);
	}

	/**
	 * API call to updats the Ratings for a resource.
	 *
	 * @param gooruOid {@link String}
	 * @param starRatingValue {@link Integer}
	 * @param showThankYouToolTip {@link Boolean}
	 */
	@Override
	public void updateStarRatings(String gooruOid, int starRatingValue,boolean showThankYouToolTip,String resourceGooruId) {
		triggerCreateRatingEvent(resourceGooruId, starRatingValue, getView().getPreviousRating());
		if(collectionPlayerPresenter!=null){
			collectionPlayerPresenter.updateRatReacSessionActivityItem(starRatingValue, resourceGooruId, RATING);
		}
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RESOURCE_PLAY)){
			if(resourcePlayerPresenter!=null){
				resourcePlayerPresenter.updateRatReacSessionActivityItem(starRatingValue, resourceGooruId, RATING);
			}
		}

		AppClientFactory.getInjector().getPlayerAppService().updateResourceStarRatings(gooruOid, starRatingValue, new SimpleAsyncCallback<ArrayList<StarRatingsDo>>(){

			@Override
			public void onSuccess(ArrayList<StarRatingsDo> result) {
				if(result!=null && result.size()>0){
					getView().setUserStarRatings(result.get(0),true);
				}
			}
		});
	}


	@Override
	public void createCollectionContentReport(String associatedGooruOid,String freeText,ArrayList<String> contentReportList,String deleteContentReportGooruOids) {
		AppClientFactory.getInjector().getPlayerAppService().createContentReport(associatedGooruOid, freeText, contentReportList, deleteContentReportGooruOids, new SimpleAsyncCallback<ContentReportDo>() {
			@Override
			public void onSuccess(ContentReportDo result) {
				String chkViewPage = AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getParameter("view", null);
				if(chkViewPage == null)
				{
				if(isPreviewPlayer){
					previewPlayerPresenter.updateFlagImageOnHomeView();
				}else if(isCollectionPlayer){
					collectionPlayerPresenter.updateFlagImageOnHomeView();
				}
				}
				else
				{
					if(isPreviewPlayer){
						previewPlayerPresenter.updateFlagImageOnHomeView();
					}else if(isCollectionPlayer){
						collectionPlayerPresenter.updateFlagImageOnHomeView();
					}
				}
			}
		});

	}

	/**
	 * Updates the Reviews for a resource.
	 *
	 * @param gooruOid {@link String}
	 * @param starRatingValue {@link Integer}
	 * @param showThankYouToolTip {@link Boolean}
	 */
	@Override
	public void updateReview(String deleteRatingGooruOid, Integer score,String userReview,String resourceGooruId) {
		triggerCreateReviewEvent(resourceGooruId, userReview);
		AppClientFactory.getInjector().getPlayerAppService().updateResourceStarReviews(deleteRatingGooruOid, score, userReview, new SimpleAsyncCallback<ArrayList<StarRatingsDo>>(){

			@Override
			public void onSuccess(ArrayList<StarRatingsDo> result) {
				if(result!=null && result.size()>0){
					getView().hideThankYouPopUp();
				}
			}
		});
	}

	/**
	 * Receiving event to open review pop-up
	 *
	 */
	@Override
	public void openReviewPopUp(String assocGooruOId, String title,String createrName) {
		Window.enableScrolling(false);
		ratingAndReviewPopup.displayPopup(resourceTitle, assocGooruOId,createrName);
		ratingAndReviewPopup.getWidget().getElement().getStyle().setZIndex(999999);
		addToPopupSlot(ratingAndReviewPopup);
	}
	/**
	 * API call to get a avg rating and total count.
	 *
	 * @param assocGooruOid {@link String}
	 * @param score {@link Integer}
	 * @param review {@link String}
	 */
	@Override
	public void getAvgRatingAndCount(final String assocGooruOid,final Integer score, final String review) {
		AppClientFactory.getInjector().getPlayerAppService().getContentStarRatings(assocGooruOid, new SimpleAsyncCallback<ContentStarRatingsDo>() {

			@Override
			public void onSuccess(ContentStarRatingsDo result) {
				getView().setRatingMetaData(assocGooruOid,score,review,result.getAverage(),result.getCount());
			}
		});
	}

	@Override
	public void setResourceMetaData(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}

	@Override
	public void updateStarRatingAndreviews(
			ArrayList<StarRatingsDo> starRatingsDo) {
		getView().setUserStarRatings(starRatingsDo.get(0),false);
	}

	public void clearStarRatings() {
		getView().clearAllStarsForAnnonymous();
	}

	public void childLoggedIn(boolean isChild) {
		getView().childLoggedIn(isChild);
	}

	@Override
	public void deleteStarRatings(String gooruId) {
		getView().deleteRatingsValue();
	}

	public void getGoogleDriveFile(String fileUrl) {
		AppClientFactory.getInjector().getPlayerAppService().getGoogleDriveFileStatusCode(fileUrl, new SimpleAsyncCallback<Integer>() {
			@Override
			public void onSuccess(Integer statusCode) {
				getView().setGoogleDriveFileStatusCode(statusCode);
			}
		});
	}

	public void getResourceTagsToDisplay(String resourceId){
		AppClientFactory.getInjector().getResourceService().getTagsToResource(resourceId, new SimpleAsyncCallback<List<ResourceTagsDo>>() {
			@Override
			public void onSuccess(List<ResourceTagsDo> resourceTagsList) {
				getView().displayResourceTags(resourceTagsList);
			}
		});
	}

	@Override
	public void getYoutubeFeedCallback(String utubeId) {
		AppClientFactory.getInjector().getPlayerAppService().getYoutubeFeedCallback(utubeId, new SimpleAsyncCallback<Map<String,String>>() {

			@Override
			public void onSuccess(Map<String, String> result) {

				getView().checkYoutubeAccessControls(result);
			}
		});

	}
	public SectionTag getCollectionContainer(){
		return getView().getCollectionContainer();
	}

	public SectionTag getResourceWidgetContainer(){
		return getView().getResourceWidgetContainer();
	}

	public Button getNarrationButton(){
		return getView().getNarrationButton();
	}
	public Button getFullScreenButton(){
		return getView().getFullScreenButton();
	}

	public void clearMarginTop(){
		getView().clearMarginTop();
	}
	public void setMarginTop(){
		getView().setMarginTop();
	}

	@Override
	public void updateResourceReview(String gooruOid,Integer reviewCount) {
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.ASSESSMENT_PLAY)){
			collectionPlayerPresenter.updateReviewAndRatings(gooruOid,reviewCount);
		}else{
			resourcePlayerPresenter.updateReviewAndRatings(gooruOid,reviewCount);
		}
	}

	@Override
	public void updateResourceRatings(String gooruOid,double average) {
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.ASSESSMENT_PLAY)){
			collectionPlayerPresenter.updateRatings(gooruOid,average);
		}else{
			resourcePlayerPresenter.updateRatings(gooruOid,average);
		}
	}

	public double getResourceRating(String resoruceGooruOid){
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.COLLECTION_PLAY)){
			return collectionPlayerPresenter.getResourceRating(resoruceGooruOid);
		}else{
			return resourcePlayerPresenter.getResourceRating(resoruceGooruOid);
		}
	}

	@Override
	public void deleteRatingsInPlayer() {
		getView().deleteRatingsValue();
	}

	public void  triggerCreateRatingEvent(String resourceId,double currentRate, double previousRate){
		if(isCollectionPlayer){
			collectionPlayerPresenter.triggerRatingDataLogEvent(resourceId, currentRate, previousRate);
		}else if(isResourcePlayer){
			resourcePlayerPresenter.triggerRatingDataLogEvent(resourceId, currentRate, previousRate);
		}
	}

	public void  triggerCreateReviewEvent(String resourceId,String reviewText){
		if(isCollectionPlayer){
			collectionPlayerPresenter.triggerReviewDataLogEvent(resourceId, reviewText);
		}else if(isResourcePlayer){
			resourcePlayerPresenter.triggerReviewDataLogEvent(resourceId, reviewText);
		}
	}

	@Override
	public void setFullScreen(boolean isFullScreen,FlowPanel pnlFullScreenNarration) {
		getView().setFullScreen(isFullScreen,pnlFullScreenNarration);
	}

	@Override
	public void updateSessionActivityItemForReactions(int emoticRatingNumber,String gooruOid, String isRatingsReactions) {
		if(collectionPlayerPresenter!=null){
			collectionPlayerPresenter.updateRatReacSessionActivityItem(emoticRatingNumber, gooruOid, isRatingsReactions);
		}
	}

	/**
	 *
	 * @function navigateToNextResource
	 *
	 * @created_date : 09-Jul-2015
	 *
	 * @description This method is used to navigate to next resource once user clicks on save and next.
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void navigateToNextResource(PlaceRequest resourceRequest){
		getView().navigateToNextResource(resourceRequest);
	}

	@Override
	public void showStandardsPopup(String standardVal, String standardsDesc,List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
		Window.enableScrolling(false);
		standardsPopupPresenter.callStandardsBasedonTypeService(standardVal,standardsDesc);
		standardsPopupPresenter.setAssessmentsResourcePlayerMetadataPresenter(this);
		standardsPopupPresenter.setAlreadySelectedItems(collectionLiPanelWithCloseArray);
		addToPopupSlot(standardsPopupPresenter);
	}
	public void setSelectedStandards(List<Map<String,String>> standListArray){
   		getView().displaySelectedStandards(standListArray);
   	}
}
