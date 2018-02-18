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

public class AddQuestionAnswerChoice extends Composite implements HasMouseOutHandlers,HasMouseOverHandlers{

	public interface AddQuestionAnswerChoiceUiBinder extends UiBinder<Widget, AddQuestionAnswerChoice>{

	}
	public static AddQuestionAnswerChoiceUiBinder uiBinder=GWT.create(AddQuestionAnswerChoiceUiBinder.class);

	@UiField Label labelChoice,optionSelectedButton,errorMessageforAnswerChoice,optionNoButton;
	@UiField TinyMCE answerTextBox;
	@UiField HTMLPanel deleteButtonContainer,tinyOrTextBoxConatiner,optionNoButtonContainer;
	@UiField AddResourceBundle addWebResourceStyle;
	public String fieldValue;
	public Label ansChoiceDeleteButton=new Label();
	private String richTextData=null;
	public AddQuestionAnswerChoice(){
		initWidget(uiBinder.createAndBindUi(this));
		labelChoice.getElement().setId("lblLabelChoice");
		tinyOrTextBoxConatiner.getElement().setId("pnlTinyOrTextBoxConatiner");
		answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		ansChoiceDeleteButton.setStyleName(addWebResourceStyle.addResourceFormAnswerDelete());
		ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
		deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		optionNoButtonContainer.getElement().setId("pnlOptionNoButtonContainer");
		optionNoButton.getElement().setId("lblOptionNoButton");
		optionSelectedButton.getElement().setId("lblOptionSelectedButton");
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
		deleteButtonContainer.add(ansChoiceDeleteButton);
		optionNoButtonContainer.setVisible(false);
	}
	public AddQuestionAnswerChoice(String labelName){
		initWidget(uiBinder.createAndBindUi(this));
		labelChoice.getElement().setId("lblLabelChoice");
		labelChoice.setText(labelName);
		tinyOrTextBoxConatiner.getElement().setId("pnlTinyOrTextBoxConatiner");
		answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		ansChoiceDeleteButton.setStyleName(addWebResourceStyle.addResourceFormAnswerDelete());
		ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
		deleteButtonContainer.add(ansChoiceDeleteButton);
		optionNoButtonContainer.getElement().setId("pnlOptionNoButtonContainer");
		optionNoButton.getElement().setId("lblOptionNoButton");
		optionSelectedButton.getElement().setId("lblOptionSelectedButton");
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
		optionNoButtonContainer.setVisible(false);
	}
	public AddQuestionAnswerChoice(String labelName,String richTextData){
		initWidget(uiBinder.createAndBindUi(this));
		this.richTextData=richTextData;
		labelChoice.getElement().setId("lblLabelChoice");
		labelChoice.setText(labelName);
		tinyOrTextBoxConatiner.getElement().setId("pnlTinyOrTextBoxConatiner");
		answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		ansChoiceDeleteButton.setStyleName(addWebResourceStyle.addResourceFormAnswerDelete());
		ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
		deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		deleteButtonContainer.add(ansChoiceDeleteButton);
		optionNoButtonContainer.getElement().setId("pnlOptionNoButtonContainer");
		optionNoButton.getElement().setId("lblOptionNoButton");
		optionSelectedButton.getElement().setId("lblOptionSelectedButton");
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
		optionNoButtonContainer.setVisible(false);
	}
	public void showAnswerChoicesForMultipleAnswers(){
		labelChoice.getElement().setId("lblLabelChoice");
		tinyOrTextBoxConatiner.getElement().setId("pnlTinyOrTextBoxConatiner");
		answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		tinyOrTextBoxConatiner.setStyleName("multipleAnswerInputControl");
		tinyOrTextBoxConatiner.addStyleName("multiAnswerChoiceContainer");
		deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		optionNoButtonContainer.getElement().setId("pnlOptionNoButtonContainer");
		optionNoButton.getElement().setId("lblOptionNoButton");
		optionSelectedButton.getElement().setId("lblOptionSelectedButton");
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
		optionNoButtonContainer.setVisible(true);
	}
	public void showAnswerChoicesForOthers(){
		labelChoice.getElement().setId("lblLabelChoice");
		tinyOrTextBoxConatiner.getElement().setId("pnlTinyOrTextBoxConatiner");
		answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		tinyOrTextBoxConatiner.setStyleName(addWebResourceStyle.addResourceFormAnswerInputControl());
		tinyOrTextBoxConatiner.addStyleName("answerChoiceAndHintsTextcontainer");
		deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		optionNoButtonContainer.getElement().setId("pnlOptionNoButtonContainer");
		optionNoButton.getElement().setId("lblOptionNoButton");
		optionSelectedButton.getElement().setId("lblOptionSelectedButton");
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
		optionNoButtonContainer.setVisible(false);
	}
	@Override
	public void onLoad(){
		super.onLoad();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
						setRichTextData();
						answerTextBox.showTinyMceToolBar();
				}
	       });
	}
	 public void setRichTextData(){
		 tinyOrTextBoxConatiner.getElement().setId("pnlTinyOrTextBoxConatiner");
		 answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		 deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		 optionNoButtonContainer.getElement().setId("pnlOptionNoButtonContainer");
		 optionNoButton.getElement().setId("lblOptionNoButton");
		 optionSelectedButton.getElement().setId("lblOptionSelectedButton");
		 errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
		   if(richTextData!=null){
			   answerTextBox.setText(richTextData);
		   }
	   }
	public void setLabelName(String labelName){
		labelChoice.setText(labelName);
		labelChoice.getElement().setId("lblLabelChoice");
		deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		tinyOrTextBoxConatiner.getElement().setId("pnlTinyOrTextBoxConatiner");
		optionNoButtonContainer.getElement().setId("pnlOptionNoButtonContainer");
		optionNoButton.getElement().setId("lblOptionNoButton");
		optionSelectedButton.getElement().setId("lblOptionSelectedButton");
		answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
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
	public TinyMCE getAnswerTextBox() {
		return answerTextBox;
	}
	public void setAnswerTextBox(TinyMCE answerTextBox) {
		this.answerTextBox = answerTextBox;
	}
}
