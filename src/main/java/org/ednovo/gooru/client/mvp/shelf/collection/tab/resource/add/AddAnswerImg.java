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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AddAnswerImg extends Composite
{
	@UiField public Label selLbl;
	@UiField Label changeImgLbl,removeImgLbl;
	@UiField Image ansImageContainer;
	@UiField HTMLPanel imgBlock;
	private int answerId;
	public boolean selectedImage=false;
	private boolean isAnswerCorrect;

	private String fileName=null;
	private static final String DEFAULT_QUESTION_IMAGE="images/default-question.png";

	public interface AddQuestionImageUiBinder extends UiBinder <Widget, AddAnswerImg>{

	}

	public static AddQuestionImageUiBinder addQuestionImageUiBinder = GWT.create(AddQuestionImageUiBinder.class);

	private  MessageProperties i18n = GWT.create(MessageProperties.class);

	public AddAnswerImg() {
		initWidget(addQuestionImageUiBinder.createAndBindUi(this));
		ansImageContainer.getElement().setId("imgAnsImageContainer");
		changeImgLbl.setText(i18n.GL3232_1());
		changeImgLbl.getElement().setId("lblChangeImgLbl");
		changeImgLbl.getElement().setAttribute("alt", i18n.GL3232_1());
		changeImgLbl.getElement().setAttribute("title",i18n.GL3232_1());
		removeImgLbl.setText(i18n.GL3233_1());
		removeImgLbl.getElement().setId("lblChangeImgLbl");
		removeImgLbl.getElement().setAttribute("alt", i18n.GL3233_1());
		removeImgLbl.getElement().setAttribute("title",i18n.GL3233_1());
		imgBlock.getElement().setId("imgBlockPnl");
		selLbl.getElement().setId("selLbl");

	}


	public void setAnswerImage(String imageUrl){
		ansImageContainer.setUrl(imageUrl);
	}
	public String getAnswerImage(){
		return ansImageContainer.getUrl();
	}

	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@UiHandler("ansImageContainer")
	public void setAnswerDefaultImage(ErrorEvent event){
		ansImageContainer.setUrl(DEFAULT_QUESTION_IMAGE);
	}

	public Label getChangeImgLbl() {
		return changeImgLbl;
	}


	public Label getRemoveImgLbl() {
		return removeImgLbl;
	}


	public void setChangeImgLbl(Label changeImgLbl) {
		this.changeImgLbl = changeImgLbl;
	}


	public void setRemoveImgLbl(Label removeImgLbl) {
		this.removeImgLbl = removeImgLbl;
	}

	public void setId(int id){
		this.getElement().setId(String.valueOf(id));
	}

	@UiHandler("selLbl")
	public void selectedImage(ClickEvent event){
		imageSelected();
	}
	
	public void imageSelected(){
		if(selectedImage){
			selLbl.removeStyleName("answerMarkSelected");
			imgBlock.removeStyleName("ansImageBlockSel");
			selectedImage=false;
		}else{
		selLbl.addStyleName("answerMarkSelected");
		imgBlock.addStyleName("ansImageBlockSel");
		selectedImage=true;
		}
	}

	public boolean isAnswerCorrect() {
		return isAnswerCorrect;
	}
	public void setAnswerCorrect(boolean isAnswerCorrect) {
		this.isAnswerCorrect = isAnswerCorrect;
	}

	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
}
