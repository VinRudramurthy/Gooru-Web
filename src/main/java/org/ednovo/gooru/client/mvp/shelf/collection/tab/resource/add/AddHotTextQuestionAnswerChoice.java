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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

public class AddHotTextQuestionAnswerChoice extends Composite implements HasMouseOutHandlers,HasMouseOverHandlers{

	public interface AddHotTextQuestionAnswerChoiceUiBinder extends UiBinder<Widget, AddHotTextQuestionAnswerChoice>{

	}

	public static AddHotTextQuestionAnswerChoiceUiBinder uiBinder=GWT.create(AddHotTextQuestionAnswerChoiceUiBinder.class);

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField Label labelChoice,errorMessageforAnswerChoice,answerHeadLbl,answerHeadingTypeLbl;
	@UiField TinyMCE answerTextBox;
	@UiField HTMLPanel deleteButtonContainer,tinyOrTextBoxConatiner;
	@UiField AddResourceBundle addWebResourceStyle;
	@UiField FlowPanel answerHeadContainer;
	@UiField RadioButton wordRDButton,sentenceRDButton;
	@UiField TinyMCE highlightTextArea;
	public String fieldValue;
	public Label ansChoiceDeleteButton=new Label();
	private String richTextData=null;
	public AddHotTextQuestionAnswerChoice(){
		initWidget(uiBinder.createAndBindUi(this));
		labelChoice.getElement().setId("lblLabelChoice");
		answerHeadLbl.getElement().setId("lblAnswerHead");
		answerHeadLbl.setText(i18n.GL3214_1());
		answerHeadingTypeLbl.getElement().setId("lblAnswerHeadintType");
		answerHeadingTypeLbl.setText(i18n.GL3215_1());
		wordRDButton.getElement().setId("rdWord");
		wordRDButton.setText(i18n.GL3219_1());
		sentenceRDButton.getElement().setId("rdSentence");
		sentenceRDButton.setText(i18n.GL3220_1());
		highlightTextArea.getElement().setId("pnlTinyOrHighlightTextBoxContainer");
		tinyOrTextBoxConatiner.getElement().setId("pnlTinyOrTextBoxConatiner");
		answerHeadContainer.getElement().setId("pnlAnswerHeadConatiner");
		answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		ansChoiceDeleteButton.setStyleName(addWebResourceStyle.addResourceFormAnswerDelete());
		ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
		deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
		deleteButtonContainer.add(ansChoiceDeleteButton);
	}
	public AddHotTextQuestionAnswerChoice(String labelName){
		initWidget(uiBinder.createAndBindUi(this));
		labelChoice.getElement().setId("lblLabelChoice");
		answerHeadLbl.getElement().setId("lblAnswerHead");
		answerHeadLbl.setText(i18n.GL3214_1());
		answerHeadingTypeLbl.getElement().setId("lblAnswerHeadintType");
		answerHeadingTypeLbl.setText(i18n.GL3215_1());
		wordRDButton.getElement().setId("rdWord");
		wordRDButton.setText(i18n.GL3219_1());
		sentenceRDButton.getElement().setId("rdSentence");
		sentenceRDButton.setText(i18n.GL3220_1());
		highlightTextArea.getElement().setId("pnlTinyOrHighlightTextBoxContainer");
		labelChoice.setText(labelName);
		tinyOrTextBoxConatiner.getElement().setId("pnlTinyOrTextBoxConatiner");
		answerHeadContainer.getElement().setId("pnlAnswerHeadConatiner");
		answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		ansChoiceDeleteButton.setStyleName(addWebResourceStyle.addResourceFormAnswerDelete());
		ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
		deleteButtonContainer.add(ansChoiceDeleteButton);
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
	}
	public AddHotTextQuestionAnswerChoice(String labelName,String richTextData){
		initWidget(uiBinder.createAndBindUi(this));
		this.richTextData=richTextData;
		labelChoice.getElement().setId("lblLabelChoice");
		answerHeadLbl.getElement().setId("lblAnswerHead");
		answerHeadLbl.setText(i18n.GL3214_1());
		answerHeadingTypeLbl.getElement().setId("lblAnswerHeadintType");
		answerHeadingTypeLbl.setText(i18n.GL3215_1());
		wordRDButton.getElement().setId("rdWord");
		wordRDButton.setText(i18n.GL3219_1());
		sentenceRDButton.getElement().setId("rdSentence");
		sentenceRDButton.setText(i18n.GL3220_1());
		highlightTextArea.getElement().setId("pnlTinyOrHighlightTextBoxContainer");
		labelChoice.setText(labelName);
		tinyOrTextBoxConatiner.getElement().setId("pnlTinyOrTextBoxConatiner");
		answerHeadContainer.getElement().setId("pnlAnswerHeadConatiner");
		answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		ansChoiceDeleteButton.setStyleName(addWebResourceStyle.addResourceFormAnswerDelete());
		ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
		deleteButtonContainer.add(ansChoiceDeleteButton);
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
	}

	@Override
	public void onLoad(){
		super.onLoad();
			 Scheduler.get().scheduleDeferred(new ScheduledCommand(){
				@Override
				public void execute() {
						setRichTextData();
						answerTextBox.showTinyMceToolBar();
						highlightTextArea.showTinyMceToolBar();
				}
	       });
	}
	 public void setRichTextData(){
		 tinyOrTextBoxConatiner.getElement().setId("pnlTinyOrTextBoxConatiner");
		 answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		 highlightTextArea.getElement().setId("pnlTinyOrHighlightTextBoxContainer");
		 deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		 errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
		   if(richTextData!=null){
			   answerTextBox.setText(richTextData);
			   highlightTextArea.setText(richTextData);
		   }
	   }
	public void setLabelName(String labelName){
		labelChoice.setText(labelName);
		labelChoice.getElement().setId("lblLabelChoice");
		deleteButtonContainer.getElement().setId("pnlDeleteButtonContainer");
		tinyOrTextBoxConatiner.getElement().setId("pnlTinyOrTextBoxConatiner");
		answerTextBox.getElement().setId("tinyMCEAnswerTextBox");
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
	}

	public void setHeadLabelFields(boolean val){
		answerHeadLbl.setVisible(val);
		answerHeadContainer.setVisible(val);
		highlightTextArea.setVisible(false);
		wordRDButton.setVisible(false);
		sentenceRDButton.setVisible(false);
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

	public TinyMCE getHighlightTextArea() {
		return highlightTextArea;
	}
	public void setHighlightTextArea(TinyMCE highlightTextArea) {
		this.highlightTextArea = highlightTextArea;
	}
	public void reorderRDButtonClick(){
		answerHeadingTypeLbl.setText(i18n.GL3225_1());
		wordRDButton.setVisible(false);
		sentenceRDButton.setVisible(false);
		highlightTextArea.setVisible(false);
		labelChoice.setVisible(true);
		tinyOrTextBoxConatiner.setVisible(true);
	}
	public void highlightRDButtonClick(){
		answerHeadingTypeLbl.setText(i18n.GL3218_1());
		wordRDButton.setVisible(true);
		sentenceRDButton.setVisible(true);
		highlightTextArea.setVisible(true);
		labelChoice.setVisible(false);
		tinyOrTextBoxConatiner.setVisible(false);

		wordRDButtonClick();
	}

	public void wordRDButtonClick(){
		wordRDButton.setValue(true);
		sentenceRDButton.setValue(false);
	}
	public void sentenceRDButtonClick(){
		wordRDButton.setValue(false);
		sentenceRDButton.setValue(true);
	}

}
