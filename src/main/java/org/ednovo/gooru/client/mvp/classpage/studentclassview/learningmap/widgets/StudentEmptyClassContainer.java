package org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.widgets;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentEmptyClassContainer extends Composite {

	@UiField PPanel errorLbl, recoverLbl;
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	private static StudentEmptyClassContainerUiBinder uiBinder = GWT
			.create(StudentEmptyClassContainerUiBinder.class);

	interface StudentEmptyClassContainerUiBinder extends
			UiBinder<Widget, StudentEmptyClassContainer> {
	}

	public StudentEmptyClassContainer() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setUserName(String userName) {
		if(AppClientFactory.isAnonymous()) {
			errorLbl.setText(i18n.GL3566());
		} else {
			String errorLblStr = StringUtil.generateMessage(i18n.GL3469_9(), userName != null ? userName : "");
			errorLbl.setText(errorLblStr);
		}
	}
}