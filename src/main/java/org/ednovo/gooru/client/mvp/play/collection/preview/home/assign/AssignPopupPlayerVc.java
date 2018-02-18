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
package org.ednovo.gooru.client.mvp.play.collection.preview.home.assign;

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
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.home.ForgotPasswordVc;
import org.ednovo.gooru.client.mvp.home.event.SetTexasAccountEvent;
import org.ednovo.gooru.client.mvp.home.event.SetTexasPlaceHolderEvent;
import org.ednovo.gooru.client.mvp.home.event.SetUserDetailsInCollectionPlayEvent;
import org.ednovo.gooru.client.mvp.home.event.SetUserDetailsInPlayEvent;
import org.ednovo.gooru.client.mvp.home.library.assign.AssignCollectionView;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AlertContentUc;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.client.util.ScrollPopupUtil;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.DataLogEvents;
import org.ednovo.gooru.shared.util.GwtUUIDGenerator;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
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

/**
 * @author BLR Team
 *
 */
public abstract class AssignPopupPlayerVc extends PopupPanel implements ClientConstants{

	@UiField
	HTMLPanel loadingImageLabel,popupContentAssign,assignPopupContent;

	@UiField
	HTMLEventPanel htmlEvenPanelContainer;
	@UiField
	HTMLEventPanel htmlLoginPanel;

	@UiField
	TextBoxWithPlaceholder loginTxtBox;

	@UiField
	TextBoxWithPlaceholder passwordTxtBox;

	@UiField
	Button loginButton,gmailButton;

	@UiField
	TextArea shareLinkTxtBox;

	@UiField
	Anchor forgotPwd,ancSignUp;

	@UiField
	Label cancelButton,lblPleaseWait,assignDes,lblAssignPopDes,lblAssignTitle,lblpopupTitle,lblLoginPopupTitle,donothaveAccountText,lblOr;

	@UiField
	Anchor  swithUrlLbl, swithToEmbedLbl;

	@UiField InlineLabel lblPii,toUsText;

	@UiField Anchor ancprivacy;

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
	private static AssignPopupPlayerVcUiBinder uiBinder = GWT
			.create(AssignPopupPlayerVcUiBinder.class);

	public interface AssignPopupPlayerVcUiBinder extends
	UiBinder<Widget, AssignPopupPlayerVc> {
	}

	@UiField(provided = true)
	AssignPopUpCBundle res;

	@UiTemplate("AssignPopupPlayerVc.ui.xml")
	interface Binder extends UiBinder<Widget, AssignPopupPlayerVc> {

	}
	private static final Binder binder = GWT.create(Binder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);
	/**
	 *
	 */
	public AssignPopupPlayerVc(String collectionIdVal) {
		super(false);
		res = AssignPopUpCBundle.INSTANCE;
		AssignPopUpCBundle.INSTANCE.css().ensureInjected();
		add(uiBinder.createAndBindUi(this));
		this.setGlassEnabled(true);
		donothaveAccountText.getElement().setInnerHTML(i18n.GL0634());
		donothaveAccountText.getElement().setAttribute("style", "float: left;margin-left: 22%;");
		ancSignUp.setText(i18n.GL0207()+i18n.GL_SPL_EXCLAMATION());

		swithUrlLbl.setText(i18n.GL0639());
		swithToEmbedLbl.setText(i18n.GL0640());
		ancSignUp.getElement().setAttribute("style", "float: left;margin-left: 2%;");

		setLabelsAndIds();
		setHandlers();

		ftmPanel = new HTMLPanel("");

		htmlLoginPanel.setVisible(false);

		loadingImageLabel.setVisible(true);
		popupContentAssign.setVisible(false);
		try{
			AppClientFactory.getInjector().getClasspageService().getSCollIdClasspageById(collectionIdVal, new SimpleAsyncCallback<CollectionDo>(){
				@Override
				public void onSuccess(CollectionDo result) {
					MixpanelUtil.Preview_Click_Assign_successful();
					String collectionId = "";
					if(result.getGooruOid() != null){
						collectionId = result.getGooruOid();
					}
					toAssignStr = collectionId;
					if (collectionId != null) {
						collectionDoGlobal = result;
						if (AppClientFactory.isAnonymous()) {
							hideContainers();
						} else {
							loadListContainers();
						}
						generateShareLink(toAssignStr);
					}
					loadingImageLabel.setVisible(false);
					popupContentAssign.setVisible(true);
				}
			});
		}
		catch(Exception ex)
		{
			AppClientFactory.printSevereLogger("AssignPopupPlayerVc : Constructor : "+ex.getMessage());
		}
		setShareUrlGenerationAsyncCallback(new SimpleAsyncCallback<Map<String,String>>() {
			@Override
			public void onSuccess(Map<String, String> result) {
				if(result.containsKey("decodeRawUrl")){
					embedBitlyLink=result.get("decodeRawUrl");
				}
			}
		});
		Window.enableScrolling(false);
		this.getElement().setAttribute("style", "z-index:99999;");
		this.getGlassElement().setAttribute("style", "z-index:99999; position:absolute; left:0px; top:0px;");

		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99999, false));
		this.center();
		ScrollPopupUtil.ScrollPopupUtilWidget(assignPopupContent,false);
	}

	public void hideContainers() {
		htmlEvenPanelContainer.setVisible(false);
		htmlLoginPanel.setVisible(true);
	}

	public void loadListContainers() {
		AssignCollectionView assignWidget = new AssignCollectionView(collectionDoGlobal) {
			@Override
			public void closePoupfromChild() {
				closePoup();
			}
		};
		htmlEvenPanelContainer.add(assignWidget);
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
		lblPii.getElement().setId("spnLblPii");
		lblPii.getElement().setAttribute("alt",i18n.GL1892());
		lblPii.getElement().setAttribute("title",i18n.GL1892());

		ancprivacy.setText(i18n.GL1893());
		ancprivacy.getElement().setId("lnkAncprivacy");
		ancprivacy.getElement().setAttribute("alt",i18n.GL1893());
		ancprivacy.getElement().setAttribute("title",i18n.GL1893());
		ancprivacy.getElement().getStyle().setMarginLeft(5, Unit.PX);

		toUsText.setText(i18n.GL1894());
		toUsText.getElement().setId("spnToUsText");
		toUsText.getElement().setAttribute("alt",i18n.GL1894());
		toUsText.getElement().setAttribute("title",i18n.GL1894());

		forgotPwd.getElement().setId("lnkForgotPwd");
		forgotPwd.setText(i18n.GL0205());
		forgotPwd.getElement().setAttribute("alt",i18n.GL0205());
		forgotPwd.getElement().setAttribute("title",i18n.GL0205());

		loginTxtBox.setPlaceholder(i18n.GL0202());
		loginTxtBox.getElement().setAttribute("placeholder",i18n.GL0202());
		loginTxtBox.setFocus(true);
		loginTxtBox.getElement().setId("tbLoginUsername");

		passwordTxtBox.setPlaceholder(i18n.GL0204());
		passwordTxtBox.getElement().setId("tbLoginPassword");

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
		gmailButton.getElement().setId("btnGmailButton");
		gmailButton.getElement().setAttribute("alt",i18n.GL0203());
		gmailButton.getElement().setAttribute("title",i18n.GL0203());

		lblOr.setText(i18n.GL0209());
		lblOr.getElement().setId("lblOr");
		lblOr.getElement().setAttribute("alt",i18n.GL0209());
		lblOr.getElement().setAttribute("title",i18n.GL0209());

		shareLinkTxtBox.setReadOnly(true);
		shareLinkTxtBox.addClickHandler(new OnClickShareHandler());
		shareLinkTxtBox.getElement().setId("tatShareLinkTxtBox");
		StringUtil.setAttributes(shareLinkTxtBox, true);

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

		loginButton.setText(i18n.GL0187());
		ancSignUp.setText(i18n.GL0207()+i18n.GL_SPL_EXCLAMATION());
		ancSignUp.getElement().setId("lnkAncSignUp");
		ancSignUp.getElement().setAttribute("alt",i18n.GL0207());
		ancSignUp.getElement().setAttribute("title",i18n.GL0207());

		cancelButton.getElement().setId("lblCancelButton");
		loadingImageLabel.getElement().setId("pnlLoadingImageLabel");
		popupContentAssign.getElement().setId("pnlPopupContentAssign");
		htmlLoginPanel.getElement().setId("epnlHtmlLoginPanel");
		donothaveAccountText.getElement().setId("lblDonothaveAccountText");
		htmlEvenPanelContainer.getElement().setId("epnlHtmlEvenPanelContainer");
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
			if(buttonType.equalsIgnoreCase(SWITCH_TO_EMBED_LABEL)) {
				shareLinkTxtBox.setText(bitlyLink);
				shareLinkTxtBox.getElement().setAttribute("alt",bitlyLink);
				shareLinkTxtBox.getElement().setAttribute("title",bitlyLink);
				swithUrlLbl.setText(i18n.GL0640());
				swithUrlLbl.getElement().setAttribute("alt",i18n.GL0640());
				swithUrlLbl.getElement().setAttribute("title",i18n.GL0640());
				swithToEmbedLbl.setText(i18n.GL0643());
				swithToEmbedLbl.getElement().setAttribute("alt",i18n.GL0643());
				swithToEmbedLbl.getElement().setAttribute("title",i18n.GL0643());
			}else{
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

	public void generateShareLink(String classpageId) {
		Map<String, String> params = new HashMap<String, String>();
		String currentPlaceToken=AppClientFactory.getCurrentPlaceToken();
		params.put("type", currentPlaceToken);
		params.put("shareType", "");
		AppClientFactory.getInjector().getSearchService().getShortenShareUrlforAssign(classpageId, params,null,new SimpleAsyncCallback<Map<String, String>>() {
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
				addShareWidgetInPlay(decodeRawUrl, rawUrl,
						collectionDoGlobal.getTitle(),
						collectionDoGlobal.getDescription(),
						bitlyLink, "",
						collectionDoGlobal.getSharing());
			}

		});
		params.put("shareType", "embed");
		AppClientFactory.getInjector().getSearchService().getShortenShareUrl(classpageId, params, getShareShortenUrlAsyncCallback());
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
			shareDo.setDecodeRawUrl(link);
			shareDo.setShareType(shareType);
		} catch (Exception ex) {
			AppClientFactory.printSevereLogger("AssignPopupPlayerVc : addShareWidgetInPlay : "+ex.getMessage());
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

			if(username.length() > 1 && password.length() > 1){
				loginButton.setVisible(false);
				lblPleaseWait.setVisible(true);
				AppClientFactory.getInjector().getAppService().v2Signin(username,password,new SimpleAsyncCallback<UserDo>(){
					@Override
					public void onSuccess(UserDo result) {
						if(result.getActive()==1){
							MixpanelUtil.Regular_User_Logged_In();
							AppClientFactory.setLoggedInUser(result);
							AppClientFactory.fireEvent(new SetUserDetailsInPlayEvent(result.getToken()));
							AppClientFactory.fireEvent(new SetUserDetailsInCollectionPlayEvent(result.getToken(),result.getGooruUId()));
							AppClientFactory.fireEvent(new SetHeaderEvent(result));
							if(TEXAS_TEACHER.equalsIgnoreCase(result.getUsername())) {
								AppClientFactory.fireEvent(new SetTexasAccountEvent("failure"));
								AppClientFactory.fireEvent(new SetTexasPlaceHolderEvent(true));
							}else{
								AppClientFactory.fireEvent(new SetTexasAccountEvent("success"));
								AppClientFactory.fireEvent(new SetTexasPlaceHolderEvent(false));
							}
							AppClientFactory.setUserflag(true);
							AppClientFactory.resetPlace();
							loadListContainers();
							MixpanelUtil.mixpanelEvent("Login_FromAssign_Pop-up");
							hide();
							// heeere add widget
						}else if(result.getActive()==0){
							loginButton.setVisible(true);
							lblPleaseWait.setVisible(false);
							new AlertContentUc(i18n.GL1966(), i18n.GL1938());
						}
					}
					@Override
					public void onFailure(Throwable caught) {
						loginButton.setVisible(true);
						lblPleaseWait.setVisible(false);
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
		this.setSize("515px", "547px");
		loginTxtBox.addKeyUpHandler(new LoginKeyupHandler());
		passwordTxtBox.addKeyUpHandler(new LoginKeyupHandler());
	}

	@UiHandler("forgotPwd")
	public void onForgotPwdClicked(ClickEvent clickEvent) {
		ForgotPasswordVc forgotPasswordVc = new ForgotPasswordVc();
		forgotPasswordVc.setGlassEnabled(true);
		forgotPasswordVc.show();
		forgotPasswordVc.center();
		closePoup();
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
		AppClientFactory.getPlaceManager().revealPlace(
				AppClientFactory.getCurrentPlaceToken(), params);
		closePoup();
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
				MixpanelUtil.mixpanelEvent("Assign_player_signin_google");
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

	@UiHandler("ancprivacy")
	public void onClickPrivacyAnchor(ClickEvent clickEvent){
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		termsOfUse=new TermsOfUse(){
			@Override
			public void openParentPopup() {
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				Window.enableScrolling(false);
			}
		};
		termsOfUse.show();
		termsOfUse.center();
		termsOfUse.getElement().getStyle().setZIndex(999999);//To display the view in collection player.
	}

	private static native boolean isCookieEnabled()/*-{
		return navigator.cookieEnabled;
	}-*/;
}
