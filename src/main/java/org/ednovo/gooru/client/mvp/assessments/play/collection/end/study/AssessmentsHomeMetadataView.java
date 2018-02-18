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
package org.ednovo.gooru.client.mvp.assessments.play.collection.end.study;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.mvp.assessments.play.collection.event.UpdateCollectionViewCountEvent;
import org.ednovo.gooru.client.mvp.assessments.play.collection.event.UpdatePreviewViewCountEvent;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.home.assign.AssignPopupPlayerVc;
import org.ednovo.gooru.client.mvp.gsearch.addResourcePopup.SearchAddResourceToCollectionPresenter;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.home.library.assign.AssignPopupVc;
import org.ednovo.gooru.client.uc.BrowserAgent;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.util.MixpanelUtil;
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
import com.google.gwt.event.shared.HandlerRegistration;
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

public class AssessmentsHomeMetadataView extends BaseViewWithHandlers<AssessmentsHomeMetadataUiHandlers> implements IsAssessmentsHomeMetadataView,ClientConstants{

	private static final String ASSESSMENT = "assessment";

	private static final String DEFULT_COLLECTION = "images/default-collection-image-160x120.png";

	private static final String DEFULT_ASSESSMENT = "images/default-assessment-image -160x120.png";

	@UiField Image collectionThumbnail;
	@UiField HTML collectionGoal;
	@UiField Button customizeCollectionBtn,shareCollectionBtn,studyButton;
	@UiField FlowPanel assessmentImageContainter;
	@UiField Label lblAssessmentTitle;

	private boolean isAssignPopup = false;

	private boolean isCustomizePopup = false;

	private PopupPanel toolTipPopupPanel=new PopupPanel();

	private HandlerRegistration studyButtonClickHandler;

	private String collectionTitle;

	private CollectionDo collectionDo=null;

	SearchAddResourceToCollectionPresenter remixPresenterWidget = AppClientFactory.getInjector().getRemixPresenterWidget();

	private static PreviewEndViewUiBinder uiBinder = GWT.create(PreviewEndViewUiBinder.class);

	interface PreviewEndViewUiBinder extends UiBinder<Widget, AssessmentsHomeMetadataView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public AssessmentsHomeMetadataView(){
		setWidget(uiBinder.createAndBindUi(this));

		customizeCollectionBtn.setText(i18n.GL2037());
		customizeCollectionBtn.getElement().setId("btnCustomizeCollectionBtn");
		customizeCollectionBtn.getElement().setAttribute("alt",i18n.GL2037());
		customizeCollectionBtn.getElement().setAttribute("title",i18n.GL2037());
		customizeCollectionBtn.setVisible(true);

		shareCollectionBtn.setText(i18n.GL0536());
		shareCollectionBtn.getElement().setId("btnShareCollectionBtn");
		shareCollectionBtn.getElement().setAttribute("alt",i18n.GL0536());
		shareCollectionBtn.getElement().setAttribute("title",i18n.GL0536());
		shareCollectionBtn.setVisible(true);

		collectionThumbnail.getElement().setId("imgCollectionThumbnail");
		collectionGoal.getElement().setId("htmlCollectionGoal");

		studyButton.setText(i18n.GL3451());
		studyButton.getElement().setId("btnAssessmentPlay");
		studyButton.getElement().setAttribute("alt",i18n.GL3451());
		studyButton.getElement().setAttribute("title",i18n.GL3451());

		customizeCollectionBtn.addMouseOverHandler(new OncustomizeCollectionBtnMouseOver());
		customizeCollectionBtn.addMouseOutHandler(new OncustomizeCollectionBtnMouseOut());
		shareCollectionBtn.addMouseOverHandler(new OnshareCollectionBtnMouseOver());
		shareCollectionBtn.addMouseOutHandler(new OnshareCollectionBtnMouseOut());
	}
	@UiHandler("collectionThumbnail")
	public void thumbnailErrorImage(ErrorEvent event){
		String collectionType=StringUtil.isEmpty(collectionDo.getCollectionType())?null:collectionDo.getCollectionType();
		StringUtil.setDefaultImages(collectionType, collectionThumbnail, "high");
	}

	@Override
	public void setCollectionMetadata(final CollectionDo collectionDo){

		this.collectionDo=collectionDo;
		lblAssessmentTitle.setText(collectionDo.getTitle());
		StringUtil.setAttributes(lblAssessmentTitle.getElement(), "lblAssessmentTitle", collectionDo.getTitle(), collectionDo.getTitle());

		String collectionType=StringUtil.isEmpty(collectionDo.getCollectionType())?null:collectionDo.getCollectionType();
		StringUtil.setDefaultImages(collectionType, collectionThumbnail, "high");

		setCollectionImage((collectionDo.getThumbnails()!=null&&collectionDo.getThumbnails().getUrl()!=null)?collectionDo.getThumbnails().getUrl():DEFULT_ASSESSMENT);
		setCollectionGoal(collectionDo.getDescription()!=null?collectionDo.getDescription():"");

		showPopupAfterGmailSignin();
		customizeCollectionBtn.getElement().setAttribute("collectionId", collectionDo.getGooruOid());
		shareCollectionBtn.getElement().setAttribute("collectionId", collectionDo.getGooruOid());
		collectionTitle = collectionDo.getTitle();

		if(studyButtonClickHandler!=null) {
			studyButtonClickHandler.removeHandler();
		}
		studyButtonClickHandler=studyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AppClientFactory.printInfoLogger("On Take Assessment : Click");
				Map<String,String> params = new LinkedHashMap<String,String>();
				params.put("id", collectionDo.getGooruOid());
				params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);

				MixpanelUtil.Preview_Player_Click_Preview();
				List<CollectionItemDo> collectionItems=(collectionDo!=null&&collectionDo.getCollectionItems()!=null)?collectionDo.getCollectionItems():null;
				if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.COLLECTION_PLAY) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.ASSESSMENT_PLAY)){
					AppClientFactory.fireEvent(new UpdateCollectionViewCountEvent());
				}else{
					try
					{
					AppClientFactory.fireEvent(new UpdatePreviewViewCountEvent());
					}
					catch(Exception ex)
					{
						AppClientFactory.printSevereLogger("AssessmentsHomeMetadataView : setCollectionMetadata : "+ex.getMessage());
					}
				}
				if(collectionItems!=null&&collectionItems.size()>0){
					CollectionItemDo collectionItemDo=collectionItems.get(0);
					if(collectionItemDo.getCollectionItemId() != null)
					{
						if(!StringUtil.isEmpty(collectionItemDo.getNarration())){
							params.put("rid", collectionItemDo.getCollectionItemId());
							params.put("tab", "narration");
							PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), params);
							AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
						}else{
							params.put("rid", collectionItemDo.getCollectionItemId());
							PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), params);
							AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
						}
					}
				}
				else{
					params.put("view", "end");
					PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), params);
					AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
				}
			}
		});
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
		resourceLink += AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters();
		return resourceLink;
	}
	public void setCollectionImage(String thumbnailUrl){
		collectionThumbnail.setUrl(thumbnailUrl);
	}
	public void setCollectionGoal(String collectionGoal){
		this.collectionGoal.setHTML("");
		if(!StringUtil.isEmpty(collectionGoal)){
			if(collectionGoal.length()>1001){
				collectionGoal =(collectionGoal.substring(0, 1000))+"...";
				this.collectionGoal.setHTML(collectionGoal);
			}
			else{
				this.collectionGoal.setHTML(collectionGoal);
			}
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
		final String collectionId = clickEvent.getRelativeElement().getAttribute("collectionId");
		final String collectionTitle= collectionDo.getTitle();
				if(!isCustomizePopup){
					isCustomizePopup=true;
					Window.scrollTo(0, 0);
					if(AppClientFactory.isAnonymous()){
						LoginPopupUc loginPopupUc=new LoginPopupUc() {
							@Override
							public	void onLoginSuccess(){
								Window.enableScrolling(false);

								getUiHandlers().setDefultVersionAndType();
								remixPresenterWidget.DisableMyCollectionsPanelData(false);
								remixPresenterWidget.getLoadingImage();
								remixPresenterWidget.getUserShelfCollectionsData(collectionId, "coursebuilder",collectionTitle);
								remixPresenterWidget.getView().getAppPopUp().show();
								isCustomizePopup = false;
								remixPresenterWidget.getView().getAppPopUp().center();
								remixPresenterWidget.getView().getAppPopUp().setGlassEnabled(true);
								remixPresenterWidget.getView().getAppPopUp().setGlassStyleName("setGlassPanelZIndex");
							}
						};
						loginPopupUc.show();
						loginPopupUc.setGlassEnabled(true);
						loginPopupUc.setGlassStyleName("setGlassPanelZIndex");
					}else{

						getUiHandlers().setDefultVersionAndType();
						remixPresenterWidget.DisableMyCollectionsPanelData(false);
						remixPresenterWidget.getLoadingImage();
						remixPresenterWidget.getUserShelfCollectionsData(collectionId, "coursebuilder",collectionTitle);
						remixPresenterWidget.getView().getAppPopUp().show();
						isCustomizePopup = false;
						remixPresenterWidget.getView().getAppPopUp().center();
						remixPresenterWidget.getView().getAppPopUp().setGlassEnabled(true);
						remixPresenterWidget.getView().getAppPopUp().setGlassStyleName("setGlassPanelZIndex");
					}

			//	successPopupVc.setWidth("500px");
				/*if (!BrowserAgent.isDevice() && AppClientFactory.isAnonymous()){
					successPopupVc.setWidth("500px");
					successPopupVc.setHeight("515px");
				}else if(!BrowserAgent.isDevice() && !AppClientFactory.isAnonymous()){
					successPopupVc.setWidth("500px");
					successPopupVc.setHeight("336px");
				}
				successPopupVc.show();
				successPopupVc.center();*/
				Map<String,String> params = StringUtil.splitQuery(Window.Location.getHref());

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
	@UiHandler("shareCollectionBtn")
	public void onshareCollectionBtnClicked(ClickEvent clickEvent) {
		getUiHandlers().triggerCollectionShareDataEvent(null,PlayerDataLogEvents.COLLECTION,"gooru",false);
		String collectionId = collectionDo.getGooruOid();
			AssignPopupVc successPopupVc = new AssignPopupVc(collectionId, collectionDo.getTitle(), collectionDo.getGoals()) {
					@Override
					public void closePoup() {
						Window.enableScrolling(true);
				        this.hide();
					}
				};

				Window.scrollTo(0, 0);
				int clientHeight=Window.getClientHeight();
				//successPopupVc.setWidth("500px");
				//successPopupVc.setHeight("658px");
				if(clientHeight>625){
					clientHeight=625;
					//successPopupVc.getAssignContainer().getElement().setAttribute("style", "max-height:"+clientHeight+"px;overflow-x:hidden;overflow-y:scroll");
				}/*else{
					successPopupVc.getAssignContainer().getElement().setAttribute("style", "max-height:"+clientHeight+"px;overflow-x:hidden;overflow-y:scroll");
				}*/
				successPopupVc.show();
				int left = (Window.getClientWidth() - 500) >> 1;
			    int top = (Window.getClientHeight() - clientHeight) >> 1;
			  //  successPopupVc.setHeight("658px");
			   // successPopupVc.setPopupPosition(Math.max(Window.getScrollLeft() + left, 0), Math.max(Window.getScrollTop()+5, 0));


				/*if(AppClientFactory.isAnonymous()){
					successPopupVc.setPopupPosition(successPopupVc.getAbsoluteLeft(), -30);

				}*/
				if (!BrowserAgent.isDevice() && AppClientFactory.isAnonymous()){
					/*successPopupVc.setWidth("550px");
					successPopupVc.setHeight("625px");
					successPopupVc.center();*/
					successPopupVc.setPopupPosition(0, (Window.getClientHeight()-625)/2);
				}else if(!BrowserAgent.isDevice() && !AppClientFactory.isAnonymous()){
					/*successPopupVc.setWidth("550px");
					successPopupVc.setHeight("502px");
					successPopupVc.center();*/
					successPopupVc.setPopupPosition(0, (Window.getClientHeight()-527)/2);
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
		String assign = AppClientFactory.getPlaceManager().getRequestParameter("assign")!=null ? AppClientFactory.getPlaceManager().getRequestParameter("assign") : null;
		String emailId = AppClientFactory.getPlaceManager().getRequestParameter("emailId")!=null ? AppClientFactory.getPlaceManager().getRequestParameter("emailId") : null;
		String collectionTitle=collectionDo.getTitle();
		if(customize!=null && YES.equals(customize) && emailId!=null){

			getUiHandlers().setDefultVersionAndType();
			remixPresenterWidget.DisableMyCollectionsPanelData(false);
			remixPresenterWidget.getLoadingImage();
			remixPresenterWidget.getUserShelfCollectionsData(collectionId, "coursebuilder",collectionTitle);
			remixPresenterWidget.getView().getAppPopUp().show();
			isCustomizePopup = false;
			remixPresenterWidget.getView().getAppPopUp().center();
			remixPresenterWidget.getView().getAppPopUp().setGlassEnabled(true);
			remixPresenterWidget.getView().getAppPopUp().setGlassStyleName("setGlassPanelZIndex");
		}
		if(assign!=null && YES.equals(assign) && emailId!=null){
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
			}else{
			successPopupVc.setPopupPosition(successPopupVc.getAbsoluteLeft(), 30);
			}
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
