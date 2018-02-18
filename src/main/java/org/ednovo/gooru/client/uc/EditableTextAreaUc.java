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
package org.ednovo.gooru.client.uc;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * @fileName : EditableTextAreaUc.java
 *
 * @description :
 *
 *
 * @version : 5.5
 *
 * @date: June 17, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class EditableTextAreaUc extends Composite implements HasValue<String> {

	private static EditableLabelUiBinder uiBinder = GWT
			.create(EditableLabelUiBinder.class);

	interface EditableLabelUiBinder extends
			UiBinder<Widget, EditableTextAreaUc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	protected HTML html;

	@UiField
	protected DeckPanel deckPanel;

	@UiField
	protected TextArea textArea;

	@UiField
	protected FocusPanel focusPanel;

	protected String placeholder = "";

	protected String text;

	@UiField Label lblErrorMessage;

	@UiField HTMLPanel duplicateTinyMce,fakeContent;

	/**
	 * Class constructor
	 */
	public EditableTextAreaUc() {
		initWidget(uiBinder.createAndBindUi(this));
		deckPanel.showWidget(0);
		focusPanel.getElement().setId("focuspnlFocusPanel");
		deckPanel.getElement().setId("dpnlDeckPanel");
		lblErrorMessage.setVisible(false);
		lblErrorMessage.getElement().setId("lblLblErrorMessage");
		lblErrorMessage.getElement().getStyle().setFloat(Float.LEFT);
		html.getElement().setId("htmlHtml");
		duplicateTinyMce.getElement().setId("pnlDuplicateTinyMce");
		fakeContent.getElement().setId("pnlFakeContent");
		StringUtil.setAttributes(textArea, true);
		textArea.addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				Map<String, String> parms = new HashMap<String, String>();
				parms.put("text", textArea.getText());
				AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean value) {
						boolean isHavingBadWords = value;
						if (value){
							textArea.getElement().getStyle().setBorderColor("orange");
							lblErrorMessage.setText(i18n.GL0554());
							lblErrorMessage.getElement().setAttribute("alt", i18n.GL0554());
							lblErrorMessage.getElement().setAttribute("title", i18n.GL0554());
							lblErrorMessage.setVisible(true);
							showProfanityError(true);
						}else{
							showProfanityError(false);
							textArea.getElement().getStyle().clearBackgroundColor();
							textArea.getElement().getStyle().setBorderColor("#ccc");
							lblErrorMessage.setVisible(false);
						}
					}
				});
			}
		});

		textArea.addKeyUpHandler(new ValidateConfirmText());
	}

	private class ValidateConfirmText implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			textArea.getElement().getStyle().clearBackgroundColor();
			textArea.getElement().getStyle().setBorderColor("#ccc");
			lblErrorMessage.setVisible(false);
			checkCharacterLimit(textArea.getText());
		}
	}

	/**
	 * Change to edit view
	 */
	public void switchToEdit() {
		textArea.getElement().getStyle().clearBackgroundColor();
		textArea.getElement().getStyle().setBorderColor("#ccc");

		if (deckPanel.getVisibleWidget() == 1)
			return;
		textArea.setText(html.getHTML().equals(getPlaceholder()) ? "" :
		html.getHTML());
		textArea.getElement().setAttribute("alt", html.getHTML().equals(getPlaceholder()) ? "" :html.getHTML());
		textArea.getElement().setAttribute("title", html.getHTML().equals(getPlaceholder()) ? "" :html.getHTML());
		deckPanel.showWidget(1);
		textArea.setFocus(true);
		textArea.addStyleName("shelfEditDesc");
		textArea.getElement().setAttribute("maxlength", "1000");
	}

	/**
	 * Change to label view
	 */
	public void switchToLabel() {
		if (deckPanel.getVisibleWidget() == 0)
			return;


		Map<String, String> parms = new HashMap<String, String>();
		parms.put("text", textArea.getText());
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean value) {
				boolean isHavingBadWords = value;
				if (value){
					textArea.getElement().getStyle().setBorderColor("orange");
					lblErrorMessage.setText(i18n.GL0554());
					lblErrorMessage.getElement().setAttribute("alt", i18n.GL0554());
					lblErrorMessage.getElement().setAttribute("title", i18n.GL0554());
					lblErrorMessage.setVisible(true);
					showProfanityError(true);
				}else{
					showProfanityError(false);
					textArea.getElement().getStyle().clearBackgroundColor();
					textArea.getElement().getStyle().setBorderColor("#ccc");
					lblErrorMessage.setVisible(false);

					setValue(textArea.getText().trim().length() == 0 ? getPlaceholder() : textArea.getText(), true); // fires events, too
					deckPanel.showWidget(0);
					String text = getValue();

					if ((textArea.getText() != null || text.trim().length() > 0)) {
						setText(text);
						onEditDisabled(getValue());
					}
				}
			}
		});
	}

	/**
	 * For cancel edit view
	 */
	public void switchToDesCancelLabel() {
		deckPanel.showWidget(0);
		textArea.getElement().getStyle().clearBackgroundColor();
		textArea.getElement().getStyle().setBorderColor("#ccc");
		lblErrorMessage.setVisible(false);
	}

	// Override this method to catch on blur
	/**
	 * @param text
	 */
	public void onEditDisabled(String text) {

	}

	// Override this method to catch on blur
	/**
	 * @param text
	 */
	public void checkCharacterLimit(String text) {

	}

	/**
	 * @param text
	 */
	public void showProfanityError(boolean value) {
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public String getValue() {
		// return textArea.getText().trim().equals(getPlaceholder()) ? "" :
		// textArea.getText();

		return textArea.getText().trim();
	}

	@Override
	public void setValue(String value) {
		if (value == null || value.trim().length() == 0) {
			value = getPlaceholder();
		}
		html.setHTML(value);
		html.getElement().setAttribute("alt", value);
		html.getElement().setAttribute("title", value);
		textArea.setText(value);
		textArea.getElement().setAttribute("alt", value);
		textArea.getElement().setAttribute("title", value);
	}

	public void setExtraHtmlStyleName(String style) {
		html.addStyleName(style);
	}

	public void setExtraTextAreaStyleName(String style) {
		textArea.addStyleName(style);
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		setValue(text);
	}

	public Label getLblErrorMessage() {
		return lblErrorMessage;
	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		if (fireEvents)
			ValueChangeEvent.fireIfNotEqual(this, getValue(), value);
		setValue(value);
	}
}
