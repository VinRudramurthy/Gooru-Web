package org.ednovo.gooru.client.mvp.shelf.collection.folders.events;

import com.google.gwt.event.shared.GwtEvent;

public class RemoveMovedCollectionFolderEvent extends GwtEvent<RemoveMovedCollectionFolderHandler> {

	public static final Type<RemoveMovedCollectionFolderHandler> TYPE = new Type<RemoveMovedCollectionFolderHandler>();
	
	String sourceId="";
	
	public RemoveMovedCollectionFolderEvent(String sourceId){
		this.sourceId = sourceId;
	}
	
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RemoveMovedCollectionFolderHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RemoveMovedCollectionFolderHandler handler) {
		handler.removeMovedCollectionFromShelf(sourceId); 
	}

}
