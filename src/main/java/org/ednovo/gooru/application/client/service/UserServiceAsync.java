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
package org.ednovo.gooru.application.client.service;

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.shared.model.code.UserDashBoardCommonInfoDO;
import org.ednovo.gooru.application.shared.model.user.BiographyDo;
import org.ednovo.gooru.application.shared.model.user.FilterSettings;
import org.ednovo.gooru.application.shared.model.user.IsFollowDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.application.shared.model.user.ProfilePageDo;
import org.ednovo.gooru.application.shared.model.user.ProfileRatingsReactionsDO;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.application.shared.model.user.UserFollowDo;
import org.ednovo.gooru.application.shared.model.user.UserTagsDo;
import org.ednovo.gooru.application.shared.model.user.UserTagsResourceDO;
import org.ednovo.gooru.application.shared.model.user.V2UserDo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Search Team
 * 
 */
public interface UserServiceAsync extends BaseServiceAsync {
	
//	void getUser(String userUid, AsyncCallback<UserDo> callback);
	
    void getEmailId(String email,String type, AsyncCallback<UserDo> callback);
    
    void getEmailId(String email, AsyncCallback<UserDo> callback);
    
    void registerUser(Map<String, String> params, AsyncCallback<UserDo> callback);
    
    void getRegistredUserDetails(String gooruUid, AsyncCallback<UserDo> callback);
    
    void getV2UserProfileDetails(String gooruUid, AsyncCallback<V2UserDo> callback);
    
    void updateUserDetails(String gooruUid, String token, Map<String, String> params, AsyncCallback<ProfileDo> callback);
    
    void resendConfirmationMail(Map<String, String> params, AsyncCallback<Void> callback);
    
    void forgotPassword(String emailId, AsyncCallback<Map<String, Object>> callback );
    
    void resetCredential(String token, String password, String mailConfirmationUrl, AsyncCallback<Map<String, Object>> callback ); 
    
    void updateUserViewFlag(String gooruUid, Integer viewFlag, AsyncCallback<UserDo> callback);

    void updateUserProfileVisibility(String gooruUid,String optionalValue,AsyncCallback<ProfilePageDo> callback);
    
    void  getUserProfilePage(String gooruUid, AsyncCallback<ProfilePageDo> callback);
    

    void updateProfileBiography(String gooruUid,String biography,String role,String firstName,String lastName,String gender,AsyncCallback<BiographyDo> callback);

    void getUserProfileV2Details(String gooruUid, String userMetaActiveFlag, AsyncCallback<ProfileDo> callback);

	void getUserPublicProfilePage(String gooruUid,
			AsyncCallback<ProfilePageDo> callback);
    
   void createUser(Map<String, String> registrationDetailsParams,String regType, AsyncCallback<UserDo> callback);

   void updateNewEmailStatus(String emailId, boolean isEmailConfirmed,
		AsyncCallback<Void> callback);
   
   void updateV2ProfileDo(String EmailId,String accountType,String firstName,String lastName,String biographyString ,String password,String userName, String gender, boolean isSendConfirmEmail,String userType, AsyncCallback<V2UserDo> callback);
   
   void sendWelcomeMail(String gooruUId, String emailType, AsyncCallback<Void> callback);
   
   void updatePartyCustomField(String gooruUid,String optionKey,String optionValue,AsyncCallback<Void> callback);
   
   void getFollowedOnUsers(String gooruUid,String offset, String limit,AsyncCallback<List<UserFollowDo>> callback);
   
   void getFollowedByUsers(String gooruUid,String offset, String limit,AsyncCallback<List<UserFollowDo>> callback);
   
   void followUser(String gooruUid,AsyncCallback<Void> callback);
   
   void unFollowUser(String gooruUid,AsyncCallback<Void> callback);
   
   void isFollowedUser(String gooruUid,AsyncCallback<IsFollowDo> callback);
   
   void getUserAddedContentTagSummary(String tagGooruOid,String offset, String limit,AsyncCallback<List<UserTagsDo>> callback);
   
   void getResourcesByTag(String tagGooruOid,String offset,String limit,String userIdVal,AsyncCallback<List<UserTagsResourceDO>> callback);
   
   void getRefershToken(String gooruUid,AsyncCallback<String> callback);
   
   void revokeToken(String gooruUid,AsyncCallback<String> callback);
   
   void isValidResetPasswordLink(String resetToken,AsyncCallback<String> callback);
   
   void getTheAnalyticsFlaggedMonthlyData(String fieldVal,String StartDate,String endDate,String operator,AsyncCallback<Map<String, Integer>> callback);
  
   void getUsersPublishedCollectionsCount(AsyncCallback<UserDashBoardCommonInfoDO> callback);

   void getFiveStarRatedResources(AsyncCallback<UserDashBoardCommonInfoDO> callback);
   
   void getFiveStarReviewdResources(AsyncCallback<UserDashBoardCommonInfoDO> callback);
  
   void getProfileAnalyticsRatings(AsyncCallback<ProfileRatingsReactionsDO> callback);
   
   void getTopViewedCollectionsInfo(String offsetval,String limitval,AsyncCallback<UserDashBoardCommonInfoDO> callback);
   
   void setUserProperties(UserDo user, AsyncCallback<FilterSettings> callback);

}
