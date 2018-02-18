package org.ednovo.gooru.client.uc;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Widget;

public class LabelPanel extends ComplexPanel implements InsertPanel,HasClickHandlers{
	String text;
	
	public LabelPanel(){
		setElement(Document.get().createLabelElement());
	}
	
	@Override
	public void add(Widget w){
	add(w,getElement());
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, ClickEvent.getType());
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		getElement().setInnerText(text);
	}
	
	@Override
	public void insert(Widget w, int beforeIndex) {
		// TODO Auto-generated method stub
		insert(w, getElement(), beforeIndex, true);
	}
	
}
