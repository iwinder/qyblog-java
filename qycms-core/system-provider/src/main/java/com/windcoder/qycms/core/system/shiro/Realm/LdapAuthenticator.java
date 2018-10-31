package com.windcoder.qycms.core.system.shiro.Realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.ldap.UnsupportedAuthenticationMechanismException;
import org.apache.shiro.realm.ldap.DefaultLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.AuthenticationNotSupportedException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

public class LdapAuthenticator  extends DefaultLdapRealm {

    @Autowired
    private LdapTemplate ldapTemplate;

    private static final Logger log = LoggerFactory
            .getLogger(LdapAuthenticator.class);

    private String rootDN;

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        AuthenticationInfo info;
        try {


            info = queryForAuthenticationInfo(token, getContextFactory());
        } catch (AuthenticationNotSupportedException e) {
            String msg = "Unsupported configured authentication mechanism";
            throw new UnsupportedAuthenticationMechanismException(msg, e);
        } catch (javax.naming.AuthenticationException e) {
            String msg = "LDAP authentication failed.";
            throw new AuthenticationException(msg, e);
        } catch (NamingException e) {
            String msg = "LDAP naming error while attempting to authenticate user.";
            throw new AuthenticationException(msg, e);
        } catch (UnknownAccountException e) {
            String msg = "UnknownAccountException";
            throw new UnknownAccountException(msg, e);
        } catch (IncorrectCredentialsException e) {
            String msg = "IncorrectCredentialsException";
            throw new IncorrectCredentialsException(msg, e);
        }

        return info;
    }



    protected AuthenticationInfo queryForAuthenticationInfo(
            AuthenticationToken token, LdapContextFactory ldapContextFactory)
            throws NamingException {
        Object principal = token.getPrincipal();
        Object credentials = token.getCredentials();

        LdapContext systemCtx = null;
        LdapContext ctx = null;
        try {
            systemCtx = ldapContextFactory.getSystemLdapContext();

            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration results = systemCtx.search(rootDN, "cn="
                    + principal, constraints);
            if (results != null && !results.hasMore()) {
                throw new UnknownAccountException();
            } else {
                while (results.hasMore()) {
                    SearchResult si = (SearchResult) results.next();
                    principal = si.getName() + "," + rootDN;
                }
                log.info("DN="+principal);
                try {
                    ctx = ldapContextFactory.getLdapContext(principal,
                            credentials);
                } catch (NamingException e) {
                    throw new IncorrectCredentialsException();
                }
                return createAuthenticationInfo(token, principal, credentials,
                        ctx);
            }
        } finally {
            LdapUtils.closeContext(systemCtx);
            LdapUtils.closeContext(ctx);
        }
    }
}
