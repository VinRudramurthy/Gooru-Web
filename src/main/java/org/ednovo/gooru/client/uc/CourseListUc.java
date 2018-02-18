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
package org.ednovo.gooru.client.uc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.LibraryCodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.CollectionEditResourceCBundle;
import org.ednovo.gooru.client.mvp.shelf.event.AddCourseEvent;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
* @description : This class used for  Add a course to the collection.
*
* @version :1.0
*
* @date: APR 19 2014
   	
* @Author Gooru Team
* 
* Reviewer Gooru Team
 */

public class CourseListUc extends PopupPanel {

	private static CourseListUcUiBinder uiBinder = GWT
			.create(CourseListUcUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface CourseListUcUiBinder extends UiBinder<Widget, CourseListUc> {
	}

	/*@UiField Anchor fromweb;
	
	@UiField HTMLEventPanel urlTabButton;*/
	
	@UiField static FlowPanel contentPanel;
	
	@UiField HTMLPanel buttonsPanel,addCourseBtnPanel,loadingPanel;
	
	@UiField static HTMLPanel addResourceTabContainer;
	
	@UiField Button cancelCourseBtn;
	
	@UiField BlueButtonUc addCourseBtnLbl;
	
	@UiField Label titleLbl;
	
	private int courseCode;
	
	HTMLEventPanel subjectWidget;
	
    HTMLPanel subjectPanel;
	
	List<LibraryCodeDo> libraryCode;
	
	CollectionDo collectionDo;
	
	private String courseName, collectionId;
	
	private boolean isSelected;
	
	Map<String, ArrayList<String>> map;
	
	
	@UiField CollectionEditResourceCBundle res;
	
	public CourseListUc(CollectionDo collectionDo ) {
		setWidget(uiBinder.createAndBindUi(this));
		this.collectionDo=collectionDo;
		res = CollectionEditResourceCBundle.INSTANCE;
		CollectionEditResourceCBundle.INSTANCE.css().ensureInjected();
		this.setGlassStyleName(res.css().loginPopupGlassStyle());
	  	this.getElement().getStyle().setZIndex(99999);
		setGlassEnabled(true);
		isSelected=false;
		
		titleLbl.setText(i18n.GL0847());
		titleLbl.getElement().setId("errlblErrorLabel");
		titleLbl.getElement().setAttribute("alt", i18n.GL0847());
		titleLbl.getElement().setAttribute("title", i18n.GL0847());
		cancelCourseBtn.setText(i18n.GL0142());
		cancelCourseBtn.getElement().setId("btnCancelCourseBtn");
		cancelCourseBtn.getElement().setAttribute("alt", i18n.GL0142());
		cancelCourseBtn.getElement().setAttribute("title", i18n.GL0142());
		addCourseBtnLbl.setText(i18n.GL0590());
		addCourseBtnLbl.getElement().setId("bluebtnAddCourseBtnLbl");
		addCourseBtnLbl.getElement().setAttribute("alt", i18n.GL0590());
		addCourseBtnLbl.getElement().setAttribute("title", i18n.GL0590());
		loadingPanel.getElement().setId("pnlLoadingPanel");
		addResourceTabContainer.getElement().setId("pnlAddResourceTabContainer");
		contentPanel.getElement().setId("fpnlContentPanel");
		buttonsPanel.getElement().setId("pnlButtonsPanel");
		addCourseBtnPanel.getElement().setId("pnlAddCourseBtnPanel");
		
		loadingPanel.setVisible(true);
		setCourseData();
		collectionId=collectionDo.getGooruOid();
		Window.enableScrolling(false);
		
		this.setHeight("469px");
		this.setWidth("542px");
		
		this.center();
		this.show();
	}
	
 
	/**
	 * This method used to call getCourse api.
	 */
	private void setCourseData() {
		AppClientFactory.getInjector().getTaxonomyService().getCourse(new SimpleAsyncCallback<List<LibraryCodeDo>>() {

			@Override
			public void onSuccess(List<LibraryCodeDo> result) {
				setCourseList(result);
				loadingPanel.setVisible(false);
			}
		});
	}



	/**
	 * This method used to set the subjects and courses data from api.
	 * @param libraryCode instance of {@link LibraryCodeDo}
	 */
	public void setCourseList(final List<LibraryCodeDo> libraryCode) {
		this.libraryCode=libraryCode;
		Map<String,Integer> subjectList = new HashMap<String, Integer>();
		subjectPanel=new HTMLPanel("");
		for(int i = 0; i < libraryCode.size(); i++) {
			String subjectName = libraryCode.get(i).getLabel();
		    subjectWidget = new HTMLEventPanel(subjectName);
			subjectWidget.setStyleName(res.css().buttonDeSelected());
			subjectWidget.getElement().setId(subjectName);
			subjectList.put(subjectName, i);
			subjectPanel.add(subjectWidget);
			if(i==0) {
				List<LibraryCodeDo> libraryCodeDo = libraryCode.get(i).getNode();
				subjectWidget.addStyleName(res.css().collectionInfobuttonSelected());
				setCourseData(libraryCodeDo);
			}
		}
		addResourceTabContainer.add(subjectPanel);
		final Map<String,Integer> subjects = subjectList;
		
		Iterator<Widget> widgets = subjectPanel.iterator();
		while (widgets.hasNext()) {
			final Widget widget = widgets.next();
			if (widget instanceof HTMLEventPanel) {
				((HTMLEventPanel) widget).addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						int subjectCode = subjects.get(widget.getElement().getId());
						final Iterator<Widget> widgetsPanel = subjectPanel.iterator();
						while (widgetsPanel.hasNext()) {
							 widgetsPanel.next().removeStyleName(res.css().collectionInfobuttonSelected());
							}
						if(subjectCode==0){
							 widget.addStyleName(res.css().buttonDeSelected());
						 }
						widget.addStyleName(res.css().collectionInfobuttonSelected());
						setCourseData(libraryCode.get(subjectCode).getNode());
					}
				});
			}
		}
	}
	
	/**
	 * This method used to set Course List.
	 * @param libraryCodeDo instance of {@link LibraryCodeDo}
	 */
	private void setCourseData(final List<LibraryCodeDo> libraryCodeDo) {
		final HTMLPanel panel=new HTMLPanel("");
		panel.clear();
		contentPanel.clear();
		Map<String,Integer> courseList = new HashMap<String, Integer>();
		for(int j=0; j<libraryCodeDo.size(); j++) {
			String courseListValues=libraryCodeDo.get(j).getLabel();
			Label courseValues=new Label(courseListValues);
			courseValues.setStyleName(res.css().infoCourseListText());
			courseValues.getElement().setId(courseListValues);
			courseList.put(courseListValues, j);
			panel.add(courseValues);
			
		}
		contentPanel.add(panel);
		final Map<String,Integer> course = courseList;
		Iterator<Widget> widgets = panel.iterator();
		while (widgets.hasNext()) {
			final Widget widget = widgets.next();
			if (widget instanceof Label) {
				((Label) widget).addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						isSelected=true;
						int subjectCode = course.get(widget.getElement().getId());
					    courseName=libraryCodeDo.get(subjectCode).getLabel();
					    
					    courseCode=libraryCodeDo.get(subjectCode).getCodeId();
					    final Iterator<Widget> widgetsPanel = panel.iterator();
						
						while (widgetsPanel.hasNext()) {
							 widgetsPanel.next().removeStyleName(res.css().collectionInfoCourseList());
						}
						widget.addStyleName(res.css().collectionInfoCourseList());
						
					}
				});
			}
		}

	}
	/**
	 * To set the Default Course data.
	 */
	
	public void setDefaultCourseData(CollectionDo collectionDo){
		this.collectionDo=collectionDo;
		collectionId=collectionDo.getGooruOid();
		isSelected=false;
		for(int i = 0; i < libraryCode.size(); i++) {
			if(i==0) {
				List<LibraryCodeDo> libraryCodeDo = libraryCode.get(i).getNode();
				setCourseData(libraryCodeDo);
			}
		}
		
		final Iterator<Widget> widgetsPanel = subjectPanel.iterator();
		while (widgetsPanel.hasNext()) {
			 widgetsPanel.next().removeStyleName(res.css().collectionInfobuttonSelected());
			 subjectPanel.getWidget(0).addStyleName(res.css().collectionInfobuttonSelected());
			}
	}
	
	/**
	 * To close the courseList popup.
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	
	@UiHandler("cancelCourseBtn")
	public void onClickClose(ClickEvent clickEvent){
		hide();
		Window.enableScrolling(true);
	}
	
	/**
	 * @param clickEvent
	 */
	@UiHandler("addCourseBtnLbl")
	public void onAddCourseBtnClick(ClickEvent clickEvent){
		MixpanelUtil.mixpanelEvent("Organize_Add_Course");
		String oldCourseId = "";
		String courseId= Integer.toString(courseCode);
		if(isSelected){
			collectionId=collectionDo.getGooruOid();
			for (CodeDo code : new HashSet<CodeDo>(collectionDo.getTaxonomySet())) {
				if(code.getDepth()==2){

				//	deleteCourse(collectionId, code.getCodeId());	
				}

					//oldCourseId=Integer.toString(code.getCodeId());
					//deleteCourse(collectionId, code.getCodeId());	
					//updateCourse(collectionId, oldCourseId,"delete");					

			}
			if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.SHELF)){
				MixpanelUtil.mixpanelEvent("Collaborator_edits_collection");
			}
			updateCourse(collectionId, courseId,"add");
			AppClientFactory.fireEvent(new AddCourseEvent(courseName,courseId));
			hide();
			Window.enableScrolling(true);
		}else{
			new AlertContentUc(i18n.GL0061(),i18n.GL1022());
		}
		
		
	}
	
	/**
	 * This method used modify/update the course of collection
	 * @param collectionId {@link String}
	 * @param courseCode {@link String}
	 * @param action {@link String}
	 */
	public void updateCourse(String collectionId, final String courseCode, String action) {
		AppClientFactory.getInjector().getResourceService().updateCollectionMetadata(collectionId, null, null, null, null, null, courseCode, null, null, action, new SimpleAsyncCallback<CollectionDo>() {

			@Override
			public void onSuccess(CollectionDo result) {	
				if(collectionDo!=null){
 					Set<CodeDo> codeSet=new HashSet<CodeDo>();
 					CodeDo codeDo=new CodeDo();
 					if(!courseCode.equalsIgnoreCase("")){
 						codeDo.setCodeId(Integer.valueOf(courseCode));
 					}
 					codeDo.setDepth((short)2);
 					codeSet.add(codeDo);
 					collectionDo.setTaxonomySet(codeSet);
 				}
			}
		});
	}
	/**
	 * This method used to delete the old course.
	 * @param collectionId {@link String}
	 * @param courseCode {@link String}
	 */
	public void deleteCourse(String collectionId, int courseCode) {
		AppClientFactory.getInjector().getResourceService().deleteTaxonomyResource(collectionId, courseCode, new SimpleAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				Set<CodeDo> codeSet=new HashSet<CodeDo>();
				collectionDo.setTaxonomySet(codeSet);
			}
		});
	}
}
