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
package org.ednovo.gooru.client.mvp.play.collection.share;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.mvp.socialshare.SocialShareView;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.ResourceImageUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public abstract class SocialShareWidget extends Composite implements ClientConstants{

	private static SocialShareWidgetUiBinder uiBinder = GWT
			.create(SocialShareWidgetUiBinder.class);

	interface SocialShareWidgetUiBinder extends
			UiBinder<Widget, SocialShareWidget> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField Button fbButton,twitterButton,emailButton;
	@UiField Image resourceHiddenImage;

	private String resourceDescription=null,resourceThumbnail=null;

	private String category="";

	private CollectionItemDo collectionItemDo;

	public SocialShareWidget(String resourceDescription,String resourceThumbnail,String category) {
		initWidget(uiBinder.createAndBindUi(this));
		fbButton.setText(i18n.GL0646());
		fbButton.getElement().setId("btnFbButton");
		fbButton.getElement().setAttribute("alt",i18n.GL0646());
		fbButton.getElement().setAttribute("title",i18n.GL0646());

		twitterButton.setText(i18n.GL0647());
		twitterButton.getElement().setId("btnTwitterButton");
		twitterButton.getElement().setAttribute("alt",i18n.GL0647());
		twitterButton.getElement().setAttribute("title",i18n.GL0647());

		emailButton.setText(i18n.GL0212());
		emailButton.getElement().setId("btnEmailButton");
		emailButton.getElement().setAttribute("alt",i18n.GL0212());
		emailButton.getElement().setAttribute("title",i18n.GL0212());
		resourceHiddenImage.getElement().setId("imgResourceHiddenImage");
		this.resourceDescription=resourceDescription;
		this.resourceThumbnail=resourceThumbnail;
		this.category=category;
		if(COLLECTION.equals(category)){
		}
	}
	public SocialShareWidget(String resourceDescription,String resourceThumbnail,String category,CollectionItemDo collectionItemDo){
		this(resourceDescription,resourceThumbnail,collectionItemDo.getResource().getCategory());
		this.collectionItemDo=collectionItemDo;
	}
	public String getResourceDescription(){
		if(!StringUtil.isEmpty(resourceDescription)&&!resourceDescription.equals("null")){
			return this.resourceDescription;
		}else{
			return "";
		}
	}
	@Override
	public void onLoad(){
		if(COLLECTION.equals(category)){
			resourceHiddenImage.setUrl(resourceThumbnail);
		}else{
			if(ASSESSMENT_QUESTION.equalsIgnoreCase(collectionItemDo.getResource().getResourceType().getName())){
				resourceThumbnail=getQuestionImage();
				resourceHiddenImage.setUrl(resourceThumbnail);
			}else if(VIDEO_YOUTUBE.equalsIgnoreCase(collectionItemDo.getResource().getResourceType().getName())){
				resourceThumbnail=ResourceImageUtil.youtubeImageLink(ResourceImageUtil.getYoutubeVideoId(collectionItemDo.getResource().getUrl()),Window.Location.getProtocol());
				resourceHiddenImage.setUrl(resourceThumbnail);
			}else{
				resourceThumbnail=collectionItemDo.getResource().getThumbnails() != null ? collectionItemDo.getResource().getThumbnails().getUrl() : "";
				resourceHiddenImage.setUrl(resourceThumbnail);
			}
		}
	}
	private String getQuestionImage(){
		String thumbnailImage="";
		String assetName="";
		try{
			if(collectionItemDo.getResource().getAssets()!=null&&collectionItemDo.getResource().getAssets().size()>0){
				assetName=collectionItemDo.getResource().getAssets().get(0).getAsset()!=null?(collectionItemDo.getResource().getAssets().get(0).getAsset().getName()!=null?collectionItemDo.getResource().getAssets().get(0).getAsset().getName():""):"";
				thumbnailImage=collectionItemDo.getResource().getAssetURI()+collectionItemDo.getResource().getFolder()+assetName;
			}else if(collectionItemDo.getResource() != null && collectionItemDo.getResource().getThumbnails() != null && collectionItemDo.getResource().getThumbnails().getUrl() != null){
				thumbnailImage=collectionItemDo.getResource().getThumbnails() != null ? collectionItemDo.getResource().getThumbnails().getUrl() : "";
			}
		}catch(Exception e){
			AppClientFactory.printSevereLogger("SocialShareWidget : getQuestionImage : "+e.getMessage());
		}
		return thumbnailImage!=null?thumbnailImage:"";
	}
	public String getThumbnailUrl(){
		return resourceThumbnail;
	}

	@UiHandler("fbButton")
	public void onClickFbButton(ClickEvent clickEvent) {
		onFacebook();
	}

	@UiHandler("twitterButton")
	public void onClickTwitterButton(ClickEvent clickEvent) {
		onTwitter();
	}

	@UiHandler("emailButton")
	public void onClickEmailButton(ClickEvent clickEvent) {
		onEmail();
	}

	@UiHandler("resourceHiddenImage")
	public void setDefaultThumnialUrl(ErrorEvent event){
		if (COLLECTION.equalsIgnoreCase(category)) {
			resourceThumbnail=SocialShareView.DEFULT_IMAGE;
		} else if (VIDEO.equalsIgnoreCase(category)) {
			resourceThumbnail=SocialShareView.DEFULT_VIMAGE;
		} else if (QUESTION.equalsIgnoreCase(category)) {
			resourceThumbnail=SocialShareView.DEFULT_QIMAGE;
		} else if (INTERACTIVE.equalsIgnoreCase(category)) {
			resourceThumbnail=SocialShareView.DEFULT_IIMAGE;
		} else if (WEBSITE.equalsIgnoreCase(category)||EXAM.equalsIgnoreCase(category)||WEBPAGE.equalsIgnoreCase(category)) {
			resourceThumbnail=SocialShareView.DEFULT_WIMAGE;
		} else if (SLIDE.equalsIgnoreCase(category)||IMAGE.equalsIgnoreCase(category)){
			resourceThumbnail=SocialShareView.DEFULT_ITYPEIMAGE;
		} else if (TEXTBOOK.equalsIgnoreCase(category)||HANDOUT.equalsIgnoreCase(category)||LESSON.equalsIgnoreCase(category)||TEXT.equalsIgnoreCase(category)) {
			resourceThumbnail=SocialShareView.DEFULT_TEXTIMAGE;
		} else if (AUDIO.equalsIgnoreCase(category)) {
			resourceThumbnail=SocialShareView.DEFULT_AIMAGE;
		} else if (OTHER.equalsIgnoreCase(category)) {
			resourceThumbnail=SocialShareView.DEFULT_OIMAGE;
		}
		if(!resourceThumbnail.isEmpty()){
			resourceHiddenImage.setUrl(resourceThumbnail);
		}
		resourceThumbnail=resourceHiddenImage.getUrl();
	}
	public abstract void onFacebook();

	public abstract void onTwitter();

	public abstract void onEmail();
}
