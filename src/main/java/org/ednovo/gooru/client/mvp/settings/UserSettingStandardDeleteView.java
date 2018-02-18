package org.ednovo.gooru.client.mvp.settings;

import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.home.library.events.StandardPreferenceSettingEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
public class UserSettingStandardDeleteView extends PopupPanel {

	private static UserSettingStandardDeleteViewUiBinder uiBinder = GWT
			.create(UserSettingStandardDeleteViewUiBinder.class);

	interface UserSettingStandardDeleteViewUiBinder extends
			UiBinder<Widget, UserSettingStandardDeleteView> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField Label titleLabel,headerLabel,descriptionLabel;
	@UiField Button cancelButton,removeButton;
	String gooruUid="";
	String USER_TAXONOMY_ROOT_CODE="user_taxonomy_root_code";
	Label standardSavingTextLabel;
	HTMLPanel standardsSaveCancelButtonContainer;
	Button standardsEditButton;
	List<String> getUserCodeId;
	private static final String USER_META_ACTIVE_FLAG = "0";
	
	public UserSettingStandardDeleteView(String gooruUid,Button standardsEditButton,HTMLPanel standardsSaveCancelButtonContainer,Label standardSavingTextLabel) {
		setWidget(uiBinder.createAndBindUi(this));
		this.gooruUid=gooruUid;
		this.standardsEditButton=standardsEditButton;
		this.standardsSaveCancelButtonContainer=standardsSaveCancelButtonContainer;
		this.standardSavingTextLabel=standardSavingTextLabel;
			
		setTextAndUi();
		this.setGlassEnabled(true);
		this.setPixelSize(450, 252);
		Window.enableScrolling(false);
	}
	public void setTextAndUi()
	{
		titleLabel.setText(i18n.GL1162());	
		titleLabel.getElement().setId("lblTitleLabel");
		titleLabel.getElement().setAttribute("alt", i18n.GL1162());
		titleLabel.getElement().setAttribute("title", i18n.GL1162());
		//headerLabel.setText(MessageProperties.i18n.GL1565);	
		descriptionLabel.setText(i18n.GL1564());
		descriptionLabel.getElement().setId("lblDescriptionLabel");
		descriptionLabel.getElement().setAttribute("alt", i18n.GL1564());
		descriptionLabel.getElement().setAttribute("title", i18n.GL1564());
		cancelButton.setText(i18n.GL0142());	
		cancelButton.getElement().setId("btnCancelButton");
		cancelButton.getElement().setAttribute("alt", i18n.GL0142());
		cancelButton.getElement().setAttribute("title", i18n.GL0142());
		removeButton.setText(i18n.GL0237());
		removeButton.getElement().setId("btnRemoveButton");
		removeButton.getElement().setAttribute("alt", i18n.GL0237());
		removeButton.getElement().setAttribute("title", i18n.GL0237());
	}
	 @UiHandler("cancelButton")
	 public void onClickCancelButton(ClickEvent event)
	 {
		 this.hide();
		 Window.enableScrolling(true);
	 }
	 @UiHandler("removeButton")
	 public void onClickremoveButton(ClickEvent event)
	 {
		 this.hide();
		 Window.enableScrolling(true);
		 standardsEditButton.setVisible(false);
		 standardsSaveCancelButtonContainer.setVisible(false);
		 standardSavingTextLabel.setText(i18n.GL0808());
		 AppClientFactory.getInjector().getUserService().updatePartyCustomField(gooruUid,USER_TAXONOMY_ROOT_CODE,"",new SimpleAsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					standardsEditButton.setVisible(true);
					standardSavingTextLabel.setText("");
					AppClientFactory.getInjector().getUserService().getUserProfileV2Details(gooruUid,USER_META_ACTIVE_FLAG,
							new SimpleAsyncCallback<ProfileDo>() {

								@Override
								public void onSuccess(ProfileDo profileObj) {
									if(profileObj.getUser().getMeta() != null && profileObj.getUser().getMeta().getTaxonomyPreference() != null && profileObj.getUser().getMeta().getTaxonomyPreference().getCode() != null){
										AppClientFactory.fireEvent(new StandardPreferenceSettingEvent(profileObj.getUser().getMeta().getTaxonomyPreference().getCode()));
									}
								}

							});
					
				}
			});
	 }
}
