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

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;
import org.ednovo.gooru.client.uc.AppPopUp;

import com.gwtplatform.mvp.client.PopupView;

/**
 * @author Search Team
 *
 */
public interface IsImageUploadView extends PopupView, IsViewWithHandlers<ImageUploadUiHandlers> {
	/**
	 * 
	 * @param mediaUploadDo 
	 * <p>
	 * 	This will  set the media upload data after completion
	 *  the process of image upload. 
	 *  <p>
	 */
	void setImageUpload(MediaUploadDo mediaUploadDo);
	/**
	 * <p>
	 *   It will  initialize the form based  event while injecting 
	 *   the {@link ImageUploadView}.
	 * <p>
	 */
	void handelFormEvent();	
	/**
	 *  It will  handle the close image upload, see more details for {@link AppPopUp}
	 */
	void closeImageUploadWidget();
	/**
	 * 
	 * @param state
	 * <p>This is used to blocking the UI while processing the image 
	 * upload, otherwise when image  service call get's start executed. 
	 * UI block will handle by enabling or disabling  using boolean value.</p>
	 */
	void glasspanelLoadingImage(boolean state);
	/**
	 * <p>Since the image upload widget get's 
	 * injected once it will maintain it's last state. 
	 * This customize method will handle the 
	 * reset to bring initial state.<p>
	 */
	void resetImageUploadWidget();
	
	public void showUploadTypeWidgets(boolean isUserUnder13);
	
	public void setAspectRatio(float aspectRatio);
	
	public void isFromEditQuestion(boolean isEdit);
	
	public void displayCropPopup(final MediaUploadDo mediaUploadDo);
	void setCroppedImage(String filename);
}
