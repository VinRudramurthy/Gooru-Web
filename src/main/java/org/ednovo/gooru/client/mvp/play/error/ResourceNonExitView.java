package org.ednovo.gooru.client.mvp.play.error;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ResourceNonExitView extends Composite {

	private static ResourceNonExitViewUiBinder uiBinder = GWT
			.create(ResourceNonExitViewUiBinder.class);

	interface ResourceNonExitViewUiBinder extends
			UiBinder<Widget, ResourceNonExitView> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField Image defaultImage;
	@UiField Label lblNoLongerMessage;
	@UiField HTMLPanel lblDesc;
	@UiField Anchor ancLearnMore;
	
	public ResourceNonExitView() {
		initWidget(uiBinder.createAndBindUi(this));
		defaultImage.setUrl("images/resource_error.png");
		defaultImage.getElement().setId("imgDefaultImage");

		ancLearnMore.setText(i18n.GL0343());
		ancLearnMore.getElement().setId("lnkAncLearnMore");
		ancLearnMore.getElement().setAttribute("alt",i18n.GL0343());
		ancLearnMore.getElement().setAttribute("title",i18n.GL0343());
		
		lblNoLongerMessage.setText(i18n.GL1760());
		lblNoLongerMessage.getElement().setId("lblNoLongerMessage");
		lblNoLongerMessage.getElement().setAttribute("alt",i18n.GL1760());
		lblNoLongerMessage.getElement().setAttribute("title",i18n.GL1760());
		
		lblDesc.getElement().setInnerHTML(i18n.GL1762());
		lblDesc.getElement().setId("lblDesc");
		lblDesc.getElement().setAttribute("alt",i18n.GL1762());
		lblDesc.getElement().setAttribute("title",i18n.GL1762());
	}
}
