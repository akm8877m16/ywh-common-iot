package ywh.common.iot.service.nbiot.httpUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.*;

/**
 * ssl通信的client
 */

public class HttpClientUtils2 {

    private static PoolingHttpClientConnectionManager secureConnectionManager;
    private static HttpClientBuilder secureHttpBulder = null;
    private static RequestConfig requestConfig = null;
    private static int MAXCONNECTION = 10;
    private static int DEFAULTMAXCONNECTION = 5;

    private static String CLIENT_KEY_STORE =  "file:/home/ywh-common-iot-test/outgoing.CertwithKey.pkcs12";
    private static String CLIENT_TRUST_KEY_STORE = "file:/home/ywh-common-iot-test/ca.jks";
    //private static String CLIENT_KEY_STORE =  "classpath:outgoing.CertwithKey.pkcs12";
    //private static String CLIENT_TRUST_KEY_STORE = "classpath:ca.jks";
    private static String CLIENT_KEY_STORE_PASSWORD = "IoM@1234";
    private static String CLIENT_TRUST_KEY_STORE_PASSWORD = "Huawei@123";
    private static String CLIENT_KEY_PASS = "IoM@1234";

    public final static String CONTENT_LENGTH = "Content-Length";

    /**
     * 进行安全通信的主机和端口
     */
    private static String HOST = "180.101.147.89";
    private static int PORT = 8743;

    static {
        //设置http的状态参数
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            File cfgFile = ResourceUtils.getFile(CLIENT_TRUST_KEY_STORE);
            FileInputStream trustStoreInput = new FileInputStream(cfgFile);
            trustStore.load(trustStoreInput, CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
            KeyStore clientKeyStore = KeyStore.getInstance("pkcs12");
            File cfgFile2 = ResourceUtils.getFile(CLIENT_KEY_STORE);
            FileInputStream clientKeyStoreInput = new FileInputStream(cfgFile2);
            clientKeyStore.load(clientKeyStoreInput, CLIENT_KEY_STORE_PASSWORD.toCharArray());

            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                    .loadKeyMaterial(clientKeyStore, CLIENT_KEY_PASS.toCharArray())
                    .setSecureRandom(new SecureRandom())
                    .useSSL()
                    .build();

            ConnectionSocketFactory plainSocketFactory = new PlainConnectionSocketFactory();
            SSLConnectionSocketFactory sslSocketFactoy = new SSLConnectionSocketFactory(
                    sslContext, new String[]{"TLSv1.2"}, null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", plainSocketFactory)
                    .register("https", sslSocketFactoy)
                    .build();

            secureConnectionManager = new PoolingHttpClientConnectionManager(r);
            HttpHost target = new HttpHost(HOST, PORT, "https");
            secureConnectionManager.setMaxTotal(MAXCONNECTION);
            //设置每个Route的连接最大数
            secureConnectionManager.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);
            //设置指定域的连接最大数
            secureConnectionManager.setMaxPerRoute(new HttpRoute(target), 20);
            secureHttpBulder = HttpClients.custom().setConnectionManager(secureConnectionManager);
        } catch (Exception e) {
            throw new Error("Failed to initialize the server-side SSLContext", e);
        }
    }

    public static CloseableHttpClient getSecureConnection() throws Exception {
        return secureHttpBulder.build();
    }

    public static HttpUriRequest getRequestMethod(Map<String, String> map,Map<String, String> headerMap, String url, String method) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> e : entrySet) {
            String name = e.getKey();
            String value = e.getValue();
            NameValuePair pair = new BasicNameValuePair(name, value);
            params.add(pair);
        }
        HttpUriRequest reqMethod = null;
        if ("post".equals(method)) {
            reqMethod = RequestBuilder.post().setUri(url)
                    .addParameters(params.toArray(new BasicNameValuePair[params.size()]))
                    .setConfig(requestConfig).build();
        } else if ("get".equals(method)) {
            reqMethod = RequestBuilder.get().setUri(url)
                    .addParameters(params.toArray(new BasicNameValuePair[params.size()]))
                    .setConfig(requestConfig).build();
        }

        addRequestHeader(reqMethod,headerMap);
        return reqMethod;
    }

    private static void addRequestHeader(HttpUriRequest request,
                                         Map<String, String> headerMap)
    {
        if (headerMap == null)
        {
            return;
        }

        for (String headerName : headerMap.keySet())
        {
            if (CONTENT_LENGTH.equalsIgnoreCase(headerName))
            {
                continue;
            }

            String headerValue = headerMap.get(headerName);
            request.addHeader(headerName, headerValue);
        }
    }

    /*
    public static void main(String args[]) throws Exception {
        String url = "https://180.101.147.89:8743/iocm/app/sec/v1.1.0/login";
        String appID = "Hqk3DpZI_UttgPDojttUTtqERV0a";
        String secret = "QSlYHwDyb5LTmc1fQZ7Qf_caIwEa";
        Map params = new HashMap();
        params.put("appId", appID);
        params.put("secret", secret);

        HttpClient client = getSecureConnection(); //使用ssl通信
        HttpUriRequest post = getRequestMethod(params, url, "post");
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String message = EntityUtils.toString(entity, "utf-8");
            System.out.println(message);
        } else {
            System.out.println("请求失败");
        }
    }*/
}