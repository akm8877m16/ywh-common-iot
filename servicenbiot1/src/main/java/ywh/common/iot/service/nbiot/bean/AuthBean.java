package ywh.common.iot.service.nbiot.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="应用对象",description="应用对象:NB-IOT平台注册应用信息")

public class AuthBean {
    @ApiModelProperty(value="用户名,填写应用程序ID",name="appId",example="Hqk3DpZI_UttgPDojttUTtqERV0a",required = true)
    String appId;

    @ApiModelProperty(value="登录用户口令,填写应用程序密码",name="secret",example="QSlYHwDyb5LTmc1fQZ7Qf_caIwEa",required = true)
    String secret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
