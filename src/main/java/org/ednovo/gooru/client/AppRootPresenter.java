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
package org.ednovo.gooru.client;

import org.ednovo.gooru.application.client.gin.IsPlaceManager;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.RootPresenter;
/**
 *
 * @fileName : AppRootPresenter.java
 *
 * @description :  This is the presenter for the top-level of the application. It is derived
*  				 from presenter widget, but it's just because it doesn't need a proxy has it
*  				 will be bound as an eager singleton.
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
public class AppRootPresenter extends RootPresenter {
	/**
	 * Inner class.
	 *
	 */
	public static final class AppSoulView extends RootView {
		@Override
		public void setInSlot(Object slot, Widget content) {
			RootPanel.get().add(content);
		}
	}
	/**
	 *
	 * @param myRootView {@link AppSoulView}
	 * @param placeManager {@link IsPlaceManager}
	 * @param eventBus {@link EventBus}
	 */
	@Inject
	public AppRootPresenter(AppSoulView myRootView, IsPlaceManager placeManager, EventBus eventBus) {
		super(eventBus, myRootView);
	}
}