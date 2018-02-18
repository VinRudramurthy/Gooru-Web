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
package org.ednovo.gooru.client.uc.tooltip;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.SimpleAsyncCallback;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ToolTip extends PopupPanel implements HasMouseOutHandlers{
	
	@UiField
	HTMLPanel panelCode;
	
	@UiField
	HTMLPanel lblTitle,tootltipContent;
	
	@UiField Anchor lblLink;
	
	
	public interface ToolTipUiBinder extends UiBinder<Widget, ToolTip>{
	}
	
	public static ToolTipUiBinder toolTipUiBinder=GWT.create(ToolTipUiBinder.class);{
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class); 
	
	public ToolTip(){
		setWidget(toolTipUiBinder.createAndBindUi(this));
	//	lblTitle.setText(GL0450);
		lblTitle.setTitle(i18n.GL0450()+" ");
		lblTitle.getElement().setId("pnlLblTitle");
		lblTitle.getElement().setAttribute("alt",i18n.GL0450()+" ");
		lblTitle.getElement().setAttribute("title", i18n.GL0450()+" ");
		lblLink.setText(i18n.GL0451());
		lblLink.getElement().setId("lnkLblLink");
		lblLink.getElement().setAttribute("alt", i18n.GL0451());
		lblLink.getElement().setAttribute("title", i18n.GL0451());

		lblLink.setTarget("_blank");
		panelCode.getElement().setId("pnlPanelCode");
		panelCode.getElement().getStyle().setWidth(150, Unit.PX);
		this.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				hide();
			}
		});
		AppClientFactory.getInjector().getHomeService().mosLink(new SimpleAsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				lblLink.setHref(result);
			}
		});
	}
	
	public ToolTip(String description){
		
		setWidget(toolTipUiBinder.createAndBindUi(this));
		lblTitle.getElement().setInnerHTML(description);
		lblLink.setText(i18n.GL0451());
		lblLink.getElement().setId("lnkLblLink");
		lblLink.getElement().setAttribute("alt", i18n.GL0451());
		lblLink.getElement().setAttribute("title", i18n.GL0451());
		lblLink.setTarget("_blank");
		panelCode.getElement().setId("pnlPanelCode");
		lblTitle.getElement().setId("pnlLblTitle");
		lblTitle.getElement().setAttribute("alt", description);
		lblTitle.getElement().setAttribute("title", description);
		panelCode.getElement().getStyle().setWidth(150, Unit.PX);
		
		if(AppClientFactory.getPlaceManager().getRequestParameter("view", null)!=null && !AppClientFactory.getPlaceManager().getRequestParameter("view", null).equals("") && AppClientFactory.getPlaceManager().getRequestParameter("view", null).equals("end")){
			tootltipContent.getElement().getStyle().setWidth(155, Unit.PX);
		}

		this.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				hide();
			}
		});
		AppClientFactory.getInjector().getHomeService().mosLink(new SimpleAsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				lblLink.setHref(result);
			}
		});
	}
	public ToolTip(String description,String type){
		
		setWidget(toolTipUiBinder.createAndBindUi(this));
		lblTitle.getElement().setInnerHTML(description);
		panelCode.getElement().setId("pnlPanelCode");
		lblTitle.getElement().setId("pnlLblTitle");
		lblTitle.getElement().setAttribute("alt", description);
		lblTitle.getElement().setAttribute("title", description);
		panelCode.getElement().getStyle().setWidth(150, Unit.PX);
		

		this.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				hide();
			}
		});
		AppClientFactory.getInjector().getHomeService().mosLink(new SimpleAsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				lblLink.setHref(result);
			}
		});
	}
	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}
	
	public Anchor getLblLink(){
		return lblLink;
	}
	public HTMLPanel getTootltipContent()
	{
		return tootltipContent;
	}
}
