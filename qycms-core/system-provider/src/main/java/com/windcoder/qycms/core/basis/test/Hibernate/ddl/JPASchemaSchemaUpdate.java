package com.windcoder.qycms.core.basis.test.Hibernate.ddl;

import org.hibernate.Session;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;

import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.internal.EntityManagerFactoryImpl;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参考资料：
 * https://stackoverflow.com/questions/34612019/programmatic-schemaexport-schemaupdate-with-hibernate-5-and-spring-4
 */
@Component
@EntityScan("com.windcoder.qycms.*")
//@AutoConfigureAfter({JPASchemaExtractor2.class})
public class JPASchemaSchemaUpdate implements ApplicationRunner {
    private static final String SCHEMA_SQL2 = "db/base/update-ddl_2_%s.sql";

    @Autowired
    LocalContainerEntityManagerFactoryBean fb;

    @Autowired
    PersistenceUnitInfo persistenceUnitInfo;

//    @Autowired
//    DataSource dataSource;


    @Override
    public void run(ApplicationArguments args) throws Exception {

//        fb.setDataSource(dataSource);

        EntityManagerFactoryImpl emf=(EntityManagerFactoryImpl)fb.getNativeEntityManagerFactory();

        SessionFactoryImpl sf= (SessionFactoryImpl) emf.getSessionFactory();
        StandardServiceRegistry serviceRegistry = sf.getSessionFactoryOptions().getServiceRegistry();
        MetadataSources metadata = new MetadataSources(serviceRegistry);
        List<String> managedClassNames = persistenceUnitInfo.getManagedClassNames();
        for (String managedClassName : managedClassNames) {
            metadata.addAnnotatedClassName(managedClassName);
        }
        MetadataImplementor metadataImplementor = (MetadataImplementor) metadata.getMetadataBuilder().build();

        SchemaUpdate schemaUpdate = new SchemaUpdate(metadataImplementor);

        schemaUpdate.setOutputFile(getOutputFilename());

        schemaUpdate.setDelimiter(";");
        schemaUpdate.setFormat(false);
        schemaUpdate.execute(true, false);

    }


    private static String getOutputFilename() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDate = sdf.format(Calendar.getInstance().getTime());

        return String.format(SCHEMA_SQL2, currentDate);
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
