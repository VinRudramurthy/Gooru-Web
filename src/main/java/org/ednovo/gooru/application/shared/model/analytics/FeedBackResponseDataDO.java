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
package org.ednovo.gooru.application.shared.model.analytics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * @fileName : FeedBackResponseDataDO.java
 * 
 * @Author :Gooru Team
 * 
 * @Reviewer:
 */
@JsonInclude(Include.NON_NULL)
public class FeedBackResponseDataDO implements IsSerializable{

	private static final long serialVersionUID = 1L;
	private String contentGooruOId;
	private String contentItemId;
	private long createdOn;
	private String feedbackProvidedByGooruId;
	private String freeText;
	private String parentGooruOId;
	private String sessionId;
	private String sessionItemFeedbackUid;
	private String userGooruId;
	public String getContentGooruOId() {
		return contentGooruOId;
	}
	public void setContentGooruOId(String contentGooruOId) {
		this.contentGooruOId = contentGooruOId;
	}
	public String getContentItemId() {
		return contentItemId;
	}
	public void setContentItemId(String contentItemId) {
		this.contentItemId = contentItemId;
	}
	public long getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}
	public String getFeedbackProvidedByGooruId() {
		return feedbackProvidedByGooruId;
	}
	public void setFeedbackProvidedByGooruId(String feedbackProvidedByGooruId) {
		this.feedbackProvidedByGooruId = feedbackProvidedByGooruId;
	}
	public String getFreeText() {
		return freeText;
	}
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}
	public String getParentGooruOId() {
		return parentGooruOId;
	}
	public void setParentGooruOId(String parentGooruOId) {
		this.parentGooruOId = parentGooruOId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionItemFeedbackUid() {
		return sessionItemFeedbackUid;
	}
	public void setSessionItemFeedbackUid(String sessionItemFeedbackUid) {
		this.sessionItemFeedbackUid = sessionItemFeedbackUid;
	}
	public String getUserGooruId() {
		return userGooruId;
	}
	public void setUserGooruId(String userGooruId) {
		this.userGooruId = userGooruId;
	}
}
