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
package org.ednovo.gooru.client.mvp.classpages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * 
 * @fileName : ClasspageListPopupViewCBundle.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface ClasspageListPopupViewCBundle extends ClientBundle {

	static final ClasspageListPopupViewCBundle INSTANCE = GWT
			.create(ClasspageListPopupViewCBundle.class);

	public interface ClasspageListCss extends CssResource {
		/* ClasspageList VC CSS*/
		String classpageListContainer();
		
		String htmlPanelNoClasspageContainer();
		
		String newClasspageLink();
		
		String classpageListTitle();
		
		String classpageBottomLine();
		
		String loadingText();
		
		String htmlPanelClasspageList();
		
		String plusImg();
		
		String plus();
		
		String htmlPanelContentContainer();
		
		String gooruGuide();
		
		String noClasspageYet();
		
		String classpageTitleStudyHeader();
		
		String classpageTitleHeader();
		
		String scrollPanelClasspageList();
		
		String classpageTitleHeaderActive();
		
		String classpageLoadingOnPagination();
		
		String inputContainer();

		String tooltipContentTitle();

		String classCodeTxtBox();
	}

	@Source("ClasspageListPopup.css")
	ClasspageListCss css();

}
