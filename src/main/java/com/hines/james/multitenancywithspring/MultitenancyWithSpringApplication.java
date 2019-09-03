package com.hines.james.multitenancywithspring;

import com.hines.james.multitenancywithspring.multitenancy.routing.TenantAwareRoutingSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class MultitenancyWithSpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(MultitenancyWithSpringApplication.class, args);
	}
}
