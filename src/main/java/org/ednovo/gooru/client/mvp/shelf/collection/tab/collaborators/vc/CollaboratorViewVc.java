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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClassPageCollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.effects.BackgroundColorEffect;
import org.ednovo.gooru.client.mvp.search.event.DeselectRadioButtonEvent;
import org.ednovo.gooru.client.mvp.search.event.DeselectRadioButtonHandler;
import org.ednovo.gooru.client.mvp.search.event.RemoveCollaboratorObjectEvent;
import org.ednovo.gooru.client.mvp.search.event.RemoveCollaboratorObjectHandler;
import org.ednovo.gooru.client.mvp.search.event.SetCollabCountEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.search.event.SetPanelVisibilityEvent;
import org.ednovo.gooru.client.mvp.settings.CustomAnimation;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class CollaboratorViewVc extends Composite {

	private static CollaboratorViewVcUiBinder uiBinder = GWT.create(CollaboratorViewVcUiBinder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	interface CollaboratorViewVcUiBinder extends UiBinder<Widget, CollaboratorViewVc> {
	}


	List<ClassPageCollectionDo> classpageTitles = new ArrayList<ClassPageCollectionDo>();

	CollaboratorsDo collaboratorsDo = null;
	CollectionDo collectionDo = null;

	/* HTML5 Storage implementation for tab persistance */
	private Storage stockStore = null;

	boolean isYou = false;
	boolean isCreator = false;
	boolean isNew = false;

	int defaultCollabCount = 5;

	@UiField HTMLEventPanel panelYou, panelCollaborators;

	@UiField HTMLPanel panelCollaboratorsListContainer;

	@UiField Button btnRemoveFromList; // Remove yourselves from Collaborators list.

	@UiField Button btnRemoveCollab; // Remove other collaborators from list.

	@UiField Label lblYou, lblUserName, lblEmailId, lblRadioButton;

	@UiField Image imgProfileImage, imgYourPhoto;

	String defaultProfileImage = "images/settings/setting-user-image.png";

	public CollaboratorViewVc(String placeToken, final CollaboratorsDo collaboratorsDo, final CollectionDo collectionDo, boolean isYou, final boolean isCreator, boolean isNew) {
		initWidget(uiBinder.createAndBindUi(this));

		this.collaboratorsDo = collaboratorsDo;
		this.collectionDo = collectionDo;
		this.isYou = isYou;
		this.isCreator = isCreator;
		this.isNew = isNew;

		panelCollaborators.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				if (!collectionDo.isIsCollaborator()){
					btnRemoveCollab.setVisible(true);
					panelCollaborators.getElement().getStyle().setBackgroundColor("#F0F0F0");
				}

			}
		});

		panelCollaborators.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				if (!collectionDo.isIsCollaborator()){
					btnRemoveCollab.setVisible(false);
					panelCollaborators.getElement().getStyle().clearBackgroundColor();
				}
			}
		});


		DeselectRadioButtonHandler handlerDeselect = new DeselectRadioButtonHandler() {

			@Override
			public void setDeSelection() {
				deselectRadioButton();
			}
		};

		RemoveCollaboratorObjectHandler handlerRemove = new RemoveCollaboratorObjectHandler() {

			@Override
			public void removeFromParentByEmailId(String emailId) {
				if (emailId.trim().equalsIgnoreCase(lblEmailId.getText().trim())){
					CustomAnimation animation = new CustomAnimation(panelCollaboratorsListContainer);
					animation.run(300);
					removeThisFromParent();
				}
			}
		};

		lblRadioButton.setVisible(false);
		btnRemoveCollab.setVisible(false);

		AppClientFactory.getEventBus().addHandler(DeselectRadioButtonEvent.TYPE, handlerDeselect);
		AppClientFactory.getEventBus().addHandler(RemoveCollaboratorObjectEvent.TYPE, handlerRemove);

		setDebugId();
		if (isNew){
			new BackgroundColorEffect(panelCollaboratorsListContainer.getElement(),"#E7F1F8" ,"white", 5000);
		}
		if (collaboratorsDo !=null && collaboratorsDo.getGooruUid() != null){
			//Getting all the List of Classpages for the particular(collaborator) user
			/*AppClientFactory.getInjector().getClasspageService().getClasspagesListByCollectionId(collectionDo.getGooruOid(), collaboratorsDo.getGooruUid(), new SimpleAsyncCallback<ArrayList<ClassPageCollectionDo>>() {

				@Override
				public void onSuccess(ArrayList<ClassPageCollectionDo> result) {
					classpageTitles = result;
				}

			});*/
		}
	}

	@Override
	public void onLoad() {
		setDebugId();
	}

	public void setDebugId() {
		panelYou.getElement().setId("divYou");
		panelCollaborators.getElement().setId("divCollaborators");

		btnRemoveFromList.setText(i18n.GL0937());
		btnRemoveFromList.getElement().setAttribute("alt",i18n.GL0937());
		btnRemoveFromList.getElement().setAttribute("title",i18n.GL0937());
		btnRemoveFromList.getElement().setId("btnRemoveFromList");
		lblYou.setText(i18n.GL0938());
		lblYou.getElement().setAttribute("alt",i18n.GL0938());
		lblYou.getElement().setAttribute("title",i18n.GL0938());
		btnRemoveCollab.setText(i18n.GL0237());
		btnRemoveCollab.getElement().setAttribute("alt",i18n.GL0237());
		btnRemoveCollab.getElement().setAttribute("title",i18n.GL0237());
		btnRemoveCollab.setVisible(false);
		if (collaboratorsDo!=null && collaboratorsDo.getStatus().equalsIgnoreCase("pending")){
			panelCollaboratorsListContainer.getElement().setId(collaboratorsDo !=null && collaboratorsDo.getEmailId() !=null ? collaboratorsDo.getEmailId() : "divListPendingCollaborators");
		}else{
			panelCollaboratorsListContainer.getElement().setId(collaboratorsDo !=null && collaboratorsDo.getGooruUid() !=null ? collaboratorsDo.getGooruUid() : "divListActiveCollaborators");
		}


		panelYou.setVisible(isYou ? true : false);
		panelCollaborators.setVisible(!isYou ? true : false);
		if (isYou){

			panelCollaborators.getElement().getStyle().setMarginLeft(0, Unit.PX);
			imgYourPhoto.setUrl((collaboratorsDo.getProfileImageUrl() !=null ) ? collaboratorsDo.getProfileImageUrl() : defaultProfileImage);
		}else{
			panelCollaborators.getElement().getStyle().setMarginLeft(0, Unit.PX);
		}

		if (collaboratorsDo == null && isCreator){
			imgProfileImage.setUrl(AppClientFactory.getLoggedInUser().getSettings().getProfileImageUrl()+collectionDo.getUser().getGooruUId()+".png");
			lblUserName.setText((collectionDo.getUser() !=null && collectionDo.getUser().getUsername() !=null )  ? collectionDo.getUser().getUsername() : "");
			lblUserName.getElement().setAttribute("alt",(collectionDo.getUser() !=null && collectionDo.getUser().getUsername() !=null )  ? collectionDo.getUser().getUsername() : "");
			lblUserName.getElement().setAttribute("title",(collectionDo.getUser() !=null && collectionDo.getUser().getUsername() !=null )  ? collectionDo.getUser().getUsername() : "");
			lblEmailId.setText((collectionDo.getUser() !=null && collectionDo.getUser().getEmailId() !=null )  ? collectionDo.getUser().getEmailId() : "");
			lblEmailId.getElement().setAttribute("alt",(collectionDo.getUser() !=null && collectionDo.getUser().getEmailId() !=null )  ? collectionDo.getUser().getEmailId() : "");
			lblEmailId.getElement().setAttribute("title",(collectionDo.getUser() !=null && collectionDo.getUser().getEmailId() !=null )  ? collectionDo.getUser().getEmailId() : "");
			lblRadioButton.getElement().setId(lblEmailId.getText());
			lblRadioButton.getElement().setAttribute("userName", lblUserName.getText());
			btnRemoveCollab.getElement().setId(lblEmailId.getText());
			btnRemoveCollab.getElement().setAttribute("username", lblUserName.getText());

		}else if (collaboratorsDo != null && !isCreator){
			imgProfileImage.setUrl(AppClientFactory.getLoggedInUser().getSettings().getProfileImageUrl()+collaboratorsDo.getGooruUid()+".png");
			lblUserName.setText((collaboratorsDo.getUsername() !=null)  ? collaboratorsDo.getUsername() : "");
			lblUserName.getElement().setAttribute("alt",(collaboratorsDo.getUsername() !=null)  ? collaboratorsDo.getUsername() : "");
			lblUserName.getElement().setAttribute("title",(collaboratorsDo.getUsername() !=null)  ? collaboratorsDo.getUsername() : "");
			lblEmailId.setText((collaboratorsDo.getEmailId() !=null)  ? collaboratorsDo.getEmailId() : "");
			lblEmailId.getElement().setAttribute("alt",(collaboratorsDo.getEmailId() !=null)  ? collaboratorsDo.getEmailId() : "");
			lblEmailId.getElement().setAttribute("title",(collaboratorsDo.getEmailId() !=null)  ? collaboratorsDo.getEmailId() : "");
			lblRadioButton.getElement().setId(lblEmailId.getText());
			lblRadioButton.getElement().setAttribute("username", (collaboratorsDo.getUsername() !=null)  ? collaboratorsDo.getUsername() : lblEmailId.getText());
			btnRemoveCollab.getElement().setId(lblEmailId.getText());
			btnRemoveCollab.getElement().setAttribute("username", (collaboratorsDo.getUsername() !=null)  ? collaboratorsDo.getUsername() : lblEmailId.getText());
		}

		if (collectionDo!=null &&  collectionDo.isIsCollaborator()){
			btnRemoveCollab.setVisible(false);
		}else{
			lblUserName.getElement().getStyle().setDisplay(lblUserName.getText() !=null && !lblUserName.getText().equalsIgnoreCase("") ? Display.INLINE_BLOCK : Display.NONE);
		}
		//TODO - This should be managed from css class
		if (collaboratorsDo!=null){
			if (collaboratorsDo.getStatus().equalsIgnoreCase("pending")){
				imgProfileImage.getElement().getStyle().setDisplay(Display.NONE);
				lblEmailId.getElement().getStyle().setWidth(81, Unit.PCT);
				lblEmailId.getElement().getStyle().setPaddingLeft(15, Unit.PX);
				lblEmailId.getElement().getStyle().setMarginTop(11, Unit.PX);
				panelCollaborators.getElement().getStyle().setHeight(50, Unit.PX);
				btnRemoveCollab.getElement().getStyle().setMarginTop(4, Unit.PX);
			}else{
				panelCollaborators.getElement().getStyle().clearHeight();;
				btnRemoveCollab.getElement().getStyle().clearMarginTop();
				imgProfileImage.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
				lblEmailId.getElement().getStyle().clearWidth();
				lblEmailId.getElement().getStyle().clearPaddingLeft();
				lblEmailId.getElement().getStyle().clearMarginTop();
			}
		}
		imgProfileImage.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				imgProfileImage.setUrl(defaultProfileImage);
			}
		});
		imgYourPhoto.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				imgYourPhoto.setUrl(defaultProfileImage);
			}
		});
		imgYourPhoto.getElement().setId("imgYourPhoto");
		lblYou.getElement().setId("lblYou");
		imgProfileImage.getElement().setId("imgProfileImage");
		lblUserName.getElement().setId("lblUserName");
		lblEmailId.getElement().setId("lblEmailId");
	}

	@UiHandler("btnRemoveCollab")
	public void clickOnRemoveCollab(ClickEvent event){
		final String toRemove =  lblEmailId.getText();
		final String emailId = lblEmailId.getText();
		final String collectionId = collectionDo.getGooruOid();
		DeletePopupViewVc delete = new DeletePopupViewVc() {
			@Override
			public void onClickPositiveButton(ClickEvent event) {
				AppClientFactory.getInjector().getCollaboratorsService().removeCollaboratorsFromListByEmailIds(collectionId, "[\"" + toRemove +"\"]", new SimpleAsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						hide();
						Window.enableScrolling(true);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
						AppClientFactory.fireEvent(new RemoveCollaboratorObjectEvent(toRemove));
						if (collaboratorsDo.getStatus().equalsIgnoreCase("active")){
							AppClientFactory.fireEvent(new SetCollabCountEvent("decrementBy", 1));
							//collectionDo.getMeta().setCollaboratorCount(collectionDo.getMeta().getCollaboratorCount() -1);
						}
						setCollabCount(1, "decrementBy");
						btnRemoveCollab.setEnabled(true);
						btnRemoveCollab.removeStyleName("disabled");
						AppClientFactory.fireEvent(new SetPanelVisibilityEvent());
					}
				});
			}

			@Override
			public void onClickNegitiveButton(ClickEvent event) {
				Window.enableScrolling(true);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
				hide();
			}
		};
		if (classpageTitles.size() > 0 && collaboratorsDo.getStatus().equalsIgnoreCase("active")){
			delete.setPopupTitle(i18n.GL1163());
			delete.setNotes(StringUtil.generateMessage(i18n.GL1164(), lblUserName.getText() != null && !lblUserName.getText().equalsIgnoreCase("") ? "<i>"+lblUserName.getText()+"</i>" : "<i>"+lblEmailId.getText()+"</i>"));
			delete.setDescText(i18n.GL1165());
			delete.setPositiveButtonText(i18n.GL0237());
		}else{
			delete.setPopupTitle(i18n.GL1118());
			delete.setNotes(i18n.GL0748());
			delete.setDescText(StringUtil.generateMessage(i18n.GL1119(), lblUserName.getText() != null && !lblUserName.getText().equalsIgnoreCase("") ? "<i>"+lblUserName.getText()+"</i>" : "<i>"+lblEmailId.getText()+"</i>"));
			delete.setPositiveButtonText(i18n.GL0190());
		}
		delete.setPleaseWaitText(i18n.GL1021());
		delete.setNegitiveButtonText(i18n.GL0142());
		delete.center();
		delete.show();
	}

	@UiHandler("lblRadioButton")
	public void onSelect(ClickEvent event){
	}

	public void deselectRadioButton(){
		lblRadioButton.getElement().removeClassName("radio-selected-icon");
	}

	public List<String> getSelectedObjectId(){
		List<String> list = new ArrayList<String>();
		if (lblRadioButton.getStyleName().toString().contains("radio-selected-icon")){
			list.add(lblRadioButton.getElement().getId());
			list.add(lblRadioButton.getElement().getAttribute("username"));
		}else{
			return null;
		}
		return list;
	}

	private void removeThisFromParent(){
		this.removeFromParent();
	}

	@UiHandler("btnRemoveFromList")
	public void onClickRemoveMySelf(ClickEvent event){
		final String toRemove =  collaboratorsDo.getEmailId();
		final String collectionId = collectionDo.getGooruOid();
		DeletePopupViewVc delete = new DeletePopupViewVc() {
			@Override
			public void onClickPositiveButton(ClickEvent event) {
				AppClientFactory.getInjector().getCollaboratorsService().removeCollaboratorsFromListByEmailIds(collectionId, "[\"" + toRemove +"\"]", new SimpleAsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						hide();
						Window.enableScrolling(true);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
						setPersistantTabFlag("resourceTab");
						Map<String, String> map = StringUtil.splitQuery(Window.Location.getHref());
						if (map.size()>1){
							map.remove("id");
							AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, map);
						}else{
							AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT);
						}
					}
				});
			}

			@Override
			public void onClickNegitiveButton(ClickEvent event) {
				Window.enableScrolling(true);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
				hide();
			}
		};
		delete.setPopupTitle(i18n.GL0748());
		if (classpageTitles.size()>0){
			StringBuffer sb = new StringBuffer();
			String anchString = "<a href=\"{0}\" target=\"_blank\">{1}</a>";
			String classpageUrl = "#teach&pageSize=10&classpageId={0}&pageNum=0&pos=1";
			int count = classpageTitles.size() >= defaultCollabCount ? defaultCollabCount : classpageTitles.size();
			for (int i=0; i<count;i++){
				String url = StringUtil.generateMessage(classpageUrl, classpageTitles.get(i).getClasspageId());
				if (classpageTitles.size()==1){
					sb.append(StringUtil.generateMessage(anchString, url,classpageTitles.get(i).getTitle()));
				}else{
					if (i == (count-1)){
						sb.append(i18n.GL_GRR_AND()+" "+StringUtil.generateMessage(anchString, url,classpageTitles.get(i).getTitle()));
					}else{
						sb.append(StringUtil.generateMessage(anchString, url,classpageTitles.get(i).getTitle()) + ", ");
					}
				}
			}
			String remaining;
			if (classpageTitles.size()>defaultCollabCount){
				remaining = "+"+(classpageTitles.size() - count) +" "+ i18n.GL1153();
			}else{
				if (classpageTitles.size()==1){
					remaining = (" "+i18n.GL1155());
				}else{
					remaining = (" "+i18n.GL1154()+i18n.GL_SPL_EXCLAMATION());
				}
			}
			delete.setNotes(i18n.GL1156()+" "+sb.toString()+" "+remaining);
		}else{
			delete.setNotes(i18n.GL1128());
		}
		if (classpageTitles.size()>0){
			if (classpageTitles.size() == 1){
				delete.setDescText(i18n.GL1157()+ " "+i18n.GL1155()+".");
			}else{
				delete.setDescText(i18n.GL1157()+ " "+i18n.GL1154()+i18n.GL_SPL_EXCLAMATION()+".");
			}

		}else{
			delete.setDescText(i18n.GL1125());
		}
		if (classpageTitles.size()>0){
			delete.setPositiveButtonText(i18n.GL0237());
		}else{
			delete.setPositiveButtonText(i18n.GL0190());
		}
		delete.setPleaseWaitText(i18n.GL0339());
		delete.setNegitiveButtonText(i18n.GL0142());
		delete.center();
		delete.show();
	}
	/**
	 * Sets the incoming tabFlag into Persistant store
	 *
	 * @param flag
	 *            generated when tabs are being switched
	 */
	public void setPersistantTabFlag(String flag) {
		stockStore = Storage.getLocalStorageIfSupported();
		if (stockStore != null) {
			stockStore.setItem("tabKey", flag);
		}
	}

	public abstract void setCollabCount(int count, String type);

}