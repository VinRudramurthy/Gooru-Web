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

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * @fileName : GradeJsonData.java
 * 
 * @Author :Gooru Team
 * 
 * @Reviewer:
 */
@JsonInclude(Include.NON_NULL)
public class GradeJsonData implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String estimatedTime;
	private String title;
	private int status;
	private int itemSequence;
	private ArrayList<UserDataDo> userData;
	private String resourceGooruOId;
	private String minimumScore;
	private long avgTimeSpent;
	private long timeSpent;
	private String aggregateData;
	
	public GradeJsonData(){
		
	}
	public String getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public ArrayList<UserDataDo> getUserData() {
		return userData;
	}
	public void setUserData(ArrayList<UserDataDo> userData) {
		this.userData = userData;
	}
	public String getResourceGooruOId() {
		return resourceGooruOId;
	}
	public void setResourceGooruOId(String resourceGooruOId) {
		this.resourceGooruOId = resourceGooruOId;
	}
	public String getMinimumScore() {
		return minimumScore;
	}
	public void setMinimumScore(String minimumScore) {
		this.minimumScore = minimumScore;
	}
	public long getAvgTimeSpent() {
		return avgTimeSpent;
	}
	public void setAvgTimeSpent(long avgTimeSpent) {
		this.avgTimeSpent = avgTimeSpent;
	}
	public long getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
	}
	public String getAggregateData() {
		return aggregateData;
	}
	public void setAggregateData(String aggregateData) {
		this.aggregateData = aggregateData;
	}
}
