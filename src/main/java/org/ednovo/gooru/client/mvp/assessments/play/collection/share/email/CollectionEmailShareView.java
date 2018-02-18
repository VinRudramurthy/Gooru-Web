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
package org.ednovo.gooru.client.mvp.assessments.play.collection.share.email;



import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public abstract class CollectionEmailShareView extends PopupPanel{

	@UiField Label fromValidation, toValidation, lblEmailFriend, lblFrom, lblTo, lblSendMeCopy, lblSubject, lblMessage, fromLbl,mandatoryErrorLbl,mandatoryErrorRichTextArea,noteTxt;

	@UiField CheckBox checkCopyEmail;

	@UiField Button btnSend, cancelLbl;

	@UiField TextBoxWithPlaceholder toTxt, fromTxt;

	@UiField TextBox subTxt;

	@UiField RichTextArea msgTxa;

	@UiField InlineLabel lblPii,toUsText;
	@UiField Anchor ancprivacy;

	@UiField HTMLPanel mainShareContainer;

	private static boolean isvalid;

	private String cfm = "no";

	private static final String AT_SYMBOL = "@";

	boolean isHavingBadWordsInTextbox=false,isHavingBadWordsInRichText=false;

	private TermsOfUse termsOfUse;

	private static CollectionEmailShareViewUiBinder uiBinder = GWT.create(CollectionEmailShareViewUiBinder.class);

	interface CollectionEmailShareViewUiBinder extends
			UiBinder<Widget, CollectionEmailShareView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);


	public CollectionEmailShareView( String mailSubject, String mailDescription) {
		setWidget(uiBinder.createAndBindUi(this));

		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		this.getElement().getStyle().setZIndex(999999);
		this.setGlassEnabled(true);
		fromValidation.setText(i18n.GL0215());
		fromValidation.getElement().setId("lblFromValidation");
		fromValidation.getElement().setAttribute("alt",i18n.GL0215());
		fromValidation.getElement().setAttribute("title",i18n.GL0215());

		toValidation.setText(i18n.GL0216());
		toValidation.getElement().setId("lblToValidation");
		toValidation.getElement().setAttribute("alt",i18n.GL0216());
		toValidation.getElement().setAttribute("title",i18n.GL0216());

		lblEmailFriend.setText(i18n.GL0222());
		lblEmailFriend.getElement().setId("lblEmailFriend");
		lblEmailFriend.getElement().setAttribute("alt",i18n.GL0222());
		lblEmailFriend.getElement().setAttribute("title",i18n.GL0222());

		lblFrom.setText(i18n.GL0223() + i18n.GL_SPL_SEMICOLON()+" ");
		lblFrom.getElement().setId("lblFrom");
		lblFrom.getElement().setAttribute("alt",i18n.GL0223());
		lblFrom.getElement().setAttribute("title",i18n.GL0223());

		lblTo.setText(i18n.GL0224() + i18n.GL_SPL_SEMICOLON()+" ");
		lblTo.getElement().setId("lblTo");
		lblTo.getElement().setAttribute("alt",i18n.GL0224());
		lblTo.getElement().setAttribute("title",i18n.GL0224());

		lblSendMeCopy.setText(i18n.GL0225());
		lblSendMeCopy.getElement().setId("lblSendMeCopy");
		lblSendMeCopy.getElement().setAttribute("alt",i18n.GL0225());
		lblSendMeCopy.getElement().setAttribute("title",i18n.GL0225());

		lblSubject.setText(i18n.GL0226() + i18n.GL_SPL_SEMICOLON()+" ");
		lblSubject.getElement().setId("lblSubject");
		lblSubject.getElement().setAttribute("alt",i18n.GL0226());
		lblSubject.getElement().setAttribute("title",i18n.GL0226());

		lblMessage.setText(i18n.GL0227() + i18n.GL_SPL_SEMICOLON()+" ");
		lblMessage.getElement().setId("lblMessage");
		lblMessage.getElement().setAttribute("alt",i18n.GL0227());
		lblMessage.getElement().setAttribute("title",i18n.GL0227());

		btnSend.setText(i18n.GL0228());
		btnSend.getElement().setId("btnSend");
		btnSend.getElement().setAttribute("alt",i18n.GL0228());
		btnSend.getElement().setAttribute("title",i18n.GL0228());

		cancelLbl.setText(i18n.GL0142());
		cancelLbl.getElement().setId("btnCancelLbl");
		cancelLbl.getElement().setAttribute("alt",i18n.GL0142());
		cancelLbl.getElement().setAttribute("title",i18n.GL0142());

		noteTxt.setText(i18n.GL1636());
		noteTxt.getElement().setId("lblNoteTxt");
		noteTxt.getElement().setAttribute("alt",i18n.GL1636());
		noteTxt.getElement().setAttribute("title",i18n.GL1636());

		lblPii.setText(i18n.GL1892());
		lblPii.getElement().setId("spnLblPii");
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

	//	lblPii.getElement().getStyle().setMarginLeft(99, Unit.PX);
		/*ancprivacy.getElement().getStyle().setMarginLeft(101, Unit.PX);*/

		mandatoryErrorLbl.setVisible(false);
		mandatoryErrorRichTextArea.setVisible(false);
		fromLbl.setVisible(false);
		fromValidation.setVisible(false);
		toValidation.setVisible(false);
		toTxt.getElement().setId("tbTo");
		subTxt.getElement().setId("tbSubject");
		StringUtil.setAttributes(subTxt, true);
		subTxt.setText(StringUtil.generateMessage(i18n.GL1997(),i18n.GL2000()));
		subTxt.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL1997(),i18n.GL2000()));
		subTxt.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL1997(),i18n.GL2000()));
		fromTxt.getElement().setId("tbFrom");
		msgTxa.getElement().setId("taMessage");
		StringUtil.setAttributes(msgTxa, true);
		fromLbl.getElement().setId("lblFromLbl");
		checkCopyEmail.getElement().setId("chkCheckCopyEmail");
		mandatoryErrorLbl.getElement().setId("errlblMandatoryErrorLbl");
		mandatoryErrorRichTextArea.getElement().setId("errlblMandatoryErrorRichTextArea");

		fromTxt.setMaxLength(50);
		if(AppClientFactory.isAnonymous()){
			fromTxt.setVisible(true);
			fromTxt.setPlaceholder(i18n.GL1442_1());
		}else{
			if(AppClientFactory.getLoggedInUser().getFirstName()!=null && AppClientFactory.getLoggedInUser().getLastName()!=null){
				fromTxt.setVisible(true);
				fromTxt.setText(AppClientFactory.getLoggedInUser().getFirstName() + " " +AppClientFactory.getLoggedInUser().getLastName());
				fromTxt.getElement().setAttribute("alt",AppClientFactory.getLoggedInUser().getFirstName() + " " +AppClientFactory.getLoggedInUser().getLastName());
				fromTxt.getElement().setAttribute("title",AppClientFactory.getLoggedInUser().getFirstName() + " " +AppClientFactory.getLoggedInUser().getLastName());
			}else{
				fromTxt.setVisible(true);
				fromTxt.setPlaceholder(i18n.GL1442_1());
			}
		}
		toTxt.setPlaceholder(i18n.GL1184_1());
		msgTxa.setHTML(mailDescription);
		msgTxa.getElement().setAttribute("alt",i18n.GL1184_1());
		msgTxa.getElement().setAttribute("title",i18n.GL1184_1());

		isvalid = true;
		this.setGlassEnabled(true);
		this.center();
		this.getGlassElement().getStyle().setZIndex(99999);

		msgTxa.addInitializeHandler(new InitializeHandler() {
			@Override
			public void onInitialize(InitializeEvent event) {
				    Document document = IFrameElement.as(msgTxa.getElement()).getContentDocument();
	                BodyElement body = document.getBody();
	                body.setAttribute("style", "font-family: Arial;font-size:12px;");
			}
		});
		fromTxt.addBlurHandler(new CheckProfanityInOnBlur(fromTxt,null, fromValidation));
		subTxt.addBlurHandler(new CheckProfanityInOnBlur(subTxt,null, mandatoryErrorLbl));
		msgTxa.addBlurHandler(new CheckProfanityInOnBlur(null,msgTxa, mandatoryErrorRichTextArea));
		ScrollPopupShareUtil.ScrollPopupUtilWidget(mainShareContainer);
	}

	@UiHandler("cancelLbl")
	public void onCancelClickEvent(ClickEvent event) {
		closeEmailpanel();
	}

	private void closeEmailpanel() {
		this.hide();
		closeEmailPopup();
	}

	@UiHandler("btnSend")
	public void onSendBtnClickEvent(ClickEvent event) {
		isvalid = true;
		if(AppClientFactory.isAnonymous()){
			if(fromTxt.isVisible()) {
				if (fromTxt.getText().equals("")) {
					fromValidation.setText(i18n.GL2112());
					fromValidation.setVisible(true);
					isvalid = false;
				}
			}
		}

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
				}else{
					toValidation.setText(StringUtil.generateMessage(i18n.GL1019(), emailIds[i]));
					toValidation.setVisible(true);
					isvalid = false;
					break;
				}
			}
		}

		if (toTxt.getText().equals("")) {
			toValidation.setText(i18n.GL0216());
			toValidation.getElement().setAttribute("alt",i18n.GL0216());
			toValidation.getElement().setAttribute("title",i18n.GL0216());
			toValidation.setVisible(true);
			isvalid = false;
		}
		if ((toTxt.getText() != null && !toTxt.getText().isEmpty())
				&& !toTxt.getText().contains(AT_SYMBOL)) {

			toValidation.setText(i18n.GL1027());
			toValidation.getElement().setAttribute("alt",i18n.GL1027());
			toValidation.getElement().setAttribute("title",i18n.GL1027());
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
										if (!isHavingBadWordsInRichText && !isHavingBadWordsInTextbox) {
											String fromEmail = "";
												fromEmail = fromTxt.getText();
											sendEmail(fromEmail, toTxt.getText(), cfm, subTxt.getText(), msgTxa.getHTML());
										}
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
		if (checkCopyEmail.getValue()) {
			cfm = "yes";
		} else {
			cfm = "no";
		}
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

	public  abstract void sendEmail(String fromEmail,String toEmail,String copyEmail,String subject,String message);
	public abstract void closeEmailPopup();
}
