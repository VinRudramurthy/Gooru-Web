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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.ui.TinyMCE;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UpdateQuestionHints extends Composite implements HasMouseOutHandlers,HasMouseOverHandlers{
	 	
      @UiField Label hintsChoice;
      
      @UiField Label addHintsLabel,hintDelLbl;
      
      @UiField TinyMCE hintsTextBox;
      
      private String hintText=null;
      
	  public  interface UpdateQuestionHintsUiBinder extends UiBinder<Widget,UpdateQuestionHints>{
		  
	  }
	  public static UpdateQuestionHintsUiBinder uiBinder=GWT.create(UpdateQuestionHintsUiBinder.class);
	  
	  private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	  public UpdateQuestionHints(){
		  initWidget(uiBinder.createAndBindUi(this));
		  addHintsLabel.getElement().setId("lblAddHintsLabel");
		  hintsTextBox.getElement().setId("tinyMCEHintsTextBox");
		  hintDelLbl.getElement().setId("lblHintDelLbl");
		  hintsChoice.getElement().setId("lblHintsChoice");
	  }
	  public UpdateQuestionHints(String labelname){
		  initWidget(uiBinder.createAndBindUi(this));
		  hintsChoice.setText(labelname);
		  hintDelLbl.getElement().setId("lblHintDelLbl");
		  hintsTextBox.getElement().setId("tinyMCEHintsTextBox");
		  hintsChoice.getElement().setId("lblHintsChoice");
		  hintsChoice.getElement().setAttribute("alt", labelname);
		  hintsChoice.getElement().setAttribute("title", labelname);
		  addHintsLabel.getElement().setId("lblAddHintsLabel");
	  }
	  public UpdateQuestionHints(int count,String hintText){
		  initWidget(uiBinder.createAndBindUi(this));
		 this.hintText=hintText;
		 hintsChoice.getElement().setId("lblHintsChoice");
		  if(count==1){
			  hintsChoice.setText(""+count);
			  hintsChoice.getElement().setAttribute("alt", ""+count);
			  hintsChoice.getElement().setAttribute("title", ""+count);
			  addHintsLabel.setText(i18n.GL0859());
			  addHintsLabel.getElement().setAttribute("alt", i18n.GL0859());
			  addHintsLabel.getElement().setAttribute("title", i18n.GL0859());
		  }else{
			  hintsChoice.setText(""+count);
			  hintsChoice.getElement().setAttribute("alt", ""+count);
			  hintsChoice.getElement().setAttribute("title", ""+count);
		  }
		  hintDelLbl.getElement().setId("lblHintDelLbl");
		  hintDelLbl.getElement().getStyle().setDisplay(Display.NONE);
		  addHintsLabel.getElement().setId("lblAddHintsLabel");
		  hintsTextBox.getElement().setId("tinyMCEHintsTextBox");
	  }
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
			  hintsTextBox.setText(hintText);
			  hintsTextBox.getElement().setId("tinyMCEHintsTextBox");
			  hintsTextBox.getElement().setAttribute("alt", hintText);
			  hintsTextBox.getElement().setAttribute("title", hintText);
		  }
	  }
	  public void setLabelName(String labelName){
		  hintsChoice.getElement().setId("lblHintsChoice");
		  hintsChoice.setText(labelName);
		  hintsChoice.getElement().setAttribute("alt", labelName);
		  hintsChoice.getElement().setAttribute("title", labelName);
		}
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
