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
package org.ednovo.gooru.client.util;

import org.ednovo.gooru.client.uc.BrowserAgent;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author SearchTeam
 */
public class ScrollPopupShareUtil {
	static boolean device = BrowserAgent.isDevice();
	static Widget widgetContainer;
	public static void ScrollPopupUtilWidget(Widget widget){
		widgetContainer=widget;
		setAddTagesPopupOnResize();
		Window.addResizeHandler(new ResizeEvent());
	}
	/**
	 * This inner class is used to handle the window resizes
	 * @author Gooru
	 */
	public static class ResizeEvent implements ResizeHandler{
		@Override
		public void onResize(com.google.gwt.event.logical.shared.ResizeEvent event) {
			setAddTagesPopupOnResize();		
		}
	}
	/**
	 * This method is used to handle the window resize for add tags popup.
	 */
	 static void setAddTagesPopupOnResize(){
		if(device){
			if((Window.getClientHeight()-150)<=564){
				widgetContainer.getElement().getStyle().setHeight(Window.getClientHeight()-150, Unit.PX);
				widgetContainer.getElement().getStyle().setOverflowY(Overflow.AUTO);
				widgetContainer.getElement().getStyle().setOverflowX(Overflow.AUTO);
			}else{
				widgetContainer.getElement().getStyle().setHeight(500, Unit.PX);
				widgetContainer.getElement().getStyle().setOverflowY(Overflow.AUTO);
			}
		}
	}
}
