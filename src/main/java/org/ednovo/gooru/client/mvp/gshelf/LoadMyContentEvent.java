package org.ednovo.gooru.client.mvp.gshelf;

import com.google.gwt.event.shared.GwtEvent;

public class LoadMyContentEvent extends GwtEvent<LoadMyContentEventHandler> {

	private String type;
	
	public static final Type<LoadMyContentEventHandler> TYPE = new Type<LoadMyContentEventHandler>();
	
	public LoadMyContentEvent(String type) {
		this.type = type;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoadMyContentEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoadMyContentEventHandler handler) {
		handler.loadMyContentData(type);
	}

}
 