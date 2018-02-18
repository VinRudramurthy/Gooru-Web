/**
 * 
 */
package org.ednovo.gooru.client.mvp.library.district;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;
import org.ednovo.gooru.client.mvp.home.library.contributors.LibraryContributorsView;

/**
 * @author Search Team
 * 
 */
public interface IsDistrictView extends IsViewWithHandlers<DistrictUiHandlers> {
	/**
	 * Load featured contributors {@link LibraryContributorsView}
	 * @param callBack
	 */
	void loadFeaturedContributors(String callBack, String placeToken,ProfileLibraryListDo profileLibraryListDo);

}
