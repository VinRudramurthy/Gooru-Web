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
package org.ednovo.gooru.client.mvp.search;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.dnd.IsDraggable;
import org.ednovo.gooru.client.mvp.dnd.IsDraggableMirage;
import org.ednovo.gooru.client.mvp.resource.dnd.ResourceDragWithImgUc;
import org.ednovo.gooru.client.uc.ResourceImageUc;
import org.ednovo.gooru.client.uc.tooltip.ToolTip;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.model.search.CollectionItemSearchResultDo;
import org.ednovo.gooru.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author Search Team
 *
 */
public class SimpleResourceVc extends Composite implements IsDraggable,MessageProperties {

	private static SimpleResourceVcUiBinder uiBinder = GWT.create(SimpleResourceVcUiBinder.class);

	interface SimpleResourceVcUiBinder extends UiBinder<Widget, SimpleResourceVc> {
	}

	@UiField
	HTML resourceTitleLbl;
	
	@UiField HTMLPanel resourceTitleContainer;

	@UiField
	ResourceImageUc resourceImageUc;

	@UiField
	Label positionLbl;

	@UiField
	FlowPanel metaDataFloPanel;
	
	@UiField Image imgNotFriendly;

	ToolTip toolTip = null;
	
	private ResourceSearchResultDo resourceSearchResultDo;
	
	private CollectionItemSearchResultDo collectionItemSearchResultDo;
	
	private static final String VIEWS_PREFIX_NAME = "Views";  

	/**
	 * Class constructor, calls resource search result setData method
	 * @param resourceSearchResultDo instance of {@link ResourceSearchResultDo}
	 * @param position of search label
	 */
	public SimpleResourceVc(CollectionItemSearchResultDo resourceSearchResultDo, int position) {
		initWidget(uiBinder.createAndBindUi(this));
		positionLbl.setText(position + "");
		imgNotFriendly.setTitle(GL0737);
		imgNotFriendly.setAltText(GL0737);
		imgNotFriendly.setUrl("images/mos/ipadFriendly.png");
		setData(resourceSearchResultDo);
	}

	/**
	 * Set resource meta data info such as title, image, views, etc..
	 * @param resourceSearchResultDo instance of {@link ResourceSearchResultDo}
	 */
	public void setData(CollectionItemSearchResultDo resourceSearchResultDo) {
		this.resourceSearchResultDo = resourceSearchResultDo;
		this.collectionItemSearchResultDo = resourceSearchResultDo;
		/*resourceTitleLbl.setText(StringUtil.truncateText(resourceSearchResultDo.getResourceTitle(), 30));*/
		resourceTitleLbl.setHTML(resourceSearchResultDo.getResourceTitle());
		setResourcePlayLink();
		final String gooruOid = resourceSearchResultDo.getCollectionItemId();
		final String collectionId = resourceSearchResultDo.getCollectionId();
		resourceTitleLbl.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//Implementing Mixpanel
				if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)) {
					MixpanelUtil.Preview_Resource_From_Profile("resourceTitleLbl");
				} else {
					MixpanelUtil.Preview_Resource_From_Search("resourceTitleLbl");
				}
				AppClientFactory.getPlaceManager().setRefreshPlace(false);
			}
		});
		
		SearchUiUtil.renderMetaData(metaDataFloPanel, resourceSearchResultDo.getCourseNames(), 30);
		SearchUiUtil.renderMetaData(metaDataFloPanel, resourceSearchResultDo.getTotalViews() + "", " " + VIEWS_PREFIX_NAME);  
//		resourceImageUc.renderSearch(resourceSearchResultDo.getCategory(), resourceSearchResultDo.getUrl(), null, resourceSearchResultDo.getCollectionItemId(), PLAYER_NAME,resourceSearchResultDo.getResourceTitle(), false,collectionId);
		
		resourceImageUc.renderSearch(resourceSearchResultDo.getCategory(), resourceSearchResultDo.getUrl(), null, resourceSearchResultDo.getCollectionItemId(), resourceSearchResultDo.getResourceTitle(), false, resourceSearchResultDo.getNarration(),collectionItemSearchResultDo.getCollectionId());
		String mediaType = resourceSearchResultDo.getMediaType();
		
		boolean setVisibility = mediaType !=null ?  mediaType.equalsIgnoreCase("not_iPad_friendly") ? true : false : false;
		
		if (resourceTitleLbl.getText().length() > 30){
			resourceTitleLbl.getElement().getStyle().setWidth(210, Unit.PX);
			resourceTitleLbl.getElement().getStyle().setFloat(Float.LEFT);
		}else{
			resourceTitleLbl.getElement().getStyle().clearWidth();
			resourceTitleLbl.getElement().getStyle().clearFloat();
		}
		
		imgNotFriendly.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				
				toolTip = new ToolTip(GL0454+""+"<img src='/images/mos/ipadFriendly.png' style='margin-top:0px;'/>"+" "+GL04431);
				toolTip.getElement().getStyle().setBackgroundColor("transparent");
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
		imgNotFriendly.setVisible(setVisibility);
		
	}
	
	public void setResourcePlayLink(){
		Anchor resourceAnchor=new Anchor();
		resourceAnchor.setHref(getResourceLink());
		resourceAnchor.setStyleName("");
		resourceAnchor.getElement().appendChild(resourceTitleLbl.getElement());
		resourceTitleContainer.add(resourceAnchor);
	}
	
	public String getResourceLink(){
		String collectionId=collectionItemSearchResultDo.getCollectionId();
		if(collectionItemSearchResultDo.getNarration()!=null&&!collectionItemSearchResultDo.getNarration().trim().equals("")){
			String resourceLink="#"+PlaceTokens.PREVIEW_PLAY+"&id="+collectionId+"&rid="+collectionItemSearchResultDo.getCollectionItemId()+"&tab=narration";
			return resourceLink;
		}else{
			String resourceLink="#"+PlaceTokens.PREVIEW_PLAY+"&id="+collectionId+"&rid="+collectionItemSearchResultDo.getCollectionItemId();
			return resourceLink;
		}
	}


	@Override
	public String getDragId() {
		return resourceSearchResultDo.getGooruOid();
	}

	@Override
	public DRAG_TYPE getDragType() {
		return DRAG_TYPE.RESOURCE;
	}

	@Override
	public IsDraggableMirage initDraggableMirage() {
		return new ResourceDragWithImgUc(resourceSearchResultDo.getCategory(), resourceSearchResultDo.getResourceTitle());
	}

	@Override
	public void onDragBlur() {
	}

	@Override
	public Widget getDragHandle() {
		return null;
	}

	@Override
	public int getDragTopCorrection() {
		return 7;
	}

	@Override
	public int getDragLeftCorrection() {
		return 11;
	}
}
