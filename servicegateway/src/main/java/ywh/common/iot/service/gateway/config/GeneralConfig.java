package ywh.common.iot.service.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ywh.common.iot.service.gateway.filter.CustomPostFilter;

@Configuration
public class GeneralConfig {
    @Bean
    public CustomPostFilter customPostFilter()
    {
        return new CustomPostFilter();
    }
}
