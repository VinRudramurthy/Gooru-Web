package org.ednovo.gooru.client.mvp.shelf.collection;

import com.google.gwt.event.shared.GwtEvent;

public class RefreshDisclosurePanelEvent extends GwtEvent<RefreshDisclosurePanelHandler> {

	public static final Type<RefreshDisclosurePanelHandler> TYPE = new Type<RefreshDisclosurePanelHandler>();


	private String collectionId=null;

	/**
	 * Class constructor , assign  collection object and refresh type
	 */
	public RefreshDisclosurePanelEvent(String collectionId) {
		this.collectionId=collectionId;
	}

	@Override
	public Type<RefreshDisclosurePanelHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RefreshDisclosurePanelHandler handler) {
		handler.refreshDisclosurePanelinSearch(collectionId);
	}

}
