package org.ednovo.gooru.client.mvp.analytics.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.analytics.ChartMetaDataOptions;
import org.ednovo.gooru.application.shared.model.analytics.GradeJsonData;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.Style;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.labels.AxisLabelsData;
import org.moxieapps.gwt.highcharts.client.labels.AxisLabelsFormatter;
import org.moxieapps.gwt.highcharts.client.labels.Labels;
import org.moxieapps.gwt.highcharts.client.labels.XAxisLabels;
import org.moxieapps.gwt.highcharts.client.labels.YAxisLabels;

import com.google.gwt.user.client.ui.HTMLPanel;

public class HCLineChart {
	final Chart chart = new Chart();


/**
 * This method is used to create a line chart
 * @param gradeData
 * @return
 */
public HTMLPanel chart(ArrayList<GradeJsonData> gradeData){
		HTMLPanel studyChartContainer=new HTMLPanel("");
		ChartMetaDataOptions chartmetadata=new ChartMetaDataOptions();
		chartmetadata.setHeight(200);
		chartmetadata.setShowLegends(true);

		int size=gradeData.size();
		Number averageTime[]=new Number[size];
	    Number suggestedTime[]=new Number[size];

		Number minimumScore[]=new Number[size];
	    Number averageScore[]=new Number[size];

	    for(int i=0;i<size;i++){
	    	if(gradeData.get(i).getEstimatedTime()!=null){
	    		String estimatedTime=gradeData.get(i).getEstimatedTime().replaceAll("hrs", ":").replaceAll("mins", "").trim();
	    		if(estimatedTime.contains(":")){
	    		String[] convertMins=estimatedTime.split(":");
	    		int convertedEstimateTime=(Integer.parseInt(convertMins[0].trim())*60)+(Integer.parseInt(convertMins[1].trim()));
	    		if(gradeData.get(i).getAvgTimeSpent()!=0){
	    			String avgTimeSpent=getTimeSpent(gradeData.get(i).getAvgTimeSpent()).replaceAll("min", ":").replaceAll("sec", "").trim();
	    			String[] convertavgTimeSpentMins=avgTimeSpent.split(":");
		    		int convertedAvgTimeSpent=(Integer.parseInt(convertavgTimeSpentMins[0].trim()))+(Integer.parseInt(convertavgTimeSpentMins[1].trim()));
		    		averageTime[i]=convertedAvgTimeSpent;
	    		}
	    		suggestedTime[i]=convertedEstimateTime;
		    	}else{
		    		averageTime[i]=0;
		    		suggestedTime[i]=0;
		    	}
	    		if(gradeData.get(i).getMinimumScore()!=null){
		    		try{
		        	int miniscoreVal=Integer.parseInt(gradeData.get(i).getMinimumScore());
		        	minimumScore[i]=miniscoreVal;
			    	averageScore[i]= miniscoreVal-(miniscoreVal/2);
		    		}catch(Exception ex){
		    			AppClientFactory.printSevereLogger("HCLineChart: chart : "+ex.getMessage());
		    		}
		    	}else{
		    		minimumScore[i]=0;
		    		averageScore[i]=0;
		    	}
	    	}else{
	    		minimumScore[i]=0;
	    		averageScore[i]=0;
	    		averageTime[i]=0;
	    		suggestedTime[i]=0;
	    	}
	    }

	    String category[]=new String[size];
	    for(int i=0;i<size;i++) {
	    	category[i] = String.valueOf(i+1);
	    }

	    List<Number[]> numberslineChart1=new ArrayList<Number[]>();
	    numberslineChart1.add(averageTime);
	    numberslineChart1.add(suggestedTime);

	    List<Number[]> numberslineChart2=new ArrayList<Number[]>();
	    numberslineChart2.add(minimumScore);
	    numberslineChart2.add(averageScore);

		List<String> contentListNew=new ArrayList<String>();
		String subjects[]={"Average Time","Suggested Time"};
		for(String subject:subjects){
			contentListNew.add(subject);
		}
		List<String> contentListNew1=new ArrayList<String>();
		String subjects1[]={"Minimum Score","Average Score"};
		for(String subject:subjects1){
			contentListNew1.add(subject);
		}
		Map<String,Number[]> data = new HashMap<String,Number[]>();
		Map<String,Number[]> data1 = new HashMap<String,Number[]>();
		for(int i=0;i<contentListNew.size();i++){
			data.put(contentListNew.get(i), numberslineChart1.get(i));
			data1.put(contentListNew1.get(i), numberslineChart2.get(i));
		}

		chartmetadata.setShowXaxisTop(true);
		LineChartView lineChartView=new LineChartView();
		lineChartView.createLineChart("", "", category, contentListNew, data, chartmetadata,true);

		chartmetadata.setShowXaxisTop(false);
		LineChartView lineChartView1=new LineChartView();
		lineChartView1.createLineChart("", "", category, contentListNew1, data1, chartmetadata,false);

		Map<String,Number[]> data2 = new HashMap<String,Number[]>();
		LineChartView lineChartView2=new LineChartView();
		List<String> contentListNew2=new ArrayList<String>();
		lineChartView2.createLineChart("Average Reaction","", category, contentListNew2, data2, chartmetadata,false);

		Map<String,Number[]> data3 = new HashMap<String,Number[]>();
		LineChartView lineChartView3=new LineChartView();
		lineChartView3.createLineChart("Student Completion","", category, contentListNew2, data3, chartmetadata,false);

		studyChartContainer.add(lineChartView);
		studyChartContainer.add(lineChartView1);
		//studyChartContainer.add(lineChartView2);
		//studyChartContainer.add(lineChartView3);

		return studyChartContainer;
	}

/**
 * This is used to convert long time format
 * @param commentCreatedTime
 * @return
 */
private String getTimeSpent(Long commentCreatedTime) {
	String createdTime = null;
	int seconds = (int) (commentCreatedTime / 1000) % 60 ;
	int minutes = (int) ((commentCreatedTime / (1000*60)) % 60);
	int hours   = (int) ((commentCreatedTime / (1000*60*60)) % 24);
	int days = (int) (commentCreatedTime / (1000*60*60*24));
	if(days!=0){
		createdTime=days+":";
	}
	if(hours!=0){
		if(createdTime!=null){
			createdTime=createdTime+hours+" ";
		}else{
			createdTime=hours+" ";
		}
	}
	if(minutes!=0){
		if(createdTime!=null){
			createdTime=createdTime+((minutes<10)?"0"+minutes+"min":minutes+"min")+" ";
		}else{
			createdTime=((minutes<10)?"0"+minutes+"min":minutes+"min")+" ";
		}
	}
	if(seconds!=0){
		if(createdTime!=null){
			createdTime=createdTime+((seconds<10)?"0"+seconds+"sec":seconds+"sec")+"";
		}else{
			createdTime="0min"+" "+((seconds<10)?"0"+seconds+"sec":seconds+"sec")+"";
		}
	}
	return createdTime;
}

	/**
	 * This method is used to create chart line
	 * @param categories
	 * @param legend
	 * @param data
	 * @param chartmetadata
	 * @param isFirst
	 * @return
	 */
	public Chart createChartLine(String[] categories, List<String> legend, Map<String, Number[]> data,ChartMetaDataOptions chartmetadata,boolean isFirst) {

		String[] colors={"red","green","blue","gray"};
		chart.setType(Series.Type.LINE)
	        .setZoomType(Chart.ZoomType.X_AND_Y)
			.setChartTitle(new ChartTitle()
				.setText("")
			)
			.setHeight(chartmetadata.getHeight())
			.setWidth(chartmetadata.getWidth())
			.setCredits(new Credits().setText(""))
			.setToolTip(new ToolTip()
				.setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						if(toolTipData.getSeriesName().equalsIgnoreCase("Average Score") || toolTipData.getSeriesName().equalsIgnoreCase("Minimum Score")){
							return ""+toolTipData.getYAsDouble();
						}else{
							long hours = Math.round(toolTipData.getYAsDouble()) / 60;
							long minutes = Math.round(toolTipData.getYAsDouble()) % 60;
							return hours+"hrs "+minutes+"mins";
						}
					}
				})
			);
		chart.getXAxis().setCategories(categories);

		/*if(chartmetadata.isShowXaxisTop()){
				chart.getXAxis().setOpposite(true);
		 } else {
			 	chart.getXAxis().setLabels(new XAxisLabels().setEnabled(true));
				chart.getXAxis().setLineColor("transparent");
				chart.getXAxis().setLineWidth(0);
				chart.getXAxis().setTickWidth(0);
				chart.getXAxis().setTickLength(0);
		 }*/
		chart.getXAxis().setAxisTitleText("Assignments");
		if(chartmetadata.getyAxisTitle()!=null&&!chartmetadata.getyAxisTitle().isEmpty()) {
			chart.getYAxis().setAxisTitleText(chartmetadata.getyAxisTitle());
		} else {
			chart.getYAxis().setAxisTitleText("");
		}
		if(isFirst){
			chart.getYAxis()
	        .setAxisTitleText("")
	        .setLineWidth(1)
	        .setLabels(new YAxisLabels()
	            .setFormatter(
	                new AxisLabelsFormatter() {
	                    public String format(AxisLabelsData axisLabelsData) {
	                    	long hours = Math.round( axisLabelsData.getValueAsLong()) / 60;
							long minutes = Math.round( axisLabelsData.getValueAsLong()) % 60;
							return hours+"hrs "+minutes+"mins";
	                    }
	                }
	            )
	        );
		}else{
			chart.getYAxis()
			.setLineWidth(1)
			.setLabels(new YAxisLabels().setEnabled(true));
		}
		//chart.getYAxis().setGridLineWidth(0);
		//chart.getYAxis().setGridLineColor("transparent");

		chart.getYAxis().setMin(0);
		if(categories.length>15) {
			chart.getXAxis()
			.setLabels(new XAxisLabels()
	            .setRotation(-45)
	            .setAlign(Labels.Align.RIGHT)
	            .setStyle(new Style()
	                .setFont("normal 10px Verdana, sans-serif")
	            )
	        );
		}
       for(int series=0; series<legend.size();series++) {
			chart.addSeries(chart.createSeries()
					.setName(legend.get(series))
					.setPoints(data.get(legend.get(series))));
				/*	.setColors(colors[series]);*/
		}

		if(chartmetadata.getyMaxValue()!=0){
			chart.getYAxis().setMax(chartmetadata.getyMaxValue());
		}
		if(chartmetadata.isShowLegends()){
			chart.setLegend(new Legend().setEnabled(true));
		}

		return chart;
	}
}