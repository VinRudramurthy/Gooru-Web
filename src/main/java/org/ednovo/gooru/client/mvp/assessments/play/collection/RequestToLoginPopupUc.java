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
package org.ednovo.gooru.client.mvp.assessments.play.collection;


import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 *
 * @fileName : RequestToLoginPopupUc.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 07-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class RequestToLoginPopupUc extends PopupPanel{

	@UiTemplate("RequestToLoginPopupUc.ui.xml")
	interface Binder extends UiBinder<Widget, RequestToLoginPopupUc> {

	}

	@UiField Button btnContinue, btnLoginAndContinue;


	private static final Binder binder = GWT.create(Binder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);


	public RequestToLoginPopupUc(){
		super(false);
        this.setGlassStyleName("requestToLoginPopupStyle");
        this.setGlassEnabled(true);
       	this.getElement().getStyle().setZIndex(999999);

        add(binder.createAndBindUi(this));
        setTextAndIds();

		this.center();
	}


	public void setTextAndIds(){

	}


	public Button getBtnLoginAndContinue() {
		return btnLoginAndContinue;
	}


	public void setBtnLoginAndContinue(Button btnLoginAndContinue) {
		this.btnLoginAndContinue = btnLoginAndContinue;
	}

	@UiHandler("btnContinue")
	public void onClickContinue(ClickEvent event){
		hide();
	}

}

