package org.ednovo.gooru.application.shared.model.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class AssignmentParentDo implements IsSerializable{

	private static final long serialVersionUID = 1L;

	public AssignmentParentDo(){}

	private String pathwayTitle;
	private String classTitle;
	private String narration;
	private String classGooruOid;
	private String collectionGooruOid;
	private String collectionTitle;
	private Long plannedEndDate;
	private Boolean isRequired;
	private String pathwayGooruOid;

	/**
	 * @return the pathwayTitle
	 */
	public String getPathwayTitle() {
		return pathwayTitle;
	}
	/**
	 * @param pathwayTitle the pathwayTitle to set
	 */
	public void setPathwayTitle(String pathwayTitle) {
		this.pathwayTitle = pathwayTitle;
	}
	/**
	 * @return the classTitle
	 */
	public String getClassTitle() {
		return classTitle;
	}
	/**
	 * @param classTitle the classTitle to set
	 */
	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}
	/**
	 * @return the narration
	 */
	public String getNarration() {
		return narration;
	}
	/**
	 * @param narration the narration to set
	 */
	public void setNarration(String narration) {
		this.narration = narration;
	}
	/**
	 * @return the classGooruOid
	 */
	public String getClassGooruOid() {
		return classGooruOid;
	}
	/**
	 * @param classGooruOid the classGooruOid to set
	 */
	public void setClassGooruOid(String classGooruOid) {
		this.classGooruOid = classGooruOid;
	}
	/**
	 * @return the collectionGooruOid
	 */
	public String getCollectionGooruOid() {
		return collectionGooruOid;
	}
	/**
	 * @param collectionGooruOid the collectionGooruOid to set
	 */
	public void setCollectionGooruOid(String collectionGooruOid) {
		this.collectionGooruOid = collectionGooruOid;
	}
	/**
	 * @return the collectionTitle
	 */
	public String getCollectionTitle() {
		return collectionTitle;
	}
	/**
	 * @param collectionTitle the collectionTitle to set
	 */
	public void setCollectionTitle(String collectionTitle) {
		this.collectionTitle = collectionTitle;
	}
	/**
	 * @return the plannedEndDate
	 */
	public Long getPlannedEndDate() {
		return plannedEndDate;
	}
	/**
	 * @param plannedEndDate the plannedEndDate to set
	 */
	public void setPlannedEndDate(Long plannedEndDate) {
		this.plannedEndDate = plannedEndDate;
	}
	public Boolean getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}
	/**
	 * @return the pathwayGooruOid
	 */
	public String getPathwayGooruOid() {
		return pathwayGooruOid;
	}
	/**
	 * @param pathwayGooruOid the pathwayGooruOid to set
	 */
	public void setPathwayGooruOid(String pathwayGooruOid) {
		this.pathwayGooruOid = pathwayGooruOid;
	}

}
