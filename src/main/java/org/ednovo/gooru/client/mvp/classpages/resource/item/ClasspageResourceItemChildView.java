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
package org.ednovo.gooru.client.mvp.classpages.resource.item;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.dnd.IsDraggableMirage;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.DeleteConfirmPopupVc;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
/**
 *
 * @fileName : ClasspageResourceItemChildView.java
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
public class ClasspageResourceItemChildView extends
		ChildView<ClasspageResourceItemChildPresenter> implements
		IsClasspageResourceItemView{

	@UiField(provided = true)
	ClasspageResourceItemCBundle res;

	private CollectionDo collectionDo;

	@UiField
	Label classpageTitleLbl, openClasspageLbl, studentViewLbl;

	@UiField
	HTMLPanel actionVerPanel;

	@UiField
	Label confirmDeleteLbl;
	@UiField HTMLPanel classpageItemPanel;

	DeleteConfirmPopupVc deleteConfirmVc =null;

//	private static final String REG_EXP = "^(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)$";


	private static ClasspageResourceItemChildViewUiBinder uiBinder = GWT
			.create(ClasspageResourceItemChildViewUiBinder.class);

	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface ClasspageResourceItemChildViewUiBinder extends
			UiBinder<Widget, ClasspageResourceItemChildView> {
	}


	/**
	 * Class constructor
	 *
	 * @param collectionItem
	 *            instance of {@link CollectionItemDo}
	 */
	public ClasspageResourceItemChildView(CollectionDo collection) {

		res = ClasspageResourceItemCBundle.INSTANCE;
		ClasspageResourceItemCBundle.INSTANCE.css().ensureInjected();
		initWidget(uiBinder.createAndBindUi(this));
		classpageTitleLbl.setText(i18n.GL1409());
		classpageTitleLbl.getElement().setId("lblClasspageTitle");
		classpageTitleLbl.getElement().setAttribute("alt",i18n.GL1409());
		classpageTitleLbl.getElement().setAttribute("title",i18n.GL1409());

		openClasspageLbl.setText(i18n.GL1115());
		openClasspageLbl.getElement().setId("lblOpenClasspage");
		openClasspageLbl.getElement().setAttribute("alt",i18n.GL1115());
		openClasspageLbl.getElement().setAttribute("title",i18n.GL1115());

		studentViewLbl.setText(i18n.GL0139());
		studentViewLbl.getElement().setId("lblStudentView");
		studentViewLbl.getElement().setAttribute("alt",i18n.GL0139());
		studentViewLbl.getElement().setAttribute("title",i18n.GL0139());

		confirmDeleteLbl.setText(i18n.GL0558());
		confirmDeleteLbl.getElement().setId("lblConfirmDeleteLbl");
		confirmDeleteLbl.getElement().setAttribute("alt",i18n.GL0558());
		confirmDeleteLbl.getElement().setAttribute("title",i18n.GL0558());
		this.collectionDo = collection;

		setData(collection);

		addDomHandler(new ActionPanelHover(), MouseOverEvent.getType());
		addDomHandler(new ActionPanelOut(), MouseOutEvent.getType());
		setPresenter(new ClasspageResourceItemChildPresenter(this));

		actionVerPanel.setVisible(false);
		actionVerPanel.getElement().setId("pnlActionVer");
		/**
		 * create delete confirmation pop and delete the collection if user wants
		 *
		 * @param clickEvent
		 *            instance of {@link ClickEvent}
		 */
		confirmDeleteLbl.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				deleteConfirmVc = new DeleteConfirmPopupVc(i18n.GL0748(),"\""+ collectionDo.getTitle() + "\"" + " "+i18n.GL0102()+i18n.GL_SPL_FULLSTOP())  {

					@Override
					public void onTextConfirmed() {
						getPresenter().deleteClasspage(collectionDo);
						deleteConfirmVc.hide();
						Window.enableScrolling(true);
				        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));

					}
				};
			}
		});

		studentViewLbl.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MixpanelUtil.Click_StudentView_Teachpage();
				Map<String, String> params = new HashMap<String, String>();
				params.put("id", collectionDo.getGooruOid());
				params.put("pageNum", "0");
				params.put("pos", "1");
				params.put("pageSize", "10");
				params.put("b", "true");
				params.put("source", "T");
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT, params);
			}
		});
		classpageItemPanel.getElement().setId("pnlClasspageItem");
	}

	@Override
	public void onPostCollectionDelete() {
		deleteConfirmVc.hide();
	}

	/**
	 *
	 * To show the ResourceMetaDataInfo Edit,Copy and Remove buttons
	 */
	private class ActionPanelHover implements MouseOverHandler {

		@Override
		public void onMouseOver(MouseOverEvent event) {
			actionVerPanel.setVisible(true);
		}
	}

	/**
	 *
	 * To hide the ResourceMetaDataInfo Edit,Copy and Remove buttons
	 */
	private class ActionPanelOut implements MouseOutHandler {

		@Override
		public void onMouseOut(MouseOutEvent event) {

			actionVerPanel.setVisible(false);

		}
	}

	/**
	 * set collection meta data , set title
	 *
	 *
	 * @param collection
	 *            instance of {@link CollectionDo}
	 */
	private void setData(CollectionDo collection) {

		classpageTitleLbl.setText(collection.getTitle());
		classpageTitleLbl.getElement().setAttribute("alt",collection.getTitle());
		classpageTitleLbl.getElement().setAttribute("title",collection.getTitle());
	}

	@UiHandler("openClasspageLbl")
	public void OnClickOpenClasspage(ClickEvent event){
		MixpanelUtil.Click_Open_Teachpage();
		Map<String, String> params = new HashMap<String, String>();
		params.put(UrlNavigationTokens.CLASSPAGEID, collectionDo.getGooruOid());
		params.put("pageSize", "10");
		params.put("pageNum", "0");
		params.put("pos", "1");
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.EDIT_CLASSPAGE, params,true);
	}


	@Override
	public Widget getDragHandle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IsDraggableMirage initDraggableMirage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDragBlur() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDragId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DRAG_TYPE getDragType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDragTopCorrection() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDragLeftCorrection() {
		// TODO Auto-generated method stub
		return 0;
	}



}
