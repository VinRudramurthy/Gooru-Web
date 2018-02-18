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

import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.featured.FeaturedCollectionContentDo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwt-service/homeService")
public interface HomeService extends BaseService {

	/**
	 * Get list of featured collection 
	 * @return serialized to List of  {@link FeaturedCollectionContentDo}
	 * @throws GwtException
	 */
//	List<FeaturedCollectionContentDo> getFeaturedCollections() throws GwtException;
	void updateUserDetails(String userName,String userRole) throws GwtException, ServerDownException;
		
	/**
	 * Get list of collection
	 * @param collectionIds
	 * @return List of serialized {@link CollectionDo}
	 * @throws GwtException
	 */
//	public List<CollectionDo> getCollectionList(List<String> collectionIds) throws GwtException;
	
	/**
	 * Get list of Teacher's picks collections (Math, Science, Social, language and featured)
	 * @param themeType
	 * @return serialized to List of  {@link FeaturedCollectionContentDo}
	 * @throws GwtException
	 */
	List<FeaturedCollectionContentDo> getFeaturedThemeCollection(String themeType) throws GwtException, ServerDownException;
	
	String whatsNewMosLink() throws GwtException, ServerDownException;
	String whatsNewFibLink() throws GwtException, ServerDownException;
	
	String mosLink() throws GwtException, ServerDownException;
	/**
	 * 
	 * @function getClientIpAddress 
	 * 
	 * @created_date : Mar 5, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @return
	 * @throws GwtException
	 * 
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	String getClientIpAddress() throws GwtException, ServerDownException;
	
	String getRedirectServerUrl() throws GwtException, ServerDownException;

}
