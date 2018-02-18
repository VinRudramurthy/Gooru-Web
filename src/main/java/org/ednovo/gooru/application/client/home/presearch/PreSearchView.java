
package org.ednovo.gooru.application.client.home.presearch;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.search.SearchFilterDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.gsearch.events.UpdateFilterEvent;
import org.ednovo.gooru.client.mvp.gsearch.events.UpdateFilterHandler;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.socialshare.SentEmailSuccessVc;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;
import org.gwt.advanced.client.ui.widget.AdvancedFlexTable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
/**
 *
 * @fileName : PreSearchView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 16-Jun-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class PreSearchView extends BaseViewWithHandlers<PreSearchUiHandlers> implements IsPreSearchView,ClientConstants {

	private static PreSearchViewUiBinder uiBinder = GWT
			.create(PreSearchViewUiBinder.class);

	interface PreSearchViewUiBinder extends
			UiBinder<Widget, PreSearchView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);
	private HandlerRegistration handlerRegistration=null;

	@UiField Button  btnGrades, btnSubjects, btnBrowseContent, btnBrowseStandard, btnGradesCaret, btnSubjectCaret;
	@UiField Anchor ancLogin, btnLearnAboutApproach;
	@UiField HTMLPanel panelAlreadyHave, panelGrades, buttonGroup, panelGradeGroup, panelSubjectGroup, flexTable;
	@UiField UlPanel ulSubjectPanel,standardsDropListValues;
	@UiField Label lblErrorMessage;
	@UiField Button enterLbl;
	@UiField TextBox classCodeTxtBox;

	private final String QUERY = "query";
	private final String FLT_SUBJECTNAME = "flt.subjectName";
	private final String FLT_GRADE = "flt.grade";
	private final String CATEGORY = "category";
	private final String FLT_COLLECTIONTYPE = "flt.collectionType";
	private final String ALL = "all";
	private final String COLLECTION = "collection";
	AlertMessageUc alertMessageUc;
	private boolean isValid = true;
	private boolean toClear = false;

	private boolean isCCSSAvailable =false;
	private boolean isNGSSAvailable =false;
	private boolean isTEKSAvailable =false;
	private boolean isCAAvailable =false;

	List<LiPanelWithClose> searchLiPanelWithCloseArray = new ArrayList<>();

	String USER_META_ACTIVE_FLAG = "userMetaActiveFlag";

	String[] standardsTypesArray = new String[]{i18n.GL3379(),i18n.GL3322(),i18n.GL3323(),i18n.GL3324(),i18n.GL3325(),i18n.GL3321()};

	TreeMap<Integer, Integer> selectedGrades = new TreeMap<Integer, Integer>();
	String selectedSubjects = "";

	public PreSearchView() {
		setWidget(uiBinder.createAndBindUi(this));

		setDebugIds();

		AppClientFactory.getEventBus().addHandler(UpdateFilterEvent.TYPE, updatefilter);

		Event.addNativePreviewHandler(new NativePreviewHandler() {
	        public void onPreviewNativeEvent(NativePreviewEvent event) {
	        	hidePopup(event);
	          }
	    });
		//getAddStandards();
		enterLbl.addClickHandler(new OnEnterClassCodeClick());
		enterLbl.setText(i18n.GL1065());
		enterLbl.getElement().setId("btnEnter");
		enterLbl.getElement().setAttribute("alt",i18n.GL1065());
		enterLbl.getElement().setAttribute("title",i18n.GL1065());
		enterLbl.setWidth("35%");
		classCodeTxtBox.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				setButtonStatus(CssTokens.ACTIVE);
			}
		});
		classCodeTxtBox.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				setButtonStatus(CssTokens.ACTIVE);
			}
		});
		classCodeTxtBox.setText("");
		classCodeTxtBox.getElement().setAttribute("maxlength", "10");
		classCodeTxtBox.getElement().setId("txtClassCode");
		classCodeTxtBox.getElement().setAttribute("placeholder", i18n.GL1785());

		AdvancedFlexTable table = new AdvancedFlexTable();

		// create headers and put them in the thead tag
		table.setHeaderWidget(0,new Label("First Name"));
		table.setHeaderWidget(1,new Label("Surname"));

		// enable verticall scrolling
		table.enableVerticalScrolling(true);

//		flexTable.add(table);
	}


	protected void hidePopup(NativePreviewEvent event) {
		if(event.getTypeInt()==Event.ONCLICK){
    		Event nativeEvent = Event.as(event.getNativeEvent());
        	boolean target=eventTargetsPopup(nativeEvent);
        	if(!target)
        	{
        		if (panelGrades !=null && panelGrades.isVisible()){
        			panelGrades.setVisible(false);
        		}
        		if (ulSubjectPanel !=null && ulSubjectPanel.isVisible()){
        			ulSubjectPanel.setVisible(false);
        		}
        		if (standardsDropListValues !=null && standardsDropListValues.isVisible()){
        			standardsDropListValues.setVisible(false);
        		}
        	}
    	}
	}

	private boolean eventTargetsPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			return  panelSubjectGroup.getElement().isOrHasChild(Element.as(target)) || panelGradeGroup.getElement().isOrHasChild(Element.as(target)) || panelGrades.getElement().isOrHasChild(Element.as(target))||ulSubjectPanel.getElement().isOrHasChild(Element.as(target));
		}
		return false;
	}

	/**
	 *
	 * @function setDebugIds
	 *
	 * @created_date : 25-Jun-2015
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
	private void setDebugIds() {

		StringUtil.setAttributes(btnLearnAboutApproach.getElement(), "btnLearnAboutApproach", i18n.GL3315(), i18n.GL3315());
		setDefaults();
		panelGrades.setVisible(false);
		StringUtil.setAttributes(buttonGroup.getElement(), "gooruSearchMainContainer", "", "");
	}

	/* */
	@Override
	public void setButtonVisibility(){
		panelAlreadyHave.setVisible(AppClientFactory.isAnonymous());
	}


	/* UI Handlers...*/

	@UiHandler("btnLearnAboutApproach")
	public void onClickLearnAboutApproach(ClickEvent event){

	}

	@UiHandler("btnGrades")
	public void onClickGrades(ClickEvent event){
		setGradeVisibility();
		if (ulSubjectPanel.isVisible()){
			setSubjectVisibility();
		}
		if (standardsDropListValues.isVisible()){
			setStandardsVisibility();
		}
	}

	@UiHandler("btnGradesCaret")
	public void onClickGradeCaret(ClickEvent event){
		setGradeVisibility();
		if (ulSubjectPanel.isVisible()){
			setSubjectVisibility();
		}
		if (standardsDropListValues.isVisible()){
			setStandardsVisibility();
		}
	}

	@UiHandler("btnSubjects")
	public void onClickSubject(ClickEvent event){

		if (ulSubjectPanel.getWidgetCount() == 0){
			getUiHandlers().loadSubjects();
		}else{
			setSubjectVisibility();
			if (panelGrades.isVisible()){
				setGradeVisibility();
			}
			if (standardsDropListValues.isVisible()){
				setStandardsVisibility();
			}
		}
	}

	@UiHandler("btnSubjectCaret")
	public void onClickSubjectCaret(ClickEvent event){
		if (ulSubjectPanel.getWidgetCount() == 0){
			getUiHandlers().loadSubjects();
		}else{
			setSubjectVisibility();
			if (panelGrades.isVisible()){
				setGradeVisibility();
			}
			if (standardsDropListValues.isVisible()){
				setStandardsVisibility();
			}
		}
	}

	@UiHandler("ancLogin")
	public void onClickLogin(ClickEvent event){
		GWT.runAsync(new SimpleRunAsyncCallback() {
			@Override
			public void onSuccess() {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
				LoginPopupUc popup = new LoginPopupUc() {
					@Override
					public void onLoginSuccess() {

					}
				};
				popup.setGlassEnabled(true);
				popup.center();
				popup.show();
			}
		});

	}

	@UiHandler("btnBrowseStandard")
	public void onClickBrowseStandards(ClickEvent event){
		getAddStandards();
		if(!standardsDropListValues.getElement().getAttribute("style").equalsIgnoreCase("display:block;")){
			standardsDropListValues.getElement().setAttribute("style", "display:block; top:auto;");
		}else{
			standardsDropListValues.getElement().removeAttribute("style");
		}
	}

	@UiHandler("btnBrowseContent")
	public void onClickBrowseContent(ClickEvent event){
		if (selectedSubjects.isEmpty()){
			lblErrorMessage.setText(i18n.GL3329());
			lblErrorMessage.setVisible(true);
		}else if (selectedSubjects.isEmpty()){
			lblErrorMessage.setText(i18n.GL3331());
			lblErrorMessage.setVisible(true);
		}else if (selectedGrades.size() <= 0){
			lblErrorMessage.setText(i18n.GL3330());
			lblErrorMessage.setVisible(true);
		}else{
			lblErrorMessage.setVisible(false);
			Map<String, String> params = new HashMap<String, String>();
			params.put(FLT_GRADE, getSelectedGrades());
			if(!selectedSubjects.isEmpty())
			{
			params.put(FLT_SUBJECTNAME, selectedSubjects);
			}
			params.put(CATEGORY,ALL);
			params.put(FLT_COLLECTIONTYPE,COLLECTION);

			params.put(QUERY, "*");
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SEARCH_COLLECTION, params, true);
		}

	}


	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == PreSearchPresenter.GRADES){
			panelGrades.add(content);
		}
	}

	/**
	 *
	 * @function setGradeVisibility
	 *
	 * @created_date : 22-Jun-2015
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
	private void setGradeVisibility(){
		lblErrorMessage.setVisible(false);

		if (panelGrades.isVisible()){
			panelGrades.getElement().getStyle().setDisplay(Display.NONE);
		}else{
			panelGrades.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		}
	}

	/**
	 *
	 * @function setSubjectVisibility
	 *
	 * @created_date : 23-Jun-2015
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
	private void setSubjectVisibility(){
		lblErrorMessage.setVisible(false);

		if (ulSubjectPanel.isVisible()){
			ulSubjectPanel.getElement().getStyle().setDisplay(Display.NONE);
		}else{
			ulSubjectPanel.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		}
	}
	private void setStandardsVisibility(){
		lblErrorMessage.setVisible(false);

		if (standardsDropListValues.isVisible()){
			standardsDropListValues.getElement().getStyle().setDisplay(Display.NONE);
		}else{
			standardsDropListValues.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		}
	}


	/* Browse Standards */
	public void OnStandardsClickEvent(Button standardsButtonClicked)
	{
		if(handlerRegistration!=null){
			handlerRegistration.removeHandler();
		}
		handlerRegistration=standardsButtonClicked.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(!standardsDropListValues.getElement().getAttribute("style").equalsIgnoreCase("display:block;")){
					standardsDropListValues.getElement().setAttribute("style", "display:block;");
				}else{
					standardsDropListValues.getElement().removeAttribute("style");
				}
			}
		});
	}

	@Override
	public void setUpdatedStandards(List<Map<String, String>> standsListArray){
		StringBuffer filterStd= new StringBuffer();
		//getUiHandlers().closeStandardsPopup();
		if(standsListArray.size()!=0){
			for(int i=0; i<standsListArray.size(); i++){
				if (i==0){
					filterStd.append(standsListArray.get(i).get("selectedCodeVal"));
				}else{
					filterStd.append(","+standsListArray.get(i).get("selectedCodeVal"));
				}
			}
			callSearch(filterStd.toString());
		}
	}

	/**
	 *
	 * @function callSearch
	 *
	 * @created_date : 23-Jun-2015
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
	public void callSearch(String stdFilter){
		getUiHandlers().refreshSearch("*", stdFilter);
	}

	/**
	 *
	 */
	UpdateFilterHandler updatefilter = new UpdateFilterHandler() {
		@Override
		public void updateFilters(String filterValue, String addOrRemove, String page) {
			if ("home".equalsIgnoreCase(page)){
				filterValue = filterValue.replaceAll("Grade", "").replaceAll(" " , "");
				int value = 0;
				if (filterValue.equalsIgnoreCase("k")){
					value = 0;
				}else if (filterValue.equalsIgnoreCase("pre-k")){
					value =-1;
				}else if (filterValue.equalsIgnoreCase("HigherEd")){
					value =13;
				}else{
					value = Integer.parseInt(filterValue);
				}

				if("add".equals(addOrRemove)){
					selectedGrades.put(value, value);
				}else{
					selectedGrades.remove(value);
				}
				displaySelectedGrades(addOrRemove);
			}
		}
	};

	/**
	 *
	 * @function displaySelectedGrades
	 *
	 * @created_date : 24-Jun-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param addOrRemove
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void displaySelectedGrades(String addOrRemove) {
		int count=0;
		StringBuffer selectedGrade = new StringBuffer();
		Iterator it = selectedGrades.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Integer,Integer> pair = (Map.Entry<Integer,Integer>)it.next();
	        String pairValue = "";

	        if (pair.getValue() == -1){
	        	pairValue = "Pre-K";
	        }else if (pair.getValue() == 0){
	        	pairValue = "K";
	        }else if (pair.getValue() == 13){
	        	pairValue = "Higher Ed";
	        }else{
	        	pairValue = String.valueOf(pair.getValue());
	        }

	        if (count==0){
	        	selectedGrade.append(pairValue);
	        }else{
	        	selectedGrade.append(", "+pairValue);
	        }
	        count++;
	    }
	    if (selectedGrades.size() > 0){
	    	btnGrades.setText(selectedGrade.toString());
	    }else{
	    	btnGrades.setText(i18n.GL3289());
	    }
	    if ("add".equalsIgnoreCase(addOrRemove)){
	    	if (selectedGrades.size() > 1){
//	    		btnGrades.getElement().addClassName("ellipsis");
	    	}
	    }else{
	    	if (selectedGrades.size() <= 2){
//	    		btnGrades.getElement().removeClassName("ellipsis");
	    	}
	    }
	}
	/**
	 *
	 * @function renderSubjects
	 *
	 * @created_date : 23-Jun-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param list
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void renderSubjects(List<String> list){
		InlineLabel text=new InlineLabel(i18n.GL3234()+i18n.GL_SPL_SEMICOLON());
		ulSubjectPanel.add(text);
		for (String subject : list) {
			LiPanel liPanel = new LiPanel();
			Anchor lblSubject=new Anchor(subject);
			liPanel.add(lblSubject);
			liPanel.addClickHandler(new SubjectItemClickHandler(subject,liPanel));
			ulSubjectPanel.add(liPanel);
		}
	}

	/**
	 *
	 * @fileName : PreSearchView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 23-Jun-2015
	 *
	 * @Author tumbalam
	 *
	 * @Reviewer:
	 */
	public class SubjectItemClickHandler implements ClickHandler{
		String subjectVal;
		LiPanel liPanel;
		SubjectItemClickHandler(String subjectVal,LiPanel liPanel){
			this.subjectVal=subjectVal;
			this.liPanel=liPanel;
		}
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new RunAsyncCallback() {
				@Override
				public void onFailure(Throwable reason) {
				}
				@Override
				public void onSuccess() {
					removeActiveStylestoOtherSubjects();
					if(liPanel.getStyleName().equals(CssTokens.ACTIVE)){
						liPanel.removeStyleName(CssTokens.ACTIVE);
						displaySelectedSbujects("remove");
					}else{
						liPanel.setStyleName(CssTokens.ACTIVE);
						selectedSubjects = subjectVal;
						displaySelectedSbujects("add");
					}


				}

			});
		}
	}
	public void removeActiveStylestoOtherSubjects() {
		Iterator<Widget> widgets = ulSubjectPanel.iterator();
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			if(widget instanceof LiPanel){
				LiPanel obj=(LiPanel) widget;				
				obj.removeStyleName("active");
				
			}
		}
	}
	/**
	 *
	 * @function displaySelectedSbujects
	 *
	 * @created_date : 24-Jun-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param action
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void displaySelectedSbujects(String action) {
	    if (!selectedSubjects.isEmpty()){
	    	btnSubjects.setText(selectedSubjects);
	    }else{
	    	btnSubjects.setText(i18n.GL3291());
	    }
	    if ("add".equalsIgnoreCase(action)){
//	    	btnSubjects.getElement().addClassName("ellipsis");
	    }else{

	    }
	}

	@Override
	public void setSearchFilter(SearchFilterDo searchFilterDo) {
		if(searchFilterDo.getSubjects()!=null && searchFilterDo.getSubjects().size()>=0){
			if (panelGrades.isVisible()){
				setGradeVisibility();
			}
			if (standardsDropListValues.isVisible()){
				setStandardsVisibility();
			}
			setSubjectVisibility();
			renderSubjects(searchFilterDo.getSubjects());
		}
	}

	/**
	 *
	 * @function getSelectedGrades
	 *
	 * @created_date : 23-Jun-2015
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
	public String getSelectedGrades(){
		int count=0;
		StringBuffer selectedGrade = new StringBuffer();
		Iterator it = selectedGrades.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Integer,Integer> pair = (Map.Entry<Integer,Integer>)it.next();
	        String pairValue = "";

	        if (pair.getValue() == -1){
	        	pairValue = "Pre-K";
	        }else if (pair.getValue() == 0){
	        	pairValue = "K";
	        }else if (pair.getValue() == 13){
	        	pairValue = "Higher Ed";
	        }else{
	        	pairValue = String.valueOf(pair.getValue());
	        }

	        if (count==0){
	        	selectedGrade.append(pairValue);
	        }else{
	        	selectedGrade.append(","+pairValue);
	        }
	        count++;
	    }
	    return selectedGrade.toString();
	}
	/**
	 *
	 * @function getSelectedSubjects
	 *
	 * @created_date : 23-Jun-2015
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
/*	public String getSelectedSubjects(){
		int count=0;
		StringBuffer selectedSubject = new StringBuffer();
		Iterator it = selectedSubjects.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String,String> pair = (Map.Entry<String,String>)it.next();
	        String pairValue = pair.getValue().trim();

	        if (count==0){
	        	selectedSubject.append(pairValue);
	        }else{
	        	selectedSubject.append("~~"+pairValue);
	        }
	        count++;
	    }
	    return selectedSubject.toString();
	}*/

	@Override
	public void setDefaults(){
		lblErrorMessage.setVisible(false);
		btnGrades.setText(i18n.GL3289());
		btnSubjects.setText(i18n.GL3291());
		StringUtil.setAttributes(btnSubjects.getElement(), "btnSubjects", i18n.GL3291(), i18n.GL3291());
		StringUtil.setAttributes(btnGrades.getElement(), "btnGrades", i18n.GL3289(), i18n.GL3289());
	}

	private void parseSelectedGrades(String selected){

		if ("pre-k".equalsIgnoreCase(selected)){

		}else if ("higher ed".equalsIgnoreCase(selected)){

		}else{
			if ("k".equalsIgnoreCase(selected)){

			}else{
				int grade = Integer.parseInt(selected.replaceAll("Grade", "").trim());
				if (grade <= 5){

				}else if (grade > 5 && grade <= 8){

				}else {

				}
			}
		}

	}

	@Override
	public HTMLPanel getPanelGrades() {
		return panelGrades;
	}
	private void setButtonStatus(final String status) {
		GWT.runAsync(new SimpleRunAsyncCallback() {
			@Override
			public void onSuccess() {
				if(classCodeTxtBox.getText().length()>0) {
					classCodeTxtBox.getElement().addClassName(CssTokens.TEXT_UPPERCASE);
					classCodeTxtBox.getElement().removeAttribute("placeholder");
				} else {
					classCodeTxtBox.getElement().removeClassName(CssTokens.TEXT_UPPERCASE);
					classCodeTxtBox.getElement().setAttribute("placeholder", i18n.GL1785());
				}
				if (status.equalsIgnoreCase(CssTokens.ACTIVE)) {
					enterLbl.getElement().removeClassName(CssTokens.DISABLED);
					enterLbl.setEnabled(true);
				} else {
					enterLbl.getElement().addClassName(CssTokens.DISABLED);
					enterLbl.setEnabled(false);
				}
			}
		});
	}
	/**
	 *
	 * @fileName :
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 06-Dec-2014
	 *
	 * @Author Gooru Team
	 *
	 * @Reviewer:
	 */
	public class OnEnterClassCodeClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {
					setButtonStatus(CssTokens.ACTIVE);
					if (classCodeTxtBox.getText().trim().equalsIgnoreCase("")
							|| classCodeTxtBox.getText().trim() == null) {
						alertMessageUc = new AlertMessageUc(i18n.GL0061(), new Label(i18n.GL0243()));
						ClickHandler alertHandler = new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								isValid = false;
								setButtonStatus("");
							}
						};
						alertMessageUc.appPopUp.addDomHandler(alertHandler,
								ClickEvent.getType());

						alertMessageUc.okButton.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								isValid = false;
								setButtonStatus("");
							}
						});
						return;
					}

					MixpanelUtil.ClickOnStudyNow();
					AppClientFactory
							.getInjector()
							.getClasspageService()
							.v3GetClassByCode(classCodeTxtBox.getText().trim(),
									new SimpleAsyncCallback<ClasspageDo>() {
										@Override
										public void onSuccess(ClasspageDo result) {
											setButtonStatus("");
											 String classUid = null;
											 String status = null;
											 boolean sharing = false;

											 if(result.getClassType()!=null) {
												 if(result.getClassType().equalsIgnoreCase("new-class")) {
													 classUid = result.getClassUid();
													 status = result.getStatus();
													 sharing = result.isVisibility();
												 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
													 classUid = result.getGooruOid();
													 status = result.getMeta().getStatus();
													 if(result.getSharing()!=null&&result.getSharing().equalsIgnoreCase("public")) {
														 sharing = true;
													 }
												 }
											 }

											 if (classUid==null) {
												Window.enableScrolling(false);
												AppClientFactory
														.fireEvent(new SetHeaderZIndexEvent(
																98, false));
												alertMessageUc = new AlertMessageUc(i18n.GL0061(), new Label(i18n.GL0244()));
												ClickHandler alertHandler = new ClickHandler() {

													@Override
													public void onClick(ClickEvent event) {
														isValid = false;
													}
												};
												alertMessageUc.appPopUp.addDomHandler(
														alertHandler,
														ClickEvent.getType());

												alertMessageUc.okButton.addClickHandler(new ClickHandler() {

															@Override
															public void onClick(
																	ClickEvent event) {
																isValid = false;
															}
														});
											} else if (result.getUser().getGooruUId().equalsIgnoreCase(AppClientFactory.getGooruUid())) {
												if (AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
													MixpanelUtil.Click_Study_LandingPage();
												}
												if(result.getClassType()!=null) {
													 Map<String, String> params = new HashMap<String, String>();
													 if(result.getClassType().equalsIgnoreCase("new-class")) {
															params.put("id",classUid);
															if(result.getCourseGooruOid() != null){
																params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
															}
															AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
													 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
															params.put("id",classUid);
															params.put("pageSize","5");
															params.put("pageNum","0");
															params.put("pos","1");
															params.put("b","true");
															AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
													 }
												 }

												classCodeTxtBox.setText("");
												if (alertMessageUc != null)
													alertMessageUc.hide();
											} else if (!sharing) {
												if (result.getUser().getGooruUId().equalsIgnoreCase(AppClientFactory.getGooruUid())) {
													if (AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
														MixpanelUtil.Click_Study_LandingPage();
													}

													 if(result.getClassType()!=null) {
														 Map<String, String> params = new HashMap<String, String>();
														 if(result.getClassType().equalsIgnoreCase("new-class")) {
																if(result.getCourseGooruOid() != null){
																	params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
																}
																params.put("id",result.getClassUid());
																AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
														 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
																params.put("id",classUid);
																params.put("pageSize","5");
																params.put("pageNum","0");
																params.put("pos","1");
																params.put("b","true");
																AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
														 }
													 }
													classCodeTxtBox.setText("");
													if (alertMessageUc != null)
														alertMessageUc.hide();
													//StudentAssignmentView.setPrivatePage();

												} else if (status!=null&&status.equalsIgnoreCase(CssTokens.ACTIVE)) {
													if (AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
														MixpanelUtil.Click_Study_LandingPage();
													}

													 if(result.getClassType()!=null) {
														 Map<String, String> params = new HashMap<String, String>();
														 if(result.getClassType().equalsIgnoreCase("new-class")) {
																if(result.getCourseGooruOid() != null){
																	params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
																}
																params.put("id",result.getClassUid());
																AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
														 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
																params.put("id",result.getGooruOid());
																params.put("pageSize","5");
																params.put("pageNum","0");
																params.put("pos","1");
																AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
														 }
													 }
													classCodeTxtBox.setText("");
													if (alertMessageUc != null)
														alertMessageUc.hide();
													//StudentAssignmentView.setPrivatePageActive();

												} else if (status!=null&&status.equalsIgnoreCase("pending")) {
													if (AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
														MixpanelUtil.Click_Study_LandingPage();
													}

													 if(result.getClassType()!=null) {
														 Map<String, String> params = new HashMap<String, String>();
														 if(result.getClassType().equalsIgnoreCase("new-class")) {
																if(result.getCourseGooruOid() != null){
																	params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
																}
																params.put("id",result.getClassUid());
																AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
														 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
													    	   new SentEmailSuccessVc(i18n.GL1177(), i18n.GL1535_1());
														 }
													 }
													classCodeTxtBox.setText("");
													if (alertMessageUc != null)
														alertMessageUc.hide();
													//StudentAssignmentView.setPrivatePagePending();

												} else {
													if (AppClientFactory.isAnonymous()) {
														new SentEmailSuccessVc(i18n.GL1177(),
																i18n.GL1535());
													} else {
														new SentEmailSuccessVc(i18n.GL1177(),
																i18n.GL1535_1());
													}
												}

											} else {
												toClear = true;
												if (AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
													MixpanelUtil.Click_Study_LandingPage();
												}

												 if(result.getClassType()!=null) {
													 Map<String, String> params = new HashMap<String, String>();
													 if(result.getClassType().equalsIgnoreCase("new-class")) {
															if(result.getCourseGooruOid() != null){
																params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
															}
															params.put("id",result.getClassUid());
															AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
													 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
															params.put("id",result.getGooruOid());
															params.put("pageSize","5");
															params.put("pageNum","0");
															params.put("pos","1");
															AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
													 }
												 }
												classCodeTxtBox.setText("");
												if (alertMessageUc != null)
													alertMessageUc.hide();
											}
											setButtonStatus("");
										}

									});
				}
			});

		}
	}

	@Override
	public UlPanel getUlSubjectPanel() {
		return ulSubjectPanel;
	}
	public void setUlSubjectPanel(UlPanel ulSubjectPanel) {
		this.ulSubjectPanel = ulSubjectPanel;
	}

	public final void populateStandardValues(){
		standardsDropListValues.clear();
        for (String standardsTypesArray1 : standardsTypesArray) {
            List<String> standardsDescriptionList = Arrays.asList(standardsTypesArray1.split(","));
            LiPanel liPanel = new LiPanel();
            for(int j=0; j<standardsDescriptionList.size(); j++){
                HTMLPanel headerDiv = new HTMLPanel("");
                if(j==0){
                	if(standardsDescriptionList.get(j).equalsIgnoreCase("CA SS")){
                        liPanel.getElement().setId("CA");
                    }else if(standardsDescriptionList.get(j).equalsIgnoreCase("LWMCS")){
                        liPanel.getElement().setId("B21");
                    }else{
                        liPanel.getElement().setId(standardsDescriptionList.get(j));
                    }

                    if((!isCCSSAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("CCSS")){
      		    	  liPanel.getElement().setAttribute("style", "opacity:0.5;");
      		        }
      		      else if((!isCAAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("CA SS"))
      		        {
      		    	  liPanel.getElement().setAttribute("style", "opacity:0.5;");
      		        }
      		      else if((!isNGSSAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("NGSS")){
      		    	  liPanel.getElement().setAttribute("style", "opacity:0.5;");
      		        }
      		      else if((!isTEKSAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("TEKS")){
      		    	  liPanel.getElement().setAttribute("style", "opacity:0.5;");
      		        }

                    headerDiv.setStyleName("liPanelStyle");
                }else{
                	if(standardsDescriptionList.get(j).equalsIgnoreCase("College Career and Civic Life"))
                	{
                		standardsDescriptionList.set(j, "College, Career, and Civic Life");
                        headerDiv.setStyleName("liPanelStylenonBold");
                        liPanel.getElement().setAttribute("standarddesc", "College, Career, and Civic Life");
                	}
                	else
                	{
                    headerDiv.setStyleName("liPanelStylenonBold");
                    liPanel.getElement().setAttribute("standarddesc", standardsDescriptionList.get(j));
                	}
                }
                headerDiv.getElement().setInnerHTML(standardsDescriptionList.get(j));
                liPanel.add(headerDiv);
            }
            if(liPanel.getElement().getAttribute("style")!=null && !liPanel.getElement().getAttribute("style").equalsIgnoreCase("opacity:0.5;"))
            {
            liPanel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                	standardsDropListValues.setVisible(false);
				String standardsVal = event.getRelativeElement().getAttribute("id");
				String standardsDesc = event.getRelativeElement().getAttribute("standarddesc");

				searchLiPanelWithCloseArray.clear();
			//	setUpdatedStandards
			/*	for(int i=0;i<ulSelectedItems.getWidgetCount();i++){
					searchLiPanelWithCloseArray.add((LiPanelWithClose) ulSelectedItems.getWidget(i));
				}*/

				getUiHandlers().showStandardsPopup(standardsVal,standardsDesc, searchLiPanelWithCloseArray);
			}
		});
            }
            standardsDropListValues.add(liPanel);
        }
}
public void checkStandarsList(List<String> standarsPreferencesList) {


		if(standarsPreferencesList!=null){
			if(standarsPreferencesList.contains("CCSS")){
				isCCSSAvailable = true;
			}else{
				isCCSSAvailable = false;
			}
			if(standarsPreferencesList.contains("NGSS")){
				isNGSSAvailable = true;
			}else{
				isNGSSAvailable = false;
			}
			if(standarsPreferencesList.contains("TEKS")){
				isTEKSAvailable = true;
			}else{
				isTEKSAvailable = false;
			}
			if(standarsPreferencesList.contains("CA")){
				isCAAvailable = true;
			}else{
				isCAAvailable = false;
			}
		}

		populateStandardValues();
	}

	public void getAddStandards() {

		if(!AppClientFactory.isAnonymous()){
			AppClientFactory.getInjector().getUserService().getUserProfileV2Details(AppClientFactory.getLoggedInUser().getGooruUId(),
				USER_META_ACTIVE_FLAG,
				new SimpleAsyncCallback<ProfileDo>() {
					@Override
					public void onSuccess(final ProfileDo profileObj) {
						checkStandarsList(profileObj.getUser().getMeta().getTaxonomyPreference().getCode());
					}

				});
		}else{
			isCCSSAvailable = true;
			isNGSSAvailable = true;
			isCAAvailable = true;
			isTEKSAvailable = false;
			populateStandardValues();
		}
	}


}
