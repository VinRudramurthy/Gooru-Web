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
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;
import org.ednovo.gooru.application.shared.model.library.LessonDo;
import org.ednovo.gooru.application.shared.model.library.PartnerFolderDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.home.library.events.OpenLessonConceptEvent;
import org.ednovo.gooru.client.mvp.home.library.events.SetConceptQuizDataEvent;
import org.ednovo.gooru.client.mvp.home.library.events.SetConceptTitleStyleEvent;
import org.ednovo.gooru.client.mvp.home.library.events.SetConceptTitleStyleHandler;
import org.ednovo.gooru.client.mvp.home.library.events.SetLoadingIconEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LibraryLessonUc extends Composite{

	@UiField HTMLPanel lessonList;
	@UiField LibraryStyleBundle libraryStyleUc;
	private Integer topicId;
	private String conceptId;
	private Integer lessonId;
	private String lessonLabel;
	private String lessonCode;
	Map<String,Label> conceptTitles = new HashMap<String,Label>();
	private static final String SUBJECT_NAME = "subject";
	private static final String STANDARDS="standard";
	
	private static LibraryLessonUcUiBinder uiBinder = GWT.create(LibraryLessonUcUiBinder.class);

	interface LibraryLessonUcUiBinder extends UiBinder<Widget, LibraryLessonUc> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public LibraryLessonUc(LessonDo lessonDo, Integer topicId, boolean isLessonHighlighted, Integer lessonNumber,String libraryGooruOid) {
		initWidget(uiBinder.createAndBindUi(this));
		AppClientFactory.getEventBus().addHandler(SetConceptTitleStyleEvent.TYPE, setConceptTitleStyleHandler);
		this.topicId = topicId;
		if(lessonDo.getConcept()!=null&&lessonDo.getConcept().size()>0) {
			String subjectName = AppClientFactory.getPlaceManager().getRequestParameter(SUBJECT_NAME);
			if(subjectName!=null && subjectName.equalsIgnoreCase(STANDARDS)) {
			setLessonData(lessonDo,null,lessonDo.getCollection(),null,isLessonHighlighted,lessonNumber,libraryGooruOid);
			}
			setLessonData(lessonDo,null,null,lessonDo.getConcept(),isLessonHighlighted,lessonNumber,libraryGooruOid);
		} else {
			setLessonData(lessonDo,null,lessonDo.getCollection(),null,isLessonHighlighted,lessonNumber,libraryGooruOid);
		}
	}
	
	public LibraryLessonUc(ArrayList<ConceptDo> conceptDoList, Integer topicId, boolean isLessonHighlighted, Integer lessonNumber,String libraryGooruOid) {
		initWidget(uiBinder.createAndBindUi(this));
		AppClientFactory.getEventBus().addHandler(SetConceptTitleStyleEvent.TYPE, setConceptTitleStyleHandler);
		this.topicId = topicId;
		setLessonData(null, null, conceptDoList, null, isLessonHighlighted,lessonNumber,libraryGooruOid);
	}
	
	public LibraryLessonUc(PartnerFolderDo partnerFolderDo, Integer topicId, boolean isLessonHighlighted, Integer lessonNumber) {
		initWidget(uiBinder.createAndBindUi(this));
		AppClientFactory.getEventBus().addHandler(SetConceptTitleStyleEvent.TYPE, setConceptTitleStyleHandler);
		this.topicId = topicId;
		setLessonData(null, partnerFolderDo,partnerFolderDo.getCollections(),null, isLessonHighlighted,lessonNumber,null);
	}
	/**
	 * 
	 * @param arrayList 
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
	 * 
	 *
	 *
	 */
	private void setLessonData(final LessonDo lessonDo, final PartnerFolderDo partnerFolderDo, ArrayList<ConceptDo> conceptDoList, ArrayList<ConceptDo> conceptQuizList, boolean isLessonHighlighted, Integer lessonNumber,final String libraryGooruOid) {
		lessonList.getElement().setId("pnlLessonList");
		String subjectName = AppClientFactory.getPlaceManager().getRequestParameter(SUBJECT_NAME);
		if(lessonDo!=null) {
			HTML lessonTitle = new HTML(i18n.GL0910()+" "+lessonNumber+": "+lessonDo.getLabel());
			lessonTitle.setStyleName(libraryStyleUc.lessonTitle());
			
			if(subjectName!=null && subjectName.equalsIgnoreCase(STANDARDS)) {
			} else {
				lessonList.add(lessonTitle);
			}
			lessonId = lessonDo.getCodeId();
		} else {
			lessonId = lessonNumber;
		}

		if(partnerFolderDo!=null) {
			HTML lessonTitle = new HTML(i18n.GL0910()+" "+lessonNumber+": "+partnerFolderDo.getTitle());
			lessonTitle.setStyleName(libraryStyleUc.lessonTitle());

			if(subjectName!=null && subjectName.equalsIgnoreCase(STANDARDS)) {

			} else {
				lessonList.add(lessonTitle);
			}
			lessonId = lessonNumber;
		}
		
		if(conceptQuizList!=null&&conceptQuizList.size()>0) {
			for(int i = 0; i<conceptQuizList.size(); i++) {
				String conceptTitle = "";
				final ConceptDo conceptDo = conceptQuizList.get(i);
				if(subjectName!=null && subjectName.equalsIgnoreCase(STANDARDS)) {
					conceptTitle = conceptDo.getCode();
				}else{
					conceptTitle = conceptDo.getLabel();
				}
				Label conceptTitleLbl = new Label(conceptTitle);
				conceptTitleLbl.addStyleName(libraryStyleUc.conceptTitle());
				lessonList.add(conceptTitleLbl);
				conceptTitles.put(conceptDo.getCollection().get(0).getGooruOid(), conceptTitleLbl);
				if(i==0&&isLessonHighlighted) {
					conceptTitleLbl.addStyleName(libraryStyleUc.conceptTitleActive());
					isLessonHighlighted = false;
				}
				conceptTitleLbl.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						conceptId = conceptDo.getCollection().get(0).getGooruOid();
						AppClientFactory.fireEvent(new SetConceptTitleStyleEvent(conceptId,topicId,lessonId));
						AppClientFactory.fireEvent(new SetLoadingIconEvent(true,topicId));
						AppClientFactory.fireEvent(new SetConceptQuizDataEvent(conceptDo.getCollection(),topicId,lessonId+"",lessonLabel,lessonCode,conceptId,libraryGooruOid));
					}
				});
			}
		} else {
			for(int i = 0; i<conceptDoList.size(); i++) {
				String conceptTitle = "";
				final ConceptDo conceptDo = conceptDoList.get(i);
				if(subjectName!=null && subjectName.equalsIgnoreCase(STANDARDS)) {
					conceptTitle = lessonDo.getCode();
					lessonCode = lessonDo.getCode();
					lessonLabel = lessonDo.getLabel();
				} else {
					conceptTitle = conceptDo.getTitle();
				}
				Label conceptTitleLbl = new Label(conceptTitle);
				conceptTitleLbl.addStyleName(libraryStyleUc.conceptTitle());
				lessonList.add(conceptTitleLbl);
				conceptTitles.put(conceptDo.getGooruOid(), conceptTitleLbl);
				if(i==0&&isLessonHighlighted) {
					conceptTitleLbl.addStyleName(libraryStyleUc.conceptTitleActive());
					isLessonHighlighted = false;
				}
				conceptTitleLbl.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						conceptId = conceptDo.getGooruOid();
						AppClientFactory.fireEvent(new SetConceptTitleStyleEvent(conceptId,topicId,lessonId));
						AppClientFactory.fireEvent(new SetLoadingIconEvent(true,topicId));
						getConceptDetails(conceptId,libraryGooruOid);
					}
				});
			}
		}
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
		if(AppClientFactory.getPlaceManager().getRequestParameter("standardId")!=null){
			String standardsId = AppClientFactory.getPlaceManager().getRequestParameter("standardId");
			AppClientFactory.getInjector().getLibraryService().getConceptForStandards(gooruOid,standardsId, false, new SimpleAsyncCallback<ConceptDo>() {
				@Override
				public void onSuccess(ConceptDo conceptDo) {
					AppClientFactory.fireEvent(new OpenLessonConceptEvent(conceptDo,topicId,lessonId+"",lessonLabel,lessonCode,libraryGooruOid));
				}			
			});
		} else {
			AppClientFactory.getInjector().getLibraryService().getConcept(gooruOid, false, new SimpleAsyncCallback<ConceptDo>() {
				@Override
				public void onSuccess(ConceptDo conceptDo) {
					AppClientFactory.fireEvent(new OpenLessonConceptEvent(conceptDo,topicId,lessonId+"",lessonLabel,lessonCode,libraryGooruOid));
				}
			});
		}
	}
	
	SetConceptTitleStyleHandler setConceptTitleStyleHandler = new SetConceptTitleStyleHandler() {
		@Override
		public void setConceptTitleStyleHandler(String collectionId, Integer topicNo, Integer lessonNo) {
			if(topicNo==topicId) {
				for (Map.Entry<String, Label> entry : conceptTitles.entrySet()) {
				    if(entry.getKey().equals(collectionId)&&(lessonId==lessonNo)) {
					    entry.getValue().addStyleName(libraryStyleUc.conceptTitleActive());
				    } else {
				    	entry.getValue().removeStyleName(libraryStyleUc.conceptTitleActive());
				    }
				}
			}
		}
	};
}