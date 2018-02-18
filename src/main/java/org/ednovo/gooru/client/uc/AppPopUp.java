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

import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class AppPopUp extends PopupPanel {

	private FlowPanel mainPanel;
	private FlowPanel innerPanel;
	private FlowPanel headerPanel;
	private FlowPanel content;
	private Label labletitle;

	private HTMLEventPanel closeBtn;


	/**
	 * Class constructor
	 */
	public AppPopUp() {
		super(false);

		mainPanel=new FlowPanel();
		innerPanel=new FlowPanel();
		mainPanel.addStyleName("PopupMainVVSmall");
		innerPanel.addStyleName("popupInnerGrey");
		headerPanel = new FlowPanel();
		headerPanel.addStyleName("popupgreyHeader");
		FlowPanel row=new FlowPanel();
		row.addStyleName("row");
		content = new FlowPanel();
		labletitle = new Label();
		labletitle.addStyleName("col-md-11 col-xs-11");
		row.add(labletitle);
		FlowPanel clearfix=new FlowPanel();
		clearfix.addStyleName("clearfix");
		row.add(clearfix);
		closeBtn = new HTMLEventPanel("");

		closeBtn.addStyleName("closeButton");
		closeBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
				Window.enableScrolling(true);
			}
		});
		row.add(closeBtn);
		headerPanel.add(row);
		innerPanel.add(headerPanel);
		mainPanel.add(innerPanel);
		mainPanel.add(clearfix);
		content.add(mainPanel);

		this.setWidget(content);
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
	public AppPopUp(String type){
		super(false);
			content = new FlowPanel();
			this.setWidget(content);
			setGlassEnabled(true);
	}

	/**
	 * Class constructor with one parameter
	 * @param type
	 */
	public AppPopUp(String type,boolean isAutoHide){
		super(isAutoHide);
		content = new FlowPanel();
		this.setWidget(content);
		setGlassEnabled(true);
	}
	/**
	 * Set appPopUp content
	 * @param title for appPopUp
	 * @param widget instance of {@link Widget}
	 */
	public void setContent(String title, Widget widget) {
		innerPanel.add(widget);
		setViewTitle(title);
	}

	public FlowPanel getHeaderPanel() {
		return headerPanel;
	}

	public FlowPanel getMainPanel(){
		return mainPanel;
	}
	public FlowPanel getInnerPanel(){
		return innerPanel;
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

	public HTMLEventPanel getCloseBtn() {
		return closeBtn;
	}
	public void setCloseBtn(HTMLEventPanel closeBtn) {
		this.closeBtn = closeBtn;
	}

}
