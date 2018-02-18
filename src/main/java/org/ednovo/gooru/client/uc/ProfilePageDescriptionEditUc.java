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
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ProfilePageDescriptionEditUc extends Composite implements
		HasValue<String> {

	private static ProfileBiographyEditUCUiBinder uiBinder = GWT
			.create(ProfileBiographyEditUCUiBinder.class);

	interface ProfileBiographyEditUCUiBinder extends
			UiBinder<Widget, ProfilePageDescriptionEditUc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	protected HTMLPanel editLabel;

	@UiField
	protected DeckPanel deckPanel;

	@UiField
	protected TextArea editTextBox;

	@UiField
	protected FocusPanel focusPanel;
	@UiField Label errorLabel,errorLabelForEditText;
	protected String placeholder = "";

	private Label biographyLabel;
	private Label biographyEditImage;
	public boolean isHavingBadWordsInTextbox;
	protected String text;

	public ProfilePageDescriptionEditUc() {
		initWidget(uiBinder.createAndBindUi(this));
		focusPanel.getElement().setId("focuspnlFocusPanel");
		deckPanel.getElement().setId("dpnlDeckPanel");
		editLabel.getElement().setId("pnlEditLabel");
		deckPanel.showWidget(0);
		errorLabel.setText(i18n.GL1043());
		errorLabel.getElement().setId("lblErrorLabel");
		errorLabel.getElement().setAttribute("alt", i18n.GL1043());
		errorLabel.getElement().setAttribute("title", i18n.GL1043());
		biographyLabel = new Label();
		biographyLabel.getElement().setAttribute("style", "float: left; max-width: 709px; min-height: 33px;");

		biographyEditImage = new Label(i18n.GL1786());
		biographyEditImage.setStyleName("Uc-editImage");
		errorLabel.setVisible(false);
		errorLabelForEditText.getElement().setId("lblErrorLabelForEditText");
		errorLabelForEditText.getElement().setAttribute("style", "float: left;text-align: right;width: 76%;");
		errorLabelForEditText.setVisible(false);
		editTextBox.getElement().setAttribute("maxlength", "725");
		editTextBox.addKeyUpHandler(new ValidateConfirmText());
		editTextBox.getElement().setAttribute("id", "txtAreaAboutYourself");
		StringUtil.setAttributes(editTextBox, true);
		editTextBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				Map<String, String> parms = new HashMap<String, String>();
				parms.put("text", editTextBox.getText());
				AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean value) {
							isHavingBadWordsInTextbox = value;
							SetStyleForProfanity.SetStyleForProfanityForTextArea(editTextBox, errorLabelForEditText, value);
					}
				});
			}
		});

	}

	public Label getBiographyEditImage() {
		return biographyEditImage;
	}

	private class ValidateConfirmText implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			errorLabel.setVisible(false);
			checkCharacterLimit(editTextBox.getText());
		}
	}

	/**
	 * Change to editable view
	 */
	public void switchToEdit() {
		if (deckPanel.getVisibleWidget() == 1)
			return;
		editTextBox.setText(getValue());
		editTextBox.getElement().setAttribute("alt", getValue());
		editTextBox.getElement().setAttribute("title", getValue());
		deckPanel.showWidget(1);
		editTextBox.setFocus(true);

	}

	/**
	 * Change to label
	 */
	public void switchToLabel() {

		if (deckPanel.getVisibleWidget() == 0)
			return;

		setValue(editTextBox.getText().trim(), true); // fires events, too
		String text = getValue();
		deckPanel.showWidget(0);
		setText(editTextBox.getText());
		onEditDisabled(editTextBox.getText());

	}

	public Label getErrorLabel()
	{
		return errorLabelForEditText;
	}
	public TextArea geteditTextBox()
	{
		return editTextBox;
	}
	public void cancel() {
		editTextBox.setText(biographyLabel.getText());
		editTextBox.getElement().setAttribute("alt", biographyLabel.getText());
		editTextBox.getElement().setAttribute("title", biographyLabel.getText());
		errorLabel.setVisible(false);
		deckPanel.showWidget(0);
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

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public String getValue() {
		return editTextBox.getText();
	}

	@Override
	public void setValue(String value) {
		biographyLabel.setText(value.length() > 500 ? value.substring(0, 500) + "..."
				: value);
		editLabel.add(biographyLabel);
		editLabel.add(biographyEditImage);
		editTextBox.setText(value);
		editTextBox.getElement().setAttribute("alt", value);
		editTextBox.getElement().setAttribute("title", value);
	}

	/**
	 * @return placeholder
	 */
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

	@Override
	public void setValue(String value, boolean fireEvents) {

		if (fireEvents)
			ValueChangeEvent.fireIfNotEqual(this, getValue(), value);
		setValue(value);
	}

	public TextArea getTextBoxSource() {
		return editTextBox;
	}





}
