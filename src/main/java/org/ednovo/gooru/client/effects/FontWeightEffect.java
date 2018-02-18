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
package org.ednovo.gooru.client.effects;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.user.client.Timer;

/**
 * 
 * @fileName : FontWeightEffect.java
 *
 * @description : 
 *	This class responsible to show Fade effect for Fonts.
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class FontWeightEffect extends Timer {

	private Element element;

	private FontWeight endWeight;

	/**
	 * Class constructor
	 * @param element instance of {@link Element}
	 * @param startWeight  font start weight
	 * @param endWeight font end weight
	 * @param time schedule timing
	 */
	public FontWeightEffect(Element element, FontWeight startWeight, FontWeight endWeight, int time) {
		this.endWeight = endWeight;
		element.getStyle().setBackgroundColor("#E1F0D1");
		element.getStyle().setFontWeight(startWeight);
		this.element = element;
		this.schedule(time);
	}

	@Override
	public void run() {
		element.getStyle().setFontWeight(endWeight);
		this.cancel();
		element.getStyle().clearBackgroundColor();
	}
}
