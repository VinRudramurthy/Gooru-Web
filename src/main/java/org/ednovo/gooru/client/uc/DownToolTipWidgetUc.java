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

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class DownToolTipWidgetUc extends FocusPanel implements MouseOverHandler, MouseOutHandler, ClickHandler {

	private DownToolTipUc tooltipPopUpUc;

	private Widget toolTipWidget;
	private boolean isIe= false;
	private boolean isFireFox=false;
	//assigning the default value true so that it will display always standards text, if we need to display 21st centruy skills we need to set the isStandards value as false.
	private boolean isStandards=true;
	String newMsg;
	int Count;
	boolean isStandardsTitle = false;
	
	List<Map<String, String>> standards = null;
	private String title;
	
	/**
	 * Class constructor with no parameter
	 */
	public DownToolTipWidgetUc() {
		addDomHandler(this, MouseOutEvent.getType());
		addDomHandler(this, MouseOverEvent.getType());
		addDomHandler(this, ClickEvent.getType());
	}

	/**
	 * Class constructor with two parameter
	 * @param widget instance of the {@link Widget}
	 * @param html text for html widget
	 */
	public DownToolTipWidgetUc(Widget widget, String html) {
		
		this(widget, !StringUtil.isEmpty(html)? new HTML(html) : null);
		this.title=title;
	}

	/**
	 * Class constructor with two parameter
	 * @param widget instance of {@link Widget}
	 * @param toolTipWidget instance of {@link Widget}
	 */
	public DownToolTipWidgetUc(Widget widget, Widget toolTipWidget) {
		this();
		setWidget(widget);
		setToolTipWidget(toolTipWidget);
	}
	
	public String getTooltipPopUpUc(String Message) {
		newMsg =	Message;
		return newMsg;
	}
	public int getTooltipPopUpUcCount(int count) {
		Count =	count;
		return Count;
	}
	/**
	 * Class constructor with three parameter
	 * @param widget instance of {@link Widget}
	 * @param toolTipWidget instance of {@link Widget}
	 * @param standards
	 */
	public DownToolTipWidgetUc(Widget widget, Widget toolTipWidget, List<Map<String, String>> standards) {
		this();
		setWidget(widget);
		setToolTipWidget(toolTipWidget);
		this.standards = standards;
	}
	
	@UiChild(tagname = "widget")
	public void setWidget(Widget widget) {
		super.setWidget(widget);
	}

	/**
	 * Create a new instance for {@link DownToolTipUc}
	 * @param toolTipWidget instance of {@link Widget}
	 */
	@UiChild(tagname = "toolTipWidget")
	public void setToolTipWidget(Widget toolTipWidget) {
		this.toolTipWidget = toolTipWidget;
		if (toolTipWidget != null) {
			tooltipPopUpUc = new DownToolTipUc();
			tooltipPopUpUc.getElement().setAttribute("style", "z-index: 99999;");
			tooltipPopUpUc.setContent(toolTipWidget);
			isStandardsTitle = true;
		}else{
			isStandardsTitle = false;
		}
	}
	
	@Override
	public void onClick(ClickEvent event){
		StandardsPopupVc standardsPopupVc;
		if (standards!=null && standards.size()>1){
			if (tooltipPopUpUc != null) {
				tooltipPopUpUc.hide();
			}
			if(isStandardsTitle){
				 standardsPopupVc = new StandardsPopupVc(standards,isStandards());
			}else{
				 standardsPopupVc = new StandardsPopupVc(standards,false);
			}
			standardsPopupVc.center();
			standardsPopupVc.show();
		}
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		if (tooltipPopUpUc != null) {
			tooltipPopUpUc.hide();
		}
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		if (tooltipPopUpUc != null && !tooltipPopUpUc.isShowing()) {
			tooltipPopUpUc.show();
			isIe = isIEBrowser();
			isFireFox=isFirefoxBrowser();
			if(isIe){
				tooltipPopUpUc.setPopupPosition(getWidget().getAbsoluteLeft() + (getWidget().getOffsetWidth() / 2) - (tooltipPopUpUc.getOffsetWidth() / 2), getWidget().getAbsoluteTop()+6 + getWidget().getOffsetHeight());	
			}
			else if(isFireFox){
				tooltipPopUpUc.setPopupPosition(getWidget().getAbsoluteLeft()-85,getWidget().getAbsoluteTop()+10);
				tooltipPopUpUc.show();
				if(tooltipPopUpUc.getElement().getStyle().getTop().equalsIgnoreCase("0px")){
					tooltipPopUpUc.hide();
				}else{
					tooltipPopUpUc.show();
				}
				if(newMsg !=null && newMsg!=""){
					if(isFireFox && newMsg.contains("Team")){
						tooltipPopUpUc.setPopupPosition(getWidget().getAbsoluteLeft()-25,getWidget().getAbsoluteTop()+10);
					}
				}
				if(Count>=1){
					if(isFireFox && Count>=1){
						tooltipPopUpUc.setPopupPosition(getWidget().getAbsoluteLeft()-80,getWidget().getAbsoluteTop()+10);
					}
				}
			}
			else{
				tooltipPopUpUc.setPopupPosition(getWidget().getAbsoluteLeft() + (getWidget().getOffsetWidth() / 2) - (tooltipPopUpUc.getOffsetWidth() / 2), getWidget().getAbsoluteTop() + getWidget().getOffsetHeight());	
			}
		}
	}

	/**
	 * @return tollTipWidget instance of {@link Widget}
	 */
	public Widget getToolTipWidget() {
		return toolTipWidget;
	}
	
	@Override
	public void onUnload() {
		super.onUnload();
		if (tooltipPopUpUc!=null)
			tooltipPopUpUc.hide();
	}
	/**
	* Gets the name of the used browser.
	*/
	public static native String getBrowserName() /*-{
	    return navigator.userAgent.toLowerCase();
	}-*/;
	/**
	* Returns true if the current browser is IE (Internet Explorer).
	*/
	public static boolean isIEBrowser() {
	    return getBrowserName().toLowerCase().contains("msie");
	}
	/**
	* Returns true if the current browser is Firefox.
	*/
	public static boolean isFirefoxBrowser() {
	    return getBrowserName().toLowerCase().contains("firefox");
	}

	public boolean isStandards() {
		return isStandards;
	}

	public void setStandards(boolean isStandards) {
		this.isStandards = isStandards;
	}
	public String getTitle(){
		return title;
	}
}
