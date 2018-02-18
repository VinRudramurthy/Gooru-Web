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
package org.ednovo.gooru.application.shared.model.classpages;

import java.util.ArrayList;
import java.util.Map;

import org.ednovo.gooru.application.shared.model.analytics.MetaDataDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * @fileName : UserDataDo.java
 * 
 * @Author :Gooru Team
 * 
 * @Reviewer:
 */
@JsonInclude(Include.NON_NULL)
public class MasterAssessmentDo implements IsSerializable{
	
	private static final long serialVersionUID = 1L;
	
	private int reaction;
	private String text;
	private int attemptStatus;
	private int avgReaction;
	private String status;
	private int itemSequence;
	private String userUId;
	private int score;
	private int totalAttemptUserCount;
	private String type;
	private long timeSpent;
	private int totalInCorrectCount;
	private int totalCorrectCount;
	private int skip;
	private long avgTimeSpent;
	private int views;
	private int attempts;
	private String userName;
	private String questionType;
	private String resourceGooruOId;
	private String feedbackStatus;
	private String answerObject;
	private Map<String, Integer> options;
	private ArrayList<MetaDataDo> metaData;
	private String gradeInPercentage;
	private String firstName;
	private String lastName;
	private String emailId;
	private String profileImageUrl;

	
	//This are add for getting the resource data in the teacher summary
	private String category;
	private String resourceFormat;
	private String title;
	
	public int getReaction() {
		return reaction;
	}
	public void setReaction(int reaction) {
		this.reaction = reaction;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getAttemptStatus() {
		return attemptStatus;
	}
	public void setAttemptStatus(int attemptStatus) {
		this.attemptStatus = attemptStatus;
	}
	public int getAvgReaction() {
		return avgReaction;
	}
	public void setAvgReaction(int avgReaction) {
		this.avgReaction = avgReaction;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserUId() {
		return userUId;
	}
	public void setUserUId(String userUId) {
		this.userUId = userUId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getTotalAttemptUserCount() {
		return totalAttemptUserCount;
	}
	public void setTotalAttemptUserCount(int totalAttemptUserCount) {
		this.totalAttemptUserCount = totalAttemptUserCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
	}
	public int getTotalInCorrectCount() {
		return totalInCorrectCount;
	}
	public void setTotalInCorrectCount(int totalInCorrectCount) {
		this.totalInCorrectCount = totalInCorrectCount;
	}
	public long getAvgTimeSpent() {
		return avgTimeSpent;
	}
	public void setAvgTimeSpent(long avgTimeSpent) {
		this.avgTimeSpent = avgTimeSpent;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getResourceGooruOId() {
		return resourceGooruOId;
	}
	public void setResourceGooruOId(String resourceGooruOId) {
		this.resourceGooruOId = resourceGooruOId;
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
	
	public Map<String, Integer> getOptions() {
		return options;
	}
	public void setOptions(Map<String, Integer> options) {
		this.options = options;
	}
	public ArrayList<MetaDataDo> getMetaData() {
		return metaData;
	}
	public void setMetaData(ArrayList<MetaDataDo> metaData) {
		this.metaData = metaData;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getTotalCorrectCount() {
		return totalCorrectCount;
	}
	public void setTotalCorrectCount(int totalCorrectCount) {
		this.totalCorrectCount = totalCorrectCount;
	}
	public int getSkip() {
		return skip;
	}
	public void setSkip(int skip) {
		this.skip = skip;
	}
	public String getGradeInPercentage() {
		return gradeInPercentage;
	}
	public void setGradeInPercentage(String gradeInPercentage) {
		this.gradeInPercentage = gradeInPercentage;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public String getResourceFormat() {
		return resourceFormat;
	}
	public void setResourceFormat(String resourceFormat) {
		this.resourceFormat = resourceFormat;
	}
}
