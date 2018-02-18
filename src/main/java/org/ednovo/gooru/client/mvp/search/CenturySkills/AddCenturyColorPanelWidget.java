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
package org.ednovo.gooru.client.mvp.search.CenturySkills;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;
/**
 * This widget is used for generating color widget for 21 century 
 * @author Gooru-Team
 */
public class AddCenturyColorPanelWidget extends Composite {

	private static AddCenturyColorPanelWidgetUiBinder uiBinder = GWT
			.create(AddCenturyColorPanelWidgetUiBinder.class);

	@UiField InlineHTML spnGreenCircle,spnBlueCircle,spnYellowCircle,spnPurpleCircle;
	
	final String GREENCOLOR="#a6caa2";
	final String BLUEOLOR="#8791dd";
	final String YELLOWCOLOR="#fbc46d";
	final String PURPLECOLOR="#eb8888";
	
	interface AddCenturyColorPanelWidgetUiBinder extends
			UiBinder<Widget, AddCenturyColorPanelWidget> {
	}
	public AddCenturyColorPanelWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public AddCenturyColorPanelWidget(String colorCode) {
		initWidget(uiBinder.createAndBindUi(this));
		if(colorCode.length()>0){
			if(!colorCode.contains(GREENCOLOR)){
				spnGreenCircle.setVisible(false);
			}
			if(!colorCode.contains(BLUEOLOR)){
				spnBlueCircle.setVisible(false);
			}
			if(!colorCode.contains(YELLOWCOLOR)){
				spnYellowCircle.setVisible(false);
			}
			if(!colorCode.contains(PURPLECOLOR)){
				spnPurpleCircle.setVisible(false);
			}
		}else{
			spnGreenCircle.setVisible(false);
			spnBlueCircle.setVisible(false);
			spnYellowCircle.setVisible(false);
			spnPurpleCircle.setVisible(false);
		}
	}
}
