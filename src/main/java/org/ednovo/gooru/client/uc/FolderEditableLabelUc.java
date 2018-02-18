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

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
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
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class FolderEditableLabelUc extends Composite implements HasValue<String>{

	private static EditableLabelUc1UiBinder uiBinder = GWT.create(EditableLabelUc1UiBinder.class);

	interface EditableLabelUc1UiBinder extends UiBinder<Widget, FolderEditableLabelUc> {
	}
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	protected Label editLabel;

	@UiField
	protected DeckPanel deckPanel;

	@UiField
	protected TextBox editTextBox;

	@UiField
	protected FocusPanel focusPanel;

	protected String placeholder = "";

	protected String text;


	/**
	 * Class constructor
	 */
	public FolderEditableLabelUc() {
		initWidget(uiBinder.createAndBindUi(this));
		focusPanel.getElement().setId("focuspnlFocusPanel");
		deckPanel.getElement().setId("dpnlDeckPanel");
		editLabel.getElement().setId("lblEditLabel");
		editTextBox.getElement().setId("txtEditTextBox");
		StringUtil.setAttributes(editTextBox, true);
		deckPanel.showWidget(0);


		editTextBox.addKeyUpHandler(new ValidateConfirmText());
	}

	private class ValidateConfirmText implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
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
		editTextBox.addStyleName("shelfEditTitleForFolders");
		editTextBox.setMaxLength(50);
	}

	/**
	 * Change to label
	 */
	public void switchToLabel() {
		if (deckPanel.getVisibleWidget() == 0)
			return;
		if(editTextBox.getText().length() > 0){
			setValue(editTextBox.getText(), true); // fires events, too
		}else{
			new AlertContentUc(i18n.GL0061(),i18n.GL1026()+i18n.GL_SPL_EXCLAMATION());
			return;
		}
		deckPanel.showWidget(0);
		String text = getValue();
		if ((getText() != null && text.trim().length() > 0) && !getText().equals(text)) {
			setText(text);
			onEditDisabled(getValue());
		}
	}
	public void switchToTitleCancelLabel() {
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
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public String getValue() {
		return editLabel.getText();
	}

	@Override
	public void setValue(String value) {
		editLabel.setText(value);
		editLabel.getElement().setAttribute("alt", value);
		editLabel.getElement().setAttribute("title", value);
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

	public TextBox getTextBoxSource(){
		return editTextBox;
	}
}
