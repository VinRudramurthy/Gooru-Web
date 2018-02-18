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
package org.ednovo.gooru.client.uc;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.StarRatingsDo;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.Widget;

/**
* @fileName : StarRatingsUc.java
*
* @description : Creates ratings widget i.e Star Ratings
* 
* @version : 1.0
*
* @date:  April, 2013.
*
* @Author: Gooru Team.
* 
* @Reviewer: Gooru Team.
*/

public abstract class StarRatingsUc extends Composite {

	@UiField
	public SimpleCheckBox starOne,starTwo,starThree,starFour,starFive;
	
	@UiField Label starValue,mouseOverStarValue; 
	@UiField HTMLPanel starRatingsPnl;
	
	private static final String POOR="Poor";
	private static final String FAIR="Fair";
	private static final String GOOD="Good";
	private static final String VERY_GOOD="Very Good";
	private static final String EXCELLENT="Excellent";
	private static final String ONE_STAR="starOne";
	private static final String TWO_STAR="starTwo";
	private static final String THREE_STAR="starThree";
	private static final String FOUR_STAR="starFour";
	private static final String FIVE_STAR="starFive";
	

	private static StarRatingsUiBinder uiBinder = GWT.create(StarRatingsUiBinder.class);

	interface StarRatingsUiBinder extends UiBinder<Widget, StarRatingsUc> {
	}

	/**
	 * Class Constructor
	 */
	public StarRatingsUc() {
		initWidget(uiBinder.createAndBindUi(this));
		starRatingsPnl.getElement().setId("starRatingsPanel");
		starValue.setVisible(false);
		starValue.getElement().setId("lblStarValue");
		mouseOverStarValue.getElement().setId("lblMouseOverStarValue");
		starOne.getElement().setId("simpleChkStarOne");
		starTwo.getElement().setId("simpleChkStarTwo");
		starThree.getElement().setId("simpleChkStarThree");
		starFour.getElement().setId("simpleChkStarFour");
		starFive.getElement().setId("simpleChkStarFive");
		
		starOne.addMouseOverHandler(new OnStarMouseOver(ONE_STAR));
		starTwo.addMouseOverHandler(new OnStarMouseOver(TWO_STAR));
		starThree.addMouseOverHandler(new OnStarMouseOver(THREE_STAR));
		starFour.addMouseOverHandler(new OnStarMouseOver(FOUR_STAR));
		starFive.addMouseOverHandler(new OnStarMouseOver(FIVE_STAR));
		
		starOne.addMouseOutHandler(new OnStarMouseOut(ONE_STAR));
		starTwo.addMouseOutHandler(new OnStarMouseOut(TWO_STAR));
		starThree.addMouseOutHandler(new OnStarMouseOut(THREE_STAR));
		starFour.addMouseOutHandler(new OnStarMouseOut(FOUR_STAR));
		starFive.addMouseOutHandler(new OnStarMouseOut(FIVE_STAR));
		
		
	}
	
	
	/**
	 * On click of Star 1 sets the rating on view.
	 * @param clickEvent {@link ClickEvent}
	 */
	@UiHandler("starOne")
	public void onStarOneclicked(ClickEvent event){
		if(AppClientFactory.isAnonymous()){
			getDefaultRatings();
			showLoginPopupWidget();
		}else{
			if(starOne.getValue()){
				crateStarRating(ONE_STAR);
			}else{
			}
		}
		
		
	}

	/**
	 * On click of Star 2 sets the rating on view.
	 * @param clickEvent {@link ClickEvent}
	 */
	@UiHandler("starTwo")
	public void onStarTwoclicked(ClickEvent event){
		if(AppClientFactory.isAnonymous()){
			getDefaultRatings();
			showLoginPopupWidget();
		}else{
			if(starTwo.getValue()){
				crateStarRating(TWO_STAR);
			}else{
			}
		}
		
	}
	
	/**
	 * On click of Star 3 sets the rating on view.
	 * @param clickEvent {@link ClickEvent}
	 */
	@UiHandler("starThree")
	public void onStarThreeclicked(ClickEvent event){
		if(AppClientFactory.isAnonymous()){
			getDefaultRatings();
			showLoginPopupWidget();
		}else{
			if(starThree.getValue()){
				crateStarRating(THREE_STAR);
			}else{
			}
		}
	}
	
	/**
	 * On click of Star 4 sets the rating on view.
	 * @param clickEvent {@link ClickEvent}
	 */
	@UiHandler("starFour")
	public void onStarFourclicked(ClickEvent event){
		if(AppClientFactory.isAnonymous()){
			getDefaultRatings();
			showLoginPopupWidget();
		}else{
			if(starFour.getValue()){
				crateStarRating(FOUR_STAR);
			}else{
			}
		}
		
	}
	
	/**
	 * On click of Star 5 sets the rating on view.
	 * @param clickEvent {@link ClickEvent}
	 */
	@UiHandler("starFive")
	public void onStarFiveclicked(ClickEvent event){
		if(AppClientFactory.isAnonymous()){
			getDefaultRatings();
			showLoginPopupWidget();
		}else{
			if(starFive.getValue()){
				crateStarRating(FIVE_STAR);
			}else{
			}
		}
		
	}

	/**
	 * Resource ratings will get set based on the respective scores.
	 * 
	 * @param result {@link StarRatingsDo}
	 */
	public void setUserRatings(StarRatingsDo result){
		if(result!=null){
			if(result.getScore()==1){
				starValue.setVisible(true);
				starValue.setText(POOR);
				starValue.getElement().setAttribute("alt", POOR);
				starValue.getElement().setAttribute("title", POOR);
				setStarRatingValue(1); 
			}else if(result.getScore()==2){
				starValue.setVisible(true);
				starValue.setText(FAIR);
				starValue.getElement().setAttribute("alt", FAIR);
				starValue.getElement().setAttribute("title", FAIR);
			}else if(result.getScore()==3){
				starValue.setVisible(true);
				starValue.setText(GOOD);
				starValue.getElement().setAttribute("alt", GOOD);
				starValue.getElement().setAttribute("title", GOOD);
				setStarRatingValue(2);
			}else if(result.getScore()==4){
				starValue.setVisible(true);
				starValue.setText(VERY_GOOD);
				starValue.getElement().setAttribute("alt", VERY_GOOD);
				starValue.getElement().setAttribute("title", VERY_GOOD);
				setStarRatingValue(4);
				
			}else if(result.getScore()==5){
				starValue.setVisible(true);
				starValue.setText(EXCELLENT);
				starValue.getElement().setAttribute("alt", EXCELLENT);
				starValue.getElement().setAttribute("title", EXCELLENT);
				setStarRatingValue(5);
			}else{
				setStarRatingValue(0);
			}
		}else{
			setStarRatingValue(0);
		}
		
	}
	
	/**
	 * Sets the stars on view based on the scores.
	 * @param starRating {@link Integer}
	 */
	private void setStarRatingValue(int starRating) {
		if(starRating==1){
			starOne.setValue(true);
			starTwo.setValue(false);
			starThree.setValue(false);
			starFour.setValue(false);
			starFive.setValue(false);
		}else if(starRating==2){
			starOne.setValue(true);
			starTwo.setValue(true);
			starThree.setValue(false);
			starFour.setValue(false);
			starFive.setValue(false);
		}else if(starRating==3){
			starOne.setValue(true);
			starTwo.setValue(true);
			starThree.setValue(true);
			starFour.setValue(false);
			starFive.setValue(false);
		}else if(starRating==4){
			starOne.setValue(true);
			starTwo.setValue(true);
			starThree.setValue(true);
			starFour.setValue(true);
			starFive.setValue(false);
		}else if(starRating==5){
			starOne.setValue(true);
			starTwo.setValue(true);
			starThree.setValue(true);
			starFour.setValue(true);
			starFive.setValue(true);
		}else{
			starOne.setValue(false);
			starTwo.setValue(false);
			starThree.setValue(false);
			starFour.setValue(false);
			starFive.setValue(false);
		}
	}

	/**
	 * Abstract method, which will send the rated information to the implemented class.
	 * @param selectedStar
	 */
	public abstract void crateStarRating(String selectedStar);
	
	/**
	 * Invokes Log-in pop-up
	 */
	private void showLoginPopupWidget() { 
		LoginPopupUc popup =new  LoginPopupUc() {
			@Override
			public void onLoginSuccess() {
				
			}
		};
		popup.setWidgetMode("ratingWidget");
		popup.setGlassEnabled(true);
	}
	
	/**
	 * default rating will get set
	 */
	public void getDefaultRatings(){
		starOne.setValue(false);
		starTwo.setValue(false);
		starThree.setValue(false);
		starFour.setValue(false);
		starFive.setValue(false);
	}
	
	public class OnStarMouseOver implements MouseOverHandler{
		private String starScore="";
		public OnStarMouseOver(String starScore) {
			this.starScore=starScore;
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			if(starScore.equalsIgnoreCase(ONE_STAR)){
				starValue.setVisible(false);
				mouseOverStarValue.setText(POOR);
				mouseOverStarValue.getElement().setAttribute("alt", POOR);
				mouseOverStarValue.getElement().setAttribute("title", POOR);
			}else if(starScore.equalsIgnoreCase(TWO_STAR)){
				starValue.setVisible(false);
				mouseOverStarValue.setText(FAIR);
				mouseOverStarValue.getElement().setAttribute("alt", FAIR);
				mouseOverStarValue.getElement().setAttribute("title", FAIR);
			}else if(starScore.equalsIgnoreCase(THREE_STAR)){
				starValue.setVisible(false);
				mouseOverStarValue.setText(GOOD);
				mouseOverStarValue.getElement().setAttribute("alt", GOOD);
				mouseOverStarValue.getElement().setAttribute("title", GOOD);
			}else if(starScore.equalsIgnoreCase(FOUR_STAR)){
				starValue.setVisible(false);
				mouseOverStarValue.setText(VERY_GOOD);
				mouseOverStarValue.getElement().setAttribute("alt", VERY_GOOD);
				mouseOverStarValue.getElement().setAttribute("title", VERY_GOOD);
			}else if(starScore.equalsIgnoreCase(FIVE_STAR)){
				starValue.setVisible(false);
				mouseOverStarValue.setText(EXCELLENT);
				mouseOverStarValue.getElement().setAttribute("alt", EXCELLENT);
				mouseOverStarValue.getElement().setAttribute("title", EXCELLENT);
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
			
			if(starScore.equalsIgnoreCase(ONE_STAR)){
				starValue.setVisible(true);
				mouseOverStarValue.setText("");
			}else if(starScore.equalsIgnoreCase(TWO_STAR)){
				starValue.setVisible(true);
				mouseOverStarValue.setText("");
			}else if(starScore.equalsIgnoreCase(THREE_STAR)){
				starValue.setVisible(true);
				mouseOverStarValue.setText("");
			}else if(starScore.equalsIgnoreCase(FOUR_STAR)){
				starValue.setVisible(true);
				mouseOverStarValue.setText("");
			}else if(starScore.equalsIgnoreCase(FIVE_STAR)){
				starValue.setVisible(true);
				mouseOverStarValue.setText("");
			}
		}
	}
	
}
