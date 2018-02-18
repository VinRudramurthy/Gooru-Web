package org.ednovo.gooru.client.mvp.search.util;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class NoResultsLibraryWidget extends Composite {

	private static NoResultsLibraryWidgetUiBinder uiBinder = GWT
			.create(NoResultsLibraryWidgetUiBinder.class);

	@UiField InlineLabel libName;
	@UiField Label libImage;
	@UiField HTMLEventPanel libOuterDiv;

	interface NoResultsLibraryWidgetUiBinder extends
			UiBinder<Widget, NoResultsLibraryWidget> {
	}
	public NoResultsLibraryWidget(String libraryName, final String libraryClickUrl,String className) {
		initWidget(uiBinder.createAndBindUi(this));
		libName.setText(libraryName);
		libOuterDiv.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
			AppClientFactory.getPlaceManager().revealPlace(libraryClickUrl);
			}
		});
		//libImage.setUrl(libraryImageUrl);
		libImage.setStyleName("libSprite");
		if (!StringUtil.isEmpty(className)){
			libImage.addStyleName(className);
		}

	}
}
