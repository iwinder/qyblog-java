package com.windcoder.qycms.core.basis.ldap;

import com.windcoder.qycms.core.basis.ldap.entity.Person;
import com.windcoder.qycms.core.basis.ldap.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ldap/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PutMapping("")
    public String addPersong(@RequestBody Person person){
        personService.addPerson(person);
        return "success";
    }

    @PostMapping("/{company}/{commonName}")
    public String updatePerson(@PathVariable("commonName") String commonName, @PathVariable("company") String company, Person person){
        Person old = personService.findByPrimaryKey(company,commonName);
        getNewPerson(old, person);
        personService.update(old);
        return "success";
    }

    @GetMapping("")
    public List<Person> findAll(){
        return personService.findAll();
    }
    @DeleteMapping("/{company}/{commonName}")
    public String delete(@PathVariable("commonName") String commonName, @PathVariable("company") String company){
        Person old = personService.findByPrimaryKey(company,commonName);
        personService.delete(old);
        return "success";
    }


    private void getNewPerson(Person old, Person person){
        if (!old.getCompany().equals(person.getCompany())){
            old.setCompany(person.getCompany());
        }

        if (!old.getCommonName().equals(person.getCommonName())){
            old.setCommonName(person.getCommonName());
        }

        if (!old.getEmail().equals(person.getEmail())){
            old.setEmail(person.getEmail());
        }

        if (!old.getSuerName().equals(person.getSuerName())){
            old.setSuerName(person.getSuerName());
        }

        if (!old.getPassword().equals(person.getPassword())){
            old.setPassword(person.getPassword());
        }
    }
}
