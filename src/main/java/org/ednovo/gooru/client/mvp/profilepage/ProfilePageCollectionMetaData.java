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
package org.ednovo.gooru.client.mvp.profilepage;

import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru Team
 * 
 */
public class ProfilePageCollectionMetaData extends Composite {

	@UiField
	Label folderTitle, folderDescription;
	
	@UiField HTMLPanel mainContainer;
	
	private static ProfilePageCollectionMetaDataUiBinder uiBinder = GWT
			.create(ProfilePageCollectionMetaDataUiBinder.class);

	interface ProfilePageCollectionMetaDataUiBinder extends UiBinder<Widget, ProfilePageCollectionMetaData> {
	}

	public ProfilePageCollectionMetaData(final CollectionItemDo collectionItemDo) {
		initWidget(uiBinder.createAndBindUi(this));
		setData(collectionItemDo);
		mainContainer.getElement().setId("gooruProfilePage");
	}

	public void setData(CollectionItemDo collectionItemDo) {
		folderTitle.setText(collectionItemDo.getCollection().getTitle());
		folderTitle.getElement().setId("lblFolderTitle");
		folderTitle.getElement().setAttribute("alt",collectionItemDo.getCollection().getTitle());
		folderTitle.getElement().setAttribute("title",collectionItemDo.getCollection().getTitle());
		
		folderDescription.setText(collectionItemDo.getCollection().getGoals());
		folderDescription.getElement().setId("lblFolderDescription");
		folderDescription.getElement().setAttribute("alt",collectionItemDo.getCollection().getGoals());
		folderDescription.getElement().setAttribute("title",collectionItemDo.getCollection().getGoals());
	}

}
