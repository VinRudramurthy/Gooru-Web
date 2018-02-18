package org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.widgets;

import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.client.uc.EmPanel;
import org.ednovo.gooru.client.uc.SpanPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SlnContentWidget extends Composite {
	
	@UiField SpanPanel collectionViews, assessmentViews;
	@UiField HTMLPanel assessmentCountData, collectionCountData, assessmentRadial, collectionRadial, contentPanel;
	@UiField Label score;
	@UiField EmPanel timeSpent;
	@UiField Image contentImageStyle;
	
	private static final String ASSESSMENT_RADIAL = "-progress-";
	
	private static SlnContentWidgetUiBinder uiBinder = GWT
			.create(SlnContentWidgetUiBinder.class);

	interface SlnContentWidgetUiBinder extends
			UiBinder<Widget, SlnContentWidget> {
	}

	public SlnContentWidget(PlanProgressDo planProgressDo, String lastItem) {
		initWidget(uiBinder.createAndBindUi(this));
		setData(planProgressDo,lastItem);
	}

	public void setData(PlanProgressDo planProgressDo, String lastItem) {
		String type = "collection";
		
		if(planProgressDo.getType()!=null) {
			type = planProgressDo.getType();
		}
		
		setContentIcon(type);
		int views = planProgressDo.getViews();
		if(type.equalsIgnoreCase("assessment")||type.equalsIgnoreCase("assessment/url")) {
			assessmentViews.setText(views+"");
			String scoreValue = "--";
			int scoreLbl = planProgressDo.getScoreInPercentage();
			if(views>0&&scoreLbl>=0) {
				scoreValue = scoreLbl+"%";
			}
			score.setText(scoreValue);
			
			assessmentRadial.addStyleName(StringUtil.getHighlightStyle(scoreLbl)+ASSESSMENT_RADIAL+scoreLbl);
			collectionCountData.setVisible(false);
			collectionRadial.setVisible(false);
		} else if(type.equalsIgnoreCase("collection")){
			collectionViews.setText(views+"");
			String timeSpentData = "--";
			if(planProgressDo.getTimeSpent()>0) {
				timeSpentData = StringUtil.getFormattedDate(planProgressDo.getTimeSpent(), "");
			}
			timeSpent.setText(timeSpentData);
			assessmentCountData.setVisible(false);
			assessmentRadial.setVisible(false);
		}
		if(!lastItem.isEmpty()) {
			contentPanel.addStyleName(lastItem);
		}
	}
	
	private void setContentIcon(String icon) {
		if(icon.equalsIgnoreCase("assessment")||icon.equalsIgnoreCase("assessment/url")) {
			contentImageStyle.setUrl("../images/folders/panel/assessment-smal.png");
		} else {
			contentImageStyle.setUrl("../images/folders/panel/collection-icon-small.png");
		}
	}
}