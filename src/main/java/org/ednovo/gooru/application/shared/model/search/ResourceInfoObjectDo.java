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
package org.ednovo.gooru.application.shared.model.search;

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.shared.model.content.ResourceDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ResourceInfoObjectDo implements IsSerializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private List<Map<String, String>> standards;
	private List<StandardFo> skills;
	private List<String> course;
	private ResourceDo resource;

	public ResourceInfoObjectDo() {

	}

	/**
	 * @return the standards
	 */
	public List<Map<String, String>> getStandards() {
		return standards;
	}

	/**
	 * @param standards the standards to set
	 */
	public void setStandards(List<Map<String, String>> standards) {
		this.standards = standards;
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

	/**
	 * @return the resource
	 */
	public ResourceDo getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(ResourceDo resource) {
		this.resource = resource;
	}

	public List<StandardFo> getSkills() {
		return skills;
	}

	public void setSkills(List<StandardFo> skills) {
		this.skills = skills;
	}








}