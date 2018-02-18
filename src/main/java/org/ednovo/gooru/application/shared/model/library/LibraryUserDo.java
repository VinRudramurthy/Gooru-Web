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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class LibraryUserDo implements IsSerializable {
	
	private static final long serialVersionUID = 2411080367742513414L;
	private String firstName;
	private String username;
	private String gooruUId;
	private String lastName;
	private String gender;
	private ArrayList<CourseDo> courses;
    private String isOwner;
    private String partnerName;
    private String partnerUrl;
    private String displayName;
    
    public LibraryUserDo(){}
	
	/** 
	 * This method is to get the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/** 
	 * This method is to set the firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/** 
	 * This method is to get the username
	 */
	public String getUsername() {
		return username;
	}
	/** 
	 * This method is to set the username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/** 
	 * This method is to get the gooruUId
	 */
	public String getGooruUId() {
		return gooruUId;
	}
	/** 
	 * This method is to set the gooruUId
	 */
	public void setGooruUId(String gooruUId) {
		this.gooruUId = gooruUId;
	}
	/** 
	 * This method is to get the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/** 
	 * This method is to set the lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/** 
	 * This method is to get the courses
	 */
	public ArrayList<CourseDo> getCourses() {
		return courses;
	}
	/** 
	 * This method is to set the courses
	 */
	public void setCourses(ArrayList<CourseDo> courses) {
		this.courses = courses;
	}
	/** 
	 * This method is to get the gender
	 */
	public String getGender() {
		return gender;
	}
	/** 
	 * This method is to set the gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/** 
	 * This method is to get the isOwner
	 */
	public String getIsOwner() {
		return isOwner;
	}
	/** 
	 * This method is to set the isOwner
	 */
	public void setIsOwner(String isOwner) {
		this.isOwner = isOwner;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerUrl() {
		return partnerUrl;
	}

	public void setPartnerUrl(String partnerUrl) {
		this.partnerUrl = partnerUrl;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}