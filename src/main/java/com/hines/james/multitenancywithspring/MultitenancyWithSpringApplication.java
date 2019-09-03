package com.hines.james.multitenancywithspring;

import com.hines.james.multitenancywithspring.multitenancy.core.ThreadLocalStorage;
import com.hines.james.multitenancywithspring.multitenancy.routing.TenantAwareRoutingSource;
import com.hines.james.multitenancywithspring.properties.DataSourceProperties;
import com.hines.james.multitenancywithspring.properties.MultitenancyDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
@EnableTransactionManagement
public class MultitenancyWithSpringApplication extends SpringBootServletInitializer {
    @Autowired
    private DataSourceProperties dataSourceProperties;

	public static void main(String[] args) {
		new MultitenancyWithSpringApplication()
				.configure(new SpringApplicationBuilder(MultitenancyWithSpringApplication.class))
				.properties(getDefaultProperties())
				.run(args);
	}

	@Bean
	public DataSource dataSource() {

		AbstractRoutingDataSource dataSource = new TenantAwareRoutingSource();

		Map<Object,Object> targetDataSources = new HashMap<>();

        for (MultitenancyDataSource multitenancyDataSource: dataSourceProperties.getDataSources()) {
            targetDataSources.put(multitenancyDataSource.getName(), createDataSource(multitenancyDataSource));
        }

		dataSource.setTargetDataSources(targetDataSources);

		dataSource.afterPropertiesSet();

		ThreadLocalStorage.setTenantName(dataSourceProperties.getDataSources().get(0).getName());

		return dataSource;
	}

	public DataSource createDataSource(MultitenancyDataSource multitenancyDataSource) {

		HikariDataSource dataSource = new HikariDataSource();

		dataSource.setInitializationFailTimeout(0);
		dataSource.setMaximumPoolSize(5);
		dataSource.setJdbcUrl(multitenancyDataSource.getUrl());
		dataSource.setUsername(multitenancyDataSource.getUsername());
		dataSource.setPassword(multitenancyDataSource.getPassword());

		return dataSource;
	}

	private static Properties getDefaultProperties() {

		Properties defaultProperties = new Properties();

		// Set sane Spring Hibernate properties:
		defaultProperties.put("spring.jpa.show-sql", "true");
		defaultProperties.put("spring.datasource.initialize", "false");

		// Prevent JPA from trying to Auto Detect the Database:
		defaultProperties.put("spring.jpa.database", "mysql");

		// Prevent Hibernate from Automatic Changes to the DDL Schema:
		defaultProperties.put("spring.jpa.hibernate.ddl-auto", "validate");

		return defaultProperties;
	}
}
