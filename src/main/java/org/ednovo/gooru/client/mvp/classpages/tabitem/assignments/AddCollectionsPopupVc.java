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

import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/*
 * 
 * @fileName : AddCollectionsPopupVc.java
 * 
 * @description :This class is used to add collection in assignment
 * 
 * @version : 5.5
 * 
 * @date: Apr 17, 2013
 * 
 * @Author :Gooru Team
 * 
 * @Reviewer:
 */
public abstract class AddCollectionsPopupVc extends AppPopUp {

	@UiField
	Label btnAdd;

	@UiField
	Label btnCancel, collectionFirstElement,loadingLbl;
	/*
	 * @UiField ListBox copyPopupListBox;
	 */
	@UiField
	HTMLPanel addLabel, htmlScrollPanel, loadingPanel,chooseCollectionsLbl,cannotFindLbl;

	@UiField
	Label mandatorySelectCollectionLbl;

	@UiField
	ScrollPanel copyPopUpScrollHtmlPanel;
	@UiField
	HTMLEventPanel collectionListContainer;
	@UiField
	FocusPanel copyPopUpResourceListImage;
	@UiField
	Label nocollectionMsgLabel;

	/**
	 * This method is to get the mandatorySelectCollectionLbl
	 */
	public Label getMandatorySelectCollectionLbl() {
		return mandatorySelectCollectionLbl;
	}

	/**
	 * This method is to set the mandatorySelectCollectionLbl
	 */
	public void setMandatorySelectCollectionLbl(
			Label mandatorySelectCollectionLbl) {
		this.mandatorySelectCollectionLbl = mandatorySelectCollectionLbl;
	}

	List<Integer> collectionItemDoSize;

	/* Setter and getters */
	/**
	 * This method is used to get collectionItem size
	 * 
	 * @return collectionItemDoSize
	 */
	public List<Integer> getCollectionItemDoSize() {
		return collectionItemDoSize;
	}

	/**
	 * This method is used to set collectionItem size
	 */

	public void setCollectionItemDoSize(List<Integer> collectionItemDoSize) {
		this.collectionItemDoSize = collectionItemDoSize;
	}

	private static AddCollectionsPopupVcUiBinder uiBinder = GWT
			.create(AddCollectionsPopupVcUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface AddCollectionsPopupVcUiBinder extends
			UiBinder<Widget, AddCollectionsPopupVc> {
	}

	/**
	 * default constructor
	 */
	public AddCollectionsPopupVc() {
		super();
		this.getElement().getStyle().setWidth(400, Unit.PX);
		this.getElement().getStyle().setHeight(231, Unit.PX);
		setContent(i18n.GL1410(), uiBinder.createAndBindUi(this));
		
		chooseCollectionsLbl.getElement().setInnerText(i18n.GL1411()+i18n.GL_SPL_STAR());
		chooseCollectionsLbl.getElement().setId("lblChooseCollections");
		chooseCollectionsLbl.getElement().setAttribute("alt",i18n.GL1411());
		chooseCollectionsLbl.getElement().setAttribute("title",i18n.GL1411());
		
		nocollectionMsgLabel.setText(i18n.GL0995());
		nocollectionMsgLabel.getElement().setId("lblNoCollectionMsg");
		nocollectionMsgLabel.getElement().setAttribute("alt",i18n.GL0995());
		nocollectionMsgLabel.getElement().setAttribute("title",i18n.GL0995());
		
		btnAdd.setText(i18n.GL0590());
		btnAdd.getElement().setId("btnAdd");
		btnAdd.getElement().setAttribute("alt",i18n.GL0590());
		btnAdd.getElement().setAttribute("title",i18n.GL0590());
		
		btnCancel.setText(i18n.GL0142());
		btnCancel.getElement().setId("lblCancel");
		btnCancel.getElement().setAttribute("alt",i18n.GL0142());
		btnCancel.getElement().setAttribute("title",i18n.GL0142());
		
		loadingLbl.setText(i18n.GL0110()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP());
		loadingLbl.getElement().setId("lblLoading");
		loadingLbl.getElement().setAttribute("alt",i18n.GL0110()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP());
		loadingLbl.getElement().setAttribute("title",i18n.GL0110()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP());
		
		cannotFindLbl.getElement().setInnerText(i18n.GL1412());
		cannotFindLbl.getElement().setId("lblCannotFind");
		cannotFindLbl.getElement().setAttribute("alt",i18n.GL1412());
		cannotFindLbl.getElement().setAttribute("title",i18n.GL1412());
		
		//
		nocollectionMsgLabel.setVisible(false);
		setModal(true);
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		show();
		center();
		populateUserCollections();
		addLabel.setVisible(false);
		addLabel.getElement().setId("lblAdd");
		
		btnCancel.setVisible(false);
		loadingPanel.setVisible(true);
		
		
		copyPopUpResourceListImage.getElement().removeAttribute("tabindex");
		copyPopUpScrollHtmlPanel.getElement().getStyle()
				.setVisibility(Visibility.HIDDEN);
		collectionFirstElement.getElement().setAttribute("style",
				"padding-left:5px");
		collectionFirstElement.setText(i18n.GL1377()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP());
		collectionFirstElement.getElement().setId("lblCollectionFirstElement");
		collectionFirstElement.getElement().setAttribute("alt",i18n.GL1377()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP());
		collectionFirstElement.getElement().setAttribute("title",i18n.GL1377()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP()+i18n.GL_SPL_FULLSTOP());
		
		copyPopUpResourceListImage.getElement().setId("pnlCopyPopUpResourceListImage");
		copyPopUpScrollHtmlPanel.getElement().setId("spnlCopyPopUpScrollHtmlPanel");
		htmlScrollPanel.getElement().setId("pnlHtmlScroll");
		mandatorySelectCollectionLbl.getElement().setId("lblMandatorySelectCollection");
		collectionListContainer.getElement().setId("pnlCollectionListContainer");
		
		collectionFirstElement.getElement().setAttribute("style", "color:#999");
		/**
		 * on click for display list of collection in listbox
		 */
		copyPopUpResourceListImage.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (copyPopUpScrollHtmlPanel.getElement().getStyle()
						.getVisibility().equalsIgnoreCase("VISIBLE")) {
					copyPopUpScrollHtmlPanel.getElement().getStyle()
							.setVisibility(Visibility.HIDDEN);
				} else {
					copyPopUpScrollHtmlPanel.getElement().getStyle()
							.setVisibility(Visibility.VISIBLE);
				}
			}
		});
	}

	public abstract void populateUserCollections();

	/**
	 * for close add collection popup
	 * 
	 * @param clickEvent
	 */
	@UiHandler("btnCancel")
	public void onCancelClick(ClickEvent clickEvent) {
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		hide();
	}

	@UiHandler("btnAdd")
	public abstract void onAddClick(ClickEvent clickEvent);

	/**
	 * on click for hide listbox when user click outside the listbox
	 */
	@UiHandler("collectionListContainer")
	public void onpopUpCopyCollection(ClickEvent clickEvent) {
		if (copyPopUpScrollHtmlPanel.getElement().getStyle().getVisibility()
				.equalsIgnoreCase("VISIBLE")) {
			copyPopUpScrollHtmlPanel.getElement().getStyle()
					.setVisibility(Visibility.HIDDEN);
		}
	}

}
