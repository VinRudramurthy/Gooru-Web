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

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.SuggestBox;

/**
 * @author Search Team
 *
 */
public abstract class AppCenturyTagSuggestBox extends SuggestBox implements KeyUpHandler, KeyDownHandler,HasClickHandlers {

	private boolean validation = true;

	private AppMultiWordSuggestOracle suggestionOrcl;
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor
	 */
	public AppCenturyTagSuggestBox() {
		super();
	}

	/**
	 * Class constructor with one parameter
	 * @param suggestOrcl instance of AppMultiWordSuggestOracle
	 */
	public AppCenturyTagSuggestBox(AppMultiWordSuggestOracle suggestOrcl) {
		super(suggestOrcl);
		this.suggestionOrcl = suggestOrcl;
//		this.getTextBox().getElement().setAttribute("placeholder", "e.g. CCSS.M.8.F.A.3");
		if (AppClientFactory.getLoggedInUser().getUsername()!=null){

				this.getTextBox().getElement().setAttribute("placeholder", i18n.GL3122_1());
			
		}else{
			this.getTextBox().getElement().setAttribute("placeholder", i18n.GL3122_1());
		}
		this.getValueBox().addKeyUpHandler(this);
		this.getValueBox().addKeyDownHandler(this);
	}

	/*
	 * Unused Methods
	 * public final void reset() {
		this.setText("");
		this.getSuggestionOrcl().clear();
	}

	public final void addAll(List<String> suggestions) {
		this.getSuggestionOrcl().clear();
		this.getSuggestionOrcl().addAll(suggestions);
	}*/

	public final boolean validate() {
		if (getSuggestionOrcl().getList().size() != 0 && getSuggestionOrcl().getList().contains(this.getText())) {
			return true;
		}
		this.setText("");
		return false;
	}

	public final String getSuggestionValue() {
		if (validate()) {
			return this.getText();
		}
		return null;
	}

	@Override
	public final void onKeyDown(KeyDownEvent event) {
		int keyCode = event.getNativeKeyCode();
		String text = this.getText().trim();
			if ((keyCode == (char) KeyCodes.KEY_TAB) || (keyCode == (char) KeyCodes.KEY_LEFT) || (keyCode == (char) KeyCodes.KEY_RIGHT) && (keyCode != (char) KeyCodes.KEY_DOWN) && (keyCode != (char) KeyCodes.KEY_UP)) {
				keyDownAction(text);
			}
		
	}

	@Override
	public final void onKeyUp(KeyUpEvent event) {
		int keyCode = event.getNativeKeyCode();
		String text = this.getText().trim();
		if (keyCode != (char) KeyCodes.KEY_TAB && keyCode != (char) KeyCodes.KEY_ENTER && keyCode != (char) KeyCodes.KEY_LEFT && (keyCode != (char) KeyCodes.KEY_RIGHT) && (keyCode != (char) KeyCodes.KEY_DOWN) && (keyCode != (char) KeyCodes.KEY_UP)) {
			keyAction(text,event);
		}
	}

	public void keyDownAction(String text) {
		if (text.equals("") || !this.validate()) {
			this.getValueBox().cancelKey();
		}
	}

	public abstract void keyAction(String text,KeyUpEvent event);

	public final AppMultiWordSuggestOracle getSuggestionOrcl() {
		return suggestionOrcl;
	}

	public final void setValidation(boolean validation) {
		this.validation = validation;
	}
}
