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

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;
import org.ednovo.gooru.client.GooruCBundle;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AlertContentUc;
import org.ednovo.gooru.client.uc.AlertForImageUpload;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.uc.BlueButtonUc;
import org.ednovo.gooru.client.uc.BrowserAgent;
import org.ednovo.gooru.client.uc.ErrorLabelUc;
import org.ednovo.gooru.client.uc.GlassPanelWithLoadingUc;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

/**
 * @author Search Team
 *
 */
public class ImageUploadView extends PopupViewWithUiHandlers<ImageUploadUiHandlers> implements IsImageUploadView{

	private static ImageUploadViewUiBinder uiBinder = GWT.create(ImageUploadViewUiBinder.class);

	interface ImageUploadViewUiBinder extends UiBinder<Widget, ImageUploadView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	private boolean isUserUnder13=false;
	private boolean isUploadProfileWidget=false;
	private int selectedWidgetIndex=-1;
	private float aspectRatio=1.34f;
	private boolean isEdit=false;
	public AppPopUp appPopUp;
	protected AppPopUp imageCropPopup;

	@UiField
	FileUpload fileUpload;

	@UiField
	Anchor imageUploadOnWebLbl,uploadGooruImages;

	@UiField
	Anchor imageUploadOnComputerLbl,readThisLbl;

	@UiField
	FlowPanel imageUploadOnUrlFloPanel,gooruProfileDefaultImagesContainer,displayCromImagePanel1,displayCromImagePanel;
	@UiField Button okButtonOnUploadGooruImages,cropImage,cropImage1;
	@UiField Label cancelButtonOnUploadGooruImages;

	@UiField
	FlowPanel imageUploadOnWebFloPanel;

	@UiField
	Button onWebCancelBtn,onSystemCancelBtn;

	@UiField
	BlueButtonUc onOkButton,onOkButton1;

	@UiField
	TextBox imageWebUploadUrlTxtBox;

	@UiField
	BlueButtonUc uploadImageButtonOnWeb;

	@UiField
	FormPanel fileuploadForm;

	@UiField
	FlowPanel imagUploadFloPanel,uploadGooruImagesContainer;

	@UiField
	FlowPanel imageCropFloPanel;

	@UiField
	GlassPanelWithLoadingUc glassPanelWithLoadingUc;

	@UiField
	ErrorLabelUc urlValidation;
	@UiField
	HTMLPanel notWorkingPanel;

	@UiField Label chooseText,uploadFromComputer,uploadLimitText,notWorkingLblText,
	uploadFromWebText,imageURLLbl,typeImageurlText,infoUrlUploadText,chooseFromText;

	private static final String GET_CROPPED_IMAGE = "/gooruapi/rest/v1/crop?height={0}&width={1}&x={2}&y={3}&mediaFileName={4}&sessionToken={5}";

	private static final String IMAGE_UPLOAD_URL_PATTERN = "(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:jpg|gif|jpeg|png))(?:\\?([^#]*))?(?:#(.*))?";

	private static final String IMAGE_UPLOAD_FILE_PATTERN = "([^\\s]+([^?#]*\\.(?:jpg|gif|jpeg|png))$)";

	MediaUploadDo mediaUploadDo;

	@UiField Image displayImage,displayImage1;
	
	String height=null,width=null,xVal=null,yVal=null;

	/**
	 * See for more details  {@link PopupViewWithUiHandlers} for details.
	 *
	 * @param eventBus instance of {@link EventBus}
	 */
	@Inject
	public ImageUploadView(EventBus eventBus) {
		super(eventBus);
		GooruCBundle.INSTANCE.css().ensureInjected();
		appPopUp = new AppPopUp(i18n.GL1424());
		appPopUp.setContent(uiBinder.createAndBindUi(this));
		appPopUp.setStyleName(GooruCBundle.INSTANCE.css().imageUploadPopup());
		imageCropPopup = new AppPopUp(i18n.GL1424());
		imageCropPopup.setStyleName(GooruCBundle.INSTANCE.css().imageUploadPopup());
		chooseText.setText(i18n.GL1215()+i18n.GL_SPL_SEMICOLON()+" ");
		chooseText.getElement().setId("lblChooseText");
		chooseText.getElement().setAttribute("alt",i18n.GL1215()+i18n.GL_SPL_SEMICOLON()+" ");
		chooseText.getElement().setAttribute("title",i18n.GL1215()+i18n.GL_SPL_SEMICOLON()+" ");

		imageUploadOnWebLbl.setText(i18n.GL1216());
		imageUploadOnWebLbl.getElement().setId("lnkOnWeb");
		imageUploadOnWebLbl.getElement().setAttribute("alt",i18n.GL1216());
		imageUploadOnWebLbl.getElement().setAttribute("title",i18n.GL1216());

		imageUploadOnComputerLbl.setText(i18n.GL1217());
		imageUploadOnComputerLbl.getElement().setId("lnkOnComputer");
		imageUploadOnComputerLbl.getElement().setAttribute("alt",i18n.GL1217());
		imageUploadOnComputerLbl.getElement().setAttribute("title",i18n.GL1217());

		uploadGooruImages.setText(i18n.GL1218());
		uploadGooruImages.getElement().setId("lnkGooruImages");
		uploadGooruImages.getElement().setAttribute("alt",i18n.GL1218());
		uploadGooruImages.getElement().setAttribute("title",i18n.GL1218());

		uploadFromComputer.setText(i18n.GL1219());
		uploadFromComputer.getElement().setId("lblUploadFromComputer");
		uploadFromComputer.getElement().setAttribute("alt",i18n.GL1219());
		uploadFromComputer.getElement().setAttribute("title",i18n.GL1219());

		uploadLimitText.setText(i18n.GL1220());
		uploadLimitText.getElement().setId("lblUploadLimitText");
		uploadLimitText.getElement().setAttribute("alt",i18n.GL1220());
		uploadLimitText.getElement().setAttribute("title",i18n.GL1220());

		notWorkingLblText.setText(i18n.GL1221()+" "+i18n.GL_SPL_QUESTION());
		notWorkingLblText.getElement().setId("lblNotWorkingLblText");
		notWorkingLblText.getElement().setAttribute("alt",i18n.GL1221());
		notWorkingLblText.getElement().setAttribute("title",i18n.GL1221());

		readThisLbl.setText(i18n.GL1222()+i18n.GL_SPL_EXCLAMATION());
		readThisLbl.getElement().setId("lblReadThisLbl");
		readThisLbl.getElement().setAttribute("alt",i18n.GL1222());
		readThisLbl.getElement().setAttribute("title",i18n.GL1222());

		onSystemCancelBtn.setText(i18n.GL0142());
		onSystemCancelBtn.getElement().setId("btnCancel");
		onSystemCancelBtn.getElement().setAttribute("alt",i18n.GL0142());
		onSystemCancelBtn.getElement().setAttribute("title",i18n.GL0142());

		uploadFromWebText.setText(i18n.GL1223());
		uploadFromWebText.getElement().setId("lblUploadFromWebText");
		uploadFromWebText.getElement().setAttribute("alt",i18n.GL1223());
		uploadFromWebText.getElement().setAttribute("title",i18n.GL1223());

		imageURLLbl.setText(i18n.GL1224());
		imageURLLbl.getElement().setId("lblImageURLLbl");
		imageURLLbl.getElement().setAttribute("alt",i18n.GL1224());
		imageURLLbl.getElement().setAttribute("title",i18n.GL1224());

		uploadImageButtonOnWeb.setText(i18n.GL1225());
		uploadImageButtonOnWeb.getElement().setId("btnUpload");
		uploadImageButtonOnWeb.getElement().setAttribute("alt",i18n.GL1225());
		uploadImageButtonOnWeb.getElement().setAttribute("title",i18n.GL1225());

		typeImageurlText.setText(i18n.GL1226());
		typeImageurlText.getElement().setId("lblTypeImageurlText");
		typeImageurlText.getElement().setAttribute("alt",i18n.GL1226());
		typeImageurlText.getElement().setAttribute("title",i18n.GL1226());

		infoUrlUploadText.setText(i18n.GL1227());
		infoUrlUploadText.getElement().setId("lblInfoUrlUploadText");
		infoUrlUploadText.getElement().setAttribute("alt",i18n.GL1227());
		infoUrlUploadText.getElement().setAttribute("title",i18n.GL1227());
		infoUrlUploadText.setVisible(false);

		onWebCancelBtn.setText(i18n.GL0142());
		onWebCancelBtn.getElement().setId("btnCancel");
		onWebCancelBtn.getElement().setAttribute("alt",i18n.GL0142());
		onWebCancelBtn.getElement().setAttribute("title",i18n.GL0142());

		chooseFromText.setText(i18n.GL1228()+i18n.GL_SPL_SEMICOLON()+" ");
		chooseFromText.getElement().setId("lblChooseFromText");
		chooseFromText.getElement().setAttribute("alt",i18n.GL1228());
		chooseFromText.getElement().setAttribute("title",i18n.GL1228());

		okButtonOnUploadGooruImages.setText(i18n.GL0190());
		okButtonOnUploadGooruImages.getElement().setId("btnOkButtonOnUploadGooruImages");
		okButtonOnUploadGooruImages.getElement().setAttribute("alt",i18n.GL0190());
		okButtonOnUploadGooruImages.getElement().setAttribute("title",i18n.GL0190());

		cancelButtonOnUploadGooruImages.setText(i18n.GL0142());
		cancelButtonOnUploadGooruImages.getElement().setId("btnCancelButtonOnUploadGooruImages");
		cancelButtonOnUploadGooruImages.getElement().setAttribute("alt",i18n.GL0142());
		cancelButtonOnUploadGooruImages.getElement().setAttribute("title",i18n.GL0142());

		fileUpload.getElement().setAttribute("size", "25");
		fileUpload.getElement().setId("fileUpload");
		fileuploadForm.getElement().setId("fileUploadForm");
		notWorkingPanel.getElement().setId("pnlNotWorkingPanel");
		imageUploadOnUrlFloPanel.getElement().setId("fpnlImageUploadOnUrlFloPanel");
		urlValidation.getElement().setId("errlblUrlValidation");
		uploadGooruImagesContainer.getElement().setId("fpnlUploadGooruImagesContainer");
		gooruProfileDefaultImagesContainer.getElement().setId("fpnlGooruProfileDefaultImagesContainer");
		imageCropFloPanel.getElement().setId("fpnlImageCropFloPanel");
		glassPanelWithLoadingUc.getElement().setId("glassPanelWithLoadingUc");


		imageUploadOnComputerLbl.setStyleName(GooruCBundle.INSTANCE.css().uploadClose());
		imageUploadOnWebLbl.setStyleName(GooruCBundle.INSTANCE.css().uploadActive());
		uploadGooruImages.setStyleName(GooruCBundle.INSTANCE.css().uploadClose());
		uploadGooruImages.getElement().getStyle().setDisplay(Display.NONE);
		imageWebUploadUrlTxtBox.getElement().setId("tbImageWebUploadUrl");

		urlValidation.setVisible(false);
		imageWebUploadUrlTxtBox.addFocusHandler(new OnTextFocus());
		urlValidation.setStyleName(GooruCBundle.INSTANCE.css().imageUrlError());
		handelFormEvent();
		appPopUp.setModal(true);
		notWorkingPanel.setVisible(false);
		readThisLbl.setHref(i18n.GL1265());
//		Window.enableScrolling(false);
//		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, false));
		addClickEventToDefaultImages();
		fileuploadForm.addSubmitHandler(new FormPanel.SubmitHandler() {
			public void onSubmit(final SubmitEvent event) {
				String browesr = BrowserAgent.getWebBrowserClient();
				if(browesr.contains("ie")){
					Timer timer = new Timer(){
			            @Override
			            public void run()
			            {
			            	glasspanelLoadingImage(false);
			            	fileuploadForm.reset();
			            	event.cancel();
			            	notWorkingPanel.setVisible(true);
			            }
			    };
			    timer.schedule(10000);
				}
				if (!"".equalsIgnoreCase(fileUpload.getFilename())) {
					String size=getFileNameSize();
					double sizeOfImage=Double.parseDouble(size);
					if(sizeOfImage>5){
						new AlertForImageUpload(i18n.GL0061(),i18n.GL1229());
						 glasspanelLoadingImage(false);
						fileuploadForm.reset();
						event.cancel();
					}
				}else{
					event.cancel(); // cancel the event
				}
			}
		});
		imagUploadFloPanel.getElement().setId("fpnlImagUploadFloPanel");
		imageUploadOnWebFloPanel.getElement().setId("fpnlImageUploadOnWebFloPanel");
		hideAndDisplayAllCropButtons(false);
	}

	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	@Override
	public void reset() {

	}

	@Override
	public void onUnload() {

	}

	@Override
	public void onLoad() {

	}

	public void addClickEventToDefaultImages(){
		int widgetsCount=gooruProfileDefaultImagesContainer.getWidgetCount();
		for(int widgetIndex=0;widgetsCount>widgetIndex;widgetIndex++){
			final int selectedWidget=widgetIndex;
			GooruImagesView gooruImagesView=(GooruImagesView)gooruProfileDefaultImagesContainer.getWidget(widgetIndex);
			gooruImagesView.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					highlightSelectedImage(selectedWidget);
				}
			});
		}
	}
	private void highlightSelectedImage(int widgetIndex){
		GooruImagesView gooruImagesView=(GooruImagesView)gooruProfileDefaultImagesContainer.getWidget(widgetIndex);
		gooruImagesView.profileGooruDefaultImage.setStyleName(GooruCBundle.INSTANCE.css().profileImageActive());
		if(selectedWidgetIndex!=-1){
			GooruImagesView gooruImagesView1=(GooruImagesView)gooruProfileDefaultImagesContainer.getWidget(selectedWidgetIndex);
			gooruImagesView1.profileGooruDefaultImage.setStyleName(GooruCBundle.INSTANCE.css().profileImageContainer());
		}
		this.selectedWidgetIndex=widgetIndex;
	}

	private void resetProfileImageSelected(){
		this.selectedWidgetIndex=-1;
		int widgetsCount=gooruProfileDefaultImagesContainer.getWidgetCount();
		for(int widgetIndex=0;widgetsCount>widgetIndex;widgetIndex++){
			GooruImagesView gooruImagesView=(GooruImagesView)gooruProfileDefaultImagesContainer.getWidget(widgetIndex);
			gooruImagesView.profileGooruDefaultImage.setStyleName(GooruCBundle.INSTANCE.css().profileImageContainer());
		}
	}


	private class OnTextFocus implements FocusHandler {
		@Override
		public void onFocus(FocusEvent event) {
			imageWebUploadUrlTxtBox.removeStyleName(GooruCBundle.INSTANCE.css().textboxUrlError());
			urlValidation.setVisible(false);
		}
	}

	public void showUploadTypeWidgets(boolean isUserUnder13){
		this.isUserUnder13=isUserUnder13;
		if(isUserUnder13){
			uploadGooruImages.getElement().getStyle().setDisplay(Display.BLOCK);
			imageUploadOnComputerLbl.getElement().getStyle().setDisplay(Display.NONE);
			imageUploadOnWebLbl.getElement().getStyle().setDisplay(Display.NONE);
			uploadGooruImages.setStyleName(GooruCBundle.INSTANCE.css().uploadActive());
			imageUploadOnComputerLbl.setStyleName(GooruCBundle.INSTANCE.css().uploadClose());
			imageUploadOnWebLbl.setStyleName(GooruCBundle.INSTANCE.css().uploadClose());
			uploadGooruImagesContainer.getElement().getStyle().setDisplay(Display.BLOCK);
			imageUploadOnUrlFloPanel.getElement().getStyle().setDisplay(Display.NONE);
			imageUploadOnWebFloPanel.getElement().getStyle().setDisplay(Display.NONE);
		}else{
			uploadGooruImages.getElement().getStyle().setDisplay(Display.BLOCK);
		}
	}

	/**
	 * Upload image from local machine to enable or disable tab.
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("imageUploadOnComputerLbl")
	public void imageUploadOnComputer(ClickEvent clickEvent) {
			imageUploadOnComputerLbl.setStyleName(GooruCBundle.INSTANCE.css().uploadActive());
			uploadGooruImages.setStyleName(GooruCBundle.INSTANCE.css().uploadClose());
			imageUploadOnWebLbl.setStyleName(GooruCBundle.INSTANCE.css().uploadClose());
			imageUploadOnUrlFloPanel.getElement().getStyle().setDisplay(Display.NONE);
			uploadGooruImagesContainer.getElement().getStyle().setDisplay(Display.NONE);
			imageUploadOnWebFloPanel.getElement().getStyle().setDisplay(Display.BLOCK);
			//setBooleanValues(true);
			hideAndDisplayAllCropButtons(false);
	}
	/**
	 * This method is used for hiding and enabling the crop and display buttons based on the selected widget.
	 * @param isTrue
	 */
	public void setBooleanValues(boolean isTrue){
		displayImage.setUrl("");
		displayImage.setVisible(isTrue);
		cropImage.setVisible(isTrue);
		//displayCromImagePanel.getElement().getStyle().clearBackgroundImage();

		displayImage1.setUrl("");
		displayImage1.setVisible(!isTrue);
		cropImage1.setVisible(!isTrue);
		//displayCromImagePanel1.getElement().getStyle().clearBackgroundImage();
	}
	/**
	 * This method is used for hiding and enabling the crop and display buttons.
	 * @param isTrue
	 */
	public void hideAndDisplayAllCropButtons(boolean isTrue){
		displayImage.setVisible(isTrue);
		cropImage.setVisible(isTrue);
		displayImage1.setVisible(isTrue);
		cropImage1.setVisible(isTrue);
		setAndClearBorder(displayCromImagePanel.getElement(), isTrue);
		setAndClearBorder(displayCromImagePanel1.getElement(), isTrue);
	}

	public void setAndClearBorder(Element element,boolean isTure){
		if(!isTure){
			element.removeAttribute("style");
		}
	}

	/**
	 * Upload image from web to enable or disable tab.
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("imageUploadOnWebLbl")
	public void imageUploadOnWeb(ClickEvent clickEvent) {
		if(isUserUnder13){
			uploadGooruImages.getElement().getStyle().setDisplay(Display.BLOCK);
			imageUploadOnComputerLbl.getElement().getStyle().setDisplay(Display.NONE);
			imageUploadOnWebLbl.getElement().getStyle().setDisplay(Display.NONE);
			uploadGooruImages.setStyleName(GooruCBundle.INSTANCE.css().uploadActive());
			imageUploadOnComputerLbl.setStyleName(GooruCBundle.INSTANCE.css().uploadClose());
			imageUploadOnWebLbl.setStyleName(GooruCBundle.INSTANCE.css().uploadClose());
			uploadGooruImagesContainer.getElement().getStyle().setDisplay(Display.BLOCK);
			imageUploadOnUrlFloPanel.getElement().getStyle().setDisplay(Display.NONE);
			imageUploadOnWebFloPanel.getElement().getStyle().setDisplay(Display.NONE);
		}else{
			imageUploadOnWebLbl.setStyleName(GooruCBundle.INSTANCE.css().uploadActive());
			uploadGooruImages.setStyleName(GooruCBundle.INSTANCE.css().uploadClose());
			imageUploadOnComputerLbl.setStyleName(GooruCBundle.INSTANCE.css().uploadClose());
			imageUploadOnUrlFloPanel.getElement().getStyle().setDisplay(Display.BLOCK);
			imageUploadOnWebFloPanel.getElement().getStyle().setDisplay(Display.NONE);
			uploadGooruImagesContainer.getElement().getStyle().setDisplay(Display.NONE);
		}
		//setBooleanValues(false);
		hideAndDisplayAllCropButtons(false);
	}

	@UiHandler("uploadGooruImages")
	public void uploadImagesFromGooru(ClickEvent clickEvent){
		uploadGooruImages.setStyleName(GooruCBundle.INSTANCE.css().uploadActive());
		imageUploadOnComputerLbl.setStyleName(GooruCBundle.INSTANCE.css().uploadClose());
		imageUploadOnWebLbl.setStyleName(GooruCBundle.INSTANCE.css().uploadClose());
		uploadGooruImagesContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		imageUploadOnUrlFloPanel.getElement().getStyle().setDisplay(Display.NONE);
		imageUploadOnWebFloPanel.getElement().getStyle().setDisplay(Display.NONE);
	}

	/**
	 * Hide {@link AppPopUp}
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("onSystemCancelBtn")
	public void onSystemCancelClick(ClickEvent clickEvent) {
		appPopUp.hide();
		resetImageUploadWidget();
		if(isEdit){
			Window.enableScrolling(false);
		}else{
			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		}
	}

	@UiHandler("cancelButtonOnUploadGooruImages")
	public void cancelOnGooruImages(ClickEvent event){
		appPopUp.hide();
		resetImageUploadWidget();
		if(isEdit){
			Window.enableScrolling(false);
		}else{
			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		}
	}

	/**
	 * Hide {@link AppPopUp}
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("onWebCancelBtn")
	public void onWebCancelClick(ClickEvent clickEvent) {
		appPopUp.hide();
		resetImageUploadWidget();
	}

	/**
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("uploadImageButtonOnWeb")
	public void uploadImageButtonOnWeb(ClickEvent clickEvent) {
		if (hasValidateData()) {
			glasspanelLoadingImage(true);
			getUiHandlers().imageWebUpload(imageWebUploadUrlTxtBox.getText());
		}
	}

	@UiHandler("okButtonOnUploadGooruImages")
	public void uploadGooruDefaultImage(ClickEvent clickEvent){
		if(this.selectedWidgetIndex!=-1){
			glasspanelLoadingImage(true);
			GooruImagesView gooruImagesView=(GooruImagesView)gooruProfileDefaultImagesContainer.getWidget(selectedWidgetIndex);
			String url=gooruImagesView.gooruDefaultImage.getUrl();
			getUiHandlers().uploadGooruDefaultImage(url);
		}
	}
	/**
	 * Validate web image url
	 * @return true if upload image url is valid else false
	 */
	private boolean hasValidateData() {
		boolean isValid = true;
		String url = imageWebUploadUrlTxtBox.getText();
		try {
			RegExp reg = RegExp.compile(IMAGE_UPLOAD_URL_PATTERN, "gi");

			if (url == null || (url != null && url.isEmpty())) {
				urlValidation.setText(i18n.GL0080());
				urlValidation.getElement().setAttribute("alt",i18n.GL0080());
				urlValidation.getElement().setAttribute("title",i18n.GL0080());
				imageWebUploadUrlTxtBox.addStyleName(GooruCBundle.INSTANCE.css().textboxUrlError());
				urlValidation.setVisible(true);
				return isValid = false;
			}
			if(url != null && !reg.test(url)){
				new AlertContentUc(i18n.GL0060(),i18n.GL0059());
				return isValid = false;
			}
		}catch (Exception e) {
			AppClientFactory.printSevereLogger("ImageUploadView hasValidateData:::"+e);
		}
		return isValid;
	}

	/**
	 * validate image which uploaded from local machine
	 * @return true if upload image file is valid else false
	 */
	public boolean hasValidateImage(){
		boolean isValid = true;
		String uploadImageName = fileUpload.getFilename();
		try {
			RegExp reg = RegExp.compile(IMAGE_UPLOAD_FILE_PATTERN, "gi");
			if(uploadImageName != null && !reg.test(uploadImageName)){
				new AlertContentUc(i18n.GL0060(),i18n.GL0059());
				return isValid = false;
			}
		}catch (Exception e) {
			AppClientFactory.printSevereLogger("ImageUploadView hasValidateImage:::"+e);
		}
		return isValid;
	}

	@Override
	public void setImageUpload(final MediaUploadDo mediaUploadDo) {
		final String placeValue = AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		if (mediaUploadDo != null && mediaUploadDo.getStatusCode() == 200) {
			this.mediaUploadDo=mediaUploadDo;
			displayImage.setUrl(mediaUploadDo.getUrl());
			displayImage1.setUrl(mediaUploadDo.getUrl());
			hideAndDisplayAllCropButtons(true);		
			displayImage.setVisible(false);
			displayImage1.setVisible(false);
			if(aspectRatio==4.53f){
				displayCromImagePanel.getElement().setAttribute("style","min-height: 90px;height: auto;border: 2px solid #efefef;background-image:url("+mediaUploadDo.getUrl()+");");
				displayCromImagePanel1.getElement().setAttribute("style","min-height: 90px;height: auto;border: 2px solid #efefef;background-image:url("+mediaUploadDo.getUrl()+");");
			}else if(aspectRatio==1.0f){
				displayCromImagePanel.getElement().setAttribute("style","min-width: 300px;width:auto;min-height: 300px;height: auto;border: 2px solid #efefef;background-image:url("+mediaUploadDo.getUrl()+");");
				displayCromImagePanel1.getElement().setAttribute("style","min-width: 300px;width:auto;min-height: 300px;height: auto;border: 2px solid #efefef;background-image:url("+mediaUploadDo.getUrl()+");");
			}else{
				displayCromImagePanel.getElement().setAttribute("style","border: 2px solid #efefef;background-image:url("+mediaUploadDo.getUrl()+");");
				displayCromImagePanel1.getElement().setAttribute("style","border: 2px solid #efefef;background-image:url("+mediaUploadDo.getUrl()+");");
			}
		} else {
			appPopUp.hide();
			imageCropPopup.hide();
			Window.scrollTo(0, 0);
			if(placeValue.equalsIgnoreCase(PlaceTokens.SHELF)){
				Window.enableScrolling(false);
			}else{
				Window.enableScrolling(true);
			}
			resetImageUploadWidget();
			new AlertContentUc(i18n.GL1089(), mediaUploadDo != null && mediaUploadDo.getImageValidationMsg() != null ? mediaUploadDo.getImageValidationMsg() : i18n.GL1230());
		}
	}

	@Override
	public void handelFormEvent() {
		fileUpload.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if(hasValidateImage()){
					glasspanelLoadingImage(true);
					fileuploadForm.setAction(GWT.getModuleBaseURL() +"upServlet?sessionToken="+AppClientFactory.getLoginSessionToken());
					fileuploadForm.submit();
				} else {
					new AlertContentUc(i18n.GL0060(),i18n.GL0059());
				}
			}
		});
		fileuploadForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				glasspanelLoadingImage(false);
				getUiHandlers().imageFileUpload(event.getResults());
			}
		});
	}

	@Override
	public void closeImageUploadWidget() {
		appPopUp.hide();
	}

	@Override
	public void glasspanelLoadingImage(boolean state) {
		glassPanelWithLoadingUc.setVisible(state);
	}

	@Override
	public void resetImageUploadWidget() {
		imageCropFloPanel.clear();
		imagUploadFloPanel.setVisible(true);
		imageUploadOnWeb(null);
		imageWebUploadUrlTxtBox.setText("");
		fileUpload.getElement().getPropertyString("");
		fileUpload.getElement().setPropertyString("value","");
		glasspanelLoadingImage(false);
		resetProfileImageSelected();
		imageCropPopup.clear();
		imageCropPopup.hide();
		notWorkingPanel.setVisible(false);
		hideAndDisplayAllCropButtons(false);
		if(isEdit){

		}else{
		  AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		}
	}


	/**
	 * To get the upload file size from client end
	 * @return it will return the upload file size in mb
	 */

	public final native String getFileNameSize() /*-{
		  var fileSize;
	      if ($wnd.$.support.msie){
		     	var objFSO = new ActiveXObject("Scripting.FileSystemObject");
		        var sPath =   $wnd.$("#fileUpload")[0].value;
		        var objFile = objFSO.getFile(sPath);
		        var iSize = objFile.size;
		        fileSize = iSize/ 1048576;
	        }else{
	       		fileSize =  $wnd.$("#fileUpload")[0].files[0].size ;//size in kb
	        	fileSize = fileSize / 1048576; //size in mb
	        }
	        return fileSize.toString();
	  }-*/;

	public static native String getUserAgent() /*-{
		return navigator.userAgent.toLowerCase();
	}-*/;

	public void setAspectRatio(float aspectRatio){
		this.aspectRatio=aspectRatio;
	}

	@Override
	public void isFromEditQuestion(boolean isEdit) {
		this.isEdit=isEdit;
	}

	@UiHandler(value={"cropImage","cropImage1"})
	public void clickEventOnCropImage(ClickEvent e){
		if(mediaUploadDo!=null){
			displayCropPopup(mediaUploadDo);
		}
	}
	@UiHandler(value={"onOkButton","onOkButton1"})
	public void clickEventOnUseImage(ClickEvent e){
		if(mediaUploadDo!=null){
			//getUiHandlers().setUploadData(mediaUploadDo.getName(), mediaUploadDo);
			getUiHandlers().cropImage(mediaUploadDo, height, width, xVal, yVal);
		}
	}

	/**
	 * This method is used to display crop popup
	 * @param mediaUploadDo
	 */
	 @Override
	 public void displayCropPopup(final MediaUploadDo mediaUploadDo){
		final String placeValue = AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		final ImageCropView imageCropView = new ImageCropView() {
			@Override
			public void onCancelCrop() {
				/*resetImageUploadWidget();
				appPopUp.hide();
				imageCropPopup.hide();
				Window.scrollTo(0, 0);
				if(placeValue.equalsIgnoreCase(PlaceTokens.SHELF)){
					Window.enableScrolling(false);
				}else{
					Window.enableScrolling(true);
				}*/
				imageCropPopup.clear();
				imageCropPopup.hide();
				Window.scrollTo(0, 0);
				if(placeValue.equalsIgnoreCase(PlaceTokens.SHELF)){
					Window.enableScrolling(false);
				}else{
					Window.enableScrolling(true);
				}
				appPopUp.show();
			}
			@Override
			public void onBackToUpload() {
				//resetImageUploadWidget();
				imageCropPopup.clear();
				imageCropPopup.hide();
				Window.scrollTo(0, 0);
				if(placeValue.equalsIgnoreCase(PlaceTokens.SHELF)){
					Window.enableScrolling(false);
				}else{
					Window.enableScrolling(true);
				}
				appPopUp.show();
			}
			@Override
			public void onCrop() {
				String tempCropUrl=AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint() + StringUtil.generateMessage(GET_CROPPED_IMAGE,getSelectionHeight(),getSelectionWidth(),getSelectionXCoordinate(), getSelectionYCoordinate(),mediaUploadDo.getName(),AppClientFactory.getLoggedInUser().getToken());
				setCroppedImage(tempCropUrl+"&id="+Math.random());
				height=getSelectionHeight();
				width=getSelectionWidth();
				xVal=getSelectionXCoordinate();
				yVal=getSelectionYCoordinate();
				//getUiHandlers().cropImage(mediaUploadDo.getName(), getSelectionHeight(), getSelectionWidth(), getSelectionXCoordinate(), getSelectionYCoordinate(),mediaUploadDo.getUrl());
			}
			@Override
			public void onLoad(){
				super.onLoad();
				imageCropPopup.center();
			}
		};
		imageCropView.cropImage(mediaUploadDo.getUrl(),aspectRatio);
		imageCropView.addCanvasLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				imageCropPopup.center();
				Window.enableScrolling(true);
			}
		});
		imageCropPopup.clear();
		imageCropPopup.add(imageCropView);
		imageCropPopup.show();
	 }

	@Override
	public void setCroppedImage(String filename) {
		displayImage.setUrl("");
		displayImage1.setUrl("");
		displayImage.setVisible(true);
		displayImage1.setVisible(true);
		displayImage.setUrl(filename);
		displayImage1.setUrl(filename);
		displayCromImagePanel.getElement().getStyle().clearBackgroundImage();
		displayCromImagePanel1.getElement().getStyle().clearBackgroundImage();
		imageCropPopup.hide();
	}
}
