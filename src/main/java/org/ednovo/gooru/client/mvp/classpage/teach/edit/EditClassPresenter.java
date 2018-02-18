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
package org.ednovo.gooru.client.mvp.classpage.teach.edit;

import org.ednovo.gooru.client.mvp.classpage.teach.edit.coursePopup.AddCourseToClassPresenter;
import org.ednovo.gooru.client.mvp.gsearch.addResourcePopup.SearchAddResourceToCollectionPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;


/**
 * @fileName : EditClassPresenter.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 26-Jun-2015
 *
 * @Author tumbalam
 *
 * @Reviewer: 
 */
public class EditClassPresenter extends PresenterWidget<IsEditClassView> implements EditClassViewUiHandlers{
	
	AddCourseToClassPresenter addCourseToClassPresenter;
	
	@Inject
	public EditClassPresenter(EventBus eventBus,IsEditClassView view,AddCourseToClassPresenter addCourseToClassPresenter){
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
		super.onReveal();
	}

	@Override
	protected void onHide() {
		super.onHide();
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.EditClassViewUiHandlers#addCourseToClass()
	 */
	@Override
	public void addCourseToClass() {
		addCourseToClassPresenter.getUserShelfCourseData("", "class");
		addCourseToClassPresenter.getView().getAppPopUp().show();
		addCourseToClassPresenter.getView().getAppPopUp().center();
		addCourseToClassPresenter.getView().getAppPopUp().setGlassEnabled(true);
		
	}

}
