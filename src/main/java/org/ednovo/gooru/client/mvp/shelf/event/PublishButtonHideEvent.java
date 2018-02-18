package org.ednovo.gooru.client.mvp.shelf.event;

import com.google.gwt.event.shared.GwtEvent;


public class PublishButtonHideEvent extends GwtEvent<PublishButtonHideEventHandler>{
	
	public static final Type<PublishButtonHideEventHandler> TYPE = new Type<PublishButtonHideEventHandler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PublishButtonHideEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PublishButtonHideEventHandler handler) {
		handler.hideEditButton();
		
	}

}
