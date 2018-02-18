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

package org.ednovo.gooru.client.mvp.gsearch.collection;

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.search.CollectionSearchResultDo;
import org.ednovo.gooru.client.mvp.gsearch.SearchAbstractView;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.search.util.CollectionSearchWidget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 * @fileName : SearchCollectionView.java
 * 
 * @description :
 * 
 * @version : 1.3
 * 
 * @date: 10-04-2015
 * 
 * @Author Gooru Team
 * 
 * @Reviewer:
 */
public class SearchCollectionView extends
		SearchAbstractView<CollectionSearchResultDo> implements
		IsSearchCollectionView {


	public SearchCollectionView() {
		super(false);
	}
	/**
	 * To render Collection search results.
	 * 
	 * @return collectionSearchWidget{@link Widget}
	 */
	@Override
	public Widget renderSearchResult(final CollectionSearchResultDo collectionSearchResultDo) {
		final CollectionSearchWidget collectionSearchWidget=new CollectionSearchWidget(collectionSearchResultDo);
		collectionSearchWidget.getRemixBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.enableScrolling(false);
				if(AppClientFactory.isAnonymous()){
					LoginPopupUc loginPopupUc=new LoginPopupUc() {
						@Override
						public	void onLoginSuccess(){
							Window.enableScrolling(false);
							displayRemixPopup(collectionSearchResultDo,collectionSearchWidget);
						}
					};
					loginPopupUc.show();
					loginPopupUc.setGlassEnabled(true);
				}else{
					displayRemixPopup(collectionSearchResultDo,collectionSearchWidget);
				}
			}
		});
		collectionSearchWidget.getElement().setId(collectionSearchResultDo.getGooruOid());
		return collectionSearchWidget;
	}
	public void displayRemixPopup(CollectionSearchResultDo collectionSearchResultDo,CollectionSearchWidget collectionSearchWidget){
		Element element = Document.get().getElementById("fixedFilterSearchID");
		if(element!=null){
			element.setAttribute("style", "opacity:0.1;");
		}
		getUiHandlers().displayRemixForCollectionsPoup(collectionSearchResultDo,collectionSearchWidget);
	}
	@Override
	public void setUpdatedStandards(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		
	}
}
