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
package org.ednovo.gooru.client.mvp.home.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.library.CourseDo;
import org.ednovo.gooru.application.shared.model.library.LibraryUserDo;
import org.ednovo.gooru.application.shared.model.library.StandardCourseDo;
import org.ednovo.gooru.application.shared.model.library.StandardsDo;
import org.ednovo.gooru.application.shared.model.library.SubjectDo;
import org.ednovo.gooru.application.shared.model.library.UnitDo;
import org.ednovo.gooru.client.SeoTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.home.library.events.OpenSubjectCourseEvent;
import org.ednovo.gooru.client.mvp.home.library.events.SetStandardDoEvent;
import org.ednovo.gooru.client.mvp.home.library.events.SetSubjectDoEvent;
import org.ednovo.gooru.client.mvp.home.library.events.StandardPreferenceSettingEvent;
import org.ednovo.gooru.client.mvp.home.library.events.StandardPreferenceSettingHandler;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class LibraryMenuNav extends Composite{

	@UiField HTMLPanel tabsInner, scienceCourses, mathCourses, socialCourses, elaCourses,standardData,partnerLibraries;
	
	@UiField HTMLEventPanel sciencePanel, mathPanel, socialPanel, elaPanel,standardPanel,dynamicContainer,partnerPanel;
	
	@UiField Label featuredCourses,scienceText,mathText,socialSciencesText,languageArtsText,standardsText,partnerText;
	
	@UiField LibraryStyleBundle libraryStyleUc;
	
	@UiField Anchor aboutGooruAnr;
	
	private static final String SCIENCE = "Science", MATH = "Math", SOCIAL="Social Sciences", LANGUAGE="Language Arts", FEATURED = "featured", STANDARDS="standard";
	
	private static final String LIBRARY_PAGE = "page";
	
	private static final String SUBJECT = "subject";
	
	private static final String CODE_ID = "code";
	
	private static final String TABS_INNER = "tabsInner";

	private static final String ACTIVE = "active";
	
	private boolean isScienceHovered = false, isMathHovered = false, isSocialHovered = false, isLanguageHovered = false, isStandatdHover = false, isPartnerHovered = false;
	
	public boolean checkRefreshVal = false;
	
	Map<String,CourseDo> courseDoMap = new LinkedHashMap<String,CourseDo>();
	
	Map<String,UnitDo> unitsDoMap = new LinkedHashMap<String,UnitDo>();
    
	private Map<String, String> subjectIdList = new HashMap<String, String>();
	
	private String placeToken = null;
	
	private boolean  isStandardCode = true;
	
	private static final String USER_META_ACTIVE_FLAG = "0";
	
	private PopupPanel toolTipPopupPanel=new PopupPanel();
	
	private boolean isStandardToolTipShow=false;
	
	private Storage stockStore = Storage.getLocalStorageIfSupported();

	HashMap<String, SubjectDo> courseMap = new HashMap<String, SubjectDo>();

    private static LibraryMenuNavUiBinder uiBinder = GWT.create(LibraryMenuNavUiBinder.class);

	interface LibraryMenuNavUiBinder extends UiBinder<Widget, LibraryMenuNav> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public LibraryMenuNav(String placeToken) {
		initWidget(uiBinder.createAndBindUi(this));
		
		dynamicContainer.setVisible(false);
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)||AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
			setPlaceToken(placeToken);
		} else {
			setPlaceToken(PlaceTokens.DISCOVER);
		}
		partnerPanel.setVisible(false);
		featuredCourses.setText(i18n.GL1009());
		featuredCourses.getElement().setId("lblFeaturedCourses");
		featuredCourses.getElement().setAttribute("alt",i18n.GL1009());
		featuredCourses.getElement().setAttribute("title",i18n.GL1009());
		
		scienceText.setText(i18n.GL1000());
		scienceText.getElement().setId("lblScienceText");
		scienceText.getElement().setAttribute("alt",i18n.GL1000());
		scienceText.getElement().setAttribute("title",i18n.GL1000());
		
		mathText.setText(i18n.GL1001());
		mathText.getElement().setId("lblMathText");
		mathText.getElement().setAttribute("alt",i18n.GL1001());
		mathText.getElement().setAttribute("title",i18n.GL1001());
		
		socialSciencesText.setText(i18n.GL1002());
		socialSciencesText.getElement().setId("lblSocialSciencesText");
		socialSciencesText.getElement().setAttribute("alt",i18n.GL1002());
		socialSciencesText.getElement().setAttribute("title",i18n.GL1002());
		
		languageArtsText.setText(i18n.GL1003());
		languageArtsText.getElement().setId("lblLanguageArtsText");
		languageArtsText.getElement().setAttribute("alt",i18n.GL1003());
		languageArtsText.getElement().setAttribute("title",i18n.GL1003());
		
		standardsText.setText(i18n.GL0575());
		standardsText.getElement().setId("lblStandardsText");
		standardsText.getElement().setAttribute("alt",i18n.GL0575());
		standardsText.getElement().setAttribute("title",i18n.GL0575());
		
		sciencePanel.getElement().setId("epnlSciencePanel");
		scienceCourses.getElement().setId("pnlScienceCourses");
		mathPanel.getElement().setId("epnlMathPanel");
		mathCourses.getElement().setId("pnlMathCourses");
		socialPanel.getElement().setId("epnlSocialPanel");
		socialCourses.getElement().setId("pnlSocialCourses");
		elaPanel.getElement().setId("epnlElaPanel");
		elaCourses.getElement().setId("pnlElaCourses");
		standardPanel.getElement().setId("epnlStandardPanel");
		standardData.getElement().setId("pnlStandardData");
		partnerPanel.getElement().setId("epnlPartnerPanel");
		partnerLibraries.getElement().setId("pnlPartnerLibraries");
		aboutGooruAnr.getElement().setId("lnkAboutGooruAnr");
		dynamicContainer.getElement().setId("epnlDynamicContainer");
		
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
			aboutGooruAnr.setText(i18n.GL1827());
			aboutGooruAnr.getElement().setAttribute("alt",i18n.GL1827());
			aboutGooruAnr.getElement().setAttribute("title",i18n.GL1827());
			aboutGooruAnr.setHref(i18n.GL1828());
		}if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.EPISD_LIBRARY)) {
			aboutGooruAnr.setText(i18n.GL3503());
			aboutGooruAnr.getElement().setAttribute("alt",i18n.GL3503());
			aboutGooruAnr.getElement().setAttribute("title",i18n.GL3503());
			aboutGooruAnr.setHref("");
		} else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)) {
			aboutGooruAnr.setText(i18n.GL1899());
			aboutGooruAnr.getElement().setAttribute("alt",i18n.GL1899());
			aboutGooruAnr.getElement().setAttribute("title",i18n.GL1899());
			aboutGooruAnr.setHref(i18n.GL1900());
		} else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.YCGL_LIBRARY)) {
			aboutGooruAnr.setText("About Yuma");
			aboutGooruAnr.getElement().setAttribute("alt",i18n.GL1899());
			aboutGooruAnr.getElement().setAttribute("title",i18n.GL1899());
			aboutGooruAnr.setHref("http://www.yumasupt.org");
		}else {
			aboutGooruAnr.setText(i18n.GL1024());
			aboutGooruAnr.getElement().setAttribute("alt",i18n.GL1024());
			aboutGooruAnr.getElement().setAttribute("title",i18n.GL1024());
			aboutGooruAnr.setHref(i18n.GL1829());
		}
		aboutGooruAnr.setTarget("_blank");
		aboutGooruAnr.addStyleName(libraryStyleUc.aboutGooruAnrPadding());
		aboutGooruAnr.addClickHandler(new MixPanelEventClick());
		featuredCourses.setVisible(false);
		partnerPanel.addStyleName(libraryStyleUc.partnerMenuPadding());
		partnerText.setText(i18n.GL1550());
		partnerText.getElement().setId("lblPartnerText");
		partnerText.getElement().setAttribute("alt",i18n.GL1550());
		partnerText.getElement().setAttribute("title",i18n.GL1550());
		
		scienceText.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		mathText.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		socialSciencesText.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		languageArtsText.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		standardsText.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		aboutGooruAnr.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		
		if(!AppClientFactory.isAnonymous()){
			try {
				if(AppClientFactory.getLoggedInUser().getMeta() != null && AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference() != null && AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference().getCode() !=null){
					getStandardPrefCode(AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference().getCode());
				}
			} catch (Exception e) {
				AppClientFactory.printSevereLogger("LibraryMenuNav :::"+e);
			}
		}
		
		featuredCourses.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AppClientFactory.getPlaceManager().revealPlace(getPlaceToken());
			}
		});
		
		sciencePanel.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(!isScienceHovered) { 
					isScienceHovered = true;
					String codeId = getSubjectIdBySubjectName(subjectIdList, SCIENCE);
					getTaxonomyData(SCIENCE,codeId,null);
				}
			}
		});

		mathPanel.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(!isMathHovered) {
					isMathHovered = true;
					String codeId = getSubjectIdBySubjectName(subjectIdList, MATH);
					getTaxonomyData(MATH,codeId,null);
				}
			}
		});

		socialPanel.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(!isSocialHovered) {
					isSocialHovered = true;
					String codeId = getSubjectIdBySubjectName(subjectIdList, SOCIAL);
					getTaxonomyData(SOCIAL,codeId,null);
				}
			}
		});

		elaPanel.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(!isLanguageHovered) {
					isLanguageHovered = true;
					String codeId = getSubjectIdBySubjectName(subjectIdList, LANGUAGE);
					getTaxonomyData(LANGUAGE,codeId,null);
				}
			}
		});

		if(!(getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)||getPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY))) {
			standardPanel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(!isStandardCode){
						isStandardToolTipShow=false;
						toolTipPopupPanel.clear();
						toolTipPopupPanel.setWidget(new GlobalToolTip(i18n.GL1634()));
						toolTipPopupPanel.setStyleName("");
						toolTipPopupPanel.setPopupPosition(standardPanel.getElement().getAbsoluteLeft()+32, standardPanel.getElement().getAbsoluteTop()+30);
						toolTipPopupPanel.show();
					}
					
					if(!isStandatdHover && isStandardCode){
						isStandatdHover = true;
						String codeId = STANDARDS;
						String courseId = AppClientFactory.getPlaceManager().getRequestParameter("courseId");
						getTaxonomyData(STANDARDS,codeId,courseId);
					}
				}
			});
			
			standardPanel.addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					toolTipPopupPanel.hide();
				}
			});
			
		} else {
			standardPanel.removeStyleName("courseScrollStyle");
			standardPanel.removeStyleName(libraryStyleUc.tabsLi());
			standardPanel.addStyleName(libraryStyleUc.tabsLiInactive());
		}
		
		partnerPanel.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(!isPartnerHovered) {
					isPartnerHovered = true;
					getPartners();
				}
			}
		});
		
		if(StringUtil.isPartnerUser(AppClientFactory.getCurrentPlaceToken())){
			isPartnerHovered = true;
			getPartners();
		}
		
		AppClientFactory.getEventBus().addHandler(StandardPreferenceSettingEvent.TYPE, standardPreferenceSettingHandler);
		tabsInner.getElement().setId("pnlTabsInner");
	}
	
	StandardPreferenceSettingHandler standardPreferenceSettingHandler= new StandardPreferenceSettingHandler(){
		@Override
		public List<String> getCode(List<String> standPrefCode) {
			try {
				if(!AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
					getStandardPrefCode(standPrefCode);
				}
			} catch (Exception e) {
				AppClientFactory.printSevereLogger("LibraryMenuNav getCode:::"+e);
			}
			return standPrefCode;
			
			}
	};
		
	public void getStandardPrefCode(List<String> code){
		try
		{
		if(!AppClientFactory.isAnonymous()) {
			String taxonomyCode = "";
			if(code!=null){
				StringBuilder builder = new StringBuilder();
				for (String temp : code) {
					builder.append(temp);
				}
				taxonomyCode = builder.toString();
				if(code.size()>0){
					isStandardCode=true;
					standardPanel.addStyleName("courseScrollStyle");
					standardPanel.addStyleName(libraryStyleUc.tabsLi());
					standardPanel.removeStyleName(libraryStyleUc.tabsLiInactive());
					if(standardData.getWidgetCount()>0) {
		 				if(taxonomyCode.contains("CCSS")&&(taxonomyCode.contains("TEXAS")||taxonomyCode.contains("TEKS"))) {
		 					/** 1st parameter refers to "CCSS" and 2nd parameter refers to TEKS**/
		 					setStandardDataWidgetVisibility(true,true);
						} else if (taxonomyCode.contains("CCSS")) {
							/** 1st parameter refers to "CCSS" and 2nd parameter refers to TEXAS or TEKS**/
							setStandardDataWidgetVisibility(true,false);
						} else if(taxonomyCode.contains("TEXAS")||taxonomyCode.contains("TEKS")) {
							/** 1st parameter refers to "CCSS" and 2nd parameter refers to TEXAS or TEKS**/
							setStandardDataWidgetVisibility(false,true);
						} else {
							/** 1st parameter refers to "CCSS" and 2nd parameter refers to TEXAS or TEKS**/
							setStandardDataWidgetVisibility(false,false);
							isStandardToolTipShow=true;
		 					isStandardCode=false;
							standardPanel.removeStyleName("courseScrollStyle");
							standardPanel.removeStyleName(libraryStyleUc.tabsLi());
							standardPanel.addStyleName(libraryStyleUc.tabsLiInactive());
							if(standardData.getWidgetCount()>0) {
								/** 1st parameter refers to "CCSS" and 2nd parameter refers to TEXAS or TEKS**/
								setStandardDataWidgetVisibility(false,false);
							}
						}
					}
				} else {
					isStandardCode=false;
					standardPanel.removeStyleName("courseScrollStyle");
					standardPanel.removeStyleName(libraryStyleUc.tabsLi());
					standardPanel.addStyleName(libraryStyleUc.tabsLiInactive());
					if(standardData.getWidgetCount()>0) {
						/** 1st parameter refers to "CCSS" and 2nd parameter refers to TEXAS **/
						setStandardDataWidgetVisibility(false,false);
						/*standardData.getWidget(0).setVisible(false);
	 					standardData.getWidget(1).setVisible(false);*/
					}
				}
			} else {
				isStandardCode=false;
				standardPanel.removeStyleName("courseScrollStyle");
				standardPanel.removeStyleName(libraryStyleUc.tabsLi());
				standardPanel.addStyleName(libraryStyleUc.tabsLiInactive());
				if(standardData.getWidgetCount()>0) {
					/** 1st parameter refers to "CCSS" and 2nd parameter refers to TEXAS **/
					setStandardDataWidgetVisibility(false,false);
					/*standardData.getWidget(0).setVisible(false);
					standardData.getWidget(1).setVisible(false);*/
				}
			}
		} else {
			isStandardCode=true;
			standardPanel.addStyleName("courseScrollStyle");
			standardPanel.addStyleName(libraryStyleUc.tabsLi());
			standardPanel.removeStyleName(libraryStyleUc.tabsLiInactive());
			if(standardData.getWidgetCount()>0) {
				setStandardDataWidgetVisibility(true,false);
			}
		}
		}
		catch(Exception ex)
		{
			AppClientFactory.printSevereLogger(ex.getMessage());
		}
	}
		
	private void setStandardDataWidgetVisibility(boolean isCCSSVisible, boolean isTEXASVisible) {
    /** 0th widget in "standardData" refers to CCSS widget and 1st widget refers to TEXAS widget **/
		standardData.getWidget(0).setVisible(isCCSSVisible);
		try
		{
		standardData.getWidget(1).setVisible(isTEXASVisible);
		}
		catch(Exception ex)
		{
			AppClientFactory.printSevereLogger(ex.getMessage());
		}
		
	}

	/**
	 * 
	 * @param courseId 
	 * @function getTaxonomyData 
	 * 
	 * @created_date : 03-Dec-2013
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
	public void getTaxonomyData(final String subjectName, final String subjectCode, final String courseId) {
		
			if (subjectCode!=null){
				if(subjectCode.equalsIgnoreCase(STANDARDS)){

					AppClientFactory.getInjector().getLibraryService().getStandardLibraryMenuList(subjectCode, getPlaceToken(), new SimpleAsyncCallback<ArrayList<StandardCourseDo>>(){
						@Override
						public void onSuccess(ArrayList<StandardCourseDo> standardCourseList) {
							setSubjectPanelIdsForStandardsCustomized(standardCourseList);
							StandardsDo standardsDoObject = new StandardsDo();
							standardsDoObject.setData(standardCourseList);
							AppClientFactory.fireEvent(new SetStandardDoEvent(STANDARDS,standardsDoObject));
							if(!getSubjectSelected(STANDARDS)) {
								setTaxonomyDataforStandards(STANDARDS, subjectCode, courseId, standardCourseList);
							}
						}
					});
				}
				else
				{
					final String libraryToken = StringUtil.getPublicLibraryName(getPlaceToken());
					String map = null;
					if(stockStore!=null&&stockStore.getItem(subjectCode)!=null){
						map = stockStore.getItem(subjectCode);
						AppClientFactory.fireEvent(new SetSubjectDoEvent(subjectName,courseMap.get(subjectName)));
						setTaxonomyData(subjectName, subjectCode, courseId, courseMap.get(subjectName).getData());
						
						deserializeAndDisplay(subjectCode, subjectName, libraryToken, map, courseId);
					} else {
						
						if(subjectName==null) {
							AppClientFactory.getInjector().getLibraryService().getLibrarySubjectsJson(null, null, StringUtil.getPublicLibraryName(getPlaceToken()), new SimpleAsyncCallback<String>() {
								@Override
								public void onSuccess(String subjectDoList) {
									if(stockStore!=null) {
										stockStore.setItem(subjectCode, subjectDoList);
									}
									deserializeAndDisplay(subjectCode, subjectName, libraryToken, subjectDoList, courseId);
								}
							});
						} else {
							getSubjects(subjectCode, subjectName, courseId);
						}
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
	void deserializeAndDisplay(final String subjectCode, final String subjectName, String libraryToken, String jsonResponse, final String onRefCourseId){
		
		AppClientFactory.getInjector().getLibraryService().deserializeLibrarySubjects(subjectName, onRefCourseId, libraryToken, jsonResponse, new SimpleAsyncCallback<HashMap<String,SubjectDo>>() {

			@Override
			public void onSuccess(
					HashMap<String, SubjectDo> result) {
				setSubjectPanelIds(result);
				if(subjectIdList.size()<=0) {
					setSubjectPanelIds(result);
				}
				getSubjects(subjectCode, subjectName, onRefCourseId);
			}
		});
	}

	public void getSubjects(final String subjectCode, final String subjectName, final String courseId) {
		AppClientFactory.getInjector().getLibraryService().getLibraryCourses(subjectCode, StringUtil.getPublicLibraryName(getPlaceToken()), new AsyncCallback<ArrayList<CourseDo>>() {
			@Override
			public void onFailure(Throwable caught) {
				
			}
			@Override
			public void onSuccess(ArrayList<CourseDo> courseDoList) {
				SubjectDo subjectDo = new SubjectDo();
				subjectDo.setLabel(subjectName);
				subjectDo.setSubjectCode(subjectCode);
				subjectDo.setData(courseDoList);
				AppClientFactory.fireEvent(new SetSubjectDoEvent(subjectName,subjectDo));
				setTaxonomyData(subjectName, subjectCode, courseId, courseDoList);
			}
		});
	}
	
	public boolean getSubjectSelected(String subjectName) {
		boolean isSelected = false;
		if(subjectName.equalsIgnoreCase(SCIENCE)&&scienceCourses.getWidgetCount()>0) {
			isSelected = true;
		} else if(subjectName.equalsIgnoreCase(MATH)&&mathCourses.getWidgetCount()>0) {
			isSelected = true;
		} else if(subjectName.equalsIgnoreCase(SOCIAL)&&socialCourses.getWidgetCount()>0) {
			isSelected = true;
		} else if(subjectName.equalsIgnoreCase(LANGUAGE)&&elaCourses.getWidgetCount()>0) {
			isSelected = true;
		} else if(subjectName.equalsIgnoreCase(STANDARDS)&&standardData.getWidgetCount()>0) {
			isSelected = true;
		}
		
		return isSelected;
	}
	
	/**
	 * @param courseId2 
	 * @function setTaxonomyData 
	 * 
	 * @created_date : 03-Dec-2013
	 * 
	 * @description
	 * 
	 * @parm(s) : @param List<LibraryCodeDo> taxonomyDo
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 * 
	*/
	protected void setTaxonomyData(String subjectname, final String subjectCode, String courseIdRefresh, ArrayList<CourseDo> courseDoList) {
		final HTMLPanel elementaryCoursePanel = new HTMLPanel("");
		Label elementaryLabel = new Label(i18n.GL2002());
		elementaryLabel.setStyleName(libraryStyleUc.gradeOption());
		elementaryCoursePanel.add(elementaryLabel);
		
		final HTMLPanel middleSchoolCoursePanel = new HTMLPanel("");
		Label middleSchoolLabel = new Label(i18n.GL2003());
		middleSchoolLabel.setStyleName(libraryStyleUc.gradeOption());
		middleSchoolCoursePanel.add(middleSchoolLabel);
		
		final HTMLPanel highSchoolCoursePanel = new HTMLPanel("");
		Label highSchoolLabel = new Label(i18n.GL2004());
		highSchoolLabel.setStyleName(libraryStyleUc.gradeOption());
		highSchoolCoursePanel.add(highSchoolLabel);
		
		if(getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
			elementaryCoursePanel.clear();
			middleSchoolCoursePanel.clear();
			highSchoolCoursePanel.clear();
		}
		
		if (courseDoList != null) {
			for (final CourseDo courseDo : courseDoList) {
					Label courseTitle = new Label(courseDo.getLabel());
					if(subjectname.equalsIgnoreCase(SCIENCE)) {
						courseTitle.setStyleName(libraryStyleUc.courseOption());
						final String courseId = courseDo.getCodeId().toString();
						courseDoMap.put(courseId, courseDo);
						courseTitle.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								Window.scrollTo(0, 0);
								setHeaderBrowserTitle(courseDo.getLabel());
								MixpanelUtil.mixpanelEvent("Library_"+SCIENCE+"_"+courseDo.getLabel());
								Map<String,String> params = new HashMap<String, String>();
								params.put(LIBRARY_PAGE, "course-page");
								params.put(SUBJECT, subjectCode);
								params.put("courseId", courseId);
								AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
							}
						});
					} else if(subjectname.equalsIgnoreCase(MATH)) {
						courseTitle.setStyleName(libraryStyleUc.courseOption());
						final String courseId = courseDo.getCodeId().toString();
						courseDoMap.put(courseId, courseDo);
						courseTitle.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								Window.scrollTo(0, 0);
								setHeaderBrowserTitle(courseDo.getLabel());
								MixpanelUtil.mixpanelEvent("Library_"+MATH+"_"+courseDo.getLabel());
								Map<String,String> params = new HashMap<String, String>();
								params.put(LIBRARY_PAGE, "course-page");
								params.put(SUBJECT, subjectCode);
								params.put("courseId", courseId);
								AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
							}
						});
					} 
								
					else if(subjectname.equalsIgnoreCase(SOCIAL)) {
						courseTitle.setStyleName(libraryStyleUc.courseOption());
						final String courseId = courseDo.getCodeId().toString();
						courseDoMap.put(courseId, courseDo);
						courseTitle.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								Window.scrollTo(0, 0);
								setHeaderBrowserTitle(courseDo.getLabel());
								MixpanelUtil.mixpanelEvent("Library_"+SOCIAL+"_"+courseDo.getLabel());
								Map<String,String> params = new HashMap<String, String>();
								params.put(LIBRARY_PAGE, "course-page");
								params.put(SUBJECT, subjectCode);
								params.put("courseId", courseId);
								AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
							}
						});
					} else if(subjectname.equalsIgnoreCase(LANGUAGE)) {
						courseTitle.setStyleName(libraryStyleUc.courseOption());
						final String courseId = courseDo.getCodeId().toString();
						courseDoMap.put(courseId, courseDo);
						courseTitle.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								Window.scrollTo(0, 0);
								setHeaderBrowserTitle(courseDo.getLabel());
								MixpanelUtil.mixpanelEvent("Library_"+LANGUAGE+"_"+courseDo.getLabel());
								Map<String,String> params = new HashMap<String, String>();
								params.put(LIBRARY_PAGE, "course-page");
								params.put(SUBJECT, subjectCode);
								params.put("courseId", courseId);
								AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
							}
						});
					}
					if(getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
						elementaryCoursePanel.add(courseTitle);
					} else {
						if(courseDo.getGrade()==null || courseDo.getGrade()<=5) {
							elementaryCoursePanel.add(courseTitle);
						} else if(courseDo.getGrade()>5 && courseDo.getGrade()<9 ) {
							middleSchoolCoursePanel.add(courseTitle);
						} else {
							highSchoolCoursePanel.add(courseTitle);
						}
					}
			}
		}
		if(courseIdRefresh!=null) {
			if(subjectname.equals(SCIENCE)) {
				isScienceHovered = true;
			} else if(subjectname.equals(MATH)) {
				isMathHovered = true;
			} else if(subjectname.equals(SOCIAL)) {
				isSocialHovered = true;
			} else if(subjectname.equals(LANGUAGE)) {
				isLanguageHovered = true;
			} else if(subjectname.equals(STANDARDS)) {
				isStandatdHover = true;
			}
			
			setTabSelection(subjectname);
			AppClientFactory.fireEvent(new OpenSubjectCourseEvent(subjectCode, courseDoMap.get(courseIdRefresh)));
		}
		setLibraryGrades(subjectname, elementaryCoursePanel, middleSchoolCoursePanel, highSchoolCoursePanel);
	}
	
	public void setLibraryGrades(String subjectname, HTMLPanel elementaryCoursePanel, HTMLPanel middleSchoolCoursePanel, HTMLPanel highSchoolCoursePanel) {
		if(subjectname.equals(SCIENCE) && scienceCourses.getWidgetCount()<=0) {
			scienceCourses.add(elementaryCoursePanel);
			scienceCourses.add(middleSchoolCoursePanel);
			scienceCourses.add(highSchoolCoursePanel);
		} else if(subjectname.equals(MATH) && mathCourses.getWidgetCount()<=0) {
			mathCourses.add(elementaryCoursePanel);
			mathCourses.add(middleSchoolCoursePanel);
			mathCourses.add(highSchoolCoursePanel);
		} else if(subjectname.equals(SOCIAL) && socialCourses.getWidgetCount()<=0) {
			socialCourses.add(elementaryCoursePanel);
			socialCourses.add(middleSchoolCoursePanel);
			socialCourses.add(highSchoolCoursePanel);
		} else if(subjectname.equals(LANGUAGE) && elaCourses.getWidgetCount()<=0) {
			elaCourses.add(elementaryCoursePanel);
			elaCourses.add(middleSchoolCoursePanel);
			elaCourses.add(highSchoolCoursePanel);
		} else if(subjectname.equals(STANDARDS)) {
			
		}
		if(elementaryCoursePanel.getWidgetCount()<=1) {
			elementaryCoursePanel.setVisible(false);
		}
		if(middleSchoolCoursePanel.getWidgetCount()<=1) {
			middleSchoolCoursePanel.setVisible(false);
		}
		if(highSchoolCoursePanel.getWidgetCount()<=1) {
			highSchoolCoursePanel.setVisible(false);
		}
	}
	
	protected void setTaxonomyDataforStandards(final String subjectname, final String subjectCode, final String courseIdRefresh, ArrayList<StandardCourseDo> courseDoList) {
		if (courseDoList != null) {
			for (final StandardCourseDo standardsCourseDo : courseDoList) {
	
					 if(subjectname.equalsIgnoreCase(STANDARDS)) {
						dynamicContainer.clear();
						final Label courseTitle = new Label(standardsCourseDo.getLabel());		
						courseTitle.setStyleName(libraryStyleUc.courseOption());
						courseTitle.getElement().setAttribute("style", "width: 50%;");
					
						final String standardsId = standardsCourseDo.getCodeId().toString();

						dynamicContainer.add(courseTitle);
	
						HTMLEventPanel panel = new HTMLEventPanel(dynamicContainer.getElement().getInnerHTML());
						panel.setStyleName(libraryStyleUc.twoColumnContainer());
						panel.getElement().setAttribute("style", "width: 200%;clear: both;height: 0px;");
						
						final HTMLPanel subDropMenu = new HTMLPanel("");
						subDropMenu.setStyleName(libraryStyleUc.subDropdown());
						subDropMenu.getElement().setAttribute("style", "background: white;top: 0px;left: -1px;border: 1px solid #ddd;");
						for (final CourseDo courseDo : standardsCourseDo.getCourse()) {
							Label unitsTitle = new Label(courseDo.getLabel());
							unitsTitle.setStyleName(libraryStyleUc.unitOption());
							final String courseId = courseDo.getCodeId().toString();
							courseDoMap.put(courseId, courseDo);
							unitsTitle.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent event) {
									Window.enableScrolling(true);
									Window.scrollTo(0, 0);
									setHeaderBrowserTitle(standardsCourseDo.getLabel());
									//MixpanelUtil.mixpanelEvent("Library_"+STANDARDS+"_"+standardsCourseDo.getLabel());
									MixpanelUtil.mixpanelEvent("standardlibrary_select_course");
									final Map<String,String> params = new HashMap<String, String>();
									params.put(LIBRARY_PAGE, "course-page");
									params.put(SUBJECT, STANDARDS);
									params.put("courseId", courseId);
									params.put("standardId", standardsId);
									if(courseTitle.getText().contains("Texas")) {
										params.put("libtype", "TEKS");
									}
									
									AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
									
									AppClientFactory.getInjector().getLibraryService().getSubjectsForStandards(subjectCode, subjectname, new SimpleAsyncCallback<HashMap<String, StandardsDo>>() {

										@Override
										public void onSuccess(HashMap<String, StandardsDo> result) {
											
											setHeaderBrowserTitle(standardsCourseDo.getLabel());
											//MixpanelUtil.mixpanelEvent("Library_"+STANDARDS+"_"+standardsCourseDo.getLabel());
											MixpanelUtil.mixpanelEvent("standardlibrary_select_course");
											final Map<String,String> params = new HashMap<String, String>();
											params.put(LIBRARY_PAGE, "course-page");
											params.put(SUBJECT, STANDARDS);
											params.put("courseId", courseId);
											params.put("standardId", standardsId);
											if(courseTitle.getText().contains("Texas")) {
												params.put("libtype", "TEKS");
											}
											
							
											
											AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
											
											
											
											setSubjectPanelIdsForStandards(result);
											AppClientFactory.fireEvent(new SetStandardDoEvent(STANDARDS,result.get(STANDARDS)));
											if(!getSubjectSelected(STANDARDS)) {
												AppClientFactory.fireEvent(new OpenSubjectCourseEvent(subjectname, courseDoMap.get(courseIdRefresh)));
												//setTaxonomyDataforStandards(STANDARDS, subjectCode, courseId, result.get(STANDARDS).getData());
											}
											
						
											
										}
									});
									
									
								}
							});
							subDropMenu.add(unitsTitle);
						}
						subDropMenu.getElement().getStyle().setDisplay(Display.NONE);
					
						panel.addMouseOverHandler(new MouseOverHandler() {
							
							@Override
							public void onMouseOver(MouseOverEvent event) {
								subDropMenu.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
								  
								
							}
						});
						panel.addMouseOutHandler(new MouseOutHandler() {
							
	
							@Override
							public void onMouseOut(MouseOutEvent event) {
								subDropMenu.getElement().getStyle().setDisplay(Display.NONE);
								
							}
						});
						
						panel.add(subDropMenu);
						standardData.add(panel);
					}
					
				
			}
			if(!AppClientFactory.isAnonymous()){
				try {
					if(AppClientFactory.getLoggedInUser().getMeta() != null && AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference() != null && AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference().getCode() != null){
						getStandardPrefCode(AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference().getCode());
					}
				} catch (Exception e) {
					AppClientFactory.printSevereLogger("LibraryMenuNav setTaxonomyDataforStandards::::"+e);
				}
			} else {
				getStandardPrefCode(null);
			}
		}
		if(courseIdRefresh!=null) {
			if(subjectname.equals(SCIENCE)) {
				isScienceHovered = true;
			} else if(subjectname.equals(MATH)) {
				isMathHovered = true;
			} else if(subjectname.equals(SOCIAL)) {
				isSocialHovered = true;
			} else if(subjectname.equals(LANGUAGE)) {
				isLanguageHovered = true;
			} else if(subjectname.equals(STANDARDS)) {
				isStandatdHover = true;
			}
			
			setTabSelection(subjectname);

			String subjectNameonRefresh = AppClientFactory.getPlaceManager().getRequestParameter("subject");
			if(!subjectname.equalsIgnoreCase(STANDARDS))
			{
				AppClientFactory.fireEvent(new OpenSubjectCourseEvent(subjectname, courseDoMap.get(courseIdRefresh)));
			}
			if(subjectNameonRefresh.equalsIgnoreCase(STANDARDS))
			{
				AppClientFactory.fireEvent(new OpenSubjectCourseEvent(subjectname, courseDoMap.get(courseIdRefresh)));
			}
		}
	}
	
	/**
	 * 
	 * @function setHeaderBrowserTitle 
	 * 
	 * @created_date : 24-Dec-2013
	 * 
	 * @description
	 * 
	 * @parm(s) : @param courseLabel
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private void setHeaderBrowserTitle(String courseLabel) {
		AppClientFactory.setBrowserWindowTitle(SeoTokens.COURSE_PAGE_TITLE+courseLabel);	
	}
	
	/**
	 * 
	 * @function setSubjectPanelIds 
	 * 
	 * @created_date : 13-Dec-2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param subjectList
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public void setSubjectPanelIds(HashMap<String, SubjectDo> subjectList) {
		for (Map.Entry<String, SubjectDo> entry : subjectList.entrySet()) {
			subjectIdList.put(entry.getKey(), entry.getValue().getSubjectCode()+"");
		}
		Timer timer = new Timer(){
            @Override
            public void run() {
            	if(!isScienceHovered) {
                	isScienceHovered = true;
                	getTaxonomyData(SCIENCE,getSubjectIdBySubjectName(subjectIdList, SCIENCE),null);
            	}
            	if(!isMathHovered) {
                	isMathHovered = true;
                	getTaxonomyData(MATH,getSubjectIdBySubjectName(subjectIdList, MATH),null);
            	}
            	if(!isSocialHovered) {
                	isSocialHovered = true;
                	getTaxonomyData(SOCIAL,getSubjectIdBySubjectName(subjectIdList, SOCIAL),null);
            	}
            	if(!isLanguageHovered) {
                	isLanguageHovered = true;
                	getTaxonomyData(LANGUAGE,getSubjectIdBySubjectName(subjectIdList, LANGUAGE),null);
            	}
            }
        };
        timer.schedule(5000);
	}
	
	public void setSubjectPanelIdsForStandards(HashMap<String, StandardsDo> subjectList) {
		for (Map.Entry<String, StandardsDo> entry : subjectList.entrySet()) {
			subjectIdList.put(entry.getKey(), entry.getValue().getCode()+"");
		}
	}
	
	public void setSubjectPanelIdsForStandardsCustomized(ArrayList<StandardCourseDo> subjectList) {
		for (int k=0;k<subjectList.size();k++) {
			subjectIdList.put(subjectList.get(k).getCodeId().toString(), subjectList.get(k).getCode()+"");
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
	
	public String getSubjectNameBySubjectIdForStandards(HashMap<String, StandardsDo> subjectList, String subjectId) {
		for (Map.Entry<String, StandardsDo> entry : subjectList.entrySet()) {
			if(entry.getValue().getCode().equals(subjectId)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	/**
	 * @function getSubjectIdBySubjectName 
	 * 
	 * @created_date : 17-Dec-2013
	 * 
	 * @description
	 * 
	 * @parm(s) : @param subjectList
	 * @parm(s) : @param subject
	 * @parm(s) : @return
	 * 
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public String getSubjectIdBySubjectName(Map<String,String> subjectList, String subject) {
		for (Map.Entry<String, String> entry : subjectList.entrySet()) {
			if(entry.getKey().trim().equalsIgnoreCase(subject)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	public void setTabSelection(String subjectName) {
		clearSelectionTabs();
		if(subjectName.equals(FEATURED)) {
			featuredCourses.addStyleName(ACTIVE);
		} else if(subjectName.equals(SCIENCE)) {
			sciencePanel.addStyleName(ACTIVE);
		} else if(subjectName.equals(MATH)) {
			mathPanel.addStyleName(ACTIVE);
		} else if(subjectName.equals(SOCIAL)) {
			socialPanel.addStyleName(ACTIVE);
		} else if(subjectName.equals(LANGUAGE)) {
			elaPanel.addStyleName(ACTIVE);
		} else if(subjectName.equals(STANDARDS)) {
			standardPanel.addStyleName(ACTIVE);
		} else if(StringUtil.isPartnerUser(subjectName)) {
			partnerPanel.addStyleName(ACTIVE);
		}
	}
	
	private void clearSelectionTabs() {
		featuredCourses.removeStyleName(ACTIVE);
		sciencePanel.removeStyleName(ACTIVE);
		mathPanel.removeStyleName(ACTIVE);
		socialPanel.removeStyleName(ACTIVE);
		elaPanel.removeStyleName(ACTIVE);
		standardPanel.removeStyleName(ACTIVE);
		partnerPanel.removeStyleName(ACTIVE);
	}
	
	private void clearContainerPanels(String subjectName) {
		if(subjectName.equals(SCIENCE)) {
			scienceCourses.clear();
		} else if(subjectName.equals(MATH)) {
			mathCourses.clear();
		} else if(subjectName.equals(SOCIAL)) {
			socialCourses.clear();
		} else if(subjectName.equals(LANGUAGE)) {
			elaCourses.clear();
		}else if(subjectName.equals(STANDARDS)) {
			standardData.clear();
		}
	}
	
	public String getPlaceToken() {
		return placeToken;
	}

	private void setPlaceToken(String placeToken) {
		this.placeToken = placeToken;
	}
	/**
	 * 
	 * @fileName : LibraryBannerView.java
	 *
	 * @description : This is an inner class for ClickHandler and ClickEvent. This will be used for calling a mixpanel event.
	 *
	 * @version : 1.0
	 *
	 * @date: 13-Dec-2013
	 */
	private class MixPanelEventClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			MixpanelUtil.mixpanelEvent("Library_Click_AboutGooru");
		}
	}
	
	public void getPartners() {
		AppClientFactory.getInjector().getLibraryService().getPartners(new SimpleAsyncCallback<ArrayList<LibraryUserDo>>() {
			@Override
			public void onSuccess(ArrayList<LibraryUserDo> partnersList) {
				setPartners(partnersList);
				setTabSelection(AppClientFactory.getCurrentPlaceToken());
			}
		});
	}
	
	public void setPartners(ArrayList<LibraryUserDo> partnersList) {
		for(int i=0;i<partnersList.size();i++) {
			final LibraryUserDo libraryUserDo = partnersList.get(i);
			final Label partnerTitle = new Label(StringUtil.getPartnerName(libraryUserDo.getUsername()));
			partnerTitle.setStyleName(libraryStyleUc.courseOption());
			partnerTitle.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					setHeaderBrowserTitle(partnerTitle.getText());
					AppClientFactory.getPlaceManager().revealPlace(libraryUserDo.getUsername());
				}
			});
			partnerLibraries.add(partnerTitle);
		}
	}
}
