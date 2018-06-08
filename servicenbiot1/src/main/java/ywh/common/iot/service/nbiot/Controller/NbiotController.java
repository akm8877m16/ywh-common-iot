package ywh.common.iot.service.nbiot.Controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ywh.common.iot.service.nbiot.bean.AuthBean;
import ywh.common.iot.service.nbiot.jsonUtil.JsonUtil;
import ywh.common.iot.service.nbiot.service.NbiotService;
import ywh.common.util.response.Msg;
import ywh.common.util.response.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "NB-IOT 服务接口", description = "NB-IOT 服务模块相关接口")
@RestController
public class NbiotController {

    @Autowired
    NbiotService nbiotService;

    @ApiOperation(value = "Auth(鉴权)",notes = "实现第三方系统在访问开放API之前的认证")
    @PostMapping(value = "/auth")
    public Msg getAuthToken(@RequestBody @ApiParam(name="应用对象",value="传入json格式,都必填",required=true) AuthBean authBean){
        String result = nbiotService.getAuthToken(authBean);
        Map map = JsonUtil.string2Map(result);
        return ResultUtil.success(map);
    }


    @ApiOperation(value = "查询设备激活状态",notes = "第三方应用调用此接口查询指定设备是否已接入 IoT 平台并激活\n.支持第三方应用通过设备 ID 查询设备激活状态,支持查询本应用的设备和授予权限的" +
            "其它应用的设备 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备 ID,用于唯一标识一个设备,在注" +
                    "册设备时由 IoT 平台分配获得", required = true, dataType = "String"),
            @ApiImplicitParam(name = "appId", value = "第三方应用的身份标识,用于唯一标识\n" +
                    "一个应用。开发者可通过该标识来指定\n" +
                    "哪个应用来调用 IoT 平台的开放 API。\n" +
                    "appid 在 IoT 平台的 SP Portal 上创建应用\n" +
                    "时获得。此处填写授权应用的 appid。", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "app_key", value = "用户名，填写应用程序ID", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "Authorization", value = "调用Auth接口获取到的accessToken,格式为: Bearer {accessToken}", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping(value = "/deviceCredentials/{deviceId}")
    public Msg deviceCredentials(@PathVariable("deviceId") String deviceId, @RequestParam("appId") String appId, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        String app_key = request.getHeader("app_key");
        if(authorization == null || app_key == null){
            return  ResultUtil.success("请添加请求头信息");
        }
        Map headerMap = new HashMap();
        headerMap.put("Authorization",authorization);
        headerMap.put("app_key",app_key);

        String result = nbiotService.deviceCredentials(deviceId,appId,headerMap);
        Map map = JsonUtil.string2Map(result);
        return ResultUtil.success(map);
    }


}
