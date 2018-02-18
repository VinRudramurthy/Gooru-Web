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

import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ResourcePlayerHeaderView extends Composite implements ClientConstants{
	
	@UiField HTML resourceTitle;
	
	@UiField Button infoButton,shareButton,addButton,flagButton;
	

	@UiField Anchor closeButton;

	
	private boolean isInfoButtonEnabled=false;
	private boolean isShareButtonEnabled=false;
	private boolean isAddButtonEnabled=false;
	private boolean isFlagButtonEnabled=false;
	private static CollectionPlayerHeaderViewUiBinder uiBinder = GWT.create(CollectionPlayerHeaderViewUiBinder.class);

	interface CollectionPlayerHeaderViewUiBinder extends UiBinder<Widget, ResourcePlayerHeaderView> {
	}
	
	public ResourcePlayerHeaderView(){
		initWidget(uiBinder.createAndBindUi(this));
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		resourceTitle.getElement().setId("htmlResourceTitle");
		addButton.getElement().setId("addButton");
		flagButton.getElement().setId("btnFlagButton");
		infoButton.getElement().setId("btnInfoButton");
		shareButton.getElement().setId("btnShareButton");
		closeButton.getElement().setId("lblCloseButton");
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
	
	public Anchor getCloseButton(){
		return closeButton;
	}
	
	public Button getAddButton(){
		return addButton;
	}
	public Button getFlagButton(){
		return flagButton;
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

	public boolean isAddButtonEnabled() {
		return isAddButtonEnabled;
	}

	public void setAddButtonEnabled(boolean isAddButtonEnabled) {
		this.isAddButtonEnabled = isAddButtonEnabled;
	}
	
	public void makeButtonActive(boolean makeAddButtonActive,boolean makeInfoButtionActive, boolean  makeShareButtonActive,boolean makeFlagButtonActive){
		if(makeInfoButtionActive){
			makeInfoButtonActive();
		}else if(makeShareButtonActive){
			makeShareButtonActive();
		}else if(makeAddButtonActive){
			makeAddButtonActive();
		}else if(makeFlagButtonActive){
			makeFlagButtonActive();
		}
	}
	public void enableButtons(boolean isAddButtonEnabel,boolean isInfoButtonEnable, boolean isShareButtonEnable,boolean isFlagButtonEnable){
		enableAddButton(isAddButtonEnabel);
		enableInfoButton(isInfoButtonEnable);
		enableShareButton(isShareButtonEnable);
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
	
	public void clearActiveButton(boolean deselectAddButton,boolean deselectInfoButton,boolean deselectShareButtion,boolean deselectFlagButton){
		if(deselectInfoButton){
			deselectInfoButton();
		}
		if(deselectShareButtion){
			deselectShareButton();
		}
		if(deselectAddButton){
			deselectAddButton();
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
	public void makeFlagButtonOrange(){
		getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonActive());
		getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonNormal());
		getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonDisable());
		getFlagButton().addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().flagButtonOrange());
	}
	public boolean isFlagButtonEnabled() {
		return isFlagButtonEnabled;
	}

	public void setFlagButtonEnabled(boolean isFlagButtonEnabled) {
		this.isFlagButtonEnabled = isFlagButtonEnabled;
	}
	
}
