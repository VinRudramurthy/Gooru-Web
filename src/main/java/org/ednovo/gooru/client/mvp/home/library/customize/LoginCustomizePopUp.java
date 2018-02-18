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
package org.ednovo.gooru.client.mvp.home.library.customize;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.home.library.assign.AssignPopUpCBundle;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshCollectionInShelfListEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author BLR Team
 * 
 */
public abstract class LoginCustomizePopUp extends PopupPanel {

	@UiField
	HTMLPanel loginCustom, copyCollectionSuccess, panelAssign,loadingImageLabel,popupcontentCustomize;

	@UiField(provided = true)
	AssignPopUpCBundle res;

	@UiField
	Label cancelButton, successDesc;

	@UiField
	Button backtoLibrary, editCollection;

	@Inject
	private ResourceServiceAsync resourceService;
	private SimpleAsyncCallback<CollectionDo> saveCollectionAsyncCallback;

	@UiTemplate("LoginCustomizePopUp.ui.xml")
	interface Binder extends UiBinder<Widget, LoginCustomizePopUp> {

	}

	private static final Binder binder = GWT.create(Binder.class);
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * 
	 */
	public LoginCustomizePopUp(String collectionId, final Boolean loginFlag,final String collectionTitle) {
		super(false);
		this.res = AssignPopUpCBundle.INSTANCE;
		res.css().ensureInjected();
		add(binder.createAndBindUi(this));
		this.setGlassEnabled(true);

		panelAssign.getElement().getStyle().setMarginBottom(10, Unit.PX);
		loginCustom.getElement().getStyle().setMarginBottom(15, Unit.PX);
		
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
		
		popupcontentCustomize.setVisible(false);
		loadingImageLabel.setVisible(true);

		AppClientFactory.getInjector().getClasspageService().getSCollIdClasspageById(collectionId, new SimpleAsyncCallback<CollectionDo>(){

			@Override
			public void onSuccess(CollectionDo result) {
				successDesc.setText(i18n.GL0476());

				if (loginFlag) {
					loginCustom.setVisible(true);
					copyCollectionSuccess.setVisible(false);
				LoginPluginView assignWidget = new LoginPluginView(result,collectionTitle) {
						
						@Override
						public void closePoupfromChild() {
							closePoup();
						}

						@Override
						public void showSuccessMsgfromChild(String collectionId,String collectionTitle) {
							showSuccessMsg(collectionId);
							
						}
					};
					loginCustom.add(assignWidget);
				} else {
					loginCustom.setVisible(false);
					copyCollectionSuccess.setVisible(true);
					AppClientFactory
							.getInjector()
							.getResourceService()
							.copyCollection(result, "true", null,
									getSaveCollectionAsyncCallback());

				}
				popupcontentCustomize.setVisible(true);
				loadingImageLabel.setVisible(false);
			}
		});
		
		MixpanelUtil.mixpanelEvent("CoursePage_customize_collection");
		this.center();

	}

	/**
	 * @return instance of collectionDo after collection save
	 */
	public SimpleAsyncCallback<CollectionDo> getSaveCollectionAsyncCallback() {
		if (saveCollectionAsyncCallback == null) {
			saveCollectionAsyncCallback = new SimpleAsyncCallback<CollectionDo>() {

				@Override
				public void onSuccess(CollectionDo result) {
					showSuccessMsg(result.getGooruOid());
					AppClientFactory.fireEvent(new RefreshCollectionInShelfListEvent(result, RefreshType.INSERT_AND_VIEW));
				}
			};
		}
		return saveCollectionAsyncCallback;
	}

	public abstract void closePoup();

	public ResourceServiceAsync getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceServiceAsync resourceService) {
		this.resourceService = resourceService;
	}

	public void showSuccessMsg(String collectionId) {
		loginCustom.setVisible(false);
		copyCollectionSuccess.setVisible(true);
		editCollection.getElement().setAttribute("collectionId", collectionId);

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
		AppClientFactory.getPlaceManager().revealPlace(
				PlaceTokens.SHELF,
				new String[] {
						"id",
						editCollection.getElement()
								.getAttribute("collectionId") });
		closePoup();
	}
}
