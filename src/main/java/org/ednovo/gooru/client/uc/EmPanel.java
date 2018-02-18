package org.ednovo.gooru.client.uc;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Widget;

public class EmPanel extends ComplexPanel implements InsertPanel {
	String text;
	public EmPanel() {
		setElement(Document.get().createElement("em"));
    }
	
	@Override
	public void add(Widget w){
	add(w,getElement());
	}

	@Override
	public void insert(Widget w, int beforeIndex) {
		insert(w, getElement(), beforeIndex, true);
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		getElement().setInnerText(text);
		this.text = text;
	}
}
