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
package org.ednovo.gooru.client.mvp.assessments.play.collection.info;


import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.service.PlayerAppServiceAsync;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ResoruceCollectionDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.assessments.play.collection.AssessmentsPlayerPresenter;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.standards.StandardsPopupPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

public class AssessmentsResourceInfoPresenter extends PresenterWidget<IsAssessmentsResourceInfoView> implements AssessmentsResourceInfoUiHandlers{

	private CollectionItemDo collectionItemDo=null;

	private AssessmentsPlayerPresenter collectionPlayerPresenter;
	StandardsPopupPresenter standardsPopupPresenter;

	public String mycollectionTitle;

	@Inject
	private PlayerAppServiceAsync playerAppService;

	@Inject
	public AssessmentsResourceInfoPresenter(EventBus eventBus, IsAssessmentsResourceInfoView view,StandardsPopupPresenter standardsPopupPresenter) {
		super(eventBus, view);
		this.standardsPopupPresenter = standardsPopupPresenter;
		getView().setUiHandlers(this);
	}

	public void setResoruceDetails(CollectionItemDo collectionItemDo){
		if(this.collectionItemDo!=null){
			if(this.collectionItemDo.getResource().getGooruOid().equals(collectionItemDo.getResource().getGooruOid())){
				return;
			}else{
				this.collectionItemDo=collectionItemDo;
				getView().setCollectionTitle(mycollectionTitle);
				getView().setResourceMedaDataInfo(collectionItemDo);
			}
		}
		else{
			this.collectionItemDo=collectionItemDo;
			getView().setCollectionTitle(mycollectionTitle);
			getView().setResourceMedaDataInfo(collectionItemDo);
		}
	}

	public void setCollectionType(String collectionType){
		getView().setCollectionType(collectionType);
	}

	public void updateViewsCount(String viewCount){
		getView().setResourceViewsCount(viewCount);
	}

	public void updateLikesCount(int likesCount){
	}

	@Override
	public void getCollectionList(String resourceGooruOid,String pageNum,String pageSize) {
		this.playerAppService.getResourceCollectionsList(resourceGooruOid, pageNum, pageSize, new SimpleAsyncCallback<ResoruceCollectionDo>() {
			@Override
			public void onSuccess(ResoruceCollectionDo resoruceCollectionDo) {
				getView().loadResourceCollection(resoruceCollectionDo);
			}
		});
	}

	public void resetResourceInfo(){
		collectionItemDo=null;
	}

	public void setMycollectionTitle(String mycollectionTitle) {
		this.mycollectionTitle = mycollectionTitle;
	}

	public String getMycollectionTitle() {
		return mycollectionTitle;
	}

	@Override
	public void getAddedResourceTags(String resourceId) {
		if(collectionPlayerPresenter!=null){
			collectionPlayerPresenter.getResourceTagsToDisplay(resourceId);
		}

	}

	public AssessmentsPlayerPresenter getCollectionPlayerPresenter() {
		return collectionPlayerPresenter;
	}

	public void setCollectionPlayerPresenter(AssessmentsPlayerPresenter collectionPlayerPresenter) {
		this.collectionPlayerPresenter = collectionPlayerPresenter;
	}
	public void insertHideButtonAtLast(){
		getView().insertHideButtonAtLast();
	}

	@Override
	public void showStandardsPopup(String standardVal, String standardsDesc,
			List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
		Window.enableScrolling(false);
		standardsPopupPresenter.callStandardsBasedonTypeService(standardVal,standardsDesc);
		standardsPopupPresenter.setAssessmentsResourceInfoPresenter(this);
		standardsPopupPresenter.setAlreadySelectedItems(collectionLiPanelWithCloseArray);
		addToPopupSlot(standardsPopupPresenter);
		
	}
	public void setSelectedStandards(List<Map<String,String>> standListArray){
   		getView().displaySelectedStandards(standListArray);
   	}

}
