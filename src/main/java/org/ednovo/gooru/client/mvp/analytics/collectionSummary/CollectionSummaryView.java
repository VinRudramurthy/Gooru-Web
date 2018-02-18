package org.ednovo.gooru.client.mvp.analytics.collectionSummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryUsersDataDo;
import org.ednovo.gooru.application.shared.model.analytics.PrintUserDataDO;
import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.client.uc.AppMultiWordSuggestOracle;
import org.ednovo.gooru.client.uc.AppSuggestBox;
import org.ednovo.gooru.client.uc.tooltip.ToolTip;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



public class CollectionSummaryView  extends BaseViewWithHandlers<CollectionSummaryUiHandlers> implements  IsCollectionSummaryView,ClientConstants {

	private static CollectionSummaryViewUiBinder uiBinder = GWT
			.create(CollectionSummaryViewUiBinder.class);

	interface CollectionSummaryViewUiBinder extends
			UiBinder<Widget, CollectionSummaryView> {
	}

	@UiField ListBox sessionsDropDown;
	@UiField Image exportImage,sessionsTooltip,imgQuestionMark;
	@UiField InlineLabel lastModifiedTime;
	@UiField HTMLPanel collectionSummaryDetails,sessionspnl,loadingImageLabel1;
	@UiField VerticalPanel pnlSummary;
	@UiField Frame downloadFile;
	@UiField Label subText,errorMessage,arrowLbl;

	@UiField(provided = true)
	AppSuggestBox studentSgstBox;

	Map<String, String> sessionData=new HashMap<String, String>();
	ToolTip toolTip;
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	String collectionId=null,pathwayId=null,classpageId=null;
	CollectionSummaryWidget collectionSummaryWidget=new CollectionSummaryWidget();
	PrintUserDataDO printUserDataDO=new PrintUserDataDO();
	List<String> allStudentsList = new ArrayList<String>();
	private Map<String, String> studentsUIdMap = new HashMap<String, String>();

	private AppMultiWordSuggestOracle studentSuggestOracle;

	private String previousText;


	/**
	 * Constructor
	 */
	public CollectionSummaryView() {
		initializeAutoSuggestedBox();
		setWidget(uiBinder.createAndBindUi(this));
		setData();
		setStaticData();
		downloadFile.setVisible(false);
		errorMessage.setVisible(false);
		studentSgstBox.addDomHandler(hanlder, MouseDownEvent.getType());
	}

	MouseDownHandler hanlder=new MouseDownHandler() {
		@Override
		public void onMouseDown(MouseDownEvent event) {
			displaySuggestedStudents();
		}
	};

	/**
	 * Initializes the suggestion box.
	 */
	private void initializeAutoSuggestedBox() {

		studentSuggestOracle= new AppMultiWordSuggestOracle(true);
		studentSgstBox=new AppSuggestBox(studentSuggestOracle) {

			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler) {
				return null;
			}

			@Override
			public void keyAction(String text, KeyUpEvent event) {

				if (text != null && text.trim().length() > 0) {
					search(text.trim());
					studentSgstBox.showSuggestionList();
				}
			}
		};
		studentSgstBox.getElement().getStyle().setFontSize(12, Unit.PX);
		studentSgstBox.getTextBox().getElement().setAttribute("placeholder", ALL_STUDENTS);
		studentSgstBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				previousText = studentSgstBox.getText().trim();
				showReport(studentSgstBox.getText().trim(),studentsUIdMap.get(studentSgstBox.getText().trim()));
			}
		});

		BlurHandler blurHandler=new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				studentSgstBox.setText(!"".equals(previousText)&& previousText!=null?previousText:ALL_STUDENTS);
			}
		};
		studentSgstBox.addDomHandler(blurHandler, BlurEvent.getType());
	}



	/**
	 * This method is used to set static data.
	 */
	void setStaticData(){
		StringUtil.setAttributes(collectionSummaryDetails.getElement(), "pnlCollectionSummaryDetails", null, null);
		StringUtil.setAttributes(sessionspnl.getElement(), "pnlSessionspnl", null, null);
		StringUtil.setAttributes(loadingImageLabel1.getElement(), "pnlLoadingImage", null, null);
		StringUtil.setAttributes(pnlSummary.getElement(), "pnlSummary", null, null);

		StringUtil.setAttributes(sessionsDropDown.getElement(), "ddlSessionsDropDown", null, null);
		StringUtil.setAttributes(exportImage.getElement(), "imgExportImage", i18n.GL3283(), i18n.GL3283());

		StringUtil.setAttributes(sessionsTooltip.getElement(), "imgSessionsTooltip", null, null);

		StringUtil.setAttributes(lastModifiedTime.getElement(), "lblLastModifiedTime", null, null);
		studentSgstBox.getElement().setAttribute("style", "box-sizing:content-box;width:19%;height:19px");
	}
	/**
	 * This method is used to set default data.
	 */
	void setData(){
		sessionspnl.setVisible(false);

		sessionsDropDown.addChangeHandler(new StudentsSessionsChangeHandler());

		sessionsTooltip.addMouseOverHandler(new QuestionMouseToolTip(ONE, sessionsTooltip));
		imgQuestionMark.addMouseOverHandler(new QuestionMouseToolTip(TWO, imgQuestionMark));

		sessionsTooltip.addMouseOutHandler(new QuestionMouseOutToolTip());
		imgQuestionMark.addMouseOutHandler(new QuestionMouseOutToolTip());
	}
	/**
	 * This inner class is used to handle the mouse over events.
	 */
	public class QuestionMouseToolTip implements MouseOverHandler{
		String fromString="";
		Image image;
		QuestionMouseToolTip(String fromString,Image image){
			this.fromString=fromString;
			this.image=image;
		}
		@Override
		public void onMouseOver(MouseOverEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {

					String setText="";
					if(ONE.equalsIgnoreCase(fromString)){
						setText=i18n.GL3091();
					}else if(TWO.equalsIgnoreCase(fromString)){
						setText=i18n.GL3088();
					}
					toolTip = new ToolTip(setText,"");
					toolTip.getTootltipContent().getElement().setAttribute("style", "width: 258px;");
					toolTip.getElement().getStyle().setBackgroundColor("transparent");
					toolTip.getElement().getStyle().setPosition(Position.ABSOLUTE);
					toolTip.setPopupPosition(image.getAbsoluteLeft()-(50+22), image.getAbsoluteTop()+22);
					toolTip.show();
				}
			});
		}
	}
	/**
	 * This class is used to hide the popup
	 */
	public class QuestionMouseOutToolTip implements MouseOutHandler{
		@Override
		public void onMouseOut(final MouseOutEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {

					EventTarget target = event.getRelatedTarget();
					  if (Element.is(target)) {
						  if (!toolTip.getElement().isOrHasChild(Element.as(target))){
							  toolTip.hide();
						  }
					  }

				}
			});
		}
	}

    public class StudentsSessionsChangeHandler implements ChangeHandler{
		@Override
		public void onChange(ChangeEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {

					int selectedSessionIndex=sessionsDropDown.getSelectedIndex();
//					int selectedStudentIndex=studentsListDropDown.getSelectedIndex();
					String classpageId=AppClientFactory.getPlaceManager().getRequestParameter("classpageid", null);
	                setSessionStartTime(selectedSessionIndex);
	                printUserDataDO.setUserName(studentSgstBox.getText());
	                printUserDataDO.setSession(sessionsDropDown.getItemText(selectedSessionIndex));
					getUiHandlers().setIndividualData(collectionId, classpageId, studentsUIdMap.get(studentSgstBox.getText()),sessionsDropDown.getValue(selectedSessionIndex),pathwayId,printUserDataDO);

				}
			});
		}
    }
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummary.IsCollectionSummaryView#setUsersData(java.util.ArrayList)
	 */
	@Override
	public void setUsersData(final ArrayList<CollectionSummaryUsersDataDo> result) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				errorMessage.setVisible(false);
				studentSuggestOracle.clear();
				studentsUIdMap.clear();
				allStudentsList.clear();
				allStudentsList.add(ALL_STUDENTS);
				studentSuggestOracle.add(ALL_STUDENTS);
				studentsUIdMap.put(ALL_STUDENTS, ALL_STUDENTS);
				studentSgstBox.setText(ALL_STUDENTS);
				for (CollectionSummaryUsersDataDo collectionSummaryUsersDataDo : result) {
					if(!StringUtil.isEmpty(collectionSummaryUsersDataDo.getUserName()) && !StringUtil.isEmpty(collectionSummaryUsersDataDo.getGooruUId())){
						allStudentsList.add(collectionSummaryUsersDataDo.getUserName());
						studentsUIdMap.put(collectionSummaryUsersDataDo.getUserName(), collectionSummaryUsersDataDo.getGooruUId());
					}
				}
				sessionspnl.setVisible(false);
				String tabReports=AppClientFactory.getPlaceManager().getRequestParameter("tab", null);
				if(REPORTS.equalsIgnoreCase(tabReports)){
					exportImage.setVisible(true);
					subText.setVisible(true);
				}else{
					exportImage.setVisible(false);
					subText.setVisible(false);
				}
				collectionSummaryWidget.getCollectionLastAccessPnl().setVisible(true);

			}
		} );
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummary.IsCollectionSummaryView#setCollectionMetaData(org.ednovo.gooru.shared.model.analytics.CollectionSummaryMetaDataDo, java.lang.String)
	 */
	@Override
	public void setCollectionMetaData(
			final CollectionSummaryMetaDataDo result,String pathwayId,String classpageId) {
		this.classpageId=classpageId;
		this.pathwayId=pathwayId;
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				if(!StringUtil.checkNull(result)){
					collectionId=result.getGooruOId();
					collectionSummaryWidget.setData(result);
					collectionSummaryDetails.add(collectionSummaryWidget);
				}
			}
		});

	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummary.IsCollectionSummaryView#setCollectionResourcesData(java.util.ArrayList)
	 */
	@Override
	public void setCollectionResourcesData(ArrayList<UserDataDo> result) {

	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummary.IsCollectionSummaryView#setUserSessionsData(java.util.ArrayList)
	 */
	@Override
	public void setUserSessionsData(final
			ArrayList<CollectionSummaryUsersDataDo> result) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				sessionsDropDown.clear();
				sessionData.clear();
				for (CollectionSummaryUsersDataDo collectionSummaryUsersDataDo : result) {
					sessionData.put(collectionSummaryUsersDataDo.getSessionId(), AnalyticsUtil.getSessionsCreatedTime(Long.toString(collectionSummaryUsersDataDo.getTimeStamp())));
					int day=collectionSummaryUsersDataDo.getFrequency();
					sessionsDropDown.addItem(day+AnalyticsUtil.getOrdinalSuffix(day)+" Session",collectionSummaryUsersDataDo.getSessionId());
				}
				setSessionStartTime(0);

			}
		});
	}
	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.ViewImpl#setInSlot(java.lang.Object, com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void setInSlot(Object slot, Widget content) {
		pnlSummary.clear();
		if (content != null) {
			 if(slot==CollectionSummaryPresenter.TEACHER_STUDENT_SLOT){
				 pnlSummary.setVisible(true);
				 pnlSummary.add(content);
			}else{
				pnlSummary.setVisible(false);
			}
		}else{
			pnlSummary.setVisible(false);
		}
	}
	/**
	 * This method is used to set session start time.
	 * @param selectedIndex
	 */
	public void setSessionStartTime(int selectedIndex) {
		if(sessionData.size()!=0){
			  lastModifiedTime.setText(sessionData.get(sessionsDropDown.getValue(selectedIndex)).toString());
			  printUserDataDO.setSessionStartTime(sessionData.get(sessionsDropDown.getValue(selectedIndex)).toString());
		}
	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummary.IsCollectionSummaryView#getLoadinImage()
	 */
	@Override
	public HTMLPanel getLoadinImage() {
		return loadingImageLabel1;
	}

	@UiHandler("exportImage")
	public void clickedOnExport(ClickEvent e){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				getUiHandlers().exportCollectionSummary(collectionId, classpageId, "", "", pathwayId, AnalyticsUtil.getTimeZone());

			}
		});
	}
	@Override
	public Frame getFrame() {
		return downloadFile;
	}
	@Override
	public void resetDataIfNoSessions() {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				lastModifiedTime.setText("");
				sessionspnl.setVisible(false);
				errorMessage.setVisible(true);
				collectionSummaryWidget.getCollectionLastAccessPnl().setVisible(true);

			}
		});
	}


	/**
	 * This method will return matched student name in the list.
	 *
	 * @param searchText {@link String}
	 */
	private void search(String searchText) {
		studentSuggestOracle.clear();
		for (String students : allStudentsList) {
			if(students.toUpperCase().startsWith(searchText.toUpperCase())){
				studentSuggestOracle.add(students);
			}
		}
	}

	/**
	 * Displays suggested student name based on the given text.
	 */
	private void displaySuggestedStudents() {
		if(ALL_STUDENTS.equals(studentSgstBox.getText())){
			studentSuggestOracle.clear();
			studentSuggestOracle.addAll(allStudentsList);
		}else{
			studentSuggestOracle.clear();
			studentSuggestOracle.add(!"".equals(previousText)&& previousText!=null?previousText:studentSgstBox.getText());
		}
		studentSgstBox.setPopupStyleName("suggestPopupContentStyle");
		studentSgstBox.showSuggestionList();
	}


	/**
	 * On selection of any student, this method is called in which shows the report for respective student.
	 * @param studentName {@link String}
	 * @param studentUid {@link String}
	 */
	public void showReport(String studentName,String studentUid){
		errorMessage.setVisible(false);
		String classpageId=AppClientFactory.getPlaceManager().getRequestParameter("classpageid", null);
		if(!"".equals(studentName)){
			if(ALL_STUDENTS.equals(studentName)){
				sessionspnl.setVisible(false);
				collectionSummaryWidget.getCollectionLastAccessPnl().setVisible(true);
				getUiHandlers().setTeacherData(collectionId,classpageId,pathwayId);
			}else{
				printUserDataDO.setUserName(studentName);
				int selectedSessionIndex=sessionsDropDown.getSelectedIndex();
				getUiHandlers().loadUserSessions(sessionsDropDown.getValue(selectedSessionIndex),collectionId, classpageId, studentUid,pathwayId,printUserDataDO);
				sessionspnl.setVisible(true);
				collectionSummaryWidget.getCollectionLastAccessPnl().setVisible(false);
			}
		}
	}

	@UiHandler("arrowLbl")
	public void onArrowClicked(ClickEvent clickEvent) {
		displaySuggestedStudents();
	}

}
