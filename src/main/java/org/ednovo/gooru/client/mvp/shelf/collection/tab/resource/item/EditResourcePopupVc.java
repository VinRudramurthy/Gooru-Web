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
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;
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
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.URL;
import com.google.gwt.regexp.shared.MatchResult;
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
import com.google.gwt.user.client.ui.FlowPanel;
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

public abstract class EditResourcePopupVc extends AppPopUp implements SelectionHandler<SuggestOracle.Suggestion>{

	CollectionItemDo collectionItemDo;
	CollectionItemDo collectionOriginalItemDo;

	@UiField HTMLPanel panelCategory;

	@UiField
	public Button addResourceBtn,cancelResourcePopupBtnLbl,mobileYes,mobileNo,browseCentury;

	@UiField
	public Label resoureDropDownLbl;

	@UiField
	Label standardMaxMsg,mandatoryEducationalLbl,mandatoryUrlLbl, mandatoryTitleLbl,educationalDropDownLbl;

	@UiField
	Label mandatoryCategoryLbl, urlTextLbl,refreshLbl;

	@UiField
	Label leftArrowLbl, rightArrowLbl, uploadImageLbl,mandatoryDescLblForSwareWords,mandatoryTitleLblForSwareWords,momentsOfLearningDropDownLbl;

	@UiField
	public TextBox titleTextBox;

	@UiField
	public TextArea descriptionTxtAera;

	@UiField HTMLEventPanel videoResourcePanel,lblContentRights,interactiveResourcePanel,websiteResourcePanel,imageResourcePanel,
	textResourcePanel,audioResourcePanel,educatioNalUseDropContainer,momentsOfLearningDropDownContianer;
	@UiField
	Image setThumbnailImage,generateImageLbl;

	// Drop down for Resource Type//
	@UiField
	HTMLPanel momentsOfLearningTitle,educationalTitle,
	descCharcterLimit,saveButtonContainer,panelContentRights,rightsContainer,videoPanel,interactivePanel,websitePanel,imagePanel,textsPanel,audioPanel,
	rightsContent,educationalContainer,momentsOfLearningContainer,accessHazardContainer,standardsBrowseContainer,centuryBrowseContainer,mobileFriendlyContainer,mediaFeatureContainer;//otherPanel

	@UiField Label mandatorymomentsOfLearninglLbl,standardsDefaultText,centuryDefaultText,resourceCategoryLabel,loadingTextLbl,rightsLbl;

	@UiField HTMLPanel hazardContainer,categorypanel,video,interactive,website,resourceTypePanel,image,texts,audio,resourceFormat,resDescription,urlTextPanel,titleTextPanel,thumbnailLbl,orLbl,refreshLblPanel,errorContainer;//other,
	@UiField CheckBox rightsChkBox;

	@UiField InlineLabel agreeText,andText,additionalText,commuGuideLinesAnr, termsAndPolicyAnr,privacyAnr,copyRightAnr,moblieFriendly,advancedText;

	@UiField(provided = true)
	AppSuggestBox standardSgstBox,centurySgstBox;

	@UiField FlowPanel standardContainer,centuryPanel,centuryContainer;

	@UiField Label accessHazard;

	@UiField
	HTMLEventPanel btnStandardsBrowse;
	@UiField
	UlPanel standardsDropListValues;

	@UiField Label mediaLabel,lblMediaPlaceHolder,lblMediaFeatureArrow;

	@UiField ScrollPanel spanelMediaFeaturePanel;

	@UiField HTMLPanel standardsCont,htmlMediaFeatureListContainer,mediaLabelContainer,mediaDropdownArrowConatainer;

	@UiField HTMLEventPanel AdvancedSetupContainer,eHearderIconEducationalUse,eHearderIconMomentsOfLearning,eHearderIconstandards,eHearderIconCentury,
	eHearderIconAccessHazard,eHearderIconMediafeature,eHearderIconMobileFriendly;

	@UiField UlPanel ulSelectedItems;

	@UiField Anchor resourceEducationalLabel,resourcemomentsOfLearningLabel;
	@UiField UlPanel educationalUsePanel,momentsOfLearningPanel;
	private static final String USER_META_ACTIVE_FLAG = "0";
	ResourceMetaInfoDo resMetaInfoDo = null;
	private CopyRightPolicyVc copyRightPolicy;

	private TermsAndPolicyVc termsAndPolicyVc;
	private TermsOfUse termsOfUse;
	private int activeImageIndex = 0;
	protected List<String> thumbnailImagesLink;

	private String thumbnailUrlStr = null;

	private boolean hasClickedOnDropDwn = false;

	private String mobileFeature;

	String fileNameWithOutRespUrl = null;

	public boolean resoureDropDownLblOpen = false,educationalDropDownLblOpen=false,educationalDropDownLblOpen1=false,momentsOfLearningOpen=false,momentsOfLearningOpen1=false;

	private static final String DEFULT_IMAGE_PREFIX = "images/default-";

	private static final String PNG = ".png";
	boolean isHavingBadWordsInTextbox=false,isHavingBadWordsInRichText=false;

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
	private ArrayList<checkboxSelectedDo> momentsOfLearning;
	private ArrayList<checkboxSelectedDo> educationalUse;
	private List<checkboxSelectedDo> accessHazardList;
	private List<ListValuesDo> mediaFeature;

	private static final String DEFAULT_COMBO_BOX_TEXT ="Please choose one of the following...";
	StandardsPreferenceOrganizeToolTip standardsPreferenceOrganizeToolTip=new StandardsPreferenceOrganizeToolTip();
	private boolean isBrowseTooltip =false;

	BrowseStandardsTooltip browseStandardsTooltip;
	private boolean isBrowseStandardsToolTip = false;

	public AddSetupAdvancedView addSetupAdvancedView;

	final List<String> tagList = new ArrayList<String>();

	List<String> tagListGlobal = new ArrayList<String>();

	HandlerRegistration videoClickHandler=null,websiteClickHandler=null,interactiveClickHandler=null,imageClickHandler=null,textClickHandler=null,audioClickHandler=null;

	private static EditResourcePopupVcUiBinder uiBinder = GWT
			.create(EditResourcePopupVcUiBinder.class);

	interface EditResourcePopupVcUiBinder extends
	UiBinder<Widget, EditResourcePopupVc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);
	String mediaFeatureStr = i18n.GL1767();
	public abstract void updateResource(CollectionItemDo collectionItemDo,List<String> tagList);
	private boolean isQuestionResource=false;
	private boolean isUserResource=false;
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


	public EditResourcePopupVc(CollectionItemDo collectionItemDo) {
		super();
		AddSetupAdvancedCBundle.INSTANCE.css().ensureInjected();
		CollectionEditResourceCBundle.INSTANCE.css().ensureInjected();
		standardSuggestOracle = new AppMultiWordSuggestOracle(true);
		centurySuggestOracle= new AppMultiWordSuggestOracle(true);
		standardSearchDo.setPageSize(10);
		super.getMainPanel().addStyleName("PopupMainVSmall");
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
		standardSgstBox.getElement().getStyle().setFontSize(12, Unit.PX);
		BlurHandler blurHandler=new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if(standardsPreferenceOrganizeToolTip.isShowing()){
					errorContainer.setVisible(false);
				}
			}
		};
		Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				hideDropDown(event);
			}
		});
		mediaFeature=collectionItemDo.getMediaFeature();
		momentsOfLearning=collectionItemDo.getMomentsOfLearning();
		educationalUse=collectionItemDo.getEducationalUse();
		accessHazardList=collectionItemDo.getAccessHazard();



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
				updateCenturyAdvancedSetupStyle();

			}
		});
		this.collectionItemDo = collectionItemDo;
		this.collectionOriginalItemDo = collectionItemDo;

		setContent(i18n.GL0949(), uiBinder.createAndBindUi(this));
		getCloseBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		advancedText.setText(i18n.GL3096());
		mediaLabelContainer.getElement().getStyle().setMarginBottom(10, Unit.PX);
		standardsCont.getElement().setAttribute("style", "position:relative;");
		addSetupAdvancedView = new AddSetupAdvancedView() {
			@Override
			public void showAndHideContainers() {
			}
		};
		getAddStandards();

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
		resourceEducationalLabel.getElement().setAttribute("data-toggle", "dropdown");
		resourcemomentsOfLearningLabel.getElement().setAttribute("data-toggle", "dropdown");
		setMomentOfLeaning();
		setEducationUse();
		setHazard();
		AdvancedSetupContainer.add(addSetupAdvancedView);
		errorContainer.setVisible(false);
		errorContainer.add(standardsPreferenceOrganizeToolTip);
		addResourceBtn.addClickHandler(new AddClickHandler());
		addResourceBtn.getElement().getStyle().setFloat(Float.LEFT);
		uploadImageLbl.addClickHandler(new OnEditImageClick());
		titleTextBox.getElement().setId("txtTitleTextBox");
		StringUtil.setAttributes(titleTextBox, true);
		titleTextBox.addKeyUpHandler(new TitleKeyUpHandler());
		descriptionTxtAera.getElement().setId("tatDescriptionTxtAera");
		StringUtil.setAttributes(descriptionTxtAera, true);
		descriptionTxtAera.addKeyUpHandler(new DescriptionKeyUpHandler());
		titleTextBox.getElement().setAttribute("maxlength", "50");
		descriptionTxtAera.getElement().setAttribute("maxlength", "300");
		mandatoryTitleLblForSwareWords.getElement().setId("lblMandatoryTitleLblForSwareWords");
		mandatoryDescLblForSwareWords.getElement().setId("lblMandatoryDescLblForSwareWords");
		mandatoryUrlLbl.setVisible(false);
		clearTitleErrorMessage();
		clearCategoryErrorMessage();
		descCharcterLimit.setVisible(false);
		leftArrowLbl.setVisible(false);
		rightArrowLbl.setVisible(false);
		setThumbnailImage.setVisible(true);
		setThumbnailImage.getElement().setId("imgSetThumbnailImage");
		resourceTypePanel.setVisible(true);
		loadingTextLbl.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
		generateImageLbl.setVisible(false);
		saveButtonContainer.getElement().setId("pnlSaveButtonContainer");
		saveButtonContainer.setVisible(true);
		loadingTextLbl.setVisible(false);
		panelContentRights.getElement().setId("pnlPanelContentRights");
		rightsContent.getElement().setId("pnlRightsContent");
		panelContentRights.setVisible(false);
		mandatoryTitleLblForSwareWords.setVisible(false);
		mandatoryDescLblForSwareWords.setVisible(false);
		rightsChkBox.addClickHandler(new rightsChecked());
		rightsChkBox.getElement().setId("chkRights");
		setModal(true);
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));

		videoClickHandler=videoResourcePanel.addClickHandler(new checkAvailableClickHandler());
		websiteClickHandler=websiteResourcePanel.addClickHandler(new checkAvailableClickHandler());
		interactiveClickHandler = interactiveResourcePanel.addClickHandler(new checkAvailableClickHandler());
		imageClickHandler=imageResourcePanel.addClickHandler(new checkAvailableClickHandler());
		textClickHandler = textResourcePanel.addClickHandler(new checkAvailableClickHandler());
		audioClickHandler = audioResourcePanel.addClickHandler(new checkAvailableClickHandler());

		categorypanel.getElement().setId("pnlCategorypanel");
		mandatoryTitleLbl.getElement().setId("lblMandatoryTitleLbl");
		mandatoryTitleLbl.setText(i18n.GL0173());
		mandatoryTitleLbl.getElement().setAttribute("alt", i18n.GL0173());
		mandatoryTitleLbl.getElement().setAttribute("title", i18n.GL0173());
		descCharcterLimit.getElement().setInnerText(i18n.GL0143());
		descCharcterLimit.getElement().setId("pnlDescCharcterLimit");
		descCharcterLimit.getElement().setAttribute("alt", i18n.GL0143());
		descCharcterLimit.getElement().setAttribute("title", i18n.GL0143());
		mandatoryCategoryLbl.setText(i18n.GL1515());
		mandatoryCategoryLbl.getElement().setId("lblMandatoryCategoryLbl");
		mandatoryCategoryLbl.getElement().setAttribute("alt", i18n.GL1515());
		mandatoryCategoryLbl.getElement().setAttribute("title", i18n.GL1515());
		thumbnailLbl.getElement().setInnerText(i18n.GL0911());
		thumbnailLbl.getElement().setId("pnlThumbnailLbl");
		thumbnailLbl.getElement().setAttribute("alt", i18n.GL0911());
		thumbnailLbl.getElement().setAttribute("title", i18n.GL0911());
		titleTextPanel.getElement().setInnerText(i18n.GL0318()+i18n.GL_SPL_STAR());
		titleTextPanel.getElement().setId("pnlTitleTextPanel");
		titleTextPanel.getElement().setAttribute("alt", i18n.GL0318()+i18n.GL_SPL_STAR());
		titleTextPanel.getElement().setAttribute("title", i18n.GL0318()+i18n.GL_SPL_STAR());

		videoPanel.getElement().setInnerHTML(i18n.GL0918());
		videoPanel.getElement().setId("pnlVideoPanel");
		videoPanel.getElement().setAttribute("alt", i18n.GL0918());
		videoPanel.getElement().setAttribute("title", i18n.GL0918());

		interactivePanel.getElement().setInnerHTML(i18n.GL0919());
		interactivePanel.getElement().setId("pnlInteractivePanel");
		interactivePanel.getElement().setAttribute("alt", i18n.GL0919());
		interactivePanel.getElement().setAttribute("title", i18n.GL0919());

		websitePanel.getElement().setInnerHTML(i18n.GL1396());
		websitePanel.getElement().setId("pnlWebsitePanel");
		websitePanel.getElement().setAttribute("alt", i18n.GL1396());
		websitePanel.getElement().setAttribute("title", i18n.GL1396());
		textsPanel.getElement().setInnerHTML(i18n.GL1044());
		textsPanel.getElement().setId("pnlTextsPanel");
		textsPanel.getElement().setAttribute("alt", i18n.GL1044());
		textsPanel.getElement().setAttribute("title", i18n.GL1044());
		audioPanel.getElement().setInnerHTML(i18n.GL1045());
		audioPanel.getElement().setId("pnlAudioPanel");
		audioPanel.getElement().setAttribute("alt", i18n.GL1045());
		audioPanel.getElement().setAttribute("title", i18n.GL1045());
		imagePanel.getElement().setInnerHTML(i18n.GL1046());
		imagePanel.getElement().setId("pnlImagePanel");
		imagePanel.getElement().setAttribute("alt", i18n.GL1046());
		imagePanel.getElement().setAttribute("title", i18n.GL1046());
		generateImageLbl.setUrl("../images/NewResourcePopup/PreviewResourceThumbnail.png");
		generateImageLbl.getElement().setId("lblGenerateImageLbl");
		generateImageLbl.getElement().setAttribute("alt", i18n.GL0922());
		generateImageLbl.getElement().setAttribute("title", i18n.GL0922());
		orLbl.getElement().setInnerText(i18n.GL_GRR_Hyphen()+i18n.GL0209().toLowerCase()+i18n.GL_GRR_Hyphen());
		orLbl.getElement().setId("pnlOrLbl");
		orLbl.getElement().setAttribute("alt", i18n.GL_GRR_Hyphen()+i18n.GL0209().toLowerCase()+i18n.GL_GRR_Hyphen());
		orLbl.getElement().setAttribute("title", i18n.GL_GRR_Hyphen()+i18n.GL0209().toLowerCase()+i18n.GL_GRR_Hyphen());
		uploadImageLbl.setText(i18n.GL0912());
		uploadImageLbl.getElement().setId("lblUploadImageLbl");
		uploadImageLbl.getElement().setAttribute("alt", i18n.GL0912());
		uploadImageLbl.getElement().setAttribute("title", i18n.GL0912());
		refreshLblPanel.getElement().setInnerText(i18n.GL0923());
		refreshLblPanel.getElement().setId("pnlRefreshLblPanel");
		refreshLblPanel.getElement().setAttribute("alt", i18n.GL0923());
		refreshLblPanel.getElement().setAttribute("title", i18n.GL0923());
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
		termsAndPolicyAnr.getElement().setAttribute("alt", i18n.GL0872()+i18n.GL_GRR_COMMA());
		termsAndPolicyAnr.getElement().setAttribute("title", i18n.GL0872()+i18n.GL_GRR_COMMA());
		privacyAnr.setText(" "+i18n.GL0873());
		privacyAnr.getElement().setId("lnkPrivacyAnr");
		privacyAnr.getElement().setAttribute("alt", i18n.GL0873());
		privacyAnr.getElement().setAttribute("title", i18n.GL0873());
		andText.setText(" "+i18n.GL_GRR_AND()+" ");
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
		loadingTextLbl.setText(i18n.GL0808().toLowerCase());
		loadingTextLbl.getElement().setId("lblLoadingTextLbl");
		loadingTextLbl.getElement().setAttribute("alt", i18n.GL0808().toLowerCase());
		loadingTextLbl.getElement().setAttribute("title", i18n.GL0808().toLowerCase());
		resourceFormat.getElement().setInnerHTML(i18n.GL3103()+i18n.GL_SPL_STAR());
		resourceFormat.getElement().setId("pnlResourceFormat");
		resourceFormat.getElement().setAttribute("alt", i18n.GL0906());
		resourceFormat.getElement().setAttribute("title", i18n.GL0906());

		resDescription.getElement().setInnerHTML(i18n.GL0904());
		resDescription.getElement().setId("pnlResDescription");
		resDescription.getElement().setAttribute("alt", i18n.GL0904());
		resDescription.getElement().setAttribute("title", i18n.GL0904());

		urlTextPanel.getElement().setInnerHTML(i18n.GL0915());
		urlTextPanel.getElement().setId("pnlUrlTextPanel");
		urlTextPanel.getElement().setAttribute("alt", i18n.GL0915());
		urlTextPanel.getElement().setAttribute("title", i18n.GL0915());


		mandatoryUrlLbl.setText(i18n.GL0916());
		mandatoryUrlLbl.getElement().setId("lblMandatoryUrlLbl");
		mandatoryUrlLbl.getElement().setAttribute("alt", i18n.GL0916());
		mandatoryUrlLbl.getElement().setAttribute("title", i18n.GL0916());

		educationalTitle.getElement().setInnerHTML(i18n.GL1664());
		educationalTitle.getElement().setId("lblEducationalTitle");
		educationalTitle.getElement().setAttribute("alt", i18n.GL1664());
		educationalTitle.getElement().setAttribute("title", i18n.GL1664());
		standardsDefaultText.setText(i18n.GL1682());
		standardsDefaultText.getElement().setId("lblStandardsDefaultText");
		standardsDefaultText.getElement().setAttribute("alt", i18n.GL1682());
		standardsDefaultText.getElement().setAttribute("title", i18n.GL1682());

		centuryDefaultText.setText(i18n.GL3199());
		centuryDefaultText.getElement().setId("lblCenturyDefaultText");
		centuryDefaultText.getElement().setAttribute("alt", i18n.GL3199());
		centuryDefaultText.getElement().setAttribute("title", i18n.GL3199());


		momentsOfLearningTitle.getElement().setInnerHTML(i18n.GL1678());
		momentsOfLearningTitle.getElement().setId("pnlMomentsOfLearningTitle");
		momentsOfLearningTitle.getElement().setAttribute("alt", i18n.GL1678());
		momentsOfLearningTitle.getElement().setAttribute("title", i18n.GL1678());


		resourceEducationalLabel.setText(i18n.GL1684());
		resourceEducationalLabel.getElement().setId("lblResourceEducationalLabel");
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1684());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1684());
		resourcemomentsOfLearningLabel.setText(i18n.GL1684());
		resourcemomentsOfLearningLabel.getElement().setId("lblResourcemomentsOfLearningLabel");
		resourcemomentsOfLearningLabel.getElement().setAttribute("alt", i18n.GL1684());
		resourcemomentsOfLearningLabel.getElement().setAttribute("title", i18n.GL1684());
		urlTextLbl.getElement().setId("lblUrlTextLbl");
		resourceCategoryLabel.getElement().setId("lblResourceCategoryLabel");
		resoureDropDownLbl.getElement().setId("lblResoureDropDownLbl");
		resourceTypePanel.getElement().setId("pnlResourceTypePanel");
		videoResourcePanel.getElement().setId("epnlVideoResourcePanel");
		interactiveResourcePanel.getElement().setId("epnlInteractiveResourcePanel");
		websiteResourcePanel.getElement().setId("epnlWebsiteResourcePanel");
		imageResourcePanel.getElement().setId("epnlImageResourcePanel");
		textResourcePanel.getElement().setId("epnlTextResourcePanel");
		audioResourcePanel.getElement().setId("epnlAudioResourcePanel");
		video.getElement().setId("pnlVideo");
		interactive.getElement().setId("pnlInteractive");
		website.getElement().setId("pnlWebsite");
		image.getElement().setId("pnlImage");
		texts.getElement().setId("pnlTexts");
		audio.getElement().setId("pnlAudio");
		educationalDropDownLbl.getElement().setId("lblEducationalDropDownLbl");
		mandatoryEducationalLbl.getElement().setId("lblMandatoryEducationalLbl");

		momentsOfLearningDropDownLbl.getElement().setId("lblMomentsOfLearningDropDownLbl");
		mandatorymomentsOfLearninglLbl.getElement().setId("lblMandatorymomentsOfLearninglLbl");

		standardContainer.getElement().setId("fpnlStandardContainer");
		standardSgstBox.getElement().setId("StandardSgstBox");
		standardMaxMsg.getElement().setId("lblStandardMaxMsg");
		leftArrowLbl.getElement().setId("lblLeftArrowLbl");
		rightArrowLbl.getElement().setId("lblRightArrowLbl");
		refreshLbl.getElement().setId("lblRefreshLbl");
		rightsContainer.getElement().setId("pnlRightsContainer");
		lblContentRights.getElement().setId("epnlLblContentRights");
		cancelResourcePopupBtnLbl.getElement().setAttribute("style", "margin-left:10px");

		displayResourceInfo();
		show();
		center();
		AppClientFactory.fireEvent(new GetEditPageHeightEvent(this, false));
		getResourceMetaInfo(collectionItemDo.getResource().getUrl());
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
		titleTextBox.addBlurHandler(new CheckProfanityInOnBlur(titleTextBox, null, mandatoryTitleLblForSwareWords));
		descriptionTxtAera.addBlurHandler(new CheckProfanityInOnBlur(null, descriptionTxtAera, mandatoryDescLblForSwareWords));

		mediaDropdownArrowConatainer.getElement().getStyle().setRight(10, Unit.PX);
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
		/*List<String> mediaFeatureList = Arrays.asList(mediaFeatureStr.split(","));
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
		}*/
		getMediaFeatures();
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
		centuryBrowseContainer.setVisible(false);
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
		standardsBrowseContainer.getElement().setId("standardsContainerBswn");
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
						DownToolTipWidgetUc downToolTipWidgetUc=create21CenturyLabel(entry.getValue(),entry.getKey()+"","");
						downToolTipWidgetUc.getElement().setId(entry.getKey()+"");
						downToolTipWidgetUc.getElement().setTitle(entry.getValue()+"");
						centuryPanel.add(downToolTipWidgetUc);
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
				&& !addSetupAdvancedView.mobileFreindlyAdvancedPnl.isVisible()
				&& !addSetupAdvancedView.centuryAdvancedPnl.isVisible()) {
			allAdditionalTagInVisisble = true;
		}
		return allAdditionalTagInVisisble;
	}

	public abstract void browseStandardsInfo(boolean val,boolean userResource);
	public abstract void closeStandardsPopup();

	private class onBrowseStandardsClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			isQuestionResource= false;
			isUserResource = false;
			browseStandardsInfo(isQuestionResource,isUserResource);
		}
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
	private void OpenMediaFeatureDropdown() {
		hasClickedOnDropDwn=true;
		if (spanelMediaFeaturePanel.isVisible()){
			spanelMediaFeaturePanel.setVisible(false);
		}else{
			spanelMediaFeaturePanel.setVisible(true);
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

	@UiHandler("cancelResourcePopupBtnLbl")
	public void cancelPopUp(ClickEvent clickEvent) {
		AppClientFactory.fireEvent(new GetEditPageHeightEvent(this, true));

		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		if(deletedStandardsDo.size()>0){
			AppClientFactory.getInjector().getResourceService().UpdateResourceTaxonomy(collectionItemDo.getResource().getGooruOid(), deletedStandardsDo, new SimpleAsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					deletedStandardsDo.clear();
				}
			});
		}
		hide();
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
		if(result.getImages()!=null && result.getImages().size()>1){
			rightArrowLbl.setVisible(true);
		}else{
			rightArrowLbl.setVisible(false);
		}
		setThumbnailImages(result.getImages());
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
	private void setThumbnailImages(List<String> images) {
		thumbnailImagesLink = images;
	}

	private void setResMetaInfo(ResourceMetaInfoDo result) {
		this.resMetaInfoDo = result;
	}

	public void setResourceDescription(){
		descriptionTxtAera.setText(collectionItemDo.getResource().getDescription());
		descriptionTxtAera.getElement().setAttribute("alt", collectionItemDo.getResource().getDescription());
		descriptionTxtAera.getElement().setAttribute("title", collectionItemDo.getResource().getDescription());
	}

	public void displayResourceInfo() {
		String url = collectionItemDo.getResource().getUrl()!=null?collectionItemDo.getResource().getUrl():"";
		updateStandardsAdvancedSetupStyle();
		if(collectionItemDo.getResource().getResourceTags()!=null){
			for(int i=0;i<collectionItemDo.getResource().getResourceTags().size();i++){
				tagListGlobal.add("\""+collectionItemDo.getResource().getResourceTags().get(i).getLabel()+"\"");

				if(collectionItemDo.getMediaFeature()!=null&&collectionItemDo.getMediaFeature().size()>0)
				{
					setMediaFeatureObjectVal(collectionItemDo.getMediaFeature());
				}



				if(collectionItemDo.getAccessHazard()!=null&&collectionItemDo.getAccessHazard().size()>0)
				{
					setAccessHazardObjectVal(collectionItemDo.getAccessHazard());
				}
			}
		}


		urlTextLbl.setText(url);
		urlTextLbl.getElement().setAttribute("alt", i18n.GL0827());
		urlTextLbl.getElement().setAttribute("title", i18n.GL0827());
		String	userUrlStr = URL.encode(url);
		if(userUrlStr.indexOf("youtube") >0){
			setVideoCategory();
			if(websiteClickHandler!=null){
				try{
					websiteClickHandler.removeHandler();
				}catch(Exception e){
					AppClientFactory.printSevereLogger("EditResourcePopupVc websiteClickHandler::"+e);
				}
			}
			if(interactiveClickHandler!=null){
				try{
					interactiveClickHandler.removeHandler();
				}catch(Exception e){
					AppClientFactory.printSevereLogger("EditResourcePopupVc interactiveClickHandler::"+e);
				}
			}
			if(imageClickHandler!=null){
				try{
					imageClickHandler.removeHandler();
				}catch(Exception e){
					AppClientFactory.printSevereLogger("EditResourcePopupVc imageClickHandler::"+e);
				}
			}
			if(textClickHandler!=null){
				try{
					textClickHandler.removeHandler();
				}catch(Exception e){
					AppClientFactory.printSevereLogger("EditResourcePopupVc textClickHandler:::"+e);
				}
			}
			if(audioClickHandler!=null){
				try{
					audioClickHandler.removeHandler();
				}catch(Exception e){
					AppClientFactory.printSevereLogger("EditResourcePopupVc audioClickHandler::"+e);
				}
			}
		}else{
			websiteClickHandler=websiteResourcePanel.addClickHandler(new checkAvailableClickHandler());
			interactiveClickHandler = interactiveResourcePanel.addClickHandler(new checkAvailableClickHandler());
			imageClickHandler=imageResourcePanel.addClickHandler(new checkAvailableClickHandler());
			textClickHandler = textResourcePanel.addClickHandler(new checkAvailableClickHandler());
			audioClickHandler = audioResourcePanel.addClickHandler(new checkAvailableClickHandler());
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
		String category = collectionItemDo.getResource().getResourceFormat().getValue();

		if (category.equalsIgnoreCase("Video")||category.equalsIgnoreCase("Videos")) {
			resourceCategoryLabel.setText(i18n.GL0918());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL0918());
			resourceCategoryLabel.getElement().setAttribute("title", i18n.GL0918());
			categorypanel.setStyleName(video.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
			videoResourcePanel.addStyleName("active");
		} else if (category.equalsIgnoreCase("Interactive")) {
			resourceCategoryLabel.setText(i18n.GL0919());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL0919());
			resourceCategoryLabel.getElement().setAttribute("title",i18n.GL0919());
			categorypanel.setStyleName(interactive.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
			interactiveResourcePanel.addStyleName("active");
		} else if (category.equalsIgnoreCase("Website")||category.equalsIgnoreCase("websites")||category.equalsIgnoreCase("webpage")) {
			resourceCategoryLabel.setText(i18n.GL1396());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1396());
			resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1396());
			categorypanel.setStyleName(website.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
			websiteResourcePanel.addStyleName("active");
		}
		else if(category.equalsIgnoreCase("audio")) {
			resourceCategoryLabel.setText(i18n.GL1045());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1045());
			resourceCategoryLabel.getElement().setAttribute("title",i18n.GL1045());
			categorypanel.setStyleName(audio.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
			audioResourcePanel.addStyleName("active");
		}
		else if(category.equalsIgnoreCase("texts")||category.equalsIgnoreCase("text")) {
			resourceCategoryLabel.setText(i18n.GL1044());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1044());
			resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1044());
			categorypanel.setStyleName(texts.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
			textResourcePanel.addStyleName("active");
		}
		else if (category.equalsIgnoreCase("image")) {
			resourceCategoryLabel.setText(i18n.GL1046());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1046());
			resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1046());
			categorypanel.setStyleName(image.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
			imageResourcePanel.addStyleName("active");
		}
		else if (category.equalsIgnoreCase("Slide")) {
			resourceCategoryLabel.setText(i18n.GL1046());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1046());
			resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1046());
			categorypanel.setStyleName(image.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
		} else if (category.equalsIgnoreCase("Handout")) {
			resourceCategoryLabel.setText(i18n.GL1044());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1044());
			resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1044());
			categorypanel.setStyleName(texts.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
		} else if (category.equalsIgnoreCase("Textbook")) {
			resourceCategoryLabel.setText(i18n.GL1044());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1044());
			resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1044());
			categorypanel.setStyleName(texts.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
		} else if (category.equalsIgnoreCase("Lesson")) {
			resourceCategoryLabel.setText(i18n.GL1044());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1044());
			resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1044());
			categorypanel.setStyleName(texts.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
		} else if (category.equalsIgnoreCase("Exam")) {
			resourceCategoryLabel.setText(i18n.GL1396());
			resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1396());
			resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1396());
			categorypanel.setStyleName(website.getStyleName());
			resourceTypePanel.setVisible(true);
			resoureDropDownLblOpen=false;
		}
		thumbnailUrlStr = collectionItemDo.getThumbnails() != null ? collectionItemDo.getThumbnails().getUrl() : null;
		setImage(thumbnailUrlStr, category);
		if( collectionItemDo.getEducationalUse()!=null){
			for (checkboxSelectedDo item : collectionItemDo.getEducationalUse()) {
				resourceEducationalLabel.setText(item.getName());
				resourceEducationalLabel.getElement().setId(item.getId()+"");
				educationalUsePanel.setVisible(false);
				educationalDropDownLblOpen = false;
				mandatoryEducationalLbl.setVisible(false);
				setAdvancedOptionsStyles();

			}
		}
		if(collectionItemDo.getMomentsOfLearning()!=null){
			for (checkboxSelectedDo item : collectionItemDo.getMomentsOfLearning()) {
				resourcemomentsOfLearningLabel.setText(item.getName());
				resourcemomentsOfLearningLabel.getElement().setId(item.getId()+"");
				resourcemomentsOfLearningLabel.getElement().setAttribute("alt", item.getName());
				resourcemomentsOfLearningLabel.getElement().setAttribute("title", item.getName());
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
		}
		if(collectionItemDo.getSkills()!= null && collectionItemDo.getSkills().size()>0){
			centuryPanel.clear();
			for (StandardFo standardObj : collectionItemDo.getSkills()) {
				CodeDo codeObj=new CodeDo();
				codeObj.setCodeId(standardObj.getId());
				codeObj.setCode(standardObj.getLabel());
				standardsDo.add(codeObj);
				centurySelectedValues.put(Long.parseLong(standardObj.getId()+""), standardObj.getLabel());
				DownToolTipWidgetUc downToolTipWidgetUc=create21CenturyLabel(standardObj.getLabel(),standardObj.getId()+"","");
				downToolTipWidgetUc.getElement().setId(standardObj.getId()+"");
				downToolTipWidgetUc.getElement().setTitle(standardObj.getLabel());

				centuryPanel.add(downToolTipWidgetUc);
			}
			updateCenturyAdvancedSetupStyle();
		}

		if(collectionItemDo.getMediaType()!=null)
		{
			setMobileFriendlyObjectVal(collectionItemDo.getMediaType());
		}
	}

	public void setImage(String thumbnailUrlImage, String category){
		if (thumbnailUrlImage==null || (thumbnailUrlImage.endsWith("null") || thumbnailUrlImage.contains("images/defaultRes.png"))) {
			thumbnailUrlImage = DEFULT_IMAGE_PREFIX + category.toLowerCase() + PNG;
		}
		if (thumbnailUrlImage!=null && thumbnailUrlImage.indexOf("youtube") >0){
			String urlStr = urlTextLbl.getText().trim();
			String youTubeIbStr = ResourceImageUtil.getYoutubeVideoId(urlStr);
			thumbnailUrlImage = "http://img.youtube.com/vi/"+youTubeIbStr+"/1.jpg";
		}
		setThumbnailImage.setUrl(thumbnailUrlImage);
	}
	public void updateUi() {
		generateImageLbl.setVisible(false);
		setThumbnailImage.setVisible(true);
		if (urlTextLbl.getText().indexOf("youtube") != -1) {
			rightArrowLbl.setVisible(false);
		}
	}

	private class AddClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			final Map<String, String> parms = new HashMap<String, String>();
			parms.put("text", titleTextBox.getValue());
			AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
				@Override
				public void onSuccess(Boolean value) {
					isHavingBadWordsInTextbox = value;
					getSelectedStandards();
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
										String titleStr = titleTextBox.getText().trim();
										String categoryStr =resourceCategoryLabel.getText();// resourceTypeListBox.getItemText(resourceTypeListBox.getSelectedIndex());

										String urlStr = urlTextLbl.getText().trim();
										if (titleStr.toLowerCase().contains("http://") || titleStr.toLowerCase().contains("https://") || titleStr.toLowerCase().contains("ftp://")){
											showTitleErrorMessage(i18n.GL0323());
											isValidate = false;
										}

										if(!rightsChkBox.getValue()){
											rightsLbl.getElement().getStyle().setColor("orange");
											isValidate = false;
										}
										if (titleStr == null || titleStr.equalsIgnoreCase("")) {
											showTitleErrorMessage(i18n.GL0173());
											isValidate = false;
										}
										if (categoryStr == null
												|| categoryStr.equalsIgnoreCase("-1")
												|| categoryStr
												.equalsIgnoreCase("Choose a resource format")) {
											showCategoryErrorMessage(i18n.GL0917());

											isValidate = false;
										}

										if(urlStr.indexOf("youtube")!=-1){
											String youTubeId = getYoutubeVideoId(urlStr);
											if(youTubeId==null || youTubeId.equalsIgnoreCase("null") || youTubeId.equalsIgnoreCase("")){
												if(!categoryStr.equalsIgnoreCase("Webpage")){
													isValidate = true;
												}else{
													showCategoryErrorMessage(i18n.GL0927());
													isValidate = false;													}
											}

										}
										if(mobileYes.getStyleName().contains("at-OffButtonsActive"))
										{

											collectionItemDo.setMediaType("mobile_friendly");

										}
										else if(mobileNo.getStyleName().contains("at-OffButtonsActive"))
										{

											collectionItemDo.setMediaType("not_mobile_friendly");

										}
										if(!lblMediaPlaceHolder.getText().equalsIgnoreCase("Choose a Media Feature Option:"))
										{	List<ListValuesDo> listValuesDos=new ArrayList<>();
										ListValuesDo listValuesDo=new ListValuesDo();
										String id=lblMediaPlaceHolder.getElement().getId();
										if(id!=null&&!id.equalsIgnoreCase("")){
											listValuesDo.setId(Integer.parseInt(id));
										}
										listValuesDo.setName(lblMediaPlaceHolder.getText());
										listValuesDos.add(listValuesDo);
										collectionItemDo.setMediaFeature(listValuesDos);
										}

										collectionItemDo.setAccessHazard(setAccessHazards());

										if(resourceEducationalLabel.getText()!=null ||!resourceEducationalLabel.getText().trim().equalsIgnoreCase(""))
										{
											ArrayList<checkboxSelectedDo> checkboxSelectedDos=new ArrayList<>();

											if(!resourceEducationalLabel.getText().trim().equalsIgnoreCase(DEFAULT_COMBO_BOX_TEXT)){
												checkboxSelectedDo checkboxSelectedDo=new checkboxSelectedDo();
												String id=resourceEducationalLabel.getElement().getId();
												if(id!=null&&!id.equalsIgnoreCase("")){
													checkboxSelectedDo.setId(Integer.parseInt(id));
													checkboxSelectedDo.setName(resourceEducationalLabel.getText());
													checkboxSelectedDos.add(checkboxSelectedDo);
												}
												collectionItemDo.setEducationalUse(checkboxSelectedDos);
												//tagList.add("Educational Use : "+resourceEducationalLabel.getText());
											}
										}
										if(resourcemomentsOfLearningLabel.getText()!=null ||!resourcemomentsOfLearningLabel.getText().trim().equalsIgnoreCase(""))
										{
											ArrayList<checkboxSelectedDo> checkboxSelectedDos=new ArrayList<>();

											if(!resourcemomentsOfLearningLabel.getText().trim().equalsIgnoreCase(DEFAULT_COMBO_BOX_TEXT)){
												checkboxSelectedDo checkboxSelectedDo=new checkboxSelectedDo();
												String id=resourcemomentsOfLearningLabel.getElement().getId();
												if(id!=null&&!id.equalsIgnoreCase("")){
													checkboxSelectedDo.setId(Integer.parseInt(id));
													checkboxSelectedDo.setName(resourcemomentsOfLearningLabel.getText());
													checkboxSelectedDos.add(checkboxSelectedDo);
												}
												collectionItemDo.setMomentsOfLearning(checkboxSelectedDos);
												//tagList.add("Educational Use : "+resourceEducationalLabel.getText());
											}
										}
										if (isValidate) {
											saveButtonContainer.setVisible(false);
											loadingTextLbl.setVisible(true);
											collectionItemDo.getResource().setTitle(titleStr);
											collectionItemDo.getResource().setDescription(descriptionTxtAera.getText().trim());
											if(categoryStr.contains("Videos")||categoryStr.contains("Interactives")||categoryStr.contains("Images")||categoryStr.contains("Texts"))
											{
												categoryStr=categoryStr.substring(0, categoryStr.length()-1);
											}
											collectionItemDo.getResource().setCategory(categoryStr);

											if (thumbnailUrlStr!=null){

												if(collectionItemDo.getResource().getThumbnails()==null){
													ThumbnailDo thumbnailObj=new ThumbnailDo();
													collectionItemDo.getResource().setThumbnails(thumbnailObj);
												}

												collectionItemDo.getResource().getThumbnails().setUrl(thumbnailUrlStr);
											}else{
												if(collectionItemDo.getResource().getThumbnails()!=null)
													collectionItemDo.getResource().getThumbnails().setUrl(null);
											}
											if(!resourceEducationalLabel.getText().equalsIgnoreCase(i18n.GL1684())){
												ArrayList<checkboxSelectedDo> arrayOfEducational=new ArrayList<checkboxSelectedDo>();
												checkboxSelectedDo educationalOfObj=new checkboxSelectedDo();
												educationalOfObj.setSelected(true);
												educationalOfObj.setValue(resourceEducationalLabel.getText());
												arrayOfEducational.add(educationalOfObj);
												if(collectionOriginalItemDo.getResource().getEducationalUse() != null)
												{
													for(int eduI=0; eduI<collectionOriginalItemDo.getResource().getEducationalUse().size(); eduI++)
													{
														if(!resourceEducationalLabel.getText().equalsIgnoreCase(collectionOriginalItemDo.getResource().getEducationalUse().get(eduI).getValue()))
														{
															checkboxSelectedDo eduUseObjPrevious=new checkboxSelectedDo();
															eduUseObjPrevious.setSelected(false);
															eduUseObjPrevious.setValue(collectionOriginalItemDo.getResource().getEducationalUse().get(eduI).getValue());
															arrayOfEducational.add(eduUseObjPrevious);
														}
													}
												}
												collectionItemDo.getResource().setEducationalUse(arrayOfEducational);
											}else{
												ArrayList<checkboxSelectedDo> arrayOfEducational=new ArrayList<checkboxSelectedDo>();
												checkboxSelectedDo educationalOfObj=new checkboxSelectedDo();
												if(resourceEducationalLabel.getText().equalsIgnoreCase(i18n.GL1684())){
													if(collectionOriginalItemDo.getResource().getEducationalUse() != null){
														for(int eduI=0; eduI<collectionOriginalItemDo.getResource().getEducationalUse().size(); eduI++)
														{
															if(!resourceEducationalLabel.getText().equalsIgnoreCase(collectionOriginalItemDo.getResource().getEducationalUse().get(eduI).getValue()))
															{
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

												if(collectionOriginalItemDo.getResource().getMomentsOfLearning() != null)
												{
													for(int momentsI=0; momentsI<collectionOriginalItemDo.getResource().getMomentsOfLearning().size(); momentsI++)
													{
														if(!resourcemomentsOfLearningLabel.getText().equalsIgnoreCase(collectionOriginalItemDo.getResource().getMomentsOfLearning().get(momentsI).getValue()))
														{
															checkboxSelectedDo momentsOfObjPrevious=new checkboxSelectedDo();
															momentsOfObjPrevious.setSelected(false);
															momentsOfObjPrevious.setValue(collectionOriginalItemDo.getResource().getMomentsOfLearning().get(momentsI).getValue());
															arrayOfMoments.add(momentsOfObjPrevious);
														}
													}
												}
												collectionItemDo.getResource().setMomentsOfLearning(arrayOfMoments);
											}else{
												ArrayList<checkboxSelectedDo> arrayOfMoments=new ArrayList<checkboxSelectedDo>();
												checkboxSelectedDo momentsOfObj=new checkboxSelectedDo();
												if(resourcemomentsOfLearningLabel.getText().equalsIgnoreCase(i18n.GL1684())){
													if(collectionOriginalItemDo.getResource().getMomentsOfLearning() != null)
													{	for(int momentsI=0; momentsI<collectionOriginalItemDo.getResource().getMomentsOfLearning().size(); momentsI++)
														{
															if(!resourcemomentsOfLearningLabel.getText().equalsIgnoreCase(collectionOriginalItemDo.getResource().getMomentsOfLearning().get(momentsI).getValue()))
															{
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
											collectionItemDo.setSkills(getCenturySkills());
											if(tagListGlobal!=null&&tagListGlobal.size()!=0){
												AppClientFactory.getInjector().getResourceService().deleteTagsServiceRequest(collectionItemDo.getResource().getGooruOid(), tagListGlobal.toString(), new AsyncCallback<Void>() {

													@Override
													public void onSuccess(Void result) {
														// TODO Auto-generated method stub
														updateResource(collectionItemDo,tagList);
													}

													@Override
													public void onFailure(Throwable caught) {
														// TODO Auto-generated method stub

													}
												});

											}
											else{
												updateResource(collectionItemDo,tagList);
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

	void showTitleErrorMessage(String message){
		mandatoryTitleLbl.setText(message);
		StringUtil.setAttributes(mandatoryTitleLbl.getElement(), "lblMandatoryTitleLbl", message, message);
		mandatoryTitleLbl.setVisible(true);

		titleTextBox.getElement().addClassName("errorBorderMessage");
	}

	void clearTitleErrorMessage(){

		titleTextBox.getElement().removeClassName("errorBorderMessage");
		mandatoryTitleLbl.setVisible(false);
	}

	void showCategoryErrorMessage(String message){
		mandatoryCategoryLbl.setText(message);
		StringUtil.setAttributes(mandatoryCategoryLbl.getElement(), "lblMandatoryCategoryLbl", message, message);

		mandatoryCategoryLbl.setVisible(true);

		panelCategory.getElement().addClassName("errorBorderMessage");
	}

	void clearCategoryErrorMessage(){

		panelCategory.getElement().removeClassName("errorBorderMessage");
		mandatoryCategoryLbl.setVisible(false);
	}

	private class OnEditImageClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			resourceImageUpload();
		}
	}

	public abstract void resourceImageUpload();

	private class TitleKeyUpHandler implements KeyUpHandler {

		public void onKeyUp(KeyUpEvent event) {
			clearTitleErrorMessage();
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
				descriptionTxtAera.getElement().setAttribute("title", descriptionTxtAera.getText());
				descCharcterLimit.setVisible(true);
			}
		}
	}

	@UiHandler("leftArrowLbl")
	void leftArrowClick(ClickEvent event) {
		activeImageIndex--;
		setImageThumbnail();
	}

	@UiHandler("rightArrowLbl")
	void rightArrowClick(ClickEvent event) {
		activeImageIndex++;
		setImageThumbnail();
	}
	void setVideoCategory(){
		resourceCategoryLabel.setText(i18n.GL0918());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL0918());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL0918());
		categorypanel.setStyleName(video.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen=false;
		videoResourcePanel.addStyleName("active");
		websiteResourcePanel.removeStyleName("active");
		interactiveResourcePanel.removeStyleName("active");
		audioResourcePanel.removeStyleName("active");
		textResourcePanel.removeStyleName("active");
		imageResourcePanel.removeStyleName("active");
	}

	void setInteractiveCategory(){
		resourceCategoryLabel.setText(i18n.GL0919());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL0919());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL0919());
		categorypanel.setStyleName(interactive.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen=false;
		interactiveResourcePanel.addStyleName("active");
		websiteResourcePanel.removeStyleName("active");
		audioResourcePanel.removeStyleName("active");
		textResourcePanel.removeStyleName("active");
		videoResourcePanel.removeStyleName("active");
		imageResourcePanel.removeStyleName("active");
	}

	void setWebsiteCategory(){
		resourceCategoryLabel.setText(i18n.GL1396());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1396());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1396());
		categorypanel.setStyleName(website.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen=false;
		websiteResourcePanel.addStyleName("active");
		interactiveResourcePanel.removeStyleName("active");
		audioResourcePanel.removeStyleName("active");
		textResourcePanel.removeStyleName("active");
		videoResourcePanel.removeStyleName("active");
		imageResourcePanel.removeStyleName("active");
	}

	void setImageCategory() {
		resourceCategoryLabel.setText(i18n.GL1046());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1046());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1046());
		categorypanel.setStyleName(image.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen = false;
		mandatoryCategoryLbl.setVisible(false);
		imageResourcePanel.addStyleName("active");
		websiteResourcePanel.removeStyleName("active");
		interactiveResourcePanel.removeStyleName("active");
		audioResourcePanel.removeStyleName("active");
		textResourcePanel.removeStyleName("active");
		videoResourcePanel.removeStyleName("active");
		clearCategoryErrorMessage();

	}

	void setTextCategory() {
		resourceCategoryLabel.setText(i18n.GL1044());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1044());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1044());
		categorypanel.setStyleName(texts.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen = false;
		mandatoryCategoryLbl.setVisible(false);
		textResourcePanel.addStyleName("active");
		websiteResourcePanel.removeStyleName("active");
		interactiveResourcePanel.removeStyleName("active");
		audioResourcePanel.removeStyleName("active");
		videoResourcePanel.removeStyleName("active");
		imageResourcePanel.removeStyleName("active");
		clearCategoryErrorMessage();
	}

	void setAudioCategory() {
		resourceCategoryLabel.setText(i18n.GL1045());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1045());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1045());
		categorypanel.setStyleName(audio.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen = false;
		mandatoryCategoryLbl.setVisible(false);
		audioResourcePanel.addStyleName("active");
		websiteResourcePanel.removeStyleName("active");
		interactiveResourcePanel.removeStyleName("active");
		textResourcePanel.removeStyleName("active");
		videoResourcePanel.removeStyleName("active");
		imageResourcePanel.removeStyleName("active");
		clearCategoryErrorMessage();
	}

	public void setImageThumbnail() {

		if (activeImageIndex == 0) {
			leftArrowLbl.setVisible(false);
		} else {
			leftArrowLbl.setVisible(true);
		}
		if (thumbnailImagesLink != null) {

			if (activeImageIndex == thumbnailImagesLink.size()) {
				rightArrowLbl.setVisible(false);
			} else {
				rightArrowLbl.setVisible(true);
			}
			setThumbnailImage.setUrl(thumbnailImagesLink.get(activeImageIndex));
			thumbnailUrlStr = thumbnailImagesLink.get(activeImageIndex);
		}
	}

	@UiHandler("refreshLbl")
	void refreshClick(ClickEvent event) {
		String thumbnailUrlStr = collectionItemDo.getResource().getThumbnails() != null ? collectionItemDo.getResource().getThumbnails().getUrl() : null;
		setImage(thumbnailUrlStr, collectionItemDo.getResource().getResourceFormat().getDisplayName());

		leftArrowLbl.setVisible(false);
		if (urlTextLbl.getText().contains("youtube")) {
			rightArrowLbl.setVisible(false);
		}
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
		rightArrowLbl.setVisible(false);
	}


	public String getYoutubeVideoId(String youtubeUrl) {

		youtubeUrl=youtubeUrl.replaceAll("feature=player_detailpage&", "");
		youtubeUrl=youtubeUrl.replaceAll("feature=player_embedded&","");


		String pattern = "^.*((youtu.be"+ "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
		String videoId = null;
		try {
			RegExp reg = RegExp.compile(pattern, "gi");
			MatchResult res = reg.exec(youtubeUrl);
			videoId = res.getGroup(7);
		} catch (Exception e) {
			AppClientFactory.printSevereLogger("EditResourcePopupVc getYoutubeVideoId:::"+e);
		}
		return videoId;

	}
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
			AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

				@Override
				public void onSuccess(Boolean value) {
					addResourceBtn.setEnabled(true);
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
			DownToolTipWidgetUc downToolTipWidgetUc=create21CenturyLabel(centuryTag, id, centuryCodesMap.get(id));
			downToolTipWidgetUc.getElement().setId(id);
			downToolTipWidgetUc.getElement().setTitle(centuryTag);
			centuryPanel.add(downToolTipWidgetUc);
		}
	}
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

			}
		};
		return new DownToolTipWidgetUc(closeLabel, description);
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
	public void setMobileFriendlyObjectVal(String mobileFriendlyVal)
	{
		if(mobileFriendlyVal.equalsIgnoreCase(i18n.GL_GRR_MOBILE_FRIENDLY()))
		{
			mobileYes.getElement().setClassName("at-OffButtonsActive");
			mobileNo.getElement().setClassName("at-OnButtonDeActive");
		}
		else if(mobileFriendlyVal.equalsIgnoreCase(i18n.GL_GRR_NOT_MOBILE_FRIENDLY()))
		{
			mobileNo.getElement().setClassName("at-OffButtonsActive");
			mobileYes.getElement().setClassName("at-OnButtonDeActive");
		}
		updateMobileFriendlyAdvancedStyles();
	}
	@UiHandler("mobileYes")
	public void onmobileYesClick(ClickEvent click)
	{
		mobileYes.getElement().setClassName("at-OffButtonsActive");
		mobileNo.getElement().setClassName("at-OnButtonDeActive");
		updateMobileFriendlyAdvancedStyles();
	}

	@UiHandler("mobileNo")
	public void onmobileNoClick(ClickEvent click)
	{

		mobileNo.getElement().setClassName("at-OffButtonsActive");
		mobileYes.getElement().setClassName("at-OnButtonDeActive");
		updateMobileFriendlyAdvancedStyles();
	}
	public List<checkboxSelectedDo> setAccessHazards()
	{
		int size=hazardContainer.getWidgetCount();
		List<checkboxSelectedDo> accessHazardsSelected = new ArrayList<checkboxSelectedDo>();

		for(int i=0;i<size;i++){
			Label label=(Label)hazardContainer.getWidget(i);
			if(label.getStyleName().contains("select")){
				String id=label.getElement().getId();
				if(id!=null&&!id.equalsIgnoreCase("")){
					int idInt=Integer.parseInt(id);
					checkboxSelectedDo checkboxSelectedDo=new checkboxSelectedDo();
					checkboxSelectedDo.setId(idInt);
					checkboxSelectedDo.setName(label.getText());
					accessHazardsSelected.add(checkboxSelectedDo);
				}
			}

		}
		setAdvancedAccessHazardStyles(accessHazardsSelected.size());
		return accessHazardsSelected;
	}

	public void setMediaFeatureObjectVal(List<ListValuesDo> mediaFeatureVal)
	{

		try
		{
			/*if(mediaFeatureVal != null)
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

		}*/
			for(ListValuesDo listValuesDo:mediaFeatureVal){
				mobileFeature =listValuesDo.getName();
				lblMediaPlaceHolder.setText(listValuesDo.getName());
				spanelMediaFeaturePanel.setVisible(false);
				lblMediaPlaceHolder.getElement().setId(listValuesDo.getId()+"");
				lblMediaPlaceHolder.setStyleName(CollectionAssignCBundle.INSTANCE.css().selectedClasspageText());
				setAdvancedOptionsStyles();
			}
		}
		catch(Exception ex)
		{
			AppClientFactory.printSevereLogger(ex.getMessage());
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
	 *
	 * @function setAdvancedOptionsStyles
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description  This method is used to set styles for educationaluse,momentsoflearning and mediafeature based on dropdown selection.
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
	 *
	 * @function setAdvancedAccessHazardStyles
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description  This method is used to set styles for accesshazard on click of perticular panel.
	 *
	 *
	 * @parm(s) : @param length
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
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
	 * @function updateCenturyAdvancedSetupStyle
	 * @created_date : 15-Dec-2014
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

	/**
	 *
	 * @function updateMobileFriendlyAdvancedStyles
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description This method is used to set styles for MobileFriendly tags based on the user selection(Yes/No).
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
	public void updateMobileFriendlyAdvancedStyles(){

		if(mobileYes.getStyleName().equalsIgnoreCase("at-OffButtonsActive"))
		{
			addSetupAdvancedView.mobileFreindlyAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.mobileFreindlyAdvancedContainer.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
		else if(mobileNo.getStyleName().equalsIgnoreCase("at-OffButtonsActive"))
		{
			addSetupAdvancedView.mobileFreindlyAdvancedContainer.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.mobileFreindlyAdvancedContainer.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
	}

	private class  checkAvailableClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			if(event.getSource() == websiteResourcePanel){
				setWebsiteCategory();
			}else if(event.getSource() == videoResourcePanel){
				setVideoCategory();
			}else if(event.getSource() == interactiveResourcePanel){
				setInteractiveCategory();
			}else if(event.getSource() == imageResourcePanel){
				setImageCategory();
			}else if(event.getSource() == textResourcePanel){
				setTextCategory();
			}else if(event.getSource() == audioResourcePanel){
				setAudioCategory();
			}
		}
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

	public void setEducationUse(){
		AppClientFactory.getInjector().getResourceService().getEducationalUseList(new AsyncCallback<List<ListValuesDo>>() {

			@Override
			public void onSuccess(List<ListValuesDo> result) {
				// TODO Auto-generated method stub
				setData(result,"education");
				displayResourceInfo();
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}


	public void setData(List<ListValuesDo> listValuesDos,String type){

		if(type.equalsIgnoreCase("education")){
			educationalUsePanel.clear();
		}else if(type.equalsIgnoreCase("mLearning")){
			momentsOfLearningPanel.clear();
		}
		for(ListValuesDo listValuesDo:listValuesDos){
			LiPanel liPanel=new LiPanel();
			Anchor anchor=new Anchor();
			anchor.setStyleName("educationUseText");
			anchor.setText(listValuesDo.getName());
			anchor.getElement().setId(listValuesDo.getId()+"");
			liPanel.add(anchor);
			if(type.equalsIgnoreCase("education")){

				educationalUsePanel.add(liPanel);
				liPanel.addDomHandler(new EducationClickHandler(liPanel, anchor), ClickEvent.getType());
			}else if(type.equalsIgnoreCase("mLearning")){
				liPanel.addDomHandler(new MomentOfLearingClickHandler(liPanel, anchor), ClickEvent.getType());
				momentsOfLearningPanel.add(liPanel);
			}

		}
	}
	public void setMomentOfLeaning(){
		AppClientFactory.getInjector().getResourceService().getMomentOfLearning(new AsyncCallback<List<ListValuesDo>>() {

			@Override
			public void onSuccess(List<ListValuesDo> result) {
				// TODO Auto-generated method stub
				setData(result, "mLearning");
				displayResourceInfo();
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}


	public class EducationClickHandler implements ClickHandler{
		Anchor anchor;
		LiPanel liPanel;
		public EducationClickHandler(LiPanel liPanel,Anchor anchor) {
			// TODO Auto-generated constructor stub
			this.anchor=anchor;
			this.liPanel=liPanel;
		}
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			//resetSelection(educationalUseOptionsContainer);
			liPanel.setStyleName("active");
			resourceEducationalLabel.setText(anchor.getText());
			resourceEducationalLabel.getElement().setId(anchor.getElement().getId());
			resourceEducationalLabel.getElement().setAttribute("alt", anchor.getText());
			resourceEducationalLabel.getElement().setAttribute("title", anchor.getText());
			educationalUsePanel.setVisible(false);
			educationalDropDownLblOpen = false;
			mandatoryEducationalLbl.setVisible(false);
			setAdvancedOptionsStyles();
		}

	}
	public class MomentOfLearingClickHandler implements ClickHandler{
		Anchor anchor;
		LiPanel liPanel;
		public MomentOfLearingClickHandler(LiPanel liPanel,Anchor anchor) {
			// TODO Auto-generated constructor stub
			this.anchor=anchor;
			this.liPanel=liPanel;
		}
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			resetSelection(momentsOfLearningPanel);
			liPanel.setStyleName("active");
			resourcemomentsOfLearningLabel.setText(anchor.getText());
			resourcemomentsOfLearningLabel.getElement().setId(anchor.getElement().getId());
			resourcemomentsOfLearningLabel.getElement().setAttribute("alt", anchor.getText());
			resourcemomentsOfLearningLabel.getElement().setAttribute("title", anchor.getText());
			momentsOfLearningPanel.setVisible(false);
			momentsOfLearningOpen = false;
			mandatorymomentsOfLearninglLbl.setVisible(false);
			setAdvancedOptionsStyles();

		}
	}

	public void resetSelection(UlPanel ulPanel){
		int count=ulPanel.getWidgetCount();
		for(int i=0;i<count;i++){
			LiPanel liPanel=(LiPanel)ulPanel.getWidget(i);
			liPanel.removeStyleName("active");
		}
	}

	public void getMediaFeatures(){
		AppClientFactory.getInjector().getResourceService().getMediaFeature(new AsyncCallback<List<ListValuesDo>>() {

			@Override
			public void onSuccess(List<ListValuesDo> result) {
				// TODO Auto-generated method stub

				for(ListValuesDo listValuesDo:result){
					String mediaTitleVal = listValuesDo.getName();
					final Label titleLabel = new Label(mediaTitleVal);
					titleLabel.setStyleName(CollectionAssignCBundle.INSTANCE.css().classpageTitleText());
					titleLabel.getElement().setAttribute("id", listValuesDo.getId()+"");
					titleLabel.setText(listValuesDo.getName());
					//Set Click event for title
					titleLabel.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							String optionSelected = titleLabel.getText();
							lblMediaPlaceHolder.setText(optionSelected);
							spanelMediaFeaturePanel.setVisible(false);
							lblMediaPlaceHolder.getElement().setId(titleLabel.getElement().getId());
							lblMediaPlaceHolder.setStyleName(CollectionAssignCBundle.INSTANCE.css().selectedClasspageText());
							lblMediaPlaceHolder.setText(optionSelected);
							setAdvancedOptionsStyles();
						}
					});
					htmlMediaFeatureListContainer.add(titleLabel);
					if(collectionItemDo!=null&&collectionItemDo.getMediaFeature()!=null){
						setMediaFeatureObjectVal(collectionItemDo.getMediaFeature());
					}
				}
			}
			@Override
			public void onFailure(Throwable caught) {
			}
		});
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
						if(label.getStyleName().toString().contains("select")){
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

	public List<StandardFo> getCenturySkills(){
		List<StandardFo> standardsList=new ArrayList<>();
		int size=centuryPanel.getWidgetCount();
		for(int i=0;i<size;i++){
			StandardFo checkboxSelectedDo=new StandardFo();
			DownToolTipWidgetUc downToolTipWidgetUc=(DownToolTipWidgetUc)centuryPanel.getWidget(i);
			checkboxSelectedDo.setId(Integer.parseInt(downToolTipWidgetUc.getElement().getId()));
			checkboxSelectedDo.setLabel(downToolTipWidgetUc.getElement().getTitle());
			standardsList.add(checkboxSelectedDo);
		}
		return standardsList;
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