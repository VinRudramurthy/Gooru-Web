package org.ednovo.gooru.client.mvp.home.library.events;

import org.ednovo.gooru.application.shared.model.library.StandardsDo;

import com.google.gwt.event.shared.EventHandler;

public interface SetStandardDoHandler extends EventHandler {
	void setStandardDo(String subjectCode, StandardsDo standardDo);
}