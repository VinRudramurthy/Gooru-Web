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
package org.ednovo.gooru.client.mvp.shelf;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public abstract class DeleteConfirmPopupVc extends AppPopUp {


	@UiField Button okButtonUc,cancelAnr;

	@UiField
	TextBox inlineTxtBox;

	@UiField
	Label entityLbl,loadingTextLbl,permenantText,typeDeleteText;

	@UiField
	FlowPanel buttonContainer,msgFlowPanel;

	@UiField
	HTML confirmMessagesText;


	private static DeleteConfirmPopupVcUiBinder uiBinder = GWT.create(DeleteConfirmPopupVcUiBinder.class);

    MessageProperties i18n = GWT.create(MessageProperties.class);

	interface DeleteConfirmPopupVcUiBinder extends UiBinder<Widget, DeleteConfirmPopupVc> {
	}

	private String confirmText = i18n.GL0558().toUpperCase();

	/**
	 * Class constructor to set title and content text for pop up
	 *
	 * @param title
	 *            is the header of the pop up
	 * @param entityInfo
	 *            is the content text of the pop up
	 */
	public DeleteConfirmPopupVc(String title, String entityInfo) {
		super();
		setContent(title, uiBinder.createAndBindUi(this));
		setStyleName("deleteResourcePopup");
		inlineTxtBox.getElement().setAttribute("placeholder", i18n.GL0826());
		StringUtil.setAttributes(inlineTxtBox, true);
		inlineTxtBox.addKeyUpHandler(new ValidateConfirmText());
		inlineTxtBox.getElement().setId("txtInline");
		okButtonUc.getElement().setId("btnOk");
		cancelAnr.getElement().setId("lnkCancel");
		msgFlowPanel.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		entityLbl.setText(" "+entityInfo);
		entityLbl.getElement().setAttribute("alt",entityInfo);
		entityLbl.getElement().setAttribute("title",entityInfo);

        setModal(true);
		Window.enableScrolling(false);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
        loadingTextLbl.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
        loadingTextLbl.setVisible(false);
        buttonContainer.getElement().setId("fpnlButtonContainer");
        buttonContainer.setVisible(true);
		show();
		center();
		confirmMessagesText.setHTML(i18n.GL0824());
		confirmMessagesText.getElement().setId("htmlConfirmMessagesText");
		confirmMessagesText.getElement().setAttribute("alt",i18n.GL0824());
		confirmMessagesText.getElement().setAttribute("title",i18n.GL0824());

		permenantText.setText(i18n.GL0825());
		permenantText.getElement().setId("lblPermenantText");
		permenantText.getElement().setAttribute("alt",i18n.GL0825());
		permenantText.getElement().setAttribute("title",i18n.GL0825());

		typeDeleteText.setText(i18n.GL0826());
		typeDeleteText.getElement().setId("lblTypeDeleteText");
		typeDeleteText.getElement().setAttribute("alt",i18n.GL0826());
		typeDeleteText.getElement().setAttribute("title",i18n.GL0826());

		okButtonUc.setText(i18n.GL0190());
		okButtonUc.getElement().setAttribute("alt",i18n.GL0190());
		okButtonUc.getElement().setAttribute("title",i18n.GL0190());

		cancelAnr.setText(i18n.GL0142());
		cancelAnr.getElement().setAttribute("alt",i18n.GL0142());
		cancelAnr.getElement().setAttribute("title",i18n.GL0142());

		loadingTextLbl.setText(i18n.GL0560());
		loadingTextLbl.getElement().setId("lblLoadingTextLbl");
		loadingTextLbl.getElement().setAttribute("alt",i18n.GL0560());
		loadingTextLbl.getElement().setAttribute("title",i18n.GL0560());

		msgFlowPanel.getElement().setId("fpnlMsgFlowPanel");
		entityLbl.getElement().setId("lblEntityLbl");
	}
	/**
	 * Hide {@link DeleteConfirmPopupVc} popup
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("cancelAnr")
	public void onCancelClick(ClickEvent clickEvent) {
		hide();
		Window.enableScrolling(true);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
	}

	/**
	 * confirm to to remove the content
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("okButtonUc")
	public void removeResource(ClickEvent clickEvent) {
		if (inlineTxtBox.getText().trim().equalsIgnoreCase(getConfirmText())) {
			this.onTextConfirmed();
			buttonContainer.setVisible(false);
			loadingTextLbl.setVisible(true);
		}
	}

	/**
	 * key handler to validate action text
	 *
	 * @author Search Team
	 *
	 */
	private class ValidateConfirmText implements KeyUpHandler {
		@Override
		public void onKeyUp(KeyUpEvent event) {
			okButtonUc.setStyleName("deleteCollectionDisabledButton");
			if (inlineTxtBox.getText().trim().equalsIgnoreCase(getConfirmText())) {
				okButtonUc.setStyleName("overRideBlueButtonDelete");
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					onTextConfirmed();
				}
			}
		}
	}

	public abstract void onTextConfirmed();

	/**
	 * @return the confirmText
	 */
	public String getConfirmText() {
		return confirmText;
	}

	/**
	 * @param confirmText
	 *            the confirmText to set
	 */
	public void setConfirmText(String confirmText) {
		this.confirmText = confirmText;
	}

}
