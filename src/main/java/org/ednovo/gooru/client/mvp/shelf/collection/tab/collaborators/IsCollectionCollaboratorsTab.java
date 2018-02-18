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

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;

/**
 * @author Search Team
 * 
 */
public interface IsCollectionCollaboratorsTab extends
		IsViewWithHandlers<CollectionCollaboratorsTabUiHandlers> {

	/**
	 * @function displayView
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
	void displayView(CollectionDo collectionDo);

	/**
	 * 
	 * @function displayCollaboratorsByList
	 * 
	 * @created_date : Jan 27, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param result
	 * 
	 * @return : void
	 * 
	 * @throws : <Mentioned if any exceptions>
	 * 
	 * 
	 * 
	 * 
	 */
	void displayViewCollaboratorsByList(List<CollaboratorsDo> result);

	/**
	 * @function displayActiveCollaboratorsByList 
	 * 
	 * @created_date : Jan 27, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param lstCollaborators
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	void displayActiveCollaboratorsByList(List<CollaboratorsDo> lstCollaborators);

	/**
	 * @function displayPendingCollaboratorsByList 
	 * 
	 * @created_date : Jan 27, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param lstCollaborators
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	void displayPendingCollaboratorsByList(List<CollaboratorsDo> lstCollaborators);
	
	/**
	 * 
	 * @function displayCollaborators 
	 * 
	 * @created_date : Jan 27, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param collabList
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	void displayCollaborators(List<CollaboratorsDo> collabList);
	
	/**
	 * 
	 * @function setLoadingVisibility 
	 * 
	 * @created_date : Jan 28, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param visibility
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	void setLoadingVisibility(boolean visibility);

	/**
	 * 
	 * @function setRemoveCollabButtonVisibility 
	 * 
	 * @created_date : Jan 28, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param visibility
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	void setRemoveCollabButtonVisibility(boolean visibility);
	/**
	 * 
	 * @function clearContainers 
	 * 
	 * @created_date : Feb 6, 2014
	 * 
	 * @description
	 * 
	 * 
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	void clearContainers();
	
	/**
	 * 
	 * @function setInviteButtonEnable 
	 * 
	 * @created_date : Mar 4, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param currentCount
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	void setInviteButtonEnable(int currentCount);
}
