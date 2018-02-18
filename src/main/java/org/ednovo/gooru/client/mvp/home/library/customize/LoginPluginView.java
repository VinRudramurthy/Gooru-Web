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
package org.ednovo.gooru.client.mvp.home.library.customize;

import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.assessments.play.collection.event.AssessmentsSetPlayerLoginStatusEvent;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.home.ForgotPasswordVc;
import org.ednovo.gooru.client.mvp.home.event.SetUserDetailsInCollectionPlayEvent;
import org.ednovo.gooru.client.mvp.home.library.LibraryTopicListView;
import org.ednovo.gooru.client.mvp.home.library.assign.AssignPopUpCBundle;
import org.ednovo.gooru.client.mvp.play.collection.event.SetPlayerLoginStatusEvent;
import org.ednovo.gooru.client.mvp.profilepage.data.item.ProfileTopicListView;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AlertContentUc;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.shared.util.DataLogEvents;
import org.ednovo.gooru.shared.util.GwtUUIDGenerator;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 *
 * @fileName : CollectionAssignViewTab.java
 *
 * @description : This class is used to set the Editing collection to Assignment
 *              under Classpages.
 *
 * @version : 1.0
 *
 * @date: Jul 30, 2013
 *
 *
 * @Reviewer:
 */
public abstract class LoginPluginView extends ChildView<LoginPluginPresenter> implements
		IsLoginPlugin{

	@UiField(provided = true)
	AssignPopUpCBundle res;

	@UiField
	TextBoxWithPlaceholder loginTxtBox;

	@UiField
	TextBoxWithPlaceholder passwordTxtBox;

	@UiField
	Button loginButton,gmailButton;

	@UiField
	Anchor forgotPwd, ancSignUp;

	@UiField
	Label lblPleaseWait, collectionDescription,lblOr,lblLoginwithGooru;

	@UiField HTMLPanel hangOnText,signUpPanel;

	@UiField InlineLabel lblPii,toUsText,donotHaveAcount;
	@UiField Anchor ancprivacy;


	@Inject
	private ResourceServiceAsync resourceService;

	private SimpleAsyncCallback<CollectionDo> saveCollectionAsyncCallback;

	CollectionDo collectionObject = new CollectionDo();
	String collectionTitle="";
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

	private static LoginPluginUiBinder uiBinder = GWT
			.create(LoginPluginUiBinder.class);

	interface LoginPluginUiBinder extends UiBinder<Widget, LoginPluginView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor
	 */
	public LoginPluginView(CollectionDo collectionObject,String collectionTitle) {

		res = AssignPopUpCBundle.INSTANCE;
		this.collectionObject = collectionObject;
		this.collectionTitle = collectionTitle;
		AssignPopUpCBundle.INSTANCE.css().ensureInjected();
		initWidget(uiBinder.createAndBindUi(this));
		setLabelsAndIds();
		setHandlers();

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
		lblPii.getElement().setId("lblPii");
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

		hangOnText.getElement().setInnerText(i18n.GL0740());
		hangOnText.getElement().setId("pnlHangOnText");
		hangOnText.getElement().setAttribute("alt",i18n.GL0740());
		hangOnText.getElement().setAttribute("title",i18n.GL0740());
		signUpPanel.getElement().setId("pnlSignUpPanel");

		donotHaveAcount.setText(i18n.GL0634()+" ");
		donotHaveAcount.getElement().setId("lblDonotHaveAcount");
		donotHaveAcount.getElement().setAttribute("alt",i18n.GL0634()+" ");
		donotHaveAcount.getElement().setAttribute("title",i18n.GL0634()+" ");
		donotHaveAcount.getElement().setAttribute("style", "float: left;");

		ancSignUp.getElement().setAttribute("style", "float: left;padding: 0;margin-left: 5px;");
		ancSignUp.setText(i18n.GL0207());
		ancSignUp.getElement().setId("lnkAncSignUp");
		ancSignUp.getElement().setAttribute("alt",i18n.GL0207());
		ancSignUp.getElement().setAttribute("title",i18n.GL0207());

		collectionDescription.setText(i18n.GL0476());
		collectionDescription.getElement().setId("lblCollectionDescription");
		collectionDescription.getElement().setAttribute("alt",i18n.GL0476());
		collectionDescription.getElement().setAttribute("title",i18n.GL0476());


		loginTxtBox.setPlaceholder(i18n.GL0202());
		loginTxtBox.getElement().setAttribute("placeholder",i18n.GL0202());
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

		loginButton.setText(i18n.GL1185());

		gmailButton.setText(i18n.GL0203());
		gmailButton.getElement().setId("customizeGmailBtn");
		gmailButton.getElement().setAttribute("alt",i18n.GL0203());
		gmailButton.getElement().setAttribute("title",i18n.GL0203());

		lblOr.setText(i18n.GL0209());
		lblOr.getElement().setId("lblOr");
		lblOr.getElement().setAttribute("alt",i18n.GL0209());
		lblOr.getElement().setAttribute("title",i18n.GL0209());

		lblLoginwithGooru.setText(i18n.GL0346());
		lblLoginwithGooru.getElement().setId("lblLoginwithGooru");
		lblLoginwithGooru.getElement().setAttribute("alt",i18n.GL0346());
		lblLoginwithGooru.getElement().setAttribute("title",i18n.GL0346());

		lblPleaseWait.setVisible(false);

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

				AppClientFactory.getInjector()
						.getAppService()
						.v2Signin(username,password,
								new SimpleAsyncCallback<UserDo>() {
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
											lblPleaseWait.setText(i18n.GL0505());
											AppClientFactory
													.setLoggedInUser(result);
											AppClientFactory
													.fireEvent(new SetHeaderEvent(
															result));
											AppClientFactory
													.fireEvent(new SetUserDetailsInCollectionPlayEvent(
															result.getToken(),
															result.getGooruUId()));
											 if (AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.ASSESSMENT_PLAY) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.COLLECTION_PLAY)){
											    	AppClientFactory.fireEvent(new SetPlayerLoginStatusEvent(true));
											    	AppClientFactory.fireEvent(new AssessmentsSetPlayerLoginStatusEvent(true));
											    }

											showSuccessMsgfromChild(collectionObject.getGooruOid(),collectionTitle);
											MixpanelUtil.mixpanelEvent("Login_FromCustomize_Pop-up");
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

	private void setHandlers() {
		loginTxtBox.addKeyUpHandler(new LoginKeyupHandler());
		passwordTxtBox.addKeyUpHandler(new LoginKeyupHandler());
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

	@UiHandler("ancSignUp")
	public void onSignUp(ClickEvent clickEvent){
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
		if(params.containsKey("customize"))
		{
			params.remove("customize");
		}
		LibraryTopicListView.isCustomizePopup=false;
		ProfileTopicListView.isCustomizePopup=false;
    	PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, false);
		closePoupfromChild();
	}

	@UiHandler("forgotPwd")
	public void onForgotPwdClicked(ClickEvent clickEvent) {

		ForgotPasswordVc forgotPasswordVc = new ForgotPasswordVc();
		forgotPasswordVc.setGlassEnabled(true);
		forgotPasswordVc.show();
		forgotPasswordVc.center();
		closePoupfromChild();

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
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.ASSESSMENT_PLAY) ||AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.COLLECTION_PLAY)){
			MixpanelUtil.mixpanelEvent("Customize_player_signin_google");
		}else{
			MixpanelUtil.mixpanelEvent("Customize_library_signin_google");
		}
		AppClientFactory.getInjector().getSearchService().getGoogleSignin(callBack, new SimpleAsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				Window.Location.replace(result);

			}
		});
	}

	public ResourceServiceAsync getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceServiceAsync resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * @return instance of collectionDo after collection save
	 */
	public SimpleAsyncCallback<CollectionDo> getSaveCollectionAsyncCallback() {
		if (saveCollectionAsyncCallback == null) {
			saveCollectionAsyncCallback = new SimpleAsyncCallback<CollectionDo>() {

				@Override
				public void onSuccess(CollectionDo result) {

					showSuccessMsgfromChild(result.getGooruOid(),collectionTitle);
				}
			};
		}
		return saveCollectionAsyncCallback;
	}

	@UiHandler("ancprivacy")
	public void onClickPrivacyAnchor(ClickEvent clickEvent){
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		termsOfUse=new TermsOfUse(){

			@Override
			public void openParentPopup() {
				if (AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.ASSESSMENT_PLAY) ||AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY)){
					Window.enableScrolling(false);

				}else{
					Window.enableScrolling(true);
				}
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


	public abstract void closePoupfromChild();

	public abstract void showSuccessMsgfromChild(String collectionId,String collectionTitle );

}
