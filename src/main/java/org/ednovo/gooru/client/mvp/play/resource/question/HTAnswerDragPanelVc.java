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
package org.ednovo.gooru.client.mvp.play.resource.question;

import org.ednovo.gooru.client.mvp.dnd.AppRepositionDragContainer;
import org.ednovo.gooru.client.mvp.dnd.AppVerticalPanelDropController;
import org.ednovo.gooru.client.mvp.dnd.Draggable;
import org.ednovo.gooru.client.mvp.resource.dnd.ResourceDragController;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Hari
 *
 */
public class HTAnswerDragPanelVc extends AppRepositionDragContainer {

	private AppVerticalPanelDropController widgetDropController;

	private ResourceDragController dragController;

	/**
	 * Class constructor create new instance of  {@link ResourceDragController} and {@link HTAnswerDropController}
	 */
	public HTAnswerDragPanelVc() {
		super();
		dragController = new ResourceDragController(RootPanel.get(), false, false);
		widgetDropController = new HTAnswerDropController(this);
		dragController.registerDropController(widgetDropController);
		setDragController(dragController);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.dnd.DropBox#onDrop(org.ednovo.gooru.client.mvp.dnd.Draggable)
	 */
	@Override
	public void onDrop(Draggable draggable) {
		/**
		 * Enabled drag and drop again in hotfix release for resources at m\My collections.
		 */
		HTAnswerChoiceOptionView resourceVc = (HTAnswerChoiceOptionView) draggable.getDraggableUc();
		if(resourceVc!=null){
			resourceVc.reorderCollectionItem(1);
		}
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.dnd.DropBox#onDragOver(org.ednovo.gooru.client.mvp.dnd.Draggable)
	 */
	@Override
	public void onDragOver(Draggable draggable) {

	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.dnd.DropBox#onDragOut(org.ednovo.gooru.client.mvp.dnd.Draggable)
	 */
	@Override
	public void onDragOut(Draggable draggable) {
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.dnd.DropBox#getDropTarget()
	 */
	@Override
	public Widget getDropTarget() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.dnd.DropBox#registerDropController()
	 */
	@Override
	public void registerDropController() {
		dragController.registerDropController(widgetDropController);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.dnd.DropBox#unregisterDropController()
	 */
	@Override
	public void unregisterDropController() {
		dragController.unregisterDropController(widgetDropController);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	@Override
	public void onLoad() {
		super.onLoad();
		registerDropController();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onUnload()
	 */
	@Override
	public void onUnload() {
		super.onUnload();
		unregisterDropController();
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.dnd.DropBox#reregisterDropController()
	 */
	@Override
	public void reregisterDropController() {
		// Not necessary
	}
}
