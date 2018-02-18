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
package org.ednovo.gooru.application.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemsList;
import org.ednovo.gooru.application.shared.model.content.ContentReportDo;
import org.ednovo.gooru.application.shared.model.content.ContentStarRatingsDo;
import org.ednovo.gooru.application.shared.model.content.ReactionDo;
import org.ednovo.gooru.application.shared.model.content.ResoruceCollectionDo;
import org.ednovo.gooru.application.shared.model.content.StarRatingsDo;
import org.ednovo.gooru.application.shared.model.content.UserPlayedSessionDo;
import org.ednovo.gooru.application.shared.model.content.UserStarRatingsDo;
import org.ednovo.gooru.application.shared.model.folder.FolderWhatsNextCollectionDo;
import org.ednovo.gooru.application.shared.model.player.CommentsDo;
import org.ednovo.gooru.application.shared.model.player.CommentsListDo;
import org.ednovo.gooru.application.shared.model.player.FeaturedContentDo;
import org.ednovo.gooru.application.shared.model.player.InsightsCollectionDo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PlayerAppServiceAsync extends BaseServiceAsync {


	public void getSimpleCollectionDetils(String simpleCollectionId,String resourceId,String tabView,String rootNodeId,AsyncCallback<CollectionDo> callback);

	public void getSimpleCollectionDetils(String apikey,String simpleCollectionId,String resourceId,String tabView,String rootNodeId,AsyncCallback<CollectionDo> callback);

	public void getResourceCollectionsList(String resourceGooruOid,String pageNum,String pageSize,AsyncCallback<ResoruceCollectionDo> callback);

	public void getResourceInfoDetails(String apiKey,String resourceId,String tabView,AsyncCallback<CollectionItemDo> callback);

	public void getResourceObj(String resourceId,AsyncCallback<CollectionItemDo> callback);

	public void getShortenShareUrl(String contentGooruOid,  AsyncCallback<Map<String, String>> callback);

	public void updateViewCount(String gooruid,String viewCount,String resourceType,AsyncCallback<String> callback);

	public void startActivityPlayerLog(String activityEventId,String activityParentEventId,String eventName,String gooruOid,String resourceGooruOid,String context,String userAgent,AsyncCallback<String> callback);

	public void stopActivityPlayerLog(String activityEventId,String activityParentEventId,String eventName,String gooruOid,String resourceGooruOid,String context,String userAgent,AsyncCallback<String> callback);

	public void createSessionTracker(String collectionGooruOid,String sessionId,String mode,AsyncCallback<String> callback);

	public void updateSessionInCollection(String sessionTrackerId,AsyncCallback<String> callback);

	public void createSessionItemInCollection(String sessionTrackerId,String collectionItemId, String resourceGooruOid,String questionType, String status,AsyncCallback<String> callback);

	public void createSessionItemAttemptTry(String contentGooruOid,String sessionTrackerId,String sessionItemTrackerId, Integer answerId, String attemptResult,AsyncCallback<String> callback);

	public void createSessionItemAttemptTryForOe(String contentGooruOid,String sessionTrackerId,String sessionItemTrackerId,String answerId, String attemptStatus,String attemptAnswerResult,AsyncCallback<String> callback);

	public void sendEmail(String fromEmail,String toEmail,String copyEmail,String subject,String message,AsyncCallback<String> callback);

	public void getUserProfileVisibility(String gooruUid,AsyncCallback<Boolean> callback);

	public void copyCollection(String collectionId,String collectionTile,AsyncCallback<String> callback);

	public void copyCollectionItem(String collectionItemId,String collectionId,AsyncCallback<String> callback);

	public void getWorkspaceCollections(String userId,String offset,String limit,AsyncCallback<ArrayList<CollectionItemsList>> callback);

	public void getContentReport(String associatedGooruOid,String gooruUid,AsyncCallback<ArrayList<ContentReportDo>> callback);

	public void createContentReport(String associatedGooruOid,String freeText,ArrayList<String> contentReportList,String deleteContentReportGooruOids,AsyncCallback<ContentReportDo> callback);

	public void deleteContentReport(String associatedGooruOid,AsyncCallback<Void> callback);

	public void createCommentForCollection(String gooruCollectionId, String userCommentsEntered,AsyncCallback<CommentsDo> callback);

	public void getCollectionCommentsList(String gooruOid, String offset, String pageLimit,AsyncCallback<CommentsListDo> callback);

	public void deleteCollectionCommentbyCommentUid(String commentUid,AsyncCallback<Void> callback);

	public void updateCollectionCommentbyCommentUid(String commentUid, String commentsUpdatedByUser, AsyncCallback<CommentsDo> callback);

	public void createReaction(String resourceId, String reactionText, String gooruReactionId, String collectionId, String createStudyPlayerReaction, AsyncCallback<ReactionDo> callback);

	public void getResourceReaction(String gooruOid, String gooruUid,AsyncCallback<ArrayList<ReactionDo>> callback);

	public void generatePdf(String innerHtml,String completedDateTime,AsyncCallback<String> callback);

	void sendEmailWithPdf(String toAddress, String fromAddress, String cfm, String subject, String message, String Url, String FileName, AsyncCallback<String> callback);

	public void getFeaturedContent(AsyncCallback<ArrayList<FeaturedContentDo>> callback);

	public void deleteReaction(String gooruReactionId,AsyncCallback<Void> callback);

	/**
	 * Creates the star rating by calling an API.
	 *
	 * @param associateGooruOid {@link String}
	 * @param starRatingValue {@link Integer} Input given by the user i.e score out of 5
	 * @param callback {@link AsyncCallback}  The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public void createStarRatings(String associateGooruOid,int starRatingValue,String userReview,AsyncCallback<StarRatingsDo> callback);

	/**
	 * Gets the resource star ratings.
	 *
	 * @param gooruOid {@link String}
	 * @param gooruUid {@link String}
	 * @param callback {@link AsyncCallback} The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public void getResourceStarRatings(String gooruOid, String gooruUid,AsyncCallback<StarRatingsDo> callback);

	/**
	 * Gets content Star ratings.
	 * @param gooruOid {@link String}
	 * @param callback {@link AsyncCallback} The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public void getContentStarRatings(String gooruOid,AsyncCallback<ContentStarRatingsDo> callback);

	/**
	 * Updates the resource ratings.
	 * @param gooruOid {@link String}
	 * @param score {@link Integer}
	 * @param callback {@link AsyncCallback} The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public void updateResourceStarRatings(String gooruOid,int score,AsyncCallback<ArrayList<StarRatingsDo>>callback);

	/**
	 * Gets the user star ratings.
	 * @param gooruUid {@link String}
	 * @param callback {@link AsyncCallback} The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public void getUserStarRatings(String gooruUid,AsyncCallback<UserStarRatingsDo> callback);

	/**
	 * Gets the resource star ratings and reviews.
	 * @param gooruOid {@link String}
	 * @param gooruUid {@link String}
	 * @param callback {@link AsyncCallback} The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public void getResourceRatingWithReviews(String gooruOid, String gooruUid,int offSet,AsyncCallback<ArrayList<StarRatingsDo>> callback);

	/**
	 * Updates resource star reviews.
	 * @param gooruOid {@link String}
	 * @param gooruUid {@link String}
	 * @param callback {@link AsyncCallback} The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public void updateResourceStarReviews(String deleteRatingGooruOid,Integer score, String userReview,AsyncCallback<ArrayList<StarRatingsDo>> callback);

	public void deleteRating(String deleteRatingGooruOid,AsyncCallback<Void> callback);

	public void getGoogleDriveFileStatusCode(String fileUrl,AsyncCallback<Integer> callback);

	public void getYoutubeFeedCallback(String utubeId, AsyncCallback<Map<String,String>> callback);

	public void getInsightsCollectionSummary(String collectionId,String classpageId,String sessionId,String userId,AsyncCallback<InsightsCollectionDo> callback);

	public void getNextCollectionFromToc(String folderId,String collectionItemId,AsyncCallback<FolderWhatsNextCollectionDo> callback);

	public void updateSessionActivityItem(String gooruOid, String status,String updateSessionId,AsyncCallback<Void> callback);

	public void getUpdateSessionActivityItemForRatReac(int emoticRatingNumber,String gooruOid, String isRatingsReactions,String sessionId, AsyncCallback<Void> callback);

	/**
	 *
	 * @function getPreviousSessionDataForUser
	 *
	 * @created_date : 10-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param gooruUid
	 * @parm(s) : @param classGooruId
	 * @parm(s) : @param lessonGooruId
	 * @parm(s) : @param unitGooruId
	 * @parm(s) : @param courseGooruId
	 * @parm(s) : @param assessmentId
	 * @parm(s) : @param callback
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void getPreviousSessionDataForUser(String gooruUid, String classGooruId,
			String lessonGooruId, String unitGooruId, String courseGooruId,
			String assessmentId,
			AsyncCallback<List<UserPlayedSessionDo>> callback);



}
