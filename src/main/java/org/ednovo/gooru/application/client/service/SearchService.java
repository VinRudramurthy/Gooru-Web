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

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Search Team
 * 
 */
@RemoteServiceRelativePath("gwt-service/searchService")
public interface SearchService extends BaseService {

	/**
	 * Get search filters
	 * @param type 
	 * @return serialized {@link SearchFilterDo}
	 * @throws GwtException
	 */
	SearchFilterDo getSearchFilters(String type) throws GwtException, ServerDownException;

	/**
	 * Get resource search result 
	 * @param searchInput instance of {@link SearchDo} type of {@link ResourceSearchResultDo}
	 * @return serialized {@link ResourceSearchResultDo}
	 * @throws GwtException
	 */
	SearchDo<ResourceSearchResultDo> getResourceSearchResults(SearchDo<ResourceSearchResultDo> searchInput) throws GwtException, ServerDownException;

	SearchDo<ResourceSearchResultDo>  getResourceSearchResultsJson(SearchDo<ResourceSearchResultDo> searchInput) throws GwtException, ServerDownException;
	
	SearchDo<ResourceSearchResultDo> descralizeResourceSearchResults(String response,SearchDo<ResourceSearchResultDo> searchDo) throws GwtException, ServerDownException;
	
	/**
	 * Get collection search result
	 * @param searchInput instance of {@link SearchDo} type of {@link CollectionSearchResultDo}
	 * @return serialized {@link CollectionSearchResultDo}
	 * @throws GwtException
	 */
	SearchDo<CollectionSearchResultDo> getCollectionSearchResults(SearchDo<CollectionSearchResultDo> searchInput) throws GwtException, ServerDownException;

	SearchDo<CollectionSearchResultDo> getCollectionSearchResultsJson(SearchDo<CollectionSearchResultDo> searchInput) throws GwtException, ServerDownException;
	
	SearchDo<CollectionSearchResultDo> descralizeCollectionSearchResults(String response,SearchDo<CollectionSearchResultDo> searchDo) throws GwtException, ServerDownException;
	/**
	 * Get resources which are as collection item of specific collection
	 * @param searchDo instance of {@link SearchDo} type of {@link ResourceSearchResultDo}
	 * @return serialized {@link ResourceSearchResultDo}
	 * @throws GwtException
	 */
//	SearchDo<ResourceSearchResultDo> getCollectionResources(SearchDo<ResourceSearchResultDo> searchDo) throws GwtException, ServerDownException;
	
	
	SearchDo<CollectionItemSearchResultDo> getCollectionItems(String collectionId) throws GwtException, ServerDownException;

	/**
	 * Get collections which holds the specific resource as collection item
	 * @param searchDo instance of {@link SearchDo} type of {@link CollectionSearchResultDo}
	 * @return serialized {@link CollectionSearchResultDo}
	 * @throws GwtException
	 */
	SearchDo<CollectionSearchResultDo> getResourceCollections(SearchDo<CollectionSearchResultDo> searchDo) throws GwtException, ServerDownException;

	/**
	 * Get suggest query 
	 * @param searchDo instance of {@link SearchDo}
	 * @return serialized {@link SearchDo}
	 * @throws GwtException
	 */
//	SearchDo<String> getSuggestSearchQuery(SearchDo<String> searchDo) throws GwtException, ServerDownException;

	/**
	 * Get suggest source 
	 * @param searchDo instance of {@link SearchDo}
	 * @return serialized {@link SearchDo}
	 * @throws GwtException
	 */
	SearchDo<String> getSuggestSource(SearchDo<String> searchDo) throws GwtException, ServerDownException;

	/**
	 * Get suggest standards 
	 * @param searchDo instance of {@link SearchDo} type of {@link CodeDo}
	 * @return serialized {@link CodeDo}
	 * @throws GwtException
	 */
	SearchDo<CodeDo> getSuggestStandard(SearchDo<CodeDo> searchDo) throws GwtException, ServerDownException;

	/**
	 * Get shorten collection url
	 * @param contentGooruOid of collection
	 * @param params set values url,type
	 * @return shrotenUrl, rawUrl
	 * @throws GwtException
	 */
	Map<String, String> getShortenShareUrl(String contentGooruOid, Map<String, String> params) throws GwtException, ServerDownException;
	
	/**
	 * Get shorten collection url
	 * @param contentGooruOid of collection
	 * @param params set values url,type
	 * @return shrotenUrl, rawUrl
	 * @throws GwtException
	 */
	Map<String, String> getShortenShareUrlforAssign(String contentGooruOid, Map<String, String> params,String classpageItemId) throws GwtException, ServerDownException;
	
	
	/*
	 * get Google Signin API
	 */
	String getGoogleSignin(String parms) throws GwtException, ServerDownException;
	
	String getCollectionPlayDirectLink(String params) throws GwtException, ServerDownException;
	
	/**
	 * Get Home End point url
	 * @param null
	 * @return home end point url
	 * @throws GwtException
	 */
	String getHomeEndPointUrl() throws GwtException, ServerDownException;
	
	SearchDo<ResourceSearchResultDo> getSuggestSearchResultForResourceNoResult(SearchDo<ResourceSearchResultDo> searchInput) throws GwtException, ServerDownException;
	
//	SearchDo<CollectionSearchResultDo> getSuggestedSearchResultForCollectionNoResult(SearchDo<CollectionSearchResultDo> searchInput) throws GwtException, ServerDownException;
	/**
	 * Get suggest standards 
	 * @param searchDo instance of {@link SearchDo} type of {@link CodeDo}
	 * @return serialized {@link CodeDo}
	 * @throws GwtException
	 */
	SearchDo<AutoSuggestKeywordSearchDo> getSuggestedAutokeyword(SearchDo<AutoSuggestKeywordSearchDo> searchDo) throws GwtException, ServerDownException;

	/**
	 * 
	 * @function getGoogleSignin 
	 * 
	 * @created_date : Dec 9, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param parms
	 * @parm(s) : @return
	 * @parm(s) : @throws GwtException
	 * 
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	String getGoogleSignin(String placeToken, Map<String, String> parms) throws GwtException, ServerDownException;
	/**
	 * 
	 * @function getSuggestStandardByFilterCourseId 
	 * 
	 * @created_date : 
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param searchDo
	 * @parm(s) : @return
	 * @parm(s) : @throws GwtException
	 * @parm(s) : @throws ServerDownException
	 * 
	 * @return : SearchDo<CodeDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public SearchDo<CodeDo> getSuggestStandardByFilterCourseId(SearchDo<CodeDo> searchDo)  throws GwtException, ServerDownException; 
	/**
	 * 
	 * @function getSuggestedAggregator 
	 * 
	 * @created_date : 
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param searchDo
	 * @parm(s) : @return
	 * @parm(s) : @throws GwtException
	 * @parm(s) : @throws ServerDownException
	 * 
	 * @return : SearchDo<String>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	SearchDo<String> getSuggestedAggregator(SearchDo<String> searchDo) throws GwtException, ServerDownException;
	
	
	/**
	 * This method is used to get the contributor results
	 * @param searchDo
	 * @return
	 * @throws GwtException
	 * @throws ServerDownException
	 */
	 ArrayList<AutoSuggestContributorSearchDo> getSuggestedContributor(String query,String contributorquery) throws GwtException, ServerDownException;
	/**
	 * 
	 * @function getCollectionSuggestedResourceSearchResults 
	 * 
	 * @created_date : 
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param searchInput
	 * @parm(s) : @param contentGorruOid
	 * @parm(s) : @return
	 * @parm(s) : @throws GwtException
	 * @parm(s) : @throws ServerDownException
	 * 
	 * @return : SearchDo<ResourceSearchResultDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public SearchDo<ResourceSearchResultDo> getCollectionSuggestedResourceSearchResults(SearchDo<ResourceSearchResultDo> searchInput,String contentGorruOid) throws GwtException, ServerDownException;

	/**
	 * @function getGoogleDerive 
	 * 
	 * @created_date : Jul 2, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param placeToken
	 * @param parms
	 * @return
	 * 
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	public String getGoogleDrive(String url, Map<String, String> parms) throws GwtException, ServerDownException;
	/**
	 * 
	 * @function getFirstLevelStandards 
	 * 
	 * @created_date : 
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param levelOrder
	 * @parm(s) : @param standardLabel
	 * @parm(s) : @return
	 * 
	 * @return : ArrayList<StandardsLevel1DO>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	ArrayList<StandardsLevel1DO> getFirstLevelStandards(String levelOrder, String standardLabel);
	/**
	 * 
	 * @function getSecondLevelStandards 
	 * 
	 * @created_date :
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param levelOrder
	 * @parm(s) : @param standardLabel
	 * @parm(s) : @return
	 * 
	 * @return : ArrayList<StandardsLevel2DO>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	ArrayList<StandardsLevel2DO> getSecondLevelStandards(String levelOrder,
			String standardLabel);
	/**
	 * 
	 * @function getThirdLevelStandards 
	 * 
	 * @created_date : 
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param levelOrder
	 * @parm(s) : @param standardLabel
	 * @parm(s) : @return
	 * 
	 * @return : ArrayList<StandardsLevel3DO>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	ArrayList<StandardsLevel3DO> getThirdLevelStandards(String levelOrder,
			String standardLabel);
	/**
	 * 
	 * @function getFourthLevelStandards 
	 * 
	 * @created_date : 
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param levelOrder
	 * @parm(s) : @param standardLabel
	 * @parm(s) : @return
	 * 
	 * @return : ArrayList<StandardsLevel4DO>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	ArrayList<StandardsLevel4DO> getFourthLevelStandards(String levelOrder,
			String standardLabel);
	/**
	 * 
	 * @function getSuggestStandardByFilterCourseIdsource 
	 * 
	 * @created_date : 
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param searchDo
	 * @parm(s) : @return
	 * @parm(s) : @throws GwtException
	 * @parm(s) : @throws ServerDownException
	 * 
	 * @return : SearchDo<CodeDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public SearchDo<CodeDo> getSuggestStandardByFilterCourseIdsource(SearchDo<CodeDo> searchDo)  throws GwtException, ServerDownException;

	/**
	 * @function getGooruStoriesUrl 
	 * 
	 * @created_date : 20-Oct-2014
	 * 
	 * @description
	 * 
	 * 
	 * @param parms
	 * @return
	 * 
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	String getGooruStoriesUrl(String parms)  throws GwtException, ServerDownException;
	
	/**
	 * 
	 * @function showGooruStoriesSection 
	 * 
	 * @created_date : 11-Nov-2014
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @return
	 * 
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	String showGooruStoriesSection()  throws GwtException, ServerDownException; 
	
	
	public SearchResourcesTagsDo getResourceTags(String resourceId, String offSet, String limit) throws GwtException, ServerDownException; 

	/**
	 * This method is used to get 21 century skils details
	 * @return
	 */
	public CenturySkilsDo getCenturySkilsRestuls();
	/**
	 * This method is used to get sugesstions for add cenury
	 * @param query
	 * @return
	 */
	public SearchDo<StandardFo> getSuggestCenturyByQuery(SearchDo<StandardFo> centuryDo);
	
	public String isClientSideLoggersEnabled() throws GwtException, ServerDownException;


} 
