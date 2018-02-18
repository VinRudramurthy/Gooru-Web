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
package org.ednovo.gooru.client.mvp.classpages.assignments;

/*
 *
 * @fileName : AssignmentPopupView.java
 *
 * @description : This class is responsible to show and add assignments
 *
 *
 * @version : 5.3
 *
 * @date: May 4, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class SuccessMessagePopupView extends Composite{
	private PopupPanel appPopUp;
	@UiField Button okayButton;
	@UiField Label successPopupHeader;

	@UiField HTML successPopupBodyText;

	public interface SuccessMessagePopupViewUiBinder extends UiBinder<Widget, SuccessMessagePopupView> {

	}
	public static SuccessMessagePopupViewUiBinder uiBinder = GWT.create(SuccessMessagePopupViewUiBinder.class);

	public MessageProperties i18n = GWT.create(MessageProperties.class);

	public SuccessMessagePopupView(String collectonTitle) {
		Window.enableScrolling(false);
		appPopUp=new PopupPanel();
		appPopUp.setWidget(uiBinder.createAndBindUi(this));
		AddAssignmentContainerCBundle.INSTANCE.css().ensureInjected();
		setStaticTexts(collectonTitle);
		appPopUp.setGlassEnabled(true);
		appPopUp.setHeight("320px");
		appPopUp.show();
		appPopUp.center();
	}

	/**
	 *
	 * @function setStaticTexts
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param collectonTitle
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setStaticTexts(String collectonTitle){
		successPopupHeader.setText(i18n.GL1384());
		successPopupHeader.getElement().setId("lblSuccessPopupHeader");
		successPopupHeader.getElement().setAttribute("alt",i18n.GL1384());
		successPopupHeader.getElement().setAttribute("title",i18n.GL1384());

		successPopupBodyText.setHTML(StringUtil.generateMessage(i18n.GL1385(), collectonTitle));
		successPopupBodyText.getElement().setId("htmlSuccessPoupBodyText");
		successPopupBodyText.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL1385(), collectonTitle));
		successPopupBodyText.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL1385(), collectonTitle));

		okayButton.setText(i18n.GL1386());
		okayButton.getElement().setId("btnOk");
		okayButton.getElement().setAttribute("alt",i18n.GL1386());
		okayButton.getElement().setAttribute("title",i18n.GL1386());
	}
	@UiHandler("okayButton")
	public void closePopupEvent(ClickEvent event){
		hide();
	}
	/**
	 *
	 * @function hide
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void hide(){
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		appPopUp.hide();
	}


}
