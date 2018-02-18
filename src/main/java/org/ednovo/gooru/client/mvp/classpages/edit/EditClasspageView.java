/**
 *
 */
package org.ednovo.gooru.client.mvp.classpages.edit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.AssignmentsListDo;
import org.ednovo.gooru.application.shared.model.content.AssignmentsSearchDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageListDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.DataInsightsUrlTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpages.classlist.ClassListPresenter;
import org.ednovo.gooru.client.mvp.classpages.event.DeleteClasspageListEvent;
import org.ednovo.gooru.client.mvp.classpages.event.RefreshClasspageResourceItemListEvent;
import org.ednovo.gooru.client.mvp.classpages.event.SetSelectedClasspageListEvent;
import org.ednovo.gooru.client.mvp.classpages.event.UpdateClasspageTitleEvent;
import org.ednovo.gooru.client.mvp.classpages.newclasspage.NewClasspagePopupView;
import org.ednovo.gooru.client.mvp.classpages.tabitem.assignments.collections.CollectionsView;
import org.ednovo.gooru.client.mvp.search.event.ResetProgressEvent;
import org.ednovo.gooru.client.mvp.search.event.ResetProgressHandler;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.DeleteConfirmPopupVc;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.assign.CollectionAssignCBundle;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.DeletePopupViewVc;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.uc.AssignmentEditLabelUc;
import org.ednovo.gooru.client.uc.PaginationButtonUc;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.uc.tooltip.ToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @fileName : EditClasspageView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: Apr 17, 2013
 *
 * @Author Anil Kumar T
 *
 * @Reviewer:
 */
public class EditClasspageView extends
		BaseViewWithHandlers<EditClasspageUiHandlers> implements
		IsEditClasspageView, ClickHandler{

	/*@UiField(provided = true)
	EditClasspageCBundle res;*/

	@UiField(provided = true)
	AssignmentEditLabelUc collectionTitleUc;

	ArrayList<ClasspageItemDo> globalClasspageProcess;

	ArrayList<ClasspageItemDo> classpageItemsList = new ArrayList<ClasspageItemDo>();

	/**
	 * This method is to get the globalClasspageProcess
	 */
	@Override
	public ArrayList<ClasspageItemDo> getGlobalClasspageProcess() {
		return globalClasspageProcess;
	}
	/**
	 * This method is to set the globalClasspageProcess
	 */
	public void setGlobalClasspageProcess(
			ArrayList<ClasspageItemDo> globalClasspageProcess) {
		this.globalClasspageProcess = globalClasspageProcess;
	}

	private PopupPanel toolTipPopupPanelNew = new PopupPanel();

	@UiField Label assignmentsDirectionsLabel, lblDefine, lblIncoporate;

	@UiField Image imgClasspageImage;

	@UiField FlowPanel mainFlowPanel;

	@UiField
	static HTMLPanel hideToggleButtons;

	@UiField HTMLPanel frameContainer,panelUpdateActionContols, panelAssignmentProgress, headerAssignments,panelAssignmentPath, panelProgressContainer,getstarteddiv/*,htmlInstructionalListContainer*/;

	@UiField
	static HTMLPanel frameDiv;

	@UiField
	static HTMLPanel mainContainer;

	@UiField
	static Frame frameUrl;

	@UiField Label titleAlertMessageLbl, lblNext, lblPrevious, lblAssignHeader,lblAssignText,lblAssignDes,lblStartAssign,lblAssignDetails,lblSequenceText,lblReadytoStart;

	@UiField Button btnStudentView,btnReadytoStart;

	@UiField
	static InlineLabel monitorProgress,monitorSummary,sequenceNumberLabel;

	@UiField
	static
	Button backArrowButton;

	@UiField
	Button assignmentsTab,btnAssignCollection;

	@UiField
	Button classListTab,reportsTab;

	@UiField HTMLPanel shareTabContainerPanel, assignmentsTabContainerPanel, noAssignmentsMessagePanel, newAssignmentAndMsgPanel;

	@UiField FlowPanel paginationFocPanel,paginationFocPanel1,assignmentsContainerPanel,classListContainer;

	@UiField FocusPanel simplePencilFocPanel, classPageTitle,collectionFloPanel;

	@UiField Button btnClasspageCancel, btnClasspageSave;

	@UiField TextBox classCodeTextBox;

	@UiField HTMLPanel questionMarkPanel;

	@UiField HTMLEventPanel panelPrevious,panelNext;

	@UiField SimpleCheckBox changeProgressSummary;

	@UiField Button btnDeleteClasspage;

	NewClasspagePopupView newPopup = null;
	private  ClasspageDo classpageDo =null;

	private HandlerRegistration reportHandler;
	private HandlerRegistration reportMouseOverHandler;

	DeleteConfirmPopupVc deleteConfirmVc =null;

	boolean isEditing=false;

	boolean isClicked=true;

	boolean refresh = true;

	private String DEFAULT_CLASSPAGE_IMAGE = "images/Classpage/default-classpage.png";

	private String classpageId = null;

	private static final String SHORTEN_URL = "shortenUrl";

	private static final String PREVIOUS = "PREVIOUS";

	private static final String NEXT = "NEXT";

	private CollectionsView assignmentTabView;

	private int pageSize = 5;

	private int pageNum = 0;

	private Integer offsetProgress=0;
	private Integer limitProgress=20;

	private int pos = 0;

	int noOfItems = 5;

	private int assignmentCount = 0;

	private int totalHitCount=0;
	private int limit=5;
	private int pageNumber=0;

	ToolTip toolTip = null;

	ClassListPresenter classlistPresenter;

	List<CollectionItemDo> collectionItemList = new ArrayList<CollectionItemDo>();

	List<String> sortingOptionsList=new ArrayList<String>();

	final String SUMMARY="Summary",PROGRESS="Progress",REPORTS="reports";

	private static EditClassPageViewUiBinder uiBinder = GWT
			.create(EditClassPageViewUiBinder.class);

	static MessageProperties i18n = GWT.create(MessageProperties.class);

	String dropSortOptionsStr = i18n.GL1947();

	interface EditClassPageViewUiBinder extends
			UiBinder<Widget, EditClasspageView> {

	}

	@Inject
	public EditClasspageView() {
		/*this.res = EditClasspageCBundle.INSTANCE;*/
		globalClasspageProcess =  new ArrayList<ClasspageItemDo>();
		collectionTitleUc = new AssignmentEditLabelUc() {
			@Override
			public void onEditDisabled(String text) {
				if (text.trim().length()>0){
					isEditing = false;
					titleAlertMessageLbl.setVisible(false);
					titleAlertMessageLbl.setText(i18n.GL0143());
					titleAlertMessageLbl.getElement().setAttribute("alt",i18n.GL0143());
					titleAlertMessageLbl.getElement().setAttribute("title",i18n.GL0143());
					titleAlertMessageLbl.addStyleName("titleAlertMessageDeActive");
					titleAlertMessageLbl.removeStyleName("titleAlertMessageActive");
		            getUiHandlers().updateClassPageInfo(classpageDo.getClasspageId(),null, text);
					AppClientFactory.fireEvent(new UpdateClasspageTitleEvent(classpageDo.getClasspageId(), text));
				}else{
					/*titleAlertMessageLbl.setText(MessageProperties.GL0173);
					titleAlertMessageLbl.setVisible(true);*/
				}
			}

			@Override
			public void checkCharacterLimit(String text) {
				titleAlertMessageLbl.setText(i18n.GL0143());
				titleAlertMessageLbl.getElement().setAttribute("alt",i18n.GL0143());
				titleAlertMessageLbl.getElement().setAttribute("title",i18n.GL0143());
				if (text.length() >= 50) {
					titleAlertMessageLbl
							.addStyleName("titleAlertMessageActive");
					titleAlertMessageLbl
							.removeStyleName("titleAlertMessageDeActive");
				} else {
					titleAlertMessageLbl
							.addStyleName("titleAlertMessageDeActive");
					titleAlertMessageLbl
							.removeStyleName("titleAlertMessageActive");
				}
			}
		};

		/*res.css().ensureInjected();*/

		setWidget(uiBinder.createAndBindUi(this));

		CollectionAssignCBundle.INSTANCE.css().ensureInjected();

		mainContainer.setVisible(true);

		frameDiv.setVisible(false);
		assignmentsTab.addClickHandler(new AssignmentsTabClicked());
		classListTab.getElement().setAttribute("style", "cursor: default;background: #999999");
		classListTab.setEnabled(false);
		classListTab.getElement().getStyle().setCursor(Cursor.DEFAULT);
		//classListTab.addClickHandler(new ClassListTabClicked());
		shareTabContainerPanel.clear();
		shareTabContainerPanel.setVisible(false);

		panelAssignmentPath.setVisible(true);
		headerAssignments.setVisible(true);
		panelProgressContainer.setVisible(true);
		noAssignmentsMessagePanel.setVisible(false);
		assignmentsTabContainerPanel.setVisible(true);
		assignmentsTab.setEnabled(false);
		panelUpdateActionContols.getElement().getStyle().setDisplay(Display.NONE);

		simplePencilFocPanel.addMouseOverHandler(new hideEditPencil());
		simplePencilFocPanel.addMouseOutHandler(new showEditPencil());

		classPageTitle.addClickHandler(new OnEditImageClick());
		collectionFloPanel.addDomHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				event.stopPropagation();

			}
		}, ClickEvent.getType());


		imgClasspageImage.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				imgClasspageImage.setUrl(DEFAULT_CLASSPAGE_IMAGE);

			}
		});

		backArrowButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
			mainContainer.setVisible(true);
			frameDiv.setVisible(false);

			assignmentsTab.addStyleName("selected");

			classListTab.getElement().setClassName("");

			reportsTab.getElement().setClassName("");

			newAssignmentAndMsgPanel.setVisible(true);
			assignmentsTabContainerPanel.setVisible(true);
			assignmentsTab.setEnabled(false);
			getClassListContainer().setVisible(false);


				Map<String,String> params = new HashMap<String,String>();
				String pageSize=AppClientFactory.getPlaceManager().getRequestParameter("pageSize", null);
				String classpageid=AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID, null);
				String pageNum=AppClientFactory.getPlaceManager().getRequestParameter("pageNum", null);
				String pos=AppClientFactory.getPlaceManager().getRequestParameter("pos", null);
				params.put("pageSize", pageSize);
				params.put(UrlNavigationTokens.CLASSPAGEID, classpageid);
				params.put("pageNum", pageNum);
				params.put("pos", pos);
				PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.EDIT_CLASSPAGE, params);
				if(refresh){
					AppClientFactory.getPlaceManager().revealPlace(true, placeRequest, true);
					refresh = false;
				}else{
					AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
				}
			}
		});
		changeProgressSummary.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String collectionId=null;
				String monitorid=AppClientFactory.getPlaceManager().getRequestParameter("monitorid", null);
				if(monitorid==null){
					collectionId=AppClientFactory.getPlaceManager().getRequestParameter("analyticsId", null);
				}else{
					collectionId=monitorid;
				}
				if(changeProgressSummary.getValue()){
					sequenceNumberLabel.setText(i18n.GL2229());
					getUiHandlers().setCollectionProgressData(PROGRESS,collectionId,collectionTitleUc.getText());
				}else{
					sequenceNumberLabel.setText(i18n.GL2228());
					getUiHandlers().setCollectionProgressData(SUMMARY,collectionId,collectionTitleUc.getText());
				}
			}
		});

		panelUpdateActionContols.getElement().setId("panelUpdateActionContols");

		btnStudentView.setText(i18n.GL0139());
		btnStudentView.getElement().setAttribute("alt",i18n.GL0139());
		btnStudentView.getElement().setAttribute("title",i18n.GL0139());

		btnClasspageSave.setText(i18n.GL0141());
		btnClasspageSave.getElement().setAttribute("alt",i18n.GL0141());
		btnClasspageSave.getElement().setAttribute("title",i18n.GL0141());

		btnClasspageCancel.setText(i18n.GL0142());
		btnClasspageCancel.getElement().setAttribute("alt",i18n.GL0142());
		btnClasspageCancel.getElement().setAttribute("title",i18n.GL0142());

		titleAlertMessageLbl.setText(i18n.GL0143());
		titleAlertMessageLbl.getElement().setId("lblTitleAlertMessage");
		titleAlertMessageLbl.getElement().setAttribute("alt",i18n.GL0143());
		titleAlertMessageLbl.getElement().setAttribute("title",i18n.GL0143());

		btnAssignCollection.setText(i18n.GL2115());
		btnAssignCollection.getElement().setId("btnAssignCollection");
		btnAssignCollection.getElement().setAttribute("alt",i18n.GL2115());
		btnAssignCollection.getElement().setAttribute("title",i18n.GL2115());

		btnAssignCollection.addStyleName(CssTokens.DISABLED);
		btnAssignCollection.setEnabled(false);

		lblStartAssign.setText(i18n.GL2116());
		lblStartAssign.getElement().setId("lblStartAssign");
		lblStartAssign.getElement().setAttribute("alt",i18n.GL2116());
		lblStartAssign.getElement().setAttribute("title",i18n.GL2116());



		backArrowButton.setText(i18n.GL1617());
		backArrowButton.getElement().setAttribute("alt",i18n.GL1617());
		backArrowButton.getElement().setAttribute("title",i18n.GL1617());

		lblAssignHeader.setText(i18n.GL2113());
		lblAssignHeader.getElement().setId("lblAssignHeader");
		lblAssignHeader.getElement().setAttribute("alt",i18n.GL2113());
		lblAssignHeader.getElement().setAttribute("title",i18n.GL2113());

		lblAssignText.setText(i18n.GL1973());
		lblAssignText.getElement().setId("lblAssignText");
		lblAssignText.getElement().setAttribute("alt",i18n.GL1973());
		lblAssignText.getElement().setAttribute("title",i18n.GL1973());

		lblAssignDes.setText(i18n.GL2114());
		lblAssignDes.getElement().setId("lblAssignDes");
		lblAssignDes.getElement().setAttribute("alt",i18n.GL2114());
		lblAssignDes.getElement().setAttribute("title",i18n.GL2114());

		monitorProgress.setText(i18n.GL2229());
		monitorProgress.getElement().setId("btnMonitorProgress");
		monitorProgress.getElement().setAttribute("alt",i18n.GL2229());
		monitorProgress.getElement().setAttribute("title",i18n.GL2229());

		monitorSummary.setText(i18n.GL2228());
		monitorSummary.getElement().setId("btnMonitorSummary");
		monitorSummary.getElement().setAttribute("alt",i18n.GL2228());
		monitorSummary.getElement().setAttribute("title",i18n.GL2228());

		lblSequenceText.setText(i18n.GL2117());
		lblSequenceText.getElement().setId("lblSequenceText");
		lblSequenceText.getElement().setAttribute("alt",i18n.GL2117());
		lblSequenceText.getElement().setAttribute("title",i18n.GL2117());

		lblAssignDetails.setText(i18n.GL2118());
		lblAssignDetails.getElement().setId("lblAssignDetails");
		lblAssignDetails.getElement().setAttribute("alt",i18n.GL2118());
		lblAssignDetails.getElement().setAttribute("title",i18n.GL2118());

		btnReadytoStart.setText(i18n.GL2115());
		btnReadytoStart.getElement().setId("btnReadytoStart");
		btnReadytoStart.getElement().setAttribute("alt",i18n.GL2115());
		btnReadytoStart.getElement().setAttribute("title",i18n.GL2115());

		btnReadytoStart.setEnabled(false);
		btnReadytoStart.addStyleName(CssTokens.DISABLED);

		reportsTab.setText(i18n.GL1737());
		reportsTab.getElement().setId("btnReportsTab");
		reportsTab.getElement().setAttribute("alt",i18n.GL1737());
		reportsTab.getElement().setAttribute("title",i18n.GL1737());

		assignmentsDirectionsLabel.setText(i18n.GL1945());
		assignmentsDirectionsLabel.getElement().setId("lblAssignmentsDirections");
		assignmentsDirectionsLabel.getElement().setAttribute("alt",i18n.GL1945());
		assignmentsDirectionsLabel.getElement().setAttribute("title",i18n.GL1945());

		lblReadytoStart.setText(i18n.GL1945());
		lblReadytoStart.getElement().setId("lblReadytoStart");
		lblReadytoStart.getElement().setAttribute("alt",i18n.GL1945());
		lblReadytoStart.getElement().setAttribute("title",i18n.GL1945());

		getstarteddiv.removeStyleName("btnContainerClasswithBG");
		getstarteddiv.setVisible(true);

		lblDefine.setText(i18n.GL1960());
		lblDefine.getElement().setId("lblDefine");
		lblDefine.getElement().setAttribute("alt",i18n.GL1960());
		lblDefine.getElement().setAttribute("title",i18n.GL1960());

		lblIncoporate.setText(i18n.GL1959());
		lblIncoporate.getElement().setId("lblIncoporate");
		lblIncoporate.getElement().setAttribute("alt",i18n.GL1959());
		lblIncoporate.getElement().setAttribute("title",i18n.GL1959());

		lblPrevious.setVisible(false);


		headerAssignments.getElement().setInnerHTML(i18n.GL1972());

		btnDeleteClasspage.setText(i18n.GL0145());
		btnDeleteClasspage.getElement().setAttribute("alt",i18n.GL0145());
		btnDeleteClasspage.getElement().setAttribute("title",i18n.GL0145());

		backArrowButton.getElement().setId("backArrowButton");

		btnStudentView.getElement().setId("btnStudentView");
		btnClasspageSave.getElement().setId("btnClasspageSave");
		btnClasspageCancel.getElement().setId("btnClasspageCancel");

		//btnAssignCollection.addClickHandler(new addAssignmentHandler());
		//btnReadytoStart.addClickHandler(new addAssignmentHandler());

		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		reportHandler=reportsTab.addClickHandler(new reportsTabClicked());

		lblNext.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				offsetProgress = offsetProgress +limitProgress;

				callAssignmentAPI(AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID), offsetProgress.toString(), limitProgress.toString());
			}
		});

		lblPrevious.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (offsetProgress <=0){
					offsetProgress =0;
				}else{
					offsetProgress = offsetProgress - limitProgress;
				}
				callAssignmentAPI(AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID), offsetProgress.toString(), limitProgress.toString());
			}
		});

		ResetProgressHandler reset = new ResetProgressHandler() {

			@Override
			public void callProgressAPI() {
				callAssignmentAPI(AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID), offsetProgress.toString(), limitProgress.toString());
			}
		};
		AppClientFactory.getEventBus().addHandler(ResetProgressEvent.TYPE,reset);
		addSortingOptionsToList();
		addSortEventToText();
		mainFlowPanel.getElement().setId("pnlMainFlow");
		collectionFloPanel.getElement().setId("pnlCollectionFloPanel");
		imgClasspageImage.getElement().setId("imgClasspageImage");
		simplePencilFocPanel.getElement().setId("pnlSimplePencilFocPanel");
		classPageTitle.getElement().setId("pnlClassPageTitle");
		collectionTitleUc.getElement().setId("lblCollectionTitleUc");
		panelUpdateActionContols.getElement().setId("pnlUpdateActionContols");
		classCodeTextBox.getElement().setId("txtClassCode");
		StringUtil.setAttributes(classCodeTextBox, true);
		questionMarkPanel.getElement().setId("pnlQuestionMark");
		mainContainer.getElement().setId("pnlMainContainer");
		panelProgressContainer.getElement().setId("pnlProgressContainer");
		headerAssignments.getElement().setId("pnlHeaderAssignments");
		panelPrevious.getElement().setId("pnlPrevious");
		lblPrevious.getElement().setId("lblPrevious");
		panelAssignmentPath.getElement().setId("pnlAssignmentPath");
		panelAssignmentProgress.getElement().setId("pnlAssignmentProgress");
		panelNext.getElement().setId("pnlNext");
		lblNext.getElement().setId("lblNext");
		newAssignmentAndMsgPanel.getElement().setId("pnlNewAssignmentAndMsg");
		getstarteddiv.getElement().setId("pnlGetstarteddiv");
		assignmentsDirectionsLabel.getElement().setId("lblAssignmentsDirections");
		paginationFocPanel.getElement().setId("pnlPaginationFoc");
		paginationFocPanel1.getElement().setId("pnlPaginationFoc1");
		noAssignmentsMessagePanel.getElement().setId("pnlNoAssignmentsMessage");
		assignmentsTabContainerPanel.getElement().setId("pnlAssignmentsTabContainer");
		assignmentsContainerPanel.getElement().setId("pnlAssignmentsContainer");
		classListContainer.getElement().setId("pnlClassListContainer");
		shareTabContainerPanel.getElement().setId("pnlShareTabContainer");
		frameDiv.getElement().setId("pnlFrameDiv");
		frameUrl.getElement().setId("ifFrameUrl");
		newAssignmentAndMsgPanel.getElement().getStyle().setMarginTop(10, Unit.PX);
		btnDeleteClasspage.getElement().setId("btnDeleteClasspage");
		frameUrl.setVisible(false);
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
	 * @fileName : EditClasspageView.java
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
			//TODO sorting
				String sortingStringValue="";
				if(sortType.equals(i18n.GL1948())){
					sortingStringValue="asce";
				}else if(sortType.equals(i18n.GL1949())){
					sortingStringValue="earliest";// previous it was desc
				}else if(sortType.equals(i18n.GL1950())){
					sortingStringValue="recent";
				}
				else if(sortType.equals(i18n.GL1994())){
					sortingStringValue="latest";// previous it was due date
				}
				assignmentsContainerPanel.clear();
				assignmentsContainerPanel.add(setLoadingPanel());
				offsetProgress = 0;
				limitProgress = 20;
				Map<String,String> params = new HashMap<String,String>();
				String classpageid=AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID, null);
				String pageNum=AppClientFactory.getPlaceManager().getRequestParameter("pageNum", null);
				params.put(UrlNavigationTokens.CLASSPAGEID, classpageid);
				params.put("pageNum", "1");
				params.put("order", sortingStringValue);
				PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.EDIT_CLASSPAGE, params);
				AppClientFactory.getPlaceManager().revealPlace(true, placeRequest, true);

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
        	if(!target){
        	}
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
			//return dropDownListContainer.getElement().isOrHasChild(Element.as(target))||dropdownPlaceHolder.getElement().isOrHasChild(Element.as(target));
		}
		return false;
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
		getUiHandlers().getAssignmentsProgress(classpageId, offsetProgress.toString(), limitProgress.toString()); // this will call displayAssignmentPath
	}
	/**
	 *
	 * @fileName : EditClasspageView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: Jun 11, 2014
	 *
	 * @Author Gooru Team
	 *
	 * @Reviewer:
	 */
	public class hideEditPencil implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {
			if (!isEditing){
			}
		}

	}
	/**
	 *
	 * @fileName : EditClasspageView.java
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
	public class showEditPencil implements MouseOutHandler {
		@Override
		public void onMouseOut(MouseOutEvent event) {
		}

	}
	/**
	 *
	 * @fileName : EditClasspageView.java
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
	private class OnEditImageClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (!isEditing){
				isEditing = true;
				collectionTitleUc.getElement().getStyle().setColor("black");;
				collectionTitleUc.switchToEdit();
				panelUpdateActionContols.getElement().getStyle().setDisplay(Display.BLOCK);
			}

		}
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (content != null) {
			if (slot == EditClasspageUiHandlers.TYPE_SHELF_TAB) {
			}else if(slot==EditClasspagePresenter.CLASSLIST_SLOT){
				getClassListContainer().setVisible(true);
				getClassListContainer().add(content);
			}else if(slot==EditClasspagePresenter.SLOT_SET_SUMMARY_PROGRESS){
				frameContainer.clear();
				frameContainer.add(content);
			}else{
				getClassListContainer().setVisible(false);
			}
		}
	}


	@UiHandler("btnDeleteClasspage")
	public void OnClickDeleteClasspage(ClickEvent event){
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		DeletePopupViewVc delete = new DeletePopupViewVc() {

			@Override
			public void onClickPositiveButton(ClickEvent event) {
				AppClientFactory.getInjector().getClasspageService().deleteClasspage(classpageDo.getClasspageId(), new SimpleAsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
				        AppClientFactory.fireEvent(new DeleteClasspageListEvent(classpageDo.getClasspageId()));
				        Window.enableScrolling(true);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
						hide();
						AppClientFactory.getInjector().getClasspageService().v2GetAllClass("10", "0",new SimpleAsyncCallback<ClasspageListDo>() {
							@Override
							public void onSuccess(ClasspageListDo result) {

								if (result.getSearchResults().size()>0){
									AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.CLASSHOME);
									}else{
									AppClientFactory.getPlaceManager().redirectPlace(PlaceTokens.STUDY);
								}
							}
					});
					}
				});
			}

			@Override
			public void onClickNegitiveButton(ClickEvent event) {
				Window.enableScrolling(true);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
				hide();
			}
		};
		delete.setPopupTitle((i18n.GL0748()));
		delete.setNotes(i18n.GL0748());
		delete.setDescText(StringUtil.generateMessage(i18n.GL0824()+"\""+ classpageDo.getTitle() + "\"" + " "+i18n.GL0102()+i18n.GL_SPL_FULLSTOP() +" "+i18n.GL0825()));
		delete.setPositiveButtonText(i18n.GL0190());
		delete.setNegitiveButtonText(i18n.GL0142());
		delete.setDeleteValidate("delete");
		delete.setPixelSize(450, 352);
		delete.show();
		delete.center();
	}

	@UiHandler("btnStudentView")
	public void OnClickStudentView(ClickEvent event){
		MixpanelUtil.Click_StudentView_Classpage();

		Map<String, String> params = new HashMap<String, String>();
		params.put("id", classpageDo.getClasspageId());
		params.put("b", "true");

		params.put("pageSize", "5");
		params.put("pageNum", "0");
		params.put("pos", "1");

		params.put("source", "E");
		Cookies.setCookie("pageSize", AppClientFactory.getPlaceManager()
				.getRequestParameter("pageSize"));
		Cookies.setCookie(UrlNavigationTokens.CLASSPAGEID, AppClientFactory.getPlaceManager()
				.getRequestParameter(UrlNavigationTokens.CLASSPAGEID));
		Cookies.setCookie("pageNum", AppClientFactory.getPlaceManager()
				.getRequestParameter("pageNum"));
		Cookies.setCookie("pos", AppClientFactory.getPlaceManager()
				.getRequestParameter("pos"));

		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,	params);

	}
	/**
	 *
	 * @function setClasspageData
	 *
	 * @created_date : Jun 11, 2014
	 *
	 * @description
	 *
	 *
	 * @param ClasspageDo classpageDo
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setClasspageData(ClasspageDo classpageDo){
		this.classpageDo=classpageDo;
		assignmentsTab.setText(i18n.GL1623()+"("+classpageDo.getItemCount()+")");
		assignmentsTab.getElement().setId("btnAssignmentsTab");
		assignmentsTab.getElement().setAttribute("alt",i18n.GL1623());
		assignmentsTab.getElement().setAttribute("title",i18n.GL1623());

		classListTab.setText(i18n.GL1624()+"("+(classpageDo.getMemberCount()!=null? classpageDo.getMemberCount() : "0") +")");
		classListTab.getElement().setId("btnClassListTab");
		classListTab.getElement().setAttribute("alt",i18n.GL1624());
		classListTab.getElement().setAttribute("title",i18n.GL1624());

		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		AppClientFactory.fireEvent(new SetSelectedClasspageListEvent(classpageDo.getClasspageId()));
		panelAssignmentPath.setVisible(true);
		headerAssignments.setVisible(true);
		panelProgressContainer.setVisible(true);
		noAssignmentsMessagePanel.setVisible(false);
		collectionTitleUc.setText(classpageDo.getTitle() !=null ? classpageDo.getTitle() : "" );
		collectionTitleUc.getElement().setAttribute("alt",classpageDo.getTitle() !=null ? classpageDo.getTitle() : "" );
		collectionTitleUc.getElement().setAttribute("title",classpageDo.getTitle() !=null ? classpageDo.getTitle() : "" );
		classCodeTextBox.setText(classpageDo.getClasspageCode()!=null ? classpageDo.getClasspageCode().toUpperCase() : "");
		classCodeTextBox.getElement().setAttribute("alt",classpageDo.getClasspageCode()!=null ? classpageDo.getClasspageCode().toUpperCase() : "");
		classCodeTextBox.getElement().setAttribute("title",classpageDo.getClasspageCode()!=null ? classpageDo.getClasspageCode().toUpperCase() : "");

		classCodeTextBox.setReadOnly(true);
		classCodeTextBox.addClickHandler(new ClassCodeTextCopy());

		final Image imgNotFriendly = new Image("images/mos/questionmark.png");
		imgNotFriendly.getElement().getStyle().setLeft(97.8, Unit.PCT);
		imgNotFriendly.getElement().getStyle().setTop(27, Unit.PX);
		imgNotFriendly.getElement().getStyle().setPosition(Position.ABSOLUTE);
		imgNotFriendly.getElement().getStyle().setCursor(Cursor.POINTER);
		imgNotFriendly.addMouseOverHandler(new MouseOverShowClassCodeToolTip());
		imgNotFriendly.addMouseOutHandler(new MouseOutHideToolTip());

		questionMarkPanel.clear();
		questionMarkPanel.add(imgNotFriendly);

		imgClasspageImage.setAltText(classpageDo.getTitle());
		imgClasspageImage.setTitle(classpageDo.getTitle());
		if(classpageDo.getThumbnailUrl() != null){
			imgClasspageImage.setUrl(classpageDo.getThumbnailUrl().isEmpty() ? DEFAULT_CLASSPAGE_IMAGE : classpageDo.getThumbnailUrl());
		}else{
			imgClasspageImage.setUrl(DEFAULT_CLASSPAGE_IMAGE);
		}

	}
	/**
	 *
	 * @fileName : EditClasspageView.java
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
	public class ClassCodeTextCopy implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			classCodeTextBox.selectAll();
			classCodeTextBox.setFocus(true);
		}
	}

	/**
	 *
	 */
	public void showClasspageItems(ArrayList<ClasspageItemDo> classpageItemsList1,String tab, String analyticsId, String monitorId,ClassListPresenter classlistPresenter,int assignmentsCount){
		this.classlistPresenter = classlistPresenter;
		classpageItemsList.clear();
		classpageItemsList.addAll(classpageItemsList1);
		if(tab!=null && tab.equalsIgnoreCase("classList")){
			panelAssignmentPath.setVisible(false);
			headerAssignments.setVisible(false);
			panelProgressContainer.setVisible(false);
			paginationFocPanel.setVisible(false);
			paginationFocPanel1.setVisible(false);
			classListTab.addStyleName("selected");
			assignmentsTab.getElement().setClassName("");
			assignmentsDirectionsLabel.setVisible(false);
			getstarteddiv.removeStyleName("btnContainerClasswithBG");
			getstarteddiv.setVisible(true);
			backArrowButton.setVisible(false);
			monitorProgress.setVisible(false);
			monitorSummary.setVisible(false);
			hideToggleButtons.setVisible(false);
			newAssignmentAndMsgPanel.setVisible(false);
			assignmentsTabContainerPanel.setVisible(false);
			assignmentsTab.setEnabled(true);
			getClassListContainer().setVisible(true);
			frameDiv.setVisible(false);
		}
		else if(tab!=null && tab.equalsIgnoreCase("reports")){
			reportsTab.addStyleName("selected");
			assignmentsTab.getElement().setClassName("");
			classListTab.getElement().setClassName("");
			newAssignmentAndMsgPanel.setVisible(false);
			assignmentsTabContainerPanel.setVisible(false);
			assignmentsTab.setEnabled(true);
			getClassListContainer().setVisible(false);
			assignmentsDirectionsLabel.setVisible(false);
			getstarteddiv.removeStyleName("btnContainerClasswithBG");
			getstarteddiv.setVisible(true);
			backArrowButton.setVisible(false);
			monitorProgress.setVisible(false);
			monitorSummary.setVisible(false);
			hideToggleButtons.setVisible(false);
			panelAssignmentPath.setVisible(false);
			headerAssignments.setVisible(false);
			panelProgressContainer.setVisible(false);
			paginationFocPanel.setVisible(false);
			paginationFocPanel1.setVisible(false);

			frameDiv.setVisible(true);
			frameUrl.getElement().getStyle().setWidth(1000, Unit.PX);
			frameUrl.getElement().getStyle().setHeight(300, Unit.PX);
			getUiHandlers().setCollectionProgressData(REPORTS,analyticsId,collectionTitleUc.getText());
		}
		else if(analyticsId!=null)
		{
			backArrowButton.setVisible(true);
			monitorProgress.setVisible(true);
			hideToggleButtons.setVisible(true);
			monitorSummary.setVisible(true);
			mainContainer.setVisible(false);
			frameDiv.setVisible(true);
			frameUrl.getElement().getStyle().setWidth(1000, Unit.PX);
			frameUrl.getElement().getStyle().setHeight(484, Unit.PX);
			monitorProgress.setVisible(true);
			monitorProgress.setText(i18n.GL2229());
			panelAssignmentPath.setVisible(false);
			headerAssignments.setVisible(false);
			panelProgressContainer.setVisible(false);
			paginationFocPanel.setVisible(false);
			paginationFocPanel1.setVisible(false);
			changeProgressSummary.setValue(false);
			sequenceNumberLabel.setText(i18n.GL2228());
			getUiHandlers().setCollectionProgressData(SUMMARY,analyticsId,collectionTitleUc.getText());

		}
		else if(monitorId!=null)
		{
			backArrowButton.setVisible(true);
			monitorProgress.setVisible(true);
			hideToggleButtons.setVisible(true);
			monitorSummary.setVisible(true);
			mainContainer.setVisible(false);
			frameDiv.setVisible(true);
			frameUrl.getElement().getStyle().setWidth(1000, Unit.PX);
			frameUrl.getElement().getStyle().setHeight(484, Unit.PX);
			//frameUrl.setUrl(frameAnalyticsUrlForMonitor());
			monitorProgress.setVisible(true);
			panelAssignmentPath.setVisible(false);
			headerAssignments.setVisible(false);
			panelProgressContainer.setVisible(false);
			paginationFocPanel.setVisible(false);
			paginationFocPanel1.setVisible(false);
			changeProgressSummary.setValue(true);
			sequenceNumberLabel.setText(i18n.GL2229());
			getUiHandlers().setCollectionProgressData(PROGRESS,monitorId,collectionTitleUc.getText());
		}
		else{
			removeLoadingPanel();
			panelAssignmentPath.setVisible(true);
			headerAssignments.setVisible(true);
			panelProgressContainer.setVisible(true);
			paginationFocPanel.setVisible(true);
			paginationFocPanel1.setVisible(true);
			backArrowButton.setVisible(false);
			monitorProgress.setVisible(false);
			hideToggleButtons.setVisible(false);
			monitorSummary.setVisible(false);
			mainContainer.setVisible(true);
			frameUrl.getElement().getStyle().clearWidth();
			frameUrl.getElement().getStyle().clearHeight();
			frameDiv.setVisible(false);
			monitorProgress.setVisible(false);
			assignmentsDirectionsLabel.setVisible(false);
			getstarteddiv.removeStyleName("btnContainerClasswithBG");
			getstarteddiv.setVisible(true);
			String order=AppClientFactory.getPlaceManager().getRequestParameter("order",null);
			if(classpageItemsList!=null&&classpageItemsList.size()>0){
				assignmentsContainerPanel.clear();
				for(int itemIndex=0;itemIndex<classpageItemsList.size();itemIndex++){
					ClasspageItemDo classpageItemDo=classpageItemsList.get(itemIndex);
					assignmentTabView = showClasspageItem(classpageItemDo,(itemIndex+1));
					this.totalHitCount=classpageItemDo.getTotalHitCount();
					assignmentsContainerPanel.add(assignmentTabView);
				}
				setPagination();
			}else if(classpageItemsList!=null&&order!=null&&(order.equals("earliest")||order.equals("latest"))&&assignmentsCount>0){
				paginationFocPanel.clear();
				paginationFocPanel1.clear();
				assignmentsContainerPanel.clear();
				Label label=new Label("It looks like none of the assignments for this class have due dates.");
				label.setStyleName("errorMessage");
				assignmentsContainerPanel.add(label);
			}else{
				panelAssignmentPath.setVisible(false);
				headerAssignments.setVisible(false);
				panelProgressContainer.setVisible(false);
				assignmentsDirectionsLabel.setVisible(true);
				getstarteddiv.addStyleName("btnContainerClasswithBG");
				getstarteddiv.setVisible(false);
				noAssignmentsMessagePanel.setVisible(true);
				assignmentsTab.setText(i18n.GL1623()+"("+0+")");
			}
			getClassListContainer().setVisible(false);
		}
	}

	/**
	 *
	 * @function showClasspageItem
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param classpageItemDo
	 * @parm(s) : @param sequenceNum
	 * @parm(s) : @return
	 *
	 * @return : CollectionsView
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public CollectionsView showClasspageItem(ClasspageItemDo classpageItemDo,int sequenceNum){
		CollectionsView assignmentCollectionView = new CollectionsView(classpageItemDo,sequenceNum){
			public void resetPagination(){
			}
			@Override
			public void updateCollectionsView(){
				String pageNum=AppClientFactory.getPlaceManager().getRequestParameter("pageNum", null);
				int pageNumber=1;
				if(pageNum!=null){
					try{
						pageNumber=Integer.parseInt(pageNum);
						if(assignmentsContainerPanel.getWidgetCount()<1){
							if(pageNumber>1){
								pageNumber=pageNumber-1;
							}
						}

					}catch(Exception e){
						AppClientFactory.printSevereLogger("EditClasspageView : showClasspageItem :"+e.getMessage());
					}
				}
				showCollectionsAfterDeletingCollection(pageNumber);
			}
		};
		return assignmentCollectionView;
	}
	/**
	 *
	 * @function showCollectionsAfterDeletingCollection
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param pageNumber
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void showCollectionsAfterDeletingCollection(int pageNumber){
		Map<String,String> params = new HashMap<String,String>();
		String classpageid=AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID, null);
		String order=AppClientFactory.getPlaceManager().getRequestParameter("order", null);
		params.put("order", order);
		params.put(UrlNavigationTokens.CLASSPAGEID, classpageid);
		params.put("pageNum", pageNumber+"");
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.EDIT_CLASSPAGE, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	/**
	 *
	 * @function restingPagination
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
	public void restingPagination(){
		setPagination();
		if((pageNumber*limit)<totalHitCount){
			assignmentsContainerPanel.add(setLoadingPanel());
			getUiHandlers().getNextClasspageItems(((pageNumber*limit)-1),1);
		}else{
			totalHitCount--;
			setPagination();
			if(totalHitCount==0){
				panelAssignmentPath.setVisible(false);
				headerAssignments.setVisible(false);
				panelProgressContainer.setVisible(false);
				assignmentsDirectionsLabel.setVisible(true);
				getstarteddiv.addStyleName("btnContainerClasswithBG");
				getstarteddiv.setVisible(false);
				noAssignmentsMessagePanel.setVisible(true);
				assignmentsTab.setText(i18n.GL1623()+"("+0+")");
			}
		}
	}
	/**
	 *
	 */
	public void setClasspageItemOnTop(ClasspageItemDo classpageItemDo){
		assignmentTabView = showClasspageItem(classpageItemDo,1);                             //TODO refresh the sequence....
		totalHitCount++;
		assignmentsContainerPanel.insert(assignmentTabView,0);
		panelAssignmentPath.setVisible(true);
		headerAssignments.setVisible(true);
		panelProgressContainer.setVisible(true);
		assignmentsDirectionsLabel.setVisible(false);
		getstarteddiv.removeStyleName("btnContainerClasswithBG");
		getstarteddiv.setVisible(true);
		noAssignmentsMessagePanel.setVisible(false);
		if(assignmentsContainerPanel.getWidgetCount()>limit){
			assignmentsContainerPanel.remove(assignmentsContainerPanel.getWidgetCount()-1);
		}
		setPagination();

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
		Label seeMoreLabel=new Label(i18n.GL0508());
		seeMoreLabel.setStyleName("paginationPanel");
		int totalPages = (this.totalHitCount / 5)
				+ ((this.totalHitCount % 5) > 0 ? 1 : 0);
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
	 * @fileName : EditClasspageView.java
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
			assignmentsContainerPanel.add(setLoadingPanel());
			getUiHandlers().getNextClasspageItems(((pageNumber-1)*limit),limit);
		}
	}
	/**
	 *
	 */
	public void resetEditClasspageView(){
		collectionTitleUc.setText("");
		collectionTitleUc.getElement().removeAttribute("title");
		collectionTitleUc.getElement().removeAttribute("alt");
		classCodeTextBox.getElement().removeAttribute("title");
		classCodeTextBox.getElement().removeAttribute("alt");
		imgClasspageImage.setUrl("/d");
		imgClasspageImage.getElement().removeAttribute("title");
		imgClasspageImage.getElement().removeAttribute("alt");
		paginationFocPanel.clear();
		paginationFocPanel1.clear();
		assignmentsContainerPanel.clear();
		assignmentsContainerPanel.add(setLoadingPanel());
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
		if(assignmentsContainerPanel.getWidgetCount()>0){
			Widget loadingPanel=assignmentsContainerPanel.getWidget(assignmentsContainerPanel.getWidgetCount()-1);
			if(loadingPanel!=null&&loadingPanel instanceof Label){
				loadingPanel.removeFromParent();
			}
		}
	}
	/* Custom methods */
	@Override
	public void setData(CollectionDo collectionDo) {
		try{
			Window.enableScrolling(true);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
			if (collectionDo != null) {

				AppClientFactory.fireEvent(new SetSelectedClasspageListEvent(collectionDo.getGooruOid()));
				panelAssignmentPath.setVisible(true);
				headerAssignments.setVisible(true);
				panelProgressContainer.setVisible(true);
				assignmentsDirectionsLabel.setVisible(false);
				getstarteddiv.removeStyleName("btnContainerClasswithBG");
				getstarteddiv.setVisible(true);
				noAssignmentsMessagePanel.setVisible(false);
				/*droplistContianer.setVisible(true);*/
				collectionTitleUc.setText(collectionDo.getTitle() !=null ? collectionDo.getTitle() : "" );
				collectionTitleUc.getElement().setAttribute("alt",collectionDo.getTitle() !=null ? collectionDo.getTitle() : "" );
				collectionTitleUc.getElement().setAttribute("title",collectionDo.getTitle() !=null ? collectionDo.getTitle() : "" );
				imgClasspageImage.setAltText(collectionDo.getTitle());
				imgClasspageImage.setTitle(collectionDo.getTitle());
				//collectionTitleUc.setText(collectionDo.getTitle());
				paginationFocPanel.clear();
				paginationFocPanel1.clear();
				getUiHandlers().getAssignmentsByClasspageById(classpageId,
						pageSize + "", pageNum + "");
				getUiHandlers().generateShareLink(classpageId);
				imgClasspageImage.setUrl(collectionDo.getThumbnails() != null ? collectionDo.getThumbnails().getUrl() : "");

		    }
		}
         catch(Exception e){
        	 AppClientFactory.printSevereLogger("EditclasspageView : setData :"+e.getMessage());
         }
	}

	@Override
	public void setAssignmentData(AssignmentsSearchDo assignmentsSearchDo,
			boolean isExpandable) {
		insertAssignment(assignmentsSearchDo, true, isExpandable);
	}

	@Override
	public void getClasspageById(String classpageId, String pageSize,
			String pageNum, String pos) {
		this.pageNum = Integer.parseInt(pageNum);
		this.pageSize = Integer.parseInt(pageSize);
		this.pos = Integer.parseInt(pos);
		getUiHandlers().getClasspageById(classpageId);
	}
	/**
	 *
	 * @function insertAssignment
	 *
	 * @created_date : Aug 17, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param assignmentsSearchDo
	 * @parm(s) : @param isNew
	 * @parm(s) : @param isExpandable
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void insertAssignment(AssignmentsSearchDo assignmentsSearchDo,
			boolean isNew, boolean isExpandable) {
		panelAssignmentPath.setVisible(true);
		headerAssignments.setVisible(true);
		panelProgressContainer.setVisible(true);
		assignmentsDirectionsLabel.setVisible(false);
		getstarteddiv.removeStyleName("btnContainerClasswithBG");
		getstarteddiv.setVisible(true);
		noAssignmentsMessagePanel.setVisible(false);
		assignmentTabView = new CollectionsView();
		assignmentsContainerPanel.add(assignmentTabView);
	}

	/**
	 * Set shorten url of the collection view url
	 *
	 * @param shortenUrl
	 *            to be set in shorten url UI field
	 */
	@Override
	public void setShareUrl(Map<String, String> shortenUrl) {
		if (shortenUrl != null && shortenUrl.containsKey(SHORTEN_URL)) {
		}
	}

	/* Setter and getters */
	/**
	 * This method is to get the collectionItemList
	 */
	public List<CollectionItemDo> getCollectionItemList() {
		return collectionItemList;
	}

	/**
	 * This method is to set the collectionItemList
	 */
	public void setCollectionItemList(List<CollectionItemDo> collectionItemList) {
		this.collectionItemList = collectionItemList;
	}

	/**
	 * This method is to get the classpageId
	 */
	public String getClasspageId() {
		return classpageId;
	}

	/**
	 * This method is to set the classpageId
	 */
	public void setClasspageId(String classpageId) {
		this.classpageId = classpageId;
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() instanceof PaginationButtonUc) {

			int pagenumber = ((PaginationButtonUc) event.getSource()).getPage();

			int pageNumVal = (pagenumber - 1) * 5;

			pageNumber = pagenumber;
			setPagination();

			assignmentsContainerPanel.clear();
			assignmentsContainerPanel.add(setLoadingPanel());

			offsetProgress = 0;
			limitProgress = 20;
			Map<String,String> params = new HashMap<String,String>();
			String classpageid=AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID, null);
			String pageNum=AppClientFactory.getPlaceManager().getRequestParameter("pageNum", null);
			String order=AppClientFactory.getPlaceManager().getRequestParameter("order", null);
			params.put("order", order);
			params.put(UrlNavigationTokens.CLASSPAGEID, classpageid);
			params.put("pageNum", pagenumber+"");
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.EDIT_CLASSPAGE, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
		} else {
		}
	}

	/**
	 *
	 */
	public void onDeleteAssignment(boolean isPostDeleteAssignment) {
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		restingPagination();
		Map<String, String> params = new HashMap<String, String>();
		params.put(UrlNavigationTokens.CLASSPAGEID,classpageDo.getClasspageId());
		params.put("pageSize", pageSize + "");
		if(AppClientFactory.getPlaceManager().getRequestParameter("pageNum")!=null)
		{
			params.put("pageNum", AppClientFactory.getPlaceManager().getRequestParameter("pageNum"));
		}
		AppClientFactory.getPlaceManager().revealPlace(
			PlaceTokens.EDIT_CLASSPAGE, params, true);
	}
/**
 * This method is used to get the assignment by classpageId
 */
	@Override
	public void getAssignemntsByClasspageId(String classpageId,
			String pageSize, String pageNum) {
		getUiHandlers().getAssignmentsByClasspageById(classpageId, pageSize,
				pageNum);
	}

	@Override
	public void listAssignments(AssignmentsListDo result) {
		isClicked=true;
		if (result.getSearchResults().size() > 0) {
			for (int i = 0; i < result.getSearchResults().size(); i++) {
				boolean isDisplay = i == 0 ? true : false;
				insertAssignment(result.getSearchResults().get(i), false,
						isDisplay);
			}

			paginationFocPanel.clear();
			paginationFocPanel1.clear();
			assignmentCount = (result.getTotalHitCount() / pageSize)
					+ ((result.getTotalHitCount() % pageSize) > 0 ? 1 : 0);

			if (assignmentCount > 1) {
				if (pos > 1) {
					paginationFocPanel.add(new PaginationButtonUc(pos - 1,
							PREVIOUS, this));
					paginationFocPanel1.add(new PaginationButtonUc(pos - 1,
							PREVIOUS, this));

				}
				int page = pos < 5 ? 1 : pos - 5;
				for (int count = 0; count < 5 && page <= assignmentCount; page++, ++count) {
					paginationFocPanel.add(new PaginationButtonUc(page,
							page == pos, this));
					paginationFocPanel1.add(new PaginationButtonUc(page,
							page == pos, this));
				}
				if (pos < assignmentCount) {
					paginationFocPanel.add(new PaginationButtonUc(pos + 1,
							NEXT, this));
					paginationFocPanel1.add(new PaginationButtonUc(pos + 1,
							NEXT, this));
				}
			}
		} else if(result.getTotalHitCount()==0) {
			panelAssignmentProgress.clear();
			panelAssignmentPath.setVisible(false);
			headerAssignments.setVisible(false);
			panelProgressContainer.setVisible(false);
			assignmentsDirectionsLabel.setVisible(true);
			getstarteddiv.addStyleName("btnContainerClasswithBG");
			getstarteddiv.setVisible(false);
			noAssignmentsMessagePanel.setVisible(true);
			assignmentsTab.setText(i18n.GL1623()+"("+0+")");
		}
		else{
			panelAssignmentProgress.clear();
			panelAssignmentPath.setVisible(true);
			headerAssignments.setVisible(true);
			panelProgressContainer.setVisible(true);
			assignmentsDirectionsLabel.setVisible(false);
			getstarteddiv.removeStyleName("btnContainerClasswithBG");
			getstarteddiv.setVisible(true);
			noAssignmentsMessagePanel.setVisible(false);
		}

	}


	@Override
	public void clearPanel() {
		assignmentsTab.addStyleName("selected");

		classListTab.getElement().setClassName("");

		reportsTab.getElement().setClassName("");

		newAssignmentAndMsgPanel.setVisible(true);
		assignmentsTabContainerPanel.setVisible(true);
		assignmentsTab.setEnabled(false);
		getClassListContainer().setVisible(false);
	}
/**
 * This method is used to update the title
 */
	@Override
	public void onPostClassPageUpdate() {
		collectionTitleUc.cancel();
		collectionTitleUc.getElement().getStyle().clearColor();
		panelUpdateActionContols.getElement().getStyle().setDisplay(Display.NONE);
		titleAlertMessageLbl.setText(i18n.GL0143());
		titleAlertMessageLbl.getElement().setAttribute("alt",i18n.GL0143());
		titleAlertMessageLbl.getElement().setAttribute("title",i18n.GL0143());
		titleAlertMessageLbl.setVisible(false);
		titleAlertMessageLbl.removeStyleName("titleAlertMessageActive");
		final CollectionDo classpage = null;
		AppClientFactory.fireEvent(new RefreshClasspageResourceItemListEvent(
				classpage, RefreshType.UPDATE));

	}
	/**
	 *
	 * @function frameAnalyticsUrl
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private static String frameAnalyticsUrl() {
		String classpageId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		String analyticsId = AppClientFactory.getPlaceManager().getRequestParameter("analyticsId");
		String monitorId = AppClientFactory.getPlaceManager().getRequestParameter("monitorid");
		if(analyticsId == null)
		{
			analyticsId = monitorId;
		}
		else if(monitorId == null)
		{
			monitorId = analyticsId;
		}

			String urlVal = StringUtil.generateMessage(AppClientFactory.getLoggedInUser().getSettings().getAnalyticsEndPoint()+DataInsightsUrlTokens.CLASS_COLLECTION_SUMMARY_DATA,classpageId,analyticsId,AppClientFactory.getLoginSessionToken());

			urlVal = urlVal+"&"+Math.random();

		return urlVal;
	}

	/**
	 *
	 * @function frameReportsUrl
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private static String frameReportsUrl() {
		String classpageId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);

			String urlVal = StringUtil.generateMessage(AppClientFactory.getLoggedInUser().getSettings().getAnalyticsEndPoint()+DataInsightsUrlTokens.CLASS_REPORTS,classpageId,AppClientFactory.getLoggedInUser().getGooruUId(),AppClientFactory.getLoginSessionToken());

			urlVal = urlVal+"&"+Math.random();

		return urlVal;
	}
	/**
	 *
	 * @function frameAnalyticsUrlForMonitor
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private static String frameAnalyticsUrlForMonitor() {

		String classpageId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		String analyticsId = AppClientFactory.getPlaceManager().getRequestParameter("analyticsId");
		String monitorId = AppClientFactory.getPlaceManager().getRequestParameter("monitorid");
		if(analyticsId == null)
		{
			analyticsId = monitorId;
		}
		else if(monitorId == null)
		{
			monitorId = analyticsId;
		}

		String urlVal = StringUtil.generateMessage(AppClientFactory.getLoggedInUser().getSettings().getAnalyticsEndPoint()+DataInsightsUrlTokens.CLASS_COLLECTION_MONITOR_DATA,
					classpageId,analyticsId,AppClientFactory.getLoginSessionToken());

		urlVal = urlVal+"&"+Math.random();


		return urlVal;
	}
	/**
	 * This handler is used to update and save title
	 * @param event instance of ClickEvent
	 */
	@UiHandler("btnClasspageSave")
	public void clickedOneditSelfCollectionSaveButton(ClickEvent event) {
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("text", collectionTitleUc.getValue());
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean value) {
				boolean isHavingBadWords = value;
				if (isHavingBadWords){
					collectionTitleUc.getElement().getStyle().setBorderColor("orange");
				}else{
					collectionTitleUc.switchToLabel();
					collectionTitleUc.getElement().getStyle().clearBackgroundColor();
					collectionTitleUc.getElement().getStyle().setBorderColor("#ccc");
				}
			}
		});
	}
/**
 * This handler is used to cancel the title update
 * @param event instance of ClickEvent
 */
	@UiHandler("btnClasspageCancel")
	public void clickedOnclassPageSaveButtonCancel(ClickEvent event) {
		isEditing = false;
		titleAlertMessageLbl.setVisible(false);
		titleAlertMessageLbl.setText(i18n.GL0143());
		titleAlertMessageLbl.getElement().setAttribute("alt",i18n.GL0143());
		titleAlertMessageLbl.getElement().setAttribute("title",i18n.GL0143());
		titleAlertMessageLbl.addStyleName("titleAlertMessageDeActive");
		titleAlertMessageLbl.removeStyleName("titleAlertMessageActive");
		panelUpdateActionContols.getElement().getStyle().setDisplay(Display.NONE);
		collectionTitleUc.getElement().getStyle().clearColor();
		collectionTitleUc.cancel();
	}

	@Override
	public void setUploadedImageToClassPage(String url) {
		MixpanelUtil.SuccessfullyAddtheImageFromClasspage();
		imgClasspageImage.setUrl(url+"?p="+Math.random());
	}

	@Override
	public void closeAllOpenedPopUp() {
		if(deleteConfirmVc!=null){
			deleteConfirmVc.hide();
		}
	}
	/**
	 *
	 * @fileName : EditClasspageView.java
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
	public class AssignmentsTabClicked implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			assignmentsTab.addStyleName("selected");
			String tab=AppClientFactory.getPlaceManager().getRequestParameter("tab", null);
			if(tab!=null){
				if(classpageItemsList!=null&&classpageItemsList.size()>0){
					assignmentsDirectionsLabel.setVisible(false);
					getstarteddiv.removeStyleName("btnContainerClasswithBG");
					getstarteddiv.setVisible(true);
					panelAssignmentPath.setVisible(true);
					headerAssignments.setVisible(true);
					panelProgressContainer.setVisible(true);
				}
				else{
					assignmentsDirectionsLabel.setVisible(true);
					getstarteddiv.addStyleName("btnContainerClasswithBG");
					getstarteddiv.setVisible(false);
					panelAssignmentPath.setVisible(false);
					headerAssignments.setVisible(false);
					panelProgressContainer.setVisible(false);
				}
			}



			classListTab.getElement().setClassName("");

			reportsTab.getElement().setClassName("");

			backArrowButton.setVisible(true);
			monitorProgress.setVisible(true);
			monitorSummary.setVisible(true);
			hideToggleButtons.setVisible(true);
			frameDiv.setVisible(false);

			paginationFocPanel.setVisible(true);
			paginationFocPanel1.setVisible(true);

			newAssignmentAndMsgPanel.setVisible(true);
			assignmentsTabContainerPanel.setVisible(true);
			assignmentsTab.setEnabled(false);
			getClassListContainer().setVisible(false);
			if(tab!=null){
				Map<String,String> params = new HashMap<String,String>();
				String pageSize=AppClientFactory.getPlaceManager().getRequestParameter("pageSize", null);
				String classpageid=AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID, null);
				String pageNum=AppClientFactory.getPlaceManager().getRequestParameter("pageNum", null);
				String pos=AppClientFactory.getPlaceManager().getRequestParameter("pos", null);
				params.put("pageSize", pageSize);
				params.put(UrlNavigationTokens.CLASSPAGEID, classpageid);
				params.put("pageNum", pageNum);
				params.put("pos", pos);
				PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.EDIT_CLASSPAGE, params);
				if(refresh){
					AppClientFactory.getPlaceManager().revealPlace(true, placeRequest, true);
				}else{
					AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
				}
			}
		}
	}
	/**
	 *
	 * @fileName : EditClasspageView.java
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
	public class reportsTabClicked implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			assignmentsDirectionsLabel.setVisible(false);
			getstarteddiv.removeStyleName("btnContainerClasswithBG");
			getstarteddiv.setVisible(true);
			reportsTab.addStyleName("selected");
			classListTab.getElement().setClassName("");
			assignmentsTab.getElement().setClassName("");
			refresh=false;
			newAssignmentAndMsgPanel.setVisible(false);
			backArrowButton.setVisible(false);
			monitorProgress.setVisible(false);
			monitorSummary.setVisible(false);
			hideToggleButtons.setVisible(false);
			assignmentsTabContainerPanel.setVisible(false);
			assignmentsTab.setEnabled(true);
			getClassListContainer().setVisible(false);
			panelAssignmentPath.setVisible(false);
			headerAssignments.setVisible(false);
			panelProgressContainer.setVisible(false);
			paginationFocPanel.setVisible(false);
			paginationFocPanel1.setVisible(false);
			frameDiv.setVisible(true);
			frameUrl.getElement().getStyle().setWidth(1000, Unit.PX);
			frameUrl.getElement().getStyle().setHeight(300, Unit.PX);

			Map<String,String> params = new HashMap<String,String>();
			String pageSize=AppClientFactory.getPlaceManager().getRequestParameter("pageSize", null);
			String classpageid=AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID, null);
			String pageNum=AppClientFactory.getPlaceManager().getRequestParameter("pageNum", null);
			String pos=AppClientFactory.getPlaceManager().getRequestParameter("pos", null);
			params.put("pageSize", pageSize);
			params.put(UrlNavigationTokens.CLASSPAGEID, classpageid);
			params.put("pageNum", pageNum);
			params.put("pos", pos);
			params.put("tab", "reports");
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.EDIT_CLASSPAGE, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
			if(classlistPresenter!=null){
                classlistPresenter.getView().clearDataAndErrorMessages();
        }
		}
	}

	@Override
	public FlowPanel getClassListContainer() {
		return classListContainer;
	}
	/**
	 *
	 * @function setAnalyticsData
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
	public static void setAnalyticsData()
	{

		mainContainer.setVisible(false);
		backArrowButton.setVisible(true);
		monitorProgress.setVisible(true);
		monitorSummary.setVisible(true);
		hideToggleButtons.setVisible(true);
		frameDiv.setVisible(true);
		frameUrl.getElement().getStyle().setWidth(1000, Unit.PX);
		frameUrl.getElement().getStyle().setHeight(300, Unit.PX);
		monitorProgress.setVisible(true);
	}
	/**
	 *
	 * @function setAnalyticsMonitoringData
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
	public static void setAnalyticsMonitoringData()
	{
		mainContainer.setVisible(false);
		backArrowButton.setVisible(true);
		monitorProgress.setVisible(true);
		monitorSummary.setVisible(true);
		hideToggleButtons.setVisible(true);
		frameDiv.setVisible(true);
		frameUrl.getElement().getStyle().setWidth(1000, Unit.PX);
		frameUrl.getElement().getStyle().setHeight(300, Unit.PX);
		monitorProgress.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpages.edit.IsEditClasspageView#getCollectionTitleUc()
	 */
	@Override
	public AssignmentEditLabelUc getCollectionTitleUc() {
		return collectionTitleUc;
	}
	@Override
	public void displayAssignmentPath(ArrayList<ClasspageItemDo> classpageProcess){
		boolean isLast = false;



		if (offsetProgress <= 0){
			lblPrevious.setVisible(false);
		}else{
			lblPrevious.setVisible(true);
		}
		if (classpageProcess.size() > 0){

			assignmentsTab.setText(i18n.GL1623()+"("+classpageProcess.get(0).getTotalHitCount()+")");

			panelAssignmentProgress.clear();
			//hide/show the next and previous buttons
			if (classpageProcess.get(0).getTotalHitCount() > limitProgress && classpageProcess.size() == limitProgress){
				lblNext.setVisible(true);
				isLast = false;
			}else{
				lblNext.setVisible(false);
				isLast = true;
			}
		}
		//display the assignments progress (DOTS)

		for (int i=0; i<classpageProcess.size(); i++){
			panelAssignmentProgress.add(new AssignmentProgressVc(isLast,
					classpageProcess.get(i), classpageProcess.get(i).getSequenceNumber(), classpageProcess.get(0).getTotalHitCount()));
		}
	}
	/**
	 *
	 * @fileName : EditClasspageView.java
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
	public class MouseOverShowClassCodeToolTip implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanelNew.clear();
			toolTipPopupPanelNew.setWidget(new GlobalToolTip(i18n.GL1869()));
			toolTipPopupPanelNew.setStyleName("");
			toolTipPopupPanelNew.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 14, event.getRelativeElement().getAbsoluteTop());
			toolTipPopupPanelNew.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanelNew.show();
		}

	}
	/**
	 *
	 * @fileName : EditClasspageView.java
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
	public class MouseOutHideToolTip implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanelNew.hide();
		}
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
		sortingOptionsList.add(i18n.GL1948());
		sortingOptionsList.add(i18n.GL1950());
		sortingOptionsList.add(i18n.GL1949());//previously it was descending order
		sortingOptionsList.add(i18n.GL1994());//previous number was 1581
	}
	@Override
	public void setSortingOrderInDropdown(String sortingOrder) {
	}

	@Override
	public void hideNoAssignmentsMessagePanel(){
		noAssignmentsMessagePanel.setVisible(false);
	}
	/**
	 *
	 * @fileName : EditClasspageView.java
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
	public class addAssignmentHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			MixpanelUtil.Click_Add_NewAssignment();
			Window.enableScrolling(false);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
			getUiHandlers().addAssignmentsContainerPopup(getClasspageId());

		}
	}
	@Override
	public HTMLPanel getFrameContainer(){
		return frameContainer;
	}
}

