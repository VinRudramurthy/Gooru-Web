/**
 * 
 */
package org.ednovo.gooru.client.mvp.search.event;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @fileName : RemoveCollaboratorObjectHandler.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: Jan 31, 2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface RemoveCollaboratorObjectHandler extends EventHandler, BaseUiHandlers {

	void removeFromParentByEmailId(String emailId);

}
