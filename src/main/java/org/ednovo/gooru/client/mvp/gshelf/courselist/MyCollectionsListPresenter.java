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
package org.ednovo.gooru.client.mvp.gshelf.courselist;
import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.gshelf.ShelfMainPresenter;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
public class MyCollectionsListPresenter extends PresenterWidget<IsMyCollectionsListView> implements MyCollectionsListUiHandlers{
	
	MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter;
	
	private ShelfMainPresenter shelfMainPresenter;

	private static final String O1_LEVEL = "o1";
	
	private static final String O2_LEVEL = "o2";
	
	private static final String O3_LEVEL = "o3";
	
	private static final String FOLDER= "Folder";
	
	/**
	 * Constructor
	 * @param eventBus
	 * @param view
	 */
	@Inject
	public MyCollectionsListPresenter(MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter,EventBus eventBus, IsMyCollectionsListView view) {
		super(eventBus, view);
		this.myCollectionsRightClusterPresenter=myCollectionsRightClusterPresenter;
		getView().setUiHandlers(this);
	}

	@Override
	public void setData(String type,List<FolderDo> listOfContent,boolean clrPanel,boolean isInnerSlot,FolderDo folderDo) {
		getView().setData(type,listOfContent,clrPanel,isInnerSlot,folderDo);
	}
	
	@Override
	public void setDataInContentSlot(final String type,String folderId,boolean isInnerSlot,final FolderDo folderObj) {
		getView().loadingImage();
		getView().getPanelCourseContainer().clear();
		String view=AppClientFactory.getPlaceManager().getRequestParameter("view",null);
		if(FOLDER.equalsIgnoreCase(view)){
			AppClientFactory.getInjector().getfolderService().getChildFolders(0, 20, folderId,null, null,false,new SimpleAsyncCallback<FolderListDo>() {
				@Override
				public void onSuccess(FolderListDo result) {
					getView().setData(type,result.getSearchResult(),true,true,folderObj);
				}
			});
		}else{
			String o1=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
			String o2=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
			String o3=AppClientFactory.getPlaceManager().getRequestParameter(O3_LEVEL,null);
			AppClientFactory.getInjector().getfolderService().getChildFoldersForCourse(0, 20,o1, o2, o3, null, null, false, new SimpleAsyncCallback<FolderListDo>() {
				@Override
				public void onSuccess(FolderListDo result) {
					getView().setData(type,result.getSearchResult(),true,true,folderObj);
				}
			});
		}
	}

	@Override
	public void setRightClusterPresenterBasedOnType(String type,FolderDo folderObj) {
		clearSlot(ShelfMainPresenter.RIGHT_SLOT);
		getMyCollectionsRightClusterPresenter().setTabItems(1, type,folderObj);
		setInSlot(ShelfMainPresenter.RIGHT_SLOT, getMyCollectionsRightClusterPresenter());
	}
	@Override
	public MyCollectionsRightClusterPresenter getMyCollectionsRightClusterPresenter() {
		return myCollectionsRightClusterPresenter;
	}

	@Override
	public void reorderWidgetPositions(String idToMove,final int itemSeqToAPI,final int movingIndex,String collectionGooruOid) {
		String view=AppClientFactory.getPlaceManager().getRequestParameter("view", null);
		String courseId=AppClientFactory.getPlaceManager().getRequestParameter("o1", null);
		String unitId=AppClientFactory.getPlaceManager().getRequestParameter("o2", null);
		String lessonId=AppClientFactory.getPlaceManager().getRequestParameter("o3", null);
		String collectionId=null;
		if("Folder".equalsIgnoreCase(view) || (courseId!=null && unitId!=null && lessonId!=null)){
			collectionId=collectionGooruOid;
		}else{
			//If not from mycollection we need to use the gooruoid for course ,units and lessons
			idToMove=collectionGooruOid;
		}
		AppClientFactory.getInjector().getfolderService().reorderFoldersOrCollections(courseId,unitId,lessonId,collectionId,itemSeqToAPI,idToMove,view,new SimpleAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {

			}
		});
	}

	public void setShelfMainPresenter(ShelfMainPresenter shelfMainPresenter) {
		this.shelfMainPresenter=shelfMainPresenter;
		myCollectionsRightClusterPresenter.setShelfMainPresenter(shelfMainPresenter);
	}

	/**
	 * @return the shelfMainPresenter
	 */
	@Override
	public ShelfMainPresenter getShelfMainPresenter() {
		return shelfMainPresenter;
	}

	@Override
	public void addNewContent(String type) {
		myCollectionsRightClusterPresenter.addNewContent(type);
	}
}
