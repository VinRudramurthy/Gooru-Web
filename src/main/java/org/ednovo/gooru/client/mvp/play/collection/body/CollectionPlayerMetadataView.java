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
package org.ednovo.gooru.client.mvp.play.collection.body;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;
import org.ednovo.gooru.application.shared.model.player.CommentsDo;
import org.ednovo.gooru.application.shared.model.player.CommentsListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.htmltags.SectionTag;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.play.collection.preview.metadata.comment.CommentWidgetChildView;
import org.ednovo.gooru.client.mvp.search.SearchResultWrapperCBundle;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class CollectionPlayerMetadataView extends BaseViewWithHandlers<CollectionPlayerMetadataUiHandlers> implements IsCollectionPlayerMetadataView{

	@UiField
	static FlowPanel studyMainContianer;

	@UiField
	FlowPanel leftPanelContainer;

	@UiField VerticalPanel commentsContainer;
	@UiField TextArea commentField;
	@UiField Button postCommentBtn,postCommentCancel;

	@UiField MetadataWidget rightPanelMetadata;

	@UiField Label commentCount,seeMoreButton,noCommentsLbl,toCommentText,orText,loginMessagingText,characterLimit;
	@UiField Label successPostMsg;
	@UiField Image userPhoto;
	@UiField HTMLPanel addComment,loginMessaging,
						switchContainer;
	@UiField Anchor loginUrl, signupUrl;

	@UiField SectionTag commentssection;

	@UiField CollectionPlayerStyleBundle playerStyle;

	@UiField Label lblCharLimitComments;
	@UiField SimpleCheckBox changeAssignmentStatusButton;
	@UiField InlineLabel requiredLabel,optionalLabel;

	private CollectionDo collectionDo=null;


	private static final String COLLECTION_COMMENTS="COLLECTION_COMMENTS";

	private static final String INITIAL_COMMENT_LIMIT = "10";

	private static final String CREATE = "CREATE";

	private static final String DELETE = "DELETE";

	private static final String EDIT = "EDIT";

	private static final String PAGINATION = "page";

	private static final String PRIMARY_STYLE = "primary";

	private static final String SECONDARY_STYLE = "secondary";

	private static final String DISABLED_STYLE = "disabled";

	private static final int INCREMENT_BY_ONE = 1;

	private static final int DECREMENT_BY_ONE = -1;

	private static final String EDUCATOR_DEFAULT_IMG = "../images/settings/setting-user-image.png";
	private Anchor usernameAnchor;

	private int totalCommentCount = 0;

	private int totalHitCount = 0;

	private int paginationCount = 0;

	private boolean isHavingBadWords;

	private MetadataWidget menuMetadataWidget=null;

	@Inject
	private ResourceServiceAsync resourceService;


	private static CollectionPlayerMetadataViewUiBinder uiBinder = GWT.create(CollectionPlayerMetadataViewUiBinder.class);

	interface CollectionPlayerMetadataViewUiBinder extends UiBinder<Widget, CollectionPlayerMetadataView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public CollectionPlayerMetadataView(){
		setWidget(uiBinder.createAndBindUi(this));
		setLabelAndIds();
		rightPanelMetadata.teacherContainer.setVisible(false);
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		SearchResultWrapperCBundle.INSTANCE.css().ensureInjected();
		loginMessagingText.setText(i18n.GL0568());
		loginMessagingText.getElement().setId("lblLoginMessagingText");
		loginMessagingText.getElement().setAttribute("alt",i18n.GL0568());
		loginMessagingText.getElement().setAttribute("title",i18n.GL0568());

		orText.setText(i18n.GL0209());
		orText.getElement().setId("lblOrText");
		orText.getElement().setAttribute("alt",i18n.GL0209());
		orText.getElement().setAttribute("title",i18n.GL0209());

		toCommentText.setText(" "+i18n.GL0569());
		toCommentText.getElement().setId("lblToCommentText");
		toCommentText.getElement().setAttribute("alt",i18n.GL0569());
		toCommentText.getElement().setAttribute("title",i18n.GL0569());

		loginMessagingText.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().toCommentTextPreviewPlayer());
		orText.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().toCommentTextPreviewPlayer());
		toCommentText.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().toCommentTextPreviewPlayer());
		loginUrl.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().toCommentTextPreviewPlayer());
		signupUrl.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().toCommentTextPreviewPlayer());
		userPhoto.getElement().setId("imgUserPhoto");
		commentField.addClickHandler(new OnCommentsFieldClicked());
		commentField.addKeyUpHandler(new ValidateConfirmText());
		commentField.addBlurHandler(new OnCommentsFieldBlur());

		String value = StringUtil.generateMessage(i18n.GL2103(), "500");
		lblCharLimitComments.setText(value);
		StringUtil.setAttributes(lblCharLimitComments.getElement(), "lblCharLimitComments", value, value);

		seeMoreButton.setVisible(false);
		Boolean isIpad = !!Navigator.getUserAgent().matches("(.*)iPad(.*)");
		Boolean isAndriod = !!Navigator.getUserAgent().matches("(.*)Android(.*)");
		if(isIpad && !StringUtil.IPAD_MESSAGE_Close_Click) {
		  studyMainContianer.getElement().setAttribute("style", "margin-top:0px;");
		}else if(isAndriod && !StringUtil.IPAD_MESSAGE_Close_Click){
		  studyMainContianer.getElement().setAttribute("style", "margin-top:0px;");
		}
		Window.addResizeHandler(new ResizeLogicEvent());
	}

	@Override
	public void setCollectionMetadata(CollectionDo collectionDo) {
		this.collectionDo = collectionDo;

		rightPanelMetadata.setCollectionMetadata(collectionDo);
		menuMetadataWidget=new MetadataWidget();
		menuMetadataWidget.setStyleName("col-md-12 col-sm-12");
		menuMetadataWidget.addStyleName("player-rightPanel");
		menuMetadataWidget.setCollectionMetadata(collectionDo);
		getUiHandlers().getMenuContainer().clear();
		getUiHandlers().getMenuContainer().add(menuMetadataWidget);
		getUiHandlers().getMenuContainer().getParent().setStyleName("player");
		switchContainer.setVisible(true);
		commentssection.setVisible(true);
		commentssection.getElement().getStyle().setOpacity(1);
			if(collectionDo!=null && collectionDo.getPermissions() != null){
				if (StringUtil.toString(collectionDo.getPermissions()).contains(ClientConstants.EDIT) || collectionDo.isIsCollaborator()){
					switchContainer.setVisible(true);
					if(collectionDo.getSettings() != null)
					{
						if(collectionDo.getSettings().getComment() != null)
						{
								if(ClientConstants.TURNON.equalsIgnoreCase(collectionDo.getSettings().getComment()))
								{
									commentField.setEnabled(true);
									commentssection.getElement().getStyle().setOpacity(1);
									changeAssignmentStatusButton.setValue(true);
									postCommentBtn.setEnabled(true);
									postCommentBtn.setStyleName(PRIMARY_STYLE);
								}
								else
								{
									commentField.setEnabled(false);
									postCommentBtn.setEnabled(false);
									postCommentBtn.removeStyleName(PRIMARY_STYLE);
									postCommentBtn.addStyleName(SECONDARY_STYLE);
									postCommentBtn.addStyleName(DISABLED_STYLE);
									commentssection.getElement().getStyle().setOpacity(0.5);
									changeAssignmentStatusButton.setValue(false);
								}
						}else{
							commentField.setEnabled(true);
							postCommentBtn.removeStyleName(SECONDARY_STYLE);
							postCommentBtn.removeStyleName(DISABLED_STYLE);
							postCommentBtn.addStyleName(PRIMARY_STYLE);
							commentssection.getElement().getStyle().setOpacity(1);
							changeAssignmentStatusButton.setValue(true);
						}
					}else{
						commentField.setEnabled(true);
						commentssection.getElement().getStyle().setOpacity(1);
						changeAssignmentStatusButton.setValue(true);
					}
				}else{
					if(collectionDo.getSettings() != null)
					{
							if(ClientConstants.TURNOFF.equalsIgnoreCase(collectionDo.getSettings().getComment()))
							{
								commentssection.setVisible(false);
							}else{
								commentssection.setVisible(true);
							}
					}else{
						commentssection.setVisible(true);
					}
					switchContainer.setVisible(false);
				}
			}else{
				switchContainer.setVisible(false);
			}

		if(!StringUtil.isEmpty(collectionDo.getViews())){
			setViewCount(collectionDo.getViews());
		}

		setLeftPanelHeight();
	}
	private void setLeftPanelHeight(){
		 Scheduler.get().scheduleDeferred(new ScheduledCommand(){
			@Override
			public void execute() {
				int height=rightPanelMetadata.getElement().getOffsetHeight();
				if(height>650){
					leftPanelContainer.getElement().setAttribute("style", "min-height:"+height+"px;");
				}
			}
	      });
	}
	@Override
    public void setInSlot(Object slot, Widget content) {
		if(slot==CollectionPlayerMetadataPresenter.METADATA_PRESENTER_SLOT){
			if(content!=null){
				if(leftPanelContainer.getWidgetCount()>1){
					leftPanelContainer.remove(0);
				}
				leftPanelContainer.insert(content,0);
			}
		}
	}
	public void setLabelAndIds() {
		studyMainContianer.getElement().getStyle().setPosition(Position.RELATIVE);
		studyMainContianer.getElement().setId("fpnlStudyMainContianer");
		commentCount.getElement().setId("lblCommentCount");
		commentsContainer.getElement().setId("vpnlCommentsContainer");
		seeMoreButton.setText(i18n.GL0508());
		seeMoreButton.getElement().setId("lblSeeMoreButton");
		seeMoreButton.getElement().setAttribute("alt",i18n.GL0508());
		seeMoreButton.getElement().setAttribute("title",i18n.GL0508());
		seeMoreButton.getElement().setId("lblSeeMoreButton");
		noCommentsLbl.getElement().setId("lblNoCommentsLbl");
		addComment.getElement().setId("pnlAddComment");
		loginUrl.setText(i18n.GL0187().toLowerCase());
		loginUrl.getElement().setId("lnkLoginUrl");
		loginUrl.getElement().setAttribute("alt",i18n.GL0187().toLowerCase());
		loginUrl.getElement().setAttribute("title",i18n.GL0187().toLowerCase());

		signupUrl.setText(i18n.GL0186().toLowerCase());
		signupUrl.getElement().setId("lnkSignupUrl");
		signupUrl.getElement().setAttribute("alt",i18n.GL0186().toLowerCase());
		signupUrl.getElement().setAttribute("title",i18n.GL0186().toLowerCase());

		successPostMsg.setText(i18n.GL0570());
		successPostMsg.getElement().setId("lblSuccessPostMsg");
		successPostMsg.getElement().setAttribute("alt",i18n.GL0570());
		successPostMsg.getElement().setAttribute("title",i18n.GL0570());

		postCommentBtn.setText(i18n.GL0571());
		postCommentBtn.getElement().setId("btnPostCommentBtn");
		postCommentBtn.getElement().setAttribute("alt",i18n.GL0571());
		postCommentBtn.getElement().setAttribute("title",i18n.GL0571());

		postCommentCancel.setText(i18n.GL0142());
		postCommentCancel.getElement().setId("btnPostCommentCancel");
		postCommentCancel.getElement().setAttribute("alt",i18n.GL0142());
		postCommentCancel.getElement().setAttribute("title",i18n.GL0142());

		characterLimit.setText(i18n.GL0143());
		characterLimit.getElement().setId("lblCharacterLimit");
		characterLimit.getElement().setAttribute("alt",i18n.GL0143());
		characterLimit.getElement().setAttribute("title",i18n.GL0143());
		postCommentBtn.setEnabled(false);
		commentField.getElement().setId("tatCommentField");
		StringUtil.setAttributes(commentField, true);
	}
	public void setCourseTitle(String title){
	}
	public void setLikesCount(int likesCount){

	}
	public void resetMetadataFields(){
		rightPanelMetadata.resetMetadataFields();
		if(menuMetadataWidget!=null){
			menuMetadataWidget.resetMetadataFields();
		}
		commentField.setText("");
		commentField.getElement().setAttribute("alt","");
		commentField.getElement().setAttribute("title","");
		commentField.setVisible(false);
		loginMessaging.setVisible(true);
		modifyEditControls(false);
		this.collectionDo=null;
	}

	@Override
	public void setUserProfileName(String gooruUid) {
		rightPanelMetadata.setUserProfileName(gooruUid);
		if(menuMetadataWidget!=null){
			menuMetadataWidget.setUserProfileName(gooruUid);
		}
	}

	public void displayAuthorDetails(boolean isDisplayDetails) {
		rightPanelMetadata.displayAuthorDetails(isDisplayDetails);
		if(menuMetadataWidget!=null){
			menuMetadataWidget.displayAuthorDetails(isDisplayDetails);
		}
	}
	@Override
	public void setRelatedConceptsContent(ArrayList<ConceptDo> conceptDoList,
			String coursePage, String subject, String lessonId,
			String libraryName) {
	}

	@Override
	public void isConceptsContainerVisible(boolean isVisible) {

	}
	public void setDataInsightsUrl(){
		String page=AppClientFactory.getPlaceManager().getRequestParameter("page", null);
		if(AppClientFactory.isAnonymous()){
		}else if(page!=null&&page.equals("teach")){
		}else{
		}
	}

	public void setClasspageInsightsUrl(String classpageId, String sessionId){
		if(sessionId==null) {
			sessionId = "";
		}
		String page=AppClientFactory.getPlaceManager().getRequestParameter("page", null);
	}

	public void setDataInsightsSummaryUrl(String sessionId){
		String page=AppClientFactory.getPlaceManager().getRequestParameter("page", null);
	}

	public class DataInsightsIframe extends Composite{
		private Frame dataInsightsFrame=null;
		private String insightsUrl="";
		public DataInsightsIframe(String insightsUrl){
			initWidget(dataInsightsFrame=new Frame());
			this.insightsUrl=insightsUrl;
			dataInsightsFrame.setStyleName(playerStyle.insightsFrameContent());
			dataInsightsFrame.getElement().setAttribute("id", "student-dashboard");
		}
		public void onLoad(){
			dataInsightsFrame.setUrl(insightsUrl);
		}
	}

	public void clearDashBoardIframe(){
	}


	/**
	 * @function setCommentsText
	 *
	 * @created_date : 03-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param commentIncrement
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private void setCommentsText(int commentIncrement) {
		totalCommentCount+=commentIncrement;
		commentCount.setText(totalCommentCount+" "+i18n.GL1432());
		commentCount.getElement().setAttribute("alt",totalCommentCount+" "+i18n.GL1432());
		commentCount.getElement().setAttribute("title",totalCommentCount+" "+i18n.GL1432());
	}

	/**
	 * @function setCommentsWidget
	 *
	 * @created_date : 02-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	*/
	@Override
	public void setCommentsWidget(CommentsDo commentsDo, String action) {
		if(CREATE.equalsIgnoreCase(action)){
			setCommentsText(INCREMENT_BY_ONE);
		}
		commentsContainer.add(new CommentWidgetChildView(commentsDo,collectionDo));
		showSeeMoreButton();
	}

	/**
	 * @function hideorShowEditButtonForAllCommentWidgets
	 *
	 * @created_date : 03-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param commentUid
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private void hideorShowEditButtonForAllCommentWidgets(Boolean boolFlag) {
		Iterator<Widget> widgets = commentsContainer.iterator();
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			if (widget instanceof CommentWidgetChildView) {
				CommentWidgetChildView commentWidgetChildView = ((CommentWidgetChildView) widget);
				if(boolFlag)
				{
					commentWidgetChildView.getEditButton().setVisible(true);
				}
				else
				{
					commentWidgetChildView.getEditButton().setVisible(false);
				}

			}
		}
	}

	/**
	 * @function deleteComment
	 *
	 * @created_date : 03-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param commentUid
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private void deleteComment(final String commentUid) {
		Iterator<Widget> widgets = commentsContainer.iterator();
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			if (widget instanceof CommentWidgetChildView && ((CommentWidgetChildView) widget).getCommentUid().equals(commentUid)) {
				CommentWidgetChildView commentWidgetChildView = ((CommentWidgetChildView) widget);
				final String commentText=commentWidgetChildView.getCommentField().getText();
				int index = commentsContainer.getWidgetIndex(commentWidgetChildView);
				commentsContainer.remove(index);
				final HTMLPanel deletePanel = new HTMLPanel(i18n.GL0555());
				deletePanel.setStyleName(playerStyle.deleteMsg());
				commentsContainer.insert(deletePanel, index);
				new FadeInAndOut(deletePanel.getElement(), 1000);
				Timer timer = new Timer()
		        {
		            @Override
		            public void run()
		            {
						int deleteIndex = commentsContainer.getWidgetIndex(deletePanel);
						commentsContainer.remove(deleteIndex);
						getUiHandlers().deleteCommentFromCollection(collectionDo.getGooruOid(),commentUid, commentsContainer.getWidgetCount()+"", 1+"",commentText);
		            }
		        };
		        timer.schedule(1000);
		        totalHitCount+=DECREMENT_BY_ONE;
		        setCommentsText(DECREMENT_BY_ONE);
			}
		}
	}

	/**
	 * @function editComment
	 *
	 * @created_date : 04-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param commentUid
	 *
	 * @return : void
	 *
	 */
	private void editComment(String commentUid) {
		Iterator<Widget> widgets = commentsContainer.iterator();
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			if (widget instanceof CommentWidgetChildView) {
				CommentWidgetChildView commentWidgetChildView = ((CommentWidgetChildView) widget);
				if(!commentWidgetChildView.getCommentUid().equals(commentUid)&&commentWidgetChildView.getCommentField().isVisible()) {
					commentWidgetChildView.enableEditFunction(false);
				}
			}
		}
	}
	public void clearCommentContainer(boolean isClear) {
		if(isClear) {
			commentsContainer.clear();
			totalCommentCount = 0;
			totalHitCount = 0;
			paginationCount = 0;
		}
	}
	/**
	 * @function showSeeMoreButton
	 *
	 * @created_date : 06-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 */
	public void showSeeMoreButton() {
		if(totalHitCount>Integer.parseInt(INITIAL_COMMENT_LIMIT) && (commentsContainer.getWidgetCount()<totalHitCount)) {
			seeMoreButton.setVisible(true);
		} else {
			seeMoreButton.setVisible(false);
		}
	}

	/**
	 * @function clickOnSeeMoreButton
	 *
	 * @created_date : 06-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 */
	@UiHandler("seeMoreButton")
	public void clickOnSeeMoreButton(ClickEvent event) {
		getUiHandlers().getPaginationResults(collectionDo.getGooruOid(), paginationCount+"", INITIAL_COMMENT_LIMIT);
	}
	/**
	 * @function setCommentsData
	 *
	 * @created_date : 02-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>m
	*/
	@Override
	public void setCommentsData(CommentsListDo commentDoList,CollectionDo collectionDo, boolean isToClearCommentContainer) {
		clearCommentContainer(isToClearCommentContainer);
		if(totalHitCount == 0) {
			totalHitCount = commentDoList.getTotalHitCount();
			setCommentsText(commentDoList.getTotalHitCount());
		}
		if(commentDoList.getSearchResults() != null){
			int size = commentDoList.getSearchResults().size();
			paginationCount=paginationCount+size;
			if(size>0) {
				for(int i=0;i<size;i++) {
					setCommentsWidget(commentDoList.getSearchResults().get(i),PAGINATION);
				}
			}else {
				totalCommentCount=0;
				setCommentsText(totalCommentCount);
				showSeeMoreButton();
			}
		}
		if(totalHitCount==0 && paginationCount==0) {
			noCommentsLbl.setVisible(true);
		} else {
			noCommentsLbl.setVisible(false);
		}
	}

	/**
	 * @function updateCommentChildView
	 *
	 * @created_date : 03-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param commentUid, @param action
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@Override
	public void updateCommentChildView(String commentUid, String action) {
		if(!commentUid.isEmpty() && DELETE.equalsIgnoreCase(action)) {
			deleteComment(commentUid);
			addComment.setVisible(true);
		}else if(!commentUid.isEmpty() && EDIT.equalsIgnoreCase(action)){
			addComment.setVisible(false);
			lblCharLimitComments.setVisible(false);
			editComment(commentUid);
		} else if(commentUid.isEmpty() && EDIT.equals(action)) {
			lblCharLimitComments.setVisible(true);
			addComment.setVisible(true);
		}
	}


	/**
	 * @function clickOnLoginUrl
	 *
	 * @created_date : 03-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param event
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("loginUrl")
	public void clickOnLoginUrl(ClickEvent event) {
		LoginPopupUc popup = new  LoginPopupUc() {
			@Override
			public void onLoginSuccess() {

			}
		};
		popup.setWidgetMode(COLLECTION_COMMENTS);
		popup.getElement().getStyle().setZIndex(100000);
		popup.setGlassEnabled(true);
		popup.center();
		popup.show();
	}

	/**
	 * @function clickOnSignupUrl
	 *
	 * @created_date : 03-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param event
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("signupUrl")
	public void clickOnSignupUrl(ClickEvent event) {
		Map<String, String> params = StringUtil.splitQuery(Window.Location.getHref());
		params.put("callback", "signup");
		params.put("type", "1");
		PlaceRequest placeRequest = new PlaceRequest(AppClientFactory.getCurrentPlaceToken());
		if (params != null) {
			for (String key : params.keySet()) {
				placeRequest = placeRequest.with(key, params.get(key));
			}
		}
		AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
	}

	/**
	 * @function setPlayerLoginStatus
	 *
	 * @created_date : 02-Jan-2014
	 *
	 * @description
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	*/
	@Override
	public void setPlayerLoginStatus(boolean isLoggedIn) {
		postCommentCancel.setVisible(false);
		successPostMsg.setVisible(false);
		characterLimit.setVisible(false);
		if(isLoggedIn) {
			userPhoto.setVisible(true);
			commentField.setVisible(true);
			loginMessaging.setVisible(false);
			String commentorImage = AppClientFactory.loggedInUser.getUserUid();
			userPhoto.setUrl(AppClientFactory.loggedInUser.getSettings().getProfileImageUrl()+commentorImage+".png");
			userPhoto.addErrorHandler(new ErrorHandler() {
				@Override
				public void onError(ErrorEvent event) {
					userPhoto.setUrl(EDUCATOR_DEFAULT_IMG);
				}
			});
			postCommentBtn.setEnabled(true);
		} else {
			userPhoto.setVisible(false);
			commentField.setVisible(false);
			loginMessaging.setVisible(true);
			postCommentBtn.setEnabled(false);
		}
	}

	/**
	 *
	 * @function clickOnPostCommentBtn
	 *
	 * @created_date : 03-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("postCommentBtn")
	public void clickOnPostCommentBtn(ClickEvent event) {

		if (commentField.getText().trim().length() > 0){
			if(postCommentBtn.getStyleName().contains(PRIMARY_STYLE)) {
				//check for bad words first.
				Map<String, String> parms = new HashMap<String, String>();
				parms.put("text", removeHtmlTags(commentField.getText()));
				postCommentBtn.setEnabled(false);
				AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean value) {
						isHavingBadWords = value;
						postCommentBtn.setEnabled(true);
						if (value){
							commentField.getElement().getStyle().setBorderColor("orange");
							characterLimit.setText(i18n.GL0554());
							characterLimit.getElement().setAttribute("alt",i18n.GL0554());
							characterLimit.getElement().setAttribute("title",i18n.GL0554());
							characterLimit.setVisible(true);
						}else{
							commentField.getElement().getStyle().clearBackgroundColor();
							commentField.getElement().getStyle().setBorderColor("#ccc");
							characterLimit.setVisible(false);

							getUiHandlers().createCommentForCollection(collectionDo.getGooruOid(), removeHtmlTags(commentField.getText()));

							commentField.setText("");
							commentField.getElement().setAttribute("alt","");
							commentField.getElement().setAttribute("title","");
							commentField.setVisible(false);
							modifyEditControls(false);
							displaySuccessMsg(true);
						}
					}
				});
			}
		}else{
			commentField.setText("");
			commentField.getElement().setAttribute("alt","");
			commentField.getElement().setAttribute("title","");
			characterLimit.setVisible(false);
			modifyEditControls(false);
		}
	}

	@UiHandler("changeAssignmentStatusButton")
	public void clickOnStatusChangeBtn(ClickEvent event) {
		if (changeAssignmentStatusButton.getValue()){
		getUiHandlers().updateCommentsStatus("turn-on");
		}
		else{
		getUiHandlers().updateCommentsStatus("turn-off");
		}
	}

	public void changeCommentsButton(CollectionDo result) {
					if(result.getSettings()!=null)
					{
						if(result.getSettings().getComment()!=null)
						{
							if(ClientConstants.TURNON.equalsIgnoreCase(result.getSettings().getComment()))
							{
								hideorShowEditButtonForAllCommentWidgets(true);
								requiredLabel.removeStyleName(playerStyle.mutedText());
								optionalLabel.removeStyleName(playerStyle.mutedText());
								commentField.setEnabled(true);
								postCommentBtn.setEnabled(true);
								postCommentBtn.setStyleName(PRIMARY_STYLE);
								commentssection.getElement().getStyle().setOpacity(1);
								changeAssignmentStatusButton.setValue(true);
							}else{
								hideorShowEditButtonForAllCommentWidgets(false);
								requiredLabel.setStyleName(playerStyle.mutedText());
								optionalLabel.setStyleName(playerStyle.mutedText());
								commentField.setEnabled(false);
								postCommentBtn.setEnabled(false);
								postCommentBtn.removeStyleName(PRIMARY_STYLE);
								postCommentBtn.addStyleName(SECONDARY_STYLE);
								postCommentBtn.addStyleName(DISABLED_STYLE);
								commentssection.getElement().getStyle().setOpacity(0.5);
								changeAssignmentStatusButton.setValue(false);
							}

						}else{
							hideorShowEditButtonForAllCommentWidgets(true);
							requiredLabel.setStyleName(playerStyle.mutedText());
							optionalLabel.setStyleName(playerStyle.mutedText());
						}
					}
					else
					{
						hideorShowEditButtonForAllCommentWidgets(true);
						requiredLabel.setStyleName(playerStyle.mutedText());
						optionalLabel.setStyleName(playerStyle.mutedText());
					}
	}
	/**
	 *
	 * @function clickOnPostCommentCancel
	 *
	 * @created_date : 03-Jan-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param event
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("postCommentCancel")
	public void clickOnPostCommentCancel(ClickEvent event) {
		commentField.setText("");
		commentField.getElement().getStyle().clearBackgroundColor();
		commentField.getElement().getStyle().setBorderColor("#ccc");
		characterLimit.setVisible(false);
		modifyEditControls(false);
	}

	/**
	 *
	 * @fileName : PreviewPlayerMetadataView.java
	 *
	 * @function : OnCommentsFieldBlur
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: Jan 6, 2014
	 *
	 * @Author Gooru Team
	 *
	 * @Reviewer: Gooru Team
	 */
	private class OnCommentsFieldBlur implements BlurHandler{
		@Override
		public void onBlur(BlurEvent event) {

			if (commentField.getText().length() > 0){
				Map<String, String> parms = new HashMap<String, String>();
				parms.put("text", commentField.getText());
				AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean value) {
						isHavingBadWords = value;
						if (value){
							commentField.getElement().getStyle().setBorderColor(ClientConstants.ORANGE_COLOR);
							characterLimit.setText(i18n.GL0554());
							characterLimit.getElement().setAttribute("alt",i18n.GL0554());
							characterLimit.getElement().setAttribute("title",i18n.GL0554());
							characterLimit.setVisible(true);
						}else{
							commentField.getElement().getStyle().clearBackgroundColor();
							commentField.getElement().getStyle().setBorderColor("#ccc");
							characterLimit.setVisible(false);
						}
					}
				});
			}
		}
	}

	/**
	 * @fileName : PreviewPlayerMetadataView.java
	 *
	 * @description : OnCommentsFieldClicked sub class
	 *
	 * @version : 1.0
	 *
	 * @date: 03-Jan-2014
	 *
	 * @Author: Gooru Team
	 *
	 * @Reviewer: Gooru Team
	 */
	private class OnCommentsFieldClicked implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			commentField.getElement().getStyle().clearBackgroundColor();
			commentField.getElement().getStyle().setBorderColor("#ccc");
			if(commentField.getText().trim().length()==0){
				modifyEditControls(false);
			}else{
				if (event.getSource() == commentField && !postCommentCancel.isVisible()) {
					modifyEditControls(true);
				}
			}
		}
	}

	/**
	 * @fileName : PreviewPlayerMetadataView.java
	 *
	 * @description : OnCommentsFieldValidated Sub class
	 *
	 * @version : 1.0
	 *
	 * @date: 03-Jan-2014
	 *
	 * @Author: Gooru Team
	 *
	 * @Reviewer: Gooru Team
	 */
	private class ValidateConfirmText implements KeyUpHandler {
		@Override
		public void onKeyUp(KeyUpEvent event) {
			if(commentField.getText()!=null&&commentField.getText().length()>500) {
				commentField.setText(commentField.getText().substring(0,500));
				commentField.getElement().setAttribute("alt",commentField.getText().substring(0,500));
				commentField.getElement().setAttribute("title",commentField.getText().substring(0,500));
				characterLimit.setText(i18n.GL0143());
				characterLimit.getElement().setAttribute("alt",i18n.GL0143());
				characterLimit.getElement().setAttribute("title",i18n.GL0143());
				characterLimit.setVisible(true);
			} else {
				if(commentField.getText().trim().length()==0){
					modifyEditControls(false);
				}else{
					modifyEditControls(true);
				}
				characterLimit.setVisible(false);
			}
		}
	}
	/**
	 * @function modifyEditControls
	 *
	 * @created_date : 03-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param isCommentsFieldClicked
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private void modifyEditControls(boolean isCommentsFieldClicked) {
		postCommentCancel.setVisible(isCommentsFieldClicked);
		if(isCommentsFieldClicked) {
			postCommentBtn.removeStyleName(SECONDARY_STYLE);
			postCommentBtn.removeStyleName(DISABLED_STYLE);
			postCommentBtn.addStyleName(PRIMARY_STYLE);
		} else {
			postCommentBtn.removeStyleName(PRIMARY_STYLE);
			postCommentBtn.addStyleName(SECONDARY_STYLE);
			postCommentBtn.addStyleName(DISABLED_STYLE);
		}
	}

	/**
	 * @function displaySuccessMsg
	 *
	 * @created_date : 03-Jan-2014
	 *
	 * @description
	 *
	 * @parm(s) : @param isVisible
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@Override
	public void displaySuccessMsg(boolean isVisible) {
		commentField.setVisible(!isVisible);
		successPostMsg.setVisible(isVisible);
	}



	public static void onClosingAndriodorIpaddiv()
	{
		studyMainContianer.getElement().setAttribute("style", "margin-top:50px;");
	}

	/**
	 * @function removeHtmlTags
	 *
	 * @created_date : 16-Oct-2014
	 *
	 * @description this method is used to remove the html tags in comment input box
	 *
	 * @parm(s) : @param String
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private String removeHtmlTags(String html){
		html = html.replaceAll("(<\\w+)[^>]*(>)", "$1$2");
		html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "").replaceAll("</a>", "").replaceAll("<a>", "");
        return html;
	}

	public class ResizeLogicEvent implements ResizeHandler{
		@Override
		public void onResize(ResizeEvent event) {
			placeCollectionMetadataContainer();
		}
	}
	public void placeCollectionMetadataContainer(){
		int clientWidth=Window.getClientWidth();
		if(clientWidth>991){
			FlowPanel menuContainer=getUiHandlers().getMenuContainer();
			menuContainer.getParent().setStyleName("player");
		}
	}

	@Override
	public void setViewCount(String viewCount) {
		rightPanelMetadata.setViewCount(viewCount);
		if(menuMetadataWidget!=null){
			menuMetadataWidget.setViewCount(viewCount);
		}
	}

	@Override
	public Anchor getFlagButton() {
		return rightPanelMetadata.getFlagButton();
	}

	@Override
	public void setTeacherInfo(ClasspageItemDo classpageItemDo) {
		rightPanelMetadata.setTeacherInfo(classpageItemDo);
		if(menuMetadataWidget!=null){
			menuMetadataWidget.setTeacherInfo(classpageItemDo);
		}
	}

	public ResourceServiceAsync getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceServiceAsync resourceService) {
		this.resourceService = resourceService;
	}


}