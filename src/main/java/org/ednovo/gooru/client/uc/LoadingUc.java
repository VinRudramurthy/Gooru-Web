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
package org.ednovo.gooru.client.uc;

import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * @author Search Team
 *
 */
public class LoadingUc extends FlowPanel implements MessageProperties {

	private Image loadingImage;
	
	private Label loadingMessage;
	
	private static  final String LOADER_IMAGE = "images/core/B-Dot.gif";   
	
	/**
	 * Class constructor
	 */
	public LoadingUc() {
		UcCBundle.INSTANCE.css().ensureInjected();
		loadingImage =  new Image();
		loadingMessage = new Label();
		loadingImage.setUrl(LOADER_IMAGE);
		loadingImage.setAltText(GL0110);
		loadingImage.setTitle(GL0110);
//		loadingMessage.setText("please wait...");
		this.add(loadingImage);
//		this.add(loadingMessage);
		this.setStyleName(UcCBundle.INSTANCE.css().imagePanel());
	}

	
	public void setLoadingText(String loadingText) {
		loadingMessage.setText(loadingText);
	}
}
