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
package org.ednovo.gooru.client.mvp.socialshare;

import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentView;
import org.ednovo.gooru.client.mvp.home.HomeCBundle;
import org.ednovo.gooru.client.mvp.shelf.ShelfCBundle;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @fileName : SentEmailSuccessVc.java
 *
 * @description : This file used to show email share success popUp.
 *                   
 * 
 * @version : 5.4
 *
 * @date: August, 2013
 *
 * @Author: Gooru Team
 * 
 * @Reviewer: Gooru Team
 */
public class SentEmailSuccessVc extends Composite implements MessageProperties{

	private static SentEmailSuccessVcUiBinder uiBinder = GWT
			.create(SentEmailSuccessVcUiBinder.class);

	interface SentEmailSuccessVcUiBinder extends
	UiBinder<Widget, SentEmailSuccessVc> {
	}

	private AppPopUp appPopUp;

	@UiField
	Label okLbl,toEmailLbl;
	@UiField HTMLPanel emailToFriendText,emailSentText;

	/**
	 * Class constructor , create a new pop up
	 * @param toEmail 
	 */
	public SentEmailSuccessVc(String toEmail) {
		initWidget(uiBinder.createAndBindUi(this));
		appPopUp = new AppPopUp();
		appPopUp.setWidget(uiBinder.createAndBindUi(this));
		appPopUp.setStyleName(ShelfCBundle.INSTANCE.css().shelfItemSucessPopUp());
		appPopUp.getElement().getStyle().setZIndex(999999);
		appPopUp.setGlassStyleName(HomeCBundle.INSTANCE.css().loginPopupGlassStyle());
		appPopUp.setGlassEnabled(true);
		appPopUp.show();
		appPopUp.center();
		emailToFriendText.getElement().setInnerHTML(GL0222);
		emailSentText.getElement().setInnerHTML(GL0648);
		toEmailLbl.setText(toEmail);
		okLbl.setText(GL0190);
		Window.enableScrolling(false);
	}
	
	public SentEmailSuccessVc(String messageHeader, String desc) {
		initWidget(uiBinder.createAndBindUi(this));
		appPopUp = new AppPopUp();
		appPopUp.setWidget(uiBinder.createAndBindUi(this));
		appPopUp.setStyleName(ShelfCBundle.INSTANCE.css().shelfItemSucessPopUp());
		appPopUp.getElement().getStyle().setZIndex(999999);
		appPopUp.setPixelSize(480, 208);
		appPopUp.setGlassStyleName(HomeCBundle.INSTANCE.css().loginPopupGlassStyle());
		appPopUp.setGlassEnabled(true);
		appPopUp.show();
		appPopUp.center();
		emailToFriendText.getElement().setInnerHTML(messageHeader);
		emailSentText.getElement().setInnerHTML("");
		/*toEmailLbl.getElement().getStyle().setWidth(70, Unit.PCT);
		toEmailLbl.getElement().getStyle().setPaddingLeft(86, Unit.PX);*/
		toEmailLbl.setStyleName(ShelfCBundle.INSTANCE.css().aleartDescText());
		toEmailLbl.setText(desc);
		okLbl.setText(GL0190);
		Window.enableScrolling(false);
	}

	/**
	 * Hide {@link SentEmailSuccessVc} popup
	 * @param clickEvent instOLance of {@link ClickEvent}
	 */
	@UiHandler("okLbl")
	public void onBtnClick(ClickEvent clickEvent) {
		appPopUp.hide();
		String placeToken = AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		if(placeToken.equals(PlaceTokens.STUDENT) && !StudentAssignmentView.getMainContainerStatus())
		{
			Window.enableScrolling(true);
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
		}
		else if(!(placeToken.equals(PlaceTokens.COLLECTION_PLAY) ||placeToken.equals(PlaceTokens.PREVIEW_PLAY)||placeToken.equals(PlaceTokens.RESOURCE_PLAY))) {
			Window.enableScrolling(true);
		}
	}
}
