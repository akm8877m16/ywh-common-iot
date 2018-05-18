package ywh.common.iot.service.mqtt.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ywh:
 mqtt:
 broker: 39.104.49.84
 port: 1883
 qos: 1
 userName:
 password:
 */
@Component
public class MqttParameters {

    @Value("${ywh.mqtt.broker:39.104.49.84}")
    private String broker;

    @Value("${ywh.mqtt.port:1883}")
    private String port;

    @Value("${ywh.mqtt.qos:1}")
    private int qos;

    @Value("${ywh.mqtt.userName:}")
    private String userName;

    @Value("${ywh.mqtt.password:}")
    private String password;

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
