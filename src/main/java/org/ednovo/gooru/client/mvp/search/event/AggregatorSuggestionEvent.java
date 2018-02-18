package org.ednovo.gooru.client.mvp.search.event;

import org.ednovo.gooru.application.shared.model.search.SearchDo;

import com.google.gwt.event.shared.GwtEvent;


public class AggregatorSuggestionEvent extends GwtEvent<AggregatorSuggestionEventHandler>{

	public static final Type<AggregatorSuggestionEventHandler> TYPE = new Type<AggregatorSuggestionEventHandler>();

	private SearchDo<String> searchDo;
	
	public AggregatorSuggestionEvent(SearchDo<String> searchDo) {
		setSearchDo(searchDo);
	}

	
	@Override
	public Type<AggregatorSuggestionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AggregatorSuggestionEventHandler handler) {
		handler.requestAggregatorSuggestion(getSearchDo());
		
	}
	public SearchDo<String> getSearchDo() {
		return searchDo;
	}

	public void setSearchDo(SearchDo<String> searchDo) {
		this.searchDo = searchDo;
	}
}
