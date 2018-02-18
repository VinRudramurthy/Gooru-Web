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

import org.ednovo.gooru.application.shared.model.content.StandardFo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CollectionStandardsView extends Composite implements HasClickHandlers{
	
	
	@UiField FlowPanel standardContainer;
	
	ToolTipPopUp toolTip ;
	
	private static CollectionStandardsViewUiBinder uiBinder = GWT.create(CollectionStandardsViewUiBinder.class);

	interface CollectionStandardsViewUiBinder extends UiBinder<Widget, CollectionStandardsView> {
	}
	
	public CollectionStandardsView(){
		initWidget(uiBinder.createAndBindUi(this));
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		standardContainer.getElement().setId("fpnlStandardContainer");
	}
	
	@UiConstructor
	public CollectionStandardsView(List<StandardFo> standardsList){
		initWidget(uiBinder.createAndBindUi(this));
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		standardContainer.getElement().setId("fpnlStandardContainer");
		renderStandards(standardsList);
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}
	
	public void renderStandards(List<StandardFo> standardsList){
		for(int i=0;i<standardsList.size();i++){
			Label standardText=new Label(standardsList.get(i).getCode());
			standardText.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().standardText());
			standardContainer.add(standardText);
			standardText.addMouseOverHandler(new MouseOverShowStandardToolTip(standardsList.get(i).getDescription()));
			standardText.addMouseOutHandler(new MouseOutHideToolTip());
		}
	}
	

	/**
	 * 
	 * Inner class.
	 *
	 */
	public class MouseOverShowStandardToolTip implements MouseOverHandler
	{
		String description=null;
		
		/**
		 * Class constructor.
		 * @param description {@link String}
		 */
		public MouseOverShowStandardToolTip(String description) {
			this.description = description;
		}
		
		/**
		 * On mouse over tooltip is shown.
		 * @param event {@link MouseOverEvent}
		 */
		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTip = new ToolTipPopUp(description, (event.getRelativeElement().getAbsoluteLeft()-45),(event.getRelativeElement().getAbsoluteTop()-140- ((description.length() / 30) * 12)));
			toolTip.popupArrow.removeFromParent();
			toolTip.removeStyleName("gwt-PopupPanel");
			toolTip.getElement().setAttribute("style", "z-index:99999 !important");
			toolTip.downPopupArrow.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().setPopupStandardArrow());
			toolTip.getElement().getStyle().setVisibility(Visibility.HIDDEN);
			toolTip.show();
			int tooltipHeight=toolTip.getOffsetHeight();
			toolTip.setPopupPosition((event.getRelativeElement().getAbsoluteLeft()-45), event.getRelativeElement().getAbsoluteTop()-tooltipHeight);
			toolTip.getElement().getStyle().clearVisibility();
		}
	}
	
	/**
	 * 
	 * Inner class.
	 *
	 */
	public class MouseOutHideToolTip implements MouseOutHandler
	{
		/**
		 * On mouse out hides the tool tip.
		 * @return event {@link MouseOutEvent}
		 */
		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTip.hide();
			 
		}
		
	}
	
}
