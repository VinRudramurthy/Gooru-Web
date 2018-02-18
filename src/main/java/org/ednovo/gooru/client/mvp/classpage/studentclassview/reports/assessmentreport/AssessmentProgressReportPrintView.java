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
package org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.assessmentreport;

import java.util.Map;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.H2Panel;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.H4Panel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru Team
 *
 */
public class AssessmentProgressReportPrintView extends Composite implements ClientConstants {

	@UiField FlowPanel printblock,titleblock,firstBlock,firstInnerBlock,firstInnerSubBlock,secondInnerSubBlock,detailsBlock,titleClear,
	secondBlock,secondInnerBlock,thirdBlock,thirdInnerBlock,thirdInnerSubBlock,thirdInnerSub2Block,progressRadial,fourthBlock,fourthInnerBlock,
	customSelect,clearfix;
	@UiField HTMLPanel title;
	@UiField Image img;
	@UiField H3Panel collectionTitle;
	@UiField H4Panel scoreTitle;
	@UiField H2Panel score;
	@UiField PPanel collectionResourcesCount,goal,lastModifiedTime;
	@UiField InlineLabel correctStatus;
	@UiField ListBox sessionsDropDown;

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	private static AssessmentProgressReportPrintViewUiBinder uiBinder = GWT.create(AssessmentProgressReportPrintViewUiBinder.class);

	interface AssessmentProgressReportPrintViewUiBinder extends UiBinder<Widget, AssessmentProgressReportPrintView> {
	}


	public AssessmentProgressReportPrintView(Map<String, String> printMap) {
		initWidget(uiBinder.createAndBindUi(this));
		
		setStaticStyle(printMap);
	}


	private void setStaticStyle(Map<String, String> printMap) {
		
		printblock.getElement().setAttribute("style", "width:1000px; margin:0 auto; font-family:Arial, Helvetica, sans-serif;");
		titleblock.getElement().setAttribute("style", "background-color:#515151; border-bottom: 1px solid #ddd; padding:6px 15px;");
		title.getElement().setInnerText(printMap.get("collectionSummaryText"));
		title.getElement().setAttribute("style", "width:50%; text-align:left; float:left; color: #fafafa; font-size: 20px; font-weight: bold;padding: 5px;");
		
		titleClear.getElement().setAttribute("style", "clear:both");
		firstBlock.getElement().setAttribute("style", "background-color: #fff; border-bottom: 1px solid #ddd; padding: 0 0;  float:left; width:100%;");
		firstInnerBlock.getElement().setAttribute("style", "width:41.6667%; float:left");
		firstInnerSubBlock.getElement().setAttribute("style", "padding:14px 0; min-height:100px;");
		secondInnerSubBlock.getElement().setAttribute("style", "height: 60px; width: 95px; display: inline-block; vertical-align: middle;");
		img.setUrl(printMap.get("collectionImage"));
		
		if(printMap.get("collectionSummaryText").equalsIgnoreCase("Collection Summary")){
			img.getElement().setAttribute("style", "border-left: 5px solid #0074be; height: 60px; width: 90px;");
			secondBlock.getElement().setAttribute("style", "width:33%; float:left; text-align:center;");
			thirdBlock.getElement().setAttribute("style", "width:20%; float:left; display:none;");
			goal.getElement().setAttribute("style", "margin:0; color:#515151; font-size:12px;display:none;");
		}else{
			img.getElement().setAttribute("style", "border-left: 5px solid #fdb128; height: 60px; width: 90px;");
			secondBlock.getElement().setAttribute("style", "width:16.6667%; float:left; text-align:center;");
			thirdBlock.getElement().setAttribute("style", "width:20%; float:left; display:block;");
			goal.getElement().setAttribute("style", "margin:0; color:#515151; font-size:12px;display:block;");
		}
		
		detailsBlock.getElement().setAttribute("style", "display: inline-block; vertical-align: middle; width:75%;");
		collectionTitle.setText(printMap.get("collectionTitle"));
		collectionTitle.getElement().setAttribute("style","color: #0074be; font-size: 16px; margin: 0 0 6px; font-weight:normal;");
		collectionResourcesCount.setText(printMap.get("collectionResourcesCount"));
		collectionResourcesCount.getElement().setAttribute("style", "margin:0; color:#515151; font-size:12px;");
		
		secondInnerBlock.getElement().setAttribute("style", "min-height:100px; padding:14px 0; border-left:1px solid #ddd;");
		scoreTitle.getElement().setAttribute("style", "margin:0 0 6px; color:#515151; font-weight:normal;");
		scoreTitle.setText(printMap.get("scoreTitle"));
		score.getElement().setAttribute("style", "color: #4897ce; font-weight: bold; margin:0 0 6px; font-size:30px;");
		score.setText(printMap.get("score"));
		goal.setText("Goal :"+printMap.get("goal"));
		
		thirdInnerBlock.getElement().setAttribute("style", "padding:15px 0; min-height:100px; border-left:1px solid #ddd;");
		thirdInnerSubBlock.getElement().setAttribute("style", "display: inline-block; vertical-align: middle; width:100%;");
		thirdInnerSub2Block.getElement().setAttribute("style", "background-color: #ddd; border-radius: 50%; float:none; margin:0 auto; height: 80px; position: relative; width: 80px; background-image:linear-gradient(90deg, #ddd 50%, rgba(0, 0, 0, 0) 50%, rgba(0, 0, 0, 0)), linear-gradient(198deg, #85b9df 50%, #ddd 50%, #ddd)");
		progressRadial.getElement().setAttribute("style", "background-color: #fafafa; border-radius: 50%; font-size: 16px; height: 68px; line-height: 25px; margin-left: 6px; margin-top: 6px; position: absolute; text-align: center; width: 68px;");
		correctStatus.getElement().setAttribute("style", "display: block; font-weight: bold; line-height: 15px; margin: 18px 0; color:#515151;");
		correctStatus.setText(printMap.get("correctStatus"));
		
		fourthBlock.getElement().setAttribute("style", "width:20.6667%; float:left;");
		fourthInnerBlock.getElement().setAttribute("style", "padding: 18px 15px 18px 15px; min-height:100px; width:100%;border-left: 1px solid #ddd;border-left: 1px solid #ddd;");
		customSelect.getElement().setAttribute("style", "position: relative;display: inline-block;width: 100%;margin-bottom: 5px;");
		sessionsDropDown.getElement().setAttribute("style", "background: #fff; border: 1px solid #ddd; border-radius: 4px; display: inline-block; font-family: inherit; font-feature-settings: inherit; font-kerning: inherit; font-language-override: inherit; font-size: inherit; font-size-adjust: inherit; font-stretch: inherit; font-style: inherit;font-synthesis: inherit; font-variant: inherit; font-weight: normal; height: 40px; line-height: 1.2; margin: 0; outline: 0 none; padding: 8px 8px 8px 4px; width: 100%;");
		sessionsDropDown.addItem(printMap.get("sessionsDropDown"));
		lastModifiedTime.getElement().setAttribute("style", "font-size: 11.5px; line-height: 14px; color:#515151;");
		lastModifiedTime.setText(printMap.get("lastModifiedTime"));
		
		clearfix.getElement().setAttribute("style", "clear:both");
	}
}