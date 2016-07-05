/**
 * Project Name:springlearn
 * File Name:HttpUtil.java
 * Package Name:com.jing.web.util.http
 * Date:2016年1月21日下午6:52:13
 * Copyright (c) 2016, jingshouyan@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * ClassName:HttpUtil <br/>
 * Function: http请求工具类 <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年1月21日 下午6:52:13 <br/>
 * 
 * @author bxy-jing
 * @version
 * @since JDK 1.6
 * @see
 */
public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	public static final String DEFAULT_CHARSET = "utf-8";

	/**
	 * 
	 * get:使用httpClient发送get请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response get(String url) {
		return get(url, null, null, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * get:使用httpClient发送get请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param params
	 *            参数
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response get(String url, Map<String, String> params) {
		return get(url, params, null, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * get:使用httpClient发送get请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param params
	 *            参数
	 * @param headers
	 *            请求头
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response get(String url, Map<String, String> params, Map<String, String> headers) {
		return get(url, params, headers, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * post:使用httpClient发送post请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param params
	 *            参数
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response post(String url, Map<String, String> params) {
		return post(url, params, null, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * postJson:使用httpClient发送post json请求 . <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param object
	 *            需要传递的对象
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response postJson(String url, Object object) {
		return postJson(url, object, null, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * postJson:使用httpClient发送post json请求 . <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param object
	 *            需要传递的对象
	 * @param headers
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response postJson(String url, Object object, Map<String, String> headers) {
		return postJson(url, object, headers, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * postJson:使用httpClient发送post json请求 . <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param object
	 *            需要传递的对象
	 * @param headers
	 * @param charset
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response postJson(String url, Object object, Map<String, String> headers, String charset) {
		if (null == headers) {
			headers = new HashMap<String, String>();
		}
		headers.put("Content-Type", "application/json");
		// headers.put("Content-Type", "application/x-www-form-urlencoded");
		String data = JSON.toJSONString(object);
		return postStr(url, data, headers, charset);
	}

	/**
	 * 
	 * post:使用httpClient发送post请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param params
	 *            参数
	 * @param headers
	 *            请求头
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response post(String url, Map<String, String> params, Map<String, String> headers) {
		return post(url, params, headers, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * postStr:使用httpClient发送post请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param data
	 *            post数据
	 * @param headers
	 *            请求头
	 * @param charset
	 *            编码格式
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response postStr(String url, String data, Map<String, String> headers, String charset) {
		logger.debug("post-send|url>>>{}|data>>>{}|headers>>>{}", url, data, headers);
		long startTime = System.currentTimeMillis();
		Response response = new Response();
		CloseableHttpClient client = createClient(url);
		HttpPost httpPost = new HttpPost(url);
		RequestConfig config = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		httpPost.setConfig(config);
		httpPost.setHeaders(map2Headers(headers));
		CloseableHttpResponse httpResponse = null;
		try {
			StringEntity stringEntity = new StringEntity(data, charset);
			httpPost.setEntity(stringEntity);
			httpResponse = client.execute(httpPost);
			response = formatResponse(httpResponse, charset);
		} catch (Exception e) {
			response.setReasonPhrase(e.getMessage());
			response.setException(e);
			e.printStackTrace();
		} finally {
			close(client, httpResponse);
		}
		long endTime = System.currentTimeMillis();
		logger.debug("post-response|url>>>{}|used>>>{}ms|response>>>{}", url, endTime - startTime,
				JSON.toJSONString(response));

		return response;
	}

	/**
	 * 
	 * post:使用httpClient发送post请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param params
	 *            参数
	 * @param headers
	 *            请求头
	 * @param charset
	 *            编码格式
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response post(String url, Map<String, String> params, Map<String, String> headers, String charset) {
		String paramsStr = URLEncodedUtils.format(map2NameValuePairs(params), charset);
		if (null == headers) {
			headers = new HashMap<String, String>();
		}
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		return postStr(url, paramsStr, headers, charset);
	}

	/**
	 * 
	 * get:使用httpClient发送get请求. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param params
	 *            参数
	 * @param headers
	 *            请求头
	 * @param charset
	 *            编码格式
	 * @return Response
	 * @since JDK 1.6
	 */
	public static Response get(String url, Map<String, String> params, Map<String, String> headers, String charset) {
		logger.debug("get-send|url>>>{}|params>>>{}|headers>>>{}", url, params, headers);
		long startTime = System.currentTimeMillis();
		Response response = new Response();
		CloseableHttpClient client = createClient(url);
		if (null != params && !params.isEmpty()) {
			if (url.indexOf("?") == -1) {
				url += "?";
			}
			String paramsStr = URLEncodedUtils.format(map2NameValuePairs(params), charset);
			if (!url.endsWith("?")) {
				url += "&";
			}
			url += paramsStr;
		}
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeaders(map2Headers(headers));
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpGet);
			response = formatResponse(httpResponse, charset);
		} catch (Exception e) {
			response.setReasonPhrase(e.getMessage());
			response.setException(e);
			e.printStackTrace();

		} finally {
			close(client, httpResponse);
		}
		long endTime = System.currentTimeMillis();
		logger.debug("get-response|url>>>{}|used>>>{}ms|response>>>{}", url, endTime - startTime,
				JSON.toJSONString(response));
		return response;
	}

	public static void download(String url, IDownloadHandler handler) {
		download(url, handler, null);
	}

	public static void download(String url, IDownloadHandler handler, Map<String, String> params) {
		download(url, handler, params, null);
	}

	public static void download(String url, IDownloadHandler handler, Map<String, String> params,
			Map<String, String> headers) {
		download(url, handler, params, headers, DEFAULT_CHARSET);
	}

	public static void download(String url, IDownloadHandler handler, Map<String, String> params,
			Map<String, String> headers, String charset) {
		logger.debug("get-send|url>>>{}|params>>>{}|headers>>>{}", url, params, headers);
		long startTime = System.currentTimeMillis();
		InputStream in = null;
		CloseableHttpClient client = createClient(url);
		if (null != params && !params.isEmpty()) {
			if (url.indexOf("?") == -1) {
				url += "?";
			}
			String paramsStr = URLEncodedUtils.format(map2NameValuePairs(params), charset);
			if (!url.endsWith("?")) {
				url += "&";
			}
			url += paramsStr;
		}
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeaders(map2Headers(headers));
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			in = entity.getContent();
			handler.handle(in);
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			close(client, httpResponse);
		}
		long endTime = System.currentTimeMillis();
		logger.debug("get-response|url>>>{}|used>>>{}ms", url, endTime - startTime);
	}

	/**
	 * 
	 * upload:httpClient上传文件. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param files
	 *            文件map
	 * @return
	 * @since JDK 1.6
	 */
	public static Response upload(String url, Map<String, String> files) {
		return upload(url, files, null);
	}

	/**
	 * 
	 * upload:httpClient上传文件. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param files
	 *            文件map
	 * @param params
	 *            其他参数
	 * @return
	 * @since JDK 1.6
	 */
	public static Response upload(String url, Map<String, String> files, Map<String, String> params) {
		return upload(url, files, params, null);
	}

	/**
	 * 
	 * upload:httpClient上传文件. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param files
	 *            文件map
	 * @param params
	 *            其他参数
	 * @param headers
	 *            头文件
	 * @return
	 * @since JDK 1.6
	 */
	public static Response upload(String url, Map<String, String> files, Map<String, String> params,
			Map<String, String> headers) {
		return upload(url, files, params, headers, DEFAULT_CHARSET);
	}

	/**
	 * 
	 * upload:httpClient上传文件. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @param files
	 *            文件map
	 * @param params
	 *            其他参数
	 * @param headers
	 *            头文件
	 * @param charset
	 * @return
	 * @since JDK 1.6
	 */
	public static Response upload(String url, Map<String, String> files, Map<String, String> params,
			Map<String, String> headers, String charset) {
		logger.debug("upload-send|url>>>{}|files>>>{}|params>>>{}|headers>>>{}", url, files, params, headers);
		long startTime = System.currentTimeMillis();
		Response response = new Response();
		CloseableHttpClient client = createClient(url);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeaders(map2Headers(headers));
		CloseableHttpResponse httpResponse = null;
		try {
			MultipartEntityBuilder builder = MultipartEntityBuilder.create().
					setMode(HttpMultipartMode.BROWSER_COMPATIBLE) // 以浏览器兼容模式运行，防止文件名乱码。  	
					.setCharset(Charset.forName(charset));			
			if (null != files && !files.isEmpty()) {
				for (Entry<String, String> entry : files.entrySet()) {
					builder.addPart(entry.getKey(), new FileBody(new File(entry.getValue())));
				}
			}
			if (null != params && !params.isEmpty()) {
				for (Entry<String, String> entry : params.entrySet()) {
					builder.addTextBody(entry.getKey(), entry.getValue(), ContentType.TEXT_PLAIN.withCharset(charset));
				}
			}
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
//			httpPost.setHeader("Content-Type", "multipart/form-data; charset=UTF-8");
			httpResponse = client.execute(httpPost);
			response = formatResponse(httpResponse, charset);
		} catch (Exception e) {
			response.setReasonPhrase(e.getMessage());
			response.setException(e);
			e.printStackTrace();
		} finally {
			close(client, httpResponse);
		}

		long endTime = System.currentTimeMillis();
		logger.debug("upload-response|url>>>{}|used>>>{}ms|response>>>{}", url, endTime - startTime,
				JSON.toJSONString(response));
		return response;
	}

	private static List<NameValuePair> map2NameValuePairs(Map<String, String> map) {
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		if (null != map && !map.isEmpty()) {
			for (Entry<String, String> param : map.entrySet()) {
				formParams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
		}
		return formParams;
	}

	public static Response upload(String url, String paramName, String fileName, InputStream fileIs) {
		Response response = new Response();
		String result = "";
		String BOUNDARY = UUID.randomUUID().toString();
		String PREFIX = "--";
		String LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data";
		int TIME_OUT = 1 * 60 * 1000;
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setConnectTimeout(TIME_OUT);
			conn.setReadTimeout(TIME_OUT);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Charset", DEFAULT_CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			StringBuffer sb = new StringBuffer();
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINE_END);
			/**
			 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
			 * filename是文件的名字，包含后缀名的 比如:abc.png
			 */

			sb.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\""
					+ LINE_END);
			sb.append("Content-Type: application/octet-stream; charset=" + DEFAULT_CHARSET + LINE_END);
			sb.append(LINE_END);
			dos.write(sb.toString().getBytes());

			byte[] bytes = new byte[10240];
			int len = 0;
			while ((len = fileIs.read(bytes)) != -1) {
				dos.write(bytes, 0, len);
				System.out.println("上传中>>>"+len);
//				Thread.sleep(100);
			}
			fileIs.close();
			dos.write(LINE_END.getBytes());
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
			dos.write(end_data);
			dos.flush();

			int statusCode = conn.getResponseCode();

			response.setStatusCode(statusCode);

			InputStream input = conn.getInputStream();
			StringBuffer sb1 = new StringBuffer();
			byte[] bs = new byte[1024];
			int length = 0;
			while ((length = input.read(bs)) > 0) {
				sb1.append(new String(bs, 0, length, "utf-8"));
			}

			result = sb1.toString();
			response.setBody(result);
		} catch (Exception e) {
			response.setReasonPhrase(e.getMessage());
			response.setException(e);
		}
		return response;
	}

	/**
	 * 
	 * formatResponse:将CloseableHttpResponse转换成自定义Response对象. <br/>
	 *
	 * @author bxy-jing
	 * @param httpResponse
	 * @param charset
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @since JDK 1.6
	 */
	private static Response formatResponse(CloseableHttpResponse httpResponse, String charset)
			throws ParseException, IOException {
		Response response = new Response();
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		String reasonPhrase = httpResponse.getStatusLine().getReasonPhrase();
		HttpEntity entity = httpResponse.getEntity();
		Header[] retHeaders = httpResponse.getAllHeaders();
		String body = EntityUtils.toString(entity, charset);
		response.setStatusCode(statusCode);
		response.setReasonPhrase(reasonPhrase);
		response.setBody(body);
		response.setHeaders(headers2Map(retHeaders));
		return response;
	}

	/**
	 * 
	 * headers2Map:header数组转map. <br/>
	 *
	 * @author bxy-jing
	 * @param headers
	 *            header数组
	 * @return map
	 * @since JDK 1.6
	 */
	private static Map<String, String> headers2Map(Header[] headers) {
		Map<String, String> map = new HashMap<String, String>();
		if (null != headers && headers.length > 0) {
			for (Header header : headers) {
				map.put(header.getName(), header.getValue());
			}
		}
		return map;
	}

	/**
	 * 
	 * map2Headers:map对象转Header数组 <br/>
	 *
	 * @author bxy-jing
	 * @param map
	 *            map对象
	 * @return Header数组
	 * @since JDK 1.6
	 */
	private static Header[] map2Headers(Map<String, String> map) {
		if (null != map && !map.isEmpty()) {
			int size = map.size();
			Header[] headers = new Header[size];
			int i = 0;
			for (Entry<String, String> entry : map.entrySet()) {
				headers[i] = new BasicHeader(entry.getKey(), entry.getValue());
				i++;
			}
			return headers;
		} else {
			return new Header[] {};
		}
	}

	/**
	 * 
	 * close:关闭链接 <br/>
	 *
	 * @author bxy-jing
	 * @param client
	 * @param response
	 * @since JDK 1.6
	 */
	private static void close(CloseableHttpClient client, CloseableHttpResponse response) {
		if (null != response) {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (null != client) {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * createClient:根据url创建对应的httpClient（http/https）. <br/>
	 *
	 * @author bxy-jing
	 * @param url
	 * @return
	 * @since JDK 1.6
	 */
	public static CloseableHttpClient createClient(String url) {
		CloseableHttpClient client;
		if (url.toLowerCase().startsWith("https://")) {
			client = createSSLClientDefault();
		} else {
			client = createClientDefault();
		}
		return client;
	}

	/**
	 * 
	 * createClientDefault:创建http httpClient. <br/>
	 *
	 * @author bxy-jing
	 * @return
	 * @since JDK 1.6
	 */
	public static CloseableHttpClient createClientDefault() {
		CloseableHttpClient client = HttpClients.createDefault();
		return client;
	}

	/**
	 * 
	 * createSSLClientDefault:创建https httpClient <br/>
	 *
	 * @author bxy-jing
	 * @return
	 * @since JDK 1.6
	 */
	public static CloseableHttpClient createSSLClientDefault() {
		CloseableHttpClient client = null;
		try {
			SSLContext sslContext = null;
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
			e.printStackTrace();
		}
		return client;
	}

	private static String base = "http://vrv.linkdood.cn/server-imrobot";

	public static void getUserid() {
		String url = base + "/email/getUserId.do";
		println(url);
		// String url = "https://www.baidu.com";
		Map<String, String> params = new HashMap<String, String>();
		params.put("clientKey", "M2HF_FSWNZiKQyYnXGhjGxwSJUiGjxGeEaGbsFEArQZGOB6JEmudkY1MH8k0b21U");
		JSONObject object = new JSONObject();
		object.put("username", "944373279@qq.com");
		object.put("userid", 4395731455l);
		object.put("password", "asbkhxloiwwhbebc");
		object.put("pop3Host", "pop.qq.com");
		object.put("pop3Port", 995);
		object.put("userid", 4395731455l);
		Response response = get(url, params);
		println(response);
	}

	@SuppressWarnings("unused")
	public static void save() {
		String url = base + "/email/saveEmailInfo.do";
		println(url);
		// String url = "https://www.baidu.com";
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("ticket", "1547e8c16c69a142c5d6ad54ac5b16958ae03713fb0");
		JSONObject object = new JSONObject();
		object.put("username", "jingshouyan@vrvmail.com.cn");
		object.put("userid", 4328625706l);
		object.put("password", "stj6722913");
		object.put("host", "vrvmail.com.cn");
		object.put("port", 143);
		object.put("protocol", "imap");
		Response response = postJson(url, object, headers);
		println(response);
	}

	@SuppressWarnings("unused")
	public static void del() {
		String url = base + "/email/delEmailInfo.do";
		println(url);
		// String url = "https://www.baidu.com";
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("ticket", "1547e8c16c69a142c5d6ad54ac5b16958ae03713fb0");
		JSONObject object = new JSONObject();
		object.put("username", "944373279@qq.com");
		object.put("userid", 4328625706l);
		Response response = postJson(url, object, headers);
		println(response);
	}

	public static void main(String[] args) {
		// getUserid();
		try {
			down();
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		// del();
	}

	public static void down() throws IOException {
		String url = "http://127.0.0.1:8080/springlearn/downBig/linkdood%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86%E7%B3%BB%E7%BB%9F1.0%E7%89%88%E4%BD%BF%E7%94%A8%E6%89%8B%E5%86%8C.docx";

		IDownloadHandler handler = new IDownloadHandler() {

			@Override
			public void handle(InputStream in) throws IOException {

				byte[] buffer = new byte[4096];
				int readLength = 0;
				while ((readLength = in.read(buffer)) > 0) {
					for (int i = 0; i < readLength; i++) {
						System.out.print(buffer[i]);
					}
					System.out.println();
				}

			}
		};
		download(url, handler);
	}

	public static void println(Object o) {
		String str = JSONObject.toJSONString(o);
		System.out.println(str);
	}

}
