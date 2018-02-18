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
/**
 *
 */
package org.ednovo.gooru.client.mvp.resource.dnd;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.dnd.AppPickupDragController;
import org.ednovo.gooru.client.mvp.dnd.Draggable;

import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;

/**
 * @author Search Team
 *
 */
public class ResourceDragController extends AppPickupDragController {

	private boolean deleteOnFalseDrop = true;
	int mouseX,mouseY;

	/**
	 * Class constructor with one parameter
	 * @param boundaryPanel instance of {@link AbsolutePanel}
	 */
	public ResourceDragController(AbsolutePanel boundaryPanel) {
		this(boundaryPanel, false);
	}

	/**
	 * Class constructor with two parameter
	 * @param boundaryPanel instance of {@link AbsolutePanel}
	 * @param allowDroppingOnBoundaryPanel  whether or not boundary panel should allow dropping
	 */
	public ResourceDragController(AbsolutePanel boundaryPanel, boolean allowDroppingOnBoundaryPanel) {
		this(boundaryPanel, allowDroppingOnBoundaryPanel, true);
	}

	/**
	 * Class constructor with three parameter
	 * @param boundaryPanel instance of {@link AbsolutePanel}
	 * @param allowDroppingOnBoundaryPanel  whether or not boundary panel should allow dropping
	 * @param deleteOnFalseDrop whether or not to set false to drop
	 */
	public ResourceDragController(AbsolutePanel boundaryPanel, boolean allowDroppingOnBoundaryPanel, boolean deleteOnFalseDrop) {
		super(boundaryPanel, allowDroppingOnBoundaryPanel);
		setBehaviorScrollIntoView(false);
		setBehaviorDragProxy(false);
		setBehaviorMultipleSelection(false);
		setBehaviorConstrainedToBoundaryPanel(false);
		setBehaviorDragStartSensitivity(1);
		setDeleteOnFalseDrop(deleteOnFalseDrop);
	}

	@Override
	public void dragMove() {
		super.dragMove();
		if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().toString().equals(PlaceTokens.SHELF)) {
			DOM.scrollIntoView(context.draggable.getElement());
		}
	}

	@Override
	public void dragStart() {
		super.dragStart();
		int adjustLeft = 0, adjustTop = 0;
		if (context.draggable instanceof Draggable) {
			adjustLeft = ((Draggable) context.draggable).getDraggableUc().getDragLeftCorrection();
			adjustTop = ((Draggable) context.draggable).getDraggableUc().getDragTopCorrection();
		}
		if(!AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().toString().equals(PlaceTokens.ASSESSMENT_PLAY) || !AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().toString().equals(PlaceTokens.COLLECTION_PLAY) || !AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().toString().equals(PlaceTokens.RESOURCE_PLAY)) {
			DOMUtil.fastSetElementPosition(context.draggable.getElement(), context.mouseX - context.draggable.getAbsoluteLeft() - adjustLeft, context.mouseY - context.draggable.getAbsoluteTop() - adjustTop);
		}

		}

	@Override
	public void previewDragStart() throws VetoDragException {
		if (context.draggable instanceof Draggable) {
			((Draggable) context.draggable).getDraggableMirageUc().onStart();
			((Draggable) context.draggable).setDragMode(true);
		}
		super.previewDragStart();
	}

	public void dragEnd() {

		super.dragEnd();
		if (context.draggable instanceof Draggable) {
			((Draggable) context.draggable).getDraggableMirageUc().onEnd();
		}
	}

	@Override
	protected void restoreSelectedWidgetsLocation() {
		if (isDeleteOnFalseDrop()) {
			// Destroy draggable on drop fail
//			new CustomAnimation(context.draggable).run(200);
//			context.draggable.removeFromParent();
		} else {
			((Draggable) context.draggable).setDragMode(false);
			super.restoreSelectedWidgetsLocation();
		}
	}

	/**
	 * @return the deleteOnFalseDrop
	 */
	public boolean isDeleteOnFalseDrop() {
		return deleteOnFalseDrop;
	}

	/**
	 * @param deleteOnFalseDrop
	 *            the deleteOnFalseDrop to set
	 */
	public void setDeleteOnFalseDrop(boolean deleteOnFalseDrop) {
		this.deleteOnFalseDrop = deleteOnFalseDrop;
	}

}
