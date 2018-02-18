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
package org.ednovo.gooru.application.shared.model.classpages;

import java.util.ArrayList;

import org.ednovo.gooru.application.shared.model.content.ResourceFormatDo;
import org.ednovo.gooru.application.shared.model.content.ResourceTypeDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * @fileName : MasterReportDo.java
 * 
 * @Author :Gooru Team
 * 
 * @Reviewer:
 */
@JsonInclude(Include.NON_NULL)
public class PlanContentDo implements IsSerializable{

	private static final long serialVersionUID = 1L;
	private String title;
	private String gooruOid;
	private String description;
	private String type;
	private String collectionType;
	private String collectionItemId;
	private String parentGooruOid;
	private int itemSequence;
	private String sharing;
	private String url;
	private SummaryDo summary;
	private ThumbnailDo thumbnails;
	private ResourceFormatDo resourceFormat;
	private ResourceTypeDo resourceType;
	private ArrayList<PlanContentDo> items;
	private PlanProgressDo progress;
	private ArrayList<StandardFo> standards;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}
	public String getCollectionItemId() {
		return collectionItemId;
	}
	public void setCollectionItemId(String collectionItemId) {
		this.collectionItemId = collectionItemId;
	}
	public String getParentGooruOid() {
		return parentGooruOid;
	}
	public void setParentGooruOid(String parentGooruOid) {
		this.parentGooruOid = parentGooruOid;
	}
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public String getSharing() {
		return sharing;
	}
	public void setSharing(String sharing) {
		this.sharing = sharing;
	}
	public SummaryDo getSummary() {
		return summary;
	}
	public void setSummary(SummaryDo summary) {
		this.summary = summary;
	}
	public ThumbnailDo getThumbnails() {
		return thumbnails;
	}
	public void setThumbnails(ThumbnailDo thumbnails) {
		this.thumbnails = thumbnails;
	}
	public ResourceFormatDo getResourceFormat() {
		return resourceFormat;
	}
	public void setResourceFormat(ResourceFormatDo resourceFormat) {
		this.resourceFormat = resourceFormat;
	}
	public ResourceTypeDo getResourceType() {
		return resourceType;
	}
	public void setResourceType(ResourceTypeDo resourceType) {
		this.resourceType = resourceType;
	}
	public ArrayList<PlanContentDo> getItems() {
		return items;
	}
	public void setItems(ArrayList<PlanContentDo> items) {
		this.items = items;
	}
	public PlanProgressDo getProgress() {
		return progress;
	}
	public void setProgress(PlanProgressDo progress) {
		this.progress = progress;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ArrayList<StandardFo> getStandards() {
		return standards;
	}
	public void setStandards(ArrayList<StandardFo> standards) {
		this.standards = standards;
	}
}