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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.exists;
/**
* @fileName : ExistsResourceView.java
*
* @description :This file is responsible show view based on ExistsResourceView.ui.xml
*
* @version :5.1
*
* @date: Apr 6 2013

* @Author  Gooru Team
*
* @Reviewer
*
*/
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ExistsResourceDo;
import org.ednovo.gooru.application.shared.model.content.GetFlagContentDO;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddResourceView;
import org.ednovo.gooru.client.mvp.shelf.event.InsertCollectionItemInAddResourceEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshCollectionItemInShelfListEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.uc.FlaggingPopUp;
import org.ednovo.gooru.shared.util.ResourceImageUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ExistsResourceView extends AppPopUp {

	private static ExistsResourceViewUiBinder uiBinder = GWT.create(ExistsResourceViewUiBinder.class);

	MessageProperties i18n = GWT.create(MessageProperties.class);

	private static final String DEFULT_IMAGE_PREFIX = "images/default-";

	private static final String PNG = ".png";

	CollectionDo collectionDo=null;

	interface ExistsResourceViewUiBinder extends UiBinder<Widget, ExistsResourceView> {
	}

	@UiField Label cancelExistsResourcePopupBtnLbl, addExistsResourceBtnLbl;

	@UiField Label resourceUrlLbl,resourceTitleLbl,loadingTextLbl;

	@UiField Anchor reportResInfoLbl;

	@UiField Image resourceThumbnailImg,resourceIconImg;

	@UiField HTMLPanel buttonContainer,alreadyExistsText,resourceExistsText;

	AddResourceView appPopup=null;

	ExistsResourceDo existsResourceDo=null;


	String idStr=null, url=null, title=null, description=null, category=null, thumbnailUrl=null;

	interface NewResourcePopupViewUiBinder extends
			UiBinder<Widget, ExistsResourceView> {

	}

	public ExistsResourceView() {

		setWidget(uiBinder.createAndBindUi(this));

        setModal(true);
		Window.enableScrolling(false);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
        alreadyExistsText.getElement().setInnerHTML(i18n.GL0932()+i18n.GL_SPL_EXCLAMATION());
        alreadyExistsText.getElement().setId("pnlAlreadyExistsText");
        alreadyExistsText.getElement().setAttribute("alt", i18n.GL0932());
        alreadyExistsText.getElement().setAttribute("title", i18n.GL0932());
        resourceExistsText.getElement().setInnerHTML(i18n.GL0933()+i18n.GL_SPL_EXCLAMATION());
        resourceExistsText.getElement().setId("pnlResourceExistsText");
        resourceExistsText.getElement().setAttribute("alt", i18n.GL0933());
        resourceExistsText.getElement().setAttribute("title", i18n.GL0933());
        resourceThumbnailImg.getElement().setId("imgResourceThumbnailImg");
        resourceThumbnailImg.setUrl("images/thumbimg-I.png");

        resourceTitleLbl.setText(i18n.GL0935());
        resourceTitleLbl.getElement().setId("lblResourceTitleLbl");
        resourceTitleLbl.getElement().setAttribute("alt", i18n.GL0935());
        resourceTitleLbl.getElement().setAttribute("title", i18n.GL0935());
        addExistsResourceBtnLbl.setText(i18n.GL0590());
        addExistsResourceBtnLbl.getElement().setAttribute("alt", i18n.GL0590());
        addExistsResourceBtnLbl.getElement().setAttribute("title", i18n.GL0590());
        cancelExistsResourcePopupBtnLbl.setText(i18n.GL0142());
        cancelExistsResourcePopupBtnLbl.getElement().setAttribute("alt", i18n.GL0142());
        cancelExistsResourcePopupBtnLbl.getElement().setAttribute("title", i18n.GL0142());
        loadingTextLbl.setText(i18n.GL0591().toLowerCase());
        loadingTextLbl.getElement().setId("lblLoadingTextLbl");
        loadingTextLbl.getElement().setAttribute("alt", i18n.GL0591());
        loadingTextLbl.getElement().setAttribute("title", i18n.GL0591());
        addExistsResourceBtnLbl.getElement().setId("lblAdd");
        cancelExistsResourcePopupBtnLbl.getElement().setId("lblCancel");
		cancelExistsResourcePopupBtnLbl.addClickHandler(new CloseExistsClickHandler());
		addExistsResourceBtnLbl.addClickHandler(new AddExistsClickHandler());
		loadingTextLbl.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
		loadingTextLbl.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		buttonContainer.setVisible(true);
		loadingTextLbl.setVisible(false);
		resourceUrlLbl.getElement().setId("lblResourceUrlLbl");
		resourceIconImg.getElement().setId("imgResourceIconImg");
		buttonContainer.getElement().setId("pnlButtonContainer");
		reportResInfoLbl.getElement().setId("lnkReportResInfoLbl");
		}

	@UiHandler("reportResInfoLbl")
	public void onClickReport(ClickEvent event){
		hide();
		FlaggingPopUp flagging = new FlaggingPopUp(idStr, title);
		flagging.center();
		flagging.show();
	}

	//Click handler for Close/Cancel

	private class CloseExistsClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {

			appPopup.getAddWebResourceWidget().clearFields();

			appPopup.getAddWebResourceWidget().addResourceBtnLbl.setVisible(false);
			appPopup.getAddWebResourceWidget().addResourceBtnPanel.setVisible(false);
			appPopup.getAddWebResourceWidget().setThumbnailImage.setVisible(false);

			hide();
			Window.enableScrolling(true);
			appPopup.show();
		}
	}
	//Click event to handle Add existing resource/collection item to collection.
	private class AddExistsClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			buttonContainer.setVisible(false);
			loadingTextLbl.setVisible(true);
			AppClientFactory.getInjector().getResourceService().createCollectionItem(collectionDo.getGooruOid(), existsResourceDo.getGooruOid(), new SimpleAsyncCallback<CollectionItemDo>() {

				@Override
				public void onSuccess(CollectionItemDo result) {
					hide();
					Window.enableScrolling(true);
			        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
			        AppClientFactory.fireEvent(new InsertCollectionItemInAddResourceEvent(result, RefreshType.INSERT));
					AppClientFactory.fireEvent(new RefreshCollectionItemInShelfListEvent(result, RefreshType.INSERT));
				}
			});
		}
	}

	public ExistsResourceDo getExistsResourceDo() {
		return existsResourceDo;
	}
	public void setExistsResourceDo(ExistsResourceDo existsResourceDo) {
		this.existsResourceDo = existsResourceDo;
	}

	public CollectionDo getCollectionDo() {
		return collectionDo;
	}

	public void setCollectionDo(CollectionDo collectionDo) {
		this.collectionDo = collectionDo;
	}

	public void displayResourceInformation(ExistsResourceDo existsResourceDo, CollectionDo collectionDo) {
		this.existsResourceDo = existsResourceDo;
		setCollectionDo(collectionDo);

		idStr = existsResourceDo.getGooruOid();
		url = existsResourceDo.getUrl();
		title = existsResourceDo.getTitle();
		description = existsResourceDo.getDescription();
		category = existsResourceDo.getResourceFormat() != null && existsResourceDo.getResourceFormat().getValue() != null ? existsResourceDo.getResourceFormat().getValue().toLowerCase() : "";

		thumbnailUrl = existsResourceDo != null && existsResourceDo.getThumbnails() != null ? existsResourceDo.getThumbnails().getUrl() : DEFULT_IMAGE_PREFIX+category+PNG;
		resourceUrlLbl.setText(url);
		resourceUrlLbl.getElement().setId("lblResourceUrlLbl");
		resourceUrlLbl.getElement().setAttribute("alt", url);
		resourceUrlLbl.getElement().setAttribute("title", url);
		resourceIconImg.getElement().setId("imgResourceIconImg");
		resourceThumbnailImg.getElement().setId("imgResourceThumbnailImg");
		buttonContainer.getElement().setId("pnlButtonContainer");
		reportResInfoLbl.getElement().setId("lnkReportResInfoLbl");
		resourceTitleLbl.getElement().setId("lblResourceTitleLbl");
	    resourceTitleLbl.getElement().setAttribute("alt", title);
	    resourceTitleLbl.getElement().setAttribute("title", title);
		resourceTitleLbl.setText(title);
		if(thumbnailUrl!=null && !thumbnailUrl.equalsIgnoreCase("")){
		if (thumbnailUrl.endsWith("null")){
			if (url.indexOf("youtube") >0){
				String youTubeIbStr = ResourceImageUtil.getYoutubeVideoId(url);
				thumbnailUrl = "http://img.youtube.com/vi/"+youTubeIbStr+"/1.jpg";
			}else{
				thumbnailUrl = DEFULT_IMAGE_PREFIX+category+PNG;

			}
		}else if(thumbnailUrl.indexOf("youtube") > 0){
			String youTubeIbStr = ResourceImageUtil.getYoutubeVideoId(url);
			thumbnailUrl = "http://img.youtube.com/vi/"+youTubeIbStr+"/1.jpg";
		}
		}else{
			thumbnailUrl = DEFULT_IMAGE_PREFIX+category+PNG;
		}

		resourceThumbnailImg.setStyleName("thumbnailImageDec");
		resourceThumbnailImg.setUrl(thumbnailUrl);

		setResourceIconStyle(category, resourceIconImg);
		AppClientFactory.getInjector().getResourceService().getContentReport(idStr, new SimpleAsyncCallback<GetFlagContentDO>() {
			@Override
			public void onSuccess(GetFlagContentDO result) {
				if(result==null ){
					reportResInfoLbl.setText(i18n.GL1497());
					reportResInfoLbl.getElement().setAttribute("alt", i18n.GL1497());
					reportResInfoLbl.getElement().setAttribute("title", i18n.GL1497());
				}
				else
				{
					reportResInfoLbl.setText(i18n.GL1498());
					reportResInfoLbl.getElement().setAttribute("alt", i18n.GL1498());
					reportResInfoLbl.getElement().setAttribute("title", i18n.GL1498());

				}
			}
		});
	}

	public void setResourceIconStyle(String categoryStr, Image resourceThumbnailImage){
		if (categoryStr.equalsIgnoreCase("Video")){
			resourceThumbnailImage.setStyleName("resourceExistsResourceTypeSpriteVideo");
		}else if (categoryStr.equalsIgnoreCase("Interactive")){
			resourceThumbnailImage.setStyleName("resourceExistsResourceTypeSpriteInteractive");
		}else if (categoryStr.equalsIgnoreCase("Website")||categoryStr.equalsIgnoreCase("Exam")||categoryStr.equalsIgnoreCase("Webpage")||categoryStr.equalsIgnoreCase("webpage")){
			resourceThumbnailImage.setStyleName("resourceExistsResourceTypeSpriteWebsite");
		}else if (categoryStr.equalsIgnoreCase("Slide")||categoryStr.equalsIgnoreCase("Image")){
			resourceThumbnailImage.setStyleName("resourceExistsResourceTypeSpriteImage");
		}else if (categoryStr.equalsIgnoreCase("Handout")||categoryStr.equalsIgnoreCase("Textbook")||categoryStr.equalsIgnoreCase("Lesson")||categoryStr.equalsIgnoreCase("Text")){
			resourceThumbnailImage.setStyleName("resourceExistsResourceTypeSpriteText");
		}else if (categoryStr.equalsIgnoreCase("Question")){
			resourceThumbnailImage.setStyleName("resourceExistsResourceTypeSpriteQuestion");
		}else if (categoryStr.equalsIgnoreCase("Audio")){
			resourceThumbnailImage.setStyleName("resourceExistsResourceTypeSpriteAudio");
		}else if(categoryStr.equalsIgnoreCase("Other")){
			resourceThumbnailImage.setStyleName("resourceExistsResourceTypeSpriteOther");
		}else if(categoryStr.equalsIgnoreCase("Image")){
			resourceThumbnailImage.setStyleName("resourceExistsResourceTypeSpriteImage");
		}else{
			resourceThumbnailImage.setStyleName("resourceExistsResourceTypeSpriteWebsite");
		}
	}

	public void setAddNewPopup(AddResourceView appPopup) {
		this.appPopup = appPopup;
	}
	/*native void redirect(String url)
    -{
            $wnd.location.reload();
    }-;*/
}
