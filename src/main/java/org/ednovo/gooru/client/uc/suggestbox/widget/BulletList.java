package org.ednovo.gooru.client.uc.suggestbox.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;
/**
 * 
 * @fileName : BulletList.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: Jan 29, 2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class BulletList extends ComplexPanel {
	public BulletList() {
		setElement(DOM.createElement("UL"));
	}

	public void add(Widget w) {
		super.add(w, getElement());
	}

	public void insert(Widget w, int beforeIndex) {
		super.insert(w, getElement(), beforeIndex, true);
	}
}
