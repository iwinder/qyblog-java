package com.windcoder.qycms.core.basis.ldap.config;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

//@Configuration
public class LdapConfig {

    public static final String URL = "ldap://192.168.137.129:389";
    public static final String BASE = "ou=User,dc=windcoder,dc=com";
    public static final String USERDN = "cn=admin,dc=windcoder,dc=com";
    public static final String PASSWORD = "123456";
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
        LdapContextSource ldapContextSource = new LdapContextSource();

        ldapContextSource.setUrl(URL);
        ldapContextSource.setBase(BASE);
        ldapContextSource.setUserDn(USERDN);
        ldapContextSource.setPassword(PASSWORD);
        ldapContextSource.setReferral("follow");
        return ldapContextSource;
    }



}
