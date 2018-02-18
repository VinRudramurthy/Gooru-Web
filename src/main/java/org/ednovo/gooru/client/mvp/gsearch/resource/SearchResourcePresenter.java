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
package org.ednovo.gooru.client.mvp.gsearch.resource;

import java.util.ArrayList;
import java.util.Map;

import org.ednovo.gooru.application.client.AppPlaceKeeper;
import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.service.SearchServiceAsync;
import org.ednovo.gooru.application.shared.model.search.CollectionSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.client.SearchAsyncCallbackForSearch;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.gsearch.GooruSearchUiHandlers;
import org.ednovo.gooru.client.mvp.gsearch.IsGooruSearchView;
import org.ednovo.gooru.client.mvp.gsearch.SearchAbstractPresenter;
import org.ednovo.gooru.client.mvp.gsearch.SearchMainPresenter;
import org.ednovo.gooru.client.mvp.gsearch.ViewMorePopup.ViewMorePeoplePresenter;
import org.ednovo.gooru.client.mvp.gsearch.addResourcePopup.SearchAddResourceToCollectionPresenter;
import org.ednovo.gooru.client.mvp.gsearch.util.GooruGradesPresenter;
import org.ednovo.gooru.client.mvp.gshelf.ShelfMainPresenter;
import org.ednovo.gooru.client.mvp.rating.RatingAndReviewPopupPresenter;
import org.ednovo.gooru.client.mvp.search.util.CollectionResourceWidget;
import org.ednovo.gooru.client.mvp.search.util.CollectionSearchWidget;
import org.ednovo.gooru.client.mvp.standards.StandardsPopupPresenter;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
/**
 * @fileName : SearchResourcePresenter.java
 *
 * @description :
 *
 * @version : 1.3
 *
 * @date: 10-04-2015
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class SearchResourcePresenter extends SearchAbstractPresenter<ResourceSearchResultDo, CollectionSearchResultDo, IsSearchResourceView, SearchResourcePresenter.IsSearchResourceProxy> implements GooruSearchUiHandlers {

	@Inject
	private SearchServiceAsync searchService;

	GooruGradesPresenter gooruGradesPresenter;

	StandardsPopupPresenter standardsPopupPresenter;

	SearchAddResourceToCollectionPresenter searchAddResourceToCollectionPresenter;

	ShelfMainPresenter shelfMainPresenter;

	ViewMorePeoplePresenter viewmorePeoplePresenter;

	RatingAndReviewPopupPresenter ratingAndReviewPopup;

	@ProxyCodeSplit
	@NameToken(PlaceTokens.SEARCH_RESOURCE)
	@UseGatekeeper(AppPlaceKeeper.class)
	public interface IsSearchResourceProxy extends ProxyPlace<SearchResourcePresenter> {
	}

	@Inject
	public SearchResourcePresenter(IsSearchResourceView view, IsSearchResourceProxy proxy,SignUpPresenter signUpViewPresenter,GooruGradesPresenter gooruGradesPresenter,SearchAddResourceToCollectionPresenter searchAddResourceToCollectionPresenter,ViewMorePeoplePresenter viewmorePeoplePresenter,RatingAndReviewPopupPresenter ratingAndReviewPopup,ShelfMainPresenter shelfMainPresenter,StandardsPopupPresenter standardsPopupPresenter) {
		super(view, proxy, signUpViewPresenter,gooruGradesPresenter,searchAddResourceToCollectionPresenter,viewmorePeoplePresenter,standardsPopupPresenter);
		this.standardsPopupPresenter=standardsPopupPresenter;
		this.ratingAndReviewPopup=ratingAndReviewPopup;
		this.gooruGradesPresenter=gooruGradesPresenter;
		this.searchAddResourceToCollectionPresenter=searchAddResourceToCollectionPresenter;
		this.viewmorePeoplePresenter = viewmorePeoplePresenter;
		this.shelfMainPresenter = shelfMainPresenter;
		getView().setUiHandlers(this);
	}

	@Override
	public String getViewToken() {
		return PlaceTokens.SEARCH_RESOURCE;
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, SearchMainPresenter.TYPE_VIEW, this);
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

	public SearchServiceAsync getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchServiceAsync searchService) {
		this.searchService = searchService;
	}
	@Override
	protected void requestSearch(SearchDo<ResourceSearchResultDo> searchDo,SearchAsyncCallbackForSearch<SearchDo<ResourceSearchResultDo>> searchAsyncCallback) {
		getSearchDo().setSearchResults(new ArrayList<ResourceSearchResultDo>());
		getSearchDo().setPageSize(9);
		getSearchService().getResourceSearchResultsJson(searchDo, getSearchResultsJsonAsyncCallbackFirstLoad());
	}
	@Override
	protected void requestSearchLoad(SearchDo<ResourceSearchResultDo> searchDo,SearchAsyncCallbackForSearch<SearchDo<ResourceSearchResultDo>> searchResultsJsonAsyncCallback,boolean isBackTotop) {
		getSearchDo().setSearchResults(new ArrayList<ResourceSearchResultDo>());
		if(isBackTotop){
			getSearchService().getResourceSearchResultsJson(searchDo, getSearchResultsBackToTop());
		}else{
			getSearchService().getResourceSearchResultsJson(searchDo, getSearchResultsJsonAsyncCallbackLoadInStore());
		}
	}
	@Override
	public void getCollectionSearchResultsOnPageWise(String query,int pageNumber, int pageSize) {
		getSearchDo().setSearchResults(new ArrayList<ResourceSearchResultDo>());
		getSearchDo().setPageNum(pageNumber);
		getSearchDo().setPageSize(pageSize);
		getSearchService().getResourceSearchResultsJson(getSearchDo(), getSearchResultsJsonAsyncCallbackLoadInStore());
	}

	/**
	 * @return search filters as Map value
	 */
	@Override
	protected Map<String, String> getSearchFilters() {
		Map<String, String> filters = super.getSearchFilters();
		String author = getPlaceManager().getRequestParameter(IsGooruSearchView.OWNER_FLT);
		if (author != null) {
			filters.put(IsGooruSearchView.OWNER_FLT, author);
		}
		return filters;
	}
	@Override
	public void displayAddResourcePoup(ResourceSearchResultDo resourceSearchResultDo,CollectionResourceWidget collectionResourceWidget) {
		shelfMainPresenter.SetDefaultTypeAndVersion();
		searchAddResourceToCollectionPresenter.DisableMyCollectionsPanelData(false);
		searchAddResourceToCollectionPresenter.getLoadingImage();
		searchAddResourceToCollectionPresenter.getUserShelfData(resourceSearchResultDo,"coursebuilder",collectionResourceWidget);
		addToPopupSlot(searchAddResourceToCollectionPresenter);
		Window.enableScrolling(false);
	}
	@Override
	public void displayUsersList(ResourceSearchResultDo resourceSearchResultDo) {
		viewmorePeoplePresenter.getResourceDataByResource(resourceSearchResultDo,"resoruce");
		//viewmorePeoplePresenter.getAddButton().addClickHandler(new ShowNewCollectionWidget(resourceSearchResultDo.getGooruOid()));
		addToPopupSlot(viewmorePeoplePresenter);
	}

	public void showRatingAndReviewPopup(ResourceSearchResultDo searchResultDo){
		Window.enableScrolling(false);
		Element element = Document.get().getElementById("fixedFilterSearchID");
		if(element!=null){
			element.setAttribute("style", "opacity:0.1;z-index:1;");
		}
		ratingAndReviewPopup.displayPopup(searchResultDo.getResourceTitle(), searchResultDo.getGooruOid(),null);
		addToPopupSlot(ratingAndReviewPopup);
	}

	@Override
	public void displayRemixForCollectionsPoup(CollectionSearchResultDo collectionsearchResultDo,CollectionSearchWidget collectionSearchWidget) {

	}
}
