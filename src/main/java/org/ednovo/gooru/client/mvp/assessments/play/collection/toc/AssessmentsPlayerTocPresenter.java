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


import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.mvp.assessments.play.collection.AssessmentsPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.AssessmentsResourcePlayerPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AssessmentsPlayerTocPresenter extends PresenterWidget<IsAssessmentsPlayerTocView> implements AssessmentsPlayerTocUiHandlers{

	private AssessmentsPlayerPresenter collectionPlayerPresenter;

	private AssessmentsPreviewPlayerPresenter previewPlayerPresenter;

	private AssessmentsResourcePlayerPresenter resourcePlayerPresenter;

	private boolean isCollectionPlayer=false;

	private boolean isResourcePlayer=false;

	private boolean isPreviewPlayer=false;


	@Inject
	public AssessmentsPlayerTocPresenter(EventBus eventBus, IsAssessmentsPlayerTocView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	public void setNavigationResources(CollectionDo collectionDo,boolean isCollectionHome){
		getView().setNavigationResources(collectionDo,isCollectionHome);
	}

	public void setResourceActive(String collectionId,String collectionItemid,boolean isCollectionHome){
		getView().setResourceActive(collectionId,collectionItemid,isCollectionHome);
	}
	public void clearNavigationPanel(){
		getView().clearNavigationPanel();
	}

	@Override
	public void setPaddingTopForPlayerBody(){
//		if(collectionPlayerPresenter!=null){
//			collectionPlayerPresenter.setPaddingTopForBodyContainer();
//		}
	}

	public void setCollectionPlayerPresnter(AssessmentsPlayerPresenter collectionPlayerPresenter){
		this.collectionPlayerPresenter=collectionPlayerPresenter;
		this.isCollectionPlayer=true;
		this.isResourcePlayer=false;
		this.isPreviewPlayer=false;
	}

	public void setPreviewPlayerPresenter(AssessmentsPreviewPlayerPresenter previewPlayerPresenter){
		this.previewPlayerPresenter=previewPlayerPresenter;
		this.isCollectionPlayer=false;
		this.isResourcePlayer=false;
		this.isPreviewPlayer=true;
	}

	public void setResourcePlayerPresenter(AssessmentsResourcePlayerPresenter resourcePlayerPresenter,boolean isCollectionPlayer){
		this.resourcePlayerPresenter=resourcePlayerPresenter;
		this.isCollectionPlayer=false;
		this.isResourcePlayer=true;
		this.isPreviewPlayer=false;
	}

	public boolean isOpenEndedAnswerSubmited(){
		if(isCollectionPlayer){
			return collectionPlayerPresenter.isOpenEndedAnswerSubmited();
		}else if(isResourcePlayer){
			return resourcePlayerPresenter.isOpenEndedAnswerSubmited();
		}else if(isPreviewPlayer){
			return previewPlayerPresenter.isOpenEndedAnswerSubmited();
		}
		return true;
	}

	public void hideResourceCountLabel(boolean hide){
		getView().hideResourceCountLabel(hide);
	}
	public void clearMarginLeft(){
		getView().clearMarginLeft();
	}

}
