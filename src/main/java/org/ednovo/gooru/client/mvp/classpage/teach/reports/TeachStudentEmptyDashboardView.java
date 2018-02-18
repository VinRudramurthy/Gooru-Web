package org.ednovo.gooru.client.mvp.classpage.teach.reports;

import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public abstract class TeachStudentEmptyDashboardView extends Composite {

	@UiField H3Panel headerText, txt1Msg, txt2Msg, txt3Msg;
	
	@UiField PPanel headerMsg, joinClassErrorMsg;
	
	@UiField HTMLPanel coursePanel, studentPanel, stepsContentTwo, stepsContentThree;
	
	@UiField Button btnCourse, btnInviteStudents;
	
	@UiField Label countTwo, countThree;
	
	@UiField Anchor myContentAnr;
	
	private static final String GREEN_CIRCLE = "greenCircle";
	
	private static final String GREEN_TEXT = "greenText";
	
	MessageProperties i18n = GWT.create(MessageProperties.class);
	
	private static TeachStudentEmptyDashboardViewUiBinder uiBinder = GWT
			.create(TeachStudentEmptyDashboardViewUiBinder.class);

	interface TeachStudentEmptyDashboardViewUiBinder extends
			UiBinder<Widget, TeachStudentEmptyDashboardView> {
	}

	public TeachStudentEmptyDashboardView(boolean isNoCourse, boolean isNoStudent) {
		initWidget(uiBinder.createAndBindUi(this));
		setPanelVisiblity(false,false);
		setData(isNoCourse, isNoStudent);
	}
	
	private void setData(boolean isNoCourse, boolean isNoStudent) {
		txt1Msg.setText(i18n.GL0747());
		txt2Msg.setText(i18n.GL3462_5());
		txt3Msg.setText(i18n.GL3462_7());
		joinClassErrorMsg.setText(i18n.GL3462_9());
		btnCourse.setText(i18n.GL3462_6());
		btnInviteStudents.setText(i18n.GL3462_8());
		
		if(isNoCourse) {
			headerText.setText(i18n.GL3462_2());
			headerMsg.setText(i18n.GL3462_4());
			countTwo.addStyleName(GREEN_CIRCLE);
			stepsContentTwo.addStyleName(GREEN_TEXT);
			setPanelVisiblity(true,false);
		} else if(isNoStudent) {
			headerText.setText(i18n.GL3462_1());
			headerMsg.setText(i18n.GL3462_3());
			countThree.addStyleName(GREEN_CIRCLE);
			stepsContentThree.addStyleName(GREEN_TEXT);
			setPanelVisiblity(false,true);
		}
	}
	
	private void setPanelVisiblity(boolean isNoCourse, boolean isNoStudent) {
		coursePanel.setVisible(isNoCourse);
		studentPanel.setVisible(isNoStudent);
	}
	
	@UiHandler("btnCourse")
	public void clickBtnCourse(ClickEvent event) {
		clickBtnCourse();
	}
	
	@UiHandler("btnInviteStudents")
	public void clickBtnInviteStudents(ClickEvent event) {
		PlaceRequest request = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
		Map<String, String> params = StringUtil.splitQuery(Window.Location.getHref());
		params.remove(UrlNavigationTokens.TEACHER_CLASSPAGE_REPORT_TYPE);
		params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.TEACHER_CLASS_SETTINGS);
		params.put(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW, UrlNavigationTokens.TEACHER_CLASS_STUDENTS_ROASTER);
		AppClientFactory.getPlaceManager().revealPlace(request,params);
	}
	
	@UiHandler("myContentAnr")
	public void redirectToMyContent(ClickEvent event) {
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT);
	}
	
	public abstract void clickBtnCourse();
}