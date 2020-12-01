package com.helix.demo.openapi.google;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.v1.Customsearch;
import com.google.api.services.customsearch.v1.model.Result;
import com.google.api.services.customsearch.v1.model.Search;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

/**
 * google自定义搜索api测试
 * @author lijianyu@yunloan.net
 * @date 2020-11-27 15:35
 */
public class GoogleApiTest {

    /**
     * google自定义搜索api，目前只支持rest请求，不支持grpc
     * 参考：https://programmablesearchengine.google.com/cse/setup/basic?cx=da3fac98bc5fe4ee0
     *
     */
    @Test
    public void customSearch() throws IOException {
        //搜索引擎 ID
        String cx = "da3fac98bc5fe4ee0";
        //appkey可以暴露在客户端，管理者可以再后台对appkey的使用进行限制
        String appKey = "AIzaSyCVV_RBuRB3_LKLP6VUCD8Yj92bRTQFOXc";
        String query = "衣服";

        String url = "/customsearch/v1?cx=%s&q=%s&key=%s";

        //Creating an HttpHost object for proxy，mac机子上shadowsocks http代理是1087端口
        HttpHost proxyhost = new HttpHost("127.0.0.1",1087);

        //Creating an HttpHost object for target
        HttpHost targethost = new HttpHost("customsearch.googleapis.com",443,"https");

        //creating a RoutePlanner object
        HttpRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxyhost);

        //Setting the route planner to the HttpClientBuilder object
        HttpClientBuilder clientBuilder = HttpClients.custom();
        clientBuilder = clientBuilder.setRoutePlanner(routePlanner);

        //Building a CloseableHttpClient
        CloseableHttpClient httpclient = clientBuilder.build();

        //Creating an HttpGet object
        HttpGet httpget = new HttpGet(String.format(url,cx,query,appKey));

        //Executing the Get request
        HttpResponse httpresponse = httpclient.execute(targethost, httpget);

        System.out.println("========");
        System.out.println(httpresponse.getEntity().getContentType());
        System.out.println(httpresponse.getEntity().getContentLength());
        System.out.println(EntityUtils.toString(httpresponse.getEntity()));

//        System.out.println(String.format(url,cx,query,appKey));
//        String result = HttpUtil.get(String.format(url,cx,query,appKey));
//        System.out.println(result);
    }

    /**
     * google自定义搜索api，通过官方jar包形式来调用
     */
    @Test
    public void customSearchByJar() {
        //代理
        NetHttpTransport netHttpTransport = new NetHttpTransport.Builder().setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1087))).build();

        Customsearch customsearch = new Customsearch(netHttpTransport, new JacksonFactory(), null);

        try {
            Customsearch.Cse.List list = customsearch.cse().list();
            list.setKey("AIzaSyCVV_RBuRB3_LKLP6VUCD8Yj92bRTQFOXc");
            list.setCx("da3fac98bc5fe4ee0");
            list.setQ("衣服");
            Search results = list.execute();
            List<Result> items = results.getItems();

            for (Result result : items) {
                System.out.println("Title:" + result.getHtmlTitle());

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
