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
package org.ednovo.gooru.client.mvp.gsearch;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePresenter;
import org.ednovo.gooru.application.client.wrap.WrapPresenter;
import org.ednovo.gooru.client.mvp.gsearch.collection.SearchCollectionPresenter;
import org.ednovo.gooru.client.mvp.search.event.RefreshSearchEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
/**
 * @fileName : FolderTocPresenter.java
 *
 * @description :
 *
 * @version : 1.3
 *
 * @date: 06-02-2015
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class SearchMainPresenter extends BasePresenter<IsSearchMainView, SearchMainPresenter.IsSearchMainProxy> implements SearchMainUiHandlers {



	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_VIEW = new Type<RevealContentHandler<?>>();

	@ProxyCodeSplit
	public interface IsSearchMainProxy extends Proxy<SearchMainPresenter> {
	}

	@Inject
	public SearchMainPresenter(EventBus eventBus, IsSearchMainView view, IsSearchMainProxy proxy,SearchCollectionPresenter searchCollectionPresenter) {
		super(view, proxy);
		getView().setUiHandlers(this);
	}


	@Override
	protected void onReveal() {
		super.onReveal();
	}
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);

	}
	@Override
	protected void onReset() {
		super.onReset();
	}

	@Override
	public void onBind() {
		super.onBind();
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, WrapPresenter.TYPE_VIEW, this);
	}

	@Override
	public void getSearchKeyword() {
		String searchText=getView().getSearchText();
		if (searchText != null && searchText.length() > 0) {
			AppClientFactory.fireEvent(new RefreshSearchEvent(searchText));
		}
	}
}
