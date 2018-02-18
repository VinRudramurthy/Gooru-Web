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
package org.ednovo.gooru.client.mvp.classpages.classlist;
import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClassPageCollectionDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;
import org.ednovo.gooru.client.effects.BackgroundColorEffect;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
/**
 *
 * @fileName : MembersViewVc.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 07-Dec-2014
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public abstract class MembersViewVc extends Composite {

	private static MembersViewVcUiBinder uiBinder = GWT.create(MembersViewVcUiBinder.class);

	interface MembersViewVcUiBinder extends UiBinder<Widget, MembersViewVc> {
	}

	public MessageProperties i18N = GWT.create(MessageProperties.class);
	List<ClassPageCollectionDo> classpageTitles = new ArrayList<ClassPageCollectionDo>();

	CollaboratorsDo collaboratorsDo = null;
	ClasspageDo classpageDo = null;

	boolean isYou = false;
	boolean isCreator = false;
	boolean isNew = false;

	int defaultCollabCount = 5;

	@UiField HTMLEventPanel panelMembers;

	@UiField HTMLPanel panelCollaboratorsListContainer;

	@UiField Button btnRemove; // Remove other collaborators from list.

	@UiField Label lblUserName, lblEmailId;

	@UiField Image imgProfileImage;

	String defaultProfileImage = "images/settings/setting-user-image.png";

	int position = 0;

	public MembersViewVc(String placeToken, boolean isNew, CollaboratorsDo collaboratorsDo, ClasspageDo classpageDo, int k) {
		initWidget(uiBinder.createAndBindUi(this));

		this.isNew = isNew;
		this.collaboratorsDo = collaboratorsDo;
		this.classpageDo = classpageDo;
		this.position = k;

		panelMembers.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				btnRemove.setVisible(true);
				panelMembers.getElement().getStyle().setBackgroundColor("#F0F0F0");
			}
		});

		panelMembers.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				btnRemove.setVisible(false);
				panelMembers.getElement().getStyle().clearBackgroundColor();
			}
		});

		btnRemove.setVisible(false);

		setDebugId();
		if (isNew){
			new BackgroundColorEffect(panelCollaboratorsListContainer.getElement(),"#E7F1F8" ,"white", 5000);
		}
	}

	@Override
	public void onLoad() {
//		setDebugId();
	}
	/**
	 *
	 * @function setDebugId
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
	public void setDebugId() {
		String emailId = collaboratorsDo.getEmailId() != null ? collaboratorsDo.getEmailId() : null;
		String email = collaboratorsDo.getEmail() != null ? collaboratorsDo.getEmail() : null;
		String userName = collaboratorsDo.getUsername() != null ? collaboratorsDo.getUsername() : null;

		String status = collaboratorsDo.getStatus();

		panelCollaboratorsListContainer.getElement().setId(emailId);
		btnRemove.setText(i18N.GL0237());
		btnRemove.getElement().setId("" + position);
		btnRemove.getElement().setAttribute("alt",i18N.GL0237());
		btnRemove.getElement().setAttribute("title",i18N.GL0237());

		imgProfileImage.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				imgProfileImage.setUrl(defaultProfileImage);
			}
		});

		if (status.equalsIgnoreCase("pending")){
			lblEmailId.setText(emailId != null ? emailId : email);
			lblEmailId.getElement().setAttribute("alt",emailId != null ? emailId : email);
			lblEmailId.getElement().setAttribute("title",emailId != null ? emailId : email);
			imgProfileImage.setVisible(false);
			lblUserName.setVisible(false);

			btnRemove.getElement().getStyle().setMarginTop(0, Unit.PX);
			btnRemove.getElement().getStyle().setMarginLeft(0, Unit.PX);

			lblEmailId.getElement().getStyle().setMarginTop(10, Unit.PX);
			lblEmailId.getElement().getStyle().setHeight(24, Unit.PX);

			lblEmailId.getElement().getStyle().setWidth(81, Unit.PCT);

		}else{
			btnRemove.getElement().getStyle().clearMarginTop();
			btnRemove.getElement().getStyle().clearMarginLeft();

			lblEmailId.getElement().getStyle().clearMarginTop();
			lblEmailId.getElement().getStyle().clearHeight();

			lblEmailId.getElement().getStyle().clearWidth();

			imgProfileImage.setVisible(true);
			lblUserName.setVisible(true);

			lblUserName.setText(userName);
			lblUserName.getElement().setAttribute("alt",userName);
			lblUserName.getElement().setAttribute("title",userName);

			lblEmailId.setText(emailId != null ? emailId : email);
			lblEmailId.getElement().setAttribute("alt",emailId != null ? emailId : email);
			lblEmailId.getElement().setAttribute("title",emailId != null ? emailId : email);
			imgProfileImage.setUrl(collaboratorsDo.getProfileImageUrl());
		}
		lblUserName.getElement().setId(emailId);
		lblEmailId.getElement().setId(emailId);
		panelCollaboratorsListContainer.getElement().setId("pnlCollaboratorsListContainer");
		panelMembers.getElement().setId("epnlMembers");
		imgProfileImage.getElement().setId("imgProfileImage");

	}
	/**
	 *
	 * @function removeThisFromParent
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
	private void removeThisFromParent(){
		this.removeFromParent();
	}
	/**
	 *
	 * @function setCollabCount
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param count
	 * @parm(s) : @param type
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public abstract void setCollabCount(int count, String type);

	@UiHandler("btnRemove")
	public abstract void setStudentsListContainer(ClickEvent event);

}