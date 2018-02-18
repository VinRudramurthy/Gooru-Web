package org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.widgets;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.uc.H2Panel;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.SmallPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class SlnCourseReportView extends Composite {

	@UiField HTMLEventPanel unitBlock;
	@UiField H3Panel unitCountName, collectionStudyCount, assessmentStudyCount, studyTimeValue;
	@UiField PPanel unitName, studyTimeLbl, scoreLbl;
	@UiField Label numericOrder;
	@UiField SmallPanel collectionStudyLbl,assessmentStudyLbl;
	@UiField H2Panel scoreValue;
	@UiField HTMLPanel scoreRadial, timeRadial, avgScore;
	
	private static final String SCORE_RADIAL_PERCENT_LABEL = "progress-radial org-progress-";
	private static final String TIME_RADIAL_PERCENT_LABEL = "progress-radial blue-progress-";
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	private static SlnUnitReportViewUiBinder uiBinder = GWT
			.create(SlnUnitReportViewUiBinder.class);

	interface SlnUnitReportViewUiBinder extends
			UiBinder<Widget, SlnCourseReportView> {
	}
	
	public SlnCourseReportView(PlanProgressDo planProgressDo, int count) {
		initWidget(uiBinder.createAndBindUi(this));
		setDebugIds();
		setCircleContainerItems(planProgressDo, count);
		if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.STUDENT_VIEW)) {
			unitBlock.addClickHandler(new LessonPageRedirection(planProgressDo.getGooruOId()));
		} else {
			unitBlock.removeStyleName(CssTokens.CURSOR_POINTER);
		}
	}
	
	private void setDebugIds() {
		collectionStudyLbl.setText(i18n.GL3469_26());
		assessmentStudyLbl.setText(i18n.GL3469_27());
		studyTimeLbl.setText(i18n.GL3469_18());
		scoreLbl.setText(i18n.GL3469_17());
	}
	
	public void setCircleContainerItems(PlanProgressDo planProgressDo, int count) {
		numericOrder.setText(count+"");
		unitCountName.setText(planProgressDo.getTitle());
		unitName.setText(i18n.GL3281());
		collectionStudyCount.setText(planProgressDo.getCollectionsViewed()+" "+i18n.GL_GRR_OF()+" "+planProgressDo.getCollectionCount());
		int timeRadialPercent = 0;
		
		if(planProgressDo.getCollectionCount()>0) {
			timeRadialPercent = (int)(((double)planProgressDo.getCollectionsViewed() / (double)planProgressDo.getCollectionCount()) * 100);
		}
		timeRadial.setStyleName(TIME_RADIAL_PERCENT_LABEL+timeRadialPercent);
		assessmentStudyCount.setText(planProgressDo.getAssessmentsAttempted()+" "+i18n.GL_GRR_OF()+" "+planProgressDo.getAssessmentCount());
		int scorePercent = 0;
		if(planProgressDo.getAssessmentCount()>0) {
			scorePercent = (int)(((double)planProgressDo.getAssessmentsAttempted() / (double)planProgressDo.getAssessmentCount()) * 100);
		}
		scoreRadial.setStyleName(SCORE_RADIAL_PERCENT_LABEL+scorePercent);
		
		String timeSpentData = "--";
		if(planProgressDo.getTotalStudyTime()>0) {
			timeSpentData = StringUtil.getFormattedDate(planProgressDo.getTotalStudyTime(), "");
		}
		studyTimeValue.setText(timeSpentData);
		
		String avgScoreTxt = "--";
		
		if(planProgressDo.getAssessmentCount()==0) {
			avgScoreTxt = i18n.GL3469_28();
			hideScoreRadial(false);
		} else if(planProgressDo.getAssessmentsAttempted()==0) {
			avgScoreTxt = "--";
		} else {
			avgScoreTxt = planProgressDo.getScoreInPercentage()+"%";
		}
		
		if(planProgressDo.getCollectionCount()==0) {
			hideTimeRadial(false);
		}
		scoreValue.setText(avgScoreTxt);
		
		int score = planProgressDo.getScoreInPercentage();
		String scoreStyle = "";
		if(planProgressDo.getAssessmentCount()==0||planProgressDo.getAssessmentsAttempted()==0) {
			scoreStyle = "darkGrey border-bottom-white";
		} else {
			if(score<60) {
				scoreStyle = "red";
			} else if(score>=60&&score<=69) {
				scoreStyle = "org";
			} else if(score>=70&&score<=79) {
				scoreStyle = "yellowGreen";
			} else if(score>=80&&score<=89) {
				scoreStyle = "lightGreen";
			} else if(score>=90&&score<=100) {
				scoreStyle = "darkGreen";
			}
		}
		avgScore.addStyleName(scoreStyle);
		
		if(planProgressDo.getAssessmentCount()==0) {
			assessmentStudyCount.setText("--");
			assessmentStudyLbl.setVisible(false);
		}
		if(planProgressDo.getCollectionCount()==0) {
			collectionStudyCount.setText("--");
			collectionStudyLbl.setVisible(false);
		}
		
	}
	
	private void hideTimeRadial(boolean isNotVisible) {
		timeRadial.setVisible(isNotVisible);
	}
	
	private void hideScoreRadial(boolean isNotVisible) {
		scoreRadial.setVisible(isNotVisible);
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
}