package org.ednovo.gooru.client.mvp.home.library.customize;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.home.library.LibraryTopicListView;
import org.ednovo.gooru.client.mvp.play.collection.preview.home.assign.AssignPopUpCBundle;
import org.ednovo.gooru.client.mvp.profilepage.data.item.ProfileTopicListView;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshUserShelfCollectionsEvent;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.SetStyleForProfanity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author BLR Team
 *
 */
public abstract class RenameAndCustomizeLibraryPopUp extends PopupPanel{

	@UiField
	HTMLPanel popupContentAssign,loginCustom, copyCollectionSuccess, panelAssign,loadingImageLabel,popupcontentCustomize,customizeText;

	@UiField(provided = true)
	AssignPopUpCBundle res;

	@UiField
	Label cancelButton, assignDes,lblpopupTitle,lbltxtBoxTitle, errorLabel;

	@UiField
	Button backtoLibrary, editCollection;

	@UiField
	TextBox copycollectionTextbox;

	@Inject
	private ResourceServiceAsync resourceService;

	private SimpleAsyncCallback<CollectionDo> saveCollectionAsyncCallback;

	CollectionDo collectionDo = new CollectionDo();

	@UiTemplate("RenameAndCustomizeLibraryPopUp.ui.xml")
	interface Binder extends UiBinder<Widget, RenameAndCustomizeLibraryPopUp> {

	}

	private static final Binder binder = GWT.create(Binder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	private boolean isCustomizePopup = false,isHavingBadWords;


	/**
	 *
	 */
	public RenameAndCustomizeLibraryPopUp(String collectionId, final Boolean loginFlag, final String collectionTitle) {
		super(false);
		this.res = AssignPopUpCBundle.INSTANCE;
		res.css().ensureInjected();
		add(binder.createAndBindUi(this));

		copycollectionTextbox.setText(collectionTitle);
		copycollectionTextbox.getElement().setId("txtCopycollectionTextbox");
		copycollectionTextbox.getElement().setAttribute("alt",collectionTitle);
		copycollectionTextbox.getElement().setAttribute("title",collectionTitle);
		copycollectionTextbox.getElement().setAttribute("spellcheck","true");

		AppClientFactory.getInjector().getClasspageService().getSCollIdClasspageById(collectionId, new SimpleAsyncCallback<CollectionDo>(){

			@Override
			public void onSuccess(CollectionDo result) {
				collectionDo = result;
				MixpanelUtil.Preview_Click_Customize_successful();
				if (loginFlag) {
					loginCustom.setVisible(true);
					copyCollectionSuccess.setVisible(false);
				LoginPluginView assignWidget = new LoginPluginView(result,collectionTitle) {

						@Override
						public void closePoupfromChild() {

							hide();
						}

						@Override
						public void showSuccessMsgfromChild(String collectionId,String collectionTitle) {
							showSuccessMsg(collectionId,collectionTitle);

						}
					};
					loginCustom.add(assignWidget);
				} else {
					loginCustom.setVisible(false);
					copyCollectionSuccess.setVisible(true);
					panelAssign.getElement().getStyle().setHeight(367, Unit.PX);
				}
				popupcontentCustomize.setVisible(true);
				loadingImageLabel.setVisible(false);
			}
		});
		errorLabel.getElement().setId("errlblErrorLabel");
		errorLabel.setVisible(false);
		this.setGlassEnabled(true);
		customizeText.getElement().setInnerHTML(i18n.GL0743());
		customizeText.getElement().setId("pnlCustomizeText");
		customizeText.getElement().setAttribute("alt",i18n.GL0743());
		customizeText.getElement().setAttribute("title",i18n.GL0743());

		backtoLibrary.setText(i18n.GL0142());
		backtoLibrary.getElement().setId("btnBacktoLibrary");
		backtoLibrary.getElement().setAttribute("alt",i18n.GL0142());
		backtoLibrary.getElement().setAttribute("title",i18n.GL0142());

		editCollection.setText(i18n.GL0590());
		editCollection.getElement().setId("btnEditCollection");
		editCollection.getElement().setAttribute("alt",i18n.GL0590());
		editCollection.getElement().setAttribute("title",i18n.GL0590());
		panelAssign.getElement().setAttribute("style", "min-height:367px");
		panelAssign.getElement().getStyle().setMarginBottom(3, Unit.PX);
		copycollectionTextbox.setMaxLength(50);
		Window.enableScrolling(false);
		this.getElement().getStyle().setZIndex(99999);
		this.getGlassElement().setAttribute("style", "z-index:99999; position:absolute; left:0px; top:0px;");
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));

		popupcontentCustomize.setVisible(false);
		loadingImageLabel.setVisible(true);

		editCollection.getElement().setAttribute("collectionId", collectionId);

		assignDes.setText(i18n.GL0744());
		assignDes.getElement().setId("lblAssignDes");
		assignDes.getElement().setAttribute("alt",i18n.GL0744());
		assignDes.getElement().setAttribute("title",i18n.GL0744());

		lblpopupTitle.setText(i18n.GL0743());
		lblpopupTitle.getElement().setId("lblpopupTitle");
		lblpopupTitle.getElement().setAttribute("alt",i18n.GL0743());
		lblpopupTitle.getElement().setAttribute("title",i18n.GL0743());

		lbltxtBoxTitle.setText(i18n.GL0553());
		lbltxtBoxTitle.getElement().setId("lbltxtBoxTitle");
		lbltxtBoxTitle.getElement().setAttribute("alt",i18n.GL0553());
		lbltxtBoxTitle.getElement().setAttribute("title",i18n.GL0553());

		copycollectionTextbox.addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				Map<String, String> parms = new HashMap<String, String>();
				parms.put("text", copycollectionTextbox.getValue());
				AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean value) {
						isHavingBadWords=value;
						SetStyleForProfanity.SetStyleForProfanityForTextBox(copycollectionTextbox, errorLabel, value);
					}
				});
			}
		});

		copycollectionTextbox.addKeyUpHandler(new OnkeyUp());
		MixpanelUtil.mixpanelEvent("CoursePage_customize_collection");
		panelAssign.getElement().setId("pnlPanelAssign");
		cancelButton.getElement().setId("btnCancelButton");
		loadingImageLabel.getElement().setId("pnlLoadingImageLabel");
		popupcontentCustomize.getElement().setId("pnlPopupcontentCustomize");
		loginCustom.getElement().setId("pnlLoginCustom");
		copyCollectionSuccess.getElement().setId("pnlCopyCollectionSuccess");
		popupContentAssign.getElement().setId("pnlPopupContentAssign");

		this.center();

	}


	public abstract void closePoup();

	public ResourceServiceAsync getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceServiceAsync resourceService) {
		this.resourceService = resourceService;
	}

	public void showSuccessMsg(String collectionId,String collectionTitle) {

		loginCustom.setVisible(false);
		copyCollectionSuccess.setVisible(true);
		editCollection.getElement().setAttribute("collectionId", collectionId);
		customizeText.getElement().setInnerHTML(i18n.GL0743());
		panelAssign.getElement().getStyle().setHeight(367, Unit.PX);
	}

	/**
	 * Added click handler to hide the login popup.
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("cancelButton")
	public void onCancelClicked(ClickEvent clickEvent) {
		closePoup();
	}

	@UiHandler("backtoLibrary")
	public void onbacktoLibrarybuttonClicked(ClickEvent clickEvent) {
		closePoup();
	}

	@UiHandler("editCollection")
	public void onEditcollectionbuttonClicked(ClickEvent clickEvent) {

		Storage stockStore = Storage.getLocalStorageIfSupported();

		if (stockStore != null) {
			stockStore.setItem("tabKey", "resourceTab");
		}
				Map<String, String> parms = new HashMap<String, String>();
				parms.put("text", copycollectionTextbox.getValue());
				AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean value) {
						isHavingBadWords=value;
						if(value){
							SetStyleForProfanity.SetStyleForProfanityForTextBox(copycollectionTextbox, errorLabel, value);
						}else{
							String collectionTitle = copycollectionTextbox.getText();
							if(!collectionTitle.isEmpty() && !collectionTitle.trim().isEmpty())
							{
								hide();
								LibraryTopicListView.isCustomizePopup=false;
								ProfileTopicListView.isCustomizePopup=false;
								if(!isCustomizePopup){
									isCustomizePopup=true;
									Boolean loginFlag = false;
									if (AppClientFactory.isAnonymous()){
										loginFlag = true;
									}
									else
									{
										loginFlag = false;
									}
									collectionDo.setTitle(collectionTitle);
									AppClientFactory
									.getInjector()
									.getResourceService()
									.copyCollection(collectionDo, "true", null,
											getSaveCollectionAsyncCallback());
								}

							}
							else
							{
								errorLabel.setText(i18n.GL0693());
								errorLabel.getElement().setAttribute("alt",i18n.GL0693());
								errorLabel.getElement().setAttribute("title",i18n.GL0693());
								errorLabel.setVisible(true);
							}
						}
					}
				});
	}


	/**
	 * @return instance of collectionDo after collection save
	 */
	public SimpleAsyncCallback<CollectionDo> getSaveCollectionAsyncCallback() {
		if (saveCollectionAsyncCallback == null) {
			saveCollectionAsyncCallback = new SimpleAsyncCallback<CollectionDo>() {

				@Override
				public void onSuccess(CollectionDo result) {

						AppClientFactory.getPlaceManager().revealPlace(
							PlaceTokens.SHELF,
							new String[] {
									"id",
									result.getGooruOid()});
						AppClientFactory.fireEvent(new RefreshUserShelfCollectionsEvent());
						hide();
					//closePoup();
				}
			};
		}
		return saveCollectionAsyncCallback;
	}
	public class OnkeyPress implements KeyPressHandler
	{
		@Override
		public void onKeyPress(KeyPressEvent event) {
			String collTitle=copycollectionTextbox.getText().trim();
			if(collTitle.length()>=50){
				errorLabel.setText(i18n.GL0143());
				errorLabel.getElement().setAttribute("alt",i18n.GL0143());
				errorLabel.getElement().setAttribute("title",i18n.GL0143());
				errorLabel.setVisible(true);
			}else{
				errorLabel.setText("");
				errorLabel.getElement().setAttribute("alt","");
				errorLabel.getElement().setAttribute("title","");
				errorLabel.setVisible(false);
			}

		}
	}
	public class OnkeyUp implements KeyUpHandler{

			@Override
			public void onKeyUp(KeyUpEvent event) {
				String collTitle=copycollectionTextbox.getText().trim();
				if(collTitle.length()>=50){
					errorLabel.setText(i18n.GL0143());
					errorLabel.getElement().setAttribute("alt",i18n.GL0143());
					errorLabel.getElement().setAttribute("title",i18n.GL0143());
					errorLabel.setVisible(true);
				}else{
					errorLabel.setText("");
					errorLabel.getElement().setAttribute("alt","");
					errorLabel.getElement().setAttribute("title","");
					errorLabel.setVisible(false);
				}

			}
		}
}
