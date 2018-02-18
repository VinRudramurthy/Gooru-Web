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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TaskResourceSearchDo extends TaskDo {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9147644387870432439L;
	
	private List<ResourceDo> resource;
	private TaskDo task;
	private Integer sequence;
	private String associationDate;
	private String taskResourceAssocUid;
	public TaskResourceSearchDo(){}
	/** 
	 * This method is to get the resource
	 */
	public List<ResourceDo> getResource() {
		return resource;
	}
	/** 
	 * This method is to set the resource
	 */
	public void setResource(List<ResourceDo> resource) {
		this.resource = resource;
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
	 * This method is to get the sequence
	 */
	public Integer getSequence() {
		return sequence;
	}
	/** 
	 * This method is to set the sequence
	 */
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	/** 
	 * This method is to get the associationDate
	 */
	public String getAssociationDate() {
		return associationDate;
	}
	/** 
	 * This method is to set the associationDate
	 */
	public void setAssociationDate(String associationDate) {
		this.associationDate = associationDate;
	}
	/** 
	 * This method is to get the taskResourceAssocUid
	 */
	public String getTaskResourceAssocUid() {
		return taskResourceAssocUid;
	}
	/** 
	 * This method is to set the taskResourceAssocUid
	 */
	public void setTaskResourceAssocUid(String taskResourceAssocUid) {
		this.taskResourceAssocUid = taskResourceAssocUid;
	}

	
	
}
