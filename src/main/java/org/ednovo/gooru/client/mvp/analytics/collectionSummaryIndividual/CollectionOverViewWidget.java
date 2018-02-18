package org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsReactionWidget;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CollectionOverViewWidget extends Composite implements ClientConstants {

	private static CollectionOverViewWidgetUiBinder uiBinder = GWT
			.create(CollectionOverViewWidgetUiBinder.class);

	interface CollectionOverViewWidgetUiBinder extends
			UiBinder<Widget, CollectionOverViewWidget> {
	}
	private static MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField Label lblViews,lblAvgReaction,collectionOverViewlbl,timeSpentTxtlbl,totalTimeSpentlbl,totalViewlbl;
	@UiField HTMLPanel totalAvgReactionlbl;
	
	/**
	 * Constructor
	 */
	public CollectionOverViewWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		setStaticData();
	}
	/**
	 * This method is used to set static data
	 */
	void setStaticData(){
		
		StringUtil.setAttributes(totalAvgReactionlbl.getElement(), "pnlTotalAvgReactionlbl", null, null);
		
		StringUtil.setAttributes(collectionOverViewlbl.getElement(), "lblCollectionOverView", i18n.GL2274(), i18n.GL2274());
		StringUtil.setAttributes(timeSpentTxtlbl.getElement(), "lblTimeSpentTxt", null, null);
		StringUtil.setAttributes(totalTimeSpentlbl.getElement(), "lblTotalTimeSpent", null, null);
		StringUtil.setAttributes(totalViewlbl.getElement(), "lblTotalViewlbl", null, null);
		StringUtil.setAttributes(lblViews.getElement(), "lblViews", i18n.GL2276(), i18n.GL2276());
		StringUtil.setAttributes(lblAvgReaction.getElement(), "lblAvgReaction", i18n.GL2277(), i18n.GL2277());
		
	}
	/**
	 * This method is used to set the data.
	 * @param result
	 * @param isTeacherSummary
	 */
	public void setData(final CollectionSummaryMetaDataDo result, final boolean isTeacherSummary){
		GWT.runAsync(new SimpleRunAsyncCallback() {
			
			@Override
			public void onSuccess() {

				collectionOverViewlbl.getElement().getStyle().setTextAlign(TextAlign.LEFT);
				if(isTeacherSummary){
					timeSpentTxtlbl.setText(i18n.GL2230());
				}else{
					timeSpentTxtlbl.setText(i18n.GL2275());
				}
				totalTimeSpentlbl.setText(AnalyticsUtil.getTimeSpent(result.getAvgTimeSpent()));
				totalViewlbl.setText(Integer.toString(result.getViews()));
				totalAvgReactionlbl.clear();
				totalAvgReactionlbl.add(new AnalyticsReactionWidget(result.getAvgReaction()));
			
			}
		});
	}

}
