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
/**
 * 
 */
package org.ednovo.gooru.client.uc.tooltip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageListDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.classpages.ClasspageListPopupViewCBundle;
import org.ednovo.gooru.client.mvp.classpages.event.RefreshClasspageListEvent;
import org.ednovo.gooru.client.mvp.classpages.event.RefreshClasspageListHandler;
import org.ednovo.gooru.client.mvp.classpages.event.SetSelectedClasspageListEvent;
import org.ednovo.gooru.client.mvp.classpages.event.SetSelectedClasspageListHandler;
import org.ednovo.gooru.client.mvp.classpages.event.UpdateClasspageTitleEvent;
import org.ednovo.gooru.client.mvp.classpages.event.UpdateClasspageTitleHandler;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentView;
import org.ednovo.gooru.client.mvp.home.HeaderUc;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.socialshare.SentEmailSuccessVc;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru Team
 *
 */
public class StudyNowToolTip extends PopupPanel {

	private static StudyNowToolTipUiBinder uiBinder = GWT
			.create(StudyNowToolTipUiBinder.class);
	
	private MessageProperties i18n = GWT.create(MessageProperties.class); 

	interface StudyNowToolTipUiBinder extends UiBinder<Widget, StudyNowToolTip> {
	}

	@UiField Label lblTitle,lblLoading;

	@UiField TextBoxWithPlaceholder classCodeTxtBox;

	@UiField Button enterLbl,disabledBtn;
	
	@UiField HTMLPanel tooltipPanel,panelCode;
	
	@UiField
	ScrollPanel spanelCollectionList;
	
	@UiField VerticalPanel classStudyList;

	AlertMessageUc alertMessageUc;

	private boolean isValid=true;
	
//	ClasspageListDo classpageListDo = null;
	
	ClasspageListDo classpageListDo= null;

	HeaderUc headerUc;
	
	Map<String, CollectionDo> classpageList = new HashMap<String, CollectionDo>();
	ArrayList<String> listClasspage = new ArrayList<String>();
	
	ClasspageListPopupViewCBundle res;
	
	private int limit = 10;
	private int offSet = 0;
	private boolean toClear = false;
	private int tmpOffSet = 0;
	private boolean isApiCalling = false;
	private int resultSize = 0;
	private int totalHitCount=0;

	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	public StudyNowToolTip() {
		setWidget(uiBinder.createAndBindUi(this));
//		lblTitle.getElement().getStyle().setMarginTop(14, Unit.PX);
		this.res = ClasspageListPopupViewCBundle.INSTANCE;
		res.css().ensureInjected();
		tooltipPanel.getElement().getStyle().setTop(14, Unit.PX);
		panelCode.getElement().setId("pnlPanelCode");
		tooltipPanel.getElement().setId("pnlTooltipPanel");
		enterLbl.setText(i18n.GL1065());
		enterLbl.getElement().setId("btnEnterLbl");	
		enterLbl.getElement().setAttribute("alt", i18n.GL1065());
		enterLbl.getElement().setAttribute("title", i18n.GL1065());
		lblTitle.setText(i18n.GL0474());
		lblTitle.getElement().setId("lblLblTitle");
		lblTitle.getElement().setAttribute("alt", i18n.GL0474());
		lblTitle.getElement().setAttribute("title", i18n.GL0474());
		lblLoading.getElement().setId("lblLblLoading");
		spanelCollectionList.getElement().setId("sbSpanelCollectionList");
		classCodeTxtBox.setText("");
		classCodeTxtBox.getElement().setAttribute("maxlength", "10");
		classCodeTxtBox.getElement().setId("txtClassCode");
		classCodeTxtBox.setPlaceholder(i18n.GL0184());
		classStudyList.getElement().setId("vpnlClassStudyList");
//		lblLoading.setText(GL0110+GL_SPL_FULLSTOP+GL_SPL_FULLSTOP+GL_SPL_FULLSTOP);
		
		if(!AppClientFactory.isAnonymous()) {
			toClear=true;
			getStudyClassList(String.valueOf(offSet));
			lblTitle.setVisible(false);
		}else{
			lblTitle.setVisible(true);
		}
		SetSelectedClasspageListHandler setSelectedHandler = new SetSelectedClasspageListHandler() {
			@Override
			public void setClasspageTitle(String classpageId) {
				setClasspageSetSelected(classpageId);
			}
		};
		
		UpdateClasspageTitleHandler updateTitleHandler = new UpdateClasspageTitleHandler() {
			
			@Override
			public void updateClasspageTitle(String classpageId, String classpageTitle) {
				updateTitle(classpageId, classpageTitle);
			}
		};
		
       RefreshClasspageListHandler refreshHandler = new RefreshClasspageListHandler() {
			
			@Override
			public void refreshClasspage() {
				toClear= true;
				offSet = 0;
				getStudyClassList(String.valueOf(offSet));
			}
		};
        spanelCollectionList.addScrollHandler(new ScrollHandler() {
			
			@Override
			public void onScroll(ScrollEvent event) {
				offSet+=limit;
				if (spanelCollectionList.getVerticalScrollPosition() == spanelCollectionList.getMaximumVerticalScrollPosition() && !isApiCalling && totalHitCount >=offSet){
					tmpOffSet = offSet;
					classStudyList.add(createClasspageTitleLabel("Loading...", "lblLoading", true));
					isApiCalling = true;
					getStudyClassList(String.valueOf(offSet));
				}
			}
		});
		AppClientFactory.getEventBus().addHandler(SetSelectedClasspageListEvent.TYPE, setSelectedHandler);
		AppClientFactory.getEventBus().addHandler(RefreshClasspageListEvent.TYPE, refreshHandler);
		AppClientFactory.getEventBus().addHandler(UpdateClasspageTitleEvent.TYPE, updateTitleHandler);
		
		enterLbl.addClickHandler(new OnEnterClassCodeClick());
		ClickHandler rootHandler=new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(!isValid && isVisible()){
					show();
					isValid=true;
				}
			}
		};
		disabledBtn.setText(i18n.GL1065());
		disabledBtn.getElement().setId("btnDisabledBtn");
		disabledBtn.getElement().setAttribute("alt", i18n.GL1065());
		disabledBtn.getElement().setAttribute("title", i18n.GL1065());
		disabledBtn.setVisible(false);
		RootPanel.get().addDomHandler(rootHandler, ClickEvent.getType());

	}
	
	public class OnEnterClassCodeClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			setEnterLblVisbility(true);
			if (classCodeTxtBox.getText().trim().equalsIgnoreCase("") || classCodeTxtBox.getText().trim() == null){
				alertMessageUc=new AlertMessageUc(i18n.GL0061(), new Label(i18n.GL0243()));
				ClickHandler alertHandler=new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						isValid=false;
						setEnterLblVisbility(false);
					}
				};
				alertMessageUc.appPopUp.addDomHandler(alertHandler, ClickEvent.getType());
				
				alertMessageUc.okButton.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						isValid=false;
						setEnterLblVisbility(false);
					}
				});
				return;
			}
			
			MixpanelUtil.ClickOnStudyNow();
			AppClientFactory.getInjector().getClasspageService().v2getClasspageByCode(classCodeTxtBox.getText().trim(), new SimpleAsyncCallback<CollectionDo>(){

				@Override
				public void onFailure(Throwable caught) {
					super.onFailure(caught);
					setEnterLblVisbility(false);
				}
				
				@Override
				public void onSuccess(CollectionDo result) {
					 setEnterLblVisbility(false);
					 if(result.getGooruOid()==null){
						 Window.enableScrolling(false);
						 AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
						alertMessageUc=new AlertMessageUc(i18n.GL0061(), new Label(i18n.GL0244()));
						ClickHandler alertHandler=new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								isValid=false;
								
							}
						};
						alertMessageUc.appPopUp.addDomHandler(alertHandler, ClickEvent.getType());
						
						alertMessageUc.okButton.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								isValid=false;
							}
						});
					}else if(result.getCreator().getGooruUId().equalsIgnoreCase(AppClientFactory.getGooruUid()))
					{
						if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
							MixpanelUtil.Click_Study_LandingPage();
						}
						
						Map<String, String> params = new HashMap<String, String>();
						params.put("id",result.getGooruOid());
						params.put("pageSize", "10");
						params.put("pageNum", "0");
						params.put("pos", "1");
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
						classCodeTxtBox.setText("");
						hide();
						if(alertMessageUc!=null)
						alertMessageUc.hide();
					}				 
					 else if(result.getSharing().equalsIgnoreCase("private")){
					
						if(result.getCreator().getGooruUId().equalsIgnoreCase(AppClientFactory.getGooruUid()))
						{
							if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
								MixpanelUtil.Click_Study_LandingPage();
							}
							
							Map<String, String> params = new HashMap<String, String>();
							params.put("id",result.getGooruOid());
							params.put("pageSize", "10");
							params.put("pageNum", "0");
							params.put("pos", "1");
							AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
							classCodeTxtBox.setText("");
							hide();
							if(alertMessageUc!=null)
							alertMessageUc.hide();
							
							StudentAssignmentView.setPrivatePage();

						}
						else if(result.getStatus().equalsIgnoreCase("active"))
						{
							if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
								MixpanelUtil.Click_Study_LandingPage();
							}
							
							Map<String, String> params = new HashMap<String, String>();
							params.put("id",result.getGooruOid());
							params.put("pageSize", "10");
							params.put("pageNum", "0");
							params.put("pos", "1");
							AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
							classCodeTxtBox.setText("");
							hide();
							if(alertMessageUc!=null)
							alertMessageUc.hide();
							
							//StudentAssignmentView.setPrivatePageActive();

						}
						else if(result.getStatus().equalsIgnoreCase("pending")) 
						{
							if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
								MixpanelUtil.Click_Study_LandingPage();
							}
							
							Map<String, String> params = new HashMap<String, String>();
							params.put("id",result.getGooruOid());
							params.put("pageSize", "10");
							params.put("pageNum", "0");
							params.put("pos", "1");
							AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
							classCodeTxtBox.setText("");
							hide();
							if(alertMessageUc!=null)
							alertMessageUc.hide();
							
							//StudentAssignmentView.setPrivatePagePending();

						}
						else 
						{
							       if(AppClientFactory.isAnonymous()){
							    	   new SentEmailSuccessVc(i18n.GL1177(), i18n.GL1535());
							       }else{
							    	   new SentEmailSuccessVc(i18n.GL1177(), i18n.GL1535_1());
							       }
						}
						
					}
					else
					{	
						toClear=true;
						getStudyClassList(String.valueOf(0));
						showClasspageList();
						if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
							MixpanelUtil.Click_Study_LandingPage();
						}
						
						Map<String, String> params = new HashMap<String, String>();
						params.put("id",result.getGooruOid());
						params.put("pageSize", "10");
						params.put("pageNum", "0");
						params.put("pos", "1");
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
						classCodeTxtBox.setText("");
						hide();
						if(alertMessageUc!=null)
						alertMessageUc.hide();
						
							/*if(result.getCreator().getGooruUId().equalsIgnoreCase(AppClientFactory.getGooruUid()))
							{
								StudentAssignmentView.setPublicPage();
							}
							else if(result.getStatus().equalsIgnoreCase("active"))
							{
								StudentAssignmentView.setPublicPageActive();
							}
							else 
							{
								StudentAssignmentView.setPublicPagePending();
							}	*/
						
				}
					 setEnterLblVisbility(false);
			}

			});
		}
	}

	public void getStudyClassList(String offSet) {
		setStudyClassList(null);
		AppClientFactory.getInjector().getClasspageService().v2GetUserClasses(String.valueOf(limit), offSet,String.valueOf(Math.random()),
				new SimpleAsyncCallback<ClasspageListDo >() {
					@Override
					public void onSuccess(ClasspageListDo result) {
						classpageListDo = result;
						setStudyClassList(result);
						totalHitCount=result.getTotalHitCount();
					}
				});
	}
	
	private void setStudyClassList(final ClasspageListDo classList) {
		
		AppClientFactory.getInjector().getClasspageService().v2GetUserClasses(String.valueOf(limit), String.valueOf(0),String.valueOf(Math.random()),
				new SimpleAsyncCallback<ClasspageListDo >() {
					@Override
					public void onSuccess(ClasspageListDo result) {
						classpageListDo = result;
						totalHitCount=result.getTotalHitCount();
												
						lblLoading.setVisible(false);
						isApiCalling = false;
						if(classList!=null){
							resultSize=classpageListDo.getSearchResults()!=null?classpageListDo.getSearchResults().size():0;
						}else{
							resultSize =0;
						}
						if(resultSize>0) {
							lblTitle.setVisible(false);
							classStudyList.setVisible(true);
							spanelCollectionList.setVisible(true);
							if (toClear) {
								classStudyList.clear();
								toClear = false;
								classpageList.clear();
								listClasspage.clear();
							}
							listClasspage.clear();
							for(int i = 0; i<resultSize;i++) 
							{
//								Label className = new Label("classList "+i);
								String classpageId = classpageListDo.getSearchResults().get(i).getClassUid();
								classpageList.put(classpageId, classpageListDo.getSearchResults().get(i));
								listClasspage.add(classpageId);
								/*className.setStyleName("studyNowToolTipLbl");
								if(i==0) {
									className.getElement().getStyle().setMarginTop(2, Unit.PX);
								}
								className.addClickHandler(new OnEnterClassCodeClick());
								classStudyList.add(className);*/
							}
							generateClasspageList();
						} else {
							/*offSet = tmpOffSet;
							Element element=Document.get().getElementById("lblLoading");
							if(element!=null){
								element.removeFromParent();
							}*/
//							lblTitle.setVisible(true);
						}
					}
				});
		
		
	}
	
	public void generateClasspageList(){
		classStudyList.clear();
		for (int i=0;i<listClasspage.size();i++){
			String classpageTitle = classpageList.get(listClasspage.get(i)).getTitle();
			classStudyList.add(createClasspageTitleLabel(
				classpageTitle, listClasspage.get(i), false));
		}
	}
	
	public void refreshClasslist()
	{
		AppClientFactory.getInjector().getClasspageService().v2GetUserClasses(String.valueOf(limit), String.valueOf(offSet),String.valueOf(Math.random()),
				new SimpleAsyncCallback<ClasspageListDo >() {
					@Override
					public void onSuccess(ClasspageListDo result) {
						classpageListDo = result;
						totalHitCount=result.getTotalHitCount();
						setStudyClassList(result);
					}
				});
	}
	
	
	/**
	 * 
	 * @function createClasspageTitleLabel
	 * 
	 * @created_date : Aug 15, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param classpageTitle
	 * @parm(s) : @param classpageId
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
	private Label createClasspageTitleLabel(String classpageTitle,
			final String classpageId, boolean isStatic) {
		Label titleLabel = null;
		
		if (classpageTitle.length() >=30){
			titleLabel = new Label(classpageTitle.substring(0, 30));
		}else{
			titleLabel = new Label(classpageTitle);
		}
		titleLabel.getElement().setAttribute("id", classpageId);
		if (!isStatic){
			titleLabel.setStyleName(ClasspageListPopupViewCBundle.INSTANCE.css().classpageTitleStudyHeader());
		}else{
			titleLabel.setStyleName(ClasspageListPopupViewCBundle.INSTANCE.css().classpageLoadingOnPagination());
		}
		// Set Click event for title
		titleLabel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				 OpenClasspageEdit(classpageId);
				 hide();
			}
		});
		return titleLabel;
	}
	
	
	/**
	 * 
	 * @function OpenClasspageEdit
	 * 
	 * @created_date : Aug 15, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param gooruOId
	 * 
	 * @return : void
	 * 
	 * @throws : <Mentioned if any exceptions>
	 * 
	 * 
	 * 
	 * 
	 */
	private void OpenClasspageEdit(String gooruOId) {
		setClassapageItemSeleted(gooruOId);
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", gooruOId);
		params.put("pageNum", "0");
		params.put("pageSize", "10");
		params.put("pos", "1");
		AppClientFactory.getPlaceManager().revealPlace(
				PlaceTokens.STUDENT, params);
		classCodeTxtBox.setText("");
	}
	
	/**
	 * 
	 * @function setClassapageItemSeleted
	 * 
	 * @created_date : Aug 15, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param classpageId
	 * 
	 * @return : void
	 * 
	 * @throws : <Mentioned if any exceptions>
	 * 
	 * 
	 * 
	 * 
	 */
	private void setClassapageItemSeleted(String classpageId) {
		
		for (int i = 0; i < listClasspage.size(); i++) {
			Element element=Document.get().getElementById(listClasspage.get(i));
			if(element!=null){
				element.setClassName(res.css().classpageTitleHeader());
			}
		}
		if(classpageId!=null && !classpageId.equalsIgnoreCase("")){
			Element element=Document.get().getElementById(classpageId);
			if(element!=null){
				element.setClassName(res.css().classpageTitleHeaderActive());
			}
		}
	}
	
	/**
	 * 
	 * @function setClasspageSetSelected 
	 * 
	 * @created_date : Mar 27, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param classpageId
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	private void setClasspageSetSelected(String classpageId){
		Iterator<Widget> widgets = classStudyList.iterator();
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			if (widget.getElement().getId().equalsIgnoreCase(classpageId)){
				widget.getElement().setClassName(res.css().classpageTitleHeaderActive());
			}else{
				widget.getElement().setClassName(res.css().classpageTitleHeader());
			}
		}
	}
	
	/**
	 * 
	 * @function removeClasspageItem 
	 * 
	 * @created_date : Mar 28, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param classpageId
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public void removeClasspageItem(String classpageId) {
		String nextClasspageId = null;
		int listCount = listClasspage.size();
		for (int i=0; i<listClasspage.size(); i++){			
			if (listClasspage.get(i).equalsIgnoreCase(classpageId)){
				if (i==(listCount-1)){
					if ((listCount-1) >0 ){
						nextClasspageId =  listClasspage.get(i-1);
					}else{
						nextClasspageId = null;
					}
					
				}else{
					nextClasspageId =  listClasspage.get(i+1);
				}
				listClasspage.remove(i);
				classpageList.remove(classpageId);
			}else{
				nextClasspageId=listClasspage.get(0);
			}
		}
		classStudyList.clear();
		generateClasspageList();
		if (nextClasspageId!=null){
			OpenClasspageEdit(nextClasspageId);
		}else{
			showNoClasspages();
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
		}
	}
	
	/**
	 * 
	 * @function showNoClasspages 
	 * 
	 * @created_date : Mar 28, 2014
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
	private void showNoClasspages() {
//		lblLoading.setVisible(false);
		classStudyList.setVisible(true);
		spanelCollectionList.setVisible(false);
	}
	
	private void showClasspageList() {
//		lblLoading.setVisible(false);
		classStudyList.setVisible(false);
		spanelCollectionList.setVisible(true);
		classStudyList.clear();
	}
	
	/**
	 * 
	 * @function updateTitle 
	 * 
	 * @created_date : Apr 02, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param classpageId
	 * @parm(s) : @param classpageTitle
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	
	private void updateTitle(String classpageId, String classpageTitle){
		Iterator<Widget> widgets = classStudyList.iterator();
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			if (widget.getElement().getId().equalsIgnoreCase(classpageId)){
				widget.getElement().setInnerHTML(classpageTitle);
			}
		}
		
		// Update the ClasspageObject inside StudyClasspageList object.
		
		CollectionDo classpageDo =  classpageList.get(classpageId);
		classpageDo.setTitle(classpageTitle);
		classpageList.put(classpageId, classpageDo);
		
	}

	/**
	 * @return the classStudyList
	 */
	public VerticalPanel getClassStudyList() {
		return classStudyList;
	}

	/**
	 * @return the lblTitle
	 */
	public Label getLblTitle() {
		return lblTitle;
	}
  
	public void setEnterLblVisbility(boolean isVisible) {
		enterLbl.setVisible(!isVisible);
		disabledBtn.setVisible(isVisible);
	}

	/**
	 * @return the classCodeTxtBox
	 */
	public TextBoxWithPlaceholder getClassCodeTxtBox() {
		return classCodeTxtBox;
	}
	
	
}
