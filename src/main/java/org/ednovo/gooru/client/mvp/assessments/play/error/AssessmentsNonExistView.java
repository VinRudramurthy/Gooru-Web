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
package org.ednovo.gooru.client.mvp.assessments.play.error;


import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
/**
* @fileName : CollectionNonExistView.java
*
* @description : If collection doesn't exist this view will be called and displayed.
*
* @version : 1.0
*
* @date:  December, 2013.
*
* @Author: Gooru Team.
*
* @Reviewer: Gooru Team.
*/
public class AssessmentsNonExistView extends Composite{

	public interface AssessmentsNonExistViewUiBinder extends UiBinder<Widget,AssessmentsNonExistView>{

	}

	public static AssessmentsNonExistViewUiBinder collectionNonExistViewUiBinder=GWT.create(AssessmentsNonExistViewUiBinder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField public FlowPanel feautredCollectionContainer;
	@UiField Button btnLibrary;
	@UiField HTMLPanel noLongerText,madePrivateOrDeletedText,meanTimetext;
	@UiField Image recycleImage;

	/**
	 * Class constructor.
	 */
	public AssessmentsNonExistView(){
		initWidget(collectionNonExistViewUiBinder.createAndBindUi(this));
		btnLibrary.setText(i18n.GL1831());
		btnLibrary.getElement().setId("btnLibrary");
		btnLibrary.getElement().setAttribute("alt",i18n.GL1831());
		btnLibrary.getElement().setAttribute("title",i18n.GL1831());

		noLongerText.getElement().setInnerHTML(i18n.GL3460_1());
		noLongerText.getElement().setId("pnlNoLongerText");
		noLongerText.getElement().setAttribute("alt",i18n.GL3460_1());
		noLongerText.getElement().setAttribute("title",i18n.GL3460_1());

		recycleImage.setUrl("images/collection_error.png");
		madePrivateOrDeletedText.getElement().setInnerHTML(i18n.GL3460_2());
		madePrivateOrDeletedText.getElement().setId("pnlMadePrivateOrDeletedText");
		madePrivateOrDeletedText.getElement().setAttribute("alt",i18n.GL3460_2());
		madePrivateOrDeletedText.getElement().setAttribute("title",i18n.GL3460_2());

		meanTimetext.getElement().setInnerHTML(i18n.GL3460_3());
		meanTimetext.getElement().setId("pnlMeanTimetext");
		meanTimetext.getElement().setAttribute("alt",i18n.GL3460_3());
		meanTimetext.getElement().setAttribute("title",i18n.GL3460_3());

		btnLibrary.setText(i18n.GL1831());
		btnLibrary.getElement().setAttribute("alt",i18n.GL1831());
		btnLibrary.getElement().setAttribute("title",i18n.GL1831());

		feautredCollectionContainer.getElement().setId("fpnlFeautredCollectionContainer");

		recycleImage.getElement().setId("imgRecycleImage");
	}

	/**
	 * On click of this button navigates user to library page.
	 * @param clickEvent {@link ClickEvent}
	 */
	@UiHandler("btnLibrary")
	public void onClickOnLibrary(ClickEvent clickEvent){
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.DISCOVER);

	}
}
