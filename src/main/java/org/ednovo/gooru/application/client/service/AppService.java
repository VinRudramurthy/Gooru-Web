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
package org.ednovo.gooru.application.client.service;

import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.user.UserDo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Search Team
 * 
 */
@RemoteServiceRelativePath("gwt-service/appService")
public interface AppService extends BaseService {

	/**
	 * Get LoggedIn user details
	 * @return serialized to {@link UserDo} has loggedIn user details
	 * @throws GwtException
	 */
	UserDo getLoggedInUser() throws GwtException, ServerDownException;

	
	/**
	 * signout from the account
	 * @return serialized anonymous {@link UserDo}
	 * @throws GwtException
	 */
	UserDo signout() throws GwtException, ServerDownException;
	
	UserDo v2Signin(String userName, String password) throws GwtException, ServerDownException;

	UserDo v2Signout() throws GwtException, ServerDownException;

	/**
	 * @function getAnalyticsURL 
	 * 
	 * @created_date : Jan 11, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param parms
	 * @return
	 * 
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	String getAnalyticsURL(String type, String id) throws GwtException, ServerDownException;
	
	public UserDo getUserFilterProperties() throws GwtException, ServerDownException;
}
