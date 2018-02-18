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
package org.ednovo.gooru.client.mvp.search.CenturySkills;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.content.StandardFo;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;

/**
 * @author Search Team
 *
 */
public interface AddCenturyUiHandlers extends BaseUiHandlers {

	void hidePopup();
	/**
	 * This method is used to load 21 century skils data.
	 */
	void loadStateStandards();
	/**
	 * This will return the add button
	 * @return
	 */
	Button getAddButton();
	/**
	 * This will return the selected vlaues
	 * @return
	 */
	Map<Long, String> getSelectedValues();
	
	/**
	 * This method will return the cancel button
	 * @return
	 */
	Button getCancelBtn();
	/**
	 * This method will return the close button
	 * @return
	 */
	Anchor getCloseBtn();
	/**
	 * This method will set the data in edit popup
	 * @param codeList
	 */
	void setEditResourceData(List<StandardFo> codeList);
	/**
	 * This method will reset the data in the add resource popups
	 * @param codeList
	 */
	void setAddResourceData(Map<Long,String> codeList);
	void setAddResourceDataAddTags(ArrayList<String> centuryDo);
	/**
	 * This method will set the collectionId, If we are coming from collection into tab in my collections
	 */
	void setCollectionIdFromCollectionInfo(String collectionId,Map<Long, String> collectionInfoSelectedValues);
	/**
	 * This method is used to delete century value for collection.
	 * @param collectionId
	 * @param courseCode
	 */
	void deleteCourseOrStandard(String collectionId, String courseCode);
}
