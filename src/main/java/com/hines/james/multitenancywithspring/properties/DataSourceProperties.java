package com.hines.james.multitenancywithspring.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "com.hines.james")
public class DataSourceProperties {
    private List<MultitenancyDataSource> dataSources;
}
