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
package org.ednovo.gooru.application.server.serializer;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Provides support to serialize an Object into JSON. Uses Jackson serializer
 * for serializing the object.
 * 
 * @author Search Team
 * @see FilterSetting
 */
public class JsonSerializer {

	/**
	 * This class cannot be instantiated
	 */
	private JsonSerializer() {
	}

	/**
	 * Creates an instance of ObjectWriter with ObjectMapper
	 * 
	 * @param setting
	 *            FilterSetting
	 * @return instance of ObjectWriter
	 */
	public static ObjectWriter getWriter(FilterSetting setting) {
		if (setting != null) {
			SimpleFilterProvider filterProvider = getFilterProviderInstance();

			if (setting.getIncludes() != null) {
				for (Map.Entry<String, String[]> filterProperty : setting.getIncludes().entrySet()) {
					filterProvider.addFilter(filterProperty.getKey(), SimpleBeanPropertyFilter.filterOutAllExcept(filterProperty.getValue()));
				}
			}
			if (setting.getExcludes() != null) {
				for (Map.Entry<String, String[]> filterProperty : setting.getExcludes().entrySet()) {
					filterProvider.addFilter(filterProperty.getKey(), SimpleBeanPropertyFilter.serializeAllExcept(filterProperty.getValue()));
				}
			}
			return JsonProcessor.getMapper().writer(filterProvider);
		}
		return JsonProcessor.getMapper().writer(getFilterProviderInstance());
	}

	/**
	 * Serializes the <code>Object</code> into json <code>String</code>. The
	 * object is deep serialized.
	 * 
	 * @param object
	 *            an instance of <code>Object</code> which needs to be
	 *            serialized
	 * @return serialized input Object as String
	 * @throws JsonProcessingException
	 */
	public static <O extends Object> String serialize(O object) {
		try {
			return getWriter(null).writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Serializes the <code>Object</code> into JSON <code>String</code> using
	 * the filter
	 * 
	 * @param object
	 *            an instance of <code>Object</code> which needs to be
	 *            serialized
	 * @param setting
	 *            FilterSetting . If the filter is null the object is deep
	 *            serialized.
	 * @return serialized input Object as String
	 * @throws JsonProcessingException
	 */
	public static <O extends Object> String serialize(O object, FilterSetting setting) throws JsonProcessingException {
		return getWriter(setting).writeValueAsString(object);
	}

	/**
	 * Get the instance of a simple filter provider
	 * 
	 * @return instance of SimpleFilterProvider
	 */
	private static SimpleFilterProvider getFilterProviderInstance() {
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.setFailOnUnknownId(false);
		return filterProvider;
	}

}
