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
package org.ednovo.gooru.client.mvp.gshelf.util;

import java.util.List;

import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.UlPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class CourseGradeWidget extends Composite {
	private static CourseGradeWidgetUiBinder uiBinder = GWT
			.create(CourseGradeWidgetUiBinder.class);

	interface CourseGradeWidgetUiBinder extends
			UiBinder<Widget, CourseGradeWidget> {
	}

	@UiField UlPanel ulGradePanel;
	
	List<String> selectedValues;
	
	final String ACTIVE="active";
	String type;
	
	public CourseGradeWidget(final List<CourseSubjectDo> libraryCodeDo,List<String> selectedValues,String type) {
		initWidget(uiBinder.createAndBindUi(this));
		this.selectedValues=selectedValues;
		this.type=type;
		if(libraryCodeDo!=null){
			setData(libraryCodeDo);
		}
	}
	public void setData(final List<CourseSubjectDo> libraryCodeDo){
		ulGradePanel.clear();
		if(libraryCodeDo.size()>0){
			for(int j=0; j<libraryCodeDo.size(); j++) {
				final String gradeText=libraryCodeDo.get(j).getName();
				final CourseSubjectDo libraryObj=libraryCodeDo.get(j);
				long codeId = 0;
				if(type.equalsIgnoreCase("course")){
					codeId=libraryCodeDo.get(j).getCourseId();
				}else if(type.equalsIgnoreCase("domain")){
					codeId=libraryCodeDo.get(j).getSubdomainId();
				}
				final LiPanel panel=new LiPanel();
				if(selectedValues.contains(gradeText)){
					panel.addStyleName(ACTIVE);
				}
				panel.setCodeId(codeId);
				Anchor courseValues=new Anchor(gradeText);
				panel.add(courseValues);
				panel.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						GWT.runAsync(new SimpleRunAsyncCallback() {
							@Override
							public void onSuccess() {
								if(panel.getStyleName().contains(ACTIVE)){
									setSelectedGrade(libraryObj,panel.getCodeId(),false,panel);
								}else{
									setSelectedGrade(libraryObj,panel.getCodeId(),true,panel);
								}
							}
						});
					}
				});
				ulGradePanel.add(panel);
			}
		}
	}
	/**
	 * This method will return the grade panel
	 * @return
	 */
	public UlPanel getGradePanel(){
		return ulGradePanel;
	}
	public abstract void setSelectedGrade(CourseSubjectDo libraryCodeDo,long codeId,boolean isAdd,LiPanel panel);
}
