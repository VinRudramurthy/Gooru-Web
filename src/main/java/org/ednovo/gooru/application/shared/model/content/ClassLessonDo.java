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


/**
 * @fileName : ClassLessonDo.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 13-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer: 
 */
@JsonInclude(Include.NON_NULL)
public class ClassLessonDo implements IsSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4627991289751808952L;
	
	public ClassLessonDo(){}
	
	private long contentId;
	
	private String title;
	
	private String gooruOid;
	
	private boolean visibility;
	
	private long collectionId;
	
	private List<ClassLessonDo> items;

	/** 
	 * This method is to get the contentId
	 */
	public long getContentId() {
		return contentId;
	}

	/** 
	 * This method is to set the contentId
	 */
	public void setContentId(long contentId) {
		this.contentId = contentId;
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
	 * This method is to get the visibility
	 */
	public boolean isVisibility() {
		return visibility;
	}

	/** 
	 * This method is to set the visibility
	 */
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	/** 
	 * This method is to get the collectionId
	 */
	public long getCollectionId() {
		return collectionId;
	}

	/** 
	 * This method is to set the collectionId
	 */
	public void setCollectionId(long collectionId) {
		this.collectionId = collectionId;
	}

	/** 
	 * This method is to get the items
	 */
	public List<ClassLessonDo> getItems() {
		return items;
	}

	/** 
	 * This method is to set the items
	 */
	public void setItems(List<ClassLessonDo> items) {
		this.items = items;
	}

}
