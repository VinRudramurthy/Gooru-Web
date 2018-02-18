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
package org.ednovo.gooru.application.shared.model.library;

import java.util.ArrayList;

import org.ednovo.gooru.application.shared.model.content.ResourceFormatDo;
import org.ednovo.gooru.application.shared.model.content.ResourceSourceDo;
import org.ednovo.gooru.application.shared.model.content.ResourceTypeDo;
import org.ednovo.gooru.application.shared.model.content.SearchRatingsDo;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class LibraryResourceDo implements IsSerializable {

	private static final long serialVersionUID = 2411080367742513414L;
	private String title;
	private String category;
	private String gooruOid;
	private String url;
	private String collectionItemId;
	private ThumbnailDo thumbnails;
	private ResourceSourceDo resourceSource;
	private ResourceTypeDo resourceType;
	private ResourceFormatDo resourceFormat;
	private ArrayList<String> publisher;

	private SearchRatingsDo ratings;
	public LibraryResourceDo(){}

	/**
	 * This method is to get the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * This method is to set the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * This method is to get the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * This method is to set the category
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * This method is to get the gooruOid
	 */
	public String getGooruOid() {
		return gooruOid;
	}
	/**
	 * This method is to set the gooruOid
	 */
	public void setGooruOid(String gooruOid) {
		this.gooruOid = gooruOid;
	}
	/**
	 * This method is to get the thumbnails
	 */
	public ThumbnailDo getThumbnails() {
		return thumbnails;
	}
	/**
	 * This method is to set the thumbnails
	 */
	public void setThumbnails(ThumbnailDo thumbnails) {
		this.thumbnails = thumbnails;
	}
	/**
	 * This method is to get the resourceSource
	 */
	public ResourceSourceDo getResourceSource() {
		return resourceSource;
	}
	/**
	 * This method is to set the resourceSource
	 */
	public void setResourceSource(ResourceSourceDo resourceSource) {
		this.resourceSource = resourceSource;
	}
	/**
	 * This method is to get the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * This method is to set the url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * This method is to get the resourceType
	 */
	public ResourceTypeDo getResourceType() {
		return resourceType;
	}
	/**
	 * This method is to set the resourceType
	 */
	public void setResourceType(ResourceTypeDo resourceType) {
		this.resourceType = resourceType;
	}

	public ResourceFormatDo getResourceFormat() {
		return resourceFormat;
	}

	public void setResourceFormat(ResourceFormatDo resourceFormat) {
		this.resourceFormat = resourceFormat;
	}

	public String getCollectionItemId() {
		return collectionItemId;
	}

	public void setCollectionItemId(String collectionItemId) {
		this.collectionItemId = collectionItemId;
	}

	public SearchRatingsDo getRatings() {
		return ratings;
	}

	public void setRatings(SearchRatingsDo ratings) {
		this.ratings = ratings;
	}

	public ArrayList<String> getPublisher() {
		return publisher;
	}

	public void setPublisher(ArrayList<String> publisher) {
		this.publisher = publisher;
	}

}