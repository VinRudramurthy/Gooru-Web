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
package org.ednovo.gooru.client.mvp.classpages.assignments;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpages.edit.EditClasspagePresenter;
import org.ednovo.gooru.client.mvp.search.event.ResetProgressEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 *
 * @fileName : AddAssignmentContainerPresenter.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 27-Dec-2013
 *
 * @Author : Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class AddAssignmentContainerPresenter extends PresenterWidget<IsAddAssignmentContainerView> implements AddAssignmentContainerUiHandlers{

	private String classpageId=null;
	private EditClasspagePresenter editClasspagePresenter=null;

	@Inject
	public AddAssignmentContainerPresenter(EventBus eventBus, IsAddAssignmentContainerView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	/**
	 *
	 * @function getUserShelfData
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void getUserShelfData(){
		getView().clearShelfData();
		getWorkspaceData(0,20,true);
	}
	/**
	 *
	 * @function getWorkspaceData
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : offset
	 * @param	:	limit
	 * @param	:	clearShelfPanel
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void getWorkspaceData(int offset,int limit, final boolean clearShelfPanel){
		AppClientFactory.getInjector().getResourceService().getFolderWorkspace(offset, limit,"public,anyonewithlink", null,true, new SimpleAsyncCallback<FolderListDo>() {
			@Override
			public void onSuccess(FolderListDo folderListDo) {
				if(folderListDo.getCount()==0){
					getView().displayNoCollectionsMsg();
				}else{
					getView().displayWorkspaceData(folderListDo,clearShelfPanel);
				}
			}
		});
	}

	@Override
	public void getFolderItems(final TreeItem item,String parentId) {
		AppClientFactory.getInjector().getfolderService().getChildFolders(0, 20, parentId,"public,anyonewithlink", null,true, new SimpleAsyncCallback<FolderListDo>() {
			@Override
			public void onSuccess(FolderListDo folderListDo) {
				getView().setFolderItems(item,folderListDo);
			}
		});
	}
	/**
	 *
	 * @function addCollectionToAssign
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : collectionId
	 * @param	:	direction
	 * @param	:	dueDate
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void addCollectionToAssign(String collectionId,String direction,String dueDate){
		AppClientFactory.getInjector().getClasspageService().createClassPageItem(this.classpageId, collectionId, dueDate, direction, new SimpleAsyncCallback<ClasspageItemDo>() {
			@Override
			public void onSuccess(ClasspageItemDo classpageItemDo) {
				getView().hideAddCollectionPopup(classpageItemDo.getCollectionTitle());
				getEditClasspagePresenter().setClasspageItemDo(classpageItemDo);
			}
		});
	}
	/**
	 *
	 * @function addCollectionToAssign
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : collectionId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void addCollectionToAssign(String collectionId){
		AppClientFactory.getInjector().getClasspageService().assignItemToClass(this.classpageId, collectionId, null, null, new SimpleAsyncCallback<ArrayList<ClasspageItemDo>>() {
			@Override
			public void onSuccess(ArrayList<ClasspageItemDo> classpageItemDoList) {
				if(classpageItemDoList!=null&&classpageItemDoList.size()>0){
					getView().hideAddCollectionPopup("");
					showCollectionsAfterAddingNewCollections();
				}
			}
		});
	}
	/**
	 *
	 * @function showCollectionsAfterAddingNewCollections
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void showCollectionsAfterAddingNewCollections(){
		Map<String,String> params = new HashMap<String,String>();
		String classpageid=AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID, null);
		params.put("order", "asce");
		params.put(UrlNavigationTokens.CLASSPAGEID, classpageid);
		params.put("pageNum", 1+"");
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.EDIT_CLASSPAGE, params);
		AppClientFactory.getPlaceManager().revealPlace(true, placeRequest, true);
		AppClientFactory.fireEvent(new ResetProgressEvent());
	}
	/**
	 *
	 * @function setClasspageId
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param classpageId
	 * @parm(s) : @param editClasspagePresenter
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setClasspageId(String classpageId,EditClasspagePresenter editClasspagePresenter){
		this.classpageId=classpageId;
		this.setEditClasspagePresenter(editClasspagePresenter);
	}
	/**
	 *
	 * @function getEditClasspagePresenter
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : EditClasspagePresenter
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public EditClasspagePresenter getEditClasspagePresenter() {
		return editClasspagePresenter;
	}
	/**
	 *
	 * @function setEditClasspagePresenter
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param editClasspagePresenter
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setEditClasspagePresenter(EditClasspagePresenter editClasspagePresenter) {
		this.editClasspagePresenter = editClasspagePresenter;
	}

}
