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
package org.ednovo.gooru.client.mvp.profilepage.data;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.child.IsChildView;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;

/**
 * @author SearchTeam
 * 
 */
public interface IsProfilePageLibraryView extends IsChildView<ProfilePageLibraryPresenter> {
	/**
	 * Load Partners page {@link loadPartnersPage}
	 * @param callBack
	 * @param placeToken
	 */
	void loadPartnersPage(String callBack, String placeToken);
	
	/**
	 * Set Partners Unit Data {@link setUnitList}
	 * @param folderList
	 */
	public void setUnitList(ProfileLibraryListDo result);

	public void setTopicListData(ArrayList<ProfileLibraryDo> searchResult, String folderId);
	
	public void setTopicListData(ProfileLibraryDo profileLibraryDo, String folderId);

	public void loadingPanel(boolean isVisible);
	
	public void clearPanels();
	
	public void setEmptyContainer(boolean isEmpty);

}