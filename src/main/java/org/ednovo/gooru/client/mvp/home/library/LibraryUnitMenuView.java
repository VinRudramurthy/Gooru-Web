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
package org.ednovo.gooru.client.mvp.home.library;

import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.library.PartnerFolderDo;
import org.ednovo.gooru.application.shared.model.library.ProfileLibraryDo;
import org.ednovo.gooru.application.shared.model.library.UnitDo;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LibraryUnitMenuView extends Composite implements ClientConstants {

	@UiField Label unitMenuItem;
	
	@UiField LibraryStyleBundle libraryStyleUc;
	
	private String unitId;
	
	private Integer childCount;
	
	private Integer widgetCount;
	
	private String libraryGooruOid;
	
	private String type;
	
	private static LibraryUnitMenuViewUiBinder uiBinder = GWT.create(LibraryUnitMenuViewUiBinder.class);

	interface LibraryUnitMenuViewUiBinder extends UiBinder<Widget, LibraryUnitMenuView> {
	}

	/**
	 * Class constructor
	 * @param unitDo {@link UnitDo}
	 */
	public LibraryUnitMenuView(UnitDo unitDo) {
		initWidget(uiBinder.createAndBindUi(this));
		if(POPULAR.equals(unitDo.getLabel())){
			unitMenuItem.setStyleName(libraryStyleUc.popularStarImage());
		}
		unitMenuItem.setText(unitDo.getLabel());
		unitMenuItem.getElement().setAttribute("alt",unitDo.getLabel());
		unitMenuItem.getElement().setAttribute("title",unitDo.getLabel());
		setUnitId(""+unitDo.getCodeId());
		if(unitDo.getCount()==null) {
			setChildCount(0);	
		} else {
			setChildCount(unitDo.getCount());
		}
		setIds();
	}

	/**
	 * Class constructor
	 * @param folderDo {@link FolderDo}
	 */
	public LibraryUnitMenuView(PartnerFolderDo folderDo) {
		initWidget(uiBinder.createAndBindUi(this));
		unitMenuItem.setText(folderDo.getTitle());
		unitMenuItem.getElement().setAttribute("alt",folderDo.getTitle());
		unitMenuItem.getElement().setAttribute("title",folderDo.getTitle());
		setUnitId(folderDo.getGooruOid());
		setLibraryGooruOid(folderDo.getParentGooruOid());
		setIds();
	}
	
	/**
	 * Class constructor
	 * @param folderDo {@link FolderDo}
	 * @param libraryGooruOid {@link String}
	 */
	public LibraryUnitMenuView(ProfileLibraryDo folderDo,String libraryGooruOid) {
		initWidget(uiBinder.createAndBindUi(this));
		unitMenuItem.setText(folderDo.getTitle());
		unitMenuItem.getElement().setAttribute("alt",folderDo.getTitle());
		unitMenuItem.getElement().setAttribute("title",folderDo.getTitle());
		setUnitId(folderDo.getGooruOid());
		setLibraryGooruOid(folderDo.getParentGooruOid());
		setIds();
	}
	
	/**
	 * Sets ID for each element.
	 */
	public void setIds(){
		unitMenuItem.getElement().setId("lblUnitMenuItem");
	}
	
	/**
	 * returns the unit menu item as a label
	 * @return unitMentItem {@link Label}
	 */
	public Label getUnitMenuItemPanel() {
		return unitMenuItem;
	}

	/** 
	 * This method is to get the unitId
	 */
	public String getUnitId() {
		return unitId;
	}

	/** 
	 * This method is to set the unitId
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	/** 
	 * This method is to get the childCount
	 */
	public Integer getChildCount() {
		return childCount;
	}

	/** 
	 * This method is to set the childCount
	 */
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	/** 
	 * This method is to get the widgetCount
	 */
	public Integer getWidgetCount() {
		return widgetCount;
	}

	/** 
	 * This method is to set the widgetCount
	 */
	public void setWidgetCount(Integer widgetCount) {
		this.widgetCount = widgetCount;
	}

	/** 
	 * This method is to get the type
	 */
	public String getType() {
		return type;
	}

	/** 
	 * This method is to set the type
	 */
	public void setType(String type) {
		this.type = type;
	}

	
	/** 
	 * This method is to get the libraryGooruOid
	 */
	public String getLibraryGooruOid() {
		return libraryGooruOid;
	}
	
	/** 
	 * This method is to set the libraryGooruOid
	 */
	public void setLibraryGooruOid(String libraryGooruOid) {
		this.libraryGooruOid = libraryGooruOid;
	}
	
	
}