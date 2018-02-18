/**
 * 
 */
package org.ednovo.gooru.client.mvp.community;

import java.util.Map;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.search.AutoSuggestKeywordSearchDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.application.shared.model.user.UserDo;

/**
 * 
 * @fileName : CommunityUiHandlers.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 07-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface CommunityUiHandlers extends BaseUiHandlers {

	/**
	 * @function homeSearch 
	 * 
	 * @created_date : Jul 29, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param hm
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	void homeSearch(Map<String, String> hm);

	/**
	 * @function initilazeRegistrationView 
	 * 
	 * @created_date : Jul 29, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param user
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	void initilazeRegistrationView(UserDo user);

	/**
	 * @function requestStandardsSuggestion 
	 * 
	 * @created_date : Jul 29, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param searchDo
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	void requestStandardsSuggestion(SearchDo<CodeDo> searchDo);

	/**
	 * @function requestAutoSuggestKeyword 
	 * 
	 * @created_date : Jul 29, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param searchDo
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	void requestAutoSuggestKeyword(SearchDo<AutoSuggestKeywordSearchDo> searchDo);
	
}
