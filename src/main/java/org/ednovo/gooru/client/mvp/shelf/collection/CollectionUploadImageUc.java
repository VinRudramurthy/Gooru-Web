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
package org.ednovo.gooru.client.mvp.shelf.collection;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class CollectionUploadImageUc extends Composite {

	@UiField
	Image collectionImg;

	@UiField
	Label changeImgLbl;
	
	@UiField HTMLEventPanel collectionEditImageContainer;
	
	private String collectionType;
	
	private static final String ASSESSMENT = "assessment";
	
	private static final String DEFULT_ASSESSMENT_IMG = "images/default-assessment-image -160x120.png";
	
	private static final String DEFULT_COLLECTION_IMG = "images/default-collection-image-160x120.png";

	private static CollectionUploadImageUcUiBinder uiBinder = GWT.create(CollectionUploadImageUcUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface CollectionUploadImageUcUiBinder extends UiBinder<Widget, CollectionUploadImageUc> {
	}

	/**
	 * Class constructor set image url
	 */
	public CollectionUploadImageUc() {
		initWidget(uiBinder.createAndBindUi(this));
		changeImgLbl.setText(i18n.GL0800());
		changeImgLbl.getElement().setAttribute("alt",i18n.GL0800());
		changeImgLbl.getElement().setAttribute("title",i18n.GL0800());
		changeImgLbl.getElement().setId("lblChangeImg");
		
		collectionImg.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				setDefaultImage();
			}
		});
		
		collectionEditImageContainer.addMouseOverHandler(new ShowUploadImageButton());
		collectionEditImageContainer.addMouseOutHandler(new HideUploadImageButton());
		collectionEditImageContainer.getElement().setId("epnlCollectionEditImageContainer");
		collectionImg.getElement().setId("imgCollectionImg");
	}

	/**
	 * @param url of image
	 * @param collectionType 
	 */
	public void setUrl(String url, String collectionType) {
		this.collectionType=collectionType;
		if(url!=null&&!url.trim().isEmpty()) {
			if(url.equalsIgnoreCase("images/defaultRes.png")){
				changeImgLbl.setText(i18n.GL1087());
				StringUtil.setAttributes(changeImgLbl.getElement(), "changeImgLbl", i18n.GL1087(), i18n.GL1087());
			}else{
				changeImgLbl.setText(i18n.GL0800());
				StringUtil.setAttributes(changeImgLbl.getElement(), "changeImgLbl", i18n.GL0800(), i18n.GL0800());
			}
			if(collectionType!=null && collectionType.equals(ASSESSMENT)){
				collectionImg.setStyleName(CollectionCBundle.INSTANCE.css().assessmentThumbnails());
			}else{
				collectionImg.setStyleName(CollectionCBundle.INSTANCE.css().collectionThumbnails());
			}
			collectionImg.setUrl(url);
			
		} else {
			setDefaultImage();
		}
	}

	/**
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("changeImgLbl")
	public void onclick(ClickEvent clickEvent) {
		MixpanelUtil.ClickOnEditImageFromCollectionEdit();

	}

	/**
	 * @return  changeImgLbl instance of {@link Label}
	 */
	public Label getChangeImage() {
		return changeImgLbl;
	}
	
	public Image getCollectionImg() {
		return collectionImg;
	}


	private class ShowUploadImageButton implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			changeImgLbl.getElement().getStyle().setDisplay(Display.BLOCK);
		}
		
	}
	private class HideUploadImageButton implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			changeImgLbl.getElement().getStyle().setDisplay(Display.NONE);
		}
		
	}
	/**
	 * To set the default image
	 */
	protected void setDefaultImage() {
		if(collectionType!=null && collectionType.equals(ASSESSMENT)){
			collectionImg.setStyleName(CollectionCBundle.INSTANCE.css().assessmentThumbnails());
			collectionImg.setUrl(DEFULT_ASSESSMENT_IMG);
		}else{
			collectionImg.setStyleName(CollectionCBundle.INSTANCE.css().collectionThumbnails());
			collectionImg.setUrl(DEFULT_COLLECTION_IMG);
		}
		changeImgLbl.setText(i18n.GL1087());
		StringUtil.setAttributes(changeImgLbl.getElement(), "changeImgLbl", i18n.GL1087(), i18n.GL1087());
	}

}
