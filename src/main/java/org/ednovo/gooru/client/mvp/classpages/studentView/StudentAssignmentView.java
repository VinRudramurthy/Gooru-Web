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
package org.ednovo.gooru.client.mvp.classpages.studentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.StudentJoinClassPopup;
import org.ednovo.gooru.client.mvp.classpages.edit.AssignmentProgressVc;
import org.ednovo.gooru.client.mvp.classpages.event.SetSelectedClasspageListEvent;
import org.ednovo.gooru.client.mvp.classpages.tabitem.assignments.collections.CollectionsView;
import org.ednovo.gooru.client.mvp.socialshare.SentEmailSuccessVc;
import org.ednovo.gooru.client.uc.PaginationButtonUc;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
/**
 *
 * @fileName : StudentAssignmentView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: Nov 22, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class StudentAssignmentView extends BaseViewWithHandlers<StudentAssignmentUiHandlers> implements IsStudentAssignmentView ,ClickHandler{

	private static StudentAssignmentViewUiBinder uiBinder = GWT.create(StudentAssignmentViewUiBinder.class);


	interface StudentAssignmentViewUiBinder extends UiBinder<Widget, StudentAssignmentView> {

	}

	@UiField Label mainTitleLbl,noAssignmentMsg,lblUserName,lblAssignmentProgress;

	@UiField HTMLPanel contentpanel,panelProgressContainer,panelAssignmentPath;
	@UiField HTMLEventPanel panelPrevious,panelNext;

	@UiField
	static HTMLPanel mainContainer,memberContainer;

	@UiField Button backToEditPanel;

	/*@UiField
	static Button btnJoinClass;*/

	/*@UiField
	static
	Button btnWithDraw;*/

	@UiField FlowPanel paginationFocPanel,paginationFocPanel1,panelAssignmentProgress;

	@UiField Image studentViewImage,imgProfileImage;

//	@UiField
//	static Image userImage;

	@UiField
	static Label  lblNext, lblPrevious;

	@UiField
	static
	Label LblMember;

	static StudentJoinClassPopup joinPopupPrivate;

	static StudentJoinClassPopup joinPopupPublic;

	static StudentJoinClassPopup joinPopupButtonClick;

	static ClasspageDo classpageDo;

	String defaultProfileImage = "images/settings/setting-user-image.png";

	private static boolean isJoinPopupPublic = false;

	private static boolean isJoinPopupPrivate = false;

	private static boolean isJoinPopupPrivateStatic = false;

	private static boolean isJoinPopupPublicStatic = false;

	private static boolean isJoinPopupButtonclick = false;

	private static final String PREVIOUS = "PREVIOUS";

	private static final String NEXT = "NEXT";

	private String DEFAULT_CLASSPAGE_IMAGE = "images/Classpage/default-classpage.png";
	private int totalHitCount=0;
	private int limit=5;
	private int pageNumber=1;
	private PopupPanel toolTipPopupPanel = new PopupPanel();

	public static boolean islogin = false;
	public static boolean isloginPrivate = false;
	public static boolean isloginButtonClick = false;

	private Map<String,AssignmentProgressVc> assignmentsDotsMap=new HashMap<String,AssignmentProgressVc>();

	List<String> sortingOptionsList=new ArrayList<String>();

	private Integer defaultOffsetForPath=0;
	private Integer defaultLimitForPath=20;

	private static final String GOORU_UID = "gooruuid";

	public static final MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public StudentAssignmentView() {
		setWidget(uiBinder.createAndBindUi(this));
		/*EditClasspageCBundle.INSTANCE.css().ensureInjected();*/
		setStaticData();
		/*lblWebHelp.addMouseOverHandler(new OnMouseOver());
		lblWebHelp.addMouseOutHandler(new OnMouseOut());*/

		addSortingOptionsToList();
		addSortEventToText();


		lblAssignmentProgress.setText(i18n.GL1971());
		lblAssignmentProgress.getElement().setId("lblAssignmentProgress");
		lblAssignmentProgress.getElement().setAttribute("alt",i18n.GL1971());
		lblAssignmentProgress.getElement().setAttribute("title",i18n.GL1971());

		lblNext.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				defaultOffsetForPath = defaultOffsetForPath +defaultLimitForPath;

				callAssignmentAPI(AppClientFactory.getPlaceManager().getRequestParameter("id"), defaultOffsetForPath.toString(), defaultLimitForPath.toString());
			}
		});

		lblPrevious.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (defaultOffsetForPath <=0){
					defaultOffsetForPath =0;
				}else{
					defaultOffsetForPath = defaultOffsetForPath - defaultLimitForPath;
				}

				callAssignmentAPI(AppClientFactory.getPlaceManager().getRequestParameter("id"), defaultOffsetForPath.toString(), defaultLimitForPath.toString());

			}
		});

		lblNext.setVisible(false);
		lblPrevious.setVisible(false);

		Event.addNativePreviewHandler(new NativePreviewHandler() {
	        public void onPreviewNativeEvent(NativePreviewEvent event) {
	        	hideDropDown(event);
	          }
	    });
	}
	/**
	 *
	 * @function addSortEventToText
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
	private void addSortEventToText(){
		if(sortingOptionsList.size()>0){
			for(int i=0;i < sortingOptionsList.size();i++){
				String sortType=sortingOptionsList.get(i);
				Label sortingLabel=new Label(sortType);
				sortingLabel.setStyleName("dropdownTextLabel");
				sortingLabel.addClickHandler(new SortAssignmentEvents(sortType));
			}
		}

	}
	/**
	 *
	 * @fileName : StudentAssignmentView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 07-Dec-2014
	 *
	 * @Author Gooru Team
	 *
	 * @Reviewer:
	 */
	public class SortAssignmentEvents implements ClickHandler{
		private String sortType=null;
		public SortAssignmentEvents(){}
		public SortAssignmentEvents(String sortType){
			this.sortType=sortType;
		}
		@Override
		public void onClick(ClickEvent event) {
			/*if(!dropdownPlaceHolder.getText().equals(sortType)){
				dropdownPlaceHolder.setText(sortType);
				dropdownPlaceHolder.getElement().setAttribute("alt",sortType);
				dropdownPlaceHolder.getElement().setAttribute("title",sortType);
				String sortingStringValue="";
				if(sortType.equals(i18n.GL1946())){
					sortingStringValue="all";
				}else if(sortType.equals(i18n.GL1952())){
					sortingStringValue="completed";
				}else if(sortType.equals(i18n.GL1953())){
					sortingStringValue="todo";
				}
				contentpanel.clear();
				noAssignmentMsg.setVisible(false);
				contentpanel.add(setLoadingPanel());

				Map<String,String> params = new HashMap<String,String>();
				params = StringUtil.splitQuery(Window.Location.getHref());
				params.put("order", sortingStringValue);
				params.put("pageNum", "1");

				PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.STUDENT, params);
				AppClientFactory.getPlaceManager().revealPlace(true, placeRequest, false);
			}else{
				dropDownListContainer.setVisible(false);
			}*/

		}
	}

	/**
	 *
	 * @function hideDropDown
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param event
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void hideDropDown(NativePreviewEvent event){
    	if(event.getTypeInt()==Event.ONCLICK){
    		Event nativeEvent = Event.as(event.getNativeEvent());
        	boolean target=eventTargetsPopup(nativeEvent);

    	}
     }
	/**
	 *
	 * @function eventTargetsPopup
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param event
	 * @parm(s) : @return
	 *
	 * @return : boolean
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private boolean eventTargetsPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
		}
		return false;
	}
	/**
	 *
	 * @function setStaticData
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
	private void setStaticData() {
		backToEditPanel.setText(i18n.GL1130());
		backToEditPanel.getElement().setAttribute("alt",i18n.GL1130());
		backToEditPanel.getElement().setAttribute("title",i18n.GL1130());
		backToEditPanel.getElement().setId("btnBackToEdit");

		noAssignmentMsg.setText(i18n.GL1131());
		noAssignmentMsg.getElement().setId("lblNoAssignmentMsg");
		noAssignmentMsg.getElement().setAttribute("alt",i18n.GL1131());
		noAssignmentMsg.getElement().setAttribute("title",i18n.GL1131());



		LblMember.setText(i18n.GL1549());
		LblMember.getElement().setId("lblMember");
		LblMember.getElement().setAttribute("alt",i18n.GL1549());
		LblMember.getElement().setAttribute("title",i18n.GL1549());


		noAssignmentMsg.setVisible(false);
		LblMember.setVisible(false);
		mainContainer.getElement().setId("pnlMainFlow");
		studentViewImage.getElement().setId("imgStudentView");
		mainTitleLbl.getElement().setId("lblMainTitle");
		imgProfileImage.getElement().setId("imgProfileImage");
		lblUserName.getElement().setId("lblUserName");
		panelProgressContainer.getElement().setId("pnlProgressContainer");
		panelPrevious.getElement().setId("pnlPrevious");
		lblPrevious.getElement().setId("lblPrevious");
		panelAssignmentPath.getElement().setId("pnlAssignmentPath");
		panelAssignmentProgress.getElement().setId("pnlAssignmentProgress");
		panelNext.getElement().setId("pnlNext");
		lblNext.getElement().setId("lblNext");
		paginationFocPanel.getElement().setId("fpnlPaginationFoc");
		paginationFocPanel1.getElement().setId("fpnlPaginationFoc1");
		contentpanel.getElement().setId("pnlContent");
	}

	@Override
	public void setClasspageData(ClasspageDo classpageDo) {
		this.classpageDo=classpageDo;
		mainTitleLbl.setText(classpageDo.getTitle() !=null ? classpageDo.getTitle() : "");
		mainTitleLbl.getElement().setAttribute("alt",classpageDo.getTitle() !=null ? classpageDo.getTitle() : "");
		mainTitleLbl.getElement().setAttribute("title",classpageDo.getTitle() !=null ? classpageDo.getTitle() : "");
		studentViewImage.setAltText(classpageDo.getTitle() !=null ? classpageDo.getTitle() : "");
		studentViewImage.setTitle(classpageDo.getTitle() !=null ? classpageDo.getTitle() : "");

		if(classpageDo.getThumbnailUrl() != null){
			studentViewImage.setUrl(classpageDo.getThumbnailUrl() == "" ? DEFAULT_CLASSPAGE_IMAGE : classpageDo.getThumbnailUrl());
		}else{
			studentViewImage.setUrl(DEFAULT_CLASSPAGE_IMAGE);
		}

		AppClientFactory.fireEvent(new SetSelectedClasspageListEvent(classpageDo.getClasspageId()));

		if(classpageDo.getCreatorProfileImage() != null){
			imgProfileImage.setUrl(classpageDo.getCreatorProfileImage());
		}else{
			imgProfileImage.setUrl(defaultProfileImage);
		}


		lblUserName.setText(classpageDo.getCreatorUsername() + "'s " + i18n.GL0102().toLowerCase());
		lblUserName.getElement().setAttribute("alt",classpageDo.getCreatorUsername() + "'s " + i18n.GL0102().toLowerCase());
		lblUserName.getElement().setAttribute("title",classpageDo.getCreatorUsername() + "'s " + i18n.GL0102().toLowerCase());
		imgProfileImage.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				imgProfileImage.setUrl(defaultProfileImage);
			}
		});

		studentViewImage.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				studentViewImage.setUrl(DEFAULT_CLASSPAGE_IMAGE);
			}
		});


		if(classpageDo.getSharing().equalsIgnoreCase("public"))
		{
			if(classpageDo.getCreatorId().equalsIgnoreCase(AppClientFactory.getGooruUid()))
			{
				memberContainer.setStyleName("techerStyle");
				LblMember.setVisible(true);
				LblMember.setText(i18n.GL1551());
				mainContainer.setVisible(true);
			}
			else if(classpageDo.getStatus().equalsIgnoreCase("active"))
			{
				memberContainer.setStyleName("studentStyle");
				LblMember.setVisible(true);
				LblMember.setText(StudentAssignmentView.i18n.GL1549());
				LblMember.setVisible(true);
				LblMember.setText(i18n.GL1549());
				mainContainer.setVisible(true);
			}
			else
			{
				memberContainer.setStyleName("techerStyle");
				LblMember.setVisible(false);
				mainContainer.setVisible(true);
			}
		}
		else
		{
			if(classpageDo.getCreatorId().equalsIgnoreCase(AppClientFactory.getGooruUid()))
			{
				LblMember.setVisible(true);
				LblMember.setText(i18n.GL1551());
				mainContainer.setVisible(true);
			}
			else if(classpageDo.getStatus().equalsIgnoreCase("active")){
					memberContainer.setStyleName("studentStyle");
					LblMember.setVisible(true);
					LblMember.setText(StudentAssignmentView.i18n.GL1549());
					mainContainer.setVisible(true);
				}
			else {
					try
					{
					mainContainer.setVisible(false);
					}
					catch(Exception ex)
					{
						AppClientFactory.printSevereLogger("StudentAssignmentView : setClasspageData : "+ex.getMessage());
					}
				       if(AppClientFactory.isAnonymous()){
				    	   new SentEmailSuccessVc(i18n.GL1177(), i18n.GL1535());
				       }else{
				    	   new SentEmailSuccessVc(i18n.GL1177(), i18n.GL1535_1());
				       }
				}

		}
	}

	@Override
	public void showClasspageItems(ArrayList<ClasspageItemDo> classpageItemsList1, String sortOrder){
		removeLoadingPanel();
		contentpanel.clear();
		ArrayList<ClasspageItemDo> classpageItemsList = new ArrayList<ClasspageItemDo>();
		classpageItemsList.clear();
		classpageItemsList.addAll(classpageItemsList1);

		if(classpageItemsList!=null&&classpageItemsList.size()>0){
			noAssignmentMsg.setVisible(false);
			for(int itemIndex=0;itemIndex<classpageItemsList.size();itemIndex++){
				ClasspageItemDo classpageItemDo=classpageItemsList.get(itemIndex);
				CollectionsView collectionsView = new CollectionsView(classpageItemDo,true,(itemIndex+1)){
					public void resetPagination(){
						setPagination();
						contentpanel.add(setLoadingPanel());
						getUiHandlers().getNextClasspageItems(((pageNumber*limit)-1),1);
					}
					public void updateAssignmentCircleColor(String collectionItemId,String readStatus){
						updateCircleColors(collectionItemId,readStatus);
					}
				};
				this.totalHitCount=classpageItemDo.getTotalHitCount();
				contentpanel.add(collectionsView);
			}
			setPagination();
		}else{
			this.totalHitCount=0;
			setPagination();
			noAssignmentMsg.setVisible(true);
		}
	}
	/**
	 *
	 */
	public void resetAll(){
		contentpanel.clear();
		contentpanel.add(setLoadingPanel());
		this.totalHitCount=0;
		setPagination();
		mainTitleLbl.setText("");
		studentViewImage.setAltText("");
		studentViewImage.setTitle("");
		studentViewImage.setUrl("");
		imgProfileImage.setUrl("");
		lblUserName.setText("");
		panelAssignmentProgress.clear();
		assignmentsDotsMap.clear();
	}
	/**
	 *
	 * @function setPagination
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
	public void setPagination(){
		if(this.totalHitCount>5){
			showPaginationButton();
		}else{
			clearPaginationButton();
		}
	}
	/**
	 *
	 * @function showPaginationButton
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
	public void showPaginationButton(){
		paginationFocPanel.clear();
		paginationFocPanel1.clear();
		String pageNum=AppClientFactory.getPlaceManager().getRequestParameter("pageNum", "1");
		try{
			pageNumber=Integer.parseInt(pageNum);
			pageNumber=pageNumber==0?1:pageNumber;
		}catch(NumberFormatException e){
			AppClientFactory.printSevereLogger("StudentAssignmentView : showPaginationButton : "+e.getMessage());
		}
		int totalPages = (this.totalHitCount / 5)
				+ ((this.totalHitCount % 5) > 0 ? 1 : 0);
		//int pageNumCount = pageNum + 1;
		if (totalPages > 1) {
			if (pageNumber > 1) {
				paginationFocPanel.add(new PaginationButtonUc(pageNumber - 1, PREVIOUS, this));
				paginationFocPanel1.add(new PaginationButtonUc(pageNumber - 1, PREVIOUS, this));
			}

			int page = pageNumber < 5 ? 1 : pageNumber - 3;

			for (int count = 1; count < 5 && page <= totalPages; page++, ++count)
			{
				paginationFocPanel.add(new PaginationButtonUc(page, page == pageNumber, this));
				paginationFocPanel1.add(new PaginationButtonUc(page, page == pageNumber, this));
			}
			if (pageNumber < totalPages) {
				paginationFocPanel.add(new PaginationButtonUc(pageNumber + 1, NEXT, this));
				paginationFocPanel1.add(new PaginationButtonUc(pageNumber + 1, NEXT, this));
			}
		}
	}
	/**
	 *
	 * @function clearPaginationButton
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
	public void clearPaginationButton(){
		paginationFocPanel.clear();
		paginationFocPanel1.clear();
	}
	/**
	 *
	 * @fileName : StudentAssignmentView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 07-Dec-2014
	 *
	 * @Author Gooru Team
	 *
	 * @Reviewer:
	 */
	private class PaginationEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			pageNumber++;
			setPagination();
			contentpanel.add(setLoadingPanel());
			getUiHandlers().getNextClasspageItems(((pageNumber-1)*limit),limit);
		}
	}
	/**
	 *
	 * @function resetEditClasspageView
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
	public void resetEditClasspageView(){
		paginationFocPanel.clear();
		paginationFocPanel1.clear();
		contentpanel.clear();
		contentpanel.add(setLoadingPanel());
		limit=5;
		pageNumber=1;
	}
	/**
	 *
	 * @function setLoadingPanel
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : Label
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public Label setLoadingPanel(){
		Label loadingImage=new Label();
		loadingImage.setStyleName("loadingpanelImage");
		return loadingImage;
	}
	/**
	 *
	 * @function removeLoadingPanel
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
	public void removeLoadingPanel(){
		if(contentpanel.getWidgetCount()>0){
			Widget loadingPanel=contentpanel.getWidget(contentpanel.getWidgetCount()-1);
			if(loadingPanel!=null&&loadingPanel instanceof Label){
				loadingPanel.removeFromParent();
			}
		}
	}

	@Override
	public void showClasspageItemsForAssignmentPath(ArrayList<ClasspageItemDo> classpageItemsList) {
		//TODO
		panelAssignmentProgress.clear();
		assignmentsDotsMap.clear(); // TODO dont forget to clear when panelAssignmentProgress clear
		if(classpageItemsList!=null&&classpageItemsList.size()>0){
			//hide/show the next and previous buttons
			if (classpageItemsList.get(0).getTotalHitCount() > defaultLimitForPath && classpageItemsList.size() == defaultLimitForPath){
				lblNext.setVisible(true);
			}else{
				lblNext.setVisible(false);
			}

			if (defaultOffsetForPath <= 0){
				lblPrevious.setVisible(false);
			}else{
				lblPrevious.setVisible(true);
			}
			if (classpageItemsList.size() > 0)
				panelAssignmentProgress.clear();

			for(int itemIndex=0; itemIndex<classpageItemsList.size(); itemIndex++){
				ClasspageItemDo classpageItemDo=classpageItemsList.get(itemIndex);
				AssignmentProgressVc assignmentProgressVc =new AssignmentProgressVc((itemIndex == classpageItemsList.size() - 1) ? true : false, classpageItemDo, classpageItemDo.getSequenceNumber());
				assignmentsDotsMap.put(classpageItemDo.getCollectionItemId(), assignmentProgressVc);
				panelAssignmentProgress.add(assignmentProgressVc);
				this.totalHitCount=classpageItemDo.getTotalHitCount();
			}
		}
	}
	/**
	 *
	 * @function updateCircleColors
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param collectionItemId
	 * @parm(s) : @param readStatus
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void updateCircleColors(String collectionItemId,String readStatus){
		AssignmentProgressVc assignmentProgressVc=assignmentsDotsMap.get(collectionItemId);
		if(assignmentProgressVc!=null){
			assignmentProgressVc.updateDotsCircle(readStatus);
		}
	}


	@UiHandler("backToEditPanel")
	public void onClickHandler(ClickEvent event){
		//getPreviousPage();
		String pageSize=Cookies.getCookie("pageSize");
		String classpageid=Cookies.getCookie("classpageId");
		String pageNum=Cookies.getCookie("pageNum");
		String pos=Cookies.getCookie("pos");

		if(AppClientFactory.getPlaceManager().getRequestParameter("source").equalsIgnoreCase("T")){
//			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.TEACH);
		}
		else if(AppClientFactory.getPlaceManager().getRequestParameter("source").equalsIgnoreCase("E")){

			Map<String, String> params=new HashMap<String, String>();
			//params.put("pageSize", pageSize);
			params.put("classpageId", classpageid);
			params.put("pageNum", pageNum);
			//params.put("pos", pos);

			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.EDIT_CLASSPAGE,params,true);
		}
		backToEditPanel.setVisible(false);
	}

	@Override
	public void onClick(ClickEvent event) {
	if (event.getSource() instanceof PaginationButtonUc) {
			int pagenumber = ((PaginationButtonUc) event.getSource()).getPage();
			pageNumber = pagenumber;
			setPagination();
			contentpanel.clear();
			contentpanel.add(setLoadingPanel());
			Map<String,String> params = new HashMap<String,String>();
			String classpageid=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			String order=AppClientFactory.getPlaceManager().getRequestParameter("order", null);
			String source=AppClientFactory.getPlaceManager().getRequestParameter("source", null);
			String backButtonStatus=AppClientFactory.getPlaceManager().getRequestParameter("b", null);
			params.put("order", order);
			params.put("id", classpageid);
			if(source!=null){
				params.put("source", source);
			}
			if(backButtonStatus!=null){
				params.put("b", "true");
			}
			params.put("pageNum", pagenumber+"");
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.STUDENT, params);
			AppClientFactory.getPlaceManager().revealPlace(true, placeRequest, false);
		}
	}

	@Override
	public void clearAll() {
		paginationFocPanel.clear();
		paginationFocPanel1.clear();
		contentpanel.clear();
		contentpanel.add(setLoadingPanel());
		limit=5;
		pageNumber=1;
	}

	@Override
	public Button getBackToEditPanel() {
		return backToEditPanel;
	}
	/**
	 *
	 * @function setPrivatePage
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
	public static void setPrivatePage()
	{
		LblMember.setVisible(true);
		LblMember.setText(i18n.GL1551());
		mainContainer.setVisible(true);
	}
	/**
	 *
	 * @function setPrivatePageActive
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
	public static void setPrivatePageActive()
	{
	LblMember.setVisible(true);
	LblMember.setText(i18n.GL1549());
	mainContainer.setVisible(true);
	}



	/**
	 *
	 * @function getMainContainerStatus
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : boolean
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public static boolean getMainContainerStatus()
	{
		Boolean mainContainerStatus = false;
		try
		{
			mainContainerStatus = mainContainer.isVisible();
		}
		catch(Exception ex)
		{
			AppClientFactory.printSevereLogger("StudentAssignmentView : getMainContainerStatus : "+ex.getMessage());
		}
		return mainContainerStatus;
	}
	/**
	 *
	 * @function addSortingOptionsToList
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
	public void addSortingOptionsToList(){
		sortingOptionsList.clear();
		sortingOptionsList.add(i18n.GL1946());
		sortingOptionsList.add(i18n.GL1952());
		sortingOptionsList.add(i18n.GL1953());

	}

	/**
	 *
	 * @function callAssignmentAPI
	 *
	 * @created_date : Jun 11, 2014
	 *
	 * @description
	 *
	 *
	 * @param classpageId
	 * @param pageSize
	 * @param pageNum
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	@Override
	public void callAssignmentAPI(String classpageId, String offsetProgress, String limitProgress){
		getUiHandlers().getAssignmentsProgress(classpageId, offsetProgress.toString(), limitProgress.toString()); // this will call showClasspageItemsForAssignmentPath
	}

	@Override
	public void setSortingOrderInDropdown(String sortingOrder) {
	}

}