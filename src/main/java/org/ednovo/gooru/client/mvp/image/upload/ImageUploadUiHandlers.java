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
package org.ednovo.gooru.client.mvp.image.upload;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author Search Team
 *
 */
public interface ImageUploadUiHandlers extends BaseUiHandlers, EventHandler {
	/**
	 * @param imageUrl
	 * <p>This is image url from web. 
	 * For Example url : <a>http://www.team-bhp.com/forum/attachments/shifting-gears/1033178d1356977973-official-non-auto-image-thread-_mg_0143.jpg</a>
	 * </p> 
	 */
	void imageWebUpload(String imageUrl);

	/**
	 * 
	 * @param fileName
	 * <p>This is filename of the  image which will get after image upload completes. </p>
	 * @param height
	 * <p>height of the  crop  selection</p>
	 * @param width
	 * <p>width of the  crop  selection</p>
	 * @param xPostion
	 * <p>x-coordinates  of the  crop  selection</p>
	 * @param yPostion
	 * <p>y-coordinates  of the  crop  selection</p>
	 */
	void cropImage(MediaUploadDo mediaUploadDo, String height, String width,  String xPostion, String yPostion);
	/**
	 * @param response 
	 * <p>
	 * 	Since the image upload are handle by form based action. By requesting the direct API call.
	 *  The response will be deserialize as java objects.   
	 * </p>
	 */
	void imageFileUpload(String response);
	
	public void uploadGooruDefaultImage(String imageURL);
	void saveImage(String gooruOid, String fileName, String resourceId);

	void saveImageCollection(String gooruOid, String fileName);
	
	void setUploadData(String filename);
}
