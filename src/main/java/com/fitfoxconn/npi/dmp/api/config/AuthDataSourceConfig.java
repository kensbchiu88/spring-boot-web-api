package com.fitfoxconn.npi.dmp.api.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "authEntityManagerFactory",
    transactionManagerRef = "authTransactionManager",
    basePackages = {AuthDataSourceConfig.REPO_PACKAGE})
public class AuthDataSourceConfig {

  final static String ENTITY_PACKAGE = "com.fitfoxconn.npi.dmp.api.entity.auth";
  final static String REPO_PACKAGE = "com.fitfoxconn.npi.dmp.api.repository.auth";

  @Bean
  @ConfigurationProperties("spring.datasource.auth")
  public DataSourceProperties authDataSourceProperties() {
    return new DataSourceProperties();
  }


  @Bean
  @ConfigurationProperties("spring.datasource.auth.configuration")
  public DataSource authDataSource() {
    return authDataSourceProperties().initializeDataSourceBuilder()
        .type(HikariDataSource.class).build();
  }

  @Bean(name = "authEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean companyEntityManagerFactory(
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(authDataSource())
        .packages(ENTITY_PACKAGE)
        .build();
  }


  @Bean(name = "authTransactionManager")
  public PlatformTransactionManager companyTransactionManager(
      final @Qualifier("authEntityManagerFactory") LocalContainerEntityManagerFactoryBean companyEntityManagerFactory) {
    return new JpaTransactionManager(companyEntityManagerFactory.getObject());
  }

}
