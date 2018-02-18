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
package org.ednovo.gooru.client.mvp.profilepage.event;

import org.ednovo.gooru.application.shared.model.library.ProfileLibraryDo;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @fileName : OpenLessonConceptEvent.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: Dec 4, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class OpenProfileCollectionEvent extends GwtEvent<OpenProfileCollectionHandler> {

	private ProfileLibraryDo profileLibraryDo;
	private Integer topicId;
	private String lessonId;
	private String lessonLabel;
	private String lessonCode;
	private String libraryGooruOid;
	
	public static final Type<OpenProfileCollectionHandler> TYPE = new Type<OpenProfileCollectionHandler>();
	
	/**
	 * 
	 */
	public OpenProfileCollectionEvent(ProfileLibraryDo profileLibraryDo, Integer topicId, String lessonId, String lessonLabel, String lessonCode,String libraryGooruOid) {
		setProfileLibraryDo(profileLibraryDo);
		setTopicId(topicId);
		setLessonId(lessonId);
		setLessonLabel(lessonLabel);
		setLessonCode(lessonCode);
		this.libraryGooruOid=libraryGooruOid;
	}

	@Override
	public Type<OpenProfileCollectionHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(OpenProfileCollectionHandler handler) {
		handler.openProfileCollection(getProfileLibraryDo(),getTopicId(),getLessonId(),getLessonLabel(),getLessonCode(),libraryGooruOid);
	}


	/**
	 * @return the profileLibraryDo
	 */
	public ProfileLibraryDo getProfileLibraryDo() {
		return profileLibraryDo;
	}

	/**
	 * @param profileLibraryDo the profileLibraryDo to set
	 */
	public void setProfileLibraryDo(ProfileLibraryDo profileLibraryDo) {
		this.profileLibraryDo = profileLibraryDo;
	}

	/** 
	 * This method is to get the topicId
	 */
	public Integer getTopicId() {
		return topicId;
	}

	/** 
	 * This method is to set the topicId
	 */
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	/** 
	 * This method is to get the lessonId
	 */
	public String getLessonId() {
		return lessonId;
	}

	/** 
	 * This method is to set the lessonId
	 */
	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}

	/** 
	 * This method is to get the lessonLabel
	 */
	public String getLessonLabel() {
		return lessonLabel;
	}

	/** 
	 * This method is to set the lessonLabel
	 */
	public void setLessonLabel(String lessonLabel) {
		this.lessonLabel = lessonLabel;
	}

	public String getLessonCode() {
		return lessonCode;
	}

	public void setLessonCode(String lessonCode) {
		this.lessonCode = lessonCode;
	}
	
	
	
	
}
