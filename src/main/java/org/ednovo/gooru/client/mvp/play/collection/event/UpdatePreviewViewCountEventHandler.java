/**
 * 
 */
package org.ednovo.gooru.client.mvp.play.collection.event;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;

import com.google.gwt.event.shared.EventHandler;


public interface UpdatePreviewViewCountEventHandler extends EventHandler, BaseUiHandlers {

	public void updateViewsCount();

}
