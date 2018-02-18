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
package org.ednovo.gooru.client.mvp.assessments.play.collection.toc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.mvp.assessments.play.collection.NoQuestionAttemptPopupUc;
import org.ednovo.gooru.client.mvp.assessments.play.collection.SubmitYourAnswersPopupUc;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.home.ResourceCurosal;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.metadata.NavigationConfirmPopup;
import org.ednovo.gooru.client.mvp.assessments.play.collection.uc.TocAssessmentsEndView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.uc.TocAssessmentsHomeView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.uc.TocAssessmentsResourceView;
import org.ednovo.gooru.client.uc.BrowserAgent;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class AssessmentsPlayerTocView extends BaseViewWithHandlers<AssessmentsPlayerTocUiHandlers> implements IsAssessmentsPlayerTocView,ClientConstants{

	@UiField FlowPanel navgationTocContainer,carouselContainer;
	@UiField Label previousButton,nextButton,hideText,resourceCountLabel;

	@UiField HTMLEventPanel hideButton;

	private int selectedWidgetIndex=-1;

	private static CollectionPlayerTocViewUiBinder uiBinder = GWT.create(CollectionPlayerTocViewUiBinder.class);

	interface CollectionPlayerTocViewUiBinder extends UiBinder<Widget, AssessmentsPlayerTocView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public AssessmentsPlayerTocView(){
		setWidget(uiBinder.createAndBindUi(this));
		hideText.setText(i18n.GL0592());
		hideText.getElement().setId("lblHideText");
		hideText.getElement().setAttribute("alt",i18n.GL0592());
		hideText.getElement().setAttribute("title",i18n.GL0592());

		previousButton.getElement().setId("lblPreviousButton");
		navgationTocContainer.getElement().setId("fpnlNavgationTocContainer");
		nextButton.getElement().setId("lblNextButton");
		hideButton.getElement().setId("epnlHideButton");
		if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.ASSESSMENT_PLAY)){
			hideButton.setVisible(false);
		}
	}
	public void clearNavigationPanel(){
		navgationTocContainer.clear();
		selectedWidgetIndex = -1;
	}
	public void hideResourceCountLabel(boolean hide){
		resourceCountLabel.setVisible(!hide);
	}

	public void clearMarginLeft(){
		navgationTocContainer.getElement().getStyle().clearMarginLeft();
	}
	@Override
	public void setNavigationResources(CollectionDo collectionDo,boolean isCollectionHome){

		if(collectionDo!=null){
			int resourcesSize=collectionDo.getCollectionItems()!=null?collectionDo.getCollectionItems().size():0;
			String collectionType=StringUtil.isEmpty(collectionDo.getCollectionType())?null:collectionDo.getCollectionType();
			if(navgationTocContainer.getWidgetCount()==0){
				int resourceCount=0;
				int questionCount=0;
				navgationTocContainer.clear();
				selectedWidgetIndex = -1;
				nextButton.setVisible(true);
				previousButton.setVisible(true);
				List<CollectionItemDo> collectionItems=collectionDo.getCollectionItems();
				TocAssessmentsHomeView tocCollectionHomeView=null;
				if(collectionDo.getThumbnails() != null && collectionDo.getThumbnails().getUrl()!=null)
				{
					tocCollectionHomeView=new TocAssessmentsHomeView(collectionDo.getThumbnails().getUrl(),collectionType);
				}
				else
				{
					if(collectionDo.getCollectionType().equals("assessment")){
						tocCollectionHomeView=new TocAssessmentsHomeView("images/default-assessment-image -160x120.png",collectionType);
					}else{
						tocCollectionHomeView=new TocAssessmentsHomeView("images/default-collection-image-160x120.png",collectionType);
					}
				}

				if(!isCollectionHome){
					tocCollectionHomeView.hideResourceThumbnailContainer(true);
				}
				tocCollectionHomeView.addClickHandler(new HomeRequest());
				navgationTocContainer.add(tocCollectionHomeView);
				if(collectionDo.getCollectionItems()!=null && collectionDo.getCollectionItems().size()>0){
					for(int i=0;i<collectionItems.size();i++){
						if(collectionDo.getCollectionItems().get(i).getResource()!=null&&collectionDo.getCollectionItems().get(i).getResource().getResourceFormat()!=null){
							if(QUESTION.equalsIgnoreCase(collectionDo.getCollectionItems().get(i).getResource().getResourceFormat().getDisplayName())){
								questionCount++;
							}else{
								resourceCount++;
							}
						}
						CollectionItemDo collectionItemDo=collectionItems.get(i);
						TocAssessmentsResourceView tocResoruceView=new TocAssessmentsResourceView(collectionItemDo,i+1,true,false);
						tocResoruceView.addClickHandler(new ResourceRequest(collectionItemDo));
						tocResoruceView.setCollectionItemId(collectionItemDo.getCollectionItemId());
						if(!isCollectionHome){
							tocResoruceView.hideResourceThumbnailContainer(true);
						}
						navgationTocContainer.add(tocResoruceView);
					}

			}
				TocAssessmentsEndView tocCollectionEndView=null;
				if(collectionDo.getThumbnails() != null && collectionDo.getThumbnails().getUrl()!=null)
				{
					tocCollectionEndView=new TocAssessmentsEndView(collectionDo.getThumbnails().getUrl(), collectionType);
				}
				else
				{
					if(collectionDo.getCollectionType().equals("assessment")){
						tocCollectionEndView=new TocAssessmentsEndView("images/default-assessment-image -160x120.png",collectionType);
					}else{
						tocCollectionEndView=new TocAssessmentsEndView("images/default-collection-image-160x120.png",collectionType);
					}
				}
				tocCollectionEndView.addClickHandler(new EndRequest());
				if(!isCollectionHome){
					tocCollectionEndView.hideResourceThumbnailContainer(true);
				}
				navgationTocContainer.add(tocCollectionEndView);
				boolean device = BrowserAgent.isDevice();
				if (device){
					navgationTocContainer.getElement().removeAttribute("style");
					//new ResourceCurosal(nextButton, previousButton, navgationTocContainer, resourcesSize+2, 100,carouselContainer);
					new ResourceCurosal(nextButton, previousButton, navgationTocContainer, resourcesSize+2, 100,carouselContainer);
				}else{
					//resources width with padding and margin constitutes 100px for each and collection home and end with padding and margin width
					//have 100px each. navgationTocContainer width is derived from this.
					if(resourcesSize>7){
						navgationTocContainer.getElement().removeAttribute("style");
						//new ResourceCurosal(nextButton, previousButton, navgationTocContainer, resourcesSize+2, 100,carouselContainer);
						new ResourceCurosal(nextButton, previousButton, navgationTocContainer, resourcesSize+2, 100,carouselContainer);
					}else{
						nextButton.getElement().getStyle().setVisibility(Visibility.HIDDEN);
						previousButton.getElement().getStyle().setVisibility(Visibility.HIDDEN);
						navgationTocContainer.getElement().setAttribute("style", "width:"+((resourcesSize+2)*100)+"px !important;");
					}
				}

				String resourceString = resourceCount == 1? resourceCount + " " + i18n.GL1110().toLowerCase() : resourceCount + " " + i18n.GL0174().toLowerCase();
				String questionString = questionCount == 1? questionCount + " " + i18n.GL0308().toLowerCase() : questionCount + " " + i18n.GL1042().toLowerCase();
				String finalMessage = "";

				String message=(collectionDo.getCollectionType()!=null&&collectionDo.getCollectionType().equals("assessment"))?i18n.GL3042():i18n.GL0578();

				if (resourceCount >0 && questionCount > 0){
					finalMessage = resourceString + " " + i18n.GL_GRR_AND() + " " + questionString + " " + message + i18n.GL_SPL_SEMICOLON()+" ";
				}else if (resourceCount >0){
					finalMessage = resourceString + " " + message + i18n.GL_SPL_SEMICOLON()+" ";
				}else if (questionCount >0){
					finalMessage = questionString + " " + message + i18n.GL_SPL_SEMICOLON()+" ";
				}
				resourceCountLabel.setText(finalMessage);

			}else{
				setResourceThumbnailVisibility(isCollectionHome);
			}
		}

	}

	public void setResourceThumbnailVisibility(boolean visibility){
		int widgetsCount=navgationTocContainer.getWidgetCount();
		for(int i=0;i<widgetsCount;i++){
			Widget widget=navgationTocContainer.getWidget(i);
			if(widget instanceof TocAssessmentsHomeView){
				((TocAssessmentsHomeView)widget).hideResourceThumbnailContainer(!visibility);
			}else if(widget instanceof TocAssessmentsResourceView){
				((TocAssessmentsResourceView)widget).hideResourceThumbnailContainer(!visibility);
			} else if(widget instanceof TocAssessmentsEndView){
				((TocAssessmentsEndView)widget).hideResourceThumbnailContainer(!visibility);
			}
		}

	}
	@Override
	public void setResourceActive(String collectionId,String collectionItemid,boolean isCollectionHome){
		if(selectedWidgetIndex!=-1){
			Widget widget=navgationTocContainer.getWidget(selectedWidgetIndex);
			widget.removeStyleName(PlayerBundle.INSTANCE.getPlayerStyle().tocResourceSelected());
		}
		if(collectionItemid!=null){
			int widgetCount=navgationTocContainer.getWidgetCount();
			for(int i=0;i<widgetCount;i++){
				Widget widget=navgationTocContainer.getWidget(i);
				if(widget instanceof TocAssessmentsResourceView){
					TocAssessmentsResourceView resourceView=(TocAssessmentsResourceView)widget;
					if(collectionItemid.equals(resourceView.getCollectionItemId())){
						selectedWidgetIndex=i;
						resourceView.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().tocResourceSelected());
						return;
					}
				}
			}
		}else if(isCollectionHome){
			selectedWidgetIndex=0;
			Widget widget=navgationTocContainer.getWidget(selectedWidgetIndex);
			widget.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().tocResourceSelected());
		}else{
			int widgetCount=navgationTocContainer.getWidgetCount();
			selectedWidgetIndex=widgetCount-1;
			Widget widget=navgationTocContainer.getWidget(selectedWidgetIndex);
			widget.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().tocResourceSelected());
		}

	}

	public class ResourceRequest implements ClickHandler{
		private CollectionItemDo collectionItemDo;
		public ResourceRequest(CollectionItemDo collectionItemDo){
			this.collectionItemDo=collectionItemDo;
		}
		public void onClick(ClickEvent event){
			Map<String,String> params = new LinkedHashMap<String,String>();
			String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			params.put("id", collectionId);
			params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);
			String viewToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
			if(collectionItemDo.getNarration()!=null&&!collectionItemDo.getNarration().trim().equals("")){
				params.put("rid", collectionItemDo.getCollectionItemId());
				params.put("tab", "narration");
				final PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(viewToken, params);
				if(!getUiHandlers().isOpenEndedAnswerSubmited()){
					NavigationConfirmPopup confirmPopup=new NavigationConfirmPopup() {
						@Override
						public void navigateToNextResource() {
							super.hide();
							AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
						}
					};
				}else{
					AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
				}
			}else{
				params.put("rid", collectionItemDo.getCollectionItemId());
				final PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(viewToken, params);
				if(!getUiHandlers().isOpenEndedAnswerSubmited()){
					NavigationConfirmPopup confirmPopup=new NavigationConfirmPopup() {
						@Override
						public void navigateToNextResource() {
							super.hide();
							AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
						}
					};
				}else{
					AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
				}
			}
		}
	}

	public class EndRequest implements ClickHandler{
		public void onClick(ClickEvent event){
			String collectionItemId = AppClientFactory.getPlaceManager().getRequestParameter("rid", null);
			
			if(collectionItemId!=null) {
				Map<String,String> params = new LinkedHashMap<String,String>();
				String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
				params.put("id", collectionId);
				params.put("view", "end");
				params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);
				String viewToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
				//PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(viewToken, params);
				final PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(viewToken, params);
				
				if (!AppClientFactory.isAnonymous()){
					SubmitYourAnswersPopupUc submit = new SubmitYourAnswersPopupUc() {
						@Override
						public void onClickSubmit(ClickEvent event) {
							if(!getUiHandlers().isOpenEndedAnswerSubmited()){
								NavigationConfirmPopup confirmPopup=new NavigationConfirmPopup() {
									@Override
									public void navigateToNextResource() {
										super.hide();
										AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
									}
								};
							}else{
								AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
							}
							super.hide();
						}
					};
					submit.show();
					submit.center();
				} else {
					AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
				}
			} else {
				NoQuestionAttemptPopupUc alertContentUc=new NoQuestionAttemptPopupUc();
			}
		}
	}

	public class HomeRequest implements ClickHandler{
		public void onClick(ClickEvent event){
			Map<String,String> params = new LinkedHashMap<String,String>();
			String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			params.put("id", collectionId);
			params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);
			String viewToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
			final PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(viewToken, params);
			if(!getUiHandlers().isOpenEndedAnswerSubmited()){
				NavigationConfirmPopup confirmPopup=new NavigationConfirmPopup() {
					@Override
					public void navigateToNextResource() {
						super.hide();
						AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
					}
				};
			}else{
				AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
			}
		}
	}
	/**
	 *
	 * @function onhideBtnClicked
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
	@UiHandler("hideButton")
	public void onhideBtnClicked(ClickEvent clickEvent)
	{
		PlaceRequest collectionRequest = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
		String collectionId = collectionRequest.getParameter("id", null);
		String collectionItemId = collectionRequest.getParameter("rid", null);
		String chkViewParam = collectionRequest.getParameter("view", null);

		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionId);
		params = AssessmentsPreviewPlayerPresenter.setConceptPlayerParameters(params);

	if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.RESOURCE_PLAY))
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.RESOURCE_PLAY).
				with("id", collectionId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.ASSESSMENT_PLAY) && chkViewParam == null && collectionItemId != null)
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.ASSESSMENT_PLAY).
				with("id", collectionId).with("rid", collectionItemId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId != null)
	{
		params.put("rid", collectionItemId);
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.ASSESSMENT_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.ASSESSMENT_PLAY).
				with("id", collectionId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.ASSESSMENT_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.ASSESSMENT_PLAY).
				with("id", collectionId).with("view", "end");
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		params.put("view", "end");
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);

	}
	}




}
