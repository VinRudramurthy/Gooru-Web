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
package org.ednovo.gooru.application.shared.model.analytics;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * @fileName : CollectionProgressDataDo.java
 * 
 * 
 * @Author :Gooru Team
 * 
 * @Reviewer:
 */
@JsonInclude(Include.NON_NULL)
public class CollectionProgressDataDo implements IsSerializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String category;
	private String resourceFormat;
	private String title;
	private int status;
	private String description;
	private int itemSequence;
	private String type;
	private int apiStatusCode;
	private ArrayList<UserDataDo> userData;
	private ArrayList<MetaDataDo> metaData;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getApiStatusCode() {
		return apiStatusCode;
	}
	public void setApiStatusCode(int apiStatusCode) {
		this.apiStatusCode = apiStatusCode;
	}
	public ArrayList<UserDataDo> getUserData() {
		return userData;
	}
	public void setUserData(ArrayList<UserDataDo> userData) {
		this.userData = userData;
	}
	public ArrayList<MetaDataDo> getMetaData() {
		return metaData;
	}
	public void setMetaData(ArrayList<MetaDataDo> metaData) {
		this.metaData = metaData;
	}
	public String getResourceFormat() {
		return resourceFormat;
	}
	public void setResourceFormat(String resourceFormat) {
		this.resourceFormat = resourceFormat;
	}
}
