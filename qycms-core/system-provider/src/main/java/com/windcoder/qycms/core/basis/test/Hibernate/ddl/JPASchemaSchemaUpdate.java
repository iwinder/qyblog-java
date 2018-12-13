package com.windcoder.qycms.core.basis.test.Hibernate.ddl;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import javax.persistence.spi.PersistenceUnitInfo;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
//@EntityScan("com.windcoder.qycms.*")
//@AutoConfigureAfter({JPASchemaExtractor2.class})
public class JPASchemaSchemaUpdate implements ApplicationRunner {
    private static final String SCHEMA_SQL2 = "db/base/update-ddl_2_%s.sql";

    @Autowired
    LocalContainerEntityManagerFactoryBean fb;
    @Autowired
    PersistenceUnitInfo persistenceUnitInfo;
    @Autowired
    private  JpaProperties properties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, Object> setting = new HashMap<String,Object>();
        setting.putAll(properties.getProperties());
        setting.putAll(fb.getJpaPropertyMap());
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(setting)
                .build();

        MetadataSources metadata = new MetadataSources(standardServiceRegistry);
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
}
