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
package org.ednovo.gooru.client.mvp.socialshare.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Gooru Team
 * 
 */
public class UpdateSocialShareMetaDataEvent extends GwtEvent<UpdateSocialShareMetaDataHandler> {

	public static final Type<UpdateSocialShareMetaDataHandler> TYPE = new Type<UpdateSocialShareMetaDataHandler>();

	private String title;
	private String description;
	private String imageUrl;
	/**
	 * Class constructor
	 */
	public UpdateSocialShareMetaDataEvent(String title, String description, String imageUrl) {
		this.title = title;
		this.description = description;
		this.imageUrl=imageUrl;
	}

	@Override
	public Type<UpdateSocialShareMetaDataHandler> getAssociatedType() {
		return TYPE;
	}
	/**
	 * Update the meta data description of the social share
	 * @param title of the collection
	 * @param description of the collection
	 */
	@Override
	protected void dispatch(UpdateSocialShareMetaDataHandler handler) {
		handler.updateSocialShareMetaData(title, description, imageUrl);
	}

}
