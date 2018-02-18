package org.ednovo.gooru.client.mvp.analytics;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Credits;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Point;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.labels.DataLabelsData;
import org.moxieapps.gwt.highcharts.client.labels.DataLabelsFormatter;
import org.moxieapps.gwt.highcharts.client.labels.PieDataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.PiePlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions;


public class HCBarChart {
  
    public Chart pieChart(String backGroundColor,final int[] pieChatValues,final boolean isPrint) {
    	final String Colors[]={"#fac6c4","#a6caa2","#A7A9AC"};
    	final String LabelsText[]={"Incorrect","Correct","Skip"};
		final Chart chart = new Chart()
		.setType(Series.Type.PIE)
		.setChartTitleText("")
		.setWidth(130)
		.setHeight(130)
		.setBackgroundColor(backGroundColor)
		.setPlotBackgroundColor((String) null)
		.setPlotBorderWidth(null)
		.setPlotShadow(true)
		.setCredits(new Credits().setText(""))
		.setPiePlotOptions(new PiePlotOptions()
			.setAllowPointSelect(true)
			.setCursor(PlotOptions.Cursor.POINTER)
			.setPieDataLabels(new PieDataLabels()
			.setConnectorColor("#000000") //new line added
				.setEnabled(true)
				.setColor("#000000")  // new line addded
				.setDistance(-10)
				 .setFormatter(new DataLabelsFormatter() {  
					 public String format(DataLabelsData dataLabelsData) {
						 if(dataLabelsData.getYAsLong()!=0){
							 if(isPrint){
								 return "<b>" + dataLabelsData.getPointName() + "</b>(" + dataLabelsData.getYAsLong()+")";
							 }else{
								 double values =(dataLabelsData.getYAsDouble()/((double)pieChatValues[3])*100);
								 return "<b>" +Math.round(values)+"%";
							 }
						 }
						 return "";
					 }
                 }) 
			)
			.setAllowPointSelect(true)
			.setShowInLegend(false)
			
		)
         .setToolTip(new ToolTip()  
             .setFormatter(new ToolTipFormatter() {  
                 public String format(ToolTipData toolTipData) { 
                	 double values =(toolTipData.getYAsDouble()/((double)pieChatValues[3])*100);
                     return "<b>" + toolTipData.getPointName() + "</b>: " + Math.round(values) + "%";  
                 }  
             })  
         ); 
		/*.setToolTip(new ToolTip()
			.setEnabled(true)
		);*/
 
		Point[] pointArr = new Point[3];
		for(int i = 0; i <3; i++){
			Point pointArray = new Point(LabelsText[i],pieChatValues[i]).setColor(Colors[i]);
			pointArr[i] = pointArray;
		}

		PiePlotOptions options = new PiePlotOptions();
		options.setSize(1.0);
		options.setInnerSize(0.20);
		chart.addSeries(chart.createSeries()
				.setPoints(pointArr)
			);
    	
		return chart;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public Chart createTestChart() {  
    	  
        final Chart chart = new Chart()  
            .setType(Series.Type.PIE)  
            .setWidth100()
            .setHeight100()
            .setChartTitleText("Browser market shares at a specific website, 2010")  
            .setPlotBackgroundColor((String) null)  
            .setPlotBorderWidth(null)  
            .setPlotShadow(false)  
            .setPiePlotOptions(new PiePlotOptions()  
                .setAllowPointSelect(true)  
                .setCursor(PlotOptions.Cursor.POINTER)  
                .setPieDataLabels(new PieDataLabels()  
                    .setConnectorColor("#000000")  
                    .setEnabled(true)  
                    .setColor("#000000")  
                    .setFormatter(new DataLabelsFormatter() {  
                        public String format(DataLabelsData dataLabelsData) {  
                            return "<b>" + dataLabelsData.getPointName() + "</b>: " + dataLabelsData.getYAsDouble() + " %";  
                        }  
                    })  
                )  
            )  
            .setLegend(new Legend()  
                .setLayout(Legend.Layout.VERTICAL)  
                .setAlign(Legend.Align.RIGHT)  
                .setVerticalAlign(Legend.VerticalAlign.TOP)  
                .setX(-100)  
                .setY(100)  
                .setFloating(true)  
                .setBorderWidth(1)  
                .setBackgroundColor("#FFFFFF")  
                .setShadow(true)  
            )  
            .setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                        return "<b>" + toolTipData.getPointName() + "</b>: " + toolTipData.getYAsDouble() + " %";  
                    }  
                })  
            );  
  
        chart.addSeries(chart.createSeries()  
            .setName("Browser share")  
            .setPoints(new Point[]{  
                new Point("Firefox", 45.0),  
                new Point("IE", 26.8),  
                new Point("Chrome", 12.8)  
                    .setSliced(true)  
                    .setSelected(true),  
                new Point("Safari", 8.5),  
                new Point("Opera", 6.2),  
                new Point("Others", 0.7)  
            })  
        );  
        return chart;  
    }  
}
