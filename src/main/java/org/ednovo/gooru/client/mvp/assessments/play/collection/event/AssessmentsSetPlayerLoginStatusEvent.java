package org.ednovo.gooru.client.mvp.assessments.play.collection.event;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @fileName : SetPlayerLoginStatusEvent.java
 *
 * @description :
 *
 * @version : 1.0
 *
 * @date: 02-Jan-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class AssessmentsSetPlayerLoginStatusEvent extends GwtEvent<AssessmentsSetPlayerLoginStatusHandler> {
	private boolean isLoggedIn;
	public static final Type<AssessmentsSetPlayerLoginStatusHandler> TYPE = new Type<AssessmentsSetPlayerLoginStatusHandler>();

	public AssessmentsSetPlayerLoginStatusEvent(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	@Override
	public Type<AssessmentsSetPlayerLoginStatusHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AssessmentsSetPlayerLoginStatusHandler handler) {
		handler.setPlayerLoginStatusHandler(isLoggedIn);
	}
}