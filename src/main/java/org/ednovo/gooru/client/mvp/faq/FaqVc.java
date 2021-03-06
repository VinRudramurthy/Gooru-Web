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
package org.ednovo.gooru.client.mvp.faq;

import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class FaqVc extends Composite implements MessageProperties{
	
	@UiField Label organizeText,createCollectionText,reOrganizeText,
	editCollectionText,shareResourceLbl,shareResourceText,needHelpLbl,moreAnsweretext,helpText;
	
	@UiField Anchor supportLbl,contactUsText;

	private static faqUiBinder uiBinder = GWT.create(faqUiBinder.class);

	interface faqUiBinder extends UiBinder<Widget, FaqVc> {
	}

	/**
	 * Class constructor
	 */
	public FaqVc() {
		initWidget(uiBinder.createAndBindUi(this));
		organizeText.setText(GL1350+GL_SPL_QUESTION);
		createCollectionText.setText(GL1351+GL_SPL_FULLSTOP);
		reOrganizeText.setText(GL1352+GL_SPL_QUESTION);
		editCollectionText.setText(GL1353);
		shareResourceLbl.setText(GL1354+GL_SPL_QUESTION);
		shareResourceText.setText(GL1355);
		needHelpLbl.setText(GL1356+GL_SPL_QUESTION);
		supportLbl.setText(GL1357);
		supportLbl.setHref("http://support.goorulearning.org/forums");
		moreAnsweretext.setText(GL1358);
		contactUsText.setText(" "+GL1245);
		contactUsText.setHref("http://support.goorulearning.org/anonymous_requests/new");
		helpText.setText(" "+GL1359+GL_SPL_EXCLAMATION);
	}

}
