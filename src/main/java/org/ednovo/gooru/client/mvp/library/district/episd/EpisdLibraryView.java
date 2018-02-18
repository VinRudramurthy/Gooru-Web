package org.ednovo.gooru.client.mvp.library.district.episd;

import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers; 

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class EpisdLibraryView extends BaseViewWithHandlers<EpisdLibraryViewUiHandlers> implements IsEpisdLibraryView {
	
	@UiField SimplePanel partnerPanel;
	
	private static EpisdLibraryViewUiBinder uiBinder = GWT.create(EpisdLibraryViewUiBinder.class);

	interface EpisdLibraryViewUiBinder extends UiBinder<Widget, EpisdLibraryView> {
	}
	
	public EpisdLibraryView() {
		setWidget(uiBinder.createAndBindUi(this));
		partnerPanel.getElement().setId("spnlPartnerPanel");
	}
	
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (content != null) {
			if (slot == EpisdLibraryViewUiHandlers.TYPE_FOLDERS_SLOT) {
				partnerPanel.setWidget(content);
			}
		}
	}
}
