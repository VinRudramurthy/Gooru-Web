package org.ednovo.gooru.client.mvp.search.event;

import com.google.gwt.event.shared.GwtEvent;

public class ResourceTagsCountUpdateEvent extends GwtEvent<ResourceTagsCountUpdateEventHandler> {
	
	public static final Type<ResourceTagsCountUpdateEventHandler> TYPE = new Type<ResourceTagsCountUpdateEventHandler>();
	
	int totalTagsCount;
	
	public ResourceTagsCountUpdateEvent(int totalTagsCount){
		
		this.totalTagsCount = totalTagsCount;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ResourceTagsCountUpdateEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ResourceTagsCountUpdateEventHandler handler) {
		handler.updateTotalTagsCount(totalTagsCount);
	}

}
