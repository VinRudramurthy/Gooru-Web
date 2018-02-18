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
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.home.presearch.PreSearchPresenter;
import org.ednovo.gooru.application.client.service.SearchServiceAsync;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel1DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel2DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel3DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel4DO;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.assessments.play.collection.info.AssessmentsResourceInfoPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.body.AssessmentsResourcePlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.gsearch.SearchAbstractPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectioncontent.CollectionContentPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.CollectionInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.lessondetails.LessonInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.play.collection.info.ResourceInfoPresenter;
import org.ednovo.gooru.client.mvp.play.resource.body.ResourcePlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddResourcePresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.addquestion.QuestionTypePresenter;

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
public class StandardsPopupPresenter extends PresenterWidget<IsStandardsPopupView> implements StandardsPopupUiHandlers {

	@Inject
	private SearchServiceAsync searchService;

	CollectionInfoPresenter collectionInfoPresenter;
	AddResourcePresenter addResourcePresenter;
	QuestionTypePresenter questionTypePresenter;
	CollectionContentPresenter collectionContentPresenter;
	SearchAbstractPresenter searchAbstractPresenter;
	PreSearchPresenter preSearchPresenter;
	LessonInfoPresenter lessonInfoPresenter;
	ResourcePlayerMetadataPresenter resourcePlayerMetadataPresenter;
	AssessmentsResourceInfoPresenter assessmentsResourceInfoPresenter;
	AssessmentsResourcePlayerMetadataPresenter assessmentsResourcePlayerMetadataPresenter;
	ResourceInfoPresenter resourceInfoPresenter;
	String standardV = "";

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


	public AssessmentsResourceInfoPresenter getAssessmentsResourceInfoPresenter() {
		return assessmentsResourceInfoPresenter;
	}


	public void setAssessmentsResourceInfoPresenter(AssessmentsResourceInfoPresenter assessmentsResourceInfoPresenter) {
		this.assessmentsResourceInfoPresenter = assessmentsResourceInfoPresenter;
	}


	public void setAddResourcePresenter(AddResourcePresenter addResourcePresenter) {
		this.addResourcePresenter = addResourcePresenter;
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
	public void callStandardsBasedonTypeService(String standardVal, final String titleVal) {
		getView().reset();
		standardV = standardVal;
		getSearchService().getFirstLevelStandards("0", standardVal, new SimpleAsyncCallback<ArrayList<StandardsLevel1DO>>() {

			@Override
			public void onSuccess(ArrayList<StandardsLevel1DO> result) {
				for(int i=0;i<result.size();i++) {
					getView().SetData(result.get(i),i,titleVal,standardV);
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
				if(!standardV.equalsIgnoreCase("b21"))
				{
					getView().loadSecondLevelContianerObjects(result);
				}
				else
				{
					getView().loadb21SecondLevelContianerObjects(result);
				}

			}
		});

	}

	@Override
	public void getSecondLevelObjects(String levelOrder,
			final String standardCodeSelected) {
		getSearchService().getThirdLevelStandards(levelOrder, standardCodeSelected, new SimpleAsyncCallback<ArrayList<StandardsLevel3DO>>() {
			@Override
			public void onSuccess(ArrayList<StandardsLevel3DO> result) {
					if(!standardV.equalsIgnoreCase("b21"))
					{
					getView().loadThirdLevelContianerObjects(result);
					}
					else
					{
					getView().loadB21ThirdLevelContianerObjects(result);	
					}

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

	public void setCollectionInfoPresenter(CollectionInfoPresenter collectionInfoPresenter) {
		this.collectionInfoPresenter = collectionInfoPresenter;
	}
	

	public void setLessonInfoPresenter(LessonInfoPresenter lessonInfoPresenter) {
		this.lessonInfoPresenter = lessonInfoPresenter;
	}

	@Override
	public void setSelectedStandards(List<Map<String,String>> standListArray){
		try
		{
		collectionInfoPresenter.setSelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}
		try
		{
		lessonInfoPresenter.setSelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}
		try
		{
		searchAbstractPresenter.setSelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}
		try
		{
		preSearchPresenter.setSelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}
		try
		{
		addResourcePresenter.setSelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}
		try
		{
		collectionContentPresenter.setSelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}
		try
		{
		assessmentsResourceInfoPresenter.setSelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}
		try
		{
		assessmentsResourcePlayerMetadataPresenter.setSelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}	
		try
		{
		resourceInfoPresenter.setSelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}	
		try
		{
		resourcePlayerMetadataPresenter.setSelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}
		try
		{
		questionTypePresenter.setSelectedStandards(standListArray);
		}
		catch(Exception ex)
		{
			
		}
	}
	
	

	public void setAlreadySelectedItems(
			List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
		getView().setSelectedItmes(collectionLiPanelWithCloseArray);
	}

	public SearchAbstractPresenter getSearchAbstractPresenter() {
		return searchAbstractPresenter;
	}

	public void setSearchAbstractPresenter(
			SearchAbstractPresenter searchAbstractPresenter) {
		this.searchAbstractPresenter = searchAbstractPresenter;
	}

	public PreSearchPresenter getPreSearchPresenter() {
		return preSearchPresenter;
	}

	public void setPreSearchPresenter(PreSearchPresenter preSearchPresenter) {
		this.preSearchPresenter = preSearchPresenter;
	}



	public Anchor getCloseButton() {
		return getView().getCloseButton();
	}


	public Button getAddButton() {
		return getView().getAddButton();
	}


	public List<Map<String, String>> getSelectedStandards() { 
		return getView().getAddedStandards();
	}
	public CollectionContentPresenter getCollectionContentPresenter() {
		return collectionContentPresenter;
	}


	public void setCollectionContentPresenter(CollectionContentPresenter collectionContentPresenter) {
		this.collectionContentPresenter = collectionContentPresenter;
	}


	public AssessmentsResourcePlayerMetadataPresenter getAssessmentsResourcePlayerMetadataPresenter() {
		return assessmentsResourcePlayerMetadataPresenter;
	}


	public void setAssessmentsResourcePlayerMetadataPresenter(
			AssessmentsResourcePlayerMetadataPresenter assessmentsResourcePlayerMetadataPresenter) {
		this.assessmentsResourcePlayerMetadataPresenter = assessmentsResourcePlayerMetadataPresenter;
	}


	public ResourceInfoPresenter getResourceInfoPresenter() {
		return resourceInfoPresenter;
	}


	public void setResourceInfoPresenter(ResourceInfoPresenter resourceInfoPresenter) {
		this.resourceInfoPresenter = resourceInfoPresenter;
	}


	public ResourcePlayerMetadataPresenter getResourcePlayerMetadataPresenter() {
		return resourcePlayerMetadataPresenter;
	}


	public void setResourcePlayerMetadataPresenter(ResourcePlayerMetadataPresenter resourcePlayerMetadataPresenter) {
		this.resourcePlayerMetadataPresenter = resourcePlayerMetadataPresenter;
	}


	public QuestionTypePresenter getQuestionTypePresenter() {
		return questionTypePresenter;
	}


	public void setQuestionTypePresenter(QuestionTypePresenter questionTypePresenter) {
		this.questionTypePresenter = questionTypePresenter;
	}


	
	
}
