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
package org.ednovo.gooru.application.shared.model.drive;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GoogleDriveItemDo implements IsSerializable {
	
	private String title;
	private String description;
	private String createdDate;
	private String id;
	private String alternateLink;
	private String kind;
	private String mimeType;
	private String modifiedByMeDate;
	private String modifiedDate;
	private String parentId;
	private String role;
	private String type;
	private String selfLink;
	private String embedLink;
	private String defaultOpenWithLink;
	private String iconLink;
	private String thumbnailLink;
	private boolean shared;
	
	public String getRole() {
		return role;
	}
	/**
	 * @return the selfLink
	 */
	public String getSelfLink() {
		return selfLink;
	}
	/**
	 * @param selfLink the selfLink to set
	 */
	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}
	/**
	 * @return the embedLink
	 */
	public String getEmbedLink() {
		return embedLink;
	}
	/**
	 * @param embedLink the embedLink to set
	 */
	public void setEmbedLink(String embedLink) {
		this.embedLink = embedLink;
	}
	/**
	 * @return the defaultOpenWithLink
	 */
	public String getDefaultOpenWithLink() {
		return defaultOpenWithLink;
	}
	/**
	 * @param defaultOpenWithLink the defaultOpenWithLink to set
	 */
	public void setDefaultOpenWithLink(String defaultOpenWithLink) {
		this.defaultOpenWithLink = defaultOpenWithLink;
	}
	/**
	 * @return the iconLink
	 */
	public String getIconLink() {
		return iconLink;
	}
	/**
	 * @param iconLink the iconLink to set
	 */
	public void setIconLink(String iconLink) {
		this.iconLink = iconLink;
	}
	/**
	 * @return the thumbnailLink
	 */
	public String getThumbnailLink() {
		return thumbnailLink;
	}
	/**
	 * @param thumbnailLink the thumbnailLink to set
	 */
	public void setThumbnailLink(String thumbnailLink) {
		this.thumbnailLink = thumbnailLink;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAlternateLink() {
		return alternateLink;
	}
	public void setAlternateLink(String alternateLink) {
		this.alternateLink = alternateLink;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getModifiedByMeDate() {
		return modifiedByMeDate;
	}
	public void setModifiedByMeDate(String modifiedByMeDate) {
		this.modifiedByMeDate = modifiedByMeDate;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public boolean isShared() {
		return shared;
	}
	public void setShared(boolean shared) {
		this.shared = shared;
	}
	
}
