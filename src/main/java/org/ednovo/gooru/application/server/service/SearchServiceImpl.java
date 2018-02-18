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
package org.ednovo.gooru.application.server.service;


import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.service.SearchService;
import org.ednovo.gooru.application.server.annotation.ServiceURL;
import org.ednovo.gooru.application.server.deserializer.AutoCompleteDeSerializer;
import org.ednovo.gooru.application.server.deserializer.AutoSearchKeyWordDeSerializer;
import org.ednovo.gooru.application.server.deserializer.CollectionItemsResultDeSerializer;
import org.ednovo.gooru.application.server.deserializer.CollectionSearchResultDeSerializer;
import org.ednovo.gooru.application.server.deserializer.ResourceSearchResultDeSerializer;
import org.ednovo.gooru.application.server.deserializer.SearchFilterDeSerialier;
import org.ednovo.gooru.application.server.deserializer.ShareDeSerializer;
import org.ednovo.gooru.application.server.request.JsonResponseRepresentation;
import org.ednovo.gooru.application.server.request.ServiceProcessor;
import org.ednovo.gooru.application.server.request.ShareUrlToken;
import org.ednovo.gooru.application.server.request.UrlToken;
import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel1DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel2DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel3DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel4DO;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.search.AutoSuggestContributorSearchDo;
import org.ednovo.gooru.application.shared.model.search.AutoSuggestKeywordSearchDo;
import org.ednovo.gooru.application.shared.model.search.CollectionItemSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.CollectionSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.application.shared.model.search.SearchFilterDo;
import org.ednovo.gooru.application.shared.model.search.SearchResourcesTagsDo;
import org.ednovo.gooru.application.shared.model.skils.CenturySkilsDo;
import org.ednovo.gooru.shared.util.GooruConstants;
import org.ednovo.gooru.shared.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Search Team
 *
 */
@Service("searchService")
@ServiceURL("/searchService")
public class SearchServiceImpl extends BaseServiceImpl implements SearchService {

	/**
	 *
	 */
	private static Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

	private static final long serialVersionUID = 4286188874651640611L;

	private static final String RESOURCE = "resource";

	private static final String SCOLLECTION = "scollection";

	private static final String SINGLE = "single";

	private static final String REAL_URL = "realUrl";

	private static final String STANDARD = "standard";

	private static final String TRUE = "true";

	private static final String MY_STRING = "my";

	private static final String TYPE = "type";

	private static final String COURSEID = "c-id";

	private static final String SHARETYPE="shareType";

	private static final String COLLECTION_TYPE="collectionType";

	private static final String CLASSPAGE = "classpage";

	private static final String SHORTEN_URL = "shortenUrl";

	private static final String FULL_URL = "fullUrl";

	private static final String FLT_CODE_ID = "flt.codeId";

	private static final String COURSE_CODE_ID = "id";

	private static final String FLT_SOURCE_CODE_ID = "flt.sourceCodeId";

	private static final String COLLECTION_EDIT_EVENT ="collection-edit";

	private static final String HTTPS = "https";

	private static final String HTTP = "http";

	private static final String PARENT_ID = "parentId";

	private static final String LIBRARY_NAME = "libName";

	private static final String USER_ID = "userId";



	@Autowired
	private CollectionSearchResultDeSerializer collectionSearchResultDeSerializer;

	@Autowired
	private ResourceSearchResultDeSerializer resourceSearchResultDeSerializer;

	@Autowired
	private CollectionItemsResultDeSerializer collectionItemsResultDeSerializer;

	@Autowired
	private AutoCompleteDeSerializer autoCompleteDeSerializer;

	@Autowired
	private SearchFilterDeSerialier searchFilterDeSerialier;

	@Autowired
	private ShareDeSerializer shareDeSerializer;

	@Autowired
	private AutoSearchKeyWordDeSerializer autoSearchKeyWordDeSerializer;

	String  query;
	String collectionQuery;
	@Override
	public SearchFilterDo getSearchFilters(String type) {
		if (type.equalsIgnoreCase(PlaceTokens.SEARCH_RESOURCE)) {
			type = RESOURCE;
		} else {
			type = SCOLLECTION;
		}
		JsonRepresentation jsonRep=null;
		String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.SEARCH_FILTER);
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.TYPE,type);
		getLogger().info("SEARCH_FILTER url:::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		return searchFilterDeSerialier.deserializeSearchFilter(jsonRep, type);
	}

	@Override
	public SearchDo<ResourceSearchResultDo> getResourceSearchResults(SearchDo<ResourceSearchResultDo> searchDo) {
		SearchDo<ResourceSearchResultDo> searchDOEmpty = new SearchDo<ResourceSearchResultDo>();
		String query1=searchDo.getSearchQuery();

		 query= query1;
		try{
			if(searchDo.getFilters()!=null){
				for (String key : searchDo.getFilters().keySet()) {
				  String value = searchDo.getFilters().get(key);
				  value = value.replaceAll("&", "%26");
				  searchDo.getFilters().put(key, value);
				 }
			}
			if("'*'".equalsIgnoreCase(query))
			{
				query = "*";
			}

		JsonRepresentation jsonRep=null;
		Map<String,String> filtersMap=searchDo.getFilters();
		if(filtersMap!=null){
	        String category=filtersMap.get("category");
	        if(category!=null&&category.equalsIgnoreCase("All")){
	                filtersMap.remove("category");
	        }
	        else if(category!=null){
	        	if(category.equalsIgnoreCase("Website")){
	               	category=category.replaceAll("Website", "webpage");
	                filtersMap.remove("category");
	                filtersMap.put("flt.resourceFormat",category);
	        	}
	        	else {
	        		 filtersMap.remove("category");
	                 filtersMap.put("flt.resourceFormat",category);
	        	}
	        }
		}
		String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.V2_RESOURCE_SEARCH);
		filtersMap.put(GooruConstants.Q, query);
		filtersMap.put(GooruConstants.START,  String.valueOf(searchDo.getPageNum()));
		filtersMap.put(GooruConstants.LENGTH,  String.valueOf(searchDo.getPageSize()));
		filtersMap.put(GooruConstants.QUERY_TYPE,  SINGLE);
		filtersMap.put(GooruConstants.ALLOW_DUPLICATES,  "false");
		filtersMap.put(GooruConstants.FETCH_HITS_IN_MULTI,  TRUE);
		filtersMap.put(GooruConstants.ALLOW_SCRIPTING,  TRUE);
		filtersMap.put(GooruConstants.PROTOCOL_SUPPORTED, "http,https");
		String url = AddQueryParameter.constructQueryParams(partialUrl, filtersMap);

		getLogger().info("url search resource results:::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		try{
			resourceSearchResultDeSerializer.deserialize(jsonRep, searchDo,"");
		}
		catch(Exception e)
		{
			logger.error("Exception::", e);
		}
		return searchDo;
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return searchDOEmpty;
	}

	private String appendHttpsURL(String url) {
		url = AddQueryParameter.constructQueryParams(url, GooruConstants.PROTOCOL_SUPPORTED, "http,https");
//		url = url+"&protocolSupported=http,https";
		return url;
	}

	@Override
	public SearchDo<CollectionSearchResultDo> getCollectionSearchResults(SearchDo<CollectionSearchResultDo> searchDo) {
		SearchDo<CollectionSearchResultDo> searchDOEmpty = new SearchDo<CollectionSearchResultDo>();
		String query1=searchDo.getSearchQuery();
		collectionQuery= query1 ;
		try{
			if(searchDo.getFilters()!=null){
			for (String key : searchDo.getFilters().keySet()) {
				  String value = searchDo.getFilters().get(key);
				  value=value.replaceAll("&", "%26");
				  searchDo.getFilters().put(key, value);
			}
		 }
		JsonRepresentation jsonRep=null;
		if(collectionQuery.equalsIgnoreCase("'*'")){
			collectionQuery = "*";
		}
		String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.V2_SIMPLE_COLLECTION_SEARCH);
		Map<String,String> params = searchDo.getFilters();

		params.put(GooruConstants.ACCESS_TYPE, MY_STRING);
		params.put(GooruConstants.LENGTH, String.valueOf(searchDo.getPageSize()));
		params.put(GooruConstants.START, String.valueOf(searchDo.getPageNum()));
		params.put(GooruConstants.Q, collectionQuery);
		params.put(GooruConstants.INCLUDECIMETADATA, "true");
		params.put(GooruConstants.PRETTY, "1");
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);

		if(getHomeEndPoint().contains(HTTPS)){
			url = appendHttpsURL(url);
		}
		getLogger().info("CollectionSearchResults:"+url);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		collectionSearchResultDeSerializer.deserialize(jsonRep, searchDo,getProfileImageUrl());
		return searchDo;
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return searchDOEmpty;
	}


	@Override
	public SearchDo<CollectionItemSearchResultDo> getCollectionItems(String collectionId) throws GwtException {
		JsonRepresentation jsonRep=null;
		SearchDo<CollectionItemSearchResultDo> searchDo=new SearchDo<CollectionItemSearchResultDo>();
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_COLLECTION_ITEMS_LIST, collectionId);
		getLogger().info("getCollectionItems search::"+url);

		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		collectionItemsResultDeSerializer.deserializeV2CollectionItems(jsonRep, searchDo);
		return searchDo;
	}


	@Override
	public SearchDo<CollectionSearchResultDo> getResourceCollections(SearchDo<CollectionSearchResultDo> searchDo) {
		JsonRepresentation jsonRep=null;
		String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.V2_RESOURCE_COLLECTION_LIST);
		Map<String, String> params = new HashMap<String, String>();
		params.put(GooruConstants.Q, searchDo.getSearchQuery());
		params.put(GooruConstants.START, String.valueOf(searchDo.getPageNum()));
		params.put(GooruConstants.LENGTH, String.valueOf(searchDo.getPageSize()));
		params.put(GooruConstants.ACCESS_TYPE, "my");
		params.put(GooruConstants.CATEGORY, "all");
		params.put(GooruConstants.FILTER_RES_GOORU_OID,searchDo.getSearchQuery());
		params.put(GooruConstants.BOOSTFIELD_HASNO_THUMBNAIL, "0");
		params.put(GooruConstants.SHOW_CANONICAL_ONLY, "false");

		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		//getLogger().info("urlresourceinfotab getResourceCollectionsList search::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		collectionSearchResultDeSerializer.deserialize(jsonRep, searchDo,"");
		return searchDo;
	}

	@Override
	public SearchDo<String> getSuggestSource(SearchDo<String> searchDo) {
		JsonRepresentation jsonRep=null;
		String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.v2_SEARCH_SUGGEST_SOURCE);
		Map<String, String> params = new HashMap<String, String>();
		params.put(GooruConstants.Q, searchDo.getSearchQuery());
		params.put(GooruConstants.LENGTH, String.valueOf(searchDo.getPageSize()));
		params.put(GooruConstants.START, String.valueOf(searchDo.getPageNum()));
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);

		getLogger().info("SEARCH_SUGGEST_SOURCE getSuggestSource url:::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		searchDo.setSearchResults(autoCompleteDeSerializer.deserializeSource(jsonRep));
		return searchDo;
	}

	@Override
	public SearchDo<CodeDo> getSuggestStandard(SearchDo<CodeDo> searchDo) {
		JsonRepresentation jsonRep=null;
		String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.V2_SEARCH_SUGGEST_STANDARD, searchDo.getType() != null ? searchDo.getType() : STANDARD);
		Map<String, String> params = new HashMap<String, String>();
		params.put(GooruConstants.Q, searchDo.getSearchQuery());
		params.put(GooruConstants.LENGTH, String.valueOf(searchDo.getPageSize()));
		params.put(GooruConstants.START, String.valueOf(searchDo.getPageNum()));
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		if(searchDo.getFilters()!=null && searchDo.getFilters().size()>0) {
			url =  AddQueryParameter.constructQueryParams(url, FLT_CODE_ID, searchDo.getFilters().get(FLT_CODE_ID));
//			url = url + "&"+FLT_CODE_ID+"="+searchDo.getFilters().get(FLT_CODE_ID);
		}
		getLogger().info("SEARCH_SUGGEST_STANDARD get url:::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		searchDo.setSearchResults(autoCompleteDeSerializer.deserializeStandards(jsonRep));
		return searchDo;
	}

	@Override
	public SearchDo<CodeDo> getSuggestStandardByFilterCourseId(SearchDo<CodeDo> searchDo) {
		JsonRepresentation jsonRep=null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.SUGGEST_STANDARD_BY_FILTER);
		Map<String, String> params = new HashMap<String, String>();
		params.put(GooruConstants.QUERY, searchDo.getSearchQuery());
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		if(searchDo.getFilters()!=null && searchDo.getFilters().size()>0) {
			url = AddQueryParameter.constructQueryParams(url, COURSE_CODE_ID, searchDo.getFilters().get(COURSE_CODE_ID));
//			url = url + "&"+COURSE_CODE_ID+"="+searchDo.getFilters().get(COURSE_CODE_ID);
		}
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		searchDo.setSearchResults(autoCompleteDeSerializer.deserializeStandards(jsonRep));
		return searchDo;
	}

	@Override
	public Map<String, String> getShortenShareUrl(String contentGooruOid, Map<String, String> params) {
		JsonRepresentation jsonRep=null;
		Map<String, String> shortenUrl = new HashMap<String, String>();
		JSONObject fullUrlObject = new JSONObject();
        //This is used for to generate folder toc shorten url
		if (params.get(TYPE).equalsIgnoreCase(PlaceTokens.FOLDER_TOC)) {
			if(params.containsKey(LIBRARY_NAME)){
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/"+ ShareUrlToken.FOLDERTOC_URL_LIBRARY.getUrl(), contentGooruOid,params.get(LIBRARY_NAME)));
				if(params.containsKey(PARENT_ID)){
					params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/"+ ShareUrlToken.FOLDERTOC_URL_PARENT.getUrl(), contentGooruOid,params.get(LIBRARY_NAME),params.get(PARENT_ID)));
				}
			}else if(params.containsKey(USER_ID)){
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/"+ ShareUrlToken.FOLDERTOC_URL_PROFILE.getUrl(), contentGooruOid, params.get(USER_ID)));
			}else{
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/"+ ShareUrlToken.FOLDERTOC_URL.getUrl(), contentGooruOid));
			}
		}else if (params.get(TYPE).equalsIgnoreCase(PlaceTokens.SEARCH_RESOURCE)) {
			if (params.get(SHARETYPE).equalsIgnoreCase("embed")){
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/"+ ShareUrlToken.RESOURCE_PLAY_URL.getUrl()+"%26embed=true", contentGooruOid, RESOURCE));
			}else{
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/"+ ShareUrlToken.RESOURCE_PLAY_URL.getUrl()+"%26share=true", contentGooruOid, RESOURCE));
			}
		}else if(params.get(TYPE).equalsIgnoreCase(PlaceTokens.EDIT_CLASSPAGE)) {
			params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint()+"/" + ShareUrlToken.CLASSPAGE.getUrl(), contentGooruOid, CLASSPAGE));
		}else if(params.get(TYPE).equalsIgnoreCase(PlaceTokens.EDIT_CLASS)) {
			if(params.containsKey(COURSEID)){
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint()+"/" + ShareUrlToken.NEWCLASSPAGE.getUrl(), contentGooruOid,params.get(COURSEID),CLASSPAGE));
			}else{
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint()+"/" + ShareUrlToken.NEWCLASSPAGEID.getUrl(), contentGooruOid, CLASSPAGE));
			}

		}else {
			if (params.get(SHARETYPE).equalsIgnoreCase("embed")){
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint()+"/" + ShareUrlToken.COLLECTION_PLAY_EMBEDED_URL.getUrl(), contentGooruOid));
			}else if(params.get(COLLECTION_TYPE).equalsIgnoreCase("assessment")){
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/" + ShareUrlToken.ASSESSMENT_PLAY_URL.getUrl()+"%26share=true", contentGooruOid));
			}else{
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/" + ShareUrlToken.COLLECTION_PLAY_URL.getUrl()+"%26share=true", contentGooruOid));
			}
		}
		/*String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.SHARE_SHORTEN_URL, params, contentGooruOid);
		getLogger().info("getShortenShareUrl :::::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());*/
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_SHARE_SHORTEN_URL,contentGooruOid);
		try {
			fullUrlObject.put("fullUrl", URLDecoder.decode(params.get(REAL_URL)).toString());
		} catch (JSONException e1) {
			logger.error("Exception-----",e1);
		}
		getLogger().info("--getShortenShareUrl url--"+url);
		getLogger().info("--getShortenShareUrl payload--"+fullUrlObject.toString());
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestUsername(), fullUrlObject.toString());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		try{
		shortenUrl = shareDeSerializer.deserializeShortenUrl(jsonRep);
		}
		catch(Exception e)
		{
			logger.error("Exception----", e);
		}
		if(getHttpRequest().getScheme().equalsIgnoreCase(HTTPS)) {
			shortenUrl.put(SHORTEN_URL, shortenUrl.get(SHORTEN_URL).replaceAll(HTTP, HTTPS));
			//shortenUrl.put(RAWURL, shortenUrl.get(RAWURL).replaceAll(HTTP, HTTPS));
			//shortenUrl.put(DECODERAWURL, shortenUrl.get(DECODERAWURL).replaceAll(HTTP, HTTPS));
		}
		return shortenUrl;
	}

	@Override
	public Map<String, String> getShortenShareUrlforAssign(String contentGooruOid, Map<String, String> params,String classpageItemId) {
		JsonRepresentation jsonRep=null;
		Map<String, String> shortenUrl = new HashMap<String, String>();
		JSONObject fullUrlObject = new JSONObject();
			if (params.get(TYPE).equalsIgnoreCase(PlaceTokens.RESOURCE_SEARCH)) {
				if (params.get(SHARETYPE).equalsIgnoreCase("embed")){
					params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/"+ ShareUrlToken.RESOURCE_PLAY_URL.getUrl()+"%26embed=true", contentGooruOid, RESOURCE));
				}else{
					params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/"+ ShareUrlToken.RESOURCE_PLAY_URL.getUrl()+"%26share=true", contentGooruOid, RESOURCE));
				}
			}else if(params.get(TYPE).equalsIgnoreCase(PlaceTokens.EDIT_CLASSPAGE)){
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint()+"/" + ShareUrlToken.CLASSPAGE.getUrl(), contentGooruOid, CLASSPAGE));
			}
			else if(params.get(TYPE).equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY)){
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/" + ShareUrlToken.COLLECTION_PLAY_CLASSPAGE_URL.getUrl()+"%26page=study%26share=true", contentGooruOid,classpageItemId));
			}
			else if(params.get(TYPE).equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY)){
				params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/" + ShareUrlToken.ASSESSMENTS_PLAY_CLASSPAGE_URL.getUrl()+"%26page=study%26share=true", contentGooruOid,classpageItemId));
			}else {
				if (params.get(SHARETYPE).equalsIgnoreCase("embed")){
					//params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint()+"/" + ShareUrlToken.COLLECTION_PLAY_URL.getUrl()+"%26embed=true", contentGooruOid));
					params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint()+"/" + ShareUrlToken.COLLECTION_PLAY_EMBEDED_URL.getUrl(), contentGooruOid));
				}else{
					params.put(REAL_URL, UrlGenerator.generateUrl(getHomeEndPoint() +"/" + ShareUrlToken.COLLECTION_PLAY_URLAssign.getUrl()+"%26share=true", contentGooruOid));
				}
			}
		/*String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.SHARE_SHORTEN_URL, params, contentGooruOid);
		getLogger().info("SHARE_SHORTEN_URL getShortenShareUrl getShortenShareUrlforAssign:::::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());*/
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_SHARE_SHORTEN_URL,contentGooruOid);
		try {
			fullUrlObject.put("fullUrl", URLDecoder.decode(params.get(REAL_URL)).toString());
		} catch (JSONException e1) {
			logger.error("Exception----",e1);
		}
		getLogger().info("-----------getShortenShareUrlforAssign url--------------"+url);
		getLogger().info("----------getShortenShareUrlforAssign payload---------"+fullUrlObject.toString());
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestUsername(), fullUrlObject.toString());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		try{
			shortenUrl = shareDeSerializer.deserializeShortenUrl(jsonRep);
		}
		catch(Exception e)
		{
			logger.error("Exception----", e);
		}

		if(getHttpRequest().getScheme().equalsIgnoreCase(HTTPS)) {
			shortenUrl.put(SHORTEN_URL, shortenUrl.get(SHORTEN_URL).replaceAll(HTTP, HTTPS));
		}
		return shortenUrl;
	}

	@Override
	public String getGoogleSignin(String parms) {

		parms = parms.replaceAll("#", "%23");
		parms = parms.replaceAll("!", "%21");
		parms = parms.replaceAll("&", "%26");

		String gSigninUrl = getGoogleSignin() + "domain=gmail.com&callBackUrl=" + parms;
		return gSigninUrl;
	}

	@Override
	public String getGooruStoriesUrl(String parms) {

		return getStoriesUrl();
	}


	@Override
	public String getGoogleSignin(String placeToken, Map<String, String> parms) {

		String callback = StringUtil.generateMessage(getHomeEndPoint() + "#" +placeToken, parms);

		callback = callback.replaceAll("#", "%23");
		callback = callback.replaceAll("!", "%21");
		callback = callback.replaceAll("&", "%26");

		String gSigninUrl = getGoogleSignin() + "domain=gmail.com&callBackUrl=" + callback;
		return gSigninUrl;
	}

	@Override
	public String getGoogleDrive(String url, Map<String, String> parms) {

		String callback = StringUtil.generateMessage(url, parms);

		callback = callback.replaceAll("#", "%23");
		callback = callback.replaceAll("!", "%21");
		callback = callback.replaceAll("&", "%26");

		String gDriveUrl = getDriveGoogle() + "?emailId="+parms.get("emailId")+"&callBackUrl=" + callback;
		return gDriveUrl;
	}

	@Override
	public String getCollectionPlayDirectLink(String params){
		String directLink="";
		directLink = getHomeEndPoint() + params;
		return directLink;
	}

	@Override
	public String getHomeEndPointUrl(){
		return  getHomeEndPoint();
	}

	@Override
	public SearchDo<ResourceSearchResultDo> getSuggestSearchResultForResourceNoResult(SearchDo<ResourceSearchResultDo> searchDo){
		    JsonRepresentation jsonRep=null;
			SearchDo<ResourceSearchResultDo> searchDOEmpty = new SearchDo<ResourceSearchResultDo>();
			try{
			String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.SEARCH_SUGGEST_NO_RESULT);
			Map<String, String> params = searchDo.getFilters();
			params.put(GooruConstants.CONTEXT, query);
			String url = AddQueryParameter.constructQueryParams(partialUrl,params);
			getLogger().info("SEARCH_SUGGEST_NO_RESULT get url::::"+url);
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
			jsonRep=jsonResponseRep.getJsonRepresentation();
			resourceSearchResultDeSerializer.deserialize(jsonRep, searchDo,"");
			return searchDo;
			}catch(Exception e){
				logger.error("Exception::", e);
			}
			return searchDOEmpty;
		}

	@Override
	public SearchDo<AutoSuggestKeywordSearchDo> getSuggestedAutokeyword(
			SearchDo<AutoSuggestKeywordSearchDo> searchDo) throws GwtException {
		String pageSize="5";
		String pageNumber="1";
		JsonRepresentation jsonRep=null;
		String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.V2_SEARCH_AUTO_SUGGEST_KEYWORD);
		Map<String, String> params = new HashMap<String, String>();
		params.put(GooruConstants.Q, searchDo.getSearchQuery());
		params.put(GooruConstants.LENGTH, pageSize);
		params.put(GooruConstants.QUERY_TYPE, searchDo.getType());
		params.put(GooruConstants.START, pageNumber);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("SEARCH_AUTO_SUGGEST_KEYWORD getSuggestedAutokeyword url get::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		searchDo.setSearchResults(autoSearchKeyWordDeSerializer.deserializeAutoKeyword(jsonRep));
		return searchDo;

	}

	@Override
	public SearchDo<String> getSuggestedAggregator(SearchDo<String> searchDo)
			throws GwtException {
		JsonRepresentation jsonRep=null;
		String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.V2_SEARCH_SUGGEST_AGGREGATOR);
		Map<String, String> params = new HashMap<String, String>();
		params.put(GooruConstants.Q, searchDo.getSearchQuery());
		params.put(GooruConstants.LENGTH, String.valueOf(searchDo.getPageSize()));
		params.put(GooruConstants.START, String.valueOf(searchDo.getPageNum()));
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("SEARCH_SUGGEST_AGGREGATOR API call:::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		searchDo.setSearchResults(autoCompleteDeSerializer.deserializeAggregator(jsonRep));
		return searchDo;
	}
	@Override
	public ArrayList<AutoSuggestContributorSearchDo> getSuggestedContributor(String query,String contributorquery) throws GwtException ,ServerDownException{
		ArrayList<AutoSuggestContributorSearchDo> autoSuggestContributorSearchDo= new ArrayList<AutoSuggestContributorSearchDo>();
		JsonRepresentation jsonRep=null;
		try {
		String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.v2_SEARCH_SUGGEST_CONTRIBUTOR);
		Map<String, String> params = new HashMap<String, String>();
		params.put(GooruConstants.Q, query);
		params.put(GooruConstants.CONTRIBUTOR_QUERY, contributorquery);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("SEARCH_SUGGEST_SOURCE get contributor url:::::"+url);
		/*JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		autoSuggestContributorSearchDo=JsonDeserializer.deserialize(jsonRep.getJsonObject().getJSONArray("searchResults").toString(), new TypeReference<ArrayList<AutoSuggestContributorSearchDo>>() {});*/
		String value="[{%22users%22:[{%22profileImage%22:%22http://profile-demo.s3.amazonaws.com/profile-demof184a0a4-c728-4c61-8200-236348c60f59.png%22,%22name%22:%22chassssrmmlejmd0122%22},{%22profileImage%22:%22http://profile-demo.s3.amazonaws.com/profile-demof184a0a4-c728-4c61-8200-236348c60f59.png%22,%22name%22:%22chassssrmmlejmd0122%22},{%22profileImage%22:%22http://profile-demo.s3.amazonaws.com/profile-demof184a0a4-c728-4c61-8200-236348c60f59.png%22,%22name%22:%22chassssrmmlejmd0122%22}],%22organization%22:[{%22image%22:%22http://profile-demo.s3.amazonaws.com/profile-demof184a0a4-c728-4c61-8200-236348c60f59.png%22,%22name%22:%22chassssrmmlejmd0122%22},{%22image%22:%22http://profile-demo.s3.amazonaws.com/profile-demof184a0a4-c728-4c61-8200-236348c60f59.png%22,%22name%22:%22chassssrmmlejmd0122%22},{%22image%22:%22http://profile-demo.s3.amazonaws.com/profile-demof184a0a4-c728-4c61-8200-236348c60f59.png%22,%22name%22:%22chassssrmmlejmd0122%22}]}]";
		String value1= value.replaceAll("%22", "\"").toString();
		autoSuggestContributorSearchDo=(ArrayList<AutoSuggestContributorSearchDo>) JsonDeserializer.deserialize(value1.toString(), new TypeReference<List<AutoSuggestContributorSearchDo>>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return autoSuggestContributorSearchDo;
	}
	@Override
	public SearchDo<ResourceSearchResultDo> getCollectionSuggestedResourceSearchResults(
			SearchDo<ResourceSearchResultDo> searchDo, String contentGorruOid)
			throws GwtException {
		// TODO Auto-generated method stub

		SearchDo<ResourceSearchResultDo> searchDOEmpty = new SearchDo<ResourceSearchResultDo>();
		String query1=searchDo.getSearchQuery();
		 query= query1;
		try{
			if(searchDo.getFilters()!=null){
				for (String key : searchDo.getFilters().keySet()) {
				  String value = searchDo.getFilters().get(key);
				  value = value.replaceAll("&", "%26");
				  searchDo.getFilters().put(key, value);
				 }
			}

		JsonRepresentation jsonRep=null;
		Map<String,String> filtersMap=searchDo.getFilters();
		if(filtersMap!=null){
	        String category=filtersMap.get("category");
	        if(category!=null&&category.equalsIgnoreCase("All")){
	                filtersMap.remove("category");
	        }
	        else if(category!=null){
	        	if(category.equalsIgnoreCase("Website")){
	               	category=category.replaceAll("Website", "webpage");
	                filtersMap.remove("category");
	                filtersMap.put("flt.resourceFormat",category);
	        	}
	        	else {
	        		 filtersMap.remove("category");
	                 filtersMap.put("flt.resourceFormat",category);
	        	}
	        }
		}
		String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.SEARCH_SUGGEST_RESOURCES);
		try{
		filtersMap.put(GooruConstants.SEARCH_TERM, searchDo.getSearchQuery());
		filtersMap.put(GooruConstants.EVENT, COLLECTION_EDIT_EVENT);
		filtersMap.put(GooruConstants.CONTENT_GOORU_OID, contentGorruOid);
		}catch(Exception e){
			logger.error("Exception query::", e);
		}
		String url = AddQueryParameter.constructQueryParams(partialUrl, filtersMap);
		if(getSearchEndPoint().contains(HTTPS)){
			url = appendHttpsURL(url);
		}
		getLogger().info("search suggested resource url:::::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		try{
			resourceSearchResultDeSerializer.deserializeSuggestedResources(jsonRep, searchDo);
		}
		catch(Exception e)
		{
			logger.error("Exception::", e);
		}
		return searchDo;
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return searchDOEmpty;


	}

	@Override
	public ArrayList<StandardsLevel1DO> getFirstLevelStandards(String levelOrder, String standardLabel) {
		JsonRepresentation jsonRep=null;
		ArrayList<StandardsLevel1DO> standardLevelArry = new ArrayList<StandardsLevel1DO>();

		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_LevelWiseStandards,levelOrder,standardLabel);

		if(getSearchEndPoint().contains(HTTPS)){
			url = appendHttpsURL(url);
		}
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				standardLevelArry = JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<ArrayList<StandardsLevel1DO>>() {});
				return standardLevelArry;
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return standardLevelArry;
	}

	@Override
	public ArrayList<StandardsLevel2DO> getSecondLevelStandards(String levelOrder, String standardLabel) {
		JsonRepresentation jsonRep=null;
		ArrayList<StandardsLevel2DO> standardLevelArry = new ArrayList<StandardsLevel2DO>();

		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_LevelWiseStandards,levelOrder,standardLabel);

		if(getSearchEndPoint().contains(HTTPS)){
			url = appendHttpsURL(url);
		}
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {

				standardLevelArry = JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<ArrayList<StandardsLevel2DO>>() {});

				return standardLevelArry;
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return standardLevelArry;
	}

	@Override
	public ArrayList<StandardsLevel3DO> getThirdLevelStandards(String levelOrder, String standardLabel) {
		JsonRepresentation jsonRep=null;
		ArrayList<StandardsLevel3DO> standardLevelArry = new ArrayList<StandardsLevel3DO>();

		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_LevelWiseStandards,levelOrder,standardLabel);

		if(getSearchEndPoint().contains(HTTPS)){
			url = appendHttpsURL(url);
		}
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {

				standardLevelArry = JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<ArrayList<StandardsLevel3DO>>() {});

				return standardLevelArry;
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return standardLevelArry;
	}
	@Override
	public ArrayList<StandardsLevel4DO> getFourthLevelStandards(String levelOrder, String standardLabel) {
		JsonRepresentation jsonRep=null;
		ArrayList<StandardsLevel4DO> standardLevelArry = new ArrayList<StandardsLevel4DO>();

		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_LevelWiseStandards,levelOrder,standardLabel);

		if(getSearchEndPoint().contains(HTTPS)){
			url = appendHttpsURL(url);
		}
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {

				standardLevelArry = JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<ArrayList<StandardsLevel4DO>>() {});

				return standardLevelArry;
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return standardLevelArry;
	}


	@Override

	public SearchDo<CodeDo> getSuggestStandardByFilterCourseIdsource(SearchDo<CodeDo> searchDo) throws GwtException, ServerDownException {
		JsonRepresentation jsonRep=null;
		String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.v2_SUGGEST_STANDARD_BY_FILTER_SOURCE_CODEID);
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.Q, searchDo.getSearchQuery());

		if(searchDo.getFilters()!=null && searchDo.getFilters().size()>0) {
			url = AddQueryParameter.constructQueryParams(url, FLT_SOURCE_CODE_ID, searchDo.getFilters().get(FLT_SOURCE_CODE_ID));
//			url = url + "&"+FLT_SOURCE_CODE_ID+"="+searchDo.getFilters().get(FLT_SOURCE_CODE_ID);
		}
		getLogger().info("SUGGEST_STANDARD_BY_FILTER_Source_CodeId getSuggestStandardByFilterCourseIdsource:::::::"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		searchDo.setSearchResults(autoCompleteDeSerializer.deserializeStandards(jsonRep));
		return searchDo;
	}

	@Override
	public String showGooruStoriesSection() throws GwtException, ServerDownException {
		return showStoriesSection();
	}

	@Override
	public SearchResourcesTagsDo getResourceTags(String resourceId,	String offSet, String limit) throws GwtException,ServerDownException {

		JsonRepresentation jsonRep = null;
		SearchResourcesTagsDo searchResourcesTagsDo = new SearchResourcesTagsDo();
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.RESOURCE_TAGS, resourceId);
		Map<String, String> params = new HashMap<String, String>();
		params.put(GooruConstants.OFFSET, offSet);
		params.put(GooruConstants.LIMIT, limit);
		String url = AddQueryParameter.constructQueryParams(partialUrl, params);
		getLogger().info("-- resource based tags url -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();

		try{
			searchResourcesTagsDo = JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), SearchResourcesTagsDo.class);
		}
		catch(JSONException ex){
			logger.error("Exception::", ex);
		}
		return searchResourcesTagsDo;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.service.SearchService#getCenturySkilsRestuls()
	 */
	@Override
	public CenturySkilsDo getCenturySkilsRestuls() {
		JsonRepresentation jsonRep = null;
		CenturySkilsDo centurySkilsDo=new CenturySkilsDo();
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_21CENTURYSKILLS);
		getLogger().info("-- getCenturySkilsRestuls -- "+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		try{
			centurySkilsDo = JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), CenturySkilsDo.class);
		}catch(JSONException ex){
			logger.error("Exception::", ex);
		}
		return centurySkilsDo;
	}

	@Override
	public SearchDo<StandardFo> getSuggestCenturyByQuery(SearchDo<StandardFo> centuryDo) {
		JsonRepresentation jsonRep=null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.SUGGEST_CENTURY_BY_QUERY, getLoggedInSessionToken());
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.QUERY, centuryDo.getQuery());
		getLogger().info("century suggest API:"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
		jsonRep=jsonResponseRep.getJsonRepresentation();
		centuryDo.setSearchResults(autoCompleteDeSerializer.deserializeCenturys(jsonRep));
		return centuryDo;
	}


	@Override
	public String isClientSideLoggersEnabled() throws GwtException,	ServerDownException {
		String loggersStatus = getClientSideLoggersStatus();
		return loggersStatus;
	}

	@Override
	public SearchDo<CollectionSearchResultDo> getCollectionSearchResultsJson(SearchDo<CollectionSearchResultDo> searchDo) throws GwtException, ServerDownException {
		String query1=searchDo.getSearchQuery();
		collectionQuery= query1 ;
		try{
			if(searchDo.getFilters()!=null){
			/*for (String key : searchDo.getFilters().keySet()) {
					  String value = searchDo.getFilters().get(key);
					  value=value.replaceAll("&", "%26");
					  searchDo.getFilters().put(key, value);
				}*/
			 }
			JsonRepresentation jsonRep=null;
			if(collectionQuery!=null)
			{
			if(collectionQuery.equalsIgnoreCase("'*'")){
				collectionQuery = "*";
			}
			}
			String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.V2_SIMPLE_COLLECTION_SEARCH,getLoggedInSessionToken());
			Map<String,String> params = searchDo.getFilters();

			//params.put(GooruConstants.ACCESS_TYPE, MY_STRING);
			params.put(GooruConstants.LENGTH, String.valueOf(searchDo.getPageSize()));
			params.put(GooruConstants.START, String.valueOf(searchDo.getPageNum()));
			params.put(GooruConstants.Q, collectionQuery);
			params.put(GooruConstants.INCLUDECIMETADATA, "true");
			//params.put(GooruConstants.PRETTY, "1");
			String url = AddQueryParameter.constructQueryParams(partialUrl, params);

			if(getHomeEndPoint().contains(HTTPS)){
				url = appendHttpsURL(url);
			}
			getLogger().info("collection search url::::::"+url);
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
			jsonRep=jsonResponseRep.getJsonRepresentation();

			return descralizeCollectionSearchResults(jsonRep.getJsonObject().toString(),searchDo);
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return null;
	}

	@Override
	public SearchDo<CollectionSearchResultDo> descralizeCollectionSearchResults(String response,SearchDo<CollectionSearchResultDo> searchDo) throws GwtException, ServerDownException {
		SearchDo<CollectionSearchResultDo> searchDOEmpty = new SearchDo<CollectionSearchResultDo>();
		if(response!=null && !response.trim().isEmpty()){
			collectionSearchResultDeSerializer.deserializeJsonObject(response, searchDo,getProfileImageUrl());
			return searchDo;
		}
		return searchDOEmpty;
	}

	@Override
	public SearchDo<ResourceSearchResultDo> getResourceSearchResultsJson(SearchDo<ResourceSearchResultDo> searchDo) throws GwtException,ServerDownException {
			String query1=searchDo.getSearchQuery();
			 query= query1;
			 try{
					if(searchDo.getFilters()!=null){
						/*for (String key : searchDo.getFilters().keySet()) {
						  String value = searchDo.getFilters().get(key);
						  value = value.replaceAll("&", "%26");
						  searchDo.getFilters().put(key, value);
						 }*/
					}
					if("'*'".equalsIgnoreCase(query))
					{
						query = "*";
					}

				JsonRepresentation jsonRep=null;
				Map<String,String> filtersMap=searchDo.getFilters();
				if(filtersMap!=null){
			        String category=filtersMap.get("category");
			        if(category!=null&&category.equalsIgnoreCase("All")){
			                filtersMap.remove("category");
			        }
			        else if(category!=null){
			        	if(category.equalsIgnoreCase("Website")){
			               	category=category.replaceAll("Website", "webpage");
			                filtersMap.remove("category");
			                filtersMap.put("flt.resourceFormat",category);
			        	}
			        	else {
			        		 filtersMap.remove("category");
			                 filtersMap.put("flt.resourceFormat",category);
			        	}
			        }
				}
				String partialUrl = UrlGenerator.generateUrl(getSearchEndPoint(), UrlToken.V2_RESOURCE_SEARCH,getLoggedInSessionToken());
				filtersMap.put(GooruConstants.Q, query);
				filtersMap.put(GooruConstants.START,  String.valueOf(searchDo.getPageNum()));
				filtersMap.put(GooruConstants.LENGTH,  String.valueOf(searchDo.getPageSize()));
				filtersMap.put(GooruConstants.QUERY_TYPE,  SINGLE);
				filtersMap.put(GooruConstants.ALLOW_DUPLICATES,  "false");
				filtersMap.put(GooruConstants.FETCH_HITS_IN_MULTI,  TRUE);
				filtersMap.put(GooruConstants.ALLOW_SCRIPTING,  TRUE);
				filtersMap.put(GooruConstants.PROTOCOL_SUPPORTED, "http,https");
				String url = AddQueryParameter.constructQueryParams(partialUrl, filtersMap);

				getLogger().info("getResourceSearchResultsJson:::::"+url);
				JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getSearchUsername(), getSearchPassword());
				jsonRep=jsonResponseRep.getJsonRepresentation();
				if(jsonRep!=null && jsonRep.getJsonObject()!=null){
					return descralizeResourceSearchResults(jsonRep.getJsonObject().toString(),searchDo);
				}else{
					SearchDo<ResourceSearchResultDo> searchDOEmpty = new SearchDo<ResourceSearchResultDo>();
					return searchDOEmpty;
				}
			}catch(Exception e){
				logger.error("Exception::", e);
			}
			return null;
	}

	@Override
	public SearchDo<ResourceSearchResultDo> descralizeResourceSearchResults(String response, SearchDo<ResourceSearchResultDo> searchDo)	throws GwtException, ServerDownException {
		SearchDo<ResourceSearchResultDo> searchDOEmpty = new SearchDo<ResourceSearchResultDo>();
		try{
			if(response!=null && !response.trim().isEmpty()){
				resourceSearchResultDeSerializer.deserializeJsonObject(response, searchDo,"");
				return searchDo;
			}
		}catch(Exception e){
			logger.error("Exception::", e);
		}
		return searchDOEmpty;
	}
}
