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
package org.ednovo.gooru.client.mvp.assessments.play.resource.narration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.uc.tooltip.GlobalTooltipWithButton;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class AssessmentsResourceNarrationView extends PopupViewWithUiHandlers<AssessmentsResourceNarrationUiHandlers> implements IsAssessmentsResourceNarrationView{

	@UiField Image authorImage;
	@UiField HTML resourceTitle,narrationText;
	@UiField Label narrationCloseButton,authorName;
	@UiField Button okButton;
	@UiField HTMLPanel narrartionpopupPnl;
	public PopupPanel appPopUp;

	GlobalTooltipWithButton globalTooltipWithButton;

	private static ResourceNarrationViewUiBinder uiBinder = GWT.create(ResourceNarrationViewUiBinder.class);

	interface ResourceNarrationViewUiBinder extends UiBinder<Widget, AssessmentsResourceNarrationView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);
	/**
	 * Default Constructor
	 * @param eventsBus
	 */
	@Inject
	public AssessmentsResourceNarrationView(EventBus eventsBus){
		super(eventsBus);
		appPopUp=new NarrationPopupPanel(true);
		appPopUp.setWidget(uiBinder.createAndBindUi(this));
		narrartionpopupPnl.getElement().setId("narrartionpopupPnl");
		appPopUp.setStyleName("narrationPopupContainer");
		narrationCloseButton.addClickHandler(new CloseNarrationPopupEvent());
		okButton.addClickHandler(new CloseNarrationPopupEvent());
		authorName.setText(i18n.GL0423());
		authorName.getElement().setId("lblAuthorName");
		authorName.getElement().setAttribute("alt",i18n.GL0423());
		authorName.getElement().setAttribute("title",i18n.GL0423());

		okButton.setText(i18n.GL0703());
		okButton.getElement().setId("btnOkButton");
		okButton.getElement().setAttribute("alt",i18n.GL0703());
		okButton.getElement().setAttribute("title",i18n.GL0703());

		resourceTitle.getElement().setId("htmlResourceTitle");
		narrationCloseButton.getElement().setId("lblNarrationCloseButton");
		authorImage.getElement().setId("imgAuthorImage");
		narrationText.getElement().setId("htmlNarrationText");
	}

	public boolean isShowingPopup(){
		return appPopUp.isShowing();
	}

	/**
	 * This method is used to set metadata to Narration Popup.
	 */

	@Override
	public void setNarrationMetadata(CollectionItemDo collectionItemDo,String userName,String gooruUid){
		if (collectionItemDo!=null){
			String narration=collectionItemDo.getNarration();
			if(narration!=null){
				narration=narration.replaceAll("background-color", "");
			}
			narrationText.setHTML(narration !=null ? StringUtil.replaceAnchoreWithTarget(narration) : "");
			narrationText.getElement().setAttribute("alt",narration !=null ? narration : "");
			narrationText.getElement().setAttribute("title",narration !=null ? narration : "");
			resourceTitle.setHTML(collectionItemDo.getItemSequence()+". "+removeHtmlTags((collectionItemDo.getResource()!=null && collectionItemDo.getResource().getTitle()!=null)?collectionItemDo.getResource().getTitle():""));
			resourceTitle.getElement().setAttribute("alt",collectionItemDo.getItemSequence()+". "+StringUtil.removeAllHtmlCss((collectionItemDo.getResource()!=null && collectionItemDo.getResource().getTitle()!=null)?collectionItemDo.getResource().getTitle():""));
			resourceTitle.getElement().setAttribute("title",collectionItemDo.getItemSequence()+". "+StringUtil.removeAllHtmlCss((collectionItemDo.getResource()!=null && collectionItemDo.getResource().getTitle()!=null)?collectionItemDo.getResource().getTitle():""));

			setUserProfileImage(gooruUid);
			authorName.setText(userName);
			authorName.getElement().setAttribute("alt",userName);
			authorName.getElement().setAttribute("title",userName);
		}
	}
	/**
	 * This is used to display the user profile image in Narration popup.
	 * @param profileUserId
	 */
	private void setUserProfileImage(String profileUserId) {
		authorImage.setUrl(AppClientFactory.loggedInUser.getSettings().getProfileImageUrl()+profileUserId+".png");
	}
	/**
	 * This method will set default image incase of error occurs.
	 * @param event
	 */
	@UiHandler("authorImage")
	public void setDefaultProfileImage(ErrorEvent event){
		authorImage.setUrl("images/settings/setting-user-image.png");
	}
	/**
	 * This method is used to clear the data inside Narration popup.
	 */
	public void resetNattationData(){
		narrationText.setHTML("");
		narrationText.getElement().setAttribute("alt","");
		narrationText.getElement().setAttribute("title","");
		authorImage.getElement().removeAttribute("src");
		resourceTitle.setHTML("");
		resourceTitle.getElement().setAttribute("alt","");
		resourceTitle.getElement().setAttribute("title","");
		authorName.setText("");
		authorName.getElement().setAttribute("alt","");
		authorName.getElement().setAttribute("title","");
	}
	/**
	 * This method will remove the htmltags
	 * @param html
	 * @return
	 */
	private String removeHtmlTags(String html){
        html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
        return html;
	}
	@Override
	public Widget asWidget() {
		return appPopUp;
	}
	@Override
	public void reset() {

	}
	@Override
	public void onLoad() {

	}
	@Override
	public void onUnload() {

	}
	/**
	 * This event is used to close the Narration popup on close button click
	 * @author gooruteam
	 *
	 */
	private class CloseNarrationPopupEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			MixpanelUtil.CloseNarration();
			hide();
			hideNarrationPopup();
		}
	}
	/**
	 * This method is used to close and to set the params on Ok button click
	 * @author gooruteam
	 *
	 */
	public void hideNarrationPopup(){
		if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.PREVIEW_PLAY)){
		}
		resetNattationData();
		String viewToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
		String resourceId=AppClientFactory.getPlaceManager().getRequestParameter("rid", null);

		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionId);
		params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);
		params.put("rid", resourceId);
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(viewToken, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	/**
	 * This native method is used to pause the video
	 * @param myPlayer
	 */
	public static native void pauseVideo(Element myPlayer) /*-{
 		myPlayer.pauseVideo();
	}-*/;
	/**
	 * This native method is used to play the video
	 * @param myPlayer
	 */
	public static native void playVideo(Element myPlayer) /*-{
  		myPlayer.playVideo();
   	}-*/;

	/**
	 * This method is used to hide narration popup on clicking outside.
	 * @author gooruteam
	 *
	 */
	private class NarrationPopupPanel extends PopupPanel{
		public NarrationPopupPanel(boolean isAutoHide){
			super(isAutoHide);
			this.setGlassEnabled(true);
			this.setStyleName("");
			this.getGlassElement().getStyle().setZIndex(999999);
			this.getElement().getStyle().setZIndex(999999);
		}
		public void hide(boolean isAutoHide){
			super.hide(isAutoHide);
			if(isAutoHide){
				hideNarrationPopup();
			}
		}
	}
	@Override
	public void showAddToolTip() {
		throw new RuntimeException("Not implemented");
	}
}
