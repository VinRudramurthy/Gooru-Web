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
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ContentStarRatingsDo;
import org.ednovo.gooru.application.shared.model.content.StarRatingsDo;
import org.ednovo.gooru.client.uc.PlayerBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class RatingAndReviewPopupView extends PopupViewWithUiHandlers<RatingAndReviewPopupUiHandlers> implements IsRatingAndReviewPopupView{

	public PopupPanel appPopUp =new PopupPanel();

	@UiField HTMLPanel maincontainer;

	@UiField Label closeButton;

	@UiField HTML lblResourceTitle;

	@UiField Label excellentScore, verygoodScore, goodScore, fairScore, poorScore,excellentLbl,veryGoodLbl,goodLbl,fairLbl,poorLbl,avgLbl,rateMsg,ratingDistributionLbl;

	@UiField HTMLPanel panelRatingValues,panelRatingLabels,userRatingContainer, dataOne, dataTwo, dataThree, dataFour, dataFive;

	@UiField VerticalPanel reviewsContainer;

	@UiField FlowPanel ratingWidgetPanel;

	@UiField Button rateResourceBtn;

	@UiField ScrollPanel reviewScrollPanel;

	private String gooruOid = null;
	private String createrName = null;

	private boolean isRated=false;

	private boolean apiInprogress=true;

	private int totalHitCount=0;

	private int reviewSize=0;

	private RatingWidgetView ratingWidgetView= new RatingWidgetView();

	private static ResourceNarrationViewUiBinder uiBinder = GWT.create(ResourceNarrationViewUiBinder.class);

	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface ResourceNarrationViewUiBinder extends UiBinder<Widget, RatingAndReviewPopupView> {

	}

	@Inject
	public RatingAndReviewPopupView(EventBus eventsBus){
		super(eventsBus);
		appPopUp.setGlassEnabled(true);
		if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY) || AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY) || AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.RESOURCE_PLAY)){
			appPopUp.setGlassStyleName("setGlassPanelZIndex");
		}
		appPopUp.setWidget(uiBinder.createAndBindUi(this));
		maincontainer.getElement().setId("epnlReviewContainer");
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		reviewsContainer.getElement().getStyle().setWidth(100, Unit.PCT);
		reviewScrollPanel.setHeight("500px");
		reviewScrollPanel.getElement().getStyle().setOverflow(Overflow.AUTO);

	}

	@UiHandler("closeButton")
	public void closeRatingAndReviewPopup(ClickEvent event){
		String currentToken = AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		Element element = Document.get().getElementById("fixedFilterSearchID");
		if(element!=null){
			element.removeAttribute("style");
		}
		hide();
		if (!currentToken.equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY) && !currentToken.equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY) && !currentToken.equalsIgnoreCase(PlaceTokens.RESOURCE_PLAY)){
			Window.enableScrolling(true);
		}
		Window.enableScrolling(true);
	}

	@Override
	public Widget asWidget() {
		return appPopUp;
	}
	@Override
	public void reset() {

	}
	@Override
	public void onLoad() {

	}
	@Override
	public void onUnload() {

	}

	@Override
	public void displayPopUp(String resourceTitle, String gooruOid,String createrName) {
		this.gooruOid = gooruOid;
		this.createrName = createrName;
		reviewSize=0;
		userRatingContainer.setVisible(false);
		lblResourceTitle.setHTML(i18n.GL1840()+" "+removeHtmlTags(resourceTitle));
		lblResourceTitle.getElement().setId("lblResourceTitle");
		lblResourceTitle.getElement().setAttribute("alt",i18n.GL1840()+" "+removeHtmlTags(resourceTitle));
		lblResourceTitle.getElement().setAttribute("title",i18n.GL1840()+" "+removeHtmlTags(resourceTitle));

		closeButton.getElement().setId("lblCloseButton");
		panelRatingLabels.getElement().setId("pnlPanelRatingLabels");
		panelRatingValues.getElement().setId("pnlPanelRatingValues");
		dataFive.getElement().setId("pnlDataFive");
		excellentScore.getElement().setId("lblExcellentScore");
		dataFour.getElement().setId("pnlDataFour");
		verygoodScore.getElement().setId("lblVerygoodScore");
		dataThree.getElement().setId("pnlDataThree");
		goodScore.getElement().setId("lblGoodScore");
		dataTwo.getElement().setId("pnlDataTwo");
		fairScore.getElement().setId("lblFairScore");
		dataOne.getElement().setId("pnlDataOne");
		poorScore.getElement().setId("lblPoorScore");
		ratingWidgetPanel.getElement().setId("fpnlRatingWidgetPanel");
		userRatingContainer.getElement().setId("pnlUserRatingContainer");
		reviewsContainer.getElement().setId("vpnlReviewsContainer");
		setStaticText();
		clearContainer();
		getAverageRatingForContent(gooruOid);
		getUserRatingsAndReviews(gooruOid);
	}

	private void clearContainer() {
		reviewsContainer.clear();
	}

	private void setStaticText() {
		excellentLbl.setText(i18n.GL1842());
		excellentLbl.getElement().setId("lblExcellentLbl");
		excellentLbl.getElement().setAttribute("alt",i18n.GL1842());
		excellentLbl.getElement().setAttribute("title",i18n.GL1842());

		veryGoodLbl.setText(i18n.GL1843());
		veryGoodLbl.getElement().setId("lblVeryGoodLbl");
		veryGoodLbl.getElement().setAttribute("alt",i18n.GL1843());
		veryGoodLbl.getElement().setAttribute("title",i18n.GL1843());

		goodLbl.setText(i18n.GL1844());
		goodLbl.getElement().setId("lblGoodLbl");
		goodLbl.getElement().setAttribute("alt",i18n.GL1844());
		goodLbl.getElement().setAttribute("title",i18n.GL1844());

		fairLbl.setText(i18n.GL1845());
		fairLbl.getElement().setId("lblFairLbl");
		fairLbl.getElement().setAttribute("alt",i18n.GL1845());
		fairLbl.getElement().setAttribute("title",i18n.GL1845());

		poorLbl.setText(i18n.GL1846());
		poorLbl.getElement().setId("lblPoorLbl");
		poorLbl.getElement().setAttribute("alt",i18n.GL1846());
		poorLbl.getElement().setAttribute("title",i18n.GL1846());

		avgLbl.setText(i18n.GL1848());
		avgLbl.getElement().setId("lblAvgLbl");
		avgLbl.getElement().setAttribute("alt",i18n.GL1848());
		avgLbl.getElement().setAttribute("title",i18n.GL1848());

		rateResourceBtn.setText(i18n.GL1849());
		rateResourceBtn.getElement().setId("btnRateResourceBtn");
		rateResourceBtn.getElement().setAttribute("alt",i18n.GL1849());
		rateResourceBtn.getElement().setAttribute("title",i18n.GL1849());

		ratingDistributionLbl.setText(i18n.GL1841());
		ratingDistributionLbl.getElement().setId("lblRatingDistributionLbl");
		ratingDistributionLbl.getElement().setAttribute("alt",i18n.GL1841());
		ratingDistributionLbl.getElement().setAttribute("title",i18n.GL1841());

		rateMsg.setText(i18n.GL1992());
		rateMsg.getElement().setId("lblRateMsg");
		rateMsg.getElement().setAttribute("alt",i18n.GL1992());
		rateMsg.getElement().setAttribute("title",i18n.GL1992());
	}

	public void getUserRatingsAndReviews(String resourceId)
	{
		getUiHandlers().getUserRatingsReviews(resourceId,0);
	}

	public void getAverageRatingForContent(String resourceId)
	{
		getUiHandlers().getAverageRatingForContent(resourceId);
	}

	@Override
	public void setGraphAndAvgContentRating(ContentStarRatingsDo result) {
		if(result!=null){
		setContentGraph(result);
		ratingWidgetView.setAvgStarRating(result.getAverage());
		ratingWidgetView.getRatingCountOpenBrace().setText(i18n. GL_SPL_OPEN_SMALL_BRACKET());
		if(result.getCount()!=null){
		ratingWidgetView.getRatingCountLabel().setText(result.getCount().toString());
		}
		ratingWidgetView.getRatingCountCloseBrace().setText(i18n. GL_SPL_CLOSE_SMALL_BRACKET());
		ratingWidgetPanel.add(ratingWidgetView);
		}
	}

	private void setContentGraph(ContentStarRatingsDo result) {

		int totalReviewCount = result.getScores().getFive() + result.getScores().getFour() + result.getScores().getThree() + result.getScores().getTwo() + result.getScores().getOne();

		dataFive.getElement().getStyle().setWidth(getBarGraphWidth(result.getScores().getFive(), totalReviewCount), Unit.PCT);
		dataFour.getElement().getStyle().setWidth(getBarGraphWidth(result.getScores().getFour(), totalReviewCount), Unit.PCT);
		dataThree.getElement().getStyle().setWidth(getBarGraphWidth(result.getScores().getThree(), totalReviewCount), Unit.PCT);
		dataTwo.getElement().getStyle().setWidth(getBarGraphWidth(result.getScores().getTwo(), totalReviewCount), Unit.PCT);
		dataOne.getElement().getStyle().setWidth(getBarGraphWidth(result.getScores().getOne(), totalReviewCount), Unit.PCT);

		excellentScore.setText("("+result.getScores().getFive()+")");
		excellentScore.getElement().setAttribute("alt","("+result.getScores().getFive()+")");
		excellentScore.getElement().setAttribute("title","("+result.getScores().getFive()+")");

		verygoodScore.setText("("+result.getScores().getFour()+")");
		verygoodScore.getElement().setAttribute("alt","("+result.getScores().getFour()+")");
		verygoodScore.getElement().setAttribute("title","("+result.getScores().getFour()+")");

		goodScore.setText("("+result.getScores().getThree()+")");
		goodScore.getElement().setAttribute("alt","("+result.getScores().getThree()+")");
		goodScore.getElement().setAttribute("title","("+result.getScores().getThree()+")");

		fairScore.setText("("+result.getScores().getTwo()+")");
		fairScore.getElement().setAttribute("alt","("+result.getScores().getTwo()+")");
		fairScore.getElement().setAttribute("title","("+result.getScores().getTwo()+")");

		poorScore.setText("("+result.getScores().getOne()+")");
		poorScore.getElement().setAttribute("alt","("+result.getScores().getOne()+")");
		poorScore.getElement().setAttribute("title","("+result.getScores().getOne()+")");
	}

	private double getBarGraphWidth(Integer count, Integer totalCount) {
		double width = 0.0;
		if(totalCount>0) {
			width = ((double)count / (double)totalCount);
		}
		return (width * 100);
	}

	@Override
	public void setUserRatingsAndReviews(ArrayList<StarRatingsDo> result) {
		if(result.size()>0)
		{
			totalHitCount = result.get(0).getTotalHitCount();
			reviewSize = reviewSize+result.size();
			for(int i=0;i<result.size();i++){
				if(result.get(i).getCreator().getUsername().equals(AppClientFactory.getLoggedInUser().getUsername())){
					isRated=true;
					break;
				}
			}
			if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SEARCH_RESOURCE)){
				rateResourceBtn.setVisible(true);
			}else{
				rateResourceBtn.setVisible(false);
			}
		}else{
			if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SEARCH_RESOURCE)){
				userRatingContainer.setVisible(true);
			}
		}


		for(int userReviews=0; userReviews<result.size(); userReviews++)
		{
			if(result.get(userReviews).getCreator().getUsername().equals(AppClientFactory.getLoggedInUser().getUsername())){
				reviewsContainer.add(new RatingUserWidgetView(result.get(userReviews),createrName));
			}else{
				if(!result.get(userReviews).getFreeText().equals("")){
					reviewsContainer.add(new RatingUserWidgetView(result.get(userReviews),createrName));
				}
			}

		}

		apiInprogress=false;

	}

	public class OnClickEditRatingButton implements ClickHandler{
		HTMLPanel htmlEdittingContainer;
		HTMLPanel ratingPanel;

		public OnClickEditRatingButton(HTMLPanel htmlEdittingContainer,HTMLPanel ratingPanel) {
			this.htmlEdittingContainer=htmlEdittingContainer;
			this.ratingPanel=ratingPanel;
		}

		@Override
		public void onClick(ClickEvent event) {
			htmlEdittingContainer.setVisible(true);
			ratingPanel.setVisible(false);
		}

	}

	public class OnClickCancelRatingButton implements ClickHandler{
		HTMLPanel htmlEdittingContainer;
		HTMLPanel ratingPanel;
		public OnClickCancelRatingButton(HTMLPanel htmlEdittingContainer,HTMLPanel ratingPanel) {
			this.htmlEdittingContainer=htmlEdittingContainer;
			this.ratingPanel=ratingPanel;
		}

		@Override
		public void onClick(ClickEvent event) {
			htmlEdittingContainer.setVisible(false);
			ratingPanel.setVisible(true);
		}

	}

	public class OnRatingReviewContainerMouseOver implements MouseOverHandler{
		Button btnEditRating;

		public OnRatingReviewContainerMouseOver(Button btnEditRating,Button btnDeleteRating) {
			this.btnEditRating=btnEditRating;

		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			btnEditRating.setVisible(true);

		}

	}

	public class OnRatingReviewContainerMouseOut implements MouseOutHandler{
		Button btnEditRating;
		public OnRatingReviewContainerMouseOut(Button btnEditRating) {
			this.btnEditRating=btnEditRating;
		}

		@Override
		public void onMouseOut(MouseOutEvent event) {
			btnEditRating.setVisible(false);
		}

	}

	@UiHandler("rateResourceBtn")
	public void openResourcePlayer(ClickEvent event) {
		hide();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", gooruOid);
		params.put("pn", "resource");
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.RESOURCE_PLAY, params);
	}


	@UiHandler("reviewScrollPanel")
	public void onScrollReviews(ScrollEvent event){
		if(!apiInprogress&&reviewScrollPanel.getVerticalScrollPosition() == reviewScrollPanel.getMaximumVerticalScrollPosition()&&(reviewSize)<(totalHitCount)){
			apiInprogress=true;
			getUiHandlers().getUserRatingsReviews(gooruOid,(reviewSize));
		}
	}
	private String removeHtmlTags(String html){
        html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
        return html;
	}
}
