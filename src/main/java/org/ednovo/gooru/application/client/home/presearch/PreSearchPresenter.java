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
package org.ednovo.gooru.application.client.home.presearch;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.application.shared.model.search.SearchFilterDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.gsearch.util.GooruGradesPresenter;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.standards.StandardsPopupPresenter;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 *
 * @fileName : ViewMorePeoplePresenter.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 16-Jun-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class PreSearchPresenter<T extends ResourceSearchResultDo, C extends ResourceSearchResultDo> extends PresenterWidget<IsPreSearchView> implements PreSearchUiHandlers,ClientConstants{
	public static final Object GRADES = new Object();

	GooruGradesPresenter gooruGradesPresenter = null;
	StandardsPopupPresenter standardsPopupPresenter;

	private boolean isCCSSAvailable =false;
	private boolean isNGSSAvailable =false;
	private boolean isTEKSAvailable =false;
	private boolean isCAAvailable =false;
	private final String QUERY = "query";

	private SearchDo<T> searchDo = new SearchDo<T>();


	@Inject
	public PreSearchPresenter(EventBus eventBus, IsPreSearchView view, GooruGradesPresenter gooruGradesPresenter,StandardsPopupPresenter standardsPopupPresenter) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.gooruGradesPresenter = gooruGradesPresenter;
		this.standardsPopupPresenter = standardsPopupPresenter;
	}

	@Override
	protected void onBind() {
		super.onBind();
//		gooruGradesPresenter.setGradePanel(getView().getPanelGrades());
		gooruGradesPresenter.setPageType("home");
		setInSlot(GRADES, gooruGradesPresenter);
	}

	@Override
	protected void onReset() {
		getView().setButtonVisibility();
	}

	@Override
	public void loadSubjects(){
		AppClientFactory.getInjector().getSearchService().getSearchFilters(PlaceTokens.HOME,new SimpleAsyncCallback<SearchFilterDo>() {
			@Override
			public void onSuccess(SearchFilterDo searchFilterDo) {
				getView().setSearchFilter(searchFilterDo);
			}
		});
	}


	@Override
	public void refreshSearch(String query, String filterStd) {
		if(query!=null){
			getSearchDo().setQuery(query);
			onSearchRequest(filterStd);
		}
	}


	public SearchDo<T> getSearchDo() {
		return searchDo;
	}


	public void setSearchDo(SearchDo<T> searchDo) {
		this.searchDo = searchDo;
	}

	public void onSearchRequest(String filterStd) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("flt.standard", filterStd);
		params.put(QUERY, "*");
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SEARCH_COLLECTION, params, true);

	}
	@Override
	public void showStandardsPopup(String standardVal, String standardsDesc,List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
		Window.enableScrolling(false);
		standardsPopupPresenter.callStandardsBasedonTypeService(standardVal,standardsDesc);
		standardsPopupPresenter.setPreSearchPresenter(this);
		standardsPopupPresenter.setAlreadySelectedItems(collectionLiPanelWithCloseArray);
		addToPopupSlot(standardsPopupPresenter);
	}
	@Override
	public void setSelectedStandards(List<Map<String, String>> standListArray) {
		getView().setUpdatedStandards(standListArray);

	}


}
