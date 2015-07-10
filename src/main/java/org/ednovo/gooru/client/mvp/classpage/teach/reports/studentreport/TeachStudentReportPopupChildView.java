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
package org.ednovo.gooru.client.mvp.classpage.teach.reports.studentreport;

import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.widgets.SlnCourseReportView;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.widgets.SlnUnitReportView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru Team
 * 
 */
public class TeachStudentReportPopupChildView extends ChildView<TeachStudentReportPopupChildPresenter> implements IsTeachStudentReportPopupView {

	@UiField HTMLPanel reportBodyBlock;
	
	private static TeachCourseReportChildViewUiBinder uiBinder = GWT.create(TeachCourseReportChildViewUiBinder.class);

	interface TeachCourseReportChildViewUiBinder extends UiBinder<Widget, TeachStudentReportPopupChildView> {
	}

	public TeachStudentReportPopupChildView() {
		initWidget(uiBinder.createAndBindUi(this));
		setPresenter(new TeachStudentReportPopupChildPresenter(this));
		getData();
	}
	
	public void getData() {
		setTableData();
	}
	
	@Override
	public void setTableData() {
		String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_REPORT_TYPE, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW)) {
			for(int i=0;i<4;i++) {
				reportBodyBlock.add(new SlnCourseReportView(i+1));
			}
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
			for(int i=0;i<10;i++) {
				reportBodyBlock.add(new SlnUnitReportView(i+1));
			}
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
			
		}
	}
}