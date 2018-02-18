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
package org.ednovo.gooru.client.mvp.home.library.events;

import java.util.ArrayList;

import org.ednovo.gooru.application.shared.model.library.ConceptDo;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @fileName : SetConceptQuizDataEvent.java
 * @description : 
 * @version : 1.0
 * @date: 11-Dec-2013
 * @Author Gooru Team
 * @Reviewer:
 */
public class SetConceptQuizDataEvent extends GwtEvent<SetConceptQuizDataHandler> {
	ArrayList<ConceptDo> conceptDoList;
	Integer topicId;
	String lessonId;
	String lessonLabel;
	String lessonCode;
	String conceptId;
	String libraryGooruOid;
	
	public static final Type<SetConceptQuizDataHandler> TYPE = new Type<SetConceptQuizDataHandler>();
	
	public SetConceptQuizDataEvent(ArrayList<ConceptDo> conceptDoList, Integer topicId, String lessonId, String lessonLabel, String lessonCode, String conceptId,String libraryGooruOid) {
		this.conceptDoList = conceptDoList;
		this.topicId = topicId;
		this.lessonId = lessonId;
		this.lessonLabel = lessonLabel;
		this.lessonCode = lessonCode;
		this.conceptId = conceptId;
		this.libraryGooruOid=libraryGooruOid;
	}
	
	@Override
	public Type<SetConceptQuizDataHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SetConceptQuizDataHandler handler) {
		handler.setConceptQuizDataHandler(conceptDoList,topicId,lessonId,lessonLabel,lessonCode,conceptId,libraryGooruOid);
	}
}
