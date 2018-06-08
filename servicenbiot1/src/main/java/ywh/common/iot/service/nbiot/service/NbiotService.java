package ywh.common.iot.service.nbiot.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import ywh.common.iot.service.nbiot.bean.AuthBean;
import ywh.common.iot.service.nbiot.httpUtil.HttpClientUtils2;

import java.util.HashMap;
import java.util.Map;

@Service
public class NbiotService {

    public String getAuthToken(AuthBean authBean){
        String url = "https://180.101.147.89:8743/iocm/app/sec/v1.1.0/login";
        String appID = authBean.getAppId();
        String secret = authBean.getSecret();
        Map params = new HashMap();
        params.put("appId", appID);
        params.put("secret", secret);
        String result = "";
        try {
            HttpClient client = HttpClientUtils2.getSecureConnection(); //使用ssl通信
            HttpUriRequest post = HttpClientUtils2.getRequestMethod(params,null, url, "post");
            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "utf-8");
                System.out.println(result);
            } else {
                System.out.println("请求失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String deviceCredentials(String deviceId, String appId, Map<String,String> headerMap){
        String url = "https://180.101.147.89:8743/iocm/app/reg/v1.1.0/deviceCredentials/"+deviceId;
        Map queryMap = new HashMap();
        if(appId != null){
            queryMap.put("appId",appId);
        }
        String result = "";
        try {
            HttpClient client = HttpClientUtils2.getSecureConnection(); //使用ssl通信
            HttpUriRequest get = HttpClientUtils2.getRequestMethod(queryMap,headerMap, url, "get");
            HttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "utf-8");
                System.out.println(result);
            } else {
                System.out.println("请求失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
