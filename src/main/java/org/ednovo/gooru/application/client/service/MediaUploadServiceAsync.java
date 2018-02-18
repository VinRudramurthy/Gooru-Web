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
package org.ednovo.gooru.application.client.service;

import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Search Team
 * 
 */
public interface MediaUploadServiceAsync extends BaseServiceAsync {

	void imageWebUpload(String imageURL, AsyncCallback<MediaUploadDo> callback);

	void saveImage(String gooruOid, String resourceId, String fileName, AsyncCallback<CollectionItemDo> callback);
	
	void saveImageCollection(String courseId, String unitId, String lessonId, String collectionId, CreateDo createDo, String fileName, AsyncCallback<String> callback);
	
	void cropImage(String fileName, String height, String width, String xPostion, String yPostion,String imageUrl, AsyncCallback<String> callback);
	
	void imageFileUpload(String response, AsyncCallback<MediaUploadDo> callback);
	
	void saveQuestionImage(String collectionItemId, String fileName, AsyncCallback<CollectionItemDo> callback);
//	Below service method not used.
//	void saveResourceImage(String gooruOid, String fileName, AsyncCallback<String> callback);
	
	void uploadProfileImage(String fileNameWithOutRepository,String fileName,AsyncCallback<String> callback);
	
}
