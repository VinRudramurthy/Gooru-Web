package org.ednovo.gooru.client.mvp.analytics.util;

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.analytics.ChartMetaDataOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : CollectionChartView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 24-Jun-2014
 *
 * @Author ibc
 *
 * @Reviewer:
 */
public class LineChartView extends Composite {

	/*@UiField Label option1,option2;*/
	@UiField HTMLPanel chartContainer;
	private static LineChartViewUiBinder uiBinder = GWT	.create(LineChartViewUiBinder.class);
	public MessageProperties msgProperties = GWT.create(MessageProperties.class);

	interface LineChartViewUiBinder extends UiBinder<Widget, LineChartView> {
	}
	@UiField CollectionChartCBundle res;

	/**
	 * Constructor
	 */
	public LineChartView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	/**
	 * This method is used to create a line.
	 * @param title1
	 * @param title2
	 * @param categories
	 * @param contentList
	 * @param data
	 * @param chartmetadata
	 * @param isFirst
	 */
	public void createLineChart(String title1,String title2,String []categories,List<String> contentList,Map<String,Number[]> data,ChartMetaDataOptions chartmetadata,boolean isFirst){
	/*	option1.setText(title1);
		option2.setText(title2);*/
		chartContainer.add(new HCLineChart().createChartLine(categories,contentList,data,chartmetadata,isFirst));
	}
}