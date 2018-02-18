package org.ednovo.gooru.client.mvp.play.resource.body;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.rating.RatingWidgetView;
import org.ednovo.gooru.client.mvp.rating.events.OpenReviewPopUpEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class RatingsConfirmationPopup extends PopupPanel{

	@UiField Button btnOk;
	@UiField public FlowPanel ratingWidgetPanel;
	private RatingWidgetView ratingWidgetView=null;
	String assocGooruOId,createrName;
	@UiField Label reviewRatingText,saveAndPsotLbl;
	@UiField HTMLPanel averageRationPanel,buttonsContainer;
	@UiField HTMLPanel imgSuccessIcon,ratingsMainContainer;
	
	private static RatingsConfirmationPopupUiBinder uiBinder = GWT
			.create(RatingsConfirmationPopupUiBinder.class);

	interface RatingsConfirmationPopupUiBinder extends
			UiBinder<Widget, RatingsConfirmationPopup> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	public RatingsConfirmationPopup(String assocGooruOId, Integer score,
			Integer count, double average,String createrName) {
			setWidget(uiBinder.createAndBindUi(this));
			this.assocGooruOId=assocGooruOId;
			this.createrName = createrName;
			ratingsMainContainer.getElement().setId("fpnlWrapperContainerField");
			reviewRatingText.setText(i18n.GL1856());
			reviewRatingText.getElement().setId("lblReviewRatingText");
			reviewRatingText.getElement().setAttribute("alt",i18n.GL1856());
			reviewRatingText.getElement().setAttribute("title",i18n.GL1856());
			
			averageRationPanel.getElement().setInnerHTML(i18n.GL1848());
			averageRationPanel.getElement().setId("pnlAverageRationPanel");
			averageRationPanel.getElement().setAttribute("alt",i18n.GL1848());
			averageRationPanel.getElement().setAttribute("title",i18n.GL1848());
			
			btnOk.setText(i18n.GL0190());
			btnOk.getElement().setId("btnOk");
			btnOk.getElement().setAttribute("alt",i18n.GL0190());
			btnOk.getElement().setAttribute("title",i18n.GL0190());
			setAvgRatingWidget(assocGooruOId,score,count,average,createrName);
			setGlassEnabled(true);
			imgSuccessIcon.getElement().setId("btnOk");
			ratingWidgetPanel.getElement().setId("fpnlRatingWidgetPanel");
			buttonsContainer.getElement().setId("pnlButtonsContainer");
			saveAndPsotLbl.getElement().setId("lblSaveAndPsotLbl");
	}
	/**
	 * Average star ratings widget will get integrated.
	 */
	private void setAvgRatingWidget(String assocGooruOId, Integer score,Integer count, double average,String createrName){
		ratingWidgetView=new RatingWidgetView();
		if(count!=null){
			ratingWidgetView.getRatingCountOpenBrace().setText(i18n. GL_SPL_OPEN_SMALL_BRACKET());
			ratingWidgetView.getRatingCountLabel().setText(count.toString());
			ratingWidgetView.getRatingCountCloseBrace().setText(i18n. GL_SPL_CLOSE_SMALL_BRACKET());
		}
		ratingWidgetView.setAvgStarRating(average);
		if(count>0)
		{
		}
		ratingWidgetPanel.add(ratingWidgetView);
	}
	/**
	 * 
	 * Inner class implementing {@link ClickEvent}
	 *
	 */
	private class ShowRatingPopupEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			/**
			 * OnClick of count label event to invoke Review pop-pup
			 */
			closeAction();
			AppClientFactory.fireEvent(new OpenReviewPopUpEvent(assocGooruOId, "",createrName)); 
		}
	}
	
	public void closeAction()
	{
		this.hide();
	}
	
	@UiHandler("btnOk")
	public void btnOkClick(ClickEvent event){
		this.hide();
	}
}
