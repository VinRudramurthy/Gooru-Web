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
package org.ednovo.gooru.client.mvp.classpage.study;

import org.ednovo.gooru.application.client.child.ChildPresenter;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;


/**
 * @fileName : StudyClassCodePresenter.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 21-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer: 
 */
public class StudyClassCodePresenter extends ChildPresenter<StudyClassCodePresenter,IsStudyClassCodeView> implements StudyClassCodeUiHandlers{
	
	
	
	@Inject
	public StudyClassCodePresenter(IsStudyClassCodeView view){
		super(view);
	}

	
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.study.StudyClassCodeUiHandlers#createNewClass(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void createNewClass(String title, String grade, boolean sharing) {
		AppClientFactory.getInjector().getClasspageService().createClass(title, grade, sharing, new AsyncCallback<ClasspageDo>() {
			
			@Override
			public void onSuccess(ClasspageDo result) {
				getView().setCreatedClass(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.study.StudyClassCodeUiHandlers#getClassData(java.lang.String)
	 */
	@Override
	public void getClassData(String classCodeText) {
		AppClientFactory.getInjector().getClasspageService().v3GetClassByCode(classCodeText, new AsyncCallback<ClasspageDo>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(ClasspageDo result) {
				getView().setClassData(result);
			}
		});
	}
	
	
}
