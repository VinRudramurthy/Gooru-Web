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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class DisclosurePanelUc extends Composite implements ClickHandler {

	private static DisclosurePanelUcUiBinder uiBinder = GWT.create(DisclosurePanelUcUiBinder.class);

	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface DisclosurePanelUcUiBinder extends UiBinder<Widget, DisclosurePanelUc> {
	}

	@UiField
	Image titleImg;

	@UiField
	Label titleLbl;

	@UiField
	FlowPanel contentPanel;

	@UiField
	DisclosurePanel filterPanel;

	@UiField
	FocusPanel headerPanel;

	@UiField
	SimplePanel imagePanel;

	/**
	 * Class constructor
	 */
	public DisclosurePanelUc() {
		initWidget(uiBinder.createAndBindUi(this));
		filterPanel.getElement().setId("discpnlFilterPanel");
		headerPanel.getElement().setId("focuspnlHeaderPanel");
		imagePanel.getElement().setId("spnlImagePanel");
		titleImg.setAltText(i18n.GL1023());
		titleImg.getElement().setId("imgTitleImg");
		titleImg.getElement().setAttribute("alt", i18n.GL1023());
		titleImg.getElement().setAttribute("title", i18n.GL1023());
		titleLbl.getElement().setId("lblTitleLbl");
		contentPanel.getElement().setId("fpnlContentPanel");
		titleImg.setUrl("images/Collection-Search/dropdown-arrow-active.png");
		headerPanel.addClickHandler(this);
	}

	public void setHeaderTitle(String title) {
		titleLbl.setText(title);
		titleLbl.getElement().setAttribute("alt", title);
		titleLbl.getElement().setAttribute("title", title);
	}

	/**
	 * Clear the widget
	 */
	public void clear() {
		contentPanel.clear();
	}

	/**
	 * @return contentPanel instance of {@link FlowPanel}
	 */
	public FlowPanel getContent() {
		return contentPanel;
	}

	@Override
	public void onClick(ClickEvent event) {
		if (!filterPanel.isOpen()) {
			titleImg.setUrl("images/Collection-Search/dropdown-arrow-active.png");
			imagePanel.setStyleName("Uc-disclosurePanelUcHeaderImgOpen");
		} else {
			titleImg.setUrl("images/Collection-Search/dropdown-arrow.png");
			imagePanel.setStyleName("Uc-disclosurePanelUcHeaderImgClose");
		}
	}

	/**
	 * Add widget to the flow panel
	 * @param widget instance of {@link Widget}
	 */
	@UiChild(tagname = "add")
	public void addWidget(Widget widget) {
		contentPanel.add(widget);
	}
}
