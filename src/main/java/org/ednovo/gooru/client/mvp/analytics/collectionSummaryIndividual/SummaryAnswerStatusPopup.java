package org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class SummaryAnswerStatusPopup extends PopupPanel implements ClientConstants {
	private static SummaryAnswerStatusPopupUiBinder uiBinder = GWT	.create(SummaryAnswerStatusPopupUiBinder.class);

	interface SummaryAnswerStatusPopupUiBinder extends UiBinder<Widget, SummaryAnswerStatusPopup> {
	}

	@UiField FlowPanel ansFlowPnl;
	@UiField Label closeLbl;
    private String questionType;
    int noOfAttempts;
	/**
	 * Constructor
	 */
	public SummaryAnswerStatusPopup(JSONArray attemptsObj ,String questionType,String attempts) {
		setWidget(uiBinder.createAndBindUi(this));
		this.setGlassEnabled(true);
		this.getGlassElement().getStyle().setZIndex(99999999);
		this.getElement().getStyle().setZIndex(99999999);
		this.center();
		this.show();
		this.questionType=questionType;
		this.noOfAttempts=Integer.parseInt(attempts!=null?attempts:"0");
		renderAnswersData(attemptsObj);
	}


	public void renderAnswersData(JSONArray attemptsObj ){

		if(HS_IMG.equalsIgnoreCase(questionType)){
			for(int j=0;j<attemptsObj.size();j++){
	        	Image answerChoice=new Image();
	        	answerChoice.addStyleName("summaryHsImg");
	            boolean skip = attemptsObj.get(j).isObject().get("skip").isBoolean().booleanValue();
	        	String status =attemptsObj.get(j).isObject().get("status").isString().stringValue();
	        	String hsImage =attemptsObj.get(j).isObject().get("text").isString().stringValue();
		         if(skip == false)
				  {
					answerChoice.setUrl(hsImage);
						if(ZERO_NUMERIC.equalsIgnoreCase(status)) {
							answerChoice.getElement().getStyle().setBorderColor(INCORRECT);
						} else if(ONE.equalsIgnoreCase(status) && (noOfAttempts == 1)) {
							answerChoice.getElement().getStyle().setBorderColor(CORRECT);
						} else if(ONE.equalsIgnoreCase(status) && (noOfAttempts > 1)) {
							answerChoice.getElement().getStyle().setBorderColor(ONMULTIPULEATTEMPTS);
						}
				  }
		        ansFlowPnl.add(answerChoice);
	         }
		}else if(questionType.equalsIgnoreCase("HS") || HS_TXT.equalsIgnoreCase(questionType)){
			 for(int j=0;j<attemptsObj.size();j++){
		        	HTML answerChoice=new HTML();
		        	answerChoice.getElement().getStyle().setPadding(5, Unit.PX);
		            boolean skip = attemptsObj.get(j).isObject().get("skip").isBoolean().booleanValue();
		        	String status =attemptsObj.get(j).isObject().get("status").isString().stringValue();
		        	String hsText =attemptsObj.get(j).isObject().get("text").isString().stringValue();
  		         if(skip == false)
					  {
						answerChoice.setHTML(URL.decodeQueryString(hsText));
							if(ZERO_NUMERIC.equalsIgnoreCase(status)) {
								answerChoice.addStyleName(HS_INCORRECT);
							} else if(ONE.equalsIgnoreCase(status) && (noOfAttempts == 1)) {
								answerChoice.addStyleName(HS_CORRECT);
							} else if(ONE.equalsIgnoreCase(status) && (noOfAttempts > 1)) {
								answerChoice.addStyleName(HS_ONMULTIPULEATTEMPTS);
							}
					  }
  		      ansFlowPnl.add(answerChoice);
		         }

		}else if(HT_HL.equalsIgnoreCase(questionType)){
			 for(int j=0;j<attemptsObj.size();j++){
		        	HTML answerChoice=new HTML();
		        	answerChoice.getElement().getStyle().setPadding(5, Unit.PX);
		            boolean skip = attemptsObj.get(j).isObject().get("skip").isBoolean().booleanValue();
		        	String status =attemptsObj.get(j).isObject().get("status").isString().stringValue();
		        	String hlText =attemptsObj.get(j).isObject().get("text").isString().stringValue();

  		         if(skip == false)
  		         {
  		        	 if(hlText.contains(PLAYER_HT_HL)){
  		        		 hlText=hlText.replaceAll(PLAYER_HT_HL, SUMMARY_HT_HL);
  		        	 }
  		        	 if(hlText.contains(PLAYER_HT_ANS)){
  		        		 hlText=hlText.replaceAll(PLAYER_HT_ANS, SUMMARY_HTPLAYER_ANS);
  		        	 }
  		        	 if(ONE.equalsIgnoreCase(status) && (noOfAttempts > 1)) {
  		        		hlText=hlText.replaceAll(CORRECT_WORD, MULTI_CORRECT_WORD);
  		        	 }
  		        	 answerChoice.setHTML(URL.decodeQueryString(hlText));
  		         }
  		       ansFlowPnl.add(answerChoice);
		         }
		}else if(HT_RO.equalsIgnoreCase(questionType)){
			 for(int j=0;j<attemptsObj.size();j++){
		        	HTML answerChoice=new HTML();
		        	answerChoice.getElement().getStyle().setPadding(5, Unit.PX);
		            boolean skip = attemptsObj.get(j).isObject().get("skip").isBoolean().booleanValue();
		        	String status =attemptsObj.get(j).isObject().get("status").isString().stringValue();
		        	String htROText =attemptsObj.get(j).isObject().get("text").isString().stringValue();
		         if(skip == false)
					  {
						answerChoice.setHTML(URL.decodeQueryString(htROText));
						if(ZERO_NUMERIC.equalsIgnoreCase(status)) {
							answerChoice.addStyleName(HS_INCORRECT);
						} else if(ONE.equalsIgnoreCase(status) && (noOfAttempts == 1)) {
							answerChoice.addStyleName(HS_CORRECT);
						} else if(ONE.equalsIgnoreCase(status) && (noOfAttempts > 1)) {
							answerChoice.addStyleName(HS_ONMULTIPULEATTEMPTS);
						}
					  }
		       ansFlowPnl.add(answerChoice);
		         }
		}else if(OE.equalsIgnoreCase(questionType)){
			 for(int j=0;j<attemptsObj.size();j++){
		        	HTML answerChoice=new HTML();
		        	answerChoice.getElement().getStyle().setPadding(5, Unit.PX);
		            boolean skip = attemptsObj.get(j).isObject().get("skip").isBoolean().booleanValue();
		        	String status =attemptsObj.get(j).isObject().get("status").isString().stringValue();
		        	String OeAnswer =attemptsObj.get(j).isObject().get("text").isString().stringValue();
		         if(skip == false)
					  {
		        	 	AppClientFactory.printInfoLogger("OeAnswer : "+OeAnswer);
						answerChoice.setHTML(URL.decodeQueryString(OeAnswer));
						/*if(ZERO_NUMERIC.equalsIgnoreCase(status)) {
							answerChoice.addStyleName(HS_INCORRECT);
						} else if(ONE.equalsIgnoreCase(status) && (noOfAttempts == 1)) {
							answerChoice.addStyleName(HS_CORRECT);
						} else if(ONE.equalsIgnoreCase(status) && (noOfAttempts > 1)) {
							answerChoice.addStyleName(HS_ONMULTIPULEATTEMPTS);
						}*/
					  }
		         ansFlowPnl.add(answerChoice);
		        }
		}


	}


	@UiHandler("closeLbl")
	public void closePopup(ClickEvent event){
		this.hide();
	}


}
