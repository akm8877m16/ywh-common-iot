package ywh.common.iot.service.mqtt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import ywh.common.iot.service.mqtt.mqtt.MqttParameters;
import ywh.common.iot.service.mqtt.mqtt.Publisher;
import ywh.common.mqtt.MQTTPublisher;
import ywh.common.mqtt.annotation.CheckMqttDeviceBinding;
import ywh.common.util.response.Msg;
import ywh.common.util.response.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class MqttController {

    @Autowired
    DiscoveryClient discoveryClient;

    //@Value("${msg:unknown}")
    //private String msg;

    @Value("${mqttRootTopic:}")
    private String mqttRootTopic;

    @Resource
    MQTTPublisher mqttPublisher;

    @Autowired
    MqttParameters mqttParameters;

    @GetMapping(value = "/")
    public String printServiceB() {
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        return serviceInstance.getServiceId() + " (" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + ")" ;
    }

    @GetMapping(path = "/current")
    public Principal getCurrentAccount(Principal principal) {
        return principal;
    }

    /**
     *
     * @param deviceSn
     * @param request
     * @return
     *
     */
    @PostMapping(path = "/control/{deviceSn}")
    @CheckMqttDeviceBinding
    public Msg sendRemoteControl(@PathVariable String deviceSn, Principal principal, HttpServletRequest request){
        String attribute = request.getParameter("attribute");
        String payload = request.getParameter("payload");
        String realTopic = mqttRootTopic + "/" + deviceSn + "/" + attribute;
        mqttPublisher.publishMessage(mqttRootTopic,payload);
        return ResultUtil.success();
    }



}