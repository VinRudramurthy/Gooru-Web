package org.ednovo.gooru.client.mvp.shelf.event;

import org.ednovo.gooru.application.shared.model.content.CollectionDo;

import com.google.gwt.event.shared.GwtEvent;

public class CopyDraggedCollectionEvent extends GwtEvent<CopyDraggedCollectionHandler> {

	public static final Type<CopyDraggedCollectionHandler> TYPE = new Type<CopyDraggedCollectionHandler>();
	
	private String collectionId="";
	private CollectionDo collectionDo;
	private String parentId;


	public CopyDraggedCollectionEvent(CollectionDo collectionDo,String collectionId,String parentId){
		this.collectionDo = collectionDo;
		this.collectionId=collectionId;
		this.parentId=parentId;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CopyDraggedCollectionHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CopyDraggedCollectionHandler handler) {
		handler.copyDraggedCollection(collectionDo,collectionId,parentId);
	} 

}
