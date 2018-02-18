package org.ednovo.gooru.client.mvp.search.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ResourceCollDo;
import org.ednovo.gooru.application.shared.model.search.CollectionSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.rating.RatingWidgetView;
import org.ednovo.gooru.client.mvp.rating.events.DeletePlayerStarReviewEvent;
import org.ednovo.gooru.client.mvp.rating.events.DeletePlayerStarReviewHandler;
import org.ednovo.gooru.client.mvp.rating.events.UpdateResourceRatingCountEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateResourceRatingCountEventHandler;
import org.ednovo.gooru.client.mvp.rating.events.UpdateResourceReviewCountEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateResourceReviewCountEventHandler;
import org.ednovo.gooru.client.mvp.search.SearchUiUtil;
import org.ednovo.gooru.client.uc.BrowserAgent;
import org.ednovo.gooru.client.util.ImageUtil;
import org.ednovo.gooru.shared.util.ResourceImageUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class CollectionResourceWidget extends Composite {

	private static CollectionResourceWidgetUiBinder uiBinder = GWT
			.create(CollectionResourceWidgetUiBinder.class);

	interface CollectionResourceWidgetUiBinder extends
	UiBinder<Widget, CollectionResourceWidget> {
	}
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField Label lblResourcePublisher,resourceTitle,lblViewCount,lbladdCount;
	@UiField InlineLabel lblUserCount;
	@UiField HTMLPanel resourceDescription,imageOverlay, panelRatings;
	@UiField Image resourseImage,relatedCollectionImage,creatorImage;
	@UiField FlowPanel standardsDataPanel,ratingWidgetPanel;
	@UiField InlineLabel relatedCollectionTitle;
	@UiField Button btnAddResource;
	@UiField Anchor ancViewMore;
	String gooruOid;

	private SearchDo<CollectionSearchResultDo> usedInSearchDo;

	String resourceTitleText = "";

	private boolean failedThumbnailGeneration = false;

	private static final String DEFULT_IMAGE_PREFIX = "images/default-";

	private static String DEFULT_COLLECTIONIMAGE = "images/default-collection-image.png";

	private static String DEFULT_ASSESSMENTIMAGE = "images/default-assessment-image.png";

	private static final String NULL = "null";

	private static final String PLAYER_NAME = "resource";
	
	private static final String ASSESSMENT = "assessment";

	private int updateReviewCount = 0;

	private ResourceSearchResultDo resourceSearchResultDo;

	private RatingWidgetView ratingWidgetView = null;

	public CollectionResourceWidget(ResourceSearchResultDo resourceSearchResultDo) {
		initWidget(uiBinder.createAndBindUi(this));
		this.resourceSearchResultDo=resourceSearchResultDo;
		setGooruOid(resourceSearchResultDo.getGooruOid());
		resourceTitleText=!StringUtil.isEmpty(resourceSearchResultDo.getResourceTitle())?StringUtil.removeAllHtmlCss(resourceSearchResultDo.getResourceTitle()):"";
		StringUtil.setAttributes(resourceTitle.getElement(), "lblResourceTitle", resourceTitleText, resourceTitleText);
		if(Window.getClientWidth()<=768){
			if(resourceTitleText.length()>=15){
				resourceTitleText=resourceTitleText.substring(0, 15)+"...";
			}
		}else{
			if(resourceTitleText.length()>33){
				resourceTitleText=resourceTitleText.substring(0, 33)+"...";
			}
		}
		resourceTitle.setText(resourceTitleText);
		if(resourceSearchResultDo.getPublisher()!=null && resourceSearchResultDo.getPublisher().size()>0){
			lblResourcePublisher.setText(resourceSearchResultDo.getPublisher().get(0));
		}
		String resourceDesc=!StringUtil.isEmpty(resourceSearchResultDo.getDescription())?StringUtil.removeAllHtmlCss(resourceSearchResultDo.getDescription()):"";
		if(resourceDesc.length()>=120){
			resourceDesc=resourceDesc.substring(0, 120)+"...";
		}
		resourceDescription.getElement().setInnerText(resourceDesc);
		if(resourceSearchResultDo.getResourceUsedUserCount()>1)		{
			ancViewMore.setVisible(true);
		}else{
			ancViewMore.setVisible(false);
		}
		if(resourceSearchResultDo.getResourceUsedUserCount()>0){
			if(resourceSearchResultDo.getResourceUsedUserCount()==1){
				lblUserCount.setText("Used by "+ resourceSearchResultDo.getResourceUsedUserCount()+" person");
			}else{
				lblUserCount.setText("Used by "+ resourceSearchResultDo.getResourceUsedUserCount()+" people");
			}
		}else{
			lblUserCount.setText("");
		}

		lbladdCount.setText(resourceSearchResultDo.getResourceAddedCount()+"");
		if(String.valueOf(resourceSearchResultDo.getTotalViews()).length()>4){
			lblViewCount.setText(String.valueOf(resourceSearchResultDo.getTotalViews()).substring(0,4));
		}else{
			lblViewCount.setText(resourceSearchResultDo.getTotalViews()+"");
		}

		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				if(Window.getClientWidth()<=768){
					if(resourceTitleText.length()>=15){
						resourceTitleText=resourceTitleText.substring(0, 15)+"...";
					}
				}else{
					if(resourceTitleText.length()>=25){
						resourceTitleText=resourceTitleText.substring(0, 25)+"...";
					}
				}
			}
		});
		String category =  resourceSearchResultDo.getNewResourceFormat() != null && resourceSearchResultDo.getNewResourceFormat().getValue() != null ? resourceSearchResultDo.getNewResourceFormat().getValue() : "webpage";
		imageOverlay.addStyleName(category.toLowerCase()+"Small");
		if(resourceSearchResultDo.getResourceType()!=null&&resourceSearchResultDo.getResourceType().getName().equalsIgnoreCase("video/youtube")) {
			setUrl(resourceSearchResultDo.getUrl(),null,category, resourceTitleText, true);
		}else{
			setUrl(resourceSearchResultDo.getUrl(),null,category, resourceTitleText, false);
		}
		SearchUiUtil.renderStandardsForresourceSearch(standardsDataPanel, resourceSearchResultDo);
		ratingWidgetView=new RatingWidgetView();
		ratingWidgetView.setAvgStarRating(resourceSearchResultDo.getRatings().getAverage());
		Integer reviewCount = resourceSearchResultDo.getRatings().getReviewCount();
		if (reviewCount == null) {
			reviewCount = 0;
		}

//		if(reviewCount!=0){
//			ratingWidgetView.getRatingCountLabel().setVisible(true);
//			if(reviewCount==1){
//				ratingWidgetView.getRatingCountLabel().setText(" "+reviewCount.toString()+" "+i18n.GL3006());
//			}else{
//				ratingWidgetView.getRatingCountLabel().setText(" "+reviewCount.toString()+" "+i18n.GL2024());
//			}
//		}else{
//			ratingWidgetView.getRatingCountLabel().setVisible(false);
//		}
		ratingWidgetView.getRatingCountLabel().setVisible(false);
		ratingWidgetPanel.add(ratingWidgetView);

		resourseImage.addClickHandler(new ResourceImageClick(resourceSearchResultDo.getGooruOid()));
		resourceTitle.addClickHandler(new ResourceImageClick(resourceSearchResultDo.getGooruOid()));
		imageOverlay.addDomHandler(new ResourceImageClick(resourceSearchResultDo.getGooruOid()),ClickEvent.getType());

		usedInSearchDo = new SearchDo<CollectionSearchResultDo>();
		usedInSearchDo.setQuery(resourceSearchResultDo.getGooruOid());
		usedInSearchDo.setPageSize(1);
		AppClientFactory.getInjector().getResourceService().getResourceBasedUsersDetails(resourceSearchResultDo.getGooruOid(), 0, 1, new SimpleAsyncCallback<ArrayList<ResourceCollDo>>() {
			@Override
			public void onSuccess(ArrayList<ResourceCollDo> userCollectionsList) {
					//getView().displayContents(userCollectionsList,searchResultDo);
				if(userCollectionsList.size()>0){
						relatedCollectionImage.setVisible(true);
						creatorImage.setVisible(true);
						final String collectionType=StringUtil.isEmpty(userCollectionsList.get(0).getCollectionType())?null:userCollectionsList.get(0).getCollectionType();
						relatedCollectionTitle.addClickHandler(new ResourceCollectionHandler(userCollectionsList.get(0).getGooruOid(),collectionType));
						relatedCollectionImage.addClickHandler(new ResourceCollectionHandler(userCollectionsList.get(0).getGooruOid(),collectionType));
						relatedCollectionTitle.setText(userCollectionsList.get(0).getTitle());
						relatedCollectionTitle.setTitle(userCollectionsList.get(0).getTitle());
						creatorImage.setUrl(userCollectionsList.get(0).getUser().getProfileImageUrl());
						setDefaultCollectionImage(collectionType);
						if(userCollectionsList.get(0).getThumbnails()!=null && userCollectionsList.get(0).getThumbnails().getUrl()!=null){
							relatedCollectionImage.setUrl(userCollectionsList.get(0).getThumbnails().getUrl());
						}
						else
						{
							setDefaultCollectionImage(collectionType);
						}
						relatedCollectionTitle.setStyleName("collectionTitle");
						relatedCollectionImage.addErrorHandler(new ErrorHandler() {
							@Override
							public void onError(ErrorEvent event) {
								setDefaultCollectionImage(collectionType);
							}
						});
				}else{
					relatedCollectionImage.setVisible(false);
					creatorImage.setVisible(false);
					relatedCollectionTitle.setStyleName("collectionTitleNoCollection");
					relatedCollectionTitle.setText(i18n.GL3212());
				}
			}
		});

		creatorImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				creatorImage.setUrl("images/profilepage/user-profile-pic.png");
			}
		});

		StringUtil.setAttributes(standardsDataPanel.getElement(), "pnlStandards", "", "");
		StringUtil.setAttributes(ratingWidgetPanel.getElement(), "pnlRatings", "", "");
		StringUtil.setAttributes(lblViewCount.getElement(), "lblViewCount", "", "");
		StringUtil.setAttributes(lbladdCount.getElement(), "lbladdCount", "", "");
		StringUtil.setAttributes(lblUserCount.getElement(), "lblUserCount", "", "");
		StringUtil.setAttributes(creatorImage.getElement(), "imgCreator", "", "");
		StringUtil.setAttributes(relatedCollectionImage.getElement(), "imgRelatedCollection", "", "");
		StringUtil.setAttributes(relatedCollectionTitle.getElement(), "lblRelatedCollectionTitle", "", "");
		StringUtil.setAttributes(resourceDescription.getElement(), "pnlResourceDescription", "", "");
		StringUtil.setAttributes(imageOverlay.getElement(), "imageOverlay", "", "");
		StringUtil.setAttributes(btnAddResource.getElement(), "btnAddResource", "", "");
		StringUtil.setAttributes(resourseImage.getElement(), "imgResoruce", "", "");

		if (!BrowserAgent.isDevice()){
			panelRatings.getElement().getStyle().setWidth(27, Unit.PCT);
		}

		AppClientFactory.getEventBus().addHandler(
				UpdateResourceRatingCountEvent.TYPE, setRatingCount);
		AppClientFactory.getEventBus().addHandler(
				DeletePlayerStarReviewEvent.TYPE, deleteStarRating);
		AppClientFactory.getEventBus().addHandler(
				UpdateResourceReviewCountEvent.TYPE, setReviewCount);
	}
	/**
	 * To set default collection type image.
	 * @param collectionType {@link String}
	 */
	protected void setDefaultCollectionImage(String collectionType) {
		if(collectionType!=null && collectionType.equals(ASSESSMENT)){
			relatedCollectionImage.setUrl(DEFULT_ASSESSMENTIMAGE);
			relatedCollectionImage.getElement().setAttribute("style", "border-left: 3px solid #feae29;");
		}else{
			relatedCollectionImage.setUrl(DEFULT_COLLECTIONIMAGE);
			relatedCollectionImage.getElement().setAttribute("style", "border-left: 3px solid #1076bb;");
		}
	}
	/**
	 * This inner class will handle the click event on the resource image click and it will play that resoruce
	 * @author Gooru
	 *
	 */
	public class ResourceImageClick implements ClickHandler{
		String resoruceId;
		ResourceImageClick(String resoruceId){
			this.resoruceId=resoruceId;
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
					params.put("id", resoruceId);
					params.put("pn", PLAYER_NAME);
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.RESOURCE_PLAY, params);
				}
			});
		}
	}
	/**
	 * This method will set the thumbnail image
	 * @param thumbnailUrl
	 * @param realUrl
	 * @param category
	 * @param title
	 * @param generateYoutube
	 */
	public void setUrl(final String thumbnailUrl, final String realUrl, final String category, final String title, final boolean generateYoutube) {
		failedThumbnailGeneration = false;
		final String categoryString = category == null || category.startsWith(ASSESSMENT) ? ImageUtil.QUESTION : category;
		if (thumbnailUrl == null || thumbnailUrl.endsWith(NULL) || thumbnailUrl.equalsIgnoreCase("") ) {
			setDefaultThumbnail(thumbnailUrl, realUrl, categoryString.trim(), generateYoutube);
		} else {
			resourseImage.setUrl(thumbnailUrl);
		}
		resourseImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				setDefaultThumbnail(thumbnailUrl, realUrl, categoryString, generateYoutube);
				failedThumbnailGeneration = true;
			}
		});
		resourseImage.setTitle(title);
		imageOverlay.setTitle(title);
	}
	/**
	 * This method will set the default image
	 * @param thumbnailUrl
	 * @param url
	 * @param categoryString
	 * @param generateYoutube
	 */
	private void setDefaultThumbnail(String thumbnailUrl, String url, String categoryString, boolean generateYoutube) {
		if(generateYoutube) {
			resourseImage.setUrl(ResourceImageUtil.youtubeImageLink(ResourceImageUtil.getYoutubeVideoId(url), Window.Location.getProtocol()));
		}else if (!failedThumbnailGeneration && thumbnailUrl!=null && thumbnailUrl.endsWith("/")) {
			resourseImage.setUrl(DEFULT_IMAGE_PREFIX + categoryString + i18n.GL0899());
		}else {
			resourseImage.setUrl(DEFULT_IMAGE_PREFIX + categoryString + i18n.GL0899());
		}
	}
	/**
	 * This method will return the add button
	 * @return
	 */
	public Button getAddResoruce(){
		return btnAddResource;
	}
	public void setUpdateReviewCount(Integer updateReviewCount) {
		// TODO Auto-generated method stub
		this.updateReviewCount = updateReviewCount;
		ratingWidgetView.getRatingCountLabel().getElement()
		.removeAttribute("class");
		if (updateReviewCount > 0) {
			ratingWidgetView
			.getRatingCountLabel()
			.getElement()
			.setAttribute("style",
					"cursor: pointer;text-decoration: none !important;color: #1076bb;");
			ratingWidgetView.getRatingCountLabel().getElement().getStyle()
			.setPadding(4, Unit.PX);
		} else {
			ratingWidgetView
			.getRatingCountLabel()
			.getElement()
			.setAttribute("style",
					"cursor: none;text-decoration: none !important;color: #4e9746;");
		}
		ratingWidgetView.getRatingCountLabel().setVisible(false);
	}
	/**
	 * @return the ratingWidgetView
	 */
	public RatingWidgetView getRatingWidgetView() {
		return ratingWidgetView;
	}
	/**
	 * @param ratingWidgetView the ratingWidgetView to set
	 */
	public void setRatingWidgetView(RatingWidgetView ratingWidgetView) {
		this.ratingWidgetView = ratingWidgetView;
	}
	/**
	 * @return the updateReviewCount
	 */
	public int getUpdateReviewCount() {
		return updateReviewCount;
	}
	/**
	 * @param updateReviewCount the updateReviewCount to set
	 */
	public void setUpdateReviewCount(int updateReviewCount) {
		this.updateReviewCount = updateReviewCount;
	}

	public Anchor getAncViewMore() {
		return ancViewMore;
	}

	UpdateResourceReviewCountEventHandler setReviewCount = new UpdateResourceReviewCountEventHandler() {
		@Override
		public void setReviewCount(String resourceId,Integer count) {
			if(resourceSearchResultDo.getGooruOid().equals(resourceId)){
				if(count!=0){
					ratingWidgetView.getRatingCountLabel().setVisible(false);
					setUpdateReviewCount(count);
					if(count==1){
						ratingWidgetView.getRatingCountLabel().setText(" "+Integer.toString(count)+" "+i18n.GL3006());
					}else{
						ratingWidgetView.getRatingCountLabel().setText(" "+Integer.toString(count)+" "+i18n.GL2024());
					}
				}else{
					ratingWidgetView.getRatingCountLabel().setVisible(false);
				}
				ratingWidgetView.getAverageRatingLabel().setVisible(false);
				ratingWidgetView.getRatingCountLabel().setVisible(false);
			}
		}

	};

	UpdateResourceRatingCountEventHandler setRatingCount = new UpdateResourceRatingCountEventHandler() {
		@Override
		public void setResourceRatingCount(String resourceId, double avg,
				Integer count) {
			if (resourceSearchResultDo.getGooruOid().equals(resourceId)) {
				ratingWidgetView.setAvgStarRating(avg);
				ratingWidgetView.getAverageRatingLabel().setVisible(false);
				ratingWidgetView.getRatingCountLabel().setVisible(false);
			}
		}

	};

	DeletePlayerStarReviewHandler deleteStarRating = new DeletePlayerStarReviewHandler() {
		@Override
		public void deleteStarRatings(String resourceGooruOid) {
			if(resourceSearchResultDo.getGooruOid().equals(resourceGooruOid)){
				if(ratingWidgetView!=null){
					String[] revCount = ratingWidgetView.getRatingCountLabel().getText().split(" ");
					if(Integer.parseInt(revCount[1].trim())==1){
						ratingWidgetView.setAvgStarRating(0);
						ratingWidgetView.getRatingCountLabel().setVisible(false);
						/**
						 * Commented the following code as 0 reviews we should not show.
						 */
						/*ratingWidgetView.getRatingCountLabel().setText(" "+ (Integer.parseInt(revCount[1])-1)+" "+i18n.GL2024());
						setUpdateReviewCount(Integer.parseInt(revCount[1])-1);*/
					}else{
						ratingWidgetView.getRatingCountLabel().setVisible(true);
						setUpdateReviewCount(Integer.parseInt(revCount[1])-1);
						if((Integer.parseInt(revCount[1])-1)==1){
							ratingWidgetView.getRatingCountLabel().setText(" "+(Integer.parseInt(revCount[1])-1)+" "+i18n.GL3006());
						}else{
							ratingWidgetView.getRatingCountLabel().setText(" "+(Integer.parseInt(revCount[1])-1)+" "+i18n.GL2024());
						}

					}
				}
			}
		}

	};
	 public class ResourceCollectionHandler implements ClickHandler{
	    String gooruOid;
	    String collectionType;
		public ResourceCollectionHandler(String gooruOid, String collectionType) {
			this.gooruOid=gooruOid;
			this.collectionType=collectionType;
		}
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new RunAsyncCallback() {
				@Override
				public void onSuccess() {
					Map<String, String> params = new HashMap<String, String>();
					params.put("id", gooruOid);
					PlaceRequest placeRequest;
					if(collectionType.equalsIgnoreCase(ASSESSMENT)){
						placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.ASSESSMENT_PLAY, params);
					}else{
						placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
					}
					AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
				}
				@Override
				public void onFailure(Throwable reason) {

				}
			});
		}
	 }
	public Label getLbladdCount() {
		return lbladdCount;
	}
	public String getGooruOid() {
		return gooruOid;
	}
	public void setGooruOid(String gooruOid) {
		this.gooruOid = gooruOid;
	}
}
