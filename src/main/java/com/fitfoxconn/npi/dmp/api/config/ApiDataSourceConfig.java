package com.fitfoxconn.npi.dmp.api.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "apiEntityManagerFactory",
    transactionManagerRef = "apiTransactionManager",
    basePackages = {ApiDataSourceConfig.REPO_PACKAGE})
public class ApiDataSourceConfig {
  final static String ENTITY_PACKAGE = "com.fitfoxconn.npi.dmp.api.entity.api";
  final static String REPO_PACKAGE = "com.fitfoxconn.npi.dmp.api.repository.api";

  @Bean
  @ConfigurationProperties("spring.datasource.api")
  public DataSourceProperties apiDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.api.configuration")
  public DataSource apiDataSource() {
    return apiDataSourceProperties().initializeDataSourceBuilder()
        .type(HikariDataSource.class).build();
  }

  @Bean(name = "apiEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean companyEntityManagerFactory(
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(apiDataSource())
        .packages(ENTITY_PACKAGE)
        .build();
  }

  @Bean(name = "apiTransactionManager")
  public PlatformTransactionManager companyTransactionManager(
      final @Qualifier("apiEntityManagerFactory") LocalContainerEntityManagerFactoryBean companyEntityManagerFactory) {
    return new JpaTransactionManager(companyEntityManagerFactory.getObject());
  }
}
