package org.ednovo.gooru.client.mvp.rating.events;

import com.google.gwt.event.shared.EventHandler;

public interface UpdateResourceReviewCountEventHandler extends EventHandler {

	void setReviewCount(String resourceId, Integer count); 

}
