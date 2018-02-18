package org.ednovo.gooru.application.shared.model.player;


import com.google.gwt.user.client.rpc.IsSerializable;

public class CommentsUserInfoDo implements IsSerializable {

	private static final long serialVersionUID = 8598215298047619076L;
	private String createdOn;
	private String firstName;
	private String gooruUId;
	private Boolean isDeleted;	
	private String lastName;
	private String usernameDisplay;
	private String username;
	public CommentsUserInfoDo(){}
	
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getGooruUId() {
		return gooruUId;
	}
	public void setGooruUId(String gooruUId) {
		this.gooruUId = gooruUId;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsernameDisplay() {
		return usernameDisplay;
	}
	public void setUsernameDisplay(String usernameDisplay) {
		this.usernameDisplay = usernameDisplay;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}