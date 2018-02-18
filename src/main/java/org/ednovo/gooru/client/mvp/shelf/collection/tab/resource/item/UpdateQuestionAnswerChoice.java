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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UpdateQuestionAnswerChoice extends Composite implements HasMouseOutHandlers,HasMouseOverHandlers{
   public interface UpdateQuestionAnswerChoiceUiBinder extends UiBinder<Widget, UpdateQuestionAnswerChoice>{
	   
   }
   public static UpdateQuestionAnswerChoiceUiBinder uiBinder=GWT.create(UpdateQuestionAnswerChoiceUiBinder.class);
   
  
   @UiField Label labelChoice,optionSelectedButton,errorMessageforAnswerChoice;
   @UiField TinyMCE answerTextBox;
   @UiField HTMLPanel deleteButtonContainer;
   @UiField public HTMLPanel answerTextBoxContainer;
   private String richTextValue=null;
   public String fieldValue="";
   public int isMultipleChoiceQuestion=0;
 
   @UiField UpdateResourceBundle editQuestionStyle;
   public Label ansChoiceDeleteButton=new Label();
   public UpdateQuestionAnswerChoice(){
	   initWidget(uiBinder.createAndBindUi(this));
   }
   public UpdateQuestionAnswerChoice(String labelname){
	   initWidget(uiBinder.createAndBindUi(this));
	   labelChoice.getElement().setId("lblLabelChoice");
		labelChoice.setText(labelname);
		labelChoice.getElement().setAttribute("alt", labelname);
		labelChoice.getElement().setAttribute("title", labelname);
		answerTextBoxContainer.getElement().setId("pnlAnswerTextBoxContainer");
		answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		ansChoiceDeleteButton.setStyleName(editQuestionStyle.addResourceFormAnswerDelete());
		ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
		deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		deleteButtonContainer.add(ansChoiceDeleteButton);
		optionSelectedButton.getElement().setId("lblOptionSelectedButton");
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
   }
   
   public  UpdateQuestionAnswerChoice(int childWidgetCount,String richTextValue,int isMultipleChoiceQuestion){
	   initWidget(uiBinder.createAndBindUi(this));
	   this.richTextValue=richTextValue;
	   this.isMultipleChoiceQuestion=isMultipleChoiceQuestion;
	   if(childWidgetCount>1){
	    ansChoiceDeleteButton.setStyleName(editQuestionStyle.addResourceFormAnswerDelete());
		ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
		deleteButtonContainer.add(ansChoiceDeleteButton);
	   }
	   labelChoice.getElement().setId("lblLabelChoice");
	   answerTextBoxContainer.getElement().setId("pnlAnswerTextBoxContainer");
		answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		optionSelectedButton.getElement().setId("lblOptionSelectedButton");
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
   }
   public void onLoad(){
	   super.onLoad();
	   Scheduler.get().scheduleDeferred(new ScheduledCommand(){
			@Override
			public void execute() {
				if(isMultipleChoiceQuestion!=3){
					setRichTextData();
				}
				
			}
       });
   }
   public void setRichTextData(){
	   if(richTextValue!=null){
		   answerTextBox.setText(richTextValue);
		   answerTextBox.getElement().setAttribute("alt", richTextValue);
		   answerTextBox.getElement().setAttribute("title", richTextValue);
	   }	   
   }
   public void setLabelName(String labelName){
		labelChoice.setText(labelName);
		labelChoice.getElement().setAttribute("alt", labelName);
		labelChoice.getElement().setAttribute("title", labelName);
	}
   public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, MouseOverEvent.getType());
	}
	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, MouseOutEvent.getType());
	}
	
//	@UiHandler("answerTextBox")
//	public void keyUponAnswerTextBox(KeyUpEvent event){
//		String textBoxValue=answerTextBox.getText();
//		if(textBoxValue.length()>0){
//			errorMessageforAnswerChoice.setText("");
//		}
//	}

 }
