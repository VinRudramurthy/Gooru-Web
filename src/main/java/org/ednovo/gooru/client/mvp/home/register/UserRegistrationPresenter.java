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
package org.ednovo.gooru.client.mvp.home.register;

import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.UserServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author Search Team
 *
 */
public class UserRegistrationPresenter extends PresenterWidget<IsUserRegistrationView> implements UserRegistrationUiHandlers{

	private static final String GOORU_UID = "gooruuid";
	private static final String SESSION_Id = "sessionid";
	private static final String DOB = "dob";

	@Inject
	private UserServiceAsync userService;

	private UserDo user;

	private AsyncCallback<ProfileDo> updateUserAsyncCallback;

	private RegistrationWelcomePopup registrationWelcomePopup;

	private SimpleAsyncCallback<UserDo> checkUserAsyncCallback;

	private ChildAccountPopup childAccountPopup;

	private SimpleAsyncCallback<UserDo> createChildUserAsyncCallback;

	private String accountType;

	private boolean showWelcomeMessage = false;

	private SimpleAsyncCallback<UserDo> updateFlagAsyncCallback;

	private static final String CHILD = "Child";

	private static final String NON_PARENT = "NonParent";

	private static final String PARENT = "Parent";


	private static final String ACCOUNT_TYPE = "accountType";

	private static final String REGISTRATION_TYPE = "registrationType";

	private static final String FIRST_NAME = "firstName";

	private static final String LAST_NAME = "lastName";

	private static final String EMAIL_ID = "emailId";

	private static final String ORGANIZATION_CODE = "organizationCode";

	private static final String GOORU = "gooru";


	private static final String GMAIL_ADDRESS = "@gmail.com";

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor
	 * @param eventBus {@link EventBus}
	 * @param view {@link View}
	 */
	@Inject
	public UserRegistrationPresenter(EventBus eventBus, IsUserRegistrationView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	@Override
	public void onBind() {
		super.onBind();
		setUpdateUserAsyncCallback(new SimpleAsyncCallback<ProfileDo>() {

			@Override
			public void onSuccess(final ProfileDo userProfile) {
				if (!getView().getRegistrationType().equalsIgnoreCase(CHILD)) {
					AppClientFactory.setLoggedInUser(userProfile.getUser());
					AppClientFactory.getInjector().getWrapPresenter().get().setLoginData(userProfile.getUser());
				}

				if (getView().getRegistrationType().equalsIgnoreCase(NON_PARENT)) {
					getView().closeRegisterPopup();
					showWelcomeMessage();
				}
				if (getView().getRegistrationType().equalsIgnoreCase(PARENT)) {
					showWelcomeMessage = true;
					getView().renderChildAccount();
				}

			}

			@Override
			public void onFailure(Throwable caught) {
			}

		});

		setCheckUserAsyncCallback(new SimpleAsyncCallback<UserDo>() {
			@Override
			public void onSuccess(UserDo result) {
				getView().checkUserNameAvailability(result);
			}
		});

		setCreateChildUserAsyncCallback(new SimpleAsyncCallback<UserDo>() {
			@Override
			public void onSuccess(UserDo user) {
				if (getView().getRegistrationType().equalsIgnoreCase(CHILD))
				{
					getView().closeRegisterPopup();
					childAccountPopup = new ChildAccountPopup();
					childAccountPopup.confirmationButton().addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							childAccountPopup.getPopupPanel().hide();
							if (showWelcomeMessage)
							{
								showWelcomeMessage();
							}
							else
							{
								AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
							}
						}
					});
				}
			}
		});

		setUpdateFlagAsyncCallback(new SimpleAsyncCallback<UserDo>() {
			@Override
			public void onSuccess(UserDo newUser) {
				UserDo user = AppClientFactory.getLoggedInUser();
				user.setViewFlag(newUser.getViewFlag());
				AppClientFactory.setLoggedInUser(user);
			}
		});
	}

	@Override
	public void onReveal() {
		super.onReveal();
		getView().setRegisteredUserDetails(getUser(), getAccountType());
	}


	@Override
	public void updateUserDetails(Map<String, String> params) {
		params.put(ACCOUNT_TYPE, getView().getRegistrationType());
		params.put(REGISTRATION_TYPE, getView().getRegistrationType());
		this.getUserService().updateUserDetails(AppClientFactory.getPlaceManager().getRequestParameter(GOORU_UID), AppClientFactory.getPlaceManager().getRequestParameter(SESSION_Id), params, getUpdateUserAsyncCallback());
	}

	@Override
	public void registerChildUserAccount(Map<String, String> params) {
		params.put(FIRST_NAME, getView().getFirstName());
		params.put(LAST_NAME, getView().getLastName());
		params.put(ACCOUNT_TYPE, getView().getRegistrationType());
		params.put(REGISTRATION_TYPE, getView().getRegistrationType());
		params.put(EMAIL_ID, GMAIL_ADDRESS);
		params.put(ORGANIZATION_CODE, GOORU);
		params.put("childFlag", "true");
		params.put("userParentId", AppClientFactory.getPlaceManager().getRequestParameter(GOORU_UID));
		params.put("dateOfBirth", AppClientFactory.getPlaceManager().getRequestParameter(DOB).replaceAll("d", "/"));

		this.getUserService().registerUser(params, getCreateChildUserAsyncCallback());

	}

	/**
	 * Creates welcome popup after registration
	 */
	private void showWelcomeMessage() {

		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
		getUserService().updateUserViewFlag(AppClientFactory.getLoggedInUser().getGooruUId(), 1, getUpdateFlagAsyncCallback());
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, false));

		/*registrationWelcomePopup = new RegistrationWelcomePopup();
		registrationWelcomePopup.getConfirmButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				registrationWelcomePopup.getPopupPanel().hide();
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
				getUserService().updateUserViewFlag(AppClientFactory.getLoggedInUser().getGooruUId(), 1, getUpdateFlagAsyncCallback());
				GooruGuideVc gooruGuideVc = new GooruGuideVc();
				gooruGuideVc.show();
				gooruGuideVc.center();

			}
		});*/
	}
	public void setUserService(UserServiceAsync userService) {
		this.userService = userService;
	}

	public UserServiceAsync getUserService() {
		return userService;
	}

	public void setUser(UserDo user) {
		this.user = user;
	}

	public UserDo getUser() {
		return user;
	}

	public void setUpdateUserAsyncCallback(AsyncCallback<ProfileDo> updateUserAsyncCallback) {
		this.updateUserAsyncCallback = updateUserAsyncCallback;
	}

	public AsyncCallback<ProfileDo> getUpdateUserAsyncCallback() {
		return updateUserAsyncCallback;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountType() {
		return accountType;
	}

	public SimpleAsyncCallback<UserDo> getCheckUserAsyncCallback() {
		return checkUserAsyncCallback;
	}

	public void setCheckUserAsyncCallback(SimpleAsyncCallback<UserDo> checkUserAsyncCallback) {
		this.checkUserAsyncCallback = checkUserAsyncCallback;
	}

	@Override
	public void checkUserAvailability(String userName, String type) {
		this.getUserService().getEmailId(userName, type, getCheckUserAsyncCallback());
	}

	public SimpleAsyncCallback<UserDo> getCreateChildUserAsyncCallback() {
		return createChildUserAsyncCallback;
	}

	public void setCreateChildUserAsyncCallback(SimpleAsyncCallback<UserDo> createChildUserAsyncCallback) {
		this.createChildUserAsyncCallback = createChildUserAsyncCallback;
	}

	public void setUpdateFlagAsyncCallback(SimpleAsyncCallback<UserDo> updateFlagAsyncCallback) {
		this.updateFlagAsyncCallback = updateFlagAsyncCallback;
	}

	public SimpleAsyncCallback<UserDo> getUpdateFlagAsyncCallback() {
		return updateFlagAsyncCallback;
	}

}
