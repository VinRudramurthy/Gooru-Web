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
/**
 *
 */
package org.ednovo.gooru.application.shared.model.search;

import java.util.List;

import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;


/**
 * @author Search Team
 *
 */
public class CollectionSearchResultDo extends ResourceSearchResultDo {


	/**
	 *
	 */
	private static final long serialVersionUID = 7126217315774345771L;

	private int resourceCount = 0;

	private int questionCount = 0;

	private String creatorName;

	private int hasAddedToShelf = 0;

	private Integer collaboratorCount;

	private Integer onlyResourceCount;

	private String collectionType;

	private Integer scollectionRemixCount;

	private List<CollectionItemDo> collectionItems;

	public CollectionSearchResultDo() {

	}

	public int getResourceCount() {
		return resourceCount;
	}

	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public int getHasAddedToShelf() {
		return hasAddedToShelf;
	}

	public void setHasAddedToShelf(int hasAddedToShelf) {
		this.hasAddedToShelf = hasAddedToShelf;
	}

	public Integer getCollaboratorCount() {
		return collaboratorCount;
	}

	public void setCollaboratorCount(Integer collaboratorCount) {
		this.collaboratorCount = collaboratorCount;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	public Integer getOnlyResourceCount() {
		return onlyResourceCount;
	}

	public void setOnlyResourceCount(Integer onlyResourceCount) {
		this.onlyResourceCount = onlyResourceCount;
	}

	public List<CollectionItemDo> getCollectionItems() {
		return collectionItems;
	}

	public void setCollectionItems(List<CollectionItemDo> collectionItems) {
		this.collectionItems = collectionItems;
	}

	/**
	 * @return the collectionType
	 */
	public String getCollectionType() {
		return collectionType;
	}

	/**
	 * @param collectionType the collectionType to set
	 */
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	/**
	 * @return the scollectionRemixCount
	 */
	public Integer getScollectionRemixCount() {
		return scollectionRemixCount;
	}

	/**
	 * @param scollectionRemixCount the scollectionRemixCount to set
	 */
	public void setScollectionRemixCount(Integer scollectionRemixCount) {
		this.scollectionRemixCount = scollectionRemixCount;
	}



}
