package com.codedata.rbac.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "weather.jwt")
public class WeatherJwtProperties {
    private String privateKey;
    private String kid;
    private String sub;
    private long tokenExpireSeconds;
}
