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
package org.ednovo.gooru.client.mvp.search;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * @author Search Team
 * 
 */
public interface SearchResultWrapperCBundle extends ClientBundle {
	
	static final SearchResultWrapperCBundle INSTANCE = GWT.create(SearchResultWrapperCBundle.class);

	/**
	 * SearchResultWrapperCss.
	 */
	public interface SearchResultWrapperCss extends CssResource {
		
		String searchPanel();

		String contentPanel();

		String statusLbl();

		String hiddenPanel();

		String share();
		
		String shareActive();
		
		String moreInfoActive();

		String moreInfo();
		
		String infoLblActive();

		String infoLbl();

		String disclosurePanel();
		
		String disclosureContentPanel();
		
/*		String disclosurePanelHeader();*/
		
		String disclosureMainHeader();
		
		String blueLink();
		
		String blueLinkPad();
		
		String moreMetaLbl();
		
		String tagText();
		
		String searchResultWrapper();
		
		String added();
		
		String disclosureHeader();
		
		// PPP Css //
		
		String collectionPPPPanel();
		String collectionPPPDisclosureHeader();
		
		String embed();
		
		String embedActive();
		
		String ratingWidgetPanel();
		
		String addLblActive();
		String tagsLblActive();
		String tabActive();
		
		String analyticsLblActive();
		String setFont();
	}

	@Source("SearchResultWrapper.css")
	SearchResultWrapperCss css();

}
