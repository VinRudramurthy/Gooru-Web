package org.ednovo.gooru.client.mvp.analytics.util;

import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class StudentScoredAboveBelowUlPanel extends Composite {

	private static StudentScoredAboveBelowUlPanelUiBinder uiBinder = GWT
			.create(StudentScoredAboveBelowUlPanelUiBinder.class);

	interface StudentScoredAboveBelowUlPanelUiBinder extends
			UiBinder<Widget, StudentScoredAboveBelowUlPanel> {
	}

	/*@UiField Image userImage;*/
	@UiField Label namelbl,scorelbl,timelbl;
	/*@UiField HTMLPanel reactionlbl;*/

	/**
	 * Constructor
	 * @param userData
	 * @param isHeading
	 */
	public StudentScoredAboveBelowUlPanel(UserDataDo userData,boolean isHeading) {
		initWidget(uiBinder.createAndBindUi(this));
		setData(userData,isHeading);
	}

	/**
	 * This will set the data
	 * @param userData
	 * @param isHeading
	 */
	void setData(UserDataDo userData,boolean isHeading){
		if(isHeading){
			namelbl.setText("Username");
			scorelbl.setText("Score");
			timelbl.setText("Time");
		}else{
			namelbl.setText(userData.getUserName());
			if(userData.getGradeInPercentage()!=null){
				scorelbl.setText(userData.getGradeInPercentage()+"");
			}else{
				scorelbl.setText("--");
			}
			if(userData.getTimeSpent()!=0){
				timelbl.setText(AnalyticsUtil.getTimeSpent(userData.getTimeSpent()));
			}else{
				timelbl.setText("--");
			}
			/*reactionlbl.add(new AnalyticsReactionWidget(userData.getReaction()));*/
		}
	}
}
