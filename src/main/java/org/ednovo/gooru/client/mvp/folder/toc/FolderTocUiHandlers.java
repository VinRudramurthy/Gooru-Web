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

package org.ednovo.gooru.client.mvp.folder.toc;

import java.util.Map;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;

import com.google.gwt.user.client.ui.TreeItem;
/**
 * @fileName : FolderTocUiHandlers.java
 *
 * @description : 
 *
 *
 * @version : 1.3
 *
 * @date: 06-02-2015
 *
 * @Author Gooru Team
 *
 * @Reviewer: 
 */
public interface FolderTocUiHandlers extends BaseUiHandlers{
	/**
	 * This method is used to get the folders and collections based on parent folder Id.
	 * @param item
	 * @param parentId
	 */
	void getFolderItems(TreeItem item, String parentId);
	/**
	 * This method is used to get the shorten url for the TOC view
	 * @param folderId
	 * @param params
	 */
	void getShortenUrl(String folderId,Map<String, String> params);
	/**
	 * To get the User profile details 
	 * @param profId {@link String}
	 */
	void getProfilePageDetails(String profId);
	/**
	 * To get the Folder Toc
	 * @param folderId {@link String}
	 * @param fromPPP {@link boolean}
	 */
	void getTocFolders(String folderId);
	/**
	 * This method used to update the folder toc page.
	 * @param folderId {@link String}
	 */
	void getfolderTocList(String folderId);
	/**
	 * This method used to get the Banner images of folder
	 * @param parentId {@link String}
	 */
	void getFolderMetaData(String parentId);
}
