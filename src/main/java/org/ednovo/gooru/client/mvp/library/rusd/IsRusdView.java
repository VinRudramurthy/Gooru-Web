/**
 * 
 */
package org.ednovo.gooru.client.mvp.library.rusd;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.client.mvp.home.library.contributors.LibraryContributorsView;

/**
 * @author Search Team
 * 
 */
public interface IsRusdView extends IsViewWithHandlers<RusdUiHandlers> {
	/**
	 * Load featured contributors {@link LibraryContributorsView}
	 * @param callBack
	 */
	void loadFeaturedContributors(String callBack, String placeToken);

}
