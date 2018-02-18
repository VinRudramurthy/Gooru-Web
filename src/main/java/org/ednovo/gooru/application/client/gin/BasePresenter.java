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
package org.ednovo.gooru.application.client.gin;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;

/**
 * 
 * @fileName : BasePresenter.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */

public abstract class BasePresenter<V extends IsView, P extends Proxy<?>> extends Presenter<V, P> {

	/**
	 * Class constructor
	 * @param view instance of {@link View}
	 * @param proxy instance of {@link Proxy}
	 */
	public BasePresenter(V view, P proxy) {
		super(AppClientFactory.getEventBus(), view, proxy);
	}

	protected final IsPlaceManager getPlaceManager() {
		return AppClientFactory.getPlaceManager();
	}
	/**
	 * Lifecycle method called when binding the object.
	 */
	@Override
	public void onBind() {
		super.onBind();
		getView().onLoad();
//		this.getWidget().getElement().addClassName("wrapperParent");
//		Element element = Document.get().getElementById("divLoading");
//		if (element!=null){
//			element.removeFromParent();
//		}
	}
	/**
	 * Lifecycle method called on all visible presenters whenever a
	 * presenter is revealed anywhere in the presenter hierarchy.
	 */
	@Override
	protected void onReset() {
		super.onReset();
		if (AppClientFactory.getPlaceManager().refreshPlace()) {
			getView().reset();
		}
	}
	/**
	 *  Lifecycle method called whenever this presenter is about to be
	 *  hidden.
	 */
	@Override
	protected void onHide() {
		super.onHide();
		getView().onUnload();
	}
	/**
	 * Prepare the state of the presenter given the information contained in
	 * the {@link PlaceRequest}. This method is called when the
	 * {@link com.gwtplatform.mvp.client.proxy.PlaceManager PlaceManager} navigates
	 * to this {@link Presenter}.
	 */
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
	}
	/**
	 * Gets the current place token.
	 * 
	 * @return {@link String}
	 */
	public final String getCurrentPlaceToken() {
		return getPlaceManager().getCurrentPlaceRequest().getNameToken();
	}
	
}
