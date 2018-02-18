package org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.widgets;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.SpanPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class StudentClassLessonContainer extends Composite {

	@UiField HTMLPanel lessonContainer, circleIcon, leftArrow, rightArrow;
	@UiField H3Panel lessonCountName;
	@UiField PPanel lessonName;
	@UiField HTMLEventPanel lessonWidget;
	@UiField Label numericOrder;
	
	ArrayList<PlanProgressDo> dataList = new ArrayList<PlanProgressDo>();
	
	int start = 0, end = 0;

	private final static int CAROUSEL_LIMIT = 6;
	
	private String gooruOid = null, status = null, userId = null;
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	private static StudentClassLessonContainerUiBinder uiBinder = GWT.create(StudentClassLessonContainerUiBinder.class);

	interface StudentClassLessonContainerUiBinder extends
			UiBinder<Widget, StudentClassLessonContainer> {
	}

	public StudentClassLessonContainer(PlanProgressDo planProgressDo, int count, String status, String userId) {
		initWidget(uiBinder.createAndBindUi(this));
		setCircleContainerItems(planProgressDo, count, status, userId);
		lessonWidget.addClickHandler(new LessonPageRedirection(planProgressDo.getGooruOId()));
	}
	
	public void setCircleContainerItems(PlanProgressDo planProgressDo, int count, String status, String userId) {
		numericOrder.setText(count+"");
		lessonCountName.setText(planProgressDo.getTitle());
		lessonName.setText(i18n.GL0910());
		
		dataList = planProgressDo.getItem();
		int size = dataList.size();
		
		String page = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_PREVIEW_MODE, UrlNavigationTokens.FALSE);
		if(!page.equalsIgnoreCase(UrlNavigationTokens.TRUE)) {
			String circleIconStyle = "";
			if(planProgressDo.getScoreStatus()!=null&&planProgressDo.getScoreStatus().equalsIgnoreCase("NotAttempted")) {
				
			} else if(planProgressDo.getScoreStatus()!=null&&planProgressDo.getScoreStatus().equalsIgnoreCase("ScoreNotMet")) {
				circleIconStyle = "blue-circle";
			} else if(planProgressDo.getScoreStatus()!=null&&planProgressDo.getScoreStatus().equalsIgnoreCase("ScoreMet")) {
				circleIconStyle = "green-circle";
			}
			circleIcon.setStyleName(circleIconStyle);
		}
		if(size>0) {
			start = 0;
			if(size < CAROUSEL_LIMIT+1) {
				end = size;
			} else {
				end = CAROUSEL_LIMIT;
			}
			this.gooruOid = planProgressDo.getGooruOId();
			this.status = status;
			this.userId = userId;
			setData(start,end);
		} else {
			setArrowVisibility(false,false);
			SpanPanel errorPanel = StringUtil.getStudentPlanErrorLbl(i18n.GL3469_6(), "error-lbl-student-course-plan");
			errorPanel.addStyleName("line-height-70");
			lessonContainer.add(errorPanel);
		}
	}
	
	private void setData(int startPoint, int endPoint) {
		String page = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_PREVIEW_MODE, UrlNavigationTokens.FALSE);
		lessonContainer.clear();
		for(int z=startPoint;z<endPoint;z++) {
			String styleName = "blueBorder ";
			PlanProgressDo planDo = dataList.get(z);
			if(planDo.getType()!=null&&(planDo.getType().equalsIgnoreCase("assessment")||planDo.getType().equalsIgnoreCase("assessment/url"))) {
				styleName = "orgBorder ";
			}
			if(!page.equalsIgnoreCase(UrlNavigationTokens.TRUE)) {
				if(planDo.getScoreStatus()!=null&&planDo.getScoreStatus().equalsIgnoreCase("Viewed")) {
					styleName = styleName + "empty-plan-collection-selected";
				} else if(planDo.getScoreStatus()!=null&&planDo.getScoreStatus().equalsIgnoreCase("ScoreNotMet")) {
					styleName = styleName + "blueselected";
				} else if(planDo.getScoreStatus()!=null&&planDo.getScoreStatus().equalsIgnoreCase("ScoreMet")) {
					styleName = styleName + "selected";
				}
			}
			lessonContainer.add(new StudentClassContentWidget(dataList.get(z), styleName,gooruOid, status, userId));
		}
		setArrows();
	}
	
	private void setArrows() {
		boolean leftArrowVisibile = false, rightArrowVisible = false;
		if(end>CAROUSEL_LIMIT) {
			leftArrowVisibile = true;
		}
		if(end<dataList.size()) {
			rightArrowVisible = true;
		}
		setArrowVisibility(leftArrowVisibile,rightArrowVisible);
	}
	
	private void setArrowVisibility(boolean leftArrowVisibile, boolean rightArrowVisible) {
		leftArrow.setVisible(leftArrowVisibile);
		rightArrow.setVisible(rightArrowVisible);
	}
	
	@UiHandler("leftArrow")
	public void clickLeftArrow(ClickEvent event) {
		end = start;
		if(start-CAROUSEL_LIMIT<0) {
			start = 0;
		} else {
			start = start - CAROUSEL_LIMIT;
		}
		setData(start, end);
	}
	
	@UiHandler("rightArrow")
	public void clickRightArrow(ClickEvent event) {
		start = end;
		if((dataList.size())-end > CAROUSEL_LIMIT) {
			end = end + CAROUSEL_LIMIT;
		} else {
			end = (dataList.size());
		}
		setData(start, end);
	}
	
	public class LessonPageRedirection implements ClickHandler{
		private String lessonId = null;
		
		public LessonPageRedirection(String lessonId) {
			this.lessonId = lessonId;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			PlaceRequest request = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
			request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW);
			request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID, lessonId);
			AppClientFactory.getPlaceManager().revealPlace(request);
		}
	}
}