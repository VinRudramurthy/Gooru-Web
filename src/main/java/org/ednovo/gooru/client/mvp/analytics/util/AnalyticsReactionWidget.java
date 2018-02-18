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
package org.ednovo.gooru.client.mvp.analytics.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AnalyticsReactionWidget extends Composite {

	private static AnalyticsReactionWidgetUiBinder uiBinder = GWT
			.create(AnalyticsReactionWidgetUiBinder.class);

	interface AnalyticsReactionWidgetUiBinder extends
			UiBinder<Widget, AnalyticsReactionWidget> {
	}
	
	@UiField Image reactionlbl;
	@UiField Label noReactionlbl;
	String urlDomain = "";
	
	public static String assessment_Reaction_needhelp1="/images/analytics/analyticsReactions5.png";
	public static String assessment_Reaction_dontunderstand1="/images/analytics/analyticsReactions4.png";
	public static String assessment_Reaction_mean1="/images/analytics/analyticsReactions3.png";
	public static String assessment_Reaction_understand1="/images/analytics/analyticsReactions2.png";
	public static String assessment_Reaction_explain1="/images/analytics/analyticsReactions1.png";
	
	
	/**
	 * Constructor
	 * @param reaction
	 */
	public AnalyticsReactionWidget(int reaction) {
		initWidget(uiBinder.createAndBindUi(this));
		urlDomain=Window.Location.getProtocol()+"//"+Window.Location.getHost();
		 if(reaction == 0){
			 noReactionlbl.setText("--");
			 reactionlbl.setVisible(false);
		 }else{
			 noReactionlbl.setVisible(false);
    		  if (reaction==1) {
    			  reactionlbl.setUrl(urlDomain+"/images/analytics/analyticsReactions5.png");
			  } else if (reaction==2) {
				  reactionlbl.setUrl(urlDomain+"/images/analytics/analyticsReactions4.png");
			  } else if (reaction==3) {
				  reactionlbl.setUrl(urlDomain+"/images/analytics/analyticsReactions3.png");
			  } else if (reaction==4) {
				  reactionlbl.setUrl(urlDomain+"/images/analytics/analyticsReactions2.png");
			  }else if (reaction>4) {
				  reactionlbl.setUrl(urlDomain+"/images/analytics/analyticsReactions1.png");
			  }
		 }
	}
}
