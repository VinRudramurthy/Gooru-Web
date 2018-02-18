package org.ednovo.gooru.client.mvp.library.partner.tical;

import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class TicalLibraryView extends BaseViewWithHandlers<TicalLibraryUiHandlers> implements IsTicalLibraryView {


	@UiField SimplePanel partnerPanel;

	private static TicalLibraryViewUiBinder uiBinder = GWT.create(TicalLibraryViewUiBinder.class);

	interface TicalLibraryViewUiBinder extends UiBinder<Widget, TicalLibraryView> {
	}
	
	public TicalLibraryView() {
		setWidget(uiBinder.createAndBindUi(this));
		partnerPanel.getElement().setId("spnlPartnerPanel");
	}
	
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (content != null) {
			if (slot == TicalLibraryUiHandlers.TYPE_FOLDERS_SLOT) {
				partnerPanel.setWidget(content);
			}
		}
	}
}
