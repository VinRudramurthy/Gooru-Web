package org.ednovo.gooru.client.mvp.rating.events;

import com.google.gwt.event.shared.GwtEvent;

public class UpdateResourceReviewCountEvent extends GwtEvent<UpdateResourceReviewCountEventHandler> {

	
	public static final Type<UpdateResourceReviewCountEventHandler> TYPE = new Type<UpdateResourceReviewCountEventHandler>();
	
	private String resourceId;
	private Integer count;
	
	public UpdateResourceReviewCountEvent(String resourceId,Integer count){
		this.resourceId=resourceId;
		this.count=count;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UpdateResourceReviewCountEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateResourceReviewCountEventHandler handler) {
		handler.setReviewCount(resourceId,count);
	}

}
