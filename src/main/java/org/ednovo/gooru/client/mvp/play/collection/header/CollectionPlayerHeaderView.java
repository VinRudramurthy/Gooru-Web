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
package org.ednovo.gooru.client.mvp.play.collection.header;


import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class CollectionPlayerHeaderView extends Composite implements ClientConstants{
	
	@UiField HTML resourceTitle;
	
	@UiField Button infoButton,shareButton,narrationButton,navigationButton,addButton,flagButton;
	
	@UiField Label closeButtonForCollection;
	
	@UiField Anchor studentViewButton;
	
	private boolean isInfoButtonEnabled=false;
	private boolean isShareButtonEnabled=false;
	private boolean isNarrationButtonEnabled=false;
	private static boolean isNavigationButtonEnabledClass=false;
	private boolean isAddButtonEnabled=false;
	private boolean isFlagButtonEnabled=false;
	private PopupPanel toolTipPopupPanel=new PopupPanel();
	
	private static CollectionPlayerHeaderViewUiBinder uiBinder = GWT.create(CollectionPlayerHeaderViewUiBinder.class);

	interface CollectionPlayerHeaderViewUiBinder extends UiBinder<Widget, CollectionPlayerHeaderView> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	public CollectionPlayerHeaderView(){
		initWidget(uiBinder.createAndBindUi(this));
		navigationButton.getElement().setId("navigationButton");
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		studentViewButton.setText(i18n.GL0139());
		studentViewButton.getElement().setId("lnkStudentViewButton");
		studentViewButton.getElement().setAttribute("alt",i18n.GL0139());
		studentViewButton.getElement().setAttribute("title",i18n.GL0139());
		  
		studentViewButton.addMouseOverHandler(new OnStudentViewButtonMouseOver());
		studentViewButton.addMouseOutHandler(new OnStudentViewButtonMouseOut());
		shareButton.addMouseOverHandler(new ShareButtonMouseOver());
		shareButton.addMouseOutHandler(new ShareButtonMouseOut());
		addButton.getElement().setId("addButton");
		flagButton.getElement().setId("btnFlagButton");
		infoButton.getElement().setId("btnInfoButton");
		shareButton.getElement().setId("btnShareButton");
		narrationButton.getElement().setId("btnNarrationButton");
		navigationButton.getElement().setId("btnNavigationButton");
		closeButtonForCollection.getElement().setId("lblCloseButtonForCollection");
		resourceTitle.getElement().setId("htmlResourceTitle");
	}
	
	public void setResourceTitle(String title){
		resourceTitle.setHTML(title);
	}

	public Button getInfoButton() {
		return infoButton;
	}

	public Button getShareButton() {
		return shareButton;
	}

	public Button getNarrationButton() {
		return narrationButton;
	}

	public Button getNavigationButton() {
		return navigationButton;
	}
	public Button getAddButton() {
		return addButton;
	}
	public Anchor getStudentViewButton() {
		return studentViewButton;
	}
	
	public void makeButtonActive(boolean makeAddButtonActive,boolean makeInfoButtionActive, boolean  makeShareButtonActive, boolean makeNarrationButtonActive, boolean makeNavigationButtonActive,boolean makeFlagButtonActive){
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
		}
	}
	public void enableButtons(boolean isAddButtonEnable,boolean isInfoButtonEnable, boolean isShareButtonEnable, boolean isNarrationButtonEnable, boolean isNavigationButtonEnable,boolean isFlagButtonEnable){
		enableAddButton(isAddButtonEnable);
		enableInfoButton(isInfoButtonEnable);
		enableShareButton(isShareButtonEnable);
		enableNarrationButton(isNarrationButtonEnable);
		enableNavigationButton(isNavigationButtonEnable);
		enableFlagButton(isFlagButtonEnable);
	}

	public void enableAddButton(boolean isAddButtonEnable){
		setAddButtonEnabled(isAddButtonEnable);
		getAddButton().getElement().removeAttribute("button");
		if(isAddButtonEnable){
			getAddButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().addButtonDisabled());
			getAddButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().addButtonActive());
			getAddButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().addButtonNormal());
		}else{
			getAddButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().addButtonActive());
			getAddButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().addButtonNormal());
			getAddButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().addButtonDisabled());
		}		
	}
	
	public void enableInfoButton(boolean isInfoButtonEnable){
		setInfoButtonEnabled(isInfoButtonEnable);
		getInfoButton().getElement().removeAttribute("button");
		if(isInfoButtonEnable){
			getInfoButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().infoButtonDisabled());
			getInfoButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().infoButtonActive());
			getInfoButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().infoButtonNormal());
		}else{
			getInfoButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().infoButtonActive());
			getInfoButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().infoButtonNormal());
			getInfoButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().infoButtonDisabled());
		}		
	}
	public void enableShareButton(boolean isShareButtonEnable){
		setShareButtonEnabled(isShareButtonEnable);
		getShareButton().getElement().removeAttribute("button");
		if(isShareButtonEnable){
			getShareButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().shareButtonDisabled());
			getShareButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().shareButtonActive());
			getShareButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().shareButtonNormal());
		}else{
			getShareButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().shareButtonActive());
			getShareButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().shareButtonNormal());
			getShareButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().shareButtonDisabled());
		
		}		
	}
	public void enableNarrationButton(boolean isNarrationButtonEnable){
		setNarrationButtonEnabled(isNarrationButtonEnable);
		getNarrationButton().getElement().removeAttribute("button");
		if(isNarrationButtonEnable){
			getNarrationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().narrationButtonDisabled());
			getNarrationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().narrationButtonActive());
			getNarrationButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().narrationButtonNormal());
		}else{
			getNarrationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().narrationButtonActive());
			getNarrationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().narrationButtonNormal());
			getNarrationButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().narrationButtonDisabled());
		}		
	}
	
	public void enableNavigationButton(boolean isNavigationButtonEnable){
		setNavigationButtonEnabled(isNavigationButtonEnable);
		getNavigationButton().getElement().removeAttribute("button");
		if(isNavigationButtonEnable){
			getNavigationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonDisabled());
			getNavigationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonActive());
			getNavigationButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonNormal());
		}else{
			getNavigationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonActive());
			getNavigationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonNormal());
			getNavigationButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonDisabled());
		}		
	}
	public void enableFlagButton(boolean isFlagButtonEnable)
	{
		setFlagButtonEnabled(isFlagButtonEnable);
		getFlagButton().getElement().removeAttribute("button");
		if(isFlagButtonEnable){
			getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonDisable());
			getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonActive());
			getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrangeActive());
			getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrange());
			getFlagButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonNormal());
		}else{
			getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonActive());
			getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonNormal());
			getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrangeActive());
			getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrange());
			getFlagButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonDisable());
		}
	}
	public void makeAddButtonActive(){
		String button=getAddButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)){
			getAddButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().addButtonActive());
			getAddButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().addButtonNormal());
			getAddButton().getElement().removeAttribute("button");
		}else{
			getAddButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().addButtonNormal());
			getAddButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().addButtonActive());
			getAddButton().getElement().setAttribute("button","active");
		}	
	}
	
	public void makeInfoButtonActive(){
		String button=getInfoButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)){
			getInfoButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().infoButtonActive());
			getInfoButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().infoButtonNormal());
			getInfoButton().getElement().removeAttribute("button");
		}else{
			getInfoButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().infoButtonNormal());
			getInfoButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().infoButtonActive());
			getInfoButton().getElement().setAttribute("button","active");
		}	
	}
	public void makeShareButtonActive(){
		String button=getShareButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)){
			getShareButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().shareButtonActive());
			getShareButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().shareButtonNormal());
			getShareButton().getElement().removeAttribute("button");
		}else{
			getShareButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().shareButtonNormal());
			getShareButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().shareButtonActive());
			getShareButton().getElement().setAttribute("button","active");
		}	
	}
	public void makeNarrationButtonActive(){
		String button=getNarrationButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)){
			getNarrationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().narrationButtonActive());
			getNarrationButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().narrationButtonNormal());
			getNarrationButton().getElement().removeAttribute("button");
		}else{
			getNarrationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().narrationButtonNormal());
			getNarrationButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().narrationButtonActive());
			getNarrationButton().getElement().setAttribute("button","active");
		}	
	}
	public void makeNavigationButtonActive(){
		String button=getNavigationButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)){
			getNavigationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonActive());
			getNavigationButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonNormal());
			getNavigationButton().getElement().removeAttribute("button");
		}else{
			getNavigationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonNormal());
			getNavigationButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonActive());
			getNavigationButton().getElement().setAttribute("button","active");
		}	
	}
	public void makeFlagButtonActive(){
		String button=getFlagButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)){
			if(getFlagButton().getStyleName().contains(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrangeActive())){
				getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrangeActive());
				getFlagButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrange());
			}else{
				getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonActive());
				getFlagButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonNormal());
			}
			getFlagButton().getElement().removeAttribute("button");
		}else{
			if(getFlagButton().getStyleName().contains(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrange())){
				getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrange());
				getFlagButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrangeActive());
			}else{
				getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonNormal());
				getFlagButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonActive());
			}
			getFlagButton().getElement().setAttribute("button","active");
		}	
	}
	public void makeFlagButtonOrange(){
		getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonActive());
		getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonNormal());
		getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonDisable());
		getFlagButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrange());
	}
	public void clearActiveButton(boolean deselectAddButton,boolean deselectInfoButton,boolean deselectShareButtion,boolean deselectNarrationButton,boolean deselectNavigationButton,boolean deselectFlagButton){
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
	}
	public void deselectAddButton(){
		String button=getAddButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)&&isAddButtonEnabled()){
			getAddButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().addButtonActive());
			getAddButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().addButtonNormal());
			getAddButton().getElement().removeAttribute("button");
		}
	}
	
	public void deselectInfoButton(){
		String button=getInfoButton().getElement().getAttribute("button");
		if(button!=null&&ACTIVE.equalsIgnoreCase(button)&&isInfoButtonEnabled()){
			getInfoButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().infoButtonActive());
			getInfoButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().infoButtonNormal());
			getInfoButton().getElement().removeAttribute("button");
		}
	}
	public void deselectShareButton(){
		String shareButtonVal=getShareButton().getElement().getAttribute("button");
		if(shareButtonVal!=null&&ACTIVE.equalsIgnoreCase(shareButtonVal)&&isShareButtonEnabled()){
			getShareButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().shareButtonActive());
			getShareButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().shareButtonNormal());
			getShareButton().getElement().removeAttribute("button");
		}
	}
	public void deselectNarrationButton(){
		String narrtionButtonVal=getNarrationButton().getElement().getAttribute("button");
		if(narrtionButtonVal!=null&&ACTIVE.equalsIgnoreCase(narrtionButtonVal)&&isNarrationButtonEnabled){
			getNarrationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().narrationButtonActive());
			getNarrationButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().narrationButtonNormal());
			getNarrationButton().getElement().removeAttribute("button");
		}
	}
	public void deselectNavigationButton(){
		String navigationButtonVal=getNavigationButton().getElement().getAttribute("button");
		if(navigationButtonVal!=null&&ACTIVE.equalsIgnoreCase(navigationButtonVal)&&isNavigationButtonEnabled()){
			getNavigationButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonActive());
			getNavigationButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().navigationButtonNormal());
			getNavigationButton().getElement().removeAttribute("button");
		}
	
	}
	public void deselectFlagButton(){
		String flagButtonVal=getFlagButton().getElement().getAttribute("button");
		if(flagButtonVal!=null&&ACTIVE.equalsIgnoreCase(flagButtonVal)&&isFlagButtonEnabled()){
			if(getFlagButton().getStyleName().contains(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrangeActive())||
				getFlagButton().getStyleName().contains(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrange())){
				getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrangeActive());
				getFlagButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrange());
			}else{
				getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonActive());
				getFlagButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonNormal());
			}
			getFlagButton().getElement().removeAttribute("button");
		}
	}
	/**
	 * allows user to preview student view of a collection
	 * 
	 * @param event
	 *            {@link ClickEvent} instance
	 */
	@UiHandler("studentViewButton")
	public void collectionPlay(ClickEvent event) {
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

	public boolean isNavigationButtonEnabled() {
		return isNavigationButtonEnabledClass;
	}

	public static void setNavigationButtonEnabled(boolean isNavigationButtonEnabled) {
		isNavigationButtonEnabledClass = isNavigationButtonEnabled;
	}
	
	public Label getCloseButton(){
		return closeButtonForCollection;
	}
	public boolean isAddButtonEnabled() {
		return isAddButtonEnabled;
	}

	public void setAddButtonEnabled(boolean isAddButtonEnabled) {
		this.isAddButtonEnabled = isAddButtonEnabled;
	}
	public Button getFlagButton()
	{
		return flagButton;
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
	
	public class OnStudentViewButtonMouseOver implements MouseOverHandler{
		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanel.clear();
			toolTipPopupPanel.setWidget(new GlobalToolTip(i18n.GL0668(),true));
			toolTipPopupPanel.setStyleName("");
			toolTipPopupPanel.setPopupPosition(studentViewButton.getElement().getAbsoluteLeft()-35, studentViewButton.getElement().getAbsoluteTop()+4);
			toolTipPopupPanel.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanel.show();
			toolTipPopupPanel.getElement().getStyle().setMarginLeft(57, Unit.PX);
		}
	}
	
	public class OnStudentViewButtonMouseOut implements MouseOutHandler{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanel.hide();
		}
	}
	public class ShareButtonMouseOver implements MouseOverHandler{
		@Override
		public void onMouseOver(MouseOverEvent event) {
			if(!isShareButtonEnabled){
			toolTipPopupPanel.clear();
			toolTipPopupPanel.setWidget(new GlobalToolTip(i18n.GL0679()));
			toolTipPopupPanel.setStyleName("");
			toolTipPopupPanel.setPopupPosition(shareButton.getElement().getAbsoluteLeft()+7, shareButton.getElement().getAbsoluteTop()+21);
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
