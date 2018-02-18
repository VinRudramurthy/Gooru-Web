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



import org.ednovo.gooru.client.uc.PlayerBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class RatingWidgetView extends Composite{

	@UiField InlineLabel ratingCountLabel,star_1,star_2,star_3,star_4,star_5,avgRatingLabel,ratingCountOpenBrace,ratingCountCloseBrace;
	@UiField FlowPanel maincontainer;
	

	public InlineLabel getRatingCountOpenBrace() {
		return ratingCountOpenBrace;
	}

	public void setRatingCountOpenBrace(InlineLabel ratingCountOpenBrace) {
		this.ratingCountOpenBrace = ratingCountOpenBrace;
	}

	public InlineLabel getRatingCountCloseBrace() {
		return ratingCountCloseBrace;
	}

	public void setRatingCountCloseBrace(InlineLabel ratingCountCloseBrace) {
		this.ratingCountCloseBrace = ratingCountCloseBrace;
	}

	private static RatingWidgetViewUiBinder uiBinder = GWT.create(RatingWidgetViewUiBinder.class);

	interface RatingWidgetViewUiBinder extends UiBinder<Widget, RatingWidgetView> {

	}

	public RatingWidgetView(){
		initWidget(uiBinder.createAndBindUi(this));
		maincontainer.getElement().setId("epnlReviewContainer");
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		star_1.getElement().setId("spnStar_1");
		star_2.getElement().setId("spnStar_2");
		star_3.getElement().setId("spnStar_3");
		star_4.getElement().setId("spnStar_4");
		star_5.getElement().setId("spnStar_5");
		ratingCountLabel.getElement().setId("spnRatingCountLabel");
		avgRatingLabel.getElement().setId("spnAvgRatingLabel");
		ratingCountOpenBrace.getElement().setId("spnRatingOpenBrace");
		ratingCountCloseBrace.getElement().setId("spnRatingCloseBrace");
	}

	public InlineLabel getRatingCountLabel(){
		return ratingCountLabel;
	}
	
	public InlineLabel getAverageRatingLabel(){
		return avgRatingLabel;
	}

	public void setAvgStarRating(double averageRating) {
		Integer roundOffAvg = (int) Math.round(averageRating);
		star_1.getElement().removeAttribute("class");
		star_2.getElement().removeAttribute("class");
		star_3.getElement().removeAttribute("class");
		star_4.getElement().removeAttribute("class");
		star_5.getElement().removeAttribute("class");
		star_1.getElement().addClassName(PlayerBundle.INSTANCE.getPlayerStyle().star());
		star_2.getElement().addClassName(PlayerBundle.INSTANCE.getPlayerStyle().star());
		star_3.getElement().addClassName(PlayerBundle.INSTANCE.getPlayerStyle().star());
		star_4.getElement().addClassName(PlayerBundle.INSTANCE.getPlayerStyle().star());
		star_5.getElement().addClassName(PlayerBundle.INSTANCE.getPlayerStyle().star());
		if(roundOffAvg!=null){
			if(roundOffAvg==1){
				star_1.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
			}else if(roundOffAvg==2){
				star_1.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_2.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
			}else if(roundOffAvg==3){
				star_1.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_2.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_3.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
			}else if(roundOffAvg==4){
				star_1.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_2.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_3.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_4.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
			}else if(roundOffAvg==5){
				star_1.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_2.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_3.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_4.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_5.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
			}else if(roundOffAvg==0){
				star_1.removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_2.removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_3.removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_4.removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
				star_5.removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().filled());
			}
		}
	}
}