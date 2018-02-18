package org.ednovo.gooru.client.mvp.assessments.play.collection.event;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @fileName : EditCommentChildViewEvent.java
 *
 * @description :
 *
 * @version : 1.0
 *
 * @date: 02-Jan-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class EditCommentChildViewEvent extends GwtEvent<EditCommentChildViewHandler> {
	private String commentUid;
	private String commentText;
	private String action;

	public static final Type<EditCommentChildViewHandler> TYPE = new Type<EditCommentChildViewHandler>();

	public EditCommentChildViewEvent(String commentUid, String commentText, String action) {
		this.commentUid = commentUid;
		this.commentText = commentText;
		this.action = action;
	}

	@Override
	public Type<EditCommentChildViewHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditCommentChildViewHandler handler) {
		handler.editCommentChildView(commentUid, commentText, action);
	}
}