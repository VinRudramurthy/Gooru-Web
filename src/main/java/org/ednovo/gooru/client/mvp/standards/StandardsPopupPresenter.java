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
package org.ednovo.gooru.client.mvp.standards;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.service.SearchServiceAsync;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel1DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel2DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel3DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel4DO;
import org.ednovo.gooru.client.SimpleAsyncCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

/**
 * @author Search Team
 *
 */
public class StandardsPopupPresenter extends PresenterWidget<IsStandardsPopupView> implements StandardsPopupUiHandlers {

	@Inject
	private SearchServiceAsync searchService;

	/**
	 * Class constructor
	 * @param view {@link View}
	 * @param proxy {@link Proxy}
	 */
	@Inject
	public StandardsPopupPresenter( EventBus eventBus,IsStandardsPopupView view) {
		super(eventBus,view);
		getView().setUiHandlers(this);
	}

	@Override
	public void onBind() {
		super.onBind();
	}

	@Override
	protected void onReveal(){
		super.onReveal();
	}


	public SearchServiceAsync getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchServiceAsync searchService) {
		this.searchService = searchService;
	}

	@Override
	public void callStandardsBasedonTypeService(String standardVal) {
		getView().reset();
		getSearchService().getFirstLevelStandards("0", standardVal, new SimpleAsyncCallback<ArrayList<StandardsLevel1DO>>() {

			@Override
			public void onSuccess(ArrayList<StandardsLevel1DO> result) {
				for(int i=0;i<result.size();i++) {
					getView().SetData(result.get(i),i);
				}
				
			}
		});		
	}

	@Override
	public void getFirstLevelObjects(String levelOrder,
			String standardCodeSelected) {
		getSearchService().getSecondLevelStandards(levelOrder, standardCodeSelected, new SimpleAsyncCallback<ArrayList<StandardsLevel2DO>>() {
			@Override
			public void onSuccess(ArrayList<StandardsLevel2DO> result) {
			
					getView().loadSecondLevelContianerObjects(result);
				
			}
		});
		
	}

	@Override
	public void getSecondLevelObjects(String levelOrder,
			String standardCodeSelected) {
		getSearchService().getThirdLevelStandards(levelOrder, standardCodeSelected, new SimpleAsyncCallback<ArrayList<StandardsLevel3DO>>() {
			@Override
			public void onSuccess(ArrayList<StandardsLevel3DO> result) {
			
					getView().loadThirdLevelContianerObjects(result);
				
			}
		});
		
	}

	@Override
	public void getThirdLevelObjects(String levelOrder,
			String standardCodeSelected) {
		getSearchService().getFourthLevelStandards(levelOrder, standardCodeSelected, new SimpleAsyncCallback<ArrayList<StandardsLevel4DO>>() {
			@Override
			public void onSuccess(ArrayList<StandardsLevel4DO> result) {
			
					getView().loadFourthLevelContianerObjects(result);
				
			}
		});
		
	}
	
}