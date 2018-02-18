package org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.widgets;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class StudentProgressContentWidget extends Composite {
	
	@UiField HTMLEventPanel contentPanel;
	@UiField H3Panel statusCount;
	@UiField PPanel message;
	@UiField HTMLPanel borderLine;
	
	private static StudentClassContentWidgetUiBinder uiBinder = GWT.create(StudentClassContentWidgetUiBinder.class);
	
	interface StudentClassContentWidgetUiBinder extends UiBinder<Widget, StudentProgressContentWidget> {}
	
	public StudentProgressContentWidget(String count, String messageTxt, String contentType) {
		initWidget(uiBinder.createAndBindUi(this));
		setData(count,messageTxt,contentType);
	}
	
	public void setData(String count, String messageTxt, String contentType) {
		if(contentType.equalsIgnoreCase("collection")) {
			borderLine.addStyleName("blueLeftBorder");
		} else {
			borderLine.addStyleName("orangeLeftBorder");
		}
		statusCount.setText(count);
		message.setText(messageTxt);
	}
}