package org.ednovo.gooru.client.mvp.search.event;

import com.google.gwt.event.shared.EventHandler;

public interface ResourceTagsCountUpdateEventHandler extends EventHandler {

	void updateTotalTagsCount(int totalTagsCount); 

}
