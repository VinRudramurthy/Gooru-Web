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
package org.ednovo.gooru.client.mvp.authentication.uc;

import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.authentication.SignUpCBundle;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : ThanksEmailConfirmPopupUc.java
 *
 * @description :
 *		This popup is shown after user click on link from confirmation mail.
 *
 * @version : 1.0
 *
 * @date: Dec 13, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class ThanksEmailConfirmPopupUc extends PopupPanel{

	@UiField Label lblLoginHeading, lblCongratsHeader,lblCheckYourEmail;

	@UiField Button btnStartUsingGooru;

	@UiField HTMLPanel panelPopupInner;
	@UiField Anchor lblClose;

	@UiField(provided = true)
	SignUpCBundle res;
	String account=null;
	String parentEmailId=null;
	String dob = null;
	String userName = null;
	String privateGooruUId = null;

	protected AppPopUp appPopUp;

	@UiTemplate("ThanksEmailConfirmPopupUc.ui.xml")
	interface Binder extends UiBinder<Widget, ThanksEmailConfirmPopupUc> {

	}

	private static final Binder binder = GWT.create(Binder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor , to create Login Popup.
	 */

	public ThanksEmailConfirmPopupUc(){
		super(false);
		this.setGlassEnabled(true);

        this.res = SignUpCBundle.INSTANCE;
        res.css().ensureInjected();

        appPopUp = new AppPopUp(i18n.GL0501());
		appPopUp.setContent(binder.createAndBindUi(this));
		appPopUp.addStyleName(SignUpCBundle.INSTANCE.css().popupBackground());
		appPopUp.setGlassStyleName(SignUpCBundle.INSTANCE.css().signUpPopUpGlassCss());
    	this.getElement().getStyle().setHeight(332, Unit.PX);
    	panelPopupInner.getElement().getStyle().setHeight(277, Unit.PX);

        setTextAndIds();

        setHandlers();

        appPopUp.center();
	}


	/**
	 *
	 * @function setHandlers
	 *
	 * @created_date : 15-09-2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void setHandlers(){
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
	}


	/**
	 *
	 * @function setText
	 *
	 * @created_date : Aug 25, 2013
	 *
	 * @description
	 * 	To the labels for each controls.
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setTextAndIds(){
		lblLoginHeading.setText(i18n.GL0501());
		lblLoginHeading.getElement().setId("lblLoginHeading");
		lblLoginHeading.getElement().setAttribute("alt",i18n.GL0501());
		lblLoginHeading.getElement().setAttribute("title",i18n.GL0501());

		lblCongratsHeader.setText(i18n.GL0502());
		lblCongratsHeader.getElement().setId("lblCongratsHeader");
		lblCongratsHeader.getElement().setAttribute("alt",i18n.GL0502());
		lblCongratsHeader.getElement().setAttribute("title",i18n.GL0502());
		lblCongratsHeader.getElement().getStyle().setFontWeight(FontWeight.BOLD);

		btnStartUsingGooru.setText(i18n.GL0431());
		btnStartUsingGooru.setVisible(true);
		btnStartUsingGooru.getElement().setId("btnStartUsingGooru");
		btnStartUsingGooru.getElement().setAttribute("alt",i18n.GL0431());
		btnStartUsingGooru.getElement().setAttribute("title",i18n.GL0431());

		lblClose.getElement().setId("lblClose");
		panelPopupInner.getElement().setId("pnlPopupInner");

		lblCheckYourEmail.getElement().setId("lblCheckYourEmail");
	}

	@UiHandler("lblClose")
	public void clickOnClose(ClickEvent event){
		closePopUp();
	}
	@UiHandler("btnStartUsingGooru")
	public void clickOnStartUsingGooru(ClickEvent event){
		closePopUp();
	}
	/**
	 *
	 * @function closePopUp
	 *
	 * @created_date : Dec 13, 2013
	 *
	 * @description
	 * 	This method is used to close the popup.
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void closePopUp(){
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));

		Map<String, String> params = StringUtil.splitQuery(Window.Location.getHref());
		params.remove("gooruuid");
		params.remove("dob");
		params.remove("callback");
		params.remove("sessionid");
		params.remove("type");

		AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getCurrentPlaceToken(), params);

		hide();
		appPopUp.hide();
	}

}

