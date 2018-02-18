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
package org.ednovo.gooru.client.mvp.home;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AppPopUp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 * 
 */
public abstract class WaitPopupVc extends AppPopUp{

	@UiField Button cancelAnr,okButtonUc;

	@UiField Label messageTextLabel,removingText;
	
	@UiField FlowPanel buttonContainer;

	private static WaitPopupVcUiBinder uiBinder = GWT.create(WaitPopupVcUiBinder.class);

	interface WaitPopupVcUiBinder extends UiBinder<Widget, WaitPopupVc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	
	/**
	 * Class constructor to set title and content text for pop up
	 * 
	 * @param title
	 *            is the header of the pop up
	 * @param entityInfo
	 *            is the content text of the pop up
	 */
	public WaitPopupVc(String title, String entityInfo) {
		super();
		setContent(title, uiBinder.createAndBindUi(this));
		messageTextLabel.setText(entityInfo);
		messageTextLabel.getElement().setId("lblMessageTextLabel");
		messageTextLabel.getElement().setAttribute("alt",entityInfo);
		messageTextLabel.getElement().setAttribute("title",entityInfo);
		
		cancelAnr.setText(i18n.GL0142());
		cancelAnr.getElement().setId("lnkCancel");
		cancelAnr.getElement().setAttribute("alt",i18n.GL0142());
		cancelAnr.getElement().setAttribute("title",i18n.GL0142());
		
		okButtonUc.setText(i18n.GL0190());
		okButtonUc.getElement().setId("btnOk");
		okButtonUc.getElement().setAttribute("alt",i18n.GL0190());
		okButtonUc.getElement().setAttribute("title",i18n.GL0190());
		
		removingText.setText(i18n.GL1021());
		removingText.getElement().setId("lblRemovingText");
		removingText.getElement().setAttribute("alt",i18n.GL1021());
		removingText.getElement().setAttribute("title",i18n.GL1021());
		removingText.setVisible(false);
	
		buttonContainer.getElement().setId("fpnlButtonContainer");
		
		setModal(true);
		Window.enableScrolling(false);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
		show();
		center();
	}

	/**
	 * Hide {@link WaitPopupVc} popup
	 * 
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("cancelAnr")
	public void onCancelClick(ClickEvent clickEvent) {
		hide();
		Window.enableScrolling(true);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
	}

	/**
	 * confirm to to remove the content
	 * 
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("okButtonUc")
	public void removeResource(ClickEvent clickEvent) {
		hideButtons(false);
		this.onTextConfirmed();
	}
	public void hide(){
		super.hide();
		Window.enableScrolling(true);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
	}
	
	public void hideButtons(boolean isHide){
		cancelAnr.setVisible(isHide);
		okButtonUc.setVisible(isHide);
		removingText.setVisible(!isHide);
	}

	public abstract void onTextConfirmed();
	
}
