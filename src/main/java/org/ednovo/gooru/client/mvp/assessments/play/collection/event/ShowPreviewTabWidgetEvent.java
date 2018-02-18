package org.ednovo.gooru.client.mvp.assessments.play.collection.event;
import com.google.gwt.event.shared.GwtEvent;


public class ShowPreviewTabWidgetEvent extends GwtEvent<ShowPreviewTabWidgetEventHandler> {

	public static final Type<ShowPreviewTabWidgetEventHandler> TYPE = new Type<ShowPreviewTabWidgetEventHandler>();
	private String widgetMode=null;
	private boolean isLoginRequestCancel=false;
	public ShowPreviewTabWidgetEvent(String widgetMode,boolean isLoginRequestCancel) {
		this.widgetMode=widgetMode;
		this.isLoginRequestCancel=isLoginRequestCancel;
	}

	@Override
	public Type<ShowPreviewTabWidgetEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowPreviewTabWidgetEventHandler handler) {
		handler.showTabWidget(widgetMode,isLoginRequestCancel);
	}

}
