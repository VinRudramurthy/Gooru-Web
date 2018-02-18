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
package org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.centuryskills;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.client.mvp.search.CenturySkills.AddCenturyPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author Search Team
 *
 */
public class CenturySkillsPresenter extends PresenterWidget<IsCenturySkillsView> implements CenturySkillsUiHandlers {

	AddCenturyPresenter addCenturyPresenter = null;


	/**
	 * Class constructor
	 * 
	 * @param eventBus {@link EventBus}
	 * @param view {@link View}
	 */
	@Inject
	public CenturySkillsPresenter(EventBus eventBus, IsCenturySkillsView view,AddCenturyPresenter addCenturyPresenter) {
		super(eventBus, view);
		this.addCenturyPresenter=addCenturyPresenter;
		getView().setUiHandlers(this);
	}

	@Override
	public void onBind() {
		super.onBind();
	}
	
	@Override
	public void onReveal() {
		super.onReveal();
	}
	
	@Override
	protected void onReset() {
		// TODO Auto-generated method stub
		super.onReset();
	}

	@Override
	protected void onHide() {
		super.onHide();
	
	}
	
	@Override
	public void setUpdatedCentury() {
		getView().setUpdatedCentury(addCenturyPresenter.getSelectedValues());
	}


	@Override
	public void getAddCentury() {
		addCenturyPresenter.setCenturySkillsObject(this);
		addCenturyPresenter.loadStateStandards();
	}
	
	@Override
	public void getAutoSuggestedCenturyList(SearchDo<StandardFo> centuryDo){
		AppClientFactory.getInjector().getSearchService().getSuggestCenturyByQuery(centuryDo, new AsyncCallback<SearchDo<StandardFo>>() {
			@Override
			public void onSuccess(SearchDo<StandardFo> result) {
				getView().setCenturySuggestions(result);
			}
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}
	@Override
	public void closeCenturyPopup() {
		addCenturyPresenter.hidePopup();
	}

	public void setselData() {
		addCenturyPresenter.getView().resetPopupHilightedData();
		addCenturyPresenter.getSelectedValues().clear();
		if(getView().getSelectedCenturyValuesThroughAutosuggest().size()> 0){
			addCenturyPresenter.setAddResourceData(getView().getSelectedCenturyValuesThroughAutosuggest());
		}
		String collectionUid = AppClientFactory.getPlaceManager().getRequestParameter("id",null);
		/*if(collectionUid!=null){*/
			addCenturyPresenter.setCollectionIdFromCollectionInfo(collectionUid,getView().getSelectedCenturyValuesThroughAutosuggest());
			addToPopupSlot(addCenturyPresenter);
			getView().OnCenturyClickEvent(addCenturyPresenter.getAddButton());
	/*	}*/
	}
}
