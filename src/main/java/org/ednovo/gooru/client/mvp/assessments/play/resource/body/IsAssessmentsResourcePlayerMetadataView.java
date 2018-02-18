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

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ReactionDo;
import org.ednovo.gooru.application.shared.model.content.ResourceTagsDo;
import org.ednovo.gooru.application.shared.model.content.StarRatingsDo;
import org.ednovo.gooru.client.htmltags.SectionTag;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public interface IsAssessmentsResourcePlayerMetadataView extends IsViewWithHandlers<AssessmentsResourcePlayerMetadataUiHandlers>{
	public void showResourceWidget(CollectionItemDo collectionItemDo,PlaceRequest nextResoruceRequest,PlaceRequest previousResourceRequest);
	public void showResourceWidget(CollectionItemDo collectionItemDo);
	public void showResourceWidget(PlaceRequest previousResourceRequest);
	public SectionTag getResourceWidgetContainer();
	public SectionTag getCollectionContainer();
	public void setReaction(ReactionDo reactionDo, String gooruReactionId);

	public void setDefaultReaction();
	public void setUserStarRatings(StarRatingsDo result, boolean showThankYouToolTip);
	public void setDefaultUserStarRatings();
	public void removeRatingContainer(boolean flag);
	public void postReview(String assocGooruOId, String userReview, Integer score, boolean isUpdate);
	public void hideThankYouPopUp();
	public void setRatingMetaData(String assocGooruOid, Integer score,String review, double average, Integer count);
	public void displaySuccessPopup();
	public void updateRatingOnSearch(StarRatingsDo starRatingsDo);
	public void clearAllStarsForAnnonymous();
	public void childLoggedIn(boolean isChild);
	public void deleteRatingsValue();
	public void setGoogleDriveFileStatusCode(Integer statusCode);
	public void displayResourceTags(List<ResourceTagsDo> resourceTagsList);
	public void checkYoutubeAccessControls(Map<String, String> result);
	public Button getNarrationButton();
	public Button getFullScreenButton();
	public void clearMarginTop();
	public void setMarginTop();
	public void setPreviousRating(double previousRating);
	public double getPreviousRating();
	public void setFullScreen(boolean isFullScreen,FlowPanel pnlFullScreenNarration);
	/**
	 *
	 * @function navigateToNextResource
	 *
	 * @created_date : 09-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param resourceRequest
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void navigateToNextResource(PlaceRequest resourceRequest);
	public void displaySelectedStandards(List<Map<String, String>> standListArray);
}
