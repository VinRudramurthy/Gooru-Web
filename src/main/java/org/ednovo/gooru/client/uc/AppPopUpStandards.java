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

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class AppPopUpStandards extends PopupPanel {

	private FlowPanel mainPanel;
	private FlowPanel innerPanel;
	private FlowPanel headerPanel;

	private FlowPanel row;
	private FlowPanel content;
	private Label labletitle;
	private FlowPanel closeBtn;
	private Anchor close;

	/**
	 * Class constructor
	 */
	public AppPopUpStandards() {
		super(false);
		mainPanel=new FlowPanel();
		innerPanel=new FlowPanel();
		row=new FlowPanel();
		close=new Anchor();
		mainPanel.setStyleName("PopupMainExtraLarge");
		innerPanel.setStyleName("popupInnerGrey");
		row.setStyleName("row");
		headerPanel = new FlowPanel();
		content = new FlowPanel();
		closeBtn = new FlowPanel();
		headerPanel.setStyleName("popupgreyHeader");
		labletitle = new Label();
		labletitle.setStyleName("col-md-8 col-xs-8");
		closeBtn.addStyleName("col-md-4 col-xs-4");
		closeBtn.addStyleName("closeContainer");
		close.addStyleName("closeButton");
		closeBtn.add(close);

		row.add(labletitle);
		row.add(closeBtn);
		HTMLPanel htmlPanel=new HTMLPanel("");
		htmlPanel.addStyleName("clearfix");
		row.add(htmlPanel);
		headerPanel.add(row);
		innerPanel.add(headerPanel);
		mainPanel.add(innerPanel);
		innerPanel.add(content);

		this.setWidget(mainPanel);
		setGlassEnabled(true);
		setAutoHideOnHistoryEventsEnabled(true);
		getElement().getStyle().setZIndex(200);
	}
	public void setGlassZindex(int index){
		getGlassElement().getStyle().setZIndex(index);
	}
	public void setPopupZindex(int index){
		getElement().getStyle().setZIndex(index);
	}
	/**
	 * Class constructor with one parameter
	 * @param type
	 */
	public AppPopUpStandards(String type){
		super(false);
			this.setStyleName("app-shelfItemPopUp");
			content = new FlowPanel();
			this.setWidget(content);
			setGlassEnabled(true);
	}

	/**
	 * Class constructor with one parameter
	 * @param type
	 */
	public AppPopUpStandards(String type,boolean isAutoHide){
		super(isAutoHide);
		this.setStyleName("app-shelfItemPopUp");
		content = new FlowPanel();
		this.setWidget(content);
		setGlassEnabled(true);
	}
	/**
	 * Class constructor with two parameter
	 * @param title of  AppPopUp
	 * @param widget instance of {@link Widget}
	 */
	/*public AppPopUp(String title, Widget widget) {
		this();
		setContent(title, widget);
	}*/

	/**
	 * Set appPopUp content
	 * @param title for appPopUp
	 * @param widget instance of {@link Widget}
	 */
	public void setContent(String title, Widget widget) {
		content.add(widget);
		setViewTitle(title);
	}

	public FlowPanel getHeaderPanel() {
		return headerPanel;
	}

	public void setHeaderPanel(FlowPanel headerPanel) {
		this.headerPanel = headerPanel;
	}

	public void setContent(Widget content) {
		this.content.add(content);
	}

	public void setViewTitle(String title) {
		labletitle.setText(title);
		labletitle.getElement().setAttribute("alt",title);
		labletitle.getElement().setAttribute("title",title);
	}
	public Anchor getCloseBtn() {
		return close;
	}
	public void setCloseBtn(Anchor close) {
		this.close = close;
	}


}
