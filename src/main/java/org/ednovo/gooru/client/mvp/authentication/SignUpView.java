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
package org.ednovo.gooru.client.mvp.authentication;

import java.util.Date;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.event.InvokeLoginEvent;
import org.ednovo.gooru.client.mvp.authentication.uc.CreateAccountUc;
import org.ednovo.gooru.client.mvp.authentication.uc.LeaveRegistrationPopUpUc;
import org.ednovo.gooru.client.mvp.faq.CopyRightPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsAndPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 *
 * @fileName : SignUpView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: Aug 30, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class SignUpView extends PopupViewWithUiHandlers<SignUpUiHandlers> implements IsSignUpView{

	private static SignUpViewUiBinder uiBinder = GWT.create(SignUpViewUiBinder.class);

	protected AppPopUp appPopUp;

	interface SignUpViewUiBinder extends UiBinder<Widget, SignUpView> {
	}
	public MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField Label lblTitle,lblJoinGooruCommunity,lblDescription,lblWhyWithGoogle,lblQuestionMark,lblPopupWhyWithGoogle,lblParentInfo;

	@UiField Label lblPopupWhyWithGoogleDesc,lblOr,lblDontHaveGoogleAccount,lblAlreadyHaveAccount;

	@UiField Button btnSignUpWithGoogle;

	@UiField Anchor  achSignUpWithEmail,achClickToLogin,lblCancel;

	@UiField HTMLPanel panelSignUp, panelUserInfo, panelCreateAccount,panelLoginPopupContent;

	CreateAccountUc createAccount = null;

	String account = null;
	String childDob = null;
	String childUserName = null;

	/**
	 * Class constructor, creates popup , focus and blur events
	 * @param eventBus {@link EventBus}
	 */
	@Inject
	public SignUpView(EventBus eventBus) {
		super(eventBus);
		SignUpCBundle.INSTANCE.css().ensureInjected();

		appPopUp = new AppPopUp(i18n.GL0697());
		appPopUp.setContent(uiBinder.createAndBindUi(this));



	}

	public void displayPopUp(int displayScreen){
		appPopUp.addStyleName(SignUpCBundle.INSTANCE.css().popupBackground());
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		appPopUp.setGlassStyleName(SignUpCBundle.INSTANCE.css().signUpPopUpGlassCss());

		appPopUp.center();
		setUiAndIds();
		showPanelById(displayScreen);
		account = AppClientFactory.getPlaceManager().getRequestParameter("account") !=null ? AppClientFactory.getPlaceManager().getRequestParameter("account")  :  null ;
		childDob = AppClientFactory.getPlaceManager().getRequestParameter("dob") !=null ? AppClientFactory.getPlaceManager().getRequestParameter("dob")  :  null ;
		childUserName = AppClientFactory.getPlaceManager().getRequestParameter("userName") !=null ? AppClientFactory.getPlaceManager().getRequestParameter("userName")  :  null ;
	}

	private void setUiAndIds(){
		lblTitle.setText(i18n.GL0186() +i18n.GL_SPL_EXCLAMATION());

		lblTitle.getElement().setId("lblTitle");
		lblTitle.getElement().setAttribute("alt",i18n.GL0186());
		lblTitle.getElement().setAttribute("title",i18n.GL0186());

		lblCancel.getElement().setId("lblCancel");
		lblCancel.getElement().setAttribute("alt","");
		lblCancel.getElement().setAttribute("title","");

		panelSignUp.getElement().setId("pnlSignUp");
		panelSignUp.getElement().setAttribute("alt","");
		panelSignUp.getElement().setAttribute("title","");

		lblJoinGooruCommunity.setText(i18n.GL0400());
		lblJoinGooruCommunity.getElement().setId("lblJoinTheGooruCommunity");
		lblJoinGooruCommunity.getElement().setAttribute("alt",i18n.GL0400());
		lblJoinGooruCommunity.getElement().setAttribute("title",i18n.GL0400());

		lblDescription.setText(i18n.GL0401());
		lblDescription.getElement().setId("lblDescription");
		lblDescription.getElement().setAttribute("alt",i18n.GL0401());
		lblDescription.getElement().setAttribute("title",i18n.GL0401());

		lblParentInfo.setVisible(false);
		lblParentInfo.setText(i18n.GL0470());
		lblParentInfo.getElement().setId("lblParentInfo");
		lblParentInfo.getElement().setAttribute("alt",i18n.GL0470());
		lblParentInfo.getElement().setAttribute("title",i18n.GL0470());

		btnSignUpWithGoogle.setText(i18n.GL0402());
		btnSignUpWithGoogle.getElement().setId("btnSignUpWithGoogle");
		btnSignUpWithGoogle.getElement().setAttribute("alt",i18n.GL0402());
		btnSignUpWithGoogle.getElement().setAttribute("title",i18n.GL0402());

		lblWhyWithGoogle.setText(i18n.GL0403());
		lblWhyWithGoogle.getElement().setId("lblWhyWithGoogle");
		lblWhyWithGoogle.getElement().setAttribute("alt",i18n.GL0403());
		lblWhyWithGoogle.getElement().setAttribute("title",i18n.GL0403());

		lblQuestionMark.setText(i18n.GL_SPL_QUESTION());
		lblQuestionMark.getElement().setId("lblQuestionMark");
		lblQuestionMark.getElement().setAttribute("alt",i18n.GL_SPL_QUESTION());
		lblQuestionMark.getElement().setAttribute("title",i18n.GL_SPL_QUESTION());

		lblPopupWhyWithGoogle.setText(i18n.GL0403() + i18n.GL_SPL_QUESTION());
		lblPopupWhyWithGoogle.getElement().setId("lblPopupWhyWithGoogle");
		lblPopupWhyWithGoogle.getElement().setAttribute("alt",i18n.GL0403());
		lblPopupWhyWithGoogle.getElement().setAttribute("title",i18n.GL0403());

		lblPopupWhyWithGoogleDesc.setText(i18n.GL0404());
		lblPopupWhyWithGoogleDesc.getElement().setId("lblPopupWhyWithGoogleDesc");
		lblPopupWhyWithGoogleDesc.getElement().setAttribute("alt",i18n.GL0404());
		lblPopupWhyWithGoogleDesc.getElement().setAttribute("title",i18n.GL0404());

		lblOr.setText(i18n.GL0209());
		lblOr.getElement().setId("lblOr");
		lblOr.getElement().setAttribute("alt",i18n.GL0209());
		lblOr.getElement().setAttribute("title",i18n.GL0209());

		lblDontHaveGoogleAccount.setText(i18n.GL0405() +i18n.GL_SPL_QUESTION());
		lblDontHaveGoogleAccount.getElement().setId("lblDontHaveGoogleAccount");
		lblDontHaveGoogleAccount.getElement().setAttribute("alt",i18n.GL0405());
		lblDontHaveGoogleAccount.getElement().setAttribute("title",i18n.GL0405());

		lblAlreadyHaveAccount.setText(i18n.GL0407() + i18n.GL_SPL_QUESTION());
		lblAlreadyHaveAccount.getElement().setId("lblAlreadyHaveAccount");
		lblAlreadyHaveAccount.getElement().setAttribute("alt",i18n.GL0407());
		lblAlreadyHaveAccount.getElement().setAttribute("title",i18n.GL0407());

		achSignUpWithEmail.setText(i18n.GL0406());
		achSignUpWithEmail.getElement().setId("lnkSignUpWithEmail");
		achSignUpWithEmail.getElement().setAttribute("alt",i18n.GL0406());
		achSignUpWithEmail.getElement().setAttribute("title",i18n.GL0406());

		achClickToLogin.setText(i18n.GL0408());
		achClickToLogin.getElement().setId("lnkClickToLogin");
		achClickToLogin.getElement().setAttribute("alt",i18n.GL0408());
		achClickToLogin.getElement().setAttribute("title",i18n.GL0408());

		panelUserInfo.getElement().setId("pnlUserInfo");
		panelUserInfo.getElement().setAttribute("alt","");
		panelUserInfo.getElement().setAttribute("title","");

	}


	private void showPanelById(int id){
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));

		panelSignUp.setVisible(id ==1 || id ==3  ? true : false);
		panelUserInfo.setVisible(id ==2 ? true : false);

		if (id==2){
			openCreateUser();
			lblJoinGooruCommunity.setVisible(true);
			lblDescription.setVisible(true);
			lblParentInfo.setVisible(false);
		}else if (id==3){
			lblJoinGooruCommunity.setVisible(false);
			lblDescription.setVisible(false);
			lblParentInfo.setVisible(true);
		}
	}

	//Ui Handlers

	/**
	 * Hide the popup and redirect to home page while clicking cancel
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("lblCancel")
	public void onCancelClick(ClickEvent clickEvent) {
		MixpanelUtil.close_signUp();

		if (AppClientFactory.getPlaceManager().getRequestParameter("type") !=null && !AppClientFactory.getPlaceManager().getRequestParameter("type").equalsIgnoreCase("1")){
			LeaveRegistrationPopUpUc leaveRegistrationPopUpUc=new LeaveRegistrationPopUpUc(i18n.GL0074().toLowerCase(),"","","");
			leaveRegistrationPopUpUc.show();
		}else{
			Map<String, String> map = StringUtil.splitQuery(Window.Location.getHref());
			if(map.containsKey("query"))
			{
				String queryVal = AppClientFactory.getPlaceManager().getRequestParameter("query");
				queryVal = queryVal.replace("+", " ");
				map.put("query", queryVal);
			}
			if(map.containsKey("flt.subjectName"))
			{
				String subjectNameVal = AppClientFactory.getPlaceManager().getRequestParameter("flt.subjectName");
				/*String subjectNameVal = map.get("flt.subjectName");
				subjectNameVal = subjectNameVal.replaceAll("%5C1", "&");
				try
				{
					subjectNameVal = URL.decodeQueryString(subjectNameVal);
				}
				catch(Exception ex)
				{

				}*/
				subjectNameVal = subjectNameVal.replace("+", " ");
				map.put("flt.subjectName", subjectNameVal);
			}
			map.remove("callback");
			map.remove("type");
			map.remove("rp");
			if (!AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.PREVIEW_PLAY) && !AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.COLLECTION_PLAY) && !AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.ASSESSMENT_PLAY) &&
					!AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.RESOURCE_PLAY) && !AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.FOLDER_TOC)&&!AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.STUDENT) && !AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.PROFILE_PAGE)){
				map.remove("id");
			}

			if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.PREVIEW_PLAY)||AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY) ||AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY)||AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.RESOURCE_PLAY)){
				PlaceRequest placeRequest = new PlaceRequest(AppClientFactory.getCurrentPlaceToken());
				if (map != null) {
					for (String key : map.keySet()) {
						placeRequest = placeRequest.with(key, map.get(key));
					}
				}
				AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
			}else{
				//AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getCurrentPlaceToken(), map);

				PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), map);
				AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, false);
				Window.enableScrolling(true);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
			}
		}
		appPopUp.hide();
	}

	@UiHandler("btnSignUpWithGoogle")
	public void onClickBtnGoogle(ClickEvent clickEvent){
		String callBack = Window.Location.getHref();
		Map<String, String> mapParms = StringUtil.splitQuery(callBack);
		if (!callBack.contains("parent")){
			mapParms.remove("callback");
			mapParms.remove("type");
			mapParms.remove("rp");
			if (!AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)){
				mapParms.remove("id");
			}
		}else{
			mapParms.put("callback", "registerChild");
		}
		AppClientFactory.getInjector().getSearchService().getGoogleSignin(AppClientFactory.getCurrentPlaceToken(),  mapParms, new SimpleAsyncCallback<String>() {

			@Override
			public void onSuccess(String gConnectUrl) {
				MixpanelUtil.Click_Gmail_SignIn("LoginPopup");
				Window.Location.replace(gConnectUrl);
			}
		});
	}

	@UiHandler("achClickToLogin")
	public void onClickAlreadyHaveAccount(ClickEvent clickEvent){
		Map<String, String> map = StringUtil.splitQuery(Window.Location.getHref());
		if(map.containsKey("query"))
		{
			String queryVal = AppClientFactory.getPlaceManager().getRequestParameter("query");
			map.put("query", queryVal);
		}
		if(map.containsKey("flt.subjectName"))
		{
			String subjectNameVal = AppClientFactory.getPlaceManager().getRequestParameter("flt.subjectName");
			map.put("flt.subjectName", subjectNameVal);
		}
		map.remove("callback");
		map.remove("type");
		//AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getCurrentPlaceToken(), map);
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), map);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, false);
		appPopUp.hide();
		String viewToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		String tabView=AppClientFactory.getPlaceManager().getRequestParameter("tab",null);
		if(tabView != null)
		{
		if((viewToken.equals(PlaceTokens.COLLECTION_PLAY) || viewToken.equals(PlaceTokens.COLLECTION_PLAY)||viewToken.equals(PlaceTokens.PREVIEW_PLAY)||viewToken.equals(PlaceTokens.RESOURCE_PLAY))&&
				(tabView!=null)&&(tabView.equals("add")||tabView.equals("flag"))){

		}else{
			AppClientFactory.fireEvent(new InvokeLoginEvent());
		}
		}
		else
		{
			AppClientFactory.fireEvent(new InvokeLoginEvent());
		}
	}

	@UiHandler("achSignUpWithEmail")
	public void onClickSignUpWithEmail(ClickEvent clickEvent){
		MixpanelUtil.sign_up_with_your_email();
		hide();
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
		if (account!=null && lblParentInfo.isVisible()){
			params.put("account", account);
		}
		if (childDob!=null){
			params.put("dob", childDob);
		}
		if (childUserName!=null){
			params.put("userName", childUserName);
		}
		//AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getCurrentPlaceToken(), params );
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, false);

	}

	private void openCreateUser(){
		createAccount = new CreateAccountUc() {

			private TermsAndPolicyVc termsAndPolicyVc;
			private CopyRightPolicyVc copyRightPolicy;
			private TermsOfUse termsOfUse;

			@Override
			public void OpenTermsPrivacy() {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				appPopUp.hide();
				termsAndPolicyVc = new TermsAndPolicyVc(false) {
					@Override
					public void openParentPopup() {
						Window.enableScrolling(false);
						appPopUp.show();
					}
				};
				termsAndPolicyVc.show();

				termsAndPolicyVc.center();
				termsAndPolicyVc.getElement().getStyle().setZIndex(99999);//To display the view in collection player.
			}

			@Override
			public void OpenCopyRight() {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				appPopUp.hide();
				copyRightPolicy = new CopyRightPolicyVc() {
					@Override
					public void openParentPopup() {
						Window.enableScrolling(false);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
						appPopUp.show();
					}
				};
				copyRightPolicy.show();
				copyRightPolicy.center();
				copyRightPolicy.getElement().getStyle().setZIndex(99999);//To display the view in collection player.
			}
			@Override
			public void OpenPrivacy() {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				appPopUp.hide();
				termsOfUse=new TermsOfUse(){
					@Override
					public void openParentPopup() {
						Window.enableScrolling(false);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
						appPopUp.show();
					}

				};
				termsOfUse.show();
				termsOfUse.center();
				termsOfUse.getElement().getStyle().setZIndex(99999);//To display the view in collection player.
			}
			//Send data to create user.
			@Override
			public void CreateUser(Map<String, String> registrationDetailsParams, String username,String password) {
				getUiHandlers().CreateUser(registrationDetailsParams, username, password);
			}

			@Override
			public void closePoup() {
				appPopUp.hide();

			}
		};
		panelUserInfo.clear();
		panelUserInfo.add(createAccount);

	}


	public void toggleButtons(){
		createAccount.toggleButtons();
	}

	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	@Override
	public void reset() {
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUnload() {
		// TODO Auto-generated method stub

	}
	private int getAge(Date birthDate){
		if(birthDate!=null) {
			long ageInMillis = new Date().getTime() - birthDate.getTime();
			Date age = new Date(ageInMillis);
			return age.getYear()- 70;
		} else {
			return 0;
		}
	}
}
