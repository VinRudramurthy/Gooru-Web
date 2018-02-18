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
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
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
 * @fileName : EditableLabelUc.java
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
public class EditableLabelUc extends Composite implements HasValue<String> {

	private static EditableLabelUcUiBinder uiBinder = GWT
			.create(EditableLabelUcUiBinder.class);

	interface EditableLabelUcUiBinder extends UiBinder<Widget, EditableLabelUc> {
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

	boolean isHavingBadWords=false;

	/**
	 * Class constructor
	 */
	public EditableLabelUc() {
		initWidget(uiBinder.createAndBindUi(this));
		deckPanel.showWidget(0);
		focusPanel.getElement().setId("focuspnlFocusPanel");
		deckPanel.getElement().setId("dpnlDeckPanel");
		editLabel.getElement().setId("lblEditLabel");
		editTextBox.getElement().setId("txtEditTextBox");
		StringUtil.setAttributes(editTextBox, true);
		/*
		 * focusPanel.addFocusHandler(new FocusHandler() {
		 *
		 * @Override public void onFocus(FocusEvent event) { switchToEdit(); }
		 * });
		 *
		 * focusPanel.addClickHandler(new ClickHandler() {
		 *
		 * @Override public void onClick(ClickEvent event) { switchToEdit(); }
		 * });
		 *
		 * editLabel.addClickHandler(new ClickHandler() {
		 *
		 * @Override public void onClick(ClickEvent event) { switchToEdit(); }
		 * });
		 *
		 * editTextBox.addBlurHandler(new BlurHandler() {
		 *
		 * @Override public void onBlur(BlurEvent event) { switchToLabel(); }
		 * });
		 */
		editTextBox.addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				Map<String, String> parms = new HashMap<String, String>();
				parms.put("text", editTextBox.getText());
				AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean value) {
						isHavingBadWords = value;
						if (value){
							editTextBox.getElement().getStyle().setBorderColor("orange");
							showProfanityError(true);
						}else{
							showProfanityError(false);
							setValue(editTextBox.getText(), true); // fires events, too

							editTextBox.getElement().getStyle().clearBackgroundColor();
							editTextBox.getElement().getStyle().setBorderColor("#ccc");

						}
					}
				});
			}
		});
		editTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				editTextBox.getElement().getStyle().clearBackgroundColor();
				editTextBox.getElement().getStyle().setBorderColor("#ccc");
				showProfanityError(false);
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					Map<String, String> parms = new HashMap<String, String>();
					parms.put("text", editTextBox.getText());
					AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

						@Override
						public void onSuccess(Boolean value) {
							isHavingBadWords = value;
							if (value){
								editTextBox.getElement().getStyle().setBorderColor("orange");
								showProfanityError(true);
							}else{
								switchToLabel();
								showProfanityError(false);
								editTextBox.getElement().getStyle().clearBackgroundColor();
								editTextBox.getElement().getStyle().setBorderColor("#ccc");
							}
						}
					});
				} else if (event.getCharCode() == KeyCodes.KEY_ESCAPE) {
					editTextBox.setText(editLabel.getText()); // reset to the original value
					editTextBox.getElement().setAttribute("alt", editLabel.getText());
					editTextBox.getElement().setAttribute("title", editLabel.getText());
				}
			}
		});

		editTextBox.addKeyUpHandler(new ValidateConfirmText());
	}

	private class ValidateConfirmText implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			checkCharacterLimit(editTextBox.getText());
			if(editTextBox.getText().length() >= 50){
				editTextBox.getElement().getStyle().setBorderColor("orange");
			}else{
				editTextBox.getElement().getStyle().clearBackgroundColor();
				editTextBox.getElement().getStyle().setBorderColor("#ccc");
			}
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
		editTextBox.addStyleName("shelfEditTitle");
		editTextBox.setMaxLength(50);
		editTextBox.getElement().getStyle().setBorderColor("#ccc");
		editTextBox.getElement().getStyle().setHeight(20, Unit.PX);
	}

	/**
	 * Change to label
	 */
	public void switchToLabel() {
		if (deckPanel.getVisibleWidget() == 0)
			return;
		if (editTextBox.getText().length() > 0 && !isHavingBadWords) {
			setValue(editTextBox.getText(), true); // fires events, too
		} else {
			if (isHavingBadWords){
				editTextBox.getElement().getStyle().setBorderColor("orange");
			}else{
//				new AlertContentUc(i18n.GL0061(),i18n.GL1026()+i18n.GL_SPL_EXCLAMATION());
				showErrorMessage(i18n.GL1026()+i18n.GL_SPL_EXCLAMATION());
			}
			return;
		}
		deckPanel.showWidget(0);
		String text = getValue();
		if ((getText() != null && text.trim().length() > 0)) {
			setText(text);
			onEditDisabled(getValue());
		}
	}
	/**
	 * For cancel edit view
	 */
	public void switchToCancelLabel() {
		deckPanel.showWidget(0);
	}


	public void showErrorMessage(String message){

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

	// Override this method to catch on blur
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

	public void setFocus(){
		editTextBox.setFocus(true);
	}

	public TextBox getTextBoxSource() {
		return editTextBox;
	}
	public Label getLabelField() {
		return editLabel;
	}


}
