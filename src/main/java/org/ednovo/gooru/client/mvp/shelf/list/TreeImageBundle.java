package org.ednovo.gooru.client.mvp.shelf.list;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * @fileName : TreeResourceBundle.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 31-Jan-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer: 
 */
public interface TreeImageBundle extends ClientBundle {
	   public static final TreeImageBundle INSTANCE =  GWT.create(TreeImageBundle.class);
	   @Source("spacer.gif")
	   public ImageResource leaf();
}