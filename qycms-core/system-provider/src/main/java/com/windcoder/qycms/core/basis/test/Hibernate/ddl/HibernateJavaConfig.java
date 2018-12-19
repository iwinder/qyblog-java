package com.windcoder.qycms.core.basis.test.Hibernate.ddl;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;

import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Configuration
@EntityScan("com.windcoder.qycms.*")
@AutoConfigureAfter({HibernateJpaAutoConfiguration.class})
public class HibernateJavaConfig {

//    @Autowired
//    LocalContainerEntityManagerFactoryBean fb;
//    @Autowired
//    DataSource dataSource;

    @ConditionalOnMissingBean({Metadata.class})
    @Bean
    public Metadata getMetadata(StandardServiceRegistry standardServiceRegistry,
                                PersistenceUnitInfo persistenceUnitInfo) {
        MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);

        List<String> managedClassNames = persistenceUnitInfo.getManagedClassNames();
        for (String managedClassName : managedClassNames) {
            metadataSources.addAnnotatedClassName(managedClassName);
        }

        Metadata metadata = metadataSources.buildMetadata();
        return metadata;
    }

    @ConditionalOnMissingBean({StandardServiceRegistry.class})
    @Bean
    public StandardServiceRegistry getStandardServiceRegistry(JpaProperties jpaProperties) {
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();

//        Map<String, String> properties3 = jpaProperties.getHibernateProperties(dataSource);
//        jpaProperties.getHibernate();
//
//        fb.setDataSource(dataSource);

        Map<String, String> properties = jpaProperties.getProperties();
//        Map<String, Object> properties = fb.getJpaPropertyMap();
//        properties.putAll(properties3);
//        properties.put("hibernate.dialect",fb.getJpaPropertyMap().get("hibernate.dialect"));
        ssrb.applySettings(properties);

        StandardServiceRegistry ssr = ssrb.build();

        return ssr;
    }

    @ConditionalOnMissingBean({PersistenceUnitInfo.class})
    @Bean
    public PersistenceUnitInfo getPersistenceUnitInfo(EntityScanPackages entityScanPackages) {
        List<String> packagesToScan = entityScanPackages.getPackageNames();

        DefaultPersistenceUnitManager persistenceUnitManager = new DefaultPersistenceUnitManager();

        String[] packagesToScanArr = (String[]) packagesToScan.toArray(new String[packagesToScan.size()]);
        persistenceUnitManager.setPackagesToScan(packagesToScanArr);
        persistenceUnitManager.afterPropertiesSet();

        PersistenceUnitInfo persistenceUnitInfo = persistenceUnitManager.obtainDefaultPersistenceUnitInfo();
        return persistenceUnitInfo;
    }
}
