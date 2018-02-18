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
public class CollectionMetaInfoDo implements IsSerializable {

	private static final long serialVersionUID = 8598215298047619076L;
	private List<StandardFo> standards;
	private List<String> course;
	private List<String> acknowledgement;
	private RatingDo rating;
	private List<StandardFo> skills;
	
	public CollectionMetaInfoDo(){}
	
	public RatingDo getRating() {
		return rating;
	}

	public void setRating(RatingDo rating) {
		this.rating = rating;
	}

	public void setStandards(List<StandardFo> standards) {
		this.standards = standards;
	}

	public List<StandardFo> getStandards() {
		return standards;
	}
	/**
	 * @return the course
	 */
	public List<String> getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(List<String> course) {
		this.course = course;
	}

	public List<String> getAcknowledgement() {
		return acknowledgement;
	}

	public void setAcknowledgement(List<String> acknowledgement) {
		this.acknowledgement = acknowledgement;
	}
	public List<StandardFo> getSkills() {
		return skills;
	}
	public void setSkills(List<StandardFo> skills) {
		this.skills = skills;
	}
}
