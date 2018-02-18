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
package org.ednovo.gooru.client.mvp.analytics.util;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.analytics.OetextDataDO;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ViewResponsesPopup extends PopupPanel {

	private static ViewResponsesPopupUiBinder uiBinder = GWT
			.create(ViewResponsesPopupUiBinder.class);

	interface ViewResponsesPopupUiBinder extends
			UiBinder<Widget, ViewResponsesPopup> {
	}

	@UiField HTMLPanel viewResponsepnl;
	@UiField Label totalResponselbl;
	/**
	 * Constructor
	 * @param questionCount
	 * @param questionText
	 * @param questionAnswers
	 * @param questionType
	 */
	public ViewResponsesPopup(String questionCount,String questionText,String questionAnswers,String questionType) {
		setWidget(uiBinder.createAndBindUi(this));
		setCollectionProgressData(questionCount, questionText, questionAnswers,questionType);
	}

	/**
	 * This method is used to display the view responses
	 * @param result
	 * @param resourceGooruId
	 * @param collectionId
	 * @param classpageId
	 * @param pathwayId
	 * @param questionType
	 * @param isSummary
	 * @param session
	 */
	public ViewResponsesPopup(ArrayList<OetextDataDO> result,String resourceGooruId,String collectionId, String classpageId,String pathwayId,String questionType,boolean isSummary,String session,ClasspageItemDo classpageItemDo) {
	 	setWidget(uiBinder.createAndBindUi(this));
		setPopUpData(result,resourceGooruId,collectionId,classpageId,pathwayId,questionType,isSummary,session,classpageItemDo);
	}
	/**
	 * This method is used to set collection progress data.
	 * @param questionCount
	 * @param questionText
	 * @param questionAnswers
	 * @param questionType
	 */
	void setCollectionProgressData(String questionCount,String questionText,String questionAnswers, String questionType){
		viewResponsepnl.clear();
		totalResponselbl.setVisible(false);
		ViewResponseUserWidget responseUserWidget=new ViewResponseUserWidget(questionCount, questionText, questionAnswers, questionType);
		viewResponsepnl.getElement().getStyle().clearHeight();
		viewResponsepnl.add(responseUserWidget);
	}
	/**
	 * This method will set the popup data.
	 * @param result
	 * @param resourceGooruId
	 * @param collectionId
	 * @param classpageId
	 * @param pathwayId
	 * @param questionType
	 * @param isSummary
	 */
	void setPopUpData(ArrayList<OetextDataDO> result,String resourceGooruId,String collectionId, String classpageId,String pathwayId,String questionType,boolean isSummary,String session,ClasspageItemDo classpageItemDo){
		viewResponsepnl.clear();
		totalResponselbl.setVisible(true);
		totalResponselbl.setText(result.size()+" Responses");

		viewResponsepnl.getElement().getStyle().setHeight(250, Unit.PX);
		for (OetextDataDO oetextDataDO : result) {
			ViewResponseUserWidget responseUserWidget=new ViewResponseUserWidget(oetextDataDO,resourceGooruId,collectionId,classpageId,pathwayId,questionType,isSummary,session,classpageItemDo);
			viewResponsepnl.add(responseUserWidget);
		}
	}
	@Override
	protected void onUnload() {
		super.onUnload();
		if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY)){
			  Window.enableScrolling(false);
			}else{
			   Window.enableScrolling(true);
		}
	}
}
