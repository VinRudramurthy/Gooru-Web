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
/**
 *
*/
package org.ednovo.gooru.client.uc;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @fileName : AlertForgetContentUc.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 06-Jun-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class AlertForgetContentUc extends PopupPanel {

	@UiField HTMLEventPanel cancelButton,gmailButton;

	@UiField(provided = true)
	ForgetPopUpCBundle res;

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	private static final String FORGET_ERROR = i18n.GL1011();

	private static final String LOGIN_MESSAGE = i18n.GL1012();

	@UiField Label suggestionMessage,errorMessage,forgetPopupHeaderText;
	@UiField HTMLPanel signinGoogleText;
	@UiField InlineLabel questionEmailText;
	@UiField Anchor supportText;

	@UiTemplate("AlertForgetContentUc.ui.xml")
	interface Binder extends UiBinder<Widget, AlertForgetContentUc> {

	}
	private static final Binder binder = GWT.create(Binder.class);

	public AlertForgetContentUc(){

		this.res = ForgetPopUpCBundle.INSTANCE;
		res.css().ensureInjected();
		add(binder.createAndBindUi(this));
		this.setGlassEnabled(true);
		//this.setSize("475px", "225px");
		this.center();
		setIds();
		forgetPopupHeaderText.setText(i18n.GL0061());
		forgetPopupHeaderText.getElement().setAttribute("alt",i18n.GL0061());
		forgetPopupHeaderText.getElement().setAttribute("title",i18n.GL0061());

		signinGoogleText.getElement().setInnerHTML(i18n.GL1010());
		signinGoogleText.getElement().setAttribute("alt",i18n.GL1010());
		signinGoogleText.getElement().setAttribute("title",i18n.GL1010());

		questionEmailText.setText(i18n.GL0298());
		questionEmailText.getElement().setAttribute("alt",i18n.GL0298());
		questionEmailText.getElement().setAttribute("title",i18n.GL0298());

		supportText.setText(i18n.GL0299());
		supportText.getElement().setAttribute("alt",i18n.GL0299());
		supportText.getElement().setAttribute("title",i18n.GL0299());

		supportText.setHref("mailto:support@gooru.org");

		suggestionMessage.setText(LOGIN_MESSAGE);
		suggestionMessage.getElement().setAttribute("alt",LOGIN_MESSAGE);
		suggestionMessage.getElement().setAttribute("title",LOGIN_MESSAGE);

		errorMessage.setText(FORGET_ERROR);
		errorMessage.getElement().setAttribute("alt",FORGET_ERROR);
		errorMessage.getElement().setAttribute("title",FORGET_ERROR);
	}

	public void setIds() {
		signinGoogleText.getElement().setId("pnlSigninGoogleText");
		suggestionMessage.getElement().setId("lblSuggestionMessage");
		errorMessage.getElement().setId("lblErrorMessage");
		forgetPopupHeaderText.getElement().setId("lblForgetPopupHeaderText");
		questionEmailText.getElement().setId("spnQuestionEmailText");
		supportText.getElement().setId("lnkSupportText");
	}

	@UiHandler("cancelButton")
	public void onCancelClicked(ClickEvent clickEvent) {
		//this.setVisible(false);
		hide();
		Window.enableScrolling(true);

	}
	@UiHandler("gmailButton")
	public void onGmailButtonClicked(ClickEvent clickEvent)
	{
		String callBack = Window.Location.getHref();
		AppClientFactory.getInjector().getSearchService().getGoogleSignin(callBack, new SimpleAsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				Window.Location.replace(result);

			}
		});
	}
}

