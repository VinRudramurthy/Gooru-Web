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
package org.ednovo.gooru.application.client.newhome;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.AppPlaceKeeper;
import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePlacePresenter;
import org.ednovo.gooru.application.client.home.banner.HomeBannerPresenter;
import org.ednovo.gooru.application.client.home.presearch.PreSearchPresenter;
import org.ednovo.gooru.application.client.service.UserServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.SeoTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.event.InvokeLoginEvent;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.SignUpAfterThirteenPresenter;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.SignUpCompleteProfilePresenter;
import org.ednovo.gooru.client.mvp.authentication.uc.StudentSignUpUc;
import org.ednovo.gooru.client.mvp.authentication.uc.ThanksEmailConfirmPopupUc;
import org.ednovo.gooru.client.mvp.classpages.edit.EditClasspagePresenter.IsEditClasspageProxy;
import org.ednovo.gooru.client.mvp.home.AlmostDoneUc;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.home.event.HeaderTabType;
import org.ednovo.gooru.client.mvp.home.event.HomeEvent;
import org.ednovo.gooru.client.mvp.home.register.UserRegistrationPresenter;
import org.ednovo.gooru.client.mvp.search.event.ConfirmStatusPopupEvent;
import org.ednovo.gooru.client.mvp.search.event.SetFooterEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AlertContentUc;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

/**
 *
 * @fileName : TestPresenter.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 26-May-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class NewHomePresenter extends BasePlacePresenter<IsNewHomeView, IsEditClasspageProxy> implements NewHomeUiHandlers {

	private HomeBannerPresenter banner = null;
	private PreSearchPresenter presearchPresenter = null;
	private SignUpPresenter signUpViewPresenter = null;
	private SignUpCompleteProfilePresenter signUpCompletePresenter = null;
	private SignUpAfterThirteenPresenter signUpAfterThirteenPresenter=null;
	private UserRegistrationPresenter userRegistrationPresenter = null;

	@Inject
	private UserServiceAsync userService;


	public static final  Object BANNER_SLOT = new Object();
	public static final  Object PRESEARCH_SLOT = new Object();

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	private SimpleAsyncCallback<UserDo> registerdUserAsyncCallback;

	private static final String GOORU_UID = "gooruuid";

	private static final String USER_TYPE = "type";

	private static final String CALLBACK = "callback";

	private static final String LIBRARY_PAGE = "page";

	private static final String LOGINEVENT = "loginEvent";

	private static final String ERROR = "error";

	private static final String CREDENTIAL = "Credential";

	private String parentGooruUID;


	@ProxyCodeSplit
	@NameToken(PlaceTokens.HOME)
	@UseGatekeeper(AppPlaceKeeper.class)
	public interface IsTestProxy extends ProxyPlace<NewHomePresenter> {
	}

	@Inject
	public NewHomePresenter(IsNewHomeView view, IsEditClasspageProxy proxy, HomeBannerPresenter banner, PreSearchPresenter presearchPresenter, UserRegistrationPresenter userRegistrationPresenter, SignUpPresenter signUpViewPresenter, SignUpCompleteProfilePresenter signUpCompletePresenter,SignUpAfterThirteenPresenter signUpAfterThirteenPresenter) {
		super(view, proxy);
		getView().setUiHandlers(this);
		this.presearchPresenter =presearchPresenter;
		this.banner = banner;

		this.signUpViewPresenter = signUpViewPresenter;
		this.userRegistrationPresenter = userRegistrationPresenter;
		this.signUpCompletePresenter = signUpCompletePresenter;
		this.signUpAfterThirteenPresenter=signUpAfterThirteenPresenter;
	}

	@Override
	public String getViewToken() {
		return AppClientFactory.getCurrentPlaceToken();
	}

	@Override
	protected void onReveal() {
		super.onReveal();
		Window.enableScrolling(true);

		if(AppClientFactory.isAnonymous()) {
			AppClientFactory.setBrowserWindowTitle(SeoTokens.HOME_TITLE_ANONYMOUS);
		} else {
			AppClientFactory.setBrowserWindowTitle(SeoTokens.HOME_TITLE_LOGGEDIN);
		}
		AppClientFactory.setMetaDataDescription(SeoTokens.HOME_META_DESCRIPTION);
		AppClientFactory.fireEvent(new HomeEvent(HeaderTabType.HOME));
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken() != null){
			AppClientFactory.fireEvent(new SetFooterEvent(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken()));
		}

		Document doc = Document.get();
		if(doc.getElementById("uvTab") != null){
			doc.getElementById("uvTab").getStyle().setDisplay(Display.BLOCK);
		}

		setInSlot(BANNER_SLOT, banner);
		setInSlot(PRESEARCH_SLOT, presearchPresenter);

	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		request.getParameter("", "register");
		callBackMethods();
	}

	@Override
	protected void onHide() {
		super.onHide();

	}

	@Override
	public void onBind() {
		super.onBind();
		Window.enableScrolling(true);
		MixpanelUtil.Arrive_Landing_Page();
		setRegisterdUserAsyncCallback(new SimpleAsyncCallback<UserDo>() {
			@Override
			public void onSuccess(final UserDo user) {
				GWT.runAsync(new SimpleRunAsyncCallback() {

					@Override
					public void onSuccess() {
						initilazeRegistrationView(user);
					}
				});
			}
		});

	}

	@Override
	protected void onReset() {
		Window.enableScrolling(true);
	}

	public void setRegisterdUserAsyncCallback(SimpleAsyncCallback<UserDo> registerdUserAsyncCallback) {
		this.registerdUserAsyncCallback = registerdUserAsyncCallback;
	}

	public SimpleAsyncCallback<UserDo> getRegisterdUserAsyncCallback() {
		return registerdUserAsyncCallback;
	}

	public UserServiceAsync getUserService() {
		return userService;
	}

	@Override
	public void initilazeRegistrationView(final UserDo user) {

		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				String userType = getPlaceManager().getRequestParameter(USER_TYPE);
				if (userType == null || (userType != null && !userType.equalsIgnoreCase("Parent") && !userType.equalsIgnoreCase("NonParent"))) {

					alert(i18n.GL1415()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP(), i18n.GL1416());

				} else if (user != null) {
					if (user.isAvailability()) {
						if (user.getConfirmStatus() == 1 && userType.equalsIgnoreCase("Parent")) {
							if (AppClientFactory.getLoggedInUser().getUserUid().equals(AppClientFactory.GOORU_ANONYMOUS)) {
								LoginPopupUc login = new LoginPopupUc(user.getEmailId()) {

									@Override
									public void onLoginSuccess() {
										// TODO Auto-generated method stub

									}
								};
							} else if(AppClientFactory.getLoggedInUser().getUserUid().equalsIgnoreCase(parentGooruUID)||AppClientFactory.getLoggedInUser().getUserUid()==parentGooruUID){
								userRegistrationPresenter.setAccountType(userType);
								userRegistrationPresenter.setUser(user);
								addToPopupSlot(userRegistrationPresenter, true);
							}
							else {
								alert(i18n.GL0065()+i18n.GL_SPL_FULLSTOP(), i18n.GL1417());

							}
						} else if (user.getConfirmStatus() == 1 && !userType.equalsIgnoreCase("Parent")) {
							alert(i18n.GL0065()+i18n.GL_SPL_FULLSTOP(), i18n.GL1418());
						}
						else {
							userRegistrationPresenter.setAccountType(userType);
							userRegistrationPresenter.setUser(user);
							addToPopupSlot(userRegistrationPresenter, true);
						}
					} else {
						alert(i18n.GL0065()+i18n.GL_SPL_FULLSTOP(), i18n.GL1419());
					}

				} else {
					alert(i18n.GL1415()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP(), i18n.GL1420()+i18n.GL_SPL_FULLSTOP());
				}

			}
		});

	}

	/**
	 * Create alert popup with message header and message content
	 * @param messageHeader popup heading
	 * @param messageContent content of the popup
	 */
	private void alert(final String messageHeader, final String messageContent) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				new AlertContentUc(messageHeader, messageContent).getAlertButton().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
					}
				});
			}
		});
	}
	private void callBackMethods(){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				if(AppClientFactory.getLoggedInUser().getConfirmStatus()==0){
					AppClientFactory.fireEvent(new ConfirmStatusPopupEvent(true));
				}
				if (getPlaceManager().getRequestParameter(CALLBACK) != null && getPlaceManager().getRequestParameter(CALLBACK).equalsIgnoreCase("registration")) {
					getUserService().getRegistredUserDetails(AppClientFactory.getPlaceManager().getRequestParameter(GOORU_UID), getRegisterdUserAsyncCallback());
					parentGooruUID=AppClientFactory.getPlaceManager().getRequestParameter(GOORU_UID);
				}else if (getPlaceManager().getRequestParameter(CALLBACK) != null && getPlaceManager().getRequestParameter(CALLBACK).equalsIgnoreCase("changePassword")) {
					validateResetLink(AppClientFactory.getPlaceManager().getRequestParameter("resetToken"));
				}else if (getPlaceManager().getRequestParameter(CALLBACK) != null && getPlaceManager().getRequestParameter(CALLBACK).equalsIgnoreCase("signup")) {
					//To show SignUp (Registration popup)
					if (AppClientFactory.isAnonymous()){
						Window.enableScrolling(false);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
						String type = getPlaceManager().getRequestParameter("type") ;
						int displayScreen =getPlaceManager().getRequestParameter("type") !=null  ? Integer.parseInt(type) : 1;
						signUpViewPresenter.displayPopup(displayScreen);
						addToPopupSlot(signUpViewPresenter);
					}
				}else if (getPlaceManager().getRequestParameter(CALLBACK) != null && getPlaceManager().getRequestParameter(CALLBACK).equalsIgnoreCase("registerChild")){
					if (getPlaceManager().getRequestParameter("dob") != null && getPlaceManager().getRequestParameter("userName") != null){
						String externalId = AppClientFactory.getLoggedInUser().getExternalId();
						String email = AppClientFactory.getLoggedInUser().getEmailId();

						String parentEmailId = email !=null && !email.equalsIgnoreCase("") ? email : externalId !=null && externalId.equalsIgnoreCase("") ? externalId : null;
						String parameterEmailId = getPlaceManager().getRequestParameter("emailId", null);
						parentEmailId = parameterEmailId !=null && !parameterEmailId.equalsIgnoreCase("") ? parameterEmailId : parentEmailId;

						StudentSignUpUc studentSignUp = new StudentSignUpUc(parentEmailId, getPlaceManager().getRequestParameter("userName"), getPlaceManager().getRequestParameter("dob").replaceAll("D", "\\/"), AppClientFactory.isAnonymous() ? getPlaceManager().getRequestParameter("privateGooruUId") : AppClientFactory.getLoggedInUser().getGooruUId());
						studentSignUp.center();
						studentSignUp.show();
					}
				}else if (getPlaceManager().getRequestParameter(CALLBACK) != null && getPlaceManager().getRequestParameter(CALLBACK).equalsIgnoreCase("guide")){

				}else if (getPlaceManager().getRequestParameter(CALLBACK) != null && getPlaceManager().getRequestParameter(CALLBACK).equalsIgnoreCase("profileUpdate")){
					if (AppClientFactory.isAnonymous()){
						AppClientFactory.fireEvent(new InvokeLoginEvent());
					}else{
						signUpCompletePresenter.displayView();
						addToPopupSlot(signUpCompletePresenter);
					}
				}
				else if (getPlaceManager().getRequestParameter(CALLBACK) != null && getPlaceManager().getRequestParameter(CALLBACK).equalsIgnoreCase("turn13")){
					if (AppClientFactory.isAnonymous()){
						AppClientFactory.fireEvent(new InvokeLoginEvent());
					}else{
						if(!signUpAfterThirteenPresenter.isVisible()) {
							signUpAfterThirteenPresenter.displayView();
							addToPopupSlot(signUpAfterThirteenPresenter);
						}
					}
				}
				else if  (getPlaceManager().getRequestParameter(CALLBACK) != null && getPlaceManager().getRequestParameter(CALLBACK).equalsIgnoreCase("confirmUser")){
					//SignUpGradeCourseView gradeCourseView = new SignUpGradeCourseView(AppClientFactory.getLoggedInUser());
					//Check if user is logged or not.
					if (AppClientFactory.isAnonymous()){
						//If not Open Login Popup
						AppClientFactory.fireEvent(new InvokeLoginEvent());
					}else{
						if (AppClientFactory.getLoggedInUser().getConfirmStatus() == 0){
							String gooruUid = getPlaceManager().getRequestParameter("gooruuid") !=null ? getPlaceManager().getRequestParameter("gooruuid") : "";
							String token = getPlaceManager().getRequestParameter("sessionid") !=null ? getPlaceManager().getRequestParameter("sessionid") : "";
							Map<String, String> params = new HashMap<String, String>();
							params.put("confirmUser", "true");
							params.put("gooruUid", AppClientFactory.getLoggedInUser().getGooruUId());
							// Confirm User and remove/hide Not confirmed Popup.
							AppClientFactory.getInjector().getUserService().updateUserDetails(gooruUid, token, params, new SimpleAsyncCallback<ProfileDo>() {
								@Override
								public void onSuccess(ProfileDo result) {
									//Display thanks popup if required.
									//Set Visiblity to false
									AppClientFactory.setLoggedInUser(result.getUser());
									boolean isConfirmed = result.getUser().getConfirmStatus() == 1 ? true : false;
									if (isConfirmed){
										AppClientFactory.fireEvent(new ConfirmStatusPopupEvent(isConfirmed));
										ThanksEmailConfirmPopupUc confirmPopup = new ThanksEmailConfirmPopupUc();
										confirmPopup.center();
										confirmPopup.show();
									}
								}
							});
						}
					}
				}

				if (getPlaceManager().getRequestParameter(LOGINEVENT) != null && getPlaceManager().getRequestParameter(LOGINEVENT).equalsIgnoreCase("true") && AppClientFactory.isAnonymous()) {
					AppClientFactory.fireEvent(new InvokeLoginEvent());
				}

				if (getPlaceManager().getRequestParameter(ERROR) != null && getPlaceManager().getRequestParameter(ERROR).equals("401") && AppClientFactory.isAnonymous()) {
					new AlertContentUc(i18n.GL1966(), i18n.GL1938());
				}


				final UserDo userDo = AppClientFactory.getLoggedInUser();
				int flag = userDo != null ? userDo.getViewFlag() : 0;
				final String loginType = userDo != null && userDo.getLoginType() !=null ? userDo.getLoginType() : "";
				//Show Popup where user can update his details like, username and role. Show this only for non regular user and if he is logging for the first time.
				if(!AppClientFactory.isAnonymous() && flag==0 &&  !loginType.equalsIgnoreCase(CREDENTIAL)) {
					Window.enableScrolling(false);
					AlmostDoneUc update = new AlmostDoneUc(AppClientFactory.getLoggedInUser().getEmailId(), AppClientFactory.getLoggedInUser());
					update.setGlassEnabled(true);
					update.show();
					update.center();
					Document doc=Document.get();
					Element bodyelement = doc.getBody();
					Window.scrollTo(0, 0);
					bodyelement.getParentElement().setAttribute("style", "overflow:hidden");
				}
				else if(flag>0 && flag<=11 && !AppClientFactory.isAnonymous()){

				}

				AppClientFactory.fireEvent(new SetFooterEvent(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken()));
			}
		});
	}

	private void validateResetLink(String resetToken) {
		AppClientFactory.getInjector().getUserService().isValidResetPasswordLink(resetToken, new SimpleAsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				if(result.equals("true")){
					getView().resetPassword(AppClientFactory.getPlaceManager().getRequestParameter("resetToken"));
				}else{
					new AlertMessageUc(i18n.GL1089(), new HTML(i18n.GL0100()));
				}
			}
		});
	}

}
