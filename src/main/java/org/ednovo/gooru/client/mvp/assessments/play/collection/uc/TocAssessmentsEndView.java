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
package org.ednovo.gooru.client.mvp.assessments.play.collection.uc;



import java.util.LinkedHashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class TocAssessmentsEndView extends Composite implements HasClickHandlers{

	@UiField Image resourceThumbnail;

	@UiField HTMLPanel resourceTitle;

	@UiField FlowPanel endImageContainer,endContainer,resourceThumbnailContainer;

	private String thumbnailUrl,collectionType;

	private static TocAssessmentsEndViewUiBinder uiBinder = GWT.create(TocAssessmentsEndViewUiBinder.class);

	interface TocAssessmentsEndViewUiBinder extends UiBinder<Widget, TocAssessmentsEndView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public TocAssessmentsEndView(){
		initWidget(uiBinder.createAndBindUi(this));
		resourceTitle.getElement().setInnerHTML(i18n.GL1051());
		endContainer.getElement().setId("fpnlEndContainer");
		endImageContainer.getElement().setId("fpnlEndImageContainer");
		resourceThumbnail.getElement().setId("imgResourceThumbnail");
		resourceTitle.getElement().setId("pnlResourceTitle");
		resourceTitle.getElement().setAttribute("alt", i18n.GL1051());
		resourceTitle.getElement().setAttribute("title",i18n.GL1051());
	}

	@UiConstructor
	public TocAssessmentsEndView(String thumbnailUrl, String collectionType){
		initWidget(uiBinder.createAndBindUi(this));
		this.thumbnailUrl=thumbnailUrl;
		this.collectionType=collectionType;
		resourceTitle.getElement().setInnerHTML(i18n.GL1051());
		endContainer.getElement().setId("fpnlEndContainer");
		endImageContainer.getElement().setId("fpnlEndImageContainer");
		resourceThumbnail.getElement().setId("imgResourceThumbnail");
		resourceTitle.getElement().setId("pnlResourceTitle");
		resourceTitle.getElement().setAttribute("alt", i18n.GL1051());
		resourceTitle.getElement().setAttribute("title",i18n.GL1051());
		//setResourcePlayLink();
		//this.addClickHandler(new ResourceRequest());
	}

	public void onLoad(){
		StringUtil.setDefaultImages(collectionType, resourceThumbnail, "toc");
		resourceThumbnail.setUrl(thumbnailUrl);
	}

	@UiHandler("resourceThumbnail")
	public void onErrorImageLoad(ErrorEvent event){
		StringUtil.setDefaultImages(collectionType, resourceThumbnail, "toc");
	}
	public void setResourcePlayLink(){
		Anchor resourceAnchor=new Anchor();
		resourceAnchor.setHref(getResourceLink());
		resourceAnchor.setStyleName("");
		resourceAnchor.getElement().appendChild(endImageContainer.getElement());
		endContainer.add(resourceAnchor);
	}
	public String getResourceLink(){
		String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
		String viewToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		String resourceLink="#"+viewToken+"&id="+collectionId+"&view=end";
		resourceLink += PreviewPlayerPresenter.setConceptPlayerParameters();
		return resourceLink;
	}
	public class ResourceRequest implements ClickHandler{
		public void onClick(ClickEvent event){
			Map<String,String> params = new LinkedHashMap<String,String>();
			String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			params.put("id", collectionId);
			params.put("view", "end");
			params = PreviewPlayerPresenter.setConceptPlayerParameters(params);
			String viewToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(viewToken, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
		}
	}
	public void hideResourceThumbnailContainer(boolean hide){
		if(hide){
			resourceThumbnailContainer.setVisible(false);
		}else{
			resourceThumbnailContainer.setVisible(true);
		}
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

}
