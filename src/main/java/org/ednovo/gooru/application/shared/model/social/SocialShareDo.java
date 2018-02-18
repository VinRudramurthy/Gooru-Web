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
package org.ednovo.gooru.application.shared.model.social;

public class SocialShareDo {
	
	private String gooruOid;
	
	private String description;
	
	private String title;
	
	private String thumbnailurl;
	
	private String categoryType;
	
	private boolean isOnlyIcon;
	
	private String bitlylink;
	
	private String to;
	
	private String fromDisplayName;
	
	private String cfm;
	
	private String subject;
	
	private String message;
	
	private String emailId;
	
	private String shareType;
	
	private Boolean isSearchShare;
	
	private String pppBitlylink;
	
	private String rawUrl;
	
	private String decodeRawUrl;
	
	public SocialShareDo(){}

	public String getGooruOid() {
		return gooruOid;
	}

	public void setGooruOid(String gooruOid) {
		this.gooruOid = gooruOid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnailurl() {
		return thumbnailurl;
	}

	public void setThumbnailurl(String thumbnailurl) {
		this.thumbnailurl = thumbnailurl;
	}

	public boolean isOnlyIcon() {
		return isOnlyIcon;
	}

	public void setOnlyIcon(boolean isOnlyIcon) {
		this.isOnlyIcon = isOnlyIcon;
	}

	public String getBitlylink() {
		return bitlylink;
	}

	public void setBitlylink(String bitlylink) {
		this.bitlylink = bitlylink;
	}
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}


	public String getFromDisplayName() {
		return fromDisplayName;
	}

	public void setFromDisplayName(String fromDisplayName) {
		this.fromDisplayName = fromDisplayName;
	}

	public String getCfm() {
		return cfm;
	}

	public void setCfm(String cfm) {
		this.cfm = cfm;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public Boolean getIsSearchShare() {
		return isSearchShare;
	}

	public void setIsSearchShare(Boolean isSearchShare) {
		this.isSearchShare = isSearchShare;
	}

	public String getPppBitlylink() {
		return pppBitlylink;
	}

	public void setPppBitlylink(String pppBitlylink) {
		this.pppBitlylink = pppBitlylink;
	}

	public String getRawUrl() {
		return rawUrl;
	}

	public void setRawUrl(String rawUrl) {
		this.rawUrl = rawUrl;
	}

	public String getDecodeRawUrl() {
		return decodeRawUrl;
	}

	public void setDecodeRawUrl(String decodeRawUrl) {
		this.decodeRawUrl = decodeRawUrl;
	}
}
