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

package org.ednovo.gooru.client.mvp.play.collection.preview.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.metadata.comment.CommentWidgetChildView;
import org.ednovo.gooru.client.uc.CollaboratorsUc;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.uc.StandardSgItemVc;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.model.content.CollectionDo;
import org.ednovo.gooru.shared.model.content.StandardFo;
import org.ednovo.gooru.shared.model.library.ConceptDo;
import org.ednovo.gooru.shared.model.player.CommentsDo;
import org.ednovo.gooru.shared.model.player.CommentsListDo;
import org.ednovo.gooru.shared.util.MessageProperties;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class PreviewPlayerMetadataView extends BaseViewWithHandlers<PreviewPlayerMetadataUiHandlers> implements IsPreviewPlayerMetadataView, MessageProperties{

	@UiField FlowPanel metadataContainer,standardsContainer,teamContainer;
	@UiField Label userNameLabel,viewsCountLabel,courseTitle,successPostMsg,commentCount,seeMoreButton,characterLimit,noCommentsLbl,orText,toCommentText;
	@UiField Label lblWhatsNext, lblSeeOtherRelatedConcepts,lblAuthor, lblCourse, lblStandards, lblRelatedConcepts,loginMessagingText;
	@UiField Image profileThumbnailImage,userPhoto;
	@UiField HTMLPanel authorPanel,whatNextPanel,addComment,loginMessaging,relatedConceptsEndPage,relatedConceptsCoverPage,homePageConceptsPanel,
						courseSection,standardSection;
	@UiField Anchor loginUrl, signupUrl,previewFlagButton;
	@UiField Button postCommentBtn,postCommentCancel;
	@UiField TextArea commentField;
	@UiField VerticalPanel commentsContainer;
	@UiField PreviewPlayerStyleBundle playerStyle;
	
	private static final String CREATE = "CREATE";
	
	private static final String DELETE = "DELETE";
	
	private static final String EDIT = "EDIT";
	
	private static final String PAGINATION = "page";
	
	private static final String COMMENTS_LBL = " "+GL1432;
	
	private static final String PRIMARY_STYLE = "primary";
	
	private static final String SECONDARY_STYLE = "secondary";
	
	private static final String DISABLED_STYLE = "disabled";
	
	private static final int INCREMENT_BY_ONE = 1;
	
	private static final int DECREMENT_BY_ONE = -1;

	private static final String EDUCATOR_DEFAULT_IMG = "../images/settings/setting-user-image.png";
	
	private CollectionDo collectionDo=null;
	
	public static final String STANDARD_CODE = "code";
	
	public static final String STANDARD_DESCRIPTION = "description";
	
	private static final String COLLECTION_COMMENTS="COLLECTION_COMMENTS";
	
	private static final String INITIAL_COMMENT_LIMIT = "10";
	
	private int totalCommentCount = 0;
	
	private int totalHitCount = 0;
	
	private int paginationCount = 0;
	
	private boolean isHavingBadWords;
	
	private boolean isConceptsVisible = false;
	
	private static CollectionPlayerMetadataViewUiBinder uiBinder = GWT.create(CollectionPlayerMetadataViewUiBinder.class);

	interface CollectionPlayerMetadataViewUiBinder extends UiBinder<Widget, PreviewPlayerMetadataView> {
	}
	
	@Inject
	public PreviewPlayerMetadataView(){
		setWidget(uiBinder.createAndBindUi(this));
		loginMessagingText.setText(GL0568);
		orText.setText(GL0209);
		toCommentText.setText(" "+GL0569);
		loginMessagingText.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().toCommentTextPreviewPlayer());
		orText.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().toCommentTextPreviewPlayer());
		toCommentText.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().toCommentTextPreviewPlayer());
		loginUrl.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().toCommentTextPreviewPlayer());
		signupUrl.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().toCommentTextPreviewPlayer());
		
		commentField.addClickHandler(new OnCommentsFieldClicked());
		commentField.addKeyUpHandler(new ValidateConfirmText());
		commentField.addBlurHandler(new OnCommentsFieldBlur());
		seeMoreButton.setVisible(false);
		setLabelAndIds();
	}
	
	@Override
	public void setCollectionMetadata(CollectionDo collectionDo) {
		this.collectionDo = collectionDo;
		teamContainer.clear();
		//getUiHandlers().getFlagedReport(collectionDo.getGooruOid());
		if (collectionDo.getMeta() !=null && collectionDo.getMeta().getCollaboratorCount()>0){
			 CollaboratorsUc collaboratorsUc=new CollaboratorsUc(collectionDo);
			 teamContainer.add(collaboratorsUc);
			setUserName(collectionDo.getUser().getUsernameDisplay() +" " + GL_GRR_AND);
		}else{
			setUserName(collectionDo.getUser().getUsernameDisplay());
		}
		setViewCount(collectionDo.getViews());
		setUserProfileImage(collectionDo.getUser().getGooruUId());
		renderCourseInfo(collectionDo.getMetaInfo().getCourse());
		renderStandards(standardsContainer,getStandardsMap(this.collectionDo.getMetaInfo().getStandards()));
	}
	

	public List<Map<String,String>> getStandardsMap(List<StandardFo> standareds){
		List<Map<String,String>> standardsList=new ArrayList<Map<String,String>>();
		for(int i=0;i<standareds.size();i++){
			Map<String, String> standardMap=new HashMap<String, String>();
			standardMap.put(STANDARD_CODE, standareds.get(i).getCode());
			standardMap.put(STANDARD_DESCRIPTION, standareds.get(i).getDescription());
			standardsList.add(standardMap);
		}
		return standardsList;
	}
	
	@Override
    public void setInSlot(Object slot, Widget content) {
		if(slot==PreviewPlayerMetadataPresenter.METADATA_PRESENTER_SLOT){
			metadataContainer.clear();
			if(content!=null){
				metadataContainer.add(content);
			}
		}
	}
	

	public void setLabelAndIds() {
		seeMoreButton.setText(GL0508);
//		loginMessaging.setTitle(GL0568);
		loginUrl.setText(GL0187.toLowerCase());
		signupUrl.setText(GL0186.toLowerCase());
		successPostMsg.setText(GL0570);
		postCommentBtn.setText(GL0571);
		postCommentCancel.setText(GL0142);
		characterLimit.setText(GL0143);
		lblWhatsNext.setText(GL0432);
		lblSeeOtherRelatedConcepts.setText(GL0572 + GL_SPL_SEMICOLON);
		lblAuthor.setText(GL0573);
		lblCourse.setText(GL0574);
		lblStandards.setText(GL0575);
		lblRelatedConcepts.setText(GL0576);
		
		postCommentBtn.setEnabled(false);
		previewFlagButton.setText(GL0556);
		previewFlagButton.removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().previewCoverFlagImageOrange());
		previewFlagButton.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().playerPreviewCoverFlagImage());
	}
	
	public void setUserName(String userName){
		userNameLabel.setText(userName);
	}
	public void setUserProfileImage(String profileUserId){
		profileThumbnailImage.setUrl(AppClientFactory.loggedInUser.getSettings().getProfileImageUrl()+profileUserId+".png?v="+Math.random());
	}
	@UiHandler("profileThumbnailImage")
	public void setDefaultProfileImage(ErrorEvent event){
		profileThumbnailImage.setUrl("images/settings/setting-user-image.png");
	}
	public void setViewCount(String viewCount){
		String viewsText=Integer.parseInt(viewCount)==1?viewCount+" "+GL1428:viewCount+" "+GL0934;
		viewsCountLabel.setText(viewsText);
	}
	public void renderCourseInfo(List<String> courseInfo){
		courseTitle.setText("");
		if(courseInfo!=null&&courseInfo.size()>0){
			courseSection.setVisible(true);
			setCourseTitle(courseInfo.get(0));
		}else{
			courseSection.setVisible(false);
		}
	}
	public void setCourseTitle(String title){
		courseTitle.setText(title);
	}
	public void setLikesCount(int likesCount){
		
	}
	public void resetMetadataFields(){
		userNameLabel.setText("");
		viewsCountLabel.setText("");
		commentField.setText("");
		commentField.setVisible(false);
		loginMessaging.setVisible(true);
		modifyEditControls(false);
		getFlagButton().setText(GL0556);
		getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().previewCoverFlagImageOrange());
		getFlagButton().setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().playerPreviewCoverFlagImage());
		this.collectionDo=null;
	}

	@Override
	public void setUserProfileName(String gooruUid) {
		Anchor anchor = new Anchor();
		String userName = userNameLabel.getText();
		anchor.setHref("#"+userName);
		anchor.setText(userName);
		anchor.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().setUserText());
		anchor.setTarget("_blank");
		userNameLabel.setText("");
		userNameLabel.getElement().appendChild(anchor.getElement());
	}
	public void renderStandards(FlowPanel standardsContainer, List<Map<String,String>> standardsList) {
		standardsContainer.clear();
		if (standardsList != null&&standardsList.size()>0) {
			standardSection.setVisible(true);
			Iterator<Map<String, String>> iterator = standardsList.iterator();
			int count = 0;
			FlowPanel toolTipwidgets = new FlowPanel();
			while (iterator.hasNext()) {
				Map<String, String> standard = iterator.next();
				String stdCode = standard.get(STANDARD_CODE);
				String stdDec = standard.get(STANDARD_DESCRIPTION);
				if (count > 2) {
					if (count < 18){
						StandardSgItemVc standardItem = new StandardSgItemVc(stdCode, stdDec);
						toolTipwidgets.add(standardItem);
					}
				} else {
					DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(stdCode), new Label(stdDec), standardsList);
					toolTipUc.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getstandardMoreInfo());
					standardsContainer.add(toolTipUc);
				}
				count++;
			}
			if (standardsList.size()>18){
				final Label left = new Label("+"+(standardsList.size() - 18));
				toolTipwidgets.add(left);
			}
			if (standardsList.size() > 2) {
				Integer moreStandardsCount = standardsList.size() - 3;
				if (moreStandardsCount > 0){
					DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label("+" + moreStandardsCount), toolTipwidgets, standardsList);
					toolTipUc.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getstandardMoreLink());
					standardsContainer.add(toolTipUc);
				}
			}
		}else{
			standardSection.setVisible(false);
		}
	}
	
	@Override
	public void displayAuthorDetails(boolean isDisplayDetails) {
		authorPanel.setVisible(isDisplayDetails);
		whatNextPanel.setVisible(!isDisplayDetails);
		homePageConceptsPanel.setVisible(isDisplayDetails);
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
		LoginPopupUc popup = new LoginPopupUc();
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
		if(action.equalsIgnoreCase(CREATE)){
			setCommentsText(INCREMENT_BY_ONE);
		}
		commentsContainer.add(new CommentWidgetChildView(commentsDo,collectionDo));
		showSeeMoreButton();
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
				parms.put("text", commentField.getText());
				postCommentBtn.setEnabled(false);
				AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
	
					@Override
					public void onSuccess(Boolean value) {
						isHavingBadWords = value;
						postCommentBtn.setEnabled(true);
						if (value){
							commentField.getElement().getStyle().setBorderColor("orange");
							characterLimit.setText(GL0554);
							characterLimit.setVisible(true);
						}else{
							commentField.getElement().getStyle().clearBackgroundColor();
							commentField.getElement().getStyle().setBorderColor("#ccc");
							characterLimit.setVisible(false);
							
							getUiHandlers().createCommentForCollection(collectionDo.getGooruOid(), commentField.getText());
							
							commentField.setText("");
							characterLimit.setVisible(false);
							modifyEditControls(false);
							displaySuccessMsg(true);
						}
					}
				});
			}
		}else{
			commentField.setText("");
			characterLimit.setVisible(false);
			modifyEditControls(false);
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
							commentField.getElement().getStyle().setBorderColor("orange");
							characterLimit.setText(GL0554);
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
			if(commentField.getText().length()>415) {
				commentField.setText(commentField.getText().substring(0,415));
				characterLimit.setText(GL0143);
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
		if(!commentUid.isEmpty() && action.equals(DELETE)) {
			deleteComment(commentUid);
			addComment.setVisible(true);
		} else if (!commentUid.isEmpty() && action.equals(EDIT)) {
			addComment.setVisible(false);
			editComment(commentUid);
		} else if(commentUid.isEmpty() && action.equals(EDIT)) {
			addComment.setVisible(true);
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
				int index = commentsContainer.getWidgetIndex(commentWidgetChildView);
				commentsContainer.remove(index);
				final HTMLPanel deletePanel = new HTMLPanel(GL0555);
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
						getUiHandlers().deleteCommentFromCollection(collectionDo.getGooruOid(),commentUid, commentsContainer.getWidgetCount()+"", 1+"");
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
		commentCount.setText(totalCommentCount+COMMENTS_LBL);
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
	
	@UiHandler("previewFlagButton")
	public void clickOnpreviewFlagButton(ClickEvent event) {
		String view=AppClientFactory.getPlaceManager().getRequestParameter("view", null);
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionDo.getGooruOid());
		params = PreviewPlayerPresenter.setConceptPlayerParameters(params);
		if(view!=null&&view.equalsIgnoreCase("end")){
			params.put("view", "end");
			params.put("tab", "flag");
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
		}
		else
		{
			params.put("tab", "flag");
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
		}
		
	}

	@Override
	public Anchor getFlagButton() {
		return previewFlagButton;
	}

	public void clearCommentContainer(boolean isClear) {
		if(isClear) {
			commentsContainer.clear();
			totalCommentCount = 0;
			totalHitCount = 0;
			paginationCount = 0;
		}
	}
	
	@Override
	public void setRelatedConceptsContent(ArrayList<ConceptDo> conceptDoList, final String coursePage, final String subject, final String lessonId, final String libraryName) {
		relatedConceptsEndPage.clear();
		relatedConceptsCoverPage.clear();
		String gooruOid = AppClientFactory.getPlaceManager().getRequestParameter("id");
		int size = conceptDoList.size();
		if(size>0) {
			for(int i = 0; i<conceptDoList.size(); i++) {
				final ConceptDo conceptDo = conceptDoList.get(i);
				if(!conceptDo.getGooruOid().equals(gooruOid)) {
					relatedConceptsCoverPage.add(getConceptAnchorLink(conceptDo.getTitle(), conceptDo.getGooruOid(), subject, lessonId));
					relatedConceptsEndPage.add(getConceptAnchorLink(conceptDo.getTitle(), conceptDo.getGooruOid(), subject, lessonId));
				}
				if (size==1 && conceptDo.getGooruOid().equals(gooruOid)){
					relatedConceptsEndPage.add(emptyAnchorLink(libraryName));
					relatedConceptsCoverPage.add(emptyAnchorLink(libraryName));
				}
			}
		} else {
			relatedConceptsEndPage.add(emptyAnchorLink(libraryName));
			relatedConceptsCoverPage.add(emptyAnchorLink(libraryName));
		}
		
	}
	
	private Anchor emptyAnchorLink(String libraryName) {
		Anchor conceptTitle = new Anchor(GL0586);
		conceptTitle.addStyleName(playerStyle.conceptTitle());
		String url = "#"+libraryName;
		conceptTitle.setHref(url);
		return conceptTitle;
	}
	
	private Anchor getConceptAnchorLink(String title, String gooruOid, String subject, String lessonId) {
		Anchor conceptTitle = new Anchor(title);
		conceptTitle.addStyleName(playerStyle.conceptTitle());
		String url = "#"+PlaceTokens.PREVIEW_PLAY+"&id="+gooruOid;
		if(subject!=null) {
			url = url+"&subject="+subject;
		}
		if(lessonId!=null) {
			url = url+"&lessonId="+lessonId;
		}
		conceptTitle.setHref(url);
		conceptTitle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				MixpanelUtil.mixpanelEvent("Preview_Player_Click_Related_Concept");
			}
		});
		return conceptTitle;
	}
	
	@Override
	public void isConceptsContainerVisible(boolean isVisible) {
		isConceptsVisible = isVisible;
		if(!isConceptsVisible) {
			whatNextPanel.setVisible(false);
			homePageConceptsPanel.setVisible(false);
		}
	}
}