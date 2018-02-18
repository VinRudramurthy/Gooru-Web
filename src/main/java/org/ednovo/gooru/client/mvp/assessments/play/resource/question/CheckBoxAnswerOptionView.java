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
package org.ednovo.gooru.client.mvp.assessments.play.resource.question;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

public class CheckBoxAnswerOptionView extends Composite{

	public interface CheckBoxAnswerOptionViewUiBinder extends UiBinder<Widget,CheckBoxAnswerOptionView>{

	}
	@UiField public Label answerChoiceResult,radioYesButton,radioNoButton;
	@UiField HTML answerOptionText;
	@UiField public RadioButton answerOptionYesRadioButton,answerOptionNoRadioButton;
	@UiField FlowPanel questionsMainPanel;
	private int answerId;
	private boolean isAnswerCorrect;
	private String answerText="";
	public static CheckBoxAnswerOptionViewUiBinder questionAnswerOptionViewUiBinder=GWT.create(CheckBoxAnswerOptionViewUiBinder.class);
	/**
	 * Constructor to set values for Answer options
	 */
	public CheckBoxAnswerOptionView(String questionText,String questionSerialNum){
		initWidget(questionAnswerOptionViewUiBinder.createAndBindUi(this));
		questionsMainPanel.getElement().setId("fpnlQuestionContainer");
		this.answerText=questionText;
		answerOptionText.setHTML(questionSerialNum+" "+removeHtmlTags(questionText!=null?questionText:""));
		answerOptionYesRadioButton.setName(questionSerialNum);
		answerOptionNoRadioButton.setName(questionSerialNum);

		answerChoiceResult.getElement().setId("lblAnswerChoiceResult");
		radioYesButton.getElement().setId("lblRadioYesButton");
		radioNoButton.getElement().setId("lblRadioNoButton");
		answerOptionText.getElement().setId("htmlAnswerOptionText");
		answerOptionYesRadioButton.getElement().setId("rdAnswerOptionYesRadioButton");
		answerOptionNoRadioButton.getElement().setId("rdAnswerOptionNoRadioButton");
	}
	/**
	 * This method is used to remove HTMLTags from the String
	 * @param text
	 * @return
	 */
	private String removeHtmlTags(String text){
		/**
		 * Commented the following line to fix issue with displaying math symbols.
		 */
		text=text.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
		return text;
	}
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	public boolean isAnswerCorrect() {
		return isAnswerCorrect;
	}
	public void setAnswerCorrect(boolean isAnswerCorrect) {
		this.isAnswerCorrect = isAnswerCorrect;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

}
