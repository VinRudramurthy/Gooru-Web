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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AssignmentDo extends TaskDo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7049532459286624939L;
	
	private String classpageId;
	private String plannedEndDate;
	
	private String createdDate;
	private String description;
	private String estimatedEffort;
	private String lastModifiedDate;
	private String modifiedBy;
	private String plannedStartDate;
	private String status;
	private String taskUid;
	private String title;
	private String typeName;
	
	
	private TaskDo task;
	private AttachToDo attachTo;
	
	public AssignmentDo(){}
	
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
	 * This method is to get the task
	 */
	public TaskDo getTask() {
		return task;
	}
	/** 
	 * This method is to set the task
	 */
	public void setTask(TaskDo task) {
		this.task = task;
	}
	/** 
	 * This method is to get the classpageId
	 */
	public String getClasspageId() {
		return classpageId;
	}
	/** 
	 * This method is to set the classpageId
	 */
	public void setClasspageId(String classpageId) {
		this.classpageId = classpageId;
	}
	/** 
	 * This method is to get the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}
	/** 
	 * This method is to set the createdDate
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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
	 * This method is to get the lastModifiedDate
	 */
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	/** 
	 * This method is to set the lastModifiedDate
	 */
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	/** 
	 * This method is to get the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/** 
	 * This method is to set the modifiedBy
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	 * This method is to get the taskUid
	 */
	public String getTaskUid() {
		return taskUid;
	}
	/** 
	 * This method is to set the taskUid
	 */
	public void setTaskUid(String taskUid) {
		this.taskUid = taskUid;
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
	 * This method is to get the attachedTo
	 */
	public AttachToDo getAttachTo() {
		return attachTo;
	}
	/** 
	 * This method is to set the attachedTo
	 */
	public void setAttachTo(AttachToDo attachTo) {
		this.attachTo = attachTo;
	}
	
}
