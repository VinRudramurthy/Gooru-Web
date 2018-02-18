package org.ednovo.gooru.client.util;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.moxieapps.gwt.highcharts.client.AxisTitle;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.PlotLine;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.Style;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.labels.AxisLabelsData;
import org.moxieapps.gwt.highcharts.client.labels.AxisLabelsFormatter;
import org.moxieapps.gwt.highcharts.client.labels.YAxisLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.ColumnPlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.Marker;
import org.moxieapps.gwt.highcharts.client.plotOptions.SplinePlotOptions;

import com.google.gwt.i18n.client.DateTimeFormat;
  
public class ProfileAnalyticsChat{  
  
	final String GREEN_COLOR="#89A54E";
	final String ORANGE_COLOR="#F6A026";
	final String DOTTED_COLOR="#AA4643";
	final String RECT_COLOR="#97D2C4";
	private static final String DATE_FORMAT="MM/dd/yyyy hh:mm:ss aaa";
	Chart chart; 
	public static String getCreatedTime(String commentCreatedTime) {
		String createdTime = null;
		Long commentTime = Long.parseLong(commentCreatedTime);
		Date currentDate = new Date(commentTime);
		DateTimeFormat fmt = DateTimeFormat.getFormat (DATE_FORMAT);
		createdTime = fmt.format (currentDate);
		return createdTime;
	}
	public Chart createChart() {  
		String[] monthArray={"Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    	String[] monthArrayNew = new String[12];
    		chart = new Chart()  
        	.setChartTitleText("Activity Breakdown")
            .setZoomType(Chart.ZoomType.X_AND_Y)  
            .setHeight(200)
            .setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                      /*  String unit = "mm";  
                        if ("Flagged".equals(toolTipData.getSeriesName())) {  
                            unit = "Â°C";  
                        } else if ("Sea-Level Pressure".equals(toolTipData.getSeriesName())) {  
                            unit = "mb";  
                        }  */
                        return toolTipData.getXAsString() + ": " + toolTipData.getYAsDouble() + " " ;  
                    }  
                })  
            )  
            .setLegend(new Legend()  
                .setLayout(Legend.Layout.VERTICAL)  
                .setAlign(Legend.Align.LEFT)  
                .setVerticalAlign(Legend.VerticalAlign.TOP)  
                .setX(40)  
                .setY(10)  
                .setFloating(true)  
                .setBackgroundColor("#FFFFFF")  
            );  
        Date date = new Date();
        int month=date.getMonth();
        int monthVal=month+1;
        int year=date.getYear()-1;
        for(int i=0;i<12;i++){
        	if(monthVal>=12){
        		monthVal=0;
        		year=year+1;
        	}
        	if(i==0){
        		monthArrayNew[i]=monthArray[monthVal]+"'"+Integer.toString(year).substring(1, 3);
        	}else if(i==11){
        		monthArrayNew[i]=monthArray[monthVal]+"'"+Integer.toString(year).substring(1, 3);
        	}else{
        		monthArrayNew[i]=monthArray[monthVal];
        	}
        	monthVal++;
        }
        chart.getXAxis()  
          .setCategories(monthArrayNew);
  
     // Primary yAxis  
        chart.getYAxis(0)   
            .setLabels(new YAxisLabels()  
                .setStyle(new Style()  
                    .setColor(GREEN_COLOR)  
                )  
                .setFormatter(new AxisLabelsFormatter() {  
                    public String format(AxisLabelsData axisLabelsData) {  
                        return axisLabelsData.getValueAsLong() + "";  
                    }  
                })  
            )  
            .setAxisTitle(new AxisTitle()  
                .setText("")  
                .setStyle(new Style()  
                    .setColor(GREEN_COLOR)  
                )  
            )  
            .setGridLineWidth(1);  
  
        // Secondary yAxis  
        chart.getYAxis(1)  
            .setLabels(new YAxisLabels()  
                .setStyle(new Style()  
                    .setColor(RECT_COLOR)  
                )  
                .setFormatter(new AxisLabelsFormatter() {  
                    public String format(AxisLabelsData axisLabelsData) {  
                        return axisLabelsData.getValueAsLong() + "";  
                    }  
                })  
            )  
            .setAxisTitle(new AxisTitle()  
                .setText("")  
                .setStyle(new Style()  
                    .setColor(RECT_COLOR)  
                )  
            )  
            .setGridLineWidth(1)
            .setOpposite(true);
  
        // Tertiary yAxis  
        chart.getYAxis(2)  
            .setLabels(new YAxisLabels()  
                .setStyle(new Style()  
                    .setColor(DOTTED_COLOR)  
                )  
                .setFormatter(new AxisLabelsFormatter() {  
                    public String format(AxisLabelsData axisLabelsData) {  
                        return axisLabelsData.getValueAsLong() + "";  
                    }  
                })  
            )  
            .setAxisTitle(new AxisTitle()  
                .setText("")  
                .setStyle(new Style()  
                    .setColor(DOTTED_COLOR)  
                )  
            )  
            .setGridLineWidth(1)  
            .setOpposite(true);  
        
        // fourth yAxis  
        chart.getYAxis(3)  
            .setLabels(new YAxisLabels()  
                .setStyle(new Style()  
                    .setColor(ORANGE_COLOR)  
                )  
                .setFormatter(new AxisLabelsFormatter() {  
                    public String format(AxisLabelsData axisLabelsData) {  
                        return axisLabelsData.getValueAsLong() + "";  
                    }  
                })  
            )  
            .setAxisTitle(new AxisTitle()  
                .setText("")  
                .setStyle(new Style()  
                    .setColor(ORANGE_COLOR)  
                )  
            )  
            .setGridLineWidth(1)  
            .setOpposite(true);  
        
        //Add chat data
        chart.addSeries(chart.createSeries()  
            .setName("Flagged")  
            .setType(Series.Type.COLUMN)  
            .setPlotOptions(new ColumnPlotOptions()  
                .setColor(RECT_COLOR)  
            )  
            .setYAxis(1)  
            .setPoints(new Number[]{  
                49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4  
            })  
        );  
        chart.addSeries(chart.createSeries()  
            .setName("Views")  
            .setType(Series.Type.SPLINE)  
            .setPlotOptions(new SplinePlotOptions()  
                .setColor(DOTTED_COLOR)  
                .setMarker(new Marker()  
                    .setEnabled(false)  
                )  
                .setDashStyle(PlotLine.DashStyle.SHORT_DOT)  
            )  
            .setYAxis(2)  
            .setPoints(new Number[]{  
                1016, 1016, 1015.9, 1015.5, 1012.3, 1009.5, 1009.6, 1010.2, 1013.1, 1016.9, 1018.2, 1016.7  
            })  
        );  
        chart.addSeries(chart.createSeries()  
            .setName("Times Shared")  
            .setType(Series.Type.SPLINE)  
            .setPlotOptions(new SplinePlotOptions()  
                .setColor(GREEN_COLOR)  
            )  
            .setPoints(new Number[]{  
                7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6  
            })  
        );  
        chart.addSeries(chart.createSeries()  
                .setName("Added to collection")  
                .setType(Series.Type.SPLINE)  
                .setPlotOptions(new SplinePlotOptions()  
                    .setColor(ORANGE_COLOR)  
                )  
                .setYAxis(3) 
                .setPoints(new Number[]{  
                    100, 200, 90.5, 140.5, 180.0, 210.5, 250, 260.5, 230.3, 180, 130, 90  
                })  
            );  
        return chart;  
    }  
	public void updateProfileAnalyticsFlaggedChatData(Map<String, Integer> result){
		Number dataPassed[]=new Number[12];
		Map<String, Integer> map = new TreeMap<String, Integer>(result); 
		if(chart!=null){
			for(Map.Entry<String, Integer> entry : map.entrySet()) {
				dataPassed[Integer.parseInt(getCreatedTime(entry.getKey()).substring(0, 2))]=entry.getValue();
			}
			chart.getSeries()[0].setPoints(dataPassed);
		}
	}
	public void updateProfileAnalyticsViewsChatData(Map<String, Integer> result){
		Number dataPassed[]=new Number[12];
		Map<String, Integer> map = new TreeMap<String, Integer>(result); 
		if(chart!=null){
			for(Map.Entry<String, Integer> entry : map.entrySet()) {
				dataPassed[Integer.parseInt(getCreatedTime(entry.getKey()).substring(0, 2))]=entry.getValue();
			}
			chart.getSeries()[1].setPoints(dataPassed);
		}
	}
	public void updateProfileAnalyticsSharedChatData(Map<String, Integer> result){
		Number dataPassed[]=new Number[12];
		Map<String, Integer> map = new TreeMap<String, Integer>(result); 
		if(chart!=null){
			for(Map.Entry<String, Integer> entry : map.entrySet()) {
				dataPassed[Integer.parseInt(getCreatedTime(entry.getKey()).substring(0, 2))]=entry.getValue();
			}
			chart.getSeries()[2].setPoints(dataPassed);
		}
	}
	
	public void updateProfileAnalyticsAddedToCollectionChatData(Map<String, Integer> result){
		Number dataPassed[]=new Number[12];
		Map<String, Integer> map = new TreeMap<String, Integer>(result); 
		if(chart!=null){
			for(Map.Entry<String, Integer> entry : map.entrySet()) {
				dataPassed[Integer.parseInt(getCreatedTime(entry.getKey()).substring(0, 2))]=entry.getValue();
			}
			chart.getSeries()[3].setPoints(dataPassed);
		}
	}
}  
