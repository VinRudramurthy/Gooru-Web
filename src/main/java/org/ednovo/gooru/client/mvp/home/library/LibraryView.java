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
/**
 *
 * @fileName : LibraryView.java
 *
 * @description :
 *
 * @version : 1.0
 *
 * @date: 02-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
package org.ednovo.gooru.client.mvp.home.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;
import org.ednovo.gooru.application.shared.model.library.CourseDo;
import org.ednovo.gooru.application.shared.model.library.PartnerFolderDo;
import org.ednovo.gooru.application.shared.model.library.PartnerFolderListDo;
import org.ednovo.gooru.application.shared.model.library.StandardCourseDo;
import org.ednovo.gooru.application.shared.model.library.StandardsDo;
import org.ednovo.gooru.application.shared.model.library.SubjectDo;
import org.ednovo.gooru.application.shared.model.library.TopicDo;
import org.ednovo.gooru.application.shared.model.library.UnitDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.home.event.SetDiscoverLinkEvent;
import org.ednovo.gooru.client.mvp.home.library.contributors.LibraryContributorsView;
import org.ednovo.gooru.client.mvp.home.library.events.OpenSubjectCourseEvent;
import org.ednovo.gooru.client.mvp.home.library.events.OpenSubjectCourseHandler;
import org.ednovo.gooru.client.mvp.home.library.events.SetStandardDoEvent;
import org.ednovo.gooru.client.mvp.home.library.events.SetStandardDoHandler;
import org.ednovo.gooru.client.mvp.home.library.events.SetSubjectDoEvent;
import org.ednovo.gooru.client.mvp.home.library.events.SetSubjectDoHandler;
import org.ednovo.gooru.client.uc.PaginationButtonUc;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LibraryView extends Composite implements  ClickHandler {

	@UiField
	static HTMLPanel courseTabs;

	@UiField
	HTMLPanel landingBanner;

	@UiField
	HTMLPanel container,districtSpecificPartnersMain,districtSpecificPartnersInnerMain,districtSpecificPartnerLogo,districtLibraryHeaderText,districtLibrarySubHeaderText;

	@UiField
	HTMLPanel featuredCourseTabs;

	@UiField
	HTMLPanel leftNav;

	@UiField
	HTMLPanel contentScroll, libraryMetaDataContainer;

	@UiField
	HTMLPanel contributorsContainer;

	@UiField
	HTMLPanel courseBanner;

	@UiField
	HTMLPanel featuredEducator;

	@UiField
	HTMLPanel featuredCourses,folderListPanel;

	@UiField Label featuredCousesLbl,featuredContributor,comingSoonLbl,folderTopicTitleLbl;

	@UiField HTML courseTitle;

	@UiField Anchor featuredContributorsLink;

	@UiField LibraryStyleBundle libraryStyleUc;

	@UiField HTMLPanel scrollPanel, loadingIconPanel,partnerLogo;

	@UiField Image courseImage, educatorPhoto;

	@UiField Button listViewBtn;

	private FlowPanel paginationFloPanel;

	ArrayList<CourseDo> courseDo = new ArrayList<CourseDo>();

	HashMap<String, SubjectDo> courseMap = new HashMap<String, SubjectDo>();

	HashMap<String, StandardsDo> standardsMap = new HashMap<String, StandardsDo>();

	CourseDo subjectCourseDo = new CourseDo();
	String onRefCourseId=null;
	List<UnitDo> unitDoListTemp;

	private static final String COURSE_PAGE = "course-page";
	private static final String FEATURED_CONTRIBUTORS = "featured-contributors";
	private static final String FEATURED_COURSE_PAGE = "featured-course";
	private static final String LIBRARY_PAGE = "page";
	private static final String COURSE_ID = "courseId";
	private static final String UNIT_ID = "unitId";
	private static final String STANDARD_ID = "standardId";
	private static final String SUBJECT_NAME = "subject";
	private static final String FEATURED_LABEL = "featured";
	private static final String ACTIVE_STYLE = "active";
	private static final String CALLBACK = "callback";

	private static final String COURSEMAPDATASERIALIZEDSTR = "courseMapDataSerializedStr";

	private String defaultCourseId = "";
	private String previousCallBack = "";
	private String previousCourseId = "";
	private boolean isUnitLoaded = false;
	private Integer selectedPage = 1;
	private Integer totalPages = 0;
	private Integer totalCount = 0;
	private String subjectListId = "";
	private String unitListId = "";
	private String standardListId = "";

	private String STANDARDS="standard";

	private final static String COURSE_DEFAULT_IMG = "../images/library/course-1000x300.png";

	private final static String STANDARD_DEFAULT_IMG = "../images/library/common_core.png";

	private final static String TEKS_MATHS = "../images/library/standards/teks_maths.png";

	private final static String TEKS_SCIENCE = "../images/library/standards/teks_science.png";

	private static final String EDUCATOR_DEFAULT_IMG = "../images/settings/setting-user-image.png";

	private static final String PNG = ".png";

	private final static String FEMALE = "female";

	private final static String MALE = "male";

	private int INITIAL_OFFSET = 0;

//	StorageJsonSerializationFactory factory = GWT.create(StorageJsonSerializationFactory.class);

	private Storage stockStore = Storage.getLocalStorageIfSupported();

	LibraryMenuNav libraryMenuNavigation = null;

    private String placeToken = null;

	private static final String PREVIOUS = "PREVIOUS";

	private static final String NEXT = "NEXT";

	private static final String PARTNER_PAGE = "partner-page";

	private String standardLibraryName = "";

	private static final String TEXAS = "TEKS";

	private static final String CCSS = "ccss";

	private static final String SHARING_TYPE = "public";

	ArrayList<PartnerFolderDo> partnerFolderList = new ArrayList<PartnerFolderDo>();

	private static LibraryViewUiBinder uiBinder = GWT.create(LibraryViewUiBinder.class);

	interface LibraryViewUiBinder extends UiBinder<Widget, LibraryView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public LibraryView(String placeToken) {
		initWidget(uiBinder.createAndBindUi(this));
		setPlaceToken(placeToken);
		setDebugId();
		Window.scrollTo(0, 0);
		AppClientFactory.getEventBus().addHandler(OpenSubjectCourseEvent.TYPE, openSubjectCourseHandler);
		AppClientFactory.getEventBus().addHandler(SetSubjectDoEvent.TYPE, setSubjectDoHandler);
		AppClientFactory.getEventBus().addHandler(SetStandardDoEvent.TYPE, setStandardDoHandler);
		loadingIconPanel.setVisible(false);
		courseImage.getElement().getStyle().setWidth(100, Unit.PCT);
		courseImage.setHeight("300px");
		featuredCousesLbl.getElement().setId("lblFeaturedCousesLbl");
		featuredCourses.getElement().setId("pnlFeaturedCourses");
		partnerLogo.getElement().setId("pnlPartnerLogo");
		courseImage.getElement().setId("imgCourseImage");
		courseTitle.getElement().setId("lblCourseTitle");
		educatorPhoto.getElement().setId("imgEducatorPhoto");
		featuredContributor.getElement().setId("lblFeaturedContributor");
		featuredContributorsLink.getElement().setId("lnkFeaturedContributorsLink");
		scrollPanel.getElement().setId("pnlScrollPanel");
		libraryMetaDataContainer.getElement().setId("pnlLibraryMetaDataContainer");
		loadingIconPanel.getElement().setId("pnlLoadingIconPanel");
		contributorsContainer.getElement().setId("pnlContributorsContainer");
		listViewBtn.addStyleName(libraryStyleUc.listViewBtnStyle());
		StringUtil.setAttributes(listViewBtn.getElement(), "listViewBtn", listViewBtn.getText(), listViewBtn.getText());
		StringUtil.setAttributes(folderTopicTitleLbl.getElement(), "folderTopicTitleLbl", "", "");
		StringUtil.setAttributes(folderListPanel.getElement(), "folderListPanel", "", "");
		folderListPanel.setVisible(false);
	}

	/**
	 *
	 * @function setDebugId
	 *
	 * @created_date : 02-Dec-2013
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
	public void setDebugId() {
		if(getPlaceToken().equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)) {
			partnerLogo.setVisible(false);
			contributorsContainer.setVisible(false);
			courseBanner.setVisible(false);
			featuredEducator.setVisible(false);
			courseTabs.setVisible(false);
			landingBanner.setVisible(false);
			featuredContributorsLink.setVisible(false);
			featuredCourseTabs.setVisible(false);
			featuredCousesLbl.setVisible(false);
		} else {
			if(getPlaceToken().equalsIgnoreCase(PlaceTokens.RUSD_LIBRARY)) {
				partnerLogo.setStyleName(libraryStyleUc.rusdPartnerLogo());
				partnerLogo.setVisible(true);
				partnerLogo.getElement().getStyle().setRight(10, Unit.PX);
				landingBanner.setVisible(false);
			} else if(getPlaceToken().equalsIgnoreCase(PlaceTokens.EPISD_LIBRARY)) {
				partnerLogo.setStyleName(libraryStyleUc.episdPartnerLogo());
				partnerLogo.setVisible(true);
				partnerLogo.getElement().getStyle().setRight(10, Unit.PX);
				landingBanner.setVisible(false);
			}else if(getPlaceToken().equalsIgnoreCase(PlaceTokens.SAUSD_LIBRARY)) {
				partnerLogo.setStyleName(libraryStyleUc.sausdPartnerLogo());
				partnerLogo.setVisible(true);
				partnerLogo.getElement().getStyle().setRight(10, Unit.PX);
				landingBanner.setVisible(false);
			}if(getPlaceToken().equalsIgnoreCase(PlaceTokens.YCGL_LIBRARY)) {
				partnerLogo.setStyleName(libraryStyleUc.rusdPartnerLogo());
				partnerLogo.setVisible(true);
				partnerLogo.getElement().getStyle().setRight(10, Unit.PX);
				landingBanner.setVisible(false);
			} else {
				partnerLogo.setVisible(false);
			}

			contributorsContainer.setVisible(false);
			courseBanner.setVisible(false);
			featuredEducator.setVisible(false);
			libraryMenuNavigation = new LibraryMenuNav(getPlaceToken());
			courseTabs.add(libraryMenuNavigation);
			landingBanner.add(new LibraryBannerView(getPlaceToken()));
			featuredContributorsLink.setText(i18n.GL1005());
			featuredContributorsLink.getElement().setAttribute("alt",i18n.GL1005());
			featuredContributorsLink.getElement().setAttribute("title",i18n.GL1005());
			featuredContributorsLink.setTitle(i18n.GL0680());
			featuredContributorsLink.setHref("#"+getPlaceToken()+"&page="+FEATURED_CONTRIBUTORS);
		}
	}

	@Override
	public void onLoad() {

		  Boolean isIpad = !!Navigator.getUserAgent().matches("(.*)iPad(.*)");
		  Boolean isAndriod = !!Navigator.getUserAgent().matches("(.*)Android(.*)");
		  Boolean isWinDskp = !!Navigator.getUserAgent().matches("(.*)NT(.*)");

		courseTabs.getElement().getStyle().setPosition( (isIpad && !StringUtil.IPAD_MESSAGE_Close_Click) || (isAndriod && !StringUtil.IPAD_MESSAGE_Close_Click) ? Position.RELATIVE : Position.FIXED);
		courseTabs.getElement().setId("courseTabs");
		container.getElement().setId("container");

		featuredCourseTabs.getElement().setId("featuredCourseTabs");
		leftNav.getElement().setId("leftNav");
		contentScroll.getElement().setId("contentScroll");
		courseBanner.getElement().setId("courseBanner");
		featuredEducator.getElement().setId("featuredEducator");

		if(getPlaceToken().equalsIgnoreCase(PlaceTokens.RUSD_LIBRARY)) {
			landingBanner.getElement().setId("landingRusdBanner");
			landingBanner.setHeight("250px");
			featuredCousesLbl.setText(i18n.GL0588());
			featuredCousesLbl.getElement().setAttribute("alt",i18n.GL0588());
			featuredCousesLbl.getElement().setAttribute("title",i18n.GL0588());
		} else if(getPlaceToken().equalsIgnoreCase(PlaceTokens.EPISD_LIBRARY)) {
			landingBanner.getElement().setId("landingRusdBanner");
			landingBanner.setHeight("250px");
			featuredCousesLbl.setText(i18n.GL3504());
			featuredCousesLbl.getElement().setAttribute("alt",i18n.GL3504());
			featuredCousesLbl.getElement().setAttribute("title",i18n.GL3504());
		}  else if(getPlaceToken().equalsIgnoreCase(PlaceTokens.SAUSD_LIBRARY)) {
			landingBanner.getElement().setId("landingSausdBanner");
			landingBanner.setHeight("250px");
			featuredCousesLbl.setText(i18n.GL1901());
			featuredCousesLbl.getElement().setAttribute("alt",i18n.GL1901());
			featuredCousesLbl.getElement().setAttribute("title",i18n.GL1901());
		}if(getPlaceToken().equalsIgnoreCase(PlaceTokens.YCGL_LIBRARY)) {
			landingBanner.getElement().setId("landingRusdBanner");
			landingBanner.setHeight("250px");
			featuredCousesLbl.setText(i18n.GL0588());
			featuredCousesLbl.getElement().setAttribute("alt",i18n.GL0588());
			featuredCousesLbl.getElement().setAttribute("title",i18n.GL0588());
		} else {
			landingBanner.getElement().setId("landingBanner");
			featuredCousesLbl.setText(i18n.GL0587());
			featuredCousesLbl.getElement().setAttribute("alt",i18n.GL0587());
			featuredCousesLbl.getElement().setAttribute("title",i18n.GL0587());
		}
	}

	/**
	 *
	 * @function setLibraryTopicListData
	 *
	 * @created_date : 11-Dec-2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param topicDoList
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setLibraryTopicListData(final ArrayList<TopicDo> topicDoList) {
		contentScroll.clear();
		try {
			for(int i = 0; i <topicDoList.size(); i++) {
				contentScroll.add(new LibraryTopicListView(topicDoList.get(i), (i+1), getPlaceToken()));
			}
			loadingPanel(false);
		} catch (Exception e) {
			AppClientFactory.printSevereLogger("LibraryView setLibraryTopicListData:::"+e);
		}
	}

	public void setLibraryConceptOnlyData(ArrayList<ConceptDo> conceptDoList, final Integer collectionTotalCount) {
		contentScroll.clear();
		try {
			for(int i = 0; i <conceptDoList.size(); i++) {
				contentScroll.add(new LibraryTopicListView(conceptDoList.get(i), (i+1), getPlaceToken()));
			}

			if(collectionTotalCount!=null&&collectionTotalCount>10) {
				paginationFloPanel = new FlowPanel();
				paginationFloPanel.setStyleName(libraryStyleUc.paginationPanel());
				totalCount = collectionTotalCount;
				totalPages = (collectionTotalCount/10)+1;
				if (selectedPage > 1) {
					paginationFloPanel.add(new PaginationButtonUc(selectedPage - 1, PREVIOUS, this));
				}
				int page = selectedPage < 10 ? 1 : selectedPage - 8;
				for (int count = 1; count < 10 && page <= totalPages; page++, ++count) {
					paginationFloPanel.add(new PaginationButtonUc(page, page == selectedPage, this));
				}
				if (selectedPage < totalPages) {
					paginationFloPanel.add(new PaginationButtonUc(selectedPage + 1, NEXT, this));
				}
				contentScroll.add(paginationFloPanel);
			}
			loadingPanel(false);
		} catch (Exception e) {
			AppClientFactory.printSevereLogger("LibraryView setLibraryConceptOnlyData::"+e);
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() instanceof PaginationButtonUc) {
			int pageNumber = ((PaginationButtonUc) event.getSource()).getPage();
			selectedPage = pageNumber;
			int pageSize = (10 * (selectedPage-1))-1;
			INITIAL_OFFSET = pageSize < 0 ? 0 : pageSize+1;
			if(subjectListId==null) {
				subjectListId = FEATURED_LABEL;
			}
			getTopicsOnPagination(subjectListId, unitListId, INITIAL_OFFSET, totalCount,standardListId);
		}
	}

	/**
	 *
	 * @function loadContributorsPage
	 *
	 * @created_date : 03-Dec-2013
	 *
	 * @description
	 *
	 * @param placeToken
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public void loadContributorsPage(String callBack, String placeToken) {
		if (placeToken.equalsIgnoreCase(PlaceTokens.LIFEBOARD) ||
				placeToken.equalsIgnoreCase(PlaceTokens.AUTODESK) ||
				placeToken.equalsIgnoreCase(PlaceTokens.COMMUNITY)||
				placeToken.equalsIgnoreCase(PlaceTokens.FTE)||
				placeToken.equalsIgnoreCase(PlaceTokens.GEOEDUCATION)||
				placeToken.equalsIgnoreCase(PlaceTokens.NGC)||
				placeToken.equalsIgnoreCase(PlaceTokens.FINCAPINC)||
				placeToken.equalsIgnoreCase(PlaceTokens.ONR)||
				placeToken.equalsIgnoreCase(PlaceTokens.PSDPAL)||
				placeToken.equalsIgnoreCase(PlaceTokens.LESSONOPOLY)||
				placeToken.equalsIgnoreCase(PlaceTokens.WSPWH)||
				placeToken.equalsIgnoreCase(PlaceTokens.YOUTHVOICES) ||
				placeToken.equalsIgnoreCase(PlaceTokens.ESYP)||
				placeToken.equalsIgnoreCase(PlaceTokens.CCST_Cal_TAC)||
				placeToken.equalsIgnoreCase(PlaceTokens.TICAL)||
				placeToken.equalsIgnoreCase(PlaceTokens.ASPIRE_EPACS)){
			container.getElement().getStyle().setWidth(1000, Unit.PX);
		}else{
			container.getElement().getStyle().clearWidth();
		}
		setPlaceToken(placeToken);
		String courseId = AppClientFactory.getPlaceManager().getRequestParameter(COURSE_ID);
		String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UNIT_ID);
		String subjectId = AppClientFactory.getPlaceManager().getRequestParameter(SUBJECT_NAME);
		String standardId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
		subjectListId = subjectId;
		unitListId = unitId;
		standardListId = standardId;
		selectedPage = 1;
		String callBackSignup = AppClientFactory.getPlaceManager().getRequestParameter(CALLBACK,null);
		String discoverUrl = Window.Location.getHref();
		if (!AppClientFactory.isAnonymous()){
			final String loginType = AppClientFactory.getLoggedInUser().getLoginType() !=null ? AppClientFactory.getLoggedInUser().getLoginType() : "";
			int flag = AppClientFactory.getLoggedInUser().getViewFlag();
			if (flag<=7 && loginType.equalsIgnoreCase("Credential")){
				Window.enableScrolling(false);
			}else{
				Window.enableScrolling(true);
			}
		}else{
			Window.enableScrolling(true);
		}

		if(callBack.equalsIgnoreCase(FEATURED_CONTRIBUTORS)){
			discoverUrl.replaceAll("&page="+FEATURED_CONTRIBUTORS, "");
			AppClientFactory.fireEvent(new SetDiscoverLinkEvent(discoverUrl));
			featuredCourseTabs.setVisible(false);
			featuredCousesLbl.setVisible(false);
			landingBanner.setVisible(false);
			container.setVisible(false);
			contributorsContainer.setVisible(true);
			if((callBack!=previousCallBack)||(courseId!=previousCourseId)) {
				if(courseMap!=null&&courseMap.get("featured")!=null) {
						setFeaturedCourseWidgets(courseMap.get("featured").getData(), true);
				} else {
					getFeaturedCourses(FEATURED_LABEL, false);
				}
			}
			if(contributorsContainer.getWidgetCount()<=0) {
				contributorsContainer.add(new LibraryContributorsView(getPlaceToken()));
			}
			libraryMenuNavigation.setTabSelection(FEATURED_CONTRIBUTORS);
		} else if(callBack.equalsIgnoreCase(COURSE_PAGE)){
			AppClientFactory.fireEvent(new SetDiscoverLinkEvent(discoverUrl));
			landingBanner.setVisible(false);
			contributorsContainer.setVisible(false);
			featuredCourseTabs.setVisible(false);
			featuredCousesLbl.setVisible(false);
			if(subjectId.equals(STANDARDS)) {
				featuredContributorsLink.setVisible(false);
			} else {
				featuredContributorsLink.setVisible(true);
			}
			featuredEducator.setVisible(true);
			courseBanner.setVisible(true);
			container.setVisible(true);

			if(standardId == null) {

				String subjectName = getSubjectNameBySubjectId(courseMap, subjectId);
				CourseDo courseDo = null;
				if(subjectName!=null&&courseMap.get(subjectName)!=null&&courseId!=null) {
					libraryMenuNavigation.setTabSelection(subjectName);
					courseDo = getCourseDoFromCourseId(courseMap.get(subjectName), courseId);
					getUnitDataFromService(courseDo, subjectId, courseId);
					//setCourseData(courseDo);
				} else {
					getFeaturedCourses(FEATURED_LABEL, true);
				}
			} else {
				String subjectName = STANDARDS;
				CourseDo courseDo = null;
				if(subjectName!=null&&standardsMap.get(subjectName)!=null&&standardsMap.get(subjectName).getData()!=null&&courseId!=null) {
					libraryMenuNavigation.setTabSelection(subjectName);
					courseDo = getCourseDoFromCourseIdStandards(standardsMap.get(STANDARDS), standardId, courseId);
					setCourseData(courseDo);
				} else {
					libraryMenuNavigation.getTaxonomyData(subjectName,subjectId,courseId);
				}
			}

		} else if(callBack.equalsIgnoreCase(FEATURED_COURSE_PAGE)){
			libraryMenuNavigation.setTabSelection(FEATURED_LABEL);
			if(callBackSignup!=null) {
				Window.enableScrolling(false);
			}
			AppClientFactory.fireEvent(new SetDiscoverLinkEvent(discoverUrl));
			contributorsContainer.setVisible(false);
			featuredEducator.setVisible(false);
			courseBanner.setVisible(false);
			landingBanner.setVisible(true);
			container.setVisible(true);
			featuredCourseTabs.setVisible(true);
			featuredCousesLbl.setVisible(true);

			if((callBack!=previousCallBack)||(courseId!=previousCourseId)) {
				if(courseMap!=null&&courseMap.get("featured")!=null) {
						setFeaturedCourseWidgets(courseMap.get("featured").getData(), true);
				} else {
					getFeaturedCourses(FEATURED_LABEL, false);
				}
			}
		} else if (callBack.equalsIgnoreCase(PARTNER_PAGE)) {
			landingBanner.setVisible(false);
			contributorsContainer.setVisible(false);
			//For setting the partner's website data
			featuredEducator.setVisible(true);
			educatorPhoto.setVisible(false);
			featuredContributor.setVisible(false);
			featuredContributorsLink.setVisible(true);

			featuredCourseTabs.setVisible(false);
			featuredCousesLbl.setVisible(false);
			courseBanner.setVisible(true);
			container.setVisible(true);
		}
		this.previousCallBack = callBack;
		this.previousCourseId = courseId;
	}

	private void getRefreshedSubjects() {

	}

	/**
	 *
	 * @function setFeaturedCourses
	 *
	 * @created_date : 04-Dec-2013
	 *
	 * @description
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public void getFeaturedCourses(final String featuredLabel, final boolean isNotHomePage) {
		String standardId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
		if (featuredLabel!=null){
			if(standardId != null)
			{
				AppClientFactory.getInjector().getLibraryService().getSubjectsForStandards(featuredLabel, getPlaceToken(), new SimpleAsyncCallback<HashMap<String, StandardsDo>>() {
					@Override
					public void onSuccess(HashMap<String, StandardsDo> result) {
						standardsMap = result;
						libraryMenuNavigation.setSubjectPanelIdsForStandards(standardsMap);
						setFeaturedCourseWidgets(standardsMap.get(featuredLabel).getData().get(0).getCourse(), false);
					}
				});
			}
			else
			{
				String map = null;
				final String libraryToken = StringUtil.getPublicLibraryName(getPlaceToken());
				Map<String, String> params = new HashMap<String,String>();
				try {
					params = StringUtil.splitQuery(Window.Location.getHref());
				} catch (Exception e) {
					AppClientFactory.printSevereLogger("LibraryView getFeaturedCourses:::"+e);
				}

				if(stockStore!=null&&stockStore.getItem(libraryToken+COURSEMAPDATASERIALIZEDSTR)!=null&&params.size()==0){
					map = stockStore.getItem(libraryToken+COURSEMAPDATASERIALIZEDSTR);

					deserializeAndDisplay(featuredLabel, isNotHomePage, libraryToken, map, onRefCourseId);
				} else {

					if((AppClientFactory.getPlaceManager().getRequestParameter("page")!=null?AppClientFactory.getPlaceManager().getRequestParameter("page"):"").equals("featured-course")){
						onRefCourseId = AppClientFactory.getPlaceManager().getRequestParameter("courseId")!=null?AppClientFactory.getPlaceManager().getRequestParameter("courseId"):null;
					}else{
						onRefCourseId=null;
					}

					AppClientFactory.getInjector().getLibraryService().getLibrarySubjectsJson(featuredLabel, onRefCourseId, libraryToken, new SimpleAsyncCallback<String>() {

						@Override
						public void onSuccess(String subjectDoList) {
							if(stockStore!=null) {
								stockStore.setItem(libraryToken+COURSEMAPDATASERIALIZEDSTR, subjectDoList);
							}
							deserializeAndDisplay(featuredLabel, isNotHomePage, libraryToken, subjectDoList, onRefCourseId);
						}
					});
					}
				}
		}
	}

	/**
	 *
	 * @function deserializeAndDisplay
	 *
	 * @created_date : 28-Apr-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param featuredLabel
	 * @parm(s) : @param isNotHomePage
	 * @parm(s) : @param libraryToken
	 * @parm(s) : @param jsonResponse
	 * @parm(s) : @param onRefCourseId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void deserializeAndDisplay(final String featuredLabel, final boolean isNotHomePage, String libraryToken, String jsonResponse, String onRefCourseId){

		AppClientFactory.getInjector().getLibraryService().deserializeLibrarySubjects(featuredLabel, onRefCourseId, libraryToken, jsonResponse, new SimpleAsyncCallback<HashMap<String,SubjectDo>>() {

			@Override
			public void onSuccess(
					HashMap<String, SubjectDo> result) {
				courseMap = result;
				setLibraryInitialData(featuredLabel,isNotHomePage);
			}
		});
	}


	void setLibraryInitialData(String featuredLabel, boolean isNotHomePage) {
		libraryMenuNavigation.setSubjectPanelIds(courseMap);
		if(isNotHomePage) {
			String subjectId = AppClientFactory.getPlaceManager().getRequestParameter(SUBJECT_NAME);
			String courseId = AppClientFactory.getPlaceManager().getRequestParameter(COURSE_ID);
			String subjectName = getSubjectNameBySubjectId(courseMap, subjectId);
			libraryMenuNavigation.getTaxonomyData(subjectName,subjectId,courseId);
		} else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.DISCOVER)||AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)||AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)) {
			setFeaturedCourseWidgets(courseMap.get(featuredLabel).getData(), false);
		}
	}

	/**
	 * @function setFeaturedCourseWidgets
	 *
	 * @created_date : 14-Dec-2013
	 *
	 * @description
	 *
	 * @parm(s) : @param courseDoList
	 * @parm(s) : @param isFeaturedCourseSelected
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private void setFeaturedCourseWidgets(final ArrayList<CourseDo> courseDoList, boolean isFeaturedCourseSelected) {
		featuredCourses.clear();
		String courseId = AppClientFactory.getPlaceManager().getRequestParameter(COURSE_ID);
		for(int i = 0; i<courseDoList.size(); i++) {

			//TEMPORARY QA fix as there is no data
			String userName = "rusdlearns";//courseDoList.get(i).getCreator().getUsername();
			//TEMPORARY QA fix as there is no data

			featuredCourses.add(new FeaturedCourseListView(courseDoList.get(i)));
			if(!isFeaturedCourseSelected) {
				if(i==0&&(courseId==null)) {
					featuredCourses.getWidget(i).addStyleName(ACTIVE_STYLE);
					defaultCourseId = ""+courseDoList.get(i).getCodeId();
					if(getPlaceToken().equalsIgnoreCase(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equalsIgnoreCase(PlaceTokens.SAUSD_LIBRARY)){
						if(partnerFolderList.size()>0) {
							setThirdPartyCourseUnitData(partnerFolderList);
						} else {
							getPartnerWorkspaceFolders(userName);
						}
						//setUnitListData(courseDoList.get(i).getUnit());
					}
					else {
						getPopularList(courseDoList.get(i).getUnit(), courseDoList.get(i).getCodeId(), true);
					}
				}
			} else if(isFeaturedCourseSelected&&courseId==null) {
				if(i==0) {
					featuredCourses.getWidget(i).addStyleName(ACTIVE_STYLE);
					if(getPlaceToken().equalsIgnoreCase(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equalsIgnoreCase(PlaceTokens.SAUSD_LIBRARY)){
						if(partnerFolderList.size()>0) {
							setThirdPartyCourseUnitData(partnerFolderList);
						} else {
							getPartnerWorkspaceFolders(userName);
						}
						//setUnitListData(courseDoList.get(i).getUnit());
					}
					else{
						getPopularList(courseDoList.get(i).getUnit(), courseDoList.get(i).getCodeId(), true);
					}
				} else {
					featuredCourses.getWidget(i).removeStyleName(ACTIVE_STYLE);
				}
			}
		}

		final Iterator<Widget> widgets = featuredCourses.iterator();
		int widgetCount = 0;
		while (widgets.hasNext()) {
			final Widget widget = widgets.next();
			final int widgetCountTemp = widgetCount;
			FeaturedCourseListView featuredCourseListView = ((FeaturedCourseListView) widget);
			try {
				if(courseId !=null && courseId.equals(""+featuredCourseListView.getCourseId())) {
					if(getPlaceToken().equalsIgnoreCase(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equalsIgnoreCase(PlaceTokens.SAUSD_LIBRARY)){
						if(courseDoList.get(widgetCount).getParentId()!=null) {
							if(partnerFolderList.size()>0) {
								setThirdPartyCourseUnitData(partnerFolderList);
							} else {
								getPartnerWorkspaceFolders(courseDoList.get(widgetCount).getCreator().getUsername());
							}
						} else {
							setUnitListData(courseDoList.get(widgetCount).getUnit());
						}
					}
					else{
						getPopularList(courseDoList.get(widgetCount).getUnit(), courseDoList.get(widgetCount).getCodeId(), true);
					}
					widget.addStyleName(ACTIVE_STYLE);
				}
			} catch (Exception e) {
				AppClientFactory.printSevereLogger("LibraryView setFeaturedCourseWidgets:::"+e);
			}
			featuredCourseListView.getfeaturedCoursePanel().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

					MixpanelUtil.mixpanelEvent("FeaturedCourse_SelectsCourse");
					final Iterator<Widget> widgetsPanel = featuredCourses.iterator();
					while (widgetsPanel.hasNext()) {
						widgetsPanel.next().removeStyleName(ACTIVE_STYLE);
					}
					widget.addStyleName(ACTIVE_STYLE);
					if(getPlaceToken().equalsIgnoreCase(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equalsIgnoreCase(PlaceTokens.SAUSD_LIBRARY)){
						if(courseDoList.get(widgetCountTemp).getParentId()!=null) {
							if(partnerFolderList.size()>0) {
								setThirdPartyCourseUnitData(partnerFolderList);
							} else {
								getPartnerWorkspaceFolders(courseDoList.get(widgetCountTemp).getCreator().getUsername());
							}
						} else {
							getUnitDataFromService(courseDoList.get(widgetCountTemp), "featured", courseDoList.get(widgetCountTemp).getCodeId()+"");
							//setUnitListData(courseDoList.get(widgetCountTemp).getUnit());
						}
					}
					else{
						getUnitDataFromService(courseDoList.get(widgetCountTemp), "featured", courseDoList.get(widgetCountTemp).getCodeId()+"");
					}
					Map<String,String> params = new HashMap<String, String>();
					params.put(LIBRARY_PAGE, FEATURED_COURSE_PAGE);
					params.put(COURSE_ID, ""+((FeaturedCourseListView) widget).getCourseId());
					AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
				}
			});
			widgetCount++;
		}
	}


	/**
	 * @param unitDoList
	 * @function setUnitListData
	 *
	 * @created_date : 04-Dec-2013
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
	public void setUnitListData(final List<UnitDo> unitDoList) {
		String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UNIT_ID);
		final String standardsId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
		final String standardsLibraryType = AppClientFactory.getPlaceManager().getRequestParameter("libtype");
		final String subjectId = AppClientFactory.getPlaceManager().getRequestParameter(SUBJECT_NAME, FEATURED_LABEL);
		leftNav.clear();
		if(unitDoList != null)
		{
		for(int i = 0; i<unitDoList.size(); i++) {
			leftNav.add(new LibraryUnitMenuView(unitDoList.get(i)));
			if(i==0&&(unitId==null)) {
				leftNav.getWidget(i).addStyleName(libraryStyleUc.unitLiActive());
				unitListId = unitDoList.get(i).getCodeId()+"";
				if(unitDoList.get(i).getTopic()!=null&&unitDoList.get(i).getTopic().size()>0) {
					setLibraryTopicListData(unitDoList.get(i).getTopic());
					folderTopicTitleLbl.setText(unitDoList.get(i).getLabel());
					//listAllBtn.addClickHandler(new ListAllBtnHandler(unitDoList.get(i).getTopic()));

				} else {
					setLibraryConceptOnlyData(unitDoList.get(i).getCollection(), unitDoList.get(i).getCount());
				}
			}
		}
		}
		else
		{
			loadingPanel(true);
		}

		int widgetCount = 0;
		final Iterator<Widget> widgets = leftNav.iterator();
		while (widgets.hasNext()) {
			final Widget widget = widgets.next();
			final int widgetCountTemp = widgetCount;
			final LibraryUnitMenuView libraryUnitMenuView = ((LibraryUnitMenuView) widget);
			try {
				if(unitId != null && unitId.equals(libraryUnitMenuView.getUnitId())) {
					widget.addStyleName(libraryStyleUc.unitLiActive());
					if(widgetCountTemp==0) {
						unitListId = unitDoList.get(widgetCountTemp).getCodeId()+"";
						if(unitDoList.get(widgetCountTemp).getTopic()!=null&&unitDoList.get(widgetCountTemp).getTopic().size()>0) {
							setLibraryTopicListData(unitDoList.get(widgetCountTemp).getTopic());

						} else {
							setLibraryConceptOnlyData(unitDoList.get(widgetCountTemp).getCollection(), libraryUnitMenuView.getChildCount());
						}
					} else {
						unitListId = unitDoList.get(widgetCountTemp).getCodeId()+"";
						loadingPanel(true);
						getTopicsOnPagination(subjectId, libraryUnitMenuView.getUnitId(), INITIAL_OFFSET, libraryUnitMenuView.getChildCount(),standardsId);
					}
				}
			} catch(Exception e) {
				AppClientFactory.printSevereLogger("LibraryView setUnitListData:::"+e);
			}

			libraryUnitMenuView.getUnitMenuItemPanel().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final Iterator<Widget> widgetsPanel = leftNav.iterator();
					unitListId = unitDoList.get(widgetCountTemp).getCodeId()+"";
					while (widgetsPanel.hasNext()) {
						widgetsPanel.next().removeStyleName(libraryStyleUc.unitLiActive());
					}
					widget.addStyleName(libraryStyleUc.unitLiActive());
					folderTopicTitleLbl.setText(libraryUnitMenuView.getTitle());
					String callBack = AppClientFactory.getPlaceManager().getRequestParameter(LIBRARY_PAGE,FEATURED_COURSE_PAGE);
					String courseId = AppClientFactory.getPlaceManager().getRequestParameter(COURSE_ID,null);
					if(unitDoList.get(widgetCountTemp).getLabel().equalsIgnoreCase("Popular")){
						MixpanelUtil.mixpanelEvent("library_clicks_popular");
						if(widgetCountTemp==0 || widgetCountTemp==1) {
							if(unitDoList.get(widgetCountTemp).getTopic()!=null&&unitDoList.get(widgetCountTemp).getTopic().size()>0) {
								setLibraryTopicListData(unitDoList.get(widgetCountTemp).getTopic());
							} else {
								setLibraryConceptOnlyData(unitDoList.get(widgetCountTemp).getCollection(), libraryUnitMenuView.getChildCount());
							}
						} else {
							loadingPanel(true);
							getTopicsOnPagination(subjectId, libraryUnitMenuView.getUnitId(), INITIAL_OFFSET, libraryUnitMenuView.getChildCount(),standardsId);
						}
					}
					else
					{
						if(widgetCountTemp==0) {
							if(unitDoList.get(widgetCountTemp).getTopic()!=null&&unitDoList.get(widgetCountTemp).getTopic().size()>0) {
								setLibraryTopicListData(unitDoList.get(widgetCountTemp).getTopic());
							} else {
								setLibraryConceptOnlyData(unitDoList.get(widgetCountTemp).getCollection(), libraryUnitMenuView.getChildCount());
							}
						} else {
							loadingPanel(true);
							getTopicsOnPagination(subjectId, libraryUnitMenuView.getUnitId(), INITIAL_OFFSET, libraryUnitMenuView.getChildCount(),standardsId);
						}
					}

					Map<String,String> params = new HashMap<String, String>();
					params.put(LIBRARY_PAGE, callBack);
					if(courseId!=null) {
						params.put(COURSE_ID, courseId);
					} else {
						params.put(COURSE_ID, defaultCourseId);
					}
					if(subjectId!=null) {
						params.put(SUBJECT_NAME, subjectId);
					}
					if(standardsId != null)
					{
						params.put(STANDARD_ID, standardsId);
						if(standardsLibraryType!=null) {
							params.put("libtype", standardsLibraryType);
						}
					}
					params.put(UNIT_ID, ((LibraryUnitMenuView) widget).getUnitId());
					AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
				}
			});
			widgetCount++;

		}
	}

	/**
	 *
	 * @function getTopicsOnPagination
	 *
	 * @created_date : 18-Dec-2013
	 *
	 * @description
	 *
	 * @parm(s) : @param unitId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private void getTopicsOnPagination(String subjectId, String unitId, int offset, final int totalCount, String standardsId) {
		AppClientFactory.getInjector().getLibraryService().getTopicsOnPagination(subjectId, unitId, getPlaceToken(), offset,standardsId, new SimpleAsyncCallback<ArrayList<TopicDo>>() {
			@Override
			public void onSuccess(ArrayList<TopicDo> topicDoList) {
				if(topicDoList!=null&&topicDoList.size()>0) {
					if(topicDoList.get(0).getCodeId()==null) {
						setLibraryConceptOnlyData(topicDoList.get(0).getCollection(),totalCount);
					} else {
						setLibraryTopicListData(topicDoList);
					}
				}
			}
		});
	}

	OpenSubjectCourseHandler openSubjectCourseHandler = new OpenSubjectCourseHandler() {
		@Override
		public void openSubjectCourse(String subjectCode, CourseDo courseDo) {
			getUnitDataFromService(courseDo, subjectCode, courseDo.getCodeId()+"");
		}
	};

	SetSubjectDoHandler setSubjectDoHandler = new SetSubjectDoHandler() {
		@Override
		public void setSubjectDo(String subjectCode, SubjectDo subjectDo) {
			courseMap.put(subjectCode, subjectDo);
		}
	};

	SetStandardDoHandler setStandardDoHandler = new SetStandardDoHandler() {
		@Override
		public void setStandardDo(String subjectCode, StandardsDo standardDo) {
			standardsMap.put(subjectCode, standardDo);
		}
	};

	public void getUnitDataFromService(final CourseDo courseDo, String subjectCode, String courseId) {
		AppClientFactory.getInjector().getLibraryService().getLibraryUnits(subjectCode, courseId, StringUtil.getPublicLibraryName(getPlaceToken()), new SimpleAsyncCallback<ArrayList<UnitDo>>() {
			@Override
			public void onSuccess(ArrayList<UnitDo> unitDoList) {
				courseDo.setUnit(unitDoList);
				setCourseData(courseDo);
			}
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	/**
	 *
	 * @param courseDo
	 * @function setCourseData
	 *
	 * @created_date : 09-Dec-2013
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
	public void setCourseData(final CourseDo courseDo) {

			if(StringUtil.isPartnerUser(AppClientFactory.getCurrentPlaceToken())){
				if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.LPS)){
//				courseTitle.setHTML(i18n.GL2054());
				courseTitle.setStyleName(libraryStyleUc.lpsHeader());
//				partnerLogo.setStyleName(libraryStyleUc.lpsPartnerLogo());
//				partnerLogo.setVisible(true);
				}else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.CORE_LIBRARY)){
//					courseTitle.setHTML("This library showcases collections created by a team from the California Office to Reform Education.");
					courseTitle.setStyleName(libraryStyleUc.lpsHeader());
//					partnerLogo.setStyleName(libraryStyleUc.coreDistrictLogo());
//					partnerLogo.setVisible(true);
				}else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.YESD_LIBRARY)){
					courseTitle.setStyleName(libraryStyleUc.lpsHeader());
				}
				else{
				partnerLogo.setVisible(false);
				courseTitle.removeStyleName(libraryStyleUc.lpsHeader());
				courseTitle.setHTML(courseDo.getLabel());
				courseTitle.getElement().setAttribute("alt",courseDo.getLabel());
				courseTitle.getElement().setAttribute("title",courseDo.getLabel());
				}
				educatorPhoto.setVisible(false);
				featuredContributor.setVisible(false);
				courseImage.setUrl(courseDo.getThumbnails().getUrl());
				featuredContributorsLink.setText(courseDo.getCreator().getPartnerName());
				featuredContributorsLink.setTitle(courseDo.getCreator().getPartnerName());
				featuredContributorsLink.setHref(courseDo.getCreator().getPartnerUrl());
				featuredContributorsLink.setTarget("_blank");
				if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.TICAL)){
					courseTitle.getElement().getStyle().setFontSize(33, Unit.PX);
					courseTitle.getElement().getStyle().setPaddingTop(21, Unit.PX);
				}
				if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.CORE_LIBRARY)) {
					courseImage.setHeight("190px");
					courseImage.getElement().getStyle().setMarginTop(50, Unit.PX);
					courseTitle.getElement().getStyle().setBottom(16, Unit.PX);
					courseImage.setVisible(false);
					districtSpecificPartnersMain.setVisible(true);
					districtLibraryHeaderText.getElement().setInnerText(i18n.GL2108());
					districtLibrarySubHeaderText.getElement().setInnerText(i18n.GL2172());

					districtSpecificPartnersMain.setStyleName(libraryStyleUc.districtSpecificPartnersMainCore());
					districtSpecificPartnersInnerMain.setStyleName(libraryStyleUc.districtSpecificPartnersInnerMainCore());
					districtSpecificPartnerLogo.setStyleName(libraryStyleUc.districtSpecificPartnerLogoCore());
					districtLibraryHeaderText.setStyleName(libraryStyleUc.districtLibraryHeaderTextCore());
					districtLibrarySubHeaderText.setStyleName(libraryStyleUc.districtLibrarySubHeaderTextCore());
				}else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.YESD_LIBRARY)) {
					courseImage.setHeight("190px");
					courseImage.getElement().getStyle().setMarginTop(50, Unit.PX);
					courseTitle.getElement().getStyle().setBottom(16, Unit.PX);
					courseImage.setVisible(false);
					districtSpecificPartnersMain.setVisible(true);
					districtLibraryHeaderText.getElement().setInnerText("Yuma Elementary School District ONE's Professional Learning Library");
					districtLibrarySubHeaderText.getElement().setInnerText("The Department of Learning Services offers these collections created by our Instructional Coaches, empowering educators to utilize digital tools for higher achievement and innovative excellence.");
					districtSpecificPartnersMain.setStyleName(libraryStyleUc.districtSpecificPartnersMainYesd());
					districtSpecificPartnersInnerMain.setStyleName(libraryStyleUc.districtSpecificPartnersInnerMainYesd());
					districtSpecificPartnerLogo.setStyleName(libraryStyleUc.districtSpecificPartnerLogoYesd());
					districtLibraryHeaderText.setStyleName(libraryStyleUc.districtLibraryHeaderTextYesd());
					districtLibrarySubHeaderText.setStyleName(libraryStyleUc.districtLibrarySubHeaderTextYesd());
				}else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.LPS)) {
					courseImage.setHeight("190px");
					courseImage.getElement().getStyle().setMarginTop(50, Unit.PX);
					courseTitle.getElement().getStyle().setBottom(16, Unit.PX);
					courseImage.setVisible(false);
					districtSpecificPartnersMain.setVisible(true);
					districtLibraryHeaderText.getElement().setInnerText(i18n.GL2053());
					districtLibrarySubHeaderText.getElement().setInnerText(i18n.GL2054());

					districtSpecificPartnersMain.setStyleName(libraryStyleUc.districtSpecificPartnersMainlps());
					districtSpecificPartnersInnerMain.setStyleName(libraryStyleUc.districtSpecificPartnersInnerMainlps());
					districtSpecificPartnerLogo.setStyleName(libraryStyleUc.districtSpecificPartnerLogolps());
					districtLibraryHeaderText.setStyleName(libraryStyleUc.districtLibraryHeaderTextlps());
					districtLibrarySubHeaderText.setStyleName(libraryStyleUc.districtLibrarySubHeaderTextlps());
				}
				else
				{
					courseImage.setVisible(true);
					districtSpecificPartnersMain.setVisible(false);

					//here
				}
			} else {
				educatorPhoto.setVisible(true);
				featuredContributor.setVisible(true);
				final String standardId = AppClientFactory.getPlaceManager().getRequestParameter(STANDARD_ID);
				if(courseDo!=null) {
					if(courseDo.getThumbnails()!=null&&courseDo.getThumbnails().getUrl().isEmpty()) {
						setDefaultCourseImage(standardId, courseDo.getLabel());
					} else {
						courseImage.setUrl(courseDo.getThumbnails().getUrl());
					}

					courseImage.addErrorHandler(new ErrorHandler() {
						@Override
						public void onError(ErrorEvent event) {
							setDefaultCourseImage(standardId, courseDo.getLabel());
						}
					});

					courseTitle.setHTML(courseDo.getLabel());
					courseTitle.getElement().setAttribute("alt",courseDo.getLabel());
					courseTitle.getElement().setAttribute("title",courseDo.getLabel());
					try {
						educatorPhoto.setHeight("46px");
						educatorPhoto.setWidth("46px");

						String authorName = "";
						String authorProfileImage = "";
						/// In User Object is null
						if (courseDo.getUser()!=null && courseDo.getUser().size()>0){
							int j=0;
							for (int i=0;i<courseDo.getUser().size();i++){
								j = i;
								if (courseDo.getUser().get(i).getIsOwner() !=null &&  courseDo.getUser().get(i).getIsOwner().equalsIgnoreCase("1")){
									break;
								}
							}
							if(MALE.equalsIgnoreCase(courseDo.getUser().get(j).getGender())) {
								authorName = (i18n.GL1422()+i18n.GL_SPL_FULLSTOP()+" ")+courseDo.getUser().get(j).getLastName();
							} else if(FEMALE.equalsIgnoreCase(courseDo.getUser().get(j).getGender())) {
								authorName = (i18n.GL1423()+i18n.GL_SPL_FULLSTOP()+" ")+courseDo.getUser().get(j).getLastName();
							} else {
								authorName = courseDo.getUser().get(j).getLastName();
							}


							if (courseDo.getUser().size()>1){
								featuredContributor.setText(authorName+" "+i18n.GL_GRR_AND()+" "+i18n.GL1117()+" "+i18n.GL1006()+" "+courseDo.getLabel()+". ");
								featuredContributor.getElement().setAttribute("alt",authorName+" "+i18n.GL_GRR_AND()+" "+i18n.GL1117()+" "+i18n.GL1006()+" "+courseDo.getLabel()+". ");
								featuredContributor.getElement().setAttribute("title",authorName+" "+i18n.GL_GRR_AND()+" "+i18n.GL1117()+" "+i18n.GL1006()+" "+courseDo.getLabel()+". ");
							}else{
								featuredContributor.setText(authorName+" "+" "+i18n.GL1007()+" "+courseDo.getLabel()+". ");
								featuredContributor.getElement().setAttribute("alt",authorName+" "+" "+i18n.GL1007()+" "+courseDo.getLabel()+". ");
								featuredContributor.getElement().setAttribute("title",authorName+" "+" "+i18n.GL1007()+" "+courseDo.getLabel()+". ");
							}

							authorProfileImage =AppClientFactory.getLoggedInUser().getSettings().getProfileImageUrl() + courseDo.getUser().get(j).getGooruUId()+PNG;

						}else{
							if (courseDo.getCreator() != null){
								if(MALE.equalsIgnoreCase(courseDo.getCreator().getGender())) {
									authorName = (i18n.GL1422()+i18n.GL_SPL_FULLSTOP()+" ")+courseDo.getCreator().getLastName();
								} else if(FEMALE.equalsIgnoreCase(courseDo.getCreator().getGender())) {
									authorName = i18n.GL1423()+i18n.GL_SPL_FULLSTOP()+" "+courseDo.getCreator().getLastName();
								} else {
									authorName = courseDo.getCreator().getLastName();
								}
							}

							featuredContributor.setText(authorName+" "+" "+i18n.GL1007()+" "+courseDo.getLabel()+". ");
							featuredContributor.getElement().setAttribute("alt",authorName+" "+" "+i18n.GL1007()+" "+courseDo.getLabel()+". ");
							featuredContributor.getElement().setAttribute("title",authorName+" "+" "+i18n.GL1007()+" "+courseDo.getLabel()+". ");
							if (courseDo.getCreator() != null){
								authorProfileImage =AppClientFactory.getLoggedInUser().getSettings().getProfileImageUrl() + courseDo.getCreator().getGooruUId()+PNG;
							}

						}

						educatorPhoto.setUrl(authorProfileImage);

						educatorPhoto.addErrorHandler(new ErrorHandler() {
							@Override
							public void onError(ErrorEvent event) {
								educatorPhoto.setUrl(EDUCATOR_DEFAULT_IMG);
							}
						});
					} catch (Exception e) {
						educatorPhoto.setVisible(false);
						featuredContributor.setVisible(false);
						AppClientFactory.printSevereLogger("LibraryView setCourseData:::"+e);
					}
				}

				String libraryPage = AppClientFactory.getPlaceManager().getRequestParameter(LIBRARY_PAGE,"emptyPage");
				if(!isUnitLoaded&&libraryPage.equals(COURSE_PAGE)) {
					if(getPlaceToken().equalsIgnoreCase(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equalsIgnoreCase(PlaceTokens.SAUSD_LIBRARY)){
						setUnitListData(courseDo.getUnit());
					}
					else{
						getPopularList(courseDo.getUnit(), courseDo.getCodeId(), false);
					}

					isUnitLoaded=true;
				}
				if(courseDo.getUnit()!=null) {
					if(getPlaceToken().equalsIgnoreCase(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equalsIgnoreCase(PlaceTokens.SAUSD_LIBRARY)){
						setUnitListData(courseDo.getUnit());
					}
					else{
						getPopularList(courseDo.getUnit(), courseDo.getCodeId(), true);
					}
				}
			}

	}

	private void setDefaultCourseImage(String standardId, String courseLabel) {
		if(standardId != null)
			{
				String libType = AppClientFactory.getPlaceManager().getRequestParameter("libtype");
				if(standardLibraryName.equals(TEXAS)) {
				if(courseLabel.equalsIgnoreCase("Integrated Physics and Chemistry")) {
				courseImage.setUrl(TEKS_SCIENCE);
				} else {
				courseImage.setUrl(TEKS_MATHS);
				}
				} else {
				if(libType!=null && libType.equals(TEXAS)){
				if(courseLabel.equalsIgnoreCase("Integrated Physics and Chemistry")) {
				courseImage.setUrl(TEKS_SCIENCE);
				} else {
				courseImage.setUrl(TEKS_MATHS);
				}
				}else{
				courseImage.setUrl(STANDARD_DEFAULT_IMG);
				}
				}
			}
		else
		{
		courseImage.setUrl(COURSE_DEFAULT_IMG);
		}

	}

	/**
	 *
	 * @function getSubjectNameBySubjectId
	 *
	 * @created_date : 12-Dec-2013
	 *
	 * @description
	 *
	 * @parm(s) : @param subjectList
	 * @parm(s) : @param subjectId
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public String getSubjectNameBySubjectId(HashMap<String, SubjectDo> subjectList, String subjectId) {
		for (Map.Entry<String, SubjectDo> entry : subjectList.entrySet()) {
			if(entry.getValue().getSubjectCode().equals(subjectId)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * @function getSubjectIdBySubjectName
	 *
	 * @created_date : 08-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param subjectList
	 * @parm(s) : @param subjectName
	 *
	 * @return : String
	 */
	public String getSubjectIdBySubjectName(HashMap<String, SubjectDo> subjectList, String subjectName) {
		for (Map.Entry<String, SubjectDo> entry : subjectList.entrySet()) {
			if(entry.getKey().equals(subjectName)) {
				return entry.getValue().getCode();
			}
		}
		return null;
	}

	/**
	 * @function getCourseDoFromCourseId
	 *
	 * @created_date : 13-Dec-2013
	 *
	 * @description
	 *
	 * @parm(s) : @param subjectDo
	 * @parm(s) : @param courseId
	 * @parm(s) : @return
	 *
	 * @return : CourseDo
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public CourseDo getCourseDoFromCourseId(SubjectDo subjectDo, String courseId) {
		final Iterator<CourseDo> courses = subjectDo.getData().iterator();
		while (courses.hasNext()) {
			final CourseDo courseDo = courses.next();
			String course = ""+courseDo.getCodeId();
			if(course.equals(courseId)) {
				return courseDo;
			}
		}
		return new CourseDo();
	}

	public CourseDo getCourseDoFromCourseIdStandards(StandardsDo standardsDo, String standardId, String courseId) {
		int count = 0;
		final Iterator<StandardCourseDo> standards = standardsDo.getData().iterator();
		while (standards.hasNext()) {
			int standardValue = Integer.parseInt(standardId);
			StandardCourseDo standardCourseDo = standards.next();
			if(standardCourseDo.getCodeId().intValue()==standardValue) {
				if(standardCourseDo.getLabel().contains("Texas Essential Knowledge and Skills")) {
					standardLibraryName = TEXAS;
				} else {
					standardLibraryName = CCSS;
				}
				return getCourseDofromStandard(standardsDo, courseId, count);
			}
			count++;
		}
		return new CourseDo();
	}

	public CourseDo getCourseDofromStandard(StandardsDo standardsDo, String courseId, Integer count) {
		final Iterator<CourseDo> courses = standardsDo.getData().get(count).getCourse().iterator();
		while (courses.hasNext()) {
			final CourseDo courseDo = courses.next();
			String course = ""+courseDo.getCodeId();
			if(course.equals(courseId)) {
				return courseDo;
			}
		}
		return new CourseDo();
	}

	public String getPlaceToken() {
		return placeToken;
	}

	private void setPlaceToken(String placeToken) {
		this.placeToken = placeToken;
	}

	public void getPopularList(List<UnitDo> unitDoTempList, final int courseId, boolean isRefresh) {
			unitDoListTemp = new ArrayList<UnitDo>();
			unitDoListTemp = unitDoTempList;
			setUnitListData(unitDoListTemp);
/*			if(unitDoListTemp.size()!=0){
			if(isRefresh&&(!unitDoListTemp.get(0).getLabel().contains("Popular"))) {
				AppClientFactory.getInjector().getLibraryService().getPopularCollectionsData(""+courseId, new AsyncCallback<ArrayList<ConceptDo>>(){
					@Override
					public void onSuccess(ArrayList<ConceptDo> result) {
						if(result.size()>0) {
							UnitDo unitDo = new UnitDo();
							unitDo.setCodeId(courseId);
							unitDo.setLabel(i18n.GL1008);
							unitDo.setCollection(result);
							unitDo.setCount(result.size());
							unitDoListTemp.add(0, unitDo);
						}
						setUnitListData(unitDoListTemp);
					}
					@Override
					public void onFailure(Throwable caught) {

					}
			});
			} else {
				setUnitListData(unitDoListTemp);
			}
			}
*/
	}

	public HTMLPanel getLeftNav() {
		return leftNav;
	}

	public HTMLPanel getLoadingIconPanel() {
		return loadingIconPanel;
	}

	public Label getComingSoonLabel(){
		return comingSoonLbl;
	}

	public HTMLPanel getContentScroll() {
		return contentScroll;
	}

	/**
	 * @return the folderTopicTitleLbl
	 */
	public Label getFolderTopicTitleLbl() {
		return folderTopicTitleLbl;
	}

	/**
	 * @return the listAllBtn
	 */
	public Button getListAllBtn() {
		return listViewBtn;
	}

	public void getPartnerWorkspaceFolders(String partnerName) {
		AppClientFactory.getInjector().getLibraryService().getLibraryPartnerWorkspace(partnerName, 20, SHARING_TYPE, null, getPlaceToken(), new SimpleAsyncCallback<PartnerFolderListDo>(){
			@Override
			public void onSuccess(PartnerFolderListDo result) {
				partnerFolderList = result.getSearchResult();
				setThirdPartyCourseUnitData(partnerFolderList);
			}
		});
	}

	public void getPartnerChildFolderItems(final String folderId, final int pageNumber) {
		AppClientFactory.getInjector().getLibraryService().getPartnerPaginationWorkspace(folderId,SHARING_TYPE, 20,new SimpleAsyncCallback<PartnerFolderListDo>() {
			@Override
			public void onSuccess(PartnerFolderListDo result) {
				//getView().setTopicListData(result.getSearchResult(), folderId);
			}

		});
	}

	private void setThirdPartyCourseUnitData(final ArrayList<PartnerFolderDo> folderList) {
		getLeftNav().clear();
		getContentScroll().clear();
		String folderId = AppClientFactory.getPlaceManager().getRequestParameter("id");
		int j = 0;
		for(int i = 0; i<folderList.size(); i++) {
			if(folderList.get(i).getType().equalsIgnoreCase("folder")) {
				LibraryUnitMenuView libraryUnitMenuView = new LibraryUnitMenuView(folderList.get(i));
				getLeftNav().add(libraryUnitMenuView);
				if(j==0&&folderId==null) {
					j++;
					libraryUnitMenuView.addStyleName(libraryStyleUc.unitLiActive());
					unitListId = folderList.get(i).getGooruOid();
					setTopicListData(folderList.get(i).getFolderItems(), unitListId);
					folderTopicTitleLbl.setText(folderList.get(i).getTitle());
					//getUiHandlers().getPartnerChildFolderItems(unitListId, );
				}
			}
		}

		final Iterator<Widget> widgets = getLeftNav().iterator();
		int widgetCount = 0;
		while (widgets.hasNext()) {
			final Widget widget = widgets.next();
			final LibraryUnitMenuView libraryUnitMenuView = ((LibraryUnitMenuView) widget);
			final int finalWidgetCount = widgetCount;
			libraryUnitMenuView.getUnitMenuItemPanel().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					getContentScroll().setVisible(false);
					final Iterator<Widget> widgetsPanel = getLeftNav().iterator();
					while (widgetsPanel.hasNext()) {
						widgetsPanel.next().removeStyleName(libraryStyleUc.unitLiActive());
					}
					widget.addStyleName(libraryStyleUc.unitLiActive());
					unitListId = libraryUnitMenuView.getUnitId();
					folderTopicTitleLbl.setText(libraryUnitMenuView.getTitle());
					if(finalWidgetCount==0) {
						setTopicListData(folderList.get(finalWidgetCount).getFolderItems(), unitListId);
					} else {
						//getUiHandlers().getPartnerChildFolderItems(unitListId, 1);
					}
				}
			});
			widgetCount++;
		}
	}

	public static void onClosingAndriodorIpaddiv() {
		 courseTabs.getElement().setAttribute("style", "position:fixed;");
	}

	public void setTopicListData(ArrayList<PartnerFolderDo> folderListDo, String folderId) {
		getContentScroll().clear();
		try {
			int count = 0;
			for(int i = 0; i <folderListDo.size(); i++) {
				count++;
				getContentScroll().add(new LibraryTopicListView(folderListDo.get(i), count, AppClientFactory.getCurrentPlaceToken(),null));
			}
			getContentScroll().setVisible(true);
		} catch (Exception e) {
			AppClientFactory.printSevereLogger("LibraryView setTopicListData:::"+e);
		}
	}

	public HTMLPanel getCourseTabs() {
		return courseTabs;
	}

	public HTMLPanel getContainer() {
		return container;
	}

	public void loadingPanel(boolean isVisible) {
		loadingIconPanel.setVisible(isVisible);
		contentScroll.setVisible(!isVisible);
		//folderListPanel.setVisible(!isVisible);
		libraryMetaDataContainer.setVisible(!isVisible);
	}

	/**
	 * @return the folderListPanel
	 */
	public HTMLPanel getFolderListPanel() {
		return folderListPanel;
	}


}