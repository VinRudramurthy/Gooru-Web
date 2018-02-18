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
package org.ednovo.gooru.client.mvp.authentication.afterthirteen;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.application.shared.model.user.V2UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.authentication.SignUpCBundle;
import org.ednovo.gooru.client.mvp.authentication.uc.SignUpDontWorryView;
import org.ednovo.gooru.client.mvp.search.event.ConfirmStatusPopupEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.uc.ErrorLabelUc;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

/**
 *
 * @fileName : SignUpTurnsAfterThirteenView.java
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
public class SignUpTurnsAfterThirteenView extends
		PopupViewWithUiHandlers<SignUpAfterThirteenUiHandler> implements
		IsAfterThirteen {

	private static SignUpTurnsAfterThirteenUiBinder uiBinder = GWT
			.create(SignUpTurnsAfterThirteenUiBinder.class);

	interface SignUpTurnsAfterThirteenUiBinder extends
			UiBinder<Widget, SignUpTurnsAfterThirteenView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField(provided = true)
	SignUpCBundle res;
	@UiField
	Label lblCancel, lblTitle, lblStuDes, lblQuestionMark;
	@UiField
	InlineLabel lblStuDesDetails, lblStuDesDetails2;
	@UiField
	TextBoxWithPlaceholder txtEmailId;
	@UiField
	ErrorLabelUc emailValidUc;
	@UiField
	Button btnEnterLater, btnSubmit;
	@UiField
	Label lblfeaturesTitle, lblfeaturesTitleDes1, lblfeaturesTitleDes2,
			lblfeaturesTitleDes3, lblUpdating;
	@UiField
	HTMLPanel tooltipContent, panelSignUp;

	String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	private static final String GOORU_UID = "gooruUid";

	private static final String ACCOUNT_TYPE = "accountType";
	boolean isValidEmailId = true;
	boolean isValid = true;
	private AppPopUp appPopUp;

	@Inject
	public SignUpTurnsAfterThirteenView(EventBus eventBus) {
		super(eventBus);
		this.res = SignUpCBundle.INSTANCE;
		res.css().ensureInjected();

	}

	@Override
	public void displayView() {
		appPopUp = new AppPopUp(i18n.GL0697());
		appPopUp.setContent(uiBinder.createAndBindUi(this));
		appPopUp.setGlassEnabled(true);
		appPopUp.addStyleName(SignUpCBundle.INSTANCE.css().popupBackground());
		appPopUp.setGlassStyleName(SignUpCBundle.INSTANCE.css()
				.signUpPopUpGlassCss());

		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		setUiAndIds();
		appPopUp.center();
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
	private void setUiAndIds() {
		lblTitle.setText(i18n.GL0481() + i18n.GL_SPL_EXCLAMATION());
		lblTitle.getElement().setId("lblTitle");
		lblTitle.getElement().setAttribute("alt", i18n.GL0481());
		lblTitle.getElement().setAttribute("title", i18n.GL0481());

		lblCancel.getElement().setId("lblCancel");
		lblCancel.getElement().setAttribute("alt", "");
		lblCancel.getElement().setAttribute("title", "");

		panelSignUp.getElement().setId("pnlSignUp");
		panelSignUp.getElement().setAttribute("alt", "");
		panelSignUp.getElement().setAttribute("title", "");

		lblStuDes.setText(i18n.GL0483() + i18n.GL_SPL_EXCLAMATION());
		lblStuDes.getElement().setId("lblStuDes");
		lblStuDes.getElement().setAttribute("alt", i18n.GL0483());
		lblStuDes.getElement().setAttribute("title", i18n.GL0483());

		lblStuDesDetails.setText(i18n.GL0484());
		lblStuDesDetails.getElement().setId("lblStuDesDetails");
		lblStuDesDetails.getElement().setAttribute("alt", i18n.GL0484());
		lblStuDesDetails.getElement().setAttribute("title", i18n.GL0484());

		lblStuDesDetails2.setText(i18n.GL0485());
		lblStuDesDetails2.getElement().setId("lblStuDesDetails2");
		lblStuDesDetails2.getElement().setAttribute("alt", i18n.GL0485());
		lblStuDesDetails2.getElement().setAttribute("title", i18n.GL0485());

		lblQuestionMark.setText(i18n.GL_SPL_QUESTION());
		lblQuestionMark.getElement().setId("lblQuestionMark");
		lblQuestionMark.getElement()
				.setAttribute("alt", i18n.GL_SPL_QUESTION());
		lblQuestionMark.getElement().setAttribute("title",
				i18n.GL_SPL_QUESTION());

		emailValidUc.setVisible(false);
		emailValidUc.getElement().setId("errlblEmailId");
		emailValidUc.getElement().setAttribute("alt", i18n.GL0447());
		emailValidUc.getElement().setAttribute("title", i18n.GL0447());

		txtEmailId.setPlaceholder(i18n.GL0426());
		txtEmailId.getElement().setId("txtEmailId");
		txtEmailId.getElement().setAttribute("alt", "");
		txtEmailId.getElement().setAttribute("title", "");

		btnEnterLater.setText(i18n.GL0487());
		btnEnterLater.getElement().setId("btnEnterLater");
		btnEnterLater.getElement().setAttribute("alt", i18n.GL0487());
		btnEnterLater.getElement().setAttribute("title", i18n.GL0487());

		btnSubmit.setText(i18n.GL0486());
		btnSubmit.getElement().setId("btnSubmit");
		btnSubmit.getElement().setAttribute("alt", i18n.GL0486());
		btnSubmit.getElement().setAttribute("title", i18n.GL0486());

		lblfeaturesTitle.setText(i18n.GL0488() + i18n.GL_SPL_QUESTION());
		lblfeaturesTitle.getElement().setId("lblfeaturesTitle");
		lblfeaturesTitle.getElement().setAttribute("alt", i18n.GL0488());
		lblfeaturesTitle.getElement().setAttribute("title", i18n.GL0488());

		lblfeaturesTitleDes1.setText(i18n.GL0489());
		lblfeaturesTitleDes1.getElement().setId("lblfeaturesTitleDes1");
		lblfeaturesTitleDes1.getElement().setAttribute("alt", i18n.GL0489());
		lblfeaturesTitleDes1.getElement().setAttribute("title", i18n.GL0489());

		lblfeaturesTitleDes2.setText(i18n.GL0490());
		lblfeaturesTitleDes2.getElement().setId("lblfeaturesTitleDes2");
		lblfeaturesTitleDes2.getElement().setAttribute("alt", i18n.GL0490());
		lblfeaturesTitleDes2.getElement().setAttribute("title", i18n.GL0490());

		lblfeaturesTitleDes3.setText(i18n.GL0491());
		lblfeaturesTitleDes3.getElement().setId("lblfeaturesTitleDes3");
		lblfeaturesTitleDes3.getElement().setAttribute("alt", i18n.GL0491());
		lblfeaturesTitleDes3.getElement().setAttribute("title", i18n.GL0491());

		tooltipContent.getElement().setAttribute("style", "width:193px");
		tooltipContent.getElement().setId("pnlTooltipContentPanel");
		tooltipContent.getElement().setAttribute("alt", "");
		tooltipContent.getElement().setAttribute("title", "");

		btnSubmit.getElement().setAttribute("style", "margin-left: 10px");
		txtEmailId.addKeyUpHandler(new OnKeyUpHandler());

		lblUpdating.setVisible(false);
		lblUpdating.getElement().setId("lblUpdating");
		lblUpdating.getElement().setAttribute("alt", i18n.GL1138());
		lblUpdating.getElement().setAttribute("title", i18n.GL1138());

		btnSubmit.getElement().addClassName("disabled");
		btnSubmit.setEnabled(false);

	}

	@UiHandler("lblCancel")
	public void onClickLblCancel(ClickEvent event) {
		this.hide();
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest()
				.getNameToken().equalsIgnoreCase(PlaceTokens.PREVIEW_PLAY)) {

		} else {
			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		}
	}
	/**
	 *
	 * @function sendConfirmationMail
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param params
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void sendConfirmationMail(Map<String, String> params) {

		AppClientFactory
				.getInjector()
				.getUserService()
				.resendConfirmationMail(params,
						new SimpleAsyncCallback<Void>() {
							@Override
							public void onSuccess(Void result) {

							}
						});
	}

	@UiHandler("btnEnterLater")
	public void onClickbtnEnterLater(ClickEvent event) {
		this.hide();
		SignUpDontWorryView signUpDontWorryView = new SignUpDontWorryView();
		signUpDontWorryView.show();

	}

	/*
	 * private class OnBlurHandler implements BlurHandler {
	 *
	 * @Override public void onBlur(BlurEvent event) { if (event.getSource() ==
	 * txtEmailId && txtEmailId.getText() != null &&
	 * !txtEmailId.getText().equalsIgnoreCase("")) { boolean isValidFrom =
	 * txtEmailId.getText().matches(EMAIL_REGEX); if (isValidFrom) {
	 * checkUserAvailability( txtEmailId.getText(), "byEmailid");
	 * emailValidUc.setVisible(false);
	 * txtEmailId.removeStyleName(res.css().errorMsgDisplay()); } else {
	 * btnSubmit.getElement().addClassName("disabled");
	 * btnSubmit.setEnabled(false);
	 * txtEmailId.addStyleName(res.css().errorMsgDisplay());
	 * emailValidUc.addStyleName(res.css().errorLbl());
	 * emailValidUc.setText(MessageProperties.i18n.GL0464);
	 * emailValidUc.setVisible(true); } }
	 *
	 * } }
	 */
	/**
	 * Checks the availability of user name, entered by User.
	 *
	 * @param userName
	 * @param type
	 *
	 */
	public boolean checkUserAvailability(String userName, final String type) {

		AppClientFactory.getInjector().getUserService()
				.getEmailId(userName, type, new SimpleAsyncCallback<UserDo>() {
					@Override
					public void onSuccess(UserDo result) {
						isValidEmailId = result.isAvailability();
						if (isValidEmailId) {
							btnSubmit.getElement().addClassName("disabled");
							btnSubmit.setEnabled(false);
							txtEmailId
									.addStyleName(res.css().errorMsgDisplay());
							emailValidUc.addStyleName(res.css().errorLbl());
							emailValidUc.setText(i18n.GL0447());
							emailValidUc.setVisible(true);
							isValid = false;
						} else {
							btnSubmit.setEnabled(true);
							btnSubmit.getElement().removeClassName("disabled");
							emailValidUc.removeStyleName(res.css().errorLbl());
							txtEmailId.removeStyleName(res.css()
									.errorMsgDisplay());
							emailValidUc.setVisible(false);
							isValid = true;
						}

					}
				});

		return isValid;
	}
	/**
	 *
	 * @fileName : SignUpTurnsAfterThirteenView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 06-Dec-2014
	 *
	 * @Author tumbalam
	 *
	 * @Reviewer:
	 */
	private class OnKeyUpHandler implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			if (event.getSource() == txtEmailId) {
				txtEmailId.removeStyleName(res.css().errorMsgDisplay());
				emailValidUc.setVisible(false);
			}
			if (event.getSource() == txtEmailId && txtEmailId.getText() != null
					&& !txtEmailId.getText().equalsIgnoreCase("")) {
				boolean isValidFrom = txtEmailId.getText().matches(EMAIL_REGEX);
				if (isValidFrom) {
					checkUserAvailability(txtEmailId.getText(), "emailId");
					emailValidUc.setVisible(false);
					txtEmailId.removeStyleName(res.css().errorMsgDisplay());
				} else {
					btnSubmit.getElement().addClassName("disabled");
					btnSubmit.setEnabled(false);
					txtEmailId.addStyleName(res.css().errorMsgDisplay());
					emailValidUc.addStyleName(res.css().errorLbl());
					emailValidUc.setText(i18n.GL0464());
					emailValidUc.setVisible(true);
				}
			}
		}
	}

	public boolean validateUserInput() {
		String emilId = txtEmailId.getText().trim();
		if (emilId.equalsIgnoreCase("") || emilId == null) {
			txtEmailId.addStyleName(res.css().errorMsgDisplay());
			isValid = false;
			btnSubmit.getElement().addClassName("disabled");
			btnSubmit.setEnabled(false);

		}
		return isValid;
	}

	@UiHandler("btnSubmit")
	public void onClickbtnSubmit(ClickEvent event) {
		if (validateUserInput() && !isValidEmailId) {
			MixpanelUtil.Registration_turns13_submit_email();
			btnSubmit.setVisible(false);
			btnEnterLater.setVisible(false);
			lblUpdating.setText(i18n.GL1138());
			lblUpdating.setVisible(true);
			AppClientFactory
					.getInjector()
					.getUserService()
					.updateV2ProfileDo(txtEmailId.getText(), "nonParent", "",
							"", "", "", "", "", true, null,
							new SimpleAsyncCallback<V2UserDo>() {

								@Override
								public void onSuccess(V2UserDo result) {
									hide();
									AppClientFactory.setLoggedInUser(result
											.getUser());
									AppClientFactory
											.fireEvent(new ConfirmStatusPopupEvent(
													false));

									lblUpdating.setVisible(false);
									Map<String, String> map = StringUtil
											.splitQuery(Window.Location
													.getHref());
									map.put("callback", "profileUpdate");
									AppClientFactory
											.getPlaceManager()
											.revealPlace(
													AppClientFactory
															.getCurrentPlaceToken(),
													map);

									Map<String, String> params = new HashMap<String, String>();
									params.put(GOORU_UID,
											AppClientFactory.getGooruUid());
									params.put(ACCOUNT_TYPE, "nonParent");
									sendConfirmationMail(params);
								}
							});

		}
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

	}

	@Override
	public void onUnload() {

	}

}
