package com.hines.james.multitenancywithspring;

import com.hines.james.multitenancywithspring.multitenancy.core.ThreadLocalStorage;
import com.hines.james.multitenancywithspring.multitenancy.routing.TenantAwareRoutingSource;
import com.zaxxer.hikari.HikariDataSource;
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

		targetDataSources.put("userDbOne", userDbOne());
		targetDataSources.put("userDbTwo", userDbTwo());

		dataSource.setTargetDataSources(targetDataSources);

		dataSource.afterPropertiesSet();

		ThreadLocalStorage.setTenantName("userDbOne");

		return dataSource;
	}

	public DataSource userDbOne() {

		HikariDataSource dataSource = new HikariDataSource();

		dataSource.setInitializationFailTimeout(0);
		dataSource.setMaximumPoolSize(5);
		dataSource.setJdbcUrl("jdbc:mysql://165.227.186.184:3306/userdb");
		dataSource.setUsername("multitenancyuser");
		dataSource.setPassword("multitenancypassword");

		return dataSource;
	}

	public DataSource userDbTwo() {

		HikariDataSource dataSource = new HikariDataSource();

		dataSource.setInitializationFailTimeout(0);
		dataSource.setMaximumPoolSize(5);
		dataSource.setJdbcUrl("jdbc:mysql://165.227.177.255:3307/userdb");
		dataSource.setUsername("multitenancyuser");
		dataSource.setPassword("multitenancypassword");

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
