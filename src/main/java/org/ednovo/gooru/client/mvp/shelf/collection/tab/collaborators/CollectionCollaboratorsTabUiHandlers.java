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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators;

import java.util.List;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;

/**
 * 
 * @fileName : CollectionCollaboratorsTabUiHandlers.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: Jan 23, 2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface CollectionCollaboratorsTabUiHandlers extends BaseUiHandlers {

	/**
	 * @function setData 
	 * 
	 * @created_date : Jan 24, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param collectionDo
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	void setData(CollectionDo collectionDo);
	
	/**
	 * 
	 * @function getCollaboratosListByCollectionId 
	 * 
	 * @created_date : Jan 27, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param CollectionId
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	void getCollaboratosListByCollectionId(String collectionId, String mode);
	
	/**
	 * 
	 * @function addCollaborators 
	 * 
	 * @created_date : Jan 27, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param collectionId
	 * @param emailIds
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	void addCollaborators(String collectionId, List<String> emailIds);
	
	
}
