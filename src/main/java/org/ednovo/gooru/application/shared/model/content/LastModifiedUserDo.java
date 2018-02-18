package org.ednovo.gooru.application.shared.model.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class LastModifiedUserDo implements IsSerializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1953593166467608116L;

	private String username;
	private String gooruid;
	private String modifiedDate;
    
    public LastModifiedUserDo(){}

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
	 * This method is to get the gooruid
	 */
	public String getGooruid() {
		return gooruid;
	}

	/** 
	 * This method is to set the gooruid
	 */
	public void setGooruid(String gooruid) {
		this.gooruid = gooruid;
	}

	/** 
	 * This method is to get the modifiedDate
	 */
	public String getModifiedDate() {
		return modifiedDate;
	}

	/** 
	 * This method is to set the modifiedDate
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	
}
