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

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.client.service.TaxonomyService;
import org.ednovo.gooru.application.server.annotation.ServiceURL;
import org.ednovo.gooru.application.server.deserializer.TaxonomyDeSerializer;
import org.ednovo.gooru.application.server.request.JsonResponseRepresentation;
import org.ednovo.gooru.application.server.request.ServiceProcessor;
import org.ednovo.gooru.application.server.request.UrlToken;
import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.code.LibraryCodeDo;
import org.ednovo.gooru.application.shared.model.library.DomainStandardsDo;
import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

@Service("taxonomyService")
@ServiceURL("/taxonomyService")
public class TaxonomyServiceImpl extends BaseServiceImpl implements TaxonomyService {

	/**
	 *
	 */
	private static final long serialVersionUID = 6947235468580822129L;
	
	private static final Logger logger = LoggerFactory.getLogger(TaxonomyDeSerializer.class);

	@Autowired
	private TaxonomyDeSerializer taxonomyDeSerializer;

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.application.client.service.TaxonomyService#getCourse()
	 */
	@Override
	public List<LibraryCodeDo> getCourse() {
		JsonRepresentation jsonRep =null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_TAXONOMY_COURSE);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return taxonomyDeSerializer.getCourse(jsonRep);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.application.client.service.TaxonomyService#getSubjectsList()
	 */
	@Override
	public List<CourseSubjectDo> getSubjectsList(int id,String type,int offset,int limit) throws GwtException,ServerDownException {
		List<CourseSubjectDo> subjectCodeDo = new ArrayList<CourseSubjectDo>();
		JsonRepresentation jsonRep =null;
		String url = null;
		String classificationId = String.valueOf(id);
		if(type.equalsIgnoreCase("subject")){
			url= UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_SUBJECTS,classificationId);
		}else if(type.equalsIgnoreCase("course")){
			url= UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_COURSES_BY_SUBJECTID,classificationId,String.valueOf(offset),limit+"");
		}else if(type.equalsIgnoreCase("domain")){
			url= UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_DOMAIN_BY_SUBJECTID,classificationId,String.valueOf(offset),limit+""); 
		}
		logger.info("url::"+url);
		if(url!=null){
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
			jsonRep = jsonResponseRep.getJsonRepresentation();
			if (jsonRep != null) {
				try {
					subjectCodeDo = JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<List<CourseSubjectDo>>() {
				  });
				} catch (JSONException e) {
					logger.error("Exception::", e);
				}
			}
		}
		return subjectCodeDo;
	}
	
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.application.client.service.TaxonomyService#getSubjectsList()
	 */
	@Override
	public List<DomainStandardsDo> getStandardsList(int subDomainId) throws GwtException,ServerDownException {
		List<DomainStandardsDo> domainStandardsCodeDo = new ArrayList<DomainStandardsDo>();
		JsonRepresentation jsonRep =null;
		String url = null;

		url= UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V1_GET_STANDARDS_BY_DOMAIN,subDomainId+"");
		
		logger.info("getStandardsListurl::"+url);
		if(url!=null){
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
			jsonRep = jsonResponseRep.getJsonRepresentation();
			if (jsonRep != null) {
				try {
					domainStandardsCodeDo = JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<List<DomainStandardsDo>>() {
				  });
				} catch (JSONException e) {
					logger.error("Exception::", e);
				}
			}
		}
		return domainStandardsCodeDo;
	}

}
