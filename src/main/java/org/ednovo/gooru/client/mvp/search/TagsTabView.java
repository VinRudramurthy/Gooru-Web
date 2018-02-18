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
package org.ednovo.gooru.client.mvp.search;

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.search.SearchResourcesTagsDo;
import org.ednovo.gooru.application.shared.model.user.UserTagsDo;
import org.ednovo.gooru.client.event.InvokeLoginEvent;
import org.ednovo.gooru.client.mvp.addTagesPopup.AddTagesPopupView;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.profilepage.tab.content.tags.ProfileUserTagWidget;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.SuccessPopupViewVc;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @fileName : TagsTabView.java
 *
 * @description : 
 *
 * @version : 1.1
 *
 * @date: 25-Nov-2014
 *
 * @Author : Gooru Team
 *
 * @Reviewer: Gooru Team
 */

public class TagsTabView extends BaseViewWithHandlers<TagsTabUiHandlers> implements IsTagsTabView {
	
	private static TagsTabViewUiBinder uiBinder = GWT.create(TagsTabViewUiBinder.class);
	
	interface TagsTabViewUiBinder extends UiBinder<Widget, TagsTabView>{
	}
	
	private  MessageProperties i18n = GWT.create(MessageProperties.class);
	
	List<UserTagsDo> resourceTag = new ArrayList<UserTagsDo>();
	
	@UiField HTMLPanel tagsContainer,loadingImageLabel;
	
	@UiField Button addTagsBtn;
	
	@UiField ScrollPanel tagScrollPanel; 
	
	@UiField SearchTabTagsStyleBundle searchTagsTabStyle;
	
	Anchor tagsLbl = new Anchor();
	
	AddTagesPopupView addTagesPopupView;
	
	private String resourceGooruOid;
	
	private String offSet="0";
	
	private String limit="10";
	
	private int totCount;
	
	private int currentTagsDispalyCount;
	
	private boolean isTagsAdded = false;
	
	/**
	 * Class constructor.
	 */
	public TagsTabView(){
		
		setWidget(uiBinder.createAndBindUi(this));
		
		addTagsBtn.getElement().setAttribute("style", "padding: 4px 9px;");
		StringUtil.setAttributes(tagsContainer.getElement(), "tagsContainer", "", "");
		StringUtil.setAttributes(addTagsBtn.getElement(), "addTagsBtn", "", "Add Tags");
		StringUtil.setAttributes(tagScrollPanel.getElement(), "tagScrollPanel", "", "");
	}
	
	
	
	/**
	 * @param scrollEvent {@link ScrollEvent} 
	 */
	@UiHandler("tagScrollPanel")
	public void onScroll(ScrollEvent scrollEvent){
		
		if(getMoreTags()){
			getUiHandlers().getResourceTags(resourceGooruOid,Integer.toString(getCurrentTagsDispalyCount()),limit,false);
		}
		
	}
	
	
	/**
	 * @param clickEvent {@link ClickEvent} 
	 */
	@UiHandler("addTagsBtn")
	public void onAddTagsBtnClicked(ClickEvent clickEvent){
		
		if(AppClientFactory.isAnonymous()) {
			AppClientFactory.fireEvent(new InvokeLoginEvent());
		} else {
			Window.enableScrolling(false);

			addTagesPopupView=new AddTagesPopupView(resourceGooruOid) {

				/** 
				 * (non-Javadoc)
				 * @see org.ednovo.gooru.client.mvp.addTagesPopup.AddTagesPopupView#closePoup(boolean)
				 */
				@Override
				public void closePoup(boolean isCancelclicked) {
					this.hide();
					if(!isCancelclicked){
						SuccessPopupViewVc success = new SuccessPopupViewVc() {

							/** (non-Javadoc)
							 * @see org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.SuccessPopupViewVc#onClickPositiveButton(com.google.gwt.event.dom.client.ClickEvent)
							 */
							@Override
							public void onClickPositiveButton(ClickEvent event) {
								this.hide();
								isTagsAdded = true;
								setCurrentTagsDispalyCount(0);
								getUiHandlers().getResourceTags(resourceGooruOid,offSet,limit,true);
								if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.COLLECTION_SEARCH) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.RESOURCE_SEARCH)){
									Window.enableScrolling(false);
								}else{
									Window.enableScrolling(true);
								}
							}

						};
						success.setHeight("253px");
						success.setWidth("450px");
						success.setPopupTitle(i18n.GL1795());
						success.setDescText(i18n.GL1796());
						success.enableTaggingImage();
						success.setPositiveButtonText(i18n.GL0190());
						success.center();
						success.show();
					}else{
						if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.COLLECTION_SEARCH) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.RESOURCE_SEARCH)){
							Window.enableScrolling(false);
						}else{
							Window.enableScrolling(true);
						}
					}
				}

				@Override
				public void onSelection(SelectionEvent<Suggestion> event) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void showStandardsPopup(String standardVal, String standardsDesc,
						List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
					// TODO Auto-generated method stub
					
				}
			};
			addTagesPopupView.getAddStandards();
			addTagesPopupView.show();
			addTagesPopupView.setPopupPosition(addTagesPopupView.getAbsoluteLeft(),Window.getScrollTop()+10);
		}
	}

	/**
	 * Sets tags related to the resources.
	 * (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.search.IsTagsTabView#getResourceTags(java.util.List)
	 */
	@Override
	public void setResourceTags(SearchResourcesTagsDo searchResourcesTagsDo, boolean isTagsClear) {

		setTotCount(searchResourcesTagsDo.getTotalHitCount());
		setCurrentTagsDispalyCount(getCurrentTagsDispalyCount()+searchResourcesTagsDo.getSearchResults().size());
		clearTagsContainer(isTagsClear);
		if(getTotCount()==0){
			Label emptyTagsLbl = new Label();
			emptyTagsLbl.getElement().getStyle().setTextAlign(TextAlign.CENTER);
			emptyTagsLbl.getElement().getStyle().setMarginTop(40,Unit.PX);
			emptyTagsLbl.setText(i18n.GL3049());
			tagsContainer.add(emptyTagsLbl);
			
		}
		if(isTagsAdded){
			isTagsAdded = false;
			tagsLbl.setText(i18n.GL3048()+" "+i18n.GL_SPL_OPEN_SMALL_BRACKET()+getTotCount()+i18n.GL_SPL_CLOSE_SMALL_BRACKET());
//			AppClientFactory.fireEvent( new ResourceTagsCountUpdateEvent(getTotCount()));
		}
		
		for(int i=0; i<searchResourcesTagsDo.getSearchResults().size();i++){
			ProfileUserTagWidget profileUserTagWidget = new ProfileUserTagWidget(searchResourcesTagsDo.getSearchResults().get(i)); 
			profileUserTagWidget.setSearchTabTagLblStyleName(searchTagsTabStyle.searchTabTagLbl());
			profileUserTagWidget.setSearchTabTagRightStyleName(searchTagsTabStyle.searchTabTagRight());
			profileUserTagWidget.setSearchTagsBgStyleName(searchTagsTabStyle.searchTagsBg());
			tagsContainer.add(profileUserTagWidget);
		}
		loadingImageLabel.setVisible(false);
	}
	
	
	
	/**
	 * Pagination is handled in the following method.
	 * @return flag {@link Boolean}
	 */
	public boolean getMoreTags(){
		boolean flag = false;
		
		if (tagScrollPanel.getVerticalScrollPosition() == tagScrollPanel.getMaximumVerticalScrollPosition() && getCurrentTagsDispalyCount()<getTotCount()) {
			clearTagsContainer(false);
			flag = true;
		}
		
		return flag;
	}

	
	/**
	 * Clears the tags panel based on the boolean value.
	 * 
	 * @param isClear {@link Boolean}
	 */
	private void clearTagsContainer(boolean isClear) {
		if(isClear){
			tagsContainer.clear();
		}
		
	}


	/**
	 * Sets the loading image w.r.t boolean parameter
	 * (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.search.IsTagsTabView#isLoadingImageVisible(boolean)
	 */
	@Override
	public void isLoadingImageVisible(boolean isVisible) {
		loadingImageLabel.setVisible(isVisible);
	}


	/**
	 * @return the totCount
	 */
	public int getTotCount() {
		return totCount;
	}


	/**
	 * @param totCount the totCount to set
	 */
	public void setTotCount(int totCount) {
		this.totCount = totCount;
	}


	/**
	 * Api call is done to get tags related to resources.
	 * 
	 * resourceId {@link String}
	 * resourceGooruOid {@link String}
	 * 
	 * (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.search.IsTagsTabView#setResourceTagsData(java.lang.String, java.lang.String)
 	 */
	@Override
	public void setResourceTagsData(String resourceGooruOid, Anchor tagsLbl) {
		
		this.resourceGooruOid = resourceGooruOid;
		this.tagsLbl = tagsLbl;
		clearTagsContainer(true);
		setCurrentTagsDispalyCount(0);
		getUiHandlers().getResourceTags(resourceGooruOid,offSet,limit,true);
		
	}


	/**
	 * @return the currentTagsDispalyCount
	 */
	public int getCurrentTagsDispalyCount() {
		return currentTagsDispalyCount;
	}


	/**
	 * @param currentTagsDispalyCount the currentTagsDispalyCount to set
	 */
	public void setCurrentTagsDispalyCount(int currentTagsDispalyCount) {
		this.currentTagsDispalyCount = currentTagsDispalyCount;
	}

}
