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
package org.ednovo.gooru.application.server.service;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.FieldError;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Search Team
 *
 */
@SuppressWarnings("serial")
public abstract class GwtAbstractServiceImpl extends RemoteServiceServlet implements ApplicationContextAware
{
	private static final boolean SIMULATE_LATENCY = false;
	protected transient final Log logger = LogFactory.getLog(this.getClass());

	private static final int LATENCY = 300; // in milliseconds

	private static final String UNEXPECTED_FAILURE = "Unexpected Failure";

	private ApplicationContext applicationContext;

	public GwtAbstractServiceImpl()
	{
		super();
	}

	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException
	{
		this.applicationContext = applicationContext;
	}

	public String getMessage(final FieldError error)
	{
		return applicationContext.getMessage(error, Locale.getDefault());
	}

	public String getMessageForExceptionCode(final String code)
	{
		return getMessageForExceptionCode(code, null);
	}

	public String getMessageForExceptionCode(final String code, final Object[] args)
	{
		return this.applicationContext.getMessage(code, args, null, Locale.getDefault());
	}

	@Override
	protected void onBeforeRequestDeserialized(final String serializedRequest)
	{
		if (SIMULATE_LATENCY)
		{
			try
			{
				Thread.sleep(LATENCY);
			}
			catch (InterruptedException e)
			{
				logger.error("InterruptedException::", e);
			}
		}
		super.onBeforeRequestDeserialized(serializedRequest);
	}

	@Override
	protected void doUnexpectedFailure(Throwable e) {
		logger.error(UNEXPECTED_FAILURE,e);
		super.doUnexpectedFailure(e);
	}
}
