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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.rating.RatingWidgetView;
import org.ednovo.gooru.client.mvp.rating.events.UpdateRatingsInSearchEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateResourceRatingCountEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateResourceRatingCountEventHandler;
import org.ednovo.gooru.client.mvp.search.SearchUiUtil;
import org.ednovo.gooru.client.mvp.search.event.UpdateSearchResultMetaDataEvent;
import org.ednovo.gooru.client.mvp.search.event.UpdateSearchResultMetaDataHandler;
import org.ednovo.gooru.client.mvp.shelf.event.InsertCollectionItemInAddResourceEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshCollectionItemInShelfListEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.uc.BlueButtonUc;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.ResourceImageUc;
import org.ednovo.gooru.client.uc.SeparatorUc;
import org.ednovo.gooru.client.uc.StandardSgItemVc;
import org.ednovo.gooru.client.uc.tooltip.ToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


public abstract  class AddSearchSuggestedResourceView extends Composite {


	public interface AddSearchSuggestedResourceViewUiBinder extends UiBinder<Widget, AddSearchSuggestedResourceView> {

	}

	public  AddSearchSuggestedResourceViewUiBinder uiBinder=GWT.create(AddSearchSuggestedResourceViewUiBinder.class);

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField SearchSuggestedResultWrapperCBundle res;

	@UiField HTML lblResourceTitle;

	@UiField Image imgNotFriendly;

	@UiField
	HTMLEventPanel resourceTitleContainer;

	@UiField
	FlowPanel metaDataFloPanel;

	@UiField
	FlowPanel ratingWidgetPanel,resourceHeaderPanel,contentPanel,resourceTitlePanel;

	@UiField
	HTML resourceDescriptionHtml;

	@UiField
	ResourceImageUc resourceImageUc;

	@UiField
	FlowPanel standardsFloPanel;

	@UiField HTMLPanel suggestedWrapperPanel,addResourceBtnPanel,buttonsPanel;

	@UiField
	public BlueButtonUc addResourceBtnLbl;

	ToolTip toolTip = null;

	private ResourceSearchResultDo resourceSearchResultDo;

	private RatingWidgetView ratingWidgetView=null;


	private static final String PLAYER_NAME = "resource";

	private static final String VIDEO = "Video";

	private static final String QUESTION = "Question";

	private static final String PAGES = " "+i18n.GL1471();

	private static final String VIEW = " "+i18n.GL1428();

	private static final String VIEWS = " "+i18n.GL0934();

	private static final String NULL = "null";
	private static String publisherData = "";
	private static String aggregatorData = "";
	private boolean isRatingUpdated=true;

	String collectionId = "";
	private AddResourceView addresourceview;

	public static final String STANDARD_CODE = "code";

	public static final String STANDARD_DESCRIPTION = "description";

	AddSearchSuggestedResourceView() {
		initWidget(uiBinder.createAndBindUi(this));
		res.css().ensureInjected();
		suggestedWrapperPanel.getElement().setId("pnlSuggestedWrapperPanel");
		addResourceBtnPanel.getElement().setId("pnlAddResourceBtnPanel");
		addResourceBtnLbl.getElement().setId("bluebtnAddResourceBtnLbl");
		ratingWidgetPanel.getElement().setId("fpnlRatingWidgetPanel");
		contentPanel.getElement().setId("fpnlContentPanel");
		resourceHeaderPanel.getElement().setId("fpnlResourceHeaderPanel");
		resourceImageUc.getElement().setId("resourceImageUc");
		resourceTitlePanel.getElement().setId("fpnlResourceTitlePanel");
		resourceTitleContainer.getElement().setId("epnlResourceTitleContainer");
		imgNotFriendly.getElement().setId("imgImgNotFriendly");
		metaDataFloPanel.getElement().setId("fpnlMetaDataFloPanel");
		standardsFloPanel.getElement().setId("fpnlStandardsFloPanel");
		resourceDescriptionHtml.getElement().setId("htmlResourceDescriptionHtml");
		buttonsPanel.getElement().setId("pnlButtonsPanel");
	}

	public AddSearchSuggestedResourceView(
			ResourceSearchResultDo resourceSearchResultDo,String collectionid) {
		initWidget(uiBinder.createAndBindUi(this));
		res.css().ensureInjected();
		buttonsPanel.setVisible(true);
		this.collectionId = collectionid;
		addResourceBtnLbl.setText(i18n.GL0590_1());
		addResourceBtnLbl.getElement().setAttribute("alt", i18n.GL0590_1());
		addResourceBtnLbl.getElement().setAttribute("title", i18n.GL0590_1());
		addResourceBtnLbl.addClickHandler(new AddClickHandler());
		imgNotFriendly.setTitle(i18n.GL0737());

		suggestedWrapperPanel.getElement().setId("pnlSuggestedWrapperPanel");
		addResourceBtnPanel.getElement().setId("pnlAddResourceBtnPanel");
		addResourceBtnLbl.getElement().setId("bluebtnAddResourceBtnLbl");
		imgNotFriendly.setAltText(i18n.GL0737());
		imgNotFriendly.getElement().setId("imgImgNotFriendly");
		imgNotFriendly.setUrl("images/mos/MobileFriendly.png");
		AppClientFactory.getEventBus().addHandler(UpdateSearchResultMetaDataEvent.TYPE,setUpdateMetaData);
		AppClientFactory.getEventBus().addHandler(UpdateResourceRatingCountEvent.TYPE,setRatingCount);
		ratingWidgetView=new RatingWidgetView();
		ratingWidgetPanel.add(ratingWidgetView);
		ratingWidgetPanel.getElement().setId("fpnlRatingWidgetPanel");
		contentPanel.getElement().setId("fpnlContentPanel");
		resourceHeaderPanel.getElement().setId("fpnlResourceHeaderPanel");
		resourceImageUc.getElement().setId("suggestedResourceImageUc");
		resourceTitlePanel.getElement().setId("fpnlResourceTitlePanel");
		resourceTitleContainer.getElement().setId("epnlResourceTitleContainer");
		metaDataFloPanel.getElement().setId("fpnlMetaDataFloPanel");
		standardsFloPanel.getElement().setId("fpnlStandardsFloPanel");
		resourceDescriptionHtml.getElement().setId("htmlResourceDescriptionHtml");
		buttonsPanel.getElement().setId("pnlButtonsPanel");
		setData(resourceSearchResultDo);
	}

	public RatingWidgetView getRatingWidgetView(){
		return ratingWidgetView;
	}

	public void updateViews(String count, String contentId, String whatToUpdate){
		if (resourceSearchResultDo.getGooruOid()!=null && resourceSearchResultDo.getGooruOid().equalsIgnoreCase(contentId)){
			metaDataFloPanel.clear();
			String category = (resourceSearchResultDo.getNewResourceFormat()!=null && resourceSearchResultDo.getNewResourceFormat().getValue() != null)? resourceSearchResultDo.getNewResourceFormat().getValue() : "webpage";
			boolean shortenMetaLength = category.equalsIgnoreCase(VIDEO) || category.equalsIgnoreCase(QUESTION) ? true : false;

			if(resourceSearchResultDo.getAggregator()!=null){
				 String aggregatorData = "";
				for (String aggregator: resourceSearchResultDo.getAggregator()) {
					if(resourceSearchResultDo.getAggregator().size()>1){
						aggregatorData = aggregatorData+aggregator+",";
					}
					else
					{
						aggregatorData = aggregator;
					}
				}
				if(aggregatorData.endsWith(",")){
					aggregatorData=aggregatorData.substring(0, aggregatorData.length()-1);
				}
			}

			if(resourceSearchResultDo.getPublisher()!=null){
				String publisherData = "";
				for (String publisher: resourceSearchResultDo.getPublisher()) {
					if(resourceSearchResultDo.getPublisher().size()>1){
						publisherData = publisherData+publisher+",";
					}else
					{
						publisherData = publisher;
					}

				}
				if(publisherData.endsWith(",")){
					publisherData=publisherData.substring(0, publisherData.length()-1);
				}
			}
			renderMetaData(metaDataFloPanel, resourceSearchResultDo.getCourseNames(), shortenMetaLength ? 15 : 18);

	       renderMetaData(metaDataFloPanel, count + (Integer.parseInt(count) == 1 ? VIEW : VIEWS));
			if (category.equalsIgnoreCase(VIDEO)) {
				SearchUiUtil.renderMetaData(metaDataFloPanel, StringUtil.stringToTime(resourceSearchResultDo.getDurationInSec()));
			} else if (category.equalsIgnoreCase(QUESTION)) {
			}
			if (resourceSearchResultDo.getNumOfPages() != null && !resourceSearchResultDo.getNumOfPages().equals("0") && !resourceSearchResultDo.getNumOfPages().equalsIgnoreCase(NULL) && resourceSearchResultDo.getNumOfPages().length() > 0) {
				SearchUiUtil.renderMetaData(metaDataFloPanel, resourceSearchResultDo.getNumOfPages() + PAGES);
			}
		}
	}

	public void setData(ResourceSearchResultDo resourceSearchResultDo) {
		this.resourceSearchResultDo = resourceSearchResultDo;
		if(resourceSearchResultDo.getRatings()!=null && resourceSearchResultDo.getRatings().getCount()!=0){
			ratingWidgetView.getRatingCountOpenBrace().setText(i18n. GL_SPL_OPEN_SMALL_BRACKET());
			ratingWidgetView.getRatingCountLabel().setText(resourceSearchResultDo.getRatings().getCount().toString());
			ratingWidgetView.getRatingCountCloseBrace().setText(i18n. GL_SPL_CLOSE_SMALL_BRACKET());
		}
		ratingWidgetView.setAvgStarRating(resourceSearchResultDo.getRatings().getAverage());
		String category = resourceSearchResultDo.getNewResourceFormat().getValue() != null ? resourceSearchResultDo.getNewResourceFormat().getValue() : "webpage";
		String description = resourceSearchResultDo.getDescription();
        String title = "";
        String resourceTitle;
        if (resourceSearchResultDo.getResourceTitle().contains("class")){
        	title = resourceSearchResultDo.getResourceTitle();
        }else{
        	title = StringUtil.truncateText(resourceSearchResultDo.getResourceTitle(), 38);
        }

		boolean shortenMetaLength = category.equalsIgnoreCase(VIDEO) || category.equalsIgnoreCase(QUESTION) ? true : false;
		if(resourceSearchResultDo.getAggregator()!=null){
			 String aggregatorData = "";
			for (String aggregator: resourceSearchResultDo.getAggregator()) {
				if(resourceSearchResultDo.getAggregator().size()>1){
					aggregatorData = aggregatorData+aggregator+",";
				}
				else
				{
					aggregatorData = aggregator;
				}
			}
			if(aggregatorData.endsWith(",")){
				aggregatorData=aggregatorData.substring(0, aggregatorData.length()-1);
			}
		}

		if(resourceSearchResultDo.getPublisher()!=null){
			String publisherData = "";
			for (String publisher: resourceSearchResultDo.getPublisher()) {
				if(resourceSearchResultDo.getPublisher().size()>1){
					publisherData = publisherData+publisher+",";
				}else
				{
					publisherData = publisher;
				}

			}
			if(publisherData.endsWith(",")){
				publisherData=publisherData.substring(0, publisherData.length()-1);
			}
		}
		renderMetaData(metaDataFloPanel, resourceSearchResultDo.getCourseNames(), shortenMetaLength ? 15 : 18);
        renderMetaData(metaDataFloPanel, resourceSearchResultDo.getTotalViews() + (resourceSearchResultDo.getTotalViews() == 1 ? VIEW : VIEWS));
		if (category.equalsIgnoreCase(VIDEO)) {
			SearchUiUtil.renderMetaData(metaDataFloPanel, StringUtil.stringToTime(resourceSearchResultDo.getDurationInSec()));
		} else if (category.equalsIgnoreCase(QUESTION)) {
		}
		if (resourceSearchResultDo.getNumOfPages() != null && !resourceSearchResultDo.getNumOfPages().equals("0") && !resourceSearchResultDo.getNumOfPages().equalsIgnoreCase(NULL) && resourceSearchResultDo.getNumOfPages().length() > 0) {
			SearchUiUtil.renderMetaData(metaDataFloPanel, resourceSearchResultDo.getNumOfPages() + PAGES);
		}
		title = title.replaceAll("<p>", "").replaceAll("</p>", "");
		lblResourceTitle.setHTML(title);
		lblResourceTitle.getElement().setAttribute("alt", title);
		lblResourceTitle.getElement().setAttribute("title", title);
		resourceTitle=resourceSearchResultDo.getResourceTitle();
		lblResourceTitle.getElement().setId(resourceSearchResultDo.getGooruOid());
		if (lblResourceTitle.getText().length()>38){
		}
		String mediaType = resourceSearchResultDo.getMediaType();
		boolean setVisibility = mediaType !=null ?  mediaType.equalsIgnoreCase("iPad_friendly") ? true : false : true;

		if(setVisibility)
		{
			imgNotFriendly.getElement().setId("imgImgFriendly");
			imgNotFriendly.setTitle(i18n.GL0737_1());
			imgNotFriendly.setAltText(i18n.GL0737_1());
			imgNotFriendly.setUrl("images/mos/MobileFriendly.png");
		}else
		{
			imgNotFriendly.getElement().setId("imgImgNotFriendly");
			imgNotFriendly.setTitle(i18n.GL0737());
			imgNotFriendly.setAltText(i18n.GL0737());
			imgNotFriendly.setUrl("images/mos/mobileunfriendly.png");
		}
		if (imgNotFriendly.isVisible()){
			lblResourceTitle.getElement().getStyle().setFloat(Float.LEFT);
		}else{
			lblResourceTitle.getElement().getStyle().clearFloat();
		}


		imgNotFriendly.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				toolTip = new ToolTip(i18n.GL0454()+""+" "+i18n.GL04431()+" "+" ");
				toolTip.getTootltipContent().getElement().setAttribute("style", "width: 258px;");
				toolTip.getElement().getStyle().setBackgroundColor("transparent");
				toolTip.getElement().getStyle().setZIndex(9999999);
				toolTip.getElement().getStyle().setPosition(Position.ABSOLUTE);
				toolTip.setPopupPosition(imgNotFriendly.getAbsoluteLeft()-(50+22), imgNotFriendly.getAbsoluteTop()+22);
				toolTip.show();
			}
		});
		imgNotFriendly.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {

				EventTarget target = event.getRelatedTarget();
				  if (Element.is(target)) {
					  if (!toolTip.getElement().isOrHasChild(Element.as(target))){
						  toolTip.hide();
					  }
				  }
			}
		});
		if(description!=null && description.length()>205){
			description = description.trim().substring(0, 205) +"...";
		}
		resourceDescriptionHtml.setHTML(description);
		resourceTitleContainer.getElement().getStyle().setZIndex(99999);
		resourceImageUc.getElement().getStyle().setZIndex(99999);
		resourceImageUc.renderSearch(category, resourceSearchResultDo.getUrl(), null, resourceSearchResultDo.getGooruOid(), PLAYER_NAME, resourceTitle, false,"","");
		renderStandards(standardsFloPanel, resourceSearchResultDo);
	}

	UpdateResourceRatingCountEventHandler setRatingCount =new UpdateResourceRatingCountEventHandler(){
		@Override
		public void setResourceRatingCount(String resourceId,double avg,Integer count) {
			if(resourceId.equals(resourceSearchResultDo.getGooruOid())){
				if(count!=null){
					ratingWidgetView.getRatingCountLabel().setText(Integer.toString(count));
					ratingWidgetView.setAvgStarRating(avg);
						if(count==1 && isRatingUpdated){
							isRatingUpdated=false;
							ratingWidgetView.getRatingCountLabel().getElement().removeAttribute("class");
							ratingWidgetView.getRatingCountLabel().getElement().setAttribute("style", "cursor: pointer;text-decoration: none !important;color: #1076bb;");
							ratingWidgetView.getRatingCountLabel().addClickHandler(new ClickHandler(){
								@Override
								public void onClick(ClickEvent event) {
									AppClientFactory.fireEvent(new UpdateRatingsInSearchEvent(resourceSearchResultDo));
								}

							});
						}
				}
			}
		}

	};



	UpdateSearchResultMetaDataHandler setUpdateMetaData =new UpdateSearchResultMetaDataHandler(){

		@Override
		public void updateSearchResultMetaData(String count, String resourceId,
				String whatToUpdate) {
			if(count!=null){
			}
		}
	};

	@UiHandler("resourceTitleContainer")
	public void onClickResourceTitle(ClickEvent event){
		MixpanelUtil.Preview_Resource_From_Search("ResourceTitleLbl");
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", resourceSearchResultDo.getGooruOid());
		params.put("pn", "resource");
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.RESOURCE_PLAY, params);
	}
	public abstract void closePopup();
	private class AddClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
		addResourceBtnPanel.setVisible(false);
		AppClientFactory.getInjector().getResourceService().createNewCollectionItem(collectionId, resourceSearchResultDo.getGooruOid(),resourceSearchResultDo.getNewResourceFormat().getValue(), new SimpleAsyncCallback<CollectionItemDo>() {
			@Override
			public void onSuccess(CollectionItemDo result) {
				Window.enableScrolling(true);
				closePopup();
				AppClientFactory.fireEvent(new RefreshCollectionItemInShelfListEvent(result, RefreshType.INSERT));
				AppClientFactory.fireEvent(new InsertCollectionItemInAddResourceEvent(result, RefreshType.INSERT));
			}
		});
	  }
	}
	public static void renderMetaData(FlowPanel flowPanel, String data) {
		renderMetaData(flowPanel, data, null, -1);
	}
	public static void renderMetaData(FlowPanel flowPanel, List<String> datas, int wrapLength) {
		if (datas == null) {
			return;
		}
		renderMetaData(flowPanel, (datas!=null&&datas.size() > 0)? datas.get(0) : null, null, wrapLength);
		FlowPanel toolTipwidgets = new FlowPanel();
		FlowPanel toolTipwidget1 = new FlowPanel();
		if(datas!=null){
			for (int count = 0; count < datas.size(); count++) {
				Label label = new Label(datas.get(count));
				label.setStyleName(SearchSuggestedResultWrapperCBundle.INSTANCE.css().moreMetaLbl());
				if(count==0){
					toolTipwidget1.add(label);
				}else{
					toolTipwidgets.add(label);
				}

			}
		}
		if (datas != null && datas.size() > 1) {
			Integer moreCount = datas.size() - 1;
			DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(i18n.GL_SPL_PLUS() + moreCount), toolTipwidgets);
			toolTipUc.setStyleName(SearchSuggestedResultWrapperCBundle.INSTANCE.css().blueLinkPad());
			flowPanel.add(toolTipUc);
		}
	}
	public static void renderMetaData(FlowPanel flowPanel, String data, String suffix, int wrapLength) {
		if (suffix != null || StringUtil.hasValidString(data)) {
			if (wrapLength > 0) {
				data = StringUtil.truncateText(data, wrapLength);
			}
			if (suffix != null) {
				data += suffix;
			}
			Label label = new Label(data);
			label.getElement().setAttribute("style", "display:inline-block;float: left;");
			renderMetaData(flowPanel, label);
		}
	}
	public static void renderMetaData(FlowPanel flowPanel, IsWidget widget) {
		if (flowPanel.iterator().hasNext()) {
			flowPanel.add(new SeparatorUc(""));
		}
		flowPanel.add(widget);
	}
	public static void renderStandards(FlowPanel standardsContainer, ResourceSearchResultDo searchResultDo) {
		if (searchResultDo.getStandards() != null) {
			List<Map<String, String>> standards = searchResultDo.getStandards();
			Iterator<Map<String, String>> iterator = standards.iterator();
			int count = 0;
			FlowPanel toolTipwidgets = new FlowPanel();
			while (iterator.hasNext()) {
				Map<String, String> standard = iterator.next();
				String stdCode = standard.get(STANDARD_CODE);
				String stdDec = standard.get(STANDARD_DESCRIPTION);
				if (count>0){
					if (count < 18){
						StandardSgItemVc standardItem = new StandardSgItemVc(stdCode, stdDec);
						toolTipwidgets.add(standardItem);
					}
				} else {
					DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(stdCode), new Label(stdDec), standards);
					toolTipUc.setStyleName("Uc-searchStandard");
					standardsContainer.add(toolTipUc);
				}
				count++;
			}
			if (standards.size()>18){
				final Label left = new Label(i18n.GL_SPL_PLUS()+(standards.size() - 18));
				toolTipwidgets.add(left);
			}
			if (searchResultDo.getStandards().size() > 1) {
				Integer moreStandardsCount = searchResultDo.getStandards().size() - 1;
				DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(i18n.GL_SPL_PLUS() + moreStandardsCount), toolTipwidgets, standards);
				toolTipUc.setStyleName(SearchSuggestedResultWrapperCBundle.INSTANCE.css().blueLink());
				standardsContainer.add(toolTipUc);
				toolTipUc.getTooltipPopUpUcCount(moreStandardsCount);

			}
		}
	}
}
