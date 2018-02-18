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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.shared.model.content.CollectionMetaInfoDo;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class ConceptDo implements IsSerializable {

	private static final long serialVersionUID = 2411080367742513414L;
	private String goals;
	private String title;
	private String gooruOid;
	private String id;
	private ArrayList<LibraryCollectionItemDo> collectionItems;
	private LibraryUserDo user;
	private ThumbnailDo thumbnails;
	private CollectionMetaInfoDo metaInfo;
	private List<Map<String, String>> standards;
	private String collectionType;
	private Integer codeId;
	private String label;
	private String code;
	private ArrayList<ConceptDo> collection;
	private Integer itemCount;

	public ConceptDo(){}

	public String getGoals() {
		return goals;
	}
	public void setGoals(String goals) {
		this.goals = goals;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGooruOid() {
		return gooruOid;
	}
	public void setGooruOid(String gooruOid) {
		this.gooruOid = gooruOid;
	}
	/**
	 * This method is to get the collectionItems
	 */
	public ArrayList<LibraryCollectionItemDo> getCollectionItems() {
		return collectionItems;
	}
	/**
	 * This method is to set the collectionItems
	 */
	public void setCollectionItems(ArrayList<LibraryCollectionItemDo> collectionItems) {
		this.collectionItems = collectionItems;
	}
	/**
	 * This method is to get the user
	 */
	public LibraryUserDo getUser() {
		return user;
	}
	/**
	 * This method is to set the user
	 */
	public void setUser(LibraryUserDo user) {
		this.user = user;
	}
	/**
	 * This method is to get the thumbnails
	 */
	public ThumbnailDo getThumbnails() {
		return thumbnails;
	}
	/**
	 * This method is to set the thumbnails
	 */
	public void setThumbnails(ThumbnailDo thumbnails) {
		this.thumbnails = thumbnails;
	}
	/**
	 * This method is to get the metaInfo
	 */
	public CollectionMetaInfoDo getMetaInfo() {
		return metaInfo;
	}
	/**
	 * This method is to set the metaInfo
	 */
	public void setMetaInfo(CollectionMetaInfoDo metaInfo) {
		this.metaInfo = metaInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Map<String, String>> getStandards() {
		return standards;
	}

	public void setStandards(List<Map<String, String>> standards) {
		this.standards = standards;
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
	 * @return the codeId
	 */
	public Integer getCodeId() {
		return codeId;
	}

	/**
	 * @param codeId the codeId to set
	 */
	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the collection
	 */
	public ArrayList<ConceptDo> getCollection() {
		return collection;
	}

	/**
	 * @param collection the collection to set
	 */
	public void setCollection(ArrayList<ConceptDo> collection) {
		this.collection = collection;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the itemCount
	 */
	public Integer getItemCount() {
		return itemCount;
	}

	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}


}