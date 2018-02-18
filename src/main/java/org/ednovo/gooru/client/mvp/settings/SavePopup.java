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
package org.ednovo.gooru.client.mvp.settings;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class SavePopup extends PopupPanel{
	
	
	@UiField Label lbOk;
	@UiField HTMLPanel confirmEmail,checkMailLbl;

	private static SavePopupUiBinder uiBinder = GWT.create(SavePopupUiBinder.class);
	
	interface SavePopupUiBinder extends UiBinder<Widget, SavePopup> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	public SavePopup(){
		super();
		setWidget(uiBinder.createAndBindUi(this));
		confirmEmail.getElement().setInnerText(i18n.GL1483());
		confirmEmail.getElement().setId("pnlConfirmEmail");
		confirmEmail.getElement().setAttribute("alt", i18n.GL1483());
		confirmEmail.getElement().setAttribute("title", i18n.GL1483());
		checkMailLbl.getElement().setInnerText(i18n.GL1484());
		checkMailLbl.getElement().setId("pnlCheckMailLbl");
		checkMailLbl.getElement().setAttribute("alt", i18n.GL1484());
		checkMailLbl.getElement().setAttribute("title", i18n.GL1484());
		lbOk.setText(i18n.GL1386());
		lbOk.getElement().setId("lblLbOk");
		lbOk.getElement().setAttribute("alt", i18n.GL1386());
		lbOk.getElement().setAttribute("title", i18n.GL1386());
		setGlassEnabled(true);
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		this.show();
		this.center();
		
	}
	
	@UiHandler("lbOk")
	public void OnClickOkButton(ClickEvent event){
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		this.hide();
		}
}
