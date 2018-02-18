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

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.authentication.SignUpCBundle;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : ThanksPopupUc.java
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
public class ThanksPopupUc extends PopupPanel{

	@UiField Label lblLoginHeading, lblCongratsHeader,lblCheckYourEmail,lblWhatsNext,lblLearnHowTo;

	@UiField Button btnStartUsingGooru, btnStartCreatingStudent;

	@UiField HTML htmlSupport;

	@UiField Anchor lblClose;

	@UiField HTMLPanel panelPopupInner,panelFooter;

	@UiField(provided = true)
	SignUpCBundle res;
	String account=null;
	String parentEmailId=null;
	String dob = null;
	String userName = null;
	String privateGooruUId = null;

	@UiTemplate("ThanksPopupUc.ui.xml")
	interface Binder extends UiBinder<Widget, ThanksPopupUc> {

	}

	private static final Binder binder = GWT.create(Binder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor , to create Login Popup.
	 */

	public ThanksPopupUc(){
		super(false);
		this.setGlassEnabled(true);
        this.res = SignUpCBundle.INSTANCE;
        res.css().ensureInjected();
        add(binder.createAndBindUi(this));
        account = AppClientFactory.getPlaceManager().getRequestParameter("account") !=null ? AppClientFactory.getPlaceManager().getRequestParameter("account") : "regular";
        dob = AppClientFactory.getPlaceManager().getRequestParameter("dob") !=null ? AppClientFactory.getPlaceManager().getRequestParameter("dob").replaceAll("D", "/") : null;
        userName = AppClientFactory.getPlaceManager().getRequestParameter("userName") !=null ? AppClientFactory.getPlaceManager().getRequestParameter("userName") : null;
        privateGooruUId = AppClientFactory.getPlaceManager().getRequestParameter("privateGooruUId") !=null ? AppClientFactory.getPlaceManager().getRequestParameter("privateGooruUId") : null;
        setTextAndIds();
        setHandlers();
		this.center();
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

	public void setAccountType(String type){
		if (type.equalsIgnoreCase("normal")){
			btnStartUsingGooru.setVisible(true);
			btnStartCreatingStudent.setVisible(false);
		}else{

		}
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
		lblLoginHeading.setText(i18n.GL0186());
		lblLoginHeading.getElement().setId("lblLoginHeading");
		lblLoginHeading.getElement().setAttribute("alt",i18n.GL0186());
		lblLoginHeading.getElement().setAttribute("title",i18n.GL0186());

		String congrats = AppClientFactory.getLoggedInUser().getLoginType().equalsIgnoreCase("apps") ? i18n.GL2195() : i18n.GL0429();

		lblCongratsHeader.setText(congrats);
		lblCongratsHeader.getElement().setId("lblCongratsHeader");
		lblCongratsHeader.getElement().setAttribute("alt",congrats);
		lblCongratsHeader.getElement().setAttribute("title",congrats);

		lblCheckYourEmail.setText(i18n.GL0430());
		lblCheckYourEmail.getElement().setId("lblCheckYourEmail");
		lblCheckYourEmail.getElement().setAttribute("alt",i18n.GL0430());
		lblCheckYourEmail.getElement().setAttribute("title",i18n.GL0430());

		lblCheckYourEmail.setVisible( AppClientFactory.getLoggedInUser().getLoginType().equalsIgnoreCase("apps") ? false : true);

		lblWhatsNext.setText(i18n.GL0432());
		lblWhatsNext.getElement().setId("lblWhatsNext");
		lblWhatsNext.getElement().setAttribute("alt",i18n.GL0432());
		lblWhatsNext.getElement().setAttribute("title",i18n.GL0432());

		//This is not required when registered as parent.
		btnStartUsingGooru.setText(i18n.GL0431());
		//This is not required when registered as parent.
		lblLearnHowTo.setText(i18n.GL0433());
		lblLearnHowTo.getElement().setId("lblLearnHowTo");
		lblLearnHowTo.getElement().setAttribute("alt",i18n.GL0433());
		lblLearnHowTo.getElement().setAttribute("title",i18n.GL0433());
		lblLearnHowTo.setVisible(false);
		lblWhatsNext.setVisible(false);

		//This is not required for Regular user.
		btnStartCreatingStudent.setText(i18n.GL0472());

		btnStartUsingGooru.setVisible(account.equalsIgnoreCase("regular") ? true : false);

		btnStartCreatingStudent.setVisible(account.equalsIgnoreCase("parent") ? true : false);

		htmlSupport.setHTML(i18n.GL0437());
		htmlSupport.getElement().setId("htmlSupport");
		htmlSupport.getElement().setAttribute("alt",i18n.GL0437());
		htmlSupport.getElement().setAttribute("title",i18n.GL0437());

		btnStartUsingGooru.getElement().setId("btnStartUsingGooru");
		btnStartUsingGooru.getElement().setAttribute("alt",i18n.GL0431());
		btnStartUsingGooru.getElement().setAttribute("title",i18n.GL0431());

		btnStartCreatingStudent.getElement().setId("btnStartCreatingStudent");
		btnStartCreatingStudent.getElement().setAttribute("alt",i18n.GL0472());
		btnStartCreatingStudent.getElement().setAttribute("title",i18n.GL0472());

		panelPopupInner.getElement().setId("pnlPopupInner");
		panelFooter.getElement().setId("pnlFooter");
		lblClose.getElement().setId("lblClose");
	}

	@UiHandler("btnStartUsingGooru")
	public void clickOnStartGooru(ClickEvent event){
		Map<String, String> map = StringUtil.splitQuery(Window.Location.getHref());
		map.remove("callback");
		map.remove("type");
		map.remove("privateGooruUId");
		map.remove("dob");
		map.remove("userName");
		map.remove("emailId");
		map.remove("email");
		String revealPlace = map.get("rp") !=null && !map.get("rp").equalsIgnoreCase("") ? map.get("rp") : null;
		String collId = map.get("id") !=null && !map.get("id").equalsIgnoreCase("") ? map.get("id") : null;

		String viewToken =  revealPlace != null ? revealPlace : AppClientFactory.getCurrentPlaceToken();
		map.remove("rp");

		AppClientFactory.getPlaceManager().revealPlace(viewToken, map);
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		hide();
	}

	@UiHandler("lblClose")
	public void clickOnClose(ClickEvent event){
		if (account.equalsIgnoreCase("parent")){
			startCreatingStudent();
		}
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.PREVIEW_PLAY)){

		}else{
			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		}
		hide();
	}

	@UiHandler("btnStartCreatingStudent")
	public void clickOnStartCreatingStudent(ClickEvent event){
		hide();
		startCreatingStudent();
	}
	/**
	 *
	 * @function startCreatingStudent
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
	private void startCreatingStudent(){
		String externalId = AppClientFactory.getLoggedInUser().getExternalId();
		String email = AppClientFactory.getLoggedInUser().getEmailId();
		parentEmailId = externalId != null ? externalId : email != null ? email : null;
		privateGooruUId = AppClientFactory.getLoggedInUser().getGooruUId();
		AppClientFactory.getInjector().getAppService().v2Signout(new SimpleAsyncCallback<UserDo>() {
			@Override
			public void onSuccess(UserDo userDo) {
				AppClientFactory.setLoggedInUser(userDo);
				AppClientFactory.fireEvent(new SetHeaderEvent(userDo));
				StudentSignUpUc studentSignUp = new StudentSignUpUc(parentEmailId, userName, dob, privateGooruUId);
				studentSignUp.center();
				studentSignUp.show();
			}
		});
	}
}

