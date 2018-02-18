/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 *
 *  http://www.goorulearning.org/
 *
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package org.ednovo.gooru.client.mvp.gshelf.util;

import org.ednovo.gooru.client.uc.LiPanel;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
/**
 * This file is used for creating Li item with close button.
 */
public class LiPanelWithClose extends Composite{
	Anchor lnkClose;
	long id;
	String name;
	int relatedId;
	int relatedSubjectId;
	String stdTitle;
	int differenceId;

	public LiPanelWithClose(String text){
		 LiPanel panel = new LiPanel();
		 Label titleText=new Label(text);
		 setStdTitle(text);
		 titleText.addStyleName("inlineBlock");
		 lnkClose=new Anchor("x");
		 panel.add(titleText);
		 panel.add(lnkClose);
		 initWidget(panel);
	}
	/**
	 * This method is used to get close button
	 * @return
	 */
	public Anchor getCloseButton(){
		return lnkClose;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRelatedId() {
		return relatedId;
	}
	public void setRelatedId(int relatedId) {
		this.relatedId = relatedId;
	}
	public int getRelatedSubjectId() {
		return relatedSubjectId;
	}
	public void setRelatedSubjectId(int relatedSubjectId) {
		this.relatedSubjectId = relatedSubjectId;
	}
	/**
	 * @return the stdTitle
	 */
	public String getStdTitle() {
		return stdTitle;
	}
	/**
	 * @param stdTitle the stdTitle to set
	 */
	public void setStdTitle(String stdTitle) {
		this.stdTitle = stdTitle;
	}
	public int getDifferenceId() {
		return differenceId;
	}
	public void setDifferenceId(int differenceId) {
		this.differenceId = differenceId;
	}
}
