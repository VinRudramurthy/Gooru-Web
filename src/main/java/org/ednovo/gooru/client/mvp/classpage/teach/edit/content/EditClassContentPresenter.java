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
package org.ednovo.gooru.client.mvp.classpage.teach.edit.content;

import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.ClassLessonDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.coursePopup.AddCourseToClassPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;


/**
 * @fileName : EditClassContentPresenter.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 02-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer: 
 */
public class EditClassContentPresenter extends PresenterWidget<IsEditClassContentView> implements EditClassContentViewUiHandler {
	
	AddCourseToClassPresenter addCourseToClassPresenter;
	
	@Inject
	public EditClassContentPresenter(EventBus eventBus,IsEditClassContentView view,AddCourseToClassPresenter addCourseToClassPresenter){
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.addCourseToClassPresenter=addCourseToClassPresenter;
	}
	
	@Override
	public void onBind() {
		super.onBind();
		
	}

	@Override
	public void onReveal() {
		/*String loadPage = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW);
		if(loadPage != null && loadPage.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASS_CONTENT_SUB_SETTINGS)){
			getUnitList(offset,limit);
		}*/
		
	}

	
	/*@Override
	private void getUnitList(int offset,int limit) {
		String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID);
		if(classId != null && courseId != null){
			AppClientFactory.getInjector().getClasspageService().getClassUnitList(classId, courseId, offset, limit, new AsyncCallback<List<FolderDo>>() {

				@Override
				public void onFailure(Throwable caught) {
					
				}

				@Override
				public void onSuccess(List<FolderDo> result) {
					getView().getUnitListView(result);
				}
			});
					
			
		}
	}*/

	@Override
	protected void onHide() {
		super.onHide();
	}
	
	@Override
	protected void onReset() {
		//getView().setNavigationTab();
	}

	public void setClassData(ClasspageDo classpageDo) {
		getView().setClassData(classpageDo);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.content.EditClassContentViewUiHandler#updateClass(org.ednovo.gooru.application.shared.model.content.ClasspageDo)
	 */
	@Override
	public void updateClass(String	 score) {
		String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID,"");
		if(!classId.isEmpty()){
			AppClientFactory.getInjector().getClasspageService().v3UpdateClass(classId, null,null,null,null,score,null, new AsyncCallback<ClasspageDo>() {
				
				@Override
				public void onSuccess(ClasspageDo result) {
					getView().setUpdateClass(result);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.content.EditClassContentViewUiHandler#getLessonList(java.lang.String)
	 */
	@Override
	public void getLessonList(String unitId) {
		String classUId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASS_PAGE_ID, null);
		String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, null);
		
		if(classUId != null && courseId != null && unitId  != null){
			AppClientFactory.getInjector().getClasspageService().getClassLessonCollectionList(classUId, courseId, unitId, 0, 20, new AsyncCallback<List<ClassLessonDo>>() {
				@Override
				public void onFailure(Throwable caught) {
					
				}

				@Override
				public void onSuccess(List<ClassLessonDo> result) {
				}
			});
		}else{
			
		}
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.content.EditClassContentViewUiHandler#updateCollectionOrAssignmentVisiblity(org.ednovo.gooru.application.shared.model.content.ClassLessonDo)
	 */
	@Override
	public void updateCollectionOrAssignmentVisiblity(List<ClassLessonDo> classLessonDo,String unitId) {
		String classUId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASS_PAGE_ID, null);
		String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, null);
		
		if(classUId != null && courseId != null && unitId  != null){
			AppClientFactory.getInjector().getClasspageService().updateClassLessonVisiblity(classUId, courseId, unitId, classLessonDo, new AsyncCallback<ClassLessonDo>() {

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(ClassLessonDo result) {
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.content.EditClassContentViewUiHandler#getUnitList(int, int)
	 */
	@Override
	public void getUnitList(int offset, int limit) {
		String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID);
		if(classId != null && courseId != null){
			AppClientFactory.getInjector().getClasspageService().getClassUnitList(classId, courseId, offset, limit, new AsyncCallback<List<FolderDo>>() {

				@Override
				public void onFailure(Throwable caught) {
					
				}

				@Override
				public void onSuccess(List<FolderDo> result) {
				}
			});
		}else{
		}
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.content.EditClassContentViewUiHandler#addCourseToClass()
	 */
	@Override
	public void addCourseToClass() {
		addCourseToClassPresenter.getUserShelfCourseData("", "class");
		addCourseToClassPresenter.getView().getAppPopUp().show();
		addCourseToClassPresenter.getView().getAppPopUp().center();
		addCourseToClassPresenter.getView().getAppPopUp().setGlassEnabled(true);
	}

	 

}
