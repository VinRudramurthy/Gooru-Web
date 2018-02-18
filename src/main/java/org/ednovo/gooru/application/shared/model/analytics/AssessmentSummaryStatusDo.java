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
 * @fileName : AssessmentSummaryStatusDo.java
 * 
 * @Author :Gooru Team
 * 
 * @Reviewer:
 */
@JsonInclude(Include.NON_NULL)
public class AssessmentSummaryStatusDo implements IsSerializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String title;
	private String profileUrl;
	private String session;
	private String gooruOId;
	private String evidence;
	private String assessmentCount;
	private String score;
	private String userName;
	private int scoreInPercentage;
	private int goal;
	private String resourceType;
	public String getTitle() {
		return title;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public String getSession() {
		return session;
	}
	public String getGooruOId() {
		return gooruOId;
	}
	public String getEvidence() {
		return evidence;
	}
	public String getAssessmentCount() {
		return assessmentCount;
	}
	public String getScore() {
		return score;
	}
	public String getUserName() {
		return userName;
	}
	public int getScoreInPercentage() {
		return scoreInPercentage;
	}
	public int getGoal() {
		return goal;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public void setGooruOId(String gooruOId) {
		this.gooruOId = gooruOId;
	}
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}
	public void setAssessmentCount(String assessmentCount) {
		this.assessmentCount = assessmentCount;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setScoreInPercentage(int scoreInPercentage) {
		this.scoreInPercentage = scoreInPercentage;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
}
