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

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


public class EmbedController extends MultiActionController{

	private static final String HTTPS = "https";

	private static final String HTTP = "http";

	@Inject
	private ResourceServiceImpl resourceService;



	public void collection(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id=request.getParameter("id");
		 StringBuffer htmlOutput=new StringBuffer();
		 PrintWriter out=response.getWriter();
		 response.setContentType("text/html");
		if(id!=null){
			String protocolRequest = request.getScheme();
			String homeEndPoint = resourceService.getHomeEndPointForEmbed();
			String restEndPoint = resourceService.getRestEndPointForEmbed();
			String cssEndPoint=homeEndPoint.replaceAll(HTTP+":", "").replaceAll(HTTPS+":", "");

			if(protocolRequest.equalsIgnoreCase(HTTPS)) {
				homeEndPoint = homeEndPoint.replaceAll(HTTP, HTTPS);
				restEndPoint = restEndPoint.replaceAll(HTTP, HTTPS);
			}

			 CollectionDo collectionDo=resourceService.getCollectionFromEmbed(id,restEndPoint);
			 if(collectionDo!=null&&collectionDo.getGooruOid()!=null){
				 int resourceCount=0;
				 int questoionResourceCount=0;
				 for(CollectionItemDo collectionItemDo:collectionDo.getCollectionItems()){
					 if(collectionItemDo.getResource() != null && collectionItemDo.getResource().getResourceFormat() != null && collectionItemDo.getResource().getResourceFormat().getValue().equalsIgnoreCase("Question")){
						 questoionResourceCount++;
					 }else{
						 resourceCount++;
					 }
				 }
				 String resourcesString=resourceCount==1?"1 resource,":resourceCount+" resources,";
				 String questoionResourceCountString=questoionResourceCount==1?"1 question":questoionResourceCount+" questions";
				 String collectionItems=resourcesString+" "+questoionResourceCountString;
				 if (collectionDo!= null){
					 htmlOutput.append(" <!DOCTYPE html>").append("<html><head>").append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
				 			.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+cssEndPoint+"/css/embed.css\">").append("</head>")
				 			.append("<body><div class=\"embed-container\"><div class=\"collection-image\"><a href=\""+homeEndPoint+"/#collection-play&id="+collectionDo.getGooruOid()+"\"target=\"_blank\" class=\"collectionImage\"><image onerror=\"this.src='"+homeEndPoint+"/images/collection-default-image.png'\" src="+(collectionDo.getThumbnails() != null ? collectionDo.getThumbnails().getUrl() : "") +" width=\"310px\" height=\"208px\"><div class=\"button\">Study</div></a></div>")
				 			.append("<div class=\"metadata\">").append("<h1 class=\"title\"><a href=\""+homeEndPoint+"/#collection-play&id="+collectionDo.getGooruOid()+"\"target=\"_blank\">"+collectionDo.getTitle()+"</a></h1>")
				 			.append("<div class=\"description\"><span class=\"label\">Description: </span>"+(collectionDo.getGoals() != null ? collectionDo.getGoals() : "")+"</div>")
				 			.append("<div class=\"resources\">"+collectionItems+"</div></div></div></div> </body></html>");
				 }
			 }
		}
		out.println(htmlOutput.toString());

	}

}
