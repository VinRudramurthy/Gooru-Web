
/**
 * 
*/
package org.ednovo.gooru.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @fileName : PeListPanel.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 30-Jun-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer: 
 */
public class PeListPanel extends ComplexPanel {
	
	public PeListPanel(){
		setElement(Document.get().createPElement());
	}
	
	@Override
	public void add(Widget w){
		add(w,getElement());
	}
	
	@Override
	public void clear() {
		try {
			// doLogicalClear();
		} finally {
			// Remove all existing child nodes.
			Node child = getElement().getFirstChild();
			while (child != null) {
				getElement().removeChild(child);
				child = getElement().getFirstChild();
			}
		}
	}
	
	public void insert(Widget w,int beforeIndex){
		insert(w, getElement(), beforeIndex, true);
	}

}
