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
package org.ednovo.gooru.application.client.service;

import java.util.ArrayList;
import java.util.Map;

import org.ednovo.gooru.application.shared.model.search.AutoSuggestContributorSearchDo;
import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel1DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel2DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel3DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel4DO;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.search.AutoSuggestKeywordSearchDo;
import org.ednovo.gooru.application.shared.model.search.CollectionItemSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.CollectionSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.application.shared.model.search.SearchFilterDo;
import org.ednovo.gooru.application.shared.model.search.SearchResourcesTagsDo;
import org.ednovo.gooru.application.shared.model.skils.CenturySkilsDo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Search Team
 * 
 */
public interface SearchServiceAsync extends BaseServiceAsync {

	void getSearchFilters(String type, AsyncCallback<SearchFilterDo> callback);

	void getResourceSearchResults(SearchDo<ResourceSearchResultDo> searchInput, AsyncCallback<SearchDo<ResourceSearchResultDo>> callback);

	void getResourceSearchResultsJson(SearchDo<ResourceSearchResultDo> searchInput,AsyncCallback<SearchDo<ResourceSearchResultDo>> callback) throws GwtException, ServerDownException;
	
	void descralizeResourceSearchResults(String response,SearchDo<ResourceSearchResultDo> searchDo,AsyncCallback<SearchDo<ResourceSearchResultDo>> callback) throws GwtException, ServerDownException;
	
	
	void getCollectionSearchResults(SearchDo<CollectionSearchResultDo> searchInput, AsyncCallback<SearchDo<CollectionSearchResultDo>> callback);
	
	void getCollectionSearchResultsJson(SearchDo<CollectionSearchResultDo> searchInput,AsyncCallback<SearchDo<CollectionSearchResultDo>> callback);
	
	void descralizeCollectionSearchResults(String response,SearchDo<CollectionSearchResultDo> searchDo,AsyncCallback<SearchDo<CollectionSearchResultDo>> callback);

//	void getCollectionResources(SearchDo<ResourceSearchResultDo> searchDo, AsyncCallback<SearchDo<ResourceSearchResultDo>> callback);
	
	void getCollectionItems(String collectionId,AsyncCallback<SearchDo<CollectionItemSearchResultDo>> callback) throws GwtException;

	void getResourceCollections(SearchDo<CollectionSearchResultDo> searchDo, AsyncCallback<SearchDo<CollectionSearchResultDo>> callback);

//	void getSuggestSearchQuery(SearchDo<List<String>> searchDo, AsyncCallback<List<String>> callback);
	
	void getSuggestSource(SearchDo<String> searchDo, AsyncCallback<SearchDo<String>> callback);
	
	void getSuggestStandard(SearchDo<CodeDo> searchDo, AsyncCallback<SearchDo<CodeDo>> callback);
	
	void getShortenShareUrl(String contentGooruOid, Map<String, String> params,  AsyncCallback<Map<String, String>> callback);
	
	void getShortenShareUrlforAssign(String contentGooruOid, Map<String, String> params,String classpageItemId,  AsyncCallback<Map<String, String>> callback);
	
	void getGoogleSignin(String parms, AsyncCallback<String> callback);
	
	void getCollectionPlayDirectLink(String params, AsyncCallback<String> callback);
	
	void getHomeEndPointUrl(AsyncCallback<String> callback);
	
	void getSuggestSearchResultForResourceNoResult(SearchDo<ResourceSearchResultDo> searchInput,AsyncCallback<SearchDo<ResourceSearchResultDo>> callback);
	
	void getSuggestedAutokeyword(SearchDo<AutoSuggestKeywordSearchDo> searchDo, AsyncCallback<SearchDo<AutoSuggestKeywordSearchDo>> callback);
//	void getSuggestedSearchResultForCollectionNoResult(SearchDo<CollectionSearchResultDo> searchInput,AsyncCallback<SearchDo<CollectionSearchResultDo>> callback);
	
	void getGoogleSignin(String placeToken, Map<String, String> parms, AsyncCallback<String> callback);
	
	public void getSuggestStandardByFilterCourseId(SearchDo<CodeDo> searchDo,AsyncCallback<SearchDo<CodeDo>> callback);
	
	void getSuggestedAggregator(SearchDo<String> searchDo, AsyncCallback<SearchDo<String>> callback);
	
	void getSuggestedContributor(String query,String contributorquery, AsyncCallback<ArrayList<AutoSuggestContributorSearchDo>> callback);
	
	void getCollectionSuggestedResourceSearchResults(SearchDo<ResourceSearchResultDo> searchInput,String contentGorruOid, AsyncCallback<SearchDo<ResourceSearchResultDo>> callback);
	
	void getGoogleDrive(String url, Map<String, String> parms, AsyncCallback<String> callback);
	
	void getFirstLevelStandards(String levelOrder, String standardLabel, AsyncCallback<ArrayList<StandardsLevel1DO>> callback);
	
	void getSecondLevelStandards(String levelOrder, String standardLabel, AsyncCallback<ArrayList<StandardsLevel2DO>> callback);
	
	void getThirdLevelStandards(String levelOrder, String standardLabel, AsyncCallback<ArrayList<StandardsLevel3DO>> callback);
	
	void getFourthLevelStandards(String levelOrder, String standardLabel, AsyncCallback<ArrayList<StandardsLevel4DO>> callback);

	 public void getSuggestStandardByFilterCourseIdsource(SearchDo<CodeDo> searchDo,
			 AsyncCallback<SearchDo<CodeDo>> callback);
	 
	 void getGooruStoriesUrl(String parms, AsyncCallback<String> callback);
	 
	 void showGooruStoriesSection(AsyncCallback<String> callback);

	void getResourceTags(String resourceId, String offSet, String limit,AsyncCallback<SearchResourcesTagsDo> callback); 
	
	void getCenturySkilsRestuls(AsyncCallback<CenturySkilsDo> callback);
	
	void getSuggestCenturyByQuery(SearchDo<StandardFo> centuryDo,AsyncCallback<SearchDo<StandardFo>> callback);

	void isClientSideLoggersEnabled(AsyncCallback<String> callback); 

}
