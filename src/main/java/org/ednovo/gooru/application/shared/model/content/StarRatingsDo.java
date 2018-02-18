package org.ednovo.gooru.application.shared.model.content;

import org.ednovo.gooru.application.shared.model.user.CreatorDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class StarRatingsDo implements IsSerializable {

	private static final long serialVersionUID = 1L;
	private String assocGooruOid;
	private String deleteRatingGooruOid=null;
	private Integer score;
	private long createdDate;
	private String freeText;
	private CreatorDo creator;
	private long lastModifiedOn;
	private SearchRatingsDo ratings;
	private Integer totalHitCount;



	public StarRatingsDo(){}

	/**
	 * @return the assocGooruOid
	 */
	public String getAssocGooruOid() {
		return assocGooruOid;
	}
	/**
	 * @param assocGooruOid the assocGooruOid to set
	 */
	public void setAssocGooruOid(String assocGooruOid) {
		this.assocGooruOid = assocGooruOid;
	}
	/**
	 * @return the deleteReactionGooruOid
	 */
	public String getDeleteRatingGooruOid() {
		return deleteRatingGooruOid;
	}
	/**
	 * @param deleteReactionGooruOid the deleteReactionGooruOid to set
	 */
	public void setDeleteRatingGooruOid(String deleteRatingGooruOid) {
		this.deleteRatingGooruOid = deleteRatingGooruOid;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return the freeText
	 */
	public String getFreeText() {
		return freeText;
	}

	/**
	 * @param freeText the freeText to set
	 */
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	public CreatorDo getCreator() {
		return creator;
	}

	public void setCreator(CreatorDo creator) {
		this.creator = creator;
	}

	/**
	 * @return the createdDate
	 */
	public long getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * This method is to get the lastModifiedOn
	 */
	public long getLastModifiedOn() {
		return lastModifiedOn;
	}

	/**
	 * This method is to set the lastModifiedOn
	 */
	public void setLastModifiedOn(long lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	/**
	 * @return the ratings
	 */
	public SearchRatingsDo getRatings() {
		return ratings;
	}

	/**
	 * @param ratings the ratings to set
	 */
	public void setRatings(SearchRatingsDo ratings) {
		this.ratings = ratings;
	}

	/**
	 * @return the totalHitCount
	 */
	public Integer getTotalHitCount() {
		return totalHitCount;
	}

	/**
	 * @param totalHitCount the totalHitCount to set
	 */
	public void setTotalHitCount(Integer totalHitCount) {
		this.totalHitCount = totalHitCount;
	}


}
