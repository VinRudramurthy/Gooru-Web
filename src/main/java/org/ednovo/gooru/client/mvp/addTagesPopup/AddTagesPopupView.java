/*******************************************************************************
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

package org.ednovo.gooru.client.mvp.addTagesPopup;

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
import org.ednovo.gooru.application.shared.model.content.ResourceTagsDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.search.CenturySkills.AddCenturyPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionCBundle;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.assign.CollectionAssignCBundle;
import org.ednovo.gooru.client.uc.AppMultiWordSuggestOracle;
import org.ednovo.gooru.client.uc.AppSuggestBox;
import org.ednovo.gooru.client.uc.CloseLabel;
import org.ednovo.gooru.client.uc.CloseLabelCentury;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.StandardsPreferenceOrganizeToolTip;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.ScrollPopupUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : AddTagesPopupView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public abstract class AddTagesPopupView extends PopupPanel implements SelectionHandler<SuggestOracle.Suggestion>{

	public PopupPanel appPopUp;

	private static AddTagesPopupViewUiBinder uiBinder = GWT
			.create(AddTagesPopupViewUiBinder.class);

	interface AddTagesPopupViewUiBinder extends
			UiBinder<Widget, AddTagesPopupView> {
	}
	public MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField Anchor  kindergarden, level1, level2, level3, level4, level5, level6, level7, level8, level9, level10, level11, level12;

	@UiField Label headerEducationalUse;

	@UiField Anchor  handout, homework, game, presentation, refMaterial, quiz, currPlan, lessonPlan, unitPlan, projectPlan, reading, textbook, article, book, activity;

	@UiField Button cancelBtn,addTagsBtn,mobileYes,mobileNo,centbrowseBtn;

	@UiField UlPanel htmlMediaFeatureListContainer;

	@UiField Label lexileHeader,AdsHeader, mediaLabel;
	@UiField Anchor lblMediaPlaceHolder;

	@UiField Label standardMaxMsg,standardsDefaultText,centuaryMaxMsg,centuaryDefaultText;

	@UiField Anchor noAds,modAds,aggreAds;

	@UiField ScrollPanel spanelMediaFeaturePanel;

	@UiField Label accessHazard;
	@UiField Anchor flashingHazard,motionSimulationHazard,soundHazard;

	@UiField InlineLabel addTagesTitle,moblieFriendly;
	@UiField PPanel popupContentText;

	@UiField HTMLPanel addTagesContent,standardsCont;

	@UiField UlPanel ulSelectedItems;

	List<String> tagListGlobal = new ArrayList<String>();

	@UiField
	HTMLEventPanel btnStandardsBrowse;

	@UiField
	UlPanel standardsDropListValues;

	@UiField(provided = true)
	AppSuggestBox standardSgstBox;

	@UiField(provided = true)
	AppSuggestBox centurySgstBox;

	PopupPanel centuryPopup=new PopupPanel();

	Map<Long, String> centurySelectedValues=new HashMap<Long, String>();

	private boolean isCCSSAvailable = false;
	private boolean isNGSSAvailable = false;
	private boolean isTEKSAvailable = false;
	private boolean isCAAvailable = false;

	List<String> selectedValues=new ArrayList<>();

	List<LiPanelWithClose> collectionLiPanelWithCloseArray = new ArrayList<>();

	String[] standardsTypesArray = new String[]{i18n.GL3379(),i18n.GL3322(),i18n.GL3323(),i18n.GL3324(),i18n.GL3325(),i18n.GL3321()};


	@UiField FlowPanel standardContainer,centuaryContainer,centuryPanel;
	private AppMultiWordSuggestOracle standardSuggestOracle;
	private AppMultiWordSuggestOracle centurySuggestOracle;
	private SearchDo<CodeDo> standardSearchDo = new SearchDo<CodeDo>();
	private SearchDo<StandardFo> centurySearchDo = new SearchDo<StandardFo>();
	private static final String FLT_CODE_ID = "id";
	List<String> standardPreflist;
	private Map<String, String> standardCodesMap = new HashMap<String, String>();
	private Map<String, String> centuryCodesMap = new HashMap<String, String>();
	String courseCode="";
	boolean isEditResource=true;
	ArrayList<String> standardsDo=new ArrayList<String>();
	ArrayList<String> centuryDo=new ArrayList<String>();
	ArrayList<String> centuryTempDo=new ArrayList<String>();

	public FolderDo courseObjG;

	Set<CodeDo> deletedStandardsDo=new HashSet<CodeDo>();
	final StandardsPreferenceOrganizeToolTip standardsPreferenceOrganizeToolTip=new StandardsPreferenceOrganizeToolTip();
	private static final String USER_META_ACTIVE_FLAG = "0";
	String CENTURYSKILLS="21st Century Skills";

	boolean isCancelclicked=false;
	private boolean isClickedOnDropDwn=false;
	String mediaFeatureStr = i18n.GL1767();
	String resourceId=null;
	Boolean isIpad,isAndriod,isWinDskp;

	public AddTagesPopupView(final String resourceId) {
		super(false);
		initializeAutoSuggestedBox();
		add(uiBinder.createAndBindUi(this));
		this.resourceId=resourceId;
		this.setGlassEnabled(true);
		lblMediaPlaceHolder.getElement().setAttribute("data-toggle","dropdown");

		if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().contains("resource-search")||AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().contains("collection-search")||AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().contains("mycollections")){

		}else{
			this.getElement().setAttribute("style", "z-index:99999;");
		}

		this.center();


		standardMaxMsg.getElement().setId("lblStandardMaxMsg");
		standardMaxMsg.getElement().setAttribute("alt","");
		standardMaxMsg.getElement().setAttribute("title","");

		addTagesTitle.setText(i18n.GL1795());
		addTagesTitle.getElement().setId("spnAddTagesTitle");
		addTagesTitle.getElement().setAttribute("alt",i18n.GL1795());
		addTagesTitle.getElement().setAttribute("title",i18n.GL1795());

		popupContentText.setText(i18n.GL1838());
		popupContentText.getElement().setId("spnpopupContentText");
		popupContentText.getElement().setAttribute("alt",i18n.GL1838());
		popupContentText.getElement().setAttribute("title",i18n.GL1838());


		headerEducationalUse.setText(i18n.GL1664());
		headerEducationalUse.getElement().setId("lblHeaderEducationalUse");
		headerEducationalUse.getElement().setAttribute("alt",i18n.GL1664());
		headerEducationalUse.getElement().setAttribute("title",i18n.GL1664());


		activity.setText(i18n.GL1665());
		activity.getElement().setId("lblActivity");
		activity.getElement().setAttribute("alt",i18n.GL1665());
		activity.getElement().setAttribute("title",i18n.GL1665());

		handout.setText(i18n.GL0907());
		handout.getElement().setId("lblHandout");
		handout.getElement().setAttribute("alt",i18n.GL0907());
		handout.getElement().setAttribute("title",i18n.GL0907());

		homework.setText(i18n.GL1666());
		homework.getElement().setId("lblHomework");
		homework.getElement().setAttribute("alt",i18n.GL1666());
		homework.getElement().setAttribute("title",i18n.GL1666());

		game.setText(i18n.GL1667());
		game.getElement().setId("lblGame");
		game.getElement().setAttribute("alt",i18n.GL1667());
		game.getElement().setAttribute("title",i18n.GL1667());

		presentation.setText(i18n.GL1668());
		presentation.getElement().setId("lblPresentation");
		presentation.getElement().setAttribute("alt",i18n.GL1668());
		presentation.getElement().setAttribute("title",i18n.GL1668());

		refMaterial.setText(i18n.GL1669());
		refMaterial.getElement().setId("lblRefMaterial");
		refMaterial.getElement().setAttribute("alt",i18n.GL1669());
		refMaterial.getElement().setAttribute("title",i18n.GL1669());

		quiz.setText(i18n.GL1670());
		quiz.getElement().setId("lblQuiz");
		quiz.getElement().setAttribute("alt",i18n.GL1670());
		quiz.getElement().setAttribute("title",i18n.GL1670());

		currPlan.setText(i18n.GL1671());
		currPlan.getElement().setId("lblCurrPlan");
		currPlan.getElement().setAttribute("alt",i18n.GL1671());
		currPlan.getElement().setAttribute("title",i18n.GL1671());

		lessonPlan.setText(i18n.GL1672());
		lessonPlan.getElement().setId("lblLessonPlan");
		lessonPlan.getElement().setAttribute("alt",i18n.GL1672());
		lessonPlan.getElement().setAttribute("title",i18n.GL1672());

		unitPlan.setText(i18n.GL1673());
		unitPlan.getElement().setId("lblUnitPlan");
		unitPlan.getElement().setAttribute("alt",i18n.GL1673());
		unitPlan.getElement().setAttribute("title",i18n.GL1673());

		projectPlan.setText(i18n.GL1674());
		projectPlan.getElement().setId("lblProjectPlan");
		projectPlan.getElement().setAttribute("alt",i18n.GL1674());
		projectPlan.getElement().setAttribute("title",i18n.GL1674());

		reading.setText(i18n.GL1675());
		reading.getElement().setId("lblReading");
		reading.getElement().setAttribute("alt",i18n.GL1675());
		reading.getElement().setAttribute("title",i18n.GL1675());

		textbook.setText(i18n.GL0909());
		textbook.getElement().setId("lblTextbook");
		textbook.getElement().setAttribute("alt",i18n.GL0909());
		textbook.getElement().setAttribute("title",i18n.GL0909());

		article.setText(i18n.GL1676());
		article.getElement().setId("lblArticle");
		article.getElement().setAttribute("alt",i18n.GL1676());
		article.getElement().setAttribute("title",i18n.GL1676());

		book.setText(i18n.GL1677());
		book.getElement().setId("lblBook");
		book.getElement().setAttribute("alt",i18n.GL1677());
		book.getElement().setAttribute("title",i18n.GL1677());

		lexileHeader.setText(i18n.GL1798());
		lexileHeader.getElement().setId("lblReadingLevel");
		lexileHeader.getElement().setAttribute("alt",i18n.GL1798());
		lexileHeader.getElement().setAttribute("title",i18n.GL1798());

		kindergarden.setText(i18n.GL1799());
		kindergarden.getElement().setId("lblKindergarten");
		kindergarden.getElement().setAttribute("alt",i18n.GL1799());
		kindergarden.getElement().setAttribute("title",i18n.GL1799());

		moblieFriendly.setText(i18n.GL1811());
		moblieFriendly.getElement().setId("spnMobileFriendly");
		moblieFriendly.getElement().setAttribute("alt",i18n.GL1811());
		moblieFriendly.getElement().setAttribute("title",i18n.GL1811());

		level1.setText(i18n.GL_GRR_NUMERIC_ONE());
		level1.getElement().setId("lblOne");
		level1.getElement().setAttribute("alt",i18n.GL_GRR_NUMERIC_ONE());
		level1.getElement().setAttribute("title",i18n.GL_GRR_NUMERIC_ONE());

		level2.setText(i18n.GL_GRR_NUMERIC_TWO());
		level2.getElement().setId("lblTwo");
		level2.getElement().setAttribute("alt",i18n.GL_GRR_NUMERIC_TWO());
		level2.getElement().setAttribute("title",i18n.GL_GRR_NUMERIC_TWO());

		level3.setText(i18n.GL_GRR_NUMERIC_THREE());
		level3.getElement().setId("lblThree");
		level3.getElement().setAttribute("alt",i18n.GL_GRR_NUMERIC_THREE());
		level3.getElement().setAttribute("title",i18n.GL_GRR_NUMERIC_THREE());

		level4.setText(i18n.GL_GRR_NUMERIC_FOUR());
		level4.getElement().setId("lblFour");
		level4.getElement().setAttribute("alt",i18n.GL_GRR_NUMERIC_FOUR());
		level4.getElement().setAttribute("title",i18n.GL_GRR_NUMERIC_FOUR());

		level5.setText(i18n.GL_GRR_NUMERIC_FIVE());
		level5.getElement().setId("lblFive");
		level5.getElement().setAttribute("alt",i18n.GL_GRR_NUMERIC_FIVE());
		level5.getElement().setAttribute("title",i18n.GL_GRR_NUMERIC_FIVE());

		level6.setText(i18n.GL_GRR_NUMERIC_SIX());
		level6.getElement().setId("lblSix");
		level6.getElement().setAttribute("alt",i18n.GL_GRR_NUMERIC_SIX());
		level6.getElement().setAttribute("title",i18n.GL_GRR_NUMERIC_SIX());

		level7.setText(i18n.GL_GRR_NUMERIC_SEVEN());
		level7.getElement().setId("lblSeven");
		level7.getElement().setAttribute("alt",i18n.GL_GRR_NUMERIC_SEVEN());
		level7.getElement().setAttribute("title",i18n.GL_GRR_NUMERIC_SEVEN());

		level8.setText(i18n.GL_GRR_NUMERIC_EIGHT());
		level8.getElement().setId("lblEight");
		level8.getElement().setAttribute("alt",i18n.GL_GRR_NUMERIC_EIGHT());
		level8.getElement().setAttribute("title",i18n.GL_GRR_NUMERIC_EIGHT());

		level9.setText(i18n.GL_GRR_NUMERIC_NINE());
		level9.getElement().setId("lblNine");
		level9.getElement().setAttribute("alt",i18n.GL_GRR_NUMERIC_NINE());
		level9.getElement().setAttribute("title",i18n.GL_GRR_NUMERIC_NINE());

		level10.setText(i18n.GL_GRR_NUMERIC_TEN());
		level10.getElement().setId("lblNine");
		level10.getElement().setAttribute("alt",i18n.GL_GRR_NUMERIC_TEN());
		level10.getElement().setAttribute("title",i18n.GL_GRR_NUMERIC_TEN());

		level11.setText(i18n.GL_GRR_NUMERIC_ELEVEN());
		level11.getElement().setId("lblEleven");
		level11.getElement().setAttribute("alt",i18n.GL_GRR_NUMERIC_ELEVEN());
		level11.getElement().setAttribute("title",i18n.GL_GRR_NUMERIC_ELEVEN());

		level12.setText(i18n.GL_GRR_NUMERIC_TWELVE());
		level12.getElement().setId("lblTwelve");
		level12.getElement().setAttribute("alt",i18n.GL_GRR_NUMERIC_TWELVE());
		level12.getElement().setAttribute("title",i18n.GL_GRR_NUMERIC_TWELVE());

		AdsHeader.setText(i18n.GL1800());
		AdsHeader.getElement().setId("lblAds");
		AdsHeader.getElement().setAttribute("alt",i18n.GL1800());
		AdsHeader.getElement().setAttribute("title",i18n.GL1800());

		noAds.setText(i18n.GL1801());
		noAds.getElement().setId("lblNoAdvertisement");
		noAds.getElement().setAttribute("alt",i18n.GL1801());
		noAds.getElement().setAttribute("title",i18n.GL1801());

		modAds.setText(i18n.GL1802());
		modAds.getElement().setId("lblModerateAdvertisements");
		modAds.getElement().setAttribute("alt",i18n.GL1802());
		modAds.getElement().setAttribute("title",i18n.GL1802());

		aggreAds.setText(i18n.GL1803());
		aggreAds.getElement().setId("lblAggressiveAdvertisements");
		aggreAds.getElement().setAttribute("alt",i18n.GL1803());
		aggreAds.getElement().setAttribute("title",i18n.GL1803());

		accessHazard.setText(i18n.GL1804());
		accessHazard.getElement().setId("lblAccessHazard");
		accessHazard.getElement().setAttribute("alt",i18n.GL1804());
		accessHazard.getElement().setAttribute("title",i18n.GL1804());

		flashingHazard.setText(i18n.GL3110());
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
		soundHazard.getElement().setAttribute("title",i18n.GL3112());

		cancelBtn.setText(i18n.GL0142());
		cancelBtn.getElement().setId("btnCancel");
		cancelBtn.getElement().setAttribute("alt",i18n.GL0142());
		cancelBtn.getElement().setAttribute("title",i18n.GL0142());

		addTagsBtn.setText(i18n.GL3196());
		addTagsBtn.getElement().setId("btnAddTags");
		addTagsBtn.getElement().setAttribute("alt",i18n.GL1795());
		addTagsBtn.getElement().setAttribute("title",i18n.GL1795());

		mobileYes.setText(i18n.GL_GRR_YES());
		mobileYes.getElement().setId("btnYes");
		mobileYes.getElement().setAttribute("alt",i18n.GL_GRR_YES());
		mobileYes.getElement().setAttribute("title",i18n.GL_GRR_YES());

		mobileNo.setText(i18n.GL1735());
		mobileNo.getElement().setId("btnNo");
		mobileNo.getElement().setAttribute("alt",i18n.GL1735());
		mobileNo.getElement().setAttribute("title",i18n.GL1735());


		spanelMediaFeaturePanel.setVisible(false);
		spanelMediaFeaturePanel.getElement().setId("sbMediaFeaturesPanel");
		spanelMediaFeaturePanel.getElement().setAttribute("alt","");
		spanelMediaFeaturePanel.getElement().setAttribute("title","");


		standardsDefaultText.setText(i18n.GL1682());
		standardsDefaultText.getElement().setId("lblStandards");
		standardsDefaultText.getElement().setAttribute("alt",i18n.GL1682());
		standardsDefaultText.getElement().setAttribute("title",i18n.GL1682());

		centuaryDefaultText.setText(i18n.GL3199());
		StringUtil.setAttributes(centuaryDefaultText.getElement(), i18n.GL3199(), i18n.GL3199(), i18n.GL3199());

		CollectionAssignCBundle.INSTANCE.css().ensureInjected();


		btnStandardsBrowse.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (!standardsDropListValues.getElement().getAttribute("style").equalsIgnoreCase("display:block;top:0;left:19.5em;color:#515151;")) {
					standardsDropListValues.getElement().setAttribute("style", "display:block;top:0;left:19.5em;color:#515151;");
				} else {
					standardsDropListValues.getElement().removeAttribute("style");
				}
			}
		});
		standardsCont.getElement().setAttribute("style", "position:relative;");
		standardContainer.getElement().setId("standardsContainerBswn");
		mediaLabel.setText(i18n.GL1706());
		mediaLabel.getElement().setId("lblMediaFeature");
		mediaLabel.getElement().setAttribute("alt",i18n.GL1706());
		mediaLabel.getElement().setAttribute("title",i18n.GL1706());

		lblMediaPlaceHolder.setHTML(i18n.GL3051()+i18n.GL_SPL_SEMICOLON()+"<span class=\"caret caretStyle\"></span>");

		lblMediaPlaceHolder.getElement().setId("phMediaFeature");
		lblMediaPlaceHolder.getElement().setAttribute("alt",i18n.GL3051());
		lblMediaPlaceHolder.getElement().setAttribute("title",i18n.GL3051());


		htmlMediaFeatureListContainer.getElement().setId("pnlMediaFeaturesList");
		htmlMediaFeatureListContainer.getElement().setAttribute("alt","");
		htmlMediaFeatureListContainer.getElement().setAttribute("title","");

		centbrowseBtn.getElement().setAttribute("style", "margin-left: 12px;");


		lblMediaPlaceHolder.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				OpenMediaFeatureDropdown();
			}
		});
		getTagsServiceRequest(resourceId);


		List<String> mediaFeatureList = Arrays.asList(mediaFeatureStr.split(","));
		for(int n=0; n<mediaFeatureList.size(); n++)
		{
				String mediaTitleVal = mediaFeatureList.get(n);

				final Label titleLabel = new Label(mediaTitleVal);
				LiPanel liPanel=new LiPanel();
				titleLabel.setStyleName(CollectionAssignCBundle.INSTANCE.css().classpageTitleText());
				titleLabel.getElement().setAttribute("id", mediaTitleVal);
				//Set Click event for title
				titleLabel.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						String optionSelected = titleLabel.getElement().getId();

						spanelMediaFeaturePanel.setVisible(false);
						lblMediaPlaceHolder.getElement().setId(titleLabel.getElement().getId());
						lblMediaPlaceHolder.setHTML(optionSelected+"<span class=\"caret caretStyle\"/>");
					}
				});
				liPanel.add(titleLabel);
				htmlMediaFeatureListContainer.add(liPanel);
		}

		ClickHandler tagHandler= new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(!isClickedOnDropDwn){
					spanelMediaFeaturePanel.setVisible(false);
				}else{
					isClickedOnDropDwn=false;
				}
			}
		};


		RootPanel.get().addDomHandler(tagHandler, ClickEvent.getType());
		ScrollPopupUtil.ScrollPopupUtilWidget(addTagesContent,true);
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
				for(int i=0;i<centuryDo.size();i++){
					if(centuryDo.get(i).toString().equalsIgnoreCase(centuryCode)){
						centurySelectedValues.remove(i);
						centuryDo.remove(centuryDo.get(i).toString());
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
	public void hideCenturyPopup(){
		centuryPopup.hide();
	}
	/**
	 *
	 * @function initializeAutoSuggestedBox
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public void initializeAutoSuggestedBox(){
		standardSuggestOracle = new AppMultiWordSuggestOracle(true);
		centurySuggestOracle= new AppMultiWordSuggestOracle(true);
		standardSearchDo.setPageSize(10);
		standardSgstBox = new AppSuggestBox(standardSuggestOracle) {
			@Override
			public void keyAction(String text,KeyUpEvent event) {
				text=text.toUpperCase();
				standardsPreferenceOrganizeToolTip.hide();
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
					if(standardsPrefDisplayPopup){
						standardsPreferenceOrganizeToolTip.hide();
						AppClientFactory.getInjector().getSearchService().getSuggestStandardByFilterCourseId(standardSearchDo, new SimpleAsyncCallback<SearchDo<CodeDo>>() {
							@Override
							public void onSuccess(SearchDo<CodeDo> result) {
								setStandardSuggestions(result);
							}
						});

						standardSgstBox.showSuggestionList();
						standardSgstBox.getElement().setAttribute("style", "z-index: 999999;");
						}
					else{
						standardSgstBox.hideSuggestionList();
						standardSuggestOracle.clear();
						standardsPreferenceOrganizeToolTip.show();
						standardsPreferenceOrganizeToolTip.setPopupPosition(standardSgstBox.getAbsoluteLeft()+3, standardSgstBox.getAbsoluteTop()+33);
						standardsPreferenceOrganizeToolTip.getElement().getStyle().setZIndex(1111);
						//standardSuggestOracle.add(i18n.GL1613);
					}
					}
			}
			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler) {
				return null;
			}
		};

		standardSgstBox.getElement().setId("tbautoStandardSgstBox");
		standardSgstBox.getElement().setAttribute("alt","");
		standardSgstBox.getElement().setAttribute("title","");

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
			public void keyAction(String text, KeyUpEvent event) {
				text=text.toUpperCase();
				//standardsPreferenceOrganizeToolTip.hide();
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

			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler) {
				// TODO Auto-generated method stub
				return null;
			}
		};

		centurySgstBox.getTextBox().getElement().setAttribute("placeholder", i18n.GL3122_1());

		BlurHandler blurHandlerCentury=new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if(standardsPreferenceOrganizeToolTip.isShowing()){
				//standardsPreferenceOrganizeToolTip.hide();
					//errorContainer.setVisible(false);
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
				//updateCenturyAdvancedSetupStyle();

			}
		});
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


	public void addCentury(String centuryTag, String id) {
			if (centuryTag != null && !centuryTag.isEmpty()) {
				centuryTag = centuryTag.trim();
				centurySelectedValues.put(Long.parseLong(id), centuryTag);
				//String codeIdVal = getCodeIdByCodeCentury(centurySgstBox.getValue(), centurySearchDo.getSearchResults());
				centuryDo.add(centuryTag);
				centuryPanel.add(create21CenturyLabel(centuryTag, id, centuryCodesMap.get(centuryTag)));
			}
	}
	public void standardMaxShow() {
		standardSgstBox.addStyleName(CollectionCBundle.INSTANCE.css().standardTxtBox());
		standardMaxMsg.setStyleName(CollectionCBundle.INSTANCE.css().standardMax());
		/*standardsPanel.addStyleName(CollectionCBundle.INSTANCE.css().floatLeftNeeded());*/
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
				for(int i=0;i<standardsDo.size();i++){
					if(standardsDo.get(i).toString().equalsIgnoreCase(standardCode)){
						standardsDo.remove(standardsDo.get(i).toString());
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
	@UiHandler("kindergarden")
	public void onKindergardenClick(ClickEvent click){
		if(kindergarden.getStyleName().toString().contains("selected"))
		{
			kindergarden.getElement().removeClassName("at-selected");
		}
		else
		{
			kindergarden.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("level1")
	public void onLevel1Click(ClickEvent click){
		if(level1.getStyleName().toString().contains("selected"))
		{
			level1.getElement().removeClassName("at-selected");
		}
		else
		{
			level1.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("level2")
	public void onLevel2Click(ClickEvent click){
		if(level2.getStyleName().toString().contains("selected"))
		{
			level2.getElement().removeClassName("at-selected");
		}
		else
		{
			level2.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("level3")
	public void onLevel3Click(ClickEvent click){
		if(level3.getStyleName().toString().contains("selected"))
		{
			level3.getElement().removeClassName("at-selected");
		}
		else
		{
			level3.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("level4")
	public void onLevel4Click(ClickEvent click){
		if(level4.getStyleName().toString().contains("selected"))
		{
			level4.getElement().removeClassName("at-selected");
		}
		else
		{
			level4.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("level5")
	public void onLevel5Click(ClickEvent click){
		if(level5.getStyleName().toString().contains("selected"))
		{
			level5.getElement().removeClassName("at-selected");
		}
		else
		{
			level5.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("level6")
	public void onLevel6Click(ClickEvent click){
		if(level6.getStyleName().toString().contains("selected"))
		{
			level6.getElement().removeClassName("at-selected");
		}
		else
		{
			level6.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("level7")
	public void onLevel7Click(ClickEvent click){
		if(level7.getStyleName().toString().contains("selected"))
		{
			level7.getElement().removeClassName("at-selected");
		}
		else
		{
			level7.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("level8")
	public void onLevel8Click(ClickEvent click){
		if(level8.getStyleName().toString().contains("selected"))
		{
			level8.getElement().removeClassName("at-selected");
		}
		else
		{
			level8.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("level9")
	public void onLevel9Click(ClickEvent click){
		if(level9.getStyleName().toString().contains("selected"))
		{
			level9.getElement().removeClassName("at-selected");
		}
		else
		{
			level9.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("level10")
	public void onLevel10Click(ClickEvent click){
		if(level10.getStyleName().toString().contains("selected"))
		{
			level10.getElement().removeClassName("at-selected");
		}
		else
		{
			level10.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("level11")
	public void onLevel11Click(ClickEvent click){
		if(level11.getStyleName().toString().contains("selected"))
		{
			level11.getElement().removeClassName("at-selected");
		}
		else
		{
			level11.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("level12")
	public void onLevel12Click(ClickEvent click){
		if(level12.getStyleName().toString().contains("selected"))
		{
			level12.getElement().removeClassName("at-selected");
		}
		else
		{
			level12.getElement().addClassName("at-selected");
		}
	}
	/**
	 *
	 * @function removeClassNameForAllEducationalUse
	 *
	 * @created_date : 06-Dec-2014
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
	public void removeClassNameForAllEducationalUse(){
		 activity.getElement().removeClassName("at-selected");
		 handout.getElement().removeClassName("at-selected");
		 homework.getElement().removeClassName("at-selected");
		 game.getElement().removeClassName("at-selected");
		 presentation.getElement().removeClassName("at-selected");
		 refMaterial.getElement().removeClassName("at-selected");
		 quiz.getElement().removeClassName("at-selected");
		 currPlan.getElement().removeClassName("at-selected");
		 lessonPlan.getElement().removeClassName("at-selected");
		 unitPlan.getElement().removeClassName("at-selected");
		 projectPlan.getElement().removeClassName("at-selected");
		 reading.getElement().removeClassName("at-selected");
		 textbook.getElement().removeClassName("at-selected");
		 article.getElement().removeClassName("at-selected");
		 book.getElement().removeClassName("at-selected");
	}

	@UiHandler("activity")
	public void onactivityClick(ClickEvent click){

		if(activity.getStyleName().toString().contains("selected"))
		{
			activity.getElement().removeClassName("at-selected");
		}
		else
		{
			activity.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("handout")
	public void onhandoutClick(ClickEvent click){
		if(handout.getStyleName().toString().contains("selected"))
		{
			handout.getElement().removeClassName("at-selected");
		}
		else
		{
			handout.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("homework")
	public void onhomeworkClick(ClickEvent click){
		if(homework.getStyleName().toString().contains("selected"))
		{
			homework.getElement().removeClassName("at-selected");
		}
		else
		{
			homework.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("game")
	public void ongameClick(ClickEvent click){
		if(game.getStyleName().toString().contains("selected"))
		{
			game.getElement().removeClassName("at-selected");
		}
		else
		{
			game.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("presentation")
	public void onpresentationClick(ClickEvent click){
		if(presentation.getStyleName().toString().contains("selected"))
		{
			presentation.getElement().removeClassName("at-selected");
		}
		else
		{
			presentation.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("refMaterial")
	public void onrefMaterialClick(ClickEvent click){
		if(refMaterial.getStyleName().toString().contains("selected"))
		{
			refMaterial.getElement().removeClassName("at-selected");
		}
		else
		{
			refMaterial.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("quiz")
	public void onquizClick(ClickEvent click){
		if(quiz.getStyleName().toString().contains("selected"))
		{
			quiz.getElement().removeClassName("at-selected");
		}
		else
		{
			quiz.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("currPlan")
	public void oncurrPlanClick(ClickEvent click){
		if(currPlan.getStyleName().toString().contains("selected"))
		{
			currPlan.getElement().removeClassName("at-selected");
		}
		else
		{
			currPlan.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("lessonPlan")
	public void onlessonPlanClick(ClickEvent click){
		if(lessonPlan.getStyleName().toString().contains("selected"))
		{
			lessonPlan.getElement().removeClassName("at-selected");
		}
		else
		{
			lessonPlan.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("unitPlan")
	public void onunitPlanClick(ClickEvent click){
		if(unitPlan.getStyleName().toString().contains("selected"))
		{
			unitPlan.getElement().removeClassName("at-selected");
		}
		else
		{
			unitPlan.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("projectPlan")
	public void onprojectPlanClick(ClickEvent click){
		if(projectPlan.getStyleName().toString().contains("selected"))
		{
			projectPlan.getElement().removeClassName("at-selected");

		}
		else
		{
			projectPlan.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("reading")
	public void onreadingClick(ClickEvent click){
		if(reading.getStyleName().toString().contains("selected"))
		{
			reading.getElement().removeClassName("at-selected");
		}
		else
		{
			reading.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("textbook")
	public void ontextbookClick(ClickEvent click){
		if(textbook.getStyleName().toString().contains("selected"))
		{
			textbook.getElement().removeClassName("at-selected");
		}
		else
		{
			textbook.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("article")
	public void onarticleClick(ClickEvent click){
		if(article.getStyleName().toString().contains("selected"))
		{
			article.getElement().removeClassName("at-selected");
		}
		else
		{
			article.getElement().addClassName("at-selected");
		}
	}
	@UiHandler("book")
	public void onbookClick(ClickEvent click){
		if(book.getStyleName().toString().contains("selected"))
		{
			book.getElement().removeClassName("at-selected");
		}
		else
		{
			book.getElement().addClassName("at-selected");
		}
	}
	public void closeFunction()
	{
		isCancelclicked=false;
		closePoup(isCancelclicked);
	}

	@UiHandler("cancelBtn")
	public void onCancelClick(ClickEvent click)
	{
		isCancelclicked=true;
		closePoup(isCancelclicked);
	}
	/**
	 *
	 * @function OpenMediaFeatureDropdown
	 *
	 * @created_date : 06-Dec-2014
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
	private void OpenMediaFeatureDropdown() {
		isClickedOnDropDwn=true;
		if (spanelMediaFeaturePanel.isVisible()){
			spanelMediaFeaturePanel.setVisible(false);
		}else{
			spanelMediaFeaturePanel.setVisible(true);
		}
	}
	/**
	 *
	 * @function removeClassNameForAllAds
	 *
	 * @created_date : 06-Dec-2014
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
	public void removeClassNameForAllAds(){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				noAds.getElement().removeClassName("at-selected");
				modAds.getElement().removeClassName("at-selected");
				aggreAds.getElement().removeClassName("at-selected");
			}
		});
	}
	@UiHandler("noAds")
	public void onnoAdsClick(ClickEvent click){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				if(noAds.getStyleName().toString().contains("selected"))
				{
					noAds.getElement().removeClassName("at-selected");
				}
				else if(modAds.getStyleName().toString().contains("selected") || aggreAds.getStyleName().toString().contains("selected"))
				{
					removeClassNameForAllAds();
					noAds.getElement().addClassName("at-selected");
				}
				else
				{
					noAds.getElement().addClassName("at-selected");
				}
			}
		} );
	}
	@UiHandler("modAds")
	public void onmodAdsClick(ClickEvent click){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				if(modAds.getStyleName().toString().contains("selected"))
				{
					modAds.getElement().removeClassName("at-selected");
				}
				else if(noAds.getStyleName().toString().contains("selected") || aggreAds.getStyleName().toString().contains("selected"))
				{
					removeClassNameForAllAds();
					modAds.getElement().addClassName("at-selected");

				}
				else
				{
					modAds.getElement().addClassName("at-selected");
				}
			}
		});
	}
	@UiHandler("aggreAds")
	public void onaggreAdsClick(ClickEvent click){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				if(aggreAds.getStyleName().toString().contains("selected"))
				{
					aggreAds.getElement().removeClassName("at-selected");
				}
				else if(noAds.getStyleName().toString().contains("selected") || modAds.getStyleName().toString().contains("selected"))
				{
					removeClassNameForAllAds();
					aggreAds.getElement().addClassName("at-selected");
				}
				else
				{
					aggreAds.getElement().addClassName("at-selected");
				}
			}
		});
	}
	/**
	 *
	 * @function removeClassNamesForAllAccessHazard
	 *
	 * @created_date : 06-Dec-2014
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
	public void removeClassNamesForAllAccessHazard(){
		soundHazard.getElement().removeClassName("select");
		flashingHazard.getElement().removeClassName("select");
		motionSimulationHazard.getElement().removeClassName("select");
	}

	@UiHandler("flashingHazard")
	public void onflashingHazardClick(ClickEvent click){
		if(flashingHazard.getStyleName().toString().contains("select"))
		{
			flashingHazard.getElement().removeClassName("select");
		}
		/*else if(soundHazard.getStyleName().toString().contains("select") ||sound.getStyleName().toString().contains("select") ||motionSimulationHazard.getStyleName().toString().contains("select") || flashing.getStyleName().toString().contains("select") || motionSimulation.getStyleName().toString().contains("select"))
		{
			removeClassNamesForAllAccessHazard();
			flashingHazard.getElement().addClassName("select");
		}*/
		else
		{
			flashingHazard.getElement().addClassName("select");
		}
	}

	@UiHandler("motionSimulationHazard")
	public void onmotionSimulationHazardClick(ClickEvent click){
		if(motionSimulationHazard.getStyleName().toString().contains("select"))
		{
			motionSimulationHazard.getElement().removeClassName("select");
		}
		/*else if(soundHazard.getStyleName().toString().contains("select") ||sound.getStyleName().toString().contains("select") ||motionSimulation.getStyleName().toString().contains("select") || flashing.getStyleName().toString().contains("select") || flashingHazard.getStyleName().toString().contains("select"))
		{
			removeClassNamesForAllAccessHazard();
			motionSimulationHazard.getElement().addClassName("select");
		}*/
		else
		{
			motionSimulationHazard.getElement().addClassName("select");
		}
	}

	@UiHandler("soundHazard")
	public void onsoundHazardClick(ClickEvent click){
		if(soundHazard.getStyleName().toString().contains("select"))
		{
			soundHazard.getElement().removeClassName("select");
		}
		/*else if(sound.getStyleName().toString().contains("select") ||motionSimulationHazard.getStyleName().toString().contains("select") ||motionSimulation.getStyleName().toString().contains("select") || flashing.getStyleName().toString().contains("select") || flashingHazard.getStyleName().toString().contains("select"))
		{
			removeClassNamesForAllAccessHazard();
			soundHazard.getElement().addClassName("select");
		}*/
		else
		{
			soundHazard.getElement().addClassName("select");
		}
	}

	@UiHandler("addTagsBtn")
	public void onaddTagsBtnClick(ClickEvent click)
	{
		getSelectedStandards();
		String frameTagsStr = "";
		String adsStr = "";
		List<String> tagList = new ArrayList<String>();

		String[] educationArr = setEducationalUseString();
		if(educationArr != null)
		{
			for(int i=0;i<educationArr.length;i++)
			{
				tagList.add("\"" + educationArr[i].toString() +"\"");
			}
		}
		String[] lexileMainarr = setLexileLevel();
		if(lexileMainarr != null)
		{
			for(int i=0;i<lexileMainarr.length;i++)
			{
				tagList.add("\"" + lexileMainarr[i].toString() +"\"");
			}
		}
		adsStr = setAdsString();

		if(!adsStr.isEmpty())
		{
			tagList.add("\"" + adsStr +"\"");
		}

		String hazardArr[] = setAccessHazards();
		if(hazardArr != null)
		{
			for(int i=0;i<hazardArr.length;i++)
			{
				tagList.add("\"" + hazardArr[i].toString() +"\"");
			}
		}
		for(final String codeObj:centuryDo){
			tagList.add("\"" +CENTURYSKILLS+" : "+codeObj+"\"");
		}
		for(final String codeObj:standardsDo){
			tagList.add("\"" +standardsDefaultText.getText()+" : "+codeObj+"\"");
		}

		if(!lblMediaPlaceHolder.getText().equalsIgnoreCase("Choose a Media Feature Option:"))
		{
			tagList.add("\"" +mediaLabel.getText()+" : "+lblMediaPlaceHolder.getText() +"\"");
		}

		if(mobileYes.getStyleName().contains("at-OffButtonsActive"))
		{
			tagList.add("\"" +"Mobile Friendly"+" : "+mobileYes.getText() +"\"");
		}
		else if(mobileNo.getStyleName().contains("at-OffButtonsActive"))
		{
			tagList.add("\"" +"Mobile Friendly"+" : "+mobileNo.getText() +"\"");
		}

		deleteTagsServiceRequest(tagListGlobal.toString(), tagList.toString());
	}
	/**
	 *
	 * @function addTagsServiceRequest
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param frameTagsStr
	 * @parm(s) : @param resourceId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void addTagsServiceRequest(String frameTagsStr, String resourceId){

		AppClientFactory.getInjector().getResourceService().addTagsToResource(resourceId, frameTagsStr, new SimpleAsyncCallback<List<ResourceTagsDo>>() {
			@Override
			public void onSuccess(List<ResourceTagsDo> result) {
			//	bindObjectsToUI(result);
				closeFunction();
				getAddedResourceTags();
			}
		});

	}
	/**
	 *
	 * @function deleteTagsServiceRequest
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param frameTagsStr
	 * @parm(s) : @param addingNewTags
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void deleteTagsServiceRequest(String frameTagsStr, final String addingNewTags)
	{
		AppClientFactory.getInjector().getResourceService().deleteTagsServiceRequest(resourceId, frameTagsStr, new SimpleAsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				addTagsServiceRequest(addingNewTags.toString(), resourceId);
				//getAddedResourceTags();
			}
		});
	}
	/**
	 *
	 * @function getTagsServiceRequest
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param resourceId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void getTagsServiceRequest(String resourceId)
	{
		AppClientFactory.getInjector().getResourceService().getTagsToResource(resourceId, new SimpleAsyncCallback<List<ResourceTagsDo>>() {
			@Override
			public void onSuccess(List<ResourceTagsDo> result) {
				bindObjectsToUI(result);
			}
		});
	}
	/**
	 *
	 * @function bindObjectsToUI
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param resultResourceTags
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void bindObjectsToUI(List<ResourceTagsDo> resultResourceTags)
	{
		tagListGlobal.clear();
		standardsDo.clear();
		centuryDo.clear();
		centuryPanel.clear();
		for(int objVal=0;objVal<resultResourceTags.size();objVal++)
		{
			tagListGlobal.add("\""+resultResourceTags.get(objVal).getLabel()+"\"");
			if(resultResourceTags.get(objVal).getLabel().contains(headerEducationalUse.getText()))
			{
				setEducationalObjectVal(resultResourceTags.get(objVal).getLabel());
			}
			if(resultResourceTags.get(objVal).getLabel().contains(lexileHeader.getText()))
			{
				setLexileObjectVal(resultResourceTags.get(objVal).getLabel());
			}
			if(resultResourceTags.get(objVal).getLabel().contains(AdsHeader.getText()))
			{
				setAdsObjectVal(resultResourceTags.get(objVal).getLabel());
			}
			if(resultResourceTags.get(objVal).getLabel().contains(accessHazard.getText()))
			{
				setAccessHazardObjectVal(resultResourceTags.get(objVal).getLabel());
			}
			if(resultResourceTags.get(objVal).getLabel().contains(standardsDefaultText.getText()))
			{
				setStandardObjectVal(resultResourceTags.get(objVal).getLabel());
			}
			if(resultResourceTags.get(objVal).getLabel().contains(CENTURYSKILLS))
			{
				setCenturyObjectVal(resultResourceTags.get(objVal).getLabel(),objVal);
			}
			if(resultResourceTags.get(objVal).getLabel().contains(mediaLabel.getText()))
			{
				setMediaFeatureObjectVal(resultResourceTags.get(objVal).getLabel());
			}
			if(resultResourceTags.get(objVal).getLabel().contains("Mobile Friendly"))
			{
				setMobileFriendlyObjectVal(resultResourceTags.get(objVal).getLabel());
			}
		}
	}
	/**
	 *
	 * @function setMobileFriendlyObjectVal
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param mobileFriendlyVal
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setMobileFriendlyObjectVal(String mobileFriendlyVal)
	{
		if(mobileFriendlyVal.contains(mobileYes.getText()))
		{
			mobileYes.getElement().setClassName("at-OffButtonsActive");
			mobileNo.getElement().setClassName("at-OnButtonDeActive");
		}
		else if(mobileFriendlyVal.contains(mobileNo.getText()))
		{
			mobileNo.getElement().setClassName("at-OffButtonsActive");
			mobileYes.getElement().setClassName("at-OnButtonDeActive");
		}
	}
	/**
	 *
	 * @function setMediaFeatureObjectVal
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param mediaFeatureVal
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setMediaFeatureObjectVal(String mediaFeatureVal)
	{
		if(mediaFeatureVal != null)
		{
			mediaFeatureVal = mediaFeatureVal.replace(mediaLabel.getText()+" : ", "");
			lblMediaPlaceHolder.setText(mediaFeatureVal);
		}
	}
	/**
	 *
	 * @function setCenturyObjectVal
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param standardStr
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public void setCenturyObjectVal(String standardStr,int keyVal){
		String[] standardArray=standardStr.split(":");
		addCentury(standardArray[1], String.valueOf(keyVal));
	}
	/**
	 * @function setStandardObjectVal
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param standardStr
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public void setStandardObjectVal(String standardStr)
	{
		String[] standardArray=standardStr.split(":");

		//standardsDo.add(standardArray[1]);
		//here
		addStandard(standardArray[1], "0");
	}
	/**
	 * Adding new standard for the collection , will check it has more than
	 * fifteen standards
	 *
	 * @param standard
	 *            which to be added for the collection
	 */
	public void addStandard(String standard, String id) {
		if (ulSelectedItems.getWidgetCount() <5) {
			if (standard != null && !standard.isEmpty()) {
				standardsDo.add(standard);
				ulSelectedItems.add(generateLiPanel(standard, id));
			//	standardsPanel.add(createStandardLabel(standard, id, standardCodesMap.get(id)));
			}
		} else {
			standardMaxShow();
			standardSgstBox.setText("");
		}
	}
	/**
	 *
	 * @function setAccessHazardObjectVal
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param accessHazardStr
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public void setAccessHazardObjectVal(String accessHazardStr)
	{
		String[] stringArry=accessHazardStr.split(":");
		if(stringArry.length!=0){
			if(stringArry[1].trim().equalsIgnoreCase(flashingHazard.getText()))
			{
					flashingHazard.getElement().addClassName("select");
			}
			if(stringArry[1].trim().equalsIgnoreCase(motionSimulationHazard.getText()))
			{
				motionSimulationHazard.getElement().addClassName("select");
			}
			if(stringArry[1].trim().equalsIgnoreCase(soundHazard.getText()))
			{
				soundHazard.getElement().addClassName("select");
			}
		}
	}
	/**
	 * @function setAdsObjectVal
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param adsStr
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public void setAdsObjectVal(String adsStr)
	{
		if(adsStr.contains(noAds.getText()))
		{
			noAds.getElement().addClassName("at-selected");
		}
		if(adsStr.contains(modAds.getText()))
		{
			modAds.getElement().addClassName("at-selected");
		}
		if(adsStr.contains(aggreAds.getText()))
		{
			aggreAds.getElement().addClassName("at-selected");
		}
	}
	/**
	 * @function setLexileObjectVal
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param lexileStr
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public void setLexileObjectVal(String lexileStr)
	{
		if(lexileStr.contains(kindergarden.getText()))
		{
			kindergarden.getElement().addClassName("at-selected");
		}
		if(lexileStr.contains(level1.getText()))
		{
			String[] stringArry=lexileStr.split(":");
			if(stringArry[1].trim().equalsIgnoreCase(i18n.GL_GRR_NUMERIC_ONE())){
				level1.getElement().addClassName("at-selected");
			}
		}
		if(lexileStr.contains(level2.getText()))
		{
			String[] stringArry=lexileStr.split(":");
			if(stringArry[1].trim().equalsIgnoreCase(i18n.GL_GRR_NUMERIC_TWO())){
				level2.getElement().addClassName("at-selected");
			}
		}
		if(lexileStr.contains(level3.getText()))
		{
			level3.getElement().addClassName("at-selected");
		}
		if(lexileStr.contains(level4.getText()))
		{
			level4.getElement().addClassName("at-selected");
		}
		if(lexileStr.contains(level5.getText()))
		{
			level5.getElement().addClassName("at-selected");
		}
		if(lexileStr.contains(level6.getText()))
		{
			level6.getElement().addClassName("at-selected");
		}
		if(lexileStr.contains(level7.getText()))
		{
			level7.getElement().addClassName("at-selected");
		}
		if(lexileStr.contains(level8.getText()))
		{
			level8.getElement().addClassName("at-selected");
		}
		if(lexileStr.contains(level9.getText()))
		{
			level9.getElement().addClassName("at-selected");
		}
		if(lexileStr.contains(level10.getText()))
		{
			level10.getElement().addClassName("at-selected");
		}
		if(lexileStr.contains(level11.getText()))
		{
			level11.getElement().addClassName("at-selected");
		}
		if(lexileStr.contains(level12.getText()))
		{
			level12.getElement().addClassName("at-selected");
		}
	}
	/**
	 * @function setEducationalObjectVal
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param educationalStr
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public void setEducationalObjectVal(String educationalStr)
	{
		if(educationalStr.contains(activity.getText()))
		{
			activity.getElement().addClassName("at-selected");
		}
		else if(educationalStr.contains(handout.getText()))
		{
			handout.getElement().addClassName("at-selected");
		}
		else if(educationalStr .contains(homework.getText()))
		{
			homework.getElement().addClassName("at-selected");
		}
		else if( educationalStr.contains(game.getText()))
		{
			game.getElement().addClassName("at-selected");
		}
		else if(educationalStr.contains(presentation.getText()))
		{
			presentation.getElement().addClassName("at-selected");
		}
		else if(educationalStr.contains(refMaterial.getText()))
		{
			refMaterial.getElement().addClassName("at-selected");
		}
		else if(educationalStr.contains(quiz.getText()))
		{
			quiz.getElement().addClassName("at-selected");
		}
		else if(educationalStr.contains(currPlan.getText()))
		{
			currPlan.getElement().addClassName("at-selected");
		}
		else if(educationalStr.contains(lessonPlan.getText()))
		{
			lessonPlan.getElement().addClassName("at-selected");
		}
		else if(educationalStr.contains(unitPlan.getText()))
		{
			unitPlan.getElement().addClassName("at-selected");
		}
		else if(educationalStr.contains(projectPlan.getText()))
		{
			projectPlan.getElement().addClassName("at-selected");
		}
		else if(educationalStr.contains(reading.getText()))
		{
			reading.getElement().addClassName("at-selected");
		}
		else if(educationalStr.contains(textbook.getText()))
		{
			textbook.getElement().addClassName("at-selected");
		}
		else if(educationalStr.contains(article.getText()))
		{
			article.getElement().addClassName("at-selected");
		}
		else if(educationalStr.contains(book.getText()))
		{
			book.getElement().addClassName("at-selected");
		}
	}
	/**
	 * @function setAdsString
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public String setAdsString()
	{
		String adsStr = "";

		if(noAds.getElement().getClassName().contains("selected"))
		{
			adsStr = AdsHeader.getText()+" : "+noAds.getText();
		}
		else if(modAds.getElement().getClassName().contains("selected"))
		{
			adsStr = AdsHeader.getText()+" : "+modAds.getText();
		}
		else if(aggreAds.getElement().getClassName().contains("selected"))
		{
			adsStr = AdsHeader.getText()+" : "+aggreAds.getText();
		}

		return adsStr;
	}
	/**
	 * @function setAccessHazards
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String[]
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public String[] setAccessHazards()
	{
		String[] accessHazardsArr = null;
		List<String> accessHazardsSelected = new ArrayList<String>();

		if(flashingHazard.getElement().getClassName().contains("select"))
		{
			String hazardsStr = accessHazard.getText()+" : "+flashingHazard.getText();
			accessHazardsSelected.add(hazardsStr);
		}
		if(motionSimulationHazard.getElement().getClassName().contains("select"))
		{
			String hazardsStr = accessHazard.getText()+" : "+motionSimulationHazard.getText();
			accessHazardsSelected.add(hazardsStr);
		}
		if(soundHazard.getElement().getClassName().contains("select"))
		{
			String hazardsStr = accessHazard.getText()+" : "+soundHazard.getText();
			accessHazardsSelected.add(hazardsStr);
		}

		accessHazardsArr = accessHazardsSelected.toArray(new String[accessHazardsSelected.size()]);
		return accessHazardsArr;
	}
	/**
	 * @function setEducationalUseString
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 * @parm(s) : @return
	 *
	 * @return : String[]
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public String[] setEducationalUseString()
	{
		String[] educationalUseArr = null;
		List<String> educationalUseSelected = new ArrayList<String>();
		if(activity.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + activity.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(handout.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + handout.getText();
			educationalUseSelected.add(educationalUse);

		}
		if(homework.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + homework.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(game.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + game.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(presentation.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + presentation.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(refMaterial.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + refMaterial.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(quiz.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + quiz.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(currPlan.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + currPlan.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(lessonPlan.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + lessonPlan.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(unitPlan.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + unitPlan.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(projectPlan.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + projectPlan.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(reading.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + reading.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(textbook.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + textbook.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(article.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + article.getText();
			educationalUseSelected.add(educationalUse);
		}
		if(book.getElement().getClassName().contains("selected"))
		{
			String educationalUse = headerEducationalUse.getText() + " : " + book.getText();
			educationalUseSelected.add(educationalUse);
		}
		educationalUseArr = educationalUseSelected.toArray(new String[educationalUseSelected.size()]);
		return educationalUseArr;
	}
	/**
	 * @function setLexileLevel
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 * @parm(s) : @return
	 *
	 * @return : String[]
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public String[] setLexileLevel()
	{
		String[] lexileLevelArr = null;
		List<String> lexileSelectedOptions = new ArrayList<String>();

		if(kindergarden.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + kindergarden.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		if(level1.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + level1.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		if(level2.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + level2.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		if(level3.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + level3.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		if(level4.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + level4.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		if(level5.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + level5.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		if(level6.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + level6.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		if(level7.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + level7.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		if(level8.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + level8.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		if(level9.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + level9.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		if(level10.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + level10.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		if(level11.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + level11.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		if(level12.getElement().getClassName().contains("selected"))
		{
			String lexileStr = lexileHeader.getText() + " : " + level12.getText();
			lexileSelectedOptions.add(lexileStr);
		}
		lexileLevelArr = lexileSelectedOptions.toArray(new String[lexileSelectedOptions.size()]);
		return lexileLevelArr;

	}

	@UiHandler("mobileYes")
	public void onmobileYesClick(ClickEvent click)
	{
		mobileYes.getElement().setClassName("at-OffButtonsActive");
		mobileNo.getElement().setClassName("at-OnButtonDeActive");
	}

	@UiHandler("mobileNo")
	public void onmobileNoClick(ClickEvent click)
	{
		mobileNo.getElement().setClassName("at-OffButtonsActive");
		mobileYes.getElement().setClassName("at-OnButtonDeActive");
	}

	@Override
	public Widget asWidget() {

		return null;
	}

	public abstract void closePoup(boolean isCancelclicked);

	public void getAddedResourceTags(){

	}

	public final void populateStandardValues() {
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

	private void bindDatabeforeLoad()
	{
		final AddCenturyPresenter centuryPresenterWidget=AppClientFactory.getInjector().getAddCenturyPresenterWidget();
		centuryPresenterWidget.loadStateStandards();
		//This will display the 21 century popup
				centbrowseBtn.addClickHandler(new ClickHandler() {
							@Override
					public void onClick(ClickEvent event) {
						centuryPopup.clear();
						centuryPresenterWidget.setAddResourceDataAddTags(centuryDo);
						centuryPopup.add(centuryPresenterWidget.getWidget());
						centuryPopup.show();
						centuryPopup.center();
						centuryPopup.getElement().getStyle().setZIndex(999999);
					}
				});
				//This will hide the popup when clicked on the cancel button
				centuryPresenterWidget.getCancelBtn().addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						for (Map.Entry<Long, String> entry : centurySelectedValues.entrySet()){
							centuryDo.add(entry.getValue());
						}

								hideCenturyPopup();
					}
				});
				//This will hide the popup when clicked on close button
				centuryPresenterWidget.getCloseBtn().addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						for (Map.Entry<Long, String> entry : centurySelectedValues.entrySet()){
							centuryDo.add(entry.getValue());
						}
								hideCenturyPopup();
					}
				});
				centuryPresenterWidget.getAddButton().addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						centuryPanel.clear();
						centuryDo.clear();
						centurySelectedValues=centuryPresenterWidget.getSelectedValues();
						if(centurySelectedValues!=null && centurySelectedValues.size()>0){
							for (Map.Entry<Long, String> entry : centurySelectedValues.entrySet()){
								centuryDo.add(entry.getValue());
								centuryPanel.add(create21CenturyLabel(entry.getValue(),entry.getKey()+"",""));
							}
						}
						hideCenturyPopup();
					}

				});
	}

	public void getAddStandards() {
		bindDatabeforeLoad();
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
				selectedValues.add(code.getLabel());
				standardCodesMap.put(code.getCodeId() + "", code.getLabel());
			}
		}
		standardSgstBox.showSuggestionList();
	}
	public void displaySelectedStandards(List<Map<String,String>> standListArray){
		for (int i=0;i<standListArray.size();i++){
			final Map<String, String> standard = standListArray.get(i);
			if (!selectedValues.contains(standard.get("selectedCodeVal"))){
				ulSelectedItems.add(generateLiPanel(standard.get("selectedCodeVal"), "standards"));
			}
		}
	}
	public void displaySelectedStandardsOne(Map<String, String> standard){
			if (!selectedValues.contains(standard.get("selectedCodeVal"))){
				ulSelectedItems.add(generateLiPanel(standard.get("selectedCodeVal"), "standards"));
			}

	}
	private LiPanelWithClose generateLiPanel(final String standard, String tagValue) {
		final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(standard);
		liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//This will remove the selected value when we are trying by close button
				//removeGradeWidget(ulSelectedItems,Long.parseLong(standard.get("selectedCodeId")));
				standardsDo.remove(standard);
				liPanelWithClose.removeFromParent();
			}
		});
		standardsDo.add(standard);
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
	public void getSelectedStandards(){
		Iterator<Widget> widgets=ulSelectedItems.iterator();
		while (widgets.hasNext()) {
			Widget widget=widgets.next();
			if(widget instanceof LiPanelWithClose){
				LiPanelWithClose obj=(LiPanelWithClose) widget;
				if(obj.getDifferenceId()==3){
					selectedValues.add(obj.getName());
					standardsDo.add(obj.getName());
				}
			}
		}
	}
}
