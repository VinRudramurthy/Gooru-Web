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

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.addquestion.QuestionTypeView;
import org.ednovo.gooru.client.mvp.shelf.event.AddAnswerImageEvent;
import org.ednovo.gooru.client.mvp.shelf.event.AddAnswerImageHandler;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

public class AddHotSpotQuestionAnswerChoice extends Composite implements AddAnswerImageHandler{

	public interface AddHotSpotQuestionAnswerChoiceUiBinder extends UiBinder<Widget, AddHotSpotQuestionAnswerChoice>{

	}

	public static AddHotSpotQuestionAnswerChoiceUiBinder uiBinder=GWT.create(AddHotSpotQuestionAnswerChoiceUiBinder.class);

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	private static final String PLUS_IMAGE="images/add-symbol.png";

	@UiField Label answerHeadLbl,answerHeadingTypeLbl;

	@UiField
	public Label errorMessageforAnswerChoice;
	@UiField public HTMLPanel imgContainer,textAnsContainer;
	@UiField public HorizontalPanel ansImageContainer;
	@UiField FlowPanel answerHeadContainer;
	@UiField Anchor ansImage,addAnswerChoice;
	@UiField Image addAnsPlusImage;
	@UiField HTMLEventPanel ansImageBlock;

	String[] anserChoiceNumArray=new String[]{"1","2","3","4","5","6"};
	public String fieldValue;
	public Label ansChoiceDeleteButton=new Label();
	private String richTextData=null;
	private String widgetId;
	private final static String CLICK="click";

	QuestionTypeView questionTypeView;
	public AddHotSpotQuestionAnswerChoice(QuestionTypeView questionTypeView){
		initWidget(uiBinder.createAndBindUi(this));
		AppClientFactory.getEventBus().addHandler(AddAnswerImageEvent.TYPE, this);
		this.questionTypeView=questionTypeView;
		answerHeadLbl.getElement().setId("lblAnswerHead");
		answerHeadLbl.setText(i18n.GL3214_1());
		answerHeadingTypeLbl.getElement().setId("lblAnswerHeadintType");
		answerHeadingTypeLbl.setText(i18n.GL3227_1());
		imgContainer.getElement().setId("pnlImgBoxContainer");
		textAnsContainer.getElement().setId("pnlTextBoxContainer");
		answerHeadContainer.getElement().setId("pnlAnswerHeadConatiner");
		ansChoiceDeleteButton.setStyleName("answerMarkDelete");
		ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
		ansImage.setText(i18n.GL3230_1());
		ansImage.getElement().setId("lnkAnswerImage");
		addAnsPlusImage.getElement().setId("imgAnsPlusImage");
		addAnsPlusImage.setUrl(PLUS_IMAGE);
		addAnswerChoice.setText(i18n.GL0866());
		addAnswerChoice.getElement().setId("lnkAnswerChoice");
		ansImageBlock.getElement().setId("pnlAnsImageBlock");
		ansImageBlock.addClickHandler(new panelsClickHandler());
		ansImageContainer.getElement().setId("pnlAnsImageContainer");
		errorMessageforAnswerChoice.getElement().setId("errlblErrorMessageforAnswerChoice");
		setAnswerFields(true);
	}

	public void setAnswerFields(boolean val){

		if(val){
			addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
			textAnsContainer.setVisible(false);
			imgContainer.setVisible(true);

		}else {
			addAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
			textAnsContainer.setVisible(true);
			imgContainer.setVisible(false);
		}

	}

	public void imageRDButtonClick(){
		setAnswerFields(true);
	}
	public void textRDButtonClick(){
		setAnswerFields(false);
		textAnsContainer.clear();
		addAnswerChoice();
		addAnswerChoice();

	}

	public void setAnswerChoices(){
		int widgetCount=textAnsContainer.getWidgetCount();
		for(int i=0;i<widgetCount;i++){
			final AddAnswerChoice addAnswerChoice=(AddAnswerChoice)textAnsContainer.getWidget(i);
			addAnswerChoice.getElement().setId(i+"");
			addAnswerChoice.setLabelName(anserChoiceNumArray[i]);

			if(!addAnswerChoice.optionSelectedButton.getElement().getId().equalsIgnoreCase(CLICK)){
				addAnswerChoice.optionSelectedButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if(addAnswerChoice.isOptionSelectedButton){
							addAnswerChoice.optionSelectedButton.setStyleName("answerMarkDeselected");
							addAnswerChoice.isOptionSelectedButton=false;
						}else{
							addAnswerChoice.optionSelectedButton.setStyleName("answerMarkSelected");
							addAnswerChoice.isOptionSelectedButton=true;
						}

					}
				});
			}

			addAnswerChoice.optionSelectedButton.getElement().setId(CLICK);

			if(i>1){

				addAnswerChoice.ansChoiceDeleteButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						addAnswerChoice.removeFromParent();
					}
				});

				addAnswerChoice.addMouseOverHandler(new MouseOverHandler() {
					@Override
					public void onMouseOver(MouseOverEvent event) {
						addAnswerChoice.ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.BLOCK);
					}
				});
				addAnswerChoice.addMouseOutHandler(new MouseOutHandler() {
					@Override
					public void onMouseOut(MouseOutEvent event) {
						addAnswerChoice.ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
					}
				});

			}
		}
		if(textAnsContainer.getWidgetCount()<5){
			addAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
		}else {
			addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		}

	}

	public void addAnswerChoice(){
		AddAnswerChoice addAnswerChoice=new AddAnswerChoice();
		textAnsContainer.add(addAnswerChoice);
		setAnswerChoices();
	}

	@UiHandler("addAnswerChoice")
	public void clickedOnAddChoiceButton(ClickEvent clickEvent){
		addAnswerChoice();
	}

	private class panelsClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			if(ansImageContainer.getWidgetCount()<6){
			questionTypeView.uploadAnswerImage();
			}
		}
	}

	@Override
	public void setAnswerImageUrl(String fileName,	String fileNameWithoutRepository, boolean isAnswerImage,boolean isSelected) {

		double randNumber = Math.random();
		final AddAnswerImg addAnswerImage = new AddAnswerImg();
		addAnswerImage.setAnswerImage(fileName+"?"+randNumber);
		addAnswerImage.setFileName(fileNameWithoutRepository);
		errorMessageforAnswerChoice.setText("");
		ansImageContainer.getElement().removeClassName("errorBorderMessage");
		if(widgetId!=null){
			addAnswerImage.setId(Integer.parseInt(widgetId));
			ansImageContainer.remove(Integer.parseInt(widgetId));
			ansImageContainer.insert(addAnswerImage, Integer.parseInt(widgetId));
			widgetId=null;
		}else{
			addAnswerImage.setId(ansImageContainer.getWidgetCount());
			ansImageContainer.add(addAnswerImage);
		}
		
		if(isSelected){
			addAnswerImage.selectedImage=false;
			addAnswerImage.imageSelected();
		}

		addAnswerImage.changeImgLbl.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
             widgetId=addAnswerImage.getElement().getId();
				questionTypeView.uploadAnswerImage();
			}
		});

		addAnswerImage.removeImgLbl.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				addAnswerImage.removeFromParent();
				refreshImageContainer();
			}
		});

		updateImageContainer();
	}


	public void updateImageContainer(){

		if(ansImageContainer.getWidgetCount()==6){
			ansImageBlock.getElement().getStyle().setOpacity(0.5);
			ansImage.getElement().getStyle().setCursor(Cursor.DEFAULT);
			ansImageBlock.getElement().getStyle().setCursor(Cursor.DEFAULT);
		}else{
			ansImage.getElement().getStyle().setCursor(Cursor.POINTER);
			ansImageBlock.getElement().getStyle().setCursor(Cursor.POINTER);
			ansImageBlock.getElement().getStyle().clearOpacity();
		}

	}

	public void refreshImageContainer(){
		for(int i=0;i<ansImageContainer.getWidgetCount();i++){
			AddAnswerImg answerImg=	(AddAnswerImg) ansImageContainer.getWidget(i);
			answerImg.getElement().setId(String.valueOf(i));
		}
		updateImageContainer();
	}


}
