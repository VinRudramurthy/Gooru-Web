/*******************************************************************************
1 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
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

package org.ednovo.gooru.client.mvp.assessments.play.collection.preview.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.content.checkboxSelectedDo;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.search.SearchResultWrapperCBundle;
import org.ednovo.gooru.client.mvp.search.SearchUiUtil;
import org.ednovo.gooru.client.uc.CollaboratorsUc;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.uc.StandardSgItemVc;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class AssessmentsPreviewPlayerMetadataView extends BaseViewWithHandlers<AssessmentsPreviewPlayerMetadataUiHandlers> implements IsAssessmentsPreviewPlayerMetadataView{

	@UiField FlowPanel metadataContainer,standardsContainer,teamContainer,courseTitle;
	@UiField
	static FlowPanel mainPlayerContainer;
	@UiField Label userNameLabel,viewsCountLabel,successPostMsg,commentCount,seeMoreButton,characterLimit,noCommentsLbl,orText,toCommentText;
	@UiField Label lblWhatsNext, lblSeeOtherRelatedConcepts,lblAuthor, lblCourse, lblStandards, lblRelatedConcepts,loginMessagingText;
	@UiField Image profileThumbnailImage,userPhoto;
	@UiField HTMLPanel authorPanel,whatNextPanel,addComment,loginMessaging,relatedConceptsEndPage,relatedConceptsCoverPage,homePageConceptsPanel,
						courseSection,standardSection,depthOfKnowledgePanel,audiencePanel,instructionalmethodPanel,learningAndInnovationSkillPanel,
						InstructionalmethodContainer,audienceContainer,learningAndInnovationSkillsContainer,depthOfKnowledgeContainer,languageObjectiveContainer;
	@UiField Anchor loginUrl, signupUrl,previewFlagButton,seeMoreAnchor;
	@UiField Button postCommentBtn,postCommentCancel;
	@UiField TextArea commentField;
	@UiField VerticalPanel commentsContainer;
	@UiField AssessmentsPreviewPlayerStyleBundle playerStyle;
	@UiField Label lbllanguageObjectiveText,lbldepthOfKnowledgeText,teacherTipLabel,lbllearningAndInnovationText,lblAudienceText,lblInstructionalmethodText;
	@UiField InlineLabel lbllanguageObjective,lbllanguageObjectiveAll;
	private static final String CREATE = "CREATE";

	private static final String DELETE = "DELETE";

	private static final String EDIT = "EDIT";

	private static final String PAGINATION = "page";

//	private static final String COMMENTS_LBL = " "+i18n.GL1432;

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

	private boolean isConceptsVisible = false;

	private String languageObjectiveValue, depthofKnowledgeValue;
	private Anchor usernameAnchor;
	private static CollectionPlayerMetadataViewUiBinder uiBinder = GWT.create(CollectionPlayerMetadataViewUiBinder.class);

	interface CollectionPlayerMetadataViewUiBinder extends UiBinder<Widget, AssessmentsPreviewPlayerMetadataView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public AssessmentsPreviewPlayerMetadataView(){
		setWidget(uiBinder.createAndBindUi(this));
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

		commentField.addClickHandler(new OnCommentsFieldClicked());
		commentField.addKeyUpHandler(new ValidateConfirmText());
		commentField.addBlurHandler(new OnCommentsFieldBlur());
		seeMoreButton.setVisible(false);
		setLabelAndIds();

		Boolean isIpad = !!Navigator.getUserAgent().matches("(.*)iPad(.*)");
		Boolean isAndriod = !!Navigator.getUserAgent().matches("(.*)Android(.*)");

		  if(isIpad && !StringUtil.IPAD_MESSAGE_Close_Click)
		  {
			//  mainPlayerContainer.getElement().setAttribute("style", "margin-top:0px;");

		  }
		  else if(isAndriod && !StringUtil.IPAD_MESSAGE_Close_Click)
		  {
			//  mainPlayerContainer.getElement().setAttribute("style", "margin-top:0px;");
		  }
		  else
		  {
			 // mainPlayerContainer.getElement().setAttribute("style", "margin-top:50px;");

		  }

	}

	@Override
	public void setCollectionMetadata(CollectionDo collectionDo) {
		this.collectionDo = collectionDo;
		teamContainer.clear();
		if (collectionDo.getMeta() !=null && collectionDo.getMeta().getCollaboratorCount()>0){
			 CollaboratorsUc collaboratorsUc=new CollaboratorsUc(collectionDo);
			 teamContainer.add(collaboratorsUc);
			 setUserName(collectionDo.getUser().getUsernameDisplay() +" " + i18n.GL_GRR_AND());
		}else{
			 setUserName(collectionDo.getUser().getUsernameDisplay());
		}
		if(!StringUtil.isEmpty(collectionDo.getViews())){
			setViewCount(collectionDo.getViews());
		}
		if(collectionDo.getUser()!=null && !StringUtil.isEmpty(collectionDo.getUser().getGooruUId())){
			setUserProfileImage(collectionDo.getUser().getGooruUId());
		}
		renderCourseInfo(collectionDo.getMetaInfo().getCourse());
		renderStandards(standardsContainer,getStandardsMap(this.collectionDo.getMetaInfo().getStandards()));
		renderDepthOfKnowledge(collectionDo.getDepthOfKnowledges());
		renderInstructionalMethod(collectionDo.getInstructionalMethod());
		renderAudience(collectionDo.getAudience());
		renderLearningAndInnovationSkill(collectionDo.getLearningSkills());
		renderLanguageObjective(collectionDo.getLanguageObjective());
		if(collectionDo.getKeyPoints() != null){
			if(collectionDo.getKeyPoints().length()>410){
				authorPanel.getElement().getStyle().setHeight(295, Unit.PX);
			}else if(collectionDo.getKeyPoints().length()>300){
				authorPanel.getElement().getStyle().setHeight(253, Unit.PX);
			}else if(collectionDo.getKeyPoints().length()>100){
				authorPanel.getElement().getStyle().setHeight(130, Unit.PX);
			}else{
				authorPanel.getElement().getStyle().setHeight(100, Unit.PX);
			}
			teacherTipLabel.setText(""+collectionDo.getKeyPoints()+"");
			teacherTipLabel.getElement().setAttribute("alt",""+collectionDo.getKeyPoints()+"");
			teacherTipLabel.getElement().setAttribute("title",""+collectionDo.getKeyPoints()+"");
		}
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
		if(slot==AssessmentsPreviewPlayerMetadataPresenter.METADATA_PRESENTER_SLOT){
			metadataContainer.clear();
			if(content!=null){
				metadataContainer.add(content);
			}
		}
	}

	public void setLabelAndIds() {
		seeMoreButton.setText(i18n.GL0508());
		seeMoreButton.getElement().setId("lblSeeMoreButton");
		seeMoreButton.getElement().setAttribute("alt",i18n.GL0508());
		seeMoreButton.getElement().setAttribute("title",i18n.GL0508());

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

		lblWhatsNext.setText(i18n.GL0432());
		lblWhatsNext.getElement().setId("lblWhatsNext");
		lblWhatsNext.getElement().setAttribute("alt",i18n.GL0432());
		lblWhatsNext.getElement().setAttribute("title",i18n.GL0432());

		lblSeeOtherRelatedConcepts.setText(i18n.GL0572() + i18n.GL_SPL_SEMICOLON()+" ");
		lblSeeOtherRelatedConcepts.getElement().setId("lblSeeOtherRelatedConcepts");
		lblSeeOtherRelatedConcepts.getElement().setAttribute("alt",i18n.GL0572());
		lblSeeOtherRelatedConcepts.getElement().setAttribute("title",i18n.GL0572());

		lblAuthor.setText(i18n.GL0573());
		lblAuthor.getElement().setId("lblAuthor");
		lblAuthor.getElement().setAttribute("alt",i18n.GL0573());
		lblAuthor.getElement().setAttribute("title",i18n.GL0573());

		lblCourse.setText(i18n.GL0574());
		lblCourse.getElement().setId("lblCourse");
		lblCourse.getElement().setAttribute("alt",i18n.GL0574());
		lblCourse.getElement().setAttribute("title",i18n.GL0574());

		lblStandards.setText(i18n.GL0575());
		lblStandards.getElement().setId("lblStandards");
		lblStandards.getElement().setAttribute("alt",i18n.GL0575());
		lblStandards.getElement().setAttribute("title",i18n.GL0575());

		lblRelatedConcepts.setText(i18n.GL0576());
		lblRelatedConcepts.getElement().setId("lblRelatedConcepts");
		lblRelatedConcepts.getElement().setAttribute("alt",i18n.GL0576());
		lblRelatedConcepts.getElement().setAttribute("title",i18n.GL0576());

		postCommentBtn.setEnabled(false);
		previewFlagButton.setText(i18n.GL0556());
		previewFlagButton.getElement().setId("btnPreviewFlagButton");
		previewFlagButton.getElement().setAttribute("alt",i18n.GL0556());
		previewFlagButton.getElement().setAttribute("title",i18n.GL0556());
		previewFlagButton.removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().previewCoverFlagImageOrange());
		previewFlagButton.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().playerPreviewCoverFlagImage());
		//added for 6.2 release
		lbllanguageObjectiveText.setText(i18n.GL1721());
		lbllanguageObjectiveText.getElement().setId("lbllanguageObjectiveText");
		lbllanguageObjectiveText.getElement().setAttribute("alt",i18n.GL1721());
		lbllanguageObjectiveText.getElement().setAttribute("title",i18n.GL1721());

		lbldepthOfKnowledgeText.setText(i18n.GL1693());
		lbldepthOfKnowledgeText.getElement().setId("lbldepthOfKnowledgeText");
		lbldepthOfKnowledgeText.getElement().setAttribute("alt",i18n.GL1693());
		lbldepthOfKnowledgeText.getElement().setAttribute("title",i18n.GL1693());

		lbllearningAndInnovationText.setText(i18n.GL1722());
		lbllearningAndInnovationText.getElement().setId("lbllearningAndInnovationText");
		lbllearningAndInnovationText.getElement().setAttribute("alt",i18n.GL1722());
		lbllearningAndInnovationText.getElement().setAttribute("title",i18n.GL1722());

		lblAudienceText.setText(i18n.GL1723());
		lblAudienceText.getElement().setId("lblAudienceText");
		lblAudienceText.getElement().setAttribute("alt",i18n.GL1723());
		lblAudienceText.getElement().setAttribute("title",i18n.GL1723());

		lblInstructionalmethodText.setText(i18n.GL1724());
		lblInstructionalmethodText.getElement().setId("lblInstructionalmethodText");
		lblInstructionalmethodText.getElement().setAttribute("alt",i18n.GL1724());
		lblInstructionalmethodText.getElement().setAttribute("title",i18n.GL1724());


		mainPlayerContainer.getElement().setId("fpnlMainPlayerContainer");
		metadataContainer.getElement().setId("fpnlMetadataContainer");
		commentCount.getElement().setId("lblCommentCount");
		commentsContainer.getElement().setId("vpnlCommentsContainer");
		seeMoreButton.getElement().setId("lblSeeMoreButton");
		noCommentsLbl.getElement().setId("lblNoCommentsLbl");
		addComment.getElement().setId("pnlAddComment");
		userPhoto.getElement().setId("imgUserPhoto");
		commentField.getElement().setId("tatCommentField");
		StringUtil.setAttributes(commentField, true);
		whatNextPanel.getElement().setId("pnlWhatNextPanel");
		relatedConceptsEndPage.getElement().setId("pnlRelatedConceptsEndPage");
		authorPanel.getElement().setId("pnlAuthorPanel");
		profileThumbnailImage.getElement().setId("imgProfileThumbnailImage");
		teacherTipLabel.getElement().setId("lblTeacherTipLabel");
		userNameLabel.getElement().setId("lblUserNameLabel");
		teamContainer.getElement().setId("fpnlTeamContainer");
		courseSection.getElement().setId("pnlCourseSection");
		courseTitle.getElement().setId("fpnlCourseTitle");
		standardSection.getElement().setId("pnlStandardSection");
		standardsContainer.getElement().setId("fpnlStandardsContainer");
		viewsCountLabel.getElement().setId("lblViewsCountLabel");
		languageObjectiveContainer.getElement().setId("pnlLanguageObjectiveContainer");
		lbllanguageObjective.getElement().setId("spnLbllanguageObjective");
		seeMoreAnchor.getElement().setId("lnkSeeMoreAnchor");
		lbllanguageObjectiveAll.getElement().setId("spnLbllanguageObjectiveAll");
		depthOfKnowledgeContainer.getElement().setId("pnlDepthOfKnowledgeContainer");
		depthOfKnowledgePanel.getElement().setId("pnlDepthOfKnowledgePanel");
		learningAndInnovationSkillsContainer.getElement().setId("pnlLearningAndInnovationSkillsContainer");
		learningAndInnovationSkillPanel.getElement().setId("pnlLearningAndInnovationSkillPanel");
		audienceContainer.getElement().setId("pnlAudienceContainer");
		audiencePanel.getElement().setId("pnlAudiencePanel");
		InstructionalmethodContainer.getElement().setId("pnlInstructionalmethodContainer");
		instructionalmethodPanel.getElement().setId("pnlInstructionalmethodPanel");
		homePageConceptsPanel.getElement().setId("pnlHomePageConceptsPanel");
		relatedConceptsCoverPage.getElement().setId("pnlRelatedConceptsCoverPage");
	}

	public void setUserName(String userName){
		if(collectionDo.getUser()!=null && collectionDo.getUser().getCustomFields()!=null && collectionDo.getUser().getCustomFields().size()>0){
			if(collectionDo.getUser().getCustomFields().get(0).getOptionalValue()!=null){
				String getUserProfileStatus	=collectionDo.getUser().getCustomFields().get(0).getOptionalValue();
				if(getUserProfileStatus.equalsIgnoreCase("true")){
					usernameAnchor = new Anchor();
					if(StringUtil.isPartnerUser(collectionDo.getUser().getUsername()!=null?collectionDo.getUser().getUsername():"")){
						usernameAnchor.setHref("#"+collectionDo.getUser().getUsernameDisplay());
					}else{
						String token= "#"+PlaceTokens.PROFILE_PAGE+"&id="+collectionDo.getUser().getGooruUId()+"&user="+collectionDo.getUser().getUsername();
						usernameAnchor.setHref(token);
					}
					usernameAnchor.setText(userName);
					usernameAnchor.getElement().setAttribute("alt",userName);
					usernameAnchor.getElement().setAttribute("title",userName);
					usernameAnchor.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().setUserText());
					usernameAnchor.setTarget("_blank");
					userNameLabel.setText("");
					userNameLabel.getElement().setAttribute("alt","");
					userNameLabel.getElement().setAttribute("title","");
					userNameLabel.getElement().appendChild(usernameAnchor.getElement());
				}
				else{
					userNameLabel.setText(userName);
					userNameLabel.getElement().setAttribute("alt",userName);
					userNameLabel.getElement().setAttribute("title",userName);
				}
			}
		}
	}
	public void setUserProfileImage(String profileUserId){
		profileThumbnailImage.setUrl(AppClientFactory.loggedInUser.getSettings().getProfileImageUrl()+profileUserId+".png?v="+Math.random());
	}
	@UiHandler("profileThumbnailImage")
	public void setDefaultProfileImage(ErrorEvent event){
		profileThumbnailImage.setUrl("images/settings/setting-user-image.png");
	}
	public void setViewCount(String viewCount){
		String viewsText=Integer.parseInt(viewCount)==1?viewCount+" "+i18n.GL1428():viewCount+" "+i18n.GL0934();
		viewsCountLabel.setText(viewsText);
		viewsCountLabel.getElement().setAttribute("alt",viewsText);
		viewsCountLabel.getElement().setAttribute("title",viewsText);
	}
	public void renderCourseInfo(List<String> courseInfo){
		courseTitle.clear();
		if(courseInfo!=null&&courseInfo.size()>0){
			courseSection.setVisible(true);
			SearchUiUtil.renderMetaData(courseTitle, courseInfo, 0);
			Label dummyLabel=new Label();
			dummyLabel.setStyleName(playerStyle.clearBoth());
			courseTitle.add(dummyLabel);
		}else{
			courseSection.setVisible(false);
		}
	}
	public void setCourseTitle(String title){

	}
	public void setLikesCount(int likesCount){

	}
	public void resetMetadataFields(){
		userNameLabel.setText("");
		userNameLabel.getElement().setAttribute("alt","");
		userNameLabel.getElement().setAttribute("title","");
		viewsCountLabel.setText("");
		viewsCountLabel.getElement().setAttribute("alt","");
		viewsCountLabel.getElement().setAttribute("title","");
		commentField.setText("");
		commentField.getElement().setAttribute("alt","");
		commentField.getElement().setAttribute("title","");
		commentField.setVisible(false);
		loginMessaging.setVisible(true);
		modifyEditControls(false);
		getFlagButton().setText(i18n.GL0556());
		getFlagButton().removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().previewCoverFlagImageOrange());
		getFlagButton().setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().playerPreviewCoverFlagImage());
		this.collectionDo=null;
	}

	@Override
	public void setUserProfileName(String gooruUid) {
		Anchor anchor = new Anchor();
		String userName=usernameAnchor.getText();
		if(StringUtil.isPartnerUser(collectionDo.getUser().getUsername())){
			anchor.setHref("#"+collectionDo.getUser().getUsernameDisplay());
		}else{
			String token= "#"+PlaceTokens.PROFILE_PAGE+"&id="+gooruUid+"&user="+collectionDo.getUser().getUsername();
			anchor.setHref(token);
		}
		anchor.setText(userName);
		anchor.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().setUserText());
		anchor.setTarget("_blank");
		userNameLabel.setText("");
		userNameLabel.getElement().setAttribute("alt","");
		userNameLabel.getElement().setAttribute("title","");
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
				commentField.getElement().setAttribute("alt",commentField.getText().substring(0,415));
				commentField.getElement().setAttribute("title",commentField.getText().substring(0,415));
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


	@UiHandler("previewFlagButton")
	public void clickOnpreviewFlagButton(ClickEvent event) {
		String view=AppClientFactory.getPlaceManager().getRequestParameter("view", null);
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionDo.getGooruOid());
		params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);
		if(view!=null&&view.equalsIgnoreCase("end")){
			params.put("view", "end");
			params.put("tab", "flag");
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
		}else{
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
					if(conceptDo.getTitle()!=null && conceptDo.getGooruOid()!=null){
						relatedConceptsCoverPage.add(getConceptAnchorLink(conceptDo.getTitle(),conceptDo.getGooruOid(), subject, lessonId));
						relatedConceptsEndPage.add(getConceptAnchorLink(conceptDo.getTitle(),conceptDo.getGooruOid(), subject, lessonId));
					}
				}
				if (size==1 && conceptDo.getGooruOid().equals(gooruOid)){
					if(!StringUtil.isEmpty(libraryName)){
						relatedConceptsEndPage.add(emptyAnchorLink(libraryName));
						relatedConceptsCoverPage.add(emptyAnchorLink(libraryName));
					}
				}
			}
		} else {
			if(!StringUtil.isEmpty(libraryName)){
				relatedConceptsEndPage.add(emptyAnchorLink(libraryName));
				relatedConceptsCoverPage.add(emptyAnchorLink(libraryName));
			}
		}
	}

	private Anchor emptyAnchorLink(String libraryName) {
		Anchor conceptTitle = new Anchor(i18n.GL0586());
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
	public void renderDepthOfKnowledge(List<checkboxSelectedDo> depthofKnowledgeList ) {
		depthOfKnowledgeContainer.setVisible(false);
		if(depthofKnowledgeList!=null && depthofKnowledgeList.size()>0){
			depthOfKnowledgePanel.clear();
			boolean depthofKnowledgeValue = false;
			for (checkboxSelectedDo checkboxSelectedDo : depthofKnowledgeList) {
				if(checkboxSelectedDo.isSelected()){
					depthofKnowledgeValue = true;
					Label depthofKnowledge = new Label(checkboxSelectedDo.getValue());
					depthofKnowledge.addStyleName(playerStyle.depthofKnow());
					depthofKnowledge.getElement().setAttribute("style", "display:table");
					depthOfKnowledgePanel.add(depthofKnowledge);
				}
			}
			if(depthofKnowledgeValue){
				depthOfKnowledgeContainer.setVisible(true);
			}else{
				depthOfKnowledgeContainer.setVisible(false);
			}
		}else{
			depthOfKnowledgeContainer.setVisible(false);
		}
	}
	public void renderInstructionalMethod(List<checkboxSelectedDo> instructionmethodList){
		InstructionalmethodContainer.setVisible(false);
		if(instructionmethodList!=null && instructionmethodList.size()>0){
				instructionalmethodPanel.clear();
				boolean instructionMethod=false;
				for(checkboxSelectedDo checkboxSelectedDo : instructionmethodList){
					if(checkboxSelectedDo.isSelected()){
						instructionMethod = true;
						Label lblInstructionMethod = new Label(checkboxSelectedDo.getValue());
						instructionalmethodPanel.add(lblInstructionMethod);
						InstructionalmethodContainer.setVisible(true);
					}
				}
				if(instructionMethod){
					InstructionalmethodContainer.setVisible(true);
				}else{
					InstructionalmethodContainer.setVisible(false);
				}
		}else{
			InstructionalmethodContainer.setVisible(false);
		}
	}
	public void renderAudience(List<checkboxSelectedDo> audienceList){
		audienceContainer.setVisible(false);
		if(audienceList!=null && audienceList.size()>0){
			audiencePanel.clear();
			boolean audience=false;
			for (checkboxSelectedDo checkboxSelectedDo : audienceList) {
				if(checkboxSelectedDo.isSelected()){
					audience = true;
					Label lblaudience = new Label(checkboxSelectedDo.getValue());
					audiencePanel.add(lblaudience);
				}
			}
			if(audience){
				audienceContainer.setVisible(true);
			}else{
				audienceContainer.setVisible(false);
			}
		}else{
			audienceContainer.setVisible(false);
		}
	}
	public void renderLearningAndInnovationSkill(List<checkboxSelectedDo> learningSkillsList){
		learningAndInnovationSkillsContainer.setVisible(false);
		if(learningSkillsList!=null && learningSkillsList.size()>0){
			learningAndInnovationSkillPanel.clear();
			boolean learningAndInnovationSkill = false;
			Label lbllearningSkills = null;
			for (checkboxSelectedDo checkboxSelectedDo : learningSkillsList) {
				if(checkboxSelectedDo.isSelected()){
					learningAndInnovationSkill = true;
					lbllearningSkills = new Label(checkboxSelectedDo.getValue());
					lbllearningSkills.addStyleName(playerStyle.depthofKnow());
					lbllearningSkills.getElement().setAttribute("style", "display:table");
					learningAndInnovationSkillPanel.add(lbllearningSkills);
				}
			}
			if(learningAndInnovationSkill){
				learningAndInnovationSkillsContainer.setVisible(true);
			}else{
				learningAndInnovationSkillsContainer.setVisible(false);
			}
		}else{
			learningAndInnovationSkillsContainer.setVisible(false);
		}
	}

	public void renderLanguageObjective(String languageObjective){
		lbllanguageObjective.getElement().setAttribute("style", "word-wrap: break-word;");
		if(!StringUtil.isEmpty(languageObjective)){
			languageObjectiveValue=languageObjective;
			languageObjectiveContainer.setVisible(true);
			seeMoreAnchor.getElement().setAttribute("style", "float:right;");
			if(languageObjective.length()>=200){
				seeMoreAnchor.setText(i18n.GL1728());
				seeMoreAnchor.getElement().setAttribute("alt",i18n.GL1728());
				seeMoreAnchor.getElement().setAttribute("title",i18n.GL1728());
				seeMoreAnchor.setVisible(true);
				lbllanguageObjective.setText(languageObjective.substring(0,200));
				lbllanguageObjective.getElement().setAttribute("alt",languageObjective.substring(0,200));
				lbllanguageObjective.getElement().setAttribute("title",languageObjective.substring(0,200));
			}else{
				seeMoreAnchor.setVisible(false);
				lbllanguageObjective.setText(languageObjective);
				lbllanguageObjective.getElement().setAttribute("alt",languageObjective);
				lbllanguageObjective.getElement().setAttribute("title",languageObjective);
			}
		}else{
			languageObjectiveContainer.setVisible(false);
		}
	}

	@UiHandler("seeMoreAnchor")
	public void clickSeeAll(ClickEvent event){
		lbllanguageObjective.setText("");
		seeMoreAnchor.setVisible(false);
		lbllanguageObjective.setText(languageObjectiveValue);
		lbllanguageObjective.getElement().setAttribute("alt",languageObjectiveValue);
		lbllanguageObjective.getElement().setAttribute("title",languageObjectiveValue);
	}

	public static void onClosingAndriodorIpaddiv()
	{
		// mainPlayerContainer.getElement().setAttribute("style", "margin-top:50px;");
	}

}