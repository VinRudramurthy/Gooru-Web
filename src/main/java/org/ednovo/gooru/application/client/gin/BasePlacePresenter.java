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

import org.ednovo.gooru.application.client.wrap.WrapPresenter;

import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
/**
 *
 * @fileName : BasePlacePresenter.java
 *
 * @description :  This is base for the the place presenters.
 *
 *
 * @version : 1.0
 *
 * @date: 26-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public abstract class BasePlacePresenter<V extends IsView, P extends Proxy<?>> extends BasePresenter<V, P> {

	protected static final String GOORU_OID = "id";

	/**
	 * Class constructor
	 * @param view instance of {@link View}
	 * @param proxy instance of {@link Proxy}
	 */
	public BasePlacePresenter(V view, P proxy) {
		super(view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, WrapPresenter.TYPE_VIEW, this);
	}

	public abstract String getViewToken();

	@Override
	protected void onReveal() {
		super.onReveal();
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
		if (getView() instanceof PopupView) {
			onReset();
		}
	}
	/**
	 *  Lifecycle method called whenever this presenter is about to be
	 *  hidden.
	 */
	@Override
	protected void onHide() {
		super.onHide();
	}
	/**
	 *  This method is used to check whether is the current view or other.
	 */
	public final boolean isCurrentView() {
		return AppClientFactory.getCurrentPlaceToken().equals(getViewToken());
	}

}
