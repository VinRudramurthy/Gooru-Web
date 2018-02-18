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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.content.ResourceMetaInfoDo;
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
import org.ednovo.gooru.client.mvp.shelf.collection.tab.assign.CollectionAssignCBundle;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddSetupAdvancedView;
import org.ednovo.gooru.client.mvp.shelf.event.GetEditPageHeightEvent;
import org.ednovo.gooru.client.uc.AppMultiWordSuggestOracle;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.uc.AppSuggestBox;
import org.ednovo.gooru.client.uc.CloseLabel;
import org.ednovo.gooru.client.uc.CloseLabelCentury;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.StandardsPreferenceOrganizeToolTip;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.uc.tooltip.BrowseStandardsTooltip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.util.ResourceImageUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.regexp.shared.RegExp;
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
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author ibc
 *
 */
public abstract class EditUserOwnResourcePopupVc extends AppPopUp implements SelectionHandler<SuggestOracle.Suggestion>  {
	CollectionItemDo collectionItemDo;

	CollectionItemDo collectionOriginalItemDo;

	@UiField
	public Button addResourceBtn,changeFileBtn,browseResourceBtn,cancelResourcePopupBtnLbl,browseCentury;

	@UiField
	FormPanel fileuploadForm;

	@UiField
	HTMLEventPanel btnStandardsBrowse;
	@UiField
	UlPanel standardsDropListValues;

	@UiField
	HTMLPanel hazardContainer,standardsCont,uploadContainer,uploadName,defaultFileTxtContainer,panelContentRights,imagesText,textsText,imageContainer,rightsContent,
	mediaLabelContainer,educationalContainer,momentsOfLearningContainer,mediaFeatureContainer,accessHazardContainer,standardsBrowseContainer,
	mobileFriendlyContainer,mediaDropdownArrowConatainer,centuryBrowseContainer;

	@UiField UlPanel ulSelectedItems;


	@UiField
	Label resourceContentChkLbl, mandatoryTitleLbl,uploadImageLbl,fileTextLbl,rightsLbl,centuryDefaultText,mandatoryCategoryLbl;

	@UiField
	public TextBox titleTextBox,resourcePathTextBox;

	@UiField
	FileUpload chooseResourceBtn;

	@UiField
	public TextArea descriptionTxtAera;

	@UiField Label lblAdding,resoureDropDownLbl,mandatoryTitleLblForSwareWords,mandatoryDescLblForSwareWords;

	@UiField HTMLEventPanel lblContentRights,imageResourcePanel,textResourcePanel,educatioNalUseDropContainer,momentsOfLearningDropDownContianer;

	@UiField
	Image setThumbnailImage,clipImage;

	@UiField
	Label descCharcterLimit;

	@UiField
	CheckBox rightsChkBox;

	@UiField Label resourceCategoryLabel;

	@UiField HTMLPanel categorypanel,texts,image,resourceTypePanel,panelAction,fileTitleText,
	descriptionText,categoryLabel,thumbnailImageText;

	@UiField Anchor rollBackToPaperClip;

	@UiField InlineLabel agreeText,andText,additionalText,commuGuideLinesAnr, termsAndPolicyAnr,privacyAnr,copyRightAnr,moblieFriendly,advancedText;

	@UiField HTMLPanel
	momentsOfLearningPanel,momentsOfLearningTitle,extendingUnderstandingText,interactingWithTheTextText,preparingTheLearningText,educationalUsePanel,educationalTitle,homeworkText,gameText,presentationText,referenceMaterialText,quizText,curriculumPlanText,lessonPlanText,
	unitPlanText,projectPlanText,readingText,textbookText,articleText,bookText,activityText,handoutText,errorContainer;

	@UiField(provided = true)
	AppSuggestBox standardSgstBox,centurySgstBox;

	@UiField FlowPanel standardContainer,centuryPanel,centuryContainer;

	@UiField Label accessHazard,mediaLabel,lblMediaPlaceHolder,lblMediaFeatureArrow;

	@UiField ScrollPanel spanelMediaFeaturePanel;

	@UiField HTMLPanel htmlMediaFeatureListContainer,educationalpanel,defaultMomentsOfLearningText,defaultText;

	@UiField Button mobileYes,mobileNo;

	@UiField Label mandatorymomentsOfLearninglLbl,standardsDefaultText,/*loadingTextLbl,*/momentsOfLearningDropDownLbl,resourcemomentsOfLearningLabel,standardMaxMsg,mandatoryEducationalLbl,resourceEducationalLabel,educationalDropDownLbl;

	@UiField
	HTMLEventPanel activityPanel,handoutPanel,homeworkPanel,gamePanel,presentationPanel,
	referenceMaterialPanel,quizPanel,curriculumPlanPanel,lessonPlanPanel,unitPlanPanel,projectPlanPanel,readingPanel,
	textbookPanel,articlePanel,bookPanel,preparingTheLearningPanel,interactingWithTheTextPanel,extendingUnderstandingPanel,
	AdvancedSetupContainer,eHearderIconEducationalUse,eHearderIconMomentsOfLearning,eHearderIconstandards,
	eHearderIconAccessHazard,eHearderIconMediafeature,eHearderIconMobileFriendly,eHearderIconCentury,defaultPanel,defaultPanelMomentsOfLearningPnl;


	private CopyRightPolicyVc copyRightPolicy;
	private TermsAndPolicyVc termsAndPolicyVc;
	private TermsOfUse termsOfUse;
	ResourceMetaInfoDo resMetaInfoDo = null;

	boolean isValidImageSize=true;

	private String thumbnailUrlStr = null;

	String fileNameWithOutRespUrl = null;

	public boolean resoureDropDownLblOpen = false;

	private static final String DEFULT_IMAGE_PREFIX = "images/default-";

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	private static final String PNG = i18n.GL0899();
	public boolean fileChanged=false;
	String mediaFileName=null;
	String originalFileName=null;
	String titleStr ;
	String categoryStr ;
	String filePath;

	private static final String RESOURCE_UPLOAD_FILE_PATTERN = "([^\\s]+([^?#]*\\.(?:jpg|jpeg|pdf))$)";
	private static final String RESOURCE_FILE_SUPPORT_MSG =i18n.GL0955();
	private static final String IMAGE_UPLOAD_URL = "/v2/media?sessionToken={0}";


	String mediaFeatureStr = i18n.GL1767();
	private boolean isQuestionResource=false;
	private boolean isUserResource = false;
	String courseCode="";
	private AppMultiWordSuggestOracle standardSuggestOracle;
	private AppMultiWordSuggestOracle centurySuggestOracle;
	private SearchDo<CodeDo> standardSearchDo = new SearchDo<CodeDo>();
	private SearchDo<StandardFo> centurySearchDo = new SearchDo<StandardFo>();
	private static final String FLT_CODE_ID = "id";
	List<String> standardPreflist;
	private Map<String, String> standardCodesMap = new HashMap<String, String>();
	private Map<String, String> centuryCodesMap = new HashMap<String, String>();
	Set<CodeDo> standardsDo=new HashSet<CodeDo>();
	Set<CodeDo> deletedStandardsDo=new HashSet<CodeDo>();
	private static final String DEFAULT_COMBO_BOX_TEXT ="Please choose one of the following...";
	StandardsPreferenceOrganizeToolTip standardsPreferenceOrganizeToolTip=new StandardsPreferenceOrganizeToolTip();
	private boolean isBrowseTooltip =false;

	 BrowseStandardsTooltip browseStandardsTooltip;
	 private boolean isBrowseStandardsToolTip = false;

	final List<String> tagList = new ArrayList<String>();

	List<String> tagListGlobal = new ArrayList<String>();

	private boolean hasClickedOnDropDwn = false;

	private String mobileFeature;

	private static final String USER_META_ACTIVE_FLAG = "0";

	public boolean educationalDropDownLblOpen=false,momentsOfLearningOpen=false,momentsOfLearningOpen1=false,educationalDropDownLblOpen1=false;

	private boolean isHavingBadWordsInTextbox=false,isHavingBadWordsInRichText=false;

	public AddSetupAdvancedView addSetupAdvancedView;
	//Added for centruy popup
	PopupPanel centuryPopup=new PopupPanel();
	Map<Long, String> centurySelectedValues=new HashMap<Long, String>();
	AddCenturyPresenter centuryPresenterWidget=AppClientFactory.getInjector().getAddCenturyPresenterWidget();

	private boolean isCCSSAvailable = false;
	private boolean isNGSSAvailable = false;
	private boolean isTEKSAvailable = false;
	private boolean isCAAvailable = false;

	List<Integer> selectedValues=new ArrayList<>();
	public FolderDo courseObjG;

	List<LiPanelWithClose> collectionLiPanelWithCloseArray = new ArrayList<>();

	String codeID="",code="",label="";

	String[] standardsTypesArray = new String[]{i18n.GL3379(),i18n.GL3322(),i18n.GL3323(),i18n.GL3324(),i18n.GL3325(),i18n.GL3321()};



	private static EditUserOwnResourcePopupVcUiBinder uiBinder = GWT.create(EditUserOwnResourcePopupVcUiBinder.class);

	interface EditUserOwnResourcePopupVcUiBinder extends UiBinder<Widget, EditUserOwnResourcePopupVc> {
	}
	public EditUserOwnResourcePopupVc(CollectionItemDo collectionItemDo) {

		super();
		AddSetupAdvancedCBundle.INSTANCE.css().ensureInjected();
		CollectionEditResourceCBundle.INSTANCE.css().ensureInjected();

		this.collectionItemDo = collectionItemDo;
		this.collectionOriginalItemDo = collectionItemDo;
		getMainPanel().setStyleName("PopupMainVSmall");
		standardSuggestOracle = new AppMultiWordSuggestOracle(true);
		centurySuggestOracle =new AppMultiWordSuggestOracle(true);
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
					//standardsPreferenceOrganizeToolTip.hide();
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
						//standardsPreferenceOrganizeToolTip.hide();
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
		standardSgstBox.getElement().getStyle().setFontSize(12, Unit.PX);
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
					AppClientFactory.getInjector().getSearchService().getSuggestCenturyByQuery(centurySearchDo, new SimpleAsyncCallback<SearchDo<StandardFo>>() {
					@Override
					public void onSuccess(SearchDo<StandardFo> result) {
							setCenturySuggestions(result);
						}
					});
					centurySgstBox.showSuggestionList();
				}
			}
		};
		centurySgstBox.getElement().getStyle().setFontSize(12, Unit.PX);
		centurySgstBox.getTextBox().getElement().setAttribute("placeholder", i18n.GL3122_1());
		BlurHandler blurHandler=new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if(standardsPreferenceOrganizeToolTip.isShowing()){
				errorContainer.setVisible(false);
				}
			}
		};
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
				updateCenturyAdvancedSetupStyle();

			}
		});
		standardSgstBox.addDomHandler(blurHandler, BlurEvent.getType());
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
		Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				hideDropDown(event);
			}
		});
		setContent(i18n.GL0949(), uiBinder.createAndBindUi(this));
		defaultText.getElement().setInnerHTML(i18n.GL3093());
		defaultMomentsOfLearningText.getElement().setInnerHTML(i18n.GL3093());
		advancedText.setText(i18n.GL3096());
		mediaLabelContainer.getElement().getStyle().setMarginBottom(10, Unit.PX);
		addSetupAdvancedView = new AddSetupAdvancedView() {
			@Override
			public void showAndHideContainers() {
			}
		};
		getAddStandards();
		setHazard();
		btnStandardsBrowse.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getAddStandards();
				if (!standardsDropListValues.getElement().getAttribute("style").equalsIgnoreCase("display:block;top:auto;left:18.9em;color:#515151;")) {
					standardsDropListValues.getElement().setAttribute("style", "display:block;top:auto;left:18.9em;color:#515151;");
				} else {
					standardsDropListValues.getElement().removeAttribute("style");
				}
			}
		});
		standardsBrowseContainer.getElement().setId("standardsContainerBswn");
		standardsCont.getElement().setAttribute("style", "position:relative;");
		AdvancedSetupContainer.add(addSetupAdvancedView);
		mediaDropdownArrowConatainer.getElement().getStyle().setRight(10, Unit.PX);

		momentsOfLearningPanel.setVisible(false);
		momentsOfLearningPanel.getElement().setId("pnlMomentsOfLearningPanel");
		educationalTitle.getElement().setInnerHTML(i18n.GL1664());
		educationalTitle.getElement().setId("lblEducationalTitle");
		educationalTitle.getElement().setAttribute("alt", i18n.GL1664());
		educationalTitle.getElement().setAttribute("title", i18n.GL1664());
		activityText.getElement().setInnerHTML(i18n.GL1665());
		activityText.getElement().setId("pnlActivityText");
		activityText.getElement().setAttribute("alt", i18n.GL1665());
		activityText.getElement().setAttribute("title", i18n.GL1665());
		handoutText.getElement().setInnerHTML(i18n.GL0907());
		handoutText.getElement().setId("pnlHandoutText");
		handoutText.getElement().setAttribute("alt", i18n.GL0907());
		handoutText.getElement().setAttribute("title", i18n.GL0907());
		homeworkText.getElement().setInnerHTML(i18n.GL1666());
		homeworkText.getElement().setId("pnlHomeworkText");
		homeworkText.getElement().setAttribute("alt", i18n.GL1666());
		homeworkText.getElement().setAttribute("title", i18n.GL1666());
		gameText.getElement().setInnerHTML(i18n.GL1667());
		gameText.getElement().setId("pnlGameText");
		gameText.getElement().setAttribute("alt", i18n.GL1667());
		gameText.getElement().setAttribute("title", i18n.GL1667());
		presentationText.getElement().setInnerHTML(i18n.GL1668());
		presentationText.getElement().setId("pnlPresentationText");
		presentationText.getElement().setAttribute("alt", i18n.GL1668());
		presentationText.getElement().setAttribute("title", i18n.GL1668());
		referenceMaterialText.getElement().setInnerHTML(i18n.GL1669());
		referenceMaterialText.getElement().setId("pnlReferenceMaterialText");
		referenceMaterialText.getElement().setAttribute("alt", i18n.GL1669());
		referenceMaterialText.getElement().setAttribute("title", i18n.GL1669());
		quizText.getElement().setInnerHTML(i18n.GL1670());
		quizText.getElement().setId("pnlQuizText");
		quizText.getElement().setAttribute("alt", i18n.GL1670());
		quizText.getElement().setAttribute("title", i18n.GL1670());
		curriculumPlanText.getElement().setInnerHTML(i18n.GL1671());
		curriculumPlanText.getElement().setId("pnlCurriculumPlanText");
		curriculumPlanText.getElement().setAttribute("alt", i18n.GL1671());
		curriculumPlanText.getElement().setAttribute("title", i18n.GL1671());
		lessonPlanText.getElement().setInnerHTML(i18n.GL1672());
		lessonPlanText.getElement().setId("pnlLessonPlanText");
		lessonPlanText.getElement().setAttribute("alt", i18n.GL1672());
		lessonPlanText.getElement().setAttribute("title", i18n.GL1672());
		unitPlanText.getElement().setInnerHTML(i18n.GL1673());
		unitPlanText.getElement().setId("pnlUnitPlanText");
		unitPlanText.getElement().setAttribute("alt", i18n.GL1673());
		unitPlanText.getElement().setAttribute("title", i18n.GL1673());
		projectPlanText.getElement().setInnerHTML(i18n.GL1674());
		projectPlanText.getElement().setId("pnlProjectPlanText");
		projectPlanText.getElement().setAttribute("alt", i18n.GL1674());
		projectPlanText.getElement().setAttribute("title", i18n.GL1674());
		readingText.getElement().setInnerHTML(i18n.GL1675());
		readingText.getElement().setId("pnlReadingText");
		readingText.getElement().setAttribute("alt", i18n.GL1675());
		readingText.getElement().setAttribute("title", i18n.GL1675());
		textbookText.getElement().setInnerHTML(i18n.GL0909());
		textbookText.getElement().setId("pnlTextbookText");
		textbookText.getElement().setAttribute("alt", i18n.GL0909());
		textbookText.getElement().setAttribute("title", i18n.GL0909());
		articleText.getElement().setInnerHTML(i18n.GL1676());
		articleText.getElement().setId("pnlArticleText");
		articleText.getElement().setAttribute("alt", i18n.GL1676());
		articleText.getElement().setAttribute("title", i18n.GL1676());
		bookText.getElement().setInnerHTML(i18n.GL1677());
		bookText.getElement().setId("pnlBookText");
		bookText.getElement().setAttribute("alt", i18n.GL1677());
		bookText.getElement().setAttribute("title", i18n.GL1677());
		educationalUsePanel.getElement().setId("pnlEducationalUsePanel");
		educationalUsePanel.setVisible(false);

		standardsDefaultText.setText(i18n.GL1682());
		standardsDefaultText.getElement().setId("lblStandardsDefaultText");
		standardsDefaultText.getElement().setAttribute("alt", i18n.GL1682());
		standardsDefaultText.getElement().setAttribute("title", i18n.GL1682());
		momentsOfLearningTitle.getElement().setInnerHTML(i18n.GL1678());
		momentsOfLearningTitle.getElement().setId("pnlMomentsOfLearningTitle");
		momentsOfLearningTitle.getElement().setAttribute("alt", i18n.GL1678());
		momentsOfLearningTitle.getElement().setAttribute("title", i18n.GL1678());
		preparingTheLearningText.getElement().setInnerHTML(i18n.GL1679());
		preparingTheLearningText.getElement().setId("pnlPreparingTheLearningText");
		preparingTheLearningText.getElement().setAttribute("alt", i18n.GL1679());
		preparingTheLearningText.getElement().setAttribute("title", i18n.GL1679());
		interactingWithTheTextText.getElement().setInnerHTML(i18n.GL1680());
		interactingWithTheTextText.getElement().setId("pnlInteractingWithTheTextText");
		interactingWithTheTextText.getElement().setAttribute("alt", i18n.GL1680());
		interactingWithTheTextText.getElement().setAttribute("title", i18n.GL1680());
		extendingUnderstandingText.getElement().setInnerHTML(i18n.GL1681());
		extendingUnderstandingText.getElement().setId("pnlExtendingUnderstandingText");
		extendingUnderstandingText.getElement().setAttribute("alt", i18n.GL1681());
		extendingUnderstandingText.getElement().setAttribute("title", i18n.GL1681());
		resourceEducationalLabel.setText(i18n.GL1684());
		resourceEducationalLabel.getElement().setId("lblResourceEducationalLabel");
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1684());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1684());
		resourcemomentsOfLearningLabel.setText(i18n.GL1684());
		resourcemomentsOfLearningLabel.getElement().setAttribute("alt", i18n.GL1684());
		resourcemomentsOfLearningLabel.getElement().setAttribute("title", i18n.GL1684());
		educationalpanel.getElement().setId("pnlEducationalpanel");
		educationalDropDownLbl.getElement().setId("lblEducationalDropDownLbl");
		mandatoryEducationalLbl.getElement().setId("lblMandatoryEducationalLbl");
		activityPanel.getElement().setId("epnlActivityPanel");
		handoutPanel.getElement().setId("epnlHandoutPanel");
		homeworkPanel.getElement().setId("epnlHomeworkPanel");
		gamePanel.getElement().setId("epnlGamePanel");
		presentationPanel.getElement().setId("epnlPresentationPanel");
		referenceMaterialPanel.getElement().setId("epnlReferenceMaterialPanel");
		quizPanel.getElement().setId("epnlQuizPanel");
		curriculumPlanPanel.getElement().setId("epnlCurriculumPlanPanel");
		lessonPlanPanel.getElement().setId("epnlLessonPlanPanel");
		unitPlanPanel.getElement().setId("epnlUnitPlanPanel");
		projectPlanPanel.getElement().setId("epnlProjectPlanPanel");
		readingPanel.getElement().setId("epnlReadingPanel");
		textbookPanel.getElement().setId("epnlTextbookPanel");
		articlePanel.getElement().setId("epnlArticlePanel");
		bookPanel.getElement().setId("epnlBookPanel");
		momentsOfLearningDropDownLbl.getElement().setId("lblMomentsOfLearningDropDownLbl");
		mandatorymomentsOfLearninglLbl.getElement().setId("lblMandatorymomentsOfLearninglLbl");
		preparingTheLearningPanel.getElement().setId("epnlPreparingTheLearningPanel");
		interactingWithTheTextPanel.getElement().setId("epnlInteractingWithTheTextPanel");
		extendingUnderstandingPanel.getElement().setId("epnlExtendingUnderstandingPanel");
		standardContainer.getElement().setId("fpnlStandardContainer");
		standardSgstBox.getElement().setId("StandardSgstBox");
		standardMaxMsg.getElement().setId("lblStandardMaxMsg");

		centuryDefaultText.setText(i18n.GL3199());
		centuryDefaultText.getElement().setId("lblCenturyDefaultText");
		centuryDefaultText.getElement().setAttribute("alt", i18n.GL3199());
		centuryDefaultText.getElement().setAttribute("title", i18n.GL3199());

		uploadName.getElement().setInnerHTML(" "+i18n.GL0948());
		uploadName.getElement().setId("pnlUploadName");
		uploadName.getElement().setAttribute("alt", i18n.GL0948());
		uploadName.getElement().setAttribute("title", i18n.GL0948());
		uploadContainer.getElement().setId("pnlUploadContainer");
		browseResourceBtn.setText(i18n.GL0902());
		browseResourceBtn.getElement().setId("btnBrowseResourceBtn");
		browseResourceBtn.getElement().setAttribute("alt", i18n.GL0902());
		browseResourceBtn.getElement().setAttribute("title", i18n.GL0902());
		fileuploadForm.getElement().setId("fpFileuploadForm");
		rollBackToPaperClip.setText(i18n.GL0950());
		rollBackToPaperClip.getElement().setId("lnkRollBackToPaperClip");
		rollBackToPaperClip.getElement().setAttribute("alt", i18n.GL0950());
		rollBackToPaperClip.getElement().setAttribute("title", i18n.GL0950());
		changeFileBtn.setText(i18n.GL0951());
		changeFileBtn.getElement().setId("btnChangeFileBtn");
		changeFileBtn.getElement().setAttribute("alt", i18n.GL0951());
		changeFileBtn.getElement().setAttribute("title", i18n.GL0951());
		fileTitleText.getElement().setInnerHTML(i18n.GL0952());
		fileTitleText.getElement().setId("pnlFileTitleText");
		fileTitleText.getElement().setAttribute("alt", i18n.GL0952());
		fileTitleText.getElement().setAttribute("title", i18n.GL0952());
		mandatoryTitleLbl.setText(i18n.GL0173());
		mandatoryTitleLbl.getElement().setId("lblMandatoryTitleLbl");
		mandatoryTitleLbl.getElement().setAttribute("alt", i18n.GL0173());
		mandatoryTitleLbl.getElement().setAttribute("title", i18n.GL0173());
		descriptionText.getElement().setInnerHTML(i18n.GL0904());
		descriptionText.getElement().setId("pnlDescriptionText");
		descriptionText.getElement().setAttribute("alt", i18n.GL0904());
		descriptionText.getElement().setAttribute("title", i18n.GL0904());
		categoryLabel.getElement().setInnerHTML(i18n.GL3103()+i18n.GL_SPL_STAR());
		categoryLabel.getElement().setId("pnlCategoryLabel");
		categoryLabel.getElement().setAttribute("alt", i18n.GL0906());
		categoryLabel.getElement().setAttribute("title", i18n.GL0906());
		mandatoryCategoryLbl.setText(i18n.GL0917());
		mandatoryCategoryLbl.getElement().setId("lblMandatoryCategoryLbl");
		mandatoryCategoryLbl.getElement().setAttribute("alt", i18n.GL0917());
		mandatoryCategoryLbl.getElement().setAttribute("title", i18n.GL0917());
		textsText.getElement().setInnerHTML(i18n.GL1044());
		textsText.getElement().setId("pnlTextsText");
		textsText.getElement().setAttribute("alt", i18n.GL1044());
		textsText.getElement().setAttribute("title", i18n.GL1044());
		imagesText.getElement().setInnerHTML(i18n.GL1046());
		imagesText.getElement().setId("pnlImagesText");
		imagesText.getElement().setAttribute("alt", i18n.GL1046());
		imagesText.getElement().setAttribute("title", i18n.GL1046());
		thumbnailImageText.getElement().setInnerHTML(i18n.GL0911());
		thumbnailImageText.getElement().setId("pnlThumbnailImageText");
		thumbnailImageText.getElement().setAttribute("alt", i18n.GL0911());
		thumbnailImageText.getElement().setAttribute("title", i18n.GL0911());
		uploadImageLbl.setText(i18n.GL0912());
		uploadImageLbl.getElement().setId("lblUploadImageLbl");
		uploadImageLbl.getElement().setAttribute("alt", i18n.GL0912());
		uploadImageLbl.getElement().setAttribute("title", i18n.GL0912());
		rightsLbl.setText(i18n.GL0869());
		rightsLbl.getElement().setId("lblRightsLbl");
		rightsLbl.getElement().setAttribute("alt", i18n.GL0869());
		rightsLbl.getElement().setAttribute("title", i18n.GL0869());
		agreeText.setText(i18n.GL0870());
		agreeText.getElement().setId("lblAgreeText");
		agreeText.getElement().setAttribute("alt", i18n.GL0870());
		agreeText.getElement().setAttribute("title", i18n.GL0870());
		commuGuideLinesAnr.setText(i18n.GL0871()+i18n.GL_GRR_COMMA());
		commuGuideLinesAnr.getElement().setId("lnkCommuGuideLinesAnr");
		commuGuideLinesAnr.getElement().setAttribute("alt", i18n.GL0871());
		commuGuideLinesAnr.getElement().setAttribute("title", i18n.GL0871());
		termsAndPolicyAnr.setText(" "+i18n.GL0872()+i18n.GL_GRR_COMMA());
		termsAndPolicyAnr.getElement().setId("lnkTermsAndPolicyAnr");
		termsAndPolicyAnr.getElement().setAttribute("alt", i18n.GL0872());
		termsAndPolicyAnr.getElement().setAttribute("title", i18n.GL0872());
		privacyAnr.setText(" "+i18n.GL0873());
		privacyAnr.getElement().setId("lnkPrivacyAnr");
		privacyAnr.getElement().setAttribute("alt", i18n.GL0873());
		privacyAnr.getElement().setAttribute("title", i18n.GL0873());
		andText.setText(i18n.GL_GRR_AND());
		andText.getElement().setId("lblAndText");
		andText.getElement().setAttribute("alt", i18n.GL_GRR_AND());
		andText.getElement().setAttribute("title", i18n.GL_GRR_AND());
		copyRightAnr.setText(" "+i18n.GL0875());
		copyRightAnr.getElement().setId("lnkCopyRightAnr");
		copyRightAnr.getElement().setAttribute("alt", i18n.GL0875());
		copyRightAnr.getElement().setAttribute("title", i18n.GL0875());
		additionalText.setText(i18n.GL0874());
		additionalText.getElement().setId("lblAdditionalText");
		additionalText.getElement().setAttribute("alt", i18n.GL0874());
		additionalText.getElement().setAttribute("title", i18n.GL0874());
		addResourceBtn.setText(i18n.GL0590());
		addResourceBtn.getElement().setId("btnAddResourceBtn");
		addResourceBtn.getElement().setAttribute("alt", i18n.GL0141());
		addResourceBtn.getElement().setAttribute("title", i18n.GL0141());
		cancelResourcePopupBtnLbl.setText(i18n.GL0142());
		cancelResourcePopupBtnLbl.getElement().setId("lnkCancelResourcePopupBtnLbl");
		cancelResourcePopupBtnLbl.getElement().setAttribute("alt", i18n.GL0142());
		cancelResourcePopupBtnLbl.getElement().setAttribute("title", i18n.GL0142());
		cancelResourcePopupBtnLbl.getElement().setAttribute("style","margin-left:10px");
		lblAdding.setText(i18n.GL0808().toLowerCase());
		lblAdding.getElement().setId("lblLblAdding");
		lblAdding.getElement().setAttribute("alt", i18n.GL0808());
		lblAdding.getElement().setAttribute("title", i18n.GL0808());
		clipImage.getElement().setId("imgClipImage");
		clipImage.setUrl("images/paperclip.png");
		addResourceBtn.addClickHandler(new AddClickHandler());
		addResourceBtn.getElement().getStyle().setFloat(Float.LEFT);
		uploadImageLbl.addClickHandler(new OnEditImageClick());

		changeFileBtn.addClickHandler(new ChangeFileBtnClick());
		titleTextBox.getElement().setId("txtTitleTextBox");
		titleTextBox.addKeyUpHandler(new TitleKeyUpHandler());
		descriptionTxtAera.getElement().setId("tatDescriptionTxtAera");
		descriptionTxtAera.addKeyUpHandler(new DescriptionKeyUpHandler());
		resourcePathTextBox.getElement().setId("txtResourcePathTextBox");
		resourcePathTextBox.getElement().getStyle().setWidth(82, Unit.PCT);
		StringUtil.setAttributes(resourcePathTextBox, true);
		resourcePathTextBox.addKeyUpHandler(new ResourcePathKeyUpHandler());
		titleTextBox.getElement().setAttribute("maxlength", "50");
		StringUtil.setAttributes(titleTextBox, true);
		descriptionTxtAera.getElement().setAttribute("maxlength", "300");
		StringUtil.setAttributes(descriptionTxtAera, true);
		resourceContentChkLbl.getElement().setId("lblResourceContentChkLbl");
		resourceContentChkLbl.setVisible(false);
		mandatoryTitleLbl.setVisible(false);
		mandatoryCategoryLbl.setVisible(false);
		descCharcterLimit.getElement().setId("lblDescCharcterLimit");
		descCharcterLimit.setVisible(false);
		setThumbnailImage.getElement().setId("imgSetThumbnailImage");
		setThumbnailImage.setVisible(true);
		resourceTypePanel.setVisible(true);
		chooseResourceBtn.getElement().setId("uploadFile");
		panelContentRights.getElement().setId("pnlPanelContentRights");
		panelContentRights.setVisible(false);
		rightsChkBox.addClickHandler(new rightsChecked());
		rightsChkBox.getElement().setId("chkRights");
		categorypanel.getElement().setId("pnlCategorypanel");
		resourceCategoryLabel.getElement().setId("lblResourceCategoryLabel");
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1044());
		resoureDropDownLbl.getElement().setId("lblResoureDropDownLbl");
		resourceTypePanel.getElement().setId("pnlResourceTypePanel");
		imageResourcePanel.getElement().setId("epnlImageResourcePanel");
		image.getElement().setId("pnlImage");
		textResourcePanel.getElement().setId("epnlTextResourcePanel");
		texts.getElement().setId("pnlTexts");
		imageContainer.getElement().setId("pnlImageContainer");
		lblContentRights.getElement().setId("epnlLblContentRights");
		rightsContent.getElement().setId("pnlRightsContent");
		panelAction.getElement().setId("pnlPanelAction");

        setModal(true);
		Window.enableScrolling(false);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));


		displayResourceInfo();
		show();
		center();
		AppClientFactory.fireEvent(new GetEditPageHeightEvent(this, false));
		getResourceMetaInfo(collectionItemDo.getResource().getUrl());
		handelFormEvent();

		lblAdding.getElement().getStyle().setDisplay(Display.NONE);
		panelAction.getElement().getStyle().setDisplay(Display.BLOCK);
		defaultFileTxtContainer.getElement().setId("pnlDefaultFileTxtContainer");
		defaultFileTxtContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		uploadContainer.getElement().getStyle().setDisplay(Display.NONE);
		uploadName.getElement().getStyle().setDisplay(Display.NONE);
		fileTextLbl.setText(i18n.GL0954());
		fileTextLbl.getElement().setId("lblFileTextLbl");
		fileTextLbl.getElement().setAttribute("alt", i18n.GL0954());
		fileTextLbl.getElement().setAttribute("title", i18n.GL0954());
		browseResourceBtn.getElement().getStyle().setMarginRight(9, Unit.PX);
		copyRightAnr.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
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
		});

		termsAndPolicyAnr.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
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

		});
		privacyAnr.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
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

		});
		commuGuideLinesAnr.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.open("http://support.gooru.org/hc/en-us/articles/200688506","_blank","");
			}
		});

		moblieFriendly.setText(i18n.GL1811());
		moblieFriendly.getElement().setId("spnMobileFriendly");
		moblieFriendly.getElement().setAttribute("alt",i18n.GL1811());
		moblieFriendly.getElement().setAttribute("title",i18n.GL1811());
		moblieFriendly.getElement().getStyle().setDisplay(Display.INLINE);

		mobileYes.setText(i18n.GL_GRR_YES());
		mobileYes.getElement().setId("btnYes");
		mobileYes.getElement().setAttribute("alt",i18n.GL_GRR_YES());
		mobileYes.getElement().setAttribute("title",i18n.GL_GRR_YES());

		mobileNo.setText(i18n.GL1735());
		mobileNo.getElement().setId("btnNo");
		mobileNo.getElement().setAttribute("alt",i18n.GL1735());
		mobileNo.getElement().setAttribute("title",i18n.GL1735());

		accessHazard.setText(i18n.GL1804());
		accessHazard.getElement().setId("lblAccessHazard");
		accessHazard.getElement().setAttribute("alt",i18n.GL1804());
		accessHazard.getElement().setAttribute("title",i18n.GL1804());
		accessHazard.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		/*flashingHazard.setText(i18n.GL3110());
		flashingHazard.getElement().setId("lblFlashingHazard");
		flashingHazard.getElement().setAttribute("alt",i18n.GL3110());
		flashingHazard.getElement().setAttribute("title",i18n.GL3110());

		motionSimulationHazard.setText(i18n.GL3111());
		motionSimulationHazard.getElement().setId("lblMotionSimulationHazard");
		motionSimulationHazard.getElement().setAttribute("alt",i18n.GL3111());
		motionSimulationHazard.getElement().setAttribute("title",i18n.GL3111());

		soundHazard.setText(i18n.GL3112());
		soundHazard.getElement().setId("lblSoundHazard");
		soundHazard.getElement().setAttribute("alt",i18n.GL3112());
		soundHazard.getElement().setAttribute("title",i18n.GL3112());*/


		mediaLabel.setText("Media Feature");
		mediaLabel.getElement().setId("lblMediaFeature");
		mediaLabel.getElement().setAttribute("alt","Media Feature");
		mediaLabel.getElement().setAttribute("title","Media Feature");
		mediaLabel.getElement().getStyle().setDisplay(Display.INLINE);

		if(mobileFeature!=null){
			if(mobileFeature.equalsIgnoreCase(""))
			{
				lblMediaPlaceHolder.setText("Choose a Media Feature Option:");
			}
		}
		else
		{
			lblMediaPlaceHolder.setText("Choose a Media Feature Option:");
		}
		lblMediaPlaceHolder.getElement().setId("phMediaFeature");
		lblMediaPlaceHolder.getElement().setAttribute("alt","Choose a Media Feature Option:");
		lblMediaPlaceHolder.getElement().setAttribute("title","Choose a Media Feature Option:");

		spanelMediaFeaturePanel.setVisible(false);

		lblMediaFeatureArrow.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				OpenMediaFeatureDropdown();
			}
		});
		lblMediaPlaceHolder.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				OpenMediaFeatureDropdown();
			}
		});
		List<String> mediaFeatureList = Arrays.asList(mediaFeatureStr.split(","));
		for(int n=0; n<mediaFeatureList.size(); n++)
		{
				String mediaTitleVal = mediaFeatureList.get(n);

				final Label titleLabel = new Label(mediaTitleVal);
				titleLabel.setStyleName(CollectionAssignCBundle.INSTANCE.css().classpageTitleText());
				titleLabel.getElement().setAttribute("id", mediaTitleVal);
				//Set Click event for title
				titleLabel.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						String optionSelected = titleLabel.getElement().getId();
						lblMediaPlaceHolder.setText(optionSelected);
						spanelMediaFeaturePanel.setVisible(false);
						lblMediaPlaceHolder.getElement().setId(titleLabel.getElement().getId());
						lblMediaPlaceHolder.setStyleName(CollectionAssignCBundle.INSTANCE.css().selectedClasspageText());
						setAdvancedOptionsStyles();
					}
				});
				htmlMediaFeatureListContainer.add(titleLabel);
		}
		HTMLEventPanel defaultMediaFeaturePnl = new HTMLEventPanel("");
		defaultMediaFeaturePnl.getElement().setClassName(CollectionEditResourceCBundle.INSTANCE.css().myFolderCollectionFolderVideoOuterContainer());
		HTMLPanel defaultMediaFeatureText = new HTMLPanel("");
		defaultMediaFeatureText.getElement().setInnerHTML(i18n.GL3093());
		defaultMediaFeatureText.getElement().setClassName(CollectionEditResourceCBundle.INSTANCE.css().myEducationalPanelSubTitles());
		defaultMediaFeatureText.getElement().addClassName(CollectionEditResourceCBundle.INSTANCE.css().setBorder());
		defaultMediaFeaturePnl.add(defaultMediaFeatureText);
		defaultMediaFeaturePnl.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				lblMediaPlaceHolder.setText(i18n.GL3051()+i18n.GL_SPL_SEMICOLON());
				spanelMediaFeaturePanel.setVisible(false);
				lblMediaPlaceHolder.setStyleName(CollectionAssignCBundle.INSTANCE.css().selectedClasspageText());
				setAdvancedOptionsStyles();
			}
		});
		htmlMediaFeatureListContainer.add(defaultMediaFeaturePnl);


		titleTextBox.addBlurHandler(new CheckProfanityInOnBlur(titleTextBox, null, mandatoryTitleLblForSwareWords));
		descriptionTxtAera.addBlurHandler(new CheckProfanityInOnBlur(null, descriptionTxtAera, mandatoryDescLblForSwareWords));

		ClickHandler rootHandler= new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(!hasClickedOnDropDwn){
					educationalUsePanel.setVisible(false);
					educationalDropDownLblOpen = false;
					momentsOfLearningPanel.setVisible(false);
					momentsOfLearningOpen = false;
					spanelMediaFeaturePanel.setVisible(false);
				}else{
					hasClickedOnDropDwn=false;
				}

			}
		};

		RootPanel.get().addDomHandler(rootHandler, ClickEvent.getType());

		/** Add Advanced Setup Changes **/

		educationalContainer.setVisible(false);
		educationalUsePanel.setVisible(false);
		momentsOfLearningContainer.setVisible(false);
		momentsOfLearningPanel.setVisible(false);
		standardContainer.setVisible(false);
		centuryContainer.setVisible(false);
		mediaFeatureContainer.setVisible(false);
		accessHazardContainer.setVisible(false);
		standardsBrowseContainer.setVisible(false);
		mobileFriendlyContainer.setVisible(false);

		addSetupAdvancedView.educationUseAdvancedPnl.addClickHandler(new AddSetupAdvancedClickHandlers());
		addSetupAdvancedView.momentsOfLearningAdvancedPnl.addClickHandler(new AddSetupAdvancedClickHandlers());
		addSetupAdvancedView.standardsAdvancedPnl.addClickHandler(new AddSetupAdvancedClickHandlers());
		addSetupAdvancedView.accessHazardAdvancedPnl.addClickHandler(new AddSetupAdvancedClickHandlers());
		addSetupAdvancedView.mediaFeatureAdvancedPnl.addClickHandler(new AddSetupAdvancedClickHandlers());
		addSetupAdvancedView.mobileFreindlyAdvancedPnl.addClickHandler(new AddSetupAdvancedClickHandlers());
		addSetupAdvancedView.centuryAdvancedPnl.addClickHandler(new AddSetupAdvancedClickHandlers());

		eHearderIconEducationalUse.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconMomentsOfLearning.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconstandards.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconCentury.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconAccessHazard.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconMediafeature.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconMobileFriendly.addClickHandler(new MinimizePanelsClickHandler());

		/** Add Advanced Setup Changes End**/
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
				if(standardsDo!=null && standardsDo.size()>0){
				for(final CodeDo codeObj:standardsDo){
					if(codeObj.getCodeId()==Integer.parseInt(id)){
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
			}
		};
		return new DownToolTipWidgetUc(closeLabel, description);
	}
	/**
	 * This method will hide the century popup
	 */
	public void hideCenturyPopup(){
		centuryPopup.hide();
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
	public void setHazardData(List<ListValuesDo> hazards){
		if(hazards!=null){
			hazardContainer.clear();
			for(ListValuesDo do1:hazards){
				final Label label=new Label();
				label.setText(do1.getName());
				label.setStyleName("accessHazardLbl");
				label.getElement().setId(do1.getId()+"");
				label.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						if(label.getStyleName().toString().contains("at-select")){
							label.getElement().removeClassName("at-select");
						}else{
							label.getElement().addClassName("at-select");
						}
					}
				});
				hazardContainer.add(label);
				if(collectionItemDo!=null&&collectionItemDo.getAccessHazard()!=null){
					setAccessHazardObjectVal(collectionItemDo.getAccessHazard());
				}
			}
		}
	}
	public void setAccessHazardObjectVal(List<checkboxSelectedDo> valuesDos){
		Map<Integer, String> accessHazards=new HashMap<>();
		for(checkboxSelectedDo listValuesDo:valuesDos){
			accessHazards.put(listValuesDo.getId(), listValuesDo.getName());
		}
		int size=hazardContainer.getWidgetCount();
		for(int i=0;i<size;i++){
			Label label=(Label)hazardContainer.getWidget(i);
			String id=label.getElement().getId();
			if(id!=null&&!id.equalsIgnoreCase("")){
				int idInt=Integer.parseInt(id);
				if(accessHazards.containsKey(idInt)){
					label.addStyleName("at-select");
				}
			}
		}
		setAdvancedAccessHazardStyles(valuesDos.size());
	}
	public void setHazard(){
		AppClientFactory.getInjector().getResourceService().getAccessHazards(new AsyncCallback<List<ListValuesDo>>() {
			@Override
			public void onSuccess(List<ListValuesDo> result) {
				if(result!=null){
					setHazardData(result);
				}
			}
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	private boolean eventTargetsStandardPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			return standardsDropListValues.getElement().isOrHasChild(Element.as(target))||standardsDropListValues.getElement().isOrHasChild(Element.as(target));
		}
		return false;
	}

	private class MinimizePanelsClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			addSetupAdvancedView.setUpLabel.setVisible(true);
			if(event.getSource()==eHearderIconEducationalUse){
				educationalContainer.setVisible(false);
				educationalUsePanel.setVisible(false);
				addSetupAdvancedView.educationUseAdvancedPnl.setVisible(true);
			}else if(event.getSource()==eHearderIconMomentsOfLearning){
				momentsOfLearningContainer.setVisible(false);
				momentsOfLearningPanel.setVisible(false);
				addSetupAdvancedView.momentsOfLearningAdvancedPnl.setVisible(true);
			}else if(event.getSource()==eHearderIconstandards){
				standardsBrowseContainer.setVisible(false);
				addSetupAdvancedView.standardsAdvancedPnl.setVisible(true);
			}else if(event.getSource()==eHearderIconAccessHazard){
				accessHazardContainer.setVisible(false);
				addSetupAdvancedView.accessHazardAdvancedPnl.setVisible(true);
			}else if(event.getSource()==eHearderIconMediafeature){
				mediaFeatureContainer.setVisible(false);
				addSetupAdvancedView.mediaFeatureAdvancedPnl.setVisible(true);
			}else if(event.getSource()==eHearderIconMobileFriendly){
				mobileFriendlyContainer.setVisible(false);
				addSetupAdvancedView.mobileFreindlyAdvancedPnl.setVisible(true);
			}else if(event.getSource()==eHearderIconCentury){
				centuryBrowseContainer.setVisible(false);
				addSetupAdvancedView.centuryAdvancedPnl.setVisible(true);
			}
		}
	}

	private class AddSetupAdvancedClickHandlers implements ClickHandler{
		public AddSetupAdvancedClickHandlers() {
		}
		@Override
		public void onClick(ClickEvent event) {
			if(event.getSource()==addSetupAdvancedView.educationUseAdvancedPnl){
			educationalContainer.setVisible(true);
			educationalUsePanel.setVisible(true);
			addSetupAdvancedView.educationUseAdvancedPnl.setVisible(false);
			}else if(event.getSource()==addSetupAdvancedView.momentsOfLearningAdvancedPnl){
				momentsOfLearningContainer.setVisible(true);
				momentsOfLearningPanel.setVisible(true);
				addSetupAdvancedView.momentsOfLearningAdvancedPnl.setVisible(false);
			}else if(event.getSource()==addSetupAdvancedView.standardsAdvancedPnl){
				standardContainer.setVisible(true);
				standardsBrowseContainer.setVisible(true);
				addSetupAdvancedView.standardsAdvancedPnl.setVisible(false);
			}else if(event.getSource()==addSetupAdvancedView.accessHazardAdvancedPnl){
				accessHazardContainer.setVisible(true);
				addSetupAdvancedView.accessHazardAdvancedPnl.setVisible(false);
			}else if(event.getSource()==addSetupAdvancedView.mediaFeatureAdvancedPnl){
				mediaFeatureContainer.setVisible(true);
				addSetupAdvancedView.mediaFeatureAdvancedPnl.setVisible(false);
			}else if(event.getSource()==addSetupAdvancedView.mobileFreindlyAdvancedPnl){
				mobileFriendlyContainer.setVisible(true);
				addSetupAdvancedView.mobileFreindlyAdvancedPnl.setVisible(false);
			}else if(event.getSource()==addSetupAdvancedView.centuryAdvancedPnl){
				centuryContainer.setVisible(true);
				centuryBrowseContainer.setVisible(true);
				addSetupAdvancedView.centuryAdvancedPnl.setVisible(false);
			}

			if(isAllAdditionalTagsOpen()){
				addSetupAdvancedView.setUpLabel.setVisible(false);
			}
		}
	}

	/**
	 * This method will check all additional tags are open or not.
	 *
	 * @return allAdditionalTagInVisisble
	 */
	public boolean isAllAdditionalTagsOpen() {

		boolean allAdditionalTagInVisisble = false;

		if (!addSetupAdvancedView.educationUseAdvancedPnl.isVisible()
				&& !addSetupAdvancedView.momentsOfLearningAdvancedPnl.isVisible()
				&& !addSetupAdvancedView.standardsAdvancedPnl.isVisible()
				&& !addSetupAdvancedView.accessHazardAdvancedPnl.isVisible()
				&& !addSetupAdvancedView.mediaFeatureAdvancedPnl.isVisible()
				&& !addSetupAdvancedView.centuryAdvancedPnl.isVisible()
				&& !addSetupAdvancedView.mobileFreindlyAdvancedPnl.isVisible()) {
			allAdditionalTagInVisisble = true;
		}
		return allAdditionalTagInVisisble;
	}

	public void onLoad(){
		super.onLoad();
		Scheduler.get().scheduleDeferred(new ScheduledCommand(){

			@Override
			public void execute() {
				setResourceDescription();
			}
        });
	}

	@UiHandler("cancelResourcePopupBtnLbl")
	public void cancelPopUp(ClickEvent clickEvent) {
		AppClientFactory.fireEvent(new GetEditPageHeightEvent(this, true));
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		hide();
	}

	public void getResourceMetaInfo(String url) {
		AppClientFactory
				.getInjector()
				.getResourceService()
				.getResourceMetaInfo(url,
						new SimpleAsyncCallback<ResourceMetaInfoDo>() {
							@Override
							public void onSuccess(ResourceMetaInfoDo result) {
								setData(result);
							}
						});
	}

	public void setData(ResourceMetaInfoDo result) {
		setResMetaInfo(result);
		updateUi();
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
	private void setResMetaInfo(ResourceMetaInfoDo result) {
		this.resMetaInfoDo = result;
	}

	public void setResourceDescription(){
		descriptionTxtAera.setText(collectionItemDo.getResource().getDescription());
		descriptionTxtAera.getElement().setAttribute("alt", collectionItemDo.getResource().getDescription());
		descriptionTxtAera.getElement().setAttribute("title", collectionItemDo.getResource().getDescription());
	}

	@UiHandler("chooseResourceBtn")
	public void onChangeFileUploadBtn(ChangeEvent event){
		if (!"".equalsIgnoreCase(chooseResourceBtn.getFilename())) {
			String size=getFileNameSize();
			double sizeOfImage=Double.parseDouble(size);
			if(sizeOfImage>5){
				isValidImageSize=false;
				resourceContentChkLbl.setText(i18n.GL0913());
				resourceContentChkLbl.getElement().setAttribute("alt", i18n.GL0913());
				resourceContentChkLbl.getElement().setAttribute("title", i18n.GL0913());
				resourceContentChkLbl.setVisible(true);
				fileuploadForm.reset();
				if(!resourcePathTextBox.getText().equalsIgnoreCase("")){
					resourcePathTextBox.setText("");
				}
			}
		}
	}


	public void displayResourceInfo() {
		String url = collectionItemDo.getResource().getUrl();

			if(collectionItemDo.getResource().getResourceTags()!=null){

			for(int i=0;i<collectionItemDo.getResource().getResourceTags().size();i++){

				tagListGlobal.add("\""+collectionItemDo.getResource().getResourceTags().get(i).getLabel()+"\"");
				if(collectionItemDo.getResource().getResourceTags().get(i).getLabel().contains("Media Feature")){

					setMediaFeatureObjectVal(collectionItemDo.getResource().getResourceTags().get(i).getLabel());
				}
				if(collectionItemDo.getResource().getResourceTags().get(i).getLabel().contains("Mobile Friendly")){
					setMobileFriendlyObjectVal(collectionItemDo.getResource().getResourceTags().get(i).getLabel());
				}
				if(collectionItemDo.getResource().getResourceTags().get(i).getLabel().contains("Access Hazard")){
					setAccessHazardObjectVal(collectionItemDo.getAccessHazard());
				}
			}
		}


		if (collectionItemDo.getResource().getDescription().length() >= 300) {
			descriptionTxtAera.setText(collectionItemDo.getResource()
					.getDescription().substring(0, 300));
			descriptionTxtAera.getElement().setAttribute("alt", collectionItemDo.getResource().getDescription());
			descriptionTxtAera.getElement().setAttribute("title", collectionItemDo.getResource().getDescription());
		} else {
			descriptionTxtAera.setText(collectionItemDo.getResource()
					.getDescription());
			descriptionTxtAera.getElement().setAttribute("alt", collectionItemDo.getResource().getDescription());
			descriptionTxtAera.getElement().setAttribute("title", collectionItemDo.getResource().getDescription());
		}
		if (collectionItemDo.getResource().getTitle().length() >= 50) {
			titleTextBox.setText(collectionItemDo.getResource().getTitle()
					.substring(0, 50));
			titleTextBox.getElement().setAttribute("alt", collectionItemDo.getResource().getTitle());
			titleTextBox.getElement().setAttribute("title", collectionItemDo.getResource().getTitle());

		} else {
			titleTextBox.setText(collectionItemDo.getResource().getTitle());
			titleTextBox.getElement().setAttribute("alt", collectionItemDo.getResource().getTitle());
			titleTextBox.getElement().setAttribute("title", collectionItemDo.getResource().getTitle());
		}

		setThumbnailImage.setVisible(true);
		String category = collectionItemDo.getResource().getResourceFormat()!=null?collectionItemDo.getResource().getResourceFormat().getValue():"";
		category=category!=null?category:"";
		if (category.equalsIgnoreCase(i18n.GL1046()) || category.equalsIgnoreCase("slide")||category.equalsIgnoreCase("image")) {
			resourceCategoryLabel.setText(i18n.GL1046());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1046());
			resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1046());
			categorypanel.setStyleName(image.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
			imageResourcePanel.addStyleName("active");
		} else if (category.equalsIgnoreCase(i18n.GL1044())|| category.equalsIgnoreCase("Textbook")|| category.equalsIgnoreCase("Lesson")|| category.equalsIgnoreCase("Handout")||category.equalsIgnoreCase("Text")) {
			resourceCategoryLabel.setText(i18n.GL1044());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1044());
			resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1044());
			categorypanel.setStyleName(texts.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
			textResourcePanel.addStyleName("active");
		}

		thumbnailUrlStr = collectionItemDo.getResource().getThumbnailUrl();
		setImage(url, category);
		if(collectionItemDo.getEducationalUse()!=null){
			for (checkboxSelectedDo item : collectionItemDo.getEducationalUse()) {
				    resourceEducationalLabel.setText(item.getValue());
					educationalUsePanel.setVisible(false);
					educationalDropDownLblOpen = false;
					mandatoryEducationalLbl.setVisible(false);
					setAdvancedOptionsStyles();
			}
		}

		if(collectionItemDo.getMomentsOfLearning()!=null){
			for (checkboxSelectedDo item : collectionItemDo.getMomentsOfLearning()) {
				   resourcemomentsOfLearningLabel.setText(item.getValue());
				   resourcemomentsOfLearningLabel.getElement().setAttribute("alt", item.getValue());
				   resourcemomentsOfLearningLabel.getElement().setAttribute("title", item.getValue());
				   momentsOfLearningPanel.setVisible(false);
				   momentsOfLearningOpen = false;
				   mandatorymomentsOfLearninglLbl.setVisible(false);
				   setAdvancedOptionsStyles();
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
			updateStandardsAdvancedSetupStyle();
		}
		//This will set the 21 centry skill values if the user as added already
		if(collectionItemDo.getSkills()!= null && collectionItemDo.getSkills().size()>0){
			centuryPanel.clear();
			for (StandardFo standardObj : collectionItemDo.getSkills()) {
				 CodeDo codeObj=new CodeDo();
				 codeObj.setCodeId(standardObj.getCodeId());
				 codeObj.setCode(standardObj.getLabel());
				 standardsDo.add(codeObj);
				 centurySelectedValues.put(Long.parseLong(standardObj.getCodeId()+""), standardObj.getLabel());
				 centuryPanel.add(create21CenturyLabel(standardObj.getLabel(),standardObj.getCodeId()+"",""));
			}
            updateCenturyAdvancedSetupStyle();
		}
		if(collectionItemDo.getMediaType()!=null){
			setMobileFriendlyObjectVal(collectionItemDo.getMediaType());
		}
	}
	/**
	 * @function updateCenturyAdvancedSetupStyle
	 * @created_date : 18-Mar-2015
	 *
	 * @description This method is used to set styles for 21 skills based on the number of skills.
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public void updateCenturyAdvancedSetupStyle() {
		if(centuryPanel.getWidgetCount()==0){
			addSetupAdvancedView.centuryAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
		}else{
			addSetupAdvancedView.centuryAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.centuryAdvancedContainer.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
	}
	public void setImage(String url, final String category){
		if (thumbnailUrlStr!=null && thumbnailUrlStr.endsWith("null")) {
			if (url.indexOf("youtube") >0){
				String youTubeIbStr = ResourceImageUtil.getYoutubeVideoId(url);
				thumbnailUrlStr = "http://img.youtube.com/vi/"+youTubeIbStr+"/1.jpg";
			}else{
				thumbnailUrlStr = DEFULT_IMAGE_PREFIX + category.toLowerCase() + PNG;
			}
		}
		setThumbnailImage.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				setThumbnailImage.setUrl(DEFULT_IMAGE_PREFIX + category.toLowerCase() + PNG);
			}
		});
		setThumbnailImage.setUrl(thumbnailUrlStr!=null?thumbnailUrlStr:"");
	}
	public void updateUi() {
		setThumbnailImage.setVisible(true);

	}

	@UiHandler("setThumbnailImage")
	public void onImageError(ErrorEvent errorEvent){
		String category = collectionItemDo.getResource().getResourceFormat()!=null?collectionItemDo.getResource().getResourceFormat().getValue():"";
		if(category!=null&&!category.equals("")){
			thumbnailUrlStr = DEFULT_IMAGE_PREFIX + category.toLowerCase() + PNG;
		}
	}

	private class ChangeFileBtnClick implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			defaultFileTxtContainer.getElement().getStyle().setDisplay(Display.NONE);
			uploadName.getElement().getStyle().setDisplay(Display.BLOCK);
			uploadContainer.getElement().getStyle().setDisplay(Display.BLOCK);
			if(!resourcePathTextBox.getText().trim().equalsIgnoreCase("")){
				resourcePathTextBox.setText("");
			}

		}

	}
	@UiHandler("lblContentRights")
	public void onMouseOver(MouseOverEvent event){
		panelContentRights.setVisible(true);
	}

	@UiHandler("lblContentRights")
	public void onMouseOut(MouseOutEvent event){
		panelContentRights.setVisible(false);
	}
	@UiHandler("rollBackToPaperClip")
	public void onClickRollBackPaperClip(ClickEvent clickEvent){
		uploadName.getElement().getStyle().setDisplay(Display.NONE);
		uploadContainer.getElement().getStyle().setDisplay(Display.NONE);
		defaultFileTxtContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		resourceContentChkLbl.setVisible(false);
	}

	private class AddClickHandler implements ClickHandler {
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(ClickEvent event) {
			lblAdding.getElement().getStyle().setDisplay(Display.BLOCK);
			panelAction.getElement().getStyle().setDisplay(Display.NONE);
			final Map<String, String> parms = new HashMap<String, String>();
			parms.put("text", titleTextBox.getValue());
			AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

				@Override
				public void onSuccess(Boolean value) {
					isHavingBadWordsInTextbox = value;
					if(value){
						SetStyleForProfanity.SetStyleForProfanityForTextBox(titleTextBox, mandatoryTitleLblForSwareWords,value);
					}else{
						parms.put("text", descriptionTxtAera.getText());
						AppClientFactory.getInjector().getResourceService().checkProfanity(parms,new SimpleAsyncCallback<Boolean>() {

							@Override
							public void onSuccess(Boolean result) {
								isHavingBadWordsInRichText=result;
								if(result){
									SetStyleForProfanity.SetStyleForProfanityForTextArea(descriptionTxtAera, mandatoryDescLblForSwareWords, result);
								}else{
									if (!isHavingBadWordsInRichText && !isHavingBadWordsInTextbox) {
										boolean isValidate = true;

										 titleStr = titleTextBox.getText().trim();
										 categoryStr =resourceCategoryLabel.getText();// resourceTypeListBox.getItemText(resourceTypeListBox.getSelectedIndex());
										 filePath = resourcePathTextBox.getText().trim();
										 if(categoryStr.contains("Videos")||categoryStr.contains("Interactives")||categoryStr.contains("Images")||categoryStr.contains("Texts"))
											{
											 categoryStr=categoryStr.substring(0, categoryStr.length()-1);
											}
										 if(uploadContainer.isVisible()){
											 if(resourcePathTextBox.getText().trim() == null || resourcePathTextBox.getText().trim().equalsIgnoreCase("")){
												 resourceContentChkLbl.setText(i18n.GL0914());
												 resourceContentChkLbl.getElement().setAttribute("alt", i18n.GL0914());
												 resourceContentChkLbl.getElement().setAttribute("title", i18n.GL0914());
												 resourceContentChkLbl.setVisible(true);
												 isValidate = false;
											 }
										 }


										if (titleStr == null || titleStr.equalsIgnoreCase("")) {
											mandatoryTitleLbl.setText(i18n.GL0173());
											mandatoryTitleLbl.getElement().setAttribute("alt", i18n.GL0173());
											mandatoryTitleLbl.getElement().setAttribute("title", i18n.GL0173());
											mandatoryTitleLbl.setVisible(true);
											isValidate = false;
										}
										if(!rightsChkBox.getValue()){
											rightsLbl.getElement().getStyle().setColor("orange");
											isValidate = false;
										}
										if(descriptionTxtAera.getText().trim()==null || descriptionTxtAera.getText().trim().equals("")){
											isValidate = false;
											descCharcterLimit.setText(i18n.GL0905());
											descCharcterLimit.getElement().setAttribute("alt", i18n.GL0905());
											descCharcterLimit.getElement().setAttribute("title", i18n.GL0905());
											descCharcterLimit.setVisible(true);
										}
										if (categoryStr == null	|| categoryStr.equalsIgnoreCase("-1")|| categoryStr.equalsIgnoreCase("Choose a resource category")) {
											mandatoryCategoryLbl.setText(i18n.GL0917());
											mandatoryCategoryLbl.getElement().setAttribute("alt", i18n.GL0917());
											mandatoryCategoryLbl.getElement().setAttribute("title", i18n.GL0917());
											mandatoryCategoryLbl.setVisible(true);
											isValidate = false;
										}

										if(mobileYes.getStyleName().contains("at-OffButtonsActive")){
											tagList.add("Mobile Friendly : "+i18n.GL_GRR_YES());
										}
										else if(mobileNo.getStyleName().contains("at-OffButtonsActive")){
											tagList.add("Mobile Friendly : "+i18n.GL1735());
										}
										if(!lblMediaPlaceHolder.getText().equalsIgnoreCase("Choose a Media Feature Option:")){
											tagList.add(mediaLabel.getText()+" : "+lblMediaPlaceHolder.getText());
										}

										if(resourceEducationalLabel.getText()!=null ||!resourceEducationalLabel.getText().trim().equalsIgnoreCase("")){
											if(!resourceEducationalLabel.getText().trim().equalsIgnoreCase(DEFAULT_COMBO_BOX_TEXT)){
												tagList.add("Educational Use : "+resourceEducationalLabel.getText());
											}
										}

										if (isValidate) {
											if(!resourceEducationalLabel.getText().equalsIgnoreCase(i18n.GL1684())){
												ArrayList<checkboxSelectedDo> arrayOfEducational=new ArrayList<checkboxSelectedDo>();
												checkboxSelectedDo educationalOfObj=new checkboxSelectedDo();
												educationalOfObj.setSelected(true);
												educationalOfObj.setValue(resourceEducationalLabel.getText());
												arrayOfEducational.add(educationalOfObj);
												if(collectionOriginalItemDo.getResource().getEducationalUse() != null){
													for(int eduI=0; eduI<collectionOriginalItemDo.getResource().getEducationalUse().size(); eduI++){
														if(!resourceEducationalLabel.getText().equalsIgnoreCase(collectionOriginalItemDo.getResource().getEducationalUse().get(eduI).getValue())){
															checkboxSelectedDo eduUseObjPrevious=new checkboxSelectedDo();
															eduUseObjPrevious.setSelected(false);
															eduUseObjPrevious.setValue(collectionOriginalItemDo.getResource().getEducationalUse().get(eduI).getValue());
															arrayOfEducational.add(eduUseObjPrevious);
														}
													}
												}
												collectionItemDo.getResource().setEducationalUse(arrayOfEducational);
											}else{
												if(resourceEducationalLabel.getText().equalsIgnoreCase(i18n.GL1684())){
													ArrayList<checkboxSelectedDo> arrayOfEducational=new ArrayList<checkboxSelectedDo>();
													checkboxSelectedDo educationalOfObj=new checkboxSelectedDo();
													if(collectionOriginalItemDo.getResource().getEducationalUse() != null){
														for(int eduI=0; eduI<collectionOriginalItemDo.getResource().getEducationalUse().size(); eduI++){
															if(!resourceEducationalLabel.getText().equalsIgnoreCase(collectionOriginalItemDo.getResource().getEducationalUse().get(eduI).getValue())){
																checkboxSelectedDo eduUseObjPrevious=new checkboxSelectedDo();
																eduUseObjPrevious.setSelected(false);
																eduUseObjPrevious.setValue(collectionOriginalItemDo.getResource().getEducationalUse().get(eduI).getValue());
																arrayOfEducational.add(eduUseObjPrevious);
															}
														}
													}
													collectionItemDo.getResource().setEducationalUse(arrayOfEducational);
												}
											}
											if(!resourcemomentsOfLearningLabel.getText().equalsIgnoreCase(i18n.GL1684())){
												ArrayList<checkboxSelectedDo> arrayOfMoments=new ArrayList<checkboxSelectedDo>();
												checkboxSelectedDo momentsOfObj=new checkboxSelectedDo();
												momentsOfObj.setSelected(true);
												momentsOfObj.setValue(resourcemomentsOfLearningLabel.getText());
												arrayOfMoments.add(momentsOfObj);
												if(collectionOriginalItemDo.getResource().getMomentsOfLearning() != null){
													for(int momentsI=0; momentsI<collectionOriginalItemDo.getResource().getMomentsOfLearning().size(); momentsI++){
														if(!resourcemomentsOfLearningLabel.getText().equalsIgnoreCase(collectionOriginalItemDo.getResource().getMomentsOfLearning().get(momentsI).getValue())){
															checkboxSelectedDo momentsOfObjPrevious=new checkboxSelectedDo();
															momentsOfObjPrevious.setSelected(false);
															momentsOfObjPrevious.setValue(collectionOriginalItemDo.getResource().getMomentsOfLearning().get(momentsI).getValue());
															arrayOfMoments.add(momentsOfObjPrevious);
														}
													}
												}
												collectionItemDo.getResource().setMomentsOfLearning(arrayOfMoments);
											}else{
												if(resourcemomentsOfLearningLabel.getText().equalsIgnoreCase(i18n.GL1684())){
													ArrayList<checkboxSelectedDo> arrayOfMoments=new ArrayList<checkboxSelectedDo>();
													checkboxSelectedDo momentsOfObj=new checkboxSelectedDo();
													if(collectionOriginalItemDo.getResource().getMomentsOfLearning() != null){
														for(int momentsI=0; momentsI<collectionOriginalItemDo.getResource().getMomentsOfLearning().size(); momentsI++){
															if(!resourcemomentsOfLearningLabel.getText().equalsIgnoreCase(collectionOriginalItemDo.getResource().getMomentsOfLearning().get(momentsI).getValue())){
																checkboxSelectedDo momentsOfObjPrevious=new checkboxSelectedDo();
																momentsOfObjPrevious.setSelected(false);
																momentsOfObjPrevious.setValue(collectionOriginalItemDo.getResource().getMomentsOfLearning().get(momentsI).getValue());
																arrayOfMoments.add(momentsOfObjPrevious);
															}
														}
													}
													collectionItemDo.getResource().setMomentsOfLearning(arrayOfMoments);
												}
											}
											collectionItemDo.setStandards(getStandards());

											if(fileChanged && (uploadContainer.isVisible())){
												fileuploadForm.setAction(GWT.getModuleBaseURL() +"upServlet?sessionToken="+AppClientFactory.getLoginSessionToken());
												fileuploadForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {
													@Override
													public void onSubmitComplete(SubmitCompleteEvent event) {
														AppClientFactory.printInfoLogger("eventgetresults-----"+event.getResults());
														AppClientFactory.printInfoLogger("isValidImageSize-----"+isValidImageSize);
														if(isValidImageSize){
															parseUploadFileDetails(event.getResults());
														}

													}
												});
												fileuploadForm.submit();
											}
											else{
												if(categoryStr.contains("Images")||categoryStr.contains("Texts")){
													categoryStr=categoryStr.substring(0, categoryStr.length()-1);
												}

												if(tagListGlobal!=null&&tagListGlobal.size()!=0){
													AppClientFactory.getInjector().getResourceService().deleteTagsServiceRequest(collectionItemDo.getResource().getGooruOid(), tagListGlobal.toString(), new AsyncCallback<Void>() {

														@Override
														public void onSuccess(Void result) {
															updateUserOwnResource(filePath,mediaFileName,originalFileName,titleStr,descriptionTxtAera.getText().trim(),categoryStr,thumbnailUrlStr,collectionItemDo,tagList);
														}

														@Override
														public void onFailure(Throwable caught) {
														}
													});

												}
												else{
													updateUserOwnResource(filePath,mediaFileName,originalFileName,titleStr,descriptionTxtAera.getText().trim(),categoryStr,thumbnailUrlStr,collectionItemDo,tagList);
												}
											}
										}
										else{
											lblAdding.getElement().getStyle().setDisplay(Display.NONE);
											panelAction.getElement().getStyle().setDisplay(Display.BLOCK);
										}
									}
								}
							}

						});
					}
				}

			});
		}

		protected void parseUploadFileDetails(String jsonString) {
			if(jsonString!=null){
				JSONValue jsonParseValue=JSONParser.parseStrict(jsonString);
				JSONObject jsonObject=jsonParseValue.isObject();
				JSONValue jsonMediaFileValue=jsonObject.get("name");
				final String mediaFileName=jsonMediaFileValue.isString().toString().replaceAll("^\"|\"$", "");
				JSONValue jsonOriginalFileValue=jsonObject.get("originalFilename");
				final String originalFileName=jsonOriginalFileValue.isString().toString().replaceAll("^\"|\"$", "");

				if(tagListGlobal!=null&&tagListGlobal.size()!=0){
					AppClientFactory.getInjector().getResourceService().deleteTagsServiceRequest(collectionItemDo.getResource().getGooruOid(), tagListGlobal.toString(), new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							updateUserOwnResource(filePath,mediaFileName,originalFileName,titleStr,descriptionTxtAera.getText().trim(),categoryStr,thumbnailUrlStr,collectionItemDo,tagList);
						}

						@Override
						public void onFailure(Throwable caught) {
						}
					});

				}
				else{
					updateUserOwnResource(filePath,mediaFileName,originalFileName,titleStr,descriptionTxtAera.getText().trim(),categoryStr,thumbnailUrlStr,collectionItemDo,tagList);
				}


			}
		}
	}


	private class OnEditImageClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			resourceImageUpload();
		}
	}

	public abstract void resourceImageUpload();

	public abstract void updateUserOwnResource(String resourceFilePath,String resMediaFileName,String resOriginalFileName,String titleStr, String desc,String categoryStr, String thumbnailUrlStr, CollectionItemDo collectionItemDo, List<String> tagList);

	private class ResourcePathKeyUpHandler implements KeyUpHandler {
		public void onKeyUp(KeyUpEvent event) {
			resourceContentChkLbl.setVisible(false);
		}
	}

	private class TitleKeyUpHandler implements KeyUpHandler {

		public void onKeyUp(KeyUpEvent event) {
			mandatoryTitleLbl.setVisible(false);
			if (titleTextBox.getText().length() >= 50) {
				mandatoryTitleLbl.setText(i18n.GL0143());
				mandatoryTitleLbl.getElement().setAttribute("alt", i18n.GL0143());
				mandatoryTitleLbl.getElement().setAttribute("title", i18n.GL0143());
				mandatoryTitleLbl.setVisible(true);
			}
		}
	}

	private class DescriptionKeyUpHandler implements KeyUpHandler {

		public void onKeyUp(KeyUpEvent event) {
			descCharcterLimit.setVisible(false);
			if (descriptionTxtAera.getText().length() >= 300) {
				descriptionTxtAera.setText(descriptionTxtAera.getText().trim().substring(0,300));
				descriptionTxtAera.getElement().setAttribute("alt", descriptionTxtAera.getText());
				descriptionTxtAera.getElement().setAttribute("title",descriptionTxtAera.getText());
				descCharcterLimit.setText(i18n.GL0143());
				descCharcterLimit.getElement().setAttribute("alt", i18n.GL0143());
				descCharcterLimit.getElement().setAttribute("title", i18n.GL0143());
				descCharcterLimit.setVisible(true);
			}
		}
	}

	@UiHandler("imageResourcePanel")
	void slideResourcePanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_image_selected");
		resourceCategoryLabel.setText(i18n.GL1046());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1046());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1046());
		categorypanel.setStyleName(image.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen = false;
		mandatoryCategoryLbl.setVisible(false);
		imageResourcePanel.addStyleName("active");
		textResourcePanel.removeStyleName("active");
	}

	@UiHandler("textResourcePanel")
	void handoutResourcePanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_text_selected");
		resourceCategoryLabel.setText(i18n.GL1044());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1044());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1044());
		categorypanel.setStyleName(texts.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen = false;
		mandatoryCategoryLbl.setVisible(false);
		imageResourcePanel.removeStyleName("active");
		textResourcePanel.addStyleName("active");
	}

	/**
	 * This method is to get the setThumbnailImage
	 */
	public Image getSetThumbnailImage() {
		return setThumbnailImage;
	}

	/**
	 * This method is to set the setThumbnailImage
	 */
	public void setSetThumbnailImage(Image setThumbnailImage) {
		this.setThumbnailImage = setThumbnailImage;
	}

	/**
	 * This method is to get the thumbnailUrlStr
	 */
	public String getThumbnailUrlStr() {
		return thumbnailUrlStr;
	}

	/**
	 * This method is to set the thumbnailUrlStr
	 */
	public void setThumbnailUrlStr(String thumbnailUrlStr) {
		this.thumbnailUrlStr = thumbnailUrlStr;
	}

	public void setFileNameWithOutRespUrl(String fileNameWithOutRespUrl ){
		this.fileNameWithOutRespUrl = fileNameWithOutRespUrl;
	}

	private void handelFormEvent() {
		chooseResourceBtn.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if(hasValidateResource()){
					 isValidImageSize=true;
					 resourceContentChkLbl.setVisible(false);
					 resourcePathTextBox.setText(chooseResourceBtn.getFilename().trim());
					 resourcePathTextBox.getElement().setAttribute("alt", chooseResourceBtn.getFilename().trim());
					 resourcePathTextBox.getElement().setAttribute("title", chooseResourceBtn.getFilename().trim());
					 fileChanged=true;

				 }
				 else{
						if(!chooseResourceBtn.getFilename().trim().equalsIgnoreCase("")){
							 resourceContentChkLbl.setText(RESOURCE_FILE_SUPPORT_MSG);
							 resourceContentChkLbl.getElement().setAttribute("alt", RESOURCE_FILE_SUPPORT_MSG);
							 resourceContentChkLbl.getElement().setAttribute("title", RESOURCE_FILE_SUPPORT_MSG);
							 resourceContentChkLbl.setVisible(true);

						 }



				 }

			}
		});
	}
	@UiHandler("resoureDropDownLbl")
	public void dropDownClick(ClickEvent event) {
		if (resoureDropDownLblOpen == false) {
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen = true;

		} else {
			resourceTypePanel.setVisible(false);
			resoureDropDownLblOpen = false;
		}

	}

	public boolean hasValidateResource(){
		boolean isValid = true;
		String uploadResourceName = chooseResourceBtn.getFilename();
		try {
			RegExp reg = RegExp.compile(RESOURCE_UPLOAD_FILE_PATTERN, "gi");
			if(uploadResourceName != null && !reg.test(uploadResourceName)){
				return isValid = false;
			}
		}catch (Exception e) {
			AppClientFactory.printSevereLogger("EditUserOwnResourcePopupVc hasValidateResource:::"+e);
		}
		return isValid;
	}

	/**
	 * To get the upload file size from client end
	 * @return it will return the upload file size in mb
	 */

	public final native String getFileNameSize() /*-{

		var fileSize;
      if ($wnd.$.browser.msie){

     	 	var objFSO = new ActiveXObject("Scripting.FileSystemObject");
        	var sPath =   $wnd.$("#uploadFile")[0].value;
        	var objFile = objFSO.getFile(sPath);
         	var iSize = objFile.size;
        	fileSize = iSize/ 1048576;
        }
        else{
       		fileSize =  $wnd.$("#uploadFile")[0].files[0].size ;//size in kb
        	fileSize = fileSize / 1048576; //size in mb
         }
           return fileSize.toString();
  }-*/;



	/**
	 * set the century suggestions
	 * @param centurySearchDo
	 */
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
	@UiHandler("defaultPanel")
	void defaultPanel(ClickEvent event) {
		resourceEducationalLabel.setText(i18n.GL1684());
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1684());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1684());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("activityPanel")
	void activityPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_activity_selected");
		resourceEducationalLabel.setText(i18n.GL1665());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("handoutPanel")
	void handoutPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_handout_selected");
		resourceEducationalLabel.setText(i18n.GL0907());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("homeworkPanel")
	void homeworkPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_homework_selected");
		resourceEducationalLabel.setText(i18n.GL1666());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("gamePanel")
	void gamePanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_game_selected");
		resourceEducationalLabel.setText(i18n.GL1667());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("presentationPanel")
	void presentationPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_presentation_selected");
		resourceEducationalLabel.setText(i18n.GL1668());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("referenceMaterialPanel")
	void referenceMaterialPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_reference_material_selected");
		resourceEducationalLabel.setText(i18n.GL1669());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("quizPanel")
	void quizPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_quiz_selected");
		resourceEducationalLabel.setText(i18n.GL1670());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("curriculumPlanPanel")
	void curriculumPlanPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_curriculum_plan_selected");
		resourceEducationalLabel.setText(i18n.GL1671());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("lessonPlanPanel")
	void lessonPlanPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_lesson_plan_selected");
		resourceEducationalLabel.setText(i18n.GL1672());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("unitPlanPanel")
	void unitPlanPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_unit_plan_selected");
		resourceEducationalLabel.setText(i18n.GL1673());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("projectPlanPanel")
	void projectPlanPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_project_plan_selected");
		resourceEducationalLabel.setText(i18n.GL1674());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("readingPanel")
	void readingPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_reading_selected");
		resourceEducationalLabel.setText(i18n.GL1675());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("textbookPanel")
	void textbookPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_textbook_selected");
		resourceEducationalLabel.setText(i18n.GL0909());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("articlePanel")
	void articlePanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_article_selected");
		resourceEducationalLabel.setText(i18n.GL1676());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("bookPanel")
	void bookPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_book_selected");
		resourceEducationalLabel.setText(i18n.GL1677());
		educationalUsePanel.setVisible(false);
		educationalDropDownLblOpen = false;
		mandatoryEducationalLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}

	@UiHandler("educationalDropDownLbl")
	public void educationalDropDownClick(ClickEvent event) {
		hasClickedOnDropDwn=true;
		if (educationalDropDownLblOpen == false) {
			educationalUsePanel.setVisible(true);
			educationalDropDownLblOpen = true;
		} else {
			educationalUsePanel.setVisible(false);
			educationalDropDownLblOpen = false;
		}
	}
	@UiHandler("defaultPanelMomentsOfLearningPnl")
	void defaultPanelMomentsOfLearningPnl(ClickEvent event) {
		resourcemomentsOfLearningLabel.setText(i18n.GL1684());
		resourcemomentsOfLearningLabel.getElement().setAttribute("alt", i18n.GL1684());
		resourcemomentsOfLearningLabel.getElement().setAttribute("title", i18n.GL1684());
		momentsOfLearningPanel.setVisible(false);
		momentsOfLearningOpen = false;
		mandatorymomentsOfLearninglLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("preparingTheLearningPanel")
	void preparingTheLearningPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_preparing_the_learning_selected");
		resourcemomentsOfLearningLabel.setText(i18n.GL1679());
		resourcemomentsOfLearningLabel.getElement().setAttribute("alt", i18n.GL1679());
		resourcemomentsOfLearningLabel.getElement().setAttribute("title", i18n.GL1679());
		momentsOfLearningPanel.setVisible(false);
		momentsOfLearningOpen = false;
		mandatorymomentsOfLearninglLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("interactingWithTheTextPanel")
	void interactingWithTheTextPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_interacting_with_the_text_selected");
		resourcemomentsOfLearningLabel.setText(i18n.GL1680());
		resourcemomentsOfLearningLabel.getElement().setAttribute("alt", i18n.GL1680());
		resourcemomentsOfLearningLabel.getElement().setAttribute("title", i18n.GL1680());
		momentsOfLearningPanel.setVisible(false);
		momentsOfLearningOpen = false;
		mandatorymomentsOfLearninglLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("extendingUnderstandingPanel")
	void extendingUnderstandingPanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_extending_Understanding_selected");
		resourcemomentsOfLearningLabel.setText(i18n.GL1681());
		resourcemomentsOfLearningLabel.getElement().setAttribute("alt", i18n.GL1681());
		resourcemomentsOfLearningLabel.getElement().setAttribute("title", i18n.GL1681());
		momentsOfLearningPanel.setVisible(false);
		momentsOfLearningOpen = false;
		mandatorymomentsOfLearninglLbl.setVisible(false);
		setAdvancedOptionsStyles();
	}
	@UiHandler("momentsOfLearningDropDownLbl")
	public void momentsOfLearningDropDownClick(ClickEvent event) {
		hasClickedOnDropDwn=true;
		if (momentsOfLearningOpen == false) {
			momentsOfLearningPanel.setVisible(true);
			momentsOfLearningOpen = true;
		} else {
			momentsOfLearningPanel.setVisible(false);
			momentsOfLearningOpen = false;
		}
	}
	@UiHandler("educatioNalUseDropContainer")
	public void educatioNalUseDropContainerDropDownClick(ClickEvent event) {
		hasClickedOnDropDwn=true;
		if (educationalDropDownLblOpen1 == false) {
			educationalUsePanel.setVisible(true);
			educationalDropDownLblOpen1 = true;
		} else {
			educationalUsePanel.setVisible(false);
			educationalDropDownLblOpen1 = false;
		}
	}
	@UiHandler("momentsOfLearningDropDownContianer")
	public void momentsOfLearningDropDownContainerClick(ClickEvent event) {
		hasClickedOnDropDwn=true;
		if (momentsOfLearningOpen1 == false) {
			momentsOfLearningPanel.setVisible(true);
			momentsOfLearningOpen1 = true;
		} else {
			momentsOfLearningPanel.setVisible(false);
			momentsOfLearningOpen1 = false;
		}
	}


	/**
	 *  Adding new skills for the resource collection , will check it has more than
	 * fifteen standards
	 * @param centuryTag
	 * @param id
	 */
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
	 * This will return the code id based on the code
	 * @param code
	 * @param codes
	 * @return
	 */
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
	/**
	 * If standards added reached to max number this method will be invoked.
	 */
	public void standardMaxShow() {
		standardSgstBox.addStyleName(CollectionCBundle.INSTANCE.css().standardTxtBox());
		standardMaxMsg.setStyleName(CollectionCBundle.INSTANCE.css().standardMax());
		//standardsPanel.addStyleName(CollectionCBundle.INSTANCE.css().floatLeftNeeded());
		new FadeInAndOut(standardMaxMsg.getElement(), 5000, 5000);
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
					if(codeObj.getCodeId()==Integer.parseInt(id)){
						//standardsDo.remove(codeObj);
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
				}
				this.getParent().removeFromParent();
				updateStandardsAdvancedSetupStyle();
			}
		};
		return new DownToolTipWidgetUc(closeLabel, description);
	}


	/**
	 * @param code {@link String}
	 * @param codes {@link List}
	 * @return {@link String}
	 */
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
	 * If resource has mobile friendly option, this method will be invoked and enables the respective value.
	 * @param mobileFriendlyVal {@link String}
	 */
	public void setMobileFriendlyObjectVal(String mobileFriendlyVal)
	{
		if(mobileFriendlyVal.trim().contains(i18n.GL_GRR_YES().trim())){
			mobileYes.getElement().setClassName("at-OffButtonsActive");
			mobileNo.getElement().setClassName("at-OnButtonDeActive");
		}
		else if(mobileFriendlyVal.trim().contains(i18n.GL1735().trim())){
			mobileNo.getElement().setClassName("at-OffButtonsActive");
			mobileYes.getElement().setClassName("at-OnButtonDeActive");
		}
		updateMobileFriendlyAdvancedStyles();
	}


	/**
	 * Clickhandler for mobile friendly "YES" button.
	 * @param click {@link ClickEvent}
	 */
	@UiHandler("mobileYes")
	public void onmobileYesClick(ClickEvent click){
		mobileYes.getElement().setClassName("at-OffButtonsActive");
		mobileNo.getElement().setClassName("at-OnButtonDeActive");
		updateMobileFriendlyAdvancedStyles();
	}

	/**
	 * Clickhandler for mobile friendly "NO" button.
	 * @param click {@link ClickEvent}
	 */
	@UiHandler("mobileNo")
	public void onmobileNoClick(ClickEvent click){
		mobileNo.getElement().setClassName("at-OffButtonsActive");
		mobileYes.getElement().setClassName("at-OnButtonDeActive");
		updateMobileFriendlyAdvancedStyles();
	}
	/**
	 * If resource has any media features, this method will be invoked and sets the respective value.
	 * @param mediaFeatureVal {@link String}
	 */
	public void setMediaFeatureObjectVal(String mediaFeatureVal){
		try{
			if(mediaFeatureVal != null)
			{
				String[] stringArry=mediaFeatureVal.split(" : ");
				if(stringArry.length!=0){
					mobileFeature = stringArry[1].trim();
					lblMediaPlaceHolder.setText(stringArry[1].trim());
					spanelMediaFeaturePanel.setVisible(false);
					//lblMediaPlaceHolder.getElement().setId(titleLabel.getElement().getId());
					lblMediaPlaceHolder.setStyleName(CollectionAssignCBundle.INSTANCE.css().selectedClasspageText());
					setAdvancedOptionsStyles();
				}
			}
		}
		catch(Exception ex)	{
			AppClientFactory.printSevereLogger(ex.getMessage());
		}
	}

	/**
	 * @param event
	 * @return
	 */
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
	 * Opens the media feature drop down.
	 */
	private void OpenMediaFeatureDropdown() {
		hasClickedOnDropDwn=true;
		if (spanelMediaFeaturePanel.isVisible()){
			spanelMediaFeaturePanel.setVisible(false);
		}else{
			spanelMediaFeaturePanel.setVisible(true);
		}
	}

	/**
	 * Inner class to invoke profanity checker.
	 *
	 */
	public class CheckProfanityInOnBlur implements BlurHandler{
		private TextBox textBox;
		private Label label;
		private TextArea textArea;
		public CheckProfanityInOnBlur(TextBox textBox,TextArea textArea,Label label){
			this.textBox=textBox;
			this.label=label;
			this.textArea=textArea;
		}
		@Override
		public void onBlur(BlurEvent event) {
			Map<String, String> parms = new HashMap<String, String>();
			if(textBox!=null){
				parms.put("text", textBox.getValue());
			}else{
				parms.put("text", textArea.getText());
			}
		//	addResourceBtn.setEnabled(false);
		//	addResourceBtn.addStyleName("disabled");
			AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

				@Override
				public void onSuccess(Boolean value) {
					addResourceBtn.setEnabled(true);
					//addResourceBtn.removeStyleName("disabled");
					if(textBox!=null){
						isHavingBadWordsInTextbox = value;
						SetStyleForProfanity.SetStyleForProfanityForTextBox(textBox, label, value);
					}else{
						isHavingBadWordsInRichText=value;
						SetStyleForProfanity.SetStyleForProfanityForTextArea(textArea, label, value);
					}

				}
			});
		}
	}
	/**
	 * This method is used to set Styles for Advanced Options(Educational Use,Moments Of Learning and Media Feature)
	 */
	public void setAdvancedOptionsStyles(){
		if(resourceEducationalLabel.getText().equalsIgnoreCase(i18n.GL1684())){
			addSetupAdvancedView.educationUseAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
		}else{
			addSetupAdvancedView.educationUseAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.educationUseAdvancedContainer.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
		if(resourcemomentsOfLearningLabel.getText().equalsIgnoreCase(i18n.GL1684())){
			addSetupAdvancedView.momentsOfLearningAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
		}else{
			addSetupAdvancedView.momentsOfLearningAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.momentsOfLearningAdvancedContainer.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
		if(lblMediaPlaceHolder.getText().equalsIgnoreCase("Choose a Media Feature Option:") || lblMediaPlaceHolder.getText().equalsIgnoreCase("")){
			addSetupAdvancedView.mediaFeatureAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
		}else{
			addSetupAdvancedView.mediaFeatureAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.mediaFeatureAdvancedContainer.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
	}
	/**
	 * This method is used to set Styles for Access Hazard Advanced Options
	 * @param length
	 */
	public void setAdvancedAccessHazardStyles(int length){
		if(length == 0){
			addSetupAdvancedView.accessHazardAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
		}else{
			addSetupAdvancedView.accessHazardAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.accessHazardAdvancedContainer.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
	}

	/**
	 *  This method is used to set Styles for Mobile Friendly Advanced Option
	 */
	public void updateMobileFriendlyAdvancedStyles(){
		if(mobileYes.getStyleName().contains("at-OffButtonsActive"))
		{
			addSetupAdvancedView.mobileFreindlyAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.mobileFreindlyAdvancedContainer.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
		else if(mobileNo.getStyleName().contains("at-OffButtonsActive"))
		{
			addSetupAdvancedView.mobileFreindlyAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
/*			addSetupAdvancedView.mobileFreindlyAdvancedContainer.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());*/		}
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
	public List<Map<String,String>> getStandards(){
		List<Map<String,String>> standardsList=new ArrayList<Map<String,String>>();
		int size=ulSelectedItems.getWidgetCount();
		for(int i=0;i<size;i++){
			Map<String, String> map=new HashMap<String, String>();
			LiPanelWithClose downToolTipWidgetUc=(LiPanelWithClose)ulSelectedItems.getWidget(i);
			map.put("id",String.valueOf(downToolTipWidgetUc.getId()));
			map.put("code", downToolTipWidgetUc.getName());
			standardsList.add(map);
		}

		return standardsList;
	}
	/**
	 *
	 * @function updateStandardsAdvancedSetupStyle
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description This method is used to set styles for standards based on the number of standards.
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public void updateStandardsAdvancedSetupStyle() {
		if(ulSelectedItems.getWidgetCount()==0){
			addSetupAdvancedView.standardsAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
		}else{
			addSetupAdvancedView.standardsAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.standardsAdvancedContainer.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
	}
}
