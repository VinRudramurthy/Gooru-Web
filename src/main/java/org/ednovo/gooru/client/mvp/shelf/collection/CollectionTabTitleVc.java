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
package org.ednovo.gooru.client.mvp.shelf.collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class CollectionTabTitleVc extends FocusPanel {

	@UiField
	Label labelLbl;

	@UiField
	SimplePanel imageSimPanel;

	@UiField
	FlowPanel wrapperFloPanel;

	String title ="";

	private static CollectionTabTitleVc selectedTabTitleVc;

	private static CollectionTabTitleVcUiBinder uiBinder = GWT.create(CollectionTabTitleVcUiBinder.class);

	interface CollectionTabTitleVcUiBinder extends UiBinder<Widget, CollectionTabTitleVc> {
	}

	/**
	 * Class constructor
	 */
	public CollectionTabTitleVc() {
		setWidget(uiBinder.createAndBindUi(this));
		wrapperFloPanel.getElement().setId("fpnlWrapperFloPanel");
		imageSimPanel.getElement().setId("spnlImageSimPanel");
		labelLbl.getElement().setId("lblLabelLbl");
	}

	/**
	 * @param enable decides to add or remove activeClass style
	 */
	public void setSelected(boolean enable) {
		if (selectedTabTitleVc != null && !selectedTabTitleVc.equals(this)) {
			selectedTabTitleVc.setSelected(false);
		}
		String activeCss;

		activeCss = "ctt-collectionMetaDataTabTitleActive";
		if (title.equalsIgnoreCase("share")){
			wrapperFloPanel.getElement().getStyle().setPaddingTop(9.0, Unit.PX);
			wrapperFloPanel.getElement().getStyle().setPaddingBottom(9.0, Unit.PX);
		}

		if (enable) {
			wrapperFloPanel.addStyleName(activeCss);
		} else {
			wrapperFloPanel.removeStyleName(activeCss);
		}
		selectedTabTitleVc = this;
	}

	public void setEnabled(boolean enabled) {
		if(enabled) {
			wrapperFloPanel.setStyleName("ctt-collectionMetaDataTabTitle");
		} else {
			wrapperFloPanel.setStyleName("ctt-collectionMetaDataTabTitleDisabled");
		}
	}

	/**
	 * @param label name of label
	 */
	public void setLabel(String label) {
		title = label;
		if (title !=null &&  (title.equalsIgnoreCase("share") || title.contains("Collaborator"))){
			wrapperFloPanel.getElement().getStyle().setPaddingTop(9.0, Unit.PX);
			wrapperFloPanel.getElement().getStyle().setPaddingBottom(9.0, Unit.PX);
		}
		labelLbl.setText(label);
		labelLbl.getElement().setAttribute("alt",label);
		labelLbl.getElement().setAttribute("title",label);
	}

	public FlowPanel getWrapperFloPanel() {
		return wrapperFloPanel;
	}

	/**
	 * @param style for image panel
	 */
	public void setImageStyle(String style) {
		imageSimPanel.setStyleName(style);
	}
}
