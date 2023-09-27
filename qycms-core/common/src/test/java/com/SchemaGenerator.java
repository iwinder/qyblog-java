package com;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 * [Using Hibernate's SchemaExport Feature from within a Spring/JPA Context](
 * http://hillert.blogspot.com/2010/05/using-hibernates-schemaexport-feature.html)
 *
 * [java – 使用Hibernate 5和Spring 4编程SchemaExport/SchemaUpdate](https://codeday.me/bug/20180527/168560.html)
 */
public class SchemaGenerator {
    @Autowired
    LocalContainerEntityManagerFactoryBean fb;


    /**
     * Method that actually creates the file.
     * @param
     */
    private void generate(Dialect dialect)
    {
        Configuration cfg = new Configuration();
//        Configuration configured =
//                cfg.configure(fb.getPersistenceUnitInfo(), fb.getJpaPropertyMap());
//
//        org.hibernate.tool.hbm2ddl.SchemaExport schemaExport =
//            new org.hibernate.tool.hbm2ddl.SchemaExport(configured.getHibernateConfiguration());

        SchemaExport export = new SchemaExport(cfg);
        export.setDelimiter(";");
        export.setOutputFile("ddl_" + dialect.name().toLowerCase() + ".sql");
        export.execute(true, false, false, false);
    }





    /**
     * Holds the classnames of hibernate dialects for easy reference.
     */
    private static enum Dialect
    {
        ORACLE("org.hibernate.dialect.Oracle10gDialect"),
        MYSQL("org.hibernate.dialect.MySQLDialect"),
        HSQL("org.hibernate.dialect.HSQLDialect");

        private String dialectClass;
        private Dialect(String dialectClass)
        {
            this.dialectClass = dialectClass;
        }
        public String getDialectClass()
        {
            return dialectClass;
        }
    }
}
