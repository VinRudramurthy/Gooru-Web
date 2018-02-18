package org.ednovo.gooru.client.mvp.assessments.play.collection.event;

import com.google.gwt.event.shared.EventHandler;
/**
 *
 * @fileName : SetPlayerLoginStatusHandler.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 02-Jan-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface AssessmentsSetPlayerLoginStatusHandler extends EventHandler {

/**
 *
 * @function setPlayerLoginStatusHandler
 *
 * @created_date : 02-Jan-2014
 *
 * @description
 *
 *
 * @parm(s) : @param isLoggedIn
 *
 * @return : void
 *
 * @throws : <Mentioned if any exceptions>
 *
 *
 *
 *
 */
	void setPlayerLoginStatusHandler(boolean isLoggedIn);
}
