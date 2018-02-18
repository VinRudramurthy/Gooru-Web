package org.ednovo.gooru.client.mvp.play.collection.preview;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;

import com.google.gwt.user.client.ui.FlowPanel;
import com.gwtplatform.mvp.client.PopupView;

public interface IsPreviewPlayerView extends PopupView, IsViewWithHandlers<PreviewPlayerUiHandlers>{
	public FlowPanel getPlayerBodyContainer();
	public void setResourceTitle(String resourceTitle);
	public FlowPanel getNavigationContainer();
	public void enablePlayerButton(boolean isAddButtonEnable,boolean isInfoButtonEnable,boolean isShareButtonEnable, boolean isNarrationButtonEnable,boolean isNavigationButtonEnable,boolean isFlagButtonActive);
	public void makeButtonActive(boolean makeAddButtionActive,boolean makeInfoButtionActive, boolean  makeShareButtonActive, boolean makeNarrationButtonActive, boolean makeNavigationButtonActive,boolean makeFlagButtonActive);
	public void clearActiveButton(boolean deselectAddButton,boolean deselectInfoButton,boolean deselectShareButtion,boolean deselectNarrationButton,boolean deselectNavigationButton,boolean deselectFlagButton);
	public void hidePlayerButtons(boolean isHidePlayerButtons,String collectionId);
	/**
	 * @function setTabPlaceRequest 
	 *  
	 * @description : this method used for to set the url 
	 *  
	 * @parm(s) : @param string tab name
	 * @parm(s) : @param b {@link Boolean}
	 * @parm(s) : @param c {@link Boolean}
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 * 
	*/
	public void setTabPlaceRequest(String string, boolean b, boolean c);
	public void showAddToolTip();
	public void makeFlagButtonOrange();
	public void hideStudentViewButton();
	public void closePreviewPlayer();
	public void setStudentViewLink();
}
