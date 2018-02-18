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
package org.ednovo.gooru.client.mvp.classpage.teach.reports;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.coursePopup.AddCourseToClassPresenter;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;


public class TeachStudentDashboardPresenter extends PresenterWidget<IsTeachStudentDashboardView> implements TeachStudentDashboardUiHandler{
	
	private ClasspageDo classpageDo;
	
	AddCourseToClassPresenter addCourseToClassPresenter;
	
	@Inject
	public TeachStudentDashboardPresenter(EventBus eventBus,IsTeachStudentDashboardView view,AddCourseToClassPresenter addCourseToClassPresenter){
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.addCourseToClassPresenter = addCourseToClassPresenter;
	}
	
	@Override
	public void onBind() {
		super.onBind();
	}

	@Override
	public void onReveal() {
		
	}

	@Override
	protected void onHide() {
		super.onHide();
		getView().getFrame().setUrl("");
	}
	
	@Override
	public void onReset() {
		
	}
	
	public void  loadNavigationPage(){
		getView().setContainerVisibility(false);
		boolean isNoCourse = false, isNoStudent = false;
		if(getClassDetails().getCourseGooruOid()==null) {
			isNoCourse = true;
		} else if(getClassDetails().getMemberCount()!=null&&getClassDetails().getMemberCount().equalsIgnoreCase("0")) {
			isNoStudent = true;
		}
		if(isNoCourse||isNoStudent) {
			getView().enableEmptyContainer(true, getClassDetails());
			getView().setContainerVisibility(true);
		} else {
			getView().enableEmptyContainer(false, null);
			String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID,null);
			String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID,null);
			String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID,null);
			String assessmentId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_ASSESSMENT_ID,null);
			if(courseId!=null&&unitId!=null&&assessmentId==null) {
				AppClientFactory.getInjector().getClasspageService().getStudentPlanProgressData(classId, courseId, null, null, "plan", null, new SimpleAsyncCallback<ArrayList<PlanProgressDo>>() {
					@Override
					public void onSuccess(ArrayList<PlanProgressDo> dataList) {
						getView().setMetadataContent(dataList);
						getView().setReportView();
						getView().setContainerVisibility(true);
					}
					@Override
					public void onFailure(Throwable caught) {
						getView().enableEmptyContainer(true, getClassDetails());
						getView().setContainerVisibility(true);
					}
				});
			} else {
				getView().setReportView();
				getView().setContainerVisibility(true);
			}
		}
	}

	@Override
	public void getXlsxReport(String htmlString, String fileName) {
		AppClientFactory.getInjector().getClasspageService().getXlsxReport(htmlString, fileName, new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if(!StringUtil.checkNull(result)){
					getView().getFrame().setUrl(result);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	public void setClassDetails(ClasspageDo classpageDo) {
		this.classpageDo = classpageDo;
	}
	
	public ClasspageDo getClassDetails() {
		return classpageDo;
	}

	@Override
	public void openAddPopup() {
		addCourseToClassPresenter.getUserShelfCourseData("", "class");
        addCourseToClassPresenter.getView().getAppPopUp().show();
        addCourseToClassPresenter.getView().getAppPopUp().center();
        addCourseToClassPresenter.getView().getAppPopUp().setGlassEnabled(true);
	}

}