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
package org.ednovo.gooru.client.mvp.play.resource.question;


import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.dnd.IsDraggable;
import org.ednovo.gooru.client.mvp.dnd.IsDraggableMirage;
import org.ednovo.gooru.client.mvp.play.resource.question.event.ResetDragDropEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class HTAnswerChoiceOptionView extends Composite implements IsDraggable{

	public interface HTAnswerChoiceOptionViewUiBinder extends UiBinder<Widget,HTAnswerChoiceOptionView>{

	}
	@UiField HTML answerOptionText;
	@UiField FlowPanel htAnsDragFpnl;
	private int answerId;
	private boolean isAnswerCorrect;
	private String answerText="";
	public static HTAnswerChoiceOptionViewUiBinder htAnswerChoiceOptionViewUiBinder=GWT.create(HTAnswerChoiceOptionViewUiBinder.class);
	/**
	 * Constructor to set values for Answer options
	 */
	public HTAnswerChoiceOptionView(String ansText,String ansSerialNum){
		initWidget(htAnswerChoiceOptionViewUiBinder.createAndBindUi(this));
		this.answerText=ansText;
		answerOptionText.setHTML(removeHtmlTags(ansText!=null?ansText:""));
		answerOptionText.getElement().setId("htmlAnswerOptionText");
		htAnsDragFpnl.getElement().setId("htAnsDragFpnl");
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
	@Override
	public Widget getDragHandle() {
		return null;
	}
	@Override
	public IsDraggableMirage initDraggableMirage() {
		return new HTAnswerDragUc(removeHtmlTags(answerText));
	}
	@Override
	public void onDragBlur() {
	}
	@Override
	public String getDragId() {
		return "";
	}
	@Override
	public DRAG_TYPE getDragType() {
		return DRAG_TYPE.COLLECTION_ITEM;
	}
	@Override
	public int getDragTopCorrection() {
		return 5;
	}
	@Override
	public int getDragLeftCorrection() {
		return 40;
	}
	public void reorderCollectionItem(int widgetIndex) {
		AppClientFactory.fireEvent(new ResetDragDropEvent());
	}
	public FlowPanel getHtAnsDragFpnl() {
		return htAnsDragFpnl;
	}
	public void setHtAnsDragFpnl(FlowPanel htAnsDragFpnl) {
		this.htAnsDragFpnl = htAnsDragFpnl;
	}


}
