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
package org.ednovo.gooru.client.mvp.assessments.play.resource.body;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.HasLoadHandlers;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.HasInitializeHandlers;
import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.InitializeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;

public class WebResourceWidget extends Composite implements HasInitializeHandlers,HasLoadHandlers{
    private Frame resourcePreviewFrame =null;

	public WebResourceWidget(String webResourceURL) {
		resourcePreviewFrame = new Frame(webResourceURL);
		initWidget(resourcePreviewFrame);
		resourcePreviewFrame.setSize("100%", "550px");
		resourcePreviewFrame.getElement().setAttribute("frameborder", "0");
		resourcePreviewFrame.getElement().setAttribute("vspace", "0");
		resourcePreviewFrame.getElement().setAttribute("hspace", "0");
		resourcePreviewFrame.removeStyleName("gwt-Frame");
		resourcePreviewFrame.getElement().getStyle().setBackgroundColor("white");
		resourcePreviewFrame.getElement().setAttribute("id", "resourcePlayerContainer");
		int windowHeight=Window.getClientHeight();
		if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.RESOURCE_PLAY)){
			resourcePreviewFrame.setHeight((windowHeight-(116))+"px");
		}else{
			resourcePreviewFrame.setHeight((windowHeight-(193))+"px");
		}
	}

	public Frame getResourcePreviewFrame() {
		return resourcePreviewFrame;
	}

	public void setResourcePreviewFrame(Frame resourcePreviewFrame) {
		this.resourcePreviewFrame = resourcePreviewFrame;
	}

    public HandlerRegistration addInitializeHandler(InitializeHandler handler) {
     return addHandler(handler, InitializeEvent.getType());
    }
    @Override
    public void onLoad(){
    	super.onLoad();
    	 Scheduler.get().scheduleDeferred(new ScheduledCommand(){
 			@Override
 			public void execute() {
 				try{
 				 Document document = IFrameElement.as(getResourcePreviewFrame().getElement()).getContentDocument();
 	             BodyElement body = document.getBody();
 			 	 Element html=document.getDocumentElement();
 	             int bodyHeight=Math.max(body.getScrollHeight(), body.getOffsetHeight());
 	             bodyHeight=Math.max(bodyHeight, html.getOffsetHeight());
 	             bodyHeight=Math.max(bodyHeight, html.getClientHeight());
 	             bodyHeight=Math.max(bodyHeight, html.getScrollHeight());
	             int scrollHeight=body.getScrollHeight();
	             getResourcePreviewFrame().getElement().getStyle().clearPosition();
	             getResourcePreviewFrame().getElement().getStyle().setVisibility(Visibility.VISIBLE);
 				}catch(Exception e){
 					AppClientFactory.printSevereLogger("WebResourceWidget : onLoad : "+e.getMessage());
 				}
 			}
         });
    }

	@Override
	public HandlerRegistration addLoadHandler(LoadHandler handler) {
		  return addHandler(handler, LoadEvent.getType());
	}

}
