package org.ednovo.gooru.client.uc;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class StandardPreferenceTooltip extends PopupPanel {

	private static StandardPreferenceTooltipUiBinder uiBinder = GWT
			.create(StandardPreferenceTooltipUiBinder.class);

	interface StandardPreferenceTooltipUiBinder extends
			UiBinder<Widget, StandardPreferenceTooltip> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField
	HTMLPanel panelCode;
	
	@UiField
	HTMLPanel lblTitle;
	public StandardPreferenceTooltip() {
		setWidget(uiBinder.createAndBindUi(this));
		panelCode.getElement().setId("pnlpanelCode");
		lblTitle.getElement().setInnerHTML(i18n.GL1613());
		lblTitle.getElement().setId("pnlLblTitle");
		lblTitle.getElement().setAttribute("alt", i18n.GL1613());
		lblTitle.getElement().setAttribute("title", i18n.GL1613());
	}

}
