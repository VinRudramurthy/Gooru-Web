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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add;

import org.ednovo.gooru.client.ui.TinyMCE;
import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AddHintsView extends Composite implements HasMouseOutHandlers,HasMouseOverHandlers,MessageProperties{

	@UiField Label hintNumLbl;
	@UiField TinyMCE hintTextBox;
	@UiField Label hintsTextLblVal,errorMessageforHints;
	@UiField HTMLPanel deleteButtonContainer;
	@UiField AddResourceBundle addWebResourceStyle;
	public Label hintDelLbl=new Label();
	private String hintText=null;
	public AddHintsView(){
		initWidget(obj.createAndBindUi(this)); 
	}
	
	public AddHintsView(int widgetsCount){
		initWidget(obj.createAndBindUi(this)); 
		showHintsMessage(widgetsCount);
	}
	public AddHintsView(int widgetsCount,String hintText){
		initWidget(obj.createAndBindUi(this)); 
		this.hintText=hintText;
		showHintsMessage(widgetsCount);
	}
	public void showHintsMessage(int widgetsCount){
		if(widgetsCount==1){
			hintNumLbl.setText(""+widgetsCount);
			hintsTextLblVal.setText(GL0859);
		}
		else{
			hintNumLbl.setText(""+widgetsCount);
		}
		hintDelLbl.setStyleName(addWebResourceStyle.addResourceFormAnswerDelete());
		hintDelLbl.getElement().getStyle().setDisplay(Display.NONE);
		deleteButtonContainer.add(hintDelLbl);
	}
	@Override
	public void onLoad(){
		super.onLoad();
		Scheduler.get().scheduleDeferred(new ScheduledCommand(){
			@Override
			public void execute() {
				 setHintText();
			}
        });
	}
	 public void setHintText(){
		  if(hintText!=null){
			  hintTextBox.setText(hintText);
		  }
	  }
	public interface Binder extends UiBinder<Widget, AddHintsView> 
	{
		
	}
	public static Binder obj = GWT.create(Binder.class); 
	
	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, MouseOverEvent.getType());
	}
	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, MouseOutEvent.getType());
	}
}
