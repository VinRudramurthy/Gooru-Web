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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;

import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.faq.CopyRightPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsAndPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.BlueButtonUc;
import org.ednovo.gooru.client.uc.HTMLEventPanel;
import org.ednovo.gooru.client.uc.RemoveToolTipUc;
import org.ednovo.gooru.client.ui.TinyMCE;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.shared.model.content.ProfanityCheckDo;
import org.ednovo.gooru.shared.model.content.QuestionAnswerDo;
import org.ednovo.gooru.shared.model.content.QuestionHintsDo;
import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class AddQuestionResourceView extends Composite implements MessageProperties{
	
	public interface AddQuestionResourceViewUiBinder extends UiBinder<Widget, AddQuestionResourceView>{
		
	}
	
	public static AddQuestionResourceViewUiBinder uiBinder=GWT.create(AddQuestionResourceViewUiBinder.class);
	
	private CollectionItemDo collectionItemDo=null;
	 boolean isAnsweEmpty = false;
	@UiField Label errorMessageForAnsCheck,errorMessageForHintsCheck,errorMessageForExplanation,addResourceFormTitleChoice,ansChoiceErrMsg; 
	@UiField HTMLEventPanel addQuestionResourceButton,lblContentRights;
	@UiField HTMLPanel questionAnswerChoiceContainer,questionTrueOrFalseAnswerChoiceContainer;
	@UiField public static Label errorMessageForQuestion;
	@UiField Label questionTypeHeader,questionTypeText,loadingTextLbl,rightsLbl,explanationLabel,andText,additionalText,agreeText,questionNameErrorLbl,explainationErrorLbl;
	@UiField Anchor addAnswerChoice,addHintsLabel;

	@UiField Anchor addQuestionImg;
	@UiField HTMLPanel hintsContainer,buttonContainer,questionText,correctText;
	@UiField HTMLPanel addQuestImgContainer,panelContentRights,rightsContent;
	/*@UiField ListBox questionTypeTextBox;*/
	@UiField BlueButtonUc addbutton;
	/*@UiField TextArea explainationTextArea;*/
	@UiField TinyMCE questionNameTextArea,explainationTextArea;
	@UiField FlowPanel answerchoiceTitleContainer;
	
	/*@UiField Button questionNameTextAreaToolBarButton;*/
	@UiField Button cancelButton;
	@UiField
	CheckBox rightsChkBox;
	@UiField
	Anchor copyRightAnr;
	
	@UiField
	Anchor termsAndPolicyAnr,privacyAnr;
	
	@UiField
	Anchor commuGuideLinesAnr;
	@UiField AddQuestionAnswerChoice alphaLetterA,alphaLetterB;
	private CopyRightPolicyVc copyRightPolicy;
	
	private TermsAndPolicyVc termsAndPolicyVc;
	private TermsOfUse termsOfUse;
	
	boolean isSaveButtonClicked=false,isAddBtnClicked=true,isRightsClicked=false;
	private String questionType="MC";
	
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
		if(questionType.equals("MC")){
			removeSelectedOption(questionAnswerChoiceContainer, null);
		}
	}
	@UiField AddResourceBundle addWebResourceStyle;
	
	
		
	RemoveToolTipUc removeToolTip=null;   
	boolean validationValue=false, isAnswerChoiceSelected=false;
	private static final String ERROR_MSG_QUESTION = GL0310;
    private static final String ERROR_MSG_ANSWER = GL0311;
    private static final String ERROR_MSG_ANSWER_SELECTED =GL0312; 
    private static final String ERROR_MSG_ATLEAST_SELECTED =GL0876; 
    private static final String ERROR_MSG_HINTS_LENGTH = GL0877;
	private static final String ERROR_MSG_ANSWER_LENGTH =GL0878;
	private static final String ERROR_MSG_EXPLAINATION_LENGTH =GL0879;
	private static final String ERROR_MSG_QUESTION_LENGTH =GL0880;
	private static final String ERROR_MSG_CHAR_LIMIT=GL0143;
	
	private static final String ERROR_MSG_FIB_BALANCED=GL0881;
	private static final String ERROR_MSG_FIB_BLANKS=GL0882;
	private static final String ERROR_MSG_MULTIPLE_FIB=GL0883;
	private static final String ERROR_MSG_MARK_AS_BLANK = GL0884;
	private static final String REGX_PATTERN = "\\[(.*?)\\]";
	private static final String FIB_SEPARATOR = GL0885;
	
	private static final int ANSWER_CHOICE_HINTS_TEXT_LENGTH =150;
	private static final int QUESTION_TEXT_LENGTH =500;
	private static final int EXPLAINATION_TEXT_LENGTH =400;
	
	private List<Widget> answerChoicesList=new ArrayList<Widget>();
	
	String[] anserChoiceArray=new String[]{"A","B","C","D","E"};
	List<ProfanityCheckDo> profanityList,hintsListForProfanity;
	public AddQuestionResourceView(){
		initWidget(uiBinder.createAndBindUi(this));
		setHeaderAndBodyText("MC");
		questionText.getElement().setInnerHTML(" "+GL0863);
		addQuestionImg.setText(GL_SPL_PLUS+" "+GL0860);
		addResourceFormTitleChoice.setText(GL0864);
		correctText.getElement().setInnerHTML(GL0314);
		alphaLetterA.setLabelName(GL_GRR_ALPHABET_A);
		alphaLetterB.setLabelName(GL_GRR_ALPHABET_B);
		addAnswerChoice.setText(GL0866);
		explanationLabel.setText(" "+GL0867);
		addHintsLabel.setText(GL0868);
		rightsLbl.setText(GL0869);
		agreeText.setText(GL0870);
		commuGuideLinesAnr.setText(GL0871);
		termsAndPolicyAnr.setText(" "+GL0872+GL_GRR_COMMA);
		privacyAnr.setText(" "+GL0873);
		andText.setText(" "+GL_GRR_AND+" ");
		copyRightAnr.setText(" "+GL0875);
		additionalText.setText(GL0874);
		cancelButton.setText(GL0142);
		addbutton.setText(GL0590);
		loadingTextLbl.setText(GL0591.toLowerCase());
		questionNameTextArea.getElement().setAttribute("maxlength", "500");
		explainationTextArea.getElement().setAttribute("maxlength", "400");
		addbutton.getElement().setId("btnAdd");
		cancelButton.getElement().setId("btnCancel");
		addQuestionImg.getElement().setId("lnkAddQuestionImg");
		addAnswerChoice.getElement().setId("lnkAnswerChoice");
		addHintsLabel.getElement().setId("lnkHints");
		panelContentRights.setVisible(false);
		questionNameTextArea.markAsBlankPanel.setVisible(false);
		loadingTextLbl.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
	    buttonContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		loadingTextLbl.setVisible(false);
		rightsChkBox.addClickHandler(new rightsChecked());
		rightsChkBox.getElement().setId("chkRights");
		alphaLetterA.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(alphaLetterA.getParent().getElement().getChildNodes().getLength()>3)
				{
				alphaLetterA.ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.BLOCK);
				}
				else
				{
				alphaLetterA.ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
				}
			}
		});
		alphaLetterA.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				alphaLetterA.ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
			}
		});
		showRemoveToolTip(alphaLetterA.ansChoiceDeleteButton);	
		alphaLetterA.ansChoiceDeleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(alphaLetterA.getParent().getElement().getChildNodes().getLength()>3)
				{
				alphaLetterA.removeFromParent();
				refreshOptionNames();
				removeToolTip.hide();
				}
			}
		});
		alphaLetterB.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(alphaLetterB.getParent().getElement().getChildNodes().getLength()>3)
				{
				alphaLetterB.ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.BLOCK);
				}
				else
				{
				alphaLetterB.ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
				}
			}
		});
		alphaLetterB.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				alphaLetterB.ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
			}
		});
		showRemoveToolTip(alphaLetterB.ansChoiceDeleteButton);	
		alphaLetterB.ansChoiceDeleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(alphaLetterB.getParent().getElement().getChildNodes().getLength()>3)
				{
				alphaLetterB.removeFromParent();
				refreshOptionNames();
				removeToolTip.hide();
				}
			}
		});
		//questionTypeTextBox.getElement().setId("lbQuestion Type*");
		for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
			final AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
			addQuestionAnswerChoice.optionSelectedButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					selectOrDeselectOption(questionAnswerChoiceContainer,addQuestionAnswerChoice);
				}
			});
		}
		setTrueOrFalseFields();
		//questionNameTextAreaToolBarButton.addClickHandler(new ShowTinyMceToolBar(questionNameTextArea));
	}
	public AddQuestionResourceView(CollectionItemDo collectionItemDo){
		initWidget(uiBinder.createAndBindUi(this));
		this.collectionItemDo=collectionItemDo;
		addbutton.setText(GL0141);
		loadingTextLbl.setVisible(false);
		loadingTextLbl.setText(GL0808);
		rightsLbl.setText(GL0869);
		agreeText.setText(GL0870);
		commuGuideLinesAnr.setText(GL0871);
		termsAndPolicyAnr.setText(" "+GL0872+GL_GRR_COMMA);
		privacyAnr.setText(" "+GL0873);
		andText.setText(" "+GL_GRR_AND+" ");
		copyRightAnr.setText(" "+GL0875);
		additionalText.setText(GL0874);
		cancelButton.setText(GL0142);
		loadingTextLbl.setText(GL0808.toLowerCase());
		explanationLabel.setText(" "+GL0867);
		addAnswerChoice.setText(GL0866);
		addHintsLabel.setText(GL0868);
		setQuestionTypeStaticTexts();
		rightsChkBox.addClickHandler(new rightsChecked());
		setTrueOrFalseFields();
	}
	
	/**
	 * Sets the text for type of question selected based on question type number.
	 */
	
	public void setQuestionTypeStaticTexts(){
		int questionTypeNum=collectionItemDo.getResource().getType();
		if(questionTypeNum==1){
			setQuestionType("MC");
			showMulipleAnswerChoice();
		}else if(questionTypeNum==3){
			setQuestionType("T/F");
			showTrueOrFalseAnswerChoice();
		}else if(questionTypeNum==6){
			setQuestionType("OE");
			showOpenEndedQuestion();
		}else if(questionTypeNum==4){
			setQuestionType("FIB");
			showFillInTheBlank();
		}else if(questionTypeNum==7){
			setQuestionType("MA");
			showMulipleAnswerChoice();
		}
	}
	public void refreshOptionNames(){
		for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
			AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
			addQuestionAnswerChoice.setLabelName(anserChoiceArray[i]);
		}
		if(questionAnswerChoiceContainer.getWidgetCount()<5){
			addAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
		}
	}
	
	public void selectOrDeselectOption(HTMLPanel questionAnswerChoiceContainer,AddQuestionAnswerChoice addQuestionAnswerChoice){
		if(questionType.equals("T/F")||questionType.equals("MC")){
			removeSelectedOption(questionAnswerChoiceContainer,addQuestionAnswerChoice);
		}
		ansChoiceErrMsg.setText("");
		if(addQuestionAnswerChoice.optionSelectedButton.getStyleName().equals(addWebResourceStyle.answerDeselected())){
			addQuestionAnswerChoice.optionSelectedButton.setStyleName(addWebResourceStyle.answerSelected());
			addStyleToBody(addQuestionAnswerChoice);
			
		}
		else if(addQuestionAnswerChoice.optionSelectedButton.getStyleName().equals(addWebResourceStyle.answerSelected())){
			addQuestionAnswerChoice.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
			removeStyleToBody(addQuestionAnswerChoice);
		}
	}
	
	public void removeSelectedOption(HTMLPanel questionAnswerChoiceContainer,AddQuestionAnswerChoice selectedAnswerChoice){
		for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
			AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
			removeStyleToBody(addQuestionAnswerChoice);
			if(selectedAnswerChoice!=null&&addQuestionAnswerChoice.optionSelectedButton.getStyleName().equals(addWebResourceStyle.answerSelected())
					&& selectedAnswerChoice.optionSelectedButton.getStyleName().equals(addWebResourceStyle.answerSelected())){
				
			}else{
				addQuestionAnswerChoice.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
			}
			
		}
	}
	
	public void addStyleToBody(AddQuestionAnswerChoice addQuestionAnswerChoice){
//		try{
//			if(addQuestionAnswerChoice!=null){
//				if(addQuestionAnswerChoice.answerTextBox!=null){
//					addQuestionAnswerChoice.answerTextBox.addStyleToBody();
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
	}
	public void removeStyleToBody(AddQuestionAnswerChoice addQuestionAnswerChoice){
//		try{
//			if(addQuestionAnswerChoice!=null){
//				if(addQuestionAnswerChoice.answerTextBox!=null){
//					addQuestionAnswerChoice.answerTextBox.removeStyleToBody();
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
	}
	@Override
	public void onLoad() {
		super.onLoad();
	}
	
	@UiHandler("cancelButton")
	public void clickedOnCancelButton(ClickEvent clickEvent){
		
		resetToHints();
		//resetToAnswers();
		hidePopup();
		
	}
	public void setTrueOrFalseFields(){
		questionTrueOrFalseAnswerChoiceContainer.clear();
		//addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		for(int i=0;i<2;i++){
			int widgetCount=questionTrueOrFalseAnswerChoiceContainer.getWidgetCount();
			final AddQuestionAnswerChoice addQuestionAnswer=new AddQuestionAnswerChoice(anserChoiceArray[widgetCount]);
			addQuestionAnswer.tinyOrTextBoxConatiner.clear();
			if(i==0){
				addQuestionAnswer.fieldValue="True";
				HTML trueHtmlString=new HTML("True");
				trueHtmlString.getElement().getStyle().setMarginTop(8, Unit.PX);
				addQuestionAnswer.tinyOrTextBoxConatiner.add(trueHtmlString);
			}else{
				addQuestionAnswer.fieldValue="False";
				HTML falseHtmlString=new HTML("False");
				falseHtmlString.getElement().getStyle().setMarginTop(8, Unit.PX);
				addQuestionAnswer.tinyOrTextBoxConatiner.add(falseHtmlString);
			}
			addQuestionAnswer.optionSelectedButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					selectOrDeselectOption(questionTrueOrFalseAnswerChoiceContainer,addQuestionAnswer);
				}
			});
			questionTrueOrFalseAnswerChoiceContainer.add(addQuestionAnswer);
		}

	}
	public void selectTrueOrFallseCorrectAnswerOption(int answerIndex,boolean isAnswerCorrect){
//		int widgetCount=questionTrueOrFalseAnswerChoiceContainer.getWidgetCount();
		//for(int i=0;i<widgetCount;i++){
			final AddQuestionAnswerChoice addQuestionAnswer=(AddQuestionAnswerChoice)questionTrueOrFalseAnswerChoiceContainer.getWidget(answerIndex);
			if(isAnswerCorrect){
				addQuestionAnswer.optionSelectedButton.setStyleName(addWebResourceStyle.answerSelected());
			}else{
				addQuestionAnswer.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
			}
		//}
	}
	public void setMultipleChoiceAnswerFields(){
		questionAnswerChoiceContainer.clear();
		for(int i=0;i<2;i++){
			int widgetCount=questionAnswerChoiceContainer.getWidgetCount();
			final AddQuestionAnswerChoice addQuestionAnswer=new AddQuestionAnswerChoice(anserChoiceArray[widgetCount]);
			addQuestionAnswer.optionSelectedButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					selectOrDeselectOption(questionAnswerChoiceContainer,addQuestionAnswer);
				}
			});
			questionAnswerChoiceContainer.add(addQuestionAnswer);
		}
	}
	@UiHandler("addAnswerChoice")
	public void clickedOnAddChoiceButton(ClickEvent clickEvent){
		int widgetCount=questionAnswerChoiceContainer.getWidgetCount();
		final AddQuestionAnswerChoice addQuestionAnswer=new AddQuestionAnswerChoice(anserChoiceArray[widgetCount]);
		addQuestionAnswer.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
		addQuesetionAnswerOptionTextArea(addQuestionAnswer,widgetCount);
	}
	private void addQuesetionAnswerOptionTextArea(final AddQuestionAnswerChoice addQuestionAnswer,int widgetCount){
		
		answerChoicesList.add(addQuestionAnswer);
		addQuestionAnswer.optionSelectedButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectOrDeselectOption(questionAnswerChoiceContainer,addQuestionAnswer);
			}
		});
		if(widgetCount>1){
			addQuestionAnswer.ansChoiceDeleteButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(addQuestionAnswer.getParent().getElement().getChildNodes().getLength()>3)
					{
					addQuestionAnswer.removeFromParent();
					refreshOptionNames();
					removeToolTip.hide();
					isAddBtnClicked=true;
					}
				}
			});
			addQuestionAnswer.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(addQuestionAnswer.getParent().getElement().getChildNodes().getLength()>3)
					{
					addQuestionAnswer.ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.BLOCK);
					}
					else
					{
					addQuestionAnswer.ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
					}
				}
			});
			addQuestionAnswer.addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					addQuestionAnswer.ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.NONE);
				}
			});
			showRemoveToolTip(addQuestionAnswer.ansChoiceDeleteButton);	
		}
		questionAnswerChoiceContainer.add(addQuestionAnswer);
		if(questionAnswerChoiceContainer.getWidgetCount()>=5){
			addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		}
	}
	
	public void showRemoveToolTip(final Label deleteButton){
		deleteButton.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				    removeToolTip=new RemoveToolTipUc();	                
	                int left=event.getRelativeElement().getAbsoluteLeft()-16;
	                int top=event.getRelativeElement().getAbsoluteTop()+27;
				    removeToolTip.setPopupPosition(left, top);
	                removeToolTip.getElement().getStyle().setZIndex(999);
	                removeToolTip.show();
			}
		});
		deleteButton.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
					removeToolTip.hide();
			}
		});
	}

	public void resetToHints(){
		int widgetsCount=hintsContainer.getWidgetCount();
		for(int i=0;i<widgetsCount;){
			AddHintsView addHintsView =(AddHintsView) hintsContainer.getWidget(i);
			//addHintsView.hintTextBox.setText("");
			if(i<0){			
			if(i==0){
				addHintsView.hintsTextLblVal.setText("");
			}else{
				addHintsView.hintsTextLblVal.setText("");
			}
			i++;
			}else{
				addHintsView.removeFromParent();
				widgetsCount=hintsContainer.getWidgetCount();
			}
		
		}
	}
	/*public void resetToAnswers(){
		int widgetsCount=questionAnswerChoiceContainer.getWidgetCount();
		for(int i=0; i< widgetsCount;){
			AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
			if(i<2){
				if(i==0){
					addQuestionAnswerChoice.answerTextBox.setText("");
					addQuestionAnswerChoice.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
				}else{
					addQuestionAnswerChoice.answerTextBox.setText("");
					addQuestionAnswerChoice.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
				}
				i++;
			}
			else{
				addQuestionAnswerChoice.removeFromParent();
				widgetsCount=questionAnswerChoiceContainer.getWidgetCount();
				addAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
			}
			
		}
	}*/
	
	/*public void resetAllFields(){
		int widgetsCount=questionAnswerChoiceContainer.getWidgetCount();
		addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		for(int i=0; i< widgetsCount;){
			AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
			if(i<2){
				if(i==0){
					//addQuestionAnswerChoice.answerTextBox.setReadOnly(true);
					addQuestionAnswerChoice.answerTextBox.setText("True");
					addQuestionAnswerChoice.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
				}else{
					//addQuestionAnswerChoice.answerTextBox.setReadOnly(true);
					addQuestionAnswerChoice.answerTextBox.setText("False");
					addQuestionAnswerChoice.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
				}
				i++;
			}
			else{
				addQuestionAnswerChoice.removeFromParent();
				widgetsCount=questionAnswerChoiceContainer.getWidgetCount();
			}
			
		}
		
	}*/
	
	
    @UiHandler("addHintsLabel")
    public void clickOnHintsLabel(ClickEvent event){
    	Window.enableScrolling(false);
    	int widgetCount=hintsContainer.getWidgetCount();
        final AddHintsView addHints = new AddHintsView(widgetCount+1);
        addHintsTextArea(addHints);
    }
    
    
    
    private void addHintsTextArea(final AddHintsView addHints){
    	
	       hintsContainer.add(addHints); 
	       addHints.hintDelLbl.addClickHandler(new ClickHandler() {
		        
		        @Override
		        public void onClick(ClickEvent event) 
		        {    
		            addHints.removeFromParent();
		            removeToolTip.hide();
		            refreshHintNumber();
		            isAddBtnClicked=true;
		        }
	       });
	    
	    addHints.addMouseOverHandler(new MouseOverHandler() {
	        
	        @Override
	        public void onMouseOver(MouseOverEvent event) {
	            addHints.hintDelLbl.getElement().getStyle().setDisplay(Display.BLOCK);
	            
	        }
	    });
	    addHints.addMouseOutHandler(new MouseOutHandler() {
	        
	        @Override
	        public void onMouseOut(MouseOutEvent event) {
	            addHints.hintDelLbl.getElement().getStyle().setDisplay(Display.NONE);
	        }
	    });
	    showRemoveToolTip(addHints.hintDelLbl);
	    
	    if(hintsContainer.getWidgetCount()>=5){
	        addHintsLabel.getElement().getStyle().setDisplay(Display.NONE);
	    }
    }
    

    
    protected void refreshHintNumber() 
    {
        int hintWidgetsCount=hintsContainer.getWidgetCount();
        for(int i=0 ; i<hintWidgetsCount;i++)
        {
            Widget childWidget=hintsContainer.getWidget(i);
            AddHintsView addHints=(AddHintsView)childWidget;
            addHints.hintNumLbl.setText(""+(i+1));
            
        }
        if(hintsContainer.getWidgetCount()<5){
            addHintsLabel.getElement().getStyle().setDisplay(Display.BLOCK);
        }
        
    }
    private class rightsChecked implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if(rightsChkBox.getValue()){
				rightsLbl.getElement().getStyle().setColor("black");
			}
			else{
				rightsLbl.getElement().getStyle().setColor("orange");
			}
			
		}
	}
    @UiHandler("addQuestionResourceButton")
	public void clickedOnAddQuestionButton(ClickEvent event)
	{
    	boolean fieldValidationCheck;
    	if(getQuestionType().equalsIgnoreCase("T/F")){
    		if (isAnswerChoiceEmpty(questionTrueOrFalseAnswerChoiceContainer)) {
    			fieldValidationCheck = false;
				isAddBtnClicked=true;
			}
				if(!isHintsAdded(hintsContainer)){
					if (!isAnswerChoiceSelected(questionTrueOrFalseAnswerChoiceContainer)) {
						fieldValidationCheck = false;
						ansChoiceErrMsg.setText(ERROR_MSG_ANSWER_SELECTED);
						isAddBtnClicked=true;
					}
			
			}
    	}else if(getQuestionType().equalsIgnoreCase("MC") || getQuestionType().equalsIgnoreCase("MA")){
    		if (isAnswerChoiceEmpty(questionAnswerChoiceContainer)) {
    			fieldValidationCheck = false;
				isAddBtnClicked=true;
			}
    	
				if(!isHintsAdded(hintsContainer)){
					if (!isAnswerChoiceSelected(questionAnswerChoiceContainer)) {
						String errorMessage=getQuestionType().equalsIgnoreCase("MA")?ERROR_MSG_ATLEAST_SELECTED:ERROR_MSG_ANSWER_SELECTED;
						ansChoiceErrMsg.setText(errorMessage);
						fieldValidationCheck = false;
						isAddBtnClicked=true;
					}
				}
    	}
    	if(isAddBtnClicked){
    		isRightsClicked=rightsChkBox.getValue();
    		isAddBtnClicked=false;
    		final Map<String, String> parms = new HashMap<String, String>();
    		parms.put("text", questionNameTextArea.getText());
    		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

    			@Override
    			public void onSuccess(Boolean value) {
    				if(value){
    					SetStyleForProfanity.SetStyleForProfanityForTinyMCE(questionNameTextArea, questionNameErrorLbl, value);
    					isAddBtnClicked=true;
    				}else{
    					SetStyleForProfanity.SetStyleForProfanityForTinyMCE(questionNameTextArea, questionNameErrorLbl, value);
    					parms.put("text", explainationTextArea.getText());
    					AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

    						@Override
    						public void onSuccess(Boolean value) {
    							if(value){
    								SetStyleForProfanity.SetStyleForProfanityForTinyMCE(explainationTextArea, explainationErrorLbl, value);
    								isAddBtnClicked=true;
    							}else{
    								
    								MixpanelUtil.mixpanelEvent("Collaborator_edits_collection");
    								
    								SetStyleForProfanity.SetStyleForProfanityForTinyMCE(explainationTextArea, explainationErrorLbl, value);
    								String mediaFileName=null;
    							       
    						        boolean isQuestEnteredFlag = true;
    						        List<String> answersListFIB = new ArrayList<String>();
    						    	boolean fieldValidationStaus=true;
    						    	if(addQuestImgContainer.getElement().hasChildNodes()){
    						    		AddQuestionImg addQuestionImage=(AddQuestionImg)addQuestImgContainer.getWidget(0);
    						    		mediaFileName=addQuestionImage.getFileName();
    						    	}
    						    	errorMessageForQuestion.setText("");
    						        if(questionNameTextArea.getText()==null||questionNameTextArea.getText().trim().equals("")){
    						        	errorMessageForQuestion.setText(ERROR_MSG_QUESTION);
    						        	fieldValidationStaus=false;
    						        	isQuestEnteredFlag=false;
    						        	isAddBtnClicked=true;
    						        }
    						        //This regex is used to get text count with out html tags
    						        String questionNameText = questionNameTextArea.getText().replaceAll("\\<.*?>","");
    						        if(questionNameText.length()>QUESTION_TEXT_LENGTH){
    						        	errorMessageForQuestion.setText(ERROR_MSG_QUESTION_LENGTH);
    						        	fieldValidationStaus=false;
    						        	isAddBtnClicked=true;
    						        }
    						        errorMessageForExplanation.setText("");
    						        if(questionNameText.length()>QUESTION_TEXT_LENGTH){
    						        	errorMessageForQuestion.setText(ERROR_MSG_QUESTION_LENGTH);
    						        	fieldValidationStaus=false;
    						        	isAddBtnClicked=true;
    						        }
    						        String explainationText = explainationTextArea.getText().replaceAll("\\<.*?>","");
    						    	if(explainationText.trim().length() > EXPLAINATION_TEXT_LENGTH){
    						    		errorMessageForExplanation.setText(ERROR_MSG_EXPLAINATION_LENGTH);
    									fieldValidationStaus=false;
    									isAddBtnClicked=true;
    								}
    						    	if (fieldValidationStaus && getQuestionType().equalsIgnoreCase("T/F")) {
    									ansChoiceErrMsg.setText("");
    									if (isAnswerChoiceEmpty(questionTrueOrFalseAnswerChoiceContainer)) {
    										fieldValidationStaus = false;
    										isAddBtnClicked=true;
    									}else{
    										
    										if(!isHintsAdded(hintsContainer)){
    											if (!isAnswerChoiceSelected(questionTrueOrFalseAnswerChoiceContainer)) {
    												fieldValidationStaus = false;
    												ansChoiceErrMsg.setText(ERROR_MSG_ANSWER_SELECTED);
    												isAddBtnClicked=true;
    											}else{
    												profanityCheckForHints(fieldValidationStaus,answersListFIB,mediaFileName);
    											}
    										}
    									}
    								}
    							
    								else if (fieldValidationStaus && getQuestionType().equalsIgnoreCase("MC") || getQuestionType().equalsIgnoreCase("MA")) {
    									ansChoiceErrMsg.setText("");
    									if (isAnswerChoiceEmpty(questionAnswerChoiceContainer)) {
    										fieldValidationStaus = false;
    										isAddBtnClicked=true;
    									}else{
    										if(!isHintsAdded(hintsContainer)){
    											if (!isAnswerChoiceSelected(questionAnswerChoiceContainer)) {
    												String errorMessage=getQuestionType().equalsIgnoreCase("MA")?ERROR_MSG_ATLEAST_SELECTED:ERROR_MSG_ANSWER_SELECTED;
    												ansChoiceErrMsg.setText(errorMessage);
    												fieldValidationStaus = false;
    												isAddBtnClicked=true;
    											}else{
    												isProfanityCheckForAnswerChoice(fieldValidationStaus,answersListFIB,mediaFileName);
    											}
    										}
    									}
    								}
    								/**
    								 * Based on Question Type FIB validations done.
    								 */
    								else if(fieldValidationStaus && getQuestionType().equalsIgnoreCase("FIB")){
    									
    									String questionTextFIB = questionNameTextArea.getRawContent().trim();
    									boolean isBalanced=true;
    									if(!isFIBQuestionBalanced(questionTextFIB) && (questionNameTextArea.getText().trim()!=null || !questionNameTextArea.getText().trim().equalsIgnoreCase(""))){ 
    										errorMessageForQuestion.setText(ERROR_MSG_FIB_BALANCED);
    							        	fieldValidationStaus=false;
    							        	isBalanced=false;
    							        	isAddBtnClicked=true;
    									}
    									
    									if(isBalanced){
    										if(answersListFIB.size()!=0){
    											answersListFIB.removeAll(answersListFIB);
    											answersListFIB = getFIBAnswers(questionTextFIB);
    										}else{
    											answersListFIB = getFIBAnswers(questionTextFIB);
    										}
    									}
    									
    									if(answersListFIB.size()==0 && isQuestEnteredFlag){
    										errorMessageForQuestion.setText(ERROR_MSG_FIB_BALANCED);
    										fieldValidationStaus=false;
    										isAddBtnClicked=true;
    									}
    									if(answersListFIB.size()!=0 && isQuestEnteredFlag){
    										for(String answer:answersListFIB){
    											if(answer.contains("[") || answer.contains("]")){
    												errorMessageForQuestion.setText(ERROR_MSG_MULTIPLE_FIB);
    												fieldValidationStaus=false;
    												break;
    											}
    										}
    									}
    									if(isAnsweEmpty){
    										errorMessageForQuestion.setText(ERROR_MSG_MARK_AS_BLANK);
    										isAnsweEmpty=false;
    										fieldValidationStaus=false;
    										isAddBtnClicked=true;
    									}else{
    										isAnsweEmpty=true;
    									}
    									if(answersListFIB.size()>3 && isQuestEnteredFlag){
    										errorMessageForQuestion.setText(ERROR_MSG_FIB_BLANKS);
    										fieldValidationStaus=false;
    									}
    									if(isAnsweEmpty && !isHintsAdded(hintsContainer)){
    										profanityCheckForHints(fieldValidationStaus,answersListFIB,mediaFileName);
    									}else{
    										isAddBtnClicked=true;
    									}
    									
    								}else if(fieldValidationStaus && getQuestionType().equalsIgnoreCase("OE")){
    									if(!isHintsAdded(hintsContainer)){
    										profanityCheckForHints(fieldValidationStaus,answersListFIB,mediaFileName);
    									}
    								}
    							}
    						}
    					});
    				}
    			}
    		});
    	}
	}

    /**
	 * If all validations successful, question is added to the collection.
	 */
	public void addFunctionality(boolean fieldValidationStaus,List<String> answersListFIB,String mediaFileName){
		
    	if(fieldValidationStaus){
    		buttonContainer.getElement().getStyle().setDisplay(Display.NONE);
    		loadingTextLbl.setVisible(true);
    		CollectionQuestionItemDo collectionQuestionItemDo = new CollectionQuestionItemDo();
			String questionText = questionNameTextArea.getRawContent().replace("&nbsp;","").trim();
			String explaination = explainationTextArea.getText();
			if(explaination!=null&&!explaination.trim().equals("")){
				explaination=explainationTextArea.getRawContent().trim();
			}
					
			ArrayList<QuestionAnswerDo> enteredAnswers = new ArrayList<QuestionAnswerDo>(); 
			ArrayList<QuestionHintsDo> enteredHints = new ArrayList<QuestionHintsDo>();
			HashMap<String,ArrayList<QuestionAnswerDo>> answerMap = new HashMap<String,ArrayList<QuestionAnswerDo>>();
			HashMap<String,ArrayList<QuestionHintsDo>> hintsMap = new HashMap<String,ArrayList<QuestionHintsDo>>();
			if(getQuestionType().equalsIgnoreCase("T/F")){
				for(int i=0;i<questionTrueOrFalseAnswerChoiceContainer.getWidgetCount();i++)
				{
					QuestionAnswerDo questionAnswerDo = new QuestionAnswerDo();
					AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionTrueOrFalseAnswerChoiceContainer.getWidget(i);
					questionAnswerDo.setAnswerText(addQuestionAnswerChoice.fieldValue);
					//questionAnswerDo.setAnswerText(addQuestionAnswerChoice.answerTextBox.getRawContent()); 
					questionAnswerDo.setAnswerType("text");
					questionAnswerDo.setSequence(i+1);
					if(addQuestionAnswerChoice.optionSelectedButton.getStyleName().equals(addWebResourceStyle.answerSelected()))
					{
						questionAnswerDo.setIsCorrect(true);
					}
					else
					{
						questionAnswerDo.setIsCorrect(false);
					}
					enteredAnswers.add(questionAnswerDo);
				}
				
			}else if(getQuestionType().equalsIgnoreCase("MC")||getQuestionType().equalsIgnoreCase("MA")){
				for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++)
				{
					QuestionAnswerDo questionAnswerDo = new QuestionAnswerDo();
					AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
					questionAnswerDo.setAnswerText(addQuestionAnswerChoice.answerTextBox.getRawContent());
					//questionAnswerDo.setAnswerText(addQuestionAnswerChoice.answerTextBox.getRawContent()); 
					questionAnswerDo.setAnswerType("text");
					questionAnswerDo.setSequence(i+1);
					if(addQuestionAnswerChoice.optionSelectedButton.getStyleName().equals(addWebResourceStyle.answerSelected()))
					{
						questionAnswerDo.setIsCorrect(true);
					}
					else
					{
						questionAnswerDo.setIsCorrect(false);
					}
					enteredAnswers.add(questionAnswerDo);
				}
			}else if(getQuestionType().equalsIgnoreCase("FIB")){
				questionText = getFIBQuestion(questionNameTextArea.getRawContent().replace("&nbsp;","").trim()); 
				for(int i=0;i<answersListFIB.size();i++)
				{
					QuestionAnswerDo questionAnswerDo = new QuestionAnswerDo();
					questionAnswerDo.setAnswerText(answersListFIB.get(i).trim()); 
					questionAnswerDo.setAnswerType("text");
					questionAnswerDo.setSequence(i+1);
					questionAnswerDo.setIsCorrect(false);
					enteredAnswers.add(questionAnswerDo);
				}
			}
			
			answerMap.put("answer",enteredAnswers);
				
			for(int i=0;i<hintsContainer.getWidgetCount();i++)
			{
				AddHintsView addHints = (AddHintsView)hintsContainer.getWidget(i);
				QuestionHintsDo questionHintsDo=new QuestionHintsDo();
				String hintText=addHints.hintTextBox.getText();
				if(hintText!=null&&!hintText.trim().equals("")){
					hintText=addHints.hintTextBox.getRawContent().trim();
				}
				questionHintsDo.setHintText(hintText); 
				questionHintsDo.setSequence(i+1);
				enteredHints.add(questionHintsDo);
			}
			hintsMap.put("hint",enteredHints);
			
			collectionQuestionItemDo.setTypeName(getQuestionType()); 
			collectionQuestionItemDo.setQuestionText(questionText);
			collectionQuestionItemDo.setAnswers(answerMap);
			collectionQuestionItemDo.setExplanation(explaination);
			collectionQuestionItemDo.setHints(hintsMap);
			collectionQuestionItemDo.setTitle(questionText);
			collectionQuestionItemDo.setDescription(questionText);  
		
			if(!isSaveButtonClicked){
				isSaveButtonClicked=true;
				if (getQuestionType().equalsIgnoreCase("T/F")) {
		    		MixpanelUtil.ClickAddOnTrueOrFalseTabFromCollectionEdit();
		    	}else if(getQuestionType().equalsIgnoreCase("MC")){
		    		MixpanelUtil.ClickAddOnMultipleChoiceTabFromCollectionEdit();
		    	}
		    	else if(getQuestionType().equalsIgnoreCase("OE")){
		    		MixpanelUtil.ClickAddOnOpenEndedTabFromCollectionEdit();
		    	}
		    	else if(getQuestionType().equalsIgnoreCase("MA")){
		    		MixpanelUtil.successfullyaddaMAquestiontype();
		    	}else if(getQuestionType().equalsIgnoreCase("FIB")){
		    		MixpanelUtil.question_Create_FIB_success();
		    	}
				createQuestionResource(mediaFileName,collectionQuestionItemDo);
			}
    	}
	}
	
	/**
	 * Abstract method to add question resource.
	 * @param mediaFileName instance of {@link String}
	 * @param collectionQuestionItemDo instance of {@link CollectionQuestionItemDo}
	 */
	public abstract void createQuestionResource(String mediaFileName,CollectionQuestionItemDo collectionQuestionItemDo);
	
	 private boolean isAnswerChoiceSelected(HTMLPanel questionAnswerChoiceContainer) 
     {
     
             boolean isAnswerChoiceSelected=false;
             for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
                     AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
                     if(addQuestionAnswerChoice.optionSelectedButton.getStyleName().equals(addWebResourceStyle.answerSelected())){
                            isAnswerChoiceSelected=true;
                     }
                     
             }
             
             return isAnswerChoiceSelected;

     }
	 public boolean profanityCheckForHints(final boolean fieldValidationStaus,final List<String> answersListFIB,final String mediaFileName){
		 validationValue=false;
		 AppClientFactory.getInjector().getResourceService().checkProfanityForList(hintsListForProfanity, new SimpleAsyncCallback<List<ProfanityCheckDo>>() {
				
				@Override
				public void onSuccess(List<ProfanityCheckDo> result) {
					 for(int i=0;i<hintsContainer.getWidgetCount();i++){
						 final AddHintsView addHints = (AddHintsView) hintsContainer.getWidget(i);
						 addHints.errorMessageforHints.setText("");
						 SetStyleForProfanity.SetStyleForProfanityForTinyMCE(addHints.hintTextBox, addHints.errorMessageforHints, result.get(i).questionValue);
						  if(result.get(i).questionValue==true){
							  addHints.errorMessageforHints.getElement().setAttribute("style", "float: left;left: 24px;");
			                  validationValue=true;
			                  isAddBtnClicked=true;
						  }
					  }
					 if(validationValue){
						  return;
					  }else{
						  if(!isRightsClicked){
								rightsLbl.getElement().getStyle().setColor("orange");
								isAddBtnClicked=true;
							}else{
								if(fieldValidationStaus){
									 addFunctionality(!validationValue,answersListFIB,mediaFileName);
								}
							}
					  }
				}
			});
		 return validationValue;
	 }
	 /**
	  * 
	  * @function isProfanityCheck 
	  * 
	  * @created_date : 24-Jan-2014
	  * 
	  * @description : This method is used to check profanity for multiple widgets list (Because we can't do sync call form client side)
	  * 
	  * @parm(s) : @return
	  * 
	  * @return : boolean
	  *
	  * @throws : <Mentioned if any exceptions>
	  *
	  */
    public boolean isProfanityCheckForAnswerChoice(final boolean fieldValidationStaus,final List<String> answersListFIB,final String mediaFileName){
    	validationValue=false;
    	AppClientFactory.getInjector().getResourceService().checkProfanityForList(profanityList, new SimpleAsyncCallback<List<ProfanityCheckDo>>() {
			
			@Override
			public void onSuccess(List<ProfanityCheckDo> result) {
				//addFunctionality(fieldValidationStaus,answersListFIB,mediaFileName);
				  for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
					  final AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
					  addQuestionAnswerChoice.errorMessageforAnswerChoice.setText("");
					  SetStyleForProfanity.SetStyleForProfanityForTinyMCE(addQuestionAnswerChoice.answerTextBox, addQuestionAnswerChoice.errorMessageforAnswerChoice, result.get(i).questionValue);
					  if(result.get(i)!=null && result.get(i).questionValue==true){
						  	 addQuestionAnswerChoice.errorMessageforAnswerChoice.getElement().setAttribute("style", "float: left;left: 24px;");
		                     validationValue=true;
		                     isAddBtnClicked=true;
					  }
				  }
				  if(validationValue && fieldValidationStaus){
					  return;
				  }else{
						AppClientFactory.getInjector().getResourceService().checkProfanityForList(hintsListForProfanity, new SimpleAsyncCallback<List<ProfanityCheckDo>>() {
							
							@Override
							public void onSuccess(List<ProfanityCheckDo> result) {
								 for(int i=0;i<hintsContainer.getWidgetCount();i++){
									  if(result.get(i).questionValue==true){
										  final AddHintsView addHints = (AddHintsView) hintsContainer.getWidget(i);
										  addHints.errorMessageforHints.setText("");
										  addHints.errorMessageforHints.getElement().setAttribute("style", "float: left;left: 24px;");
						                  SetStyleForProfanity.SetStyleForProfanityForTinyMCE(addHints.hintTextBox, addHints.errorMessageforHints, result.get(i).questionValue);
						                  validationValue=true;
						                  isAddBtnClicked=true;
									  }
								  }
								 if(validationValue){
									  return;
								  }else{
									  if(!isRightsClicked){
											rightsLbl.getElement().getStyle().setColor("orange");
											isAddBtnClicked=true;
										}else{
											 if(fieldValidationStaus)
											  addFunctionality(!validationValue,answersListFIB,mediaFileName);
										}
								  }
							}
						});
				  }
			}
		});
		return validationValue;
    }
    public boolean isAnswerChoiceEmpty(HTMLPanel questionAnswerChoiceContainer){
		profanityList=new ArrayList<ProfanityCheckDo>();
         for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
                 final AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
                 String answerChoiceValue=null;
                 addQuestionAnswerChoice.errorMessageforAnswerChoice.setText("");
                 if(getQuestionType().equalsIgnoreCase("T/F")){
                	 answerChoiceValue=addQuestionAnswerChoice.fieldValue;
                 }else if(getQuestionType().equalsIgnoreCase("MC")||getQuestionType().equalsIgnoreCase("MA")){
                	 answerChoiceValue=addQuestionAnswerChoice.answerTextBox.getContent().replaceAll("\\<.*?>","");
                 }
                 ProfanityCheckDo profanitymodel=new ProfanityCheckDo();
                 if(answerChoiceValue==null||answerChoiceValue.trim().equalsIgnoreCase("")){
                	  	 isAnswerChoiceSelected=true;
                         addQuestionAnswerChoice.errorMessageforAnswerChoice.setText(ERROR_MSG_ANSWER);
                         profanitymodel.setQuestionID(Integer.toString(i));
                         profanityList.add(profanitymodel);
                         addQuestionAnswerChoice.errorMessageforAnswerChoice.getElement().setAttribute("style", "display:block");
                 }else{
                	 if(answerChoiceValue.trim().length()>ANSWER_CHOICE_HINTS_TEXT_LENGTH){
                		   isAnswerChoiceSelected=true;
                		   Document.get().getElementById(addQuestionAnswerChoice.answerTextBox.getID()+"_message").setInnerText("");
                		   addQuestionAnswerChoice.errorMessageforAnswerChoice.setText(ERROR_MSG_ANSWER_LENGTH);
                	 }else{
                		 	isAnswerChoiceSelected=false;
	                		profanitymodel.setQuestionID(Integer.toString(i));
	                     	profanitymodel.setQuestionText(answerChoiceValue);
	                     	profanityList.add(profanitymodel);
                	 }
                 }
         }
         return isAnswerChoiceSelected;
 }
    public boolean isHintsAdded(HTMLPanel hintsContainer){
    	boolean hintsAdded=false;
    	hintsListForProfanity=new ArrayList<ProfanityCheckDo>();
    	if(hintsContainer.getWidgetCount()>=1){
    		  for(int i=0;i<hintsContainer.getWidgetCount();i++){
  	        	final AddHintsView addHints = (AddHintsView) hintsContainer.getWidget(i);
  	        	  ProfanityCheckDo profanitymodel=new ProfanityCheckDo();
  	        	  profanitymodel.setQuestionID(Integer.toString(i));
  	        	  if(addHints.hintTextBox.getContent()!=null|| !addHints.hintTextBox.getContent().trim().equals("")){
  	        			String hintsText=addHints.hintTextBox.getContent().replaceAll("\\<.*?>","");
  	        		  if(hintsText.trim().length()>ANSWER_CHOICE_HINTS_TEXT_LENGTH){
  	        			  Document.get().getElementById(addHints.hintTextBox.getID()+"_message").setInnerText("");
  	            		  addHints.errorMessageforHints.setText(ERROR_MSG_HINTS_LENGTH);
  	            		  hintsAdded=true;
  	            		  isAddBtnClicked=true;
  	        		  }
  	        		  profanitymodel.setQuestionText(addHints.hintTextBox.getContent());
  	        	  }
  	        	  hintsListForProfanity.add(profanitymodel);
  	        }
    	}
    	
        return hintsAdded;
}
     /*public void resetAllErrorFields(){
    		errorMessageForQuestion.setText("");
    		ansChoiceErrMsg.setText("");
    		errorMessageForExplanation.setText("");
    		 for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
         		 AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);   
                 addQuestionAnswerChoice.errorMessageforAnswerChoice.setText("");       
    		 }
     }*/
     
	@UiHandler("addQuestionImg")
	public void clickOnAddQuestImg(ClickEvent event){
		uploadQuestionImage();
	}
	public void setEditQuestionImage(){
		String tumbnailUrl="";
		if(collectionItemDo!=null){
			if (collectionItemDo.getResource().getAssets() != null
					&& collectionItemDo.getResource().getAssets().size() > 0) {
				tumbnailUrl = collectionItemDo.getCollection().getAssetURI()
						+ collectionItemDo.getResource().getFolder()
						+ collectionItemDo.getResource().getAssets().get(0).getAsset().getName();
				
			} 
			setEditQuestionImage(tumbnailUrl);
		}
		
	}
	
	/**
	 * This method sets Multiple Answer question type content.
	 * 
	 */
	
	public void showMulipleAnswerChoice(){
		setHeaderAndBodyText(questionType);
		ansChoiceErrMsg.setText("");
		errorMessageForQuestion.setText("");
		 for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
     		 AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);   
     		 addQuestionAnswerChoice.errorMessageforAnswerChoice.setText("");       
		 }
		questionTrueOrFalseAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		addAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.BLOCK);
	}
	
	/**
	 * This method sets True/False question type content.
	 * 
	 */
	public void showTrueOrFalseAnswerChoice(){
		setHeaderAndBodyText("T/F");
		ansChoiceErrMsg.setText("");
		errorMessageForQuestion.setText("");
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionTrueOrFalseAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.BLOCK);
	}
	
	/**
	 * This method sets Open-Ended question type content.
	 * 
	 */
	
	public void showOpenEndedQuestion(){ 
		setHeaderAndBodyText("OE");
		ansChoiceErrMsg.setText("");
		errorMessageForQuestion.setText("");
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionTrueOrFalseAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.NONE);
		
	}
	/**
	 * This method sets Fill in the blank question content.
	 */
	public void showFillInTheBlank(){
		questionNameTextArea.markAsBlankPanel.setVisible(true);
		setHeaderAndBodyText("FIB");
		ansChoiceErrMsg.setText("");
		errorMessageForQuestion.setText("");
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionTrueOrFalseAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.NONE);
	}
	
	/**
	 * This method sets header text for respective questions.
	 * 
	 * @param tabType
	 *            is a selected question type.
	 */
	
	public void setHeaderAndBodyText(String tabType){
		if(tabType.equals("MC")){
			questionTypeHeader.setText(GL0349);
			questionTypeText.setText(GL0350);
			questionNameTextArea.markAsBlankPanel.setVisible(false);
		}else if(tabType.equals("MA")){
			questionTypeHeader.setText(GL0351);
			questionTypeText.setText(GL0352);
			questionNameTextArea.markAsBlankPanel.setVisible(false);
		}else if(tabType.equals("T/F")){
			questionTypeHeader.setText(GL0353);
			questionTypeText.setText(GL0354);
			questionNameTextArea.markAsBlankPanel.setVisible(false);
		}else if(tabType.equals("FIB")){
			questionTypeHeader.setText(GL0355);
			questionTypeText.setText(GL0356);
		}else if(tabType.equals("OE")){
			questionTypeHeader.setText(GL0357);
			questionTypeText.setText(GL0358);
			questionNameTextArea.markAsBlankPanel.setVisible(false);
		}
	}
	public boolean getQuestionEditMode(){
		return collectionItemDo!=null?true:false;
	}
	public void getHideRightsToolTip()
	{
	panelContentRights.setVisible(false);
	}
	public abstract void uploadQuestionImage();
	public abstract void hidePopup();
	public abstract void setEditQuestionImage(String fileName);
	
	public class ShowTinyMceToolBar implements ClickHandler{
		private TinyMCE tinyMce=null;
		public ShowTinyMceToolBar(TinyMCE tinyMce){
			this.tinyMce=tinyMce;
		}
		@Override
		public void onClick(ClickEvent event) {
			tinyMce.showTinyMceToolBar();
		}
		
	}
	
	/**
	 * Sets the added content to Edit and updating based on question type.
	 */
	
	protected void showEditQuestionResourceView(){
		
		TreeSet<QuestionAnswerDo> answerChoicesSet = collectionItemDo.getResource().getAnswers();
		Iterator<QuestionAnswerDo> it = answerChoicesSet.iterator(); 
		List<QuestionAnswerDo> questionAnswerDoList = new ArrayList<QuestionAnswerDo>();
		
		try{
			/**
			 *  If type = 4 from API, treated as FIB.
			 */
			 if(collectionItemDo.getResource().getType()==4){
				 while(it.hasNext()){
					 QuestionAnswerDo answer = it.next();
					 questionAnswerDoList.add(answer); 
				 }
				 String fillInTheBlankQuestion = collectionItemDo.getResource().getTitle();
				 String[] fibArray = fillInTheBlankQuestion.split(FIB_SEPARATOR);
				 List<String> questionArray = Arrays.asList(fibArray);
				 int answerArraySize = answerChoicesSet.size();
				 StringBuffer questionText = new StringBuffer();
				 for(int j = 0; j < questionArray.size(); j++) {
					 questionText.append(questionArray.get(j));
					 if(j<answerArraySize) {
							 questionText.append("["+questionAnswerDoList.get(j).getAnswerText()+"]");
					 }
				 }
				 questionNameTextArea.setText(questionText.toString());
				 explainationTextArea.setText(collectionItemDo.getResource().getExplanation());
			 }else{
				 questionNameTextArea.setText(collectionItemDo.getResource().getTitle());
				 explainationTextArea.setText(collectionItemDo.getResource().getExplanation());
			 }
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		TreeSet<QuestionHintsDo> hintsList = collectionItemDo.getResource().getHints();
		Iterator<QuestionHintsDo> iterator = hintsList.iterator();
		hintsContainer.clear();
		while (iterator.hasNext()) {
			QuestionHintsDo hints = iterator.next();
			int widgetCount=hintsContainer.getWidgetCount();
	        final AddHintsView addHints = new AddHintsView(widgetCount+1,hints.getHintText());
	        addHintsTextArea(addHints);
		}
		if(collectionItemDo.getResource().getType()==1||collectionItemDo.getResource().getType()==7){
			questionAnswerChoiceContainer.clear();
			while (it.hasNext()) {
				QuestionAnswerDo answer = it.next();
				int widgetCount=questionAnswerChoiceContainer.getWidgetCount();
				final AddQuestionAnswerChoice addQuestionAnswer=new AddQuestionAnswerChoice(anserChoiceArray[widgetCount],answer.getAnswerText());
				if(answer.isIsCorrect()){	
					addQuestionAnswer.optionSelectedButton.setStyleName(addWebResourceStyle.answerSelected());
				}else{
					addQuestionAnswer.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
				}
				addQuesetionAnswerOptionTextArea(addQuestionAnswer,widgetCount);
			}
		}else if(collectionItemDo.getResource().getType()==3){
			setMultipleChoiceAnswerFields();
			int answerCount=0;
			while (it.hasNext()) {
				QuestionAnswerDo answer = it.next();
				selectTrueOrFallseCorrectAnswerOption(answerCount,answer.isIsCorrect());
				answerCount++;
			}
		}else{
			setMultipleChoiceAnswerFields();
		}
	}
	
	
	/**
	 * This method checks the FIB question is balanced or not i.e. balanced with open and closing brackets ([ ]) 
	 * 
	 * @param questionTextFIB
	 *                   is a user entered question.
	 *                   
	 * @return the Boolean value.
	 */
	
	private boolean isFIBQuestionBalanced(String questionTextFIB) {
		
		 Stack<Character> st = new Stack<Character>();

	        for(char chr : questionTextFIB.toCharArray())
	        {
	            switch(chr) {

	                case '[':
	                    st.push(chr);
	                    break;

	                case ']':
	                    if(st.isEmpty() || st.pop() != '[') {
	                    	
	                    	return false;
	                    }
	                    break;
	                        
	            }
	        }
	        return st.isEmpty();
	}
	
	
	/**
	 * This method will extract the blank answer (bracketed text) in the entered question.
	 * 
	 * @param questionTextFIB
	 *                 is a user entered question.
	 *                 
	 * @return matchList-FIB answers instance of {@link List} 
	 */
	
	private List<String> getFIBAnswers(String questionTextFIB) {
		List<String> matchList = new ArrayList<String>();
		
		String temp="";
		String ans="";
		for(int i=0;i<questionTextFIB.length();i++)
		{
			if(questionTextFIB.charAt(i)=='[')
			{
				temp="";
				int j=i;
				while((questionTextFIB.charAt(j)!=']'))
				{

					temp=temp+questionTextFIB.charAt(j);
					j=j+1;
				}
			
				if(temp.equalsIgnoreCase("[")){ 
					isAnsweEmpty = true;
				}else{
					isAnsweEmpty = false;
				}
				
				/**
				 * As question text is a raw content, replacing unnecessary spaces. 
				 */
				
				ans = temp.substring(1).replace("&nbsp;", "").trim(); 
				System.out.println("ans ::"+ans);
				if(ans.equalsIgnoreCase("")){
					isAnsweEmpty = true;
				}else{
					isAnsweEmpty = false;
				}
				matchList.add(ans);

			}
		}
		return matchList;
	} 
	
	
	/**
	 * This method is used to replace all brackets ([], so called blanks) to underscores(_ replaced with 7 underscores).
	 * 
	 * @param rawQuest
	 *            is a user entered question.
	 *            
	 * @return rawQuest instance of {@link String}
	 */
	private String getFIBQuestion(String rawQuest) {
		String temp="";
		for(int i=0;i<rawQuest.length();i++)
		{
			if(rawQuest.charAt(i)=='[')
			{
				temp="";
				int j=i;
				while( (rawQuest.charAt(j)!=']'))
				{
					temp=temp+rawQuest.charAt(j);
					j=j+1;
				}
				rawQuest=rawQuest.replace(temp+"]", "_______").trim();
			}
		}
		return rawQuest;
	}
		
	/**
	 * On mouse over panelContentRights will be visible.
	 * 
	 * @param event instance of {@link MouseOverEvent}
	 */
	@UiHandler("lblContentRights")
	public void onMouseOver(MouseOverEvent event){
		panelContentRights.setVisible(true);
	}
	
	/**
	 * On mouse out panelContentRights will not be visible.
	 * 
	 * @param event instance of {@link MouseOutEvent}
	 */
	
	@UiHandler("lblContentRights")
	public void onMouseOut(MouseOutEvent event){
		panelContentRights.setVisible(false);
	}
	
	/**
	 * Opens up Terms of Use pop-up.
	 * 
	 * @param event instance of {@link ClickEvent}
	 */
	
	@UiHandler("termsAndPolicyAnr")
	public void onClickTrems(ClickEvent event){
		Window.enableScrolling(false);
		termsOfUse = new TermsOfUse() {
			@Override
			public void openParentPopup() {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98,false));
			}
		};
		
		termsOfUse.show();
		termsOfUse.setSize("902px", "300px");
		termsOfUse.center();
		termsOfUse.getElement().getStyle().setZIndex(999);
	
	}
	
	/**
	 * Opens up Privacy pop-up.
	 * 
	 * @param event instance of {@link ClickEvent}
	 */
	
	@UiHandler("privacyAnr")
	public void onClickPrivacy(ClickEvent event){
		Window.enableScrolling(false);
		termsAndPolicyVc = new TermsAndPolicyVc(false) {
			@Override
			public void openParentPopup() {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98,false));
			}
		};
		
		termsAndPolicyVc.show();
		termsAndPolicyVc.setSize("902px", "300px");
		termsAndPolicyVc.center();
		termsAndPolicyVc.getElement().getStyle().setZIndex(999);
	
	}
	
	/**
	 * Opens up Copy rights pop-up.
	 * 
	 * @param event instance of {@link ClickEvent}
	 */
	
	@UiHandler("copyRightAnr")
	public void onClickCopyright(ClickEvent event){
		Window.enableScrolling(false);
		copyRightPolicy = new  CopyRightPolicyVc() {
			@Override
			public void openParentPopup() {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98,false));
			}
		};
		
		copyRightPolicy.show();
		copyRightPolicy.setSize("902px", "300px");
		copyRightPolicy.center();
		copyRightPolicy.getElement().getStyle().setZIndex(999);
	}
	@UiHandler("commuGuideLinesAnr")
	public void onClickCommunityGuide(ClickEvent event){
		Window.open("http://support.goorulearning.org/hc/en-us/articles/200688506","_blank",""); 
	
	}
	
	/**
     * Gets the name of the used browser.
     */
     public static native String getBrowserName() /*-{
         return navigator.userAgent.toLowerCase();
     }-*/;
     /*
     */
    /* public static boolean isFirefoxBrowser() {
         return getBrowserName().toLowerCase().contains("firefox");
     }
     public static boolean isIEBrowser() {
         return getBrowserName().toLowerCase().contains("msie");
     }*/
     
}
