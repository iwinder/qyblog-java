package com.windcoder.qycms.core.basis.ldap.service;

import com.windcoder.qycms.core.basis.ldap.config.LdapConfig;
import com.windcoder.qycms.core.basis.ldap.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.ldap.LdapName;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class PersonService {
    @Autowired
    private LdapTemplate ldapTemplate;

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
                query().where("objectclass").is("person"),PERSON_CONTEXT_MAPPER);
    }


    private final static ContextMapper<Person> PERSON_CONTEXT_MAPPER = new AbstractContextMapper<Person>() {
       @Override
       protected Person doMapFromContext(DirContextOperations ctx) {
           Person person = new Person();
           LdapName dn = LdapUtils.newLdapName(ctx.getDn());
           person.setCommonName(ctx.getStringAttribute("cn"));
           person.setSuerName(ctx.getStringAttribute("sn"));
           person.setCompany(LdapUtils.getStringValue(dn, 0));
           person.setPhone(ctx.getStringAttribute("telephoneNumber"));
//           person.setEmail(ctx.getStringAttribute("email"));
//           if(ctx.getObjectAttribute("userPassword")!=null){
////               System.out.println("userPassword: "+ctx.getObjectAttribute("userPassword"));
//               person.setPassword(String.valueOf(ctx.getObjectAttribute("userPassword")));
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
                .add("ou",company)
                .add("cn", commpnName)
                .build();
    }

    protected Name buildNewDn(Person p) {
        return LdapNameBuilder.newInstance()
                .add("ou", p.getCompany())
                .add("cn", p.getCommonName())
                .build();
    }


    private void PersonToContext(Person person, DirContextAdapter ctx){
        ctx.setAttributeValues("objectclass", new String[] {"top", "person" });
        ctx.setAttributeValue("cn", person.getCommonName());
        ctx.setAttributeValue("sn", person.getSuerName());

        ctx.setAttributeValue("telephoneNumber", person.getPhone());
//        ctx.setAttributeValue("email",person.getEmail());
        ctx.setAttributeValue("userPassword", person.getPassword());

    }

}
