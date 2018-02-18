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

package org.ednovo.gooru.client;

/**
 * 
 * @fileName : UrlNavigationTokens.java
 *
 * @description : 
 *	The page navigation tokens will be used in this file.
 *
 * @version : 1.0
 *
 * @date: 30-Jun-2015
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface UrlNavigationTokens{
	//Student Class page Tabs
	String STUDENT_CLASSPAGE_TAB = "classpage-tab";
	String STUDENT_CLASSPAGE_LEARNING_MAP_ITEM = "learning-map";
	String STUDENT_CLASSPAGE_REPORT_ITEM = "student-report";
	//Student Class page view tokens
	String STUDENT_CLASSPAGE_PAGE_DIRECT = "page-view";
	String STUDENT_CLASSPAGE_COURSE_VIEW = "course-view";
	String STUDENT_CLASSPAGE_UNIT_VIEW = "unit-view";
	String STUDENT_CLASSPAGE_LESSON_VIEW = "lesson-view";
	String STUDENT_CLASSPAGE_LESSON_NAME = "lesson-name";
	String STUDENT_CLASSPAGE_COURSE_REPORT_VIEW = "course-report-view";
	String STUDENT_CLASSPAGE_ASSESSMENT_REPORT_VIEW = "assessment-report-view";
	//Student Class page unique ids
	String STUDENT_CLASSPAGE_CLASS_ID = "id";
	String STUDENT_CLASSPAGE_COURSE_ID = "c-id";
	String STUDENT_CLASSPAGE_UNIT_ID = "u-id";
	String STUDENT_CLASSPAGE_LESSON_ID = "l-id";
	String STUDENT_CLASSPAGE_ASSESSMENT_ID = "a-id";
	String TEACHER_CLASSPAGE_COLLECTION_ID = "d-id";
	String TEACHER_CLASSPAGE_STUDENT_ID = "s-id";
	String TEACHER_CLASS_PAGE_ID = "classpageId";
	
	//Teacher Class Page View tabs
	String TEACHER_CLASS_SUBPAGE_VIEW = "subpage-view";
	String TEACHER_CLASS_STUDENTES="students";
	String TEACHER_CLASS_SETTINGS="class-settings";
	String TEACHER_CLASS_SETTINGS_INFO="class-info";
	String TEACHER_CLASS_CONTENT_SETTINGS="class-content";
	String TEACHER_CLASS_DASHBOARD="teach-dashboard";

	String TEACHER_CLASS_CONTENT_SUB_REPORTS="reports";

	String TEACHER_CLASS_CONTENT_SUB_SCORE="min-score";
	String TEACHER_CLASS_CONTENT_SUB_SETTINGS="content-visibility";
	String TEACHER_CLASS_STUDENTS_ROASTER="roaster-view";
	String TEACHER_CLASS_STUDENTS_REPORT="report";
	
	//Teacher Class Page Report tokens
	String TEACHER_CLASSPAGE_REPORT_TYPE = "report-type";
	String TEACHER_CLASSPAGE_CONTENT = "content";
	String TEACHER_CLASSPAGE_COLLECTION = "collection";
	String TEACHER_CLASSPAGE_ASSESSMENT = "assessment";
	String EXTERNAL_ASSESSMENT = "assessment/url";
	
	String TEACHER_PREVIEW_MODE = "teach-preview";
	String TRUE = "true";
	String FALSE = "false";
	String CLASSPAGEID="classpageId";
	String CONTENT_NAME = "content-name";
	
	String MYCLASS="myclass";
	String OLDCLASS="oldclass";
}