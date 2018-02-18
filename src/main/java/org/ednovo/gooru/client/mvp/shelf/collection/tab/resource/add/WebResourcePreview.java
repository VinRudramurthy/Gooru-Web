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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.BlueButtonUc;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class WebResourcePreview extends PopupPanel {
	
	@UiField BlueButtonUc okButton, goBackBtn;
	
	
	@UiField Label descriptionTxtValueLbl,descriptionTxtLbl,filePathValueLbl,resourceTitleValueLbl,categoryValueLbl,thumbnailLbl,previewCloseButton;
	
	@UiField
	public Image setThumbnailImage;
	
	@UiField HTMLPanel actionPanel,areYouSureText;
	
	@UiField Label lblConfirmAdding,messageText,titleText,categoryText;
	
	private static WebResourcePreviewUiBinder uiBinder = GWT.create(WebResourcePreviewUiBinder.class);
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	interface WebResourcePreviewUiBinder extends UiBinder<Widget, WebResourcePreview> {
	}
	
	
	public WebResourcePreview() {
		setWidget(uiBinder.createAndBindUi(this));
		
		lblConfirmAdding.getElement().getStyle().setDisplay(Display.NONE);
		actionPanel.getElement().setId("pnlActionPanel");
		actionPanel.getElement().getStyle().setDisplay(Display.BLOCK);
		setGlassEnabled(true);
		areYouSureText.getElement().setInnerHTML(i18n.GL0363());
		areYouSureText.getElement().setId("pnlAreYouSureText");
		areYouSureText.getElement().setAttribute("alt", i18n.GL0363());
		areYouSureText.getElement().setAttribute("title", i18n.GL0363());
		messageText.setText(i18n.GL0930());
		messageText.getElement().setId("lblMessageText");
		messageText.getElement().setAttribute("alt", i18n.GL0930());
		messageText.getElement().setAttribute("title", i18n.GL0930());
		titleText.setText(i18n.GL0318());
		titleText.getElement().setId("lblTitleText");
		titleText.getElement().setAttribute("alt", i18n.GL0318());
		titleText.getElement().setAttribute("title", i18n.GL0318());
		descriptionTxtLbl.setText(i18n.GL0904());
		descriptionTxtLbl.getElement().setId("lblDescriptionTxtLbl");
		descriptionTxtLbl.getElement().setAttribute("alt", i18n.GL0904());
		descriptionTxtLbl.getElement().setAttribute("title", i18n.GL0904());
		categoryText.setText(i18n.GL0906());
		categoryText.getElement().setId("lblCategoryText");
		categoryText.getElement().setAttribute("alt", i18n.GL0906());
		categoryText.getElement().setAttribute("title", i18n.GL0906());
		thumbnailLbl.setText(i18n.GL0911());
		thumbnailLbl.getElement().setId("lblThumbnailLbl");
		thumbnailLbl.getElement().setAttribute("alt", i18n.GL0911());
		thumbnailLbl.getElement().setAttribute("title", i18n.GL0911());
		goBackBtn.setText(i18n.GL0841());
		goBackBtn.getElement().setId("bluebtnGoBackBtn");
		goBackBtn.getElement().setAttribute("alt", i18n.GL0841());
		goBackBtn.getElement().setAttribute("title", i18n.GL0841());
		okButton.setText(i18n.GL_GRR_YES());
		okButton.getElement().setId("bluebtnOkButton");
		okButton.getElement().setAttribute("alt", i18n.GL_GRR_YES());
		okButton.getElement().setAttribute("title", i18n.GL_GRR_YES());
		previewCloseButton.getElement().setId("lblPreviewCloseButton");
		filePathValueLbl.getElement().setId("lblFilePathValueLbl");
		resourceTitleValueLbl.getElement().setId("lblResourceTitleValueLbl");
		descriptionTxtValueLbl.getElement().setId("lblDescriptionTxtValueLbl");
		categoryValueLbl.getElement().setId("CategoryValueLbl");
		setThumbnailImage.getElement().setId("imgSetThumbnailImage");
		lblConfirmAdding.setText(i18n.GL0591().toLowerCase());
		lblConfirmAdding.getElement().setId("lblLblConfirmAdding");
		lblConfirmAdding.getElement().setAttribute("alt", i18n.GL0591().toLowerCase());
		lblConfirmAdding.getElement().setAttribute("title", i18n.GL0591().toLowerCase());
		setWidth("508px");
		this.getElement().setAttribute("style", "min-height:420px;");
		center();
	}
	
	
	
	@UiHandler("previewCloseButton")
	public void onClickClosePreviewBtn(ClickEvent event){
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		hide();
		closeAppPopUp();
	}
	
	@UiHandler("goBackBtn")
	public void hidePop(ClickEvent event){
		showAddResourcePopup();
	}
	
	
	@UiHandler("okButton")
	public void onAddResourceClick(ClickEvent event){
		MixpanelUtil.mixpanelEvent("Collaborator_edits_collection");
		addWebResource();
	}
	
	public abstract void showAddResourcePopup(); 
	public abstract void addWebResource(); 
	public abstract void closeAppPopUp();
	
	
	
	
	/** 
	 * This method is to get the actionPanel
	 */
	public HTMLPanel getActionPanel() {
		return actionPanel;
	}

	/** 
	 * This method is to set the actionPanel
	 */
	public void setActionPanel(HTMLPanel actionPanel) {
		this.actionPanel = actionPanel;
	}

	/** 
	 * This method is to get the lblAdding
	 */
	public Label getLblAdding() {
		return lblConfirmAdding;
	}

	/** 
	 * This method is to set the lblAdding
	 */
	public void setLblAdding(Label lblAdding) {
		this.lblConfirmAdding = lblAdding;
	}	
}
