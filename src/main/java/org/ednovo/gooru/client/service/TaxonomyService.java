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
package org.ednovo.gooru.client.service;

import java.util.List;

import org.ednovo.gooru.shared.exception.GwtException;
import org.ednovo.gooru.shared.model.code.LibraryCodeDo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwt-service/taxonomyService")
public interface TaxonomyService extends BaseService {

	/**
	 * Get taxonomy values as library values
	 * @param depthLimit to get taxonomy values
	 * @param parentCodeId of taxonomy
	 * @return serialized {@link LibraryCodeDo}
	 * @throws GwtException
	 */
//	LibraryCodeDo getTaxonomyTree(int depthLimit, String parentCodeId) throws GwtException;

	/**
	 * Get taxonomy course
	 * @return List of course value 
	 * @throws GwtException
	 */
	List<LibraryCodeDo> getCourse() throws GwtException;
}
