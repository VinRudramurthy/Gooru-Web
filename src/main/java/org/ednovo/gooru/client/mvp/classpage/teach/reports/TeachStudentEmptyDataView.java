package org.ednovo.gooru.client.mvp.classpage.teach.reports;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.PPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TeachStudentEmptyDataView extends Composite {

	@UiField H3Panel headerText;
	
	@UiField PPanel headerMsg;
	
	MessageProperties i18n = GWT.create(MessageProperties.class);
	
	private static TeachStudentEmptyDashboardViewUiBinder uiBinder = GWT
			.create(TeachStudentEmptyDashboardViewUiBinder.class);

	interface TeachStudentEmptyDashboardViewUiBinder extends
			UiBinder<Widget, TeachStudentEmptyDataView> {
	}

	public TeachStudentEmptyDataView() {
		initWidget(uiBinder.createAndBindUi(this));
		setItemData();
	}
	
	private void setItemData() {
		headerText.setText(i18n.GL3462_2());
		headerMsg.setText(i18n.GL3462_4_1());
	}
	
}