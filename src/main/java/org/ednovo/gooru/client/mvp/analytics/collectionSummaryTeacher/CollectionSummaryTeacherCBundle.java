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
package org.ednovo.gooru.client.mvp.analytics.collectionSummaryTeacher;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;

public interface CollectionSummaryTeacherCBundle extends ClientBundle{
	static final CollectionSummaryTeacherCBundle INSTANCE = GWT.create(CollectionSummaryTeacherCBundle.class);
	public interface CollectionSummaryCss extends CssResource{
		    String alignCenterAndBackground();
		    String setMarginAuto();
	        
	        String category_new_type_audio();
	        String category_new_type_image();
	        String category_new_type_other();
	        String category_new_type_interactive();
	        String category_new_type_text();
	        String category_new_type_video();
	        String category_new_type_webpage();
	        
	        String setProgressBar();
	        String setIncorrectProgressBar();
	        String viewResponseTextOpended();
	        
	        //Table css
	        String tableMain();
	        String tableHeader();
	        String tableRowEven();
	        String tableRowOdd();
	        String assignment_quesiton_ans_bar();
	        String wrongSelectStyle();
	        String barGraphCharacter();
	        String tickMarkImgCss();
	        String tickMarkImg();
	        
	        String displayMessageTextForScoredQuestions();
	        String displayMessageTextForOEQuestions();
	        String setOETextPopupCenter();
			String alignLeft();
	        
	}
	@NotStrict
	@Source("collectionSummaryTeacher.css")
	CollectionSummaryCss css();
}
