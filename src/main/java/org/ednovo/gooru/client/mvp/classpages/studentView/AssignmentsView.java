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

import java.util.List;

import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.AssignmentsSearchDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.ResourceDo;
import org.ednovo.gooru.client.mvp.classpages.tabitem.assignments.collections.CollectionsView;
import org.ednovo.gooru.client.mvp.dnd.IsDraggableMirage;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.shared.util.DataLogEvents;
import org.ednovo.gooru.shared.util.GwtUUIDGenerator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @fileName : AssignmentsView.java
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
public class AssignmentsView extends
		ChildView<AssignmentsPresenter> implements
		IsAssignmentsView {

	@UiField(provided = true)
	AssignmentsViewCBundle res;
	
	List<Integer> collectionItems;
	
	private String collId;
	
	private AssignmentsSearchDo assignmentsSearchDo;
	
	private Boolean description = null;

//	private static final String REG_EXP = "^(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)$";

	/*@UiField Label assignmentTitelLbl,dueDateLbl,assignmentDescriptionLbl,editLabel,deleteLabel,cancelLabel;
	@UiField HTMLPanel actionPanel;
	
	@UiField TextBox assignmentTitleTxt;//, assignmentDueDateTxt;
	@UiField TextArea assignmentDescriptionTxtArea;
	
	@UiField Label addCollectionToAssignLbl;
	
	@UiField HTMLPanel collectionsPanel, assignmentContainerPanel, noAssignmentContainerPanel;
	
	@UiField
	SimplePanel dateSimPanel;
	
	@UiField
	ErrorLabelUc dateValidationUc;

	private DateBoxUc dateBoxUc;
	
	boolean isInEditMode;
	*/
	@UiField Label titleLb,expandLbl,descLbl,dueLbl,emptyAssignmentLbl;

	@UiField HTMLEventPanel assignmentPanel;

	@UiField HTMLPanel colletionPanel,descPanel;
	
	String assignmentId;
	
	boolean isOpen=false;

	private static AssignmentsViewUiBinder uiBinder = GWT
			.create(AssignmentsViewUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface AssignmentsViewUiBinder extends
			UiBinder<Widget, AssignmentsView> {
	}	

	/**
	 * Class constructor
	 * 
	 * @param collectionDo
	 *            instance of {@link CollectionDo}
	 */
	public AssignmentsView(AssignmentsSearchDo assignmentsSearchDo, boolean isOpen) {
		this.isOpen = isOpen;
		
		String taskTitle = assignmentsSearchDo.getTask().getTitle();
		String dueDate = assignmentsSearchDo.getTask().getPlannedEndDate() !=null ? assignmentsSearchDo.getTask().getPlannedEndDate() : "";
		String description = assignmentsSearchDo.getTask().getDescription();
		
		res = AssignmentsViewCBundle.INSTANCE;
		AssignmentsViewCBundle.INSTANCE.css().ensureInjected();
		initWidget(uiBinder.createAndBindUi(this));
		
		setPresenter(new AssignmentsPresenter(this));
		this.assignmentsSearchDo = assignmentsSearchDo;
		assignmentId = assignmentsSearchDo.getTask().getGooruOid();
		titleLb.setText(taskTitle);
		titleLb.getElement().setId("lblTitle");
		titleLb.getElement().setAttribute("alt",taskTitle);
		titleLb.getElement().setAttribute("title",taskTitle);
		
		descLbl.setText(description);
		descLbl.getElement().setId("lblDesc");
		descLbl.getElement().setAttribute("alt",description);
		descLbl.getElement().setAttribute("title",description);
		
		
		expandLbl.setText(i18n.GL0241());
		expandLbl.getElement().setAttribute("alt",i18n.GL0241());
		expandLbl.getElement().setAttribute("title",i18n.GL0241());
		expandLbl.getElement().setId("lblexpand");
		
		dueLbl.getElement().setId("lblDue");
		if (dueDate!=null && !dueDate.equalsIgnoreCase("")){
			dueLbl.setText(i18n.GL0238()+i18n.GL_SPL_SEMICOLON()+" "+dueDate);
			dueLbl.getElement().setAttribute("alt",i18n.GL0238()+i18n.GL_SPL_SEMICOLON()+" "+dueDate);
			dueLbl.getElement().setAttribute("title",i18n.GL0238()+i18n.GL_SPL_SEMICOLON()+" "+dueDate);
		}else{
			dueLbl.setText(" ");
			dueLbl.getElement().setAttribute("alt","");
			dueLbl.getElement().setAttribute("title","");
		}
		emptyAssignmentLbl.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
		getPresenter().getAssignmentCollections(assignmentId);
		assignmentPanel.addMouseOverHandler(new OnMouseOver());
		assignmentPanel.addMouseOutHandler(new OnMouseOut());
		assignmentPanel.addClickHandler(new OnTitleClick());
		descPanel.setVisible(false);
		
		if (isOpen){
				descPanel.getElement().getStyle().setDisplay(Display.INLINE);
				this.description= true;
				DataLogEvents.assignmentView(GwtUUIDGenerator.uuid(), "assignment-view", assignmentId, AppClientFactory.getLoggedInUser().getGooruUId(), PlayerDataLogEvents.getUnixTime(), PlayerDataLogEvents.getUnixTime(),"",0L, AppClientFactory.getLoggedInUser().getToken()	,"start");
		}else{
				descPanel.getElement().getStyle().setDisplay(Display.NONE);
				this.description= false;
		}
		assignmentPanel.getElement().setId("pnlAssignment");
		colletionPanel.getElement().setId("pnlColletion");
		emptyAssignmentLbl.getElement().setId("lblEmptyAssignment");
	}
	/**
	 * 
	 * @fileName : AssignmentsView.java
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
	private class OnMouseOver implements MouseOverHandler{
		@Override
		public void onMouseOver(MouseOverEvent event) {
			if((description == null || !description)){
			expandLbl.setVisible(true);
			}
		}
		
	}
	/**
	 * 
	 * @fileName : AssignmentsView.java
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
	private class OnMouseOut implements MouseOutHandler{
		
		@Override
		public void onMouseOut(MouseOutEvent event) {
			expandLbl.setVisible(false);
		}
	}
	/**
	 * 
	 * @fileName : AssignmentsView.java
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
	private class OnTitleClick implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {			
			if((description == null || !description)){
				descPanel.getElement().getStyle().setDisplay(Display.INLINE);
				description= true;
				DataLogEvents.assignmentView(GwtUUIDGenerator.uuid(), "assignment-view", assignmentId, AppClientFactory.getLoggedInUser().getGooruUId(), PlayerDataLogEvents.getUnixTime(), PlayerDataLogEvents.getUnixTime(),"",0L, AppClientFactory.getLoggedInUser().getToken()	,"start");
			}
			else{
				descPanel.getElement().getStyle().setDisplay(Display.NONE);
				description= false;
//				DataLogEvents.assignmentView(GwtUUIDGenerator.uuid(), "assignment-view", assignmentId, AppClientFactory.getLoggedInUser().getGooruUId(), PlayerDataLogEvents.getUnixTime(), PlayerDataLogEvents.getUnixTime(),"",0L, AppClientFactory.getLoggedInUser().getToken()	,"start");
			}
			
		}
		
	}
	
	/**
	 * 
	 */
	public void insertCollectionToAssignment(ResourceDo resourceDo){
		CollectionsView cv = new CollectionsView();
		
		colletionPanel.add(cv);

	}
	
	
	@Override
	public void emptyAssignment() {
		
		if((descLbl.getText()==null || descLbl.getText().equalsIgnoreCase(""))){
			emptyAssignmentLbl.setText(i18n.GL1129());
			emptyAssignmentLbl.getElement().setAttribute("alt",i18n.GL1129());
			emptyAssignmentLbl.getElement().setAttribute("title",i18n.GL1129());
		}
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

}
