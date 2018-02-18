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
package org.ednovo.gooru.client.mvp.gshelf.coursedetails;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.SimpleAsyncCallback;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageListDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.standards.StandardsPopupPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

/**
 * @author Search Team
 *
 */
public class CourseSharePresenter extends PresenterWidget<IsCourseShareView> implements CourseShareUiHandlers {

	FolderDo folderDo;
	
	/**
	 * Class constructor
	 * @param view {@link View}
	 * @param proxy {@link Proxy}
	 */
	@Inject
	public CourseSharePresenter( EventBus eventBus,IsCourseShareView view,StandardsPopupPresenter standardsPopupPresenter) {
		super(eventBus,view);
		getView().setUiHandlers(this);
	}

	@Override
	public void onBind() {
		super.onBind();
	}

	@Override
	protected void onReveal(){
		super.onReveal();
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		getView().clearSharePlanes();
		getAssociatedClasses();
	}

	public void setData(FolderDo folderObj) {
		folderDo=folderObj;
		getTeachClassesList();
	}
	
	/**
	 * To get User teach classes with exclude course.
	 */
	private void getTeachClassesList() {
		// TODO Auto-generated method stub
		AppClientFactory.getInjector().getClasspageService().v3GetUserClasses("20", "0",true, new SimpleAsyncCallback<ClasspageListDo>() {

			@Override
			public void onSuccess(ClasspageListDo classPageListDo) {
				getView().setClassesList(classPageListDo.getSearchResult());
				
			}
		});
	}
   /**
    * To get classes of associated with Course
    */
	public void getAssociatedClasses() {
		// TODO Auto-generated method stub
		final String courseId= AppClientFactory.getPlaceManager().getRequestParameter("o1",null);
		if(courseId!=null){
			AppClientFactory.getInjector().getClasspageService().getClassesAssociatedWithCourse(courseId, new SimpleAsyncCallback<ArrayList<ClasspageDo>>() {

				@Override
				public void onSuccess(ArrayList<ClasspageDo> result) {
					getView().showClassesInList(result,courseId);
				}
			});
		}
		
	}

	/**
	 * To assign course to particular class.
	 */
	@Override
	public void assign2ClassPage(String value, String courseId) {
		AppClientFactory.getInjector().getClasspageService().v3UpdateClass(value, null,null,null,null,null,courseId, new SimpleAsyncCallback<ClasspageDo>() {

			@Override
			public void onSuccess(ClasspageDo result) {
				getTeachClassesList();
				getView().showClassesInList(null,folderDo.getGooruOid());
			}
			
		});
	}
	
}
