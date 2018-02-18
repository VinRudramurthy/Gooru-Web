package org.ednovo.gooru.client.mvp.shelf.event;

import org.ednovo.gooru.application.shared.model.content.CollectionDo;

import com.google.gwt.event.shared.EventHandler;

public interface CopyDraggedCollectionHandler extends EventHandler {

	void copyDraggedCollection(CollectionDo collectionDo, String collectionId,String parentId); 

}
