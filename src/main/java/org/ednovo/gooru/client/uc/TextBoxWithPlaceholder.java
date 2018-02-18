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
/**
 *
 */
package org.ednovo.gooru.client.uc;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * @author SearchTeam
 *
 */
public class TextBoxWithPlaceholder extends TextBox {

	private String placeholder = null;
	private boolean isPlaceHolderVisible = false;
	private boolean isPassword;
	private boolean isSetFocus=false;

	/**
	 * @param element
	 *            the element to be wrapped
	 */
	public static TextBoxWithPlaceholder wrap(Element element) {
		// Assert that the element is attached.
		assert Document.get().getBody().isOrHasChild(element);

		TextBoxWithPlaceholder textBox = new TextBoxWithPlaceholder(element);
		// Mark it attached and remember it for cleanup.
		textBox.onAttach();
		textBox.getElement().setAttribute("spellcheck", "true");
		RootPanel.detachOnWindowClose(textBox);

		return textBox;
	}

	/**
	 * Creates an empty password text box.
	 */
	public TextBoxWithPlaceholder() {
		this(DOM.createInputText());
		setStyleName("Uc-textBoxWithPlaceholder");
		this.getElement().setAttribute("spellcheck", "true");
	}

	/**
	 * This constructor may be used by subclasses to explicitly use an existing
	 * element. This element must be an &lt;input&gt; element whose type is
	 * 'text'.
	 *
	 * @param element
	 *            the element to be used
	 */
	protected TextBoxWithPlaceholder(Element element) {
		super(element);
		setupHandlers();
		this.getElement().setAttribute("spellcheck", "true");
	}

	@Override
	public String getText() {
		if (isPlaceHolderVisible) {
			return "";
		} else {
			return super.getText();
		}
	}

	@Override
	public void setText(String text) {
		super.setText(text);

		if (text == null || text.length() == 0 ) {
			showPlaceholder();
		} else {
			hidePlaceholder(text);
		}
		setFocus(true);
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String value) {
		if (isPlaceHolderVisible && value != null && !value.equals(placeholder)) {
			// clear the text so the new placeholder will be displayed
			super.setText("");
		}

		placeholder = value;
		showPlaceholder();
	}

	private void setupHandlers() {
		addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				hidePlaceholder();
			}
		});

		addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				showPlaceholder();
			}
		});
	}

	private void showPlaceholder() {
		if (super.getText().equals("") && getPlaceholder() != null) {
			addStyleName("Uc-textBoxWithPlaceholderText");
			super.setText(getPlaceholder());
			isPlaceHolderVisible = true;
			if(isPassword()) {
				getElement().setPropertyString("type", "text");
			}
		}
	}

	private void hidePlaceholder(String newText) {
		if (isPlaceHolderVisible) {
			removeStyleName("Uc-textBoxWithPlaceholderText");
			super.setText(newText);
			isPlaceHolderVisible = false;
			if(isPassword()) {
				getElement().setPropertyString("type", "password");
			}
		}
	}

	private void hidePlaceholder() {
		hidePlaceholder("");
	}

	/**
	 * @return the isPassword
	 */
	public boolean isPassword() {
		return isPassword;
	}

	/**
	 * @param isPassword the isPassword to set
	 */
	public void setPassword(boolean isPassword) {
		this.isPassword = isPassword;
	}
	public void onLoad(){
		super.onLoad();
		Scheduler.get().scheduleDeferred(new  ScheduledCommand(){
			public void execute() {
				if(isSetFocus){
					setText(null);
				}

			}
		});
	}

	public boolean isSetFocus() {
		return isSetFocus;
	}

	public void setSetFocus(boolean isSetFocus) {
		this.isSetFocus = isSetFocus;
	}


}
