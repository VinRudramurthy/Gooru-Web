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
package org.ednovo.gooru.application.shared.model.user;


import com.google.gwt.user.client.rpc.IsSerializable; 
import java.util.List;

import org.ednovo.gooru.application.shared.model.library.CourseDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserMetaDo implements IsSerializable {

	private static final long serialVersionUID = 8598215298047619076L;

	private UserTaxonomyPreferenceDo taxonomyPreference;
	private UserSummaryDo summary;
	private String grade;
	private List<CourseDo> course;

	public UserMetaDo(){}

	public UserTaxonomyPreferenceDo getTaxonomyPreference() {
		return taxonomyPreference;
	}

	public void setTaxonomyPreference(UserTaxonomyPreferenceDo taxonomyPreference) {
		this.taxonomyPreference = taxonomyPreference;
	}

	public UserSummaryDo getSummary() {
		return summary;
	}

	public void setSummary(UserSummaryDo summary) {
		this.summary = summary;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public List<CourseDo> getCourse() {
		return course;
	}

	public void setCourse(List<CourseDo> course) {
		this.course = course;
	}



}