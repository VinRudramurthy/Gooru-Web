package org.ednovo.gooru.client.mvp.assessments.play.collection.event;
import com.google.gwt.event.shared.EventHandler;
/**
 * @fileName : EditCommentChildViewHandler.java
 *
 * @description :
 *
 * @version : 1.0
 *
 * @date: 06-Jan-2014
 *
 * @Author: Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public interface EditCommentChildViewHandler extends EventHandler {
	/**
	 * @function editCommentChildView
	 *
	 * @created_date : 06-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param commentUid
	 * @parm(s) : @param commentText
	 * @parm(s) : @param action
	 *
	 * @return : void
	 */
	void editCommentChildView(String commentUid, String commentText, String action);
}
