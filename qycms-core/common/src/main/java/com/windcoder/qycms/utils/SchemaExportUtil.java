package com.windcoder.qycms.utils;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class SchemaExportUtil {
    public static void main(String[] args) {
//        Configuration cfg=new Configuration().configure();
//        SchemaExport export = new SchemaExport(cfg);



        ServiceRegistry serviceRegistry = (ServiceRegistry) new StandardServiceRegistryBuilder().configure("qycms-core/common/src/main/resources/application.properties").build();
        MetadataImplementor metadataImplementor = (MetadataImplementor) new MetadataSources(serviceRegistry).buildMetadata();
        SchemaExport export = new SchemaExport(serviceRegistry, metadataImplementor);
        export.setOutputFile("schema.sql");
//        SchemaExport export = new EnversSchemaGenerator(config)

        export.execute(true, false, false, false);


//        Configuration cfg = new Configuration();
//        cfg.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
//        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        SchemaExport schemaExport = new SchemaExport(cfg);
//        schemaExport.execute(true, false, false, false);
    }
}
