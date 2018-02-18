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

import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.ProfileCodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryListDo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwt-service/profilePageService")
public interface ProfilePageService extends BaseService {
	
		
	/**
	 * Get a folder information
	 * @return serialized created {@link CollectionDo}
	 * @throws GwtException
	 */
//	public CollectionDo getFolderInformation(String folderId) throws GwtException, ServerDownException;

	/**
	 * Get Folders of the second level and third level by User
	 * @return serialized created {@link List<CollectionDo>}
	 * @throws GwtException
	 */
	public List<CollectionItemDo> getFolders(String collectionId) throws GwtException, ServerDownException;
	
	/**
	 * Adds the user grades
	 * @return serialized created {@link String}
	 * @throws GwtException
	 */
	
	public void addGradeUserProfile(String grade, String userLevel) throws GwtException, ServerDownException;
	
	/**
	 * Delets the user grades
	 * @return serialized created {@link String}
	 * @throws GwtException
	 */
	
	public void deleteGradeUserProfile(String grade, String userLevel) throws GwtException, ServerDownException;
	
	/**
	 * Adds the user course
	 * @return serialized created {@link String}
	 * @throws GwtException
	 */
	
	public void addCourseUserProfile(Set<ProfileCodeDo> profileCodeDo,String userLevel) throws GwtException, ServerDownException;
	
	void deleteCourseUserProfile(CodeDo codeDo, String userLevel) throws GwtException, ServerDownException;
	
	/**
	 * @function getProfileLibraryWorkspace
	 * @return : ProfileLibraryListDo
	 * @description: Get the list of the workspace of the partners
	 * @parm(s) : @param offset
	 * @parm(s) : @param limit
	 * @parm(s) : @param sharingType
	 * @parm(s) : @param collectionType
	 * @throws : GwtException
	 */
	public ProfileLibraryListDo getProfileLibraryWorkspace(String gooruUid, int limit,String sharingType, String collectionType, String placeToken, int offset) throws GwtException, ServerDownException;
	
	/**
	 * Get paginated workspace API
	 * @return serialized created {@link ProfileLibraryListDo}
	 * @parm(s) : @param limit
	 * @parm(s) : @param parentId
	 * @parm(s) : @param sharingType
	 * @throws GwtException
	 */
	public ProfileLibraryListDo getProfilePaginationWorkspace(String parentId, String sharingType, int limit) throws GwtException, ServerDownException;
	
	/**
	 * @function getConcept 
	 * @return : ConceptDo
	 * @description: Get the Collection data for a concept
	 * @param: gooruOid
	 * @param: skipCollectionItems
	 * @throws : GwtException
	 */
	public ProfileLibraryDo getProfileLibraryCollection(String gooruOid, boolean skipCollectionItems) throws GwtException, ServerDownException;

}
