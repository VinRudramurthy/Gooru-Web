package org.ednovo.gooru.client.uc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class LiecenceTooltip extends PopupPanel {

	private static LiecenceTooltipUiBinder uiBinder = GWT
			.create(LiecenceTooltipUiBinder.class);

	interface LiecenceTooltipUiBinder extends UiBinder<Widget, LiecenceTooltip> {
	}
	@UiField Label codeLabel,descLabel;
	public LiecenceTooltip(String code,String desc, int leftPos, int rightPos) {
		setWidget(uiBinder.createAndBindUi(this));
		codeLabel.setText(code);
		descLabel.setText(desc);
		setPopupPosition(leftPos, rightPos);
	}

}
