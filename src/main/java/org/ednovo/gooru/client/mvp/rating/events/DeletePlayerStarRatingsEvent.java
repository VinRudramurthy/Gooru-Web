package org.ednovo.gooru.client.mvp.rating.events;

import com.google.gwt.event.shared.GwtEvent;

public class DeletePlayerStarRatingsEvent extends GwtEvent<DeletePlayerStarRatingsEventHandler> {
	
	public static final Type<DeletePlayerStarRatingsEventHandler> TYPE = new Type<DeletePlayerStarRatingsEventHandler>();
	
	public DeletePlayerStarRatingsEvent(){
		
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DeletePlayerStarRatingsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeletePlayerStarRatingsEventHandler handler) {
		handler.deleteRatingsInPlayer();
	}

}
