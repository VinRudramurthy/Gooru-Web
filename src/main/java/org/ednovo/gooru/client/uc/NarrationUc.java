
/**
 * 
 */
package org.ednovo.gooru.client.uc;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;


/**
 * @fileName : NarrationUc.java
 *
 * @description : This class is used to show the Author details of resource
 *                 when click on narration in player.
 *                 
 * @version : 1.0
 *
 * @date: 31-Dec-2013
 *
 * @Author: Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public abstract class NarrationUc extends PopupPanel {

	private static NarrationUcUiBinder uiBinder = GWT
			.create(NarrationUcUiBinder.class);

	interface NarrationUcUiBinder extends UiBinder<Widget, NarrationUc> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField Label lblResourceTitle,lblClose,lblUserName;

	@UiField Button btnOk;

	@UiField HTMLPanel lblAboutAuthor;

	@UiField Image userImage;

	@Inject
	PreviewPlayerView previewPlayerView;

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
	public NarrationUc() {
		setWidget(uiBinder.createAndBindUi(this));
		setGlassEnabled(true);
		this.getElement().getStyle().setBackgroundColor("transparent");
		lblResourceTitle.getElement().setId("lblLblResourceTitle");
		lblClose.getElement().setId("lblLblClose");
		userImage.getElement().setId("imgUserImage");
		lblAboutAuthor.getElement().setId("pnlLblAboutAuthor");
		btnOk.getElement().setId("btnBtnOk");
		center();
		show();
		}
	/**
	 * Class Constructor
	 * @param collectionItemDo
	 */
	public NarrationUc(CollectionItemDo collectionItemDo) {
		super(true);
		setWidget(uiBinder.createAndBindUi(this));
		lblResourceTitle.setText("1.Resource Title");
		lblResourceTitle.getElement().setId("lblLblResourceTitle");
		lblClose.getElement().setId("lblLblClose");
		userImage.getElement().setId("imgUserImage");
		lblUserName.setText(i18n.GL1035());
		lblUserName.getElement().setId("lblLblUserName");
		lblUserName.getElement().setAttribute("alt", i18n.GL1035());
		lblUserName.getElement().setAttribute("title", i18n.GL1035());
		lblAboutAuthor.getElement().setId("pnlLblAboutAuthor");
		btnOk.getElement().setId("btnBtnOk");
		btnOk.setText(i18n.GL0703());
		btnOk.getElement().setAttribute("alt", i18n.GL0703());
		btnOk.getElement().setAttribute("title", i18n.GL0703());
		setUserProfileDetails(collectionItemDo);
		setUserProfileImage(collectionItemDo.getResource().getUser().getGooruUId());
		setGlassEnabled(true);
		this.getElement().getStyle().setBackgroundColor("transparent");
		center();
		show();
	}


	public abstract void modifyUrl();

		/**
		 * @function setUserProfileDetails 
		 *  
		 * @description : setting user details in Narration popup.
		 *  
		 * @parm(s) : @param collectionItemDo {@link CollectionItemDo}
		 * 
		 */

		private void setUserProfileDetails(CollectionItemDo collectionItemDo) {
			if(collectionItemDo.getResource().getTitle()!=null){
				String resourceName=collectionItemDo.getResource().getTitle();
				int sequenceNumber=collectionItemDo.getItemSequence();
				if(resourceName.length() > 50){
					lblResourceTitle.setText(sequenceNumber+". "+resourceName.substring(0, 50)+"...");
					lblResourceTitle.getElement().setAttribute("alt", sequenceNumber+". "+resourceName);
					lblResourceTitle.getElement().setAttribute("title", sequenceNumber+". "+resourceName);
					
				}else{
					lblResourceTitle.setText(sequenceNumber+". "+resourceName);
					lblResourceTitle.getElement().setAttribute("alt", sequenceNumber+". "+resourceName);
					lblResourceTitle.getElement().setAttribute("title", sequenceNumber+". "+resourceName);
				}
			}else{
				lblResourceTitle.setText("");
			}

			if(collectionItemDo.getNarration()!=null){
				if(collectionItemDo.getNarration().length() > 300){
					lblAboutAuthor.add(new HTML(collectionItemDo.getNarration().substring(0, 300)+"..."));
				}else{
					lblAboutAuthor.add(new HTML(collectionItemDo.getNarration()));
				}
			}
			if(collectionItemDo.getResource().getUser().getUsername()!=null)
				lblUserName.setText(collectionItemDo.getResource().getUser().getUsername());
			lblUserName.getElement().setAttribute("alt", collectionItemDo.getResource().getUser().getUsername());
			lblUserName.getElement().setAttribute("title", collectionItemDo.getResource().getUser().getUsername());
		}

		/**
		 * @function setUserProfileImage 
		 *  
		 * @description : setting the Author image
		 *  
		 * @parm(s) : @param profileUserId {@link String}
		 * 
		 */
		private void setUserProfileImage(String profileUserId) {
			userImage.setUrl(AppClientFactory.loggedInUser.getSettings().getProfileImageUrl()+profileUserId+".png");
		}
		/**
		 * 
		 * @function clickOnCloseBtn 
		 *  
		 * @description : This abstract method to close the narration popup.
		 *  
		 * @parm(s) : @param clickEvent {@link ClickEvent}
		 * 
		 */

		@UiHandler("lblClose")
		public abstract void clickOnCloseBtn(ClickEvent clickEvent);

		/**
		 * 
		 * @function clickOnOkBtn 
		 *  
		 * @description :This abstract method to close the narration popup.
		 *  
		 * @parm(s) : @param clickEvent {@link ClickEvent}
		 * 
		 */
		@UiHandler("btnOk")
		public abstract void clickOnOkBtn(ClickEvent clickEvent);
		
		
		@Override
		public void hide(boolean autoClose) {
			super.hide(true);
			modifyUrl();
		}

	}
