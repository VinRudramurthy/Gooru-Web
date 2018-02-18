package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.addquestion;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
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
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddAnswerChoice;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddAnswerImg;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddHintsView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddHotSpotQuestionAnswerChoice;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddQuestionImg;
import org.ednovo.gooru.client.uc.AppMultiWordSuggestOracle;
import org.ednovo.gooru.client.uc.AppSuggestBox;
import org.ednovo.gooru.client.uc.BlueButtonUc;
import org.ednovo.gooru.client.uc.CloseLabel;
import org.ednovo.gooru.client.uc.CloseLabelCentury;
import org.ednovo.gooru.client.uc.ConfirmationPopupVc;
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

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;

public class QuestionTypeView extends BaseViewWithHandlers<QuestionTypeUiHandlers>
implements IsQuestionTypeView,SelectionHandler<SuggestOracle.Suggestion> {

	private static QuestionTypeViewUiBinder uiBinder = GWT	.create(QuestionTypeViewUiBinder.class);

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	interface QuestionTypeViewUiBinder extends UiBinder<Widget, QuestionTypeView> {
	}

	@UiField Label questionTypeHeader,questionTypeText,charLimitLbl,questionNameErrorLbl,advancedLbl,
	explanationLabel,charLimitExplanation,explainationErrorLbl,errorMessageForExplanation,errorMessageForHintsCheck,
	depthOfKnowledgeHeader,depthOfKnowledgeTitle,standardsDefaultText,standardMaxMsg,centuryDefaultText,rightsLbl,
	loadingTextLbl,ansChoiceErrMsg;
	@UiField Anchor addQuestionImg,addExplanationAnc,addHintsAnc,addDepthOfKnowledgeAnc,addStandardsAnc,addCenturyAnc;
	@UiField HTMLPanel standardsCont,questionText,addQuestImgContainer,questionHotSpotAnswerChoiceContainer,advancedContainer,hintsContainer,
	errorContainer,panelContentRights,rightsContent,buttonContainer,questionNameTextAreaContainer,explainationTextAreaContainer;
	TinyMCE questionNameTextArea,explainationTextArea;
	@UiField FlowPanel explanationContainer,depthOfKnowledgeContainer,standardContainer,centuryContainer,
	centuryPanel;
	@UiField HTMLEventPanel eHearderIconExplanation,eHearderIconDepthOfKnowledge,eHearderIconStandards,eHearderIconCentury,
	lblContentRights,addQuestionResourceButton;
	@UiField Image depthOfKnoweldgeToolTip;
	@UiField CheckBox chkLevelRecall,chkLevelSkillConcept,chkLevelStrategicThinking,chkLevelExtendedThinking,rightsChkBox;
	@UiField Button browseCentury,cancelButton;
	@UiField InlineLabel agreeText,andText,additionalText,commuGuideLinesAnr, termsAndPolicyAnr,privacyAnr,copyRightAnr;
	@UiField(provided = true)
	AppSuggestBox standardSgstBox,centurySgstBox;
	@UiField BlueButtonUc addbutton;

	@UiField
	HTMLEventPanel btnStandardsBrowse;

	@UiField UlPanel ulSelectedItems;

	@UiField
	UlPanel standardsDropListValues;

	@UiField public static Label errorMessageForQuestion;

	private static final String MESSAGE_HEADER = i18n.GL0748();
	private static final String MESSAGE_CONTENT = i18n.GL0891();
	private static final String ERROR_MSG_ANSWER = i18n.GL0311();
	private static final String ERROR_MSG_HS_IMG_ANSWER = i18n.GL4014();
	private static final String ERROR_MSG_ANSWER_LENGTH =i18n.GL0878();
	private static final String ERROR_MSG_ANSWER_SELECTED =i18n.GL0312();

	private DeleteConfirmationPopupVc deleteConfirmationPopup;
	private CollectionItemDo collectionItemDo=null;
	RemoveToolTipUc removeToolTip=null;
	ToolTip toolTip=null;
	private boolean isBrowseTooltip =false;
	BrowseStandardsTooltip browseStandardsTooltip;
	private boolean isBrowseStandardsToolTip = false;
	private static final String USER_META_ACTIVE_FLAG = "0";
	List<String> standardPreflist;
	private Map<String, String> standardCodesMap = new HashMap<String, String>();
	private Map<String, String> centuryCodesMap = new HashMap<String, String>();
	ArrayList<CodeDo> standardsDo=new ArrayList<CodeDo>();
	Set<CodeDo> deletedStandardsDo=new HashSet<CodeDo>();
	private AppMultiWordSuggestOracle standardSuggestOracle;
	private AppMultiWordSuggestOracle centurySuggestOracle;
	private SearchDo<CodeDo> standardSearchDo = new SearchDo<CodeDo>();
	private SearchDo<StandardFo> centurySearchDo = new SearchDo<StandardFo>();
	final StandardsPreferenceOrganizeToolTip standardsPreferenceOrganizeToolTip=new StandardsPreferenceOrganizeToolTip();
	String courseCode="";
	private static final String FLT_CODE_ID = "id";
	boolean isEditResource=false;

	PopupPanel centuryPopup=new PopupPanel();
	Map<Long, String> centurySelectedValues = new HashMap<Long, String>();
	AddCenturyPresenter centuryPresenterWidget=null;

	ArrayList<Integer> depthOfKnowledges= new ArrayList<Integer>();
	private HandlerRegistration handlerRegistration=null;

	boolean isAddBtnClicked=true,isRightsClicked=false,validationValue=false,isSaveButtonClicked=false;


	private static final int QUESTION_TEXT_LENGTH =500;
	private static final int EXPLAINATION_TEXT_LENGTH =500;
	private static final int ANSWER_CHOICE_HINTS_TEXT_LENGTH =150;
	private static final String ERROR_MSG_QUESTION_LENGTH =i18n.GL0880();
	private static final String ERROR_MSG_EXPLAINATION_LENGTH =i18n.GL0879();
	private static final String ERROR_MSG_QUESTION = i18n.GL0310();
	private static final String ERROR_MSG_HINTS_LENGTH = i18n.GL0877();
	private static final String ERROR_MSG_HINTS = i18n.GL2201();

	private String questionType;
	private String hsType=i18n.GL3228_1();

	List<ProfanityCheckDo> profanityList,hintsListForProfanity;


	private boolean isCCSSAvailable = false;
	private boolean isNGSSAvailable = false;
	private boolean isTEKSAvailable = false;
	private boolean isCAAvailable = false;

	String codeID="",code="",label="";
	List<Integer> selectedValues=new ArrayList<>();

	public FolderDo courseObjG;

	List<LiPanelWithClose> collectionLiPanelWithCloseArray = new ArrayList<>();

	String[] standardsTypesArray = new String[]{i18n.GL3379(),i18n.GL3322(),i18n.GL3323(),i18n.GL3324(),i18n.GL3325(),i18n.GL3321()};


	ArrayList<String> isValidHintsList = new ArrayList<String>();

	private TermsAndPolicyVc termsAndPolicyVc;
	private TermsOfUse termsOfUse;
	private CopyRightPolicyVc copyRightPolicy;

	public QuestionTypeView() {
		initializeAutoSuggestedBox();
		setWidget(uiBinder.createAndBindUi(this));
		setHeaderAndBodyText("HS_IMG");
		questionTypeHeader.getElement().setId("lblQuestionTypeHeader");
		questionTypeText.getElement().setId("lblQuestionTypeText");
		questionText.getElement().setId("pnlQuestionText");
		questionText.getElement().setInnerHTML(" "+i18n.GL0863());
		/*questionNameTextArea.getElement().setId("tinyMCEQuestionNameTextArea");
		questionNameTextArea.getElement().setAttribute("maxlength", "500");
		questionNameTextArea.markAsBlankPanel.setVisible(false);*/
//		questionNameTextArea.showTinyMceToolBar();
		questionNameTextAreaContainer.getElement().setId("questionNameTextAreaContainer");
		explainationTextAreaContainer.getElement().setId("explainationTextAreaContainer");
		questionNameErrorLbl.getElement().setId("errlblQuestionNameErrorLbl");
		errorMessageForQuestion.getElement().setId("errlblErrorMessageForQuestion");
		addQuestionImg.setText(i18n.GL0860());
		addQuestionImg.getElement().setId("lnkAddQuestionImg");
		addQuestImgContainer.getElement().setId("pnlAddQuestImgContainer");
		questionHotSpotAnswerChoiceContainer.getElement().setId("pnlQuestionHotSpotAnswerChoiceContainer");

		advancedContainer.getElement().setId("pnladvancedContainer");
		addHintsAnc.setText(i18n.GL3210_1() +i18n.GL_SPL_OPEN_SMALL_BRACKET()+5+i18n.GL3207_1()+i18n.GL_SPL_CLOSE_SMALL_BRACKET());
		addExplanationAnc.setText( i18n.GL3208_1());
		addDepthOfKnowledgeAnc.setText(i18n.GL3209_1());
		addStandardsAnc.setText(i18n.GL0575());
		addCenturyAnc.setText(i18n.GL3199());
		advancedLbl.setText(i18n.GL3096());

		/**
		 * Explanation
		 */
		explanationContainer.getElement().setId("fpnlExplanationContainer");
		explainationErrorLbl.getElement().setId("errlblExplainationErrorLbl");
		eHearderIconExplanation.getElement().setId("eHearderIconExplanation");
		eHearderIconExplanation.addClickHandler(new MinimizePanelsClickHandler());

		explanationContainer.getElement().setId("fpnlExplanationContainer");

		explanationLabel.setText(" "+i18n.GL0867());
		explanationLabel.getElement().setId("lblExplanationLabel");
		explainationErrorLbl.getElement().setId("errlblExplainationErrorLbl");
		/**
		 * hints
		 */
		hintsContainer.getElement().setId("pnlHintsContainer");
		errorMessageForHintsCheck.getElement().setId("errlblErrorMessageForHintsCheck");

		/**
		 * Depth of Knowledge
		 */
		eHearderIconDepthOfKnowledge.getElement().setId("eHearderIconDepthOfKnowledge");
		eHearderIconDepthOfKnowledge.addClickHandler(new MinimizePanelsClickHandler());

		depthOfKnowledgeHeader.getElement().setId("lblDepthOfKnowledgeHeader");
		depthOfKnoweldgeToolTip.getElement().setId("imgDepthOfKnoweldgeToolTip");
		depthOfKnowledgeTitle.getElement().setId("lblDepthOfKnowledgeTitle");


		depthOfKnowledgeHeader.setText(i18n.GL1693());

		chkLevelRecall.getElement().setId("chkChkLevelRecall");
		chkLevelSkillConcept.getElement().setId("chkChkLevelSkillConcept");
		chkLevelStrategicThinking.getElement().setId("chkChkLevelStrategicThinking");
		chkLevelExtendedThinking.getElement().setId("chkChkLevelExtendedThinking");

		chkLevelRecall.setText(i18n.GL1645());
		chkLevelSkillConcept.setText(i18n.GL1646());
		chkLevelStrategicThinking.setText(i18n.GL1647());
		chkLevelExtendedThinking.setText(i18n.GL1648());
		depthOfKnoweldgeToolTip.setUrl("images/mos/questionmark.png");
		depthOfKnoweldgeToolTip.setTitle("Question Mark");

		/**
		 * Standards
		 */
		standardContainer.getElement().setId("fpnlStandardContainer");
		eHearderIconStandards.getElement().setId("eHearderIconStandards");
		eHearderIconStandards.addClickHandler(new MinimizePanelsClickHandler());
		standardsDefaultText.getElement().setId("lblStandardsDefaultText");
		standardSgstBox.getElement().setId("appSuggestBoxStandardSgstBox");
		standardSgstBox.getElement().setAttribute("style", "box-sizing:content-box;width:65%;height:19px");

		standardsDefaultText.setText(i18n.GL1682());


		errorContainer.add(standardsPreferenceOrganizeToolTip);
		standardMaxMsg.getElement().setId("lblStandardMaxMsg");


		/**
		 * century
		 */
		eHearderIconCentury.getElement().setId("eHearderIconCentury");
		eHearderIconCentury.addClickHandler(new MinimizePanelsClickHandler());
		centuryDefaultText.setText(i18n.GL3199());
		centurySgstBox.getElement().setAttribute("style", "box-sizing:content-box;width:85%;height:19px");


		/**
		 * Terms Policy
		 */
		rightsChkBox.addClickHandler(new rightsChecked());
		rightsChkBox.getElement().setId("chkRights");
		rightsLbl.setText(i18n.GL0869());
		rightsLbl.getElement().setId("lblRightsLbl");
		lblContentRights.getElement().setId("epnlLblContentRights");
		panelContentRights.getElement().setId("pnlPanelContentRights");
		rightsContent.getElement().setId("pnlRightsContent");
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

		/**
		 * add
		 */
		buttonContainer.getElement().setId("pnlButtonContainer");
		addQuestionResourceButton.getElement().setId("epnlAddQuestionResourceButton");
		loadingTextLbl.setText(i18n.GL0591().toLowerCase());
		loadingTextLbl.getElement().setId("lblLoadingTextLbl");
		loadingTextLbl.getElement().getStyle().setFontStyle(FontStyle.ITALIC);

		addbutton.setText(i18n.GL0590());
		addbutton.getElement().setId("btnAdd");
		cancelButton.setText(i18n.GL0142());
		cancelButton.getElement().setAttribute("style", "margin-left:10px;float: none;display: inline-block;");
		cancelButton.getElement().setId("btnCancel");

		setTextAndStyle();
		Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				hideDropDown(event);
			}
		});

		standardContainer.getElement().setId("standardsContainerBswn");
		standardsCont.getElement().setAttribute("style", "position:relative;");


		btnStandardsBrowse.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (!standardsDropListValues.getElement().getAttribute("style").equalsIgnoreCase("display:block;top:0;left:33.5em;color:#515151;")) {
					standardsDropListValues.getElement().setAttribute("style", "display:block;top:0;left:33.5em;color:#515151;");
				} else {
					standardsDropListValues.getElement().removeAttribute("style");
				}
			}
		});

		ansChoiceErrMsg.getElement().setId("lblAnsChoiceErrMsg");
	}


	public void setTextAndStyle(){
		/**
		 * Explanation
		 */
		explanationContainer.setVisible(false);
		addExplanationAnc.addStyleName("advancedOptionsTabs");
		addExplanationAnc.removeStyleName("advancedOptionsTabActive");
		explanationLabel.getElement().getStyle().setDisplay(Display.INLINE);
		addExplanationAnc.setVisible(true);

		/**
		 * Depth of Knowledge
		 */
		depthOfKnowledgeContainer.setVisible(false);
		addDepthOfKnowledgeAnc.addStyleName("advancedOptionsTabs");
		addDepthOfKnowledgeAnc.removeStyleName("advancedOptionsTabActive");
		addDepthOfKnowledgeAnc.setVisible(true);

		/**
		 * Standards
		 */
		standardContainer.setVisible(false);
		addStandardsAnc.addStyleName("advancedOptionsTabs");
		addStandardsAnc.removeStyleName("advancedOptionsTabActive");
		errorContainer.setVisible(false);
		addStandardsAnc.setVisible(true);

		/**
		 * century
		 */
		centuryContainer.setVisible(false);
		addCenturyAnc.addStyleName("advancedOptionsTabs");
		addCenturyAnc.removeStyleName("advancedOptionsTabActive");
		addCenturyAnc.setVisible(true);

		/**
		 * Hints
		 */
		hintsContainer.setVisible(false);
		addHintsAnc.addStyleName("advancedOptionsTabs");
		addHintsAnc.removeStyleName("advancedOptionsTabActive");
		addHintsAnc.setText(i18n.GL3210_1() +i18n.GL_SPL_OPEN_SMALL_BRACKET()+5+i18n.GL3207_1()+i18n.GL_SPL_CLOSE_SMALL_BRACKET());
		addHintsAnc.setVisible(true);

		/**
		 * Add
		 */
		loadingTextLbl.setVisible(false);
		loadingTextLbl.setText(i18n.GL0591().toLowerCase());
	}




	public void setTextForTheFields(){
		String value = StringUtil.generateMessage(i18n.GL2103(), "500");
		charLimitLbl.setText(value);
		StringUtil.setAttributes(charLimitLbl.getElement(), "charLimitLbl", value, value);

		charLimitExplanation.setText(value);
		StringUtil.setAttributes(charLimitExplanation.getElement(), "charLimitExplanation", value, value);

		addClickEventsForCheckBox();

	}


	public void setHeaderAndBodyText(String tabType){
		if( tabType.equals("HS_IMG")){
			questionTypeHeader.setText(i18n.GL3226_1());
			questionTypeText.setText(i18n.GL4011());
		}else if(tabType.equals("HS_TXT")){
			questionTypeHeader.setText(i18n.GL4013());
			questionTypeText.setText(i18n.GL4012());
		}
	}


	@UiHandler("addQuestionImg")
	public void clickOnAddQuestImg(ClickEvent event){

		if(addQuestImgContainer.getElement().hasChildNodes()){
			addQuestImgContainer.setVisible(true);
			addQuestionImg.setVisible(false);
			setImageContainer();

		}else{
			uploadQuestionImage();
		}


	}

	public void setImageContainer(){

		if(addQuestImgContainer.getElement().hasChildNodes()){
			addQuestionImg.removeStyleName("ancTab");
			addQuestionImg.addStyleName("ancTabActive");
		}else{
			addQuestionImg.addStyleName("ancTab");
			addQuestionImg.removeStyleName("ancTabActive");
		}
	}

	public void uploadQuestionImage(){
		getUiHandlers().questionImageUpload();
	}


	public void uploadAnswerImage(){
		getUiHandlers().answerImageUpload();
	}


	@Override
	public void getRevealType() {
		getHideRightsToolTip();
		centuryPresenterWidget=AppClientFactory.getInjector().getAddCenturyPresenterWidget();
		setCenturyData();
		setTextForTheFields();
	}

	public void clearTinyMce(){
		questionNameTextArea=new TinyMCE(500);
		explainationTextArea= new TinyMCE(500);
		questionNameTextAreaContainer.clear();
		explainationTextAreaContainer.clear();
		questionNameTextArea.setCharacterLimit(500);
		questionNameTextArea.showTinyMceToolBar();
		explainationTextArea.setCharacterLimit(500);
		questionNameTextArea.getElement().setId("tinyMCEQuestionNameTextArea");
		questionNameTextArea.getElement().setAttribute("maxlength", "500");
		questionNameTextArea.markAsBlankPanel.setVisible(false);
		questionNameTextAreaContainer.add(questionNameTextArea);
		explainationTextAreaContainer.add(explainationTextArea);
	}


	@Override
	public void setImageUrl(String fileName,String fileNameWithoutRepository,boolean isQuestionImage,boolean isUserOwnResourceImage){

		double randNumber = Math.random();

		if(isQuestionImage){
			AddQuestionImg addQuestionImage = new AddQuestionImg();
			addQuestionImage.setQuestionImage(fileName+"?"+randNumber);
			addQuestionImage.setFileName(fileNameWithoutRepository);
			addQuestImgContainer.clear();
			addQuestionImg.getElement().getStyle().setDisplay(Display.NONE);
			addQuestImgContainer.add(addQuestionImage);
			setImageHandler();


			addQuestionImage.getChangeImgLbl().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(getQuestionEditMode()){
						getUiHandlers().questionImageUpload(collectionItemDo.getCollectionItemId());
					}else{
						getUiHandlers().questionImageUpload();
					}
				}
			});
			addQuestionImage.getRemoveImgLbl().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					boolean isQuestionEditMode=getQuestionEditMode();
					if(isQuestionEditMode){
						deleteConfirmationPopup=new DeleteConfirmationPopupVc(MESSAGE_HEADER,MESSAGE_CONTENT);
					}else{
						addQuestImgContainer.clear();
						addQuestionImg.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
					}
				}
			});
		}
		else{
		}
	}


	public void setImageHandler(){
		if(addQuestImgContainer.getElement().hasChildNodes()){
			AddQuestionImg addQuestionImage=(AddQuestionImg)addQuestImgContainer.getWidget(0);
			addQuestionImage.geteHearderIconImage().addClickHandler(new MinimizePanelsClickHandler());
		}
	}

	private class MinimizePanelsClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			if(event.getRelativeElement().getId().equalsIgnoreCase("eHearderIconImage")){
				addQuestImgContainer.setVisible(false);
				addQuestionImg.setVisible(true);
				setImageContainer();
			}else if(event.getRelativeElement().getId().equalsIgnoreCase("eHearderIconExplanation")){
				explanationContainer.setVisible(false);
				addExplanationAnc.setVisible(true);
				addExplanationAnc.setText(i18n.GL3208_1());
				setExplanationContainer();
			}else if(event.getRelativeElement().getId().equalsIgnoreCase("eHearderIconHint")){
				hintsContainer.setVisible(false);
				int widgetsCount=hintsContainer.getWidgetCount();
				for(int i=0;i<widgetsCount;){
					AddHintsView addHintsView =(AddHintsView) hintsContainer.getWidget(i);
					if(addHintsView.hintTextBox.getText().equalsIgnoreCase("")){
						addHintsView.removeFromParent();
						widgetsCount=hintsContainer.getWidgetCount();
						i--;
					}else{
						addHintsView.showHintsMessage(i+1);
						addHintsAnc.removeStyleName("advancedOptionsTabs");
						addHintsAnc.addStyleName("advancedOptionsTabActive");
					}

					i++;
				}
				int count=hintsContainer.getWidgetCount();
				addHintsAnc.setText(i18n.GL3210_1()+i18n.GL_SPL_OPEN_SMALL_BRACKET()+(5-count)+i18n.GL3207_1()+i18n.GL_SPL_CLOSE_SMALL_BRACKET());

			}else if(event.getRelativeElement().getId().equalsIgnoreCase("eHearderIconDepthOfKnowledge")){
				depthOfKnowledgeContainer.setVisible(false);
				addDepthOfKnowledgeAnc.setVisible(true);
				addDepthOfKnowledgeAnc.setText(i18n.GL3209_1());
				setDepthOfKnowledgeContainer();
			}else if(event.getRelativeElement().getId().equalsIgnoreCase("eHearderIconStandards")){
				standardContainer.setVisible(false);
				addStandardsAnc.setVisible(true);
				addStandardsAnc.setText(i18n.GL0575());
				setStandardsContainer();
			}else if(event.getRelativeElement().getId().equalsIgnoreCase("eHearderIconCentury")){
				centuryContainer.setVisible(false);
				addCenturyAnc.setVisible(true);
				addCenturyAnc.setText(i18n.GL3199());
				setCenturyContainer();
			}
		}
	}

	public boolean getQuestionEditMode(){
		return collectionItemDo!=null?true:false;
	}
	private class DeleteConfirmationPopupVc extends ConfirmationPopupVc{
		public DeleteConfirmationPopupVc(String messageHeader,String messageContent){
			super(messageHeader, messageContent);
			setPopupZindex(9999);
			setGlassZindex(9998);
			setScrollDisable();
		}
		@Override
		public void onDelete(ClickEvent clickEvent) {
			setImageContainer();
			getUiHandlers().removeQuestionImage(collectionItemDo.getCollectionItemId());
		}
		public void hide() {
			super.hide();

		}
	}

	public void setHotSpotAnswerFields(){
		questionHotSpotAnswerChoiceContainer.clear();
		final AddHotSpotQuestionAnswerChoice addQuestionAnswer=new AddHotSpotQuestionAnswerChoice(this);
		if(questionType.equalsIgnoreCase("HS_IMG")){
			addQuestionAnswer.imageRDButtonClick();
		}else{
			addQuestionAnswer.textRDButtonClick();
		}
		questionHotSpotAnswerChoiceContainer.add(addQuestionAnswer);

	}

	public void setExplanationContainer(){
		if(explainationTextArea.getText().isEmpty() || explainationTextArea.getText().trim().length()==0){
			addExplanationAnc.addStyleName("advancedOptionsTabs");
			addExplanationAnc.removeStyleName("advancedOptionsTabActive");
		}else{
			addExplanationAnc.removeStyleName("advancedOptionsTabs");
			addExplanationAnc.addStyleName("advancedOptionsTabActive");
		}

	}

	public void setHintsContainer(){
		if(hintsContainer.getWidgetCount()>0){
			addHintsAnc.removeStyleName("advancedOptionsTabs");
			addHintsAnc.addStyleName("advancedOptionsTabActive");
		}else{
			addHintsAnc.addStyleName("advancedOptionsTabs");
			addHintsAnc.removeStyleName("advancedOptionsTabActive");
		}
	}

	public void setDepthOfKnowledgeContainer(){

		boolean isSelected=false;
		if(chkLevelRecall.isChecked() || chkLevelSkillConcept.isChecked() || chkLevelStrategicThinking.isChecked() || chkLevelExtendedThinking.isChecked()){
			isSelected=true;
		}

		if(isSelected){
			addDepthOfKnowledgeAnc.removeStyleName("advancedOptionsTabs");
			addDepthOfKnowledgeAnc.addStyleName("advancedOptionsTabActive");
		}else{
			addDepthOfKnowledgeAnc.addStyleName("advancedOptionsTabs");
			addDepthOfKnowledgeAnc.removeStyleName("advancedOptionsTabActive");
		}

	}


	@UiHandler("addExplanationAnc")
	public void clickOnExplanationAnc(ClickEvent event){
		addExplanationAnc.setVisible(false);
		addExplanationAnc.addStyleName("advancedOptionsTab");
		addExplanationAnc.removeStyleName("advancedOptionsTabActive");
		explanationContainer.setVisible(true);
		explainationTextArea.showTinyMceToolBar();
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
			addHintsAnc.getElement().getStyle().setDisplay(Display.NONE);
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
			addHintsAnc.addStyleName("advancedOptionsTabs");
			addHintsAnc.removeStyleName("advancedOptionsTabActive");
			addHintsAnc.setText(i18n.GL3210_1()+i18n.GL_SPL_OPEN_SMALL_BRACKET()+(5-hintWidgetsCount)+i18n.GL3207_1()+i18n.GL_SPL_CLOSE_SMALL_BRACKET());
			addHintsAnc.getElement().getStyle().setDisplay(Display.BLOCK);
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

	@UiHandler("addHintsAnc")
	public void clickOnHintsLabel(ClickEvent event){
		Window.enableScrolling(false);
		hintsContainer.setVisible(true);
		int widgetCount=hintsContainer.getWidgetCount();
		addHintsAnc.addStyleName("advancedOptionsTabs");
		addHintsAnc.removeStyleName("advancedOptionsTabActive");
		addHintsAnc.setText(i18n.GL3210_1()+i18n.GL_SPL_OPEN_SMALL_BRACKET()+(4-widgetCount)+i18n.GL3207_1()+i18n.GL_SPL_CLOSE_SMALL_BRACKET());
		final AddHintsView addHints = new AddHintsView(widgetCount+1);
		addHintsTextArea(addHints);
	}

	public void addClickEventsForCheckBox(){
		chkLevelRecall.addClickHandler(new AddCheckBoxClickHandler());
		chkLevelSkillConcept.addClickHandler(new AddCheckBoxClickHandler());
		chkLevelStrategicThinking.addClickHandler(new AddCheckBoxClickHandler());
		chkLevelExtendedThinking.addClickHandler(new AddCheckBoxClickHandler());
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
	public class AddCheckBoxClickHandler implements ClickHandler  {

		@Override
		public void onClick(ClickEvent event) {
			CheckBox checkBox = (CheckBox) event.getSource();
			boolean checked = checkBox.getValue();
			depthOfKnowledges.clear();
			setDOKCheckBoxes();
		}
	}


	public void setDOKCheckBoxes(){
		if(chkLevelRecall.getValue()){
			checkboxSelectedDo depthObj=new checkboxSelectedDo();
			depthObj.setSelected(true);
			depthObj.setValue(chkLevelRecall.getText());
			depthOfKnowledges.add(depthObj.getId());
		}else{
			checkboxSelectedDo depthObj=new checkboxSelectedDo();
			depthObj.setSelected(false);
			depthObj.setValue(chkLevelRecall.getText());
			depthOfKnowledges.add(depthObj.getId());
		}

		if(chkLevelSkillConcept.getValue()){
			checkboxSelectedDo depthObj=new checkboxSelectedDo();
			depthObj.setSelected(true);
			depthObj.setValue(chkLevelSkillConcept.getText());
			depthOfKnowledges.add(depthObj.getId());
		}else{
			checkboxSelectedDo depthObj=new checkboxSelectedDo();
			depthObj.setSelected(false);
			depthObj.setValue(chkLevelSkillConcept.getText());
			depthOfKnowledges.add(depthObj.getId());
		}

		if(chkLevelStrategicThinking.getValue()){
			checkboxSelectedDo depthObj=new checkboxSelectedDo();
			depthObj.setSelected(true);
			depthObj.setValue(chkLevelStrategicThinking.getText());
			depthOfKnowledges.add(depthObj.getId());
		}else{
			checkboxSelectedDo depthObj=new checkboxSelectedDo();
			depthObj.setSelected(false);
			depthObj.setValue(chkLevelStrategicThinking.getText());
			depthOfKnowledges.add(depthObj.getId());
		}

		if(chkLevelExtendedThinking.getValue()){
			checkboxSelectedDo depthObj=new checkboxSelectedDo();
			depthObj.setSelected(true);
			depthObj.setValue(chkLevelExtendedThinking.getText());
			depthOfKnowledges.add(depthObj.getId());
		}else{
			checkboxSelectedDo depthObj=new checkboxSelectedDo();
			depthObj.setSelected(false);
			depthObj.setValue(chkLevelExtendedThinking.getText());
			depthOfKnowledges.add(depthObj.getId());
		}
	}


	@UiHandler("addDepthOfKnowledgeAnc")
	public void clickOnaddDepthOfKnowledgeAnc(ClickEvent event){
		addDepthOfKnowledgeAnc.setVisible(false);
		addDepthOfKnowledgeAnc.addStyleName("advancedOptionsTabs");
		addDepthOfKnowledgeAnc.removeStyleName("advancedOptionsTabActive");
		depthOfKnowledgeContainer.setVisible(true);
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
			standardsDo.add(codeObjStandard);

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
		standardSgstBox.addStyleName("standardTxtBox");
		standardMaxMsg.setStyleName("standardMax");
		//standardsPanel.addStyleName("floatLeftNeeded");
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
	private List<String> getAddedStandards(FlowPanel flowPanel) {
		List<String> suggestions = new ArrayList<String>();
		for (Widget widget : flowPanel) {
			if (widget instanceof DownToolTipWidgetUc) {
				suggestions.add(((CloseLabel) ((DownToolTipWidgetUc) widget).getWidget()).getSourceText());
			}
		}
		return suggestions;
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

	private class onBrowseStandardsClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			callBrowseStandards();
		}
	}

	public void callBrowseStandards() {
		getUiHandlers().browseStandardsInfo(true, false);
	}

	@Override
	public void OnBrowseStandardsClickEvent(Button addStandardsBtn) {
		if(handlerRegistration!=null){
			handlerRegistration.removeHandler();
		}
		handlerRegistration=addStandardsBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().addUpdatedBrowseStandards();
			}
		});
	}



	public void closeStandardsPopup(){
		getUiHandlers().closeStandardsPopup();
	}


	public void hideBrowseStandardsPopup(NativePreviewEvent event){
		try{
			if(event.getTypeInt()==Event.ONMOUSEOVER){
				Event nativeEvent = Event.as(event.getNativeEvent());
				boolean target=eventTargetsPopup(nativeEvent);
				if(!target)
				{
					if(isBrowseStandardsToolTip){
						browseStandardsTooltip.hide();
					}
				}
			}
		}catch(Exception ex){}
	}
	private boolean eventTargetsPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			try{
				return browseStandardsTooltip.getElement().isOrHasChild(Element.as(target));
			}catch(Exception ex){}
		}
		return false;
	}


	@UiHandler("addStandardsAnc")
	public void clickOnaddStandardsLabel(ClickEvent event){
		addStandardsAnc.setVisible(false);
		addStandardsAnc.addStyleName("advancedOptionsTabs");
		addStandardsAnc.removeStyleName("advancedOptionsTabActive");
		standardContainer.setVisible(true);
	}

	public void setCenturyContainer(){

		if(centuryPanel.getWidgetCount()>0){
			addCenturyAnc.removeStyleName("advancedOptionsTabs");
			addCenturyAnc.addStyleName("advancedOptionsTabActive");
		}else{
			addCenturyAnc.addStyleName("advancedOptionsTabs");
			addCenturyAnc.removeStyleName("advancedOptionsTabActive");
		}
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
				for(final CodeDo codeObj:standardsDo){
					if(id.equalsIgnoreCase(String.valueOf(codeObj.getCodeId()))){
						AppClientFactory.getInjector().getResourceService().deleteTaxonomyResource(collectionItemDo.getResource().getGooruOid(), codeObj.getCodeId(), new SimpleAsyncCallback<Void>() {
							@Override
							public void onSuccess(Void result) {
								CodeDo deletedObj=new CodeDo();
								deletedObj.setCodeId(codeObj.getCodeId());
								deletedStandardsDo.add(deletedObj);
								standardsDo.remove(codeObj);
								centurySelectedValues.remove(Long.parseLong(id));
							}
						});
						this.getParent().removeFromParent();
						return;
					}
				}
			}
		};
		return new DownToolTipWidgetUc(closeLabel, description);
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
						standardsDo.add(codeObjStandard);
						centuryPanel.add(create21CenturyLabel(entry.getValue(),entry.getKey()+"",""));
					}
				}
				hideCenturyPopup();
			}
		});
	}
	/**
	 * This method will hide the century popup
	 */
	public void hideCenturyPopup(){
		centuryPopup.hide();
	}
	@UiHandler("addCenturyAnc")
	public void clickOnaddCenturyLabel(ClickEvent event){
		addCenturyAnc.setVisible(false);
		addCenturyAnc.addStyleName("advancedOptionsTabs");
		addCenturyAnc.removeStyleName("advancedOptionsTabActive");
		centuryContainer.setVisible(true);
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
	public void getHideRightsToolTip()
	{
		panelContentRights.setVisible(false);
	}


	/**
	 * add functionality
	 */

	@UiHandler("addQuestionResourceButton")
	public void clickedOnAddQuestionButton(ClickEvent event)
	{
		boolean fieldValidationCheck;
		if(questionType.equalsIgnoreCase("HS_TXT") ||questionType.equalsIgnoreCase("HS_IMG") ){
			if (isHotSpotAnswerChoiceEmpty(questionHotSpotAnswerChoiceContainer)) {
				fieldValidationCheck = false;
				isAddBtnClicked=true;
			}
			if (!isHotSpotAnswerChoiceSelected(questionHotSpotAnswerChoiceContainer)) {
				String errorMessage=ERROR_MSG_ANSWER_SELECTED;
				showErrorMessageForAnswer(errorMessage);
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
									boolean fieldValidationStaus=true;
									if(addQuestImgContainer.getElement().hasChildNodes()){
										AddQuestionImg addQuestionImage=(AddQuestionImg)addQuestImgContainer.getWidget(0);
										mediaFileName=addQuestionImage.getFileName();
									}
									clearErrorQuestionMessage();
									if(questionNameTextArea.getText()==null||questionNameTextArea.getText().trim().equals("")){
										showErrorQuestionMessage(ERROR_MSG_QUESTION);
										fieldValidationStaus=false;
										isQuestEnteredFlag=false;
										isAddBtnClicked=true;
									}
									//This regex is used to get text count with out html tags
									String questionNameText = questionNameTextArea.getText().replaceAll("\\<.*?>","");
									if(questionNameText.length()>QUESTION_TEXT_LENGTH){
										showErrorQuestionMessage(ERROR_MSG_QUESTION_LENGTH);
										fieldValidationStaus=false;
										isAddBtnClicked=true;
									}
									errorMessageForExplanation.setText("");
									if(questionNameText.length()>QUESTION_TEXT_LENGTH){
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


									else if(fieldValidationStaus && (questionType.equalsIgnoreCase("HS_TXT") || questionType.equalsIgnoreCase("HS_IMG"))){
										clearErrorQuestionMessage();

										if (isHotSpotAnswerChoiceEmpty(questionHotSpotAnswerChoiceContainer)) {
											fieldValidationStaus = false;
											isAddBtnClicked=true;
										}else{

											if(!isHintsAdded(hintsContainer)){

												if (!isHotSpotAnswerChoiceSelected(questionHotSpotAnswerChoiceContainer)) {
													String errorMessage=ERROR_MSG_ANSWER_SELECTED;
													showErrorMessageForAnswer(errorMessage);
													fieldValidationStaus = false;
													isAddBtnClicked=true;
												}else{
													isProfanityCheckForAnswerChoice(fieldValidationStaus,mediaFileName);
												}

											}else{
												profanityCheckForHints(fieldValidationStaus,mediaFileName);

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


	void clearErrorQuestionMessage(){
		errorMessageForQuestion.setText("");
		StringUtil.setAttributes(errorMessageForQuestion.getElement(), "errlblErrorMessageForQuestion", null, null);

		questionNameTextArea.getElement().removeClassName("errorBorderMessage");
	}

	void showErrorQuestionMessage(String errMessage){

		errorMessageForQuestion.setText(errMessage);
		StringUtil.setAttributes(errorMessageForQuestion.getElement(), "errlblErrorMessageForQuestion", errMessage, errMessage);

		questionNameTextArea.getElement().addClassName("errorBorderMessage");
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
				if(hintText!=null && !hintText.trim().equals("")){
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

	public boolean profanityCheckForHints(final boolean fieldValidationStaus,final String mediaFileName){
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
							addFunctionality(!validationValue,mediaFileName);
						}
					}
				}
			}
		});
		return validationValue;
	}


	public boolean isHotSpotAnswerChoiceEmpty(HTMLPanel questionHotSpotAnswerChoiceContainer){
		profanityList=new ArrayList<ProfanityCheckDo>();
		String answerChoiceValue=null;
		boolean selectedAnswerImage = false;
		final AddHotSpotQuestionAnswerChoice addHotSpotQuestionAnswerChoice=(AddHotSpotQuestionAnswerChoice)questionHotSpotAnswerChoiceContainer.getWidget(0);

		addHotSpotQuestionAnswerChoice.errorMessageforAnswerChoice.setText("");
		addHotSpotQuestionAnswerChoice.ansImageContainer.getElement().removeClassName("errorBorderMessage");
		addHotSpotQuestionAnswerChoice.textAnsContainer.getElement().removeClassName("errorBorderMessage");

		if(questionType.equalsIgnoreCase("HS_IMG")){
			int count=addHotSpotQuestionAnswerChoice.ansImageContainer.getWidgetCount();
			if(count>1){
				selectedAnswerImage=false;
			}else{
				selectedAnswerImage=true;
				addHotSpotQuestionAnswerChoice.errorMessageforAnswerChoice.setText(ERROR_MSG_HS_IMG_ANSWER);
				addHotSpotQuestionAnswerChoice.ansImageContainer.getElement().addClassName("errorBorderMessage");
			}
		}else if(questionType.equalsIgnoreCase("HS_TXT")){
			int widgetCount=addHotSpotQuestionAnswerChoice.textAnsContainer.getWidgetCount();
			for(int i=0;i<widgetCount;i++){
				AddAnswerChoice addAnswerChoice=(AddAnswerChoice) addHotSpotQuestionAnswerChoice.textAnsContainer.getWidget(i);
				answerChoiceValue=addAnswerChoice.answerTextBox.getContent().replaceAll("\\<.*?>","");

				ProfanityCheckDo profanitymodel=new ProfanityCheckDo();
				if(answerChoiceValue==null||answerChoiceValue.trim().equalsIgnoreCase("")){
					selectedAnswerImage=true;
					addHotSpotQuestionAnswerChoice.errorMessageforAnswerChoice.setText(ERROR_MSG_ANSWER);
					addAnswerChoice.answerTextBox.getElement().addClassName("errorBorderMessage");
					profanitymodel.setQuestionID(Integer.toString(i));
					profanityList.add(profanitymodel);
				}else{
					if(answerChoiceValue.trim().length()>ANSWER_CHOICE_HINTS_TEXT_LENGTH){
						selectedAnswerImage=true;
						Document.get().getElementById(addAnswerChoice.answerTextBox.getID()+"_message").setInnerText("");
						addHotSpotQuestionAnswerChoice.errorMessageforAnswerChoice.setText(ERROR_MSG_ANSWER_LENGTH);
						addAnswerChoice.answerTextBox.getElement().addClassName("errorBorderMessage");
					}else{
						selectedAnswerImage=false;
						profanitymodel.setQuestionID(Integer.toString(i));
						profanitymodel.setQuestionText(answerChoiceValue);
						profanityList.add(profanitymodel);
					}
				}

			}
		}
		return selectedAnswerImage;
	}



	/**
	 * If all validations successful, question is added to the collection.
	 */
	public void addFunctionality(boolean fieldValidationStaus,String mediaFileName){
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

			ArrayList<String> answerImageIds=new ArrayList<String>();

			if(questionType.equalsIgnoreCase("HS_TXT") || questionType.equalsIgnoreCase("HS_IMG")){

				AddHotSpotQuestionAnswerChoice addQuestionAnswerChoice=(AddHotSpotQuestionAnswerChoice)questionHotSpotAnswerChoiceContainer.getWidget(0);
				if(questionType.equalsIgnoreCase("HS_IMG")){
					hsType=i18n.GL3228_1();
					for(int i=0;i<addQuestionAnswerChoice.ansImageContainer.getWidgetCount();i++)
					{
						QuestionAnswerDo questionAnswerDo = new QuestionAnswerDo();
						final AddAnswerImg ansImage=(AddAnswerImg)addQuestionAnswerChoice.ansImageContainer.getWidget(i);
						answerImageIds.add(ansImage.getFileName());
						questionAnswerDo.setAnswerText(ansImage.getFileName());
						questionAnswerDo.setAnswerType(hsType);
						questionAnswerDo.setSequence(i+1);
						questionAnswerDo.setIsCorrect(ansImage.selectedImage);
						enteredAnswers.add(questionAnswerDo);
					}

				}else{
					hsType=i18n.GL3229_1();
					for(int i=0;i<addQuestionAnswerChoice.textAnsContainer.getWidgetCount();i++)
					{
						QuestionAnswerDo questionAnswerDo = new QuestionAnswerDo();
						final AddAnswerChoice addAnswerChoice=(AddAnswerChoice)addQuestionAnswerChoice.textAnsContainer.getWidget(i);
						questionAnswerDo.setAnswerText(addAnswerChoice.answerTextBox.getRawContent());
						questionAnswerDo.setAnswerType(hsType);
						questionAnswerDo.setSequence(i+1);
						questionAnswerDo.setIsCorrect(addAnswerChoice.isOptionSelectedButton);
						enteredAnswers.add(questionAnswerDo);
					}
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
			String qType="HS_TXT";
			if(hsType.equalsIgnoreCase(i18n.GL3228_1())){
				qType="HS_IMG";
			}
			collectionQuestionItemDo.setTypeName(qType);
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
			collectionQuestionItemDo.setSkillIds(getSelectedCenturySkills());
			collectionQuestionItemDo.setDepthOfKnowledgeIds(depthOfKnowledges);

			if(questionType.equalsIgnoreCase("HS_IMG")){
				collectionQuestionItemDo.setHlType(hsType);
				collectionQuestionItemDo.setSingleCorrectAnswer(false);
				collectionQuestionItemDo.setMediaFiles(answerImageIds);
			}else if(questionType.equalsIgnoreCase("HS_TXT")){
				collectionQuestionItemDo.setHlType(hsType);
				collectionQuestionItemDo.setSingleCorrectAnswer(false);
			}
			if(!isSaveButtonClicked){
				isSaveButtonClicked=true;
				createQuestionResource(mediaFileName,collectionQuestionItemDo);
			}
		}
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
		getUiHandlers().hidePopup();
	}


	public void createQuestionResource(String mediaFileName, CollectionQuestionItemDo collectionQuestionItemDo) {
		if(getQuestionEditMode()){
			String thumbnailUrl=null;
			if(addQuestImgContainer.getWidgetCount()>0){
				AddQuestionImg addQuestionImage=(AddQuestionImg)addQuestImgContainer.getWidget(0);
				thumbnailUrl=addQuestionImage.getFileName();
			}
			collectionQuestionItemDo.setMediaFilename(thumbnailUrl);
			getUiHandlers().v2UpdateQuestionResource(collectionItemDo,collectionQuestionItemDo,thumbnailUrl==null?null:"asset-question_"+thumbnailUrl);
		}else{
			getUiHandlers().addHSQuestionResource(mediaFileName,collectionQuestionItemDo);
		}


	}

	public void resetFields(String type) {
		clearTinyMce();
		setHeaderAndBodyText(type);
		questionType=type;
		buttonContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		resetToHints();
		setHotSpotAnswerFields();
		depthOfKnowledges.clear();
		ulSelectedItems.clear();
		centuryPanel.clear();
		setTextAndStyle();
		resetDepthOfKnowledges();
		isAddBtnClicked=true;
		isRightsClicked=false;
		validationValue=false;
		isSaveButtonClicked=false;
		clearObjects();
		ansChoiceErrMsg.setText("");
		addQuestImgContainer.clear();
		addQuestionImg.setVisible(true);
		setAncTabs();
	}

	public void clearObjects(){
		standardCodesMap.clear();
		centuryCodesMap.clear();
		standardsDo.clear();
		deletedStandardsDo.clear();
		standardSearchDo=new SearchDo<CodeDo>();
		centurySearchDo=new SearchDo<StandardFo>();
		centurySelectedValues.clear();
		depthOfKnowledges.clear();
		isValidHintsList.clear();
	}


	public void resetDepthOfKnowledges(){
		chkLevelRecall.setChecked(false);
		chkLevelSkillConcept.setChecked(false);
		chkLevelStrategicThinking.setChecked(false);
		chkLevelExtendedThinking.setChecked(false);
	}

	private boolean isHotSpotAnswerChoiceSelected(HTMLPanel questionHotSpotAnswerChoiceContainer)
	{
		boolean isAnswerChoiceSelected=false;
		AddHotSpotQuestionAnswerChoice addQuestionAnswerChoice=(AddHotSpotQuestionAnswerChoice)questionHotSpotAnswerChoiceContainer.getWidget(0);

		if(questionType.equalsIgnoreCase("HS_IMG")){
			for(int i=0;i<addQuestionAnswerChoice.ansImageContainer.getWidgetCount();i++)
			{
				final AddAnswerImg addAnswerImg=(AddAnswerImg)addQuestionAnswerChoice.ansImageContainer.getWidget(i);
				if(addAnswerImg.selectedImage){
					isAnswerChoiceSelected=true;
				}
			}
		}else{
			for(int i=0;i<addQuestionAnswerChoice.textAnsContainer.getWidgetCount();i++)
			{
				final AddAnswerChoice addAnswerChoice=(AddAnswerChoice)addQuestionAnswerChoice.textAnsContainer.getWidget(i);
				if(addAnswerChoice.isOptionSelectedButton){
					isAnswerChoiceSelected=true;
				}
			}
		}

		return isAnswerChoiceSelected;
	}
	void showErrorMessageForAnswer(String errMessage){

		ansChoiceErrMsg.setText(errMessage);
		StringUtil.setAttributes(ansChoiceErrMsg.getElement(), "lblErrorMessageForAnswer", errMessage, errMessage);

		questionNameTextArea.getElement().addClassName("errorBorderMessage");
	}


	public boolean isProfanityCheckForAnswerChoice(final boolean fieldValidationStaus,final String mediaFileName){


		validationValue=false;
		AppClientFactory.getInjector().getResourceService().checkProfanityForList(profanityList, new SimpleAsyncCallback<List<ProfanityCheckDo>>() {

			@Override
			public void onSuccess(List<ProfanityCheckDo> result) {
				if(questionType.equalsIgnoreCase("HS_TXT")){
					AddHotSpotQuestionAnswerChoice addQuestionAnswerChoice=(AddHotSpotQuestionAnswerChoice)questionHotSpotAnswerChoiceContainer.getWidget(0);
					for(int i=0;i<addQuestionAnswerChoice.textAnsContainer.getWidgetCount();i++)
					{
						final AddAnswerChoice addAnswerChoice=(AddAnswerChoice)addQuestionAnswerChoice.textAnsContainer.getWidget(i);
						addAnswerChoice.errorMessageforAnswerChoice.setText("");
						addAnswerChoice.getAnswerTextBox().getElement().removeClassName("errorBorderMessage");
						SetStyleForProfanity.SetStyleForProfanityForTinyMCE(addAnswerChoice.answerTextBox, addAnswerChoice.errorMessageforAnswerChoice, result.get(i).questionValue);
						if(result.get(i)!=null && result.get(i).questionValue==true){
							addAnswerChoice.errorMessageforAnswerChoice.getElement().setAttribute("style", "float: left;left: 24px;");
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
											addFunctionality(!validationValue,mediaFileName);
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



	@Override
	public void editQuestion(CollectionItemDo collectionItemDo) {
		this.collectionItemDo=collectionItemDo;
		clearTinyMce();
		if(collectionItemDo!=null){
		isEditResource=true;
		}
		explanationContainer.setVisible(false);
    	depthOfKnowledgeContainer.setVisible(false);
    	hintsContainer.setVisible(false);
    	standardContainer.setVisible(false);
    	centuryContainer.setVisible(false);


	}

	public void setAncTabs(){
		explanationContainer.setVisible(!addExplanationAnc.isVisible());
    	depthOfKnowledgeContainer.setVisible(!addDepthOfKnowledgeAnc.isVisible());
    	hintsContainer.setVisible(!addHintsAnc.isVisible());
    	standardContainer.setVisible(!addStandardsAnc.isVisible());
    	centuryContainer.setVisible(!addCenturyAnc.isVisible());
	}


	public void setEditData(){
		try{
			loadingTextLbl.setText(i18n.GL0808());
			setAncTabs();
			setEditQuestionImage();

			int type = collectionItemDo.getResource().getType() != null ? collectionItemDo.getResource().getType() : collectionItemDo.getQuestionInfo().getType();
			timer.schedule(0);

			if(collectionItemDo.getResource().getDepthOfKnowledge()!=null){
				int checkBoxCount=0;
				for (checkboxSelectedDo item : collectionItemDo.getResource().getDepthOfKnowledge()) {
					   if(item.isSelected()){
						   if(checkBoxCount==0)
						   chkLevelRecall.setChecked(true);
						   if(checkBoxCount==1)
					       chkLevelSkillConcept.setChecked(true);
						   if(checkBoxCount==2)
					       chkLevelStrategicThinking.setChecked(true);
						   if(checkBoxCount==3)
					       chkLevelExtendedThinking.setChecked(true);
					   }
					   checkBoxCount++;
					}
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
				if(collectionItemDo.getResource().getSkills()!= null && collectionItemDo.getResource().getSkills().size()>0){
					centuryPanel.clear();
					for (StandardFo standardObj : collectionItemDo.getResource().getSkills()) {
						 CodeDo codeObj=new CodeDo();
						 codeObj.setCodeId(standardObj.getCodeId());
						 codeObj.setCode(standardObj.getLabel());
						 standardsDo.add(codeObj);
						 centurySelectedValues.put(Long.parseLong(standardObj.getCodeId()+""), standardObj.getLabel());
						 centuryPanel.add(create21CenturyLabel(standardObj.getLabel(),standardObj.getCodeId()+"",""));
					}
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

				TreeSet<QuestionAnswerDo> answerChoicesSet = collectionItemDo.getResource().getAnswers() != null ? collectionItemDo.getResource().getAnswers() : collectionItemDo.getQuestionInfo().getAnswers();
				Iterator<QuestionAnswerDo> it = answerChoicesSet.iterator();


				AddHotSpotQuestionAnswerChoice addHotSpotQuestion=(AddHotSpotQuestionAnswerChoice) questionHotSpotAnswerChoiceContainer.getWidget(0);

				if(collectionItemDo.getResource()!=null){

				String HsType=	collectionItemDo.getResource().getHlType();

				if(HsType.equalsIgnoreCase(i18n.GL3229_1())){
					int widgetcount=1;
					addHotSpotQuestion.textAnsContainer.clear();
					while (it.hasNext()) {
						addHotSpotQuestion.setAnswerFields(false);
						QuestionAnswerDo answer = it.next();

						final AddAnswerChoice addAnswerChoice=new AddAnswerChoice(widgetcount+"",answer.getAnswerText());

						if(answer.isIsCorrect()){
							addAnswerChoice.isOptionSelectedButton=true;
							addAnswerChoice.optionSelectedButton.setStyleName("answerMarkSelected");
						}else{
							addAnswerChoice.optionSelectedButton.setStyleName("answerMarkDeselected");
						}


						addHotSpotQuestion.textAnsContainer.add(addAnswerChoice);
						widgetcount++;
					}
					addHotSpotQuestion.setAnswerChoices();
				}else {
					addHotSpotQuestion.ansImageContainer.clear();
					while (it.hasNext()) {
						QuestionAnswerDo answer = it.next();
						//hard code Image url for 1.5 QA
						addHotSpotQuestion.setAnswerImageUrl(collectionItemDo.getAssetURI()+collectionItemDo.getFolder()+answer.getAnswerText(), answer.getAnswerText(), true,answer.isIsCorrect());

					}
				}
				}



			setDepthOfKnowledgeContainer();
			setHintsContainer();
			setStandardsContainer();
			setCenturyContainer();


			}catch(Exception e){
				AppClientFactory.printSevereLogger("QuestionTypeView setEditData:::"+e.getMessage());
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

			setImageUrl(tumbnailUrl, null, true, false);

		}

	}


	Timer timer=new Timer() {

		@Override
		public void run() {
			String explanation = collectionItemDo.getResource().getExplanation() != null ? collectionItemDo.getResource().getExplanation() : collectionItemDo.getQuestionInfo().getExplanation();

			questionNameTextArea.setText(collectionItemDo.getResource().getTitle());
			explainationTextArea.setText(explanation);


			setExplanationContainer();
		}
	};

	@Override
	public void setMetadata(final CollectionQuestionItemDo collectionQuestionItemDo) {

		Timer timer1=new Timer() {
			@Override
			public void run() {
				questionNameTextArea.setText(collectionQuestionItemDo.getQuestionText());
				explainationTextArea.setText(collectionQuestionItemDo.getExplanation());

				if(addExplanationAnc.isVisible()){setExplanationContainer();}
			}
		};
		timer1.schedule(0);

		if(collectionQuestionItemDo.getDepthOfKnowledgeIds()!=null){
			int checkBoxCount=0;
			resetDepthOfKnowledges();
			depthOfKnowledges.clear();
			for (Integer item : collectionQuestionItemDo.getDepthOfKnowledgeIds()) {
				/*
				   if(item.isSelected()){
					   if(checkBoxCount==0)
					   chkLevelRecall.setChecked(true);
					   if(checkBoxCount==1)
				       chkLevelSkillConcept.setChecked(true);
					   if(checkBoxCount==2)
				       chkLevelStrategicThinking.setChecked(true);
					   if(checkBoxCount==3)
				       chkLevelExtendedThinking.setChecked(true);
				   }*/
				   checkBoxCount++;
				}

			setDOKCheckBoxes();

			}

		TreeSet<QuestionHintsDo> hintset = new TreeSet<QuestionHintsDo>(collectionQuestionItemDo.getHints().get("hint"));
		TreeSet<QuestionHintsDo> hintsList = hintset;
		Iterator<QuestionHintsDo> iterator = hintsList.iterator();
		hintsContainer.clear();
		while (iterator.hasNext()) {
			QuestionHintsDo hints = iterator.next();
			int widgetCount=hintsContainer.getWidgetCount();
	        final AddHintsView addHints = new AddHintsView(widgetCount+1,hints.getHintText());
	        addHintsTextArea(addHints);
		}
		int count=hintsContainer.getWidgetCount();
		addHintsAnc.setText(i18n.GL3210_1()+i18n.GL_SPL_OPEN_SMALL_BRACKET()+(5-count)+i18n.GL3207_1()+i18n.GL_SPL_CLOSE_SMALL_BRACKET());

		centuryPanel.clear();
		//standardsPanel.clear();
		standardsDo.clear();

		Map<Long, String> centurySkills=collectionQuestionItemDo.getCenturySelectedValues();

		for (Map.Entry<Long, String> entry : centurySkills.entrySet())
		{
			CodeDo codeDo=new CodeDo();
			codeDo.setDepth((short) 2);
			codeDo.setLabel(entry.getValue());
			codeDo.setCodeId(entry.getKey().intValue());
			standardsDo.add(codeDo);
			centurySelectedValues.put(entry.getKey(),entry.getValue());
			centuryPanel.add(create21CenturyLabel(entry.getValue(), entry.getKey()+"", centuryCodesMap.get(entry.getKey())));
		}

		for(int j=0;j<collectionQuestionItemDo.getTaxonomySet().get("taxonomyCode").size();j++){
			Integer codeID=collectionQuestionItemDo.getTaxonomySet().get("taxonomyCode").get(j).getCodeId();
			if(!centurySkills.containsKey(codeID.longValue())){
			CodeDo codeDo=new CodeDo();
			codeDo.setDepth((short) 2);
			String label=collectionQuestionItemDo.getTaxonomySet().get("taxonomyCode").get(j).getLabel();
			String code=collectionQuestionItemDo.getTaxonomySet().get("taxonomyCode").get(j).getCode();
			codeDo.setLabel(code);
			codeDo.setCode(code);
			codeDo.setCodeId(codeID);
			standardsDo.add(codeDo);
			ulSelectedItems.add(createStandardLabel(code,String.valueOf(codeID),label));
			}
		}

		HashMap<String,Boolean> moreOptions= collectionQuestionItemDo.getMoreOptions();

		addExplanationAnc.setVisible(moreOptions.get("explanation"));
		addHintsAnc.setVisible(moreOptions.get("hints"));
		addDepthOfKnowledgeAnc.setVisible(moreOptions.get("DOK"));
		addStandardsAnc.setVisible(moreOptions.get("standards"));
		addCenturyAnc.setVisible(moreOptions.get("21stcentury"));

		setAncTabs();


		if(addDepthOfKnowledgeAnc.isVisible()){setDepthOfKnowledgeContainer();}
		if(addHintsAnc.isVisible()){setHintsContainer();}
		if(addCenturyAnc.isVisible()){setCenturyContainer();}

	}

	public CollectionQuestionItemDo getMetadata(){
		final CollectionQuestionItemDo collectionQuestionItemDo = new CollectionQuestionItemDo();

		Timer timer2=new Timer() {
			@Override
			public void run() {
		collectionQuestionItemDo.setQuestionText(questionNameTextArea!=null && questionNameTextArea.getText()!=null?questionNameTextArea.getText():"");
		collectionQuestionItemDo.setExplanation(explainationTextArea!=null && explainationTextArea.getText()!=null?explainationTextArea.getText():"");
			}
		};
		collectionQuestionItemDo.setDepthOfKnowledgeIds(depthOfKnowledges);

		ArrayList<QuestionHintsDo> enteredHints = new ArrayList<QuestionHintsDo>();
		HashMap<String,ArrayList<QuestionHintsDo>> hintsMap = new HashMap<String,ArrayList<QuestionHintsDo>>();

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
		collectionQuestionItemDo.setHints(hintsMap);


		collectionQuestionItemDo.setCenturySelectedValues(centurySelectedValues);

		if(collectionQuestionItemDo.getTaxonomySet()!=null){
			collectionQuestionItemDo.getTaxonomySet().clear();
		}


		timer2.schedule(0);

		HashMap<String,Boolean> moreOptions=new HashMap<String, Boolean>();

		moreOptions.put("explanation",addExplanationAnc.isVisible());
		moreOptions.put("hints", addHintsAnc.isVisible());
		moreOptions.put("DOK", addDepthOfKnowledgeAnc.isVisible());
		moreOptions.put("standards", addStandardsAnc.isVisible());
		moreOptions.put("21stcentury", addCenturyAnc.isVisible());

		collectionQuestionItemDo.setMoreOptions(moreOptions);
		return collectionQuestionItemDo;
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


	@Override
	public void removeQuestionEditImage() {
		deleteConfirmationPopup.hide();
		addQuestImgContainer.clear();
	    addQuestionImg.setVisible(true);
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
	@Override
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

	public void showStandardsPopup(String standardVal, String standardsDesc,
			List<LiPanelWithClose> collectionLiPanelWithCloseArray){
		getUiHandlers().showStandardsPopup(standardVal,standardsDesc,collectionLiPanelWithCloseArray);
	}

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


	@Override
	public void onSelection(SelectionEvent<Suggestion> event) {
		// TODO Auto-generated method stub

	}


	@Override
	public void setUpdatedStandardsCode(String setStandardsVal, int setStandardsIdVal, String setStandardDesc) {
		// TODO Auto-generated method stub

	}

	public List<Integer> getSelectedCenturySkills(){
		List<Integer> selectedValues=new ArrayList<Integer>();
		int size=centuryPanel.getWidgetCount();
		System.out.println("AsdfADS::"+size);
		for(int i=0;i<size;i++){
			DownToolTipWidgetUc downToolTipWidgetUc=(DownToolTipWidgetUc)centuryPanel.getWidget(i);
			selectedValues.add(Integer.parseInt(downToolTipWidgetUc.getElement().getId()));
		}
		return selectedValues;
	}
	public void setStandardsContainer(){
		if(ulSelectedItems.getWidgetCount()>0){
			addStandardsAnc.removeStyleName("advancedOptionsTabs");
			addStandardsAnc.addStyleName("advancedOptionsTabActive");
		}else{
			addStandardsAnc.addStyleName("advancedOptionsTabs");
			addStandardsAnc.removeStyleName("advancedOptionsTabActive");
		}
	}

}