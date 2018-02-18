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
package org.ednovo.gooru.client.mvp.folder.toc.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @description : This widget is used for dispaying the tooltip on the resource mouse over in the folder TOC page
 *
 * @version :1.3
 *
 * @date: Feb 16 2015

 * @Author Gooru Team
 * 
 * Reviewer Gooru Team
 *
 */
public class ResourceTooltip extends PopupPanel {

	private static ResourceTooltipUiBinder uiBinder = GWT
			.create(ResourceTooltipUiBinder.class);

	interface ResourceTooltipUiBinder extends UiBinder<Widget, ResourceTooltip> {
	}
	@UiField HTMLPanel pnlToolTipContainer;
	@UiField Label lblResourceDesc;
	/**
	 * Default constructor
	 */
	public ResourceTooltip() {
		setWidget(uiBinder.createAndBindUi(this));
		this.setGlassStyleName("resourcetooltip");
		this.setStyleName("");
	}
	/**
	 * This method will set the resource description.
	 * @param description
	 */
	public void setResourceDesc(String description) {
		lblResourceDesc.setText(description);
	}
}
