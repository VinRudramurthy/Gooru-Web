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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.application.shared.model.content.QuestionAnswerDo;
import org.ednovo.gooru.application.shared.model.content.QuestionHintsDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionCBundle;
import org.ednovo.gooru.client.mvp.shelf.event.GetEditPageHeightEvent;
import org.ednovo.gooru.client.uc.AppMultiWordSuggestOracle;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.uc.AppSuggestBox;
import org.ednovo.gooru.client.uc.CloseLabel;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.RemoveToolTipUc;
import org.ednovo.gooru.client.uc.StandardsPreferenceOrganizeToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.ui.TinyMCE;
import org.ednovo.gooru.client.util.ImageUtil;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
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
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;

public abstract class EditQuestionPopupVc extends AppPopUp implements SelectionHandler<SuggestOracle.Suggestion>{

	@UiField
	ListBox questionTypeListBox;

	private TinyMCE resourceQuestion = null;
	private TinyMCE explainationTextArea = null;

	@UiField
	HTMLEventPanel questionTabButton,trueOrFlaseButton,openEndedButton,
	activityPanel,handoutPanel,homeworkPanel,gamePanel,presentationPanel,referenceMaterialPanel,quizPanel,curriculumPlanPanel,
	lessonPlanPanel,unitPlanPanel,projectPlanPanel,readingPanel,textbookPanel,articlePanel,bookPanel;
	
	@UiField
	HTMLPanel addQuestionHintsContainer, questionTextRichTextboxContainer,
			questionExplantionRichTextboxContainer, listQuestionType, answerchoiceTitleContainer, panelPleaseWait,panelControls,typeLbl,educationalTitle,educationalpanel,
			activityText,handoutText,homeworkText,gameText,presentationText,referenceMaterialText,quizText,
			curriculumPlanText,lessonPlanText,unitPlanText,projectPlanText,readingText,textbookText,articleText,bookText;

	@UiField
	Button btnSave, btnCancel,browseStandards;
	
	@UiField
	Label mandatoryEducationalLbl,resourceEducationalLabel,standardMaxMsg,depthOfKnowledgeHeader,lblEditQuestionTitle, lblQuestion, lblPleaseWait,depthOfKnowledgeTitle,educationalDropDownLbl,
	standardsDefaultText;
	
	@UiField 
	CheckBox chkLevelRecall,chkLevelSkillConcept,chkLevelStrategicThinking,chkLevelExtendedThinking;
	
	@UiField Anchor ancMultipleChoice, ancTrueOfFalse,ancOpenEnded;
	
	@UiField
	Label lblAddHints, lblChoices, lblCorrect, lblAddAnswerChoice,lblExplanation,
			lblAddQuestion, lblErrorMessageForExplanation;
	
	@UiField RadioButton multipleChoiceRadioButton,trueOrFalseRadioButton,openEndedRadioButton;
	
	@UiField FlowPanel standardContainer;

	RemoveToolTipUc removeToolTip = null;
	boolean isSaveButtonClicked = false,educationalDropDownLblOpen=false;

	@UiField(provided = true)
	AppSuggestBox standardSgstBox;
	
	@UiField FlowPanel standardsPanel;
	private AppMultiWordSuggestOracle standardSuggestOracle;
	private SearchDo<CodeDo> standardSearchDo = new SearchDo<CodeDo>();
	private static final String FLT_CODE_ID = "id";
	List<String> standardPreflist=new ArrayList<String>();
	private Map<String, String> standardCodesMap = new HashMap<String, String>();
	String courseCode="";
	
	public Label getAddQuestion() {
		return lblAddQuestion;
	}

	public void setAddQuestion(Label lblAddQuestion) {
		this.lblAddQuestion = lblAddQuestion;
	}

	@UiField
	HTMLPanel educationalUsePanel,questionImageContainer;

	@UiField
	UpdateQuestionImageView updateQuestionImageView;

	public HTMLPanel getQuestionImageContainer() {
		return questionImageContainer;
	}

	public UpdateQuestionImageView getUpdateQuestionImageView() {
		return updateQuestionImageView;
	}

	public void setQuestionImageContainer(HTMLPanel questionImageContainer) {
		this.questionImageContainer = questionImageContainer;
	}

	public void setUpdateQuestionImageView(
			UpdateQuestionImageView updateQuestionImageView) {
		this.updateQuestionImageView = updateQuestionImageView;
	}
	
	@UiField
	Label errorMessageForQuestion, errorMessageForAnsCheck, ansChoiceErrMsg,
			errorMessageForHintsCheck;
	@UiField
	UpdateResourceBundle editQuestionStyle;
	@UiField
	HTMLPanel questionAnswerChoiceContainer;
	CollectionItemDo collectionItemDo;
	String[] anserChoiceArray = new String[] { "A", "B", "C", "D", "E" };
	private static final int EXPLAINATION_TEXT_LENGTH = 400;

	private static final String DEFULT_IMAGE_PREFIX = "images/default-";
	private static final String PNG = ".png";

	private List<Widget> answerChoicesList = new ArrayList<Widget>();
	
	private boolean isQuestion = false;
	private boolean isUserResource = false;

	private static EditQuestionPopupVcUiBinder uiBinder = GWT
			.create(EditQuestionPopupVcUiBinder.class);

	interface EditQuestionPopupVcUiBinder extends
			UiBinder<Widget, EditQuestionPopupVc> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public EditQuestionPopupVc(CollectionItemDo collectionItemDo) {
		super();
		standardSuggestOracle = new AppMultiWordSuggestOracle(true);
		standardSearchDo.setPageSize(10);
		standardSgstBox = new AppSuggestBox(standardSuggestOracle) {
			final StandardsPreferenceOrganizeToolTip standardsPreferenceOrganizeToolTip=new StandardsPreferenceOrganizeToolTip();
			@Override
			public void keyAction(String text,KeyUpEvent event) {
				text=text.toUpperCase();
				standardsPreferenceOrganizeToolTip.hide();
				standardSearchDo.setSearchResults(null);
				boolean standardsPrefDisplayPopup = false;
				if(!courseCode.isEmpty()) {
					Map<String,String> filters = new HashMap<String, String>();
					filters.put(FLT_CODE_ID,courseCode);
					standardSearchDo.setFilters(filters);
				}
				standardSearchDo.setQuery(text);
				if (text != null && text.trim().length() > 0) {
					standardsPreferenceOrganizeToolTip.hide();
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
					
						standardsPreferenceOrganizeToolTip.hide();
						AppClientFactory.getInjector().getSearchService().getSuggestStandardByFilterCourseId(standardSearchDo, new SimpleAsyncCallback<SearchDo<CodeDo>>() {
							
							@Override
							public void onSuccess(SearchDo<CodeDo> result) {
								setStandardSuggestions(result);
								
							}							
						});

					}
					
				
			}

			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		standardSgstBox.addSelectionHandler(this);
		this.getElement().getStyle().setWidth(640, Unit.PX);
		this.collectionItemDo = collectionItemDo;
		setWidget(uiBinder.createAndBindUi(this));
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
		show();
		center();
		 this.getElement().getStyle().setTop(100, Unit.PX);
		 displayQuestionResource();
		 
		 lblEditQuestionTitle.getElement().setId("lblLblEditQuestionTitle");
		 questionTabButton.getElement().setId("epnlQuestionTabButton");
		 multipleChoiceRadioButton.getElement().setId("rdMultipleChoiceRadioButton");
		 trueOrFalseRadioButton.getElement().setId("rdTrueOrFalseRadioButton");
		 ancMultipleChoice.getElement().setId("lnkAncMultipleChoice");
		 trueOrFlaseButton.getElement().setId("epnlTrueOrFlaseButton");
		 ancTrueOfFalse.getElement().setId("lnkAncTrueOfFalse");
		 openEndedButton.getElement().setId("epnlOpenEndedButton");
		 openEndedRadioButton.getElement().setId("rdOpenEndedRadioButton");
		 ancOpenEnded.getElement().setId("lnkAncOpenEnded");
		 listQuestionType.getElement().setId("pnlListQuestionType");
		 typeLbl.getElement().setId("pnlTypeLbl");
		 questionTypeListBox.getElement().setId("lbQuestionTypeListBox");
		 lblQuestion.getElement().setId("lblLblQuestion");
		 questionTextRichTextboxContainer.getElement().setId("pnlQuestionTextRichTextboxContainer");
		 lblAddQuestion.getElement().setId("lblLblAddQuestion");
		 errorMessageForQuestion.getElement().setId("errlblErrorMessageForQuestion");
		 questionImageContainer.getElement().setId("pnlQuestionImageContainer");
		 updateQuestionImageView.getElement().setId("UpdateQuestionImageView");
		 answerchoiceTitleContainer.getElement().setId("pnlAnswerchoiceTitleContainer");
		 lblChoices.getElement().setId("lblLblChoices");
		 lblCorrect.getElement().setId("lblLblCorrect");
		 questionAnswerChoiceContainer.getElement().setId("pnlQuestionAnswerChoiceContainer");
		 lblAddAnswerChoice.getElement().setId("lblLblAddAnswerChoice");
		 errorMessageForAnsCheck.getElement().setId("errlblErrorMessageForAnsCheck");
		 ansChoiceErrMsg.getElement().setId("lblAnsChoiceErrMsg");
		 lblExplanation.getElement().setId("lblLblExplanation");
		 questionExplantionRichTextboxContainer.getElement().setId("pnlQuestionExplantionRichTextboxContainer");
		 lblErrorMessageForExplanation.getElement().setId("lblLblErrorMessageForExplanation");
		 addQuestionHintsContainer.getElement().setId("pnlAddQuestionHintsContainer");
		 lblAddHints.getElement().setId("lblLblAddHints");
		 errorMessageForHintsCheck.getElement().setId("errlblErrorMessageForHintsCheck");
		 depthOfKnowledgeTitle.getElement().setId("lblDepthOfKnowledgeTitle");
		 
		 depthOfKnowledgeHeader.setText(i18n.GL1643());
		 depthOfKnowledgeHeader.getElement().setId("lblDepthOfKnowledgeHeader");
		 depthOfKnowledgeHeader.getElement().setAttribute("alt", i18n.GL1643());
		 depthOfKnowledgeHeader.getElement().setAttribute("title", i18n.GL1643());
		 chkLevelRecall.setText(i18n.GL1645());
		 chkLevelRecall.getElement().setId("chkChkLevelRecall");
		 chkLevelRecall.getElement().setAttribute("alt", i18n.GL1645());
		 chkLevelRecall.getElement().setAttribute("title", i18n.GL1645());
		 chkLevelSkillConcept.setText(i18n.GL1646());
		 chkLevelSkillConcept.getElement().setId("chkChkLevelSkillConcept");
		 chkLevelSkillConcept.getElement().setAttribute("alt", i18n.GL1646());
		 chkLevelSkillConcept.getElement().setAttribute("title", i18n.GL1646());
	 
		 chkLevelStrategicThinking.setText(i18n.GL1647());
		 chkLevelStrategicThinking.getElement().setId("chkChkLevelStrategicThinking");
		 chkLevelStrategicThinking.getElement().setAttribute("alt", i18n.GL1647());
		 chkLevelStrategicThinking.getElement().setAttribute("title", i18n.GL1647());
		 chkLevelExtendedThinking.setText(i18n.GL1648());
		 chkLevelExtendedThinking.getElement().setId("chkChkLevelExtendedThinking");
		 chkLevelExtendedThinking.getElement().setAttribute("alt", i18n.GL1648());
		 chkLevelExtendedThinking.getElement().setAttribute("title", i18n.GL1648());
		 educationalTitle.getElement().setId("pnlEducationalTitle");
		 educationalpanel.getElement().setId("pnlEducationalpanel");
		 resourceEducationalLabel.getElement().setId("lblResourceEducationalLabel");
		 educationalDropDownLbl.getElement().setId("lblEducationalDropDownLbl");
		 mandatoryEducationalLbl.getElement().setId("lblMandatoryEducationalLbl");
		 educationalUsePanel.getElement().setId("pnlEducationalUsePanel");
		 activityPanel.getElement().setId("epnlActivityPanel");
		 activityText.getElement().setId("pnlActivityText");
		 handoutPanel.getElement().setId("epnlHandoutPanel");
		 handoutText.getElement().setId("pnlHandoutText");
		 homeworkPanel.getElement().setId("epnlHomeworkPanel");
		 homeworkText.getElement().setId("pnlHomeworkText");
		 gamePanel.getElement().setId("epnlGamePanel");
		 gameText.getElement().setId("pnlGameText");
		 presentationPanel.getElement().setId("epnlPresentationPanel");
		 presentationText.getElement().setId("pnlPresentationText");
		 referenceMaterialPanel.getElement().setId("epnlReferenceMaterialPanel");
		 referenceMaterialText.getElement().setId("pnlReferenceMaterialText");
		 quizPanel.getElement().setId("epnlQuizPanel");
		 quizText.getElement().setId("pnlQuizText");
		 curriculumPlanPanel.getElement().setId("epnlCurriculumPlanPanel");
		 curriculumPlanText.getElement().setId("pnlCurriculumPlanText");
		 lessonPlanPanel.getElement().setId("epnlLessonPlanPanel");
		 lessonPlanText.getElement().setId("pnlLessonPlanText");
		 unitPlanPanel.getElement().setId("epnlUnitPlanPanel");
		 unitPlanText.getElement().setId("pnlUnitPlanText");
		 projectPlanPanel.getElement().setId("epnlProjectPlanPanel");
		 projectPlanText.getElement().setId("pnlProjectPlanText");
		 readingPanel.getElement().setId("epnlReadingPanel");
		 readingText.getElement().setId("pnlReadingText");
		 textbookPanel.getElement().setId("epnlTextbookPanel");
		 textbookText.getElement().setId("pnlTextbookText");
		 articlePanel.getElement().setId("epnlArticlePanel");
		 articleText.getElement().setId("pnlArticleText");
		 bookPanel.getElement().setId("epnlBookPanel");
		 bookText.getElement().setId("pnlBookText");
		 standardContainer.getElement().setId("fpnlStandardContainer");
		 standardsDefaultText.getElement().setId("lblStandardsDefaultText");
		 standardSgstBox.getElement().setId("standardSgstBox");
		 standardMaxMsg.getElement().setId("lblStandardMaxMsg");
		 standardsPanel.getElement().setId("fpnlStandardsPanel");
		 panelPleaseWait.getElement().setId("pnlPanelPleaseWait");
		 lblPleaseWait.getElement().setId("lblLblPleaseWait");
		 panelControls.getElement().setId("pnlPanelControls");
		 btnCancel.getElement().setAttribute("style", "margin-left:10px");
		 browseStandards.addClickHandler(new callBrowseStandards());
	}

	public abstract void callBrowseStandardsInfo(boolean val,boolean userResource);
	
	private class callBrowseStandards implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			isQuestion = true;
			isUserResource = false;
			callBrowseStandardsInfo(isQuestion,isUserResource);
		}
		
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		questionTextRichTextboxContainer.clear();
		questionExplantionRichTextboxContainer.clear();
		resourceQuestion = new TinyMCE();
		explainationTextArea = new TinyMCE();
		questionTextRichTextboxContainer.add(resourceQuestion);
		questionExplantionRichTextboxContainer.add(explainationTextArea);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				displayQuestionResource();
			}
		});

	}
	@Override
	public void onSelection(SelectionEvent<Suggestion> event) {
		addStandard(standardSgstBox.getValue(), getCodeIdByCode(standardSgstBox.getValue(), standardSearchDo.getSearchResults()));
		standardSgstBox.setText("");
		standardSuggestOracle.clear();
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
	/**
	 * Adding new standard for the collection , will check it has more than
	 * fifteen standards
	 * 
	 * @param standard
	 *            which to be added for the collection
	 */
	public void addStandard(String standard, String id) {
		if (standardsPanel.getWidgetCount() <5) {
			if (standard != null && !standard.isEmpty()) {
				standardsPanel.add(createStandardLabel(standard, id, standardCodesMap.get(id)));
			}
		} else {
			standardMaxShow();
			standardSgstBox.setText("");
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
				this.getParent().removeFromParent();
			}
		};
		return new DownToolTipWidgetUc(closeLabel, description);
	}
	public void standardMaxShow() {
		standardSgstBox.addStyleName(CollectionCBundle.INSTANCE.css().standardTxtBox());
		standardMaxMsg.setStyleName(CollectionCBundle.INSTANCE.css().standardMax());
		standardsPanel.addStyleName(CollectionCBundle.INSTANCE.css().floatLeftNeeded());
		new FadeInAndOut(standardMaxMsg.getElement(), 5000, 5000);
	}
	public void setStandardSuggestions(SearchDo<CodeDo> standardSearchDo) {
		standardSuggestOracle.clear();
		this.standardSearchDo = standardSearchDo;
		if (this.standardSearchDo.getSearchResults() != null) {
			List<String> sources = getAddedStandards(standardsPanel);
			for (CodeDo code : standardSearchDo.getSearchResults()) {
				if (!sources.contains(code.getCode())) {
					standardSuggestOracle.add(code.getCode());
				}
				standardCodesMap.put(code.getCodeId() + "", code.getLabel());
			}
		}
		standardSgstBox.showSuggestionList();
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
	public void displayQuestionResource() {
		String tumbnailUrl;
		String category = collectionItemDo.getResource().getCategory();
		final String categoryString = category == null
				|| category.startsWith("assessment") ? ImageUtil.QUESTION
				: category.toLowerCase();

		if (collectionItemDo.getResource().getAssets() != null
				&& collectionItemDo.getResource().getAssets().size() > 0) {
			tumbnailUrl = collectionItemDo.getCollection().getAssetURI()
					+ collectionItemDo.getResource().getFolder()
					+ collectionItemDo.getResource().getAssets().get(0)
							.getAsset().getName();
			updateQuestionImageView.getUpdateQuestionImage()
					.setUrl(tumbnailUrl);
		} else {

			updateQuestionImageView.getUpdateQuestionImage().setUrl(
					DEFULT_IMAGE_PREFIX + categoryString + PNG);
		}
		try {
			resourceQuestion.setText(collectionItemDo.getResource().getTitle());
		} catch (Exception e) {
			AppClientFactory.printSevereLogger("EditQuestionPopupVc displayQuestionResource title:::"+e);
		}
		try {
			explainationTextArea.setText(collectionItemDo.getResource()
					.getExplanation());
		} catch (Exception e) {
			AppClientFactory.printSevereLogger("EditQuestionPopupVc displayQuestionResource explanation:::"+e);
		}
		listQuestionType.getElement().getStyle().setDisplay(Display.NONE);
		
		setLabelAndIds();
		
		TreeSet<QuestionHintsDo> hintsList = collectionItemDo.getResource()
				.getHints();
		if (collectionItemDo.getResource().getType() == 1) {
			questionTypeListBox.setItemSelected(0, true);
			showMulipleAnswerChoice();
		} else if (collectionItemDo.getResource().getType() == 3) {
			questionTypeListBox.setItemSelected(1, true);
			showTrueOrFalseAnswerChoice();
		}else if (collectionItemDo.getResource().getType() == 6){
			showOpenEndedQuestion();
			questionTypeListBox.setItemSelected(2, true);
		}
		
		
		if (collectionItemDo.getResource().getType() != 6){
			Iterator<QuestionHintsDo> iterator = hintsList.iterator();
			while (iterator.hasNext()) {
				QuestionHintsDo hints = iterator.next();
				int widgetCount = addQuestionHintsContainer.getWidgetCount();
				final UpdateQuestionHints updateQuestionHints = new UpdateQuestionHints(
						widgetCount + 1, hints.getHintText());
				addQuestionHintsContainer.add(updateQuestionHints);
				addEventsToHints(updateQuestionHints);
				showRemoveToolTip(updateQuestionHints.hintDelLbl);
				if (addQuestionHintsContainer.getWidgetCount() >= 5) {
					lblAddHints.getElement().getStyle().setDisplay(Display.NONE);
				}
			}
			
			addAnswerChoices();
		}
		
		center();
	}
	
	public void setLabelAndIds(){
		typeLbl.getElement().setInnerText(i18n.GL1499());
		typeLbl.getElement().setAttribute("alt", i18n.GL1499());
		typeLbl.getElement().setAttribute("title", i18n.GL1499());
		btnSave.setText(i18n.GL0590());
		btnSave.getElement().setAttribute("alt", i18n.GL0141());
		btnSave.getElement().setAttribute("title", i18n.GL0141());
		btnCancel.setText(i18n.GL0142());
		btnCancel.getElement().setAttribute("alt", i18n.GL0142());
		btnCancel.getElement().setAttribute("title", i18n.GL0142());
		btnCancel.getElement().setAttribute("style", "margin-left:10px");
		lblEditQuestionTitle.setText(i18n.GL0304());
		lblEditQuestionTitle.getElement().setAttribute("alt", i18n.GL0304());
		lblEditQuestionTitle.getElement().setAttribute("title", i18n.GL0304());
		ancMultipleChoice.setText(i18n.GL0305());
		ancMultipleChoice.getElement().setAttribute("alt", i18n.GL0305());
		ancMultipleChoice.getElement().setAttribute("title", i18n.GL0305());
		ancTrueOfFalse.setText(i18n.GL0306());
		ancTrueOfFalse.getElement().setAttribute("alt", i18n.GL0306());
		ancTrueOfFalse.getElement().setAttribute("title", i18n.GL0306());
		ancOpenEnded.setText(i18n.GL0307());
		ancOpenEnded.getElement().setAttribute("alt", i18n.GL0307());
		ancOpenEnded.getElement().setAttribute("title", i18n.GL0307());
		lblQuestion.setText(i18n.GL0308() + i18n.GL_SPL_STAR());
		lblQuestion.getElement().setAttribute("alt", i18n.GL0308() + i18n.GL_SPL_STAR());
		lblQuestion.getElement().setAttribute("title", i18n.GL0308() + i18n.GL_SPL_STAR());
		lblAddQuestion.setText(i18n.GL_SPL_PLUS() + " " + i18n.GL0309());
		lblAddQuestion.getElement().setAttribute("alt", i18n.GL_SPL_PLUS() + " " + i18n.GL0309());
		lblAddQuestion.getElement().setAttribute("title", i18n.GL_SPL_PLUS() + " " + i18n.GL0309());
		lblChoices.setText(i18n.GL0313() + i18n.GL_SPL_STAR());
		lblChoices.getElement().setAttribute("alt", i18n.GL0313() + i18n.GL_SPL_STAR());
		lblChoices.getElement().setAttribute("title", i18n.GL0313() + i18n.GL_SPL_STAR());
		lblCorrect.setText(i18n.GL0314());
		lblCorrect.getElement().setAttribute("alt", i18n.GL0314());
		lblCorrect.getElement().setAttribute("title", i18n.GL0314());
		lblAddAnswerChoice.setText(i18n.GL_SPL_PLUS() + " " +i18n.GL0315());
		lblAddAnswerChoice.getElement().setAttribute("alt", i18n.GL_SPL_PLUS() + " " +i18n.GL0315());
		lblAddAnswerChoice.getElement().setAttribute("title", i18n.GL_SPL_PLUS() + " " +i18n.GL0315());
		
		lblExplanation.setText(i18n.GL0316());
		lblExplanation.getElement().setAttribute("alt", i18n.GL0316());
		lblExplanation.getElement().setAttribute("title", i18n.GL0316());
		
		lblAddHints.setText(i18n.GL_SPL_PLUS() + " " + i18n.GL0317());
		lblAddHints.getElement().setAttribute("alt", i18n.GL_SPL_PLUS() + " " + i18n.GL0317());
		lblAddHints.getElement().setAttribute("title", i18n.GL_SPL_PLUS() + " " + i18n.GL0317());
		
		lblPleaseWait.setText(i18n.GL0339());
		lblPleaseWait.getElement().setAttribute("alt", i18n.GL0339());
		lblPleaseWait.getElement().setAttribute("title", i18n.GL0339());
		
		panelPleaseWait.setVisible(false);
		panelControls.setVisible(true);
		
		btnSave.getElement().setId("btnSave");
		btnCancel.getElement().setId("btnCancel");
	}

	@UiHandler("questionTypeListBox")
	public void onChangeQuestionType(ChangeEvent event) {
		String questionTypeValue = questionTypeListBox
				.getValue(questionTypeListBox.getSelectedIndex());
		if (questionTypeValue.equals("MC")) {
			if (collectionItemDo.getResource().getType() == 1) {
				addAnswerChoices();
			} else {
				// resetToMultipleChoice();
				setMultipleChoiceAnswerFields();
				clearErrorMessages();
			}
		} else if (questionTypeValue.equals("T/F")) {
			if (collectionItemDo.getResource().getType() == 3) {
				addAnswerChoices();
			} else {
				// resetAllFields();
				setTrueOrFalseFields();
				clearErrorMessages();
			}

		}
	}

	public void setTrueOrFalseFields() {
		questionAnswerChoiceContainer.clear();
		lblAddAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		for (int i = 0; i < 2; i++) {
			int widgetsCount = questionAnswerChoiceContainer.getWidgetCount();
			final UpdateQuestionAnswerChoice updateQuestionAnswerChoice = new UpdateQuestionAnswerChoice(
					anserChoiceArray[widgetsCount]);
			updateQuestionAnswerChoice.answerTextBoxContainer.clear();
			if (i == 0) {
				updateQuestionAnswerChoice.fieldValue = "True";
				HTML trueHtmlString = new HTML("True");
				trueHtmlString.getElement().getStyle().setMarginTop(8, Unit.PX);
				updateQuestionAnswerChoice.answerTextBoxContainer
						.add(trueHtmlString);
			} else {
				updateQuestionAnswerChoice.fieldValue = "False";
				HTML falseHtmlString = new HTML("False");
				falseHtmlString.getElement().getStyle()
						.setMarginTop(8, Unit.PX);
				updateQuestionAnswerChoice.answerTextBoxContainer
						.add(falseHtmlString);
			}
			updateQuestionAnswerChoice.optionSelectedButton
					.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							selectOrDeselectOption(updateQuestionAnswerChoice);
						}
					});
			questionAnswerChoiceContainer.add(updateQuestionAnswerChoice);
		}
	}

	public void setMultipleChoiceAnswerFields() {
		questionAnswerChoiceContainer.clear();
		for (int i = 0; i < 2; i++) {
			int widgetCount = questionAnswerChoiceContainer.getWidgetCount();
			final UpdateQuestionAnswerChoice updateQuestionAnswerChoice = new UpdateQuestionAnswerChoice(
					anserChoiceArray[widgetCount]);
			updateQuestionAnswerChoice.optionSelectedButton
					.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							selectOrDeselectOption(updateQuestionAnswerChoice);
						}
					});
			questionAnswerChoiceContainer.add(updateQuestionAnswerChoice);
		}
	}

	public void clearErrorMessages() {
		errorMessageForQuestion.setText("");
		lblErrorMessageForExplanation.setText("");
		ansChoiceErrMsg.setText("");
		for (int i = 0; i < questionAnswerChoiceContainer.getWidgetCount(); i++) {
			UpdateQuestionAnswerChoice updateQuestionAnswerChoice = (UpdateQuestionAnswerChoice) questionAnswerChoiceContainer
					.getWidget(i);
			updateQuestionAnswerChoice.errorMessageforAnswerChoice.setText("");
		}
	}

	public void addAnswerChoices() {
		questionAnswerChoiceContainer.clear();
		TreeSet<QuestionAnswerDo> answerChoicesSet = collectionItemDo
				.getResource().getAnswers();
		Iterator<QuestionAnswerDo> it = answerChoicesSet.iterator();
		int i = 0;
		while (it.hasNext()) {
			QuestionAnswerDo answer = it.next();
			final UpdateQuestionAnswerChoice updateQuestionAnswerChoice = new UpdateQuestionAnswerChoice(
					i, answer.getAnswerText(), collectionItemDo.getResource()
							.getType());
			if (collectionItemDo.getResource().getType() == 3) {
				updateQuestionAnswerChoice.answerTextBoxContainer.clear();

				updateQuestionAnswerChoice.fieldValue = answer.getAnswerText();
				HTML trueHtmlString = new HTML(answer.getAnswerText());
				trueHtmlString.getElement().getStyle().setMarginTop(8, Unit.PX);
				updateQuestionAnswerChoice.answerTextBoxContainer
						.add(trueHtmlString);

			}
			updateQuestionAnswerChoice.setLabelName(anserChoiceArray[i]);
			if (answer.isIsCorrect() == true) {
				updateQuestionAnswerChoice.optionSelectedButton
						.setStyleName(editQuestionStyle.answerSelected());
			}
			questionAnswerChoiceContainer.add(updateQuestionAnswerChoice);
			updateQuestionAnswerChoice.optionSelectedButton
					.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {

							selectOrDeselectOption(updateQuestionAnswerChoice);
						}
					});

			if (i > 1) {
				addMouseOverToAnswerWidget(updateQuestionAnswerChoice);
				showRemoveToolTip(updateQuestionAnswerChoice.ansChoiceDeleteButton);
			}

			if (questionAnswerChoiceContainer.getWidgetCount() >= 5) {
				lblAddAnswerChoice.getElement().getStyle()
						.setDisplay(Display.NONE);
			}
			i++;
		}
		if (collectionItemDo.getResource().getType() == 3) {
			lblAddAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	public void showRemoveToolTip(final Label deleteButton) {

		deleteButton.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				removeToolTip = new RemoveToolTipUc();
				removeToolTip.show();
				int left = event.getRelativeElement().getAbsoluteLeft() - 16;
				int top = event.getRelativeElement().getAbsoluteTop() + 27;
				removeToolTip.setPopupPosition(left, top);
				removeToolTip.getElement().getStyle().setZIndex(999);
			}
		});
		deleteButton.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				removeToolTip.hide();
			}
		});
	}

	public void selectOrDeselectOption(
			UpdateQuestionAnswerChoice updateQuestionAnswerChoice) {
		ansChoiceErrMsg.setText("");
		removeSelectedOption(updateQuestionAnswerChoice);
		if (updateQuestionAnswerChoice.optionSelectedButton.getStyleName()
				.equals(editQuestionStyle.answerDeselected())) {
			updateQuestionAnswerChoice.optionSelectedButton
					.setStyleName(editQuestionStyle.answerSelected());
		} else if (updateQuestionAnswerChoice.optionSelectedButton
				.getStyleName().equals(editQuestionStyle.answerSelected())) {
			updateQuestionAnswerChoice.optionSelectedButton
					.setStyleName(editQuestionStyle.answerDeselected());
		}
	}

	public void removeSelectedOption(
			UpdateQuestionAnswerChoice selectedAnswerChoice) {
		for (int i = 0; i < questionAnswerChoiceContainer.getWidgetCount(); i++) {
			UpdateQuestionAnswerChoice updateQuestionAnswerChoice = (UpdateQuestionAnswerChoice) questionAnswerChoiceContainer
					.getWidget(i);
			if (updateQuestionAnswerChoice.optionSelectedButton.getStyleName()
					.equals(editQuestionStyle.answerSelected())
					&& selectedAnswerChoice.optionSelectedButton.getStyleName()
							.equals(editQuestionStyle.answerSelected())) {
				updateQuestionAnswerChoice.optionSelectedButton
						.setStyleName(editQuestionStyle.answerSelected());
			} else {
				updateQuestionAnswerChoice.optionSelectedButton
						.setStyleName(editQuestionStyle.answerDeselected());
			}
		}
	}

	@UiHandler("btnCancel")
	public void cancelPopUp(ClickEvent clickEvent) {
		AppClientFactory.fireEvent(new GetEditPageHeightEvent(this, true));
		hide();
	}
	@UiHandler("lblAddHints")
	public void addHints(ClickEvent clickEvent) {

		int widgetCount = addQuestionHintsContainer.getWidgetCount();
		final UpdateQuestionHints updateQuestionHints = new UpdateQuestionHints(
				widgetCount + 1, null);
		addQuestionHintsContainer.add(updateQuestionHints);
		if (addQuestionHintsContainer.getWidgetCount() >= 5) {

			lblAddHints.getElement().getStyle().setDisplay(Display.NONE);
		}

		addEventsToHints(updateQuestionHints);
		showRemoveToolTip(updateQuestionHints.hintDelLbl);

	}

	protected void refreshHintNumber() {
		int hintWidgetsCount = addQuestionHintsContainer.getWidgetCount();
		for (int i = 0; i < hintWidgetsCount; i++) {
			Widget childWidget = addQuestionHintsContainer.getWidget(i);
			UpdateQuestionHints updateQuestionHints = (UpdateQuestionHints) childWidget;
			updateQuestionHints.hintsChoice.setText("" + (i + 1));
		}
		if (addQuestionHintsContainer.getWidgetCount() < 5) {
			lblAddHints.getElement().getStyle().setDisplay(Display.BLOCK);
		}

	}

	public void addEventsToHints(final UpdateQuestionHints updateQuestionHints) {
		updateQuestionHints.hintDelLbl.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				updateQuestionHints.removeFromParent();

				refreshHintNumber();
				if (addQuestionHintsContainer.getWidgetCount() <= 3) {
					addQuestionHintsContainer.setVisible(true);
				}
				removeToolTip.hide();

			}
		});
		updateQuestionHints.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				updateQuestionHints.hintDelLbl.getElement().getStyle()
						.setDisplay(Display.BLOCK);
			}
		});
		updateQuestionHints.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				updateQuestionHints.hintDelLbl.getElement().getStyle()
						.setDisplay(Display.NONE);
			}
		});

	}

	@UiHandler("btnSave")
	public void updateResourceQuestion(ClickEvent clickEvent) {
		boolean fieldValidationStaus = true;

		errorMessageForQuestion.setText("");
		if (resourceQuestion.getText() == null
				|| resourceQuestion.getText().trim().equals("")) {
			errorMessageForQuestion.setText(i18n.GL0310());
			errorMessageForQuestion.getElement().setAttribute("alt", i18n.GL0310());
			errorMessageForQuestion.getElement().setAttribute("title", i18n.GL0310());
			fieldValidationStaus = false;
		} else if (resourceQuestion.getText().length() > 500) {
			errorMessageForQuestion.setText(i18n.GL0143());
			errorMessageForQuestion.getElement().setAttribute("alt", i18n.GL0143());
			errorMessageForQuestion.getElement().setAttribute("title", i18n.GL0143());
			fieldValidationStaus = false;
		}
		ansChoiceErrMsg.setText("");
		if (!questionTypeListBox.getValue(questionTypeListBox.getSelectedIndex()).equalsIgnoreCase("OE")){
			if (isAnswerChoiceEmpty()) {
				// ansChoiceErrMsg.setText(ERROR_MSG_ANSWER);
				fieldValidationStaus = false;
			}
			if (!isAnswerChoiceSelected()) {
				ansChoiceErrMsg.setText(i18n.GL0312());
				ansChoiceErrMsg.getElement().setAttribute("alt", i18n.GL0312());
				ansChoiceErrMsg.getElement().setAttribute("title", i18n.GL0312());
				fieldValidationStaus = false;
			}
		}
		lblErrorMessageForExplanation.setText("");

		if (explainationTextArea.getText().trim().length() > EXPLAINATION_TEXT_LENGTH) {
			lblErrorMessageForExplanation.setText(i18n.GL0143());
			lblErrorMessageForExplanation.getElement().setAttribute("alt", i18n.GL0143());
			lblErrorMessageForExplanation.getElement().setAttribute("title", i18n.GL0143());
			fieldValidationStaus = false;
		}
		if (fieldValidationStaus) {
			CollectionQuestionItemDo collectionQuestionItemDo = new CollectionQuestionItemDo();
			String questionText = resourceQuestion.getRawContent().trim();
			String explaination = explainationTextArea.getText();
			if (explaination != null && !explaination.equals("")) {
				explaination = explainationTextArea.getRawContent().trim();
			}

			ArrayList<QuestionAnswerDo> enteredAnswers = new ArrayList<QuestionAnswerDo>();
			ArrayList<QuestionHintsDo> enteredHints = new ArrayList<QuestionHintsDo>();
			HashMap<String, ArrayList<QuestionAnswerDo>> answerMap = new HashMap<String, ArrayList<QuestionAnswerDo>>();
			HashMap<String, ArrayList<QuestionHintsDo>> hintsMap = new HashMap<String, ArrayList<QuestionHintsDo>>();

			for (int i = 0; i < questionAnswerChoiceContainer.getWidgetCount(); i++) {
				QuestionAnswerDo questionAnswerDo = new QuestionAnswerDo();
				UpdateQuestionAnswerChoice updateQuestionAnswerChoice = (UpdateQuestionAnswerChoice) questionAnswerChoiceContainer
						.getWidget(i);
				String questionTypeValue = questionTypeListBox
						.getValue(questionTypeListBox.getSelectedIndex());
				if (questionTypeValue.equals("T/F")) {
					questionAnswerDo
							.setAnswerText(updateQuestionAnswerChoice.fieldValue);
				} else if (questionTypeValue.equals("OE")){
					questionAnswerDo.setAnswerText("");
				}else {
				
					questionAnswerDo
							.setAnswerText(updateQuestionAnswerChoice.answerTextBox
									.getRawContent());
				}

				questionAnswerDo.setAnswerType("text");
				questionAnswerDo.setSequence(i + 1);
				if (updateQuestionAnswerChoice.optionSelectedButton
						.getStyleName().equals(
								editQuestionStyle.answerSelected())) {
					questionAnswerDo.setIsCorrect(true);
				} else {
					questionAnswerDo.setIsCorrect(false);
				}
				enteredAnswers.add(questionAnswerDo);
			}
			answerMap.put("answer", enteredAnswers);

			for (int i = 0; i < addQuestionHintsContainer.getWidgetCount(); i++) {
				UpdateQuestionHints updateQuestionHints = (UpdateQuestionHints) addQuestionHintsContainer
						.getWidget(i);
				QuestionHintsDo questionHintsDo = new QuestionHintsDo();
				String hintsText = updateQuestionHints.hintsTextBox.getText();
				if (hintsText != null && !hintsText.trim().equals("")) {
					hintsText = updateQuestionHints.hintsTextBox
							.getRawContent().trim();
				}
				questionHintsDo.setHintText(hintsText);
				questionHintsDo.setSequence(i + 1);
				enteredHints.add(questionHintsDo);
			}
			hintsMap.put("hint", enteredHints);
			collectionQuestionItemDo.setTypeName(questionTypeListBox
					.getValue(questionTypeListBox.getSelectedIndex()));
			collectionQuestionItemDo.setQuestionText(questionText);
			collectionQuestionItemDo.setAnswers(answerMap);
			collectionQuestionItemDo.setExplanation(explaination);
			collectionQuestionItemDo.setHints(hintsMap);
			collectionQuestionItemDo.setTitle(questionText);

			if (!isSaveButtonClicked) {
				isSaveButtonClicked = true;
				updateQuestionResource(collectionItemDo.getResource().getGooruOid(), collectionQuestionItemDo);
			}
			panelPleaseWait.setVisible(true);
			panelControls.setVisible(false);
		}
	}

	public abstract void updateQuestionResource(String collectionItemId,
			CollectionQuestionItemDo collectionQuestionItemDo);

	private boolean isAnswerChoiceSelected() {

		boolean isAnswerChoiceSelected = false;
		for (int i = 0; i < questionAnswerChoiceContainer.getWidgetCount(); i++) {
			UpdateQuestionAnswerChoice updateQuestionAnswerChoice = (UpdateQuestionAnswerChoice) questionAnswerChoiceContainer
					.getWidget(i);
			if (updateQuestionAnswerChoice.optionSelectedButton.getStyleName()
					.equals(editQuestionStyle.answerSelected())) {
				isAnswerChoiceSelected = true;
			}

		}
		return isAnswerChoiceSelected;

	}

	public boolean isAnswerChoiceEmpty() {
		boolean isAnswerChoiceSelected = false;
		for (int i = 0; i < questionAnswerChoiceContainer.getWidgetCount(); i++) {
			UpdateQuestionAnswerChoice updateQuestionAnswerChoice = (UpdateQuestionAnswerChoice) questionAnswerChoiceContainer
					.getWidget(i);
			String questionTypeValue = questionTypeListBox
					.getValue(questionTypeListBox.getSelectedIndex());
			if (questionTypeValue.equals("T/F")) {

			} else {
				String answerChoiceValue = updateQuestionAnswerChoice.answerTextBox
						.getText();
				updateQuestionAnswerChoice.errorMessageforAnswerChoice
						.setText("");
				if (answerChoiceValue == null
						|| answerChoiceValue.trim().equals("")) {
					isAnswerChoiceSelected = true;
					updateQuestionAnswerChoice.errorMessageforAnswerChoice
							.setText(i18n.GL0311());
				}

			}

		}
		return isAnswerChoiceSelected;
	}

	@UiHandler("lblAddAnswerChoice")
	public void addClickButton(ClickEvent clickEvent) {
		int widgetCount = questionAnswerChoiceContainer.getWidgetCount();
		final UpdateQuestionAnswerChoice updateQuestionAnswerChoice = new UpdateQuestionAnswerChoice(
				anserChoiceArray[widgetCount]);
		answerChoicesList.add(updateQuestionAnswerChoice);

		updateQuestionAnswerChoice.optionSelectedButton
				.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						selectOrDeselectOption(updateQuestionAnswerChoice);
					}
				});

		addMouseOverToAnswerWidget(updateQuestionAnswerChoice);
		questionAnswerChoiceContainer.add(updateQuestionAnswerChoice);
		showRemoveToolTip(updateQuestionAnswerChoice.ansChoiceDeleteButton);
		widgetCount = questionAnswerChoiceContainer.getWidgetCount();
		if (questionAnswerChoiceContainer.getWidgetCount() >= 5) {
			lblAddAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	public void refreshOptionNames() {
		for (int i = 0; i < questionAnswerChoiceContainer.getWidgetCount(); i++) {
			UpdateQuestionAnswerChoice updateQuestionAnswerChoice = (UpdateQuestionAnswerChoice) questionAnswerChoiceContainer
					.getWidget(i);
			updateQuestionAnswerChoice.setLabelName(anserChoiceArray[i]);
		}
		if (questionAnswerChoiceContainer.getWidgetCount() < 5) {
			lblAddAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
		}
	}

	public void addMouseOverToAnswerWidget(
			final UpdateQuestionAnswerChoice updateQuestionAnswerChoice) {
		updateQuestionAnswerChoice.ansChoiceDeleteButton
				.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						updateQuestionAnswerChoice.removeFromParent();
						refreshOptionNames();
						removeToolTip.hide();
					}
				});
		updateQuestionAnswerChoice.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				updateQuestionAnswerChoice.ansChoiceDeleteButton.getElement()
						.getStyle().setDisplay(Display.BLOCK);
			}
		});
		updateQuestionAnswerChoice.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				updateQuestionAnswerChoice.ansChoiceDeleteButton.getElement()
						.getStyle().setDisplay(Display.NONE);
			}
		});
	}
	public void showMulipleAnswerChoice(){
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		lblAddAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		
		questionTabButton.setStyleName(editQuestionStyle.buttonSelected());
		trueOrFlaseButton.setStyleName(editQuestionStyle.buttonDeSelected());
		openEndedButton.setStyleName(editQuestionStyle.buttonDeSelected());
		
		questionTypeListBox.setSelectedIndex(0);
		
		if (collectionItemDo.getResource().getType() == 1) {
			addAnswerChoices();
		} else {
			setMultipleChoiceAnswerFields();
			clearErrorMessages();
		}		
	}
	public void showTrueOrFalseAnswerChoice(){
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		lblAddAnswerChoice.getElement().getStyle().setDisplay(Display.BLOCK);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		
		questionTabButton.setStyleName(editQuestionStyle.buttonDeSelected());
		trueOrFlaseButton.setStyleName(editQuestionStyle.buttonSelected());
		openEndedButton.setStyleName(editQuestionStyle.buttonDeSelected());
		
		questionTypeListBox.setSelectedIndex(1);
		if (collectionItemDo.getResource().getType() == 3) {
			addAnswerChoices();
		} else {
			setTrueOrFalseFields();
			clearErrorMessages();
		}
	}
	public void showOpenEndedQuestion(){
		questionAnswerChoiceContainer.getElement().getStyle().setDisplay(Display.NONE);
		lblAddAnswerChoice.getElement().getStyle().setDisplay(Display.NONE);
		answerchoiceTitleContainer.getElement().getStyle().setDisplay(Display.NONE);
		
		questionTabButton.setStyleName(editQuestionStyle.buttonDeSelected());
		trueOrFlaseButton.setStyleName(editQuestionStyle.buttonDeSelected());
		openEndedButton.setStyleName(editQuestionStyle.buttonSelected());
		
		questionTypeListBox.setSelectedIndex(2);
		
		clearErrorMessages();
	}
	
	@UiHandler("questionTabButton")
	public void showMultipleChoise(ClickEvent event){
		showMulipleAnswerChoice();
	}
	
	@UiHandler("trueOrFlaseButton")
	public void showTrueOrFalse(ClickEvent event){
		showTrueOrFalseAnswerChoice();
	}
	
	@UiHandler("openEndedButton")
	public void showOpenEnded(ClickEvent event){
		showOpenEndedQuestion();
	}
	@UiHandler("activityPanel")
	void activityPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_activity_selected");
		resourceEducationalLabel.setText(i18n.GL1665());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1665());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1665());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("handoutPanel")
	void handoutPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_handout_selected");
		resourceEducationalLabel.setText(i18n.GL0907());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL0907());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL0907());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("homeworkPanel")
	void homeworkPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_homework_selected");
		resourceEducationalLabel.setText(i18n.GL1666());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1666());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1666());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("gamePanel")
	void gamePanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_game_selected");
		resourceEducationalLabel.setText(i18n.GL1667());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1667());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1667());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("presentationPanel")
	void presentationPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_presentation_selected");
		resourceEducationalLabel.setText(i18n.GL1668());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1668());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1668());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("referenceMaterialPanel")
	void referenceMaterialPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_reference_material_selected");
		resourceEducationalLabel.setText(i18n.GL1669());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1669());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1669());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("quizPanel")
	void quizPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_quiz_selected");
		resourceEducationalLabel.setText(i18n.GL1670());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1670());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1670());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("curriculumPlanPanel")
	void curriculumPlanPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_curriculum_plan_selected");
		resourceEducationalLabel.setText(i18n.GL1671());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1671());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1671());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("lessonPlanPanel")
	void lessonPlanPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_lesson_plan_selected");
		resourceEducationalLabel.setText(i18n.GL1672());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1672());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1672());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("unitPlanPanel")
	void unitPlanPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_unit_plan_selected");
		resourceEducationalLabel.setText(i18n.GL1673());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1673());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1673());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("projectPlanPanel")
	void projectPlanPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_project_plan_selected");
		resourceEducationalLabel.setText(i18n.GL1674());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1674());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1674());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("readingPanel")
	void readingPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_reading_selected");
		resourceEducationalLabel.setText(i18n.GL1675());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1675());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1675());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("textbookPanel")
	void textbookPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_textbook_selected");
		resourceEducationalLabel.setText(i18n.GL0909());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL0909());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL0909());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("articlePanel")
	void articlePanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_article_selected");
		resourceEducationalLabel.setText(i18n.GL1676());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1676());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1676());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	@UiHandler("bookPanel")
	void bookPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_book_selected");
		resourceEducationalLabel.setText(i18n.GL1677());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1677());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1677());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
	}
	
	@UiHandler("educationalDropDownLbl")
	public void educationalDropDownClick(ClickEvent event) {
		if (educationalDropDownLblOpen == false) {
			educationalUsePanel.setVisible(true);
			educationalDropDownLblOpen = true;
		} else {
			educationalUsePanel.setVisible(false);
			educationalDropDownLblOpen = false;
		}
	}
	public abstract void closeBrowseStandardsPopup();
	
	public void setUpdatedBrowseStandards(String setStandardsVal,Integer codeId, String setStandardDesc) {
		if (standardsPanel.getWidgetCount() <5) {
			if (setStandardsVal != null && !setStandardsVal.isEmpty()) {
				standardsPanel.add(createStandardLabel(setStandardsVal, Integer.toString(codeId), setStandardDesc));
			}
		} else {
			standardMaxShow();
			standardSgstBox.setText("");
		}
		closeBrowseStandardsPopup();
	}
}
