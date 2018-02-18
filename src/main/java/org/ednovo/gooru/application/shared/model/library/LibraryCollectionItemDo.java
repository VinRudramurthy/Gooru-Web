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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class LibraryCollectionItemDo implements IsSerializable {
	
	private static final long serialVersionUID = 2411080367742513414L;
	private String collectionItemId;
	private Integer itemSequence;
	private LibraryResourceDo resource;

	public LibraryCollectionItemDo(){}
	/** 
	 * This method is to get the collectionItemId
	 */
	public String getCollectionItemId() {
		return collectionItemId;
	}
	/** 
	 * This method is to set the collectionItemId
	 */
	public void setCollectionItemId(String collectionItemId) {
		this.collectionItemId = collectionItemId;
	}
	/** 
	 * This method is to get the itemSequence
	 */
	public Integer getItemSequence() {
		return itemSequence;
	}
	/** 
	 * This method is to set the itemSequence
	 */
	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}
	/** 
	 * This method is to get the resource
	 */
	public LibraryResourceDo getResource() {
		return resource;
	}
	/** 
	 * This method is to set the resource
	 */
	public void setResource(LibraryResourceDo resource) {
		this.resource = resource;
	}
}
