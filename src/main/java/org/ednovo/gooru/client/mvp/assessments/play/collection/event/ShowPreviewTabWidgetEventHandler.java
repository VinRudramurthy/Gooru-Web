/**
 *
 */
package org.ednovo.gooru.client.mvp.assessments.play.collection.event;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;

import com.google.gwt.event.shared.EventHandler;


public interface ShowPreviewTabWidgetEventHandler extends EventHandler, BaseUiHandlers {

	public void showTabWidget(String WidgetMode,boolean isLoginRequestCancel);

}
