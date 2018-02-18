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
package org.ednovo.gooru.client.mvp.play.collection.preview.metadata;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.classpages.assignments.AddAssignmentContainerCBundle;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @fileName : NavigationConfirmPopup.java
 * 
 * 
 * @Author :Gooru Team
 * 
 * @Reviewer:
 */
public abstract class NavigationConfirmPopup extends PopupPanel{
	
	@UiField Button continueButton,backToResponseButton;
	@UiField Label popupHeader,popupText;

	private static NavigationConfirmPopupUiBinder uiBinder = GWT.create(NavigationConfirmPopupUiBinder.class);

	interface NavigationConfirmPopupUiBinder extends UiBinder<Widget, NavigationConfirmPopup> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	/**
	 * default constructor of CopyConfirmPopupVc
	 */
	public NavigationConfirmPopup() {
		super();
		this.setWidget(uiBinder.createAndBindUi(this));
		setStaticTexts();
		AddAssignmentContainerCBundle.INSTANCE.css().ensureInjected();
		this.getElement().getStyle().setZIndex(999999);
		//this.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().copyResourcePopupContainer());
		this.setGlassEnabled(true);
		this.setAutoHideOnHistoryEventsEnabled(true);
		this.getGlassElement().getStyle().setZIndex(999999);
		this.show();
		this.center();
		this.setModal(true);
	}
	public void setStaticTexts(){
		popupHeader.setText(i18n.GL1660());
		popupHeader.getElement().setId("lblPopupHeader");
		popupHeader.getElement().setAttribute("alt",i18n.GL1660());
		popupHeader.getElement().setAttribute("title",i18n.GL1660());
		
		popupText.setText(i18n.GL1661());
		popupText.getElement().setId("lblPopupText");
		popupText.getElement().setAttribute("alt",i18n.GL1661());
		popupText.getElement().setAttribute("title",i18n.GL1661());
		
		continueButton.setText(i18n.GL1663());
		continueButton.getElement().setId("backtoresponse");
		continueButton.getElement().getStyle().setMarginRight(5, Unit.PX);
		continueButton.getElement().getStyle().setMarginBottom(10, Unit.PX);
		continueButton.getElement().setAttribute("alt",i18n.GL1663());
		continueButton.getElement().setAttribute("title",i18n.GL1663());
		
		backToResponseButton.setText(i18n.GL1662());
		backToResponseButton.getElement().setAttribute("alt",i18n.GL1662());
		backToResponseButton.getElement().setAttribute("title",i18n.GL1662());
		backToResponseButton.getElement().setId("continue");
	}
	

	/**
	 * for close copy collection popup
	 * 
	 * @param clickEvent
	 */
	@UiHandler("backToResponseButton")
	public void onCancelClick(ClickEvent clickEvent) {
		hide();
	}

	@UiHandler("continueButton")
	public void onClickCopyBtn(ClickEvent clickEvent){
		navigateToNextResource();
	}
	
	public void hide(){
		super.hide();
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
	}
	
	public abstract void navigateToNextResource();
}
