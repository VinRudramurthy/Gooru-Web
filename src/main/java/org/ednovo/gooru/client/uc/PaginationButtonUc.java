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

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;

/**
 * @author Search Team
 *
 */
public class PaginationButtonUc extends Anchor {

	private int pageNum;

	private String label;

	/**
	 * Class constructor with two parameter
	 * @param pageNum
	 * @param clickHandler
	 */
	public PaginationButtonUc(int pageNum, ClickHandler clickHandler) {
		this(pageNum, pageNum + "", clickHandler);
	}

	/**
	 * Class constructor with three parameter
	 * @param pageNum
	 * @param selected
	 * @param clickHandler
	 */
	public PaginationButtonUc(int pageNum, boolean selected, ClickHandler clickHandler) {
		this(pageNum, clickHandler);
		if (selected) {
			this.addStyleName("Uc-paginationUcSelected");
		}
		this.getElement().setId("page"+pageNum);
	}

	/**
	 * Class constructor with three parameter
	 * @param pageNum
	 * @param label
	 * @param clickHandler
	 */
	public PaginationButtonUc(int pageNum, String label, ClickHandler clickHandler) {
		super();
		this.pageNum = pageNum;
		setText(label);
		this.addClickHandler(clickHandler);
		this.setStyleName("Uc-paginationUc");
		this.getElement().setId("page"+label);
	}

	public int getPage() {
		return pageNum;
	}

	public String getLabel() {
		return label;
	}
}
