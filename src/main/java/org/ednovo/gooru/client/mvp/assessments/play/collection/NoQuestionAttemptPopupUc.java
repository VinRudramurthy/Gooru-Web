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
package org.ednovo.gooru.client.mvp.assessments.play.collection;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 *
 * @fileName : NoQuestionAttemptPopupUc.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 07-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class NoQuestionAttemptPopupUc extends PopupPanel{
	
	@UiField Button btnSubmit;
	
	private static NoQuestionAttemptPopupUcUiBinder uiBinder = GWT
			.create(NoQuestionAttemptPopupUcUiBinder.class);

	interface NoQuestionAttemptPopupUcUiBinder extends
			UiBinder<Widget, NoQuestionAttemptPopupUc> {
	}
	public NoQuestionAttemptPopupUc(){
		super(false);
		setWidget(uiBinder.createAndBindUi(this));
        this.setGlassStyleName("loginPopupGlassStyle");
        this.setGlassEnabled(true);
       	this.getElement().getStyle().setZIndex(999999);
       	this.center();
	}
	
	@UiHandler("btnSubmit")
	public void onSubmit(ClickEvent event){
		hide();
	}
}