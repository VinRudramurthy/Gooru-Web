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
package org.ednovo.gooru.client.mvp.home.library.assign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ClasspageServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.AssignmentsListDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageListDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.social.SocialShareDo;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.assessments.play.collection.event.AssessmentsSetPlayerLoginStatusEvent;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.home.ForgotPasswordVc;
import org.ednovo.gooru.client.mvp.home.event.SetTexasAccountEvent;
import org.ednovo.gooru.client.mvp.home.event.SetTexasPlaceHolderEvent;
import org.ednovo.gooru.client.mvp.home.event.SetUserDetailsInCollectionPlayEvent;
import org.ednovo.gooru.client.mvp.home.event.SetUserDetailsInPlayEvent;
import org.ednovo.gooru.client.mvp.home.library.LibraryTopicListView;
import org.ednovo.gooru.client.mvp.home.library.events.SetLoginStatusEvent;
import org.ednovo.gooru.client.mvp.home.library.events.SetLoginStatusHandler;
import org.ednovo.gooru.client.mvp.play.collection.event.SetPlayerLoginStatusEvent;
import org.ednovo.gooru.client.mvp.profilepage.data.item.ProfileTopicListView;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AlertContentUc;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.uc.BrowserAgent;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.client.util.ScrollPopupUtil;
import org.ednovo.gooru.shared.util.DataLogEvents;
import org.ednovo.gooru.shared.util.GwtUUIDGenerator;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author BLR Team
 *
 */
public abstract class AssignPopupVc extends PopupPanel {

	@UiField
	HTMLPanel loadingImageLabel,popupContentAssign,signUpStyles,assignContainer,assignPopupContent,contentBorderPanel;

	@UiField
	HTMLEventPanel htmlEvenPanelContainer;
	@UiField
	HTMLEventPanel htmlLoginPanel;

	@UiField
	TextBoxWithPlaceholder loginTxtBox;

	@UiField
	TextBoxWithPlaceholder passwordTxtBox;

	@UiField
	Button loginButton;

	@UiField
	TextArea shareLinkTxtBox;

	@UiField
	Anchor forgotPwd,ancSignUp;

	@UiField
	Button gmailButton;

	@UiField Label lblOr,lblLoginwithGooru;

	@UiField
	Label cancelButton,lblPleaseWait,assignDes,lblAssignPopDes,lblAssignTitle,lblpopupTitle,lblLoginPopupTitle;

	@UiField InlineLabel lblPii,toUsText,donothaveAC;
	@UiField Anchor ancprivacy ,swithUrlLbl, swithToEmbedLbl;

	private boolean isPrivate = false;
	private static final String SWITCH_URL_LABEL = "swithUrlLbl";
	private static final String SWITCH_TO_EMBED_LABEL = "swithToEmbedLbl";
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
	private TermsOfUse termsOfUse;
	private static final int HTTP_UNAUTHORISED_STATUS_CODE = 401;
	private static final String UNAUTHORIZED_MSG ="Please double-check your password and try signing in again.";
	private static final String USER_ID_WRONG_MSG = "Please double-check your email address and password, and then try logging in again.";


	private static final int HTTP_SUCCESS_STATUS_CODE = 200;
	private static final String GOOGLE_REFRESH_TOKEN = "google-refresh-token";

	private static final String ERR_GL0078 = "401-GL0078";
	private static final String ERR_GL0079 = "401-GL0079";
	private static final String ERR_GL010501 = "401-GL010501";
	private static final String ERR_GL010502 = "401-GL010502";
	private static final String ERR_GL010503 = "401-GL010503";
	private static final String ERR_GL0081="401-GL0081";

	public HTMLPanel getAssignContainer(){
		return assignContainer;
	}

	private static AssignPopupVcUiBinder uiBinder = GWT
			.create(AssignPopupVcUiBinder.class);

	public interface AssignPopupVcUiBinder extends
			UiBinder<Widget, AssignPopupVc> {
	}

	@UiField(provided = true)
	AssignPopUpCBundle res;

	@UiTemplate("AssignPopupVc.ui.xml")
	interface Binder extends UiBinder<Widget, AssignPopupVc> {

	}

	private static final Binder binder = GWT.create(Binder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);


	/**
	 *
	 */
	public AssignPopupVc(String collectionIdVal, String collectionTitle, String collectionDescription) {
		super(false);
		res = AssignPopUpCBundle.INSTANCE;
		AssignPopUpCBundle.INSTANCE.css().ensureInjected();
		add(uiBinder.createAndBindUi(this));
		this.setGlassEnabled(true);
		this.getGlassElement().getStyle().setZIndex(99999);
		this.getElement().getStyle().setZIndex(99999);
		swithUrlLbl.setText(i18n.GL0639());
		swithUrlLbl.getElement().setAttribute("alt",i18n.GL0639());
		swithUrlLbl.getElement().setAttribute("title",i18n.GL0639());
		swithToEmbedLbl.setText(i18n.GL0640());
		swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0640());
		swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0640());
		AppClientFactory.getEventBus().addHandler(SetLoginStatusEvent.TYPE, setLoginStatusHandler);
		Window.enableScrolling(false);
		setLabelsAndIds();
		setHandlers();

		ftmPanel = new HTMLPanel("");

		htmlLoginPanel.setVisible(false);
		contentBorderPanel.setVisible(false);

		loadingImageLabel.setVisible(true);
		popupContentAssign.setVisible(false);

		AppClientFactory.getInjector().getClasspageService().getSCollIdClasspageById(collectionIdVal, new SimpleAsyncCallback<CollectionDo>(){
			@Override
			public void onSuccess(CollectionDo result) {
				toAssignStr = result.getGooruOid();
				if (result.getGooruOid() != null) {
					collectionDoGlobal = result;
					if (AppClientFactory.isAnonymous()) {
						hideContainers();
					} else {
						//loadListContainers();
					}
				}
				loadingImageLabel.setVisible(false);
				popupContentAssign.setVisible(true);
			}
		});
		generateShareLink(collectionIdVal,collectionTitle,collectionDescription);
		setShareUrlGenerationAsyncCallback(new SimpleAsyncCallback<Map<String,String>>() {
			@Override
			public void onSuccess(Map<String, String> result) {
				embedBitlyLink=result.get("decodeRawUrl");
			}
		});
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
		this.center();
		Window.enableScrolling(false);
		 boolean device = BrowserAgent.isDevice();
		if(device){
			ScrollPopupUtil.ScrollPopupUtilWidget(assignPopupContent,false);
		}
	}

	public void hideContainers() {
		htmlEvenPanelContainer.setVisible(false);
		htmlLoginPanel.setVisible(true);
		contentBorderPanel.setVisible(true);
	}

	public void loadListContainers() {
		AssignCollectionView assignWidget = new AssignCollectionView(
				collectionDoGlobal) {
			@Override
			public void closePoupfromChild() {
				hide();
			}
		};
		htmlEvenPanelContainer.add(assignWidget);
		this.setPopupPosition(0, (Window.getClientHeight()-527)/2);
		htmlEvenPanelContainer.setVisible(true);
		htmlLoginPanel.setVisible(false);
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
		if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY)){
		Window.enableScrolling(false);
		}else{
			Window.enableScrolling(true);
		}
	}

	/**
	 *
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
	 *
	 *
	 *
	 *
	 */
	public void setLabelsAndIds() {

		lblPii.setText(i18n.GL1892());
		lblPii.getElement().setId("spnPii");
		lblPii.getElement().setAttribute("alt",i18n.GL1892());
		lblPii.getElement().setAttribute("title",i18n.GL1892());

		ancprivacy.setText(i18n.GL1893());
		ancprivacy.getElement().setId("lnkAncprivacy");
		ancprivacy.getElement().setAttribute("alt",i18n.GL1893());
		ancprivacy.getElement().setAttribute("title",i18n.GL1893());

		toUsText.setText(i18n.GL1894());
		toUsText.getElement().setId("spnToUsText");
		toUsText.getElement().setAttribute("alt",i18n.GL1894());
		toUsText.getElement().setAttribute("title",i18n.GL1894());

		loginTxtBox.setPlaceholder(i18n.GL0202());
		loginTxtBox.getElement().setAttribute("placeholder",
				i18n.GL0202());
		loginTxtBox.setFocus(true);
		loginTxtBox.getElement().setId("tbLoginUsername");

		passwordTxtBox.setPlaceholder(i18n.GL0204());
		passwordTxtBox.getElement().setId("tbLoginPassword");

		forgotPwd.setText(i18n.GL0205());
		forgotPwd.getElement().setId("lnkForgotPwd");
		forgotPwd.getElement().setAttribute("alt",i18n.GL0205());
		forgotPwd.getElement().setAttribute("title",i18n.GL0205());

		loginButton.setText(i18n.GL0187());
		loginButton.getElement().setId("btnLogin");
		loginButton.getElement().setAttribute("alt",i18n.GL0187());
		loginButton.getElement().setAttribute("title",i18n.GL0187());

		lblPleaseWait.setText(i18n.GL0242());
		lblPleaseWait.getElement().setId("lblPleaseWait");
		lblPleaseWait.getElement().setAttribute("alt",i18n.GL0242());
		lblPleaseWait.getElement().setAttribute("title",i18n.GL0242());
		lblPleaseWait.setVisible(false);

		gmailButton.setText(i18n.GL0203());
		gmailButton.getElement().setId("btnAssignGmail");
		gmailButton.getElement().setAttribute("alt",i18n.GL0203());
		gmailButton.getElement().setAttribute("title",i18n.GL0203());

		lblOr.setText(i18n.GL0209());
		lblOr.getElement().setId("lblOr");
		lblOr.getElement().setAttribute("alt",i18n.GL0209());
		lblOr.getElement().setAttribute("title",i18n.GL0209());

		shareLinkTxtBox.setReadOnly(true);
		shareLinkTxtBox.addClickHandler(new OnClickShareHandler());

		assignDes.setText(i18n.GL0513());
		assignDes.getElement().setId("lblAssignDes");
		assignDes.getElement().setAttribute("alt",i18n.GL0513());
		assignDes.getElement().setAttribute("title",i18n.GL0513());

		lblAssignPopDes.setText(i18n.GL0514());
		lblAssignPopDes.getElement().setId("lblAssignPopDes");
		lblAssignPopDes.getElement().setAttribute("alt",i18n.GL0514());
		lblAssignPopDes.getElement().setAttribute("title",i18n.GL0514());

		lblAssignTitle.setText(i18n.GL0518());
		lblAssignTitle.getElement().setId("lblAssignTitle");
		lblAssignTitle.getElement().setAttribute("alt",i18n.GL0518());
		lblAssignTitle.getElement().setAttribute("title",i18n.GL0518());

		lblpopupTitle.setText(i18n.GL0519());
		lblpopupTitle.getElement().setId("lblpopupTitle");
		lblpopupTitle.getElement().setAttribute("alt",i18n.GL0519());
		lblpopupTitle.getElement().setAttribute("title",i18n.GL0519());

		lblLoginPopupTitle.setText(i18n.GL0520());
		lblLoginPopupTitle.getElement().setId("lblLoginPopupTitle");
		lblLoginPopupTitle.getElement().setAttribute("alt",i18n.GL0520());
		lblLoginPopupTitle.getElement().setAttribute("title",i18n.GL0520());

		donothaveAC.getElement().setInnerHTML(i18n.GL0634()+"&nbsp;");
		donothaveAC.getElement().setId("lblDonothaveAC");
		donothaveAC.getElement().setAttribute("alt",i18n.GL0634()+" ");
		donothaveAC.getElement().setAttribute("title",i18n.GL0634()+" ");

		ancSignUp.setText(i18n.GL0207()+i18n.GL_SPL_EXCLAMATION());
		ancSignUp.getElement().setId("lnkAncSignUp");
		ancSignUp.getElement().setAttribute("alt",i18n.GL0207()+i18n.GL_SPL_EXCLAMATION());
		ancSignUp.getElement().setAttribute("title",i18n.GL0207()+i18n.GL_SPL_EXCLAMATION());

		lblLoginwithGooru.setText(i18n.GL0346());
		lblLoginwithGooru.getElement().setId("lblLoginwithGooru");
		lblLoginwithGooru.getElement().setAttribute("alt",i18n.GL0346());
		lblLoginwithGooru.getElement().setAttribute("title",i18n.GL0346());

		ancSignUp.getElement().setAttribute("style", "float: left;");
		donothaveAC.getElement().setAttribute("style", "float: left;padding:0;");

		cancelButton.getElement().setId("btnCancelButton");
		loadingImageLabel.getElement().setId("pnlLoadingImageLabel");
		popupContentAssign.getElement().setId("pnlPopupContentAssign");
		shareLinkTxtBox.getElement().setId("tatShareLinkTxtBox");
		StringUtil.setAttributes(shareLinkTxtBox, true);
		swithUrlLbl.getElement().setId("lblSwithUrl");
		swithToEmbedLbl.getElement().setId("lblSwithToEmbedLbl");
		htmlLoginPanel.getElement().setId("epnlHtmlLoginPanel");
		signUpStyles.getElement().setId("pnlSignUpStyles");
		htmlEvenPanelContainer.getElement().setId("epnlHtmlEvenPanelContainer");
		contentBorderPanel.getElement().setId("epnlcontentBorderPanelId");
		contentBorderPanel.setVisible(false);
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
		if (swithToEmbedLbl.getText().equalsIgnoreCase(i18n.GL0639())
				&& swithUrlLbl.getText().equalsIgnoreCase(i18n.GL0640())) {
			if (buttonType.equalsIgnoreCase(SWITCH_TO_EMBED_LABEL)) {
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
		} else if (swithToEmbedLbl.getText()
				.equalsIgnoreCase(i18n.GL0640())
				&& swithUrlLbl.getText().equalsIgnoreCase(i18n.GL0639())) {
			if (buttonType.equalsIgnoreCase(SWITCH_TO_EMBED_LABEL)) {
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
		} else if (swithToEmbedLbl.getText().equalsIgnoreCase(i18n.GL0643())
				&& swithUrlLbl.getText().equalsIgnoreCase(i18n.GL0640())) {
			if (buttonType.equalsIgnoreCase(SWITCH_TO_EMBED_LABEL)) {
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
		} else if (swithToEmbedLbl.getText()
				.equalsIgnoreCase(i18n.GL0640())
				&& swithUrlLbl.getText().equalsIgnoreCase(i18n.GL0643())) {
			if (buttonType.equalsIgnoreCase(SWITCH_TO_EMBED_LABEL)) {
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
		} else if (swithToEmbedLbl.getText().equalsIgnoreCase(i18n.GL0639())
				&& swithUrlLbl.getText().equalsIgnoreCase(i18n.GL0643())) {
			if (buttonType.equalsIgnoreCase(SWITCH_TO_EMBED_LABEL)) {
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
		} else if (swithToEmbedLbl.getText().equalsIgnoreCase(i18n.GL0643())
				&& swithUrlLbl.getText().equalsIgnoreCase(i18n.GL0639())) {
			if (buttonType.equalsIgnoreCase(SWITCH_TO_EMBED_LABEL)) {
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
		/*
		 * if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.
		 * COLLECTION_SEARCH)){ MixpanelUtil.Share_direct_search(); } else
		 * if(AppClientFactory
		 * .getCurrentPlaceToken().equals(PlaceTokens.SHELF)){
		 * MixpanelUtil.Share_direct_collection_edit(); }
		 */
	}

	private String getIframeText() {
		String iframeText = null;
		if (embedBitlyLink == null) {
			embedBitlyLink = shareLinkTxtBox.getText();
		}
		iframeText = "<iframe width=\"1024px\" height=\"768px\" src=\""
				+ decodeRawUrl + "\" frameborder=\"0\" ></iframe>";
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
			changeShareUrlEvents(SWITCH_TO_EMBED_LABEL);
		}
	}

	public void setShareUrlGenerationAsyncCallback(
			SimpleAsyncCallback<Map<String, String>> shareShortenUrlAsyncCallback) {
		this.shareUrlGenerationAsyncCallback = shareShortenUrlAsyncCallback;
	}

	public SimpleAsyncCallback<Map<String, String>> getShareShortenUrlAsyncCallback() {
		return shareUrlGenerationAsyncCallback;
	}

	public void generateShareLink(final String classpageId, final String collectionTitle, final String collectionDesc) {
		Map<String, String> params = new HashMap<String, String>();
		String classpageItemId=null;
		String currentPlaceToken=AppClientFactory.getCurrentPlaceToken();
		params.put("type", "");
		if(currentPlaceToken!=null && currentPlaceToken.equals(PlaceTokens.COLLECTION_PLAY)){
			String classpageCollectionItemId=AppClientFactory.getPlaceManager().getRequestParameter("cid", null);
			String page=AppClientFactory.getPlaceManager().getRequestParameter("page", null);
			if(page!=null&&(page.equalsIgnoreCase("teach")||page.equalsIgnoreCase("study"))&&classpageCollectionItemId!=null){
				classpageItemId=classpageCollectionItemId;
			}
		}else if(currentPlaceToken!=null && currentPlaceToken.equals(PlaceTokens.ASSESSMENT_PLAY)){
			String classpageCollectionItemId=AppClientFactory.getPlaceManager().getRequestParameter("cid", null);
			if(classpageCollectionItemId!=null){
				classpageItemId=classpageCollectionItemId;
			}
		}
		params.put("type", currentPlaceToken);
		params.put("shareType", "");
		AppClientFactory.getInjector().getSearchService().getShortenShareUrlforAssign(classpageId, params,classpageItemId, new SimpleAsyncCallback<Map<String, String>>() {
			@Override
			public void onSuccess(Map<String, String> result) {
				decodeRawUrl = result.get("decodeRawUrl");
				shareLinkTxtBox.setText(decodeRawUrl);
				shareLinkTxtBox.getElement().setAttribute("alt",decodeRawUrl);
				shareLinkTxtBox.getElement().setAttribute("title",decodeRawUrl);
				bitlyLink = result.get("shortenUrl");
				rawUrl = result.get("rawUrl");
				addShareWidgetInPlay(decodeRawUrl, rawUrl,collectionTitle,collectionDesc,bitlyLink, "","public");
			}
		});
	}

	public void addShareWidgetInPlay(String link, String rawUrl, String title,
			String desc, String shortenUrl, String type, String shareType) {
		try {
			SocialShareDo shareDo = new SocialShareDo();
			shareDo.setBitlylink(link);
			shareDo.setRawUrl(rawUrl);
			shareDo.setTitle(title);
			shareDo.setDescription(desc);
			shareDo.setThumbnailurl(shortenUrl);
			shareDo.setCategoryType(type);
			shareDo.setOnlyIcon(false);
			shareDo.setShareType(shareType);
			shareDo.setDecodeRawUrl(link);
			} catch (Exception ex) {
				AppClientFactory.printSevereLogger(ex.getMessage());
		}
	}

	/**
	 * Added click handler to perform Login operation by taking entered user
	 * name and password from the user.
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */

	@UiHandler("loginButton")
	public void onLoginClicked(ClickEvent clickEvent) {

		if (isCookieEnabled()) {

			String username = loginTxtBox.getText().trim();
			String password = StringUtil.getCryptoData(passwordTxtBox.getText().trim());

			if (username.length() > 1 && password.length() > 1) {

				loginButton.setVisible(false);
				lblPleaseWait.setVisible(true);

				AppClientFactory.getInjector().getAppService().v2Signin(username,password, new SimpleAsyncCallback<UserDo>() {
					@Override
					public void onSuccess(UserDo result) {

						int statusCode = result.getStatusCode();
						String errorCode = null;
						String errorMessage = null;
						if (result.getResponseDo() !=null){
							errorCode = result.getResponseDo().getErrorCode();
							errorMessage = result.getResponseDo().getErrorMessage();
						}

						if(statusCode==HTTP_SUCCESS_STATUS_CODE){

							MixpanelUtil.Regular_User_Logged_In();
							AppClientFactory.setLoggedInUser(result);
							AppClientFactory.fireEvent(new SetUserDetailsInPlayEvent(result.getToken()));
							AppClientFactory.fireEvent(new SetUserDetailsInCollectionPlayEvent(result.getToken(),result.getGooruUId()));

							AppClientFactory.fireEvent(new SetHeaderEvent(result));

							if (result.getUsername().equalsIgnoreCase("TexasTeacher")) {
								AppClientFactory.fireEvent(new SetTexasAccountEvent("failure"));
								AppClientFactory.fireEvent(new SetTexasPlaceHolderEvent(true));
							} else {
								AppClientFactory.fireEvent(new SetTexasAccountEvent("success"));
								AppClientFactory.fireEvent(new SetTexasPlaceHolderEvent(false));
							}

							if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.ASSESSMENT_PLAY) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.COLLECTION_PLAY) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.ASSESSMENT_PLAY)){
								AppClientFactory.fireEvent(new SetPlayerLoginStatusEvent(true));
								AppClientFactory.fireEvent(new AssessmentsSetPlayerLoginStatusEvent(true));
							}

							AppClientFactory.setUserflag(true);
							AppClientFactory.resetPlace();
							hide();
							//loadListContainers();
							MixpanelUtil.mixpanelEvent("Login_FromAssign_Pop-up");
						}else if(statusCode==HTTP_UNAUTHORISED_STATUS_CODE){
							loginButton.setVisible(true);
							lblPleaseWait.setVisible(false);
							if (errorCode.equalsIgnoreCase(ERR_GL0078)){
								new AlertContentUc(i18n.GL1966(), i18n.GL0347());
							}else if (errorCode.equalsIgnoreCase(ERR_GL0079)){
								// For blocked users
								new AlertContentUc(i18n.GL1966(), i18n.GL1938());
							}else if (errorCode.equalsIgnoreCase(ERR_GL010501)){
								new AlertContentUc(i18n.GL1966(), i18n.GL3114());
							}else if (errorCode.equalsIgnoreCase(ERR_GL010502)){
								new AlertContentUc(i18n.GL1966(), i18n.GL0347());
							}else if (errorCode.equalsIgnoreCase(ERR_GL010503)){
								new AlertContentUc(i18n.GL1966(), i18n.GL0347());
							}else if (errorCode.equalsIgnoreCase(ERR_GL0081)){
								new AlertContentUc(i18n.GL1966(), i18n.GL3119());
							}
						}else{
							new AlertContentUc(i18n.GL1966(), errorMessage);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						loginButton.setVisible(true);
						lblPleaseWait.setVisible(false);
						//										new AlertContentUc(i18n.GL0061(), i18n.GL0347());
					}
				});
			} else {
				loginButton.setVisible(true);
				lblPleaseWait.setVisible(false);
				new AlertContentUc(i18n.GL0061(), i18n.GL0347());
			}
		} else {
			loginButton.setVisible(true);
			lblPleaseWait.setVisible(false);
			new AlertMessageUc(i18n.GL0738(), new HTML(i18n.GL0348()));
		}

	}

	/**
	 * creating inner class implementing KeyUpHandler interface.
	 *
	 */
	public class LoginKeyupHandler implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				onLoginClicked(null);
			}
		}

	}

	private void setHandlers() {
		loginTxtBox.addKeyUpHandler(new LoginKeyupHandler());
		passwordTxtBox.addKeyUpHandler(new LoginKeyupHandler());
	}

	@UiHandler("forgotPwd")
	public void onForgotPwdClicked(ClickEvent clickEvent) {

		ForgotPasswordVc forgotPasswordVc = new ForgotPasswordVc();
		forgotPasswordVc.setGlassEnabled(true);
		forgotPasswordVc.show();
		forgotPasswordVc.center();
		hide();
	}

	@UiHandler("ancSignUp")
	public void onSignUp(ClickEvent clickEvent) {
		MixpanelUtil.mixpanelEvent("Library_Assign_Signup");
		Map<String, String> params = StringUtil.splitQuery(Window.Location.getHref());
		if(params.containsKey("query"))
		{
			String queryVal = AppClientFactory.getPlaceManager().getRequestParameter("query");
			params.put("query", queryVal);
		}
		if(params.containsKey("flt.subjectName"))
		{
			String subjectNameVal = AppClientFactory.getPlaceManager().getRequestParameter("flt.subjectName");
			params.put("flt.subjectName", subjectNameVal);
		}
		params.put("callback", "signup");
		params.put("type", "1");
		LibraryTopicListView.isAssignPopup=false;
		ProfileTopicListView.isAssignPopup=false;
		if(params.containsKey("assign"))
		{
			params.remove("assign");
		}
    	PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, false);
		hide();
	}

	/**
	 * User login popup allows to sign in if correct credentials and set user
	 * info on header view
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("gmailButton")
	public void onGmailButtonClicked(ClickEvent clickEvent){
		DataLogEvents.signIn(GwtUUIDGenerator.uuid(),"login",PlayerDataLogEvents.getUnixTime(),PlayerDataLogEvents.getUnixTime(), "", AppClientFactory.getLoggedInUser().getToken());
		String callBack = Window.Location.getHref();
		AppClientFactory.getInjector().getSearchService().getGoogleSignin(callBack, new SimpleAsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				MixpanelUtil.mixpanelEvent("Assign_library_signin_google");
				Window.Location.replace(result);
			}
		});
	}

	public class OnClickShareHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			shareLinkTxtBox.selectAll();
			shareLinkTxtBox.setFocus(true);
		}

	}

	SetLoginStatusHandler setLoginStatusHandler = new SetLoginStatusHandler() {
		@Override
		public void setLoginStatusHandler(boolean isLoggedIn) {
			if(isLoggedIn) {
				//loadListContainers();
			}
		}
	};

	@UiHandler("ancprivacy")
	public void onClickPrivacyAnchor(ClickEvent clickEvent){
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		termsOfUse=new TermsOfUse(){

			@Override
			public void openParentPopup() {
				Window.enableScrolling(true);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
			}

		};
		termsOfUse.show();
		termsOfUse.center();
		termsOfUse.getElement().getStyle().setZIndex(999999);//To display the view in collection player.
	}

	private static native boolean isCookieEnabled() /*-{
													return navigator.cookieEnabled;
													}-*/;
}
