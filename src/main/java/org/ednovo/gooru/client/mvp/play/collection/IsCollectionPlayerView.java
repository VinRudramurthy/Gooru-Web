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
package org.ednovo.gooru.client.mvp.play.collection;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ContentReportDo;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public interface IsCollectionPlayerView extends PopupView, IsViewWithHandlers<CollectionPlayerUiHandlers>{
	public FlowPanel getPlayerBodyContainer();
	public void setResourceTitle(String resourceTitle);
	public FlowPanel getResourceAnimationContainer();
	public void enablePlayerButton(boolean isAddButtonEnable,boolean isInfoButtonEnable,boolean isShareButtonEnable, boolean isNarrationButtonEnable,boolean isNavigationButtonEnable,boolean isFlagButtonActive,boolean isFullScreenButtonActive);
	public void makeButtonActive(boolean makeAddButtionActive,boolean makeInfoButtionActive, boolean  makeShareButtonActive, boolean makeNarrationButtonActive, boolean makeNavigationButtonActive,boolean makeFlagButtonActive,boolean makeFullScreenButtonActive);
	public void clearActiveButton(boolean deselectAddButton,boolean deselectInfoButton,boolean deselectShareButtion,boolean deselectNarrationButton,boolean deselectNavigationButton,boolean deselectFlagButton,boolean deselectFullScreenButton);
	public void updateThumbsRatingView(int userThumbRating);
	public void resetThumbsButtons();
	public void defaultReportView();
	public void flaggedReportView(ContentReportDo contentReportDo,String FlagId);
	public void hideFlagButton(boolean hideButton);
	public void removeStudentViewButton();
	public void setTabPlaceRequest(String string, boolean b, boolean c);
	public void showAddToolTip();
	public void closePreviewPlayer();
	public void makeFlagButtonOrange();
	public void hidePlayerButtons(boolean isHidePlayerButtons,String collectionId);
	public void scrollStudyPage();
	public void showClasspage();
	public void showClasspage(String classpageId,String page);
	public void updateAuthorDetails();
	public void setNarrationButton(Button narrationButton);
	public void setFullScreenButton(Button fullscreenButton);

	public void showFlaggedResourcePopup(PlaceRequest previousResoruceRequest, PlaceRequest nextResoruceRequest);


	public void addClonedMenuContent(FlowPanel rightPanelElement);
	public FlowPanel menuContent();
	public FlowPanel getHeaderFixedContainer();
	public FlowPanel getNavigationContainer();


	public void setCollectionType(String collectionType);
	public void restFullScreenChanges();
	public void setFullScreenMode();
	public void setNarrationInFullScreenMode(CollectionItemDo collectionItemDo, CollectionDo collectionDo);
	public void hideAuthorInHeader(boolean isHidePlayerButtons);
}
