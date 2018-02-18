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

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class NewResourceDo extends ResourceDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6944012321010679229L;
	/**
	 * 
	 */
	private String id;
	private String url;
	private String title;
	private String description;
	private String category;
	private String thumbnailImgUrl;
	private Integer stop;
	private ResourceFormatDo resourceFormat;
	private ArrayList<checkboxSelectedDo> educationalUse;
	private ArrayList<checkboxSelectedDo> momentsOfLearning;
	private List<Integer> momentsOfLearningIds;
	private List<Integer> depthOfKnowledgeIds;
	private List<Integer> skillIds;
	private List<Integer> standardIds;
	private List<Integer> educationalUseIds; 
	private List<Integer> accessHazardIds;
	private List<Integer> mediaFeatureIds;

	private ArrayList<String> host;
	private String thumbnail;
	private String mediaType;
	
	
	
	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public List<Integer> getMomentsOfLearningIds() {
		return momentsOfLearningIds;
	}

	public void setMomentsOfLearningIds(List<Integer> momentsOfLearningIds) {
		this.momentsOfLearningIds = momentsOfLearningIds;
	}

	public List<Integer> getDepthOfKnowledgeIds() {
		return depthOfKnowledgeIds;
	}

	public void setDepthOfKnowledgeIds(List<Integer> depthOfKnowledgeIds) {
		this.depthOfKnowledgeIds = depthOfKnowledgeIds;
	}

	public List<Integer> getSkillIds() {
		return skillIds;
	}

	public void setSkillIds(List<Integer> skillIds) {
		this.skillIds = skillIds;
	}

	public List<Integer> getStandardIds() {
		return standardIds;
	}

	public void setStandardIds(List<Integer> standardIds) {
		this.standardIds = standardIds;
	}

	public List<Integer> getEducationalUseIds() {
		return educationalUseIds;
	}

	public void setEducationalUseIds(List<Integer> educationalUseIds) {
		this.educationalUseIds = educationalUseIds;
	}

	/** 
	 * This method is to get the resourceFormat
	 */
	public ResourceFormatDo getResourceFormat() {
		return resourceFormat;
	}

	/** 
	 * This method is to set the resourceFormat
	 */
	public void setResourceFormat(ResourceFormatDo resourceFormat) {
		this.resourceFormat = resourceFormat;
	}

	public NewResourceDo(){}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getThumbnailImgUrl() {
		return thumbnailImgUrl;
	}
	public void setThumbnailImgUrl(String thumbnailImgUrl) {
		this.thumbnailImgUrl = thumbnailImgUrl;
	}
	/** 
	 * This method is to get the stop
	 */
	public Integer getStop() {
		return stop;
	}
	/** 
	 * This method is to set the stop
	 */
	public void setStop(Integer stop) {
		this.stop = stop;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	
	public ArrayList<checkboxSelectedDo> getEducationalUse() {
		return educationalUse;
	}

	public void setEducationalUse(ArrayList<checkboxSelectedDo> educationalUse) {
		this.educationalUse = educationalUse;
	}

	public ArrayList<checkboxSelectedDo> getMomentsOfLearning() {
		return momentsOfLearning;
	}

	public void setMomentsOfLearning(ArrayList<checkboxSelectedDo> momentsOfLearning) {
		this.momentsOfLearning = momentsOfLearning;
	}

	public ArrayList<String> getHost() {
		return host;
	}

	public void setHost(ArrayList<String> host) {
		this.host = host;
	}

	public List<Integer> getAccessHazardIds() {
		return accessHazardIds;
	}

	public void setAccessHazardIds(List<Integer> accessHazardIds) {
		this.accessHazardIds = accessHazardIds;
	}

	public List<Integer> getMediaFeatureIds() {
		return mediaFeatureIds;
	}

	public void setMediaFeatureIds(List<Integer> mediaFeatureIds) {
		this.mediaFeatureIds = mediaFeatureIds;
	}

	

}
