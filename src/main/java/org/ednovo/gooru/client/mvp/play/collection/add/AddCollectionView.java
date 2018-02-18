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
package org.ednovo.gooru.client.mvp.play.collection.add;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class AddCollectionView extends BaseViewWithHandlers<AddCollectionUiHandlers> implements IsAddCollectionView,ClientConstants{


	private static AddCollectionViewUiBinder uiBinder = GWT.create(AddCollectionViewUiBinder.class);

	interface AddCollectionViewUiBinder extends UiBinder<Widget, AddCollectionView> {

	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	public String specialCh = "!@#$%^&*?";
	public String thumbnailUrl="";
	public String collectionGooruOid=null;

	
	@UiField TextBox collectionTitleInCoverPage;
	
	@UiField HTMLPanel addToCollectionWidgetContainer,addCollectionInsteadLabelContainer,collectionAddImageContainer,addResourceInsteadLabelContainerInCollectionImage,addcollectionText;
	
	@UiField Label addingLabel,addResourceInsteadLabel,addErrorLabel,successMessageLabelText,hideText,renameText;
	
	@UiField Button addToShelfCollectionButton;
	
	@UiField FlowPanel collectionAddedSuccessMessageContainer;
	
	@UiField Anchor workSpaceLink;

	@UiField HTMLEventPanel hideButton;
	
	@Inject
	public AddCollectionView(){
		setWidget(uiBinder.createAndBindUi(this));
		hideText.setText(i18n.GL0592());
		hideText.getElement().setId("lblHideText");
		hideText.getElement().setAttribute("alt",i18n.GL0592());
		hideText.getElement().setAttribute("title",i18n.GL0592());
		  
		addcollectionText.getElement().setInnerHTML(i18n.GL0690());
		addcollectionText.getElement().setId("pnlAddcollectionText");
		addcollectionText.getElement().setAttribute("alt",i18n.GL0690());
		addcollectionText.getElement().setAttribute("title",i18n.GL0690());
		
		renameText.setText(i18n.GL0593());
		renameText.getElement().setId("lblRenameText");
		renameText.getElement().setAttribute("alt",i18n.GL0593());
		renameText.getElement().setAttribute("title",i18n.GL0593());
		
		addToShelfCollectionButton.setText(i18n.GL0590());
		addToShelfCollectionButton.getElement().setId("btnAddToShelfCollectionButton");
		addToShelfCollectionButton.getElement().setAttribute("alt",i18n.GL0590());
		addToShelfCollectionButton.getElement().setAttribute("title",i18n.GL0590());
		
		collectionAddedSuccessMessageContainer.setVisible(false);
		getAddToShelfCollectionButton().addClickHandler(new OnAddCollectionClick());
		collectionTitleInCoverPage.addKeyUpHandler(new onKeyErrorMsg());
		workSpaceLink.setText(i18n.GL0589());
		workSpaceLink.getElement().setId("lnkWorkSpaceLink");
		workSpaceLink.getElement().setAttribute("alt",i18n.GL0589());
		workSpaceLink.getElement().setAttribute("title",i18n.GL0589());
		
		
		addToCollectionWidgetContainer.getElement().setId("pnlAddToCollectionWidgetContainer");
		collectionAddImageContainer.getElement().setId("pnlCollectionAddImageContainer");
		addResourceInsteadLabelContainerInCollectionImage.getElement().setId("pnlAddResourceInsteadLabelContainerInCollectionImage");
		collectionAddedSuccessMessageContainer.getElement().setId("pnlCollectionAddedSuccessMessageContainer");
		successMessageLabelText.getElement().setId("lblSuccessMessageLabelText");
		addCollectionInsteadLabelContainer.getElement().setId("pnlAddCollectionInsteadLabelContainer");
		collectionTitleInCoverPage.getElement().setId("txtCollectionTitleInCoverPage");
		StringUtil.setAttributes(collectionTitleInCoverPage, true);
		addingLabel.getElement().setId("lblAddingLabel");
		addErrorLabel.getElement().setId("errlblAddErrorLabel");
		hideButton.getElement().setId("epnlHideButton");
	}
	
	@UiHandler("workSpaceLink")
	public void workspaceLinkClickEvent(ClickEvent event){
		AppClientFactory.getPlaceManager().setRefreshPlace(true);
	}
	
	public Label getAddingLabel() {
		return addingLabel;
	}
	public void setAddingLabel(Label addingLabel) {
		this.addingLabel = addingLabel;
	}
	public Label getAddResourceViewButton() {
		return addResourceInsteadLabel;
	}
	public void setAddResourceInsteadLabel(Label addResourceInsteadLabel) {
		this.addResourceInsteadLabel = addResourceInsteadLabel;
	}
	public Label getAddErrorLabel() {
		return addErrorLabel;
	}
	public void setAddErrorLabel(Label addErrorLabel) {
		this.addErrorLabel = addErrorLabel;
	}
	public Button getAddToShelfCollectionButton() {
		return addToShelfCollectionButton;
	}
	public void setAddToShelfCollectionButton(
			Button addToShelfCollectionButton) {
		this.addToShelfCollectionButton = addToShelfCollectionButton;
	}
	public TextBox getCollectionTitleInCoverPage() {
		return collectionTitleInCoverPage;
	}
	public void setCollectionTitleInCoverPage(TextBox collectionTitleInCoverPage) {
		this.collectionTitleInCoverPage = collectionTitleInCoverPage;
	}
	public HTMLPanel getAddToCollectionWidgetContainer() {
		return addToCollectionWidgetContainer;
	}
	public void setAddToCollectionWidgetContainer(HTMLPanel addToCollectionWidgetContainer) {
		this.addToCollectionWidgetContainer = addToCollectionWidgetContainer;
	}
	
	public void showSuccessMessageWidget(String collectionId){
		getAddingLabel().setVisible(false);
		getAddToShelfCollectionButton().setVisible(true);
		if(ADDTEXTLBL.equalsIgnoreCase(getAddToShelfCollectionButton().getText())){
			getAddToShelfCollectionButton().setText(i18n.GL0691());
			successMessageLabelText.setText(i18n.GL0547());
			successMessageLabelText.getElement().setAttribute("alt",i18n.GL0547());
			successMessageLabelText.getElement().setAttribute("title",i18n.GL0547());
		}else{
			successMessageLabelText.setText(i18n.GL0692());
			successMessageLabelText.getElement().setAttribute("alt",i18n.GL0692());
			successMessageLabelText.getElement().setAttribute("title",i18n.GL0692());
		}
		addCollectionInsteadLabelContainer.clear();
		addResourceInsteadLabel.setText(i18n.GL0685());
		addCollectionInsteadLabelContainer.add(addResourceInsteadLabel);
		addResourceInsteadLabel.getElement().getStyle().setMarginRight(60,Unit.PX);
		collectionAddImageContainer.setVisible(false);
		collectionAddedSuccessMessageContainer.setVisible(true);
		workSpaceLink.setHref("#organize&id="+collectionId+"&eventType=refresh");
	}
	public void showCollectionAddImageWidget(){
		getAddToShelfCollectionButton().setText(i18n.GL0590());
		successMessageLabelText.setText(i18n.GL0547());
		successMessageLabelText.getElement().setAttribute("alt",i18n.GL0547());
		successMessageLabelText.getElement().setAttribute("title",i18n.GL0547());
		addResourceInsteadLabelContainerInCollectionImage.clear();
		addResourceInsteadLabelContainerInCollectionImage.add(addResourceInsteadLabel);
		
		addResourceInsteadLabel.getElement().getStyle().clearMarginLeft();
		addResourceInsteadLabel.getElement().getStyle().clearMarginTop();
		collectionAddImageContainer.setVisible(true);
		collectionAddedSuccessMessageContainer.setVisible(false);
	}
	
	public class OnAddCollectionClick implements ClickHandler {
		public void onClick(ClickEvent event) {
			addingLabel.setText(i18n.GL0591());
			addingLabel.getElement().setAttribute("alt",i18n.GL0591());
			addingLabel.getElement().setAttribute("title",i18n.GL0591());
			String title = getCollectionTitleInCoverPage().getText();
			if(title!=null){
				if(title.trim().length()==0){
					getAddErrorLabel().setVisible(true);
					getAddErrorLabel().setText(i18n.GL1425());
				} else if(title.trim().length()>50){
					getAddErrorLabel().setVisible(true);
					getAddErrorLabel().setText(i18n.GL1427());
				}else if (title.toLowerCase().contains("www.") || title.toLowerCase().contains("http://") || title.toLowerCase().contains("https://") || title.toLowerCase().contains("ftp://")){
					getAddErrorLabel().setVisible(true);
					getAddErrorLabel().setText(i18n.GL0323());
				}else if(title.contains(specialCh)){
					getAddErrorLabel().setVisible(true);
					getAddErrorLabel().setText(i18n.GL1426());
				}
				else{
				getAddingLabel().setVisible(true);
				getAddToShelfCollectionButton().setVisible(false);
				getAddErrorLabel().setVisible(false);
				copyCollection(collectionGooruOid,title);
				}
			}
	  }		
	}
	public class onKeyErrorMsg implements KeyUpHandler{
		@Override
		public void onKeyUp(KeyUpEvent event) {
			String text=getCollectionTitleInCoverPage().getText();
			if(text!=null){
				if(text.trim().length()>0){
					getAddErrorLabel().setVisible(false);
				}
				if(text.length()>50){
					getAddErrorLabel().setVisible(true);
					getAddErrorLabel().setText(i18n.GL1427());
				}	
			}
		}	
	}
	
	@Override
	public void setCollectionData(String collectionTitle, String collectionId,
			String collectionImageUrl) {
		collectionTitleInCoverPage.setValue(collectionTitle);
		this.collectionGooruOid=collectionId;
		this.thumbnailUrl=collectionImageUrl;
		getAddErrorLabel().setVisible(false);
		getAddingLabel().setVisible(false);
		getCollectionTitleInCoverPage().setVisible(true);
		showCollectionAddImageWidget();
	}
	public void copyCollection(String collectionId,String collectionTile){
		getUiHandlers().copyCollection( collectionId, collectionTile);
	}
	
	/**
	 * 
	 * @function onhideBtnClicked 
	 * 
	 * @created_date : 11-Dec-2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param clickEvent
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("hideButton")
	public void onhideBtnClicked(ClickEvent clickEvent) 
	{
		PlaceRequest collectionRequest = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
		String collectionId = collectionRequest.getParameter("id", null);
		String collectionItemId = collectionRequest.getParameter("rid", null);
		String chkViewParam = collectionRequest.getParameter("view", null);
	if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.RESOURCE_PLAY))
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.RESOURCE_PLAY).
				with("id", collectionId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam == null && collectionItemId != null)
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.COLLECTION_PLAY).
				with("id", collectionId).with("rid", collectionItemId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId != null)
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.PREVIEW_PLAY).
				with("id", collectionId).with("rid", collectionItemId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.COLLECTION_PLAY).
				with("id", collectionId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.PREVIEW_PLAY).
				with("id", collectionId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.COLLECTION_PLAY).
				with("id", collectionId).with("view", "end");
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.PREVIEW_PLAY).
				with("id", collectionId).with("view", "end");
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	}

}
