package com.helix.demo.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.MessageDigest;

/**
 *
 * 快递鸟电子面单接口
 *
 * @技术QQ群: 340378554
 * @see: http://kdniao.com/api-eorder
 * @copyright: 深圳市快金数据技术服务有限公司
 *
 * ID和Key请到官网申请：http://kdniao.com/reg
 */

public class KdGoldAPIDemo {

//	final String EBusinessID = "1632971";//kdniao.com EBusinessID
//	final String AppKey = "6d21e6cd-84cf-4733-a32d-2fcca00df11c"; //kdniao.com AppKey

	//电子面单测试appid信息
	final String EBusinessID = "test1632971";//kdniao.com EBusinessID
	final String AppKey = "d95acc59-5d52-4c94-b7b4-694bdb46865e"; //kdniao.com AppKey
	//电商ID
//  private String EBusinessID="请到快递鸟官网申请http://kdniao.com/reg";
	//电商加密私钥，快递鸟提供，注意保管，不要泄漏
//  private String AppKey="请到快递鸟官网申请http://kdniao.com/reg";
	//请求url, 正式环境地址：http://api.kdniao.com/api/Eorderservice    测试环境地址：http://testapi.kdniao.com:8081/api/EOrderService
//	private String ReqURL="http://testapi.kdniao.com:8081/api/Eorderservice";


	//电子面单测试地址 http://sandboxapi.kdniao.com:8080/kdniaosandbox/gateway/exterfaceInvoke.json
	private String ReqURL="http://sandboxapi.kdniao.com:8080/kdniaosandbox/gateway/exterfaceInvoke.json";

//	private String ReqURL="http://api.kdniao.com/api/Eorderservice";
	//快递公司不支持的字符
	private static String EXPRESS_NO_SUPPORT_CHAR_REG = "'|\"|#|&|\\+|%|\\\\|<|>";


	public static void main(String[] args) throws Exception{
		KdGoldAPIDemo kdGoldAPIDemo = new KdGoldAPIDemo();
//		System.out.println(kdGoldAPIDemo.orderOnlineByJson());
		System.out.println(kdGoldAPIDemo.orderOnlineByJsonSTO());

	}

	/**
	 * Json方式 SF电子面单
	 * @throws Exception
	 */
	public String orderOnlineByJson() throws Exception{
		String requestData= "{'OrderCode': '012657700391'," +
				"'ShipperCode':'SF'," +
				"'PayType':1," +
				"'ExpType':1," +
				"'Cost':1.0," +
				"'OtherCost':1.0," +
				"'Sender':" +
				"{" +
				"'Company':'LV','Name':'Taylor','Mobile':'15018442396','ProvinceName':'上海','CityName':'上海','ExpAreaName':'青浦区','Address':'明珠路73号'}," +
				"'Receiver':" +
				"{" +
				"'Company':'GCCUI','Name':'Yann','Mobile':'15018442396','ProvinceName':'北京','CityName':'北京','ExpAreaName':'朝阳区','Address':'三里屯街道雅秀大厦'}," +
				"'Commodity':" +
				"[{" +
				"'GoodsName':'鞋子','Goodsquantity':1,'GoodsWeight':1.0}]," +
				"'Weight':1.0," +
				"'Quantity':1," +
				"'Volume':0.0," +
				"'Remark':'小心轻放'," +
				"'IsReturnPrintTemplate':1}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1007");
		String dataSign=encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");

		String result=sendPost(ReqURL, params);

		//根据公司业务处理返回的信息......

		return result;
	}

	/**
	 * Json方式 电子面单申通
	 * @throws Exception
	 */
	public String orderOnlineByJsonSTO() throws Exception{
		String requestData= "{'OrderCode': '2020041504'," +
				"'ShipperCode':'STO'," +
//				"'CustomerName':'teststo'," +
//				"'CustomerPwd':'teststopwd'," +
//				"'SendSite':'teststosendsite'," +
				"'CustomerName':'华英雄'," +
				"'CustomerPwd':'13588862291'," +
				"'SendSite':'310030'," +
				"'PayType':1," +
				"'ExpType':'1'," +
//				"'Cost':1.0," +
//				"'OtherCost':1.0," +
//				"'CustomArea':'CustomArea-你好'," +
				"'Sender':" +
				"{" +
				"'Name':'Taylor','Mobile':'15018442396','ProvinceName':'上海2','CityName':'上海','ExpAreaName':'青浦区','Address':'明珠路73号'}," +
				"'Receiver':" +
				"{" +
				"'Name':'Yann','Mobile':'15018442396','ProvinceName':'北京','CityName':'北京','ExpAreaName':'朝阳区','Address':'三里屯街道雅秀大厦'}," +
				"'Commodity':" +
				"[{" +
				"'GoodsName':'潮流100%棉蓝黑','Goodsquantity':1,'GoodsWeight':1.0}]," +
				"'Weight':1.0," +
				"'Quantity':1," +
				"'Volume':0.0," +
				"'Remark':'小心轻放222'," +
				"'TemplateSize':'130'," +
				"'IsReturnPrintTemplate':'1'}";
		System.out.println(requestData);
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1007");
		String dataSign=encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");

		String result=sendPost(ReqURL, params);

		//根据公司业务处理返回的信息......

		return result;
	}

	/**
	 * MD5加密
	 * @param str 内容
	 * @param charset 编码方式
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private String MD5(String str, String charset) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes(charset));
		byte[] result = md.digest();
		StringBuffer sb = new StringBuffer(32);
		for (int i = 0; i < result.length; i++) {
			int val = result[i] & 0xff;
			if (val <= 0xf) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(val));
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * base64编码
	 * @param str 内容
	 * @param charset 编码方式
	 * @throws UnsupportedEncodingException
	 */
	private String base64(String str, String charset) throws UnsupportedEncodingException{
		String encoded = Base64.encode(str.getBytes(charset));
		return encoded;
	}

	@SuppressWarnings("unused")
	private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
		String result = URLEncoder.encode(str, charset);
		return result;
	}

	/**
	 * 电商Sign签名生成
	 * @param content 内容
	 * @param keyValue Appkey
	 * @param charset 编码方式
	 * @throws UnsupportedEncodingException ,Exception
	 * @return DataSign签名
	 */
	@SuppressWarnings("unused")
	private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
	{
		if (keyValue != null)
		{
			return base64(MD5(content + keyValue, charset), charset);
		}
		return base64(MD5(content, charset), charset);
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * @param url 发送请求的 URL
	 * @param params 请求的参数集合
	 * @return 远程资源的响应结果
	 */
	@SuppressWarnings("unused")
	private String sendPost(String url, Map<String, String> params) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// POST方法
			conn.setRequestMethod("POST");
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.connect();
			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			// 发送请求参数
			if (params != null) {
				StringBuilder param = new StringBuilder();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					System.out.println(entry.getKey()+">>>>"+entry.getValue());
					if(param.length()>0){
						param.append("&");
					}
					param.append(entry.getKey());
					param.append("=");
					param.append(entry.getValue());
					System.out.println(entry.getKey()+":"+entry.getValue());
				}
				System.out.println("param:"+param.toString());
				out.write(param.toString());
			}
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result.toString();
	}


}
