package ywh.common.iot.service.mqtt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ywh.common.mqtt.aspectHandler.CheckMqttDeviceBindingHandler;

@Configuration
//@Import({CheckMqttDeviceBindingHandler.class})
public class GeneralConfiguration {
    @Bean
    CheckMqttDeviceBindingHandler checkMqttDeviceBindingHandler(){
        return new CheckMqttDeviceBindingHandler();
    }

}
