package org.ednovo.gooru.client.mvp.gshelf.taxonomy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.library.DomainStandardsDo;
import org.ednovo.gooru.application.shared.model.library.SubDomainStandardsDo;
import org.ednovo.gooru.application.shared.model.library.SubSubDomainStandardsDo;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class TaxonomyPopupView extends PopupViewWithUiHandlers<TaxonomyPopupUiHandlers> implements IsTaxonomyPopupView {
	
	private TaxonomyPopupViewUiBinder uiBinder = GWT.create(TaxonomyPopupViewUiBinder.class);
	
	@UiTemplate("TaxonomyPopupView.ui.xml")
	interface TaxonomyPopupViewUiBinder extends UiBinder<Widget, TaxonomyPopupView> {
	}
	
	static MessageProperties i18n = GWT.create(MessageProperties.class);
	
	private PopupPanel appPopUp;
	
	@UiField HTMLPanel taxonomyMainContainer;
	@UiField ScrollPanel scrollDiv,scrollDivDomain;
	
	@UiField Image closeBtn;
	
	@UiField Label errorMsgLbl;
	
	@UiField Button k12Btn,higherEduBtn, profLearningBtn,addTaxonomyBtn;
	
	@UiField UlPanel subjectUlContainer,courseUlContainer,domainUlContainer, standardsUlContainer,selectedUlContainer;
	
	LiPanel previousSelectedLiPanel = new LiPanel();
	
	LiPanel previousSelectedCourseLiPanel = new LiPanel();
	
	LiPanel previousSelectedDomainLiPanel = new LiPanel();
	
	LiPanel previousSelectedStdLiPanel = new LiPanel();
	
	List<Button> buttonsList = new ArrayList<Button>();
	
	List<LiPanelWithClose> liPanelWithCloseArray = new ArrayList<LiPanelWithClose>();
	
	List<LiPanelWithClose> removedLiPanelWithCloseArray = new ArrayList<LiPanelWithClose>();
	
	int coursePagination,domainPagination = 0;
	
	int coursePaginationSubjectId,domainPaginationCourseId = 0;
	
	private String viewType;
	
	private final static int LIMIT = 15;
	
	
	@Inject
	public TaxonomyPopupView(EventBus eventbus){
		super(eventbus);
		appPopUp=new PopupPanel();
		appPopUp.setGlassEnabled(true);
		appPopUp.add(uiBinder.createAndBindUi(this));
		setElementsId();
		
	}

	private void setElementsId() { 
		buttonsList.add(k12Btn);
		buttonsList.add(higherEduBtn);
		buttonsList.add(profLearningBtn);
		taxonomyMainContainer.getElement().setId("taxonomyStructureSelectionPopup");
		closeBtn.getElement().getStyle().setCursor(Cursor.POINTER);
		setButtonActiveStyle(k12Btn);
	}

	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	@Override
	public void reset() {
	}

	@Override
	public void onLoad() {
	}

	@Override
	public void onUnload() {
	}
	
	
	@Override
	public void setCurrentTypeView(String viewType) {
		this.viewType = viewType;
		selectedUlContainer.clear();
		clearAllContainers();
		setButtonActiveStyle(k12Btn);
	}
	
	private void clearAllContainers() {
		subjectUlContainer.clear();
		courseUlContainer.clear();
		domainUlContainer.clear();
		standardsUlContainer.clear();
		removedLiPanelWithCloseArray.clear();
		if(errorMsgLbl.isVisible()){
			errorMsgLbl.setVisible(false);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	@UiHandler("closeBtn")
	public void onColseButtonClicked(ClickEvent event){
		appPopUp.hide();
		
	}
	
	/**
	 * 
	 * @param event
	 */
	@UiHandler("k12Btn")
	public void onClickK12Btn(ClickEvent event){
		setButtonActiveStyle(k12Btn);
		clearAllContainers();
		getUiHandlers().populateK12TaxonomyData(1,"subject",0,20); 
	}
	
	
	/**
	 * 
	 * @param event
	 */
	@UiHandler("higherEduBtn")
	public void onClickHigherEduBtn(ClickEvent event){
		setButtonActiveStyle(higherEduBtn);
		clearAllContainers();
		getUiHandlers().getPopulateHigherEduData(2,"subject",0,20);
	}
	

	/**
	 * 
	 * @param event
	 */
	@UiHandler("addTaxonomyBtn")
	public void onClickAddTaxonomy(ClickEvent event){ 
		passTaxonomyData(); 
		appPopUp.hide();
	}
	
	
	private void passTaxonomyData() {
		liPanelWithCloseArray.clear();
		for(int i=0;i<selectedUlContainer.getWidgetCount();i++){
			liPanelWithCloseArray.add((LiPanelWithClose) selectedUlContainer.getWidget(i));
		}
		getUiHandlers().addTaxonomyData(liPanelWithCloseArray,removedLiPanelWithCloseArray);
		removedLiPanelWithCloseArray.clear();
	}

	/**
	 * 
	 * @param event
	 */
	@UiHandler("profLearningBtn")
	public void onClickProfLearningBtn(ClickEvent event){
		setButtonActiveStyle(profLearningBtn);
		clearAllContainers();
		getUiHandlers().populateProfLearningData(3,"subject",0,20);
	}
	
	
	/**
	 * 
	 * @param k12Btn2
	 */
	private void setButtonActiveStyle(Button activeButton) { 
		for(Button button:buttonsList){
			if(button==activeButton){
				button.addStyleName("active");
			}else{
				button.removeStyleName("active");
			}
		}
	}

	@Override
	public void addTaxonomySubjects(List<CourseSubjectDo> result) {
		subjectUlContainer.clear();
		if(result.size()>0){
			for(CourseSubjectDo courseSubjectDo:result){
				LiPanel liPanel=new LiPanel();
				Anchor title=new Anchor(courseSubjectDo.getName().trim());
				liPanel.setTitle(title.getText());
				title.addClickHandler(new OnClickSubjects(liPanel,courseSubjectDo.getSubjectId())); 
				liPanel.add(title);
				subjectUlContainer.add(liPanel);
			}
		}
		
		if(subjectUlContainer.getWidgetCount()>0){
			subjectUlContainer.getWidget(0).addStyleName("active");
			previousSelectedLiPanel = (LiPanel) subjectUlContainer.getWidget(0);
		}
	}
	
	

	@Override
	public void addTaxonomyCourses(List<CourseSubjectDo> taxonomyCourseList,Integer subjectId) {
		courseUlContainer.clear();
		coursePaginationSubjectId = subjectId;

		if(taxonomyCourseList.size()>0){
			for(CourseSubjectDo courseSubjectDo:taxonomyCourseList){
				LiPanel liPanel=new LiPanel();
				Anchor title=new Anchor(courseSubjectDo.getName());
				liPanel.setTitle(title.getText().trim());
				title.addClickHandler(new OnClickCourses(liPanel,courseSubjectDo.getCourseId(),title));
				liPanel.add(title);
				courseUlContainer.add(liPanel);
			}
			
		}
		if(courseUlContainer.getWidgetCount()>0){
			courseUlContainer.getWidget(0).addStyleName("active");
			previousSelectedCourseLiPanel = (LiPanel) courseUlContainer.getWidget(0);
		}
		
		if(taxonomyCourseList.size()>=20){
			coursePagination = 20;
			scrollDiv.addScrollHandler(new ScrollHandler() {
					
					@Override
					public void onScroll(ScrollEvent event) {						
						if(coursePagination<=60){
							getUiHandlers().getCoursespaginatedData(coursePaginationSubjectId,coursePagination);
						}
						coursePagination = coursePagination+20;
						
						
					}
			});
		}
	}
	
	@Override
	public void appendTaxonomyCourses(List<CourseSubjectDo> taxonomyCourseList,final Integer subjectId) {

		if(taxonomyCourseList.size()>0){
			for(CourseSubjectDo courseSubjectDo:taxonomyCourseList){
				LiPanel liPanel=new LiPanel();
				Anchor title=new Anchor(courseSubjectDo.getName());
				liPanel.setTitle(title.getText().trim());
				title.addClickHandler(new OnClickCourses(liPanel,courseSubjectDo.getCourseId(),title));
				liPanel.add(title);
				courseUlContainer.add(liPanel);
			}
			
		}
		if(courseUlContainer.getWidgetCount()>0){
			courseUlContainer.getWidget(0).addStyleName("active");
			previousSelectedCourseLiPanel = (LiPanel) courseUlContainer.getWidget(0);
		}
		

	}
	
	@Override
	public void addTaxonomyDomains(List<CourseSubjectDo> taxonomyDomainList,Integer courseId) {
		domainUlContainer.clear();
		domainPaginationCourseId = courseId;
		if(taxonomyDomainList.size()>0){
			for(CourseSubjectDo courseSubjectDo:taxonomyDomainList){
				LiPanel liPanel=new LiPanel();
				Anchor title=new Anchor(courseSubjectDo.getName());
				liPanel.setTitle(courseSubjectDo.getName());
				liPanel.setCodeId(courseSubjectDo.getSubdomainId());
				title.addClickHandler(new OnClickDomain(liPanel,courseSubjectDo.getSubdomainId(),title)); 
				liPanel.add(title);
				domainUlContainer.add(liPanel);
			}
			highlightSelectedWidgets();
		}
		if(domainUlContainer.getWidgetCount()>0){
			if(!"Unit".equalsIgnoreCase(viewType)){
				domainUlContainer.getWidget(0).addStyleName("active");
				previousSelectedDomainLiPanel = (LiPanel) domainUlContainer.getWidget(0);
			}
		}
		
		if(taxonomyDomainList.size()>=20){
			domainPagination = 20;
			scrollDivDomain.addScrollHandler(new ScrollHandler() {
					
					@Override
					public void onScroll(ScrollEvent event) {						
						if(domainPagination<=60)
						{
						getUiHandlers().getDomainspaginatedData(domainPaginationCourseId,domainPagination);
						}
						domainPagination = domainPagination+20;
						
						
					}
			});
		}
	}
	
	
	@Override
	public void appendTaxonomyDomains(List<CourseSubjectDo> taxonomyDomainList,Integer courseId) {
		
		
		if(taxonomyDomainList.size()>0){
			for(CourseSubjectDo courseSubjectDo:taxonomyDomainList){
				LiPanel liPanel=new LiPanel();
				Anchor title=new Anchor(courseSubjectDo.getName());
				liPanel.setTitle(courseSubjectDo.getName());
				liPanel.setCodeId(courseSubjectDo.getSubdomainId());
				title.addClickHandler(new OnClickDomain(liPanel,courseSubjectDo.getSubdomainId(),title)); 
				liPanel.add(title);
				domainUlContainer.add(liPanel);
			}
			highlightSelectedWidgets();
		}
		if(domainUlContainer.getWidgetCount()>0){
			if(!"Unit".equalsIgnoreCase(viewType)){
				domainUlContainer.getWidget(0).addStyleName("active");
				previousSelectedDomainLiPanel = (LiPanel) domainUlContainer.getWidget(0);
			}
		}
		

	}
	
	@Override
	public void addTaxonomyStandards(List<DomainStandardsDo> taxonomyStdList) {
		standardsUlContainer.clear();
		
		for(DomainStandardsDo domainStandardsDo:taxonomyStdList){
			LiPanel liPanel=new LiPanel();

			HTMLEventPanel htmlPanel = new HTMLEventPanel("");
			Anchor title=new Anchor(domainStandardsDo.getCode());
			
			Label lblStandardDesc=new Label(domainStandardsDo.getLabel());
			Label lblStandardcode=new Label(domainStandardsDo.getCode());


			liPanel.setCodeId(domainStandardsDo.getCodeId());
			if(!domainStandardsDo.getCode().contains("Math")){
				htmlPanel.add(lblStandardcode);
				htmlPanel.add(lblStandardDesc);
				htmlPanel.setStyleName("standardDiv");
				htmlPanel.addClickHandler(new OnClickStandards(liPanel,domainStandardsDo.getCodeId(),title));
			}else{
				htmlPanel.add(lblStandardDesc);	
				htmlPanel.setStyleName("standardDiv");
			}
			if(domainStandardsDo.getTypeId()!=null && !(domainStandardsDo.getCode().contains("ELA"))){
				if(domainStandardsDo.getTypeId().equals(1)){
				}
				else if(domainStandardsDo.getTypeId().equals(2)){
					htmlPanel.setStyleName("standardDivSub");	
				}
				else if(domainStandardsDo.getTypeId().equals(3)){
					htmlPanel.setStyleName("standardDivSubSub");
				}
				else{
					htmlPanel.setStyleName("standardDiv");
				}
			}
			else{
				htmlPanel.setStyleName("standardDiv");
			}
			liPanel.add(htmlPanel);
			standardsUlContainer.add(liPanel);
			displaysubTaxonomyStandards(domainStandardsDo.getNode());
		}
		highlightSelectedWidgets();
		/*if(standardsUlContainer.getWidgetCount()>0){
			standardsUlContainer.getWidget(0).addStyleName("active");
			previousSelectedStdLiPanel = (LiPanel) standardsUlContainer.getWidget(0);
		}*/
	}
	
	
	public void displaysubTaxonomyStandards(List<SubDomainStandardsDo> taxonomyStdList) {
		
		for(SubDomainStandardsDo subdomainStandardsDo:taxonomyStdList){
			LiPanel liPanel=new LiPanel();
			HTMLEventPanel htmlPanel = new HTMLEventPanel("");
			Anchor title=new Anchor(subdomainStandardsDo.getCode());
			Label lblStandardDesc = new Label();
			lblStandardDesc.setText(subdomainStandardsDo.getLabel());			
			Label lblStandardcode = new Label();
			lblStandardcode.setText(subdomainStandardsDo.getCode());
			htmlPanel.add(lblStandardcode);
			htmlPanel.add(lblStandardDesc);
			htmlPanel.setStyleName("standardDivSub");
			liPanel.setCodeId(subdomainStandardsDo.getCodeId());
			htmlPanel.addClickHandler(new OnClickStandards(liPanel,subdomainStandardsDo.getCodeId(),title)); 
			liPanel.add(htmlPanel);
			standardsUlContainer.add(liPanel);
			displaysubsubTaxonomyStandards(subdomainStandardsDo.getNode());
		}
		/*if(standardsUlContainer.getWidgetCount()>0){
			standardsUlContainer.getWidget(0).addStyleName("active");
			previousSelectedStdLiPanel = (LiPanel) standardsUlContainer.getWidget(0);
		}*/
	}
	
	public void displaysubsubTaxonomyStandards(List<SubSubDomainStandardsDo> taxonomyStdList) {
		for(SubSubDomainStandardsDo subsubdomainStandardsDo:taxonomyStdList){
			LiPanel liPanel=new LiPanel();
			HTMLEventPanel htmlPanel = new HTMLEventPanel("");
			Anchor title=new Anchor(subsubdomainStandardsDo.getCode());
			Label lblStandardDesc = new Label();
			lblStandardDesc.setText(subsubdomainStandardsDo.getLabel());			
			Label lblStandardcode = new Label();
			lblStandardcode.setText(subsubdomainStandardsDo.getCode());
			htmlPanel.add(lblStandardcode);
			htmlPanel.add(lblStandardDesc);
			htmlPanel.setStyleName("standardDivSubSub");
			liPanel.setCodeId(subsubdomainStandardsDo.getCodeId());
			htmlPanel.addClickHandler(new OnClickStandards(liPanel,subsubdomainStandardsDo.getCodeId(),title)); 
			liPanel.add(htmlPanel);
			standardsUlContainer.add(liPanel);
		}
		/*if(standardsUlContainer.getWidgetCount()>0){
			standardsUlContainer.getWidget(0).addStyleName("active");
			previousSelectedStdLiPanel = (LiPanel) standardsUlContainer.getWidget(0);
		}*/
	}

	
	
	
	
	/**
	 * Written functionality for subject click, which displays all subject related domains
	 * 
	 *
	 */
	public class OnClickSubjects implements ClickHandler{
		LiPanel liPanel;
		int subId;
		public OnClickSubjects(LiPanel liPanel,int subId) {
			this.liPanel = liPanel;
			this.subId = subId;
		}
		@Override
		public void onClick(ClickEvent event) {
			setSubCouDomainActiveStyle(liPanel,previousSelectedLiPanel,false);
			previousSelectedLiPanel = liPanel;
			courseUlContainer.clear();
			domainUlContainer.clear();
			standardsUlContainer.clear();
			getUiHandlers().getCoursesBasedOnSelectedSub(subId,"course",0,20);
		}
	}

	
	
	public class OnClickCourses implements ClickHandler{
		LiPanel liPanel;
		int courseId;
		Anchor title;
		public OnClickCourses(LiPanel liPanel,int courseId, Anchor title) {
			this.liPanel = liPanel;
			this.courseId = courseId;
			this.title = title;
		}
		@Override
		public void onClick(ClickEvent event) {
			if(isLimitReached()){
				errorMsgLbl.setVisible(true); 
			}else{
				setSubCouDomainActiveStyle(liPanel,previousSelectedCourseLiPanel,false);
				previousSelectedCourseLiPanel = liPanel;
				domainUlContainer.clear();
				standardsUlContainer.clear();
				getUiHandlers().getDomainsBasedOnSelectedCourse(courseId,"domain",0,20,title,courseId,liPanel,previousSelectedCourseLiPanel);
			}
		}
	}

	public class OnClickDomain implements ClickHandler{
		LiPanel liPanel;
		int subDomainId;
		Anchor title;
		public OnClickDomain(LiPanel liPanel,int subDomainId,Anchor title) {
			this.liPanel = liPanel;
			this.subDomainId = subDomainId;
			this.title = title;
		}
		@Override
		public void onClick(ClickEvent event) {
			if(isLimitReached()){
				errorMsgLbl.setVisible(true); 
			}else{
				if("Unit".equalsIgnoreCase(viewType)){
					setStandardsActiveStyle(liPanel,previousSelectedDomainLiPanel);
					final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(title.getText());
					liPanelWithClose.setId(subDomainId);
					liPanelWithClose.setDifferenceId(2);
					liPanelWithClose.setName(title.getText());
					liPanelWithClose.getCloseButton().addClickHandler(new RemoveLiPanelWithCloseBtn(liPanelWithClose));
					addOrRemoveContent(liPanel,previousSelectedDomainLiPanel,liPanelWithClose);
				}else{
					setSubCouDomainActiveStyle(liPanel,previousSelectedDomainLiPanel,false);
				}
				previousSelectedDomainLiPanel = liPanel;
				standardsUlContainer.clear();
				getUiHandlers().getStdBasedOnSelectedDomain(subDomainId,title,liPanel,previousSelectedDomainLiPanel);
			}
		}
	}
	

	public class OnClickStandards implements ClickHandler{
		LiPanel liPanel;
		int id;
		Anchor title;
		public OnClickStandards(LiPanel liPanel,int id,Anchor title) {
			this.liPanel = liPanel;
			this.id = id;
			this.title = title;
		}
		@Override
		public void onClick(ClickEvent event) {
			if(isLimitReached()){
				errorMsgLbl.setVisible(true); 
			}else{
				setStandardsActiveStyle(liPanel,previousSelectedStdLiPanel);
				final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(title.getText());
				liPanelWithClose.setId(id);
				liPanelWithClose.setName(title.getText());
				liPanelWithClose.setDifferenceId(3);
				liPanelWithClose.getCloseButton().addClickHandler(new RemoveLiPanelWithCloseBtn(liPanelWithClose));
				addOrRemoveContent(liPanel,previousSelectedDomainLiPanel,liPanelWithClose);
				previousSelectedStdLiPanel = liPanel;
			}
		}
	} 
	
	
	public void setStandardsActiveStyle(LiPanel selectedLiPanel,LiPanel previousSelectedStdLiPanel) {
		if(selectedLiPanel.getStyleName().contains("active")){ 
			selectedLiPanel.removeStyleName("active");
		}else{
			selectedLiPanel.addStyleName("active");
		}
	}
	

	private void setSubCouDomainActiveStyle(LiPanel selectedLiPanel,LiPanel previousSelectedLiPanel,boolean flag) {
		if(flag){
//			previousSelectedLiPanel.addStyleName("active");
			selectedLiPanel.addStyleName("active");
		}else{
			selectedLiPanel.addStyleName("active");
			previousSelectedLiPanel.removeStyleName("active");
		}
	}
	
	
	public void removeSelectedDomainStyle(LiPanelWithClose liPanelWithClose, String type) {
		Iterator<Widget> widgets;
		if("Unit".equalsIgnoreCase(type)){
			widgets = domainUlContainer.iterator();
		}else{
			widgets = standardsUlContainer.iterator();
		}
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanel && (((LiPanel) widget).getCodeId()==liPanelWithClose.getId())){
				widget.removeStyleName("active");
			}
		}
	}

	public void addOrRemoveContent(LiPanel selectedLiPanel,	LiPanel previousSelectedDomainLiPanel, LiPanelWithClose liPanelWithClose) {
		if(!selectedLiPanel.getStyleName().contains("active")){
			removeAddedContent(String.valueOf(liPanelWithClose.getId())); 
		}else{
			selectedUlContainer.add(liPanelWithClose);
		}
	}


	private void removeAddedContent(String id) {
		Iterator<Widget> widgets=selectedUlContainer.iterator();
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			if (widget instanceof LiPanelWithClose && (String.valueOf(((LiPanelWithClose) widget).getId())).equals(id)) {
				removedLiPanelWithCloseArray.add((LiPanelWithClose) widget);
				widget.removeFromParent();
			}
		}
	}
	
	/*@Override
	public void displaySelectedTaxonomyData(UlPanel ulSelectedItems) {
		Iterator<Widget> widgets = ulSelectedItems.iterator();
		
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanelWithClose){
				setActiveStyle(((LiPanelWithClose) widget).getId());
				selectedUlContainer.add(widget);
			}
		}
	}*/
	
	private void highlightSelectedWidgets() {
		/*for(int i=0;i<selectedUlContainer.getWidgetCount();i++){
			setActiveStyle(selectedUlContainer.getWidget(i).g
		}*/
		Iterator<Widget> widgets = selectedUlContainer.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanelWithClose){
				setActiveStyle(((LiPanelWithClose) widget).getId());
			}
		}

	}


	private void setActiveStyle(long id) {
		Iterator<Widget> widgets;
		if("Unit".equalsIgnoreCase(viewType)){
			widgets = domainUlContainer.iterator();
		}else{
			widgets = standardsUlContainer.iterator();
		}
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanel && ((LiPanel) widget).getCodeId() == id){
				widget.setStyleName("active");
			}
		}
	}

	@Override
	public void displaySelectedTaxonomyData(List<LiPanelWithClose> liPanelWithCloseArrayData) {
		
		for(int i=0;i<liPanelWithCloseArrayData.size();i++){
			setActiveStyle(liPanelWithCloseArrayData.get(i).getId()); 
			LiPanelWithClose closeLiPanel = new LiPanelWithClose("Unit".equalsIgnoreCase(viewType)?liPanelWithCloseArrayData.get(i).getName():liPanelWithCloseArrayData.get(i).getStdTitle());
			closeLiPanel.getCloseButton().addClickHandler(new RemoveLiPanelWithCloseBtn(closeLiPanel));
			closeLiPanel.setId(liPanelWithCloseArrayData.get(i).getId());
			closeLiPanel.setDifferenceId(liPanelWithCloseArrayData.get(i).getDifferenceId());
			selectedUlContainer.add(closeLiPanel);
		}
	}
	
	
	private class RemoveLiPanelWithCloseBtn implements ClickHandler{
		
		LiPanelWithClose closeLiPanel;
		
		public RemoveLiPanelWithCloseBtn(LiPanelWithClose closeLiPanel) {
			this.closeLiPanel = closeLiPanel;
		}

		@Override
		public void onClick(ClickEvent event) {
			if(errorMsgLbl.isVisible()){
				errorMsgLbl.setVisible(false); 
			}
			removeSelectedDomainStyle(closeLiPanel,viewType);
			removedLiPanelWithCloseArray.add(closeLiPanel);
			closeLiPanel.removeFromParent();
		}
		
	}

	
	/**
	 * If any empty courses found, it will get add into the container.
	 */
	@Override
	public void addEmptyCourses(Anchor title, int courseId, LiPanel liPanel, LiPanel previousSelLiPanel) {
		setSubCouDomainActiveStyle(liPanel,previousSelLiPanel,true);
		final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(title.getText());
		liPanelWithClose.setId(courseId);
		liPanelWithClose.setName(title.getText());
		liPanelWithClose.setDifferenceId(1);
		liPanelWithClose.getCloseButton().addClickHandler(new RemoveLiPanelWithCloseBtn(liPanelWithClose));
		addOrRemoveContent(liPanel,previousSelLiPanel,liPanelWithClose); 
	}

	/**
	 * If any empty domains found, it will get add into the container.
	 */
	@Override
	public void addEmptyDomains(int subDomainId, Anchor title, LiPanel liPanel,LiPanel previousSelDomainLiPanel) {
		setSubCouDomainActiveStyle(liPanel,previousSelDomainLiPanel,true);
		final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(title.getText());
		liPanelWithClose.setId(subDomainId);
		liPanelWithClose.setName(title.getText());
		liPanelWithClose.setDifferenceId(2);
		liPanelWithClose.getCloseButton().addClickHandler(new RemoveLiPanelWithCloseBtn(liPanelWithClose));
		addOrRemoveContent(liPanel,previousSelDomainLiPanel,liPanelWithClose); 
	}
	
	public boolean isLimitReached() {
		int selWidgetCount = selectedUlContainer.getWidgetCount();
		boolean flag = selWidgetCount>=LIMIT?true:false;
		return flag;
	}

}
