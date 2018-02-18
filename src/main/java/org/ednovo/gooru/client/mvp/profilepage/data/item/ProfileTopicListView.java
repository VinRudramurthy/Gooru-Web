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
package org.ednovo.gooru.client.mvp.profilepage.data.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.folder.FolderTocDo;
import org.ednovo.gooru.application.shared.model.library.LessonDo;
import org.ednovo.gooru.application.shared.model.library.PartnerConceptListDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.gsearch.IsGooruSearchView;
import org.ednovo.gooru.client.mvp.gsearch.addResourcePopup.SearchAddResourceToCollectionPresenter;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.home.library.assign.AssignPopupVc;
import org.ednovo.gooru.client.mvp.home.library.events.SetLoadingIconEvent;
import org.ednovo.gooru.client.mvp.home.library.events.SetLoadingIconHandler;
import org.ednovo.gooru.client.mvp.home.library.events.StandardPreferenceSettingEvent;
import org.ednovo.gooru.client.mvp.home.library.events.StandardPreferenceSettingHandler;
import org.ednovo.gooru.client.mvp.profilepage.data.ProfilePageLibraryStyleBundle;
import org.ednovo.gooru.client.mvp.profilepage.event.OpenProfileCollectionEvent;
import org.ednovo.gooru.client.mvp.profilepage.event.OpenProfileCollectionHandler;
import org.ednovo.gooru.client.uc.BrowserAgent;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.StandardSgItemVc;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.uc.tooltip.LibraryTopicCollectionToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.shared.util.ResourceImageUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class ProfileTopicListView extends Composite{

	@UiField ScrollPanel lessonScrollPanel;
	@UiField HTMLPanel topicBlock, conceptList,collectionInfo,resourcesInside,moreOnTopicText;
	@UiField Label topicTitleLbl, noCollectionLbl, libraryTopicLbl;
	@UiField Image collectionImage;
	@UiField HTML collectionTitleLbl, collectionDescriptionLbl;
	@UiField Button assignCollectionBtn, customizeCollectionBtn,viewAllBtn,go2Assessment;
	@UiField HTMLPanel loadingImage, collectionViewer;
	@UiField FlowPanel standardsFloPanel;
	@UiField ProfilePageLibraryStyleBundle style;
	@UiField HTMLEventPanel searchLink;

	private Integer topicId;

	private String placeToken = null;

	private boolean isScrollable = true;

	public static boolean isAssignPopup = false;

	public static boolean isCustomizePopup = false;

	private ProfileLibraryDo profileLibraryDo;

	private String searchTitle="";

	private HandlerRegistration imageHandler;

	private HandlerRegistration titleHandler;

	ProfileLibraryDo profileFolderDoMainObject = new ProfileLibraryDo();

	private static final String DEFULT_IMAGE_PREFIX = "images/default-";

	private static final String PNG = ".png";

	private static final String SMALL = "Small";

	private static final String CUSTOMIZE = "customize";

	private static final String ASSIGN = "assign";

	private static final String DEFAULT_COLLECTION_IMAGE = "../images/default-collection-image-160x120.png";

	private static final String DEFULT_ASSESSMENT = "images/default-assessment-image -160x120.png";

	private static Integer LESSON_PAGE_INITIAL_LIMIT = 20;

	private static String PAGE = "page";

	private static final String COURSE_PAGE = "course-page";

	private static final String STANDARD_CODE = "code";

	private static final String STANDARD_DESCRIPTION = "description";

	private static final String PNG_CROP = "-80x60.";

	private PopupPanel toolTipPopupPanel = new PopupPanel();

	private PopupPanel toolTipPopupPanelNew = new PopupPanel();

	private static final String SUBJECT_NAME = "subject";

	private static final String STANDARDS="standard";

	private static final String SHARING_TYPE = "public";

	private int pageNumber = 2;

	String lessonCode="";

	List<String> standPrefCode = new ArrayList<String>();

	private static boolean isVisible=true;

	private static final String STANDARD_ID = "standardId";

	private static final String COLLECTION_TITLE = "collectionTitle";

	private static final String  ASSESSMENT = "assessment";

	private static final String  ASSESSMENT_URL = "assessment/url";

	private static final String  COLLECTION = "collection";

	private String libraryGooruOid=null;

	SearchAddResourceToCollectionPresenter remixPresenterWidget = AppClientFactory.getInjector().getRemixPresenterWidget();

	String collectionIdVal = "";
	String folderIdVal = "";
	String folderIdValGbl = "";


	private static ProfileTopicListViewUiBinder uiBinder = GWT
			.create(ProfileTopicListViewUiBinder.class);

	interface ProfileTopicListViewUiBinder extends
			UiBinder<Widget, ProfileTopicListView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public ProfileTopicListView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ProfileTopicListView(ProfileLibraryDo profileFolderDo, String placeToken,String libraryGooruOid,int topicNumber,String parentId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.topicId = topicNumber;
		this.libraryGooruOid=libraryGooruOid;
		setPlaceToken(placeToken);
		assignCollectionBtn.setText(i18n.GL0526());
		assignCollectionBtn.getElement().setAttribute("alt",i18n.GL0526());
		assignCollectionBtn.getElement().setAttribute("title",i18n.GL0526());

		customizeCollectionBtn.setText(i18n.GL2037());
		customizeCollectionBtn.getElement().setAttribute("alt",i18n.GL2037());
		customizeCollectionBtn.getElement().setAttribute("title",i18n.GL2037());

		noCollectionLbl.setVisible(false);

		noCollectionLbl.setText(i18n.GL1170());
		noCollectionLbl.getElement().setAttribute("alt",i18n.GL1170());
		noCollectionLbl.getElement().setAttribute("title",i18n.GL1170());

		moreOnTopicText.getElement().setInnerHTML(i18n.GL1169());
		moreOnTopicText.getElement().setAttribute("alt",i18n.GL1169());
		moreOnTopicText.getElement().setAttribute("title",i18n.GL1169());
		searchLink.setVisible(false);
		searchLink.addClickHandler(new OnSearchLinkClick());
		setTopicLabel(profileFolderDo.getTitle());
		searchTitle=profileFolderDo.getTitle();
		collectionInfo.setVisible(false);

		profileFolderDoMainObject = profileFolderDo;

		if(profileFolderDo.getCollections()!=null) {
			setOnlyConceptData(profileFolderDo.getCollectionItems(), false, profileFolderDo.getGooruOid(), profileFolderDo.getItemCount(), libraryGooruOid);
			try {
				setConceptData(profileFolderDo.getCollectionItems().get(0),topicId, null, null,null,libraryGooruOid);
			} catch(Exception e) {
				setDefaultCollectionLbl();
				AppClientFactory.printSevereLogger("ProfileTopicListView ...."+e.getMessage());
			}
		} else {
			setPartnerLibraryLessonData(profileFolderDo.getCollectionItems(), profileFolderDo.getGooruOid(),libraryGooruOid);
			try {
				if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY) ||
						AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.VALVERDE) ||
						AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SUSD) ||
						AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.YCGL_LIBRARY) ||
						AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.EPISD_LIBRARY) ||
						AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
					setConceptData(profileFolderDo.getCollectionItems().get(0).getCollectionItems().get(0).getCollectionItems().get(0),topicId, null, null,null,libraryGooruOid);
					searchLink.setVisible(false);
				} else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.LIFEBOARD)){
					setConceptData(profileFolderDo.getCollectionItems().get(0).getCollectionItems().get(0),topicId, null, null,null,libraryGooruOid);
					searchLink.setVisible(false);

				}
				else
				{
					searchLink.setVisible(false);
				}
			} catch(Exception e) {
				setDefaultCollectionLbl();
//				AppClientFactory.printSevereLogger(e.toString());
			}
		}

		loadingImage.setVisible(false);



		assignCollectionBtn.addMouseOverHandler(new OnassignCollectionBtnMouseOver());
		assignCollectionBtn.addMouseOutHandler(new OnassignCollectionBtnMouseOut());
		customizeCollectionBtn.addMouseOverHandler(new OncustomizeCollectionBtnMouseOver());
		customizeCollectionBtn.addMouseOutHandler(new OncustomizeCollectionBtnMouseOut());
		if(!AppClientFactory.isAnonymous()){
			try {
				if(AppClientFactory.getLoggedInUser().getMeta() != null && AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference() !=null && AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference().getCode() != null){
					getStandardPrefCode(AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference().getCode());
				}
			} catch (Exception e) {
				AppClientFactory.printSevereLogger("ProfileTopicListView"+e.getMessage());
			}
		}else{
			standardsFloPanel.setVisible(true);
		}
		AppClientFactory.getEventBus().addHandler(OpenProfileCollectionEvent.TYPE, openProfileCollectionHandler);
		AppClientFactory.getEventBus().addHandler(SetLoadingIconEvent.TYPE, setLoadingIconHandler);
		AppClientFactory.getEventBus().addHandler(StandardPreferenceSettingEvent.TYPE, standardPreferenceSettingHandler);
		setId();
		viewAllBtn.getElement().setAttribute("style", "float:right;margin: -37px 9px 0 0;");
		viewAllBtn.getElement().setId("btnViewAll");
		viewAllBtn.setVisible(true);

		viewAllBtn.addClickHandler(new ViewAllBtnClickHandler(profileFolderDo.getGooruOid(),parentId));
		go2Assessment.setVisible(false);

	}
	public void setId(){
		assignCollectionBtn.getElement().setId("btnAssignCollectionBtn");
		customizeCollectionBtn.getElement().setId("btnCustomizeCollectionBtn");
		noCollectionLbl.getElement().setId("lblNoCollectionLbl");
		topicBlock.getElement().setId("pnlTopicBlock");
		topicTitleLbl.getElement().setId("lblTopicTitleLbl");
		libraryTopicLbl.getElement().setId("lblLibraryTopicLbl");
		lessonScrollPanel.getElement().setId("sbLessonScrollPanel");
		conceptList.getElement().setId("pnlConceptList");
		loadingImage.getElement().setId("pnlLoadingImage");
		collectionViewer.getElement().setId("pnlCollectionViewer");
		collectionInfo.getElement().setId("pnlCollectionInfo");
		collectionImage.getElement().setId("imgCollectionImage");
		collectionTitleLbl.getElement().setId("htmlCollectionTitleLbl");
		collectionDescriptionLbl.getElement().setId("htmlCollectionDescriptionLbl");
		standardsFloPanel.getElement().setId("fpnlStandardsFloPanel");
		resourcesInside.getElement().setId("htmlResourcesInside");
		moreOnTopicText.getElement().setId("pnlMoreOnTopicText");
	}

	public ProfileTopicListView(ProfileLibraryDo profileFolderDo, Integer conceptNumber, String placeToken, String collectionType,String libraryGooruOid) {
		initWidget(uiBinder.createAndBindUi(this));
		this.topicId = conceptNumber;
		this.libraryGooruOid=libraryGooruOid;
		setPlaceToken(placeToken);
		collectionImage.getElement().setAttribute("collid", profileFolderDo.getGooruOid());
		collectionTitleLbl.getElement().setAttribute("collid", profileFolderDo.getGooruOid());

		collectionTitleLbl.getElement().setAttribute(COLLECTION_TITLE,profileFolderDo.getTitle());
		assignCollectionBtn.setText(i18n.GL0526());
		assignCollectionBtn.getElement().setAttribute("alt",i18n.GL0526());
		assignCollectionBtn.getElement().setAttribute("title",i18n.GL0526());

		customizeCollectionBtn.setText(i18n.GL2037());
		customizeCollectionBtn.getElement().setAttribute("alt",i18n.GL2037());
		customizeCollectionBtn.getElement().setAttribute("title",i18n.GL2037());

		moreOnTopicText.getElement().setInnerHTML(i18n.GL1169());
		moreOnTopicText.getElement().setAttribute("alt",i18n.GL1169());
		moreOnTopicText.getElement().setAttribute("title",i18n.GL1169());
		searchLink.setVisible(false);
		searchLink.addClickHandler(new OnSearchLinkClick());

		setTopicLabel(profileFolderDo.getTitle());
		searchTitle=profileFolderDo.getTitle();
		if(profileFolderDo.getType().contains(ASSESSMENT)){
			topicTitleLbl.addStyleName("assessment");
			if(profileFolderDo.getType().equals(ASSESSMENT_URL)){
				showAssessmentButton(true);
			}
		}else{
			topicTitleLbl.addStyleName("collection");
		}

		try {
			setConceptData(profileFolderDo,conceptNumber,null, null,null,libraryGooruOid);
		} catch(Exception e) {
			collectionInfo.setVisible(false);
			resourcesInside.setVisible(false);
			noCollectionLbl.setVisible(true);
			AppClientFactory.printSevereLogger("ProfileTopicListView  rrrrr:"+e);

		}

		//searchLink.getElement().getStyle().setDisplay(Display.NONE);

		assignCollectionBtn.addMouseOverHandler(new OnassignCollectionBtnMouseOver());
		assignCollectionBtn.addMouseOutHandler(new OnassignCollectionBtnMouseOut());
		customizeCollectionBtn.addMouseOverHandler(new OncustomizeCollectionBtnMouseOver());
		customizeCollectionBtn.addMouseOutHandler(new OncustomizeCollectionBtnMouseOut());


		lessonScrollPanel.setVisible(false);
		collectionViewer.addStyleName(style.collectionViewerSubStyle());
		collectionInfo.addStyleName(style.collectionInfoSubStyle());
		resourcesInside.addStyleName(style.resourcesInsideSubStyle());

		loadingImage.setVisible(false);
		if(!AppClientFactory.isAnonymous()){
			try {
				if(AppClientFactory.getLoggedInUser().getMeta() != null && AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference() != null && AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference().getCode() != null){
					getStandardPrefCode(AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference().getCode());
				}
			} catch (Exception e) {
				AppClientFactory.printSevereLogger("ProfileTopicListView"+e.getMessage());
			}
		}else{
			standardsFloPanel.setVisible(true);
		}

		AppClientFactory.getEventBus().addHandler(StandardPreferenceSettingEvent.TYPE, standardPreferenceSettingHandler);
		setId();
		Map<String, String> maps = StringUtil.splitQuery(Window.Location
				.getHref());
		if(maps.containsKey("emailId")){
			showPopupAfterGmailSignin();
		}

		viewAllBtn.setVisible(false);

	}
	/**
	 * To show go2Assessment button when collectionType is assessment/url
	 * @param isVisible {@link Boolean}
	 */
	private void showAssessmentButton(boolean isVisible) {
		go2Assessment.setVisible(isVisible);
		assignCollectionBtn.setVisible(!isVisible);
		customizeCollectionBtn.setVisible(!isVisible);
	}

	private void setOnlyConceptData(ArrayList<ProfileLibraryDo> profileFolderDoList, boolean isTopicCalled, final String parentId, final int partnerItemCount,String libraryGooruOid) {
		boolean isLessonHighlighted = true;
		int pageCount = 0;
		String subjectName = AppClientFactory.getPlaceManager().getRequestParameter(SUBJECT_NAME);
		if(subjectName!=null && subjectName.equalsIgnoreCase(STANDARDS) || StringUtil.isPartnerUser(AppClientFactory.getCurrentPlaceToken())) {
			LESSON_PAGE_INITIAL_LIMIT = 20;
		}
		if(parentId!=null) {
			LESSON_PAGE_INITIAL_LIMIT = 20;
			pageCount = partnerItemCount;
		} else {
			pageCount = profileFolderDoList.size();
		}
		if(pageCount>=LESSON_PAGE_INITIAL_LIMIT) {
			conceptList.add(new PartnerLessonUc(profileFolderDoList,topicId,isLessonHighlighted, 0, false,libraryGooruOid));
			final String subject = AppClientFactory.getPlaceManager().getRequestParameter("subject","featured");
			lessonScrollPanel.addScrollHandler(new ScrollHandler() {
				@Override
				public void onScroll(ScrollEvent event) {
					if(isScrollable) {
						if(StringUtil.isPartnerUser(AppClientFactory.getCurrentPlaceToken())) {
							if(LESSON_PAGE_INITIAL_LIMIT<partnerItemCount) {
								isScrollable = false;
								String gooruUid = AppClientFactory.getPlaceManager().getRequestParameter("pid");
								AppClientFactory.getInjector().getLibraryService().getPartnerChildFolders(gooruUid, (pageNumber-1)*20, 20,parentId,SHARING_TYPE, null,new SimpleAsyncCallback<PartnerConceptListDo>() {
									@Override
									public void onSuccess(PartnerConceptListDo result) {
										LESSON_PAGE_INITIAL_LIMIT = LESSON_PAGE_INITIAL_LIMIT + result.getSearchResult().size();
										pageNumber = pageNumber + 1;
										if(LESSON_PAGE_INITIAL_LIMIT < partnerItemCount) {
											isScrollable = true;
										}
										//conceptList.add(new PartnerLessonUc(result.getSearchResult(),topicId,false, 0));
									}
								});
							}
						} else {
							AppClientFactory.getInjector().getLibraryService().getLessonsOnPagination(subject, ""+topicId, LESSON_PAGE_INITIAL_LIMIT, 20, getPlaceToken(), new SimpleAsyncCallback<ArrayList<LessonDo>>() {

								@Override
								public void onSuccess(ArrayList<LessonDo> lessonDoList) {
									for(int i=0;i<lessonDoList.size();i++) {
										isScrollable = false;
										//conceptList.add(new PartnerLessonUc(lessonDoList.get(i).getCollection(),topicId,false, 0));
									}
								}
							});
						}
					}
				}
			});
		} else {
			int conceptSize = 1;
			if(isTopicCalled) {
				conceptSize = profileFolderDoList.size();
			}
			for(int i=0;i<conceptSize;i++) {
				if(i==0) {
					isLessonHighlighted = true;
				} else {
					isLessonHighlighted = false;
				}
				conceptList.add(new PartnerLessonUc(profileFolderDoList,topicId,isLessonHighlighted,(i+1), false,libraryGooruOid));
			}
		}
	}

	public void setConceptData(final ProfileLibraryDo conceptDo, Integer topicId, final String lessonId, String lessonLabel,String lessonCode,final String libraryGooruOid) {
		setConceptDo(conceptDo);

		if(profileFolderDoMainObject.getCollectionItems()!=null)
		{
		for(int j=0;j<profileFolderDoMainObject.getCollectionItems().size();j++)
		{
			if(profileFolderDoMainObject.getCollectionItems().get(j).getGooruOid().equalsIgnoreCase(conceptDo.getGooruOid()))
			{
				if(profileFolderDoMainObject.getCollectionItems().get(j).getParentGooruOid()!=null)
				{
				folderIdValGbl = profileFolderDoMainObject.getCollectionItems().get(j).getParentGooruOid();
				collectionImage.getElement().setAttribute("folderId", profileFolderDoMainObject.getCollectionItems().get(j).getParentGooruOid());
				collectionTitleLbl.getElement().setAttribute("folderId", profileFolderDoMainObject.getCollectionItems().get(j).getParentGooruOid());
				}
				break;
			}
			else
			{
			if(profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems()!=null)
			{
			for(int k=0;k<profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().size();k++)
			{
				if(profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().get(k).getGooruOid().equalsIgnoreCase(conceptDo.getGooruOid()))
				{
					if(profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().get(k).getParentGooruOid()!=null)
					{
					folderIdValGbl = profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().get(k).getParentGooruOid();
					collectionImage.getElement().setAttribute("folderId", profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().get(k).getParentGooruOid());
					collectionTitleLbl.getElement().setAttribute("folderId", profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().get(k).getParentGooruOid());
					}
					break;
				}
				else
				{
					if(profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().get(k).getCollectionItems()!=null)
					{
					for(int l=0;l<profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().get(k).getCollectionItems().size();l++)
					{
						if(profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().get(k).getCollectionItems().get(l).getGooruOid().equalsIgnoreCase(conceptDo.getGooruOid()))
						{
							if(profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().get(k).getCollectionItems().get(l).getParentGooruOid()!=null)
							{
							folderIdValGbl = profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().get(k).getCollectionItems().get(l).getParentGooruOid();
							collectionImage.getElement().setAttribute("folderId", profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().get(k).getCollectionItems().get(l).getParentGooruOid());
							collectionTitleLbl.getElement().setAttribute("folderId", profileFolderDoMainObject.getCollectionItems().get(j).getCollectionItems().get(k).getCollectionItems().get(l).getParentGooruOid());
							}
							break;
						}
					}
					}
				}
			}
			}
			}
		}
		}

			this.lessonCode=lessonCode;
			if(this.topicId==topicId) {
				String id = null;
				if(conceptDo.getGooruOid()!=null){
					id=conceptDo.getGooruOid();
				}
				if(id!=null) {
					collectionViewer.setVisible(true);
					collectionInfo.setVisible(true);
					resourcesInside.setVisible(true);
					noCollectionLbl.setVisible(false);
					final String collectionType=StringUtil.isEmpty(conceptDo.getType())?null:conceptDo.getType();
					try {
						StringUtil.setDefaultImages(conceptDo.getType(), collectionImage, "high");

						//AppClientFactory.printSevereLogger("conceptDo.getThumbnails().getUrl()::"+conceptDo.getThumbnails().getUrl());
						if(conceptDo.getThumbnails() != null && conceptDo.getThumbnails().getUrl() != null){
							collectionImage.setUrl(StringUtil.formThumbnailName(conceptDo.getThumbnails().getUrl(),"-160x120."));
							collectionImage.addErrorHandler(new ErrorHandler() {
								@Override
								public void onError(ErrorEvent event) {
									StringUtil.setDefaultImages(collectionType, collectionImage, "high");
								}
							});
						}else{
							StringUtil.setDefaultImages(collectionType, collectionImage, "high");
						}


						if(imageHandler!=null) {
							imageHandler.removeHandler();
						}
						if(titleHandler!=null) {
							titleHandler.removeHandler();
						}
						imageHandler=collectionImage.addClickHandler(new CollectionOpenClickHandler(lessonId,conceptDo.getGooruOid(),libraryGooruOid,conceptDo,"image"));
						titleHandler=collectionTitleLbl.addClickHandler(new CollectionOpenClickHandler(lessonId,conceptDo.getGooruOid(),libraryGooruOid,conceptDo,"title"));
					  } catch (Exception e) {
						StringUtil.setDefaultImages(collectionType, collectionImage, "high");
						AppClientFactory.printSevereLogger("ProfileTopicListView 3333...."+e.getMessage());
					}

					try {
						collectionTitleLbl.setHTML(conceptDo.getTitle());
						String description = conceptDo.getGoals();
						if(description!=null&&description.length()>=97) {
							String browesr = BrowserAgent.getWebBrowserClient();
							if(browesr.contains("chrome")||browesr.contains("safari")) {
								description = description.substring(0,97)+"...";
							} else {
								description = description.substring(0,85)+"...";
							}
						}
						collectionDescriptionLbl.setHTML(description);
					} catch(Exception ex) {
						AppClientFactory.printSevereLogger("exception222 "+ex.getMessage());
					}
					setMetaDataInfo(conceptDo);
					resourcesInside.clear();
					ArrayList<ProfileLibraryDo> libraryResources =  conceptDo.getCollectionItems();
					int resourceCount = 0;
					if(libraryResources!=null) {
						if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.COMMUNITY)||AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.EPISD_LIBRARY) ){
							resourceCount = libraryResources.size();
						}else{
							if(conceptDo.getItemCount()!=null){
								resourceCount = conceptDo.getItemCount();
							}
							else{
								resourceCount = libraryResources.size();
							}

						}
						int resources=resourceCount<=4?resourceCount:4;
						String resourceText="";
						if(collectionType!=null){
							if(collectionType.equals(ASSESSMENT)){
								resourceText=resources+" "+i18n.GL_GRR_OF()+" "+i18n.GL_GRR_THE()+" "+resourceCount+" "+i18n.GL1094_1().toLowerCase();
								go2Assessment.setVisible(false);
							}else if(!collectionType.equals(ASSESSMENT_URL)){
								resourceText=resources+" "+i18n.GL_GRR_OF()+" "+i18n.GL_GRR_THE()+" "+resourceCount+" "+i18n.GL1094().toLowerCase();
								go2Assessment.setVisible(false);
							}else{
								go2Assessment.getElement().setAttribute("style", "margin-left: 77px;margin-top: 38px;");
								go2Assessment.addClickHandler(new ClickHandler() {

									@Override
									public void onClick(ClickEvent event) {
										Window.open(conceptDo.getUrl(), "", "");
									}
								});
								showAssessmentButton(true);
							}
						}
						final Label resourceCountLbl = new Label(resourceText);

						resourcesInside.add(resourceCountLbl);
						for(int i=0;i<resources;i++) {
							try {
								ProfileLibraryDo profileLibraryTemp = new ProfileLibraryDo();

								if(libraryResources.size()>0 && (libraryResources.get(i)!=null && libraryResources.get(i).getResource()!=null)) {
									profileLibraryTemp = libraryResources.get(i).getResource();
								} else {
									profileLibraryTemp = libraryResources.get(i);
								}
								final ProfileLibraryDo profileLibraryItem = profileLibraryTemp;

								String categoryString = "";
								if(profileLibraryItem.getResourceFormat()!=null){
									categoryString = profileLibraryItem.getResourceFormat().getDisplayName();
								}

								final String category = categoryString;
								final HTMLEventPanel resourcePanel = new HTMLEventPanel("");

								final HTMLEventPanel resourceCategoryIcon = new HTMLEventPanel("");

								if(conceptDo.getParentGooruOid()!= null)
								{
								resourcePanel.getElement().setAttribute("folderId", conceptDo.getParentGooruOid());
								resourceCategoryIcon.getElement().setAttribute("folderId", conceptDo.getParentGooruOid());
								}
								else
								{
									resourcePanel.getElement().setAttribute("folderId", folderIdValGbl);
									resourceCategoryIcon.getElement().setAttribute("folderId", folderIdValGbl);
								}

								resourcePanel.setStyleName(style.resourceImage());


								final Image resourceImage = new Image();
								resourceImage.setWidth("80px");
								resourceImage.setHeight("60px");

								try
								{
									String resourceTitle = profileLibraryItem.getTitle().replaceAll("\\<[^>]*>","");
									profileLibraryItem.setTitle(resourceTitle);
								} catch (Exception e){
									//e.printStackTrace();
									AppClientFactory.printSevereLogger("ProfileTopicListView"+e.getMessage());
								}
								resourceImage.setAltText(profileLibraryItem.getTitle());
								resourceImage.setTitle(profileLibraryItem.getTitle());

								final String categoryImage=categoryString;

								String sourceAttribution = "";

								final List<String> attribution = profileLibraryItem.getPublisher();
								String domainName = "";
								if(profileLibraryItem.getResourceSource()!=null&&profileLibraryItem.getResourceSource().getDomainName()!=null) {
									domainName = profileLibraryItem.getResourceSource().getDomainName();
								}
								final String domain = domainName;



								resourceCategoryIcon.addMouseOverHandler(new MouseOverHandler() {

									@Override
									public void onMouseOver(MouseOverEvent event) {
										toolTipPopupPanel.clear();
										toolTipPopupPanel.setWidget(new LibraryTopicCollectionToolTip(profileLibraryItem.getTitle(),categoryImage,attribution,profileLibraryItem.getRatings().getCount(),profileLibraryItem.getRatings().getAverage(),domain));
										toolTipPopupPanel.setStyleName("");
										toolTipPopupPanel.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 2, event.getRelativeElement().getAbsoluteTop() + 55);
										toolTipPopupPanel.show();
									}
								});

								resourceImage.addMouseOverHandler(new MouseOverHandler() {

									@Override
									public void onMouseOver(MouseOverEvent event) {
										toolTipPopupPanel.clear();
										toolTipPopupPanel.setWidget(new LibraryTopicCollectionToolTip(profileLibraryItem.getTitle(),categoryImage,attribution,profileLibraryItem.getRatings().getCount(),profileLibraryItem.getRatings().getAverage(),domain));
										toolTipPopupPanel.setStyleName("");
										toolTipPopupPanel.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 2, event.getRelativeElement().getAbsoluteTop() + 55);
										toolTipPopupPanel.show();
									}
								});

								resourceCategoryIcon.addMouseOutHandler(new MouseOutHandler() {

									@Override
									public void onMouseOut(MouseOutEvent event) {
										toolTipPopupPanel.hide();
									}
								});
								resourceImage.addMouseOutHandler(new MouseOutHandler() {

									@Override
									public void onMouseOut(MouseOutEvent event) {
										toolTipPopupPanel.hide();
									}
								});
								try {
									if(profileLibraryItem.getResourceTypeDo()!=null&&profileLibraryItem.getResourceTypeDo().getName()!=null&&profileLibraryItem.getResourceTypeDo().getName().equalsIgnoreCase("video/youtube")) {
										String youTubeIbStr = ResourceImageUtil.getYoutubeVideoId(profileLibraryItem.getUrl());
										String thumbnailUrl = ResourceImageUtil.youtubeImageLink(youTubeIbStr,Window.Location.getProtocol());
										resourceImage.setUrl(thumbnailUrl);
									} else {
										String youTubeIbStr1 = ResourceImageUtil.getYoutubeVideoId(profileLibraryItem.getUrl());
										if(youTubeIbStr1!=null && youTubeIbStr1.length()==11)
										{
											String thumbnailUrl = ResourceImageUtil.youtubeImageLink(youTubeIbStr1,Window.Location.getProtocol());
											resourceImage.setUrl(thumbnailUrl);
										}
										else
										{
										if(profileLibraryItem.getThumbnails()!=null&&profileLibraryItem.getThumbnails().getUrl()!=null&&!profileLibraryItem.getThumbnails().getUrl().isEmpty()) {
											resourceImage.setUrl(profileLibraryItem.getThumbnails().getUrl());
										} else {
											resourceImage.setUrl(DEFULT_IMAGE_PREFIX +getDetaultResourceImage(category.toLowerCase()) + PNG);
										}
										}
									}
									resourceImage.addErrorHandler(new ErrorHandler() {
										@Override
										public void onError(ErrorEvent event) {
											resourceImage.setUrl(DEFULT_IMAGE_PREFIX +getDetaultResourceImage(category.toLowerCase()) + PNG);
										}
									});
								} catch (Exception e){
									resourceImage.setUrl(DEFULT_IMAGE_PREFIX + getDetaultResourceImage(category.toLowerCase()) + PNG);
									resourceImage.setAltText(profileLibraryItem.getTitle());
									resourceImage.setTitle(profileLibraryItem.getTitle());
									//e.printStackTrace();
									AppClientFactory.printSevereLogger("ProfileTopicListView 222"+e.getMessage());
								}

								resourcePanel.addClickHandler(new ClickHandler() {
									@Override
									public void onClick(ClickEvent event) {
										try{
											folderIdVal = ((HTMLEventPanel)event.getSource()).getElement().getAttribute("folderId");
										}
										catch(Exception ex){
											folderIdVal = "";
										}
										String page = AppClientFactory.getPlaceManager().getRequestParameter(PAGE,"landing");
										if(page.equals(COURSE_PAGE)) {
											MixpanelUtil.mixpanelEvent("CoursePage_Plays_Resource");
										} else {
											MixpanelUtil.mixpanelEvent("LandingPage_Plays_Resource");
										}
										final Map<String, String> params = new HashMap<String, String>();
										params.put("id", conceptDo.getGooruOid());

										String resourceId = profileLibraryItem.getCollectionItemId();
										if(resourceId==null) {
											resourceId = profileLibraryItem.getCollectionItemId();
										}
										params.put("rid", resourceId);

										if(folderIdVal!=null && !folderIdVal.isEmpty())
										{

											AppClientFactory.getInjector().getfolderService().getTocFolders(folderIdVal,false, new SimpleAsyncCallback<FolderTocDo>() {
												@Override
												public void onSuccess(FolderTocDo folderListDo) {
													if(folderListDo.getCollectionItems().size()>0)
													{
														String folderItemId = "";
													for(int i=0;i<folderListDo.getCollectionItems().size();i++)
													{
														if(conceptDo.getGooruOid().equalsIgnoreCase(folderListDo.getCollectionItems().get(i).getGooruOid()))
														{
															folderItemId = folderListDo.getCollectionItems().get(i).getCollectionItemId();
															params.put("folderId", folderIdVal);
															params.put("folderItemId", folderListDo.getCollectionItems().get(i).getCollectionItemId());
															//AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.COLLECTION_PLAY, params);
															PlaceRequest placeRequest;
															if(collectionType.equalsIgnoreCase(ASSESSMENT)){
																placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
															}else{
																placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
															}
															AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
															break;
														}
													}
													if(folderItemId.isEmpty())
													{

														params.put("subject", AppClientFactory.getPlaceManager().getRequestParameter("subject","featured"));
														params.put("lessonId", lessonId);
														if(libraryGooruOid!=null){
															params.put("lid", libraryGooruOid);
														}
														String libraryEventId=AppClientFactory.getPlaceManager().getLibaryEventId();
														if(libraryEventId!=null){
															params.put("eventid", libraryEventId);
														}
														if(getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)) {
															params.put("library", getPlaceToken());
														}
														String standardId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
														if(standardId!=null){
															params.put("rootNodeId", standardId);
														}
														PlaceRequest placeRequest;
														if(collectionType.equalsIgnoreCase(ASSESSMENT)){
															placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
														}else{
															placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
														}
														AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
													}
													}
													else
													{

														params.put("subject", AppClientFactory.getPlaceManager().getRequestParameter("subject","featured"));
														params.put("lessonId", lessonId);
														if(libraryGooruOid!=null){
															params.put("lid", libraryGooruOid);
														}
														String libraryEventId=AppClientFactory.getPlaceManager().getLibaryEventId();
														if(libraryEventId!=null){
															params.put("eventid", libraryEventId);
														}
														if(getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)) {
															params.put("library", getPlaceToken());
														}
														String standardId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
														if(standardId!=null){
															params.put("rootNodeId", standardId);
														}
														PlaceRequest placeRequest;
														if(collectionType.equalsIgnoreCase(ASSESSMENT)){
															placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
														}else{
															placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
														}
														AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
													}
												}
											});

										}
										else
										{
											params.put("subject", AppClientFactory.getPlaceManager().getRequestParameter("subject","featured"));
											params.put("lessonId", lessonId);
											if(libraryGooruOid!=null){
												params.put("lid", libraryGooruOid);
											}
											String libraryEventId=AppClientFactory.getPlaceManager().getLibaryEventId();
											if(libraryEventId!=null){
												params.put("eventid", libraryEventId);
											}
											if(getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)) {
												params.put("library", getPlaceToken());
											}
											String standardId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
											if(standardId!=null){
												params.put("rootNodeId", standardId);
											}
											PlaceRequest placeRequest;
											if(collectionType.equalsIgnoreCase(ASSESSMENT)){
												placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
											}else{
												placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
											}
											AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
									}


									}
								});
								resourceCategoryIcon.addClickHandler(new ClickHandler() {
									@Override
									public void onClick(ClickEvent event) {
										try{
											folderIdVal = ((HTMLEventPanel)event.getSource()).getElement().getAttribute("folderId");
										}
										catch(Exception ex){
											folderIdVal = "";
										}
										String page = AppClientFactory.getPlaceManager().getRequestParameter(PAGE,"landing");
										if(page.equals(COURSE_PAGE)) {
											MixpanelUtil.mixpanelEvent("CoursePage_Plays_Resource");
										} else {
											MixpanelUtil.mixpanelEvent("LandingPage_Plays_Resource");
										}
										final Map<String, String> params = new HashMap<String, String>();
										params.put("id", conceptDo.getGooruOid());

										String resourceId = profileLibraryItem.getCollectionItemId();
										if(resourceId==null) {
											resourceId = profileLibraryItem.getCollectionItemId();
										}
										params.put("rid", resourceId);


										if(folderIdVal!=null && !folderIdVal.isEmpty())
										{
											AppClientFactory.getInjector().getfolderService().getTocFolders(folderIdVal,false, new SimpleAsyncCallback<FolderTocDo>() {
												@Override
												public void onSuccess(FolderTocDo folderListDo) {
													if(folderListDo.getCollectionItems().size()>0)
													{
														String folderItemId = "";
													for(int i=0;i<folderListDo.getCollectionItems().size();i++)
													{
														if(conceptDo.getGooruOid().equalsIgnoreCase(folderListDo.getCollectionItems().get(i).getGooruOid()))
														{
															folderItemId = folderListDo.getCollectionItems().get(i).getCollectionItemId();
															params.put("folderId", folderIdVal);
															params.put("folderItemId", folderListDo.getCollectionItems().get(i).getCollectionItemId());
															//AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.COLLECTION_PLAY, params);
															PlaceRequest placeRequest;
															if(collectionType.equalsIgnoreCase(ASSESSMENT)){
																placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
															}else{
																placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
															}
															AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
															break;
														}
													}
													if(folderItemId.isEmpty())
													{
														params.put("subject", AppClientFactory.getPlaceManager().getRequestParameter("subject","featured"));
														params.put("lessonId", lessonId);
														if(libraryGooruOid!=null){
															params.put("lid", libraryGooruOid);
														}
														String libraryEventId=AppClientFactory.getPlaceManager().getLibaryEventId();
														if(libraryEventId!=null){
															params.put("eventid", libraryEventId);
														}
														if(getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)) {
															params.put("library", getPlaceToken());
														}
														String standardId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
														if(standardId!=null){
															params.put("rootNodeId", standardId);
														}
														PlaceRequest placeRequest;
														if(collectionType.equalsIgnoreCase(ASSESSMENT)){
															placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
														}else{
															placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
														}
														AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
													}
													}
													else
													{
														params.put("subject", AppClientFactory.getPlaceManager().getRequestParameter("subject","featured"));
														params.put("lessonId", lessonId);
														if(libraryGooruOid!=null){
															params.put("lid", libraryGooruOid);
														}
														String libraryEventId=AppClientFactory.getPlaceManager().getLibaryEventId();
														if(libraryEventId!=null){
															params.put("eventid", libraryEventId);
														}
														if(getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)) {
															params.put("library", getPlaceToken());
														}
														String standardId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
														if(standardId!=null){
															params.put("rootNodeId", standardId);
														}
														PlaceRequest placeRequest;
														if(collectionType.equalsIgnoreCase(ASSESSMENT)){
															placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
														}else{
															placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
														}
														AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
													}
												}
											});

										}
										else
										{
											params.put("subject", AppClientFactory.getPlaceManager().getRequestParameter("subject","featured"));
											params.put("lessonId", lessonId);
											if(libraryGooruOid!=null){
												params.put("lid", libraryGooruOid);
											}
											String libraryEventId=AppClientFactory.getPlaceManager().getLibaryEventId();
											if(libraryEventId!=null){
												params.put("eventid", libraryEventId);
											}
											if(getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)) {
												params.put("library", getPlaceToken());
											}
											String standardId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
											if(standardId!=null){
												params.put("rootNodeId", standardId);
											}
											PlaceRequest placeRequest;
											if(collectionType.equalsIgnoreCase(ASSESSMENT)){
												placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
											}else{
												placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
											}
											AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
									}






									}
								});

								resourceCategoryIcon.addStyleName("Uc-resourceName");
								resourceCategoryIcon.addStyleName(getDetaultResourceImage(category.toLowerCase()) + SMALL);
								resourcePanel.add(resourceImage);
								resourcePanel.add(resourceCategoryIcon);
								resourcesInside.add(resourcePanel);
							} catch (Exception e){
								//e.printStackTrace();
								AppClientFactory.printSevereLogger("ProfileTopicListView 222"+e);
							}
						}
					}
				} else {
					collectionInfo.setVisible(false);
					resourcesInside.setVisible(false);

					noCollectionLbl.setVisible(true);
				}
			}
			loadingImage.setVisible(false);
			collectionViewer.setVisible(true);
			collectionInfo.setVisible(true);
	}

	public String getPlaceToken() {
		return placeToken;
	}

	private void setPlaceToken(String placeToken) {
		this.placeToken = placeToken;
	}

	private void setDefaultCollectionLbl() {
		collectionInfo.setVisible(false);
		resourcesInside.setVisible(false);

		noCollectionLbl.setVisible(true);
	}

	private void setPartnerLibraryLessonData(final ArrayList<ProfileLibraryDo> profileLibraryDoList, final String gooruOid,final String libraryGooruOid) {
		boolean isLessonHighlighted = true;
		final int count = profileLibraryDoList.size();
		if(profileLibraryDoList.size()>=LESSON_PAGE_INITIAL_LIMIT) {
			for(int i=0;i<LESSON_PAGE_INITIAL_LIMIT;i++) {
				if(i==0) {
					isLessonHighlighted = true;
				} else {
					isLessonHighlighted = false;
				}
				conceptList.add(new PartnerLessonUc(profileLibraryDoList.get(i),topicId,isLessonHighlighted, (i+1), false,libraryGooruOid));
			}
			final String subject = AppClientFactory.getPlaceManager().getRequestParameter("subject","featured");
			lessonScrollPanel.addScrollHandler(new ScrollHandler() {
				@Override
				public void onScroll(ScrollEvent event) {
					if(isScrollable) {
						isScrollable = false;
						AppClientFactory.getInjector().getLibraryService().getLibraryCoursesList(gooruOid, "public", conceptList.getWidgetCount()+"", new SimpleAsyncCallback<ProfileLibraryListDo>() {
							@Override
							public void onSuccess(ProfileLibraryListDo profileLibraryList) {
								int count = LESSON_PAGE_INITIAL_LIMIT;
								for(int j = 0; j<profileLibraryList.getSearchResult().size();j++) {
									conceptList.add(new PartnerLessonUc(profileLibraryList.getSearchResult().get(j),topicId,false, (j+count+1), true,libraryGooruOid));
								}
								LESSON_PAGE_INITIAL_LIMIT = LESSON_PAGE_INITIAL_LIMIT + profileLibraryList.getSearchResult().size();
								if(!(profileLibraryList.getCount()>LESSON_PAGE_INITIAL_LIMIT)) {
									isScrollable = false;
									LESSON_PAGE_INITIAL_LIMIT=10;
								} else {
									isScrollable = true;
								}
							}
						});
					}
				}
			});
		} else {
			for(int i=0;i<profileLibraryDoList.size();i++) {
				if(i==0) {
					isLessonHighlighted = true;
				} else {
					isLessonHighlighted = false;
				}
				conceptList.add(new PartnerLessonUc(profileLibraryDoList.get(i),topicId,isLessonHighlighted,(i+1), false,libraryGooruOid));
			}
		}
	}

	/**
	 * This method is to set the conceptDo
	 */
	public void setConceptDo(ProfileLibraryDo profileLibraryDo) {
		this.profileLibraryDo = profileLibraryDo;
	}

	private ProfileLibraryDo getProfileLibraryDo() {
		return profileLibraryDo;
	}

	protected void getStandardPrefCode(List<String> standPrefCode) {
		if(!AppClientFactory.isAnonymous()) {
			if(standPrefCode!=null){
				this.standPrefCode=standPrefCode;
				standardsFloPanel.setVisible(true);
				if(profileLibraryDo!=null){
					setMetaDataInfo(profileLibraryDo);
				}

			}else{
				standardsFloPanel.setVisible(false);
			}
		}else{
			standardsFloPanel.setVisible(true);
			if(profileLibraryDo!=null){
				setMetaDataInfo(profileLibraryDo);
			}
		}
	}

	OpenProfileCollectionHandler openProfileCollectionHandler = new OpenProfileCollectionHandler() {
		@Override
		public void openProfileCollection(ProfileLibraryDo profileLibraryDo, Integer topicId, String lessonId, String lessonLabel, String lessonCode, String libraryGooruOid) {
			setConceptData(profileLibraryDo, topicId, lessonId, lessonLabel,lessonCode,libraryGooruOid);
			Map<String, String> maps = StringUtil.splitQuery(Window.Location
					.getHref());
			String colId= profileLibraryDo.getGooruOid();
			if(maps.containsKey("emailId")){
				if(AppClientFactory.getPlaceManager().getRequestParameter("collectionId").equals(colId)){
					showPopupAfterGmailSignin();
				}
			}
		}
	};

	SetLoadingIconHandler setLoadingIconHandler = new SetLoadingIconHandler() {
		@Override
		public void setLoadingIcon(boolean isVisible, Integer topicIdCollection) {
			if(topicId==topicIdCollection) {
				collectionViewer.setVisible(!isVisible);
				loadingImage.setVisible(isVisible);
			}
		}
	};

	StandardPreferenceSettingHandler standardPreferenceSettingHandler= new StandardPreferenceSettingHandler(){
		@Override
		public List<String> getCode(List<String> standPrefCode) {
			try {

				if(!AppClientFactory.isAnonymous()){
					AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference().setCode(standPrefCode);
					getStandardPrefCode(standPrefCode);
				}else{
					getStandardPrefCode(null);
				}

			} catch (Exception e) {
				AppClientFactory.printSevereLogger("ProfileTopicListView 333"+e.getMessage());
			}
			return standPrefCode;

			}
	};

	public class OnassignCollectionBtnMouseOver implements MouseOverHandler{
		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanelNew.clear();
			toolTipPopupPanelNew.setWidget(new GlobalToolTip(i18n.GL0676()));
			toolTipPopupPanelNew.setStyleName("");
			toolTipPopupPanelNew.setPopupPosition(assignCollectionBtn.getElement().getAbsoluteLeft()+8, assignCollectionBtn.getElement().getAbsoluteTop()+10);
			toolTipPopupPanelNew.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanelNew.show();
		}
	}

	public class OnassignCollectionBtnMouseOut implements MouseOutHandler{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanelNew.hide();
		}
	}

	public class OncustomizeCollectionBtnMouseOver implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanelNew.clear();
			toolTipPopupPanelNew.setWidget(new GlobalToolTip(i18n.GL0677()));
			toolTipPopupPanelNew.setStyleName("");
			toolTipPopupPanelNew.setPopupPosition(customizeCollectionBtn.getElement().getAbsoluteLeft()+18, customizeCollectionBtn.getElement().getAbsoluteTop()+10);
			toolTipPopupPanelNew.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanelNew.show();
		}

	}

	public class OncustomizeCollectionBtnMouseOut implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanelNew.hide();
		}
	}
	public String getDetaultResourceImage(String category){
		String categoryIcon=category;
		if(category!=null){
			if(category.equalsIgnoreCase("Lesson")||category.equalsIgnoreCase("Textbook")||category.equalsIgnoreCase("Handout"))
			{
				categoryIcon=category.replaceAll("Lesson", "Text").replaceAll("Textbook", "Text").replaceAll("Handout", "Text").replaceAll("lesson", "Text").replaceAll("textbook", "Text").replaceAll("handout", "Text");
			}
			if(category.equalsIgnoreCase("Slide"))
			{
				categoryIcon=category.replaceAll("Slide","Image").replaceAll("slide","Image");
			}
			if(category.equalsIgnoreCase("Exam") || category.equalsIgnoreCase("Challenge")||categoryIcon.equalsIgnoreCase("Website"))
			{
				categoryIcon=category.replaceAll("Exam","Webpage").replaceAll("Challenge", "Webpage").replaceAll("exam","Webpage").replaceAll("challenge", "Webpage");
			}
		}
		return categoryIcon.toLowerCase();
	}

	private void setMetaDataInfo(ProfileLibraryDo profileLibraryDo) {
		collectionImage.getElement().setAttribute("collid", profileLibraryDo.getGooruOid());
		collectionTitleLbl.getElement().setAttribute("collid", profileLibraryDo.getGooruOid());
		if(profileLibraryDo.getParentGooruOid()!=null)
		{
		collectionImage.getElement().setAttribute("folderId", profileLibraryDo.getParentGooruOid());
		collectionTitleLbl.getElement().setAttribute("folderId", profileLibraryDo.getParentGooruOid());

		}
		collectionTitleLbl.getElement().setAttribute(COLLECTION_TITLE,profileLibraryDo.getTitle());
		if(AppClientFactory.isAnonymous()){
			standardsFloPanel.clear();
			standardsFloPanel.setVisible(true);
		}
		if(profileLibraryDo.getMetaInfo()!=null){
			if(profileLibraryDo.getMetaInfo().getStandards()!=null) {
				standardsFloPanel.clear();
				List<StandardFo> standardFoList = profileLibraryDo.getMetaInfo().getStandards();
				List<Map<String, String>> standardMap = new ArrayList<Map<String, String>>();
				List<Map<String, String>> tempStandardMap = new ArrayList<Map<String, String>>();

				ResourceSearchResultDo searchResultDo = new ResourceSearchResultDo();
				for(int i=0;i<standardFoList.size();i++) {
					if(isUserStandards(standardFoList.get(i).getCode())) {
						if(AppClientFactory.getPlaceManager().getRequestParameter("libtype")!=null&&standardFoList.get(i).getCode().contains("CCSS")){
						}else{
							Map<String, String> standards = new HashMap<String, String>();
							standards.put(STANDARD_CODE, standardFoList.get(i).getCode());
							standards.put(STANDARD_DESCRIPTION, standardFoList.get(i).getDescription());
							standardMap.add(standards);
							tempStandardMap.add(standards);

							if(standardFoList.get(i).getCode() == lessonCode)
							{
								standardMap.clear();
								tempStandardMap.remove(standards);
								standardMap.add(standards);
								standardMap.addAll(tempStandardMap);
							}
						}
					}
				}
				if(standardMap.size()<=0) {
//					standardsFloPanel.setVisible(false);
				} else {
					standardsFloPanel.setVisible(true);
					searchResultDo.setStandards(standardMap);
					renderStandards(standardsFloPanel, searchResultDo);
				}
			}
			}
			else
			{
				if(profileLibraryDo.getStandards() != null){
					standardsFloPanel.clear();
					List<Map<String, String>> standardMap = profileLibraryDo.getStandards();
					Iterator<Map<String, String>> iterator2 = standardMap.iterator();
					int removeCount = 0;
					while (iterator2.hasNext()) {
						Map<String, String> standard = iterator2.next();
						if(!isUserStandards(standard.get(STANDARD_CODE))) {
							if(AppClientFactory.getPlaceManager().getRequestParameter("libtype")!=null&&standard.get(STANDARD_CODE).contains("CCSS")){
							}else{
								standardMap.remove(removeCount);
							}

						}
						removeCount++;
					}

					ResourceSearchResultDo searchResultDo = new ResourceSearchResultDo();
					if(standardMap.size()<=0) {
						standardsFloPanel.setVisible(false);
					} else {
						standardsFloPanel.setVisible(true);
						searchResultDo.setStandards(standardMap);
						renderStandards(standardsFloPanel, searchResultDo);
					}
				}
			}
	}

	private boolean isUserStandards(String code) {
		boolean isUserStandards = false;
		if(!AppClientFactory.isAnonymous()) {
			outerloop:
			for(int i=0;i<standPrefCode.size();i++){
				if(code.contains(standPrefCode.get(i))){
					isUserStandards=true;
					break outerloop;
				}
			}
		} else {
//			if(AppClientFactory.getPlaceManager().getRequestParameter("subject")!=null) {
				if(AppClientFactory.getPlaceManager().getRequestParameter("libtype")!=null&&code.contains("CCSS")){
				}
				else if(AppClientFactory.getPlaceManager().getRequestParameter("libtype")==null&&code.contains("TEKS")){
				}
				else {
					isUserStandards=true;
				}
//			}
		}
		return isUserStandards;
	}

	public void renderStandards(FlowPanel standardsContainer, ResourceSearchResultDo searchResultDo) {
		if (searchResultDo.getStandards() != null) {
			List<Map<String, String>> standards = searchResultDo.getStandards();
			Iterator<Map<String, String>> iterator = standards.iterator();
			int count = 0;
			FlowPanel toolTipwidgets = new FlowPanel();
			while (iterator.hasNext()) {
				Map<String, String> standard = iterator.next();
				String stdCode = standard.get(STANDARD_CODE);
				String stdDec = standard.get(STANDARD_DESCRIPTION);
				if (count > 0) {
					if (count < 18){
						StandardSgItemVc standardItem = new StandardSgItemVc(stdCode, stdDec);
						toolTipwidgets.add(standardItem);
					}
				} else {
					DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(stdCode), new Label(stdDec), standards);
					toolTipUc.setStyleName("Uc-searchStandard");
					standardsContainer.add(toolTipUc);
				}
				count++;
			}
			if (standards.size()>18){
				final Label left = new Label("+"+(standards.size() - 18));
				toolTipwidgets.add(left);
			}
			if (searchResultDo.getStandards().size() > 1) {
				Integer moreStandardsCount = searchResultDo.getStandards().size() - 1;
				DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label("+" + moreStandardsCount), toolTipwidgets, standards);
				toolTipUc.setStyleName("");
				standardsContainer.add(toolTipUc);
			}
		}
	}

	public class CollectionOpenClickHandler implements ClickHandler {
		private String lessonId;
		private String oId;
		private String libraryGooruOid;
		private ProfileLibraryDo conceptDo;
		private String type;
		public CollectionOpenClickHandler(String lessonId, String oId,String libraryGooruOid,ProfileLibraryDo conceptDo,String type) {
			this.lessonId = lessonId;
			this.oId = oId;
			this.libraryGooruOid=libraryGooruOid;
			this.conceptDo=conceptDo;
			this.type=type;
		}
		@Override
		public void onClick(ClickEvent event) {
			final String collectionType=StringUtil.isEmpty(conceptDo.getType())?null:conceptDo.getType();
			if(collectionType.equals(ASSESSMENT_URL)){
				Window.open(conceptDo.getUrl(), "", "");
			}else{

				try{
					if(type.equalsIgnoreCase("image")){
						collectionIdVal =(String) ((Image)event.getSource()).getElement().getAttribute("collid");
						folderIdVal = (String)((Image)event.getSource()).getElement().getAttribute("folderId");
					}else if(type.equalsIgnoreCase("title")){
						collectionIdVal = (String) ((HTML)event.getSource()).getElement().getAttribute("collid");
						folderIdVal = (String) ((HTML)event.getSource()).getElement().getAttribute("folderId");
					}
				}
				catch(Exception ex){
					AppClientFactory.printSevereLogger("CollectionOpenClickHandler...:"+ex);
				}
				String page = AppClientFactory.getPlaceManager().getRequestParameter(PAGE,"landing");
				if(AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID)!=null){
					MixpanelUtil.mixpanelEvent("standardlibrary_play_collection");
				}
				if(page.equals(COURSE_PAGE)) {
					MixpanelUtil.mixpanelEvent("CoursePage_Plays_Collection");
				} else {
					MixpanelUtil.mixpanelEvent("LandingPage_Plays_Collection");
				}
				if(folderIdVal!=null && !folderIdVal.isEmpty())
				{
					final HashMap<String,String> params = new HashMap<String,String>();
					params.put("id", collectionIdVal);

					AppClientFactory.getInjector().getfolderService().getTocFolders(folderIdVal,false, new SimpleAsyncCallback<FolderTocDo>() {
						@Override
						public void onSuccess(FolderTocDo folderListDo) {
							if(folderListDo.getCollectionItems().size()>0)
							{
								String folderItemId = "";
							for(int i=0;i<folderListDo.getCollectionItems().size();i++)
							{
								if(collectionIdVal.equalsIgnoreCase(folderListDo.getCollectionItems().get(i).getGooruOid()))
								{
									folderItemId = folderListDo.getCollectionItems().get(i).getCollectionItemId();
									params.put("folderId", folderIdVal);
									params.put("folderItemId", folderListDo.getCollectionItems().get(i).getCollectionItemId());

									//AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.COLLECTION_PLAY, params);
									PlaceRequest placeRequest;
									if(collectionType.equalsIgnoreCase(ASSESSMENT)){
										placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
									}else{
										placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
									}
									AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
									break;
								}
							}
							if(folderItemId.isEmpty())
							{
								Map<String, String> params = new HashMap<String, String>();
								params.put("id", collectionIdVal);
								params.put("subject", AppClientFactory.getPlaceManager().getRequestParameter("subject","featured"));
								params.put("lessonId", lessonId);
								if(libraryGooruOid!=null){
									params.put("lid", libraryGooruOid);
								}
								String libraryEventId=AppClientFactory.getPlaceManager().getLibaryEventId();
								if(libraryEventId!=null){
									params.put("eventid", libraryEventId);
								}
								if(getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)) {
									params.put("library", getPlaceToken());
								}
								String standardId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
								if(standardId!=null){
									params.put("rootNodeId", standardId);
								}
								if(collectionType.equalsIgnoreCase(ASSESSMENT)){
									AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.ASSESSMENT_PLAY, params);
								}else{
									AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.COLLECTION_PLAY, params);
								}

							}
							}
							else
							{
								Map<String, String> params = new HashMap<String, String>();
								params.put("id", collectionIdVal);
								params.put("subject", AppClientFactory.getPlaceManager().getRequestParameter("subject","featured"));
								params.put("lessonId", lessonId);
								if(libraryGooruOid!=null){
									params.put("lid", libraryGooruOid);
								}
								String libraryEventId=AppClientFactory.getPlaceManager().getLibaryEventId();
								if(libraryEventId!=null){
									params.put("eventid", libraryEventId);
								}
								if(getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)) {
									params.put("library", getPlaceToken());
								}
								String standardId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
								if(standardId!=null){
									params.put("rootNodeId", standardId);
								}
								if(collectionType.equalsIgnoreCase(ASSESSMENT)){
									AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.ASSESSMENT_PLAY, params);
								}else{
									AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.COLLECTION_PLAY, params);
								}
							}
						}
					});

				}
				else
				{
				Map<String, String> params = new HashMap<String, String>();
				params.put("id", collectionIdVal);
				params.put("subject", AppClientFactory.getPlaceManager().getRequestParameter("subject","featured"));
				params.put("lessonId", lessonId);
				if(libraryGooruOid!=null){
					params.put("lid", libraryGooruOid);
				}
				String libraryEventId=AppClientFactory.getPlaceManager().getLibaryEventId();
				if(libraryEventId!=null){
					params.put("eventid", libraryEventId);
				}
				if(getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)) {
					params.put("library", getPlaceToken());
				}
				String standardId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
				if(standardId!=null){
					params.put("rootNodeId", standardId);
				}
				if(collectionType.equalsIgnoreCase(ASSESSMENT)){
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.ASSESSMENT_PLAY, params);
				}else{
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.COLLECTION_PLAY, params);
				}
			}

			}
		}
	}

	@UiHandler("assignCollectionBtn")
	public void onassignCollectionBtnClicked(ClickEvent clickEvent) {
		String collectionId = collectionTitleLbl.getElement().getAttribute("collid");
		if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.PROFILE_PAGE)){
			PlayerDataLogEvents.triggerProfileCollectionShareDataEvent(collectionId);
		}else{
			PlayerDataLogEvents.triggerLibraryShareDataEvent(collectionId, libraryGooruOid);
		}
		if(!isAssignPopup){
			isAssignPopup=true;
			final Map<String, String> params = StringUtil.splitQuery(Window.Location
					.getHref());
			if(params.containsKey(CUSTOMIZE)){
				params.remove(CUSTOMIZE);
			}
			AssignPopupVc successPopupVc = new AssignPopupVc(collectionId, getProfileLibraryDo().getTitle(), getProfileLibraryDo().getGoals()) {
				@Override
				public void closePoup() {
					Window.enableScrolling(true);
			        this.hide();
			    	isAssignPopup=false;
				}
			};
			Window.scrollTo(0, 0);
			successPopupVc.show();
			if (!BrowserAgent.isDevice() && AppClientFactory.isAnonymous()){
				successPopupVc.setPopupPosition(0, (Window.getClientHeight()-625)/2);
			}else if(!BrowserAgent.isDevice() && !AppClientFactory.isAnonymous()){
				successPopupVc.setPopupPosition(0, (Window.getClientHeight()-527)/2);
			}else{
				successPopupVc.center();
			}


			params.put(ASSIGN, "yes");
			params.put("collectionId", collectionId);
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
		}
	}

	@UiHandler("customizeCollectionBtn")
	public void oncustomizeCollectionBtnClicked(ClickEvent clickEvent) {
		final String collectionId = collectionTitleLbl.getElement().getAttribute("collid");
		final String collectionTitle=collectionTitleLbl.getText();
		if(!isCustomizePopup){
			isCustomizePopup=true;

			final Map<String, String> params = StringUtil.splitQuery(Window.Location
					.getHref());
			if(params.containsKey(ASSIGN)){
				params.remove(ASSIGN);
			}
			Window.scrollTo(0, 0);
			if(AppClientFactory.isAnonymous()){
				LoginPopupUc loginPopupUc=new LoginPopupUc() {
					@Override
					public	void onLoginSuccess(){
						Window.enableScrolling(false);
						remixPresenterWidget.DisableMyCollectionsPanelData(false);
						remixPresenterWidget.getLoadingImage();
						remixPresenterWidget.getUserShelfCollectionsData(collectionId, "coursebuilder",collectionTitle);
						remixPresenterWidget.getView().getAppPopUp().show();
						isCustomizePopup = false;
						remixPresenterWidget.getView().getAppPopUp().center();
						remixPresenterWidget.getView().getAppPopUp().setGlassEnabled(true);
					}
				};
				loginPopupUc.show();
				loginPopupUc.setGlassEnabled(true);
			}else{
				remixPresenterWidget.DisableMyCollectionsPanelData(false);
				remixPresenterWidget.getLoadingImage();
				remixPresenterWidget.getUserShelfCollectionsData(collectionId, "coursebuilder",collectionTitle);
				remixPresenterWidget.getView().getAppPopUp().show();
				isCustomizePopup = false;
				remixPresenterWidget.getView().getAppPopUp().center();
				remixPresenterWidget.getView().getAppPopUp().setGlassEnabled(true);
			}

			/*if (!BrowserAgent.isDevice() && AppClientFactory.isAnonymous()){
				successPopupVc.setPopupPosition(0, 30);
			}else{
				successPopupVc.show();
				successPopupVc.center();

			}*/

			params.put(CUSTOMIZE, "yes");
			params.put("collectionId", collectionId);
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
		}
	}

	/**
	 *
	 * Showing Customize or Assign popup after login with gmail account.
	 *
	 */

	private void showPopupAfterGmailSignin() {

		String collectionId = getProfileLibraryDo().getGooruOid()!= null ? getProfileLibraryDo().getGooruOid() : null;
		String colleId = AppClientFactory.getPlaceManager().getRequestParameter("collectionId")!=null ? AppClientFactory.getPlaceManager().getRequestParameter("collectionId") : null;
		String customize = AppClientFactory.getPlaceManager().getRequestParameter(CUSTOMIZE)!=null ? AppClientFactory.getPlaceManager().getRequestParameter(CUSTOMIZE) : null;
		String assign = AppClientFactory.getPlaceManager().getRequestParameter(ASSIGN)!=null ? AppClientFactory.getPlaceManager().getRequestParameter(ASSIGN) : null;
		String collectionTitle=getProfileLibraryDo().getTitle();
		if(customize!=null && customize.equals("yes")){
			if(colleId.equals(collectionId) && isVisible ){
				isVisible=false;
				Window.scrollTo(0, 0);
				remixPresenterWidget.DisableMyCollectionsPanelData(false);
				remixPresenterWidget.getLoadingImage();
				remixPresenterWidget.getUserShelfCollectionsData(collectionId, "coursebuilder",collectionTitle);
				remixPresenterWidget.getView().getAppPopUp().show();
				isCustomizePopup = false;
				remixPresenterWidget.getView().getAppPopUp().center();
				remixPresenterWidget.getView().getAppPopUp().setGlassEnabled(true);
			}

		}
		if(assign!=null && assign.equals("yes")){
			final Map<String, String> params = StringUtil.splitQuery(Window.Location
					.getHref());
			if(colleId.equals(collectionId) && isVisible){
				isVisible=false;
				AssignPopupVc assignPopup = new AssignPopupVc(collectionId, getProfileLibraryDo().getTitle(), getProfileLibraryDo().getGoals()) {
					@Override
					public void closePoup() {
						Window.enableScrolling(true);
						this.hide();
						isAssignPopup=false;
						params.remove(ASSIGN);
						PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), params);
						AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
					}
				};
				Window.scrollTo(0, 0);
				assignPopup.show();
				assignPopup.center();
				if (AppClientFactory.isAnonymous()){
					assignPopup.setPopupPosition(assignPopup.getAbsoluteLeft(), -30);
				}
				else {
					assignPopup.center();
				}

			}

		}


	}

	private void setTopicLabel(String title) {
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.PROFILE_PAGE)) {
			topicTitleLbl.setVisible(true);
			libraryTopicLbl.setVisible(false);
			topicTitleLbl.setText(title);
			topicTitleLbl.getElement().setAttribute("alt",title);
			topicTitleLbl.getElement().setAttribute("title",title);
		} else {
			topicTitleLbl.setVisible(false);
			libraryTopicLbl.setVisible(true);
			libraryTopicLbl.setText(title);
			libraryTopicLbl.getElement().setAttribute("alt",title);
			libraryTopicLbl.getElement().setAttribute("title",title);
		}
	}
	public Map<String, String> updateParams(String searchQuery) {
		Map<String, String> params = new HashMap<String,String>();
		params.put("category", "All");
		params.put("query", searchQuery);
		params.put("pageSize", "8");
		params.put("pageNum", "1");
		params.put(IsGooruSearchView.RATINGS_FLT, "5,4,3,2,1,0");
		return params;
	}
	private class OnSearchLinkClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {

			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SEARCH_RESOURCE, updateParams(searchTitle));


		}
	}

	/**
	 * This Inner class used to navigate to Folder TOC page when click on ViewAll button.
	 */

	private class ViewAllBtnClickHandler implements ClickHandler{
		String folderId="";
		String parentId="";
		public ViewAllBtnClickHandler(String folderId, String parentId){
			this.folderId=folderId;
			this.parentId=parentId;
		}
		@Override
		public void onClick(ClickEvent event) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", folderId);
			if(!parentId.equals("")){
				params.put("parentId", parentId);
			}
			if(!PlaceTokens.PROFILE_PAGE.equals(getPlaceToken())){
				params.put("libName", getPlaceToken());
			}else{
				params.put("userId", AppClientFactory.getPlaceManager().getRequestParameter("id"));
			}
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.FOLDER_TOC,params);
		}

	}

}