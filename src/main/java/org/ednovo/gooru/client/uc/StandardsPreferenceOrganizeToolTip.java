package org.ednovo.gooru.client.uc;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class StandardsPreferenceOrganizeToolTip extends PopupPanel {

	private static StandardsPreferenceOrganizeToolTipUiBinder uiBinder = GWT
			.create(StandardsPreferenceOrganizeToolTipUiBinder.class);

	interface StandardsPreferenceOrganizeToolTipUiBinder extends
			UiBinder<Widget, StandardsPreferenceOrganizeToolTip> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField
	HTMLPanel lblTitle,panelCode;
	
	public StandardsPreferenceOrganizeToolTip() {
		setWidget(uiBinder.createAndBindUi(this));
		lblTitle.getElement().setInnerHTML(i18n.GL1613());
		panelCode.getElement().setId("pnlPanelCode");
		lblTitle.getElement().setId("pnlLblTitle");
		lblTitle.getElement().setAttribute("alt", i18n.GL1613());
		lblTitle.getElement().setAttribute("title", i18n.GL1613());
	}

}
