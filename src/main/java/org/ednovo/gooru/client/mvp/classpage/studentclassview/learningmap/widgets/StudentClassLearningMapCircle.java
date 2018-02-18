package org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.widgets;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.uc.SpanPanel;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class StudentClassLearningMapCircle extends Composite {
	
	@UiField HTMLEventPanel circleField;
	@UiField SpanPanel spanTxt;
	private PopupPanel toolTipPopupPanel = new PopupPanel();
	
	private String unitId = null, lessonId = null;
	
	private static StudentClassLearningMapCircleUiBinder uiBinder = GWT.create(StudentClassLearningMapCircleUiBinder.class);
	
	interface StudentClassLearningMapCircleUiBinder extends UiBinder<Widget, StudentClassLearningMapCircle> {}

	public StudentClassLearningMapCircle(PlanProgressDo planProgressDo, String unitId) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.unitId = unitId;
		this.lessonId = planProgressDo.getGooruOId();
		
		String circleType = "";
		String page = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_PREVIEW_MODE, UrlNavigationTokens.FALSE);
		if(!page.equalsIgnoreCase(UrlNavigationTokens.TRUE)) {
			if(planProgressDo.getScoreStatus()!=null&&planProgressDo.getScoreStatus().equalsIgnoreCase("NotAttempted")) {
				
			} else if(planProgressDo.getScoreStatus()!=null&&planProgressDo.getScoreStatus().equalsIgnoreCase("ScoreNotMet")) {
				circleType = "blue-circle";
			} else if(planProgressDo.getScoreStatus()!=null&&planProgressDo.getScoreStatus().equalsIgnoreCase("ScoreMet")) {
				circleType = "green-circle";
			}
		}
		
		String lessonName = planProgressDo.getTitle();
		circleField.addMouseOverHandler(new MouseOverShowClassCodeToolTip(lessonName));
		circleField.addMouseOutHandler(new MouseOutHideToolTip());
		circleField.addClickHandler(new LessonViewNavigation());
		if(!circleType.isEmpty()) {
			circleField.addStyleName(circleType);
		}
	}
	
	public class MouseOverShowClassCodeToolTip implements MouseOverHandler{
		private String label = null;
		public MouseOverShowClassCodeToolTip(String label) {
			this.label = label;
		}
		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanel.clear();
			toolTipPopupPanel.setWidget(new GlobalToolTip(label));
			toolTipPopupPanel.setStyleName("");
			toolTipPopupPanel.setPopupPosition(event.getRelativeElement().getAbsoluteLeft(), event.getRelativeElement().getAbsoluteTop()+25);
			toolTipPopupPanel.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanel.show();
		}
	}
	
	public class MouseOutHideToolTip implements MouseOutHandler{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanel.hide();
		}
	}
	
	public class LessonViewNavigation implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			PlaceRequest request = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
			request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW);
			request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID, unitId);
			request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID, lessonId);
			AppClientFactory.getPlaceManager().revealPlace(request);			
		}
	}
}