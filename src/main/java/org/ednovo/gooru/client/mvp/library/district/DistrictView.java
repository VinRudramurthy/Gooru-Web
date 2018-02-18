package org.ednovo.gooru.client.mvp.library.district;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.home.library.LibraryUnitMenuView;
import org.ednovo.gooru.client.mvp.library.district.metadata.LibraryMetaDataContentUc;
import org.ednovo.gooru.client.mvp.profilepage.data.item.ProfileTopicListView;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
` * 
 */
public class DistrictView extends BaseViewWithHandlers<DistrictUiHandlers> implements IsDistrictView {

	@UiField HTMLPanel courseTabs,folderListPanel;

	@UiField HTMLPanel landingBanner, container, featuredCourseTabs, leftNav, contentScroll, libraryMetaDataContainer, contributorsContainer, courseBanner, featuredEducator,
	featuredCourses, scrollPanel, loadingIconPanel,partnerLogo;

	@UiField Label courseTitle, featuredCousesLbl,featuredContributor,folderTopicTitleLbl;
	
	@UiField Anchor featuredContributorsLink;
	
	@UiField DistrictStyleBundle districtStyleUc;
	
	@UiField Image courseImage, educatorPhoto;
	
	@UiField Button listViewBtn;
	
	private String placeToken;
	
	HandlerRegistration handlerRegistration = null;
	
	DistrictMenuNav districtMenuNav = null;
	ProfileLibraryDo ProfileLibraryDoObj;
	public static boolean scrollFlag=false;
	
	private static final String FEATURED_COURSE="featured-course",COURSE_PAGE = "course-page", COURSE_ID = "courseId", FEATURED_LABEL = "featured", 
			CALLBACK = "callback", ACTIVE_STYLE = "active",LIBRARY_PAGE = "page";

	private String unitListId = "";
	
	private String parentId = "";

	private final static String COURSE_DEFAULT_IMG = "../images/library/course-1000x300.png";

	private static DistrictViewUiBinder uiBinder = GWT.create(DistrictViewUiBinder.class);
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	private int collectionCount =0;
	private int totalCollectionCount =0 ;
	interface DistrictViewUiBinder extends UiBinder<Widget, DistrictView> {
	}
	
	public DistrictView() {		
		setWidget(uiBinder.createAndBindUi(this));
		setAssets(AppClientFactory.getCurrentPlaceToken());
	}
	
	@Override
	public void onLoad() {
	}
	
	@Override
	public void loadFeaturedContributors(String callBack, String placeToken,ProfileLibraryListDo profileLibraryListDo) {
		if(callBack.equalsIgnoreCase(FEATURED_COURSE)) {
			districtMenuNav.setSubjectPanelIds(profileLibraryListDo);
			showCourseBanner(null, false);
			if(profileLibraryListDo.getSearchResult()!=null&&profileLibraryListDo.getSearchResult().size()>0) {
				setFeaturedCourseWidgets(profileLibraryListDo.getSearchResult(),false);
			}
		}
	}
	
	private void setFeaturedCourseWidgets(ArrayList<ProfileLibraryDo> profileLibDoList, boolean isFeaturedCourseSelected) {
		featuredCourses.clear();
		leftNav.clear();
		String courseId = AppClientFactory.getPlaceManager().getRequestParameter(COURSE_ID);
		
		final ArrayList<ProfileLibraryDo> courseList = profileLibDoList.get(0).getCollectionItems();
		final String libraryGooruOid=profileLibDoList.get(0).getParentGooruOid();//!=null?profileLibDoList.get(0).getParentGooruOid():"0d31b835-f481-46d7-b1ea-1e1009f878a6";
		PlayerDataLogEvents.triggerLibraryViewEvent(libraryGooruOid);
		if(districtMenuNav!=null){
			districtMenuNav.setLibraryGooruOid(libraryGooruOid);
		}
		for(int i = 0; i<courseList.size(); i++) {
			featuredCourses.add(new DistrictFeaturedView(courseList.get(i),libraryGooruOid));
			if(!isFeaturedCourseSelected) {
				if(i==0&&(courseId==null)) {
					featuredCourses.getWidget(i).addStyleName(ACTIVE_STYLE);
					setUnitList(courseList.get(i).getCollectionItems(),libraryGooruOid);
				}
			} else if(isFeaturedCourseSelected&&courseId==null) {
				if(i==0) {
					featuredCourses.getWidget(i).addStyleName(ACTIVE_STYLE);
					
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
			final DistrictFeaturedView districtFeaturedView = ((DistrictFeaturedView) widget);
			try {
				if(districtFeaturedView!=null && districtFeaturedView.getCourseId().equals(courseId)) {
					widget.addStyleName(ACTIVE_STYLE);
				}
			} catch (Exception e) {
				AppClientFactory.printSevereLogger("DistrictView setFeaturedCourseWidgets:::"+e);
			}
			districtFeaturedView.getfeaturedCoursePanel().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					leftNav.clear();
					scrollFlag = false;
					MixpanelUtil.mixpanelEvent("FeaturedCourse_SelectsCourse");
					final Iterator<Widget> widgetsPanel = featuredCourses.iterator();
					while (widgetsPanel.hasNext()) {
						widgetsPanel.next().removeStyleName(ACTIVE_STYLE);
					}
					widget.addStyleName(ACTIVE_STYLE);
					setUnitList(courseList.get(widgetCountTemp).getCollectionItems(),districtFeaturedView.getLibraryGooruOid());
				}
			});
			widgetCount++;
		}
	}
	
	public void setUnitList(final ArrayList<ProfileLibraryDo> profileLibraryDoList,String libraryGooruOid) {
		Window.addWindowScrollHandler(new LeftPanelScroll(libraryGooruOid));
		if(profileLibraryDoList.get(0).getItemCount()>20){
			totalCollectionCount = profileLibraryDoList.get(0).getItemCount();
		}
		//final String parentId=profileLibraryDoList.get(0).getParentGooruOid();
		int firstWidgetCount = leftNav.getWidgetCount();
		for(int i = 0; i<profileLibraryDoList.size(); i++) {
			LibraryUnitMenuView libraryUnitMenuView = new LibraryUnitMenuView(profileLibraryDoList.get(i),libraryGooruOid);
			leftNav.add(libraryUnitMenuView);
			libraryUnitMenuView.setWidgetCount(leftNav.getWidgetCount()+1);
			libraryUnitMenuView.setType(profileLibraryDoList.get(i).getType());
			if(firstWidgetCount==0) {
				firstWidgetCount++;
				loadingPanel(true);
				libraryUnitMenuView.addStyleName(districtStyleUc.unitLiActive());
				unitListId = profileLibraryDoList.get(i).getGooruOid();
				if(profileLibraryDoList.get(i).getType().equals("collection")||profileLibraryDoList.get(i).getType().contains("assessment")) {
					setTopicListData(profileLibraryDoList.get(i), unitListId,libraryGooruOid);
				} else {
					folderTopicTitleLbl.setText(profileLibraryDoList.get(i).getTitle());
				    parentId = profileLibraryDoList.get(i).getParentGooruOid();
					if(handlerRegistration!=null){
						handlerRegistration.removeHandler();
					}
					handlerRegistration = listViewBtn.addClickHandler(new ListAllBtnClickHandler(profileLibraryDoList.get(i).getGooruOid(),parentId));
					
					setTopicListData(profileLibraryDoList.get(i).getCollectionItems(), unitListId, profileLibraryDoList.get(i),libraryGooruOid,parentId);
				}
			}
		}
		
		final Iterator<Widget> widgets = leftNav.iterator();
		int widgetCount = 0;
		while (widgets.hasNext()) {
			final Widget widget = widgets.next();
			final LibraryUnitMenuView libraryUnitMenuView = ((LibraryUnitMenuView) widget);
			final int widgetCountTemp = widgetCount;
			libraryUnitMenuView.getUnitMenuItemPanel().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(libraryUnitMenuView.getWidgetCount()>10) {
						Window.scrollTo(0, 0);
					}
					scrollFlag = false;
					loadingPanel(true);
					final Iterator<Widget> widgetsPanel = leftNav.iterator();
					while (widgetsPanel.hasNext()) {
						final Widget widgetTxt = widgetsPanel.next();
						widgetTxt.removeStyleName(districtStyleUc.unitLiActive());
					}
					widget.addStyleName(districtStyleUc.unitLiActive());
					unitListId = libraryUnitMenuView.getUnitId();
					if(libraryUnitMenuView.getType().equals("scollection")) {
						setTopicListData(profileLibraryDoList.get(widgetCountTemp),  unitListId,libraryUnitMenuView.getLibraryGooruOid());
					} else {
						folderTopicTitleLbl.setText(libraryUnitMenuView.getTitle());
						if(handlerRegistration!=null){
							handlerRegistration.removeHandler();
						}
						handlerRegistration=listViewBtn.addClickHandler(new ListAllBtnClickHandler(libraryUnitMenuView.getUnitId(), parentId));
						if(widgetCountTemp==0) {
							totalCollectionCount=profileLibraryDoList.get(widgetCountTemp).getItemCount();
							setTopicListData(profileLibraryDoList.get(widgetCountTemp).getCollectionItems(), unitListId, profileLibraryDoList.get(widgetCountTemp),libraryUnitMenuView.getLibraryGooruOid(),parentId);
						} else {
							getUnitTopics(unitListId, profileLibraryDoList.get(widgetCountTemp),libraryUnitMenuView.getLibraryGooruOid());
							ProfileLibraryDoObj= profileLibraryDoList.get(widgetCountTemp);
						}
					}
				}
			});
			widgetCount++;
		}
	}
	
	private void getUnitTopics(final String unitListId, final ProfileLibraryDo profileLibraryDo,final String libraryGooruOid) {
		
		String sharing = "public";
		if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.LIFEBOARD)) {
			sharing = null;
		}
		
		AppClientFactory.getInjector().getLibraryService().getLibraryPaginationWorkspace(unitListId, sharing, 20,0, new SimpleAsyncCallback<ProfileLibraryListDo>() {

			@Override
			public void onSuccess(ProfileLibraryListDo profileLibraryListDo) {
				totalCollectionCount = profileLibraryListDo.getCount();
				setTopicListData(profileLibraryListDo.getSearchResult(), unitListId, profileLibraryDo,libraryGooruOid,"");
			}
		});
	}

	public void setTopicListData(ProfileLibraryDo profileLibraryDo, String folderId,String libraryGooruOid) {
		contentScroll.clear();
		try {
			setMetaDataContent(profileLibraryDo);
			contentScroll.add(new ProfileTopicListView(profileLibraryDo, 0, AppClientFactory.getCurrentPlaceToken(), "scollection",libraryGooruOid));
			loadingPanel(false);
		} catch (Exception e) {
			loadingPanel(false);
			AppClientFactory.printSevereLogger("DistrictView setTopicListData:::"+e);
		}
	}

	public void setTopicListData(ArrayList<ProfileLibraryDo> folderListDo, String folderId, ProfileLibraryDo profileLibraryDo,String libraryGooruOid, String parentId) {
		contentScroll.clear();
		try {
			int count = 0;
			collectionCount= folderListDo.size();
			setMetaDataContent(profileLibraryDo);
			if(folderListDo.size()>0) {
				for(int i = 0; i <folderListDo.size(); i++) {
					count++;
					if(folderListDo.get(i).getType().equals("scollection")) {
						contentScroll.add(new ProfileTopicListView(folderListDo.get(i), count, AppClientFactory.getCurrentPlaceToken(), "scollection",libraryGooruOid));
					} else {
						contentScroll.add(new ProfileTopicListView(folderListDo.get(i), AppClientFactory.getCurrentPlaceToken(),libraryGooruOid,count,parentId));
					}
				}
			} else {
				HTMLPanel emptyContainer = new HTMLPanel("");
				contentScroll.add(emptyContainer);
			}
			loadingPanel(false);
		} catch (Exception e) {
			loadingPanel(false);
			AppClientFactory.printSevereLogger("DistrictView setTopicListData:::"+e);
		}
	}
	
	public void loadingPanel(boolean isVisible) {
		loadingIconPanel.setVisible(isVisible);
		contentScroll.setVisible(!isVisible);
		folderListPanel.setVisible(!isVisible);
		libraryMetaDataContainer.setVisible(!isVisible);
	}

	public String getPlaceToken() {
		return placeToken;
	}

	private void setPlaceToken(String placeToken) {
		this.placeToken = placeToken;
	}

	private void setMetaDataContent(ProfileLibraryDo profileLibraryDo) {
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY) || AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.VALVERDE)) {
			libraryMetaDataContainer.clear();
			libraryMetaDataContainer.add(new LibraryMetaDataContentUc(profileLibraryDo));
		}
	}
	
	public void setAssets(String placeToken) {
		courseTabs.getElement().setId("courseTabs");
		container.getElement().setId("container");
		if (placeToken.equalsIgnoreCase(PlaceTokens.LIFEBOARD) ||
				placeToken.equalsIgnoreCase(PlaceTokens.AUTODESK) ||
				placeToken.equalsIgnoreCase(PlaceTokens.COMMUNITY) ||
				placeToken.equalsIgnoreCase(PlaceTokens.RUSD_LIBRARY) ||
				placeToken.equalsIgnoreCase(PlaceTokens.EPISD_LIBRARY) ||
				placeToken.equalsIgnoreCase(PlaceTokens.YCGL_LIBRARY) ||
				placeToken.equalsIgnoreCase(PlaceTokens.SAUSD_LIBRARY) ||
				placeToken.equalsIgnoreCase(PlaceTokens.SUSD) ||
				placeToken.equalsIgnoreCase(PlaceTokens.VALVERDE) || placeToken.equalsIgnoreCase(PlaceTokens.LUSD)){
			container.getElement().getStyle().setWidth(1000, Unit.PX);
		}else{
			container.getElement().getStyle().clearWidth();
		}
		featuredCourseTabs.getElement().setId("featuredCourseTabs");
		leftNav.getElement().setId("leftNav");
		contentScroll.getElement().setId("contentScroll");
		courseBanner.getElement().setId("courseBanner");
		featuredEducator.getElement().setId("featuredEducator");
		featuredCousesLbl.getElement().setId("lblFeaturedCousesLbl");
		featuredCourses.getElement().setId("pnlFeaturedCourses");
		partnerLogo.getElement().setId("pnlPartnerLogo");
		courseImage.getElement().setId("imgCourseImage");
		courseTitle.getElement().setId("lblCourseTitle");
		educatorPhoto.getElement().setId("imgEducatorPhoto");
		featuredContributor.getElement().setId("lblFeaturedContributor");
		featuredContributorsLink.getElement().setId("lnkFeaturedContributorsLink");
		scrollPanel.getElement().setId("sbScrollPanel");
		libraryMetaDataContainer.getElement().setId("pnlLibraryMetaDataContainer");
		loadingIconPanel.getElement().setId("pnlLoadingIconPanel");
		contributorsContainer.getElement().setId("pnlContributorsContainer");
		StringUtil.setAttributes(listViewBtn.getElement(), "listViewBtn", listViewBtn.getText(), listViewBtn.getText());
		StringUtil.setAttributes(folderTopicTitleLbl.getElement(), "folderTopicTitleLbl", "", "");
		StringUtil.setAttributes(folderListPanel.getElement(), "folderListPanel", "", "");
		
		setPlaceToken(placeToken);
		if(getPlaceToken().equalsIgnoreCase(PlaceTokens.SAUSD_LIBRARY)) {
			setLandingBannerMetaData("landingSausdBanner", "", i18n.GL1901(), districtStyleUc.sausdPartnerLogo(), true);
		} else if(getPlaceToken().equalsIgnoreCase(PlaceTokens.LIFEBOARD)) {
			setLandingBannerMetaData("landingLifeboardBanner", "", i18n.GL2052(), districtStyleUc.sausdPartnerLogo(), false);
		} else if(getPlaceToken().equalsIgnoreCase(PlaceTokens.RUSD_LIBRARY)) {
			setLandingBannerMetaData("landingRusdBanner", "250px", i18n.GL0588(), districtStyleUc.rusdPartnerLogo(), true);
		} else if(getPlaceToken().equalsIgnoreCase(PlaceTokens.EPISD_LIBRARY)) {
			setLandingBannerMetaData("landingEpisdBanner", "250px", i18n.GL3504(), districtStyleUc.episdPartnerLogo(), true);
		}  else if(getPlaceToken().equalsIgnoreCase(PlaceTokens.SUSD)) {
			setLandingBannerMetaData("landingSusdBanner", "250px", i18n.GL2078() + " " + i18n.GL0587(), districtStyleUc.susdPartnerLogo(), true);
		} else if(getPlaceToken().equalsIgnoreCase(PlaceTokens.VALVERDE)) {
			setLandingBannerMetaData("landingValverdeBanner", "250px", i18n.GL2075() + " " + i18n.GL0587(), districtStyleUc.valverdePartnerLogo(), true);
		}else if(getPlaceToken().equalsIgnoreCase(PlaceTokens.LUSD)) {
			setLandingBannerMetaData("landingLusdBanner", "250px", i18n.GL2180(), districtStyleUc.lusdPartnerLogo(), true);
		} else if(getPlaceToken().equalsIgnoreCase(PlaceTokens.YCGL_LIBRARY)) {
			setLandingBannerMetaData("landingYcglBanner", "250px", i18n.GL3289_1(), districtStyleUc.ycglDistrictLogo(), true);
		} else {
			partnerLogo.setVisible(false);
		}
		folderListPanel.setVisible(false);
		contributorsContainer.setVisible(false);
		courseBanner.setVisible(false);
		featuredEducator.setVisible(false);
		districtMenuNav = new DistrictMenuNav(getPlaceToken()) {
			@Override
			public void clickOnCourse(ArrayList<ProfileLibraryDo> unitList, String courseId, ProfileLibraryDo profileLibraryDo) {
				showCourseBanner(profileLibraryDo, true);
				setSubjectUnits(unitList,getLibraryGooruOid());
			}
		};
		listViewBtn.addStyleName(districtStyleUc.listViewBtnStyle());
		courseTabs.add(districtMenuNav);
		landingBanner.add(new DistrictBannerView(getPlaceToken()));
		featuredContributorsLink.setText(i18n.GL1005());
		featuredContributorsLink.getElement().setAttribute("alt",i18n.GL1005());
		featuredContributorsLink.setTitle(i18n.GL1005());
		courseImage.setWidth("1000px");
		courseImage.setHeight("300px");
	}
	
	private void setLandingBannerMetaData(String bannerId, String height, String featuredCourseLbl, String partnerLogoStyle, boolean isPartnerLogoVisible) {
		landingBanner.getElement().setId(bannerId);
		if(!height.isEmpty()) {
			landingBanner.setHeight(height);
		}
		featuredCousesLbl.setText(featuredCourseLbl);
		featuredCousesLbl.getElement().setAttribute("alt",featuredCourseLbl);
		featuredCousesLbl.getElement().setAttribute("title",featuredCourseLbl);
		partnerLogo.setStyleName(partnerLogoStyle);
		partnerLogo.setVisible(isPartnerLogoVisible);
		partnerLogo.getElement().getStyle().setRight(10, Unit.PX);
	}
	
	private void setSubjectUnits(ArrayList<ProfileLibraryDo> unitList,String libraryGooruOid) {
		leftNav.clear();
		setUnitList(unitList,libraryGooruOid);
	}
	/**
	 * This Inner class used to navigate to Folder TOC page when click on ListAll button.
	 */
	public class ListAllBtnClickHandler implements ClickHandler{
		String folderId="",parentId="";
		public ListAllBtnClickHandler(String folderId, String parentId){
			this.folderId=folderId;
			this.parentId=parentId;
		}
		@Override
		public void onClick(ClickEvent event) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", folderId);
			params.put("libName", getPlaceToken());
			params.put("parentId",parentId);
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.FOLDER_TOC,params);
		}
		
	}
	
	private void showCourseBanner(ProfileLibraryDo profileLibraryDo, boolean isCoursePageVisible) {
		landingBanner.setVisible(!isCoursePageVisible);
		featuredCourseTabs.setVisible(!isCoursePageVisible);
		featuredCousesLbl.setVisible(!isCoursePageVisible);
		courseBanner.setVisible(isCoursePageVisible);
		if(isCoursePageVisible) {
			courseImage.setUrl(profileLibraryDo.getThumbnails().getUrl());
			courseImage.addErrorHandler(new ErrorHandler() {
				@Override
				public void onError(ErrorEvent event) {
					courseImage.setUrl(COURSE_DEFAULT_IMG);
				}
			});
			courseTitle.setText(profileLibraryDo.getTitle());
			courseTitle.getElement().setAttribute("alt",profileLibraryDo.getTitle());
			courseTitle.getElement().setAttribute("title",profileLibraryDo.getTitle());
		}
	}
	 class LeftPanelScroll implements  com.google.gwt.user.client.Window.ScrollHandler{
		 private String libraryGooruOid;
		 public LeftPanelScroll(String libraryGooruOid){
			 this.libraryGooruOid=libraryGooruOid;
		 }

		@Override
		public void onWindowScroll(
				com.google.gwt.user.client.Window.ScrollEvent event) {
			String sharing = "public";
			if(!scrollFlag){
			if(totalCollectionCount>collectionCount){
				scrollFlag=true;
			AppClientFactory.getInjector().getLibraryService().getLibraryPaginationWorkspace(unitListId, sharing, 20,collectionCount, new SimpleAsyncCallback<ProfileLibraryListDo>() {

				@Override
				public void onSuccess(ProfileLibraryListDo profileLibraryListDo) {
					//setTopicListData(profileLibraryListDo.getSearchResult(), unitListId, ProfileLibraryDoObj);
					
					try {
						int count = 0;
						count = collectionCount;
						totalCollectionCount= profileLibraryListDo.getCount();
						collectionCount= collectionCount+profileLibraryListDo.getSearchResult().size();
						if(totalCollectionCount>collectionCount){
							scrollFlag=false;	
						}
						setMetaDataContent(ProfileLibraryDoObj);
						if(profileLibraryListDo.getSearchResult().size()>0) {
							for(int i = 0; i <profileLibraryListDo.getSearchResult().size(); i++) {
								count++;
								if(profileLibraryListDo.getSearchResult().get(i).getType().equals("scollection")) {
									contentScroll.add(new ProfileTopicListView(profileLibraryListDo.getSearchResult().get(i), count, AppClientFactory.getCurrentPlaceToken(), "scollection",libraryGooruOid));
								} else {
									contentScroll.add(new ProfileTopicListView(profileLibraryListDo.getSearchResult().get(i), AppClientFactory.getCurrentPlaceToken(),libraryGooruOid,count,parentId));
								}
							}
						} else {
							HTMLPanel emptyContainer = new HTMLPanel("");
							contentScroll.add(emptyContainer);
						}
						loadingPanel(false);
					} catch (Exception e) {
						loadingPanel(false);
						AppClientFactory.printSevereLogger("DistrictView getLibraryPaginationWorkspace:::"+e);
					}
				}
			});
			}
		}
		}
	}
}