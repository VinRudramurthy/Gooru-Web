/**
 * 
 */
package org.ednovo.gooru.client.mvp.search.event;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @fileName : SetCollabCountHandler.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: Jan 28, 2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface SetPanelVisibilityHandler extends EventHandler, BaseUiHandlers {

	void setPendingActiveVisibility();

}
