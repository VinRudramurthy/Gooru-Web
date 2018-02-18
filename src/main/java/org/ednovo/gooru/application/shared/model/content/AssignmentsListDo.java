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
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class AssignmentsListDo implements IsSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3223243025838735212L;
	private List<AssignmentsSearchDo> searchResults;
	private String category;
	private Integer totalHitCount;
	
	private Integer pageNum;
	private Integer pageSize;
	
	private Integer pos;
	
	private String classpageId;
	
	public AssignmentsListDo(){}
	
	/** 
	 * This method is to get the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}
	/** 
	 * This method is to set the pageNum
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	/** 
	 * This method is to get the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	/** 
	 * This method is to set the pageSize
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	

	/** 
	 * This method is to get the category
	 */
	public String getCategory() {
		return category;
	}
	/** 
	 * This method is to set the category
	 */
	public void setCategory(String category) {
		this.category = category;
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
	 * This method is to get the searchResults
	 */
	public List<AssignmentsSearchDo> getSearchResults() {
		return searchResults;
	}
	/** 
	 * This method is to set the searchResults
	 */
	public void setSearchResults(List<AssignmentsSearchDo> searchResults) {
		this.searchResults = searchResults;
	}
	/** 
	 * This method is to get the pos
	 */
	public Integer getPos() {
		return pos;
	}
	/** 
	 * This method is to set the pos
	 */
	public void setPos(Integer pos) {
		this.pos = pos;
	}
}
