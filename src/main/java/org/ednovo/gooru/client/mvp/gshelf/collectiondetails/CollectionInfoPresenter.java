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
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.SimpleAsyncCallback;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.FolderServiceAsync;
import org.ednovo.gooru.application.client.service.TaxonomyServiceAsync;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.library.DomainStandardsDo;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.centuryskills.CenturySkillsPresenter;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;
import org.ednovo.gooru.client.mvp.gshelf.taxonomy.TaxonomyPopupPresenter;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadPresenter;
import org.ednovo.gooru.client.mvp.standards.StandardsPopupPresenter;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

/**
 * @author Search Team
 *
 */
public class CollectionInfoPresenter extends PresenterWidget<IsCollectionInfoView> implements CollectionInfoUiHandlers {

	@Inject
	private TaxonomyServiceAsync taxonomyService;

	@Inject
	private FolderServiceAsync folderServiceAsync;

	@ContentSlot public static final Type<RevealContentHandler<?>> CENTURYSKILLS = new Type<RevealContentHandler<?>>();

	CenturySkillsPresenter centurySkillsPresenter;

	TaxonomyPopupPresenter taxonomyPopupPresenter;

	MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter;

	StandardsPopupPresenter standardsPopupPresenter;

	final String SUBJECT="subject";

	final String COURSE="Course";

	private String UNIT = "Unit";

	private String VIEW ="view";

	private static final String FOLDER = "Folder";

	private String type;

	private static final String O1_LEVEL = "o1";
	private static final String O2_LEVEL = "o2";
	private static final String O3_LEVEL = "o3";

	ImageUploadPresenter imgUploadPresenter=null;

	/**
	 * Class constructor
	 * @param view {@link View}
	 * @param proxy {@link Proxy}
	 */
	@Inject
	public CollectionInfoPresenter( EventBus eventBus,IsCollectionInfoView view,ImageUploadPresenter imgUploadPresenter,StandardsPopupPresenter standardsPopupPresenter,CenturySkillsPresenter centurySkillsPresenter,TaxonomyPopupPresenter taxonomyPopupPresenter) {
		super(eventBus,view);
		getView().setUiHandlers(this);
		this.imgUploadPresenter = imgUploadPresenter;
		this.standardsPopupPresenter=standardsPopupPresenter;
		this.centurySkillsPresenter=centurySkillsPresenter;
		this.taxonomyPopupPresenter = taxonomyPopupPresenter;
		taxonomyPopupPresenter.setCollectionInfoPresenterInstance(this);
	}

	@Override
	public void onBind() {
		super.onBind();
		Window.enableScrolling(true);
		setInSlot(CENTURYSKILLS,centurySkillsPresenter);

	}

	@Override
	protected void onReset() {
		super.onReset();
	}
	@Override
	protected void onReveal(){
		super.onReveal();
		setDepthofKnowledgeDetails();
		setAudienceDetails();
		Window.enableScrolling(true);
	}

	public TaxonomyServiceAsync getTaxonomyService() {
		return taxonomyService;
	}

	public void setTaxonomyService(TaxonomyServiceAsync taxonomyService) {
		this.taxonomyService = taxonomyService;
	}


	public FolderServiceAsync getFolderServiceAsync() {
		return folderServiceAsync;
	}

	public void setFolderServiceAsync(FolderServiceAsync folderServiceAsync) {
		this.folderServiceAsync = folderServiceAsync;
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
	public void callCourseBasedOnSubject(int subjectId,final String selectedText) {
		getTaxonomyService().getSubjectsList(subjectId, COURSE, 0, 10, new SimpleAsyncCallback<List<CourseSubjectDo>>() {
			@Override
			public void onSuccess(List<CourseSubjectDo> result) {
				if(result.size()>0){
					//getView().showCourseDetailsBasedOnSubjectd(result,selectedText);
				}
			}
		});
	}

	@Override
	public void setCollectionType(String templateType) {
		getView().setCollectionType(templateType);
	}

	@Override
	public void createAndSaveCourseDetails(final CreateDo createObj,final boolean isCreateUnit,final TreeItem currentShelfTreeWidget) {
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
					myCollectionsRightClusterPresenter.setTabItems(2, createObj.getCollectionType(), result);
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
					result.setAudience(StringUtil.getCheckBoxSelectedDo(getView().getAudienceContainer().getSelectedValues()));
					result.setDepthOfKnowledge(StringUtil.getCheckBoxSelectedDo(getView().getDepthOfKnowledgeContainer().getSelectedValue()));
					result.setSkills(StringUtil.getStandardFos(centurySkillsPresenter.getView().getSelectedValuesFromAutoSuggest()));
					result.setLanguageObjective(getView().getLanguageObjectiveContainer().getLanguageObjective());
					myCollectionsRightClusterPresenter.getShelfMainPresenter().updateTitleOfTreeWidget(result, true,currentShelfTreeWidget);
					myCollectionsRightClusterPresenter.updateBreadCrumbsTitle(result,createObj.getCollectionType());
					myCollectionsRightClusterPresenter.getShelfMainPresenter().enableCreateCourseButton(true); // To enable Create course button passing true value.
					myCollectionsRightClusterPresenter.setTabItems(2, createObj.getCollectionType(), result);
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
				}
			});
		}
		Window.scrollTo(0, 0);
		getView().spinnerImageVisibility(false);
	}

	/*private void createCollectionInFolder() {
		AppClientFactory.getInjector().getfolderService().createCollectionInParent(data, courseCodeId, folderId, simpleAsyncCallback)
	}*/

	public void setMyCollectionRightClusterPresenter(MyCollectionsRightClusterPresenter myCollectionsRightClusterPresenter) {
		this.myCollectionsRightClusterPresenter=myCollectionsRightClusterPresenter;
	}

	public void setData(FolderDo folderObj, String type) {
		getView().setCouseData(folderObj,type);
		if(!getViewType().equalsIgnoreCase(FOLDER)){
			callCourseInfoTaxonomy();
		}
		String view=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getParameter("view",null);	
		String idVal=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getParameter("id",null);
		if(view!=null && view.equalsIgnoreCase("Folder")){
			if(idVal!=null){
			if(folderObj!=null && folderObj.getGooruOid()!=null)
			{
			getCollectionDo(idVal);
			}
			}
		}
	}
	@Override
	public void showStandardsPopup(String standardVal, String standardsDesc,List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
		Window.enableScrolling(false);
		standardsPopupPresenter.callStandardsBasedonTypeService(standardVal,standardsDesc);
		standardsPopupPresenter.setCollectionInfoPresenter(this);
		standardsPopupPresenter.setAlreadySelectedItems(collectionLiPanelWithCloseArray);
		addToPopupSlot(standardsPopupPresenter);
	}
	@Override
	public void updateCourseDetails(final CreateDo createDo, final String id,final boolean isCreateUnit,final FolderDo folderDo, final TreeItem currentShelfTreeWidget) {
		String o1= AppClientFactory.getPlaceManager().getRequestParameter("o1",null);
		String o2= AppClientFactory.getPlaceManager().getRequestParameter("o2",null);
		String o3= AppClientFactory.getPlaceManager().getRequestParameter("o3",null);
		String o4= AppClientFactory.getPlaceManager().getRequestParameter("id",null);

		folderDo.setTitle(createDo.getTitle());
		folderDo.setCollectionType(createDo.getCollectionType());
		folderDo.setDescription(createDo.getDescription());
		folderDo.setAudience(StringUtil.getCheckBoxSelectedDo(getView().getAudienceContainer().getSelectedValues()));
		folderDo.setDepthOfKnowledge(StringUtil.getCheckBoxSelectedDo(getView().getDepthOfKnowledgeContainer().getSelectedValue()));
		folderDo.setSkills(StringUtil.getStandardFos(centurySkillsPresenter.getView().getSelectedValuesFromAutoSuggest()));
		folderDo.setLanguageObjective(getView().getLanguageObjectiveContainer().getLanguageObjective());
		folderDo.setThumbnails(createDo.getThumbnails());
		if(getViewType().equalsIgnoreCase(FOLDER)){
			getFolderServiceAsync().updateCollectionDetails(createDo,id, getView().getAudienceContainer().getSelectedValues(),getView().getDepthOfKnowledgeContainer().getSelectedValue(), centurySkillsPresenter.getView().getSelectedValuesFromAutoSuggest(), getView().getLanguageObjectiveContainer().getLanguageObjective(), new AsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
					getView().resetBtns();
					myCollectionsRightClusterPresenter.getShelfMainPresenter().updateTitleOfTreeWidget(folderDo,false,currentShelfTreeWidget);
					myCollectionsRightClusterPresenter.setTabItems(2, createDo.getCollectionType(), folderDo);
					AppClientFactory.getPlaceManager().revealCurrentPlace();
				}
				@Override
				public void onFailure(Throwable caught) {
					AppClientFactory.printInfoLogger("I am In updateCollectionDetails Failure ");
				}
			});
		}else{
			AppClientFactory.getInjector().getfolderService().updateCourse(o1,o2,o3,o4,createDo, new SimpleAsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
					getView().resetBtns();
					myCollectionsRightClusterPresenter.getShelfMainPresenter().updateTitleOfTreeWidget(folderDo,false,currentShelfTreeWidget);
					myCollectionsRightClusterPresenter.updateBreadCrumbsTitle(folderDo,createDo.getCollectionType());
					myCollectionsRightClusterPresenter.setTabItems(2, createDo.getCollectionType(), folderDo);
					AppClientFactory.getPlaceManager().revealCurrentPlace();
				}
			});
		}
		Window.scrollTo(0, 0);
		getView().spinnerImageVisibility(false);
	}

	@Override
	public void checkProfanity(String textValue,final boolean isCreate,final int index,final String collectionType,final CreateDo createOrUpDate,final TreeItem currentShelfTreeWidget){
		final Map<String, String> parms = new HashMap<String, String>();
		parms.put("text",textValue);
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean value) {
				getView().resetBtns();
				getView().callCreateAndUpdate(isCreate,value,index,collectionType,createOrUpDate,currentShelfTreeWidget);
			}
		});
	}

	@Override
	public void uploadCollectionImage(CreateDo createDoObj) {
		addToPopupSlot(imgUploadPresenter);
		CreateDo createOrUpDate=new CreateDo();
		createOrUpDate.setTitle(createDoObj.getTitle());
		createOrUpDate.setDescription(createDoObj.getDescription());
		createOrUpDate.setCollectionType(createDoObj.getCollectionType());
		imgUploadPresenter.setCollectionData(createDoObj);
		imgUploadPresenter.setCollectionImage(true);
		imgUploadPresenter.setProfileImage(false);
		imgUploadPresenter.setEditResourceImage(false);
		imgUploadPresenter.setAnswerImage(false);
/*		imgUploadPresenter.setCollectionImage(true);
		imgUploadPresenter.setClassPageImage(false);
		imgUploadPresenter.setUpdateQuestionImage(false);
		imgUploadPresenter.setEditResourceImage(false);*/
	}


	public void setDepthofKnowledgeDetails(){
		getFolderServiceAsync().getDepthOfKnowledgesList(new AsyncCallback<List<ListValuesDo>>() {
			@Override
			public void onSuccess(List<ListValuesDo> result) {
				getView().getDepthOfKnowledgeContainer().init(result);
				getView().getDepthOfKnowledgeContainer().setFolderDo(getView().getFolderDo());
			}
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	public void setAudienceDetails(){
		getFolderServiceAsync().getAudienceList(new AsyncCallback<List<ListValuesDo>>() {
			@Override
			public void onSuccess(List<ListValuesDo> result) {
				getView().getAudienceContainer().init(result);
				getView().getAudienceContainer().setFolderDetails(getView().getFolderDo());
			}
			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	public void getCollectionDo(String collectionUid){

			AppClientFactory.getInjector().getResourceService().getCollection(collectionUid,true, new SimpleAsyncCallback<CollectionDo>() {
				@Override
				public void onSuccess(CollectionDo result) {
					FolderDo folderDoObj = new FolderDo();
					folderDoObj.setGooruOid(result.getGooruOid());
					folderDoObj.setTitle(result.getTitle());
					folderDoObj.setLanguageObjective(result.getLanguageObjective());
					folderDoObj.setAudience(result.getAudience());
					folderDoObj.setType(result.getCollectionType());
					folderDoObj.setDescription(result.getDescription());
					folderDoObj.setDepthOfKnowledge(result.getDepthOfKnowledges());
					folderDoObj.setSkills(result.getMetaInfo().getSkills());	
					folderDoObj.setThumbnails(result.getThumbnails());	
					folderDoObj.setTaxonomyCourse(result.getTaxonomyCourse());
					folderDoObj.setSubdomain(result.getSubdomain());
					getView().setCouseData(folderDoObj,result.getCollectionType());
					getView().setStandardsValue(result.getMetaInfo().getStandards());
					centurySkillsPresenter.getView().setCollectionDo(result);
					getView().getDepthOfKnowledgeContainer().setCollectionDo(result);
					getView().getAudienceContainer().setCollectonDetails(result);
				}
			});
		
	}

	@Override
	public void invokeTaxonomyPopup(String viewType,List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
		taxonomyPopupPresenter.setSelectedUlContainer(collectionLiPanelWithCloseArray);
		taxonomyPopupPresenter.getTaxonomySubjects(viewType, 1, "subject", 0, 20);
		addToPopupSlot(taxonomyPopupPresenter);
	}

	public void callCourseInfoTaxonomy(){
		String courseId=AppClientFactory.getPlaceManager().getRequestParameter("o1",null);
		String unitId=AppClientFactory.getPlaceManager().getRequestParameter("o2",null);
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
	/*public void addTaxonomyData(UlPanel selectedUlContainer) {
		getView().addTaxonomyData(selectedUlContainer);
	}*/

	@Override
	public CenturySkillsPresenter getCenturySkillsPresenters(){
		return centurySkillsPresenter;
	}

	public void addTaxonomyData(List<LiPanelWithClose> liPanelWithCloseArray, List<LiPanelWithClose> removedLiPanelWithCloseArray) {
		getView().addTaxonomyData(liPanelWithCloseArray,removedLiPanelWithCloseArray);
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
   	public void setSelectedStandards(List<Map<String,String>> standListArray){
   		getView().displaySelectedStandards(standListArray);
   	}

	@Override
	public void displayCropImage(String imageUrl) {
		addToPopupSlot(imgUploadPresenter);
		MediaUploadDo mediaUploadDo=new MediaUploadDo();
		System.out.println("createDoObj.getThumbnails().getUrl()::"+imageUrl);
		mediaUploadDo.setName("e6355260-d03f-484c-b6eb-dd6dcd075b12.png");
		mediaUploadDo.setUrl(imageUrl);
		imgUploadPresenter.getView().displayCropPopup(mediaUploadDo);
		imgUploadPresenter.setCollectionImage(true);
		imgUploadPresenter.setProfileImage(false);
		imgUploadPresenter.setEditResourceImage(false);
		imgUploadPresenter.setAnswerImage(false);
	}
}
