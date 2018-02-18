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


import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class StandardSgItemVc extends Composite {

	private static StandardSgItemVcUiBinder uiBinder = GWT.create(StandardSgItemVcUiBinder.class);

	@UiField
	HTML standardsTitle;

	@UiField
	HTML standardsDesc;

	interface StandardSgItemVcUiBinder extends UiBinder<Widget, StandardSgItemVc> {
	}

	/**
	 * Class constructor
	 * @param title of the standard
	 * @param desc standard description
	 */
	public StandardSgItemVc(String title, String desc) {
		initWidget(uiBinder.createAndBindUi(this));
		if(!StringUtil.isEmpty(title) && !StringUtil.isEmpty(desc)){
			standardsTitle.setHTML(title);
			standardsTitle.getElement().setId("htmlStandardsTitle");
			standardsTitle.getElement().setAttribute("alt", title);
			standardsTitle.getElement().setAttribute("title", title);
			standardsDesc.getElement().setId("htmlStandardsDesc");
			// As per story : DO-1446
			// standardsDesc.setHTML(StringUtil.truncateText(desc,60));
			standardsDesc.setVisible(false);
		}
	}

}
