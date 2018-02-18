package org.ednovo.gooru.client.mvp.assessments.play.collection.event;

import com.google.gwt.event.shared.EventHandler;
/**
 *
 * @fileName : UpdateCommentChildViewHandler.java
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
 * @Reviewer: Gooru Team
 */
public interface UpdateCommentChildViewHandler extends EventHandler {

/**
 * @function updateCommentChildViewHandler
 *
 * @created_date : 02-Jan-2014
 *
 * @description
 *
 * @parm(s) : @param commentUid, @param action
 *
 * @return : void
 *
 * @throws : <Mentioned if any exceptions>
 *
 */
	void updateCommentChildView(String commentUid, String action);
}
