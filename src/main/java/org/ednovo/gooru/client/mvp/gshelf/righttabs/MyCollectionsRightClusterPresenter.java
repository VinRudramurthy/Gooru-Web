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
package org.ednovo.gooru.client.mvp.gshelf.righttabs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.gsearch.addResourcePopup.SearchAddResourceToCollectionPresenter;
import org.ednovo.gooru.client.mvp.gshelf.LoadMyContentEvent;
import org.ednovo.gooru.client.mvp.gshelf.ShelfMainPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectioncontent.CollectionContentPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.CollectionInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.CollectionShareTabPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.ExternalAssessmentInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.coursedetails.CourseInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.coursedetails.CourseSharePresenter;
import org.ednovo.gooru.client.mvp.gshelf.lessondetails.LessonInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.unitdetails.UnitInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.util.AssessmentPopupWidget;
import org.ednovo.gooru.client.uc.AlertForImageUpload;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
public class MyCollectionsRightClusterPresenter extends PresenterWidget<IsMyCollectionsRightClusterView> implements MyCollectionsRightClusterUiHandlers{

	public static final  Object INNER_SLOT = new Object();

	CourseInfoPresenter courseInfoPresenter;

	LessonInfoPresenter lessonInfoPresenter;
	
	CollectionInfoPresenter collectionInfoPresenter;
	
	CourseSharePresenter courseSharePresenter;

	UnitInfoPresenter unitInfoPresenter; 
	
	AssessmentPopupWidget assessmentPopup;

	ShelfMainPresenter shelfMainPresenter;
	
	ExternalAssessmentInfoPresenter externalAssessmentInfoPresenter;
	
	private HashMap<String, String> selectedWidgetsTitleType;

	CollectionContentPresenter collectionContentPresenter;
	
	CollectionShareTabPresenter collectionShareTabPresenter = null;
	
	SearchAddResourceToCollectionPresenter searchAddResourceToCollectionPresenter=null;
	
	List<FolderDo> folderListDoChild;

	final String COLLECTION="collection";
	final String ASSESSMENT="assessment";
	private static final String ASSESSMENT_URL = "assessment/url";
	
	private static final String COURSE = "Course";
	private static final String UNIT = "Unit";
	private static final String LESSON = "Lesson";
	private static final String FOLDER = "Folder";
	private boolean isCopySelected= false;
	private boolean isMoveSelected= false;
	
	Map<Integer,Integer> firstSelectedData;
	/**
	 * Constructor
	 * @param eventBus
	 * @param view
	 */
	@Inject
	public MyCollectionsRightClusterPresenter(EventBus eventBus, IsMyCollectionsRightClusterView view,CollectionContentPresenter collectionContentPresenter,CourseInfoPresenter courseInfoPresenter,LessonInfoPresenter lessonInfoPresenter,ExternalAssessmentInfoPresenter externalAssessmentInfoPresenter,UnitInfoPresenter unitInfoPresenter,CollectionInfoPresenter collectionInfoPresenter,CollectionShareTabPresenter collectionShareTabPresenter,
			SearchAddResourceToCollectionPresenter searchAddResourceToCollectionPresenter,CourseSharePresenter courseSharePresenter) {
		super(eventBus, view);
		this.courseInfoPresenter=courseInfoPresenter;
		this.lessonInfoPresenter=lessonInfoPresenter;
		this.unitInfoPresenter=unitInfoPresenter;
		this.collectionInfoPresenter=collectionInfoPresenter;
		this.collectionContentPresenter=collectionContentPresenter;
		this.externalAssessmentInfoPresenter=externalAssessmentInfoPresenter;
		this.collectionShareTabPresenter=collectionShareTabPresenter;
		this.searchAddResourceToCollectionPresenter=searchAddResourceToCollectionPresenter;
		this.courseSharePresenter=courseSharePresenter;
		collectionShareTabPresenter.setMyCollectionRightClusterPresenter(this);
		collectionContentPresenter.setMyCollectionRightClusterPresenter(this);
		externalAssessmentInfoPresenter.setMyCollectionRightClusterPresenter(this);
		courseInfoPresenter.setMyCollectionRightClusterPresenter(this);
		collectionInfoPresenter.setMyCollectionRightClusterPresenter(this);
		unitInfoPresenter.setMyCollectionRightClusterPresenter(this);
		lessonInfoPresenter.setMyCollectionRightClusterPresenter(this);
		getView().setUiHandlers(this);
	}
	@Override
	public void setTabItems(int index,String type,FolderDo folderObj) {
		clearSlot(INNER_SLOT);
		if(folderObj==null){
			selectedWidgetsTitleType = null;
		}
		String view=AppClientFactory.getPlaceManager().getRequestParameter("view",null);
		getView().setBreadCrumbSlot(folderObj,type,selectedWidgetsTitleType);
		getView().setDefaultActiveTab(index);
		getView().setCurrentTypeView(type);
		getView().enableAndHideTabs(!StringUtil.isEmpty(folderObj==null?"":folderObj.getGooruOid())); 
		if(view!=null && FOLDER.equalsIgnoreCase(view)){
			getView().disableAndEnableBreadCums(false);
		}else{
			getView().disableAndEnableBreadCums(true);
		}
		if(index==1 || ASSESSMENT_URL.equalsIgnoreCase(folderObj.getType())){
				//For displaying template and data
				if(COURSE.equalsIgnoreCase(type)){
					courseInfoPresenter.setData(folderObj);
					setInSlot(INNER_SLOT, courseInfoPresenter);
				}else if("Unit".equalsIgnoreCase(type)){ 
					unitInfoPresenter.setData(folderObj);
					setInSlot(INNER_SLOT, unitInfoPresenter);
				}else if("Lesson".equalsIgnoreCase(type)){
					lessonInfoPresenter.setLessonData(folderObj); 
					setInSlot(INNER_SLOT, lessonInfoPresenter);
				}else if(ASSESSMENT.equalsIgnoreCase(type) || COLLECTION.equalsIgnoreCase(type)){
					if(view!=null && FOLDER.equalsIgnoreCase(view)){ 
						getView().disableAndEnableBreadCums(false);
					}else{
						getView().disableAndEnableBreadCums(true);
					}
					collectionInfoPresenter.setCollectionType(type);
					collectionInfoPresenter.setData(folderObj,type);
					setInSlot(INNER_SLOT, collectionInfoPresenter);
				}else if(FOLDER.equalsIgnoreCase(type)){
					//To disable bread cums
					getView().disableAndEnableBreadCums(false);
					getView().setFolderInfoWidget(folderObj, this);
				}else{
					if(view!=null && FOLDER.equalsIgnoreCase(view)){
						getView().disableAndEnableBreadCums(false);
					}else{
						getView().disableAndEnableBreadCums(true);
					}
					getView().enableAndHideTabs(false);
					if(folderObj!=null && folderObj.getGooruOid()!=null){
						getView().disableButtons(true);
					}else{
						getView().disableButtons(false);
					}
					externalAssessmentInfoPresenter.setData(folderObj);
					setInSlot(INNER_SLOT, externalAssessmentInfoPresenter);
				}
		}else if(index==2){
			if(view!=null && FOLDER.equalsIgnoreCase(view)){
				getView().disableAndEnableBreadCums(false);
			}else{
				getView().disableAndEnableBreadCums(true);
			}
			 if(COLLECTION.equalsIgnoreCase(type) || ASSESSMENT.equalsIgnoreCase(type) || COLLECTION.equalsIgnoreCase(folderObj.getType()) || ASSESSMENT.equalsIgnoreCase(folderObj.getType())){
				collectionContentPresenter.getView().getResourceListPanel();
				collectionContentPresenter.setData(folderObj);
				setInSlot(INNER_SLOT, collectionContentPresenter);
			}else{
				shelfMainPresenter.getMyCollectionsListPresenter().getView().loadingImage();
				shelfMainPresenter.getMyCollectionsListPresenter().setData(type, getFolderListDoChild(), true, true, folderObj);
				//shelfMainPresenter.getMyCollectionsListPresenter().setDataInContentSlot(type, folderObj.getGooruOid(),true,folderObj);
				setInSlot(INNER_SLOT, shelfMainPresenter.getMyCollectionsListPresenter());
			}
		}else if(index==3){
			if(COURSE.equalsIgnoreCase(folderObj.getType())){
				courseSharePresenter.setData(folderObj);
				setInSlot(INNER_SLOT, courseSharePresenter);
			}else if(COLLECTION.equalsIgnoreCase(folderObj.getType()) || ASSESSMENT.equalsIgnoreCase(folderObj.getType())){
				collectionShareTabPresenter.setData(folderObj);
				setInSlot(INNER_SLOT, collectionShareTabPresenter);
			}
			Window.scrollTo(0, 0);
		}
	}
	//This method is not using present
	@Override
	public void setDefaultActiveTab(){
		getView().resetHilightStyles();
		//getView().setDefaultActiveTab();
	}

	
	public void setUnitTemplate(String type, TreeItem currentShelfTreeWidget){  	    
		//shelfMainPresenter.getMyCollectionsListPresenter().setData(type, null, true, true, null);
		//setInSlot(INNER_SLOT, shelfMainPresenter.getMyCollectionsListPresenter());
		shelfMainPresenter.createNewUnitItem(type,currentShelfTreeWidget);
		folderListDoChild=null;
		getView().enableAndHideTabs(false);
	}

	/**
	 * To set the shelfMainPresenter obj
	 * @param shelfMainPresenter
	 */
	public void setShelfMainPresenter(ShelfMainPresenter shelfMainPresenter) {
		this.shelfMainPresenter=shelfMainPresenter;
	}

	/**
	 * To set the shelfMainPresenter obj
	 * @param shelfMainPresenter
	 */
	public ShelfMainPresenter getShelfMainPresenter() {
		return shelfMainPresenter;
	}
	public List<FolderDo> getFolderListDoChild() {
		return folderListDoChild;
	}
	public void setFolderListDoChild(List<FolderDo> folderListDoChild) {
		this.folderListDoChild = folderListDoChild;
	}
	public void updateBreadCrumbsTitle(FolderDo folderObj, String type) { 
		getView().setBreadCrumbSlot(folderObj, type,null);
	}
	
	
	public void setMycontentBreadcrumbs(HashMap<String, String> selectedWidgetsTitleType) {
		this.selectedWidgetsTitleType = selectedWidgetsTitleType;
	}
	/**
	 * To add new course/unit/lesson
	 * @param type
	 */
	public void addNewContent(String type) {
		if(type!=null){
			if(type.contains(COURSE)){
			    setTabItems(1,COURSE , null);
				setUnitTemplate(COURSE,null);
				//courseInfoPresenter.createAndSaveCourseDetails(courseInfoPresenter.getView().getCourseTitle(), false);
			}else if(type.contains(UNIT)){	
			    setTabItems(1, UNIT, null);
				setUnitTemplate(UNIT,null);
				//courseInfoPresenter.createAndSaveCourseDetails(courseInfoPresenter.getView().getCourseTitle(), false);
			}else if(type.contains(LESSON)){
				setTabItems(1, LESSON, null);
				setUnitTemplate(LESSON,null);
			}else if(type.toLowerCase().contains(FOLDER.toLowerCase())){
				setTabItems(1, FOLDER, null);
				setUnitTemplate(FOLDER,null);
			}else if(type.toLowerCase().contains(COLLECTION.toLowerCase())){
				setTabItems(1, COLLECTION, null);
				setUnitTemplate(COLLECTION,null);
			}else if(type.toLowerCase().contains(ASSESSMENT.toLowerCase())){
				Window.enableScrolling(false);
				assessmentPopup=new AssessmentPopupWidget() {
					@Override
					public void clickOnNoramlAssessmentClick() {
						Window.enableScrolling(true);
						setTabItems(1, ASSESSMENT, null);
						setUnitTemplate(ASSESSMENT,null);
						assessmentPopup.hide();
					}
					@Override
					public void clickOnExternalAssessmentClick() {
						assessmentPopup.hide();
						Window.enableScrolling(true);
						//This will display the external assessment info
						setTabItems(1, ASSESSMENT_URL, null);
						setUnitTemplate(ASSESSMENT_URL,null);
					}
				};
				assessmentPopup.setGlassEnabled(true);
				assessmentPopup.show();
				assessmentPopup.center();
			}
		}
	}
	
	/**
	 * calls API to delete cousrse.
	 */
	@Override
	public void deleteCourseContent(final String o1CourseId) {
		AppClientFactory.getInjector().getfolderService().deleteCourse(o1CourseId, new SimpleAsyncCallback<Integer>() {
			@Override
			public void onSuccess(Integer result) {
				if(result==200){
					getView().onDeleteCourseSuccess(o1CourseId);
				}
			}
		});
	}
	
	/**
	 * Sets the right side view on delete of course.
	 */
	@Override
	public void setRightClusterContent(String o1CourseId,String currentTypeView) {
		if(shelfMainPresenter!=null){
			shelfMainPresenter.setUserAllCourses(o1CourseId,currentTypeView);
		}
	}
	@Override
	public void getUserShelfData(String collectionId,String valuetype) {
		searchAddResourceToCollectionPresenter.getUserShelfCollectionsData(collectionId, valuetype,"");
		showAppPopup();
	}

	/**
	 * calls API to delete Unit.
	 */
	@Override
	public void deleteUnitContent(final String o1CourseId, final String o2UnitId) {
		AppClientFactory.getInjector().getfolderService().deleteUnit(o1CourseId,o2UnitId, new SimpleAsyncCallback<Integer>() {

			@Override
			public void onSuccess(Integer result) {
				if(result==200){
					getView().onDeleteUnitSuccess(o1CourseId,o2UnitId);
				}
			}
		});
	}
	

	/**
	 * calls API to delete Lesson.
	 */
	@Override
	public void deleteLessonContent(final String o1CourseId, final String o2UnitId,	final String o3LessonId) {
		AppClientFactory.getInjector().getfolderService().deleteLesson(o1CourseId,o2UnitId,o3LessonId, new SimpleAsyncCallback<Integer>() {

			@Override
			public void onSuccess(Integer result) {
				if(result==200){
					getView().onDeleteLessonSuccess(o1CourseId,o2UnitId,o3LessonId);
				}
			}
			
		});
	}
	
	/**
	 * calls API to delete Collection/Assessment.
	 */
	@Override
	public void deleteCollectionContent(final String o1CourseId, final String o2UnitId,	final String o3LessonId, final String assessmentCollectionId) {
		AppClientFactory.getInjector().getfolderService().deleteCollectionAssessment(o1CourseId,o2UnitId,o3LessonId,assessmentCollectionId, new SimpleAsyncCallback<Integer>() {
			@Override
			public void onSuccess(Integer result) {
				if(result==200){
					getView().onDeleteCollectionAssessmentSuccess(o1CourseId,o2UnitId,o3LessonId,assessmentCollectionId);
				}
			}
		});
	}
	
	
	/**
	 * calls API to delete Collection/Assessment at Mycollections level(old view).
	 */
	
	@Override
	public void deleteMyCollectionContent(final String id, final String type) {
		
		AppClientFactory.getInjector().getfolderService().deleteCollectionsFolder(id, new SimpleAsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				if(COURSE.equalsIgnoreCase(type)){
					getView().onDeleteCourseSuccess(id);
				}else if(UNIT.equalsIgnoreCase(type)){
					getView().onDeleteUnitSuccess("", id);
				}else{
					getView().onDeleteLessonSuccess("", "", id);
				}
			}
		});
		
		/*if("folderCollection".equals(type)){
			AppClientFactory.getInjector().getfolderService().deleteCollectionsFolder(id, new SimpleAsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					getView().onDeleteCollectionAssessmentSuccess("","","",id);
				}
			});
		}else{
			
		}*/
	}
	
	
	
	@Override
	public void deleteMyCollectionColl(final String id) {
		AppClientFactory.getInjector().getResourceService().deleteCollection(id, new SimpleAsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				getView().onDeleteCollectionAssessmentSuccess("","","",id);
			}
		});
	}
	
	
	/**
	 * Sets the right side view on delete of Unit.
	 */
	@Override
	public void setUnitsListOnRightCluster(String o1CourseId,String o2DeletedUnitId,String currentTypeView) {
		shelfMainPresenter.setUserAllUnits(o1CourseId, o2DeletedUnitId,currentTypeView);
	}
	
	/**
	 * Sets the right side view on delete of Lesson.
	 */
	@Override
	public void setLessonsListOnRightCluster(String o1CourseId,	String o2UnitId, String o3LessDeletedonId, String currentTypeView) {
		shelfMainPresenter.setUserAllLessons(o1CourseId,o2UnitId, o3LessDeletedonId,currentTypeView);
	}
	
	
	/**
	 * Sets the right side view on delete of Collection/Assesssment.
	 */
	@Override
	public void setCollectionsListOnRightCluster(String o1CourseId,String o2UnitId, String o3LessonId,String deletedAssessmentCollectionId, String currentTypeView) {
		shelfMainPresenter.setUserAllCollAssessment(o1CourseId,o2UnitId, o3LessonId,deletedAssessmentCollectionId,currentTypeView); 
	}
	
	/**
	 * Calls API to check weather Course is assigned to class or not.
	 */
	@Override
	public void isAssignedToClassPage(String type,final String o1CourseId, final String o2UnitId, final String o3LessonId, final String collAssessmentId) { 
		
		AppClientFactory.getInjector().getClasspageService().getClassesAssociatedWithCourse(o1CourseId, new SimpleAsyncCallback<ArrayList<ClasspageDo>>(){ 

			@Override
			public void onSuccess(ArrayList<ClasspageDo> result) {
				getView().invokeContentDeletePopup(o1CourseId,o2UnitId,o3LessonId,collAssessmentId,result); 
			} 
		});
	}
	@Override
	public boolean checkCopyOrMoveStatus(boolean copySelected,boolean moveSelected,String clickedType) {
		this.isCopySelected=copySelected;
		this.isMoveSelected= moveSelected;
		searchAddResourceToCollectionPresenter.selectedCopyOrMoveStatus(isCopySelected,isMoveSelected,clickedType);
		return false;
	}
	@Override
	public void EnableMyCollectionsTreeData(String collectionId,String collectionTitle) {
		searchAddResourceToCollectionPresenter.getLoadingImage();
		searchAddResourceToCollectionPresenter.getUserShelfCollectionsData(collectionId, "coursebuilder",collectionTitle);
		searchAddResourceToCollectionPresenter.setCollectionTitle(collectionTitle);
		searchAddResourceToCollectionPresenter.DisableMyCollectionsPanelData(false);
		shelfMainPresenter.SetDefaultTypeAndVersion();
		showAppPopup();
	}
	@Override
	public void DisableMyCollectionsTreeData(String collectionId,String collectionTitle) {
		searchAddResourceToCollectionPresenter.getLoadingImage();
		searchAddResourceToCollectionPresenter.getUserShelfCollectionsData(collectionId, "coursebuilder",collectionTitle);
		searchAddResourceToCollectionPresenter.setCollectionTitle(collectionTitle);
		searchAddResourceToCollectionPresenter.DisableMyCollectionsPanelData(true);
		shelfMainPresenter.SetDefaultTypeAndVersion();
		showAppPopup();
	}
	public void showAppPopup(){
		searchAddResourceToCollectionPresenter.getView().getAppPopUp().show();
		searchAddResourceToCollectionPresenter.getView().getAppPopUp().center();
		searchAddResourceToCollectionPresenter.getView().getAppPopUp().setGlassEnabled(true);
		searchAddResourceToCollectionPresenter.getView().getAppPopUp().setGlassStyleName("setGlassPanelZIndex");
	}
	public Map<Integer,Integer> getFirstSelectedData(){
		return firstSelectedData;
	}
	@Override
	public void setFirstSelectedData(Map<Integer,Integer> firstSelectedData){
		this.firstSelectedData=firstSelectedData;
	}
	@Override
	public void enableAddButton() {
		searchAddResourceToCollectionPresenter.enableAddButton();
	}
	public void disableCollabaratorOptions(boolean isHide) {
		getView().disableCollabaratorOptions(isHide);
		getView().setIsCollaboratorValue(isHide);
	}
	/**
	 * This handles the display of respective view title and respective type icon.
	 */
	@Override
	public void setViewTitleWthicon(String title, String type) {
		shelfMainPresenter.setTileIcon(title,type);
	}
	
	public TreeItem getCurrentTreeItem() {
		return shelfMainPresenter.getEditingWidget();
	}
	
	
	@Override
	public void disableCopyPopupTabs(boolean isVisible, String copyType) {
		searchAddResourceToCollectionPresenter.getView().disableTabs(isVisible,copyType);
	}
	
	
	@Override
	public void copyCourse(String gooruOid) {
		AppClientFactory.getInjector().getfolderService().copyCourse(gooruOid, null, null, new SimpleAsyncCallback<String>() {

			@Override
			public void onSuccess(String url) {
				callJobSuccessApi(url,null);
			}
		});
	}
	
	protected void callJobSuccessApi(final String jobUrl,final HashMap<String, String> urlparams) {
		AppClientFactory.getInjector().getfolderService().jobCheck(jobUrl,new SimpleAsyncCallback<Map<String,String>>() {

			@Override
			public void onSuccess(Map<String, String> result) {
				if(result.get("status").equalsIgnoreCase("completed")){
					HashMap<String,String> params = new HashMap<String,String>();
					params.put("o1", result.get("gooruOid"));
					params.put("view", "Course");
					shelfMainPresenter.setVersion();
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
				}else if(result.get("status").equalsIgnoreCase("inprogress")){
					Timer timer = new Timer() {

						@Override
						public void run() {
							callJobSuccessApi(jobUrl,urlparams);
						}
						
					};
					timer.schedule(150);
				}else{
					new AlertForImageUpload("Oops", "Something went wrong, plewase try again.");
					//getView().hidePopup();
				}
			}
		});
	}
	@Override
	public void isStudentDataAvailable(final String type, final String o1CourseId,final  String o2UnitId, final String o3LessonId, final String assessmentCollectionId) {
		AppClientFactory.getInjector().getfolderService().isTiedWithStudentData(o1CourseId, new SimpleAsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				getView().isCourseDeleteStatus(result, type,  o1CourseId,  o2UnitId,  o3LessonId,  assessmentCollectionId);
				
			}
		});
	}
	
}
