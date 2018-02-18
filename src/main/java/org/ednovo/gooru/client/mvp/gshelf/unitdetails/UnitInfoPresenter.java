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
package org.ednovo.gooru.client.mvp.gshelf.unitdetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.SimpleAsyncCallback;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.TaxonomyServiceAsync;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;
import org.ednovo.gooru.client.mvp.gshelf.taxonomy.TaxonomyPopupPresenter;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

/**
 * @author Search TeamUnitInfoPresenter.java
 *
 */
public class UnitInfoPresenter extends PresenterWidget<IsUnitInfoView> implements UnitInfoUiHandlers {
	

	@Inject
	private TaxonomyServiceAsync taxonomyService;

	MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter;
	
	final String SUBJECT="subject";
	
	final String COURSE="course";
	
	final String UNIT="Unit";
	
	private String LESSON = "Lesson";
	
	TaxonomyPopupPresenter taxonomyPopupPresenter;
	
	Map<String, String> params= new HashMap<String, String>();
	
	/**
	 * Class constructor
	 * @param view {@link View}
	 * @param proxy {@link Proxy}
	 */
	@Inject
	public UnitInfoPresenter( EventBus eventBus,IsUnitInfoView view, TaxonomyPopupPresenter taxonomyPopupPresenter) {
		super(eventBus,view);
		getView().setUiHandlers(this);
		this.taxonomyPopupPresenter = taxonomyPopupPresenter;
		taxonomyPopupPresenter.setUnitInfoPresenterInstance(this);
	}

	@Override
	public void onBind() {
		super.onBind();
	}

	@Override
	protected void onReveal(){
		super.onReveal();
	}

	public TaxonomyServiceAsync getTaxonomyService() {
		return taxonomyService;
	}

	public void setTaxonomyService(TaxonomyServiceAsync taxonomyService) {
		this.taxonomyService = taxonomyService;
	}
	@Override
	public void callCourseBasedOnSubject(int subjectId,final int selectedId) {
		getTaxonomyService().getSubjectsList(subjectId, "course", 0,0, new SimpleAsyncCallback<List<CourseSubjectDo>>() {
			@Override
			public void onSuccess(List<CourseSubjectDo> result) {
				getView().setCourseList(result,selectedId);
			}
		});
	}
	@Override
	public void getDomainsBasedOnCourseId(final int courseId,final int selectedId,final CourseSubjectDo libraryCodeDo) {
		getTaxonomyService().getSubjectsList(courseId,"domain", 0, 20, new SimpleAsyncCallback<List<CourseSubjectDo>>() {
			@Override
			public void onSuccess(List<CourseSubjectDo> result) {
				getView().showCourseDetailsBasedOnSubjectd(result,selectedId,courseId,libraryCodeDo);
			}
		});
	}
	@Override
	public void getPaginatedDomainsBasedOnCourseId(int courseId,final int selectedId, int offSetVal) {
		getTaxonomyService().getSubjectsList(courseId,"domain", offSetVal, 20, new SimpleAsyncCallback<List<CourseSubjectDo>>() {
			@Override
			public void onSuccess(List<CourseSubjectDo> result) {
				if(result.size()>0){
					getView().appendDoamins(result,selectedId);
				}
			}
		});
	}
	@Override
	public void createAndSaveUnitDetails(final CreateDo createDo,final boolean isCreateLesson,final FolderDo courseObj,final String courseId,final TreeItem currentShelfTreeWidget) {
		AppClientFactory.getInjector().getfolderService().createCourse(createDo, true, courseId,null,null, new SimpleAsyncCallback<FolderDo>() {
			@Override
			public void onSuccess(FolderDo result) {
				getView().resetBtns();
				params.put("o1",courseId);
				params.put("o2", result.getGooruOid());
				params.put("view", "Course");
				myCollectionsRightClusterPresenter.getShelfMainPresenter().updateTitleOfTreeWidget(result,isCreateLesson,currentShelfTreeWidget);
				myCollectionsRightClusterPresenter.updateBreadCrumbsTitle(result,UNIT); 
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
				if(isCreateLesson){
					myCollectionsRightClusterPresenter.setTabItems(1, UNIT, result);
					myCollectionsRightClusterPresenter.setTabItems(1, LESSON, null);
					myCollectionsRightClusterPresenter.setUnitTemplate(LESSON,currentShelfTreeWidget);
				}else{
					myCollectionsRightClusterPresenter.setFolderListDoChild(null);
					myCollectionsRightClusterPresenter.setTabItems(2, UNIT, result);
				}
				Window.scrollTo(0, 0);
				getView().spinnerImageVisibility(false);
			}
		});
	}
	@Override
	public void updateUnitDetails(final CreateDo createDo, final String id,final boolean isCreateUnit,final FolderDo folderDo,final TreeItem currentShelfTreeWidget) {
		String o1= AppClientFactory.getPlaceManager().getRequestParameter("o1",null);
		AppClientFactory.getInjector().getfolderService().updateCourse(o1,id,null,null,createDo, new SimpleAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				getView().resetBtns();
				folderDo.setTitle(createDo.getTitle());
				folderDo.setType(UNIT);
				folderDo.setIdeas(createDo.getIdeas());
				folderDo.setQuestions(createDo.getQuestions());
				//folderDo.setGooruOid(id);
				myCollectionsRightClusterPresenter.getShelfMainPresenter().updateTitleOfTreeWidget(folderDo,isCreateUnit,currentShelfTreeWidget);
				myCollectionsRightClusterPresenter.updateBreadCrumbsTitle(folderDo,UNIT); 
				if(isCreateUnit){
					myCollectionsRightClusterPresenter.setTabItems(1, UNIT, folderDo);
					myCollectionsRightClusterPresenter.setTabItems(1, LESSON, null);
					myCollectionsRightClusterPresenter.setUnitTemplate(LESSON,currentShelfTreeWidget);
				}else{
					myCollectionsRightClusterPresenter.setTabItems(2, UNIT, folderDo);
				}
				Window.scrollTo(0, 0);
				getView().spinnerImageVisibility(false);
			}
		});
	}
	@Override
	public void checkProfanity(String textValue,final boolean isCreate,final int index,final String courseId,final CreateDo createOrUpDate,final TreeItem currentShelfTreeWidget){
		final Map<String, String> parms = new HashMap<String, String>();
		parms.put("text",textValue);
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean value) {
				getView().callCreateAndUpdate(isCreate,value,index,courseId,createOrUpDate,currentShelfTreeWidget);
				getView().resetBtns();
			}
		});
	}
	@Override
	public void showUnitInfo() {
		//myCollectionsRightClusterPresenter.setUnitInfo();
	}
	@Override
	public void showUnitTemplate() {
		//myCollectionsRightClusterPresenter.setUnitTemplate();
	}

	public void setMyCollectionRightClusterPresenter(MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter) {
		this.myCollectionsRightClusterPresenter=myCollectionsRightClusterPresenter;
	}
	
	@Override
	public MyCollectionsRightClusterPresenter getMyCollectionsRightClusterPresenter() {
		return myCollectionsRightClusterPresenter;
	}

	public void setMyCollectionsRightClusterPresenter(
			MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter) {
		this.myCollectionsRightClusterPresenter = myCollectionsRightClusterPresenter;
	}

	public void setData(FolderDo folderObj) { 
		getView().setCouseData(folderObj);
	}

	/*@Override
	public void invokeTaxonomyPopup(String viewType, UlPanel ulSelectedItems) {
		taxonomyPopupPresenter.setSelectedUlContainer(ulSelectedItems);
		taxonomyPopupPresenter.getTaxonomySubjects(viewType, 1, "subject", 0, 20);
		addToPopupSlot(taxonomyPopupPresenter);
	}*/ 
	@Override
	public void callCourseInfoTaxonomy(){
		String courseId=AppClientFactory.getPlaceManager().getRequestParameter("o1",null);
		AppClientFactory.getInjector().getfolderService().getCourseDetails(courseId, null, null, new SimpleAsyncCallback<FolderDo>() {
			@Override
			public void onSuccess(FolderDo result) {
				if(result.getTaxonomyCourse()!=null && result.getTaxonomyCourse().size()>0){
					CourseSubjectDo courseSubjectObj=result.getTaxonomyCourse().get(0);
					callCourseBasedOnSubject(courseSubjectObj.getSubjectId(),courseSubjectObj.getId());
				}
			}
		});
	}

	public void addTaxonomy(List<LiPanelWithClose> liPanelWithCloseArray, List<LiPanelWithClose> removedLiPanelWithCloseArray) {
		getView().addTaxonomyData(liPanelWithCloseArray, removedLiPanelWithCloseArray);
	}

	@Override
	public void invokeTaxonomyPopup(String viewType, List<LiPanelWithClose> unitLiPanelWithCloseArray) {
		taxonomyPopupPresenter.setSelectedUlContainer(unitLiPanelWithCloseArray);
		taxonomyPopupPresenter.getTaxonomySubjects(viewType, 1, "subject", 0, 0);
		addToPopupSlot(taxonomyPopupPresenter);
	}
	
	@Override
	public TreeItem getSelectedWidget() {
		TreeItem shelfTreeWidget = myCollectionsRightClusterPresenter.getShelfMainPresenter().getEditingWidget(); 
		return shelfTreeWidget;
	}
}
