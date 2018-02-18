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
package org.ednovo.gooru.client.mvp.home;

import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AlertForgetContentUc;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class ForgotPasswordVc extends PopupPanel implements ClientConstants {

	private static ForgotPasswordVcUiBinder uiBinder = GWT
			.create(ForgotPasswordVcUiBinder.class);



	@UiField
	Anchor supportLink;

	@UiField
	Button sendMailBtnUc;

	@UiField
	TextBox forgotEmailIdTxtBox;

	@UiField Label lblLoginHeading,lblDisplayTextMessage,lblTextMessageInfomation,errorMessage,
	queriesText;

	@UiField
	Anchor cancelButton;

	@UiField InlineLabel pleaseContactLbl;


	interface ForgotPasswordVcUiBinder extends
			UiBinder<Widget, ForgotPasswordVc> {
	}
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor , to create forgot password popup
	 */
	public ForgotPasswordVc() {
		super(false);
		this.setGlassStyleName("loginPopupGlassStyle");
        this.setGlassEnabled(true);
       	this.getElement().getStyle().setZIndex(99999);
       	setWidget(uiBinder.createAndBindUi(this));
  		forgotEmailIdTxtBox.getElement().setId("tbUsername");
  		StringUtil.setAttributes(forgotEmailIdTxtBox, true);
		sendMailBtnUc.getElement().setId("btnSubmit");
		supportLink.getElement().setId("lnkSupport");

		sendMailBtnUc.setText(i18n.GL0486());
		sendMailBtnUc.getElement().setAttribute("alt",i18n.GL0486());
		sendMailBtnUc.getElement().setAttribute("title",i18n.GL0486());

		queriesText.setText(i18n.GL1139()+i18n.GL_GRR_COMMA());
		queriesText.getElement().setId("lblQueriesText");
		queriesText.getElement().setAttribute("alt",i18n.GL1139());
		queriesText.getElement().setAttribute("title",i18n.GL1139());

		supportLink.setText(i18n.GL0299());
		supportLink.getElement().setAttribute("alt",i18n.GL0299());
		supportLink.getElement().setAttribute("title",i18n.GL0299());
		supportLink.setHref("mailto:support@gooru.org");
		pleaseContactLbl.setText(i18n.GL1145());
		pleaseContactLbl.getElement().setId("spnPleaseContact");
		pleaseContactLbl.getElement().setAttribute("alt",i18n.GL1145());
		pleaseContactLbl.getElement().setAttribute("title",i18n.GL1145());

		this.center();
		lblLoginHeading.setHeight("16px");
		lblLoginHeading.setText(i18n.GL0063());
		lblLoginHeading.getElement().setId("lblLoginHeading");
		lblLoginHeading.getElement().setAttribute("alt",i18n.GL0063());
		lblLoginHeading.getElement().setAttribute("title",i18n.GL0063());

		lblDisplayTextMessage.setText(i18n.GL0436());
		lblDisplayTextMessage.getElement().setId("lblDisplayTextMessage");
		lblDisplayTextMessage.getElement().setAttribute("alt",i18n.GL0436());
		lblDisplayTextMessage.getElement().setAttribute("title",i18n.GL0436());

		lblTextMessageInfomation.getElement().setAttribute("style", "font-size: 13px !important");
		lblTextMessageInfomation.setText(i18n.GL0435());
		lblTextMessageInfomation.getElement().setId("lblTextMessageInfomation");
		lblTextMessageInfomation.getElement().setAttribute("alt",i18n.GL0435());
		lblTextMessageInfomation.getElement().setAttribute("title",i18n.GL0435());

		forgotEmailIdTxtBox.getElement().setAttribute("placeholder",i18n.GL0434());
		forgotEmailIdTxtBox.setFocus(true);
		errorMessage.setVisible(false);

		errorMessage.getElement().setId("lblerrErrorMessage");
		cancelButton.getElement().setId("epnlCancelButton");
	}
	@UiHandler("cancelButton")
	public void onCloseClick(ClickEvent clickEvent)
	{
		this.hide();

        if (AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.ASSESSMENT_PLAY) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.COLLECTION_PLAY) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.PREVIEW_PLAY) ||
				AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.RESOURCE_PLAY)){
		}else{
			Window.enableScrolling(true);
	        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		}
	}


	/**
	 * @return {@link BlueButtonUc} uiField
	 *//*
	protected Button getConfirmationButton() {
		return sendMailBtnUc;
	}

	protected boolean hasValidateData() {
		return true;
	}*/

	/**
	 * @return emailId which is to be reset password
	 */
	protected String getforgotEmailId() {
		return forgotEmailIdTxtBox.getText();
	}


	/**
	 * Send a mail to user to update their password through forgot password link if user exists show new pop up
	 *  that tells the password reset mail has been sent else through validation error
	 *
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("sendMailBtnUc")
	public void forgotPassword(ClickEvent clickEvent){
		if (forgotEmailIdTxtBox.getText().trim().length() > 0) {

			AppClientFactory.getInjector().getUserService().forgotPassword(this.getforgotEmailId(), new SimpleAsyncCallback<Map<String, Object>>() {

				@Override
				public void onSuccess(Map<String, Object> result) {
                    if (result != null) {
                    	if(result.containsKey(ERROR_CODE)&&result.get(ERROR_CODE)!=null){
                    		String	error=(String) result.get(ERROR_MESSAGE);
                    		if(GOOGLE_TIE_UP_TEXT.equalsIgnoreCase(error)){
   							 errorMessage.setVisible(true);
   							 hide();
   							 AlertForgetContentUc alertForgetContentUc = new AlertForgetContentUc();
   							 alertForgetContentUc.show();
   							 alertForgetContentUc.center();
   							 alertForgetContentUc.getElement().getStyle().setZIndex(999999);
   						}else{
   							errorMessage.setText(i18n.GL0438());
   							errorMessage.getElement().setAttribute("alt",i18n.GL0438());
   							errorMessage.getElement().setAttribute("title",i18n.GL0438());
   							errorMessage.setVisible(true);
   						}
                    	}
					}
                    if (result != null && result.containsKey("gooruUId") && result.get("gooruUId").toString().length() > 0) {
						hide();
						ForgotPwdSuccessVc forgotPwdSuccessVc=new ForgotPwdSuccessVc();
						forgotPwdSuccessVc.setGlassEnabled(true);
						forgotPwdSuccessVc.show();
						forgotPwdSuccessVc.center();

					}
				}

			});
		}else
		{
			errorMessage.setVisible(true);
			errorMessage.setText(i18n.GL0439());
			errorMessage.getElement().setAttribute("alt",i18n.GL0439());
			errorMessage.getElement().setAttribute("title",i18n.GL0439());
		}
		}

}
