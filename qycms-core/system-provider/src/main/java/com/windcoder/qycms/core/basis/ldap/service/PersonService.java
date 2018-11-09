package com.windcoder.qycms.core.basis.ldap.service;

import com.windcoder.qycms.core.basis.ldap.config.LdapSettingUtll;
import com.windcoder.qycms.core.basis.ldap.entity.Person;
import com.windcoder.qycms.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.ldap.LdapName;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class PersonService {
    @Autowired
    private LdapTemplate ldapTemplate;

    private static Logger logger = LoggerFactory.getLogger(PersonService.class);

    public void addPerson(Person person){
        Name dn = buildDn(person);
        DirContextAdapter ctx = new DirContextAdapter(dn);
        PersonToContext(person,ctx);
        ldapTemplate.bind(dn,ctx,null);
    }

    public void addNewDnPerson(Person person){
        Name dn = buildNewDn(person);
        DirContextAdapter ctx = new DirContextAdapter(dn);
        PersonToContext(person,ctx);
        ldapTemplate.bind(dn,ctx,null);
    }

    public void delete(Person person) {
        ldapTemplate.unbind(buildDn(person));
    }

    public void  update(Person person){
        Name dn = buildDn(person);
        DirContextAdapter ctx = (DirContextAdapter) ldapTemplate.lookup(dn);
        PersonToContext(person,ctx);
        ldapTemplate.modifyAttributes(dn,ctx.getModificationItems());
    }


    public List<Person> findAll(){
        return ldapTemplate.search(
                query().where("objectclass").is("inetOrgPerson"),PERSON_CONTEXT_MAPPER);
    }


    private final static ContextMapper<Person> PERSON_CONTEXT_MAPPER = new AbstractContextMapper<Person>() {
       @Override
       protected Person doMapFromContext(DirContextOperations ctx) {
           Person person = new Person();
           LdapName dn = LdapUtils.newLdapName(ctx.getDn());
           person.setUid(ctx.getStringAttribute("uid"));
           person.setCommonName(ctx.getStringAttribute("givenName"));
           person.setSuerName(ctx.getStringAttribute("sn"));
           person.setCompany(LdapUtils.getStringValue(dn, 0));
           person.setPhone(ctx.getStringAttribute("telephoneNumber"));
           person.setEmail(ctx.getStringAttribute("mail"));
//           if(ctx.getObjectAttribute("userPassword")!=null){
////               System.out.println("userPassword: "+ctx.getObjectAttribute("userPassword"));


//               person.setPassword(new String(String.valueOf(ctx.getObjectAttribute("userPassword"))));


//           }

           return person;
       }
    };


    public Person findByPrimaryKey(String company, String commpnName){
       LdapName dn = buildDn(company, commpnName);
       return ldapTemplate.lookup(dn,PERSON_CONTEXT_MAPPER);
    }

    protected LdapName buildDn(Person p){
        return buildDn(p.getCompany(), p.getCommonName());
    }

    protected LdapName buildDn(String company, String commpnName){
//        return LdapNameBuilder.newInstance(LdapConfig.BASE)
        return LdapNameBuilder.newInstance()
//                .add("ou","User")
                .add("cn", commpnName)
                .build();
    }

    protected Name buildNewDn(Person p) {
        return LdapNameBuilder.newInstance()
//                .add("ou", p.getCompany())
                .add("cn", p.getCommonName())
                .build();
    }

    public Person findByUid(String uid) {
        LdapQuery query = query()
                .where("objectclass").is("person")
                .and("uid").is(uid);
        List<Person> ps = ldapTemplate.search(query().where("uid").is(uid), PERSON_CONTEXT_MAPPER);
        if (ps.size()==1){
            return ps.get(0);
        }
        throw new BusinessException("获取多个或一个");

    }

    private void PersonToContext(Person person, DirContextAdapter ctx){
        ctx.setAttributeValues("objectclass", new String[] {"top", "person", "inetOrgPerson" });
        ctx.setAttributeValue("uid", person.getUid());
        ctx.setAttributeValue("sn", person.getSuerName());
        ctx.setAttributeValue("givenName", person.getCommonName());
        ctx.setAttributeValue("telephoneNumber", person.getPhone());
        ctx.setAttributeValue("mail",person.getEmail());
        ctx.setAttributeValue("userPassword", person.getPassword());

    }

    /**
     * 无需获取Ldap用户信息的情况下。
     * @param uid
     * @param password
     * @return
     */
    public Boolean authenticate(String uid, String password){

        try {
//            getDnForUser("uid",uid);
            ldapTemplate.authenticate(query().where("uid").is(uid), password);
            return  true;
        } catch (AuthenticationException e){
            throw new BusinessException("密码错误："+e.getMessage());
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException("用户名错误："+e.getMessage());
        }

    }

    /**
     * 需要获取用户信息时
     * @param uid
     * @param password
     * @return
     */
    public Person authenticate2(String uid, String password){
        return null;
    }


    private String getDnForUser(String uid) {

//        List<String> results = ldapTemplate.search("", "(&(objectclass=person)(uid="+uid+"))",  new DnMapper());
//
//        if (results.size() != 1) {
//            throw new RuntimeException("User not found or not unique");
//        }
//        System.out.println(results.get(0));
//        return results.get(0);
        return null;
    }

    private String getDnForUser(String key, String uid) {
        List<String> result = null;
        try {
            result = ldapTemplate.search(
                    query().where(key).is(uid),
                    new AbstractContextMapper() {
                        protected String doMapFromContext(DirContextOperations ctx) {
                            return ctx.getNameInNamespace();
                        }
                    });
        } catch (AuthenticationException e) {
//            logger.error(e.getMessage(), e);
            throw new BusinessException("Ldap配置中账号/密码错误");
        }


        if(result.size() != 1) {
            throw new BusinessException("用户名未找到或不唯一");
        }

        return result.get(0);
    }




}
