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
package org.ednovo.gooru.application.shared.model.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.shared.model.content.LicenseDo;
import org.ednovo.gooru.application.shared.model.content.ResourceDo;
import org.ednovo.gooru.application.shared.model.content.ResourceSourceDo;
import org.ednovo.gooru.application.shared.model.content.ResourceTypeDo;
import org.ednovo.gooru.application.shared.model.content.SearchRatingsDo;
import org.ednovo.gooru.application.shared.model.content.SearchResourceFormatDO;
import org.ednovo.gooru.application.shared.model.content.SearchResultsTagsDo;
import org.ednovo.gooru.application.shared.model.content.TagDo;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ResourceSearchResultDo extends ResourceDo implements IsSerializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6634893604641263955L;

	private String gooruOid;

	private long entryId;

	private String url;

	private String resourceTitle;

	private String resourceTypeString;

	private String category;

	private String description;

	private int totalViews;

	private List<String> subjectNames;

	private List<String> courseNames;

	private List<String> unitNames;

	private List<String> topicNames;

	private List<String> lessonNames;

	private UserDo owner;

	private String attribution;

	private List<Map<String, String>> standards;

	private String grade;

	private String tags;

	private String averageTime;

	private int sharedCount = 0;

	private String numOfPages;

	private String durationInSec;

	private LicenseDo license;

	private Integer votesUp;

	private Integer votesDown;

	private Set<TagDo> tagSet;

	private String assetURI;

	private ResourceTypeDo resourceType;

	private ResourceSourceDo  resourceSource;

	private SearchResourceFormatDO resourceFormat;

	private String questionType;

	private String mediaType;

	private int noOfResources;

	private String folder;

	private String gooruUId;

	private int noOfQuestions;

	private int scollectionCount;

	private int resourceCount;

	private int resourceAddedCount;

	private int resourceUsedUserCount;

	private int questionCount;

	private SearchRatingsDo ratings;

	private List<String> publisher;

	private List<String> aggregator;

	private List<SearchResultsTagsDo> resourceTags = new ArrayList<SearchResultsTagsDo>();


	public SearchRatingsDo getRatings() {
		return ratings;
	}

	public void setRatings(SearchRatingsDo ratings) {
		this.ratings = ratings;
	}

	/**
	 * This method is to get the mediaType
	 */
	public String getMediaType() {
		return mediaType;
	}

	/**
	 * This method is to set the mediaType
	 */
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public ResourceSearchResultDo() {

	}

	public int getTotalViews() {
		return totalViews;
	}

	public void setTotalViews(int totalViews) {
		this.totalViews = totalViews;
	}

	public String getResourceTitle() {
		return resourceTitle;
	}

	public void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}

	public String getResourceTypeString() {
		return resourceTypeString;
	}

	public void setResourceTypeString(String resourceType) {
		this.resourceTypeString = resourceType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGooruOid() {
		return gooruOid;
	}

	public void setGooruOid(String gooruOid) {
		this.gooruOid = gooruOid;
	}

	public long getEntryId() {
		return entryId;
	}

	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}

	public void setOwner(UserDo creatorDo) {
		this.owner = creatorDo;
	}

	public UserDo getOwner() {
		return owner;
	}

	/*public void setStandards(List<Map<String, String>> standards) {
		this.standards = standards;
	}

	public List<Map<String, String>> getStandards() {
		return standards;
	}
*/
	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public String getAttribution() {
		return attribution;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(String averageTime) {
		this.averageTime = averageTime;
	}

	public int getSharedCount() {
		return sharedCount;
	}

	public void setSharedCount(int sharedCount) {
		this.sharedCount = sharedCount;
	}

	public String getNumOfPages() {
		return numOfPages;
	}

	public void setNumOfPages(String numOfPages) {
		this.numOfPages = numOfPages;
	}

	public String getDurationInSec() {
		return durationInSec;
	}

	public void setDurationInSec(String durationInSec) {
		this.durationInSec = durationInSec;
	}

	public List<String> getSubjectNames() {
		return subjectNames;
	}

	public void setSubjectNames(List<String> subjectNames) {
		this.subjectNames = subjectNames;
	}

	public List<String> getCourseNames() {
		return courseNames;
	}

	public void setCourseNames(List<String> courseNames) {
		this.courseNames = courseNames;
	}

	public List<String> getUnitNames() {
		return unitNames;
	}

	public void setUnitNames(List<String> unitNames) {
		this.unitNames = unitNames;
	}

	public List<String> getTopicNames() {
		return topicNames;
	}

	public void setTopicNames(List<String> topicNames) {
		this.topicNames = topicNames;
	}

	public List<String> getLessonNames() {
		return lessonNames;
	}

	public void setLessonNames(List<String> lessonNames) {
		this.lessonNames = lessonNames;
	}

	public void setLicense(LicenseDo license) {
		this.license = license;
	}

	public LicenseDo getLicense() {
		return license;
	}

	public Integer getVotesUp() {
		return votesUp;
	}

	public void setVotesUp(Integer votesUp) {
		this.votesUp = votesUp;
	}

	public Integer getVotesDown() {
		return votesDown;
	}

	public void setVotesDown(Integer votesDown) {
		this.votesDown = votesDown;
	}

	public void setTagSet(Set<TagDo> tagSet) {
		this.tagSet = tagSet;
	}

	public Set<TagDo> getTagSet() {
		return tagSet;
	}

	public String getAssetURI() {
		return assetURI;
	}

	public void setAssetURI(String assetURI) {
		this.assetURI = assetURI;
	}

	public void setResourceType(ResourceTypeDo resourceTypeDo) {
		this.resourceType = resourceTypeDo;
	}

	public ResourceTypeDo getResourceType() {
		return resourceType;
	}

	public void setResourceSource(ResourceSourceDo resourceSourceDo) {
		this.resourceSource = resourceSourceDo;
	}

	public ResourceSourceDo getResourceSource() {
		return resourceSource;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionType() {
		return questionType;
	}

	public int getNoOfResources() {
		return noOfResources;
	}

	public void setNoOfResources(int noOfResources) {
		this.noOfResources = noOfResources;
	}

	public List<Map<String, String>> getStandards() {
		return standards;
	}

	public void setStandards(List<Map<String, String>> standards) {
		this.standards = standards;
	}

	/**
	 * This method is to get the folder
	 */
	public String getFolder() {
		return folder;
	}

	/**
	 * This method is to set the folder
	 */
	public void setFolder(String folder) {
		this.folder = folder;
	}

	public SearchResourceFormatDO getNewResourceFormat() {
		return resourceFormat;
	}

	public void setResourceFormat(SearchResourceFormatDO resourceFormat) {
		this.resourceFormat = resourceFormat;
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

	public int getNoOfQuestions() {
		return noOfQuestions;
	}

	public void setNoOfQuestions(int noOfQuestions) {
		this.noOfQuestions = noOfQuestions;
	}

	public int getResourceCount() {
		return resourceCount;
	}

	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	public int getScollectionCount() {
		return scollectionCount;
	}

	public void setScollectionCount(int scollectionCount) {
		this.scollectionCount = scollectionCount;
	}



	public List<String> getPublisher() {
		return publisher;
	}

	public void setPublisher(List<String> publisher) {
		this.publisher = publisher;
	}

	public List<String> getAggregator() {
		return aggregator;
	}

	public void setAggregator(List<String> aggregator) {
		this.aggregator = aggregator;
	}

	/**
	 * @return the resourceTags
	 */
	public List<SearchResultsTagsDo> getNewResourceTags() {
		return resourceTags;
	}

	/**
	 * @param resourceTags the resourceTags to set
	 */
	public void setResourceTags(List<SearchResultsTagsDo> resourceTags) {
		this.resourceTags = resourceTags;
	}

	public int getResourceAddedCount() {
		return resourceAddedCount;
	}

	public void setResourceAddedCount(int resourceAddedCount) {
		this.resourceAddedCount = resourceAddedCount;
	}

	public int getResourceUsedUserCount() {
		return resourceUsedUserCount;
	}

	public void setResourceUsedUserCount(int resourceUsedUserCount) {
		this.resourceUsedUserCount = resourceUsedUserCount;
	}
}