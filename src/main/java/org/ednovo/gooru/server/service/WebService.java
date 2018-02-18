package org.ednovo.gooru.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * This class is mainly used for invoking the web services and return the
 * corresponding response
 * 
 * @author Gooru Module : all
 * 
 */

public class WebService {
	DefaultHttpClient httpClient;
	HttpContext localContext;
	String ret;
	public  HttpResponse response = null;
	HttpPost httpPost = null;
	HttpDelete httpDelete = null;
	HttpGet httpGet = null;
	HttpPut httpPut = null;
	String webServiceUrl;

	// The serviceName should be the name of the Service you are going to be
	// using.
	public WebService(String url,boolean isGoogleDriveFile) {
		HttpParams myParams = new BasicHttpParams();
		if(isGoogleDriveFile){
			myParams.setParameter("http.protocol.handle-redirects",false);
			myParams.setParameter("http.protocol.single-cookie-header", true);
		}
		HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		HttpConnectionParams.setSoTimeout(myParams, 10000);
		httpClient = new DefaultHttpClient(myParams);
		localContext = new BasicHttpContext();
		webServiceUrl = url;
	} // Use this method to do a HttpPost\WebInvoke on a Web Service


	public String postWebservice(String methodName, String data, String contentType,String accessToken) throws UnsupportedEncodingException {
		ret = null;

		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.RFC_2109);

		httpPost = new HttpPost(webServiceUrl);
		response = null;
		StringEntity tmp = null;

		httpPost.setHeader(
				"Accept",
				"text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		httpPost.setHeader("Authorization","Bearer "+accessToken);
		if (contentType != null) {
			httpPost.setHeader("Content-Type", contentType);

		} else {
			httpPost.setHeader("Content-Type",
					"application/x-www-form-urlencoded");
		}
		tmp = new StringEntity(data, "UTF-8");
		httpPost.setEntity(tmp);

		try {
			response = httpClient.execute(httpPost, localContext);

			if (response != null) {
				ret = EntityUtils.toString(response.getEntity());

			}
		} catch (Exception e) {
		}

		return ret;
	}

	public String webInvokefordelete(String methodName, String data,
			String contentType) throws UnsupportedEncodingException {
		ret = null;

		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.RFC_2109);

		httpDelete = new HttpDelete(webServiceUrl);
		response = null;
		StringEntity tmp = null;

		httpDelete
				.setHeader(
						"Accept",
						"text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		if (contentType != null) {
			httpDelete.setHeader("Content-Type", contentType);

		} else {
			httpDelete.setHeader("Content-Type",
					"application/x-www-form-urlencoded");
		}
		tmp = new StringEntity(data, "UTF-8");

		try {
			response = httpClient.execute(httpDelete, localContext);

			if (response != null) {
				ret = EntityUtils.toString(response.getEntity());

			}
		} catch (Exception e) {
		}

		return ret;
	}

	public String webInvokeforput(String methodName, String data,
			String contentType) throws UnsupportedEncodingException {
		ret = null;

		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.RFC_2109);

		httpPut = new HttpPut(webServiceUrl);

		response = null;
		StringEntity tmp = null;

		httpPut.setHeader(
				"Accept",
				"text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");

		if (contentType != null) {
			httpPut.setHeader("Content-Type", contentType);

		} else {
			httpPut.setHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
		}

		tmp = new StringEntity(data, "UTF-8");
		httpPut.setEntity(tmp);

		try {
			response = httpClient.execute(httpPut, localContext);

			if (response != null) {
				ret = EntityUtils.toString(response.getEntity());

			}
		} catch (Exception e) {

		}

		return ret;
	}

	public String webInvokeforget(String methodName, String data,
			String contentType, String accessToken) throws UnsupportedEncodingException {
		ret = null;

		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.RFC_2109);

		httpGet = new HttpGet(webServiceUrl);

		response = null;
		StringEntity tmp = null;

		httpGet.setHeader(
				"Accept",
				"text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		if (accessToken !=null){		
			if (contentType != null) {
				httpGet.setHeader("Content-Type", contentType);
				httpGet.setHeader("Authorization","Bearer "+accessToken);
	
			} else {
				httpGet.setHeader("Content-Type",
						"application/x-www-form-urlencoded; charset=UTF-8");
			}
			tmp = new StringEntity(data, "UTF-8");
			try {
				response = httpClient.execute(httpGet, localContext);
//				response = httpConn.getResponseCode();
				if (response != null) {
					ret = EntityUtils.toString(response.getEntity());
				}
			} catch (Exception e) {

			}
		}else{
			ret = null;
		}
		
		return ret;
	}
	
	public int getStatusCode(String methodName) {
		int statusCode=0;
		ret = null;
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,CookiePolicy.RFC_2965);
		httpGet = new HttpGet(webServiceUrl);
		response = null;
		httpGet.setHeader("Accept","text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		httpGet.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		try {
			response = httpClient.execute(httpGet, localContext);
			if (response != null) {
				statusCode=response.getStatusLine().getStatusCode();
			}
		} catch (Exception e) {

		}
		return statusCode;
	}

	public InputStream getHttpStream(String urlString) throws IOException {
		InputStream in = null;
		int response = -1;
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");

		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			response = httpConn.getResponseCode();

			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}
		} catch (Exception e) {
			throw new IOException("Error connecting");
		} // end try-catch
		return in;
	}

	public void clearCookies() {
		httpClient.getCookieStore().clear();
	}

	public void abort() {
		try {
			if (httpClient != null) {

				httpPost.abort();
				response = null;
				httpPost.abort();
				httpGet.abort();

			}
		} catch (Exception e) {

		}
	}

	public void close() {

	}
}