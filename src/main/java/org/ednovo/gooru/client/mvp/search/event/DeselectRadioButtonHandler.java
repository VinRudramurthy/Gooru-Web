/**
 * 
 */
package org.ednovo.gooru.client.mvp.search.event;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @fileName : DeselectRadioButtonHandler.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: Jan 29, 2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface DeselectRadioButtonHandler extends EventHandler, BaseUiHandlers {

	void setDeSelection();

}
