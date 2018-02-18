package org.ednovo.gooru.client.mvp.assessments.play.collection.event;
import com.google.gwt.event.shared.GwtEvent;


public class UpdatePreviewViewCountEvent extends GwtEvent<UpdatePreviewViewCountEventHandler> {

	public static final Type<UpdatePreviewViewCountEventHandler> TYPE = new Type<UpdatePreviewViewCountEventHandler>();

	public UpdatePreviewViewCountEvent() {
	}

	@Override
	public Type<UpdatePreviewViewCountEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdatePreviewViewCountEventHandler handler) {
		handler.updateViewsCount();
	}


}
