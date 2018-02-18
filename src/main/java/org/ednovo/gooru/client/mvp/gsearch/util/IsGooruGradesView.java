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
package org.ednovo.gooru.client.mvp.gsearch.util;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HTMLPanel;


/**
 * @author Search Team
 *
 */
public interface IsGooruGradesView extends IsViewWithHandlers<GooruGradesUiHandlers> {

	/**
	 * Update the grade style.
	 * @param filterName {@link String}
	 * @param addOrRemove {@link String}
	 */
	void updateFilterStyle(String filterName, String addOrRemove);

	void showGradesFilter();

	void highlightGradeLevel(String filterName);

	Label  getGradeHeader();

	public void setGradePanel(HTMLPanel panel);

	void setGrade(String grades);

	/**
	 * @function clearGradesStyles
	 *
	 * @created_date : 14-Jul-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	*/

	void clearGradesStyles();

	String getPageType();

	void setPageType(String pageType);


}
