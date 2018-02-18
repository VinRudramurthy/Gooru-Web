package org.ednovo.gooru.client.mvp.library.partner.esyp;

import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class EsypLibraryView extends BaseViewWithHandlers<EsypLibraryUiHandlers> implements IsEsypLibraryView {

	@UiField SimplePanel partnerPanel;
	
	private static EsypLibraryViewUiBinder uiBinder = GWT.create(EsypLibraryViewUiBinder.class);

	interface EsypLibraryViewUiBinder extends UiBinder<Widget, EsypLibraryView> {
	}
	
	public EsypLibraryView() {
		setWidget(uiBinder.createAndBindUi(this));
		partnerPanel.getElement().setId("spnlPartnerPanel");
	}
	
	public void setInSlot(Object slot, Widget content) {
		if (content != null) {
			if (slot == EsypLibraryUiHandlers.TYPE_FOLDERS_SLOT) {
				partnerPanel.setWidget(content);
			}
		}
	}

}
