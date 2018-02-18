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
package org.ednovo.gooru.client.mvp.gshelf.collectiondetails;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.SimpleAsyncCallback;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

/**
 * @author Search Team
 *
 */
public class ExternalAssessmentInfoPresenter extends PresenterWidget<IsExternalAssessmentView> implements ExternalAssessmentInfoUiHandlers {

	MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter;
	
	final String ASSESSMENTURL="assessment/url";
	
	private static final String O1_LEVEL = "o1";
	private static final String O2_LEVEL = "o2";
	private static final String O3_LEVEL = "o3";
	
	final String COURSE="Course";
	
	private String VIEW ="view";
	
	private static final String FOLDER = "Folder";
	
	/**
	 * Class constructor
	 * @param view {@link View}
	 * @param proxy {@link Proxy}
	 */
	@Inject
	public ExternalAssessmentInfoPresenter(EventBus eventBus,IsExternalAssessmentView view) {
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

	@Override
	public void checkProfanity(String textValue,final boolean isCreate,final int index,final CreateDo createOrUpDate,final TreeItem currentShelfTreeWidget) {
		final Map<String, String> parms = new HashMap<String, String>();
		parms.put("text",textValue);
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean value) {
				getView().callCreateAndUpdate(isCreate,value,index, createOrUpDate,currentShelfTreeWidget);
			}
		});
	}

	@Override
	public void createAndSaveAssessmentDetails(CreateDo createObj,boolean isCreateAssessment,final TreeItem currentShelfTreeWidget) {
		final String o1=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
		final String o2=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
		final String o3=AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL,null);		
		String parentId = null;
		if(getViewType().equalsIgnoreCase(FOLDER)){
			final Map<String, String> params= new HashMap<String, String>();
			if(o3!=null){
				params.put(O1_LEVEL, o1);
				params.put(O2_LEVEL, o2);
				params.put(O3_LEVEL, o3);
				parentId=o3;
			}else if(o2!=null){
				params.put(O1_LEVEL, o1);
				params.put(O2_LEVEL, o2);
				parentId=o2;
			}else if(o1!=null){
				parentId=o1;
				params.put(O1_LEVEL, o1);
			}
			AppClientFactory.getInjector().getfolderService().createCollection(createObj, parentId, false, new SimpleAsyncCallback<FolderDo>() {
				@Override
				public void onSuccess(FolderDo result) {
					getView().resetBtns();
					params.put("id", result.getGooruOid());
					params.put("view", FOLDER);
					myCollectionsRightClusterPresenter.getShelfMainPresenter().updateTitleOfTreeWidget(result, true,currentShelfTreeWidget);
					myCollectionsRightClusterPresenter.setTabItems(2, ASSESSMENTURL, result);
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
				}
			});
		}else{
			AppClientFactory.getInjector().getfolderService().createCourse(createObj, true,o1,o2,o3, new SimpleAsyncCallback<FolderDo>() {
				@Override
				public void onSuccess(FolderDo result) {
					getView().resetBtns();
					Map<String, String> params= new HashMap<String, String>();
					params.put(O1_LEVEL, o1);
					params.put(O2_LEVEL, o2);
					params.put(O3_LEVEL, o3);
					params.put("id", result.getGooruOid());
					params.put("view", "course");
					myCollectionsRightClusterPresenter.getShelfMainPresenter().updateTitleOfTreeWidget(result, true,currentShelfTreeWidget);
					myCollectionsRightClusterPresenter.updateBreadCrumbsTitle(result,ASSESSMENTURL); 
					myCollectionsRightClusterPresenter.getShelfMainPresenter().enableCreateCourseButton(true); // To enable Create course button passing true value.
					myCollectionsRightClusterPresenter.setTabItems(1, ASSESSMENTURL, result);
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
				}
			});
		}
		getView().spinnerImageVisibility(false);
		
	}

	@Override
	public void updateAssessmentDetails(final CreateDo createOrUpDate, String id,boolean isCreateAssessment,final FolderDo folderObj,final TreeItem currentShelfTreeWidget) {
		String o1= AppClientFactory.getPlaceManager().getRequestParameter("o1",null);
		String o2= AppClientFactory.getPlaceManager().getRequestParameter("o2",null);
		String o3= AppClientFactory.getPlaceManager().getRequestParameter("o3",null);
		String o4= AppClientFactory.getPlaceManager().getRequestParameter("id",null);
		folderObj.setTitle(createOrUpDate.getTitle());
		folderObj.setCollectionType(ASSESSMENTURL);
		folderObj.setDescription(createOrUpDate.getDescription());
		folderObj.setUrl(createOrUpDate.getUrl());
		folderObj.setSharing(createOrUpDate.getSharing());
		folderObj.getSettings().setIsLoginRequired(createOrUpDate.getSettings().getIsLoginRequired());
		if(getViewType().equalsIgnoreCase(FOLDER)){
			AppClientFactory.getInjector().getfolderService().updateCollectionDetails(createOrUpDate,id, null,null, null, null, new AsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
					getView().resetBtns();
					myCollectionsRightClusterPresenter.getShelfMainPresenter().updateTitleOfTreeWidget(folderObj,false,currentShelfTreeWidget);
					myCollectionsRightClusterPresenter.setTabItems(2, createOrUpDate.getCollectionType(), folderObj);
					AppClientFactory.getPlaceManager().revealCurrentPlace();
				}
				@Override
				public void onFailure(Throwable caught) {
				}
			});
		}else{
			AppClientFactory.getInjector().getfolderService().updateCourse(o1,o2,o3,o4,createOrUpDate, new SimpleAsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
					getView().resetBtns();
					//folderDo.setGooruOid(id);
					myCollectionsRightClusterPresenter.setTabItems(1, ASSESSMENTURL, folderObj);
					myCollectionsRightClusterPresenter.getShelfMainPresenter().updateTitleOfTreeWidget(folderObj,false,currentShelfTreeWidget);
					AppClientFactory.getPlaceManager().revealCurrentPlace();
				}
			});
		}
		Window.scrollTo(0, 0);
		getView().spinnerImageVisibility(false);
	}
	/**
	 * This method is used to set the right cluster presenter
	 * @param myCollectionsRightClusterPresenter
	 */
	public void setMyCollectionRightClusterPresenter(MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter) {
		this.myCollectionsRightClusterPresenter=myCollectionsRightClusterPresenter;
	}
	/**
	 * This method is used to set data object
	 * @param folderObj
	 */
	public void setData(FolderDo folderObj){
		getView().setData(folderObj);
	}
	/**
   	 * @return viewType
   	 */
   	public String getViewType(){
   		String view =AppClientFactory.getPlaceManager().getRequestParameter(VIEW,null);
		return view==null?COURSE:view;
   	}
   	
   	@Override
	public TreeItem getSelectedWidget() {
   		TreeItem shelfTreeWidget = myCollectionsRightClusterPresenter.getShelfMainPresenter().getEditingWidget(); 
		return shelfTreeWidget;
	}
}
