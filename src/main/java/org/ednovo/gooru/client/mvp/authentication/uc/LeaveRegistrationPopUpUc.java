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

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.authentication.SignUpCBundle;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
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
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
/**
 *
 * @fileName : LeaveRegistrationPopUpUc.java
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
public class LeaveRegistrationPopUpUc extends PopupPanel{

	private static LeaveRegistrationPopUpUcUiBinder uiBinder = GWT
			.create(LeaveRegistrationPopUpUcUiBinder.class);

	interface LeaveRegistrationPopUpUcUiBinder extends
			UiBinder<Widget, LeaveRegistrationPopUpUc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField(provided = true)
	SignUpCBundle res;
	@UiField
	Label lblTitle, lblHeading, lblSubHeading,quriesText;
	@UiField
    Anchor	lblCancel;
	@UiField
	Button btnLeave, btnContinue;
	@UiField InlineLabel pleaseContactText;
	@UiField Anchor supportLink;
	@UiField HTMLPanel panelSignUp;
	String accountType = "";
	String childDob = "";
	String parentEmailId = "";
	String childUsername = "";

	public LeaveRegistrationPopUpUc(String type,String emailId,String username,String dob) {
		super(false);
		this.res = SignUpCBundle.INSTANCE;
		res.css().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));
		accountType = type;
		parentEmailId = emailId;
		childUsername = username;
		childDob = dob;
		this.setGlassEnabled(true);
		this.center();
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		//this.getElement().setAttribute("style", "width:502px;height:352px;z-index:98;visibility: visible;position: absolute;left: 0 !important;right: 0 !important;margin:auto;top:0 !important;bottom:0 !important;");
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
		lblTitle.setText(i18n.GL0480()
				+ i18n.GL_SPL_QUESTION());
		lblTitle.getElement().setId("lblTitle");
		lblTitle.getElement().setAttribute("alt",i18n.GL0480());
		lblTitle.getElement().setAttribute("title",i18n.GL0480());

		lblCancel.getElement().setId("lblCancel");

		panelSignUp.getElement().setId("pnlSignUp");

		lblHeading.setText(i18n.GL0477());
		lblHeading.getElement().setId("lblHeading");
		lblHeading.getElement().setAttribute("alt",i18n.GL0477());
		lblHeading.getElement().setAttribute("title",i18n.GL0477());
		lblHeading.getElement().setAttribute("style", "margin-bottom:0px");

		lblSubHeading.setText(i18n.GL0478());
		lblSubHeading.getElement().setId("lblSubHeading");
		lblSubHeading.getElement().setAttribute("alt",i18n.GL0478());
		lblSubHeading.getElement().setAttribute("title",i18n.GL0478());

		btnLeave.setText(i18n.GL0480());
		btnLeave.getElement().setId("btnLeave");
		btnLeave.getElement().setAttribute("alt",i18n.GL0480());
		btnLeave.getElement().setAttribute("title",i18n.GL0480());

		btnContinue.getElement().setId("btnContinue");
		btnLeave.getElement().setAttribute("alt",i18n.GL0479());
		btnLeave.getElement().setAttribute("title",i18n.GL0479());
		btnContinue.setText(i18n.GL0479());
		btnContinue.getElement().setAttribute("style", "margin-left: 20px");

		quriesText.setText(i18n.GL1139()+i18n.GL_GRR_COMMA());
		quriesText.getElement().setId("lblQuriesText");
		quriesText.getElement().setAttribute("alt",i18n.GL1139());
		quriesText.getElement().setAttribute("title",i18n.GL1139());

		pleaseContactText.setText(i18n.GL1145());
		pleaseContactText.getElement().setId("spnPleaseContactText");
		pleaseContactText.getElement().setAttribute("alt",i18n.GL1145());
		pleaseContactText.getElement().setAttribute("title",i18n.GL1145());

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
			String callBack = Window.Location.getHref();
			Map<String, String> mapParms = StringUtil.splitQuery(callBack);
			if(mapParms.containsKey("query"))
			{
				String queryVal = AppClientFactory.getPlaceManager().getRequestParameter("query");
				mapParms.put("query", queryVal);
			}
			if(mapParms.containsKey("flt.subjectName"))
			{
				String subjectNameVal = AppClientFactory.getPlaceManager().getRequestParameter("flt.subjectName");
				mapParms.put("flt.subjectName", subjectNameVal);
			}
			mapParms.remove("callback");
			mapParms.remove("type");
			//AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getCurrentPlaceToken(), mapParms );
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), mapParms);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, false);

		}
		this.hide();
	}

	@UiHandler("btnLeave")
	public void onClickButtonLeave(ClickEvent event) {
		MixpanelUtil.close_signUp();
		this.hide();
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.PREVIEW_PLAY)){

		}else{
			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
			String callBack = Window.Location.getHref();
			Map<String, String> mapParms = StringUtil.splitQuery(callBack);
			if(mapParms.containsKey("query"))
			{
				String queryVal = AppClientFactory.getPlaceManager().getRequestParameter("query");
				mapParms.put("query", queryVal);
			}
			if(mapParms.containsKey("flt.subjectName"))
			{
				String subjectNameVal = AppClientFactory.getPlaceManager().getRequestParameter("flt.subjectName");
				mapParms.put("flt.subjectName", subjectNameVal);
			}
			mapParms.remove("callback");
			mapParms.remove("type");
			//AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getCurrentPlaceToken(), mapParms );
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), mapParms);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, false);

		}
	}

	@UiHandler("btnContinue")
	public void onClickBtnContinue(ClickEvent event) {
		MixpanelUtil.continue_registration();
		if(accountType.equalsIgnoreCase("parent")){

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
			params.put("type", "2");
			params.put("callback", "signup");
			AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getCurrentPlaceToken(), params );

		}else if(accountType.equalsIgnoreCase("registerChild")){
			Map<String, String> params = new HashMap<String, String>();
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
			params.put("callback", "registerChild");
			params.put("type", "4");

			if (childDob != null) {
				params.put("dob", childDob);
			}
			if (childUsername != null) {
				params.put("userName", childUsername);
			}
			if(parentEmailId!=null){
				params.put("emailId",parentEmailId);
			}
			//AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getCurrentPlaceToken(), params );
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, false);

		}
		this.hide();
	}

}
