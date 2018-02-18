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
package org.ednovo.gooru.application.shared.model.folder;

import java.util.List;

import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.content.CollectionSettingsDo;
import org.ednovo.gooru.application.shared.model.content.ResourceFormatDo;
import org.ednovo.gooru.application.shared.model.content.ResourceTypeDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;
import org.ednovo.gooru.application.shared.model.content.checkboxSelectedDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;


@JsonInclude(Include.NON_NULL)
public class FolderDo implements IsSerializable{

	private static final long serialVersionUID = -3298220423508874309L;

	private CourseSummaryDo summary;
	private String collectionType;
	private String gooruOid;
	private String title;
	private String sharing;
	private Integer itemCount;
	private String type;
	private ThumbnailDo thumbnails;
	private List<FolderItemDo> collectionItems;
	private ResourceFormatDo resourceFormat;
	private ResourceTypeDo resourceTypeDo;
	private List<checkboxSelectedDo> depthOfKnowledge;
	private List<checkboxSelectedDo> instructionalMethod;
	private List<checkboxSelectedDo> audience;
	private List<StandardFo> skills;
	private String ideas;
	private String performanceTasks;
	private String questions;
	private String collectionItemId;
	private String description;
	private String url;
	private String uri;
	private String publishStatus;
	private String goals;
	private int itemSequence;
	private CollectionSettingsDo settings;
	private List<CourseSubjectDo> taxonomyCourse;
	private List<CourseSubjectDo> subdomain;
	private List<CourseSubjectDo> standards;
	private String languageObjective;
	private String parentGooruOid;
	private String isLoginRequired;
	
	public FolderDo(){}
	
	public CourseSummaryDo getSummary() {
		return summary;
	}
	public void setSummary(CourseSummaryDo summary) {
		this.summary = summary;
	}
	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	/**
	 * This method is to get the gooruOid
	 */
	public String getGooruOid() {
		return gooruOid;
	}

	/**
	 * This method is to set the gooruOid
	 */
	public void setGooruOid(String gooruOid) {
		this.gooruOid = gooruOid;
	}

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
	 * This method is to get the sharing
	 */
	public String getSharing() {
		return sharing;
	}

	/**
	 * This method is to set the sharing
	 */
	public void setSharing(String sharing) {
		this.sharing = sharing;
	}

	/**
	 * This method is to get the itemCount
	 */
	public Integer getItemCount() {
		return itemCount;
	}

	/**
	 * This method is to set the itemCount
	 */
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
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
	 * This method is to get the thumbnails
	 */
	public ThumbnailDo getThumbnails() {
		return thumbnails;
	}

	/**
	 * This method is to set the thumbnails
	 */
	public void setThumbnails(ThumbnailDo thumbnails) {
		this.thumbnails = thumbnails;
	}

	/**
	 * This method is to get the collectionItems
	 */
	public List<FolderItemDo> getCollectionItems() {
		return collectionItems;
	}

	/**
	 * This method is to set the collectionItems
	 */
	public void setCollectionItems(List<FolderItemDo> collectionItems) {
		this.collectionItems = collectionItems;
	}

	/**
	 * @return the resourceFormat
	 */
	public ResourceFormatDo getResourceFormat() {
		return resourceFormat;
	}

	/**
	 * @param resourceFormat the resourceFormat to set
	 */
	public void setResourceFormat(ResourceFormatDo resourceFormat) {
		this.resourceFormat = resourceFormat;
	}

	/**
	 * This method is to get the resourceTypeDo
	 */
	public ResourceTypeDo getResourceTypeDo() {
		return resourceTypeDo;
	}

	/**
	 * This method is to set the resourceTypeDo
	 */
	public void setResourceTypeDo(ResourceTypeDo resourceTypeDo) {
		this.resourceTypeDo = resourceTypeDo;
	}

	public String getIdeas() {
		return ideas;
	}

	public void setIdeas(String ideas) {
		this.ideas = ideas;
	}

	public String getPerformanceTasks() {
		return performanceTasks;
	}

	public void setPerformanceTasks(String performanceTasks) {
		this.performanceTasks = performanceTasks;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	/**
	 * @return the collectionItemId
	 */
	public String getCollectionItemId() {
		return collectionItemId;
	}

	/**
	 * @param collectionItemId the collectionItemId to set
	 */
	public void setCollectionItemId(String collectionItemId) {
		this.collectionItemId = collectionItemId;
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
	public String getGoals() {
		return goals;
	}
	public void setGoals(String goals) {
		this.goals = goals;
	}

	public CollectionSettingsDo getSettings() {
		return settings;
	}
	public void setSettings(CollectionSettingsDo settings) {
		this.settings = settings;
	}
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}


	

	public List<checkboxSelectedDo> getDepthOfKnowledge() {
		return depthOfKnowledge;
	}

	public void setDepthOfKnowledge(List<checkboxSelectedDo> depthOfKnowledge) {
		this.depthOfKnowledge = depthOfKnowledge;
	}

	/**
	 * @return the publishStatus
	 */
	public String getPublishStatus() {
		return publishStatus;
	}

	/**
	 * @param publishStatus the publishStatus to set
	 */
	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public List<CourseSubjectDo> getTaxonomyCourse() {
		return taxonomyCourse;
	}

	public void setTaxonomyCourse(List<CourseSubjectDo> taxonomyCourse) {
		this.taxonomyCourse = taxonomyCourse;
	}

	public List<CourseSubjectDo> getSubdomain() {
		return subdomain;
	}
	public void setSubdomain(List<CourseSubjectDo> subdomain) {
		this.subdomain = subdomain;
	}

	public List<CourseSubjectDo> getStandards() {
		return standards;
	}

	public void setStandards(List<CourseSubjectDo> standards) {
		this.standards = standards;
	}

	public List<checkboxSelectedDo> getInstructionalMethod() {
		return instructionalMethod;
	}

	public void setInstructionalMethod(List<checkboxSelectedDo> instructionalMethod) {
		this.instructionalMethod = instructionalMethod;
	}

	public List<checkboxSelectedDo> getAudience() {
		return audience;
	}

	public void setAudience(List<checkboxSelectedDo> audience) {
		this.audience = audience;
	}

	public List<StandardFo> getSkills() {
		return skills;
	}

	public void setSkills(List<StandardFo> skills) {
		this.skills = skills;
	}

	public String getLanguageObjective() {
		return languageObjective;
	}

	public void setLanguageObjective(String languageObjective) {
		this.languageObjective = languageObjective;
	}

	public String getParentGooruOid() {
		return parentGooruOid;
	}

	public void setParentGooruOid(String parentGooruOid) {
		this.parentGooruOid = parentGooruOid;
	}

	public String getIsLoginRequired() {
		return isLoginRequired;
	}

	public void setIsLoginRequired(String isLoginRequired) {
		this.isLoginRequired = isLoginRequired;
	}
}