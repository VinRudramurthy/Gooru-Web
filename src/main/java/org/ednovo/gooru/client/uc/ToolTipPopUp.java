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

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public class ToolTipPopUp extends PopupPanel {

	private static ToolTipPopUpUiBinder uiBinder = GWT
			.create(ToolTipPopUpUiBinder.class);

	interface ToolTipPopUpUiBinder extends UiBinder<Widget, ToolTipPopUp> {
	}
	
	HTML text = new HTML();
	
	@UiField HTMLPanel popupArrow,downPopupArrow;
	
	@UiField
	SimplePanel contentPanel;
	

	@UiConstructor
	public ToolTipPopUp(String description, int leftPos, int rightPos) { 
		setWidget(uiBinder.createAndBindUi(this));
		text.setHTML(description);
		setContentPanel(text);
		setPopupPosition(leftPos, rightPos);
		popupArrow.getElement().setId("pnlPopupArrow");
		contentPanel.getElement().setId("spnlContentPanel");
		downPopupArrow.getElement().setId("pnlDownPopupArrow");
	}
	
	

	public ToolTipPopUp(Widget widget, int leftPos, int rightPos) {
		setWidget(uiBinder.createAndBindUi(this));
		setContentPanel(widget);
		setPopupPosition(leftPos, rightPos);
		popupArrow.getElement().setId("pnlPopupArrow");
		contentPanel.getElement().setId("spnlContentPanel");
		downPopupArrow.getElement().setId("pnlDownPopupArrow");
	}



	public SimplePanel getContentPanel() {
		return contentPanel;
	}

	public void setContentPanel(Widget contentPanel) {
		this.contentPanel.setWidget(contentPanel);
	}

}
