package ywh.common.iot.service.nbiot;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import ywh.common.iot.service.nbiot.httpUtil.HttpClientUtils2;


import java.util.HashMap;
import java.util.Map;

import static ywh.common.iot.service.nbiot.httpUtil.HttpClientUtils2.getRequestMethod;

@EnableDiscoveryClient
@SpringBootApplication
public class NbiotApplication {

    public static void main(String[] args) {
        SpringApplication.run(NbiotApplication.class, args);
    }
}

