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
package org.ednovo.gooru.client.mvp.image.upload;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.BlueButtonUc;
import org.ednovo.gooru.client.uc.LoadingUc;

import com.google.code.gwt.crop.client.GWTCropper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public abstract class ImageCropView extends Composite{

	@UiField
	Label backPageLinkLbl;

	@UiField
	FlowPanel cropImageWidgetFloPanel,imagCropContainer;

	@UiField
	BlueButtonUc cropImageBtn;

	@UiField
	Button cancelButtonAnr;
	
	@UiField
	VerticalPanel cropImageLoadingVerPanel;
	
	@UiField
	LoadingUc cropImageLoading;
	
	@UiField
	Label loadingTextLbl;
	
	@UiField HTML cropText,dragText;
	
	@UiField
	FlowPanel buttonContainer;
	
	@UiField HTMLPanel panel;
	
	private GWTCropper crop;

	private static ImageCropViewUiBinder uiBinder = GWT.create(ImageCropViewUiBinder.class);

	interface ImageCropViewUiBinder extends UiBinder<Widget, ImageCropView> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor
	 */
	public ImageCropView() {
		super();
		initWidget(uiBinder.createAndBindUi(this));
		backPageLinkLbl.setText(i18n.GL1231());
		backPageLinkLbl.getElement().setId("lblBackPageLinkLbl");
		backPageLinkLbl.getElement().setAttribute("alt",i18n.GL1231());
		backPageLinkLbl.getElement().setAttribute("title",i18n.GL1231());
		
		cropText.setHTML(i18n.GL1232());
		cropText.getElement().setId("htmlCropText");
		cropText.getElement().setAttribute("alt",i18n.GL1232());
		cropText.getElement().setAttribute("title",i18n.GL1232());
		
		dragText.setHTML(i18n.GL1233());
		dragText.getElement().setId("htmlDragText");
		dragText.getElement().setAttribute("alt",i18n.GL1233());
		dragText.getElement().setAttribute("title",i18n.GL1233());
		dragText.setVisible(false);
		
		cropImageLoading.setLoadingText(i18n.GL1234());
		cropImageBtn.setText(i18n.GL1235());
		cropImageBtn.getElement().setId("btnCropImageBtn");
		cropImageBtn.getElement().setAttribute("alt",i18n.GL1235());
		cropImageBtn.getElement().setAttribute("title",i18n.GL1235());
		
		cancelButtonAnr.setText(i18n.GL0142());
		cancelButtonAnr.getElement().setId("lnkCancelButtonAnr");
		cancelButtonAnr.getElement().setAttribute("alt",i18n.GL0142());
		cancelButtonAnr.getElement().setAttribute("title",i18n.GL0142());
		
		loadingTextLbl.setText(i18n.GL1236());
		loadingTextLbl.getElement().setId("lblLoadingTextLbl");
		loadingTextLbl.getElement().setAttribute("alt",i18n.GL1236());
		loadingTextLbl.getElement().setAttribute("title",i18n.GL1236());
		
		cropImageLoadingVerPanel.setCellVerticalAlignment(cropImageLoading, HasVerticalAlignment.ALIGN_MIDDLE);
		buttonContainer.setVisible(true);
		loadingTextLbl.setVisible(false);
		
		imagCropContainer.getElement().setId("fpnlImagCropContainer");
		cropImageWidgetFloPanel.getElement().setId("fpnlCropImageWidgetFloPanel");
		cropImageLoadingVerPanel.getElement().setId("vpnlCropImageLoadingVerPanel");
		cropImageLoading.getElement().setId("loadingUcCropImageLoading");
		buttonContainer.getElement().setId("fpnlButtonContainer");
		panel.getElement().setId("cropContainer");
	}

	/**
	 * Crop given image and add it to crop image widget
	 * @param imageURL of the image , which is to be cropped. 
	 */
	public void cropImage(String imageURL,float aspectRatio) {
		Image img = new Image(imageURL);
		crop = new GWTCropper(imageURL);
		crop.setAspectRatio(aspectRatio);
		if(aspectRatio==1.0f){
			crop.setSize(200, 200);
		}else if(aspectRatio==4.53f){
			if(img.getHeight()>=270){
				crop.setSize(400, 273);
			}else{
				crop.setSize(400,img.getHeight());
			}
		}else{
			if(img.getWidth()<=400){
				crop.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
			}else{
				crop.setSize(400, 273);
			}
		}
		panel.add(crop);
		Timer timer = new Timer() {
		    public void run() {
		    	cropImageLoading.setVisible(false);
		    }
		};
	   // Execute the timer to expire 7 seconds in the future
	   timer.schedule(7000);	
	}
	
	/**
	 * @return height of cropped image
	 */
	public String getSelectionHeight() {
		return String.valueOf(crop.getSelectionHeight());
	}
	
	/**
	 * @return width of cropped image
	 */
	public String getSelectionWidth() {
		return String.valueOf(crop.getSelectionWidth());
	}
	
	/**
	 * @return x-coordinate of cropped image
	 */
	public String getSelectionXCoordinate() {
		return String.valueOf(crop.getSelectionXCoordinate());
	}
	/**
	 * @return y-coordinate of cropped image
	 */
	public String getSelectionYCoordinate() {
		return String.valueOf(crop.getSelectionYCoordinate());
	}

	/**
	 * Cancel crop event 
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("cancelButtonAnr")
	public void onClickCancelCrop(ClickEvent clickEvent) {
		onCancelCrop();
	}
	
	/**
	 * Back to upload page
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("backPageLinkLbl")
	public void onClickBackToUpload(ClickEvent clickEvent) {
		onBackToUpload();
	}
	
	/**
	 * Crop image 
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("cropImageBtn")
	public void onClickCrop(ClickEvent clickEvent) {
		buttonContainer.setVisible(false);
		loadingTextLbl.setVisible(true);
		onCrop();
	}
	public abstract void onCancelCrop();

	public abstract void onBackToUpload();
	
	public abstract void onCrop();
	
	public void addCanvasLoadHandler(LoadHandler handler) {
		crop.addCanvasLoadHandler(handler);
	}

	public GWTCropper getCrop() {
		return crop;
	}

	public HTMLPanel getPanel() {
		return panel;
	}
}
