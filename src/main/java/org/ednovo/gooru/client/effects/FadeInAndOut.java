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

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.Duration;
import com.google.gwt.dom.client.Element;

/**
 * 
 * @fileName : FadeInAndOut.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class FadeInAndOut extends Animation {

	private Element element;
	private double opacityIncrement;
	private boolean fadeIn = false;
	private int duration;
	private int sleepInBeween = 0;

	/**
	 * Class constructor
	 * @param element instance of {@link Element}
	 * @param duration of the effect 
	 */
	public FadeInAndOut(Element element, int duration) {
		this(element, duration, 0);
	}
	
	/**
	 * Class constructor
	 * @param element instance of {@link Element}
	 * @param duration of the effect 
	 */
	public FadeInAndOut(Element element, int duration,boolean isFadeIn) {
		this(element, duration, 0);
		this.fadeIn=isFadeIn;
	}


	/**
	 * Class constructor
	 * @param element Instance of {@link Element}
	 * @param duration of effect
	 * @param sleepInBeween sleep time
	 */
	public FadeInAndOut(Element element, int duration, int sleepInBeween) {
		this.element = element;
		this.duration = duration;
		this.sleepInBeween = sleepInBeween;
		fade(duration, Duration.currentTimeMillis());
	}

	@Override
	protected void onUpdate(double progress) {
		element.getStyle().setOpacity((fadeIn ? 0.0 : 1.0) - (progress * opacityIncrement));
	}

	@Override
	protected double interpolate(double progress) {
		return super.interpolate(progress);
	}

	@Override
	protected void onComplete() {
		super.onComplete();
		if (fadeIn) {
			element.getStyle().setOpacity(1.0);
			fadeIn = false;
			fade(duration, Duration.currentTimeMillis() + sleepInBeween);
		} else {
			element.getStyle().setOpacity(0.0);
		}
	}

	protected void fade(int duration, double startTime) {
		opacityIncrement = (fadeIn ? 0.0 : 1.0) - (fadeIn ? 1.0 : 0.0);
		run(duration, startTime);
	}

}
