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
package org.ednovo.gooru.client.mvp.rating;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.StarRatingsDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.mvp.rating.events.DeletePlayerStarRatingsEvent;
import org.ednovo.gooru.client.mvp.rating.events.DeletePlayerStarReviewEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateRatingOnDeleteEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateRatingsGraphEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateUserStarReviewEvent;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class RatingUserWidgetView extends Composite {

	@UiField HTMLEventPanel reviewContainer;
	
	@UiField Label userName, timeStamp, review,mouseOverStarValue,starValue,mandatoryDescLblForSwareWords,errorLbl;
	
	@UiField Button editReview,editReviewBtn,cancelReviewBtn,deleteReview;
	
	@UiField HTMLPanel editReviewTextareaContainer,editReviewLabelContainer;
	
	@UiField InlineHTML starOne,starTwo,starThree,starFour,starFive;
	
	@UiField TextArea editReviewText;
	
	@UiField InlineHTML userratingOne,userratingTwo,userratingThree,userratingFour,userratingFive;
	
	/*@UiField RatingAndReviewStyleBundle style;*/

	
	private static final String DATE_FORMAT="MMMM dd, yyyy";
	
	private static final String ONE_STAR="oneStar";
	private static final String TWO_STAR="twoStar";
	private static final String THREE_STAR="threeStar";
	private static final String FOUR_STAR="fourStar";
	private static final String FIVE_STAR="fiveStar";
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);
	
	private StarRatingsDo starRatingsDo;
	private static final String POOR = i18n.GL1846();
	private static final String FAIR=i18n.GL1845();
	private static final String GOOD=i18n.GL1844();
	private static final String VERY_GOOD=i18n.GL1843();
	private static final String EXCELLENT=i18n.GL1842();
	
	int currentRating=0,clickedRating;
	private static final String FILLED_BLUE = "filled filledBlue";
	private static final String CONTENT_ADMIN_ROLE = "Content_Admin";
	private static RatingUserWidgetViewUiBinder uiBinder = GWT
			.create(RatingUserWidgetViewUiBinder.class);

	interface RatingUserWidgetViewUiBinder extends
			UiBinder<Widget, RatingUserWidgetView> {
	}

	public RatingUserWidgetView(StarRatingsDo starRatingsDo,String createrName) {
		initWidget(uiBinder.createAndBindUi(this));
		setData(starRatingsDo,createrName);
		this.starRatingsDo = starRatingsDo;
		deleteReview.setVisible(false);
		editReviewText.addKeyUpHandler(new KeyUpHandler(){
			@Override
			public void onKeyUp(KeyUpEvent event) {
				editReviewText.getElement().setAttribute("style", "border-color: rgb(169, 169, 169);");
				mandatoryDescLblForSwareWords.setVisible(false);
			}
		});
	}
	
	public void setData(final StarRatingsDo starRatingsDo,final String createrName) {
		reviewContainer.getElement().setId("epnlReviewContainer");
		userName.getElement().setId("lblUserName");
		editReviewLabelContainer.getElement().setId("pnlEditReviewLabelContainer");
		starOne.getElement().setId("divStarOne");
		starTwo.getElement().setId("divStarTwo");
		starThree.getElement().setId("divStarThree");
		starFour.getElement().setId("divStarFour");
		starFive.getElement().setId("divStarFive");
		editReviewTextareaContainer.getElement().setId("pnlEditReviewTextareaContainer");
		userratingOne.getElement().setId("divUserratingOne");
		userratingTwo.getElement().setId("divUserratingTwo");
		userratingThree.getElement().setId("divUserratingThree");
		userratingFour.getElement().setId("divUserratingFour");
		userratingFive.getElement().setId("divUserratingFive");
		starValue.getElement().setId("lblStarValue");
		mouseOverStarValue.getElement().setId("lblMouseOverStarValue");
		mandatoryDescLblForSwareWords.getElement().setId("lblMandatoryDescLblForSwareWords");
		errorLbl.getElement().setId("lblErrorLbl");

		editReviewText.getElement().setAttribute("maxlength", "500");
		StringUtil.setAttributes(editReviewText, true);
		editReview.setText(i18n.GL1860());
		editReview.getElement().setId("btnEditReview");
		editReview.getElement().setAttribute("alt",i18n.GL1860());
		editReview.getElement().setAttribute("title",i18n.GL1860());
		
		editReviewBtn.setText(i18n.GL0141());
		editReviewBtn.getElement().setId("btnEditReviewBtn");
		editReviewBtn.getElement().setAttribute("alt",i18n.GL0141());
		editReviewBtn.getElement().setAttribute("title",i18n.GL0141());
		
		cancelReviewBtn.setText(i18n.GL0142());
		cancelReviewBtn.getElement().setId("btnCancelReviewBtn");
		cancelReviewBtn.getElement().setAttribute("alt",i18n.GL0142());
		cancelReviewBtn.getElement().setAttribute("title",i18n.GL0142());
		
		String commentTime = getCreatedTime(Long.toString(starRatingsDo.getCreatedDate())); 
		long lastModifiedOn = starRatingsDo.getLastModifiedOn();
		timeStamp.setText(commentTime +""+ (lastModifiedOn > 0 ? " " + i18n.GL_GRR_Hyphen() + " " + i18n.GL1434() : ""));
		timeStamp.getElement().setId("lblTimeStamp");
		timeStamp.getElement().setAttribute("alt",commentTime +""+ (lastModifiedOn > 0 ? " " + i18n.GL_GRR_Hyphen() + " " + i18n.GL1434() : ""));
		timeStamp.getElement().setAttribute("title",commentTime +""+ (lastModifiedOn > 0 ? " " + i18n.GL_GRR_Hyphen() + " " + i18n.GL1434() : ""));
		
		deleteReview.setText(i18n.GL1861());
		deleteReview.getElement().setId("btnDeleteReview");
		deleteReview.getElement().setAttribute("alt",i18n.GL1861());
		deleteReview.getElement().setAttribute("title",i18n.GL1861());
		
		review.setText(starRatingsDo.getFreeText());
		review.getElement().setId("lblReview");
		review.getElement().setAttribute("alt",starRatingsDo.getFreeText());
		review.getElement().setAttribute("title",starRatingsDo.getFreeText());
		
		editReviewText.setText(starRatingsDo.getFreeText());
		editReviewText.getElement().setId("tatEditReviewText");
		editReviewText.getElement().setAttribute("alt",starRatingsDo.getFreeText());
		editReviewText.getElement().setAttribute("title",starRatingsDo.getFreeText());
		
		editReview.setVisible(false);
		deleteReview.addStyleName("deleteButtonAlign");
		
		editReviewTextareaContainer.setVisible(false);
		if(starRatingsDo.getCreator().getUsername().equals(AppClientFactory.getLoggedInUser().getUsername())){
			reviewContainer.setVisible(true);
			editReviewLabelContainer.setVisible(true);
			userName.setText(starRatingsDo.getCreator().getUsername());
			userName.getElement().setAttribute("alt",starRatingsDo.getCreator().getUsername());
			userName.getElement().setAttribute("title",starRatingsDo.getCreator().getUsername());
			clearAllStarsReadOnly();
			this.currentRating = starRatingsDo.getScore();
			if(starRatingsDo.getScore() == 1)
			{
				setStarRatings(starRatingsDo);
			}
			else if(starRatingsDo.getScore() == 2)
			{
				setStarRatings(starRatingsDo);
			}
			else if(starRatingsDo.getScore() == 3)
			{
				setStarRatings(starRatingsDo);
			}
			else if(starRatingsDo.getScore() == 4)
			{
				setStarRatings(starRatingsDo);
			}
			else if(starRatingsDo.getScore() == 5)
			{
				setStarRatings(starRatingsDo);
			}
			
		  if(starRatingsDo.getFreeText()!=null && !starRatingsDo.getFreeText().equals("")){
			reviewContainer.setVisible(true);
			editReviewLabelContainer.setVisible(true);
		  }else{
			userName.setText(starRatingsDo.getCreator().getUsername());
			userName.getElement().setAttribute("alt",starRatingsDo.getCreator().getUsername());
			userName.getElement().setAttribute("title",starRatingsDo.getCreator().getUsername());
		}
			
		}else{
			userName.setText(starRatingsDo.getCreator().getUsername());
			userName.getElement().setAttribute("alt",starRatingsDo.getCreator().getUsername());
			userName.getElement().setAttribute("title",starRatingsDo.getCreator().getUsername());
			clearAllStarsReadOnly();
			this.currentRating = starRatingsDo.getScore();
			if(starRatingsDo.getScore() == 1)
			{
				starOne.addStyleName("filled");
				starTwo.removeStyleName("filled");
				starThree.removeStyleName("filled");
				starFour.removeStyleName("filled");
				starFive.removeStyleName("filled");
			}
			else if(starRatingsDo.getScore() == 2)
			{
				starOne.addStyleName("filled");
				starTwo.addStyleName("filled");
				starThree.removeStyleName("filled");
				starFour.removeStyleName("filled");
				starFive.removeStyleName("filled");
			}
			else if(starRatingsDo.getScore() == 3)
			{
				starOne.addStyleName("filled");
				starTwo.addStyleName("filled");
				starThree.addStyleName("filled");
				starFour.removeStyleName("filled");
				starFive.removeStyleName("filled");
			}
			else if(starRatingsDo.getScore() == 4)
			{
				starOne.addStyleName("filled");
				starTwo.addStyleName("filled");
				starThree.addStyleName("filled");
				starFour.addStyleName("filled");
				starFive.removeStyleName("filled");
			}
			else if(starRatingsDo.getScore() == 5)
			{
				starOne.addStyleName("filled");
				starTwo.addStyleName("filled");
				starThree.addStyleName("filled");
				starFour.addStyleName("filled");
				starFive.addStyleName("filled");
			}
			if(starRatingsDo.getFreeText()!=null && !starRatingsDo.getFreeText().equals("")){
				reviewContainer.setVisible(true);
				editReviewLabelContainer.setVisible(true);
			}
		}
		
		if(starRatingsDo.getCreator().getUsername().equals(AppClientFactory.getLoggedInUser().getUsername())) {
			editReview.addStyleName("editReview");
			editReviewBtn.removeStyleName("editReview");
			cancelReviewBtn.removeStyleName("editReview");
			userName.setText(i18n.GL1850());
			userName.getElement().setAttribute("alt",i18n.GL1850());
			userName.getElement().setAttribute("title",i18n.GL1850());
			deleteReview.removeStyleName("editReview");
		} else {
			editReview.removeStyleName("editReview");
			deleteReview.removeStyleName("editReview");
			
		}
		reviewContainer.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(starRatingsDo.getCreator().getUsername().equals(AppClientFactory.getLoggedInUser().getUsername())) {
					editReview.setVisible(true);
					deleteReview.setVisible(true); 
				}
				
				if(!AppClientFactory.isAnonymous() && AppClientFactory.getLoggedInUser().getUserRoleSetString().contains(CONTENT_ADMIN_ROLE)){
					deleteReview.setVisible(true);     
				}
			}
		});
		reviewContainer.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				editReview.setVisible(false);
				deleteReview.setVisible(false);
				
			}
		});
		
	}
	
	private void setStarRatings(StarRatingsDo starRatingsDo) {
		if(starRatingsDo.getScore()==1){
			clearAllStarsReadOnly();
			if(starRatingsDo.getCreator().getUsername().equals(AppClientFactory.getLoggedInUser().getUsername())){
				starOne.addStyleName("filled");
				starOne.getElement().addClassName(FILLED_BLUE);
				starTwo.removeStyleName("filled");
				starThree.removeStyleName("filled");
				starFour.removeStyleName("filled");
				starFive.removeStyleName("filled");
				starTwo.removeStyleName(FILLED_BLUE);
				starThree.removeStyleName(FILLED_BLUE);
				starFour.removeStyleName(FILLED_BLUE);
				starFive.removeStyleName(FILLED_BLUE);
			}else{
				starOne.addStyleName("filled");
				starTwo.removeStyleName("filled");
				starThree.removeStyleName("filled");
				starFour.removeStyleName("filled");
				starFive.removeStyleName("filled");
			}
			
		}else if(starRatingsDo.getScore()==2){
			clearAllStarsReadOnly();
			if(starRatingsDo.getCreator().getUsername().equals(AppClientFactory.getLoggedInUser().getUsername())){
				starOne.addStyleName("filled");
				starTwo.addStyleName("filled");
				starOne.getElement().addClassName(FILLED_BLUE);
				starTwo.getElement().addClassName(FILLED_BLUE);
				starThree.removeStyleName("filled");
				starFour.removeStyleName("filled");
				starFive.removeStyleName("filled");
				starThree.removeStyleName(FILLED_BLUE);
				starFour.removeStyleName(FILLED_BLUE);
				starFive.removeStyleName(FILLED_BLUE);
			}else{
				starOne.addStyleName("filled");
				starTwo.addStyleName("filled");
				starThree.removeStyleName("filled");
				starFour.removeStyleName("filled");
				starFive.removeStyleName("filled");
			}
			
		}else if(starRatingsDo.getScore()==3){
			clearAllStarsReadOnly();
			if(starRatingsDo.getCreator().getUsername().equals(AppClientFactory.getLoggedInUser().getUsername())){
				starOne.addStyleName("filled");
				starTwo.addStyleName("filled");
				starThree.addStyleName("filled");
				starOne.getElement().addClassName(FILLED_BLUE);
				starTwo.getElement().addClassName(FILLED_BLUE);
				starThree.getElement().addClassName(FILLED_BLUE);
				starFour.removeStyleName("filled");
				starFive.removeStyleName("filled");
				starFour.removeStyleName(FILLED_BLUE);
				starFive.removeStyleName(FILLED_BLUE);
			}else{
				starOne.addStyleName("filled");
				starTwo.addStyleName("filled");
				starThree.addStyleName("filled");
				starFour.removeStyleName("filled");
				starFive.removeStyleName("filled");
			}
			
		} else if(starRatingsDo.getScore()==4){
			clearAllStarsReadOnly();
			if(starRatingsDo.getCreator().getUsername().equals(AppClientFactory.getLoggedInUser().getUsername())){
				starOne.addStyleName("filled");
				starTwo.addStyleName("filled");
				starThree.addStyleName("filled");
				starFour.addStyleName("filled");
				starOne.getElement().addClassName(FILLED_BLUE);
				starTwo.getElement().addClassName(FILLED_BLUE);
				starThree.getElement().addClassName(FILLED_BLUE);
				starFour.getElement().addClassName(FILLED_BLUE);
				starFive.removeStyleName("filled");
				starFive.removeStyleName(FILLED_BLUE);
			}else{
				starOne.addStyleName("filled");
				starTwo.addStyleName("filled");
				starThree.addStyleName("filled");
				starFour.addStyleName("filled");
				starFive.removeStyleName("filled");
			}
			
		}else if(starRatingsDo.getScore()==5){
			clearAllStarsReadOnly();
			if(starRatingsDo.getCreator().getUsername().equals(AppClientFactory.getLoggedInUser().getUsername())){
				starOne.addStyleName("filled");
				starTwo.addStyleName("filled");
				starThree.addStyleName("filled");
				starFour.addStyleName("filled");
				starFive.addStyleName("filled");
				starOne.getElement().addClassName(FILLED_BLUE);
				starTwo.getElement().addClassName(FILLED_BLUE);
				starThree.getElement().addClassName(FILLED_BLUE);
				starFour.getElement().addClassName(FILLED_BLUE);
				starFive.getElement().addClassName(FILLED_BLUE);
			}else{
				starOne.addStyleName("filled");
				starTwo.addStyleName("filled");
				starThree.addStyleName("filled");
				starFour.addStyleName("filled");
				starFive.addStyleName("filled");
			}
		}
	}


	@UiHandler("editReview")
	public void editReview(ClickEvent event) {
		editReviewTextareaContainer.setVisible(true);
		editReviewLabelContainer.setVisible(false);
		if(currentRating==1){
			userratingOne.getElement().addClassName(FILLED_BLUE);
			userratingTwo.removeStyleName(FILLED_BLUE);
			userratingThree.removeStyleName(FILLED_BLUE);
			userratingFour.removeStyleName(FILLED_BLUE);
			userratingFive.removeStyleName(FILLED_BLUE);
		}else if(currentRating==2){
			userratingOne.getElement().addClassName(FILLED_BLUE);
			userratingTwo.getElement().addClassName(FILLED_BLUE);
			userratingThree.removeStyleName(FILLED_BLUE);
			userratingFour.removeStyleName(FILLED_BLUE);
			userratingFive.removeStyleName(FILLED_BLUE);
		}else if(currentRating==3){
			userratingOne.getElement().addClassName(FILLED_BLUE);
			userratingTwo.getElement().addClassName(FILLED_BLUE);
			userratingThree.getElement().addClassName(FILLED_BLUE);
			userratingFour.removeStyleName(FILLED_BLUE);
			userratingFive.removeStyleName(FILLED_BLUE);
		}else if(currentRating==4){
			userratingOne.getElement().addClassName(FILLED_BLUE);
			userratingTwo.getElement().addClassName(FILLED_BLUE);
			userratingThree.getElement().addClassName(FILLED_BLUE);
			userratingFour.getElement().addClassName(FILLED_BLUE);
			userratingFive.removeStyleName(FILLED_BLUE);
		}else if(currentRating==5){
			userratingOne.getElement().addClassName(FILLED_BLUE);
			userratingTwo.getElement().addClassName(FILLED_BLUE);
			userratingThree.getElement().addClassName(FILLED_BLUE);
			userratingFour.getElement().addClassName(FILLED_BLUE);
			userratingFive.getElement().addClassName(FILLED_BLUE);
		}
		
		userratingOne.addMouseOverHandler(new OnStarMouseOver(ONE_STAR));
		userratingTwo.addMouseOverHandler(new OnStarMouseOver(TWO_STAR));
		userratingThree.addMouseOverHandler(new OnStarMouseOver(THREE_STAR));
		userratingFour.addMouseOverHandler(new OnStarMouseOver(FOUR_STAR));
		userratingFive.addMouseOverHandler(new OnStarMouseOver(FIVE_STAR));
		
		userratingOne.addMouseOutHandler(new OnStarMouseOut(ONE_STAR));
		userratingTwo.addMouseOutHandler(new OnStarMouseOut(TWO_STAR));
		userratingThree.addMouseOutHandler(new OnStarMouseOut(THREE_STAR));
		userratingFour.addMouseOutHandler(new OnStarMouseOut(FOUR_STAR));
		userratingFive.addMouseOutHandler(new OnStarMouseOut(FIVE_STAR));
		editReviewBtn.removeStyleName("editReview");
		cancelReviewBtn.removeStyleName("editReview");
	}
	@UiHandler("editReviewText")
	public void keyRatingTextArea(KeyUpEvent event){
		String review=editReviewText.getText();
		errorLbl.setText("");
		errorLbl.getElement().setAttribute("alt","");
		errorLbl.getElement().setAttribute("title","");
		if(review.length()>0){
			errorLbl.setText("");
			errorLbl.getElement().setAttribute("alt","");
			errorLbl.getElement().setAttribute("title","");
		}
		if(review.length()==500){
			errorLbl.setText(i18n.GL0143());
			errorLbl.getElement().setAttribute("alt",i18n.GL0143());
			errorLbl.getElement().setAttribute("title",i18n.GL0143());
			errorLbl.setVisible(true);
		}else{
			errorLbl.setVisible(false);
		}
	}
	@UiHandler("editReviewBtn")
	public void editReviewForEditingReview(ClickEvent event) {
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("text", editReviewText.getText());
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(!result){
				// TODO Auto-generated method stub
				AppClientFactory.getInjector().getPlayerAppService().updateResourceStarReviews(starRatingsDo.getDeleteRatingGooruOid(),currentRating, editReviewText.getText(), new SimpleAsyncCallback<ArrayList<StarRatingsDo>>(){
					@Override
					public void onSuccess(ArrayList<StarRatingsDo> result) {
						if(result.size()>0){
							AppClientFactory.fireEvent(new UpdateUserStarReviewEvent(result));
							String commentTime = getCreatedTime(Long.toString(starRatingsDo.getCreatedDate())); 
							timeStamp.setText(commentTime +""+ (" " + i18n.GL_GRR_Hyphen() + " " + i18n.GL1434()));
							timeStamp.getElement().setAttribute("alt",commentTime +""+ (" " + i18n.GL_GRR_Hyphen() + " " + i18n.GL1434()));
							timeStamp.getElement().setAttribute("title",commentTime +""+ (" " + i18n.GL_GRR_Hyphen() + " " + i18n.GL1434()));
							editReviewTextareaContainer.setVisible(false);
							review.setText(result.get(0).getFreeText());
							review.getElement().setAttribute("alt",result.get(0).getFreeText());
							review.getElement().setAttribute("title",result.get(0).getFreeText());
							editReviewLabelContainer.setVisible(true);
							editReviewBtn.removeStyleName("editReview");
							cancelReviewBtn.removeStyleName("editReview");
							updateStars(result.get(0));
							starRatingsDo.setScore(result.get(0).getScore());
							starRatingsDo.setFreeText(result.get(0).getFreeText());
							AppClientFactory.fireEvent(new UpdateRatingsGraphEvent(starRatingsDo.getAssocGooruOid()));  
						}
					}
				}); 
			}
				SetStyleForProfanity.SetStyleForProfanityForTextArea(editReviewText, mandatoryDescLblForSwareWords, result);
	   }
			
		});
	}
	
	private void updateStars(StarRatingsDo starRatingsDo) {
		clearAllStarsReadOnly();
		// TODO Auto-generated method stub
		if(starRatingsDo.getScore() == 1)
		{
			starOne.addStyleName("filled");
			starOne.getElement().addClassName(FILLED_BLUE);
			
			starTwo.removeStyleName("filled");
			starThree.removeStyleName("filled");
			starFour.removeStyleName("filled");
			starFive.removeStyleName("filled");
			starTwo.removeStyleName(FILLED_BLUE);
			starThree.removeStyleName(FILLED_BLUE);
			starFour.removeStyleName(FILLED_BLUE);
			starFive.removeStyleName(FILLED_BLUE);
		}
		else if(starRatingsDo.getScore() == 2)
		{
			starOne.addStyleName("filled");
			starTwo.addStyleName("filled");
			starOne.getElement().addClassName(FILLED_BLUE);
			starTwo.getElement().addClassName(FILLED_BLUE);
			
			starThree.removeStyleName("filled");
			starFour.removeStyleName("filled");
			starFive.removeStyleName("filled");
			starThree.removeStyleName(FILLED_BLUE);
			starFour.removeStyleName(FILLED_BLUE);
			starFive.removeStyleName(FILLED_BLUE);
		}
		else if(starRatingsDo.getScore() == 3)
		{
			starOne.addStyleName("filled");
			starTwo.addStyleName("filled");
			starThree.addStyleName("filled");
			starOne.getElement().addClassName(FILLED_BLUE);
			starTwo.getElement().addClassName(FILLED_BLUE);
			starThree.getElement().addClassName(FILLED_BLUE);
			
			starFour.removeStyleName("filled");
			starFive.removeStyleName("filled");
			starFour.removeStyleName(FILLED_BLUE);
			starFive.removeStyleName(FILLED_BLUE);
			
		}
		else if(starRatingsDo.getScore() == 4)
		{
			starOne.addStyleName("filled");
			starTwo.addStyleName("filled");
			starThree.addStyleName("filled");
			starFour.addStyleName("filled");
			starOne.getElement().addClassName(FILLED_BLUE);
			starTwo.getElement().addClassName(FILLED_BLUE);
			starThree.getElement().addClassName(FILLED_BLUE);
			starFour.getElement().addClassName(FILLED_BLUE);
			
			starFive.removeStyleName("filled");
			starFive.removeStyleName(FILLED_BLUE);
			
		}
		else if(starRatingsDo.getScore() == 5)
		{
			starOne.addStyleName("filled");
			starTwo.addStyleName("filled");
			starThree.addStyleName("filled");
			starFour.addStyleName("filled");
			starFive.addStyleName("filled");
			
			starOne.getElement().addClassName(FILLED_BLUE);
			starTwo.getElement().addClassName(FILLED_BLUE);
			starThree.getElement().addClassName(FILLED_BLUE);
			starFour.getElement().addClassName(FILLED_BLUE);
			starFive.getElement().addClassName(FILLED_BLUE);
		}
	}
	
	
	@UiHandler("userratingOne")
	public void userratingOne(ClickEvent event) {
		this.currentRating =1;
		userratingOne.addStyleName(FILLED_BLUE);
		userratingTwo.removeStyleName(FILLED_BLUE);
		userratingThree.removeStyleName(FILLED_BLUE);
		userratingFour.removeStyleName(FILLED_BLUE);
		userratingFive.removeStyleName(FILLED_BLUE);
		
	}

	@UiHandler("userratingTwo")
	public void userratingTwo(ClickEvent event) {
		this.currentRating =2;
		userratingOne.addStyleName(FILLED_BLUE);
		userratingTwo.addStyleName(FILLED_BLUE);
		userratingThree.removeStyleName(FILLED_BLUE);
		userratingFour.removeStyleName(FILLED_BLUE);
		userratingFive.removeStyleName(FILLED_BLUE);
	
	}
	@UiHandler("userratingThree")
	public void userratingThree(ClickEvent event) {
		this.currentRating =3;
		userratingOne.addStyleName(FILLED_BLUE);
		userratingTwo.addStyleName(FILLED_BLUE);
		userratingThree.addStyleName(FILLED_BLUE);
		userratingFour.removeStyleName(FILLED_BLUE);
		userratingFive.removeStyleName(FILLED_BLUE);
	}
	@UiHandler("userratingFour")
	public void userratingFour(ClickEvent event) {
		this.currentRating =4;
		userratingOne.addStyleName(FILLED_BLUE);
		userratingTwo.addStyleName(FILLED_BLUE);
		userratingThree.addStyleName(FILLED_BLUE);
		userratingFour.addStyleName(FILLED_BLUE);
		userratingFive.removeStyleName(FILLED_BLUE);
	}
	@UiHandler("userratingFive")
	public void userratingFive(ClickEvent event) {
		this.currentRating =5;
		userratingOne.addStyleName(FILLED_BLUE);
		userratingTwo.addStyleName(FILLED_BLUE);
		userratingThree.addStyleName(FILLED_BLUE);
		userratingFour.addStyleName(FILLED_BLUE);
		userratingFive.addStyleName(FILLED_BLUE);
	}
	@UiHandler("cancelReviewBtn")
	public void cancelReview(ClickEvent event) {
		this.currentRating =starRatingsDo.getScore();
		editReviewText.setText(starRatingsDo.getFreeText());
		editReviewText.getElement().setAttribute("alt",starRatingsDo.getFreeText());
		editReviewText.getElement().setAttribute("title",starRatingsDo.getFreeText());
		
		editReviewTextareaContainer.setVisible(false);
		editReviewLabelContainer.setVisible(true);
		editReviewBtn.removeStyleName("editReview");
		cancelReviewBtn.removeStyleName("editReview");
	}

	@UiHandler("deleteReview")
	public void onClickDeleteReview(ClickEvent event){

		AppClientFactory.getInjector().getPlayerAppService().deleteRating(starRatingsDo.getDeleteRatingGooruOid(), new SimpleAsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				reviewContainer.clear();
				reviewContainer.addStyleName("deletePanel");
				if(!starRatingsDo.getFreeText().equals("") && starRatingsDo.getFreeText() != null){
					AppClientFactory.fireEvent(new DeletePlayerStarReviewEvent(starRatingsDo.getAssocGooruOid()));
				}else{
					AppClientFactory.fireEvent(new DeletePlayerStarRatingsEvent());
				}
				
				final HTMLPanel deletePanel = new HTMLPanel("");
				Label deleteMsg = new Label();
				deleteMsg.setText(i18n.GL1853());
				deleteMsg.getElement().setAttribute("alt",i18n.GL1853());
				deleteMsg.getElement().setAttribute("title",i18n.GL1853());
				deleteMsg.setStyleName("lbldeleteMsg");
				deletePanel.add(deleteMsg);
				reviewContainer.add(deletePanel);
				new FadeInAndOut(deletePanel.getElement(), 1000);
				Timer timer = new Timer()
		        {
		            @Override
		            public void run()
		            {
		            	int deleteIndex = reviewContainer.getWidgetIndex(deletePanel);
		            	reviewContainer.remove(deleteIndex);
		            	reviewContainer.setVisible(false);
		            	AppClientFactory.fireEvent(new UpdateRatingOnDeleteEvent(true)); 
		            	AppClientFactory.fireEvent(new UpdateRatingsGraphEvent(starRatingsDo.getAssocGooruOid())); 
					
		            }
		        };
		        timer.schedule(1000);
			}			
		});
}
	
	private String getCreatedTime(String commentCreatedTime) {
		String createdTime = null;
		Long currentTime = System.currentTimeMillis();
		Long commentTime = Long.parseLong(commentCreatedTime);
		Long elapsedTime = (currentTime - commentTime);
		int seconds = (int) (elapsedTime / 1000) % 60 ;
		int minutes = (int) ((elapsedTime / (1000*60)) % 60);
		int hours   = (int) ((elapsedTime / (1000*60*60)) % 24);
		int days = (int) (elapsedTime / (1000*60*60*24));
		Date currentDate = new Date(commentTime);
		DateTimeFormat fmt = DateTimeFormat.getFormat (DATE_FORMAT);
		if(days>6){
			createdTime = fmt.format (currentDate);
		}
		else if(days>0&&days<=6) {
			createdTime = days + getTimePrefix(days," "+i18n.GL0562(), i18n.GL0579(), i18n.GL0580());
		} else if(hours>0&&hours<24) {
			createdTime = hours + getTimePrefix(hours," "+i18n.GL0563(), i18n.GL1435(), i18n.GL1436());
		} else if(minutes>0&&minutes<60) {
			createdTime = minutes + getTimePrefix(minutes," "+i18n.GL0564(), i18n.GL1437(), i18n.GL1438());
		} else if(seconds<=60) {
			createdTime = i18n.GL0561();
		}
		return createdTime;

	}
	
	public class OnStarMouseOver implements MouseOverHandler{
		private String starScore="";
		/**
		 * Class Constructor.
		 * @param starScore {@link String}
		 */
		public OnStarMouseOver(String starScore) {
			this.starScore=starScore;
		}
		@Override
		public void onMouseOver(MouseOverEvent event) {
			clearAllStars();
			if(starScore.equalsIgnoreCase(ONE_STAR)){
				mouseOverStarValue.setText(POOR);
				mouseOverStarValue.getElement().setAttribute("alt",POOR);
				mouseOverStarValue.getElement().setAttribute("title",POOR);
				userratingOne.getElement().addClassName(FILLED_BLUE);
			}else if(starScore.equalsIgnoreCase(TWO_STAR)){
				mouseOverStarValue.setText(FAIR);
				mouseOverStarValue.getElement().setAttribute("alt",FAIR);
				mouseOverStarValue.getElement().setAttribute("title",FAIR);
				userratingOne.getElement().addClassName(FILLED_BLUE);
				userratingTwo.getElement().addClassName(FILLED_BLUE);
			}else if(starScore.equalsIgnoreCase(THREE_STAR)){
				mouseOverStarValue.setText(GOOD);
				mouseOverStarValue.getElement().setAttribute("alt",GOOD);
				mouseOverStarValue.getElement().setAttribute("title",GOOD);
				userratingOne.getElement().addClassName(FILLED_BLUE);
				userratingTwo.getElement().addClassName(FILLED_BLUE);
				userratingThree.getElement().addClassName(FILLED_BLUE);
			}else if(starScore.equalsIgnoreCase(FOUR_STAR)){
				mouseOverStarValue.setText(VERY_GOOD);
				mouseOverStarValue.getElement().setAttribute("alt",VERY_GOOD);
				mouseOverStarValue.getElement().setAttribute("title",VERY_GOOD);
				userratingOne.getElement().addClassName(FILLED_BLUE);
				userratingTwo.getElement().addClassName(FILLED_BLUE);
				userratingThree.getElement().addClassName(FILLED_BLUE);
				userratingFour.getElement().addClassName(FILLED_BLUE);
			}else if(starScore.equalsIgnoreCase(FIVE_STAR)){
				mouseOverStarValue.setText(EXCELLENT);
				mouseOverStarValue.getElement().setAttribute("alt",EXCELLENT);
				mouseOverStarValue.getElement().setAttribute("title",EXCELLENT);
				userratingOne.getElement().addClassName(FILLED_BLUE);
				userratingTwo.getElement().addClassName(FILLED_BLUE);
				userratingThree.getElement().addClassName(FILLED_BLUE);
				userratingFour.getElement().addClassName(FILLED_BLUE);
				userratingFive.getElement().addClassName(FILLED_BLUE);
			}
		}
		
	}
	
	public class OnStarMouseOut implements MouseOutHandler{
		private String starScore="";
		public OnStarMouseOut(String starScore) {
			this.starScore=starScore;
		}

		@Override
		public void onMouseOut(MouseOutEvent event) {
			clearAllStars();
			if(starScore.equalsIgnoreCase(ONE_STAR)){
				starValue.setVisible(true);
				mouseOverStarValue.setText("");
				mouseOverStarValue.getElement().setAttribute("alt","");
				mouseOverStarValue.getElement().setAttribute("title","");
			}else if(starScore.equalsIgnoreCase(TWO_STAR)){
				starValue.setVisible(true);
				mouseOverStarValue.setText("");
				mouseOverStarValue.getElement().setAttribute("alt","");
				mouseOverStarValue.getElement().setAttribute("title","");
			}else if(starScore.equalsIgnoreCase(THREE_STAR)){
				starValue.setVisible(true);
				mouseOverStarValue.setText("");
				mouseOverStarValue.getElement().setAttribute("alt","");
				mouseOverStarValue.getElement().setAttribute("title","");
			}else if(starScore.equalsIgnoreCase(FOUR_STAR)){
				starValue.setVisible(true);
				mouseOverStarValue.setText("");
				mouseOverStarValue.getElement().setAttribute("alt","");
				mouseOverStarValue.getElement().setAttribute("title","");
			}else if(starScore.equalsIgnoreCase(FIVE_STAR)){
				starValue.setVisible(true);
				mouseOverStarValue.setText("");
				mouseOverStarValue.getElement().setAttribute("alt","");
				mouseOverStarValue.getElement().setAttribute("title","");
			}
			setStarRatingValue(currentRating);
		}

		private void setStarRatingValue(int currentRating) {
			clearAllStars();
			if(currentRating==1){
				userratingOne.getElement().addClassName(FILLED_BLUE);
			}else if(currentRating==2){
				userratingOne.getElement().addClassName(FILLED_BLUE);
				userratingTwo.getElement().addClassName(FILLED_BLUE);
			}else if(currentRating==3){
				userratingOne.getElement().addClassName(FILLED_BLUE);
				userratingTwo.getElement().addClassName(FILLED_BLUE);
				userratingThree.getElement().addClassName(FILLED_BLUE);
			}else if(currentRating==4){
				userratingOne.getElement().addClassName(FILLED_BLUE);
				userratingTwo.getElement().addClassName(FILLED_BLUE);
				userratingThree.getElement().addClassName(FILLED_BLUE);
				userratingFour.getElement().addClassName(FILLED_BLUE);
			}else if(currentRating==5){
				userratingOne.getElement().addClassName(FILLED_BLUE);
				userratingTwo.getElement().addClassName(FILLED_BLUE);
				userratingThree.getElement().addClassName(FILLED_BLUE);
				userratingFour.getElement().addClassName(FILLED_BLUE);
				userratingFive.getElement().addClassName(FILLED_BLUE);
			}else{
				clearAllStars();
			}
		}
	}
	
	public void clearAllStars(){
		userratingOne.getElement().removeClassName(FILLED_BLUE);
		userratingTwo.getElement().removeClassName(FILLED_BLUE);
		userratingThree.getElement().removeClassName(FILLED_BLUE);
		userratingFour.getElement().removeClassName(FILLED_BLUE);
		userratingFive.getElement().removeClassName(FILLED_BLUE);
	}
	public void clearAllStarsReadOnly(){
		starOne.removeStyleName("filled");
		starTwo.removeStyleName("filled");
		starThree.removeStyleName("filled");
		starFour.removeStyleName("filled");
		starFive.removeStyleName("filled");
	}

	private String getTimePrefix(int count, String msg, String regex, String replacement) {
		if(count==1) {
			msg = msg.replaceAll(regex, replacement);
		}
		return msg;
	}
	
}