package org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.widgets;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentProgressScoreWidget extends Composite {
	
	@UiField HTMLEventPanel scorePanel;
	@UiField H3Panel score, timeSpent;
	@UiField PPanel scoreMessage, timeMessage;
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	private static StudentProgressScoreWidgetUiBinder uiBinder = GWT.create(StudentProgressScoreWidgetUiBinder.class);
	
	interface StudentProgressScoreWidgetUiBinder extends UiBinder<Widget, StudentProgressScoreWidget> {}
	
	public StudentProgressScoreWidget(String scoreTxt, String style, String timeSpentTxt) {
		initWidget(uiBinder.createAndBindUi(this));
		setScoreData(scoreTxt,style);
		setCollectionData(timeSpentTxt);
	}
	
	public void setScoreData(String scoreTxt, String style) {
		scoreMessage.setText(i18n.GL3469_17());
		score.setText(scoreTxt);
		scorePanel.addStyleName(style);
	}
	
	public void setCollectionData(String timeSpentTxt) {
		timeMessage.setText(i18n.GL3469_18());
		timeSpent.setText(timeSpentTxt);
	}
}