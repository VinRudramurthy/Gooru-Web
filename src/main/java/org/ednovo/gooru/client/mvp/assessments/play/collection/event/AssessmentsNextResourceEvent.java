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
public class AssessmentsNextResourceEvent extends GwtEvent<AssessmentsNextResourceHandler> {
	public static final Type<AssessmentsNextResourceHandler> TYPE = new Type<AssessmentsNextResourceHandler>();

	public AssessmentsNextResourceEvent() {
	}

	@Override
	public Type<AssessmentsNextResourceHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AssessmentsNextResourceHandler handler) {
		handler.loadNextResource();
	}
}