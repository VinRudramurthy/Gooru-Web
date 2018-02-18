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
package org.ednovo.gooru.client.mvp.standards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel1DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel2DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel3DO;
import org.ednovo.gooru.application.shared.model.code.StandardsLevel4DO;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.uc.UlPanel;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.gwtplatform.mvp.client.PopupView;

/**
 * @author Search Team
 *
 */
public interface IsStandardsPopupView extends PopupView, IsViewWithHandlers<StandardsPopupUiHandlers> {

	void loadData();

	void SetData(StandardsLevel1DO levelOneData, int valArr, String titleVal, String standardV);

	void loadSecondLevelContianerObjects(ArrayList<StandardsLevel2DO> result);

	void loadThirdLevelContianerObjects(ArrayList<StandardsLevel3DO> result);

	void loadFourthLevelContianerObjects(ArrayList<StandardsLevel4DO> result);

	void hidePopup();

	void hideLoaderIcon();

	void setSelectedItmes(List<LiPanelWithClose> collectionLiPanelWithCloseArray);

	Anchor getCloseButton();

	Button getAddButton();

	List<Map<String, String>> getAddedStandards();

	void loadB21ThirdLevelContianerObjects(ArrayList<StandardsLevel3DO> result);

	void loadb21SecondLevelContianerObjects(ArrayList<StandardsLevel2DO> result);  


}
