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
 * @fileName : OetextDataDO.java
 * 
 * @Author :Gooru Team
 * 
 * @Reviewer:
 */
@JsonInclude(Include.NON_NULL)
public class OetextDataDO implements IsSerializable{

	private static final long serialVersionUID = 1L;
	private String OEText;
	private String gooruUId;
	private String organizationUId;
	private String userName;
	private String userGroupUId;
	private String feedbackStatus;
	private String feedbackText;
	private long feedbackTimestamp;
	private String feedbackProviderUId;
	private String answerObject;
	private int status;
	public String getOEText() {
		return OEText;
	}
	public void setOEText(String oEText) {
		OEText = oEText;
	}
	public String getGooruUId() {
		return gooruUId;
	}
	public void setGooruUId(String gooruUId) {
		this.gooruUId = gooruUId;
	}
	public String getOrganizationUId() {
		return organizationUId;
	}
	public void setOrganizationUId(String organizationUId) {
		this.organizationUId = organizationUId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserGroupUId() {
		return userGroupUId;
	}
	public void setUserGroupUId(String userGroupUId) {
		this.userGroupUId = userGroupUId;
	}
	public String getFeedbackStatus() {
		return feedbackStatus;
	}
	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}
	public String getAnswerObject() {
		return answerObject;
	}
	public void setAnswerObject(String answerObject) {
		this.answerObject = answerObject;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getFeedbackText() {
		return feedbackText;
	}
	public void setFeedbackText(String feedbackText) {
		this.feedbackText = feedbackText;
	}
	public long getFeedbackTimestamp() {
		return feedbackTimestamp;
	}
	public void setFeedbackTimestamp(long feedbackTimestamp) {
		this.feedbackTimestamp = feedbackTimestamp;
	}
	public String getFeedbackProviderUId() {
		return feedbackProviderUId;
	}
	public void setFeedbackProviderUId(String feedbackProviderUId) {
		this.feedbackProviderUId = feedbackProviderUId;
	}
}
