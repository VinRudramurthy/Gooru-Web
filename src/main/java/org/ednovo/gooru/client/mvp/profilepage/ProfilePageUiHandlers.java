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

import java.util.Set;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.ProfileCodeDo;
import org.ednovo.gooru.application.shared.model.user.UserFollowDo;
import org.ednovo.gooru.client.mvp.profilepage.event.RequestCollectionOpenHandler;
import org.ednovo.gooru.client.mvp.profilepage.event.RequestFolderOpenHandler;
import org.ednovo.gooru.client.mvp.profilepage.event.SetUserPublicProfileImageEventHandler;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

/**
 * @fileName : ProfilePageUiHandlers.java
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

public interface ProfilePageUiHandlers extends BaseUiHandlers, RequestFolderOpenHandler, RequestCollectionOpenHandler,SetUserPublicProfileImageEventHandler {
	
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_PUBLIC_SHELF_VIEW = new Type<RevealContentHandler<?>>();
	
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_FOLLOWING_VIEW = new Type<RevealContentHandler<?>>();
	
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_FOLLWER_VIEW = new Type<RevealContentHandler<?>>();
	
	void setShareView();
	
	void showUploadImageWidget();

	void updateProfileVisibilitySetting(String string);

	void updateUserBiography(String gooruUid, String bioText, String userType, String firstName,
			String lastName, String gender);
	
	void addCourse(Set<ProfileCodeDo> profileCodeDo);
	
	void deleteCourse(CodeDo codeDo);
	
	void getTaxonomyData();
	
	void revealTab(Type<RevealContentHandler<?>> tabType);

	void clearTabSlot();
	
	UserFollowDo getFollwingData();
	
	UserFollowDo getFollowerData();
	
	void followUser(String gooruUid);
	
	void unFollowUser(String gooruUid);
	
	void getUserAddedContentTagSummary(String tagGooruUid,String offset,String limit);
	
	
	
}
