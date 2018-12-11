package com.windcoder.qycms.core.basis.test.Hibernate.ddl;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import java.sql.Connection;
import java.sql.DriverManager;

public class JpaEntityExport {
    public static void main(String[] args) {
//        Connection connection =  DriverManager.getConnection("jdbc:h2:mem:jooq-meta-extensions", "sa", "");
        MetadataSources metadata = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                        .build());

// [...] adding annotated classes to metadata here...
//        metadata.addAnnotatedClass(...);

        SchemaExport export = new SchemaExport(
                (MetadataImplementor) metadata.buildMetadata()
        );
        export.create(true, true);
    }
}
