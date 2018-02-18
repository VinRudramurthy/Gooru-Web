package org.ednovo.gooru.client.mvp.home.library.events;

import org.ednovo.gooru.application.shared.model.library.StandardsDo;

import com.google.gwt.event.shared.GwtEvent;
public class SetStandardDoEvent extends GwtEvent<SetStandardDoHandler> {

	private String subjectCode;
	private StandardsDo subjectDo;
	
	public static final Type<SetStandardDoHandler> TYPE = new Type<SetStandardDoHandler>();
	
	/**
	 * 
	 */
	public SetStandardDoEvent(String subjectCode, StandardsDo subjectDo) {
		this.subjectCode = subjectCode;
		this.subjectDo = subjectDo;
	}
	
	@Override
	public Type<SetStandardDoHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SetStandardDoHandler handler) {
		handler.setStandardDo(subjectCode, subjectDo);
	}

}
