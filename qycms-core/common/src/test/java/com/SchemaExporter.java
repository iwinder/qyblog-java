package com;

import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.Entity;

public class SchemaExporter {

     static String CONNECTIONURL = "jdbc:MySQL://localhost:3306/qycmsdev?characterEncoding=utf8&useSSL=false";
    static String USERNAME = "qycmsdev";
    static String PWD = "root";
    static String DRIVER = "=com.mysql.jdbc.Driver";
    public static org.hibernate.cfg.Configuration getConfiguration() {
        org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        for (BeanDefinition bd : scanner.findCandidateComponents("com.package.where.my.entitybeans.are")) {
            String name = bd.getBeanClassName();
            try {
                System.out.println("Added annotated entity class " + bd.getBeanClassName());
                cfg.addAnnotatedClass(Class.forName(name));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.format_sql", "true");
        cfg.setProperty("hibernate.hbm2ddl.auto", "update");
        cfg.setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");

        cfg.setProperty("hibernate.connection.url", CONNECTIONURL);
        cfg.setProperty("hibernate.connection.username", USERNAME);
        cfg.setProperty("hibernate.connection.password", PWD);
        cfg.setProperty("hibernate.connection.driver", DRIVER);
        return cfg;
    }

    public static void main(String[] args) {
        SchemaExport export = new SchemaExport(getConfiguration());
        export.setDelimiter(";");
        export.setHaltOnError(true);
        export.setFormat(true);
        export.create(true,true);
    }
}
