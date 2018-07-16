package ywh.common.iot.service.nbiot.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;
import ywh.common.iot.service.nbiot.jsonUtil.JsonUtil;
import ywh.common.util.response.Msg;
import ywh.common.util.response.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "NB-IOT 推送相关接口", description = "NB-IOT 服务模块推送相关接口")
@RestController
public class CallbackController {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    @ApiOperation(value = "设备消息确认通知",notes = "订阅设备消息确认通知后(订阅的通知类型为 messageConfirm),设备向平台确认收到\n" +
            "消息时,平台会给第三方应用推送设备消息确认通知.")
    @PostMapping(value = "/commandConfirm")
    public Msg commandConfirm(@RequestBody String params){
        logger.info(params);
        System.out.println(params);
        Map map = JsonUtil.string2Map(params);
        logger.info(map.toString());
        System.out.println(map.toString());
        return ResultUtil.success();
    }


    @ApiOperation(value = "设备服务信息变化通知",notes = "订阅设备服务信息变化通知后(订阅的通知类型为 serviceInfoChanged),设备服务信\n" +
            "息发生变化时平台会给第三方应用推送设备服务信息变化通知。")
    @PostMapping(value = "/deviceStaticInfoChange")
    public Msg deviceStaticInfoChange(@RequestBody String params){
        logger.info(params);
        System.out.println(params);
        Map map = JsonUtil.string2Map(params);
        logger.info(map.toString());
        System.out.println(map.toString());
        return ResultUtil.success();
    }

    @ApiOperation(value = "设备数据变化通知",notes = "订阅设备数据变化通知后(订阅的通知类型为 deviceDataChanged),设备数据发生变 化时平台会给第三方应用推送设备数据变化通知。")
    @PostMapping(value = "/deviceDataChanged")
    public Msg deviceDataChanged(@RequestBody String params){
        logger.info(params);
        System.out.println(params);
        Map map = JsonUtil.string2Map(params);
        logger.info(map.toString());
        System.out.println(map.toString());
        return ResultUtil.success();
    }

    @ApiOperation(value = "批量设备数据变化通知",notes = "订阅设备数据批量变化通知后(订阅的通知类型为 deviceDatasChanged),发生批量设备数据变化时,平台会给第三方应用推送设备数据批量变化通知。")
    @PostMapping(value = "/deviceDatasChanged")
    public Msg deviceDatasChanged(@RequestBody String params){
        logger.info(params);
        System.out.println(params);
        Map map = JsonUtil.string2Map(params);
        logger.info(map.toString());
        System.out.println(map.toString());
        return ResultUtil.success();
    }


}
