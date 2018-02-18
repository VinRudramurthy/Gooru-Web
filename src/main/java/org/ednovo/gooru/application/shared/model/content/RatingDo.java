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
package org.ednovo.gooru.application.shared.model.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class RatingDo implements IsSerializable {
	private static final long serialVersionUID = 1849886397443409797L;
	
	private Integer votesDown;
	private Integer votesUp;
	
	private Integer count=0;
	private double average=0.0;
	private Integer reviewCount=0;
	
	public RatingDo(){}
	
	public Integer getVotesDown() {
		return votesDown;
	}
	public void setVotesDown(Integer votesDown) {
		this.votesDown = votesDown;
	}
	public Integer getVotesUp() {
		return votesUp;
	}
	public void setVotesUp(Integer votesUp) {
		this.votesUp = votesUp;
	}

	public Integer getCount() {
		return count;
	}

	public double getAverage() {
		return average;
	}

	public Integer getReviewCount() {
		return reviewCount;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}
	
	
}
