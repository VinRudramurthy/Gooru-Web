package org.ednovo.gooru.client.mvp.assessments.play.collection.event;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @fileName : ClosePreviewPlayerEvent.java
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
public class ClosePreviewPlayerEvent extends GwtEvent<ClosePreviewPlayerHandler> {

	private boolean isClose;

	public static final Type<ClosePreviewPlayerHandler> TYPE = new Type<ClosePreviewPlayerHandler>();

	public ClosePreviewPlayerEvent(boolean isClose) {
		this.isClose = isClose;
	}

	@Override
	public Type<ClosePreviewPlayerHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ClosePreviewPlayerHandler handler) {
		handler.closePreviewPlayer(isClose);
	}
}