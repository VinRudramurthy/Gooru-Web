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

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.authentication.SignUpCBundle;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.util.MixpanelUtil;

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
import com.google.gwt.user.client.ui.Widget;
/**
 *
 * @fileName : ThankPopUpForUpdateProfile.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class ThankPopUpForUpdateProfile extends PopupPanel{

	private static ThankPopUpForUpdateProfileUiBinder uiBinder = GWT
			.create(ThankPopUpForUpdateProfileUiBinder.class);

	interface ThankPopUpForUpdateProfileUiBinder extends
			UiBinder<Widget, ThankPopUpForUpdateProfile> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField(provided = true)
	SignUpCBundle res;
	@UiField
	Label lblCancel, lblTitle, lblHeading, lblSubHeading,queriesText;
	@UiField
	Button btnOk;
	@UiField InlineLabel contactText;
	@UiField Anchor supportLink;
	public ThankPopUpForUpdateProfile() {
		super(false);
		this.res = SignUpCBundle.INSTANCE;
		res.css().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));
		this.setGlassEnabled(true);
		this.center();
		this.setSize("502px", "352px");
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		this.addStyleName(SignUpCBundle.INSTANCE.css().popupBackground());
		this.setGlassStyleName(SignUpCBundle.INSTANCE.css().signUpPopUpGlassCss());
		setUiAndIds();
	}
	/**
	 *
	 * @function setUiAndIds
	 *
	 * @created_date : 06-Dec-2014
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
	public void setUiAndIds() {
		lblTitle.setText(i18n.GL0481()
				+i18n.GL_SPL_EXCLAMATION());
		lblTitle.getElement().setId("lblTitle");
		lblTitle.getElement().setAttribute("alt",i18n.GL0481());
		lblTitle.getElement().setAttribute("title",i18n.GL0481());

		lblCancel.getElement().setId("lblCancel");

		lblHeading.setText(i18n.GL0498()+i18n.GL_SPL_EXCLAMATION());
		lblHeading.getElement().setId("lblHeading");
		lblHeading.getElement().setAttribute("alt",i18n.GL0498());
		lblHeading.getElement().setAttribute("title",i18n.GL0498());
		lblHeading.getElement().setAttribute("style", "margin-bottom:0px");

		lblSubHeading.setText(i18n.GL0499());
		lblSubHeading.getElement().setId("lblSubHeading");
		lblSubHeading.getElement().setAttribute("alt",i18n.GL0499());
		lblSubHeading.getElement().setAttribute("title",i18n.GL0499());

		btnOk.getElement().setId("btnOk");
		btnOk.setText(i18n.GL0190());
		btnOk.getElement().setAttribute("alt",i18n.GL0190());
		btnOk.getElement().setAttribute("title",i18n.GL0190());

		queriesText.setText(i18n.GL1139()+i18n.GL_GRR_COMMA()+" ");
		queriesText.getElement().setId("lblQuriesText");
		queriesText.getElement().setAttribute("alt",i18n.GL1139());
		queriesText.getElement().setAttribute("title",i18n.GL1139());

		contactText.setText(i18n.GL1145());
		contactText.getElement().setId("spnContactText");
		contactText.getElement().setAttribute("alt",i18n.GL1145());
		contactText.getElement().setAttribute("title",i18n.GL1145());

		supportLink.setText(i18n.GL0299());
		supportLink.getElement().setId("lnkSupportLink");
		supportLink.getElement().setAttribute("alt",i18n.GL0299());
		supportLink.getElement().setAttribute("title",i18n.GL0299());
		supportLink.setHref(i18n.GL1055());
	}

	@UiHandler("lblCancel")
	public void onClickLblCancel(ClickEvent event) {
		MixpanelUtil.close_signUp();
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.PREVIEW_PLAY)){

		}else{
			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		}
		this.hide();
	}

	@UiHandler("btnOk")
	public void onClickButtonLeave(ClickEvent event) {
		MixpanelUtil.close_signUp();
		this.hide();
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.PREVIEW_PLAY)){

		}else{
			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		}

	}

}
