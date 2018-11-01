package com.windcoder.qycms.core.basis.ldap.config;

import com.windcoder.qycms.core.basis.ldap.entity.Ldap;
import com.windcoder.qycms.core.basis.ldap.entity.LdapSetting;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

public class LdapSettingUtll {

    private static LdapTemplate ldapTemplate;

    public static LdapTemplate getLdapTemplate(LdapSetting ldap){
        if (ldapTemplate==null){
            ldapTemplate = new LdapTemplate(getLdapContextSource(ldap));
        }
        return ldapTemplate;
    }


    private static LdapContextSource getLdapContextSource(LdapSetting ldap) {
        LdapContextSource ldapContextSource = new LdapContextSource();
        String url = ldap.getIsLDAPS() ? "ldap://" : "ldaps://" + ldap.getHost() + ":" + ldap.getPort() ;
        ldapContextSource.setUrl(url);
        ldapContextSource.setBase(ldap.getBaseDN());
        ldapContextSource.setUserDn(ldap.getUsername());
        ldapContextSource.setPassword(ldap.getPassword());
        ldapContextSource.afterPropertiesSet();
        return ldapContextSource;
    }
}
