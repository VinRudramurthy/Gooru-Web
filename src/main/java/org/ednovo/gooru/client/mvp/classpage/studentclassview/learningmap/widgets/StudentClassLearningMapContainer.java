package org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.widgets;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.PPanel;
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

public class StudentClassLearningMapContainer extends Composite {

	@UiField HTMLEventPanel unitBlock, leftArrow, rightArrow;
	@UiField HTMLPanel circleContainer;
	@UiField H3Panel unitCountName;
	@UiField PPanel unitName;
	@UiField Label numericOrder;
	
	private String unitId = null;
	
	ArrayList<PlanProgressDo> dataList = new ArrayList<PlanProgressDo>();
	
	int start = 0, end = 0;

	private final static int CAROUSEL_LIMIT = 11;
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	private static StudentClassLearningMapContainerUiBinder uiBinder = GWT
			.create(StudentClassLearningMapContainerUiBinder.class);

	interface StudentClassLearningMapContainerUiBinder extends
			UiBinder<Widget, StudentClassLearningMapContainer> {
	}

	public StudentClassLearningMapContainer(PlanProgressDo planProgressDo, int count) {
		initWidget(uiBinder.createAndBindUi(this));
		this.unitId = planProgressDo.getGooruOId();
		setCircleContainerItems(planProgressDo, count);
		unitBlock.addClickHandler(new LessonPageRedirection(unitId));
	}
	
	public void setCircleContainerItems(PlanProgressDo planProgressDo, int count) {
		numericOrder.setText((count+1)+"");
		unitCountName.setText(planProgressDo.getTitle());
		unitName.setText(i18n.GL3281());
		int size = planProgressDo.getItem().size();
		if(size>0) {
			dataList = planProgressDo.getItem();
			start = 0;
			if(size < CAROUSEL_LIMIT+1) {
				end = size;
			} else {
				end = CAROUSEL_LIMIT;
			}
			setData(start,end);
		} else {
			setArrowVisibility(false,false);
			circleContainer.add(StringUtil.getStudentPlanErrorLbl(i18n.GL3469_5(), "error-lbl-student-course-plan"));
		}
	}
	
	public class LessonPageRedirection implements ClickHandler{
		private String unitId = null;
		
		public LessonPageRedirection(String unitId) {
			this.unitId = unitId;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			PlaceRequest request = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
			request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW);
			request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID, unitId);
			AppClientFactory.getPlaceManager().revealPlace(request);
		}
		
	}
	
	private void setData(int startPoint, int endPoint) {
		circleContainer.clear();
		for(int z=startPoint;z<endPoint;z++) {
			circleContainer.add(new StudentClassLearningMapCircle(dataList.get(z), unitId));
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

}