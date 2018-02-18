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
package org.ednovo.gooru.client.mvp.analytics.util;

import java.util.Iterator;
import java.util.Set;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.analytics.FeedBackResponseDataDO;
import org.ednovo.gooru.application.shared.model.analytics.OetextDataDO;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ViewResponseUserWidget extends Composite {

	private static ViewResponseUserWidgetUiBinder uiBinder = GWT
			.create(ViewResponseUserWidgetUiBinder.class);

	interface ViewResponseUserWidgetUiBinder extends
			UiBinder<Widget, ViewResponseUserWidget> {
	}
	@UiField Label teacherName,questionCountlbl,usernamelbl,userResponselbl,editedText,createOn;
	@UiField HTMLPanel giveFeedBackpnl,editFeedBackpnl,userAnswerspnl;
	@UiField TextBox feedBacktxt;
	@UiField InlineLabel spnEdit,spnDelete;
	@UiField Button btnSubmit;
	@UiField Image userProfileImage,userProfileImage1;

	OetextDataDO oetextDataDO;
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Constructor
	 * @param oetextDataDO
	 * @param resourceGooruId
	 * @param collectionId
	 * @param classpageId
	 * @param pathwayId
	 * @param questionType
	 * @param isSummary
	 */
	public ViewResponseUserWidget(OetextDataDO oetextDataDO,String resourceGooruId,String collectionId, String classpageId,String pathwayId,String questionType,boolean isSummary,String session,ClasspageItemDo classpageItemDo) {
		initWidget(uiBinder.createAndBindUi(this));
		setData(oetextDataDO,resourceGooruId,collectionId,classpageId,pathwayId,questionType,isSummary,session,classpageItemDo);
	 	feedBacktxt.getElement().setAttribute("placeholder", i18n.GL3117());
		questionCountlbl.setVisible(false);
	}
	/**
	 * Constructor
	 * @param questionCount
	 * @param questionText
	 * @param questionAnswers
	 */
	public ViewResponseUserWidget(String questionCount,String questionText,String questionAnswers,String questionType) {
		initWidget(uiBinder.createAndBindUi(this));
		questionCountlbl.setVisible(true);
		questionCountlbl.setText(i18n.GL0308()+" "+questionCount);
		usernamelbl.setText(questionText);
		String answerVal="";
		if(questionType.equalsIgnoreCase("MA")){
			 answerVal=questionAnswers.replaceAll("1", "Yes");
			 answerVal=answerVal.replaceAll("0", "No");
		}else{
			answerVal=questionAnswers;
		}

		userResponselbl.setText(answerVal);
		giveFeedBackpnl.setVisible(false);
		editFeedBackpnl.setVisible(false);
		spnEdit.setVisible(false);
		spnDelete.setVisible(false);
	}
	class OnErrorProfileImage implements ErrorHandler{
		@Override
		public void onError(ErrorEvent event) {
			userProfileImage.setUrl("../images/settings/setting-user-image.png");
			userProfileImage1.setUrl("../images/settings/setting-user-image.png");
		}
	}
	/**
	 * This method is used to set the data.
	 * @param oetextDataDO
	 * @param resourceGooruId
	 * @param collectionId
	 * @param classpageId
	 * @param pathwayId
	 * @param questionType
	 * @param isSummary
	 */
	void setData(final OetextDataDO oetextDataDO,final String resourceGooruId,final String collectionId, final String classpageId,final String pathwayId,String questionType,boolean isSummary,final String session,ClasspageItemDo classpageItemDo){
		this.oetextDataDO=oetextDataDO;
		giveFeedBackpnl.setVisible(false);
		editFeedBackpnl.setVisible(false);
		userAnswerspnl.setVisible(false);
		userResponselbl.setVisible(false);
		if(isSummary){
			spnEdit.setVisible(true);
			spnDelete.setVisible(true);
		}else{
			spnEdit.setVisible(false);
			spnDelete.setVisible(false);
		}
		if(classpageItemDo!=null && classpageItemDo.getUserNameDispaly()!=null){
			teacherName.setText(classpageItemDo.getUserNameDispaly()+i18n.GL_GRR_ALPHABET_APOSTROPHE()+" "+i18n.GL0195());
			if(classpageItemDo.getProfileImageUrl()!=null){
				userProfileImage.setUrl(classpageItemDo.getProfileImageUrl());
				userProfileImage1.setUrl(classpageItemDo.getProfileImageUrl());
			}else{
				userProfileImage.setUrl("../images/settings/setting-user-image.png");
				userProfileImage1.setUrl("../images/settings/setting-user-image.png");
			}
		}else{
			teacherName.setText(AppClientFactory.getLoggedInUser().getUsernameDisplay()+i18n.GL_GRR_ALPHABET_APOSTROPHE()+" "+i18n.GL0195());
			if(AppClientFactory.getLoggedInUser().getProfileImageUrl()!=null){
				userProfileImage.setUrl(AppClientFactory.getLoggedInUser().getProfileImageUrl());
				userProfileImage1.setUrl(AppClientFactory.getLoggedInUser().getProfileImageUrl());
			}else{
				userProfileImage.setUrl("../images/settings/setting-user-image.png");
				userProfileImage1.setUrl("../images/settings/setting-user-image.png");
			}
		}

		userProfileImage.addErrorHandler(new OnErrorProfileImage());
		userProfileImage1.addErrorHandler(new OnErrorProfileImage());

		usernamelbl.setText(oetextDataDO.getUserName());
		String oeText=oetextDataDO.getOEText();
		if(questionType.equalsIgnoreCase("MA") || questionType.equalsIgnoreCase("FIB")){
				userAnswerspnl.setVisible(true);
				if(oetextDataDO.getAnswerObject()!=null) {
	   			 JSONValue value = JSONParser.parseStrict(oetextDataDO.getAnswerObject());
	   			 JSONObject answerObject = value.isObject();
	   			 Set<String> keys=answerObject.keySet();
	   			 Iterator<String> itr = keys.iterator();
	   		      while(itr.hasNext()) {
	   		    	userAnswerspnl.clear();
	   		         JSONArray attemptsObj=(JSONArray) answerObject.get(itr.next().toString());
	   		         for(int j=0;j<attemptsObj.size();j++){
	   		        	Label answerChoice=new Label();
	   		            boolean skip = attemptsObj.get(j).isObject().get("skip").isBoolean().booleanValue();
	   		        	String status =attemptsObj.get(j).isObject().get("status").isString().stringValue();
	   		        	String matext =getTextFromHTML(attemptsObj.get(j).isObject().get("text").isString().stringValue());
	   		        	String colorCode="",text="";
						if(status.equalsIgnoreCase("1")) {
							colorCode = "#4E9746";
						} else {
							colorCode = "#FF0000";
						}
						if(questionType.equalsIgnoreCase("MA")) {
							if(matext.equalsIgnoreCase("1")) {
							    text = "Yes";
							} else {
							    text = "No";
							}
					    }else if(questionType.equalsIgnoreCase("FIB")) {
							text=text+" "+matext;
					    }
	        		    if(skip == false)
						{
	        		    	answerChoice.setText(text+",");
	        		    	answerChoice.getElement().getStyle().setColor(colorCode);
	        		    	answerChoice.getElement().getStyle().setFloat(Float.LEFT);
	        		    	answerChoice.getElement().getStyle().setPaddingRight(1, Unit.PX);
	        		    	answerChoice.getElement().getStyle().setFontWeight(FontWeight.BOLD);
	        		    	answerChoice.getElement().getStyle().setFontSize(14, Unit.PX);
	        		    	userAnswerspnl.add(answerChoice);
						}else{
							answerChoice.setText(i18n.GL3115());
							userAnswerspnl.add(answerChoice);
							break;
						}
	   		         }
	   		      }
	   		}else{
	   			Label answerChoice=new Label();
	   			answerChoice.setText(i18n.GL3115());
				userAnswerspnl.add(answerChoice);
	   		}
		}else{
			userResponselbl.setVisible(true);
			String feedBackStatus=oetextDataDO.getFeedbackStatus();
			if(oeText==null || oeText.trim().isEmpty()){
				userResponselbl.setText(i18n.GL3116());
			}else{
				userResponselbl.setText(getTextFromHTML(oetextDataDO.getOEText()));
			}
			if((isSummary && feedBackStatus!=null && feedBackStatus.equalsIgnoreCase("false")) && (oeText!=null && !oeText.trim().isEmpty())){
				giveFeedBackpnl.setVisible(true);
			}
			if((feedBackStatus!=null && feedBackStatus.equalsIgnoreCase("true")) && (oeText!=null && !oeText.trim().isEmpty())){
				editFeedBackpnl.setVisible(true);
				editedText.setText(oetextDataDO.getFeedbackText());
				feedBacktxt.setText(oetextDataDO.getFeedbackText());
				createOn.setText(AnalyticsUtil.getCreatedTime(Long.toString(oetextDataDO.getFeedbackTimestamp())));
			}
			final String classCode=Document.get().getElementById("txtClassCode")!=null?Document.get().getElementById("txtClassCode").getInnerText():"";
			btnSubmit.addClickHandler(new  ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					AppClientFactory.getInjector().getAnalyticsService().postTeacherFeedBackToStudent(feedBacktxt.getText(), resourceGooruId, collectionId, classpageId, pathwayId, oetextDataDO.getGooruUId(),session,"","",classCode,AppClientFactory.getLoggedInUser().getGooruUId(), new AsyncCallback<FeedBackResponseDataDO>() {
						@Override
						public void onSuccess(FeedBackResponseDataDO result) {
							if(result!=null){
								giveFeedBackpnl.setVisible(false);
								editFeedBackpnl.setVisible(true);
								editedText.setText(result.getFreeText());
								feedBacktxt.setText(result.getFreeText());
								createOn.setText(AnalyticsUtil.getCreatedTime(Long.toString(result.getCreatedOn())));
							}
						}
						@Override
						public void onFailure(Throwable caught) {
						}
					});
				}
			});
			spnDelete.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									AppClientFactory.getInjector().getAnalyticsService().postTeacherFeedBackToStudent("", resourceGooruId, collectionId, classpageId, pathwayId, oetextDataDO.getGooruUId(), session,"commentsDelete","",classCode,AppClientFactory.getLoggedInUser().getGooruUId(), new AsyncCallback<FeedBackResponseDataDO>() {
										@Override
										public void onSuccess(FeedBackResponseDataDO result) {
											if(result!=null){
												giveFeedBackpnl.setVisible(true);
											editFeedBackpnl.setVisible(false);
												editedText.setText(result.getFreeText());
												feedBacktxt.setText(result.getFreeText());
												createOn.setText(AnalyticsUtil.getCreatedTime(Long.toString(result.getCreatedOn())));
											}
										}
										@Override
										public void onFailure(Throwable caught) {
										}
									});
								}
			});
			spnEdit.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					giveFeedBackpnl.setVisible(true);
					editFeedBackpnl.setVisible(false);
				}
			});
		}
	}

	private String getTextFromHTML(String html){
		html = html.replaceAll("\\+", "%2B");
		html = URL.decodeQueryString(html);
		return html;
	}
}
