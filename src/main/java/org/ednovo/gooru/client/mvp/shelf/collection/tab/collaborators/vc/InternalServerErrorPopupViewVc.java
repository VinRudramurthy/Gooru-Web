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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author BLR Team
 *
 */
public abstract class InternalServerErrorPopupViewVc extends PopupPanel {

	@UiField Label lblOops, lblErrorIEMessage;

	@UiField Button btnOk;


	Timer timer = null;

	private static Integer DELAY_TIME_SECONDS = 30;

	private MessageProperties i18n = GWT.create(MessageProperties.class);


	@UiTemplate("InternalServerErrorPopupViewVc.ui.xml")
	interface Binder extends UiBinder<Widget, InternalServerErrorPopupViewVc> {

	}


	private static final Binder binder = GWT.create(Binder.class);

	/**
	 *
	 */
	public InternalServerErrorPopupViewVc() {
		super(false);
		add(binder.createAndBindUi(this));
		this.setGlassEnabled(true);
		Window.enableScrolling(false);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
        this.getElement().getStyle().setBackgroundColor("transparent");
        setElementId();

		this.center();


		timer = new Timer() {
			public void run() {
				//Call API...
			}
		};
		timer.schedule(DELAY_TIME_SECONDS);



	}

	private void setElementId() {
		lblOops.setText(i18n.GL0061());
		lblErrorIEMessage.setText( i18n.GL2038());
		btnOk.setText(i18n.GL0190());

		StringUtil.setAttributes(lblOops.getElement(), i18n.GL0061(), i18n.GL0061(), i18n.GL0061());
		StringUtil.setAttributes(lblErrorIEMessage.getElement(), i18n.GL2038(), i18n.GL2038(), i18n.GL2038());
		StringUtil.setAttributes(btnOk.getElement(), i18n.GL0190(), i18n.GL0190(), i18n.GL0190());
	}

	@UiHandler("btnOk")
	public void OnClickOk(ClickEvent event){
		timer.cancel();
		hide();
	}
}
