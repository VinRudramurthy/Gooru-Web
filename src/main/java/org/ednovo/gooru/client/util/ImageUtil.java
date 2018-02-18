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
package org.ednovo.gooru.client.util;

import org.ednovo.gooru.client.mvp.dnd.IsDraggable.DRAG_TYPE;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author SearchTeam
 *
 */
public class ImageUtil {

	public static final String FOLDER = "folder";

	public static final String COLLECTION = "scollection";

	public static final String YOUTUBE = "video/youtube";

	public static final String QUESTION = "question";

	public static final String ASSESSMENT = "assessment";

	public static final String EXAM = "exam";

	public static final String WEBSITE = "website";

	public static final String VIDEO = "video";

	public static final String HANDOUT = "handout";

	public static final String INTERACTIVE = "interactive";

	public static final String TEXTBOOK = "textbook";

	public static final String SLIDE = "slide";

	public static final String LESSON = "lesson";

	public static final String CHALLENGE = "challenge";

	public static final String IMAGE = "image";

	public static final String OTHER = "other";

	public static final String AUDIO = "audio";

	public static final String WEBPAGE = "webpage";

	public static final String TEXT = "text";

	public static void renderResourceImage(Widget widget, String category) {
		category=category.toLowerCase();
		if (category == null || category.equalsIgnoreCase(QUESTION) || category.startsWith(ASSESSMENT)) {
			widget.setStyleName("icon-questionSmall");
		} else if (category.equalsIgnoreCase(EXAM)) {
			widget.setStyleName("icon-websiteSmall");
		} else if (category.equalsIgnoreCase(HANDOUT)) {
			widget.setStyleName("icon-textbookSmall");
		} else if (category.equalsIgnoreCase(SLIDE)) {
			widget.setStyleName("icon-imageSmall");
		} else if (category.equalsIgnoreCase(INTERACTIVE)) {
			widget.setStyleName("icon-interactiveSmall");
		} else if (category.equalsIgnoreCase(WEBSITE) || category.equalsIgnoreCase(WEBPAGE)) {
			widget.setStyleName("icon-websiteSmall");
		} else if (category.equalsIgnoreCase(TEXTBOOK)|| category.equalsIgnoreCase(TEXT)) {
			widget.setStyleName("icon-textbookSmall");
		} else if (category.equalsIgnoreCase(LESSON)) {
			widget.setStyleName("icon-textbookSmall");
		} else if (category.equalsIgnoreCase(VIDEO)) {
			widget.setStyleName("icon-videoSmall");
		}else if (category.equalsIgnoreCase(CHALLENGE)) {
			widget.setStyleName("icon-websiteSmall");
		} else if (category.equalsIgnoreCase(IMAGE)) {
			widget.setStyleName("icon-imageSmall");
		} else if (category.equalsIgnoreCase(OTHER)) {
			widget.setStyleName("icon-otherSmall");
		} else if (category.equalsIgnoreCase(AUDIO)) {
			widget.setStyleName("icon-audioSmall");
		} else if (category.equalsIgnoreCase(DRAG_TYPE.COLLECTION.getName())) {
			widget.setStyleName("icon-collectionSmall");
		} else if(category.equalsIgnoreCase(FOLDER)){
			widget.setStyleName("icon-myFoldersIcon");
		} else if(category.equalsIgnoreCase(COLLECTION)){
			widget.setStyleName("icon-myScollectionsIcon");
		}
	}
}
