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
package org.ednovo.gooru.client.mvp.resource.dnd;

import org.ednovo.gooru.client.mvp.dnd.IsDraggableMirage;
import org.ednovo.gooru.client.util.ImageUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class ResourceDragWithImgUc extends FocusPanel implements IsDraggableMirage {

	private static ResourceDragWithImgUcUiBinder uiBinder = GWT.create(ResourceDragWithImgUcUiBinder.class);

	interface ResourceDragWithImgUcUiBinder extends UiBinder<Widget, ResourceDragWithImgUc> {
	}

	@UiField
	Label dragResourceImage;

	@UiField
	HTML dragresourceTitle;

	@UiField
	Label dragImage;

	@UiField
	AbsolutePanel container;

	@UiField
	FlowPanel labelPanel;

	/**
	 * Class constructor , to call setaData method and set image
	 * @param category of resource which is being dragged
	 * @param title of the resource which is being dragged
	 */
	public ResourceDragWithImgUc(String category, String title) {
		setWidget(uiBinder.createAndBindUi(this));
		container.setWidgetPosition(dragImage, 226, 0);
		container.getElement().setAttribute("style","box-sizing:content-box");
		onDroppable(false);
		setData(category, title);
	}

	/**
	 * Set resource meta data such as title and image
	 * @param category of resource which is being dragged
	 * @param title of resource which is being dragged
	 */
	public void setData(String category, String title) {
		dragresourceTitle.setHTML(title);
		dragresourceTitle.getElement().setId("htmlDragresourceTitle");
		dragresourceTitle.getElement().setAttribute("alt",title);
		dragresourceTitle.getElement().setAttribute("title",title);
		container.getElement().setId("apnlContainer");
		labelPanel.getElement().setId("fpnlLabelPanel");
		dragResourceImage.getElement().setId("lblDragResourceImage");

		ImageUtil.renderResourceImage(dragResourceImage, category);
	}

	public void onDroppable(Boolean droppable) {
		if (droppable) {
			dragImage.setStyleName("plusImage");
		} else {
			dragImage.setStyleName("minusImage");
		}
	}

	@Override
	public void onStart() {
		RootPanel.get().addStyleName("Uc-userDefaultSelectDisable");
	}

	@Override
	public void onEnd() {
		RootPanel.get().removeStyleName("Uc-userDefaultSelectDisable");
	}

}
