package org.ednovo.gooru.client.uc;



import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.rating.RatingWidgetView;
import org.ednovo.gooru.client.mvp.rating.events.OpenReviewPopUpEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateRatingsInRealTimeEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateRatingsInRealTimeHandler;
import org.ednovo.gooru.shared.util.ResourceImageUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PreviewResourceView extends Composite implements HasClickHandlers{

	@UiField Image resourceThumbnail;
	@UiField Label resourceTypeImage,resourceCategory,resourceSourceName,resourceIndex,resourceNumber;
	@UiField HTML resourceTitle,resourceHoverTitle;
	@UiField FlowPanel resourceImageContainer,resourceThumbContainer;
	@UiField public FlowPanel ratingWidgetPanel;

	private RatingWidgetView ratingWidgetView=null;

	private CollectionItemDo collectionItemDo=null;

	private String collectionItemId=null;

	private static TocResourceViewUiBinder uiBinder = GWT.create(TocResourceViewUiBinder.class);

	interface TocResourceViewUiBinder extends UiBinder<Widget, PreviewResourceView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);


	public PreviewResourceView(){
		initWidget(uiBinder.createAndBindUi(this));
		resourceThumbContainer.getElement().setId("fpnlResourceThumbContainer");
		resourceImageContainer.getElement().setId("fpnlResourceImageContainer");
		resourceThumbnail.getElement().setId("imgResourceThumbnail");
		resourceTypeImage.getElement().setId("lblResourceTypeImage");
		resourceNumber.getElement().setId("lblResourceNumber");
		resourceTitle.getElement().setId("htmlResourceTitle");
		resourceIndex.getElement().setId("lblResourceIndex");
		resourceHoverTitle.getElement().setId("htmlResourceHoverTitle");
		resourceCategory.getElement().setId("lblResourceCategory");
		resourceSourceName.getElement().setId("lblResourceSourceName");
		ratingWidgetPanel.getElement().setId("fpnlRatingWidgetPanel");
	}


	@UiConstructor
	public PreviewResourceView(CollectionItemDo collectionItemDo, int itemIndex){
		initWidget(uiBinder.createAndBindUi(this));
		resourceThumbContainer.getElement().setId("fpnlResourceThumbContainer");
		resourceImageContainer.getElement().setId("fpnlResourceImageContainer");
		resourceThumbnail.getElement().setId("imgResourceThumbnail");
		resourceTypeImage.getElement().setId("lblResourceTypeImage");
		resourceNumber.getElement().setId("lblResourceNumber");
		resourceTitle.getElement().setId("htmlResourceTitle");
		resourceIndex.getElement().setId("lblResourceIndex");
		resourceHoverTitle.getElement().setId("htmlResourceHoverTitle");
		resourceCategory.getElement().setId("lblResourceCategory");
		resourceSourceName.getElement().setId("lblResourceSourceName");
		ratingWidgetPanel.getElement().setId("fpnlRatingWidgetPanel");
		this.collectionItemDo=collectionItemDo;
		setNavigationResourceTitle(collectionItemDo.getResource().getTitle());
		if(collectionItemDo.getResource().getResourceFormat()!=null){
			setResourceTypeIcon(collectionItemDo.getResource().getResourceFormat().getDisplayName());
		}
		setResourceCategory();
		setReourceSourceName();
		setResourceSequence(itemIndex+1);
		setResourcePlayLink();

		if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY)){
			setAvgRatingWidget();
			AppClientFactory.getEventBus().addHandler(UpdateRatingsInRealTimeEvent.TYPE,setRatingWidgetMetaData);
		}
	}


	UpdateRatingsInRealTimeHandler setRatingWidgetMetaData = new UpdateRatingsInRealTimeHandler() {

		@Override
		public void updateRatingInRealTime(String gooruOid, double average,Integer count) {
			if(collectionItemDo!=null && collectionItemDo.getResource()!=null && collectionItemDo.getResource().getGooruOid()!=null && collectionItemDo.getResource().getGooruOid().equals(gooruOid)){
				collectionItemDo.getResource().getRatings().setCount(count);
                collectionItemDo.getResource().getRatings().setAverage(average);
				setAvgRatingWidget();
			}
		}
	};

	/**
	 * Average star ratings widget will get integrated.
	 */
	private void setAvgRatingWidget() {
		ratingWidgetView=new RatingWidgetView();
		if(collectionItemDo.getResource().getRatings()!=null){
			if(collectionItemDo.getResource().getRatings().getCount()!=null && collectionItemDo.getResource().getRatings().getCount()!= 0){
				ratingWidgetView.getRatingCountOpenBrace().setText(i18n. GL_SPL_OPEN_SMALL_BRACKET());
				ratingWidgetView.getRatingCountLabel().setText(collectionItemDo.getResource().getRatings().getCount().toString());
				ratingWidgetView.getRatingCountCloseBrace().setText(i18n. GL_SPL_CLOSE_SMALL_BRACKET());
				ratingWidgetView.setAvgStarRating(collectionItemDo.getResource().getRatings().getAverage());
				ratingWidgetPanel.getElement().setId("fpnlRatingWidgetPanel");
			}
		}
		ratingWidgetPanel.clear();
		ratingWidgetPanel.add(ratingWidgetView);
	}

	/**
	 *
	 * Inner class implementing {@link ClickEvent}
	 *
	 */
	private class ShowRatingPopupEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			/**
			 * OnClick of count label event to invoke Review pop-pup
			 */

			AppClientFactory.fireEvent(new OpenReviewPopUpEvent(collectionItemDo.getResource().getGooruOid(), collectionItemDo.getResource().getTitle(),collectionItemDo.getResource().getUser().getUsername()));
		}
	}


	@UiHandler("resourceThumbnail")
	public void onErrorResourceImage(ErrorEvent errorEvent){
		resourceThumbnail.setUrl("images/resource_trans.png");
		if(collectionItemDo.getResource().getResourceFormat()!=null){
		String resourceType=collectionItemDo.getResource().getResourceFormat().getDisplayName();
			resourceType=resourceType.toLowerCase();
		if(resourceType.equalsIgnoreCase("lesson")||resourceType.equalsIgnoreCase("textbook")||resourceType.equalsIgnoreCase("handout"))
		{
			resourceType=resourceType.replaceAll("lesson", "text").replaceAll("textbook", "text").replaceAll("handout", "text");
		}
		if(resourceType.equalsIgnoreCase("slide"))
		{
			resourceType=resourceType.replaceAll("slide","image");
		}
		if(resourceType.equalsIgnoreCase("exam")||resourceType.equalsIgnoreCase("website") || resourceType.equalsIgnoreCase("challenge"))
		{
			resourceType=resourceType.replaceAll("exam","webpage").replaceAll("website","webpage").replaceAll("challenge","webpage");
		}
		resourceThumbnail.setStyleName(getResourceDefaultImage(resourceType));
	}
	}
	@Override
	public void onLoad(){
		if(collectionItemDo.getResource().getResourceType().getName().equalsIgnoreCase("assessment-question")){
			resourceThumbnail.setUrl(getQuestionImage());
		}else if(collectionItemDo.getResource().getResourceType().getName().equalsIgnoreCase("video/youtube")){
			resourceThumbnail.setUrl(ResourceImageUtil.youtubeImageLink(ResourceImageUtil.getYoutubeVideoId(collectionItemDo.getResource().getUrl()),Window.Location.getProtocol()));
		}else{
			resourceThumbnail.setUrl(collectionItemDo.getResource().getThumbnails() != null ? collectionItemDo.getResource().getThumbnails().getUrl() : "images/defaultRes.png");
		}
	}

	public void setResourcePlayLink(){
		Anchor resourceAnchor=new Anchor();
		resourceAnchor.setHref(getResourceLink());
		resourceAnchor.setStyleName("");
		resourceAnchor.getElement().appendChild(resourceImageContainer.getElement());
		resourceThumbContainer.insert(resourceAnchor, 0);
	}
	public String getResourceLink(){
		String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
		if(collectionItemDo.getNarration()!=null&&!collectionItemDo.getNarration().trim().equals("")){
			String resourceLink="#"+AppClientFactory.getCurrentPlaceToken()+"&id="+collectionId+"&rid="+collectionItemDo.getCollectionItemId()+"&tab=narration";
			resourceLink+=PreviewPlayerPresenter.setConceptPlayerParameters();
			return resourceLink;
		}else{
			String resourceLink="#"+AppClientFactory.getCurrentPlaceToken()+"&id="+collectionId+"&rid="+collectionItemDo.getCollectionItemId();
			resourceLink+=PreviewPlayerPresenter.setConceptPlayerParameters();
			return resourceLink;
		}
	}
	public void setResourceTypeIcon(String category){
		resourceTypeImage.addStyleName(getResourceTypeImage(category));
	}

	public void setNavigationResourceTitle(String title){
		resourceTitle.setHTML(getHTML(title));
		resourceTitle.getElement().setAttribute("alt", getHTML(title));
		resourceTitle.getElement().setAttribute("title", getHTML(title));
		resourceHoverTitle.setHTML(getHTML(title));
		resourceHoverTitle.getElement().setAttribute("alt", getHTML(title));
		resourceHoverTitle.getElement().setAttribute("title", getHTML(title));
	}
	public void setResourceCategory(){
		if(collectionItemDo.getResource().getResourceFormat()!=null){
			String resourceType=collectionItemDo.getResource().getResourceFormat().getDisplayName();
			resourceType=resourceType.toLowerCase();

			if(resourceType.equalsIgnoreCase("lesson")||resourceType.equalsIgnoreCase("textbook")||resourceType.equalsIgnoreCase("handout"))
			{
				resourceType=resourceType.replaceAll("lesson", "text").replaceAll("textbook", "text").replaceAll("handout", "text");
			}
			if(resourceType.equalsIgnoreCase("slide"))
			{
				resourceType=resourceType.replaceAll("slide","image");
			}
			if(resourceType.equalsIgnoreCase("exam")||resourceType.equalsIgnoreCase("website")|| resourceType.equalsIgnoreCase("challenge"))
			{
				resourceType=resourceType.replaceAll("exam","webpage").replaceAll("website","webpage").replaceAll("challenge","webpage");
			}
			resourceCategory.setText(resourceType);
			resourceCategory.getElement().setAttribute("alt", resourceType);
			resourceCategory.getElement().setAttribute("title", resourceType);
		}
	}
	public void setReourceSourceName(){
		if(collectionItemDo.getResource().getResourceSource()!=null){
			if((!collectionItemDo.getResource().getUrl().startsWith("https://docs.google.com"))&&(!collectionItemDo.getResource().getUrl().startsWith("http://docs.google.com"))){
			resourceSourceName.setText(collectionItemDo.getResource().getResourceSource().getAttribution()!=null?collectionItemDo.getResource().getResourceSource().getAttribution():"");
			resourceSourceName.getElement().setAttribute("alt", collectionItemDo.getResource().getResourceSource().getAttribution()!=null?collectionItemDo.getResource().getResourceSource().getAttribution():"");
			resourceSourceName.getElement().setAttribute("title", collectionItemDo.getResource().getResourceSource().getAttribution()!=null?collectionItemDo.getResource().getResourceSource().getAttribution():"");
			}
			}else{
			resourceSourceName.setText("");
		}

	}
	public void setResourceSequence(int itemIndex){
		resourceIndex.setText(itemIndex<10?"0"+itemIndex:""+itemIndex);
		resourceIndex.getElement().setAttribute("alt", itemIndex<10?"0"+itemIndex:""+itemIndex);
		resourceIndex.getElement().setAttribute("title", itemIndex<10?"0"+itemIndex:""+itemIndex);
		resourceNumber.setText(itemIndex<10?"0"+itemIndex:""+itemIndex);
		resourceNumber.getElement().setAttribute("alt", itemIndex<10?"0"+itemIndex:""+itemIndex);
		resourceNumber.getElement().setAttribute("title", itemIndex<10?"0"+itemIndex:""+itemIndex);
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}
	public String getResourceTypeImage(String resourceType){
		if(resourceType.equalsIgnoreCase("Video")||resourceType.equalsIgnoreCase("Videos")){
			return PlayerBundle.INSTANCE.getPlayerStyle().videoResourceType();
		}else if(resourceType.equalsIgnoreCase("Interactive")){
			return PlayerBundle.INSTANCE.getPlayerStyle().interactiveResourceType();
		}
		else if(resourceType.equalsIgnoreCase("Website")||resourceType.equalsIgnoreCase("Exam")||resourceType.equalsIgnoreCase("Webpage")|| resourceType.equalsIgnoreCase("challenge")){
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceType();
		}
		else if(resourceType.equalsIgnoreCase("Slide")||resourceType.equalsIgnoreCase("Image")){
			return PlayerBundle.INSTANCE.getPlayerStyle().imageResourceType();
		}
		else if(resourceType.equalsIgnoreCase("Textbook")||resourceType.equalsIgnoreCase("lesson")||resourceType.equalsIgnoreCase("Handout")||resourceType.equalsIgnoreCase("Text")){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceType();
		}
		else if(resourceType.equalsIgnoreCase("Question")){
			return PlayerBundle.INSTANCE.getPlayerStyle().questionResourceType();
		}
		else if(resourceType.equalsIgnoreCase("Audio")){
			return PlayerBundle.INSTANCE.getPlayerStyle().audioResourceType();
		}else if(resourceType.equalsIgnoreCase("Other")){
			return PlayerBundle.INSTANCE.getPlayerStyle().otherResourceType();
		}else {
			return PlayerBundle.INSTANCE.getPlayerStyle().otherResourceType();
		}
	}

	public String getResourceDefaultImage(String resourceType){
		if(resourceType.equalsIgnoreCase("Video")||resourceType.equalsIgnoreCase("Videos")){
			return PlayerBundle.INSTANCE.getPlayerStyle().videoResourceDefault();
		}else if(resourceType.equalsIgnoreCase("Interactive")){
			return PlayerBundle.INSTANCE.getPlayerStyle().interactiveResourceDefault();
		}
		else if(resourceType.equalsIgnoreCase("Website")||resourceType.equalsIgnoreCase("Exam")||resourceType.equalsIgnoreCase("Webpage")|| resourceType.equalsIgnoreCase("challenge")){
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceDefault();
		}
		else if(resourceType.equalsIgnoreCase("Slide")||resourceType.equalsIgnoreCase("Image")){
			return PlayerBundle.INSTANCE.getPlayerStyle().imageResourceDefault();
		}
		else if(resourceType.equalsIgnoreCase("Textbook")||resourceType.equalsIgnoreCase("lesson")||resourceType.equalsIgnoreCase("Handout")||resourceType.equalsIgnoreCase("Text")){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceDefault();
		}
		else if(resourceType.equalsIgnoreCase("Question")){
			return PlayerBundle.INSTANCE.getPlayerStyle().questionResourceDefault();
		}
		else if(resourceType.equalsIgnoreCase("Audio")){
			return PlayerBundle.INSTANCE.getPlayerStyle().audioResourceDefault();
		}else if(resourceType.equalsIgnoreCase("Other")){
			return PlayerBundle.INSTANCE.getPlayerStyle().otherResourceDefault();
		}else {
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceDefault();
		}
	}

	public String getCollectionItemId() {
		return collectionItemId;
	}

	public void setCollectionItemId(String collectionItemId) {
		this.collectionItemId = collectionItemId;
	}

	private String getQuestionImage(){
		String thumbnailImage="";
		String assetName="";
		try{
			if(collectionItemDo.getResource().getAssets()!=null&&collectionItemDo.getResource().getAssets().size()>0){
				assetName=collectionItemDo.getResource().getAssets().get(0).getAsset().getName();
				thumbnailImage=collectionItemDo.getResource().getAssetURI()+collectionItemDo.getResource().getFolder()+assetName;
			}else{
				thumbnailImage=collectionItemDo.getResource().getThumbnails().getUrl();
			}
		}catch(Exception e){
			AppClientFactory.printSevereLogger("PreviewResourceView getQuestionImage:::"+e);
		}
		return thumbnailImage!=null?thumbnailImage:"images/defaultRes.png";
	}

	private String getHTML(String html){
		if (html != null){
			html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
			HTML contentHtml=new HTML(html);
			contentHtml.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().setEllipses());
		}
		return html;
	}

}
