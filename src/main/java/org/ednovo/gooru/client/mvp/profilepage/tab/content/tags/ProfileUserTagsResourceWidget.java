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
package org.ednovo.gooru.client.mvp.profilepage.tab.content.tags;

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.UserTagsResourceDO;
import org.ednovo.gooru.client.mvp.rating.RatingWidgetView;
import org.ednovo.gooru.client.uc.PlayerBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ProfileUserTagsResourceWidget extends Composite {

	private static ProfileUserTagsResourceWidgetUiBinder uiBinder = GWT
			.create(ProfileUserTagsResourceWidgetUiBinder.class);

	interface ProfileUserTagsResourceWidgetUiBinder extends
			UiBinder<Widget, ProfileUserTagsResourceWidget> {
	}
	
	MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField Label resourceType,resourceSource;
	@UiField HTMLPanel ratingContainer;
	@UiField Image resourceImage,resourceTypeIcon;
	@UiField HTML resourceTitle;
	RatingWidgetView ratingWidgetView;
	private String resourceTypeImage;
	private UserTagsResourceDO userTagsResourceDO;
	List<UserTagsResourceDO> listOfTagsResource =new ArrayList<UserTagsResourceDO>();
	
	public ProfileUserTagsResourceWidget(UserTagsResourceDO userTagsResourceDO) {
		initWidget(uiBinder.createAndBindUi(this));
		this.userTagsResourceDO = userTagsResourceDO;
		setData(userTagsResourceDO);
		resourceImage.setWidth("80px");
		resourceImage.setHeight("60px");
		
	}

	
	public void setData(UserTagsResourceDO userTagsResourceDO)
	{
		resourceTitle.setHTML(removeHtmlTags(userTagsResourceDO.getTitle()));
		resourceTitle.getElement().setId("htmlResourceTitle");
		resourceTitle.getElement().setAttribute("alt",removeHtmlTags(userTagsResourceDO.getTitle()));
		resourceTitle.getElement().setAttribute("title",removeHtmlTags(userTagsResourceDO.getTitle()));
		
		resourceType.setText(userTagsResourceDO.getResourceFormat().getValue());
		resourceType.getElement().setId("lblResourceType");
		resourceType.getElement().setAttribute("alt",userTagsResourceDO.getResourceFormat().getValue());
		resourceType.getElement().setAttribute("title",userTagsResourceDO.getResourceFormat().getValue());
		
		resourceImage.getElement().setId("imgResourceImage");
		resourceTypeIcon.getElement().setId("imgResourceTypeIcon");
		resourceSource.getElement().setId("lblResourceSource");
		ratingContainer.getElement().setId("pnlRatingContainer");
		
		resourceTypeImage=userTagsResourceDO.getResourceFormat().getValue();
		setPublisger(userTagsResourceDO.getPublisher());
		if(userTagsResourceDO.getResourceFormat()!=null){
			setResourceTypeImage(userTagsResourceDO.getResourceFormat().getValue());
		}
		setAvgRatingWidget(userTagsResourceDO.getRatings().getCount(),userTagsResourceDO.getRatings().getAverage());
		
	}
	private void setAvgRatingWidget(int ratingCount,double averageRating) {
		ratingContainer.clear();
		ratingWidgetView=new RatingWidgetView();
		ratingWidgetView.getRatingCountOpenBrace().setText(i18n. GL_SPL_OPEN_SMALL_BRACKET());
		ratingWidgetView.getRatingCountLabel().setText(ratingCount+"");
		ratingWidgetView.getRatingCountCloseBrace().setText(i18n. GL_SPL_CLOSE_SMALL_BRACKET());
		ratingWidgetView.setAvgStarRating(averageRating);
		ratingContainer.add(ratingWidgetView);
	}
	public void setResourceTypeImage(String resourceType){
		if(resourceType!=null){
			resourceType=resourceType.toLowerCase();
			resourceTypeIcon.setStyleName(getResourceTypeImage(resourceType));
		}
	}

	private String removeHtmlTags(String html){
		html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
		return html;
	}

	public String getResourceTypeImage(String resourceType){
		
		if(resourceType.equalsIgnoreCase("Video")||resourceType.equalsIgnoreCase("Videos")){
			return PlayerBundle.INSTANCE.getPlayerStyle().videoResourceTypeInfoNew();
			
		}else if(resourceType.equalsIgnoreCase("Interactive")){
			
			return PlayerBundle.INSTANCE.getPlayerStyle().interactiveResourceTypeInfoNew();
		}
		else if(resourceType.equalsIgnoreCase("Website")||resourceType.equalsIgnoreCase("Webpage")){
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceTypeInfoNew();		
		}
		else if(resourceType.equalsIgnoreCase("Slide")){
			return PlayerBundle.INSTANCE.getPlayerStyle().imageResourceTypeInfoNew();
		}
		else if(resourceType.equalsIgnoreCase("Textbook")){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceTypeInfoNew();
		}
		else if(resourceType.equalsIgnoreCase("Question")){
			return PlayerBundle.INSTANCE.getPlayerStyle().questionResourceTypeInfoNew();
		}
		else if(resourceType.equalsIgnoreCase("lesson")){
			return PlayerBundle.INSTANCE.getPlayerStyle().lessonResourceTypeInfoNew();

		}else if(resourceType.equalsIgnoreCase("Handout")){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceTypeInfoNew();
		} else if(resourceType.equalsIgnoreCase("text")){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceTypeInfoNew();
		}
		else if(resourceType.equalsIgnoreCase("image")){
			return PlayerBundle.INSTANCE.getPlayerStyle().imageResourceTypeInfoNew();
		}
		else if(resourceType.equalsIgnoreCase("audio")){
			return PlayerBundle.INSTANCE.getPlayerStyle().audioResourceTypeInfoNew();
		}else if(resourceType.equalsIgnoreCase("exam")){
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceTypeInfoNew();
		}
		else {
			return PlayerBundle.INSTANCE.getPlayerStyle().otherResourceTypeInfoNew();
		}
	}
	public void setPublisger(List<String> publisher){
	if(publisher!=null){
		 String publisherValue= "";
		for (String publisherData: publisher) {
			if(publisher.size()>1){
				publisherValue = publisherValue+publisherData+",";
			}
			else
			{
				publisherValue = publisherData;
			}
		}
		if(publisherValue.endsWith(",")){
			publisherValue=publisherValue.substring(0, publisherValue.length()-1);
		}
		resourceSource.setText(publisherValue);
		resourceSource.getElement().setAttribute("alt",publisherValue);
		resourceSource.getElement().setAttribute("title",publisherValue);
	}
	}
	
	@Override
	public void onLoad(){
		resourceImage.setUrl(userTagsResourceDO.getThumbnails());
		if(userTagsResourceDO.getThumbnails()==null||userTagsResourceDO.getThumbnails().equalsIgnoreCase(""))
		{
			
			resourceImage.setUrl("images/resource_trans.png");
			resourceTypeImage=resourceTypeImage.toLowerCase();
			resourceImage.setStyleName(getResourceDefaultImage(resourceTypeImage));
		}
		resourceImage.addErrorHandler(new ErrorHandler() {
			
			@Override
			public void onError(ErrorEvent event) {
				resourceImage.setUrl("images/resource_trans.png");
				resourceTypeImage=resourceTypeImage.toLowerCase();
				resourceImage.setStyleName(getResourceDefaultImage(resourceTypeImage));
				
			}
		});
	}
	public String getResourceDefaultImage(String resourceType){
		
		if(resourceType.equalsIgnoreCase("Video")||resourceType.equalsIgnoreCase("Videos")){
			return PlayerBundle.INSTANCE.getPlayerStyle().videoResourceDefault();
		}else if(resourceType.equalsIgnoreCase("Interactive")){
		
			return PlayerBundle.INSTANCE.getPlayerStyle().interactiveResourceDefault();
		}
		else if(resourceType.equalsIgnoreCase("Website")||resourceType.equalsIgnoreCase("Exam")||resourceType.equalsIgnoreCase("Webpage")|| resourceType.equalsIgnoreCase("challenge")){
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceDefault();		
		}
		else if(resourceType.equalsIgnoreCase("Slide")||resourceType.equalsIgnoreCase("Image")){
			return PlayerBundle.INSTANCE.getPlayerStyle().imageResourceDefault();
		}
		else if(resourceType.equalsIgnoreCase("Textbook")||resourceType.equalsIgnoreCase("lesson")||resourceType.equalsIgnoreCase("Handout")||resourceType.equalsIgnoreCase("Text")){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceDefault();
		}
		else if(resourceType.equalsIgnoreCase("Question")){
			
			return PlayerBundle.INSTANCE.getPlayerStyle().questionResourceDefault();
		}
		else if(resourceType.equalsIgnoreCase("Audio")){
			return PlayerBundle.INSTANCE.getPlayerStyle().audioResourceDefault();
		}else if(resourceType.equalsIgnoreCase("Other")){
			return PlayerBundle.INSTANCE.getPlayerStyle().otherResourceDefault();
		}else {
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceDefault();
		}
	}
	
}
