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
package org.ednovo.gooru.client.mvp.library.district;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;
import org.ednovo.gooru.application.shared.model.library.SubjectDo;
import org.ednovo.gooru.client.SeoTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class DistrictMenuNav extends Composite {

	@UiField HTMLPanel tabsInner, scienceCourses, mathCourses, socialCourses, elaCourses,learnCourses,aboutCourses;
	
	@UiField HTMLEventPanel sciencePanel, mathPanel, socialPanel, elaPanel,learnPanel,aboutPanel;
	
	@UiField Label scienceText,mathText,socialSciencesText,languageArtsText,learnText,aboutText;
	
	@UiField DistrictStyleBundle districtStyleUc;
	
	@UiField Anchor aboutGooruAnr;
	
	private static final String SCIENCE = "science", MATH = "math", SOCIAL="social-sciences", LANGUAGE="language-arts", LEARNING = "learning",EXTENDING="extending",MORE_SUBJECTS="more subjects";
	
	private static final String ACTIVE = "active";
	
	private boolean isScienceHovered = false, isMathHovered = false, isSocialHovered = false, isLanguageHovered = false,isExtendingHovered=false,isMoreSubHovered=false, isLearningHovered = false,iselementaryHoverd =false;
	
	private Map<String, String> subjectIdList = new HashMap<String, String>();
	
	private String placeToken = null;
	
	private String libraryGooruOid;
	
    private static DistrictMenuNavUiBinder uiBinder = GWT.create(DistrictMenuNavUiBinder.class);

    private MessageProperties i18n = GWT.create(MessageProperties.class);
    
	interface DistrictMenuNavUiBinder extends UiBinder<Widget, DistrictMenuNav> {
	}

	public DistrictMenuNav(String placeToken) {
		initWidget(uiBinder.createAndBindUi(this));
		setPlaceToken(placeToken);
		setAssets();
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.VALVERDE)
				||AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SUSD) ||AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.LUSD) ) {
			sciencePanel.addStyleName(districtStyleUc.tabsLiInactive());
		} else {
			
			sciencePanel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(!isScienceHovered) {
						isScienceHovered = true;
						getTaxonomyData(subjectIdList.get(SCIENCE), SCIENCE);
					}
				}
			});
		}
		
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.VALVERDE)) {
			mathPanel.addStyleName(districtStyleUc.tabsLiInactive());
		} else {
			mathPanel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(!isMathHovered) {
						isMathHovered = true;
						getTaxonomyData(subjectIdList.get(MATH),MATH);
					}
				}
			});
		}

		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.VALVERDE)
				||AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SUSD) || AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.LUSD)) {
			socialPanel.addStyleName(districtStyleUc.tabsLiInactive());
		} else {
			socialPanel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(!isSocialHovered) {
						isSocialHovered = true;
						getTaxonomyData(subjectIdList.get(SOCIAL),SOCIAL);
					}
				}
			});
		}
		
		elaPanel.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(!isLanguageHovered) {
					isLanguageHovered = true;
					getTaxonomyData(subjectIdList.get(LANGUAGE),LANGUAGE);
				}
			}
		});
		aboutPanel.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(!isExtendingHovered) {
					isExtendingHovered = true;
					getTaxonomyData(subjectIdList.get(EXTENDING),EXTENDING);
				}
				if(!isMoreSubHovered){
					isMoreSubHovered = true;
					getTaxonomyData(subjectIdList.get(MORE_SUBJECTS),MORE_SUBJECTS);
				}
			}
		});

		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.LIFEBOARD)
					||AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.VALVERDE)
					|| AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.LUSD)) {
			
				learnPanel.addStyleName(districtStyleUc.tabsLiInactive());
		} else {
			
			learnPanel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(subjectIdList.get(LEARNING)!=null) {
						if(!isLearningHovered) {
							isLearningHovered = true;
							getTaxonomyData(subjectIdList.get(LEARNING),LEARNING);
						}
					}
					if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RUSD_LIBRARY))
					{
						if(subjectIdList.get("elementary")!=null) {
							if(!iselementaryHoverd) {
								iselementaryHoverd = true;
								getTaxonomyData(subjectIdList.get("elementary"),"elementary");
							}
						}
					}
				}
			});
		}
	}
	
	private void setAssets() {
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
		
		learnText.setText(i18n.GL1682());
		learnText.getElement().setId("lblLearnText");
		learnText.getElement().setAttribute("alt","Elementary");
		learnText.getElement().setAttribute("title","Elementary");
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)) {
			learnText.setText(i18n.GL2077());
			setGooruAnrText(i18n.GL1899(), i18n.GL1900(),true);
			learnPanel.getElement().getStyle().setWidth(171, Unit.PX);
			learnPanel.getElement().getStyle().setPadding(0, Unit.PX);
		} else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.LIFEBOARD)) {
			setGooruAnrText(i18n.GL2063(), i18n.GL2064(),false);
		} else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
			learnText.setText(i18n.GL2076());
			setGooruAnrText(i18n.GL1827(), i18n.GL1828(),false);
		} else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.EPISD_LIBRARY)) {
			learnText.setText(i18n.GL3505());
			setGooruAnrText(i18n.GL3506(), "",true);
		}else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SUSD)) {
			learnText.setText(i18n.GL2077());
			setGooruAnrText(i18n.GL2069(), i18n.GL2070(),false);
			learnPanel.getElement().getStyle().setWidth(171, Unit.PX);
			learnPanel.getElement().getStyle().setPadding(0, Unit.PX);
		} else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.VALVERDE)) {
			setGooruAnrText(i18n.GL2071(), i18n.GL2072(),false);
		}else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.LUSD)) {
			setGooruAnrText(i18n.GL2184(), i18n.GL2185(),false);
		}else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.YCGL_LIBRARY)) {
			learnText.setText(i18n.GL2077());
			setGooruAnrText(i18n.GL3291_1(), i18n.GL3292_1(),false);
			learnPanel.getElement().getStyle().setWidth(171, Unit.PX);
			learnPanel.getElement().getStyle().setPadding(0, Unit.PX);
		} 

		aboutGooruAnr.setTarget("_blank");
		aboutGooruAnr.addStyleName(districtStyleUc.aboutGooruAnrPadding());
		aboutGooruAnr.addClickHandler(new MixPanelEventClick());
		
		scienceText.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		mathText.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		socialSciencesText.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		languageArtsText.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		learnText.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		aboutGooruAnr.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.LIFEBOARD)){
			aboutGooruAnr.getElement().getStyle().setWidth(10, Unit.PCT);
		}
		aboutPanel.getElement().setId("epnlAboutPanel");
		sciencePanel.getElement().setId("epnlSciencePanel");
		scienceCourses.getElement().setId("pnlScienceCourses");
		mathPanel.getElement().setId("epnlMathPanel");
		mathCourses.getElement().setId("pnlMathCourses");
		socialPanel.getElement().setId("epnlSocialPanel");
		socialCourses.getElement().setId("pnlSocialCourses");
		elaPanel.getElement().setId("epnlElaPanel");
		elaCourses.getElement().setId("pnlElaCourses");
		learnPanel.getElement().setId("epnlLearnPanel");
		//learnPanel.getElement().getStyle().setWidth(171, Unit.PX);
		//learnPanel.getElement().getStyle().setPadding(0, Unit.PX);
		learnCourses.getElement().setId("pnlLearnCourses");
		aboutGooruAnr.getElement().setId("lnkAboutGooruAnr");
	}
	
	private void setGooruAnrText(String anrTxt, String anrLink,boolean isSausd) {
		if(isSausd){
			aboutPanel.setVisible(true);
			aboutPanel.getElement().setAttribute("style", "border-right:1px solid #ddd;");
			aboutGooruAnr.setVisible(false);
			if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)){
				aboutText.setText(i18n.GL1899());
				aboutText.getElement().setId("lblLanguageArtsText");
				aboutText.getElement().setAttribute("alt",i18n.GL1899());
				aboutText.getElement().setAttribute("title",i18n.GL1899());
			}else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.EPISD_LIBRARY)){
				aboutText.setText(anrTxt);
				aboutText.getElement().setId("lblLanguageArtsText");
				aboutText.getElement().setAttribute("alt",anrTxt);
				aboutText.getElement().setAttribute("title",anrTxt);
			}
			sciencePanel.addStyleName(districtStyleUc.tabsSausdLi());
			mathPanel.addStyleName(districtStyleUc.tabsSausdLi());
			socialPanel.addStyleName(districtStyleUc.tabsSausdLi());
			elaPanel.addStyleName(districtStyleUc.tabsSausdLi());
			learnPanel.addStyleName(districtStyleUc.tabsSausdLi());
			aboutPanel.addStyleName(districtStyleUc.tabsSausdLi());
		}else{
			sciencePanel.removeStyleName(districtStyleUc.tabsSausdLi());
			mathPanel.removeStyleName(districtStyleUc.tabsSausdLi());
			socialPanel.removeStyleName(districtStyleUc.tabsSausdLi());
			elaPanel.removeStyleName(districtStyleUc.tabsSausdLi());
			learnPanel.removeStyleName(districtStyleUc.tabsSausdLi());
			aboutPanel.removeStyleName(districtStyleUc.tabsSausdLi());
			
			aboutGooruAnr.setVisible(true);
			aboutPanel.setVisible(false);
			aboutGooruAnr.setText(anrTxt);
			aboutGooruAnr.getElement().setAttribute("alt",anrTxt);
			aboutGooruAnr.getElement().setAttribute("title",anrTxt);
			aboutGooruAnr.setHref(anrLink);
		}
	}
	
	public void getTaxonomyData(final String subjectCode, final String subjectName) {
		AppClientFactory.getInjector().getLibraryService().getLibraryCoursesList(subjectCode, "public", "0", new SimpleAsyncCallback<ProfileLibraryListDo>() {
			@Override
			public void onSuccess(ProfileLibraryListDo profileLibraryListDo) {
				setTaxonomyData(subjectName, subjectCode, profileLibraryListDo);
			}
		});
	}

	public void getCourse(final String subjectCode, final String subjectName, final ProfileLibraryDo profileLibraryDo) {
		String sharing = "public";
		if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.LIFEBOARD)) {
			sharing = null;
		}
		AppClientFactory.getInjector().getLibraryService().getLibraryPaginationWorkspace(subjectCode, sharing, 20,0, new SimpleAsyncCallback<ProfileLibraryListDo>() {
			@Override
			public void onSuccess(ProfileLibraryListDo profileLibraryListDo) {
				clickOnCourse(profileLibraryListDo.getSearchResult(), subjectCode, profileLibraryDo);
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
		} else if(subjectName.equalsIgnoreCase(LEARNING)&&learnCourses.getWidgetCount()>0) {
			isSelected = true;
		}
		return isSelected;
	}
	
	protected void setTaxonomyData(final String subjectname, final String subjectCode, ProfileLibraryListDo profileLibraryListDo) {
		if (profileLibraryListDo.getSearchResult() != null) {
			for (final ProfileLibraryDo profileLibraryDo : profileLibraryListDo.getSearchResult()) {
				Label courseTitle = new Label(profileLibraryDo.getTitle());
				courseTitle.setStyleName(districtStyleUc.courseOption());
				final String courseId = profileLibraryDo.getGooruOid().toString();
				courseTitle.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						Window.scrollTo(0, 0);
						DistrictView.scrollFlag = false;
						setTabSelection(subjectname);
						getCourse(courseId, subjectname, profileLibraryDo);
					}
				});
				if(subjectname.equalsIgnoreCase(SCIENCE)) {
					scienceCourses.add(courseTitle);
				} else if(subjectname.equalsIgnoreCase(MATH)) {
					mathCourses.add(courseTitle);
				} else if(subjectname.equalsIgnoreCase(SOCIAL)) {
					socialCourses.add(courseTitle);
				} else if(subjectname.equalsIgnoreCase(LANGUAGE)) {
					elaCourses.add(courseTitle);
				} else if(subjectname.equalsIgnoreCase(LEARNING)) {
					learnCourses.add(courseTitle);
				} else if(subjectname.equalsIgnoreCase(EXTENDING)) {
					aboutCourses.add(courseTitle);
				}else if(subjectname.equalsIgnoreCase(MORE_SUBJECTS)) {
					aboutCourses.add(courseTitle);
				}
			}
		}
	}
	
	private void setHeaderBrowserTitle(String courseLabel) {
		AppClientFactory.setBrowserWindowTitle(SeoTokens.COURSE_PAGE_TITLE+courseLabel);	
	}
	
	public void setSubjectPanelIds(ProfileLibraryListDo profileLibraryListDo) {
		
		for (ProfileLibraryDo profileListDo : profileLibraryListDo.getSearchResult()) {
			if(profileListDo.getTitle().toLowerCase().contains("social")) {
				subjectIdList.put(SOCIAL, profileListDo.getGooruOid());
			} else if(profileListDo.getTitle().toLowerCase().contains("math")) {
				subjectIdList.put(MATH, profileListDo.getGooruOid());
			} else if(profileListDo.getTitle().toLowerCase().contains("science")) {
				subjectIdList.put(SCIENCE, profileListDo.getGooruOid());
			}else if(profileListDo.getTitle().toLowerCase().contains("language")) {
				subjectIdList.put(LANGUAGE, profileListDo.getGooruOid());
			} else if(profileListDo.getTitle().toLowerCase().contains("learning")) {
				subjectIdList.put(LEARNING, profileListDo.getGooruOid());
			}else if(profileListDo.getTitle().toLowerCase().contains("elementary")) {
				subjectIdList.put(LEARNING, profileListDo.getGooruOid());
			}else if(profileListDo.getTitle().toLowerCase().contains(EXTENDING)) {
				subjectIdList.put(EXTENDING, profileListDo.getGooruOid());
			}
			
			if(profileListDo.getTitle().toLowerCase().contains("connecting languages")) {
				subjectIdList.put(LEARNING, profileListDo.getGooruOid());
			}
			if(profileListDo.getTitle().toLowerCase().contains(MORE_SUBJECTS)) {
				subjectIdList.put(MORE_SUBJECTS, profileListDo.getGooruOid());
			}
			setLearningTabStyle();
 		}
	}
	
	private void setLearningTabStyle() {
		if(!AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)){
			if(subjectIdList.get(LEARNING) != null) {
				learnPanel.removeStyleName(districtStyleUc.tabsLiInactive());
			} else {
				learnPanel.addStyleName(districtStyleUc.tabsLiInactive());
			}
		}
	}
	
	public String getSubjectNameBySubjectId(HashMap<String, SubjectDo> subjectList, String subjectId) {
		for (Map.Entry<String, SubjectDo> entry : subjectList.entrySet()) {
			if(entry.getValue().getCode().equals(subjectId)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public String getSubjectIdBySubjectName(Map<String,String> subjectList, String subject) {
		for (Map.Entry<String, String> entry : subjectList.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(subject)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	public void setTabSelection(String subjectName) {
		clearSelectionTabs();
		if(subjectName.equals(SCIENCE)) {
			sciencePanel.addStyleName(ACTIVE);
		} else if(subjectName.equals(MATH)) {
			mathPanel.addStyleName(ACTIVE);
		} else if(subjectName.equals(SOCIAL)) {
			socialPanel.addStyleName(ACTIVE);
		} else if(subjectName.equals(LANGUAGE)) {
			elaPanel.addStyleName(ACTIVE);
		} else if(subjectName.equals(LEARNING)) {
			learnPanel.addStyleName(ACTIVE);
		}
	}
	
	private void clearSelectionTabs() {
		sciencePanel.removeStyleName(ACTIVE);
		mathPanel.removeStyleName(ACTIVE);
		socialPanel.removeStyleName(ACTIVE);
		elaPanel.removeStyleName(ACTIVE);
		learnPanel.removeStyleName(ACTIVE);
	}
	
	public String getPlaceToken() {
		return placeToken;
	}

	private void setPlaceToken(String placeToken) {
		this.placeToken = placeToken;
	}

	private class MixPanelEventClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			MixpanelUtil.mixpanelEvent("Sausd_Library_Click_AboutGooru");
		}
	}
	
	public abstract void clickOnCourse(ArrayList<ProfileLibraryDo> unitList, String courseId, ProfileLibraryDo profileLibraryDo);

	public String getLibraryGooruOid() {
		return libraryGooruOid;
	}

	public void setLibraryGooruOid(String libraryGooruOid) {
		this.libraryGooruOid = libraryGooruOid;
	}
}