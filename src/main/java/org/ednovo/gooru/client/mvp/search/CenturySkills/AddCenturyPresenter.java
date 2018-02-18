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
package org.ednovo.gooru.client.mvp.search.CenturySkills;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.skils.CenturySkilsDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.centuryskills.CenturySkillsPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

/**
 * @author Search Team
 *
 */
public class AddCenturyPresenter extends PresenterWidget<IsAddCenturyView> implements AddCenturyUiHandlers {
	
	CenturySkillsPresenter centurySkillsPresenter;

	/**
	 * Class constructor
	 * @param view {@link View}
	 * @param proxy {@link Proxy}
	 */
	@Inject
	public AddCenturyPresenter( EventBus eventBus,IsAddCenturyView view) {
		super(eventBus,view);
		getView().setUiHandlers(this);
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
	 */
	@Override
	public void onBind() {
		super.onBind();
	}
	
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.search.CenturySkills.AddCenturyUiHandlers#loadStateStandards()
	 */
	@Override
	public void loadStateStandards(){
		AppClientFactory.getInjector().getSearchService().getCenturySkilsRestuls(new SimpleAsyncCallback<CenturySkilsDo>() {
			@Override
			public void onSuccess(CenturySkilsDo result) {
				if(result!=null){
					getView().SetData(result);
					if(centurySkillsPresenter!=null){
						centurySkillsPresenter.setselData();
					}
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.PresenterWidget#onReveal()
	 */
	@Override
	protected void onReveal(){
		super.onReveal();
	}
	
	@Override
	public void hidePopup() {
		getView().hidePopup();
	}

	@Override
	public Button getAddButton() {
		return getView().getAddBtn();
	}

	@Override
	public Map<Long, String> getSelectedValues() {
		return getView().getSelectedValues();
	}

	@Override
	public Button getCancelBtn() {
		return getView().getCancelBtn();
	}

	@Override
	public Anchor getCloseBtn() {
		return getView().getCloseBtn();
	}

	@Override
	public void setEditResourceData(List<StandardFo> codeList) {
		getView().setEditResourceData(codeList);
	}

	@Override
	public void setAddResourceData(Map<Long, String> codeList) {
		getView().setAddResourceData(codeList);
	}
	
	@Override
	public void setAddResourceDataAddTags(ArrayList<String> centuryDo) {
		getView().setAddResourceDataForTags(centuryDo);
	}

	@Override
	public void setCollectionIdFromCollectionInfo(String collectionId,Map<Long, String> collectionInfoSelectedValues) {
		getView().setCollectionIdFromCollectionInfo(collectionId,collectionInfoSelectedValues);
	}
	@Override
	public void deleteCourseOrStandard(String collectionId, String courseCode) {
		AppClientFactory.getInjector().getResourceService().deleteTaxonomyResource(collectionId, Integer.valueOf(courseCode), new SimpleAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
			}
		});
	}

	public void setCenturySkillsObject(CenturySkillsPresenter centurySkillsPresenter) {
		this.centurySkillsPresenter = centurySkillsPresenter;
	}
}
