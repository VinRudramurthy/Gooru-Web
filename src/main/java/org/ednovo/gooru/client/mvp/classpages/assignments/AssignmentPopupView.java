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
package org.ednovo.gooru.client.mvp.classpages.assignments;

/*
 *
 * @fileName : AssignmentPopupView.java
 *
 * @description : This class is responsible to show and add assignments
 *
 *
 * @version : 5.3
 *
 * @date: May 4, 2013
 *
 * @Author Gooru team
 *
 * @Reviewer:
 */

import java.util.Date;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.AssignmentDo;
import org.ednovo.gooru.application.shared.model.content.AttachToDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.TaskDo;
import org.ednovo.gooru.client.uc.DateBoxUc;
import org.ednovo.gooru.client.uc.ErrorLabelUc;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public abstract class AssignmentPopupView extends Composite{

	public interface AssignmentPopupViewUiBinder extends
			UiBinder<Widget, AssignmentPopupView> {

	}

	@UiField
	SimplePanel dateSimPanel;

	@UiField
	ErrorLabelUc dateValidationUc;

	private DateBoxUc dateBoxUc;

	public static AssignmentPopupViewUiBinder uiBinder = GWT
			.create(AssignmentPopupViewUiBinder.class);

	static MessageProperties i18n = GWT.create(MessageProperties.class);

	private static final String MANDATORY_TITLE = i18n.GL0173();
	private static final String CHARACTERS_LIMIT = i18n.GL0143();
	String classpageId = "";

	@UiField
	public Label cancelResourcePopupBtnLbl, addResourceBtnLbl,assignmentTitleLabel,assignmentDirectionLabel;

	@UiField
	Label mandatoryTitleLabel, mandatoryDueDateLabel, mandatoryDirectionLabel,assignmentDueDateLabel;

	@UiField
	TextBox assignmentTitleTxt;

	@UiField
	TextArea assignmentDirectionsTxtArea;

	@UiField
	HTMLPanel addResourceBtnPanel;

    boolean isClicked = false;

	public AssignmentPopupView() {
		initWidget(uiBinder.createAndBindUi(this));
		assignmentTitleLabel.setText(i18n.GL1407()+i18n.GL_SPL_STAR());
		assignmentTitleLabel.getElement().setId("lblAssignmentTitle");
		assignmentTitleLabel.getElement().setAttribute("alt",i18n.GL1407());
		assignmentTitleLabel.getElement().setAttribute("title",i18n.GL1407());

		mandatoryTitleLabel.setText(i18n.GL0173());
		mandatoryTitleLabel.getElement().setId("lblMandatoryTitle");
		mandatoryTitleLabel.getElement().setAttribute("alt",i18n.GL0173());
		mandatoryTitleLabel.getElement().setAttribute("title",i18n.GL0173());

		assignmentDueDateLabel.setText(i18n.GL0238());
		assignmentDueDateLabel.getElement().setId("lblAssignmentDueDate");
		assignmentDueDateLabel.getElement().setAttribute("alt",i18n.GL0238());
		assignmentDueDateLabel.getElement().setAttribute("title",i18n.GL0238());

		mandatoryDueDateLabel.setText(i18n.GL0235());
		mandatoryDueDateLabel.getElement().setAttribute("id", "datePickerErrorMessageLabel");
		mandatoryDueDateLabel.getElement().setAttribute("alt",i18n.GL0235());
		mandatoryDueDateLabel.getElement().setAttribute("title",i18n.GL0235());

		assignmentDirectionLabel.setText(i18n.GL1408());
		assignmentDirectionLabel.getElement().setAttribute("id", "lblAssignmentDirection");
		assignmentDirectionLabel.getElement().setAttribute("alt",i18n.GL1408());
		assignmentDirectionLabel.getElement().setAttribute("title",i18n.GL1408());

		mandatoryDirectionLabel.setText(i18n.GL0236());
		mandatoryDirectionLabel.getElement().setId("errlblDirectional");

		addResourceBtnLbl.setText(i18n.GL0590());
		addResourceBtnLbl.getElement().setId("lblAdd");
		addResourceBtnLbl.getElement().setAttribute("alt",i18n.GL0590());
		addResourceBtnLbl.getElement().setAttribute("title",i18n.GL0590());

		cancelResourcePopupBtnLbl.setText(i18n.GL0142());
		cancelResourcePopupBtnLbl.getElement().setId("lblCancel");
		cancelResourcePopupBtnLbl.getElement().setAttribute("alt",i18n.GL0142());
		cancelResourcePopupBtnLbl.getElement().setAttribute("title",i18n.GL0142());

		assignmentTitleTxt.getElement().setAttribute("id", "txtAssignmentTitle");
		assignmentDirectionsTxtArea.getElement().setAttribute("id", "txtAreaDirections");
		StringUtil.setAttributes(assignmentTitleTxt, true);

		cancelResourcePopupBtnLbl.addClickHandler(new CloseClickHandler());
		addResourceBtnLbl.addClickHandler(new AddClickHandler());

		assignmentTitleTxt.getElement().setAttribute("maxlength", "50");
		assignmentDirectionsTxtArea.getElement().setAttribute("maxlength",
				"400");

		assignmentTitleTxt.addKeyUpHandler(new TitleKeyUpHandler());
		assignmentDirectionsTxtArea
				.addKeyUpHandler(new DirectionsKeyUpHandler());

		dateBoxUc = new DateBoxUc(false, false,false);
		dateSimPanel.add(dateBoxUc);
		dateSimPanel.getElement().setId("spnlDate");
		dateValidationUc.setStyleName(AddAssignmentContainerCBundle.INSTANCE.css().registerErrorLabel());

		dateBoxUc.getDateBox().addFocusHandler(new OnDateFocus());
		dateBoxUc.getDateBox().addBlurHandler(new OnDateBlur());
		dateBoxUc.addDomHandler(new OnDateFocus(), FocusEvent.getType());
		dateBoxUc.getDoneButton().addClickHandler(new OnDoneClick());

		dateValidationUc.setVisible(false);
		dateValidationUc.getElement().setId("errlblDateValidationUc");

		addResourceBtnPanel.getElement().setId("pnlAddResource");
		clearFields();
	}

	public abstract void hidePopup();

	public abstract void createAssignment(CollectionDo collectionDo, String todaysDate);

	public abstract void createAssignment(AssignmentDo assignmentDo);

	/**
	 * Validate Assignment Date
	 *
	 * @return true if valid
	 */
	private boolean hasValidateDate(String dueDate) {

		boolean isValid = true;
		if (dueDate == null || (dueDate != null && dueDate.isEmpty())) {
			dateValidationUc.setText("");
			assignmentDueDateLabel.getElement().setAttribute("alt","");
			assignmentDueDateLabel.getElement().setAttribute("title","");
			dateValidationUc.setVisible(true);

			isValid = false;
		}
		return isValid;
	}

	/**
	 *
	 * @fileName : AssignmentPopupView.java
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
	private class OnDateFocus implements FocusHandler {
		@Override
		public void onFocus(FocusEvent event) {
			dateBoxUc.removeStyleName(AddAssignmentContainerCBundle.INSTANCE.css().gooruDateBoxError());
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
	 * @fileName : AssignmentPopupView.java
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
			if (dateBoxUc.dateValidation()){
				if (!(dateBoxUc.getValue() == null || dateBoxUc.getDateBox()
						.getText().isEmpty())
						&& dateBoxUc.hasValidateDate()) {
				Date date = dateBoxUc.getValue();
				} else {
					dateBoxUc.getDatePickerUc().hide();
				}
			}
		}
	}

	/**
	 *
	 * @fileName : AssignmentPopupView.java
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
			dateBoxUc.removeStyleName(AddAssignmentContainerCBundle.INSTANCE.css().gooruDateBoxError());
			dateBoxUc.getDateBox().removeStyleName(
					AddAssignmentContainerCBundle.INSTANCE.css()
							.gooruDateError());
		}
	}

	/*
	 * Event handlers for all controls in this class
	 */
	private class CloseClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			clearFields();
			hidePopup();
		}
	}

	/**
	 *
	 * @fileName : AssignmentPopupView.java
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
	private class AddClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			boolean isFormFilled = true;

			String title = assignmentTitleTxt.getText().trim();
			String dueDate = dateBoxUc.getDateBox().getText();
			String directions = assignmentDirectionsTxtArea.getText().trim();

			if (title == null || title.equalsIgnoreCase("")) {
				mandatoryTitleLabel.setText(MANDATORY_TITLE);
				mandatoryTitleLabel.getElement().setAttribute("alt",MANDATORY_TITLE);
				mandatoryTitleLabel.getElement().setAttribute("title",MANDATORY_TITLE);
				mandatoryTitleLabel.setVisible(true);
				isFormFilled = false;
			} else if (title.length() > 50) {
				mandatoryTitleLabel.setText(CHARACTERS_LIMIT);
				mandatoryTitleLabel.getElement().setAttribute("alt",CHARACTERS_LIMIT);
				mandatoryTitleLabel.getElement().setAttribute("title",CHARACTERS_LIMIT);
				mandatoryTitleLabel.setVisible(true);
				isFormFilled = false;
			}
			if (dueDate != null && !dueDate.equalsIgnoreCase("")){
				isFormFilled = hasValidateDate(dueDate);
				if (!isFormFilled){
					mandatoryDueDateLabel.setVisible(true);
				}
			}
			if (directions.length() > 400) {
				mandatoryDirectionLabel.setText(CHARACTERS_LIMIT);
				mandatoryDirectionLabel.setVisible(true);
				isFormFilled = false;
			}

			if (isFormFilled) {
				AssignmentDo assignmentDo = new AssignmentDo();
				if (dueDate!=null && !dueDate.equalsIgnoreCase("")){
					assignmentDo.setPlannedEndDate(dueDate);
				}
				assignmentDo.setClasspageId(classpageId);

				TaskDo taskDo = new TaskDo();
				taskDo.setTitle(title);
				taskDo.setTypeName("assignment");
				if (directions!=null && !directions.trim().equalsIgnoreCase("")){
					taskDo.setDescription(directions);
				}

				assignmentDo.setTask(taskDo);

				AttachToDo attachToDo = new AttachToDo();
				attachToDo.setId(classpageId);
				attachToDo.setType("classpage");

				assignmentDo.setAttachTo(attachToDo);

				if(!isClicked){
					isClicked = true;
					MixpanelUtil.Create_Assignment_Success();
					createAssignment(assignmentDo);
				}
			}

		}
	}


	/**
	 *
	 * @fileName : AssignmentPopupView.java
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
	private class TitleKeyUpHandler implements KeyUpHandler {

		public void onKeyUp(KeyUpEvent event) {
			String title = assignmentTitleTxt.getText().trim();
			mandatoryTitleLabel.setVisible(false);
			if (title.length() >= 50) {
				mandatoryTitleLabel.setText(CHARACTERS_LIMIT);
				mandatoryTitleLabel.getElement().setAttribute("alt",CHARACTERS_LIMIT);
				mandatoryTitleLabel.getElement().setAttribute("title",CHARACTERS_LIMIT);
				mandatoryTitleLabel.setVisible(true);
			}
		}
	}

	/**
	 *
	 * @fileName : AssignmentPopupView.java
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
			String directions = assignmentDirectionsTxtArea.getText().trim();

			mandatoryDirectionLabel.setVisible(false);

			if (directions.length() >= 400) {
				mandatoryDirectionLabel.setText(CHARACTERS_LIMIT);
				mandatoryDirectionLabel.setVisible(true);
			}
		}
	}

	/**
	 *
	 * @function clearFields
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
	public void clearFields() {
		mandatoryDirectionLabel.setVisible(false);
		mandatoryDueDateLabel.setVisible(false);
		mandatoryTitleLabel.setVisible(false);

		assignmentDirectionsTxtArea.setText("");
		assignmentTitleTxt.setText("");
		dateBoxUc.getDateBox().setText("");

		isClicked = false;
	}

	/* Setter and getters */
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

	public void isClikedMethod(){
			isClicked = false;
			}

}
