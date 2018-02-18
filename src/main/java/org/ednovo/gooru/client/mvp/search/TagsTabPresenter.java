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

package org.ednovo.gooru.client.mvp.search;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.search.SearchResourcesTagsDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Anchor;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;



/**
 * 
 * @fileName : TagsTabPresenter.java
 *
 * @description : 
 *
 *
 * @version : 1.1
 *
 * @date: 25-Nov-2014
 *
 * @Author : Gooru Team
 *
 * @Reviewer: Gooru Team
 */


public class TagsTabPresenter extends PresenterWidget<IsTagsTabView> implements TagsTabUiHandlers {

	/**
	 * @param eventBus {@link EventBus}
	 * @param view {@link IsTagsTabView}
	 */
	@Inject
	public TagsTabPresenter(EventBus eventBus, IsTagsTabView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
	 */
	@Override
	protected void onBind() {
		super.onBind();
	}


	/**
	 * 
	 * @param resourceId {@link String}
	 * @param resourceId1 {@link String}
	 */
	public void setData(String resourceId, Anchor tagsLbl) { 
		
		getView().setResourceTagsData(resourceId, tagsLbl);
		getView().isLoadingImageVisible(false);
		
	}


	/**
	 * Calls the API to get tags related to resource.
	 * 
	 * (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.search.TagsTabUiHandlers#getResourceTags(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void getResourceTags(String resourceId, String offSet, String limit, final boolean isTagsclear) {
		
		AppClientFactory.getInjector().getSearchService().getResourceTags(resourceId,offSet,limit, new SimpleAsyncCallback<SearchResourcesTagsDo>() {

			@Override
			public void onSuccess(SearchResourcesTagsDo result) {
				getView().setResourceTags(result,isTagsclear);
			}
		});
	}

}
