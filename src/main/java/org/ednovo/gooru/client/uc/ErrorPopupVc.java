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

import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class ErrorPopupVc extends Composite{

	private static ErrorPopupVcUiBinder uiBinder = GWT.create(ErrorPopupVcUiBinder.class);

	interface ErrorPopupVcUiBinder extends UiBinder<Widget, ErrorPopupVc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	PopupPanel errorPopUpNew;

	@UiField FlowPanel captionTitle;

	@UiField
	Label okButton,errorPopupHeaderText,dragText,questionLabel,faqsText,cancelButton;

	/**
	 * Class constructor
	 */
	public ErrorPopupVc() {
		initWidget(uiBinder.createAndBindUi(this));
		errorPopUpNew.getElement().setId("popupnlErrorPopUpNew");
		captionTitle.getElement().setId("fpnlCaptionTitle");
		errorPopupHeaderText.setText(i18n.GL0061());
		errorPopupHeaderText.getElement().setId("lblErrorPopupHeaderText");
		errorPopupHeaderText.getElement().setAttribute("alt", i18n.GL0061());
		errorPopupHeaderText.getElement().setAttribute("title", i18n.GL0061());
		dragText.setText(i18n.GL1028());
		dragText.getElement().setId("lblDragText");
		dragText.getElement().setAttribute("alt", i18n.GL1028());
		dragText.getElement().setAttribute("title", i18n.GL1028());
		questionLabel.setText(i18n.GL1029());
		questionLabel.getElement().setId("lblQuestionLabel");
		questionLabel.getElement().setAttribute("alt", i18n.GL1029());
		questionLabel.getElement().setAttribute("title", i18n.GL1029());
		faqsText.setText(i18n.GL1030());
		faqsText.getElement().setId("lblFaqsText");
		faqsText.getElement().setAttribute("alt", i18n.GL1030());
		faqsText.getElement().setAttribute("title", i18n.GL1030());
		okButton.setText(i18n.GL0190());
		okButton.getElement().setId("lblOkButton");
		okButton.getElement().setAttribute("alt", i18n.GL0190());
		okButton.getElement().setAttribute("title", i18n.GL0190());
		cancelButton.setText(i18n.GL0142());
		cancelButton.getElement().setId("lblCancelButton");
		cancelButton.getElement().setAttribute("alt", i18n.GL0142());
		cancelButton.getElement().setAttribute("title", i18n.GL0142());
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				errorPopUpNew.setVisible(false);
			}
		});
	}



}
