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

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.folder.FolderTocDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.util.ImageUtil;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.ResourceImageUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author Search Team
 *
 */
public class ResourceImageUc extends Composite implements ClickHandler {

	private static ResourceImageUcUiBinder uiBinder = GWT.create(ResourceImageUcUiBinder.class);

	interface ResourceImageUcUiBinder extends UiBinder<Widget, ResourceImageUc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	Label resourceType;

	@UiField
	Image image;

	@UiField
	FlowPanel resourceThumbnail;

	private String resourceId;

	private String collectionId;

	private String playerName;

	private String narration;

	private String profilePageMoreInfoCollectionId;

	private static final String DEFULT_IMAGE_PREFIX = "images/default-";

	private static final String NULL = "null";

	String selectedFolderId = "";

//	private static final String DEFAULT_THUMBNAIL = "slides/thumbnail.jpg";

	private boolean failedThumbnailGeneration = false;

	/**
	 * Class constructor
	 */
//	private static final String SMALL = i18n.GL0900;

//	private static final String PNG = i18n.GL0899;

	boolean suggestFlag;
	/**
	 *
	 */
	public ResourceImageUc() {
		initWidget(uiBinder.createAndBindUi(this));
		resourceThumbnail.getElement().setId("fpnlResourceThumbnail");
		image.getElement().setId("imgImage");
		resourceType.getElement().setId("lblResourceType");
		addDomHandler(this, ClickEvent.getType());
		suggestFlag=false;
	}

	/**
	 * Class constructor with multi parameter
	 *
	 * @param category
	 *            of the resource
	 * @param thumbnailUrl
	 *            of the image
	 * @param gooruOid
	 *            of the resource
	 * @param playerName
	 */
	public ResourceImageUc(String category, String thumbnailUrl, String realUrl, String gooruOid, String playerName, String title, boolean generateYoutube, String collectionId) {
		this();
		renderSearch(category, thumbnailUrl, realUrl, gooruOid, playerName, title, generateYoutube,collectionId);
	}

	/**
	 * Set resource image info
	 *
	 * @param category
	 *            of the resource
	 * @param thumbnailUrl
	 *            of the image
	 * @param gooruOid
	 *            of the resource
	 * @param playerName
	 */
	public void renderSearch(String category, String thumbnailUrl, String realUrl, String gooruOid, String playerName, String title, boolean generateYoutube, String collectionId) {
		final String categoryString = category == null || category.startsWith("assessment") ? ImageUtil.QUESTION : category.toLowerCase();
		resourceType.addStyleName(categoryString.trim() + i18n.GL0900());
		setUrl(thumbnailUrl, realUrl, categoryString.trim(), title, generateYoutube);
		setResourceId(gooruOid);
		setPlayerName(playerName);
		if(!AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)){
			if(!collectionId.equalsIgnoreCase("") || !collectionId.isEmpty())
			{
				setCollectionId(collectionId);
			}
		}


	}
	/**
	 *
	 * @function renderSearch
	 *
	 * @created_date : 23-Jan-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param category
	 * @parm(s) : @param thumbnailUrl
	 * @parm(s) : @param realUrl
	 * @parm(s) : @param gooruOid
	 * @parm(s) : @param playerName
	 * @parm(s) : @param title
	 * @parm(s) : @param generateYoutube
	 * @parm(s) : @param collectionId
	 * @parm(s) : @param suggestedtype
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void renderSearch(String category, String thumbnailUrl, String realUrl, String gooruOid, String playerName, String title, boolean generateYoutube, String collectionId, String suggestedtype) {
			final String categoryString = category == null || category.startsWith("assessment") ? ImageUtil.QUESTION : category.toLowerCase();
		resourceType.addStyleName(categoryString.trim() + i18n.GL0900());
		suggestFlag=true;
		setUrl(thumbnailUrl, realUrl, categoryString.trim(), title, generateYoutube);
		setResourceId(gooruOid);
		setPlayerName(playerName);
		if(!AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)){
			if(!collectionId.equalsIgnoreCase("") || !collectionId.isEmpty())
			{
				setCollectionId(collectionId);
			}
		}
	}
	/**
	 *
	 * @function renderSearch
	 *
	 * @created_date : 23-Jan-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param category
	 * @parm(s) : @param thumbnailUrl
	 * @parm(s) : @param realUrl
	 * @parm(s) : @param collectionItemId
	 * @parm(s) : @param title
	 * @parm(s) : @param youtube
	 * @parm(s) : @param narration
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void renderSearch(String category, String thumbnailUrl, String realUrl,String collectionItemId,String title, boolean youtube, String narration) {
		String categoryString = category == null || category.startsWith("assessment") ? ImageUtil.QUESTION : category.toLowerCase();
		String cssName = categoryString.trim() + i18n.GL0900();
		resourceType.addStyleName(cssName);
		setUrl(thumbnailUrl, realUrl, categoryString.trim(), title, youtube);
		setResourceId(collectionItemId);
		setNarration(narration);
	}
	/**
	 *
	 * @function renderSearch
	 *
	 * @created_date : 23-Jan-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param category
	 * @parm(s) : @param thumbnailUrl
	 * @parm(s) : @param realUrl
	 * @parm(s) : @param collectionItemId
	 * @parm(s) : @param title
	 * @parm(s) : @param youtube
	 * @parm(s) : @param narration
	 * @parm(s) : @param collectionId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void renderSearch(String category, String thumbnailUrl, String realUrl,String collectionItemId, String title, boolean youtube,String narration, String collectionId) {
		final String categoryString = category == null || category.startsWith("assessment") ? ImageUtil.QUESTION : category.toLowerCase();
		resourceType.addStyleName(categoryString.trim() + i18n.GL0900());
		setUrl(thumbnailUrl, realUrl, categoryString.trim(), title, youtube);
		setResourceId(collectionItemId);
		setNarration(narration);
		setProfilePageMoreInfoCollectionId(collectionId);
	}
	/**
	 *
	 * @function setUrl
	 *
	 * @created_date : 23-Jan-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param thumbnailUrl
	 * @parm(s) : @param realUrl
	 * @parm(s) : @param category
	 * @parm(s) : @param title
	 * @parm(s) : @param generateYoutube
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setUrl(final String thumbnailUrl, final String realUrl, final String category, final String title, final boolean generateYoutube) {
		failedThumbnailGeneration = false;
		final String categoryString = category == null || category.startsWith("assessment") ? ImageUtil.QUESTION : category;
		image.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				setDefaultThumbnail(thumbnailUrl, realUrl, categoryString, generateYoutube);
				failedThumbnailGeneration = true;
			}
		});
		if (thumbnailUrl == null || thumbnailUrl.endsWith(NULL) || thumbnailUrl.equalsIgnoreCase("") ) {
			setDefaultThumbnail(thumbnailUrl, realUrl, categoryString.trim(), generateYoutube);
		} else {

			image.setUrl(thumbnailUrl);
		}
		image.setAltText(title);
		image.setTitle(title);
	}
	/**
	 *
	 * @function setDefaultThumbnail
	 *
	 * @created_date : 23-Jan-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param thumbnailUrl
	 * @parm(s) : @param url
	 * @parm(s) : @param categoryString
	 * @parm(s) : @param generateYoutube
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void setDefaultThumbnail(String thumbnailUrl, String url, String categoryString, boolean generateYoutube) {
		categoryString = categoryString.trim();
		categoryString = categoryString.replaceAll("gooru_classplan", "webpage");
		if(categoryString.contains("lesson")||categoryString.contains("textbook")||categoryString.contains("handout"))
		{
			categoryString=categoryString.replaceAll("lesson", "text").replaceAll("textbook", "text").replaceAll("handout", "text");
		}
		if(categoryString.contains("slide")||categoryString.contains("Slide"))
		{
			categoryString=categoryString.replaceAll("slide","image");
		}
		if(categoryString.contains("exam")||categoryString.contains("Exam")||categoryString.contains("Website")||categoryString.contains("website"))
		{
			categoryString=categoryString.replaceAll("exam","webpage").replaceAll("Exam","webpage").replaceAll("website","webpage").replaceAll("Website","webpage");
		}
		if (generateYoutube) {
			image.setUrl(ResourceImageUtil.youtubeImageLink(ResourceImageUtil.getYoutubeVideoId(url), Window.Location.getProtocol()));
		} else if (!failedThumbnailGeneration && thumbnailUrl!=null && thumbnailUrl.endsWith("/")) {
			//image.setUrl(thumbnailUrl + DEFAULT_THUMBNAIL);

			image.setUrl(DEFULT_IMAGE_PREFIX + categoryString + i18n.GL0899());
		} else {
			image.setUrl(DEFULT_IMAGE_PREFIX + categoryString + i18n.GL0899());
		}

	}

	@Override
	public void onClick(ClickEvent event) {
		//Implementing Mixpanel
		if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)) {
			MixpanelUtil.Preview_Resource_From_Profile("ResourceImageUc");
		} else {
			MixpanelUtil.Preview_Resource_From_Search("ResourceImageUc");
		}
		String gooruOid = getResourceId();
		String collectionIdVal = getCollectionId();
		if((collectionId == null || collectionId.isEmpty())&& !AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.SHELF) && !AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.PROFILE_PAGE) && !AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.COLLECTION_SEARCH))
		{
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", getResourceId());
			params.put("pn", getPlayerName());
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.RESOURCE_PLAY, params);
		}
		else
		{
			if(suggestFlag){
				Map<String, String> params = new HashMap<String, String>();
				params.put("id", getResourceId());
				params.put("pn", getPlayerName());
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.RESOURCE_PLAY, params);
			}else{
				if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SHELF)){
					final String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
					final HashMap<String,String> params = new HashMap<String,String>();
					if(AppClientFactory.getPlaceManager().getRequestParameter("o3")!=null){
						selectedFolderId=AppClientFactory.getPlaceManager().getRequestParameter("o3");
					}else if(AppClientFactory.getPlaceManager().getRequestParameter("o2")!=null){
						selectedFolderId=AppClientFactory.getPlaceManager().getRequestParameter("o2");
					}else if(AppClientFactory.getPlaceManager().getRequestParameter("o1")!=null){
						selectedFolderId=AppClientFactory.getPlaceManager().getRequestParameter("o1");
					}
					params.put("id", collectionId);
					params.put("rid", gooruOid);
					if(!selectedFolderId.isEmpty())
					{
					AppClientFactory.getInjector().getfolderService().getTocFolders(selectedFolderId,true, new SimpleAsyncCallback<FolderTocDo>() {
						@Override
						public void onSuccess(FolderTocDo folderListDo) {
							for(int i=0;i<folderListDo.getCollectionItems().size();i++)
							{
								if(collectionId.equalsIgnoreCase(folderListDo.getCollectionItems().get(i).getGooruOid()))
								{
									params.put("folderId", selectedFolderId);
									params.put("folderItemId", folderListDo.getCollectionItems().get(i).getCollectionItemId());
									if(getNarration()!=null&& !getNarration().equalsIgnoreCase("")){
										params.put("tab", "narration");
									}
									AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.COLLECTION_PLAY, params);
									PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
									AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
									break;
								}
							}
						}
					});
					}
					else
					{
					if(getNarration()!=null&& !getNarration().equalsIgnoreCase("")){
						params.put("tab", "narration");
					}
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.COLLECTION_PLAY, params);
					PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
					AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
					}
				}else if (AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.PROFILE_PAGE) || AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.COLLECTION_SEARCH)){
					if(getNarration()!=null&& !getNarration().equalsIgnoreCase("")){
						PlaceRequest request=new PlaceRequest(PlaceTokens.COLLECTION_PLAY).with("id", getProfilePageMoreInfoCollectionId()).with("rid", gooruOid).with("tab", "narration");
						AppClientFactory.getPlaceManager().revealPlace(false,request,true);
					}else{
						PlaceRequest request=new PlaceRequest(PlaceTokens.COLLECTION_PLAY).with("id", getProfilePageMoreInfoCollectionId()).with("rid", gooruOid);
						AppClientFactory.getPlaceManager().revealPlace(false,request,true);
					}
				}


			}
		}
/*		Map<String, String> params = new HashMap<String, String>();
		params.put("id", getResourceId());
		params.put("pn", getPlayerName());
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.RESOURCE_PLAY, params);*/

	}
	/**
	 *
	 * @function setResourceId
	 *
	 * @created_date : 23-Jan-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param resourceId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 *
	 * @function getResourceId
	 *
	 * @created_date : 23-Jan-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public String getResourceId() {
		return resourceId;
	}
	/**
	 *
	 * @function setPlayerName
	 *
	 * @created_date : 23-Jan-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param playerName
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	/**
	 *
	 * @function getPlayerName
	 *
	 * @created_date : 23-Jan-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public String getPlayerName() {
		return playerName;
	}
	/**
	 *
	 * @function getCollectionId
	 *
	 * @created_date : 23-Jan-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public String getCollectionId() {
		return collectionId;
	}
	/**
	 *
	 * @function setCollectionId
	 *
	 * @created_date : 23-Jan-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param collectionId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	/**
	 * @return the narration
	 */
	public String getNarration() {
		return narration;
	}

	/**
	 * @param narration the narration to set
	 */
	public void setNarration(String narration) {
		this.narration = narration;
	}

	/**
	 * @return the profilePageMoreInfoCollectionId
	 */
	public String getProfilePageMoreInfoCollectionId() {
		return profilePageMoreInfoCollectionId;
	}

	/**
	 * @param profilePageMoreInfoCollectionId the profilePageMoreInfoCollectionId to set
	 */
	public void setProfilePageMoreInfoCollectionId(
			String profilePageMoreInfoCollectionId) {
		this.profilePageMoreInfoCollectionId = profilePageMoreInfoCollectionId;
	}

}