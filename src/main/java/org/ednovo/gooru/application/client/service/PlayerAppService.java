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

import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
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
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwt-service/playerService")
public interface PlayerAppService extends BaseService {


	public CollectionDo getSimpleCollectionDetils(String simpleCollectionId,String resourceId,String tabView,String rootNodeId) throws GwtException, ServerDownException;

	public CollectionDo getSimpleCollectionDetils(String apiKey,String simpleCollectionId,String resourceId,String tabView,String rootNodeId) throws GwtException, ServerDownException;

	public ResoruceCollectionDo getResourceCollectionsList(String resourceGooruOid,String pageNum,String pageSize) throws GwtException, ServerDownException;

	public CollectionItemDo getResourceInfoDetails(String apiKey,String resourceId,String tabView) throws GwtException, ServerDownException;

	public CollectionItemDo getResourceObj(String resourceId) throws GwtException, ServerDownException;



	/**
	 * Get shorten collection url
	 * @param contentGooruOid of collection
	 * @param params set values url,type
	 * @return shrotenUrl, rawUrl
	 * @throws GwtException
	 */
	Map<String, String> getShortenShareUrl(String contentGooruOid) throws GwtException, ServerDownException;

	public String updateViewCount(String gooruid,String viewCount,String resourceType) throws GwtException, ServerDownException;


	public String startActivityPlayerLog(String activityEventId,String activityParentEventId,String eventName,String gooruOid,String resourceGooruOid,String context,String userAgent) throws GwtException, ServerDownException;

	public String stopActivityPlayerLog(String activityEventId,String activityParentEventId,String eventName,String gooruOid,String resourceGooruOid,String context,String userAgent) throws GwtException, ServerDownException;

	public String createSessionTracker(String collectionGooruOid,String sessionId,String mode) throws GwtException, ServerDownException;

	public String updateSessionInCollection(String sessionTrackerId) throws GwtException, ServerDownException;

	public String createSessionItemInCollection(String sessionTrackerId,String collectionItemId, String resourceGooruOid, String questionType, String status) throws GwtException, ServerDownException;

	public String createSessionItemAttemptTry(String contentGooruOid,String sessionTrackerId,String sessionItemTrackerId, Integer answerId, String attemptResult) throws GwtException, ServerDownException;

	public String createSessionItemAttemptTryForOe(String contentGooruOid,String sessionTrackerId,String sessionItemTrackerId, String answerId,String attemptStatus,String attemptAnswerResult) throws GwtException, ServerDownException;

	public String sendEmail(String fromEmail,String toEmail,String copyEmail,String subject,String message) throws GwtException, ServerDownException;

	public boolean getUserProfileVisibility(String gooruUid) throws GwtException, ServerDownException;

	public String copyCollection(String collectionId,String collectionTile) throws GwtException, ServerDownException;

	public String copyCollectionItem(String collectionItemId,String collectionId) throws GwtException, ServerDownException;

	public ArrayList<CollectionItemsList> getWorkspaceCollections(String userId,String offset,String limit)  throws GwtException, ServerDownException;

	public ArrayList<ContentReportDo> getContentReport(String associatedGooruOid,String gooruUid) throws GwtException, ServerDownException;

	public ContentReportDo createContentReport(String associatedGooruOid,String freeText,ArrayList<String> contentReportList,String deleteContentReportGooruOids) throws GwtException, ServerDownException;

	public void deleteContentReport(String associatedGooruOid) throws GwtException, ServerDownException;

	public CommentsDo createCommentForCollection(String gooruCollectionId, String userCommentsEntered) throws GwtException, ServerDownException;

	public CommentsListDo getCollectionCommentsList(String gooruOid,String offset, String pageLimit) throws GwtException, ServerDownException;

	public void deleteCollectionCommentbyCommentUid(String commentUid) throws GwtException, ServerDownException;

	public CommentsDo updateCollectionCommentbyCommentUid(String commentUid, String commentsUpdatedByUser) throws GwtException, ServerDownException;

	public ReactionDo createReaction(String resourceId, String reactionText,String gooruReactionId,String collectionId, String createStudyPlayerReaction) throws GwtException, ServerDownException;

	public ArrayList<ReactionDo> getResourceReaction(String resourceId, String gooruUid) throws GwtException, ServerDownException;

	public String generatePdf(String innerHtml,String completedDateTime) throws GwtException, ServerDownException;

	public String sendEmailWithPdf(String toAddress, String fromAddress, String cfm, String subject, String message, String Url, String FileName) throws GwtException, ServerDownException;

	public ArrayList<FeaturedContentDo> getFeaturedContent() throws GwtException, ServerDownException;

	public void deleteReaction(String gooruReactionId) throws GwtException, ServerDownException;

	/**
	 * Creates the star rating by calling an API.
	 *
	 * @param associateGooruOid {@link String}
	 * @param starRatingValue {@link Integer} Input given by the user i.e score out of 5
	 * @param callback {@link AsyncCallback}  The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public StarRatingsDo createStarRatings(String associateGooruOid,int starRatingValue,String userReview) throws GwtException, ServerDownException;

	/**
	 * Gets the resource star ratings.
	 *
	 * @param gooruOid {@link String}
	 * @param gooruUid {@link String}
	 * @param callback {@link AsyncCallback} The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public StarRatingsDo getResourceStarRatings(String gooruOid, String gooruUid) throws GwtException, ServerDownException;


	/**
	 * Gets content Star ratings.
	 * @param gooruOid {@link String}
	 * @param callback {@link AsyncCallback} The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public ContentStarRatingsDo getContentStarRatings(String gooruOid) throws GwtException, ServerDownException;

	/**
	 * Updates the resource ratings.
	 * @param gooruOid {@link String}
	 * @param score {@link Integer}
	 * @param callback {@link AsyncCallback} The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public ArrayList<StarRatingsDo> updateResourceStarRatings(String gooruOid, int score) throws GwtException, ServerDownException;


	/**
	 * Gets the user star ratings.
	 * @param gooruUid {@link String}
	 * @param callback {@link AsyncCallback} The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public UserStarRatingsDo getUserStarRatings(String gooruOid) throws GwtException, ServerDownException;

	/**
	 * Gets the resource star ratings and reviews.
	 * @param gooruOid {@link String}
	 * @param gooruUid {@link String}
	 * @param callback {@link AsyncCallback} The asynchronous method always takes an AsyncCallback<T> as its last parameter, where T is the return type of the correlated synchronous method.
	 */
	public ArrayList<StarRatingsDo> getResourceRatingWithReviews(String resourceId, String gooruUid,int offSet) throws GwtException, ServerDownException;

	public ArrayList<StarRatingsDo> updateResourceStarReviews(String deleteRatingGooruOid,Integer score, String userReview) throws GwtException, ServerDownException;

	public void deleteRating(String deleteRatingGooruOid) throws GwtException, ServerDownException;

	public int getGoogleDriveFileStatusCode(String fileUrl) throws GwtException, ServerDownException;

	public Map<String,String> getYoutubeFeedCallback(String utubeId) throws GwtException, ServerDownException;

	public InsightsCollectionDo getInsightsCollectionSummary(String collectionId,String classpageId,String sessionId,String userId) throws GwtException, ServerDownException;

	public FolderWhatsNextCollectionDo getNextCollectionFromToc(String folderId,String collectionItemId) throws GwtException, ServerDownException;

	public void updateSessionActivityItem(String gooruOid, String status,String updateSessionId) throws GwtException, ServerDownException;

	public void getUpdateSessionActivityItemForRatReac(int emoticRatingNumber,String gooruOid, String isRatingsReactions,String sessionId) throws GwtException, ServerDownException;

	public List<UserPlayedSessionDo> getPreviousSessionDataForUser(String gooruUid,
			String classGooruId, String lessonGooruId, String unitGooruId,
			String courseGooruId, String assessmentId)throws GwtException, ServerDownException;


}
