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
package org.ednovo.gooru.client.mvp.profilepage;

import java.util.List;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.code.LibraryCodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.application.shared.model.user.UserFollowDo;
import org.ednovo.gooru.application.shared.model.user.UserTagsDo;
import org.ednovo.gooru.client.mvp.profilepage.data.ProfilePageLibraryView;
import org.ednovo.gooru.client.uc.ProfilePageDescriptionEditUc;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * @fileName : IsProfilePageView.java
 * 
 * @description :
 * 
 * 
 * @version : 1.0
 * 
 * @date: July 12, 2013
 * 
 * @Author Gooru Team
 * 
 * @Reviewer:
 */

public interface IsProfilePageView extends IsViewWithHandlers<ProfilePageUiHandlers> {

//	void setPublicPPData(CollectionItemDo collectionItemDo); 
	
	void setProfileData(ProfileDo profileDo);
	
	HTMLPanel getOnProfileContainer();
	
	HTMLPanel getOffProfileContainer();

	void setContentItemData(List<CollectionItemDo> collectionItemDo);
	
	void setCollectionData(CollectionItemDo collectionItemDo);
	
	void clearContentItemData();

	void setShareData(ProfileDo profileDo, List<String> shortenUrl, String profileUrl);

	void setMetaData(CollectionItemDo collectionItemDo);
	
	void showProfileView(boolean isVisible);
	
	void setContentTabVisibility(boolean isVisible);
	
	public void setUserProfileImage(String imageUrl);
	
	void enableEditableData(String profileOnStatus);

	ProfilePageDescriptionEditUc getProfileBiographyEditUC();

//	FocusPanel noAboutUsContainer();

//	Button getBiographyCancelButton();

//	Label getaboutUsCharacterValidation();
	
	/**
	 * Set collection default course
	 * @param libraryCode instance {@link LibraryCodeDo} as List
	 */
	void setCourseList(List<LibraryCodeDo> libraryCode);
	
	ProfileDo getProfileDo();

//	void onEditMyPageClickEvent();

	void disableChildData();
	
	void editOptions(boolean toEnable);

	Label getChilNoShareOption();

	void closeAllOpenedPopUp();
	
	ProfilePageLibraryView getContentView();
	
	public void getFollowersObj(List<UserFollowDo> userFollowDo);
	
	public void getFolloweingsObj(List<UserFollowDo> userFollowDo);
	
	public void getTagsObj(List<UserTagsDo> userTagsDo);
	
	void isFollow(String isFollow);

	/**
	 * @function getFollowButton 
	 * 
	 * @created_date : Jun 13, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @return
	 * 
	 * @return : Button
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	Button getFollowButton();

	
	/**
	 * @function getFollowingButton 
	 * 
	 * @created_date : Jun 13, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @return
	 * 
	 * @return : Button
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	Button getFollowingButton();
}
