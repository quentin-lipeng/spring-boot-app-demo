package org.quentin.springbootredisapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.api.rule")
public class ApiRuleProperties {
    private String host = "127.0.0.1";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
