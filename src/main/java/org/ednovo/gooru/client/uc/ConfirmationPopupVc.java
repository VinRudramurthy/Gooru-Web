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
package org.ednovo.gooru.client.uc;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public abstract class ConfirmationPopupVc extends Composite {

	private static AppPopUp appPopUp;
	private boolean isEditQuestion=false;
	@UiField
	Button cancelButton;

	@UiField
	BlueButtonUc okButton;

	@UiField 
	Label contentText,loadingTextLbl;
	
	@UiField
	FlowPanel buttonContainer;
	
	private static ConfirmationPopupVcUiBinder uiBinder = GWT.create(ConfirmationPopupVcUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface ConfirmationPopupVcUiBinder extends UiBinder<Widget, ConfirmationPopupVc> {
	}

	/**
	 * Class constructor
	 * @param messageHeader header for popup
	 * @param messageContent for popup
	 */
	public ConfirmationPopupVc(String messageHeader, String  messageContent) {
		initWidget(uiBinder.createAndBindUi(this));
		appPopUp = new AppPopUp();
//		appPopUp.setStyleName("removeResourcePopup");
		appPopUp.setContent(messageHeader, uiBinder.createAndBindUi(this));
		contentText.setText(messageContent);
		contentText.getElement().setId("lblContentText");
		contentText.getElement().setAttribute("alt", messageContent);
		contentText.getElement().setAttribute("title", messageContent);
		appPopUp.show();
		appPopUp.center();
		cancelButton.setText(i18n.GL0142());
		cancelButton.getElement().setAttribute("alt", i18n.GL0142());
		cancelButton.getElement().setAttribute("title", i18n.GL0142());
		cancelButton.setVisible(true);
		okButton.setText(i18n.GL0190());
		okButton.getElement().setAttribute("alt", i18n.GL0190());
		okButton.getElement().setAttribute("title", i18n.GL0190());
		okButton.getElement().setId("btnOk");
		loadingTextLbl.setText(i18n.GL1021());
		loadingTextLbl.getElement().setId("lblLoadingTextLbl");
		loadingTextLbl.getElement().setAttribute("alt", i18n.GL1021());
		loadingTextLbl.getElement().setAttribute("title", i18n.GL1021());
		cancelButton.getElement().setId("lnkCancel");
		loadingTextLbl.setVisible(false);
		buttonContainer.getElement().setId("fpnlButtonContainer");
        buttonContainer.setVisible(true);
		cancelButton.getElement().getStyle().setMarginRight(10, Unit.PX);
		appPopUp.getCloseBtn().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				appPopUp.hide();
				
			}
		});
		setAutoHideEnabled(true);
	}

	@UiHandler("cancelButton")
	public void onCancelClick(ClickEvent clickEvent) {
		hide();
        if(isEditQuestion){
        	Window.enableScrolling(false);
        }else{
        	Window.enableScrolling(true);
	        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
        }
	}
	
	public void hide() {
		appPopUp.hide();
		if(isEditQuestion){
        	Window.enableScrolling(false);
        }else{
        	Window.enableScrolling(true);
            AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
        }
		

	}
	
	public void setGlassZindex(int index){
		if(appPopUp!=null){
			appPopUp.setGlassZindex(index);
		}
	}
	public void setPopupZindex(int index){
		if(appPopUp!=null){
			appPopUp.setPopupZindex(index);
		}
	}

	@UiHandler("okButton")
	public void onClickDelete(ClickEvent clickEvent){
		loadingTextLbl.setVisible(true);
        buttonContainer.setVisible(false);
		onDelete(clickEvent);
	}
	
	public abstract void onDelete(ClickEvent clickEvent);
	
	public void setScrollDisable(){
		isEditQuestion=true;
	}

	public void setDeleteData(String deleteMsg, String deleteBtnTxt) {
		loadingTextLbl.setText(deleteMsg);
		loadingTextLbl.getElement().setAttribute("alt", deleteMsg);
		loadingTextLbl.getElement().setAttribute("title", deleteMsg);
		okButton.setText(deleteBtnTxt);
		okButton.getElement().setAttribute("alt", deleteBtnTxt);
		okButton.getElement().setAttribute("title", deleteBtnTxt);
	}

	/**
	 * @return the cancelButton
	 */
	public Button getCancelButton() {
		return cancelButton;
	}
	
	public void setAndHideButtonInPlayer(String okText, String cancelTxt){
		okButton.setText(okText);
		cancelButton.setText(cancelTxt);
		buttonContainer.getElement().setAttribute("style", "margin-top:0px;");
		StringUtil.setAttributes(okButton.getElement(), okText, okText, okText);
		StringUtil.setAttributes(cancelButton.getElement(), cancelTxt, cancelTxt, cancelTxt);
	}

	/**
	 * @return the okButton
	 */
	public BlueButtonUc getOkButton() {
		return okButton;
	}
	
	public static void setAutoHideEnabled(boolean isAutoHide) {
		if(isAutoHide){
			appPopUp.setAutoHideEnabled(isAutoHide);
			}
	}
	
}
