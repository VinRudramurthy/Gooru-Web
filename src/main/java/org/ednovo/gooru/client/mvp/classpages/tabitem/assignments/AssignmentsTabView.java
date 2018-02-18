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
package org.ednovo.gooru.client.mvp.classpages.tabitem.assignments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.AssignmentDo;
import org.ednovo.gooru.application.shared.model.content.AssignmentsSearchDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.ResourceDo;
import org.ednovo.gooru.application.shared.model.content.TaskDo;
import org.ednovo.gooru.application.shared.model.content.TaskResourceAssocDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.classpages.assignments.AddAssignmentContainerCBundle;
import org.ednovo.gooru.client.mvp.classpages.event.GetStudentJoinListEvent;
import org.ednovo.gooru.client.mvp.classpages.event.GetStudentJoinListHandler;
import org.ednovo.gooru.client.mvp.classpages.tabitem.assignments.collections.CollectionsView;
import org.ednovo.gooru.client.mvp.dnd.IsDraggableMirage;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.settings.CustomAnimation;
import org.ednovo.gooru.client.mvp.shelf.DeleteConfirmPopupVc;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.CollectionEditResourceCBundle;
import org.ednovo.gooru.client.uc.DateBoxUc;
import org.ednovo.gooru.client.uc.ErrorLabelUc;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @fileName : AssignmentsTabView.java
 * 
 * @description :
 * 
 * 
 * @version : 5.5 
 * 
 * @date: May 17, 2013
 * 
 * @Author Gooru Team
 * 
 * @Reviewer:
 */
public class AssignmentsTabView extends ChildView<AssignmentsTabPresenter>
		implements IsAssignmentsTabView{

	@UiField(provided = true)
	AssignmentsTabViewCBundle res;

	AddCollectionsPopupVc addCollections = null;

	List<Integer> collectionItems;

	private String collId;

	private AssignmentsSearchDo assignmentsSearchDo;

	@UiField
	Label dueDateLbl, deleteLabel, cancelLabel;
	
	@UiField Button btnEditAssignment;
	
	@UiField
	HTMLPanel actionPanel, inLinePanel,displayMetaInfoPanel;
	@UiField
	HTMLPanel collectionsPanel, assignmentContainerPanel;
	@UiField
	HTMLPanel assignmentTitleTxtPanel, assignmentTitleLblPanel;
	@UiField
	HTMLPanel loadingPanel;

	@UiField
	Button btnAddCollectionToAssign;
	
	@UiField
	Label mandatoryTitleLabel, mandatoryDueDateLabel, mandatoryDirectionLabel, lblDueDateDisplay;
	@UiField
	Label clickToExpandLabel;

	@UiField
	HTML assignmentDescriptionLbl, assignmentTitelLbl;
	@UiField
	TextBox assignmentTitleTxt;// , assignmentDueDateTxt;
	@UiField
	TextArea assignmentDescriptionTxtArea;
	@UiField
	SimplePanel dateSimPanel;
	@UiField
	ErrorLabelUc dateValidationUc;
	@UiField
	HTMLEventPanel clickEventPanel,asignmentTiltleContainer;

	private DateBoxUc dateBoxUc;

	DeleteConfirmPopupVc deleteConfirmVc = null;
	
	private CollectionsView cv;

	private boolean isInEditMode;

	int totalSize;
	int totalSelfCollection;
	// Used for Pagination
	private Integer pageNumber = 1;
	private Integer pageSize = 20;

	private boolean isExpandable = false;

	String assignmentId = null;

	static int totalCollection;
	
	private int activeMemberCounter;

	private static AssignmentsTabViewUiBinder uiBinder = GWT
			.create(AssignmentsTabViewUiBinder.class);
	
	static MessageProperties i18n = GWT.create(MessageProperties.class);
	
	private static final String MANDATORY_TITLE = i18n.GL0173();
	private static final String MANDATORY_DUEDATE = i18n.GL0235();
	private static final String MANDATORY_DIRECTIONS =i18n.GL0236();

	private static final String CHARACTERS_LIMIT =i18n.GL0143();

	interface AssignmentsTabViewUiBinder extends
			UiBinder<Widget, AssignmentsTabView> {
	}

	/**
	 * Class constructor
	 * 
	 * @param AssignmentsSearchDo
	 *            and isExpandable (to open the tab by default) instance of
	 *            {@link CollectionDo}
	 */
	public AssignmentsTabView(AssignmentsSearchDo assignmentsSearchDo,
			boolean isExpandable) {

		this.isExpandable = isExpandable;

		res = AssignmentsTabViewCBundle.INSTANCE;
		AssignmentsTabViewCBundle.INSTANCE.css().ensureInjected();
		initWidget(uiBinder.createAndBindUi(this));

		setPresenter(new AssignmentsTabPresenter(this));
		this.assignmentsSearchDo = assignmentsSearchDo;

		btnAddCollectionToAssign
				.addClickHandler(new OnClickAddCollectionToAssignment());

		asignmentTiltleContainer.addMouseOutHandler(new ActionPanelOut());
		asignmentTiltleContainer.addMouseOverHandler(new ActionPanelHover());

		clickEventPanel.addClickHandler(new ActionPanelClick());
		clickToExpandLabel.addClickHandler(new ActionPanelClick());

		assignmentTitleTxt.getElement().setAttribute("maxlength", "50");
		assignmentDescriptionTxtArea.getElement().setAttribute("maxlength",
				"400");
		StringUtil.setAttributes(assignmentTitleTxt, true);

		assignmentTitleTxt.addKeyUpHandler(new TitleKeyUpHandler());
		assignmentDescriptionTxtArea
				.addKeyUpHandler(new DirectionsKeyUpHandler());
		StringUtil.setAttributes(assignmentDescriptionTxtArea, true);

		dateBoxUc = new DateBoxUc(false,false,false);
		dateSimPanel.add(dateBoxUc);
		dateValidationUc.setStyleName(AddAssignmentContainerCBundle.INSTANCE
				.css().registerErrorLabel());

		dateBoxUc.getDateBox().addFocusHandler(new OnDateFocus());
		dateBoxUc.getDateBox().addBlurHandler(new OnDateBlur());
		dateBoxUc.addDomHandler(new OnDateFocus(), FocusEvent.getType());
		dateBoxUc.getDoneButton().addClickHandler(new OnDoneClick());

		dateValidationUc.setVisible(false);

		setUiElements(); // Setting the UI contents

		hideShowControls(false);
		hideShowLabels(true);
		cancelLabel.setVisible(false);
		actionPanel.setVisible(false);
		clickToExpandLabel.setVisible(false);
		assignmentId = assignmentsSearchDo.getTask().getGooruOid();

		mandatoryTitleLabel.setVisible(false);
		mandatoryDueDateLabel.setVisible(false);
		mandatoryDirectionLabel.setVisible(false);

		showPanel(true);

		loadingPanel.setVisible(true);

		// Open tab based on variable.
		if (isExpandable) {
			inLinePanel.setStyleName(res.css().displayInlinePanel());
			inLinePanel.getElement().getStyle().setDisplay(Display.BLOCK);
			getPresenter().getAssignmentCollections(assignmentId);
			// new CustomAnimation(inLinePanel).run(300);
		} else {
			inLinePanel.getElement().getStyle().setDisplay(Display.NONE);
		}
		deleteLabel.getElement().setId("lblDelete");
		btnAddCollectionToAssign.getElement().setId("btnAddCollectionToAssign");
		btnEditAssignment.getElement().setId("btnEditAssignment");
		assignmentTitleTxt.getElement().setId("txtAssignmentTitle");
		
		
		assignmentDescriptionTxtArea.getElement().setId("tatDescription");
		cancelLabel.getElement().setId("lblCancel");
		btnEditAssignment.setText(i18n.GL0140());
		btnEditAssignment.getElement().setAttribute("alt",i18n.GL0140());
		btnEditAssignment.getElement().setAttribute("title",i18n.GL0140());
		
		deleteLabel.setText(i18n.GL0237());
		deleteLabel.getElement().setAttribute("alt",i18n.GL0237());
		deleteLabel.getElement().setAttribute("title",i18n.GL0237());
		
		cancelLabel.setText(i18n.GL0142());
		cancelLabel.getElement().setAttribute("alt",i18n.GL0142());
		cancelLabel.getElement().setAttribute("title",i18n.GL0142());
		
		mandatoryTitleLabel.setText(MANDATORY_TITLE);
		mandatoryTitleLabel.getElement().setAttribute("alt",MANDATORY_TITLE);
		mandatoryTitleLabel.getElement().setAttribute("title",MANDATORY_TITLE);
		
		lblDueDateDisplay.setText(i18n.GL0238() +i18n.GL_SPL_SEMICOLON()+" ");
		lblDueDateDisplay.getElement().setId("lblDueDateDisplay");
		lblDueDateDisplay.getElement().setAttribute("alt",i18n.GL0238());
		lblDueDateDisplay.getElement().setAttribute("title",i18n.GL0238());
		
		mandatoryDueDateLabel.setText(MANDATORY_DUEDATE);
		mandatoryDueDateLabel.getElement().setId("lblMandatoryDueDate");
		mandatoryDueDateLabel.getElement().setAttribute("alt",MANDATORY_DUEDATE);
		mandatoryDueDateLabel.getElement().setAttribute("title",MANDATORY_DUEDATE);
		
		mandatoryDirectionLabel.setText(MANDATORY_DIRECTIONS);
		mandatoryDirectionLabel.getElement().setId("lblMandatoryDirection");
		mandatoryDirectionLabel.getElement().setAttribute("alt",MANDATORY_DIRECTIONS);
		mandatoryDirectionLabel.getElement().setAttribute("title",MANDATORY_DIRECTIONS);
		
		btnAddCollectionToAssign.setText(i18n.GL0239());
		btnAddCollectionToAssign.getElement().setAttribute("alt",i18n.GL0239());
		btnAddCollectionToAssign.getElement().setAttribute("title",i18n.GL0239());
		
		clickToExpandLabel.setText(i18n.GL0241());
		clickToExpandLabel.getElement().setId("lblClickToExpand");
		clickToExpandLabel.getElement().setAttribute("alt",i18n.GL0241());
		clickToExpandLabel.getElement().setAttribute("title",i18n.GL0241());
		
		asignmentTiltleContainer.getElement().setId("epnlAsignmentTiltleContainer");
		assignmentTitleTxtPanel.getElement().setId("pnlAssignmentTitleTxt");
		clickEventPanel.getElement().setId("epnlClickEventPanel");
		assignmentTitleLblPanel.getElement().setId("pnlAssignmentTitleLbl");
		assignmentTitelLbl.getElement().setId("htmlAssignmentTitel");
		actionPanel.getElement().setId("pnlAction");
		inLinePanel.getElement().setId("pnlInline");
		mandatoryTitleLabel.getElement().setId("lblMandatoryTitle");
		displayMetaInfoPanel.getElement().setId("pnlDisplayMetaInfo");
		dueDateLbl.getElement().setId("lblDueDate");
		dateSimPanel.getElement().setId("spnlDateSim");
		dateValidationUc.getElement().setId("errlblDateValidationUc");
		assignmentDescriptionLbl.getElement().setId("htmlAssignmentDescription");
		assignmentContainerPanel.getElement().setId("pnlAssignmentContainer");
		collectionsPanel.getElement().setId("pnlCollections");
		loadingPanel.getElement().setId("pnlLoading");
		
	}

	/**
	 * Validate Assignment Date
	 * 
	 * @return true if valid
	 */
	private boolean hasValidateDate(String dueDate) {

		boolean isValid = true;
		if (dueDate == null || (dueDate != null && dueDate.isEmpty())) {
			dateBoxUc.addStyleName(AddAssignmentContainerCBundle.INSTANCE.css()
					.gooruDateBoxError());
			dateBoxUc.getDateBox().addStyleName(
					AddAssignmentContainerCBundle.INSTANCE.css()
							.gooruDateError());
			dateValidationUc.setText("");
			dateValidationUc.setVisible(true);
			isValid = false;
		}
		return isValid;
	}

	/*
	 * override classes/method for Data picker.
	 */
	private class OnDateFocus implements FocusHandler {
		@Override
		public void onFocus(FocusEvent event) {
			dateBoxUc.removeStyleName(AddAssignmentContainerCBundle.INSTANCE
					.css().gooruDateBoxError());
			dateBoxUc.getDateBox().removeStyleName(
					AddAssignmentContainerCBundle.INSTANCE.css()
							.gooruDateError());
			if (dateValidationUc.isVisible()) {
				dateValidationUc.setVisible(false);
			}

		}
	}
	/**
	 * 
	 * @fileName : AssignmentsTabView.java
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
	private class OnDoneClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (!(dateBoxUc.getValue() == null || dateBoxUc.getDateBox()
					.getText().isEmpty())
					&& dateBoxUc.hasValidateDate()) {
				Date date = dateBoxUc.getValue();

			} else {
				dateBoxUc.getDatePickerUc().hide();
			}
		}
	}
	/**
	 * 
	 * @fileName : AssignmentsTabView.java
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
	private class OnDateBlur implements BlurHandler {
		@Override
		public void onBlur(BlurEvent event) {
			dateBoxUc.removeStyleName(AddAssignmentContainerCBundle.INSTANCE
					.css().gooruDateBoxError());
			dateBoxUc.getDateBox().removeStyleName(
					AddAssignmentContainerCBundle.INSTANCE.css()
							.gooruDateError());
		}
	}
	/**
	 * 
	 * @function hideShowControls 
	 * 
	 * @created_date : 07-Dec-2014
	 * 
	 * @description
	 * 	To hide and show controls (parms visibility)
	 * 
	 * @parm(s) : @param visibility
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	private void hideShowControls(boolean visibility) {
		assignmentTitleTxt.setVisible(visibility);
		dateBoxUc.setVisible(visibility);
		assignmentDescriptionTxtArea.setVisible(visibility);
		assignmentTitleTxtPanel.setVisible(visibility);
		assignmentTitleTxt.setFocus(true);
	}

	/**
	 * 
	 * @function hideShowLabels 
	 * 
	 * @created_date : 07-Dec-2014
	 * 
	 * @description
	 * 	To hide and show labels (parms visibility)
	 * 
	 * @parm(s) : @param visibility
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	private void hideShowLabels(boolean visibility) {
		assignmentTitelLbl.setVisible(visibility);
		dueDateLbl.setVisible(visibility);
		assignmentDescriptionLbl.setVisible(visibility);
		assignmentTitleLblPanel.setVisible(visibility);
	}

	
	/**
	 * 
	 * @function setUiElements 
	 * 
	 * @created_date : 07-Dec-2014
	 * 
	 * @description
	 * 	Display the content to UI
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
	private void setUiElements() {
		
		String taskTitle = assignmentsSearchDo.getTask().getTitle().replaceAll("%20"," ");
		String dueDate = assignmentsSearchDo.getTask().getPlannedEndDate() !=null ? assignmentsSearchDo.getTask().getPlannedEndDate() : "";
		String description = assignmentsSearchDo.getTask().getDescription() !=null ? assignmentsSearchDo.getTask().getDescription().replaceAll("%20"," ") : "";
		if(assignmentTitelLbl.getText()==null || assignmentTitelLbl.getText().equalsIgnoreCase("")){
		     assignmentTitelLbl.setHTML(taskTitle.trim());
		     assignmentTitelLbl.getElement().setAttribute("alt",taskTitle.trim());
		     assignmentTitelLbl.getElement().setAttribute("title",taskTitle.trim());
		     assignmentTitleTxt.setText(taskTitle.trim());
		     assignmentTitleTxt.getElement().setAttribute("alt",taskTitle.trim());
				assignmentTitleTxt.getElement().setAttribute("title",taskTitle.trim());
        }else{
        	assignmentTitelLbl.setHTML(assignmentTitelLbl.getText().trim());
        	  assignmentTitelLbl.getElement().setAttribute("alt",assignmentTitelLbl.getText().trim());
 		     assignmentTitelLbl.getElement().setAttribute("title",assignmentTitelLbl.getText().trim());
        	assignmentTitleTxt.setText(assignmentTitelLbl.getText().trim());
        	assignmentTitleTxt.getElement().setAttribute("alt",assignmentTitelLbl.getText().trim());
			assignmentTitleTxt.getElement().setAttribute("title",assignmentTitelLbl.getText().trim());
        }
		
		dueDateLbl.setText(dueDate);
		dueDateLbl.getElement().setAttribute("alt",dueDate);
		dueDateLbl.getElement().setAttribute("title",dueDate);
		dateBoxUc.getDateBox().setText(dueDate);
		assignmentDescriptionLbl.setHTML(description);
		assignmentDescriptionTxtArea.setText(description);
	}

	
	/**
	 * 
	 * @fileName : AssignmentsTabView.java
	 *
	 * @description : 
	 *	Display add collections popup
	 *
	 * @version : 1.0
	 *
	 * @date: 07-Dec-2014
	 *
	 * @Author Gooru Team
	 *
	 * @Reviewer:
	 */
	public class OnClickAddCollectionToAssignment implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
		
			addCollections = new AddCollectionsPopupVc() {
				// to retrive all users collections and display
				@Override
				public void populateUserCollections() {
					pageNumber = 1;
					getPresenter().getUserColletionsList(pageSize, pageNumber);

				}

				@Override
				@UiHandler("btnAdd")
				public void onAddClick(ClickEvent clickEvent) {
					if(totalSelfCollection==0){
						AppClientFactory.fireEvent(new GetStudentJoinListEvent(activeMemberCounter));
					}
					if (addCollections.collectionFirstElement.getText() == "Please choose one of the following..."
							|| addCollections.collectionFirstElement
									.getText()
									.equalsIgnoreCase(
											"Please choose one of the following...")) {

						addCollections.getMandatorySelectCollectionLbl()
								.setText(i18n.GL1134());
						addCollections.getMandatorySelectCollectionLbl()
								.setVisible(true);
						return;
					} else {

						String assignmentId = assignmentsSearchDo.getTask()
								.getGooruOid();

						String collectionId = addCollections.collectionFirstElement
								.getElement().getId();
						TaskResourceAssocDo taskResourceAssocDo = new TaskResourceAssocDo();
						ResourceDo resourceDo = new ResourceDo();
						resourceDo.setGooruOid(collectionId);
						taskResourceAssocDo.setResource(resourceDo);
						// Api call for adding Collection to Assignment
						AppClientFactory
								.getInjector()
								.getClasspageService()
								.v2AddCollectionToAssignment(
										assignmentId,
										taskResourceAssocDo,
										new SimpleAsyncCallback<TaskResourceAssocDo>() {
											@Override
											public void onSuccess(
													TaskResourceAssocDo result) {
												loadingPanel.setVisible(false);
												insertCollectionToAssignment(result
														.getResource());

												hide();
												Window.enableScrolling(true);
												AppClientFactory
														.fireEvent(new SetHeaderZIndexEvent(
																0, true));
												if (result.getSequence() >= 10) {
													disableAddNewCollection();
												}
												if (result.getSequence() > 0) {
													showPanel(true);
												}
											}
										});
					}
				}
			};

		}

	}

	/**
	 * This method is used to get the list of collection
	 * 
	 */
	@Override
	public void onPostUserCollections(List<CollectionDo> result) {
		totalSize = result.size();
		totalSelfCollection = totalSelfCollection + result.size();
		if (totalSelfCollection == 0) {
			addCollections.collectionFirstElement.setVisible(false);
			addCollections.loadingPanel.setVisible(false);
			addCollections.btnCancel.setVisible(true);
			addCollections.btnCancel.getElement().setAttribute("style",
					"margin-left:37px");
			addCollections.nocollectionMsgLabel.setVisible(true);
		}
		if (totalSize == 0 && totalSelfCollection != 0) {
			addCollections.addLabel.setVisible(true);
			addCollections.btnCancel.setVisible(true);
			addCollections.loadingPanel.setVisible(false);
		}
		int count = 1;
		collectionItems = new ArrayList<Integer>();
		collectionItems.add(-1);

		for (CollectionDo collection : result) {
			if (!collection.getCollectionType().toString().trim().equalsIgnoreCase("folder")){
				final Label titleLabel = new Label(collection.getTitle());
				titleLabel.setStyleName(CollectionEditResourceCBundle.INSTANCE
						.css().copyPopUpResourceListBoxText());
				titleLabel.getElement()
						.setAttribute("id", collection.getGooruOid());
				addCollections.htmlScrollPanel.add(titleLabel);
	
				count++;
				addCollections.addLabel.setVisible(true);
				addCollections.btnCancel.setVisible(true);
				addCollections.loadingPanel.setVisible(false);
				addCollections.nocollectionMsgLabel.setVisible(false);
				int collectionItemDoSize = collection.getCollectionItems().size();
				collectionItems.add(collectionItemDoSize);
				addCollections.setCollectionItemDoSize(collectionItems);
				titleLabel.addClickHandler(new ClickHandler() {
	
					@Override
					public void onClick(ClickEvent event) {
						addCollections.getMandatorySelectCollectionLbl()
								.setVisible(false);
						if (titleLabel.getText().length() >= 40) {
							addCollections.collectionFirstElement
									.setText(titleLabel.getText().substring(0, 40)
											+ "...");
							addCollections.collectionFirstElement.getElement()
									.setAttribute(
											"id",
											titleLabel.getElement().getAttribute(
													"id"));
						} else {
							addCollections.collectionFirstElement
									.setText(titleLabel.getText());
							addCollections.collectionFirstElement.getElement()
									.setAttribute(
											"id",
											titleLabel.getElement().getAttribute(
													"id"));
	
						}
						addCollections.copyPopUpScrollHtmlPanel.getElement()
								.getStyle().setVisibility(Visibility.HIDDEN);
						collId = addCollections.collectionFirstElement.getElement()
								.getId();
	
					}
				});
			}
		}
		/**
		 * to get more collection after scroll down, if collection item is more
		 * than 20
		 */
		addCollections.copyPopUpScrollHtmlPanel
				.addScrollHandler(new ScrollHandler() {

					@Override
					public void onScroll(ScrollEvent event) {

						if (addCollections.copyPopUpScrollHtmlPanel
								.getVerticalScrollPosition() == addCollections.copyPopUpScrollHtmlPanel
								.getMaximumVerticalScrollPosition()
								&& totalSize >= 20) {
							addCollections.addLabel.getElement().setAttribute(
									"style", "display:none");
							addCollections.addLabel.setVisible(false);
							addCollections.btnCancel.setVisible(false);
							addCollections.loadingPanel.setVisible(true);
							addCollections.nocollectionMsgLabel
									.setVisible(false);
							addCollections.loadingPanel.getElement()
									.setAttribute("style", "display:block");
							addCollections.loadingPanel.getElement()
									.setAttribute("style", "margin-top:18px");
							pageNumber = pageNumber + 1;
							getPresenter().getUserColletionsList(pageSize,
									pageNumber);

						}

					}
				});
	}

	@UiHandler("btnEditAssignment")
	public void onClickEditAssignment(ClickEvent event) {
		if (btnEditAssignment.getText().equalsIgnoreCase("edit")) {
			isInEditMode = true;
			btnEditAssignment.setText(i18n.GL0240());
			btnEditAssignment.getElement().setAttribute("alt",i18n.GL0240());
			btnEditAssignment.getElement().setAttribute("title",i18n.GL0240());

			cancelLabel.setVisible(true);
			deleteLabel.setVisible(false);
			// Handle Edit sequence
			hideShowControls(true);
			hideShowLabels(false);
		} else {
			boolean isFormFilled = true;

			// validations...
			String title = assignmentTitleTxt.getText().trim();
			String dueDate = dateBoxUc.getDateBox().getText();
			String directions = assignmentDescriptionTxtArea.getText().trim();

			if (title == null || title.equalsIgnoreCase("")) {
				mandatoryTitleLabel.setText(MANDATORY_TITLE);
				mandatoryTitleLabel.setVisible(true);
				isFormFilled = false;
			} else if (title.length() > 50) {
				mandatoryTitleLabel.setText(CHARACTERS_LIMIT);
				mandatoryTitleLabel.setVisible(true);
				isFormFilled = false;
			}
			
			if (dueDate != null && !dueDate.equalsIgnoreCase("")) {
				boolean isDate = hasValidateDate(dueDate);
				if (!isDate){
					mandatoryDueDateLabel.setVisible(true);
					isFormFilled = false;
				}
			}
			if (directions.length() > 400) {
				mandatoryDirectionLabel.setText(CHARACTERS_LIMIT);
				mandatoryDirectionLabel.setVisible(true);
				isFormFilled = false;
			}

			if (isFormFilled) {

				isInEditMode = false;
				btnEditAssignment.setText(i18n.GL0140());
				btnEditAssignment.getElement().setAttribute("alt",i18n.GL0140());
				btnEditAssignment.getElement().setAttribute("title",i18n.GL0140());

				AssignmentDo assignmentDo = new AssignmentDo();
				if (dueDate!=null && !dueDate.equalsIgnoreCase("")){
					assignmentDo.setPlannedEndDate(dateBoxUc.getDateBox().getText());
				}

				TaskDo taskDo = new TaskDo();
				taskDo.setTitle(assignmentTitleTxt.getText().trim());
					taskDo.setDescription(assignmentDescriptionTxtArea.getText()
							.trim());
				taskDo.setStatus("open");

			    assignmentDo.setTask(taskDo);
				assignmentTitelLbl.setHTML(assignmentDo.getTask().getTitle());
				assignmentTitleTxt.setText(assignmentDo.getTask().getTitle());
				assignmentTitleTxt.getElement().setAttribute("alt",assignmentDo.getTask().getTitle());
				assignmentTitleTxt.getElement().setAttribute("title",assignmentDo.getTask().getTitle());
				 
				dueDateLbl.setText(dateBoxUc.getDateBox().getText());
				dueDateLbl.getElement().setAttribute("alt",dateBoxUc.getDateBox().getText());
				dueDateLbl.getElement().setAttribute("title",dateBoxUc.getDateBox().getText());
				dateBoxUc.getDateBox().setText(dueDateLbl.getText());
		
				assignmentDescriptionLbl.setHTML(assignmentDo.getTask()
						.getDescription());
				assignmentDescriptionTxtArea.setText(assignmentDo.getTask()
						.getDescription());

				getPresenter().updateAssignmet(assignmentDo,
						this.assignmentsSearchDo.getTask().getGooruOid());

				mandatoryTitleLabel.setVisible(false);
				mandatoryDueDateLabel.setVisible(false);
				mandatoryDirectionLabel.setVisible(false);

				// / Update logic ends here
				hideShowControls(false);
				hideShowLabels(true);
				deleteLabel.setVisible(true);
				cancelLabel.setVisible(false);
			}
		}
	}

	@UiHandler("cancelLabel")
	public void onClickCancelUpdate(ClickEvent event) {
		isInEditMode = false;
		btnEditAssignment.setText(i18n.GL0140());
		btnEditAssignment.getElement().setAttribute("alt",i18n.GL0140());
		btnEditAssignment.getElement().setAttribute("title",i18n.GL0140());
		cancelLabel.setVisible(false);
		deleteLabel.setVisible(true);
		hideShowControls(false);
		hideShowLabels(true);

		mandatoryTitleLabel.setVisible(false);
		mandatoryDueDateLabel.setVisible(false);
		mandatoryDirectionLabel.setVisible(false);

		// Reset the values back
		setUiElements();
	}

	// Method for deleting Assignment from Classpage
	@UiHandler("deleteLabel")
	public void onClickDeleteAssignment(ClickEvent event) {
		deleteConfirmVc = new DeleteConfirmPopupVc(i18n.GL0748(), "\""
				+ assignmentTitelLbl.getHTML() + "\"" + i18n.GL0103()+i18n.GL_SPL_FULLSTOP()) {

			@Override
			public void onTextConfirmed() {
				getPresenter().deleteAssignment(
						assignmentsSearchDo.getTask().getGooruOid());
			}
		};
	}

	@Override
	public void onPostCollectionDelete() {
		deleteConfirmVc.hide();
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
	}

	/**
	 * 
	 * To show the ResourceMetaDataInfo Edit,Copy and Remove buttons
	 */
	private class ActionPanelHover implements MouseOverHandler {

		@Override
		public void onMouseOver(MouseOverEvent event) {
			if (!isInEditMode && !isExpandable) {
				clickToExpandLabel.setVisible(true);
			} else if (!isInEditMode && isExpandable) {
				actionPanel.setVisible(true);
			}
		}
	}

	/**
	 * 
	 * To hide the ResourceMetaDataInfo Edit,Copy and Remove buttons
	 */
	private class ActionPanelOut implements MouseOutHandler {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			if (!isInEditMode && !isExpandable) {
				clickToExpandLabel.setVisible(false);
			} else if (!isInEditMode && isExpandable) {
				actionPanel.setVisible(false);
			}
		}
	}

	/*
	 * Key event handlers for Title and Directions field.
	 */
	private class TitleKeyUpHandler implements KeyUpHandler {

		public void onKeyUp(KeyUpEvent event) {
			String title = assignmentTitleTxt.getText().trim();
			mandatoryTitleLabel.setVisible(false);
			if (title.length() >= 50) {
				mandatoryTitleLabel.setText(CHARACTERS_LIMIT);
				mandatoryTitleLabel.setVisible(true);
			}
		}
	}
	/**
	 * 
	 * @fileName : AssignmentsTabView.java
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
	private class DirectionsKeyUpHandler implements KeyUpHandler {

		public void onKeyUp(KeyUpEvent event) {
			String directions = assignmentDescriptionTxtArea.getText().trim();

			mandatoryDirectionLabel.setVisible(false);

			if (directions.length() >= 400) {
				mandatoryDirectionLabel.setText(CHARACTERS_LIMIT);
				mandatoryDirectionLabel.setVisible(true);
			}
		}
	}

	/*
	 * Class for handling click event for openning Assignment Tab.
	 */

	private class ActionPanelClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (isExpandable && !isInEditMode) {
				isExpandable = false;
				actionPanel.setVisible(false);
				clickToExpandLabel.setVisible(true);
				new CustomAnimation(inLinePanel).run(300);
			} else {
				isExpandable = true;
				actionPanel.setVisible(true);
				clickToExpandLabel.setVisible(false);
				collectionsPanel.clear();
				loadingPanel.setVisible(true);
				getPresenter().getAssignmentCollections(assignmentId);
				new CustomAnimation(inLinePanel).run(300);
			}
		}
	}

	/**
	 * @description : To insert newly created Assignment
	 * @param : org.ednovo.gooru.shared.model.content.ResourceDo
	 */

	public void insertCollectionToAssignment(ResourceDo resourceDo) {

		 cv = new CollectionsView();

		collectionsPanel.add(cv);
	}

	@Override
	public Widget getDragHandle() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public IsDraggableMirage initDraggableMirage() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void onDragBlur() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public String getDragId() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public DRAG_TYPE getDragType() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public int getDragTopCorrection() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public int getDragLeftCorrection() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void showPanel(boolean visible) {
		assignmentContainerPanel.setVisible(visible);

	}

	@Override
	public void disableAddNewCollection() {
		btnAddCollectionToAssign.setVisible(false);
	}

	@Override
	public void hideLoading() {
		loadingPanel.setVisible(false);
	}

	@Override
	public void closeAllOpenedPopUp() {
		if(addCollections !=null){
			addCollections.hide();
		}
	}
	GetStudentJoinListHandler getStudentJoinListHandler = new GetStudentJoinListHandler(){
			
			@Override
			public void getStudentJoinList(int joinClassList) {
				activeMemberCounter=joinClassList;
			}
	};
}
