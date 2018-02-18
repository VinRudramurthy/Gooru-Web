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
package org.ednovo.gooru.client.mvp.classpage.studentclassview;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
/**
 * 
 * @fileName : IsStudentClassView.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 19-Jun-2015
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface IsStudentClassView extends IsViewWithHandlers<StudentClassUiHandlers>  {

	/**
	 * @function clearAll 
	 * 
	 * @created_date : @date: 19-Jun-2015
	 * 
	 * @description
	 * 
	 * @parm(s) : 
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	void clearAll();

	void setCourseData(ClasspageDo classpageDo);
	
	void setButtonHighlight();
	
	void setPreviewClassMode(boolean isPreview);
	
	void setJoinClassData();

	void initiateJoinClassPopup();

	void setSuccesspopup();
	
	void setProgressBarVisibility(boolean isVisible);
	
	void disableSwitchBtn(boolean isDisable);
	
	void setEmptyContainerVisiblity(boolean isVisible);
	
	void closeJoinPopup(boolean isVisible);

	void setPrivateLogoutPanelVisibility(boolean isVisible);
	
}