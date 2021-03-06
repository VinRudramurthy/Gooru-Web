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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item;

import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UpdateQuestionImageView extends Composite implements MessageProperties{
	
	private String thumbnailImageUrl=null;
	@UiField Image updateQuestionImage;
	@UiField Label uploadImage;
	@UiField Label removeImage;
	@UiField HTMLPanel questionImageText;
	public interface UpdateQuestionImageViewUiBinder extends UiBinder<Widget, UpdateQuestionImageView>{
		
	}
	
	private static UpdateQuestionImageViewUiBinder uiBinder=GWT.create(UpdateQuestionImageViewUiBinder.class);
	
	
	
	public UpdateQuestionImageView(){
		initWidget(uiBinder.createAndBindUi(this));
		questionImageText.getElement().setInnerHTML(GL0860);
		uploadImage.setText(GL0861);
		removeImage.setText(GL0862);
	}



	public Image getUpdateQuestionImage() {
		return updateQuestionImage;
	}



	public Label getUploadImage() {
		return uploadImage;
	}



	public Label getRemoveImage() {
		return removeImage;
	}



	public void setUpdateQuestionImage(Image updateQuestionImage) {
		this.updateQuestionImage = updateQuestionImage;
	}



	public void setUploadImage(Label uploadImage) {
		this.uploadImage = uploadImage;
	}



	public void setRemoveImage(Label removeImage) {
		this.removeImage = removeImage;
	}



	public String getThumbnailImageUrl() {
		return thumbnailImageUrl;
	}



	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		this.thumbnailImageUrl = thumbnailImageUrl;
	}
	
	
}
