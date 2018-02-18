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
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author Search Team
 *
 */
public class CollectionImageUc extends Composite implements ClickHandler {

	private static CollectionImageUcUiBinder uiBinder = GWT.create(CollectionImageUcUiBinder.class);

	interface CollectionImageUcUiBinder extends UiBinder<Widget, CollectionImageUc> {
	}
	@UiField
	FlowPanel collectionThumbnail;

	@UiField
	Image image;

	@UiField
	Hidden collectionGooruOid;

	private static String DEFULT_IMAGE = "images/default-collection-image.png";

	private static final String NULL = "null";

	/**
	 * Class constructor
	 */
	public CollectionImageUc() {
		initWidget(uiBinder.createAndBindUi(this));
		collectionThumbnail.getElement().setId("fpnlCollectionThumbnail");
		image.getElement().setId("imgImage");
		image.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				image.setUrl(DEFULT_IMAGE);
			}
		});
		addDomHandler(this, ClickEvent.getType());
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.PROFILE_PAGE)){
			collectionThumbnail.addStyleName("Uc-collectionPPPThumbnail");
		}
	}

	/**
	 * Set collection image
	 * @param url of the image
	 */
	/*public CollectionImageUc(String url) {
		this();
		setUrl(url);
	}*/

	/**
	 * @param url of the image
	 */
	public void setUrl(String url, String title) {
		if (url == null || url.endsWith(NULL) || (url!=null&&url.trim().isEmpty())) {
			image.setUrl(DEFULT_IMAGE);
		} else {
			if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)) {
				image.setUrl(url);
			} else {
				image.setUrl(StringUtil.formThumbnailName(url, "-160x120."));
			}
			image.setAltText(title);
			image.setTitle(title);
		}
	}

	/**
	 * @param gooruOid of the collection
	 */
	public void setGooruOid(String gooruOid) {
		collectionGooruOid.setValue(gooruOid);
	}

	@Override
	public void onClick(ClickEvent event) {
		if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)) {
			MixpanelUtil.Preview_Collection_From_Profile("CollectionUcClick");
		} else {
			MixpanelUtil.Preview_Collection_From_Search("CollectionUcClick");
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", collectionGooruOid.getValue());
		com.google.gwt.user.client.Window.scrollTo(0, 0);
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
	}
}
