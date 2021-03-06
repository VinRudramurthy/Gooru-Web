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
/**
 * 
 */
package org.ednovo.gooru.client.mvp.search;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.SeparatorUc;
import org.ednovo.gooru.client.uc.StandardSgItemVc;
import org.ednovo.gooru.client.uc.UcCBundle;
import org.ednovo.gooru.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.shared.util.MessageProperties;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;

/**
 * @author Search Team
 * 
 */
public class SearchUiUtil implements MessageProperties{

	public static final String STANDARD_CODE = "code";

	public static final String STANDARD_DESCRIPTION = "description";

	public static void renderStandards(FlowPanel standardsContainer, ResourceSearchResultDo searchResultDo) {
		if (searchResultDo.getStandards() != null) {
			List<Map<String, String>> standards = searchResultDo.getStandards();
			Iterator<Map<String, String>> iterator = standards.iterator();
			int count = 0;
			FlowPanel toolTipwidgets = new FlowPanel();
			while (iterator.hasNext()) {
				Map<String, String> standard = iterator.next();
				String stdCode = standard.get(STANDARD_CODE);
				String stdDec = standard.get(STANDARD_DESCRIPTION);
				if (count > 1) {
					if (count < 18){
						StandardSgItemVc standardItem = new StandardSgItemVc(stdCode, stdDec);
						toolTipwidgets.add(standardItem);
					}
				} else {
					DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(stdCode), new Label(stdDec), standards);
					toolTipUc.setStyleName(UcCBundle.INSTANCE.css().searchStandard());
					standardsContainer.add(toolTipUc);
				}
				count++;
			}
			if (standards.size()>18){
				final Label left = new Label(GL_SPL_PLUS+(standards.size() - 18));
				toolTipwidgets.add(left);
			}
			if (searchResultDo.getStandards().size() > 2) {
				Integer moreStandardsCount = searchResultDo.getStandards().size() - 2;
				DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(GL_SPL_PLUS + moreStandardsCount), toolTipwidgets, standards);
				toolTipUc.setStyleName(SearchResultWrapperCBundle.INSTANCE.css().blueLink());
				standardsContainer.add(toolTipUc);
				toolTipUc.getTooltipPopUpUcCount(moreStandardsCount);
				
			}
		}
	}
	/*public static void renderPPPStandards(FlowPanel standardsContainer,CollectionItemDo collectionItemDo) {
		if (collectionItemDo.getStandards() != null) {
			List<Map<String, String>> standards = collectionItemDo.getStandards();
			Iterator<Map<String, String>> iterator = standards.iterator();
			int count = 0;
			FlowPanel toolTipwidgets = new FlowPanel();
			while (iterator.hasNext()) {
				Map<String, String> standard = iterator.next();
				String stdCode = standard.get(STANDARD_CODE);
				String stdDec = standard.get(STANDARD_DESCRIPTION);
				if (count > 2) {
					StandardSgItemVc standardItem = new StandardSgItemVc(stdCode, stdDec);
					toolTipwidgets.add(standardItem);
				} else {
					DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(stdCode), new Label(stdDec));
					toolTipUc.setStyleName(UcCBundle.INSTANCE.css().searchStandard());
					standardsContainer.add(toolTipUc);
				}
				count++;
			}
			if (collectionItemDo.getStandards().size() > 3) {
				Integer moreStandardsCount = collectionItemDo.getStandards().size() - 3;
				DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label("+" + moreStandardsCount), toolTipwidgets);
				toolTipUc.setStyleName(SearchResultWrapperCBundle.INSTANCE.css().blueLink());
				standardsContainer.add(toolTipUc);
			}
		}
	}*/
	
	

	public static void renderMetaData(FlowPanel flowPanel, String data) {
		renderMetaData(flowPanel, data, null, -1);
	}

	/*public static void renderMetaData(FlowPanel flowPanel, String data, int wrapLength) {
		renderMetaData(flowPanel, data, null, wrapLength);
	}*/

	public static void renderMetaData(FlowPanel flowPanel, String data, String suffix) {
		renderMetaData(flowPanel, data, suffix, -1);
	}

	/*public static void renderMetaData(FlowPanel flowPanel, List<String> datas) {
		renderMetaData(flowPanel, datas, -1);
	}*/

	public static void renderMetaData(FlowPanel flowPanel, List<String> datas, int wrapLength) {
		if (datas == null) {
			return;
		}
		renderMetaData(flowPanel, datas.size() > 0 ? datas.get(0) : null, null, wrapLength);
		FlowPanel toolTipwidgets = new FlowPanel();
		FlowPanel toolTipwidget1 = new FlowPanel();
		for (int count = 0; count < datas.size(); count++) {
			Label label = new Label(datas.get(count));
			label.setStyleName(SearchResultWrapperCBundle.INSTANCE.css().moreMetaLbl());
			if(count==0){
				toolTipwidget1.add(label);
			}else{
				toolTipwidgets.add(label);
			}
			
		}
		if (datas != null && datas.size() > 1) {
			Integer moreCount = datas.size() - 1;
			DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(GL_SPL_PLUS + moreCount), toolTipwidgets);
			toolTipUc.setStyleName(SearchResultWrapperCBundle.INSTANCE.css().blueLinkPad());
			flowPanel.add(toolTipUc);
		}
	}

	public static void renderMetaData(FlowPanel flowPanel, String data, String suffix, int wrapLength) {
		if (suffix != null || StringUtil.hasValidString(data)) {
			if (wrapLength > 0) {
				data = StringUtil.truncateText(data, wrapLength);
			}
			if (suffix != null) {
				data += suffix;
			}
			Label label = new Label(data);
			label.setStyleName(CssTokens.FLOAT_LEFT);
			renderMetaData(flowPanel, label);
		}
	}
	
	public static void renderSourceMetadata(FlowPanel flowPanel,String data,String suffix, int wrapLength){
		if (suffix != null || StringUtil.hasValidString(data)) {
			if (wrapLength > 0) {
				data = StringUtil.truncateText(data, wrapLength);
			}
			if (suffix != null) {
				data += suffix;
			}
			HTMLPanel label = new HTMLPanel(data);
			label.setStyleName(CssTokens.FLOAT_LEFT);
			renderMetaData(flowPanel, label);
		}
	}

	public static void renderMetaData(FlowPanel flowPanel, IsWidget widget) {
		if (flowPanel.iterator().hasNext()) {
			flowPanel.add(new SeparatorUc());
		}
		flowPanel.add(widget);
	}



	

}
