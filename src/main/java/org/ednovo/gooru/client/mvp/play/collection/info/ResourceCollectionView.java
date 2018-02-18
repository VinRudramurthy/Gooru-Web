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
package org.ednovo.gooru.client.mvp.play.collection.info;

import java.util.List;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.uc.ToolTipPopUp;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ResourceCollectionView extends Composite{
	
	@UiField HTMLPanel imageContainer;
	
	@UiField Label userName,createdByText;
	
	@UiField Anchor collectionTitle;
	
	@UiField HTMLPanel metadataContainer;
	
	@UiField Image collectionThumbnail;
	
	ToolTipPopUp toolTip ; 
	
	private ResourceSearchResultDo resourceSearchResultDo;
	
	private static final String SEPARATOR="&#8226;";
	
	private static ResourceCollectionViewUiBinder uiBinder = GWT.create(ResourceCollectionViewUiBinder.class);

	interface ResourceCollectionViewUiBinder extends UiBinder<Widget, ResourceCollectionView> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	public ResourceCollectionView(){
		initWidget(uiBinder.createAndBindUi(this));
		createdByText.setText(i18n.GL0622());
		createdByText.getElement().setAttribute("alt",i18n.GL0622());
		createdByText.getElement().setAttribute("title",i18n.GL0622());
		setId();
	}
	public void setId(){
		createdByText.getElement().setId("lblCreatedByText");
		imageContainer.getElement().setId("pnlImageContainer");
		collectionThumbnail.getElement().setId("imgCollectionThumbnail");
		collectionTitle.getElement().setId("lnkCollectionTitle");
		userName.getElement().setId("lblUserName");
		metadataContainer.getElement().setId("pnlMetadataContainer");
	}
	@UiConstructor
	public ResourceCollectionView(final ResourceSearchResultDo resourceSearchResultDo){
		initWidget(uiBinder.createAndBindUi(this));
		createdByText.setText(i18n.GL0622());
		createdByText.getElement().setAttribute("alt",i18n.GL0622());
		createdByText.getElement().setAttribute("title",i18n.GL0622());
		this.resourceSearchResultDo=resourceSearchResultDo;
		setCollectionTitle(resourceSearchResultDo.getResourceTitle()!=null?resourceSearchResultDo.getResourceTitle():"");
		//This is used for to enable partner names hyperlinks (FTE, Lessonopoly, and Autodesk)
		setId();
			AppClientFactory.getInjector().getPlayerAppService().getUserProfileVisibility(resourceSearchResultDo.getGooruUId(), new SimpleAsyncCallback<Boolean>() {
				@Override
				public void onSuccess(Boolean result) {
					if(result){
						setUserNameDisplay((resourceSearchResultDo.getOwner()!=null&&resourceSearchResultDo.getOwner().getUsername()!=null)?resourceSearchResultDo.getOwner().getUsername():"");
						setUserProfileName(resourceSearchResultDo.getGooruUId());
					}else{
						setUserNameDisplay((resourceSearchResultDo.getOwner()!=null&&resourceSearchResultDo.getOwner().getUsername()!=null)?resourceSearchResultDo.getOwner().getUsername():"");
					}
				}
			});
		
		if(resourceSearchResultDo.getOwner().isProfileUserVisibility()){
			setUserNameDisplay((resourceSearchResultDo.getOwner()!=null&&resourceSearchResultDo.getOwner().getUsername()!=null)?resourceSearchResultDo.getOwner().getUsername():"");
			setUserProfileName(resourceSearchResultDo.getGooruUId());
		}
		setCollectionMetadata();
		setCollectionLink();
	}
	
	public void setCollectionTitle(String collectionTitle){
		this.collectionTitle.setText(collectionTitle);
	}
	
	public void setUserNameDisplay(String userNameDisplay){
		userName.setText(userNameDisplay);
	}
	
	public void setCollectionMetadata(){
		int resourceCount = resourceSearchResultDo.getResourceCount();
		int questionCount = resourceSearchResultDo.getQuestionCount();
		metadataContainer.clear();
		metadataContainer.add(getCourseNames());
		metadataContainer.add(getViewsLabel());
		if(resourceCount>0 || (resourceCount==0&&questionCount==0)){
			metadataContainer.add(getSeparator());
			metadataContainer.add(setResourceCount(resourceCount));
		}
		if(questionCount>0) {
			metadataContainer.add(getSeparator());
			metadataContainer.add(setQuestionCount(questionCount));
		}
	}
	
	public void setUserProfileName(String gooruUid) {
		Anchor anchor=new Anchor();
		String userNameText=resourceSearchResultDo.getOwner().getUsername();
		if(StringUtil.isPartnerUser(userNameText)){
			anchor.setHref("#"+userNameText);
		}else{
            String token= "#"+PlaceTokens.PROFILE_PAGE+"&id="+gooruUid;
			anchor.setHref(token);
		}
		anchor.setText(userNameText);
		anchor.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().setUserText());
		anchor.setTarget("_blank");
		userName.setText("");
		userName.getElement().appendChild(anchor.getElement());
	}	
	private HTMLPanel getCourseNames(){
		HTMLPanel cousreContainer=new HTMLPanel("");
		List<String> coursesList=resourceSearchResultDo.getCourseNames();
		if(coursesList!=null&&coursesList.size()>0){
			Label courseName=new Label(coursesList.get(0));
			courseName.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getCourseTitle());
			cousreContainer.add(courseName);
			if(coursesList.size()>1){
				Label courseCount=new Label("+"+(coursesList.size()-1));
				courseCount.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getCourseCount());
				cousreContainer.add(courseCount);
				Widget Coursewidget = getToolTipwidgets(coursesList);
				courseCount.addMouseOverHandler(new MouseOverShowToolTip(Coursewidget));
				courseCount.addMouseOutHandler(new MouseOutHideToolTip());
			}
			cousreContainer.add(getSeparator());
		}
		return cousreContainer;
	}
	
	private Widget getToolTipwidgets(List<String> coursesList) {
		FlowPanel toolTipwidgets = new FlowPanel();
		if(coursesList!=null && coursesList.size()>0){
			for(int i=1;i<coursesList.size();i++){
				Label courseLabel = new Label(coursesList.get(i));
				toolTipwidgets.add(courseLabel);
			}
		}
		return toolTipwidgets;
	}

	private Label getViewsLabel(){
		String views=resourceSearchResultDo.getTotalViews()==1?resourceSearchResultDo.getTotalViews()+" "+i18n.GL1428():resourceSearchResultDo.getTotalViews()+" "+i18n.GL1099();
		Label viewsLabel=new Label(views);
		viewsLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getCourseTitle());
		return viewsLabel;
	}
	
	private HTMLPanel getSeparator(){
		HTMLPanel separator=new HTMLPanel(SEPARATOR);
		separator.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().sourceSepartor());
		return separator;
	}
	
	private Label setResourceCount(int count){
		String resourcesCount=count==1?count+" "+i18n.GL1110():count+" "+i18n.GL0174();
		Label resourceCountLabel=new Label(resourcesCount);
		resourceCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getResourceCount());
		return resourceCountLabel;
	}

	private Label setQuestionCount(int count){
		String questionCount=count==1?count+" "+i18n.GL0308():count+" "+i18n.GL1042();
		Label resourceCountLabel=new Label(questionCount);
		resourceCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getResourceCount());
		return resourceCountLabel;
	}

	@UiHandler("collectionThumbnail")
	public void setDefaultCollectionThumbnail(ErrorEvent event){
		collectionThumbnail.setUrl("images/default-collection-image-160x120.png");
	}
	
	@Override
	public void onLoad(){
		if(resourceSearchResultDo.getUrl()==null){
			collectionThumbnail.setUrl("images/default-collection-image-160x120.png");
		}else{
			collectionThumbnail.setUrl(resourceSearchResultDo.getUrl());
		}
	}
	
	public void setCollectionLink(){
		String viewToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		if(viewToken=="resource-play")
		{
			viewToken=viewToken.replaceAll("resource-play","preview-play");
		}
		String collectionUrl = "#"+viewToken+"&id="+resourceSearchResultDo.getGooruOid();
		
		Anchor imageAnchor = new Anchor();
		imageAnchor.setHref(collectionUrl);
		imageAnchor.setTarget("_blank");
		
		collectionTitle.setHref(collectionUrl);
		collectionTitle.setTarget("_blank");
		
		imageAnchor.setStyleName("");
		imageAnchor.getElement().appendChild(collectionThumbnail.getElement());
		imageContainer.add(imageAnchor);
	}
	
	public class MouseOverShowToolTip implements MouseOverHandler
	{
		Widget widget;
		public MouseOverShowToolTip(Widget coursewidget) {
			this.widget = coursewidget;
		}
		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTip = new ToolTipPopUp(widget,(event.getRelativeElement().getAbsoluteLeft()-45),(event.getRelativeElement().getAbsoluteTop()+22)); 
			toolTip.show();
		}
	}
	
	public class MouseOutHideToolTip implements MouseOutHandler
	{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTip.hide();
		}
	}
}
