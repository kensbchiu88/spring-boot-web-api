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
    entityManagerFactoryRef = "odsEntityManagerFactory",
    transactionManagerRef = "odsTransactionManager",
    basePackages = {OdsDataSourceConfig.REPO_PACKAGE})
public class OdsDataSourceConfig {
  final static String ENTITY_PACKAGE = "com.fitfoxconn.npi.dmp.api.entity.ods";
  final static String REPO_PACKAGE = "com.fitfoxconn.npi.dmp.api.repository.ods";

  @Primary
  @Bean
  @ConfigurationProperties("spring.datasource.ods")
  public DataSourceProperties odsDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean
  @ConfigurationProperties("spring.datasource.ods.configuration")
  public DataSource odsDataSource() {
    return odsDataSourceProperties().initializeDataSourceBuilder()
        .type(HikariDataSource.class).build();
  }

  @Primary
  @Bean(name = "odsEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean companyEntityManagerFactory(
      EntityManagerFactoryBuilder builder) {
    return builder
        .dataSource(odsDataSource())
        .packages(ENTITY_PACKAGE)
        .build();
  }

  @Primary
  @Bean(name = "odsTransactionManager")
  public PlatformTransactionManager companyTransactionManager(
      final @Qualifier("odsEntityManagerFactory") LocalContainerEntityManagerFactoryBean companyEntityManagerFactory) {
    return new JpaTransactionManager(companyEntityManagerFactory.getObject());
  }
}
