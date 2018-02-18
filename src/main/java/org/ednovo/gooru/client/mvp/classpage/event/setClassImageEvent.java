package org.ednovo.gooru.client.mvp.classpage.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class setClassImageEvent extends GwtEvent<setClassImageHandler> {
	
	String fileName;
	String mediaName;
	
	public static Type<setClassImageHandler> TYPE = new Type<setClassImageHandler>();

	public setClassImageEvent(String fileName,String mediaName){
		this.fileName=fileName;
		this.mediaName=mediaName;
	}
	
	@Override
	protected void dispatch(setClassImageHandler handler) {
		handler.setImage(fileName,mediaName);
	}

	@Override
	public Type<setClassImageHandler> getAssociatedType() {
		return TYPE;
	}

}
