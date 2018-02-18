package org.ednovo.gooru.client.mvp.classpage.teach.reports.studentreport;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.uc.SpanPanel;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class TeachStudentReportPopupWidget extends PopupPanel {
	
	@UiField Label popupTitle;
	
	@UiField HTMLPanel contentPanel, panel, scoreWidget;
	
	@UiField Anchor closeButton;
	
	@UiField SpanPanel userName;
	
	@UiField Image userImage;
	
	private static final String DEFAULT_USER_IMAGE = "../images/settings/setting-user-image.png";
	
	private static TeachStudentReportPopupWidgetUiBinder uiBinder = GWT
			.create(TeachStudentReportPopupWidgetUiBinder.class);

	interface TeachStudentReportPopupWidgetUiBinder extends
			UiBinder<Widget, TeachStudentReportPopupWidget> {
	}
	
	public TeachStudentReportPopupWidget(String contentName, String userNameLbl, String gooruUId, String classId, String courseId, String unitId, String lessonId, String assessmentId, String collectionType, String pageType) {
		setWidget(uiBinder.createAndBindUi(this));
		this.getElement().getStyle().setZIndex(999999);
		this.setGlassEnabled(true);
		this.setPopupPosition(0, Window.getScrollTop()+50);
		this.show();
		if(pageType!=null&&pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
			scoreWidget.setVisible(false);
		}
		setData(contentName, userNameLbl, gooruUId, classId, courseId, unitId, lessonId, assessmentId, collectionType, pageType);
	}

	public void setData(String contentName, String userNameLbl,String gooruUId, String classId, String courseId, String unitId, String lessonId, String assessmentId, String collectionType, String pageType) {
		panel.clear();
		userName.setText("Hello, "+userNameLbl+"!");
		userImage.setUrl(AppClientFactory.getLoggedInUser().getSettings().getProfileImageUrl()+gooruUId+ClientConstants.PNG);
		
		userImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				userImage.setUrl(DEFAULT_USER_IMAGE);
			}
		});
		popupTitle.setText(contentName);
		panel.add(new TeachStudentReportPopupChildView(userNameLbl,gooruUId,classId,courseId,unitId,lessonId,assessmentId,collectionType,pageType));
	}
	
	@UiHandler("closeButton")
	public void CloseButton(ClickEvent event) {
		this.hide();
	}

	@Override
	protected void onLoad() {
		contentPanel.getElement().setAttribute("style", "min-height:"+(Window.getClientHeight()+Window.getScrollTop()-100)+"px");
	}
	
}