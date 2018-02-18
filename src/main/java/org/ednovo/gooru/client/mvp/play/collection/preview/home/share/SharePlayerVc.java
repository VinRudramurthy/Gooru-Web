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
package org.ednovo.gooru.client.mvp.play.collection.preview.home.share;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ClasspageServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.AssignmentsListDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageListDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.social.SocialShareDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.play.collection.preview.home.assign.AssignPopUpCBundle;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru Team
 *
 */
public abstract class SharePlayerVc extends PopupPanel{

	@UiField
	HTMLPanel socialSharePanel,loadingImageLabel,popupContentAssign,sharetext;

	@UiField
	TextArea shareLinkTxtBox;

	@UiField
	Label cancelButton,swithUrlLbl, swithToEmbedLbl,assignDes,lblAssignTitle,lblpopupTitle;

	private boolean isPrivate = false;
	private String bitlyLink, decodeRawUrl, embedBitlyLink, rawUrl;
	private SimpleAsyncCallback<Map<String, String>> shareUrlGenerationAsyncCallback;
	private ClasspageServiceAsync classpageService;
	private SimpleAsyncCallback<ClasspageListDo> getClasspageList;
	private SimpleAsyncCallback<AssignmentsListDo> assignmentsListAsyncCallback;
	private SimpleAsyncCallback<CollectionDo> collectionDoAsyncCallback;
	String shareType = null;
	HTMLPanel ftmPanel;
	String toAssignStr = null;
	String limit = "10";// pagesize
	int classpageOffSet = 0;
	int assignmentOffSet = 0;
	boolean isApiCalling = false;
	boolean toClear = true;
	boolean isAdded = false;
	List<String> collectionsList = new ArrayList<String>();
	boolean toClearAssignment = true;
	boolean isAssignmentsEnabled = false;
	CollectionDo collectionDoGlobal = null;
	String classpageId = null;
	String assignmentId = null;
	boolean isMoreThanLimit = false; // Limit = 10
	private static AssignPopupPlayerVcUiBinder uiBinder = GWT
			.create(AssignPopupPlayerVcUiBinder.class);

	public interface AssignPopupPlayerVcUiBinder extends
			UiBinder<Widget, SharePlayerVc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField(provided = true)
	AssignPopUpCBundle res;

	@UiTemplate("SharePlayerVc.ui.xml")
	interface Binder extends UiBinder<Widget, SharePlayerVc> {
	}

	private static final Binder binder = GWT.create(Binder.class);
	/**
	 *
	 */
	public SharePlayerVc(String collectionIdVal) {
		super(false);
		res = AssignPopUpCBundle.INSTANCE;
		AssignPopUpCBundle.INSTANCE.css().ensureInjected();
		add(uiBinder.createAndBindUi(this));
		this.setGlassEnabled(true);
		sharetext.getElement().setInnerHTML(i18n.GL0638());
		sharetext.getElement().setId("pnlSharetext");
		sharetext.getElement().setAttribute("alt",i18n.GL0638());
		sharetext.getElement().setAttribute("title",i18n.GL0638());

		swithUrlLbl.setText(i18n.GL0639());
		swithUrlLbl.getElement().setId("lblSwithUrlLbl");
		swithUrlLbl.getElement().setAttribute("alt",i18n.GL0639());
		swithUrlLbl.getElement().setAttribute("title",i18n.GL0639());

		swithToEmbedLbl.setText(i18n.GL0640());
		swithToEmbedLbl.getElement().setId("lblSwithToEmbedLbl");
		swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0640());
		swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0640());

		setLabelsAndIds();
		ftmPanel = new HTMLPanel("");
		loadingImageLabel.setVisible(true);
		popupContentAssign.setVisible(false);
		try{
			AppClientFactory.getInjector().getClasspageService().getSCollIdClasspageById(collectionIdVal, new SimpleAsyncCallback<CollectionDo>(){
				@Override
				public void onSuccess(CollectionDo result){
					if(result!=null){
						String collectionId = "";
						if (result.getGooruOid() != null){
							collectionId = result.getGooruOid();
						}
						toAssignStr = collectionId;
						if (collectionId != null) {
							collectionDoGlobal = result;
							generateShareLink(toAssignStr);
						}
						loadingImageLabel.setVisible(false);
						popupContentAssign.setVisible(true);
					}
				}
			});
		}
		catch(Exception ex){
			AppClientFactory.printSevereLogger("SharePlayerVc : Constructor : "+ex.getMessage());
		}
		setShareUrlGenerationAsyncCallback(new SimpleAsyncCallback<Map<String,String>>() {
			@Override
			public void onSuccess(Map<String, String> result) {
				if(result!=null && result.size()>0)
					if(result.containsKey("decodeRawUrl")){
						embedBitlyLink=result.get("decodeRawUrl");
					}
			}
		});
		Window.enableScrolling(false);
		this.getElement().setAttribute("style", "z-index:99999;");
		this.getGlassElement().setAttribute("style", "z-index:99999; position:absolute; left:0px; top:0px;");

		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99999, false));
		this.setHeight("500px");
		this.show();
		this.center();
	}

	public abstract void closePoup();

	/**
	 * Added click handler to hide the login popup.
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("cancelButton")
	public void onCancelClicked(ClickEvent clickEvent) {
		closePoup();
	}

	/**
	 * @function setLabelsAndIds
	 *
	 * @created_date : Jul 30, 2013
	 *
	 * @description To set the default values for labels, button and id for
	 *              button.
	 *
	 * @parm(s) : NONE
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public void setLabelsAndIds() {
		shareLinkTxtBox.setReadOnly(true);
		shareLinkTxtBox.addClickHandler(new OnClickShareHandler());
		assignDes.setText(i18n.GL0546());
		assignDes.getElement().setId("lblAssignDes");
		assignDes.getElement().setAttribute("alt",i18n.GL0546());
		assignDes.getElement().setAttribute("title",i18n.GL0546());

		lblAssignTitle.setText(i18n.GL0545());
		lblAssignTitle.getElement().setId("lblAssignTitle");
		lblAssignTitle.getElement().setAttribute("alt",i18n.GL0545());
		lblAssignTitle.getElement().setAttribute("title",i18n.GL0545());

		lblpopupTitle.setText(i18n.GL0544());
		lblpopupTitle.getElement().setId("lblAssignTitle");
		lblpopupTitle.getElement().setAttribute("alt",i18n.GL0544());
		lblpopupTitle.getElement().setAttribute("title",i18n.GL0544());

		cancelButton.getElement().setId("lblCancelButton");
		loadingImageLabel.getElement().setId("pnlLoadingImageLabel");
		popupContentAssign.getElement().setId("pnlPopupContentAssign");
		shareLinkTxtBox.getElement().setId("txtShareLinkTxtBox");
		StringUtil.setAttributes(shareLinkTxtBox, true);
		socialSharePanel.getElement().setId("pnlSocialSharePanel");
	}

	@UiHandler("swithUrlLbl")
	public void onClickSwithUrl(ClickEvent clickevent) {
		if (!getIsPrivate()) {
			changeShareUrlEvents(i18n.GL0639());
		}
	}

	public boolean getIsPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
		setSwitchButtonStyles();
	}

	public void setSwitchButtonStyles() {
		if (getIsPrivate()) {
			swithToEmbedLbl.getElement().getStyle().setCursor(Cursor.DEFAULT);
			swithUrlLbl.getElement().getStyle().setCursor(Cursor.DEFAULT);
		} else {
			swithToEmbedLbl.getElement().getStyle().setCursor(Cursor.POINTER);
			swithUrlLbl.getElement().getStyle().setCursor(Cursor.POINTER);
		}
	}

	private void changeShareUrlEvents(String buttonType) {
		if (i18n.GL0639().equals(swithToEmbedLbl.getText()) &&  i18n.GL0640().equalsIgnoreCase(swithUrlLbl.getText())) {
			if (i18n.GL0642().equalsIgnoreCase(buttonType)) {
				shareLinkTxtBox.setText(bitlyLink);
				shareLinkTxtBox.getElement().setAttribute("alt",bitlyLink);
				shareLinkTxtBox.getElement().setAttribute("title",bitlyLink);
				swithUrlLbl.setText(i18n.GL0640());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0640());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0640());
				swithToEmbedLbl.setText(i18n.GL0643());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0643());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0643());
			} else {
				shareLinkTxtBox.setText(getIframeText());
				shareLinkTxtBox.getElement().setAttribute("alt",getIframeText());
				shareLinkTxtBox.getElement().setAttribute("title",getIframeText());
				swithUrlLbl.setText(i18n.GL0643());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0643());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0643());
				swithToEmbedLbl.setText(i18n.GL0639());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0639());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0639());
			}
		} else if (i18n.GL0640().equalsIgnoreCase(swithToEmbedLbl.getText())&& i18n.GL0639().equalsIgnoreCase(swithUrlLbl.getText())) {
			if (i18n.GL0642().equalsIgnoreCase(buttonType)){
				shareLinkTxtBox.setText(getIframeText());
				shareLinkTxtBox.getElement().setAttribute("alt",getIframeText());
				shareLinkTxtBox.getElement().setAttribute("title",getIframeText());
				swithUrlLbl.setText(i18n.GL0639());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0639());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0639());
				swithToEmbedLbl.setText(i18n.GL0643());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0643());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0643());
			} else {
				shareLinkTxtBox.setText(bitlyLink);
				shareLinkTxtBox.getElement().setAttribute("alt",bitlyLink);
				shareLinkTxtBox.getElement().setAttribute("title",bitlyLink);
				swithUrlLbl.setText(i18n.GL0643());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0643());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0643());
				swithToEmbedLbl.setText(i18n.GL0640());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0640());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0640());
			}
		} else if (i18n.GL0643().equals(swithToEmbedLbl.getText())&& i18n.GL0640().equalsIgnoreCase(swithUrlLbl.getText())) {
			if (i18n.GL0642().equalsIgnoreCase(buttonType)) {
				shareLinkTxtBox.setText(decodeRawUrl);
				shareLinkTxtBox.getElement().setAttribute("alt",decodeRawUrl);
				shareLinkTxtBox.getElement().setAttribute("title",decodeRawUrl);
				fullUrlMixPanelEvent();
				swithUrlLbl.setText(i18n.GL0640());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0640());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0640());
				swithToEmbedLbl.setText(i18n.GL0639());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0639());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0639());
			} else {
				shareLinkTxtBox.setText(getIframeText());
				shareLinkTxtBox.getElement().setAttribute("alt",getIframeText());
				shareLinkTxtBox.getElement().setAttribute("title",getIframeText());
				swithUrlLbl.setText(i18n.GL0639());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0639());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0639());
				swithToEmbedLbl.setText(i18n.GL0643());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0643());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0643());
			}
		} else if (i18n.GL0640().equalsIgnoreCase(swithToEmbedLbl.getText())&& i18n.GL0643().equalsIgnoreCase(swithUrlLbl.getText())) {
			if (i18n.GL0642().equalsIgnoreCase(buttonType)) {
				shareLinkTxtBox.setText(getIframeText());
				shareLinkTxtBox.getElement().setAttribute("alt",getIframeText());
				shareLinkTxtBox.getElement().setAttribute("title",getIframeText());
				swithUrlLbl.setText(i18n.GL0643());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0643());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0643());
				swithToEmbedLbl.setText(i18n.GL0639());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0639());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0639());
			} else {
				shareLinkTxtBox.setText(decodeRawUrl);
				shareLinkTxtBox.getElement().setAttribute("alt",decodeRawUrl);
				shareLinkTxtBox.getElement().setAttribute("title",decodeRawUrl);
				fullUrlMixPanelEvent();
				swithUrlLbl.setText(i18n.GL0639());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0639());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0639());
				swithToEmbedLbl.setText(i18n.GL0640());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0640());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0640());
			}
		} else if (i18n.GL0639().equalsIgnoreCase(swithToEmbedLbl.getText())&& i18n.GL0643().equalsIgnoreCase(swithUrlLbl.getText())) {
			if (i18n.GL0642().equalsIgnoreCase(buttonType)) {
				shareLinkTxtBox.setText(bitlyLink);
				shareLinkTxtBox.getElement().setAttribute("alt",bitlyLink);
				shareLinkTxtBox.getElement().setAttribute("title",bitlyLink);
				swithUrlLbl.setText(i18n.GL0643());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0643());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0643());
				swithToEmbedLbl.setText(i18n.GL0640());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0640());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0640());
			} else {
				shareLinkTxtBox.setText(decodeRawUrl);
				shareLinkTxtBox.getElement().setAttribute("alt",decodeRawUrl);
				shareLinkTxtBox.getElement().setAttribute("title",decodeRawUrl);
				fullUrlMixPanelEvent();
				swithUrlLbl.setText(i18n.GL0640());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0640());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0640());
				swithToEmbedLbl.setText(i18n.GL0639());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0639());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0639());
			}
		} else if (i18n.GL0643().equalsIgnoreCase(swithToEmbedLbl.getText())&& i18n.GL0639().equalsIgnoreCase(swithUrlLbl.getText())) {
			if (i18n.GL0642().equalsIgnoreCase(buttonType)) {
				shareLinkTxtBox.setText(decodeRawUrl);
				shareLinkTxtBox.getElement().setAttribute("alt",decodeRawUrl);
				shareLinkTxtBox.getElement().setAttribute("title",decodeRawUrl);
				fullUrlMixPanelEvent();
				swithUrlLbl.setText(i18n.GL0639());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0639());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0639());
				swithToEmbedLbl.setText(i18n.GL0640());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0640());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0640());
			} else {
				shareLinkTxtBox.setText(bitlyLink);
				shareLinkTxtBox.getElement().setAttribute("alt",bitlyLink);
				shareLinkTxtBox.getElement().setAttribute("title",bitlyLink);
				swithUrlLbl.setText(i18n.GL0640());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0640());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0640());
				swithToEmbedLbl.setText(i18n.GL0643());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0643());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0643());
			}
		}
	}

	private void fullUrlMixPanelEvent() {

	}

	private String getIframeText() {
		String iframeText = null;
		if (embedBitlyLink == null) {
			embedBitlyLink = shareLinkTxtBox.getText();
		}
		iframeText = "<iframe width=\"1024px\" height=\"768px\" src=\""
				+ embedBitlyLink + "\" frameborder=\"0\" ></iframe>";
		return iframeText;
	}

	/**
	 * Switching between Url and Bitly link
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("swithToEmbedLbl")
	public void onClickSwithToEmbed(ClickEvent clickevent) {
		if (!getIsPrivate()) {
			changeShareUrlEvents(i18n.GL0642());
		}
	}

	public void setShareUrlGenerationAsyncCallback(
			SimpleAsyncCallback<Map<String, String>> shareShortenUrlAsyncCallback) {
		this.shareUrlGenerationAsyncCallback = shareShortenUrlAsyncCallback;
	}

	public SimpleAsyncCallback<Map<String, String>> getShareShortenUrlAsyncCallback() {
		return shareUrlGenerationAsyncCallback;
	}

	public void generateShareLink(String classpageId) {
		Map<String, String> params = new HashMap<String, String>();
		String currentPlaceToken=AppClientFactory.getCurrentPlaceToken();
		params.put("type", currentPlaceToken);
		params.put("shareType", "");
		AppClientFactory.getInjector().getSearchService().getShortenShareUrl(classpageId, params,new SimpleAsyncCallback<Map<String, String>>() {
			@Override
			public void onSuccess(Map<String, String> result) {
				if(result.containsKey("decodeRawUrl")){
					decodeRawUrl = result.get("decodeRawUrl");
					shareLinkTxtBox.setText(decodeRawUrl);
				}
				if(result.containsKey("shortenUrl")){
					bitlyLink = result.get("shortenUrl");
				}
				if(result.containsKey("rawUrl")){
					rawUrl = result.get("rawUrl");
				}
				if(collectionDoGlobal!=null){
					addShareWidgetInPlay(decodeRawUrl, rawUrl,collectionDoGlobal.getTitle(),collectionDoGlobal.getDescription(),collectionDoGlobal.getThumbnails().getUrl(), "",collectionDoGlobal.getSharing());
				}
			}
		});
		params.put("shareType", "embed");
		AppClientFactory.getInjector().getSearchService().getShortenShareUrl(classpageId, params, getShareShortenUrlAsyncCallback());
	}

	public void addShareWidgetInPlay(String link, String rawUrl, String title,String desc, String shortenUrl, String type, String shareType) {
		try {
			SocialShareDo shareDo = new SocialShareDo();
			shareDo.setBitlylink(rawUrl);
			shareDo.setRawUrl(rawUrl);
			shareDo.setTitle(title);
			shareDo.setDescription(desc);
			shareDo.setThumbnailurl(shortenUrl);
			shareDo.setCategoryType(type);
			shareDo.setOnlyIcon(false);
			shareDo.setShareType(shareType);
			shareDo.setDecodeRawUrl(link);
			SocialShareSmallView socialView = new SocialShareSmallView(shareDo){
				@Override
				public void triggerShareDataEvent(String shareType,boolean confirmStatus){
					triggerShareEvent(shareType,confirmStatus);
				}
			};
			ftmPanel.add(socialView);
			socialSharePanel.add(ftmPanel);
			} catch (Exception ex) {
				AppClientFactory.printSevereLogger("SharePlayerVc : addShareWidgetInPlay : "+ex.getMessage());
		}
	}

	public class OnClickShareHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			shareLinkTxtBox.selectAll();
			shareLinkTxtBox.setFocus(true);
		}
	}
	public void triggerShareEvent(String shareType,boolean confirmStatus){

	}
	private static native boolean isCookieEnabled() /*-{
		return navigator.cookieEnabled;
	}-*/;
}
