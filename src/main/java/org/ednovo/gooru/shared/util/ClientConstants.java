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

package org.ednovo.gooru.shared.util;


public interface ClientConstants {

	String TRUE = "true";

	String FALSE = "false";

	String GOORU_ACTIVE_USER = "gooru-active-user";

	String GOORU_ANONYMOUS = "ANONYMOUS";

	String PERMISSIONS = "permissions";

	String SEQUENCE = "sequence";

	String POPULAR_LOWER_CASE = "popular";

	String PUBLIC = "public";

	String GRADE = "grade";

	String GRADES = "grades";

	String NAME = "name";

	String VISIBILITY = "visibility";

	String USER_META_ACTIVE_FLAG = "userMetaActiveFlag";


	/* ****************** Base service Impl Constants Start ******************** */


	String REST_ENDPOINT = "rest.endpoint";

	String SEARCH_ENDPOINT = "search.endpoint";

	String REST_USERNAME = "rest.username";

	String REST_PASSWORD = "rest.password";

	String SEARCH_USERNAME = "search.username";

	String SEARCH_PASSWORD = "search.password";

	String API_KEY = "api.key";

	String YOUTUBE_API_KEY= "youtube.api.key";

	String YOUTUBE_API_URL= "youtube.url";

	String HOME_ENDPOINT = "home.endpoint";

	String ANALYTICS_ENDPOINT = "analytics.endpoint";

	String DOMAIN_NAME = "domain.name";

	String DOCVIEWER_HOME = "docViewer.home";

	String DOCVIEWER_CACHE = "docViewer.cache";

	String GOOGLE_ANALTICS_ADDITIONAL_ACCOUNTS = "google.analtics.additional.accounts";

	String CLASSIC_ENDPOINT = "classic.endpoint";

	String GOORU_SESSION_TOKEN = "gooru-session-token";

	String HEADER_GOORU_APIKEY = "Gooru-Apikey";

	String GOORU_ACCESS_TOKEN = "google-access-token";

	String SIGNED_USER_UID = "signed-user-uid";

	String SIGNED_USER_EMAILID = "signed-user-emailid";

	String COOKIE_PATH = "/";

	String TOKEN = "token";

	String USER_INFO_FAILED_ON_TOKEN = "Get User info failed on token : ";

	int COOKIE_AGE = 86400;//24 hrs

	String PRODUCTION_SWITCH = "production.switch";

	String GOOGLE_SIGNIN = "google.signin";

	String ENABLE_CLIENT_LOGGERS = "clientside.loggers";

	String GOOGLE_DRIVE = "drive.api";

	String PROFILE_IMAGE_RESPOSITORY_URL="profile.image.url";

	String CDN_ENDPOINT = "cdn.endpoint";

	String DATA_LOG_API_KEY = "log.api.key";

	String WHATS_NEW_MOS_LINK = "whats.new.mos.link";

	String WHATS_NEW_FIB_LINK = "whats.new.fib.link";

	String MOS_LINK = "mos.link";

	String GOORU_RELEASE_VERSION = "gooru.release.version";

	String FACEBOOK_APP_ID="facebook.app_id";

	String FACEBOOK_FEED_URL="facebook.dialogfeedurl";

	String TAXONOMY_PREFERENCES = "taxonomy.preferences";

	String SIGNED_USER_DOB = "signed-user-dob";

	String SERVER_REDIRECT_URL="redirect.url";

	String GOOGLE_RESTENDPOINT="google.restendpoint";

	String STORIES_URL = "stories.url";

	String SHOW_STORIES = "show.stories";

	String COMMUNITY_LIBRARY_ID = "community.library.gooruOid";

	String HTTPS = "https";

	String HTTP = "http";

	/* ****************** Base service Impl Constants End ******************** */

	/*************** Analytics start **************************************************/

	String CLEARSUMMARY="ClearSummary";

	String CLEARPROGRESS="ClearProgress";

	String SUMMARY="Summary";

	String PROGRESS="Progress";

	String BELOWSCORE="BelowScore";

	String ABOVESCORE="AboveScore";

	String ONE = "1";

	String TWO = "2";

	String THREE = "3";

	String REPORTS = "reports";

	String GREEN = "#BCD1B9 !important";
	String RED = "#EAB4B3 !important";
	String ORANGE = "#FFE7C2 !important";
	String WHITE = "#FFF";
	String VIEWRESPONSE = "View Answer";
	String QUESTION = "question";
	String RESOURCE="resource";
	String OE = "OE";
	String MA = "MA";
	String FIB = "FIB";
	String TF = "TF";
	String TSLASHF = "T/F";
	String HT_RO = "HT_RO";
	String HT_HL = "HT_HL";
	String HS_TXT = "HS_TXT";
	String HS_IMG = "HS_IMG";
	String ANS_A = "A";
	String ANS_B = "B";
	String FIRST_SESSION = "1st Session";
	String ALL_STUDENTS ="All Students";
	String SORT_BY = "Sort BY:";
	String INCORRECT="#db0f0f";
	String CORRECT="#4d9645";
	String ONMULTIPULEATTEMPTS="#FBB03B";
	String SCORED="scoredTab";
	String OPENENDED="openendedTab";
	String BREAKDOWN="breakdownTab";
	String PRINT="print";
	String EMAIL="email";
	String WEBSITE ="website";
	String WEBPAGE = "webpage";
	String SLIDE = "slide";
	String IMAGE ="image";
	String HANDOUT = "handout";
	String LESSON ="lesson";
	String TEXTBOOK = "textbook";
	String TEXT = "text";
	String EXAM = "exam";
	String VIDEO = "video";
	String INTERACTIVE ="interactive";
	String AUDIO = "audio";
	String CHALLENGE ="Challenge";
	String VIDEOS ="Videos";
	String OTHER ="Other";
	String MC = "MC";
	String ZERO_NUMERIC = "0";
	String INITIAL_COMMENT_LIMIT ="10";
	String HS_INCORRECT="summaryHsanswer-wrong";
	String HS_CORRECT="summaryHsanswer-right";
	String HS_ONMULTIPULEATTEMPTS="summaryHsanswer-multiRight";
	String SUMMARY_HT_HL="summaryHtHiglightText";
	String PLAYER_HT_HL="htHiglightText";
	String CORRECT_WORD="correct";
	String MULTI_CORRECT_WORD="multiCorrect";
	String SUMMARY_HTPLAYER_ANS="summaryHtPlayerAns";
	String PLAYER_HT_ANS ="htPlayerAns ";
	String STYLE_TABLE_CENTER="alignCenterAndBackground";
	String STYLE_TXTLEFT="alignLeft";
	String STYLE_MARGIN_AUTO="setMarginAuto";
	String STYLE_ERROR_MSG="displayMessageTextForScoredQuestions";
	String STYLE_WHITE="white";
	String STYLE_GREEN="green";
	String STATUS_CORRECT="correct";
	String STATUS_INCORRECT="in-correct";
	String COURSEID="courseId";
	String UNITID="unitId";
	String LESSONID="lessonId";
	String CID="cid";

	/*************** Analytics End **************************************************/

	/***** Resource Player Related **************/
	String SAVING = "Saving..";
	String POSTING = "Posting..";
	String SUBMIT ="Submit";
	String NOTLODING ="not-loading";
	String FIB_SEPARATOR = "_______";



	/*************** Library Start **************************************************/

	String FOLDER = "folder";
	String U_WEBSITE ="Website";
	String U_WEBPAGE = "Webpage";
	String U_SLIDE = "Slide";
	String U_IMAGE ="Image";
	String U_HANDOUT = "Handout";
	String U_LESSON ="Lesson";
	String U_TEXTBOOK = "Textbook";
	String U_TEXT = "Text";
	String U_EXAM = "Exam";
	String U_VIDEO = "Video";
	String U_INTERACTIVE ="Interactive";
	String DEFULT_IMAGE_PREFIX = "images/default-";
	String PNG = ".png";
	String SMALL = "Small";
	String DEFAULT_COLLECTION_IMAGE = "../images/default-collection-image-160x120.png";
	String DEFULT_ASSESSMENT = "images/default-assessment-image -160x120.png";
	String PAGE = "page";
	String COURSE_PAGE = "course-page";
	String STANDARD_CODE = "code";
	String STANDARD_DESCRIPTION = "description";
	String PNG_CROP = "-80x60.";
	String CUSTOMIZE = "customize";
	String ASSIGN = "assign";
	String COLLECTION_TITLE = "collectionTitle";
	String SUBJECT_NAME = "subject";
	String STANDARDS="standard";
	String SHARING_TYPE = "public";
	String STANDARD_ID = "standardId";
	String PARTNER = "partner";
	String CONCEPT = "concept";
	String POPULAR = "Popular";
	String SAVEBTN = "save";
	String NONE = "none";
	String IMAGE_1 ="image/png";
	String PDF ="pdf";
	String FORWARD ="forward";
	String PREVIEW ="preview";




	/*************** Library End **************************************************/
	String ASSESSMENT_QUESTION="assessment-question";
	String EMBED="embed";
	String COLLECTION="collection";
	String VIDEO_YOUTUBE="video/youtube";
	String PROFILE="profile";
	String PRIVATE="private";
	String COLLECTIONSHREVIEWTEXTLBL="collectionShareViewPage";
	String COLLECTIONSUMMARYVIEWTEXTLBL="collectionShareSummaryPage";
	String RESULT="res";
	String COLLECTION_RESOURCE_RATING="collection_resource_rating";
	String COLLECTION_RESOURCE_ADD ="collection_resource_add";
	String COLLECTION_RESOURCE_FLAG = "collection_resource_flag";
	String COLLECTION_FLAG = "collection_flag";
	String UPDATE_HEADER="update_header";
	String NARRATION="narration";
	String ADD="add";
	String INFO="info";
	String SHARE="share";
	String NAVIGATION="navigation";
	String FLAG="flag";
	String QUIZ="quiz";
	String END="end";
	String ACTIVE="active";
	String TEACH="teach";
	String STUDY="study";
	String AUTODESK="Autodesk";
	String LESSONOPOLOGY="Lessonopoly";
	String COMMONSENCEMEDIA="CommonSenseMedia";
	String FTE="FTE";
	String WSPWH="WSPWH";
	String LISANGC="lisaNGC";
	String NGC = "NGC";
	String ONR = "ONR";
	String YES = "yes";
	String ACESSTEXT ="public,anyonewithlink";
	String SCOLLECTION ="scollection";
	String EDIT ="edit";
	String TURNON ="turn-on";
	String TURNOFF ="turn-off";
	String RESOURCE_URL ="resource/url";
	String RESOURCE_VIMEO_URL ="vimeo/video";
	String DELETE ="delete";
	String NEXT ="next";
	String PREVIOUS ="previous";
	String ADDTEXTLBL ="Add";
	String CREATE= "create";
	String FEATCHINGCOMMENT="featchingcomment";
	String ORANGE_COLOR="orange";
	String TEXAS_TEACHER="TexasTeacher";
	String COMPLETETEXT="Example: Complete this collection. When you're finished, you may continue onto the lab station.";
	String REACTIONWIDGET="reactionWidget";
	String STAR_RATING_WIDGET="Star_Rating_Widget";


	/***Player share constants***/
	String SHORTENURL="shortenUrl";
	String DECODERAWURL="decodeRawUrl";
	String RAWURL="rawUrl";
	String EMBEDURLRAWURL="embedUrlRawUrl";
	String SHARETYPE="shareType";
	String TYPE="type";
	String TWITTERURL="http://twitter.com/intent/tweet?text=";
	String IMG="img";
	/***Player share constants end***/

	String CRYPTO_KEY = "ErGwDuu9YEwqMpF7AxnGzjJx"; // Don't modify this key

	String CHILD_REG = "childReg";

	String FULLSCREEN = "fullscreen";
	String NOTHING ="Nothing";
	String ASSETQUESTION ="asset-question";
	String BUFFERIMAGE ="bufferImage";

	String WELCOME = "welcome";
	String ERROR_MESSAGE ="errorMessage";
	String GOOGLE_TIE_UP_TEXT ="Looks like this email is tied with Google!";
	String ERROR_CODE ="errorCode";
}
