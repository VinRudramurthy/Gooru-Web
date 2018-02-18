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

import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author Gooru Team
 *
 */
public abstract class CloseLabelCentury extends FlowPanel implements ClickHandler {

	private Label label;

	private Label removeLabel;

	MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor
	 * @param text for label
	 */
	public CloseLabelCentury(String text) {
		removeLabel = new Label();
		removeLabel.setStyleName("Uc-closeLabelRemoveInCentury");
		removeLabel.setText(i18n.GL_GRR_Close()+" ");
		label = new Label();
		label.setStyleName("Uc-closeLabelText");
		label.setText(text);
		setStyleName("Uc-closeLabelCentury");
		add(removeLabel);
		add(label);
		removeLabel.addClickHandler(this);
	}

	public abstract void onCloseLabelClick(ClickEvent event);

	public Label getLabelSource() {
		return removeLabel;
	}

	public String getSourceText() {
		return label.getText();
	}

	@Override
	public final void onClick(ClickEvent event) {
		if (event.getSource().equals(removeLabel)) {
			onCloseLabelClick(event);
			this.removeFromParent();
		}
	}

	public void disableParentRemoveFunction() {
		removeLabel.getElement().getStyle().setDisplay(Display.NONE);
	}
}
