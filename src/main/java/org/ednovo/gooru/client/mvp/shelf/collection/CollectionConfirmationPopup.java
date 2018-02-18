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
package org.ednovo.gooru.client.mvp.shelf.collection;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.BlueButtonUc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public  class CollectionConfirmationPopup extends PopupPanel {

	@UiField Label shareMsgTitle, shareMsgTxt, shareMsgLbl, closeButton,classPageNames;
	

	@UiField BlueButtonUc okButton, goBackBtn;
	@UiField HTMLPanel buttonContainer;
	
	
	
	private static CollectionConfirmationPopupUiBinder uiBinder = GWT
			.create(CollectionConfirmationPopupUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface CollectionConfirmationPopupUiBinder extends
			UiBinder<Widget, CollectionConfirmationPopup> {
	}

	public CollectionConfirmationPopup() {
		setWidget(uiBinder.createAndBindUi(this));
		
		this.getElement().setAttribute("style", "min-height:247px; width:436px;");
		shareMsgTitle.setText(i18n.GL0836());
		shareMsgTitle.getElement().setId("lblShareMsgTitle");
		shareMsgTitle.getElement().setAttribute("alt",i18n.GL0836());
		shareMsgTitle.getElement().setAttribute("title",i18n.GL0836());
		
		shareMsgTxt.setText(i18n.GL0837());
		shareMsgTxt.getElement().setId("lblShareMsgTxt");
		shareMsgTxt.getElement().setAttribute("alt",i18n.GL0837());
		shareMsgTxt.getElement().setAttribute("title",i18n.GL0837());
		
		shareMsgLbl.setText(i18n.GL0838());
		shareMsgLbl.getElement().setId("lblShareMsgLbl");
		shareMsgLbl.getElement().setAttribute("alt",i18n.GL0838());
		shareMsgLbl.getElement().setAttribute("title",i18n.GL0838());
		
		okButton.getElement().getStyle().setMarginLeft(7, Unit.PX);
		goBackBtn.setText(i18n.GL0834());
		goBackBtn.getElement().setAttribute("alt",i18n.GL0834());
		goBackBtn.getElement().setAttribute("title",i18n.GL0834());
		
		okButton.setText(i18n.GL0835());
		okButton.getElement().setAttribute("alt",i18n.GL0835());
		okButton.getElement().setAttribute("title",i18n.GL0835());
		
		goBackBtn.getElement().setId("btnGoBack");
		okButton.getElement().setId("btnOk");
		closeButton.getElement().setId("lblCloseButton");
		classPageNames.getElement().setId("lblClassPageNames");
		buttonContainer.getElement().setId("pnlButtonContainer");
		
		
		
		this.setGlassEnabled(true);
		this.center();
        this.show();
        
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
	}

	
	@UiHandler("closeButton")
	public void onClickCloseBtn(ClickEvent clickEvent) {
		Window.enableScrolling(true);
		this.hide();
	}
	
	@UiHandler("goBackBtn")
	public void onClickGoBackBtn(ClickEvent clickEvent) {
		Window.enableScrolling(true);
		this.hide();
	}
	/*
	 * return OkButton 
	 */
	public BlueButtonUc getOkButtonMethod(){
		return okButton;
	}
	/*
	 * return classPageNames Label
	 */
	public Label getClassPageNames(){
		return classPageNames;
	}
	

}
