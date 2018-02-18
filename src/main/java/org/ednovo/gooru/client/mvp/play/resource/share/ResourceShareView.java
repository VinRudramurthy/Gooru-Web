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
package org.ednovo.gooru.client.mvp.play.resource.share;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.share.SocialShareWidget;
import org.ednovo.gooru.client.mvp.play.collection.share.email.CollectionEmailShareView;
import org.ednovo.gooru.client.mvp.play.collection.share.email.SentEmailSuccessVc;
import org.ednovo.gooru.client.mvp.socialshare.SocialShareView;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class ResourceShareView extends BaseViewWithHandlers<ResourceShareUiHandlers> implements IsResourceShareView{


	private static ResourceShareViewUiBinder uiBinder = GWT.create(ResourceShareViewUiBinder.class);

	 interface ResourceShareViewUiBinder extends UiBinder<Widget, ResourceShareView> {

	 }
	 
	 private MessageProperties i18n = GWT.create(MessageProperties.class);

		@UiField HTMLPanel sharePanel,shareMainTitle;

		@UiField TextArea resourceShareTextArea;
		
		@UiField FlowPanel socialSharePanel,resourceSharePanel;

		@UiField InlineLabel embedLink,bitlyLink;
		
		@UiField Label resourceTitleText,hideText;
		
		@UiField HTMLEventPanel hideButton;

		private String shareUrl="";
		
		private String originalUrl="";
		
		private String shareBitlyUrl="";

		private CollectionEmailShareView emailShareView=null;

		private Map<String, String> resourceShareMap=null;

	@Inject
	public ResourceShareView(){
		setWidget(uiBinder.createAndBindUi(this));
		resourceShareTextArea.setReadOnly(true);
		resourceShareTextArea.getElement().setAttribute("readOnly", "");
		resourceSharePanel.getElement().setId("resourceShareMainPanel");
		embedLink.setText(i18n.GL0640());
		embedLink.getElement().setId("spnEmbedLink");
		embedLink.getElement().setAttribute("alt",i18n.GL0640());
		embedLink.getElement().setAttribute("title",i18n.GL0640());
		
		bitlyLink.setText(i18n.GL0639());
		bitlyLink.getElement().setId("spnBitlyLink");
		bitlyLink.getElement().setAttribute("alt",i18n.GL0639());
		bitlyLink.getElement().setAttribute("title",i18n.GL0639());
		
		shareMainTitle.getElement().setInnerHTML(i18n.GL0644());
		shareMainTitle.getElement().setId("pnlShareMainTitle");
		shareMainTitle.getElement().setAttribute("alt",i18n.GL0644());
		shareMainTitle.getElement().setAttribute("title",i18n.GL0644());
		
		bitlyLink.getElement().setInnerHTML(i18n.GL0639());
		bitlyLink.getElement().setId("spnBitlyLink");
		bitlyLink.getElement().setAttribute("alt",i18n.GL0639());
		bitlyLink.getElement().setAttribute("title",i18n.GL0639());
		
		embedLink.getElement().setInnerHTML(i18n.GL0640());
		embedLink.getElement().setAttribute("alt",i18n.GL0640());
		embedLink.getElement().setAttribute("title",i18n.GL0640());
		
		hideText.setText(i18n.GL0592());
		hideText.getElement().setId("lblHideText");
		hideText.getElement().setAttribute("alt",i18n.GL0592());
		hideText.getElement().setAttribute("title",i18n.GL0592());
		
		resourceTitleText.getElement().setId("lblResourceTitleText");
		resourceShareTextArea.getElement().setId("tatResourceShareTextArea");
		StringUtil.setAttributes(resourceShareTextArea, true);
		socialSharePanel.getElement().setId("fpnlSocialSharePanel");
		sharePanel.getElement().setId("pnlSharePanel");
		hideButton.getElement().setId("epnlHideButton");
	}
	
	public void setResourceShareData(Map<String, String> shareUrlsList){
		if(shareUrlsList.size()>0){
		resourceShareMap=new HashMap<String,String>();
		if(shareUrlsList.containsKey("decodeRawUrl")){
		shareUrl= shareUrlsList.get("decodeRawUrl").toString();
		}
		if(shareUrlsList.containsKey("rawUrl")){
		originalUrl=shareUrlsList.get("rawUrl").toString();
		}
		setResourceIframeUrl(shareUrl);
		if(shareUrlsList.containsKey("shortenUrl")){
		shareBitlyUrl = shareUrlsList.get("shortenUrl").toString();
		}
		resourceShareMap.put(i18n.GL0643(), shareUrl);
		resourceShareMap.put(i18n.GL0639(), shareBitlyUrl);
		resourceShareTextArea.setText(shareUrl);
		resourceShareTextArea.getElement().setAttribute("alt",shareUrl);
		resourceShareTextArea.getElement().setAttribute("title",shareUrl);
		embedLink.setText(i18n.GL0640());
		embedLink.getElement().setAttribute("alt",i18n.GL0640());
		embedLink.getElement().setAttribute("title",i18n.GL0640());
		bitlyLink.setText(i18n.GL0639());
		bitlyLink.getElement().setAttribute("alt",i18n.GL0639());
		bitlyLink.getElement().setAttribute("title",i18n.GL0639());
		}
	}
	
	public void setResourceShareData(){
		String resourceShareUrl=resourceShareMap.get(i18n.GL0643());
		resourceShareTextArea.setText(resourceShareUrl);
		resourceShareTextArea.getElement().setAttribute("alt",resourceShareUrl);
		resourceShareTextArea.getElement().setAttribute("title",resourceShareUrl);
	}
	public String removeHtmlTags(String content){
		return content.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
	}
	
	@Override
	public void setData(final CollectionItemDo collectionItemDo) {
		sharePanel.clear();
		SocialShareWidget swidget= new SocialShareWidget(collectionItemDo.getResource().getDescription(),collectionItemDo.getResource().getThumbnails().getUrl(),collectionItemDo.getCategory(),collectionItemDo) {
			@Override
			public void onTwitter() {
				getUiHandlers().triggerShareDataLogEvent(collectionItemDo.getResource().getGooruOid(),PlayerDataLogEvents.RESOURCE,PlayerDataLogEvents.TWITTER,false);
				Window.open("http://twitter.com/intent/tweet?text=" + i18n.GL1439()+" "+removeHtmlTags(collectionItemDo.getResource().getTitle()).replaceAll("\\+", "%2B") +": " + shareBitlyUrl, "_blank", "width=600,height=300");
			}
			
			@Override
			public void onFacebook() {
				getUiHandlers().triggerShareDataLogEvent(collectionItemDo.getResource().getGooruOid(),PlayerDataLogEvents.RESOURCE,PlayerDataLogEvents.FACEBOOK,false);
				SocialShareView.postOnFacebook(removeHtmlTags(collectionItemDo.getResource().getTitle()),originalUrl,getResourceDescription(),getThumbnailUrl());
			}

			/**
			 * This method is used in resource player share.
			 */
			@Override
			public void onEmail() {
				String emailSubject=i18n.GL1439()+" "+collectionItemDo.getResource().getTitle();
				String emailDescription= removeHtmlTags(collectionItemDo.getResource().getTitle())+"<div><br/></div><div>"+shareBitlyUrl+"</div><div><br/></div><div>"+i18n.GL1440()+" "+AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()+" "+i18n.GL1441()+"</div>";
				 emailShareView=new CollectionEmailShareView(emailSubject, emailDescription){
					@Override
					public void sendEmail(String fromEmail, String toEmail,
							String copyEmail, String subject, String message) {
						getUiHandlers().sendEmail( fromEmail,  toEmail, copyEmail,  subject,  message);
						getUiHandlers().triggerShareDataLogEvent(collectionItemDo.getResource().getGooruOid(),PlayerDataLogEvents.RESOURCE,PlayerDataLogEvents.MAIL,true);
					}

					@Override
					public void closeEmailPopup() {
						getUiHandlers().triggerShareDataLogEvent(collectionItemDo.getResource().getGooruOid(),PlayerDataLogEvents.RESOURCE,PlayerDataLogEvents.MAIL,false);
					}
				};
				emailShareView.show();
			}
		};

		sharePanel.add(swidget);
	}


	public void setResourceIframeUrl(String iframeBitlyUrl){
		String resourceEmbedBitlyUrl = "<iframe width=\"1024px\" height=\"768px\" src=\"" + iframeBitlyUrl + "\" frameborder=\"0\" ></iframe>";
		resourceShareMap.put(i18n.GL0640(), resourceEmbedBitlyUrl);
	}
	

	@UiHandler("embedLink")
	public void onClickEmbedLink(ClickEvent clickEvent){
		String shareTxt=resourceShareTextArea.getText();
		String embed=embedLink.getText();
		Iterator<String> keyIterator = resourceShareMap.keySet().iterator();
		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			String value= resourceShareMap.get(key);
			if(shareTxt.equalsIgnoreCase(value)){
				embedLink.setText(key);
				embedLink.getElement().setAttribute("alt",key);
				embedLink.getElement().setAttribute("title",key);
				resourceShareTextArea.setText(resourceShareMap.get(embed));
				resourceShareTextArea.getElement().setAttribute("alt",resourceShareMap.get(embed));
				resourceShareTextArea.getElement().setAttribute("title",resourceShareMap.get(embed));
			}
		}

	}

	@UiHandler("bitlyLink")
	public void onClickBitlyLink(ClickEvent clickEvent){
		String shareTxt=resourceShareTextArea.getText();
		String linkUrl=bitlyLink.getText();
		Iterator<String> keyIterator = resourceShareMap.keySet().iterator();
		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			String value= resourceShareMap.get(key);
			if(shareTxt.equalsIgnoreCase(value)){
				bitlyLink.setText(key);
				bitlyLink.getElement().setAttribute("alt",key);
				bitlyLink.getElement().setAttribute("title",key);
				resourceShareTextArea.setText(resourceShareMap.get(linkUrl));
				resourceShareTextArea.getElement().setAttribute("alt",resourceShareMap.get(linkUrl));
				resourceShareTextArea.getElement().setAttribute("title",resourceShareMap.get(linkUrl));
			}
		}
	}
	@Override
	public void hideSendEmailPopup(String toEmail){
		emailShareView.hide();
		new SentEmailSuccessVc(toEmail);
	}
	
	@UiHandler("resourceShareTextArea")
	public void onClickresourceShareTextArea(ClickEvent clickEvent){
	
		resourceShareTextArea.selectAll();
		resourceShareTextArea.setFocus(true);
	}
	
	/**
	 * 
	 * @function onhideBtnClicked 
	 * 
	 * @created_date : 11-Dec-2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param clickEvent
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("hideButton")
	public void onhideBtnClicked(ClickEvent clickEvent) 
	{
		PlaceRequest collectionRequest = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
		String collectionId = collectionRequest.getParameter("id", null);
		String collectionItemId = collectionRequest.getParameter("rid", null);
		String chkViewParam = collectionRequest.getParameter("view", null);
		
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionId);
		params = PreviewPlayerPresenter.setConceptPlayerParameters(params);

		
	if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.RESOURCE_PLAY))
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.RESOURCE_PLAY).
				with("id", collectionId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam == null && collectionItemId != null)
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.COLLECTION_PLAY).
				with("id", collectionId).with("rid", collectionItemId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId != null)
	{
		params.put("rid", collectionItemId);
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.COLLECTION_PLAY).
				with("id", collectionId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.COLLECTION_PLAY).
				with("id", collectionId).with("view", "end");
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		params.put("view", "end");
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	}
}
