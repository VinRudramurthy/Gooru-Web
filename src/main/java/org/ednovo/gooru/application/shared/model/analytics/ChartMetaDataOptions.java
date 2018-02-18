/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 * 
 *  http://www.goorulearning.org/
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package org.ednovo.gooru.application.shared.model.analytics;

import java.io.Serializable;
/**
 * @fileName : ChartMetaDataOptions.java
 * 
 * @Author :Gooru Team
 * 
 * @Reviewer:
 */
public class ChartMetaDataOptions implements Serializable {
	private static final long serialVersionUID = -4629006109833216512L;
	
	private Integer height;
	private Integer width;
	private Integer rotation;
	private boolean isClickable;
	private boolean isPercentage;
	private String title;
	private boolean isDonut = true;
	private String xAxisTitle;
	private String yAxisTitle;
	private int yMinValue;
	private int yMaxValue;
	private boolean isLegendBottom = false;
	private boolean showXaxisTop=false;
	private boolean showLegends=true;
	private boolean showGridLines=true;
	
	
	public boolean isShowXaxisTop() {
		return showXaxisTop;
	}
	public void setShowXaxisTop(boolean showXaxisTop) {
		this.showXaxisTop = showXaxisTop;
	}
	public boolean isShowGridLines() {
		return showGridLines;
	}
	public void setShowGridLines(boolean showGridLines) {
		this.showGridLines = showGridLines;
	}
	public boolean isShowLegends() {
		return showLegends;
	}
	public void setShowLegends(boolean showLegends) {
		this.showLegends = showLegends;
	}
	public int getyMinValue() {
		return yMinValue;
	}
	public void setyMinValue(int yMinValue) {
		this.yMinValue = yMinValue;
	}
	public int getyMaxValue() {
		return yMaxValue;
	}
	public void setyMaxValue(int yMaxValue) {
		this.yMaxValue = yMaxValue;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public boolean isClickable() {
		return isClickable;
	}
	public void setClickable(boolean isClickable) {
		this.isClickable = isClickable;
	}
	public boolean isPercentage() {
		return isPercentage;
	}
	public void setPercentage(boolean isPercentage) {
		this.isPercentage = isPercentage;
	}
	public boolean isDonut() {
		return isDonut;
	}
	public void setDonut(boolean isDonut) {
		this.isDonut = isDonut;
	}
	public Integer getRotation() {
		return rotation;
	}
	public void setRotation(Integer rotation) {
		this.rotation = rotation;
	}
	public String getxAxisTitle() {
		return xAxisTitle;
	}
	public void setxAxisTitle(String xAxisTitle) {
		this.xAxisTitle = xAxisTitle;
	}
	public String getyAxisTitle() {
		return yAxisTitle;
	}
	public void setyAxisTitle(String yAxisTitle) {
		this.yAxisTitle = yAxisTitle;
	}
	public boolean isLegendBottom() {
		return isLegendBottom;
	}
	public void setLegendBottom(boolean isLegendBottom) {
		this.isLegendBottom = isLegendBottom;
	}
}