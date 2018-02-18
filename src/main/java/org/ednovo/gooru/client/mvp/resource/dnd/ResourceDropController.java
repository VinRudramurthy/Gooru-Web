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
import org.ednovo.gooru.client.mvp.dnd.AppSimpleDropController;
import org.ednovo.gooru.client.mvp.dnd.Draggable;
import org.ednovo.gooru.client.mvp.dnd.DropBox;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class ResourceDropController extends AppSimpleDropController {

	private DropBox resourceDropBox;

	private boolean dropActive;

	/**
	 * Class constructor , assign dropBox object
	 * @param dropBox instance of {@link DropBox}
	 */
	public ResourceDropController(DropBox dropBox) {
		super(dropBox.asWidget());
		this.resourceDropBox = dropBox;
	}

	@Override
	public void onDrop(DragContext context) {
		setDropActive(false);
		if (AppClientFactory.getLoggedInUser().getUserUid().equals(AppClientFactory.GOORU_ANONYMOUS)) {
			//AppClientFactory.fireEvent(new InvokeLoginEvent());
			LoginPopupUc loginPopupUc=new  LoginPopupUc() {
				@Override
				public void onLoginSuccess() {

				}
			};
			if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.RESOURCE_SEARCH)){
				MixpanelUtil.sign_up_from_dragging_resource();
				loginPopupUc.setMixPanelEvent("dragresource");
			}else if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.COLLECTION_SEARCH)){
				MixpanelUtil.sign_up_from_saving_a_collection();
				loginPopupUc.setMixPanelEvent("savecollection");
			}
		} else {
			for (Widget widget : context.selectedWidgets) {
				if (widget instanceof Draggable) {
					resourceDropBox.onDrop((Draggable) widget);
				}
			}
		}
		super.onDrop(context);
	}

	@Override
	public void onEnter(DragContext context) {
		setDropActive(true);
		super.onEnter(context);
		for (Widget widget : context.selectedWidgets) {
			if (widget instanceof Draggable) {
				resourceDropBox.onDragOver((Draggable) widget);
			}
		}
	}

	@Override
	public void onLeave(DragContext context) {
		setDropActive(false);
		for (Widget widget : context.selectedWidgets) {
			if (widget instanceof Draggable) {
				resourceDropBox.onDragOut((Draggable) widget);
			}
		}
		super.onLeave(context);
	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		super.onPreviewDrop(context);
	}

	public DropBox getResourceDropBox() {
		return resourceDropBox;
	}

	public boolean isDropActive() {
		return dropActive;
	}

	public void setDropActive(boolean dropActive) {
		this.dropActive = dropActive;
	}
}
