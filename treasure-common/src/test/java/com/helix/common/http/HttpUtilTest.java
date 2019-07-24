package com.helix.common.http;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class HttpUtilTest {

	@Test
	public void httpsWithNoAuthTest(){
		String httpUrl = "https://www.baidu.com";
		String result = HttpUtil.get(httpUrl);
		System.out.println(result);
	}
	
	@Test
	public void httpsOtherPortTest(){
		String httpUrl = "https://172.20.200.8:8002/hexin-crm/rs/rs.do?method=staffHasTask&jobnum=T123";
		String result = HttpUtil.get(httpUrl);
		System.out.println(result);
	}

	@Test
	public void postJsonHttp()throws Exception{
		String url = "http://118.31.48.163:9303/services/xzServer/initApproveNotify.do";
		String jsonBody = "{\"userId\":330104000504283,\"token\":\"FJto6kBkH61lPI3j2oq92tVmcQE0O/tQmwN5xLhr8c++fcSvIU7AUQ==\",\"partnerUserId\":\"1\"}";

		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(new BasicHeader("Content-Type","application/json"));
		httpPost.addHeader(new BasicHeader("Accept-Encoding","gzip"));
		httpPost.setEntity(new StringEntity(jsonBody,"utf-8"));
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse httpResponse = httpclient.execute(httpPost);


		Header[] header=httpResponse.getHeaders("Content-Encoding");
		Header gzipHeader = Arrays.stream(header).filter(e->{
			return e.getValue().equals("gzip");
		}).findFirst().orElse(null);

		byte[] bs = new byte[1024];
		int n = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		while ( (n=httpResponse.getEntity().getContent().read(bs,0,1024))!=-1){
			baos.write(bs,0,n);
		}

		String result;
		if(gzipHeader != null){
			result = new String(uncompress(baos.toByteArray()));
		}else {
			result = new String(baos.toByteArray(), "UTF-8");
		}
		System.out.println(result);
	}

	public static String uncompress(byte[] bytes) throws IOException {
		if (bytes == null || bytes.length == 0) {
			return "";
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		return out.toString("utf-8");
	}

	/**
	 * post:Content-Type:application/json测试
	 */
	@Test
	public void postJson(){
		String httpUrl = "http://127.0.0.1:8081/hello/jsonParamSimple?name=tom";
		String jsonBody = "{\"age\":18,\"userName\":\"中国\"}";
		String result = HttpUtil.postJson(httpUrl,null,jsonBody);
		System.out.println(result);
	}

	@Test
	public void postUpload(){
		String httpUrl = "http://127.0.0.1:8081/hello/upload?name=tom";
		File file = new File("D:\\onedrive\\图片\\one_poice.jpg");

		Map mapParam = new HashMap();
		mapParam.put("msg","msg1");

		Map mapFile = new HashMap();
		mapFile.put("file",file);

//		String result = HttpUtil.postUpload(httpUrl,mapParam,mapFile,null);
		String result = HttpUtil.postMultipart(httpUrl,null,null,null);
		System.out.println(result);
	}
}
