package com.framework.module.common.plugins.emchat.httpClient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.module.common.plugins.emchat.constant.HttpRequestMethod;
import com.framework.module.common.plugins.emchat.httpClient.http.HttpRequestMessage;
import com.framework.module.common.plugins.emchat.httpClient.http.HttpResponseMessage;

public class HttpClientUtil {
	private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * 发送http请求
	 * 
	 * @param request
	 * @return
	 */
	public static HttpResponseMessage sendHttpRequest(HttpRequestMessage request) {
		Assert.notNull(request, "请求参数不能为空");

		HttpClient client = getHttpClient(StringUtils.startsWithIgnoreCase(request.getUrl(), "HTTPS"));

		// 构建请求方法
		HttpUriRequest httpMethod = buildHttpMethod(request.getMethod(), request.getUrl());

		// 添加首部
		if (!request.headerIsEmpty()) {
			Map<String, String> headers = request.getHeaders();
			for (Entry<String, String> key : headers.entrySet()) {
				httpMethod.addHeader(key.getKey(), key.getValue());
			}

			log.debug("请求首部：{}", headers);
		}

		// 添加实体
		Object body = null;
		if ((body = request.getBody()) != null) {
			try {
				String parametersJson = new ObjectMapper().writeValueAsString(body);
				log.debug("请求实体: {}", parametersJson);
				((HttpEntityEnclosingRequestBase) httpMethod).setEntity(new StringEntity(parametersJson, "UTF-8"));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		// 响应
		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpMethod);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return buildHttpResponseMessage(httpResponse);
	}

	/**
	 * 构建请求方法
	 * 
	 * @param method
	 *            请求方法
	 * @param url
	 *            请求url
	 * @return
	 */
	private static HttpUriRequest buildHttpMethod(HttpRequestMethod method, String url) {
		HttpUriRequest response = null;

		switch (method) {
		case GET:
			response = new HttpGet(url);
			break;
		case POST:
			response = new HttpPost(url);
			break;
		case PUT:
			response = new HttpPut(url);
			break;
		case DELETE:
			response = new HttpDelete(url);
			break;
		}

		return response;
	}

	/**
	 * 构建http响应报文
	 * 
	 * @param httpResponse
	 * @return
	 */
	private static HttpResponseMessage buildHttpResponseMessage(HttpResponse httpResponse) {
		HttpResponseMessage response = new HttpResponseMessage();

		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			try {
				String responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				log.debug("响应实体: {}", responseContent);
				response.setBody(responseContent);
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}
		int status = httpResponse.getStatusLine().getStatusCode();
		response.setStatus(status);

		return response;
	}

	/**
	 * 创建HttpClient实例
	 *
	 * @param isSSL
	 *            if the request is protected by ssl
	 * @return HttpClient
	 */
	public static HttpClient getHttpClient(boolean isSSL) {
		CloseableHttpClient client = null;

		if (isSSL) {
			try {
				X509HostnameVerifier verifier = new X509HostnameVerifier() {
					public void verify(String host, SSLSocket ssl) throws IOException {
					}

					public void verify(String host, X509Certificate cert) throws SSLException {
					}

					public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
					}

					public boolean verify(String s, SSLSession sslSession) {
						return true;
					}
				};

				TrustManager[] tm = new TrustManager[] { new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkServerTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}

					public void checkClientTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}
				} };

				SSLContext sslContext = SSLContext.getInstance("SSL");
				sslContext.init(null, tm, new SecureRandom());
				client = HttpClients.custom().setSslcontext(sslContext).setHostnameVerifier(verifier).build();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}
		} else {
			client = HttpClients.createDefault();
		}

		return client;
	}
}
