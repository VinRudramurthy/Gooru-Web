package org.ednovo.gooru.client.mvp.assessments.play.collection.event;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @fileName : UpdateCommentChildViewEvent.java
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
public class UpdateCommentChildViewEvent extends GwtEvent<UpdateCommentChildViewHandler> {
	private String commentUid;
	private String action;

	public static final Type<UpdateCommentChildViewHandler> TYPE = new Type<UpdateCommentChildViewHandler>();

	public UpdateCommentChildViewEvent(String commentUid, String action) {
		this.commentUid = commentUid;
		this.action = action;
	}

	@Override
	public Type<UpdateCommentChildViewHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateCommentChildViewHandler handler) {
		handler.updateCommentChildView(commentUid, action);
	}
}