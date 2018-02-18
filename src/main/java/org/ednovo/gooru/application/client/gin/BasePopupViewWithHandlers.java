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

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.shared.model.user.FilterSettings;
import org.ednovo.gooru.client.SeoTokens;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

/**
 *
 * @fileName : BasePopupViewWithHandlers.java
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

public abstract class BasePopupViewWithHandlers<H extends BaseUiHandlers> extends PopupViewWithUiHandlers<H> implements IsViewWithHandlers<H> {

	/**
	 * Class constructor
	 *
	 * @param eventBus instance of {@link com.google.gwt.event.shared.EventBus}
	 */
	protected BasePopupViewWithHandlers(EventBus eventBus) {
		super(eventBus);
	}

	private boolean isHideFromCloseButton=false;

	@Override
	public void show() {
		Window.enableScrolling(false);
		super.show();
		super.center();
	}

	@Override
	public void hide() {
		super.hide();
		String count = Cookies.getCookie("MyCookie");
		String scrollVal = Cookies.getCookie("getScrollTop");
		if(count!= null && Integer.parseInt(count)==7){

		}
		else{
			Document.get().getDocumentElement().getStyle().setProperty("overflow", "");
		}
		if(!StringUtil.isEmpty(scrollVal) && AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.SEARCH_COLLECTION)){
			Window.scrollTo(0, Integer.parseInt(scrollVal));
		}
	}

	public void hideFromPopup(boolean isHideFromCloseButton){
		Element element = Document.get().getElementById("fixedFilterSearchID");
		if(element!=null){
			element.removeAttribute("style");
		}
		this.isHideFromCloseButton=isHideFromCloseButton;
	}

	@Override
	public void reset() {

	}

	@Override
	public void onUnload() {
		if(isHideFromCloseButton){
			if(!AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.HOME)){
				AppClientFactory.getPlaceManager().revealClosePlayer();
				setSeoToken();
			}
		}
	}

	@Override
	public void onLoad() {
	}

	protected abstract String getDefaultView();

	protected FilterSettings getSettings() {
		return AppClientFactory.getLoggedInUser() != null ? AppClientFactory.getLoggedInUser().getSettings() : null;
	}
	private void setSeoToken(){
		String seoToken=AppClientFactory.getPlaceManager().getBeforePlayerOpenSeoToken();
		if(seoToken!=null&&!seoToken.equals("")){
			Window.setTitle(seoToken);
		}else if(AppClientFactory.isAnonymous()){
			Window.setTitle(SeoTokens.HOME_TITLE_ANONYMOUS);
		}else{
			Window.setTitle(SeoTokens.HOME_TITLE_LOGGEDIN);
		}
	}

}
