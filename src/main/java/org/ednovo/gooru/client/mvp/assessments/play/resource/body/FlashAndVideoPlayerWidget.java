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
import org.ednovo.gooru.client.uc.BrowserAgent;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class FlashAndVideoPlayerWidget extends Composite {



	public FlashAndVideoPlayerWidget(String resourceUrl, String videoStartTime, String videoEndTime) {
		int startTimeInSeconds = 0;
		int endTimeInSeconds = 0;
		String startTimeEndTime="";
		if(videoStartTime==null && videoEndTime==null)
		{
			startTimeEndTime="start=0&end=0;";
		}
		if(videoStartTime!=null){
			startTimeInSeconds=getStartOrEndTime(videoStartTime);
			startTimeEndTime="start=" + startTimeInSeconds;
		}
		if(videoEndTime!=null){
			endTimeInSeconds = getStartOrEndTime(videoEndTime);
			startTimeEndTime=startTimeEndTime+"&end="+endTimeInSeconds+";";
		}
		int windowHeight=Window.getClientHeight();
		if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.RESOURCE_PLAY)){
			windowHeight=windowHeight-116;
		}else{
			windowHeight=windowHeight-193;
		}
		String tabView=AppClientFactory.getPlaceManager().getRequestParameter("tab", null);
		int autoPlay=tabView!=null&&tabView.equalsIgnoreCase("narration")?0:1;
		String embeddableHtmlString = null;
		
		PlaceRequest collectionRequest = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
		String chkViewParam = collectionRequest.getParameter("view", null);	
		if(chkViewParam!= null && chkViewParam.equalsIgnoreCase("fullScreen"))
		{
			windowHeight=windowHeight+71;
		}
		
		
		String sourceUrl = getProtocal()+"//www.youtube.com/v/"+ resourceUrl+"?" +startTimeEndTime +"rel=0&amp;enablejsapi=1&amp;version=3&amp;autoplay=0&amp;start=1";
		String sourceUrl1 = getProtocal()+"//www.youtube.com/embed/"+ resourceUrl+"?" +startTimeEndTime +"rel=0&amp;enablejsapi=1&amp;version=3&amp;autoplay=0&amp;start=1";
		
		if (BrowserAgent.isDevice()){
			embeddableHtmlString = "<iframe id=\"playerid\" src=\""+sourceUrl1+"\" frameborder=\"0\" allowfullscreen=\"\" style=\"width:100%;height:"+windowHeight+"px\"></iframe>";
		}else{
			embeddableHtmlString = "<embed id=\"playerid\" type=\"application/x-shockwave-flash\" src=\""+sourceUrl+ "\""
					+ " width=\"100%\" height=\""+windowHeight+"px\" quality=\"high\" allowfullscreen=\"true\" allowscriptaccess=\"always\" autoplay=\"0\" wmode=\"transparent\">";
		}

		HTMLPanel resourcePreviewPanel = new HTMLPanel(embeddableHtmlString);
		resourcePreviewPanel.setStyleName("resourcePreviewWebResourceContainer");
		resourcePreviewPanel.setSize("100%", windowHeight+"px");
		resourcePreviewPanel.getElement().getStyle().setPosition(Position.RELATIVE);		
		initWidget(resourcePreviewPanel);

	}

	private int getStartOrEndTime(String resStart) {
		String startParam = resStart;
		int start=0;
		String[] starTimeSplit = startParam.split(":");
		if (starTimeSplit.length == 2) {
			int minute = Integer.parseInt(starTimeSplit[0]);
			int second = Integer.parseInt(starTimeSplit[1]);
			start = second + (60 * minute);

		} else if (starTimeSplit.length == 3) {

			int hour = Integer.parseInt(starTimeSplit[0]);
			int minute = Integer.parseInt(starTimeSplit[1]);
			int second = Integer.parseInt(starTimeSplit[2]);

			start = second + (60 * minute) + (3600 * hour);

		}

		return start;
	}
	public static String getProtocal(){
		return Window.Location.getProtocol().equalsIgnoreCase("http:")?"http:":"https:";
	}
	
	public void setResourceWidgetContainerHeight(HTMLPanel resourcePreviewPanel){
		int windowHeight=Window.getClientHeight();
		if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.RESOURCE_PLAY)){
			resourcePreviewPanel.setHeight((windowHeight-116)+"px");
		}else{
			resourcePreviewPanel.setHeight((windowHeight-190)+"px");
		}
		resourcePreviewPanel.setWidth("100%");
	}
}
