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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class JSONStandardsDo implements IsSerializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6740181817732454396L;
	
	private String label;
	private Integer courseId;
	private Integer subjectId;
	private String link;
	private String extraParms;
	

	public JSONStandardsDo(){}
	
	
	/** 
	 * This method is to get the label
	 */
	public String getLabel() {
		return label;
	}


	/** 
	 * This method is to set the label
	 */
	public void setLabel(String label) {
		this.label = label;
	}


	/** 
	 * This method is to get the link
	 */
	public String getLink() {
		return link;
	}


	/** 
	 * This method is to set the link
	 */
	public void setLink(String link) {
		this.link = link;
	}


	/** 
	 * This method is to get the extraParms
	 */
	public String getExtraParms() {
		return extraParms;
	}


	/** 
	 * This method is to set the extraParms
	 */
	public void setExtraParms(String extraParms) {
		this.extraParms = extraParms;
	}


	/** 
	 * This method is to get the courseId
	 */
	public Integer getCourseId() {
		return courseId;
	}


	/** 
	 * This method is to set the courseId
	 */
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}


	/** 
	 * This method is to get the subjectId
	 */
	public Integer getSubjectId() {
		return subjectId;
	}


	/** 
	 * This method is to set the subjectId
	 */
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}	
}
