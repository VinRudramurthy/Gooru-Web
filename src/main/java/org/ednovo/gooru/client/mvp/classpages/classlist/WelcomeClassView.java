/**
 *
 */
package org.ednovo.gooru.client.mvp.classpages.classlist;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : WelcomeClassView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 07-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class WelcomeClassView extends PopupPanel {

	private static NewFeaturesClassVcUiBinder uiBinder = GWT
			.create(NewFeaturesClassVcUiBinder.class);
	MessageProperties i18n = GWT.create(MessageProperties.class);
	interface NewFeaturesClassVcUiBinder extends
			UiBinder<Widget, WelcomeClassView> {
	}

	@UiField Button btnOk;
	@UiField HTMLPanel popupHeader,headingTxt,manageList;
	@UiField InlineHTML headingTxtDesc,manageListDesc;
	@UiField Label popupContentDesc,popupContentDesc1;
	private static final String CLASSKEY = "classpage_welcome_popup_is_autoopen";

	public WelcomeClassView(boolean isHide) {
		super(isHide);
		setWidget(uiBinder.createAndBindUi(this));
		setGlassEnabled(true);
		this.setPixelSize(520, 453);
		show();
		center();
		setDefaultText();
	}
	/**
	 *
	 * @function setDefaultText
	 *
	 * @created_date : 07-Dec-2014
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
	private void setDefaultText() {
		btnOk.setText(i18n.GL1386());
		StringUtil.setAttributes(btnOk.getElement(), "btnOk", i18n.GL1386(), i18n.GL1386());

		popupHeader.getElement().setInnerHTML(i18n.GL1605());
		StringUtil.setAttributes(popupHeader.getElement(), "pnlPopupHeader", i18n.GL1605(), i18n.GL1605());

		headingTxt.getElement().setInnerHTML(i18n.GL1609());
		StringUtil.setAttributes(headingTxt.getElement(), "pnlHeadingText", i18n.GL1605(), i18n.GL1609());

		manageList.getElement().setInnerHTML(i18n.GL1611());
		StringUtil.setAttributes(manageList.getElement(), "pnlManageList", i18n.GL1611(), i18n.GL1611());

		popupContentDesc.setText(i18n.GL1606());
		StringUtil.setAttributes(popupContentDesc.getElement(), "lblPopupContentDesc", i18n.GL1606(), i18n.GL1606());

		popupContentDesc1.setText(i18n.GL1606_1());
		StringUtil.setAttributes(popupContentDesc1.getElement(), "lblPopupContentDesc1", i18n.GL1606_1(), i18n.GL1606_1());

		headingTxtDesc.setText(i18n.GL1610());
		StringUtil.setAttributes(headingTxtDesc.getElement(), "spnHeadingTxtDesc", i18n.GL1610(), i18n.GL1610());

		manageListDesc.setText(i18n.GL1612());
		StringUtil.setAttributes(manageListDesc.getElement(), "spnManageListDesc", i18n.GL1612(), i18n.GL1612());
	}

	@UiHandler("btnOk")
	public void clickOnOkButton(ClickEvent clickEvent){
		AppClientFactory.getInjector().getUserService().updatePartyCustomField(AppClientFactory.getLoggedInUser().getGooruUId(), CLASSKEY, "false", new SimpleAsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				hide(true);
			}
		});
	}

	@Override
	public void hide(boolean isHide){
		super.hide(isHide);
		Window.enableScrolling(true);
	}

}
