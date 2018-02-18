package org.ednovo.gooru.client.mvp.library.partner.esyp;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public interface EsypLibraryUiHandlers extends BaseUiHandlers {
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_FOLDERS_SLOT = new Type<RevealContentHandler<?>>();
}
