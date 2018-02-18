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
package org.ednovo.gooru.client.mvp.home.register;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.user.UserDo;

import com.gwtplatform.mvp.client.PopupView;

/**
 * @author Search Team
 *
 */
public interface IsUserRegistrationView extends PopupView, IsViewWithHandlers<UserRegistrationUiHandlers> {
 
 /**
 * update registered user details  
 * @param user instance of {@link UserDo}
 * @param accoutType of the user , needs while register
 */
void setRegisteredUserDetails(UserDo user, String accoutType);
 
 /**
 * hide registration popup
 */
void closeRegisterPopup();
 
 /**
 * @return registration type of the user
 */
String getRegistrationType();
  
 /**
 * user availability info  
 * @param user instance of {@link UserDo}
 */
void checkUserNameAvailability(UserDo user);
 
 /**
 * Create child account popup 
 */
void renderChildAccount();
/**
 * To get child firstname
 */

String getFirstName();
/**
 * To get child lastname
 */
String getLastName();
}
