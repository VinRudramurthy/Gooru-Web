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
package org.ednovo.gooru.application.shared.model.content;

import java.util.List;

import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class InsightsUserDataDo implements IsSerializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -1909303124373795643L;

	private String title;
	private Long avgTimeSpent=null;
	private Integer status=null;
	private String gooruUId=null;
	private Integer views=null;
	private Integer score=null;
	private String userName=null;
	private Long timeSpent=0L;
	private String resourceGooruOId=null;
	private Integer isRequired=0;

	private String text;
	private String questionType;
	private String type;
//	private attemptStatus;
	private Integer avgReaction;
	private Integer reaction;
	private String collectionGooruOId;
	private String description;
	private String lastModified;
	private String category;
	private String thumbnail;
	private String options;
	private String metaData;
	private List<UserDataDo> userData;
	private Integer skip;
	private Integer totalAttemptUserCount;
	private Integer attempts;
	private Integer totalCorrectCount;
	/**
	 * This method is to get the userData
	 */
	public List<UserDataDo> getUserData() {
		return userData;
	}


	/**
	 * This method is to set the userData
	 */
	public void setUserData(List<UserDataDo> userData) {
		this.userData = userData;
	}


	private Integer totalInCorrectCount;
	private Integer yetToAnswer;
	private Integer userCount;
	private Integer itemSequence;
	private Integer gradeInPercentage;
	private Integer totalQuestionCount;
	private String answerObject;
	private String feedbackStatus;
	private String feedbackText;
	private String feedbackProviderUId;
	private Long feedbackTimestamp;
	private String feedbackTeacherName;
	private String minimumScore;
	private String estimatedTime;



	public InsightsUserDataDo(){}


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
	 * This method is to get the avgTimeSpent
	 */
	public long getAvgTimeSpent() {
		return avgTimeSpent;
	}


	/**
	 * This method is to get the status
	 */
	public Integer getStatus() {
		return status;
	}


	/**
	 * This method is to set the status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}


	/**
	 * This method is to get the gooruUId
	 */
	public String getGooruUId() {
		return gooruUId;
	}


	/**
	 * This method is to set the gooruUId
	 */
	public void setGooruUId(String gooruUId) {
		this.gooruUId = gooruUId;
	}


	/**
	 * This method is to get the views
	 */
	public Integer getViews() {
		return views;
	}


	/**
	 * This method is to set the views
	 */
	public void setViews(Integer views) {
		this.views = views;
	}


	/**
	 * This method is to get the score
	 */
	public Integer getScore() {
		return score;
	}


	/**
	 * This method is to set the score
	 */
	public void setScore(Integer score) {
		this.score = score;
	}


	/**
	 * This method is to get the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * This method is to set the userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * This method is to get the timeSpent
	 */
	public Long getTimeSpent() {
		return timeSpent;
	}


	/**
	 * This method is to set the timeSpent
	 */
	public void setTimeSpent(Long timeSpent) {
		this.timeSpent = timeSpent;
	}


	/**
	 * This method is to get the resourceGooruOId
	 */
	public String getResourceGooruOId() {
		return resourceGooruOId;
	}


	/**
	 * This method is to set the resourceGooruOId
	 */
	public void setResourceGooruOId(String resourceGooruOId) {
		this.resourceGooruOId = resourceGooruOId;
	}


	/**
	 * This method is to get the isRequired
	 */
	public Integer getIsRequired() {
		return isRequired;
	}


	/**
	 * This method is to set the isRequired
	 */
	public void setIsRequired(Integer isRequired) {
		this.isRequired = isRequired;
	}


	/**
	 * This method is to get the text
	 */
	public String getText() {
		return text;
	}


	/**
	 * This method is to set the text
	 */
	public void setText(String text) {
		this.text = text;
	}


	/**
	 * This method is to get the questionType
	 */
	public String getQuestionType() {
		return questionType;
	}


	/**
	 * This method is to set the questionType
	 */
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}


	/**
	 * This method is to get the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * This method is to set the type
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * This method is to get the avgReaction
	 */
	public Integer getAvgReaction() {
		return avgReaction;
	}


	/**
	 * This method is to set the avgReaction
	 */
	public void setAvgReaction(Integer avgReaction) {
		this.avgReaction = avgReaction;
	}


	/**
	 * This method is to get the reaction
	 */
	public Integer getReaction() {
		return reaction;
	}


	/**
	 * This method is to set the reaction
	 */
	public void setReaction(Integer reaction) {
		this.reaction = reaction;
	}


	/**
	 * This method is to get the collectionGooruOId
	 */
	public String getCollectionGooruOId() {
		return collectionGooruOId;
	}


	/**
	 * This method is to set the collectionGooruOId
	 */
	public void setCollectionGooruOId(String collectionGooruOId) {
		this.collectionGooruOId = collectionGooruOId;
	}


	/**
	 * This method is to get the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * This method is to set the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * This method is to get the lastModified
	 */
	public String getLastModified() {
		return lastModified;
	}


	/**
	 * This method is to set the lastModified
	 */
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
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
	 * This method is to get the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}


	/**
	 * This method is to set the thumbnail
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}


	/**
	 * This method is to get the options
	 */
	public String getOptions() {
		return options;
	}


	/**
	 * This method is to set the options
	 */
	public void setOptions(String options) {
		this.options = options;
	}


	/**
	 * This method is to get the metaData
	 */
	public String getMetaData() {
		return metaData;
	}


	/**
	 * This method is to set the metaData
	 */
	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	/**
	 * This method is to get the skip
	 */
	public Integer getSkip() {
		return skip;
	}


	/**
	 * This method is to set the skip
	 */
	public void setSkip(Integer skip) {
		this.skip = skip;
	}


	/**
	 * This method is to get the totalAttemptUserCount
	 */
	public Integer getTotalAttemptUserCount() {
		return totalAttemptUserCount;
	}


	/**
	 * This method is to set the totalAttemptUserCount
	 */
	public void setTotalAttemptUserCount(Integer totalAttemptUserCount) {
		this.totalAttemptUserCount = totalAttemptUserCount;
	}


	/**
	 * This method is to get the attempts
	 */
	public Integer getAttempts() {
		return attempts;
	}


	/**
	 * This method is to set the attempts
	 */
	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}


	/**
	 * This method is to get the totalCorrectCount
	 */
	public Integer getTotalCorrectCount() {
		return totalCorrectCount;
	}


	/**
	 * This method is to set the totalCorrectCount
	 */
	public void setTotalCorrectCount(Integer totalCorrectCount) {
		this.totalCorrectCount = totalCorrectCount;
	}


	/**
	 * This method is to get the totalInCorrectCount
	 */
	public Integer getTotalInCorrectCount() {
		return totalInCorrectCount;
	}


	/**
	 * This method is to set the totalInCorrectCount
	 */
	public void setTotalInCorrectCount(Integer totalInCorrectCount) {
		this.totalInCorrectCount = totalInCorrectCount;
	}


	/**
	 * This method is to get the yetToAnswer
	 */
	public Integer getYetToAnswer() {
		return yetToAnswer;
	}


	/**
	 * This method is to set the yetToAnswer
	 */
	public void setYetToAnswer(Integer yetToAnswer) {
		this.yetToAnswer = yetToAnswer;
	}


	/**
	 * This method is to get the userCount
	 */
	public Integer getUserCount() {
		return userCount;
	}


	/**
	 * This method is to set the userCount
	 */
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}


	/**
	 * This method is to get the itemSequence
	 */
	public Integer getItemSequence() {
		return itemSequence;
	}


	/**
	 * This method is to set the itemSequence
	 */
	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}


	/**
	 * This method is to get the gradeInPercentage
	 */
	public Integer getGradeInPercentage() {
		return gradeInPercentage;
	}


	/**
	 * This method is to set the gradeInPercentage
	 */
	public void setGradeInPercentage(Integer gradeInPercentage) {
		this.gradeInPercentage = gradeInPercentage;
	}


	/**
	 * This method is to get the totalQuestionCount
	 */
	public Integer getTotalQuestionCount() {
		return totalQuestionCount;
	}


	/**
	 * This method is to set the totalQuestionCount
	 */
	public void setTotalQuestionCount(Integer totalQuestionCount) {
		this.totalQuestionCount = totalQuestionCount;
	}


	/**
	 * This method is to get the answerObject
	 */
	public String getAnswerObject() {
		return answerObject;
	}


	/**
	 * This method is to set the answerObject
	 */
	public void setAnswerObject(String answerObject) {
		this.answerObject = answerObject;
	}


	/**
	 * This method is to get the feedbackStatus
	 */
	public String getFeedbackStatus() {
		return feedbackStatus;
	}


	/**
	 * This method is to set the feedbackStatus
	 */
	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}


	/**
	 * This method is to get the feedbackText
	 */
	public String getFeedbackText() {
		return feedbackText;
	}


	/**
	 * This method is to set the feedbackText
	 */
	public void setFeedbackText(String feedbackText) {
		this.feedbackText = feedbackText;
	}


	/**
	 * This method is to get the feedbackProviderUId
	 */
	public String getFeedbackProviderUId() {
		return feedbackProviderUId;
	}


	/**
	 * This method is to set the feedbackProviderUId
	 */
	public void setFeedbackProviderUId(String feedbackProviderUId) {
		this.feedbackProviderUId = feedbackProviderUId;
	}


	/**
	 * This method is to get the feedbackTimestamp
	 */
	public Long getFeedbackTimestamp() {
		return feedbackTimestamp;
	}


	/**
	 * This method is to set the feedbackTimestamp
	 */
	public void setFeedbackTimestamp(Long feedbackTimestamp) {
		this.feedbackTimestamp = feedbackTimestamp;
	}


	/**
	 * This method is to get the feedbackTeacherName
	 */
	public String getFeedbackTeacherName() {
		return feedbackTeacherName;
	}


	/**
	 * This method is to set the feedbackTeacherName
	 */
	public void setFeedbackTeacherName(String feedbackTeacherName) {
		this.feedbackTeacherName = feedbackTeacherName;
	}


	/**
	 * This method is to get the minimumScore
	 */
	public String getMinimumScore() {
		return minimumScore;
	}


	/**
	 * This method is to set the minimumScore
	 */
	public void setMinimumScore(String minimumScore) {
		this.minimumScore = minimumScore;
	}


	/**
	 * This method is to get the estimatedTime
	 */
	public String getEstimatedTime() {
		return estimatedTime;
	}


	/**
	 * This method is to set the estimatedTime
	 */
	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}


	/**
	 * This method is to set the avgTimeSpent
	 */
	public void setAvgTimeSpent(Long avgTimeSpent) {
		this.avgTimeSpent = avgTimeSpent;
	}



}
