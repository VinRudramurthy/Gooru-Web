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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive;

import java.util.List;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.drive.GoogleDriveItemDo;

public interface DriveUiHandlers extends BaseUiHandlers{

	public void getGoogleDriveFiles(String folderId,String nextPageToken,boolean isPanelClear);


	/**
	 * @function addResource 
	 * 
	 * @created_date : Jul 3, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param idStr
	 * @param urlStr
	 * @param titleStr
	 * @param descriptionStr
	 * @param webResourceCategory
	 * @param thumbnailUrlStr
	 * @param endTime
	 * @param educationalUse
	 * @param momentsOfLearning
	 * @param standards
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	public void addResource(String idStr, String urlStr, String titleStr,
			String descriptionStr, String webResourceCategory,
			String thumbnailUrlStr, Integer endTime, String educationalUse,
			String momentsOfLearning, List<CodeDo> standards);

	/**
	 * @function isShortenUrl 
	 * 
	 * @created_date : Jul 3, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param userUrlStr
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	public void isShortenUrl(String userUrlStr);
	
	public void showAddResourceWidget(GoogleDriveItemDo googleDriveItemDo);
}
