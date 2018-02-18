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

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.server.annotation.ServiceURL;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

/**
 * @author Search Team
 *
 */
public class GwtUrlHandlerMapping extends SimpleUrlHandlerMapping {

	private static final String MISSING_BEAN_NAME= "Missing ServiceURL annotation for bean name ";

	@Override
	public void initApplicationContext() throws BeansException {

		Map<String, Object> urlMappings = new HashMap<String, Object>();
		Map<String, GwtAbstractServiceImpl> beans = getApplicationContext().getBeansOfType(GwtAbstractServiceImpl.class);
		for (Map.Entry<String, GwtAbstractServiceImpl> entry : beans.entrySet()) {
			String beanName = entry.getKey();
			GwtAbstractServiceImpl controller = entry.getValue();
			Class serviceClass = controller.getClass();

			@SuppressWarnings("unchecked")
			ServiceURL serviceURL = (ServiceURL) serviceClass.getAnnotation(ServiceURL.class);

			if (serviceURL == null) {
				throw new BeanDefinitionValidationException(MISSING_BEAN_NAME + beanName);
			} else {
				urlMappings.put(serviceURL.value(), beanName);
			}
		}

		setUrlMap(urlMappings);

		super.initApplicationContext();
	}
}
