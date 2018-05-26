package ywh.common.iot.service.mqtt.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import springfox.documentation.annotations.ApiIgnore;
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
     * @param deviceSn
     * @param request
     * @return
     *
     */
    @ApiOperation(value = "遥控指定设备",notes = "目前只有拥有ROLE_USER权限的用户才能遥控属于自己的设备")
    @PostMapping(path = "/control/{deviceSn}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceSn", value = "设备唯一标识Sn", required = true, dataType = "String",paramType="path"),
            @ApiImplicitParam(name = "attribute", value = "需要遥控的设备topic属性", required = true, dataType = "String",paramType="form"),
            @ApiImplicitParam(name = "payload", value = "设备遥控指令", required = true, dataType = "String",paramType="form")
    })
    @CheckMqttDeviceBinding
    public Msg sendRemoteControl(@PathVariable String deviceSn, Principal principal, HttpServletRequest request){
        String attribute = request.getParameter("attribute");
        String payload = request.getParameter("payload");
        String realTopic = mqttRootTopic + "/" + deviceSn + "/" + attribute;
        mqttPublisher.publishMessage(realTopic,payload);
        return ResultUtil.success();
    }



}
