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
package org.ednovo.gooru.client.mvp.gsearch.util;

import java.util.HashMap;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.folders.event.RefreshFolderType;
import org.ednovo.gooru.client.mvp.shelf.collection.folders.events.RefreshFolderItemEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru
 * 
 */
public class SuccessPopupForResource extends PopupPanel {

	private static SuccessPopupForResourceUiBinder uiBinder = GWT
			.create(SuccessPopupForResourceUiBinder.class);

	interface SuccessPopupForResourceUiBinder extends
	UiBinder<Widget, SuccessPopupForResource> {
	}
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField Button btnContinueSearching,btnViewInMyCollections;
	@UiField Label lblSuccessText,cancelResourcePopupBtnLbl,headerLbl;
	
	private HashMap<String, String> params;
	private String selectedGooruOid,collectionName,searchType;
	private FolderDo folderDo;
	public SuccessPopupForResource() {
		setWidget(uiBinder.createAndBindUi(this));
		if(AppClientFactory.getCurrentPlaceToken().contains("search")){
			btnContinueSearching.setText(i18n.GL3191());
			btnContinueSearching.setTitle(i18n.GL3191());
		}else{
			btnContinueSearching.setText(i18n.GL0460());
			btnContinueSearching.setTitle(i18n.GL0460());
		}
		btnViewInMyCollections.addClickHandler(new ViewinMyCollection());
	}
	
	
	/**
	 * Inner class
	 */
	public class ViewinMyCollection implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Element element = Document.get().getElementById("fixedFilterSearchID");
			if(element!=null){
				element.removeAttribute("style");
			}
			hide();
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params); 
		}
	}
	public void setData(final String collectionName,String selectedGooruOid,HashMap<String, String> params,String type,FolderDo folderDo){
		setCollectionName(collectionName);
		setSelectedGooruOid(selectedGooruOid);
		setParams(params);
		this.params = params;
		this.folderDo = folderDo;
		this.searchType = type;
		StringBuffer buffer = new StringBuffer();
		if(type.equalsIgnoreCase("resource")){
			buffer.append(i18n.GL3192());
			buffer.append(" ");
			buffer.append(collectionName);
			buffer.append(i18n.GL_SPL_FULLSTOP());
			lblSuccessText.setText(String.valueOf(buffer)); 
			headerLbl.setText(i18n.GL3224());
		}else{
			buffer.append(i18n.GL3225());
			buffer.append(" ");
			buffer.append(collectionName);
			buffer.append(i18n.GL_SPL_FULLSTOP());
			headerLbl.setText(i18n.GL3223());
			lblSuccessText.setText(String.valueOf(buffer)); 
		}
	}
		
	public void enableTopFilters(){
		Element element = Document.get().getElementById("fixedFilterSearchID");
		if(element!=null){
			element.removeAttribute("style");
		}
		Window.enableScrolling(true);
	}

	
	@UiHandler("btnContinueSearching")
	public void clickOnContinue(ClickEvent clickevent){
		this.hide();
		enableTopFilters();
		AppClientFactory.fireEvent(new RefreshFolderItemEvent(folderDo, RefreshFolderType.INSERT, params,null));
		String nameToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		if(nameToken.equalsIgnoreCase(PlaceTokens.RESOURCE_PLAY )|| nameToken.equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY)
			|| nameToken.equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY)){
			Window.enableScrolling(false);
		}else{
			Window.enableScrolling(true);
		}
		
	}
	@UiHandler("cancelResourcePopupBtnLbl")
	public void clickOnCloseBtn (ClickEvent clickevent){
		this.hide();
		enableTopFilters();
	}

	/**
	 * @return the params
	 */
	public HashMap<String, String> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	/**
	 * @return the collectionName
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * @param collectionName the collectionName to set
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * @return the selectedGooruOid
	 */
	public String getSelectedGooruOid() {
		return selectedGooruOid;
	}

	/**
	 * @param selectedGooruOid the selectedGooruOid to set
	 */
	public void setSelectedGooruOid(String selectedGooruOid) {
		this.selectedGooruOid = selectedGooruOid;
	}
	
}
