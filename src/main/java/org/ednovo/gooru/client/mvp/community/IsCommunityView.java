/**
 *
 */
package org.ednovo.gooru.client.mvp.community;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.client.mvp.home.library.contributors.LibraryContributorsView;

/**
 *
 * @fileName : IsCommunityView.java
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
public interface IsCommunityView extends IsViewWithHandlers<CommunityUiHandlers> {
	/**
	 * Load featured contributors {@link LibraryContributorsView}
	 * @param callBack
	 */
	void loadFeaturedContributors(String callBack, String placeToken);

	/**
	 * @function resetPassword
	 *
	 * @created_date : Jul 29, 2014
	 *
	 * @description
	 *
	 *
	 * @param resetToken
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	*/

	void resetPassword(String resetToken);

}
