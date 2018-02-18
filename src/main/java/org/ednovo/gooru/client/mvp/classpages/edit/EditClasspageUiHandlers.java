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
package org.ednovo.gooru.client.mvp.classpages.edit;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.client.mvp.classpages.event.RefreshAssignmentsListEventHandler;
import org.ednovo.gooru.client.mvp.classpages.event.UpdateClasspageImageEventHandler;
import org.ednovo.gooru.client.mvp.shelf.event.AssignmentHandler;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;


/**
 * @fileName : ClasspageUiHandlers.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: Apr 17, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface EditClasspageUiHandlers extends BaseUiHandlers, AssignmentHandler,RefreshAssignmentsListEventHandler,UpdateClasspageImageEventHandler {

	void getClasspageById(String classpageId);

	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SHELF_TAB = new Type<RevealContentHandler<?>>();
	/**
	 *
	 * @function addAssignmentsContainerPopup
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param classpageId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void addAssignmentsContainerPopup(String classpageId);
	/**
	 *
	 * @function getAssignmentsByClasspageById
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param classpageId
	 * @parm(s) : @param pageSize
	 * @parm(s) : @param pageNum
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void getAssignmentsByClasspageById(String classpageId, String pageSize,String pageNum);
	/**
	 *
	 * @function generateShareLink
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param classpageId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void generateShareLink(String classpageId);
	/**
	 *
	 * @function updateClassPageInfo
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param classPageId
	 * @parm(s) : @param collectionType
	 * @parm(s) : @param title
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void updateClassPageInfo(String classPageId,String collectionType, String title);
	/**
	 *
	 * @function showImageUploadWidget
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void showImageUploadWidget();
	/**
	 *
	 * @function getNextClasspageItems
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param offset
	 * @parm(s) : @param limit
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void getNextClasspageItems(Integer offset,Integer limit);

	/**
	 * @function getAssignmentsProgress
	 *
	 * @created_date : Jun 11, 2014
	 *
	 * @description
	 *
	 *
	 * @param classpageId
	 * @param action
	 * @param string
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	*/

	void getAssignmentsProgress(String classpageId, String offSet, String limit);

	void setCollectionProgressData(String clickedTab,String collectionId,String collectionTitle);

}
