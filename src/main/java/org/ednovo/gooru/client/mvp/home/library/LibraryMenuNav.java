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

import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.SeoTokens;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.home.library.events.OpenSubjectCourseEvent;
import org.ednovo.gooru.client.mvp.home.library.events.SetStandardDoEvent;
import org.ednovo.gooru.client.mvp.home.library.events.SetSubjectDoEvent;
import org.ednovo.gooru.client.mvp.home.library.events.StandardPreferenceSettingEvent;
import org.ednovo.gooru.client.mvp.home.library.events.StandardPreferenceSettingHandler;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.model.library.CourseDo;
import org.ednovo.gooru.shared.model.library.LibraryUserDo;
import org.ednovo.gooru.shared.model.library.StandardCourseDo;
import org.ednovo.gooru.shared.model.library.StandardsDo;
import org.ednovo.gooru.shared.model.library.SubjectDo;
import org.ednovo.gooru.shared.model.library.UnitDo;
import org.ednovo.gooru.shared.util.MessageProperties;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.seanchenxi.gwt.storage.client.StorageExt;
import com.seanchenxi.gwt.storage.client.StorageKey;
import com.seanchenxi.gwt.storage.client.StorageKeyFactory;

public class LibraryMenuNav extends Composite implements MessageProperties{

	@UiField HTMLPanel tabsInner, scienceCourses, mathCourses, socialCourses, elaCourses,standardData,partnerLibraries;
	
	@UiField HTMLEventPanel sciencePanel, mathPanel, socialPanel, elaPanel,standardPanel,dynamicContainer,partnerPanel;
	
	@UiField Label featuredCourses,scienceText,mathText,socialSciencesText,languageArtsText,standardsText,partnerText;
	
	@UiField LibraryStyleBundle libraryStyleUc;
	
	@UiField Anchor aboutGooruAnr;
	
	private static final String SCIENCE = "science", MATH = "math", SOCIAL="social-sciences", LANGUAGE="language-arts", FEATURED = "featured", STANDARDS="standard";
	
	private static final String LIBRARY_PAGE = "page";
	
	private static final String SUBJECT = "subject";
	
	private static final String CODE_ID = "code";
	
	private static final String TABS_INNER = "tabsInner";

	private static final String ACTIVE = "active";
	
	private boolean isScienceHovered = false, isMathHovered = false, isSocialHovered = false, isLanguageHovered = false, isStandatdHover = false, isPartnerHovered = false;
	
    StorageExt localStorage = StorageExt.getLocalStorage();

    StorageKey<HashMap<String, ArrayList<CourseDo>>> libraryStorageObject = StorageKeyFactory.objectKey("libraryStorageObject");

	Map<String,CourseDo> courseDoMap = new LinkedHashMap<String,CourseDo>();
	
	Map<String,UnitDo> unitsDoMap = new LinkedHashMap<String,UnitDo>();
    
	private Map<String, String> subjectIdList = new HashMap<String, String>();
	
	private String placeToken = null;
	
	private boolean  isStandardCode = true;
	
	private static final String USER_META_ACTIVE_FLAG = "0";
	
	private PopupPanel toolTipPopupPanel=new PopupPanel();
	
	private boolean isStandardToolTipShow=false;
	
    private static LibraryMenuNavUiBinder uiBinder = GWT.create(LibraryMenuNavUiBinder.class);

	interface LibraryMenuNavUiBinder extends UiBinder<Widget, LibraryMenuNav> {
	}

	public LibraryMenuNav(String placeToken) {
		initWidget(uiBinder.createAndBindUi(this));
		
		dynamicContainer.setVisible(false);
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)||AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
			setPlaceToken(placeToken);
		} else {
			setPlaceToken(PlaceTokens.HOME);
		}
		featuredCourses.setText(GL1009);
		scienceText.setText(GL1000);
		mathText.setText(GL1001);
		socialSciencesText.setText(GL1002);
		languageArtsText.setText(GL1003);
		standardsText.setText(GL0575);
		aboutGooruAnr.setText(GL1024);
		aboutGooruAnr.setHref("http://about.goorulearning.org/");
		aboutGooruAnr.setTarget("_blank");
		aboutGooruAnr.addStyleName(libraryStyleUc.aboutGooruAnrPadding());
		aboutGooruAnr.addClickHandler(new MixPanelEventClick());
		featuredCourses.setVisible(false);
		partnerPanel.addStyleName(libraryStyleUc.partnerMenuPadding());
		partnerText.setText(GL1550);
		
		if(!AppClientFactory.isAnonymous()){
			try {
				getStandardPrefCode(AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference().getCode());
			} catch (Exception e) {}
		}
		
		featuredCourses.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AppClientFactory.getPlaceManager().revealPlace(getPlaceToken());
			}
		});
		
		if(!getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
			sciencePanel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(!isScienceHovered) {
						isScienceHovered = true;
						String codeId = getSubjectIdBySubjectName(subjectIdList, SCIENCE);
						getTaxonomyData(codeId,null);
					}
				}
			});
		} else {
			sciencePanel.removeStyleName("courseScrollStyle");
			sciencePanel.removeStyleName(libraryStyleUc.tabsLi());
			sciencePanel.addStyleName(libraryStyleUc.tabsLiInactive());
		}
		
//		if(!getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
			mathPanel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(!isMathHovered) {
						isMathHovered = true;
						String codeId = getSubjectIdBySubjectName(subjectIdList, MATH);
						getTaxonomyData(codeId,null);
					}
				}
			});
//		} else {
//			mathPanel.removeStyleName("courseScrollStyle");
//			mathPanel.removeStyleName(libraryStyleUc.tabsLi());
//			mathPanel.addStyleName(libraryStyleUc.tabsLiInactive());
//		}

//		if(!getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
			socialPanel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(!isSocialHovered) {
						isSocialHovered = true;
						String codeId = getSubjectIdBySubjectName(subjectIdList, SOCIAL);
						getTaxonomyData(codeId,null);
					}
				}
			});
//		} else {
//			socialPanel.removeStyleName("courseScrollStyle");
//			socialPanel.removeStyleName(libraryStyleUc.tabsLi());
//			socialPanel.addStyleName(libraryStyleUc.tabsLiInactive());
//		}
		
//		if(!getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
			elaPanel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(!isLanguageHovered) {
						isLanguageHovered = true;
						String codeId = getSubjectIdBySubjectName(subjectIdList, LANGUAGE);
						getTaxonomyData(codeId,null);
					}
				}
			});
//		} else {
//			elaPanel.removeStyleName("courseScrollStyle");
//			elaPanel.removeStyleName(libraryStyleUc.tabsLi());
//			elaPanel.addStyleName(libraryStyleUc.tabsLiInactive());
//		}
		if(!getPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)) {
			standardPanel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(!isStandardCode){
						isStandardToolTipShow=false;
						toolTipPopupPanel.clear();
						toolTipPopupPanel.setWidget(new GlobalToolTip(GL1634));
						toolTipPopupPanel.setStyleName("");
						toolTipPopupPanel.setPopupPosition(standardPanel.getElement().getAbsoluteLeft()+32, standardPanel.getElement().getAbsoluteTop()+30);
						toolTipPopupPanel.show();
					}
					
					if(!isStandatdHover && isStandardCode){
						isStandatdHover = true;
						String codeId = STANDARDS;
						getTaxonomyData(codeId,null);
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
	}
	
	StandardPreferenceSettingHandler standardPreferenceSettingHandler= new StandardPreferenceSettingHandler(){
		@Override
		public List<String> getCode(List<String> standPrefCode) {
			try {
				getStandardPrefCode(standPrefCode);
			} catch (Exception e) {}
			return standPrefCode;
			
			}
	};
		
	public void getStandardPrefCode(List<String> code){
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
		 					/** 1st parameter refers to "CCSS" and 2nd parameter refers to TEXAS or TEKS**/
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
				standardData.getWidget(0).setVisible(true);
				standardData.getWidget(1).setVisible(true);
			}
		}
	}
		
	private void setStandardDataWidgetVisibility(boolean isCCSSVisible, boolean isTEXASVisible) {
    /** 0th widget in "standardData" refers to CCSS widget and 1st widget refers to TEXAS widget **/
		standardData.getWidget(0).setVisible(isCCSSVisible);
		standardData.getWidget(1).setVisible(isTEXASVisible);
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
	public void getTaxonomyData(final String subjectCode, final String courseId) {
		
	
			if (subjectCode!=null){
				if(subjectCode.equalsIgnoreCase(STANDARDS))
				{
					AppClientFactory.getInjector().getLibraryService().getSubjectsForStandards(subjectCode, getPlaceToken(), new AsyncCallback<HashMap<String, StandardsDo>>() {


						@Override
						public void onFailure(Throwable caught) {
						}

						@Override
						public void onSuccess(
							HashMap<String, StandardsDo> result) {
							setSubjectPanelIdsForStandards(result);
							AppClientFactory.fireEvent(new SetStandardDoEvent(STANDARDS,result.get(STANDARDS)));
							if(!getSubjectSelected(STANDARDS)) {
								setTaxonomyDataforStandards(STANDARDS, subjectCode, courseId, result.get(STANDARDS).getData());
							}
						}
					});
				}
				else
				{
					AppClientFactory.getInjector().getLibraryService().getSubjects(subjectCode, getPlaceToken(), new AsyncCallback<HashMap<String, SubjectDo>>() {
						@Override
						public void onSuccess(HashMap<String, SubjectDo> subjectListDo) {
					
							if(subjectIdList.size()<=0) {
								setSubjectPanelIds(subjectListDo);
							}
							String subjectName = getSubjectNameBySubjectId(subjectListDo, subjectCode);
							AppClientFactory.fireEvent(new SetSubjectDoEvent(subjectName,subjectListDo.get(subjectName)));
							if(!getSubjectSelected(subjectName)) {
								setTaxonomyData(subjectName, subjectCode, courseId, subjectListDo.get(subjectName).getData());
							}
						}

						@Override
						public void onFailure(Throwable caught) {
						}
					});
				}
				
			}
/*			}
		} catch (Exception e) {
		      e.printStackTrace(); 
		}
*/	}

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
		if (courseDoList != null) {
			for (final CourseDo courseDo : courseDoList) {
				if(courseDo.getUnit()!=null&&courseDo.getUnit().size()>0) {
					if(subjectname.equalsIgnoreCase(SCIENCE)) {
						Label courseTitle = new Label(courseDo.getLabel());
						courseTitle.setStyleName(libraryStyleUc.courseOption());
						final String courseId = courseDo.getCodeId().toString();
						courseDoMap.put(courseId, courseDo);
						courseTitle.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								setHeaderBrowserTitle(courseDo.getLabel());
								MixpanelUtil.mixpanelEvent("Library_"+SCIENCE+"_"+courseDo.getLabel());
								Map<String,String> params = new HashMap<String, String>();
								params.put(LIBRARY_PAGE, "course-page");
								params.put(SUBJECT, subjectCode);
								params.put("courseId", courseId);
								AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
							}
						});
						scienceCourses.add(courseTitle);
					} else if(subjectname.equalsIgnoreCase(MATH)) {
						Label courseTitle = new Label(courseDo.getLabel());
						courseTitle.setStyleName(libraryStyleUc.courseOption());
						final String courseId = courseDo.getCodeId().toString();
						courseDoMap.put(courseId, courseDo);
						courseTitle.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								setHeaderBrowserTitle(courseDo.getLabel());
								MixpanelUtil.mixpanelEvent("Library_"+MATH+"_"+courseDo.getLabel());
								Map<String,String> params = new HashMap<String, String>();
								params.put(LIBRARY_PAGE, "course-page");
								params.put(SUBJECT, subjectCode);
								params.put("courseId", courseId);
								AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
							}
						});
						mathCourses.add(courseTitle);
					} 
								
					else if(subjectname.equalsIgnoreCase(SOCIAL)) {
						Label courseTitle = new Label(courseDo.getLabel());
						courseTitle.setStyleName(libraryStyleUc.courseOption());
						final String courseId = courseDo.getCodeId().toString();
						courseDoMap.put(courseId, courseDo);
						courseTitle.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								setHeaderBrowserTitle(courseDo.getLabel());
								MixpanelUtil.mixpanelEvent("Library_"+SOCIAL+"_"+courseDo.getLabel());
								Map<String,String> params = new HashMap<String, String>();
								params.put(LIBRARY_PAGE, "course-page");
								params.put(SUBJECT, subjectCode);
								params.put("courseId", courseId);
								AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
							}
						});
						socialCourses.add(courseTitle);
					} else if(subjectname.equalsIgnoreCase(LANGUAGE)) {
						Label courseTitle = new Label(courseDo.getLabel());
						courseTitle.setStyleName(libraryStyleUc.courseOption());
						final String courseId = courseDo.getCodeId().toString();
						courseDoMap.put(courseId, courseDo);
						courseTitle.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								setHeaderBrowserTitle(courseDo.getLabel());
								MixpanelUtil.mixpanelEvent("Library_"+LANGUAGE+"_"+courseDo.getLabel());
								Map<String,String> params = new HashMap<String, String>();
								params.put(LIBRARY_PAGE, "course-page");
								params.put(SUBJECT, subjectCode);
								params.put("courseId", courseId);
								AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
							}
						});
						elaCourses.add(courseTitle);
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
			AppClientFactory.fireEvent(new OpenSubjectCourseEvent(subjectname, courseDoMap.get(courseIdRefresh)));
		}
	}
	
	protected void setTaxonomyDataforStandards(String subjectname, final String subjectCode, String courseIdRefresh, ArrayList<StandardCourseDo> courseDoList) {
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
									setHeaderBrowserTitle(standardsCourseDo.getLabel());
									//MixpanelUtil.mixpanelEvent("Library_"+STANDARDS+"_"+standardsCourseDo.getLabel());
									MixpanelUtil.mixpanelEvent("standardlibrary_select_course");
									Map<String,String> params = new HashMap<String, String>();
									params.put(LIBRARY_PAGE, "course-page");
									params.put(SUBJECT, STANDARDS);
									params.put("courseId", courseId);
									params.put("standardId", standardsId);
									if(courseTitle.getText().contains("Texas")) {
										params.put("libtype", "TEKS");
									}
									AppClientFactory.getPlaceManager().revealPlace(getPlaceToken(),params);
									
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
					getStandardPrefCode(AppClientFactory.getLoggedInUser().getMeta().getTaxonomyPreference().getCode());
				} catch (Exception e) {
					e.printStackTrace();
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
			AppClientFactory.fireEvent(new OpenSubjectCourseEvent(subjectname, courseDoMap.get(courseIdRefresh)));
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
			subjectIdList.put(entry.getKey(), entry.getValue().getCode()+"");
			/*if(entry.getKey().equals(SCIENCE)) {
				sciencePanel.getElement().setAttribute(CODE_ID, entry.getValue().getCode());
				sciencePanel.getElement().setAttribute(SUBJECT, entry.getKey());
			} else if(entry.getKey().equals(MATH)) {
				mathPanel.getElement().setAttribute(CODE_ID, entry.getValue().getCode());
				mathPanel.getElement().setAttribute(SUBJECT, entry.getKey());
			} else if(entry.getKey().equals(SOCIAL)) {
				socialPanel.getElement().setAttribute(CODE_ID, entry.getValue().getCode());
				socialPanel.getElement().setAttribute(SUBJECT, entry.getKey());
			} else if(entry.getKey().equals(LANGUAGE)) {
				elaPanel.getElement().setAttribute(CODE_ID, entry.getValue().getCode());
				elaPanel.getElement().setAttribute(SUBJECT, entry.getKey());				
			}
			tabsInner.getElement().setId(TABS_INNER);*/
		}
	}
	
	public void setSubjectPanelIdsForStandards(HashMap<String, StandardsDo> subjectList) {
		for (Map.Entry<String, StandardsDo> entry : subjectList.entrySet()) {
			subjectIdList.put(entry.getKey(), entry.getValue().getCode()+"");
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
			if(entry.getValue().getCode().equals(subjectId)) {
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
			if(entry.getKey().equalsIgnoreCase(subject)) {
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
		AppClientFactory.getInjector().getLibraryService().getPartners(new AsyncCallback<ArrayList<LibraryUserDo>>() {
			@Override
			public void onSuccess(ArrayList<LibraryUserDo> partnersList) {
				setPartners(partnersList);
				setTabSelection(AppClientFactory.getCurrentPlaceToken());
			}

			@Override
			public void onFailure(Throwable caught) {}
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
