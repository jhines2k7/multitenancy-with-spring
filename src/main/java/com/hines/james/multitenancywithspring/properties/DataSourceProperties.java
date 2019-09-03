package com.hines.james.multitenancywithspring.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "com.hines.james")
public class DataSourceProperties {
    private List<MultitenancyDataSource> dataSources;
}
