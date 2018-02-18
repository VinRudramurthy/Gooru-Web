/**
 *
 */
package org.ednovo.gooru.client.mvp.search.util;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.search.CollectionSearchResultDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.home.library.customize.RenameAndCustomizeLibraryPopUp;
import org.ednovo.gooru.client.mvp.search.SearchUiUtil;
import org.ednovo.gooru.client.uc.BrowserAgent;
import org.ednovo.gooru.client.uc.CollaboratorsUc;
import org.ednovo.gooru.client.uc.UserProfileUc;
import org.ednovo.gooru.client.uc.suggestbox.widget.Paragraph;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author janamitra
 *
 */
public class CollectionSearchWidget extends Composite {

	private static CollectionSearchWidgetUiBinder uiBinder = GWT
			.create(CollectionSearchWidgetUiBinder.class);

	interface CollectionSearchWidgetUiBinder extends
			UiBinder<Widget, CollectionSearchWidget> {
	}

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	private PopupPanel toolTipPopupPanelCustomize = new PopupPanel();
	public static boolean isCustomizePopup = false;
	String CUSTOMIZE = "customize";

	@UiField HTMLPanel pnlResourceWidget,collectionDescription,creatorPanel;
	@UiField Label collectionTitle,authorName,lblViewCount,remixCountLbl,noResourcesText;
	@UiField Paragraph pResourceText;
	@UiField Image imgAuthor,imgCollection;
	@UiField FlowPanel standardsDataPanel,teamContainer;
	@UiField Button remixBtn;

	private final FlowPanel profilePanel=new FlowPanel();

	private static final String USER_META_ACTIVE_FLAG = "0";

	private static String DEFULT_IMAGE = "images/default-collection-image.png";

	private static final String ASSESSMENT = "assessment";

	
	String collectionType;

	CollectionSearchResultDo collectionSearchResultDo = null;

	public CollectionSearchWidget(final CollectionSearchResultDo collectionSearchResultDo) {
		initWidget(uiBinder.createAndBindUi(this));
		this.collectionSearchResultDo = collectionSearchResultDo;
		//set the data
		imgAuthor.setUrl(collectionSearchResultDo.getAssetURI()+collectionSearchResultDo.getOwner().getGooruUId()+".png");
		imgAuthor.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				imgAuthor.setUrl("images/settings/setting-user-image.png");
			}
		});

		toolTipPopupPanelCustomize.clear();
		toolTipPopupPanelCustomize.hide();
		remixBtn.addMouseOverHandler(new OncustomizeCollectionBtnMouseOver());
		remixBtn.addMouseOutHandler(new OncustomizeCollectionBtnMouseOut());

		collectionTitle.setText(StringUtil.removeAllHtmlCss(collectionSearchResultDo.getResourceTitle()));
		String collectionDesc=StringUtil.removeAllHtmlCss(collectionSearchResultDo.getDescription());
		collectionDescription.getElement().setAttribute("title", collectionDesc);
		if(!StringUtil.isEmpty(collectionDesc)){
			if(collectionDesc.length()>=120){
				collectionDesc=collectionDesc.substring(0,120)+"...";
			}
			collectionDescription.getElement().setInnerText(collectionDesc);
		}
		if(collectionSearchResultDo.getCollaboratorCount()>0){
			 authorName.setText(collectionSearchResultDo.getOwner().getUsername()+" "+ i18n.GL_GRR_AND()+"");
			 CollaboratorsUc collaboratorsUc=new CollaboratorsUc(collectionSearchResultDo);
			 teamContainer.add(collaboratorsUc);
		}else{
			authorName.setText(collectionSearchResultDo.getOwner().getUsername());
			teamContainer.clear();
		}
		if ((collectionSearchResultDo.getOwner().isProfileUserVisibility())){
			 collectionCreatorDetails(collectionSearchResultDo);
		}
		remixCountLbl.setText(collectionSearchResultDo.getScollectionRemixCount()+"");
		collectionType=StringUtil.isEmpty(collectionSearchResultDo.getCollectionType())?null:collectionSearchResultDo.getCollectionType();
		StringUtil.setDefaultImages(collectionType, imgCollection, "high");
		if(!StringUtil.isEmpty(collectionSearchResultDo.getUrl())){
			imgCollection.setUrl(StringUtil.formThumbnailName(collectionSearchResultDo.getUrl(), "-160x120."));
		}
		else
		{
			StringUtil.setDefaultImages(collectionType, imgCollection, "high");
		}
		imgCollection.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				StringUtil.setDefaultImages(collectionType, imgCollection, "high");
			}
		});
		imgCollection.addClickHandler(new OnCollectionImageClick(collectionSearchResultDo.getGooruOid()));
		collectionTitle.addClickHandler(new OnCollectionImageClick(collectionSearchResultDo.getGooruOid()));
		imgCollection.getElement().getStyle().setZIndex(9999);
		//imgCollection.setGooruOid(collectionSearchResultDo.getGooruOid());
		if(String.valueOf(collectionSearchResultDo.getTotalViews()).length()>4){
			lblViewCount.setText(String.valueOf(collectionSearchResultDo.getTotalViews()).substring(0,4));
		}else{
			lblViewCount.setText(collectionSearchResultDo.getTotalViews()+"");
		}
		setResourceAndQuestionCount(collectionSearchResultDo);
		if(collectionSearchResultDo.getCollectionItems().size()>0){
			noResourcesText.setVisible(false);
			int count=0;
				for (final CollectionItemDo collectionItemSearchResultDo :collectionSearchResultDo.getCollectionItems()) {
					if(count>=4){
						break;
					}
					ResourceImageWidget resourceImageWidget=new ResourceImageWidget(collectionItemSearchResultDo.getResource());
					resourceImageWidget.getElement().setId(collectionItemSearchResultDo.getResource().getGooruOid());
					pnlResourceWidget.add(resourceImageWidget);
					resourceImageWidget.getImgResourceImg().addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							Map<String, String> params = new HashMap<String, String>();
							params.put("id", collectionSearchResultDo.getGooruOid());
							params.put("rid",collectionItemSearchResultDo.getCollectionItemId());
							String placeToken = PlaceTokens.COLLECTION_PLAY;
							if(collectionType.equalsIgnoreCase(ASSESSMENT)){
								placeToken = PlaceTokens.ASSESSMENT_PLAY;
							}
							PlaceRequest placeRequest = AppClientFactory.getPlaceManager().preparePlaceRequest(placeToken, params);
							AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
						}
					});
					count++;
				}
		}else{
			if(collectionType!=null){
				noResourcesText.setVisible(true);
				if(collectionType.contains(ASSESSMENT)){
					noResourcesText.setText(i18n.GL3233());
				}else{
					noResourcesText.setText(i18n.GL0684());
				}
			}
		}
		SearchUiUtil.renderStandardsforCollection(standardsDataPanel, collectionSearchResultDo);
		StringUtil.setAttributes(pnlResourceWidget.getElement(), "pnlResourceWidget", "", "");
		StringUtil.setAttributes(creatorPanel.getElement(), "pnlcreatorPanel", "", "");
		StringUtil.setAttributes(standardsDataPanel.getElement(), "pnlStandards", "", "");
		StringUtil.setAttributes(imgCollection.getElement(), "imgCollection", "", "");
		StringUtil.setAttributes(remixBtn.getElement(), "btnRemix", "", "");
		StringUtil.setAttributes(authorName.getElement(), "lblAuthorName", collectionSearchResultDo.getOwner().getUsername(), collectionSearchResultDo.getOwner().getUsername());
		StringUtil.setAttributes(lblViewCount.getElement(), "lblViewCount", collectionSearchResultDo.getTotalViews()+"", collectionSearchResultDo.getTotalViews()+"");
		StringUtil.setAttributes(remixCountLbl.getElement(), "remixCountLbl", collectionSearchResultDo.getScollectionRemixCount()+"", collectionSearchResultDo.getScollectionRemixCount()+"");
		StringUtil.setAttributes(collectionDescription.getElement(), "pnlCollectionDescription", collectionDesc, collectionDesc);
		StringUtil.setAttributes(collectionTitle.getElement(), "lblCollectionTitle", collectionSearchResultDo.getResourceTitle(), collectionSearchResultDo.getResourceTitle());
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
	 *category
	 * @throws : <Mentioned if any exceptions>
	 *
	 */

	public void onremixBtnClicked(String collectionId,String collectionTitle) {
		toolTipPopupPanelCustomize.clear();
		toolTipPopupPanelCustomize.hide();

		final Map<String, String> params = StringUtil.splitQuery(Window.Location.getHref());
			Boolean loginFlag = false;
			if (AppClientFactory.isAnonymous()){
				loginFlag = true;
			}else{
				loginFlag = false;
			}
			RenameAndCustomizeLibraryPopUp successPopupVc = new RenameAndCustomizeLibraryPopUp(collectionId, loginFlag, collectionTitle) {
				@Override
				public void closePoup() {
					Window.enableScrolling(true);
					this.hide();
				}
			};
			//Window.scrollTo(0, 0);
			if (!BrowserAgent.isDevice() && AppClientFactory.isAnonymous()){
				successPopupVc.setWidth("500px");
				successPopupVc.setHeight("515px");
			}else if(!BrowserAgent.isDevice() && !AppClientFactory.isAnonymous()){
				successPopupVc.setWidth("500px");
				successPopupVc.setHeight("336px");
			}
			successPopupVc.show();
			successPopupVc.center();

			params.put(CUSTOMIZE, "yes");
			params.put("collectionId", collectionId);
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, false);
	}
	public class OncustomizeCollectionBtnMouseOver implements MouseOverHandler{
		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanelCustomize.clear();
			toolTipPopupPanelCustomize.setWidget(new GlobalToolTip(i18n.GL0677()));
			toolTipPopupPanelCustomize.setStyleName("");
			toolTipPopupPanelCustomize.setPopupPosition(remixBtn.getElement().getAbsoluteLeft()+18, remixBtn.getElement().getAbsoluteTop()+10);
			toolTipPopupPanelCustomize.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanelCustomize.show();
		}

	}

	public class OncustomizeCollectionBtnMouseOut implements MouseOutHandler{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanelCustomize.hide();
		}
	}
	/**
	 * This inner class will handle the click event on the collection image click
	 * @author Gooru
	 */
	public class OnCollectionImageClick implements ClickHandler{
		String gooruOid;
		OnCollectionImageClick(String gooruOid){
			this.gooruOid=gooruOid;
		}
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new RunAsyncCallback() {
				@Override
				public void onFailure(Throwable reason) {
				}

				@Override
				public void onSuccess() {
					Map<String, String> params = new HashMap<String, String>();
					params.put("id", gooruOid);
					Cookies.setCookie("getScrollTop", Window.getScrollTop()+"");
					String placeToken = PlaceTokens.COLLECTION_PLAY;
					if(collectionType.equalsIgnoreCase(ASSESSMENT)){
						placeToken = PlaceTokens.ASSESSMENT_PLAY;
					}
					PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(placeToken, params);
					AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
				}
			});
		}
	}
	/**
	 * To show collection creator/partner info
	 * @param collectionSearchResultDo
	 */
	private void collectionCreatorDetails(final CollectionSearchResultDo collectionSearchResultDo) {
		if(StringUtil.isPartnerUser(collectionSearchResultDo.getOwner().getUsername())) {
			authorName.getElement().getStyle().setColor("#1076bb");
			authorName.getElement().getStyle().setCursor(Cursor.POINTER);
			authorName.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					MixpanelUtil.Click_Username();
					Map<String, String> params = new HashMap<String, String>();
					params.put("pid", collectionSearchResultDo.getOwner().getGooruUId());
					AppClientFactory.getPlaceManager().revealPlace(collectionSearchResultDo.getOwner().getUsername());
				}
			});

			authorName.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					AppClientFactory.getInjector().getUserService().getUserProfileV2Details(collectionSearchResultDo.getOwner().getGooruUId(), USER_META_ACTIVE_FLAG, new SimpleAsyncCallback<ProfileDo>(){
						@Override
						public void onSuccess(ProfileDo result) {
							String username=result.getUser().getUsernameDisplay();
							String aboutMe=result.getAboutMe();
							UserProfileUc userProfileUc = new UserProfileUc(username,aboutMe, result.getUser().getProfileImageUrl());
							profilePanel.clear();
							profilePanel.add(userProfileUc);
						}
					});
					creatorPanel.clear();
					creatorPanel.add(profilePanel);
				}
			});

			authorName.addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					//profilePanel.clear();
					creatorPanel.clear();
				}
			});

		}else if(PlaceTokens.YCGL_LIBRARY.equals(StringUtil.getLibNameOnClickAuthorName(collectionSearchResultDo.getOwner().getUsername()))){
			authorName.getElement().getStyle().setColor("#1076bb");
			authorName.getElement().getStyle().setCursor(Cursor.POINTER);
			authorName.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					MixpanelUtil.Click_Username();
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.YCGL_LIBRARY);
				}
			});


		}else if(PlaceTokens.EPISD_LIBRARY.equals(StringUtil.getLibNameOnClickAuthorName(collectionSearchResultDo.getOwner().getUsername()))){
			authorName.getElement().getStyle().setColor("#1076bb");
			authorName.getElement().getStyle().setCursor(Cursor.POINTER);
			authorName.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					MixpanelUtil.Click_Username();
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.EPISD_LIBRARY);
				}
			});


		}else{
			authorName.getElement().getStyle().setColor("#1076bb");
			authorName.getElement().getStyle().setCursor(Cursor.POINTER);
			authorName.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					MixpanelUtil.Click_Username();
					Map<String, String> params = new HashMap<String, String>();
					params.put("id", collectionSearchResultDo.getOwner().getGooruUId());
					params.put("user", collectionSearchResultDo.getOwner().getUsername());
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.PROFILE_PAGE,params);
				}
			});
		}
	}

	public Button getRemixBtn() {
		return remixBtn;
	}
	/**
	 * To set the resource and questions count in the collection.
	 * @param collectionSearchResultDo
	 */
	private void setResourceAndQuestionCount(CollectionSearchResultDo collectionSearchResultDo) {
		if(collectionSearchResultDo!=null){
			String collectionText = "",resourceText = "",questionsText = "";
			int resourceCount =collectionSearchResultDo.getOnlyResourceCount();
			int questionsCount=collectionSearchResultDo.getQuestionCount();
			if(resourceCount>0){
				if(resourceCount==1){
					resourceText=resourceCount+" "+i18n.GL3217()+" ";
				}else{
					resourceText=resourceCount+" "+i18n.GL3215()+" ";
				}
			}
			if(questionsCount>0){
				if(questionsCount==1){
					questionsText=" "+questionsCount+" "+i18n.GL3218()+" ";
				}else{
					questionsText=" "+questionsCount+" "+i18n.GL3216()+" ";
				}
			}
			if(collectionSearchResultDo.getCollectionType().equalsIgnoreCase(ASSESSMENT)){
				if(questionsText.isEmpty() && !resourceText.isEmpty()){
					collectionText = resourceText+i18n.GL3572();
				}else if(resourceText.isEmpty() && !questionsText.isEmpty()){
					collectionText=questionsText+i18n.GL3572();
				}else if(!questionsText.isEmpty() && !resourceText.isEmpty()){
					collectionText=resourceText+i18n.GL3219()+questionsText+i18n.GL3572();
				}
			}else{
				if(questionsText.isEmpty() && !resourceText.isEmpty()){
					collectionText = resourceText+i18n.GL3220();
				}else if(resourceText.isEmpty() && !questionsText.isEmpty()){
					collectionText=questionsText+i18n.GL3220();
				}else if(!questionsText.isEmpty() && !resourceText.isEmpty()){
					collectionText=resourceText+i18n.GL3219()+questionsText+i18n.GL3220();
				}
			}
			pResourceText.setText(collectionText);
			StringUtil.setAttributes(pResourceText.getElement(), "pResourceText", resourceText, resourceText);
		}
	}
	public Label getRemixCountLbl() {
		return remixCountLbl;
	}
}
