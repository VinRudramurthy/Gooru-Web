package org.ednovo.gooru.client.mvp.gshelf.util;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class EmptyCourseBuilderWidget extends Composite {

	private static EmptyCourseBuilderWidgetUiBinder uiBinder = GWT
			.create(EmptyCourseBuilderWidgetUiBinder.class);

	interface EmptyCourseBuilderWidgetUiBinder extends
			UiBinder<Widget, EmptyCourseBuilderWidget> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField HTMLPanel courseEmptyContainer;
	@UiField Button btnCreateCourse;


	public EmptyCourseBuilderWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		courseEmptyContainer.getElement().setId("myAllCorsesNullCase");
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				courseEmptyContainer.setHeight(Window.getClientHeight()+"px");
			}
		});
	}
	@UiHandler("btnCreateCourse")
	public void createCourseClick(ClickEvent e){
		onClick();
	}
	public abstract void onClick();
	
}
