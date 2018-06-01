package ywh.common.iot.service.mqtt.controller;


import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import springfox.documentation.annotations.ApiIgnore;
import ywh.common.bean.MqttPublishBean;
import ywh.common.iot.service.mqtt.mqtt.MqttParameters;
import ywh.common.iot.service.mqtt.mqtt.Publisher;
import ywh.common.mqtt.MQTTPublisher;
import ywh.common.mqtt.annotation.CheckMqttDeviceBinding;
import ywh.common.util.response.Msg;
import ywh.common.util.response.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Api(tags = "MQTT服务接口", description = "MQTT服务模块相关接口")
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

    /*
    @GetMapping(value = "/")
    public String printServiceB() {
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        return serviceInstance.getServiceId() + " (" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + ")" ;
    }
    */
    @ApiIgnore
    @GetMapping(path = "/current")
    public Principal getCurrentAccount(Principal principal) {
        return principal;
    }

    /**
     *
     */
    @ApiOperation(value = "遥控指定设备",notes = "目前只有拥有ROLE_USER权限的用户才能遥控属于自己的设备")
    @PostMapping(path = "/control")
    @CheckMqttDeviceBinding
    public Msg sendRemoteControl(@RequestBody @ApiParam(name="Mqtt发布对象",value="传入json格式,都必填",required=true) MqttPublishBean mqttPublishBean, Principal principal){
        String attribute = mqttPublishBean.getAttribute();
        String payload = mqttPublishBean.getPayload();
        String deviceSn = mqttPublishBean.getSn();
        String realTopic = mqttRootTopic + "/" + deviceSn + "/" + attribute;
        mqttPublisher.publishMessage(realTopic,payload);
        return ResultUtil.success();
    }



}
