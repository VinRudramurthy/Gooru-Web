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
package org.ednovo.gooru.client.mvp.play.resource.question;

import org.ednovo.gooru.client.mvp.dnd.AppVerticalPanelDropController;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.util.Area;
import com.allen_sauer.gwt.dnd.client.util.CoordinateLocation;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.DragClientBundle;
import com.allen_sauer.gwt.dnd.client.util.Location;
import com.allen_sauer.gwt.dnd.client.util.LocationWidgetComparator;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Hari
 * 
 */
public class HTAnswerDropController extends AppVerticalPanelDropController {

	private Widget positioner;

	/**
	 * Class constructor
	 * @param dropTarget  target view panel while drop collection item
	 */
	public HTAnswerDropController(VerticalPanel dropTarget) {
		super(dropTarget);
	}

	@Override
	protected Widget newPositioner(DragContext context) {
		Widget widget = super.newPositioner(context);
		widget.removeStyleName(DragClientBundle.INSTANCE.css().positioner());
		widget.addStyleName("answerDragdropPositioner");
		positioner = widget;
		return widget;
	}

	@Override
	protected LocationWidgetComparator getLocationWidgetComparator() {
		return new LocationWidgetComparator() {

			@Override
			public boolean locationIndicatesIndexFollowingWidget(Area widgetArea, Location location) {
				return location.getTop() > widgetArea.getTop() + widgetArea.getHeight();
			}
		};
	}

	/**
	 * @return drag and drop widget comparator
	 */
	protected LocationWidgetComparator getMoveLocationWidgetComparator() {
		return new LocationWidgetComparator() {

			@Override
			public boolean locationIndicatesIndexFollowingWidget(Area widgetArea, Location location) {
				return location.getTop() > widgetArea.getTop() + widgetArea.getHeight() / 2;
			}
		};
	}

	@Override
	public void onEnter(DragContext context) {
		super.onEnter(context);
		int positionerIndex = dropTarget.getWidgetIndex(positioner);
		if (positionerIndex == 0) {
			dropTarget.insert(positioner, 2);
		}
	}

	@Override
	public void onMove(DragContext context) {
		int targetIndex = DOMUtil.findIntersect(dropTarget, new CoordinateLocation(context.mouseX, context.mouseY), getMoveLocationWidgetComparator());

		// check that positioner not already in the correct location
		int positionerIndex = dropTarget.getWidgetIndex(positioner);
		if (positionerIndex != targetIndex && (positionerIndex != targetIndex - 1 && targetIndex != 0)) {
			if (positionerIndex == 0 && dropTarget.getWidgetCount() == 1) {
				// do nothing, the positioner is the only widget
			} else if (targetIndex == -1) {
				// outside drop target, so remove positioner to indicate a drop
				positioner.removeFromParent();
			} else {
				dropTarget.insert(positioner, targetIndex);
			}
		}
	}

	@Override
	public void onLeave(DragContext context) {
		positioner.removeFromParent();
		positioner = null;
		super.onLeave(context);
	}

}
