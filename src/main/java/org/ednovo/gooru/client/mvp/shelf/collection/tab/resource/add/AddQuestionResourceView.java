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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.content.ProfanityCheckDo;
import org.ednovo.gooru.application.shared.model.content.QuestionAnswerDo;
import org.ednovo.gooru.application.shared.model.content.QuestionHintsDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.content.checkboxSelectedDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.mvp.faq.CopyRightPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsAndPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.search.CenturySkills.AddCenturyPresenter;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionCBundle;
import org.ednovo.gooru.client.uc.AppMultiWordSuggestOracle;
import org.ednovo.gooru.client.uc.AppSuggestBox;
import org.ednovo.gooru.client.uc.BlueButtonUc;
import org.ednovo.gooru.client.uc.CloseLabel;
import org.ednovo.gooru.client.uc.CloseLabelCentury;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.RemoveToolTipUc;
import org.ednovo.gooru.client.uc.StandardsPreferenceOrganizeToolTip;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.uc.tooltip.BrowseStandardsTooltip;
import org.ednovo.gooru.client.uc.tooltip.ToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.ui.TinyMCE;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;

public abstract class AddQuestionResourceView extends Composite implements SelectionHandler<SuggestOracle.Suggestion>{

	public interface AddQuestionResourceViewUiBinder extends UiBinder<Widget, AddQuestionResourceView>{

	}

	public static AddQuestionResourceViewUiBinder uiBinder=GWT.create(AddQuestionResourceViewUiBinder.class);

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	private CollectionItemDo collectionItemDo=null;
	boolean isAnsweEmpty = false;
	@UiField Label centuryDefaultText,depthOfKnowledgeHeader,standardMaxMsg,standardsDefaultText,errorMessageForAnsCheck,errorMessageForHintsCheck,errorMessageForExplanation,addResourceFormTitleChoice,ansChoiceErrMsg,advancedLbl;
	@UiField HTMLEventPanel addQuestionResourceButton,lblContentRights,eHearderIconExplanation,eHearderIconDepthOfKnowledge,eHearderIconStandards,eHearderIconCentury;
	@UiField HTMLPanel questionAnswerChoiceContainer,standardsCont,questionTrueOrFalseAnswerChoiceContainer,advancedContainer,questionHotTextAnswerChoiceContainer;
	@UiField public static Label errorMessageForQuestion;
	@UiField Label questionTypeHeader,questionTypeText,loadingTextLbl,rightsLbl,explanationLabel,questionNameErrorLbl,explainationErrorLbl,depthOfKnowledgeTitle;
	@UiField Anchor addAnswerChoice,addHintsLabel,addExplanationLabel,addDepthOfKnowledgeLabel,addStandardsLabel,addCenturyLabel;


	@UiField Anchor addQuestionImg;
	@UiField HTMLPanel hintsContainer,buttonContainer,questionText,correctText,noLabelText,pnlDepthOfKnowledges;

	@UiField HTMLPanel addQuestImgContainer,panelContentRights,rightsContent,errorContainer;
	@UiField BlueButtonUc addbutton;
	@UiField TinyMCE questionNameTextArea,explainationTextArea;
	@UiField FlowPanel standardContainer,answerchoiceTitleContainer,explanationContainer,centuryPanel,depthOfKnowledgeContainer,centuryContainer;

	@UiField Button cancelButton,browseCentury;
	@UiField CheckBox rightsChkBox;

	@UiField AddQuestionAnswerChoice alphaLetterA,alphaLetterB;
	private CopyRightPolicyVc copyRightPolicy;

	@UiField Image depthOfKnoweldgeToolTip;

	@UiField
	HTMLEventPanel btnStandardsBrowse;

	@UiField UlPanel ulSelectedItems;

	@UiField
	UlPanel standardsDropListValues;

	@UiField InlineLabel agreeText,andText,additionalText,commuGuideLinesAnr, termsAndPolicyAnr,privacyAnr,copyRightAnr;

	@UiField Label charLimitLbl,charLimitExplanation;

	ToolTip toolTip=null;
	private TermsAndPolicyVc termsAndPolicyVc;
	private TermsOfUse termsOfUse;

	String codeID="",code="",label="";

	boolean isSaveButtonClicked=false,isAddBtnClicked=true,isRightsClicked=false,educationalDropDownLblOpen=false;
	private String questionType="MC";
	ArrayList<Integer> depthOfKnowledgesList= new ArrayList<Integer>();
	ArrayList<CodeDo> standardsDo=new ArrayList<CodeDo>();
	Set<CodeDo> deletedStandardsDo=new HashSet<CodeDo>();
	List<Integer> centurySkills=new ArrayList<Integer>();
	private static final String USER_META_ACTIVE_FLAG = "0";
	private String htType=i18n.GL3219_1();

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
	private static final String ERROR_MSG_QUESTION = i18n.GL0310();
	private static final String ERROR_MSG_ANSWER = i18n.GL0311();
	private static final String ERROR_MSG_ANSWER_SELECTED =i18n.GL0312();
	private static final String ERROR_MSG_ATLEAST_SELECTED =i18n.GL0876();
	private static final String ERROR_MSG_HINTS_LENGTH = i18n.GL0877();
	private static final String ERROR_MSG_ANSWER_LENGTH =i18n.GL0878();
	private static final String ERROR_MSG_HTANSWER_LENGTH =i18n.GL4000();
	private static final String ERROR_MSG_EXPLAINATION_LENGTH =i18n.GL0879();
	private static String ERROR_MSG_QUESTION_LENGTH =i18n.GL0880();
	private static final String ERROR_MSG_CHAR_LIMIT=i18n.GL0143();
	private static final String ERROR_MSG_HINTS = i18n.GL2201();

	private static final String ERROR_MSG_FIB_BALANCED=i18n.GL0881();
	private static final String ERROR_MSG_FIB_BLANKS=i18n.GL0882();
	private static final String ERROR_MSG_MULTIPLE_FIB=i18n.GL0883();
	private static final String ERROR_MSG_MARK_AS_BLANK = i18n.GL0884();
	private static final String REGX_PATTERN = "\\[(.*?)\\]";
	private static final String FIB_SEPARATOR = i18n.GL0885();


	private static final String ERROR_MSG_HTHL = i18n.GL3235_1();
	private static final String ERROR_MSG_HTHL_SYNTAX = i18n.GL3236_1();
	private static final String ERROR_MSG_HTHL_SENTENCE = i18n.GL3237_1();

	private static final int ANSWER_CHOICE_HINTS_TEXT_LENGTH =150;
	private static final int QUESTION_TEXT_LENGTH =500;
	private static final int EXPLAINATION_TEXT_LENGTH =500;
	private static final int HT_ANSWER_CHOICE_HINTS_TEXT_LENGTH =500;
	private static final int HT_QUESTION_TEXT_LENGTH =5000;

	private static String DELIMITER_SPACE="[\\s\\xA0]+";

	public static int questionCharcterLimit=0;

	private List<Widget> answerChoicesList=new ArrayList<Widget>();

	@UiField(provided = true)
	AppSuggestBox standardSgstBox,centurySgstBox;

	private AppMultiWordSuggestOracle standardSuggestOracle;
	private AppMultiWordSuggestOracle centurySuggestOracle;

	private SearchDo<CodeDo> standardSearchDo = new SearchDo<CodeDo>();
	private SearchDo<StandardFo> centurySearchDo = new SearchDo<StandardFo>();
	private static final String FLT_CODE_ID = "id";
	List<String> standardPreflist;
	private Map<String, String> standardCodesMap = new HashMap<String, String>();
	Map<String, String> centuryCodesMap = new HashMap<String, String>();
	String courseCode="";
	boolean isEditResource=false;

	String[] anserChoiceArray=new String[]{"A","B","C","D","E"};
	String[] anserChoiceNumArray=new String[]{"1","2","3","4","5","6","7","8","9","10"};
	List<ProfanityCheckDo> profanityList,hintsListForProfanity;
	private boolean isBrowseTooltip =false;

	BrowseStandardsTooltip browseStandardsTooltip;
	private boolean isBrowseStandardsToolTip = false;

	final StandardsPreferenceOrganizeToolTip standardsPreferenceOrganizeToolTip=new StandardsPreferenceOrganizeToolTip();

	PopupPanel centuryPopup=new PopupPanel();
	Map<Long, String> centurySelectedValues = new HashMap<Long, String>();
	AddCenturyPresenter centuryPresenterWidget=AppClientFactory.getInjector().getAddCenturyPresenterWidget();
	ArrayList<String> isValidHintsList = new ArrayList<String>();

	private boolean isCCSSAvailable = false;
	private boolean isNGSSAvailable = false;
	private boolean isTEKSAvailable = false;
	private boolean isCAAvailable = false;

	List<Integer> selectedValues=new ArrayList<>();

	public FolderDo courseObjG;

	List<LiPanelWithClose> collectionLiPanelWithCloseArray = new ArrayList<>();

	String[] standardsTypesArray = new String[]{i18n.GL3379(),i18n.GL3322(),i18n.GL3323(),i18n.GL3324(),i18n.GL3325(),i18n.GL3321()};

	public AddQuestionResourceView(){
		initializeAutoSuggestedBox();
		initWidget(uiBinder.createAndBindUi(this));
		setHeaderAndBodyText("MC");
		CollectionCBundle.INSTANCE.css().ensureInjected();
		isEditResource=false;
		questionTypeHeader.getElement().setId("lblQuestionTypeHeader");
		questionTypeText.getElement().setId("lblQuestionTypeText");
		questionText.getElement().setId("pnlQuestionText");
		questionText.getElement().setInnerHTML(" "+i18n.GL0863());
		questionNameErrorLbl.getElement().setId("errlblQuestionNameErrorLbl");
		addQuestionImg.setText(i18n.GL0860());
		addResourceFormTitleChoice.getElement().setId("lblAddResourceFormTitleChoice");
		addResourceFormTitleChoice.setText(i18n.GL0864());
		correctText.getElement().setId("pnlCorrectText");
		noLabelText.getElement().setId("pnlNoLabelText");
		correctText.getElement().setInnerHTML(i18n.GL0314());
		noLabelText.setVisible(false);
		alphaLetterA.getElement().setId("qacAlphaLetterA");
		alphaLetterA.setLabelName(i18n.GL_GRR_ALPHABET_A());
		alphaLetterB.setLabelName(i18n.GL_GRR_ALPHABET_B());
		alphaLetterB.getElement().setId("qacAlphaLetterB");
		addAnswerChoice.setText(i18n.GL0866());
		explanationLabel.setText(" "+i18n.GL0867());
		explanationLabel.getElement().setId("lblExplanationLabel");
		explanationLabel.getElement().getStyle().setDisplay(Display.INLINE);
		addHintsLabel.setText(i18n.GL3210_1() +i18n.GL_SPL_OPEN_SMALL_BRACKET()+5+i18n.GL3207_1()+i18n.GL_SPL_CLOSE_SMALL_BRACKET());
		addExplanationLabel.setText( i18n.GL3208_1());
		addDepthOfKnowledgeLabel.setText(i18n.GL3209_1());
		addStandardsLabel.setText(i18n.GL0575());
		addCenturyLabel.setText(i18n.GL3199());
		advancedLbl.setText(i18n.GL3096());
		rightsLbl.setText(i18n.GL0869());
		rightsLbl.getElement().setId("lblRightsLbl");
		agreeText.setText(i18n.GL0870());
		agreeText.getElement().setId("lblAgreeText");
		commuGuideLinesAnr.setText(i18n.GL0871()+i18n.GL_GRR_COMMA());
		commuGuideLinesAnr.getElement().setId("lnkCommuGuideLinesAnr");
		termsAndPolicyAnr.setText(i18n.GL0872()+i18n.GL_GRR_COMMA());
		termsAndPolicyAnr.getElement().setId("lnkTermsAndPolicyAnr");
		privacyAnr.setText(" "+i18n.GL0873());
		privacyAnr.getElement().setId("lnkPrivacyAnr");
		andText.setText(" "+i18n.GL_GRR_AND().trim()+" ");
		andText.getElement().setId("lblAndText");
		copyRightAnr.setText(" "+i18n.GL0875().trim());
		copyRightAnr.getElement().setId("lnkCopyRightAnr");
		additionalText.setText(i18n.GL0874());
		additionalText.getElement().setId("lblAdditionalText");

		cancelButton.setText(i18n.GL0142());
		cancelButton.getElement().setAttribute("style", "margin-left:10px;float: none;display: inline-block;");
		addbutton.setText(i18n.GL0590());
		loadingTextLbl.setText(i18n.GL0591().toLowerCase());

		loadingTextLbl.getElement().setId("lblLoadingTextLbl");
		questionNameTextArea.getElement().setId("tinyMCEQuestionNameTextArea");

		explainationTextArea.getElement().setAttribute("maxlength", "400");
		explainationTextArea.getElement().setId("tinyMCEExplainationTextArea");
		addbutton.getElement().setId("btnAdd");
		cancelButton.getElement().setId("btnCancel");
		addQuestionImg.getElement().setId("lnkAddQuestionImg");
		addAnswerChoice.getElement().setId("lnkAnswerChoice");
		addHintsLabel.getElement().setId("lnkHints");
		addExplanationLabel.getElement().setId("lnkExplanation");
		addDepthOfKnowledgeLabel.getElement().setId("lnkDepthofKnowledge");
		addStandardsLabel.getElement().setId("lnkStandards");
		addCenturyLabel.getElement().setId("lnkCentury");
		panelContentRights.getElement().setId("pnlPanelContentRights");
		questionNameTextArea.markAsBlankPanel.setVisible(false);
		loadingTextLbl.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
		buttonContainer.getElement().setId("pnlButtonContainer");
		buttonContainer.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		loadingTextLbl.setVisible(false);
		rightsChkBox.addClickHandler(new rightsChecked());
		rightsChkBox.getElement().setId("chkRights");
		errorMessageForQuestion.getElement().setId("errlblErrorMessageForQuestion");
		addQuestImgContainer.getElement().setId("pnlAddQuestImgContainer");
		answerchoiceTitleContainer.getElement().setId("fpnlAnswerchoiceTitleContainer");
		questionAnswerChoiceContainer.getElement().setId("pnlQuestionAnswerChoiceContainer");
		questionTrueOrFalseAnswerChoiceContainer.getElement().setId("pnlQuestionTrueOrFalseAnswerChoiceContainer");
		errorMessageForAnsCheck.getElement().setId("lblErrorMessageForAnsCheck");
		ansChoiceErrMsg.getElement().setId("lblAnsChoiceErrMsg");
		explanationContainer.getElement().setId("fpnlExplanationContainer");
		explainationErrorLbl.getElement().setId("errlblExplainationErrorLbl");
		hintsContainer.getElement().setId("pnlHintsContainer");
		errorMessageForHintsCheck.getElement().setId("errlblErrorMessageForHintsCheck");
		depthOfKnowledgeHeader.getElement().setId("lblDepthOfKnowledgeHeader");
		depthOfKnoweldgeToolTip.getElement().setId("imgDepthOfKnoweldgeToolTip");
		depthOfKnowledgeTitle.getElement().setId("lblDepthOfKnowledgeTitle");
		standardContainer.getElement().setId("fpnlStandardContainer");
		standardsDefaultText.getElement().setId("lblStandardsDefaultText");
		standardSgstBox.getElement().setId("appSuggestBoxStandardSgstBox");
		standardSgstBox.getElement().setAttribute("style", "box-sizing:content-box;width:65%;height:19px");
		centurySgstBox.getElement().setAttribute("style", "box-sizing:content-box;width:85%;height:19px");
		standardMaxMsg.getElement().setId("lblStandardMaxMsg");
		lblContentRights.getElement().setId("epnlLblContentRights");
		rightsContent.getElement().setId("pnlRightsContent");
		addQuestionResourceButton.getElement().setId("epnlAddQuestionResourceButton");
		eHearderIconExplanation.getElement().setId("eHearderIconExplanation");
		eHearderIconDepthOfKnowledge.getElement().setId("eHearderIconDepthOfKnowledge");
		eHearderIconStandards.getElement().setId("eHearderIconStandards");
		eHearderIconCentury.getElement().setId("eHearderIconCentury");
		eHearderIconExplanation.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconDepthOfKnowledge.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconStandards.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconCentury.addClickHandler(new MinimizePanelsClickHandler());
		advancedContainer.getElement().setId("pnladvancedContainer");
		questionHotTextAnswerChoiceContainer.getElement().setId("pnlQuestionHotTextAnswerChoiceContainer");
		setTextForTheFields();
		errorContainer.setVisible(false);
		errorContainer.add(standardsPreferenceOrganizeToolTip);
		alphaLetterA.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(alphaLetterA.getParent().getElement().getChildNodes().getLength()>3)
				{
					alphaLetterA.ansChoiceDeleteButton.getElement().getStyle().setDisplay(Display.BLOCK);

				}else {

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
		for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
			final AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
			addQuestionAnswerChoice.optionSelectedButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(questionType.equals("MA")){
						selectYesOption(questionAnswerChoiceContainer, addQuestionAnswerChoice);
					}else{
						selectOrDeselectOption(questionAnswerChoiceContainer,addQuestionAnswerChoice);
					}
				}
			});
			addQuestionAnswerChoice.optionNoButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(questionType.equals("MA")){
						selectNoOption(questionAnswerChoiceContainer, addQuestionAnswerChoice);
					}
				}
			});
		}
		setTrueOrFalseFields();
		setCenturyData();
		explanationContainer.setVisible(false);
		addExplanationLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
		addExplanationLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		depthOfKnowledgeContainer.setVisible(false);
		addDepthOfKnowledgeLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
		addDepthOfKnowledgeLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		standardContainer.setVisible(false);
		addStandardsLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
		addStandardsLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		centuryContainer.setVisible(false);
		addCenturyLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
		addCenturyLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
	}
	public AddQuestionResourceView(CollectionItemDo collectionItemDo){
		initializeAutoSuggestedBox();
		initWidget(uiBinder.createAndBindUi(this));
		this.collectionItemDo=collectionItemDo;
		CollectionCBundle.INSTANCE.css().ensureInjected();
		isEditResource=true;
		questionTypeHeader.getElement().setId("lblQuestionTypeHeader");
		questionTypeText.getElement().setId("lblQuestionTypeText");
		questionNameErrorLbl.getElement().setId("errlblQuestionNameErrorLbl");
		questionNameTextArea.getElement().setId("tinyMCEQuestionNameTextArea");
		addQuestionImg.getElement().setId("lnkAddQuestionImg");
		addbutton.setText(i18n.GL0590());
		addbutton.getElement().setId("btnAdd");
		cancelButton.getElement().setAttribute("style", "margin-left:10px;float: none;display: inline-block;");
		loadingTextLbl.setVisible(false);
		loadingTextLbl.setText(i18n.GL0808());
		loadingTextLbl.getElement().setId("lblLoadingTextLbl");
		rightsLbl.setText(i18n.GL0869());
		rightsLbl.getElement().setId("lblRightsLbl");
		agreeText.setText(i18n.GL0870());
		agreeText.getElement().setId("lblAgreeText");
		commuGuideLinesAnr.setText(i18n.GL0871()+i18n.GL_GRR_COMMA());
		commuGuideLinesAnr.getElement().setId("lnkCommuGuideLinesAnr");
		termsAndPolicyAnr.setText(" "+i18n.GL0872()+i18n.GL_GRR_COMMA());
		termsAndPolicyAnr.getElement().setId("lnkTermsAndPolicyAnr");
		privacyAnr.setText(" "+i18n.GL0873());
		privacyAnr.getElement().setId("lnkPrivacyAnr");
		andText.setText(" "+i18n.GL_GRR_AND()+" ");
		andText.getElement().setId("lblAndText");
		copyRightAnr.setText(" "+i18n.GL0875());
		copyRightAnr.getElement().setId("lnkCopyRightAnr");
		additionalText.setText(i18n.GL0874());
		additionalText.getElement().setId("lblAdditionalText");
		cancelButton.setText(i18n.GL0142());
		cancelButton.getElement().setId("btnCancel");
		loadingTextLbl.setText(i18n.GL0808().toLowerCase());
		loadingTextLbl.getElement().setId("lblLoadingTextLbl");
		explanationLabel.setText(" "+i18n.GL0867());
		explanationLabel.getElement().setId("lblExplanationLabel");
		explanationLabel.getElement().getStyle().setDisplay(Display.INLINE);
		addAnswerChoice.setText(i18n.GL0866());
		addAnswerChoice.getElement().setId("lnkAnswerChoice");
		addHintsLabel.setText(i18n.GL3210_1());
		addHintsLabel.getElement().setId("lnkHints");
		addStandardsLabel.setText(i18n.GL0575());
		addStandardsLabel.getElement().setId("lnkStandards");
		addExplanationLabel.setText( i18n.GL3208_1());
		addExplanationLabel.getElement().setId("lnkExplanation");
		addDepthOfKnowledgeLabel.setText(i18n.GL3209_1());
		addDepthOfKnowledgeLabel.getElement().setId("lnkDepthofKnowledge");
		addCenturyLabel.setText(i18n.GL3199());
		addCenturyLabel.getElement().setId("lnkCentury");
		advancedLbl.setText(i18n.GL3096());
		errorMessageForQuestion.getElement().setId("errlblErrorMessageForQuestion");
		addQuestImgContainer.getElement().setId("pnlAddQuestImgContainer");
		answerchoiceTitleContainer.getElement().setId("fpnlAnswerchoiceTitleContainer");
		addResourceFormTitleChoice.getElement().setId("lblAddResourceFormTitleChoice");
		questionAnswerChoiceContainer.getElement().setId("pnlQuestionAnswerChoiceContainer");
		questionTrueOrFalseAnswerChoiceContainer.getElement().setId("pnlQuestionTrueOrFalseAnswerChoiceContainer");
		errorMessageForAnsCheck.getElement().setId("lblErrorMessageForAnsCheck");
		ansChoiceErrMsg.getElement().setId("lblAnsChoiceErrMsg");
		correctText.getElement().setId("pnlCorrectText");
		noLabelText.getElement().setId("pnlNoLabelText");
		alphaLetterA.getElement().setId("qacAlphaLetterA");
		explanationContainer.getElement().setId("fpnlExplanationContainer");
		explainationTextArea.getElement().setId("tinyMCEExplainationTextArea");
		explainationErrorLbl.getElement().setId("errlblExplainationErrorLbl");
		hintsContainer.getElement().setId("pnlHintsContainer");
		errorMessageForHintsCheck.getElement().setId("errlblErrorMessageForHintsCheck");
		depthOfKnowledgeHeader.getElement().setId("lblDepthOfKnowledgeHeader");
		depthOfKnoweldgeToolTip.getElement().setId("imgDepthOfKnoweldgeToolTip");
		depthOfKnowledgeTitle.getElement().setId("lblDepthOfKnowledgeTitle");
		standardContainer.getElement().setId("fpnlStandardContainer");
		standardsDefaultText.getElement().setId("lblStandardsDefaultText");
		standardSgstBox.getElement().setId("appSuggestBoxStandardSgstBox");
		standardSgstBox.getElement().setAttribute("style", "box-sizing:content-box;width:65%;height:19px");
		centurySgstBox.getElement().setAttribute("style", "box-sizing:content-box;width:85%;height:19px");
		standardMaxMsg.getElement().setId("lblStandardMaxMsg");

		lblContentRights.getElement().setId("epnlLblContentRights");
		panelContentRights.getElement().setId("pnlPanelContentRights");
		rightsContent.getElement().setId("pnlRightsContent");
		buttonContainer.getElement().setId("pnlButtonContainer");
		eHearderIconExplanation.getElement().setId("eHearderIconExplanation");
		eHearderIconExplanation.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconDepthOfKnowledge.getElement().setId("eHearderIconDepthOfKnowledge");
		eHearderIconDepthOfKnowledge.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconStandards.getElement().setId("eHearderIconStandards");
		eHearderIconStandards.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconCentury.getElement().setId("eHearderIconCentury");
		eHearderIconCentury.addClickHandler(new MinimizePanelsClickHandler());
		advancedContainer.getElement().setId("pnladvancedContainer");
		addQuestionImg.setText(i18n.GL0860());
		setQuestionTypeStaticTexts();
		rightsChkBox.addClickHandler(new rightsChecked());
		rightsChkBox.getElement().setId("chkRights");
		setTrueOrFalseFields();
		setTextForTheFields();
		errorContainer.setVisible(false);
		errorContainer.add(standardsPreferenceOrganizeToolTip);
		questionHotTextAnswerChoiceContainer.getElement().setId("pnlQuestionHotTextAnswerChoiceContainer");
		setCenturyData();
		explanationContainer.setVisible(false);
		depthOfKnowledgeContainer.setVisible(false);
		hintsContainer.setVisible(false);
		standardContainer.setVisible(false);
		centuryContainer.setVisible(false);
	}
	public void setCenturyData(){
		//This will hide the popup when clicked on the cancel button
		centuryPresenterWidget.getCancelBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hideCenturyPopup();
			}
		});
		//This will hide the popup when clicked on close button
		centuryPresenterWidget.getCloseBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hideCenturyPopup();
			}
		});
		centuryPresenterWidget.getAddButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				centurySelectedValues.clear();
				centurySelectedValues.putAll(centuryPresenterWidget.getSelectedValues());
				centuryPanel.clear();
				if(centurySelectedValues!=null && centurySelectedValues.size()>0){
					for (Map.Entry<Long, String> entry : centurySelectedValues.entrySet()){
						CodeDo codeObjStandard=new CodeDo();
						codeObjStandard.setCodeId(Integer.parseInt(entry.getKey()+""));
						codeObjStandard.setCode(entry.getValue());
						/*	standardsDo.add(codeObjStandard);
						 */							centuryPanel.add(create21CenturyLabel(entry.getValue(),entry.getKey()+"",""));
					}
				}
				hideCenturyPopup();
			}
		});
	}

	protected void hideDropDown(NativePreviewEvent event) {
		if(event.getTypeInt()==Event.ONCLICK){
    		Event nativeEvent = Event.as(event.getNativeEvent());
        	boolean target=eventTargetsStandardPopup(nativeEvent);
        	if(!target){
        		standardsDropListValues.getElement().removeAttribute("style");
        	}
    	}
	}

	private boolean eventTargetsStandardPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			return standardsDropListValues.getElement().isOrHasChild(Element.as(target))||standardsDropListValues.getElement().isOrHasChild(Element.as(target));
		}
		return false;
	}
	/**
	 * new label is created for the 21 century which needs to be added
	 *
	 * @param standardCode
	 *            update standard code
	 * @return instance of {@link DownToolTipWidgetUc}
	 */
	public DownToolTipWidgetUc create21CenturyLabel(final String centuryCode, final String id, String description) {
		CloseLabelCentury closeLabel = new CloseLabelCentury(centuryCode) {
			@Override
			public void onCloseLabelClick(ClickEvent event) {
				for (Map.Entry<Long, String> entry : centurySelectedValues.entrySet()){
					if(id.equalsIgnoreCase(String.valueOf(entry.getKey()))){
						int idVal=Integer.parseInt(id);
						AppClientFactory.getInjector().getResourceService().deleteTaxonomyResource(collectionItemDo.getResource().getGooruOid(),idVal, new SimpleAsyncCallback<Void>() {
							@Override
							public void onSuccess(Void result) {
								centurySelectedValues.remove(Long.parseLong(id));
							}
						});
						this.getParent().removeFromParent();
						return;
					}
				}
			}
		};
		DownToolTipWidgetUc downToolTipWidgetUc=new DownToolTipWidgetUc(closeLabel, description);
		downToolTipWidgetUc.getElement().setId(id);
		return downToolTipWidgetUc;
	}
	/**
	 * This method will hide the century popup
	 */
	public void hideCenturyPopup(){
		centuryPopup.hide();
	}
	public void initializeAutoSuggestedBox(){
		standardSuggestOracle = new AppMultiWordSuggestOracle(true);
		centurySuggestOracle= new AppMultiWordSuggestOracle(true);
		standardSearchDo.setPageSize(10);
		standardSgstBox = new AppSuggestBox(standardSuggestOracle) {
			@Override
			public void keyAction(String text,KeyUpEvent event) {
				text=text.toUpperCase();
				errorContainer.setVisible(false);
				standardSearchDo.setSearchResults(null);
				boolean standardsPrefDisplayPopup = false;
				standardSgstBox.hideSuggestionList();
				if(!courseCode.isEmpty()) {
					Map<String,String> filters = new HashMap<String, String>();
					filters.put(FLT_CODE_ID,courseCode);
					standardSearchDo.setFilters(filters);
				}
				standardSearchDo.setQuery(text);
				if (text != null && text.trim().length() > 0) {
					errorContainer.setVisible(false);
					if(standardPreflist!=null){
						for(int count=0; count<standardPreflist.size();count++) {
							if(text.contains(standardPreflist.get(count))) {
								standardsPrefDisplayPopup = true;
								break;
							} else {
								standardsPrefDisplayPopup = false;
							}
						}
					}
					if(standardsPrefDisplayPopup){
						errorContainer.setVisible(false);
						AppClientFactory.getInjector().getSearchService().getSuggestStandardByFilterCourseId(standardSearchDo, new SimpleAsyncCallback<SearchDo<CodeDo>>() {
							@Override
							public void onSuccess(SearchDo<CodeDo> result) {
								setStandardSuggestions(result);

							}
						});

						standardSgstBox.showSuggestionList();
					}
					else{
						errorContainer.setVisible(true);
						standardSgstBox.hideSuggestionList();
						standardSuggestOracle.clear();
					}
				}
			}
			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler) {
				return null;
			}
		};
		standardSgstBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				Map<String, String> standard = new HashMap<>();

				standard.put("selectedCodeId", String.valueOf(getCodeIdByCode(standardSgstBox.getValue(), standardSearchDo.getSearchResults())));
				standard.put("selectedCodeVal", standardSgstBox.getValue());
				standard.put("selectedDifferenceId", String.valueOf(3));
				standard.put("selectedCodeDesc", standardSgstBox.getValue());
				displaySelectedStandardsOne(standard);
				standardSgstBox.setText("");
				standardSuggestOracle.clear();

			}
		});
		BlurHandler blurHandler=new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if(standardsPreferenceOrganizeToolTip.isShowing()){
					errorContainer.setVisible(false);
				}
			}
		};
		standardSgstBox.addDomHandler(blurHandler, BlurEvent.getType());
		centurySgstBox=new AppSuggestBox(centurySuggestOracle) {

			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler) {
				return null;
			}

			@Override
			public void keyAction(String text, KeyUpEvent event) {
				text=text.toUpperCase();
				centurySearchDo.setSearchResults(null);
				centurySearchDo.setQuery(text);
				if (text != null && text.trim().length() > 0) {
					AppClientFactory.getInjector().getSearchService().getSuggestCenturyByQuery(centurySearchDo, new AsyncCallback<SearchDo<StandardFo>>() {

						@Override
						public void onSuccess(SearchDo<StandardFo> result) {
							setCenturySuggestions(result);
						}

						@Override
						public void onFailure(Throwable caught) {

						}
					});
					centurySgstBox.showSuggestionList();
				}
			}
		};
		centurySgstBox.getElement().getStyle().setFontSize(12, Unit.PX);
		centurySgstBox.getTextBox().getElement().setAttribute("placeholder", i18n.GL3122_1());

		BlurHandler blurHandlerCentury=new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if(standardsPreferenceOrganizeToolTip.isShowing()){
					errorContainer.setVisible(false);
				}
			}
		};

		centurySgstBox.addDomHandler(blurHandlerCentury, BlurEvent.getType());
		centurySgstBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				addCentury(centurySgstBox.getValue(), getCodeIdByCodeCentury(centurySgstBox.getValue(), centurySearchDo.getSearchResults()));
				centurySgstBox.setText("");
				centurySuggestOracle.clear();

			}
		});
	}
	public void setTextForTheFields(){
		standardsDefaultText.setText(i18n.GL1682());
		centuryDefaultText.setText(i18n.GL3199());
		depthOfKnowledgeHeader.setText(i18n.GL1693());
		depthOfKnoweldgeToolTip.setUrl("images/mos/questionmark.png");
		depthOfKnoweldgeToolTip.setTitle("Question Mark");
		addQuestionResourceButton.getElement().setId("epnlAddQuestionResourceButton");
		standardContainer.getElement().setId("standardsContainerBswn");
		standardsCont.getElement().setAttribute("style", "position:relative;");

		String value = StringUtil.generateMessage(i18n.GL2103(), "500");
		charLimitExplanation.setText(value);
		StringUtil.setAttributes(charLimitExplanation.getElement(), "charLimitExplanation", value, value);

		addClickEventsForCheckBox();


		Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				hideDropDown(event);
			}
		});


		btnStandardsBrowse.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getAddStandards();
				if (!standardsDropListValues.getElement().getAttribute("style").equalsIgnoreCase("display:block;top:0;left:33.5em;color:#515151;")) {
					standardsDropListValues.getElement().setAttribute("style", "display:block;top:0;left:33.5em;color:#515151;");
				} else {
					standardsDropListValues.getElement().removeAttribute("style");
				}
			}
		});
	}

	private static String getCodeIdByCode(String code, List<CodeDo> codes) {
		if (codes != null) {
			for (CodeDo codeDo : codes) {
				if (code.equals(codeDo.getCode())) {
					return codeDo.getCodeId() + "";
				}
			}
		}
		return null;
	}
	private static String getCodeIdByCodeCentury(String code, List<StandardFo> codes) {
		if (codes != null) {
			for (StandardFo codeDo : codes) {
				if (code.equals(codeDo.getLabel())) {
					return codeDo.getCodeId() + "";
				}
			}
		}
		return null;
	}

	public void addCentury(String centuryTag, String id) {
		if (centuryTag != null && !centuryTag.isEmpty()) {
			String codeIdVal = getCodeIdByCodeCentury(centurySgstBox.getValue(), centurySearchDo.getSearchResults());
			CodeDo codeObjStandard=new CodeDo();
			codeObjStandard.setCodeId(Integer.parseInt(codeIdVal));
			codeObjStandard.setCode(centurySgstBox.getValue());
			centurySelectedValues.put(Long.parseLong(codeIdVal),centurySgstBox.getValue());
			centuryPanel.add(create21CenturyLabel(centuryTag, id, centuryCodesMap.get(id)));
		}
	}
	/**
	 * new label is created for the standard which needs to be added
	 *
	 * @param standardCode
	 *            update standard code
	 * @return instance of {@link DownToolTipWidgetUc}
	 */
	public DownToolTipWidgetUc createStandardLabel(final String standardCode, final String id, String description) {
		CloseLabel closeLabel = new CloseLabel(standardCode) {

			@Override
			public void onCloseLabelClick(ClickEvent event) {
				for(final CodeDo codeObj:standardsDo){
					if(isEditResource){
						if(codeObj.getCodeId()==Integer.parseInt(id)){
							AppClientFactory.getInjector().getResourceService().deleteTaxonomyResource(collectionItemDo.getResource().getGooruOid(), codeObj.getCodeId(), new SimpleAsyncCallback<Void>() {

								@Override
								public void onSuccess(Void result) {
									CodeDo deletedObj=new CodeDo();
									deletedObj.setCodeId(codeObj.getCodeId());
									deletedStandardsDo.add(deletedObj);
									standardsDo.remove(codeObj);
								}
							});
						}
					}else
					{
						if(codeObj.getCodeId()==Integer.parseInt(id)){
							standardsDo.remove(codeObj);
						}
					}
				}
				this.getParent().removeFromParent();
			}
		};
		return new DownToolTipWidgetUc(closeLabel, description);
	}
	public void standardMaxShow() {
		standardSgstBox.addStyleName(CollectionCBundle.INSTANCE.css().standardTxtBox());
		standardMaxMsg.setStyleName(CollectionCBundle.INSTANCE.css().standardMax());
		//standardsPanel.addStyleName(CollectionCBundle.INSTANCE.css().floatLeftNeeded());
		new FadeInAndOut(standardMaxMsg.getElement(), 5000, 5000);
	}
	public void setCenturySuggestions(SearchDo<StandardFo> centurySearchDo) {
		centurySuggestOracle.clear();
		this.centurySearchDo = centurySearchDo;
		if (this.centurySearchDo.getSearchResults() != null) {
			List<String> sources = getAddedCentury(centuryPanel);
			for (StandardFo code : centurySearchDo.getSearchResults()) {
				if (!sources.contains(code.getLabel())) {
					centurySuggestOracle.add(code.getLabel());
				}
				centuryCodesMap.put(code.getCodeId() + "", code.getLabel());
			}
		}
		centurySgstBox.showSuggestionList();
	}

	/**
	 * get the standards are added for collection
	 *
	 * @param flowPanel
	 *            having all added standards label
	 * @return standards text in list which are added for the collection
	 */
	private List<String> getAddedCentury(FlowPanel flowPanel) {
		List<String> suggestions = new ArrayList<String>();
		for (Widget widget : flowPanel) {
			if (widget instanceof DownToolTipWidgetUc) {
				suggestions.add(((CloseLabelCentury) ((DownToolTipWidgetUc) widget).getWidget()).getSourceText());
			}
		}
		return suggestions;
	}
	/**
	 * Sets the text for type of question selected based on question type number.
	 */

	public void setQuestionTypeStaticTexts(){
		int questionTypeNum=collectionItemDo.getResource().getType() !=null ? collectionItemDo.getResource().getType() : collectionItemDo.getQuestionInfo().getType();
		if(questionTypeNum==1){
			setQuestionType("MC");
			showMulipleChoice();
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
			showMulipleAnswerChoiceOptions();
		}else if(questionTypeNum==8){
			setQuestionType("HT_HL");
			showHotTextQuestion();
		}else if(questionTypeNum==9){
			setQuestionType("HT_RO");
			showHotTextQuestion();
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
	public void refreshHotTextOptionNames(){
		for(int i=0;i<questionHotTextAnswerChoiceContainer.getWidgetCount();i++){
			AddHotTextQuestionAnswerChoice addQuestionAnswerChoice=(AddHotTextQuestionAnswerChoice)questionHotTextAnswerChoiceContainer.getWidget(i);
			addQuestionAnswerChoice.setLabelName(anserChoiceNumArray[i]);
		}
		if(questionHotTextAnswerChoiceContainer.getWidgetCount()<5){
			addAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
		}
	}

	public void selectOrDeselectOption(HTMLPanel questionAnswerChoiceContainer,AddQuestionAnswerChoice addQuestionAnswerChoice){

		if(questionType.equals("T/F")||questionType.equals("MC")){
			removeSelectedOption(questionAnswerChoiceContainer,addQuestionAnswerChoice);
		}
		clearErrorMessageForAnswer();
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
	public void selectYesOption(HTMLPanel questionAnswerChoiceContainer,AddQuestionAnswerChoice addQuestionAnswerChoice){

		String styleSelectedVal = "";
		String styleSelectedValcomparer = "";

		List<String> items = Arrays.asList(addQuestionAnswerChoice.optionSelectedButton.getStyleName().split("\\s*-\\s*"));

		if (!items.isEmpty()) {
			styleSelectedVal = items.get(items.size()-1);
		}

		List<String> itemsComparer = Arrays.asList(addWebResourceStyle.answerDeselected().split("\\s*-\\s*"));

		if (!itemsComparer.isEmpty()) {
			styleSelectedValcomparer = itemsComparer.get(itemsComparer.size()-1);
		}

		if(styleSelectedVal.equals(styleSelectedValcomparer)){
			addQuestionAnswerChoice.optionSelectedButton.setStyleName(addWebResourceStyle.answerSelected());
			addQuestionAnswerChoice.optionNoButton.setStyleName(addWebResourceStyle.answerDeselected());
		}



	}
	public void selectNoOption(HTMLPanel questionAnswerChoiceContainer,AddQuestionAnswerChoice addQuestionAnswerChoice){
		if(addQuestionAnswerChoice.optionNoButton.getStyleName().equals(addWebResourceStyle.answerDeselected())){
			addQuestionAnswerChoice.optionNoButton.setStyleName(addWebResourceStyle.answerSelected());
			addQuestionAnswerChoice.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
		}
	}

	public void addStyleToBody(AddQuestionAnswerChoice addQuestionAnswerChoice){
	}
	public void removeStyleToBody(AddQuestionAnswerChoice addQuestionAnswerChoice){
	}
	@Override
	public void onLoad() {
		super.onLoad();
	}

	@UiHandler("cancelButton")
	public void clickedOnCancelButton(ClickEvent clickEvent){

		resetToHints();
		if(isEditResource){
			if(deletedStandardsDo.size()>0){
				AppClientFactory.getInjector().getResourceService().UpdateResourceTaxonomy(collectionItemDo.getResource().getGooruOid(), deletedStandardsDo, new SimpleAsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						deletedStandardsDo.clear();
					}
				});
			}
		}
		hidePopup();
	}
	public void setTrueOrFalseFields(){
		questionTrueOrFalseAnswerChoiceContainer.clear();
		for(int i=0;i<2;i++){
			int widgetCount=questionTrueOrFalseAnswerChoiceContainer.getWidgetCount();
			final AddQuestionAnswerChoice addQuestionAnswer=new AddQuestionAnswerChoice(anserChoiceArray[widgetCount]);
			addQuestionAnswer.optionNoButton.setStyleName(addWebResourceStyle.answerDeselected());
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
		final AddQuestionAnswerChoice addQuestionAnswer=(AddQuestionAnswerChoice)questionTrueOrFalseAnswerChoiceContainer.getWidget(answerIndex);
		if(isAnswerCorrect){
			addQuestionAnswer.optionSelectedButton.setStyleName(addWebResourceStyle.answerSelected());
		}else{
			addQuestionAnswer.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
		}
	}
	public void setMultipleChoiceAnswerFields(){
		questionAnswerChoiceContainer.clear();
		for(int i=0;i<2;i++){
			int widgetCount=questionAnswerChoiceContainer.getWidgetCount();
			final AddQuestionAnswerChoice addQuestionAnswer=new AddQuestionAnswerChoice(anserChoiceArray[widgetCount]);
			addQuestionAnswer.optionNoButton.setStyleName(addWebResourceStyle.answerDeselected());
			addQuestionAnswer.optionSelectedButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(questionType.equals("MA")){
						selectYesOption(questionAnswerChoiceContainer, addQuestionAnswer);
					}else{
						selectOrDeselectOption(questionAnswerChoiceContainer,addQuestionAnswer);
					}
				}
			});
			addQuestionAnswer.optionNoButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(questionType.equals("MA")){
						selectNoOption(questionAnswerChoiceContainer, addQuestionAnswer);
					}
				}
			});
			questionAnswerChoiceContainer.add(addQuestionAnswer);
		}
	}


	public void setHotTextROFields(){
		questionHotTextAnswerChoiceContainer.clear();
		for(int i=0;i<2;i++){
			int widgetCount=questionHotTextAnswerChoiceContainer.getWidgetCount();
			final AddHotTextQuestionAnswerChoice addQuestionAnswer=new AddHotTextQuestionAnswerChoice(anserChoiceNumArray[widgetCount]);

			if(i==0){
				addQuestionAnswer.setHeadLabelFields(true);
				addQuestionAnswer.reorderRDButtonClick();
			}else{
				addQuestionAnswer.setHeadLabelFields(false);
			}

			questionHotTextAnswerChoiceContainer.add(addQuestionAnswer);
		}

		addAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);

	}

	public void setHotTextHLFields(){
		questionHotTextAnswerChoiceContainer.clear();
		int widgetCount=questionHotTextAnswerChoiceContainer.getWidgetCount();
		final AddHotTextQuestionAnswerChoice addQuestionAnswer=new AddHotTextQuestionAnswerChoice(anserChoiceNumArray[widgetCount]);
		setHotTextAnswers(addQuestionAnswer);
		addQuestionAnswer.setHeadLabelFields(true);
		questionHotTextAnswerChoiceContainer.add(addQuestionAnswer);
		addQuestionAnswer.highlightRDButtonClick();
		addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);

	}


	public void setHotTextAnswers(final AddHotTextQuestionAnswerChoice addQuestionAnswer){

		/*
    	if(questionType.equalsIgnoreCase("HT_RO")){
    		addQuestionAnswer.reorderRDButtonClick();
			addAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
    	}else if(questionType.equalsIgnoreCase("HT_HL")){
    		int widgetCount=questionHotTextAnswerChoiceContainer.getWidgetCount();
			for(int i=widgetCount-1;i>0;i--){
				questionHotTextAnswerChoiceContainer.remove(i);
			}
			addQuestionAnswer.highlightRDButtonClick();
			addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
    	}*/

		addQuestionAnswer.wordRDButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				addQuestionAnswer.wordRDButtonClick();
				htType=i18n.GL3219_1();
			}
		});
		addQuestionAnswer.sentenceRDButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				addQuestionAnswer.sentenceRDButtonClick();
				htType=i18n.GL3220_1();
			}
		});
	}

	@UiHandler("addAnswerChoice")
	public void clickedOnAddChoiceButton(ClickEvent clickEvent){

		if(questionType.equals("HT_HL")|| questionType.equals("HT_RO") ){
			int widgetCount=questionHotTextAnswerChoiceContainer.getWidgetCount();
			final AddHotTextQuestionAnswerChoice addHotTextQuestionAnswer=new AddHotTextQuestionAnswerChoice(anserChoiceNumArray[widgetCount]);
			addHotTextQuestionAnswer.setHeadLabelFields(false);
			addHotTextQuesetionAnswerOptionTextArea(addHotTextQuestionAnswer,widgetCount);
		}else{

			int widgetCount=questionAnswerChoiceContainer.getWidgetCount();
			final AddQuestionAnswerChoice addQuestionAnswer=new AddQuestionAnswerChoice(anserChoiceArray[widgetCount]);
			addQuestionAnswer.optionNoButton.setStyleName(addWebResourceStyle.answerDeselected());
			addQuestionAnswer.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
			if(questionType.equals("MA")){
				addQuestionAnswer.showAnswerChoicesForMultipleAnswers();
			}else{
				addQuestionAnswer.showAnswerChoicesForOthers();
			}
			addQuesetionAnswerOptionTextArea(addQuestionAnswer,widgetCount);
		}
	}
	private void addQuesetionAnswerOptionTextArea(final AddQuestionAnswerChoice addQuestionAnswer,int widgetCount){

		answerChoicesList.add(addQuestionAnswer);
		addQuestionAnswer.optionSelectedButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(questionType.equals("MA")){
					selectYesOption(questionAnswerChoiceContainer, addQuestionAnswer);
				}else{
					selectOrDeselectOption(questionAnswerChoiceContainer,addQuestionAnswer);
				}
			}
		});
		addQuestionAnswer.optionNoButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(questionType.equals("MA")){
					selectNoOption(questionAnswerChoiceContainer, addQuestionAnswer);
				}
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

	private void addHotTextQuesetionAnswerOptionTextArea(final AddHotTextQuestionAnswerChoice addQuestionAnswer,int widgetCount){

		answerChoicesList.add(addQuestionAnswer);
		if(widgetCount>1){
			addQuestionAnswer.ansChoiceDeleteButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(addQuestionAnswer.getParent().getElement().getChildNodes().getLength()>1)
					{
						addQuestionAnswer.removeFromParent();
						refreshHotTextOptionNames();
						removeToolTip.hide();
						isAddBtnClicked=true;
					}
				}
			});
			addQuestionAnswer.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(addQuestionAnswer.getParent().getElement().getChildNodes().getLength()>1)
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
		questionHotTextAnswerChoiceContainer.add(addQuestionAnswer);
		if(questionHotTextAnswerChoiceContainer.getWidgetCount()>=5){
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
	@UiHandler("addHintsLabel")
	public void clickOnHintsLabel(ClickEvent event){
		Window.enableScrolling(false);
		hintsContainer.setVisible(true);
		int widgetCount=hintsContainer.getWidgetCount();
		addHintsLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
		addHintsLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		addHintsLabel.setText(i18n.GL3210_1()+i18n.GL_SPL_OPEN_SMALL_BRACKET()+(4-widgetCount)+i18n.GL3207_1()+i18n.GL_SPL_CLOSE_SMALL_BRACKET());

		final AddHintsView addHints = new AddHintsView(widgetCount+1);
		addHintsTextArea(addHints);
	}

	@UiHandler("addExplanationLabel")
	public void clickOnExplanationLabel(ClickEvent event){
		addExplanationLabel.setVisible(false);
		addExplanationLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
		addExplanationLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		explanationContainer.setVisible(true);
		explainationTextArea.showTinyMceToolBar();

	}

	@UiHandler("addDepthOfKnowledgeLabel")
	public void clickOnaddDepthOfKnowledgeLabel(ClickEvent event){
		addDepthOfKnowledgeLabel.setVisible(false);
		addDepthOfKnowledgeLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
		addDepthOfKnowledgeLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		depthOfKnowledgeContainer.setVisible(true);
	}

	@UiHandler("addStandardsLabel")
	public void clickOnaddStandardsLabel(ClickEvent event){
		addStandardsLabel.setVisible(false);
		addStandardsLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
		addStandardsLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		standardContainer.setVisible(true);
	}
	@UiHandler("addCenturyLabel")
	public void clickOnaddCenturyLabel(ClickEvent event){
		addCenturyLabel.setVisible(false);
		addCenturyLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
		addCenturyLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		centuryContainer.setVisible(true);
	}

	void addHintsTextArea(final AddHintsView addHints){
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

		addHints.eHearderIconHint.addClickHandler(new MinimizePanelsClickHandler());

	}



	protected void refreshHintNumber()
	{
		int hintWidgetsCount=hintsContainer.getWidgetCount();
		for(int i=0 ; i<hintWidgetsCount;i++)
		{
			Widget childWidget=hintsContainer.getWidget(i);
			AddHintsView addHints=(AddHintsView)childWidget;
			addHints.hintNumLbl.setText(""+(i+1));
			if(i==0){
				addHints.eHearderIconHint.setVisible(true);
				addHints.hintsTextLblVal.setText(i18n.GL0859());
			}

		}
		if(hintsContainer.getWidgetCount()<5){
			addHintsLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
			addHintsLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
			addHintsLabel.setText(i18n.GL3210_1()+i18n.GL_SPL_OPEN_SMALL_BRACKET()+(5-hintWidgetsCount)+i18n.GL3207_1()+i18n.GL_SPL_CLOSE_SMALL_BRACKET());
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
	private class onBrowseStandardsClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			callBrowseStandards();
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
					showErrorMessageForAnswer(ERROR_MSG_ANSWER_SELECTED);
					isAddBtnClicked=true;
				}

			}
		}else if(getQuestionType().equalsIgnoreCase("MC")){
			if (isAnswerChoiceEmpty(questionAnswerChoiceContainer)) {
				fieldValidationCheck = false;
				isAddBtnClicked=true;
			}
			if(!isHintsAdded(hintsContainer)){
				if (!isAnswerChoiceSelected(questionAnswerChoiceContainer)) {
					String errorMessage=getQuestionType().equalsIgnoreCase("MA")?ERROR_MSG_ATLEAST_SELECTED:ERROR_MSG_ANSWER_SELECTED;
					showErrorMessageForAnswer(errorMessage);
					fieldValidationCheck = false;
					isAddBtnClicked=true;
				}
			}
		} else if(getQuestionType().equalsIgnoreCase("MA")){
			if (isAnswerChoiceEmpty(questionAnswerChoiceContainer)) {
				fieldValidationCheck = false;
				isAddBtnClicked=true;
			}

			if(!isHintsAdded(hintsContainer)){
				if (!isYesOrNoChoiceSelected(questionAnswerChoiceContainer)) {
					String errorMessage=getQuestionType().equalsIgnoreCase("MA")?ERROR_MSG_ATLEAST_SELECTED:ERROR_MSG_ANSWER_SELECTED;
					showErrorMessageForAnswer(errorMessage);
					fieldValidationCheck = false;
					isAddBtnClicked=true;
				}
			}
		} else if(getQuestionType().equalsIgnoreCase("HT_HL") ||getQuestionType().equalsIgnoreCase("HT_RO")){
			if (isHotTextAnswerChoiceEmpty(questionHotTextAnswerChoiceContainer)) {
				fieldValidationCheck = false;
				isAddBtnClicked=true;
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
									clearErrorQuestionMessage();
									String questionNameText = questionNameTextArea.getText().replaceAll("\\<.*?>","");
									String questionName=questionNameText.replaceAll("&nbsp;", " ").trim();
									if(questionName==null||questionName.trim().equals("")){
										showErrorQuestionMessage(ERROR_MSG_QUESTION);
										fieldValidationStaus=false;
										isQuestEnteredFlag=false;
										isAddBtnClicked=true;
									}
									//This regex is used to get text count with out html tags
									int questionMaxLength;
									if(questionType=="HT_HL" || questionType=="HT_RO"){
										questionMaxLength=HT_QUESTION_TEXT_LENGTH;
										ERROR_MSG_QUESTION_LENGTH=i18n.GL4001();
									}else{
										questionMaxLength=QUESTION_TEXT_LENGTH;
										ERROR_MSG_QUESTION_LENGTH=i18n.GL0880();
									}
									errorMessageForExplanation.setText("");
									if(questionName.length()>questionMaxLength){
										showErrorQuestionMessage(ERROR_MSG_QUESTION_LENGTH);
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
										clearErrorMessageForAnswer();
										if (isAnswerChoiceEmpty(questionTrueOrFalseAnswerChoiceContainer)) {
											fieldValidationStaus = false;
											isAddBtnClicked=true;
										}else{

											if(!isHintsAdded(hintsContainer)){
												if (!isAnswerChoiceSelected(questionTrueOrFalseAnswerChoiceContainer)) {
													fieldValidationStaus = false;
													showErrorMessageForAnswer(ERROR_MSG_ANSWER_SELECTED);
													isAddBtnClicked=true;
												}else{
													profanityCheckForHints(fieldValidationStaus,answersListFIB,mediaFileName);
												}
											}
										}
									}

									else if (fieldValidationStaus && getQuestionType().equalsIgnoreCase("MC")) {
										clearErrorMessageForAnswer();
										if (isAnswerChoiceEmpty(questionAnswerChoiceContainer)) {
											fieldValidationStaus = false;
											isAddBtnClicked=true;
										}else{
											if(!isHintsAdded(hintsContainer)){
												if (!isAnswerChoiceSelected(questionAnswerChoiceContainer)) {
													String errorMessage=getQuestionType().equalsIgnoreCase("MA")?ERROR_MSG_ATLEAST_SELECTED:ERROR_MSG_ANSWER_SELECTED;
													showErrorMessageForAnswer(errorMessage);
													fieldValidationStaus = false;
													isAddBtnClicked=true;
												}else{
													isProfanityCheckForAnswerChoice(fieldValidationStaus,answersListFIB,mediaFileName);
												}
											}
										}
									}

									else if (fieldValidationStaus && getQuestionType().equalsIgnoreCase("MA")) {
										clearErrorMessageForAnswer();
										if (isAnswerChoiceEmpty(questionAnswerChoiceContainer)) {
											fieldValidationStaus = false;
											isAddBtnClicked=true;
										}else{
											if(!isHintsAdded(hintsContainer)){
												if (!isYesOrNoChoiceSelected(questionAnswerChoiceContainer)) {
													String errorMessage=getQuestionType().equalsIgnoreCase("MA")?ERROR_MSG_ATLEAST_SELECTED:ERROR_MSG_ANSWER_SELECTED;
													showErrorMessageForAnswer(errorMessage);

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
											isAddBtnClicked=true;
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
									else if(fieldValidationStaus && getQuestionType().equalsIgnoreCase("HT_HL") || fieldValidationStaus && getQuestionType().equalsIgnoreCase("HT_RO")){

										clearErrorMessageForAnswer();
										if (isHotTextAnswerChoiceEmpty(questionHotTextAnswerChoiceContainer)) {
											fieldValidationStaus = false;
											isAddBtnClicked=true;
										}else{

											if(!isHintsAdded(hintsContainer)){
												isProfanityCheckForAnswerChoice(fieldValidationStaus,answersListFIB,mediaFileName);

											}else{
												profanityCheckForHints(fieldValidationStaus,answersListFIB,mediaFileName);
											}
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
	 *
	 * @function showErrorMessageForAnswer
	 *
	 * @created_date : 16-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param errMessage
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void showErrorMessageForAnswer(String errMessage){

		ansChoiceErrMsg.setText(errMessage);
		StringUtil.setAttributes(ansChoiceErrMsg.getElement(), "lblErrorMessageForAnswer", errMessage, errMessage);

		questionNameTextArea.getElement().addClassName("errorBorderMessage");
	}
	/**
	 *
	 * @function clearErrorMessageForAnswer
	 *
	 * @created_date : 16-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void clearErrorMessageForAnswer(){
		errorMessageForQuestion.setText("");
		StringUtil.setAttributes(errorMessageForQuestion.getElement(), "errlblErrorMessageForQuestion", null, null);

		questionNameTextArea.getElement().removeClassName("errorBorderMessage");
	}
	/**
	 *
	 * @function showErrorQuestionMessage
	 *
	 * @created_date : 16-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param errMessage
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void showErrorQuestionMessage(String errMessage){

		errorMessageForQuestion.setText(errMessage);
		StringUtil.setAttributes(errorMessageForQuestion.getElement(), "errlblErrorMessageForQuestion", errMessage, errMessage);

		questionNameTextArea.getElement().addClassName("errorBorderMessage");
	}
	/**
	 *
	 * @function clearErrorQuestionMessage
	 *
	 * @created_date : 16-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void clearErrorQuestionMessage(){
		errorMessageForQuestion.setText("");
		StringUtil.setAttributes(errorMessageForQuestion.getElement(), "errlblErrorMessageForQuestion", null, null);

		questionNameTextArea.getElement().removeClassName("errorBorderMessage");
	}

	/**
	 * If all validations successful, question is added to the collection.
	 */
	public void addFunctionality(boolean fieldValidationStaus,List<String> answersListFIB,String mediaFileName){
		standardsDo.clear();
		getSelectedStandards();
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
			}else if(getQuestionType().equalsIgnoreCase("HT_RO")){
				for(int i=0;i<questionHotTextAnswerChoiceContainer.getWidgetCount();i++)
				{
					QuestionAnswerDo questionAnswerDo = new QuestionAnswerDo();
					AddHotTextQuestionAnswerChoice addQuestionAnswerChoice=(AddHotTextQuestionAnswerChoice)questionHotTextAnswerChoiceContainer.getWidget(i);
					questionAnswerDo.setAnswerText(addQuestionAnswerChoice.answerTextBox.getRawContent());
					//questionAnswerDo.setAnswerText(addQuestionAnswerChoice.answerTextBox.getRawContent());
					questionAnswerDo.setAnswerType("text");
					questionAnswerDo.setSequence(i+1);
					questionAnswerDo.setIsCorrect(true);
					enteredAnswers.add(questionAnswerDo);
				}
			}else if(getQuestionType().equalsIgnoreCase("HT_HL")){
				QuestionAnswerDo questionAnswerDo = new QuestionAnswerDo();
				AddHotTextQuestionAnswerChoice addQuestionAnswerChoice=(AddHotTextQuestionAnswerChoice)questionHotTextAnswerChoiceContainer.getWidget(0);
				questionAnswerDo.setAnswerText(StringUtil.getHotTextHiglightText(addQuestionAnswerChoice.highlightTextArea.getRawContent()));
				questionAnswerDo.setAnswerType("text");
				questionAnswerDo.setSequence(1);
				questionAnswerDo.setIsCorrect(true);
				enteredAnswers.add(questionAnswerDo);
			}

			answerMap.put("answer",enteredAnswers);

			for(int i=0;i<hintsContainer.getWidgetCount();i++)
			{
				AddHintsView addHints = (AddHintsView)hintsContainer.getWidget(i);
				QuestionHintsDo questionHintsDo=new QuestionHintsDo();
				String hintText=addHints.hintTextBox.getText();
				if(hintText!=null&&!hintText.trim().equals("")&&!hintText.isEmpty()){
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
			HashMap<String,ArrayList<CodeDo>> taxonomySet = new HashMap<String,ArrayList<CodeDo>>();
			taxonomySet.put("taxonomyCode", standardsDo);
			ArrayList<Integer> standards=new ArrayList<Integer>();
			for(CodeDo codeDo:standardsDo){
				standards.add(codeDo.getCodeId());
			}
			collectionQuestionItemDo.setStandardIds(standards);
			//collectionQuestionItemDo.setTaxonomySet(taxonomySet);
			collectionQuestionItemDo.setDepthOfKnowledgeIds(depthOfKnowledgesList);
			collectionQuestionItemDo.setSkillIds(getSelectedCenturySkills());
			if(getQuestionType().equalsIgnoreCase("HT_HL") || getQuestionType().equalsIgnoreCase("HT_RO") ){
				collectionQuestionItemDo.setHlType(htType);
				collectionQuestionItemDo.setSingleCorrectAnswer(true);
			}

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
	private boolean isYesOrNoChoiceSelected(HTMLPanel questionAnswerChoiceContainer) {
		boolean isAnswerChoiceSelected=false;
		for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
			AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
			if(addQuestionAnswerChoice.optionSelectedButton.getStyleName().equals(addWebResourceStyle.answerSelected())||
					addQuestionAnswerChoice.optionNoButton.getStyleName().equals(addWebResourceStyle.answerSelected())){
				isAnswerChoiceSelected=true;
			}else{
				return false;
			}
		}

		return isAnswerChoiceSelected;
	}
	public boolean profanityCheckForHints(final boolean fieldValidationStaus,final List<String> answersListFIB,final String mediaFileName){
		validationValue=false;
		isValidHintsList.clear();
		for(int i=0;i<hintsListForProfanity.size();i++){
			isValidHintsList.add(hintsListForProfanity.get(i).getQuestionText());
		}
		if(!isValidHintsList.toString().contains("undefined")){
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
		}
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
				if(getQuestionType().equalsIgnoreCase("HT_HL") || getQuestionType().equalsIgnoreCase("HT_RO")){
					for(int i=0;i<questionHotTextAnswerChoiceContainer.getWidgetCount();i++){
						final AddHotTextQuestionAnswerChoice addHTQuestionAnswerChoice=(AddHotTextQuestionAnswerChoice)questionHotTextAnswerChoiceContainer.getWidget(i);
						addHTQuestionAnswerChoice.errorMessageforAnswerChoice.setText("");

						if(getQuestionType().equalsIgnoreCase("HT_HL")){
							addHTQuestionAnswerChoice.getHighlightTextArea().getElement().removeClassName("errorBorderMessage");
							SetStyleForProfanity.SetStyleForProfanityForTinyMCE(addHTQuestionAnswerChoice.highlightTextArea, addHTQuestionAnswerChoice.errorMessageforAnswerChoice, result.get(i).questionValue);
						}else{
							addHTQuestionAnswerChoice.getAnswerTextBox().getElement().removeClassName("errorBorderMessage");
							SetStyleForProfanity.SetStyleForProfanityForTinyMCE(addHTQuestionAnswerChoice.answerTextBox, addHTQuestionAnswerChoice.errorMessageforAnswerChoice, result.get(i).questionValue);
						}


						if(result.get(i)!=null && result.get(i).questionValue==true){
							addHTQuestionAnswerChoice.errorMessageforAnswerChoice.getElement().setAttribute("style", "float: left;left: 24px;");
							validationValue=true;
							isAddBtnClicked=true;
						}
					}
				}else{

					for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
						final AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
						addQuestionAnswerChoice.errorMessageforAnswerChoice.setText("");
						addQuestionAnswerChoice.getAnswerTextBox().getElement().removeClassName("errorBorderMessage");
						SetStyleForProfanity.SetStyleForProfanityForTinyMCE(addQuestionAnswerChoice.answerTextBox, addQuestionAnswerChoice.errorMessageforAnswerChoice, result.get(i).questionValue);
						if(result.get(i)!=null && result.get(i).questionValue==true){
							addQuestionAnswerChoice.errorMessageforAnswerChoice.getElement().setAttribute("style", "float: left;left: 24px;");
							validationValue=true;
							isAddBtnClicked=true;
						}
					}
				}
				if(validationValue && fieldValidationStaus){
					return;
				}else{
					isValidHintsList.clear();
					for(int i=0;i<hintsListForProfanity.size();i++){
						isValidHintsList.add(hintsListForProfanity.get(i).getQuestionText());
					}
					if(!isValidHintsList.toString().contains("undefined")){
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
			addQuestionAnswerChoice.getAnswerTextBox().getElement().removeClassName("errorBorderMessage");
			if(getQuestionType().equalsIgnoreCase("T/F")){
				answerChoiceValue=addQuestionAnswerChoice.fieldValue;
			}else if(getQuestionType().equalsIgnoreCase("MC")||getQuestionType().equalsIgnoreCase("MA")){
				answerChoiceValue=addQuestionAnswerChoice.answerTextBox.getContent().replaceAll("\\<.*?>","");
			}
			ProfanityCheckDo profanitymodel=new ProfanityCheckDo();
			if(answerChoiceValue==null||answerChoiceValue.trim().equalsIgnoreCase("")){
				isAnswerChoiceSelected=true;
				addQuestionAnswerChoice.errorMessageforAnswerChoice.setText(ERROR_MSG_ANSWER);
				addQuestionAnswerChoice.getAnswerTextBox().getElement().addClassName("errorBorderMessage");
				profanitymodel.setQuestionID(Integer.toString(i));
				profanityList.add(profanitymodel);
				addQuestionAnswerChoice.errorMessageforAnswerChoice.getElement().setAttribute("style", "display:block");
			}else{
				if(answerChoiceValue.trim().length()>ANSWER_CHOICE_HINTS_TEXT_LENGTH){
					isAnswerChoiceSelected=true;
					Document.get().getElementById(addQuestionAnswerChoice.answerTextBox.getID()+"_message").setInnerText("");
					addQuestionAnswerChoice.errorMessageforAnswerChoice.setText(ERROR_MSG_ANSWER_LENGTH);
					addQuestionAnswerChoice.getAnswerTextBox().getElement().addClassName("errorBorderMessage");
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

				String hintText=addHints.hintTextBox.getContent().toString().trim().replaceAll("&nbsp;", " ");
				hintText=hintText.replaceAll("\\<.*?>","");
				if(hintText!=null && !hintText.trim().equals("") && !hintText.isEmpty()){
					String hintsText=addHints.hintTextBox.getContent().replaceAll("\\<.*?>","");
					if(hintsText.trim().length()>ANSWER_CHOICE_HINTS_TEXT_LENGTH){
						Document.get().getElementById(addHints.hintTextBox.getID()+"_message").setInnerText("");
						addHints.errorMessageforHints.setText(ERROR_MSG_HINTS_LENGTH);
						hintsAdded=true;
						isAddBtnClicked=true;
					}else{
						hintsAdded=false;
						isAddBtnClicked=true;
						addHints.errorMessageforHints.setText("");
						profanitymodel.setQuestionText(addHints.hintTextBox.getContent());
					}

				}else{
					addHints.errorMessageforHints.setText(ERROR_MSG_HINTS);
					hintsAdded=true;
					isAddBtnClicked=true;
				}
				hintsListForProfanity.add(profanitymodel);
			}
		}
		return hintsAdded;
	}

	public boolean isHotTextAnswerChoiceEmpty(HTMLPanel questionAnswerChoiceContainer){
		profanityList=new ArrayList<ProfanityCheckDo>();
		for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
			final AddHotTextQuestionAnswerChoice addQuestionAnswerChoice=(AddHotTextQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
			String answerChoiceValue=null;
			addQuestionAnswerChoice.errorMessageforAnswerChoice.setText("");
			addQuestionAnswerChoice.getAnswerTextBox().getElement().removeClassName("errorBorderMessage");
			if(questionType.equalsIgnoreCase("HT_RO")){
				answerChoiceValue=addQuestionAnswerChoice.answerTextBox.getContent().replaceAll("\\<.*?>","");
			}else{
				answerChoiceValue=addQuestionAnswerChoice.highlightTextArea.getContent().replaceAll("\\<.*?>","");
			}

			ProfanityCheckDo profanitymodel=new ProfanityCheckDo();
			if(answerChoiceValue==null||answerChoiceValue.trim().equalsIgnoreCase("")){
				isAnswerChoiceSelected=true;
				addQuestionAnswerChoice.errorMessageforAnswerChoice.setText(ERROR_MSG_ANSWER);
				addQuestionAnswerChoice.getAnswerTextBox().getElement().addClassName("errorBorderMessage");
				profanitymodel.setQuestionID(Integer.toString(i));
				profanityList.add(profanitymodel);
				addQuestionAnswerChoice.errorMessageforAnswerChoice.getElement().setAttribute("style", "display:block");
			}else{
				if(answerChoiceValue.trim().length()>HT_ANSWER_CHOICE_HINTS_TEXT_LENGTH){
					isAnswerChoiceSelected=true;
					Document.get().getElementById(addQuestionAnswerChoice.answerTextBox.getID()+"_message").setInnerText("");
					addQuestionAnswerChoice.errorMessageforAnswerChoice.setText(ERROR_MSG_HTANSWER_LENGTH);
					addQuestionAnswerChoice.getAnswerTextBox().getElement().addClassName("errorBorderMessage");
				}else if(questionType.equalsIgnoreCase("HT_HL")){
					answerChoiceValue=StringUtil.getHotTextHiglightText(answerChoiceValue);
					String text=answerChoiceValue;
					String[] temp;
					String errorMsg;
					String errorMsg2;

					if(htType.equalsIgnoreCase(i18n.GL3219_1())){
						temp = text.split(DELIMITER_SPACE);
						errorMsg=ERROR_MSG_HTHL_SYNTAX;
						errorMsg2=ERROR_MSG_HTHL;
					}else{
						temp = text.split("\\.");
						errorMsg=ERROR_MSG_HTHL_SENTENCE;
						errorMsg2=ERROR_MSG_HTHL_SENTENCE;
					}
					if(temp.length>1  && answerChoiceValue.contains("[") && answerChoiceValue.contains("]")){
						boolean isCorrect=false;
						for(int k=0;k<temp.length;k++){
							if(temp[k].contains("[") || temp[k].contains("]")){
								if((temp[k].startsWith("[") || temp[k].startsWith("&nbsp;[") || temp[k].startsWith(" [")) &&(temp[k].endsWith("]") || temp[k].endsWith("].") || temp[k].endsWith("],") || temp[k].endsWith("];") || temp[k].endsWith("] ")) && temp[k].trim().length()>0){
									isCorrect=true;
								}else{
									isCorrect=false;
									break;
								}
							}
						}

						if(isCorrect){
							isAnswerChoiceSelected=false;
							profanitymodel.setQuestionID(Integer.toString(i));
							profanitymodel.setQuestionText(answerChoiceValue);
							profanityList.add(profanitymodel);
						}else{
							isAnswerChoiceSelected=true;
							setHTAnswerErrorMessage(addQuestionAnswerChoice,errorMsg);
						}

					}

					else{
						isAnswerChoiceSelected=true;
						setHTAnswerErrorMessage(addQuestionAnswerChoice,errorMsg2);
					}


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


	public void setHTAnswerErrorMessage(final AddHotTextQuestionAnswerChoice addQuestionAnswerChoice,String errorMsg){
		Document.get().getElementById(addQuestionAnswerChoice.answerTextBox.getID()+"_message").setInnerText("");
		addQuestionAnswerChoice.errorMessageforAnswerChoice.setText(errorMsg);
		addQuestionAnswerChoice.getAnswerTextBox().getElement().addClassName("errorBorderMessage");
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
		Window.enableScrolling(false);
		if(addQuestImgContainer.getElement().hasChildNodes()){
			addQuestImgContainer.setVisible(true);
			addQuestionImg.setVisible(false);
			setImageContainer();

		}else{
			uploadQuestionImage();
		}
	}
	public void setEditQuestionImage(){
		String tumbnailUrl="";
		if(collectionItemDo!=null){
			if (collectionItemDo.getResource().getAssets() != null
					&& collectionItemDo.getResource().getAssets().size() > 0) {
				tumbnailUrl = collectionItemDo.getCollection().getAssetURI()
						+ collectionItemDo.getResource().getFolder()
						+ collectionItemDo.getResource().getAssets().get(0).getAsset().getName();

			}else if(collectionItemDo.getResource().getThumbnails()!=null && collectionItemDo.getResource().getThumbnails().getUrl()!=null) {
				tumbnailUrl = collectionItemDo.getResource().getThumbnails().getUrl();
			}
			setEditQuestionImage(tumbnailUrl);
		}

	}

	/**
	 * This method sets Multiple Answer question type content.
	 *
	 */

	public void showMulipleChoice(){
		setHeaderAndBodyText(questionType);
		clearErrorMessageForAnswer();
		clearErrorQuestionMessage();
		for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
			AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
			addQuestionAnswerChoice.optionNoButton.setStyleName(addWebResourceStyle.answerDeselected());
			addQuestionAnswerChoice.errorMessageforAnswerChoice.setText("");
			addQuestionAnswerChoice.getAnswerTextBox().getElement().removeClassName("errorBorderMessage");
			addQuestionAnswerChoice.showAnswerChoicesForOthers();
		}
		questionTrueOrFalseAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		addAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		questionHotTextAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
	}

	/**
	 * This method sets Multiple Answer question type content.
	 *
	 */

	public void showMulipleAnswerChoiceOptions(){
		setHeaderAndBodyText(questionType);
		clearErrorMessageForAnswer();
		clearErrorQuestionMessage();
		for(int i=0;i<questionAnswerChoiceContainer.getWidgetCount();i++){
			AddQuestionAnswerChoice addQuestionAnswerChoice=(AddQuestionAnswerChoice)questionAnswerChoiceContainer.getWidget(i);
			addQuestionAnswerChoice.optionNoButton.setStyleName(addWebResourceStyle.answerDeselected());
			addQuestionAnswerChoice.errorMessageforAnswerChoice.setText("");
			addQuestionAnswerChoice.getAnswerTextBox().getElement().removeClassName("errorBorderMessage");
			addQuestionAnswerChoice.showAnswerChoicesForMultipleAnswers();
		}
		questionTrueOrFalseAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		addAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		questionHotTextAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
	}

	/**
	 * This method sets True/False question type content.
	 *
	 */
	public void showTrueOrFalseAnswerChoice(){
		setHeaderAndBodyText("T/F");
		clearErrorMessageForAnswer();
		clearErrorQuestionMessage();
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionTrueOrFalseAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		questionHotTextAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
	}

	/**
	 * This method sets Open-Ended question type content.
	 *
	 */

	public void showOpenEndedQuestion(){
		setHeaderAndBodyText("OE");
		clearErrorMessageForAnswer();
		clearErrorQuestionMessage();
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionTrueOrFalseAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionHotTextAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);

	}
	/**
	 * This method sets Fill in the blank question content.
	 */
	public void showFillInTheBlank(){
		questionNameTextArea.markAsBlankPanel.setVisible(true);
		setHeaderAndBodyText("FIB");
		clearErrorMessageForAnswer();
		clearErrorQuestionMessage();
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionTrueOrFalseAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		addAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionHotTextAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
	}

	/**
	 * This method sets Open-Ended question type content.
	 *
	 */

	public void showHotTextQuestion(){
		setHeaderAndBodyText(questionType);
		clearErrorMessageForAnswer();
		clearErrorQuestionMessage();
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionTrueOrFalseAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		addAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.NONE);
		questionHotTextAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		if(questionType.equalsIgnoreCase("HT_RO")){
			setHotTextROFields();
		}else if(questionType.equalsIgnoreCase("HT_HL")){
			setHotTextHLFields();
		}

	}

	/**
	 * This method sets header text for respective questions.
	 *
	 * @param tabType
	 *            is a selected question type.
	 */

	public void setHeaderAndBodyText(String tabType){
		if(tabType.equals("MC")){
			questionTypeHeader.setText(i18n.GL0349());
			questionTypeText.setText(i18n.GL0350());
			questionNameTextArea.markAsBlankPanel.setVisible(false);
			questionNameTextArea.getElement().setAttribute("maxlength", "500");
			questionCharcterLimit=500;
			questionNameTextArea.showTinyMceToolBar();
		}else if(tabType.equals("MA")){
			questionTypeHeader.setText(i18n.GL0351());
			questionTypeText.setText(i18n.GL0352());
			questionNameTextArea.markAsBlankPanel.setVisible(false);
			questionNameTextArea.getElement().setAttribute("maxlength", "500");
			questionCharcterLimit=500;
			questionNameTextArea.showTinyMceToolBar();
		}else if(tabType.equals("T/F")){
			questionTypeHeader.setText(i18n.GL0353());
			questionTypeText.setText(i18n.GL0354());
			questionNameTextArea.markAsBlankPanel.setVisible(false);
			questionNameTextArea.getElement().setAttribute("maxlength", "500");
			questionCharcterLimit=500;
			questionNameTextArea.showTinyMceToolBar();
		}else if(tabType.equals("FIB")){
			questionTypeHeader.setText(i18n.GL0355());
			questionTypeText.setText(i18n.GL0356());
			questionNameTextArea.getElement().setAttribute("maxlength", "500");
			questionCharcterLimit=500;
			questionNameTextArea.showTinyMceToolBar();
		}else if(tabType.equals("OE")){
			questionTypeHeader.setText(i18n.GL0357());
			questionTypeText.setText(i18n.GL0358());
			questionNameTextArea.markAsBlankPanel.setVisible(false);
			questionNameTextArea.getElement().setAttribute("maxlength", "500");
			questionCharcterLimit=500;
			questionNameTextArea.showTinyMceToolBar();
		}else if(tabType.equals("HT_HL")){
			questionTypeHeader.setText(i18n.GL3224_1());
			questionTypeText.setText(i18n.GL3213_1());
			questionNameTextArea.markAsBlankPanel.setVisible(false);
			questionCharcterLimit=5000;
			questionNameTextArea.showTinyMceToolBar();
		}else if(tabType.equals("HT_RO")){
			questionTypeHeader.setText(i18n.GL4009());
			questionTypeText.setText(i18n.GL4010());
			questionNameTextArea.markAsBlankPanel.setVisible(false);
			questionCharcterLimit=5000;
			questionNameTextArea.showTinyMceToolBar();
		}

		questionNameTextArea.setCharacterLimit(questionCharcterLimit);
		questionNameTextArea.getElement().setAttribute("maxlength", String.valueOf(questionCharcterLimit));
		questionNameTextArea.tinyMceTextArea.getElement().setAttribute("charLimit",String.valueOf(questionCharcterLimit));
//		questionNameTextArea.showTinyMceToolBar();
		String value = StringUtil.generateMessage(i18n.GL2103(),String.valueOf(questionCharcterLimit));
		charLimitLbl.setText(value);
		StringUtil.setAttributes(charLimitLbl.getElement(), "charLimitLbl", value, value);

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


		TreeSet<QuestionAnswerDo> answerChoicesSet = collectionItemDo.getResource().getAnswers() != null ? collectionItemDo.getResource().getAnswers() : collectionItemDo.getQuestionInfo().getAnswers();
		Iterator<QuestionAnswerDo> it = answerChoicesSet.iterator();

		List<QuestionAnswerDo> questionAnswerDoList = new ArrayList<QuestionAnswerDo>();

		try{
			/**
			 *  If type = 4 from API, treated as FIB.
			 */
			int type = collectionItemDo.getResource().getType() != null ? collectionItemDo.getResource().getType() : collectionItemDo.getQuestionInfo().getType();
			final String explanation = collectionItemDo.getResource().getExplanation() != null ? collectionItemDo.getResource().getExplanation() : collectionItemDo.getQuestionInfo().getExplanation();
			final String questionTitle = collectionItemDo.getResource().getTitle();


			if(collectionItemDo.getDepthOfKnowledge()!=null){
				setCheckedData(collectionItemDo.getDepthOfKnowledge());
			}
			if(collectionItemDo.getSkills()!= null && collectionItemDo.getSkills().size()>0){
				centuryPanel.clear();
				for (StandardFo standardObj : collectionItemDo.getSkills()) {
					CodeDo codeObj=new CodeDo();
					codeObj.setCodeId(standardObj.getId());
					codeObj.setCode(standardObj.getLabel());
					centurySelectedValues.put(Long.parseLong(standardObj.getId()+""), standardObj.getLabel());
					centuryPanel.add(create21CenturyLabel(standardObj.getLabel(),standardObj.getId()+"",""));
				}
			}
			if(type==4){
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
				explainationTextArea.setText(explanation);
			}else{

				questionNameTextArea.setText(questionTitle);
				explainationTextArea.setText(explanation);
			}

		}
		catch(Exception e){
			AppClientFactory.printSevereLogger("AddQuestionResourceView showEditQuestionResourceView:::"+e);
		}

		TreeSet<QuestionHintsDo> hintsList = collectionItemDo.getResource().getHints() != null ? collectionItemDo.getResource().getHints() : collectionItemDo.getQuestionInfo().getHints();
		Iterator<QuestionHintsDo> iterator = hintsList.iterator();
		hintsContainer.clear();
		while (iterator.hasNext()) {
			QuestionHintsDo hints = iterator.next();
			int widgetCount=hintsContainer.getWidgetCount();
			final AddHintsView addHints = new AddHintsView(widgetCount+1,hints.getHintText());
			addHintsTextArea(addHints);
		}
		int type = collectionItemDo.getResource().getType() != null ? collectionItemDo.getResource().getType() : collectionItemDo.getQuestionInfo().getType();
		if(type==1){
			addResourceFormTitleChoice.setText(i18n.GL0864());
			correctText.clear();
			correctText.getElement().setInnerHTML(i18n.GL0314());
			setCorrectTextStyle();
			noLabelText.setVisible(false);
			questionAnswerChoiceContainer.clear();
			while (it.hasNext()) {
				QuestionAnswerDo answer = it.next();
				int widgetCount=questionAnswerChoiceContainer.getWidgetCount();

				final AddQuestionAnswerChoice addQuestionAnswer=new AddQuestionAnswerChoice(anserChoiceArray[widgetCount],answer.getAnswerText());
				addQuestionAnswer.optionNoButton.setStyleName(addWebResourceStyle.answerDeselected());
				if(answer.isIsCorrect()){
					addQuestionAnswer.optionSelectedButton.setStyleName(addWebResourceStyle.answerSelected());
				}else{
					addQuestionAnswer.optionSelectedButton.setStyleName(addWebResourceStyle.answerDeselected());
				}
				addQuesetionAnswerOptionTextArea(addQuestionAnswer,widgetCount);
			}
		}else if(type==7){
			addResourceFormTitleChoice.setText("Enter answers and select correct ones *");
			correctText.clear();
			correctText.getElement().setInnerHTML("Yes");
			noLabelText.setVisible(true);
			noLabelText.getElement().setInnerHTML("No");
			setYesOrNoLabelStyles();
			questionAnswerChoiceContainer.clear();
			while (it.hasNext()) {
				QuestionAnswerDo answer = it.next();
				int widgetCount=questionAnswerChoiceContainer.getWidgetCount();
				final AddQuestionAnswerChoice addQuestionAnswer=new AddQuestionAnswerChoice(anserChoiceArray[widgetCount],answer.getAnswerText());
				addQuestionAnswer.optionNoButton.setStyleName(addWebResourceStyle.answerDeselected());
				addQuestionAnswer.showAnswerChoicesForMultipleAnswers();
				if(answer.isIsCorrect()){
					addQuestionAnswer.optionSelectedButton.setStyleName(addWebResourceStyle.answerSelected());
				}else{
					addQuestionAnswer.optionNoButton.setStyleName(addWebResourceStyle.answerSelected());
				}
				addQuesetionAnswerOptionTextArea(addQuestionAnswer,widgetCount);
			}
		}else if(type==3){
			addResourceFormTitleChoice.setText(i18n.GL0864());
			correctText.clear();
			correctText.getElement().setInnerHTML(i18n.GL0314());
			setCorrectTextStyle();
			noLabelText.setVisible(false);
			setMultipleChoiceAnswerFields();
			int answerCount=0;
			while (it.hasNext()) {
				QuestionAnswerDo answer = it.next();
				selectTrueOrFallseCorrectAnswerOption(answerCount,answer.isIsCorrect());
				answerCount++;
			}
		}else if(type==8){
			questionHotTextAnswerChoiceContainer.clear();
			while (it.hasNext()) {
				QuestionAnswerDo answer = it.next();
				AddHotTextQuestionAnswerChoice addHotTextAnsChoice = new AddHotTextQuestionAnswerChoice(anserChoiceNumArray[0], answer.getAnswerText());

				addHotTextAnsChoice.setHeadLabelFields(true);
				addHotTextAnsChoice.highlightRDButtonClick();
				setHotTextAnswers(addHotTextAnsChoice);
				htType=	collectionItemDo.getResource().getHlType();

				if(htType.equalsIgnoreCase(i18n.GL3219_1())){
					addHotTextAnsChoice.wordRDButtonClick();
				}else{
					addHotTextAnsChoice.sentenceRDButtonClick();
				}
				questionHotTextAnswerChoiceContainer.add(addHotTextAnsChoice);
			}

		}else if(type==9){
			questionHotTextAnswerChoiceContainer.clear();
			int k=0;
			while (it.hasNext()) {
				QuestionAnswerDo answer = it.next();
				if(k==0){
					AddHotTextQuestionAnswerChoice addHotTextAnsChoice=new AddHotTextQuestionAnswerChoice(anserChoiceNumArray[k], answer.getAnswerText());
					addHotTextAnsChoice.setHeadLabelFields(true);
					setHotTextAnswers(addHotTextAnsChoice);
					questionHotTextAnswerChoiceContainer.add(addHotTextAnsChoice);
				}else{
					int widgetCount=questionHotTextAnswerChoiceContainer.getWidgetCount();
					AddHotTextQuestionAnswerChoice addHotTextAnsChoice=new AddHotTextQuestionAnswerChoice(anserChoiceNumArray[k], answer.getAnswerText());
					addHotTextAnsChoice.setHeadLabelFields(false);
					addHotTextQuesetionAnswerOptionTextArea(addHotTextAnsChoice,widgetCount);
					questionHotTextAnswerChoiceContainer.add(addHotTextAnsChoice);

				}
				k++;
			}
		}

		else{
			setMultipleChoiceAnswerFields();
		}


		if(collectionItemDo.getStandards()!=null){
			ulSelectedItems.clear();
			standardsDo.clear();

			for (Map<String, String> map: collectionItemDo.getStandards()) {
				CodeDo codeObj=new CodeDo();
				for (Map.Entry<String, String> entry : map.entrySet()) {
					String key = entry.getKey();
					String values = entry.getValue();
					if(key.contains("id")){
						codeID=values;
						codeObj.setCodeId(Integer.parseInt(values));
					}
					if(key.contains("code")){
						code=values;
						codeObj.setCode(values);
					}
					if(key.contains("code")){
						label=values;
						codeObj.setLabel(values);
					}
				}
				standardsDo.add(codeObj);
				final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(code);
				liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						for(int i=0;i<selectedValues.size();i++) {
						     if((selectedValues.get(i)).equals(codeID)){
						    	 selectedValues.remove(codeID);
						    	 Element element = Document.get().getElementById(codeID);
						    	 if(element!=null){
						 			element.removeClassName("active");
						 		}
						     }
						 }
						removeGradeWidget(ulSelectedItems,Long.parseLong(codeID));
						liPanelWithClose.removeFromParent();
						//lblGradeErrorMsg.setVisible(false);
					}
				});
				liPanelWithClose.setId(Long.parseLong(codeID));
				liPanelWithClose.setName(label);
				liPanelWithClose.setDifferenceId(3);
				liPanelWithClose.getElement().setAttribute("tag", "taxonomy");
				ulSelectedItems.add(liPanelWithClose);
			}
		}
		setStandardContainer();
		setExplanationContainer();
		setDepthOfKnowledgeContainer();
		setHintsContainer();
		setStandardsContainer();
		setCenturyContainer();
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
		copyRightPolicy.center();
		copyRightPolicy.getElement().getStyle().setZIndex(999);
	}
	@UiHandler("commuGuideLinesAnr")
	public void onClickCommunityGuide(ClickEvent event){
		Window.open("http://support.gooru.org/hc/en-us/articles/200688506","_blank","");

	}

	/**
	 * Gets the name of the used browser.
	 */
	public static native String getBrowserName() /*-{
         return navigator.userAgent.toLowerCase();
     }-*/;
	public void setYesOrNoLabelStyles(){
		correctText.setStyleName(addWebResourceStyle.yesNoTextStyle());
		noLabelText.setStyleName(addWebResourceStyle.yesNoTextStyle());
	}

	public void setCorrectTextStyle(){
		correctText.setStyleName(addWebResourceStyle.addResourceFormTitleChoiceAlign());
	}
	public void addClickEventsForCheckBox(){
		depthOfKnoweldgeToolTip.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				toolTip = new ToolTip(i18n.GL1734());
				toolTip.getLblLink().setVisible(false);
				toolTip.getElement().getStyle().setBackgroundColor("transparent");
				toolTip.getElement().getStyle().setPosition(Position.ABSOLUTE);
				toolTip.setPopupPosition(depthOfKnoweldgeToolTip.getAbsoluteLeft()-(50+22), depthOfKnoweldgeToolTip.getAbsoluteTop()+22);
				toolTip.getElement().getStyle().setZIndex(1111);
				toolTip.show();
			}
		});
		depthOfKnoweldgeToolTip.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {

				EventTarget target = event.getRelatedTarget();
				if (Element.is(target)) {
					if (!toolTip.getElement().isOrHasChild(Element.as(target))){
						toolTip.hide();
					}
				}
			}
		});
	}

	public void setDOKCheckBoxes(){
		/* if(chkLevelRecall.isChecked()){
	    	 checkboxSelectedDo depthObj=new checkboxSelectedDo();
		     depthObj.setSelected(true);
		     depthObj.setValue(chkLevelRecall.getText());
		     depthOfKnowledges.add(depthObj);
	     }else{
	      	 checkboxSelectedDo depthObj=new checkboxSelectedDo();
		     depthObj.setSelected(false);
		     depthObj.setValue(chkLevelRecall.getText());
		     depthOfKnowledges.add(depthObj);
	     }

	     if(chkLevelSkillConcept.isChecked())
	     {
	    	 checkboxSelectedDo depthObj=new checkboxSelectedDo();
		     depthObj.setSelected(true);
		     depthObj.setValue(chkLevelSkillConcept.getText());
		     depthOfKnowledges.add(depthObj);
	     }
	     else
	     {
	      	 checkboxSelectedDo depthObj=new checkboxSelectedDo();
		     depthObj.setSelected(false);
		     depthObj.setValue(chkLevelSkillConcept.getText());
		     depthOfKnowledges.add(depthObj);
	     }

	     if(chkLevelStrategicThinking.isChecked())
	     {
	    	 checkboxSelectedDo depthObj=new checkboxSelectedDo();
		     depthObj.setSelected(true);
		     depthObj.setValue(chkLevelStrategicThinking.getText());
		     depthOfKnowledges.add(depthObj);
	     }
	     else
	     {
	      	 checkboxSelectedDo depthObj=new checkboxSelectedDo();
		     depthObj.setSelected(false);
		     depthObj.setValue(chkLevelStrategicThinking.getText());
		     depthOfKnowledges.add(depthObj);
	     }

	     if(chkLevelExtendedThinking.isChecked())
	     {
	    	 checkboxSelectedDo depthObj=new checkboxSelectedDo();
		     depthObj.setSelected(true);
		     depthObj.setValue(chkLevelExtendedThinking.getText());
		     depthOfKnowledges.add(depthObj);
	     }
	     else
	     {
	      	 checkboxSelectedDo depthObj=new checkboxSelectedDo();
		     depthObj.setSelected(false);
		     depthObj.setValue(chkLevelExtendedThinking.getText());
		     depthOfKnowledges.add(depthObj);
	     }*/

	}


	public abstract void callBrowseStandards();

	public abstract void closeStandardsPopup();



	private boolean eventTargetsPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			try{
				return browseStandardsTooltip.getElement().isOrHasChild(Element.as(target));
			}catch(Exception ex){
				AppClientFactory.printSevereLogger(ex.getMessage());
			}
		}
		return false;
	}
	/**
	 * This will handle the click event on the browser century
	 * @param e
	 */
	@UiHandler("browseCentury")
	public void onClickOfBrowseCentury(ClickEvent e){
		centuryPopup.clear();
		centuryPresenterWidget.setAddResourceData(centurySelectedValues);
		centuryPopup.add(centuryPresenterWidget.getWidget());
		centuryPopup.show();
		centuryPopup.center();
		centuryPopup.getElement().getStyle().setZIndex(999999);
	}

	public void setExplanationContainer(){
		if(explainationTextArea.getText().isEmpty() || explainationTextArea.getText().trim().length()==0){
			addExplanationLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
			addExplanationLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}else{
			addExplanationLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabs());
			addExplanationLabel.addStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}
	}
	public void setStandardContainer(){
		boolean isSelected=false;
		isSelected=isStandardsAdded();
		if(isSelected){
			addStandardsLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabs());
			addStandardsLabel.addStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}else{
			addStandardsLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
			addStandardsLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}
	}
	public boolean isStandardsAdded(){
		Iterator<Widget> widgets=ulSelectedItems.iterator();
		boolean isSelected=false;
		while(widgets.hasNext()){
			Widget widget=widgets.next();
			if(widget instanceof LiPanelWithClose){
				isSelected=true;
			}
		}
		return isSelected;
	}

	public void setDepthOfKnowledgeContainer(){
		boolean isSelected=false;
		isSelected=isDepthofKnowledge();
		if(isSelected){
			addDepthOfKnowledgeLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabs());
			addDepthOfKnowledgeLabel.addStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}else{
			addDepthOfKnowledgeLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
			addDepthOfKnowledgeLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}
	}

	public void setHintsContainer(){
		if(hintsContainer.getWidgetCount()>0){
			addHintsLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabs());
			addHintsLabel.addStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}else{
			addHintsLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
			addHintsLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}
	}

	public void setCenturyContainer(){

		if(centuryPanel.getWidgetCount()>0){
			addCenturyLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabs());
			addCenturyLabel.addStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}else{
			addCenturyLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
			addCenturyLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}
	}

	public void setImageContainer(){

		if(addQuestImgContainer.getElement().hasChildNodes()){
			addQuestionImg.removeStyleName(addWebResourceStyle.advancedOptionsTabs());
			addQuestionImg.addStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}else{
			addQuestionImg.addStyleName(addWebResourceStyle.advancedOptionsTabs());
			addQuestionImg.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}
	}

	public void setImageHandler(){
		if(addQuestImgContainer.getElement().hasChildNodes()){
			AddQuestionImg addQuestionImage=(AddQuestionImg)addQuestImgContainer.getWidget(0);
			addQuestionImage.eHearderIconImage.addClickHandler(new MinimizePanelsClickHandler());
		}
	}


	private class MinimizePanelsClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			if(event.getRelativeElement().getId().equalsIgnoreCase("eHearderIconHint")){
				hintsContainer.setVisible(false);
				int widgetsCount=hintsContainer.getWidgetCount();
				if(widgetsCount==5){
					addHintsLabel.getElement().getStyle().setDisplay(Display.BLOCK);
				}
				for(int i=0;i<widgetsCount;){
					AddHintsView addHintsView =(AddHintsView) hintsContainer.getWidget(i);
					if(addHintsView.hintTextBox.getText().equalsIgnoreCase("")){
						addHintsView.removeFromParent();
						widgetsCount=hintsContainer.getWidgetCount();
						i--;
					}else{
						addHintsView.showHintsMessage(i+1);
						addHintsLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabs());
						addHintsLabel.addStyleName(addWebResourceStyle.advancedOptionsTabActive());
					}
					i++;
				}
				int count=hintsContainer.getWidgetCount();
				addHintsLabel.setText(i18n.GL3210_1()+i18n.GL_SPL_OPEN_SMALL_BRACKET()+(5-count)+i18n.GL3207_1()+i18n.GL_SPL_CLOSE_SMALL_BRACKET());
			}else if(event.getRelativeElement().getId().equalsIgnoreCase("eHearderIconExplanation")){
				explanationContainer.setVisible(false);
				addExplanationLabel.setVisible(true);
				addExplanationLabel.setText(i18n.GL3208_1());
				setExplanationContainer();
			}else if(event.getRelativeElement().getId().equalsIgnoreCase("eHearderIconDepthOfKnowledge")){
				depthOfKnowledgeContainer.setVisible(false);
				addDepthOfKnowledgeLabel.setVisible(true);
				addDepthOfKnowledgeLabel.setText(i18n.GL3209_1());
				setDepthOfKnowledgeContainer();
			}else if(event.getRelativeElement().getId().equalsIgnoreCase("eHearderIconStandards")){
				standardContainer.setVisible(false);
				addStandardsLabel.setVisible(true);
				addStandardsLabel.setText(i18n.GL0575());
				setStandardContainer();
			}else if(event.getRelativeElement().getId().equalsIgnoreCase("eHearderIconCentury")){
				centuryContainer.setVisible(false);
				addCenturyLabel.setVisible(true);
				addCenturyLabel.setText(i18n.GL3199());
				setCenturyContainer();
			}else if(event.getRelativeElement().getId().equalsIgnoreCase("eHearderIconImage")){
				addQuestImgContainer.setVisible(false);
				addQuestionImg.setVisible(true);
				setImageContainer();
			}
		}
	}


	public void setAncTabs(){
		explanationContainer.setVisible(!addExplanationLabel.isVisible());
		depthOfKnowledgeContainer.setVisible(!addDepthOfKnowledgeLabel.isVisible());
		hintsContainer.setVisible(!addHintsLabel.isVisible());
		standardContainer.setVisible(!addStandardsLabel.isVisible());
		centuryContainer.setVisible(!addCenturyLabel.isVisible());
	}
	public void resetDepthOfKnowledges(){

	}
	/**
	 * This inner class is used for generate check boxes
	 */
	class DepthOfKnowledgePanel extends Composite{
		HTMLPanel panel;
		CheckBox checkbox;
		ListValuesDo listValuesDo;
		DepthOfKnowledgePanel(final ListValuesDo listValuesDo){
			this.listValuesDo=listValuesDo;
			panel=new HTMLPanel("");
			checkbox=new CheckBox();
			checkbox.setText(listValuesDo.getName());
			checkbox.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(depthOfKnowledgesList.contains(listValuesDo.getId())){
						depthOfKnowledgesList.remove(listValuesDo.getId());
					}else{
						depthOfKnowledgesList.add(listValuesDo.getId());
					}
				}
			});
			panel.setStyleName(CollectionCBundle.INSTANCE.css().checkBoxinnerContinerForQuestion());
			checkbox.setStyleName(CollectionCBundle.INSTANCE.css().checkBoxlevels());
			panel.add(checkbox);
			initWidget(panel);
		}
		public ListValuesDo getListValuesDo(){
			return listValuesDo;
		}
	}
	/*C
	 * This method is used to render the depth of knowledges.
	 * @param depthOfKnowledges
	 */
	public void setDepthOfKnowledes(List<ListValuesDo> depthOfKnowledges){
		depthOfKnowledgesList.clear();
		pnlDepthOfKnowledges.clear();
		for (final ListValuesDo listValuesDo : depthOfKnowledges) {
			pnlDepthOfKnowledges.add(new DepthOfKnowledgePanel(listValuesDo));
		}
		if(collectionItemDo!=null && collectionItemDo.getDepthOfKnowledge()!=null){
			if( collectionItemDo.getDepthOfKnowledge().size()>0){
				for (checkboxSelectedDo checkboxObj : collectionItemDo.getDepthOfKnowledge()) {
					Iterator<Widget> widgets=pnlDepthOfKnowledges.iterator();
					while(widgets.hasNext()){
						Widget widget=widgets.next();
						if(widget instanceof DepthOfKnowledgePanel){
							DepthOfKnowledgePanel pnlWidget=(DepthOfKnowledgePanel) widget;
							if(checkboxObj.getName().equalsIgnoreCase(pnlWidget.checkbox.getText())){
								pnlWidget.checkbox.setValue(true);
								depthOfKnowledgesList.add(pnlWidget.listValuesDo.getId());
							}
						}
					}
				}
			}
			setDepthOfKnowledgeContainer();
		}
	}
	/**
	 * This will set the depth of knowledges when editing question
	 * @param depthOfKnowledges
	 */
	public void setCheckedData(List<checkboxSelectedDo> depthOfKnowledges){

		Iterator<Widget> widgets=pnlDepthOfKnowledges.iterator();
		Map<Integer,String> depthOfKnowledgeMap=new HashMap<Integer, String>();
		for (checkboxSelectedDo checkboxSelectedDo : depthOfKnowledges) {
			depthOfKnowledgeMap.put(checkboxSelectedDo.getId(), checkboxSelectedDo.getName());
		}
		while(widgets.hasNext()){
			Widget widget=widgets.next();
			if(widget instanceof DepthOfKnowledgePanel){
				DepthOfKnowledgePanel pnlWidget=(DepthOfKnowledgePanel) widget;
				if(depthOfKnowledgeMap.containsKey(pnlWidget.getListValuesDo().getId())){
					pnlWidget.checkbox.setValue(true);
				}
			}
		}
	}

	public List<Integer> getSelectedCenturySkills(){
		List<Integer> selectedValues=new ArrayList<Integer>();
		int size=centuryPanel.getWidgetCount();
		for(int i=0;i<size;i++){
			DownToolTipWidgetUc downToolTipWidgetUc=(DownToolTipWidgetUc)centuryPanel.getWidget(i);
			selectedValues.add(Integer.parseInt(downToolTipWidgetUc.getElement().getId()));
		}

		return selectedValues;
	}

	public boolean isDepthofKnowledge(){
		Iterator<Widget> widgets=pnlDepthOfKnowledges.iterator();
		boolean depthOfKnowledge=false;
		while(widgets.hasNext()){
			Widget widget=widgets.next();
			if(widget instanceof DepthOfKnowledgePanel){
				DepthOfKnowledgePanel pnlWidget=(DepthOfKnowledgePanel) widget;
				if(pnlWidget.checkbox.getValue()==true){
					depthOfKnowledge=true;
				}
			}
		}
		return depthOfKnowledge;
	}
	public final void populateStandardValues() {
		standardsDropListValues.clear();
		for (String standardsTypesArray1 : standardsTypesArray) {
			List<String> standardsDescriptionList = Arrays.asList(standardsTypesArray1.split(","));
			LiPanel liPanel = new LiPanel();
			for (int j = 0; j < standardsDescriptionList.size(); j++) {
				HTMLPanel headerDiv = new HTMLPanel("");
				if (j == 0) {
					if(standardsDescriptionList.get(j).equalsIgnoreCase("CA SS")){
                        liPanel.getElement().setId("CA");
                    }else if(standardsDescriptionList.get(j).equalsIgnoreCase("LWMCS")){
                        liPanel.getElement().setId("B21");
                    }else{
                        liPanel.getElement().setId(standardsDescriptionList.get(j));
                    }

					if ((!isCCSSAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("CCSS")) {
						liPanel.getElement().setAttribute("style", "opacity:0.5;");
					} else if ((!isCAAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("CA SS")) {
						liPanel.getElement().setAttribute("style", "opacity:0.5;");
					} else if ((!isNGSSAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("NGSS")) {
						liPanel.getElement().setAttribute("style", "opacity:0.5;");
					} else if ((!isTEKSAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("TEKS")) {
						liPanel.getElement().setAttribute("style", "opacity:0.5;");
					}

					headerDiv.setStyleName("liPanelStyle");
				} else {
					if (standardsDescriptionList.get(j).equalsIgnoreCase("College Career and Civic Life")) {
						standardsDescriptionList.set(j, "College, Career, and Civic Life");
						headerDiv.setStyleName("liPanelStylenonBold");
						liPanel.getElement().setAttribute("standarddesc", "College, Career, and Civic Life");
					} else {
						headerDiv.setStyleName("liPanelStylenonBold");
						liPanel.getElement().setAttribute("standarddesc", standardsDescriptionList.get(j));
					}
				}
				headerDiv.getElement().setInnerHTML(standardsDescriptionList.get(j));
				liPanel.add(headerDiv);
			}
			if (liPanel.getElement().getAttribute("style") != null
					&& !liPanel.getElement().getAttribute("style").equalsIgnoreCase("opacity:0.5;")) {
				liPanel.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						standardsDropListValues.setVisible(false);
						String standardsVal = event.getRelativeElement().getAttribute("id");
						String standardsDesc = event.getRelativeElement().getAttribute("standarddesc");

						collectionLiPanelWithCloseArray.clear();
						for (int i = 0; i < ulSelectedItems.getWidgetCount(); i++) {
							collectionLiPanelWithCloseArray.add((LiPanelWithClose) ulSelectedItems.getWidget(i));
						}
						showStandardsPopup(standardsVal, standardsDesc,
								collectionLiPanelWithCloseArray);
					}
				});
			}
			standardsDropListValues.add(liPanel);
		}
	}



	public void checkStandarsList(List<String> standarsPreferencesList) {

		if (standarsPreferencesList != null) {
			if (standarsPreferencesList.contains("CCSS")) {
				isCCSSAvailable = true;
			} else {
				isCCSSAvailable = false;
			}
			if (standarsPreferencesList.contains("NGSS")) {
				isNGSSAvailable = true;
			} else {
				isNGSSAvailable = false;
			}
			if (standarsPreferencesList.contains("TEKS")) {
				isTEKSAvailable = true;
			} else {
				isTEKSAvailable = false;
			}
			if (standarsPreferencesList.contains("CA")) {
				isCAAvailable = true;
			} else {
				isCAAvailable = false;
			}
		}

		populateStandardValues();
	}

	public void getAddStandards() {
		if (!AppClientFactory.isAnonymous()) {
			AppClientFactory.getInjector().getUserService().getUserProfileV2Details(
					AppClientFactory.getLoggedInUser().getGooruUId(), USER_META_ACTIVE_FLAG,
					new SimpleAsyncCallback<ProfileDo>() {
						@Override
						public void onSuccess(final ProfileDo profileObj) {
							if (profileObj.getUser().getMeta() != null
									&& profileObj.getUser().getMeta().getTaxonomyPreference() != null
									&& profileObj.getUser().getMeta().getTaxonomyPreference().getCode() != null) {
								checkStandarsList(profileObj.getUser().getMeta().getTaxonomyPreference().getCode());
							}
							standardPreflist=new ArrayList<String>();
							for (String code : profileObj.getUser().getMeta().getTaxonomyPreference().getCode()) {
								standardPreflist.add(code);
								standardPreflist.add(code.substring(0, 2));
							 }
						}

					});
		} else {
			isCCSSAvailable = true;
			isNGSSAvailable = true;
			isCAAvailable = true;
			isTEKSAvailable = false;
		}
	}

	public abstract void showStandardsPopup(String standardVal, String standardsDesc,
			List<LiPanelWithClose> collectionLiPanelWithCloseArray);

	public void setStandardSuggestions(SearchDo<CodeDo> standardSearchDo) {
		standardSuggestOracle.clear();
		this.standardSearchDo = standardSearchDo;
		if (this.standardSearchDo.getSearchResults() != null) {
			List<String> sources = getAddedStandards();
			for (CodeDo code : standardSearchDo.getSearchResults()) {
				if (!sources.contains(code.getCode())) {
					standardSuggestOracle.add(code.getCode());
				}
				selectedValues.add(code.getCodeId());
				standardCodesMap.put(code.getCodeId() + "", code.getLabel());
			}
		}
		standardSgstBox.showSuggestionList();
	}
	public void displaySelectedStandards(List<Map<String,String>> standListArray){
		for (int i=0;i<standListArray.size();i++){
			final Map<String, String> standard = standListArray.get(i);
			if (!selectedValues.contains(standard.get("selectedCodeId"))){
				ulSelectedItems.add(generateLiPanel(standard, "standards"));
			}
		}
	}
	public void displaySelectedStandardsOne(Map<String, String> standard){
			if (!selectedValues.contains(standard.get("selectedCodeId"))){
				ulSelectedItems.add(generateLiPanel(standard, "standards"));
			}

	}
	private LiPanelWithClose generateLiPanel(final Map<String, String> standard, String tagValue) {
		final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(standard.get("selectedCodeVal"));
		liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//This will remove the selected value when we are trying by close button
				removeGradeWidget(ulSelectedItems,Long.parseLong(standard.get("selectedCodeId")));
				liPanelWithClose.removeFromParent();
			}
		});
		selectedValues.add(Integer.parseInt(standard.get("selectedCodeId")));
		liPanelWithClose.setId(Long.parseLong(standard.get("selectedCodeId")));
		liPanelWithClose.setName(standard.get("selectedCodeVal"));
		liPanelWithClose.setRelatedId(Integer.parseInt(standard.get("selectedCodeId")));
		liPanelWithClose.setDifferenceId(Integer.parseInt(standard.get("selectedDifferenceId")));
		liPanelWithClose.getElement().setAttribute("tag", tagValue);
		return liPanelWithClose;
	}
	/**
	 * This method will remove the widget based on the codeId in the UlPanel
	 * @param ulPanel
	 * @param codeId
	 */
	public void removeGradeWidget(UlPanel ulPanel,long codeId){
		Iterator<Widget> widgets=ulPanel.iterator();
		while (widgets.hasNext()) {
			Widget widget=widgets.next();
			if(widget instanceof LiPanelWithClose){
				LiPanelWithClose obj=(LiPanelWithClose) widget;
				if(obj.getId()==codeId){
					obj.removeFromParent();
				}
			}
			if(widget instanceof LiPanel){
				LiPanel obj=(LiPanel) widget;
				if(obj.getCodeId()==codeId){
					obj.removeStyleName("active");
				}
			}
		}
	}

	/**
	 * This method is used to get the selected Std id's
	 * @return
	 */
	public List<Integer> getSelectedStandards(){
		List<Integer> taxonomyCourseIds=new ArrayList<>();
		Iterator<Widget> widgets=ulSelectedItems.iterator();
		List<CourseSubjectDo> courseList=new ArrayList<>();
		while (widgets.hasNext()) {
			Widget widget=widgets.next();
			if(widget instanceof LiPanelWithClose){
				LiPanelWithClose obj=(LiPanelWithClose) widget;
				if(obj.getDifferenceId()==3){
					Integer intVal = (int)obj.getId();
					taxonomyCourseIds.add(intVal);
					CourseSubjectDo courseObj=new CourseSubjectDo();
					selectedValues.add((int)obj.getId());
					courseObj.setId((int)obj.getId());
					courseObj.setCode(obj.getName());
					courseObj.setSubjectId(obj.getRelatedId());
					courseList.add(courseObj);
					CodeDo codeObj=new CodeDo();
					codeObj.setCodeId((int)obj.getId());
					codeObj.setCode(obj.getName());
					standardsDo.add(codeObj);
				}
			}
		}
		if(courseObjG!=null){
			courseObjG.setStandards(courseList);
		}

		return taxonomyCourseIds;
	}
	/**
	 * get the standards are added for collection
	 *
	 * @param flowPanel
	 *            having all added standards label
	 * @return standards text in list which are added for the collection
	 */
	private List<String> getAddedStandards() {
		List<String> suggestions = new ArrayList<String>();

		Iterator<Widget> widgets = ulSelectedItems.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if (widget instanceof DownToolTipWidgetUc) {
				suggestions.add(((CloseLabel) ((DownToolTipWidgetUc) widget).getWidget()).getSourceText());
			}
		}
		return suggestions;
	}
	public void setStandardsContainer(){
		if(ulSelectedItems.getWidgetCount()>0){
			addStandardsLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabs());
			addStandardsLabel.addStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}else{
			addStandardsLabel.addStyleName(addWebResourceStyle.advancedOptionsTabs());
			addStandardsLabel.removeStyleName(addWebResourceStyle.advancedOptionsTabActive());
		}
	}

}