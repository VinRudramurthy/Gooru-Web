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

import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class LabelGlassPanel extends AbsolutePanel {

	protected Label glassPanelText;
	protected PopupPanel glassPanel;
	protected FlowPanel contentPanel;
	protected FlowPanel topPanel;

	/**
	 * Class constructor
	 */
	public LabelGlassPanel() {
		super();
		glassPanelText = new Label();
		topPanel = new FlowPanel();
		glassPanelText.setStyleName("Uc-labelGlassPanelText");
		glassPanel = new PopupPanel(false);
		glassPanel.setGlassEnabled(true);
		topPanel.add(glassPanelText);
		contentPanel = new FlowPanel();
		this.add(contentPanel);
		this.add(glassPanel);

		this.add(topPanel);
		this.setWidgetPosition(glassPanel, 0, 0);
		this.setWidgetPosition(topPanel, 0, 0);
		setGlassVisible(false);
	}

	public void setGlassText(String label) {
		glassPanelText.setText(label);
	}

	public void setGlassStyleName(String style) {
		glassPanel.addStyleName(style);
	}

	public void setTopStyleName(String style) {
		topPanel.addStyleName(style);
	}

	public FlowPanel getTopPanel() {
		return topPanel;
	}

	public FlowPanel getContentPanel() {
		return contentPanel;
	}

	public PopupPanel getGlassPanel() {
		return glassPanel;
	}

	public void setGlassVisible(boolean visible) {
		getTopPanel().setHeight(getOffsetHeight() + "px");
		getGlassPanel().setVisible(visible);
		getTopPanel().setVisible(visible);
	}

	@UiChild(tagname = "glassContent")
	public void setContent(Widget widget) {
		contentPanel.add(widget);
	}
}
