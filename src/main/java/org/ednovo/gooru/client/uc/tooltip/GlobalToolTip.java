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

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class GlobalToolTip extends Composite {
	
	@UiField
	public
	HTMLEventPanel confirmationPanel;
	
	@UiField
	Label desLbl;
	
	@UiField
	HTMLPanel panelArrow;
	
	
	public interface GlobalToolTipUiBinder extends UiBinder<Widget, GlobalToolTip>{
	}
	
	public static GlobalToolTipUiBinder toolTipGlobalUiBinder=GWT.create(GlobalToolTipUiBinder.class);{
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class); 
	
	private static final String TOP="top";
	private static final String RIGHT="right";
	private static final String BOTTOM="bottom";
	
	public GlobalToolTip(){
		initWidget(toolTipGlobalUiBinder.createAndBindUi(this));
		desLbl.setText(i18n.GL1060());
		confirmationPanel.getElement().setId("epnlConfirmationPanel");
		desLbl.getElement().setId("lblDesLbl");
		desLbl.getElement().setAttribute("alt", i18n.GL1060());
		desLbl.getElement().setAttribute("title", i18n.GL1060());
		panelArrow.getElement().setId("pnlPanelArrow");
		setPanelPosition();
	}
	
	
    public GlobalToolTip(String description){
		initWidget(toolTipGlobalUiBinder.createAndBindUi(this));
		desLbl.setText(description);
		confirmationPanel.getElement().setId("epnlConfirmationPanel");
		desLbl.getElement().setId("lblDesLbl");
		desLbl.getElement().setAttribute("alt", description);
		desLbl.getElement().setAttribute("title", description);
		panelArrow.getElement().setId("pnlPanelArrow");
		setPanelPosition();
	}
    public GlobalToolTip(String description, boolean isSharePopup){
		initWidget(toolTipGlobalUiBinder.createAndBindUi(this));
		desLbl.setText(description);
		confirmationPanel.getElement().setId("epnlConfirmationPanel");
		desLbl.getElement().setId("lblDesLbl");
		desLbl.getElement().setAttribute("alt", description);
		desLbl.getElement().setAttribute("title", description);
		setArrowLeft();
	}
    public GlobalToolTip(String description, String value){
		initWidget(toolTipGlobalUiBinder.createAndBindUi(this));
		desLbl.setText(description);
		confirmationPanel.getElement().setId("epnlConfirmationPanel");
		desLbl.getElement().setId("lblDesLbl");
		desLbl.getElement().setAttribute("alt", description);
		desLbl.getElement().setAttribute("title", description);
		if(value.equals(RIGHT) || value.equals(TOP) || value.equals(BOTTOM) ){
			arrowVisibility(value);
			setArrowRight(value);
		}else{
			setArrowDirections();
		}
	}
    
    
    private void arrowVisibility(String value) {
    	panelArrow.getElement().getStyle().setPosition(Position.ABSOLUTE);
    	panelArrow.getElement().getStyle().setDisplay(Display.BLOCK);
	}


	public void setPanelPosition(){
    	panelArrow.getElement().getStyle().setPosition(Position.ABSOLUTE);
    	/*if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)){
			panelArrow.getElement().getStyle().setPosition(Position.ABSOLUTE);
		}else{
			panelArrow.getElement().getStyle().clearPosition();
		}*/
    	if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.SHELF)){
    		panelArrow.getElement().getStyle().setDisplay(Display.NONE);
    	}
    	if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.EDIT_CLASSPAGE)){
    		confirmationPanel.getElement().getStyle().setLeft(-136, Unit.PX);
			panelArrow.getElement().getStyle().setLeft(141, Unit.PX);
		}else if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY) || AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.ASSESSMENT_PLAY)){
			if(AppClientFactory.getPlaceManager().getRequestParameter("page")!=null && AppClientFactory.getPlaceManager().getRequestParameter("page").equals("teach")){
				confirmationPanel.getElement().getStyle().setWidth(131, Unit.PX);
				desLbl.getElement().getStyle().setTextAlign(TextAlign.CENTER);
			}
		}else{
			confirmationPanel.getElement().getStyle().clearLeft();
			panelArrow.getElement().getStyle().clearLeft();
		}
    }
    public void setArrowLeft(){
     	panelArrow.getElement().getStyle().setPosition(Position.ABSOLUTE);
    //	panelArrow.getElement().getStyle().setLeft(165, Unit.PX);
     	
    }
    public void setArrowDirections(){
     	confirmationPanel.getElement().setAttribute("style", "width: 149px;");
     	panelArrow.getElement().setAttribute("style", "left: -158px;top: 22px;-webkit-transform: rotate(265deg);position:absolute;");
    }
    
    public void setArrowRight(String direction){
    	if(direction.equals(TOP)){
    		confirmationPanel.getElement().setAttribute("style", "width: 110px;");
         	panelArrow.getElement().setAttribute("style", "left: 104px;top: 17px;-webkit-transform: rotate(90deg);position:absolute;");
    	}else if(direction.equals(BOTTOM)){
    		confirmationPanel.getElement().setAttribute("style", "width: 124px;");
         	panelArrow.getElement().setAttribute("style", "left: 118px;top: 18px;-webkit-transform: rotate(90deg);position:absolute;");
    	}else if(direction.equals(RIGHT)){
    		confirmationPanel.getElement().setAttribute("style", "width: 165px;");
         	panelArrow.getElement().setAttribute("style", "left: 159px;top: 26px;-webkit-transform: rotate(90deg);position:absolute;");
    	}
     
    }
    
    public HTMLPanel getPanelArrow(){
    	return panelArrow;
    }
}
