package com.windcoder.qycms.core.basis.test.Hibernate.ddl;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.spi.PersistenceUnitInfo;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
//@EntityScan("com.windcoder.qycms.*")
public class JPASchemaExtractor2 implements ApplicationRunner {
    private static final String SCHEMA_SQL2 = "db/base/create-ddl_2_%s.sql";

    @Autowired
    LocalContainerEntityManagerFactoryBean fb;
//    @Autowired
//    PersistenceUnitInfo persistenceUnitInfo;
    @Override
    public void run(ApplicationArguments args) throws Exception {


        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(fb.getJpaPropertyMap())
                .build();

        MetadataSources metadata = new MetadataSources(standardServiceRegistry);
//        List<String> managedClassNames = persistenceUnitInfo.getManagedClassNames();
        List<String> managedClassNames = fb.getPersistenceUnitInfo().getManagedClassNames();
        for (String managedClassName : managedClassNames) {
            metadata.addAnnotatedClassName(managedClassName);
        }

        MetadataImplementor metadataImplementor = (MetadataImplementor) metadata.getMetadataBuilder().build();
        SchemaExport schemaExport = new SchemaExport(metadataImplementor);
        String outputFile = getOutputFilename();
        schemaExport.setOutputFile(outputFile);
        schemaExport.setDelimiter(";");
        schemaExport.setFormat(false);
        schemaExport.execute(true, false, false, false);
    }


//    @ConditionalOnMissingBean({PersistenceUnitInfo.class})
//    @Bean
//    public PersistenceUnitInfo getPersistenceUnitInfo(EntityScanPackages entityScanPackages) {
//        List<String> packagesToScan = entityScanPackages.getPackageNames();
//
//        DefaultPersistenceUnitManager persistenceUnitManager = new DefaultPersistenceUnitManager();
//
//        String[] packagesToScanArr = (String[]) packagesToScan.toArray(new String[packagesToScan.size()]);
//        persistenceUnitManager.setPackagesToScan(packagesToScanArr);
//        persistenceUnitManager.afterPropertiesSet();
//
//        PersistenceUnitInfo persistenceUnitInfo = persistenceUnitManager.obtainDefaultPersistenceUnitInfo();
//        return persistenceUnitInfo;
//    }


    private static String getOutputFilename() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDate = sdf.format(Calendar.getInstance().getTime());

        return String.format(SCHEMA_SQL2, currentDate);
    }
}
