
/**
 * 
*/
package org.ednovo.gooru.application.shared.model.content;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @fileName : CollaboratorsDo.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: Jan 27, 2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class CollaboratorsDo implements IsSerializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = -6635116419130714954L;

	private String gooruUid;
	private String gooruOid;
	private String username;
	private String emailId;
	private String status;
	private String profileImageUrl;
	private String associatedDate;
	private String firstName;
	private String lastName;
	private String gooruUId;
	
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

	//Specific to invite students in Class.
	private String email;
	
	
	public CollaboratorsDo(){}
	
	/** 
	 * This method is to get the gooruUid
	 */
	public String getGooruUid() {
		return gooruUid;
	}

	/** 
	 * This method is to set the gooruUid
	 */
	public void setGooruUid(String gooruUid) {
		this.gooruUid = gooruUid;
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
	 * This method is to get the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/** 
	 * This method is to set the emailId
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/** 
	 * This method is to get the status
	 */
	public String getStatus() {
		return status;
	}

	/** 
	 * This method is to set the status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/** 
	 * This method is to get the profileImageUrl
	 */
	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	/** 
	 * This method is to set the profileImageUrl
	 */
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	/** 
	 * This method is to get the associatedDate
	 */
	public String getAssociatedDate() {
		return associatedDate;
	}

	/** 
	 * This method is to set the associatedDate
	 */
	public void setAssociatedDate(String associatedDate) {
		this.associatedDate = associatedDate;
	}


	/** 
	 * This method is to get the email
	 */
	public String getEmail() {
		return email;
	}

	/** 
	 * This method is to set the email
	 */
	public void setEmail(String email) {
		this.email = email;
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
	
}
