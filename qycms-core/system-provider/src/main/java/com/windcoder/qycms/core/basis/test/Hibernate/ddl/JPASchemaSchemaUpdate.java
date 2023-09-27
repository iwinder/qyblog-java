package com.windcoder.qycms.core.basis.test.Hibernate.ddl;


import org.hibernate.boot.MetadataSources;

import org.hibernate.boot.registry.StandardServiceRegistry;

import org.hibernate.boot.spi.MetadataImplementor;


import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.internal.EntityManagerFactoryImpl;

import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.List;


/**
 * 参考资料：
 * https://stackoverflow.com/questions/34612019/programmatic-schemaexport-schemaupdate-with-hibernate-5-and-spring-4
 */
@Component
public class JPASchemaSchemaUpdate implements ApplicationRunner {
    private static final String SCHEMA_SQL2 = "db/base/update-ddl_2_%s.sql";

    @Autowired
    LocalContainerEntityManagerFactoryBean fb;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        EntityManagerFactoryImpl emf=(EntityManagerFactoryImpl)fb.getNativeEntityManagerFactory();

        SessionFactoryImpl sf= (SessionFactoryImpl) emf.getSessionFactory();
        StandardServiceRegistry serviceRegistry = sf.getSessionFactoryOptions().getServiceRegistry();
        MetadataSources metadata = new MetadataSources(serviceRegistry);
        List<String> managedClassNames = fb.getPersistenceUnitInfo().getManagedClassNames();
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

}
