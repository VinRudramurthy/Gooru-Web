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
package org.ednovo.gooru.client.mvp.play.collection.preview.end;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.home.assign.AssignPopupPlayerVc;
import org.ednovo.gooru.client.mvp.play.collection.preview.home.customize.RenameCustomizePopUp;
import org.ednovo.gooru.client.mvp.play.collection.preview.home.share.SharePlayerVc;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class PreviewEndView extends BaseViewWithHandlers<PreviewEndUiHandlers> implements IsPreviewEndView,ClientConstants{
	
	@UiField Image collectionThumbnail;
	@UiField HTML collectionGoal;
	@UiField Button assignCollectionBtn,customizeCollectionBtn,shareCollectionBtn;
	@UiField HTMLPanel endText;
	@UiField Label replayCollection,learningobjectiveText;
	@UiField FlowPanel thumbnailContainer;
	
	
	private boolean isAssignPopup = false;
	
	private boolean isCustomizePopup = false;
	
	private boolean isSharePopup = false;
	
	private PopupPanel toolTipPopupPanel=new PopupPanel();
	
	private String collectionTitle;
	private static PreviewEndViewUiBinder uiBinder = GWT.create(PreviewEndViewUiBinder.class);

	interface PreviewEndViewUiBinder extends UiBinder<Widget, PreviewEndView> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@Inject
	public PreviewEndView(){
		setWidget(uiBinder.createAndBindUi(this));
		endText.getElement().setInnerHTML(i18n.GL0596());
		endText.getElement().setId("pnlEndText");
		endText.getElement().setAttribute("alt",i18n.GL0596());
		endText.getElement().setAttribute("title",i18n.GL0596());
		
		assignCollectionBtn.setText(i18n.GL0526());
		assignCollectionBtn.getElement().setId("btnAssignCollectionBtn");
		assignCollectionBtn.getElement().setAttribute("alt",i18n.GL0526());
		assignCollectionBtn.getElement().setAttribute("title",i18n.GL0526());
		
		customizeCollectionBtn.setText(i18n.GL2037());
		customizeCollectionBtn.getElement().setId("btnCustomizeCollectionBtn");
		customizeCollectionBtn.getElement().setAttribute("alt",i18n.GL2037());
		customizeCollectionBtn.getElement().setAttribute("title",i18n.GL2037());
		
		shareCollectionBtn.setText(i18n.GL0536());
		shareCollectionBtn.getElement().setId("btnShareCollectionBtn");
		shareCollectionBtn.getElement().setAttribute("alt",i18n.GL0536());
		shareCollectionBtn.getElement().setAttribute("title",i18n.GL0536());
		
		replayCollection.setText(i18n.GL0632());
		replayCollection.getElement().setId("lblReplayCollection");
		replayCollection.getElement().setAttribute("alt",i18n.GL0632());
		replayCollection.getElement().setAttribute("title",i18n.GL0632());
		
		learningobjectiveText.setText(i18n.GL0618());
		learningobjectiveText.getElement().setId("lblLearningobjectiveText");
		learningobjectiveText.getElement().setAttribute("alt",i18n.GL0618());
		learningobjectiveText.getElement().setAttribute("title",i18n.GL0618());
		
		collectionThumbnail.getElement().setId("imgCollectionThumbnail");
		thumbnailContainer.getElement().setId("fpnlThumbnailContainer");
		collectionGoal.getElement().setId("htmlCollectionGoal");
		
		
		assignCollectionBtn.addMouseOverHandler(new OnassignCollectionBtnMouseOver());
		assignCollectionBtn.addMouseOutHandler(new OnassignCollectionBtnMouseOut());
		customizeCollectionBtn.addMouseOverHandler(new OncustomizeCollectionBtnMouseOver());
		customizeCollectionBtn.addMouseOutHandler(new OncustomizeCollectionBtnMouseOut());
		shareCollectionBtn.addMouseOverHandler(new OnshareCollectionBtnMouseOver());
		shareCollectionBtn.addMouseOutHandler(new OnshareCollectionBtnMouseOut());
	}
	@UiHandler("collectionThumbnail")
	public void thumbnailErrorImage(ErrorEvent event){
		collectionThumbnail.setUrl("images/default-collection-image-160x120.png");
	}
	
	@Override
	public void setCollectionMetadata(final CollectionDo collectionDo){
		showPopupAfterGmailSignin();
		setCollectionImage(collectionDo.getThumbnails().getUrl());
		setCollectionGoal(collectionDo.getGoals());
		assignCollectionBtn.getElement().setAttribute("collectionId", collectionDo.getGooruOid());
		customizeCollectionBtn.getElement().setAttribute("collectionId", collectionDo.getGooruOid());
		shareCollectionBtn.getElement().setAttribute("collectionId", collectionDo.getGooruOid());
		setReplyLink();
		collectionTitle = collectionDo.getTitle();
	}
	public void setReplyLink(){
		Anchor resourceAnchor=new Anchor();
		resourceAnchor.setHref(getReplayLink());
		resourceAnchor.setStyleName("");
		HTMLPanel collectionHTMLPanel = new HTMLPanel("");
		resourceAnchor.addClickHandler(new ReplayCollectionEvent());
		collectionHTMLPanel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().collectionreplay());
		Label collectionReplayButton=new Label(i18n.GL0632());
		collectionReplayButton.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().collectionreplayText());
		collectionHTMLPanel.add(collectionReplayButton);
		resourceAnchor.getElement().appendChild(collectionHTMLPanel.getElement());
		thumbnailContainer.clear();
		thumbnailContainer.add(resourceAnchor);
	}
	
	private class ReplayCollectionEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			getUiHandlers().resetCollectionActivityEventId();
		}
	}
	public String getReplayLink(){
		String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
		String viewToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		String resourceLink="#"+viewToken+"&id="+collectionId;
		resourceLink += PreviewPlayerPresenter.setConceptPlayerParameters();
		return resourceLink;
	}
	public void setCollectionImage(String thumbnailUrl){
		collectionThumbnail.setUrl(thumbnailUrl);
	}
	public void setCollectionGoal(String collectionGoal){
		this.collectionGoal.setHTML("");
		if(!StringUtil.isEmpty(collectionGoal)){
			if(collectionGoal.length()>415){
				collectionGoal =(collectionGoal.substring(0, 415))+"...";
				this.collectionGoal.setHTML(collectionGoal);
			}
			else{
				this.collectionGoal.setHTML(collectionGoal);
			}
		}else{
			this.collectionGoal.setHTML(i18n.GL1374());
		}
	}
	
	/**
	 * 
	 * @function onassignCollectionBtnClicked 
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
	 * 
	 */
	@UiHandler("assignCollectionBtn")
	public void onassignCollectionBtnClicked(ClickEvent clickEvent) {
		String collectionId = clickEvent.getRelativeElement().getAttribute("collectionId");
				if(!isAssignPopup){
					isAssignPopup=true;
				AssignPopupPlayerVc successPopupVc = new AssignPopupPlayerVc(collectionId) {
					@Override
					public void closePoup() {
						Window.enableScrolling(true);
				        this.hide();
				    	isAssignPopup=false;
					}
				};
				Window.scrollTo(0, 0);
				successPopupVc.setWidth("500px");
				successPopupVc.setHeight("635px");

				successPopupVc.show();
				successPopupVc.center();
				if (AppClientFactory.isAnonymous()){
				successPopupVc.setPopupPosition(successPopupVc.getAbsoluteLeft(), 30);
				}
				else
				{
				successPopupVc.setPopupPosition(successPopupVc.getAbsoluteLeft(), 30);
				}

				Map<String,String> params = StringUtil.splitQuery(Window.Location.getHref());
				params.put("assign", "yes");
				PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
				AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
			}
	}
	/**
	 *
	 * @function oncustomizeCollectionBtnClicked
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
	@UiHandler("customizeCollectionBtn")
	public void oncustomizeCollectionBtnClicked(ClickEvent clickEvent) {
		String collectionId = clickEvent.getRelativeElement().getAttribute("collectionId");
				if(!isCustomizePopup){
					isCustomizePopup=true;
				Boolean loginFlag = AppClientFactory.isAnonymous();
				RenameCustomizePopUp successPopupVc = new RenameCustomizePopUp(collectionId, loginFlag,collectionTitle) {
					@Override
					public void closePoup() {
						Window.enableScrolling(true);
						this.hide();	
						isCustomizePopup = false;
					}
				};
				Window.scrollTo(0, 0);
			//	successPopupVc.setWidth("500px");
				successPopupVc.center();
				successPopupVc.show();
				
			}
				
				Map<String,String> params = new HashMap<String,String>();
				params.put("id", AppClientFactory.getPlaceManager().getRequestParameter("id"));
				if(AppClientFactory.getPlaceManager().getRequestParameter("subject")!=null)
					params.put("subject", AppClientFactory.getPlaceManager().getRequestParameter("subject"));
				if(AppClientFactory.getPlaceManager().getRequestParameter("lessonId")!=null)
					params.put("lessonId", AppClientFactory.getPlaceManager().getRequestParameter("lessonId"));
				if(AppClientFactory.getPlaceManager().getRequestParameter("view")!=null)
					params.put("view", AppClientFactory.getPlaceManager().getRequestParameter("view"));
				params.put("customize", "yes");
				PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
				AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	/**
	 * 
	 * @function oncustomizeCollectionBtnClicked 
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
	@UiHandler("shareCollectionBtn")
	public void onshareCollectionBtnClicked(ClickEvent clickEvent) {
		final String collectionId = clickEvent.getRelativeElement().getAttribute("collectionId");
				if(!isSharePopup){
					isSharePopup=true;
				SharePlayerVc successPopupVc = new SharePlayerVc(collectionId) {
					@Override
					public void closePoup() {
						Window.enableScrolling(true);
						this.hide();	
						isSharePopup = false;
					}
					public void triggerShareEvent(String shareType,boolean confirmStatus){
						getUiHandlers().triggerCollectionShareDataEvent(collectionId,PlayerDataLogEvents.COLLECTION,shareType,confirmStatus);
					}
				};
				Window.scrollTo(0, 0);
				successPopupVc.setWidth("500px");
				successPopupVc.setHeight("350px");
				successPopupVc.show();
				successPopupVc.center();
			}
	}
	
	
	/**
	 * 
	 * Showing Customize or Assign popup after login with gmail account.
	 * 
	 */
	
	private void showPopupAfterGmailSignin() {
		String collectionId = AppClientFactory.getPlaceManager().getRequestParameter("id")!=null ? AppClientFactory.getPlaceManager().getRequestParameter("id") : null;
		String customize = AppClientFactory.getPlaceManager().getRequestParameter("customize")!=null ? AppClientFactory.getPlaceManager().getRequestParameter("customize") : null;
		String assign = AppClientFactory.getPlaceManager().getRequestParameter("Assign")!=null ? AppClientFactory.getPlaceManager().getRequestParameter("Assign") : null;
		if(customize!=null && YES.equals(customize)){
			Boolean loginFlag = AppClientFactory.isAnonymous();
			RenameCustomizePopUp successPopupVc = new RenameCustomizePopUp(collectionId, loginFlag, collectionTitle) {
				@Override
				public void closePoup() {
					Window.enableScrolling(true);
					this.hide();	
					isCustomizePopup = false;
				}
			};
			Window.scrollTo(0, 0);
		//	successPopupVc.setWidth("500px");
			successPopupVc.show();
			successPopupVc.center();

		}
		if(assign!=null && YES.equals(assign)){
			AssignPopupPlayerVc successPopupVc = new AssignPopupPlayerVc(collectionId) {
				@Override
				public void closePoup() {
					Window.enableScrolling(true);
			        this.hide();
			    	isAssignPopup=false;
				}
			};
			Window.scrollTo(0, 0);
			successPopupVc.setWidth("500px");
			successPopupVc.setHeight("635px");
			successPopupVc.show();
			successPopupVc.center();
			if (AppClientFactory.isAnonymous()){
			successPopupVc.setPopupPosition(successPopupVc.getAbsoluteLeft(), 30);
			}
			else
			{
			successPopupVc.setPopupPosition(successPopupVc.getAbsoluteLeft(), 30);
			}
		}
	}
	
	public class OnassignCollectionBtnMouseOver implements MouseOverHandler{
		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanel.clear();
			toolTipPopupPanel.setWidget(new GlobalToolTip(i18n.GL0676()));
			toolTipPopupPanel.setStyleName("");
			toolTipPopupPanel.setPopupPosition(assignCollectionBtn.getElement().getAbsoluteLeft()+8, assignCollectionBtn.getElement().getAbsoluteTop()+10);
			toolTipPopupPanel.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanel.show();
		}
	}
	
	public class OnassignCollectionBtnMouseOut implements MouseOutHandler{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanel.hide();
		}
	}
	
	public class OncustomizeCollectionBtnMouseOver implements MouseOverHandler{
		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanel.clear();
			toolTipPopupPanel.setWidget(new GlobalToolTip(i18n.GL0677()));
			toolTipPopupPanel.setStyleName("");
			toolTipPopupPanel.setPopupPosition(customizeCollectionBtn.getElement().getAbsoluteLeft()+18, customizeCollectionBtn.getElement().getAbsoluteTop()+10);
			toolTipPopupPanel.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanel.show();
		}
	}
	
	public class OncustomizeCollectionBtnMouseOut implements MouseOutHandler{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanel.hide();
		}
	}
	
	public class OnshareCollectionBtnMouseOver implements MouseOverHandler{
		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanel.clear();
			toolTipPopupPanel.setWidget(new GlobalToolTip(i18n.GL0678()));
			toolTipPopupPanel.setStyleName("");
			toolTipPopupPanel.setPopupPosition(shareCollectionBtn.getElement().getAbsoluteLeft()+5, shareCollectionBtn.getElement().getAbsoluteTop()+10);
			toolTipPopupPanel.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanel.show();
		}
	}
	
	public class OnshareCollectionBtnMouseOut implements MouseOutHandler{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanel.hide();
		}
	}
}
