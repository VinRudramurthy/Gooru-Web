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
package org.ednovo.gooru.client.mvp.gshelf.collectiondetails;

import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author Search Team
 *
 */
public class StandardsCodeDecView extends Composite{

	private static StandardsCodeDescUiBinder uiBinder = GWT.create(StandardsCodeDescUiBinder.class);

	@UiTemplate("StandardsCodeDescView.ui.xml")
	interface StandardsCodeDescUiBinder extends UiBinder<Widget, StandardsCodeDecView> {
	}	


	@UiField Label standardCode;
	@UiField PPanel standardDesc,standardDescMain;
	@UiField HTMLEventPanel widgetContainer,widgetContainerDesc;
     
	
	
	/**
	 * Class constructor 
	 * @param eventBus {@link EventBus}
	 */
	@Inject
	public StandardsCodeDecView(String codeVal, String labelVal, Boolean boolFlag) {
		initWidget(uiBinder.createAndBindUi(this));
		standardCode.setText(codeVal);
		standardDesc.setText(labelVal);
		standardDescMain.setText(labelVal);
		if(boolFlag)
		{
			widgetContainerDesc.setVisible(true);
			widgetContainer.setVisible(false);
		}
		else
		{
			widgetContainerDesc.setVisible(false);
			widgetContainer.setVisible(true);
		}
	}

	public HTMLEventPanel getWidgetContainer() {
		return widgetContainer;
	}



	public void setWidgetContainer(HTMLEventPanel widgetContainer) {
		this.widgetContainer = widgetContainer;
	}
	
	
	
	
	
	
}
