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
/**
 *
 */
package org.ednovo.gooru.client.mvp.classpages.resource.item;

import org.ednovo.gooru.application.client.child.ChildPresenter;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ClasspageServiceAsync;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.classpages.event.RefreshClasspageResourceItemListEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;

/**
 *
 * @fileName : ClasspageResourceItemChildPresenter.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 07-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class ClasspageResourceItemChildPresenter extends ChildPresenter<ClasspageResourceItemChildPresenter, IsClasspageResourceItemView> {


	private SimpleAsyncCallback<Void> deleteClasspageAsyncCallback;


	/**
	 * Class constructor
	 *
	 * @param childView
	 */
	public ClasspageResourceItemChildPresenter(IsClasspageResourceItemView childView) {
		super(childView);
	}



	/**
	 *  Delete classpage by classpage id which is mandatory
	 *
	 * @param classpageId  gooruOid of collection item
	 */
	public void deleteClasspage(final CollectionDo classpage) {

		 AppClientFactory.getInjector().getClasspageService().deleteClasspage(classpage.getGooruOid(), new SimpleAsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {

				getView().asWidget().removeFromParent();

				AppClientFactory.fireEvent(new RefreshClasspageResourceItemListEvent(classpage, RefreshType.DELETE));

			}
		});
	}


	/**
	 * @return the deleteClasspageAsyncCallback
	 */
	public SimpleAsyncCallback<Void> getDeleteClasspageAsyncCallback() {


		return deleteClasspageAsyncCallback;
	}

	/**
	 * @param deleteClasspageAsyncCallback the deleteClasspageAsyncCallback to set
	 */
	public void setDeleteClasspageAsyncCallback(
			SimpleAsyncCallback<Void> deleteClasspageAsyncCallback) {
		this.deleteClasspageAsyncCallback = deleteClasspageAsyncCallback;
	}
	/**
	 *
	 * @function getClasspageService
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : ClasspageServiceAsync
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public ClasspageServiceAsync getClasspageService() {
		return AppClientFactory.getInjector().getClasspageService();
	}

}
