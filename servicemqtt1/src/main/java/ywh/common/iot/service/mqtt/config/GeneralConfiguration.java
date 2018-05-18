package ywh.common.iot.service.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import ywh.common.iot.service.mqtt.mqtt.MqttParameters;
import ywh.common.mqtt.aspectHandler.CheckMqttDeviceBindingHandler;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class GeneralConfiguration {
    @Bean
    CheckMqttDeviceBindingHandler checkMqttDeviceBindingHandler(){
        return new CheckMqttDeviceBindingHandler();
    }

    @Bean("taskExecutor")
    public Executor taskExecutor()
    {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }

}
