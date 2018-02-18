package org.ednovo.gooru.client.mvp.assessments.play.collection.end.study;
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
public class CloseAssessmentsPlayerEvent extends GwtEvent<CloseAssessmentsPlayerHandler> {

	private boolean isClose;

	public static final Type<CloseAssessmentsPlayerHandler> TYPE = new Type<CloseAssessmentsPlayerHandler>();

	public CloseAssessmentsPlayerEvent(boolean isClose) {
		this.isClose = isClose;
	}

	@Override
	public Type<CloseAssessmentsPlayerHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CloseAssessmentsPlayerHandler handler) {
		handler.closePreviewPlayer(isClose);
	}
}