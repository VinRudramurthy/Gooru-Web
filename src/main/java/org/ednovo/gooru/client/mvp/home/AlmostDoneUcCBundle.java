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
package org.ednovo.gooru.client.mvp.home;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**'
 * 
 * @author BLR Team
 *
 */

public interface AlmostDoneUcCBundle  extends ClientBundle {
	
	static final AlmostDoneUcCBundle INSTANCE = GWT.create(AlmostDoneUcCBundle.class);
	
	public interface AlmostDoneUcCss extends CssResource
	{
		String popup();
		String imgBG();
		String popupInner();
		String popupHeader();
		String closeButton();
		String popupContent();
		String h1();

		String subheader();
		String userInfoContainer();
		String ssoEmail();
		String role();
		String roleOption();
		String teacherRole();
		String roleOptionImage();
		String tooltipContainer();
		String arrowBorder();
		String arrow();
		String tooltipContent();
		String tooltipContentTitle();
		String studentRole();
		String parentRole();

		String otherRole();
		String chooseUsername();
		String questionHoverContainer();
		String inputRow();
		String horizontalTooltipContainer();
		String submitRegistration();
		String textUserName();
		String roleLabel();
		String teacherRoleSelected();
		String studentRoleSelected();
		String parentRoleSelected();
		String otherRoleSelected();
		String studentRoleDefault();
		String userRegErrorStyle();
		String errorLbl();
		String usernameToolTipContainer();
		String roleStep1();
		String unameStep2();
	}
	@Source("AlmostDonePopUp.css")
	AlmostDoneUcCss css();

}
