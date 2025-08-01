package com.codedata.rbac.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static PoolingHttpClientConnectionManager cm = null;
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    static {
        // 初始化连接池，可用于请求HTTP
        cm = new PoolingHttpClientConnectionManager(getRegistry());
        // 整个连接池最大连接数
        cm.setMaxTotal(200);
        // 每路由最大连接数，默认值是2
        cm.setDefaultMaxPerRoute(5);
        //  socket配置 3分钟未响应阻塞超时
        SocketConfig socketConfig  = SocketConfig.custom().setSoTimeout(3*60*1000).build();
        cm.setDefaultSocketConfig(socketConfig);
    }
    private static Registry getRegistry() {
        return RegistryBuilder.create().register("http",
                new PlainConnectionSocketFactory()).build();
    }
    /**
     * 发送 HTTP GET请求 不带参数和请求头
     *
     * @param url
     * @return
     * @throws Exception
     */
    //
    public static String httpGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        return doHttp(httpGet);
    }
    public static String httpsGet(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
        URIBuilder ub = new URIBuilder(url);
        List<NameValuePair> pairs = covertParams(params);
        ub.setParameters(pairs);
        HttpGet httpGet = new HttpGet(ub.build());

        if (headers != null) {
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }

        // 创建支持 HTTPS 忽略证书校验的 HttpClient
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(new TrustSelfSignedStrategy()) // 信任所有自签名证书
                .build();

        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE) // 不校验域名
                .build();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {

            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }
    /**
     * 送 HTTP GET请求 带请求参数，不带请求头
     */
    public static String httpGet(String url, Map<String, Object> params) throws Exception {
        // 转换请求参数
        List<NameValuePair> pairs = covertParams(params);
        // 装载请求地址和参数
        URIBuilder ub = new URIBuilder(url);
        ub.setParameters(pairs);
        HttpGet httpGet = new HttpGet(ub.build());
        return doHttp(httpGet);
    }

    /**
     * 送 HTTP GET请求 带请求参数，和请求头
     */

    public static String httpGet(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
        // 使用 URIBuilder 直接解析原始 URL
        URIBuilder ub = new URIBuilder(url);

        // 添加请求参数
        List<NameValuePair> pairs = covertParams(params);
        ub.setParameters(pairs); // 会覆盖原有 query 参数

        // 构建请求
        HttpGet httpGet = new HttpGet(ub.build());

        // 设置请求头
        if (headers != null) {
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }

        return doHttp(httpGet);
    }



    /**
     * 发送 HTTP POST请求
     * 不带请求参数和请求头
     *
     * @param url 地址
     * @return
     * @throws Exception
     */
    public static String httpPost(String url) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        return doHttp(httpPost);
    }

    /**
     * 发送 HTTP POST请求带请求参数，不带请求头
     *
     * @param url    地址
     * @param params 参数
     * @return
     * @throws Exception
     */
    public static String httpPost(String url, Map<String, Object> params) throws Exception {
        // 转换请求参数
        List<NameValuePair> pairs = covertParams(params);

        HttpPost httpPost = new HttpPost(url);
        // 设置请求参数
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8.name()));
        return doHttp(httpPost);
    }


    /**
     * 发送 HTTP POST请求
     * 带请求参数和请求头
     *
     * @param url     地址
     * @param headers 请求头
     * @param params  参数
     * @return
     * @throws Exception
     */
    public static String httpPost(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
        // 转换请求参数
        List<NameValuePair> pairs = covertParams(params);

        HttpPost httpPost = new HttpPost(url);
        // 设置请求参数
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8.name()));
        // 设置请求头
        for (Map.Entry<String, Object> param : headers.entrySet())
            httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
        return doHttp(httpPost);
    }

    /**
     * 发送 HTTP POST请求，参数格式JSON
     * 请求参数是JSON格式，数据编码是UTF-8
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String httpPostJson(String url, String param) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
        // 设置请求参数
        httpPost.setEntity(new StringEntity(param, StandardCharsets.UTF_8.name()));
        return doHttp(httpPost);
    }
    /**
     * 发送 HTTP POST请求，参数格式XML
     * 请求参数是XML格式，数据编码是UTF-8
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String httpPostXml(String url, String param) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/xml; charset=UTF-8");
        // 设置请求参数
        httpPost.setEntity(new StringEntity(param, StandardCharsets.UTF_8.name()));
        return doHttp(httpPost);
    }

    /**
     * 将Map键值对拼接成QueryString字符串，UTF-8编码
     */
    public static String getQueryStr(Map<String, Object> params) throws Exception {
        return URLEncodedUtils.format(covertParams(params), StandardCharsets.UTF_8.name());
    }


    private static String doHttp(HttpRequestBase request) throws Exception {
        // 通过连接池获取连接对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        return doRequest(httpClient, request);
    }

    private static String doRequest(CloseableHttpClient httpClient, HttpRequestBase request) throws Exception {
        String result = null;
        CloseableHttpResponse response = null;
        try {
            // 获取请求结果
            response = httpClient.execute(request);
            // 解析请求结果
            HttpEntity entity = response.getEntity();
            // 转换结果
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8.name());
            // 关闭IO流
            EntityUtils.consume(entity);
        } catch (Exception e) {
            logger.error("发送http请求失败: {}",e.getMessage());
            throw new Exception("发送http请求失败: " + e.getMessage());
        } finally {
            if (null != response){
                response.close();
            }
        }
        return result;
    }

    /**
     * 转换请求参数
     *
     * @param params
     * @return
     */
    public static List<NameValuePair> covertParams(Map<String, Object> params) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param : params.entrySet())
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        return pairs;
    }
}
