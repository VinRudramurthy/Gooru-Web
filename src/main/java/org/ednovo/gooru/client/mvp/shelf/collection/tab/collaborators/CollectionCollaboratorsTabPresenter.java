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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.CollaboratorsServiceAsync;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.home.event.HeaderTabType;
import org.ednovo.gooru.client.mvp.home.event.HomeEvent;
import org.ednovo.gooru.client.mvp.search.event.SetFooterEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * 
 * @fileName : CollectionCollaboratorsTabPresenter.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: Jan 23, 2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class CollectionCollaboratorsTabPresenter extends PresenterWidget<IsCollectionCollaboratorsTab> implements CollectionCollaboratorsTabUiHandlers {

	
	@Inject
	private CollaboratorsServiceAsync collaboratorsService;

	private SimpleAsyncCallback<Map<String, ArrayList<CollaboratorsDo>>> collabAsyncCallback;
	
	private SimpleAsyncCallback<ArrayList<CollaboratorsDo>> addCollabAsyncCallback;
		
	CollectionDo collectionDo=null;
		
	String mode = null;
	/**
	 * Class constructor
	 * 
	 * @param eventBus {@link EventBus}
	 * @param view {@link View}
	 */
	@Inject
	public CollectionCollaboratorsTabPresenter(EventBus eventBus, IsCollectionCollaboratorsTab view) {
		super(eventBus, view);
		getView().setUiHandlers(this);		
	}

	@Override
	public void onBind() {
		super.onBind();
		setCollabAsyncCallback(new SimpleAsyncCallback<Map<String,ArrayList<CollaboratorsDo>>>() {

			@Override
			public void onSuccess(Map<String, ArrayList<CollaboratorsDo>> result) {
				if (mode.equalsIgnoreCase("view")){
					List<CollaboratorsDo> lstCollaborators = result.get("active");
					getView().displayViewCollaboratorsByList(lstCollaborators);
					getView().setRemoveCollabButtonVisibility(false);
				}else if (mode.equalsIgnoreCase("edit")){
					//Display all collaborators in active  and pending list.
					
					getView().setLoadingVisibility(false);
					
					List<CollaboratorsDo> lstActiveCollaborators = result.get("active");
					List<CollaboratorsDo> lstPendingCollaborators = result.get("pending");
					
					getView().displayActiveCollaboratorsByList(lstActiveCollaborators);
					getView().displayPendingCollaboratorsByList(lstPendingCollaborators);
					
					getView().setInviteButtonEnable(lstActiveCollaborators.size() + lstPendingCollaborators.size());
				}
			}
		});
		
		setAddCollabAsyncCallback(new SimpleAsyncCallback<ArrayList<CollaboratorsDo>>() {

			@Override
			public void onSuccess(ArrayList<CollaboratorsDo> result) {
				getView().displayCollaborators(result);
			}
		});
	}

	@Override
	public void onReveal() {
		super.onReveal();
		
		AppClientFactory.fireEvent(new HomeEvent(HeaderTabType.ORGANIZE));
		AppClientFactory.fireEvent(new SetFooterEvent(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken()));
	}
	
	@Override
	protected void onReset() {
		super.onReset();
	}
	
	@Override
	protected void onUnbind() {
	}
	
	@Override
	protected void onHide() {
		super.onHide();
	}
	
	
	//Custom Methods
	// This method is called by ShelfView.java while adding presenter.
	@Override
	public void setData(CollectionDo collectionDo){
		this.collectionDo = collectionDo;
		getView().displayView(collectionDo);
	}

	/**
	 * @param collectionId
	 * 
	 */
	@Override
	public void getCollaboratosListByCollectionId(String collectionId, String mode) {
		this.mode = mode;
		getView().clearContainers();
		getCollaboratorsService().getAssociatedCollaborators(collectionId, mode, getCollabAsyncCallback());
	}

	@Override
	public void addCollaborators(String collectionId, List<String> emailIds){
		getCollaboratorsService().addCollaboratorToCollectionById(emailIds, collectionId, getAddCollabAsyncCallback());
	}
	
	
	/** 
	 * This method is to get the collaboratorsService
	 */
	public CollaboratorsServiceAsync getCollaboratorsService() {
		return collaboratorsService;
	}

	/** 
	 * This method is to set the collaboratorsService
	 */
	public void setCollaboratorsService(
			CollaboratorsServiceAsync collaboratorsService) {
		this.collaboratorsService = collaboratorsService;
	}

	/** 
	 * This method is to get the collabAsyncCallback
	 */
	public SimpleAsyncCallback<Map<String, ArrayList<CollaboratorsDo>>> getCollabAsyncCallback() {
		return collabAsyncCallback;
	}

	/** 
	 * This method is to set the collabAsyncCallback
	 */
	public void setCollabAsyncCallback(SimpleAsyncCallback<Map<String, ArrayList<CollaboratorsDo>>> collabAsyncCallback) {
		this.collabAsyncCallback = collabAsyncCallback;
	}

	/** 
	 * This method is to get the addCollabAsyncCallback
	 */
	public SimpleAsyncCallback<ArrayList<CollaboratorsDo>> getAddCollabAsyncCallback() {
		return addCollabAsyncCallback;
	}

	/** 
	 * This method is to set the addCollabAsyncCallback
	 */
	public void setAddCollabAsyncCallback(SimpleAsyncCallback<ArrayList<CollaboratorsDo>> addCollabAsyncCallback) {
		this.addCollabAsyncCallback = addCollabAsyncCallback;
	}
}
