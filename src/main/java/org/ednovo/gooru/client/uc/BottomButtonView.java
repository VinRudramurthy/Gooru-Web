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


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class BottomButtonView extends Composite implements HasClickHandlers{

	@UiField InlineLabel buttonName;
	
	@UiField InlineHTML courseCount;
	
	private int count=0;
	
	private static BottomButtonViewUiBinder uiBinder = GWT.create(BottomButtonViewUiBinder.class);

	interface BottomButtonViewUiBinder extends UiBinder<Widget, BottomButtonView> {
	}
	
	public BottomButtonView(){
		initWidget(uiBinder.createAndBindUi(this));
		buttonName.getElement().setId("inlineLblButtonName");
		courseCount.getElement().setId("inlineLblCourseCount");
	}
	
	public void setButtonStyle(String styleName){
		buttonName.setStyleName(styleName);
	}
	
	public void setButtonName(String buttonName){
		this.buttonName.setText(buttonName);
		this.buttonName.getElement().setAttribute("alt", buttonName);
		this.buttonName.getElement().setAttribute("title", buttonName);
	}
	
	public void setCountStyle(String styleName){
		courseCount.setStyleName(styleName);
	}
	public void addCountStyle(String styleName){
		courseCount.addStyleName(styleName);
	}
	
	public InlineHTML getCourseCountText(){
		return courseCount;
	}
	
	public void setCount(int count){
		this.count=count;
		courseCount.setText("("+count+")");
		courseCount.getElement().setAttribute("alt", count+"");
		courseCount.getElement().setAttribute("title", count+"");
	}
	
	public int getCount(){
		return count;
	}
	
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}
	
}
