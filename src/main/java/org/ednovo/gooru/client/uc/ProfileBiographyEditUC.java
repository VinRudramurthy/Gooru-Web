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

import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ProfileBiographyEditUC extends Composite implements
		HasValue<String> {

	private static ProfileBiographyEditUCUiBinder uiBinder = GWT
			.create(ProfileBiographyEditUCUiBinder.class);

	interface ProfileBiographyEditUCUiBinder extends
			UiBinder<Widget, ProfileBiographyEditUC> {
	}

	@UiField
	protected HTMLPanel editLabel;

	@UiField
	protected DeckPanel deckPanel;

	@UiField
	protected TextArea editTextBox;

	@UiField
	protected FocusPanel focusPanel;
	protected String placeholder = "";

	private InlineLabel biographyLabel;
	private InlineLabel biographyEditImage;

	protected String text;

	public ProfileBiographyEditUC() {
		initWidget(uiBinder.createAndBindUi(this));
		focusPanel.getElement().setId("focuspnlFocusPanel");
		deckPanel.getElement().setId("dpnlDeckPanel");
		editLabel.getElement().setId("pnlEditLabel");
		deckPanel.showWidget(0);

		biographyLabel = new InlineLabel();
		biographyEditImage = new InlineLabel();
		biographyEditImage.setStyleName("Uc-editImage");

		editTextBox.setHeight("146px");
		editTextBox.setWidth("200px");
		editTextBox.getElement().setAttribute("maxlength", "500");
		editTextBox.addKeyUpHandler(new ValidateConfirmText());
		editTextBox.getElement().setAttribute("id", "txtAreaAboutYourself");
		StringUtil.setAttributes(editTextBox, true);

	}

	public InlineLabel getBiographyEditImage() {
		return biographyEditImage;
	}

	private class ValidateConfirmText implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			checkCharacterLimit(editTextBox.getText());
		}
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
