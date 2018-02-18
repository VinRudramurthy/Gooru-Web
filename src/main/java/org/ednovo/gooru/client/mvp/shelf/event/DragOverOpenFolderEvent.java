package org.ednovo.gooru.client.mvp.shelf.event;

import com.google.gwt.event.shared.GwtEvent;

public class DragOverOpenFolderEvent extends GwtEvent<DragOverOpenFolderHandler> {
	
	public static final Type<DragOverOpenFolderHandler> TYPE = new Type<DragOverOpenFolderHandler>();
	
	private String folderId="";
	private boolean showCollectionFormView;
	
	public DragOverOpenFolderEvent(String folderId,boolean showCollectionFormView){
		this.folderId=folderId;
		this.showCollectionFormView=showCollectionFormView;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DragOverOpenFolderHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DragOverOpenFolderHandler handler) {
		handler.onDragOverOpenFolder(folderId,showCollectionFormView);
	}

}
