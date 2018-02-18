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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class FolderEditableTextAreaUc extends Composite implements HasValue<String> {

	private static EditableLabelUiBinder uiBinder = GWT.create(EditableLabelUiBinder.class);

	interface EditableLabelUiBinder extends UiBinder<Widget, FolderEditableTextAreaUc> {
	}

	@UiField
	protected HTML html;

	@UiField
	protected DeckPanel deckPanel;

	@UiField
	protected TextArea textArea;

	@UiField
	protected FocusPanel focusPanel;

//	protected String placeholder = "";

	protected String text;


	/**
	 * Class constructor
	 */
	public FolderEditableTextAreaUc() {
		initWidget(uiBinder.createAndBindUi(this));
		focusPanel.getElement().setId("focuspnlFocusPanel");
		deckPanel.getElement().setId("dpnlDeckPanel");
		html.getElement().setId("htmlHtml");
		textArea.getElement().setId("tatTextArea");
		StringUtil.setAttributes(textArea, true);
		deckPanel.showWidget(0);

		textArea.addKeyUpHandler(new ValidateConfirmText());
	}

	private class ValidateConfirmText implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			checkCharacterLimit(textArea.getText());
		}
	}

	/**
	 * Change to edit view
	 */
	public void switchToEdit() {
		if (deckPanel.getVisibleWidget() == 1)
			return;
//		textArea.setText(html.getHTML().equals(getPlaceholder()) ? "" : html.getHTML());
		deckPanel.showWidget(1);
		textArea.setFocus(true);
		textArea.addStyleName("shelfEditDescFolder");
		textArea.getElement().setAttribute("maxlength", "415");
	}

	/**
	 * Change to label view
	 */
	public void switchToLabel() {
		if (deckPanel.getVisibleWidget() == 0)
			return;
		setValue(textArea.getText(), true); // fires events, too
		deckPanel.showWidget(0);
		String text = getValue();

		if ((getText() != null || text.trim().length() > 0)) {

			setText(text);
			onEditDisabled(getValue());
		}
	}
	public void switchToDescCancelLabel() {
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
//		return textArea.getText().trim().equals(getPlaceholder()) ? "" : textArea.getText();
		return textArea.getText().trim();
	}

	@Override
	public void setValue(String value) {
		/*if (value == null || value.trim().length() == 0) {
			value = getPlaceholder();
		}*/
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

}
