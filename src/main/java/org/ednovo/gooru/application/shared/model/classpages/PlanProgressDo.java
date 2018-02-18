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

import com.google.gwt.user.client.rpc.IsSerializable; 
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * @fileName : PlanProgressDo.java
 * 
 * @Author :Gooru Team
 * 
 * @Reviewer:
 */
@JsonInclude(Include.NON_NULL)
public class PlanProgressDo implements IsSerializable {

	private static final long serialVersionUID = 1L;
	private String title;
	private String gooruOId;
	private String scoreStatus;
	private String type;
	private int views;
	private int scoreInPercentage;
	private Integer sequence;
	private long timeSpent;
	private int collectionCount;
	private int assessmentCount;
	private int collectionsViewed;
	private int assessmentsAttempted;
	private String collectionType;
	private String evidence;
	private long lastAccessed;
	private long totalStudyTime;
	private String userName;
	private String userUId;
	private String url;
	private String thumbnail;
	private ArrayList<PlanProgressDo> item;
	private ArrayList<PlanProgressDo> usageData;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGooruOId() {
		return gooruOId;
	}
	public void setGooruOId(String gooruOId) {
		this.gooruOId = gooruOId;
	}
	public String getScoreStatus() {
		return scoreStatus;
	}
	public void setScoreStatus(String scoreStatus) {
		this.scoreStatus = scoreStatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getScoreInPercentage() {
		return scoreInPercentage;
	}
	public void setScoreInPercentage(int scoreInPercentage) {
		this.scoreInPercentage = scoreInPercentage;
	}
	public long getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
	}
	public int getCollectionCount() {
		return collectionCount;
	}
	public void setCollectionCount(int collectionCount) {
		this.collectionCount = collectionCount;
	}
	public int getAssessmentCount() {
		return assessmentCount;
	}
	public void setAssessmentCount(int assessmentCount) {
		this.assessmentCount = assessmentCount;
	}
	public int getCollectionsViewed() {
		return collectionsViewed;
	}
	public void setCollectionsViewed(int collectionsViewed) {
		this.collectionsViewed = collectionsViewed;
	}
	public int getAssessmentsAttempted() {
		return assessmentsAttempted;
	}
	public void setAssessmentsAttempted(int assessmentsAttempted) {
		this.assessmentsAttempted = assessmentsAttempted;
	}
	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	public String getEvidence() {
		return evidence;
	}
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}
	public long getLastAccessed() {
		return lastAccessed;
	}
	public void setLastAccessed(long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
	public long getTotalStudyTime() {
		return totalStudyTime;
	}
	public void setTotalStudyTime(long totalStudyTime) {
		this.totalStudyTime = totalStudyTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserUId() {
		return userUId;
	}
	public void setUserUId(String userUId) {
		this.userUId = userUId;
	}
	public ArrayList<PlanProgressDo> getItem() {
		return item;
	}
	public void setItem(ArrayList<PlanProgressDo> item) {
		this.item = item;
	}
	public ArrayList<PlanProgressDo> getUsageData() {
		return usageData;
	}
	public void setUsageData(ArrayList<PlanProgressDo> usageData) {
		this.usageData = usageData;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}