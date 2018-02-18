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

import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.user.UserDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class CollectionItemDo extends ResourceSearchResultDo implements IsSerializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6903126019265446978L;

	private String collectionItemId;
	private CollectionDo collection;
	private ResourceDo resource;
	private int resourceCount = 0;
	private CollectionQuestionItemDo collectionQuestionItemDo;
	private String itemType;
	private Integer itemSequence;
	private String narration;
	private String narrationType;
	private String start;
	private String stop;
	private Integer totalPages;
	private List<String> course;
	private Integer totalHitCount;
	private Integer statusCode;
	private ResourceDo questionInfo;
	private String parentGooruOid;
	private RatingDo rating;

	public CollectionItemDo(){
		
	}
	
	
	
	public String getParentGooruOid() {
		return parentGooruOid;
	}
	public void setParentGooruOid(String parentGooruOid) {
		this.parentGooruOid = parentGooruOid;
	}

	/**
	 * This method is to get the totalHitCount
	 */
	public Integer getTotalHitCount() {
		return totalHitCount;
	}

	/**
	 * This method is to set the totalHitCount
	 */
	public void setTotalHitCount(Integer totalHitCount) {
		this.totalHitCount = totalHitCount;
	}

	public String getCollectionItemId() {
		return collectionItemId;
	}

	public void setCollectionItemId(String collectionItemId) {
		this.collectionItemId = collectionItemId;
	}

	public CollectionDo getCollection() {
		return collection;
	}

	public void setCollection(CollectionDo collectionDo) {
		this.collection = collectionDo;
	}

	public ResourceDo getResource() {
		return resource;
	}

	public void setResource(ResourceDo resourceDo) {
		this.resource = resourceDo;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Integer getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getNarrationType() {
		return narrationType;
	}

	public void setNarrationType(String narrationType) {
		this.narrationType = narrationType;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public CollectionQuestionItemDo getCollectionQuestionItemDo() {
		return collectionQuestionItemDo;
	}

	public void setCollectionQuestionItemDo(CollectionQuestionItemDo collectionQuestionItemDo) {
		this.collectionQuestionItemDo = collectionQuestionItemDo;
	}

	
	



	@Override
	public String getGooruOid() {
		return getResource().getGooruOid();
	}


	public String getDescription() {
		return getResource().getGoals();
	}

	public String getResourceTitle() {
		return getResource().getTitle();
	}

	public String getUrl() {
		if(getResource().getThumbnails()!=null){
			 return getResource().getThumbnails().getUrl();
		}else{
			return null;
		}
    }

	public String getViews() {
		return getResource().getViews();
	}

	public Integer getVotesUp() {
		return getResource().getVotesUp();
	}

	public UserDo getCreator() {
		return getResource().getCreator();
	}

	public LicenseDo getLicense() {
		return getResource().getLicense();
	}

	public String getAssetURI() {
        return getResource().getAssetURI();
    }

	public String getGrade() {
		return getResource().getGrade();
	}

	public List<String> getCourse() {
		return course;
	}

	public void setCourse(List<String> course) {
		this.course = course;
	}

	public int getResourceCount() {
		return resourceCount;
	}

	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}

	public RatingDo getRating() {
		return rating;
	}

	public void setRating(RatingDo rating) {
		this.rating = rating;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public ResourceDo getQuestionInfo() {
		return questionInfo;
	}

	public void setQuestionInfo(ResourceDo questionInfo) {
		this.questionInfo = questionInfo;
	}
	
	public String getFolder() {
        return getResource().getFolder();
    }

}
