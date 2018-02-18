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
package org.ednovo.gooru.client.mvp.search;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class StandardSuggestionsVc extends Composite {

	private static StandardSuggestionsUiBinder uiBinder = GWT.create(StandardSuggestionsUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface StandardSuggestionsUiBinder extends UiBinder<Widget, StandardSuggestionsVc> {
	}

	@UiField Label seeMoreText;
	@UiField FlowPanel contentPanel;
	/**
	 * Class constructor
	 */
	public StandardSuggestionsVc() {
		initWidget(uiBinder.createAndBindUi(this));
		seeMoreText.setText(i18n.GL0739());
		seeMoreText.getElement().setId("lblSeeMoreText");
		seeMoreText.getElement().setAttribute("alt",i18n.GL0739());
		seeMoreText.getElement().setAttribute("title",i18n.GL0739());
		contentPanel.getElement().setId("fpnlContentPanel");
	}

}
