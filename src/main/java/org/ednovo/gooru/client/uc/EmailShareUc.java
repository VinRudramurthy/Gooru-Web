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
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.social.SocialShareDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.socialshare.SentEmailSuccessVc;
import org.ednovo.gooru.client.util.ScrollPopupShareUtil;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.InitializeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @fileName : EmailShareUc.java
 *
 * @description : This file used to show popUp for share the data through email.
 *
 * @version : 5.4
 *
 * @date:  August, 2013.
 *
 * @Author: Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class EmailShareUc extends PopupPanel{

	private static EmailShareUcUiBinder uiBinder = GWT
			.create(EmailShareUcUiBinder.class);

	interface EmailShareUcUiBinder extends UiBinder<Widget, EmailShareUc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	Label  cancelLbl, fromValidation, toValidation, checkCopyEmail, lblEmailFriend,lblFrom, lblTo,lblSendMeCopy,lblSubject,lblMessage,mandatoryErrorLbl,mandatoryErrorRichTextArea,noteTxt;

	@UiField
	Button btnSend;

	@UiField
	TextBoxWithPlaceholder toTxt, fromTxt;

	@UiField
	TextBox subTxt;

	@UiField HTMLPanel mainShareContainer;

	@UiField
	RichTextArea msgTxa;

	@UiField InlineLabel lblPii,toUsText;
	@UiField Anchor ancprivacy;

	/*
	 * @UiField CheckBox checkCopyEmail;
	 */

	SocialShareDo socialShareDo;

	private static boolean isCheckedValue;

	private static boolean isvalid;

	private SimpleAsyncCallback<Void> socialShareAsyncCallback = null;

	private String cfm = "no";

	private String loggedEmailId;
	boolean isHavingBadWordsInTextbox=false,isHavingBadWordsInRichText=false;
	private int count=0;

	private TermsOfUse termsOfUse;

	Boolean isIpad,isAndriod,isWinDskp;


	private static final String AT_SYMBOL = "@";

	/**
	 * Class constructor , create a new pop up
	 * @param socialDo, Object of SocialShareDo.
	 */
	public EmailShareUc(SocialShareDo socialDo) {
		super(false);
		setWidget(uiBinder.createAndBindUi(this));
		this.socialShareDo = socialDo;
		setSocialShareAsyncCallback(new SimpleAsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
			}

		});
		this.getElement().getStyle().setZIndex(999999);
		this.setGlassStyleName("loginPopupGlassStyle");
		fromValidation.setText(i18n.GL0215());
		fromValidation.getElement().setId("lblFromValidation");
		fromValidation.getElement().setAttribute("alt", i18n.GL0215());
		fromValidation.getElement().setAttribute("title", i18n.GL0215());
		toValidation.setText(i18n.GL0216());
		toValidation.getElement().setId("lblToValidation");
		toValidation.getElement().setAttribute("alt", i18n.GL0216());
		toValidation.getElement().setAttribute("title", i18n.GL0216());
		lblEmailFriend.setText(i18n.GL0222());

		lblEmailFriend.getElement().setId("lblLblEmailFriend");
		lblEmailFriend.getElement().setAttribute("alt", i18n.GL0222());
		lblEmailFriend.getElement().setAttribute("title", i18n.GL0222());
		lblFrom.setText(i18n.GL0223()+i18n.GL_SPL_SEMICOLON()+" ");
		lblFrom.getElement().setId("lblLblFrom");
		lblFrom.getElement().setAttribute("alt", i18n.GL0223()+i18n.GL_SPL_SEMICOLON());
		lblFrom.getElement().setAttribute("title", i18n.GL0223()+i18n.GL_SPL_SEMICOLON());
		lblTo.setText(i18n.GL0224()+i18n.GL_SPL_SEMICOLON()+" ");
		lblTo.getElement().setId("lblLblTo");
		lblTo.getElement().setAttribute("alt", i18n.GL0224()+i18n.GL_SPL_SEMICOLON());
		lblTo.getElement().setAttribute("title", i18n.GL0224()+i18n.GL_SPL_SEMICOLON());
		lblSendMeCopy.setText(i18n.GL0225());
		lblSendMeCopy.getElement().setId("lblLblSendMeCopy");
		lblSendMeCopy.getElement().setAttribute("alt", i18n.GL0225());
		lblSendMeCopy.getElement().setAttribute("title", i18n.GL0225());
		lblSubject.setText(i18n.GL0226()+i18n.GL_SPL_SEMICOLON()+" ");
		lblSubject.getElement().setId("lblLblSubject");
		lblSubject.getElement().setAttribute("alt", i18n.GL0226());
		lblSubject.getElement().setAttribute("title", i18n.GL0226());
		lblMessage.setText(i18n.GL0227()+i18n.GL_SPL_SEMICOLON()+" ");
		lblMessage.getElement().setId("lblLblMessage");
		lblMessage.getElement().setAttribute("alt", i18n.GL0227());
		lblMessage.getElement().setAttribute("title", i18n.GL0227());

		btnSend.setText(i18n.GL0228());
		btnSend.getElement().setAttribute("alt", i18n.GL0228());
		btnSend.getElement().setAttribute("title", i18n.GL0228());
		cancelLbl.setText(i18n.GL0142());
		cancelLbl.getElement().setAttribute("alt", i18n.GL0142());
		cancelLbl.getElement().setAttribute("title", i18n.GL0142());
		fromTxt.setMaxLength(50);
		lblPii.setText(i18n.GL1892());
		lblPii.getElement().setId("inlineLblLblPii");
		lblPii.getElement().setAttribute("alt", i18n.GL1892());
		lblPii.getElement().setAttribute("title", i18n.GL1892());
		ancprivacy.setText(i18n.GL1893());
		ancprivacy.getElement().setId("lnkAncprivacy");
		ancprivacy.getElement().setAttribute("alt", i18n.GL1893());
		ancprivacy.getElement().setAttribute("title", i18n.GL1893());
		toUsText.setText(i18n.GL1894());
		toUsText.getElement().setId("inlineLblToUsText");
		toUsText.getElement().setAttribute("alt", i18n.GL1894());
		toUsText.getElement().setAttribute("title", i18n.GL1894());

		mandatoryErrorLbl.getElement().setId("lblMandatoryErrorLbl");
		mandatoryErrorLbl.setVisible(false);
		mandatoryErrorRichTextArea.getElement().setId("lblMandatoryErrorRichTextArea");
		mandatoryErrorRichTextArea.setVisible(false);

		fromValidation.setVisible(false);
		toValidation.setVisible(false);

		noteTxt.setText(i18n.GL1636());
		noteTxt.getElement().setId("lblNoteTxt");
		noteTxt.getElement().setAttribute("alt", i18n.GL1636());
		noteTxt.getElement().setAttribute("title", i18n.GL1636());

		if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.EDIT_CLASSPAGE)){
			noteTxt.setVisible(false);
		}else{
			noteTxt.setVisible(true);
		}

		if(!AppClientFactory.isAnonymous())
		{
		loggedEmailId=AppClientFactory.getLoggedInUser().getFirstName() + " " +AppClientFactory.getLoggedInUser().getLastName();
		}
		else
		{
			loggedEmailId = "";
		}
		cancelLbl.getElement().setId("lblCancel");
		toTxt.getElement().setId("tbTo");
		subTxt.getElement().setId("tbSubject");
		StringUtil.setAttributes(subTxt, true);
		fromTxt.getElement().setId("tbFrom");
		msgTxa.getElement().setId("taMessage");
		StringUtil.setAttributes(msgTxa, true);
		btnSend.getElement().setId("btnSend");
		checkCopyEmail.getElement().setId("lblCheckCopyEmail");
		fromTxt.setText(loggedEmailId);
		fromTxt.getElement().setAttribute("alt", loggedEmailId);
		fromTxt.getElement().setAttribute("title",loggedEmailId);
		toTxt.setPlaceholder(i18n.GL1184_1());
		fromTxt.addBlurHandler(new CheckProfanityInOnBlur(fromTxt,null, fromValidation));
		subTxt.addBlurHandler(new CheckProfanityInOnBlur(subTxt,null, mandatoryErrorLbl));
		msgTxa.addBlurHandler(new CheckProfanityInOnBlur(null,msgTxa, mandatoryErrorRichTextArea));
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.PROFILE_PAGE)){
			if(socialShareDo.getIsSearchShare()){
				subTxt.setText(StringUtil.generateMessage(i18n.GL0218(), socialShareDo.getTitle()));
				subTxt.getElement().setAttribute("alt", StringUtil.generateMessage(i18n.GL0218(), socialShareDo.getTitle()));
				subTxt.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0218(), socialShareDo.getTitle()));
				msgTxa.setHTML(StringUtil.generateMessage(i18n.GL0219(), socialShareDo.getTitle(), socialShareDo.getDecodeRawUrl(), AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
				msgTxa.getElement().setAttribute("alt", StringUtil.generateMessage(i18n.GL0219(), socialShareDo.getTitle(), socialShareDo.getDecodeRawUrl(), AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
				msgTxa.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0219(), socialShareDo.getTitle(), socialShareDo.getDecodeRawUrl(), AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
			}else{
				subTxt.setText(StringUtil.generateMessage(i18n.GL0220(), socialShareDo.getTitle()));
				subTxt.getElement().setAttribute("alt", StringUtil.generateMessage(i18n.GL0220(), socialShareDo.getTitle()));
				subTxt.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0220(), socialShareDo.getTitle()));
				msgTxa.setHTML(StringUtil.generateMessage(i18n.GL0221(), socialShareDo.getTitle(), socialShareDo.getBitlylink(), AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
				msgTxa.getElement().setAttribute("alt", StringUtil.generateMessage(i18n.GL0221(), socialShareDo.getTitle(), socialShareDo.getBitlylink(), AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
				msgTxa.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0221(), socialShareDo.getTitle(), socialShareDo.getBitlylink(), AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
			}
		}else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.EDIT_CLASSPAGE)){
			   lblEmailFriend.setText(i18n.GL0222_1());
			   lblEmailFriend.getElement().setAttribute("alt", i18n.GL0222_1());
				lblEmailFriend.getElement().setAttribute("title", i18n.GL0222_1());
			   subTxt.setText(StringUtil.generateMessage(i18n.GL0218_1(), socialShareDo.getCategoryType(), "\"" + socialShareDo.getTitle() + "\""));
			   subTxt.getElement().setAttribute("alt", StringUtil.generateMessage(i18n.GL0218_1(), socialShareDo.getCategoryType(), "\"" + socialShareDo.getTitle() + "\""));
			   subTxt.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0218_1(), socialShareDo.getCategoryType(), "\"" + socialShareDo.getTitle() + "\""));
			   msgTxa.setHTML(StringUtil.generateMessage(i18n.GL0219_1(), socialShareDo.getCategoryType(), socialShareDo.getTitle(), socialShareDo.getBitlylink(), socialShareDo.getDecodeRawUrl(), AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
			   msgTxa.getElement().setAttribute("alt", StringUtil.generateMessage(i18n.GL0219_1(), socialShareDo.getCategoryType(), socialShareDo.getTitle(), socialShareDo.getBitlylink(), socialShareDo.getDecodeRawUrl(), AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
			   msgTxa.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0219_1(), socialShareDo.getCategoryType(), socialShareDo.getTitle(), socialShareDo.getBitlylink(), socialShareDo.getDecodeRawUrl(), AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));

		}else{
			if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SEARCH_RESOURCE)){
				subTxt.setText(StringUtil.generateMessage(i18n.GL1997(),i18n.GL2000()));
				subTxt.getElement().setAttribute("alt", StringUtil.generateMessage(i18n.GL1997(),i18n.GL2000()));
				subTxt.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL1997(),i18n.GL2000()));
				msgTxa.setHTML(StringUtil.generateMessage(i18n.GL1999(),AppClientFactory.getLoggedInUser().getUsername(),i18n.GL2000(),socialShareDo.getTitle(),socialShareDo.getDecodeRawUrl(),AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
				msgTxa.getElement().setAttribute("alt", StringUtil.generateMessage(i18n.GL1999(),AppClientFactory.getLoggedInUser().getUsername(),i18n.GL2000(),socialShareDo.getTitle(),socialShareDo.getDecodeRawUrl(),AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
				msgTxa.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL1999(),AppClientFactory.getLoggedInUser().getUsername(),i18n.GL2000(),socialShareDo.getTitle(),socialShareDo.getDecodeRawUrl(),AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));

			}else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SEARCH_COLLECTION) || AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.PREVIEW_PLAY) || AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.COLLECTION_PLAY) || AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.ASSESSMENT_PLAY) || AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SHELF) ){
				subTxt.setText(StringUtil.generateMessage(i18n.GL1997(),i18n.GL2001()));
				subTxt.getElement().setAttribute("alt", StringUtil.generateMessage(i18n.GL1997(),i18n.GL2001()));
				subTxt.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL1997(),i18n.GL2001()));
				msgTxa.setHTML(StringUtil.generateMessage(i18n.GL1999(),AppClientFactory.getLoggedInUser().getUsername(),i18n.GL2001(),socialShareDo.getTitle(),socialShareDo.getDecodeRawUrl(),AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
				msgTxa.getElement().setAttribute("alt", StringUtil.generateMessage(i18n.GL1999(),AppClientFactory.getLoggedInUser().getUsername(),i18n.GL2001(),socialShareDo.getTitle(),socialShareDo.getDecodeRawUrl(),AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
				msgTxa.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL1999(),AppClientFactory.getLoggedInUser().getUsername(),i18n.GL2001(),socialShareDo.getTitle(),socialShareDo.getDecodeRawUrl(),AppClientFactory.getLoggedInUser().getSettings().getHomeEndPoint()));
			}
		}

		isCheckedValue = false;
		isvalid = true;
		Window.enableScrolling(false);
		this.setGlassEnabled(true);
		this.center();
		msgTxa.addInitializeHandler(new InitializeHandler() {
			@Override
			public void onInitialize(InitializeEvent event) {
				Document document = IFrameElement.as(msgTxa.getElement()).getContentDocument();
				BodyElement body = document.getBody();
				body.setAttribute("style", "font-family: Arial;font-size:12px;");
			}
		});
		ScrollPopupShareUtil.ScrollPopupUtilWidget(mainShareContainer);
	}
	/**
	 * Hide {@link EmailShareUc} popup
	 * @param clickEvent instOLance of {@link ClickEvent}
	 */
	@UiHandler("cancelLbl")
	public void onCancelClickEvent(ClickEvent event) {
		this.hide();
		String placeToken = AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		triggerEmailEvent(false);
		if(!placeToken.equals(PlaceTokens.ASSESSMENT_PLAY) || !placeToken.equals(PlaceTokens.COLLECTION_PLAY) || !placeToken.equals(PlaceTokens.PREVIEW_PLAY)|| !placeToken.equals(PlaceTokens.RESOURCE_PLAY)) {
			Window.enableScrolling(true);
		}
		if (placeToken.equals(PlaceTokens.SEARCH_RESOURCE) || placeToken.equals(PlaceTokens.SEARCH_COLLECTION)){
			Window.enableScrolling(false);
		}else{
			Window.enableScrolling(true);
		}
	}

	/**
	 * Validates and send the data.
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("btnSend")
	public void onSendBtnClickEvent(ClickEvent event) {
		isvalid = true;

		if(!toTxt.getText().trim().equals("")){
			String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
			String strEmails = toTxt.getText().trim();
			String emailIds[] = strEmails.split("\\s*,\\s*");
			if (strEmails.contains(",")){
				emailIds = strEmails.split("\\s*,\\s*");
			}else if (strEmails.contains(";")){
				emailIds = strEmails.split("\\s*;\\s*");
			}

			for (int i=0; i<emailIds.length; i++){
				boolean to = emailIds[i].matches(EMAIL_REGEX);
				if(to){
					isvalid = true;
				}else{
					toValidation.setText(StringUtil.generateMessage(i18n.GL1019(), emailIds[i]));
					toValidation.getElement().setAttribute("alt", StringUtil.generateMessage(i18n.GL1019(), emailIds[i]));
					toValidation.getElement().setAttribute("title", StringUtil.generateMessage(i18n.GL1019(), emailIds[i]));
					toValidation.setVisible(true);
					isvalid = false;
					break;
				}
			}
		}
		if ((toTxt.getText() != null && !toTxt.getText().isEmpty())
				&& !toTxt.getText().contains(AT_SYMBOL)) {
			toValidation.setText(i18n.GL1027());
			toValidation.getElement().setAttribute("alt", i18n.GL1027());
			toValidation.getElement().setAttribute("title", i18n.GL1027());
			toValidation.setVisible(true);
			isvalid = false;

		}

		if (toTxt.getText().equals("") || toTxt.getText().trim().equals("")) {
			toValidation.setText(i18n.GL0216());
			toValidation.getElement().setAttribute("alt", i18n.GL0216());
			toValidation.getElement().setAttribute("title", i18n.GL0216());
			toValidation.setVisible(true);
			isvalid = false;
		}
		if(isvalid){
			final Map<String, String> parms = new HashMap<String, String>();
			parms.put("text", subTxt.getValue());
			AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
				@Override
				public void onSuccess(Boolean value) {
						isHavingBadWordsInTextbox = value;
						if(value){
							SetStyleForProfanity.SetStyleForProfanityForTextBox(subTxt, mandatoryErrorLbl,value);
						}else{
							parms.put("text", msgTxa.getText());
							AppClientFactory.getInjector().getResourceService().checkProfanity(parms,new SimpleAsyncCallback<Boolean>() {

								@Override
								public void onSuccess(Boolean result) {
									isHavingBadWordsInRichText=result;
									if(result){
										SetStyleForProfanity.SetStyleForProfanityForRichTextArea(msgTxa, mandatoryErrorRichTextArea, result);
									}else{
										if(count==0){
										if (!isHavingBadWordsInRichText && !isHavingBadWordsInTextbox) {
											if("yes".equalsIgnoreCase(cfm)){
												 toTxt.setText(toTxt.getText()+","+AppClientFactory.getLoggedInUser().getEmailId());
											}else{
												toTxt.setText(toTxt.getText());
											}
											AppClientFactory
											.getInjector()
											.getClasspageService()
											.socialShareEmail(fromTxt.getText(), toTxt.getText(), cfm,
													subTxt.getText(), msgTxa.getHTML(),
													new SimpleAsyncCallback<Void>() {

												@Override
												public void onSuccess(Void result) {
													hide();
													triggerEmailEvent(true);
													String placeToken = AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
													if(!(placeToken.equals(PlaceTokens.ASSESSMENT_PLAY) || placeToken.equals(PlaceTokens.COLLECTION_PLAY) || !placeToken.equals(PlaceTokens.PREVIEW_PLAY)||placeToken.equals(PlaceTokens.RESOURCE_PLAY))) {
														Window.enableScrolling(true);
													}
													new SentEmailSuccessVc(toTxt.getText());
												}
											});
										}
									}
										count++;
									}

								}
							});
						}
				}
			});
		}
	}

	@UiHandler("fromTxt")
	public void onKeydownEvent(KeyDownEvent event) {
		fromValidation.setVisible(false);
	}

	@UiHandler("toTxt")
	public void onKeydownClickEvent(KeyDownEvent event) {
		toValidation.setVisible(false);
	}

	@UiHandler("checkCopyEmail")
	public void oncheckCopyEmailEvent(ClickEvent event) {

		if (isCheckedValue) {
			checkCopyEmail.setStyleName("Uc-classPageEmailCheckBoxBgHoverSprite");
			isCheckedValue = false;
			cfm = "no";
		} else {
			checkCopyEmail.setStyleName("Uc-classPageEmailCheckBoxBgHover");
			isCheckedValue = true;
			cfm = "yes";
		}
	}

	public SimpleAsyncCallback<Void> getSocialShareAsyncCallback() {
		return socialShareAsyncCallback;
	}

	public void setSocialShareAsyncCallback(
			SimpleAsyncCallback<Void> socialShareAsyncCallback) {
		this.socialShareAsyncCallback = socialShareAsyncCallback;
	}
	public class CheckProfanityInOnBlur implements BlurHandler{
		private TextBox textBox;
		private Label label;
		private RichTextArea richTextArea;
		public CheckProfanityInOnBlur(TextBox textBox,RichTextArea richTextArea,Label label){
			this.textBox=textBox;
			this.label=label;
			this.richTextArea=richTextArea;
		}
		@Override
		public void onBlur(BlurEvent event) {
			Map<String, String> parms = new HashMap<String, String>();
			if(textBox!=null){
				parms.put("text", textBox.getValue());
			}else{
				parms.put("text", richTextArea.getText());
			}
			btnSend.setEnabled(false);
			AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

				@Override
				public void onSuccess(Boolean value) {
					btnSend.setEnabled(true);
					if(textBox!=null){
						isHavingBadWordsInTextbox = value;
						SetStyleForProfanity.SetStyleForProfanityForTextBox(textBox, label, value);
					}else{
						isHavingBadWordsInRichText=value;
						SetStyleForProfanity.SetStyleForProfanityForRichTextArea(richTextArea, label, value);
					}

				}
			});
		}
	}

	@UiHandler("fromTxt")
	public void fromTxtKeyUpEvent(KeyUpEvent event){
		String fromTxtText=fromTxt.getText();
		if(fromTxtText.length()>=50){
			//fromValidation.setVisible(true);

		}else{
			fromValidation.setVisible(false);
		}
	}

	@UiHandler("ancprivacy")
	public void onClickPrivacyAnchor(ClickEvent clickEvent){
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		termsOfUse=new TermsOfUse(){

			@Override
			public void openParentPopup() {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
			}

		};
		termsOfUse.show();
		termsOfUse.center();
		termsOfUse.getElement().getStyle().setZIndex(999999);//To display the view in collection player.
	}

	public void triggerEmailEvent(boolean confirmStaus){

	}

}
