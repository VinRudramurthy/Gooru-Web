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
package org.ednovo.gooru.application.client.gin;

import org.ednovo.gooru.application.client.service.AppServiceAsync;
import org.ednovo.gooru.application.client.service.HomeServiceAsync;
import org.ednovo.gooru.application.client.service.MediaUploadServiceAsync;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.client.service.SearchServiceAsync;
import org.ednovo.gooru.application.client.service.ShelfServiceAsync;
import org.ednovo.gooru.application.client.service.TaxonomyServiceAsync;
import org.ednovo.gooru.application.client.service.UserServiceAsync;

import com.google.inject.Singleton;

/**
 * 
 * @fileName : ServiceModule.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class ServiceModule extends AppPresenterModule {

	@Override
	protected void configure() {
		bind(AppServiceAsync.class).in(Singleton.class);
		bind(SearchServiceAsync.class).in(Singleton.class);
		bind(HomeServiceAsync.class).in(Singleton.class);
		bind(ShelfServiceAsync.class).in(Singleton.class);
		bind(ResourceServiceAsync.class).in(Singleton.class);
		bind(TaxonomyServiceAsync.class).in(Singleton.class);
		bind(UserServiceAsync.class).in(Singleton.class);
		bind(MediaUploadServiceAsync.class).in(Singleton.class);
	}
}
