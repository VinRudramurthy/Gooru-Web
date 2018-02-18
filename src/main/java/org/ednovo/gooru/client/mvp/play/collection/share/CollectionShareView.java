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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.share.email.CollectionEmailShareView;
import org.ednovo.gooru.client.mvp.play.collection.share.email.SentEmailSuccessVc;
import org.ednovo.gooru.client.mvp.socialshare.SocialShareView;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.shared.util.ClientConstants;
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
public class CollectionShareView extends BaseViewWithHandlers<CollectionShareUiHandlers> implements IsCollectionShareView,ClientConstants{


	private static CollectionShareViewUiBinder uiBinder = GWT.create(CollectionShareViewUiBinder.class);

	interface CollectionShareViewUiBinder extends UiBinder<Widget, CollectionShareView> {

	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField HTMLPanel sharePanel,shareMainTitle;

	@UiField TextArea resourceShareTextArea,collectionShareTextArea;
	
	@UiField FlowPanel socialSharePanel,collectionShareContainer;

	@UiField InlineLabel embedLink,bitlyLink;
	
	@UiField Label resourceTitleText,collectionShareText,hideText,shareText;
	
	@UiField HTMLEventPanel hideButton;
	
	private boolean isResourceView=false;

	private String embedurl, bitlyUrl, originalUrl,embededIframeURL,resourceShareUrl;
	
	private String shareUrl="";
	
	private String shareBitlyUrl="";
	
	private String embedBitlyUrl="";

	private CollectionEmailShareView emailShareView=null;
	
	private Map<String, String> collectionShareMap=null;
	
	private Map<String, String> resourceShareMap=null;
	
	String resourceTitle="";
	String resourceDesc="";
	String resourceCategory="";
	String resourceGooruId="";
	String resoruceItemId="";

	@Inject
	public CollectionShareView(){
		setWidget(uiBinder.createAndBindUi(this));		
		resourceShareTextArea.setReadOnly(true);
		resourceShareTextArea.getElement().setAttribute("readOnly", "");
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
		
		collectionShareText.setText(i18n.GL0645());
		collectionShareText.getElement().setId("lblCollectionShareText");
		collectionShareText.getElement().setAttribute("alt",i18n.GL0645());
		collectionShareText.getElement().setAttribute("title",i18n.GL0645());
		
		bitlyLink.getElement().setInnerHTML(i18n.GL0639());
		bitlyLink.getElement().setAttribute("alt",i18n.GL0639());
		bitlyLink.getElement().setAttribute("title",i18n.GL0639());
		
		embedLink.getElement().setInnerHTML(i18n.GL0640());
		embedLink.getElement().setAttribute("alt",i18n.GL0640());
		embedLink.getElement().setAttribute("title",i18n.GL0640());
		
		hideText.setText(i18n.GL0592());
		hideText.getElement().setId("lblHideText");
		hideText.getElement().setAttribute("alt",i18n.GL0592());
		hideText.getElement().setAttribute("title",i18n.GL0592());
		
		shareText.setText(i18n.GL0638());
		shareText.getElement().setId("lblShareText");
		shareText.getElement().setAttribute("alt",i18n.GL0638());
		shareText.getElement().setAttribute("title",i18n.GL0638());
		
		resourceTitleText.getElement().setId("lblResourceTitleText");
		resourceShareTextArea.getElement().setId("tatResourceShareTextArea");
		StringUtil.setAttributes(resourceShareTextArea, true);
		socialSharePanel.getElement().setId("fpnlSocialSharePanel");
		sharePanel.getElement().setId("fpnlSharePanel");
		collectionShareContainer.getElement().setId("fpnlCollectionShareContainer");
		collectionShareTextArea.getElement().setId("tatCollectionShareTextArea");
		StringUtil.setAttributes(collectionShareTextArea, true);
		hideButton.getElement().setId("epnlHideButton");
	}
	
	public void showShareView(boolean isResourceView){
		this.isResourceView=isResourceView;
		collectionShareTextArea.setValue("");
		if(isResourceView){
			socialSharePanel.setVisible(true);
			collectionShareContainer.setVisible(false);
		}else{
			resourceTitleText.setText(i18n.GL0645());
			resourceTitleText.getElement().setAttribute("alt",i18n.GL0645());
			resourceTitleText.getElement().setAttribute("title",i18n.GL0645());
			collectionShareContainer.setVisible(false);
			socialSharePanel.setVisible(true);
		}	
	}
	
	public void setCollectionShareData(Map<String, String> shareUrlsList){
		isResourceView=false;
		collectionShareMap=new HashMap<String,String>();
		if(shareUrlsList!=null){
			shareBitlyUrl = shareUrlsList.get(ClientConstants.SHORTENURL).toString();
			shareUrl= shareUrlsList.get(ClientConstants.DECODERAWURL).toString();
			originalUrl=shareUrlsList.get(ClientConstants.RAWURL).toString();
			embedurl=shareUrlsList.get(ClientConstants.EMBEDURLRAWURL).toString();
			setIframeUrl(embedurl);		
		}
		collectionShareMap.put(i18n.GL0643(), shareUrl);
		collectionShareMap.put(i18n.GL0639(), shareBitlyUrl);
		embedLink.setText(i18n.GL0640());
		embedLink.getElement().setAttribute("alt",i18n.GL0640());
		embedLink.getElement().setAttribute("title",i18n.GL0640());
		
		bitlyLink.setText(i18n.GL0639());
		bitlyLink.getElement().setAttribute("alt",i18n.GL0639());
		bitlyLink.getElement().setAttribute("title",i18n.GL0639());
		
		resourceShareTextArea.setText(shareUrl);
		resourceShareTextArea.getElement().setAttribute("alt",shareUrl);
		resourceShareTextArea.getElement().setAttribute("title",shareUrl);
	}
	
	public void setResourceShareData(Map<String, String> shareUrlsList){
		isResourceView=true;
		resourceShareTextArea.setValue(shareUrl);
		resourceShareMap=new HashMap<String,String>();
		if(shareUrlsList!=null){
			String resourceShareBitlyUrl = shareUrlsList.get(ClientConstants.SHORTENURL).toString();
			resourceShareUrl= shareUrlsList.get(ClientConstants.DECODERAWURL).toString();
			setResourceIframeUrl(resourceShareUrl);	
			resourceShareMap.put(i18n.GL0643(), resourceShareUrl);
			resourceShareMap.put(i18n.GL0639(), resourceShareBitlyUrl);
			resourceShareTextArea.setText(resourceShareUrl);
			resourceShareTextArea.getElement().setAttribute("alt",resourceShareUrl);
			resourceShareTextArea.getElement().setAttribute("title",resourceShareUrl);
		}
		embedLink.setText(i18n.GL0640());
		embedLink.getElement().setAttribute("alt",i18n.GL0640());
		embedLink.getElement().setAttribute("title",i18n.GL0640());
		bitlyLink.setText(i18n.GL0639());
		bitlyLink.getElement().setAttribute("alt",i18n.GL0639());
		bitlyLink.getElement().setAttribute("title",i18n.GL0639());
	}
	public void setCollectionShareData(){
		embedLink.setText(i18n.GL0640());
		embedLink.getElement().setAttribute("alt",i18n.GL0640());
		embedLink.getElement().setAttribute("title",i18n.GL0640());
		bitlyLink.setText(i18n.GL0639());
		bitlyLink.getElement().setAttribute("alt",i18n.GL0639());
		bitlyLink.getElement().setAttribute("title",i18n.GL0639());
		if(isResourceView){
			collectionShareTextArea.setText(shareUrl);
			collectionShareTextArea.getElement().setAttribute("alt",shareUrl);
			collectionShareTextArea.getElement().setAttribute("title",shareUrl);
		}else{
			resourceShareTextArea.setText(shareUrl);
			resourceShareTextArea.getElement().setAttribute("alt",shareUrl);
			resourceShareTextArea.getElement().setAttribute("title",shareUrl);
		}
	}
	
	public void setResourceShareData(){
		resourceShareTextArea.setValue("");
		String resourceShareUrl=resourceShareMap.get(i18n.GL0643());
		resourceShareTextArea.setText(resourceShareUrl);
		resourceShareTextArea.getElement().setAttribute("alt",resourceShareUrl);
		resourceShareTextArea.getElement().setAttribute("title",resourceShareUrl);
	}
	
	@Override
	public void setData(final CollectionDo collectionDo) {
		sharePanel.clear();
		SocialShareWidget swidget=null;
		if(collectionDo!=null){
			  final String collectionTitle=collectionDo.getTitle()!=null?collectionDo.getTitle():"";
			  String collectionImg=collectionDo.getThumbnails()!=null?(collectionDo.getThumbnails().getUrl()!=null?collectionDo.getThumbnails().getUrl():""):"";
			  swidget= new SocialShareWidget(collectionDo.getGoals()!=null?collectionDo.getGoals():"",collectionImg,ClientConstants.COLLECTION) {

				@Override
				public void onTwitter() {
					Window.open(ClientConstants.TWITTERURL + i18n.GL1439()+" "+collectionTitle.replaceAll("\\+", "%2B") +": " + shareBitlyUrl, "_blank", "width=600,height=300");
				}
				
				@Override
				public void onFacebook() {
					 SocialShareView.postOnFacebook(collectionTitle,SocialShareView.getEncodedUrl(shareUrl),getResourceDescription(),getThumbnailUrl());
				}

				@Override
				public void onEmail() {
					String emailSubject=i18n.GL1439()+" "+collectionTitle;
					String emailDescription=collectionTitle+"<div><br/></div><div>"+shareUrl+"</div><div><br/></div><div>"+i18n.GL1440()+" "+AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()+" "+i18n.GL1441()+"</div>";
					 emailShareView=new CollectionEmailShareView(emailSubject, emailDescription){
						@Override
						public void sendEmail(String fromEmail, String toEmail,String copyEmail, String subject, String message) {
							getUiHandlers().sendEmail( fromEmail,  toEmail, copyEmail,  subject,  message);
						}
						@Override
						public void closeEmailPopup() {
						}
					};
					emailShareView.show();
				}
			};
			sharePanel.add(swidget);
		}
	}
	
	public void setIframeUrl(String iframeBitlyUrl){
		embedBitlyUrl = "<iframe width=\"1024px\" height=\"768px\" src=\"" + iframeBitlyUrl + "\" frameborder=\"0\" ></iframe>";
		collectionShareMap.put(i18n.GL0640(), embedBitlyUrl);
	}
	public void setResourceIframeUrl(String iframeBitlyUrl){
		String resourceEmbedBitlyUrl = "<iframe width=\"1024px\" height=\"768px\" src=\"" + iframeBitlyUrl + "\" frameborder=\"0\" ></iframe>";
		resourceShareMap.put(i18n.GL0640(), resourceEmbedBitlyUrl);
	}
	
	@Override
	public void setIframeText(Map<String, String> embedLink) {
		String iframeText;
		if (embedLink != null && embedLink.containsKey(ClientConstants.SHORTENURL)) {
			embedurl = embedLink.get(ClientConstants.DECODERAWURL).toString();
			iframeText = "<iframe width=\"1024px\" height=\"768px\" src=\"" + embedurl + "\" frameborder=\"0\" ></iframe>";
			collectionShareMap.put(i18n.GL0640(), iframeText);
		}
	}

	@UiHandler("embedLink")
	public void onClickEmbedLink(ClickEvent clickEvent){
		String shareTxt=resourceShareTextArea.getText();
		String embed=embedLink.getText();
		Iterator<String> keyIterator =null;
		if(isResourceView){
			keyIterator=resourceShareMap.keySet().iterator();
		}else{
			keyIterator=collectionShareMap.keySet().iterator();
		}
		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			String value= isResourceView?resourceShareMap.get(key):collectionShareMap.get(key);
			if(shareTxt.equalsIgnoreCase(value)){
				embedLink.setText(key);
				embedLink.getElement().setAttribute("alt",key);
				embedLink.getElement().setAttribute("title",key);
				if(isResourceView){
					resourceShareTextArea.setText(resourceShareMap.get(embed));
					resourceShareTextArea.getElement().setAttribute("alt",resourceShareMap.get(embed));
					resourceShareTextArea.getElement().setAttribute("title",resourceShareMap.get(embed));
				}else{
					resourceShareTextArea.setText(collectionShareMap.get(embed));
					resourceShareTextArea.getElement().setAttribute("alt",resourceShareMap.get(embed));
					resourceShareTextArea.getElement().setAttribute("title",resourceShareMap.get(embed));
				}
			}
		}
	}
	
	@UiHandler("resourceShareTextArea")
	public void onClickresourceShareTextArea(ClickEvent clickEvent){
		resourceShareTextArea.selectAll();
		resourceShareTextArea.setFocus(true);
	}

	@UiHandler("bitlyLink")
	public void onClickBitlyLink(ClickEvent clickEvent){
		String shareTxt=resourceShareTextArea.getText();
		String linkUrl=bitlyLink.getText();
		Iterator<String> keyIterator =null;
		if(isResourceView){
			keyIterator=resourceShareMap.keySet().iterator();
		}else{
			keyIterator=collectionShareMap.keySet().iterator();
		}
		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			String value= isResourceView?resourceShareMap.get(key):collectionShareMap.get(key);
			if(shareTxt.equalsIgnoreCase(value)){
				bitlyLink.setText(key);
				bitlyLink.getElement().setAttribute("alt",key);
				bitlyLink.getElement().setAttribute("title",key);
				if(isResourceView){
					resourceShareTextArea.setText(resourceShareMap.get(linkUrl));
					resourceShareTextArea.getElement().setAttribute("alt",resourceShareMap.get(linkUrl));
					resourceShareTextArea.getElement().setAttribute("title",resourceShareMap.get(linkUrl));
				}else{
					resourceShareTextArea.setText(collectionShareMap.get(linkUrl));
					resourceShareTextArea.getElement().setAttribute("alt",resourceShareMap.get(linkUrl));
					resourceShareTextArea.getElement().setAttribute("title",resourceShareMap.get(linkUrl));
				}
			}
		}
	}
	@Override
	public void hideSendEmailPopup(String toEmail){
		emailShareView.hide();
		new SentEmailSuccessVc(toEmail);
	}	
	
	/**
	 * 
	 * @function onhideBtnClicked 
	 * 
	 * @created_date : 11-Dec-2013
	 * 
	 * @description
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
		params.put("id", collectionId);
		params.put("rid", collectionItemId);
		PlaceRequest request=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
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
		params.put("id", collectionId);
		PlaceRequest request=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		params.put("id", collectionId);
		params.put("view", "end");
		PlaceRequest request=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		params.put("view", "end");
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	}
	
	public String removeHtmlTags(String content){
		return content.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.play.collection.share.IsCollectionShareView#setEmbedurl(java.lang.String)
	 */
	@Override
	public void setEmbedurl(String embedurl) {
		this.embedurl=embedurl;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.play.collection.share.IsCollectionShareView#setEmbededIframeURL(java.lang.String)
	 */
	@Override
	public void setEmbededIframeURL(String embededIframeURL) {
		this.embededIframeURL=embededIframeURL;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.play.collection.share.IsCollectionShareView#showResourceData(org.ednovo.gooru.shared.model.content.CollectionItemDo)
	 */
	@Override
	public void showResourceData(final CollectionItemDo collectionItemDo) {
		if(collectionItemDo!=null){
			sharePanel.clear();
			if(collectionItemDo.getResource()!=null){
				resourceTitle=collectionItemDo.getResource().getTitle()!=null?collectionItemDo.getResource().getTitle():"";
			    resourceDesc=collectionItemDo.getResource()!=null?(collectionItemDo.getResource().getDescription()!=null?collectionItemDo.getResource().getDescription():""):"";
				resourceCategory=collectionItemDo.getCategory()!=null?collectionItemDo.getCategory():"";
				resourceGooruId=collectionItemDo.getResource().getGooruOid()!=null?collectionItemDo.getResource().getGooruOid():"";
				resoruceItemId=collectionItemDo.getCollectionItemId()!=null?collectionItemDo.getCollectionItemId():"";
			}
			String resourceImageUrl=collectionItemDo.getResource()!=null?(collectionItemDo.getResource().getThumbnails()!=null?(collectionItemDo.getResource().getThumbnails().getUrl()!=null?collectionItemDo.getResource().getThumbnails().getUrl():""):""):"";
			SocialShareWidget swidget= new SocialShareWidget(resourceDesc,resourceImageUrl,resourceCategory,collectionItemDo) {
				@Override
				public void onTwitter() {
					getUiHandlers().triggerShareDatalogEvent(resourceGooruId,resoruceItemId,PlayerDataLogEvents.RESOURCE,PlayerDataLogEvents.TWITTER,false);
					Window.open(ClientConstants.TWITTERURL + i18n.GL1439()+" "+removeHtmlTags(resourceTitle).replaceAll("\\+", "%2B") +": " + SocialShareView.getEncodedUrl(resourceShareUrl), "_blank", "width=600,height=300");
				}
				@Override
				public void onFacebook() {
					getUiHandlers().triggerShareDatalogEvent(resourceGooruId,resoruceItemId,PlayerDataLogEvents.RESOURCE,PlayerDataLogEvents.FACEBOOK,false);
					SocialShareView.postOnFacebook(removeHtmlTags(resourceTitle),SocialShareView.getEncodedUrl(resourceShareUrl),getResourceDescription(),getThumbnailUrl());
				}
				/**
				 * used in preview player resource share.
				 */
				@Override
				public void onEmail() {
					String emailSubject=removeHtmlTags(resourceTitle);
					String emailDescription = StringUtil.generateMessage(i18n.GL1999(),AppClientFactory.getLoggedInUser().getUsername(),i18n.GL2000(),resourceTitle,resourceShareUrl,AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint());
					emailShareView=new CollectionEmailShareView(emailSubject, emailDescription){
						@Override
						public void sendEmail(String fromEmail, String toEmail,String copyEmail, String subject, String message) {
							getUiHandlers().triggerShareDatalogEvent(resourceGooruId,collectionItemDo.getCollectionItemId(),PlayerDataLogEvents.RESOURCE,PlayerDataLogEvents.MAIL,true);
							getUiHandlers().sendEmail( fromEmail,  toEmail, copyEmail,  subject,  message);
						}
						@Override
						public void closeEmailPopup(){
							getUiHandlers().triggerShareDatalogEvent(resourceGooruId,resoruceItemId,PlayerDataLogEvents.RESOURCE,PlayerDataLogEvents.MAIL,false);
						}
					};
					emailShareView.show();
				}
			};
			sharePanel.add(swidget);
		}
	}
}
