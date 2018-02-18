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

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.drive.GoogleDriveItemDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.faq.CopyRightPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsAndPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.search.CenturySkills.AddCenturyPresenter;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.assign.CollectionAssignCBundle;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.DriveView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.AddSetupAdvancedCBundle;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.CollectionEditResourceCBundle;
import org.ednovo.gooru.client.uc.AppMultiWordSuggestOracle;
import org.ednovo.gooru.client.uc.AppSuggestBox;
import org.ednovo.gooru.client.uc.BlueButtonUc;
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

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
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
import com.google.gwt.user.client.ui.Composite;
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

public abstract class AddWebResourceView extends Composite implements SelectionHandler<SuggestOracle.Suggestion> {

	public interface AddWebResourceViewUiBinder extends UiBinder<Widget, AddWebResourceView> {
	}

	public static AddWebResourceViewUiBinder uiBinder = GWT.create(AddWebResourceViewUiBinder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	public Label standardsDefaultText, centuryDefaultText, mandatoryEducationalLbl, mandatorymomentsOfLearninglLbl,
			driveFileInfoLbl, mandatorygenerateFromUrlLbl;

	@UiField
	public BlueButtonUc addResourceBtnLbl;

	@UiField
	Label standardMaxMsg, centuryMaxMsg, mandatoryUrlLbl, mandatoryTitleLbl, rightsLbl;

	@UiField
	Label mandatoryCategoryLbl;
	@UiField UlPanel ulSelectedItems;

	@UiField
	UlPanel educationalUseOptionsContainer, momentsOfLearningPanel;

	@UiField
	HTMLEventPanel eHearderIconCentury, refreshLbl, lblContentRights, videoResourcePanel, websiteResourcePanel,
			interactiveResourcePanel, imageResourcePanel, textResourcePanel, audioResourcePanel, advancedSetupContainer,
			eHearderIconEducationalUse, eHearderIconMomentsOfLearning, eHearderIconstandards, eHearderIconAccessHazard,
			eHearderIconMediafeature, eHearderIconMobileFriendly, educatioNalUseDropContainer,
			momentsOfLearningDropDownContianer;

	@UiField
	Label leftArrowLbl, rightArrowLbl;

	@UiField
	public TextBox urlTextBox, titleTextBox;

	@UiField
	public TextArea descriptionTxtAera;

	@UiField
	public Image setThumbnailImage, generateImageLbl;
	// Drop down for Resource Type//
	@UiField
	HTMLPanel descCharcterLimit, panelContentRights, titleText, categoryTitle, educationalTitle, momentsOfLearningTitle,
			orText, refreshText, generateFromUrlPanel, momentsOfLearningContainer, accessHazardContainer,
			standardsBrowseContainer, mobileFriendlyContainer, mediaFeatureContainer, mediaDropdownArrowConatainer;

	@UiField
	HTMLPanel panelCategoryInputDiv;

	@UiField
	HTMLEventPanel mediaLabelContainer;

	@UiField
	public HTMLPanel hazardContainer, addResourceBtnPanel, loadingPanel, urlTitle, descriptionLabel, videoLabel,
			interactiveText, websiteText, imagesText, contentPanel, textsText, audioText, urlContianer;// otherText

	@UiField
	HTMLPanel categorypanel, video, interactive, website, thumbnailText, audio, texts, image, rightsContent,
			errorContainer, errorContainerForCentury;// other

	@UiField
	HTMLPanel centuryBrowseContainer, resourceTypePanel, educationalUsePanel, resourceDescriptionContainer,
			buttonsPanel, educationalContainer;

	@UiField
	Label resoureDropDownLbl, resourceCategoryLabel, loadingTextLbl, mandatoryDescLblForSwareWords,
			mandatoryTitleLblForSwareWords;
	@UiField
	Anchor resourceEducationalLabel, resourcemomentsOfLearningLabel;

	@UiField
	CheckBox rightsChkBox;

	@UiField
	InlineLabel agreeText, andText, additionalText, commuGuideLinesAnr, termsAndPolicyAnr, privacyAnr, copyRightAnr,
			moblieFriendly;

	@UiField(provided = true)
	AppSuggestBox standardSgstBox, centurySgstBox;

	@UiField
	FlowPanel centuryContainer, standardContainer, centuryPanel;

	@UiField
	Button cancelResourcePopupBtnLbl, mobileYes, mobileNo, generateFromUrlBtn, uploadImageLbl;

	@UiField
	Label accessHazard;

	@UiField
	Label mediaLabel, lblMediaPlaceHolder, lblMediaFeatureArrow;

	@UiField
	ScrollPanel spanelMediaFeaturePanel, scrollPanel;

	@UiField
	HTMLPanel htmlMediaFeatureListContainer;

	@UiField
	Button browseCentury;

	@UiField
	HTMLEventPanel btnStandardsBrowse;
	@UiField
	UlPanel standardsDropListValues;

	@UiField
	InlineLabel advancedText;

	Integer videoDuration = 0;

	private CopyRightPolicyVc copyRightPolicy;

	private TermsAndPolicyVc termsAndPolicyVc;

	private TermsOfUse termsOfUse;
	public boolean isValidYoutubeUrlFlag = true;

	private boolean hasClickedOnDropDwn = false;

	public boolean resoureDropDownLblOpen = false, educationalDropDownLblOpen = false,
			educationalDropDownLblOpen1 = false, momentsOfLearningOpen = false, momentsOfLearningOpen1 = false;

	private boolean isShortenedUrl;

	boolean isValidate = true;

	private static final String GOOGLE_DRIVE = "Google Drive";

	private AppMultiWordSuggestOracle standardSuggestOracle;

	private AppMultiWordSuggestOracle centurySuggestOracle;

	private SearchDo<CodeDo> standardSearchDo = new SearchDo<CodeDo>();

	private SearchDo<StandardFo> centurySearchDo = new SearchDo<StandardFo>();

	private static final String FLT_CODE_ID = "id";

	List<String> standardPreflist, centuryPreflist;

	private Map<String, String> standardCodesMap = new HashMap<String, String>();
	private Map<String, String> centuryCodesMap = new HashMap<String, String>();

	List<CodeDo> standardsDo = new ArrayList<CodeDo>();

	List<StandardFo> centuryDo = new ArrayList<StandardFo>();

	String courseCode = "";

	int activeImageIndex = 0;

	protected List<String> thumbnailImages;

	String thumbnailUrlStr = null;

	CollectionDo collectionDo;

	public FolderDo courseObjG;

	boolean isHavingBadWordsInTextbox = false, isHavingBadWordsInRichText = false;
	private static final String USER_META_ACTIVE_FLAG = "0";

	private static final String DEFAULT_COMBO_BOX_TEXT = "Please choose one of the following...";

	String mediaFeatureStr = i18n.GL1767();

	final StandardsPreferenceOrganizeToolTip standardsPreferenceOrganizeToolTip = new StandardsPreferenceOrganizeToolTip();

	private boolean isGoogleDriveFile = false;

	private GoogleDriveItemDo googleDriveItemDo = null;

	BrowseStandardsTooltip browseStandardsTooltip;


	private boolean isGenerateURL = false;

	boolean processing = false;

	public AddSetupAdvancedView addSetupAdvancedView;

	HandlerRegistration videoClickHandler = null, websiteClickHandler = null, interactiveClickHandler = null,
			imageClickHandler = null, textClickHandler = null, audioClickHandler = null;

	PopupPanel centuryPopup = new PopupPanel();

	Map<Long, String> centurySelectedValues = new HashMap<Long, String>();

	AddCenturyPresenter centuryPresenterWidget = null;

	private boolean isCCSSAvailable = false;
	private boolean isNGSSAvailable = false;
	private boolean isTEKSAvailable = false;
	private boolean isCAAvailable = false;

	List<Integer> selectedValues=new ArrayList<>();

	List<LiPanelWithClose> collectionLiPanelWithCloseArray = new ArrayList<>();

	String[] standardsTypesArray = new String[]{i18n.GL3379(),i18n.GL3322(),i18n.GL3323(),i18n.GL3324(),i18n.GL3325(),i18n.GL3321()};

	public AddWebResourceView(CollectionDo collectionDo, boolean isGoogleDriveFile,
			GoogleDriveItemDo googleDriveItemDo) {
		AddSetupAdvancedCBundle.INSTANCE.css().ensureInjected();
		CollectionEditResourceCBundle.INSTANCE.css().ensureInjected();
		this.isGoogleDriveFile = isGoogleDriveFile;
		this.googleDriveItemDo = googleDriveItemDo;
		centuryPresenterWidget = AppClientFactory.getInjector().getAddCenturyPresenterWidget();
		standardSuggestOracle = new AppMultiWordSuggestOracle(true);
		centurySuggestOracle = new AppMultiWordSuggestOracle(true);
		standardSearchDo.setPageSize(10);
		setEducationUse();
		setMomentOfLeaning();
		standardSgstBox = new AppSuggestBox(standardSuggestOracle) {
			@SuppressWarnings("deprecation")
			@Override
			public void keyAction(String text, KeyUpEvent event) {
				text = text.toUpperCase();
				errorContainer.setVisible(false);
				standardSearchDo.setSearchResults(null);
				boolean standardsPrefDisplayPopup = false;
				if (!courseCode.isEmpty()) {
					Map<String, String> filters = new HashMap<String, String>();
					filters.put(FLT_CODE_ID, courseCode);
					standardSearchDo.setFilters(filters);
				}
				standardSearchDo.setQuery(text);
				if (text != null && text.trim().length() > 0) {
					errorContainer.setVisible(false);
					if (standardPreflist != null) {
						for (int count = 0; count < standardPreflist.size(); count++) {
							if (text.contains(standardPreflist.get(count))) {
								standardsPrefDisplayPopup = true;
								break;
							} else {
								standardsPrefDisplayPopup = false;
							}
						}
					}

					if (standardsPrefDisplayPopup) {
						errorContainer.setVisible(false);
						AppClientFactory.getInjector().getSearchService().getSuggestStandardByFilterCourseId(
								standardSearchDo, new SimpleAsyncCallback<SearchDo<CodeDo>>() {

							@Override
							public void onSuccess(SearchDo<CodeDo> result) {
								setStandardSuggestions(result);

							}
						});
						standardSgstBox.showSuggestionList();
					}

					else {
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
		BlurHandler blurHandler = new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if (standardsPreferenceOrganizeToolTip.isShowing()) {
					errorContainer.setVisible(false);
				}
			}
		};
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
		centurySgstBox = new AppSuggestBox(centurySuggestOracle) {

			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler) {
				return null;
			}

			@Override
			public void keyAction(String text, KeyUpEvent event) {
				text = text.toUpperCase();
				centurySearchDo.setSearchResults(null);
				centurySearchDo.setQuery(text);
				if (text != null && text.trim().length() > 0) {
					AppClientFactory.getInjector().getSearchService().getSuggestCenturyByQuery(centurySearchDo,
							new AsyncCallback<SearchDo<StandardFo>>() {

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

		BlurHandler blurHandlerCentury = new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if (standardsPreferenceOrganizeToolTip.isShowing()) {
					errorContainer.setVisible(false);
				}
			}
		};

		centurySgstBox.addDomHandler(blurHandlerCentury, BlurEvent.getType());
		centurySgstBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				addCentury(centurySgstBox.getValue(),
						getCodeIdByCodeCentury(centurySgstBox.getValue(), centurySearchDo.getSearchResults()));
				centurySgstBox.setText("");
				centurySuggestOracle.clear();
				updateCenturyAdvancedSetupStyle();

			}
		});

		this.collectionDo = collectionDo;
		initWidget(uiBinder.createAndBindUi(this));

		advancedText.setText(i18n.GL3096());
		mediaLabelContainer.getElement().getStyle().setMarginBottom(10, Unit.PX);
		mediaDropdownArrowConatainer.getElement().getStyle().setRight(10, Unit.PX);
		addSetupAdvancedView = new AddSetupAdvancedView() {
			@Override
			public void showAndHideContainers() {
			}
		};
		setHazard();
		Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				hideDropDown(event);
			}
		});
		advancedSetupContainer.add(addSetupAdvancedView);
		generateFromUrlBtn.setText(i18n.GL3092());
		disableGenerateBtn();
		standardsBrowseContainer.getElement().setId("standardsContainerBswn");
		refreshLbl.setVisible(false);
		resourceEducationalLabel.getElement().setAttribute("data-toggle", "dropdown");
		resourcemomentsOfLearningLabel.getElement().setAttribute("data-toggle", "dropdown");
		errorContainer.setVisible(false);
		errorContainer.add(standardsPreferenceOrganizeToolTip);
		urlTitle.getElement().setInnerHTML(i18n.GL0915());
		urlTitle.getElement().setId("pnlUrlTitle");
		urlTitle.getElement().setAttribute("alt", i18n.GL0915());
		urlTitle.getElement().setAttribute("title", i18n.GL0915());
		titleText.getElement().setInnerHTML(i18n.GL0318() + i18n.GL_SPL_STAR());
		titleText.getElement().setId("pnlTitleText");
		titleText.getElement().setAttribute("alt", i18n.GL0318());
		titleText.getElement().setAttribute("title", i18n.GL0318());
		descriptionLabel.getElement().setInnerHTML(i18n.GL0904());
		descriptionLabel.getElement().setId("pnlDescriptionLabel");
		descriptionLabel.getElement().setAttribute("alt", i18n.GL0904());
		descriptionLabel.getElement().setAttribute("title", i18n.GL0904());
		categoryTitle.getElement().setInnerHTML(i18n.GL3103() + i18n.GL_SPL_STAR());
		categoryTitle.getElement().setId("pnlCategoryTitle");
		categoryTitle.getElement().setAttribute("alt", i18n.GL0906());
		categoryTitle.getElement().setAttribute("title", i18n.GL0906());
		videoLabel.getElement().setInnerHTML(i18n.GL0918());
		videoLabel.getElement().setId("pnlVideoLabel");
		videoLabel.getElement().setAttribute("alt", i18n.GL0918());
		videoLabel.getElement().setAttribute("title", i18n.GL0918());
		interactiveText.getElement().setInnerHTML(i18n.GL0919());
		interactiveText.getElement().setId("pnlInteractiveText");
		interactiveText.getElement().setAttribute("alt", i18n.GL0919());
		interactiveText.getElement().setAttribute("title", i18n.GL0919());
		websiteText.getElement().setInnerHTML(i18n.GL1396());
		websiteText.getElement().setId("pnlWebsiteText");
		websiteText.getElement().setAttribute("alt", i18n.GL1396());
		websiteText.getElement().setAttribute("title", i18n.GL1396());
		educationalTitle.getElement().setInnerHTML(i18n.GL1664());
		educationalTitle.getElement().setId("pnlEducationalTitle");
		educationalTitle.getElement().setAttribute("alt", i18n.GL1664());
		educationalTitle.getElement().setAttribute("title", i18n.GL1664());

		momentsOfLearningTitle.getElement().setInnerHTML(i18n.GL1678());
		momentsOfLearningTitle.getElement().setId("pnlMomentsOfLearningTitle");
		momentsOfLearningTitle.getElement().setAttribute("alt", i18n.GL1678());
		momentsOfLearningTitle.getElement().setAttribute("title", i18n.GL1678());
		standardsDefaultText.setText(i18n.GL1682());
		standardsDefaultText.getElement().setId("lblStandardsDefaultText");
		standardsDefaultText.getElement().setAttribute("alt", i18n.GL1682());
		standardsDefaultText.getElement().setAttribute("title", i18n.GL1682());

		centuryDefaultText.setText(i18n.GL3199());
		centuryDefaultText.getElement().setId("lblCenturyDefaultText");
		centuryDefaultText.getElement().setAttribute("alt", i18n.GL3199());
		centuryDefaultText.getElement().setAttribute("title", i18n.GL3199());

		resourceDescriptionContainer.getElement().setId("pnlResourceDescriptionContainer");
		textsText.getElement().setInnerHTML(i18n.GL1044());
		textsText.getElement().setId("pnlTextsText");
		textsText.getElement().setAttribute("alt", i18n.GL1044());
		textsText.getElement().setAttribute("title", i18n.GL1044());
		audioText.getElement().setInnerHTML(i18n.GL1045());
		audioText.getElement().setId("pnlAudioText");
		audioText.getElement().setAttribute("alt", i18n.GL1045());
		audioText.getElement().setAttribute("title", i18n.GL1045());
		imagesText.getElement().setInnerHTML(i18n.GL1046());
		imagesText.getElement().setId("pnlImagesText");
		imagesText.getElement().setAttribute("alt", i18n.GL1046());
		imagesText.getElement().setAttribute("title", i18n.GL1046());
		contentPanel.getElement().setId("pnlContentPanel");
		urlContianer.getElement().setId("pnlUrlContianer");

		thumbnailText.getElement().setInnerHTML(i18n.GL0911());
		thumbnailText.getElement().setId("pnlThumbnailText");
		thumbnailText.getElement().setAttribute("alt", i18n.GL0911());
		thumbnailText.getElement().setAttribute("title", i18n.GL0911());
		generateImageLbl.setUrl("../images/NewResourcePopup/PreviewResourceThumbnail.png");
		generateImageLbl.getElement().setId("lblGenerateImageLbl");
		generateImageLbl.getElement().setAttribute("alt", i18n.GL0922());
		generateImageLbl.getElement().setAttribute("title", i18n.GL0922());
		orText.getElement().setInnerHTML(i18n.GL_GRR_Hyphen() + i18n.GL0209() + i18n.GL_GRR_Hyphen());
		orText.getElement().setId("pnlOrText");
		orText.getElement().setAttribute("alt", i18n.GL0209());
		orText.getElement().setAttribute("title", i18n.GL0209());
		uploadImageLbl.setText(i18n.GL0912());
		uploadImageLbl.getElement().setAttribute("alt", i18n.GL0912());
		uploadImageLbl.getElement().setAttribute("title", i18n.GL0912());
		getAddStandards();
		refreshText.getElement().setInnerHTML(i18n.GL0923());
		refreshText.getElement().setId("pnlRefreshText");
		refreshText.getElement().setAttribute("alt", i18n.GL0923());
		refreshText.getElement().setAttribute("title", i18n.GL0923());
		rightsLbl.setText(i18n.GL0869());
		rightsLbl.getElement().setId("lblRightsLbl");
		rightsLbl.getElement().setAttribute("alt", i18n.GL0869());
		rightsLbl.getElement().setAttribute("title", i18n.GL0869());
		addResourceBtnLbl.setText(i18n.GL0590());
		addResourceBtnLbl.getElement().setAttribute("alt", i18n.GL0590());
		addResourceBtnLbl.getElement().setAttribute("title", i18n.GL0590());
		cancelResourcePopupBtnLbl.setText(i18n.GL0142());
		cancelResourcePopupBtnLbl.getElement().setAttribute("alt", i18n.GL0142());
		cancelResourcePopupBtnLbl.getElement().setAttribute("title", i18n.GL0142());
		loadingTextLbl.setText(i18n.GL0591().toLowerCase());
		loadingTextLbl.getElement().setId("lblLoadingTextLbl");
		loadingTextLbl.getElement().setAttribute("alt", i18n.GL0591().toLowerCase());
		loadingTextLbl.getElement().setAttribute("title", i18n.GL0591().toLowerCase());
		cancelResourcePopupBtnLbl.addClickHandler(new CloseClickHandler());
		addResourceBtnLbl.setEnable(true);
		addResourceBtnLbl.getElement().removeClassName("secondary");
		addResourceBtnLbl.getElement().addClassName("primary");
		standardsDropListValues.getElement().getStyle().setTop(0, Unit.PX);

		addResourceBtnLbl.addClickHandler(new AddClickHandler());
		uploadImageLbl.addClickHandler(new OnEditImageClick());

		generateFromUrlBtn.addClickHandler(new onGenerateFromUrlBtnClick());


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

		uploadImageLbl.getElement().setId("lblUploadImage");
		addResourceBtnLbl.getElement().setId("btnAdd");
		urlTextBox.getElement().setId("tbUrl");
		titleTextBox.getElement().setId("tbTitle");
		StringUtil.setAttributes(titleTextBox, true);
		cancelResourcePopupBtnLbl.getElement().setId("lblCancel");
		descriptionTxtAera.getElement().setId("taDescription");
		StringUtil.setAttributes(descriptionTxtAera, true);
		descriptionTxtAera.getElement().setAttribute("placeholder", i18n.GL0359());
		if (!isGoogleDriveFile) {
			urlTextBox.addKeyUpHandler(new UrlKeyUpHandler());
			urlTextBox.addBlurHandler(new UrlBlurHandler());
		}
		titleTextBox.addKeyUpHandler(new TitleKeyUpHandler());
		descriptionTxtAera.addKeyUpHandler(new DescriptionKeyUpHandler());
		titleTextBox.getElement().setAttribute("maxlength", "50");
		descriptionTxtAera.getElement().setAttribute("maxlength", "500");
		resourceCategoryLabel.setText(i18n.GL0360());
		resourceCategoryLabel.getElement().setId("lblResourceCategoryLabel");
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL0360());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL0360());
		resourceEducationalLabel.setText(i18n.GL1684());
		// resourceEducationalLabel.getElement().setId("lblResourceEducationalLabel");
		resourceEducationalLabel.getElement().setAttribute("alt", i18n.GL1684());
		resourceEducationalLabel.getElement().setAttribute("title", i18n.GL1684());
		resourcemomentsOfLearningLabel.setText(i18n.GL1684());
		// resourcemomentsOfLearningLabel.getElement().setId("lblResourcemomentsOfLearningLabel");
		resourcemomentsOfLearningLabel.getElement().setAttribute("alt", i18n.GL1684());
		resourcemomentsOfLearningLabel.getElement().setAttribute("title", i18n.GL1684());
		mandatoryUrlLbl.getElement().setId("lblMandatoryUrlLbl");
		mandatoryUrlLbl.setVisible(false);
		mandatoryTitleLbl.setVisible(false);
		mandatoryTitleLbl.getElement().setId("lblMandatoryTitleLbl");
		mandatoryTitleLblForSwareWords.getElement().setId("lblMandatoryTitleLblForSwareWords");
		mandatoryTitleLblForSwareWords.setVisible(false);
		mandatoryDescLblForSwareWords.getElement().setId("lblMandatoryDescLblForSwareWords");
		mandatoryDescLblForSwareWords.setVisible(false);
		mandatoryCategoryLbl.setVisible(false);
		mandatoryCategoryLbl.getElement().setId("lblMandatoryCategoryLbl");
		mandatoryCategoryLbl.getElement().getStyle().setTop(-10, Unit.PX);
		descCharcterLimit.getElement().setInnerText(i18n.GL0143());
		descCharcterLimit.getElement().setId("pnlDescCharcterLimit");
		descCharcterLimit.getElement().setAttribute("alt", i18n.GL0143());
		descCharcterLimit.getElement().setAttribute("title", i18n.GL0143());
		descCharcterLimit.setVisible(false);
		agreeText.setText(i18n.GL0870());
		agreeText.getElement().setId("lblAgreeText");
		agreeText.getElement().setAttribute("alt", i18n.GL0870());
		agreeText.getElement().setAttribute("title", i18n.GL0870());
		commuGuideLinesAnr.setText(i18n.GL0871() + i18n.GL_GRR_COMMA());
		commuGuideLinesAnr.getElement().setId("lnkCommuGuideLinesAnr");
		commuGuideLinesAnr.getElement().setAttribute("alt", i18n.GL0871());
		commuGuideLinesAnr.getElement().setAttribute("title", i18n.GL0871());
		termsAndPolicyAnr.setText(" " + i18n.GL0872() + i18n.GL_GRR_COMMA());
		termsAndPolicyAnr.getElement().setId("lnkTermsAndPolicyAnr");
		termsAndPolicyAnr.getElement().setAttribute("alt", i18n.GL0872());
		termsAndPolicyAnr.getElement().setAttribute("title", i18n.GL0872());
		privacyAnr.setText(" " + i18n.GL0873());
		privacyAnr.getElement().setId("lnkPrivacyAnr");
		privacyAnr.getElement().setAttribute("alt", i18n.GL0873());
		privacyAnr.getElement().setAttribute("title", i18n.GL0873());
		andText.setText(" " + i18n.GL_GRR_AND() + " ");
		andText.getElement().setId("lblAndText");
		andText.getElement().setAttribute("alt", i18n.GL_GRR_AND());
		andText.getElement().setAttribute("title", i18n.GL_GRR_AND());
		copyRightAnr.setText(" " + i18n.GL0875());
		copyRightAnr.getElement().setId("lnkCopyRightAnr");
		copyRightAnr.getElement().setAttribute("alt", i18n.GL0875());
		copyRightAnr.getElement().setAttribute("title", i18n.GL0875());
		additionalText.setText(i18n.GL0874());
		additionalText.getElement().setId("" + "lblAdditionalText");
		additionalText.getElement().setAttribute("alt", i18n.GL0874());
		additionalText.getElement().setAttribute("title", i18n.GL0874());
		leftArrowLbl.getElement().setId("lblLeftArrowLbl");
		leftArrowLbl.setVisible(false);
		rightArrowLbl.setVisible(false);
		rightArrowLbl.getElement().setId("lblRightArrowLbl");
		setThumbnailImage.getElement().setId("imgSetThumbnailImage");
		setThumbnailImage.setVisible(false);
		loadingTextLbl.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
		resourceTypePanel.setVisible(true);
		resourceTypePanel.getElement().setId("pnlResourceTypePanel");
		educationalUsePanel.getElement().setId("pnlEducationalUsePanel");
		educationalUsePanel.setVisible(false);
		momentsOfLearningPanel.getElement().setId("pnlMomentsOfLearningPanel");
		momentsOfLearningPanel.setVisible(false);
		loadingPanel.getElement().setId("pnlLoadingPanel");
		categorypanel.getElement().setId("pnlCategorypanel");
		loadingPanel.setVisible(false);
		panelContentRights.getElement().setId("pnlPanelContentRights");
		panelContentRights.setVisible(false);
		rightsChkBox.addClickHandler(new rightsChecked());
		rightsChkBox.getElement().setId("chkRights");
		resoureDropDownLbl.getElement().setId("lblResoureDropDownLbl");
		videoResourcePanel.getElement().setId("epnlVideoResourcePanel");
		video.getElement().setId("pnlVideo");
		websiteResourcePanel.getElement().setId("epnlWebsiteResourcePanel");
		website.getElement().setId("pnlWebsite");
		interactiveResourcePanel.getElement().setId("epnlInteractiveResourcePanel");

		clearCategorySelections();
		videoClickHandler = videoResourcePanel.addClickHandler(new checkAvailableClickHandler());
		websiteClickHandler = websiteResourcePanel.addClickHandler(new checkAvailableClickHandler());
		interactiveClickHandler = interactiveResourcePanel.addClickHandler(new checkAvailableClickHandler());
		imageClickHandler = imageResourcePanel.addClickHandler(new checkAvailableClickHandler());
		textClickHandler = textResourcePanel.addClickHandler(new checkAvailableClickHandler());
		audioClickHandler = audioResourcePanel.addClickHandler(new checkAvailableClickHandler());

		interactive.getElement().setId("pnlInteractive");
		imageResourcePanel.getElement().setId("epnlImageResourcePanel");
		image.getElement().setId("pnlImage");
		textResourcePanel.getElement().setId("epnlTextResourcePanel");
		texts.getElement().setId("pnlTexts");
		audioResourcePanel.getElement().setId("epnlAudioResourcePanel");
		audio.getElement().setId("pnlAudio");
		refreshLbl.getElement().setId("epnlRefreshLbl");
		refreshLbl.getElement().getStyle().setWidth(70, Unit.PX);

		/*
		 * educationalDropDownLbl.getElement().setId("lblEducationalDropDownLbl"
		 * );
		 */ mandatoryEducationalLbl.getElement().setId("lblMandatoryEducationalLbl");

		// momentsOfLearningDropDownLbl.getElement().setId("lblMomentsOfLearningDropDownLbl");
		mandatorymomentsOfLearninglLbl.getElement().setId("lblMandatorymomentsOfLearninglLbl");
		standardContainer.getElement().setId("fpnlStandardContainer");
		standardSgstBox.getElement().setId("StandardSgstBox");
		standardMaxMsg.getElement().setId("lblStandardMaxMsg");

		driveFileInfoLbl.getElement().setId("lblDriveFileInfoLbl");
		lblContentRights.getElement().setId("epnlLblContentRights");
		rightsContent.getElement().setId("pnlRightsContent");
		buttonsPanel.getElement().setId("pnlButtonsPanel");
		addResourceBtnPanel.getElement().setId("pnlAddResourceBtnPanel");

		moblieFriendly.setText(i18n.GL1811());
		moblieFriendly.getElement().setId("spnMobileFriendly");
		moblieFriendly.getElement().setAttribute("alt", i18n.GL1811());
		moblieFriendly.getElement().setAttribute("title", i18n.GL1811());
		moblieFriendly.getElement().getStyle().setDisplay(Display.INLINE);

		mobileYes.setText(i18n.GL_GRR_YES());
		mobileYes.getElement().setId("btnYes");
		mobileYes.getElement().setAttribute("alt", i18n.GL_GRR_YES());
		mobileYes.getElement().setAttribute("title", i18n.GL_GRR_YES());

		mobileNo.setText(i18n.GL1735());
		mobileNo.getElement().setId("btnNo");
		mobileNo.getElement().setAttribute("alt", i18n.GL1735());
		mobileNo.getElement().setAttribute("title", i18n.GL1735());

		accessHazard.setText(i18n.GL1804());
		accessHazard.getElement().setId("lblAccessHazard");
		accessHazard.getElement().setAttribute("alt", i18n.GL1804());
		accessHazard.getElement().setAttribute("title", i18n.GL1804());
		// accessHazard.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);

		mediaLabel.setText(i18n.GL3094());
		mediaLabel.getElement().setId("lblMediaFeature");
		mediaLabel.getElement().setAttribute("alt", "Media Feature");
		mediaLabel.getElement().setAttribute("title", "Media Feature");
		mediaLabel.getElement().getStyle().setDisplay(Display.INLINE);

		lblMediaPlaceHolder.setText(i18n.GL3051() + i18n.GL_SPL_SEMICOLON());
		lblMediaPlaceHolder.getElement().setId("phMediaFeature");
		lblMediaPlaceHolder.getElement().setAttribute("alt", "Choose a Media Feature Option:");
		lblMediaPlaceHolder.getElement().setAttribute("title", "Choose a Media Feature Option:");

		mandatorygenerateFromUrlLbl.setText(i18n.GL3095());

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

		/*
		 * List<String> mediaFeatureList =
		 * Arrays.asList(mediaFeatureStr.split(",")); for(int n=0;
		 * n<mediaFeatureList.size(); n++){ String mediaTitleVal =
		 * mediaFeatureList.get(n); final Label titleLabel = new
		 * Label(mediaTitleVal);
		 * titleLabel.setStyleName(CollectionAssignCBundle.INSTANCE.css().
		 * classpageTitleText()); titleLabel.getElement().setAttribute("id",
		 * mediaTitleVal); //Set Click event for title
		 * titleLabel.addClickHandler(new ClickHandler() {
		 *
		 * @Override public void onClick(ClickEvent event) { String
		 * optionSelected = titleLabel.getElement().getId();
		 * lblMediaPlaceHolder.setText(optionSelected);
		 * spanelMediaFeaturePanel.setVisible(false);
		 * lblMediaPlaceHolder.getElement().setId(titleLabel.getElement().getId(
		 * ));
		 * lblMediaPlaceHolder.setStyleName(CollectionAssignCBundle.INSTANCE.css
		 * ().selectedClasspageText());
		 * lblMediaPlaceHolder.setText(optionSelected);
		 * setAdvancedOptionsStyles(); } });
		 * htmlMediaFeatureListContainer.add(titleLabel); }
		 */
		getMediaFeatures();
		HTMLEventPanel defaultMediaFeaturePnl = new HTMLEventPanel("");
		defaultMediaFeaturePnl.getElement().setClassName(
				CollectionEditResourceCBundle.INSTANCE.css().myFolderCollectionFolderVideoOuterContainer());
		HTMLPanel defaultMediaFeatureText = new HTMLPanel("");
		defaultMediaFeatureText.getElement().setInnerHTML(i18n.GL3093());
		defaultMediaFeatureText.getElement()
				.setClassName(CollectionEditResourceCBundle.INSTANCE.css().myEducationalPanelSubTitles());
		defaultMediaFeatureText.getElement().addClassName(CollectionEditResourceCBundle.INSTANCE.css().setBorder());
		defaultMediaFeaturePnl.add(defaultMediaFeatureText);
		defaultMediaFeaturePnl.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				lblMediaPlaceHolder.setText(i18n.GL3051() + i18n.GL_SPL_SEMICOLON());
				spanelMediaFeaturePanel.setVisible(false);
				lblMediaPlaceHolder.setStyleName(CollectionAssignCBundle.INSTANCE.css().selectedClasspageText());
				setAdvancedOptionsStyles();
			}
		});

		htmlMediaFeatureListContainer.add(defaultMediaFeaturePnl);
		clearFields();
		cancelResourcePopupBtnLbl.getElement().setAttribute("style", "margin-top:10px");
		copyRightAnr.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.enableScrolling(false);
				copyRightPolicy = new CopyRightPolicyVc() {
					@Override
					public void openParentPopup() {
						Window.enableScrolling(false);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
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
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
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
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
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
				Window.open("http://support.gooru.org/hc/en-us/articles/200688506", "_blank", "");
			}
		});

		titleTextBox.addBlurHandler(new CheckProfanityInOnBlur(titleTextBox, null, mandatoryTitleLblForSwareWords));
		descriptionTxtAera
				.addBlurHandler(new CheckProfanityInOnBlur(null, descriptionTxtAera, mandatoryDescLblForSwareWords));


		ClickHandler rootHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!hasClickedOnDropDwn) {
					educationalUsePanel.setVisible(false);
					educationalDropDownLblOpen = false;
					momentsOfLearningPanel.setVisible(false);
					momentsOfLearningOpen = false;
					spanelMediaFeaturePanel.setVisible(false);

				} else {
					hasClickedOnDropDwn = false;
				}
			}
		};

		RootPanel.get().addDomHandler(rootHandler, ClickEvent.getType());
		generateFromUrlBtn.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				mandatorygenerateFromUrlLbl.setVisible(false);
			}
		});

		/** Add Advanced Setup Changes **/

		educationalContainer.setVisible(false);
		educationalUsePanel.setVisible(false);
		momentsOfLearningContainer.setVisible(false);
		momentsOfLearningPanel.setVisible(false);
		standardContainer.setVisible(false);
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
		eHearderIconCentury.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconMomentsOfLearning.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconstandards.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconAccessHazard.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconMediafeature.addClickHandler(new MinimizePanelsClickHandler());
		eHearderIconMobileFriendly.addClickHandler(new MinimizePanelsClickHandler());

		/** Add Advanced Setup Changes End **/
		// This will hide the popup when clicked on the cancel button
		centuryPresenterWidget.getCancelBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hideCenturyPopup();
			}
		});
		// This will hide the popup when clicked on close button
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
				if (centurySelectedValues != null && centurySelectedValues.size() > 0) {
					for (Map.Entry<Long, String> entry : centurySelectedValues.entrySet()) {
						StandardFo codeObj = new StandardFo();
						codeObj.setCodeId(Integer.parseInt(entry.getKey() + ""));
						codeObj.setCode(entry.getValue());
						centuryDo.add(codeObj);
						CodeDo codeObjStandard = new CodeDo();
						codeObjStandard.setCodeId(Integer.parseInt(entry.getKey() + ""));
						codeObjStandard.setCode(entry.getValue());
						// standardsDo.add(codeObjStandard);
						centuryPanel.add(create21CenturyLabel(entry.getValue(), entry.getKey() + "", ""));
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
				for (int i = 0; i < centuryDo.size(); i++) {
					if (centuryCode.equalsIgnoreCase(centuryDo.get(i).getCode())) {
						centuryDo.remove(i);
						// standardsDo.remove(i);
						centurySelectedValues.remove(Long.parseLong(id));
					}
				}
				this.getParent().removeFromParent();
			}
		};
		return new DownToolTipWidgetUc(closeLabel, description);
	}

	/**
	 * This method will hide the century popup
	 */
	public void hideCenturyPopup() {
		centuryPopup.hide();
	}

	private void OpenMediaFeatureDropdown() {
		hasClickedOnDropDwn = true;
		if (spanelMediaFeaturePanel.isVisible()) {
			spanelMediaFeaturePanel.setVisible(false);
		} else {
			spanelMediaFeaturePanel.setVisible(true);
		}
	}

	public void hideAllPanels() {
		videoResourcePanel.setVisible(false);
		websiteResourcePanel.setVisible(false);
		interactiveResourcePanel.setVisible(false);
		imageResourcePanel.setVisible(false);
		textResourcePanel.setVisible(false);
		audioResourcePanel.setVisible(false);
	}

	public void setDriveFileDetails() {
		if (isGoogleDriveFile && !googleDriveItemDo.isShared()) {
			mandatoryUrlLbl.setText(i18n.GL2009_1());
			mandatoryUrlLbl.setVisible(true);
		}
		titleTextBox.setValue(googleDriveItemDo.getTitle());
		titleTextBox.getElement().setAttribute("alt", googleDriveItemDo.getTitle());
		titleTextBox.getElement().setAttribute("title", googleDriveItemDo.getTitle());
		urlTextBox.setReadOnly(true);
		titleTextBox.setFocus(true);
		hideAllPanels();
		if (googleDriveItemDo.getMimeType().equals(DriveView.DOCUMENT_MIMETYPE)
				|| googleDriveItemDo.getMimeType().equals(DriveView.PRESENTATION_MIMETYPE)
				|| googleDriveItemDo.getMimeType().equals(DriveView.SPREADSHEET_MIMETYPE)) {
			urlTextBox.setValue(googleDriveItemDo.getEmbedLink());
			urlTextBox.getElement().setAttribute("alt", googleDriveItemDo.getEmbedLink());
			urlTextBox.getElement().setAttribute("title", googleDriveItemDo.getEmbedLink());
			textResourcePanel.setVisible(true);
			setTextCategory();
		} else if (googleDriveItemDo.getMimeType().equals(DriveView.DRAWING_MIMETYPE)) {
			urlTextBox.setValue(googleDriveItemDo.getEmbedLink());
			urlTextBox.getElement().setAttribute("alt", googleDriveItemDo.getEmbedLink());
			urlTextBox.getElement().setAttribute("title", googleDriveItemDo.getEmbedLink());
			imageResourcePanel.setVisible(true);
			setImageCategory();
		} else if (googleDriveItemDo.getMimeType().equals(DriveView.FORM_MIMETYPE)) {
			try {
				urlTextBox.setValue(googleDriveItemDo.getDefaultOpenWithLink().replaceFirst("edit", "viewform"));
				urlTextBox.getElement().setAttribute("alt",
						googleDriveItemDo.getDefaultOpenWithLink().replaceFirst("edit", "viewform"));
				urlTextBox.getElement().setAttribute("title",
						googleDriveItemDo.getDefaultOpenWithLink().replaceFirst("edit", "viewform"));
			} catch (Exception e) {
				urlTextBox.setValue(googleDriveItemDo.getAlternateLink().replaceFirst("edit", "viewform"));
				urlTextBox.getElement().setAttribute("alt",
						googleDriveItemDo.getAlternateLink().replaceFirst("edit", "viewform"));
				urlTextBox.getElement().setAttribute("title",
						googleDriveItemDo.getAlternateLink().replaceFirst("edit", "viewform"));
			}
			interactiveResourcePanel.setVisible(true);
			setInteractiveCategory();
		}
	}

	public void onLoad() {
		super.onLoad();
		urlTextBox.setFocus(true);
		clearFields();
		if (isGoogleDriveFile) {
			setDriveFileDetails();
			driveFileInfoLbl.removeFromParent();
		} else {
			driveFileInfoLbl.removeFromParent();
		}
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

	private class CloseClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			clearFields();
			hidePopup();
		}
	}

	@UiHandler("lblContentRights")
	public void onMouseOver(MouseOverEvent event) {
		panelContentRights.setVisible(true);
	}

	@UiHandler("lblContentRights")
	public void onMouseOut(MouseOutEvent event) {
		panelContentRights.setVisible(false);
	}

	public abstract void hidePopup();

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
			StandardFo codeObj = new StandardFo();
			String codeIdVal = getCodeIdByCodeCentury(centurySgstBox.getValue(), centurySearchDo.getSearchResults());
			codeObj.setCodeId(Integer.parseInt(codeIdVal));
			codeObj.setCode(centurySgstBox.getValue());
			centuryDo.add(codeObj);
			CodeDo codeObjStandard = new CodeDo();
			codeObjStandard.setCodeId(Integer.parseInt(codeIdVal));
			codeObjStandard.setCode(centurySgstBox.getValue());
			// standardsDo.add(codeObjStandard);
			centurySelectedValues.put(Long.parseLong(codeIdVal), centurySgstBox.getValue());
			centuryPanel.add(create21CenturyLabel(centuryTag, id, centuryCodesMap.get(id)));
		}
	}





	private class OnEditImageClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			resourceImageUpload();
		}
	}

	/**
	 *
	 * @author GooruTeam This method is used to generate image on button click
	 *
	 */
	private class onGenerateFromUrlBtnClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (isGenerateURL) {
				String userUrlStr = urlTextBox.getText().trim();
				userUrlStr = URL.encode(userUrlStr);
				// userUrlStr = userUrlStr.replaceAll("#", "%23");
				urlTextBox.setText(URL.decode(userUrlStr));
				urlTextBox.getElement().setAttribute("alt", userUrlStr);
				urlTextBox.getElement().setAttribute("title", userUrlStr);
				String userUrlStr1 = userUrlStr.replaceAll("feature=player_detailpage&", "");
				userUrlStr1 = userUrlStr.replaceAll("feature=player_embedded&", "");

				if (userUrlStr.indexOf("youtube") > 0) {
					String youTubeIbStr = ResourceImageUtil.getYoutubeVideoId(userUrlStr);
					String thumbnailUrl = "http://img.youtube.com/vi/" + youTubeIbStr + "/1.jpg";
					generateImageLbl.setVisible(false);
					setThumbnailImage.getElement().setAttribute("style", "width: 80px;height: 60px;");
					setThumbnailImage.setUrl(thumbnailUrl);
				} else {
					activeImageIndex = 0;
					setImageThumbnail();
					generateImageLbl.setVisible(false);
					setThumbnailImage.getElement().setAttribute("style", "width: 80px;height: 60px;");
				}
			}
		}
	}

	private class rightsChecked implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (rightsChkBox.getValue()) {
				rightsLbl.getElement().getStyle().setColor("black");
			} else {
				rightsLbl.getElement().getStyle().setColor("orange");
			}

		}
	}

	public void setMobileFriendlyObjectVal(String mobileFriendlyVal) {
		if (mobileFriendlyVal.contains(mobileYes.getText())) {
			mobileYes.getElement().setClassName("at-OffButtonsActive");
			mobileNo.getElement().setClassName("at-OnButtonDeActive");
		} else if (mobileFriendlyVal.contains(mobileNo.getText())) {
			mobileNo.getElement().setClassName("at-OffButtonsActive");
			mobileYes.getElement().setClassName("at-OnButtonDeActive");
		}
	}

	@UiHandler("mobileYes")
	public void onmobileYesClick(ClickEvent click) {
		mobileYes.getElement().setClassName("at-OffButtonsActive");
		mobileNo.getElement().setClassName("at-OnButtonDeActive");
		updateMobileFriendlyAdvancedStyles();
	}

	@UiHandler("mobileNo")
	public void onmobileNoClick(ClickEvent click) {
		mobileNo.getElement().setClassName("at-OffButtonsActive");
		mobileYes.getElement().setClassName("at-OnButtonDeActive");
		updateMobileFriendlyAdvancedStyles();
	}

	/*
	 * method for select access hazards
	 */
	public List<Integer> setAccessHazards() {
		int size = hazardContainer.getWidgetCount();
		List<Integer> accessHazardsSelected = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			Label label = (Label) hazardContainer.getWidget(i);
			if (label.getStyleName().contains("select")) {
				accessHazardsSelected.add(Integer.parseInt(label.getElement().getId()));
			}
		}
		setAdvancedAccessHazardStyles(accessHazardsSelected.size());
		return accessHazardsSelected;
	}

	public abstract void resourceImageUpload();

	private class AddClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			final Map<String, String> parms = new HashMap<String, String>();
			parms.put("text", titleTextBox.getValue());
			final List<String> tagList = new ArrayList<String>();
			getSelectedStandards();
			AppClientFactory.getInjector().getResourceService().checkProfanity(parms,
					new SimpleAsyncCallback<Boolean>() {
						@Override
						public void onSuccess(Boolean value) {
							isHavingBadWordsInTextbox = value;
							if (value) {
								SetStyleForProfanity.SetStyleForProfanityForTextBox(titleTextBox,
										mandatoryTitleLblForSwareWords, value);
							} else {
								parms.put("text", descriptionTxtAera.getText());
								AppClientFactory.getInjector().getResourceService().checkProfanity(parms,
										new SimpleAsyncCallback<Boolean>() {

									@Override
									public void onSuccess(Boolean result) {
										isValidate = true;
										isHavingBadWordsInRichText = result;
										if (result) {
											SetStyleForProfanity.SetStyleForProfanityForTextArea(descriptionTxtAera,
													mandatoryDescLblForSwareWords, result);
										} else {
											if (!isHavingBadWordsInRichText && !isHavingBadWordsInTextbox) {

												String urlStr = urlTextBox.getText();
												Map<String, List<Integer>> hazardsAndMediaFeatures = new HashMap<>();
												urlStr = urlStr.replaceAll("feature=player_detailpage&", "");
												urlStr = urlStr.replaceAll("feature=player_embedded&", "");
												urlStr = URL.encode(urlStr);
												String youTubeId = getYoutubeVideoId(urlStr);

												if (urlStr.endsWith("/")) {
													urlStr = urlStr.substring(0, urlStr.length() - 1);
												}

												String descriptionStr = descriptionTxtAera.getText().trim(); // tinyMce.getText().trim();
												final String titleStr = titleTextBox.getText().trim();
												final String categoryStr = resourceCategoryLabel.getText();// resourceTypeListBox.getItemText(resourceTypeListBox.getSelectedIndex());
												final String idStr = collectionDo.getGooruOid();

												if (urlStr.contains("goorulearning.org") || urlStr.contains("gooru.org")) {
													if (urlStr.contains("support.goorulearning.org")
															|| urlStr.contains("about.goorulearning.org") || urlStr.contains("support.gooru.org")
															|| urlStr.contains("about.gooru.org")) {
														isValidate = true;
													} else {
														showUrlErrorMessage(i18n.GL0924());
														isValidate = false;
													}
												}
												if (isGoogleDriveFile && !googleDriveItemDo.isShared()) {
													showUrlErrorMessage(i18n.GL2009_1());
													isValidate = false;
												}
												if (!rightsChkBox.getValue()) {
													rightsLbl.getElement().getStyle().setColor("orange");
													isValidate = false;
												}
												if (urlStr == null || urlStr.equalsIgnoreCase("")) {
													showUrlErrorMessage(i18n.GL0916());
													isValidate = false;
												} else {
													boolean isStartWithHttp = urlStr.matches("^(http|https)://.*$");
													if (!isStartWithHttp) {
														urlStr = "http://" + urlStr;
														urlTextBox.setText(urlStr);
														urlTextBox.getElement().setAttribute("alt", urlStr);
														urlTextBox.getElement().setAttribute("title", urlStr);
													}
												}

												if (titleStr.toLowerCase().contains("www.")
														|| titleStr.toLowerCase().contains("http://")
														|| titleStr.toLowerCase().contains("https://")
														|| titleStr.toLowerCase().contains("ftp://")) {
													showTitleErrorMessage(i18n.GL0323());
													isValidate = false;
												}

												if (titleStr == null || titleStr.equalsIgnoreCase("")) {
													showTitleErrorMessage(i18n.GL0173());
													isValidate = false;
												}
												if (descriptionStr.length() > 300) {
													descCharcterLimit.setVisible(true);
													isValidate = false;
												}
												if (categoryStr == null || categoryStr.equalsIgnoreCase("-1")
														|| categoryStr.equalsIgnoreCase("Choose a resource format")) {
													showCategoryErrorMessage(i18n.GL0917());
													scrollPanel.setVerticalScrollPosition(0);
													isValidate = false;
												}

												if (!isValidYoutubeUrlFlag && categoryStr.equalsIgnoreCase("Video")) {
													showCategoryErrorMessage(i18n.GL0925());
													scrollPanel.setVerticalScrollPosition(0);
													isValidate = false;

												}

												if (!isValidUrl(urlStr, true)) {
													showUrlErrorMessage(i18n.GL0926());
													isValidate = false;
												}
												if (urlStr.indexOf("youtube") != -1) {
													if (youTubeId == null || youTubeId.equalsIgnoreCase("null")
															|| youTubeId.equalsIgnoreCase("")) {
														if (!categoryStr.equalsIgnoreCase("Webpage")) {
															isValidate = true;
														} else {
															showCategoryErrorMessage(i18n.GL0927());
															scrollPanel.setVerticalScrollPosition(0);
															isValidate = false;
														}
													}
												}
												if (categoryStr.equalsIgnoreCase("Audio") && !hasValidateResource()) {
													showUrlErrorMessage(i18n.GL1161());
													isValidate = false;
												}
												String mediaType = "";
												if (mobileYes.getStyleName()
														.contains("at-OffButtonsActive")) {
													mediaType = i18n.GL_GRR_MOBILE_FRIENDLY();
													tagList.add("Mobile Friendly : " + mobileYes.getText());

												}else if (mobileNo.getStyleName()
												.contains("at-OffButtonsActive")) {
													tagList.add("Mobile Friendly : " + mobileNo.getText());
													mediaType = i18n.GL_GRR_NOT_MOBILE_FRIENDLY();
												}
												List<Integer> mediaFeaturesList = new ArrayList<>();
												if (!lblMediaPlaceHolder.getText()
														.equalsIgnoreCase("Choose a Media Feature Option:")) {
													mediaFeaturesList.add(
															Integer.parseInt(lblMediaPlaceHolder.getElement().getId()));
													tagList.add(mediaLabel.getText() + " : "
															+ lblMediaPlaceHolder.getText());

												}
												hazardsAndMediaFeatures.put("media", mediaFeaturesList);
												hazardsAndMediaFeatures.put("hazard", setAccessHazards());

												/*
												 * // String hazardArr[] =
												 * setAccessHazards();
												 *
												 * if(hazardArr != null) {
												 * for(int
												 * i=0;i<hazardArr.length;i++) {
												 *
												 *
												 * //tagList.add('
												 * "' + hazardArr[i].toString() +'"
												 * ');
												 *
												 * tagList.add(hazardArr[i].
												 * toString()); } }
												 */
												if (resourceEducationalLabel.getText() != null
														|| !resourceEducationalLabel.getText().trim()
																.equalsIgnoreCase("")) {
													if (!resourceEducationalLabel.getText().trim()
															.equalsIgnoreCase(DEFAULT_COMBO_BOX_TEXT)) {
														tagList.add("Educational Use : "
																+ resourceEducationalLabel.getText());
													}

												}
												// AreYouSurceToolTip
												// AreYouSurceToolTip=new
												// AreYouSurceToolTip();
												if (isValidate && !isShortenedUrl()) {
													if (!processing) {
														MixpanelUtil.Create_NewResource();
														processing = true;
														loadingTextLbl.getElement().getStyle()
																.setDisplay(Display.BLOCK);
														buttonsPanel.getElement().getStyle().setDisplay(Display.NONE);
														descriptionStr = descriptionTxtAera.getText().trim();
														urlStr = urlStr.replaceAll("feature=player_detailpage&", "");
														urlStr = urlStr.replaceAll("feature=player_embedded&", "");
														String hostName = null;
														if (isGoogleDriveFile) {
															hostName = GOOGLE_DRIVE;
														}
														if (collectionDo.getSharing() != null && collectionDo
																.getSharing().equalsIgnoreCase("public")) {
															addResource(idStr, urlStr, titleStr, descriptionStr,
																	categoryStr, thumbnailUrlStr, getVideoDuration(),
																	true, resourceEducationalLabel.getElement().getId(),
																	resourcemomentsOfLearningLabel.getElement().getId(),
																	standardsDo, centuryDo, hostName, tagList,
																	hazardsAndMediaFeatures, mediaType);
														} else {
															addResource(idStr, urlStr, titleStr, descriptionStr,
																	categoryStr, thumbnailUrlStr, getVideoDuration(),
																	false,
																	resourceEducationalLabel.getElement().getId(),
																	resourcemomentsOfLearningLabel.getElement().getId(),
																	standardsDo, centuryDo, hostName, tagList,
																	hazardsAndMediaFeatures, mediaType);
														}
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
	 * @function addResource
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description
	 *
	 *
	 * 				@parm(s) : @param idStr @parm(s) : @param urlStr @parm(s)
	 *              : @param titleStr @parm(s) : @param descriptionStr @parm(s)
	 *              : @param categoryStr @parm(s) : @param
	 *              thumbnailUrlStr @parm(s) : @param endTime @parm(s) : @param
	 *              conformationFlag @parm(s) : @param educationalUse @parm(s)
	 *              : @param momentsOfLearning @parm(s) : @param
	 *              standards @parm(s) : @param hostName @parm(s) : @param
	 *              tagList
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public abstract void addResource(String idStr, String urlStr, String titleStr, String descriptionStr,
			String categoryStr, String thumbnailUrlStr, Integer endTime, boolean conformationFlag,
			String educationalUse, String momentsOfLearning, List<CodeDo> standards, List<StandardFo> centurySkill,
			String hostName, List<String> tagList, Map<String, List<Integer>> hazardAndMediaMap, String mediaType);

	private class UrlBlurHandler implements BlurHandler {

		@Override
		public void onBlur(BlurEvent event) {

			refreshLbl.setVisible(false);
			setThumbnailImage.setUrl("");
			setThumbnailImage.setVisible(false);
			rightArrowLbl.setVisible(false);
			leftArrowLbl.setVisible(false);
			generateImageLbl.setVisible(true);
			videoResourcePanel.removeStyleName("active");
			resourceCategoryLabel.setText(i18n.GL0360());

			clearCategorySelections();

			final Map<String, String> parms = new HashMap<String, String>();

			parms.put("text", urlTextBox.getText().trim());
			AppClientFactory.getInjector().getResourceService().checkProfanity(parms,
					new SimpleAsyncCallback<Boolean>() {

						@Override
						public void onSuccess(Boolean value) {
							if (!value) {
								addResourceBtnLbl.setVisible(true);
								addResourceBtnPanel.setVisible(true);
								String userUrlStr = urlTextBox.getText().trim();
								if (userUrlStr.contains("goorulearning.org") ||userUrlStr.contains("gooru.org")) {
									if (userUrlStr.contains("support.goorulearning.org")
											|| userUrlStr.contains("about.goorulearning.org") || userUrlStr.contains("support.gooru.org")
											|| userUrlStr.contains("about.gooru.org")) {

									} else {
										showUrlErrorMessage(i18n.GL0924());
										return;
									}
								}

								if (userUrlStr.endsWith("/")) {
									userUrlStr = userUrlStr.substring(0, userUrlStr.length() - 1);
								}
								if (!userUrlStr.equalsIgnoreCase("")) {
									boolean isStartWithHttp = userUrlStr.matches("^(http|https)://.*$");
									if (!isStartWithHttp) {
										userUrlStr = "http://" + userUrlStr;
										urlTextBox.setText(userUrlStr);
										urlTextBox.getElement().setAttribute("alt", userUrlStr);
										urlTextBox.getElement().setAttribute("title", userUrlStr);
									}
									if (isValidUrl(userUrlStr, true)) {
										enableGenerateBtn();
										refreshLbl.setVisible(true);
										userUrlStr = URL.encode(userUrlStr);
										urlTextBox.setText(URL.decode(userUrlStr));
										urlTextBox.getElement().setAttribute("alt", userUrlStr);
										urlTextBox.getElement().setAttribute("title", userUrlStr);
										String userUrlStr1 = userUrlStr.replaceAll("feature=player_detailpage&", "");
										userUrlStr1 = userUrlStr.replaceAll("feature=player_embedded&", "");
										checkShortenUrl(userUrlStr);
										if (userUrlStr.indexOf("youtube") > 0) {
											setVideoCategory();
											if (websiteClickHandler != null) {
												try {
													websiteClickHandler.removeHandler();
												} catch (Exception e) {
													AppClientFactory.printSevereLogger(
															"AddWebResourceView websiteClickHandler:::" + e);
												}
											}
											if (interactiveClickHandler != null) {
												try {
													interactiveClickHandler.removeHandler();
												} catch (Exception e) {
													AppClientFactory.printSevereLogger(
															"AddWebResourceView interactiveClickHandler:::" + e);
												}
											}
											if (imageClickHandler != null) {
												try {
													imageClickHandler.removeHandler();
												} catch (Exception e) {
													AppClientFactory.printSevereLogger(
															"AddWebResourceView imageClickHandler:::" + e);
												}
											}
											if (textClickHandler != null) {
												try {
													textClickHandler.removeHandler();
												} catch (Exception e) {
													AppClientFactory.printSevereLogger(
															"AddWebResourceView textClickHandler:::" + e);
												}
											}
											if (audioClickHandler != null) {
												try {
													audioClickHandler.removeHandler();
												} catch (Exception e) {
													AppClientFactory.printSevereLogger(
															"AddWebResourceView audioClickHandler:::" + e);
												}
											}
										} else {
											websiteClickHandler = websiteResourcePanel
													.addClickHandler(new checkAvailableClickHandler());
											interactiveClickHandler = interactiveResourcePanel
													.addClickHandler(new checkAvailableClickHandler());
											imageClickHandler = imageResourcePanel
													.addClickHandler(new checkAvailableClickHandler());
											textClickHandler = textResourcePanel
													.addClickHandler(new checkAvailableClickHandler());
											audioClickHandler = audioResourcePanel
													.addClickHandler(new checkAvailableClickHandler());
										}
										loadingPanel.setVisible(true);
										contentPanel.getElement().getStyle().setOpacity(0.6);

									} else {
										disableGenerateBtn();
										refreshLbl.setVisible(false);
										showUrlErrorMessage(i18n.GL0926());
									}
								} else {
									disableGenerateBtn();
									refreshLbl.setVisible(false);
								}
							} else {
								SetStyleForProfanity.SetStyleForProfanityForTextBox(urlTextBox, mandatoryUrlLbl, value);
								disableGenerateBtn();
								refreshLbl.setVisible(false);
							}
						}
					});
		}
	}

	public abstract void getResourceInfo(String userUrlStr);

	public abstract void checkShortenUrl(String userUrlStr);

	public abstract void updateThumbanilImage(String userUrlStr);

	private class UrlKeyUpHandler implements KeyUpHandler {
		public void onKeyUp(KeyUpEvent event) {
			clearUrlErrorMessage();
			String userUrlStr = urlTextBox.getText().trim();
			if (userUrlStr.equalsIgnoreCase("")) {
				refreshLbl.setVisible(false);
			}
		}
	}

	private class TitleKeyUpHandler implements KeyUpHandler {
		public void onKeyUp(KeyUpEvent event) {
			clearTitleErrorMessage();
			if (titleTextBox.getText().length() >= 50) {
				showTitleErrorMessage(i18n.GL0143());
			}
		}
	}

	private class DescriptionKeyUpHandler implements KeyUpHandler {
		public void onKeyUp(KeyUpEvent event) {
			descCharcterLimit.setVisible(false);
			if (descriptionTxtAera.getText().length() >= 500) {
				descriptionTxtAera.setText(descriptionTxtAera.getText().trim().substring(0, 500));
				descriptionTxtAera.getElement().setAttribute("alt", descriptionTxtAera.getText());
				descriptionTxtAera.getElement().setAttribute("title", descriptionTxtAera.getText());
				descCharcterLimit.setVisible(true);
			}

		}
	}

	private class MinimizePanelsClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {

			addSetupAdvancedView.setUpLabel.setVisible(true);

			if (event.getSource() == eHearderIconEducationalUse) {
				educationalContainer.setVisible(false);
				educationalUsePanel.setVisible(false);
				addSetupAdvancedView.educationUseAdvancedPnl.setVisible(true);
			} else if (event.getSource() == eHearderIconMomentsOfLearning) {
				momentsOfLearningContainer.setVisible(false);
				momentsOfLearningPanel.setVisible(false);
				addSetupAdvancedView.momentsOfLearningAdvancedPnl.setVisible(true);
			} else if (event.getSource() == eHearderIconstandards) {
				standardsBrowseContainer.setVisible(false);
				addSetupAdvancedView.standardsAdvancedPnl.setVisible(true);
			} else if (event.getSource() == eHearderIconAccessHazard) {
				accessHazardContainer.setVisible(false);
				addSetupAdvancedView.accessHazardAdvancedPnl.setVisible(true);
			} else if (event.getSource() == eHearderIconMediafeature) {
				mediaFeatureContainer.setVisible(false);
				addSetupAdvancedView.mediaFeatureAdvancedPnl.setVisible(true);
			} else if (event.getSource() == eHearderIconMobileFriendly) {
				mobileFriendlyContainer.setVisible(false);
				addSetupAdvancedView.mobileFreindlyAdvancedPnl.setVisible(true);
			} else if (event.getSource() == eHearderIconCentury) {
				centuryBrowseContainer.setVisible(false);
				addSetupAdvancedView.centuryAdvancedPnl.setVisible(true);
			}
		}
	}

	private class AddSetupAdvancedClickHandlers implements ClickHandler {
		public AddSetupAdvancedClickHandlers() {
		}

		@Override
		public void onClick(ClickEvent event) {

			if (event.getSource() == addSetupAdvancedView.educationUseAdvancedPnl) {
				educationalContainer.setVisible(true);
				educationalUsePanel.setVisible(true);
				addSetupAdvancedView.educationUseAdvancedPnl.setVisible(false);
			} else if (event.getSource() == addSetupAdvancedView.momentsOfLearningAdvancedPnl) {
				momentsOfLearningContainer.setVisible(true);
				momentsOfLearningPanel.setVisible(true);
				addSetupAdvancedView.momentsOfLearningAdvancedPnl.setVisible(false);
			} else if (event.getSource() == addSetupAdvancedView.standardsAdvancedPnl) {
				standardContainer.setVisible(true);
				standardsBrowseContainer.setVisible(true);
				addSetupAdvancedView.standardsAdvancedPnl.setVisible(false);
			} else if (event.getSource() == addSetupAdvancedView.accessHazardAdvancedPnl) {
				accessHazardContainer.setVisible(true);
				addSetupAdvancedView.accessHazardAdvancedPnl.setVisible(false);
			} else if (event.getSource() == addSetupAdvancedView.mediaFeatureAdvancedPnl) {
				mediaFeatureContainer.setVisible(true);
				addSetupAdvancedView.mediaFeatureAdvancedPnl.setVisible(false);
			} else if (event.getSource() == addSetupAdvancedView.mobileFreindlyAdvancedPnl) {
				mobileFriendlyContainer.setVisible(true);
				addSetupAdvancedView.mobileFreindlyAdvancedPnl.setVisible(false);
			} else if (event.getSource() == addSetupAdvancedView.centuryAdvancedPnl) {
				centuryContainer.setVisible(true);
				centuryBrowseContainer.setVisible(true);
				addSetupAdvancedView.centuryAdvancedPnl.setVisible(false);
			}

			if (isAllAdditionalTagsOpen()) {
				addSetupAdvancedView.setUpLabel.setVisible(false);
			}
		}
	}

	@UiHandler("leftArrowLbl")
	void leftArrowClick(ClickEvent event) {
		activeImageIndex--;
		setImageThumbnail();
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

	@UiHandler("rightArrowLbl")
	void rightArrowClick(ClickEvent event) {
		activeImageIndex++;
		setImageThumbnail();
	}

	/**
	 *
	 * @function setVideoCategory
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description
	 *
	 *
	 * 				@parm(s) :
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void setVideoCategory() {
		MixpanelUtil.mixpanelEvent("organize_add_resource_video_selected");
		resourceCategoryLabel.setText(i18n.GL0918());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL0918());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL0918());
		categorypanel.setStyleName(video.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen = false;
		clearCategoryErrorMessage();
		videoResourcePanel.addStyleName("active");
		websiteResourcePanel.removeStyleName("active");
		interactiveResourcePanel.removeStyleName("active");
		audioResourcePanel.removeStyleName("active");
		textResourcePanel.removeStyleName("active");
		imageResourcePanel.removeStyleName("active");
	}

	void setInteractiveCategory() {
		MixpanelUtil.mixpanelEvent("organize_add_resource_interactive_selected");
		resourceCategoryLabel.setText(i18n.GL0919());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL0919());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL0919());
		categorypanel.setStyleName(interactive.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen = false;
		clearCategoryErrorMessage();
		interactiveResourcePanel.addStyleName("active");
		websiteResourcePanel.removeStyleName("active");
		audioResourcePanel.removeStyleName("active");
		textResourcePanel.removeStyleName("active");
		videoResourcePanel.removeStyleName("active");
		imageResourcePanel.removeStyleName("active");
	}

	void setWebsiteCategory() {
		MixpanelUtil.mixpanelEvent("organize_add_resource_website_selected");
		resourceCategoryLabel.setText(i18n.GL1396());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1396());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1396());
		categorypanel.setStyleName(website.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen = false;
		websiteResourcePanel.addStyleName("active");
		interactiveResourcePanel.removeStyleName("active");
		audioResourcePanel.removeStyleName("active");
		textResourcePanel.removeStyleName("active");
		videoResourcePanel.removeStyleName("active");
		imageResourcePanel.removeStyleName("active");
		clearCategoryErrorMessage();
	}

	void setImageCategory() {
		MixpanelUtil.mixpanelEvent("organize_add_resource_image_selected");
		resourceCategoryLabel.setText(i18n.GL1046());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1046());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1046());
		categorypanel.setStyleName(image.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen = false;
		clearCategoryErrorMessage();
		imageResourcePanel.addStyleName("active");
		websiteResourcePanel.removeStyleName("active");
		interactiveResourcePanel.removeStyleName("active");
		audioResourcePanel.removeStyleName("active");
		textResourcePanel.removeStyleName("active");
		videoResourcePanel.removeStyleName("active");
	}

	void setTextCategory() {
		MixpanelUtil.mixpanelEvent("organize_add_resource_text_selected");
		resourceCategoryLabel.setText(i18n.GL1044());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1044());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1044());
		categorypanel.setStyleName(texts.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen = false;
		clearCategoryErrorMessage();
		textResourcePanel.addStyleName("active");
		websiteResourcePanel.removeStyleName("active");
		interactiveResourcePanel.removeStyleName("active");
		audioResourcePanel.removeStyleName("active");
		videoResourcePanel.removeStyleName("active");
		imageResourcePanel.removeStyleName("active");
	}

	void setAudioCategory() {
		MixpanelUtil.mixpanelEvent("organize_add_resource_audio_selected");
		resourceCategoryLabel.setText(i18n.GL1045());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL1045());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL1045());
		categorypanel.setStyleName(audio.getStyleName());
		resourceTypePanel.setVisible(true);
		resoureDropDownLblOpen = false;
		clearCategoryErrorMessage();
		audioResourcePanel.addStyleName("active");
		websiteResourcePanel.removeStyleName("active");
		interactiveResourcePanel.removeStyleName("active");
		textResourcePanel.removeStyleName("active");
		videoResourcePanel.removeStyleName("active");
		imageResourcePanel.removeStyleName("active");
	}

	@UiHandler("momentsOfLearningDropDownContianer")
	public void momentsOfLearningDropDownContainerClick(ClickEvent event) {
		hasClickedOnDropDwn = true;
		if (momentsOfLearningOpen1 == false) {
			momentsOfLearningPanel.setVisible(true);
			momentsOfLearningOpen1 = true;
		} else {
			momentsOfLearningPanel.setVisible(false);
			momentsOfLearningOpen1 = false;
		}
	}

	/**
	 *
	 * @function setImageThumbnail
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description
	 *
	 *
	 * 				@parm(s) :
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setImageThumbnail() {
		if (thumbnailImages.size() > 0) {
			if (activeImageIndex == 0) {
				leftArrowLbl.setVisible(false);
			} else {
				leftArrowLbl.setVisible(true);
			}
			if (thumbnailImages != null) {
				if (activeImageIndex == thumbnailImages.size()) {
					rightArrowLbl.setVisible(false);
				} else {
					rightArrowLbl.setVisible(true);
				}
				// setThumbnailImage.setUrlAndVisibleRect(thumbnailImages.get(activeImageIndex),
				// 0, 0, 80, 60);
				setThumbnailImage.getElement().setAttribute("style", "width: 80px;height: 60px;");
				setThumbnailImage.setUrl(thumbnailImages.get(activeImageIndex));
				thumbnailUrlStr = thumbnailImages.get(activeImageIndex);
			}
		}
	}

	@UiHandler("refreshLbl")
	void refreshClick(ClickEvent event) {
		String userUrlStr = urlTextBox.getText().trim();
		if (userUrlStr.indexOf("youtube") == -1) {
			activeImageIndex = 0;
			setImageThumbnail();
		}
	}

	/*
	 * @UiHandler("refreshlabel") void refreshlabelClick(ClickEvent event){
	 * String userUrlStr = urlTextBox.getText().trim(); if
	 * (userUrlStr.indexOf("youtube")==-1){ activeImageIndex=0;
	 * setImageThumbnail(); } }
	 */
	public void clearFields() {
		urlTextBox.setText("");
		titleTextBox.setText("");

		descriptionTxtAera.setText("");
		generateImageLbl.setVisible(true);
		setThumbnailImage.setUrl("");
		if (thumbnailImages != null) {
			thumbnailImages.clear();
		}
		resourceCategoryLabel.setText(i18n.GL0360());
		resourceCategoryLabel.getElement().setAttribute("alt", i18n.GL0360());
		resourceCategoryLabel.getElement().setAttribute("title", i18n.GL0360());
		categorypanel.setStyleName("");

		clearCategoryErrorMessage();
		clearTitleErrorMessage();
		clearUrlErrorMessage();
		descCharcterLimit.setVisible(false);
		loadingTextLbl.getElement().getStyle().setDisplay(Display.NONE);
		buttonsPanel.getElement().getStyle().setDisplay(Display.BLOCK);
		setVisible(true);
	}

	public void setVisible(boolean visible) {
		addResourceBtnLbl.setVisible(visible);
		addResourceBtnPanel.setVisible(visible);
	}

	private RegExp urlValidator;
	private RegExp urlPlusTldValidator;

	/**
	 *
	 * @function isValidUrl
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description
	 *
	 *
	 * 				@parm(s) : @param url @parm(s) : @param
	 *              topLevelDomainRequired @parm(s) : @return
	 *
	 * @return : boolean
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public boolean isValidUrl(String url, boolean topLevelDomainRequired) {
		int count = returnCount(url);
		if (count > 2)
			return false;
		if (urlValidator == null || urlPlusTldValidator == null) {
			urlValidator = RegExp.compile("^((ftp|http|https)://[\\w@.\\-\\_\\()]+(:\\d{1,5})?(/[\\?%&=]+)*)");

			urlPlusTldValidator = RegExp.compile("^((ftp|http|https)://[\\w@.\\-\\_\\()]+(:\\d{1,5})?(/[\\?%&=]+)*)");

		}
		return (topLevelDomainRequired ? urlPlusTldValidator : urlValidator).exec(url) != null;
	}

	/**
	 *
	 * @function returnCount
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description
	 *
	 *
	 * 				@parm(s) : @param url @parm(s) : @return
	 *
	 * @return : Integer
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public Integer returnCount(String url) {

		String string = url;
		String substring1 = "http:";
		String substring2 = "https:";
		String substring3 = "ftp:";
		String substring4 = "www";

		Integer count = 0;
		Integer idx = 0;

		while ((idx = string.indexOf(substring1, idx)) != -1) {
			idx++;
			count++;
		}

		idx = 0;
		while ((idx = string.indexOf(substring2, idx)) != -1) {
			idx++;
			count++;
		}
		idx = 0;
		while ((idx = string.indexOf(substring3, idx)) != -1) {
			idx++;
			count++;
		}

		idx = 0;
		while ((idx = string.indexOf(substring4, idx)) != -1) {
			idx++;
			count++;
		}

		return count;

	}

	/**
	 *
	 * @function getYoutubeVideoId
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description
	 *
	 *
	 * 				@parm(s) : @param youtubeUrl @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public String getYoutubeVideoId(String youtubeUrl) {

		youtubeUrl = youtubeUrl.replaceAll("feature=player_detailpage&", "");
		youtubeUrl = youtubeUrl.replaceAll("feature=player_embedded&", "");
		String pattern = "^.*((youtu.be" + "\\/)"
				+ "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
		String videoId = null;
		try {
			RegExp reg = RegExp.compile(pattern, "gi");
			MatchResult res = reg.exec(youtubeUrl);
			if (res != null) {
				videoId = res.getGroup(7);
			}
		} catch (Exception e) {
			AppClientFactory.printSevereLogger("AddWebResourceView getYoutubeVideoId:::" + e);
		}
		return videoId;

	}

	public boolean isShortenedUrl() {
		return isShortenedUrl;
	}

	public void setShortenedUrl(boolean isShortenedUrl) {
		this.isShortenedUrl = isShortenedUrl;
	}

	/**
	 * This method is to get the videoDuration
	 */
	public Integer getVideoDuration() {
		return videoDuration;
	}

	/**
	 * This method is to set the videoDuration
	 */
	public void setVideoDuration(Integer videoDuration) {
		this.videoDuration = videoDuration;
	}

	/**
	 *
	 * @fileName : AddWebResourceView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 15-Dec-2014
	 *
	 * @Author Gooru Team
	 *
	 * @Reviewer:
	 */
	public class CheckProfanityInOnBlur implements BlurHandler {
		private TextBox textBox;
		private Label label;
		private TextArea textArea;

		public CheckProfanityInOnBlur(TextBox textBox, TextArea textArea, Label label) {
			this.textBox = textBox;
			this.label = label;
			this.textArea = textArea;
		}

		@Override
		public void onBlur(BlurEvent event) {
			Map<String, String> parms = new HashMap<String, String>();
			if (textBox != null) {
				parms.put("text", textBox.getValue());
			} else {
				descCharcterLimit.setVisible(false);
				parms.put("text", textArea.getText());
			}
			AppClientFactory.getInjector().getResourceService().checkProfanity(parms,
					new SimpleAsyncCallback<Boolean>() {

						@Override
						public void onSuccess(Boolean value) {
							addResourceBtnLbl.setEnabled(true);
							addResourceBtnLbl.getElement().removeClassName("secondary");
							addResourceBtnLbl.getElement().addClassName("primary");
							if (textBox != null) {
								isHavingBadWordsInTextbox = value;
								SetStyleForProfanity.SetStyleForProfanityForTextBox(textBox, label, value);
							} else {
								isHavingBadWordsInRichText = value;
								SetStyleForProfanity.SetStyleForProfanityForTextArea(textArea, label, value);
							}

						}
					});
		}
	}

	/**
	 *
	 * @function hasValidateResource
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description
	 *
	 *
	 * 				@parm(s) : @return
	 *
	 * @return : boolean
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public boolean hasValidateResource() {
		String userUrlStr = urlTextBox.getText().trim();
		boolean isValid;
		if (userUrlStr.endsWith(".mp3")) {
			return isValid = false;
		} else {
			return isValid = true;
		}

	}






	/**
	 *
	 * @function eventTargetsPopup
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description
	 *
	 *
	 * 				@parm(s) : @param event @parm(s) : @return
	 *
	 * @return : boolean
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private boolean eventTargetsPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			try {
				return browseStandardsTooltip.getElement().isOrHasChild(Element.as(target));
			} catch (Exception ex) {
				AppClientFactory.printSevereLogger(ex.getMessage());
			}
		}
		return false;
	}

	/**
	 *
	 * @function enableGenerateBtn
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description This method is used to enable the generatefromURL button
	 *              based on the url entered.
	 *
	 *
	 * 				@parm(s) :
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void enableGenerateBtn() {
		generateFromUrlBtn.getElement().getStyle().clearColor();
		generateFromUrlBtn.getElement().removeClassName("disabled");
		generateFromUrlBtn.getElement().getStyle().setOpacity(1);
		isGenerateURL = true;
		generateFromUrlBtn.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				mandatorygenerateFromUrlLbl.setVisible(false);
			}
		});
	}

	/**
	 *
	 * @function disableGenerateBtn
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description This method is used to disable the generatefromURL button
	 *              based on the url entered.
	 *
	 *
	 * 				@parm(s) :
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void disableGenerateBtn() {
		generateFromUrlBtn.getElement().getStyle().setColor("#999");
		generateFromUrlBtn.getElement().addClassName("disabled");
		generateFromUrlBtn.getElement().getStyle().setOpacity(0.5);
		isGenerateURL = false;
		mandatorygenerateFromUrlLbl.setVisible(false);
		generateFromUrlBtn.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				mandatorygenerateFromUrlLbl.setVisible(true);
			}
		});
	}

	/**
	 *
	 * @function setAdvancedOptionsStyles
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description This method is used to set styles for
	 *              educationaluse,momentsoflearning and mediafeature based on
	 *              dropdown selection.
	 *
	 *
	 * 				@parm(s) :
	 *
	 * @return : void
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setAdvancedOptionsStyles() {
		if (resourceEducationalLabel.getText().equalsIgnoreCase(i18n.GL1684())) {
			addSetupAdvancedView.educationUseAdvancedContainer
					.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
		} else {
			addSetupAdvancedView.educationUseAdvancedContainer
					.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.educationUseAdvancedContainer
					.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
		if (resourcemomentsOfLearningLabel.getText().equalsIgnoreCase(i18n.GL1684())) {
			addSetupAdvancedView.momentsOfLearningAdvancedContainer
					.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
		} else {
			addSetupAdvancedView.momentsOfLearningAdvancedContainer
					.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.momentsOfLearningAdvancedContainer
					.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
		if (lblMediaPlaceHolder.getText().equalsIgnoreCase("Choose a Media Feature Option:")) {
			addSetupAdvancedView.mediaFeatureAdvancedContainer
					.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
		} else {
			addSetupAdvancedView.mediaFeatureAdvancedContainer
					.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.mediaFeatureAdvancedContainer
					.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
	}

	/**
	 *
	 * @function setAdvancedAccessHazardStyles
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description This method is used to set styles for accesshazard on click
	 *              of perticular panel.
	 *
	 *
	 * 				@parm(s) : @param length
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setAdvancedAccessHazardStyles(int length) {
		if (length == 0) {
			addSetupAdvancedView.accessHazardAdvancedContainer
					.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
		} else {
			addSetupAdvancedView.accessHazardAdvancedContainer
					.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.accessHazardAdvancedContainer
					.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
	}



	public void updateCenturyAdvancedSetupStyle() {
		if (centuryPanel.getWidgetCount() == 0) {
			addSetupAdvancedView.centuryAdvancedContainer
					.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
		} else {
			addSetupAdvancedView.centuryAdvancedContainer
					.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.centuryAdvancedContainer.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		}
	}

	/**
	 *
	 * @function updateMobileFriendlyAdvancedStyles
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description This method is used to set styles for MobileFriendly tags
	 *              based on the user selection(Yes/No).
	 *
	 *
	 * 				@parm(s) :
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void updateMobileFriendlyAdvancedStyles() {
		if (mobileYes.getStyleName().contains("at-OffButtonsActive")) {
			addSetupAdvancedView.mobileFreindlyAdvancedContainer
					.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			addSetupAdvancedView.mobileFreindlyAdvancedContainer
					.addStyleName(AddSetupAdvancedCBundle.INSTANCE.css().active());
		} else if (mobileNo.getStyleName().contains("at-OffButtonsActive")) {
			addSetupAdvancedView.mobileFreindlyAdvancedContainer
					.setStyleName(AddSetupAdvancedCBundle.INSTANCE.css().setupBoxes());
			/*
			 * addSetupAdvancedView.mobileFreindlyAdvancedContainer.addStyleName
			 * (AddSetupAdvancedCBundle.INSTANCE.css().active());
			 */
		}
	}

	/**
	 *
	 * @function showUrlErrorMessage
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description : This method is used to show UrlErrorMessage.
	 *
	 *
	 * 				@parm(s) : @param message
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void showUrlErrorMessage(String message) {
		mandatoryUrlLbl.setText(message);
		StringUtil.setAttributes(mandatoryUrlLbl.getElement(), "lblMandatoryUrlLbl", message, message);
		urlTextBox.getElement().getStyle().setBorderColor("orange");
		urlTextBox.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		urlTextBox.getElement().getStyle().setBorderWidth(1, Unit.PX);
		mandatoryUrlLbl.setVisible(true);
	}

	/**
	 *
	 * @function clearUrlErrorMessage
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description This method is used to clear the URL Error Message.
	 *
	 *
	 * 				@parm(s) :
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void clearUrlErrorMessage() {
		mandatoryUrlLbl.setVisible(false);
		urlTextBox.getElement().getStyle().clearBorderColor();
		urlTextBox.getElement().getStyle().clearBorderStyle();
		urlTextBox.getElement().getStyle().clearBorderWidth();
	}

	/**
	 *
	 * @function showTitleErrorMessage
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description : This method is used to show the error message for title
	 *
	 *
	 * 				@parm(s) : @param message
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void showTitleErrorMessage(String message) {
		mandatoryTitleLbl.setText(message);
		StringUtil.setAttributes(mandatoryTitleLbl.getElement(), "lblMandatoryTitleLbl", message, message);
		titleTextBox.getElement().getStyle().setBorderColor("orange");
		titleTextBox.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		titleTextBox.getElement().getStyle().setBorderWidth(1, Unit.PX);
		mandatoryTitleLbl.setVisible(true);
	}

	/**
	 *
	 * @function clearTitleErrorMessage
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description This method is used to clear the error message for title
	 *
	 *
	 * 				@parm(s) :
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void clearTitleErrorMessage() {
		mandatoryTitleLbl.setVisible(false);
		titleTextBox.getElement().getStyle().clearBorderColor();
		titleTextBox.getElement().getStyle().clearBorderStyle();
		titleTextBox.getElement().getStyle().clearBorderWidth();
	}

	/**
	 *
	 * @function showCategoryErrorMessage
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description This method is used to show the error message for category
	 *
	 *
	 * 				@parm(s) : @param message
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void showCategoryErrorMessage(String message) {
		mandatoryCategoryLbl.setText(message);
		StringUtil.setAttributes(mandatoryTitleLbl.getElement(), "lblMandatoryCategoryLbl", message, message);
		panelCategoryInputDiv.getElement().getStyle().setBorderColor("orange");
		panelCategoryInputDiv.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelCategoryInputDiv.getElement().getStyle().setBorderWidth(1, Unit.PX);
		panelCategoryInputDiv.getElement().setId("panelCategoryInputDiv");
		mandatoryCategoryLbl.setVisible(true);
	}

	/**
	 *
	 * @function clearCategoryErrorMessage
	 *
	 * @created_date : 15-Dec-2014
	 *
	 * @description This method is used to clear the error message for category
	 *
	 *
	 * 				@parm(s) :
	 *
	 * @return : void
	 *
	 * @throws :
	 *             <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void clearCategoryErrorMessage() {
		mandatoryCategoryLbl.setVisible(false);
		panelCategoryInputDiv.getElement().getStyle().clearBorderColor();
		panelCategoryInputDiv.getElement().getStyle().clearBorderStyle();
		panelCategoryInputDiv.getElement().getStyle().clearBorderWidth();
	}

	private class checkAvailableClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == websiteResourcePanel) {
				setWebsiteCategory();
			} else if (event.getSource() == videoResourcePanel) {
				setVideoCategory();
			} else if (event.getSource() == interactiveResourcePanel) {
				setInteractiveCategory();
			} else if (event.getSource() == imageResourcePanel) {
				setImageCategory();
			} else if (event.getSource() == textResourcePanel) {
				setTextCategory();
			} else if (event.getSource() == audioResourcePanel) {
				setAudioCategory();
			}

		}

	}

	public void clearCategorySelections() {
		websiteResourcePanel.removeStyleName("active");
		interactiveResourcePanel.removeStyleName("active");
		audioResourcePanel.removeStyleName("active");
		textResourcePanel.removeStyleName("active");
		videoResourcePanel.removeStyleName("active");
		imageResourcePanel.removeStyleName("active");
	}

	/**
	 * This will handle the click event on the browser century
	 *
	 * @param e
	 */
	@UiHandler("browseCentury")
	public void onClickOfBrowseCentury(ClickEvent e) {
		centuryPopup.clear();
		centuryPresenterWidget.setAddResourceData(centurySelectedValues);
		centuryPopup.add(centuryPresenterWidget.getWidget());
		centuryPopup.show();
		centuryPopup.center();
		centuryPopup.getElement().getStyle().setZIndex(999999);
	}

	public void setEducationUse() {
		AppClientFactory.getInjector().getResourceService()
				.getEducationalUseList(new AsyncCallback<List<ListValuesDo>>() {

					@Override
					public void onSuccess(List<ListValuesDo> result) {
						// TODO Auto-generated method stub
						setData(result, "education");
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
	}

	public void setData(List<ListValuesDo> listValuesDos, String type) {

		if (type.equalsIgnoreCase("education")) {
			educationalUseOptionsContainer.clear();
		} else if (type.equalsIgnoreCase("mLearning")) {
			momentsOfLearningPanel.clear();
		}
		for (ListValuesDo listValuesDo : listValuesDos) {
			LiPanel liPanel = new LiPanel();
			Anchor anchor = new Anchor();
			anchor.setStyleName("educationUseText");
			anchor.setText(listValuesDo.getName());
			anchor.getElement().setId(listValuesDo.getId() + "");
			liPanel.add(anchor);
			if (type.equalsIgnoreCase("education")) {

				educationalUseOptionsContainer.add(liPanel);
				liPanel.addDomHandler(new EducationClickHandler(liPanel, anchor), ClickEvent.getType());
			} else if (type.equalsIgnoreCase("mLearning")) {
				liPanel.addDomHandler(new MomentOfLearingClickHandler(liPanel, anchor), ClickEvent.getType());
				momentsOfLearningPanel.add(liPanel);
			}

		}
	}

	public void setMomentOfLeaning() {
		AppClientFactory.getInjector().getResourceService()
				.getMomentOfLearning(new AsyncCallback<List<ListValuesDo>>() {

					@Override
					public void onSuccess(List<ListValuesDo> result) {
						// TODO Auto-generated method stub
						setData(result, "mLearning");
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
	}

	public class EducationClickHandler implements ClickHandler {
		Anchor anchor;
		LiPanel liPanel;

		public EducationClickHandler(LiPanel liPanel, Anchor anchor) {
			// TODO Auto-generated constructor stub
			this.anchor = anchor;
			this.liPanel = liPanel;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			resetSelection(educationalUseOptionsContainer);
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

	public class MomentOfLearingClickHandler implements ClickHandler {
		Anchor anchor;
		LiPanel liPanel;

		public MomentOfLearingClickHandler(LiPanel liPanel, Anchor anchor) {
			// TODO Auto-generated constructor stub
			this.anchor = anchor;
			this.liPanel = liPanel;
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

	public void resetSelection(UlPanel ulPanel) {
		int count = ulPanel.getWidgetCount();
		for (int i = 0; i < count; i++) {
			LiPanel liPanel = (LiPanel) ulPanel.getWidget(i);
			liPanel.removeStyleName("active");
		}
	}

	public void getMediaFeatures() {
		AppClientFactory.getInjector().getResourceService().getMediaFeature(new AsyncCallback<List<ListValuesDo>>() {

			@Override
			public void onSuccess(List<ListValuesDo> result) {
				// TODO Auto-generated method stub

				for (ListValuesDo listValuesDo : result) {
					String mediaTitleVal = listValuesDo.getName();
					final Label titleLabel = new Label(mediaTitleVal);
					titleLabel.setStyleName(CollectionAssignCBundle.INSTANCE.css().classpageTitleText());
					titleLabel.getElement().setAttribute("id", listValuesDo.getId() + "");
					titleLabel.setText(listValuesDo.getName());
					// Set Click event for title
					titleLabel.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							String optionSelected = titleLabel.getText();
							lblMediaPlaceHolder.setText(optionSelected);
							spanelMediaFeaturePanel.setVisible(false);
							lblMediaPlaceHolder.getElement().setId(titleLabel.getElement().getId());
							lblMediaPlaceHolder
									.setStyleName(CollectionAssignCBundle.INSTANCE.css().selectedClasspageText());
							lblMediaPlaceHolder.setText(optionSelected);
							setAdvancedOptionsStyles();
						}
					});
					htmlMediaFeatureListContainer.add(titleLabel);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void setHazardData(List<ListValuesDo> hazards) {
		if (hazards != null) {
			hazardContainer.clear();
			if (hazards != null) {
				for (ListValuesDo do1 : hazards) {
					final Label label = new Label();
					label.setText(do1.getName());
					label.setStyleName("accessHazardLbl");
					label.getElement().setId(do1.getId() + "");
					label.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							if (label.getStyleName().toString().contains("at-select")) {
								label.getElement().removeClassName("at-select");
							} else {
								label.getElement().addClassName("at-select");
							}

						}
					});
					hazardContainer.add(label);
				}

			}
		}

	}

	public void setHazard() {
		AppClientFactory.getInjector().getResourceService().getAccessHazards(new AsyncCallback<List<ListValuesDo>>() {

			@Override
			public void onSuccess(List<ListValuesDo> result) {
				// TODO Auto-generated method stub
				if (result != null) {
					setHazardData(result);
				}

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
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

}