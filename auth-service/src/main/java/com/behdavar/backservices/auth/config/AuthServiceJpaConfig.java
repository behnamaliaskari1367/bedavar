package com.behdavar.backservices.auth.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(
        entityManagerFactoryRef = "authServiceEntityManager",
        transactionManagerRef = "authServiceTransactionManager",
        basePackages = {
                "com.behdavar.backservices.auth.repository"
        }
)
public class AuthServiceJpaConfig {

    private static final Logger LOGGER = LogManager.getLogger(AuthServiceJpaConfig.class);
    private AuthServiceProperties authServiceProperties;

    @Autowired
    public void prepare(AuthServiceProperties authServiceProperties) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.authServiceProperties = authServiceProperties;
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return LOGGER.traceExit(new AuditorAwareImpl());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean authServiceEntityManager() {
        EntryMessage traceEntry = LOGGER.traceEntry();

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", authServiceProperties.getJpa().getHibernate().getHbm2ddl().getAuto());
        properties.put("hibernate.show_sql", authServiceProperties.getJpa().getHibernate().getShowSql());
        properties.put("hibernate.cache.use_second_level_cache", authServiceProperties.getJpa().getHibernate().getCache().getUseSecondLevelCache());
        properties.put("hibernate.cache.region.factory_class", authServiceProperties.getJpa().getHibernate().getCache().getRegion().getFactoryClass());
        properties.put("hibernate.order_inserts", authServiceProperties.getJpa().getHibernate().getOrderInserts());
        properties.put("hibernate.order_updates", authServiceProperties.getJpa().getHibernate().getOrderUpdates());
        properties.put("hibernate.jdbc.batch_size", authServiceProperties.getJpa().getHibernate().getJdbc().getBatchSize());
        properties.put("hibernate.dialect", authServiceProperties.getJpa().getHibernate().getDialect());

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(authServiceDataSource());
        em.setPackagesToScan(
                "com.behdavar.backservices.auth.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(properties);

        return LOGGER.traceExit(traceEntry, em);
    }

    @Bean
    public DataSource authServiceDataSource() {
        EntryMessage traceEntry = LOGGER.traceEntry("driverClassName: {}",
                authServiceProperties.getJpa().getDataSource().getDriver().getClassName());

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(authServiceProperties.getJpa().getDataSource().getDriver().getClassName());
        dataSource.setJdbcUrl(authServiceProperties.getJpa().getDataSource().getUrl());
        dataSource.setUsername(authServiceProperties.getJpa().getDataSource().getUsername());
        dataSource.setPassword(authServiceProperties.getJpa().getDataSource().getPassword());
        dataSource.setMaximumPoolSize(Integer.parseInt(authServiceProperties.getJpa().getDataSource().getHikari().getMaximumPoolSize()));

        return LOGGER.traceExit(traceEntry, dataSource);
    }

    @Bean
    public PlatformTransactionManager authServiceTransactionManager() {
        EntryMessage traceEntry = LOGGER.traceEntry();

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(authServiceEntityManager().getObject());

        return LOGGER.traceExit(traceEntry, transactionManager);
    }
}
