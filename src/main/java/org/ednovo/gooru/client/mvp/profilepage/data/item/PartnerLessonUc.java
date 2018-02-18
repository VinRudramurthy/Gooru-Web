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
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.library.LessonDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.home.library.events.SetLoadingIconEvent;
import org.ednovo.gooru.client.mvp.profilepage.data.events.SetProfileCollectionStyleEvent;
import org.ednovo.gooru.client.mvp.profilepage.data.events.SetProfileCollectionStyleHandler;
import org.ednovo.gooru.client.mvp.profilepage.event.OpenProfileCollectionEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PartnerLessonUc extends Composite{

	private static final String ASSESSMENT = "assessment";
	@UiField HTMLPanel lessonList;
	private Integer topicId;
	private String conceptId;
	private Integer lessonId;
	private String lessonLabel;
	private String lessonCode;
	Map<String,Label> conceptTitles = new HashMap<String,Label>();
	HTMLPanel conceptList = new HTMLPanel("");
	HTML lessonTitle = new HTML();
	
	private static PartnerLessonUcUiBinder uiBinder = GWT
			.create(PartnerLessonUcUiBinder.class);

	interface PartnerLessonUcUiBinder extends UiBinder<Widget, PartnerLessonUc> {
	}

	public PartnerLessonUc(ArrayList<ProfileLibraryDo> profileLibraryDoList, Integer topicId, boolean isLessonHighlighted, Integer lessonNumber, boolean isPaginated,String libraryGooruOid) {
		initWidget(uiBinder.createAndBindUi(this));
		AppClientFactory.getEventBus().addHandler(SetProfileCollectionStyleEvent.TYPE, setProfileCollectionStyleHandler);
		this.topicId = topicId;
		setLessonData(null, null, profileLibraryDoList, isLessonHighlighted,lessonNumber,isPaginated,libraryGooruOid);
	}

	public PartnerLessonUc(ProfileLibraryDo profileLibraryDo, Integer topicId, boolean isLessonHighlighted, Integer lessonNumber, boolean isPaginated,String libraryGooruOid) {
		initWidget(uiBinder.createAndBindUi(this));
		AppClientFactory.getEventBus().addHandler(SetProfileCollectionStyleEvent.TYPE, setProfileCollectionStyleHandler);
		this.topicId = topicId;
		if(profileLibraryDo.getType().equals("collection") || profileLibraryDo.getType().contains("assessment")) {
			setCollectionData(profileLibraryDo, isLessonHighlighted, lessonNumber,libraryGooruOid);
		} else {
			setLessonData(null, profileLibraryDo, profileLibraryDo.getCollectionItems(),isLessonHighlighted,lessonNumber, isPaginated,libraryGooruOid);
		}
	}
	/**
	 * 
	 * @param arrayList 
	 * @param lessonNumber 
	 * @function setLessonData 
	 * 
	 * @created_date : 11-Dec-2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param lessonDo
	 * @parm(s) : @param isLessonHighlighted
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>	 
	 * 
	 */
	private void setLessonData(final LessonDo lessonDo, final ProfileLibraryDo profileLibraryDo, ArrayList<ProfileLibraryDo> profileLibraryDoList, boolean isLessonHighlighted, Integer lessonNumber, boolean isPaginated,final String libraryGooruOid) {
		lessonList.getElement().setId("pnlLessonList");
		if(profileLibraryDo!=null) {
			lessonTitle.setHTML(profileLibraryDo.getTitle());
			lessonList.add(lessonTitle);
			lessonId = lessonNumber;
		}
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.PROFILE_PAGE)) {
			lessonTitle.addStyleName("lessonTitle");
			if(lessonNumber == 1) {
				lessonTitle = setOpenStyle(true);
			} else {
				lessonTitle = setOpenStyle(false);
			}
			lessonTitle.addClickHandler(new OpenLessonHandler());
		} else {
			if(lessonNumber==1) {
				lessonTitle.addStyleName("marginTop5");
			}
			lessonTitle.addStyleName("libraryTitle");
		}
		for(int i = 0; i<profileLibraryDoList.size(); i++) {
			String conceptTitle = "";
			ProfileLibraryDo profileLibraryTemp = null;
			if((AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SAUSD_LIBRARY)|| 
					AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RUSD_LIBRARY)|| 
					AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.EPISD_LIBRARY)|| 
					AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SUSD)|| 
					AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.VALVERDE))&&(isPaginated==false)) {
				profileLibraryTemp = profileLibraryDoList.get(i).getCollectionItems().get(0);
			} else {
				profileLibraryTemp = profileLibraryDoList.get(i);
			}
			final ProfileLibraryDo profileLibrary = profileLibraryTemp;
			conceptTitle = profileLibrary.getTitle();
			
			Label conceptTitleLbl = new Label(conceptTitle);
			if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.PROFILE_PAGE)) {
				conceptTitleLbl.addStyleName("conceptTitle"); 
				if(profileLibraryTemp.getType().contains(ASSESSMENT)){
					conceptTitleLbl.addStyleName("assessmentSmall");
				}else{
					conceptTitleLbl.addStyleName("collectionSmall");
				}
				
			} else {
				conceptTitleLbl.addStyleName("libraryConceptTitle");
			}

			conceptList.add(conceptTitleLbl);
			conceptTitles.put(profileLibrary.getGooruOid(), conceptTitleLbl);
			if(i==0&&isLessonHighlighted) {
				if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.PROFILE_PAGE)) {
					conceptTitleLbl.addStyleName("conceptActive");
					conceptId = profileLibrary.getGooruOid();
					openCollection(libraryGooruOid);
				} else {
					conceptTitleLbl.addStyleName("libraryConceptActive");
				}
				isLessonHighlighted = false;
			}
			conceptTitleLbl.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					conceptId = profileLibrary.getGooruOid();
					openCollection(libraryGooruOid);
				}
			});
		}
		lessonList.add(conceptList);
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.PROFILE_PAGE)) {
			if(lessonNumber == 1) {
				conceptList = openConceptList(true);
			} else {
				conceptList = openConceptList(false);
			}
		}
	}

	public void setCollectionData(final ProfileLibraryDo profileLibraryDo, boolean isLessonHighlighted, Integer lessonNumber,final String libraryGooruOid) {
		if(profileLibraryDo!=null) {
			lessonTitle.setHTML(profileLibraryDo.getTitle());
			lessonList.add(lessonTitle);
			lessonId = lessonNumber;
			conceptId = profileLibraryDo.getGooruOid();
			if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.PROFILE_PAGE)) {
				if(profileLibraryDo.getType().contains(ASSESSMENT)){
					lessonTitle.setStyleName("assessment");
				}else{
					lessonTitle.setStyleName("collection"); 
				}
				lessonTitle.addStyleName("lessonTitle");
				
			} else {
				//lessonTitle.addStyleName(style.libraryTitle());
				lessonTitle.addStyleName("libraryConceptTitle");
			}
		}
		if(lessonNumber==1&&isLessonHighlighted) {
			if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.PROFILE_PAGE)) {
				lessonTitle.addStyleName("conceptActive");
			} else {
				lessonTitle.addStyleName("libraryConceptActive");
				lessonTitle.addStyleName("marginTop5");
			}
			openCollection(libraryGooruOid);
			isLessonHighlighted = false;
		}
		lessonTitle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				openCollection(libraryGooruOid);
				NodeList links = lessonList.getElement().getParentElement().getChildNodes();
				    for (int i = 0; i < links.getLength(); i++) {
				      com.google.gwt.user.client.Element link =
				        (com.google.gwt.user.client.Element) links.getItem(i);
				      link.getFirstChildElement().removeClassName("conceptActive");
				      link.getFirstChildElement().removeClassName("libraryConceptActive");
				    }
				lessonTitle.addStyleName("conceptActive");
			}
		});
	}
	
	private void openCollection(String libraryGooruOid) {
		AppClientFactory.fireEvent(new SetProfileCollectionStyleEvent(conceptId,topicId,lessonId));
		AppClientFactory.fireEvent(new SetLoadingIconEvent(true,topicId));
		getConceptDetails(conceptId,libraryGooruOid);
	}
	
	/**
	 * 
	 * @function getConceptDetails 
	 * 
	 * @created_date : 13-Dec-2013
	 * 
	 * @description
	 * 
	 * @parm(s) : @param gooruOid
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private void getConceptDetails(String gooruOid,final String libraryGooruOid) {
		AppClientFactory.getInjector().getProfilePageService().getProfileLibraryCollection(gooruOid, false, new SimpleAsyncCallback<ProfileLibraryDo>() {
			@Override
			public void onSuccess(ProfileLibraryDo profileLibraryDo) {
				if(profileLibraryDo != null){
					AppClientFactory.fireEvent(new OpenProfileCollectionEvent(profileLibraryDo,topicId,lessonId+"",lessonLabel,lessonCode,libraryGooruOid));
				}
			}			
		});
	}
	
	
	SetProfileCollectionStyleHandler setProfileCollectionStyleHandler = new SetProfileCollectionStyleHandler() {
		@Override
		public void setProfileCollectionStyleHandler(String collectionId, Integer topicNo, Integer lessonNo) {
			if(topicNo==topicId) {
				String activeStyle = "";
				if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.PROFILE_PAGE)) {
					activeStyle = "conceptActive";
				} else {
					activeStyle = "libraryConceptActive";
				}
				for (Map.Entry<String, Label> entry : conceptTitles.entrySet()) {
				    if(entry.getKey().equals(collectionId)&&(lessonId==lessonNo)) {
				    	entry.getValue().addStyleName(activeStyle);
				    } else {
				    	entry.getValue().removeStyleName(activeStyle);
				    }
				}
			}
		}
	};
	
	private HTML setOpenStyle(boolean isOpen) {
		if(isOpen) {
			lessonTitle.addStyleName("folderOpen");
		} else {
			lessonTitle.removeStyleName("folderOpen");
		}
		return lessonTitle;
	}
	
	private HTMLPanel openConceptList(boolean isOpen) {
		if(isOpen) {
			conceptList.setVisible(true);
		} else {
			conceptList.setVisible(false);
		}
		return conceptList;
	}
	
	public class OpenLessonHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			if(conceptList.isVisible()) {
				openConceptList(false);
				setOpenStyle(false);
			} else {
				openConceptList(true);
				setOpenStyle(true);
			}
		}
	}
}