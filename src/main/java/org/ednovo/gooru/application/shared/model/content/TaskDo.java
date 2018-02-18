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

import org.ednovo.gooru.application.shared.model.user.UserDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class TaskDo implements IsSerializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 3944569416998889491L;

	private String title;
	private String typeName;
	private String description;
	private String plannedEndDate;
	private String plannedStartDate;


//	private ArrayList contentPermissions;
	private String createdOn;
	private UserDo creator;
	private String estimatedEffort;
	private String gooruOid;
	private String lastModified;
	private String lastUpdatedUserUid;
	private String sharing;
	private String status;
//	private ArrayList taxonomySet[]
	private UserDo user;

	public TaskDo(){}


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
	 * This method is to get the typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * This method is to set the typeName
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	 * This method is to get the plannedEndDate
	 */
	public String getPlannedEndDate() {
		return plannedEndDate;
	}
	/**
	 * This method is to set the plannedEndDate
	 */
	public void setPlannedEndDate(String plannedEndDate) {
		this.plannedEndDate = plannedEndDate;
	}
	/**
	 * This method is to get the plannedStartDate
	 */
	public String getPlannedStartDate() {
		return plannedStartDate;
	}
	/**
	 * This method is to set the plannedStartDate
	 */
	public void setPlannedStartDate(String plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}
	/**
	 * This method is to get the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}
	/**
	 * This method is to set the createdOn
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	/**
	 * This method is to get the creator
	 */
	public UserDo getCreator() {
		return creator;
	}
	/**
	 * This method is to set the creator
	 */
	public void setCreator(UserDo creator) {
		this.creator = creator;
	}
	/**
	 * This method is to get the estimatedEffort
	 */
	public String getEstimatedEffort() {
		return estimatedEffort;
	}
	/**
	 * This method is to set the estimatedEffort
	 */
	public void setEstimatedEffort(String estimatedEffort) {
		this.estimatedEffort = estimatedEffort;
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
	 * This method is to get the lastUpdatedUserUid
	 */
	public String getLastUpdatedUserUid() {
		return lastUpdatedUserUid;
	}
	/**
	 * This method is to set the lastUpdatedUserUid
	 */
	public void setLastUpdatedUserUid(String lastUpdatedUserUid) {
		this.lastUpdatedUserUid = lastUpdatedUserUid;
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
	 * This method is to get the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * This method is to set the status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * This method is to get the user
	 */
	public UserDo getUser() {
		return user;
	}
	/**
	 * This method is to set the user
	 */
	public void setUser(UserDo user) {
		this.user = user;
	}

}
