package com.windcoder.qycms.core.basis.test.Hibernate.ddl;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.spi.PersistenceUnitInfo;
import java.util.Properties;

/**
 * [Extract DDL in Spring Boot + Spring Data JPA](https://bestna.wordpress.com/2015/06/05/extract-ddl-in-springboot-jpa/)
 */
public class JPASchemaExtractor {

    @Autowired
    private LocalContainerEntityManagerFactoryBean lcemfb;

    public void extract_table_schema() throws Exception {

        final Properties prop = new Properties();
        prop.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");

        final PersistenceUnitInfo info = lcemfb.getPersistenceUnitInfo();
        final PersistenceUnitInfoDescriptor puid = new PersistenceUnitInfoDescriptor(info);
        final EntityManagerFactoryBuilderImpl emfbi = new EntityManagerFactoryBuilderImpl(puid, prop);
//
//        final ServiceRegistry serviceRegistry = emfbi.buildServiceRegistry();
//        final Configuration configuration = emfbi.buildHibernateConfiguration(serviceRegistry);

//        final SchemaExport schemaExport = new SchemaExport(serviceRegistry, configuration);
//        schemaExport.setDelimiter(";");
//
//        schemaExport.execute(true, false, false, true);
    }
}
