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
package org.ednovo.gooru.client.mvp.classpage.teach.edit.coursePopup;



import java.util.HashMap;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.search.util.CollectionResourceWidget;
import org.ednovo.gooru.client.mvp.search.util.CollectionSearchWidget;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 *
 * @fileName : SearchAddResourceToCollectionPresenter.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 22-APR-2015
 *
 * @Author : Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class AddCourseToClassPresenter extends PresenterWidget<IsAddCourseToClassView> implements AddCourseToClassUiHandlers,ClientConstants{

	ResourceSearchResultDo searchResultDo =null;
	String type =null;
	String accessType =null;
	String collectionId = null;
	HashMap<String, String>  urlParameters;
	CollectionResourceWidget collectionResourceWidget=null;
	CollectionSearchWidget collectionSearchWidget=null;
	HashMap<String,String> successparams = new HashMap<String, String>();

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	String classId;

	@Inject
	public AddCourseToClassPresenter(EventBus eventBus, IsAddCourseToClassView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.classId=AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	public void getWorkspaceData(int offset,int limit, final boolean clearShelfPanel,final String searchType){
		getView().clearUrlParams();
		if(searchType.equalsIgnoreCase("class")){
			type="course";
		}
		AppClientFactory.getInjector().getResourceService().getFolderWorkspace(offset, limit,null, type,true, new SimpleAsyncCallback<FolderListDo>() {
			@Override
			public void onSuccess(FolderListDo folderListDo) {
				if(type.equalsIgnoreCase("course")){
					getView().displayCourseWorkspaceData(folderListDo,clearShelfPanel,searchType);
				}
			}
		});
	}

	@Override
	public Anchor getAddButton() {
		return getView().getAddButton();
	}


	@Override
	public void hidePopup() {
		getView().hidePopup();
	}

	public void getUserShelfCourseData(String string, String type) {
		getWorkspaceData(0, 20, true, type);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.coursePopup.AddCourseToClassUiHandlers#connectCourseToClass(java.lang.String, org.ednovo.gooru.application.shared.model.content.ClasspageDo)
	 */
	@Override
	public void connectCourseToClass(final String classId, final String courseId) {
		AppClientFactory.getInjector().getClasspageService().v3UpdateClass(classId,null,null,null,null,null,courseId, new AsyncCallback<ClasspageDo>() {

			@Override
			public void onSuccess(ClasspageDo result) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put(UrlNavigationTokens.CLASSPAGEID, classId);
				params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, courseId);
				params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.TEACHER_CLASS_SETTINGS);
				params.put(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW, UrlNavigationTokens.TEACHER_CLASS_CONTENT_SUB_SCORE);
				getView().displaySuccessPopup("Class", classId, params);
			}

			@Override
			public void onFailure(Throwable caught) {

			}
		});
	}
}
