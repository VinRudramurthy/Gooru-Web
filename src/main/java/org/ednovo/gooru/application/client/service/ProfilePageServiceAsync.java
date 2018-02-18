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
package org.ednovo.gooru.application.client.service;

import java.util.List;
import java.util.Set;

import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.ProfileCodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProfilePageServiceAsync extends BaseServiceAsync {

	void getFolders(String collectionId, AsyncCallback<List<CollectionItemDo>> callback);

//	void getFolderInformation(String folderId, AsyncCallback<CollectionDo> callback);
	
	void addGradeUserProfile(String grade, String userLevel,
			AsyncCallback<Void> callback);

	void deleteGradeUserProfile(String grade, String userLevel,
			AsyncCallback<Void> callback);

	void addCourseUserProfile(Set<ProfileCodeDo> profileCodeDo,
			String userLevel, AsyncCallback<Void> callback);

	void deleteCourseUserProfile(CodeDo codeDo, String userLevel,
			AsyncCallback<Void> callback);

	void getProfileLibraryWorkspace(String gooruUid, int limit,String sharingType, String collectionType, String placeToken, int offset, AsyncCallback<ProfileLibraryListDo> callback);

	void getProfilePaginationWorkspace(String parentId,String sharingType, int limit, AsyncCallback<ProfileLibraryListDo> callback);
	
	void getProfileLibraryCollection(String gooruOid, boolean skipCollectionItems, AsyncCallback<ProfileLibraryDo> callback);

	
}