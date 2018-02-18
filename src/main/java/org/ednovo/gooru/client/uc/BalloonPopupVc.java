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

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class BalloonPopupVc extends Composite {

	private static BalloonPopupVcUiBinder uiBinder = GWT.create(BalloonPopupVcUiBinder.class);

	MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	public Label popupDesc;

	@UiField public HTMLPanel popupContainer;

	interface BalloonPopupVcUiBinder extends UiBinder<Widget, BalloonPopupVc> {
	}

	/**
	 * Class constructor
	 * @param title of the popup
	 * @param desc popup description
	 */
	public BalloonPopupVc(String title, String desc) {
		initWidget(uiBinder.createAndBindUi(this));
//		popupDesc.setText(StringUtil.truncateText(desc,60));
		popupContainer.getElement().setId("pnlPopupContainer");
		popupDesc.setText(i18n.GL1013());
		popupDesc.getElement().setId("lblPopupDesc");
		popupDesc.getElement().setAttribute("alt", i18n.GL1013());
		popupDesc.getElement().setAttribute("title", i18n.GL1013());
		popupDesc.setText(desc);
		popupDesc.getElement().setAttribute("alt", desc);
		popupDesc.getElement().setAttribute("title", desc);
	}


	/**
	 * This method is to get the popupDesc
	 */
	public Label getPopupDesc() {
		return popupDesc;
	}

	/**
	 * This method is to set the popupDesc
	 */
	public void setPopupDesc(Label popupDesc) {
		this.popupDesc = popupDesc;
	}
	/**
	 * This method is to get the popupContainer
	 */
	public HTMLPanel getPopupContainer() {
		return popupContainer;
	}


	/**
	 * This method is to set the popupContainer
	 */
	public void setPopupContainer(HTMLPanel popupContainer) {
		this.popupContainer = popupContainer;
	}
}
