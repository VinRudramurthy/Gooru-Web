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
package org.ednovo.gooru.client.mvp.gshelf.lessondetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.TaxonomyServiceAsync;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.library.DomainStandardsDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.gshelf.ShelfTreeWidget;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;
import org.ednovo.gooru.client.mvp.gshelf.taxonomy.TaxonomyPopupPresenter;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.standards.StandardsPopupPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

/**
 * @author Search Team
 *
 */
public class LessonInfoPresenter extends PresenterWidget<IsLessonInfoView> implements LessonInfoUiHandlers {

	@Inject
	private TaxonomyServiceAsync taxonomyService;

	StandardsPopupPresenter standardsPopupPresenter;

	MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter;
	
	TaxonomyPopupPresenter taxonomyPopupPresenter;
	
	private static final String O1_LEVEL = "o1";
	private static final String O2_LEVEL = "o2";
	
	Map<String, String> params= new HashMap<String, String>();
	final String LESSON="Lesson";

	/**
	 * Class constructor
	 * @param view {@link View}
	 * @param proxy {@link Proxy}
	 */
	@Inject
	public LessonInfoPresenter( EventBus eventBus,IsLessonInfoView view,StandardsPopupPresenter standardsPopupPresenter, TaxonomyPopupPresenter taxonomyPopupPresenter) {
		super(eventBus,view);
		this.standardsPopupPresenter = standardsPopupPresenter;
		this.taxonomyPopupPresenter = taxonomyPopupPresenter;
		taxonomyPopupPresenter.setLessonInfoPresenterInstance(this);
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

	public TaxonomyServiceAsync getTaxonomyService() {
		return taxonomyService;
	}

	public void setTaxonomyService(TaxonomyServiceAsync taxonomyService) {
		this.taxonomyService = taxonomyService;
	}

	@Override
	public void showStandardsPopup(String standardVal, String standardsDesc,List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
		Window.enableScrolling(false);
		standardsPopupPresenter.callStandardsBasedonTypeService(standardVal,standardsDesc);
		standardsPopupPresenter.setLessonInfoPresenter(this);
		standardsPopupPresenter.setAlreadySelectedItems(collectionLiPanelWithCloseArray);
		addToPopupSlot(standardsPopupPresenter);
	}

	@Override
	public void createAndSaveLessonDetails(CreateDo createDo,final boolean isCreateCollOrAssessment,final String creationType,final String courseId,final String unitId, final TreeItem currentShelfTreeWidget) {
		AppClientFactory.getInjector().getfolderService().createCourse(createDo, true, courseId,unitId,null, new SimpleAsyncCallback<FolderDo>() {
			@Override
			public void onSuccess(FolderDo result) {
				getView().resetBtns();
				params.put("o1",courseId);
				params.put("o2",unitId);
				params.put("o3",result.getGooruOid());
				params.put("view", "Course");
				myCollectionsRightClusterPresenter.getShelfMainPresenter().updateTitleOfTreeWidget(result,isCreateCollOrAssessment,currentShelfTreeWidget);
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
				if(isCreateCollOrAssessment && creationType!=null){
					myCollectionsRightClusterPresenter.setTabItems(1, LESSON, result);
					myCollectionsRightClusterPresenter.setTabItems(1, creationType, null);
					myCollectionsRightClusterPresenter.setUnitTemplate(creationType,currentShelfTreeWidget);
				}else{
					myCollectionsRightClusterPresenter.setFolderListDoChild(null);
					myCollectionsRightClusterPresenter.setTabItems(2, LESSON, result);
				}
				Window.scrollTo(0, 0);
				getView().spinnerImageVisibility(false);
			}
		});
	}
	@Override
	public void updateLessonDetails(final CreateDo createDo, final String id,final boolean isCreateColl,final String type,final FolderDo folderObj,final TreeItem currentShelfTreeWidget) {
		String o1= AppClientFactory.getPlaceManager().getRequestParameter("o1",null);
		String o2= AppClientFactory.getPlaceManager().getRequestParameter("o2",null);
		AppClientFactory.getInjector().getfolderService().updateCourse(o1,o2,id,null,createDo, new SimpleAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				getView().resetBtns();
				folderObj.setTitle(createDo.getTitle());
				folderObj.setType(LESSON);
				
				myCollectionsRightClusterPresenter.getShelfMainPresenter().updateTitleOfTreeWidget(folderObj,isCreateColl,currentShelfTreeWidget);
				if(isCreateColl && type!=null){
					myCollectionsRightClusterPresenter.setTabItems(1, LESSON, folderObj);
					myCollectionsRightClusterPresenter.setTabItems(1, type, null);
					myCollectionsRightClusterPresenter.setUnitTemplate(type,currentShelfTreeWidget);
				}else{
					myCollectionsRightClusterPresenter.setTabItems(2, LESSON, folderObj);
				}
				Window.scrollTo(0, 0);
				getView().spinnerImageVisibility(false);
			}
		});
	}
	
	@Override
	public MyCollectionsRightClusterPresenter getMyCollectionsRightClusterPresenter() {
		return myCollectionsRightClusterPresenter;
	}
	
	public void setMyCollectionRightClusterPresenter(MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter) {
		this.myCollectionsRightClusterPresenter=myCollectionsRightClusterPresenter;
	}

	public void setLessonData(FolderDo folderObj) {
		getView().setLessonInfoData(folderObj);
		//callTaxonomyService(1);
	}

	@Override
	public void checkProfanity(String textValue, final boolean isCreate,final String type,final CreateDo createOrUpDate,final String courseId,final String unitId,final TreeItem currentShelfTreeWidget) {

		final Map<String, String> parms = new HashMap<String, String>();
		parms.put("text",textValue);
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean value) {
				getView().resetBtns();
				getView().callCreateAndUpdate(isCreate,value,type,createOrUpDate,courseId,unitId,currentShelfTreeWidget);
			}
		});
	}
	@Override
	public void callTaxonomyService(int subdomainId) {
		getTaxonomyService().getStandardsList(subdomainId,new SimpleAsyncCallback<List<DomainStandardsDo>>() { 
			@Override
			public void onSuccess(List<DomainStandardsDo> result) {
				if(result.size()>0){
					getView().displayStandardsList(result);
				}
			}
		});
	}

	@Override
	public void invokeTaxonomyPopup(String viewType,  List<LiPanelWithClose> lessonLiPanelWithCloseArray) {
		taxonomyPopupPresenter.setSelectedUlContainer(lessonLiPanelWithCloseArray);
		taxonomyPopupPresenter.getTaxonomySubjects(viewType, 1, "subject", 0, 20);
		addToPopupSlot(taxonomyPopupPresenter);
	}
	@Override
	public void callCourseInfoTaxonomy(){
		String courseId=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
		String unitId=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
		AppClientFactory.getInjector().getfolderService().getCourseDetails(courseId, unitId, null, new SimpleAsyncCallback<FolderDo>() {
			@Override
			public void onSuccess(FolderDo result) {
				if(result.getSubdomain()!=null && result.getSubdomain().size()>0){
					getView().getStadardsPanel().setVisible(true);
					CourseSubjectDo courseSubjectObj=result.getSubdomain().get(0);
					callTaxonomyService(courseSubjectObj.getId());
				}else{
					getView().getStadardsPanel().setVisible(false);
				}
			}
		}); 
	}

	public void addTaxonomyData(List<LiPanelWithClose> liPanelWithCloseArray, List<LiPanelWithClose> removedLiPanelWithCloseArray) {
		getView().addTaxonomyData(liPanelWithCloseArray,removedLiPanelWithCloseArray);
		
	}
	
	@Override
	public TreeItem getSelectedWidget() {
		TreeItem shelfTreeWidget = myCollectionsRightClusterPresenter.getShelfMainPresenter().getEditingWidget(); 
		return shelfTreeWidget;
	}

   	public void setSelectedStandards(List<Map<String,String>> standListArray){
   		getView().displaySelectedStandards(standListArray);
   	}
   	
   	

}
