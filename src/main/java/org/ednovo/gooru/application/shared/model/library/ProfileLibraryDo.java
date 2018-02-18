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
package org.ednovo.gooru.application.shared.model.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.shared.model.content.CollectionMetaInfoDo;
import org.ednovo.gooru.application.shared.model.content.ResourceFormatDo;
import org.ednovo.gooru.application.shared.model.content.ResourceSourceDo;
import org.ednovo.gooru.application.shared.model.content.ResourceTypeDo;
import org.ednovo.gooru.application.shared.model.content.SearchRatingsDo;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class ProfileLibraryDo implements IsSerializable{

	private static final long serialVersionUID = -3298220423508874309L;

	private String gooruOid;
	private String title;
	private String sharing;
	private Integer itemCount;
	private String type;
	private ThumbnailDo thumbnails;
	private ArrayList<ProfileLibraryDo> collectionItems;
	private ArrayList<ConceptDo> collections;
	private ResourceFormatDo resourceFormat;
	private String collectionItemId;
	private Integer itemSequence;
	private ResourceSourceDo resourceSource;

	private String collectionType;

	//mandatory variables to convert PartnerFolderDo to ConceptDo
	private String goals;
	private LibraryUserDo user;
	private CollectionMetaInfoDo metaInfo;
	private List<Map<String, String>> standards;
	private ProfileLibraryDo resource;
	private ResourceTypeDo resourceType;
	private String url;
	private SearchRatingsDo ratings;
	private String ideas;
	private String performanceTasks;
	private String questions;
	private ArrayList<String> publisher;

	private String parentGooruOid;

	public ProfileLibraryDo(){}

	/**
	 * @return the gooruOid
	 */
	public String getGooruOid() {
		return gooruOid;
	}

	/**
	 * @param gooruOid the gooruOid to set
	 */
	public void setGooruOid(String gooruOid) {
		this.gooruOid = gooruOid;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the sharing
	 */
	public String getSharing() {
		return sharing;
	}

	/**
	 * @param sharing the sharing to set
	 */
	public void setSharing(String sharing) {
		this.sharing = sharing;
	}

	/**
	 * @return the itemCount
	 */
	public Integer getItemCount() {
		return itemCount;
	}

	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the thumbnails
	 */
	public ThumbnailDo getThumbnails() {
		return thumbnails;
	}

	/**
	 * @param thumbnails the thumbnails to set
	 */
	public void setThumbnails(ThumbnailDo thumbnails) {
		this.thumbnails = thumbnails;
	}

	/**
	 * @return the collectionItems
	 */
	public ArrayList<ProfileLibraryDo> getCollectionItems() {
		return collectionItems;
	}

	/**
	 * @param collectionItems the collectionItems to set
	 */
	public void setCollectionItems(ArrayList<ProfileLibraryDo> collectionItems) {
		this.collectionItems = collectionItems;
	}

	/**
	 * @return the collections
	 */
	public ArrayList<ConceptDo> getCollections() {
		return collections;
	}

	/**
	 * @param collections the collections to set
	 */
	public void setCollections(ArrayList<ConceptDo> collections) {
		this.collections = collections;
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

	/**
	 * @return the itemSequence
	 */
	public Integer getItemSequence() {
		return itemSequence;
	}

	/**
	 * @param itemSequence the itemSequence to set
	 */
	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}

	/**
	 * @return the goals
	 */
	public String getGoals() {
		return goals;
	}

	/**
	 * @param goals the goals to set
	 */
	public void setGoals(String goals) {
		this.goals = goals;
	}

	/**
	 * @return the user
	 */
	public LibraryUserDo getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(LibraryUserDo user) {
		this.user = user;
	}

	/**
	 * @return the metaInfo
	 */
	public CollectionMetaInfoDo getMetaInfo() {
		return metaInfo;
	}

	/**
	 * @param metaInfo the metaInfo to set
	 */
	public void setMetaInfo(CollectionMetaInfoDo metaInfo) {
		this.metaInfo = metaInfo;
	}

	/**
	 * @return the standards
	 */
	public List<Map<String, String>> getStandards() {
		return standards;
	}

	/**
	 * @param standards the standards to set
	 */
	public void setStandards(List<Map<String, String>> standards) {
		this.standards = standards;
	}

	/**
	 * @return the resourceSource
	 */
	public ResourceSourceDo getResourceSource() {
		return resourceSource;
	}

	/**
	 * @param resourceSource the resourceSource to set
	 */
	public void setResourceSource(ResourceSourceDo resourceSource) {
		this.resourceSource = resourceSource;
	}

	/**
	 * @return the resource
	 */
	public ProfileLibraryDo getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(ProfileLibraryDo resource) {
		this.resource = resource;
	}

	/**
	 * @return the resourceTypeDo
	 */
	public ResourceTypeDo getResourceTypeDo() {
		return resourceType;
	}

	/**
	 * @param resourceTypeDo the resourceTypeDo to set
	 */
	public void setResourceTypeDo(ResourceTypeDo resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public SearchRatingsDo getRatings() {
		return ratings;
	}

	public void setRatings(SearchRatingsDo ratings) {
		this.ratings = ratings;
	}

	/**
	 * @return the ideas
	 */
	public String getIdeas() {
		return ideas;
	}

	/**
	 * @param ideas the ideas to set
	 */
	public void setIdeas(String ideas) {
		this.ideas = ideas;
	}

	/**
	 * @return the performanceTasks
	 */
	public String getPerformanceTasks() {
		return performanceTasks;
	}

	/**
	 * @param performanceTasks the performanceTasks to set
	 */
	public void setPerformanceTasks(String performanceTasks) {
		this.performanceTasks = performanceTasks;
	}

	/**
	 * @return the questions
	 */
	public String getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public ArrayList<String> getPublisher() {
		return publisher;
	}

	public void setPublisher(ArrayList<String> publisher) {
		this.publisher = publisher;
	}

	public String getParentGooruOid() {
		return parentGooruOid;
	}

	public void setParentGooruOid(String parentGooruOid) {
		this.parentGooruOid = parentGooruOid;
	}

	/**
	 * @return the collectionType
	 */
	public String getCollectionType() {
		return collectionType;
	}

	/**
	 * @param collectionType the collectionType to set
	 */
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}


}