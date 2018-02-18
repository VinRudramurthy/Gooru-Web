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
package org.ednovo.gooru.application.shared.model.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Search Team
 * 
 */
public class AbstractSearchDo<T> implements IsSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8405723445226672485L;

	private static int DEFAULT_PAGE_NUM = 1;

	private static int DEFAULT_PAGE_SIZE = 8;

	private long version = 0;

	private String query;
	
	private String spellCheckQueryString;
	
	private String userQueryString;
	
	private Integer collectionItemsCount;

	private String type;

	private int pageNum = DEFAULT_PAGE_NUM;

	private int pageSize = DEFAULT_PAGE_SIZE;

	private int searchHits;

	private T suggestResults;
	
	private Map<String, String> filters;

	private static char[] ALLOWED_SPECIAL_CHARS = { ',', '-', ' ', '_', '@', ':', '$','^','~','`','<','>','.', '\''};
	
	private static char[] INVALID_URL_CHARS = { '/', ';', '\\', '&', '#','%','*'};
	
	private String notFriendly;
	
	public static String STANDARD_CODE = "standardCode";

	private static String ALL = "*";
	
	private T searchResults;

	public AbstractSearchDo() {

	}

	public T getSearchResults() {
		return searchResults;
	}
	
	public T getSuggestResults() {
		return suggestResults;
	}

	public void setSuggestResults(T suggestResults) {
		this.suggestResults = suggestResults;
	}


	public void setSearchResults(T searchResults) {
		this.searchResults = searchResults;
	}

	public String getSearchQuery() {
		return validateQuery(query);
	}

	public String getUrlQuery() {
		return validateQueryForUrl(query);
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	private static String validateQueryForUrl(String query) {
		if (query != null) {
			query = query.trim();
			for (char invalidChar : INVALID_URL_CHARS) {
				//query = query.replace(invalidChar + "", "");
			}
			query = query.trim();
		}
		return query;
	}

	private static String validateQuery(String query) {
		if (query != null) {
			query = query.trim();
			List<Character> invalidChars = new ArrayList<Character>();
				for (char character : query.toCharArray()) {
					if (!Character.isLetterOrDigit(character)) {
						boolean invalid = true;
						for (char allowedChar : ALLOWED_SPECIAL_CHARS) {
							if (allowedChar == character) {
								invalid = false;						
								break;
							}
						}
						if (invalid) {
							invalidChars.add(character);
						}
					}
				}
				
				query = query.trim();
				if(query.length()==invalidChars.size()){
					query= '\'' + query + '\'';
				}else{
					for (char oldChar : invalidChars) {
						query = query.replace(oldChar + "", " ");
					}
				}
			query = query.trim();		
		}
		return query;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		if (pageNum == null) {
			pageNum = DEFAULT_PAGE_NUM;
		}
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize == null) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public int getSearchHits() {
		return searchHits;
	}

	public void setSearchHits(int searchHits) {
		this.searchHits = searchHits;
	}

	public Map<String, String> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}

	public int getTotalPages() {
		return (searchHits / pageSize) + ((searchHits % pageSize) > 0 ? 1 : 0);
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}

	public Integer getCollectionItemsCount() {
		return collectionItemsCount;
	}

	public void setCollectionItemsCount(Integer collectionItemsCount) {
		this.collectionItemsCount = collectionItemsCount;
	}

	/** 
	 * This method is to get the notFriendly
	 */
	public String getNotFriendly() {
		return notFriendly;
	}

	/** 
	 * This method is to set the notFriendly
	 */
	public void setNotFriendly(String notFriendly) {
		this.notFriendly = notFriendly;
	}

	public String getSpellCheckQueryString() {
		return spellCheckQueryString;
	}

	public void setSpellCheckQueryString(String spellCheckQueryString) {
		this.spellCheckQueryString = spellCheckQueryString;
	}

	public String getUserQueryString() {
		return userQueryString;
	}

	public void setUserQueryString(String userQueryString) {
		this.userQueryString = userQueryString;
	}
	

}
