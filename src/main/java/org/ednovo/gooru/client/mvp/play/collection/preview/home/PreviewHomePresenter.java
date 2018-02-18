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
package org.ednovo.gooru.client.mvp.play.collection.preview.home;



import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.play.collection.CollectionPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.rating.RatingAndReviewPopupPresenter;
import org.ednovo.gooru.client.mvp.rating.events.OpenReviewPopUpEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;


public class PreviewHomePresenter extends PresenterWidget<IsPreviewHomeView> implements PreviewHomeUiHandlers{


	SignUpPresenter signUpViewPresenter = null;
	
	private CollectionPlayerPresenter collectionPlayerPresenter=null;
	private PreviewPlayerPresenter previewPlayerPresenter=null;
	private RatingAndReviewPopupPresenter ratingAndReviewPopup;
	CollectionDo collectionDo= null;
	@Inject
	public PreviewHomePresenter(EventBus eventBus, IsPreviewHomeView view, SignUpPresenter signUpViewPresenter, RatingAndReviewPopupPresenter ratingAndReviewPopup) {
		super(eventBus, view);
		this.signUpViewPresenter = signUpViewPresenter;
		this.ratingAndReviewPopup = ratingAndReviewPopup;
		getView().setUiHandlers(this);
		addRegisteredHandler(OpenReviewPopUpEvent.TYPE, this);
	}
	public void setCollectionMetadata(CollectionDo collectionDo){
		this.collectionDo = collectionDo;
		getView().setCollectionMetadata(collectionDo);
		setCollectionResources(collectionDo);
	}
	
	public PreviewPlayerPresenter getPreviewPlayerPresenter() {
		return previewPlayerPresenter;
	}
	public void setPreviewPlayerPresenter(PreviewPlayerPresenter previewPlayerPresenter) {
		this.previewPlayerPresenter = previewPlayerPresenter;
	}
	
	public void setCollectionResources(CollectionDo collectionDo){
		getView().setCollectionResources(collectionDo);
	}
	public void removeAssignmentButtons(){
		getView().removeAssignmentButtons();
	}
	
	public void removeAssignmentImagetButtons(){
		getView().removeAssignmentImageButtons();
	}
	public CollectionPlayerPresenter getCollectionPlayerPresenter() {
		return collectionPlayerPresenter;
	}

	public void setCollectionPlayerPresenter(CollectionPlayerPresenter collectionPlayerPresenter) {
		this.collectionPlayerPresenter = collectionPlayerPresenter;
		
	}
	
	public void scrollStudyPage(){
		if(collectionPlayerPresenter!=null){
			collectionPlayerPresenter.scrollStudyPage();
		}
	}
	@Override
	public void scrollStudyPageEndPage() {
		scrollStudyPage();
	}
	
	public Button getBackToClassButton(){
		return getView().getBackToClassButton();
	}
	
	@Override
	public void resetCollectionActivityEventId() {
		if(collectionPlayerPresenter!=null){
			collectionPlayerPresenter.resetcollectionActivityEventId();
		}
	}
	
	@Override
	public void openReviewPopUp(String assocGooruOId, String title,
			String createrName) {
	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.play.collection.preview.home.PreviewHomeUiHandlers#openReviewPopUp(java.lang.String, java.lang.String)
	 */
	@Override
	public void openReviewPopUp(String assocGooruOId, String title) {
		throw new RuntimeException("Not implemented");
	}
	@Override
	public void triggerCollectionShareDataEvent(String collectionId,
			String itemType, String shareType, boolean confirmStatus) {
		if(previewPlayerPresenter!=null){
			previewPlayerPresenter.triggerCollectionShareDataEvent( collectionId, itemType,  shareType,  confirmStatus);
		}
		
	}

}
