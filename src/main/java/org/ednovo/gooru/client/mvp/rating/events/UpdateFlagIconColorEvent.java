package org.ednovo.gooru.client.mvp.rating.events;

import com.google.gwt.event.shared.GwtEvent;

public class UpdateFlagIconColorEvent extends GwtEvent<UpdateFlagIconColorEventHandler> {

	public static final Type<UpdateFlagIconColorEventHandler> TYPE = new Type<UpdateFlagIconColorEventHandler>();
	
	public UpdateFlagIconColorEvent(){
		
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UpdateFlagIconColorEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateFlagIconColorEventHandler handler) {
		handler.updateFlagColor();
	}

}
