
/**
 * 
*/
package org.ednovo.gooru.application.shared.model.content;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * @fileName : MetaDO.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 05-Jun-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: 
 */

@JsonInclude(Include.NON_NULL)
public class MetaDO implements IsSerializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5352082220908500490L;
	/**
	 * 
	 */

	//private Array permissions;
	//private String[] permissions;
	private List<String> permissions;
	private Integer collaboratorCount;
	private boolean isCollaborator;
	private String status;
	
	public MetaDO(){}
	
	
	/** 
	 * This method is to get the collaboratorCount
	 */
	public Integer getCollaboratorCount() {
		return collaboratorCount;
	}

	/** 
	 * This method is to set the collaboratorCount
	 */
	public void setCollaboratorCount(Integer collaboratorCount) {
		this.collaboratorCount = collaboratorCount;
	}

	/** 
	 * This method is to get the isCollaborator
	 */
	public boolean isIsCollaborator() {
		return isCollaborator;
	}

	/** 
	 * This method is to set the isCollaborator
	 */
	public void setCollaborator(boolean isCollaborator) {
		this.isCollaborator = isCollaborator;
	}

	/** 
	 * This method is to get the permissions
	 */
	public List<String> getPermissions() {
		return permissions;
	}

	/** 
	 * This method is to set the permissions
	 */
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
