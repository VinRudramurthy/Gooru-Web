package org.ednovo.gooru.client.mvp.search.event;

import org.ednovo.gooru.application.shared.model.search.SearchDo;

import com.google.gwt.event.shared.EventHandler;

public interface AggregatorSuggestionEventHandler extends EventHandler{

	/**
	 * Get aggregator source
	 * @param searchDo instance of {@link SearchDo}
	 */
	void requestAggregatorSuggestion(SearchDo<String> searchDo);

}
