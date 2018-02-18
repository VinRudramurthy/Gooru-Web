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
package org.ednovo.gooru.client.mvp.classpages.tabitem.assignments;

import java.util.List;

import org.ednovo.gooru.application.client.child.IsChildView;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.ResourceDo;
import org.ednovo.gooru.client.mvp.dnd.IsDraggable;

/**
 * 
 * @fileName : IsAssignmentsTabView.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date:  Apr 17, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface IsAssignmentsTabView extends IsChildView<AssignmentsTabPresenter>, IsDraggable {
	
	/**
	 * 
	 * @function onPostUserCollections 
	 * 
	 * @created_date : 07-Dec-2014
	 * 
	 * @description
	 * Override method. Once collection is added this method is called and set the relevant data.
	 * 
	 * @parm(s) : @param result
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	void onPostUserCollections(List<CollectionDo> result);
	
	/**
	 * @description	: To insert newly created Assignment 
	 * @param : org.ednovo.gooru.shared.model.content.ResourceDo
	 */
	void insertCollectionToAssignment(ResourceDo resourceDo);
	
	/**
	 * to hide the loading animation.
	 */
	void hideLoading();
	
	/**
	 * Display Assignment Container panel
	 */
	void showPanel(boolean visible);
	
	/**
	 * To disable the "Add Collection to Assign" label
	 */
	void disableAddNewCollection();
	
	/**
	 * Delete the collection
	 */
	void onPostCollectionDelete();

	void closeAllOpenedPopUp();

}

