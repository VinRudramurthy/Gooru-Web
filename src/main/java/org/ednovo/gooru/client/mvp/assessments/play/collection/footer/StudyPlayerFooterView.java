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
package org.ednovo.gooru.client.mvp.assessments.play.collection.footer;



import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class StudyPlayerFooterView extends Composite implements ClientConstants{

	@UiField FlowPanel resourceAnimationContainer;

	@UiField Button infoButton,shareButton,addButton,flagButton;

	private Button narrationButton=null;

	private Button fullScreenButton=null;

	@UiField FooterStyleResource footerstyle;

	private boolean isInfoButtonEnabled=false;
	private boolean isShareButtonEnabled=false;
	private boolean isNarrationButtonEnabled=false;
	private boolean isFullScreenButtonEnabled=false;
	private static boolean isNavigationButtonEnabledClass=false;
	private boolean isAddButtonEnabled=false;
	private boolean isFlagButtonEnabled=false;
	private PopupPanel toolTipPopupPanel=new PopupPanel();
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	private static CollectionPlayerHeaderViewUiBinder uiBinder = GWT.create(CollectionPlayerHeaderViewUiBinder.class);

	interface CollectionPlayerHeaderViewUiBinder extends UiBinder<Widget, StudyPlayerFooterView> {
	}

	public StudyPlayerFooterView(){
		initWidget(uiBinder.createAndBindUi(this));
		addButton.getElement().setId("addButton");
		flagButton.getElement().setId("btnFlagButton");
		infoButton.getElement().setId("btnInfoButton");
		shareButton.getElement().setId("btnShareButton");
		shareButton.addMouseOverHandler(new ShareButtonMouseOver());
		shareButton.addMouseOutHandler(new ShareButtonMouseOut());

		/*addButton.setVisible(false);*/
	}
	/**
	 * @return the resourceAnimationContainer
	 */
	public FlowPanel getResourceAnimationContainer() {
		return resourceAnimationContainer;
	}

	/**
	 * @param resourceAnimationContainer the resourceAnimationContainer to set
	 */
	public void setResourceAnimationContainer(FlowPanel resourceAnimationContainer) {
		this.resourceAnimationContainer = resourceAnimationContainer;
	}

	public Button getInfoButton() {
		return infoButton;
	}

	public Button getShareButton() {
		return shareButton;
	}

	public void setNarrationButton(Button narrationButton){
		this.narrationButton=narrationButton;
		narrationButton.getElement().setId("btnNarrationButton");
		getNarrationButton().setStyleName(footerstyle.narrationButtonNormal());
	}

	public void setFullScreenButton(Button fullScreenButton){
		this.fullScreenButton=fullScreenButton;
		fullScreenButton.getElement().setId("btnFullScreenButton");
		getFullScreenButton().setStyleName(footerstyle.fullscreenButtonNormal());
	}

	public Button getNarrationButton() {
		return narrationButton;
	}

	public Button getFullScreenButton() {
		return fullScreenButton;
	}

	public Button getAddButton() {
		return addButton;
	}
	public Button getFlagButton(){
		return flagButton;
	}
	public void makeButtonActive(boolean makeAddButtonActive,boolean makeInfoButtionActive, boolean  makeShareButtonActive, boolean makeNarrationButtonActive, boolean makeNavigationButtonActive,boolean makeFlagButtonActive,boolean makefullScreenButtonActive){
		if(makeAddButtonActive){
			makeAddButtonActive();
		}
		else if(makeInfoButtionActive){
			makeInfoButtonActive();
		}else if(makeShareButtonActive){
			makeShareButtonActive();
		}else if(makeNarrationButtonActive){
			makeNarrationButtonActive();
		}else if(makeNavigationButtonActive){
			makeNavigationButtonActive();
		}
		else if(makeFlagButtonActive){
			makeFlagButtonActive();
		}else if(makefullScreenButtonActive){
			makefullScreenButtonActive();
		}
	}
	public void enableButtons(boolean isAddButtonEnable,boolean isInfoButtonEnable, boolean isShareButtonEnable, boolean isNarrationButtonEnable, boolean isNavigationButtonEnable,boolean isFlagButtonEnable,boolean isFullScreenButtonEnable){
		enableAddButton(isAddButtonEnable);
		enableInfoButton(isInfoButtonEnable);
		enableShareButton(isShareButtonEnable);
		enableNarrationButton(isNarrationButtonEnable);
		enableFullScreennButton(isFullScreenButtonEnable);
		enableNavigationButton(isNavigationButtonEnable);
		enableFlagButton(isFlagButtonEnable);
	}

	public void enableAddButton(boolean isAddButtonEnable){
		setAddButtonEnabled(isAddButtonEnable);
		getAddButton().getElement().removeAttribute("button");
		if(isAddButtonEnable){
			getAddButton().removeStyleName(footerstyle.addButtonDisabled());
			getAddButton().removeStyleName(footerstyle.addButtonActive());
			getAddButton().addStyleName(footerstyle.addButtonNormal());
		}else{
			getAddButton().removeStyleName(footerstyle.addButtonActive());
			getAddButton().removeStyleName(footerstyle.addButtonNormal());
			getAddButton().addStyleName(footerstyle.addButtonDisabled());
		}
	}

	public void enableInfoButton(boolean isInfoButtonEnable){
		setInfoButtonEnabled(isInfoButtonEnable);
		getInfoButton().getElement().removeAttribute("button");
		if(isInfoButtonEnable){
			getInfoButton().removeStyleName(footerstyle.infoButtonDisabled());
			getInfoButton().removeStyleName(footerstyle.infoButtonActive());
			getInfoButton().addStyleName(footerstyle.infoButtonNormal());
		}else{
			getInfoButton().removeStyleName(footerstyle.infoButtonActive());
			getInfoButton().removeStyleName(footerstyle.infoButtonNormal());
			getInfoButton().addStyleName(footerstyle.infoButtonDisabled());
		}
	}
	public void enableShareButton(boolean isShareButtonEnable){
		setShareButtonEnabled(isShareButtonEnable);
		getShareButton().getElement().removeAttribute("button");
		if(isShareButtonEnable){
			getShareButton().removeStyleName(footerstyle.shareButtonDisabled());
			getShareButton().removeStyleName(footerstyle.shareButtonActive());
			getShareButton().addStyleName(footerstyle.shareButtonNormal());
		}else{
			getShareButton().removeStyleName(footerstyle.shareButtonActive());
			getShareButton().removeStyleName(footerstyle.shareButtonNormal());
			getShareButton().addStyleName(footerstyle.shareButtonDisabled());

		}
	}
	public void enableNarrationButton(boolean isNarrationButtonEnable){
		setNarrationButtonEnabled(isNarrationButtonEnable);
		getNarrationButton().getElement().removeAttribute("button");
		if(isNarrationButtonEnable){
			getNarrationButton().removeStyleName(footerstyle.narrationButtonDisabled());
			getNarrationButton().removeStyleName(footerstyle.narrationButtonActive());
			getNarrationButton().addStyleName(footerstyle.narrationButtonNormal());
		}else{
			getNarrationButton().removeStyleName(footerstyle.narrationButtonActive());
			getNarrationButton().removeStyleName(footerstyle.narrationButtonNormal());
			getNarrationButton().addStyleName(footerstyle.narrationButtonDisabled());
		}
	}
	public void enableFullScreennButton(boolean isFullScreenButtonEnable){
		setFullScreenButtonEnabled(isFullScreenButtonEnable);
		getFullScreenButton().getElement().removeAttribute("button");
		if(isFullScreenButtonEnable){
			getFullScreenButton().removeStyleName(footerstyle.fullscreenButtonDisabled());
			getFullScreenButton().removeStyleName(footerstyle.fullscreenButtonActive());
			getFullScreenButton().addStyleName(footerstyle.fullscreenButtonNormal());
		}else{
			getFullScreenButton().removeStyleName(footerstyle.fullscreenButtonActive());
			getFullScreenButton().removeStyleName(footerstyle.fullscreenButtonNormal());
			getFullScreenButton().addStyleName(footerstyle.fullscreenButtonDisabled());
		}
	}

	public void enableNavigationButton(boolean isNavigationButtonEnable){
	}
	public void enableFlagButton(boolean isFlagButtonEnable)
	{
		setFlagButtonEnabled(isFlagButtonEnable);
		getFlagButton().getElement().removeAttribute("button");
		if(isFlagButtonEnable){
			getFlagButton().removeStyleName(footerstyle.flagButtonDisable());
			getFlagButton().removeStyleName(footerstyle.flagButtonActive());
			getFlagButton().removeStyleName(footerstyle.flagButtonOrangeActive());
			getFlagButton().removeStyleName(footerstyle.flagButtonOrange());
			getFlagButton().addStyleName(footerstyle.flagButtonNormal());
		}else{
			getFlagButton().removeStyleName(footerstyle.flagButtonActive());
			getFlagButton().removeStyleName(footerstyle.flagButtonNormal());
			getFlagButton().removeStyleName(footerstyle.flagButtonOrangeActive());
			getFlagButton().removeStyleName(footerstyle.flagButtonOrange());
			getFlagButton().addStyleName(footerstyle.flagButtonDisable());
		}
	}
	public void makeAddButtonActive(){
		String button=getAddButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)){
			getAddButton().removeStyleName(footerstyle.addButtonActive());
			getAddButton().addStyleName(footerstyle.addButtonNormal());
			getAddButton().getElement().removeAttribute("button");
		}else{
			getAddButton().removeStyleName(footerstyle.addButtonNormal());
			getAddButton().addStyleName(footerstyle.addButtonActive());
			getAddButton().getElement().setAttribute("button","active");
		}
	}

	public void makeInfoButtonActive(){
		String button=getInfoButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)){
			getInfoButton().removeStyleName(footerstyle.infoButtonActive());
			getInfoButton().addStyleName(footerstyle.infoButtonNormal());
			getInfoButton().getElement().removeAttribute("button");
		}else{
			getInfoButton().removeStyleName(footerstyle.infoButtonNormal());
			getInfoButton().addStyleName(footerstyle.infoButtonActive());
			getInfoButton().getElement().setAttribute("button","active");
		}
	}
	public void makeShareButtonActive(){
		String button=getShareButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)){
			getShareButton().removeStyleName(footerstyle.shareButtonActive());
			getShareButton().addStyleName(footerstyle.shareButtonNormal());
			getShareButton().getElement().removeAttribute("button");
		}else{
			getShareButton().removeStyleName(footerstyle.shareButtonNormal());
			getShareButton().addStyleName(footerstyle.shareButtonActive());
			getShareButton().getElement().setAttribute("button","active");
		}
	}
	public void makeNarrationButtonActive(){
		String button=getNarrationButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)){
			getNarrationButton().removeStyleName(footerstyle.narrationButtonActive());
			getNarrationButton().addStyleName(footerstyle.narrationButtonNormal());
			getNarrationButton().getElement().removeAttribute("button");
		}else{
			getNarrationButton().removeStyleName(footerstyle.narrationButtonNormal());
			getNarrationButton().addStyleName(footerstyle.narrationButtonActive());
			getNarrationButton().getElement().setAttribute("button","active");
		}
	}
	public void makefullScreenButtonActive(){
		String button=getFullScreenButton().getElement().getAttribute("button");
		if(button!=null&&button.equalsIgnoreCase("active")){
			getFullScreenButton().removeStyleName(footerstyle.fullscreenButtonActive());
			getFullScreenButton().addStyleName(footerstyle.fullscreenButtonNormal());
			getFullScreenButton().getElement().removeAttribute("button");
		}else{
			getFullScreenButton().removeStyleName(footerstyle.fullscreenButtonNormal());
			getFullScreenButton().addStyleName(footerstyle.fullscreenButtonActive());
			getFullScreenButton().getElement().setAttribute("button","active");
		}
	}
	public void makeNavigationButtonActive(){

	}
	public void makeFlagButtonActive(){
		String button=getFlagButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)){
			if(getFlagButton().getStyleName().contains(footerstyle.flagButtonOrangeActive())){
				getFlagButton().removeStyleName(footerstyle.flagButtonOrangeActive());
				getFlagButton().addStyleName(footerstyle.flagButtonOrange());
			}else{
				getFlagButton().removeStyleName(footerstyle.flagButtonActive());
				getFlagButton().addStyleName(footerstyle.flagButtonNormal());
			}
			getFlagButton().getElement().removeAttribute("button");
		}else{
			if(getFlagButton().getStyleName().contains(footerstyle.flagButtonOrange())){
				getFlagButton().removeStyleName(footerstyle.flagButtonOrange());
				getFlagButton().addStyleName(footerstyle.flagButtonOrangeActive());
			}else{
				getFlagButton().removeStyleName(footerstyle.flagButtonNormal());
				getFlagButton().addStyleName(footerstyle.flagButtonActive());
			}
			getFlagButton().getElement().setAttribute("button","active");
		}
	}
	public void makeFlagButtonOrange(){
		getFlagButton().removeStyleName(footerstyle.flagButtonActive());
		getFlagButton().removeStyleName(footerstyle.flagButtonNormal());
		getFlagButton().removeStyleName(footerstyle.flagButtonDisable());
		getFlagButton().addStyleName(footerstyle.flagButtonOrange());
	}
	public void clearActiveButton(boolean deselectAddButton,boolean deselectInfoButton,boolean deselectShareButtion,boolean deselectNarrationButton,boolean deselectNavigationButton,boolean deselectFlagButton,boolean deselectFullScreenButton){
		if(deselectAddButton){
			deselectAddButton();
		}
		if(deselectInfoButton){
			deselectInfoButton();
		}
		if(deselectShareButtion){
			deselectShareButton();
		}
		if(deselectNarrationButton){
			deselectNarrationButton();
		}
		if(deselectNavigationButton){
			deselectNavigationButton();
		}
		if(deselectFlagButton){
			deselectFlagButton();
		}
		if(deselectFullScreenButton){
			deselectFullScreenButton();
		}
	}
	public void deselectAddButton(){
		String button=getAddButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)&&isAddButtonEnabled()){
			getAddButton().removeStyleName(footerstyle.addButtonActive());
			getAddButton().addStyleName(footerstyle.addButtonNormal());
			getAddButton().getElement().removeAttribute("button");
		}
	}

	public void deselectInfoButton(){
		String button=getInfoButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)&&isInfoButtonEnabled()){
			getInfoButton().removeStyleName(footerstyle.infoButtonActive());
			getInfoButton().addStyleName(footerstyle.infoButtonNormal());
			getInfoButton().getElement().removeAttribute("button");
		}
	}
	public void deselectShareButton(){
		String shareButtonVal=getShareButton().getElement().getAttribute("button");
		if(shareButtonVal!=null&&ACTIVE.equalsIgnoreCase(shareButtonVal)&&isShareButtonEnabled()){
			getShareButton().removeStyleName(footerstyle.shareButtonActive());
			getShareButton().addStyleName(footerstyle.shareButtonNormal());
			getShareButton().getElement().removeAttribute("button");
		}
	}
	public void deselectNarrationButton(){
		String narrtionButtonVal=getNarrationButton().getElement().getAttribute("button");
		if(narrtionButtonVal!=null&&ACTIVE.equalsIgnoreCase(narrtionButtonVal)&&isNarrationButtonEnabled){
			getNarrationButton().removeStyleName(footerstyle.narrationButtonActive());
			getNarrationButton().addStyleName(footerstyle.narrationButtonNormal());
			getNarrationButton().getElement().removeAttribute("button");
		}
	}
	public void deselectFullScreenButton(){
		String fullscreenButtonVal=getFullScreenButton().getElement().getAttribute("button");
		if(fullscreenButtonVal!=null&&fullscreenButtonVal.equalsIgnoreCase("active")&&isFullScreenButtonEnabled){
			getFullScreenButton().removeStyleName(footerstyle.fullscreenButtonActive());
			getFullScreenButton().addStyleName(footerstyle.fullscreenButtonNormal());
			getFullScreenButton().getElement().removeAttribute("button");
		}
	}
	public void deselectNavigationButton(){

	}
	public void deselectFlagButton(){
		String flagButtonVal=getFlagButton().getElement().getAttribute("button");
		if(flagButtonVal!=null&&ACTIVE.equalsIgnoreCase(flagButtonVal)&&isFlagButtonEnabled()){
			if(getFlagButton().getStyleName().contains(footerstyle.flagButtonOrangeActive())||
				getFlagButton().getStyleName().contains(footerstyle.flagButtonOrange())){
				getFlagButton().removeStyleName(footerstyle.flagButtonOrangeActive());
				getFlagButton().addStyleName(footerstyle.flagButtonOrange());
			}else{
				getFlagButton().removeStyleName(footerstyle.flagButtonActive());
				getFlagButton().addStyleName(footerstyle.flagButtonNormal());
			}
			getFlagButton().getElement().removeAttribute("button");
		}
	}

	public boolean isInfoButtonEnabled() {
		return isInfoButtonEnabled;
	}
	public void setInfoButtonEnabled(boolean isInfoButtonEnabled) {
		this.isInfoButtonEnabled = isInfoButtonEnabled;
	}

	public boolean isShareButtonEnabled() {
		return isShareButtonEnabled;
	}

	public void setShareButtonEnabled(boolean isShareButtonEnabled) {
		this.isShareButtonEnabled = isShareButtonEnabled;
	}

	public boolean isNarrationButtonEnabled() {
		return isNarrationButtonEnabled;
	}

	public void setNarrationButtonEnabled(boolean isNarrationButtonEnabled) {
		this.isNarrationButtonEnabled = isNarrationButtonEnabled;
	}

	public void setFullScreenButtonEnabled(boolean isFullScreenButtonEnabled) {
		this.isFullScreenButtonEnabled = isFullScreenButtonEnabled;
	}

	public boolean isNavigationButtonEnabled() {
		return isNavigationButtonEnabledClass;
	}

	public static void setNavigationButtonEnabled(boolean isNavigationButtonEnabled) {
		isNavigationButtonEnabledClass = isNavigationButtonEnabled;
	}
	public boolean isAddButtonEnabled() {
		return isAddButtonEnabled;
	}

	public void setAddButtonEnabled(boolean isAddButtonEnabled) {
		this.isAddButtonEnabled = isAddButtonEnabled;
	}
	public boolean isFlagButtonEnabled() {
		return isFlagButtonEnabled;
	}
	public void setFlagButtonEnabled(boolean isFlagButtonEnabled) {
		this.isFlagButtonEnabled = isFlagButtonEnabled;
	}
	public static void setNavigationFlag()
	{
		setNavigationButtonEnabled(false);
	}

	public class ShareButtonMouseOver implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			if(!isShareButtonEnabled){
				toolTipPopupPanel.clear();
				GlobalToolTip globalToolTip=new GlobalToolTip(i18n.GL0679());
				globalToolTip.getPanelArrow().getElement().setAttribute("style", "position: absolute;top:45px;transform: rotate(180deg);-ms-transform: rotate(180deg);-webkit-transform: rotate(180deg)");
				toolTipPopupPanel.setWidget(globalToolTip);
				toolTipPopupPanel.setStyleName("");
				toolTipPopupPanel.setPopupPosition(shareButton.getElement().getAbsoluteLeft()-13, shareButton.getElement().getAbsoluteTop()-84);
				toolTipPopupPanel.getElement().getStyle().clearMarginLeft();
				toolTipPopupPanel.getElement().getStyle().setZIndex(999999);
				toolTipPopupPanel.show();
			}
		}

	}

	public class ShareButtonMouseOut implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanel.hide();
		}
	}


}
