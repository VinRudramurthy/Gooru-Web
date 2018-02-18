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
package org.ednovo.gooru.application.client.service;

import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Search Team
 * 
 */
@RemoteServiceRelativePath("gwt-service/mediaUploadService")
public interface MediaUploadService extends BaseService {
	
	/**
	 * Upload image from web
	 * @param imageURL from web 
	 * @return serialized to {@link MediaUploadDo} after upload
	 * @throws GwtException
	 */
	MediaUploadDo imageWebUpload(String imageURL) throws GwtException, ServerDownException;


	/**
	 * Crop uploaded image 
	 * @param fileName image file name or url
	 * @param height cropped image height
	 * @param width  cropped image width
	 * @param xPostion cropped image x-position
	 * @param yPosition cropped image y-position
	 * @return file name of the cropped image
	 * @throws GwtException
	 */
	String cropImage(String fileName, String height, String width, String xPostion, String yPosition,String imageUrl) throws GwtException, ServerDownException;
	
	/**
	 * Upload image from local file
	 * @param response in json to serialize as {@link MediaUploadDo}  
	 * @return serialized to {@link MediaUploadDo} after upload
	 * @throws GwtException
	 */
	MediaUploadDo imageFileUpload(String response) throws GwtException, ServerDownException;
	
	
	CollectionItemDo saveQuestionImage(String colletionItemId, String fileName)  throws GwtException, ServerDownException;
	

	/**
	 * save image to server
	 * @param gooruOid and fileName  
	 * @return fileName
	 * @throws GwtException
	 */
//	String saveResourceImage(String gooruOid, String fileName);
	
	
	String uploadProfileImage(String fileNameWithOutRepository,String fileName)  throws GwtException, ServerDownException;

	CollectionItemDo saveImage(String gooruOid, String resourceId,
			String fileName);

	
	String saveImageCollection(String courseId, String unitId, String lessonId, String collectionId, CreateDo createDo, String fileName) throws GwtException, ServerDownException;
}
