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
package org.ednovo.gooru.client.mvp.search;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class MoreInfoFieldVc extends Composite {

	@UiField
	public FlowPanel contentFloPanel;

	@UiField
	public SimplePanel imageIconSimPanel,clearSimplePanel;

	@UiField
	HTML toolTipHtml;
	
	
	protected static final String UNDEFINED = " -- ";

	private static MoreInfoFieldVcUiBinder uiBinder = GWT.create(MoreInfoFieldVcUiBinder.class);

	interface MoreInfoFieldVcUiBinder extends UiBinder<Widget, MoreInfoFieldVc> {
	}

	/**
	 * Class constructor
	 */
	public MoreInfoFieldVc() {
		initWidget(uiBinder.createAndBindUi(this));
		SearchMoreInfoVcCBundle.INSTANCE.css().ensureInjected();
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RESOURCE_SEARCH)){
			clearSimplePanel.removeStyleName(SearchMoreInfoVcCBundle.INSTANCE.css().clear());
			imageIconSimPanel.setVisible(false);
			contentFloPanel.getElement().getStyle().setFloat(Float.RIGHT);
			contentFloPanel.getElement().getStyle().setPosition(Position.RELATIVE);
			contentFloPanel.getElement().getStyle().setMarginRight(22, Unit.PX);
//			contentFloPanel.getElement().getStyle().setTop(70, Unit.PX);
		}
		imageIconSimPanel.getElement().setId("spnlImageIconSimPanel");
		toolTipHtml.getElement().setId("htmlToolTipHtml");
		contentFloPanel.getElement().setId("fpnlContentFloPanel");
		clearSimplePanel.getElement().setId("spnlClearSimplePanel");
	}

	/**
	 * @param htmlString to be set for html
	 */
	public void setHtmlTxt(String htmlString) {
		setHtmlTxt(htmlString, null);
	}
	
	/**
	 * Add widget to FlowPanel
	 * @param widget which is to be added to FlowPanel
	 * 
	 */
	public void addWidget(Widget widget) {
		contentFloPanel.add(widget);
	}

	/**
	 * Set text to html content
	 * @param htmlString to be set on html widget
	 * @param styleName for the html widget
	 */
	public void setHtmlTxt(String htmlString, String styleName) {
		if(htmlString == null || htmlString.length() == 0 || htmlString.equals(UNDEFINED)) {
			this.setVisible(false);
			return;
		}
		HTML html = new HTML();
		html.setHTML(htmlString);
		if (styleName != null) {
			html.setText(styleName);
		}
		contentFloPanel.add(html);
	}

	/**
	 * Set style name for image
	 * @param style name of css.
	 */
	public void setImageStyle(String style) {
		imageIconSimPanel.setStyleName(style);
	}

	/**
	 * Assign test for tool tip
	 * @param html test to be set for toolTipHtml
	 */
	public void setToolTip(String html) {
		toolTipHtml.setHTML(html);
		toolTipHtml.getElement().setAttribute("alt",html);
		toolTipHtml.getElement().setAttribute("title",html);
	}

}
