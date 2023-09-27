package com.windcoder.qycms.core.basis.ldap.config;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {

    public static final String URL = "ldap://192.168.137.129:389";
    public static final String URL3 = "ldaps://192.168.137.129:636";
    public static final String BASE = "ou=User,dc=windcoder,dc=com";
    public static final String USERDN = "cn=admin,dc=windcoder,dc=com";
    public static final String PASSWORD = "123456";


    public static final String URL2 = "ldap://192.168.0.79:389";
    public static final String BASE2 = "ou=User,dc=qimooc,dc=net";
    public static final String USERDN2 = "cn=Manager,dc=qimooc,dc=net";
    public static final String PASSWORD2 = "manager1";
    /*
     * SpringLdap的javaConfig注入方式
     */
    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSourceTarget());
    }

    /*
     * SpringLdap的javaConfig注入方式
     */
    @Bean
    public LdapContextSource contextSourceTarget() {
//        System.setProperty("javax.net.ssl.trustStore", "G:\\Work\\certs\\ldapserver.crt");
//        System.setProperty("javax.net.ssl.trustStore", "F:\\Java\\Java8\\jdk1.8.0_171\\jre\\lib\\security\\cacerts.jks");

        LdapContextSource ldapContextSource = new LdapContextSource();

//        ldapContextSource.setUrl(URL2);
//        ldapContextSource.setBase(BASE2);
//        ldapContextSource.setUserDn(USERDN2);
//        ldapContextSource.setPassword(PASSWORD2);

        ldapContextSource.setUrl(URL);
        ldapContextSource.setBase(BASE);
        ldapContextSource.setUserDn(USERDN);
        ldapContextSource.setPassword(PASSWORD);
        ldapContextSource.setReferral("follow");

        ldapContextSource.afterPropertiesSet();
        return ldapContextSource;
    }



}
