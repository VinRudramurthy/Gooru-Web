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
package org.ednovo.gooru.client.mvp.gsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.search.AutoSuggestContributorSearchDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.application.shared.model.search.SearchFilterDo;

import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * @author Search Team
 *
 */
public interface IsGooruSearchView<T extends ResourceSearchResultDo> extends
		IsViewWithHandlers<GooruSearchUiHandlers> {

	String CATEGORY_FLT = "category";

	String SUBJECT_FLT = "flt.subjectName";

	String STANDARD_FLT = "flt.standard";

	String GRADE_FLT = "flt.grade";

	/*String SOURCE_FLT = "flt.source";*/

	String PUBLISHER_FLT = "flt.publisher";

	String OWNER_FLT = "flt.owner";

	String MEDIATYPE_FLT = "fltNot.mediaType";

	String OER_FLT = "flt.isOer";

	String AGGREGATOR_FLT = "flt.aggregator";
	
	String CONTRIBUTOR_FLT = "flt.contributor";
	
	String CONTRIBUTOR_FLT_TYPE = "flt.contributorType";
	
	String ACCESS_MODE_FLT = "flt.cfAccessMode";

	String RATINGS_FLT = "flt.rating";

	String REVIEWS_FLT = "flt.isReviewed";

	String COLLECTIONTYPE_FLT = "flt.collectionType";

	/**
	 * Set post search view
	 * @param searchDo instance of {@link SearchDo}
	 */
	void postSearch(SearchDo<T> searchDo,boolean isApiCalled);

	/**
	 * This method will set the search Filters
	 * @param searchFilterDo
	 */
	void setSearchFilter(SearchFilterDo searchFilterDo);

	/**
	 * Get filters for search
	 * @param viewToken current palce token
	 * @return search filters
	 */
	Map<String,String> getSearchFilters(String viewToken);
	/**
	 * This method will reset the all the values
	 */
	void resetData();
    /**
     * @return gradePanel
     */
	HTMLPanel getGradePanel();

	void setUpdatedStandards(List<Map<String, String>> list);

	void setSourceSuggestions(SearchDo<String> result);

	void setAggregatorSuggestions(SearchDo<String> result);
	
	/*void setContributorSuggestions(ArrayList<AutoSuggestContributorSearchDo> result);*/

	void setCollectionContributorSuggestions(ArrayList<AutoSuggestContributorSearchDo> result);

	Map<String, String> getStandardsSelectedFilters(String viewToken);

	void noStarSearchResult();
}
