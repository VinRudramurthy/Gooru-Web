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
package org.ednovo.gooru.client.mvp.classpages.edit;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.classpages.event.RefreshAssignmentsListEvent;
import org.ednovo.gooru.client.mvp.search.event.ResetProgressEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : AssignmentProgress.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: Jun 9, 2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class AssignmentProgressVc extends Composite{


	private static AssignmentProgressVcUiBinder uiBinder = GWT
			.create(AssignmentProgressVcUiBinder.class);

	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface AssignmentProgressVcUiBinder extends UiBinder<Widget, AssignmentProgressVc> {
	}

	@UiField(provided = true)
	AssignmentProgressCBundle res;

	@UiField
	Label lblLineStart, lblLineEnd, lblAssignmentNo, lblCircle1;

	@UiField HTMLPanel panelCircle,panelCicle1, resourceTypePanel,moveAssignmentPopup,dueDateContainer, panelComboList;

	@UiField HTML htmlCollectiontitle,assignmentCollectiontitle;

	@UiField Label lblMoveTo, resoureDropDownLbl, resourceCategoryLabel;

	@UiField FlowPanel panelMainContainer,assignmentInfoPopup;

	@UiField Button studyButtonText;

	boolean isLast;
	private ClasspageItemDo classpageItemDo;
	int assignmentNumber;

	/**
	 * Class constructor
	 */
	public AssignmentProgressVc(boolean isLast, ClasspageItemDo classpageList, int assignmentNumber, int assignmentTotalCount) {

		this.isLast = isLast;
		this.classpageItemDo = classpageList;
		this.assignmentNumber = assignmentNumber;

		this.res = AssignmentProgressCBundle.INSTANCE;
		initWidget(uiBinder.createAndBindUi(this));
		res.css().ensureInjected();
		assignmentInfoPopup.removeFromParent();

		panelMainContainer.getElement().setAttribute("id", classpageList.getCollectionItemId());
		lblLineEnd.setVisible(this.isLast ? false : true);
		lblLineStart.setVisible(assignmentNumber == 1 ? false : true);

		lblAssignmentNo.setText(String.valueOf(assignmentNumber));
		lblAssignmentNo.getElement().setAttribute("alt",String.valueOf(assignmentNumber));
		lblAssignmentNo.getElement().setAttribute("title",String.valueOf(assignmentNumber));
		htmlCollectiontitle.setHTML(classpageList.getCollectionTitle());
		htmlCollectiontitle.getElement().setAttribute("alt",classpageList.getCollectionTitle());
		htmlCollectiontitle.getElement().setAttribute("title",classpageList.getCollectionTitle());
		lblMoveTo.setText(i18n.GL1912());
		lblMoveTo.getElement().setAttribute("alt",i18n.GL1912());
		lblMoveTo.getElement().setAttribute("title",i18n.GL1912());
		resourceTypePanel.setVisible(false);
		resourceCategoryLabel.setText(String.valueOf(assignmentNumber));

		/*.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				resourceTypePanel.setVisible(resourceTypePanel.isVisible() ? false : true);
			}
		});*/

		/*resourceCategoryLabel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				resourceTypePanel.setVisible(resourceTypePanel.isVisible() ? false : true);
			}
		});*/

		for (int i=0; i<assignmentTotalCount; i++){
			resourceTypePanel.add(createLabel(""+(i+1)));
		}
		Event.addNativePreviewHandler(new NativePreviewHandler() {
	        public void onPreviewNativeEvent(NativePreviewEvent event) {
	        	hideDropDown(event);
	          }
	    });
		setId();
	}
	/**
	 *
	 * @function setId
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
	public void setId(){
		lblLineStart.getElement().setId("lblLineStart");
		lblLineEnd.getElement().setId("lblLineEnd");
		panelCircle.getElement().setId("pnlCircle");
		panelCicle1.getElement().setId("pnlCircle1");
		lblAssignmentNo.getElement().setId("lblAssignmentNo");
		moveAssignmentPopup.getElement().setId("pnlMoveAssignmentPopup");
		htmlCollectiontitle.getElement().setId("htmlCollectionTitle");
		lblMoveTo.getElement().setId("lblMoveTo");
		panelComboList.getElement().setId("pnlComboList");
		resourceCategoryLabel.getElement().setId("lblResourceCategory");
		resoureDropDownLbl.getElement().setId("lblResourceDropDown");
		resourceTypePanel.getElement().setId("pnlResourceType");
		resourceTypePanel.getElement().getStyle().setBackgroundColor("#FFF");
		assignmentInfoPopup.getElement().setId("pnlAssignmentInfoPopup");
		lblCircle1.getElement().setId("lblCircle1");
		assignmentCollectiontitle.getElement().setId("htmlAssignmentCollectionTitle");
		studyButtonText.getElement().setId("btnStudy");
		dueDateContainer.getElement().setId("pnlDueDateContainer");
	}
	/**
	 *
	 * @param isLastCollection
	 * @param classpageItemDo
	 * @param sequenceNumber
	 */
	public AssignmentProgressVc(boolean isLastCollection, final ClasspageItemDo classpageItemDo, int sequenceNumber) {
		this.res = AssignmentProgressCBundle.INSTANCE;
		initWidget(uiBinder.createAndBindUi(this));
		this.res.css().ensureInjected();
		this.classpageItemDo=classpageItemDo;
		moveAssignmentPopup.removeFromParent();
		setDueDateAndDirection();
		assignmentCollectiontitle.setHTML(classpageItemDo.getCollectionTitle());
		assignmentCollectiontitle.getElement().setAttribute("alt",classpageItemDo.getCollectionTitle());
		assignmentCollectiontitle.getElement().setAttribute("title",classpageItemDo.getCollectionTitle());
		panelMainContainer.getElement().setAttribute("id", classpageItemDo.getCollectionItemId());
		lblLineStart.setVisible(sequenceNumber == 1 ? false : true);
		lblLineEnd.setVisible(isLastCollection ? false : true);
		lblAssignmentNo.setText(""+sequenceNumber);
		lblAssignmentNo.getElement().setAttribute("alt",""+sequenceNumber);
		lblAssignmentNo.getElement().setAttribute("title",""+sequenceNumber);
		lblCircle1.setText(""+sequenceNumber);
		lblCircle1.getElement().setAttribute("alt",""+sequenceNumber);
		lblCircle1.getElement().setAttribute("title",""+sequenceNumber);
		if(classpageItemDo.getStatus().equalsIgnoreCase("open")){
			panelCircle.setStyleName(this.res.css().bluecircle());
			panelCicle1.setStyleName(this.res.css().bluecircle());
		}else{
			panelCircle.setStyleName(this.res.css().greencircle());
			panelCicle1.setStyleName(this.res.css().greencircle());
		}
		studyButtonText.setText(i18n.GL0182());
		studyButtonText.getElement().setAttribute("alt",i18n.GL0182());
		studyButtonText.getElement().setAttribute("title",i18n.GL0182());
		studyButtonText.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Map<String, String> parms = new HashMap<String, String>();
				parms.put("id", classpageItemDo.getCollectionId());
				parms.put("cid", classpageItemDo.getCollectionItemId());
				parms.put("page", "study");
				if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY)){
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.ASSESSMENT_PLAY, parms, false);
				}else{
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.COLLECTION_PLAY, parms, false);
				}

			}
		});
		Event.addNativePreviewHandler(new NativePreviewHandler() {
	        public void onPreviewNativeEvent(NativePreviewEvent event) {
	        	hideDropDown(event);
	          }
	    });
		setId();
	}
	/**
	 *
	 * @function updateDotsCircle
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
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
	public void updateDotsCircle(String readStatus){
		classpageItemDo.setStatus(readStatus);
		if(classpageItemDo.getStatus().equalsIgnoreCase("open")){
			panelCircle.setStyleName(this.res.css().bluecircle());
			panelCicle1.setStyleName(this.res.css().bluecircle());
		}else{
			panelCircle.setStyleName(this.res.css().greencircle());
			panelCicle1.setStyleName(this.res.css().greencircle());
		}
	}
	/**
	 *
	 * @function setDueDateAndDirection
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
	public void setDueDateAndDirection(){
		dueDateContainer.clear();
		String dueDate=classpageItemDo.getPlannedEndDate();
		if(dueDate!=null&&!dueDate.equals("")){
			Label dueDateText=new Label(i18n.GL1390());
			dueDateText.setStyleName(this.res.css().dueDataIcon());
			Label dueDateLabel=new Label(dueDate.toString());
			dueDateLabel.setStyleName(this.res.css().headerDueDate());
			dueDateContainer.add(dueDateText);
			dueDateContainer.add(dueDateLabel);
		}
		String directionDescription=classpageItemDo.getDirection();
		if(directionDescription!=null&&!directionDescription.equals("")){
			InlineLabel directionHeadLabel=new InlineLabel(i18n.GL1372());
			directionHeadLabel.setStyleName(this.res.css().directionHeading());
			InlineLabel directionTextLabel=new InlineLabel(directionDescription);
			directionTextLabel.setStyleName(this.res.css().directionDesc());
			dueDateContainer.add(directionHeadLabel);
			dueDateContainer.add(directionTextLabel);
		}
	}

	/**
	 *
	 * @function createLabel
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param title
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
	public Label createLabel(String title){
		Label lblLabel = new Label();
		lblLabel.setText(title);
		lblLabel.getElement().addClassName(res.css().myFolderCollectionFolderDropdown());
		lblLabel.getElement().addClassName(res.css().myFolderCollectionFolderVideoTitle());
		lblLabel.getElement().setAttribute("alt", title);
		lblLabel.getElement().setAttribute("title", title);
		lblLabel.getElement().setAttribute("id", title);
		lblLabel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Label lbl = (Label)event.getSource();
				resourceCategoryLabel.setText(lbl.getText());
				resourceCategoryLabel.getElement().setAttribute("alt",lbl.getText());
				resourceCategoryLabel.getElement().setAttribute("title",lbl.getText());

				AppClientFactory.getInjector().getClasspageService().v2ChangeAssignmentSequence("", classpageItemDo.getCollectionItemId(), Integer.parseInt(lbl.getText()), new SimpleAsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						resourceTypePanel.setVisible(resourceTypePanel.isVisible() ? false : true);
						AppClientFactory.fireEvent(new ResetProgressEvent());
						AppClientFactory.fireEvent(new RefreshAssignmentsListEvent());
					}
				});
			}
		});
		return lblLabel;
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
    	if(event.getTypeInt()==Event.ONMOUSEOVER){
    		Event nativeEvent = Event.as(event.getNativeEvent());
        	boolean target=eventTargetsPopup(nativeEvent);
        	if(!target){
        		resourceTypePanel.setVisible(false);
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
			return resourceTypePanel.getElement().isOrHasChild(Element.as(target)) || resourceCategoryLabel.getElement().isOrHasChild(Element.as(target))
					|| panelComboList.getElement().isOrHasChild(Element.as(target));
		}
		return false;
	}
}
