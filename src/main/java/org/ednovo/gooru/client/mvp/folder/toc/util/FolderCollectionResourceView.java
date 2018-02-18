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
package org.ednovo.gooru.client.mvp.folder.toc.util;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderItemDo;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.UlPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @description : This widget is used to show Collection resources in FolderToc View page
 *
 * @version :1.3
 *
 * @date: Feb 10 2015

 * @Author Gooru Team
 * 
 * Reviewer Gooru Team
 *
 */
public class FolderCollectionResourceView extends Composite {

	private static FolderCollectionResourceViewUiBinder uiBinder = GWT
			.create(FolderCollectionResourceViewUiBinder.class);

	interface FolderCollectionResourceViewUiBinder extends
	UiBinder<Widget, FolderCollectionResourceView> {
	}

	@UiField
	UlPanel ulCollectionResources;
	FolderDo folderDo;

	FolderTocCBundle res;
	
	String parentId=null;

	LiPanel liPanel;
	ResourceTooltip resourceTooltip=new ResourceTooltip();

	public FolderCollectionResourceView(FolderDo folderDo,String parentId) {
		this.res = FolderTocCBundle.INSTANCE;
		res.css().ensureInjected();
		this.folderDo = folderDo;
		this.parentId=parentId;
		initWidget(uiBinder.createAndBindUi(this));
		if (folderDo != null) {
			setListData();
		}

	}
	/**
	 * This method used to show type and title of the resource.
	 */
	private void setListData() {
		if (folderDo.getCollectionItems().size() > 0) {
			int resourceCount = folderDo.getCollectionItems().size();
			String resourceType = "";
			for (int i = 0; i < resourceCount; i++) {
				liPanel = new LiPanel();
				Label sequenceNumber = new Label((i+1)+" ");
				sequenceNumber.setStyleName(res.css().sequenceNumner());
				liPanel.add(sequenceNumber);
				// To set the resource title and resource format image
				String resTitle = stripHtmlRegex(folderDo.getCollectionItems().get(i).getTitle());
				resTitle = resTitle.length() > 100 ? resTitle.substring(0, 100)+ "..." : resTitle;
				final Label text = new Label(resTitle);
				text.setStyleName(res.css().resourceTitle());
				liPanel.add(text);
				if (folderDo.getCollectionItems().get(i).getResourceFormat() != null && folderDo.getCollectionItems().get(i).getResourceFormat().getValue() != null) {
					resourceType = folderDo.getCollectionItems().get(i).getResourceFormat().getValue();
					liPanel.addStyleName(ResourceCategoryClass.getInstance().getCategoryStyle(resourceType));
				}
				liPanel.addClickHandler(new clickOnResource(folderDo.getCollectionItems().get(i)));
				ulCollectionResources.add(liPanel);
				final String description=stripHtmlRegex(folderDo.getCollectionItems().get(i).getDescription()!=null?folderDo.getCollectionItems().get(i).getDescription():"");
				//Mouse over handler on resource title
				MouseOverHandler mouseOverHandler=new MouseOverHandler() {
					@Override
					public void onMouseOver(MouseOverEvent event) {
						if(!description.trim().isEmpty()){
							resourceTooltip.setResourceDesc(description);
							resourceTooltip.show();
							resourceTooltip.setPopupPosition(text.getElement().getAbsoluteLeft(),text.getElement().getAbsoluteTop()+20);
						}
					}
				};
				text.addDomHandler(mouseOverHandler, MouseOverEvent.getType());
				//Mouse out handler on resource title
				MouseOutHandler mouseOutHandler=new MouseOutHandler() {
					@Override
					public void onMouseOut(MouseOutEvent event) {
						if(resourceTooltip!=null){
							resourceTooltip.hide();
						}
					}
				};
				text.addDomHandler(mouseOutHandler, MouseOutEvent.getType());
			}
		}
	}

	/**
	  @description : This inner class is used to redirect to particular resource in collection player.
	 *
	 * @version :1.3
	 *
	 * @date: Feb 10 2015

	 * @Author Gooru Team
	 * 
	 * Reviewer Gooru Team
	 */

	private class clickOnResource implements ClickHandler {
		FolderItemDo folderItemDo;
		String resourceLink = "";
		public clickOnResource(FolderItemDo folderItemDo) {
			this.folderItemDo = folderItemDo;
		}
		@Override
		public void onClick(ClickEvent event) {
			String collectionId = folderDo.getGooruOid();
			String selectedfolderId = AppClientFactory.getPlaceManager().getRequestParameter("id");
			if(parentId==null){
				parentId = selectedfolderId;
			}
			resourceLink = "#" + PlaceTokens.COLLECTION_PLAY + "&id="
					+ collectionId + "&rid="
					+ folderItemDo.getCollectionItemId()+"&folderId"+parentId+"&folderItemId"+folderDo.getCollectionItemId();
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", collectionId);
			params.put("rid", folderItemDo.getCollectionItemId());
			params.put("folderId", parentId);
			params.put("folderItemId", folderDo.getCollectionItemId());

			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.COLLECTION_PLAY, params);

		}
	}

	/**
	 * To remove html tags
	 * 
	 * @param html
	 * @return text without tags
	 */

	public String stripHtmlRegex(String source) {
	 // Replace all tag characters with an empty string.
	 return source.replaceAll("<.*?>", "");
    }
}
