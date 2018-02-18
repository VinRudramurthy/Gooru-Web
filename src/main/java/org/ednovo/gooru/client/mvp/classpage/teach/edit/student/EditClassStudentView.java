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
package org.ednovo.gooru.client.mvp.classpage.teach.edit.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;
import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.DeletePopupViewVc;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.SuccessPopupViewVc;
import org.ednovo.gooru.client.uc.H5Panel;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.suggestbox.widget.AutoSuggestForm;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;


/**
 * @fileName : EditClassStudentView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 03-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class EditClassStudentView extends BaseViewWithHandlers<EditClassStudentViewUiHandler> implements IsEditClassStudentView {


	@UiField PPanel classCodePanel,shareLnkPanel,emailAddTxt,analyPanel;

	@UiField InlineLabel classCodeTxtPanel;

	@UiField TextBox sharTxtBox,fullTxtBox;

	@UiField Button inviteBtn;

	@UiField H5Panel inviteEmailTxt,studentJoinPanel,studentPendingPanel;

	@UiField VerticalPanel tableContainer,pendingContainer;

	@UiField HTMLPanel panelSuggestBox,roasterMainConatiner/*,reportContainer,panelActions,panelCode*/;

	@UiField Label lblPleaseWait,lblErrorMessage;

	private static final String QUESTIONIMAGE = "images/question.png";

	MessageProperties i18n = GWT.create(MessageProperties.class);

	private PopupPanel toolTipPopupPanelNew = new PopupPanel();

	private PopupPanel toolTipPopupPanelNew1 = new PopupPanel();

	private PopupPanel toolTipPopupPanelNew2 = new PopupPanel();

	@UiField HTMLPanel pendindUserContainer,activeUserConatiner;

	@UiField Anchor ancPendingListSeeMore,ancActiveListSeeMore;

	@UiField Label lblActivePleaseWait,lblPendingPleaseWait;

	@UiField FlowPanel reportBox;

	private int  activeListPageNum=0;

	List<CollaboratorsDo> liCollaboratorsDos;

	private static final String SHORTEN_URL = "shortenUrl";

	private static final String DECODERAWURL="decodeRawUrl";

	ClasspageDo classpageDo;

	int currentStudentsCount=0;

	int overAllStudentsCount = 0;

	private static int studentsLimitCount = 500;

	private int pendingListTotalCount=0;

	private int pendingOffsetValue=0;

	private int pageSize = 20;

	private int activeListTotalCount=0;

	private int pendingListPageNum=0;

	private static EditClassStudentViewUiBinder uiBinder = GWT.create(EditClassStudentViewUiBinder.class);

	MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();

	String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	interface EditClassStudentViewUiBinder extends UiBinder<Widget, EditClassStudentView> {
	}

	AutoSuggestForm autoSuggetTextBox =null;

	public EditClassStudentView() {
		setWidget(uiBinder.createAndBindUi(this));
		setIds();
		sharTxtBox.addClickHandler(new TextCopyHandler());
		fullTxtBox.addClickHandler(new FullTextCopyHandler());
	}

	public void setIds(){

		classCodePanel.setText(i18n.GL0184());
		classCodePanel.getElement().setId("classCodePanelId");

		shareLnkPanel.setText(i18n.GL1594());
		shareLnkPanel.getElement().setId("sharelnkPanel");

		inviteEmailTxt.setText(i18n.GL3419());
		inviteEmailTxt.getElement().setId("inviteEmailTxtId");

		emailAddTxt.setText(i18n.GL1591());
		emailAddTxt.getElement().setId("emailAddTextId");

		inviteBtn.setText(i18n.GL3450_17());
		inviteBtn.getElement().setId("inviteBtnId");


		studentJoinPanel.setText(i18n.GL1526());
		studentJoinPanel.getElement().setId("studentJoinPanelId");

		analyPanel.setText(i18n.GL1633());
		analyPanel.getElement().setId("analyPanelId");

		Image image = new Image(QUESTIONIMAGE);
		image.getElement().setId("classCodeImageId");
		image.addMouseOverHandler(new MouseOverShowClassCodeToolTip1());
		image.addMouseOutHandler(new MouseOutHideToolTip1());


		Image image2 = new Image(QUESTIONIMAGE);
		image2.getElement().setId("sharLnkId");
		image2.addMouseOverHandler(new MouseOverShowClassCodeToolTip2());
		image2.addMouseOutHandler(new MouseOutHideToolTip2());

		lblPleaseWait.setText(i18n.GL1137());
		lblPleaseWait.getElement().setId("lblPleaseWait");
		lblPleaseWait.getElement().setAttribute("alt",i18n.GL1137());
		lblPleaseWait.getElement().setAttribute("title",i18n.GL1137());


		ancPendingListSeeMore.setText(i18n.GL0508().toLowerCase());
		ancPendingListSeeMore.getElement().setId("lnkPendingListSeeMore");
		ancPendingListSeeMore.getElement().setAttribute("alt",i18n.GL0508().toLowerCase());
		ancPendingListSeeMore.getElement().setAttribute("title",i18n.GL0508().toLowerCase());
		ancPendingListSeeMore.setVisible(false);


		ancActiveListSeeMore.setText(i18n.GL0508().toLowerCase());
		ancActiveListSeeMore.getElement().setId("lnkActiveListSeeMore");
		ancActiveListSeeMore.getElement().setAttribute("alt",i18n.GL0508().toLowerCase());
		ancActiveListSeeMore.getElement().setAttribute("title",i18n.GL0508().toLowerCase());
		ancActiveListSeeMore.setVisible(false);

		lblPendingPleaseWait.setVisible(false);
		lblActivePleaseWait.setVisible(false);

		lblPendingPleaseWait.setText(i18n.GL0339().toLowerCase());
		lblPendingPleaseWait.getElement().setId("lblPendingPleaseWait");
		lblPendingPleaseWait.getElement().setAttribute("alt",i18n.GL0339().toLowerCase());
		lblPendingPleaseWait.getElement().setAttribute("title",i18n.GL0339().toLowerCase());

		lblActivePleaseWait.setText(i18n.GL0339().toLowerCase());
		lblActivePleaseWait.getElement().setId("lblActivePleaseWait");
		lblActivePleaseWait.getElement().setAttribute("alt",i18n.GL0339().toLowerCase());
		lblActivePleaseWait.getElement().setAttribute("title",i18n.GL0339().toLowerCase());

		classCodePanel.add(image);
		shareLnkPanel.add(image2);

		studentPendingPanel.setText(i18n.GL1525());
		studentPendingPanel.getElement().setId("studentPendingPanelId");

		panelSuggestBox.getElement().setId("pnlSuggestbox");


		sharTxtBox.setReadOnly(true);
		sharTxtBox.getElement().getStyle().setBackgroundColor("#FFF");
		sharTxtBox.getElement().getStyle().setCursor(Cursor.DEFAULT);
		StringUtil.setAttributes(sharTxtBox, true);

		fullTxtBox.setReadOnly(true);
		fullTxtBox.getElement().setAttribute("style", "margin:10px 0px;background-color: #FFF;cursor: default");
		StringUtil.setAttributes(fullTxtBox, true);


		createAutoSuggestBox();


	}

	public class TextCopyHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			sharTxtBox.selectAll();
			sharTxtBox.setFocus(true);
		}

	}

	public class FullTextCopyHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			fullTxtBox.selectAll();
			fullTxtBox.setFocus(true);
		}

	}

	public void setTabVisible(boolean isVisible){
		roasterMainConatiner.setVisible(isVisible);
	}

	public class MouseOverShowClassCodeToolTip1 implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanelNew.clear();
			toolTipPopupPanelNew.setWidget(new GlobalToolTip(i18n.GL2090()));
			toolTipPopupPanelNew.setStyleName("");
			toolTipPopupPanelNew.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 14, event.getRelativeElement().getAbsoluteTop());
			toolTipPopupPanelNew.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanelNew.show();
		}

	}

	public class MouseOutHideToolTip1 implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanelNew.hide();
		}
	}

	public class MouseOverShowClassCodeToolTip2 implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanelNew1.clear();
			toolTipPopupPanelNew1.setWidget(new GlobalToolTip(i18n.GL2091()));
			toolTipPopupPanelNew1.setStyleName("");
			toolTipPopupPanelNew1.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 14, event.getRelativeElement().getAbsoluteTop());
			toolTipPopupPanelNew1.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanelNew1.show();
		}

	}

	public class MouseOutHideToolTip2 implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanelNew1.hide();
		}
	}

	@Override
	public void createAutoSuggestBox() {
		panelSuggestBox.setStyleName("auto_suggest");
		autoSuggetTextBox = new AutoSuggestForm(oracle) {

			@Override
			public void onSubmit(DomEvent<EventHandler> event) {
				// Nothing to do....
			}

			@Override
			public void dataObjectModel(String text) {
				if (text.trim().length() > 0){
					AppClientFactory.getInjector().getClasspageService().getSuggestionByName(text.trim(), new SimpleAsyncCallback<List<String>>() {

						@Override
						public void onSuccess(List<String> lstOfSuggestedCollaborators) {
							autoSuggetTextBox.clearAutoSuggestData();
							for (String lstCollab : lstOfSuggestedCollaborators){
								oracle.add(lstCollab);
							}
							autoSuggetTextBox.setAutoSuggest(oracle);
						}
					});
				}
			}

			@Override
			public void keyPressOnTextBox(KeyPressEvent event) {
				lblErrorMessage.setVisible(false);
			}

			@Override
			public void errorMsgVisibility(boolean visibility, String emailId) {
				if (visibility){
					showErrorMessage(StringUtil.generateMessage(i18n.GL1019(), emailId));
				}else{
					lblErrorMessage.setVisible(false);
				}
			}

		};
		autoSuggetTextBox.clearAutoSuggestData();
		panelSuggestBox.clear();
		panelSuggestBox.add(autoSuggetTextBox);
		autoSuggetTextBox.getTxtInput().getTxtInputBox().setFocus(true);
	}

	public void showErrorMessage(String errorMessage){
		lblErrorMessage.setText(errorMessage);
		lblErrorMessage.getElement().setAttribute("alt",errorMessage);
		lblErrorMessage.getElement().setAttribute("title",errorMessage);
		lblErrorMessage.setVisible(true);
	}

	public class MouseOverShowClassCodeToolTip3 implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanelNew2.clear();
			toolTipPopupPanelNew2.setWidget(new GlobalToolTip(i18n.GL2092()));
			toolTipPopupPanelNew2.setStyleName("");
			toolTipPopupPanelNew2.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 14, event.getRelativeElement().getAbsoluteTop());
			toolTipPopupPanelNew2.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanelNew2.show();
		}

	}

	public class MouseOutHideToolTip3 implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanelNew2.hide();
		}
	}


	public class EditClassStudentTabHandler implements ClickHandler{

		String subView;
		LiPanel liPanel;

		public EditClassStudentTabHandler(String subView,LiPanel liPanel){
			this.subView=subView;
			this.liPanel=liPanel;
		}

		@Override
		public void onClick(ClickEvent event) {
			/*roasterPanel.removeStyleName(CssTokens.ACTIVE);
			reportPanel.removeStyleName(CssTokens.ACTIVE);*/
			liPanel.addStyleName(CssTokens.ACTIVE);
			PlaceRequest request = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
			request = request.with(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW, subView);
			AppClientFactory.getPlaceManager().revealPlace(request);
		}

	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.student.IsEditClassStudentView#setNavigationTab()
	 */
	@Override
	public void setNavigationTab() {
		String subView = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW,"");
		if(subView.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASS_STUDENTS_ROASTER)){
			setTabVisible(true);
			//roasterPanel.setStyleName(CssTokens.ACTIVE);
		}else{
			setTabVisible(false);
			//reportPanel.setStyleName(CssTokens.ACTIVE);
		}
	}

	@Override
	public void setReportView() {

	}

	private void setReportVisiblity(boolean isVisible) {
		reportBox.setVisible(isVisible);
		roasterMainConatiner.setVisible(!isVisible);
		//reportContainer.setVisible(false);
	}

	@Override
	public void setRoasterView() {
		/*roasterPanel.addStyleName(CssTokens.ACTIVE);
		reportPanel.removeStyleName(CssTokens.ACTIVE);*/
		setReportVisiblity(false);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.student.IsEditClassStudentView#setClassView(org.ednovo.gooru.application.shared.model.content.ClasspageDo)
	 */
	@Override
	public void setClassView(ClasspageDo classpageDo) {
		this.classpageDo=classpageDo;
		classCodeTxtPanel.setText(classpageDo.getClassCode());
		activeListPageNum=0;
		activeListTotalCount=0;
		pendingListPageNum=0;
		pendingListTotalCount=0;
		pendingOffsetValue=0;


		//getUiHandlers().generateShareLink(classpageDo.getClassUid());
		//getUiHandlers().getActiveMembersListByCollectionId(classpageDo.getClassUid(),pageSize*activeListPageNum, pageSize, "active",true,true,false);	//this will callback displayActiveMembersList method ....



	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.student.IsEditClassStudentView#setShortenUrl(java.util.Map)
	 */
	@Override
	public void setShortenUrl(Map<String, String> shortenUrl) {
		if (shortenUrl != null && shortenUrl.containsKey(SHORTEN_URL)) {
			sharTxtBox.setText(shortenUrl.get(SHORTEN_URL));
		}
		if(shortenUrl != null && shortenUrl.containsKey(DECODERAWURL)){
			fullTxtBox.setText(shortenUrl.get(DECODERAWURL));
		}

	}

	@UiHandler("inviteBtn")
	public void OnClickInvite(ClickEvent event){
		lblPleaseWait.setVisible(true);
		inviteBtn.setVisible(false);
		String studentsEmailIds = autoSuggetTextBox.getSelectedItemsAsString();
		String emailIds[] = studentsEmailIds.trim().split("\\s*,\\s*");
		List<String> lstEmailID = new ArrayList<String>();
		for (int i=0; i<emailIds.length; i++){
			lstEmailID.add("\""+emailIds[i].toLowerCase().trim()+"\"");
		}
		if (studentsEmailIds != null && studentsEmailIds.equalsIgnoreCase("")){
			lblPleaseWait.setVisible(false);
			inviteBtn.setVisible(true);
			return;
		}

		currentStudentsCount = emailIds.length + overAllStudentsCount;
		if (currentStudentsCount > studentsLimitCount){
			lblPleaseWait.setVisible(false);
			inviteBtn.setVisible(true);
			showErrorMessage(StringUtil.generateMessage(i18n.GL1523(), ""+studentsLimitCount));
			return;
		}

		//Check for Valid Email ID format
		boolean from;
		for (int i=0; i<emailIds.length; i++){
			String emailID = emailIds[i].toLowerCase().trim();
			from = emailID.matches(EMAIL_REGEX);
			if (!from){
				lblPleaseWait.setVisible(false);
				inviteBtn.setVisible(true);
				showErrorMessage(StringUtil.generateMessage(i18n.GL1019(), emailID));
				return;
			}
		}
		if (AppClientFactory.getLoggedInUser().getEmailId()!=null){
			boolean isValid=true;
			for (int i=0; i<emailIds.length; i++){
				String emailId = emailIds[i].toLowerCase().trim();
				if (emailId.equalsIgnoreCase(AppClientFactory.getLoggedInUser().getEmailId())){
					lblPleaseWait.setVisible(false);
					inviteBtn.setVisible(true);
					showErrorMessage(i18n.GL1524());
					isValid = false;
					break;
				}
			}
			if (!isValid){
				return;
			}
		}

		inviteBtn.getElement().addClassName("disabled");
		inviteBtn.setEnabled(false);
		//Call API to add the Student to the class.
		String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		if(classId != null){
			getUiHandlers().addStudents(classId, lstEmailID);	// this will callback the displayPendingMembersList method.
		}
	}

	@Override
	public void displayInvitationSuccessPopUp(int listSize) {
		Window.enableScrolling(false);
		SuccessPopupViewVc success = new SuccessPopupViewVc() {

			@Override
			public void onClickPositiveButton(ClickEvent event) {
				// TODO Auto-generated method stub
				this.hide();
				if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.SEARCH_COLLECTION) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.SEARCH_RESOURCE)){
					Window.enableScrolling(false);
				}else{
					Window.enableScrolling(true);
				}
			}

		};

		success.setPopupTitle(i18n.GL1556());
		if (listSize>1){
			success.setDescText(i18n.GL1557());
		}else{
			success.setDescText(i18n.GL1360());
		}
		success.setPositiveButtonText(i18n.GL0190());
		success.center();
		success.show();

	}

	@Override
	public void displayPendingMembersList(List<CollaboratorsDo> lstPendingMembers, boolean isNew, int totalCount,boolean increasePageNum,boolean insertTop) {
		lblPleaseWait.setVisible(false);
		lblErrorMessage.setVisible(false);
		if(!insertTop || isNew){
			this.pendingListTotalCount=totalCount;
		}
		if(increasePageNum){
			pendingOffsetValue=pendingOffsetValue+pageSize;
		}
		/*if(pendingListTotalCount==0&&activeListTotalCount==0){
			Label noActiveStudents = new Label(i18n.GL1527());
			noActiveStudents.getElement().addClassName("noActiveClassStudents");
			pendingContainer.add(noActiveStudents);
			pendindUserContainer.setVisible(false);
			ancPendingListSeeMore.setVisible(false);
		}else */if(pendingListTotalCount==0){
			ancPendingListSeeMore.setVisible(false);
			pendindUserContainer.setVisible(false);
			pendingContainer.clear();
		}else{
			lblPendingPleaseWait.setVisible(false);
			pendindUserContainer.setVisible(true);
			if(!isNew){
				pendingContainer.clear();
			}
			for (int k=0; k<lstPendingMembers.size();k++){
				if(insertTop){
					pendingOffsetValue++;
					pendingListTotalCount++;
				}
				if((pendingOffsetValue)< pendingListTotalCount){
					ancPendingListSeeMore.setVisible(true);
				}
				else{
					ancPendingListSeeMore.setVisible(false);
				}
				insertPendingUserAfterDeletion(lstPendingMembers.get(k),isNew,totalCount,k,insertTop);
				enableInvite();
			}
		}
	}


	@Override
	 public void insertPendingUserAfterDeletion(final CollaboratorsDo lstPendingMembers, boolean isNew, int totalCount, int intPos,boolean insertAtTop){
				final MembersViewVc membersViewVc = new MembersViewVc(AppClientFactory.getCurrentPlaceToken(), isNew, lstPendingMembers, classpageDo,intPos,"pending") {
				public void removeUserWidget(){
					String emailId = lstPendingMembers.getEmail();
					getUiHandlers().removePendingUserFromCalss(classpageDo, emailId,0, true,this);
				}
				@Override
				public void setCollabCount(int count, String type) {}
				@Override
				public void setStudentsListContainer(ClickEvent event) {
					Window.enableScrolling(false);
					AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
					DeletePopupViewVc delete = new DeletePopupViewVc() {
						@Override
						public void onClickPositiveButton(ClickEvent event) {
							removeUserWidget();
							Window.enableScrolling(true);
							AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
							hide();
						}

						@Override
						public void onClickNegitiveButton(ClickEvent event) {
							Window.enableScrolling(true);
							AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
							hide();
						}
					};
					delete.setPopupTitle(i18n.GL1548());
					delete.setNotes(i18n.GL0748());
					delete.setDescText(StringUtil.generateMessage(i18n.GL1547(), lblUserName.getText() != null && !lblUserName.getText().equalsIgnoreCase("") ? "<i>"+lblUserName.getText()+"</i>" : "<i>"+lblEmailId.getText()+"</i>"));
					delete.setPositiveButtonText(i18n.GL0190());
					delete.setNegitiveButtonText(i18n.GL0142());
					delete.setDeleteValidate("delete");
					delete.setPixelSize(450, 360);
					delete.show();
					delete.center();
				}
			};
			if(insertAtTop){
				pendingContainer.insert(membersViewVc,0);
			}else{
				pendingContainer.add(membersViewVc);
			}
	 }

	@Override
	public Button getInviteButton() {
		return inviteBtn;
	}


	@Override
	public Label getLblPleaseWait() {
		return lblPleaseWait;
	}

	@UiHandler("ancPendingListSeeMore")
	public void onClickPendingListSeeMore(ClickEvent event){
		lblPendingPleaseWait.setVisible(true);
		ancPendingListSeeMore.setVisible(false);
		System.out.println("classpageDo.getClassUid() 1 : "+classpageDo.getClassUid());
		getUiHandlers().getMembersListByCollectionId(classpageDo.getClassUid(),  pendingOffsetValue, pageSize, "pending",true,true);	//this will callback displayPendingMembersList method ....
	}
	@UiHandler("ancActiveListSeeMore")
	public void onClickActiveListSeeMore(ClickEvent event){
		lblActivePleaseWait.setVisible(true);
		ancActiveListSeeMore.setVisible(false);
		int offset=(pageSize*activeListPageNum);
		getUiHandlers().getActiveMembersListByCollectionId(classpageDo.getClassUid(),  offset, pageSize, "active",true,false,true);	//this will callback displayActiveMembersList method ....
	}

	@Override
	public void displayActiveMembersList(List<CollaboratorsDo> lstActiveMembers, boolean isNew, int totalCount,boolean increasePageNum) {
		this.liCollaboratorsDos=lstActiveMembers;
		lblPleaseWait.setVisible(false);
		lblErrorMessage.setVisible(false);
		if(increasePageNum){
			activeListPageNum++;
		}
		this.activeListTotalCount=totalCount;
		if(activeListTotalCount==0){
			ancActiveListSeeMore.setVisible(false);
			tableContainer.clear();
			Label noActiveStudents = new Label(i18n.GL1527());
			noActiveStudents.getElement().addClassName("noActiveClassStudents");
			tableContainer.add(noActiveStudents);
		}else{
			if(!isNew){
				tableContainer.clear();
			}
			for (int k=0; k<lstActiveMembers.size();k++){
				insertActiveUserAfterDeletion(lstActiveMembers.get(k),isNew,totalCount,k);
				if((pageSize*activeListPageNum)<activeListTotalCount){
					ancActiveListSeeMore.setVisible(true);
				}else{
					ancActiveListSeeMore.setVisible(false);
				}
				enableInvite();
			}
		}
	}

	@Override
	 public void insertActiveUserAfterDeletion(final CollaboratorsDo lstActiveMembers, boolean isNew, int totalCount, int intPos){
		MembersViewVc membersViewVc = new MembersViewVc(AppClientFactory.getCurrentPlaceToken(), isNew, lstActiveMembers, classpageDo,intPos,"active") {
			public void removeActiveUserObject(){
				ArrayList<String> arrayEmailId = new ArrayList<String>();
				arrayEmailId.add('"'+lstActiveMembers.getEmail()+'"');
				String gooruUid = lstActiveMembers.getGooruUId();
				getUiHandlers().removeActiveUserFromClass(classpageDo,gooruUid,0,false,this);
			}
			@Override
			public void setCollabCount(int count, String type) {}
			@Override
			public void setStudentsListContainer(ClickEvent event) {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				DeletePopupViewVc delete = new DeletePopupViewVc() {
					@Override
					public void onClickPositiveButton(ClickEvent event) {
						removeActiveUserObject();
						Window.enableScrolling(true);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
						hide();
					}

					@Override
					public void onClickNegitiveButton(ClickEvent event) {
						Window.enableScrolling(true);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
						hide();

					}
				};
				delete.setPopupTitle(i18n.GL1548());
				delete.setNotes(i18n.GL0748());
				delete.setDescText(StringUtil.generateMessage(i18n.GL1547(), lblUserName.getText() != null && !lblUserName.getText().equalsIgnoreCase("") ? "<i>"+lblUserName.getText()+"</i>" : "<i>"+lblEmailId.getText()+"</i>"));
				delete.setPositiveButtonText(i18n.GL0190());
				delete.setNegitiveButtonText(i18n.GL0142());
				delete.setDeleteValidate("delete");
				delete.setPixelSize(450, 360);
				delete.show();
				delete.center();
			}
		};
		//this validation added for checking duplicate emailIds in pending list
		lblActivePleaseWait.setVisible(false);
		tableContainer.add(membersViewVc);

	 }



	@Override
	public void enableInvite() {
		lblPleaseWait.setVisible(false);
		inviteBtn.setVisible(true);

		if (overAllStudentsCount >= studentsLimitCount){
			inviteBtn.setEnabled(false);
			inviteBtn.getElement().addClassName("disabled");
			//Enable this line if we need to disable after max number of user/students invited.
			//panelActions.getElement().addClassName(res.css().buttonTooltip());
		}else{
			inviteBtn.setEnabled(true);
			inviteBtn.getElement().removeClassName("disabled");

			//panelActions.getElement().removeClassName(res.css().buttonTooltip());
		}

		createAutoSuggestBox();
	}

	public void getPendingMembersList(){
		System.out.println("classpageDo.getClassUid() : "+classpageDo.getClassUid());
		if (classpageDo.getClassUid() != null){
			getUiHandlers().getMembersListByCollectionId(classpageDo.getClassUid(), 0, pageSize, "pending",true,false);	//this will callback displayPendingMembersList method ....
		}else{
			AppClientFactory.printInfoLogger("Class Uid is null @ Edit Class Student View @ getPendingMembersList....");
		}
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.student.IsEditClassStudentView#removePendiUserWidget(org.ednovo.gooru.client.mvp.classpage.teach.edit.student.MembersViewVc, boolean)
	 */

	public void removePendingUserWidget(MembersViewVc membersViewVc,	boolean isPendingList) {
		membersViewVc.removeFromParent();
		if(isPendingList){
			if(pendingContainer.getWidgetCount()==0){
				ancPendingListSeeMore.setVisible(false);
			}
		}else{
			if(tableContainer.getWidgetCount()== 0){
				ancActiveListSeeMore.setVisible(false);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.student.IsEditClassStudentView#setReportDataView()
	 */
	@Override
	public void setReportDataView() {
		reportBox.setVisible(false);
		roasterMainConatiner.setVisible(false);
		//reportContainer.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.student.IsEditClassStudentView#clearAllErrorLable()
	 */
	@Override
	public void clearAllErrorLabel() {
		createAutoSuggestBox();
        lblErrorMessage.setVisible(false);
	}



}
