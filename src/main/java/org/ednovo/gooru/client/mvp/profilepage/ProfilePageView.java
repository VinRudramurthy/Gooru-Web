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
package org.ednovo.gooru.client.mvp.profilepage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.LibraryCodeDo;
import org.ednovo.gooru.application.shared.model.code.ProfileCodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.social.SocialShareDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.application.shared.model.user.UserFollowDo;
import org.ednovo.gooru.application.shared.model.user.UserTagsDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.mvp.home.FooterUc;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.profilepage.data.ProfilePageLibraryView;
import org.ednovo.gooru.client.mvp.profilepage.tab.content.Followers.ProfilePageFollowersView;
import org.ednovo.gooru.client.mvp.profilepage.tab.content.Followers.ProfilePagefollowingView;
import org.ednovo.gooru.client.mvp.profilepage.tab.content.tags.ProfileUserTagView;
import org.ednovo.gooru.client.mvp.profilepage.tab.content.tags.ProfileUserTagsResourceView;
import org.ednovo.gooru.client.mvp.search.SearchResultWrapperCBundle;
import org.ednovo.gooru.client.mvp.settings.UserSettingStyle;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionCBundle;
import org.ednovo.gooru.client.mvp.socialshare.SocialShareView;
import org.ednovo.gooru.client.uc.AlertContentUc;
import org.ednovo.gooru.client.uc.CloseLabel;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.ProfilePageDescriptionEditUc;
import org.ednovo.gooru.client.uc.ProfilePageGradeLabel;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.tractionsoftware.gwt.user.client.ui.GroupedListBox;

public class ProfilePageView extends BaseViewWithHandlers<ProfilePageUiHandlers> implements IsProfilePageView{
	@UiField
	Anchor contentTabVc;

	@UiField
	Label userName, userBio, aboutUsCharacterValidation, courseMaxMsg,profilePageViewMsg, roleTxt, userLibraryMessage, libraryMessage;

	@UiField Label cancelBtn,gradeText,courseLabel,profilePageText, courseGradeLbl,lblDescCharLimit;

	@UiField
	Image userProfilePic,errorImage;

	@UiField
	HTMLPanel profileOnContainerPanel, profileOffContainerPanel,hpnlQuestion;

	@UiField
	HTMLPanel contentview, shareLinkFloPanel,
			socialButtonContainer, bioMainContainer,mainContainer;

	@UiField
	HTMLPanel loadingPanel, userGradeList, userCourseList, metaDataContainer;

	@UiField
	HTMLEventPanel profileImageContainer, profileDescriptionlabel,
			pencilTextAreaImage, userCoursePopup,
			userMetadata, editPencil;

	@UiField
	Button  profileOnButton, profileOffButton, btnSave,
			addCourseBtn, saveBtn, addBioBtn, addCourseGradeBtn,biographyCancelButton,followButton,FollowingButtonBlue;

	Boolean justClicked = false;

	/**
	 * This method is to get the followButton
	 */
	@Override
	public Button getFollowButton() {
		return followButton;
	}
	/**
	 * This method is to set the followButton
	 */
	public void setFollowButton(Button followButton) {
		this.followButton = followButton;
	}
	/**
	 * This method is to get the unFollowButton
	 */

	/**
	 * This method is to set the unFollowButton
	 */

	@UiField
	HTMLPanel gooruSocialButtonsContainer, gooruProfileOnOffContainer,
			profilePageEditBioPanel,profileMainContainer,followingContainer,tagResourceContainer;

	@UiField FlowPanel moreGradeCourseLbl, moreCourseLbl;

	@UiField
	UserSettingStyle settingsStyle;

	@UiField(provided = true)
	ProfilePageDescriptionEditUc profileTextArea;

	@UiField
	FocusPanel noAboutUsContainer;

	@UiField
	FlowPanel gradeTopList, gradeMiddleList, KinderGarten, higherEducation;

	@UiField
	FlowPanel courseData, collectionCourseLstPanel, coursesPanel;

	@UiField
	ProfilePageLibraryView profilePageLibraryView;

	@UiField HTML profileVisiblityMessage;


	@UiField
	ProfilePageTabVc collectionsTabVc, followingTabVc, followersTabVc,tagTabVc;

	Label noCollectionMsgPanel = new Label();

	private Label editImageButton = new Label("Edit image");

	private FooterUc footerUc = new FooterUc();

	private AlertContentUc alertContentUc;

	private PopupPanel toolTipPopupPanel=new PopupPanel();

	private SocialShareView socialView=null;

	boolean enableEdit = true;
	private String isFollowUser ;

	/* HTML5 Storage implementation for tab persistance */
	private Storage stockStore = null;

    boolean isProfileAdmin = false;

	private boolean isShowing=false;

	private ProfileDo profileDo;

	private GroupedListBox collectionCourseLst;

	private SocialShareDo shareDo;

	private String profileOnStatus = "false";

	private static String USER_META_ACTIVE_FLAG = "1";

	private boolean isGradeCourseBtnClick = false;

	private String profileImageUrl="images/profilepage/user-profile-pic.png";

	List<UserFollowDo> userFollowingDo = new ArrayList<UserFollowDo>();
	List<UserFollowDo> userFollowerDo = new ArrayList<UserFollowDo>();
	List<UserTagsDo> userTagDo = new ArrayList<UserTagsDo>();

	private HandlerRegistration tagHandler;
	private HandlerRegistration collectionHandler;
	private HandlerRegistration followingHandler;
	private HandlerRegistration follwerHandler;
	private PopupPanel toolTipPopupPanelNew = new PopupPanel();

	ProfileUserTagsResourceView profileUserTagsResourceView = null;
		private static ProfilePageViewUiBinder uiBinder = GWT
			.create(ProfilePageViewUiBinder.class);

	interface ProfilePageViewUiBinder extends UiBinder<Widget, ProfilePageView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public ProfilePageView() {
		profileTextArea = new ProfilePageDescriptionEditUc() {
			@Override
			public void onEditDisabled(String text) {
				aboutUsCharacterValidation.setVisible(false);
				btnSave.setVisible(false);
				biographyCancelButton.setVisible(false);
				lblDescCharLimit.setVisible(false);
				if (text.isEmpty()) {
					enableAddBioBtn("addBioBtn");
				}else{
					addBioBtn.setVisible(false);
				}

				userBio.setText(text);
				userBio.getElement().setAttribute("alt",text);
				userBio.getElement().setAttribute("title",text);

				String gender = "";
				if(profileDo.getGender()!=null&&profileDo.getGender().getGenderId()!=null) {
					gender = profileDo.getGender().getGenderId();
				}
				getUiHandlers().updateUserBiography(profileDo.getUser().getGooruUId(), text,profileDo.getUserType(),profileDo.getUser().getFirstName(),
						profileDo.getUser().getLastName(),gender);
			}

			@Override
			public void checkCharacterLimit(String text) {
				if (text.length() >= 500) {
					aboutUsCharacterValidation.setVisible(true);
				} else {
					aboutUsCharacterValidation.setVisible(false);
				}
			}
		};
		profileTextArea.getElement().getStyle().setWidth(795, Unit.PX);

		profileTextArea.getElement().setAttribute("maxlength", "500");

		setWidget(uiBinder.createAndBindUi(this));
		addCourseGradeBtn.setText(i18n.GL1075());
		addCourseGradeBtn.getElement().setAttribute("alt",i18n.GL1075());
		addCourseGradeBtn.getElement().setAttribute("title",i18n.GL1075());

		editPencil.getElement().setInnerHTML(i18n.GL0140());
		editPencil.getElement().setId("epnlEditPencil");
		editPencil.getElement().setAttribute("alt",i18n.GL0140());
		editPencil.getElement().setAttribute("title",i18n.GL0140());

		gradeText.setText(i18n.GL1076());
		gradeText.getElement().setId("lblGradeText");
		gradeText.getElement().setAttribute("alt",i18n.GL1076());
		gradeText.getElement().setAttribute("title",i18n.GL1076());

		courseLabel.setText(i18n.GL0574());
		courseLabel.getElement().setId("lblCourseLabel");
		courseLabel.getElement().setAttribute("alt",i18n.GL0574());
		courseLabel.getElement().setAttribute("title",i18n.GL0574());

		addCourseBtn.setText(i18n.GL0590());
		addCourseBtn.getElement().setAttribute("alt",i18n.GL0590());
		addCourseBtn.getElement().setAttribute("title",i18n.GL0590());

		courseMaxMsg.setText(i18n.GL0822());
		courseMaxMsg.getElement().setId("lblCourseMaxMsg");
		courseMaxMsg.getElement().setAttribute("alt",i18n.GL0822());
		courseMaxMsg.getElement().setAttribute("title",i18n.GL0822());

		saveBtn.setText(i18n.GL0141());
		saveBtn.getElement().setId("btnSaveBtn");
		saveBtn.getElement().setAttribute("alt",i18n.GL0141());
		saveBtn.getElement().setAttribute("title",i18n.GL0141());

		cancelBtn.setText(i18n.GL0142());
		cancelBtn.getElement().setId("lblCancelBtn");
		cancelBtn.getElement().setAttribute("alt",i18n.GL0142());
		cancelBtn.getElement().setAttribute("title",i18n.GL0142());

		addBioBtn.setText(i18n.GL1786());
		addBioBtn.getElement().setAttribute("alt",i18n.GL1786());
		addBioBtn.getElement().setAttribute("title",i18n.GL1786());

		aboutUsCharacterValidation.setText(i18n.GL0143());
		aboutUsCharacterValidation.getElement().setId("lblAboutUsCharacterValidation");
		aboutUsCharacterValidation.getElement().setAttribute("alt",i18n.GL0143());
		aboutUsCharacterValidation.getElement().setAttribute("title",i18n.GL0143());

		btnSave.setText(i18n.GL0141());
		btnSave.getElement().setId("btnSave");
		btnSave.getElement().setAttribute("alt",i18n.GL0141());
		btnSave.getElement().setAttribute("title",i18n.GL0141());

		biographyCancelButton.setText(i18n.GL0142());
		biographyCancelButton.getElement().setId("btnBiographyCancelButton");
		biographyCancelButton.getElement().setAttribute("alt",i18n.GL0142());
		biographyCancelButton.getElement().setAttribute("title",i18n.GL0142());

		profilePageViewMsg.setText(i18n.GL1078());
		profilePageViewMsg.getElement().setId("lblProfilePageViewMsg");
		profilePageViewMsg.getElement().setAttribute("alt",i18n.GL1078());
		profilePageViewMsg.getElement().setAttribute("title",i18n.GL1078());

		profilePageText.setText(i18n.GL1079());
		profilePageText.getElement().setId("lblProfilePageText");
		profilePageText.getElement().setAttribute("alt",i18n.GL1079());
		profilePageText.getElement().setAttribute("title",i18n.GL1079());

		profileOnButton.setText(i18n.GL0802());
		profileOnButton.getElement().setAttribute("alt",i18n.GL0802());
		profileOnButton.getElement().setAttribute("title",i18n.GL0802());

		profileOffButton.setText(i18n.GL0803());
		profileOffButton.getElement().setAttribute("alt",i18n.GL0803());
		profileOffButton.getElement().setAttribute("title",i18n.GL0803());

		contentTabVc.setText(i18n.GL1082());
		contentTabVc.getElement().setAttribute("alt",i18n.GL1082());
		contentTabVc.getElement().setAttribute("title",i18n.GL1082());

		courseGradeLbl.setText(i18n.GL1886());
		courseGradeLbl.getElement().setId("lblCourseGradeLbl");
		courseGradeLbl.getElement().setAttribute("alt",i18n.GL1886());
		courseGradeLbl.getElement().setAttribute("title",i18n.GL1886());

		errorImage.setTitle(i18n.GL1091_1());
		errorImage.getElement().setId("imgErrorImage");
		errorImage.getElement().setAttribute("title",i18n.GL1091_1());
		errorImage.setAltText(i18n.GL1091_1());
		errorImage.setUrl("images/404_message.png");

		profileOnContainerPanel.setVisible(false);
		profileOffContainerPanel.setVisible(false);
		loadingPanel.setVisible(false);
		addCourseGradeBtn.getElement().setId("btnAddCourseGrade");
		addCourseBtn.getElement().setId("btnAddCourse");
		editImageButton.getElement().setId("btnEditImage");
		editImageButton.setStyleName(CollectionCBundle.INSTANCE.css().profileEditImageButton());
		userCoursePopup.setVisible(false);
		profileOnButton.getElement().setAttribute("id", "btnProfileOn");
		profileOffButton.getElement().setAttribute("id", "btnProfileOff");
		contentTabVc.removeStyleName("pppTabsLi");
		contentTabVc.setStyleName("pppTabsLiActive");
		contentTabVc.getElement().setId("lnkContentTab");
		shareLinkFloPanel.setVisible(false);
		profilePageEditBioPanel.setVisible(false);
		editImageButton.setVisible(false);
		addCourseGradeBtn.setVisible(false);
		enableAddBioBtn("userBio");
		addBioBtn.getElement().setId("btnBioEdit");
		//added in 6.4
		collectionsTabVc.setLabel(i18n.GL1888());
		followingTabVc.setLabel(i18n.GL1895());
		followersTabVc.setLabel(i18n.GL1896());
		tagTabVc.setLabel(i18n.GL1897());

		collectionsTabVc.getElement().setId("collections");
		followingTabVc.getElement().setId("following");
		followersTabVc.getElement().setId("followers");
		tagTabVc.getElement().setId("tags");
		collectionsTabVc.setStyleName("tabAlign");
		followingTabVc.setStyleName("tabAlign");
		followersTabVc.setStyleName("tabAlign");
		tagTabVc.setStyleName("tabAlign");

		hpnlQuestion.getElement().setAttribute("style", "margin-top: 12px;");

		followButton.setText(i18n.GL1935());
		followButton.getElement().setAttribute("alt",i18n.GL1935());
		followButton.getElement().setAttribute("title",i18n.GL1935());

		FollowingButtonBlue.setText(i18n.GL1895());
		FollowingButtonBlue.getElement().setAttribute("alt",i18n.GL1895());
		FollowingButtonBlue.getElement().setAttribute("title",i18n.GL1895());

		FollowingButtonBlue.setVisible(false);
		FollowingButtonBlue.addStyleName("followingBtn");
		FollowingButtonBlue.addMouseOverHandler(new MouseOverOnUnFollowingButton());
		FollowingButtonBlue.addMouseOutHandler(new MouseOutOnUnFollowingButton());
		followButton.getElement().setAttribute("style", "width: 100px;");
		//end for 6.4
		userCoursePopup.setVisible(false);
		if(AppClientFactory.getLoggedInUser().getConfirmStatus()==1){
			profileOnButton.addClickHandler(new ProfileOnClickEvent());
		 }else{
			profileOnButton.addMouseOverHandler(new MouseOverHandler() {

				@Override
				public void onMouseOver(MouseOverEvent event) {
					toolTipPopupPanel.clear();
					toolTipPopupPanel.setWidget(new GlobalToolTip());
					toolTipPopupPanel.setStyleName("");
					toolTipPopupPanel.setPopupPosition(profileOnButton.getElement().getAbsoluteLeft()+10, profileOnButton.getElement().getAbsoluteTop()+4);
					toolTipPopupPanel.show();
				}
			});
			profileOnButton.addMouseOutHandler(new MouseOutHandler() {

				@Override
				public void onMouseOut(MouseOutEvent event) {
					toolTipPopupPanel.hide();
				}
			});

		}
		contentTabVc.getElement().getStyle().setCursor(Cursor.POINTER);

		userCoursePopup.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				isShowing=true;

			}
		});
		ClickHandler eve1=new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(userCoursePopup.isVisible() && isShowing && enableEdit){
						userCoursePopup.setVisible(true);
				}else{
				//	userCoursePopup.setVisible(false);
				}
				isShowing=false;
			}
		};
		RootPanel.get().addDomHandler(eve1, ClickEvent.getType());

		profileOnContainerPanel.getElement().setId("pnlProfileOnContainerPanel");
		profileImageContainer.getElement().setId("epnlProfileImageContainer");
		userProfilePic.getElement().setId("imgUserProfilePic");
		userName.getElement().setId("lblUserName");
		roleTxt.getElement().setId("lblRoleTxt");
		profilePageViewMsg.getElement().setId("lblProfilePageViewMsg");
		gooruProfileOnOffContainer.getElement().setId("pnlGooruProfileOnOffContainer");
		profileVisiblityMessage.getElement().setId("htmlProfileVisiblityMessage");
		gooruSocialButtonsContainer.getElement().setId("pnlGooruSocialButtonsContainer");
		socialButtonContainer.getElement().setId("pnlSocialButtonContainer");
		followButton.getElement().setId("btnFollowButton");
		FollowingButtonBlue.getElement().setId("btnFollowingButtonBlue");
		userMetadata.getElement().setId("epnlUserMetadata");
		metaDataContainer.getElement().setId("pnlMetaDataContainer");
		userGradeList.getElement().setId("pnlUserGradeList");
		moreGradeCourseLbl.getElement().setId("fpnlMoreGradeCourseLbl");
		userCourseList.getElement().setId("pnlUserCourseList");
		moreCourseLbl.getElement().setId("fpnlMoreCourseLbl");
		userCoursePopup.getElement().setId("epnlUserCoursePopup");
		KinderGarten.getElement().setId("fpnlKinderGarten");
		gradeTopList.getElement().setId("fpnlGradeTopList");
		higherEducation.getElement().setId("fpnlHigherEducation");
		gradeMiddleList.getElement().setId("fpnlGradeMiddleList");
		courseData.getElement().setId("fpnlCourseData");
		collectionCourseLstPanel.getElement().setId("fpnlCollectionCourseLstPanel");
		coursesPanel.getElement().setId("fpnlCoursesPanel");
		bioMainContainer.getElement().setId("pnlBioMainContainer");
		userBio.getElement().setId("lblUserBio");
		profilePageEditBioPanel.getElement().setId("pnlProfilePageEditBioPanel");
		profileDescriptionlabel.getElement().setId("pnlProfileDescriptionlabel");
		profileTextArea.getElement().setId("profilePageDescUcProfileTextArea");
		noAboutUsContainer.getElement().setId("fpnlNoAboutUsContainer");
		pencilTextAreaImage.getElement().setId("epnlPencilTextAreaImage");
		mainContainer.getElement().setId("pnlMainContainer");
		userLibraryMessage.getElement().setId("lblUserLibraryMessage");
		hpnlQuestion.getElement().setId("pnlHpnlQuestion");
		libraryMessage.getElement().setId("lblLibraryMessage");
		loadingPanel.getElement().setId("pnlLoadingPanel");
		contentview.getElement().setId("pnlContentview");
		profilePageLibraryView.getElement().setId("profilePageLibraryViewProfilePageLibraryView");
		shareLinkFloPanel.getElement().setId("pnlShareLinkFloPanel");
		followingContainer.getElement().setId("pnlFollowingContainer");
		tagResourceContainer.getElement().setId("pnlTagResourceContainer");
		profileOffContainerPanel.getElement().setId("pnlProfileOffContainerPanel");

		String value = StringUtil.generateMessage(i18n.GL2103(), "500");
		lblDescCharLimit.setText(value);
		StringUtil.setAttributes(lblDescCharLimit.getElement(), "lblDescCharLimit", value, value);
		profileMainContainer.getElement().setId("gooruProfilePage");
	}

	public class ProfileOnClickEvent implements ClickHandler  {

		@Override
		public void onClick(ClickEvent event) {
			MixpanelUtil.Click_On();
			enableProfileButton(true);
			shareDo.setShareType("public");
			socialView = new SocialShareView(shareDo){
				public void triggerShareDataEvent(String shareType,boolean confirmStaus){
					PlayerDataLogEvents.triggerItemShareDataLogEventForProfile(AppClientFactory.getLoggedInUser().getGooruUId(), "", "", "", "", PlayerDataLogEvents.PROFILE, shareType, confirmStaus, "", AppClientFactory.getLoggedInUser().getGooruUId(), "profile");
				}
			};
			socialButtonContainer.clear();
			socialButtonContainer.add(socialView);
			gooruSocialButtonsContainer.getElement().getStyle().setOpacity(1.0);

			//update profile setting
			getUiHandlers().updateProfileVisibilitySetting("true");
		}


	}
	@Override
	public void isFollow(String isFollow) {
		this.isFollowUser=isFollow;

	}
	@UiHandler("profileOffButton")
	public void onClickOffButton(ClickEvent event) {
		MixpanelUtil.Click_Off();
		enableProfileButton(false);
		shareDo.setShareType("private");
		socialView = new SocialShareView(shareDo);
		socialButtonContainer.clear();
		socialButtonContainer.add(socialView);
		gooruSocialButtonsContainer.getElement().getStyle().setOpacity(0.6);

		//update profile setting
		getUiHandlers().updateProfileVisibilitySetting("false");
	}

	void enableProfileButton(boolean isToEnabled) {
		if (isToEnabled) {
			this.profileOnStatus = "true";
			MixpanelUtil.Click_Profile_On();
			profileOnButton.setStyleName("publicProfileOnButtonActive");
			profileOnButton.removeStyleName("publicProfileOnButtonDeActive");
			profileOffButton.setStyleName("publicProfileOffButtonsDeActive");
			profileOffButton.removeStyleName("publicProfileOffButtonsActive");
			setPublicShareDo("public");
		} else {
			this.profileOnStatus = "false";
			MixpanelUtil.Click_Profile_Off();
			profileOffButton.setStyleName("publicProfileOffButtonsActive");
			profileOffButton.removeStyleName("publicProfileOffButtonsDeActive");
			profileOnButton.setStyleName("publicProfileOnButtonDeActive");
			profileOnButton.removeStyleName("publicProfileOnButtonActive");
			setPublicShareDo("private");
		}
	}

	public void setInitialProfileStatus(String isEnabled) {
		if(isEnabled == "true") {
			setPublicShareDo("public");
		} else if(isEnabled == "false"){
			if(enableEdit==true) {
				setPublicShareDo("private");
			} else {
				setPublicShareDo("public");
			}
		}
	}

	private void setPublicShareDo(String privatePublic) {
		try {
			if(shareDo != null){
				shareDo.setShareType(privatePublic);
				socialView = new SocialShareView(shareDo){
					public void triggerShareDataEvent(String shareType,boolean confirmStaus){
						PlayerDataLogEvents.triggerItemShareDataLogEventForProfile(AppClientFactory.getLoggedInUser().getGooruUId(), "", "", "", "", PlayerDataLogEvents.PROFILE, shareType, confirmStaus, "", AppClientFactory.getLoggedInUser().getGooruUId(), "profile");
					}
				};
				socialButtonContainer.clear();
				socialButtonContainer.add(socialView);
				if(privatePublic.equalsIgnoreCase("private")) {
					gooruSocialButtonsContainer.getElement().getStyle().setOpacity(0.6);
				} else {
					gooruSocialButtonsContainer.getElement().getStyle().setOpacity(1.0);
				}
			}

		} catch (Exception e) {
			 AppClientFactory.printSevereLogger("profilePageView setPublicShareDo :"+e);
		}


	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (content != null) {
			if (slot == ProfilePageUiHandlers.TYPE_FOLLOWING_VIEW) {
				followingContainer.clear();
				//followingContainer().setVisible(false);
				if(content!=null){
					followingContainer.add(content);
				}
			}

		}
	}

	@Override
	public void setProfileData(final ProfileDo profileDo) {
		if(profileDo.getAboutMe()==null) {
			profileDo.setAboutMe("");
		}
		this.profileDo = profileDo;
		userCoursePopup.setVisible(false);
		if(profileDo.getUserType() != null && profileDo.getUserType().length() > 1)
		{
			profileDo.setUserType(profileDo.getUserType().substring(0,1).toUpperCase()+profileDo.getUserType().substring(1, profileDo.getUserType().length()));
		}
		roleTxt.setText(profileDo.getUserType());
		roleTxt.getElement().setAttribute("alt",profileDo.getUserType());
		roleTxt.getElement().setAttribute("title",profileDo.getUserType());

		profileTextArea.cancel();
		btnSave.setVisible(false);
		biographyCancelButton.setVisible(false);
		lblDescCharLimit.setVisible(false);
		moreGradeCourseLbl.clear();
		moreCourseLbl.clear();
		noCollectionMsgPanel.setText(profileDo.getUser().getUsernameDisplay()+" "+i18n.GL1083());
		noCollectionMsgPanel.getElement().setAttribute("alt",profileDo.getUser().getUsernameDisplay()+" "+i18n.GL1083());
		noCollectionMsgPanel.getElement().setAttribute("title",profileDo.getUser().getUsernameDisplay()+" "+i18n.GL1083());

		userName.setText(profileDo.getUser().getUsernameDisplay());
		userName.getElement().setAttribute("alt",profileDo.getUser().getUsernameDisplay());
		userName.getElement().setAttribute("title",profileDo.getUser().getUsernameDisplay());

		userLibraryMessage.setText(profileDo.getUser().getUsernameDisplay()+i18n.GL_GRR_ALPHABET_APOSTROPHE()+" "+i18n.GL1888());
		userLibraryMessage.getElement().setAttribute("alt",profileDo.getUser().getUsernameDisplay()+i18n.GL_GRR_ALPHABET_APOSTROPHE()+" "+i18n.GL1888());
		userLibraryMessage.getElement().setAttribute("title",profileDo.getUser().getUsernameDisplay()+i18n.GL_GRR_ALPHABET_APOSTROPHE()+" "+i18n.GL1888());

		libraryMessage.setText(i18n.GL1792());
		libraryMessage.getElement().setAttribute("alt",i18n.GL1792());
		libraryMessage.getElement().setAttribute("title",i18n.GL1792());

		profileVisiblityMessage.setHTML(i18n.GL1793()+"<br/><br/>"+i18n.GL1794());
		profileVisiblityMessage.getElement().setAttribute("alt",i18n.GL1793()+"<br/><br/>"+i18n.GL1794());
		profileVisiblityMessage.getElement().setAttribute("title",i18n.GL1793()+"<br/><br/>"+i18n.GL1794());

		userBio.setText(profileDo.getAboutMe());
		userBio.getElement().setAttribute("alt",profileDo.getAboutMe());
		userBio.getElement().setAttribute("title",profileDo.getAboutMe());

		profileTextArea.setText(profileDo.getAboutMe());
		profileTextArea.getElement().setAttribute("alt",profileDo.getAboutMe());
		profileTextArea.getElement().setAttribute("title",profileDo.getAboutMe());

		profileImageUrl=profileDo.getUser().getProfileImageUrl() + "?p="+ Math.random();
		userProfilePic.setUrl(profileImageUrl);
		editImageButton.setText(i18n.GL0800());
		StringUtil.setAttributes(editImageButton.getElement(), "editImageButton", i18n.GL0800(), i18n.GL0800());
		userProfilePic.setAltText(profileDo.getUser().getUsername());
		userProfilePic.setTitle(profileDo.getUser().getUsername());
		userProfilePic.getElement().getStyle().setHeight(174, Unit.PX);
		userProfilePic.getElement().getStyle().setWidth(175, Unit.PX);
		userProfilePic.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				userProfilePic.setUrl("images/profilepage/user-profile-pic.png");
				editImageButton.setText(i18n.GL1087());
				StringUtil.setAttributes(editImageButton.getElement(), "editImageButton", i18n.GL1087(), i18n.GL1087());
				userProfilePic.setAltText(profileDo.getUser().getUsername());
				userProfilePic.setTitle(profileDo.getUser().getUsername());
			}
		});
		getUiHandlers().setShareView();
		getUiHandlers().getTaxonomyData();
		gooruSocialButtonsContainer.setVisible(true);
		String userId=AppClientFactory.getPlaceManager().getRequestParameter("id");
		boolean enable;
		if(userId.equalsIgnoreCase(AppClientFactory.getLoggedInUser().getGooruUId())) {
			enable=true;
		}else{
			enable=false;
		}
		setUserGradeList(profileDo.getGrade(), enable);
		setUserCourseList(profileDo.getCourses(), enable);
		setMetaDataContainerWidth("grade");
		setMetaDataContainerWidth("course");
		setAddGradeCourseBtnVisibility();
		getEnableWidget(enableEdit,profileDo.getAboutMe(),profileDo.getCourses());
		if(profileDo.getUser().getMeta().getSummary().getCollection()==1||profileDo.getUser().getMeta().getSummary().getCollection()==0){
			collectionsTabVc.setLabel(i18n.GL2173());
		}
		else{
			collectionsTabVc.setLabel(i18n.GL1888());
		}
//		collectionsTabVc.setLabelCount(profileDo.getUser().getMeta().getSummary().getCollection()+"");
		followingTabVc.setLabelCount(profileDo.getUser().getMeta().getSummary().getFollowing()+"");
		followersTabVc.setLabelCount("");

		followersTabVc.setLabelCount(profileDo.getUser().getMeta().getSummary().getFollowers()+"");
		//tagTabVc.setLabelCount(profileDo.getUser().getMeta().getSummary().getTags()+"");
		getUiHandlers().getFollowerData();
		getUiHandlers().getFollwingData();
		getUiHandlers().getUserAddedContentTagSummary(AppClientFactory.getPlaceManager().getRequestParameter("id"),"0","10");
		buttonDisable();
		/*String tabValue = AppClientFactory.getPlaceManager().getRequestParameter("tab");
		if(tabValue!=null || "".equalsIgnoreCase("tabValue")){
	*/		setTab(collectionsTabVc);
		//}



	}

	private void setUserCourseList(Set<ProfileCodeDo> codeList, boolean enable) {
		profileDo.setCourses(codeList);
		userCourseList.clear();
		int count = 0;

		List<String> moreCourseLbls = new ArrayList<String>();
		for (ProfileCodeDo profileCodeDo : codeList) {
			if(count<2) {
				Label courseLabel = new Label(profileCodeDo.getCode().getLabel());
				courseLabel.setStyleName(CollectionCBundle.INSTANCE.css().userCourseName());
				if(enable){
					courseLabel.addClickHandler(new OnGradeEditImageClick());
				}else{
					courseLabel.getElement().getStyle().setCursor(Cursor.DEFAULT);
				}
				userCourseList.add(courseLabel);
				count++;
			} else {
				moreCourseLbls.add(profileCodeDo.getCode().getLabel());
			}
		}
		if (moreCourseLbls.size() > 0){
			userCourseList.setVisible(true);
			moreCourseLbl.setVisible(true);
			renderExtraGradeCourse(moreCourseLbls, "course");
		}else{
//			userCourseList.setVisible(false);
//			moreCourseLbl.setVisible(false);
		}
	}

	private void setUserGradeList(String grade, boolean enable) {
		profileDo.setGrade(grade);
		userGradeList.clear();
		setGradeList(grade);
		if(grade!=null) {
			userGradeList.setVisible(true);
			moreGradeCourseLbl.setVisible(true);
			String[] grades = grade.split(",");
			List<String> moreGradeCourseLbls = new ArrayList<String>();
			int gradeLength = grades.length;
			for(int i = 0; i < gradeLength; i ++) {
				if(!grades[i].isEmpty()) {
					moreGradeCourseLbls.add(grades[i]);
				}
			}

			if(grade!=null && !grade.isEmpty()) {
				boolean isKinderGartnen = false;
				boolean isHigherEducation = false;
				if(grade.contains(i18n.GL0850())) {
					isKinderGartnen = true;
					/*Label gradeLabel = new Label(i18n.GL0850);
					moreGradeCourseLbls.remove(i18n.GL0850);
					gradeLabel.setStyleName(CollectionCBundle.INSTANCE.css().userNumber());
					gradeLabel.addClickHandler(new OnGradeEditImageClick());
					userGradeList.add(gradeLabel);*/
				}
				if(grade.contains("Higher Education")) {
					isHigherEducation = true;
				}
				Set set= new HashSet();
				gradeLength = 0;
				if(isKinderGartnen&&grades.length>2) {
					gradeLength = 2;
					isHigherEducation = false;
				} else if(!isKinderGartnen&&grades.length>3) {
					gradeLength = 3;
					isHigherEducation = false;
				} else {
					gradeLength = grades.length;
				}
				for(int i = 0; i < gradeLength; i ++) {
					if(!grades[i].isEmpty()) {
						set.add(grades[i]);
					}
				}
				Iterator gradeLbl = set.iterator();
				while(gradeLbl.hasNext()) {
					String label = gradeLbl.next().toString();
					moreGradeCourseLbls.remove(label);
					label = concatenateGradeTxt(label);
					Label gradeLabel = new Label(label);
					gradeLabel.setStyleName(CollectionCBundle.INSTANCE.css().userNumber());
					if(enable){
						gradeLabel.addClickHandler(new OnGradeEditImageClick());
					}else{
						gradeLabel.getElement().getStyle().setCursor(Cursor.DEFAULT);
					}
					userGradeList.add(gradeLabel);
				}
				if(isHigherEducation == true) {
					/*Label gradeLabel = new Label(i18n.GL0169);
					moreGradeCourseLbls.remove(i18n.GL0169);
					gradeLabel.setStyleName(CollectionCBundle.INSTANCE.css().userNumber());
					gradeLabel.addClickHandler(new OnGradeEditImageClick());
					userGradeList.add(gradeLabel);*/
				}
				renderExtraGradeCourse(moreGradeCourseLbls,"grade");
			}
		}else{
			userGradeList.setVisible(false);
			moreGradeCourseLbl.setVisible(false);
		}
	}

	public void setMetaDataContainerWidth(String type) {
			int gradeCount = 0;
			int limit = 0;
			Iterator<Widget> gradeWidgets;
			if(type.equals("grade")) {
				gradeWidgets = userGradeList.iterator();
				limit = 2;
			} else {
				gradeWidgets = userCourseList.iterator();
				limit = 1;
			}
			while (gradeWidgets.hasNext()) {
				Widget widget = gradeWidgets.next();
				String commaSeparator = "";
				if (widget instanceof Label) {
					String text = ((Label) widget).getText();
					if(gradeCount<limit) {
						commaSeparator = i18n.GL_GRR_COMMA();
					}
					((Label) widget).setText(text+commaSeparator);
				}
				gradeCount++;
			}
	}

	public void renderExtraGradeCourse(List<String> datas, String type) {
		if (datas.size() > 0) {
			FlowPanel toolTipwidgets = new FlowPanel();
			for (int count = 0; count < datas.size(); count++) {
				String text = concatenateGradeTxt(datas.get(count));
				Label label = new Label(text);
				label.setStyleName(SearchResultWrapperCBundle.INSTANCE.css().moreMetaLbl());
				toolTipwidgets.add(label);
			}
			DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(i18n.GL_SPL_PLUS() + datas.size() +" "+i18n.GL1152()), toolTipwidgets);
			toolTipUc.setStyleName(SearchResultWrapperCBundle.INSTANCE.css().blueLinkPad());
			if(type.equals("grade")) {
				moreGradeCourseLbl.add(toolTipUc);
			} else {
				moreCourseLbl.add(toolTipUc);
			}
		}
	}

	public void setContentTabVisibility(boolean isVisible) {
		if (isVisible) {
			setContentViewCss();
			contentview.getElement().getStyle().setDisplay(Display.BLOCK);
			shareLinkFloPanel.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	private void setContentViewCss() {
		contentTabVc.removeStyleName("tabsLi");
		contentTabVc.setStyleName("tabsLiActive");
	}

	@UiHandler("contentTabVc")
	public void onContentTabVc(ClickEvent event) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", AppClientFactory.getPlaceManager().getRequestParameter("id"));
		params.put("user", AppClientFactory.getPlaceManager().getRequestParameter("user"));
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.PROFILE_PAGE, params);
	}

	@Override
	public void setContentItemData(List<CollectionItemDo> collectionItemDo) {

	}

	@Override
	public void clearContentItemData() {

	}

	@Override
	public void setMetaData(CollectionItemDo collectionItemDo) {

	}

	@Override
	public void setShareData(ProfileDo profileDo, List<String> shortenUrl,
			String profileUrl) {
		shareDo = new SocialShareDo();
		shareDo.setBitlylink(shortenUrl.get(0));
		shareDo.setRawUrl(shortenUrl.get(1));
		shareDo.setTitle(profileDo.getUser().getUsername());
		shareDo.setDescription(i18n.GL1085_3());
		shareDo.setThumbnailurl(profileImageUrl);
		shareDo.setCategoryType("profile");
		shareDo.setPppBitlylink(profileUrl);
		shareDo.setOnlyIcon(true);
		shareDo.setIsSearchShare(false);
		setInitialProfileStatus(profileOnStatus);
	}

	@Override
	public HTMLPanel getOnProfileContainer() {
		return profileOnContainerPanel;
	}

	@Override
	public HTMLPanel getOffProfileContainer() {
		return profileOffContainerPanel;
	}

	@Override
	public void setCollectionData(CollectionItemDo collectionItemDo) {
		/*PPPCollectionResult pppCollectionResult = new PPPCollectionResult(collectionItemDo);
		pppCollectionResult.openDisclosurePanel();*/
		displayFooter();
	}

	public void displayFooter() {
		footerUc.setFooterWidth();
		footerUc.getElement().getStyle().setMarginLeft(-119, Unit.PX);
		footerUc.getElement().getStyle().setDisplay(Display.BLOCK);
	}

	@Override
	public void showProfileView(boolean isVisible) {
		userName.setText("");
		userName.getElement().setAttribute("alt","");
		userName.getElement().setAttribute("title","");

		userBio.setText("");
		userBio.getElement().setAttribute("alt","");
		userBio.getElement().setAttribute("title","");

		userProfilePic.setUrl("");
		shareLinkFloPanel.clear();
		socialButtonContainer.clear();
		profileTextArea.setText("");
		profileTextArea.getElement().setAttribute("alt","");
		profileTextArea.getElement().setAttribute("title","");
	}

	private class ShowImageUploadWidget implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (enableEdit){
				MixpanelUtil.Click_On_EditMyImage();
				getUiHandlers().showUploadImageWidget();
				editImageButton.getElement().getStyle().clearDisplay();
			}
		}
	}

	private class ShowEditButton implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {
			if(enableEdit){
				editImageButton.getElement().getStyle().setDisplay(Display.BLOCK);
			}
		}
	}

	private class HideEditButton implements MouseOutHandler {
		@Override
		public void onMouseOut(MouseOutEvent event) {
				editImageButton.getElement().getStyle().clearDisplay();
		}
	}

	@Override
	public void setUserProfileImage(String imageUrl) {
		profileImageUrl=imageUrl + "?" + Math.random();
		userProfilePic.setUrl(profileImageUrl);
		editImageButton.setText(i18n.GL0800());
		StringUtil.setAttributes(editImageButton.getElement(), "editImageButton", i18n.GL0800(), i18n.GL0800());
		shareDo.setThumbnailurl(profileImageUrl);
		if(socialView!=null){
			socialView.setShareDo(shareDo);
		}
	}

	@Override
	public void enableEditableData(String profileOnStatus) {
		this.profileOnStatus = profileOnStatus;
		isProfileAdmin = true;
		// Profile Image Edit option
		profileImageContainer.add(editImageButton);

		if(profileDo.getUser().getProfileImageUrl()==null) {
			editImageButton.setText(i18n.GL1087());
			editImageButton.getElement().setAttribute("alt",i18n.GL1087());
			editImageButton.getElement().setAttribute("title",i18n.GL1087());
		}
		courseData.getElement().getStyle().setWidth(324, Unit.PX);
		saveBtn.getElement().getStyle().setFloat(Float.LEFT);
		saveBtn.getElement().setAttribute("id", "btnSave");
		cancelBtn.getElement().setAttribute("id", "btnCancel");

		biographyCancelButton.getElement().setAttribute("id", "btnCancelEditBio");

		editImageButton.addClickHandler(new ShowImageUploadWidget());
		profileImageContainer.addMouseOverHandler(new ShowEditButton());
		profileImageContainer.addMouseOutHandler(new HideEditButton());
		if (profileDo.getAboutMe()!=null && !profileDo.getAboutMe().equalsIgnoreCase("")){
			enableAddBioBtn("other");
		}else{
			enableAddBioBtn("addBioBtn");
		}
		gooruSocialButtonsContainer.setVisible(true);
		// On/Off button and Edit My Page Button
		//editMyPage.setVisible(true);
		profilePageEditBioPanelUpdate();
		if (profileOnStatus == "true" || profileOnStatus.equalsIgnoreCase("true")) {
			enableProfileButton(true);
		} else {
			enableProfileButton(false);
		}
	}

	private void profilePageEditBioPanelUpdate() {
		btnSave.getElement().setAttribute("id", "btnSaveEditBio");
		biographyCancelButton.getElement().setAttribute("id", "btnCancelEditBio");

		aboutUsCharacterValidation.setWidth("100%");
		aboutUsCharacterValidation.setVisible(false);
		noAboutUsContainer.setVisible(false);
		profileTextArea.getBiographyEditImage().setVisible(false);
		profileTextArea.getBiographyEditImage().getElement().setAttribute("style", "none");
		btnSave.setVisible(false);
		biographyCancelButton.setVisible(false);
		lblDescCharLimit.setVisible(false);
		pencilTextAreaImage.setVisible(false);
		noAboutUsContainer.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if (enableEdit){
					pencilTextAreaImage.setVisible(true);
				}
			}
		});
		noAboutUsContainer.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				if (enableEdit){
					pencilTextAreaImage.setVisible(false);
				}
			}
		});
		pencilTextAreaImage.addClickHandler(new OnPencilImageClick());
		profileTextArea.getBiographyEditImage().addClickHandler(new OnEditImageClick());
		profileTextArea.getBiographyEditImage().setVisible(false);
		profileDescriptionlabel.addClickHandler(new OnEditImageClick());
		profileDescriptionlabel.addMouseOverHandler(new hideEditPencil());
		profileDescriptionlabel.addMouseOutHandler(new showEditPencil());
		editPencil.setVisible(false);
		userCoursePopup.setVisible(false);
		editPencil.addClickHandler(new OnGradeEditImageClick());
		addCourseGradeBtn.getElement().getStyle().setWidth(163, Unit.PX);
		addCourseGradeBtn.getElement().getStyle().setMarginTop(-3, Unit.PX);
		addCourseGradeBtn.addClickHandler(new OnGradeEditImageClick());
		editPencil.getElement().getStyle().setMarginTop(-3, Unit.PX);
		editPencil.getElement().getStyle().setMarginLeft(0, Unit.PX);
		userMetadata.addMouseOverHandler(new showGradeEditPencil());
		userMetadata.addMouseOutHandler(new hideGradeEditPencil());
		getProfileBiographyEditUC().setText(profileDo.getAboutMe());
	}

	private class OnEditImageClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (enableEdit){
				btnSave.setVisible(true);
				biographyCancelButton.setVisible(true);
				lblDescCharLimit.setVisible(true);
				profileTextArea.switchToEdit();
			}
		}
	}

	private class OnPencilImageClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if(enableEdit){
				profileTextArea.switchToEdit();
				noAboutUsContainer.setVisible(false);
				btnSave.setVisible(true);
				biographyCancelButton.setVisible(true);
				lblDescCharLimit.setVisible(true);
			}
		}
	}

	public class hideEditPencil implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {
			if (enableEdit){
				profileTextArea.getBiographyEditImage().setVisible(true);
			}
		}
	}

	public class showEditPencil implements MouseOutHandler {
		@Override
		public void onMouseOut(MouseOutEvent event) {
			if (enableEdit){
				profileTextArea.getBiographyEditImage().setVisible(false);
			}
		}
	}

	private class OnGradeEditImageClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (enableEdit)
					clickGradeCourseEditBtn();
					isGradeCourseBtnClick = true;
				userCoursePopup.setVisible(true);
	        	isShowing=true;
		}
	}

	public class showGradeEditPencil implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {
			if (enableEdit)
				if(userCourseList.getWidgetCount() > 0 || userGradeList.getWidgetCount() > 0) {
					editPencil.setVisible(true);
				}
			}
	}

	public class hideGradeEditPencil implements MouseOutHandler {
		@Override
		public void onMouseOut(MouseOutEvent event) {
			if (enableEdit)
				editPencil.setVisible(false);
		}
	}

	@UiHandler("addBioBtn")
	public void OnClickAddBioButton(ClickEvent event) {
		if(enableEdit){
			enableAddBioBtn("other");
			btnSave.setVisible(true);
			biographyCancelButton.setVisible(true);
			lblDescCharLimit.setVisible(true);
			profileTextArea.switchToEdit();
		}
	}

	@UiHandler("saveBtn")
	public void OnClickGradeEditButton(ClickEvent event) {
		if (enableEdit){
			AppClientFactory.getInjector().getUserService().getUserProfileV2Details(profileDo.getUser().getGooruUId(), USER_META_ACTIVE_FLAG, new SimpleAsyncCallback<ProfileDo>(){
				@Override
				public void onSuccess(ProfileDo result) {
					setUserGradeList(result.getGrade(),true);
					setUserCourseList(result.getCourses(),true);
					setMetaDataContainerWidth("grade");
					setMetaDataContainerWidth("course");
					setAddGradeCourseBtnVisibility();
				}
			});
			moreGradeCourseLbl.clear();
			moreCourseLbl.clear();
			userCoursePopup.setVisible(false);
		}
	}

	private void setAddGradeCourseBtnVisibility() {
		if(userGradeList.getWidgetCount() > 0 || userCourseList.getWidgetCount() > 0) {
			addCourseGradeBtn.setVisible(false);
			editPencil.setVisible(true);
		} else {
			addCourseGradeBtn.setVisible(true);
			editPencil.setVisible(false);
		}
	}
	@UiHandler("cancelBtn")
	public void OnCancelGradeEditButton(ClickEvent event) {
		userCoursePopup.setVisible(false);
	}

	@UiHandler("btnSave")
	public void OnClickbiographySaveButton(ClickEvent event) {
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("text", profileTextArea.geteditTextBox().getText());
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean value) {
				if(value){
					SetStyleForProfanity.SetStyleForProfanityForTextArea(profileTextArea.geteditTextBox(), profileTextArea.getErrorLabel(), value);
				}else{
					if (enableEdit){
						noAboutUsContainer.setVisible(false);
						aboutUsCharacterValidation.setVisible(false);
						MixpanelUtil.Click_On_Save();
						profileTextArea.switchToLabel();
					}
				}
			}
		});
	}

	@UiHandler("biographyCancelButton")
	public void OnClickBiographyCancelButton(ClickEvent event) {
		//disableContentAndSetOldContent(profileDo.getAboutMe());
		aboutUsCharacterValidation.setVisible(false);
		btnSave.setVisible(false);
		biographyCancelButton.setVisible(false);
		lblDescCharLimit.setVisible(false);
		profileTextArea.cancel();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("text", profileTextArea.geteditTextBox().getText());
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean value) {
				profileTextArea.getErrorLabel().setVisible(false);
			}
		});
	}

	@Override
	public ProfilePageDescriptionEditUc getProfileBiographyEditUC() {
		return profileTextArea;
	}

	/**
	 * separate the view according to grade level of the collection
	 */
	public void setGradeList(String grades) {
		KinderGarten.clear();
		higherEducation.clear();
		gradeTopList.clear();
		gradeMiddleList.clear();
		KinderGarten.add(new ProfilePageGradeLabel(i18n.GL0850(), profileDo));
		higherEducation.add(new ProfilePageGradeLabel(i18n.GL0169(),profileDo));
		for (int i = 1; i <= 12; i++) {
			if (i <= 6) {
				gradeTopList.add(new ProfilePageGradeLabel(i + "", profileDo));
			}
			if (i >= 7) {
				gradeMiddleList.add(new ProfilePageGradeLabel(i + "", profileDo));
			}
		}
	}

	/**
	 * to display message if course exceeds more than five
	 */
	public void courseMaxShow() {
		courseMaxMsg.setStyleName(CollectionCBundle.INSTANCE.css().profileCourseMaxMsgShow());
		new FadeInAndOut(courseMaxMsg.getElement(), 10000, 10000);
	}

	/**
	 * to hide message if course exceeds less than five
	 */
	public void courseMaxHide() {
		courseData.setStyleName(CollectionCBundle.INSTANCE.css().floatLeft());
		collectionCourseLst.setStyleName(CollectionCBundle.INSTANCE.css()
				.infoTextBox());
		addCourseBtn.setStyleName(CollectionCBundle.INSTANCE.css()
				.infoAddButton());
		courseMaxMsg.setStyleName("courseMaxMsg");
		courseMaxMsg.getElement().getStyle().setFloat(Float.LEFT);
	}

	@Override
	public void setCourseList(List<LibraryCodeDo> libraryCode) {
		collectionCourseLst = new GroupedListBox();
		collectionCourseLst.addStyleName(CollectionCBundle.INSTANCE.css()
				.infoTextBox());
		collectionCourseLst.addItem(" What course(s) do you teach? ", "-1");
		if (libraryCode != null) {
			for (LibraryCodeDo libraryCodeDo : libraryCode) {
				for (LibraryCodeDo liCodeDo : libraryCodeDo.getNode()) {
					collectionCourseLst.addItem(libraryCodeDo.getLabel() + "|"
							+ liCodeDo.getLabel(), liCodeDo.getCodeId() + "");
				}
			}
		}
		collectionCourseLstPanel.clear();
		collectionCourseLstPanel.add(collectionCourseLst);
	}

	/**
	 * New course is created for collection
	 *
	 * @param courseLabel
	 *            the course of the label widget which is used to get all
	 *            courses
	 * @param courseCode
	 *            the course of the course code, an absolute course code will be
	 *            used to create a new course
	 * @return the label of all course created for the collection.
	 */
	protected CloseLabel createCourseLabel(final String courseLabel,
			final String courseCode) {
		CloseLabel closeLabel = new CloseLabel(courseLabel) {
			@Override
			public void onCloseLabelClick(ClickEvent event) {
				this.removeFromParent();
				CodeDo codeDo = new CodeDo();
				codeDo.setCodeId(Integer.parseInt(courseCode));
				getUiHandlers().deleteCourse(codeDo);
			}
		};
		closeLabel.addStyleName("margin5");
		return closeLabel;
	}

	/**
	 * Adding new course for the collection , will check it has more than five
	 * courses and the selected course is repeated or not
	 *
	 * @param clickEvent
	 *            specifies event type
	 */
	@UiHandler("addCourseBtn")
	public void onAddCourseClick(ClickEvent clickEvent) {
		if (enableEdit){
			if (coursesPanel.getWidgetCount() < 6) {
				final String courseCodeLabel = collectionCourseLst
						.getItemText(collectionCourseLst.getSelectedIndex());
				final String courseCode = collectionCourseLst
						.getValue(collectionCourseLst.getSelectedIndex());
				if (collectionCourseLst.getSelectedIndex() == 0) {
					return;
				}
				if (validateCourse(courseCodeLabel) && courseCode != null) {
					alertContentUc=new AlertContentUc(i18n.GL1089(), i18n.GL1090());
				} else {
					Set<ProfileCodeDo> profileCodeDoSet = new HashSet<ProfileCodeDo>();
					ProfileCodeDo profileCodeDo = new ProfileCodeDo();
					CodeDo codeDo = new CodeDo();
					codeDo.setCodeId(Integer.parseInt(courseCode));
					profileCodeDo.setCode(codeDo);
					profileCodeDoSet.add(profileCodeDo);
					getUiHandlers().addCourse(profileCodeDoSet);
					coursesPanel.add(createCourseLabel(courseCodeLabel, courseCode));
					collectionCourseLst.setSelectedIndex(0);
				}
				courseMaxHide();
			} else {
				courseMaxShow();
			}
		}
	}

	/**
	 * Will check it ,the selected course is repeated or not
	 *
	 * @param course
	 *            to validate with already added course
	 * @return true if repeated course is selected or false
	 */
	protected boolean validateCourse(String course) {
		for (Widget widget : coursesPanel) {
			if (widget instanceof CloseLabel) {
				if (course.equals(((CloseLabel) widget).getSourceText())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ProfileDo getProfileDo() {
		return profileDo;
	}

	private void enableAddBioBtn(String textType) {
		if (textType.equalsIgnoreCase("addBioBtn")) {
			addBioBtn.setVisible(true);
			userBio.setVisible(false);
			profilePageEditBioPanel.setVisible(false);
		} else if (textType.equalsIgnoreCase("userBio")) {
			addBioBtn.setVisible(false);
			userBio.setVisible(true);
			profilePageEditBioPanel.setVisible(false);
		} else {
			addBioBtn.setVisible(false);
			userBio.setVisible(false);
			profilePageEditBioPanel.setVisible(true);
			profileTextArea.cancel();
		}
	}

	@Override
	public void disableChildData() {
		userMetadata.setVisible(false);
		bioMainContainer.setVisible(false);
		gooruSocialButtonsContainer.setVisible(false);
		gooruProfileOnOffContainer.setVisible(false);
	}

	@Override
	public void editOptions(boolean toEnable){
		enableEdit = toEnable;

		if(!toEnable){
			gooruProfileOnOffContainer.setVisible(false);
			if(isFollowUser !=null && (isFollowUser.toString().trim().equalsIgnoreCase("false") || isFollowUser.toString().trim()=="false")){
				followButton.setVisible(true);
				FollowingButtonBlue.setVisible(false);
			}
			else{
				followButton.setVisible(false);
				FollowingButtonBlue.setVisible(true);
			}
			editPencil.getElement().getStyle().setVisibility(Visibility.HIDDEN);
			addBioBtn.getElement().getStyle().setVisibility(Visibility.HIDDEN);
			addCourseGradeBtn.getElement().getStyle().setVisibility(Visibility.HIDDEN);
		}else{
			gooruProfileOnOffContainer.setVisible(true);
			followButton.setVisible(false);

			FollowingButtonBlue.setVisible(false);
			editPencil.getElement().getStyle().setVisibility(Visibility.VISIBLE);
			addBioBtn.getElement().getStyle().setVisibility(Visibility.VISIBLE);
			addCourseGradeBtn.getElement().getStyle().setVisibility(Visibility.VISIBLE);
		}
	}

	@Override
	public Label getChilNoShareOption() {
		return profilePageViewMsg;
	}

	public void getEnableWidget(boolean toEnable,String about,Set<ProfileCodeDo> set) {
		if(toEnable){
			gooruProfileOnOffContainer.setVisible(true);
			followButton.setVisible(false);

			FollowingButtonBlue.setVisible(false);
			editPencil.getElement().getStyle().setVisibility(Visibility.VISIBLE);
			addBioBtn.getElement().getStyle().setVisibility(Visibility.VISIBLE);
			addCourseGradeBtn.getElement().getStyle().setVisibility(Visibility.VISIBLE);
			bioMainContainer.setVisible(true);
			userMetadata.setVisible(true);
			bioMainContainer.getElement().setAttribute("style", "margin-top:0px");
		} else {
			if(about==""||about.equalsIgnoreCase("")) {
				bioMainContainer.setVisible(false);
			} else {
				bioMainContainer.getElement().setAttribute("style", "margin-top:8px");
				bioMainContainer.setVisible(true);
			}
			if(set.isEmpty()) {
				userMetadata.setVisible(false);
			} else {
				userMetadata.setVisible(true);
			}
			gooruProfileOnOffContainer.setVisible(false);
			if("false".equalsIgnoreCase(isFollowUser)||isFollowUser=="false"){
				followButton.setVisible(true);
				FollowingButtonBlue.setVisible(false);
			}
			else{
				followButton.setVisible(false);
				FollowingButtonBlue.setVisible(true);
			}
			editPencil.getElement().getStyle().setVisibility(Visibility.HIDDEN);
			addBioBtn.getElement().getStyle().setVisibility(Visibility.HIDDEN);
			addCourseGradeBtn.getElement().getStyle().setVisibility(Visibility.HIDDEN);
		}
	}

	@Override
	public void closeAllOpenedPopUp() {
		if(alertContentUc!=null){
			alertContentUc.getAlertBox().hide();
		}

	}

	private void clickGradeCourseEditBtn() {
		//getUiHandlers().getTaxonomyData();
		setGradeList(profileDo.getGrade());
		Set<ProfileCodeDo> codeDo = profileDo.getCourses();
		coursesPanel.clear();
		Label addedLabel = new Label("Added Courses");
		addedLabel.setStyleName("addedCourseLbl");
		coursesPanel.add(addedLabel);
		for (ProfileCodeDo code : codeDo) {
			coursesPanel.add(createCourseLabel(code.getCode().getLabel(), code.getCode().getCodeId() + ""));
		}
	}

	@Override
	public void onLoad() {
		Window.enableScrolling(true);
	}

	@Override
	public ProfilePageLibraryView getContentView() {
		return profilePageLibraryView;
	}

	private String concatenateGradeTxt(String text) {
		if(text.length()<3) {
			if(text.equals("1")) {
				text = text.concat("st "+i18n.GL0325());
			} else if(text.equals("2")) {
				text = text.concat("nd "+i18n.GL0325());
			} else if(text.equals("3")) {
				text = text.concat("rd "+i18n.GL0325());
			} else {
				text = text.concat("th "+i18n.GL0325());
			}
		}
		return text;
	}

	@Override
	public void getFollowersObj(List<UserFollowDo> userFollowDo) {

		userFollowerDo.clear();
		userFollowerDo.addAll(userFollowDo);

		buttonDisableCickOnFollow(userFollowDo.size());
		followersTabVc.setLabelCount("");
		if(userFollowDo.size()!=0){
			followersTabVc.setLabelCount(userFollowDo.get(0).getTotalHintCount()+"");
		}
		else
		{
			followersTabVc.setLabelCount(userFollowDo.size()+"");
		}


		String tabValue = AppClientFactory.getPlaceManager().getRequestParameter("tab");
		if(tabValue!=null || "".equalsIgnoreCase("tabValue")){
			if("followers".equalsIgnoreCase(tabValue))
			{

					setTab(followersTabVc);

			}
		}
	}
	@Override
	public void getFolloweingsObj(List<UserFollowDo> userFollowDo) {
		userFollowingDo.clear();
		userFollowingDo.addAll(userFollowDo);
		String tabValue = AppClientFactory.getPlaceManager().getRequestParameter("tab");
		if(tabValue!=null || "".equalsIgnoreCase("tabValue")){
			if("following".equalsIgnoreCase(tabValue))
			{
				setTab(followingTabVc);

			}
		}

	}
	@Override
	public void getTagsObj(List<UserTagsDo> userTagsDo) {
		userTagDo.clear();
		userTagDo.addAll(userTagsDo);

		tagTabVc.setLabelCount("");
		if(userTagsDo.size()!=0){
			tagTabVc.setLabelCount(userTagsDo.get(0).getTotalHitCount()+"");
		}
		else
		{
			tagTabVc.setLabelCount(userTagsDo.size()+"");
		}
		String tabValue = AppClientFactory.getPlaceManager().getRequestParameter("tab");
		if(tabValue!=null || !"".equalsIgnoreCase("tabValue")){
			if("tags".equalsIgnoreCase(tabValue))
			{
				setTab(tagTabVc);
				tagTabVc.setSelected(true);
			}
		}
		buttonDisableOnTags(userTagsDo.size());

	}
	public void onClick(ClickEvent event) {
		Object source = event.getSource();
		getUiHandlers().clearTabSlot();
		setTab(source);

	}
	@UiHandler("followButton")
	public void onClickFollowButton(ClickEvent event)
	{

		justClicked = true;
		if(!AppClientFactory.isAnonymous()){
		FollowingButtonBlue.setVisible(true);
		followButton.setVisible(false);
		getUiHandlers().followUser(AppClientFactory.getPlaceManager().getRequestParameter("id", null));
		}else{
		LoginPopupUc popup =new  LoginPopupUc() {
			@Override
			public void onLoginSuccess() {

			}
		};
		popup.show();
		popup.setGlassEnabled(true);
		}
	}
	@UiHandler("FollowingButtonBlue")
	public void onClickUnFollowButton(ClickEvent event)
	{

		if(!AppClientFactory.isAnonymous()){
		FollowingButtonBlue.setVisible(false);
		followButton.setVisible(true);
		getUiHandlers().unFollowUser(AppClientFactory.getPlaceManager().getRequestParameter("id", null));
		}
		else{
			LoginPopupUc popup =new  LoginPopupUc() {
				@Override
				public void onLoginSuccess() {

				}
			};
			popup.show();
			popup.setGlassEnabled(true);
		}


	}


	public class clickOnCollection implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			setTab(collectionsTabVc);
			Map<String,String> params = new HashMap<String,String>();
			String id=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			String user=AppClientFactory.getPlaceManager().getRequestParameter("user", null);

			params.put("id", id);
			params.put("user", user);
			params.put("tab", "collections");
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PROFILE_PAGE, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);

		}

	}
	public class clickOnFollowing implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			setTab(followingTabVc);
			Map<String,String> params = new HashMap<String,String>();
			String id=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			String user=AppClientFactory.getPlaceManager().getRequestParameter("user", null);
			params.put("id", id);
			params.put("user", user);
			params.put("tab", "following");
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PROFILE_PAGE, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);

		}
	}
	public class clickOnFollowers implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			setTab(followersTabVc);
			Map<String,String> params = new HashMap<String,String>();
			String id=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			String user=AppClientFactory.getPlaceManager().getRequestParameter("user", null);

			params.put("id", id);
			params.put("user", user);
			params.put("tab", "followers");
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PROFILE_PAGE, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);


		}
	}
	public class clickOnTags implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {

			Map<String,String> params = new HashMap<String,String>();
			String id=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			String user=AppClientFactory.getPlaceManager().getRequestParameter("user", null);
			params.put("id", id);
			params.put("user", user);
			params.put("tab", "tags");
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PROFILE_PAGE, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
			setTab(tagTabVc);


		}
	}
	public void setTab(Object tab) {
		if(tab!=null){
			if (tab.equals(collectionsTabVc)) {
				followingContainer.setVisible(false);
				collectionsTabVc.setSelected(true);
				mainContainer.setVisible(true);
				tagResourceContainer.setVisible(false);
				//getUiHandlers().revealTab(ProfilePageUiHandlers.TYPE_PUBLIC_SHELF_VIEW);

			}
			if (tab.equals(followingTabVc)) {
				followingContainer.clear();
				followingTabVc.setSelected(true);
				mainContainer.setVisible(false);
				ProfilePagefollowingView profilePagefollowingView = new ProfilePagefollowingView(userFollowingDo,"following");
				followingContainer.add(profilePagefollowingView);
				followingContainer.setVisible(true);
				tagResourceContainer.setVisible(false);
				//getUiHandlers().revealTab(ProfilePageUiHandlers.TYPE_FOLLOWING_VIEW);
			}
			if (tab.equals(followersTabVc)) {

				followingContainer.clear();
				followersTabVc.setSelected(true);
				mainContainer.setVisible(false);
				ProfilePageFollowersView profilePageFollowersView = new ProfilePageFollowersView(userFollowerDo,"followers");
				followingContainer.add(profilePageFollowersView);
				followingContainer.setVisible(true);
				tagResourceContainer.setVisible(false);
			//	getUiHandlers().revealTab(ProfilePageUiHandlers.TYPE_FOLLWER_VIEW);

			}
			if (tab.equals(tagTabVc)) {
				followingContainer.clear();
				tagTabVc.setSelected(true);
				mainContainer.setVisible(false);
				ProfileUserTagView profileUserTagView = new ProfileUserTagView(userTagDo,followingContainer,tagResourceContainer);
				followingContainer.add(profileUserTagView);
				followingContainer.setVisible(true);
				tagResourceContainer.setVisible(false);



			}
		}


 }



	public void buttonDisable()
	{
		try {
			if(collectionHandler!=null) {
				collectionHandler.removeHandler();
			}
		 } catch (AssertionError ae) {
			 AppClientFactory.printSevereLogger(ae.getMessage());
		 }
		try {
			if(followingHandler!=null) {
				followingHandler.removeHandler();
			}
		} catch (AssertionError ae) {
			 AppClientFactory.printSevereLogger(ae.getMessage());
		}

		if(profileDo.getUser().getMeta().getSummary().getCollection()==0)
		{
			collectionsTabVc.setEnabled(false);
			collectionsTabVc.getLabelCount().getElement().setAttribute("style", "color:#999");
		}
		else
		{
			collectionsTabVc.setEnabled(true);
			collectionHandler = collectionsTabVc.addClickHandler(new clickOnCollection());
			collectionsTabVc.getLabelCount().getElement().setAttribute("style", "color:#1076bb");
		}

		if(profileDo.getUser().getMeta().getSummary().getFollowing()==0)
		{
			followingTabVc.setEnabled(false);
			followingTabVc.getLabelCount().getElement().setAttribute("style", "color:#999");
		}
		else
		{
			followingTabVc.getLabelCount().getElement().setAttribute("style", "color: #1076bb;");
			followingTabVc.setEnabled(true);
			followingHandler = followingTabVc.addClickHandler(new clickOnFollowing());
		}



	}
	public void buttonDisableCickOnFollow(int totalCount)
	{
		try {
			if(follwerHandler!=null) {
				follwerHandler.removeHandler();
			}
		}catch (AssertionError ae) {
			 AppClientFactory.printSevereLogger(ae.getMessage());
		}


		if(totalCount==0)
		{
			followersTabVc.setEnabled(false);
			followersTabVc.getLabelCount().getElement().setAttribute("style", "color:#999");

		}
		else
		{
			followersTabVc.setEnabled(true);
			follwerHandler=followersTabVc.addClickHandler(new clickOnFollowers());
			followersTabVc.getLabelCount().getElement().setAttribute("style", "color: #1076bb;");

		}
}
	public void buttonDisableOnTags(int totalCount)
	{
		try{
			if(tagHandler!=null) {
				tagHandler.removeHandler();
			}
		}catch (AssertionError ae) {
			 AppClientFactory.printSevereLogger(ae.getMessage());
		}

		if(totalCount==0)
		{
			tagTabVc.setEnabled(false);
			tagTabVc.getLabelCount().getElement().setAttribute("style", "color:#999");
		}
		else
		{

			tagTabVc.setEnabled(true);
			tagHandler = tagTabVc.addClickHandler(new clickOnTags());
			tagTabVc.getLabelCount().getElement().setAttribute("style", "color: #1076bb;");
		}
}
	@Override
	public Button getFollowingButton() {
		// TODO Auto-generated method stub
		return FollowingButtonBlue;
	}

	public class MouseOverOnUnFollowingButton implements MouseOverHandler{

		@Override
			public void onMouseOver(MouseOverEvent event) {
			if(!justClicked)
			{
			FollowingButtonBlue.setText("");
			FollowingButtonBlue.setText(i18n.GL1936());
			FollowingButtonBlue.getElement().setAttribute("alt",i18n.GL1936());
			FollowingButtonBlue.getElement().setAttribute("title",i18n.GL1936());
			FollowingButtonBlue.removeStyleName("followingBtn");
			FollowingButtonBlue.addStyleName("blackPrimaryButton");
			}

		}
	}
	public class MouseOutOnUnFollowingButton implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {

			if(!justClicked)
			{
			FollowingButtonBlue.setText("");
			FollowingButtonBlue.setText(i18n.GL1895());
			FollowingButtonBlue.getElement().setAttribute("alt",i18n.GL1895());
			FollowingButtonBlue.getElement().setAttribute("title",i18n.GL1895());
			FollowingButtonBlue.removeStyleName("blackPrimaryButton");
			FollowingButtonBlue.addStyleName("followingBtn");
			}
			else
			{
				justClicked = false;
			}

		}
	}


}
